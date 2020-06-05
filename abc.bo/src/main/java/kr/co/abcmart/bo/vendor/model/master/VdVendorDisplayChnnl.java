package kr.co.abcmart.bo.vendor.model.master;

import kr.co.abcmart.bo.vendor.model.master.base.BaseVdVendorDisplayChnnl;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.message.Message;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString(callSuper = true)
public class VdVendorDisplayChnnl extends BaseVdVendorDisplayChnnl implements Validator {

	private String siteNo;
	private String chnnlName;

	/*
	 * (non-Javadoc)
	 *
	 * @see kr.co.abcmart.common.validation.Validator#validate()
	 */
	@Override
	public void validate() throws ValidatorException {
		if (UtilsText.isBlank(getVndrNo())) {
			validationMessage("vendor.valid.variableisnull", "",
					new String[] { Message.getMessage("vendor.msg.vendor") });
		}
	}
}
