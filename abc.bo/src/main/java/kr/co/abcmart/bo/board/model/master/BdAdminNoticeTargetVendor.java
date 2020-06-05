package kr.co.abcmart.bo.board.model.master;

import kr.co.abcmart.bo.board.model.master.base.BaseBdAdminNoticeTargetVendor;
import lombok.Data;

@Data
public class BdAdminNoticeTargetVendor extends BaseBdAdminNoticeTargetVendor {
	/**
	 * 업체 이름
	 */
	private String vndrName;

}
