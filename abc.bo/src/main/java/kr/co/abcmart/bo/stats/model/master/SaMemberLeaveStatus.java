package kr.co.abcmart.bo.stats.model.master;

import kr.co.abcmart.bo.stats.model.master.base.BaseSaMemberLeaveStatus;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SaMemberLeaveStatus extends BaseSaMemberLeaveStatus {

	private String periodType;
	private String fromDate;
	private String toDate;
	private String periodYearStart;
	private String periodYearEnd;
	private String periodMonthStart;
	private String periodMonthEnd;

	private String totalDayC;
	private String total;
	private String type0;
	private String type1;
	private String type2;
	private String type3;
	private String type4;
	private String type5;
	private String type6;
	private String type7;
	private String type8;
	private String type9;
	private String type10;
	private String type11;

}
