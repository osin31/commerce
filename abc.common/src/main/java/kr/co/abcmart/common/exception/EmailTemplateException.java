package kr.co.abcmart.common.exception;

public class EmailTemplateException extends RuntimeException {
	
	public EmailTemplateException() {
		super();
	}
	
	public EmailTemplateException(Throwable throwable) {
		super(throwable);
	}
	
	public EmailTemplateException(String message) {
		super(message);
	}
}
