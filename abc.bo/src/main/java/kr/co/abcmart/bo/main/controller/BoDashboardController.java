package kr.co.abcmart.bo.main.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.board.model.master.BdAdminNotice;
import kr.co.abcmart.bo.board.service.AdminNoticeService;
import kr.co.abcmart.bo.main.service.BoDashboardService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;

@Controller
@RequestMapping("bo")
public class BoDashboardController extends BaseController {

	@Autowired
	private BoDashboardService boDashboardService;

	@Autowired
	private AdminNoticeService adminNoticeService;

	/**
	 * @Desc : BO Dashboard를 조회한다.
	 * @Method Name : boDashboard
	 * @Date : 2019. 4. 24.
	 * @Author : 고웅환
	 * @param parameter
	 */
	@GetMapping("/dashboard")
	public ModelAndView boDashboard(Parameter<?> parameter) throws Exception {
		UserDetails userDetails = LoginManager.getUserDetails();
		if (!userDetails.isLogin()) {
			return redirect("system/login");
		} else if (UtilsText.equals(userDetails.getAuthApplySystemType(), Const.AUTH_APPLY_SYSTEM_TYPE_PO)) {
			return redirect("po/dashboard");
		}

		Map<String, Object> resultMap = boDashboardService.getDashboardData(parameter);

		parameter.addAttribute("nowDtm", resultMap.get("nowDtm"));
		parameter.addAttribute("oneMonthAgoDtm", resultMap.get("oneMonthAgoDtm"));
		parameter.addAttribute("oneDayAgoDtm", resultMap.get("oneDayAgoDtm"));
		parameter.addAttribute("unAswrList", resultMap.get("unAswrList"));
		parameter.addAttribute("etcList", resultMap.get("etcList"));
		parameter.addAttribute("adminNoticeList", resultMap.get("adminNoticeList"));
		parameter.addAttribute("adminNoticeMainPop", resultMap.get("adminNoticeMainPop"));
		parameter.addAttribute("productStateList", resultMap.get("productStateList"));
		parameter.addAttribute("claimCountInfo", resultMap.get("claimCountInfo"));
		parameter.addAttribute("useplanning", resultMap.get("useplanning"));
		parameter.addAttribute("usereadyplanning", resultMap.get("usereadyplanning"));
		parameter.addAttribute("notuseplanning", resultMap.get("notuseplanning"));
		parameter.addAttribute("memberStatus", resultMap.get("memberStatus"));
		parameter.addAttribute("localOrderCntAmt", resultMap.get("localOrderCntAmt"));
		parameter.addAttribute("orderDlvyCountInfo", resultMap.get("orderDlvyCountInfo"));
		parameter.addAttribute("vendorOrderCntAmt", resultMap.get("vendorOrderCntAmt"));
		parameter.addAttribute("bestVndrOrderCnt", resultMap.get("bestVndrOrderCnt"));
		parameter.addAttribute("bestVndrOrderAmt", resultMap.get("bestVndrOrderAmt"));
		parameter.addAttribute("unAswrVndrList", resultMap.get("unAswrVndrList"));
		parameter.addAttribute("delayedDeliveryVndrList", resultMap.get("delayedDeliveryVndrList"));

		return forward("/main/bo-dashboard");
	}

	/**
	 * @Desc : 새로고침
	 * @Method Name : getInquiry
	 * @Date : 2019. 4. 24.
	 * @Author : 고웅환
	 * @param parameter
	 */
	@GetMapping("/dashboard/refresh")
	public void getInquiry(Parameter<?> parameter) throws Exception {
		writeJson(parameter, boDashboardService.getDashboardRefresh(parameter));
	}

	/**
	 * @Desc : 관리자 공지 더보기 팝업
	 * @Method Name : getAdminNoticeListPop
	 * @Date : 2019. 7. 22.
	 * @Author : 고웅환
	 * @param parameter
	 */
	@GetMapping("/dashboard/read-list-pop")
	public ModelAndView getAdminNoticeListPop(Parameter<?> parameter) throws Exception {
		parameter.addAttribute("fromDash", "BO");

		return forward("/board/admin-notice/admin-notice-list-pop");
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
		Page<BdAdminNotice> page = null;

		page = adminNoticeService.getAdminNoticeList(adminNtcVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 관리자 공지 상세 팝업 호출
	 * @Method Name : getAdminNoticeDetailPop
	 * @Date : 2019. 7. 23.
	 * @Author : 고웅환
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/dashboard/read-detail-pop")
	public ModelAndView getAdminNoticeDetailPop(Parameter<?> parameter) throws Exception {
		BdAdminNotice params = parameter.create(BdAdminNotice.class);
		Map<String, Object> result = adminNoticeService.getAdminNoticeDetail(params);

		// 메인팝업 호출여부
		parameter.addAttribute("isMainPopup", params.isMainPopup());
		// 공지 사항 상세 정보
		parameter.addAttribute("bdAdminNotice", result.get("bdAdminNotice"));
		// 공지 사항 첨부 파일
		parameter.addAttribute("fileDetailList", result.get("bdAdminNoticeAttachFileList"));

		return forward("/board/admin-notice/admin-notice-detail-pop");
	}

	/**
	 * @Desc : 클레임 현황 새로고침
	 * @Method Name : getClaimCountInfo
	 * @Date : 2019. 7. 25.
	 * @Author : 이강수
	 * @param Parameter<?> parameter
	 */
	@GetMapping("/dashboard/claim/refresh")
	public void getClaimCountInfo(Parameter<?> parameter) throws Exception {
		writeJson(parameter, boDashboardService.getClaimCountInfo());
	}
}
