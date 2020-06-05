package kr.co.abcmart.bo.settlement.model.master;

import kr.co.abcmart.bo.settlement.model.master.base.BaseSeExactCalculationMemo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SeExactCalculationMemo extends BaseSeExactCalculationMemo {

	// 등록날짜 변환
	private String rgstDate;

	// 로그인 아이디
	private String loginId;

	// 어드민 네임
	private String adminName;
}
