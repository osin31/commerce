package kr.co.abcmart.zconfiguration.interceptor;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.common.util.UtilsRequest;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.trace.SQLTrace;
import kr.co.abcmart.trace.TraceContext;
import kr.co.abcmart.trace.handler.SQLTraceHandler;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @Desc : 컨트롤러 실행 시 전, 후 처리
 * @FileName : ControllerMethodInterceptor.java
 * @Project : abc.bo
 * @Date : 2019. 3. 13.
 * @Author : 장진철 (zerocooldog@zen9.co.kr
 */
@Slf4j
@Component
public class ControllerMethodInterceptor implements HandlerInterceptor {

	@Autowired
	SQLTraceHandler sqlTraceHandler;

	private String[] arrExceptUrlList = { "/noacl/setForceLogin", "/noacl/getPoLoginCrtfcNo",
			"/noacl/setPoLoginParameter" };

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestURI	= request.getRequestURI();
//		String parameter 	= "";	// 요청 파라미터
		String menuYn 		= "N";
		boolean isEndSlash	= request.getRequestURI().endsWith("/");

		if (UtilsText.countMatches(request.getRequestURI(), "/noacl") > 0) {
			if (!Arrays.asList(arrExceptUrlList).contains(request.getRequestURI())) {
				if (!LoginManager.isLogin()) {
					response.sendRedirect("/system/login");
					return false;
				}
			}
		}

		if(isEndSlash && requestURI.length() > 1) {
			requestURI = requestURI.substring(0, requestURI.length() - 1);
		}

		HandlerMethod handlerMethod = getHandlerMethod(handler);

		if (handlerMethod != null && !Arrays.asList(arrExceptUrlList).contains(request.getRequestURI())) {
			menuYn = (handlerMethod.isVoid() == false) ? "Y" : "N";
			SQLTrace sqlTrace = new SQLTrace();
			sqlTrace.setMenuYn(menuYn);
			sqlTrace.setAccessUrl(requestURI);

//			if("POST".equals(request.getMethod())){
//				Enumeration<String> chkData = request.getParameterNames(); //java.util.Enumeration
//				String paramName  = "";
//				String paramValue = "";
//
//				while(chkData.hasMoreElements()){
//					paramName  = chkData.nextElement().toString();
//					paramValue = request.getParameter(paramName);
//					parameter += "&" + paramName + "=" + paramValue;
//				}
//	        }else{	// get 방식 호출일 경우
//	            parameter = request.getQueryString();
//	        }

//			sqlTrace.setAccessParameterText(parameter);
			sqlTrace.setAccessIpText(this.getIpAddress());
			sqlTrace.setSucessYn("Y");
			TraceContext.set(sqlTrace);
		}

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	public String getIpAddress() {
		for (String header : Const.HEADER_NAMES_FOR_CLIENT_IP) {
			String ipAddress = UtilsRequest.getRequest().getHeader(header);
			if (UtilsText.isNotEmpty(ipAddress)) {
				return UtilsText.trim(UtilsText.split(ipAddress, ",")[0]);
			}
		}
		return UtilsRequest.getRequest().getRemoteAddr();
	}
	
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		HandlerMethod handlerMethod = getHandlerMethod(handler);

		// ※[고정] 수정 금지
		// Controller 메소드가 void 일 경우에 Spring view에서 자동으로 ModelAndView객체를 생성한다.
		// 이때 jsp페이지로 이동 하여 404에러가 뜨는데 void는 필요 없으므로 ModelAndView 객체를 비워준다.
		if (handlerMethod != null && handlerMethod.isVoid() && modelAndView != null) {

			if (UtilsText.isBlank(response.getContentType())) {
				response.setContentType(MediaType.TEXT_HTML_VALUE);
			}

			response.setStatus(HttpStatus.OK.value());
			modelAndView.clear();
		}

		if (handlerMethod != null && !Arrays.asList(arrExceptUrlList).contains(request.getRequestURI())) {
			SQLTrace sqlTrace = TraceContext.getContext();
			if(sqlTrace != null) {
				List list = TraceContext.getSqlTrace();

				for(int i = 0; i < list.size(); i++){
					SQLTrace param = (SQLTrace)list.get(i);
					sqlTraceHandler.call(LoginManager.getUserDetails(), param);
				}
			}
			TraceContext.sqlTraceClean();
		}
		TraceContext.clear();
	}

	/**
	 * HandlerMethod를 리턴 한다.
	 *
	 * @param handler HandlerMethod
	 * @return HandlerMethod
	 */
	private HandlerMethod getHandlerMethod(Object handler) {

		if (handler instanceof HandlerMethod) {
			return (HandlerMethod) handler;
		}

		return null;
	}
}