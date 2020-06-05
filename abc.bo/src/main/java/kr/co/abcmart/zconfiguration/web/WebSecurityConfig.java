package kr.co.abcmart.zconfiguration.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import kr.co.abcmart.constant.Const;
import kr.co.abcmart.security.BoAccessDeniedHandler;
import kr.co.abcmart.security.BoAuthenticationEntryPoint;
import kr.co.abcmart.security.BoAuthenticationFailHandler;
import kr.co.abcmart.security.BoAuthenticationSuccessHandler;
import kr.co.abcmart.security.DefaultAuthenticationProvider;
import kr.co.abcmart.security.acl.interceptor.ReloadableFilterInvocationSecurityMetadataSource;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 사용하기 위함
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String LOGIN_PAGE = "/system/login";
	public static final String SUCCESS_PAGE = "/";
	public static final String UNAUTHORIZED_PAGE = "/error/Unauthorized.jspa";

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// 공통 정의
		http.csrf().disable();
		http.headers().frameOptions().sameOrigin().httpStrictTransportSecurity().disable();

		// Security 예외 핸들링
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler()) // 권한 접근 실패시 - 로그인은 하였으나 접근 권한이 아닐때
				.authenticationEntryPoint(authenticationEntryPoint()); // 인증이 필요한대 인증을 하지 않았을 경우

		// Form 로그인 정의
		http.formLogin().loginPage("/system/login").loginProcessingUrl("/login/login-processing")
				.usernameParameter("username").passwordParameter("password")
				.successHandler(authenticationSuccessHandler()).failureHandler(authenticationFailureHandler());

//         로그아웃 정의
		http.logout().invalidateHttpSession(true) // 세션 만료 시킬 것인가? true
				.deleteCookies("JSESSIONID").logoutUrl("/login/logout-processing")
				.logoutSuccessHandler(logoutSuccessHandler());

		// 중복로그인 관련
		http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false).sessionRegistry(sessionRegistry())
				.expiredUrl("/system/login?expired=Y");

		// 인증 URL 필터
		http.addFilterBefore(filterSecurityInterceptor(), FilterSecurityInterceptor.class);

	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new WebHttpSessionListener());
	}

	@Bean
	public DefaultAuthenticationProvider authenticationProvider() {
		return new DefaultAuthenticationProvider();
	}

	@Bean
	public BoAuthenticationSuccessHandler authenticationSuccessHandler() {
		BoAuthenticationSuccessHandler handler = new BoAuthenticationSuccessHandler();
		handler.setAlwaysUseDefaultTargetUrl(false);
		handler.setUseReferer(false);
		handler.setTargetUrlParameter("returnUrl");
		handler.setDefaultTargetUrl(SUCCESS_PAGE);
		return handler;
	}

	@Bean
	public BoAuthenticationFailHandler authenticationFailureHandler() {

		BoAuthenticationFailHandler handler = new BoAuthenticationFailHandler();
		handler.setDefaultFailureUrl(LOGIN_PAGE);
		return handler;
	}

