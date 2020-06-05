package kr.co.abcmart.bo.system.model.master.base;

import lombok.Data;
import java.io.Serializable;
import kr.co.abcmart.common.bean.BaseBean;

@Data
public class BaseSyEmployeeLimit extends BaseBean implements Serializable {

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 임직원순번
     */
	private java.lang.Integer empSeq;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 적용년월
     */
	private String applyMonthText;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 한도금액
     */
	private java.lang.Integer limitAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 사용금액
     */
	private java.lang.Integer useAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 잔여금액
     */
	private java.lang.Integer remainAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 등록자번호
     */
	private String rgsterNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 등록일시
     */
	private java.sql.Timestamp rgstDtm;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 수정자번호
     */
	private String moderNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 수정일시
     */
	private java.sql.Timestamp modDtm;
	
}
