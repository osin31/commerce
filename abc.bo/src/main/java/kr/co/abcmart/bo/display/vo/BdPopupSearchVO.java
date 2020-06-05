package kr.co.abcmart.bo.display.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class BdPopupSearchVO implements Serializable {

	private static final long serialVersionUID = 1L;

	// 목록 개수
	private int pageCount;

	/** 팝업 타입 */
	private String[] popupTypes;

	/** 날짜 타입 */
	private String dateType;

	/** 시작일시 */
	private String startYmd;

	/** 종료일시 */
	private String endYmd;

	/** 사용여부 */
	private String useYn;

	/** 전시여부 */
	private String dispYn;

	/** 검색 타입 */
	private String keywordType;

	/** 검색어 */
	private String keyword;

	/** 사이트 번호 */
	private String siteNo;

	/** 특정일 전시 사용 여부 */
	private String dispDaySetupYn;

	/** 디바이스 */
	private String deviceCode;

	/** 디바이스 팝업 유형 */
	private String devicePopupType;

}
