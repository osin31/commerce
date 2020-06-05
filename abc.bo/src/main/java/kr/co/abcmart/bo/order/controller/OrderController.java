package kr.co.abcmart.bo.order.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.abcmart.bo.order.model.master.IfOffDealHistory;
import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.bo.order.model.master.OcOrderMemo;
import kr.co.abcmart.bo.order.model.master.OcOrderPayment;
import kr.co.abcmart.bo.order.model.master.OcOrderPaymentFailureHistory;
import kr.co.abcmart.bo.order.model.master.OcOrderProduct;
import kr.co.abcmart.bo.order.model.master.OcOrderProductHistory;
import kr.co.abcmart.bo.order.model.master.OcOrderReceiverChangeHistory;
import kr.co.abcmart.bo.order.model.master.OcOrderZeroBankbookFailureDetails;
import kr.co.abcmart.bo.order.service.OrderService;
import kr.co.abcmart.bo.order.vo.OcOrderExcelVo;
import kr.co.abcmart.bo.order.vo.OcOrderMemberExpostSaveVO;
import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsResponse;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.interfaces.module.payment.kakao.model.KakaoPaymentOrder;
import kr.co.abcmart.interfaces.module.payment.naver.model.NaverPaymentListReturn;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc :
 * @FileName : OrderController.java
 * @Project : abc.bo
 * @Date : 2019. 2. 11.
 * @Author : ljyoung@3top.co.kr
 */
@Slf4j
@Controller
@RequestMapping("order")
public class OrderController extends BaseController {

	@Autowired
	private OrderService service;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private SiteService siteService;

	/*************************************************************************************************
	 * jeon start
	 *************************************************************************************************/

