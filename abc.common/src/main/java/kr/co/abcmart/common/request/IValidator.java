package kr.co.abcmart.common.request;

import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.validation.Validator;

public interface IValidator {
	
	public void validate(Validator validator) throws ValidatorException;
	
	public void validate(Validator[] validators) throws ValidatorException;

	public void validate() throws ValidatorException;
	
	/***
	 * Validator를 구현 받은 클래스 필드를 검증 한다. ValidatorException이 아니고 boolean타입으로 결정한다.
	 * @param validators Validator를  구현한 클래스. 배열형식으로 전달 받는다.
	 * @return boolean
	 */
	default boolean isValidation(Validator[] validators) {

		try {
			if(validators != null) {
				for (Validator validator : validators) {
					
					validator.validate();
				}				
			}
			
		} catch (Exception e) {
			return false;
		}
		
		return true;
	};
	
	/***
	 * Validator를 구현 받은 클래스 필드를 검증 한다. ValidatorException이 아니고 boolean타입으로 결정한다.
	 * @param validator Validato를  구현한 클래스.
	 * @return boolean
	 */
	default boolean isValidation(Validator validator) {

		try {
			if( validator != null ) {
				validator.validate();
			}
		} catch (Exception e) {
			return false;
		}
		
		return true;
	};
	
	/***
	 * Validator를 구현 받은 클래스 필드를 검증 한다. ValidatorException이 아니고 boolean타입으로 결정한다.
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

}
