package kr.co.abcmart.common.exception;

public class FileExtentionMismatchException extends RuntimeException {
	
	public FileExtentionMismatchException() {
		super();
	}
	
	public FileExtentionMismatchException(String message) {
		super(message);
	}
}
