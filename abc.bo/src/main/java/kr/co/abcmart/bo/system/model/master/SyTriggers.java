package kr.co.abcmart.bo.system.model.master;

import kr.co.abcmart.bo.system.model.master.base.BaseSyTriggers;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsMasking;
import kr.co.abcmart.util.UtilsSchedule;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SyTriggers extends BaseSyTriggers {

	private String siteId;
	private String schedId;
	private String parameters;
	private String cronExpression;
	private String regUserId;
	private String regLoginId;
	private String regUserName;
	private java.sql.Timestamp regDtime;
	private String modifyUserId;
	private String modifyLoginId;
	private String modifyUserName;
	private java.sql.Timestamp modifyDtime;

	public String getSchedId() {
		if (UtilsText.isBlank(schedId)) {
			schedId = UtilsSchedule.genSchedId(siteId, getTriggerName(), getTriggerGroup());
		}

		return schedId;
	}

	public String getModerDisplayName() {
		return UtilsText.concat(UtilsMasking.loginId(regLoginId), Const.L_PAREN, UtilsMasking.userName(regUserName),
				Const.R_PAREN);
	}

	public String getRgsterDisplayName() {
		return UtilsText.concat(UtilsMasking.loginId(modifyLoginId), Const.L_PAREN,
				UtilsMasking.userName(modifyUserName), Const.R_PAREN);
	}

}
