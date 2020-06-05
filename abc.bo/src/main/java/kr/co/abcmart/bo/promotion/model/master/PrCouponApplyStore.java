package kr.co.abcmart.bo.promotion.model.master;

import kr.co.abcmart.bo.promotion.model.master.base.BasePrCouponApplyStore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PrCouponApplyStore extends BasePrCouponApplyStore {

	private String storeName;

}
