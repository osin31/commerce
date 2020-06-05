package kr.co.abcmart.bo.system.model.master;

import kr.co.abcmart.bo.system.model.master.base.BaseSyAppVersion;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString
public class SyAppVersion extends BaseSyAppVersion implements Validator {
	private String siteName; // 사이트명
	private String loginId; // 로그인ID
	private String adminName; // 관리자명
	private String appOsCodeField; // APP 버전 공통코드 필드 APP_OS_CODE
	private String codeDtlName; // 버전정보(Android, IOS)

	// 등록자 정보 마스킹 처리
	public String getAdminInfo() {
		if (LoginManager.isPersonalInfoManager()) {
			return UtilsText.concat(this.getLoginId(), Const.L_PAREN, this.getAdminName(), Const.R_PAREN);
		} else {
			return UtilsText.concat(UtilsMasking.loginId(getLoginId()), Const.L_PAREN,
					UtilsMasking.userName(getAdminName()), Const.R_PAREN);
		}
	}

	@Override
	public void validate() throws ValidatorException {
		if (UtilsText.isBlank(getAppOsCode())) {
			validationMessage("system.valid.appversion.apposcode"); // 앱 OS
		} else if (UtilsText.isBlank(getSiteNo())) {
			validationMessage("system.valid.appversion.siteno"); // 사이트 정보
		} else if (getAppRgstYmd() == null) {
			validationMessage("system.valid.appversion.apprgstymd"); // 앱 등록일
		} else if (UtilsText.isBlank(getAppVerText())) {
			validationMessage("system.valid.appversion.appvertext"); // app 버전 정보
		}
	}
}
