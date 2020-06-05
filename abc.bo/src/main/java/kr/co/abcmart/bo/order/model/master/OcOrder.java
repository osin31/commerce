package kr.co.abcmart.bo.order.model.master;

import java.util.List;

import kr.co.abcmart.bo.order.model.master.base.BaseOcOrder;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 주문
 * @FileName : OcOrder.java
 * @Project : abc.bo
 * @Date : 2019. 2. 11.
 * @Author : ljyoung@3top.co.kr
 */
@Slf4j
@Data
public class OcOrder extends BaseOcOrder {

	/**************************************************************
	 * 검색 관련
	 **************************************************************/
	/**
	 * 주문관련 검색 key
	 */
	private String orderSearchKey;

	/**
	 * 주문관련 검색 text, key에 따른 validation check
	 */
	private String orderSearchText;

	/**
	 * 주문상품관련 검색 key
	 */
	private String productSearchKey;

	/**
	 * 주문상품관련 검색 text, key에 따른 validation check
	 */
	private String productSearchText;

	/**
	 * 기간 검색 key
	 */
	private String orderDateKey;

	/**
	 * 검색기간 from
	 */
	private String fromDate;

	/**
	 * 검색기간 to
	 */
	private String toDate;

	/**
	 * 에스크로
	 */
	private String chkEscroTrue;
	private String chkEscroFalse;

	/**
	 * 회원유형
	 */
	private String[] memberTypeCodes;

	/**
	 * 회원등급
	 */
	private String[] mbshpGradeCodes;

	/**
	 * 회원등급
	 */
	private String[] deviceCodes;

	/**
	 * 임직원
	 */
	private String chkMemberTypeERP;

	/**
	 * 주문배송 유형 ( 매장픽업 , 택배 전환 )
	 */
	private String chnnlNo;

	/**
	 * 입점사 코드
	 */
	private String vndrNo;

	/**
	 * 입점사 명
	 */
	private String vndrName;

	/**
	 * 브랜드 번호
	 */
	private String brandNo;

	/**
	 * 브랜드 이름
	 */
	private String brandName;

	/**
	 * 쿠폰 사용 여부
	 */
	private String cpnDscntAmtYn;

	/**
	 * 주문 취소 여부l
	 */
	private String chkCancelAll;

	/**
	 * 회원유형 전체 선택
	 */
	private String chkMemberTypeAll;

	/**
	 * 주문배송상태 전체 선택
	 */
	private String orderPrdtStatCodeAll;

	/**
	 * 결제수단 전체 선택
	 */
	private String chkPaymentAll;

	/**
	 * 발송처 전체 선택
	 */
	private String stockGbnCodeAll;

	/**
	 * 주문 취소 여부
	 */
	private String chkCancel;
	private String optnName;

	/**
	 * 사이트명
	 */
	private String siteName;

	/**
	 * 재고 구분 코드 -- 발송처 ( 검색 )
	 */
	@ParameterOption(target = "stockGbnCode")
	private OcOrderDeliveryHistory[] stockGbnCodeList;
	/**
	 * 주문상품상태코드-- 주문배송상태 ( 검색 )
	 */
	@ParameterOption(target = "orderPrdtStatCode")
	private OcOrderProduct[] orderPrdtStatCodeList;

	/**
	 * 주문상품상태코드-- 결제수단 ( 검색 )
	 */
	@ParameterOption(target = "pymntMeansCode")
	private OcOrderPayment[] pymntMeansCodeList;

	private List<String> authSiteList;

	/**************************************************************
	 * 목록 관련
	 **************************************************************/

	/**
	 * 주문 목록 결제 수단
	 */
	private String pymntMeansCodeName;

	/**
	 * 주문 목록 발송처
	 */
	private String stockGbnCodeName;

	/**
	 * 주문 목록 주문취소여부
	 */
	private String orderCancelFlag;

	/**
	 * 주문 목록 결제 완료일
	 */
	private java.sql.Timestamp pymntDtm;

