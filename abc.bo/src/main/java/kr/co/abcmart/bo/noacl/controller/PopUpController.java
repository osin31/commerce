package kr.co.abcmart.bo.noacl.controller;

import java.util.ArrayList;
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

import kr.co.abcmart.bo.display.model.master.CmArea;
import kr.co.abcmart.bo.display.model.master.DpCategory;
import kr.co.abcmart.bo.display.service.DisplayCategoryService;
import kr.co.abcmart.bo.display.service.StoreService;
import kr.co.abcmart.bo.product.model.master.DpBrand;
import kr.co.abcmart.bo.product.model.master.DpBrandVisual;
import kr.co.abcmart.bo.product.model.master.DpSizeChart;
import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.SyHandlingPrecaution;
import kr.co.abcmart.bo.product.model.master.SyStandardCategory;
import kr.co.abcmart.bo.product.service.BrandService;
import kr.co.abcmart.bo.product.service.BrandVisualService;
import kr.co.abcmart.bo.product.service.ProductIconService;
import kr.co.abcmart.bo.product.service.ProductInfoNoticeService;
import kr.co.abcmart.bo.product.service.ProductInsideAddInfoService;
import kr.co.abcmart.bo.product.service.ProductInsideColorService;
import kr.co.abcmart.bo.product.service.ProductInsideDetailService;
import kr.co.abcmart.bo.product.service.ProductInsideIconService;
import kr.co.abcmart.bo.product.service.ProductInsideMemoService;
import kr.co.abcmart.bo.product.service.ProductInsidePriceHistoryService;
import kr.co.abcmart.bo.product.service.ProductInsideRelationFileService;
import kr.co.abcmart.bo.product.service.ProductService;
import kr.co.abcmart.bo.product.service.SizeChartService;
import kr.co.abcmart.bo.product.service.StandardCategoryService;
import kr.co.abcmart.bo.product.vo.DpBrandSearchVO;
import kr.co.abcmart.bo.product.vo.PdProductSearchVO;
import kr.co.abcmart.bo.promotion.model.master.PrCoupon;
import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplayCornerProduct;
import kr.co.abcmart.bo.promotion.model.master.PrPromotion;
import kr.co.abcmart.bo.promotion.service.CouponService;
import kr.co.abcmart.bo.promotion.service.PlanningDisplayService;
import kr.co.abcmart.bo.promotion.service.PromotionService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.bo.vendor.model.master.VdVendorDisplayChnnl;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("noacl")
public class PopUpController extends BaseController {

	@Autowired
	private BrandService brandService;
	@Autowired
	private BrandVisualService brandVisualService;

	@Autowired
	private ProductService productService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private StandardCategoryService standardCategoryService;

	@Autowired
	private DisplayCategoryService displayCategoryService;

	@Autowired
	private SizeChartService sizeChartService;

	@Autowired
	private ProductInfoNoticeService productInfoNoticeService;

	@Autowired
	private PlanningDisplayService planningDisplayService;

	@Autowired
	private ProductInsideDetailService productInsideDetailService;
	@Autowired
	private ProductInsideColorService productInsideColorService;
	@Autowired
	private ProductInsideIconService productInsideIconService;
	@Autowired
	private ProductInsideAddInfoService productInsideAddInfoService;
	@Autowired
	private ProductInsidePriceHistoryService productInsidePriceHistoryService;
	@Autowired
	private ProductInsideMemoService productInsideMemoService;
	@Autowired
	private ProductInsideRelationFileService productInsideRelationFileService;
	@Autowired
	private ProductIconService productIconService;

	@Autowired
	private PromotionService promotionService;
	@Autowired
	private CouponService couponService;

	@Autowired
	private VendorService vendorService;

	@GetMapping("/common-popup-list")
	public ModelAndView commonPopupList() throws Exception {
		return forward("/main/common-popup-list");
	}

