package kr.co.abcmart.bo.system.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.system.model.master.SyAppVersion;
import kr.co.abcmart.bo.system.service.AppversionService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/system/appversion")
public class AppVersionController extends BaseController {
	@Autowired
	private AppversionService appversionService;

	/**
	 *
	 * @Desc : APP 버전관리 화면
	 * @Method Name : appversionMain
	 * @Date : 2019. 3. 19.
	 * @Author : choi
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("")
	public ModelAndView appversionMain(Parameter<?> parameter) throws Exception {
		parameter.addAttribute("APP_OS_CODE", appversionService.getCodeNoName());
		parameter.addAttribute("SITE_CODE", appversionService.getSiteList());

		return forward("system/appversion/appversion-main");
	}

	/**
	 * APP 버전을 등록한다.
	 *
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/create")
	@ResponseBody
	public void appversionCreate(Parameter<SyAppVersion> parameter) throws Exception {
		SyAppVersion syAppVersion = parameter.get();

		parameter.validate();
		Map<String, Object> resultMap = appversionService.setAppversionData(syAppVersion);
		writeJson(parameter, resultMap);
	}

	/**
	 * 앱버전 리스트를 조회한다.
	 *
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/read-list")
	@ResponseBody
	public void appversionList(Parameter<SyAppVersion> parameter) throws Exception {
		Pageable<SyAppVersion, SyAppVersion> pageable = new Pageable<>(parameter);

		Page<SyAppVersion> page = appversionService.getAppversionList(pageable);

		writeJson(parameter, page.getGrid());
	}

}
