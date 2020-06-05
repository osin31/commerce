package kr.co.abcmart.bo.board.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.board.model.master.BdMemberCounsel;
import kr.co.abcmart.bo.board.model.master.BdMemberCounselMemo;
import kr.co.abcmart.bo.board.service.BdInquiryService;
import kr.co.abcmart.bo.board.vo.BdInquirySearchVO;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 고객의소리 문의 관리
 * @FileName : InquiryController.java
 * @Project : abc.bo
 * @Date : 2019. 2. 26.
 * @Author : 신인철
 */
@Slf4j
@Controller
@RequestMapping("board/voiceofcustomer")
public class BdVoiceOfCustomerController extends BaseController {

	@Autowired
	BdInquiryService bdInquiryService;

	/**
	 * @Desc : 고객의 소리 리스트 화면 호출
	 * @Method Name : vocSearchForm
	 * @Date : 2019. 11. 18.
	 * @Author : sic
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("")
	public ModelAndView vocSearchForm(Parameter<?> parameter) throws Exception {
		BdInquirySearchVO inquirySearchVO = parameter.create(BdInquirySearchVO.class);

		Map<String, Object> rtnVal = bdInquiryService.getVocSearchForm();

		if (!UtilsText.isBlank(parameter.getString("fromDash"))
				&& UtilsText.equals(Const.BOOLEAN_TRUE, parameter.getString("fromDash"))) {
			parameter.addAttribute("fromDash", parameter.getString("fromDash"));
			parameter.addAttribute("tabIdx", parameter.getString("tabIdx"));
		}

		parameter.addAttribute("cnslTypeCode", rtnVal.get("vocTypeList"));
		parameter.addAttribute("aswrStatCode", rtnVal.get("aswrStatCode"));
		parameter.addAttribute("siteNoList", rtnVal.get("siteNoList"));

		parameter.addAttribute("inquirySearchVO", inquirySearchVO); // 1:1 문의 검색 조건

		return forward("/board/voiceofcustomer/voiceofcustomer-main");
	}

	/**
	 * @Desc : 검색 조건에 맞는 고객의소리 정보를 조회한다.
	 * @Method Name : readInquieryList
	 * @Date : 2019. 2. 11.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-list")
	public void readInquieryList(Parameter<BdInquirySearchVO> parameter) throws Exception {
		Pageable<BdInquirySearchVO, BdMemberCounsel> inquirySearchVO = new Pageable<>(parameter);
		try {
			Page<BdMemberCounsel> page = bdInquiryService.getInquiryReadList(inquirySearchVO);

			writeJson(parameter, page.getGrid());
		} catch (Exception e) {
			log.error(e.toString());
		}

	}

	/**
	 * @Desc : 고객의소리 상세 정보
	 * @Method Name : readInquiryDetail
	 * @Date : 2019. 2. 12.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-detail-pop")
	public ModelAndView readInquiryDetail(Parameter<BdMemberCounsel> parameter) throws Exception {
		Map<String, Object> rtnVal = bdInquiryService.getVocDetail(parameter.get());

		BdMemberCounsel bdMemberCounsel = (BdMemberCounsel) rtnVal.get("bdMemberCounsel");

		parameter.addAttribute("bdMemberCounsel", bdMemberCounsel); // 상담정보 및 답변 정보

		parameter.addAttribute("cnslTypeCode", rtnVal.get("cnslTypeCode")); // 문의 유형 코드
		parameter.addAttribute("cnslTypeDtlCode", rtnVal.get("cnslTypeDtlCode")); // 문의 구분 코드

		parameter.addAttribute("aswrCnslTypeCode", rtnVal.get("aswrCnslTypeCode")); // 상담 유형 코드
		parameter.addAttribute("aswrCnslTypeDtlCode", rtnVal.get("aswrCnslTypeDtlCode")); // 상담 분류 코드

		parameter.addAttribute("bdMemberCounselMemo", rtnVal.get("bdMemberCounselMemo")); // 관리자 메모

		parameter.addAttribute("memberInfo", rtnVal.get("memberInfo")); // 회원 정보

		// 상담스크립트가 있을시 추가
		if (bdMemberCounsel.getCnslScriptSeq() != null) {
			parameter.addAttribute("counselScript", rtnVal.get("counselScript")); // 회원 정보
		}

		return forward("/board/voiceofcustomer/voiceofcustomer-detail-pop");
	}

	/**
	 * @Desc : 고객의소리 문의 답변 등록
	 * @Method Name : updateInquiry
	 * @Date : 2019. 2. 25
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/update")
	public void updateInquiry(Parameter<BdMemberCounsel> parameter) throws Exception {
		BdMemberCounsel bdMemberCounselParam = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			bdMemberCounselParam.validate();
			bdInquiryService.updateInquryNoFile(bdMemberCounselParam);

			resultMap.put("code", Const.BOOLEAN_TRUE);
			resultMap.put("memberCnslSeq", bdMemberCounselParam.getMemberCnslSeq());
		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}
		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 관리자메모 작성
	 * @Method Name : createAdminMemo
	 * @Date : 2019. 3. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/create-adminmemo")
	public void createAdminMemo(Parameter<BdMemberCounselMemo> parameter) throws Exception {
		BdMemberCounselMemo bdMemberCounselMemo = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			bdMemberCounselMemo.validate();
			BdMemberCounselMemo resultVO = bdInquiryService.setAdminMemo(bdMemberCounselMemo);

			resultMap.put("code", Const.BOOLEAN_TRUE);
			resultMap.put("memberCnslSeq", resultVO.getMemberCnslSeq());
			resultMap.put("cnslMemoSeq", resultVO.getCnslMemoSeq());
			resultMap.put("rgsterDpName", resultVO.getRgsterDpName());
			resultMap.put("rgstDtm", resultVO.getRgstDtm());
			resultMap.put("rgsterNo", resultVO.getRgsterNo());
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
	 * @Date : 2019. 2. 21.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/remove-adminmemo")
	public void removeAdminMemo(Parameter<BdMemberCounselMemo> parameter) throws Exception {
		Map<String, Object> rsMap = new HashMap<>();
		BdMemberCounselMemo bdMemberCounselMemo = parameter.get();
		try {
			bdInquiryService.deleteAdminMemo(bdMemberCounselMemo);

			rsMap.put("result", 1);
		} catch (Exception e) {
			rsMap.put("result", 0);
			rsMap.put("Message", e.toString());
		}
		writeJson(parameter, rsMap);
	}

	@PostMapping("save-voc-leave")
	public void saveVocLeave(Parameter<BdMemberCounsel> parameter) throws Exception {
		Map<String, Object> rsMap = bdInquiryService.setMemberCounselLeave(parameter.get());

		writeJson(parameter, rsMap);
	}
	
	/**
	 * @Desc      	: 엑셀다운로드
	 * @Method Name : downloadExcel
	 * @Date  	  	: 2020. 5. 29.
	 * @Author    	: sic
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-excel")
	public void downloadExcel(Parameter<BdInquirySearchVO> parameter) throws Exception {
		List<BdMemberCounsel> list = bdInquiryService.getCounselExcelList(parameter.get());
		
		ExcelValue excelValue = ExcelValue.builder(2, 0).columnNames(Arrays.asList("rowNum", "aswrStatName", "cnslTypeName",
				"inqryTitleText", "siteName", "memberInfo", "inqryDtmString", "aswrInfo", "aswrDtmString"))
				.data(list).build();
	
		parameter.downloadExcelTemplate("/board/voiceofcustomer/excel/counselList", excelValue);
	}

}