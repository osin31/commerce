package kr.co.abcmart.bo.display.model.master;

import kr.co.abcmart.bo.display.model.master.base.BaseDpSearchWord;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.util.UtilsMasking;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DpSearchWord extends BaseDpSearchWord implements Validator {

	/* row status */
	private String status;
	private int delYn;

	/* 검색어 이력 */
	private java.sql.Timestamp applyDtm;
	private String siteName;

	/* 관리자 정보 */
	private String rgsterName;
	private String rgsterId;
	private String rgsterInfo;

	private String moderName;
	private String moderId;
	private String moderInfo;
	private String pageType;

	private String isPageable = Const.BOOLEAN_TRUE;

	// 등록자 정보 마스킹 처리
	public String getRgsterInfo() {

		String maskingStr = "";
		if (UtilsText.equals(this.getPageType(), "L")) {
			maskingStr = UtilsText.concat(UtilsMasking.loginId(this.rgsterId), Const.L_PAREN,
					UtilsMasking.userName(this.rgsterName), Const.R_PAREN);
		} else {
			if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
				maskingStr = UtilsText.concat(UtilsMasking.loginId(this.rgsterId), Const.L_PAREN,
						UtilsMasking.userName(this.rgsterName), Const.R_PAREN);
			} else {
				maskingStr = UtilsText.concat(this.rgsterId, Const.L_PAREN, this.rgsterName, Const.R_PAREN);
			}
		}

		return maskingStr;
	}

	// 수정자 정보 마스킹 처리
	public String getModerInfo() {
		String maskingStr = "";

		if (UtilsText.equals(this.getPageType(), "L")) {
			maskingStr = UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
					UtilsMasking.userName(this.moderName), Const.R_PAREN);
		} else {
			if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
				maskingStr = UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
						UtilsMasking.userName(this.moderName), Const.R_PAREN);
			} else {
				maskingStr = UtilsText.concat(this.moderId, Const.L_PAREN, this.moderName, Const.R_PAREN);
			}
		}
		return maskingStr;
	}
	/* 관리자 정보 */

	@Override
	public void validate() throws ValidatorException {

		// 검색어
		if (UtilsText.isBlank(getSrchWordText())) {
			validationMessage("display.search.valid.srchWordText");
		}
		// 연결유형 == U
		if (UtilsText.equals(getCnnctnType(), "U")) {
			// 연결url
			if (UtilsText.isBlank(getCnnctnUrl())) {
				validationMessage("display.search.valid.cnnctnUrl");
			}
		}
		// 구분타입 != S
		if (UtilsText.equals(getSrchWordGbnType(), "S")) {
			// 정렬순번
			if (getSortSeq() == null) {
				validationMessage("display.search.valid.sortSeq");
			}
		}
	}

}
