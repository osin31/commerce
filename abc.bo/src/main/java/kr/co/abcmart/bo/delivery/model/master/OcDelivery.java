package kr.co.abcmart.bo.delivery.model.master;

import java.util.List;

import kr.co.abcmart.bo.order.model.master.base.BaseOcOrder;
import kr.co.abcmart.common.util.UtilsMasking;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString(callSuper = true)
public class OcDelivery extends BaseOcOrder {

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 주문상품순번
	 */
	private java.lang.Integer orderPrdtSeq;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 상품번호
	 */
	private String prdtNo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 상품옵션번호
	 */
	private String prdtOptnNo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 상품명
	 */
	private String prdtName;

	private String engPrdtName;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 옵션명
	 */
	private String optnName;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 스타일정보
	 */
	private String styleInfo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 상품색상코드
	 */
	private String prdtColorCode;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 업체번호
	 */
	private String vndrNo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 업체명
	 */
	private String vndrName;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 브랜드번호
	 */
	private String brandNo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 표준카테고리번호
	 */
	private String stdCtgrNo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 카테고리번호
	 */
	private String ctgrNo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 자사상품여부
	 */
	private String mmnyPrdtYn;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 임직원할인여부
	 */
	private String empDscntYn;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 주문제작여부
	 */
	private String orderMnfctYn;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 감가제외여부
	 */
	private String dprcExceptYn;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 무료배송여부
	 */
	private String freeDlvyYn;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 주문수량
	 */
	private java.lang.Integer orderQty;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 상품정상가
	 */
	private java.lang.Integer prdtNormalAmt;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 상품판매가
	 */
	private java.lang.Integer prdtSellAmt;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 옵션추가금액
	 */
	private java.lang.Integer optnAddAmt;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 판매가
	 */
	private java.lang.Integer sellAmt;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 임직원할인율
	 */
	private java.lang.Short empDscntRate;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 임직원가
	 */
	private java.lang.Integer empAmt;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 할인금액합계
	 */
	private java.lang.Integer totalDscntAmt;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 쿠폰할인금액
	 */
	private java.lang.Integer cpnDscntAmt;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 주문금액
	 */
	private java.lang.Integer orderAmt;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 업체수수료율
	 */
	private java.lang.Short vndrCmsnRate;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 제휴사주문번호
	 */
	private String affltsOrderNo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 제휴사주문상품번호
	 */
	private String affltsOrderPrdtNo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 판매취소요청여부
	 */
	private String sellCnclReqYn;

	private String sellCnclReqtrId;

	private String sellCnclReqtrName;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 판매취소사유
	 */
	private String sellCnclRsnText;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 주문상품상태코드
	 */
	private String orderPrdtStatCode;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 등록자번호
	 */
	private String rgsterNo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 등록일시
	 */
	private java.sql.Timestamp rgstDtm;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 수정자번호
	 */
	private String moderNo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 수정일시
	 */
	private java.sql.Timestamp modDtm;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 배송상태코드
	 */
	private String dlvyStatCode;
	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 택배사코드
	 */
	private String logisVndrCode;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 운송장번호
	 */
	private String waybilNoText;

	/* ***********************추가 Vo *********************************/
	// 배송ID
	private String dlvyIdText;

	/* 검색 파라미터 */

	// 조회기간 구분 : 주문일/배송일/배송완료일
	private String searchDateKey;

	// 조회기간 구분 Value - start date
	private String fromDate;

	// 조회기간 구분 Value - start end date
	private String toDate;

	// 조회 기본정보 구분 : 주문자,수취인,주문번호주문자ID
	private String searchOrderKey;

	// 조회 기본정보 구분 Value
	private String searchOrderValue;

	// 조회 상품 구분 : 상품명 , 상품코드, 스타일 코드
	private String searchOrderProdKey;

	// 택배사 명 - function 함수 Name
	private String logisVndrCodeNm;

	// 배송상태 - 코드명
	private String dlvyStatCodeNm;

	// 조회 상품 구분 Value
	private String searchOrderProdValue;

	private String stockGbnCode; // 발송처
	private String stockGbnCodeName;
	private String pymntMeansCode; // 결제수단

	private String dlvyDscntcYn; // dlvyDscntcYn:배송중단여부 -상품대기
	private String missProcYn; // missProcYn:분실처리여부-분실대기

