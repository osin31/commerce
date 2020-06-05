package kr.co.abcmart.bo.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.board.model.master.BdFaq;
import kr.co.abcmart.bo.board.service.BdFaqService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.CommonCode;

/**
 * @Desc : FAQ 관리
 * @FileName : InquiryController.java
 * @Project : abc.bo
 * @Date : 2019. 2. 28.
 * @Author : 신인철
 */
@Controller
@RequestMapping("board/faq")
public class BdFaqController extends BaseController {

	@Autowired
	BdFaqService bdFaqService;

	/**
	 * @Desc : FAQ 문의 조회 form을 조회한다.
	 * @Method Name : inquirySearchForm
	 * @Date : 2019. 3. 4.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("")
	public ModelAndView faqSearchForm(Parameter<?> parameter) throws Exception {
		List<SyCodeDetail> cnslTypeList = bdFaqService.getFaqForm();

		parameter.addAttribute("cnslTypeCode", cnslTypeList); // 문의 유형 코드

		return forward("/board/faq/faq-main");
	}

	/**
	 * @Desc : FAQ문의 그리드 조회
	 * @Method Name : readFaqGrid
	 * @Date : 2019. 3. 5.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("read-faq-list")
	public void readFaqGrid(Parameter<BdFaq> parameter) throws Exception {
		Pageable<BdFaq, BdFaq> bdFaqVO = new Pageable<>(parameter);
		Page<BdFaq> page = bdFaqService.getFaqGridList(bdFaqVO);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : FAQ 상세보기
	 * @Method Name : readDetailForm
	 * @Date : 2019. 3. 5.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("read-detail-form")
	public ModelAndView readDetailForm(Parameter<BdFaq> parameter) throws Exception {
		BdFaq bdFaqParam = parameter.get();

		String[] codeField = { CommonCode.CNSL_TYPE_CODE, CommonCode.CNSL_TYPE_DTL_CODE }; // 조회 코드 그룹
		Map<String, Object> rtnVal = bdFaqService.getFaqDetailForm(bdFaqParam, codeField);

		parameter.addAttribute("bdFaq", rtnVal.get("bdFaq"));
		parameter.addAttribute("cnslTypeCode", rtnVal.get("cnslTypeCode"));
		parameter.addAttribute("cnslTypeDtlCode", rtnVal.get("cnslTypeDtlCode"));

		return forward("/board/faq/faq-detail-form");
	}

	/**
	 * @Desc : FAQ 수정
	 * @Method Name : updateFaqDetail
	 * @Date : 2019. 3. 5.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("update-faq-detail")
	public void updateFaqDetail(Parameter<BdFaq> parameter) throws Exception {
		BdFaq bdFaqParam = parameter.get();
		bdFaqParam.setFaqContText(parameter.getString("faqContText", false));
		Map<String, Object> rsMap = bdFaqService.updateFaqDetail(bdFaqParam);

		writeJson(parameter, rsMap);
	}

	/**
	 * @Desc : FAQ 등록 페이지
	 * @Method Name : createFaqForm
	 * @Date : 2019. 3. 5.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("create-faq-form")
	public ModelAndView createFaqForm(Parameter<?> parameter) throws Exception {
		List<SyCodeDetail> cnslTypeList = bdFaqService.getFaqForm();

		parameter.addAttribute("cnslTypeCode", cnslTypeList);

		return forward("/board/faq/faq-detail-form");
	}

	/**
	 * @Desc : FAQ 등록
	 * @Method Name : createFaq
	 * @Date : 2019. 3. 5.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("create-faq")
	public void createFaq(Parameter<BdFaq> parameter) throws Exception {
		BdFaq bdFaq = parameter.get();
		bdFaq.setFaqContText(parameter.getString("faqContText", false));
		Map<String, Object> rsMap = bdFaqService.setFaqDetail(bdFaq);

		writeJson(parameter, rsMap);
	}

	/**
	 * @Desc : FAQ 삭제
	 * @Method Name : removeFaq
	 * @Date : 2019. 3. 5.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("remove-faq")
	public void removeFaq(Parameter<BdFaq> parameter) throws Exception {
		BdFaq bdFaq = parameter.get();

		Map<String, Object> rsMap = bdFaqService.deleteFaqDetail(bdFaq);

		writeJson(parameter, rsMap);
	}

	/**
	 * @Desc : top10 리스트 팝업 호출
	 * @Method Name : top10ListForm
	 * @Date : 2019. 3. 12.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("top10-list-form")
	public ModelAndView top10ListForm(Parameter<BdFaq> parameter) throws Exception {

		return forward("board/faq/faq-top10-list-form");
	}

	/**
	 * @Desc : TOP10 설정한 개수
	 * @Method Name : readTop10SaveCount
	 * @Date : 2019. 4. 16.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("read-top10-save-count")
	public void readTop10SaveCount(Parameter<BdFaq> parameter) throws Exception {
		Map<String, Object> rsMap = bdFaqService.getTop10Count();

		writeJson(parameter, rsMap);
	}

	/**
	 * @Desc : top10 그리드 검색
	 * @Method Name : readTop10List
	 * @Date : 2019. 3. 12.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("read-top10-list")
	public void readTop10List(Parameter<BdFaq> parameter) throws Exception {
		Pageable<BdFaq, BdFaq> pageable = new Pageable<>(parameter);
		Page<BdFaq> page = bdFaqService.getTop10GridList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : TOP10 정렬순서 저장
	 * @Method Name : updateSort
	 * @Date : 2019. 4. 12.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("update-sort")
	public void updateSort(Parameter<BdFaq[]> parameter) throws Exception {
		bdFaqService.updateTop10Sort(parameter.get());

		writeJson(parameter, true);
	}

	/**
	 * @Desc : TOP10 설정 취소
	 * @Method Name : updateTop10Cancel
	 * @Date : 2019. 8. 30.
	 * @Author : sic
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("update-top10-cancel")
	public void updateTop10Cancel(Parameter<BdFaq> parameter) throws Exception {
		bdFaqService.updateTop10Cancel(parameter.get());

		writeJson(parameter, true);
	}

}