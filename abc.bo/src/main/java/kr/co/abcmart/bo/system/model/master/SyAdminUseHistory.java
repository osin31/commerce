package kr.co.abcmart.bo.system.model.master;

import kr.co.abcmart.bo.system.model.master.base.BaseSyAdminUseHistory;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SyAdminUseHistory extends BaseSyAdminUseHistory {

	private String loginFailRsnCodeName;

}
