package kr.co.abcmart.bo.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.board.model.master.BdNotice;
import kr.co.abcmart.bo.board.service.BdNoticeService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.CommonCode;

/**
 * @Desc : 공지사항 관리
 * @FileName : BdNoticeController.java
 * @Project : abc.bo
 * @Date : 2019. 2. 26.
 * @Author : 신인철
 */
@Controller
@RequestMapping("board/notice")
public class BdNoticeController extends BaseController {

	@Autowired
	BdNoticeService bdNoticeService;

	@RequestMapping("")
	public ModelAndView noticeMainForm(Parameter<?> parameter) throws Exception {
		String codeField = CommonCode.NOTC_TYPE_CODE;
		Map<String, Object> rsMap = bdNoticeService.getNoticeSearchForm(codeField);

		parameter.addAttribute("siteList", rsMap.get("siteList"));
		parameter.addAttribute("notcTypeCodeList", rsMap.get("notcTypeCodeList"));

		return forward("/board/notice/notice-main");
	}

	/**
	 * @Desc : 공지사항 등록페이지
	 * @Method Name : createNoticeForm
	 * @Date : 2019. 3. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/create-notice-form")
	public ModelAndView createNoticeForm(Parameter<?> parameter) throws Exception {
		String codeField = CommonCode.NOTC_TYPE_CODE;
		Map<String, Object> rsMap = bdNoticeService.getNoticeSearchForm(codeField);

		parameter.addAttribute("siteList", rsMap.get("siteList"));
		parameter.addAttribute("notcTypeCodeList", rsMap.get("notcTypeCodeList"));

		return forward("/board/notice/notice-detail-form");
	}

	/**
	 * @Desc : 공지사항 등록
	 * @Method Name : createNotice
	 * @Date : 2019. 3. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/create-notice")
	public void createNotice(Parameter<BdNotice> parameter) throws Exception {
		BdNotice bdNoticeParam = parameter.get();
		bdNoticeParam.setNotcContText(parameter.getString("notcContText", false));
		Map<String, Object> rsMap = bdNoticeService.setNotice(bdNoticeParam);

		writeJson(parameter, rsMap);
	}

	/**
	 * @Desc : 공지사항 그리드 호출
	 * @Method Name : readNoticeGrid
	 * @Date : 2019. 3. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-notice-list")
	public void readNoticeGrid(Parameter<BdNotice> parameter) throws Exception {
		Pageable<BdNotice, BdNotice> bdNoticeVO = new Pageable<>(parameter);
		Page<BdNotice> page = bdNoticeService.getNoticeGridList(bdNoticeVO);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 공지사항 상세보기
	 * @Method Name : readDetailForm
	 * @Date : 2019. 3. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-detail-form")
	public ModelAndView readDetailForm(Parameter<BdNotice> parameter) throws Exception {
		BdNotice param = parameter.get();
		String codeField = CommonCode.NOTC_TYPE_CODE;
		Map<String, Object> rsMap = bdNoticeService.getNoticeDetailForm(codeField, param);

		parameter.addAttribute("bdNotice", rsMap.get("bdNotice"));
		parameter.addAttribute("siteList", rsMap.get("siteList"));
		parameter.addAttribute("notcTypeCodeList", rsMap.get("notcTypeCodeList"));
		parameter.addAttribute("atchFiles", rsMap.get("atchFiles"));

		return forward("/board/notice/notice-detail-form");
	}

	/**
	 * @Desc : 공지사항 상세 수정
	 * @Method Name : updateNoticeDetail
	 * @Date : 2019. 3. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update-notice-detail")
	public void updateNoticeDetail(Parameter<BdNotice> parameter) throws Exception {
		BdNotice bdNoticeParam = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			bdNoticeParam.setNotcContText(parameter.getString("notcContText", false));
			bdNoticeService.updateNoticeDetail(bdNoticeParam);

			resultMap.put("result", 1);
		} catch (Exception e) {
			resultMap.put("result", 0);
			resultMap.put("Message", e.toString());
		}
		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 상단공지 공통일경우 개수 조회
	 * @Method Name : readTopNotcCount
	 * @Date : 2019. 3. 11.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-topnotice-total-count")
	public void readTopNotcTotalCount(Parameter<?> parameter) throws Exception {
		Map<String, Object> rsMap = bdNoticeService.getTopNotcTotalCount();

		writeJson(parameter, rsMap);
	}

	/**
	 * @Desc : 상단공지 사이트별 개수 조회
	 * @Method Name : readTopNotcSiteCount
	 * @Date : 2019. 11. 18.
	 * @Author : sic
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-topnotice-site-count")
	public void readTopNotcSiteCount(Parameter<BdNotice> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int count = bdNoticeService.getTopNotcSiteCount(parameter.get());

			resultMap.put("result", 1);
			resultMap.put("count", count);
		} catch (Exception e) {
			resultMap.put("result", 0);
			resultMap.put("Message", e.toString());
		}
		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 공지사항 삭제
	 * @Method Name : removeNoticeDetail
	 * @Date : 2019. 3. 11.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/remove-notice-detail")
	public void removeNoticeDetail(Parameter<BdNotice> parameter) throws Exception {
		BdNotice param = parameter.get();
		Map<String, Object> rsMap = new HashMap<>();
		try {
			bdNoticeService.deleteNoticeDetail(param);
			rsMap.put("result", 1);
		} catch (Exception e) {
			rsMap.put("result", 0);
			rsMap.put("Message", e.toString());
		}
		writeJson(parameter, rsMap);
	}

}