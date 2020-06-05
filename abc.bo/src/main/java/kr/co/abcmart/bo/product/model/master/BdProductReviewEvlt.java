package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.product.model.master.base.BaseBdProductReviewEvlt;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BdProductReviewEvlt extends BaseBdProductReviewEvlt {

	/** 상품후기코드명 */
	private String prdtRvwCodeName;

	/** 상위 상품후기코드명 */
	private String upPrdtRvwCodeName;

}
