package kr.co.abcmart.bo.stats.model.master;

import kr.co.abcmart.bo.stats.model.master.base.BaseSaMemberLoginStatus;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SaMemberLoginStatus extends BaseSaMemberLoginStatus {
	private String dateInfo;			// 날짜정보
	private String[] deviceTypeCodeArr;	// 디바이스 구분
	private String periodType;			// 기간 검색 타입
	private String periodMonthStart;	// 시작월
	private String periodMonthEnd;		// 종료월
	private String fromDate;			// 시작일자
	private String toDate;				// 종료일자
	private String periodYearStart;		// 시작년도
	private String periodYearEnd;		// 종료년도
	private String snsLoginYn;	// SNS 계정 로그인 여부
	private String[] chkSnsTypeCodeArr;	// SNS 구분 배열
	private boolean isList=false;		// 리스트인지 엑셀 다운로드용 조회인지 구분

}
