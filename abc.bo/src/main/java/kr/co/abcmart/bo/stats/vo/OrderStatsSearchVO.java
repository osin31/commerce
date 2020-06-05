package kr.co.abcmart.bo.stats.vo;

import java.io.Serializable;

import kr.co.abcmart.constant.Const;
import lombok.Data;

@Data
public class OrderStatsSearchVO implements Serializable {

	private static final long serialVersionUID = 4249321934754645170L;

	// 엑셀 용 sort 정보
	private String sortColumn;
	private String sortType;
	// 엑셀인지 여부
	private String isExcel;
	// cd통계인지여부
	private String isClmStatsYn = Const.BOOLEAN_FALSE;
	// 일별,월별,년별
	private String choicePeriod;
	// 시작일
	private String dayFromDate;
	private String monthFromDate;
	private String yearFromDate;
	// 종료일
	private String dayToDate;
	private String monthToDate;
	private String yearToDate;
	// 현황 구분
	private String typeStats;
	// 사이트 코드
	private String siteNo;
	// 재고구분코드
	private String stockGbnCode;

	// 자사 상품 여부
	private char mmnyPrdtYn;

	// 자사 상품 등록 채널 코드
	private String ourChannel;

	// 입점사 상품 등록 사이트 코드
	private String shoperSiteNo;

	// 디바이스 코드
	private String[] deviceCodes;
	// 주문번호
	private String orderNo;
	// 발송처 코드
	private String[] stockGbnCodeArr;

	// 클레임상품상태코드
	private String clmPrdtStatCode;

	/**
	 * 결제 수단코드-- 결제수단 ( 검색 )
	 */
//	@ParameterOption(target = "pymntMeansCode")
//	private OcOrderPayment[] pymntMeansCodeList;
	private String[] pymntMeansCode;

	/**
	 * 에스크로
	 */
	private char chkEscroTrue;
	private char chkEscroFalse;

	// 시작시간
	private String fromTime;
	// 종료시간
	private String toTime;
	// 클레임타입
	private String claimType;
	// 입점업체 번호
	private String vndrNo;
}
