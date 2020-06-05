package kr.co.abcmart.bo.noacl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.afterService.model.master.OcAsAcceptProduct;
import kr.co.abcmart.bo.afterService.service.AfterServiceService;
import kr.co.abcmart.bo.board.model.master.BdCorprBoard;
import kr.co.abcmart.bo.board.model.master.BdMemberCounsel;
import kr.co.abcmart.bo.cmm.model.master.CmCounselScript;
import kr.co.abcmart.bo.cmm.model.master.CmEmailSendHistory;
import kr.co.abcmart.bo.cmm.model.master.CmEmailTemplate;
import kr.co.abcmart.bo.cmm.model.master.CmMessageTemplate;
import kr.co.abcmart.bo.cmm.service.CmCounselScriptService;
import kr.co.abcmart.bo.cmm.service.EmailTemplateService;
import kr.co.abcmart.bo.cmm.service.ForbiddenWordService;
import kr.co.abcmart.bo.cmm.service.MailService;
import kr.co.abcmart.bo.cmm.service.MessageService;
import kr.co.abcmart.bo.cmm.service.MsgTemplateService;
import kr.co.abcmart.bo.cmm.vo.EmailTemplateSearchVO;
import kr.co.abcmart.bo.cmm.vo.MessageVO;
import kr.co.abcmart.bo.cmm.vo.MsgTemplateSearchVO;
import kr.co.abcmart.bo.member.service.MemberService;
import kr.co.abcmart.bo.member.vo.CertificationVO;
import kr.co.abcmart.bo.noacl.vo.CommonEmailPopupVO;
import kr.co.abcmart.bo.noacl.vo.DownloadVO;
import kr.co.abcmart.bo.noacl.vo.TextMsgVO;
import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.bo.order.service.OrderService;
import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SyEmployee;
import kr.co.abcmart.bo.system.repository.master.SyAdminDao;
import kr.co.abcmart.bo.system.service.AdminService;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.EmployeeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.bo.system.service.SystemService;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.message.Message;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsRequest;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.security.password.SHA256KISAPasswordEncoder;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

/**
 * security acl 검증 에서 제외되는 공통영역 컨트롤러
 *
 * @author 이동엽
 *
 */
@Slf4j
@Controller
@RequestMapping("noacl")
public class NoAclCommonController extends BaseController {

	private final static String SEND_PROC_GBN_TYPE_AUTO = "A"; // 자동 메일 발송 구분
	private final static String SEND_PROC_GBN_TYPE_MANUAL = "M"; // 자동 메일 발송 구분

	@Autowired
	private SessionRegistry sessionRegistry;

	@Autowired
	private AdminService adminService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private SystemService systemService;

	@Autowired
	private ForbiddenWordService forbiddenWordService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private MessageService messageService;

	/**
	 * 이메일 발송 템플릿 서비스
	 */
	@Autowired
	private EmailTemplateService emailTemplateService;

	/**
	 * 문자 발송 템플릿 서비스
	 */
	@Autowired
	private MsgTemplateService msgTemplateService;

	/**
	 * 메일 발송 서비스
	 */
	@Autowired
	private MailService mailService;

	@Autowired
	private SyAdminDao syAdminDao;

	// 상담 스크립트 조회
	@Autowired
	CmCounselScriptService conselScriptService;

	@Autowired
	private AfterServiceService afterServiceService;

	private String noPermissionMessage = "사용권한이 없습니다.";

