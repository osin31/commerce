package kr.co.abcmart.bo.display.model.master;

import kr.co.abcmart.bo.display.model.master.base.BaseDpCategoryCorner;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DpCategoryCorner extends BaseDpCategoryCorner implements Validator {

	@ParameterOption(target = "sortSeq")
	private DpCategoryCornerName[] list;

	@ParameterOption(target = "sortSeq")
	private DpCategoryCornerSet[] set;

	private String contTypeCode;

	@Override
	public void validate() throws ValidatorException {

		if (UtilsText.equals(getContTypeCode(), "I") || UtilsText.equals(getContTypeCode(), "T")) {
			if (list == null || list.length == 0) {
				// 코너 등록 필요
				validationMessage("display.contents.valid.name.null");
			} else {
				for (DpCategoryCornerName name : list) {
					name.validate();
				}
			}
		} else {
			if (set == null || set.length == 0) {
				// 코너 등록 필요
				validationMessage("display.contents.valid.set.null");
			} else {
				for (DpCategoryCornerSet s : set) {
					s.validate();
				}
			}
		}

	}
}
