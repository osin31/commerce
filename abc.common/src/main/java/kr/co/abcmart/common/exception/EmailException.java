package kr.co.abcmart.common.exception;

public class EmailException extends RuntimeException {
	
	public EmailException() {
		super();
	}
	
	public EmailException(Throwable throwable) {
		super(throwable);
	}
	
	public EmailException(String message) {
		super(message);
	}
}