	private String prdtTypeCode;
	private String adminNames;
	private String deliveryAmt;

	private String siteName;
	private String dlvyTypeName; // 배송구분
	private String stockGbnName; // 발송처
	private String dlvyDscntcRsnReason; // 배송중단사유

	private String clmRsnName; // 클레임 사유
	private String clmGbnName; // 클레임구분
	private String addDlvyAmtPymntTypeName; // 클레임배송비결제수단
	private String clmPrdtStatName; // 클레임상태

	private String clmPrdtStatCode; // 클레임상품상태코드
	private String clmPrdtStatCodeBefor; // 클레임상품상태코드
	
	private String buyerHdphnBackNo; // 주문자 휴대번호 뒷4자리
	
	// 검색조건 Arrary

	private List<String> siteNoList; // 사이트 리스트
	private List<String> dlvyTypeCodeList; // 배송유형코드 리스트
	private List<String> dlvyStatCodeList; // 배송상태 리스트
	private List<String> dlvyPrdtStatCodeList; // 주문상품상태 리스트
	private List<String> stockGbnCodeList; // 발송처 리스트
	private List<String> pymntMeansCodeList; // 결제수단 리스트
	private List<String> chanList; // 채널 리스트
	private List<Integer> orderPrdtSeqList; // 주문상품 순번 List
	private List<String> dlvyIdTextList; // 배송ID List
	private List<String> deviceCodeList; // 결제구분ID List

	private List<String> clmPrdtStatList; // 클레임 상품 상태
	private List<String> mmnyPrdtYnList; // 자사/입점 리스트

	private String[] siteNoArr = null; // 사이트
	private String[] dlvyTypeCodeArr = null; // 설명 : 배송유형코드
	private String[] dlvyStatCodeArr = null; // 배송상태
	private String[] dlvyPrdtStatCodeArr = null; // 주문상품상태
	private String[] stockGbnCodeArr = null; // 발송처
	private String[] pymntMeansCodeArr = null; // 결제수단
	private String[] deliveryEtcStatArr = null; // 배송예외구분 - 상품대기[dlvy_dscntc_yn] , 분실대기[miss_proc_yn]
	private String[] chanListArr = null; // 채널리스트
	private String[] dlvyIdTextArr = null; // 채널리스트
	private String[] deviceCodeArr = null; // 결제구분

	private String[] clmPrdtStatCArr = null; // 취소
	private String[] clmPrdtStatRArr = null; // 반품
	private String[] clmPrdtStatEArr = null; // 교환
	private String[] mmnyPrdtYnArr = null; // 자사/입점

	private Integer orderDlvOverDay; // 미출고 조회일수

	private java.sql.Timestamp dlvyProcDtm; // 발송여부(발송일)
	private java.sql.Timestamp logisCnvrtReqDtm; // 신청일시

	private String deliveryAddressYn; // 택배전환상태의 미발송 선택시 - 신청: 주소 유 , 접수:주소 무
	private String deliveryLogisSendYn; // 택배전환 배송 여부
	private String selectChangeType; // 택배전환상태의 코드
	private String logisCnvrtRsnCode; // 전환사유코드
	private String logisCnvrtYn; // 택배전환여부

	private String deliveryAddressEmptyYn; // 주소여부 존재 - 신청 여부

	private String newStockGbnCode; // 신규 출고지 지정값

	private String dlvyDscntcProc; // 처리상태

	private java.lang.Integer orderDlvyHistSeq; // 주문배송이력순번

	private java.sql.Timestamp dlvyDscntcProcDtm; // 재배송 처리일자
	private java.sql.Timestamp dlvyDscntcAcceptDtm; // 발생일
	private String dlvyDscntcOpetrNo; // 재배송 처리자

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 택배사결제상품금액
	 */
	private java.lang.Integer logisPymntPrdtAmt;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 택배사결제배송비
	 */
	private java.lang.Integer logisPymntDlvyAmt;

	private String strLogisPymntDlvyAmt;

	private String excelType; // 엑셀 다운로드 구분
	private String missProcTypeCode; // 분실처리구분
	private String missProcTypeCodeName;

	private String cpnDscntYn; // 쿠폰사용여부

	private String delaySendYn; // 발송지연안내여부

	private String waybilNoYn; // 운송장 정보 유무 여부

