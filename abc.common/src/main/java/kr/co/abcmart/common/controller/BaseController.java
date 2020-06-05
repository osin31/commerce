package kr.co.abcmart.common.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.constant.BaseConst;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsRequest;
import kr.co.abcmart.common.util.UtilsResponse;
import kr.co.abcmart.common.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseController {

	public ModelAndView forward(String viewName) {
		return forward(viewName, null);
	}

	public ModelAndView forward(String viewName, Map model) {
		uriInfo(viewName);

		ModelAndView mav = new ModelAndView(viewName, model);
		setBrowserTitle(mav);

		return mav;
	}

	public ModelAndView redirect(String url) {
		return redirect(url, null);
	}

	public ModelAndView redirect(String url, Map model) {
		uriInfo(url);
		return new ModelAndView("redirect:/".concat(url), model);
	}

	private void uriInfo(String viewName) {

		String profile = Config.getString("spring.profiles.active", "").toLowerCase();

		// local일 경우 static 경로 등록
		if (BaseConst.PROFILE_LOCAL.equals(profile)) {

			HttpServletRequest request = UtilsRequest.getRequest();

			UrlPathHelper urlPathHelper = new UrlPathHelper();

			log.debug("ACTION URL : {} ", urlPathHelper.getRequestUri(request));
			log.debug("JSP        : {} ", viewName);
		}
	}

	/***
	 * @Desc : 일반 문자나 html을 client 출력 시 사용.
	 * @Method Name : write
	 * @Date : 2019. 2. 25.
	 * @Author : user
	 * @param : parameter Parameter 객체
	 * @param : 문자열
	 */
	public void write(Parameter<?> parameter, String data) {
		parameter.getResponse().setContentType(MediaType.TEXT_PLAIN_VALUE);
		UtilsResponse.write(parameter.getResponse(), data);
	}

	/**
	 * @Desc : Json형식으로 client에 출력시 사용.
	 * @Method Name : writeJson
	 * @Date : 2019. 2. 25.
	 * @Author : user
	 * @param : parameter Parameter 객체
	 * @param : JSON 형식의 문자열
	 */
	public void writeJson(Parameter<?> parameter, String data) {
		parameter.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		UtilsResponse.write(parameter.getResponse(), data);
	}

	/**
	 * @Desc : Json형식으로 client에 출력시 사용.
	 * @Method Name : writeJson
	 * @Date : 2019. 2. 25.
	 * @Author : user
	 * @param : parameter Parameter 객체
	 * @param : data Map 객체로 된 데이터
	 */
	public void writeJson(Parameter<?> parameter, Map<?, ?> data) {
		UtilsResponse.writeJson(parameter.getResponse(), UtilsText.stringify(data));
	}

	/**
	 * @Desc : Json형식으로 client에 출력시 사용.
	 * @Method Name : writeJson
	 * @Date : 2019. 2. 25.
	 * @Author : user
	 * @param : parameter Parameter 객체
	 * @param : data List 객체로 된 데이터
	 */
	public void writeJson(Parameter<?> parameter, List<?> data) {
		UtilsResponse.writeJson(parameter.getResponse(), UtilsText.stringify(data));
	}

	/**
	 * @Desc : Json형식으로 client에 출력시 사용.
	 * @Method Name : writeJson
	 * @Date : 2019. 2. 25.
	 * @Author : user
	 * @param parameter
	 * @param data
	 */
	public void writeJson(Parameter<?> parameter, Object data) {
		UtilsResponse.writeJson(parameter.getResponse(), UtilsText.stringify(data));
	}

	/**
	 * @Desc : @JsonView에 따른 json 출력
	 * @Method Name : writeJson
	 * @Date : 2019. 4. 26.
	 * @Author : Kimyounghyun
	 * @param parameter
	 * @param clazz
	 * @param data
	 */
	public void writeJson(Parameter<?> parameter, Class clazz, Object data) {
		UtilsResponse.writeJson(parameter.getResponse(), UtilsText.stringify(clazz, data));
	}

	/**
	 * @Desc : 사이트&채널 별 타이틀을 set한다.
	 * @Method Name : setBrowserTitle
	 * @Date : 2019. 9. 6.
	 * @Author : Kimyounghyun
	 * @param mav
	 */
	private void setBrowserTitle(ModelAndView mav) {
		String serverName = UtilsRequest.getRequest().getServerName().toLowerCase();
		log.debug("setBrowserTitle.serverName - {}", serverName);

		mav.addObject("browserChannelTitle", UtilsText.getChannelTitle(serverName));
		mav.addObject("browserChannelDescription", UtilsText.getChannelDescription(serverName));
	}

}
