package kr.co.abcmart.bo.member.model.master;

import kr.co.abcmart.bo.member.model.master.base.BaseMbMemberExpostSavePoint;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MbMemberExpostSavePoint extends BaseMbMemberExpostSavePoint {

	private String memberTypeCode;

}
