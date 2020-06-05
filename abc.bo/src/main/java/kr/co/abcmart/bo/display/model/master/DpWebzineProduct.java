package kr.co.abcmart.bo.display.model.master;

import kr.co.abcmart.bo.display.model.master.base.BaseDpWebzineProduct;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DpWebzineProduct extends BaseDpWebzineProduct implements Validator {

	private String sellStatCode;
	private String sellStatCodeName;
	private String prdtName;
	private String ctgrName;
	private String imageUrl;
	private String altrnText;
	private String dispYn;
	private String siteNo;
	private String chnnlNo;
	private String vndrPrdtNoText;

	@Override
	public void validate() throws ValidatorException {
		// TODO Auto-generated method stub

		if (this.getSortSeq() == null) {
			validationMessage("display.contents.valid.sortSeq");
		}

		if (UtilsText.isBlank(getPrdtNo())) {
			validationMessage("display.contents.valid.product");
		}
	}

}
