package kr.co.abcmart.security.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import kr.co.abcmart.common.message.Message;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.util.UtilsRequest;
import kr.co.abcmart.util.UtilsResponse;
import kr.co.abcmart.zconfiguration.exception.RedirectException;

public abstract class BaseAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements DefaultAuthenticationHandler,Success{

	public final String SUCCESS_MESSAGE = Message.getMessage("member.msg.login.success");
	
	private boolean redirect;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
		Parameter<?> parameter = new Parameter(request,response);
		
		doProcess(parameter, authentication);
		
		
		boolean isWrite = (UtilsRequest.isAjax() || UtilsRequest.isJson(request)) ;
		
    	if(isWrite) {
			writeResponse(parameter, Auth.S,null, SUCCESS_MESSAGE);
			clearAuthenticationAttributes(request);
		}else if(!redirect){
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}
	
	public void redirect(String redirectUrl) throws RedirectException {

		this.redirect = true;

		try {
			getRedirectStrategy().sendRedirect(UtilsRequest.getRequest(), UtilsResponse.getResponse(), redirectUrl);
			clearAuthenticationAttributes(UtilsRequest.getRequest());			
		} catch (Exception e) {
			throw new RedirectException(e.getMessage());
		}
	}
	



}
