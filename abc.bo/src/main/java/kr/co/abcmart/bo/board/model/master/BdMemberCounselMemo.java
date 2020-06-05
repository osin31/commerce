package kr.co.abcmart.bo.board.model.master;

import kr.co.abcmart.bo.board.model.master.base.BaseBdMemberCounselMemo;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;

@Data
public class BdMemberCounselMemo extends BaseBdMemberCounselMemo implements Validator {

	private static final long serialVersionUID = 1L;

	/**
	 * 관리자 로그인 ID
	 */
	private String loginId;

	private String escalationYn;

	/**
	 * 관리자 이름
	 */
	private String adminName;

	private String rgsterDpName;

	public String getRgsterDpName() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsText.concat(UtilsMasking.loginId(this.loginId), Const.L_PAREN,
					UtilsMasking.userName(this.adminName), Const.R_PAREN);
		} else {
			maskingStr = UtilsText.concat(this.loginId, Const.L_PAREN, this.adminName, Const.R_PAREN);
		}
		return maskingStr;
	}

	@Override
	public void validate() throws ValidatorException {

	}

}
