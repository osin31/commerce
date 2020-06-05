package kr.co.abcmart.bo.stats.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class PromotionStatsSearchVO implements Serializable {

	// 프로모션번호
	private String promoNo;

	// 시작일, 종료일 선택
	private String choiceDate;

	// 사이트번호
	private String siteNo;

	// 디바이스 구분
	private String[] deviceCodeArr;

	// 프로모션 타입
	private String promoType;

	// 입점업체 번호
	private String vndrNo;

	// 판매시작일
	private String sellFromDate;

	// 판매종료일
	private String sellToDate;

	// 기획전시작일
	private String plndpFromDate;

	// 기획전종료일
	private String plndpToDate;

	// 프로모션 시작일
	private String promoFromDate;

	// 프로모션 종료일
	private String promoToDate;

	/** 검색어. 구분 */
	private String searchKeywordType;

	/** 검색어. 키워드 */
	private String searchKeyword;

	/** 엑셀 클릭여부 */
	private String isExcel;

	/**
	 * 프로모션 현황 통계에선 판매 기간 - 주문일, 결제확인일 기획전 현황 통계에선 판매 기간 - 주문일 고정
	 */
	private String dateKeywordType;

	/** 상세 필요 값 */
	// 기획전 아이디
	private String planningId;
	// 기획전명
	private String planningName;
	// 프로모션 구분
	private String promotionType;
	// 기간구분
	private String choicePeriod;

}
