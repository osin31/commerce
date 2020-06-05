package kr.co.abcmart.security.acl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode(exclude = { "configAttributes", "order" })
public class AuthorizedUrl implements Serializable, Ordered, Comparable<AuthorizedUrl> {

	@Getter
	private final String authApplySystemType;

	@Getter
	private final String pattern;

	@Getter
	private final String httpMethod;

	@Getter
	private final RequestMatcher requestMatcher;

	@Getter
	private final List<ConfigAttribute> configAttributes = new ArrayList<>();

	private final int order;

	public AuthorizedUrl(String authApplySystemType, String pattern, String... configAttributes) {
		this(authApplySystemType, pattern, null, 0, configAttributes);
	}

	public AuthorizedUrl(String authApplySystemType, String pattern, int order, String... configAttributes) {
		this(authApplySystemType, pattern, null, order, configAttributes);
	}

	public AuthorizedUrl(String authApplySystemType, String pattern, String httpMethod, int order,
			String... configAttributes) {
		this.authApplySystemType = authApplySystemType;
		this.pattern = pattern;
		this.httpMethod = httpMethod;
		this.order = order;
		this.requestMatcher = new AntPathRequestMatcher(pattern, httpMethod);
		this.addConfigAttributes(configAttributes);
	}

	public void addConfigAttributes(String... configAttributes) {
		if (configAttributes == null || configAttributes.length == 0)
			return;

		for (String configAttribute : configAttributes) {
			SecurityConfig config = new SecurityConfig(configAttribute.trim());

			if (!this.configAttributes.contains(config)) {
				this.configAttributes.add(config);
			}
		}
	}

	public void addConfigAttributes(List<ConfigAttribute> configAttributes) {
		if (configAttributes == null || configAttributes.size() == 0)
			return;

		for (ConfigAttribute configAttribute : configAttributes) {
			if (!this.configAttributes.contains(configAttribute)) {
				this.configAttributes.add(configAttribute);
			}
		}
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	@Override
	public int compareTo(AuthorizedUrl other) {
		return Integer.compare(this.getOrder(), other.getOrder());
	}

}
