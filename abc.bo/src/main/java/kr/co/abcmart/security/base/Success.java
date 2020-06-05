package kr.co.abcmart.security.base;

import org.springframework.security.core.Authentication;

import kr.co.abcmart.common.request.Parameter;

public interface Success{
	
	public void doProcess (Parameter<?> parameter,Authentication authentication);

}
