package kr.co.abcmart.bo.board.vo;

import java.io.Serializable;

import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.validation.Validator;
import lombok.Data;

@Data
public class BdInquirySearchVO implements Serializable, Validator {

	/**
	 * 검색 기간 유형
	 */
	private String searchDateKey;

	/**
	 * 검색 시작일
	 */
	private String fromDate;

	/**
	 * 검색 종료일
	 */
	private String toDate;

	/**
	 * 답변 상태코드
	 */
	private String searchAswrStatCode;

	/**
	 * 문의유형 코드
	 */
	private String searchCnslTypeCode;

	/**
	 * 문의분류 코드
	 */
	private String searchCnslTypeDtlCode;

	/**
	 * 담당업체
	 */
	private String searchChageVndr;

	/**
	 * 담당 업체 직접 조회
	 */
	private String searchChageVndrValue;

	/**
	 * 검색어 유형
	 */
	private String searchKey;

	/**
	 * 검색어
	 */
	private String searchValue;

	/**
	 * 목록 개수
	 */
	private int pageCount;

	/**
	 * 상담구분코드
	 */
	private String cnslGbnCode;

	/**
	 * 회원번호
	 */
	private String memberNo;

	/**
	 * 사이트번호
	 */
	private String siteNo;

	/**
	 * 제목
	 */
	private String inqryTitleText;

	/**
	 * 업체지정여부
	 */
	private String vndrAssignYn;

	/**
	 * 업체지정자번호
	 */
	private String vndrAsnrNo;

	/**
	 * 업체번호
	 */
	private String vndrNo;

	/**
	 * 업체지정일시
	 */
	private String vndrAssignDtm;
	
	/**
	 * 휴대폰번호 뒷자리 
	 */
	private String hdphnBackNoText;

	@Override
	public void validate() throws ValidatorException {

	}
}
