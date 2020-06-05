package kr.co.abcmart.bo.cmm.model.master;

import kr.co.abcmart.bo.cmm.model.master.base.BaseCmCounselScript;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.message.Message;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;

@Data
public class CmCounselScript extends BaseCmCounselScript implements Validator {

	private static final long serialVersionUID = 1L;

	// 작성자 명
	private String rgsterName;

	// 작성자 id
	private String rgsterId;

	// 수정자 명
	private String moderName;

	// 수정자 id
	private String moderId;

	// 상담 메뉴 코드 명
	private String cnslGbnCodeName;

	// 상담유형 코드 명
	private String cnslTypeCodeName;

	// 상담유형상세코드 명
	private String cnslTypeDtlCodeName;

	/**
	 * @Desc : 마스킹된 등록자 정보(관리자ID(관리자명))
	 * @Method Name : getMaskingRgsterName
	 * @Date : 2019. 2. 25.
	 * @Author : kiowa
	 * @return
	 */
	public String getMaskingRgsterName() {
		return UtilsText.concat(UtilsMasking.loginId(this.rgsterId), Const.L_PAREN,
				UtilsMasking.userName(this.rgsterName), Const.R_PAREN);
	}

	/**
	 * @Desc : 마스킹된 수정자 정보(관리자ID(관리자명))
	 * @Method Name : getMaskingModerName
	 * @Date : 2019. 2. 25.
	 * @Author : kiowa
	 * @return
	 */
	/*
	 * public String getMaskingModerName() { return
	 * UtilsText.concat(UtilsMasking.masking(this.moderId), Const.L_PAREN,
	 * UtilsMasking.masking(this.moderName), Const.R_PAREN); }
	 */

	/**
	 * @Desc : 등록자 정보(관리자ID(관리자명))
	 * @Method Name : getMaskingRgsterName
	 * @Date : 2019. 2. 25.
	 * @Author : kiowa
	 * @return
	 */
	public String getDispRgsterName() {
		return UtilsText.concat(this.rgsterId, Const.L_PAREN, this.rgsterName, Const.R_PAREN);
	}

	/**
	 * @Desc : 수정자 정보(관리자ID(관리자명))
	 * @Method Name : getMaskingModerName
	 * @Date : 2019. 2. 25.
	 * @Author : kiowa
	 * @return
	 */
	public String getDispModerName() {
		return UtilsText.concat(this.moderId, Const.L_PAREN, this.moderName, Const.R_PAREN);
	}

	public String getMaskingDispModerName() {
		return UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
				UtilsMasking.userName(this.moderName), Const.R_PAREN);
	}

	/**
	 * @Desc : 권한에 따른 카스킹 처리
	 * @Method Name : getAuthRgsterMasking
	 * @Date : 2019. 6. 14.
	 * @Author : 신인철
	 * @return
	 */
	public String getAuthRgsterMasking() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsText.concat(UtilsMasking.loginId(this.rgsterId), Const.L_PAREN,
					UtilsMasking.userName(this.rgsterName), Const.R_PAREN);
		} else {
			maskingStr = UtilsText.concat(this.rgsterId, Const.L_PAREN, this.rgsterName, Const.R_PAREN);
		}
		return maskingStr;
	}

	/**
	 * @Desc : 권한에 따른 카스킹 처리
	 * @Method Name : getAuthRgsterMasking
	 * @Date : 2019. 6. 14.
	 * @Author : 신인철
	 * @return
	 */
	public String getAuthModerMasking() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsText.concat(UtilsMasking.loginId(this.moderId), Const.L_PAREN,
					UtilsMasking.userName(this.moderName), Const.R_PAREN);
		} else {
			maskingStr = UtilsText.concat(this.moderId, Const.L_PAREN, this.moderName, Const.R_PAREN);
		}
		return maskingStr;
	}

	@Override
	public void validate() throws ValidatorException {
		// 상담유형코드 Null 체크
		if (UtilsText.isBlank(getCnslScriptSeq()) && UtilsText.isBlank(getCnslGbnCode())) {
			validationMessage("common.valid.variableisnull", Message.getMessage("cs.msg.cnslGbnCode"));
		}
		// 상담유형코드 Null 체크
		if (UtilsText.isBlank(getCnslScriptSeq()) && UtilsText.isBlank(getCnslTypeCode())) {
			validationMessage("common.valid.variableisnull", Message.getMessage("cs.msg.cnsltypecode"));
		}
		// 상담유형상세코드 Null 체크
		if (UtilsText.isBlank(getCnslScriptSeq()) && UtilsText.isBlank(getCnslTypeDtlCode())) {
			validationMessage("common.valid.variableisnull", Message.getMessage("cs.msg.cnsltypedtlcode"));
		}
		// 상담스크립트제목 Null 체크
		if (UtilsText.isBlank(getCnslScriptTitleText())) {
			validationMessage("common.valid.variableisnull", Message.getMessage("cs.msg.cnslscripttitletext"));
		}
		if (UtilsText.isBlank(getPointPayYn())) {
			setPointPayYn(Const.BOOLEAN_FALSE);
		}

		if (CommonCode.ASWR_ALERT_TYPE_NON.equals(getAswrAlertType())) {
			setAswrAlertId(null);
		}
		// 사용여부 Null 체크
		if (UtilsText.isBlank(getUseYn())) {
			validationMessage("common.valid.variableisnull", Message.getMessage("cs.msg.useyn"));
		}
	}

}
