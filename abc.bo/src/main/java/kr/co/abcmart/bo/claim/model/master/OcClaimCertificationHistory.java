package kr.co.abcmart.bo.claim.model.master;

import kr.co.abcmart.bo.claim.model.master.base.BaseOcClaimCertificationHistory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OcClaimCertificationHistory extends BaseOcClaimCertificationHistory {

	private String bankCode; // 은행코드
	private String acntNoText; // 계좌번호
	private String acntHldrName; // 예금주

}
