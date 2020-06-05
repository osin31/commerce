package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.product.model.master.base.BaseBdProductInquiryMemo;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BdProductInquiryMemo extends BaseBdProductInquiryMemo implements Validator {

	/** 등록자 ID */
	private String rgsterId;

	/** 등록자 */
	private String rgsterName;

	@Override
	public void validate() throws ValidatorException {
		// 상품Q&A번호
		if (UtilsObject.isEmpty(getPrdtInqrySeq())) {
			this.validationMessage("product.valid.product.inquiry.memo.prdtInqrySeq");
		}

		// 메모내용
		if (UtilsText.isBlank(getMemoText())) {
			validationMessage("product.valid.product.inquiry.memo.memoText");
		}
	}

	public String getRgsterInfo() {
		return UtilsMasking.adminName(getRgsterId(), getRgsterName());
	}

}