	private String rsvDlvyType; // 주문상품구분 A:전체 , N: 일반 , Y:예약상품

	private String deviceCode; // 결제구분 - 10000 피시, 10001 모바일, 10002 APP

	private String vndrPrdtNoText; // 업체 상품코드

	private String dlvyStatCodeBefore;
	private String orderPrdtStatCodeBefore;

	// 브랜드명
	private String brandName;

	private java.sql.Timestamp sellCnclReqDtm; // 요청일시

	private String sellCnclReqtrNo; // 요청자

	private String rsvOrderYn; // 예약주문여부

	private String sellCnclRsnCode; // 취소요청 사유

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 추가배송비결제방법
	 */
	private String addDlvyAmtPymntType;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 추가배송비
	 */
	private java.lang.Integer addDlvyAmt;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 클레임상태코드
	 */
	private String clmStatCode;
	private String clmStatCodeBefor;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 클레임번호
	 */
	private String clmNo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 클레임구분코드
	 */
	private String clmGbnCode;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 클레임사유코드
	 */
	private String clmRsnCode;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 클레임상품순번
	 */
	private java.lang.Short clmPrdtSeq;

	// 불가 접수일
	private java.sql.Timestamp impsbltProcAcceptDtm;

	/**
	 * 설명 : 클레임불가사유코드
	 */
	private String clmImpsbltRsnCode;

	private String impsbltProcYn; // 불가처리 여부

	// org 클레임구분코드
	private String orgClmGbnCode;

	// org 사이트번호
	private String orgSiteNo;

	private String buyerId;

	private String moderName;
	private String moderId;
	private String moderInfo;

	private String dlvyDscntcOpetrName;
	private String dlvyDscntcOpetrId;
	private String vndrGbnType;

	// 대쉬보드용
	private String fromDashYn;

	// 택배전환발송일시
	private java.sql.Timestamp logisCnvrtSendDtm;

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

	// 주문상품 상테 명
	private String orderPrdtStatCodeNm;

	// 이미지 URL
	private String imageUrl;

	// 디바이스코드명
	private String deviceCodeName;
	// 주문자
	private String buyerInfo;
	// str 판매가
	private String strSellAmt;
	// str 주문금액
	private String strOrderAmt;
	// 판매취소사유코드명
	private String sellCnclRsnCodeName;
	// str 판매취소요청시간
	private String strSellCnclReqDtm;
	// 판매취소요청자
	private String sellCnclReqtr;
	// str 판매취소시간
	private String strModDtm;

	// str 상품 총 할인금액
	private String strTotalDscntAmt;
	// str 상품 쿠폰할인금액
	private String strCpnDscntAmt;
	// 주문상품구분
	private String prdtTypeCodeName;
	// str 주문일
	private String strOrderDtm;
	// 주문배송상태 명
	private String dlvyStatCodeName;
	// 주문상품상태 명
	private String orderPrdtStatCodeName;
	// 택배사 명
	private String logisVndrCodeName;
	// str 배송처리일시
	private String strDlvyProcDtm;

	private String isExcel;

	// 배송중단 사유코드
	private String dlvyDscntcRsnCode;

	// 배송중단 사유코드 이름
	private String dlvyDscntcRsnCodeName;

	// 발생일 엑셀다운로드
	private String occurrenceDate;

	// 처리일시 엑셀다운로드
	private String procDate;
	
	private String chnnlNo; // 채널

	private String changeLogisVndrCode; // 교환 택배사코드
	private String changeLogisVndrCodeName; // 교환 택배사코드명
	private String changeWaybilNoText; // 교환 운송장번호
	private java.sql.Timestamp changeDlvyProcDtm; // 교환 배송처리일자
	
	private int stockQtyAi;			//대기조회 관련 옵션 재고 AI
	private int stockQtyAw;			//대기조회 관련 옵션 재고 AW
	private int stockQtyAs;			//대기조회 관련 옵션 재고 AS
	private int orderQtyAi;			//대기조회 관련 옵션 주문수량 AI
	private int orderQtyAw;			//대기조회 관련 옵션 주문수량 AW
	private int orderQtyAs;			//대기조회 관련 옵션 주문수량 AS
	private int availableQtyAi;		//대기조회 관련 옵션 주문수량 AS
	private int availableQtyAw;		//대기조회 관련 옵션 주문수량 AS
	private int availableQtyAs;		//대기조회 관련 옵션 주문수량 AS
	