//
	@Bean
	public BoAuthenticationEntryPoint authenticationEntryPoint() {
		return new BoAuthenticationEntryPoint(LOGIN_PAGE);
	}

	@Bean
	public BoAccessDeniedHandler accessDeniedHandler() {

		BoAccessDeniedHandler boAccessDeniedHandler = new BoAccessDeniedHandler();
		boAccessDeniedHandler.setErrorPage(UNAUTHORIZED_PAGE);
		return boAccessDeniedHandler;
	}

	@Bean
	public SimpleUrlLogoutSuccessHandler logoutSuccessHandler() {
		SimpleUrlLogoutSuccessHandler handler = new SimpleUrlLogoutSuccessHandler();
		handler.setAlwaysUseDefaultTargetUrl(false);
		handler.setUseReferer(false);
		handler.setTargetUrlParameter("returnUrl");
		handler.setDefaultTargetUrl(LOGIN_PAGE);
		return handler;
	}

	/**
	 * ########################### spring-security-acl ###########################
	 **/

	/***
	 * FilterSecurityInterceptor는 SecurityFilterChain중 가장 마지막에 호출 되는데 이때 인증이 완료 된 후
	 * 자원에 대한 접근 여부를 판단하게 된다.
	 *
	 * FilterSecurityInterceptor가 작동 되기 위해서는 3가지 정보가 필요하다. 1. 사용자 인증 정보 2.
	 * Resource,URL등의 대상정보 3. 해당 인증 정보가 리소스에 접근 가능 한지 판단하는 주체
	 *
	 * @return FilterSecurityInterceptor
	 * @throws Exception
	 */
	@Bean
	public FilterSecurityInterceptor filterSecurityInterceptor() throws Exception {

		FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
		filterSecurityInterceptor.setAuthenticationManager(authenticationManager());
		filterSecurityInterceptor.setSecurityMetadataSource(filterInvocationSecurityMetadataSource());
		filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager());

		log.debug("filterSecurityInterceptor : {}", filterSecurityInterceptor);
		return filterSecurityInterceptor;
	}

	/**
	 * DB 에서 접근 권한 및 리소스를 가져온다.
	 *
	 * @return
	 */
	@Bean
	public FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource() {
		ReloadableFilterInvocationSecurityMetadataSource securityMetadataSource = new ReloadableFilterInvocationSecurityMetadataSource();
		securityMetadataSource.setCacheSeconds(30);

		securityMetadataSource.setAccessRootRole(Const.ROLE_ADMIN);
		securityMetadataSource.setAccessAuthenticatedFullyUrl(SUCCESS_PAGE);
		securityMetadataSource.setDefaultFailureUrl(LOGIN_PAGE);

		// 해당 URL은 security acl 검증 에서 제외 한다.
		securityMetadataSource.addIgnoreUrl("/");
		securityMetadataSource.addIgnoreUrl("/common/**");
		securityMetadataSource.addIgnoreUrl("/error/**");
		securityMetadataSource.addIgnoreUrl("/static/**");
		securityMetadataSource.addIgnoreUrl("/**/**.ico");
		securityMetadataSource.addIgnoreUrl("/noacl/**");
		securityMetadataSource.addIgnoreUrl("/bo/dashboard/**");
		securityMetadataSource.addIgnoreUrl("/po/dashboard/**");
		securityMetadataSource.addIgnoreUrl("/cmm/editor/image/upload");
		
		// 기획전, 이벤트 관련은 따로 업로드
		securityMetadataSource.addIgnoreUrl("/cmm/editor/image/promotion/upload");

		return securityMetadataSource;
	}

//    @Bean
//    public RoleHierarchy roleHierarchy() {
//        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
////        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_MANAGER\n ROLE_MANAGER > ROLE_MEMBER_MANAGER\n ROLE_MANAGER > ROLE_SAMPLE_MANAGER");
//        return roleHierarchy;
//    }
//
//    @Bean
//    public RoleVoter roleHierarchyVoter() {
//        return new RoleHierarchyVoter(roleHierarchy());
//    }

	/***
	 *
	 * 판단 주체 인증 정보와 대상(resource) 정보를 가지고 접근을 허용 할 것인지 말 것인지 판단하게 된다. 이때 판단 할 참고 자료를
	 * accessDecisionManager(판단주체) 에게 넘겨주고 어떤 기준으로 판단 할 것인지 정해준다.
	 *
	 * 판단 주체는 3가지의 결과를 리턴 한다 - ACCESS_GRANTED 허가 - ACCESS_DENIED 거절 - ACCESS_ABSTAIN
	 * 보류
	 *
	 * AffirmativeBased 등록된 Voter 객체중 하나라도 ACCESS_GRANGED의 리턴 값을 받으면 최종적으로 접근을 허가한다.
	 *
	 * @return
	 */
	@Bean
	public AffirmativeBased accessDecisionManager() {

		List<AccessDecisionVoter<? extends Object>> voters = new ArrayList<>();
		// 인증에 관한 판단
		// 권한에 관한 판단
		// ex) IS_AUTHENTICATED_ANONYMOUSLY(익명-비회원),IS_AUTHENTICATED_FULLY(인증된회원)
		voters.add(new AuthenticatedVoter());
		// 권한에 관한 판단
		// ex) ROLE_ADMIN(전체관리자),ROLE_MEMBER_MAGNAGER(회원관리자)
		voters.add(new RoleVoter());

		// role hierarchy
//        voters.add(roleHierarchyVoter());

		return new AffirmativeBased(voters);
	}

}