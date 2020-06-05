package kr.co.abcmart.bo.stats.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProductStatsSearchVO implements Serializable {

	// 사이트번호
	private String siteNo;

	// 브랜드번호
	private String brandNo;

	// 브랜드명
	private String brandName;

	// 입점사번호
	private String vndrNo;

	// 입점사명
	private String vndrName;

	// 디바이스 구분
	private String[] deviceCodeArr;

	// 시작날짜
	private String fromDate;

	// 끝날짜
	private String toDate;

	// 날짜타입
	private String choicePeriod;

	// 검색
	private String searchType;

	// 상품명
	private String prdtName;

	// 스타일
	private String stypeInfo;

	// 색상코드
	private String colorCode;

	// 상품검색어
	private String srchKeyword;

	// 표준 카테고리 번호
	private String vndrStdCtgrNo;

	// 수수료 유형
	private String cmsnKind;

	/** 검색어. 구분 */
	private String searchKeywordType;

	/** 검색어. 키워드 */
	private String searchKeyword;

	/** 엑셀 클릭여부 */
	private String isExcel;

	public String getFromDate() {
		return this.fromDate.replace(".", "");
	}

	public String getToDate() {
		return this.toDate.replace(".", "");
	}

}
