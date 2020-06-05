package kr.co.abcmart.bo.cmm.model.master;

import kr.co.abcmart.bo.cmm.model.master.base.BaseCmTermsDetail;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import lombok.Data;

@Data
public class CmTermsDetail extends BaseCmTermsDetail {

	private String termsCodeName;
	private String moderName;

	@ParameterOption(target = "mngrType")
	private CmTermsDetailAddInfo[] addInfoList;

}
