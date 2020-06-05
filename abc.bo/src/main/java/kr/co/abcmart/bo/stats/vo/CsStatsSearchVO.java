package kr.co.abcmart.bo.stats.vo;

import java.io.Serializable;

import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;

@Data
public class CsStatsSearchVO extends BaseBean implements Serializable {

	// 시작일, 종료일 선택
	private String choiceDate;

	// 채널번호
	private String chnnlNo;

	// 사이트번호
	private String siteNo;

	// 입점사 번호
	private String vndrNo;

	// 디바이스 구분
	private String[] deviceCodeArr;

	// 디바이스
	private String deviceCode;

	// 시작날짜
	private String fromDate;

	// 끝날짜
	private String toDate;

	// 검색 기간 선택
	private String choicePeriod;

	// 구입처 구분
	private String onlnBuyYn;

	// 자사상품여부
	private String mmnyPrdtYn;

	// 회원 유형
	private String[] memberTypeCodeArr;

	// 상품문의유형 list
	private String[] cnslList;

	/** 엑셀 클릭여부 */
	private String isExcel;

}
