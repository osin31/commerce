package kr.co.abcmart.bo.system.model.master;

import java.util.List;

import kr.co.abcmart.bo.system.model.master.base.BaseSyAdmin;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SyAdmin extends BaseSyAdmin {

	private String authNo;

	private String authName;

	private String authApplySystemType;

	private String siteNo;

	private String siteName;

	private String accessIpText;

	private String rgstLoginId;

	private String rgstAdminName;

	private String modLoginId;

	private String modAdminName;

	private String status;

	private String pswdChangeYn;

	private String viewType;

	private List<SyAdminAuthority> adminAuthorities;

	private String loginFailIncrease;

	private String loginFailInit;

	private String[] adminNos;

	private String[] siteNos;

	private String[] accessIpTexts;

	private String storeName;

	private String searchKey;

	private String searchValue;

	private String mailSendPswd;

	private String mailSendCode;

	private String searchType; // 통계성 페이지의 관리지 셀렉트 박스의 정렬을 위한 변수

	public String getListHdphnNoText() {
		return UtilsText.concat(UtilsMasking.cellPhoneNumber(getHdphnNoText()));
	}

	public String getListEmail() {
		return UtilsText.concat(UtilsMasking.emailAddress(getEmailAddrText()));
	}

	public String getListRgstName() {
		return UtilsText.concat(UtilsMasking.loginId(getRgstLoginId()), Const.L_PAREN,
				UtilsMasking.userName(getRgstAdminName()), Const.R_PAREN);
	}

	public String getListDisplayName() {
		return UtilsText.concat(UtilsMasking.loginId(getLoginId()), Const.L_PAREN,
				UtilsMasking.userName(getAdminName()), Const.R_PAREN);
	}

	public String getRgstName() {
		if (LoginManager.isPersonalInfoManager()) {
			return UtilsText.concat(getRgstLoginId(), Const.L_PAREN, getRgstAdminName(), Const.R_PAREN);
		} else {
			return UtilsText.concat(UtilsMasking.loginId(getRgstLoginId()), Const.L_PAREN,
					UtilsMasking.userName(getRgstAdminName()), Const.R_PAREN);
		}
	}

	public String getModerName() {
		if (LoginManager.isPersonalInfoManager()) {
			return UtilsText.concat(getModLoginId(), Const.L_PAREN, getModAdminName(), Const.R_PAREN);
		} else {
			return UtilsText.concat(UtilsMasking.loginId(getModLoginId()), Const.L_PAREN,
					UtilsMasking.userName(getModAdminName()), Const.R_PAREN);
		}
	}

	public String getAdminInfo() {
		if (LoginManager.isPersonalInfoManager()) {
			return UtilsText.concat(getLoginId(), Const.L_PAREN, getAdminName(), Const.R_PAREN);
		} else {
			return UtilsText.concat(UtilsMasking.loginId(getLoginId()), Const.L_PAREN,
					UtilsMasking.userName(getAdminName()), Const.R_PAREN);
		}
	}

	public String getListAconnectName() {
		return UtilsMasking.userName(getAdminName());
	}

	public String getListAconnectId() {
		return UtilsMasking.userName(getLoginId());
	}

	public String getLoginIdDetail() {
		if (LoginManager.isPersonalInfoManager()) {
			return getLoginId();
		} else {
			return UtilsMasking.loginId(getLoginId());
		}
	}

}
