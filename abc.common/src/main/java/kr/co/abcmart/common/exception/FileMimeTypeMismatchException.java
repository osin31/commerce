package kr.co.abcmart.common.exception;

public class FileMimeTypeMismatchException extends RuntimeException {
	
	public FileMimeTypeMismatchException() {
		super();
	}
	
	public FileMimeTypeMismatchException(String message) {
		super(message);
	}
}
