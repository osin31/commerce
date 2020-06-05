package kr.co.abcmart.bo.event.vo;

import java.io.Serializable;

import kr.co.abcmart.common.bean.BaseBean;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;

@Data
public class EvEventJoinResultVO extends BaseBean implements Serializable {

	/**
	 * 설명 : 이벤트참여회원순번
	 */
	private java.lang.Long evEventJoinMemberSeq;

	/**
	 * 설명 : 이벤트번호
	 */
	private String eventNo;
	/**
	 * 설명 : 이벤트 명
	 */
	private String eventName;

	/**
	 * 설명 : 회원번호
	 */
	private String memberNo;

	/**
	 * 설명 : 상품번호
	 */
	private String prdtNo;

	/**
	 * 설명 : 추가정보1
	 */
	private String addInfo1;

	/**
	 * 설명 : 추가정보2
	 */
	private String addInfo2;

	/**
	 * 설명 : 추가정보3
	 */
	private String addInfo3;

	/**
	 * 설명 : 추가정보4
	 */
	private String addInfo4;

	/**
	 * 설명 : 추가정보5
	 */
	private String addInfo5;

	/**
	 * 설명 : 참여일시
	 */
	private java.sql.Timestamp joinDtm;

	/**
	 * 설명 : 당첨여부
	 */
	private String winYn;

	/**
	 * 설명 : 이벤트룰렛참여회원순번
	 */
	private java.lang.Long eventRuletJoinMemberSeq;

	/**
	 * 설명 : 이벤트룰렛혜택순번
	 */
	private java.lang.Short eventRuletBenefitSeq;

	/**
	 * 설명 : 발급여부
	 */
	private String issueYn;

	/**
	 * 설명 : 발급정보
	 */
	private String issueInfo;

	/**
	 * 설명 : 발급자번호
	 */
	private String issuerNo;

	/**
	 * 설명 : 발급일시
	 */
	private java.sql.Timestamp issueDtm;

	/**
	 * 설명 : 이벤트출석체크회원순번
	 */
	private java.lang.Long eventAtendCheckMemberSeq;

	/**
	 * 설명 : 출석일시
	 */
	private java.sql.Timestamp atendDtm;

	/**
	 * 설명 : 이벤트댓글순번
	 */
	private java.lang.Long eventAswrSeq;

	/**
	 * 설명 : 댓글내용
	 */
	private String aswrContText;

	/**
	 * 설명 : 전시여부
	 */
	private String dispYn;

	/**
	 * 설명 : 전시여부명
	 */
	private String dispYnName;

	/**
	 * 설명 : 미전시사유코드
	 */
	private String unDispRsnCode;
	/**
	 * 설명 : 미전시사유코드명
	 */
	private String unDispRsnCodeName;

	/**
	 * 설명 : 미전시사유
	 */
	private String unDispRsnText;

	/**
	 * 설명 : 등록일시
	 */
	private java.sql.Timestamp rgstDtm;

	/**
	 * 설명 : 수정자번호
	 */
	private String moderNo;

	/**
	 * 설명 : 수정일시
	 */
	private java.sql.Timestamp modDtm;

	/**
	 * 설명 : 쿠폰명
	 */
	private String cpnName;

	/**
	 * 설명 : 회원유형명
	 */
	private String memberTypeCodeName;
	/**
	 * 설명 : 혜택명
	 */
	private String benefitName;
	/**
	 * 설명 : 출석 수
	 */
	private String attendCount;
	/**
	 * 설명 : 로그인 아이디
	 */
	private String loginId;
	/**
	 * 설명 : 디바이스명
	 */
	private String deviceCodeName;
	/**
	 * 설명 : 채널 명
	 */
	private String chnnlName;
	/**
	 * 설명 : 이벤트 타입 명
	 */
	private String eventTypeCodeName;
	/**
	 * 설명 : 수정자명
	 */
	private String moderName;
	/**
	 * 설명 : 수정자ID
	 */
	private String moderId;
	/**
	 * 설명 : 엑셀번호
	 */
	private int excelIndex;

