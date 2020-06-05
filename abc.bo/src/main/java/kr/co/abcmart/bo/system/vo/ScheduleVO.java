package kr.co.abcmart.bo.system.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.abcmart.common.bean.BaseBean;
import kr.co.abcmart.common.constant.BaseConst;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.common.util.UtilsText;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@ToString(callSuper = true)
public class ScheduleVO extends BaseBean {

	@Getter
	private String siteId;

	@Getter
	private String jobName;

	@Getter
	private String jobGroup;

	@Getter
	private String jobDescription;

	@Getter
	private String triggerGroup;

	@Getter
	private String triggerName;

	@Getter
	private String description;

	@Getter
	private String parameters;

	@Getter
	private String cronExpression;

	@Getter
	private String schedId;

	@Getter
	private String schedulerHistoryId;

	@Getter
	private String triggerStartTime;

	@Getter
	private String triggerEndTime;

	@Getter
	private String jobExecutionId;

	@JsonIgnore
	public Date getTriggerStartTimeToDate() {

		if (!UtilsText.isBlank(getTriggerStartTime())) {
			return new Date(Long.parseLong(getTriggerStartTime()));
		}

		return UtilsDate.getDate();
	}

	@JsonIgnore
	public Date getTriggerEndTimeToDate() {

		if (!UtilsText.isBlank(getTriggerEndTime())) {
			return new Date(Long.parseLong(getTriggerEndTime()));
		}

		return UtilsDate.parseDate(BaseConst.DEFAULT_END_DATETIME, BaseConst.DEFAULT_DATETIME_PATTERN);
	}

}
