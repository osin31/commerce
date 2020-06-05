package kr.co.abcmart.bo.product.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class DpBrandRelationFileSearchVO implements Serializable {

	/** 브랜드번호 */
	private String brandNo;

	/** 파일 유형 */
	private String fileType;

	/** 목록 개수 */
	private int pageCount;

}
