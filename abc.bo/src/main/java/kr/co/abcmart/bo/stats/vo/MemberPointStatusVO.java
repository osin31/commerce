package kr.co.abcmart.bo.stats.vo;

import java.io.Serializable;

import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MemberPointStatusVO extends BaseBean implements Serializable {

	private String periodType;
	private String fromDate;
	private String toDate;
	private String periodYearStart;
	private String periodYearEnd;
	private String periodMonthStart;
	private String periodMonthEnd;

	private String totalDayC;
	private String totalPlus;
	private String totalMinus;
	private String type0;
	private String type1;
	private String type2;
	private String type3;
	private String type4;
	private String type5;
	private String type8;
	private String type9;
	private String type11;
	private String type12;
	private String type13;

}
