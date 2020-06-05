package kr.co.abcmart.bo.promotion.model.master;

import kr.co.abcmart.bo.promotion.model.master.base.BasePrCouponApplyBrand;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PrCouponApplyBrand extends BasePrCouponApplyBrand {

	private String brandName;
}
