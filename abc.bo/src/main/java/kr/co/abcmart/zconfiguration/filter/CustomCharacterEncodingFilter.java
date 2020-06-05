/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kr.co.abcmart.zconfiguration.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.core.Ordered;
import org.springframework.web.filter.CharacterEncodingFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * Servlet Filter that allows one to specify a character encoding for requests.
 * This is useful because current browsers typically do not set a character
 * encoding even if specified in the HTML page or form.
 *
 * <p>
 * This filter can either apply its encoding if the request does not already
 * specify an encoding, or enforce this filter's encoding in any case
 * ("forceEncoding"="true"). In the latter case, the encoding will also be
 * applied as default response encoding (although this will usually be
 * overridden by a full content type set in the view).
 *
 * @author Juergen Hoeller
 * @since 15.03.2004
 * @see #setEncoding
 * @see #setForceEncoding
 * @see javax.servlet.http.HttpServletRequest#setCharacterEncoding
 * @see javax.servlet.http.HttpServletResponse#setCharacterEncoding
 */
@Slf4j
public class CustomCharacterEncodingFilter extends CharacterEncodingFilter implements OrderedFilter {

	private Map<String, String> dynamicEncoding = new HashMap<>();

	private int order = Ordered.HIGHEST_PRECEDENCE;

	@Override
	public int getOrder() {
		return this.order;
	}

	/**
	 * Set the order for this filter.
	 * 
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * Create a default {@code CharacterEncodingFilter}, with the encoding to be set
	 * via {@link #setEncoding}.
	 * 
	 * @see #setEncoding
	 */
	public CustomCharacterEncodingFilter() {
	}

	/**
	 * Create a {@code CharacterEncodingFilter} for the given encoding.
	 * 
	 * @param encoding the encoding to apply
	 * @since 4.2.3
	 * @see #setEncoding
	 */
	public CustomCharacterEncodingFilter(String encoding) {
		super(encoding);
	}

	/**
	 * Create a {@code CharacterEncodingFilter} for the given encoding.
	 * 
	 * @param encoding      the encoding to apply
	 * @param forceEncoding whether the specified encoding is supposed to override
	 *                      existing request and response encodings
	 * @since 4.2.3
	 * @see #setEncoding
	 * @see #setForceEncoding
	 */
	public CustomCharacterEncodingFilter(String encoding, boolean forceEncoding) {
		super(encoding, forceEncoding, forceEncoding);
	}

	/**
	 * Create a {@code CharacterEncodingFilter} for the given encoding.
	 * 
	 * @param encoding              the encoding to apply
	 * @param forceRequestEncoding  whether the specified encoding is supposed to
	 *                              override existing request encodings
	 * @param forceResponseEncoding whether the specified encoding is supposed to
	 *                              override existing response encodings
	 * @since 4.3
	 * @see #setEncoding
	 * @see #setForceRequestEncoding(boolean)
	 * @see #setForceResponseEncoding(boolean)
	 */
	public CustomCharacterEncodingFilter(String encoding, boolean forceRequestEncoding, boolean forceResponseEncoding) {
		super(encoding, forceRequestEncoding, forceResponseEncoding);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String encoding = getEncoding();

		if (encoding != null) {
			// 인코딩 동적 처리시 절 때 request.getParameter 를 절대 호출 하지 않는다. 해당 메소드 호출후에
			// setCharacterEncoding 호출 해도 값이 변경 되지 않는다. 이미 getParameter를 호출 할때 변경하기전 인코딩 값으로
			// 설정되어 인코딩 되기 때문이다.
			// 인코딩을 동적으로 처리 할 경우 등록된 값에 따라 처리.
			if (dynamicEncoding.containsKey(request.getRequestURI())) {
				request.setCharacterEncoding(dynamicEncoding.get(request.getRequestURI()));
			} else {
				if (isForceRequestEncoding() || request.getCharacterEncoding() == null) {
					request.setCharacterEncoding(encoding);
				}
			}

			if (isForceResponseEncoding()) {
				response.setCharacterEncoding(encoding);
			}
		}

		filterChain.doFilter(request, response);
	}

	/***
	 * 
	 * @Desc : 외부에서 UTF-8이 아닌 다른 encoding 형식으로 값을 받을 경우 문자열이 깨진다. 외부에서 던저주는 값을 받는
	 *       uri를 지정하여 동적으로 인코딩을 변경한다.
	 * @Method Name : addUriToEncoding
	 * @Date : 2019. 5. 8.
	 * @Author : user
	 * @param uri
	 * @param charset
	 */
	public void addUriToEncoding(String uri, String charset) {
		dynamicEncoding.put(uri, charset);
	}

}
