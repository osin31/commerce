package kr.co.abcmart.bo.order.model.master;

import kr.co.abcmart.bo.order.model.master.base.BaseOcOrderPayment;
import kr.co.abcmart.common.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OcOrderPayment extends BaseOcOrderPayment {

	private String deviceCodeName;

	private String pymntMeansCodeName;

	private String pymntVndrCodeName;

	private String pymntStatCodeName;

	private String pymntVndrCodeNameGroup;

	private String[] pymntMeansCodes;

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
	 * 수령자명
	 */
	private String rcvrName;

	/**
	 * 구매자 로그인Id
	 */
	private String loginId;

	/**
	 * 구매자 핸드폰번호
	 */
	private String buyerHdphnNoText;

	/**
	 * 설명 : 주문일시
	 */
	private java.sql.Timestamp orderDtm;

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

	/**
	 * 전체결제수단 검색
	 */
	private String chkPaymentAll;

	/**
	 * 검색에 사용할 결제수단
	 */
	private String[] chkPaymentModule;

	/**
	 * 검색에서 에스크로 사용여부 사용
	 */
	private String chkEscroTrue;

	/**
	 * 검색에서 에스크로 사용여부 미사용
	 */
	private String chkEscroFalse;

	/**
	 * 주문번호 배열
	 */
	private String[] orderNos;

	public String getBuyerName() {
		return UtilsMasking.userName(this.buyerName);
	}

	// 카드/실시간포인트 제외한 결제수단이 있는경우
	private int pymntMeansCnt;

	// 결제수단 변경 COUNT
	private int changeCnt;

	// 업체 주문 상품건수
	private int vendorPrdtCnt;

	// 클레임 접수 건수
	private int claimCnt;

	// gift 카드 현금영수증 발급 번호
	private String rcptDealNoText;

}
