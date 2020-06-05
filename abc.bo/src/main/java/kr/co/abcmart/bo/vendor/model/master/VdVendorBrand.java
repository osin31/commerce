package kr.co.abcmart.bo.vendor.model.master;

import kr.co.abcmart.bo.vendor.model.master.base.BaseVdVendorBrand;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class VdVendorBrand extends BaseVdVendorBrand {

	private String brandName;
	private String vendorBrandNo;
	private String vendorBrandStatus;

}
