package kr.co.abcmart.bo.member.model.master.base;

import lombok.Data;
import java.io.Serializable;
import kr.co.abcmart.common.bean.BaseBean;

@Data
public class BaseMbEmployeeCertificationHistory extends BaseBean implements Serializable {

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 회원번호
     */
	private String memberNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 사원번호
     */
	private String empNoText;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 인증일시
     */
	private java.sql.Timestamp crtfcDtm;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 퇴사처리일시
     */
	private java.sql.Timestamp resignProcDtm;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 내부관리정보
     */
	private String insdMgmtInfoText;
	
}
