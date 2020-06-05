package kr.co.abcmart.common.message;

import java.util.Locale;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.MessageSource;

import kr.co.abcmart.common.util.UtilsCookie;

public class Message {

	private static MessageSource messageSource;
	
	public static MessageSource getMessageSource() {
		return messageSource;
	}

	public static void setMessageSource(MessageSource messageSource) {
		Message.messageSource = messageSource;
	}

	/**
	 * 메세지 code 를 입력 ex) "message.code.alert"
	 * Message.getMessage("message.code.alert")	<br><br>
	 *
	 * @param code 메세지 프로퍼티에 등록 되어 있는 프로퍼티명
	 * @return
	 */
	public static String getMessage(String code) {
		return getMessage(code, null, Locale.getDefault());
	}
	
	/**
	 * 메세지 code 를 입력 ex) "message.code.alert"
	 * @param code 메세지 프로퍼티에 등록 되어 있는 프로퍼티명
	 * @param locale language를 설정한 Locale
	 * @return
	 */
	public static String getMessage(String code,Locale locale) {
		return getMessage(code, null, locale);
	}
	

	/**
	 * 메세지 code 를 입력 <br><br>
	 * 
	 * ex)	<br> 
	 * message.code.alert=hey! {0}. nice to meet you. {1} 	<br>
	 * Message.getMessage("message.code.alert","minsu","bye!")	<br><br>
	 * 
	 * print = hey! minsu. nice to meet you. bye!<br><br>
	 * 
	 * @param code 메세지 프로퍼티에 등록 되어 있는 프로퍼티명
	 * @param values 메세지 변수를 치환 할 값
	 * @return
	 */
	public static String getMessage(String code,Object ...values) {
		return getMessage(code, null, values);
	}
	
	public static String getMessage(String code,String defaultMessage) {
		return getMessage(code,defaultMessage,null);
	}
	
	/**
	 * 메세지 code 를 입력 <br><br>
	 * 
	 * ex)	<br> 
	 * message.code.alert=hey! {0}. nice to meet you. {1} 	<br>
	 * Message.getMessage("message.code.alert","hello!!!! see you tomorrow","minsu","bye!")	<br><br>
	 * 
	 * message.code.alert= 
	 * 위 처럼 값이 지정되어 있지 않을 경우 아래 처럼 대체해서 나온다
	 * 
	 * print = hello!!!! see you tomorrow<br><br>
	 * 
	 * @param code 메세지 프로퍼티에 등록 되어 있는 프로퍼티명
	 * @param defaultMessage 프로퍼티값이 없을 경우 기본 메세지
	 * @param values 메세지 변수를 치환 할 값
	 * @return
	 */
	public static String getMessage(String code,String defaultMessage,Object ...values) {
		return getMessage(code,defaultMessage,Locale.getDefault(), values);
	}
	
	/**
	 * 메세지 code 를 입력 <br><br>
	 * 
	 * ex)	<br> 
	 * message.code.alert=hey! {0}. nice to meet you. {1} 	<br>
	 * Message.getMessage("message.code.alert","hello!!!! see you tomorrow",new Locale("ko"),"minsu","bye!")	<br><br>
	 * 
	 * @param code 메세지 프로퍼티에 등록 되어 있는 프로퍼티명
	 * @param defaultMessage 프로퍼티값이 없을 경우 기본 메세지
	 * @param locale language를 설정한 Locale
	 * @param values 메세지 변수를 치환 할 값
	 * 
	 * @return
	 */
	public static String getMessage(String code,String defaultMessage,Locale locale,Object ...values) {
		
		Object[] o = null;
		
		if(values != null) {
			o = ArrayUtils.toArray(values);
		}

        return messageSource.getMessage(code, o , defaultMessage, locale.getDefault());
	}
}
