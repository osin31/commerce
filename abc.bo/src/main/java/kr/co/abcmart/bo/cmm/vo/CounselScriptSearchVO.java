package kr.co.abcmart.bo.cmm.vo;

import java.io.Serializable;

import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.validation.Validator;
import lombok.Data;

@Data
public class CounselScriptSearchVO implements Serializable, Validator {

	private static final long serialVersionUID = 1L;

	// 상담메뉴 코드
	private String cnslGbnCode;
	// 상담구부 코드
	private String cnslTypeCode;
	// 상담유형 코드
	private String cnslTypeDtlCode;
	// 사용 여부
	private String useYn;
	// 검색어 유형
	private String searchKey;
	// 검색어
	private String searchValue;
	// 목록 개수
	private int pageCount;

	@Override
	public void validate() throws ValidatorException {
	}

}
