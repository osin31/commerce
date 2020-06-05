package kr.co.abcmart.bo.promotion.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * @Desc : 상품 기획전정보 객체
 * @FileName : PrExhibitionPlanningVO.java
 * @Project : abc.bo
 * @Date : 2019. 4. 23.
 * @Author : tennessee
 */
@Data
public class PrExhibitionPlanningVO implements Serializable {

	/** 상품번호 */
	private String prdtNo;

	/** 기획전번호 */
	private String plndpNo;

	/** 기획전명 */
	private String plndpName;

	/** 사이트번호 */
	private String siteNo;

	/** 사이트명 */
	private String siteName;

	/** 채널번호 */
	private String chnnlNo;

	/** 채널명 */
	private String chnnlName;

	/** PC기획전주소 */
	private String pcPlndpUrl;

	/** MOBILE기획전주소 */
	private String mobilePlndpUrl;

	/** 기획전시작일 */
	private String plndpStartDtm;

	/** 기획전종료일 */
	private String plndpEndDtm;

	/** 상품기획전매핑전시순서 */
	private Integer sortSeq;

}
