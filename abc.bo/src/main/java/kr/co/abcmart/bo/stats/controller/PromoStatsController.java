package kr.co.abcmart.bo.stats.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
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

import kr.co.abcmart.bo.stats.model.master.SaCouponStats;
import kr.co.abcmart.bo.stats.model.master.SaPromotionPlanStatus;
import kr.co.abcmart.bo.stats.service.OrderRealTimeStatsService;
import kr.co.abcmart.bo.stats.service.PromoStatsService;
import kr.co.abcmart.bo.stats.vo.CouponStatsSearchVO;
import kr.co.abcmart.bo.stats.vo.PromotionStatsSearchVO;
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
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/stats/promotion")
public class PromoStatsController extends BaseController {

	@Autowired
	private OrderRealTimeStatsService orderRealTimeService;

	@Autowired
	private PromoStatsService promoStatsService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private SiteService siteService;

	private final String[] ADMIN_COLUMN = { "orderQty10000", "prdtNormalAmt10000", "sellAmt10000", "orderQty10001",
			"prdtNormalAmt10001", "sellAmt10001", "orderQty10002", "prdtNormalAmt10002", "sellAmt10002" };

	private final String[] VENDOR_COLUMN = { "orderQty10003", "prdtNormalAmt10003", "sellAmt10003" };

