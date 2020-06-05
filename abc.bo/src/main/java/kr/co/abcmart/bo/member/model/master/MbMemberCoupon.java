package kr.co.abcmart.bo.member.model.master;

import kr.co.abcmart.bo.member.model.master.base.BaseMbMemberCoupon;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MbMemberCoupon extends BaseMbMemberCoupon {

	private int couponCnt;
	private int tCnt;
	private int aCnt;
	private int cCnt;
	private int eCnt;
	private int nCnt;
	private String cpnName; // 쿠폰명
	private String cpnTypeCode; // 쿠폰유형코드
	private String[] cpnStatCodes; // 쿠폰상태코드(복수)
	private java.lang.Integer reIssueHoldCpnSeq; // 재발급 보유쿠폰순번
}
