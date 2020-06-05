package kr.co.abcmart.bo.display.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.display.model.master.DpCategoryCorner;
import kr.co.abcmart.bo.display.model.master.DpCategoryCornerName;
import kr.co.abcmart.bo.display.model.master.DpCategoryCornerSet;
import kr.co.abcmart.bo.display.model.master.DpDisplayPageCorner;
import kr.co.abcmart.bo.display.model.master.DpDisplayPageCornerName;
import kr.co.abcmart.bo.display.model.master.DpDisplayPageCornerSet;
import kr.co.abcmart.bo.display.model.master.DpDisplayTemplateCorner;
import kr.co.abcmart.bo.display.model.master.DpDisplayTemplateCornerSet;
import kr.co.abcmart.bo.display.service.DisplayContentsPopupService;
import kr.co.abcmart.bo.display.service.DisplayTemplateService;
import kr.co.abcmart.bo.display.vo.DisplayContentsPopupVO;
import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.service.ProductService;
import kr.co.abcmart.bo.product.vo.PdProductSearchVO;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("display/contents/popup")
public class DisplayContentsPopupController extends BaseController {

	@Autowired
	private DisplayTemplateService displayTemplateService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private DisplayContentsPopupService displayContentsPopupService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private ProductService productService;

	/**
	 *
	 * @Desc : 전시 코너 기본정보 팝업
	 * @Method Name : corner
	 * @Date : 2019. 3. 11.
	 * @Author : SANTA
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/corner-info-pop")
	public ModelAndView corner(Parameter<DisplayContentsPopupVO> parameter) throws Exception {

		String[] codeFields = { CommonCode.CONT_TYPE_CODE, CommonCode.SELL_STAT_CODE };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		DisplayContentsPopupVO vo = parameter.get();

		parameter.addAttribute("codeMap", pair.getFirst()); // 그리드 공통코드. 판매상태
		parameter.addAttribute("codeList", pair.getSecond());
		parameter.addAttribute("deviceTypeName", vo.getDeviceTypeName());
		parameter.addAttribute("ctgrNo", vo.getCtgrNo());
		parameter.addAttribute("dispPageNo", vo.getDispPageNo());

		if (!UtilsText.isBlank(vo.getDispTmplNo())) {

			DpDisplayTemplateCornerSet dpDisplayTemplateCornerSet = new DpDisplayTemplateCornerSet();
			DpDisplayTemplateCorner dpDisplayTemplateCorner = new DpDisplayTemplateCorner();

			if (UtilsObject.isNotEmpty(vo)) {
				BeanUtils.copyProperties(dpDisplayTemplateCorner, vo);
			}

			dpDisplayTemplateCorner = displayTemplateService.getTemplateCorner(dpDisplayTemplateCorner);

			dpDisplayTemplateCornerSet.setDispTmplNo(dpDisplayTemplateCorner.getDispTmplNo());
			dpDisplayTemplateCornerSet.setDispTmplCornerSeq(dpDisplayTemplateCorner.getDispTmplCornerSeq());

			parameter.addAttribute("corner", dpDisplayTemplateCorner);
			parameter.addAttribute("map",
					displayTemplateService.getTemplateCornerSetListToMap(dpDisplayTemplateCornerSet));
		}

		return forward("/display/contents/popup/corner-info-pop");
	}

	/**
	 *
	 * @Desc : 전시 콘텐츠 코너명 조회
	 * @Method Name : readCornerNameList
	 * @Date : 2019. 3. 18.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/corner-name/list")
	public void readCornerNameList(Parameter<DisplayContentsPopupVO> parameter) throws Exception {

		DisplayContentsPopupVO vo = parameter.get();
		Map<String, Object> grid;

		if (!UtilsText.isBlank(vo.getCtgrNo())) {
			Page<DpCategoryCornerName> page = displayContentsPopupService.getDisplayCategoryCornerNameList(parameter);
			grid = page.getGrid();
		} else {
			Page<DpDisplayPageCornerName> page = displayContentsPopupService.getDisplayPageCornerNameList(parameter);
			grid = page.getGrid();
		}

		writeJson(parameter, grid);
	}

	/**
	 *
	 * @Desc : 전시 코너 콘텐츠 세트 조회
	 * @Method Name : readCornerSetList
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/corner-set/list")
	public void readCornerSetList(Parameter<DisplayContentsPopupVO> parameter) throws Exception {

		DisplayContentsPopupVO vo = parameter.get();
		Map<String, Object> grid;

		if (!UtilsText.isBlank(vo.getCtgrNo())) {
			Page<DpCategoryCornerSet> page = displayContentsPopupService.getDisplayCategoryCornerSetList(parameter);
			grid = page.getGrid();
		} else {
			Page<DpDisplayPageCornerSet> page = displayContentsPopupService.getDisplayPageCornerSetList(parameter);
			grid = page.getGrid();
		}

		writeJson(parameter, grid);
	}

	/**
	 *
	 * @Desc : 전시 콘텐츠 코너 셋 팝업
	 * @Method Name : cornerSet
	 * @Date : 2019. 3. 20.
	 * @Author : SANTA
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/corner-set-pop")
	public ModelAndView cornerSet(Parameter<DisplayContentsPopupVO> parameter) throws Exception {

		DisplayContentsPopupVO displayContentsPopupVO = parameter.get();

		parameter.addAttribute("param", displayContentsPopupVO);

		return forward("/display/contents/popup/corner-set-pop");
	}

	/**
	 *
	 * @Desc : 전시 콘텐츠 코너명 팝업
	 * @Method Name : cornerName
	 * @Date : 2019. 3. 20.
	 * @Author : SANTA
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/corner-name-pop")
	public ModelAndView cornerName(Parameter<DisplayContentsPopupVO> parameter) throws Exception {

		DisplayContentsPopupVO displayContentsPopupVO = parameter.get();

		parameter.addAttribute("data", displayContentsPopupVO);

		return forward("/display/contents/popup/corner-name-pop");
	}

	/**
	 *
	 * @Desc : 코너 콘텐츠 등록(인기상품) 팝업
	 * @Method Name : cornerProductPopular
	 * @Date : 2019. 7. 1.
	 * @Author : bje0507
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/corner-product-popular-pop")
	public ModelAndView cornerProductPopular(Parameter<DisplayContentsPopupVO> parameter) throws Exception {

		DisplayContentsPopupVO displayContentsPopupVO = parameter.get();

		// 화면에 사용되는 공통코드 조회
		String[] codeFields = { CommonCode.SELL_STAT_CODE, CommonCode.GENDER_GBN_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
				.getCodeListByGroupByCombo(codeFields, false);

		parameter.addAttribute("gridCodes", pair.getFirst()); // 그리드 공통코드. 판매상태
		parameter.addAttribute("data", displayContentsPopupVO);
		parameter.addAttribute("searchConditionSiteList", this.siteService.getSiteList()); // 사이트 정보
		parameter.addAttribute("searchConditionSiteChannelList", this.siteService.getUseChannelList()); // 사이트 채널 코드
		parameter.addAttribute("searchConditionGenderGbnCodeList", pair.getSecond().get(CommonCode.GENDER_GBN_CODE)); // 성별구분코드
		parameter.addAttribute("searchConditionSellStatCodeList", pair.getSecond().get(CommonCode.SELL_STAT_CODE)); // 판매상태코드

		return forward("/display/contents/popup/corner-product-popular-pop");
	}

	/**
	 * @Desc : 인기상품목록 조회
	 * @Method Name : searchProduct
	 * @Date : 2019. 7. 2.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/corner-product-popular-pop")
	public void searchProduct(Parameter<PdProductSearchVO> parameter) throws Exception {

		Pageable<PdProductSearchVO, PdProduct> pageable = new Pageable<PdProductSearchVO, PdProduct>(parameter);
		pageable.getBean().organizeParameters();
		pageable.getBean().setAprvStatCode(CommonCode.APRV_STAT_CODE_CONFIRM);
		Page<PdProduct> productList = this.productService.searchProduct(pageable);

		this.writeJson(parameter, productList.getGrid());
	}

	/**
	 * @Desc : 전시카테고리 코너 콘텐츠 코너명 등록
	 * @Method Name : saveName
	 * @Date : 2019. 3. 20.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/save/name")
	public void saveName(Parameter<?> parameter) throws Exception {

		DpCategoryCorner dpCategoryCorner = parameter.create(DpCategoryCorner.class);
		DpDisplayPageCorner dpDisplayPageCorner = parameter.create(DpDisplayPageCorner.class);

		// 전시카테고리 코너명 저장
		if (!UtilsText.isBlank(dpCategoryCorner.getCtgrNo())) {
			dpCategoryCorner.validate();

			displayContentsPopupService.saveCornerName(dpCategoryCorner);
			writeJson(parameter, dpCategoryCorner);
		} else {
			dpDisplayPageCorner.validate();

			displayContentsPopupService.saveCornerName(dpDisplayPageCorner);
			writeJson(parameter, dpDisplayPageCorner);
		}

	}

	/**
	 *
	 * @Desc : 전시카테고리 코너 콘텐츠 세트 등록
	 * @Method Name : saveSet
	 * @Date : 2019. 3. 20.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/save/set")
	public void saveSet(Parameter<?> parameter) throws Exception {

		DpCategoryCorner dpCategoryCorner = parameter.create(DpCategoryCorner.class);
		DpDisplayPageCorner dpDisplayPageCorner = parameter.create(DpDisplayPageCorner.class);

		if (!UtilsText.isBlank(dpCategoryCorner.getCtgrNo())) {
			dpCategoryCorner.validate();

			displayContentsPopupService.saveCornerSet(dpCategoryCorner);
			writeJson(parameter, dpCategoryCorner);
		} else {
			dpDisplayPageCorner.validate();

			displayContentsPopupService.saveCornerSet(dpDisplayPageCorner);
			writeJson(parameter, dpDisplayPageCorner);
		}
	}

	/**
	 *
	 * @Desc : 전시 콘텐츠 코너세트 삭제
	 * @Method Name : deleteSet
	 * @Date : 2019. 3. 29.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/delete/set")
	public void deleteSet(Parameter<DisplayContentsPopupVO> parameter) throws Exception {

		DisplayContentsPopupVO displayContentsPopupVO = parameter.get();

		if (!UtilsText.isBlank(displayContentsPopupVO.getCtgrNo())) {

			displayContentsPopupService.deleteCategoryCornerSet(displayContentsPopupVO);
		} else {
			displayContentsPopupService.deletePageCornerSet(displayContentsPopupVO);
		}

		writeJson(parameter, displayContentsPopupVO);

	}

	/**
	 *
	 * @Desc : 전시 콘텐츠 코너명 삭제
	 * @Method Name : deleteName
	 * @Date : 2019. 3. 29.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/delete/name")
	public void deleteName(Parameter<DisplayContentsPopupVO> parameter) throws Exception {

		DisplayContentsPopupVO displayContentsPopupVO = parameter.get();

		if (!UtilsText.isBlank(displayContentsPopupVO.getCtgrNo())) {

			displayContentsPopupService.deleteCategoryCornerName(displayContentsPopupVO);
		} else {
			displayContentsPopupService.deletePageCornerName(displayContentsPopupVO);
		}

		writeJson(parameter, displayContentsPopupVO);

	}

	/**
	 *
	 * @Desc : 전시 컨텐츠 파일 업로드
	 * @Method Name : fileUpload
	 * @Date : 2019. 8. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/file/upload")
	public void fileUpload(Parameter<DisplayContentsPopupVO> parameter) throws Exception {

		Map<String, Object> result = displayContentsPopupService.uploadImage(parameter.get());

		writeJson(parameter, result);

	}

}