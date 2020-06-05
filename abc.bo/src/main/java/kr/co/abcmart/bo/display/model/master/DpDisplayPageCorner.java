package kr.co.abcmart.bo.display.model.master;

import java.io.Serializable;

import kr.co.abcmart.bo.display.model.master.base.BaseDpDisplayPageCorner;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DpDisplayPageCorner extends BaseDpDisplayPageCorner implements Serializable, Validator {

	@ParameterOption(target = "sortSeq")
	private DpDisplayPageCornerName[] list;

	@ParameterOption(target = "sortSeq")
	private DpDisplayPageCornerSet[] set;

	private String contTypeCode;

	private String dispYn;

	@Override
	public void validate() throws ValidatorException {

		if (UtilsText.equals(getContTypeCode(), "I") || UtilsText.equals(getContTypeCode(), "T")) {
			if (list == null || list.length == 0) {
				// 코너 등록 필요
				validationMessage("display.contents.valid.name.null");
			} else {
				for (DpDisplayPageCornerName name : list) {
					name.validate();
				}
			}
		} else {
			if (set == null || set.length == 0) {
				// 코너 등록 필요
				validationMessage("display.contents.valid.set.null");
			} else {
				for (DpDisplayPageCornerSet s : set) {
					s.validate();
				}
			}
		}

	}

}
