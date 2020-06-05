package kr.co.abcmart.bo.product.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품 테이블을 같이 쓰는 사은품이랑 명칭이 애매
 * @FileName : PdFreeGiftSearchVO.java
 * @Project : abc.bo
 * @Date : 2019. 3. 29.
 * @Author : hsjhsj19
 */
@Slf4j
@Data
public class PdFreeGiftSearchVO {

	/** 업체구분 중 업체유형. (C:자사, V:입점) */
	private String vndrGbnType;

	/** 사은품명 */
	private String prdtName;

	/** 사은품 ID */
	private String prdtNo;

	/** 사용여부 */
	private String useYn;

	/** 전시여부 */
	private String dispYn;

	/** 상품유형코드 */
	private String prdtTypeCode;
}
