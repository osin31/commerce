package kr.co.abcmart.bo.promotion.model.master;

import kr.co.abcmart.bo.promotion.model.master.base.BasePrPlanningDisplayCornerProduct;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.validation.Validator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PrPlanningDisplayCornerProduct extends BasePrPlanningDisplayCornerProduct implements Validator {

	private Integer[] cornerSeqArr;

	/* 대상 상품 관리 */
	private String status;
	private String prdtNo;
	private String prdtName;
	private String vndrPrdtNoText;
	private String titleImageName;
	private String titleImagePathText;
	private String titleImageUrl;
	private String titleImage;
	private String stdCtgrName;
	private String chnnlName;
	private String sellStatCode;
	private String dispYn;
	private String brandName;

	@Override
	public void validate() throws ValidatorException {

		// 노출순서
		if (getSortSeq() == null) {
			validationMessage("promotion.valid.planning.display.corner.sortSeq");
		}
	}

}