	/**
	 * @Desc : 다족구매 족수별 통계
	 * @Method Name :
	 * @Date : 2019. 7. 12.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/purchases-multi-shoe")
	public ModelAndView purchasesAshoeMain(Parameter<?> parameter) throws Exception {
		orderRealTimeService.getStatusSearchCondition(parameter);

		promoStatsService.getToday(parameter);

		return forward("/stats/promotion/purchases-multi-shoe");
	}

	/**
	 * @Desc : 다족구매 족수별 통계 조회
	 * @Method Name : shoeReadList
	 * @Date : 2019. 7. 29.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/shoe-read-list")
	public void shoeReadList(Parameter<PromotionStatsSearchVO> parameter) throws Exception {
		Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable = new Pageable<>(parameter);

		// bo, po 권한 구분
		UserDetails user = LoginManager.getUserDetails();
		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;

		parameter.addAttribute("isAdmin", isAdmin);
		if (!isAdmin) {
			VdVendor vndr = new VdVendor();
			vndr.setVndrNo(user.getVndrNo());
			vndr = vendorService.getVendorBaseInfo(vndr);

			pageable.getBean().setVndrNo(vndr.getVndrNo());
		}

		Page<SaPromotionPlanStatus> page = promoStatsService.getMultiShoeList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 다족구매 족수별 통계 엑셀 다운로드
	 * @Method Name : shoeExcelDown
	 * @Date : 2019. 7. 29.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/shoe-excel-down")
	public void shoeExcelDown(Parameter<PromotionStatsSearchVO> parameter) throws Exception {
		Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable = new Pageable<>(parameter);
		pageable.setRowsPerPage(600000);
		// bo, po 권한 구분
		UserDetails user = LoginManager.getUserDetails();
		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP);

		if (!isAdmin) {
			VdVendor vndr = new VdVendor();
			vndr.setVndrNo(user.getVndrNo());
			vndr = vendorService.getVendorBaseInfo(vndr);

			pageable.getBean().setVndrNo(vndr.getVndrNo());
		}
		Page<SaPromotionPlanStatus> page = promoStatsService.getMultiShoeList(pageable);

		ExcelValue excelValue = ExcelValue.builder(2, 0)
				.columnNames(Arrays.asList("hourTitle", "orderQtyAll", "dscntAmtAll", "pymntAmtAll", "twoQty",
						"twoDscnt", "twoPymnt", "threeQty", "threeDscnt", "threePymnt", "fourQty", "fourDscnt",
						"fourPymnt", "fiveQty", "fiveDscnt", "fivePymnt", "sixMoreQty", "sixMoreDscnt", "sixMorePymnt"))
				.data(page.getContent()).build();

		parameter.downloadExcelTemplate("stats/promotion/excel/promotionMultiShoeStats", excelValue);
	}

	/**
	 * @Desc : 기획전 현황 통계 화면
	 * @Method Name : readPlanningStatus
	 * @Date : 2019. 7. 11.
	 * @Author : bje0507
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/planning-main")
	public ModelAndView readPlanningStatus(Parameter<?> parameter) throws Exception {
		String[] codeFields = { CommonCode.DEVICE_CODE, "PLNDP_TYPE_CODE" };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		parameter.addAttribute("searchConditionSiteList", this.siteService.getSiteList()); // 사이트 정보
		parameter.addAttribute("codeList", pair.getSecond());
		parameter.addAttribute("isAdmin",
				UtilsText.equals(LoginManager.getUserDetails().getUpAuthNo(), Const.ROLE_ADMIN_GROUP));

		return forward("/stats/promotion/planning-main");
	}

	/**
	 * @Desc : 기획전 현황 조회
	 * @Method Name : statsSearch
	 * @Date : 2019. 7. 15.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/stats-planning-search")
	public void statsSearch(Parameter<PromotionStatsSearchVO> parameter) throws Exception {

		Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable = new Pageable<>(parameter);

		// 입점사인 경우 업체번호 설정
		UserDetails user = LoginManager.getUserDetails();
		if (!UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP)) {
			PromotionStatsSearchVO bean = pageable.getBean();
			bean.setVndrNo(user.getVndrNo());
			pageable.setBean(bean);
		}

		Page<SaPromotionPlanStatus> page = promoStatsService.getPlanningList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 기획전 번호별 상품 판매현황 조회 (popup)
	 * @Method Name : productPopup
	 * @Date : 2019. 7. 17.
	 * @Author : bje0507
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/planning-main/promotion-product-pop")
	public ModelAndView productPopup(Parameter<?> parameter) throws Exception {
		Date currentTime = UtilsDate.getSqlTimeStamp();

		parameter.addAttribute("currentTime", currentTime);
		
		parameter.addAttribute("planningId", parameter.getString("planningId"));
		parameter.addAttribute("planningName", parameter.getString("planningName"));
		parameter.addAttribute("sellDate", parameter.getString("sellDate"));
		parameter.addAttribute("planningDate", parameter.getString("planningDate"));
		parameter.addAttribute("siteType", parameter.getString("siteType"));
		parameter.addAttribute("deviceType", parameter.getString("deviceType"));

		return forward("/stats/promotion/promotion-product-pop");
	}

	/**
	 * @Desc : 기획전 번호별 상품 판매현황 조회
	 * @Method Name : statsSearch
	 * @Date : 2019. 7. 15.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/stats-planning-search-product")
	public void statsSearchProduct(Parameter<PromotionStatsSearchVO> parameter) throws Exception {

		Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable = new Pageable<>(parameter);

		// 입점사인 경우 업체번호 설정
		UserDetails user = LoginManager.getUserDetails();
		if (!UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP)) {
			PromotionStatsSearchVO bean = pageable.getBean();
			bean.setVndrNo(user.getVndrNo());
			pageable.setBean(bean);
		}

		Page<SaPromotionPlanStatus> page = promoStatsService.getPlanningProductList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 기획전 통계 엑셀
	 * @Method Name : downloadPlanningExcel
	 * @Date : 2019. 7. 18.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-planningExcel")
	public void downloadPlanningExcel(Parameter<PromotionStatsSearchVO> parameter) throws Exception {
		Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable = new Pageable<>(parameter);
		PromotionStatsSearchVO bean = pageable.getBean();
		bean.setIsExcel("Y");
		pageable.setBean(bean);

		// 입점사인 경우 업체번호 설정
		UserDetails user = LoginManager.getUserDetails();
		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP);
		if (!isAdmin) {
			bean.setVndrNo(user.getVndrNo());
			pageable.setBean(bean);
		}

		Page<SaPromotionPlanStatus> page = promoStatsService.getPlanningList(pageable);

		List<String> columnNames = new ArrayList<String>();
		columnNames.addAll(Arrays.asList("planningId", "planningName", "planningType", "orderQtyAll",
				"prdtNormalAmtAll", "sellAmtAll"));
		if (isAdmin) {
			Collections.addAll(columnNames, ADMIN_COLUMN);
		}
		Collections.addAll(columnNames, VENDOR_COLUMN);

		ExcelValue excelValue = ExcelValue.builder(2, 0).columnNames(columnNames).data(page.getContent()).build();

		parameter.downloadExcelTemplate("stats/promotion/excel/planningStats", excelValue);
	}

	/**
	 * @Desc : 기획전 통계 popup 엑셀
	 * @Method Name : downloadPlanningPdExcel
	 * @Date : 2019. 7. 18.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-planningPdExcel")
	public void downloadPlanningPdExcel(Parameter<PromotionStatsSearchVO> parameter) throws Exception {
		Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable = new Pageable<>(parameter);
		PromotionStatsSearchVO bean = pageable.getBean();
		bean.setIsExcel("Y");
		pageable.setBean(bean);

		// 입점사인 경우 업체번호 설정
		UserDetails user = LoginManager.getUserDetails();
		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP);
		if (!isAdmin) {
			bean.setVndrNo(user.getVndrNo());
			pageable.setBean(bean);
		}

		Page<SaPromotionPlanStatus> page = promoStatsService.getPlanningProductList(pageable);

		List<String> columnNames = new ArrayList<String>();
		columnNames.addAll(Arrays.asList("selectSeq", "brandName", "ctgrName", "styleInfo", "prdtColorInfo",
				"prdtNoName", "orderQtyAll", "prdtNormalAmtAll", "sellAmtAll"));
		if (isAdmin) {
			Collections.addAll(columnNames, ADMIN_COLUMN);
		}
		Collections.addAll(columnNames, VENDOR_COLUMN);

		ExcelValue excelValue = ExcelValue.builder(2, 0).columnNames(columnNames).data(page.getContent()).build();

		parameter.downloadExcelTemplate("stats/promotion/excel/planningPdStats", excelValue);
	}

	/**
	 * @Desc : 프로모션별 현황 통계 화면
	 * @Method Name : readPromotionStatus
	 * @Date : 2019. 7. 16.
	 * @Author : bje0507
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/promotion-main")
	public ModelAndView readPromotionStatus(Parameter<?> parameter) throws Exception {
		String[] codeFields = { CommonCode.DEVICE_CODE, CommonCode.PROMO_TYPE_CODE, "PLNDP_TYPE_CODE" };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		parameter.addAttribute("codeList", pair.getSecond());
		parameter.addAttribute("searchConditionSiteList", this.siteService.getSiteList()); // 사이트 정보
		parameter.addAttribute("isAdmin",
				UtilsText.equals(LoginManager.getUserDetails().getUpAuthNo(), Const.ROLE_ADMIN_GROUP));

		return forward("/stats/promotion/promotion-main");
	}

	/**
	 * @Desc : 프로모션별 현황 조회
	 * @Method Name : statsPromotionSearch
	 * @Date : 2019. 7. 16.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/stats-promotion-search")
	public void statsPromotionSearch(Parameter<PromotionStatsSearchVO> parameter) throws Exception {

		Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable = new Pageable<>(parameter);

		// 입점사인 경우 업체번호 설정
		UserDetails user = LoginManager.getUserDetails();
		if (!UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP)) {
			PromotionStatsSearchVO bean = pageable.getBean();
			bean.setVndrNo(user.getVndrNo());
			pageable.setBean(bean);
		}

		Page<SaPromotionPlanStatus> page = promoStatsService.getPromotionList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 프로모션 번호별 상품 판매현황 조회 (popup)
	 * @Method Name : promoProductPopup
	 * @Date : 2019. 7. 22.
	 * @Author : bje0507
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/promotion-main/promotion-product-pop")
	public ModelAndView promoProductPopup(Parameter<?> parameter) throws Exception {
		Date currentTime = UtilsDate.getSqlTimeStamp();

		parameter.addAttribute("currentTime", currentTime);

		parameter.addAttribute("promoName", parameter.getString("promoName"));
		parameter.addAttribute("promoNo", parameter.getString("promoNo"));
		parameter.addAttribute("sellDate", parameter.getString("sellDate"));
		parameter.addAttribute("planningDate", parameter.getString("planningDate"));
		parameter.addAttribute("siteType", parameter.getString("siteType"));
		parameter.addAttribute("deviceType", parameter.getString("deviceType"));
		parameter.addAttribute("promoType", parameter.getString("promoType"));

		return forward("/stats/promotion/promotion-product-pop");
	}

	/**
	 * @Desc : 프로모션 번호별 상품 판매현황 조회
	 * @Method Name : statsPromoSearchProduct
	 * @Date : 2019. 7. 15.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/stats-promotion-search-product")
	public void statsPromoSearchProduct(Parameter<PromotionStatsSearchVO> parameter) throws Exception {

		Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable = new Pageable<>(parameter);

		// 입점사인 경우 업체번호 설정
		UserDetails user = LoginManager.getUserDetails();
		if (!UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP)) {
			PromotionStatsSearchVO bean = pageable.getBean();
			bean.setVndrNo(user.getVndrNo());
			pageable.setBean(bean);
		}

		Page<SaPromotionPlanStatus> page = promoStatsService.getPromotionProductList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 프로모션 통계 popup 엑셀
	 * @Method Name : downloadPromotionPdExcel
	 * @Date : 2019. 7. 18.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-promotionPdExcel")
	public void downloadPromotionPdExcel(Parameter<PromotionStatsSearchVO> parameter) throws Exception {
		Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable = new Pageable<>(parameter);
		PromotionStatsSearchVO bean = pageable.getBean();
		bean.setIsExcel("Y");
		pageable.setBean(bean);

		// 입점사인 경우 업체번호 설정
		UserDetails user = LoginManager.getUserDetails();
		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP);
		if (!isAdmin) {
			bean.setVndrNo(user.getVndrNo());
			pageable.setBean(bean);
		}

		Page<SaPromotionPlanStatus> page = promoStatsService.getPromotionProductList(pageable);

		List<String> columnNames = new ArrayList<String>();
		columnNames.addAll(Arrays.asList("selectSeq", "brandName", "ctgrName", "styleInfo", "prdtColorInfo",
				"prdtNoName", "orderQtyAll", "prdtNormalAmtAll", "sellAmtAll"));
		if (isAdmin) {
			Collections.addAll(columnNames, ADMIN_COLUMN);
		}
		Collections.addAll(columnNames, VENDOR_COLUMN);

		ExcelValue excelValue = ExcelValue.builder(2, 0).columnNames(columnNames).data(page.getContent()).build();

		parameter.downloadExcelTemplate("stats/promotion/excel/promotionPdStats", excelValue);
	}

	/**
	 * @Desc : 프로모션 통계 엑셀
	 * @Method Name : downloadPromotionExcel
	 * @Date : 2019. 7. 18.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-promotionExcel")
	public void downloadPromotionExcel(Parameter<PromotionStatsSearchVO> parameter) throws Exception {
		Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable = new Pageable<>(parameter);
		PromotionStatsSearchVO bean = pageable.getBean();
		bean.setIsExcel("Y");
		pageable.setBean(bean);

		// 입점사인 경우 업체번호 설정
		UserDetails user = LoginManager.getUserDetails();
		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP);
		if (!isAdmin) {
			bean.setVndrNo(user.getVndrNo());
			pageable.setBean(bean);
		}

		Page<SaPromotionPlanStatus> page = promoStatsService.getPromotionList(pageable);

		List<String> columnNames = new ArrayList<String>();
		columnNames.addAll(
				Arrays.asList("promoNo", "promoName", "promoType", "orderQtyAll", "prdtNormalAmtAll", "sellAmtAll"));
		if (isAdmin) {
			Collections.addAll(columnNames, ADMIN_COLUMN);
		}
		Collections.addAll(columnNames, VENDOR_COLUMN);

		ExcelValue excelValue = ExcelValue.builder(2, 0).columnNames(columnNames).data(page.getContent()).build();

		parameter.downloadExcelTemplate("stats/promotion/excel/promotionStats", excelValue);
	}

	/**
	 * @Desc : 쿠폰 현황 통계
	 * @Method Name : couponStatsMain
	 * @Date : 2019. 7. 17.
	 * @Author : 백인천
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/coupon-main")
	public ModelAndView couponStatsMain(Parameter<?> parameter) throws Exception {
		String[] codeFields = { CommonCode.DEVICE_CODE, "CPN_TYPE_CODE" };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		parameter.addAttribute("cpnSrchCodeList", pair.getSecond());
		return forward("/stats/promotion/coupon-main");
	}

	/**
	 * @Desc : 쿠폰현황 리스트 조회
	 * @Method Name : couponStatsSearch
	 * @Date : 2019. 7. 17.
	 * @Author : 백인천
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/coupon-list")
	public void couponStatsSearch(Parameter<CouponStatsSearchVO> parameter) throws Exception {

		Pageable<CouponStatsSearchVO, SaCouponStats> pageable = new Pageable<>(parameter);

		// 입점사인 경우 업체번호 설정
		UserDetails user = LoginManager.getUserDetails();
		if (!UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP)) {
			CouponStatsSearchVO bean = pageable.getBean();
			bean.setVndrNo(user.getVndrNo());
			pageable.setBean(bean);
		}

		Page<SaCouponStats> page = promoStatsService.getCouponStatsList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 쿠폰현황 리스트 엑셀다운로드
	 * @Method Name : downloadCouponStatsExcel
	 * @Date : 2019. 7. 19.
	 * @Author : 백인천
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-couponStatsExcel")
	public void downloadCouponStatsExcel(Parameter<CouponStatsSearchVO> parameter) throws Exception {
		CouponStatsSearchVO bean = parameter.get();

		// 입점사인 경우 업체번호 설정
		UserDetails user = LoginManager.getUserDetails();
		if (!UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP)) {
			bean.setVndrNo(user.getVndrNo());
		}

		List<SaCouponStats> list = promoStatsService.getCouponStatsListForExcel(bean);

		ExcelValue excelValue = ExcelValue.builder(2, 0)
				.columnNames(Arrays.asList("cpnNo", "dispYn", "cpnUseGbnType", "normalCpnYn", "cpnName", "cpnCrtType",
						"usePlaceGbnType", "cpnTypeCode", "dscntValue", "validDtm", "totalIssueCount", "useCount",
						"useOnlineCount", "useOfflineCount", "devicePcCount", "deviceMoCount", "deviceAppCount"))
				.data(list).build();

		parameter.downloadExcelTemplate("stats/promotion/excel/couponStats", excelValue);
	}

}
