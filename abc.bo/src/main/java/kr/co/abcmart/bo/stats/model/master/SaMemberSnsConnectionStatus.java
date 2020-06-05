package kr.co.abcmart.bo.stats.model.master;

import kr.co.abcmart.bo.stats.model.master.base.BaseSaMemberSnsConnectionStatus;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SaMemberSnsConnectionStatus extends BaseSaMemberSnsConnectionStatus {
	private String dateInfo;			// 날짜정보
	private String periodType;			// 기간 검색 타입
	private String periodMonthStart;	// 시작월
	private String periodMonthEnd;		// 종료월
	private String fromDate;			// 시작일자
	private String toDate;				// 종료일자
	private String periodYearStart;		// 시작년도
	private String periodYearEnd;		// 종료년도
	private boolean isList=false;		// 리스트인지 엑셀 다운로드용 조회인지 구분

//	private String totalMemberCount;
	private String naverTotalCount;
	private String naverManCount;
	private String naverWomanCount;
	private String naverEtcCount;
	private String facebookTotalCount;
	private String facebookManCount;
	private String facebookWomanCount;
	private String facebookEtcCount;
	private String kkoTotalCount;
	private String kkoManCount;
	private String kkoWomanCount;
	private String kkoEtcCount;

	private String naverOnlineCnt;
	private String facebookOnlineCnt;
	private String kkoOnlineCnt;
	private String naverMembershipCnt;
	private String facebookMembershipCnt;
	private String kkoMembershipCnt;
}
