package kr.co.abcmart.bo.vendor.model.master;

import kr.co.abcmart.bo.vendor.model.master.base.BaseVdVendorBrandEmployeeDiscount;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class VdVendorBrandEmployeeDiscount extends BaseVdVendorBrandEmployeeDiscount {

	private int discountBrandIndex;

}
