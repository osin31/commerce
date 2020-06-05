package kr.co.abcmart.bo.main.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.board.model.master.BdAdminNotice;
import kr.co.abcmart.bo.board.model.master.BdCorprBoard;
import kr.co.abcmart.bo.board.service.AdminNoticeService;
import kr.co.abcmart.bo.delivery.model.master.OcDelivery;
import kr.co.abcmart.bo.delivery.service.DeliveryService;
import kr.co.abcmart.bo.main.service.PoDashboardService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;

@Controller
@RequestMapping("/po")
public class PoDashBoardController extends BaseController {
	@Autowired
	private PoDashboardService poDashboardService;
	@Autowired
	private AdminNoticeService adminNoticeService;
	@Autowired
	private DeliveryService deliveryService;

	/**
	 * @Desc : PO 대시보드 메인화면
	 * @Method Name : poDashboardMain
	 * @Date : 2019.4.25.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/dashboard")
	public ModelAndView poDashboardMain(Parameter<?> parameter) throws Exception {
		UserDetails userDetails = LoginManager.getUserDetails();
		if (!userDetails.isLogin()) {
			return redirect("system/login");
		} else if (UtilsText.equals(userDetails.getAuthApplySystemType(), Const.AUTH_APPLY_SYSTEM_TYPE_BO)) {
			return redirect("bo/dashboard");
		}

		Map<String, Object> resultMap = poDashboardService.poDashboardData(parameter);

		parameter.addAttribute("vndrNo", resultMap.get("vndrNo"));
		parameter.addAttribute("vndrName", resultMap.get("vndrName"));
		parameter.addAttribute("nowDtm", resultMap.get("nowDtm"));
		parameter.addAttribute("oneMonthAgoDtm", resultMap.get("oneMonthAgoDtm"));
		parameter.addAttribute("oneDayAgoDtm", resultMap.get("oneDayAgoDtm"));
		parameter.addAttribute("coworkCount", resultMap.get("coworkCount"));
		parameter.addAttribute("orderDlvyCountInfo", resultMap.get("orderDlvyCountInfo"));
		parameter.addAttribute("inquiryCount", resultMap.get("inquiryCount"));
		parameter.addAttribute("vndrProductInquiryCount", resultMap.get("vndrProductInquiryCount"));
		parameter.addAttribute("useplanning", resultMap.get("useplanning"));
		parameter.addAttribute("usereadyplanning", resultMap.get("usereadyplanning"));
		parameter.addAttribute("notuseplanning", resultMap.get("notuseplanning"));
		parameter.addAttribute("productsell", resultMap.get("productsell"));
		parameter.addAttribute("productsold", resultMap.get("productsold"));
		parameter.addAttribute("productAprv", resultMap.get("productAprv"));
		parameter.addAttribute("productDenyAprv", resultMap.get("productDenyAprv"));
		parameter.addAttribute("adNoticeList", resultMap.get("adNoticeList"));
		parameter.addAttribute("adminNoticeMainPop", resultMap.get("adminNoticeMainPop"));
		parameter.addAttribute("orderPrdtStatCodeClaim", resultMap.get("orderPrdtStatCodeClaim"));
		parameter.addAttribute("orderPrdtStatCodeDeliveryReturn", resultMap.get("orderPrdtStatCodeDeliveryReturn"));
		parameter.addAttribute("orderPrdtStatCodeExchangeDelivery", resultMap.get("orderPrdtStatCodeExchangeDelivery"));
		parameter.addAttribute("orderPrdtStatCodeClaimComplete", resultMap.get("orderPrdtStatCodeClaimComplete"));
		parameter.addAttribute("poOrderCntAmt", resultMap.get("poOrderCntAmt"));

		return forward("/main/po-dashboard");

	}

	/**
	 * @Desc : PO 대시보드 관리자 공지 목록 팝업 화면 호출
	 * @Method Name : getPoAdminNoticeListPop
	 * @Date : 2019. 5. 2.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/dashboard/read-list-pop")
	public ModelAndView getPoAdminNoticeListPop(Parameter<?> parameter) {
		parameter.addAttribute("fromDash", "PO");

		return forward("/board/admin-notice/admin-notice-list-pop");
	}

	/**
	 * @Desc : PO 대시보드 관리자 공지 상세 팝업 호출
	 * @Method Name : getPoAdminNoticeDetailPop
	 * @Date : 2019. 5. 2.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/dashboard/read-detail-pop")
	public ModelAndView getPoAdminNoticeDetailPop(Parameter<?> parameter) throws Exception {
		BdAdminNotice params = parameter.create(BdAdminNotice.class);
		Map<String, Object> result = adminNoticeService.getAdminNoticeDetail(params);

		// 공지 사항 상세 정보
		parameter.addAttribute("bdAdminNotice", result.get("bdAdminNotice"));
		// 공지 사항 첨부 파일
		parameter.addAttribute("fileDetailList", result.get("bdAdminNoticeAttachFileList"));

		return forward("/board/admin-notice/admin-notice-detail-pop");
	}

	/**
	 * @Desc : 관리자 공지 목록 호출
	 * @Method Name : getAdminNoticeList
	 * @Date : 2019. 7. 22.
	 * @Author : 고웅환
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/dashboard/read-list")
	public void getAdminNoticeList(Parameter<BdAdminNotice> parameter) throws Exception {
		Pageable<BdAdminNotice, BdAdminNotice> adminNtcVOPageble = new Pageable<>(parameter);
		UserDetails userDetails = LoginManager.getUserDetails();
		Page<BdAdminNotice> page = null;

		adminNtcVOPageble.getBean().setVndrNoForDashboard(userDetails.getVndrNo());
		page = adminNoticeService.selectAdminNoticeForPoDashboard(adminNtcVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : PO 대시보드 협력게시판 새로고침
	 * @Method Name : refreshCoworkBoardCount
	 * @Date : 2019.4.25.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/dashboard/refreshCoworkBoard")
	public void refreshCoworkBoardCount(Parameter<BdCorprBoard> parameter) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("dashboardCount", poDashboardService.getPoDashboardCoworkCount(parameter));

		writeJson(parameter, resultMap);

	}

	/**
	 * @Desc : PO 대시보드 1:1문의 게시판 새로고침
	 * @Method Name : refreshInquiryBoardCount
	 * @Date : 2019.4.25.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/dashboard/refreshInquiryBoard")
	public void refreshInquiryBoardCount(Parameter<?> parameter) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("dashboardCount", poDashboardService.getPoDashboardInquiryCount());

		writeJson(parameter, resultMap);

	}

	/**
	 * @Desc : PO 대시보드 공지사항 게시판 새로고침
	 * @Method Name : refreshAdNoticeBoard
	 * @Date : 2019.4.25.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/dashboard/refreshAdNoticeBoard")
	public void refreshAdNoticeBoard(Parameter<?> parameter) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("dashboardList", poDashboardService.getPoDashboardAdNoticeBoardTopFive());

		writeJson(parameter, resultMap);

	}

	/**
	 * @Desc : 상품 후기 게시판 새로고침
	 * @Method Name : refreshProductQnA
	 * @Date : 2019.6.7.
	 * @Author : 김영진
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/dashboard/refreshProductQnA")
	public void refreshProductQnA(Parameter<?> parameter) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("dashboardCount", poDashboardService.getPoDashboardProductQna());

		writeJson(parameter, resultMap);

	}

	/**
	 * @Desc : PO 대시보드 기획전현황 새로고침
	 * @Method Name : refreshplanningDisplay
	 * @Date : 2019.6.7.
	 * @Author : 김영진
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/dashboard/refreshplanningDisplay")
	public void refreshplanningDisplay(Parameter<?> parameter) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("dashboardCount", poDashboardService.getplanningDisplay());

		writeJson(parameter, resultMap);

	}

	/**
	 * @Desc : PO 대시보드 상품현황 새로고침
	 * @Method Name : refreshProduct
	 * @Date : 2019.6.7.
	 * @Author : 김영진
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/dashboard/refreshProduct")
	public void refreshProduct(Parameter<?> parameter) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("dashboardCount", poDashboardService.getProductDashboardCount());

		writeJson(parameter, resultMap);

	}

	/**
	 * @Desc : PO 대시보드 주문/배송 현황 새로고침
	 * @Method Name : refreshOrder
	 * @Date : 2019. 10. 16.
	 * @Author : 최경호
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/dashboard/refreshOrderBoard")
	public void refreshOrder(Parameter<?> parameter) throws Exception {
		writeJson(parameter, poDashboardService.getDashboardOrderDlvyCount());
	}

	/**
	 * @Desc : PO 대시보드 클레임 현황 새로고침
	 * @Method Name : refreshClaim
	 * @Date : 2019. 10. 11.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/dashboard/refreshClaimBoard")
	public void refreshClaim(Parameter<?> parameter) throws Exception {
		OcDelivery ocDelivery = new OcDelivery();
		ocDelivery.setVndrNo(LoginManager.getUserDetails().getVndrNo());
		ocDelivery.setFromDashYn(Const.BOOLEAN_TRUE);
		Map<String, Object> claimMap = deliveryService.getDeliveryOrderVendorClaim(ocDelivery);

		writeJson(parameter, claimMap);
	}

}
