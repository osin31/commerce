package kr.co.abcmart.bo.member.model.master;

import kr.co.abcmart.bo.member.model.master.base.BaseMbMemberChangeHistory;
import kr.co.abcmart.common.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MbMemberChangeHistory extends BaseMbMemberChangeHistory {

	public String rgsterDisplayName;

	public String getChangeValue() {
		String chngBeforeValue = getChngBeforeFieldValue();
		String resultValue = "";

		if (UtilsText.isNotBlank(chngBeforeValue)) {
			resultValue = UtilsText.concat(getChngBeforeFieldValue(), " > ", getChngAfterFieldValue());
		} else {
			resultValue = UtilsText.concat(getChngAfterFieldValue());
		}

		return resultValue;
	}

}
