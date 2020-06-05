package kr.co.abcmart.bo.system.model.master;

import kr.co.abcmart.bo.system.model.master.base.BaseSyMenu;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SyMenu extends BaseSyMenu {

	private String status;
	private String haveChild;
	private String level;
	private String rgsterName;
	private String moderName;
	private String rgsterId;
	private String moderId;

	public String getRgsterName() {
		String rgsterName = "";

		if (LoginManager.isPersonalInfoManager()) {
			rgsterName = UtilsText.concat(this.rgsterId, Const.L_PAREN, this.rgsterName, Const.R_PAREN);
		} else {
			rgsterName = UtilsText.concat(UtilsMasking.loginId(this.rgsterId), Const.L_PAREN,
					UtilsMasking.userName(this.rgsterName), Const.R_PAREN);
		}

		return rgsterName;
	}

	public String getModerName() {
		String moderName = "";

		if (LoginManager.isPersonalInfoManager()) {
			moderName = UtilsText.concat(this.moderId, Const.L_PAREN, this.moderName, Const.R_PAREN);
		} else {
			moderName = UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
					UtilsMasking.userName(this.moderName), Const.R_PAREN);
		}

		return moderName;
	}

}
