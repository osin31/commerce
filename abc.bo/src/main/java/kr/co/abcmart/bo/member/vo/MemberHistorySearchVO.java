/**
 * 
 */
package kr.co.abcmart.bo.member.vo;

import java.io.Serializable;

import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import lombok.Data;

/**
 * @Desc : 회원이력조회 검색VO. 로그인이력, 변경이력, 본인인증이력을 같이 사용한다.
 * @FileName : MemberHistorySearchVO.java
 * @Project : abc.bo
 * @Date : 2019. 2. 14.
 * @Author : Kimyounghyun
 */

@Data
public class MemberHistorySearchVO implements Serializable {

	private static final long serialVersionUID = 5074090283126463257L;

	// 회원번호
	private String memberNo;
	// 로그인이력 : 디바이스 코드 전체
	private String deviceCodeAll;
	// 로그인이력 : 디바이스 코드
	private String[] deviceCode;
	// 로그인이력 : 회원구분
	private String memberType;
	// 로그인이력 : 로그인결과 코드
	private String cnnctrStatCode;
	// 로그인이력 : 임직원 여부
	private String empYn;

	// 변경이력 : changeGroup
	private String changeGroup;
	// 변경이력 : changeField
	private String changeField;

	// 본인인증이력 : 인증유형(수단)
	private String crtfcTypeCode;
	// 본인인증이력 : 인증경로
	private String crtfcPathCode;
	// 본인인증이력 : 인증결과
	private String crtfcSuccessYn;

	// 검색 시작일
	private String fromDateStr;
	// 검색 종료일
	private String toDateStr;

	public String[] getChangeFieldArray() {
		String[] changeFieldArray = null;
		if (UtilsText.isNotBlank(changeField)) {
			changeFieldArray = changeField.split(",");
		}
		return changeFieldArray;
	}

	public java.sql.Date getFromDate() {
		if (!UtilsText.isBlank(getFromDateStr())) {
			java.util.Date fromDate = UtilsDate.parseDate(getFromDateStr(), Const.DEFAULT_DATE_PATTERN);

			return new java.sql.Date(fromDate.getTime());
		}

		return null;
	}

	public java.sql.Date getToDate() {
		if (!UtilsText.isBlank(getToDateStr())) {
			java.util.Date toDate = UtilsDate.parseDate(getToDateStr(), Const.DEFAULT_DATE_PATTERN);
			toDate = UtilsDate.addDays(toDate, 1);

			return new java.sql.Date(toDate.getTime());
		}

		return null;
	}
}
