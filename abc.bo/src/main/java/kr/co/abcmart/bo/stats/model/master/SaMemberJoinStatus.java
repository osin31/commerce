package kr.co.abcmart.bo.stats.model.master;

import kr.co.abcmart.bo.stats.model.master.base.BaseSaMemberJoinStatus;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString
public class SaMemberJoinStatus extends BaseSaMemberJoinStatus {
	private String memberType;			// 검색타입[memberType1:회원타입별/성별 회원가입현황, memberType2:연령별/성별 회원가입현황]
	private String dateInfo;			// 날짜정보
	private String totalCnt;			// 데이터 총 개수
	private String[] deviceTypeCodeArr;	// 디바이스 구분
	private String periodType;			// 기간 검색 타입
	private String periodMonthStart;	// 시작월
	private String periodMonthEnd;		// 종료월
	private String fromDate;			// 시작일자
	private String toDate;				// 종료일자
	private String periodYearStart;		// 시작년도
	private String periodYearEnd;		// 종료년도
	private String totMale;				// 전체[남]
	private String totFemale;			// 전체[여]

}
