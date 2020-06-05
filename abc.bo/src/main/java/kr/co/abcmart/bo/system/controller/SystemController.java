package kr.co.abcmart.bo.system.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsRequest;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("system")
public class SystemController extends BaseController {

	/**
	 * @Desc : 로그인 화면 호출
	 * @Method Name : login
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public ModelAndView login(Parameter<?> parameter) throws Exception {
		String expired = parameter.getString("expired", Const.BOOLEAN_FALSE);
		String serverName = UtilsRequest.getRequest().getServerName().toLowerCase();
		log.debug("************************************");
		log.debug("serverName = {}, Const.LOGIN_DOMAIN_BO = {}", serverName, Const.LOGIN_DOMAIN_BO);
		log.debug("************************************");

		Map<String, String> attribute = new HashMap<>();
		attribute.put("expired", expired);
		String viewName = "system/login/login_manager";

		// 입점업체 로그인 화면
		if (!serverName.startsWith(Const.LOGIN_DOMAIN_BO)) {
			viewName = "system/login/login_seller_office";
		}

		return forward(viewName, attribute);
	}

	@RequestMapping("/codeInfo")
	public ModelAndView codeList(Parameter<?> parameter) throws Exception {

		return forward("system/codeInfo");
	}

}