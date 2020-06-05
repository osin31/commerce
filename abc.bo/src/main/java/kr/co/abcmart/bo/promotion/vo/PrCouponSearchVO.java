package kr.co.abcmart.bo.promotion.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class PrCouponSearchVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cpnNo;

	/** 목록 개수 */
	private int pageCount;

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

	/** 쿠폰유형 */
	private String cpnTypeCode;

	/** 검색 타입 */
	private String keywordType;

	/** 검색어 */
	private String keyword;

	/** 사이트 번호 */
	private String siteNo;

	/** 디바이스 */
	private String[] deviceCodes;

	/** 채널 */
	private String[] chnnlNos;

	/** 쿠폰속성 */
	private String[] cpnUseGbnTypes;

	/** 쿠폰분류 */
	private String normalCpnYn;

	/** 임직원 진행여부 */
	private String empApplyYn;

	/** 사용처 온/오프라인 여부 */
	private String usePlaceGbnType;

	private String cpnZoneDispYn;

	private String chkChannelModule;

	private String chkDeviceModule;

	// 회원상세
	private String searchKey;

	private String searchValue;

	private String cpnStatCode;

	private String searchDtm;

	private String fromDate;

	private String toDate;

	private String memberNo;

	private String loginId;

	private String memberName;

	private String[] cpnStatCodes;

	private String[] cpnUseYns;

	private String prdtNo;

}