	/**
	 * 설명 : 상품명
	 */
	private String prdtName;
	/**
	 * 설명 : 매장명
	 */
	private String storeName;
	/**
	 * 설명 : 상품 옵션명
	 */
	private String optnName;
	/**
	 * 설명 : 혜택 출석 일수
	 */
	private String benefitAtendDayCount;
	/**
	 * 설명 : 당첨자 수
	 */
	private int winCount;
	/**
	 * 설명 : 주문번호
	 */
	private String orderNo;
	/**
	 * 설명 : 주문 상품 상태 코드
	 */
	private String orderPrdtStatCodeName;
	/**
	 * 설명 : 혜택 코드
	 */
	private String benefitGbnCode;
	
	public String getLoginIdMask() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsMasking.loginId(this.loginId);
		} else {
			maskingStr = this.loginId;
		}

		return maskingStr;
	}

	public String getAddInfo1Mask() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsMasking.userName(this.addInfo1);
		} else {
			maskingStr = this.addInfo1;
		}

		return maskingStr;
	}
	public String getAddInfo2Mask() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsMasking.cellPhoneNumber(this.addInfo2);
		} else {
			maskingStr = this.addInfo2;
		}
		
		return maskingStr;
	}
	public String getAddInfo3Mask() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsMasking.emailAddress(this.addInfo3);
		} else {
			maskingStr = this.addInfo3;
		}
		
		return maskingStr;
	}

	// 수정자 정보 마스킹 처리
	public String getModerInfo() {
		String maskingStr = "";
		if (UtilsText.isNotBlank(this.moderId)) {
			if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
				maskingStr = UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
						UtilsMasking.userName(this.moderName), Const.R_PAREN);
			} else {
				maskingStr = UtilsText.concat(this.moderId, Const.L_PAREN, this.moderName, Const.R_PAREN);
			}
		}
		return maskingStr;
	}

	public String getIssueYnName() {
		String issueYnName = "발급안함";

		if (UtilsText.isNotBlank(this.getIssueYn()) && UtilsText.equals(this.getIssueYn(), "Y")) {
			issueYnName = "발급";
		}

		return issueYnName;
	}

	public String getDispYnReqName() {
		String dispYnName = "전시안함";

		if (UtilsText.isNotBlank(this.getDispYn()) && UtilsText.equals(this.getDispYn(), "N")) {
			dispYnName = "전시";
		}

		return dispYnName;
	}

	public String getDispYnName() {
		String dispYnName = "전시";

		if (UtilsText.isNotBlank(this.getDispYn()) && UtilsText.equals(this.getDispYn(), "N")) {
			dispYnName = "전시안함";
		}

		return dispYnName;
	}

	public String getWinYnName() {
		String winYnName = "미당첨";

		if (this.getWinCount() > 0) {
			winYnName = "당첨";
		}

		return winYnName;
	}
	
	public String getLoginIdMaskNoAuth() {
		String maskingStr = "";
		if (UtilsText.isNotBlank(this.loginId)) {
			maskingStr = UtilsMasking.loginId(this.loginId);
		}
		return maskingStr;
	}

	public String getAddInfo1MaskNoAuth() {
		String maskingStr = "";
		if (UtilsText.isNotBlank(this.addInfo1)) {
			maskingStr = UtilsMasking.userName(this.addInfo1);
		}
		return maskingStr;
	}
	public String getAddInfo2MaskNoAuth() {
		String maskingStr = "";
		if (UtilsText.isNotBlank(this.addInfo2)) {
			maskingStr = UtilsMasking.cellPhoneNumber(this.addInfo2);
		}
		return maskingStr;
	}
	public String getAddInfo3MaskNoAuth() {
		String maskingStr = "";
		if (UtilsText.isNotBlank(this.addInfo3)) {
			maskingStr = UtilsMasking.emailAddress(this.addInfo3);
		}
		return maskingStr;
	}

	// 수정자 정보 마스킹 처리
	public String getModerInfoNoAuth() {
		String maskingStr = "";
		if (UtilsText.isNotBlank(this.moderId)) {
			maskingStr = UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
					UtilsMasking.userName(this.moderName), Const.R_PAREN);
		}
		return maskingStr;
	}
	
}
