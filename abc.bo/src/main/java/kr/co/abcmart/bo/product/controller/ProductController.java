package kr.co.abcmart.bo.product.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.display.service.DisplayCategoryService;
import kr.co.abcmart.bo.display.vo.DpExhibitionPageVO;
import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductApprovalHistory;
import kr.co.abcmart.bo.product.model.master.PdProductChangeHistory;
import kr.co.abcmart.bo.product.model.master.PdProductMapped;
import kr.co.abcmart.bo.product.model.master.PdProductMemo;
import kr.co.abcmart.bo.product.model.master.PdProductOption;
import kr.co.abcmart.bo.product.model.master.PdProductOptionStock;
import kr.co.abcmart.bo.product.model.master.PdProductPriceHistory;
import kr.co.abcmart.bo.product.model.master.PdRelationProduct;
import kr.co.abcmart.bo.product.service.BrandService;
import kr.co.abcmart.bo.product.service.ProductFileService;
import kr.co.abcmart.bo.product.service.ProductIconService;
import kr.co.abcmart.bo.product.service.ProductInsideAddInfoService;
import kr.co.abcmart.bo.product.service.ProductInsideApprovalHistoryService;
import kr.co.abcmart.bo.product.service.ProductInsideChangeHistoryService;
import kr.co.abcmart.bo.product.service.ProductInsideColorService;
import kr.co.abcmart.bo.product.service.ProductInsideDetailService;
import kr.co.abcmart.bo.product.service.ProductInsideIconService;
import kr.co.abcmart.bo.product.service.ProductInsideMemoService;
import kr.co.abcmart.bo.product.service.ProductInsideOptionService;
import kr.co.abcmart.bo.product.service.ProductInsidePriceHistoryService;
import kr.co.abcmart.bo.product.service.ProductInsideRelationFileService;
import kr.co.abcmart.bo.product.service.ProductInsideRelationProductService;
import kr.co.abcmart.bo.product.service.ProductInterfaceService;
import kr.co.abcmart.bo.product.service.ProductService;
import kr.co.abcmart.bo.product.vo.PdProductEditorUploadVO;
import kr.co.abcmart.bo.product.vo.PdProductMappingVO;
import kr.co.abcmart.bo.product.vo.PdProductOptionStockSearchVO;
import kr.co.abcmart.bo.product.vo.PdProductSearchVO;
import kr.co.abcmart.bo.product.vo.PdProductSkuExcelListVO;
import kr.co.abcmart.bo.promotion.service.PlanningDisplayService;
import kr.co.abcmart.bo.promotion.vo.PrExhibitionPlanningVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.bo.vendor.model.master.VdVendorDisplayChnnl;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.bo.vendor.vo.VendorOtherPartVo;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsCollection;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsDate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("product/product")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductInsideDetailService productInsideDetailService;
	@Autowired
	private ProductInsideColorService productInsideColorService;
	@Autowired
	private ProductInsideIconService productInsideIconService;
	@Autowired
	private ProductInsideAddInfoService productInsideAddInfoService;
	@Autowired
	private ProductInsideOptionService productInsideOptionService;
	@Autowired
	private ProductInsidePriceHistoryService productInsidePriceHistoryService;
	@Autowired
	private ProductInsideChangeHistoryService productInsideChangeHistoryService;
	@Autowired
	private ProductInsideApprovalHistoryService productInsideApprovalHistoryService;
	@Autowired
	private ProductInsideMemoService productInsideMemoService;
	@Autowired
	private ProductInsideRelationFileService productInsideRelationFileService;
	@Autowired
	private ProductIconService productIconService;
	@Autowired
	private ProductInsideRelationProductService productInsideRelationProductService;

	@Autowired
	private ProductFileService productFileService;

	@Autowired
	private ProductInterfaceService productInterfaceService;

	@Autowired
	private DisplayCategoryService displayCategoryService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private CommonCodeService commonCodeService;
	@Autowired
	private VendorService vendorService;
	@Autowired
	private PlanningDisplayService planningDisplayService;

	/** 자사 엑셀다운로드 헤더 목록 */
	@SuppressWarnings("serial")
	private final List<String> HEADER_EXCEL = new ArrayList<String>() {
		{
			add("mmnyPrdtYn"); // 자사상품여부
			add("vndrPrdtNoText"); // 내부관리코드
			add("chnnlName"); // 채널이름
			add("prdtNo"); // 상품번호
			add("prdtName"); // 상품명
			add("brandName"); // 브랜드명
			add("engPrdtName"); // 영문상품명
			add("brandEnName"); // 영문브랜드명
			add("styleInfo"); // 스타일정보
			add("prdtColorInfo"); // 색상정보
			add("sellStatCode"); // 판매상태코드
			add("stockAiQty"); // AI 재고량
			add("stockAwQty"); // AW 재고량
			add("stockAsQty"); // AS 재고량
			add("stockVdQty"); // VD 재고량
			add("avaiableStockQty"); // 주문가능수량(가용재고량)
			add("availabilityRate"); // 가용율
			add("dispYn"); // 전시여부
			add("stockIntgrYn"); // 재고통합여부
			add("stockUnIntgrRsnCode"); // 재고통합미연동 사유
			add("normalAmt"); // 정상가
			add("displayProductPrice"); // 판매가
			add("displayDiscountRate"); // 할인율
			add("genderGbnCode"); // 테마카테고리명
			add("stdCtgrName"); // 표준카테고리명
			add("stdrCtgrName"); // 기준카테고리명
			add("erpSellAmt"); // 기간계할인가
			add("erpDscntRate"); // 기간계할인율
			add("onlnSellAmt"); // 온라인할인가
			add("onlnDscntRate"); // 온라인할인율
			add("empSellAmt"); // 임직원할인가
			add("empDscntRate"); // 임직원할인율
			add("vndrName"); // 업체명
			add("rsvPrdtYn"); // 예약상품여부
			add("vndrCmsnRate"); // 입점사수수료율
			add("sellStartDtm"); // 판매시작일
			add("sellEndDtm"); // 판매종료일
			add("aprverDtm"); // 승인일
			add("lastWrhsDay"); // 최종입고일
		}
	};

	/** SKU_SIZE별 엑셀다운로드 헤더목록 */
	@SuppressWarnings("serial")
	private final List<String> HEADER_EXCEL_FOR_SKUSIZE = new ArrayList<String>() {
		{
			add("prdtNo"); // 상품코드
			add("chnnlNo"); // 채널구분
			add("vndrPrdtNoText"); // 업체상품번호
			add("brandEnName"); // 영문브랜드명
			add("engPrdtName"); // 영문상품명
			add("prdtName"); // 한글상품명
			add("styleInfo"); // 스타일
			add("prdtColorInfo"); // 컬러
			add("prdtOptnNo"); // 옵션ID
			add("optnName"); // 옵션명
			add("stockAiQty"); // 온라인재고
			add("stockAwQty"); // 스마트재고
			add("stockAsQty"); // 매장재고
			add("orderPsbltQty"); // 주문가능수량
			add("sellStatName"); // 판매상태
			add("useYn"); // 전시여부
			add("stockIntgrYn"); // 연동여부
			add("cmsnRate"); // 입점사수수료율
		}
	};

	/** 입점사 엑셀다운로드 헤더 목록 */
	@SuppressWarnings("serial")
	private final List<String> HEADER_EXCEL_FOR_VENDOR = new ArrayList<String>() {
		{
			add("mmnyPrdtYn"); // 자사상품여부
			add("vndrPrdtNoText"); // 내부관리코드
			add("chnnlName"); // 채널이름
			add("prdtNo"); // 상품번호
			add("prdtName"); // 상품명
			add("brandName"); // 브랜드명
			add("engPrdtName"); // 영문상품명
			add("brandEnName"); // 영문브랜드명
			add("styleInfo"); // 스타일정보
			add("prdtColorInfo"); // 색상정보
			add("sellStatCode"); // 판매상태코드
			add("stockVdQty"); // VD 재고량
			add("avaiableStockQty"); // 주문가능수량(가용재고량)
			add("dispYn"); // 전시여부
			add("stockIntgrYn"); // 재고통합여부
			add("stockUnIntgrRsnCode"); // 재고통합미연동 사유
			add("normalAmt"); // 정상가
			add("displayProductPrice"); // 판매가
			add("displayDiscountRate"); // 할인율
			add("genderGbnCode"); // 테마카테고리명
			add("stdCtgrName"); // 표준카테고리명
			add("stdrCtgrName"); // 기준카테고리명
			add("erpSellAmt"); // 기간계할인가
			add("erpDscntRate"); // 기간계할인율
			add("onlnSellAmt"); // 온라인할인가
			add("onlnDscntRate"); // 온라인할인율
			// add("empSellAmt"); // 임직원할인가
			// add("empDscntRate"); // 임직원할인율
			add("vndrName"); // 업체명
			add("rsvPrdtYn"); // 예약상품여부
			add("aprverDtm"); // 승인일
			add("vndrCmsnRate"); // 입점사수수료율
		}
	};

	private static final String CHNNL_NO_ABCMART = "10001";

	/**
	 * @Desc : 상품 목록 조회
	 * @Method Name : getList
	 * @Date : 2019. 2. 26.
	 * @Author : tennessee
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public ModelAndView getList(Parameter<?> parameter) throws Exception {
		log.debug("상품 목록 페이지");

		// 화면에 사용되는 공통코드 조회
		String[] codeFields = { CommonCode.SELL_STAT_CODE, CommonCode.GENDER_GBN_CODE, CommonCode.PRDT_TYPE_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
				.getCodeListByGroupByCombo(codeFields, false);

		parameter.addAttribute("gridCodes", pair.getFirst()); // 그리드 공통코드. 판매상태

		parameter.addAttribute("userAuthority", LoginManager.getUserDetails().getAuthApplySystemType()); // 사용자 권한 조회
		parameter.addAttribute("searchConditionSiteList", this.siteService.getSiteList()); // 사이트 정보

		// 매장 등급 코드
		// PO 상품관리와 동일하게 사용되어 전체 적용되게 제외 처리함
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

		return forward("/product/product/product-list");
	}

	/**
	 * @Desc : 상품 목록 조회
	 * @Method Name : searchProductList
	 * @Date : 2019. 2. 26.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping
	public void searchProductList(Parameter<PdProductSearchVO> parameter) throws Exception {
		log.debug("상품 목록 조회");
		Pageable<PdProductSearchVO, PdProduct> pageable = new Pageable<PdProductSearchVO, PdProduct>(parameter);
		pageable.getBean().validate();
		pageable.getBean().setAprvStatCode(CommonCode.APRV_STAT_CODE_CONFIRM);
		Page<PdProduct> productList = this.productService.searchProduct(pageable);

		this.writeJson(parameter, productList.getGrid());
	}

	/**
	 * @Desc      	: 상품목록 조회(NEW)
	 * @Method Name : searchProductListNew
	 * @Date  	  	: 2020. 3. 30.
	 * @Author    	: 3top
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("productSearchNew")
	public void searchProductListNew(Parameter<PdProductSearchVO> parameter) throws Exception {
		Pageable<PdProductSearchVO, PdProduct> pageable = new Pageable<PdProductSearchVO, PdProduct>(parameter);
		pageable.getBean().validate();
		pageable.getBean().setAprvStatCode(CommonCode.APRV_STAT_CODE_CONFIRM);

		Page<PdProduct> productList = productService.searchProductNew(pageable);

		this.writeJson(parameter, productList.getGrid());
	}

	/**
	 * @Desc : 엑셀다운로드(옵션별 상품 엑셀 다운로드)
	 * @Method Name : downloadAsExcelFileSku
	 * @Date : 2020. 3. 20.
	 * @Author : 3top
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("download/excel/sku/prodlist")
	public void downloadAsExcelFileSku(Parameter<PdProductSearchVO> parameter) throws Exception {
		Pageable<PdProductSearchVO, PdProductSkuExcelListVO> pageable = new Pageable<PdProductSearchVO, PdProductSkuExcelListVO>(
				parameter);
		pageable.getBean().validate();
		pageable.getBean().setAprvStatCode(CommonCode.APRV_STAT_CODE_CONFIRM);

		pageable.setPageNum(1);
		pageable.setRowsPerPage(Integer.MAX_VALUE);

		Page<PdProductSkuExcelListVO> productList = this.productService.searchProductExcelSku(pageable);

		// 화면에 사용되는 공통코드 조회
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
				.getCodeListByGroupByCombo(new String[] { CommonCode.SELL_STAT_CODE }, false);
		List<SyCodeDetail> sellStatCodeList = pair.getSecond().get(CommonCode.SELL_STAT_CODE);

		productList.getContent().forEach(x -> {
			// 판매상태 공통코드값을 공통코드이름으로 치환
			List<SyCodeDetail> foundSellStatCode = sellStatCodeList.stream()
					.filter(y -> UtilsText.equals(x.getSellStatCode(), y.getCodeDtlNo())).collect(Collectors.toList());
			if (UtilsCollection.isNotEmpty(foundSellStatCode)) {
				x.setSellStatCode(foundSellStatCode.get(0).getCodeDtlName());
			}
		});

		String excelFileName = "productSkuSizeList";
		ExcelValue excelValue = ExcelValue.builder(1)
				.columnNames(Arrays.asList("prdtNo", "chnnlName", "vndrPrdtNoText", "brandEnName", "engPrdtName",
						"brandName", "prdtName", "styleInfo", "prdtColorInfo", "prdtOptnNo", "optnName", "sortSeq",
						"availableStockAiQty", "availableStockAwQty", "availableStockAsQty", "orderPsbltQty",
						"sellStatCode", "useYn", "stockIntgrYn", "stockUnIntgrRsnCodeName","cmsnRate"))
				.data(productList.getContent()).build();
		parameter.downloadExcelTemplate(UtilsText.concat("product/product/excel/", excelFileName), excelValue);
	}

	/**
	 * @Desc : 엑셀파일 다운로드
	 * @Method Name : downloadAsExcelFile
	 * @Date : 2019. 5. 2.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download/excel")
	public void downloadAsExcelFile(Parameter<PdProductSearchVO> parameter) throws Exception {
		Pageable<PdProductSearchVO, PdProduct> pageable = new Pageable<PdProductSearchVO, PdProduct>(parameter);
		pageable.getBean().validate();
		pageable.getBean().setAprvStatCode(CommonCode.APRV_STAT_CODE_CONFIRM);

		pageable.setPageNum(1);
		pageable.setRowsPerPage(Integer.MAX_VALUE);

		Page<PdProduct> productList = this.productService.searchProduct(pageable);

		// 화면에 사용되는 공통코드 조회
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
				.getCodeListByGroupByCombo(new String[] { CommonCode.SELL_STAT_CODE, CommonCode.GENDER_GBN_CODE,
						CommonCode.STOCK_UN_INTGR_RSN_CODE }, false);
		List<SyCodeDetail> sellStatCodeList = pair.getSecond().get(CommonCode.SELL_STAT_CODE);
		List<SyCodeDetail> genderGbnCodeList = pair.getSecond().get(CommonCode.GENDER_GBN_CODE);
		List<SyCodeDetail> stockUnIntgrRsnCodeList = pair.getSecond().get(CommonCode.STOCK_UN_INTGR_RSN_CODE);

		// 코드정보 치환작업
		productList.getContent().forEach(x -> {

			// 가용율
			if (UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_TRUE)) {
				x.setAvailabilityRate(
						(int) Math.round(Math.ceil((double) x.getUseYnQty() / (double) x.getTotalOptionCount()) * 100));
			} else {
				x.setAvailabilityRate(0);
			}

			// 자사/입점 값 설정
			x.setMmnyPrdtYn(UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_TRUE) ? "자사" : "입점");
			// 판매상태 공통코드값을 공통코드이름으로 치환
			List<SyCodeDetail> foundSellStatCode = sellStatCodeList.stream()
					.filter(y -> UtilsText.equals(x.getSellStatCode(), y.getCodeDtlNo())).collect(Collectors.toList());
			if (UtilsCollection.isNotEmpty(foundSellStatCode)) {
				x.setSellStatCode(foundSellStatCode.get(0).getCodeDtlName());
			}

			// 테마카테고리 공통코드값을 공통코드이름으로 치환
			List<SyCodeDetail> foundGenderGbnCode = genderGbnCodeList.stream()
					.filter(y -> UtilsText.equals(x.getGenderGbnCode(), y.getCodeDtlNo())).collect(Collectors.toList());
			if (UtilsCollection.isNotEmpty(foundGenderGbnCode)) {
				x.setGenderGbnCode(foundGenderGbnCode.get(0).getCodeDtlName());
			}

			// 재고통합 미연동사유 공통코드값을 공통코드 이름으로 치환
			List<SyCodeDetail> foundStockUnIntgrRsnCode = stockUnIntgrRsnCodeList.stream()
					.filter(y -> UtilsText.equals(x.getStockUnIntgrRsnCode(), y.getCodeDtlNo()))
					.collect(Collectors.toList());
			if (UtilsText.equals(x.getStockIntgrYn(), Const.BOOLEAN_FALSE)) {
				if (UtilsCollection.isNotEmpty(foundStockUnIntgrRsnCode)) {
					x.setStockUnIntgrRsnCode(foundStockUnIntgrRsnCode.get(0).getCodeDtlName());
				}
			} else {
				x.setStockUnIntgrRsnCode(null);
			}

			// 재고통합연동여부
			x.setStockIntgrYn(UtilsText.equals(x.getStockIntgrYn(), Const.BOOLEAN_TRUE) ? "연동" : "미연동");

			// 할인가격 설정
			if (UtilsText.isNotBlank(x.getNormalAmt())) {
				int normalAmt = Integer.parseInt(x.getNormalAmt());

				// 임직원가 만 계산

				if (UtilsText.isNotBlank(x.getEmpDscntRate())) {
					float rate = Integer.parseInt(x.getEmpDscntRate());
					rate = rate / 100;
					int dscntAmt = (int) (normalAmt * rate);
					int calculated = normalAmt - dscntAmt;

					x.setEmpSellAmt(calculated);
					x.setEmpDscntRate(x.getEmpDscntRate());
				}
			}
			// 전시여부
			x.setDispYn(UtilsText.equals("Y", x.getDispYn()) ? "전시" : "전시안함");
			// 예약상품여부
			x.setRsvPrdtYn(UtilsText.equals("Y", x.getRsvPrdtYn()) ? "예약" : "일반");
		});

		String excelFileName = null;
		List<String> excelHeader = null;

		if (UtilsText.equals(Const.AUTH_APPLY_SYSTEM_BO, LoginManager.getUserDetails().getAuthApplySystemType())) {
			excelHeader = this.HEADER_EXCEL;
			excelFileName = "productList";
		} else {
			excelHeader = this.HEADER_EXCEL_FOR_VENDOR;
			excelFileName = "productListForVendor";
		}

		ExcelValue excelValue = ExcelValue.builder(1).columnNames(excelHeader).data(productList.getContent()).build();

		parameter.downloadExcelTemplate(UtilsText.concat("product/product/excel/", excelFileName), excelValue);
	}

	/**
	 * @Desc      	: 상품목록 엑셀다운로드(NEW)
	 * @Method Name : downloadAsExcelFileNew
	 * @Date  	  	: 2020. 3. 30.
	 * @Author    	: 3top
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download/excelNew")
	public void downloadAsExcelFileNew(Parameter<PdProductSearchVO> parameter) throws Exception {
		Pageable<PdProductSearchVO, PdProduct> pageable = new Pageable<PdProductSearchVO, PdProduct>(parameter);
		pageable.getBean().validate();
		pageable.getBean().setAprvStatCode(CommonCode.APRV_STAT_CODE_CONFIRM);

		pageable.setPageNum(1);
		pageable.setRowsPerPage(Integer.MAX_VALUE);

		Page<PdProduct> productList = productService.getExcelDownLoadProductList(pageable);

		String excelFileName = null;
		List<String> excelHeader = null;

		if (UtilsText.equals(Const.AUTH_APPLY_SYSTEM_BO, LoginManager.getUserDetails().getAuthApplySystemType())) {
			excelHeader = this.HEADER_EXCEL;
			excelFileName = "productList";
		} else {
			excelHeader = this.HEADER_EXCEL_FOR_VENDOR;
			excelFileName = "productListForVendor";
		}

		ExcelValue excelValue = ExcelValue.builder(1).columnNames(excelHeader).data(productList.getContent()).build();

		parameter.downloadExcelTemplate(UtilsText.concat("product/product/excel/", excelFileName), excelValue);
	}

	/**
	 * @Desc : 상품 상세 정보 페이지. ERP코드만 있는 경우, 자사상품승인접근으로 판단. ERP코드가 없는 경우, 입점사
	 * @Method Name : getDetail
	 * @Date : 2019. 4. 1.
	 * @Author : tennessee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	// FIXME 업체 상품일 경우 전시 카테고리 노출 방법 정의 필요
	@SuppressWarnings("unchecked")
	@GetMapping("detail")
	public ModelAndView getDetail(Parameter<?> parameter) throws Exception {
		log.debug("상품 등록 페이지");

		parameter.addAttribute("userAuthority", LoginManager.getUserDetails().getUpAuthNo()); // 사용자 권한 조회

		// 접근 파라미터 획득
		String prdtNo = parameter.getString("prdtNo"); // 온라인상품번호
		String siteNo = parameter.getString("siteNo"); // 사이트 번호
		String chnnlNo = parameter.getString("chnnlNo"); // 채널 번호
		String vndrPrdtNoText = parameter.getString("vndrPrdtNoText"); // ERP상품코드 또는 업체번호

		Boolean foundOnlineProduct = false;

		// 온라인상품번호가 유효한지 판단
		if (UtilsText.isNotBlank(prdtNo)) {
			PdProduct product = this.productService.getProduct(prdtNo, siteNo, chnnlNo, vndrPrdtNoText);
			if (UtilsObject.isNotEmpty(product)) {
				// 온라인상품번호 유효
				foundOnlineProduct = true;
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

				parameter.addAttribute("memoList", this.productInsideMemoService.searchByPrdtNo(prdtNo)); // 메모
				parameter.addAttribute("associatedProducts",
						this.productService.getProductInfoAboutEachChannel(product)); // 채널별 상품정보 조회
				parameter.addAttribute("product", product);
				parameter.addAttribute("type", PdProduct.TYPE_MODIFY);

				parameter.addAttribute("channelList", this.productService.getChannels(product)); // 상품정보에 따른 등록가능채널 조회
			} else {
				throw new Exception("존재하지 않는 상품입니다.");
			}
		}

		// 채널번호가 있으나 사이트번호가 없는 경우, 사이트번호 조회
		if (UtilsText.isNotBlank(chnnlNo) && UtilsText.isBlank(siteNo)) {
			List<SySiteChnnl> channelList = this.siteService.getUseChannelList();
			if (UtilsCollection.isNotEmpty(channelList)) {
				for (SySiteChnnl item : channelList) {
					if (UtilsText.equals(chnnlNo, item.getChnnlNo())) {
						siteNo = item.getSiteNo();
						break;
					}
				}
			}
		}

		parameter.addAttribute("vndrPrdtNoText", vndrPrdtNoText);
		parameter.addAttribute("siteNo", siteNo);
		parameter.addAttribute("chnnlNo", chnnlNo);

		if (!foundOnlineProduct) {
			// 온라인상품을 찾지 못한경우 진입
			parameter.addAttribute("type", PdProduct.TYPE_SAVE);

			// 업체관리코드가 있는지 판단
			if (UtilsText.isNotBlank(vndrPrdtNoText)) {
				// 자사인 경우, 승인등록. 단, 채널정보가 있는 경우 중간계에서 상품정보를 조회하여 화면으로 전달
				if (UtilsText.equals(Const.AUTH_APPLY_SYSTEM_BO,
						LoginManager.getUserDetails().getAuthApplySystemType())) {
					// BO권한 사용자인 경우, 업체관리코드와 채널정보가 있으면 중간계에서 데이터 조회
					PdProduct convertedProduct = this.productInterfaceService.getErpProduct(vndrPrdtNoText, siteNo,
							UtilsText.isNotBlank(chnnlNo) ? chnnlNo : CHNNL_NO_ABCMART);
					parameter.addAttribute("product", convertedProduct); // 중간계 상품정보
					parameter.addAttribute("newErpProductYn", "Y"); // ERP상품등록플래그
				}
			} else {
				// 업체관리코드가 없는 경우
				if (UtilsText.equals(Const.AUTH_APPLY_SYSTEM_BO,
						LoginManager.getUserDetails().getAuthApplySystemType())) {
					// 자사인 경우, 오류페이지로 이동.
					throw new Exception("정상적인 접근이 아닙니다.");
				} else {
					// 입점인 경우, 신규등록으로 이동.
					PdProduct product = new PdProduct();
					product.setMmnyPrdtYn("N"); // 자사상품여부
					product.setVndrNo(LoginManager.getUserDetails().getVndrNo()); // 입점업체번호
					product.setDefaultSellDtm(); // 판매기간 기본값 설정
					parameter.addAttribute("product", product);
					parameter.addAttribute("type", parameter.getString("type"));
				}
			}

			parameter.addAttribute("channelList", this.productService.getChannels(null)); // 사용자정보에 따른 등록가능채널 조회

			// 채널별 상품정보 조회
			parameter.addAttribute("associatedProducts", this.productService.getProductInfoAboutEachChannel(null));
		}

		// 화면에 부가적으로 필요한 정보 설정
		parameter.addAttribute("iconList", this.productIconService.getUseIconList()); // 사용 중인 아이콘 정보 조회

		// 화면에 사용되는 공통코드 조회
		String[] codeFields = { CommonCode.PRDT_TYPE_CODE, CommonCode.SELL_STAT_CODE, CommonCode.ORG_PLACE_CODE,
				CommonCode.PRDT_COLOR_CODE, CommonCode.STOCK_UN_INTGR_RSN_CODE, CommonCode.DEVICE_CODE,
				CommonCode.RLTN_PRDT_TYPE_CODE, CommonCode.PRDT_SAFE_TYPE_CODE, CommonCode.GENDER_GBN_CODE };
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

		return forward("/product/product/product-detail-popup");
	}

	/**
	 * @Desc : 관리자 메모 저장
	 * @Method Name : saveProductMemo
	 * @Date : 2019. 3. 7.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/memo/save")
	public void saveProductMemo(Parameter<PdProductMemo> parameter) throws Exception {
		log.debug("관리자 메모 저장");
		parameter.validate();
		PdProductMemo pdProductMemo = parameter.get();
		this.productInsideMemoService.insert(pdProductMemo);
		this.writeJson(parameter, this.productInsideMemoService.searchByPrdtNo(pdProductMemo.getPrdtNo()).get(0));
	}

	/**
	 * @Desc : 관리자 메모 삭제
	 * @Method Name : removeProductMemo
	 * @Date : 2019. 3. 7.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/memo/remove")
	public void removeProductMemo(Parameter<PdProductMemo> parameter) throws Exception {
		log.debug("관리자 메모 삭제");
		PdProductMemo pdProductMemo = parameter.get();
		this.productInsideMemoService.delete(pdProductMemo);
		this.writeJson(parameter, pdProductMemo);
	}

	/**
	 * @Desc : 상품 정보 등록
	 * @Method Name : saveProduct
	 * @Date : 2019. 3. 29.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/save")
	public void saveProduct(Parameter<PdProduct> parameter) throws Exception {
		log.debug("상품정보 등록");
		parameter.validate();
		PdProduct product = parameter.get();
		this.productService.regist(product);
		this.writeJson(parameter, product);
	}

	/**
	 * @Desc : 관련 상품 조회
	 * @Method Name : searchRelationProductList
	 * @Date : 2019. 3. 27.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("relation")
	public void searchRelationProductList(Parameter<PdRelationProduct> parameter) throws Exception {
		log.debug("관련 상품 조회");
		Pageable<PdRelationProduct, PdRelationProduct> pageable = new Pageable<PdRelationProduct, PdRelationProduct>(
				parameter);
		Page<PdRelationProduct> productList = this.productInsideRelationProductService.searchRelationProduct(pageable);
		this.writeJson(parameter, productList.getGrid());
	}

	/**
	 * @Desc : 상품정보고시 품목 조회
	 * @Method Name : getNoticeInfoItemListByItemCode
	 * @Date : 2019. 3. 28.
	 * @Author : 이가영
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/notice/info/list/itemCode")
	public void getNoticeInfoItemListByItemCode(Parameter<?> parameter) throws Exception {
		log.debug("상품정보고시 품목 목록 조회");
		String[] codeFields = { CommonCode.ITEM_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);
		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();
		this.writeJson(parameter, codeList.get(CommonCode.ITEM_CODE));
	}

	/**
	 * @Desc : 상품정보고시 상품안전유형코드 목록 조회
	 * @Method Name : getNoticeInfoListByPrdtSafeTypeCode
	 * @Date : 2019. 3. 28.
	 * @Author : 이가영
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/notice/info/list/prdtSafeTypeCode")
	public void getNoticeInfoListByPrdtSafeTypeCode(Parameter<?> parameter) throws Exception {
		log.debug("상품정보고시 상품안전유형코드 목록 조회");
		String[] codeFields = { CommonCode.PRDT_SAFE_TYPE_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);
		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();
		this.writeJson(parameter, codeList.get(CommonCode.PRDT_SAFE_TYPE_CODE));
	}

	/**
	 * @Desc : 상품 옵션/재고정보 조회
	 * @Method Name : searchProductOption
	 * @Date : 2019. 4. 1.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/option")
	public void searchProductOption(Parameter<PdProductOptionStockSearchVO> parameter) throws Exception {
		PdProductOptionStockSearchVO criteria = parameter.get();
		if (UtilsText.equalsIgnoreCase(criteria.getNewErpProductYn(), "Y")) {
			// 신규등록인 경우 중, ERP 상품등록인 경우. 중간계에서 정보를 조회해 옴.
			List<PdProductOption> resultErp = this.productInterfaceService.getErpProductOptionWithStock(criteria);
			Page<PdProductOption> page = new Page<PdProductOption>();
			page.setOrgPageNum(1);
			page.setPageNum(1);
			page.setRowsPerPage(999);
			page.setTotalCount(resultErp.size());
			page.setContent(resultErp);
			Page<PdProductOption> test = page;
			this.writeJson(parameter, test.getGrid());
		} else {
			// 수정화면인 경우
			Pageable<PdProductOptionStockSearchVO, PdProductOption> pageable = new Pageable<PdProductOptionStockSearchVO, PdProductOption>(
					parameter);
			// 옵션은 페이징이 없으므로 다건조회함
			pageable.setPageNum(1);
			pageable.setRowsPerPage(999);
			Page<PdProductOption> result = this.productInsideOptionService.searchProductOptionWithStocks(pageable);
			this.writeJson(parameter, result.getGrid());
		}
	}

	/**
	 * @Desc : 재고수량 변경 처리
	 * @Method Name : setProductOptionStock
	 * @Date : 2019. 4. 1.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@Deprecated
	@PostMapping("/option/stock")
	public void setProductOptionStock(Parameter<PdProductOptionStock> parameter) throws Exception {
		Boolean result = this.productInsideOptionService.updateProductOptionStock(parameter.get());
		Map<String, Object> responseJson = new HashMap<String, Object>();
		responseJson.put("result", result);
		this.writeJson(parameter, responseJson);
	}

	/**
	 * @Desc : 상품 이력 조회 팝업
	 * @Method Name : getHistoryPopup
	 * @Date : 2019. 4. 22.
	 * @Author : tennessee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/history")
	public ModelAndView getHistoryPopup(Parameter<?> parameter) throws Exception {
		// 화면에 사용되는 공통코드 조회
		String[] codeFields = { CommonCode.PRDT_APRV_REQ_CODE, CommonCode.PRDT_APRV_STAT_CODE,
				CommonCode.SELL_STAT_CODE, CommonCode.APRV_STAT_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
				.getCodeListByGroupByCombo(codeFields, false);
		parameter.addAttribute("commonCode", pair.getFirst()); // 그리드 공통코드

		return forward("/product/product/popup/product-popup-history");
	}

	/**
	 * @Desc : 상품 가격 이력 조회
	 * @Method Name : getProductPriceHistoryPoppup
	 * @Date : 2019. 4. 22.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/history/price")
	public void getProductPriceHistoryPoppup(Parameter<PdProductPriceHistory> parameter) throws Exception {
		Pageable<PdProductPriceHistory, PdProductPriceHistory> pageable = new Pageable<PdProductPriceHistory, PdProductPriceHistory>(
				parameter);
		Page<PdProductPriceHistory> page = this.productInsidePriceHistoryService.getProdutPriceHistory(pageable);
		this.writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 상품 변경 이력 조회
	 * @Method Name : getProductChangeHistoryPoppup
	 * @Date : 2019. 4. 22.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/history/product")
	public void getProductChangeHistoryPoppup(Parameter<PdProductChangeHistory> parameter) throws Exception {
		Pageable<PdProductChangeHistory, PdProductChangeHistory> pageable = new Pageable<PdProductChangeHistory, PdProductChangeHistory>(
				parameter);
		Page<PdProductChangeHistory> page = this.productInsideChangeHistoryService.getProdutChangeHistory(pageable);
		this.writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 상품 전시 정보 팝업
	 * @Method Name : getExhibitionPopup
	 * @Date : 2019. 4. 23.
	 * @Author : tennessee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/exhibition")
	public ModelAndView getExhibitionPopup(Parameter<?> parameter) throws Exception {
		return forward("/product/product/popup/product-popup-exhibition");
	}

	/**
	 * @Desc : 상품 승인 이력 조회
	 * @Method Name : getProductApprovalHistoryPoppup
	 * @Date : 2019. 4. 23.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/history/approval")
	public void getProductApprovalHistoryPoppup(Parameter<PdProductApprovalHistory> parameter) throws Exception {
		Pageable<PdProductApprovalHistory, PdProductApprovalHistory> pageable = new Pageable<PdProductApprovalHistory, PdProductApprovalHistory>(
				parameter);
		Page<PdProductApprovalHistory> page = this.productInsideApprovalHistoryService.getProdutChangeHistory(pageable);
		this.writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 프로모션 대상 매핑 기준 상품 조회
	 * @Method Name : searchPromotionTargetProduct
	 * @Date : 2019. 5. 20.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/mapping/promotion/target")
	public void searchPromotionTargetProduct(Parameter<PdProductMappingVO> parameter) throws Exception {
		Pageable<PdProductMappingVO, PdProductMapped> pageable = new Pageable<PdProductMappingVO, PdProductMapped>(
				parameter);
		pageable.getBean().setMappingTableName("pr_promotion_target_product");
		pageable.getBean().setConditionColumnName("promo_no");
		pageable.getBean().setConditionColumnValue(parameter.getString("promoNo"));
		pageable.getBean().setSortColumnName("prdt_no");
		pageable.getBean().setSortType("ASC");
		pageable.setUsePage(false);
		Page<PdProductMapped> page = this.productService.searchProductByMapped(pageable);
		this.writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 상품에 대한 프론트 URL을 반환
	 * @Method Name : getFrontUrl
	 * @Date : 2019. 7. 22.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/front/url")
	public void getFrontUrl(Parameter<?> parameter) throws Exception {
		String prdtNo = parameter.getString("prdtNo");
		String url = this.productService.getUrl(prdtNo);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("data", url);
		this.writeJson(parameter, result);
	}

	/**
	 * @Desc : 입점사관련 정보 조회
	 * @Method Name : getVendorInformation
	 * @Date : 2019. 7. 22.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/information/vendor")
	public void getVendorInformation(Parameter<?> parameter) throws Exception {
		String vndrNo = parameter.getString("vndrNo");
		String stdCtgrNo = parameter.getString("stdCtgrNo");
		String brandNo = parameter.getString("brandNo");
		VendorOtherPartVo result = this.vendorService.getVendorCmsnRateAndEmpDscntRate(vndrNo, stdCtgrNo, brandNo);
		this.writeJson(parameter, result);
	}

	/**
	 * @Desc : 에디터에 의한 이미지 업로드
	 * @Method Name : uploadByEditor
	 * @Date : 2019. 8. 7.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/editer/upload")
	public void uploadByEditor(Parameter<PdProductEditorUploadVO> parameter) throws Exception {
		Map<String, String> result = this.productFileService.uploadFile(parameter.get().getUpload(), Arrays.asList(
				Const.IMG_SRC_PRODUCT_DETAIL_PREFIX, UtilsDate.today(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS)));
		result.put("uploaded", "1"); // 에디터에서 사용 될 성공여부
		result.put("url", result.get(ProductFileService.KEY_FILE_URL)); // 에디터에서 사용 될 URL
		this.writeJson(parameter, result);

	}

	/**
	 * @Desc : 한 상품에 대한 전시페이지 정보 조회
	 * @Method Name : readExhibitionPageForProduct
	 * @Date : 2019. 4. 23.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/exhibition/page")
	public void readExhibitionPageForProduct(Parameter<DpExhibitionPageVO> parameter) throws Exception {
		Pageable<DpExhibitionPageVO, DpExhibitionPageVO> pageable = new Pageable<DpExhibitionPageVO, DpExhibitionPageVO>(
				parameter);
		Page<DpExhibitionPageVO> productList = this.displayCategoryService.getExhibitionPageForProduct(pageable);
		this.writeJson(parameter, productList.getGrid());
	}

	/**
	 * @Desc : 한 상품에 대한 기획전 전시정보 조회
	 * @Method Name : readExhibitionPlanningForProduct
	 * @Date : 2019. 10. 4.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/exhibition/planning")
	public void readExhibitionPlanningForProduct(Parameter<PrExhibitionPlanningVO> parameter) throws Exception {
		Pageable<PrExhibitionPlanningVO, PrExhibitionPlanningVO> pageable = new Pageable<PrExhibitionPlanningVO, PrExhibitionPlanningVO>(
				parameter);
		Page<PrExhibitionPlanningVO> productList = this.planningDisplayService
				.getExhibitionPlanningForProduct(pageable);
		this.writeJson(parameter, productList.getGrid());
	}

	/**
	 * @Desc : 상품 내부관리번호 기반 등록여부 검사. 미등록된 경우, 중간계로 신호를 재전송
	 * @Method Name : checkOnlineRegistred
	 * @Date : 2019. 11. 26.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	// TODO ERP 상품 조회 및 상품 상세 정보 조회 쿼리 모두 확인 해야함.
	@GetMapping("/check/online/registered")
	public void checkOnlineRegistred(Parameter<PdProduct> parameter) throws Exception {
		log.debug("상품 내부관리번호 기반 온라인 등록여부 조회");

		if (UtilsText.isBlank(parameter.get().getChnnlNo()) || UtilsText.isBlank(parameter.get().getVndrPrdtNoText())) {
			throw new Exception("채널번호 또는 내부관리정보가 없습니다.");
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", productService.checkRegistered(parameter.get()));

		this.writeJson(parameter, result);
	}

}
