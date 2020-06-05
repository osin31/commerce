package kr.co.abcmart.bo.board.model.master;

import kr.co.abcmart.bo.board.model.master.base.BaseBdContactUs;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BdContactUs extends BaseBdContactUs {

	private String memberName;
	private String adminName;
	private String siteName;
	private String aswrStat;
	private String hdphnNoText;
	private String emailAddrText;
	private String searchKey;
	private String searchValue;
	private String toDate;
	private String fromDate;
	private String searchDateKey;
	private String memberNo;
	private String memberLoginId;
	private String aswrStatCode;
	private String adminLoginId;
	private String chnnlName;
	private String cnslTypeDtlCodeName;
	private String memberTypeCodeName;
	private int totalCnt;

	/**
	 * @Desc : 로그인ID
	 * @Method Name : getDispMemberLoginId
	 * @Date : 2019. 3. 8.
	 * @Author : kiowa
	 * @return
	 */
	public String getDispMemberLoginId() {
		String dispMemberLoginId = memberLoginId;
		if (null != dispMemberLoginId) {
			if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
				dispMemberLoginId = UtilsMasking.loginId(dispMemberLoginId);
			}
		}
		return dispMemberLoginId;
	}

	/**
	 * @Desc : 회원명
	 * @Method Name : getDispMemberName
	 * @Date : 2019. 3. 8.
	 * @Author : kiowa
	 * @return
	 */
	public String getDispMemberName() {
		String dispMemberName = memberName;
		if (null != dispMemberName) {
			if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
				dispMemberName = UtilsMasking.userName(dispMemberName);
			}
		}
		return dispMemberName;
	}

	/**
	 * @Desc : 이메일(평문)
	 * @Method Name : getDispEmailAddrText
	 * @Date : 2019. 3. 8.
	 * @Author : kiowa
	 * @return
	 */
	public String getDispEmailAddrText() {
		String dispEmailAddrText = emailAddrText;
		if (null != dispEmailAddrText) {
			if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
				dispEmailAddrText = UtilsMasking.emailAddress(dispEmailAddrText);
			}
		}
		return dispEmailAddrText;
	}

	/**
	 * @Desc : 이메일(마스킹)
	 * @Method Name : getMaskingEmailAddrText
	 * @Date : 2019. 3. 8.
	 * @Author : kiowa
	 * @return
	 */
	public String getMaskingEmailAddrText() {
		String dispEmailAddrText = emailAddrText;
		if (null != dispEmailAddrText) {
			dispEmailAddrText = UtilsMasking.emailAddress(dispEmailAddrText);
		}
		return dispEmailAddrText;
	}

	/**
	 * @Desc : 휴대전화(평문)
	 * @Method Name : getDispHdphnNoText
	 * @Date : 2019. 3. 8.
	 * @Author : kiowa
	 * @return
	 */
	public String getDispHdphnNoText() {
		String dispHdphnNoText = hdphnNoText;
		if (null != dispHdphnNoText) {
			if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
				dispHdphnNoText = UtilsMasking.cellPhoneNumber(dispHdphnNoText);
			}
		}

		return dispHdphnNoText;
	}

	/**
	 * @Desc : 휴대전화(마스킹)
	 * @Method Name : getMaskingHdphnNoText
	 * @Date : 2019. 3. 8.
	 * @Author : kiowa
	 * @return
	 */
	public String getMaskingHdphnNoText() {
		String dispHdphnNoText = hdphnNoText;
		if (null != dispHdphnNoText) {
			dispHdphnNoText = UtilsMasking.cellPhoneNumber(dispHdphnNoText);
		}

		return dispHdphnNoText;
	}

	/**
	 * @Desc : 작성자 정보(평문)
	 * @Method Name : getDisprRsterInfo
	 * @Date : 2019. 3. 8.
	 * @Author : kiowa
	 * @return
	 */
	public String getDisprRsterInfo() {
		String rsterInfo = "";
		if (null != this.memberLoginId || null != this.memberName) {
			if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
				rsterInfo = UtilsText.concat(UtilsMasking.loginId(this.memberLoginId), Const.L_PAREN,
						UtilsMasking.userName(this.memberName), Const.R_PAREN);
			} else {
				rsterInfo = UtilsText.concat(this.memberLoginId, Const.L_PAREN, this.memberName, Const.R_PAREN);
			}
		}

		return rsterInfo;
	}

	/**
	 * @Desc : 작성자 정보(마스킹)
	 * @Method Name : getMaskingRsterInfo
	 * @Date : 2019. 3. 8.
	 * @Author : kiowa
	 * @return
	 */
	public String getMaskingRsterInfo() {
		String rsterInfo = "";
		if (null != this.memberLoginId || null != this.memberName) {
			rsterInfo = UtilsText.concat(UtilsMasking.loginId(this.memberLoginId), Const.L_PAREN,
					UtilsMasking.userName(this.memberName), Const.R_PAREN);
		}
		return rsterInfo;
	}

	/**
	 * @Desc : 답변자 정보(평문)
	 * @Method Name : getDispAswrInfo
	 * @Date : 2019. 3. 8.
	 * @Author : kiowa
	 * @return
	 */
	public String getDispAswrInfo() {
		String rsterInfo = "";

		if (null != this.adminLoginId || null != this.adminName) {
			if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
				rsterInfo = UtilsText.concat(UtilsMasking.loginId(this.adminLoginId), Const.L_PAREN,
						UtilsMasking.userName(this.adminName), Const.R_PAREN);
			} else {
				rsterInfo = UtilsText.concat(this.adminLoginId, Const.L_PAREN, this.adminName, Const.R_PAREN);
			}
		}

		return rsterInfo;
	}

	/**
	 * @Desc : 답변자 정보(마스킹)
	 * @Method Name : getMaskingAswrInfo
	 * @Date : 2019. 3. 8.
	 * @Author : kiowa
	 * @return
	 */
	public String getMaskingAswrInfo() {
		String rsterInfo = "";
		if (null != this.adminLoginId || null != this.adminName) {
			rsterInfo = UtilsText.concat(UtilsMasking.loginId(this.adminLoginId), Const.L_PAREN,
					UtilsMasking.userName(this.adminName), Const.R_PAREN);
		}
		return rsterInfo;
	}

}
