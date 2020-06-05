package kr.co.abcmart.bo.product.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class DpBrandPageSearchVO implements Serializable {

	// 목록 개수
	private int pageCount;

	/** 브랜드번호 */
	private String brandNo;

	/** 사이트번호 */
	private String siteNo;

	/** 디바이스코드 */
	private String deviceCode;

	/** 디바이스코드 */
	private String dispGbnType;

	private String chnnlNo;

}
