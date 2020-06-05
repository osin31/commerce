package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.product.model.master.base.BasePdProductMemo;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PdProductMemo extends BasePdProductMemo implements Validator {

	/** 관리자계정 */
	private String rgsterId;
	/** 관리자이름 */
	private String rgsterName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.abcmart.common.validation.Validator#validate()
	 */
	@Override
	public void validate() throws ValidatorException {
		// 상품코드
		if (UtilsText.isBlank(this.getPrdtNo())) {
			this.validationMessage("product.valid.product.badRequest");
		}
		// 메모내용
		if (UtilsText.isBlank(this.getMemoText())) {
			this.validationMessage("product.valid.product.memo.memoText");
		} else if (this.getMemoText().length() > 500) {
			this.validationMessage("product.valid.product.memo.memoText.length");
		}
	}

	public String getRgsterInfo() {
		return UtilsMasking.adminName(getRgsterId(), getRgsterName());
	}

}
