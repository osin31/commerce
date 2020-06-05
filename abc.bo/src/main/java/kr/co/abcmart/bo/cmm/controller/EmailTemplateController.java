package kr.co.abcmart.bo.cmm.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.cmm.model.master.CmEmailTemplate;
import kr.co.abcmart.bo.cmm.service.EmailTemplateService;
import kr.co.abcmart.bo.cmm.vo.EmailTemplateSearchVO;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;

/**
 * @Desc : Eamil Template, Email Key 관리 Controller
 * @FileName : EmailController.java
 * @Project : abc.bo
 * @Date : 2019. 3. 21.
 * @Author : kiowa
 */
@Controller
@RequestMapping("/cmm/msg")
public class EmailTemplateController extends BaseController {

	@Autowired
	EmailTemplateService emailTemplateService;

	private final static String SEND_PROC_GBN_TYPE_AUTO = "A"; // 자동 메일 발송 구분
	private final static String SEND_PROC_GBN_TYPE_MANUAL = "M"; // 자동 메일 발송 구분

	/**
	 * @Desc : 이메일 수동 템플릿 검색 Form 호출
	 * @Method Name : emailTemplateSearchForm
	 * @Date : 2019. 3. 14.
	 * @Author : kiowa
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/email-template/manual")
	public ModelAndView emailTemplateSearchForm(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = emailTemplateService.getEmailTemplateSearchForm();

		parameter.addAttribute("emailTypeCodeList", resultMap.get("emailTypeCodeList"));
		parameter.addAttribute("siteList", resultMap.get("siteList"));

		return forward("/cmm/email-template/email-manual-template-main");
	}

	/**
	 * @Desc : 이메일 수동 템플릿 조회 리스트
	 * @Method Name : readEmailTemplateList
	 * @Date : 2019. 3. 14.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/email-template/manual/read-list")
	public void readEmailTemplateList(Parameter<EmailTemplateSearchVO> parameter) throws Exception {
		EmailTemplateSearchVO emailTemplateSearchVO = parameter.get();
		emailTemplateSearchVO.setSendProcGbnType(SEND_PROC_GBN_TYPE_MANUAL); // 발송처리구분
		emailTemplateSearchVO.setEmailTmplYn(Const.BOOLEAN_TRUE); // 이메일 템플릿 여부

		Pageable<EmailTemplateSearchVO, CmEmailTemplate> cmEmailTemplate = new Pageable<>(parameter);
		Page<CmEmailTemplate> page = emailTemplateService.getEmailTemplateList(cmEmailTemplate);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 이메일 수동 템플릿 상세 조회
	 * @Method Name : readEmailTemplateDetail
	 * @Date : 2019. 3. 14.
	 * @Author : kiowa
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/email-template/manual/read-detail-pop")
	public ModelAndView readEmailTemplateDetail(Parameter<CmEmailTemplate> parameter) throws Exception {
		CmEmailTemplate cmEmailTemplate = parameter.get();

		cmEmailTemplate.setSendProcGbnType(SEND_PROC_GBN_TYPE_MANUAL); // 발송처리구분
		cmEmailTemplate.setEmailTmplYn(Const.BOOLEAN_TRUE); // 이메일 템플릿 여부

		Map<String, Object> resultMap = emailTemplateService.getEmailTemplateReadDetail(cmEmailTemplate);

		parameter.addAttribute("emailTypeCodeList", resultMap.get("emailTypeCodeList"));
		parameter.addAttribute("siteList", resultMap.get("siteList"));
		parameter.addAttribute("cmEmailTemplate", resultMap.get("cmEmailTemplate"));

		return forward("/cmm/email-template/email-manual-template-detail-pop");
	}

	/**
	 * @Desc : 이메일 수동 템플릿 정보를 등록한다.
	 * @Method Name : saveEmailTemplateDetail
	 * @Date : 2019. 3. 18.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/email-template/manual/create")
	public void createEmailTemplate(Parameter<CmEmailTemplate> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CmEmailTemplate cmEmailTemplate = parameter.get();

		cmEmailTemplate.setSendProcGbnType(SEND_PROC_GBN_TYPE_MANUAL); // 발송처리구분
		cmEmailTemplate.setEmailTmplYn(Const.BOOLEAN_TRUE); // 이메일 템플릿 여부

		String adminNo = LoginManager.getUserDetails().getAdminNo();

		cmEmailTemplate.setRgsterNo(adminNo);
		cmEmailTemplate.setModerNo(adminNo);

		try {
			cmEmailTemplate.setEmailCnfrmCount(0);
			int rtnval = emailTemplateService.setEmailTemplateCreate(cmEmailTemplate);

			if (rtnval >= 1) {
				resultMap.put("code", Const.BOOLEAN_TRUE);
				resultMap.put("cnslScriptSeq", cmEmailTemplate.getEmailTmplSeq());
				resultMap.put("Message", "이메일 템플릿 정보를 저장했습니다.");
			} else {
				resultMap.put("code", Const.BOOLEAN_FALSE);
				resultMap.put("Message", "이메일 템플릿 정보를 저장하지 못 했습니다.");
			}

		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 이메일 수동 템플릿 정보를 수정한다.
	 * @Method Name : modifyEmailTemplateDetail
	 * @Date : 2019. 3. 18.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/email-template/manual/modify")
	public void modifyEmailTemplate(Parameter<CmEmailTemplate> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CmEmailTemplate cmEmailTemplate = parameter.get();

		String adminNo = LoginManager.getUserDetails().getAdminNo();

		cmEmailTemplate.setModerNo(adminNo);

		try {
			int rtnval = emailTemplateService.setEmailTemplateModify(cmEmailTemplate);

			if (rtnval >= 1) {
				resultMap.put("code", Const.BOOLEAN_TRUE);
				resultMap.put("cnslScriptSeq", cmEmailTemplate.getEmailTmplSeq());
				resultMap.put("Message", "이메일 템플릿 정보를 수정했습니다.");
			} else {
				resultMap.put("code", Const.BOOLEAN_FALSE);
				resultMap.put("Message", "이메일 템플릿 정보를 수정하지 못 했습니다.");
			}

		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 이메일 템플릿 정보를 삭제한다.
	 * @Method Name : deleteEmailTemplate
	 * @Date : 2019. 3. 18.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/email-template/manual/delete")
	public void deleteEmailTemplate(Parameter<CmEmailTemplate> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CmEmailTemplate cmEmailTemplate = parameter.get();

		try {
			int rtnval = emailTemplateService.setEmailTemplateDelete(cmEmailTemplate);

			if (rtnval >= 1) {
				resultMap.put("code", Const.BOOLEAN_TRUE);
				resultMap.put("cnslScriptSeq", cmEmailTemplate.getEmailTmplSeq());
				resultMap.put("Message", "이메일 템플릿 정보를 삭제했습니다.");
			} else {
				resultMap.put("code", Const.BOOLEAN_FALSE);
				resultMap.put("Message", "이메일 템플릿 정보를 삭제하지 못 했습니다.");
			}

		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 이메일 Key 검색 Form 호출
	 * @Method Name : emailKeySearchForm
	 * @Date : 2019. 3. 21.
	 * @Author : kiowa
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/email-key")
	public ModelAndView emailKeySearchForm(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = emailTemplateService.getEmailTemplateSearchForm();

		parameter.addAttribute("siteList", resultMap.get("siteList"));

		return forward("/cmm/email-key/email-key-main");
	}

	/**
	 * @Desc : 이메일 키 조회 리스트
	 * @Method Name : readEmailTemplateList
	 * @Date : 2019. 3. 14.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/email-key/read-list")
	public void readEmailKeyList(Parameter<EmailTemplateSearchVO> parameter) throws Exception {
		EmailTemplateSearchVO emailTemplateSearchVO = parameter.get();
		emailTemplateSearchVO.setSendProcGbnType(SEND_PROC_GBN_TYPE_AUTO);
		emailTemplateSearchVO.setEmailTmplYn(Const.BOOLEAN_FALSE);
		Pageable<EmailTemplateSearchVO, CmEmailTemplate> cmEmailTemplate = new Pageable<>(parameter);
		Page<CmEmailTemplate> page = emailTemplateService.getEmailTemplateList(cmEmailTemplate);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 이메일 키 상세/Form 조회
	 * @Method Name : readEmailKeyDetail
	 * @Date : 2019. 3. 22.
	 * @Author : kiowa
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/email-key/read-detail-pop")
	public ModelAndView readEmailKeyDetail(Parameter<CmEmailTemplate> parameter) throws Exception {
		CmEmailTemplate cmEmailTemplate = parameter.get();

		cmEmailTemplate.setSendProcGbnType(SEND_PROC_GBN_TYPE_AUTO); // 발송처리구분
		cmEmailTemplate.setEmailTmplYn(Const.BOOLEAN_FALSE); // 이메일 템플릿 여부

		Map<String, Object> resultMap = emailTemplateService.getEmailKeyReadDetail(cmEmailTemplate);

		parameter.addAttribute("siteList", resultMap.get("siteList"));
		parameter.addAttribute("cmEmailTemplate", resultMap.get("cmEmailTemplate"));

		return forward("/cmm/email-key/email-key-detail-pop");
	}

	/**
	 * @Desc : 광고 이메일 상세보기
	 * @Method Name : readAdvertiseEmailPreview
	 * @Date : 2019. 9. 4.
	 * @Author : 고웅환
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/email-key/read-preview-pop")
	public ModelAndView readAdvertiseEmailPreview(Parameter<CmEmailTemplate> parameter) throws Exception {
		parameter.addAttribute("cmEmailTemplate", parameter.get());

		return forward("/cmm/email-key/email-key-preview-pop");
	}

	/**
	 * @Desc : 이메일 템플릿 정보를 등록한다.
	 * @Method Name : saveEmailTemplateDetail
	 * @Date : 2019. 3. 18.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/email-key/create")
	public void createEmailKey(Parameter<CmEmailTemplate> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CmEmailTemplate cmEmailTemplate = parameter.get();

		String adminNo = LoginManager.getUserDetails().getAdminNo();

		cmEmailTemplate.setRgsterNo(adminNo);
		cmEmailTemplate.setModerNo(adminNo);

		try {

			cmEmailTemplate.setSendDtm(UtilsDate.getSqlTimeStamp(
					UtilsDate.parseDate(cmEmailTemplate.getSendDateTime(), Const.DEFAULT_DATETIME_PATTERN)));

			cmEmailTemplate.setEmailCnfrmCount(0);
			cmEmailTemplate.setSendProcGbnType(SEND_PROC_GBN_TYPE_AUTO); // 발송처리구분
			cmEmailTemplate.setEmailTmplYn(Const.BOOLEAN_FALSE); // 이메일 템플릿 여부
			cmEmailTemplate.setEmailTmplName(cmEmailTemplate.getEmailTitleText());

			int rtnval = emailTemplateService.setEmailTemplateCreate(cmEmailTemplate);

			if (rtnval >= 1) {
				resultMap.put("code", Const.BOOLEAN_TRUE);
				resultMap.put("cnslScriptSeq", cmEmailTemplate.getEmailTmplSeq());
				resultMap.put("Message", "광고 이메일을 등록 했습니다.");
			} else {
				resultMap.put("code", Const.BOOLEAN_FALSE);
				resultMap.put("Message", "광고 이메일을 등록 하지 못 했습니다.");
			}

		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 이메일 키 정보를 수정한다.
	 * @Method Name : modifyEmailTemplateDetail
	 * @Date : 2019. 3. 18.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/email-key/modify")
	public void modifyEmailKey(Parameter<CmEmailTemplate> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CmEmailTemplate cmEmailTemplate = parameter.get();

		String adminNo = LoginManager.getUserDetails().getAdminNo();

		cmEmailTemplate.setModerNo(adminNo);

		try {

			cmEmailTemplate.setSendDtm(UtilsDate.getSqlTimeStamp(
					UtilsDate.parseDate(cmEmailTemplate.getSendDateTime(), Const.DEFAULT_DATETIME_PATTERN)));

			int rtnval = emailTemplateService.setEmailTemplateModify(cmEmailTemplate);

			if (rtnval >= 1) {
				resultMap.put("code", Const.BOOLEAN_TRUE);
				resultMap.put("cnslScriptSeq", cmEmailTemplate.getEmailTmplSeq());
				resultMap.put("Message", "광고 이메일을 수정했습니다.");
			} else {
				resultMap.put("code", Const.BOOLEAN_FALSE);
				resultMap.put("Message", "광고 이메일을 수정하지 못 했습니다.");
			}

		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 이메일 자동 템플릿 검색 Form 호출
	 * @Method Name : emailAutoTemplateSearchForm
	 * @Date : 2019. 4. 5.
	 * @Author : 고웅환
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/email-template/auto")
	public ModelAndView emailAutoTemplateSearchForm(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = emailTemplateService.getEmailTemplateSearchForm();
		parameter.addAttribute("emailTypeCodeList", resultMap.get("emailTypeCodeList"));
		parameter.addAttribute("siteList", resultMap.get("siteList"));

		return forward("/cmm/email-template/email-auto-template-main");
	}

	/**
	 * @Desc : 이메일 자동 템플릿 조회 리스트
	 * @Method Name : readEmailAutoTemplateList
	 * @Date : 2019. 4. 5.
	 * @Author : 고웅환
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/email-template/auto/read-list")
	public void readEmailAutoTemplateList(Parameter<EmailTemplateSearchVO> parameter) throws Exception {
		EmailTemplateSearchVO emailTemplateSearchVO = parameter.get();
		emailTemplateSearchVO.setSendProcGbnType(SEND_PROC_GBN_TYPE_AUTO); // 발송처리구분
		emailTemplateSearchVO.setEmailTmplYn(Const.BOOLEAN_TRUE); // 이메일 템플릿 여부

		Pageable<EmailTemplateSearchVO, CmEmailTemplate> cmEmailTemplate = new Pageable<>(parameter);
		Page<CmEmailTemplate> page = emailTemplateService.getEmailTemplateList(cmEmailTemplate);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 이메일 자동 템플릿 상세 조회
	 * @Method Name : readEmailAutoTemplateDetail
	 * @Date : 2019. 4. 5.
	 * @Author : 고웅환
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/email-template/auto/read-detail-pop")
	public ModelAndView readEmailAutoTemplateDetail(Parameter<CmEmailTemplate> parameter) throws Exception {
		CmEmailTemplate cmEmailTemplate = parameter.get();

		cmEmailTemplate.setSendProcGbnType(SEND_PROC_GBN_TYPE_AUTO); // 발송처리구분
		cmEmailTemplate.setEmailTmplYn(Const.BOOLEAN_TRUE); // 이메일 템플릿 여부

		Map<String, Object> resultMap = emailTemplateService.getEmailTemplateReadDetail(cmEmailTemplate);

		parameter.addAttribute("emailTypeCodeList", resultMap.get("emailTypeCodeList"));
		parameter.addAttribute("siteList", resultMap.get("siteList"));
		parameter.addAttribute("cmEmailTemplate", resultMap.get("cmEmailTemplate"));

		return forward("/cmm/email-template/email-auto-template-detail-pop");
	}

	/**
	 * @Desc : 이메일 자동 템플릿 정보를 등록한다.
	 * @Method Name : createEmailAutoTemplate
	 * @Date : 2019. 4. 5.
	 * @Author : 고웅환
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/email-template/auto/create")
	public void createEmailAutoTemplate(Parameter<CmEmailTemplate> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CmEmailTemplate cmEmailTemplate = parameter.get();
		cmEmailTemplate.setSendProcGbnType(SEND_PROC_GBN_TYPE_AUTO); // 발송처리구분
		cmEmailTemplate.setEmailTmplYn(Const.BOOLEAN_TRUE); // 이메일 템플릿 여부
		cmEmailTemplate.setEmailContText(parameter.getString("emailContText", false));

		String adminNo = LoginManager.getUserDetails().getAdminNo();

		cmEmailTemplate.setRgsterNo(adminNo);
		cmEmailTemplate.setModerNo(adminNo);

		try {
			cmEmailTemplate.setEmailCnfrmCount(0);
			int rtnval = emailTemplateService.setEmailTemplateCreate(cmEmailTemplate);

			if (rtnval >= 1) {
				resultMap.put("code", Const.BOOLEAN_TRUE);
				resultMap.put("cnslScriptSeq", cmEmailTemplate.getEmailTmplSeq());
				resultMap.put("message", "이메일 템플릿 정보를 저장했습니다.");
			} else {
				resultMap.put("code", Const.BOOLEAN_FALSE);
				resultMap.put("message", "이메일 템플릿 정보를 저장하지 못 했습니다.");
			}

		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("message", e.toString());
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 이메일 자동 템플릿 정보를 수정한다.
	 * @Method Name : modifyEmailAutoTemplate
	 * @Date : 2019. 4. 5.
	 * @Author : 고웅환
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/email-template/auto/modify")
	public void modifyEmailAutoTemplate(Parameter<CmEmailTemplate> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CmEmailTemplate cmEmailTemplate = parameter.get();
		cmEmailTemplate.setEmailContText(parameter.getString("emailContText", false));

		String adminNo = LoginManager.getUserDetails().getAdminNo();

		cmEmailTemplate.setModerNo(adminNo);

		try {
			int rtnval = emailTemplateService.setEmailTemplateModify(cmEmailTemplate);

			if (rtnval >= 1) {
				resultMap.put("code", Const.BOOLEAN_TRUE);
				resultMap.put("cnslScriptSeq", cmEmailTemplate.getEmailTmplSeq());
				resultMap.put("message", "이메일 템플릿 정보를 수정했습니다.");
			} else {
				resultMap.put("code", Const.BOOLEAN_FALSE);
				resultMap.put("message", "이메일 템플릿 정보를 수정하지 못 했습니다.");
			}

		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("message", e.toString());
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 이메일 자동 템플릿 정보를 삭제한다.
	 * @Method Name : deleteEmailAutoTemplate
	 * @Date : 2019. 4. 5.
	 * @Author : 고웅환
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/email-template/auto/delete")
	public void deleteEmailAutoTemplate(Parameter<CmEmailTemplate> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CmEmailTemplate cmEmailTemplate = parameter.get();

		try {
			int rtnval = emailTemplateService.setEmailTemplateDelete(cmEmailTemplate);

			if (rtnval >= 1) {
				resultMap.put("code", Const.BOOLEAN_TRUE);
				resultMap.put("cnslScriptSeq", cmEmailTemplate.getEmailTmplSeq());
				resultMap.put("message", "이메일 템플릿 정보를 삭제했습니다.");
			} else {
				resultMap.put("code", Const.BOOLEAN_FALSE);
				resultMap.put("message", "이메일 템플릿 정보를 삭제하지 못 했습니다.");
			}

		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("message", e.toString());
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 이메일 ID 중복확인을 한다.
	 * @Method Name : readCheckEmailId
	 * @Date : 2019. 4. 12.
	 * @Author : 고웅환
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/email-template/auto/double-check")
	@ResponseBody
	public int readDoubleCheckEmailId(Parameter<?> parameter) throws Exception {
		return emailTemplateService.getEmailIdCount(parameter.getString("emailId"));
	}
}