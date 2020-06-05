package kr.co.abcmart.common.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UtilsRequest  {
	
    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    
    private static final String X_REQUESTED_WITH = "X-Requested-With";
	
	public static HttpSession getSession() {
		return getSession(UtilsRequest.getRequest(),true);
	}
	
	public static HttpSession getSession(boolean create) {
		return getSession(UtilsRequest.getRequest(),create);
	}
	
	public static HttpSession getSession(HttpServletRequest request) {
		return getSession(request, true);
	}

	public static HttpSession getSession(HttpServletRequest request,boolean create) {
		return request.getSession(create);
	}

	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	
	/***
	 * 
	 * ajax 호출 여부 판별 
	 * 
	 * 권장  : isAjax(HttpServletRequest request)
	 * 
	 * HttpServletRequest 객체를 주입 받지 않을 경우 권장.
	 * @param request
	 * @return boolean
	 */
	public static boolean isAjax() {
        return isAjax(UtilsRequest.getRequest());
    }
	
	/***
	 * 
	 * ajax 호출 여부 판별 
	 * 
	 * HttpServletRequest 객체를 직접 주입 받을 경우 권장.
	 * @param request
	 * @return boolean
	 */
	public static boolean isAjax(HttpServletRequest request) {
        return XML_HTTP_REQUEST.equals(request.getHeader(X_REQUESTED_WITH));
    }
	
	public static boolean isJson(HttpServletRequest request) {
        if (request == null || request.getHeader("Accept") == null)
            return false;

        String accept = request.getHeader("Accept").toLowerCase(Locale.ENGLISH);
        return MediaType.APPLICATION_JSON_VALUE.equals(accept);
	}
	
	
	public static boolean isJson() {
		return isJson(getRequest());
	}
}
