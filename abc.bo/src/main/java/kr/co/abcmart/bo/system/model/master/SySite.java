package kr.co.abcmart.bo.system.model.master;

import java.util.List;

import kr.co.abcmart.bo.system.model.master.base.BaseSySite;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString(callSuper = true)
public class SySite extends BaseSySite implements Validator {

	private SyAdmin rgster;
	private SyAdmin moder;

	@ParameterOption(target = "dlvyTypeCode")
	private SySiteDeliveryType[] deliveryTypeList;

	@ParameterOption(target = "snsChnnlCode")
	private SySiteApplySns[] applySnsArray;

	private List<SySiteApplySns> applySnsList;

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
		if (UtilsText.isBlank(getSiteName())) {
			validationMessage("사이트명이 존재하지 않습니다.", true);
		}
	}
}
