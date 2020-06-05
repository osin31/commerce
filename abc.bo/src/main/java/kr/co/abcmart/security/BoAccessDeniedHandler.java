package kr.co.abcmart.security;

import org.springframework.security.access.AccessDeniedException;

import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.security.base.BaseAccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoAccessDeniedHandler extends BaseAccessDeniedHandler {

	@Override
	public void doProcess(Parameter<?> parameter, AccessDeniedException exception) {

		log.debug(DENIED_MESSAGE);
	}
	

}