package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.product.model.master.base.BasePdProductChangeHistory;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PdProductChangeHistory extends BasePdProductChangeHistory {

	/** 등록자아이디 */
	private String rgsterId;
	/** 등록자이름 */
	private String rgsterName;
	/** 등록자이름 */
	private String rgsterInfo;

	// 등록자 정보 마스킹 처리
	public String getRgsterInfo() {
		return UtilsMasking.adminName(this.getRgsterId(), this.getRgsterName());
	}

	/**
	 * @Desc : 개인정보 마스킹 수행
	 * @Method Name : setPrivacy
	 * @Date : 2019. 5. 14.
	 * @Author : tennessee
	 */
	public void setPrivacy() {
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			// 개인정보 접근 권한이 없는 경우
			this.setRgsterId(UtilsMasking.loginId(this.getRgsterId()));
			this.setRgsterName(UtilsMasking.userName(this.getRgsterName()));
		}
	}

}
