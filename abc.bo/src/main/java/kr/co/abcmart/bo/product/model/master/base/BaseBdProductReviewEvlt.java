package kr.co.abcmart.bo.product.model.master.base;

import lombok.Data;
import java.io.Serializable;
import kr.co.abcmart.common.bean.BaseBean;

@Data
public class BaseBdProductReviewEvlt extends BaseBean implements Serializable {

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 상품후기순번
     */
	private java.lang.Integer prdtRvwSeq;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 상품후기코드
     */
	private String prdtRvwCode;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 평가점수
     */
	private java.lang.Integer evltScore;
	
}
