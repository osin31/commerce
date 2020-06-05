package kr.co.abcmart.bo.product.model.master;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.abcmart.bo.product.model.master.base.BaseDpBrand;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DpBrand extends BaseDpBrand implements Validator {

	/** 대표이미지(컬러) */
	@JsonIgnore
	private FileUpload fileBrandImageColor;

	/** 대표이미지 대체텍스트(컬러) */
	private String altrnText;

	/** 등록자ID */
	private String rgstrId;

	/** 등록자이름 */
	private String rgstrName;

	/** 수정자ID */
	private String moderId;

	/** 수정자이름 */
	private String moderName;

	/** 등록/수정 여부. save or modify */
	private String type;

	private String pageType;

	private String orgBrandPrdtArtDispYn;

	// 등록자 정보 마스킹 처리
	public String getRgstrInfo() {

		String maskingStr = "";
		if (UtilsText.equals(this.getPageType(), "L")) {
			maskingStr = UtilsMasking.gridMasking(getRgstrId(), getRgstrName());
		} else {
			if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
				maskingStr = UtilsMasking.gridMasking(getRgstrId(), getRgstrName());
			} else {
				maskingStr = UtilsText.concat(this.rgstrId, Const.L_PAREN, this.rgstrName, Const.R_PAREN);
			}
		}

		return maskingStr;
	}

	// 수정자 정보 마스킹 처리
	public String getModerInfo() {
		String maskingStr = "";

		if (UtilsText.equals(this.getPageType(), "L")) {
			maskingStr = UtilsMasking.gridMasking(getModerId(), getModerName());
		} else {
			if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
				maskingStr = UtilsMasking.gridMasking(getModerId(), getModerName());
			} else {
				maskingStr = UtilsText.concat(this.moderId, Const.L_PAREN, this.moderName, Const.R_PAREN);
			}
		}
		return maskingStr;
	}

	@Override
	public void validate() throws ValidatorException {
		// 브랜드번호
		if (UtilsText.isBlank(this.getBrandNo())) {
			validationMessage("product.error.brand.brandNo");
		}

		// 온라인코드
		if (UtilsText.isBlank(this.getBrandGroupNoText())) {
			validationMessage("product.valid.brand.brandGroupNoText");
		}

		// 브랜드이름 (국문 / 영문)
		if (UtilsText.isBlank(this.getBrandName()) || UtilsText.isBlank(this.getBrandEnName())) {
			validationMessage("product.valid.brand.brandName");
		}

		// 브랜드초성 (한글 / 영어 / 숫자)
//		if (UtilsText.isBlank(this.getKorInitialText()) && UtilsText.isBlank(this.getEngInitialText())
//				&& UtilsText.isBlank(this.getEtcInitialText())) {
//			validationMessage("product.valid.brand.brandInitial");
//		}
		// 사용여부
		if (UtilsText.isBlank(this.getUseYn())) {
			validationMessage("product.valid.brand.useYn");
		}
		// 브랜드 대표이미지 (컬러)
		if (UtilsText.equals("modify", this.getType())) {
			if (!UtilsObject.isEmpty(this.getFileBrandImageColor()) && this.getFileBrandImageColor().isFileItem()) {
				// 확장자 검사
				if (!this.getFileBrandImageColor().isAllowExtAndMimeType("jpg", "png", "gif", "bmp")) {
					validationMessage("product.valid.brand.brandImageColorFileExtension");
				}
			}
		} else {
			if (UtilsObject.isEmpty(this.getFileBrandImageColor())) {
				validationMessage("product.valid.brand.brandImageColorFile");
			} else {
				if (this.getFileBrandImageColor().isFileItem()) {
					// 확장자 검사
					if (!this.getFileBrandImageColor().isAllowExtAndMimeType("jpg", "png")) {
						validationMessage("product.valid.brand.brandImageColorFileExtension");
					}
				} else {
					validationMessage("product.valid.brand.brandImageColorFile");
				}
			}
		}
		// 브랜드 대표이미지 (컬러) 대체텍스트
//		if (UtilsText.isBlank(this.getAltrnText())) {
//			validationMessage("product.valid.brand.brandImageColorAltrnText");
//		}
	}

}
