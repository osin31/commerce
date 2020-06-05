package kr.co.abcmart.bo.system.model.master;

import kr.co.abcmart.bo.system.model.master.base.BaseSySitePaymentMeans;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SySitePaymentMeans extends BaseSySitePaymentMeans implements Validator {

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
		if (UtilsText.isBlank(getMemberTypeCode())) {
			validationMessage("회원유형코드가 존재하지 않습니다.", true);
		}

		if (UtilsText.isBlank(getEmpYn())) {
			validationMessage("임직원여부가 존재하지 않습니다.", true);
		}

		if (UtilsText.isBlank(getOrderType())) {
			validationMessage("주문구분코드가 존재하지 않습니다.", true);
		}

		if (UtilsText.isBlank(getMainPymntMeansYn())) {
			validationMessage("주결제수단여부가 존재하지 않습니다.", true);
		}

		if (UtilsText.isBlank(getPymntMeansCode())) {
			validationMessage("결제수단코드가 존재하지 않습니다.", true);
		}

		if (UtilsText.isBlank(getUseYn())) {
			validationMessage("사용여부가 존재하지 않습니다.", true);
		}
	}

}