	/**
	 * 회원명 마스킹 처리
	 */
	public String getUserDpName() {
		String maskingText = "";

		if (null != this.getBuyerName() && null != this.getLoginId()) {
			if (LoginManager.isPersonalInfoManager()) {
				maskingText = UtilsText.concat(this.getLoginId(), Const.L_PAREN, this.getBuyerName(), Const.R_PAREN);
			} else {
				maskingText = UtilsText.concat(UtilsMasking.loginId(this.getLoginId()), Const.L_PAREN,
						UtilsMasking.userName(this.getBuyerName()), Const.R_PAREN);
			}
		}

		return maskingText;
	}

	/**
	 * 그리드 주문자 마스킹 처리
	 */
	public String getBuyerGridName() {
		return UtilsText.concat(UtilsMasking.loginId(this.getLoginId()), Const.L_PAREN,
				UtilsMasking.userName(this.getBuyerName()), Const.R_PAREN);
	}

	/**
	 * 그리드 수령자 마스킹 처리
	 */
	public String getRcvrGridName() {
		return UtilsMasking.userName(this.getRcvrName());
	}

	/**
	 * 수령자명 마스킹 처리
	 */
	public String getRcvrDpName() {
		String maskingText = "";

		if (null != this.getRcvrName()) {
			if (LoginManager.isPersonalInfoManager()) {
				maskingText = this.getRcvrName();
			} else {
				maskingText = UtilsMasking.userName(this.getRcvrName());
			}
		}

		return maskingText;
	}

	/**
	 * 비회원명 마스킹 처리
	 */
	public String getNonUserDpName() {
		String maskingText = "";

		if (null != this.getBuyerName()) {
			if (LoginManager.isPersonalInfoManager()) {
				maskingText = this.getBuyerName();
			} else {
				maskingText = UtilsMasking.userName(this.getBuyerName());
			}
		}

		return maskingText;
	}

	/**
	 * 취소 수량
	 */
	private Long claimCancelCnt;
	/**
	 * 반품 수량
	 */
	private Long claimReturnCnt;
	/**
	 * 교환 수량
	 */
	private Long claimExchangeCnt;

	/**
	 * as 수량
	 */
	private Long claimASCnt;

	/**
	 * 유선상담 갯수
	 */
	// private Long inquiryCnt;

	private Long orderCnt;
	private Long orderAmt;

	// 주문 취소 여부
	private String orderCancelYn;

	/**
	 * 주문번호배열
	 */
	private String[] orderNos;
	private String strOrderNos;

	/**************************************************************
	 * 상세 관련
	 **************************************************************/

	private String memberName;

	private String loginId;

	private String memberTypeCodeName;

	private String mbshpGradeCodeName;

	private String deviceCodeName;

	private String dlvyTypeCodeName;

	private String bankCodeName;

	private String orderStatCodeName;

	private String memBankCode;
	private String memBankAcntTxt;
	private String memBankUserName;
	private String memBankAuth;
	private String memBankAuthNo;
	private String memBankName;

	private Integer productTotCnt;
	private Long cancelPossibleCnt;
	private Long confirmCnt;

	private Long exchangeCnt;
	private Long returnCnt;

	private Long exchangeCancelCnt;
	private Long returnCancelCnt;

	// 취소접수 상품 갯수
	private Long cancelAcceptCnt;
	
	private Long asAcceptCnt;
	private Integer memoTotCnt;
	private Long cancelSumAmt;

	// 적립포인트
	private Integer savePoint;
	// 포인트 적립일시
	private java.sql.Timestamp savepointDtm;
	// 포인트 적립여부
	private String savepointYn;

	private String vaildationType;

	// 배송 id
	private String dlvyIdText;

	// 대시보드 local, vendor 구분
	private String orderType;

	// 자사상품 여부
	private String mmnyPrdtYn;

	// 상품 기준 택배 전환 counting
	private Long logisCnvrtCnt;

	// 휴대전화 뒷자리
	private String buyerHdphnBackNo;

	// 예약 발송일
	private java.sql.Timestamp rsvDlvyDtm;
	
	private Integer[] orderPrdtSeqs;

