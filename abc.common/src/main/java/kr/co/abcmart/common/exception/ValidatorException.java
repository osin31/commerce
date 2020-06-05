package kr.co.abcmart.common.exception;

import kr.co.abcmart.common.message.Message;

public class ValidatorException extends RuntimeException {

	// 에러가 발생한 필드 명. client 에서 처리할 경우에 사용한다.
	private String fieldName;

	public ValidatorException() {
		super();
	}

	public ValidatorException(Throwable throwable) {
		super(throwable);
	}

	/***
	 * 검증 메세지는 message.property 의 code값을 입력 한다. a.b.v=메세지 new
	 * ValidatorException("a.b.c");
	 * 
	 * @param code 메세지 프로퍼티 값.
	 */
	public ValidatorException(String code) {
		super(Message.getMessage(code));
	}

	/***
	 * 검증 메세지는 message.property 의 code값을 입력 하고 클라이언트에서 focus처리 할 수 있게 fieldName값을
	 * 설정한다. a.b.v=메세지 new ValidatorException("a.b.c","fieldName");
	 * 
	 * @param code 메세지 프로퍼티 값.
	 */
	public ValidatorException(String code, String fieldName) {
		super(Message.getMessage(code));
		this.fieldName = fieldName;
	}

	/***
	 * 메세지 코드 값을 이용하지 않고 단순 문자열로 에레 메세지를 넘길 경우. isNotCode 값을 true로 지정한다. 그렇지 않으면 메세지
	 * 프로퍼티 값으로 인식한다. new ValidatorException("일반 메세지 입니다.",true);
	 * 
	 * @param message   일반 문자열 메세지
	 * @param isNotCode 일반 문자열 메세지로 보일지 메세지 프로퍼티에 있는 코드값으로 출력 할지 결정. true : 일반 메세지,
	 *                  false = 메세지 프로퍼티 값.
	 */
	public ValidatorException(String message, boolean isNotCode) {
		super((isNotCode) ? message : Message.getMessage(message));
	}

	public String getFieldName() {
		return fieldName;
	}

}
