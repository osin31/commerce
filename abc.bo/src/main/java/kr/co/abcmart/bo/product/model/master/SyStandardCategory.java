package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.display.model.master.DpCategory;
import kr.co.abcmart.bo.product.model.master.base.BaseSyStandardCategory;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.common.util.UtilsMasking;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SyStandardCategory extends BaseSyStandardCategory implements Validator {

	private String upStdCtgrName;

	private String level;

	private String status;

	private String noPath;

	private String vndrNo;

	/* 관리자 정보 */
	private String rgsterName;
	private String rgsterId;
	private String rgsterInfo;

	private String moderName;
	private String moderId;
	private String moderInfo;

	@ParameterOption(target = "handlPrecauSeq")
	private SyHandlingPrecaution[] syHandlingPrecautionList;

	private String[] removeSeqs;

	private String isMasterPage;

	/** 전시 카테고리 목록 */
	@ParameterOption(target = "divider")
	private DpCategory[] displayCategoryList;

	// 등록자 정보 마스킹 처리
	public String getRgsterInfo() {
		return UtilsText.concat(UtilsMasking.loginId(getRgsterId()), Const.L_PAREN,
				UtilsMasking.userName(getRgsterName()), Const.R_PAREN);
	}

	// 수정자 정보 마스킹 처리
	public String getModerInfo() {
		return UtilsText.concat(UtilsMasking.loginId(getModerId()), Const.L_PAREN,
				UtilsMasking.userName(getModerName()), Const.R_PAREN);
	}
	/* 관리자 정보 */

	@Override
	public void validate() throws ValidatorException {

		// 표준 카테고리명
		if (UtilsText.isBlank(getStdCtgrName())) {
			validationMessage("product.valid.standardCategory.stdCtgrName");
		}

		// 표준 카테고리명 length (임의)
		if (UtilsText.length(getStdCtgrName()) > 100) {
			validationMessage("product.valid.standardCategory.stdCtgrName.length");
		}

	}

}
