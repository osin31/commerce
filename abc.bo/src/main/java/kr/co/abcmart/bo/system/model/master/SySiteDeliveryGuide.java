package kr.co.abcmart.bo.system.model.master;

import kr.co.abcmart.bo.system.model.master.base.BaseSySiteDeliveryGuide;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SySiteDeliveryGuide extends BaseSySiteDeliveryGuide implements Validator {

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
		if (UtilsText.isBlank(getSiteNo())) {
			validationMessage("사이트고유번호가 존재하지 않습니다.", true);
		}

		if (UtilsText.isBlank(getDlvyGuideBgnCode())) {
			validationMessage("배송안내구분코드가 존재하지 않습니다.", true);
		}

		if (UtilsText.isBlank(getDlvyGuideInfo())) {
			validationMessage("안내정보가 존재하지 않습니다.", true);
		}

	}

}
