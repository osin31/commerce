package kr.co.abcmart.bo.cmm.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.cmm.model.master.CmDaysCondtn;
import kr.co.abcmart.bo.cmm.service.DaysCondtnService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("cmm")
public class DaysCondtnController extends BaseController {

	@Autowired
	private DaysCondtnService daysCondtnService;

	/**
	 * @Desc : 조건 날짜 관리 화면 호출
	 * @Method Name : read
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/days-condtn")
	public ModelAndView read(Parameter<?> parameter) throws Exception {
		return forward("cmm/days-condtn/read");
	}

	/**
	 * @Desc : 조건 날짜 관리 리스트 호출
	 * @Method Name : readList
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/days-condtn/read")
	@ResponseBody
	public void readList(Parameter<CmDaysCondtn> parameter) throws Exception {

		Pageable<CmDaysCondtn, CmDaysCondtn> dasyVOPageble = new Pageable<>(parameter);

		try {
			Page<CmDaysCondtn> page = daysCondtnService.getDaysCondtnList(dasyVOPageble);

			UtilsResponse.writeJson(parameter.getResponse(), page.getGrid());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @Desc : 조건날짜 관리 목록 수정
	 * @Method Name : updateList
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/days-condtn/update")
	@ResponseBody
	public void updateList(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// modDtm, rgstDtm 은 CmDaysCondtn 처리 제외
		CmDaysCondtn[] params = parameter.createArray(CmDaysCondtn.class, "condtnSetupSeq",
				Arrays.asList("modDtm", "rgstDtm"));

		try {
			daysCondtnService.updateList(params);

			resultMap.put("Message", "저장되었습니다.");
		} catch (Exception e) {
			resultMap.put("Message", "저장에 실패하였습니다.");
		}

		UtilsResponse.writeJson(parameter.getResponse(), resultMap);
	}
}
