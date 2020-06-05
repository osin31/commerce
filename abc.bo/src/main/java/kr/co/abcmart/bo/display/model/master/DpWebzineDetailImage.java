package kr.co.abcmart.bo.display.model.master;

import kr.co.abcmart.bo.display.model.master.base.BaseDpWebzineDetailImage;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DpWebzineDetailImage extends BaseDpWebzineDetailImage implements Validator {

	private FileUpload addImageFile;

	@Override
	public void validate() throws ValidatorException {

		if (this.getSortSeq() == null) {
			validationMessage("display.contents.valid.sortSeq");
		}

		// MO 썸네일
		if (UtilsText.isBlank(getImageName())) {
			validationMessage("display.contents.valid.add.image");
		} else {
			if (this.getAddImageFile() != null) {
				if (this.getAddImageFile().isFileItem()
						&& !this.getAddImageFile().isAllowExtAndMimeType("jpg", "gif", "png")) {
					validationMessage("display.contents.valid.image.type");
				}
			}
		}
	}

}
