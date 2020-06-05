package kr.co.abcmart.bo.display.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.abcmart.bo.display.model.master.DpDisplayTemplate;
import kr.co.abcmart.bo.display.model.master.DpDisplayTemplateCorner;
import kr.co.abcmart.bo.display.model.master.DpDisplayTemplateCornerSet;
import kr.co.abcmart.bo.display.service.DisplayTemplateService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.message.Message;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsResponse;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("display/template")
public class DisplayTemplateController extends BaseController {

	@Autowired
	private DisplayTemplateService displayTemplateService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private SiteService siteService;

//	@Autowired
	private ObjectMapper mapper;

	/**
	 *
	 * @Desc : 전시 템픞릿 관리
	 * @Method Name : template
	 * @Date : 2019. 2. 18.
	 * @Author : SANTA
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping({ "/", "" })
	public ModelAndView template(Parameter<?> parameter) throws Exception {

		String[] codeFields = { CommonCode.TMPL_TYPE_CODE, CommonCode.DEVICE_CODE };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);
//		List<SySite> siteList = siteService.getUseSiteList();
		List<SySite> siteList = siteService.getSiteList();

//		List<SySiteChnnl> channelList = siteService.getUseChannelList();

		parameter.addAttribute("codeList", pair.getSecond());
		parameter.addAttribute("siteList", siteList);
//		parameter.addAttribute("channelList", channelList);

		return forward("/display/template/template");
	}

	/**
	 *
	 * @Desc : 전시템플릿 관리(상세)
	 * @Method Name : detail
	 * @Date : 2019. 2. 18.
	 * @Author : SANTA
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/detail")
	public ModelAndView detail(Parameter<DpDisplayTemplate> parameter) throws Exception {

		String[] codeFields = { CommonCode.TMPL_TYPE_CODE, CommonCode.DEVICE_CODE };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);
//		List<SySite> siteList = siteService.getUseSiteList();
		List<SySite> siteList = siteService.getSiteList();
		List<SySiteChnnl> channelList = siteService.getUseChannelList();

		DpDisplayTemplate dpDisplayTemplate = parameter.get();
		if (!UtilsText.isBlank(dpDisplayTemplate.getDispTmplNo())) {
			dpDisplayTemplate = displayTemplateService.getTemplate(dpDisplayTemplate);
		}

		parameter.addAttribute("codeList", pair.getSecond());
		parameter.addAttribute("siteList", siteList);
		parameter.addAttribute("channelList", channelList);
		parameter.addAttribute("dpDisplayTemplate", dpDisplayTemplate);

		return forward("/display/template/template-detail");
	}

	/**
	 *
	 * @Desc : 전시 템플릿 관리(저장)
	 * @Method Name : save
	 * @Date : 2019. 2. 18.
	 * @Author : SANTA
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/save")
	public void save(DpDisplayTemplate dpDisplayTemplate, HttpServletResponse response) throws Exception {

		dpDisplayTemplate.validate();

		displayTemplateService.insertTemplate(dpDisplayTemplate);

		UtilsResponse.writeJson(response, dpDisplayTemplate.getDispTmplNo());
	}

	/**
	 *
	 * @Desc : 전시 템플릿 관리(수정)
	 * @Method Name : modify
	 * @Date : 2019. 2. 18.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/modify")
	public void modify(DpDisplayTemplate dpDisplayTemplate, HttpServletResponse response) throws Exception {

		dpDisplayTemplate.validate();

//		 현재 템플릿 사용중일 경우
		if (displayTemplateService.isTmplUsing(dpDisplayTemplate.getDispTmplNo())) {
			throw new ValidationException(Message.getMessage("display.template.valid.isTmplUsing"));
		}

		displayTemplateService.updateTemplate(dpDisplayTemplate);

		UtilsResponse.writeJson(response, dpDisplayTemplate.getDispTmplNo());
	}

	/**
	 *
	 * @Desc : 전시 템플릿 관리(리스트 조회)
	 * @Method Name : list
	 * @Date : 2019. 2. 18.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/list")
	public void list(Parameter<DpDisplayTemplate> parameter) throws Exception {

		Pageable<DpDisplayTemplate, DpDisplayTemplate> pageable = new Pageable<>(parameter);

		Page<DpDisplayTemplate> page = displayTemplateService.getTemplateList(pageable);

		writeJson(parameter, page.getGrid());

	}

	/**
	 *
	 * @Desc : 코너 등록 화면
	 * @Method Name : cornerPop
	 * @Date : 2019. 2. 18.
	 * @Author : SANTA
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/corner-pop")
	public ModelAndView cornerPop(Parameter<DpDisplayTemplateCorner> parameter) throws Exception {

		//
		String[] codeFields = { CommonCode.CONT_TYPE_CODE };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		DpDisplayTemplateCorner dpDisplayTemplateCorner = parameter.get();

		parameter.addAttribute("codeList", pair.getSecond());

		if (!UtilsText.isBlank(dpDisplayTemplateCorner.getDispTmplNo())) {

			DpDisplayTemplateCornerSet dpDisplayTemplateCornerSet = new DpDisplayTemplateCornerSet();

			dpDisplayTemplateCorner = displayTemplateService.getTemplateCorner(dpDisplayTemplateCorner);

			dpDisplayTemplateCornerSet.setDispTmplNo(dpDisplayTemplateCorner.getDispTmplNo());
			dpDisplayTemplateCornerSet.setDispTmplCornerSeq(dpDisplayTemplateCorner.getDispTmplCornerSeq());

			List<DpDisplayTemplateCornerSet> dpDisplayTemplateCornerSetList = displayTemplateService
					.getTemplateCornerSetList(dpDisplayTemplateCornerSet);

			parameter.addAttribute("corner", dpDisplayTemplateCorner);
			parameter.addAttribute("list", dpDisplayTemplateCornerSetList);
		}

		return forward("/display/template/corner-pop");
	}

	/**
	 *
	 * @Desc : 코너 등록
	 * @Method Name : cornerSave
	 * @Date : 2019. 2. 18.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/corner/save")
	public void cornerSave(Parameter<DpDisplayTemplate> parameter) throws Exception {

		writeJson(parameter, parameter);
	}

	/**
	 *
	 * @Desc : 사이트 별 채널 조회
	 * @Method Name : getChannelInfo
	 * @Date : 2019. 11. 4.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/get-channel-info")
	public void getChannelInfo(Parameter<?> parameter) throws Exception {

		String siteNo = parameter.getString("siteNo");

		List<SySiteChnnl> channelList = siteService.getChannelListBySiteNo(siteNo, true);

		writeJson(parameter, channelList);

	}

	/**
	 *
	 * @Desc : 전시 코너 리스트 조회
	 * @Method Name : cornerList
	 * @Date : 2019. 11. 4.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/corner/list")
	public void cornerList(Parameter<DpDisplayTemplateCorner> parameter) throws Exception {

		Pageable<DpDisplayTemplateCorner, DpDisplayTemplateCorner> pageable = new Pageable<>(parameter);

		Page<DpDisplayTemplateCorner> page = displayTemplateService.getTemplateCornerList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 *
	 * @Desc : 코너 삭제
	 * @Method Name : cornerDelete
	 * @Date : 2019. 3. 4.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/corner/delete")
	public void cornerDelete(Parameter<DpDisplayTemplateCorner> parameter) throws Exception {

		DpDisplayTemplateCorner dpDisplayTemplateCorner = parameter.get();

		// 현재 템플릿 사용중일 경우
		if (displayTemplateService.isTmplUsing(dpDisplayTemplateCorner.getDispTmplNo())) {
			throw new ValidationException(Message.getMessage("display.template.valid.isTmplUsing"));
		}

		displayTemplateService.deleteCorner(dpDisplayTemplateCorner);

		writeJson(parameter, dpDisplayTemplateCorner);
	}

	/**
	 *
	 *
	 * @Desc : 전시 템플릿 조회 팝업
	 * @Method Name : templatePop
	 * @Date : 2019. 11. 4.
	 * @Author : SANTA
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/pop")
	public ModelAndView templatePop(Parameter<DpDisplayTemplate> parameter) throws Exception {

		DpDisplayTemplate dpDisplayTemplate = parameter.get();

		String[] codeFields = { CommonCode.TMPL_TYPE_CODE, CommonCode.DEVICE_CODE };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);
		List<SySite> siteList = siteService.getSiteList();
		List<SySiteChnnl> channelList = siteService.getUseChannelList();

		parameter.addAttribute("codeList", pair.getSecond());
		parameter.addAttribute("siteList", siteList);
		parameter.addAttribute("channelList", channelList);
		parameter.addAttribute("dpDisplayTemplate", dpDisplayTemplate);

		return forward("/display/template/template-pop");
	}

}