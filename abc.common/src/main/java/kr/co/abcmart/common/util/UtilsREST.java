package kr.co.abcmart.common.util;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.co.abcmart.common.request.Parameter;

/**
 * Rest 호출시 사용하는 클래스
 * 
 * @author zerocooldog@zen9.co.kr
 */
public class UtilsREST{
	
	public static Map<String,Object> responseMessage(HttpServletResponse response,  Exception e) {
		
		Map<String,Object> result = new LinkedHashMap<>();

		result.put("status", response.getStatus());
		if(e != null) {
			result.put("type", e.getClass().getName());
			result.put("message", e.getMessage());
		} else {
			HttpStatus statusValue = HttpStatus.valueOf(response.getStatus());
			result.put("type", statusValue);
			result.put("message", statusValue);
		}
		result.put("data", null);
		
		return result;
	}
	
	public static ResponseEntity<?> responseOk(Parameter<?> parameter) {
		return responseOk(parameter.getResponse(), null);
	}
	
	public static ResponseEntity<?> responseOk(Parameter<?> parameter ,Object object) {
		return responseOk(parameter.getResponse(), object);
	}
	
	public static ResponseEntity<?> responseOk(HttpServletResponse response ,Object object) {
		return response(response, object, HttpStatus.OK);
	}
	
	public static ResponseEntity<?> response(Parameter<?> parameter ,Object object, HttpStatus httpStatus) {
		return response(parameter.getResponse(), object, httpStatus);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ResponseEntity<?> response(HttpServletResponse response ,Object object, HttpStatus httpStatus) {
		
		Map<String,Object> result = responseMessage(response, null);
		result.put("data", object);
		
		return new ResponseEntity(result, httpStatus);
	}
}
