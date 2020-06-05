package kr.co.abcmart.bo.display.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.ValidationException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.display.model.master.DpWebzine;
import kr.co.abcmart.bo.display.model.master.DpWebzineDetailImage;
import kr.co.abcmart.bo.display.model.master.DpWebzineProduct;
import kr.co.abcmart.bo.display.service.DisplayContentsService;
import kr.co.abcmart.bo.display.vo.DpWebzineSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
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
@RequestMapping("display/contents/")
public class DisplayContentsController extends BaseController {

	@Autowired
	private DisplayContentsService displayContentsService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	SiteService siteService;

	/**
	 * 
	 * @Desc : 웹진관리
	 * @Method Name : webzine
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/webzine")
	public ModelAndView webzine(Parameter<?> parameter) throws Exception {

//		List<SySite> siteList = siteService.getUseSiteList();
//		parameter.addAttribute("siteList", siteList);

		return forward("/display/contents/webzine");
	}

	/**
	 * 
	 * @Desc : 웹진 관리(상세)
	 * @Method Name : webzineDetail
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/webzine/detail")
	public ModelAndView webzineDetail(Parameter<?> parameter) throws Exception {

		DpWebzine dpWebzine = parameter.create(DpWebzine.class);
		DpWebzineProduct dpWebzineProduct = parameter.create(DpWebzineProduct.class);
		List<DpWebzineProduct> dpWebzineProductList = new ArrayList<>();

		// 화면에 사용되는 공통코드 조회
		String[] codeFields = { CommonCode.SELL_STAT_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
				.getCodeListByGroupByCombo(codeFields, false);

		if (dpWebzine.getWbznSeq() != null) {
			dpWebzine = displayContentsService.getDpWebzine(dpWebzine);
			dpWebzineProductList = displayContentsService.getDpWebzineProductList(dpWebzineProduct);

			DpWebzineDetailImage dpWebzineDetailImage = parameter.create(DpWebzineDetailImage.class);
			List<DpWebzineDetailImage> imageList = displayContentsService
					.getDpWebzineDetailImageList(dpWebzineDetailImage);
			parameter.addAttribute("imageList", imageList);
		}

		parameter.addAttribute("sellStatCodes", pair.getFirst()); // 그리드 공통코드. 판매상태
		parameter.addAttribute("dpWebzine", dpWebzine);
		parameter.addAttribute("dpWebzineProductList", dpWebzineProductList);

		return forward("/display/contents/webzine-detail");
	}

	/**
	 * 
	 * @Desc : 웹진관리(저장)
	 * @Method Name : saveWebzine
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/save")
	public void saveWebzine(Parameter<DpWebzine> parameter) throws Exception {

		parameter.validate();

		if (!parameter.isValidation()) {
			throw new ValidationException();
		}

		DpWebzine dpWebzine = parameter.get();
		dpWebzine.setWbznTitleText(parameter.getString("wbznTitleText", false));

		displayContentsService.insertWebzine(dpWebzine);

		writeJson(parameter, dpWebzine);
	}

	/**
	 * 
	 * @Desc : 웹진관리(수정)
	 * @Method Name : modifyWebzine
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/modify")
	public void modifyWebzine(Parameter<DpWebzine> parameter) throws Exception {

		parameter.validate();

		if (!parameter.isValidation()) {
			throw new ValidationException();
		}

		DpWebzine dpWebzine = parameter.get();
		dpWebzine.setWbznTitleText(parameter.getString("wbznTitleText", false));

		displayContentsService.updateWebzine(dpWebzine);
		dpWebzine.setDpWebzineDetailImage(null);
		writeJson(parameter, dpWebzine);
	}

	/**
	 * 
	 * @Desc :웹진관리(리스트조회)
	 * @Method Name : readWebzineList
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/list/read")
	public void readWebzineList(Parameter<DpWebzineSearchVO> parameter) throws Exception {

		Pageable<DpWebzineSearchVO, DpWebzine> pageable = new Pageable<>(parameter);

		Page<DpWebzine> page = displayContentsService.getDpWebzineList(pageable);

		writeJson(parameter, page.getGrid());

	}

	/**
	 * 
	 * @Desc : OTS 목록 조회
	 * @Method Name : otsMagazine
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/ots")
	public ModelAndView otsMagazine(Parameter<?> parameter) throws Exception {

		parameter.addAttribute("wbznType", "O");

		return forward("/display/contents/ots");
	}

	/**
	 * 
	 * @Desc : OTS(상세)
	 * @Method Name : otsMagazineDetail
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/ots/detail")
	public ModelAndView otsMagazineDetail(Parameter<DpWebzine> parameter) throws Exception {

		DpWebzine dpWebzine = parameter.get();
		DpWebzineProduct dpWebzineProduct = parameter.create(DpWebzineProduct.class);
		List<DpWebzineProduct> dpWebzineProductList = new ArrayList<>();

		// 화면에 사용되는 공통코드 조회
		String[] codeFields = { CommonCode.SELL_STAT_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
				.getCodeListByGroupByCombo(codeFields, false);

		if (dpWebzine.getWbznSeq() != null) {
			dpWebzine = displayContentsService.getDpWebzine(dpWebzine);
			dpWebzineProductList = displayContentsService.getDpWebzineProductList(dpWebzineProduct);

		}

		parameter.addAttribute("dpWebzine", dpWebzine);
		parameter.addAttribute("sellStatCodes", pair.getFirst()); // 그리드 공통코드. 판매상태
		parameter.addAttribute("dpWebzineProductList", dpWebzineProductList);

		return forward("/display/contents/ots-detail");
	}

	/**
	 * 
	 * @Desc : 에디터 이미지 업로드
	 * @Method Name : editorUpload
	 * @Date : 2019. 11. 5.
	 * @Author : SANTA
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/editer/upload")
	public void editorUpload(Parameter<DpWebzine> parameter) throws Exception {

		DpWebzine dpWebzine = parameter.get();

		Map<String, Object> result = displayContentsService.uploadImageByEditor(dpWebzine.getUpload());

		writeJson(parameter, result);

	}

}