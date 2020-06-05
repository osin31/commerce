package kr.co.abcmart.bo.claim.model.master;

import kr.co.abcmart.bo.claim.model.master.base.BaseOcClaimStatusHistory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OcClaimStatusHistory extends BaseOcClaimStatusHistory {

	// 클레임 상품 상태 코드 명
	private String clmPrdtStatCodeName;

	// 관리자ID(관리자명)
	private String displayAdmin;

}
