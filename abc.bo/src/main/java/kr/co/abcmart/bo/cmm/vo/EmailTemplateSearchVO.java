package kr.co.abcmart.bo.cmm.vo;

import java.io.Serializable;

import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import lombok.Data;

@Data
public class EmailTemplateSearchVO implements Serializable, Validator {

	private static final long serialVersionUID = 1L;

	/**
	 * 이메일제목
	 */
	private String emailTitleText;

	/**
	 * 이메일유형코드
	 */
	private String emailTypeCode;

	/**
	 * 메일템플릿명
	 */
	private String emailTmplName;

	/**
	 * 이메일키
	 */
	private String emailKeyText;

	/**
	 * 기간검색 조건
	 */
	private String searchDateKey;

	/**
	 * 기간검색 시작일
	 */
	private String fromDate;

	/**
	 * 기간검색 종료일
	 */
	private String toDate;

	/**
	 * 사이트 번호
	 */
	private String siteNo;

	/**
	 * 페이지 카운트
	 */
	private int pageCount;

	/*
	 * 이메일 템플릿 순번
	 */
	private int emailTmplSeq;

	/**
	 * 수정자 Login ID
	 */
	private String adminLoginId;

	/**
	 * 이메일 템플릿 여부 Y: 이메일 템플릿, N: Mail Key 생성
	 */
	private String emailTmplYn;

	/**
	 * 이메일 발송 처리 구분 : A :자동, M : 수동
	 */
	private String sendProcGbnType;

	/**
	 * 이메일 ID
	 */
	private String emailId;

	/**
	 * @Desc : 검색 시작일 Data Type를 Java.sql.Timestamp로 변경 한다.
	 * @Method Name : getFromDtm
	 * @Date : 2019. 3. 13.
	 * @Author : kiowa
	 * @return
	 */
	public java.sql.Timestamp getFromDtm() {
		return UtilsDate.getSqlTimeStamp(UtilsDate.parseDate(fromDate, Const.DEFAULT_DATE_PATTERN));
	}

	/**
	 * @Desc : 검색 종료일 Data Type를 Java.sql.Timestamp로 변경 한다.
	 * @Method Name : getToDtm
	 * @Date : 2019. 3. 13.
	 * @Author : kiowa
	 * @return
	 */
	public java.sql.Timestamp getToDtm() {
		return UtilsDate.getSqlTimeStamp(UtilsDate.addDays(UtilsDate.parseDate(toDate, Const.DEFAULT_DATE_PATTERN), 1));
	}

	@Override
	public void validate() throws ValidatorException {

	}

}
