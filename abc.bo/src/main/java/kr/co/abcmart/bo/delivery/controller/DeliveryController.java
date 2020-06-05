package kr.co.abcmart.bo.delivery.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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

import kr.co.abcmart.bo.claim.model.master.OcClaim;
import kr.co.abcmart.bo.claim.model.master.OcClaimProduct;
import kr.co.abcmart.bo.cmm.model.master.CmCounselScript;
import kr.co.abcmart.bo.cmm.service.CmCounselScriptService;
import kr.co.abcmart.bo.delivery.model.master.OcDelivery;
import kr.co.abcmart.bo.delivery.service.DeliveryService;
import kr.co.abcmart.bo.delivery.vo.DeliveryOrderNotExcelVO;
import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.service.MemberService;
import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.bo.order.model.master.OcOrderDeliveryHistory;
import kr.co.abcmart.bo.order.model.master.OcOrderMemo;
import kr.co.abcmart.bo.order.model.master.OcOrderProduct;
import kr.co.abcmart.bo.order.service.OrderService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsResponse;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Desc : 배송관리 관련 Controller
 * @FileName : DeliveryController.java
 * @Project : abc.bo
 * @Date : 2019. 2. 8.
 * @Author : NKB
 */
@Slf4j
@Controller
@RequestMapping("delivery")
public class DeliveryController extends BaseController {

	@Autowired
	private DeliveryService deliveryService;

	@Autowired
	private SiteService siteService; // 사이트

	@Autowired
	private CommonCodeService commonCodeService; // 코드

	@Autowired
	private MemberService memberService;

	@Autowired
	CmCounselScriptService conselScriptService;

	@Autowired
	VendorService vendorService;

	@Autowired
	OrderService orderService;

	/**
	 * 
	 * @Desc : 배송관리 목록 (자사) - Main
	 * @Method Name : deliveryOrderMain
	 * @Date : 2019. 2. 8.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delivery-order")
	public ModelAndView deliveryOrderMain(Parameter<?> parameter) throws Exception {

		// Grid 코드정보
		String[] codeFields = { CommonCode.STOCK_GBN_CODE // 발송처
				, CommonCode.DLVY_TYPE_CODE // 주문배송유형
				, CommonCode.DLVY_STAT_CODE // 주문배송상태
				, CommonCode.LOGIS_VNDR_CODE // 택배사코드
				, CommonCode.PYMNT_MEANS_CODE // 결제수단
				, CommonCode.STORE_GBN_CODE // 매장구분코드
		};

		// site Combo,site List
		Pair<JSONObject, List<SySite>> siteCode = siteService.getSiteListByCombo();

		// Code값 get
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		parameter.addAttribute("codeCombo", pair.getFirst()); // code Combo
		parameter.addAttribute("codeList", pair.getSecond()); // code list
		parameter.addAttribute("siteCombo", siteCode.getFirst()); // Site Combo
		parameter.addAttribute("siteList", siteCode.getSecond()); // Site List

		if (UtilsText.isNotBlank(parameter.getString("fromDash"))
				&& UtilsText.equals(Const.BOOLEAN_TRUE, parameter.getString("fromDash"))) {
			parameter.addAttribute("fromDash", parameter.getString("fromDash"));
			parameter.addAttribute("tabIdx", parameter.getString("tabIdx"));
			parameter.addAttribute("fromDashDlvyStatCode", parameter.getString("dlvyStatCode"));
		}

		return forward("/delivery/delivery-order-main");
	}

	/**
	 * 
	 * @Desc : 배송관리 목록 (자사) - 검색
	 * @Method Name : deliveryOrder
	 * @Date : 2019. 2. 8.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delivery-order/read-list")
	@ResponseBody
	public void deliveryOrder(Parameter<OcDelivery> parameter) throws Exception {

		Pageable<OcDelivery, OcDelivery> pageable = new Pageable<>(parameter);

		Page<OcDelivery> page = deliveryService.searchDeliveryOrderList(pageable);

		UtilsResponse.writeJson(parameter.getResponse(), page.getGrid());

	}

	/**
	 * 
	 * @Desc : 배송관리 목록 (자사) - 엑셀 전체 다운로드
	 * @Method Name : deliveryOrderAllExcel
	 * @Date : 2020. 1. 21.
	 * @Author : 이강수
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/delivery-order/download-all-excel")
	public void deliveryOrderAllExcel(Parameter<OcDelivery> parameter) throws Exception {

		Pageable<OcDelivery, DeliveryOrderNotExcelVO> pageable = new Pageable<>(parameter);

		pageable.getBean().setIsExcel(Const.BOOLEAN_TRUE);

		List<DeliveryOrderNotExcelVO> list = deliveryService.searchDeliveryOrderAllExcelList(pageable).getContent();

		ExcelValue excelValue = ExcelValue.builder(1)
				.columnNames(Arrays.asList("siteName", "strOrderDtm", "orderPrdtSeq", "prdtNo", "prdtName", "optnName",
						"strOrderAmt", "dlvyStatCodeName", "dlvyTypeCodeName", "dlvyIdText", "logisVndrCodeName",
						"waybilNoText", "stockGbnCodeName", "buyerName", "rcvrName", "rcvrPostAddrText",
						"rcvrDtlAddrText"))
				.data(list).build();

		parameter.downloadExcelTemplate("delivery/excel/deliveryOrderList", excelValue);
	}

	/**
	 * 미출고 현황
	 * 
	 * @Desc : 미출고 현황
	 * @Method Name : deliveryOrderNotMain
	 * @Date : 2019. 2. 12.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delivery-order-not")
	public ModelAndView deliveryOrderNotMain(Parameter<OcDelivery> parameter) throws Exception {

		/*
		 * 입점 업체 분기 처리 용
		 */
		UserDetails user = LoginManager.getUserDetails();

		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		if (!isAdmin) {
			VdVendor vndr = new VdVendor();
			vndr.setVndrNo(user.getVndrNo());
			vndr = vendorService.getVendorBaseInfo(vndr);

			parameter.addAttribute("vndrNo", vndr.getVndrNo());
			parameter.addAttribute("vndrName", vndr.getVndrName());
		}

		// Grid 코드정보
		String[] codeFields = { CommonCode.STOCK_GBN_CODE // 발송처
				, CommonCode.DLVY_TYPE_CODE // 주문배송유형
				, CommonCode.DLVY_STAT_CODE // 주문배송상태
				, CommonCode.LOGIS_VNDR_CODE // 택배사코드
				, CommonCode.PYMNT_MEANS_CODE // 결제수단
				, CommonCode.STORE_GBN_CODE // 매장구분코드
				, CommonCode.LOGIS_CNVRT_RSN_CODE // 택배전환 사유
		};

		// site Combo,site List
		Pair<JSONObject, List<SySite>> siteCode = siteService.getSiteListByCombo();

		// Code값 get
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		parameter.addAttribute("codeCombo", pair.getFirst()); // code Combo
		parameter.addAttribute("codeList", pair.getSecond()); // code list
		parameter.addAttribute("siteCombo", siteCode.getFirst()); // Site Combo
		parameter.addAttribute("siteList", siteCode.getSecond()); // Site List

