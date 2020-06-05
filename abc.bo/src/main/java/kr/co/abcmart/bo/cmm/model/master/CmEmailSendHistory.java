package kr.co.abcmart.bo.cmm.model.master;

import kr.co.abcmart.bo.cmm.model.master.base.BaseCmEmailSendHistory;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString(callSuper = true)
public class CmEmailSendHistory extends BaseCmEmailSendHistory {
	private String[] arrSendYn; // 발송,미발송 저장 변수
	private String fromDate; // 검색 시작일자
	private String toDate; // 검색 종료일자
	private String rcvrSearchType; // 검색 유형[이름,아이디]
	private String dateSearchType; // 날짜 검색 유형
	private String rcvrSearchText; // 받는사람 검색 유형
	private String siteName; // 사이트명
	private String adminNo;
	private String adminId; // 관리자ID(ex:sysdev)
	private String adminName; // 관리자명
	private String loginId; // 회원 로그인ID
	private String leaveYn; // 회원 탈퇴여부(Y/N)
	private String adminSendYnName; // 분류[관리자/시스템]

	// 관리자 정보 마스킹
	public String getAdminInfo() {
		String result = "";
		boolean memberInfoMgmtYn = LoginManager.isPersonalInfoManager();

		if (getAdminId() == null) {
			if (memberInfoMgmtYn) {
				result = "system";
			} else {
				result = UtilsMasking.loginId("system");
			}
		} else if (UtilsText.equals(Const.SYSTEM_ADMIN_NO, getAdminNo())) {
			if (memberInfoMgmtYn) {
				result = getAdminId();
			} else {
				result = UtilsMasking.loginId(getAdminId());
			}
		} else {
			if (memberInfoMgmtYn) {
				result = UtilsText.concat(getAdminId(), Const.L_PAREN, getAdminName(), Const.R_PAREN);
			} else {
				result = UtilsText.concat(UtilsMasking.loginId(getAdminId()), Const.L_PAREN,
						UtilsMasking.userName(getAdminName()), Const.R_PAREN);
			}
		}
		return result;
	}

	// 관리자 정보 마스킹[그리드]
	public String getGridAdminInfo() {
		String result = "";

		if (getAdminId() == null) {
			result = UtilsMasking.loginId("system");
		} else if (UtilsText.equals(Const.SYSTEM_ADMIN_NO, getAdminNo())) {
			result = UtilsMasking.loginId(getAdminId());
		} else {
			result = UtilsText.concat(UtilsMasking.loginId(getAdminId()), Const.L_PAREN,
					UtilsMasking.userName(getAdminName()), Const.R_PAREN);
		}
		return result;
	}

	// 회원 정보 마스킹
	public String getMemberInfo() {
		String result = "";
		boolean memberInfoMgmtYn = LoginManager.isPersonalInfoManager();

		if (getLoginId() == null) { // 비회원이거나 탈퇴한 경우는 login를 보여줄 수 없으므로 수신자명만 암호화해서 보여줌
			if (memberInfoMgmtYn) {
				result = getRcvrName();
			} else {
				result = UtilsMasking.userName(getRcvrName());
			}
		} else {
			if (memberInfoMgmtYn) {
				result = UtilsText.concat(getLoginId(), Const.L_PAREN + getRcvrName(), Const.R_PAREN);
			} else {
				result = UtilsText.concat(UtilsMasking.loginId(getLoginId()), Const.L_PAREN,
						UtilsMasking.userName(getRcvrName()), Const.R_PAREN);
			}
		}
		return result;
	}

	// 회원 정보 마스킹[그리드]
	public String getGridMemberInfo() {
		String result = "";

		if (getLoginId() == null) { // 비회원이거나 탈퇴한 경우는 login를 보여줄 수 없으므로 수신자명만 암호화해서 보여줌
			result = UtilsMasking.userName(getRcvrName());
		} else {
			result = UtilsText.concat(UtilsMasking.loginId(getLoginId()), Const.L_PAREN,
					UtilsMasking.userName(getRcvrName()), Const.R_PAREN);
		}
		return result;
	}

	// 보낸사람 이메일 마스킹
	public String getSendEmailInfo() {
		String result = "";
		boolean memberInfoMgmtYn = LoginManager.isPersonalInfoManager();

		if (memberInfoMgmtYn) {
			result = getSndrEmailAddrText();
		} else {
			result = UtilsMasking.emailAddress(getSndrEmailAddrText());
		}

		return result;
	}

	// 보낸사람 이메일 마스킹[그리드]
	public String getGridSendEmailInfo() {
		String result = "";
		result = UtilsMasking.emailAddress(getSndrEmailAddrText());

		return result;
	}

	// 받는사람 이메일 마스킹
	public String getRcvrEmailInfo() {
		String result = "";
		boolean memberInfoMgmtYn = LoginManager.isPersonalInfoManager();

		if (memberInfoMgmtYn) {
			result = getRcvrEmailAddrText();
		} else {
			result = UtilsMasking.emailAddress(getRcvrEmailAddrText());
		}

		return result;
	}

	// 받는사람 이메일 마스킹[그리드]
	public String getGridRcvrEmailInfo() {
		String result = "";
		result = UtilsMasking.emailAddress(getRcvrEmailAddrText());

		return result;
	}

}
