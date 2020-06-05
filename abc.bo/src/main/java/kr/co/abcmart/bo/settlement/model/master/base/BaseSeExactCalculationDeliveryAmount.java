package kr.co.abcmart.bo.settlement.model.master.base;

import lombok.Data;
import java.io.Serializable;
import kr.co.abcmart.common.bean.BaseBean;

@Data
public class BaseSeExactCalculationDeliveryAmount extends BaseBean implements Serializable {

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 정산순번
     */
	private java.lang.Integer excclcSeq;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문번호
     */
	private String orderNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문상품순번
     */
	private java.lang.Short orderPrdtSeq;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 매출취소구분
     */
	private String salesCnclGbnType;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문금액
     */
	private java.lang.Integer orderAmt;
	
}
