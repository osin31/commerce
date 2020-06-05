package kr.co.abcmart.security.base;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.util.UtilsResponse;

public interface DefaultAuthenticationHandler{
	
	public default void writeResponse(Parameter<?> parameter,Auth auth,Exception exception, String message) {
		
		Map<String,Object> result = new HashMap<>();
		
		if(exception != null) {
			parameter.getResponse().setStatus(HttpStatus.UNAUTHORIZED.value());
		}
		
		result.put("status", parameter.getResponse().getStatus());
		result.put("type", (exception != null) ? exception.getClass().getName() : "");
		result.put("message", UtilsText.isBlank(message) ? exception.getMessage() : message);
		result.put("auth", auth);
		
		UtilsResponse.writeJson(parameter.getResponse(), result);
	}
	
}
