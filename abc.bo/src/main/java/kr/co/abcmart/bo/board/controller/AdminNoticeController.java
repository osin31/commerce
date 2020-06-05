package kr.co.abcmart.bo.board.controller;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.board.model.master.BdAdminNotice;
import kr.co.abcmart.bo.board.service.AdminNoticeService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 관리자 공지 컨트롤러
 * @FileName : AdminNoticeController.java
 * @Project : abc.bo
 * @Date : 2019. 2. 13.
 * @Author : 3top
 */
@Slf4j
@Controller
@RequestMapping("board/admin-notice")
public class AdminNoticeController extends BaseController {

	@Autowired
	private AdminNoticeService adminNoticeService;

	/**
	 * @Desc : 관리자 공지 목록 화면 호출
	 * @Method Name : read
	 * @Date : 2019. 1. 31.
	 * @Author : 3top [lks@3top.co.kr]
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("")
	public ModelAndView adminNoticemain(Parameter<?> parameter) {
		return forward("/board/admin-notice/admin-notice-main");
	}

	/**
	 * @Desc : 관리자 공지 목록 호출
	 * @Method Name : getAdminNoticeList
	 * @Date : 2019. 1. 31.
	 * @Author : 3top [이강수]
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-list")
	public void getAdminNoticeList(Parameter<BdAdminNotice> parameter) throws Exception {
		Pageable<BdAdminNotice, BdAdminNotice> adminNtcVOPageble = new Pageable<>(parameter);
		Page<BdAdminNotice> page = null;

		page = adminNoticeService.getAdminNoticeList(adminNtcVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 관리자 공지 상세 페이지
	 * @Method Name : getAdminNoticeDetail
	 * @Date : 2019. 1. 31.
	 * @Author : 3top [이강수]
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-detail")
	public ModelAndView getAdminNoticeDetail(Parameter<?> parameter) throws Exception {
		BdAdminNotice params = parameter.create(BdAdminNotice.class);
		Map<String, Object> result = adminNoticeService.getAdminNoticeDetail(params);

		// 공지 사항 상세 정보
		parameter.addAttribute("bdAdminNotice", result.get("bdAdminNotice"));
		// 공지 대상 업체 정보
		parameter.addAttribute("bdAdminNoticeTargetVendorList", result.get("bdAdminNoticeTargetVendorList"));

		return forward("/board/admin-notice/admin-notice-detail");
	}

	/**
	 * @Desc : 관리자 공지 상세에서 미리보기 화면 호출
	 * @Method Name : getAdminNoticeDetailPrev
	 * @Date : 2019. 1. 31.
	 * @Author : 3top [이강수]
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-detail-prev-pop")
	public ModelAndView getAdminNoticeDetailPrev(Parameter<BdAdminNotice> parameter) throws Exception {
		BdAdminNotice params = parameter.get();

		params.setPreViewYN(Const.BOOLEAN_TRUE);

		// 공지 사항 상세 정보
		parameter.addAttribute("bdAdminNotice", params);

		return forward("/board/admin-notice/admin-notice-detail-prev-pop");
	}

	/**
	 * @Desc : 관리자 공지 목록 팝업 화면 호출
	 * @Method Name : getAdminNoticeListPop
	 * @Date : 2019. 1. 31.
	 * @Author : 3top [이강수]
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-list-pop")
	public ModelAndView getAdminNoticeListPop(Parameter<?> parameter) {

		return forward("/board/admin-notice/admin-notice-list-pop");
	}

	/**
	 * @Desc : 관리자 공지 상세 팝업 호출
	 * @Method Name : getAdminNoticeDetailPop
	 * @Date : 2019. 1. 31.
	 * @Author : 3top
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-detail-pop")
	public ModelAndView getAdminNoticeDetailPop(Parameter<?> parameter) throws Exception {
		BdAdminNotice params = parameter.create(BdAdminNotice.class);
		Map<String, Object> result = adminNoticeService.getAdminNoticeDetail(params);

		// 메인팝업 호출여부
		parameter.addAttribute("isMainPopup", params.isMainPopup());
		// 공지 사항 상세 정보
		parameter.addAttribute("bdAdminNotice", result.get("bdAdminNotice"));

		return forward("/board/admin-notice/admin-notice-detail-pop");
	}

	/**
	 * @Desc : 관리자 공지 등록 화면 호출
	 * @Method Name : setAdminNoticeForm
	 * @Date : 2019. 1. 31.
	 * @Author : 3top [이강수]
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/create-form")
	public ModelAndView createAdminNoticeForm(Parameter<?> parameter) {

		return forward("/board/admin-notice/create-admin-notice-form");
	}

	/**
	 * @Desc : 관리자 공지 등록 미리보기 화면 호출
	 * @Method Name : setAdminNoticePrevForm
	 * @Date : 2019. 1. 31.
	 * @Author : 3top [이강수]
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/create-prev-pop")
	public ModelAndView setAdminNoticePrevForm(Parameter<BdAdminNotice> parameter) throws Exception {

		BdAdminNotice params = parameter.get();

		SimpleDateFormat format1 = new SimpleDateFormat(Const.DEFAULT_DATETIME_PATTERN);
		params.setNowTime(format1.format(System.currentTimeMillis()));
		params.setAdminName(UtilsText.concat(LoginManager.getUserDetails().getLoginId(), Const.L_PAREN,
				LoginManager.getUserDetails().getUsername(), Const.R_PAREN));
		params.setHitCount(0);

		parameter.addAttribute("bdAdminNotice", params);

		return forward("/board/admin-notice/create-admin-notice-prev-pop");
	}

	/**
	 * @Desc :
	 * @Method Name : setAdminNotice
	 * @Date : 2019. 1. 31.
	 * @Author : 3top [이강수]
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/create")
	public void setAdminNotice(Parameter<BdAdminNotice> parameter) throws Exception {
		BdAdminNotice params = parameter.get();

		// ckeditor 관련 : notcContText을 필터링 없이 태그 포함하여디비로 넣는 구문
		params.setNotcContText(parameter.getString("notcContText", false));

		Map<String, Object> resultMap = adminNoticeService.setAdminNotice(params);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 관리자 공지를 업데이트
	 * @Method Name : updateAdminNotice
	 * @Date : 2019. 1. 31.
	 * @Author : 3top [이강수]
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/update")
	public void updateAdminNotice(Parameter<BdAdminNotice> parameter) throws Exception {

		BdAdminNotice params = parameter.get();

		// ckeditor 관련 : notcContText을 필터링 없이 태그 포함하여디비로 넣는 구문
		params.setNotcContText(parameter.getString("notcContText", false));

		adminNoticeService.updateAdminNotice(params);
	}

	/**
	 * @Desc : 관리자 공지를 삭제
	 * @Method Name : remove
	 * @Date : 2019. 1. 31.
	 * @Author : 3top [이강수]
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/delete")
	public void deleteAdminNotice(Parameter<?> parameter) throws Exception {
		BdAdminNotice[] entity = parameter.createArray(BdAdminNotice.class, "adminNotcSeq");

		adminNoticeService.deleteAdminNotice(entity);
	}

}