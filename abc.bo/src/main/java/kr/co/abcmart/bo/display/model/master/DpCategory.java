package kr.co.abcmart.bo.display.model.master;

import kr.co.abcmart.bo.display.model.master.base.BaseDpCategory;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DpCategory extends BaseDpCategory implements Validator {

	private FileUpload pcImageFile;
	private FileUpload mobileImageFile;

	private String level;

	private String ctgrNamePath;
	private String ctgrNoPath;
	private String chnnlPath;

	private String siteName;
	private String chnnlName;

	private String pcDispTmplName;
	private String pcPreviewImageUrl;
	private String mobileDispTmplName;
	private String mobilePreviewImageUrl;

	private String deviceType;

	// 업체 코드
	private String vndrNo;

	@Override
	public void validate() throws ValidatorException {

		// 전시카테고리명
		if (UtilsText.isBlank(getCtgrName())) {
			validationFieldMessage("display.category.valid.ctgrName", "ctgrName");
		}
		if (UtilsText.length(getCtgrName()) > 20) {
			validationMessage("display.category.valid.ctgrName.length");
		}
		// 사용여부
		if (UtilsText.isBlank(getUseYn())) {
			validationMessage("display.category.valid.useYn");
		}
		// 전시여부
		if (UtilsText.isBlank(getDispYn())) {
			validationMessage("display.category.valid.dispYn");
		}
		// Leaf Category 여부
		if (UtilsText.isBlank(getLeafCtgrYn())) {
			validationMessage("display.category.valid.leafCtgrYn");
		}
		// 검색필터 사용여부
		if (UtilsText.isBlank(getSrchFilterUseYn())) {
			validationMessage("display.category.valid.srchFilterUseYn");
		}
		// 카테고리명 노출 타입
		if (UtilsText.equals(getCtgrNameDispType(), "I")) {
			// 카테고리명 이미지(PC)
			if (getPcImageFile() == null && getPcImagePathText() == null) {
				validationMessage("display.category.valid.pcImageName");
			}
			// 카테고리명 이미지(MO)
			if (getMobileImageFile() == null && getMobileImagePathText() == null) {
				validationMessage("display.category.valid.mobileImageName");
			}
		}

	}

}
