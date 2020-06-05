package kr.co.abcmart.bo.system.model.master;

import kr.co.abcmart.bo.system.model.master.base.BaseSyAuthority;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SyAuthority extends BaseSyAuthority {

	private String adminCount;
	private String authMenuCount;
	private String rgsterName;
	private String status;
	private String loginId;

	public String getRgsterName() {
		return UtilsText.concat(UtilsMasking.loginId(this.loginId), Const.L_PAREN,
				UtilsMasking.userName(this.rgsterName), Const.R_PAREN);
	}

}
