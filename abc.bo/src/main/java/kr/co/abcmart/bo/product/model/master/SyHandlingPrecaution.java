package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.product.model.master.base.BaseSyHandlingPrecaution;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SyHandlingPrecaution extends BaseSyHandlingPrecaution implements Validator {

	/**
	 * Leaf 카테고리 여부
	 */
	private String leafCtgrYn;

	/**
	 * 카테고리 Level
	 */
	private String ctgrLevel;

	@Override
	public void validate() throws ValidatorException {

		// 소재명
		if (UtilsText.isBlank(getTitleText())) {
			validationMessage("product.valid.handlingPreCaution.titleText");
		}

		// 취급시 주의사항
		if (UtilsText.isBlank(getContText())) {
			validationMessage("product.valid.handlingPreCaution.contText");
		}
	}

}
