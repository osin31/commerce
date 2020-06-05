package kr.co.abcmart.bo.stats.model.master.base;

import lombok.Data;
import java.io.Serializable;
import kr.co.abcmart.common.bean.BaseBean;

@Data
public class BaseSaMemberSnsConnectionStatus extends BaseBean implements Serializable {

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 
     */
	private java.sql.Timestamp totalDay;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 온라인회원, 맴버쉽회원,비회원
     */
	private String memberTypeCode;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 네이버, 페이스북, 카카오
     */
	private String snsGbnCode;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 
     */
	private String dotwName;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 집계일 회원수
     */
	private java.lang.Integer manMemberCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 집계일 회원수
     */
	private java.lang.Integer womanMemberCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 집계일 회원수
     */
	private java.lang.Integer etcMemberCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 집계일기준 총 회원수
     */
	private java.lang.Integer totalMemberCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 
     */
	private java.sql.Timestamp rgstDtm;
	
}
