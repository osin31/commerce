package kr.co.abcmart.bo.promotion.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class PrPromotionSearchVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 프로모션번호 */
	private String promoNo;

	/** 목록 개수 */
	private int pageCount;

	/** 날짜 타입 */
	private String dateType;

	/** 시작일시 */
	private String startYmd;

	/** 종료일시 */
	private String endYmd;

	/** 사용여부 */
	private String useYn;

	/** 검색 타입 */
	private String keywordType;

	/** 검색어 */
	private String keyword;

	/** 디바이스 */
	private String[] deviceCodes;

	/** 채널 */
	private String[] chnnlNos;

	/** 프로모션 유형 */
	private String promoTypeCode;

	/** 회원 등급 배열 */
	private String[] mbshpGradeCodes;

	/** 회원 유형 배열 */
	private String[] memberTypeCodes;

	/** 진행상태 */
	private String promoProgressStatus;

	/** 임직원여부 */
	private String empYn;

	/** 상품번호 */
	private String prdtNo;

	/** 상품구분 */
	private String prdtType;

}
