package kr.co.abcmart.bo.afterService.model.master;

import kr.co.abcmart.bo.afterService.model.master.base.BaseOcAsPayment;
import kr.co.abcmart.common.util.UtilsMasking;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OcAsPayment extends BaseOcAsPayment {

	// 엑셀 용 sort 정보
	private String sortColumn;
	private String sortType;

	private String orgSiteNo;

	// 사이트번호
	private String siteNo;

	// 사이트명
	private String siteName;

	// 원주문번호
	private String orgOrderNo;

	// 주문번호
	private String orderNo;

	// 상품번호
	private String prdtNo;

	// 상품명
	private String prdtName;

	// 주문회원번호
	private String memberNo;

	// 주문자, 처리자 loginId
	private String rgsterId;

	// 주문자명
	private String rgsterName;

	// 배송비용
	private int addDlvyAmt;

	// A/S 비용
	private int asAmt;

	// TOTAL A/S 비용
	private int totalAsAmt;

	// TOTAL 합계 비용
	private int totalPymntAmt;

	// 접수상태코드 명
	private String asStatCodeName;

	// 접수상태코드
	private String asStatCode;

	// 기간검색 : ~부터
	private String fromDate;

	// 기간검색 : ~까지
	private String toDate;

	// 기간검색 - 환불,환수금액 발생일시 : R / 처리일시 : M
	private char dateGbnType;

	// 브랜드 네임
	private String brandName;

	// 주문자 명
	private String buyerName;

	// 주문자 Id
	private String loginId;

	// 휴대폰 번호
	private String buyerHdphnNoText;

	// 사유구분
	private String asGbnCode;

	// 상품 순번
	private short asAcceptPrdtSeq;

	// 접수일시
	private String rgstDtm;

	// 관리자 접수 여부
	private String adminAcceptYn;

	// 온라인 / 비회원 / 맵버쉽
	private String memberTypeName;
	
	// 휴대폰 번호 뒷자리
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

	// 브랜드 번호
	private String brandNo;

	/**
	 * 결제 상세 변수용
	 */
	// 디바이스코드 네임
	private String deviceCodeName;
	// 결제 방법 코드네임
	private String pymntMeansCodeName;
	// 결제 대행 업체 코드 명
	private String pymntVndrCodeName;
	// 결제상태코드 네임
	private String pymntStatCodeName;
	// 은행 코드 네임
	private String bankCodeName;
	// 결제 완료일시
	private String pymntCmlptDate;

	private String pymntVndrCode;

	private String ocrncRsnCodeName;

	private String cardMask;

	private String strPymntDtm;

	private java.sql.Timestamp dbPymntCmlptDate;

	public String getCardMask() {
		String cardMask = "";
		if (getPymntOrganNoText() != null && getPymntMeansCode().equals(CommonCode.PYMNT_MEANS_CODE_CARD)) {
			// if (getPymntMeansCode().equals(CommonCode.PYMNT_MEANS_CODE_CARD)) {
			cardMask = UtilsMasking.cardNumber(getPymntOrganNoText(), true);
		}
		return cardMask;
	}

}