	/**
	 * @Desc : 중복로그인 로그인 세팅
	 * @Method Name : setForceLogin
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/setForceLogin")
	public void setForceLogin(Parameter<?> parameter) throws Exception {

		String serverName = UtilsRequest.getRequest().getServerName().toLowerCase();

		// 입점업체 로그인 화면
		if (!serverName.startsWith(Const.LOGIN_DOMAIN_BO)) {
			String loginId = parameter.getString("username");
			String password = parameter.getString("password");
			String crtfcNoText = parameter.getString("crtfcNoText");
			UtilsRequest.getSession().setAttribute("loginId", loginId);
			UtilsRequest.getSession().setAttribute("password", password);
			UtilsRequest.getSession().setAttribute("crtfcNoText", crtfcNoText);
		}

		UtilsRequest.getSession().setAttribute("forceLoginYn", Const.BOOLEAN_TRUE);

		writeJson(parameter, true);
	}

	/**
	 * @Desc : po login 인증번호 세팅
	 * @Method Name : getPoLoginCrtfcNo
	 * @Date : 2019. 8. 19.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/getPoLoginCrtfcNo")
	public void getPoLoginCrtfcNo(Parameter<?> parameter) throws Exception {

		// 로그인아이디로 업체관리자정보 조회
		String loginId = parameter.getString("username");
		String password = parameter.getString("password");

		SyAdmin syAdmin = new SyAdmin();
		syAdmin.setLoginId(loginId);
		SyAdmin result = syAdminDao.selectAdminAndAuthorityByLoginId(syAdmin);

		if (result == null) {
			throw new Exception(Message.getMessage("member.msg.login.fail"));
		}

		SHA256KISAPasswordEncoder passwordEncoder = new SHA256KISAPasswordEncoder();
		String passwordEncode = passwordEncoder.encode(password, result.getPswdSaltText());
		// 화면에서 입력한 이용자의 비밀번호(평문)와 DB에서 가져온 이용자의 암호화된 비밀번호를 비교한다
		if (!passwordEncoder.matches(passwordEncode, result.getPswdText())) {
			// 비밀번호 실패횟수 저장
			String failCode = CommonCode.LOGIN_FAIL_RSN_PWD;
			syAdmin.setAdminNo(result.getAdminNo());
			syAdmin.setModerNo(result.getAdminNo());
			syAdmin.setLoginFailIncrease(Const.BOOLEAN_TRUE);
			if (result.getLoginFailCount().intValue() >= 4) {
				syAdmin.setPswdDscordYn(Const.BOOLEAN_TRUE);
				failCode = CommonCode.LOGIN_FAIL_RSN_PWD5FAIL;
			}
			try {
				systemService.updateAdmin(syAdmin);
				systemService.setAdminLoginLog(syAdmin, parameter, failCode);
			} catch (Exception e) {

			}
			if (result.getLoginFailCount().intValue() >= 4) {
				throw new Exception(Message.getMessage("member.msg.login.loginFail5Count"));
			} else {
				throw new Exception(Message.getMessage("member.msg.login.fail"));
			}
		}

		CertificationVO certificationVO = new CertificationVO();
		certificationVO.setCrtfcYn(Const.BOOLEAN_TRUE);
		certificationVO.setMemberNo(result.getAdminNo());
		certificationVO.setMemberName(result.getAdminName());
		certificationVO.setCrtfcNoSendInfo(result.getHdphnNoText());
		certificationVO.setCrtfcPathCode(CommonCode.CRTFC_PATH_CODE_VENDOR_LOGIN);

		String certNumTxt = memberService.createCertNumberHdphn(certificationVO, CommonCode.SEND_TYPE_CODE_SMS);
		
		writeJson(parameter, certNumTxt);
	}

	@PostMapping("/setPoLoginParameter")
	public void setPoLoginParameter(Parameter<?> parameter) throws Exception {

		String loginId = parameter.getString("username");
		String password = parameter.getString("password");
		String crtfcNoText = parameter.getString("crtfcNoText");

		log.debug("loginId : {}", loginId);
		log.debug("password : {}", password);
		log.debug("crtfcNoText : {}", crtfcNoText);

		UtilsRequest.getSession().setAttribute("loginId", loginId);
		UtilsRequest.getSession().setAttribute("password", password);
		UtilsRequest.getSession().setAttribute("crtfcNoText", crtfcNoText);

		writeJson(parameter, true);
	}

	/**
	 * @Desc : logout시 sessionRegistry expireNow
	 * @Method Name : expireUserSessions
	 * @Date : 2019. 6. 14.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/expire-user-session")
	public void expireUserSessions(Parameter<?> parameter) throws Exception {
		UserDetails userDetails = LoginManager.getUserDetails();

		List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
		for (Object principal : allPrincipals) {
			if (UtilsText.equals(String.valueOf(principal), userDetails.getLoginId())) {
				for (SessionInformation information : sessionRegistry.getAllSessions(principal, true)) {
					information.expireNow();
					sessionRegistry.removeSessionInformation(information.getSessionId());
				}
			}
		}
		writeJson(parameter, true);
	}

	/**
	 * @Desc : security acl 검증에서 제외되는 관리자 상세
	 * @Method Name : adminDetailPop
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin-detail-pop")
	public ModelAndView adminDetailPop(Parameter<SyAdmin> parameter) throws Exception {
		Map<String, Object> resutMap = new HashMap<>();
		Map<String, Object> dataMap = new HashMap<>();
		String adminNo = parameter.getString("adminNo");

		// 관리자 화면 생성 데이터(코드성 데이터)
		resutMap = adminService.getAdminDetailCodeData();

		// 관리자 상세 관련 데이터 조회
		dataMap = adminService.getAdminDetailInfo(adminNo);

		parameter.addAttribute("authGroup", resutMap.get("authGroup"));
		parameter.addAttribute("siteInfo", resutMap.get("siteInfo"));
		parameter.addAttribute("emailSiteCode", resutMap.get("emailSiteCode"));

		parameter.addAttribute("detailInfo", dataMap.get("detailData"));
		parameter.addAttribute("siteData", dataMap.get("siteData"));
		parameter.addAttribute("accessIpData", dataMap.get("accessIpData"));

		return forward("/system/admin/admin/admin-detail-account-pop");
	}

	/**
	 * @Desc : 관리자 상세 데이터 수정 (본인계정 수정)
	 * @Method Name : updateAdminAccount
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/update-account")
	@ResponseBody
	public void updateAdminAccount(Parameter<SyAdmin> parameter) throws Exception {
		SyAdmin params = parameter.get();

		Map<String, Object> resultMap = new HashMap<>();
		resultMap = adminService.updateAdminAccount(params);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : security acl 검증에서 제외되는 관리자 상세(간략정보)
	 * @Method Name : adminDetailPop
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin-detail-noauth-pop")
	public ModelAndView adminDetailNoauthPop(Parameter<SyAdmin> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<>();
		String adminNo = parameter.getString("adminNo");

		// 관리자 상세 관련 데이터 조회
		dataMap = adminService.getAdminDetailInfo(adminNo);
		parameter.addAttribute("detailInfo", dataMap.get("detailData"));

		return forward("/system/admin/admin/admin-detail-noauth-pop");
	}

	/**
	 * @Desc : 현재 패스워드 체크
	 * @Method Name : readCheckPswd
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-checkPswd")
	public void readCheckPswd(Parameter<SyAdmin> parameter) throws Exception {
		boolean result = false;

		SyAdmin params = new SyAdmin();
		Map<String, Object> resultMap = new HashMap<>();

		params.setAdminNo(parameter.getString("adminNo"));
		params.setPswdText(parameter.getString("pswdText"));

		result = adminService.getAdminCheckPswd(params);
		resultMap.put("result", result);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 패스워드 금지어 체크
	 * @Method Name : readForbiddenPswd
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-forbiddenPswd")
	public void readForbiddenPswd(Parameter<SyAdmin> parameter) throws Exception {
		Pair<Boolean, String> result = null;
		Map<String, Object> resultMap = new HashMap<>();

		String pswdText = parameter.getString("pswdText");
		result = forbiddenWordService.hasForbiddenPwdWithData(pswdText);
		resultMap.put("result", result);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 비밀번호 수정
	 * @Method Name : loginPasswordUpdate
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/login/password-update")
	public void loginPasswordUpdate(Parameter<SyAdmin> parameter) throws Exception {
		SyAdmin params = parameter.get();

		writeJson(parameter, systemService.updatePassword(params));

	}

	/**
	 * @Desc : 아이디 중복 체크
	 * @Method Name : readCheckLoginId
	 * @Date : 2019. 2. 18.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-chekLoginId")
	public void readCheckLoginId(Parameter<SyAdmin> parameter) throws Exception {
		SyAdmin params = parameter.get();
		Map<String, Object> resultMap = new HashMap<>();

		int cnt = adminService.getCheckLoginId(params);
		resultMap.put("resultCnt", cnt);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 임직원 인증 초기화
	 * @Method Name : updateCrtfcReset
	 * @Date : 2019. 2. 25.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/update-crtfc-reset")
	public void updateCrtfcReset(Parameter<SyEmployee> parameter) throws Exception {
		SyEmployee params = parameter.get();

		writeJson(parameter, employeeService.updateCrtfcReset(params));
	}

	/**
	 * @Desc : 관리자 찾기 Form
	 * @Method Name : managerFindPopForm
	 * @Date : 2019. 2. 14.
	 * @Author : kiowa
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-list-pop-form")
	public ModelAndView adminFindPopForm(Parameter<SyAdmin> parameter) throws Exception {

		UserDetails userDetails = LoginManager.getUserDetails();
		if (UtilsText.equals(userDetails.getUpAuthNo(), Const.ROLE_VENDER_GROUP)) {
			throw new Exception(this.noPermissionMessage);
		}

		Map<String, Object> resultMap = new HashMap<>();

		resultMap = adminService.getAdminFindPopForm();
		parameter.addAttribute("siteInfo", resultMap.get("siteInfo"));

		return forward("/system/admin/admin/admin-list-pop");
	}

	/**
	 * @Desc : 관리자 검색 Pop
	 * @Method Name : readAdminPop
	 * @Date : 2019. 2. 15.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-list-pop")
	public void readAdminPop(Parameter<SyAdmin> parameter) throws Exception {
		UserDetails userDetails = LoginManager.getUserDetails();
		if (UtilsText.equals(userDetails.getUpAuthNo(), Const.ROLE_VENDER_GROUP)) {
			throw new Exception(this.noPermissionMessage);
		}
		Pageable<SyAdmin, SyAdmin> adminVOPageble = new Pageable<>(parameter);
		Page<SyAdmin> page = adminService.getAdminList(adminVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 입점사 검색 공통 팝업 Form
	 * @Method Name : vendorSearchPop
	 * @Date : 2019. 2. 20.
	 * @Author : kiowa
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/vendor-search-pop")
	public ModelAndView vendorSearchPop(Parameter<?> parameter) throws Exception {
		UserDetails userDetails = LoginManager.getUserDetails();
		if (UtilsText.equals(userDetails.getUpAuthNo(), Const.ROLE_VENDER_GROUP)) {
			throw new Exception(this.noPermissionMessage);
		}
		return forward("/vendor/popup/vendor-search-pop");
	}

	/**
	 * @Desc : 협력 게시판 등록 팝업
	 * @Method Name : coworkCreateForm
	 * @Date : 2019. 7. 16.
	 * @Author : 신인철
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/cowork-create-form")
	public ModelAndView coworkCreateForm(Parameter<BdCorprBoard> parameter) throws Exception {
		// 첨부 파일 관련 상수
		parameter.addAttribute("COWORK_FILE_MAX_CNT", Const.COWORK_FILE_MAX_CNT);

		parameter.addAttribute("isUpdate", "false");

		return forward("/vendor/cowork/detail-cowork-pop");
	}

	/**
	 * @Desc : 입점사 검색 공통 팝업 결과
	 * @Method Name : vendorReadListPop
	 * @Date : 2019. 2. 20.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("vendor-read-pop-list")
	public void vendorReadListPop(Parameter<VdVendor> parameter) throws Exception {
		Pageable<VdVendor, VdVendor> pageable = new Pageable<>(parameter);
		pageable.setUsePage(true);
		Page<VdVendor> page = vendorService.selectVendorInfoList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 일반 다온로드 기능
	 * @Method Name : defaultContentTypedownload
	 * @Date : 2019. 3. 7.
	 * @Author : kiowa
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/defaultContentTypedownload")
	public ResponseEntity<?> defaultContentTypedownload(Parameter<?> parameter) throws Exception {
		DownloadVO downloadVO = parameter.create(DownloadVO.class);

		return parameter.download(downloadVO.getAtchFilePathText(), downloadVO.getDownLoadFileName());
	}

	/**
	 * @Desc : 요청한 코드의 하위 코드를 조회한다.
	 * @Method Name : getCodeDetailList
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/code-detail-list")
	@ResponseBody
	public void getCodeDetailList(Parameter<?> parameter) throws Exception {
		SyCodeDetail syCodeDetail = parameter.create(SyCodeDetail.class);

		syCodeDetail.setUseYn("Y");

		List<SyCodeDetail> rtnVal = commonCodeService.getCodeDtlInfoList(syCodeDetail);

		writeJson(parameter, rtnVal);
	}

	/**
	 * @Desc : 공통 메일 발송 Pop 창을 호출 한다.
	 * @Method Name : emailSendFormPop
	 * @Date : 2019. 3. 18.
	 * @Author : kiowa
	 * @return
	 */
	@RequestMapping("email-send-pop-form")
	public ModelAndView emailSendFormPop(Parameter<CommonEmailPopupVO> parameter) throws Exception {
		CommonEmailPopupVO commonEmailPopupVo = parameter.get();

		Map<String, Object> resultMap = emailTemplateService.getEmailSendForm(commonEmailPopupVo);

		parameter.addAttribute("commonEmailPopupVo", resultMap.get("commonEmailPopupVo"));
		parameter.addAttribute("emailTypeCodeList", resultMap.get("emailTypeCodeList"));
		parameter.addAttribute("siteList", resultMap.get("siteList"));
		parameter.addAttribute("textMsgVo", resultMap.get("textMsgVo"));

		return forward("/cmm/email-template/email-send-pop-form");
	}

