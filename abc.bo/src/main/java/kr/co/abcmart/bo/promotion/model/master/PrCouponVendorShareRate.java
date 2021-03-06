package kr.co.abcmart.bo.promotion.model.master;

import kr.co.abcmart.bo.promotion.model.master.base.BasePrCouponVendorShareRate;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PrCouponVendorShareRate extends BasePrCouponVendorShareRate {

	private String status;

	private String vndrName;

}
