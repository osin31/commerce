package kr.co.abcmart.bo.stats.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class CouponStatsSearchVO implements Serializable {

	/** 사이트번호 */
	private String siteNo;

	/** 쿠폰 분류 */
	private String[] cpnUseGbnTypeArr;

	/** 쿠폰 유형 */
	private String[] cpnTypeCodeArr;

	/** 쿠폰 속성 */
	private String normalCpnYn;

	/** 사용 여부 */
	private String useYn;

	/** 전시 여부 */
	private String dispYn;

	/** 쿠폰발급타입 */
	private String issueType;

	/** 쿠폰시작일시 */
	private String dayFromDate;

	/** 쿠폰종료일시 */
	private String dayToDate;

	/** 일별, 월별, 년별 선택값 */
	private String choicePeriod;

	/** 쿠폰 사용처 */
	private String[] usePlaceGbnTypeArr;

	/** 쿠폰 생성 형태 */
	private String cpnCrtType;

	/** 디바이스 */
	private String[] deviceCodeArr;

	/** 채널 */
	private String[] chnnlNoArr;

	/** 쿠폰검색 키 */
	private String cpnSrchKey;

	/** 쿠폰검색 값 */
	private String cpnSrchVal;

	/** 릴레이쿠폰 사용여부 */
	private String relayCpnUseYn;

	/** 업체번호 */
	private String vndrNo;
}
