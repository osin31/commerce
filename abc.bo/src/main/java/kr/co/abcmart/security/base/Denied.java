package kr.co.abcmart.security.base;

import org.springframework.security.access.AccessDeniedException;

import kr.co.abcmart.common.request.Parameter;

public interface Denied{

	public void doProcess (Parameter<?> parameter,AccessDeniedException exception);

}
