package kr.co.abcmart.bo.promotion.model.master;

import java.text.SimpleDateFormat;

import kr.co.abcmart.bo.promotion.model.master.base.BaseEvCardBenefit;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.util.UtilsMasking;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class EvCardBenefit extends BaseEvCardBenefit implements Validator {

	private int pageCount;

	/** 이미지 파일 */
	private FileUpload cardBenefitImageFile;

	/** 전시 기간(S~E) */
	private String dispYmd;

	/** 기간 검색 */
	private String startYmd;
	private String endYmd;
	private String condition;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

	/* 관리자 정보 */
	private String rgsterName;
	private String rgsterId;
	private String rgsterInfo;

	private String moderName;
	private String moderId;
	private String moderInfo;

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

	public String getDispYmd() {

		if (this.getDispStartYmd() != null && this.getDispEndYmd() != null) {
			String start = sdf.format(getDispStartYmd());
			String end = sdf.format(getDispEndYmd());

			return start + "~" + end;
		}

		return null;
	}

	@Override
	public void validate() throws ValidatorException {

		// 무이자 할부 행사명
		if (UtilsText.isBlank(getCardBenefitName())) {
			validationMessage("promotion.valid.card.benefit.cardBenefitName");
		}

		if (UtilsText.getByteLength(getCardBenefitName()) > 100) {
			validationMessage("promotion.valid.card.benefit.cardBenefitName.length");
		}

		// 전시기간
		if (UtilsText.isBlank(getStartYmd())) {
			validationMessage("promotion.valid.card.benefit.startYmd");
		}
		if (UtilsText.isBlank(getEndYmd())) {
			validationMessage("promotion.valid.card.benefit.endYmd");
		}

		// 전시여부
		if (UtilsText.isBlank(getDispYn())) {
			validationMessage("promotion.valid.card.benefit.dispYn");
		}

		// 카드혜택 이미지
		if (!(getCardBenefitImageFile() != null || !UtilsText.isBlank(getCardBenefitImagePathText()))) {
			validationMessage("promotion.valid.card.benefit.cardBenefitImageName");
		}

	}

}