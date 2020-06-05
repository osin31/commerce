package kr.co.abcmart.bo.system.model.master;

import kr.co.abcmart.bo.system.model.master.base.BaseSyAdminChangeHistory;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SyAdminChangeHistory extends BaseSyAdminChangeHistory {

	private String rgsterName;
	
	private String chngFieldValue;
	
	private String toDate;
	
	private String fromDate;

}
