package kr.co.abcmart.bo.product.model.master.base;

import lombok.Data;
import java.io.Serializable;
import kr.co.abcmart.common.bean.BaseBean;

@Data
public class BasePdProductMemo extends BaseBean implements Serializable {

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 상품번호
     */
	private String prdtNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 상품메모순번
     */
	private java.lang.Integer prdtMemoSeq;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 메모
     */
	private String memoText;
	
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
