package kr.co.abcmart.bo.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.product.model.master.DpSizeChart;
import kr.co.abcmart.bo.product.model.master.SyStandardCategory;
import kr.co.abcmart.bo.product.service.SizeChartService;
import kr.co.abcmart.bo.product.service.StandardCategoryService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("product/sizechart")
public class SizeChartController extends BaseController {

	@Autowired
	private SizeChartService sizeChartService;

	@Autowired
	private StandardCategoryService standardCategoryService;

	/**
	 * @Desc : 사이즈조견표 관리
	 * @Method Name : sizeChart
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ModelAndView sizeChart(Parameter<?> parameter) throws Exception {

		SyStandardCategory syStandardCategory = new SyStandardCategory();
		syStandardCategory.setLevel("1");

		parameter.addAttribute("standardCategoryList",
				standardCategoryService.getStandardCategoryList(syStandardCategory));
		log.debug("sizechart");

		return forward("/product/sizechart");
	}

	/**
	 * @Desc : 사이즈조견표 관리(리스트조회)
	 * @Method Name : readSizeChartList
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/list/read")
	public void readSizeChartList(Parameter<DpSizeChart> parameter) throws Exception {

		Pageable<DpSizeChart, DpSizeChart> pageable = new Pageable<>(parameter);

		Page<DpSizeChart> page = sizeChartService.getSizeChartList(pageable);

		writeJson(parameter, page.getGrid());

	}

	/**
	 * @Desc : 사이즈조견표 관리(상세)
	 * @Method Name : sizeChartDetail
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/detail")
	public ModelAndView sizeChartDetail(Parameter<DpSizeChart> parameter) throws Exception {

		DpSizeChart dpSizeChart = parameter.get();

		if (dpSizeChart.getSizeChartSeq() != null) {
			dpSizeChart = sizeChartService.getSizeChart(dpSizeChart);
			log.debug("sizeChart :: {} ", dpSizeChart);
		}

		// 표준 대 카테고리 param
		SyStandardCategory syStandardCategory = new SyStandardCategory();
		syStandardCategory.setLevel("1");

		parameter.addAttribute("dpSizeChart", dpSizeChart);
		parameter.addAttribute("standardCategoryList",
				standardCategoryService.getStandardCategoryList(syStandardCategory));

		return forward("/product/sizechart-detail");
	}

	/**
	 * @Desc : 사이즈조견표 관리(저장)
	 * @Method Name : saveSizeChart
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/save")
	public void saveSizeChart(Parameter<DpSizeChart> parameter) throws Exception {

		parameter.validate();

		DpSizeChart dpSizeChart = parameter.get();

		sizeChartService.insertSizeChart(dpSizeChart);
	}

	/**
	 * @Desc : 사이즈조견표 관리(수정)
	 * @Method Name : modifySizeChart
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/modify")
	public void modifySizeChart(Parameter<DpSizeChart> parameter) throws Exception {

		parameter.validate();

		DpSizeChart dpSizeChart = parameter.get();

		sizeChartService.updateSizeChart(dpSizeChart);
	}

}
