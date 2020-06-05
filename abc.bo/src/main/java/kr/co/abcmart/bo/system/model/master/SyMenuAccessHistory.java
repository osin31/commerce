package kr.co.abcmart.bo.system.model.master;

import kr.co.abcmart.bo.system.model.master.base.BaseSyMenuAccessHistory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SyMenuAccessHistory extends BaseSyMenuAccessHistory {
	private String loginId;
	private String adminNo;
	private String fromDate;
	private String toDate;
	private String level;
	private String authName;
	private String[] arrAdminNo; // 관리자ID 배열

}
