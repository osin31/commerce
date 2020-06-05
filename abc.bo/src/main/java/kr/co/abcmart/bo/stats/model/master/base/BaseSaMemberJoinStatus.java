package kr.co.abcmart.bo.stats.model.master.base;

import lombok.Data;
import java.io.Serializable;
import kr.co.abcmart.common.bean.BaseBean;

@Data
public class BaseSaMemberJoinStatus extends BaseBean implements Serializable {

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 
     */
	private java.sql.Timestamp joinDay;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : J(가입현황), A(연령현황)
     */
	private String statusGbnType;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : PC(D), Mobile, APP
     */
	private String deviceCode;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 
     */
	private String dotwName;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 온라인남, 10대남
     */
	private java.lang.Integer statusField1Count;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 온라인여, 10대여
     */
	private java.lang.Integer statusField2Count;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 온라인기타, 20대남
     */
	private java.lang.Integer statusField3Count;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 온라인전체, 20대여
     */
	private java.lang.Integer statusField4Count;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 멤버십남, 30대남
     */
	private java.lang.Integer statusField5Count;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 멤버십여, 30대여
     */
	private java.lang.Integer statusField6Count;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 멤버십전체, 40대남
     */
	private java.lang.Integer statusField7Count;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 멤버십전환남, 40대여
     */
	private java.lang.Integer statusField8Count;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 멤버십전환여, 50대남
     */
	private java.lang.Integer statusField9Count;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 멤버십전환전체, 50대여
     */
	private java.lang.Integer statusField10Count;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 60대남
     */
	private java.lang.Integer statusField11Count;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 60대여
     */
	private java.lang.Integer statusField12Count;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 기타남
     */
	private java.lang.Integer statusField13Count;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 기타여
     */
	private java.lang.Integer statusField14Count;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 탈퇴남
     */
	private java.lang.Integer statusField15Count;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 탈퇴여
     */
	private java.lang.Integer statusField16Count;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 해당일기준 총 회원수
     */
	private java.lang.Integer onlnTotalMemberCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 해당일기준 총 회원수
     */
	private java.lang.Integer mbshpTotalMemberCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 해당일기준 온라인>멤버십 전환 총 회원수
     */
	private java.lang.Integer cnvrtTotalMemberCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 
     */
	private java.sql.Timestamp rgstDtm;
	
}
