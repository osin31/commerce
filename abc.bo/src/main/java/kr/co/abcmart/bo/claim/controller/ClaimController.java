package kr.co.abcmart.bo.claim.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.afterService.model.master.OcAsAccept;
import kr.co.abcmart.bo.claim.model.master.IfBankda;
import kr.co.abcmart.bo.claim.model.master.OcClaim;
import kr.co.abcmart.bo.claim.model.master.OcClaimCertificationHistory;
import kr.co.abcmart.bo.claim.model.master.OcClaimMemo;
import kr.co.abcmart.bo.claim.model.master.OcClaimPayment;
import kr.co.abcmart.bo.claim.model.master.OcClaimProduct;
import kr.co.abcmart.bo.claim.model.master.OcClaimStatusHistory;
import kr.co.abcmart.bo.claim.service.ClaimService;
import kr.co.abcmart.bo.claim.vo.OcClaimDiscountVO;
import kr.co.abcmart.bo.claim.vo.OcClaimPaymentExcelVo;
import kr.co.abcmart.bo.claim.vo.OcClaimProductExcelVo;
import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.bo.order.model.master.OcOrderDeliveryHistory;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsResponse;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.interfaces.module.payment.kcp.model.KcpPaymentApproval;
import kr.co.abcmart.interfaces.util.UtilsText;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc :
 * @FileName : ClaimController.java
 * @Project : abc.bo
 * @Date : 2019. 2. 20.
 * @Author : 3top
 */
@Slf4j
@Controller
@RequestMapping("claim")
public class ClaimController extends BaseController {

	@Autowired
	private ClaimService claimService;

	@Autowired
	private CommonCodeService commonCodeService;

	/**************************** S : 이강수 *****************************/

