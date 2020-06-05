package kr.co.abcmart.bo.product.controller;

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

import kr.co.abcmart.bo.display.service.DisplayCategoryService;
import kr.co.abcmart.bo.product.model.master.SyHandlingPrecaution;
import kr.co.abcmart.bo.product.model.master.SyStandardCategory;
import kr.co.abcmart.bo.product.service.StandardCategoryService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("product/category-standard")
public class StandardCategoryController extends BaseController {

	@Autowired
	private StandardCategoryService standardCategoryService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private DisplayCategoryService displayCategoryService;

	/**
	 *
	 * @Desc : 표준 카테고리 관리
	 * @Method Name : standard
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ModelAndView standard(Parameter<?> parameter) throws Exception {

		String[] codeFields = { CommonCode.ITEM_CODE };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();

		parameter.addAttribute("itemCode", codeList.get(CommonCode.ITEM_CODE));

		parameter.addAttribute("chnnlList",
				siteService.getUseChannelList().stream()
						.filter(x -> UtilsText.equals(x.getSrchRelmExpsrYn(), Const.BOOLEAN_TRUE))
						.collect(Collectors.toList()));

		return forward("/product/category/standard/standard");
	}

	/**
	 * @Desc : 표준 카테고리 리스트 조회
	 * @Method Name : readList
	 * @Date : 2019. 2. 15.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/list/read")
	@ResponseBody
	public void readList(Parameter<SyStandardCategory> parameter) throws Exception {

		List<SyStandardCategory> data = standardCategoryService.getStandardCategoryList(parameter.get());

		// root 카테고리 설정
		SyStandardCategory root = new SyStandardCategory();
		root.setLevel("0");
		root.setStdCtgrName("표준카테고리");
		data.add(0, root);

		Map<String, Object> resultData = new HashMap<String, Object>();
		resultData.put("data", data);

		writeJson(parameter, resultData);
	}

	/**
	 * @Desc : 표준 카테고리 저장
	 * @Method Name : saveStandardCategory
	 * @Date : 2019. 2. 19.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/save")
	public void saveStandardCategory(Parameter<SyStandardCategory> parameter) throws Exception {

		SyStandardCategory syStandardCategory = parameter.get();

		if (!(UtilsText.isBlank(syStandardCategory.getStdCtgrName()))) {
			parameter.validate();
			standardCategoryService.setStandardCategory(syStandardCategory);
		}
	}

	/**
	 * @Desc : 표준 카테고리 정렬 순서 저장
	 * @Method Name : saveStandardCategorySort
	 * @Date : 2019. 2. 19.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/save/sort")
	public void saveStandardCategorySort(Parameter<SyStandardCategory[]> parameter) throws Exception {

		standardCategoryService.setStandardCategorySort(parameter);
	}

	/**
	 * @Desc : 취급주의사항 리스트 조회
	 * @Method Name : readHandlingPrecautionList
	 * @Date : 2019. 5. 14.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/list/handling-precaution")
	@ResponseBody
	public void readHandlingPrecautionList(Parameter<SyHandlingPrecaution> parameter) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		SyHandlingPrecaution syHandlingPrecaution = parameter.get();

		resultMap.put("handlingPrecautionList",
				standardCategoryService.getSyHandlingPrecautionById(syHandlingPrecaution.getStdCtgrNo()));

		// 카테고리 Level이 1Level 일 경우에만 전시 카테고리 정보를 가지고 온다.
		if (UtilsText.equals(syHandlingPrecaution.getLeafCtgrYn(), "Y")) {
			resultMap.put("dispCategoryList",
					displayCategoryService.getCategoryListByStdCtgrNo(syHandlingPrecaution.getStdCtgrNo()));
		}

		writeJson(parameter, resultMap);
	}

}