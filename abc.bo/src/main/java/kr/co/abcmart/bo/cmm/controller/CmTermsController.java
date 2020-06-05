package kr.co.abcmart.bo.cmm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.cmm.model.master.CmTerms;
import kr.co.abcmart.bo.cmm.model.master.CmTermsDetail;
import kr.co.abcmart.bo.cmm.service.CmTermsService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.CommonCode;

@Controller
@RequestMapping("cmm/terms")
public class CmTermsController extends BaseController {
	@Autowired
	CmTermsService cmTermsService;

	/**
	 * @Desc : 메인화면 호출
	 * @Method Name : termsMain
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("")
	public ModelAndView termsMain(Parameter<?> parameter) throws Exception {
		Map<String, Object> termsSettingMap = cmTermsService.getTermsPage();

		parameter.addAttribute("termsTypeCode", (List<SyCodeDetail>) termsSettingMap.get("termsTypeCode"));
		parameter.addAttribute("termsUseCode", (List<SyCodeDetail>) termsSettingMap.get("termsDtlCode"));
		parameter.addAttribute("privacyCodeList", (List<SyCodeDetail>) termsSettingMap.get("privacyCodeList"));
		parameter.addAttribute("signUpCodeList", (List<SyCodeDetail>) termsSettingMap.get("signUpCodeList"));
		parameter.addAttribute("orderCodeList", (List<SyCodeDetail>) termsSettingMap.get("orderCodeList"));

		if (parameter.getString("tabIndex") != null) {
			parameter.addAttribute("tabIndex", parameter.getString("tabIndex"));
		}

		return forward("/cmm/terms/terms-main");
	}

	/**
	 * @Desc : 이용약관 설정 등록 화면
	 * @Method Name : termsofuseForm
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/termsofuse-form")
	public ModelAndView termsofuseForm(Parameter<?> parameter) throws Exception {
		List<SyCodeDetail> termsDtlList = cmTermsService.getTermsCodeList(CommonCode.TERMS_TYPE_CODE_TERMSOFUSE);
		parameter.addAttribute("termsDtlList", termsDtlList);

		return forward("/cmm/terms/termsofuse-form");
	}

	/**
	 * @Desc : 개인정보 취급방침 등록화면
	 * @Method Name : privacyStatementForm
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/privacy-statement-form")
	public ModelAndView privacyStatementForm(Parameter<?> parameter) throws Exception {

		return forward("/cmm/terms/privacy-statement-form");
	}

	/**
	 * @Desc : 회원가입 동의 약관 등록화면
	 * @Method Name : signupAgreementForm
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/signup-regist-form")
	public ModelAndView signupAgreementForm(Parameter<?> parameter) throws Exception {
		List<SyCodeDetail> signUpCodeList = cmTermsService.getTermsCodeList(CommonCode.TERMS_TYPE_CODE_SIGNUP);
		parameter.addAttribute("signUpCodeList", signUpCodeList);
		parameter.addAttribute("tabIndex", parameter.getString("tabIndex"));

		return forward("/cmm/terms/signup-regist-form");
	}

	/**
	 * @Desc : 회원가입동의 약관 등록 페이지에서 에디터 영역 호출
	 * @Method Name : signUpListArea
	 * @Date : 2019. 2. 11.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/signup-list-area")
	public ModelAndView signUpListArea(Parameter<?> parameter) throws Exception {
		String signUpListIndex = parameter.getString("signUpListIndex");
		String termsDtlSeq = parameter.getString("termsDtlSeq");

		parameter.addAttribute("signUpListIndex", signUpListIndex);
		parameter.addAttribute("termsDtlSeq", termsDtlSeq);
		return forward("cmm/terms/signup-list-area");
	}

	/**
	 * @Desc : 회원가입동의 상세보기시에 영역 호출
	 * @Method Name : signUpDetailArea
	 * @Date : 2019. 2. 11.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/signup-detail-area")
	public ModelAndView signUpDetailArea(Parameter<?> parameter) throws Exception {
		CmTermsDetail cmTermsDetail = parameter.create(CmTermsDetail.class);
		String signUpListIndex = parameter.getString("signUpListIndex");
		String termsDtlSeq = parameter.getString("termsDtlSeq");
		cmTermsDetail = cmTermsService.getTermsDetailArea(cmTermsDetail);

		parameter.addAttribute("cmTermsDetail", cmTermsDetail);
		parameter.addAttribute("signUpListIndex", signUpListIndex);
		parameter.addAttribute("termsDtlSeq", termsDtlSeq);

		return forward("cmm/terms/signup-list-area");
	}

	/**
	 * @Desc : 주문동의 상세보기시에 영역 호출
	 * @Method Name : orderDetailArea
	 * @Date : 2019. 2. 12.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/order-detail-area")
	public ModelAndView orderDetailArea(Parameter<?> parameter) throws Exception {
		CmTermsDetail cmTermsDetail = parameter.create(CmTermsDetail.class);
		String orderListIndex = parameter.getString("orderListIndex");
		String termsDtlSeq = parameter.getString("termsDtlSeq");
		cmTermsDetail = cmTermsService.getTermsDetailArea(cmTermsDetail);

		parameter.addAttribute("cmTermsDetail", cmTermsDetail);
		parameter.addAttribute("orderListIndex", orderListIndex);
		parameter.addAttribute("termsDtlSeq", termsDtlSeq);

		return forward("cmm/terms/order-list-area");
	}

	/**
	 * @Desc : 주문동의 약관 등록페이지 에서 에디터 영역 호출
	 * @Method Name : onlineBenafitArea
	 * @Date : 2019. 2. 11.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/order-list-area")
	public ModelAndView orderListArea(Parameter<?> parameter) throws Exception {
		String orderListIndex = parameter.getString("orderListIndex");
		String termsDtlSeq = parameter.getString("termsDtlSeq");

		parameter.addAttribute("orderListIndex", orderListIndex);
		parameter.addAttribute("termsDtlSeq", termsDtlSeq);
		return forward("cmm/terms/order-list-area");
	}

	/**
	 * @Desc : 주문동의 약관 등록화면
	 * @Method Name : orderAcceptanceForm
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/order-regist-form")
	public ModelAndView orderRegistForm(Parameter<?> parameter) throws Exception {
		List<SyCodeDetail> orderCodeList = cmTermsService.getTermsCodeList(CommonCode.TERMS_TYPE_CODE_ORDER);
		parameter.addAttribute("orderCodeList", orderCodeList);

		return forward("/cmm/terms/order-regist-form");
	}

	/**
	 * @Desc : 그리드 호출
	 * @Method Name : readTermsGrid
	 * @Date : 2019. 1. 31.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-terms-grid")
	public void readTermsGrid(Parameter<CmTerms> parameter) throws Exception {
		Pageable<CmTerms, CmTerms> cmTerms = new Pageable<>(parameter);
		Page<CmTerms> page = cmTermsService.getTermsGrid(cmTerms);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 이용약관 상세보기
	 * @Method Name : readTermsOfUseDetail
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-termsofuse-detail")
	public ModelAndView readTermsOfUseDetail(Parameter<?> parameter) throws Exception {
		parameter.validate();
		CmTerms cmTerms = parameter.create(CmTerms.class);
		CmTermsDetail cmTermsDetail = parameter.create(CmTermsDetail.class);

		Map<String, Object> termsMap = cmTermsService.getTermsOfUseDetail(cmTerms, cmTermsDetail);

		parameter.addAttribute("cmTermsDetail", termsMap.get("cmTermsDetail"));
		parameter.addAttribute("cmTerms", termsMap.get("cmTerms"));
		parameter.addAttribute("termsDtlList", termsMap.get("termsDtlList"));
		return forward("/cmm/terms/termsofuse-form");

	}

	/**
	 * @Desc : 개인정보 취급방침 상세보기
	 * @Method Name : readPrivacyDetail
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-privacy-detail")
	public ModelAndView readPrivacyDetail(Parameter<?> parameter) throws Exception {
		CmTerms cmTerms = parameter.create(CmTerms.class);
		CmTermsDetail cmTermsDetail = parameter.create(CmTermsDetail.class);
		Map<String, Object> termsMap = cmTermsService.getPrivacyDetail(cmTerms, cmTermsDetail);

		parameter.addAttribute("cmTerms", termsMap.get("cmTerms"));
		parameter.addAttribute("cmTermsDetail", termsMap.get("cmTermsDetail"));

		parameter.addAttribute("termsTypeCode", CommonCode.TERMS_TYPE_CODE_PRIVACY);
		parameter.addAttribute("termsDtlCode", CommonCode.TERMS_DTL_CODE_PRIVACY);

		return forward("/cmm/terms/privacy-statement-form");
	}

	/**
	 * @Desc : 회원가입동의 약관 상세 화면
	 * @Method Name : readSignUpDetail
	 * @Date : 2019. 2. 7.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-signup-detail")
	public ModelAndView readSignUpDetail(Parameter<?> parameter) throws Exception {
		CmTerms cmTerms = parameter.create(CmTerms.class);
		CmTermsDetail cmTermsDetail = parameter.create(CmTermsDetail.class);
		Map<String, Object> termsMap = cmTermsService.getSignUpOrderDetail(cmTerms, cmTermsDetail);

		parameter.addAttribute("cmTerms", termsMap.get("cmTerms"));
		parameter.addAttribute("cmTermsDetailList", termsMap.get("cmTermsDetail"));
		parameter.addAttribute("signUpCodeList",
				(List<SyCodeDetail>) cmTermsService.getTermsCodeList(CommonCode.TERMS_TYPE_CODE_SIGNUP));
		return forward("/cmm/terms/signup-detail-form");
	}

	/**
	 * @Desc : 주문동의 약관 상세 화면
	 * @Method Name : readOrderDetail
	 * @Date : 2019. 2. 12.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-order-detail")
	public ModelAndView readOrderDetail(Parameter<?> parameter) throws Exception {
		CmTerms cmTerms = parameter.create(CmTerms.class);
		CmTermsDetail cmTermsDetail = parameter.create(CmTermsDetail.class);
		Map<String, Object> termsMap = cmTermsService.getSignUpOrderDetail(cmTerms, cmTermsDetail);

		parameter.addAttribute("cmTerms", termsMap.get("cmTerms"));
		parameter.addAttribute("cmTermsDetailList", termsMap.get("cmTermsDetail"));
		parameter.addAttribute("orderCodeList",
				(List<SyCodeDetail>) cmTermsService.getTermsCodeList(CommonCode.TERMS_TYPE_CODE_ORDER));
		return forward("/cmm/terms/order-detail-form");
	}

	/**
	 * @Desc : 이용약관, 개인정보취급방침 등록
	 * @Method Name : createTermsOfUse
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/create-termsofuse-privacy")
	public void createTermsOfUsePrivacy(Parameter<?> parameter) throws Exception {
		CmTerms termsParam = parameter.create(CmTerms.class);
		CmTermsDetail detailParam = parameter.create(CmTermsDetail.class);
		detailParam.setTermsInfo(parameter.getString("termsInfo", false));
		Map<String, Object> rsMap = cmTermsService.setTermsOfUsePrivacy(termsParam, detailParam);

		writeJson(parameter, rsMap);
	}

	/**
	 * @Desc : 회원가입동의, 주문동의약관 등록
	 * @Method Name : createSignUpTerms
	 * @Date : 2019. 2. 7.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/create-signup-order")
	public void createSignupOrder(Parameter<?> parameter) throws Exception {
		CmTerms cmTerms = parameter.create(CmTerms.class);

		Map<String, Object> rsMap = cmTermsService.setSignUpOrderTerms(cmTerms, parameter);

		writeJson(parameter, rsMap);
	}

	/**
	 * @Desc : 약관삭제
	 * @Method Name : removeTerms
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/remove-terms")
	public void removeTerms(Parameter<?> parameter) throws Exception {
		CmTerms cmTerms = parameter.create(CmTerms.class);
		CmTermsDetail cmTermsDetail = parameter.create(CmTermsDetail.class);
		Map<String, Object> rsMap = cmTermsService.deleteTerms(cmTerms, cmTermsDetail);

		writeJson(parameter, rsMap);
	}

}
