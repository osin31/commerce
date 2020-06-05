package kr.co.abcmart.bo.cmm.model.master;

import kr.co.abcmart.bo.cmm.model.master.base.BaseCmPushSendHistory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class CmPushSendHistory extends BaseCmPushSendHistory {

	private java.lang.Integer sendCount;

}
