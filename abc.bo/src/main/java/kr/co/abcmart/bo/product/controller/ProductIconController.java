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

import kr.co.abcmart.bo.product.model.master.CmProductIcon;
import kr.co.abcmart.bo.product.model.master.PdProductIcon;
import kr.co.abcmart.bo.product.service.ProductIconService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.CommonCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("product/icon")
public class ProductIconController extends BaseController {

	@Autowired
	private ProductIconService productIconService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private CommonCodeService commonCodeService;

	/**
	 * 
	 * @Desc : 상품 아이콘 관리
	 * @Method Name : icon
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ModelAndView icon(Parameter<?> parameter) throws Exception {

		Pair<JSONObject, List<SySite>> siteInfo = siteService.getSiteListByCombo();

		parameter.addAttribute("siteCombo", siteInfo.getFirst());

		String[] codeFields = { CommonCode.SELL_STAT_CODE };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();

		parameter.addAttribute("codeCombo", pair.getFirst());

		return forward("/product/icon/icon");
	}

	/**
	 * 
	 * @Desc : 상품 아이콘 리스트 조회
	 * @Method Name : readIconList
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/list/read")
	public void readIconList(Parameter<CmProductIcon> parameter) throws Exception {

		CmProductIcon params = parameter.get();

		Pageable<CmProductIcon, CmProductIcon> pageable = new Pageable<>(parameter);

		Page<CmProductIcon> page = productIconService.getIconList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * 
	 * @Desc : 상품 아이콘 이미지 저장
	 * @Method Name : saveIconImage
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/save/image")
	public void saveIconImage(Parameter<CmProductIcon> parameter) throws Exception {

		CmProductIcon params = parameter.get();

		CmProductIcon result = productIconService.setIconImage(params);

		writeJson(parameter, result);
	}

	/**
	 * 
	 * @Desc : 상품 아이콘 저장
	 * @Method Name : saveIconList
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/save")
	public void saveIconList(Parameter<CmProductIcon[]> parameter) throws Exception {

		CmProductIcon[] params = parameter.get();

		productIconService.setIcon(params);
	}

	/**
	 * 
	 * @Desc : 아이콘 대상 상품 리스트 조회
	 * @Method Name : iconMappingProductList
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/mapping-product/list")
	public void iconMappingProductList(Parameter<PdProductIcon> parameter) throws Exception {

		PdProductIcon params = parameter.get();

		Pageable<PdProductIcon, PdProductIcon> pageable = new Pageable<>(parameter);

		Page<PdProductIcon> page = productIconService.getIconMappingProductList(pageable);

		writeJson(parameter, page.getGrid());
	}

}