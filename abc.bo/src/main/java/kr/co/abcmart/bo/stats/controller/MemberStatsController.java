/**
 *
 */
package kr.co.abcmart.bo.stats.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.stats.model.master.SaMemberJoinStatus;
import kr.co.abcmart.bo.stats.model.master.SaMemberLoginStatus;
import kr.co.abcmart.bo.stats.model.master.SaMemberSnsConnectionStatus;
import kr.co.abcmart.bo.stats.service.MemberStatsService;
import kr.co.abcmart.bo.stats.vo.MemberInterestStoreVO;
import kr.co.abcmart.bo.stats.vo.MemberPointStatusVO;
import kr.co.abcmart.bo.stats.vo.MemberStatsVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 회원통계
 * @FileName : MemberStatsController.java
 * @Project : abc.bo
 * @Date : 2019. 6. 17.
 * @Author : Kimyounghyun
 */
@Slf4j
@Controller
@RequestMapping("/stats/member")
public class MemberStatsController extends BaseController {

	@Autowired
	CommonCodeService commonCodeService;

	@Autowired
	MemberStatsService memberStatsService;

	@Autowired
	private SiteService siteService;

	/**
	 * @Desc : 당일회원 가입 현황 화면
	 * @Method Name : todayJoinMain
	 * @Date : 2019. 6. 13.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/today-join-main")
	public ModelAndView todayJoinMain(Parameter<?> parameter) throws Exception {
		String[] codeFields = { CommonCode.DEVICE_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);
		Date currentTime = UtilsDate.getSqlTimeStamp();

		parameter.addAttribute("currentTime", currentTime);
		parameter.addAttribute("codeList", pair.getSecond());

		return forward("/stats/member/today-join-main");
	}

	/**
	 * @Desc : 당일회원 가입 현황 리스트 조회
	 * @Method Name : todayJoinList
	 * @Date : 2019. 6. 13.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/today-join-list")
	public void todayJoinList(Parameter<MemberStatsVO> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MemberStatsVO memberStatsVO = parameter.get();

		dataMap.put("data", memberStatsService.getTodayJoinList(memberStatsVO));

		writeJson(parameter, dataMap);
	}

	/**
	 * @Desc : 당일회원 가입 현황 리스트 엑셀 다운로드
	 * @Method Name : todayJoinList
	 * @Date : 2019. 6. 20.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/today-join-list-excel")
	public void todayJoinListExcel(Parameter<MemberStatsVO> parameter) throws Exception {
		MemberStatsVO memberStatsVO = parameter.get();
		List<MemberStatsVO> list = memberStatsService.getTodayJoinList(memberStatsVO);

		ExcelValue excelValue = ExcelValue.builder(2, 0).columnNames(Arrays.asList("title", "total", "totot", "tomen",
				"tofemale", "toetc", "tmtot", "tmmen", "tmfemale", "tmctot", "tmcmen", "tmcfemale")).data(list).build();

		parameter.downloadExcelTemplate("stats/member/excel/todayJoinMemberList", excelValue);
	}

	/**
	 * @Desc : 회원가입 현황 화면
	 * @Method Name : memberJoinMain
	 * @Date : 2019. 6. 20.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/member-join-main")
	public ModelAndView memberJoinMain(Parameter<MemberStatsVO> parameter) throws Exception {
		String[] codeFields = { CommonCode.DEVICE_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		Calendar cal = Calendar.getInstance();
		parameter.addAttribute("yesterdayMemberInfo", memberStatsService.getYesterdayMemberInfo());

		cal = Calendar.getInstance();
		parameter.addAttribute("thisYear", String.valueOf(cal.get(Calendar.YEAR)));
		parameter.addAttribute("thisMonth", String.valueOf(cal.get(Calendar.MONTH) + 1));

		SimpleDateFormat sdf2 = new SimpleDateFormat(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS); // 날짜 포맷
		String yyyyMM = sdf2.format(cal.getTime()); // String으로 저장

		parameter.addAttribute(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS, yyyyMM);
		parameter.addAttribute("codeList", pair.getSecond());

		return forward("/stats/member/member-join-main");
	}

	/**
	 * @Desc : 회원가입 현황 화면 리스트 조회
	 * @Method Name : todayJoinList
	 * @Date : 2019. 6. 13.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/member-join-list")
	public void memberJoinList(Parameter<SaMemberJoinStatus> parameter) throws Exception {
		SaMemberJoinStatus saMemberJoinStatus = parameter.get();
		Page<SaMemberJoinStatus> page = null;

		Pageable<SaMemberJoinStatus, SaMemberJoinStatus> pageable = new Pageable<>(parameter);
		if ("1".equals(saMemberJoinStatus.getMemberType())) {
			page = memberStatsService.getMemberJoinList(pageable);
		} else {
			page = memberStatsService.getMemberAgeJoinList(pageable);
		}

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 회원가입 현황 화면 리스트 엑셀 다운로드
	 * @Method Name : todayJoinList
	 * @Date : 2019. 6. 13.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/member-join-list-excel")
	public void memberJoinListExcel(Parameter<SaMemberJoinStatus> parameter) throws Exception {
		SaMemberJoinStatus saMemberJoinStatus = parameter.get();
		List<SaMemberJoinStatus> list = null;
		Pageable<SaMemberJoinStatus, SaMemberJoinStatus> pageable = new Pageable<>(parameter);
		pageable.setUsePage(false);

		if ("1".equals(saMemberJoinStatus.getMemberType())) {
			list = memberStatsService.getMemberJoinList(pageable).getContent();
			ExcelValue excelValue = ExcelValue.builder(2, 0)
					.columnNames(Arrays.asList("dateInfo", "totalCnt", "statusField4Count", "statusField1Count",
							"statusField2Count", "statusField3Count", "statusField7Count", "statusField5Count",
							"statusField6Count", "statusField10Count", "statusField8Count", "statusField9Count"))
					.data(list).build();

			parameter.downloadExcelTemplate("stats/member/excel/memberJoinList", excelValue);
		} else {
			list = memberStatsService.getMemberAgeJoinList(pageable).getContent();
			ExcelValue excelValue = ExcelValue.builder(2, 0)
					.columnNames(Arrays.asList("dateInfo", "totMale", "totFemale", "statusField1Count",
							"statusField2Count", "statusField3Count", "statusField4Count", "statusField5Count",
							"statusField6Count", "statusField7Count", "statusField8Count", "statusField9Count",
							"statusField10Count", "statusField11Count", "statusField12Count", "statusField13Count",
							"statusField14Count", "statusField15Count", "statusField16Count"))
					.data(list).build();

			parameter.downloadExcelTemplate("stats/member/excel/memberAgeJoinList", excelValue);
		}

	}

	/**
	 * @Desc : 당일 로그인 현황 화면
	 * @Method Name : todayLoginMain
	 * @Date : 2019. 6. 24.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/today-login-main")
	public ModelAndView todayLoginMain(Parameter<MemberStatsVO> parameter) throws Exception {
		String[] codeFields = { CommonCode.DEVICE_CODE, CommonCode.SNS_GBN_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);
		Date currentTime = UtilsDate.getSqlTimeStamp();

		parameter.addAttribute("currentTime", currentTime);
		parameter.addAttribute("siteList", this.siteService.getSiteList()); // 사이트 정보
		parameter.addAttribute("codeList", pair.getSecond());

		return forward("/stats/member/today-login-main");
	}

	/**
	 * @Desc : 당일 로그인 리스트 조회
	 * @Method Name : todayLoginList
	 * @Date : 2019. 6. 24.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/today-login-list")
	public void todayLoginList(Parameter<MemberStatsVO> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MemberStatsVO memberStatsVO = parameter.get();

		dataMap.put("data", memberStatsService.getTodayLoginList(memberStatsVO));

		writeJson(parameter, dataMap);
	}

	/**
	 * @Desc : 당일 로그인 리스트 엑셀 다운로드
	 * @Method Name : todayLoginListExcel
	 * @Date : 2019. 6. 24.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/today-login-list-excel")
	public void todayLoginListExcel(Parameter<MemberStatsVO> parameter) throws Exception {
		MemberStatsVO memberStatsVO = parameter.get();
		List<MemberStatsVO> list = memberStatsService.getTodayLoginList(memberStatsVO);

		ExcelValue excelValue = ExcelValue.builder(2, 0).columnNames(
				Arrays.asList("dtmTitle", "total", "totot", "tomen", "tofemale", "toetc", "tmtot", "tmmen", "tmfemale"))
				.data(list).build();

		parameter.downloadExcelTemplate("stats/member/excel/todayMemberLoginList", excelValue);
	}

	/**
	 * @Desc : 로그인 현황 페이지
	 * @Method Name : loginMain
	 * @Date : 2019. 6. 24.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/login-main")
	public ModelAndView loginMain(Parameter<SaMemberLoginStatus> parameter) throws Exception {
		String[] codeFields = { CommonCode.DEVICE_CODE, CommonCode.SNS_GBN_CODE, CommonCode.MEMBER_TYPE_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		Calendar cal = Calendar.getInstance();
		parameter.addAttribute("thisYear", String.valueOf(cal.get(Calendar.YEAR)));
		parameter.addAttribute("thisMonth", String.valueOf(cal.get(Calendar.MONTH) + 1));

		SimpleDateFormat sdf = new SimpleDateFormat(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS); // 날짜 포맷
		String yyyyMM = sdf.format(cal.getTime()); // String으로 저장

		parameter.addAttribute(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS, yyyyMM); // 년월정보
		parameter.addAttribute("siteList", this.siteService.getSiteList()); // 사이트 정보
		parameter.addAttribute("codeList", pair.getSecond()); // 코드정보

		return forward("/stats/member/login-main");
	}

	/**
	 * @Desc : 로그인 리스트 조회
	 * @Method Name : todayLoginList
	 * @Date : 2019. 6. 24.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/login-list")
	public void LoginList(Parameter<SaMemberLoginStatus> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SaMemberLoginStatus saMemberLoginStatus = parameter.get();
		saMemberLoginStatus.setList(true);
		System.out.println(saMemberLoginStatus.isList());

		dataMap.put("data", memberStatsService.getLoginList(saMemberLoginStatus));

		writeJson(parameter, dataMap);
	}

	/**
	 * @Desc : 로그인 리스트 엑셀 다운로드
	 * @Method Name : todayLoginList
	 * @Date : 2019. 6. 24.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/login-list-excel")
	public void LoginListExcel(Parameter<SaMemberLoginStatus> parameter) throws Exception {
		SaMemberLoginStatus saMemberLoginStatus = parameter.get();
		List<SaMemberLoginStatus> list = memberStatsService.getLoginList(saMemberLoginStatus);

		ExcelValue excelValue = ExcelValue.builder(2, 0)
				.columnNames(Arrays.asList("dateInfo", "manMemberCount", "womanMemberCount", "etcMemberCount",
						"statusField0Count", "statusField1Count", "statusField2Count", "statusField3Count",
						"statusField4Count", "statusField5Count", "statusField6Count", "statusField7Count",
						"statusField8Count", "statusField9Count", "statusField10Count", "statusField11Count",
						"statusField12Count", "statusField13Count", "statusField14Count", "statusField15Count",
						"statusField16Count", "statusField17Count", "statusField18Count", "statusField19Count",
						"statusField20Count", "statusField21Count", "statusField22Count", "statusField23Count"))
				.data(list).build();

		parameter.downloadExcelTemplate("stats/member/excel/logoinMemberList", excelValue);
	}

	/**
	 * @Desc : SNS 계정 연결 현황
	 * @Method Name : loginMain
	 * @Date : 2019. 6. 28.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sns-connect-main")
	public ModelAndView snsConnectMain(Parameter<SaMemberLoginStatus> parameter) throws Exception {
		String[] codeFields = { CommonCode.MEMBER_TYPE_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		Calendar cal = Calendar.getInstance();
		parameter.addAttribute("thisYear", String.valueOf(cal.get(Calendar.YEAR)));
		parameter.addAttribute("thisMonth", String.valueOf(cal.get(Calendar.MONTH) + 1));

		SimpleDateFormat sdf = new SimpleDateFormat(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS); // 날짜 포맷
		String yyyyMM = sdf.format(cal.getTime()); // String으로 저장

		parameter.addAttribute(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS, yyyyMM); // 년월정보
		parameter.addAttribute("codeList", pair.getSecond()); // 코드정보
		parameter.addAttribute("DATA", memberStatsService.getYesterdaySnsTotal()); // 전일기준 SNS 연결 현황

		return forward("/stats/member/sns-connect-main");
	}

	/**
	 * @Desc : SNS 계정 연결 현황 리스트 조회
	 * @Method Name : todayLoginList
	 * @Date : 2019. 6. 24.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/sns-connect-list")
	public void snsConnectList(Parameter<SaMemberSnsConnectionStatus> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();

		Page<SaMemberSnsConnectionStatus> page = null;
		Pageable<SaMemberSnsConnectionStatus, SaMemberSnsConnectionStatus> pageable = new Pageable<>(parameter);
		page = memberStatsService.getSnsConnectList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : SNS 계정 연결 현황 리스트 다운로드
	 * @Method Name : todayLoginList
	 * @Date : 2019. 6. 24.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/sns-connect-list-excel")
	public void snsConnectListExcel(Parameter<SaMemberSnsConnectionStatus> parameter) throws Exception {
		Page<SaMemberSnsConnectionStatus> page = null;
		Pageable<SaMemberSnsConnectionStatus, SaMemberSnsConnectionStatus> pageable = new Pageable<>(parameter);
		pageable.setUsePage(false);
		page = memberStatsService.getSnsConnectList(pageable);

		ExcelValue excelValue = ExcelValue.builder(2, 0)
				.columnNames(Arrays.asList("dateInfo", "totalMemberCount", "naverTotalCount", "naverManCount",
						"naverWomanCount", "naverEtcCount", "facebookTotalCount", "facebookManCount",
						"facebookWomanCount", "facebookEtcCount", "kkoTotalCount", "kkoManCount", "kkoWomanCount",
						"kkoEtcCount"))
				.data(page.getContent()).build();

		parameter.downloadExcelTemplate("stats/member/excel/snsConnectList", excelValue);
	}

	/*********************************************************************
	 * S : 신인철
	 *******************************************************************/
	/**
	 * @Desc : 단골 매장 현황 메인
	 * @Method Name : readInterestStoreMain
	 * @Date : 2019. 6. 25.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/interest-store-main")
	public ModelAndView readInterestStoreMain(Parameter<?> parameter) throws Exception {
		Date currentTime = UtilsDate.getSqlTimeStamp();

		parameter.addAttribute("currentTime", currentTime);

		return forward("stats/member/member-interest-store");
	}

	/**
	 * @Desc : 단골 매장 현황 조회
	 * @Method Name : readInterestStore
	 * @Date : 2019. 6. 25.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-interest-store")
	public void readInterestStore(Parameter<?> parameter) throws Exception {
		Map<String, Object> gridMap = memberStatsService.getInterestStore();

		writeJson(parameter, gridMap);
	}

	/**
	 * @Desc : 단골 매장 현황 엑셀 다운
	 * @Method Name : interestStoreExcel
	 * @Date : 2019. 6. 25.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/interest-store-excel")
	public void interestStoreExcel(Parameter<?> parameter) throws Exception {
		Map<String, Object> gridMap = memberStatsService.getInterestStore();

		ExcelValue excelValue = ExcelValue.builder(1, 0).columnNames(Arrays.asList("noSeq", "storeName", "storeCount"))
				.data((List<MemberInterestStoreVO>) gridMap.get("Data")).build();
		parameter.downloadExcelTemplate("stats/member/excel/memberInterestStoreList", excelValue);
	}

	/**
	 * @Desc : 단골 브랜드 현황 메인
	 * @Method Name : readInterestBrandMain
	 * @Date : 2019. 6. 25.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/interest-brand-main")
	public ModelAndView readInterestBrandeMain(Parameter<?> parameter) throws Exception {
		Date currentTime = UtilsDate.getSqlTimeStamp();

		parameter.addAttribute("currentTime", currentTime);

		return forward("stats/member/member-interest-brand");
	}

	/**
	 * @Desc : 단골 브랜드 현황 조회
	 * @Method Name : readInterestBrand
	 * @Date : 2019. 6. 25.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-interest-brand")
	public void readInterestBrand(Parameter<?> parameter) throws Exception {
		Map<String, Object> gridMap = memberStatsService.getInterestBrand();

		writeJson(parameter, gridMap);
	}

	/**
	 * @Desc : 단골 브랜드 현황 엑셀 다운
	 * @Method Name : interestBrandExcel
	 * @Date : 2019. 6. 25.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/interest-brand-excel")
	public void interestBrandExcel(Parameter<MemberStatsVO> parameter) throws Exception {
		Map<String, Object> gridMap = memberStatsService.getInterestBrand();

		ExcelValue excelValue = ExcelValue.builder(1, 0).columnNames(Arrays.asList("noSeq", "brandName", "brandCount"))
				.data((List<MemberInterestStoreVO>) gridMap.get("Data")).build();
		parameter.downloadExcelTemplate("stats/member/excel/memberInterestBrandList", excelValue);
	}

	/**
	 * @Desc : 포인트 현황 통계 화면
	 * @Method Name : readPointStatus
	 * @Date : 2019. 7. 9.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/point-status-main")
	public ModelAndView readPointStatus(Parameter<?> parameter) throws Exception {
		Calendar cal = Calendar.getInstance();
		parameter.addAttribute("thisYear", String.valueOf(cal.get(Calendar.YEAR)));
		parameter.addAttribute("thisMonth", String.valueOf(cal.get(Calendar.MONTH) + 1));

		SimpleDateFormat sdf = new SimpleDateFormat(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS); // 날짜 포맷
		String yyyyMM = sdf.format(cal.getTime()); // String으로 저장

		parameter.addAttribute(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS, yyyyMM); // 년월정보

		return forward("stats/member/point-status-main");
	}

	/**
	 * @Desc : 포인트 현황 그리드 조회
	 * @Method Name : readPointStatusList
	 * @Date : 2019. 7. 16.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-point-status")
	public void readPointStatusList(Parameter<MemberPointStatusVO> parameter) throws Exception {
		Pageable<MemberPointStatusVO, MemberPointStatusVO> pointVO = new Pageable<>(parameter);
		Page<MemberPointStatusVO> page = memberStatsService.getPointGrid(pointVO);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 포인트 현황 엑셀 다운로드
	 * @Method Name : pointDownloadExcel
	 * @Date : 2019. 7. 16.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/point-download-excel")
	public void pointDownloadExcel(Parameter<MemberPointStatusVO> parameter) throws Exception {
		List<MemberPointStatusVO> pointData = memberStatsService.getPointExcelList(parameter.get());

		ExcelValue excelValue = ExcelValue
				.builder(2, 0).columnNames(Arrays.asList("totalDayC", "totalPlus", "totalMinus", "type0", "type1",
						"type2", "type3", "type4", "type5", "type8", "type9", "type11", "type12", "type13"))
				.data(pointData).build();
		parameter.downloadExcelTemplate("stats/member/excel/memberPointStatusList", excelValue);
	}
	/*********************************************************************
	 * E : 신인철
	 *******************************************************************/
}
