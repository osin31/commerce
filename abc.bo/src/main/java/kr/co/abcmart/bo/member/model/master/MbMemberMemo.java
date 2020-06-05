package kr.co.abcmart.bo.member.model.master;

import kr.co.abcmart.bo.member.model.master.base.BaseMbMemberMemo;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MbMemberMemo extends BaseMbMemberMemo {

	private String loginId;

	private String adminName;

	public String getDpRgsterName() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsText.concat(UtilsMasking.loginId(this.loginId), Const.L_PAREN,
					UtilsMasking.userName(this.adminName), Const.R_PAREN);
		} else {
			maskingStr = UtilsText.concat(this.loginId, Const.L_PAREN, this.adminName, Const.R_PAREN);
		}
		return maskingStr;
	}

}