	/**
	 * 
	 * @Desc : 주문관리 목록 페이지 이동
	 * @Method Name : getOrderList
	 * @Date : 2019. 2. 8.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("")
	public ModelAndView getOrderList(Parameter<?> parameter) throws Exception {

		service.getOrderInfoMain(parameter);

		if (UtilsText.isNotBlank(parameter.getString("fromDash"))
				&& UtilsText.equals(Const.BOOLEAN_TRUE, parameter.getString("fromDash"))) {
			parameter.addAttribute("fromDash", parameter.getString("fromDash"));
			parameter.addAttribute("tabIdx", parameter.getString("tabIdx"));
			parameter.addAttribute("fromDashOrderPrdtStatCode", parameter.getString("orderPrdtStatCode"));
		}

		return forward("/order/order-main");
	}

	/**
	 * 
	 * @Desc : Channel 검색
	 * @Method Name : changeHistoryFields
	 * @Date : 2019. 2. 22.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/channel-list")
	@ResponseBody
	public void changeHistoryFields(Parameter<?> parameter) throws Exception {
		writeJson(parameter, siteService.getUseChannelList());
	}

	/**
	 * 
	 * @Desc : 결제 목록 조회
	 * @Method Name : readList
	 * @Date : 2019. 2. 15.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-list")
	@ResponseBody
	public void readList(Parameter<OcOrder> parameter) throws Exception {

		Pageable<OcOrder, OcOrder> orderVOPageble = new Pageable<>(parameter);

		Page<OcOrder> page = service.getOrderList(orderVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * 
	 * @Desc : 엑셀다운로드 전체
	 * @Method Name : onOrderForExcelDn
	 * @Date : 2019. 3. 21.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/onOrdeListExcelDn")
	public void onOrderForExcelDn(Parameter<OcOrder> parameter) throws Exception {
		OcOrder params = parameter.get();
		List<OcOrderExcelVo> list = service.getonOrderForExcelList(params);

		ExcelValue excelValue = ExcelValue.builder(1)
				.columnNames(Arrays.asList("siteName", "orderNo", "orderDtm", "orderPrdtStatCodeName", "salesCnclGbnType",
						"pymntMeansCodeName", "prdtNo", "vndrPrdtNoText", "brandName", "prdtName", "styleInfo",
						"prdtColorInfo", "optnName", "prdtTypeFlag", "orderQty", "mmnyPrdtYn", "dlvyTypeCodeName",
						"stockGbnCodeName", "storeName", "logisVndrCodeName", "waybilNoText", "dlvyProcDtm",
						"prdtNormalAmt", "prdtSellAmt", "empAmt", "totalDscntAmt", "cpnDscntAmt", "orderAmt", "buyPointUseAmt", "eventPointUseAmt",
						"promoName", "couponName", "vendorCancelFlag", "memberTypeCodeName", "dlvyIdText"))
				.data(list).build();

		parameter.downloadExcelTemplate("order/excel/orderList", excelValue);

	}

	/**
	 * 
	 * @Desc : 엑셀 다운로드 선택
	 * @Method Name : onOrdeSelectListExcelDn
	 * @Date : 2019. 3. 21.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/onOrderSelectListExcelDn")
	public void onOrdeSelectListExcelDn(Parameter<OcOrder> parameter) throws Exception {
		OcOrder params = parameter.get();

		String[] orderNos = params.getStrOrderNos().split(",");
		params.setOrderNos(orderNos);

		List<OcOrderExcelVo> list = service.getonOrdeSelectListExcelDn(params);

		ExcelValue excelValue = ExcelValue.builder(1)
				.columnNames(Arrays.asList("siteName", "orderNo", "orderDtm", "orderPrdtStatCodeName", "salesCnclGbnType",
						"pymntMeansCodeName", "prdtNo", "vndrPrdtNoText", "brandName", "prdtName", "styleInfo",
						"prdtColorInfo", "optnName", "prdtTypeFlag", "orderQty", "mmnyPrdtYn", "dlvyTypeCodeName",
						"stockGbnCodeName", "storeName", "logisVndrCodeName", "waybilNoText", "dlvyProcDtm",
						"prdtNormalAmt", "prdtSellAmt", "empAmt", "totalDscntAmt", "cpnDscntAmt", "orderAmt", "buyPointUseAmt", "eventPointUseAmt",
						"promoName", "couponName", "vendorCancelFlag", "memberTypeCodeName", "dlvyIdText"))
				.data(list).build();

		parameter.downloadExcelTemplate("order/excel/orderList", excelValue);

	}

	/**
	 * 
	 * @Desc : 주문 상세
	 * @Method Name : orderDetailPop
	 * @Date : 2019. 2. 24.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/read-detail-pop")
	public ModelAndView orderDetailPop(Parameter<OcOrder> parameter) throws Exception {
		service.getOrderDetailInfo(parameter);
		return forward("/order/read-order-detail-pop");
	}

	/**
	 * 
	 * @Desc : 주문 상세 내 주문 정보 영역
	 * @Method Name : readOrderInfoTab
	 * @Date : 2019. 2. 24.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-order-info-tab")
	public ModelAndView readOrderInfoTab(Parameter<OcOrder> parameter) throws Exception {
		service.getReadOrderInfoTab(parameter);
		
		// 2020.05.13 : 쿠폰지급 버튼 노출여부를 따지기 위해 set
		parameter.addAttribute("noCpnBtnLoinId", LoginManager.getUserDetails().getLoginId());
		
		return forward("/order/read-order-info-tab");
	}

	/**
	 * 
	 * @Desc : 주문 상세 내 주문 상품 영역
	 * @Method Name : readOrderInfoTab
	 * @Date : 2019. 2. 24.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-order-info-product-list")
	public void readOrderProductList(Parameter<?> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("data", service.getReadOrderProductList(parameter));

		writeJson(parameter, dataMap);
	}

	/**
	 * 
	 * @Desc : 할인 정보 ( 쿠폰 + 프로모션)
	 * @Method Name : readOrderDiscountList
	 * @Date : 2019. 2. 24.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-order-info-discount-list")
	public void readOrderDiscountList(Parameter<OcOrder> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("data", service.getReadOrderDiscountList(parameter));

		writeJson(parameter, dataMap);
	}

	/**
	 * 
	 * @Desc : 결제 정보 상세
	 * @Method Name : readOrderPaymentList
	 * @Date : 2019. 2. 24.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-order-info-payment-list")
	public void readOrderPaymentList(Parameter<OcOrder> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("data", service.getReadOrderPaymentList(parameter));

		writeJson(parameter, dataMap);
	}

	/**
	 * 
	 * @Desc : 구매 확정 처리
	 * @Method Name : createAccountAuth
	 * @Date : 2019. 3. 1.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/order-Confirm")
	public void UpdateOrderConfirm(Parameter<OcOrder> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OcOrder ocOrder = parameter.get();
		resultMap = service.updateOrderConfirm(ocOrder);

		writeJson(parameter, resultMap);
	}

	/**
	 * 
	 * @Desc : 배송중단처리
	 * @Method Name : UpdateDeliveryStop
	 * @Date : 2019. 3. 1.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/delivery-stop")
	public void UpdateDeliveryStop(Parameter<OcOrderProduct> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OcOrderProduct ocOrderProduct = parameter.get();
		resultMap = service.UpdateDeliveryStop(ocOrderProduct);

		writeJson(parameter, resultMap);
	}

	/**
	 * 
	 * @Desc : 비회원 비밀번호 변경
	 * @Method Name : nonCustPasswordChange
	 * @Date : 2019. 3. 2.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/passwordChangePop")
	public ModelAndView nonCustPasswordChange(Parameter<OcOrder> parameter) throws Exception {
		OcOrder orderDtail = service.getOcOrderMstDetail(parameter);
		parameter.addAttribute("orderDtail", orderDtail);
		return forward("/order/password-change-pop");
	}

	/**
	 * 
	 * @Desc : 비회원 비밀번호 변경 처리
	 * @Method Name : setPasswordChange
	 * @Date : 2019. 3. 2.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/passwordChange")
	public void setPasswordChange(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = service.updatePasswordChange(parameter);
		writeJson(parameter, resultMap);
	}

	/**
	 * 
	 * @Desc : 주문 마스터 정보 조회
	 * @Method Name : gerOrderDetailInfo
	 * @Date : 2019. 3. 3.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/order-detail-info")
	public void gerOrderDetailInfo(Parameter<OcOrder> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		OcOrder orderDtail = service.getOcOrderMstDetail(parameter);
		resultMap.put("result", orderDtail);

		writeJson(parameter, resultMap);
	}

	/**
	 * 
	 * @Desc : 주문 상태 및 변경 가능 여부 validation 체크
	 * @Method Name : getOrderVaildation
	 * @Date : 2019. 3. 3.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
//	@RequestMapping("/order-vaildation2")
//	public void getOrderVaildation2(Parameter<OcOrderProduct> parameter) throws Exception {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		log.debug("================================================= /vaildation ");
//		OcOrderProduct ocOrderProduct = parameter.get();
//		String vaildationType = ocOrderProduct.getVaildationType();
//
////		vaildationType 1 : 수령지 정보 변경 -- 결제 완료 일경우 에만 해당 
////		vaildationType 2 : 결제수단 변경 -- 클레임 접수 완료건이 없는 상태 ( 금액 변동이 일어나는 클레임건 제외 )
////		vaildationType 3  : 구매 확정 
//
//		if ("1".equals(vaildationType)) {
//			resultMap = service.getOrderVaildateTypeOne(ocOrderProduct);
//
//		} else if ("2".equals(vaildationType)) {
//			resultMap = service.getOrderVaildateTypeTwo(ocOrderProduct);
//		} else if ("3".equals(vaildationType)) {
//			resultMap = service.getOrderVaildateTypeThree(ocOrderProduct);
//		}
//
//		log.debug("================================================= /vaildation " + resultMap);
//		writeJson(parameter, resultMap);
//	}

	@RequestMapping("/order-vaildation")
	public void getOrderVaildation(Parameter<OcOrderProduct> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		log.debug("================================================= /vaildation ");
		OcOrderProduct ocOrderProduct = parameter.get();
		String vaildationType = ocOrderProduct.getVaildationType();

//		vaildationType 1 : 수령지 정보 변경 -- 결제 완료 일경우 에만 해당 
//		vaildationType 2 : 결제수단 변경 -- 클레임 접수 완료건이 없는 상태 ( 금액 변동이 일어나는 클레임건 제외 )
//		vaildationType 3  : 구매 확정 
//		vaildationType 4  : 주문 전체 취소 처리시 ( 상품준비중 상태까지 변경  20190918)  

		if (UtilsText.equals(vaildationType, "1")) {
			resultMap = service.getOrderAddrUpdateCheck(ocOrderProduct);
		} else if (UtilsText.equals(vaildationType, "2")) {
			resultMap = service.getOrderPaymentUpdateCheck(ocOrderProduct);
		} else if (UtilsText.equals(vaildationType, "3")) {
			resultMap = service.getOrderComfirmUpdateCheck(ocOrderProduct);
		} else if (UtilsText.equals(vaildationType, "4")) {
			resultMap = service.getOrderCancelCheck(ocOrderProduct);
		}
		log.debug("================================================= /vaildation " + resultMap);
		writeJson(parameter, resultMap);
	}

	/**
	 * 
	 * @Desc : 배송지 수정
	 * @Method Name : setRcvrInfoUdpate
	 * @Date : 2019. 3. 3.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/rcvrInfoUdpate")
	public void setRcvrInfoUdpate(Parameter<OcOrderReceiverChangeHistory> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		System.out.println("parameter.get() " + parameter.get());
		OcOrderReceiverChangeHistory rcvrInfo = parameter.get();
		resultMap = service.updateRcvrInfoUdpate(rcvrInfo);

		writeJson(parameter, resultMap);
	}

	/**
	 * 
	 * @Desc : 결제 수단 변경 처리
	 * @Method Name : setOrderPaymentChange
	 * @Date : 2019. 3. 8.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/orderPaymentChange")
	public void setOrderPaymentChange(Parameter<OcOrderPayment> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OcOrderPayment ocOrderPayment = parameter.get();

		resultMap = service.updateOrderPaymentChange(ocOrderPayment);

		writeJson(parameter, resultMap);
	}

	@RequestMapping("/dlvyDateModify")
	@ResponseBody
	public void updateNonPickupDlvyDateModify(Parameter<?> parameter) throws Exception {

		OcOrderProduct[] params = parameter.createArray(OcOrderProduct.class, "orderPrdtSeq",
				Arrays.asList("deliveryConfirmRgstDtm", "dlvyProcDtm", "dlvyDscntcProcDtm", "orderPrdtStatCodeName"));
		Map<String, Object> rsMap = service.updateNonPickupDlvyDateModify(params);

		writeJson(parameter, rsMap);
	}

	/**
	 * 
	 * @Desc : 회원 온라인 주문 목록 조회
	 * @Method Name : readOnlineOrderList
	 * @Date : 2019. 3. 9.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/member/onlineOrderList")
	@ResponseBody
	public void readOnlineOrderList(Parameter<OcOrder> parameter) throws Exception {

		Pageable<OcOrder, OcOrder> orderVOPageble = new Pageable<>(parameter);

		Page<OcOrder> page = service.readOnlineOrderList(orderVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * 
	 * @Desc : 회원 오프라인 주문 목록 조회
	 * @Method Name : readOfflineOrderList
	 * @Date : 2019. 3. 9.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/member/offlineOrderList")
	@ResponseBody
	public void readOfflineOrderList(Parameter<IfOffDealHistory> parameter) throws Exception {

		Pageable<IfOffDealHistory, IfOffDealHistory> orderVOPageble = new Pageable<>(parameter);

		Page<IfOffDealHistory> page = service.readOfflineOrderList(orderVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * 
	 * @Desc : 회원 문의시 상품 정보 조회
	 * @Method Name : getOrderProductList
	 * @Date : 2019. 3. 26.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/product-list")
	@ResponseBody
	public void getOrderProductList(Parameter<?> parameter) throws Exception {
		String orderNo = parameter.getString("orderNo");

		List<OcOrderProduct> list = service.selectOrderProductList(orderNo);

		Map<String, List<OcOrderProduct>> listGroup = list.stream().filter(f -> !"10004".equals(f.getPrdtTypeCode()))
				.collect(Collectors.groupingBy(OcOrderProduct::getPrdtNo)); // 배송비 상품 제외

		List<OcOrderProduct> returnList = new ArrayList<OcOrderProduct>();

		for (Map.Entry<String, List<OcOrderProduct>> entry : listGroup.entrySet()) {
			List<OcOrderProduct> listValue = entry.getValue();

			returnList.addAll(listValue);
		}

		writeJson(parameter, returnList);
	}

	/**
	 * 
	 * @Desc : 카카오페이 결제내역 조회 팝업 호출
	 * @Method Name : kakaoPayHistory
	 * @Date : 2019. 8. 30.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/kakaoPay/history")
	public ModelAndView kakaoPayHistory(Parameter<?> parameter) throws Exception {
		parameter.addAttribute("orderNo", parameter.getString("orderNo"));
		parameter.addAttribute("siteNo", parameter.getString("siteNo"));
		// parameter.addAttribute("tid", parameter.getString("pymntAprvNoText")); //
		// 카카오페이 tid로 변경하여 넘겨줌
		parameter.addAttribute("tid", parameter.getString("pymntOrganNoText")); // 카카오페이 tid로 변경하여 넘겨줌
		return forward("/order/kakaoPay-history-pop");
	}

	/**
	 * 
	 * @Desc : 카카오페이 결제내역 조회
	 * @Method Name : kakaoPayHistoryList
	 * @Date : 2019. 8. 30.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/kakaoPay/history-list")
	@ResponseBody
	public void kakaoPayHistoryList(Parameter<KakaoPaymentOrder> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		KakaoPaymentOrder kakaoPaymentOrder = parameter.get();
		String siteNo = parameter.getString("siteNo");
		if (UtilsText.equals(siteNo, Const.SITE_NO_ART)) {
			kakaoPaymentOrder.setKakaoAdminKey(Config.getString("art.kakao.api.adminkey"));
			kakaoPaymentOrder.setCid(Config.getString("art.kakao.api.cid"));
		}
		if (UtilsText.equals(siteNo, Const.SITE_NO_OTS)) {
			kakaoPaymentOrder.setKakaoAdminKey(Config.getString("ots.kakao.api.adminkey"));
			kakaoPaymentOrder.setCid(Config.getString("ots.kakao.api.cid"));
		}

		dataMap.put("data", service.getKakaoPayHistoryList(kakaoPaymentOrder));

		writeJson(parameter, dataMap);
	}

	/**
	 * @Desc : 편의점 배송 result
	 * @Method Name : svcResult
	 * @Date : 2019. 5. 2.
	 * @Author : ljyoung@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "cvsResult")
	public ModelAndView svcResult(Parameter<?> parameter) throws Exception {
		parameter.addAttribute("store_code", parameter.getString("store_code"));
		parameter.addAttribute("ho_code", parameter.getString("ho_code"));
		parameter.addAttribute("store_address1", parameter.getString("store_address1"));
		parameter.addAttribute("store_address2", parameter.getString("store_address2"));
		parameter.addAttribute("post_no", parameter.getString("post_no"));
		parameter.addAttribute("code_f", parameter.getString("code_f"));
		parameter.addAttribute("dd_zone", parameter.getString("dd_zone"));
		parameter.addAttribute("dd_bag", parameter.getString("dd_bag"));
		parameter.addAttribute("codePany", parameter.getString("codePany"));
		parameter.addAttribute("codeName", parameter.getString("codeName"));
		parameter.addAttribute("codeTel", parameter.getString("codeTel"));
		parameter.addAttribute("eupmyeon", parameter.getString("eupmyeon"));
		return forward("/order/cvsResult");
	}

	/**
	 * 
	 * @Desc : 결제 현금 영수증 조회 ( kcp , naverpay)
	 * @Method Name : getCashReceipte
	 * @Date : 2019. 8. 13.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/order-cashReceipte")
	@ResponseBody
	public void getCashReceipte(Parameter<OcOrderPayment> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		OcOrderPayment ocOrderPayment = parameter.get();

		resultMap = service.getCashReceipte(ocOrderPayment);

		writeJson(parameter, resultMap);
	}

	/*************************************************************************************************
	 * jeon end
	 *************************************************************************************************/

