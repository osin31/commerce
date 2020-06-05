package kr.co.abcmart.security.base;

import org.springframework.security.core.AuthenticationException;

import kr.co.abcmart.common.request.Parameter;

public interface Fail{

	public void doProcess (Parameter<?> parameter,AuthenticationException exception);

}
