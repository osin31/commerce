package kr.co.abcmart.common.validation;

import java.util.Locale;

import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.message.Message;

public interface Validator {

	/***
	 * Validator를 구현 받은 클래스 필드를 검증 한다. ValidatorException을 예외 처리 한다.
	 * 
	 * @throws ValidatorException
	 */
	public void validate() throws ValidatorException;

	/***
	 * Validator를 구현 받은 클래스 필드를 검증 한다. ValidatorException이 아니고 boolean타입으로 결정한다.
	 * 
	 * @return boolean
	 */
	default boolean isValidation() {

		try {
			validate();
		} catch (Exception e) {
			return false;
		}

		return true;
	};

	default void validationMessage(String code) throws ValidatorException {
		throw new ValidatorException(code);
	};

	default void validationFieldMessage(String code, String fieldName) throws ValidatorException {
		throw new ValidatorException(code, fieldName);
	};

	default void validationFieldMessage(String code, String fieldName, Object... values) throws ValidatorException {
		throw new ValidatorException(Message.getMessage(code, values), fieldName);
	};

	default void validationMessage(String code, String defaultMessage) throws ValidatorException {
		validationMessage(code, defaultMessage, Locale.getDefault());
	}

	default void validationMessage(String code, Object... values) throws ValidatorException {
		throw new ValidatorException(Message.getMessage(code, values), true);
	}

	default void validationMessage(String code, String defaultMessage, Object... values) throws ValidatorException {
		validationMessage(code, defaultMessage, Locale.getDefault(), values);
	}

	default void validationMessage(String code, String defaultMessage, Locale locale, Object... values)
			throws ValidatorException {
		throw new ValidatorException(Message.getMessage(code, defaultMessage, locale, values), true);
	}

	default void validationMessage(String message, boolean isNotCode) throws ValidatorException {
		throw new ValidatorException((isNotCode) ? message : Message.getMessage(message));
	};

}
