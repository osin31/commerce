package kr.co.abcmart.bo.claim.model.master;

import kr.co.abcmart.bo.claim.model.master.base.BaseOcClaimMemo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OcClaimMemo extends BaseOcClaimMemo {
	private String clmGbnCodeName; // 클레임구분코드명
	private String loginId; // (관리자) 로그인ID
	private String adminName; // 관리자명
}
