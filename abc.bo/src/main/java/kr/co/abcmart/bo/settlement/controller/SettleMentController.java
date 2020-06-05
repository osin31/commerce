/**
 *
 */
package kr.co.abcmart.bo.settlement.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.settlement.model.master.SeExactCalculation;
import kr.co.abcmart.bo.settlement.model.master.SeExactCalculationBrand;
import kr.co.abcmart.bo.settlement.model.master.SeExactCalculationBrandProduct;
import kr.co.abcmart.bo.settlement.model.master.SeExactCalculationMemo;
import kr.co.abcmart.bo.settlement.service.SettleMentService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsDate;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 정산
 * @FileName : SettleMentController.java
 * @Project : abc.bo
 * @Date : 2019. 7. 1.
 * @Author : lee
 */
@Slf4j
@Controller
@RequestMapping("/settlement")
public class SettleMentController extends BaseController {

	@Autowired
	CommonCodeService commonCodeService;

	@Autowired
	SettleMentService settleMentService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private VendorService vendorService;

	/**
	 * @Desc : 정산관리 > 업체별 정산관리
	 * @Method Name : settleMentMain
	 * @Date : 2019. 7. 3.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/settlement-main")
	public ModelAndView settleMentMain(Parameter<?> parameter) throws Exception {
		String[] codeFields = { CommonCode.DEVICE_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		parameter.addAttribute("codeList", pair.getSecond());

		return forward("/settlement/settlement-main");
	}

	/**
	 * @Desc : 업체별 정산관리
	 * @Method Name : settleMentList
	 * @Date : 2019. 7. 3.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/settlement-list")
	public void settleMentList(Parameter<SeExactCalculation> parameter) throws Exception {
		SeExactCalculation seExactCalculation = parameter.get();
		Page<SeExactCalculation> page = null;

		Pageable<SeExactCalculation, SeExactCalculation> pageable = new Pageable<>(parameter);

		page = settleMentService.getSettleMentListList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 업체별 정산 금액 합계
	 * @Method Name : setGiftCardHistoryCancel
	 * @Date : 2019. 7. 3.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/settlement-total-amt")
	public void getSettlementTotalAmt(Parameter<SeExactCalculation> parameter) throws Exception {
		SeExactCalculation seExactCalculation = parameter.get();

		Map<String, Object> resultMap = settleMentService.getSettlementTotalAmt(seExactCalculation);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc :월장 메인
	 * @Method Name : settleMentMonthMain
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/settlement-month-main")
	public ModelAndView settleMentMonthMain(Parameter<SeExactCalculation> parameter) throws Exception {
		SeExactCalculation seExactCalculation = parameter.get();

		String excclcSeq = settleMentService.getSeExactCal(seExactCalculation);

		SeExactCalculationMemo seExactCalculationMemo = new SeExactCalculationMemo();
		seExactCalculationMemo.setExcclcSeq(Integer.parseInt(excclcSeq));

		Map<String, Object> dataMap = settleMentService.getSeExactCalMemoData(seExactCalculationMemo);

		parameter.addAttribute("excclcSeq", seExactCalculation.getExcclcSeq());
		parameter.addAttribute("excclcYm", seExactCalculation.getExcclcYm());
		parameter.addAttribute("vndrNo", seExactCalculation.getVndrNo());
		parameter.addAttribute("vndrName", seExactCalculation.getVndrName());
		parameter.addAttribute("memoInfo", dataMap.get("memoInfo"));

		return forward("/settlement/settlement-month-main");
	}

	/**
	 * @Desc : 업체
	 * @Method Name : settleMentMonthMainVndr
	 * @Date : 2019. 10. 16.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/settlement-month-main-vendor")
	public ModelAndView settleMentMonthMainVndr(Parameter<?> parameter) throws Exception {

		UserDetails user = LoginManager.getUserDetails();

		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		VdVendor vndr = new VdVendor();
		if (!isAdmin) {
			vndr.setVndrNo(user.getVndrNo());
			vndr = vendorService.getVendorBaseInfo(vndr);

			parameter.addAttribute("vndrNo", vndr.getVndrNo());
			parameter.addAttribute("vndrName", vndr.getVndrName());
		}

		// 업체 년도 당월에 맞는 excclcSeq 구하기
		SeExactCalculation seExactCalculation = new SeExactCalculation();
		seExactCalculation.setVndrNo(vndr.getVndrNo());
		seExactCalculation.setExcclcYm(UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

		String excclcSeq = settleMentService.getSeExactCal(seExactCalculation);

		SeExactCalculationMemo seExactCalculationMemo = new SeExactCalculationMemo();
		seExactCalculationMemo.setExcclcSeq(Integer.parseInt(excclcSeq));

		Map<String, Object> dataMap = settleMentService.getSeExactCalMemoData(seExactCalculationMemo);
		parameter.addAttribute("excclcSeq", excclcSeq);
		parameter.addAttribute("memoInfo", dataMap.get("memoInfo"));
		parameter.addAttribute("excclcYm", UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS));

		return forward("/settlement/settlement-month-main-vendor");
	}

	/**
	 * @Desc :월정산 탭별 리스트
	 * @Method Name : settleMentMonthList
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/sellteMent-month-list")
	public void settleMentMonthList(Parameter<SeExactCalculation> parameter) throws Exception {
		SeExactCalculation seExactCalculation = parameter.get();

		// 검색 되는 YYYYMM 에 맞는 EXCCLC_SEQ 구하기
		String excclcSeq = settleMentService.getSeExactCal(seExactCalculation);

		seExactCalculation.setExcclcSeq(Integer.parseInt(excclcSeq));

		// 브랜드 상품별 리스트(판매수수료, 배송비 , 프로모션, 패널티 탭에 사용됨)
		Page<SeExactCalculationBrandProduct> page1 = null;
		// 브랜드 (월정산 탭)
		Page<SeExactCalculationBrand> page2 = null;

		// 1: 월정산
		// 2: 판매수수료 탭
		// 3:배송비 탭
		// 4:프로모션 비용탭
		// 5:패널티산정 탭
		if ("1".equals(seExactCalculation.getTabGubun())) {
			Pageable<SeExactCalculation, SeExactCalculationBrand> pageable1 = new Pageable<>(parameter);
			page2 = settleMentService.getSeExactCalcMonthSettle(pageable1);
		} else if ("2".equals(seExactCalculation.getTabGubun())) {
			Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable1 = new Pageable<>(parameter);
			page1 = settleMentService.getSeExactCalcSellAmt(pageable1);
		} else if ("3".equals(seExactCalculation.getTabGubun())) {
			Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable1 = new Pageable<>(parameter);
			page1 = settleMentService.getSeExactCalcDlvyAmt(pageable1);
		} else if ("4".equals(seExactCalculation.getTabGubun())) {
			Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable1 = new Pageable<>(parameter);
			page1 = settleMentService.getSeExactCalcPromoAmt(pageable1);
		} else if ("5".equals(seExactCalculation.getTabGubun())) {
			Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable1 = new Pageable<>(parameter);
			page1 = settleMentService.getSeExactCalcPenltyAmt(pageable1);
		}

		if ("1".equals(seExactCalculation.getTabGubun())) {
			writeJson(parameter, page2.getGrid());
		} else {
			writeJson(parameter, page1.getGrid());
		}
	}

	/**
	 * @Desc :월정산 탭별 정산내역
	 * @Method Name : getSettlementHistory
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/settlement-history")
	public void getSettlementHistory(Parameter<SeExactCalculation> parameter) throws Exception {
		SeExactCalculation seExactCalculation = parameter.get();

		// 검색 되는 YYYYMM 에 맞는 EXCCLC_SEQ 구하기
		String excclcSeq = settleMentService.getSeExactCal(seExactCalculation);

		seExactCalculation.setExcclcSeq(Integer.parseInt(excclcSeq));

		Map<String, Object> resultMap = null;
		if ("1".equals(seExactCalculation.getTabGubun())) {
			resultMap = settleMentService.getSettlementMonth(seExactCalculation);
		} else {
			resultMap = settleMentService.getSettlementHistory(seExactCalculation);
		}
		resultMap.put("excclcSeq", excclcSeq);
		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc :월정산 조정금액
	 * @Method Name : updateSettleMentExcclcMod
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/settlement-excclc-payment-mdat")
	public void updateSettleMentExcclcMod(Parameter<?> parameter) throws Exception {
		// Parameter 클래스의 createArray 메소드를 이용하여 그리드에서 배열로 넘어온 파라메터 값을 VO에 맵핑

		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 대상 파라미터 맵핑
		SeExactCalculationBrand seExactCalculationBrand = parameter.create(SeExactCalculationBrand.class);

		// Parameter 클래스의 createArray 메소드를 이용하여 그리드에서 배열로 넘어온 파라메터 값을 VO에 맵핑
		SeExactCalculationBrand[] entityGrid = parameter.createArray(SeExactCalculationBrand.class, "excclcSeq");

		log.info("lengthlength ::" + entityGrid.length);

		//
		int penltyMdatAmt = 0;
		int excclcMdatAmt = 0;
		int exccleSeq = 0;
		for (int i = 0; i < entityGrid.length; i++) {
			SeExactCalculationBrand seExactCalculationBrandData = entityGrid[i];
			exccleSeq = seExactCalculationBrandData.getExcclcSeq();
			if (seExactCalculationBrandData.getPenltyMdatAmt() != null) {
				penltyMdatAmt += seExactCalculationBrandData.getPenltyMdatAmt();
			}
			if (seExactCalculationBrandData.getExcclcMdatAmt() != null) {
				excclcMdatAmt += seExactCalculationBrandData.getExcclcMdatAmt();
			}
			resultMap = settleMentService.updateSeExactBrandMod(seExactCalculationBrandData);
		}

		if ("Y".equals(resultMap.get("result"))) {
			SeExactCalculation seExactCalculation = new SeExactCalculation();
			seExactCalculation.setExcclcSeq(exccleSeq);
			seExactCalculation.setPenltyMdatAmt(penltyMdatAmt);
			seExactCalculation.setExcclcMdatAmt(excclcMdatAmt);
			resultMap = settleMentService.updateSeExactCalSumMod(seExactCalculation);
		}
		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc :월정산 탭별 엑셀
	 * @Method Name : settleMentMonthListExcel
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/settlement-list-excel")
	public void settleMentMonthListExcel(Parameter<SeExactCalculation> parameter) throws Exception {
		SeExactCalculation seExactCalculation = parameter.get();

//		Page<SeExactCalculationBrandProduct> page1 = null;
//	
//		Page<SeExactCalculationBrand> page2 = null;
		// 브랜드 (월정산 탭)
		List<SeExactCalculationBrand> list = null;
		// 브랜드 상품별 리스트(판매수수료, 배송비 , 프로모션, 패널티 탭에 사용됨)
		List<SeExactCalculationBrandProduct> listProduct = null;
		// 1: 월정산
		// 2: 판매수수료 탭
		// 3:배송비 탭
		// 4:프로모션 비용탭
		// 5:패널티산정 탭
		if ("1".equals(seExactCalculation.getTabGubun())) {
			Pageable<SeExactCalculation, SeExactCalculationBrand> pageable1 = new Pageable<>(parameter);
			pageable1.setUsePage(false);

			list = settleMentService.getSeExactCalcMonthSettle(pageable1).getContent();

			ExcelValue excelValue = ExcelValue.builder(2, 0)
					.columnNames(Arrays.asList("brandNo", "brandName", "pymntAmt", "sellCmsnAmt", "promoAmt",
							"penltyCount", "penltyTrgtAmt", "penltyMdatAmt", "excclcAmt", "excclcMdatAmt"))
					.data(list).build();

			parameter.downloadExcelTemplate("settlement/excel/settlementMonthList", excelValue);

		} else if ("2".equals(seExactCalculation.getTabGubun())) {
			Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable1 = new Pageable<>(parameter);
			pageable1.setUsePage(false);

			listProduct = settleMentService.getSeExactCalcSellAmt(pageable1).getContent();

			ExcelValue excelValue = ExcelValue.builder(2, 0)
					.columnNames(Arrays.asList("excclcYm", "salesCnclGbnTypeName", "orderNo", "brandName", "brandNo",
							"vndrPrdtNoText", "prdtName", "prdtNormalAmt", "sellAmt", "delayDayYn", "cpnUseYn",
							"cpnName", "cpnDscntAmt", "abcAmt", "cpnShareAmt", "plusCpnName", "plusCpnDscntAmtTemp",
							"plusAbcAmt", "plusCpnShareAmt", "supplyAmtTemp", "vatAmtTemp", "actPymnt",
							"vndrCmsnRateTemp", "vndrSupplyAmtTemp", "vndrVatAmtTemp", "vndrCmsnAmt",
							"buyPointSaveRateTemp", "buyPointSaveAmt", "excclcAmt", "excclcDcsnYn", "excclcDtm",
							"moderNo", "finalModDtm"))
					.data(listProduct).build();

			parameter.downloadExcelTemplate("settlement/excel/settlementSellAmtList", excelValue);
		} else if ("3".equals(seExactCalculation.getTabGubun())) {
			Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable1 = new Pageable<>(parameter);
			pageable1.setUsePage(false);

			listProduct = settleMentService.getSeExactCalcDlvyAmt(pageable1).getContent();

			ExcelValue excelValue = ExcelValue.builder(1, 0)
					.columnNames(Arrays.asList("excclcYm", "salesCnclGbnTypeName", "orderNo", "orderAmt",
							"excclcDcsnYn", "excclcDtm", "moderNo", "finalModDtm"))
					.data(listProduct).build();

			parameter.downloadExcelTemplate("settlement/excel/settlementDlvyAmtList", excelValue);
		} else if ("4".equals(seExactCalculation.getTabGubun())) {
			Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable1 = new Pageable<>(parameter);
			pageable1.setUsePage(false);

			listProduct = settleMentService.getSeExactCalcPromoAmt(pageable1).getContent();

			ExcelValue excelValue = ExcelValue.builder(2, 0)
					.columnNames(Arrays.asList("excclcYm", "salesCnclGbnTypeName", "orderNo", "brandName", "brandNo",
							"vndrPrdtNoText", "prdtName", "prdtNormalAmt", "sellAmt", "cpnUseYn", "cpnName",
							"cpnDscntAmt", "abcAmt", "cpnShareAmt", "plusCpnName", "plusCpnDscntAmtTemp", "plusAbcAmt",
							"plusCpnShareAmt", "supplyAmtTemp", "vatAmtTemp", "actPymnt", "excclcAmt", "excclcDcsnYn",
							"excclcDtm", "moderNo", "finalModDtm"))
					.data(listProduct).build();

			parameter.downloadExcelTemplate("settlement/excel/settlementPromoAmtList", excelValue);
		} else if ("5".equals(seExactCalculation.getTabGubun())) {
			Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable1 = new Pageable<>(parameter);
			pageable1.setUsePage(false);

			listProduct = settleMentService.getSeExactCalcPenltyAmt(pageable1).getContent();

			ExcelValue excelValue = ExcelValue.builder(2, 0)
					.columnNames(Arrays.asList("excclcYm", "salesCnclGbnTypeName", "orderNo", "brandNo", "brandName",
							"vndrPrdtNoText", "prdtName", "prdtNormalAmt", "sellAmt", "delayDayYn", "delayDayCountTemp",
							"penltyLevyRateTemp", "penltyAmtTemp", "supplyAmtTemp", "vatAmtTemp", "actPymnt",
							"excclcAmt", "excclcDcsnYn", "excclcDtm", "moderNo", "finalModDtm"))
					.data(listProduct).build();

			parameter.downloadExcelTemplate("settlement/excel/settlementPenltyAmtList", excelValue);
		}
	}

	/**
	 * @Desc :정산 확정
	 * @Method Name : updateExcclcConfirmation
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/settlement-confirmation")
	public void updateExcclcConfirmation(Parameter<SeExactCalculation> parameter) throws Exception {
		SeExactCalculation seExactCalculation = parameter.get();

		Map<String, Object> resultMap = null;
		resultMap = settleMentService.updateExcclcConfirmation(seExactCalculation);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc :월정산 메모등록
	 * @Method Name : createSeExactCalculationMemo
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/create-settlement-memo")
	public void createSeExactCalculationMemo(Parameter<SeExactCalculationMemo> parameter) throws Exception {
		SeExactCalculationMemo seExactCalculationMemo = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = settleMentService.createSeExactCalculationMemo(seExactCalculationMemo);

		parameter.addAttribute("memoInfo", resultMap);
		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 관리자 메모 삭제 상태만 Y으로 업데이트
	 * @Method Name : deleteOcAsAcceptMemo
	 * @Date : 2019. 7. 16.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/update-settlement-memo")
	public void updateSeExactCalculationMemo(Parameter<SeExactCalculationMemo> parameter) throws Exception {
		SeExactCalculationMemo seExactCalculationMemo = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = settleMentService.updateSeExactCalculationMemo(seExactCalculationMemo);
		parameter.addAttribute("memoInfo", resultMap);
		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc :재정산
	 * @Method Name : propertySettleMent
	 * @Date : 2019. 7. 24.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/property-settlement")
	public void propertySettleMent(Parameter<SeExactCalculation> parameter) throws Exception {
		SeExactCalculation seExactCalculation = parameter.get();

		Map<String, Object> resultMap = null;
		resultMap = settleMentService.getPropertySettleMent(seExactCalculation);

		writeJson(parameter, resultMap);
	}

}