	/*************************************************************************************************
	 * ljyoung start
	 *************************************************************************************************/
	/**
	 * @Desc : 결제 실패 목록 페이지
	 * @Method Name : paymentFailList
	 * @Date : 2019. 2. 12.
	 * @Author : ljyoung@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/payment-fail-list")
	public ModelAndView paymentFailList(Parameter<OcOrder> parameter) throws Exception {
		String[] codeFields = { CommonCode.DEVICE_CODE, CommonCode.PYMNT_MEANS_CODE }; // 디바이스코드, 결제수단코드
		parameter.addAttribute("codeList", service.getCodeListByGroup(codeFields));
		service.getSiteList(parameter);

		return forward("/order/payment-fail-list");
	}

	/**
	 * @Desc : 결제 실패 목록 검색
	 * @Method Name : searchPaymentFailList
	 * @Date : 2019. 2. 12.
	 * @Author : ljyoung@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/payment-fail-list/search")
	public void searchPaymentFailList(Parameter<OcOrderPaymentFailureHistory> parameter) throws Exception {
		Pageable<OcOrderPaymentFailureHistory, OcOrderPaymentFailureHistory> pageable = new Pageable<>(parameter);
		Page<OcOrderPaymentFailureHistory> page = service.selectPaymentFailList(pageable);

		UtilsResponse.writeJson(parameter.getResponse(), page.getGrid());
	}

	/**
	 * @Desc : 무통장입금 실패 목록 페이지
	 * @Method Name : accountTransferFailList
	 * @Date : 2019. 2. 11.
	 * @Author : ljyoung@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/account-transfer-fail-list")
	public ModelAndView accountTransferFailList(Parameter<OcOrderZeroBankbookFailureDetails> parameter)
			throws Exception {
		service.getSiteList(parameter);
		return forward("/order/account-transfer-fail-list");
	}

	/**
	 * @Desc : 무통장입금 실패 목록 검색
	 * @Method Name : searchFailList
	 * @Date : 2019. 2. 11.
	 * @Author : ljyoung@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/account-transfer-fail-list/search")
	public void searchAccountTransferFailList(Parameter<OcOrderZeroBankbookFailureDetails> parameter) throws Exception {
		Pageable<OcOrderZeroBankbookFailureDetails, OcOrderZeroBankbookFailureDetails> pageable = new Pageable<>(
				parameter);
		Page<OcOrderZeroBankbookFailureDetails> page = service.selectAccountTransferFailList(pageable);

		UtilsResponse.writeJson(parameter.getResponse(), page.getGrid());
	}

	/**
	 * @Desc : 무통장입금 실패 건 환불계좌 등록 팝업 호출
	 * @Method Name : refundInfo
	 * @Date : 2019. 3. 28.
	 * @Author : ljyoung@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/account-transfer-fail-list/popRefundInfo")
	public ModelAndView refundInfo(Parameter<OcOrderZeroBankbookFailureDetails> parameter) throws Exception {
		String[] codeFields = { CommonCode.BANK_CODE };
		parameter.addAttribute("codeList", service.getCodeListByGroup(codeFields));
		/*
		 * TODO : 회원정보에서 환불 정보를 들고와서 처리. 입력된 환불 정보가 없는 경우 회원정보의 환불 정보를 표시.
		 * parameter.addAttribute("memberRefundInfo", );
		 */

