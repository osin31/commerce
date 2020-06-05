package kr.co.abcmart.bo.system.model.master.base;

import lombok.Data;
import java.io.Serializable;
import kr.co.abcmart.common.bean.BaseBean;

@Data
public class BaseSyStepExecution extends BaseBean implements Serializable {

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : STEP_EXECUTION_ID
     */
	private java.lang.Long stepExecutionId;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : VERSION
     */
	private java.lang.Long version;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : STEP_NAME
     */
	private String stepName;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : JOB_EXECUTION_ID
     */
	private java.lang.Long jobExecutionId;
	
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
     * 설명 : COMMIT_COUNT
     */
	private java.lang.Long commitCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : READ_COUNT
     */
	private java.lang.Long readCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : FILTER_COUNT
     */
	private java.lang.Long filterCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : WRITE_COUNT
     */
	private java.lang.Long writeCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : READ_SKIP_COUNT
     */
	private java.lang.Long readSkipCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : WRITE_SKIP_COUNT
     */
	private java.lang.Long writeSkipCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : PROCESS_SKIP_COUNT
     */
	private java.lang.Long processSkipCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : ROLLBACK_COUNT
     */
	private java.lang.Long rollbackCount;
	
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
	
}
