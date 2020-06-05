package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.product.model.master.base.BaseDpSizeChart;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DpSizeChart extends BaseDpSizeChart implements Validator {

	private FileUpload imageFile;

	private String brandName;

	private String stdCtgrName;

	private String vndrNo;

	/* 관리자 정보 */
	private String rgsterName;
	private String rgsterId;

	private String moderName;
	private String moderId;

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

	@Override
	public void validate() throws ValidatorException {

		if (UtilsText.isBlank(getSizeChartName())) {
			validationMessage("product.valid.sizechart.sizeChartName");
		}
		if (UtilsText.length(getSizeChartName()) > 100) {
			validationMessage("product.valid.sizechart.sizeChartName.length");
		}

		if (UtilsText.isBlank(getStdCtgrNo())) {
			validationMessage("product.valid.sizechart.stdCtgrNo");
		}

		// 적용브랜드
		if (UtilsText.equals(getBrandAssignYn(), Const.BOOLEAN_TRUE)) {
			if (UtilsText.isBlank(getBrandNo())) {
				validationMessage("product.valid.sizechart.brandNo");
			}
		}

		// 이미지
		if (getSizeChartSeq() == null && getImageFile() == null) {
			validationMessage("product.valid.sizechart.image");
		}
		if (getSizeChartSeq() != null && UtilsText.isBlank(getImageName())) {
			validationMessage("product.valid.sizechart.image");
		}
		if (UtilsText.length(getAltrnText()) > 100) {
			validationMessage("product.valid.sizechart.altrnText.length");

		}
	}
}
