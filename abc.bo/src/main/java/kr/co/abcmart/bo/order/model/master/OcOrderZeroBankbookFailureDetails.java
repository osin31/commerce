package kr.co.abcmart.bo.order.model.master;

import kr.co.abcmart.bo.order.model.master.base.BaseOcOrderZeroBankbookFailureDetails;
import kr.co.abcmart.common.util.UtilsMasking;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString(callSuper = true)
public class OcOrderZeroBankbookFailureDetails extends BaseOcOrderZeroBankbookFailureDetails {

	/**
	 * 사이트
	 */
	private String siteNo;

	/**
	 * 설명 : 회원번호
	 */
	private String memberNo;

	/**
	 * 구매자명
	 */
	private String buyerName;

	/**
	 * 구매자명
	 */
	private String buyerId;

	/**
	 * 설명 : 주문일시
	 */
	private java.sql.Timestamp orderDtm;

	/**
	 * 설명 : 주문상품정보
	 */
	private String orderPrdtInfo;

	/**
	 * 처리자명
	 */
	private String moderName;

	/**
	 * 처리자 ID
	 */
	private String moderId;

	/**
	 * 환불계좌계좌 유무
	 */
	private String searchRefundAccount;

	/**
	 * 처리 일시 업데이트 여부.
	 */
	private boolean isUpdateProcDate;

	/**
	 * 주문관련 검색 key
	 */
	private String searchKey;

	/**
	 * 주문관련 검색 text, key에 따른 validation check
	 */
	private String searchText;

	/**
	 * 검색기간 기준 date
	 */
	private String searchDateType;

	/**
	 * 검색기간 from
	 */
	private String fromDate;

	/**
	 * 검색기간 to
	 */
	private String toDate;

	public String getBuyerName() {
		return UtilsMasking.userName(this.buyerId) + "(" + UtilsMasking.userName(this.buyerName) + ")";
	}

	public String getModerName() {
		if (this.moderId != null && this.moderName != null)
			return UtilsMasking.userName(this.moderId) + "(" + UtilsMasking.userName(this.moderName) + ")";
		else
			return "";
	}
}
