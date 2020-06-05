package kr.co.abcmart.bo.product.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 기프트 카드 검색 조건
 * @FileName : PdGiftCardSearchVO.java
 * @Project : abc.bo
 * @Date : 2019. 4. 11.
 * @Author : hsjhsj19
 */
@Slf4j
@Data
public class PdGiftCardSearchVO {

	/** 카드유형 */
	private String giftCardTypeCode;

	/** 기프트카드명 */
	private String giftCardName;

	/** 기간 */
	private String periodType;

	/** 시작일 */
	private String periodStartDate;

	/** 종료일 */
	private String periodEndDate;

	/** 유효기간 */
	private String validTermType;

	/** 사용여부 */
	private String useYn;

	/** 기프트카드구분 */
	private String giftCardGbnType;

}
