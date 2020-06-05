package kr.co.abcmart.bo.claim.model.master;

import java.util.List;

import kr.co.abcmart.bo.claim.model.master.base.BaseOcClaim;
import kr.co.abcmart.bo.order.model.master.OcOrderDeliveryHistory;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.common.util.UtilsMasking;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OcClaim extends BaseOcClaim {

	/**
	 * 클레임 목록/검색 화면
	 */

	// 클레임상태코드조건
	private String clmStatCodeCondition;

	// 결제상태코드 : 결제완료
	private String pymntStatCodePaymentFinish;

	// 자사상품여부
	private String mmnyPrdtYn;

	// 접수자 관리자ID
	private String rgsterAdminId;
	// 접수자 관리자명
	private String rgsterAdminName;
	// 처리자 관리자ID
	private String moderAdminId;
	// 처리자 관리자명
	private String moderAdminName;

	// 클레임 접수자 (접수자id + 접수자이름)
	private String clmApplicant;

	// 클레임 처리자 (관리자id + 관리자이름)
	private String clmHandler;

	// 클레임 상품 갯수 : 클레임 목록 표시용
	private java.lang.Integer clmPrdtCnt;

	// ~ 외 몇 건의 클레임 상품 갯수
	private java.lang.Integer clmPrdtExtraCnt;

	// 클레임 상품 목록표시 명 : 클레임 목록 표시용
	private String clmPrdtName;

	// 배송비결제방법 : 클레임 목록 표시용
	private String clmAddDlvyAmtPymntType;

	// 미처리여부 : 클레임 목록 표시용
	private String clmUnProcYn;

	// 사이트 네임 : 클레임 목록 표시용
	private String clmSiteName;

	// 클레임구분코드 명
	private String clmGbnCodeName;

	// 기기 코드 : 결제구분
	private String deviceCode;

	// 기기 코드 값 : 결제구분
	private String deviceCodeName;

	// 상품번호
	private String prdtNo;

	// 상품명
	private String prdtName;

	// 스타일
	private String styleInfo;

	// 업체상품번호
	private String vndrPrdtNoText;

	// 클레임 관리 - 기간검색 - 주문일(ORDER) : O / 접수일시(CLAIM) : C
	private char dateGbnType;

	// 기간 설정 시작일
	private String fromDate;

	// 기간 설정 끝일
	private String toDate;

	// MEMBER_TYPE_CODE 의 값 : 온라인회원/맴버쉽회원/비회원
	private String memberTypeName;

	// 클레임 검색 : 주문 - 주문자id
	private String loginId;

	// 재고 구분 코드 -- 발송처 ( 검색 )
	@ParameterOption(target = "stockGbnCode")
	private OcOrderDeliveryHistory[] stockGbnCodeList;

	// 에스크로 미적용 N
	private String chkPaymentNoESCR;

	// 에스크로 적용 Y
	private String chkPaymentESCR;

	// 결제수단코드
	private String pymntMeansCode;

	// 클레임환불 -- 결제수단 ( 검색 )
	@ParameterOption(target = "pymntMeansCode")
	private OcClaimPayment[] pymntMeansCodeList;

	// 클레임별진행상태 - 클레임상태코드타입
	private String clmPrcsStat;

	// 클레임 상태 코드 배열
	@ParameterOption(target = "clmStatCode")
	private BaseOcClaim[] clmStatCodeList;

	// 클레임철회사유코드명
	private String clmWthdrawRsnCodeName;

	// 등록자id(회원,관리자 접수구분에 따른 login id)
	private String rgsterId;

	// 등록자명(등록자id 에 대한 이름)
	private String rgsterName;

	// 업체명
	private String vndrName;

	// 업체구분
	private String vndrGbnType;

	// 클레임상태코드명
	private String clmStatCodeName;

	// 주문 수취인명
	private String orderRcvrName;

	// 주문 수취인전화번호
	private String orderRcvrTelNoText;

	// 주문 수취인핸드폰번호
	private String orderRcvrHdphnNoText;

	// 주문 수취인우편번호
	private String orderRcvrPostCodeText;

	// 주문 수취인우편주소
	private String orderRcvrPostAddrText;

	// 주문 수취인상세주소
	private String orderRcvrDtlAddrText;

	// 클레임 사유코드 명
	private String clmRsnName;

	// 클레임 사유코드명 ~외 ~건 갯수
	private java.lang.Integer clmRsnNameExtraCnt;

	// 관리자 번호
	private String adminNo;

	// 입점사(업체) 번호
	private String vndrNo;

	// 브랜드 번호
	private String brandNo;

	// 브랜드 이름
	private String brandName;

	// 사이트명
	private String siteName;

	// 회원명
	private String memberName;

	// 상품유형코드 : 사은품
	private String prdtTypeCodeGift;

	// 상품유형코드 : 배송비
	private String prdtTypeCodeDelivery;

	// 비회원 회원 번호 MEMBER_NO
	private String nonMemberNo;

	// 결제수단코드 : 가상계좌
	private String pymntMeansCodeVirtualAccount;

	// 상품타입코드 배열
	private String[] prdtTypeCodeList;

	// 상품 유형 코드
	private String prdtTypeCode;

	// 클레임사유코드
	private String clmRsnCode;

	// 클레임사유코드 명
	private String clmRsnCodeName;

	// 클레임기타사유
	private String clmEtcRsnText;

	// 클레임생성일시 String
	private String strClmDtm;

	// 클레임수정일시 String
	private String strModDtm;

	// 은행코드명
	private String bankCodeName;

	// 가상계좌여부
	private String vrtlAcntYn;

	// 상품관련파일순번
	private java.lang.Integer prdtRltnFileSeq;

	// 클레임상품목록
	private List<OcClaimProduct> ocClaimProductList;

	// 리오더 주문번호
	private String reOrderNo;

	// 택배사코드 명
	private String logisVndrCodeName;

	// 클레임 등록자 번호
	private String claimRgsterNo;

	private String[] clmStatCodes;

	private String[] clmGbnCodes;

	private String clmGbnCodeCancel;

	private String clmGbnCodeExchange;

	private String clmGbnCodeReturn;

	private String redempRefundGbnType;

	private String mmnyProcTrgtYn;

	private String histGbnType;

	private String isMemberYn;

	private String buyerHdphnBackNo;

	// 폼 취소금액 변경 여부
	private String chngInputCnclAmtYn;

	/**
	 * 취소/반품 - 환불금액 - 취소금액영역
	 */
	// 주문금액
	private java.lang.Integer sumOrderAmt;
	// 환불 배송비
	private java.lang.Integer sumDlvyAmt;

	// 사용자 IP
	private String custIp;

	// 환수금 임의처리 여부
	private String redempAmtRndmProcYn;

	// 주문결제상태코드
	private String orderPymntStatCode;

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

	// 회원 ID (관리자 ID)
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

	public String getRgsterAdminId() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return this.rgsterAdminId;
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.loginId(this.rgsterAdminId);
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return this.rgsterAdminId;
				} else {
					return UtilsMasking.loginId(this.rgsterAdminId);
				}
			}
		}
	}

	public String getRgsterAdminName() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return this.rgsterAdminName;
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.userName(this.rgsterAdminName);
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return this.rgsterAdminName;
				} else {
					return UtilsMasking.userName(this.rgsterAdminName);
				}
			}
		}
	}

	public String getModerAdminId() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return this.moderAdminId;
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.loginId(this.moderAdminId);
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return this.moderAdminId;
				} else {
					return UtilsMasking.loginId(this.moderAdminId);
				}
			}
		}
	}

	public String getModerAdminName() {

		if (this.isMessageMailYn.equals(Const.BOOLEAN_TRUE)) {
			return this.moderAdminName;
		} else {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				return UtilsMasking.userName(this.moderAdminName);
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					return this.moderAdminName;
				} else {
					return UtilsMasking.userName(this.moderAdminName);
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
