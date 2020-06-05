package kr.co.abcmart.bo.product.vo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class VdVendorProductAllNoticeSearchVO {

	/** 업체구분 중 업체유형. (C:자사, V:입점) */
	private String vndrGbnType;

	/** 업체번호 */
	private String vndrNo;

	/** 업체명 */
	private String vndrName;

	/** 전시여부 */
	private String dispYn;

	/** 검색어. 구분 */
	private String searchKeywordType;

	/** 검색어. 키워드 */
	private String searchKeyword;

	/** 기간 중 형태 */
	private String periodType;

	/** 기간 중 시작일자 */
	private String periodStartDate;

	/** 기간 중 종료일자 */
	private String periodEndDate;
}
