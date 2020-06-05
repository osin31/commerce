package kr.co.abcmart.bo.product.vo;

import lombok.Data;

@Data
public class DpBrandSearchVO {

	/** 브랜드번호 */
	private String brandNo;

	/** 브랜드이름(국/영문 공통) */
	private String brandName;

	/** 브랜드구분 (온라인전용브랜드여부) */
	private String onlnPrvuseBrandYn;

	/** 온라인코드 */
	private String brandGroupNoText;

	/** 브랜드ID (내부관리정보) */
	private String insdMgmtInfoText;

	/** 베스트여부 */
	private String bestYn;

	/** 사용여부 */
	private String useYn;

	/** A-Connect 전시여부 */
	private String aconnectDispYn;

	/** 브랜드이름(영문) */
	private String brandEnName;

	/** 검색조건 */
	private String searchType;

	/** 검색어 */
	private String searchWord;

	/** 사이트번호 */
	private String siteNo;

	/** 온라인 코드 */
	private String altrnText;

	/** 승인여부 */
	private String complateYn;

	/** 사용자 소속업체번호 */
	private String vndrNo;

	/** 사용자 소속업체타입 */
	private String vndrGbnType;

}
