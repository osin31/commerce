package kr.co.abcmart.bo.promotion.model.master;

import kr.co.abcmart.bo.promotion.model.master.base.BasePrPlanningDisplayApprovalHistory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PrPlanningDisplayApprovalHistory extends BasePrPlanningDisplayApprovalHistory {

	private String[] plndpNoArr;

}
