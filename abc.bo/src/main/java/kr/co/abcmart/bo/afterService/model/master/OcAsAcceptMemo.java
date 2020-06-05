package kr.co.abcmart.bo.afterService.model.master;

import kr.co.abcmart.bo.afterService.model.master.base.BaseOcAsAcceptMemo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OcAsAcceptMemo extends BaseOcAsAcceptMemo {
	private String loginId;

	private String adminName;

}
