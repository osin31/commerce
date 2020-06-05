package kr.co.abcmart.security.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import kr.co.abcmart.common.message.Message;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.util.UtilsRequest;
import kr.co.abcmart.util.UtilsResponse;
import kr.co.abcmart.zconfiguration.exception.RedirectException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseAccessDeniedHandler extends AccessDeniedHandlerImpl implements DefaultAuthenticationHandler, Denied{
	
	public final String DENIED_MESSAGE = Message.getMessage("member.msg.login.accessDenied");

	DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	private boolean redirect;

    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {

		Parameter<?> parameter = new Parameter<>(request, response);
		
		doProcess(parameter, exception);
		
		boolean isWrite = (UtilsRequest.isAjax() || UtilsRequest.isJson(request)) ;
		
    	if(isWrite) {
			writeResponse(parameter, Auth.D, exception, DENIED_MESSAGE);
		}else if(!redirect){
			super.handle(request, response, exception);
		}
    }
    
	public void redirect(String redirectUrl) throws RedirectException {

		this.redirect = true;

		try {
			redirectStrategy.sendRedirect(UtilsRequest.getRequest(), UtilsResponse.getResponse(), redirectUrl);
		} catch (Exception e) {
			throw new RedirectException(e.getMessage());
		}
	}


}