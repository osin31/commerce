package kr.co.abcmart.bo.display.model.master;

import kr.co.abcmart.bo.display.model.master.base.BaseDpDisplayTemplateCornerSet;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.validation.Validator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DpDisplayTemplateCornerSet extends BaseDpDisplayTemplateCornerSet implements Validator {

	private String contTypeCodeName;

	private String seq;

	@Override
	public void validate() throws ValidatorException {

		// 코너 명
		if (getDispContCount() == null) {
			validationMessage("display.template.valid.dispContCount");
		}
		// 노출순서
		if (getSortSeq() == null) {
			validationMessage("display.template.valid.sortSeq");
		}
	}
}
