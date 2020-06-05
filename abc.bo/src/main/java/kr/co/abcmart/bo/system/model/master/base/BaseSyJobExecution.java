package kr.co.abcmart.bo.system.model.master.base;

import lombok.Data;
import java.io.Serializable;
import kr.co.abcmart.common.bean.BaseBean;

@Data
public class BaseSyJobExecution extends BaseBean implements Serializable {

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : JOB_EXECUTION_ID
     */
	private java.lang.Long jobExecutionId;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : VERSION
     */
	private java.lang.Long version;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : JOB_INSTANCE_ID
     */
	private java.lang.Long jobInstanceId;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : CREATE_TIME
     */
	private java.sql.Timestamp createTime;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : START_TIME
     */
	private java.sql.Timestamp startTime;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : END_TIME
     */
	private java.sql.Timestamp endTime;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : STATUS
     */
	private String status;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : EXIT_CODE
     */
	private String exitCode;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : EXIT_MESSAGE
     */
	private String exitMessage;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : LAST_UPDATED
     */
	private java.sql.Timestamp lastUpdated;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : JOB_CONFIGURATION_LOCATION
     */
	private String jobConfigurationLocation;
	
}
