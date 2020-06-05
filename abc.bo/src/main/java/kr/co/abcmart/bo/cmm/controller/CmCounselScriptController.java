package kr.co.abcmart.bo.cmm.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.cmm.model.master.CmCounselScript;
import kr.co.abcmart.bo.cmm.service.CmCounselScriptService;
import kr.co.abcmart.bo.cmm.vo.CounselScriptSearchVO;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상담 스트립트 관리
 * @FileName : CounselScriptController.java
 * @Project : abc.bo
 * @Date : 2019. 1. 30.
 * @Author : kiowa
 */
@Slf4j
@Controller
@RequestMapping("cmm/counselscript")
public class CmCounselScriptController extends BaseController {

	@Autowired
	CmCounselScriptService conselScriptService;

	/**
	 * @Desc : 상담 스크립트 조회 form을 조회한다.
	 * @Method Name : counselScriptSearchForm
	 * @Date : 2019. 1. 30.
	 * @Author : kiowa
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("")
	public ModelAndView counselScriptSearchForm(Parameter<CounselScriptSearchVO> parameter) throws Exception {

		parameter.addAttribute("cnslGbnCode", conselScriptService.getCounselScriptSearchForm(CommonCode.CNSL_GBN_CODE));

		return forward("/cmm/counselscript/counselscript-main");
	}

	/**
	 * @Desc : 검색 조건에 맞는 상담스크립트 정보를 조회한다.
	 * @Method Name : readCounselScriptList
	 * @Date : 2019. 2. 8.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-list")
	public void readCounselScriptList(Parameter<CounselScriptSearchVO> parameter) throws Exception {
		Pageable<CounselScriptSearchVO, CmCounselScript> counselSriptVo = new Pageable<>(parameter);
		Page<CmCounselScript> page = conselScriptService.getCouselScriptReadList(counselSriptVo);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 선택한 상담스크립트 상세 정보를 등록 화면 혹은 상세 화면을 조회한다.
	 * @Method Name : readCounselScriptDetail
	 * @Date : 2019. 2. 8.
	 * @Author : kiowa
	 * @param parameter
	 * @param parameter1
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/read-detail")
	public ModelAndView readCounselScriptDetail(Parameter<CmCounselScript> parameter) throws Exception {
		CmCounselScript cmCounselScript = parameter.get();

		Map<String, Object> rtnVal = conselScriptService.getCouselScriptDetail(cmCounselScript);

		parameter.addAttribute("cnslGbnCode", rtnVal.get("cnslGbnCode")); // 상담 메뉴 코드
		parameter.addAttribute("cnslTypeCode", rtnVal.get("cnslTypeCode")); // 상담유형 코드
		parameter.addAttribute("cnslTypeDtlCode", rtnVal.get("cnslTypeDtlCode")); // 상담유형 상세 코드
		parameter.addAttribute("cmCounselScript", rtnVal.get("cmCounselScript")); // 상담스크립트 상세 정보

		return forward("/cmm/counselscript/counselscript-detail");
	}

	/**
	 * @Desc : 상담스크립트 정보를 등록한다.
	 * @Method Name : createCounselScript
	 * @Date : 2019. 2. 8.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/create")
	public void createCounselScript(Parameter<CmCounselScript> parameter) throws Exception {
		CmCounselScript cmCounselScript = parameter.get();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int rtnVal = 0; // 등록 개수

		try {
			cmCounselScript.validate();

			String cnslScriptContText = parameter.getString("cnslScriptContText", false);
			String cnslScriptTitleText = parameter.getString("cnslScriptTitleText", false);
			cmCounselScript.setCnslScriptContText(cnslScriptContText);
			cmCounselScript.setCnslScriptTitleText(cnslScriptTitleText);

			rtnVal = conselScriptService.insertCouselScriptDetail(cmCounselScript);
			if (rtnVal >= 1) {
				resultMap.put("code", Const.BOOLEAN_TRUE);
				resultMap.put("cnslScriptSeq", cmCounselScript.getCnslScriptSeq());
				resultMap.put("Message", "상담 스크립트 정보를 등록 했습니다.");
				/*
				 * } else if(rtnVal < 0) { resultMap.put("code","N"); resultMap.put("Message",
				 * "선택한 상담 유형에 이미 등록된 정보가 존재합니다.");
				 */
			} else {
				resultMap.put("code", Const.BOOLEAN_FALSE);
				resultMap.put("Message", "상담 스크립트 정보를 등록 하지 못 했습니다.");
			}
		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 상담 스크립트 정보 수정
	 * @Method Name : updateCounselScript
	 * @Date : 2019. 2. 8.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/update")
	public void updateCounselScript(Parameter<CmCounselScript> parameter) throws Exception {
		CmCounselScript cmCounselScript = parameter.get();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int rtnVal = 0; // 수정 개수

		try {
			cmCounselScript.validate();
			String cnslScriptContText = parameter.getString("cnslScriptContText", false);
			String cnslScriptTitleText = parameter.getString("cnslScriptTitleText", false);
			cmCounselScript.setCnslScriptContText(cnslScriptContText);
			cmCounselScript.setCnslScriptTitleText(cnslScriptTitleText);

			rtnVal = conselScriptService.updateCouselScriptDetail(cmCounselScript);

			if (rtnVal >= 1) {
				resultMap.put("code", Const.BOOLEAN_TRUE);
				resultMap.put("cnslScriptSeq", cmCounselScript.getCnslScriptSeq());
				resultMap.put("Message", "상담 스크립트 정보를 수정 했습니다.");
			} else {
				resultMap.put("code", Const.BOOLEAN_FALSE);
				resultMap.put("Message", "상담 스크립트 정보를 수정 하지 못 했습니다.");
			}
		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 상담 스크립트 상세페이지에서 삭제
	 * @Method Name : deleteCounselScript
	 * @Date : 2019. 2. 8.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@Deprecated
	@PostMapping("/delete")
	public void deleteCounselScript(Parameter<CmCounselScript> parameter) throws Exception {
		CmCounselScript cmCounselScript = parameter.get();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int rtnVal = 0; // 삭제 개수

		try {
			rtnVal = conselScriptService.deleteCounselScriptDetail(cmCounselScript);

			if (rtnVal >= 1) {
				resultMap.put("code", Const.BOOLEAN_TRUE);
				resultMap.put("Message", "상담 스크립트 정보를 삭제 했습니다.");
			} else {
				resultMap.put("code", Const.BOOLEAN_FALSE);
				resultMap.put("Message", "상담 스크립트 정보를 삭제 하지 못 했습니다.");
			}
		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 상담 스크립트 상세리스트에서 삭제
	 * @Method Name : multiDeleteCounselScript
	 * @Date : 2019. 2. 8.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@Deprecated
	@PostMapping("/multidelete")
	public void multiDeleteCounselScript(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int rtnVal = 0; // 삭제 개수

		try {
			CmCounselScript[] cmCounselScriptArray = parameter.createArray(CmCounselScript.class, "cnslScriptSeq",
					Arrays.asList("rgstDtm"));

			rtnVal = conselScriptService.deleteMmultiCounselScriptDetail(cmCounselScriptArray);

			if (rtnVal == cmCounselScriptArray.length) {
				resultMap.put("Code", Const.BOOLEAN_TRUE);
				resultMap.put("Message", "상담 스크립트 정보를 삭제 했습니다.");
			} else {
				resultMap.put("Code", Const.BOOLEAN_FALSE);
				resultMap.put("Message", "상담 스크립트 정보를 삭제 하지 못 했습니다.");
			}
		} catch (Exception e) {
			resultMap.put("Code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}
		Map<String, Object> resut = new HashMap<String, Object>();
		resut.put("Result", resultMap);
		writeJson(parameter, resut);
	}

	/**
	 * @Desc : 상담스크립트제목 조회 외부 서비스
	 * @Method Name : readCounselScriptDtText
	 * @Date : 2019. 2. 8.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-cnslscriptcontitle")
	public void readCounselScriptTitle(Parameter<CmCounselScript> parameter) throws Exception {
		CmCounselScript cmCounselScript = parameter.get();

		List<CmCounselScript> cmCounselScriptList = conselScriptService.selectCounselScriptTitle(cmCounselScript);

		writeJson(parameter, cmCounselScriptList);
	}

	/**
	 * @Desc : 상담 스크립트 내용 조회 외부 서비스
	 * @Method Name : readCounselScript
	 * @Date : 2019. 2. 8.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-cnslscriptconttext")
	public void readCounselScript(Parameter<CmCounselScript> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CmCounselScript cmCounselScript = parameter.get();

		if (cmCounselScript != null && !"".equals(cmCounselScript.getCnslScriptSeq())) {
			resultMap.put("cmCounselScript", conselScriptService.selectCouselScript(cmCounselScript));
		}

		writeJson(parameter, resultMap);
	}

}