	private String procGubnCode;	//대기조회 관련 처리 구분코드
	private String procGubnCodeName;//대기조회 관련 처리 구분코드이름
	private String procGubnDtm;		//대기조회 관련 처리 일시
	private String optionUseYn;		//대기조회 관련 옵션 전시여부
	private String procGubnSelect;	//대기조회 seelctBox값
 
	private String[] procGubnCodeArr;	//대기조회 관련 처리 구분코드
	
	
	/*****************************************
	 * 마스킹 처리 권한별 혹은 전체 구분자
	 *****************************************/

	// Y:전체 마스킹 / N:권한별 마스킹 (defalt : N)
	private String isListYn = Const.BOOLEAN_FALSE;
	// Y: 마스킹 X / N: 마스킹 O (defalt : N)
	private String isMessageMailYn = Const.BOOLEAN_FALSE;

	/*****************************************
	 * 마스킹 처리 get 메서드
	 *****************************************/

	// 판매취소요청 주문자 정보
	public String getBuyerInfo() {
		if (UtilsText.equals(super.getMemberNo(), Const.NON_MEMBER_NO)) {
			return this.getBuyerName();
		} else {
			return UtilsText.concat(this.getBuyerName(), Const.L_PAREN, this.getBuyerId(), Const.R_PAREN);
		}
	}

