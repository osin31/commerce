package kr.co.abcmart.bo.cmm.model.master;

import kr.co.abcmart.bo.cmm.model.master.base.BaseCmForbiddenWord;
import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.validation.Validator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CmForbiddenWord extends BaseCmForbiddenWord implements Validator {

	private SyAdmin rgster;
	private SyAdmin moder;

	public String getRgsterDisplayName() {
		if (getRgster() != null) {
			return rgster.getAdminInfo();
		} else {
			return "";
		}

	}

	public String getModerDisplayName() {
		if (getModer() != null) {
			return moder.getAdminInfo();
		} else {
			return "";
		}

	}

	@Override
	public void validate() throws ValidatorException {
	}

}
