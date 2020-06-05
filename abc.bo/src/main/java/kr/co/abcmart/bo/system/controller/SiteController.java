package kr.co.abcmart.bo.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.model.master.SySiteDeliveryGuide;
import kr.co.abcmart.bo.system.model.master.SySiteDeliveryType;
import kr.co.abcmart.bo.system.model.master.SySitePaymentMeans;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.CommonCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("system/site")
public class SiteController extends BaseController {

	@Autowired
	SiteService service;

	/**
	 * 사이트 관리 form 조회
	 * 
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ModelAndView read(Parameter<?> parameter) throws Exception {
		Map<String, List<SyCodeDetail>> map = service.getCommonCode();

		// 배송유형코드
		parameter.addAttribute("dlvyTypeCode", map.get(CommonCode.DLVY_TYPE_CODE));
		// 결제수단코드
		parameter.addAttribute("pymntMeansCode", map.get(CommonCode.PYMNT_MEANS_CODE));
		// 배송/클레임정책코드
		parameter.addAttribute("dlvyGuideBgnCode", map.get(CommonCode.DLVY_GUIDE_BGN_CODE));
		// SNS 채널 코드
		parameter.addAttribute("snsChnnlCode", map.get(CommonCode.SNS_CHNNL_CODE));

		return forward("system/site/site-main");
	}

	/**
	 * 사이트 json data
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("list")
	public void list(Parameter<SySite> parameter) throws Exception {
		List<SySite> siteList = service.getAllWithAdmin();

		Map<String, Object> ibsheet = new HashMap<String, Object>();
		ibsheet.put("data", siteList);

		writeJson(parameter, ibsheet);
	}

	/**
	 * 사이트 수정/등록.
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("update")
	public void setSite(Parameter<SySite> parameter) throws Exception {
		SySite sySite = parameter.get();

		sySite.validate();

		service.setSite(sySite);
	}

	/**
	 * 사이트의 배송유형을 조회한다.
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("delivery-type")
	public void deliveryType(Parameter<?> parameter) throws Exception {
		String siteNo = parameter.getString("siteNo");

		List<SySiteDeliveryType> list = service.getDeliveryType(siteNo);

		writeJson(parameter, list);
	}

	/**
	 * 채널 리스트 조회
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("channel/list")
	public void channelList(Parameter<SySiteChnnl> parameter) throws Exception {
		Pageable<SySiteChnnl, SySiteChnnl> pageable = new Pageable<>(parameter);
		Page<SySiteChnnl> page = service.getChannelForPaging(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 채널 상세조회
	 * @Method Name : channelDetail
	 * @Date : 2019. 12. 17.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("channel/detail")
	public void getChnnlDetailInfo(Parameter<SySiteChnnl> parameter) throws Exception {
		SySiteChnnl params = parameter.get();
		params = service.getChnnlDetailInfo(params);

		writeJson(parameter, params);
	}

	/**
	 * 채널 수정/등록.
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("channel/update")
	public void channelUpdate(Parameter<SySiteChnnl> parameter) throws Exception {
		System.out.println();

		SySiteChnnl sySiteChnnl = parameter.get();
		service.setChannel(sySiteChnnl);

		/*
		 * SySiteChnnl[] sySiteChnnl = parameter.createArray(SySiteChnnl.class,
		 * "chnnlNo", getExcludeFields());
		 * 
		 * for (SySiteChnnl siteChnnl : sySiteChnnl) { siteChnnl.validate(); }
		 * 
		 * service.setChannel(sySiteChnnl);
		 */
	}

	/**
	 * 결제수단 조회
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("payment")
	public void paymentRead(Parameter<SySitePaymentMeans> parameter) throws Exception {
		SySitePaymentMeans sySitePaymentMeans = parameter.get();

		List<SySitePaymentMeans> list = service.getPaymentBySiteNo(sySitePaymentMeans);

		writeJson(parameter, list);
	}

	/**
	 * 결제수단 등록/수정
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("payment/update")
	public void paymentUpdate(Parameter<?> parameter) throws Exception {
		SySitePaymentMeans[] sySitePaymentMeans = parameter.createArray(SySitePaymentMeans.class, "siteNo",
				getExcludeFields());

		for (SySitePaymentMeans paymentMeans : sySitePaymentMeans) {
			paymentMeans.validate();
		}

		service.setPayment(sySitePaymentMeans);
	}

	/**
	 * 정책 조회
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("guide")
	public void guideRead(Parameter<SySiteDeliveryGuide> parameter) throws Exception {
		SySiteDeliveryGuide sySiteDeliveryGuide = parameter.get();

		sySiteDeliveryGuide = service.getDeliveryGuide(sySiteDeliveryGuide);

		writeJson(parameter, sySiteDeliveryGuide);
	}

	/**
	 * 정책 등록/수정
	 * 
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("guide/update")
	public void guideUpdate(Parameter<SySiteDeliveryGuide> parameter) throws Exception {
		SySiteDeliveryGuide sySiteDeliveryGuide = parameter.get();
		sySiteDeliveryGuide.setDlvyGuideInfo(parameter.getString("dlvyGuideInfo", false));

		sySiteDeliveryGuide.validate();

		service.setDeliveryGuide(sySiteDeliveryGuide);
	}

	/**
	 * request parameter를 model에 binding할 때 제외할 필드리스트를 리턴한다.
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<String> getExcludeFields() throws Exception {
		List<String> exclude = new ArrayList<String>();
		exclude.add("rgstDtm");
		exclude.add("modDtm");

		return exclude;
	}
}
