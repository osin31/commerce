package kr.co.abcmart.bo.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.board.model.master.BdBulkBuyInquiry;
import kr.co.abcmart.bo.board.model.master.BdBulkBuyInquiryMemo;
import kr.co.abcmart.bo.board.service.BdBulkBuyInquiryService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 단체주문 문의 관리
 * @FileName : InquiryController.java
 * @Project : abc.bo
 * @Date : 2019. 3. 13.
 * @Author : 신인철
 */
@Slf4j
@Controller
@RequestMapping("board/bulkbuy-inquiry")
public class BdBulkBuyInquiryController extends BaseController {

	@Autowired
	BdBulkBuyInquiryService bdBulkBuyInquiryService;

	/**
	 * @Desc : 단체주문 문의 조회 form을 조회한다.
	 * @Method Name : inquirySearchForm
	 * @Date : 2019. 3. 13.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("")
	public ModelAndView inquirySearchForm(Parameter<?> parameter) throws Exception {
		if (!UtilsText.isBlank(parameter.getString("fromDash"))
				&& UtilsText.equals(Const.BOOLEAN_TRUE, parameter.getString("fromDash"))) {
			parameter.addAttribute("fromDash", parameter.getString("fromDash"));
			parameter.addAttribute("tabIdx", parameter.getString("tabIdx"));
		}

		parameter.addAttribute("siteNoList", bdBulkBuyInquiryService.getBulkBuyMainSiteNo());

		return forward("/board/bulkbuy-inquiry/bulkbuy-inquiry-main");
	}

	/**
	 * @Desc : 검색 조건에 맞는 단체주문문의 정보를 조회한다.
	 * @Method Name : readInquieryList
	 * @Date : 2019. 3. 13
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-list")
	public void readInquieryList(Parameter<BdBulkBuyInquiry> parameter) throws Exception {
		Pageable<BdBulkBuyInquiry, BdBulkBuyInquiry> bulkbuyVO = new Pageable<>(parameter);
		try {
			Page<BdBulkBuyInquiry> page = bdBulkBuyInquiryService.getBulkBuyReadList(bulkbuyVO);

			writeJson(parameter, page.getGrid());
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.toString());
		}

	}

	/**
	 * @Desc : 단체주문 문의 상세 정보
	 * @Method Name : readInquiryDetail
	 * @Date : 2019. 3. 13.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-detail-pop")
	public ModelAndView readInquiryDetail(Parameter<BdBulkBuyInquiry> parameter) throws Exception {
		BdBulkBuyInquiry bulkBuyParam = parameter.get();

		Map<String, Object> result = bdBulkBuyInquiryService.getBulkBuyDetail(bulkBuyParam);

		parameter.addAttribute("bdBulkBuyDetail", result.get("bdBulkBuyDetail"));
		parameter.addAttribute("memberInfo", result.get("memberInfo"));
		parameter.addAttribute("bdBulkBuyMemo", result.get("bdBulkBuyMemo"));

		return forward("/board/bulkbuy-inquiry/bulkbuy-inquiry-detail-pop");
	}

	/**
	 * @Desc : 관리자메모 작성
	 * @Method Name : createBulkBuyMemo
	 * @Date : 2019. 3. 13.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/create-bulkbuy-memo")
	public void createBulkBuyMemo(Parameter<BdBulkBuyInquiryMemo> parameter) throws Exception {
		BdBulkBuyInquiryMemo bdBulkbuyParam = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			BdBulkBuyInquiryMemo resultVO = bdBulkBuyInquiryService.setBulkBuyInqryMemo(bdBulkbuyParam);

			resultMap.put("code", Const.BOOLEAN_TRUE);
			resultMap.put("blkBuyInqrySeq", resultVO.getBlkBuyInqrySeq());
			resultMap.put("blkBuyMemoSeq", resultVO.getBlkBuyMemoSeq());
			resultMap.put("loginId", resultVO.getLoginId());
			resultMap.put("adminName", resultVO.getAdminName());
			resultMap.put("rgsterNo", resultVO.getRgsterNo());
			resultMap.put("rgstDtm", resultVO.getRgstDtm());
			resultMap.put("memoText", resultVO.getMemoText());
		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}
		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 관리자 메모 삭제
	 * @Method Name : removeAdminMemo
	 * @Date : 2019. 3. 13.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/remove-bulkbuy-memo")
	public void removeAdminMemo(Parameter<BdBulkBuyInquiryMemo> parameter) throws Exception {
		BdBulkBuyInquiryMemo bdBulkBuyInquiryMemo = parameter.get();

		bdBulkBuyInquiryService.deleteBulkBuyMemo(bdBulkBuyInquiryMemo);
	}

}