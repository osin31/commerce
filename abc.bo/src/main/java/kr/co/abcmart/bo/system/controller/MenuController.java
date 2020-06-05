package kr.co.abcmart.bo.system.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.system.model.master.SyMenu;
import kr.co.abcmart.bo.system.service.MenuService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("system/menu")
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;

	/**
	 *
	 * @Desc :메뉴관리 화면호출
	 * @Method Name : main
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("")
	public ModelAndView main(Parameter<?> parameter) throws Exception {
		return forward("system/menu/menu-main");
	}

	/**
	 *
	 * @Desc :권한적용시스템별 메뉴조회
	 * @Method Name : readList
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-list")
	@ResponseBody
	public void readList(Parameter<SyMenu> parameter) throws Exception {
		SyMenu params = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = menuService.getMenuList(params);

		writeJson(parameter, resultMap);
	}

	/**
	 *
	 * @Desc :메뉴저장
	 * @Method Name : update
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/update")
	@ResponseBody
	public void update(Parameter<SyMenu> parameter) throws Exception {
		SyMenu params = parameter.get();
		menuService.setMenu(params);

		writeJson(parameter, true);
	}

	/**
	 *
	 * @Desc :메뉴삭제
	 * @Method Name : delete
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/delete")
	@ResponseBody
	public void delete(Parameter<SyMenu> parameter) throws Exception {
		SyMenu params = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (!parameter.hasRole("ROLE_20000")) {
			resultMap.put("Message", "삭제 권한이 없습니다.");
		} else {
			menuService.deleteMenu(params);
			resultMap.put("result", Const.BOOLEAN_TRUE);
			resultMap.put("Message", "삭제되었습니다.");
		}

		writeJson(parameter, resultMap);
	}

}