	/**
	 * @Desc : 브랜드 찾기 팝업 화면
	 * @Method Name : findBrand
	 * @Date : 2019. 5. 13.
	 * @Author : tennessee
	 * @return
	 * @throws Exception
	 */
	@GetMapping("brand/find")
	public ModelAndView goToFindBrand(Parameter<?> parameter) throws Exception {
		log.debug("브랜드 목록 페이지");

		String joinedSiteAll = this.siteService.getSiteList().stream().map(SySite::getSiteName)
				.collect(Collectors.joining(","));

		String joinedSiteNames = this.siteService.getSiteList().stream().map(SySite::getSiteName)
				.collect(Collectors.joining("|"));
		String joinedSiteNos = this.siteService.getSiteList().stream().map(SySite::getSiteNo)
				.collect(Collectors.joining("|"));

		parameter.addAttribute("siteGubunText", String.join("|", joinedSiteAll, joinedSiteNames));
		parameter.addAttribute("siteGubunCode", String.join("|", "", joinedSiteNos));

		parameter.addAttribute("searchConditionSiteList", this.siteService.getSiteList()); // 사이트 정보

		return forward("/product/brand/brand-popup-find");
	}

	/**
	 * @Desc : 브랜드 찾기 목록 조회 서비스
	 * @Method Name : findBrand
	 * @Date : 2019. 5. 13.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("brand/find")
	public void findBrand(Parameter<DpBrandSearchVO> parameter) throws Exception {
		log.debug("브랜드 목록 조회");
		Pageable<DpBrandSearchVO, DpBrand> pageable = new Pageable<DpBrandSearchVO, DpBrand>(parameter);
		Page<DpBrand> brandList = this.brandService.searchBrandList(pageable);

		this.writeJson(parameter, brandList.getGrid());
	}

	/**
	 * @Desc : 상품 찾기 팝업 화면
	 * @Method Name : findProductListPage
	 * @Date : 2019. 5. 13.
	 * @Author : tennessee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("product/find")
	public ModelAndView findProductListPage(Parameter<?> parameter) throws Exception {
		log.debug("상품 목록 조회");

		// 화면에 사용되는 공통코드 조회
		String[] codeFields = { CommonCode.SELL_STAT_CODE, CommonCode.GENDER_GBN_CODE, CommonCode.PRDT_TYPE_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
				.getCodeListByGroupByCombo(codeFields, false);

		parameter.addAttribute("gridCodes", pair.getFirst()); // 그리드 공통코드. 판매상태

		parameter.addAttribute("userAuthority", LoginManager.getUserDetails().getAuthApplySystemType()); // 사용자 권한 조회
		parameter.addAttribute("searchConditionSiteList", this.siteService.getSiteList()); // 사이트 정보

		String[] codeFields1 = { CommonCode.TIER_FLAG_CODE };
		Map<String, List<SyCodeDetail>> codeList = commonCodeService.getAllCodeListByGroup(codeFields1);
		List<SyCodeDetail> tierFlagCodeList = codeList.get(codeFields1[0]);

		String codeName = tierFlagCodeList.stream().map(SyCodeDetail::getCodeDtlName).collect(Collectors.joining("|"));
		String codeAddInfo1 = tierFlagCodeList.stream().map(SyCodeDetail::getAddInfo1).collect(Collectors.joining("|"));

		JSONObject json = new JSONObject();
		json.put("name", codeName);
		json.put("addInfo1", codeAddInfo1);

		parameter.addAttribute("tierFlagCodeCombo", json);
		parameter.addAttribute("tierFlagCodeList", tierFlagCodeList);

		if (UtilsText.equals(Const.AUTH_APPLY_SYSTEM_BO, LoginManager.getUserDetails().getAuthApplySystemType())) {
			// BO권한
			parameter.addAttribute("searchConditionSiteChannelList", this.siteService.getUseChannelList()); // 채널 코드
		} else {
			// PO권한
			VdVendorDisplayChnnl criteria = new VdVendorDisplayChnnl();
			criteria.setVndrNo(LoginManager.getUserDetails().getVndrNo());
			parameter.addAttribute("searchConditionSiteChannelList",
					this.vendorService.selectVendorDisplayChnnlList(criteria)); // 채널 코드
		}

		parameter.addAttribute("searchConditionSellStatCodeList", pair.getSecond().get(CommonCode.SELL_STAT_CODE)); // 판매상태코드
		parameter.addAttribute("searchConditionGenderGbnCodeList", pair.getSecond().get(CommonCode.GENDER_GBN_CODE)); // 성별구분코드
		parameter.addAttribute("searchConditionPrdtTypeCodeList", pair.getSecond().get(CommonCode.PRDT_TYPE_CODE)); // 상품유형코드

		return forward("/product/product/product-popup-find");
	}

	/**
	 * @Desc : 상품 찾기 목록 조회 서비스
	 * @Method Name : findProduct
	 * @Date : 2019. 5. 13.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("product/find")
	public void findProduct(Parameter<PdProductSearchVO> parameter) throws Exception {
		log.debug("상품 목록 조회");
		Pageable<PdProductSearchVO, PdProduct> pageable = new Pageable<PdProductSearchVO, PdProduct>(parameter);
		pageable.getBean().organizeParameters();
		Page<PdProduct> productList = this.productService.searchProduct(pageable);

		this.writeJson(parameter, productList.getGrid());
	}

	/**
	 * @Desc : 오프라인 매장 검색 페이지
	 * @Method Name : findStore
	 * @Date : 2019. 11. 8.
	 * @Author : 이가영
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("store/find")
	public ModelAndView findStore(Parameter<?> parameter) throws Exception {
		log.debug("오프라인 매장 검색 페이지");

		List<SySite> siteList = siteService.getSiteList();
		List<CmArea> areaList = storeService.getCmAreaList();

		String[] codeFields = { CommonCode.STORE_TYPE_CODE, CommonCode.STORE_SERVICE_CODE };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();

		parameter.addAttribute("codeCombo", pair.getFirst());

		parameter.addAttribute("siteList", siteList);
		parameter.addAttribute("areaList", areaList);
		parameter.addAttribute("storeTypeCodeList", codeList.get(CommonCode.STORE_TYPE_CODE));
		parameter.addAttribute("storeServiceCodeList", codeList.get(CommonCode.STORE_SERVICE_CODE));

		return forward("/display/store/store-find-popup");
	}

	/**
	 * @Desc : 선택 일괄답변
	 * @Method Name : aswr
	 * @Date : 2019. 4. 8.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("review/aswr")
	public ModelAndView aswr(Parameter<SyCodeDetail> parameter) throws Exception {
		log.debug("선택 일괄답변 팝업");

		List<SyCodeDetail> codeSearchCondition = new ArrayList<SyCodeDetail>();
		// 상담유형 코드
		SyCodeDetail syCodeDetail = new SyCodeDetail();
		syCodeDetail.setCodeField(CommonCode.CNSL_TYPE_CODE);
		codeSearchCondition.add(syCodeDetail);
		Map<String, List<SyCodeDetail>> codeListMap = commonCodeService.getCodeListByGroup(codeSearchCondition);

		parameter.addAttribute("cnslTypeCode", codeListMap.get(CommonCode.CNSL_TYPE_CODE)); // 상담유형

		return forward("/product/review/review-list-popup");
	}

	/**
	 * @Desc : 하위 표준 카테고리 리스트 조회
	 * @Method Name : getChildStdCategory
	 * @Date : 2019. 3. 21.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("product/category-standard/list/path")
	@ResponseBody
	public void getChildStdCategory(Parameter<SyStandardCategory> parameter) throws Exception {

		SyStandardCategory syStandardCategory = parameter.get();

		if (UtilsText.isNotEmpty(syStandardCategory.getVndrNo())
				&& (UtilsText.equals("VD10000001", syStandardCategory.getVndrNo())
						|| UtilsText.equals("VD10000002", syStandardCategory.getVndrNo())
						|| UtilsText.equals("VD10000003", syStandardCategory.getVndrNo()))) {
			syStandardCategory.setVndrNo(null);
		}

		// 사용 중인 표준 카테고리 조회
		syStandardCategory.setUseYn(Const.BOOLEAN_TRUE);

		List<SyStandardCategory> list = standardCategoryService.getStandardCategoryList(syStandardCategory);

		writeJson(parameter, list);
	}

	/**
	 * @Desc : 하위 전시 카테고리 리스트 조회
	 * @Method Name : getCtgrList
	 * @Date : 2019. 3. 21.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("display/display-category/list")
	@ResponseBody
	public void getCtgrList(Parameter<DpCategory> parameter) throws Exception {

		DpCategory selected = parameter.get();
		selected.setUseYn(Const.BOOLEAN_TRUE);
		// 상품 등록시에는 해당 업체가 등록가능한 전시 카테고리만 조회 한다.
		if (UtilsText.isNotEmpty(parameter.getString("mmnyPrdtYn"))) {
			if (UtilsText.equals(Const.BOOLEAN_FALSE, parameter.getString("mmnyPrdtYn"))) {
				selected.setVndrNo(LoginManager.getUserDetails().getVndrNo());
				selected.setStdCtgrNo(parameter.get().getStdCtgrNo());
			} else {
				selected.setVndrNo("T");
				// 자사 상품 표준 카테고리 제약 조건 추가시 아래 소스 코드 제거 2020.02.06
				selected.setVndrNo(null);
				selected.setStdCtgrNo(null);
			}
		}
		selected = displayCategoryService.getDpCategory(selected);

//		log.error("selected : {}", selected);
//		log.error("selected.getLeafCtgrYn() : {}", selected.getLeafCtgrYn());

		if (selected != null && UtilsText.equals(selected.getLeafCtgrYn(), Const.BOOLEAN_TRUE)) {
			writeJson(parameter, selected);
		} else {
			parameter.get().setUseYn(Const.BOOLEAN_TRUE);
			parameter.get().setVndrNo(LoginManager.getUserDetails().getVndrNo());

			// 자사 상품 표준 카테고리 제약 조건 추가시 아래 소스 코드 제거 2020.02.06
			if (UtilsText.isNotEmpty(parameter.getString("mmnyPrdtYn"))) {
				parameter.get().setVndrNo(LoginManager.getUserDetails().getVndrNo());
			} else {
				parameter.get().setVndrNo(null);
				parameter.get().setStdCtgrNo(null);
			}
			writeJson(parameter, displayCategoryService.getDpCategoryList(parameter.get()));
		}

//		writeJson(parameter, displayCategoryService.getDpCategoryList(parameter.get()));
	}

	/**
	 * @Desc : 사이즈 조견표 검색
	 * @Method Name : getSizeChartForProduct
	 * @Date : 2019. 4. 22.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("product/sizechart/search")
	public void getSizeChartForProduct(Parameter<DpSizeChart> parameter) throws Exception {
		DpSizeChart criteria = parameter.get();
		SyStandardCategory rootStandardCategory = this.standardCategoryService
				.getRootByStdCtgrNo(criteria.getStdCtgrNo());
		criteria.setStdCtgrNo(rootStandardCategory.getStdCtgrNo());
		criteria.setUseYn(Const.BOOLEAN_TRUE);
		this.writeJson(parameter, this.sizeChartService.getSizeChartForProduct(criteria));
	}

	/**
	 * @Desc : 상품정보고시 리스트 조회
	 * @Method Name : getListByPrdtInfoNotcCode
	 * @Date : 2019. 3. 28.
	 * @Author : 이가영
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("product/info-notice/list/prdtInfoNotcCode")
	public void getListByPrdtInfoNotcCode(Parameter<?> parameter) throws Exception {
		log.debug("상품정보고시 리스트");

		String itemCode = parameter.getString("itemCode");
		String mmnyPrdtYn = parameter.getString("mmnyPrdtYn");
		String vndrNo = parameter.getString("vndrNo");

		this.writeJson(parameter, productInfoNoticeService.getUseCmProductInfoNoticeList(itemCode, mmnyPrdtYn, vndrNo));
	}

	/**
	 * @Desc : 대상상품관리 팝업 화면
	 * @Method Name : getTargetProductManagement
	 * @Date : 2019. 5. 15.
	 * @Author : tennessee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/product/management/product/target")
	public ModelAndView getTargetProductManagement(Parameter<?> parameter) throws Exception {
		log.debug("대상상품관리 팝업");
		// 화면에 사용되는 공통코드 조회
		String[] codeFields = { CommonCode.SELL_STAT_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
				.getCodeListByGroupByCombo(codeFields, false);

		parameter.addAttribute("gridCodes", pair.getFirst()); // 그리드 공통코드. 판매상태
		return forward("/product/product/popup/product-popup-targetmanagement");
	}

	/**
	 * @Desc : 취급주의사항 리스트 조회
	 * @Method Name : readHandlingPrecautionList
	 * @Date : 2019. 5. 14.
	 * @Author : 이가영
	 * @param parameter
	 */
	@GetMapping("/product/category/standard/handling-precaution")
	public void readHandlingPrecautionList(Parameter<SyHandlingPrecaution> parameter) throws Exception {
		SyHandlingPrecaution syHandlingPrecaution = parameter.get();
		List<SyHandlingPrecaution> syHandlingPrecautionList = standardCategoryService
				.getSyHandlingPrecautionById(syHandlingPrecaution.getStdCtgrNo());
		this.writeJson(parameter, syHandlingPrecautionList);
	}

