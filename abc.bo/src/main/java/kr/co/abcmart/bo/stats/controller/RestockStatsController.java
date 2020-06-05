package kr.co.abcmart.bo.stats.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.stats.model.master.SaRestockStatus;
import kr.co.abcmart.bo.stats.service.RestockStatsService;
import kr.co.abcmart.bo.stats.vo.RestockStatsSearchVO;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/stats/restock")
public class RestockStatsController extends BaseController {
	@Autowired
	private RestockStatsService restockStatsService;

	@Autowired
	private SiteService siteService;

	/**
	 * @Desc : 재입고 알림 서비스 상품별 통계 화면
	 * @Method Name : readProductRestockStatus
	 * @Date : 2019. 7. 26.
	 * @Author : bje0507
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/product-restock-main")
	public ModelAndView readProductRestockStatus(Parameter<?> parameter) throws Exception {

		parameter.addAttribute("isAdmin",
				UtilsText.equals(LoginManager.getUserDetails().getUpAuthNo(), Const.ROLE_ADMIN_GROUP));
		parameter.addAttribute("searchConditionSiteList", this.siteService.getSiteList()); // 사이트 정보
		parameter.addAttribute("searchConditionSiteChannelList", this.siteService.getUseChannelList()); // 채널 정보

		return forward("/stats/restock/product-restock-main");
	}

	/**
	 * @Desc : 재입고 알림 서비스 기간별 통계 화면
	 * @Method Name : readDateRestockStatus
	 * @Date : 2019. 7. 26.
	 * @Author : bje0507
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/date-restock-main")
	public ModelAndView readDateRestockStatus(Parameter<?> parameter) throws Exception {

		parameter.addAttribute("isAdmin",
				UtilsText.equals(LoginManager.getUserDetails().getUpAuthNo(), Const.ROLE_ADMIN_GROUP));
		parameter.addAttribute("searchConditionSiteList", this.siteService.getSiteList()); // 사이트 정보
		parameter.addAttribute("searchConditionSiteChannelList", this.siteService.getUseChannelList()); // 채널 정보

		return forward("/stats/restock/date-restock-main");
	}

	/**
	 * @Desc : 재입고 알림 서비스 상품별 통계 조회
	 * @Method Name : statsSearch
	 * @Date : 2019. 7. 15.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/stats-product-restock-search")
	public void statsSearch(Parameter<RestockStatsSearchVO> parameter) throws Exception {

		Pageable<RestockStatsSearchVO, SaRestockStatus> pageable = new Pageable<>(parameter);
		Page<SaRestockStatus> page = restockStatsService.getProductRestockList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 재입고 알림 서비스 기간별 통계 조회
	 * @Method Name : statsDateSearch
	 * @Date : 2019. 7. 15.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/stats-date-restock-search")
	public void statsDateSearch(Parameter<RestockStatsSearchVO> parameter) throws Exception {

		Pageable<RestockStatsSearchVO, SaRestockStatus> pageable = new Pageable<>(parameter);
		Page<SaRestockStatus> page = restockStatsService.getDateRestockList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 재입고 알림 서비스 상품별 통계 조회 엑셀다운로드
	 * @Method Name : restockPdExcelDown
	 * @Date : 2019. 7. 30.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-restockPdExcel")
	public void restockPdExcelDown(Parameter<RestockStatsSearchVO> parameter) throws Exception {
		Pageable<RestockStatsSearchVO, SaRestockStatus> pageable = new Pageable<RestockStatsSearchVO, SaRestockStatus>(
				parameter);
		RestockStatsSearchVO bean = pageable.getBean();
		bean.setIsExcel("Y");
		pageable.setBean(bean);
		Page<SaRestockStatus> page = restockStatsService.getProductRestockList(pageable);

		ExcelValue excelValue = ExcelValue.builder(2, 0)
				.columnNames(Arrays.asList("selectSeq", "brandName", "productName", "productNo", "sizeCode",
						"optionName", "readyRestock", "successRestock", "cancelRestock", "endDtmRestock", "buyCount",
						"sellPrice"))
				.data(page.getContent()).build();

		parameter.downloadExcelTemplate("stats/restock/excel/restockProductStats", excelValue);
	}

	/**
	 * @Desc : 재입고 알림 서비스 기간별 통계 조회 엑셀다운로드
	 * @Method Name : restockDateExcelDown
	 * @Date : 2019. 7. 30.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-restockDateExcel")
	public void restockDateExcelDown(Parameter<RestockStatsSearchVO> parameter) throws Exception {
		Pageable<RestockStatsSearchVO, SaRestockStatus> pageable = new Pageable<RestockStatsSearchVO, SaRestockStatus>(
				parameter);
		RestockStatsSearchVO bean = pageable.getBean();
		bean.setIsExcel("Y");
		pageable.setBean(bean);
		Page<SaRestockStatus> page = restockStatsService.getDateRestockList(pageable);

		ExcelValue excelValue = ExcelValue.builder(2, 0).columnNames(Arrays.asList("alertSendDtm", "readyRestock",
				"successRestock", "cancelRestock", "endDtmRestock", "buyCount", "sellPrice")).data(page.getContent())
				.build();

		parameter.downloadExcelTemplate("stats/restock/excel/restockDateStats", excelValue);
	}

}
