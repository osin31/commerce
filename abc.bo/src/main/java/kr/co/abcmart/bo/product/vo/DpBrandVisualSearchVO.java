package kr.co.abcmart.bo.product.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class DpBrandVisualSearchVO implements Serializable {

	// 목록 개수
	private int pageCount;

	/** 브랜드번호 */
	private String brandNo;

}