	/**
	 * @Desc : 기획전 코너 대상 상품 리스트 조회
	 * @Method Name : searchPlanningDisplayProductManagementList
	 * @Date : 2019. 5. 17.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/promotion/planning-display/product-management/list")
	public void searchPlanningDisplayProductManagementList(Parameter<PrPlanningDisplayCornerProduct> parameter)
			throws Exception {

		Pageable<PrPlanningDisplayCornerProduct, PrPlanningDisplayCornerProduct> pageable = new Pageable<>(parameter);

		Page<PrPlanningDisplayCornerProduct> page = planningDisplayService
				.getPrPlanningDisplayCornerProductList(pageable);

		this.writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 기획전 코너 대상 상품 저장
	 * @Method Name : savePlanningDisplayProductManagementList
	 * @Date : 2019. 5. 17.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/promotion/planning-display/product-management/save")
	public void savePlanningDisplayProductManagementList(Parameter<PrPlanningDisplayCornerProduct[]> parameter)
			throws Exception {

		PrPlanningDisplayCornerProduct[] array = parameter.get();

		Map<String, Object> resultMap = new HashMap<>();

		try {
			planningDisplayService.setPlanningDisplayProductManagementList(array);

			resultMap.put("success", true);
			resultMap.put("Message", "저장되었습니다.");
		} catch (Exception e) {
			resultMap.put("success", false);
			resultMap.put("Message", "저장에 실패하였습니다.");
		}

		this.writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 상품 상세 보기전용 팝업 열기
	 * @Method Name : readProduct
	 * @Date : 2019. 11. 7.
	 * @Author : tennessee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/product/detail")
	public ModelAndView readProduct(Parameter<?> parameter) throws Exception {
		log.debug("상품 상세 보기전용 팝업");

		String prdtNo = parameter.getString("prdtNo"); // 온라인상품번호
		String siteNo = parameter.getString("siteNo"); // 사이트 번호
		String chnnlNo = parameter.getString("chnnlNo"); // 채널 번호
		String vndrPrdtNoText = parameter.getString("vndrPrdtNoText"); // ERP상품코드 또는 업체번호

		parameter.addAttribute("siteNo", siteNo);
		parameter.addAttribute("chnnlNo", chnnlNo);
		parameter.addAttribute("vndrPrdtNoText", vndrPrdtNoText);

		PdProduct product = this.productService.getProduct(prdtNo, siteNo, chnnlNo, vndrPrdtNoText);

		if (UtilsObject.isEmpty(product)) {
			throw new Exception("조회된 상품이 없습니다.");
		} else {
			// 온라인상품번호 유효
			siteNo = product.getSiteNo(); // 온라인 상품정보로 사이트번호 설정
			chnnlNo = product.getChnnlNo(); // 온라인 상품정보로 채널번호 설정
			vndrPrdtNoText = product.getVndrPrdtNoText(); // 온라인 상품정보로 내부관리정보 설정

			this.productInsideDetailService.setIntoProduct(product); // 상품 상세정보
			this.productInsideRelationFileService.setProductRelationFiles(product); // 상품 연관파일 설정
			this.productInsidePriceHistoryService.setProductPriceHistory(product); // 최근 가격정보 설정
			this.productInsideAddInfoService.setProductAddInfo(product); // 상품추가정보(정보제공고시) 조회
			this.productInsideColorService.setProductColor(product); // 상품 색상
			this.productInsideIconService.setProductIcon(product); // 상품 아이콘 설정
			this.displayCategoryService.getDpCategoryListByPrdtNo(product); // 전시 카테고리 목록 조회

			product.setBrandName(this.brandService.getBrandNameByBrandNo(product.getBrandNo())); // 브랜드 이름 설정

			// 화면에 부가적으로 필요한 정보 설정
			// 사용자 권한 조회
			parameter.addAttribute("userAuthority", LoginManager.getUserDetails().getUpAuthNo());
			// 채널조회
			parameter.addAttribute("channelList", this.siteService.getUseChannelList());
			// 메모
			parameter.addAttribute("memoList", this.productInsideMemoService.searchByPrdtNo(prdtNo));
			// 자사 채널별 상품정보 조회
			parameter.addAttribute("associatedProducts", this.productService.getProductInfoAboutEachChannel(product));
			// 사용 중인 아이콘 정보 조회
			parameter.addAttribute("iconList", this.productIconService.getUseIconList());
			parameter.addAttribute("product", product);
			parameter.addAttribute("type", PdProduct.TYPE_READ);

			// 화면에 사용되는 공통코드 조회
			String[] codeFields = { CommonCode.PRDT_TYPE_CODE, CommonCode.SELL_STAT_CODE, CommonCode.ORG_PLACE_CODE,
					CommonCode.PRDT_COLOR_CODE, CommonCode.STOCK_UN_INTGR_RSN_CODE, CommonCode.DEVICE_CODE,
					CommonCode.RLTN_PRDT_TYPE_CODE, CommonCode.PRDT_SAFE_TYPE_CODE, "GENDER_GBN_CODE" };
			Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
					.getCodeListByGroupByCombo(codeFields, false);
			parameter.addAttribute("gridCodes", pair.getFirst()); // 그리드 공통코드
			parameter.addAttribute("prdtTypeCodeList", pair.getSecond().get(CommonCode.PRDT_TYPE_CODE)); // 상품유형코드
			parameter.addAttribute("sellStatCodeList", pair.getSecond().get(CommonCode.SELL_STAT_CODE)); // 판매상태코드
			parameter.addAttribute("orgPlaceCodeList", pair.getSecond().get(CommonCode.ORG_PLACE_CODE)); // 제조국/원산지 코드
			parameter.addAttribute("prdtColorCodeList", pair.getSecond().get(CommonCode.PRDT_COLOR_CODE)); // 상품색상코드
			parameter.addAttribute("stockUnIntgrRsnCodeList", pair.getSecond().get(CommonCode.STOCK_UN_INTGR_RSN_CODE)); // 재입고미통합사유코드
			parameter.addAttribute("deviceCodeList", pair.getSecond().get(CommonCode.DEVICE_CODE)); // 장비코드
			parameter.addAttribute("rltnPrdtTypeCodeList", pair.getSecond().get(CommonCode.RLTN_PRDT_TYPE_CODE)); // 관련상품유형코드
			parameter.addAttribute("prdtSafeTypeCodeList", pair.getSecond().get(CommonCode.PRDT_SAFE_TYPE_CODE)); // 제품안전인증정보코드
			parameter.addAttribute("genderGbnCodeList", pair.getSecond().get(CommonCode.GENDER_GBN_CODE)); // 성별구분코드
		}

		return this.forward("/product/product/product-detail-popup");
	}

	/**
	 * @Desc : 최근 브랜드 비주얼 이미지 조회
	 * @Method Name : getLastestBrandVisual
	 * @Date : 2019. 7. 24.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/brand/visual")
	public void getLastestBrandVisual(Parameter<DpBrandVisual> parameter) throws Exception {
		this.writeJson(parameter, this.brandVisualService.getBrandVisualInfo(parameter.get()));
	}

	/**
	 * @Desc : 상품번호에 의한 프로모션 조회
	 * @Method Name : prdtNoByPromoList
	 * @Date : 2019. 11. 8.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/promotion/target/product/promotion/list")
	public void prdtNoByPromoList(Parameter<?> parameter) throws Exception {
		String prdtNo = parameter.getString("prdtNo");

		List<PrPromotion> prdtNoByPromoList = promotionService.getPrdtNoByPromoList(prdtNo);

		writeJson(parameter, prdtNoByPromoList);
	}

	/**
	 * @Desc : 상품번호에 의한 쿠폰 조회
	 * @Method Name : prdtNoByCpnList
	 * @Date : 2019. 11. 8.
	 * @Author : easyhun
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/coupon/target/product/coupon/list")
	public void prdtNoByCpnList(Parameter<?> parameter) throws Exception {
		String prdtNo = parameter.getString("prdtNo");

		List<PrCoupon> prdtNoByCpnList = couponService.getPrdtNoByCpnList(prdtNo);

		writeJson(parameter, prdtNoByCpnList);
	}

	/**
	 * @Desc : depth별 표준 카테고리 목록 조회 (인자값: 소카테고리 번호)
	 * @Method Name : readstdCtgr
	 * @Date : 2019. 3. 8.
	 * @Author : 이가영
	 * @param params
	 * @return
	 */
	@PostMapping("/product/category-standard/list/map")
	@ResponseBody
	public void readstdCtgr(Parameter<SyStandardCategory> parameter) throws Exception {

		String stdCtgrNo = parameter.get().getStdCtgrNo();
		Map<String, Object> resultData = new HashMap<>();

		if (stdCtgrNo != null) {

			resultData = standardCategoryService.getStandardCategoryMap(parameter.get());
		}

		writeJson(parameter, resultData);
	}

}
