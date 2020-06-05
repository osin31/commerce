package kr.co.abcmart.bo.display.model.master;

import kr.co.abcmart.bo.display.model.master.base.BaseDpDisplayPageCornerName;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DpDisplayPageCornerName extends BaseDpDisplayPageCornerName implements Validator {

	private FileUpload imageUpload;

	private String cornerNameDispType;

	private String status;

	@Override
	public void validate() throws ValidatorException {

		// 전시여부
		if (UtilsText.isBlank(getDispYn())) {
			validationMessage("display.contents.valid.name.dispYn");
		}

		// 전시일때 전시일시
		if (UtilsText.equals(getDispYn(), "Y")) {
			if (getDispStartYmd() == null) {
				validationMessage("display.contents.valid.name.dispStartYmd");
			}

			if (getDispEndYmd() == null) {
				validationMessage("display.contents.valid.name.dispEndYmd");
			}
		}

		// 코너명
		if (UtilsText.isBlank(getDispCornerName())) {
			validationMessage("display.contents.valid.name.dispCornerName");
		}
		// 코너명 타입 이미지일 경우
		if (UtilsText.equals(getCornerNameDispType(), Const.DISPLAY_CORNER_NAME_IMAGE)) {
			// 이미지
			if (UtilsText.isBlank(getImageName())) {
				validationMessage("display.contents.valid.name.imageName");
			}
		}
	}

}
