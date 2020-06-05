package kr.co.abcmart.bo.board.model.master;

import kr.co.abcmart.bo.board.model.master.base.BaseBdNotice;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BdNotice extends BaseBdNotice {

	private String toDate;

	private String fromDate;

	private String searchKey;

	private String siteName;

	private String searchValue;

	private String rgsterId;

	private String rgsterName;

	private String moderId;

	private String moderName;
	// 상단공지 받아오기
	private String topNotcCheck;

	private int topNotcCount;

	public String getRgsterDpName() {
		return UtilsText.concat(UtilsMasking.loginId(this.rgsterId), Const.L_PAREN,
				UtilsMasking.userName(this.rgsterName), Const.R_PAREN);
	}

	public String getModerDpName() {
		return UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
				UtilsMasking.userName(this.moderName), Const.R_PAREN);
	}

	public String getModerDetailDpName() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
					UtilsMasking.userName(this.moderName), Const.R_PAREN);
		} else {
			maskingStr = UtilsText.concat(this.moderId, Const.L_PAREN, this.moderName, Const.R_PAREN);
		}
		return maskingStr;
	}

	public String getRgsterDetailDpName() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsText.concat(UtilsMasking.loginId(this.rgsterId), Const.L_PAREN,
					UtilsMasking.userName(this.rgsterName), Const.R_PAREN);
		} else {
			maskingStr = UtilsText.concat(this.rgsterId, Const.L_PAREN, this.rgsterName, Const.R_PAREN);
		}
		return maskingStr;
	}

}
