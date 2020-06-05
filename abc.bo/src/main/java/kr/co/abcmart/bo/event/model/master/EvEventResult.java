package kr.co.abcmart.bo.event.model.master;

import kr.co.abcmart.bo.event.model.master.base.BaseEvEventResult;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class EvEventResult extends BaseEvEventResult implements Validator {
	private String eventName;

	private String eventTypeCode;

	private String eventTypeCodeName;

	private String deviceCodeName;

	private String chnnlName;

	private String winCount;

	private java.sql.Timestamp eventStartDtm;

	private java.sql.Timestamp eventEndDtm;

	@ParameterOption(target = "benefitName")
	private EvEventResultBenefit[] evEventResultBenefitArr;

	@ParameterOption(target = "memberNo")
	private EvEventResultBenefitMember[] evEventResultBenefitMemberArr;

	private String przwrPblcTodoYmd;

	/* 관리자 정보 */
	private String rgsterName;
	private String rgsterId;

	private String moderName;
	private String moderId;

	private String pageType;

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

	public String getEventResultName() {
		String eventResultName = "";

		if (UtilsText.isNotBlank(this.getEventName())) {
			eventResultName = this.getEventName() + " 당첨자 안내";
		}

		return eventResultName;
	}

	@Override
	public void validate() throws ValidatorException {
		if (UtilsText.isBlank(this.getEventNo())) {
			validationMessage("promotion.valid.event.eventNo");
		}

		if (UtilsText.isBlank(this.getPblcContText())) {
			validationMessage("promotion.valid.event.pblcContText");
		}

		/*
		 * if (this.getPblcYmd() == null) {
		 * validationMessage("promotion.valid.event.pblcYmd"); }
		 */

		if (this.getEvEventResultBenefitArr() != null) {
			for (EvEventResultBenefit evEventResultBenefit : this.getEvEventResultBenefitArr()) {
				if (UtilsText.isBlank(evEventResultBenefit.getBenefitName())) {
					validationMessage("promotion.valid.event.benefitName");
				}
				if (evEventResultBenefit.getSortSeq() == null) {
					validationMessage("promotion.valid.event.sortSeq");
				}
			}
		}
	}

}
