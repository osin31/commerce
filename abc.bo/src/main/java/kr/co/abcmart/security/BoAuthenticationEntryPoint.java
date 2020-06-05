package kr.co.abcmart.security;

import org.springframework.security.core.AuthenticationException;

import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.security.base.BaseAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoAuthenticationEntryPoint extends BaseAuthenticationEntryPoint {
	
	public BoAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	@Override
	public void doProcess(Parameter<?> parameter, AuthenticationException exception) {
		log.debug("BoAuthenticationEntryPoint" + UNAUTHORIZED_MESSAGE);
		
		
	}
	

}