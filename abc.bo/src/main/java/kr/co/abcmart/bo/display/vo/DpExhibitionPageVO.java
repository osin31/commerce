package kr.co.abcmart.bo.display.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

/**
 * @Desc : 전시페이지 정보 조회용 객체
 * @FileName : DpExhibitionPageVO.java
 * @Project : abc.bo
 * @Date : 2019. 4. 23.
 * @Author : tennessee
 */
@Data
public class DpExhibitionPageVO implements Serializable {

	/** 장치유형 (PC 또는 MOBILE) */
	private String deviceType;

	/** 사이트번호 */
	private String siteNo;

	/** 사이트명 */
	private String siteName;

	/** 채널번호 */
	private String chnnlNo;

	/** 채널명 */
	private String chnnlName;

	/** 전시페이지번호 */
	private String dispPageNo;

	/** 전시페이지명 */
	private String dispPageName;

	/** 전시여부 */
	private String dispYn;

	/** 카테고리번호 */
	private String ctgrNo;

	/** 상품번호 */
	private String prdtNo;

	/** 상품매핑정렬순서 */
	private String sortSeq;

	/** 카테고리와 상품 매핑등록일 */
	private Timestamp dispStartDtm;
}
