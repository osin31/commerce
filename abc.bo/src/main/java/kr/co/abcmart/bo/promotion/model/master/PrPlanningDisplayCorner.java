package kr.co.abcmart.bo.promotion.model.master;

import kr.co.abcmart.bo.promotion.model.master.base.BasePrPlanningDisplayCorner;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.validation.Validator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PrPlanningDisplayCorner extends BasePrPlanningDisplayCorner implements Validator {

	private FileUpload imageFile;

	private String prdtCount;

	private Integer[] cornerSeqArr;

	@Override
	public void validate() throws ValidatorException {

	}

}
