package kr.co.abcmart.bo.claim.model.master;

import kr.co.abcmart.bo.claim.model.master.base.BaseIfBankda;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString(callSuper = true)
public class IfBankda extends BaseIfBankda {

	/**
	 * 검색기간 from
	 */
	private String fromDate;

	/**
	 * 검색기간 to
	 */
	private String toDate;

	/**
	 * 오늘 입금액
	 */
	private long todayInput;

	/**
	 * 오늘 출금액
	 */
	private long todayOutput;

	/**
	 * 잔액
	 */
	private long jango;

}