	/**
	 * @Desc : 메일 보내기 팝업에서 이메일 템플릿 조회
	 * @Method Name : getEmailTmplNameList
	 * @Date : 2019. 3. 18.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("email-tmpl-read-list")
	public void readEmailTmplNameList(Parameter<EmailTemplateSearchVO> parameter) throws Exception {
		EmailTemplateSearchVO emailTemplateSearchVO = parameter.get();

		emailTemplateSearchVO.setSendProcGbnType(SEND_PROC_GBN_TYPE_MANUAL); // 발송처리구분
		emailTemplateSearchVO.setEmailTmplYn(Const.BOOLEAN_TRUE); // 이메일 템플릿 여부

		List<CmEmailTemplate> resultList = emailTemplateService.getEmailTemplateList(emailTemplateSearchVO);

		writeJson(parameter, resultList);
	}

	/**
	 * @Desc : 메일 자동 발송 템플릿 유형 조회
	 * @Method Name : readEmailTmplAutoNameList
	 * @Date : 2019. 4. 5.
	 * @Author : 고웅환
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("auto/email-tmpl-read-list")
	public void readEmailAutoTmplNameList(Parameter<EmailTemplateSearchVO> parameter) throws Exception {
		EmailTemplateSearchVO emailTemplateSearchVO = parameter.get();

		emailTemplateSearchVO.setSendProcGbnType(SEND_PROC_GBN_TYPE_AUTO); // 발송처리구분
		emailTemplateSearchVO.setEmailTmplYn(Const.BOOLEAN_TRUE); // 이메일 템플릿 여부

		List<CmEmailTemplate> resultList = emailTemplateService.getEmailTemplateList(emailTemplateSearchVO);

		writeJson(parameter, resultList);
	}

	/**
	 * @Desc : 메일을 발송한다.
	 * @Method Name : setEmailSend
	 * @Date : 2019. 3. 19.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("email-send")
	public void sendEmail(Parameter<CmEmailSendHistory> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		CmEmailSendHistory cmEmailSendHistory = parameter.get();
		String emailTitle = parameter.getString("emailTitleText", false);
		String emailCont = UtilsText.unescapeXss(parameter.getString("emailContText", false));
		try {
			if (emailTitle.indexOf("<script") > -1 || emailCont.indexOf("<script") > -1) {
				throw new Exception("발송 불가능한 내용이 포함되어 있습니다.");
			}
			cmEmailSendHistory.setEmailTitleText(emailTitle);
			cmEmailSendHistory.setEmailContText(emailCont);

			int rtnVal = mailService.sendMail(cmEmailSendHistory);

			if (rtnVal > 0) {
				resultMap.put("Code", Const.BOOLEAN_TRUE);
				resultMap.put("Message", "메일을 발송했습니다.");
			} else {
				resultMap.put("Code", Const.BOOLEAN_FALSE);
				resultMap.put("Message", "메일을 발송하지 못 했습니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("Code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 문자 메시지 공통 팝업
	 * @Method Name : textMsgSendFormPop
	 * @Date : 2019. 3. 29.
	 * @Author : kiowa
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("text-msg-send-pop-form")
	public ModelAndView textMsgSendFormPop(Parameter<TextMsgVO> parameter) throws Exception {
		TextMsgVO textMsgVo = parameter.get();

		Map<String, Object> resultMap = msgTemplateService.getTextMsgSendForm(textMsgVo);

		parameter.addAttribute("sendTypeCodeList", resultMap.get("sendTypeCodeList"));
		parameter.addAttribute("mesgTypeCodeList", resultMap.get("mesgTypeCodeList"));
		parameter.addAttribute("siteList", resultMap.get("siteList"));
		parameter.addAttribute("textMsgVo", resultMap.get("textMsgVo"));

		return forward("/cmm/text-msg-template/text-msg-send-pop-form");
	}

	/**
	 * @Desc : 문자 메시지 템플릿 조회
	 * @Method Name : readTextMesgTmplNameList
	 * @Date : 2019. 3. 29.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("text-msg-tmpl-read-list")
	public void readTextMesgTmplNameList(Parameter<MsgTemplateSearchVO> parameter) throws Exception {

		MsgTemplateSearchVO msgTemplatetSearchVO = parameter.get();

		msgTemplatetSearchVO.setSendProcGbnType(SEND_PROC_GBN_TYPE_MANUAL);
		List<CmMessageTemplate> resultList = msgTemplateService.getTextMesgTemplateList(msgTemplatetSearchVO);

		writeJson(parameter, resultList);
	}

	/**
	 * @Desc : 자동 문자 메시지 템플릿 조회
	 * @Method Name : readAutoTextMesgTmplNameList
	 * @Date : 2019. 4. 9.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("text-msg-auto-tmpl-read-list")
	public void readAutoTextMesgTmplNameList(Parameter<MsgTemplateSearchVO> parameter) throws Exception {

		MsgTemplateSearchVO msgTemplatetSearchVO = parameter.get();

		msgTemplatetSearchVO.setSendProcGbnType(SEND_PROC_GBN_TYPE_AUTO);
		List<CmMessageTemplate> resultList = msgTemplateService.getTextMesgTemplateList(msgTemplatetSearchVO);

		writeJson(parameter, resultList);
	}

	/**
	 * @Desc : 문자 메시지 전송
	 * @Method Name : sendTextMesg
	 * @Date : 2019. 3. 29.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@Deprecated
	@PostMapping("text-mesg-send")
	public void sendTextMesg(Parameter<MessageVO> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		MessageVO messageVO = parameter.get();
		UserDetails user = LoginManager.getUserDetails();
		String adminNo = user.getAdminNo();

		messageVO.setReal(true);

		try {
			SyAdmin syAdmin = adminService.selectAdminDetailInfo(adminNo);
			messageVO.setSndrName(syAdmin.getAdminName());
			messageService.setSendMessageProcess(messageVO);

			resultMap.put("Code", Const.BOOLEAN_TRUE);
			resultMap.put("Message", "메시지를 발송했습니다.");
		} catch (Exception e) {
			resultMap.put("Code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 입점업체 기본정보 팝업
	 * @Method Name : vendorInfoPop
	 * @Date : 2019. 3. 21.
	 * @Author : 유성민
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("vendor-info-pop")
	public ModelAndView vendorInfoPop(Parameter<VdVendor> parameter) throws Exception {
		VdVendor vdVendor = parameter.create(VdVendor.class);

		UserDetails userDetails = LoginManager.getUserDetails();
		if (UtilsText.equals(userDetails.getUpAuthNo(), Const.ROLE_VENDER_GROUP)) {
			vdVendor.setVndrNo(userDetails.getVndrNo());
		}

		String[] codeFields = { CommonCode.VNDR_STAT_CODE, CommonCode.CPN_TYPE_CODE, CommonCode.LOGIS_VNDR_CODE,
				CommonCode.BANK_CODE, CommonCode.DLVY_GUIDE_BGN_CODE };
		// 공통코드 조회
		Map<String, List<SyCodeDetail>> codeList = commonCodeService.getCodeListByGroup(codeFields);
		parameter.addAttribute("codeList", codeList);

		// 전시채널 조회
		parameter.addAttribute("channelList", siteService.getUseChannelList());

		// 입점사기본정보
		Map<String, Object> resultMap = vendorService.getVendorBaseTabInfo(vdVendor);

		return forward("/vendor/popup/vendor-info-pop", resultMap);
	}

	/**
	 * @Desc : 입점사 정보등록 화면(부가정보탭)
	 * @Method Name : vendorAddInfo
	 * @Date : 2019. 2. 13.
	 * @Author : 유성민
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/info/create-addinfo")
	public ModelAndView vendorAddInfo(Parameter<VdVendor> parameter) throws Exception {
		VdVendor vdVendor = parameter.get();

		UserDetails userDetails = LoginManager.getUserDetails();
		if (UtilsText.equals(userDetails.getUpAuthNo(), Const.ROLE_VENDER_GROUP)) {
			vdVendor.setVndrNo(userDetails.getVndrNo());
		}

		String[] codeFields = { CommonCode.LOGIS_VNDR_CODE };
		// 공통코드 조회
		Map<String, List<SyCodeDetail>> codeList = commonCodeService.getCodeListByGroup(codeFields);
		parameter.addAttribute("codeList", codeList);

		// 입점사 부가정보
		Map<String, Object> resultMap = vendorService.getVendorAddInfoTabBaseInfo(vdVendor);

		return forward("/vendor/info/create-form-vendor-addinfotab", resultMap);
	}

	/**
	 * @Desc : 유선상담 등록/상세 팝업
	 * @Method Name : memberCounselPop
	 * @Date : 2019. 4. 10.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("member-counsel-pop")
	public ModelAndView memberCounselPop(Parameter<BdMemberCounsel> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		BdMemberCounsel params = parameter.get();

		resultMap = memberService.getMemberCouselInfo(params);

		OcOrder ocOrder = new OcOrder();
		ocOrder.setMemberNo(params.getMemberNo());
		OcAsAcceptProduct ocAsAcceptProduct = new OcAsAcceptProduct();
		ocAsAcceptProduct.setMemberNo(params.getMemberNo());

		List<OcOrder> orderList = orderService.selectOrderMstList(ocOrder);
		List<OcAsAcceptProduct> asList = afterServiceService.getAsListByMemberNo(ocAsAcceptProduct);

		parameter.addAttribute("viewType", parameter.getString("viewType"));
		parameter.addAttribute("codeList", resultMap.get("codeList"));
		parameter.addAttribute("detailInfo", resultMap.get("detailInfo"));
		parameter.addAttribute("aswrCounselAttachFiles", resultMap.get("fileInfo"));
		parameter.addAttribute("bdMemberCounselMemo", resultMap.get("memoInfo"));
		parameter.addAttribute("siteInfo", resultMap.get("siteInfo"));
		parameter.addAttribute("memberInfo", resultMap.get("memberInfo"));
		parameter.addAttribute("orderList", orderList);
		parameter.addAttribute("asList", asList);

		return forward("/member/member-counsel-pop");
	}

	/**
	 * @Desc : 로그인 유무 체크
	 * @Method Name : loginMemberCheck
	 * @Date : 2019. 7. 12.
	 * @Author : 최경호
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/login-check")
	public void getLoginUserCheck(Parameter<?> parameter) throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		writeJson(parameter, user.isLogin());
	}

	/**
	 * @Desc : 스윗트래커 키값 가져오기
	 * @Method Name : getSweetTrackerKey
	 * @Date : 2019. 9. 4.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/get-sweettracker-key")
	public void getSweetTrackerKey(Parameter<?> parameter) throws Exception {
		Map<String, Object> sweetTracker = new HashMap<>();
		sweetTracker.put("t_key", Const.SWEETTRACKER_KEY_VALUE);

		writeJson(parameter, sweetTracker);
	}

	/**
	 * @Desc : 상담스크립트제목 조회 외부 서비스
	 * @Method Name : readCounselScriptDtText
	 * @Date : 2019. 2. 8.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/counselscript/read-cnslscriptcontitle")
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
	@RequestMapping("/counselscript/read-cnslscriptconttext")
	public void readCounselScript(Parameter<CmCounselScript> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		CmCounselScript cmCounselScript = parameter.get();

		if (cmCounselScript != null && !"".equals(cmCounselScript.getCnslScriptSeq())) {
			resultMap.put("cmCounselScript", conselScriptService.selectCouselScript(cmCounselScript));
		}

		writeJson(parameter, resultMap);
	}

}
