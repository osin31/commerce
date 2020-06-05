package kr.co.abcmart.bo.event.model.master;

import kr.co.abcmart.bo.event.model.master.base.BaseEvEventJoinMember;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class EvEventJoinMember extends BaseEvEventJoinMember {

	private String limitType;

	private String recentYn;

}
