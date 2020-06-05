package kr.co.abcmart.bo.display.controller;

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

import kr.co.abcmart.bo.display.model.master.CmCatalogEvent;
import kr.co.abcmart.bo.display.service.CatalogEventService;
import kr.co.abcmart.bo.product.model.master.DpBrand;
import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.service.BrandService;
import kr.co.abcmart.bo.product.service.ProductInsideChangeHistoryService;
import kr.co.abcmart.bo.product.service.ProductService;
import kr.co.abcmart.bo.product.service.StandardCategoryService;
import kr.co.abcmart.bo.product.vo.DpBrandSearchVO;
import kr.co.abcmart.bo.product.vo.PdProductSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("display/a-connect")
public class AConnectController extends BaseController {

	@Autowired
	BrandService brandService;

	@Autowired
	ProductService productService;

	@Autowired
	CatalogEventService catalogEventService;

	@Autowired
	ProductInsideChangeHistoryService productInsideChangeHistoryService;

	@Autowired
	CommonCodeService commonCodeService;

	@Autowired
	StandardCategoryService standardCategoryService;

	/**
	 * @Desc : 브랜드 관리
	 * @Method Name : brand
	 * @Date : 2019. 4. 24.
	 * @Author : 이가영
	 * @param parameter
	 * @return
	 */
	@GetMapping("/brand")
	public ModelAndView brand(Parameter<?> parameter) throws Exception {

		return forward("/display/a-connect/brand");
	}

	/**
	 * @Desc : 브랜드 목록 조회
	 * @Method Name : brandList
	 * @Date : 2019. 4. 24.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/brand/list")
	public void brandList(Parameter<DpBrandSearchVO> parameter) throws Exception {

		Pageable<DpBrandSearchVO, DpBrand> pageable = new Pageable<>(parameter);

		Page<DpBrand> page = brandService.searchBrandList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 브랜드 수정 (A-Connect 전시여부)
	 * @Method Name : brandModify
	 * @Date : 2019. 4. 24.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/brand/modify")
	public void brandModify(Parameter<DpBrandSearchVO[]> parameter) throws Exception {

		brandService.updateBrands(parameter.get());
	}

	/**
	 * @Desc : 브랜드 상세 조회
	 * @Method Name : brandDetail
	 * @Date : 2019. 4. 24.
	 * @Author : 이가영
	 * @param parameter
	 * @return
	 */
	@GetMapping("/brand/detail")
	public ModelAndView brandDetail(Parameter<DpBrandSearchVO> parameter) throws Exception {

		DpBrand dpBrand = brandService.searchBrand(parameter.get());

		parameter.addAttribute("dpBrand", dpBrand);

		return forward("/display/a-connect/brand-detail");
	}

	/**
	 * @Desc : 상품 전시 관리
	 * @Method Name : product
	 * @Date : 2019. 4. 24.
	 * @Author : 이가영
	 * @param parameter
	 * @return
	 */
	@GetMapping("/product")
	public ModelAndView product(Parameter<?> parameter) throws Exception {

		String[] codeFields = { CommonCode.APRV_STAT_CODE };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();

		parameter.addAttribute("codeCombo", pair.getFirst());
		parameter.addAttribute("aprvStatCodeList", codeList.get(CommonCode.APRV_STAT_CODE));

		return forward("/display/a-connect/product");
	}

