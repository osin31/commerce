package kr.co.abcmart.bo.product.controller;

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

import kr.co.abcmart.bo.display.service.DisplayCategoryService;
import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductApprovalHistory;
import kr.co.abcmart.bo.product.service.BrandService;
import kr.co.abcmart.bo.product.service.ProductApprovalService;
import kr.co.abcmart.bo.product.service.ProductIconService;
import kr.co.abcmart.bo.product.service.ProductInsideAddInfoService;
import kr.co.abcmart.bo.product.service.ProductInsideApprovalHistoryService;
import kr.co.abcmart.bo.product.service.ProductInsideColorService;
import kr.co.abcmart.bo.product.service.ProductInsideDetailService;
import kr.co.abcmart.bo.product.service.ProductInsideIconService;
import kr.co.abcmart.bo.product.service.ProductInsideMemoService;
import kr.co.abcmart.bo.product.service.ProductInsidePriceHistoryService;
import kr.co.abcmart.bo.product.service.ProductInsideRelationFileService;
import kr.co.abcmart.bo.product.service.ProductService;
import kr.co.abcmart.bo.product.vo.PdProductApprovalSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 입점상품 승인관리
 * @FileName : ProductVndrApprovalController.java
 * @Project : abc.bo
 * @Date : 2019. 4. 2.
 * @Author : hsjhsj19
 */
@Slf4j
@Controller
@RequestMapping("product/vndrApproval")
public class ProductVndrApprovalController extends BaseController {

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
	private ProductInsidePriceHistoryService productInsidePriceHistoryService;
	@Autowired
	private ProductInsideApprovalHistoryService productInsideApprovalHistoryService;
	@Autowired
	private ProductInsideMemoService productInsideMemoService;
	@Autowired
	private ProductInsideRelationFileService productInsideRelationFileService;
	@Autowired
	private ProductIconService productIconService;
	@Autowired
	private ProductApprovalService productApprovalService;

	@Autowired
	private DisplayCategoryService displayCategoryService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private CommonCodeService commonCodeService;

