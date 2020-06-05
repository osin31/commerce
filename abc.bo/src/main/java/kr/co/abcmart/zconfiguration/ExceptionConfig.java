package kr.co.abcmart.zconfiguration;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.util.UtilsRequest;
import kr.co.abcmart.util.UtilsResponse;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionConfig {

	private boolean isWrite(HttpServletRequest request, HttpServletResponse response) {
		return (UtilsRequest.isAjax(request) || UtilsRequest.isJson(request));
	}

	private void writeCondition(HttpServletRequest request, HttpServletResponse response, Exception e) {

		Map<String, Object> exceptionMap = new HashMap<>();

		exceptionMap.put("status", response.getStatus());
		exceptionMap.put("message", e.getMessage());
		exceptionMap.put("type", e.getClass().getName());
		if (e instanceof ValidatorException) {
			exceptionMap.put("fieldName", ((ValidatorException) e).getFieldName());
		} else {
			exceptionMap.put("fieldName", "");
		}
		UtilsResponse.writeJson(response, exceptionMap);
	}

	private ModelAndView handleResponse(HttpServletRequest request, HttpServletResponse response, Exception e,
			int statusCode) {

		e.printStackTrace();
		response.setStatus(statusCode);

		if (isWrite(request, response)) {
			// ajax 나 json 타입일 경우
			response.setStatus(statusCode);
			writeCondition(request, response, e);
		} else {

			ModelAndView mav = new ModelAndView("error/error");
			mav.addObject("exception", e);
			return mav;
		}

		return null;
	}

	/**
	 * 어플리케이션 에러 처리
	 *
	 * @param request
	 * @param response
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(HttpServletRequest request, HttpServletResponse response, Exception e) {
		return handleResponse(request, response, e, HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	/**
	 * controller에 등록된 action handler를 찾을 수 없을 경우 not found 처리
	 *
	 * @param request
	 * @param response
	 * @param e
	 * @return
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ModelAndView handleError404(HttpServletRequest request, HttpServletResponse response, Exception e) {
		return handleResponse(request, response, e, HttpStatus.NOT_FOUND.value());
	}

}