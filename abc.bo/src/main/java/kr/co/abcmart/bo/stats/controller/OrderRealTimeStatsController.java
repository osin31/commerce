package kr.co.abcmart.bo.stats.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.stats.model.master.CurrentSaleStats;
import kr.co.abcmart.bo.stats.service.OrderRealTimeStatsService;
import kr.co.abcmart.bo.stats.service.SalesStatsService;
import kr.co.abcmart.bo.stats.vo.OrderStatsSearchVO;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/stats/order/realtime/")
public class OrderRealTimeStatsController extends BaseController {

	@Autowired
	private OrderRealTimeStatsService orderRealTimeService;
	@Autowired
	private VendorService vendorService;
	@Autowired
	private SalesStatsService salesStatsService;

	/**
	 * @Desc : 당일 매출 현황 Form 호출
	 * @Method Name : currentSalesStats
	 * @Date : 2019. 7. 1.
	 * @Author : kiowa
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("current-sales-stats")
	public ModelAndView currentSalesStats(Parameter<?> parameter) throws Exception {
		orderRealTimeService.getStatusSearchCondition(parameter);
		return forward("/stats/order/realtime/current-sales-stats");
	}

	/**
	 * @Desc : 당일 매출 현황 상세팝업 Form 호출
	 * @Method Name : currentSalesStatsDetail
	 * @Date : 2019. 7. 1.
	 * @Author : kiowa
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("current-sales-stats/detail-pop")
	public ModelAndView currentSalesStatsDetail(Parameter<?> parameter) throws Exception {
		orderRealTimeService.getStatusSearchCondition(parameter);
		return forward("/stats/order/realtime/current-sales-stats-detail");
	}

	/**
	 * @Desc : 당일 디바이스별 매출현황 form 호출
	 * @Method Name : currentDeviceStats
	 * @Date : 2019. 7. 19.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("current-device-stats")
	public ModelAndView currentDeviceStats(Parameter<?> parameter) throws Exception {
		orderRealTimeService.getStatusSearchCondition(parameter);
		return forward("/stats/order/realtime/current-device-stats");
	}

	/**
	 * @Desc : 당일 결제수단별 매출현황 form 호출
	 * @Method Name : currentPaymentStats
	 * @Date : 2019. 7. 19.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("current-payment-stats")
	public ModelAndView currentPaymentStats(Parameter<?> parameter) throws Exception {
		orderRealTimeService.getStatusSearchCondition(parameter);
		return forward("/stats/order/realtime/current-payment-stats");
	}

	/**
	 * @Desc : 당일 상품 판매 현황
	 * @Method Name : currentPrdtStats
	 * @Date : 2019. 7. 22.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("current-prdt-stats")
	public ModelAndView currentPrdtStats(Parameter<?> parameter) throws Exception {
		orderRealTimeService.getStatusSearchCondition(parameter);
		return forward("/stats/order/realtime/current-prdt-stats");
	}

	/**
	 * @Desc : 당일 교환/반품 처리 현황
	 * @Method Name : currentChangeBackStats
	 * @Date : 2019. 7. 22
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("current-claim-stats")
	public ModelAndView currentChangeBackStats(Parameter<?> parameter) throws Exception {
		orderRealTimeService.getStatusSearchCondition(parameter);
		return forward("/stats/order/realtime/current-claim-stats");
	}

	/**
	 * @Desc : 당일 매출 현황 목록 조회.
	 * @Method Name : readCurrentSalesStatsList
	 * @Date : 2019. 7. 18.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("current-sales-stats/read-list")
	public void readCurrentSalesStatsList(Parameter<OrderStatsSearchVO> parameter) throws Exception {
		Pageable<OrderStatsSearchVO, CurrentSaleStats> orderStatsSearchVO = new Pageable<>(parameter);

		Page<CurrentSaleStats> page = new Page<CurrentSaleStats>();
		String typeStats = parameter.getString("typeStats");
		String isTimeType = parameter.getString("isTimeType");
		// bo, po 권한 구분
		UserDetails user = LoginManager.getUserDetails();
		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;

		parameter.addAttribute("isAdmin", isAdmin);
		if (!isAdmin) {
			VdVendor vndr = new VdVendor();
			vndr.setVndrNo(user.getVndrNo());
			vndr = vendorService.getVendorBaseInfo(vndr);
			orderStatsSearchVO.getBean().setVndrNo(vndr.getVndrNo());

			if (UtilsText.equals(typeStats, "device")) {
				String[] stockGbnCode = { CommonCode.STOCK_GBN_CODE_VD };
				orderStatsSearchVO.getBean().setStockGbnCodeArr(stockGbnCode);
			}
		}

		if (UtilsText.equals(typeStats, "sale")) {
			page = orderRealTimeService.getCurrentSalesStatsList(orderStatsSearchVO);
		} else if (UtilsText.equals(typeStats, "saleDetail") && UtilsText.equals(isTimeType, null)) {
			page = orderRealTimeService.getCurrentSalesDetailList(orderStatsSearchVO);
		} else if (UtilsText.equals(typeStats, "device")) {
			page = orderRealTimeService.getCurrentDeviceStatsList(orderStatsSearchVO);
		} else if (UtilsText.equals(typeStats, "payment")) {
			page = orderRealTimeService.getCurrentPaymentStatsList(orderStatsSearchVO);
		} else if (UtilsText.equals(typeStats, "prdt")) {
			page = orderRealTimeService.getCurrentPrdtStatsList(orderStatsSearchVO);
		} else if (UtilsText.equals(typeStats, "claim")) {
			page = orderRealTimeService.getCurrentClaimStatsList(orderStatsSearchVO);
		}

		writeJson(parameter, page.getGrid());
	}

}