package kr.co.abcmart.bo.claim.vo;

import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OcClaimCountVO extends BaseBean {

	// 클레임구분코드 교환
	private String clmGbnCodeExchange;
	// 클레임구분코드 반품
	private String clmGbnCodeReturn;

	// 상품타입코드 배송비
	private String prdtTypeCodeGift;
	// 상품타입코드 사은품
	private String prdtTypeCodeDelivery;

	// 클레임상품상태코드 교환불가
	private String clmPrdtStatCodeExchangeImpossible;
	// 클레임상품상태코드 반품불가
	private String clmPrdtStatCodeReturnImpossible;

	/**
	 * 자사
	 */
	// 반품접수/진행중 건수
	private java.lang.Integer clmExchangeCnt;

	public java.lang.Integer getClmExchangeCnt() {
		if (clmExchangeCnt == null) {
			return 0;
		}
		return clmExchangeCnt;
	}

	// 교환접수/진행중 건수
	private java.lang.Integer clmReturnCnt;

	public java.lang.Integer getClmReturnCnt() {
		if (clmReturnCnt == null) {
			return 0;
		}
		return clmReturnCnt;
	}

	// AS접수/진행중 건수
	private java.lang.Integer asCnt;

	public java.lang.Integer getAsCnt() {
		if (asCnt == null) {
			return 0;
		}
		return asCnt;
	}

	/**
	 * 업체
	 */
	// 반품접수/진행중 건수
	private java.lang.Integer vndrClmExchangeCnt;

	public java.lang.Integer getVndrClmExchangeCnt() {
		if (vndrClmExchangeCnt == null) {
			return 0;
		}
		return vndrClmExchangeCnt;
	}

	// 교환접수/진행중 건수
	private java.lang.Integer vndrClmReturnCnt;

	public java.lang.Integer getVndrClmReturnCnt() {
		if (vndrClmReturnCnt == null) {
			return 0;
		}
		return vndrClmReturnCnt;
	}

	// AS접수/진행중 건수
	private java.lang.Integer vndrAsCnt;

	public java.lang.Integer getVndrAsCnt() {
		if (vndrAsCnt == null) {
			return 0;
		}
		return vndrAsCnt;
	}

	// 판매취소요청 건수
	private java.lang.Integer cancelSellRequestCnt;

	public java.lang.Integer getCancelSellRequestCnt() {
		if (cancelSellRequestCnt == null) {
			return 0;
		}
		return cancelSellRequestCnt;
	}

	// 반품불가요청 건수
	private java.lang.Integer rejectReturnRequestCnt;
	// 교환불가요청 건수
	private java.lang.Integer rejectExchangeRequestCnt;

	// 교환 클레임 제외 상태코드 배열
	private String[] notInExchangeClmStatCodes;
	// 반품 클레임 제외 상태코드 배열
	private String[] notInReturnClmStatCodes;
}
