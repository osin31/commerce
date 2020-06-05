package kr.co.abcmart.security.acl.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

import kr.co.abcmart.bo.system.service.SystemService;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.security.acl.AuthorizedUrl;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReloadableFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	public static final long DISABLE_REFRESH = -1;

	private long cacheMillis = DISABLE_REFRESH;

	private long lastUpdated;

	private String defaultFailureUrl;

	private String accessAuthenticatedFullyUrl;
	private String accessRootRole = Const.IS_AUTHENTICATED_FULLY;

	private List<String> ignoreUrls = new ArrayList<>();

	private final Map<RequestMatcher, Collection<ConfigAttribute>> backoffice = new LinkedHashMap<>();
	private final Map<RequestMatcher, Collection<ConfigAttribute>> partener = new LinkedHashMap<>();

	@Autowired
	private SystemService systemService;

	public ReloadableFilterInvocationSecurityMetadataSource() {
	}

	public void setCacheSeconds(int cacheSeconds) {
		this.cacheMillis = (cacheSeconds * 1000);
	}

	private boolean isRefresh() {
		return lastUpdated == 0 || cacheMillis != -1 && 0 < System.currentTimeMillis() - (cacheMillis + lastUpdated);
	}

	public String getAccessAuthenticatedFullyUrl() {
		return accessAuthenticatedFullyUrl;
	}

	/***
	 * 로그인 성공시 권한에 상관없이 접근 할 수 있는 페이지를 지정한다. 비인증 회원은 접근 할 수 없고 로그인 한 사용자들만 접근이 가능하다.
	 *
	 * @param accessAuthenticatedFullyUrl
	 */
	public void setAccessAuthenticatedFullyUrl(String accessAuthenticatedFullyUrl) {
		this.accessAuthenticatedFullyUrl = accessAuthenticatedFullyUrl;
	}

	/***
	 * URL(인증) 접근 실패 시 이동할 페이지 설정.
	 *
	 * @param loginPage 인증 실패시 이동할 페이지.
	 */
	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	public List<String> getIgnoreUrls() {
		return ignoreUrls;
	}

	public String getAccessRootRole() {
		return accessRootRole;
	}

	public void setAccessRootRole(String accessRootRole) {
		this.accessRootRole = accessRootRole;
	}

	/***
	 * spring acl filter에서 특정 URL은 검증 하지 않게 제외한다. 제외된 URL은 인증,비인증 모두 접근이 가능하다. ex)
	 * /test/** or /test/*.gif
	 *
	 * @param ignoreUrl 제외 URL
	 */
	public void addIgnoreUrl(String ignoreUrl) {
		this.ignoreUrls.add(ignoreUrl);
	}

	public void setIgnoreUrl(String ignoreUrl) {
		this.ignoreUrls.add(ignoreUrl);
	}

	/***
	 * spring acl filter에서 특정 URL은 검증 하지 않게 제외한다. 제외된 URL은 인증,비인증 모두 접근이 가능하다. ex)
	 * /test/** or /test/*.gif
	 *
	 * @param ignoreUrls 제외 URL
	 */
	public void setIgnoreUrls(List<String> ignoreUrls) {
		this.ignoreUrls = ignoreUrls;
	}

	/***
	 * spring acl filter에서 특정 URL은 검증 하지 않게 제외한다. 제외된 URL은 인증,비인증 모두 접근이 가능하다. ex)
	 * /test/** or /test/*.gif
	 *
	 * @param ignoreUrls 제외 URL
	 */
	public void setIgnoreUrls(String... ignoreUrls) {

		for (String ignoreUrl : ignoreUrls) {
			this.ignoreUrls.add(ignoreUrl);
		}
	}

	/***
	 * 실제 url 호출 할 경우 실행 된다. db에서 읽어온 정보로 matcher작동 시켜 일치하면 ConfigAttribute를 리턴 하고
	 * 아니면 null로 리턴 한다. null로 리턴하면 권한 체크를 하지 않아 모든 페이지에 접근이 가능하다.
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

		refreshSecurityMetadata();

		final HttpServletRequest request = ((FilterInvocation) object).getRequest();

		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : getRequestMatcher().entrySet()) {

			if (entry.getKey().matches(request)) {
				return entry.getValue();
			}
		}
		return null;
	}

	/***
	 * 서버 구동시 AbstractSecurityInterceptor에 의해 실행 된다. DB에서 ROLE,RESOURCES 정보들을 읽어와 미리
	 * 설정 한 뒤 ConfigAttribute를 통하여 유효성 검사를 한다.
	 *
	 */
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {

		refreshSecurityMetadata();

		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : getRequestMatcher().entrySet()) {
			allAttributes.addAll(entry.getValue());
		}

		return allAttributes;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	public List<AuthorizedUrl> getAuthorizedUrls() throws Exception {
		List<String> allRoles = systemService.getAllRoles();

		List<AuthorizedUrl> allUrls = new ArrayList<>();

		allUrls.add(addAuthorizedUrlIgnore(getDefaultFailureUrl(), Const.IS_AUTHENTICATED_ANONYMOUSLY));
		for (String ignoreUrl : ignoreUrls) {
			allUrls.add(addAuthorizedUrlIgnore(ignoreUrl, Const.IS_AUTHENTICATED_ANONYMOUSLY,
					Arrays.asList(Const.IS_AUTHENTICATED_FULLY)));
		}

		if (!UtilsText.isBlank(getAccessAuthenticatedFullyUrl())) {
			allUrls.add(addAuthorizedUrlIgnore(getAccessAuthenticatedFullyUrl(), Const.IS_AUTHENTICATED_FULLY));
		}

		if (allRoles != null) {
			for (String role : allRoles) {
				List<AuthorizedUrl> urls = systemService.getAuthorizedUrls(role);
				if (urls != null) {
					for (AuthorizedUrl url : urls) {
						int idx = allUrls.indexOf(url);
						if (idx == -1) {
							allUrls.add(url);
						} else {
							AuthorizedUrl object = allUrls.get(idx);
							object.addConfigAttributes(url.getConfigAttributes());
							allUrls.set(idx, object);
						}
					}
				}
			}
		}

		allUrls.add(addAuthorizedUrlIgnore("/**", getAccessRootRole()));

		return allUrls;
	}

	/***
	 * ignore URL 정의
	 *
	 * @param ignoreUrl
	 * @return
	 */
	private AuthorizedUrl addAuthorizedUrlIgnore(String ignoreUrl) {
		return addAuthorizedUrlIgnore(ignoreUrl, Const.IS_AUTHENTICATED_FULLY);
	}

	private AuthorizedUrl addAuthorizedUrlIgnore(String ignoreUrl, String role) {
		return addAuthorizedUrlIgnore(ignoreUrl, role, null);
	}

	private AuthorizedUrl addAuthorizedUrlIgnore(String ignoreUrl, String role, List<String> addRoles) {
		return addAuthorizedUrlIgnore(ignoreUrl, 0, role, addRoles);
	}

	private AuthorizedUrl addAuthorizedUrlIgnore(String ignoreUrl, int order, String role, List<String> addRoles) {
		return addAuthorizedUrlIgnore(ignoreUrl, null, order, role, addRoles);
	}

	private AuthorizedUrl addAuthorizedUrlIgnore(String ignoreUrl, String httpMethod, String role,
			List<String> addRoles) {
		return addAuthorizedUrlIgnore(ignoreUrl, httpMethod, 0, role, addRoles);
	}

	private AuthorizedUrl addAuthorizedUrlIgnore(String ignoreUrl, String httpMethod, int order, String role,
			List<String> addRoles) {

		AuthorizedUrl ignore = new AuthorizedUrl(null, ignoreUrl, httpMethod, order, role);

		if (addRoles != null) {
			for (String addRole : addRoles) {
				ignore.addConfigAttributes(addRole);
			}
		}

		return ignore;
	}

	protected void refreshSecurityMetadata() {
		if (!isRefresh())
			return;

		log.debug("metadata refreshed time : {} ", System.currentTimeMillis() - (cacheMillis + lastUpdated));

		lastUpdated = System.currentTimeMillis();
		backoffice.clear();
		partener.clear();

		List<AuthorizedUrl> authorizedUrls = null;

		try {
			authorizedUrls = getAuthorizedUrls();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (authorizedUrls != null) {
			for (AuthorizedUrl url : authorizedUrls) {

				if (Const.AUTH_APPLY_SYSTEM_TYPE_BO.equals(url.getAuthApplySystemType())) {
					backoffice.put(url.getRequestMatcher(), url.getConfigAttributes());
				} else if (Const.AUTH_APPLY_SYSTEM_TYPE_PO.equals(url.getAuthApplySystemType())) {
					partener.put(url.getRequestMatcher(), url.getConfigAttributes());
				} else {
					backoffice.put(url.getRequestMatcher(), url.getConfigAttributes());
					partener.put(url.getRequestMatcher(), url.getConfigAttributes());
				}
			}
		}

		log.debug("SecurityMetadata Updated (Rules: {})", backoffice.size());
	}

	public Map<RequestMatcher, Collection<ConfigAttribute>> getRequestMatcher() {

		if (LoginManager.isLogin()) {
			UserDetails user = LoginManager.getUserDetails();

			if (Const.ROLE_ADMIN_GROUP.equals(user.getUpAuthNo())) {
				return backoffice;
			} else if (Const.ROLE_VENDER_GROUP.equals(user.getUpAuthNo())) {
				return partener;
			}
		}

		return backoffice;
	}

}
