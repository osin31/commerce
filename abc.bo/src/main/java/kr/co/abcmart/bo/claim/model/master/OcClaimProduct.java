package kr.co.abcmart.bo.claim.model.master;

import kr.co.abcmart.bo.claim.model.master.base.BaseOcClaim;
import kr.co.abcmart.bo.claim.model.master.base.BaseOcClaimProduct;
import kr.co.abcmart.bo.order.model.master.OcOrderDeliveryHistory;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.constant.Const;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OcClaimProduct extends BaseOcClaimProduct {

	private static final long serialVersionUID = -6432140899867385794L;

	// 엑셀 용 sort 정보
	private String sortColumn;
	private String sortType;

	// 결제상태코드 : 결제완료
	private String pymntStatCodePaymentFinish;

	private String includeDlvyPrdtYn = "N"; // 클레임상품 조회 시 교환대상상품 포함여부(임의로 set 하기 이전에는 디폴트 제외)

	// 클레임구분코드 취소
	private String clmGbnCodeCancel;
	// 클레임구분코드 교환
	private String clmGbnCodeExchange;
	// 클레임구분코드 반품
	private String clmGbnCodeReturn;

	// 클레임 상품 상태코드 조건
	private String clmPrdtStatCodeCondition;

	private java.lang.Integer cancelPrdtAmt;

	private java.lang.Integer cancelDlvyAmt;

	private java.lang.Integer clmRsnCodeCnt;

	// 사은품명
	private String giftPrdtName;

	private String siteNo;

	private String deviceCode;

	// 재고 구분 코드 -- 발송처 ( 검색 )
	@ParameterOption(target = "stockGbnCode")
	private OcOrderDeliveryHistory[] stockGbnCodeList;

	private String buyerName;

	private String rcvrName;

	private String buyerHdphnNoText;

	private String loginId;

	private char dateGbnType;

	private String fromDate;

	private String toDate;

	private String unProcYn;

	// 클레임환불 -- 결제수단 ( 검색 )
	@ParameterOption(target = "pymntMeansCode")
	private OcClaimPayment[] pymntMeansCodeList;

	private String chkPaymentESCR;

	private String chkPaymentNoESCR;

	// 클레임 상태 코드 배열
	@ParameterOption(target = "clmStatCode")
	private BaseOcClaim[] clmStatCodeList;

	private String oflnAcceptYn;

	private String[] orderNos;
	private Integer[] orderPrdtSeqs;
	private String[] clmPrdtStatCodes;

	private String clmGbnCode;

	// 처리구분(로직 처리와 관련한 분기용)
	private String clmProcDiv;

	// 클레임 불가 사유 텍스트
	private String clmImpsbltRsnText;

	// 브랜드명 + 상품정보
	private String displayPrdt;

	// 클레임상품상태코드 명
	private String clmPrdtStatCodeName;

	// 브랜드명
	private String brandName;

	private String stockGbnCode; // 발송처(재고구분)코드

	private String stockGbnCodeName; // 발송처(재고구분)코드명

	private String dlvyStatCode; // 배송상태코드

	private String dlvyIdText; // 배송ID

	private String dlvyTypeCode; // 배송유형코드

	private String dlvyStatCodeName; // 배송상태코드명

	private java.lang.Integer orderGiftPrdtSeq; // 사은품 주문상품순번

	private String prdtTypeCodeGift; // 사은품 코드

	private String prdtTypeCodeDelivery; // 상품타입코드 배송

	private String deliveryWaybilNoText; // 운송장번호(배송이력쪽 구분용)

	private String logisVndrCodeName; // 택배사 명

	private String promoTypeCode; // 프로모션유형코드

	private String clmPrdtStatCodesBefor; // 이전클레임 상품상태

	// 클레임사유코드 명
	private String clmRsnCodeName;

	// 환수배송비 판단여부의 클레임사유 addInfo2
	private String clmRsnCodeAddInfo2;

	// 상품타입코드 배열
	private String[] prdtTypeCodeList;

	/**
	 * 상품관련파일 칼럼들
	 */
	// 상품관련파일순번
	private java.lang.Integer prdtRltnFileSeq;

	// 파일유형
	private String fileType;

	// 이미지명
	private String imageName;

	// 이미지경로
	private String imagePathText;

	// 이미지 URL
	private String imageUrl;

	// 대체 텍스트
	private String altrnText;

	// 구매포인트적립율
	private java.lang.Short buyPointSaveRate;

	// 교환 상품옵션번호
	private String changePrdtOptnNo;

	// 교환 옵션명
	private String changeOptnName;

	// ERP 매장코드
	private String insdMgmtInfoText;

	// 매장명
	private String storeName;

	// 환불대상 주문배송비
	private java.lang.Integer refundDlvyAmt;
	
	/*****************************************
	 * 마스킹 처리 권한별 혹은 전체 구분자
	 *****************************************/

	// Y:전체 마스킹 / N:권한별 마스킹 (defalt : N)
	private String isListYn = Const.BOOLEAN_FALSE;

	/*****************************************
	 * 마스킹 처리 get 메서드
	 *****************************************/

	/*****************************************
	 * 마스킹 처리 회피용 검색 변수들 search****
	 *****************************************/

	private String searchBuyerName;

	private String searchRcvrName;

	private String searchBuyerHdphnNoText;

	private String searchLoginId;

	// 재배송상품 클레임상품순번
	private java.lang.Integer changeClmPrdtSeq;

	// 재배송상품 택배사코드
	private String changeLogisVndrCode;

	// 재배송상품 택배사코드명
	private String changeLogisVndrCodeName;

	// 재배송상품 택배사코드 addInfo
	private String changeLogisVndrCodeAddInfo;

	// 재배송상품 운송장번호
	private String changeWaybilNoText;

	// 재배송상품 배송중단여부
	private String changeDlvyDscntcYn;
	
	// 재배송상품 배송중단사유코드
	private String changeDlvyDscntcRsnCode;

	// 재배송상품 배송상태
	private String changeDlvyStatCodeName;

	// 재배송상품 상태 코드
	private String changeClmPrdtStatCode;

	// 업체별 주문 배송비
	private java.lang.Integer vndrOrderDlvyAmt;
	
	// 비회원 AS반풉접수 여부
	private String asReturnYn;
	
	private String orderPrdtStatCode;
}
