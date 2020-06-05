package kr.co.abcmart.bo.cmm.model.master;

import kr.co.abcmart.bo.cmm.model.master.base.BaseCmMessageSendHistory;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CmMessageSendHistory extends BaseCmMessageSendHistory {
	private String[] arrSendYn;	//발송,미발송 
	private String rcvrInfo;	//받는 사람 정보
	private String fromDate;	// 검색 시작날짜
	private String toDate;		// 검색 종료날짜
	private String rcvrSearchType;	// 검색 타입
	private String dateSearchType;	// 날짜 검색 타입
	private String rcvrSearchText;	// 받는사람 관련 검색어
	private String siteName;	// 사이트명
	private String adminId;	// 관리자ID(ex:sysdev)
	private String adminName;	// 관리자명
	private String loginId;	// 회원 로그인ID
	private String leaveYn;	// 회원 탈퇴여부(Y/N)
	private String adminSendYnName; // 분류[관리자/시스템]
	
	// 관리자 정보 마스킹
	public String getAdminInfo() {
		String result = "";
		boolean memberInfoMgmtYn = LoginManager.isPersonalInfoManager();
		
		if(getAdminId() == null ) {
			if(memberInfoMgmtYn) {
				result = "system";
			}else {
				result = UtilsMasking.loginId("system");
			}
		}else {
			if(memberInfoMgmtYn) {
				result = UtilsText.concat(getAdminId(), Const.L_PAREN, getAdminName(), Const.R_PAREN);				
			}else {
				result = UtilsText.concat(UtilsMasking.loginId(getAdminId()), Const.L_PAREN
						,UtilsMasking.userName(getAdminName()), Const.R_PAREN);
			}
		}
		return result;
	}
	
	// 관리자 정보 마스킹[그리드]
	public String getGridAdminInfo() {
		String result = "";
		
		if (getAdminId() == null) {
			result = UtilsMasking.loginId("system");
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
		
		if(getLoginId() == null ) {	// 비회원이거나 탈퇴한 경우는 login를 보여줄 수 없으므로 수신자명만 암호화해서 보여줌
			if(memberInfoMgmtYn) {
				result = getRcvrName();
			}else {
				result = UtilsMasking.userName(getRcvrName());
			}
		}else {
			if(memberInfoMgmtYn) {
				result = UtilsText.concat(getLoginId(), Const.L_PAREN+getRcvrName(), Const.R_PAREN);
			}else {
				result = UtilsText.concat(UtilsMasking.loginId(getLoginId()), Const.L_PAREN
										 ,UtilsMasking.userName(getRcvrName()), Const.R_PAREN);
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
	
	// 휴대폰 번호 마스킹[그리드]
	public String getGridRecvTelNoTextInfo() {
		String result = "";
		
		result = UtilsMasking.cellPhoneNumber(getRecvTelNoText());
		return result;
	}
}
