package kr.co.abcmart.bo.stats.model.master;

import kr.co.abcmart.bo.stats.model.master.base.BaseSaCouponStats;
import lombok.Data;

@Data
public class SaCouponStats extends BaseSaCouponStats {

	/**
	 * 쿠폰번호
	 */
	private String cpnNo;
	/**
	 * 전시여부
	 */
	private String dispYn;
	/**
	 * 분류
	 */
	private String cpnUseGbnType;
	/**
	 * 속성
	 */
	private String normalCpnYn;
	/**
	 * 쿠폰명
	 */
	private String cpnName;
	/**
	 * 생성형태
	 */
	private String cpnCrtType;
	/**
	 * 사용처
	 */
	private String usePlaceGbnType;
	/**
	 * 쿠폰종류
	 */
	private String cpnTypeCode;
	/**
	 * 할인금액/율
	 */
	private String dscntValue;
	/**
	 * 유효기간
	 */
	private String validDtm;
	/**
	 * 발급수
	 */
	private int totalIssueCount;
	/**
	 * 사용수/쿠폰매출
	 */
	private String useCount;
	/**
	 * 사용처 - 온라인
	 */
	private int useOn;
	/**
	 * 사용처 - 오프라인
	 */
	private int useOff;
	/**
	 * 디바이스 - PC
	 */
	private int devicePc;
	/**
	 * 디바이스 - Mobile
	 */
	private int deviceMobile;
	/**
	 * 디바이스 - App
	 */
	private int deviceApp;
	
	/**
	 * 디바이스 - PC
	 */
	private String devicePcCount;
	/**
	 * 디바이스 - MOBILE
	 */
	private String deviceMoCount;
	/**
	 * 디바이스 - APP
	 */
	private String deviceAppCount;
	/**
	 * 온라인 사용수
	 */
	private String useOnlineCount;
	/**
	 * 오프라인 사용수
	 */
	private String useOfflineCount;

}