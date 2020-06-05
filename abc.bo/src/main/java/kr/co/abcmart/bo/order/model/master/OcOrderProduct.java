package kr.co.abcmart.bo.order.model.master;

import kr.co.abcmart.bo.order.model.master.base.BaseOcOrderProduct;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString(callSuper = true)
public class OcOrderProduct extends BaseOcOrderProduct {

	/**************************************************************************
	 * 주문 상세 상품 grid
	 **************************************************************************/
	// 배송 영역
	private java.lang.Short orderDlvyHistSeq; // 주문배송이력순번
	private java.sql.Timestamp dlvyProcDtm; // 배송처리일시
	private String logisVndrCode; // 택배사코드
	private String logisVndrCodeName; // 택배사코드명
	private String waybilNoText; // 운송장번호

	private String dlvyDscntcYn; // 배송중단여부
	private String dlvyDscntcRsnCode; // 배송중단사유코드
	private String dlvyDscntcOpetrNo; // 배송중단처리자번호
	private java.sql.Timestamp dlvyDscntcProcDtm; // 배송중단처리일시

	private java.sql.Timestamp pickupPrpareCmlptDtm; // 픽업준비완료일시
	private java.sql.Timestamp pickupPsbltYmd; // 픽업가능일
	private String missProcYn; // 분실처리여부
	private String missProcTypeCode; // 분실처리유형코드
	private String missProcTypeCodeName;
	private String insdMgmtInfoText; // 내부관리정보
	private String dlvyStatCode; // 배송상태코드
	private String stockGbnCode; // 발송처
	private String orgStockGbnCode; // 발송처 notCombo
	private String stockGbnCodeName; // 발송처명
	private String stockGbnCbcd; // 인터페이스 B코드
	private String storeNo; // 매장번호
	private String storeName; // 매장명
	private String crtfcNoText; // 적립인증번호
	private String clmGbnCode; // 클레임유형코드
	private String salesCnclGbnType; // 매출취소구분코드
	private String giftPrdtNo; // 사은품 상품번호
	private String giftPrdtName; // 사은품 상품명
	private String vndrGbnType; // 업체구분 : C(자사), V(입점사:d)

	// t상품 유형 타입 네임
	private String prdtTypeCodeName;

	// 구매 확정 일자
	private java.sql.Timestamp buyDcsnDtm;
	// 주문 상품 추가 정보 조회
	private String orderPrdtStatCodeName; // 주문상품상태

	private String status; // grid 상태
	private String prdtTypeCodeGift; // 사은품 코드
	private String pickupPsbltYmdOverYn; // 픽업 가능일 초과 여부
	private String pickupPsbltYmdAddSeven; // 픽업 가능일 ADD 7
	private String wmsSendYn; // wms 전송여부
	/**
	 * 주문번호 배열
	 */
	private String[] orderNos;

	/**
	 * 주문상품순번 배열
	 */
	private java.lang.Integer[] orderPrdtSeqs;

	/**
	 * 원주문번호
	 */
	private String orgOrderNo;

	/**
	 * 매출취소구분
	 */
	private String[] salesCnclGbnTypes;
	/**
	 * 클레임 상태 ( 클레임 상품기준)
	 */
	private String[] clmPrdtStatCodes;

	// ===
	private String vaildationType;
	private String orderStatCode; // 주문상태코드
	private String clmNo; // 클레임 번호

	// 클레임 상태 코드
	private String clmPrdtStatCode;

	// 배송상태코드명
	private String dlvyStatCodeName;

	// 사은품 주문상품순번
	private java.lang.Integer orderGiftPrdtSeq;

	// 배송ID
	private String dlvyIdText;

	/** 사이트명 */
	private String siteNo;
	/** 사이트명 */
	private String siteName;
	/** 채널명 */
	private String chnnlName;
	/** 브랜드명 */
	private String brandName;

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

	// 상품색상코드 명
	private String prdtColorCodeName;

	// 상품 색상코드
	private String prdtColorInfo;

	private String memberNo;
	private String memberTypeCode;

	// 매장 출고시 상품 interface
	private String chasu;
	private String dropReason1;
	private String dropReason2;
	private String memo;

	// 배송 중단 처리
	private String cbcd;

	private java.lang.Integer orderTotalCnt; // 상품전체 건수
	private java.lang.Integer orderReadlyCnt; // 결제완료 , 입금대기
	private java.lang.Integer orderCancelPsltCnt; // 결제완료 , 입금대기, 상품준비중
	private java.lang.Integer orderConfirmCnt; // 배송중 , 픽업 준비완료 , 배송완료
	private java.lang.Integer orderCancelCnt; // 취소완료
	private java.lang.Integer orderReqCancelCnt; // 취소요청건 ( 취소 완료건 제외)
	private java.lang.Integer orderClaimTotalCnt; // 클레임 건수
	private java.lang.Integer vndrPrdtCnt; // 업체상품 건수
	private java.lang.Integer orderComfirmCnt; // 구매확정 건수

	private String prdtAsAcceptFlag; // 상품 as 접수 여부
	private String asAcceptNo; // 상품 as 접수 번호
	private String dlvyTypeCode; // 주문 마스터 배송타입 (교환주문건)

	private String layerType; // 옵션 레이어 타입 정보
	private String promoNo; // 주문 상품 프로모션 정보
	private String selectVal; // 옵션 선택 정보

	// NOT IN AS상품상태코드 배열
	private String[] notInAsPrdtStatCodeList;
	// NOT IN 클레임상품 상태 코드 배열
	private String[] notInClmPrdtStatCodeList;

	// 조건절 주문상품상태코드
	private String whereOrderPrdtStatCode;
}
