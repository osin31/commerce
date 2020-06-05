package kr.co.abcmart.bo.claim.model.master;

import kr.co.abcmart.bo.claim.model.master.base.BaseOcClaimPayment;
import kr.co.abcmart.common.util.UtilsMasking;
import kr.co.abcmart.common.util.UtilsMaskingFront;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OcClaimPayment extends BaseOcClaimPayment {

	private String isRefundProcCmplt = Const.BOOLEAN_FALSE;

	// 엑셀 용 sort 정보
	private String sortColumn;
	private String sortType;

	private java.lang.Integer redempAmt;

	private java.lang.Integer refundAmt;

	// 관리자ID
	private String adminId;
	// 관리자 명
	private String adminName;

	// 환불/환수 구분값에 따른 명칭
	private String redempRfndGbnName;

	// 원주문번호
	private String orgOrderNo;

	// 주문번호
	private String orderNo;

	// 주문결제상태코드
	private String orderPymntStatCode;

	// 결제상태코드 - 배열형태
	private String[] pymntStatCodes;

	// 결제상태코드 명
	private String pymntStatCodeName;

	// 결제상태코드(addInfo) 명
	private String pymntStatCodeAddinfoName;

	// 결제상태코드 환불접수
	private String pymntStatCodeRefundAccept;
	// 결제상태코드 환불완료
	private String pymntStatCodeRefundComplete;

	// 결제상태코드 환수접수
	private String pymntStatCodeRedempAccept;
	// 결제상태코드 환수완료
	private String pymntStatCodeRedempComplete;

	// 사이트번호
	private String siteNo;

	// 사이트명
	private String clmSiteName;

	// 클레임구분코드
	private String clmGbnCode;

	// 클레임구분코드 명
	private String clmGbnCodeName;

	// 회원번호
	private String memberNo;

	// 회원이름
	private String memberName;

	// 주문자, 처리자 loginId
	private String loginId;

	// 주문자명
	private String buyerName;

	// 주문자 (loginId + 주문자명)
	private String orderMember;

	// 기간검색 : ~부터
	private String fromDate;

	// 기간검색 : ~까지
	private String toDate;

	// 기간검색 - 환불,환수금액 발생일시 : R / 처리일시 : M
	private char dateGbnType;

	// 주문자 휴대폰번호
	private String buyerHdphnNoText;

	// 은행 코드 명
	private String bankCodeName;

	// 환불,환수 계좌 : 은행명 계좌번호
	private String clmAcnt;

	// 처리자
	private String clmHandler;

	// 발생사유코드 명
	private String ocrncRsnCodeName;

	// 멤버 타입 코드
	private String memberTypeCode;

	// 디바이스코드명
	private String deviceCodeName;

	// 결제수단코드명
	private String pymntMeansCodeName;

	// 결제업체코드명
	private String pymntVndrCodeName;

	// 상품유형코드 : 사은품
	private String prdtTypeCodeGift;

	// 상품유형코드 : 배송비
	private String prdtTypeCodeDelivery;

	// 비회원 회원 번호 MEMBER_NO
	private String nonMemberNo;

	// 결제일 string
	private String strPymntDtm;

	// 요청IP
	private String custIp;

	private String cardMask;

	// 에스크로전송응답코드
	private String escrSendRspnsCodeText;
	// 에스크로전송일시
	private java.sql.Timestamp escrSendDtm;

	// 결제 누적 취소금(결제 취소 성공과 무관)
	private java.lang.Integer accumulatedPymntCnclAmt;
	// 결제 잔여금(결제 취소 성공과 무관)
	private java.lang.Integer remainPymntCnclAmt;
	// 실제 결제 취소금(결제취소 성공 기준)
	private java.lang.Integer realPymntCnclAmt;
	// 실제 결제 잔여금(결제취소 성공 기준)
	private java.lang.Integer realRemainPymntCnclAmt;
	// 현재 클레임 결제 취소금
	private java.lang.Integer thisPymntCnclAmt;

	// str 디비 현재 시각
	private String strNowTime;

	// 결제수단코드명 여러개 한번에 보여주기
	private String pymntMeansCodeStuff;
	
	// 환불계좌등록여부
	private String rfndAcntExistYn;

	// 휴대폰 뒷4자리
	private String buyerHdphnBackNo;
	
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

	// 회원 ID
	public String getLoginId() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return loginId;
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.loginId(loginId);
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return loginId;
				} else {
					return UtilsMasking.loginId(loginId);
				}
			}
		}
	}

	// 회원명
	public String getMemberName() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return memberName;
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.userName(memberName);
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return memberName;
				} else {
					return UtilsMasking.userName(memberName);
				}
			}
		}
	}
	
	// 주문자명
	public String getBuyerName() {
		
		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return buyerName;
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.userName(buyerName);
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return buyerName;
				} else {
					return UtilsMasking.userName(buyerName);
				}
			}
		}
	}

	// 신용카드번호
	public String getCardMask() {

		if (UtilsObject.isEmpty(cardMask)) {
			return "";
		} else {
			return UtilsMaskingFront.cardNumber(cardMask, false);
		}
	}

	// 예금주 반환
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

	// 계좌번호
	public String getAcntNoText() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return super.getAcntNoText();
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.bankAccoutNumber(super.getAcntNoText());
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return super.getAcntNoText();
				} else {
					return UtilsMasking.bankAccoutNumber(super.getAcntNoText());
				}
			}
		}
	}

	public String getAdminId() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return this.adminId;
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.loginId(this.adminId);
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return this.adminId;
				} else {
					return UtilsMasking.loginId(this.adminId);
				}
			}
		}
	}

	public String getAdminName() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return this.adminName;
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.userName(this.adminName);
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return this.adminName;
				} else {
					return UtilsMasking.userName(this.adminName);
				}
			}
		}
	}

	/*****************************************
	 * 마스킹 처리 회피용 검색 변수들 search****
	 *****************************************/

	private String searchBuyerName;

	private String searchRcvrName;

	private String searchBuyerHdphnNoText;

	private String searchLoginId;
}