		return forward("/delivery/delivery-order-not-main");
	}

	/**
	 * 
	 * @Desc : 미출고 현황 - 검색
	 * @Method Name : deliveryOrderNot
	 * @Date : 2019. 2. 25.
	 * @Author : NKB
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/delivery-order-not/read-list")
	@ResponseBody
	public void deliveryOrderNot(Parameter<OcDelivery> parameter) throws Exception {

		Pageable<OcDelivery, OcDelivery> pageable = new Pageable<>(parameter);

		Page<OcDelivery> page = deliveryService.deliveryOrderNotReadList(pageable);

		UtilsResponse.writeJson(parameter.getResponse(), page.getGrid());

	}

	/**
	 * @Desc : 미출고 엑셀파일 모두 다운로드
	 * @Method Name : downloadDeliveryOrderNotAllExcel
	 * @Date : 2019. 9. 23.
	 * @Author : 이강수
	 * @param Parameter<OcDelivery>
	 * @throws Exception
	 */
	@RequestMapping("/delivery-order-not/download-all-excel")
	public void downloadDeliveryOrderNotAllExcel(Parameter<OcDelivery> parameter) throws Exception {

		Pageable<OcDelivery, DeliveryOrderNotExcelVO> pageable = new Pageable<>(parameter);

		List<DeliveryOrderNotExcelVO> list = deliveryService.getDeliveryOrderNotAllExcel(pageable);

		ExcelValue excelValue = ExcelValue.builder(1)
				.columnNames(Arrays.asList("siteName", "strOrderDtm", "orderPrdtSeq", "prdtNo", "prdtName", "optnName",
						"strOrderAmt", "dlvyStatCodeName", "dlvyTypeCodeName", "dlvyIdText", "logisVndrCodeName",
						"waybilNoText", "stockGbnCodeName"))
				.data(list).build();

		parameter.downloadExcelTemplate("delivery/excel/deliveryOrderNotList", excelValue);
	}

	/**
	 * 매장픽업 택배전환 -main
	 * 
	 * @Desc : 매장픽업 택배전환
	 * @Method Name : storePickDeliveryChangetList
	 * @Date : 2019. 2. 12.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/store-pick-delivery-change")
	public ModelAndView storePickDeliveryChangeMain(Parameter<OcDelivery> parameter) throws Exception {

		// Grid 코드정보
		String[] codeFields = { CommonCode.STOCK_GBN_CODE // 발송처
				, CommonCode.DLVY_STAT_CODE // 주문배송상태
				, CommonCode.LOGIS_VNDR_CODE // 택배사코드
				, CommonCode.STORE_GBN_CODE // 매장구분코드
				, CommonCode.LOGIS_CNVRT_RSN_CODE // 전환사유
		};

		// site Combo,site List
		Pair<JSONObject, List<SySite>> siteCode = siteService.getSiteListByCombo();

		// Code값 get
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		List<SySiteChnnl> chanList = siteService.getUseChannelList(); // 채널 리스트
		log.debug("chanList:::::::::::" + chanList.size());

		parameter.addAttribute("codeCombo", pair.getFirst()); // code Combo
		parameter.addAttribute("codeList", pair.getSecond()); // code list
		parameter.addAttribute("siteCombo", siteCode.getFirst()); // Site Combo
		parameter.addAttribute("siteList", siteCode.getSecond()); // Site List
		parameter.addAttribute("chanList", chanList); // channel list

		return forward("/delivery/store-pick-delivery-change-main");
	}

	/**
	 * 매장픽업 택배전환
	 * 
	 * @Desc : 매장픽업 택배전환
	 * @Method Name : storePickDeliveryChangetList
	 * @Date : 2019. 2. 12.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/store-pick-delivery-change/read-list")
	public void storePickDeliveryChange(Parameter<OcDelivery> parameter) throws Exception {

		Pageable<OcDelivery, OcDelivery> pageable = new Pageable<>(parameter);

		Page<OcDelivery> page = deliveryService.storePickDeliveryChangeReadList(pageable);

		UtilsResponse.writeJson(parameter.getResponse(), page.getGrid());

	}

	/**
	 * 매장픽업 택배전환 -main
	 * 
	 * @Desc : 매장픽업 택배전환
	 * @Method Name : storePickDeliveryChangetList
	 * @Date : 2019. 2. 12.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/store-pick-delivery-change-popup")
	public ModelAndView storePickDeliveryChangePopupMain(Parameter<?> parameter) throws Exception {
		/*
		 * 소스 정리 예정 -
		 */
		// 주문번호
		String orderNo = parameter.getString("orderNo");
		String callType = parameter.getString("callType"); // O:주문상새 , D : 배송관리
		String callBackFunc = parameter.getString("callBackFunc"); // 호출 함수

		log.debug("[callType] ::::::::::::::::::::::::::::::::::::::::" + callType);

		log.debug("[dlvyIdText] ::::::::::::::::::::::::::::::::::::::::" + parameter.getString("dlvyIdText"));
		// popup호출시 넘어오는 주문상품 순번
		// String[] orderPrdSeqArr = parameter.getString("orderPrdtSeq").split(",");
		// popup호출시 넘어오는 배송 ID
		String[] dlvyIdTextarr = null;
		if (UtilsText.equals(callType, "OV")) {
			dlvyIdTextarr = parameter.getString("dlvyIdText").split(",");
		} else {
			dlvyIdTextarr = parameter.getString("dlvyIdText").split(",");
		}

		/*
		 * // 주문상품 순번을 List로 변경 integer List<Integer> orderPrdSeqList = new
		 * ArrayList<>(); for (int i = 0; i < orderPrdSeqArr.length; i++) {
		 * orderPrdSeqList.add(Integer.parseInt(orderPrdSeqArr[i])); }
		 */

		// 고객주문정보
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(orderNo);
		OcOrder ocOrderInfo = deliveryService.getDeliveryOrderInfo(ocOrder);

		parameter.addAttribute("ocOrder", ocOrderInfo);

		// 선택 상품정보
		OcDelivery ocDelivery = new OcDelivery();
		ocDelivery.setOrderNo(orderNo);
		ocDelivery.setDlvyIdTextList(Arrays.asList(dlvyIdTextarr));

		List<OcDelivery> ocOrderProductDeliveryList = deliveryService.getDeliveryOrderProductDelivery(ocDelivery);
		// 주소 등록이 된 상품(주소가 있으면 택배전환을 한 상태)
		int deliveryCount = (int) ocOrderProductDeliveryList.stream()
				.filter(o -> o.getDeliveryAddressEmptyYn().equals("Y")).count();

		// 주소 등록이 된 상품(주소가 있으면 택배전환을 한 상태)
		int deliverySendCount = (int) ocOrderProductDeliveryList.stream()
				.filter(o -> o.getDeliveryLogisSendYn().equals("Y")).count();

		parameter.addAttribute("deliveryAddressEmptyCnt", deliveryCount);
		parameter.addAttribute("deliveryLogisCnt", deliverySendCount);
		parameter.addAttribute("callBackFunc", callBackFunc); // 호출 함수
		parameter.addAttribute("callType", callType); // OV:주문상새 , DV : 배송관리
		parameter.addAttribute("ocOrderProductDeliveryList", ocOrderProductDeliveryList);

		return forward("/delivery/store-pick-delivery-change-popup");
	}

	/**
	 * 매장픽업 택배전환 -main
	 * 
	 * @Desc : 매장픽업 택배전환
	 * @Method Name : storePickDeliveryChangetList
	 * @Date : 2019. 2. 12.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/store-pick-delivery-change-popup/update")
	public void storePickDeliveryChangePopupSave(Parameter<?> parameter) throws Exception {

		// 주문정보 Mapping
		OcOrder ocOrder = parameter.create(OcOrder.class);

		// 배송정보 Mappingparameter.createArray(OcClaimProduct.class, "clmNo");
		OcOrderDeliveryHistory[] ocOrderDeliveryHistory = parameter.createArray(OcOrderDeliveryHistory.class,
				"dlvyIdText");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (UtilsText.equals(parameter.getString("rcvrName"), parameter.getString("rcvrName1"))) {
			ocOrder.setRcvrName(parameter.getString("orgRcvrName"));
		}
		if (UtilsText.equals(parameter.getString("rcvrHdphnNoText"), parameter.getString("rcvrHdphnNoText1"))) {
			ocOrder.setRcvrHdphnNoText(parameter.getString("orgRcvrHdphnNoText"));
		}
		if (UtilsText.equals(parameter.getString("rcvrTelNoText"), parameter.getString("rcvrTelNoText1"))) {
			ocOrder.setRcvrTelNoText(parameter.getString("orgRcvrTelNoText"));
		}
		if (UtilsText.equals(parameter.getString("rcvrPostAddrText"), parameter.getString("rcvrPostAddrText1"))) {
			ocOrder.setRcvrPostAddrText(parameter.getString("orgRcvrPostAddrText"));
		}
		if (UtilsText.equals(parameter.getString("rcvrDtlAddrText"), parameter.getString("rcvrDtlAddrTextv1"))) {
			ocOrder.setRcvrDtlAddrText(parameter.getString("orgRcvrDtlAddrText"));
		}

		log.debug("================================================= /storePickDeliveryChangePopupSave");

		resultMap = deliveryService.updateStorePickDeliveryPopup(ocOrderDeliveryHistory, ocOrder);

		log.debug("================================================= /storePickDeliveryChangePopupSave " + resultMap);
		writeJson(parameter, resultMap);

	}

	/**
	 * 분실처리 -main Popup
	 * 
	 * @Desc : 분실처리
	 * @Method Name : storePickDeliveryChangetList
	 * @Date : 2019. 2. 12.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delivery-miss-proc-popup")
	public ModelAndView deliveryMissProcPopup(Parameter<?> parameter) throws Exception {
		/*
		 * 소스 정리 예정 -
		 */
		// 주문번호
		String orderNo = parameter.getString("orderNo");
		String callType = parameter.getString("callType"); // O:주문상새 , D : 배송관리
		String callBackFunc = parameter.getString("callBackFunc"); // 호출 함수

		log.debug("[callType] ::::::::::::::::::::::::::::::::::::::::" + callType);
		// popup호출시 넘어오는 주문상품 순번
		// String[] orderPrdSeqArr = parameter.getString("orderPrdtSeq").split(",");
		// popup호출시 넘어오는 배송 ID
		String[] dlvyIdTextarr = parameter.getString("dlvyIdText").split(",");

		/*
		 * // 주문상품 순번을 List로 변경 integer List<Integer> orderPrdSeqList = new
		 * ArrayList<>(); for (int i = 0; i < orderPrdSeqArr.length; i++) {
		 * orderPrdSeqList.add(Integer.parseInt(orderPrdSeqArr[i])); }
		 */

		// addInfo4 있는 은행만 뽑기
		List<SyCodeDetail> bankCodeList = commonCodeService.getCodeNoName(CommonCode.BANK_CODE);
		bankCodeList = bankCodeList.stream().filter(x -> UtilsObject.isNotEmpty(x.getAddInfo4()))
				.collect(Collectors.toList());

		parameter.addAttribute(CommonCode.BANK_CODE, bankCodeList); // 은행코드

		// 고객 주문정보
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(orderNo);
		OcOrder ocOrderInfo = deliveryService.getDeliveryOrderInfo(ocOrder);
		parameter.addAttribute("ocOrder", ocOrderInfo);

		// 고객 정보
		MbMember memberInfo = memberService.getMember(ocOrderInfo.getMemberNo());
		if (UtilsText.isNotBlank(memberInfo.getBankName()) && UtilsText.isNotBlank(memberInfo.getAcntHldrName())
				&& UtilsText.isNotBlank(memberInfo.getAcntNoText())) {
			parameter.addAttribute("isAcntExist", Const.BOOLEAN_TRUE);

			// 환불계좌 인증을 위해 kcp은행코드로 set
			bankCodeList = bankCodeList.stream()
					.filter(x -> UtilsText.equals(memberInfo.getBankCode(), x.getCodeDtlNo()))
					.collect(Collectors.toList());
			String kcpBankCode = bankCodeList.get(0).getAddInfo4();
			memberInfo.setBankCode(kcpBankCode);

			parameter.addAttribute("memberInfo", memberInfo);

		} else {
			parameter.addAttribute("isAcntExist", Const.BOOLEAN_FALSE);
		}

		// 선택 상품정보
		OcDelivery ocDelivery = new OcDelivery();
		ocDelivery.setOrderNo(orderNo);
		// ocDelivery.setOrderPrdtSeqList(orderPrdSeqList);
		ocDelivery.setDlvyIdTextList(Arrays.asList(dlvyIdTextarr));
		List<OcDelivery> ocOrderProductDeliveryList = deliveryService.getDeliveryOrderProductDelivery(ocDelivery);

		parameter.addAttribute("callBackFunc", callBackFunc); // 호출 함수
		parameter.addAttribute("callType", callType); // O:주문상새 , D : 배송관리
		parameter.addAttribute("ocOrderProductDeliveryList", ocOrderProductDeliveryList);

		return forward("/delivery/delivery-miss-proc-popup");
	}

	/**
	 * 매장픽업 택배전환 -main
	 * 
	 * @Desc : 매장픽업 택배전환
	 * @Method Name : storePickDeliveryChangetList
	 * @Date : 2019. 2. 12.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delivery-miss-proc-popup/update")
	public void deliveryMissProcPopupSave(Parameter<?> parameter) throws Exception {

		// 주문정보 Mapping
		OcOrder ocOrder = parameter.create(OcOrder.class);

		// 배송정보 Mappingparameter.createArray(OcClaimProduct.class, "clmNo");
		OcOrderDeliveryHistory[] ocOrderDeliveryHistory = parameter.createArray(OcOrderDeliveryHistory.class,
				"dlvyIdText");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		log.debug("================================================= /deliveryMissProcPopupSave");

		resultMap = deliveryService.updateMissDeliveryPopup(ocOrderDeliveryHistory, ocOrder);

		log.debug("================================================= /deliveryMissProcPopupSave " + resultMap);
		writeJson(parameter, resultMap);

	}

	/**
	 * 상품대기 조회
	 * 
	 * @Desc :
	 * @Method Name : storePickDeliveryChangetList
	 * @Date : 2019. 2. 12.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delivery-order-standby")
	public ModelAndView deliveryOrderStandbyMain(Parameter<OcDelivery> parameter) throws Exception {

		// Grid 코드정보
		String[] codeFields = { CommonCode.STOCK_GBN_CODE // 발송처
				, CommonCode.DLVY_STAT_CODE // 주문배송상태
				, CommonCode.LOGIS_VNDR_CODE // 택배사코드
				, CommonCode.STORE_GBN_CODE // 매장구분코드
				, CommonCode.DLVY_DSCNTC_RSN_CODE // 배송준단사유
				, CommonCode.DLVY_TYPE_CODE // 배송유형
				, CommonCode.PROC_GUBN_CODE // 처리구분 코드
		};

		// site Combo,site List
		Pair<JSONObject, List<SySite>> siteCode = siteService.getSiteListByCombo();

		// Code값 get
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		List<SySiteChnnl> chanList = siteService.getUseChannelList(); // 채널 리스트

		parameter.addAttribute("codeCombo", pair.getFirst()); // code Combo
		parameter.addAttribute("codeList", pair.getSecond()); // code list
		parameter.addAttribute("siteCombo", siteCode.getFirst()); // Site Combo
		parameter.addAttribute("siteList", siteCode.getSecond()); // Site List
		parameter.addAttribute("chanList", chanList); // channel list

		return forward("/delivery/delivery-order-standby-main");
	}

	/**
	 * 상품대기 조회
	 * 
	 * @Desc :
	 * @Method Name : deliveryProdStandby
	 * @Date : 2019. 2. 12.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delivery-order-standby/read-list")
	public void deliveryOrderStandby(Parameter<OcDelivery> parameter) throws Exception {

		Pageable<OcDelivery, OcDelivery> pageable = new Pageable<>(parameter);

		Page<OcDelivery> page = deliveryService.deliveryOrderStandbyList(pageable);

		UtilsResponse.writeJson(parameter.getResponse(), page.getGrid());
	}

	/**
	 * @Desc : 상품대기조회 엑셀전체다운
	 * @Method Name : delveryOrderStandbyAllExcel
	 * @Date : 2019. 10. 14.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/delivery-order-standby/excel-all-down")
	public void delveryOrderStandbyAllExcel(Parameter<OcDelivery> parameter) throws Exception {
		Pageable<OcDelivery, OcDelivery> pageable = new Pageable<>(parameter);
		pageable.setRowsPerPage(600000);

		Page<OcDelivery> page = deliveryService.deliveryOrderStandbyList(pageable);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateParam = new Date();
		for (int i = 0; i < page.getTotalCount(); i++) {
			if (UtilsText.equals(page.getContent().get(i).getSiteNo(), Const.SITE_NO_ART)) {
				page.getContent().get(i).setSiteNo("통합몰");
			} else {
				page.getContent().get(i).setSiteNo("OTS");
			}
			if (UtilsText.equals(page.getContent().get(i).getDlvyDscntcProc(), "01")) {
				page.getContent().get(i).setDlvyDscntcProc("대기");
			} else {
				page.getContent().get(i).setDlvyDscntcProc("완료");
			}
			if (page.getContent().get(i).getDlvyDscntcAcceptDtm() != null) {
				dateParam.setTime(page.getContent().get(i).getDlvyDscntcAcceptDtm().getTime());
				page.getContent().get(i).setOccurrenceDate(dateFormat.format(dateParam));
			}
			if (page.getContent().get(i).getDlvyDscntcProcDtm() != null) {
				dateParam.setTime(page.getContent().get(i).getDlvyDscntcProcDtm().getTime());
				page.getContent().get(i).setProcDate(dateFormat.format(dateParam));
			}
		}

		ExcelValue excelValue = ExcelValue.builder(1, 0).columnNames(Arrays.asList("siteNo" // 사이트
				, "orderNo" // 주문번호
				, "dlvyIdText" // 배송ID
				, "procGubnCodeName" // 처리구분 코드
				, "prdtNo" // 상품코드
				, "optionUseYn" // 전시여부
				, "prdtName" // 상품명
				, "optnName" // 옵션
				, "availableQtyAi" // 온라인주문가능수량
				, "availableQtyAw" // 스마트주문가능수량
				, "availableQtyAs" // 매장주문가능수량
				, "buyerName" // 주문자
				, "stockGbnCodeName" // 미출처
				, "dlvyTypeName" // 배송유형
				, "occurrenceDate" // 발생일
				, "procDate" // 처리일시
				, "dlvyDscntcOpetrName" // 처리자
				, "dlvyDscntcProc" // 처리상태
				, "dlvyDscntcRsnReason" // 배송중단사유
				, "orderPrdtSeq" // 주문상품순번
				, "orderDlvyHistSeq" // 주문배송이력순번
				, "dlvyStatCodeName" // 배송상태
				, "rcvrPostAddrText" // 배송주소
		)).data(page.getContent()).build();

		parameter.downloadExcelTemplate("delivery/excel/deliveryOrderStandByAll", excelValue);
	}

	/**
	 * 
	 * @Desc : 상품대기조회 일괄 재배송 처리
	 * @Method Name : deliveryOrderStandbySave
	 * @Date : 2019. 3. 28.
	 * @Author : NKB
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/delivery-order-standby/save")
	public void deliveryOrderStandbySave(Parameter<?> parameter) throws Exception {
		// Parameter 클래스의 createArray 메소드를 이용하여 그리드에서 배열로 넘어온 파라메터 값을 VO에 맵핑

		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 대상 파라미터 맵핑
		OcDelivery ocDelivery = parameter.create(OcDelivery.class,
				Arrays.asList("modDtm", "dlvyDscntcProcDtm", "fromDate", "toDate", "dlvyDscntcAcceptDtm"));

		// Parameter 클래스의 createArray 메소드를 이용하여 그리드에서 배열로 넘어온 파라메터 값을 VO에 맵핑
		OcOrderDeliveryHistory[] entityGrid = parameter.createArray(OcOrderDeliveryHistory.class, "dlvyIdText",
				Arrays.asList("modDtm", "dlvyDscntcProcDtm", "fromDate", "toDate", "dlvyDscntcAcceptDtm"));

		log.debug("entityGrid.length ::::" + entityGrid.length);

		// 배송상태 변경 처리
		for (int i = 0; i < entityGrid.length; i++) {
			OcOrderDeliveryHistory gridOcOrderDeliveryHistory = entityGrid[i];
			if (gridOcOrderDeliveryHistory.getCheckedRows() > 0) {
				log.debug("entityGrid[i] ::::" + entityGrid[i]);
				resultMap = deliveryService.insertDeliveryOrderStandby(ocDelivery, gridOcOrderDeliveryHistory);
			}
		}

		writeJson(parameter, resultMap);
	}
	
	/**
	 * @Desc      	: 상품 대기조회 처리구분 코드 수정
	 * @Method Name : deliveryOrderStandbyConfirmSave
	 * @Date  	  	: 2020. 5. 28.
	 * @Author    	: sic
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/delivery-order-standby/confirm-save")
	public void deliveryOrderStandbyConfirmSave(Parameter<?> parameter) throws Exception {
		// Parameter 클래스의 createArray 메소드를 이용하여 그리드에서 배열로 넘어온 파라메터 값을 VO에 맵핑
		OcDelivery[] entityGrid = parameter.createArray(OcDelivery.class, "dlvyIdText",
				Arrays.asList("modDtm", "dlvyDscntcProcDtm", "fromDate", "toDate", "dlvyDscntcAcceptDtm"));;
		
		Map<String, Object> rsMap = deliveryService.updateConfirmStandby(entityGrid);
		
		writeJson(parameter, rsMap);
	}

	/**
	 * 
	 * @Desc : 분실배송조회 Main
	 * @Method Name : deliveryOrderLossMain
	 * @Date : 2019. 4. 5.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delivery-order-loss-main")
	public ModelAndView deliveryOrderLossMain(Parameter<OcDelivery> parameter) throws Exception {

		// Grid 코드정보
		String[] codeFields = { CommonCode.STOCK_GBN_CODE // 발송처
				, CommonCode.LOGIS_VNDR_CODE // 택배사코드
		};

		// site Combo,site List
		Pair<JSONObject, List<SySite>> siteCode = siteService.getSiteListByCombo();

		// Code값 get
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		// List<SySiteChnnl> chanList = siteService.getUseChannelList(); // 채널 리스트

		parameter.addAttribute("codeCombo", pair.getFirst()); // code Combo
		parameter.addAttribute("codeList", pair.getSecond()); // code list
		parameter.addAttribute("siteCombo", siteCode.getFirst()); // Site Combo
		parameter.addAttribute("siteList", siteCode.getSecond()); // Site List
		// parameter.addAttribute("chanList", chanList); // channel list

		return forward("/delivery/delivery-order-loss-main");
	}

	/**
	 * 
	 * @Desc : 분실배송조회
	 * @Method Name : deliveryOrderLoss
	 * @Date : 2019. 11. 26.
	 * @Author : NKB, 이강수
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/delivery-order-loss/read-list")
	public void deliveryOrderLoss(Parameter<OcDelivery> parameter) throws Exception {

		Pageable<OcDelivery, OcDelivery> pageable = new Pageable<>(parameter);

		Page<OcDelivery> page = deliveryService.deliveryOrderLossList(pageable);

		UtilsResponse.writeJson(parameter.getResponse(), page.getGrid());
	}

	/**
	 * 
	 * @Desc : 분실배송조회 금액 관련 Update
	 * @Method Name : deliveryOrderLossSave
	 * @Date : 2019. 4. 8.
	 * @Author : NKB
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/delivery-order-loss/save")
	public void deliveryOrderLossSave(Parameter<?> parameter) throws Exception {
		// Parameter 클래스의 createArray 메소드를 이용하여 그리드에서 배열로 넘어온 파라메터 값을 VO에 맵핑

		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 대상 파라미터 맵핑
		OcDelivery ocDelivery = parameter.create(OcDelivery.class,
				Arrays.asList("orderDtm", "dlvyDscntcProcDtm", "fromDate", "toDate"));

		// Parameter 클래스의 createArray 메소드를 이용하여 그리드에서 배열로 넘어온 파라메터 값을 VO에 맵핑
		OcOrderDeliveryHistory[] entityGrid = parameter.createArray(OcOrderDeliveryHistory.class, "dlvyIdText",
				Arrays.asList("orderDtm", "dlvyDscntcProcDtm", "fromDate", "toDate"));

		resultMap = deliveryService.UpdateDeliveryOrderLoss(ocDelivery, entityGrid);

		writeJson(parameter, resultMap);
	}

	/**
	 * 
	 * @Desc : 분실배송조회 엑셀 다운로드
	 * @Method Name : deliveryOrderLossSelectExcel
	 * @Date : 2019. 11. 26.
	 * @Author : NKB, 이강수
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/delivery-order-loss/excel")
	public void deliveryOrderLossSelectExcel(Parameter<OcDelivery> parameter) throws Exception {

		Pageable<OcDelivery, OcDelivery> pageable = new Pageable<>(parameter);
		pageable.getBean().setIsExcel(Const.BOOLEAN_TRUE);
		log.error(":::ocDelivery.excelType ALL전체 SELECT 선택 :::" + parameter.getString("excelType"));
		// 선택 엑셀 다운로드
		if (UtilsText.equals(parameter.get().getExcelType(), "SELECT")) {
			OcDelivery[] ocDeliveryArray = parameter.createArray(OcDelivery.class, "dlvyIdText");
			List<String> dlvyIdTextList = new ArrayList<>();
			for (OcDelivery od : ocDeliveryArray) {
				dlvyIdTextList.add(od.getDlvyIdText());
			}
			pageable.getBean().setDlvyIdTextList(dlvyIdTextList);
		}

		Page<OcDelivery> page = deliveryService.deliveryOrderLossList(pageable);

		ExcelValue excelValue = ExcelValue.builder(1)
				.columnNames(Arrays.asList("siteName", "orderNo", "dlvyIdText", "stockGbnCodeName", "prdtNo",
						"prdtName", "buyerInfo", "missProcTypeCodeName", "logisVndrCodeName", "waybilNoText",
						"dlvyProcDtm", "logisPymntPrdtAmt", "logisPymntDlvyAmt", "moderInfo", "modDtm"))
				.data(page.getContent()).build();

		parameter.downloadExcelTemplate("delivery/excel/deliveryLoss", excelValue);
	}

	/**
	 * 
	 * @Desc : 업체 주문 배송관리
	 * @Method Name : deliveryOrderVendorMain
	 * @Date : 2019. 4. 13.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor")
	public ModelAndView deliveryOrderVendorMain(Parameter<?> parameter) throws Exception {

		/*
		 * 입점 업체 분기 처리 용
		 */
		UserDetails user = LoginManager.getUserDetails();

		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		if (!isAdmin) {
			VdVendor vndr = new VdVendor();
			vndr.setVndrNo(user.getVndrNo());
			vndr = vendorService.getVendorBaseInfo(vndr);

			parameter.addAttribute("vndrNo", vndr.getVndrNo());
			parameter.addAttribute("vndrName", vndr.getVndrName());
		}

		// Grid 코드정보
		String[] codeFields = { CommonCode.STOCK_GBN_CODE // 발송처
				, CommonCode.DLVY_TYPE_CODE // 주문배송유형
				, CommonCode.DLVY_STAT_CODE // 주문배송상태
				, CommonCode.LOGIS_VNDR_CODE // 택배사코드
				, CommonCode.DEVICE_CODE // 디바이스 구분
				, CommonCode.PRDT_TYPE_CODE // 주문상품 구분
				, CommonCode.ORDER_PRDT_STAT_CODE // 상품 배송 상태
		};

		// site Combo,site List
		Pair<JSONObject, List<SySite>> siteCode = siteService.getSiteListByCombo();

		// Code값 get
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		parameter.addAttribute("codeCombo", pair.getFirst()); // code Combo
		parameter.addAttribute("codeList", pair.getSecond()); // code list
		parameter.addAttribute("siteCombo", siteCode.getFirst()); // Site Combo
		parameter.addAttribute("siteList", siteCode.getSecond()); // Site List

		return forward("/delivery/delivery-order-vendor-main");
	}

	/**
	 * 
	 * @Desc : 최근 3개월 이내 주문 기준 새로고침 - 상태별 건수
	 * @Method Name : deliveryOrderVendorMainPrdtStat
	 * @Date : 2019. 4. 16.
	 * @Author : NKB
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor-main/stat/read-list")
	public void deliveryOrderVendorMainPrdtStat(Parameter<OcDelivery> parameter) throws Exception {

		OcDelivery ocDelivery = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		/*
		 * 입점 업체 분기 처리 용
		 */
		UserDetails user = LoginManager.getUserDetails();

		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		if (!isAdmin) {
			ocDelivery.setVndrNo(user.getVndrNo());
		}

		resultMap = deliveryService.getDeliveryOrderVendorPrdtStat(ocDelivery);

		log.debug("================================================= /getDeliveryOrderVendorPrdtStat " + resultMap);
		writeJson(parameter, resultMap);

	}

	/**
	 * 
	 * @Desc : 업체 주문 배송관리 - List
	 * @Method Name : deliveryOrderVendor
	 * @Date : 2019. 4. 21.
	 * @Author : NKB
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor/read-list")
	public void deliveryOrderVendor(Parameter<OcDelivery> parameter) throws Exception {

		Pageable<OcDelivery, OcDelivery> pageable = new Pageable<>(parameter);

		/*
		 * 입점 업체 분기 처리 용
		 */
		UserDetails user = LoginManager.getUserDetails();

		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		if (!isAdmin) {
			pageable.getBean().setVndrNo(user.getVndrNo());
		}

		Page<OcDelivery> page = deliveryService.deliveryOrderVendorList(pageable);

		log.debug("page.getGrid() :::" + page.getGrid());
		UtilsResponse.writeJson(parameter.getResponse(), page.getGrid());
	}

	/**
	 * 
	 * @Desc : 택배사 코드 엑셀다운
	 * @Method Name : deliveryOrderVendorLogisExcelSelect
	 * @Date : 2019. 4. 22.
	 * @Author : NKB
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor/logisExcel")
	public void deliveryOrderVendorLogisExcelSelect(Parameter<OcDelivery> parameter) throws Exception {

		// Grid 코드정보
		String[] codeFields = { CommonCode.LOGIS_VNDR_CODE // 택배사코드
		};

		// Code값 get
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();
		List<SyCodeDetail> excelList = codeList.get(CommonCode.LOGIS_VNDR_CODE);

		ExcelValue excelValue = ExcelValue.builder(1).columnNames(Arrays.asList("codeDtlNo", "codeDtlName"))
				.data(excelList).build();

		parameter.downloadExcelTemplate("delivery/excel/Logis_Excel", excelValue);
	}

	/**
	 * 
	 * @Desc : 업체 배송관리 배송상태변경
	 * @Method Name : deliveryOrderStandbySave
	 * @Date : 2019. 4. 23.
	 * @Author : NKB
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor/save")
	public void deliveryOrderVendorSave(Parameter<?> parameter) throws Exception {
		// Parameter 클래스의 createArray 메소드를 이용하여 그리드에서 배열로 넘어온 파라메터 값을 VO에 맵핑

		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 대상 파라미터 맵핑
		OcDelivery ocDelivery = parameter.create(OcDelivery.class);

		// Parameter 클래스의 createArray 메소드를 이용하여 그리드에서 배열로 넘어온 파라메터 값을 VO에 맵핑
		OcOrderDeliveryHistory[] entityGrid = parameter.createArray(OcOrderDeliveryHistory.class, "dlvyIdText");

		// 배송지 변경 시작
		for (int i = 0; i < entityGrid.length; i++) {
			OcOrderDeliveryHistory gridOcOrderDeliveryHistory = entityGrid[i];
			// log.debug("::::::::::::::::::::::::::::::gridOcOrderDeliveryHistory]]]" +
			// gridOcOrderDeliveryHistory);
			resultMap = deliveryService.updateDeliveryOrderVendorStatCode(ocDelivery, gridOcOrderDeliveryHistory);
			log.debug("::::::::::::::::::::::::::::::resultMap]]]" + resultMap);
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * 
	 * @Desc : 판매취소 요청
	 * @Method Name : deliveryOrderVendorCancelPopup
	 * @Date : 2019. 4. 23.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor/cancel-popup")
	public ModelAndView deliveryOrderVendorCancelPopup(Parameter<?> parameter) throws Exception {

		/*
		 * 입점 업체 분기 처리 용
		 */
		UserDetails user = LoginManager.getUserDetails();

		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		if (!isAdmin) {
			VdVendor vndr = new VdVendor();
			vndr.setVndrNo(user.getVndrNo());
			vndr = vendorService.getVendorBaseInfo(vndr);

			parameter.addAttribute("vndrNo", vndr.getVndrNo());
			parameter.addAttribute("vndrName", vndr.getVndrName());
		}

		// Grid 코드정보
		String[] codeFields = { CommonCode.SELL_CNCL_RSN_CODE // 취소 요청 사유코드
		};
		// Code값 get
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);
		parameter.addAttribute("codeList", pair.getSecond()); // code list

		// popup호출시 넘어오는 배송 ID
		String[] dlvyIdTextarr = parameter.getString("dlvyIdText").split(",");

		// 선택 상품정보
		OcDelivery ocDelivery = new OcDelivery();
		ocDelivery.setDlvyIdTextList(Arrays.asList(dlvyIdTextarr));
		List<OcDelivery> ocOrderProductDeliveryList = deliveryService.getDeliveryOrderProductDelivery(ocDelivery);

		parameter.addAttribute("ocOrderProductDeliveryList", ocOrderProductDeliveryList);
		parameter.addAttribute("cancelPopupType", "C"); // 팝업 호출 구분 C:등록 R:읽기

		return forward("/delivery/delivery-order-vendor-cancel-popup");
	}

	/**
	 * 
	 * @Desc : 판매취소 요청 팝업 등록
	 * @Method Name : deliveryOrderVendorCancelPopupSave
	 * @Date : 2019. 4. 23.
	 * @Author : NKB
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor/cancel-popup/update")
	public void deliveryOrderVendorCancelPopupSave(Parameter<OcOrderProduct[]> parameter) throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		OcOrderProduct ocOrderProduct = parameter.create(OcOrderProduct.class);

		/*
		 * 입점 업체 분기 처리 용
		 */
		UserDetails user = LoginManager.getUserDetails();

		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		if (!isAdmin) {
			ocOrderProduct.setVndrNo(user.getVndrNo());
		}

		log.debug("================================================= /deliveryOrderVendorCancelPopupUpdate");

		List<OcOrderProduct> deliveryCancelList = Arrays
				.asList(parameter.createArray(OcOrderProduct.class, "dlvyIdText"));

		resultMap = deliveryService.updateDeliveryPrdtCancelPopup(deliveryCancelList, ocOrderProduct);

		log.debug(
				"================================================= /deliveryOrderVendorCancelPopupUpdate " + resultMap);
		writeJson(parameter, resultMap);

	}

	/**
	 * 
	 * @Desc : 판매취소 요청 팝업 상세보기
	 * @Method Name : deliveryOrderVendorCancelPopupRead
	 * @Date : 2019. 4. 24.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor/cancel-popup/read")
	public ModelAndView deliveryOrderVendorCancelPopupRead(Parameter<?> parameter) throws Exception {

		// Grid 코드정보
		String[] codeFields = { CommonCode.SELL_CNCL_RSN_CODE // 취소 요청 사유코드
		};

		// Code값 get
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		// 선택 상품정보
		OcOrderProduct ocOrderProduct = parameter.create(OcOrderProduct.class);

		log.debug("::::::::" + ocOrderProduct);
		OcOrderProduct prdtInfo = deliveryService.selectOrderProductDeatail(ocOrderProduct);

		parameter.addAttribute("ocOrderProduct", prdtInfo);
		parameter.addAttribute("codeList", pair.getSecond()); // code list
		parameter.addAttribute("cancelPopupType", "C"); // 팝업 호출 구분 C:등록 R:읽기

		return forward("/delivery/delivery-order-vendor-cancel-popup-read");
	}

	/**
	 * 
	 * @Desc : 발송지연 안내 등록 팝업
	 * @Method Name : deliveryOrderVendorReservationPopup
	 * @Date : 2019. 4. 25.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor/reservation-popup")
	public ModelAndView deliveryOrderVendorReservationPopup(Parameter<?> parameter) throws Exception {

		// popup호출시 넘어오는 배송 ID
		String[] dlvyIdTextarr = parameter.getString("dlvyIdText").split(",");

		// 선택 상품정보
		OcDelivery ocDelivery = new OcDelivery();
		ocDelivery.setDlvyIdTextList(Arrays.asList(dlvyIdTextarr));
		List<OcDelivery> ocOrderProductDeliveryList = deliveryService.getDeliveryOrderProductDelivery(ocDelivery);

		// 상담유형 가져오기
		CmCounselScript cmCounselScript = new CmCounselScript();
		cmCounselScript.setCnslTypeCode("10003");
		cmCounselScript.setCnslTypeDtlCode("10013");
		List<CmCounselScript> cmCounselScriptList = conselScriptService.selectCounselScriptTitle(cmCounselScript);

		log.debug("cmCounselScriptList:::" + cmCounselScriptList.size());
		parameter.addAttribute("cmCounselScriptList", cmCounselScriptList);
		parameter.addAttribute("ocOrderProductDeliveryList", ocOrderProductDeliveryList);

		// 실 결제일 내리기 위한 주결제수단 조회
		parameter.addAttribute("mainPayment",
				orderService.getMainPayment(ocOrderProductDeliveryList.get(0).getOrderNo()));

		return forward("/delivery/delivery-order-vendor-reservation-popup");
	}

	/**
	 * 
	 * @Desc : 발송지연 안내 메세지 등록
	 * @Method Name : deliveryOrderVendorReservationPopupSave
	 * @Date : 2019. 4. 25.
	 * @Author : NKB
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor/reservation-popup/update")
	public void deliveryOrderVendorReservationPopupSave(Parameter<?> parameter) throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		// log.debug("parameter ---> :" + parameter.get());

		log.debug("================================================= /deliveryOrderVendorCancelPopupUpdate");

		List<OcOrderProduct> deliveryReservatioList = Arrays
				.asList(parameter.createArray(OcOrderProduct.class, "dlvyIdText")); // 선택대상 리스트

		log.debug("deliveryReservatioList.size() :::::::::::::" + deliveryReservatioList.size());
		resultMap = deliveryService.updateDeliveryPrdtReservationPopup(deliveryReservatioList, parameter);

		log.debug(
				"================================================= /deliveryOrderVendorCancelPopupUpdate " + resultMap);
		writeJson(parameter, resultMap);

	}

	/**
	 * 
	 * @Desc : 엑셀 샘플 자료 down
	 * @Method Name : deliveryExcelSampleDown
	 * @Date : 2019. 8. 16.
	 * @Author : NKB
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor/delivery-excel-sample-down")
	public void deliveryExcelSampleDown(Parameter<?> parameter) throws Exception {
		log.debug("::::::::::::::::::::::::::::::: deliveryExcelSampleDown ::::::::::::::::::");
		parameter.downloadExcelTemplate("delivery/excel/excelUploadSample");
	}

	/**
	 * 
	 * @Desc : 배송관리 엑셀 업로드
	 * @Method Name : deliveryOrderVendorExcelUploadPopup
	 * @Date : 2019. 5. 16.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor/delivery-excel-upload-popup")
	public ModelAndView deliveryOrderVendorExcelUploadPopup(Parameter<?> parameter) throws Exception {

		String callPopupType = parameter.getString("callPopupType");

		String[] codeFields = { CommonCode.LOGIS_VNDR_CODE // 택배사코드
		};

		// Code값 get
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		parameter.addAttribute("codeCombo", pair.getFirst()); // code Combo
		parameter.addAttribute("codeList", pair.getSecond()); // code list
		parameter.addAttribute("callPopupType", callPopupType);

		return forward("/delivery/delivery-order-vendor-excel-popup");
	}

	/**
	 * 
	 * @Desc : 엑셀 업로드 진행
	 * @Method Name : deliveryOrderVendorExcelUploadSave
	 * @Date : 2019. 5. 8.
	 * @Author : NKB
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor/excel-upload-save")
	public void deliveryOrderVendorExcelUploadSave(Parameter<?> parameter) throws Exception {

		String uploadResultMsg = "성공";
		int failCnt = 0;
		// popup 호출 유형
		String callPopupType = parameter.getString("callPopupType");

		// 전체 return 객체
		Map<String, Object> resultMap = new HashMap<>();
		// Grid Return Value 셋팅값
		List<Map<String, Object>> dataGrid = new ArrayList<>();

		// 대상 파라미터 맵핑
		OcDelivery ocDelivery = parameter.create(OcDelivery.class);

		// Parameter 클래스의 createArray 메소드를 이용하여 그리드에서 배열로 넘어온 파라메터 값을 VO에 맵핑
		OcOrderDeliveryHistory[] entityGrid = parameter.createArray(OcOrderDeliveryHistory.class, "dlvyIdText");
		log.debug(" entityGrid.length():::::::::::" + entityGrid.length);
		// 배송지 변경 시작
		for (int i = 0; i < entityGrid.length; i++) {

			OcOrderDeliveryHistory gridOcOrderDeliveryHistory = entityGrid[i];

			gridOcOrderDeliveryHistory.setDlvyStatCode("10003"); // 변경될 배송상태값 - 배송중 
			gridOcOrderDeliveryHistory.setDlvyStatCodeBefore("10001"); // 이전배송상태값 - 상품준비중 조건처리

			try {
				log.debug(":::::::::::::::::::::::::::::: getOrderNo ]]]" + gridOcOrderDeliveryHistory.getOrderNo());
				resultMap = deliveryService.updateDeliveryOrderVendorStatCode(ocDelivery, gridOcOrderDeliveryHistory);

				if (Const.BOOLEAN_FALSE.equals(resultMap.get("result"))) {
					uploadResultMsg  = String.valueOf(resultMap.get("resultMsg"));
					failCnt++;
				}

			} catch (Exception e) {
				uploadResultMsg = "실패(ETC)";
				failCnt++;
			}

			// Grid Data Set
			Map jsonData = new HashMap();
			jsonData.put("orderNo", gridOcOrderDeliveryHistory.getOrderNo());
			jsonData.put("orderPrdtSeq", gridOcOrderDeliveryHistory.getOrderPrdtSeq());
			jsonData.put("vndrPrdtNoText", gridOcOrderDeliveryHistory.getVndrPrdtNoText());
			jsonData.put("prdtNo", gridOcOrderDeliveryHistory.getPrdtNo());
			jsonData.put("dlvyIdText", gridOcOrderDeliveryHistory.getDlvyIdText());
			jsonData.put("logisVndrCode", gridOcOrderDeliveryHistory.getLogisVndrCode());
			jsonData.put("waybilNoText", gridOcOrderDeliveryHistory.getWaybilNoText());
			jsonData.put("uploadResultMsg", uploadResultMsg);

			// Grid List객체
			dataGrid.add(jsonData);

		}

		resultMap.put("failCnt", failCnt); // 실패건수
		resultMap.put("successCnt", (entityGrid.length - failCnt)); // 성공건수
		resultMap.put("callPopupType", "R");
		resultMap.put("result", "Y");
		resultMap.put("DATA", dataGrid);

		writeJson(parameter, resultMap);

	}

	/**
	 * 
	 * @Desc : 업체 주문 배송관리 - 엑셀 전체 다운로드
	 * @Method Name : deliveryOrderVendorSelectExcel
	 * @Date : 2019. 5. 14.
	 * @Author : NKB 이강수
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/delivery-order-vendor/excelAll")
	public void deliveryOrderVendorAllExcel(Parameter<OcDelivery> parameter) throws Exception {

		Pageable<OcDelivery, OcDelivery> pageable = new Pageable<>(parameter);
		/*
		 * 입점 업체 분기 처리 용
		 */
		UserDetails user = LoginManager.getUserDetails();

		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		if (!isAdmin) {
			pageable.getBean().setVndrNo(user.getVndrNo());
		}

		Page<OcDelivery> page = deliveryService.getDeliveryOrderVendorAllExcel(pageable);

		ExcelValue excelValue = ExcelValue.builder(1, 0).columnNames(Arrays.asList("vndrName" // 업체명
				, "vndrNo" // 업체번호
				, "siteName" // 사이트
				, "deviceCodeName" // 결제구분
				, "prdtTypeCodeName" // 주문상품구분
				, "strOrderDtm" // 주문일시
				, "orderNo" // 주문번호
				, "orderPrdtSeq" // 주문상품순번
				, "vndrPrdtNoText" // 업체상품코드
				, "dlvyIdText" // 배송ID
				//, "orderPrdtStatCodeName" // 주문상품상태
				, "dlvyStatCodeName" // 주문배송상태
				, "logisVndrCodeName" // 택배사
				, "waybilNoText" // 운송장번호
				, "strDlvyProcDtm" // 배송지시일자
				, "prdtNo" // 상품코드
				, "prdtName" // 상품명
				, "optnName" // 옵션
				, "strSellAmt" // 판매가
				, "strOrderAmt" // 결제금액
				, "sellCnclReqYn" // 판매취소요청
				, "delaySendYn" // 발송지연\n안내여부
				, "buyerInfo" // 주문자
				, "buyerHdphnNoText" // 주문자\n휴대폰번호
				, "brandName" // 브랜드
				, "brandNo" // 브랜드NO
				// , "strLogisPymntDlvyAmt" // 배송비
				, "strTotalDscntAmt" // 상품\n총 할인금액
				, "strCpnDscntAmt" // 적용쿠폰 할인금액\n(일반/플러스)
				, "rcvrName" // 수취인명
				, "rcvrTelNoText" // 수취인전화번호
				, "rcvrHdphnNoText" // 수취인핸드폰번호
				, "rcvrPostCodeText" // 수취인우편번호
				, "rcvrPostAddrText" // 수취인우편주소
				, "rcvrDtlAddrText" // 수취인상세주소
		)).data(page.getContent()).build();

		parameter.downloadExcelTemplate("delivery/excel/deliveryOrderVendor", excelValue);

	}

	/**
	 * @Desc : 판매취소 요청 현황
	 * @Method Name : deliveryOrderVendorCancelMain
	 * @Date : 2019. 5. 14.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor-cancel")
	public ModelAndView deliveryOrderVendorCancelMain(Parameter<?> parameter) throws Exception {

		/*
		 * 입점 업체 분기 처리 용
		 */
		UserDetails user = LoginManager.getUserDetails();

		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		if (!isAdmin) {
			VdVendor vndr = new VdVendor();
			vndr.setVndrNo(user.getVndrNo());
			vndr = vendorService.getVendorBaseInfo(vndr);

			parameter.addAttribute("vndrNo", vndr.getVndrNo());
			parameter.addAttribute("vndrName", vndr.getVndrName());
		}

		// dash board
		if (parameter.getString("fromDash") != null) {
			parameter.addAttribute("fromDash", parameter.getString("fromDash"));
		}

		// Grid 코드정보
		String[] codeFields = { CommonCode.DEVICE_CODE // 발송처
				, CommonCode.SELL_CNCL_RSN_CODE // 주문배송유형
		};

		// site Combo,site List
		Pair<JSONObject, List<SySite>> siteCode = siteService.getSiteListByCombo();

		// Code값 get
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		parameter.addAttribute("codeCombo", pair.getFirst()); // code Combo
		parameter.addAttribute("codeList", pair.getSecond()); // code list
		parameter.addAttribute("siteCombo", siteCode.getFirst()); // Site Combo
		parameter.addAttribute("siteList", siteCode.getSecond()); // Site List

		return forward("/delivery/delivery-order-vendor-cancel-main");
	}

	/**
	 * 
	 * @Desc : 판매취소 요청 현황 조회
	 * @Method Name : deliveryOrderVendorCancel
	 * @Date : 2019. 5. 14.
	 * @Author : NKB
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor-cancel/read-list")
	@ResponseBody
	public void deliveryOrderVendorCancel(Parameter<OcDelivery> parameter) throws Exception {

		Pageable<OcDelivery, OcDelivery> pageable = new Pageable<>(parameter);

		/*
		 * 입점 업체 분기 처리 용
		 */
		UserDetails user = LoginManager.getUserDetails();

		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		if (!isAdmin) {
			pageable.getBean().setVndrNo(user.getVndrNo());
		}

		Page<OcDelivery> page = deliveryService.deliveryOrderVendorCancelList(pageable);

		UtilsResponse.writeJson(parameter.getResponse(), page.getGrid());

	}

	/**
	 * 
	 * @Desc : 판매취소요청 주문 목록 엑셀 다운로드
	 * @Method Name : deliveryOrderVendorClaimAllExcel
	 * @Date : 2019. 5. 19.
	 * @Author : NKB
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/delivery-order-vendor/cancel/excelAll")
	public void deliveryOrderVendorCancelAllExcel(Parameter<OcDelivery> parameter) throws Exception {

		Pageable<OcDelivery, OcDelivery> pageable = new Pageable<>(parameter);
		/*
		 * 입점 업체 분기 처리 용
		 */
		UserDetails user = LoginManager.getUserDetails();

		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		if (!isAdmin) {
			pageable.getBean().setVndrNo(user.getVndrNo());
		}

		Page<OcDelivery> page = deliveryService.getDeliveryOrderVendorCancelAllExcel(pageable);

		ExcelValue excelValue = ExcelValue.builder(1).columnNames(Arrays.asList("siteName" // 사이트
				, "deviceCodeName" // 결제구분
				, "orderNo" // 주문번호
				, "buyerInfo" // 주문자
				, "buyerHdphnNoText" // 주문자\n휴대폰번호
				, "vndrPrdtNoText" // 업체상품코드
				, "prdtNo" // 상품코드
				, "prdtName" // 상품명
				, "optnName" // 옵션
				, "dlvyIdText" // 배송ID
				, "strSellAmt" // 판매가
				, "strOrderAmt" // 결제금액
				, "sellCnclRsnCodeName" // 판매취소사유
				, "strSellCnclReqDtm" // 요청일시
				, "sellCnclReqtr" // 요청자
				, "strModDtm" // 주문취소일시
		)).data(page.getContent()).build();

		parameter.downloadExcelTemplate("delivery/excel/deliveryOrderVendorCancel", excelValue);

	}

	/**
	 * @Desc : 엑셀 업로드 샘플 양식
	 * @Method Name : vendorSampleDownload
	 * @Date : 2019. 9. 2.
	 * @Author : NKB
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor/excelUploadSample")
	public void vendorSampleDownload(Parameter<OcDelivery> parameter) throws Exception {

		List<OcDelivery> list = new ArrayList<>();

		ExcelValue excelValue = ExcelValue.builder(2, 0)
				.columnNames(Arrays.asList("주문번호", "주문순번", "업체상품코드", "상품코드", "배송ID", "택배사", "운송장번호")).data(list)
				.build();

		log.debug(":::::[vendorSampleDownload]:::::::");
		parameter.downloadExcelTemplate("delivery/excel/excelUploadSample", excelValue);
	}

	/**
	 * 
	 * @Desc : 업체 클레임 관리 메인
	 * @Method Name : deliveryOrderVendorClaimMain
	 * @Date : 2019. 5. 14.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor-claim")
	public ModelAndView deliveryOrderVendorClaimMain(Parameter<?> parameter) throws Exception {

		/*
		 * 입점 업체 분기 처리 용
		 */
		UserDetails user = LoginManager.getUserDetails();

		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		if (!isAdmin) {
			VdVendor vndr = new VdVendor();
			vndr.setVndrNo(user.getVndrNo());
			vndr = vendorService.getVendorBaseInfo(vndr);

			parameter.addAttribute("vndrNo", vndr.getVndrNo());
			parameter.addAttribute("vndrName", vndr.getVndrName());
		}

		// Grid 코드정보
		String[] codeFields = { CommonCode.CLM_GBN_CODE // 클레임구분
				, CommonCode.CLM_RSN_CODE // 클레임사유
				// , CommonCode.CLM_STAT_CODE // 클레임상태
				, "CLM_PRDT_STAT_CODE" // 클레임 상품 상태
				, CommonCode.LOGIS_VNDR_CODE // 택배사코드

		};

		// site Combo,site List
		Pair<JSONObject, List<SySite>> siteCode = siteService.getSiteListByCombo();

		// Code값 get
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		parameter.addAttribute("codeCombo", pair.getFirst()); // code Combo
		parameter.addAttribute("codeList", pair.getSecond()); // code list
		parameter.addAttribute("siteCombo", siteCode.getFirst()); // Site Combo
		parameter.addAttribute("siteList", siteCode.getSecond()); // Site List

		return forward("/delivery/delivery-order-vendor-claim-main");
	}

	/**
	 * 
	 * @Desc : 업체별 클레임 건수 조회
	 * @Method Name : deliveryOrderVendorClaimCount
	 * @Date : 2019. 5. 15.
	 * @Author : NKB
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor-claim-main/claim/count")
	public void deliveryOrderVendorClaimCount(Parameter<OcDelivery> parameter) throws Exception {

		OcDelivery ocDelivery = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		/*
		 * 입점 업체 분기 처리 용
		 */
		UserDetails user = LoginManager.getUserDetails();

		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		if (!isAdmin) {
			ocDelivery.setVndrNo(user.getVndrNo());
		}

		resultMap = deliveryService.getDeliveryOrderVendorClaim(ocDelivery);

		log.debug("================================================= /deliveryOrderVendorClaimCount " + resultMap);
		writeJson(parameter, resultMap);

	}

	/**
	 * 
	 * @Desc : 업체 주문 클레임관리 - List
	 * @Method Name : deliveryOrderVendorClaim
	 * @Date : 2019. 5. 15.
	 * @Author : NKB
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor-claim-main/claim/read-list")
	public void deliveryOrderVendorClaim(Parameter<OcDelivery> parameter) throws Exception {

		Pageable<OcDelivery, OcDelivery> pageable = new Pageable<>(parameter);

		/*
		 * 입점 업체 분기 처리 용
		 */
		UserDetails user = LoginManager.getUserDetails();

		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		if (!isAdmin) {
			pageable.getBean().setVndrNo(user.getVndrNo());
		}

		Page<OcDelivery> page = deliveryService.deliveryOrderVendorClaimList(pageable);

		UtilsResponse.writeJson(parameter.getResponse(), page.getGrid());
	}

	/**
	 * 
	 * @Desc : 엑셀 전체 다운로드
	 * @Method Name : deliveryOrderVendorClaimAllExcel
	 * @Date : 2019. 5. 15.
	 * @Author : NKB
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/delivery-order-vendor/claim/excelAll")
	public void deliveryOrderVendorClaimAllExcel(Parameter<OcDelivery> parameter) throws Exception {

		Pageable<OcDelivery, OcDelivery> pageable = new Pageable<>(parameter);
		pageable.setRowsPerPage(60000);

		/*
		 * 입점 업체 분기 처리 용
		 */
		UserDetails user = LoginManager.getUserDetails();

		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		if (!isAdmin) {
			pageable.getBean().setVndrNo(user.getVndrNo());
		}

		Page<OcDelivery> page = deliveryService.deliveryOrderVendorClaimList(pageable);

		// S(환불금액차감), P(추가결제), C:무료쿠폰
		for (int i = 0; i < page.getTotalCount(); i++) {
			if (UtilsText.equals(page.getContent().get(i).getAddDlvyAmtPymntType(),
					CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_REFUND_DEDUCTION)) {
				page.getContent().get(i).setAddDlvyAmtPymntTypeName("환불액차감");
			} else if (UtilsText.equals(page.getContent().get(i).getAddDlvyAmtPymntType(),
					CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT)) {
				page.getContent().get(i).setAddDlvyAmtPymntTypeName("추가결제");
			} else if (UtilsText.equals(page.getContent().get(i).getAddDlvyAmtPymntType(),
					CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON)) {
				page.getContent().get(i).setAddDlvyAmtPymntTypeName("무료쿠폰");
			} else if (UtilsText.equals(page.getContent().get(i).getAddDlvyAmtPymntType(), "")) {
				page.getContent().get(i).setAddDlvyAmtPymntTypeName("환불액차감");
			}
		}
		ExcelValue excelValue = ExcelValue.builder(1).columnNames(Arrays.asList("vndrName" // 업체명
				, "clmNo" // 클레임번호
				, "rgstDtm" // 클레임접수일
				, "clmGbnName" // 클레임구분
				, "clmRsnName" // 클레임사유
				, "orderNo" // 주문번호
				, "vndrPrdtNoText" // 업체상품코드
				, "prdtNo" // 상품코드
				, "prdtName" // 상품명
				, "optnName" // 옵션
				, "sellAmt" // 판매가
				, "orderAmt" // 결제금액
				, "addDlvyAmtPymntTypeName" // 클레임 베송결제수단
				, "addDlvyAmt" // 클레임배송비
				, "clmPrdtStatName" // 클레임상태
				, "logisVndrCode" // 택배사
				, "waybilNoText" // 운송장번호
				, "dlvyProcDtm" // 발송일자
		)).data(page.getContent()).build();

		parameter.downloadExcelTemplate("delivery/excel/deliveryOrderVendorClaim", excelValue);

	}

	/**
	 * 
	 * @Desc : 업체클레임 관리 클레임 상태변경
	 * @Method Name : deliveryOrderVendorClaimSave
	 * @Date : 2019. 5. 15.
	 * @Author : NKB
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor/claim/save")
	public void deliveryOrderVendorClaimSave(Parameter<?> parameter) throws Exception {
		// Parameter 클래스의 createArray 메소드를 이용하여 그리드에서 배열로 넘어온 파라메터 값을 VO에 맵핑

		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 대상 파라미터 맵핑
		OcClaim ocClaim = parameter.create(OcClaim.class);

		// Parameter 클래스의 createArray 메소드를 이용하여 그리드에서 배열로 넘어온 파라메터 값을 VO에 맵핑
		OcClaimProduct[] ocClaimProdGrid = parameter.createArray(OcClaimProduct.class, "clmNo");

		// 배송지 변경 시작
		for (int i = 0; i < ocClaimProdGrid.length; i++) {
			OcClaimProduct gridOcClaimProduct = ocClaimProdGrid[i];

			log.debug("::::::::::::::::::::::::::::::getClmNo]]]" + ocClaim.getClmNo());
			log.debug("::::::::::::::::::::::::::::::getClmPrdtSeq]]]" + gridOcClaimProduct.getClmPrdtSeq());
			log.debug("::::::::::::::::::::::::::::::getOrderNo]]]" + gridOcClaimProduct.getOrderNo());
			log.debug("::::::::::::::::::::::::::::::getClmPrdtStatCode]]]" + gridOcClaimProduct.getClmPrdtStatCode());

			resultMap = deliveryService.updateDeliveryOrderVendorClaim(ocClaim, gridOcClaimProduct);
			log.debug("::::::::::::::::::::::::::::::resultMap]]]" + resultMap);
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * 
	 * @Desc : 엑셀 업로드 팝업 호출 - 우선은 기능에서 제외 하지만 추후 만들어 질수 있기에 소스는 삭제 안함
	 * @Method Name : deliveryOrderVendorClaimExcelUploadPopup
	 * @Date : 2019. 5. 16.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor/claim-excel-upload-popup")
	public ModelAndView deliveryOrderVendorClaimExcelUploadPopup(Parameter<?> parameter) throws Exception {

		String callPopupType = parameter.getString("callPopupType");

		String[] codeFields = { CommonCode.LOGIS_VNDR_CODE // 택배사코드
		};

		// Code값 get
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		parameter.addAttribute("codeCombo", pair.getFirst()); // code Combo
		parameter.addAttribute("codeList", pair.getSecond()); // code list
		parameter.addAttribute("callPopupType", callPopupType);

		return forward("/delivery/delivery-order-vendor-excel-popup");
	}

	/**
	 * 
	 * @Desc : 클레임 불가 요청 현황
	 * @Method Name : deliveryOrderVendorImpossibilityMain
	 * @Date : 2019. 5. 19.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor-impossibility")
	public ModelAndView deliveryOrderVendorImpossibilityMain(Parameter<?> parameter) throws Exception {

		// dash borad
		if (parameter.getString("fromDash") != null) {
			parameter.addAttribute("fromDash", parameter.getString("fromDash"));
		}

		// Grid 코드정보
		String[] codeFields = { CommonCode.DEVICE_CODE // 디바이스 구분
				, CommonCode.CLM_IMPSBLT_RSN_CODE // 클레임 불가 요청사유
				, CommonCode.CLM_GBN_CODE // 클레임구분
				, CommonCode.LOGIS_VNDR_CODE // 택배사코드
		};

		// site Combo,site List
		Pair<JSONObject, List<SySite>> siteCode = siteService.getSiteListByCombo();

		// Code값 get
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		parameter.addAttribute("codeCombo", pair.getFirst()); // code Combo
		parameter.addAttribute("codeList", pair.getSecond()); // code list
		parameter.addAttribute("siteCombo", siteCode.getFirst()); // Site Combo
		parameter.addAttribute("siteList", siteCode.getSecond()); // Site List

		return forward("/delivery/delivery-order-vendor-impossibility-main");
	}

	/**
	 * 
	 * @Desc : 클레임 불가 요청 현황
	 * @Method Name : deliveryOrderVendorImpossibility
	 * @Date : 2019. 5. 19.
	 * @Author : NKB
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor-impossibility/read-list")
	@ResponseBody
	public void deliveryOrderVendorImpossibility(Parameter<OcDelivery> parameter) throws Exception {

		Pageable<OcDelivery, OcDelivery> pageable = new Pageable<>(parameter);

		Page<OcDelivery> page = deliveryService.deliveryOrderVendorImpossibilityList(pageable);

		UtilsResponse.writeJson(parameter.getResponse(), page.getGrid());

	}

	@RequestMapping("/delivery-order-vendor/impossibility/excelAll")
	public void deliveryOrderVendorImpossibilityAllExcel(Parameter<OcDelivery> parameter) throws Exception {

		Pageable<OcDelivery, OcDelivery> pageable = new Pageable<>(parameter);
		pageable.setRowsPerPage(60000);
		Page<OcDelivery> page = deliveryService.deliveryOrderVendorImpossibilityList(pageable);

		ExcelValue excelValue = ExcelValue.builder(1).columnNames(Arrays.asList("vndrName" // 업체명
				, "siteNo" // 사이트
				, "clmNo" // 클레임번호
				, "clmGbnCode" // 클레임구분
				, "orderNo" // 주문번호
				, "buyerName" // 주문자
				, "buyerHdphnNoText" // 주문자 휴대폰번호
				, "prdtNo" // 상품코드
				, "prdtName" // 상품명
				, "optnName" // 옵션
				, "sellAmt" // 판매가
				, "orderAmt" // 결제금액
				, "clmImpsbltRsnCode" // 클레임 불가 요청사유
				, "dlvyIdText" // 배송ID
				, "logisVndrCode" // 택배사
				, "waybilNoText" // 운송장번호
				, "dlvyProcDtm" // 발송일자
				, "sellCnclReqDtm" // 요청일시
				, "sellCnclReqtrNo" // 요청자
				, "modDtm" // 주문취소일시
		)).data(page.getContent()).build();

		parameter.downloadExcelTemplate("delivery/excel/deliveryOrderVendorImpossibility", excelValue);

	}

	// =========================================================================================================================

	/**
	 * 
	 * @Desc : 재배송처리 가능 여부 확인 체크
	 * @Method Name : getReDeliveryCheck
	 * @Date : 2019. 3. 25.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/re-delivery-check")
	public void getReDeliveryCheck(Parameter<OcOrderDeliveryHistory> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		OcOrderDeliveryHistory ocOrderDeliveryHistory = parameter.get();

		resultMap = deliveryService.getReDeliveryCheck(ocOrderDeliveryHistory);

		writeJson(parameter, resultMap);
	}

	/**
	 * 
	 * @Desc : 재배송 처리 팝업
	 * @Method Name : getReDeliveryPop
	 * @Date : 2019. 3. 25.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/re-delivery-pop")
	public ModelAndView getReDeliveryPop(Parameter<OcOrderDeliveryHistory> parameter) throws Exception {

		deliveryService.getOrderDeliveryInfo(parameter);

		return forward("/delivery/re_delivery-pop");
	}

	/**
	 * 
	 * @Desc : 재배송 처리 등록
	 * @Method Name : setReDeliverySave
	 * @Date : 2019. 3. 25.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/re-delivery-save")
	public void setReDeliverySave(Parameter<OcOrderDeliveryHistory> parameter) throws Exception {
		// System.out.println("parameter.get()11111 >>>> " + parameter.get());

		String stockGbnCode = parameter.get().getStockGbnCode(); // 선택된 재고구분코드
		String orgStockGbnCode = parameter.get().getOrgStockGbnCode(); // 이전 배송 구분코드

		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 신규 재고,재고 구분
		OcDelivery ocDelivery = new OcDelivery();
		ocDelivery.setOrderNo(parameter.get().getOrderNo());
		ocDelivery.setNewStockGbnCode(stockGbnCode);

		// 이전배송,재고 구분
		OcOrderDeliveryHistory ocOrderDeliveryHistory = parameter.get();
		ocOrderDeliveryHistory.setStockGbnCode(orgStockGbnCode);

		// resultMap = deliveryService.setReDeliverySave(ocOrderDeliveryHistory);

		// System.out.println("ocDelivery >>> " + ocDelivery);
		// System.out.println("ocOrderDeliveryHistory >>> " + ocOrderDeliveryHistory);

		resultMap = deliveryService.insertDeliveryOrderStandby(ocDelivery, ocOrderDeliveryHistory);

		writeJson(parameter, resultMap);
	}

	/**
	 * 
	 * @Desc : 택배사 코드 팝업
	 * @Method Name : deliveryOrderVendorLogisSelectPopup
	 * @Date : 2019. 10. 1.
	 * @Author : NKB
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/vendor/delivery-order-vendor/logisPopup")
	public ModelAndView deliveryOrderVendorLogisSelectPopup(Parameter<?> parameter) throws Exception {
		return forward("/delivery/delivery-order-vendor-logis-popup");

	}

	@RequestMapping("/vendor/delivery-order-vendor/logisPopup/read-list")
	@ResponseBody
	public void deliveryOrderVendorLogisRead(Parameter<OcDelivery> parameter) throws Exception {

		SyCodeDetail syCodeDetail = new SyCodeDetail();
		syCodeDetail.setCodeField(CommonCode.LOGIS_VNDR_CODE);
		syCodeDetail.setUseYn("Y");

		// Code값 get
		List<SyCodeDetail> list = commonCodeService.getCodeDtlInfoList(syCodeDetail);

		Map<String, Object> gridPage = new HashMap<>();
		gridPage.put("Data", list);
		gridPage.put("Total", list.size());

		UtilsResponse.writeJson(parameter.getResponse(), gridPage);

	}

	/*************************************************************************************************
	 * leesangho START
	 *************************************************************************************************/
	/**
	 * @Desc : 업체 주문 상세 팝업으로 포워딩
	 * @Method Name : orderVendorDetailPop
	 * @Date : 2019. 4. 23.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/vendor/order/read-order-vendor-detail-pop")
	public ModelAndView orderVendorDetailPop(Parameter<OcOrder> parameter) throws Exception {
		orderService.getOrderVendorDetailInfo(parameter);
		return forward("/delivery/read-order-vendor-detail-pop");
	}

	/**
	 * @Desc : 업체 주문 상세
	 * @Method Name : readOrderVendorInfoTab
	 * @Date : 2019. 4. 23.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/vendor/order/read-order-vendor-info-tab")
	public ModelAndView readOrderVendorInfoTab(Parameter<OcOrder> parameter) throws Exception {
		orderService.getReadOrderVendorInfoTab(parameter);
		return forward("/delivery/read-order-vendor-info-tab");
	}

	/**
	 * @Desc : 업체 주문 상품 리스트
	 * @Method Name : readOrderVendorProductList
	 * @Date : 2019. 4. 23.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/vendor/order/read-order-vendor-product-list")
	public void readOrderVendorProductList(Parameter<?> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("data", orderService.getReadOrderVendorProductList(parameter));

		writeJson(parameter, dataMap);
	}

	/**
	 * @Desc : 업체 주문 상세 관리자 메모 탭으로 포워딩
	 * @Method Name : memoVendorRead
	 * @Date : 2019. 4. 23.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/vendor/order/read-memo-vendor-info-tab")
	public ModelAndView memoVendorRead(Parameter<OcOrderMemo> parameter) throws Exception {
		parameter.addAttribute("orderNo", parameter.get().getOrderNo());
		return forward("/delivery/read-memo-vendor-info-tab");
	}

	/**
	 * @Desc : 업체 관리자 메모 리스트
	 * @Method Name : memoVendorList
	 * @Date : 2019. 4. 23.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/vendor/order/read-memo-vendor-info-tab/list")
	public void memoVendorList(Parameter<OcOrderMemo> parameter) throws Exception {
		Map<String, Object> memoList = new HashMap<String, Object>();
		if (parameter.getString("listType") != null && parameter.getString("listType").equals("option")) {
			memoList = orderService.memoSelectByOrderNo(parameter.get());
		} else {
			String[] codeFields = { CommonCode.CLM_GBN_CODE, CommonCode.ORDER_MEMO_GBN_CODE };
			memoList = orderService.memoSelectAllByOrderNo(parameter.get());
			memoList.put("codeList", orderService.getCodeListByGroup(codeFields));
		}
		memoList.put("adminNo", LoginManager.getUserDetails().getAdminNo());
		UtilsResponse.writeJson(parameter.getResponse(), memoList);
	}

	/**
	 * @Desc : 업체 관리자 메모 등록
	 * @Method Name : memoVendorRegist
	 * @Date : 2019. 4. 23.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/vendor/order/regist-vendor-memo")
	public void memoVendorRegist(Parameter<OcOrderMemo> parameter) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			OcOrderMemo ocOrderMemo = parameter.get();
			ocOrderMemo.setDelYn("N");
			ocOrderMemo.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
			orderService.memoCreate(ocOrderMemo);

			result.put("status", "success");
			UtilsResponse.writeJson(parameter.getResponse(), result);
		} catch (Exception e) {
			result.put("status", e.getStackTrace());
			UtilsResponse.writeJson(parameter.getResponse(), result);
		}
	}

	/**
	 * @Desc : 업체 주문 상세 관리자 메모 삭제
	 * @Method Name : memoVendorDelete
	 * @Date : 2019. 4. 23.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/vendor/order/delete-vendor-memo")
	public void memoVendorDelete(Parameter<OcOrderMemo> parameter) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			orderService.memoDelete(parameter.get());
			result.put("status", "success");
			UtilsResponse.writeJson(parameter.getResponse(), result);
		} catch (Exception e) {
			result.put("status", e.getStackTrace());
			UtilsResponse.writeJson(parameter.getResponse(), result);
		}
	}

	/*************************************************************************************************
	 * leesangho END
	 *************************************************************************************************/
}