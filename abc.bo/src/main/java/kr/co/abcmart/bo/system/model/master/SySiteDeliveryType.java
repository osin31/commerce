package kr.co.abcmart.bo.system.model.master;

import kr.co.abcmart.bo.system.model.master.base.BaseSySiteDeliveryType;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SySiteDeliveryType extends BaseSySiteDeliveryType implements Validator {
	@Override
	public void validate() throws ValidatorException {
		if (UtilsText.isBlank(getDlvyTypeCode())) {
			validationMessage("배송유형코드가 존재하지 않습니다.", true);
		}

		if (UtilsText.isBlank(getUseYn())) {
			validationMessage("사용여부가 존재하지 않습니다.", true);
		}

	}

}
