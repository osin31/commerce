package kr.co.abcmart.bo.vendor.model.master;

import kr.co.abcmart.bo.vendor.model.master.base.BaseVdVendorChargeMd;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class VdVendorChargeMd extends BaseVdVendorChargeMd {

	private String chargeMdChnnlNo;
	private String chargeMdName;
	private String chargeMdId;

}
