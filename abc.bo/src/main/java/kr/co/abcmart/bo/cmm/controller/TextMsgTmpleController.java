package kr.co.abcmart.bo.cmm.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.cmm.model.master.CmMessageTemplate;
import kr.co.abcmart.bo.cmm.service.MsgTemplateService;
import kr.co.abcmart.bo.cmm.vo.MsgTemplateSearchVO;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;

@Controller
@RequestMapping("/cmm/msg/text-template")
public class TextMsgTmpleController extends BaseController {

	private final static String SEND_PROC_GBN_TYPE_AUTO = "A"; // 자동 메일 발송 구분
	private final static String SEND_PROC_GBN_TYPE_MANUAL = "M"; // 자동 메일 발송 구분

	@Autowired
	MsgTemplateService cmMsgTempleService;

	/**
	 * @Desc : 문자템플릿 검색 Form 호출
	 * @Method Name : msgTemplateSearchForm
	 * @Date : 2019. 3. 11.
	 * @Author : kiowa
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/manual")
	public ModelAndView textMsgTemplateSearchForm(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = cmMsgTempleService.getTextMsgTemplateSearchForm();

		parameter.addAttribute("mesgTypeCodeList", resultMap.get("mesgTypeCodeList"));
		parameter.addAttribute("siteList", resultMap.get("siteList"));

		return forward("/cmm/text-msg-template/text-msg-manual-template-main");
	}

	/**
	 * @Desc : 문자 템플릿 조회 리스트
	 * @Method Name : readMsgTemplateList
	 * @Date : 2019. 3. 13.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/manual/read-list")
	public void readTextMsgTemplateList(Parameter<MsgTemplateSearchVO> parameter) throws Exception {
		MsgTemplateSearchVO msgTemplatetSearchVO = parameter.get();

		msgTemplatetSearchVO.setSendProcGbnType(SEND_PROC_GBN_TYPE_MANUAL);

		if (!UtilsText.isAllBlank(msgTemplatetSearchVO.getFromDate())
				&& !UtilsText.isAllBlank(msgTemplatetSearchVO.getToDate())) {
			msgTemplatetSearchVO.setSearchDateKey("modDtm");
		}

		Pageable<MsgTemplateSearchVO, CmMessageTemplate> cmMessageTemplate = new Pageable<>(parameter);
		Page<CmMessageTemplate> page = cmMsgTempleService.getTextMsgTemplateReadList(cmMessageTemplate);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 문자 템플릿 상세 조회
	 * @Method Name : readTextMsgTemplateDetail
	 * @Date : 2019. 3. 13.
	 * @Author : kiowa
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/manual/read-detail-pop")
	public ModelAndView readTextMsgTemplateDetail(Parameter<CmMessageTemplate> parameter) throws Exception {
		CmMessageTemplate cmMessageTemplate = parameter.get();

		cmMessageTemplate.setSendProcGbnType(SEND_PROC_GBN_TYPE_MANUAL);

		Map<String, Object> resultMap = cmMsgTempleService.getTextMsgTemplateReadDetail(cmMessageTemplate);

		parameter.addAttribute("mesgTypeCodeList", resultMap.get("mesgTypeCodeList"));
		parameter.addAttribute("siteList", resultMap.get("siteList"));
		parameter.addAttribute("cmMessageTemplate", resultMap.get("cmMessageTemplate"));

		return forward("/cmm/text-msg-template/text-msg-manual-template-detail-pop");
	}

	/**
	 * @Desc : 문자템플릿 정보를 등록한다.
	 * @Method Name : createTextMsgTemplate
	 * @Date : 2019. 3. 14.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/manual/create")
	public void createTextMsgTemplate(Parameter<CmMessageTemplate> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CmMessageTemplate cmMessageTemplate = parameter.get();
		String textMsgTitle = parameter.getString("mesgTmplName", false);
		String textMsgContText = parameter.getString("mesgContText", false);

		String adminNo = LoginManager.getUserDetails().getAdminNo();

		cmMessageTemplate.setSendProcGbnType(SEND_PROC_GBN_TYPE_MANUAL);

		if (UtilsText.isAllBlank(cmMessageTemplate.getUseYn())) {
			cmMessageTemplate.setUseYn(Const.BOOLEAN_TRUE);
		}

		cmMessageTemplate.setRgsterNo(adminNo);
		cmMessageTemplate.setModerNo(adminNo);

		try {
			if (textMsgTitle.indexOf("<script") > -1 || textMsgContText.indexOf("<script") > -1) {
				throw new Exception("발송 불가능한 내용이 포함되어 있습니다.");
			}
			cmMessageTemplate.setMesgTmplName(textMsgTitle);
			cmMessageTemplate.setMesgContText(textMsgContText);

			int rtnval = cmMsgTempleService.setTextMsgTemplateCreate(cmMessageTemplate);

			if (rtnval >= 1) {
				resultMap.put("code", Const.BOOLEAN_TRUE);
				resultMap.put("cnslScriptSeq", cmMessageTemplate.getMesgTmplSeq());
				resultMap.put("Message", "문자 템플릿 정보 정보를 등록 했습니다.");
			} else {
				resultMap.put("code", Const.BOOLEAN_FALSE);
				resultMap.put("Message", "문자 템플릿 정보를 등록 하지 못 했습니다.");
			}

		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 문자 템플릿 정보를 수정한다.
	 * @Method Name : modifyTextMsgTemplate
	 * @Date : 2019. 3. 14.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/manual/modify")
	public void modifyTextMsgTemplate(Parameter<CmMessageTemplate> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CmMessageTemplate cmMessageTemplate = parameter.get();
		String textMsgTitle = parameter.getString("mesgTmplName", false);
		String textMsgContText = parameter.getString("mesgContText", false);

		String adminNo = LoginManager.getUserDetails().getAdminNo();

		cmMessageTemplate.setModerNo(adminNo);

		try {
			if (textMsgTitle.indexOf("<script") > -1 || textMsgContText.indexOf("<script") > -1) {
				throw new Exception("발송 불가능한 내용이 포함되어 있습니다.");
			}
			cmMessageTemplate.setMesgTmplName(textMsgTitle);
			cmMessageTemplate.setMesgContText(textMsgContText);

			int rtnval = cmMsgTempleService.setTextMsgTemplateModify(cmMessageTemplate);

			if (rtnval >= 1) {
				resultMap.put("code", Const.BOOLEAN_TRUE);
				resultMap.put("cnslScriptSeq", cmMessageTemplate.getMesgTmplSeq());
				resultMap.put("Message", "문자 템플릿 정보 정보를 수정 했습니다.");
			} else {
				resultMap.put("code", Const.BOOLEAN_FALSE);
				resultMap.put("Message", "문자 템플릿 정보를 수정 하지 못 했습니다.");
			}

		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 문자 템플릿 정보를 삭제한다.
	 * @Method Name : deleteTextMsgTemplate
	 * @Date : 2019. 3. 18.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/manual/delete")
	public void deleteTextMsgTemplate(Parameter<CmMessageTemplate> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CmMessageTemplate cmMessageTemplate = parameter.get();

		try {
			int rtnval = cmMsgTempleService.setTextMsgTemplateDelete(cmMessageTemplate);

			if (rtnval >= 1) {
				resultMap.put("code", Const.BOOLEAN_TRUE);
				resultMap.put("cnslScriptSeq", cmMessageTemplate.getMesgTmplSeq());
				resultMap.put("Message", "문자 템플릿 정보 정보를 삭제 했습니다.");
			} else {
				resultMap.put("code", Const.BOOLEAN_FALSE);
				resultMap.put("Message", "문자 템플릿 정보를 삭제 하지 못 했습니다.");
			}

		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 수동 문자 템플릿 정보 엑셀 다운로드.
	 * @Method Name : manualTextMsgTemplateExcelDown
	 * @Date : 2019. 4. 11.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/manual/excel-down")
	public void manualTextMsgTemplateExcelDown(Parameter<MsgTemplateSearchVO> parameter) throws Exception {
		MsgTemplateSearchVO msgTemplatetSearchVO = parameter.get();

		msgTemplatetSearchVO.setSendProcGbnType(SEND_PROC_GBN_TYPE_MANUAL);

		if (!UtilsText.isAllBlank(msgTemplatetSearchVO.getFromDate())
				&& !UtilsText.isAllBlank(msgTemplatetSearchVO.getToDate())) {
			msgTemplatetSearchVO.setSearchDateKey("modDtm");
		}

		Pageable<MsgTemplateSearchVO, CmMessageTemplate> cmMessageTemplate = new Pageable<>(parameter);
		Page<CmMessageTemplate> page = cmMsgTempleService.getTextMsgTemplateReadListExcelDown(cmMessageTemplate);

		ExcelValue excelValue = ExcelValue.builder(1, 0).columnNames(Arrays.asList("siteName", "sendTypeCodeName",
				"mesgTypeCodeName", "mesgTmplName", "mesgContText", "modDtm")).data(page.getContent()).build();

		parameter.downloadExcelTemplate("cmm/text-msg-template/excel/textMsgList", excelValue);

	}

	/**
	 * @Desc : 자동 문자템플릿 검색 Form 호출.
	 * @Method Name : autoTextMsgTemplateSearchForm
	 * @Date : 2019. 4. 4.
	 * @Author : 이재렬
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/auto")
	public ModelAndView autoTextMsgTemplateSearchForm(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = cmMsgTempleService.getTextMsgTemplateSearchForm();

		parameter.addAttribute("mesgTypeCodeList", resultMap.get("mesgTypeCodeList"));
		parameter.addAttribute("siteList", resultMap.get("siteList"));

		return forward("/cmm/text-msg-template/text-msg-auto-template-main");
	}

	/**
	 * @Desc : 자동 문자 템플릿 조회 리스트
	 * @Method Name : readAutoTextMsgTemplateList
	 * @Date : 2019. 4. 5.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/auto/read-list")
	public void readAutoTextMsgTemplateList(Parameter<MsgTemplateSearchVO> parameter) throws Exception {
		MsgTemplateSearchVO msgTemplatetSearchVO = parameter.get();

		msgTemplatetSearchVO.setSendProcGbnType(SEND_PROC_GBN_TYPE_AUTO);

		if (!UtilsText.isAllBlank(msgTemplatetSearchVO.getFromDate())
				&& !UtilsText.isAllBlank(msgTemplatetSearchVO.getToDate())) {
			msgTemplatetSearchVO.setSearchDateKey("modDtm");
		}

		Pageable<MsgTemplateSearchVO, CmMessageTemplate> cmMessageTemplate = new Pageable<>(parameter);
		Page<CmMessageTemplate> page = cmMsgTempleService.getTextMsgTemplateReadList(cmMessageTemplate);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 자동 문자 템플릿 상세 조회.
	 * @Method Name : readAutoTextMsgTemplateDetail
	 * @Date : 2019. 4. 4.
	 * @Author : 이재렬
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/auto/read-detail-pop")
	public ModelAndView readAutoTextMsgTemplateDetail(Parameter<CmMessageTemplate> parameter) throws Exception {
		CmMessageTemplate cmMessageTemplate = parameter.get();

		cmMessageTemplate.setSendProcGbnType(SEND_PROC_GBN_TYPE_AUTO);

		Map<String, Object> resultMap = cmMsgTempleService.getTextMsgTemplateReadDetail(cmMessageTemplate);

		parameter.addAttribute("mesgTypeCodeList", resultMap.get("mesgTypeCodeList"));
		parameter.addAttribute("siteList", resultMap.get("siteList"));
		parameter.addAttribute("cmMessageTemplate", resultMap.get("cmMessageTemplate"));

		return forward("/cmm/text-msg-template/text-msg-auto-template-detail-pop");
	}

	/**
	 * @Desc : 자동 문자템플릿 정보를 등록.
	 * @Method Name : createAutoTextMsgTemplate
	 * @Date : 2019. 4. 4.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/auto/create")
	public void createAutoTextMsgTemplate(Parameter<CmMessageTemplate> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CmMessageTemplate cmMessageTemplate = parameter.get();
		String textMsgTitle = parameter.getString("mesgTmplName", false);
		String textMsgContText = parameter.getString("mesgContText", false);
		String failMesgContText = parameter.getString("failMesgContText", false);

		String adminNo = LoginManager.getUserDetails().getAdminNo();

		cmMessageTemplate.setSendProcGbnType(SEND_PROC_GBN_TYPE_AUTO);

		if (UtilsText.isAllBlank(cmMessageTemplate.getUseYn())) {
			cmMessageTemplate.setUseYn(Const.BOOLEAN_TRUE);
		}

		cmMessageTemplate.setRgsterNo(adminNo);
		cmMessageTemplate.setModerNo(adminNo);

		try {

			if (textMsgTitle.indexOf("<script") > -1 || textMsgContText.indexOf("<script") > -1
					|| failMesgContText.indexOf("<script") > -1) {
				throw new Exception("발송 불가능한 내용이 포함되어 있습니다.");
			}
			cmMessageTemplate.setMesgTmplName(textMsgTitle);
			cmMessageTemplate.setMesgContText(textMsgContText);
			cmMessageTemplate.setFailMesgContText(failMesgContText);

			int rtnval = cmMsgTempleService.setTextMsgTemplateCreate(cmMessageTemplate);

			if (rtnval >= 1) {
				resultMap.put("code", Const.BOOLEAN_TRUE);
				resultMap.put("cnslScriptSeq", cmMessageTemplate.getMesgTmplSeq());
				resultMap.put("Message", "문자 템플릿 정보 정보를 등록 했습니다.");
			} else {
				resultMap.put("code", Const.BOOLEAN_FALSE);
				resultMap.put("Message", "문자 템플릿 정보를 등록 하지 못 했습니다.");
			}

		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 자동 문자 템플릿 정보를 수정.
	 * @Method Name : autoModifyTextMsgTemplate
	 * @Date : 2019. 4. 8.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/auto/modify")
	public void autoModifyTextMsgTemplate(Parameter<CmMessageTemplate> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CmMessageTemplate cmMessageTemplate = parameter.get();
		String textMsgTitle = parameter.getString("mesgTmplName", false);
		String textMsgContText = parameter.getString("mesgContText", false);
		String failMesgContText = parameter.getString("failMesgContText", false);

		String adminNo = LoginManager.getUserDetails().getAdminNo();

		cmMessageTemplate.setModerNo(adminNo);

		try {
			if (textMsgTitle.indexOf("<script") > -1 || textMsgContText.indexOf("<script") > -1
					|| failMesgContText.indexOf("<script") > -1) {
				throw new Exception("발송 불가능한 내용이 포함되어 있습니다.");
			}
			cmMessageTemplate.setMesgTmplName(textMsgTitle);
			cmMessageTemplate.setMesgContText(textMsgContText);
			cmMessageTemplate.setFailMesgContText(failMesgContText);

			int rtnval = cmMsgTempleService.setTextMsgTemplateModify(cmMessageTemplate);

			if (rtnval >= 1) {
				resultMap.put("code", Const.BOOLEAN_TRUE);
				resultMap.put("cnslScriptSeq", cmMessageTemplate.getMesgTmplSeq());
				resultMap.put("Message", "문자 템플릿 정보 정보를 수정 했습니다.");
			} else {
				resultMap.put("code", Const.BOOLEAN_FALSE);
				resultMap.put("Message", "문자 템플릿 정보를 수정 하지 못 했습니다.");
			}

		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 자동 문자 템플릿 정보를 삭제.
	 * @Method Name : autoDeleteTextMsgTemplate
	 * @Date : 2019. 4. 8.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/auto/delete")
	public void autoDeleteTextMsgTemplate(Parameter<CmMessageTemplate> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CmMessageTemplate cmMessageTemplate = parameter.get();

		try {
			int rtnval = cmMsgTempleService.setTextMsgTemplateDelete(cmMessageTemplate);

			if (rtnval >= 1) {
				resultMap.put("code", Const.BOOLEAN_TRUE);
				resultMap.put("cnslScriptSeq", cmMessageTemplate.getMesgTmplSeq());
				resultMap.put("Message", "문자 템플릿 정보 정보를 삭제 했습니다.");
			} else {
				resultMap.put("code", Const.BOOLEAN_FALSE);
				resultMap.put("Message", "문자 템플릿 정보를 삭제 하지 못 했습니다.");
			}

		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 자동 문자 템플릿 정보 엑셀 다운로드.
	 * @Method Name : autoTextMsgTemplateExcelDown
	 * @Date : 2019. 4. 10.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/auto/excel-down")
	public void autoTextMsgTemplateExcelDown(Parameter<MsgTemplateSearchVO> parameter) throws Exception {
		MsgTemplateSearchVO msgTemplatetSearchVO = parameter.get();

		msgTemplatetSearchVO.setSendProcGbnType(SEND_PROC_GBN_TYPE_AUTO);

		if (!UtilsText.isAllBlank(msgTemplatetSearchVO.getFromDate())
				&& !UtilsText.isAllBlank(msgTemplatetSearchVO.getToDate())) {
			msgTemplatetSearchVO.setSearchDateKey("modDtm");
		}

		Pageable<MsgTemplateSearchVO, CmMessageTemplate> cmMessageTemplate = new Pageable<>(parameter);
		Page<CmMessageTemplate> page = cmMsgTempleService.getTextMsgTemplateReadListExcelDown(cmMessageTemplate);

		ExcelValue excelValue = ExcelValue.builder(1, 0).columnNames(Arrays.asList("siteName", "sendTypeCodeName",
				"mesgTypeCodeName", "mesgTmplName", "mesgContText", "modDtm")).data(page.getContent()).build();

		parameter.downloadExcelTemplate("cmm/text-msg-template/excel/textMsgList", excelValue);

	}

	/**
	 * @Desc : 발송코드 중복확인을 한다.
	 * @Method Name : readDoubleCheckMesgId
	 * @Date : 2019. 7. 11.
	 * @Author : 고웅환
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/auto/double-check")
	@ResponseBody
	public int readDoubleCheckMesgId(Parameter<?> parameter) throws Exception {
		return cmMsgTempleService.getMesgIdCount(parameter.getString("mesgId"));
	}

}