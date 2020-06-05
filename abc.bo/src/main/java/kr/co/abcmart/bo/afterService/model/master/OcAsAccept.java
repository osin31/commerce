package kr.co.abcmart.bo.afterService.model.master;

import kr.co.abcmart.bo.afterService.model.master.base.BaseOcAsAccept;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.common.util.UtilsMasking;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OcAsAccept extends BaseOcAsAccept implements Validator {

	// 엑셀 용 sort 정보
	private String sortColumn;
	private String sortType;

	private String orgSiteNo;

	// 로그인 아이디
	private String loginId;

	// 회원명
	private String memberName;

	// 상품번호
	private String prdtNo;

	// 상품명
	private String prdtName;

	// 옵션명
	private String optnName;

	// 브랜드명
	private String brandName;

	// 결제금액
	private int pymntAmt;

	// 접수자
	private String rgsterNo;

	// 스타일
	private String styleInfo;

	// 기간 설정 시작일
	private String fromDate;

	// 기간 설정 끝일
	private String toDate;

	// 온라인 / 비회원 / 맵버쉽
	private String memberTypeName;

	// 회원 및 관리자 번호
	private String asRgsterNo;

	// 관리자 번호
	private String adminNo;

	// 재접수 버튼을 나타내기 위한 변수
	private String colModify;

	// 진행상태코드 네임
	private String asStatCodeName;

	// 처리자 아이디
	private String moderId;

	// 처리자 이름
	private String moderName;

	// 사유코드
	private String asRsnCode;

	// 상품 여러게 일경우 대비
	private OcAsAcceptProduct[] ocAsAcceptProducts;

	// 주문순번
	private int orderPrdtSeq;

	// 상품 옵션 번호
	private String prdtOptnNo;

	// 영문상품명
	private String engPrdtName;

	// 상품색상코드
	private String prdtColorCode;

	// 상품유형코드
	private String prdtTypeCode;

	// 접수내용
	private String asAcceptContText;

	// 등록자id(회원,관리자 접수구분에 따른 login id)
	private String rgsterId;

	// 등록자명(등록자id 에 대한 이름)
	private String rgsterName;

	// 상품 순번
	private short asAcceptPrdtSeq;

	// AS관리 진행상태
	@ParameterOption(target = "asStatCode")
	private BaseOcAsAccept[] asStatCodeList;

	@ParameterOption(target = "deviceCode")
	private BaseOcAsAccept[] deviceCodeList;

	// 처리(심의)유형 코드
	private String asProcTypeCode;

	// 처리(심의)유형 상세코드
	private String asProcTypeDtlCode;

	// 처리 유형 내용
	private String asProcContText;

	// A/S비용
	private int asAmt;

	// A/S배송정보
	private String logisVndrCode;

	// AS회수택배사 addInfo1
	private String rtrvlLogisVndrCodeAddInfo1;

	// 운송장 번호
	private String waybilNoText;

	// 브랜드 번호
	private String brandNo;

	// 주문금액
	private int orderAmt;

	// 수선불가 > 교환시
	private String exchangeYn;

	// 수선불가 > 반품시
	private String returnYn;

	// 관리자 확인여부
	private String handler;

	// 관리자 YN
	private String isConfirm;

	// 회수택배사 명
	private String rtrvlVndrCodeName;

	// 에러메세지
	private String errorMessage;

	// 업체 상품 코드
	private String vndrPrdtNoText;

	// 배송ID
	private String dlvyIdText;

	// 처리내용
	private String asProcTypeInfo;
	
	// 휴대폰번호 뒷자리
	private String hdphnBackNoText;
	

	/*****************************************
	 * 마스킹 처리 권한별 혹은 전체 구분자
	 *****************************************/

	// Y:전체 마스킹 / N:권한별 마스킹 (defalt : N)
	private String isListYn = Const.BOOLEAN_FALSE;

	/*****************************************
	 * 마스킹 처리 get 메서드
	 *****************************************/

	// 접수자 (관리자 +사용자)마스킹 처리 여부
	public String getRgsterName() {
		String rgsterName = "";

		if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
			rgsterName = UtilsText.concat(UtilsMasking.loginId(this.rgsterId), Const.L_PAREN,
					UtilsMasking.userName(this.rgsterName), Const.R_PAREN);
		} else {
			if (LoginManager.isPersonalInfoManager()) {
				rgsterName = UtilsText.concat(this.rgsterId, Const.L_PAREN, this.rgsterName, Const.R_PAREN);
			} else {
				rgsterName = UtilsText.concat(UtilsMasking.loginId(this.rgsterId), Const.L_PAREN,
						UtilsMasking.userName(this.rgsterName), Const.R_PAREN);
			}
		}

		return rgsterName;
	}

	// 처리자 마스킹
	public String getModerName() {
		String moderName = "";

		if (this.moderId != null && this.moderName != null) {
			if (this.isListYn.equals(Const.BOOLEAN_TRUE)) {
				moderName = UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
						UtilsMasking.userName(this.moderName), Const.R_PAREN);
			} else {
				if (LoginManager.isPersonalInfoManager()) {
					moderName = UtilsText.concat(this.moderId, Const.L_PAREN, this.moderName, Const.R_PAREN);
				} else {
					moderName = UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
							UtilsMasking.userName(this.moderName), Const.R_PAREN);
				}
			}
		}

		return moderName;
	}

	@Override
	public void validate() throws ValidatorException {
		if (UtilsText.isBlank(getRcvrPostCodeText())) {
			validationMessage("재발송수령지 우편번호가 존재하지 않습니다.", true);
		}

		if (UtilsText.isBlank(getRcvrPostAddrText())) {
			validationMessage("재발송수령지 주소가 존재하지 않습니다.", true);
		}

		if (UtilsText.isBlank(getRcvrDtlAddrText())) {
			validationMessage("재발송수령지 상세주소가 존재하지 않습니다.", true);
		}

		if (UtilsText.isBlank(getRcvrName())) {
			validationMessage("재수령자명이 존재하지 않습니다.", true);
		}

		if (UtilsText.isBlank(getRcvrHdphnNoText())) {
			validationMessage("재발송수령지 연락처가 존재하지 않습니다.", true);
		}
	}

	/************ S: 이강수 ******************************/

	// AS 사유 코드 명
	private String asRsnCodeName;

	// AS 상품 명
	private String asAcceptPrdtName;

	// AS 처리자 명
	private String asAcceptHandler;

	// 카드 인지 실시간인지 구분코드
	private String pymntMeansCode;

	// 접수일시 변환 YYYY.MM.DD
	private String asAcceptDate;

	// 사유구분코드 네임
	private String asGbnCodeName;

	private String orgOrderDate;

	private String strModDtm;

	private String asProcTypeCodeName;

	private String asProcTypeDtlCodeName;

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

	/************ E: 이강수 ******************************/

	/*****************************************
	 * 마스킹 처리
	 *****************************************/

	private String maskingYn = Const.BOOLEAN_FALSE;

	// 이름
	public String getBuyerName() {

		if (this.maskingYn.equals(Const.BOOLEAN_FALSE)) {
			return super.getBuyerName();
		} else {
			if (LoginManager.isPersonalInfoManager()) {
				return super.getBuyerName();
			} else {
				return UtilsMasking.userName(super.getBuyerName());
			}
		}
	}

	// 전화번호
	public String getBuyerTelNoText() {

		if (UtilsText.equals(this.maskingYn, Const.BOOLEAN_FALSE)) {
			return super.getBuyerTelNoText();
		} else {
			if (LoginManager.isPersonalInfoManager()) {
				return super.getBuyerTelNoText();
			} else {
				return UtilsMasking.cellPhoneNumber(super.getBuyerTelNoText());
			}
		}
	}

	// 휴대폰번호
	public String getBuyerHdphnNoText() {

		if (UtilsText.equals(this.maskingYn, Const.BOOLEAN_FALSE)) {
			return super.getBuyerHdphnNoText();
		} else {
			if (LoginManager.isPersonalInfoManager()) {
				return super.getBuyerHdphnNoText();
			} else {
				return UtilsMasking.cellPhoneNumber(super.getBuyerHdphnNoText());
			}
		}
	}

	// 우편주소
	public String getBuyerPostAddrText() {

		if (UtilsText.equals(this.maskingYn, Const.BOOLEAN_FALSE)) {
			return super.getBuyerPostAddrText();
		} else {
			if (LoginManager.isPersonalInfoManager()) {
				return super.getBuyerPostAddrText();
			} else {
				return UtilsMasking.postAddress(super.getBuyerPostAddrText());
			}
		}
	}

	// 상세주소
	public String getBuyerDtlAddrText() {

		if (UtilsText.equals(this.maskingYn, Const.BOOLEAN_FALSE)) {
			return super.getBuyerDtlAddrText();
		} else {
			if (LoginManager.isPersonalInfoManager()) {
				return super.getBuyerDtlAddrText();
			} else {
				return UtilsMasking.postAddress(super.getBuyerDtlAddrText());
			}
		}
	}

	// 이름
	public String getRcvrName() {

		if (UtilsText.equals(this.maskingYn, Const.BOOLEAN_FALSE)) {
			return super.getRcvrName();
		} else {
			if (LoginManager.isPersonalInfoManager()) {
				return super.getRcvrName();
			} else {
				return UtilsMasking.userName(super.getRcvrName());
			}
		}
	}

	// 전화번호
	public String getRcvrTelNoText() {

		if (UtilsText.equals(this.maskingYn, Const.BOOLEAN_FALSE)) {
			return super.getRcvrTelNoText();
		} else {
			if (LoginManager.isPersonalInfoManager()) {
				return super.getRcvrTelNoText();
			} else {
				return UtilsMasking.cellPhoneNumber(super.getRcvrTelNoText());
			}
		}
	}

	// 핸드폰
	public String getRcvrHdphnNoText() {

		if (UtilsText.equals(this.maskingYn, Const.BOOLEAN_FALSE)) {
			return super.getRcvrHdphnNoText();
		} else {
			if (LoginManager.isPersonalInfoManager()) {
				return super.getRcvrHdphnNoText();
			} else {
				return UtilsMasking.cellPhoneNumber(super.getRcvrHdphnNoText());
			}
		}

	}

	// 우편주소
	public String getRcvrPostAddrText() {

		if (UtilsText.equals(this.maskingYn, Const.BOOLEAN_FALSE)) {
			return super.getRcvrPostAddrText();
		} else {
			if (LoginManager.isPersonalInfoManager()) {
				return super.getRcvrPostAddrText();
			} else {
				return UtilsMasking.postAddress(super.getRcvrPostAddrText());
			}
		}
	}

	// 상세주소
	public String getRcvrDtlAddrText() {

		if (UtilsText.equals(this.maskingYn, Const.BOOLEAN_FALSE)) {
			return super.getRcvrDtlAddrText();
		} else {
			if (LoginManager.isPersonalInfoManager()) {
				return super.getRcvrDtlAddrText();
			} else {
				return UtilsMasking.postAddress(super.getRcvrDtlAddrText());
			}
		}
	}

}
