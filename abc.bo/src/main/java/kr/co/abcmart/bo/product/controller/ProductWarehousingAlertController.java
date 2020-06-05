package kr.co.abcmart.bo.product.controller;

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

import kr.co.abcmart.bo.member.model.master.MbMemberInterestProduct;
import kr.co.abcmart.bo.member.service.MemberInterestProductService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.CommonCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("product/warehousing-alert")
public class ProductWarehousingAlertController extends BaseController {

	@Autowired
	private MemberInterestProductService memberInterestProductService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private CommonCodeService commonCodeService;

	/**
	 * 
	 * @Desc : 재입고 알림 서비스 조회
	 * @Method Name : warehousingAlert
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ModelAndView warehousingAlert(Parameter<?> parameter) throws Exception {

		Pair<JSONObject, List<SySite>> siteInfo = siteService.getSiteListByCombo();

		String[] codeFields = { CommonCode.SELL_STAT_CODE, "WRHS_ALERT_STAT_CODE", CommonCode.STOCK_GBN_CODE };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();

		parameter.addAttribute("siteList", siteService.getSiteList());
		parameter.addAttribute("siteCombo", siteInfo.getFirst());
		parameter.addAttribute("codeCombo", pair.getFirst());

		// 공통코드
		parameter.addAttribute("sellStatCodeList", codeList.get(CommonCode.SELL_STAT_CODE));
		parameter.addAttribute("wrhsAlertStatCodeList", codeList.get("WRHS_ALERT_STAT_CODE"));

		return forward("/product/warehousing-alert/warehousing-alert");
	}

	/**
	 * 
	 * @Desc : 재입고 알림 서비스 리스트 조회
	 * @Method Name : warehousingAlertList
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/list")
	@ResponseBody
	public void warehousingAlertList(Parameter<MbMemberInterestProduct> parameter) throws Exception {

		Page<MbMemberInterestProduct> page = memberInterestProductService.getWarehousingAlertList(parameter);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * 
	 * @Desc : 재입고 알림 서비스 임의처리·일괄처리
	 * @Method Name : sendWarehousingAlert
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/send")
	@ResponseBody
	public void sendWarehousingAlert(Parameter<MbMemberInterestProduct[]> parameter) throws Exception {

		memberInterestProductService.setMsgProcess(parameter.get());
	}

	/**
	 * 
	 * @Desc : 재입고 알림 서비스 신청취소
	 * @Method Name : cancelWarehousingAlert
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/cancel")
	@ResponseBody
	public void cancelWarehousingAlert(Parameter<MbMemberInterestProduct[]> parameter) throws Exception {

		memberInterestProductService.setAlertCancel(parameter.get());
	}

}