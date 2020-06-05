package kr.co.abcmart.bo.board.model.master;

import kr.co.abcmart.bo.board.model.master.base.BaseBdContactUsMemo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BdContactUsMemo extends BaseBdContactUsMemo {

	private String adminName;
	private String loginId;
	private String adminNo;

}
