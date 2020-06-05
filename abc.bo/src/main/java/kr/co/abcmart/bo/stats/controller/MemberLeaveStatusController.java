/**
 * 
 */
package kr.co.abcmart.bo.stats.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.stats.model.master.SaMemberLeaveStatus;
import kr.co.abcmart.bo.stats.service.MemberLeaveStatusService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.Const;

@Controller
@RequestMapping("/stats/member/leave")
public class MemberLeaveStatusController extends BaseController {

	@Autowired
	MemberLeaveStatusService leaveService;

	/**
	 * @Desc : 회원탈퇴통계 페이지 호출
	 * @Method Name : memberLeave
	 * @Date : 2019. 6. 18.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("")
	public ModelAndView memberLeave(Parameter<?> parameter) throws Exception {
		Map<String, Object> leaveMap = leaveService.getLeaveMain();
		Calendar cal = Calendar.getInstance();
		cal = Calendar.getInstance();
		parameter.addAttribute("thisYear", String.valueOf(cal.get(Calendar.YEAR)));
		parameter.addAttribute("thisMonth", String.valueOf(cal.get(Calendar.MONTH) + 1));

		SimpleDateFormat sdf2 = new SimpleDateFormat(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS); // 날짜 포맷
		String yyyyMM = sdf2.format(cal.getTime()); // String으로 저장

		parameter.addAttribute(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS, yyyyMM);
		parameter.addAttribute("memberTypeCode", leaveMap.get("memberTypeCode"));

		return forward("/stats/member/member-leave");
	}

	/**
	 * @Desc : 통계 그리드 조회
	 * @Method Name : readMemberLeave
	 * @Date : 2019. 6. 20.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-member-leave")
	public void readMemberLeave(Parameter<SaMemberLeaveStatus> parameter) throws Exception {
		Pageable<SaMemberLeaveStatus, SaMemberLeaveStatus> leaveVO = new Pageable<>(parameter);
		Page<SaMemberLeaveStatus> page = leaveService.getLeaveGrid(leaveVO);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 엑셀 다운로드
	 * @Method Name : downloadExcel
	 * @Date : 2019. 6. 21.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-excel")
	public void downloadExcel(Parameter<SaMemberLeaveStatus> parameter) throws Exception {
		List<SaMemberLeaveStatus> list = leaveService.getLeaveExcelList(parameter.get());
		ExcelValue excelValue = ExcelValue.builder(2, 0).columnNames(Arrays.asList("totalDayC", "total", "type0",
				"type1", "type2", "type3", "type4", "type5", "type6", "type7", "type8", "type9", "type10", "type11"))
				.data(list).build();

		parameter.downloadExcelTemplate("stats/member/excel/memberLeaveStatusList", excelValue);
	}

}