	// 주문자id
	public String getBuyerId() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return this.buyerId;
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.loginId(this.buyerId);
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return this.buyerId;
				} else {
					return UtilsMasking.loginId(this.buyerId);
				}
			}
		}
	}

	// 이름
	public String getBuyerName() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return super.getBuyerName();
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.userName(super.getBuyerName());
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return super.getBuyerName();
				} else {
					return UtilsMasking.userName(super.getBuyerName());
				}
			}
		}
	}

	// 전화번호
	public String getBuyerTelNoText() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return super.getBuyerTelNoText();
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.cellPhoneNumber(super.getBuyerTelNoText());
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return super.getBuyerTelNoText();
				} else {
					return UtilsMasking.cellPhoneNumber(super.getBuyerTelNoText());
				}
			}
		}
	}

	// 휴대폰번호
	public String getBuyerHdphnNoText() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return super.getBuyerHdphnNoText();
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.cellPhoneNumber(super.getBuyerHdphnNoText());
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return super.getBuyerHdphnNoText();
				} else {
					return UtilsMasking.cellPhoneNumber(super.getBuyerHdphnNoText());
				}
			}
		}
	}

	// 우편주소
	public String getBuyerPostAddrText() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return super.getBuyerPostAddrText();
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.postAddress(super.getBuyerPostAddrText());
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return super.getBuyerPostAddrText();
				} else {
					return UtilsMasking.postAddress(super.getBuyerPostAddrText());
				}
			}
		}

	}

	// 상세주소
	public String getBuyerDtlAddrText() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return super.getBuyerDtlAddrText();
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.postAddress(super.getBuyerDtlAddrText());
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return super.getBuyerDtlAddrText();
				} else {
					return UtilsMasking.postAddress(super.getBuyerDtlAddrText());
				}
			}
		}

	}

	// 이름
	public String getRcvrName() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return super.getRcvrName();
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.userName(super.getRcvrName());
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return super.getRcvrName();
				} else {
					return UtilsMasking.userName(super.getRcvrName());
				}
			}
		}
	}

	// 전화번호
	public String getRcvrTelNoText() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return super.getRcvrTelNoText();
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.cellPhoneNumber(super.getRcvrTelNoText());
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return super.getRcvrTelNoText();
				} else {
					return UtilsMasking.cellPhoneNumber(super.getRcvrTelNoText());
				}
			}
		}
	}

	// 핸드폰
	public String getRcvrHdphnNoText() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return super.getRcvrHdphnNoText();
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.cellPhoneNumber(super.getRcvrHdphnNoText());
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return super.getRcvrHdphnNoText();
				} else {
					return UtilsMasking.cellPhoneNumber(super.getRcvrHdphnNoText());
				}
			}
		}
	}

	// 우편주소
	public String getRcvrPostAddrText() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return super.getRcvrPostAddrText();
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.postAddress(super.getRcvrPostAddrText());
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return super.getRcvrPostAddrText();
				} else {
					return UtilsMasking.postAddress(super.getRcvrPostAddrText());
				}
			}
		}
	}

	// 상세주소
	public String getRcvrDtlAddrText() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return super.getRcvrDtlAddrText();
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.postAddress(super.getRcvrDtlAddrText());
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return super.getRcvrDtlAddrText();
				} else {
					return UtilsMasking.postAddress(super.getRcvrDtlAddrText());
				}
			}
		}
	}

	// 환불계좌
	public String getRfndAcntText() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return super.getRfndAcntText();
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.bankAccoutNumber(super.getRfndAcntText());
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return super.getRfndAcntText();
				} else {
					return UtilsMasking.bankAccoutNumber(super.getRfndAcntText());
				}
			}
		}
	}

	// 예금주이름
	public String getAcntHldrName() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return super.getAcntHldrName();
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.userName(super.getAcntHldrName());
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return super.getAcntHldrName();
				} else {
					return UtilsMasking.userName(super.getAcntHldrName());
				}
			}
		}
	}

	// 요청자id
	public String getSellCnclReqtrId() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return this.sellCnclReqtrId;
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.loginId(this.sellCnclReqtrId);
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return this.sellCnclReqtrId;
				} else {
					return UtilsMasking.loginId(this.sellCnclReqtrId);
				}
			}
		}
	}

	// 요청자이름
	public String getSellCnclReqtrName() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return this.sellCnclReqtrName;
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.userName(this.sellCnclReqtrName);
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return this.sellCnclReqtrName;
				} else {
					return UtilsMasking.userName(this.sellCnclReqtrName);
				}
			}
		}
	}

	public String getSellCnclReqtr() {

		if (UtilsObject.isNotEmpty(this.getSellCnclReqtrName()) && UtilsObject.isNotEmpty(this.getSellCnclReqtrId())) {
			return UtilsText.concat(this.getSellCnclReqtrName(), Const.L_PAREN, this.getSellCnclReqtrId(),
					Const.R_PAREN);
		} else {
			return "";
		}
	}

	// 요청자id
	public String getModerId() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return this.moderId;
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.loginId(this.moderId);
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return this.moderId;
				} else {
					return UtilsMasking.loginId(this.moderId);
				}
			}
		}
	}

	// 요청자이름
	public String getModerName() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return this.moderName;
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.userName(this.moderName);
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return this.moderName;
				} else {
					return UtilsMasking.userName(this.moderName);
				}
			}
		}
	}

	// 요청자정보
	public String getModerInfo() {
		return UtilsText.concat(this.getModerName(), Const.L_PAREN, this.getModerId(), Const.R_PAREN);
	}

	// 요청자id
	public String getDlvyDscntcOpetrId() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return this.dlvyDscntcOpetrId;
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.loginId(this.dlvyDscntcOpetrId);
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return this.dlvyDscntcOpetrId;
				} else {
					return UtilsMasking.loginId(this.dlvyDscntcOpetrId);
				}
			}
		}
	}

	// 요청자이름
	public String getDlvyDscntcOpetrName() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return this.dlvyDscntcOpetrName;
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.userName(this.dlvyDscntcOpetrName);
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return this.dlvyDscntcOpetrName;
				} else {
					return UtilsMasking.userName(this.dlvyDscntcOpetrName);
				}
			}
		}
	}

	public String getStockGbnCodeName() {

		if (UtilsText.equals(this.stockGbnCode, CommonCode.STOCK_GBN_CODE_AS)
				&& !UtilsText.isBlank(super.getStoreName())) {
			// 재고구분코드가 매장이고 매장명이 있다면 : 매장(매장명)
			return UtilsText.concat(this.stockGbnCodeName, Const.L_PAREN, super.getStoreName(), Const.R_PAREN);
		}
		return this.stockGbnCodeName;
	}
	
	//주문 가능수량 계산
	public int getAvailableQtyAi() {
		return this.stockQtyAi - this.orderQtyAi;
	}
	public int getAvailableQtyAw() {
		return this.stockQtyAw - this.orderQtyAw;
	}
	public int getAvailableQtyAs() {
		return this.stockQtyAs - this.orderQtyAs;
	}
}
