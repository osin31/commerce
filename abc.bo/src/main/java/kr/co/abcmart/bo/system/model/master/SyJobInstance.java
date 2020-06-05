package kr.co.abcmart.bo.system.model.master;

import kr.co.abcmart.bo.system.model.master.base.BaseSyJobInstance;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SyJobInstance extends BaseSyJobInstance {

	private Long jobExecutionId;

	private java.sql.Timestamp createTime;

	private java.sql.Timestamp startTime;

	private java.sql.Timestamp endTime;

	private String status;

	private String exitCode;

	private String message;

	private java.sql.Timestamp lastUpdated;

}
