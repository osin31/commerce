/**
 * 
 */
package kr.co.abcmart.bo.order.vo;

import kr.co.abcmart.bo.member.model.master.base.BaseMbMemberExpostSavePoint;
import kr.co.abcmart.common.util.UtilsMasking;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 포인트 사후적립 목록
 * @FileName : OcOrderMemberExpostSave.java
 * @Project : abc.bo
 * @Date : 2019. 3. 4.
 * @Author : ljyoung@3top.co.kr
 */
@Slf4j
@Data
public class OcOrderMemberExpostSaveVO extends BaseMbMemberExpostSavePoint {

	/**
	 * 설명 : 주문번호
	 */
	private String orderNo;

	/**
	 * 설명 : 적립금액
	 */
	private java.lang.Integer saveAmt;

	/**
	 * 설명 : 등록자번호
	 */
	private String rgsterNo;

	/**
	 * 설명 : 사이트번호
	 */
	private String siteNo;

	/**
	 * 설명 : 회원유형코드
	 */
	private String memberTypeCode;

	/**
	 * 설명 : 주문 회원 유형
	 */
	private String memberTypeCodeName;

	/**
	 * 설명 : 주문자명
	 */
	private String buyerName;

	/**
	 * 설명 : 주문일시
	 */
	private java.sql.Timestamp orderDtm;

	/**
	 * 설명 : 적립일시
	 */
	private java.sql.Timestamp saveDtm;

	/**
	 * 설명 : 적립대상 로그인ID
	 */
	private String saveLoginId;

	/**
	 * 설명 : 적립대상 회원명
	 */
	private String saveMemberName;

	/**
	 * 설명 : 처리 담당 관리자 NO
	 */
	private String adminNo;

	/**
	 * 설명 : 처리 담당 관리자 로그인ID
	 */
	private String adminLoginId;

	/**
	 * 설명 : 처리 담당 관리자 이름
	 */
	private String adminName;

	/**
	 * 설명 : 처리자 MEMBER NO
	 */
	private String rgsterMemberNo;

	/**
	 * 설명 : 처리자 로그인ID
	 */
	private String rgsterLoginId;

	/**
	 * 설명 : 처리자 이름
	 */
	private String rgsterName;

	/**
	 * 설명 : 주문상품정보
	 */
	private String orderPrdtInfo;

	/**
	 * 주문관련 검색 key
	 */
	private String searchKey;

	/**
	 * 주문관련 검색 text, key에 따른 validation check
	 */
	private String searchText;

	/**
	 * 검색기간 from
	 */
	private String fromDate;

	/**
	 * 검색기간 to
	 */
	private String toDate;

	public String getBuyerName() {
		return UtilsMasking.userName(this.buyerName);
	}

	public String getSaveMemberName() {
		return UtilsText.concat(UtilsMasking.loginId(this.saveLoginId), Const.L_PAREN,
				UtilsMasking.userName(this.saveMemberName), Const.R_PAREN);
	}

	public String getAdminName() {
		return UtilsText.concat(UtilsMasking.loginId(this.adminLoginId), Const.L_PAREN,
				UtilsMasking.userName(this.adminName), Const.R_PAREN);
	}

	public String getRgsterName() {
		return UtilsText.concat(UtilsMasking.loginId(this.rgsterLoginId), Const.L_PAREN,
				UtilsMasking.userName(this.rgsterName), Const.R_PAREN);
	}
}
