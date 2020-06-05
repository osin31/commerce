package kr.co.abcmart.bo.display.model.master;

import kr.co.abcmart.bo.display.model.master.base.BaseDpDisplayPage;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DpDisplayPage extends BaseDpDisplayPage implements Validator {

	private String pcDispTmplName;
	private String pcPreviewImageUrl;
	private String mobileDispTmplName;
	private String mobilePreviewImageUrl;
	private String level;

	private String codeDtlNo;
	private String upCodeDtlNo;
	private String codeDtlName;
	private String sortSeq;
	private String addInfo2;

	/* 관리자 정보 */
	private String rgsterName;
	private String rgsterId;
	private String rgsterInfo;

	private String moderName;
	private String moderId;
	private String moderInfo;

	// 등록자 정보 마스킹 처리
	public String getRgsterInfo() {
		return UtilsMasking.adminName(this.getRgsterId(), this.getRgsterName());
	}

	// 수정자 정보 마스킹 처리
	public String getModerInfo() {
		return UtilsMasking.adminName(this.getModerId(), this.getModerName());
	}
	/* 관리자 정보 */

	@Override
	public void validate() throws ValidatorException {

		// 전시페이지명
		if (UtilsText.isBlank(getDispPageName())) {
			validationMessage("display.page.valid.dispPageName");
		}
		if (UtilsText.length(getDispPageName()) > 20) {
			validationMessage("display.page.valid.dispPageName.length");
		}
		// 사용 여부
		if (UtilsText.isBlank(getUseYn())) {
			validationMessage("display.page.valid.useYn");
		}
		// 전시시작일
		if (getDispStartDtm() == null) {
			validationMessage("display.page.valid.dispStartDtm");
		}
		// 전시 여부
		if (UtilsText.isBlank(getDispYn())) {
			validationMessage("display.page.valid.dispYn");
		}
		/*
		 * PC 전시 URL if (UtilsText.isBlank(getPcDispUrl())) {
		 * validationMessage("display.page.valid.pcDispUrl"); } // MOBILE 전시 URL if
		 * (UtilsText.isBlank(getMobileDispUrl())) {
		 * validationMessage("display.page.valid.mobileDispUrl"); }
		 */
		// PC 템플릿 == MO 템플릿
		if (!UtilsText.isBlank(getPcDispTmplNo()) && !UtilsText.isBlank(getMobileDispTmplNo())
				&& UtilsText.equals(getPcDispTmplNo(), getMobileDispTmplNo())) {

			validationMessage("display.category.valid.sameDispTmplNo");
		}
	}

}
