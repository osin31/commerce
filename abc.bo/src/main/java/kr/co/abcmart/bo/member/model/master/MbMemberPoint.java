package kr.co.abcmart.bo.member.model.master;

import kr.co.abcmart.bo.member.model.master.base.BaseMbMemberPoint;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MbMemberPoint extends BaseMbMemberPoint {

	private int point;
	private int eventPoint;
	private String pointType;
	private int pointCnt;
	private String safeKey;
	private String validateCode;
	private String etcSavedCode;
	private int changeAmount;
	private String saveType;
	private String orderNo;

}