	/**************************************************************
	 * S : 상세 관련 - 마스킹
	 **************************************************************/

	// 이름
	public String getBuyerName() {

		if (LoginManager.isPersonalInfoManager()) {
			return super.getBuyerName();
		} else {
			return UtilsMasking.userName(super.getBuyerName());
		}
	}

	// 전화번호
	public String getBuyerTelNoText() {

		if (LoginManager.isPersonalInfoManager()) {
			return super.getBuyerTelNoText();
		} else {
			return UtilsMasking.cellPhoneNumber(super.getBuyerTelNoText());
		}
	}

	// 휴대폰번호
	public String getBuyerHdphnNoText() {

		if (LoginManager.isPersonalInfoManager()) {
			return super.getBuyerHdphnNoText();
		} else {
			return UtilsMasking.cellPhoneNumber(super.getBuyerHdphnNoText());
		}
	}

	// 우편주소
	public String getBuyerPostAddrText() {

		if (LoginManager.isPersonalInfoManager()) {
			return super.getBuyerPostAddrText();
		} else {
			return UtilsMasking.postAddress(super.getBuyerPostAddrText());
		}
	}

	// 상세주소
	public String getBuyerDtlAddrText() {

		if (LoginManager.isPersonalInfoManager()) {
			return super.getBuyerDtlAddrText();
		} else {
			return UtilsMasking.postAddress(super.getBuyerDtlAddrText());
		}
	}

	// 주문자 이메일주소
	public String getBuyerEmailAddrText() {

		if (LoginManager.isPersonalInfoManager()) {
			return super.getBuyerEmailAddrText();
		} else {
			return UtilsMasking.emailAddress(super.getBuyerEmailAddrText());
		}
	}

	// 이름
	public String getRcvrName() {

		if (LoginManager.isPersonalInfoManager()) {
			return super.getRcvrName();
		} else {
			return UtilsMasking.userName(super.getRcvrName());
		}
	}

	// 전화번호
	public String getRcvrTelNoText() {

		if (LoginManager.isPersonalInfoManager()) {
			return super.getRcvrTelNoText();
		} else {
			return UtilsMasking.cellPhoneNumber(super.getRcvrTelNoText());
		}
	}

	// 핸드폰
	public String getRcvrHdphnNoText() {

		if (LoginManager.isPersonalInfoManager()) {
			return super.getRcvrHdphnNoText();
		} else {
			return UtilsMasking.cellPhoneNumber(super.getRcvrHdphnNoText());
		}
	}

	// 우편주소
	public String getRcvrPostAddrText() {

		if (LoginManager.isPersonalInfoManager()) {
			return super.getRcvrPostAddrText();
		} else {
			return UtilsMasking.postAddress(super.getRcvrPostAddrText());
		}
	}

	// 상세주소
	public String getRcvrDtlAddrText() {

		if (LoginManager.isPersonalInfoManager()) {
			return super.getRcvrDtlAddrText();
		} else {
			return UtilsMasking.postAddress(super.getRcvrDtlAddrText());
		}
	}

	// 환불계좌
	public String getRfndAcntText() {

		if (LoginManager.isPersonalInfoManager()) {
			return super.getRfndAcntText();
		} else {
			return UtilsMasking.bankAccoutNumber(super.getRfndAcntText());
		}
	}

	// 예금주이름
	public String getAcntHldrName() {

		if (LoginManager.isPersonalInfoManager()) {
			return super.getAcntHldrName();
		} else {
			return UtilsMasking.userName(super.getAcntHldrName());
		}
	}

	// 회원 ID (관리자 ID)
	public String getLoginId() {

		if (LoginManager.isPersonalInfoManager()) {
			return this.loginId;
		} else {
			return UtilsMasking.loginId(this.loginId);
		}
	}

	// 회원명
	public String getMemberName() {

		if (LoginManager.isPersonalInfoManager()) {
			return this.memberName;
		} else {
			return UtilsMasking.userName(this.memberName);
		}
	}

	/**************************************************************
	 * E : 상세 관련 - 마스킹
	 **************************************************************/
}
