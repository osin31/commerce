package kr.co.abcmart.bo.system.model.master.base;

import lombok.Data;
import java.io.Serializable;
import kr.co.abcmart.common.bean.BaseBean;

@Data
public class BaseSySchedulerHistoryLog extends BaseBean implements Serializable {

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : SEQ
     */
	private java.lang.Integer seq;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : SCHEDULER_HISTORY_ID
     */
	private java.lang.Integer schedulerHistoryId;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : SCHED_ID
     */
	private String schedId;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : SITE_ID
     */
	private String siteId;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : TRIGGER_GROUP
     */
	private String triggerGroup;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : TRIGGER_NAME
     */
	private String triggerName;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : MESSAGE
     */
	private String message;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : REG_DTIME
     */
	private java.sql.Timestamp regDtime;
	
}
