package kr.co.abcmart.bo.display.model.master;

import kr.co.abcmart.bo.display.model.master.base.BaseDpWebzine;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DpWebzine extends BaseDpWebzine implements Validator {

	private FileUpload pcImageFile;

	private FileUpload mobileImageFile;

	private FileUpload movieFile;

	@ParameterOption(target = "sortSeq")
	private DpWebzineDetailImage[] dpWebzineDetailImage;

	private java.lang.Short[] changedImageSeq;

	@ParameterOption(target = "prdtNo")
	private DpWebzineProduct[] dpWebzineProduct;

	private String dispStartYmdDot;

	private String wbznTypeName;

	private String dispYnName;

	/* 관리자 정보 */
	private String rgsterName;
	private String rgsterId;
	private String rgsterInfo;

	private String moderName;
	private String moderId;
	private String moderInfo;

	private FileUpload upload;

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

	public String getWbznTypeName() {

		if (UtilsText.isNotEmpty(this.getWbznType())) {
			switch (this.getWbznType()) {
			case "C":
				return "CATALOGUE";
			case "M":
				return "STAFF PICK";
			case "N":
				return "A-RT NEWS";
			case "O":
				return "MAGAZINE";
			case "S":
				return "STYLING";
			case "E":
				return "EXCLUSIVE";
			default:
				return "";
			}
		} else {
			return null;
		}
	}

	public String getDispYnName() {

		if (UtilsText.isNotEmpty(this.getDispYn())) {
			if (UtilsText.equals(this.getDispYn(), "Y")) {
				return "전시";
			} else {
				return "전시안함";
			}
		} else {
			return null;
		}
	}

	@Override
	public void validate() throws ValidatorException {

		// 제목
		if (UtilsText.isBlank(getWbznTitleText())) {
			validationMessage("display.contents.valid.wbznTitleText");
		}
		// 전시여부
		if (UtilsText.isBlank(getDispYn())) {
			validationMessage("display.contents.valid.dispYn");
		}
		// 전시 일 경우 전시일시
		if (UtilsText.equals(getDispYn(), "Y")) {
			if (UtilsText.isBlank(getDispStartYmdDot())) {
				validationMessage("display.contents.valid.dispStartYmdDot");
			}
		}
		// PC 썸네일
		if (UtilsText.isBlank(getPcImageName())) {
			validationMessage("display.contents.valid.image");
		} else {
			if (this.getPcImageFile() != null) {
				if (this.getPcImageFile().isFileItem()
						&& !this.getPcImageFile().isAllowExtAndMimeType("jpg", "gif", "jpeg", "png", "bmp")) {
					validationMessage("display.contents.valid.image.type");
				}
			}
		}

		// MO 썸네일
		if (UtilsText.isBlank(getMobileImageName())) {
			validationMessage("display.contents.valid.image");
		} else {
			if (this.getMobileImageFile() != null) {
				if (this.getMobileImageFile().isFileItem()
						&& !this.getMobileImageFile().isAllowExtAndMimeType("jpg", "gif", "jpeg", "png", "bmp")) {
					validationMessage("display.contents.valid.image.type");
				}
			}
		}
		// 웹진유형
		if (UtilsText.isBlank(getWbznType())) {
			validationMessage("display.contents.valid.wbznType");
		}

		if (UtilsText.equals(getWbznType(), Const.WEBZINE_TYPE_NEWS)) {
			// 본문
			if (UtilsText.isBlank(getWbznInfo())) {
				validationMessage("display.contents.valid.wbznInfo");
			}
		}

		if (UtilsText.equals(getWbznType(), Const.WEBZINE_TYPE_CATALOGUE)) {
//			// 발행호
//			if (getWbznNo() == null || getWbznNo() < 1) {
//				validationMessage("display.contents.valid.wbznNo");
//			}
//
			if (this.getDpWebzineDetailImage() != null && this.getDpWebzineDetailImage().length > 0) {

				for (DpWebzineDetailImage dpWebzineDetailImage : this.getDpWebzineDetailImage()) {
					dpWebzineDetailImage.validate();
				}

			}
//
		}

		if (UtilsText.equals(getWbznType(), Const.WEBZINE_TYPE_MOVIE)) {

			if (UtilsText.equals(getMovieUploadYn(), "Y")) {
				if (UtilsText.isBlank(getMovieName())) {
					validationMessage("display.contents.valid.movie");
				} else {
					// 확장자 검사
					if (this.getMovieFile() != null) {
						if (this.getMovieFile().isAllowExtAndMimeType("mp4", "video/mp4")) {
							validationMessage("display.contents.valid.movie.type");
						}
					}
				}
			} else {
				if (UtilsText.isBlank(getMovieUrl())) {
					validationMessage("common.valid.variableisnull");
				}
			}
		}
	}
}
