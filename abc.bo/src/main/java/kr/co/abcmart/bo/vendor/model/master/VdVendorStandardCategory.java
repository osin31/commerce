package kr.co.abcmart.bo.vendor.model.master;

import kr.co.abcmart.bo.vendor.model.master.base.BaseVdVendorStandardCategory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class VdVendorStandardCategory extends BaseVdVendorStandardCategory {

	private String stdCtgrName;
	private String vendorStdCtgrNo;
	private String vendorStdCtgrStatus;
}
