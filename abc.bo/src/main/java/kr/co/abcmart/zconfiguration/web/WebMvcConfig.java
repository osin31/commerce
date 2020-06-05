package kr.co.abcmart.zconfiguration.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.constant.BaseConst;
import kr.co.abcmart.common.request.SubDomainMappingHandlerMapping;
import kr.co.abcmart.zconfiguration.ResourceBundleMessageSourceConfig;
import kr.co.abcmart.zconfiguration.argument.RequestArgumentResolver;
import kr.co.abcmart.zconfiguration.interceptor.ControllerMethodInterceptor;
import kr.co.abcmart.zconfiguration.multipart.CustomCommonsMultipartResolver;
import kr.co.abcmart.zconfiguration.view.resolver.JsonViewResolver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer, WebMvcRegistrations {

	/**
	 * @see ResourceBundleMessageSourceConfig.java
	 */
	@Autowired
	private LocaleChangeInterceptor localeChangeInterceptor;

	@Autowired
	private ControllerMethodInterceptor controllerMethodInterceptor;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		String profile = Config.getString("spring.profiles.active", "").toLowerCase();

		// local일 경우 static 경로 등록
		if (BaseConst.PROFILE_LOCAL.equals(profile)) {
			registry.addResourceHandler("/static/common/**").addResourceLocations("file:static/common/", "/common/");
			registry.addResourceHandler("/static/images/**").addResourceLocations("file:static/images/", "/images/");
		}

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor);
		registry.addInterceptor(controllerMethodInterceptor);
		registry.addInterceptor(deviceResolverHandlerInterceptor());

	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new RequestArgumentResolver());
		resolvers.add(deviceHandlerMethodArgumentResolver());

	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false).ignoreAcceptHeader(false).favorParameter(true)
				.useRegisteredExtensionsOnly(false).parameterName("_format").mediaType("html", MediaType.TEXT_HTML)
				.mediaType("json", MediaType.APPLICATION_JSON);
	}

	@Bean
	public ContentNegotiatingViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		// JSP ViewResolver
		InternalResourceViewResolver jspViewResolver = new InternalResourceViewResolver();
		jspViewResolver.setViewClass(JstlView.class);
		jspViewResolver.setPrefix("/WEB-INF/views/");
		jspViewResolver.setSuffix(".jsp");

		// JSON ViewResolver
		JsonViewResolver jsonViewResolver = new JsonViewResolver();

		// Content Negotiating ViewResolver
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);
		resolver.setUseNotAcceptableStatusCode(true);

		List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
		resolvers.add(jspViewResolver);
		resolvers.add(jsonViewResolver);

		resolver.setViewResolvers(resolvers);
		return resolver;
	}

	/***
	 * 
	 * @Desc : ibsheet 엑셀 다운로드 때문에 isMultipart를 재정의 한다. SheetWork값이 들어오면 멀티파트로 판단하지
	 *       않는다.
	 * @Method Name : multipartResolver
	 * @Date : 2019. 5. 3.
	 * @Author : zerocooldog@zen9.co.kr
	 * @return
	 */
	@Bean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
	public CommonsMultipartResolver multipartResolver() {
		CustomCommonsMultipartResolver multipartResolver = new CustomCommonsMultipartResolver();
		// 100000000 = 100MB
		multipartResolver.setMaxUploadSize(120000000);
		multipartResolver.setMaxUploadSizePerFile(120000000);

		return multipartResolver;
	}

	/**
	 * 접속 디바이스(mobile,tablet,pc) 판단하는 핸들러 등록
	 * 
	 * @return
	 */
	@Bean
	public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
		return new DeviceResolverHandlerInterceptor();
	}

	/**
	 * 접속 디바이스(mobile,tablet,pc) 판단하는 객체를 사용할 수 있도 Controller argument에 등록.
	 * 
	 * @return
	 */
	@Bean
	public DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver() {
		return new DeviceHandlerMethodArgumentResolver();
	}

	/***
	 * 서브 도메인 어노테이션 조작 할 수 있도록 설정.
	 */
	@Override
	public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
		SubDomainMappingHandlerMapping handlerMapping = new SubDomainMappingHandlerMapping();
		handlerMapping.setOrder(0);
		return handlerMapping;
	}
}
