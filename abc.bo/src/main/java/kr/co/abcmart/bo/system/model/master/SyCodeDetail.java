package kr.co.abcmart.bo.system.model.master;

import kr.co.abcmart.bo.system.model.master.base.BaseSyCodeDetail;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;

@Data
public class SyCodeDetail extends BaseSyCodeDetail {

	private String rgsterId;

	private String moderId;

	private String rgsterName;

	private String moderName;

	private String rgsterDpName;

	private String moderDpName;

	private String upCodeDtlName;

	/**
	 * 동일한 codefield중에서 addinfo1 값으로 조회 할 경우 Return
	 */
	private String dispCodeField;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 상위코드상세번호
	 */
	private String upCodeDtlNo;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 코드상세명
	 */
	private String codeDtlName;

	/**
	 * 이 필드는 Code Generator를 통하여 생성 되었습니다. 설명 : 코드상세번호
	 */
	private String codeDtlNo;

	public String getRgsterDpName() {
		return UtilsText.concat(UtilsMasking.loginId(this.rgsterId), Const.L_PAREN,
				UtilsMasking.userName(this.rgsterName), Const.R_PAREN);
	}

	public String getModerDpName() {
		return UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
				UtilsMasking.userName(this.moderName), Const.R_PAREN);
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
