package kr.co.abcmart.security.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import kr.co.abcmart.common.message.Message;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.util.UtilsRequest;
import kr.co.abcmart.util.UtilsResponse;
import kr.co.abcmart.zconfiguration.exception.RedirectException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint implements DefaultAuthenticationHandler,Fail{
	
	public final String UNAUTHORIZED_MESSAGE = Message.getMessage("member.msg.login.unauthorized");

	private DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public BaseAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}
	
	private boolean redirect;

	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

    
		Parameter<?> parameter = new Parameter<>(request, response);
		
		doProcess(parameter, exception);
		
		boolean isWrite = (UtilsRequest.isAjax() || UtilsRequest.isJson(request)) ;
		
    	if(isWrite) {
			writeResponse(parameter, Auth.U,exception, UNAUTHORIZED_MESSAGE);
		}else if(!redirect){
			super.commence(request, response, exception);
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