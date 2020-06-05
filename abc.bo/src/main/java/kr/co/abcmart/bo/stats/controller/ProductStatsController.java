package kr.co.abcmart.bo.stats.controller;

import java.util.Arrays;
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

import kr.co.abcmart.bo.product.model.master.SyStandardCategory;
import kr.co.abcmart.bo.product.service.StandardCategoryService;
import kr.co.abcmart.bo.stats.model.master.SaProductStats;
import kr.co.abcmart.bo.stats.service.OrderRealTimeStatsService;
import kr.co.abcmart.bo.stats.service.ProductStatsService;
import kr.co.abcmart.bo.stats.service.SalesStatsService;
import kr.co.abcmart.bo.stats.vo.ProductStatsSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.CommonCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 회원통계
 * @FileName : MemberStatsController.java
 * @Project : abc.bo
 * @Date : 2019. 6. 17.
 * @Author : Kimyounghyun
 */
@Slf4j
@Controller
@RequestMapping("/stats/etc")
public class ProductStatsController extends BaseController {

	@Autowired
	private ProductStatsService productStatsService;

	@Autowired
	private OrderRealTimeStatsService orderRealTimeService;

	@Autowired
	private SalesStatsService salesStatsService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private StandardCategoryService standardCategoryService;

	@Autowired
	private SiteService siteService;

	/**
	 * @Desc : 상품 등록 현황
	 * @Method Name : productStatsMain
	 * @Date : 2019. 7. 26.
	 * @Author : 백인천
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/product-stats-main")
	public ModelAndView productStatsMain(Parameter<?> parameter) throws Exception {
		parameter.addAttribute("searchConditionSiteList", this.siteService.getSiteList()); // 사이트 정보
		return forward("/stats/etc/product-stats-main");
	}

	/**
	 * @Desc : 상품 등록 리스트 조회
	 * @Method Name : productStatsList
	 * @Date : 2019. 7. 26.
	 * @Author : 백인천
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/product-list")
	public void productStatsList(Parameter<ProductStatsSearchVO> parameter) throws Exception {

		Pageable<ProductStatsSearchVO, SaProductStats> pageable = new Pageable<>(parameter);
		Page<SaProductStats> page = productStatsService.getProductStatsList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 상품 등록 현황 리스트 엑셀다운로드
	 * @Method Name : downloadProductStatsExcel
	 * @Date : 2019. 7. 26.
	 * @Author : 백인천
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-productStatsExcel")
	public void downloadProductStatsExcel(Parameter<ProductStatsSearchVO> parameter) throws Exception {
		List<SaProductStats> list = productStatsService.getProductStatsListForExcel(parameter.get());

		ExcelValue excelValue = ExcelValue.builder(2, 0).columnNames(Arrays.asList("vndrName", "totalCount", "sellWait",
				"sellSelling", "sellPause", "sellStop", "stgTemp", "stgWait", "stgReturn")).data(list).build();

		parameter.downloadExcelTemplate("stats/etc/excel/productStats", excelValue);
	}

	/**
	 * @Desc : 공유하기 현황 화면
	 * @Method Name : readShareStatus
	 * @Date : 2019. 8. 1.
	 * @Author : bje0507
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sns-share-main")
	public ModelAndView readShareStatus(Parameter<?> parameter) throws Exception {

		orderRealTimeService.getStatusSearchCondition(parameter);
		salesStatsService.getToday(parameter);

		String[] codeFields = { CommonCode.DEVICE_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		parameter.addAttribute("codeList", pair.getSecond()); // 디바이스 코드 목록
		parameter.addAttribute("searchConditionSiteList", this.siteService.getSiteList()); // 사이트 정보
		return forward("/stats/etc/sns-share-main");
	}

	/**
	 * @Desc : 공유하기 현황 조회
	 * @Method Name : shareStatsList
	 * @Date : 2019. 8. 1.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/share-list")
	public void shareStatsList(Parameter<ProductStatsSearchVO> parameter) throws Exception {

		Pageable<ProductStatsSearchVO, SaProductStats> pageable = new Pageable<ProductStatsSearchVO, SaProductStats>(
				parameter);
		Page<SaProductStats> page = productStatsService.getShareStatsList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 공유하기 현황 엑셀
	 * @Method Name : downloadSnsStatsExcel
	 * @Date : 2019. 8. 14.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-snsStatsExcel")
	public void downloadSnsStatsExcel(Parameter<ProductStatsSearchVO> parameter) throws Exception {
		Pageable<ProductStatsSearchVO, SaProductStats> pageable = new Pageable<ProductStatsSearchVO, SaProductStats>(
				parameter);
		ProductStatsSearchVO bean = pageable.getBean();
		bean.setIsExcel("Y");
		pageable.setBean(bean);
		Page<SaProductStats> page = productStatsService.getShareStatsList(pageable);

		ExcelValue excelValue = ExcelValue.builder(2, 0).columnNames(Arrays.asList("snsDtm", "snsTotal", "sns10005",
				"sns10000", "sns10001", "sns10002", "sns10003", "sns10004", "sns10006")).data(page.getContent())
				.build();

		parameter.downloadExcelTemplate("stats/etc/excel/snsStats", excelValue);
	}

	/**
	 * @Desc : 입점사 수수료 변경이력 조회 화면
	 * @Method Name : readCommissionStatus
	 * @Date : 2019. 8. 2.
	 * @Author : bje0507
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/commission-stats-main")
	public ModelAndView readCommissionStatus(Parameter<?> parameter) throws Exception {

		SyStandardCategory params = new SyStandardCategory();
		params.setLevel("1");
		parameter.addAttribute("searchConditionStandardCategoryList",
				this.standardCategoryService.getStandardCategoryList(params)); // 표준 카테고리 (대)
		parameter.addAttribute("searchConditionSiteList", this.siteService.getSiteList()); // 사이트 정보
		return forward("/stats/etc/commission-stats-main");
	}

	/**
	 * @Desc : 입점사 수수료 변경이력 조회 테이블
	 * @Method Name : cmsnStatsList
	 * @Date : 2019. 8. 5.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/cmsn-list")
	public void cmsnStatsList(Parameter<ProductStatsSearchVO> parameter) throws Exception {

		Pageable<ProductStatsSearchVO, SaProductStats> pageable = new Pageable<ProductStatsSearchVO, SaProductStats>(
				parameter);
		Page<SaProductStats> page = productStatsService.getCmsnStatsList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 입점사 수수료 변경이력 조회 테이블 엑셀
	 * @Method Name : downloadCmsnStatsExcel
	 * @Date : 2019. 8. 13.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-cmsnStatsExcel")
	public void downloadCmsnStatsExcel(Parameter<ProductStatsSearchVO> parameter) throws Exception {
		Pageable<ProductStatsSearchVO, SaProductStats> pageable = new Pageable<ProductStatsSearchVO, SaProductStats>(
				parameter);
		ProductStatsSearchVO bean = pageable.getBean();
		bean.setIsExcel("Y");
		pageable.setBean(bean);
		Page<SaProductStats> page = productStatsService.getCmsnStatsList(pageable);

		ExcelValue excelValue = ExcelValue.builder(1, 0)
				.columnNames(Arrays.asList("siteName", "vndrName", "brandName", "stdCtgr", "pdCode", "onPdCode",
						"pdName", "sellYn", "dispYn", "beforeRate", "afterRate", "rateType", "rgstDate", "rgsterName"))
				.data(page.getContent()).build();

		parameter.downloadExcelTemplate("stats/etc/excel/cmsnStats", excelValue);
	}

}
