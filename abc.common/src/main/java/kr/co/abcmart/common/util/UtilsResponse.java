package kr.co.abcmart.common.util;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kr.co.abcmart.common.constant.BaseConst;

public class UtilsResponse {
	
	public static HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}
	

	public static void write(HttpServletResponse response,String data) {
		
		try {
			
			String contentType = response.getContentType();
			
			if(UtilsText.isBlank(contentType)) {
				response.setContentType(MediaType.TEXT_PLAIN_VALUE);
			}
			
	        response.setCharacterEncoding(BaseConst.DEFAULT_CHARSET_UTF_8);
	        PrintWriter out = response.getWriter();
	        out.println(data);
	        out.close();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void writeJson(HttpServletResponse response,String data) {
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		write(response, data);
	}
	
	public static void writeJson(HttpServletResponse response,Map<?,?> data) {
		writeJson(response, UtilsText.stringify(data));
	}
	
	public static void writeJson(HttpServletResponse response,List<?> data) {
		writeJson(response, UtilsText.stringify(data));
	}
	
	public static void writeJson(HttpServletResponse response,Object data) {
		writeJson(response, UtilsText.stringify(data));
	}
	
}