	/**
	 * @Desc : 상품 목록 조회
	 * @Method Name : productList
	 * @Date : 2019. 4. 24.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/product/list")
	public void productList(Parameter<PdProductSearchVO> parameter) throws Exception {

		// 검색 타입 없는 경우 검색어도 null 설정
		if (UtilsText.isEmpty(parameter.get().getSearchItemType())) {
			parameter.get().setSearchItemKeyword(null);
		}

		Pageable<PdProductSearchVO, PdProduct> pageable = new Pageable<PdProductSearchVO, PdProduct>(parameter);
		pageable.getBean().organizeParameters();
		pageable.getBean().setMmnyPrdtYn(Const.BOOLEAN_TRUE);
		pageable.getBean().setAprvStatCode(CommonCode.APRV_STAT_CODE_CONFIRM); // 승인완료 상품 조회
		Page<PdProduct> productList = this.productService.searchProduct(pageable);

		this.writeJson(parameter, productList.getGrid());
	}

	/**
	 * @Desc : 상품 수정 (A-Connect 전시여부)
	 * @Method Name : productModify
	 * @Date : 2019. 4. 25.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/product/modify")
	public void productModify(Parameter<PdProduct[]> parameter) throws Exception {

		this.productService.updateProductForAconnect(parameter.get());
	}

	/**
	 * @Desc : 상품 상세 조회 (popup)
	 * @Method Name : productDetail
	 * @Date : 2019. 4. 24.
	 * @Author : 이가영
	 * @param parameter
	 * @return
	 */
	@GetMapping("/product/detail")
	public ModelAndView productDetail(Parameter<PdProduct> parameter) throws Exception {

		PdProduct pdProduct = this.productService.searchProductByPrimaryKey(parameter.get().getPrdtNo());

		parameter.addAttribute("pdProduct", pdProduct);

		String[] codeFields = { CommonCode.APRV_STAT_CODE, CommonCode.SELL_STAT_CODE, CommonCode.ORG_PLACE_CODE };
		String stdCtgrNo = pdProduct.getStdCtgrNo();

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();

		parameter.addAttribute("codeCombo", pair.getFirst());
		parameter.addAttribute("aprvStatCodeList", codeList.get(CommonCode.APRV_STAT_CODE));
		parameter.addAttribute("sellStatCodeList", codeList.get(CommonCode.SELL_STAT_CODE));
		parameter.addAttribute("orgPlaceCodeList", codeList.get(CommonCode.ORG_PLACE_CODE));
		parameter.addAttribute("stdCtgrName", standardCategoryService.getStandardCategoryName(stdCtgrNo));

		return forward("/display/a-connect/product-detail");
	}

	/**
	 * @Desc : 이벤트 관리
	 * @Method Name : event
	 * @Date : 2019. 4. 25.
	 * @Author : 이가영
	 * @param parameter
	 * @return
	 */
	@GetMapping("/event")
	public ModelAndView event(Parameter<?> parameter) throws Exception {

		return forward("/display/a-connect/event");
	}

	/**
	 * @Desc : 이벤트 목록 조회
	 * @Method Name : eventList
	 * @Date : 2019. 4. 25.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/event/list")
	public void eventList(Parameter<CmCatalogEvent> parameter) throws Exception {

		Pageable<CmCatalogEvent, CmCatalogEvent> pageable = new Pageable<CmCatalogEvent, CmCatalogEvent>(parameter);
		Page<CmCatalogEvent> eventList = this.catalogEventService.getCmCatalogEventList(pageable);

		this.writeJson(parameter, eventList.getGrid());
	}

	/**
	 * @Desc : 이벤트 상세 조회
	 * @Method Name : eventDetail
	 * @Date : 2019. 4. 25.
	 * @Author : 이가영
	 * @param parameter
	 * @return
	 */
	@GetMapping("/event/detail")
	public ModelAndView eventDetail(Parameter<CmCatalogEvent> parameter) throws Exception {

		parameter.addAttribute("event", catalogEventService.getCmCatalogEvent(parameter.get()));

		parameter.addAttribute("storeList",
				catalogEventService.getCmCatalogEventStoreListByCtlgEventNo(parameter.get().getCtlgEventNo()));

		return forward("/display/a-connect/event-detail");
	}

	/**
	 * @Desc : 이벤트 추가
	 * @Method Name : eventAdd
	 * @Date : 2019. 4. 26.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/event/add")
	public void eventAdd(Parameter<CmCatalogEvent> parameter) throws Exception {

		parameter.validate();

		CmCatalogEvent cmCatalogEvent = parameter.get();

		catalogEventService.insertCmCatalogEvent(cmCatalogEvent);
	}

	/**
	 * @Desc : 이벤트 수정
	 * @Method Name : eventModify
	 * @Date : 2019. 4. 26.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/event/modify")
	public void eventModify(Parameter<CmCatalogEvent> parameter) throws Exception {

		CmCatalogEvent cmCatalogEvent = parameter.get();

		catalogEventService.updateCmCatalogEvent(cmCatalogEvent);
	}

}