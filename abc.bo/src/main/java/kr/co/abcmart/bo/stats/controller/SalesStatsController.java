package kr.co.abcmart.bo.stats.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.stats.model.master.SaSalesOrder;
import kr.co.abcmart.bo.stats.service.OrderRealTimeStatsService;
import kr.co.abcmart.bo.stats.service.SalesStatsService;
import kr.co.abcmart.bo.stats.vo.SalesStatsSearchVO;
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

@Controller
@RequestMapping("/stats/sales")
public class SalesStatsController extends BaseController {

	@Autowired
	private OrderRealTimeStatsService orderRealTimeService;
	@Autowired
	private SalesStatsService salesStatsService;
	@Autowired
	private VendorService vendorService;

	/**
	 * @Desc : 발송처별 매출통계
	 * @Method Name : sendTypeStats
	 * @Date : 2019. 7. 2.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/send-type-stats")
	public ModelAndView sendTypeStats(Parameter<?> parameter) throws Exception {
		orderRealTimeService.getStatusSearchCondition(parameter);

		salesStatsService.getCommonCondition(parameter);

		return forward("/stats/sales/send-type-stats");
	}

	/**
	 * @Desc : 디바이스별 매출통계
	 * @Method Name : deviceTypeStats
	 * @Date : 2019. 7. 2.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/device-type-stats")
	public ModelAndView deviceTypeStats(Parameter<?> parameter) throws Exception {
		orderRealTimeService.getStatusSearchCondition(parameter);

		salesStatsService.getCommonCondition(parameter);

		return forward("/stats/sales/device-type-stats");
	}

	/**
	 * @Desc : 결제수단별 매출통계
	 * @Method Name : paymentTypeStats
	 * @Date : 2019. 7. 3.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/payment-type-stats")
	public ModelAndView paymentTypeStats(Parameter<?> parameter) throws Exception {
		orderRealTimeService.getStatusSearchCondition(parameter);

		salesStatsService.getCommonCondition(parameter);

		return forward("/stats/sales/payment-type-stats");
	}

	/**
	 * @Desc : 배송수단별 매출통계
	 * @Method Name : dlvyTypeStats
	 * @Date : 2019. 7. 3.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/dlvy-type-stats")
	public ModelAndView dlvyTypeStats(Parameter<?> parameter) throws Exception {
		orderRealTimeService.getStatusSearchCondition(parameter);

		salesStatsService.getCommonCondition(parameter);

		return forward("/stats/sales/dlvy-type-stats");
	}

	/**
	 * @Desc : 시간별 매출통계
	 * @Method Name : timeTypeStats
	 * @Date : 2019. 7. 3.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/time-type-stats")
	public ModelAndView timeTypeStats(Parameter<?> parameter) throws Exception {
		orderRealTimeService.getStatusSearchCondition(parameter);

		salesStatsService.getCommonCondition(parameter);

		return forward("/stats/sales/time-type-stats");
	}

	/**
	 * @Desc : 시간대별 상품 매출 현황 팝업
	 * @Method Name : timeTypeDetailStats
	 * @Date : 2019. 7. 22.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("time-type-stats/detail-pop")
	public ModelAndView timeTypeDetailStats(Parameter<?> parameter) throws Exception {
		orderRealTimeService.getStatusSearchCondition(parameter);

		parameter.addAttribute("timeType", "timeType");
		salesStatsService.getCommonCondition(parameter);

		return forward("/stats/order/realtime/current-sales-stats-detail");
	}

	/**
	 * @Desc : 연령별 매출통계
	 * @Method Name : timeTypeStats
	 * @Date : 2019. 7. 3.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/age-type-stats")
	public ModelAndView ageTypeStats(Parameter<?> parameter) throws Exception {
		orderRealTimeService.getStatusSearchCondition(parameter);

		salesStatsService.getCommonCondition(parameter);

		return forward("/stats/sales/age-type-stats");
	}

	/**
	 * @Desc : 성별 매출통계
	 * @Method Name : genderTypeStats
	 * @Date : 2019. 7. 11.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/gender-type-stats")
	public ModelAndView genderTypeStats(Parameter<?> parameter) throws Exception {
		orderRealTimeService.getStatusSearchCondition(parameter);

		salesStatsService.getCommonCondition(parameter);

		return forward("/stats/sales/gender-type-stats");
	}

	/**
	 * @Desc : 상품 매출통계
	 * @Method Name : prdtTypeStats
	 * @Date : 2019. 7. 12.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/prdt-type-stats")
	public ModelAndView prdtTypeStats(Parameter<?> parameter) throws Exception {
		orderRealTimeService.getStatusSearchCondition(parameter);

		// 대분류 카테고리 list
		salesStatsService.getBigCategoryList(parameter);
		salesStatsService.getCommonCondition(parameter);

		return forward("/stats/sales/prdt-type-stats");
	}

	/**
	 * @Desc : 임점사 매출통계
	 * @Method Name : vendorTypeStats
	 * @Date : 2019. 7. 16.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/vendor-type-stats")
	public ModelAndView vendorTypeStats(Parameter<?> parameter) throws Exception {
		orderRealTimeService.getStatusSearchCondition(parameter);

		salesStatsService.getCommonCondition(parameter);

		return forward("/stats/sales/vendor-type-stats");
	}

	/**
	 * @Desc : 입점사 상품매출 현황 상세팝업
	 * @Method Name : vendorTypeReadDetail
	 * @Date : 2019. 7. 16.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/vendor-type-stats/detail-pop")
	public ModelAndView vendorTypeReadDetail(Parameter<?> parameter) throws Exception {
		orderRealTimeService.getStatusSearchCondition(parameter);

		salesStatsService.getCommonCondition(parameter);

		return forward("/stats/sales/vendor-detail-pop");
	}

	/**
	 * @Desc : 매출통계 검색조회.
	 * @Method Name : statsSearch
	 * @Date : 2019. 7. 5.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/stats-sales-search")
	public void statsSearch(Parameter<SalesStatsSearchVO> parameter) throws Exception {
		String statsType = parameter.getString("typeStats");
		String isTimeType = parameter.getString("isTimeType");
		Pageable<SalesStatsSearchVO, SaSalesOrder> pageable = new Pageable<>(parameter);
		Page<SaSalesOrder> page = new Page<SaSalesOrder>();

		UserDetails user = LoginManager.getUserDetails();
		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;

		if (!isAdmin) {
			VdVendor vndr = new VdVendor();
			vndr.setVndrNo(user.getVndrNo());
			vndr = vendorService.getVendorBaseInfo(vndr);

			pageable.getBean().setVndrNo(vndr.getVndrNo());
			if (UtilsText.equals(statsType, "paymentType") || UtilsText.equals(statsType, "ageType")
					|| UtilsText.equals(statsType, "genderType") || UtilsText.equals(statsType, "deviceType")) {
				String[] stockGbnCode = { CommonCode.STOCK_GBN_CODE_VD };
				pageable.getBean().setStockGbnCodeArr(stockGbnCode);
			}
		}

		if (UtilsText.equals("sendType", statsType)) {
			page = salesStatsService.getSendTypeList(pageable);
		} else if (UtilsText.equals("deviceType", statsType)) {
			page = salesStatsService.getDeviceTypeList(pageable);
		} else if (UtilsText.equals("paymentType", statsType)) {
			page = salesStatsService.getPaymentTypeList(pageable);
		} else if (UtilsText.equals("dlvyType", statsType)) {
			page = salesStatsService.getDlvyTypeList(pageable);
		} else if (UtilsText.equals("timeType", statsType)) {
			page = salesStatsService.getTimeTypeList(pageable);
		} else if (UtilsText.equals("ageType", statsType)) {
			page = salesStatsService.getAgeTypeList(pageable);
		} else if (UtilsText.equals("genderType", statsType)) {
			page = salesStatsService.getGenderTypeList(pageable);
		} else if (UtilsText.equals("prdtType", statsType)) {
			page = salesStatsService.getPrdtTypeList(pageable);
		} else if (UtilsText.equals("vendorType", statsType)) {
			page = salesStatsService.getVendorTypeList(pageable);
		} else if (UtilsText.equals("vendorPop", statsType)) {
			page = salesStatsService.getVendorPopList(pageable);
		} else if (UtilsText.equals("saleDetail", statsType) && UtilsText.equals(isTimeType, "timeType")) {
			pageable.getBean().setFromDate(parameter.getString("dayFromDate"));
			pageable.getBean().setToDate(parameter.getString("dayToDate"));
			pageable.getBean().setDeviceCodeArr(parameter.getStringArray("deviceCodes"));
			pageable.getBean().setFromHour(parameter.getString("fromTime"));
			pageable.getBean().setToHour(parameter.getString("toTime"));

			page = salesStatsService.getPrdtTypeList(pageable);
		}

		writeJson(parameter, page.getGrid());

	}

	/**
	 * @Desc : 발송처별 매출통계 엑셀 다운로드
	 * @Method Name : downloadSendTypeExcel
	 * @Date : 2019. 7. 5.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-sendTypeExcel")
	public void downloadSendTypeExcel(Parameter<SalesStatsSearchVO> parameter) throws Exception {
		List<SaSalesOrder> list = salesStatsService.getSendTypeListForExcel(getSearchVO(parameter));

		ExcelValue excelValue = ExcelValue.builder(2, 0)
				.columnNames(Arrays.asList("hourTitle", "orderQtyAll", "prdtNormalAmtAll", "orderAmtAll",
						"orderQty10000", "prdtNormalAmt10000", "orderAmt10000", "orderQty10001", "prdtNormalAmt10001",
						"orderAmt10001", "orderQty10002", "prdtNormalAmt10002", "orderAmt10002", "orderQty10003",
						"prdtNormalAmt10003", "orderAmt10003"))
				.data(list).build();

		parameter.downloadExcelTemplate("stats/sales/excel/sendTypeSalesStats", excelValue);
	}

	/**
	 * @Desc : 디바이스별 매출통계 엑셀 다운로드
	 * @Method Name : downloadDeviceTypeExcel
	 * @Date : 2019. 7. 8.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-deviceTypeExcel")
	public void downloadDeviceTypeExcel(Parameter<SalesStatsSearchVO> parameter) throws Exception {
		List<SaSalesOrder> list = salesStatsService.getDeviceListForExcel(getSearchVO(parameter));

		ExcelValue excelValue = ExcelValue.builder(2, 0)
				.columnNames(Arrays.asList("hourTitle", "orderQtyAll", "prdtNormalAmtAll", "orderAmtAll",
						"orderQty10000", "prdtNormalAmt10000", "orderAmt10000", "orderQty10001", "prdtNormalAmt10001",
						"orderAmt10001", "orderQty10002", "prdtNormalAmt10002", "orderAmt10002"))
				.data(list).build();

		parameter.downloadExcelTemplate("stats/sales/excel/deviceTypeSalesStats", excelValue);
	}

	/**
	 * @Desc : 결제수단별 매출통계 엑셀 다운로드
	 * @Method Name : downloadPaymentTypeExcel
	 * @Date : 2019. 7. 8.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-paymentTypeExcel")
	public void downloadPaymentTypeExcel(Parameter<SalesStatsSearchVO> parameter) throws Exception {
		List<SaSalesOrder> list = salesStatsService.getPaymentListForExcel(getSearchVO(parameter));

		ExcelValue excelValue = ExcelValue.builder(2, 0)
				.columnNames(Arrays.asList("hourTitle", "orderQtyAll", "orderAmtAll", "orderQty10000", "orderAmt10000",
						"orderQty10001", "orderAmt10001", "orderQty10002", "orderAmt10002", "orderQty10003",
						"orderAmt10003", "orderQty10004", "orderAmt10004", "orderQty10005", "orderAmt10005",
						"orderQty10006", "orderAmt10006", "orderQty99999", "orderAmt99999"))
				.data(list).build();

		parameter.downloadExcelTemplate("stats/sales/excel/paymentTypeSalesStats", excelValue);
	}

	/**
	 * @Desc : 배송수단별 매출통계 엑셀 다운로드
	 * @Method Name : downloadDlvyTypeExcel
	 * @Date : 2019. 7. 8.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-dlvyTypeExcel")
	public void downloadDlvyTypeExcel(Parameter<SalesStatsSearchVO> parameter) throws Exception {
		List<SaSalesOrder> list = salesStatsService.getDlvyListForExcel(getSearchVO(parameter));

		ExcelValue excelValue = ExcelValue.builder(2, 0)
				.columnNames(Arrays.asList("hourTitle", "orderQtyAll", "prdtNormalAmtAll", "orderAmtAll",
						"orderQty10000", "prdtNormalAmt10000", "orderAmt10000", "orderQty10001", "prdtNormalAmt10001",
						"orderAmt10001", "orderQty10002", "prdtNormalAmt10002", "orderAmt10002"))
				.data(list).build();

		parameter.downloadExcelTemplate("stats/sales/excel/dlvyTypeSalesStats", excelValue);
	}

	/**
	 * @Desc : 시간별 매출통계 엑셀 다운로드
	 * @Method Name : downloadTimeTypeExcel
	 * @Date : 2019. 7. 8.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-timeTypeExcel")
	public void downloadTimeTypeExcel(Parameter<SalesStatsSearchVO> parameter) throws Exception {
		List<SaSalesOrder> list = salesStatsService.getTimeListForExcel(getSearchVO(parameter));

		ExcelValue excelValue = ExcelValue.builder(2, 0)
				.columnNames(Arrays.asList("hourTitle", "orderQtyAll", "prdtNormalAmtAll", "orderAmtAll",
						"orderQty10000", "prdtNormalAmt10000", "orderAmt10000", "orderQty10001", "prdtNormalAmt10001",
						"orderAmt10001", "orderQty10002", "prdtNormalAmt10002", "orderAmt10002", "orderQty10003",
						"prdtNormalAmt10003", "orderAmt10003"))
				.data(list).build();

		parameter.downloadExcelTemplate("stats/sales/excel/timeTypeSalesStats", excelValue);
	}

	/**
	 * @Desc : 연령별 매출통계 엑셀 다운로드
	 * @Method Name : downloadAgeTypeExcel
	 * @Date : 2019. 7. 8.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-ageTypeExcel")
	public void downloadAgeTypeExcel(Parameter<SalesStatsSearchVO> parameter) throws Exception {
		List<SaSalesOrder> list = salesStatsService.getAgeListForExcel(getSearchVO(parameter));

		ExcelValue excelValue = ExcelValue.builder(2, 0)
				.columnNames(Arrays.asList("hourTitle", "orderQtyAll", "prdtNormalAmtAll", "orderAmtAll",
						"orderQty10000", "prdtNormalAmt10000", "orderAmt10000", "orderQty10001", "prdtNormalAmt10001",
						"orderAmt10001", "orderQty10002", "prdtNormalAmt10002", "orderAmt10002", "orderQty10003",
						"prdtNormalAmt10003", "orderAmt10003", "orderQty10004", "prdtNormalAmt10004", "orderAmt10004",
						"orderQty10005", "prdtNormalAmt10005", "orderAmt10005", "orderQty10006", "prdtNormalAmt10006",
						"orderAmt10006"))
				.data(list).build();

		parameter.downloadExcelTemplate("stats/sales/excel/ageTypeSalesStats", excelValue);
	}

	/**
	 * @Desc : 성별 매출통계 엑셀 다운로드
	 * @Method Name : downloadGenderTypeExcel
	 * @Date : 2019. 7. 11.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-genderTypeExcel")
	public void downloadGenderTypeExcel(Parameter<SalesStatsSearchVO> parameter) throws Exception {
		List<SaSalesOrder> list = salesStatsService.getGenderListForExcel(getSearchVO(parameter));

		ExcelValue excelValue = ExcelValue.builder(3, 0)
				.columnNames(Arrays.asList("hourTitle", "orderQtyAll", "prdtNormalAmtAll", "orderAmtAll",
						"orderQty10000", "prdtNormalAmt10000", "orderAmt10000", "orderQty10001", "prdtNormalAmt10001",
						"orderAmt10001", "orderQty10002", "prdtNormalAmt10002", "orderAmt10002", "orderQty10003",
						"prdtNormalAmt10003", "orderAmt10003", "orderQty10004", "prdtNormalAmt10004", "orderAmt10004",
						"orderQty10005", "prdtNormalAmt10005", "orderAmt10005"))
				.data(list).build();

		parameter.downloadExcelTemplate("stats/sales/excel/genderTypeSalesStats", excelValue);
	}

	/**
	 * @Desc : 상품별 매출통계 엑셀 다운로드
	 * @Method Name : downloadPrdtTypeExcel
	 * @Date : 2019. 7. 15.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-prdtTypeExcel")
	public void downloadPrdtTypeExcel(Parameter<SalesStatsSearchVO> parameter) throws Exception {
		List<SaSalesOrder> list = salesStatsService.getPrdtListForExcel(getSearchVO(parameter));

		ExcelValue excelValue = ExcelValue.builder(3, 0)
				.columnNames(Arrays.asList("brandNo", "siteName", "brandName", "stdCtgrName", "styleInfo",
						"prdtColorName", "prdtNo", "prdtName", "orderQtyAll", "prdtNormalAmtAll", "orderAmtAll",
						"orderQty10000", "prdtNormalAmt10000", "orderAmt10000", "orderQty10001", "prdtNormalAmt10001",
						"orderAmt10001", "orderQty10002", "prdtNormalAmt10002", "orderAmt10002", "orderQty10003",
						"prdtNormalAmt10003", "orderAmt10003"))
				.data(list).build();

		parameter.downloadExcelTemplate("stats/sales/excel/prdtTypeSalesStats", excelValue);
	}

	/**
	 * @Desc : 입점사 매출통계 엑셀 다운로드
	 * @Method Name : downloadVendorTypeExcel
	 * @Date : 2019. 7. 16.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-vendorTypeExcel")
	public void downloadVendorTypeExcel(Parameter<SalesStatsSearchVO> parameter) throws Exception {
		List<SaSalesOrder> list = salesStatsService.getVendorListForExcel(getSearchVO(parameter));

		ExcelValue excelValue = ExcelValue.builder(2, 0)
				.columnNames(Arrays.asList("vndrName", "vndrNo", "orderQtyAll", "prdtNormalAmtAll", "orderAmtAll",
						"orderQty10000", "prdtNormalAmt10000", "orderAmt10000", "orderQty10001", "prdtNormalAmt10001",
						"orderAmt10001", "orderQty10002", "prdtNormalAmt10002", "orderAmt10002"))
				.data(list).build();

		parameter.downloadExcelTemplate("stats/sales/excel/vendorTypeSalesStats", excelValue);
	}

	/**
	 * @Desc : 입점사 매출통계 엑셀 다운로드
	 * @Method Name : downloadVendorPopExcel
	 * @Date : 2019. 7. 16.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-vendorPopExcel")
	public void downloadVendorPopExcel(Parameter<SalesStatsSearchVO> parameter) throws Exception {
		List<SaSalesOrder> list = salesStatsService.getVendorPopListForExcel(getSearchVO(parameter));

		ExcelValue excelValue = ExcelValue
				.builder(2, 0).columnNames(Arrays.asList("brandNo", "brandName", "stdCtgrName", "styleInfo",
						"prdtColorCode", "prdtNoName", "orderQtyAll", "prdtNormalAmtAll", "orderAmtAll"))
				.data(list).build();

		parameter.downloadExcelTemplate("stats/sales/excel/vendorDetailSalesStats", excelValue);
	}

	/**
	 * @Desc : 중분류 카테고리 목록 조회
	 * @Method Name : getMidCategory
	 * @Date : 2019. 7. 15.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/get-mid-category")
	public void getMidCategory(Parameter<?> parameter) throws Exception {
		String ctgrNo = parameter.getString("ctgrNo");

		writeJson(parameter, salesStatsService.getMidCategoryList(ctgrNo));
	}

	/**
	 * @Desc : 소분류 카테고리 목록 조회
	 * @Method Name : getSmallCategory
	 * @Date : 2019. 7. 15.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/get-small-category")
	public void getSmallCategory(Parameter<?> parameter) throws Exception {
		String ctgrNo = parameter.getString("ctgrNo");

		writeJson(parameter, salesStatsService.getSmallCategoryList(ctgrNo));
	}

	/**
	 * @Desc : bo, po 구분후 검색조건VO 가져오기.
	 * @Method Name : getIsAdmin
	 * @Date : 2019. 10. 14.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	public SalesStatsSearchVO getSearchVO(Parameter<SalesStatsSearchVO> parameter) throws Exception {
		SalesStatsSearchVO salesStatsSearchVO = parameter.get();
		// bo, po 권한 구분
		UserDetails user = LoginManager.getUserDetails();
		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;

		if (!isAdmin) {
			VdVendor vndr = new VdVendor();
			vndr.setVndrNo(user.getVndrNo());
			vndr = vendorService.getVendorBaseInfo(vndr);
			salesStatsSearchVO.setVndrNo(vndr.getVndrNo());
		}

		return salesStatsSearchVO;
	}

}
