package kr.co.abcmart.bo.cmm.model.master;

import kr.co.abcmart.bo.cmm.model.master.base.BaseCmTerms;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CmTerms extends BaseCmTerms {

	private String termsCodeName;

	private String moderId;

	private String moderName;

	private String moderDetailDpName;

	private String directChange;

	private String liveDisp;

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

}
