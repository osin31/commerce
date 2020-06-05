package kr.co.abcmart.bo.display.model.master;

import kr.co.abcmart.bo.display.model.master.base.BaseDpDisplayPageCornerSet;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DpDisplayPageCornerSet extends BaseDpDisplayPageCornerSet implements Validator {
	private FileUpload imageUpload;

	private FileUpload movieUpload;

	private String status;

	private String addInfo8Name;

	private String imageUrl;

	public String getAddInfo8Name() {

		if (!UtilsText.isBlank(getContTypeCode())) {
			if (UtilsText.equals(getContTypeCode(), "10002")) {
				return UtilsText.equals(getAddInfo8(), "N") ? "선택안함"
						: UtilsText.equals(getAddInfo8(), "B") ? "블랙" : "화이트";
			}
		}

		return "";
	}

	@Override
	public void validate() throws ValidatorException {

		if (UtilsText.isBlank(getDispYn())) {
			validationMessage("display.contents.valid.set.dispYn");
		}

		// 전시일때 전시일시
		if (UtilsText.equals(getDispYn(), "Y")) {
			if (getDispStartYmd() == null) {
				validationMessage("display.contents.valid.set.dispStartYmd");
			}

			if (getDispEndYmd() == null) {
				validationMessage("display.contents.valid.set.dispEndYmd");
			}
		}

		if (getSortSeq() == null) {
			validationMessage("display.contents.valid.set.sortSeq");
		}

		switch (getContTypeCode()) {
		case "10000":
			// 전시세트명
			if (UtilsText.isBlank(getAddInfo1())) {
				validationMessage("display.contents.valid.set.displaySetName");
			}
			break;
		case "10002":
			// 제목
			if (UtilsText.isBlank(getAddInfo1())) {
				validationMessage("display.contents.valid.set.title");
			}
			// 이미지
			if (UtilsText.isBlank(getAddInfo2())) {
				validationMessage("display.contents.valid.set.imageName");
			} else {
				if (this.getAddInfo2() != null) {
					if (this.getImageUpload() != null && this.getImageUpload().isFileItem()
							&& !this.getImageUpload().isAllowExtAndMimeType("jpg", "gif", "jpeg", "png", "bmp", "JPG",
									"GIF", "JPEG", "PNG", "BMP")) {
						validationMessage("display.contents.valid.image.type");
					}
				}
			}
			// 연결방법이 전시안함이 아닐 경우
			if (!UtilsText.equals(getAddInfo5(), "N")) {
				// 랜딩 URL
				if (UtilsText.isBlank(getAddInfo7())) {
					validationMessage("display.contents.valid.set.landingUrl");
				}
			}
			// 텍스트 컬러
			if (UtilsText.isBlank(getAddInfo8())) {
				validationMessage("display.contents.valid.set.textColor");
			}

			break;
		case "10003":
			// 제목
			if (UtilsText.isBlank(getAddInfo1())) {
				validationMessage("display.contents.valid.set.title");
			}
			// 이미지
			if (UtilsText.isBlank(getAddInfo2())) {
				validationMessage("display.contents.valid.set.imageName");
			} else {
				if (this.getAddInfo2() != null) {
					if (this.getImageUpload() != null && this.getImageUpload().isFileItem()
							&& !this.getImageUpload().isAllowExtAndMimeType("jpg", "gif", "jpeg", "png", "bmp", "JPG",
									"GIF", "JPEG", "PNG", "BMP")) {
						validationMessage("display.contents.valid.image.type");
					}
				}
			}
			// 연결방법이 URL일 경우
			if (UtilsText.equals(getAddInfo5(), "U")) {
				// URL
				if (UtilsText.isBlank(getAddInfo9())) {
					validationMessage("display.contents.valid.set.url");
				}
			} else {
				// 동영상
				if (UtilsText.isBlank(getAddInfo7())) {
					validationMessage("display.contents.valid.set.movie");
				}
			}
			break;
		case "10004":
			// 텍스트
			if (UtilsText.isBlank(getAddInfo1())) {
				validationMessage("display.contents.valid.set.title");
			}
			// 연결방법이 URL일 경우
			if (UtilsText.equals(getAddInfo2(), "U")) {
				// URL
				if (UtilsText.isBlank(getAddInfo4())) {
					validationMessage("display.contents.valid.set.url");
				}
			}
			break;
		case "10005":
			// 제목
			if (UtilsText.isBlank(getAddInfo1())) {
				validationMessage("display.contents.valid.set.title");
			}
			// html
			if (UtilsText.isBlank(getAddInfo10())) {
				validationMessage("display.contents.valid.set.html");
			}
			break;
		default:
			break;
		}
	}
}