	/**
	 * @Desc : 클레임 검색/목록 화면 호출
	 * @Method Name : claimMain
	 * @Date : 2019. 2. 13.
	 * @Author : lks@3top.co.kr
	 * @param Parameter<?>
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/claim")
	public ModelAndView claimMain(Parameter<?> parameter) throws Exception {

		if (parameter.getString("clmGbnCode") != null) {
			parameter.addAttribute("getClmGbnCode", parameter.getString("clmGbnCode"));
		}
		if (parameter.getString("mmnyPrdtYn") != null) {
			parameter.addAttribute("getMmnyPrdtYn", parameter.getString("mmnyPrdtYn"));
		}

		Map<String, Object> map = claimService.getSiteCommonCodeData();

		parameter.addAttribute("SITE_TYPE", map.get("SITE_TYPE"));
		parameter.addAttribute(CommonCode.CLM_STAT_CODE, map.get(CommonCode.CLM_STAT_CODE));
		parameter.addAttribute(CommonCode.CLM_RSN_CODE, map.get(CommonCode.CLM_RSN_CODE));
		parameter.addAttribute(CommonCode.CLM_GBN_CODE, map.get(CommonCode.CLM_GBN_CODE));
		parameter.addAttribute(CommonCode.DEVICE_CODE, map.get(CommonCode.DEVICE_CODE));
		parameter.addAttribute(CommonCode.STOCK_GBN_CODE, map.get(CommonCode.STOCK_GBN_CODE));
		parameter.addAttribute(CommonCode.PYMNT_MEANS_CODE, map.get(CommonCode.PYMNT_MEANS_CODE));
		parameter.addAttribute(CommonCode.PYMNT_STAT_CODE, map.get(CommonCode.PYMNT_STAT_CODE));

		return forward("/claim/claim-main");
	}

	/**
	 * @Desc : 클레임 목록 조회
	 * @Method Name : readClaimList
	 * @Date : 2019. 2. 13.
	 * @Author : lks@3top.co.kr
	 * @param Parameter<OcClaim>
	 * @throws Exception
	 */
	@PostMapping("/claim/read-claim-list")
	public void readClaimList(Parameter<OcClaim> parameter) throws Exception {
		Pageable<OcClaim, OcClaim> claimVOPageble = new Pageable<>(parameter);

		Page<OcClaim> page = claimService.getClaimList(claimVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 클레임 엑셀파일 모두 다운로드
	 * @Method Name : downloadClaimAllExcel
	 * @Date : 2019. 3. 6.
	 * @Author : 이강수
	 * @param Parameter<OcClaimProduct>
	 * @throws Exception
	 */
	@PostMapping("/claim/download-claim-all-excel")
	public void downloadClaimAllExcel(Parameter<OcClaimProduct> parameter) throws Exception {

		List<OcClaimProductExcelVo> list = claimService.getOcClaimProductForAllExcelList(parameter.get());

		ExcelValue excelValue = ExcelValue.builder(1)
				.columnNames(Arrays.asList("siteName", "clmGbnCodeName", "rgstDtm", "clmPrdtStatCodeName", "clmNo",
						"clmRsnCodeName", "orgOrderNo", "dlvyIdText", "prdtNo", "brandName", "prdtName", "styleInfo",
						"optnName", "vndrPrdtNoText", "vndrName", "vndrNo", "stockGbnCodeName", "addDlvyAmtPymntText",
						"dlvyPymntAmt", "redempAmt", "refundAmt", "unProcYnText", "pymntAmt", "modDtm"))
				.data(list).build();

		parameter.downloadExcelTemplate("claim/excel/claimList", excelValue);
	}

	/**
	 * @Desc : 클레임 엑셀파일 다운로드
	 * @Method Name : downloadClaimExcel
	 * @Date : 2019. 3. 6.
	 * @Author : 이강수
	 * @param Parameter<OcClaimProduct>
	 * @throws Exception
	 */
	@PostMapping("/claim/download-claim-excel")
	public void downloadClaimExcel(Parameter<OcClaimProductExcelVo> parameter) throws Exception {
		List<OcClaimProductExcelVo> list = claimService.getOcClaimProductForExcelList(parameter.get());

		ExcelValue excelValue = ExcelValue.builder(1)
				.columnNames(Arrays.asList("siteName", "clmGbnCodeName", "rgstDtm", "clmPrdtStatCodeName", "clmNo",
						"clmRsnCodeName", "orgOrderNo", "dlvyIdText", "prdtNo", "brandName", "prdtName", "styleInfo",
						"optnName", "vndrPrdtNoText", "vndrName", "vndrNo", "stockGbnCodeName", "addDlvyAmtPymntText",
						"dlvyPymntAmt", "redempAmt", "refundAmt", "unProcYnText", "pymntAmt", "modDtm"))
				.data(list).build();

		parameter.downloadExcelTemplate("claim/excel/claimList", excelValue);
	}

	/**
	 * @Desc : 환불 금액 관리 검색/목록 화면 호출
	 * @Method Name : refundMain
	 * @Date : 2019. 2. 14.
	 * @Author : lks@3top.co.kr
	 * @param Parameter<?> parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/refund")
	public ModelAndView refundMain(Parameter<?> parameter) throws Exception {
		Map<String, Object> map = claimService.getSiteCommonCodeData();

		parameter.addAttribute("SITE_TYPE", map.get("SITE_TYPE"));
		parameter.addAttribute(CommonCode.CLM_STAT_CODE, map.get(CommonCode.CLM_STAT_CODE));
		parameter.addAttribute(CommonCode.CLM_GBN_CODE, map.get(CommonCode.CLM_GBN_CODE));
		parameter.addAttribute(CommonCode.DEVICE_CODE, map.get(CommonCode.DEVICE_CODE));
		parameter.addAttribute(CommonCode.PYMNT_STAT_CODE, map.get(CommonCode.PYMNT_STAT_CODE));

		return forward("/claim/refund-main");
	}

	/**
	 * @Desc : 환불 금액 목록 조회
	 * @Method Name : readRefundList
	 * @Date : 2019. 2. 20.
	 * @Author : lks@3top.co.kr
	 * @param Parameter<OcClaimPayment>
	 * @throws Exception
	 */
	@PostMapping("/refund/read-refund-list")
	public void readRefundList(Parameter<OcClaimPayment> parameter) throws Exception {
		Pageable<OcClaimPayment, OcClaimPayment> claimVOPageble = new Pageable<>(parameter);

		Page<OcClaimPayment> page = claimService.getRefundRedempList(claimVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 환수 금액 관리 검색/목록 화면 호출
	 * @Method Name : redemptionMain
	 * @Date : 2019. 2. 14.
	 * @Author : lks@3top.co.kr
	 * @param Parameter<?>
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/redemption")
	public ModelAndView redemptionMain(Parameter<?> parameter) throws Exception {
		Map<String, Object> map = claimService.getSiteCommonCodeData();

		parameter.addAttribute("SITE_TYPE", map.get("SITE_TYPE"));
		parameter.addAttribute(CommonCode.CLM_STAT_CODE, map.get(CommonCode.CLM_STAT_CODE));
		parameter.addAttribute(CommonCode.CLM_GBN_CODE, map.get(CommonCode.CLM_GBN_CODE));
		parameter.addAttribute(CommonCode.DEVICE_CODE, map.get(CommonCode.DEVICE_CODE));
		parameter.addAttribute(CommonCode.PYMNT_STAT_CODE, map.get(CommonCode.PYMNT_STAT_CODE));

		return forward("/claim/redemption-main");
	}

	/**
	 * @Desc : 환수 금액 목록 조회
	 * @Method Name : readRedemptionList
	 * @Date : 2019. 2. 20.
	 * @Author : lks@3top.co.kr
	 * @param Parameter<OcClaimPayment>
	 * @throws Exception
	 */
	@PostMapping("/redemption/read-redemption-list")
	public void readRedemptionList(Parameter<OcClaimPayment> parameter) throws Exception {
		Pageable<OcClaimPayment, OcClaimPayment> claimVOPageble = new Pageable<>(parameter);

		Page<OcClaimPayment> page = claimService.getRefundRedempList(claimVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 환불 처리상태 업데이트 - 처리완료
	 * @Method Name : updateProcessComplete
	 * @Date : 2019. 2. 25.
	 * @Author : lks@3top.co.kr
	 * @param Parameter<?>
	 * @throws Exception
	 */
	@PostMapping("/refund/update-process-complete")
	public void updateProcessComplete(Parameter<?> parameter) throws Exception {
		OcClaimPayment[] paramsArr = parameter.createArray(OcClaimPayment.class, "clmNo",
				Arrays.asList("modDtm", "redempRfndOpetrDtm"));

		claimService.updateProcessComplete(paramsArr);
	}

	/**
	 * @Desc : 환불 처리상태 업데이트 - 처리불가
	 * @Method Name : updateProcessImpossible
	 * @Date : 2019. 2. 25.
	 * @Author : lks@3top.co.kr
	 * @param Parameter<?>
	 * @throws Exception
	 */
	@PostMapping("/refund/update-process-impossible")
	public void updateProcessImpossible(Parameter<?> parameter) throws Exception {
		OcClaimPayment[] paramsArr = parameter.createArray(OcClaimPayment.class, "clmNo",
				Arrays.asList("modDtm", "redempRfndOpetrDtm"));

		claimService.updateProcessImpossible(paramsArr);
	}

	/**
	 * @Desc : 환불/환수 엑셀파일 모두 다운로드
	 * @Method Name : downloadRefundRedempAllExcel
	 * @Date : 2019. 3. 7.
	 * @Author : 이강수
	 * @param Parameter<OcClaimProduct>
	 * @throws Exception
	 */
	@PostMapping("/refund/download-refund-redemp-all-excel")
	public void downloadRefundRedempAllExcel(Parameter<OcClaimPayment> parameter) throws Exception {

		List<OcClaimPaymentExcelVo> list = claimService.getRefundRedempListForAllExcel(parameter.get());

		String[] refundStr = { "clmSiteName", "clmGbnCodeName", "ocrncRsnCodeName", "clmNo", "orgOrderNo",
				"orderMember", "pymntMeansCodeStuff", "strPymntAmt", "pymntStatCodeName", "procImpsbltYn",
				"procImpsbltRsnText", "bankCodeName", "clmAcnt", "acntHldrName", "rgstDtm", "clmHandler", "modDtm" };

		String[] redempStr = { "clmSiteName", "clmGbnCodeName", "ocrncRsnCodeName", "clmNo", "orgOrderNo",
				"orderMember", "strPymntAmt", "pymntStatCodeName", "redempAmtRndmProcYn", "bankCodeName", "clmAcnt",
				"acntHldrName", "rgstDtm", "clmHandler", "modDtm" };

		if (parameter.get().getRedempRfndGbnType().equals(CommonCode.REDEMP_RFND_GBN_TYPE_REFUND)) {

			ExcelValue excelValue = ExcelValue.builder(1).columnNames(Arrays.asList(refundStr)).data(list).build();

			parameter.downloadExcelTemplate("claim/excel/refundList", excelValue);
		} else {

			ExcelValue excelValue = ExcelValue.builder(1).columnNames(Arrays.asList(redempStr)).data(list).build();

			parameter.downloadExcelTemplate("claim/excel/redempList", excelValue);
		}
	}

	/**
	 * @Desc : 환불/환수 엑셀파일 선택 다운로드
	 * @Method Name : downloadRefundRedempExcel
	 * @Date : 2019. 3. 7.
	 * @Author : 이강수
	 * @param Parameter<OcClaimProduct>
	 * @throws Exception
	 */
	@PostMapping("/refund/download-refund-redemp-excel")
	public void downloadRefundRedempExcel(Parameter<OcClaimPaymentExcelVo> parameter) throws Exception {
		List<OcClaimPaymentExcelVo> list = claimService.getRefundRedempListForExcel(parameter.get());

		String[] refundStr = { "clmSiteName", "clmGbnCodeName", "ocrncRsnCodeName", "clmNo", "orgOrderNo",
				"orderMember", "pymntMeansCodeStuff", "strPymntAmt", "pymntStatCodeName", "procImpsbltYn",
				"procImpsbltRsnText", "bankCodeName", "clmAcnt", "acntHldrName", "rgstDtm", "clmHandler", "modDtm" };

		String[] redempStr = { "clmSiteName", "clmGbnCodeName", "ocrncRsnCodeName", "clmNo", "orgOrderNo",
				"orderMember", "strPymntAmt", "pymntStatCodeName", "redempAmtRndmProcYn", "bankCodeName", "clmAcnt",
				"acntHldrName", "rgstDtm", "clmHandler", "modDtm" };

		if (parameter.get().getRedempRfndGbnType().equals(CommonCode.REDEMP_RFND_GBN_TYPE_REFUND)) {

			ExcelValue excelValue = ExcelValue.builder(1).columnNames(Arrays.asList(refundStr)).data(list).build();

			parameter.downloadExcelTemplate("claim/excel/refundList", excelValue);
		} else {

			ExcelValue excelValue = ExcelValue.builder(1).columnNames(Arrays.asList(redempStr)).data(list).build();

			parameter.downloadExcelTemplate("claim/excel/redempList", excelValue);
		}
	}

	/**
	 * @Desc : 클레임불가 처리 팝업
	 * @Method Name : registClaimImpossibilityFormPop
	 * @Date : 2019. 3. 12.
	 * @Author : KTH
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/claim/regist-claim-impossibility-form-pop")
	public ModelAndView registClaimImpossibilityFormPop(Parameter<OcClaimProduct> parameter) throws Exception {
		Map<String, Object> map = claimService.getClaimImpossibilityDetail(parameter.get());

		parameter.addAttribute(CommonCode.CLM_IMPSBLT_RSN_CODE, map.get(CommonCode.CLM_IMPSBLT_RSN_CODE)); // 레임불가사유코드
		parameter.addAttribute(CommonCode.LOGIS_VNDR_CODE, map.get(CommonCode.LOGIS_VNDR_CODE)); // 택배사코드
		parameter.addAttribute("ocOrder", map.get("ocOrder"));
		parameter.addAttribute("ocClaimProduct", map.get("ocClaimProduct"));

		return forward("/claim/regist-claim-impossibility-form-pop");
	}

	/**
	 * @Desc : 클레임불가 처리
	 * @Method Name : registClaimImpossibility
	 * @Date : 2019. 3. 13.
	 * @Author : 이강수
	 * @param Parameter<?>
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/claim/regist-claim-impossibility")
	public void registClaimImpossibility(Parameter<?> parameter) throws Exception {
		// 클레임 불가 사유 코드, 요청 내용, 택배사 코드, 운송장 정보
		OcClaimProduct ocClaimProduct = parameter.create(OcClaimProduct.class);
		// 발송예정일, 수취인명, 휴대폰번호, 우편번호, 우편주소, 상세주소, 배송 시 요청사항
		OcOrderDeliveryHistory ocOrderDeliveryHistory = parameter.create(OcOrderDeliveryHistory.class);

		claimService.setClaimImpossibility(ocClaimProduct, ocOrderDeliveryHistory);
	}

	/**
	 * @Desc : 클레임불가 상세 팝업
	 * @Method Name : readClaimImpossibilityDetailPop
	 * @Date : 2019. 3. 13.
	 * @Author : KTH
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/claim/read-claim-impossibility-detail-pop")
	public ModelAndView readClaimImpossibilityDetailPop(Parameter<OcClaimProduct> parameter) throws Exception {
		Map<String, Object> resultMap = claimService.getClaimImpossibilityDetail(parameter.get());

		parameter.addAttribute("ocOrderDeliveryHistory", resultMap.get("ocOrderDeliveryHistory"));
		parameter.addAttribute("ocClaimProduct", resultMap.get("ocClaimProduct"));

		return forward("/claim/read-claim-impossibility-detail-pop");
	}

	/**
	 * @Desc : 클레임 이력 팝업창 호출
	 * @Method Name : claimHistoryPop
	 * @Date : 2019. 3. 18.
	 * @Author : 이강수
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/claim/claim-history-pop")
	public ModelAndView claimHistoryPop(Parameter<OcClaimProduct> parameter) throws Exception {
		parameter.addAttribute("ocClaimProduct", claimService.getClaimHistoryDetail(parameter.get()));
		return forward("/claim/claim-history-pop");
	}

	/**
	 * @Desc : 클레임 이력 목록 조회
	 * @Method Name : readClaimHistoryList
	 * @Date : 2019. 3. 18.
	 * @Author : 이강수
	 * @param Parameter<OcClaimStatusHistory>
	 * @throws Exception
	 */
	@PostMapping("/claim/read-claim-histroy-list")
	public void readClaimHistoryList(Parameter<OcClaimStatusHistory> parameter) throws Exception {
		Pageable<OcClaimStatusHistory, OcClaimStatusHistory> pageble = new Pageable<>(parameter);

		Page<OcClaimStatusHistory> page = claimService.getClaimHistoryList(pageble);

		writeJson(parameter, page.getGrid());
	}

	/**************************** E : 이강수 *****************************/

	/**************************** S : 김태호 *****************************/

	/**
	 * @Desc : 클레임정보 화면 - 주문상세 팝업 내부 탭
	 * @Method Name : readClaimInfoTab
	 * @Date : 2019. 2. 13.
	 * @Author : KTH, 이강수
	 * @param Parameter<SyAdmin>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@PostMapping("/claim/read-claim-info-tab")
	public ModelAndView readClaimInfoTab(Parameter<OcClaim> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap = claimService.getRefundInfoInOrderTab(parameter.get());

		parameter.addAttribute("totalCancelAmt", dataMap.get("totalCancelAmt"));
		parameter.addAttribute("totalAddAmtList", dataMap.get("totalAddAmtList"));
		parameter.addAttribute("totalAddAmtSum", dataMap.get("totalAddAmtSum"));
		parameter.addAttribute("totalRefundAmtList", dataMap.get("totalRefundAmtList"));
		parameter.addAttribute("totalRefundAmtSum", dataMap.get("totalRefundAmtSum"));
		parameter.addAttribute("refundPreviousRedempDlvyList", dataMap.get("refundPreviousRedempDlvyList"));
		parameter.addAttribute("refundPreviousRedempDlvySumAmt", dataMap.get("refundPreviousRedempDlvySumAmt"));
		parameter.addAttribute("addMultiBuyDifferList", dataMap.get("addMultiBuyDifferList"));
		parameter.addAttribute("addMultiBuyDifferSumAmt", dataMap.get("addMultiBuyDifferSumAmt"));

		return forward("/order/read-claim-info-tab");
	}

	/**
	 * @Desc : 할인취소 정보 목록 조회 - 주문상세 팝업 클레임 탭
	 * @Method Name : readDiscountCancelListIntab
	 * @Date : 2019. 2. 18.
	 * @Author : KTH, 이강수
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/read-discount-cancel-list-intab")
	public void readDiscountCancelListIntab(Parameter<OcClaimDiscountVO> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("data", claimService.getClaimCancelDiscountList(parameter.get()));

		writeJson(parameter, dataMap);
	}

	/**
	 * @Desc : 환불/환수 정보 목록 조회 - 주문상세 팝업 클레임 탭
	 * @Method Name : readRedemptionRefundListIntab
	 * @Date : 2019. 2. 18.
	 * @Author : KTH, 이강수
	 * @param Parameter<OcClaimPayment>
	 * @throws Exception
	 */
	@PostMapping("/claim/read-redemption-refund-list-intab")
	public void readRedemptionRefundListIntab(Parameter<OcClaimPayment> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("data", claimService.getClaimPaymentListIntab(parameter.get()));

		writeJson(parameter, dataMap);
	}

	/**
	 * @Desc : 클레임내역 목록(교환|반품|취소 상품 대상) 조회 - 주문상세 팝업 클레임 탭
	 * @Method Name : readClaimListIntab
	 * @Date : 2019. 3. 4.
	 * @Author : KTH, 이강수
	 * @param Parameter<OcClaim>
	 * @throws Exception
	 */
	@PostMapping("/claim/read-claim-list-intab")
	public void readClaimListIntab(Parameter<OcClaim> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("data", claimService.getClaimListIntab(parameter.get()));

		writeJson(parameter, dataMap);
	}

	/**
	 * @Desc : A/S내역 목록 조회 - 주문상세 팝업 클레임 탭
	 * @Method Name : readAfterserviceListIntab
	 * @Date : 2019. 3. 4.
	 * @Author : KTH, 이강수
	 * @param Parameter<OcAsAccept>
	 * @throws Exception
	 */
	@PostMapping("/claim/read-afterservice-list-intab")
	public void readAfterserviceListIntab(Parameter<OcAsAccept> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();

		dataMap.put("data", claimService.getAsAcceptListInOrderDetailTap(parameter.get()));

		writeJson(parameter, dataMap);
	}

	/**
	 * @Desc : 결제 페이지 sample
	 * @Method Name : orderSample
	 * @Date : 2019. 3. 21.
	 * @Author : KTH
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/claim/order-sample")
	public ModelAndView orderSample(Parameter<?> parameter) throws Exception {
		return forward("/sample/payment/order");
	}

	/**
	 * @Desc : 클레임 취소 등록 화면
	 * @Method Name : createClaimCancelFormPop
	 * @Date : 2019. 2. 28.
	 * @Author : KTH
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/claim/create-claim-cancel-form-pop")
	public ModelAndView createClaimCancelFormPop(Parameter<OcClaimProduct> parameter) throws Exception {
		// 클레임 취소 접수 대상 주문상품 정보
		Map<String, Object> map = claimService.getClaimCancelOrderProductInfo(parameter.get());

		parameter.addAttribute(CommonCode.CLM_RSN_CODE, map.get(CommonCode.CLM_RSN_CODE)); // 클레임사유코드
		parameter.addAttribute("orderInfo", map.get("ORDER_INFO")); // 주문기본정보
		parameter.addAttribute("orderProductList", map.get("ORDER_PRODUCT_LIST")); // 주문상품정보
		parameter.addAttribute("orderPaymentList", map.get("ORDER_PAYMENT_LIST")); // 주문결제정보
		parameter.addAttribute("adminNo", LoginManager.getUserDetails().getAdminNo());
		parameter.addAttribute("adminLoginId", LoginManager.getUserDetails().getLoginId());
		parameter.addAttribute("adminLoginName", LoginManager.getUserDetails().getUsername());
		parameter.addAttribute("sySite", map.get("sySite"));
		parameter.addAttribute("onlyOrderProductCnt", map.get("onlyOrderProductCnt"));
		
		return forward("/claim/create-claim-cancel-form-pop");
	}

	/**
	 * @Desc : 클레임 취소 등록 validate
	 * @Method Name : validateClaimCanceleProduct
	 * @Date : 2019. 3. 21.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/validate-claim-cancel-product")
	public void validateClaimCanceleProduct(Parameter<?> parameter) throws Exception {
		// none
	}

	/**
	 * @Desc : 클레임 취소 등록
	 * @Method Name : registClaimExchange
	 * @Date : 2019. 3. 8.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/regist-claim-cancel")
	public void registClaimCancel(Parameter<?> parameter) throws Exception {
		OcClaim ocClaim = parameter.create(OcClaim.class);
		OcClaimProduct[] ocClaimProducts = parameter.createArray(OcClaimProduct.class, "orderPrdtSeq");
		OcClaimMemo ocClaimMemo = parameter.create(OcClaimMemo.class);

		claimService.registClaimCancel(ocClaim, ocClaimProducts, ocClaimMemo);
		writeJson(parameter, ocClaim);
	}

	/**
	 * @Desc : 클레임 취소 상세 화면
	 * @Method Name : readClaimCancelDetailPop
	 * @Date : 2019. 2. 20.
	 * @Author : KTH
	 * @param Parameter<OcClaim>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@GetMapping("/claim/read-claim-cancel-detail-pop")
	public ModelAndView readClaimCancelDetailPop(Parameter<OcClaim> parameter) throws Exception {
		Map<String, Object> map = claimService.getClaimCancelBasisInfo(parameter.get());

		parameter.addAttribute(CommonCode.CLM_RSN_CODE, map.get(CommonCode.CLM_RSN_CODE)); // 클레임사유코드
		parameter.addAttribute(CommonCode.CLM_WTHDRAW_RSN_CODE, map.get(CommonCode.CLM_WTHDRAW_RSN_CODE)); // 클레임철회사유코드
		parameter.addAttribute(CommonCode.BANK_CODE, map.get(CommonCode.BANK_CODE)); // 은행코드
		parameter.addAttribute("orderInfo", map.get("ORDER_INFO")); // 주문기본정보
		parameter.addAttribute("claimInfo", map.get("CLAIM_INFO")); // 클레임기본정보
		parameter.addAttribute("orderPaymentList", map.get("ORDER_PAYMENT_LIST")); // 주문결제정보
		parameter.addAttribute("thisTiemClaimPaymentList", map.get("THIS_TIME_CLAIM_PAYMENT_LIST")); // 현재 클레임결제 정보 목록
		parameter.addAttribute("redempClaimPaymentList", map.get("REDEMP_CLAIM_PAYMENT_LIST")); // 환수결제 목록
		parameter.addAttribute("refundClaimPaymentList", map.get("REFUND_CLAIM_PAYMENT_LIST")); // 환불금 이력 목록
		parameter.addAttribute("refundClaimPaymentPublicList", map.get("REFUND_CLAIM_PAYMENT_PUBLIC_LIST")); // 환불금실결제목록(실제환불대상실결제)
		parameter.addAttribute("refundPreviousRedempDlvyList", map.get("REFUND_PREVIOUS_REDEMP_DLVY_LIST")); // 기존환수배송비환불발생내역
		parameter.addAttribute("refundPreviousRedempDlvySumAmt", map.get("REFUND_PREVIOUS_REDEMP_DLVY_SUM_AMT")); // 기존환수배송비환불발생내역sum
		parameter.addAttribute("addMultiBuyDifferSumAmt", map.get("ADD_MULTI_BUY_DIFFER_SUM_AMT")); // 다족구매프로모션추가환불대상환불발생내역sum
		parameter.addAttribute("mmnyRedempPaymentList", map.get("MMNY_REDEMP_PAYMENT_LIST")); // 재경팀환수처리대상목록
		parameter.addAttribute("mmnyRefundPaymentList", map.get("MMNY_REFUNND_PAYMENT_LIST")); // 재경팀환불처리대상목록
		parameter.addAttribute("calcPaymentList", map.get("CALC_PAYMENT_LIST")); // 계산용 클레임결제 정보 목록
		parameter.addAttribute("claimProductList", map.get("CLAIM_PRODUCT_LIST")); // 클레임 상품 목록
		parameter.addAttribute("claimSumAmt", map.get("CLAIM_SUM_AMT")); // // 클레임총금액
		parameter.addAttribute("claimProductSumAmt", map.get("CLAIM_PRODUCT_SUM_AMT")); // 클레임상품총금액
		parameter.addAttribute("claimDlvySumAmt", map.get("CLAIM_DLVY_SUM_AMT")); // 클레임배송비총금액
		parameter.addAttribute("thisClaimVariationSavePoint", map.get("THIS_CLAIM_VARIATION_SAVE_POINT")); // 클레임으로변경되는구매적립증감포인트

		return forward("/claim/read-claim-cancel-detail-pop");
	}

	/**
	 * @Desc : 클레임 취소 상품 저장(업데이트)
	 * @Method Name : saveClaimCancelProduct
	 * @Date : 2019. 3. 5.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/save-claim-cancel-product")
	public void saveClaimCancelProduct(Parameter<?> parameter) throws Exception {
		OcClaim ocClaim = parameter.create(OcClaim.class);

		claimService.updateClaimCancelProduct(parameter.createArray(OcClaimProduct.class, "clmPrdtSeq"));
	}

	/**
	 * @Desc : 클레임 교환 등록 화면
	 * @Method Name : createClaimExchangeFormPop
	 * @Date : 2019. 2. 28.
	 * @Author : KTH
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/claim/create-claim-exchange-form-pop")
	public ModelAndView createClaimExchangeFormPop(Parameter<OcClaimProduct> parameter) throws Exception {
		OcClaimProduct ocClaimProduct = parameter.get();
		ocClaimProduct.setClmGbnCode(CommonCode.CLM_GBN_CODE_EXCHANGE);

		// 클레임 교환 접수 대상 주문상품 정보
		Map<String, Object> map = claimService.getClaimOrderProductInfo(ocClaimProduct,
				CommonCode.CPN_TYPE_CODE_FREE_CHANGE);

		parameter.addAttribute(CommonCode.CLM_RSN_CODE, map.get(CommonCode.CLM_RSN_CODE)); // 클레임사유코드
		parameter.addAttribute("orderInfo", map.get("ORDER_INFO")); // 주문기본정보
		parameter.addAttribute("orderProductList", map.get("ORDER_PRODUCT_LIST")); // 주문상품정보
		parameter.addAttribute("orderPaymentList", map.get("ORDER_PAYMENT_LIST")); // 주문결제정보
		parameter.addAttribute("usableCouponList", map.get("USABLE_COUPON_LIST")); // 회원 무료사용 가능 보유쿠폰
		parameter.addAttribute("claimDeliveryAmt", map.get("CLAIM_DELIVERY_AMT")); // 클레임 배송비
		parameter.addAttribute("adminNo", LoginManager.getUserDetails().getAdminNo());
		parameter.addAttribute("adminLoginId", LoginManager.getUserDetails().getLoginId());
		parameter.addAttribute("adminLoginName", LoginManager.getUserDetails().getUsername());

		parameter.addAttribute("kcpJsUrl", Config.getString("pg.kcp.js.url")); // kcp js모듈 호출 경로
		parameter.addAttribute("siteCd", map.get("pgSiteCd")); // kcp siteCode(클레임 배송비 결제용)
		parameter.addAttribute("siteName", map.get("pgSiteName")); // kcp siteName
		parameter.addAttribute("moduleType", Config.getString("pg.kcp.module.type")); // kcp moduleType
		parameter.addAttribute("kcpPaymentApproval", map.get("KCP_PAYMENT_APPROVAL")); // kcp 결제요청 정보

		return forward("/claim/create-claim-exchange-form-pop");
	}

	/**
	 * @Desc : 클레임 교환 등록 validate
	 * @Method Name : validateClaimExchangeProduct
	 * @Date : 2019. 3. 21.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/validate-claim-exchange-product")
	public void validateClaimExchangeProduct(Parameter<?> parameter) throws Exception {
		// none
	}

	/**
	 * @Desc : 클레임 교환 등록
	 * @Method Name : registClaimExchange
	 * @Date : 2019. 3. 8.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/regist-claim-exchange")
	public void registClaimExchange(Parameter<?> parameter) throws Exception {
		OcClaim ocClaim = parameter.create(OcClaim.class);
		OcClaimProduct[] ocClaimProducts = parameter.createArray(OcClaimProduct.class, "orderPrdtSeq");
		OcClaimMemo ocClaimMemo = parameter.create(OcClaimMemo.class);
		KcpPaymentApproval kcpPaymentApproval = parameter.create(KcpPaymentApproval.class);

		ocClaim.setClmGbnCode(CommonCode.CLM_GBN_CODE_EXCHANGE);
		kcpPaymentApproval.setCustIp(parameter.getRequest().getRemoteAddr()); // 요청 IP set

		claimService.registClaimExchange(ocClaim, ocClaimProducts, ocClaimMemo, kcpPaymentApproval);
		writeJson(parameter, ocClaim);
	}

	/**
	 * @Desc : 클레임 교환 상세 화면
	 * @Method Name : readClaimExchangeDetailPop
	 * @Date : 2019. 2. 20.
	 * @Author : KTH
	 * @param Parameter<OcClaim>
	 * @return ModelAndView
	 * @throws Exception
	 */
	@GetMapping("/claim/read-claim-exchange-detail-pop")
	public ModelAndView readClaimExchangeDetailPop(Parameter<OcClaim> parameter) throws Exception {
		OcClaim ocClaim = parameter.get();
		ocClaim.setClmGbnCode(CommonCode.CLM_GBN_CODE_EXCHANGE);

		Map<String, Object> map = claimService.getClaimExchangeBasisInfo(parameter.get(),
				CommonCode.CPN_TYPE_CODE_FREE_CHANGE);

		parameter.addAttribute(CommonCode.CLM_RSN_CODE, map.get(CommonCode.CLM_RSN_CODE)); // 클레임사유코드
		parameter.addAttribute(CommonCode.CLM_PROC_TYPE_CODE, map.get(CommonCode.CLM_PROC_TYPE_CODE)); // 클레임처리유형코드
		parameter.addAttribute(CommonCode.LOGIS_VNDR_CODE, map.get(CommonCode.LOGIS_VNDR_CODE)); // 택배사코드
		parameter.addAttribute(CommonCode.CLM_WTHDRAW_RSN_CODE, map.get(CommonCode.CLM_WTHDRAW_RSN_CODE)); // 클레임철회사유코드
		parameter.addAttribute(CommonCode.BANK_CODE, map.get(CommonCode.BANK_CODE)); // 은행코드

		parameter.addAttribute("orderInfo", map.get("ORDER_INFO")); // 주문기본정보
		parameter.addAttribute("claimInfo", map.get("CLAIM_INFO")); // 클레임기본정보
		parameter.addAttribute("claimProductList", map.get("CLAIM_PRODUCT_LIST")); // 클레임상품정보
		parameter.addAttribute("orderPaymentList", map.get("ORDER_PAYMENT_LIST")); // 주문결제정보
		parameter.addAttribute("claimPaymentList", map.get("CLAIM_PAYMENT_LIST")); // 클레임결제정보 목록
		parameter.addAttribute("addDeliveryPaymentInfo", map.get("ADD_DELIVERY_PAYMENT_INFO")); // 교환배송비 결제정보
		parameter.addAttribute("publicAddDlvyPymntInfo", map.get("publicAddDlvyPymntInfo")); // 2020.03.18 추가
		parameter.addAttribute("usableCouponList", map.get("USABLE_COUPON_LIST")); // 회원 무료사용 가능 보유쿠폰
		parameter.addAttribute("claimDeliveryAmt", map.get("CLAIM_DELIVERY_AMT")); // 클레임 배송비

		parameter.addAttribute("kcpJsUrl", Config.getString("pg.kcp.js.url")); // kcp js모듈 호출 경로
		parameter.addAttribute("siteCd", map.get("pgSiteCd")); // kcp siteCode(클레임 배송비 결제용)
		parameter.addAttribute("siteName", map.get("pgSiteName")); // kcp siteName
		parameter.addAttribute("moduleType", Config.getString("pg.kcp.module.type")); // kcp moduleType
		parameter.addAttribute("kcpPaymentApproval", map.get("KCP_PAYMENT_APPROVAL")); // kcp 결제요청 정보

		return forward("/claim/read-claim-exchange-detail-pop");
	}

	/**
	 * @Desc : 클레임 교환 상품 저장(업데이트)
	 * @Method Name : saveClaimExchangeProduct
	 * @Date : 2019. 3. 5.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/save-claim-exchange-product")
	public void saveClaimExchangeProduct(Parameter<?> parameter) throws Exception {
		claimService.updateClaimExchangeProduct(parameter.create(OcClaim.class),
				parameter.createArray(OcClaimProduct.class, "clmPrdtSeq"));
	}

	/**
	 * @Desc : 클레임 반품 등록 화면
	 * @Method Name : createClaimReturnFormPop
	 * @Date : 2019. 4. 29.
	 * @Author : KTH
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/claim/create-claim-return-form-pop")
	public ModelAndView createClaimReturnFormPop(Parameter<OcClaimProduct> parameter) throws Exception {
		OcClaimProduct ocClaimProduct = parameter.get();
		ocClaimProduct.setClmGbnCode(CommonCode.CLM_GBN_CODE_RETURN);

		// 클레임 반품 접수 대상 주문상품 정보
		Map<String, Object> map = claimService.getClaimOrderProductInfo(ocClaimProduct,
				CommonCode.CPN_TYPE_CODE_FREE_RETURN);

		parameter.addAttribute(CommonCode.CLM_RSN_CODE, map.get(CommonCode.CLM_RSN_CODE)); // 클레임사유코드
		parameter.addAttribute("orderInfo", map.get("ORDER_INFO")); // 주문기본정보
		parameter.addAttribute("orderProductList", map.get("ORDER_PRODUCT_LIST")); // 주문상품정보
		parameter.addAttribute("orderPaymentList", map.get("ORDER_PAYMENT_LIST")); // 주문결제정보
		parameter.addAttribute("usableCouponList", map.get("USABLE_COUPON_LIST")); // 회원 무료사용 가능 보유쿠폰
		parameter.addAttribute("claimDeliveryAmt", map.get("CLAIM_DELIVERY_AMT")); // 클레임 배송비
		parameter.addAttribute("adminNo", LoginManager.getUserDetails().getAdminNo());
		parameter.addAttribute("adminLoginId", LoginManager.getUserDetails().getLoginId());
		parameter.addAttribute("adminLoginName", LoginManager.getUserDetails().getUsername());

		parameter.addAttribute("kcpJsUrl", Config.getString("pg.kcp.js.url")); // kcp js모듈 호출 경로
		parameter.addAttribute("siteCd", map.get("pgSiteCd")); // kcp siteCode(클레임 배송비 결제용)
		parameter.addAttribute("siteName", map.get("pgSiteName")); // kcp siteName
		parameter.addAttribute("moduleType", Config.getString("pg.kcp.module.type")); // kcp moduleType
		parameter.addAttribute("kcpPaymentApproval", map.get("KCP_PAYMENT_APPROVAL")); // kcp 결제요청 정보

		// 2020.03.05 : 주결제수단을 내린다. (휴대폰결제시 환불금차감 반품접수를 막기위해)
		OcOrder ocOrder = (OcOrder) map.get("ORDER_INFO");
		if (ocOrder != null) {
			parameter.addAttribute("mainPayment", claimService.getMainPayment(ocOrder.getOrgOrderNo()));
		}
		
		// 2020.05.07 : 비회원 AS반풉접수를 위한 set
		if (UtilsText.equals(ocClaimProduct.getAsReturnYn(), Const.BOOLEAN_TRUE)) {
			parameter.addAttribute("asReturnYn", Const.BOOLEAN_TRUE); // 비회원 AS접수여부 Y라면
		}else {
			parameter.addAttribute("asReturnYn", Const.BOOLEAN_FALSE);
		}

		return forward("/claim/create-claim-return-form-pop");
	}

	/**
	 * @Desc : 클레임 반품 등록
	 * @Method Name : registClaimExchange
	 * @Date : 2019. 3. 8.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/regist-claim-return")
	public void registClaimReturn(Parameter<?> parameter) throws Exception {
		OcClaim ocClaim = parameter.create(OcClaim.class);
		OcClaimProduct[] ocClaimProducts = parameter.createArray(OcClaimProduct.class, "orderPrdtSeq");
		OcClaimMemo ocClaimMemo = parameter.create(OcClaimMemo.class);
		KcpPaymentApproval kcpPaymentApproval = parameter.create(KcpPaymentApproval.class);

		ocClaim.setClmGbnCode(CommonCode.CLM_GBN_CODE_RETURN);
		kcpPaymentApproval.setCustIp(parameter.getRequest().getRemoteAddr()); // 요청 IP set

		claimService.registClaimReturn(ocClaim, ocClaimProducts, ocClaimMemo, kcpPaymentApproval);
		writeJson(parameter, ocClaim);
	}

	/**
	 * @Desc : 클레임 반품 상세 화면
	 * @Method Name : readClaimReturnDetailPop
	 * @Date : 2019. 4. 29.
	 * @Author : KTH
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/claim/read-claim-return-detail-pop")
	public ModelAndView readClaimReturnDetailPop(Parameter<OcClaim> parameter) throws Exception {
		OcClaim ocClaim = parameter.get();
		ocClaim.setClmGbnCode(CommonCode.CLM_GBN_CODE_RETURN);

		Map<String, Object> map = claimService.getClaimReturnBasisInfo(parameter.get(),
				CommonCode.CPN_TYPE_CODE_FREE_RETURN);

		parameter.addAttribute(CommonCode.CLM_RSN_CODE, map.get(CommonCode.CLM_RSN_CODE)); // 클레임사유코드
		parameter.addAttribute(CommonCode.CLM_PROC_TYPE_CODE, map.get(CommonCode.CLM_PROC_TYPE_CODE)); // 클레임처리유형코드
		parameter.addAttribute(CommonCode.LOGIS_VNDR_CODE, map.get(CommonCode.LOGIS_VNDR_CODE)); // 택배사코드
		parameter.addAttribute(CommonCode.CLM_WTHDRAW_RSN_CODE, map.get(CommonCode.CLM_WTHDRAW_RSN_CODE)); // 클레임철회사유코드
		parameter.addAttribute(CommonCode.BANK_CODE, map.get(CommonCode.BANK_CODE)); // 은행코드

		parameter.addAttribute("orderInfo", map.get("ORDER_INFO")); // 주문기본정보
		parameter.addAttribute("claimInfo", map.get("CLAIM_INFO")); // 클레임기본정보
		parameter.addAttribute("claimProductList", map.get("CLAIM_PRODUCT_LIST")); // 클레임상품정보
		parameter.addAttribute("orderPaymentList", map.get("ORDER_PAYMENT_LIST")); // 주문결제정보
		parameter.addAttribute("claimPaymentList", map.get("CLAIM_PAYMENT_LIST")); // 클레임결제정보 목록
		parameter.addAttribute("addDeliveryPaymentInfo", map.get("ADD_DELIVERY_PAYMENT_INFO")); // 반품배송비 결제정보
		parameter.addAttribute("publicAddDlvyPymntInfo", map.get("publicAddDlvyPymntInfo")); // 2020.03.18 추가
		parameter.addAttribute("redempPointPaymentInfo", map.get("REDEMP_POINT_PAYMENT_INFO")); // 환수포인트 결제 내역
		parameter.addAttribute("usableCouponList", map.get("USABLE_COUPON_LIST")); // 회원 무료사용 가능 보유쿠폰
		parameter.addAttribute("claimDeliveryAmt", map.get("CLAIM_DELIVERY_AMT")); // 클레임 배송비

		parameter.addAttribute("thisTiemClaimPaymentList", map.get("THIS_TIME_CLAIM_PAYMENT_LIST")); // 현재 클레임결제 정보 목록
		parameter.addAttribute("redempClaimPaymentList", map.get("REDEMP_CLAIM_PAYMENT_LIST")); // 환수결제 목록
		parameter.addAttribute("refundClaimPaymentList", map.get("REFUND_CLAIM_PAYMENT_LIST")); // 환불결제 목록
		parameter.addAttribute("refundClaimPaymentPublicList", map.get("REFUND_CLAIM_PAYMENT_PUBLIC_LIST")); // 환불금실결제목록(실제환불대상실결제)
		parameter.addAttribute("addMultiBuyDifferSumAmt", map.get("ADD_MULTI_BUY_DIFFER_SUM_AMT")); // 다족구매프로모션추가환불대상환불발생내역sum
		parameter.addAttribute("mmnyRedempPaymentList", map.get("MMNY_REDEMP_PAYMENT_LIST")); // 재경팀환수처리대상목록
		parameter.addAttribute("mmnyRefundPaymentList", map.get("MMNY_REFUNND_PAYMENT_LIST")); // 재경팀환불처리대상목록
		parameter.addAttribute("calcPaymentList", map.get("CALC_PAYMENT_LIST")); // 계산용 클레임결제 정보 목록
		parameter.addAttribute("claimSumAmt", map.get("CLAIM_SUM_AMT")); // 클레임총금액
		parameter.addAttribute("claimPossibleSumAmt", map.get("CLAIM_POSSIBLE_SUM_AMT")); // 반품불가 상태 상품을 제외한 클레임상품 금액
		parameter.addAttribute("claimProductSumAmt", map.get("CLAIM_PRODUCT_SUM_AMT")); // 클레임상품총금액
		parameter.addAttribute("thisClaimVariationSavePoint", map.get("THIS_CLAIM_VARIATION_SAVE_POINT")); // 클레임으로변경되는구매적립증감포인트

		parameter.addAttribute("kcpJsUrl", Config.getString("pg.kcp.js.url")); // kcp js모듈 호출 경로
		parameter.addAttribute("siteCd", map.get("pgSiteCd")); // kcp siteCode(클레임 배송비 결제용)
		parameter.addAttribute("siteName", map.get("pgSiteName")); // kcp siteName
		parameter.addAttribute("moduleType", Config.getString("pg.kcp.module.type")); // kcp moduleType
		parameter.addAttribute("kcpPaymentApproval", map.get("KCP_PAYMENT_APPROVAL")); // kcp 결제요청 정보

		// 2020.03.16 : 주결제수단을 내린다. (휴대폰결제시 환불금차감 배송비 저장을 막기위해)
		OcOrder ocOrder = (OcOrder) map.get("ORDER_INFO");
		if (ocOrder != null) {
			parameter.addAttribute("mainPayment", claimService.getMainPayment(ocOrder.getOrgOrderNo()));
		}

		return forward("/claim/read-claim-return-detail-pop");
	}

	/**
	 * @Desc : 클레임 반품 상품 저장(업데이트)
	 * @Method Name : saveClaimReturnProduct
	 * @Date : 2019. 4. 29.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/save-claim-return-product")
	public void saveClaimReturnProduct(Parameter<?> parameter) throws Exception {
		claimService.updateClaimReturnProduct(parameter.create(OcClaim.class),
				parameter.createArray(OcClaimProduct.class, "clmPrdtSeq"));
	}

	/**
	 * @Desc : 클레임 메모 목록
	 * @Method Name : readClaimMemoList
	 * @Date : 2019. 3. 30.
	 * @Author : KTH
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/claim/read-claim-memo-list")
	public ModelAndView readClaimMemoList(Parameter<OcClaimMemo> parameter) throws Exception {
		parameter.addAttribute("claimMemoList", claimService.getClaimMemoList(parameter.get()));

		return forward("/claim/read-claim-memo-list");
	}

	/**
	 * @Desc : 클레임 메모 등록
	 * @Method Name : createClaimMemo
	 * @Date : 2019. 3. 30.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/create-claim-memo")
	public void createClaimMemo(Parameter<OcClaimMemo> parameter) throws Exception {
		claimService.insertClaimMemo(parameter.get());
	}

	/**
	 * @Desc : 클레임 메모 삭제(삭제 플래그 업데이트)
	 * @Method Name : deleteClaimMemo
	 * @Date : 2019. 3. 30.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/delete-claim-memo")
	public void deleteClaimMemo(Parameter<OcClaimMemo> parameter) throws Exception {
		OcClaimMemo ocClaimMemo = null;
		if ("orderDetail".equals(parameter.getString("click"))) {
			ocClaimMemo = new OcClaimMemo();
			ocClaimMemo.setClmNo(parameter.getString("orderNo"));
			ocClaimMemo.setClmMemoSeq(parameter.getInt("orderMemoSeq", 0));
		} else {
			ocClaimMemo = parameter.get();
		}

		claimService.deleteClaimMemo(ocClaimMemo);
	}

	/**
	 * @Desc : 클레임 미처리표시 상태 업데이트
	 * @Method Name : updateClaimUnProcYn
	 * @Date : 2019. 3. 31.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/claim/update-claim-unprocyn")
	public void updateClaimUnProcYn(Parameter<OcClaim> parameter) throws Exception {
		claimService.updateClaimUnProcYn(parameter.get());
	}

	/**
	 * @Desc : 클레임 점간이동여부 업데이트
	 * @Method Name : updateClaimBranchMoveCode
	 * @Date : 2019. 3. 31.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/claim/update-claim-branchmovecode")
	public void updateClaimBranchMoveCode(Parameter<OcClaim> parameter) throws Exception {
		claimService.updateClaimBranchMoveCode(parameter.get());
	}

	/**
	 * @Desc : 취소 접수절회
	 * @Method Name : updateClaimCancelWthdraw
	 * @Date : 2019. 4. 2.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/update-claim-cancel-wthdraw")
	public void updateClaimCancelWthdraw(Parameter<OcClaim> parameter) throws Exception {
		claimService.updateClaimCancelWthdraw(parameter.get());
	}

	/**
	 * @Desc : 교환 접수절회
	 * @Method Name : updateClaimExchangeWthdraw
	 * @Date : 2019. 4. 2.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/update-claim-exchange-wthdraw")
	public void updateClaimExchangeWthdraw(Parameter<OcClaim> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = claimService.updateClaimExchangeWthdraw(parameter.get());

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 반품 접수절회
	 * @Method Name : updateClaimReturnWthdraw
	 * @Date : 2019. 4. 2.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/update-claim-return-wthdraw")
	public void updateClaimReturnWthdraw(Parameter<OcClaim> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = claimService.updateClaimReturnWthdraw(parameter.get());

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 회수지시
	 * @Method Name : requestClaimRetrieval
	 * @Date : 2019. 4. 4.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/request-claim-retrieval")
	public void requestClaimRetrieval(Parameter<?> parameter) throws Exception {
		OcClaim ocClaim = parameter.create(OcClaim.class);

		claimService.setRequestClaimRetrieval(ocClaim);
	}

	/**
	 * @Desc : 수령완료
	 * @Method Name : finishClaimReceipt
	 * @Date : 2019. 4. 4.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/finish-claim-receipt")
	public void finishClaimReceipt(Parameter<?> parameter) throws Exception {
		OcClaim ocClaim = parameter.create(OcClaim.class);

		claimService.setFinishiClaimReceipt(ocClaim);
	}

	/**
	 * @Desc : 교환 심의완료
	 * @Method Name : finishClaimExchangeJudge
	 * @Date : 2019. 4. 4.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/finish-claim-exchange-judge")
	public void finishClaimExchangeJudge(Parameter<?> parameter) throws Exception {
		OcClaim ocClaim = parameter.create(OcClaim.class);

		claimService.setFinishClaimExchangeJudge(ocClaim);
	}

	/**
	 * @Desc : 반품 심의완료
	 * @Method Name : finishClaimReturnJudge
	 * @Date : 2019. 4. 4.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/finish-claim-return-judge")
	public void finishClaimReturnJudge(Parameter<?> parameter) throws Exception {
		OcClaim ocClaim = parameter.create(OcClaim.class);

		claimService.setFinishClaimReturnJudge(ocClaim);
	}

	/**
	 * @Desc : 교환 배송지시
	 * @Method Name : cmdClaimExchangeDeliveryProc
	 * @Date : 2019. 4. 4.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/cmd-claim-exchange-delivery-proc")
	public void cmdClaimExchangeDeliveryProc(Parameter<?> parameter) throws Exception {
		OcClaim ocClaim = parameter.create(OcClaim.class);

		claimService.setCmdClaimExchangeDeliveryProc(ocClaim);
	}

	/**
	 * @Desc : 교환 클레임완료
	 * @Method Name : finishClaimExchangeAllProc
	 * @Date : 2019. 4. 4.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/finish-claim-exchange-all-proc")
	public void finishClaimExchangeAllProc(Parameter<?> parameter) throws Exception {
		OcClaim ocClaim = parameter.create(OcClaim.class);

		claimService.setFinishClaimExchangeAllProc(ocClaim);
	}

	/**
	 * @Desc : 반품 클레임완료
	 * @Method Name : finishClaimReturnAllProc
	 * @Date : 2019. 4. 4.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/finish-claim-return-all-proc")
	public void finishClaimReturnAllProc(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = claimService.setFinishClaimReturnAllProc(parameter.create(OcClaim.class),
				parameter.createArray(OcClaimPayment.class, "pymntMeansCode"));

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 적립포인트 환수접수
	 * @Method Name : acceptClaimRedempPoint
	 * @Date : 2019. 8. 22.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/accept-claim-redemp-point")
	public void acceptClaimRedempPoint(Parameter<OcClaim> parameter) throws Exception {
		OcClaim ocClaim = parameter.create(OcClaim.class);
		KcpPaymentApproval kcpPaymentApproval = parameter.create(KcpPaymentApproval.class);

		kcpPaymentApproval.setCustIp(parameter.getRequest().getRemoteAddr()); // 요청 IP set

		claimService.setAcceptClaimRedempPoint(ocClaim, kcpPaymentApproval);
	}

	/**
	 * @Desc : 환수환불 접수
	 * @Method Name : acceptClaimRedempRefund
	 * @Date : 2019. 4. 4.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/accept-claim-redemp-refund")
	public void acceptClaimRedempRefund(Parameter<OcClaim> parameter) throws Exception {
		OcClaim ocClaim = parameter.create(OcClaim.class);
		OcClaimProduct ocClaimProduct = parameter.create(OcClaimProduct.class);

		claimService.setAcceptClaimRedempRefund(ocClaim, ocClaimProduct);
	}

	/**
	 * @Desc : 환수포인트 가상계좌 재발급
	 * @Method Name : requestVirtualAccountPointAgain
	 * @Date : 2019. 4. 2.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/request-virtual-account-point-again")
	public void requestVirtualAccountPointAgain(Parameter<?> parameter) throws Exception {
		OcClaimPayment ocClaimPayment = parameter.create(OcClaimPayment.class);
		KcpPaymentApproval kcpPaymentApproval = parameter.create(KcpPaymentApproval.class);

		kcpPaymentApproval.setCustIp(parameter.getRequest().getRemoteAddr()); // 요청 IP set

		claimService.setClaimDlvyVirtualAccountPointAgain(ocClaimPayment, kcpPaymentApproval);
	}

	/**
	 * @Desc : 배송비 가상계좌 재발급
	 * @Method Name : requestVirtualAccountAgain
	 * @Date : 2019. 4. 2.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/request-virtual-account-again")
	public void requestVirtualAccountAgain(Parameter<?> parameter) throws Exception {
		OcClaimPayment ocClaimPayment = parameter.create(OcClaimPayment.class);
		KcpPaymentApproval kcpPaymentApproval = parameter.create(KcpPaymentApproval.class);

		kcpPaymentApproval.setCustIp(parameter.getRequest().getRemoteAddr()); // 요청 IP set

		claimService.setClaimDlvyVirtualAccountAgain(ocClaimPayment, kcpPaymentApproval);
	}

	/**
	 * @Desc : 클레임 배송비 결제취소/무료쿠폰 사용 복원
	 * @Method Name : cancelClaimDeliveryAmt
	 * @Date : 2019. 7. 23.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/cancel-claim-delivery-amt")
	public void cancelClaimDeliveryAmt(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		OcClaim ocClaim = parameter.create(OcClaim.class);
		KcpPaymentApproval kcpPaymentApproval = parameter.create(KcpPaymentApproval.class);

		kcpPaymentApproval.setCustIp(parameter.getRequest().getRemoteAddr()); // 요청 IP set

		resultMap = claimService.setCancelDeliveryAmt(ocClaim);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 교환 접수기본정보 및 배송비 저장
	 * @Method Name : saveClaimExchangeDeliveryInfo
	 * @Date : 2019. 4. 3.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/save-claim-exchange-delivery-info")
	public void saveClaimExchangeDeliveryInfo(Parameter<?> parameter) throws Exception {
		OcClaim ocClaim = parameter.create(OcClaim.class);
		KcpPaymentApproval kcpPaymentApproval = parameter.create(KcpPaymentApproval.class);

		kcpPaymentApproval.setCustIp(parameter.getRequest().getRemoteAddr()); // 요청 IP set

		claimService.setClaimExchangeDeliveryInfo(ocClaim, kcpPaymentApproval);
	}

	/**
	 * @Desc : 반품 접수기본정보 및 배송비 저장
	 * @Method Name : saveClaimReturnDeliveryInfo
	 * @Date : 2019. 4. 3.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/save-claim-return-delivery-info")
	public void saveClaimReturnDeliveryInfo(Parameter<?> parameter) throws Exception {
		OcClaim ocClaim = parameter.create(OcClaim.class);
		KcpPaymentApproval kcpPaymentApproval = parameter.create(KcpPaymentApproval.class);

		kcpPaymentApproval.setCustIp(parameter.getRequest().getRemoteAddr()); // 요청 IP set

		claimService.setClaimReturnDeliveryInfo(ocClaim, kcpPaymentApproval);
	}

	/**
	 * @Desc : 환불계좌 인증
	 * @Method Name : claimAccountAuth
	 * @Date : 2019. 7. 18.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/claim-account-auth")
	public void claimAccountAuth(Parameter<OcClaimCertificationHistory> parameter) throws Exception {
		OcClaimCertificationHistory ocClaimCertificationHistory = parameter.get();

		Map<String, Object> resultMap = claimService.setClaimAccountAuthProc(ocClaimCertificationHistory);
		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 클레임 환불계좌 저장
	 * @Method Name : saveRefundAccountInfoInfo
	 * @Date : 2019. 7. 18.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/save-refund-account-info")
	public void saveRefundAccountInfoInfo(Parameter<?> parameter) throws Exception {
		claimService.setRefundAccountInfo(parameter.create(OcClaim.class));
	}

	/**
	 * @Desc : 클레임 취소 처리
	 * @Method Name : setClaimCancelProc
	 * @Date : 2019. 7. 20.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/claim/claim-cancel-proc")
	public void setClaimCancelProc(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = claimService.setClaimCancelProc(parameter.create(OcClaim.class),
				parameter.createArray(OcClaimPayment.class, "pymntMeansCode"));

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 주문 전체 취소용 팝업(클레임 사유코드 선택)
	 * @Method Name : orderAllCancelPop
	 * @Date : 2019. 7. 26.
	 * @Author : KTH
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/claim/order-all-cancel-pop")
	public ModelAndView orderAllCancelPop(Parameter<OcClaim> parameter) throws Exception {
		parameter.addAttribute("claimInfo", parameter.get());
		parameter.addAttribute(CommonCode.CLM_RSN_CODE,
				commonCodeService.getCodeNoNameFilterAddInfo(CommonCode.CLM_RSN_CODE, CommonCode.CLM_GBN_CODE_CANCEL));

		return forward("/claim/order-all-cancel-pop");
	}

	/**
	 * 
	 * @Desc : 주문 전체 취소
	 * @Method Name : setOrderAllCancel
	 * @Date : 2019. 3. 29.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/claim/order-all-cancel")
	public void setOrderAllCancel(Parameter<OcClaim> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = claimService.setOrderAllCancelProc(parameter.create(OcClaim.class));

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 주문 전체 취소 validate
	 * @Method Name : validateOrderAllCancel
	 * @Date : 2019. 9. 23.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/validate-order-all-cancel")
	public void validateOrderAllCancel(Parameter<OcOrder> parameter) throws Exception {

		Map<String, Object> resultMap = claimService.validateOrderAllCancel(parameter.get());

		writeJson(parameter, resultMap);
	}

	/**
	 * 
	 * @Desc : 주문에서 클레임 접수시 validate(개별 상품 선택)
	 * @Method Name : validateClaimProduct
	 * @Date : 2019. 3. 29.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/validate-claim-product")
	public void validateClaimAccept(Parameter<OcClaimProduct> parameter) throws Exception {

		Map<String, Object> resultMap = claimService.validateClaimAccept(parameter.get());

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 분실 취소 등록
	 * @Method Name : registClaimMissCancel
	 * @Date : 2019. 8. 28.
	 * @Author : KTH
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/regist-claim-miss-cancel")
	public void registClaimMissCancel(Parameter<?> parameter) throws Exception {
		OcClaim ocClaim = parameter.create(OcClaim.class);
		OcClaimProduct[] ocClaimProducts = parameter.createArray(OcClaimProduct.class, "orderPrdtSeq");

		Map<String, Object> resultMap = claimService.setMissCancelProc(ocClaim, ocClaimProducts);
		writeJson(parameter, resultMap);
	}

	/**************************** E : 김태호 *****************************/

	/**************************** S : 이재영 *****************************/
	/**
	 * @Desc : bankda 관리 목록 호출
	 * @Method Name : listBankda
	 * @Date : 2019. 4. 2.
	 * @Author : ljyoung@3top.co.kr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bankda/list")
	public ModelAndView listBankda() throws Exception {
		return forward("/claim/bankda-list");
	}

	/**
	 * @Desc : bankda 관리 목록 검색
	 * @Method Name : searchBankda
	 * @Date : 2019. 4. 2.
	 * @Author : ljyoung@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/bankda/list/search")
	public void searchListBankda(Parameter<IfBankda> parameter) throws Exception {
		Pageable<IfBankda, IfBankda> pageable = new Pageable<>(parameter);
		Page<IfBankda> page = claimService.selectListBankda(pageable);

		UtilsResponse.writeJson(parameter.getResponse(), page.getGrid());
	}

	/**
	 * @Desc : bankda 계좌 오늘 입출금 정보 조회
	 * @Method Name : todayInfoBankda
	 * @Date : 2019. 4. 2.
	 * @Author : ljyoung@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/bankda/todayInfo")
	public void todayInfoBankda(Parameter<IfBankda> parameter) throws Exception {
		Map<String, Object> returnList = new HashMap<String, Object>();
		IfBankda ifBankDa = claimService.selectTodayInfoOfBankda(parameter.get());
		returnList.put("todayInfo", ifBankDa);
		UtilsResponse.writeJson(parameter.getResponse(), returnList);
	}

	/**
	 * @Desc : bankda 입금 정보 update popup 호출
	 * @Method Name : registDepositBankda
	 * @Date : 2019. 4. 2.
	 * @Author : ljyoung@3top.co.kr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bankda/deposit/popup")
	public ModelAndView popupBankdaDeposit(Parameter<IfBankda> parameter) throws Exception {
		parameter.addAttribute("bankdaInfo", claimService.selectBankdaByPrimaryKey(parameter.get()));
		return forward("/claim/bankda-deposit-pop");
	}

	/**
	 * @Desc : bankda 정보 update
	 * @Method Name : registDepositBankda
	 * @Date : 2019. 4. 2.
	 * @Author : ljyoung@3top.co.kr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bankda/update")
	public void updateBankda(Parameter<IfBankda[]> parameter) throws Exception {
		IfBankda[] lists = parameter.get();
		for (IfBankda ifBankda : lists) {
			claimService.updateBankda(ifBankda);
		}
	}

	/**************************** E : 이재영 *****************************/

	/**
	 * @Desc : 동봉 배송비 업데이트 - 심의완료 전까지만
	 * @Method Name : saveEclsDlvyAmt
	 * @Date : 2020. 2. 3.
	 * @Author : 이강수
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/save-ecls-dlvy-amt")
	public void saveEclsDlvyAmt(Parameter<?> parameter) throws Exception {
		OcClaim ocClaim = parameter.create(OcClaim.class);

		claimService.setEclsDlvyAmt(ocClaim);
	}

	/**
	 * @Desc : 반품 배송비 결제취소 (배송비 환불금차감으로 반품완료했으나 차감한 배송비를 결제취소해서 환불해줘야하는 경우)
	 * @Method Name : cancelReturnDlvyAmt
	 * @Date : 2020. 3. 9.
	 * @Author : 이강수
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/cancel-refund-dlvy-amt-pymnt")
	public void cancelReturnDlvyAmt(Parameter<?> parameter) throws Exception {
		OcClaim ocClaim = parameter.create(OcClaim.class);

		Map<String, Object> resultMap = claimService.setCancelReturnDlvyAmt(ocClaim);

		writeJson(parameter, resultMap);
	}
	
	/**
	 * @Desc : 미출로인한 교환불가 (교환배송지시가 내려진 상태에서 교환재배송상품 중 하나라도 미출반품처리대상이되어 배송중단이 내려지면 전체 교환불가)
	 * @Method Name : setClaimExchangeImpossible
	 * @Date : 2020. 4. 23.
	 * @Author : 이강수
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim/set-claim-exchange-impossible")
	public void setClaimExchangeImpossible(Parameter<?> parameter) throws Exception {
		OcClaim ocClaim = parameter.create(OcClaim.class);
		OcClaimProduct[] ocClaimProducts = parameter.createArray(OcClaimProduct.class, "clmPrdtSeq");
		
		Map<String, Object> resultMap = claimService.setClaimExchangeImpossible(ocClaim, ocClaimProducts);

		writeJson(parameter, resultMap);
	}
}