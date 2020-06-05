package kr.co.abcmart.bo.stats.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class SalesStatsSearchVO implements Serializable{
	
	private static final long serialVersionUID = 4249321934754645170L;
	//매출통계 검색조건 vo
	
	//사이트번호
	private String siteNo;
	//채널번호
	private String chnnlNo;
	//통계 종류
	private String typeStats;
	//결제수단 구분
	private String[] pymntMeansCode;
	//디바이스 구분
	private String[] deviceCodeArr;
	//발송처 구분
	private String[] stockGbnCodeArr;
	//시작일
	private String fromDate;
	//종료일
	private String toDate;
	//일별, 월별, 년별 선택값
	private String choicePeriod;
	//에스크로 적용
	private char chkEscroTrue;
	//에스크로 미적용
	private char chkEscroFalse;
	//자사, 입점구분
	private char vndrGbnType;
	//회원구분
	private String[] chkMember;
	//시작시간
	private String fromHour;
	//종료시간
	private String toHour;
	// 브랜드번호
	private String vndrBrandNo;
	//성별 테마
	private String genderTheme;
	//입점사번호
	private String vndrNo;
	//예약상품 여부
	private char reservePrdt;
	//대분류 카테고리
	private String bigCategory;
	//중분류 카테고리
	private String midCategory;
	//소분류 카테고리
	private String smallCategory;
	//상품명
	private String prdtName;
	//업체명
	private String vndrName;
	//클레임 타입
	private String claimType;
	//임직원여부
	private char empYn;
	//입점업체 사이트구분
	private String shoperSiteNo;
			
}
