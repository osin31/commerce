package kr.co.abcmart.bo.stats.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class RestockStatsSearchVO implements Serializable {

	// 사이트번호
	private String siteNo;

	// 판매시작일
	private String fromDate;

	// 판매종료일
	private String toDate;

	/** 검색어. 구분 */
	private String searchKeywordType;

	/** 검색어. 키워드 */
	private String searchKeyword;

	// 채널번호
	private String chnnlNo;

	/** 자사상품여부 */
	private String mmnyPrdtYn;

	// 입점사번호
	private String vndrNo;

	/** 브랜드명 */
	private String brandName;

	/** 엑셀 클릭여부 */
	private String isExcel;

}
