package kr.co.abcmart.bo.board.model.master;

import kr.co.abcmart.bo.board.model.master.base.BaseBdCorprBoardMemo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BdCorprBoardMemo extends BaseBdCorprBoardMemo {

	private String adminName;
	private String adminNo;
	private String loginId;

}
