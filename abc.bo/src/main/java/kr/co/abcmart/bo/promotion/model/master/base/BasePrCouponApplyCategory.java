package kr.co.abcmart.bo.promotion.model.master.base;

import lombok.Data;
import java.io.Serializable;
import kr.co.abcmart.common.bean.BaseBean;

@Data
public class BasePrCouponApplyCategory extends BaseBean implements Serializable {

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 쿠폰번호
     */
	private String cpnNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 카테고리번호
     */
	private String ctgrNo;
	
}
