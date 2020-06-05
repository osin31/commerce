package kr.co.abcmart.bo.sales.model.master;

import kr.co.abcmart.bo.sales.model.master.base.BaseIfMaechul;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class IfMaechul extends BaseIfMaechul {

	// 기간 설정 시작일
	private String fromDate;

	// 기간 설정 끝일
	private String toDate;

	// 판매구분
	private String salesCnclGbnType;

	// 오류
	private String errorStatusYn;

	// bit
	private String pan;

	// site_no
	private String siteNo;

	// seq
	private String rowSeq;

	@ParameterOption(target = "rowSeq")
	private IfMaechul[] asNos;

	// 매출액 SUM
	private int saleAmtSum;
	// 에누리 금액 SUM
	private int saleEnurySum;
	// 구매포인트 금액 SUM
	private int pointAmtSum;
	// 이벤트 포인트 사용액 SUM
	private int eventPointAmtSum;
}
