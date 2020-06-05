package kr.co.abcmart.bo.display.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.display.model.master.DpCategory;
import kr.co.abcmart.bo.display.model.master.DpCategoryCorner;
import kr.co.abcmart.bo.display.model.master.DpCategoryProduct;
import kr.co.abcmart.bo.display.model.master.DpDisplayTemplateCorner;
import kr.co.abcmart.bo.display.service.DisplayCategoryService;
import kr.co.abcmart.bo.display.vo.DpExhibitionPageVO;
import kr.co.abcmart.bo.product.model.master.SyStandardCategory;
import kr.co.abcmart.bo.product.service.StandardCategoryService;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.message.Message;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("display/category")
public class DisplayCategoryController extends BaseController {

	@Autowired
	private DisplayCategoryService displayCategoryService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private StandardCategoryService standardCategoryService;

	@Autowired
	private Environment env;

	/**
	 * 
	 * @Desc : 전시 카테고리 관리
	 * @Method Name : category
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ModelAndView category(Parameter<DpCategory> parameter) throws Exception {

		List<SySiteChnnl> chnnlList = siteService.getUseChannelList();

		parameter.addAttribute("chnnlList", chnnlList);

		return forward("/display/category/category");
	}

	/**
	 * 
	 * @Desc : 전시카테고리관리(리스트조회)
	 * @Method Name : readList
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/list/read")
	public void readList(Parameter<DpCategory> parameter) throws Exception {

		List<DpCategory> data = displayCategoryService.getDpCategoryList(parameter.get());
		List<SySite> siteList = siteService.getSiteList();

		DpCategory root = new DpCategory();
		root.setCtgrName(parameter.get().getChnnlName());
		root.setSiteNo(parameter.get().getSiteNo());
		root.setChnnlNo(parameter.get().getChnnlNo());
		root.setLevel("0");
		data.add(0, root);

		Map<String, Object> resultData = new HashMap<String, Object>();
		resultData.put("data", data);
		resultData.put("siteList", siteList);

		writeJson(parameter, resultData);
	}

	/**
	 * 
	 * @Desc : 전시카테고리관리(depth별 표준카테고리 조회)
	 * @Method Name : readstdCtgr
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read/stdCtgr")
	@Deprecated
	public void readstdCtgr(Parameter<SyStandardCategory> parameter) throws Exception {

		String stdCtgrNo = parameter.get().getStdCtgrNo();
		Map<String, Object> resultData = new HashMap<>();

		if (stdCtgrNo != null) {

			resultData = standardCategoryService.getStandardCategoryMap(parameter.get());
		}

		writeJson(parameter, resultData);
	}

	/**
	 * 
	 * @Desc : 전시카테고리관리(등록/수정)
	 * @Method Name : save
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/save")
	public void save(Parameter<DpCategory> parameter) throws Exception {

		DpCategory category = parameter.get();

		if (category.getCtgrLevel() > 0) {
			parameter.validate();

			// 전시카테고리명 변환되서 들어가지 않게 수정
			category.setCtgrName(parameter.getString("ctgrName", false));

			if (category.getCtgrNo() == null) {
				displayCategoryService.insertDpCategoryList(category);
			} else {
				// 전시 안함 처리 시 하위카테고리 사용 여부 확인 valid
				if (UtilsText.equals(category.getDispYn(), "N")) {

					DpCategory subCategory = new DpCategory();
					subCategory.setChnnlNo(category.getChnnlNo());
					subCategory.setSiteNo(category.getSiteNo());
					subCategory.setUseYn("Y");
					subCategory.setUpCtgrNo(category.getCtgrNo());

					List<DpCategory> subList = displayCategoryService.getDpCategoryList(subCategory);
					if (subList.size() > 0) {
						throw new ValidationException(
								Message.getMessage("display.category.valid.usingSubCategoryList"));
					}
				}
				displayCategoryService.updateDpCategoryList(category);
			}
		} else {

			throw new ValidationException(Message.getMessage("display.category.valid.root"));
		}

	}

	/**
	 * 
	 * @Desc : 전시카테고리관리(정렬 순서 저장)
	 * @Method Name : saveSort
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/save/sort")
	public void saveSort(Parameter<DpCategory[]> parameter) throws Exception {

		displayCategoryService.updateDpCategorySort(parameter);
	}

	/**
	 * 
	 * @Desc : 전시콘텐츠관리(목록 조회)
	 * @Method Name : readContentsList
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/contents/list/read")
	@ResponseBody
	public void readContentsList(Parameter<DpCategoryCorner> parameter) throws Exception {

		Pageable<DpCategoryCorner, DpDisplayTemplateCorner> pageable = new Pageable<>(parameter);

		Page<DpDisplayTemplateCorner> page = displayCategoryService.getDpCategoryCornerList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * 
	 * @Desc : 전시카테고리관리(상품조회)
	 * @Method Name : readProductList
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/list/product")
	@Deprecated
	public void readProductList(Parameter<DpCategoryProduct> parameter) throws Exception {

		writeJson(parameter, displayCategoryService.searchProductByCtgrNo(parameter));
	}

	/**
	 * 
	 * @Desc : 전시 카테고리 리스트 조회(selectbox 용)
	 * @Method Name : readCtgrList
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/list/category")
	@ResponseBody
	public void readCtgrList(Parameter<DpCategory> parameter) throws Exception {

		writeJson(parameter, displayCategoryService.getDpCategoryList(parameter.get()));
	}

	/**
	 * 
	 * @Desc : 전시카테고리 조회(상품)
	 * @Method Name : searchCtgrList
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/search")
	@Deprecated
	public void searchCtgrList(Parameter<DpCategory> parameter) throws Exception {

		writeJson(parameter, displayCategoryService.getDpCategoryListBySelf(parameter.get()));
	}

	/**
	 * @Desc : 한 상품에 대한 전시페이지 정보 조회
	 * @Method Name : readExhibitionPageForProduct
	 * @Date : 2019. 4. 23.
	 * @Author : tennessee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/product/exhibition/page")
	public void readExhibitionPageForProduct(Parameter<DpExhibitionPageVO> parameter) throws Exception {
		Pageable<DpExhibitionPageVO, DpExhibitionPageVO> pageable = new Pageable<DpExhibitionPageVO, DpExhibitionPageVO>(
				parameter);
		Page<DpExhibitionPageVO> productList = this.displayCategoryService.getExhibitionPageForProduct(pageable);
		this.writeJson(parameter, productList.getGrid());
	}

	/**
	 * @Desc : 카테고리 번호 및 그와 연계된 카테고리에 연결된 상품이 있는지 확인
	 * @Method Name : hasProduct
	 * @Date : 2019. 6. 20.
	 * @Author : bje0507
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/check")
	public void hasProduct(Parameter<DpCategory> parameter) throws Exception {
		DpCategory category = parameter.get();
		log.debug(category.getCtgrNo());
		this.writeJson(parameter, displayCategoryService.hasProduct(category.getCtgrNo()));
	}

	/**
	 * 
	 * @Desc : fo, mo domain 조회
	 * @Method Name : getFrontDomain
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/front-domain")
	public void getFrontDomain(Parameter<DpCategory> parameter) throws Exception {
		DpCategory category = parameter.get();

		Map<String, Object> domainData = new HashMap<String, Object>();

		if (UtilsText.equals(category.getChnnlNo(), "10000")) {
			domainData.put("pc", env.getProperty("service.domain.art.fo"));
			domainData.put("mobile", env.getProperty("service.domain.art.mo"));
		} else if (UtilsText.equals(category.getChnnlNo(), "10001")) {
			domainData.put("pc", env.getProperty("service.domain.abc.fo"));
			domainData.put("mobile", env.getProperty("service.domain.abc.mo"));
		} else if (UtilsText.equals(category.getChnnlNo(), "10002")) {
			domainData.put("pc", env.getProperty("service.domain.gs.fo"));
			domainData.put("mobile", env.getProperty("service.domain.gs.mo"));
		} else if (UtilsText.equals(category.getChnnlNo(), "10003")) {
			domainData.put("pc", env.getProperty("service.domain.ots.fo"));
			domainData.put("mobile", env.getProperty("service.domain.ots.mo"));
		}

		this.writeJson(parameter, domainData);
	}

}