	/**
	 * @Desc : 입점상품 화면
	 * @Method Name : getList
	 * @Date : 2019. 4. 2.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public ModelAndView getList(Parameter<?> parameter) throws Exception {
		log.debug("입점상품 승인관리 (BO) 목록 페이지");
		String[] codeFields = { CommonCode.PRDT_APRV_REQ_CODE, CommonCode.APRV_STAT_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);
		JSONObject codeCombo = pair.getFirst();

		parameter.addAttribute("codeCombo", codeCombo);
		// 승인요청코드
		parameter.addAttribute("searchConditionPrdtAprvReqCodeList",
				pair.getSecond().get(CommonCode.PRDT_APRV_REQ_CODE));
		// 승인 상태 코드
		parameter.addAttribute("searchConditionAprvStatCodeList", pair.getSecond().get(CommonCode.APRV_STAT_CODE));
		// 사이트 채널 코드
		parameter.addAttribute("searchConditionSiteChannelList", this.siteService.getVendorUseChannelList());
		// 승인 처리자 목록
		parameter.addAttribute("searchAprvAdminList", this.productApprovalService.getAprvList());

		return forward("/product/vndr-approval/vndr-approval-list");
	}

	/**
	 * @Desc : 입점상품 목록 조회
	 * @Method Name : searchApprovalProductList
	 * @Date : 2019. 4. 2.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping
	public void searchApprovalProductList(Parameter<PdProductApprovalSearchVO> parameter) throws Exception {
		log.debug("입점상품 승인관리 (BO) 목록 조회");

		// 코드 목록 리스트화
		parameter.validate();

		Pageable<PdProductApprovalSearchVO, PdProduct> pageable = new Pageable<PdProductApprovalSearchVO, PdProduct>(
				parameter);
		PdProductApprovalSearchVO pdProductApprovalSearchVO = parameter.get();
		pdProductApprovalSearchVO.setAtPO("N");
		pdProductApprovalSearchVO.setMmnyPrdtYn("N");
		pageable.setBean(pdProductApprovalSearchVO);

		Page<PdProduct> productList = this.productApprovalService.selectProduct(pageable);

		this.writeJson(parameter, productList.getGrid());
	}

	/**
	 * @Desc : 일괄반려 팝업창 새창
	 * @Method Name : batchReturn
	 * @Date : 2019. 4. 2.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("batchReturn")
	public ModelAndView batchReturn(Parameter<PdProduct> parameter) throws Exception {
		log.debug("입점상품 승인관리 (BO) 선택상품 일괄반려");

		return forward("/product/vndr-approval/vndr-approval-list-popup");
	}

	/**
	 * @Desc : 선택상품 일괄승인/반려 수정
	 * @Method Name : batchModify
	 * @Date : 2019. 4. 2.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("batchModify")
	public void batchModify(Parameter<PdProductApprovalHistory[]> parameter) throws Exception {
		log.debug("입점상품 승인관리 (BO) 선택상품 일괄승인/반려 수정");

		this.writeJson(parameter, this.productApprovalService.batchVndrModify(parameter.get()));
	}

	/**
	 * @Desc : 선택상품 단건/다건 승인,반려
	 * @Method Name : modify
	 * @Date : 2019. 4. 2.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("modify")
	public void modify(Parameter<PdProductApprovalHistory[]> parameter) throws Exception {
		log.debug("입점상품 승인관리 (BO) 단건(선택상품)/다건(일괄) 반려");

		this.writeJson(parameter, this.productApprovalService.batchVndrModify(parameter.get()));
	}

	/**
	 * @Desc : 입점상품 승인관리 (BO) 상세 페이지
	 * @Method Name : getDetail
	 * @Date : 2019. 4. 3.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("detail")
	public ModelAndView getDetail(Parameter<PdProduct> parameter) throws Exception {
		log.debug("입점상품 승인관리 (BO) 상세 페이지");

		parameter.addAttribute("userAuthority", LoginManager.getUserDetails().getUpAuthNo()); // 사용자 권한 조회
//		parameter.addAttribute("channelList", this.siteService.getUseChannelList()); // 채널조회

		// 접근 파라미터 획득
		String prdtNo = parameter.getString("prdtNo"); // 온라인상품번호
		String siteNo = parameter.getString("siteNo"); // 사이트 번호
		String chnnlNo = parameter.getString("chnnlNo"); // 채널 번호
		String vndrPrdtNoText = parameter.getString("vndrPrdtNoText"); // ERP상품코드 또는 업체번호

		// 온라인상품번호가 유효한지 판단
		if (UtilsText.isNotBlank(prdtNo)) {
			PdProduct product = this.productService.getProduct(prdtNo, siteNo, chnnlNo, vndrPrdtNoText);
			if (UtilsObject.isNotEmpty(product)) {
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
				this.productInsideApprovalHistoryService.setProductApprovalHistory(product); // 상품 승인이력 조회

				product.setBrandName(this.brandService.getBrandNameByBrandNo(product.getBrandNo())); // 브랜드 이름 설정

				parameter.addAttribute("memoList", this.productInsideMemoService.searchByPrdtNo(prdtNo)); // 메모
				parameter.addAttribute("associatedProducts",
						this.productService.getProductInfoAboutEachChannel(product)); // 자사 채널별 상품정보 조회
				parameter.addAttribute("product", product);
				parameter.addAttribute("type", PdProduct.TYPE_MODIFY);

				parameter.addAttribute("channelList", this.productService.getChannels(product)); // 상품정보에 따른 등록가능채널 조회
			}
		}

		parameter.addAttribute("vndrPrdtNoText", vndrPrdtNoText);
		parameter.addAttribute("siteNo", siteNo);
		parameter.addAttribute("chnnlNo", chnnlNo);

		// 화면에 부가적으로 필요한 정보 설정
		parameter.addAttribute("iconList", this.productIconService.getUseIconList()); // 사용 중인 아이콘 정보 조회

		// 화면에 사용되는 공통코드 조회
		String[] codeFields = { CommonCode.PRDT_TYPE_CODE, CommonCode.SELL_STAT_CODE, CommonCode.ORG_PLACE_CODE,
				CommonCode.PRDT_COLOR_CODE, CommonCode.STOCK_UN_INTGR_RSN_CODE, CommonCode.DEVICE_CODE,
				CommonCode.RLTN_PRDT_TYPE_CODE, CommonCode.PRDT_SAFE_TYPE_CODE, CommonCode.GENDER_GBN_CODE,
				CommonCode.APRV_STAT_CODE };
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
		parameter.addAttribute("aprvStatCodeList", pair.getSecond().get(CommonCode.APRV_STAT_CODE)); // 승인상태코드

		return forward("/product/vndr-approval/vndr-apporval-detail-popup");
	}

}
