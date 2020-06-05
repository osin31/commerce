package kr.co.abcmart.bo.vendor.model.master;

import kr.co.abcmart.bo.vendor.model.master.base.BaseVdVendorManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class VdVendorManager extends BaseVdVendorManager {

	private String vndrMngrName;
	private String telNoText;
	private String hdphnNoText;
	private String emailAddrText;
	private String vndrMngrId;
	private String pswdText;
	private String managerDelYn;
	private String managetStatus;
	private String vndrMngrAdminNo;

}
