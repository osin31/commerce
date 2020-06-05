package kr.co.abcmart.bo.member.model.master;

import kr.co.abcmart.bo.member.model.master.base.BaseMbMemberEasyLogin;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MbMemberEasyLogin extends BaseMbMemberEasyLogin {

	private String snsGbnCodeName;

}
