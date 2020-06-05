package kr.co.abcmart.bo.system.model.master.base;

import lombok.Data;
import java.io.Serializable;
import kr.co.abcmart.common.bean.BaseBean;

@Data
public class BaseSyTriggers extends BaseBean implements Serializable {

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : SCHED_NAME
     */
	private String schedName;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : TRIGGER_NAME
     */
	private String triggerName;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : TRIGGER_GROUP
     */
	private String triggerGroup;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : JOB_NAME
     */
	private String jobName;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : JOB_GROUP
     */
	private String jobGroup;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : DESCRIPTION
     */
	private String description;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : NEXT_FIRE_TIME
     */
	private java.lang.Long nextFireTime;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : PREV_FIRE_TIME
     */
	private java.lang.Long prevFireTime;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : PRIORITY
     */
	private java.lang.Integer priority;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : TRIGGER_STATE
     */
	private String triggerState;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : TRIGGER_TYPE
     */
	private String triggerType;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : START_TIME
     */
	private java.lang.Long startTime;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : END_TIME
     */
	private java.lang.Long endTime;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : CALENDAR_NAME
     */
	private String calendarName;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : MISFIRE_INSTR
     */
	private java.lang.Short misfireInstr;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : JOB_DATA
     */
	private byte[] jobData;
	
}
