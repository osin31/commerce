package kr.co.abcmart.bo.cmm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.cmm.model.master.CmOnlineMemberPolicy;
import kr.co.abcmart.bo.cmm.model.master.CmOnlineMemberPolicyDetail;
import kr.co.abcmart.bo.cmm.service.OnlinePolicyService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/cmm/policy")
public class OnlinePolicyController extends BaseController {
	@Autowired
	private OnlinePolicyService onlinePolicyService;

	/**
	 * @Desc : 온라인 회원혜택 리스트 화면
	 * @Method Name : onlinePolicyMain
	 * @Date : 2019. 2. 8.
	 * @Author : choi
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("")
	public ModelAndView onlinePolicyMain(Parameter<?> parameter) throws Exception {
		return forward("cmm/policy/online-policy-main");
	}

	/**
	 * @Desc : 온라인 혜택 등록/상세 페이지
	 * @Method Name : onlinePolicyPop
	 * @Date : 2019. 2. 8.
	 * @Author : choi
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/online-policy-pop")
	public ModelAndView onlinePolicyPop(Parameter<CmOnlineMemberPolicy> parameter) throws Exception {
		Map<String, Object> result = null;
		String plcySeq = parameter.getString("plcySeq", "");
		String viewPage = (!"".equals(plcySeq) ? "cmm/policy/detail-online-policy" : "cmm/policy/regist-online-policy");
		CmOnlineMemberPolicy cmOnlineMemberPolicy = parameter.get();

		if (!"".equals(plcySeq)) { // 상세일 때 데이터 select
			result = onlinePolicyService.getOnlinePolicyData(cmOnlineMemberPolicy);
			parameter.addAttribute("DATA", result.get("DATA"));
			parameter.addAttribute("PLCY_SEQ", result.get("PLCY_SEQ"));
			parameter.addAttribute("SOLDOUT_DATA_ONE", result.get("SOLDOUT_DATA_ONE"));
			parameter.addAttribute("SOLDOUT_DATA", result.get("SOLDOUT_DATA"));
			parameter.addAttribute("ONLINE_COUPON_LIST", result.get("ONLINE_COUPON_LIST"));
			parameter.addAttribute("MEMBERSHIP_COUPON_LIST", result.get("MEMBERSHIP_COUPON_LIST"));
		}

		return forward(viewPage);
	}

	/**
	 * @Desc : 온라인 회원혜택 '혜택영역' 페이지 로딩
	 * @Method Name : onlineBenafitArea
	 * @Date : 2019. 2. 8.
	 * @Author : choi
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/online-benefit-area")
	public ModelAndView onlineBenafitArea(Parameter<CmOnlineMemberPolicyDetail> parameter) throws Exception {
//		Map<String, Object> attribute = new HashMap<String, Object>();
		CmOnlineMemberPolicyDetail cmOnlineMemberPolicyDetail = parameter.get();

		parameter.addAttribute("benefitIndex", parameter.getString("benefitIndex"));
		if(parameter.getString("plcyDtlSeq") != null) {
			parameter.addAttribute("BENEFIT_DATA", onlinePolicyService.getOnlinePolicyBenefitData(cmOnlineMemberPolicyDetail));
		}

		return forward("cmm/policy/online-benefit-area");
	}

	/**
	 * @Desc : 온라인 혜택 등록/상세 쿠폰 영역 로딩
	 * @Method Name : onlinePolicyPopCpn
	 * @Date : 2019. 1. 31.
	 * @Author : choi
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/online-benefit-area-cpn")
	public void onlinePolicyPopCpn(Parameter<CmOnlineMemberPolicy> parameter) throws Exception {
		CmOnlineMemberPolicy cmOnlineMemberPolicy = parameter.get();

		writeJson(parameter, onlinePolicyService.getOnlinePolicyCpnData(cmOnlineMemberPolicy));
	}

	/**
	 * @Desc : 온라인 회원혜택 리스트 데이터를 조회한다.
	 * @Method Name : onlinePolicyList
	 * @Date : 2019. 2. 8.
	 * @Author : choi
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-list")
	public void onlinePolicyList(Parameter<CmOnlineMemberPolicy> parameter) throws Exception {
		Pageable<CmOnlineMemberPolicy, CmOnlineMemberPolicy> pageable = new Pageable<>(parameter);

		Page<CmOnlineMemberPolicy> page = onlinePolicyService.getOnlinePolicyList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 온라인 회원혜택을 등록한다.
	 * @Method Name : onlinePolicyMain
	 * @Date : 2019. 2. 8.
	 * @Author : choi
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/create")
	public void onlinePolicyCreate(Parameter<CmOnlineMemberPolicy> parameter) throws Exception {
		Map<String,Object> resultMap = new HashMap<String, Object>();

		resultMap = onlinePolicyService.setOnlinePolicyCreate(parameter);

		UtilsResponse.writeJson(parameter.getResponse(), resultMap);
	}

}
