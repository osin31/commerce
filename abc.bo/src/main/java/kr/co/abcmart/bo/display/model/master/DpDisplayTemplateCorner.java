package kr.co.abcmart.bo.display.model.master;

import kr.co.abcmart.bo.display.model.master.base.BaseDpDisplayTemplateCorner;
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
public class DpDisplayTemplateCorner extends BaseDpDisplayTemplateCorner implements Validator {

	@ParameterOption(target = "seq")
	private DpDisplayTemplateCornerSet[] set;

	private String useYnName;

	private String cornerSetName;

	private String cornerNameDispTypeName;

//	private String deviceTypeName;

	private String status;

//	private String ctgrNo;
//
//	private String dispPageNo;

	// 삭제 코너 seq arr
	private Integer[] dispTmplCornerSeqArr;

	/* 관리자 정보 */
	private String rgsterName;
	private String rgsterId;
	private String rgsterInfo;

	private String moderName;
	private String moderId;
	private String moderInfo;

	private String tmplTypeCode;

	private String dispYn;

	// 등록자 정보 마스킹 처리
	public String getRgsterInfo() {
		return UtilsMasking.adminName(this.getRgsterId(), this.getRgsterName());
	}

	// 수정자 정보 마스킹 처리
	public String getModerInfo() {
		return UtilsMasking.adminName(this.getModerId(), this.getModerName());
	}

	// 권한에 따른 마스킹 추가
	public String getAuthRgsterInfo() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsMasking.adminName(this.getRgsterId(), this.getRgsterName());
		} else {
			maskingStr = UtilsText.concat(this.getRgsterId(), Const.L_PAREN, this.getRgsterName(), Const.R_PAREN);
		}
		return maskingStr;
	}

	public String getAuthModerInfo() {
		String maskingStr = "";
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			maskingStr = UtilsMasking.adminName(this.getModerId(), this.getModerName());
		} else {
			maskingStr = UtilsText.concat(this.getModerId(), Const.L_PAREN, this.getModerName(), Const.R_PAREN);
		}
		return maskingStr;
	}
	/* 관리자 정보 */

	public String getUseYnName() {

		if (UtilsText.equals(getUseYn(), "Y")) {
			return "사용";
		} else {
			return "사용안함";
		}
	}

	public String getcornerNameDispTypeName() {

		if (UtilsText.equals(getCornerNameDispType(), "T")) {
			return "텍스트";
		} else if (UtilsText.equals(getCornerNameDispType(), "I")) {
			return "이미지";
		} else if (UtilsText.equals(getCornerNameDispType(), "N")) {
			return "전시안함";
		} else {
			return "";
		}
	}

	@Override
	public void validate() throws ValidatorException {

		// 코너 명
		if (UtilsText.isBlank(getCornerName())) {
			validationMessage("display.template.valid.cornerName");
		}

		// 전시코너명 노출 유형 코드
		if (UtilsText.isBlank(getCornerNameDispType())) {
			validationMessage("display.template.valid.cornerNameDispType");
		}
		// 코너설명
		if (UtilsText.isBlank(getNoteText())) {
			validationMessage("display.template.valid.noteText");
		}
		// 사용여부
		if (UtilsText.isBlank(getUseYn())) {
			validationMessage("display.template.valid.useYn");
		}

		// 노출순서
		if (UtilsText.equals(getTmplTypeCode(), "10003")) {
			if (getSortSeq() == null || getSortSeq() == 0) {
				validationMessage("display.template.valid.sortSeq");
			}
		} else {
			// 메인 이외 나머지 경우 0으로 세팅
			if (getSortSeq() == null || getSortSeq() == 0) {
				this.setSortSeq(0);
			}
		}

		if (set != null) {
			for (DpDisplayTemplateCornerSet s : set) {
				s.validate();
			}
		}
		/*
		 * else { if (getDispTmplCornerSeq() == null)
		 * validationMessage("display.template.valid.set"); }
		 */
	}

}
