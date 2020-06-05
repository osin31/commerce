package kr.co.abcmart.bo.vendor.model.master;

import kr.co.abcmart.bo.vendor.model.master.base.BaseVdVendorExceptionCommission;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class VdVendorExceptionCommission extends BaseVdVendorExceptionCommission {

	private int commissionRowIndex;
	private String applyPeriod;
	private String commissionstdCtgrName;
	private String commissionBrandName;
	private String rgsterName;
	private String moderName;
	private String commissionGubun;
}
