package kr.co.abcmart.bo.promotion.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplay;
import kr.co.abcmart.bo.promotion.service.PlanningDisplayService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/promotion/planning-display/approval")
public class PlanningDisplayApprovalController extends BaseController {

	@Autowired
	PlanningDisplayService planningDisplayService;

	@Autowired
	SiteService siteService;

	@Autowired
	CommonCodeService commonCodeService;

	@Autowired
	VendorService vendorService;

	/**
	 * 
	 * @Desc : 기획전 승인 관리
	 * @Method Name : approval
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ModelAndView approval(Parameter<?> parameter) throws Exception {

		UserDetails user = LoginManager.getUserDetails();

		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		// PO 관리자인 경우 입점사 번호 세팅
		if (!isAdmin) {
			VdVendor vndr = new VdVendor();
			vndr.setVndrNo(user.getVndrNo());
			vndr = vendorService.getVendorBaseInfo(vndr);

			parameter.addAttribute("vndrNo", vndr.getVndrNo());
		}

		String[] codeFields = { CommonCode.DEVICE_CODE, "PLNDP_STAT_CODE" };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();

		parameter.addAttribute("codeCombo", pair.getFirst());
		parameter.addAttribute("deviceCodeList", codeList.get("DEVICE_CODE"));
		parameter.addAttribute("plndpStatCodeList", codeList.get("PLNDP_STAT_CODE"));

		/** 채널 콤보 */
		List<SySiteChnnl> chnnlList = siteService.getUseChannelList();

		String chnnlName = chnnlList.stream().map(SySiteChnnl::getChnnlName).collect(Collectors.joining("|"));
		String chnnlValue = chnnlList.stream().map(SySiteChnnl::getChnnlNo).collect(Collectors.joining("|"));

		JSONObject json = new JSONObject();
		json.put("code", chnnlValue);
		json.put("text", chnnlName);

		parameter.addAttribute("chnnlList", chnnlList);
		parameter.addAttribute("chnnlCombo", Pair.of(json, chnnlList).getFirst());
		/** 채널 콤보 */

		return forward("/promotion/planning-display/planning-display-approval");
	}

	/**
	 * 
	 * @Desc : 기획전 승인반려 사유 팝업
	 * @Method Name : popup
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/feedback/popup")
	public ModelAndView popup(Parameter<?> parameter) throws Exception {

		return forward("/promotion/planning-display/planning-display-approval-feedback-popup");
	}

	/**
	 * 
	 * @Desc : 기획전 승인 관리 리스트 조회
	 * @Method Name : list
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/list")
	@ResponseBody
	public void list(Parameter<PrPlanningDisplay> parameter) throws Exception {

		UserDetails user = LoginManager.getUserDetails();
		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;

		// pageType : 기획전 승인 관리 (approval)
		parameter.get().setPageType("A");

		// 승인요청, 승인반려 상태의 기획전만 조회
		if (isAdmin && parameter.get().getPlndpStatCodeArr() == null) {
			String[] arr = { "10001", "10002" };
			parameter.get().setPlndpStatCodeArr(arr);
		}

		Pageable<PrPlanningDisplay, PrPlanningDisplay> pageable = new Pageable<>(parameter);

		Page<PrPlanningDisplay> page = planningDisplayService.getPrPlanningDisplayList(pageable);

		writeJson(parameter, page.getGrid());

	}

	/**
	 * 
	 * @Desc : 기획전 승인완료 처리
	 * @Method Name : approve
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/approve")
	@ResponseBody
	public void approve(Parameter<PrPlanningDisplay[]> parameter) throws Exception {

		planningDisplayService.setPlanningDisplayStatusApprove(parameter);
	}

	/**
	 * 
	 * @Desc : 기획전 승인반려 처리
	 * @Method Name : feedback
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/feedback")
	@ResponseBody
	public void feedback(Parameter<PrPlanningDisplay[]> parameter) throws Exception {

		PrPlanningDisplay[] prPlanningDisplay = parameter.get();

		planningDisplayService.updatePrPlanningDisplayStatus(prPlanningDisplay, "10002");

	}

	/**
	 * 
	 * @Desc : 기획전 삭제
	 * @Method Name : remove
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/remove")
	@ResponseBody
	public void remove(Parameter<PrPlanningDisplay[]> parameter) throws Exception {

		PrPlanningDisplay[] prPlanningDisplay = parameter.get();

		planningDisplayService.deletePrPlanningDisplayByArray(prPlanningDisplay);

	}

	/**
	 * 
	 * @Desc : 기획전 승인요청 처리
	 * @Method Name : request
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/request")
	@ResponseBody
	public void request(Parameter<PrPlanningDisplay[]> parameter) throws Exception {

		PrPlanningDisplay[] prPlanningDisplay = parameter.get();

		// 10001 > 승인요청
		planningDisplayService.updatePrPlanningDisplayStatus(prPlanningDisplay, "10001");

	}

}