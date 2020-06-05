package kr.co.abcmart.bo.display.model.master;

import kr.co.abcmart.bo.display.model.master.base.BaseDpDisplayTemplate;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DpDisplayTemplate extends BaseDpDisplayTemplate implements Validator {

	//////////// 조회
	// @ParameterOption(target = "cornerName")
	private DpDisplayTemplateCorner[] corner;

	private FileUpload imageFile;

	//////////// 조회

	private String siteName;

	private String chnnlName;

	private String deviceName;

	private String tmplTypeName;

	private String useYnName;

	//////////// 검색조건

	private String[] tmplTypeCodeArr;

	private String tmplTypeCodeAll;

	/** 검색 타입 */
	private String keywordType;

	/** 검색어 */
	private String keyword;

	private String callback;

	private String menuType;

	/* 관리자 정보 */
	private String rgsterName;
	private String rgsterId;
	private String rgsterInfo;

	private String moderName;
	private String moderId;
	private String moderInfo;

	// 등록자 정보 마스킹 처리
	public String getRgsterInfo() {

		if (!UtilsText.isBlank(getRgsterId())) {
			return UtilsText.concat(UtilsMasking.loginId(getRgsterId()), Const.L_PAREN,
					UtilsMasking.userName(getRgsterName()), Const.R_PAREN);
		} else {
			return "";
		}

	}

	// 수정자 정보 마스킹 처리
	public String getModerInfo() {

		if (!UtilsText.isBlank(getModerId())) {
			return UtilsText.concat(UtilsMasking.loginId(getModerId()), Const.L_PAREN,
					UtilsMasking.userName(getModerName()), Const.R_PAREN);
		} else {
			return "";
		}
	}
	/* 관리자 정보 */

	public String getUseYnName() {

		if (UtilsText.equals(getUseYn(), "Y")) {
			return "사용";
		} else {
			return "사용안함";
		}
	}

	@Override
	public void validate() throws ValidatorException {

		// 템플릿 명
		if (UtilsText.isBlank(getTmplName())) {
			validationMessage("display.template.valid.tmplName");
		}

		// 템플릿 유형 코드
		if (UtilsText.isBlank(getTmplTypeCode())) {
			validationMessage("display.template.valid.tmplTypeCde");
		}
		// 사이트
		if (UtilsText.isBlank(getSiteNo())) {
			validationMessage("display.template.valid.siteNo");
		}
		// 채널
		if (UtilsText.isBlank(getChnnlNo())) {
			validationMessage("display.template.valid.chnnlNo");
		}
		// 디바이스
		if (UtilsText.isBlank(getDeviceCode())) {
			validationMessage("display.template.valid.deviceCode");
		}
		// 사용여부
		if (UtilsText.isBlank(getUseYn())) {
			validationMessage("display.template.valid.useYn");
		}
		// 템플릿 url
		if (UtilsText.isBlank(getTmplUrl())) {
			validationMessage("display.template.valid.tmplUrl");
		} /*
			 * else { String regex = "/([a-z0-9\\w]+\\.*)+[a-z0-9]{1,5}/gi"; if
			 * (!getTmplUrl().matches(regex)) {
			 * validationMessage("display.template.valid.tmplUrl.regex"); } }
			 */

		if (corner != null) {
			for (DpDisplayTemplateCorner c : corner) {
				c.validate();
			}
		} else {
//			validationMessage("display.template.valid.corner");
		}
	}
}
