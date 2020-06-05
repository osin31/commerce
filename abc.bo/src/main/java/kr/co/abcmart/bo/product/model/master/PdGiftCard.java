package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.product.model.master.base.BasePdGiftCard;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PdGiftCard extends BasePdGiftCard implements Validator {

	/** 등록, 수정 구분 */
	private String typeGbn;

	/** 이미지 파일 */
	private FileUpload imageFile;

	/** 카드 유형 */
	private String[] giftCardTypeCodeArr;

	/** 등록자 */
	private String rgsterName;

	/** 등록자 Id */
	private String rgsterId;

	/** 수정자 */
	private String moderName;

	/** 수정자 Id */
	private String moderId;

	/** 판매가 */
	private String formatSellAmt;

	@Override
	public void validate() throws ValidatorException {
		/** 카드유형 */
		if (UtilsText.isAllEmpty(this.getGiftCardTypeCodeArr())) {
			validationMessage("product.valid.product.giftCard.giftCardTypeCode");
		}

		/** 기프트카드명 */
		if (UtilsText.isEmpty(this.getGiftCardName())) {
			validationMessage("product.valid.product.giftCard.giftCardName");
		}

		/** 관련번호 */
		if (UtilsText.isEmpty(this.getMgmtNoText())) {
			validationMessage("product.valid.product.giftCard.mgmtNoText");
		}

		// 등록인 경우에는 이미지 파일을 꼭 넣기 위함
		if ("save".equals(getTypeGbn())) {
			/** 이미지 파일 */
			if (UtilsObject.isEmpty(this.getImageFile())) {
				// 이미지 첨부여부 검사
				validationMessage("product.valid.product.giftCard.imageFile");
			} else {
				if (this.getImageFile().isFileItem()) {
					// 이미지 확장자 검사
					if (!this.getImageFile().isAllowExtAndMimeType("jpg", "png", "gif")) {
						validationMessage("product.valid.product.giftCard.imageFileExtension");
					}
				} else {
					// 이미지 첨부여부 검사
					validationMessage("product.valid.product.giftCard.imageFile");
				}
			}
		}

		/** 판매가(권종일때만) */
		if ("0".equals(this.getGiftCardGbnType())) {
			if (UtilsObject.isEmpty(this.getSellAmt()) || "0".equals(this.getSellAmt().toString())) {
				validationMessage("product.valid.product.giftCard.sellAmt");
			}
		}
	}

	// 등록자 정보 마스킹 처리
	public String getRgsterInfo() {
		return UtilsMasking.gridMasking(getRgsterId(), getRgsterName());
	}

	// 수정자 정보 마스킹 처리
	public String getModerInfo() {
		return UtilsMasking.gridMasking(getModerId(), getModerName());
	}

}
