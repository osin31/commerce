package kr.co.abcmart.bo.display.vo;

import java.io.Serializable;

import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.validation.Validator;
import lombok.Data;

@Data
public class HashTagSearchVO implements Serializable, Validator {

	private static final long serialVersionUID = -2796426322101562602L;

	// 사이트 번호
	private String siteNo;

	// 사용 여부
	private String useYn;

	// 해쉬태그명
	private String hshtgName;

	// 사용기간 검색 조건
	private String searchDateKey;

	// 검색 시작일
	private String fromDate;

	// 검색 종료일
	private String toDate;

	// 해쉬태그 순번
	private java.lang.Integer hshtgSeq;

	@Override
	public void validate() throws ValidatorException {
	}
}
