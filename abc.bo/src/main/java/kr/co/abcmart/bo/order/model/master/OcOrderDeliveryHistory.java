package kr.co.abcmart.bo.order.model.master;

import kr.co.abcmart.bo.order.model.master.base.BaseOcOrderDeliveryHistory;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString(callSuper = true)
public class OcOrderDeliveryHistory extends BaseOcOrderDeliveryHistory {

	private String stockGbnCodeName;

	private String logisVndrCodeName;

	private String dlvyDscntcRsnCodeName;

	private String missProcTypeCodeName;

	private String dlvyStatCodeName;

	/**
	 * 배송상태 - 변경전
	 */
	private String dlvyStatCodeBefore;

	/**
	 * 상품 배송 상태
	 */
	private String orderPrdtStatCode;

	/**
	 * 배송상태 - 변경 후
	 */
	private String dlvyStatCode;

	/**
	 * 주문번호 배열
	 */
	private String[] orderNos;

	/**
	 * 로그인ID
	 */
	private String loginId;

	/**
	 * 관리자 명
	 */
	private String adminName;

	/**
	 * 배송아이디 배열
	 */
	private String[] dlvyIdTextArr = null;

	/**
	 * 업체 상품 코드
	 */
	private String vndrPrdtNoText;

	/**
	 * 상품 코드
	 */
	private String prdtNo;

	/**
	 * 설명 : 조건절 배송상태코드
	 */
	private String whereDlvyStatCode;

	/**
	 * 재배송 처리시 원 재고구분 코드
	 */
	private String orgStockGbnCode;

	private String cbcd;

	/**
	 * 상품 옵션 코드
	 */
	private String prdtOptnNo;
	/**
	 * 상품 타입
	 */
	private String prdtTypeCode;

	/**
	 * 상위주문상품순번
	 */
	private java.lang.Integer upOrderPrdtSeq;

	/**
	 * 매장명
	 */
	private String storeName;

	// 상태
	private String status;

	// 체크박스 체크 여부
	private java.lang.Integer checkedRows;
	
	//처리구분 코드
	private String procGubnCode; 

}
