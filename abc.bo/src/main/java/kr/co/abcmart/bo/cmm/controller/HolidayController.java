package kr.co.abcmart.bo.cmm.controller;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.cmm.model.master.CmHoliday;
import kr.co.abcmart.bo.cmm.service.HolidayService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsDate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/cmm/holiday")
public class HolidayController extends BaseController {
	@Autowired
	private HolidayService holidayService;

	/**
	 *
	 * @Desc : 휴일관리 화면 페이지[시스템 관리자]
	 * @Method Name : holidayMain
	 * @Date : 2019. 2. 19.
	 * @Author : choi
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	// @Access(menuNo="10018", successRsnText="휴일관리 화면", accessType="R10")
	@RequestMapping("")
	public ModelAndView holidayMain(Parameter<?> parameter) throws Exception {
		Map<String, Object> result = holidayService.getYearInfo();

		parameter.addAttribute("vendorList", holidayService.getVendorList()); // 업체 리스트
		parameter.addAttribute("beforeTenYear", result.get("beforeTenYear"));
		parameter.addAttribute("thisYear", result.get("thisYear"));
		parameter.addAttribute("nextYear", result.get("nextYear"));
		parameter.addAttribute("thisMonth", result.get("thisMonth"));
		parameter.addAttribute("authType", Const.AUTH_APPLY_SYSTEM_TYPE_BO); // BO 권한 세팅

		return forward("cmm/holiday/holiday-main");
	}

	/**
	 *
	 * @Desc : 휴일관리 화면 페이지[업체 관리자]
	 * @Method Name : holidayVendorMain
	 * @Date : 2019. 2. 19.
	 * @Author : choi
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("vendor")
	public ModelAndView holidayVendorMain(Parameter<?> parameter) throws Exception {
		Map<String, Object> result = holidayService.getYearInfo();

		List<Map<String, Object>> vendorList = holidayService.getVendorList();
		parameter.addAttribute("vendorList", vendorList); // 업체 리스트
		parameter.addAttribute("vendorListSize", vendorList.size()); // 업체 리스트 사이트
		parameter.addAttribute("beforeTenYear", result.get("beforeTenYear"));
		parameter.addAttribute("thisYear", result.get("thisYear"));
		parameter.addAttribute("nextYear", result.get("nextYear"));
		parameter.addAttribute("authType", Const.AUTH_APPLY_SYSTEM_TYPE_PO); // PO 권한 세팅

		return forward("cmm/holiday/holiday-main");
	}

	/**
	 *
	 * @Desc : 휴일관리 캘린더 팝업 페이지
	 * @Method Name : holidayDetailCalendarPop
	 * @Date : 2019. 2. 19.
	 * @Author : choi
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/detail-calendar-pop")
	public ModelAndView holidayDetailCalendarPop(Parameter<?> parameter) throws Exception {
		CmHoliday cmHoliday = parameter.create(CmHoliday.class);
		Calendar cal = Calendar.getInstance();

		int currentMonth = cal.get(Calendar.MONTH) + 1;
		int month = parameter.getInt("month", currentMonth);
		String year = parameter.getString("year");
		String currentYear = String.valueOf(cal.get(Calendar.YEAR));

		if (year == null) {
			year = currentYear; // 올해
		}

		String monthString = (month < 10 ? "0" + month : String.valueOf(month));
		cmHoliday.setYyyyMm(year + monthString);

		cal.set(Integer.parseInt(year), month - 1, 1);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 2019년 1월 1일의 요일 인덱스(일:1,월:2...토:7)
		cal.set(Integer.parseInt(year), month - 1, 1);

		cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -10); // -10년
		parameter.addAttribute("beforeTenYear", String.valueOf(cal.get(Calendar.YEAR))); // 10년 전 세팅

		cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1); // +1년
		parameter.addAttribute("nextYear", String.valueOf(cal.get(Calendar.YEAR))); // 내년
		parameter.addAttribute("today", UtilsDate.today("yyyyMMdd"));
		parameter.addAttribute("year", year);
		parameter.addAttribute("month", monthString);
		parameter.addAttribute("dayStartIndex", dayOfWeek);
		parameter.addAttribute("list", holidayService.getHolidayDetailCalendarPop(cmHoliday));

		return forward("cmm/holiday/detail-calendar-pop");
	}

	/**
	 *
	 * @Desc : 년도의 휴일관리 데이터를 TextArea에 세팅한다.
	 * @Method Name : holidayDetail
	 * @Date : 2019. 2. 19.
	 * @Author : choi
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-detail")
	public void holidayDetail(Parameter<CmHoliday> parameter) throws Exception {
		CmHoliday cmHoliday = parameter.get();
		List<?> resultList = holidayService.getHolidayDate(cmHoliday);

		writeJson(parameter, resultList);
	}

	/**
	 *
	 * @Desc : 그리드에 휴일관리 리스트 데이터를 조회한다.
	 * @Method Name : holidayList
	 * @Date : 2019. 2. 19.
	 * @Author : choi
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-list")
	public void holidayList(Parameter<CmHoliday> parameter) throws Exception {
		Pageable<CmHoliday, CmHoliday> pageable = new Pageable<>(parameter);

		Page<CmHoliday> page = holidayService.getHolidayList(pageable);
		writeJson(parameter, page.getGrid());
	}

	/**
	 *
	 * @Desc : 휴일정보를 등록한다.
	 * @Method Name : holidayCreate
	 * @Date : 2019. 3. 19.
	 * @Author : choi
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/create")
	public void holidayCreate(Parameter<CmHoliday> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CmHoliday cmHoliday = parameter.get();
		parameter.validate();

		holidayService.setHolidayDate(cmHoliday);

		writeJson(parameter, resultMap);
	}

	/**
	 *
	 * @Desc : 그리드의 휴일에서 제거한다.[UPDATE]
	 * @Method Name : holidayDelete
	 * @Date : 2019. 3. 19.
	 * @Author : choi
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/update")
	public void holidayUpdate(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CmHoliday cmHoliday = parameter.create(CmHoliday.class);
		CmHoliday[] cmHolidayArray = parameter.createArray(CmHoliday.class, "hldySeq", Arrays.asList("rgstDtm"));

		resultMap = holidayService.setUpdateHoliday(cmHoliday, cmHolidayArray);
		writeJson(parameter, resultMap);
	}
}