		service.readOcOrderZeroBankbookFailureDetails(parameter);

		return forward("/order/refund-account-pop");
	}

	/**
	 * @Desc : 무통장입금 오류 건 환불 계좌 등록.
	 * @Method Name : registRefundAccount
	 * @Date : 2019. 3. 28.
	 * @Author : ljyoung@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/account-transfer-fail-list/registRefundInfo")
	public void registRefundAccount(Parameter<OcOrderZeroBankbookFailureDetails> parameter) throws Exception {
		service.registOcOrderZeroBankbookFailureDetails(parameter.get());
	}

	/**
	 * @Desc : 무통장입금 오류 건 환불 처리 완료.
	 * @Method Name : completeRefundProc
	 * @Date : 2019. 3. 28.
	 * @Author : ljyoung@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/account-transfer-fail-list/refundComplete")
	public void completeRefundProc(Parameter<OcOrderZeroBankbookFailureDetails[]> parameter) throws Exception {
		OcOrderZeroBankbookFailureDetails[] params = parameter.get();
		for (OcOrderZeroBankbookFailureDetails ocOrderZeroBankbookFailureDetails : params) {
			service.completeOcOrderZeroBankbookFailureDetails(ocOrderZeroBankbookFailureDetails);
		}
	}

	/**
	 * @Desc : 네이버페이 결제내역
	 * @Method Name : history
	 * @Date : 2019. 2. 13.
	 * @Author : ljyoung@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/naverpay/history")
	public ModelAndView naverpayHistory2(Parameter<?> parameter) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		NaverPaymentListReturn result;
		result = service.naverpaymentList(parameter.getString("siteNo"), parameter.getString("paymentId"), null, null);
		parameter.addAttribute("orderId", parameter.getString("orderNo"));
		parameter.addAttribute("naverpayList", mapper.writeValueAsString(result));
		return forward("/order/naverpay-history-pop");
	}

	@RequestMapping("/naverpay/history2")
	@ResponseBody
	public ModelAndView naverpayHistory(Parameter<?> parameter) throws Exception {
		parameter.addAttribute("siteNo", parameter.getString("siteNo"));
		parameter.addAttribute("orderNo", parameter.getString("orderNo"));
		parameter.addAttribute("paymentId", parameter.getString("paymentId"));

		parameter.addAttribute("tid", parameter.getString("pymntOrganNoText")); // 카카오페이 tid로 변경하여 넘겨줌
		return forward("/order/naverpay-history-pop2");
	}

	@PostMapping("/naverpay/history-list")
	public void naverPayHistoryList(Parameter<?> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String siteNo = parameter.getString("siteNo");
		String paymentId = parameter.getString("paymentId");

		NaverPaymentListReturn naverPaymentListReturn = service.naverpaymentList(siteNo, paymentId, null, null);

		dataMap.put("data", naverPaymentListReturn.getBody().getList());

		writeJson(parameter, dataMap);
	}

	/**
	 * @Desc : 관리자 메모 화면 포워딩
	 * @Method Name : memoList
	 * @Date : 2019. 2. 14.
	 * @Author : ljyoung@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-memo-info-tab")
	public ModelAndView memoRead(Parameter<OcOrderMemo> parameter) throws Exception {
		parameter.addAttribute("orderNo", parameter.get().getOrderNo());
		return forward("/order/read-memo-info-tab");
	}

	/**
	 * @Desc : 관리자 메모 목록
	 * @Method Name : memoList
	 * @Date : 2019. 2. 14.
	 * @Author : ljyoung@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-memo-info-tab/list")
	public void memoList(Parameter<OcOrderMemo> parameter) throws Exception {
		Map<String, Object> memoList = new HashMap<String, Object>();
		if (parameter.getString("listType") != null && parameter.getString("listType").equals("option")) {
			memoList = service.memoSelectByOrderNo(parameter.get());
		} else {
			String[] codeFields = { CommonCode.CLM_GBN_CODE, CommonCode.ORDER_MEMO_GBN_CODE, CommonCode.AS_GBN_CODE };
			memoList = service.memoSelectAllByOrderNo(parameter.get());
			memoList.put("codeList", service.getCodeListByGroup(codeFields));
		}
		memoList.put("adminNo", LoginManager.getUserDetails().getAdminNo());
		UtilsResponse.writeJson(parameter.getResponse(), memoList);
	}

	/**
	 * @Desc : 관리자 메모 등록
	 * @Method Name : memoCreate
	 * @Date : 2019. 2. 14.
	 * @Author : ljyoung@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/regist-memo")
	public void memoRegist(Parameter<OcOrderMemo> parameter) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			OcOrderMemo ocOrderMemo = parameter.get();
			ocOrderMemo.setDelYn("N");
			ocOrderMemo.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
			service.memoCreate(ocOrderMemo);

			result.put("status", "success");
			UtilsResponse.writeJson(parameter.getResponse(), result);
		} catch (Exception e) {
			result.put("status", e.getStackTrace());
			UtilsResponse.writeJson(parameter.getResponse(), result);
		}
	}

	/**
	 * @Desc :관리자 메모 삭제
	 * @Method Name : memoDelete
	 * @Date : 2019. 2. 14.
	 * @Author : ljyoung@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/delete-memo")
	public void memoDelete(Parameter<OcOrderMemo> parameter) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			service.memoDelete(parameter.get());
			result.put("status", "success");
			UtilsResponse.writeJson(parameter.getResponse(), result);
		} catch (Exception e) {
			result.put("status", e.getStackTrace());
			UtilsResponse.writeJson(parameter.getResponse(), result);
		}
	}

	/**
	 * 
	 * @Desc : 상품 옵션 변경
	 * @Method Name : optionChange
	 * @Date : 2019. 2. 27.
	 * @Author : ljyoung@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/option-change")
	public ModelAndView optionChange(Parameter<OcOrderProduct> parameter) throws Exception {
		parameter.addAttribute("orderNo", parameter.get().getOrderNo());
		parameter.addAttribute("orderPrdtSeq", parameter.get().getOrderPrdtSeq());

		log.debug("parameter  option-change :" + parameter.get());

		service.getOrderPrdtDetailInfo(parameter);

		return forward("/order/option-change-pop");
	}

	/**
	 * 
	 * @Desc : 추가 옵션 정보 조회
	 * @Method Name : getPrdtOptionAdd
	 * @Date : 2019. 7. 17.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/order-option-add")
	public void getPrdtOptionAdd(Parameter<OcOrderProduct> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		log.debug("parameter  order-option-add :" + parameter.get());
		resultMap = service.getPrdtOptionAdd(parameter);

		writeJson(parameter, resultMap);
	}

	/**
	 * 
	 * @Desc : 옵션 저장
	 * @Method Name : getPrdtOptionAdd
	 * @Date : 2019. 7. 17.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/option-change-save")
	public void setPrdtOptionSave(Parameter<OcOrderProduct> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		OcOrderProduct ocOrderProduct = parameter.get();
		resultMap = service.setPrdtOptionSave(ocOrderProduct);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 주문/배송 이력 팝업
	 * @Method Name : history
	 * @Date : 2019. 2. 27.
	 * @Author : ljyoung@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delivery-history")
	public ModelAndView deliveryHistory(Parameter<OcOrderProduct> parameter) throws Exception {
		parameter.addAttribute("orderNo", parameter.get().getOrderNo());
		parameter.addAttribute("orderPrdtSeq", parameter.get().getOrderPrdtSeq());

		parameter.addAttribute("productInfo", service.selectProduct(parameter.get()));
		parameter.addAttribute("ocOrder", service.getOrderMasterInfo(parameter.get().getOrderNo()));

		return forward("/order/delivery-history-pop");
	}

	/**
	 * @Desc : 주문/배송 이력 조회
	 * @Method Name : deliveryHistoryList
	 * @Date : 2019. 2. 28.
	 * @Author : ljyoung@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/delivery-history/list")
	public void deliveryHistoryList(Parameter<OcOrderProductHistory> parameter) throws Exception {
		Map<String, Object> list = new HashMap<String, Object>();
		list.put("Data", service.selectOrderProductHistory(parameter.get()));

		UtilsResponse.writeJson(parameter.getResponse(), list);
	}

	/**
	 * @Desc : 포인트 사후적립 주문
	 * @Method Name : exposePointList
	 * @Date : 2019. 2. 27.
	 * @Author : ljyoung@3top.co.kr
	 * @param paramter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/expost-point-list")
	public ModelAndView expostPointList(Parameter<OcOrderMemberExpostSaveVO> parameter) throws Exception {
		service.getSiteList(parameter);
		return forward("/order/expost-point-list");
	}

	/**
	 * @Desc : 포인트 사후적립 주문
	 * @Method Name : exposePointList
	 * @Date : 2019. 2. 27.
	 * @Author : ljyoung@3top.co.kr
	 * @param paramter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/expost-point-list/search")
	public void expostPointListSearch(Parameter<OcOrderMemberExpostSaveVO> parameter) throws Exception {
		Pageable<OcOrderMemberExpostSaveVO, OcOrderMemberExpostSaveVO> pageable = new Pageable<>(parameter);
		Page<OcOrderMemberExpostSaveVO> page = service.selectMemberExpostSavePointList(pageable);
		UtilsResponse.writeJson(parameter.getResponse(), page.getGrid());
	}

	/*************************************************************************************************
	 * ljyoung end
	 *************************************************************************************************/

}