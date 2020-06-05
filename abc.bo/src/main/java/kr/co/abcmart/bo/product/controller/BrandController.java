package kr.co.abcmart.bo.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.product.model.master.DpBrand;
import kr.co.abcmart.bo.product.service.BrandService;
import kr.co.abcmart.bo.product.vo.DpBrandSearchVO;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.message.Message;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("product/brand")
public class BrandController extends BaseController {

	@Autowired
	private BrandService brandService;

	@Autowired
	private SiteService siteService;

	/**
	 * @Desc : 브랜드 목록 화면
	 * @Method Name : getList
	 * @Date : 2019. 2. 11.
	 * @Author : tennessee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public ModelAndView getList(Parameter<?> parameter) throws Exception {
		log.debug("브랜드 목록 페이지");

		List<SySite> siteList = this.siteService.getSiteList();

		String joinedSiteAll = siteList.stream().map(SySite::getSiteName).collect(Collectors.joining(","));

		String joinedSiteNames = siteList.stream().map(SySite::getSiteName).collect(Collectors.joining("|"));
		String joinedSiteNos = siteList.stream().map(SySite::getSiteNo).collect(Collectors.joining("|"));

		parameter.addAttribute("siteGubunText", String.join("|", joinedSiteAll, joinedSiteNames));
		parameter.addAttribute("siteGubunCode", String.join("|", "", joinedSiteNos));

		parameter.addAttribute("searchConditionSiteList", siteList); // 사이트 정보

		return forward("/product/brand/brand-list");
	}

	/**
	 * @Desc : 브랜드 목록 조회
	 * @Method Name : searchBrandList
	 * @Date : 2019. 2. 21.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("brand")
	public void searchBrandList(Parameter<DpBrandSearchVO> parameter) throws Exception {
		log.debug("브랜드 목록 조회");
		Pageable<DpBrandSearchVO, DpBrand> pageable = new Pageable<DpBrandSearchVO, DpBrand>(parameter);
		Page<DpBrand> brandList = this.brandService.searchBrandList(pageable);

		this.writeJson(parameter, brandList.getGrid());
	}

	/**
	 * @Desc : 브랜드 상세 화면
	 * @Method Name : create
	 * @Date : 2019. 2. 11.
	 * @Author : tennessee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("create")
	public ModelAndView create(Parameter<?> parameter) throws Exception {

		parameter.addAttribute("searchConditionSiteList", this.siteService.getSiteList()); // 사이트 정보
		parameter.addAttribute("chnnlList", this.siteService.getUseChannelListBySiteNo(Const.SITE_NO_ART));
		parameter.addAttribute("brandInfoStatus", Const.CRUD_C);
		return forward("/product/brand/brand-detail");
	}

	/**
	 * @Desc : 브랜드 저장
	 * @Method Name : save
	 * @Date : 2019. 2. 11.
	 * @Author : tennessee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("save")
	public void save(Parameter<DpBrand> parameter) throws Exception {
		log.debug("브랜드 정보 저장");
		Map<String, Object> resultMap = new HashMap<>();

		parameter.validate();

		DpBrand brand = parameter.get();
		log.debug("brand > {}", brand);

		// 브랜드번호 중복체크
		int count = this.brandService.getBrandNoCount(brand);
		if (count > 0) {
			resultMap.put("resultCd", Const.BOOLEAN_FALSE);
			resultMap.put("resultMsg", Message.getMessage("product.error.brand.dupBrandNo"));
		} else {
			this.brandService.inputBrand(brand);
			resultMap.put("resultCd", Const.BOOLEAN_TRUE);
			resultMap.put("brand", brand);
		}

		this.writeJson(parameter, resultMap);

	}

	/**
	 * @Desc : 브랜드 수정
	 * @Method Name : modify
	 * @Date : 2019. 2. 11.
	 * @Author : tennessee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("modify")
	public void modify(Parameter<DpBrand> parameter) throws Exception {
		log.debug("브랜드 정보 수정");
		Map<String, Object> resultMap = new HashMap<>();

		parameter.validate();

		if (!parameter.isValidation()) {
			throw new ValidationException();
		}

		log.debug("parameter > {}", parameter.get());

		DpBrand brand = parameter.create(DpBrand.class);

		log.debug("brand > {}", brand);

		this.brandService.updateBrand(brand);

		resultMap.put("resultCd", Const.BOOLEAN_TRUE);
		resultMap.put("brand", brand);
		this.writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 브랜드 상세정보 읽기
	 * @Method Name : searchDetail
	 * @Date : 2019. 2. 11.
	 * @Author : tennessee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("detail")
	public ModelAndView searchDetail(Parameter<DpBrandSearchVO> parameter) throws Exception {
		DpBrandSearchVO searchData = parameter.get();

		if (StringUtils.isNotBlank(searchData.getBrandNo())) {
			DpBrand brand = this.brandService.searchBrand(searchData);
			parameter.addAttribute("brand", brand);

			if (!"10001".equals(brand.getSiteNo())) {
				parameter.addAttribute("mallPcList", this.brandService.searchMallPc(searchData));
				parameter.addAttribute("mallMoList", this.brandService.searchMallMo(searchData));
			}
		}

		parameter.addAttribute("searchConditionSiteList", this.siteService.getSiteList()); // 사이트 정보
		parameter.addAttribute("chnnlList", this.siteService.getUseChannelListBySiteNo(Const.SITE_NO_ART));
		parameter.addAttribute("brandInfoStatus", Const.CRUD_U);

		return forward("/product/brand/brand-detail");
	}

	/**
	 * @Desc : 브랜드 ID 중복 조회
	 * @Method Name : checkDuplicationId
	 * @Date : 2019. 2. 11.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("check/id")
	public void checkDuplicationId(Parameter<DpBrandSearchVO> parameter) throws Exception {
		DpBrandSearchVO dpBrandSearch = parameter.create(DpBrandSearchVO.class);
		Boolean isDuplication = this.brandService.isDuplicateBrandId(dpBrandSearch);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", isDuplication);

		this.writeJson(parameter, result);
	}

}
