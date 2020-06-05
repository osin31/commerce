package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.product.model.master.base.BaseCmProductInfoNotice;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.util.UtilsMasking;
import kr.co.abcmart.common.util.UtilsNumber;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CmProductInfoNotice extends BaseCmProductInfoNotice implements Validator {

	private int pageCount;

	private String status;
	private String delSeq;

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

	@Override
	public void validate() throws ValidatorException {

		// 항목명
		if (UtilsText.isBlank(getInfoNotcName())) {
			validationMessage("product.valid.notice.infoNotcName");
		}
		if (UtilsText.length(getInfoNotcName()) > 100) {
			validationMessage("product.valid.notice.infoNotcName.length");
		}

		if (UtilsText.length(getInfoNotcDfltValue()) > 500) {
			validationMessage("product.valid.notice.infoNotcDfltValue.length");
		}
		if (UtilsText.length(getInfoNotcWriteInfo()) > 500) {
			validationMessage("product.valid.notice.infoNotcWriteInfo.length");
		}

		// 노출 우선순위
		if (UtilsText.isBlank(UtilsNumber.toStr(getSortSeq()))) {
			validationMessage("product.valid.notice.sortSeq");
		} else {
			if (getSortSeq() < 0) {
				validationMessage("product.valid.notice.sortSeq.minus");
			}
		}

	}

}
