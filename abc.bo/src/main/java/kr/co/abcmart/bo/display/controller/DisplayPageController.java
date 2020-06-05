package kr.co.abcmart.bo.display.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.display.model.master.DpDisplayPage;
import kr.co.abcmart.bo.display.model.master.DpDisplayPageCorner;
import kr.co.abcmart.bo.display.model.master.DpDisplayTemplateCorner;
import kr.co.abcmart.bo.display.service.DisplayPageService;
import kr.co.abcmart.bo.display.service.DisplayTemplateService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.CommonCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("display/page")
public class DisplayPageController extends BaseController {

	@Autowired
	DisplayPageService displayPageService;

	@Autowired
	CommonCodeService commonCodeService;

	@Autowired
	SiteService siteService;

	@Autowired
	DisplayTemplateService displayTemplateService;

	/**
	 * 
	 * @Desc : 전시 페이지 관리
	 * @Method Name : page
	 * @Date : 2019. 3. 12.
	 * @Author : 이가영
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping({ "/", "" })
	public ModelAndView page(Parameter<DpDisplayPage> parameter) throws Exception {

		String[] codeFields = { CommonCode.DISP_PAGE_TYPE_CODE };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		List<SySite> siteList = siteService.getSiteList();

		List<SySiteChnnl> channelList = siteService.getUseChannelList();

		parameter.addAttribute("codeList", pair.getSecond());
		parameter.addAttribute("codeCombo", pair.getFirst());
		parameter.addAttribute("siteList", siteList);
		parameter.addAttribute("channelList", channelList);

		return forward("/display/page/page");

	}

	/**
	 * 
	 * @Desc : 전시 페이지 관리(조회)
	 * @Method Name : list
	 * @Date : 2019. 3. 12.
	 * @Author : 이가영
	 * @param parameter
	 * @throws Exception
	 */
	@Deprecated
	public void list33(Parameter<DpDisplayPage> parameter) throws Exception {

		List<DpDisplayPage> result = displayPageService.getDpDisplayPageList(parameter.get());

		writeJson(parameter, result);

	}

	/**
	 * @Desc : 전시 페이지 관리 (조회)
	 * @Method Name : readDisplayPageList
	 * @Date : 2019. 12. 12.
	 * @Author : sic
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/list")
	public void list(Parameter<DpDisplayPage> parameter) throws Exception {

		Map<String, Object> result = displayPageService.getDisplayPageList(parameter.get());

		writeJson(parameter, result);

	}

	/**
	 * 
	 * @Desc : 전시 페이지 관리(등록)
	 * @Method Name : add
	 * @Date : 2019. 3. 13.
	 * @Author : 이가영
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/add")
	public void add(Parameter<DpDisplayPage> parameter) throws Exception {

		parameter.validate();

		displayPageService.insertDpDisplayPage(parameter.get());

	}

	/**
	 * 
	 * @Desc : 전시 페이지 관리(수정)
	 * @Method Name : modify
	 * @Date : 2019. 3. 13.
	 * @Author : 이가영
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/modify")
	public void modify(Parameter<DpDisplayPage> parameter) throws Exception {

		parameter.validate();

		displayPageService.updateDpDisplayPage(parameter.get());
	}

	/**
	 * 
	 * @Desc : 콘텐츠 목록 조회
	 * @Method Name : readContentsList
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/contents/list")
	@ResponseBody
	public void readContentsList(Parameter<DpDisplayPageCorner> parameter) throws Exception {

		Pageable<DpDisplayPageCorner, DpDisplayTemplateCorner> pageable = new Pageable<>(parameter);

		Page<DpDisplayTemplateCorner> page = displayPageService.getDpDisplayPageCornerList(pageable);

		writeJson(parameter, page.getGrid());

	}

	/**
	 * @Desc : 전시 콘텐츠관리 전시여부 수정
	 * @Method Name : updateDispCornerDispYn
	 * @Date : 2019. 12. 16.
	 * @Author : sic
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/content/updateDispCornerDispYn")
	public void updateDispCornerDispYn(Parameter<DpDisplayPageCorner> parameter) throws Exception {
		displayPageService.updateDispCornerDispYn(parameter.get());

	}

	/**
	 * @Desc : 채널에 맞는 uri 조회
	 * @Method Name : readCurrentPreviewUrl
	 * @Date : 2019. 12. 27.
	 * @Author : sic
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/content/getCurrentPreviewUrl")
	@ResponseBody
	public String readCurrentPreviewUrl(Parameter<?> parameter) throws Exception {
		String previewUri = displayPageService.getCurrentPreviewUrl(parameter.getString("chnnlNo"),
				parameter.getString("deviceCode"));

		return previewUri;
	}

	/**
	 * @Desc : 채널번호로 사이트 번호 조회
	 * @Method Name : getSelectedSiteNoByChnnl
	 * @Date : 2019. 12. 16.
	 * @Author : sic
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("getSelectedSiteNoByChnnl")
	public void getSelectedSiteNoByChnnl(Parameter<DpDisplayPage> parameter) throws Exception {
		String siteNo = siteService.getSiteNo(parameter.get().getChnnlNo());
		writeJson(parameter, siteNo);
	}

}