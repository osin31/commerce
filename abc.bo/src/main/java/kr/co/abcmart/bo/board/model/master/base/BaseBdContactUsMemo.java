package kr.co.abcmart.bo.board.model.master.base;

import lombok.Data;
import java.io.Serializable;
import kr.co.abcmart.common.bean.BaseBean;

@Data
public class BaseBdContactUsMemo extends BaseBean implements Serializable {

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 입점문의순번
     */
	private java.lang.Integer contactUsSeq;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 입점문의메모순번
     */
	private java.lang.Integer contactUsMemoSeq;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 메모
     */
	private String memoText;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 삭제여부
     */
	private String delYn;
	
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
	
}
