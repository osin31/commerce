package kr.co.abcmart.bo.system.service;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.ReflectionUtils;

import kr.co.abcmart.bo.cmm.model.master.CmDaysCondtn;
import kr.co.abcmart.bo.cmm.service.DaysCondtnService;
import kr.co.abcmart.bo.cmm.service.MailService;
import kr.co.abcmart.bo.cmm.service.MessageService;
import kr.co.abcmart.bo.cmm.vo.MailTemplateVO;
import kr.co.abcmart.bo.cmm.vo.MessageVO;
import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.bo.system.model.master.SyAdminAccessIp;
import kr.co.abcmart.bo.system.model.master.SyAdminAuthority;
import kr.co.abcmart.bo.system.model.master.SyAdminChangeHistory;
import kr.co.abcmart.bo.system.model.master.SyAdminUseHistory;
import kr.co.abcmart.bo.system.model.master.SyAuthority;
import kr.co.abcmart.bo.system.model.master.SyAuthorityChangeHistory;
import kr.co.abcmart.bo.system.model.master.SyAuthorityMenu;
import kr.co.abcmart.bo.system.repository.master.SyAdminAccessIpDao;
import kr.co.abcmart.bo.system.repository.master.SyAdminAuthorityDao;
import kr.co.abcmart.bo.system.repository.master.SyAdminChangeHistoryDao;
import kr.co.abcmart.bo.system.repository.master.SyAdminDao;
import kr.co.abcmart.bo.system.repository.master.SyAdminUseHistoryDao;
import kr.co.abcmart.bo.system.repository.master.SyAuthorityChangeHistoryDao;
import kr.co.abcmart.bo.system.repository.master.SyAuthorityDao;
import kr.co.abcmart.bo.system.repository.master.SyAuthorityMenuDao;
import kr.co.abcmart.bo.system.vo.HistoryFieldsName;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsCrypt;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.constant.MailCode;
import kr.co.abcmart.constant.MessageCode;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsDate;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminService {

	@Autowired
	private AdminService adminService;

	@Autowired
	private DaysCondtnService daysCondtnService;

	@Autowired
	private SyAdminDao syAdminDao;

	@Autowired
	private SyAdminUseHistoryDao syAdminUseHistoryDao;

	@Autowired
	private SyAdminAuthorityDao syAdminAuthorityDao;

	@Autowired
	private SyAdminAccessIpDao syAdminAccessIpDao;

	@Autowired
	private SyAuthorityDao syAuthorityDao;

	@Autowired
	private SyAuthorityMenuDao syAuthorityMenuDao;

	@Autowired
	private SyAdminChangeHistoryDao syAdminChangeHistoryDao;

	@Autowired
	private SyAuthorityChangeHistoryDao syAuthorityChangeHistoryDao;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private MailService mailService;

	@Autowired
	private MessageService messageService;

	/**
	 * @Desc : 관리자 목록을 조회
	 * @Method Name : getAdminList
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param pageable
	 * @return pageable
	 * @throws Exception
	 */
	public Page<SyAdmin> getAdminList(Pageable<SyAdmin, SyAdmin> pageable) throws Exception {
		int count = syAdminDao.selectAdminListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(syAdminDao.selectAdminList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 관리자 권한 조회
	 * @Method Name : getAuthorytyList
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param params
	 * @return List
	 * @throws Exception
	 */
	public List<SyAuthority> getAuthorytyList(SyAuthority params) throws Exception {
		return syAuthorityDao.selectAuthorytyList(params);
	}

	/**
	 * @Desc : 관리자 중복 체크 조회
	 * @Method Name : getCheckLoginId
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param params
	 * @return int
	 * @throws Exception
	 */
	public int getCheckLoginId(SyAdmin params) throws Exception {
		return syAdminDao.selectCheckLoginId(params);
	}

	/**
	 * @Desc : 관리자 등록
	 * @Method Name : setAdmin
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param syAdmin
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setAdmin(SyAdmin syAdmin) throws Exception {
		Map<String, Object> result = new HashMap<>();

		StringBuilder sb = new StringBuilder();
		Random rnd = new Random();
		for (int i = 0; i < 10; i++) {
			int rIndex = rnd.nextInt(3);
			switch (rIndex) {
			case 0:
				// a-z
				sb.append((char) ((int) (rnd.nextInt(26)) + 97));
				break;
			case 1:
				// A-Z
				sb.append((char) ((int) (rnd.nextInt(26)) + 65));
				break;
			case 2:
				// 0-9
				sb.append((rnd.nextInt(10)));
				break;
			}
		}

		Pair<String, String> pair = UtilsCrypt.getEncryptPassword(sb.toString());
		String passwordText = sb.toString();

		// 세션정보
		UserDetails user = LoginManager.getUserDetails();

		try {
			// 패스워드 암호화
			syAdmin.setPswdSaltText(pair.getSecond());
			syAdmin.setPswdText(pair.getFirst());

			// 등록자, 수정자 셋팅
			syAdmin.setRgsterNo(user.getAdminNo());
			syAdmin.setModerNo(user.getAdminNo());

			// 업체번호
			syAdmin.setVndrNo(syAdmin.getVndrNo());
			// 업체담당자번호
			short vndrMngrNo = syAdmin.getVndrMngrNo() != null ? syAdmin.getVndrMngrNo() : 0;
			syAdmin.setVndrMngrNo(vndrMngrNo);

			// 관리자 기본정보 등록
			adminService.insertAdmin(syAdmin);

			// 관리자 권한 등록
			adminService.insertAdminAuthority(syAdmin);

			if (syAdmin.getAccessIpTexts() != null) {
				// 관리자 접근아이피 등록
				adminService.insertAdminAccessIp(syAdmin);
			}

			// 등록 후 메일 발송
			syAdmin.setMailSendPswd(passwordText);
			if (UtilsText.isBlank(syAdmin.getVndrNo())) {
				syAdmin.setMailSendCode(MailCode.SY_ADMIN_PASSWORD);
				mailService.sendMail(getMailTemplateVo(syAdmin));
			} else {
				sendVendorAdminEmail(syAdmin);
			}
			result.put("result", Const.BOOLEAN_TRUE);

		} catch (Exception e) {
			log.debug(e.toString());
			e.printStackTrace(System.err);
			result.put("result", Const.BOOLEAN_FALSE);
		}

		return result;
	}

	/**
	 * @Desc : 이메일 발송
	 * @Method Name : sendVendorAdminEmail
	 * @Date : 2019. 8. 2.
	 * @Author : 신인철
	 * @param syAdmin
	 * @throws Exception
	 */
	public void sendVendorAdminEmail(SyAdmin syAdmin) throws Exception {
		MailTemplateVO mailVO = new MailTemplateVO();
		Map<String, Object> dataMap = new HashMap<>();

		VdVendor vendorParam = new VdVendor();
		vendorParam.setVndrNo(syAdmin.getVndrNo());
		vendorParam = vendorService.getVendorBaseInfo(vendorParam);

		dataMap.put("vndrName", vendorParam.getVndrName());
		dataMap.put("loginId", syAdmin.getLoginId());
		dataMap.put("pswdText", syAdmin.getMailSendPswd());
		dataMap.put("backOfficeUrl", Const.URL_WWW_HTTPS);
		dataMap.put("abcUrl", Const.SERVICE_DOMAIN_ART_FO);

		// 템플릿ID, 템플릿에 들어가는 모델, 회원 번호, 수신자 메일, 이름
		mailVO.setMailTemplateId(MailCode.SY_VENDOR_ADMIN_PASSWORD);
		mailVO.setMailTemplateModel(dataMap);
		mailVO.setReceiverEmail(syAdmin.getEmailAddrText());
		mailVO.setReceiverName(syAdmin.getAdminName());
		mailVO.setRealTime(true);

		mailService.sendMail(mailVO);
	}

	/**
	 * @Desc : A-Connect 관리자 등록 (A-Connect 관리자는 등록 시 패스워드 입력을 받으며, 가입시 메일을 발송하지 않아
	 *       별도로 해당 메소드 생성)
	 * @Method Name : setAdmin
	 * @Date : 2019. 6. 04.
	 * @Author : 이동엽
	 * @param syAdmin
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setAconnectAdmin(SyAdmin syAdmin) throws Exception {
		Map<String, Object> result = new HashMap<>();
		Pair<String, String> pair = UtilsCrypt.getEncryptPassword(syAdmin.getPswdText());

		// 세션정보
		UserDetails user = LoginManager.getUserDetails();

		try {
			// 패스워드 암호화
			syAdmin.setPswdSaltText(pair.getSecond());
			syAdmin.setPswdText(pair.getFirst());

			// 등록자, 수정자 셋팅
			syAdmin.setRgsterNo(user.getAdminNo());
			syAdmin.setModerNo(user.getAdminNo());

			// 업체번호
			syAdmin.setVndrNo(syAdmin.getVndrNo());
			// 업체담당자번호
			short vndrMngrNo = syAdmin.getVndrMngrNo() != null ? syAdmin.getVndrMngrNo() : 0;
			syAdmin.setVndrMngrNo(vndrMngrNo);

			// 관리자 기본정보 등록
			adminService.insertAdmin(syAdmin);

			// 관리자 권한 등록
			adminService.insertAdminAuthority(syAdmin);

			if (syAdmin.getAccessIpTexts() != null) {
				// 관리자 접근아이피 등록
				adminService.insertAdminAccessIp(syAdmin);
			}

			result.put("result", Const.BOOLEAN_TRUE);

		} catch (Exception e) {
			log.debug(e.toString());
			e.printStackTrace(System.err);
			result.put("result", Const.BOOLEAN_FALSE);
		}

		return result;
	}

	/**
	 * @Desc : 관리자 기본정보 등록
	 * @Method Name : insertAdmin
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param syAdmin
	 * @throws Exception
	 */
	public void insertAdmin(SyAdmin syAdmin) throws Exception {
		syAdminDao.insertAdmin(syAdmin);
	}

	/**
	 * @Desc : 관리자 권한 등록
	 * @Method Name : insertAdminAuthority
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param syAdmin
	 * @throws Exception
	 */
	public void insertAdminAuthority(SyAdmin syAdmin) throws Exception {
		SyAdminAuthority syAdminAuthority = new SyAdminAuthority();

		syAdminAuthority.setAdminNo(syAdmin.getAdminNo());
		syAdminAuthority.setAuthNo(syAdmin.getAuthNo());
		syAdminAuthority.setRgsterNo(syAdmin.getRgsterNo());

		syAdminAuthorityDao.insertAdminAuthority(syAdminAuthority);
	}

	/**
	 * @Desc : 관리자 접근 IP 등록
	 * @Method Name : insertAdminAccessIp
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param syAdmin
	 * @throws Exception
	 */
	public void insertAdminAccessIp(SyAdmin syAdmin) throws Exception {
		String[] ipItemArray = syAdmin.getAccessIpTexts();
		SyAdminAccessIp syAdminAccessIp = new SyAdminAccessIp();

		syAdminAccessIp.setAdminNo(syAdmin.getAdminNo());
		syAdminAccessIp.setRgsterNo(syAdmin.getRgsterNo());

		// 관리자 상세에서 수정 시 해당 유저의 데이터를 삭제 후 다시 입력.
		if (UtilsText.equals(syAdmin.getViewType(), Const.CRUD_U)) {
			syAdminAccessIpDao.deleteAdminAccessIp(syAdminAccessIp);
		}

		for (String ipItem : ipItemArray) {
			syAdminAccessIp.setAccessIpText(ipItem);
			syAdminAccessIpDao.insertAdminAccessIp(syAdminAccessIp);
		}
	}

	/**
	 * @Desc : 관리자 권한그룹 변경 (단일 형태의 데이터 수정)
	 * @Method Name : setAdminAuthority
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param syAdminAuthority
	 * @throws Exception
	 */
	public void setAdminAuthority(SyAdminAuthority syAdminAuthority) throws Exception {
		syAdminAuthorityDao.updataAdminAuthority(syAdminAuthority);
	}

	/**
	 * @Desc : 관리자 권한그룹 변경 (리스트 형태의 데이터 수정)
	 * @Method Name : setAdminAuthorityList
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param params
	 * @param authNo
	 * @throws Exception
	 */
	public void setAdminAuthorityList(SyAdmin[] params, String authNo) throws Exception {
		SyAdminAuthority syAdminAuthority = new SyAdminAuthority();
		syAdminAuthority.setAuthNo(authNo);

		for (SyAdmin syAdmin : params) {
			if (UtilsText.equals(syAdmin.getStatus(), "U")) {
				syAdminAuthority.setAdminNo(syAdmin.getAdminNo());
				syAdminAuthorityDao.updataAdminAuthority(syAdminAuthority);
			}
		}
	}

	/**
	 * @Desc : 관리자 화면 생성 데이터(코드성 데이터)
	 * @Method Name : getAdminDetailCodeData
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @return Map
	 * @throws Exception
	 */
	public Map<String, Object> getAdminDetailCodeData() throws Exception {

		ModelMap resultMap = new ModelMap();
		SyAuthority syAuthority = new SyAuthority();
		syAuthority.setUseYn(Const.BOOLEAN_TRUE);

		// 관리자 권한
		List<SyAuthority> authorytyList = getAuthorytyList(syAuthority);
		// 이메일
		resultMap.addAttribute("emailSiteCode", commonCodeService.getCodeNoName(CommonCode.EMAIL_SITE_CODE));
		resultMap.addAttribute("authGroup", authorytyList);

		return resultMap;
	}

	/**
	 * @Desc : 관리자 상세 관련 데이터 조회
	 * @Method Name : getAdminDetailInfo
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param adminNo
	 * @return Map
	 * @throws Exception
	 */
	public Map<String, Object> getAdminDetailInfo(String adminNo) throws Exception {
		ModelMap resultMap = new ModelMap();
		// 2019. 02. 18 Aconnect 관리자 관련 소스 추가
		if (adminNo != null && !"".equals(adminNo)) {
			String pswdInitYn = "";
			String condtnTermName = Const.ADMIN_PSWD_RENEW_CONDITION;
			Date pswdChngDtm = new Date();
			// 2019.02.15 리택토링을 위해 추가
			Map<String, Object> detailCodeMap = new ModelMap();
			// 관리자 기본정보 조회
			SyAdmin detailData = selectAdminDetailInfo(adminNo);
			pswdInitYn = detailData.getPswdInitYn();
			pswdChngDtm = detailData.getPswdChngDtm();

			// 비밀번호 초기화 값이 Y인 경우 조건날짜 관리 테이블에서 조건 값을 조회하여 비밀번호 변경기한 값을 세팅.
			if (UtilsText.equals(pswdInitYn, Const.BOOLEAN_TRUE)) {
				// 조건날짜 테이블 조회
				CmDaysCondtn cmDaysCondtn = daysCondtnService.getDaysCondtn(condtnTermName);

				String condtnTermValue = cmDaysCondtn.getCondtnTermValue();
				pswdChngDtm = UtilsDate.addDays(pswdChngDtm, Integer.parseInt(condtnTermValue));
				// Date 형태의 값을 Timestamp 형태의 값으로 변환
				Timestamp convertDate = new Timestamp(pswdChngDtm.getTime());
				detailData.setPswdChngDtm(convertDate);
			}
			// 접근 허용 아이피 조회
			List<SyAdminAccessIp> accessIpData = adminService.selectAdminAccessIp(adminNo);

			resultMap.addAttribute("detailData", detailData);
			resultMap.addAttribute("accessIpData", accessIpData);

			// 2019.02.15 리택토링을 위해 추가
			detailCodeMap = this.getAdminDetailCodeData();
			resultMap.addAttribute("authGroup", detailCodeMap.get("authGroup"));
		}

		// 2019.02.18 이메일 코드 정보 조회 추가.
		resultMap.addAttribute("emailSiteCode", commonCodeService.getCodeNoName(CommonCode.EMAIL_SITE_CODE));

		return resultMap;
	}

	/**
	 * @Desc : 관리자 기본정보 조회
	 * @Method Name : selectAdminDetailInfo
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param adminNo
	 * @return SyAdmin
	 * @throws Exception
	 */
	public SyAdmin selectAdminDetailInfo(String adminNo) throws Exception {
		return syAdminDao.selectAdminDetailInfo(adminNo);
	}

	/**
	 * @Desc : 접근 허용 아이피 조회
	 * @Method Name : selectAdminAccessIp
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param adminNo
	 * @return List
	 * @throws Exception
	 */
	public List<SyAdminAccessIp> selectAdminAccessIp(String adminNo) throws Exception {
		return syAdminAccessIpDao.selectAdminAccessIp(adminNo);
	}

	/**
	 * @Desc : 관리자 상세 데이터 수정
	 * @Method Name : updateAdmin
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param syAdmin
	 * @return Map
	 * @throws Exception
	 */
	public Map<String, Object> updateAdmin(SyAdmin syAdmin) throws Exception {
		Map<String, Object> result = new HashMap<>();
		SyAdminAuthority syAdminAuthority = new SyAdminAuthority();

		// 세션정보
		UserDetails user = LoginManager.getUserDetails();

		// 파라메터 세팅
		syAdmin.setRgsterNo(user.getAdminNo());
		syAdmin.setModerNo(user.getAdminNo());
		if (UtilsText.isEmpty(syAdmin.getBatchErrorAlertYn())) {
			syAdmin.setBatchErrorAlertYn(Const.BOOLEAN_FALSE);
		}
		syAdminAuthority.setAdminNo(syAdmin.getAdminNo());
		syAdminAuthority.setAuthNo(syAdmin.getAuthNo());

		// 변경이력 저장을 위해 과거 데이터 조회
		Map<String, Object> map = adminService.getAdminDetailInfo(syAdmin.getAdminNo());
		SyAdmin oldData = (SyAdmin) map.get("detailData");
		List<SyAdminAccessIp> accessIpData = (List<SyAdminAccessIp>) map.get("accessIpData");

		if (UtilsText.equals(Const.BOOLEAN_TRUE, oldData.getPswdDscordYn())
				&& UtilsText.equals(Const.BOOLEAN_FALSE, syAdmin.getPswdDscordYn())) {
			syAdmin.setLoginFailInit(Const.BOOLEAN_TRUE);
		}

		// 관리자 상세 데이터 중 N개가 존재하는 파라메터들의 경우 조회된 리스트들을 문자열로 반환(ex: 가,나)
		// 아이피 같은 경우는 추가 케이스만 저장되야 됨(To-Do)
		String accessIpText = accessIpData.stream().map(SyAdminAccessIp::getAccessIpText)
				.collect(Collectors.joining(","));
		oldData.setAccessIpText(accessIpText);

		// 관리자 기본정보 수정
		adminService.updateAdminDefaultData(syAdmin);

		// 관리자 권한 수정
		adminService.setAdminAuthority(syAdminAuthority);

		if (syAdmin.getAccessIpText() != null) {
			// 관리자 접근아이피 등록
			adminService.insertAdminAccessIp(syAdmin);
		}

		// 관리자 변경 이력 저장
		if (oldData != null) {
			try {
				String[] historyFields = { "adminName", "authName", "memberInfoMgmtYn", "accessIpText" };
				SyAdminChangeHistory historyParams = new SyAdminChangeHistory();

				for (String historyField : historyFields) {
					Field oldKey = ReflectionUtils.findField(oldData.getClass(), historyField);
					Field newKey = ReflectionUtils.findField(syAdmin.getClass(), historyField);
					oldKey.setAccessible(true);
					newKey.setAccessible(true);

					String oldValue = (String) oldKey.get(oldData);
					String newValue = (String) newKey.get(syAdmin);

					if (!UtilsText.equals(oldValue, newValue)) {
						historyParams.setAdminNo(syAdmin.getAdminNo());
						historyParams.setChngField(historyField);
						historyParams.setChngFieldName(HistoryFieldsName.getByCode(historyField).getDesc());
						historyParams.setChngBeforeFieldValue(oldValue);
						historyParams.setChngAfterFieldValue(newValue);
						historyParams.setRgsterNo(user.getAdminNo());

						adminService.insertAdminChangeHistory(historyParams);
					}
				}
			} catch (Exception e) {
				log.error("관리자 변경이력 저장 에러 : {}", e.getMessage());
			}
		}

		result.put("result", Const.BOOLEAN_TRUE);

		return result;
	}

	/**
	 * @Desc : 관리자 상세 데이터 수정 (본인계정 수정)
	 * @Method Name : updateAdminAccount
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param syAdmin
	 * @return Map
	 * @throws Exception
	 */
	public Map<String, Object> updateAdminAccount(SyAdmin syAdmin) throws Exception {
		int resultCnt = 0;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 세션정보
		UserDetails user = LoginManager.getUserDetails();
		// 암호화 SaltKey를 셋팅하기 위해 등록되어 있는 데이터 조회
		SyAdmin oldData = selectAdminDetailInfo(syAdmin.getAdminNo());
		// 파라메터 세팅
		syAdmin.setPswdText(UtilsCrypt.sha256(syAdmin.getPswdText(), oldData.getPswdSaltText()));
		syAdmin.setPswdInitYn(Const.BOOLEAN_FALSE);
		syAdmin.setPswdChangeYn(Const.BOOLEAN_TRUE);
		syAdmin.setModerNo(user.getAdminNo());

		resultCnt = adminService.updateAdminDefaultData(syAdmin);

		if (resultCnt > 0) {
			user.setPswdInitYn(Const.BOOLEAN_FALSE);
			user.setPswdChngDtm(UtilsDate.getSqlTimeStamp());
			LoginManager.setUserDetails(user);
		}

		// 비밀번호 변경 이력을 저장
		if (oldData != null) {
			try {
				String[] historyFields = { "pswdText" };
				SyAdminChangeHistory historyParams = new SyAdminChangeHistory();

				for (String historyField : historyFields) {
					Field oldKey = ReflectionUtils.findField(oldData.getClass(), historyField);
					Field newKey = ReflectionUtils.findField(syAdmin.getClass(), historyField);
					oldKey.setAccessible(true);
					newKey.setAccessible(true);

					String oldValue = (String) oldKey.get(oldData);
					String newValue = (String) newKey.get(syAdmin);

					if (!UtilsText.equals(oldValue, newValue)) {
						historyParams.setAdminNo(syAdmin.getAdminNo());
						historyParams.setChngField(historyField);
						historyParams.setChngFieldName(HistoryFieldsName.getByCode(historyField).getDesc());
						historyParams.setChngBeforeFieldValue(oldValue);
						historyParams.setChngAfterFieldValue(newValue);
						historyParams.setRgsterNo(user.getAdminNo());

						adminService.insertAdminChangeHistory(historyParams);
					}
				}
			} catch (Exception e) {
				log.error("관리자 수정 이력 저장 에러 : {}", e.getMessage());
			}
		}

		resultMap.put("result", resultCnt);

		if (resultCnt > 0) {
			resultMap.put("Message", "저장되었습니다.");
		} else {
			resultMap.put("Message", "저장에 실패하였습니다.");
		}

		return resultMap;
	}

	/**
	 * @Desc : 관리자 기본정보 변경
	 * @Method Name : updateAdminDefaultData
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param syAdmin
	 * @return int
	 * @throws Exception
	 */
	public int updateAdminDefaultData(SyAdmin syAdmin) throws Exception {
		return syAdminDao.updateAdmin(syAdmin);
	}

	/**
	 * @Desc : 관리자 변경 이력 저장
	 * @Method Name : insertAdminChangeHistoryNoTrx
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param historyParams
	 * @throws Exception
	 */
	public void insertAdminChangeHistory(SyAdminChangeHistory historyParams) throws Exception {
		syAdminChangeHistoryDao.insertAdminChangeHistoryNoTrx(historyParams);
	}

	/**
	 * @Desc : 비밀번호 초기화
	 * @Method Name : updatePswdReset
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param params
	 * @return Map
	 * @throws Exception
	 */
	public Map<String, Object> updatePswdReset(SyAdmin params) throws Exception {
		// 비밀번호 변경 플래그
		String pswdYn = Const.BOOLEAN_TRUE;
		Map<String, Object> result = new HashMap<>();
		// 세션정보
		UserDetails user = LoginManager.getUserDetails();
		// 이력 저장을 위한 관리자 조회 및 데이터 세팅
		SyAdmin oldData = syAdminDao.selectAdminDetailInfo(params.getAdminNo());

		// 임시 비밀번호 생성 및 파라메터 세팅
		// 자바 UUID 유틸을 사용하여 랜덤한 36자리의 숫자&영문소문자 문자열값을 출력
		// 구분자인 '-' 제거 총 32자리의 랜덤 문자열이 출력 그중 앞에서 부터 12자리를 잘라서 사용
		// 요구사항 : 영문 소문자 & 숫자 조합 12자리
		String tempPswd = UUID.randomUUID().toString().replaceAll("-", "");
		tempPswd = tempPswd.substring(0, 12);

		// PswdSaltText값이 존재하지 않을 경우
		if (UtilsText.isBlank(oldData.getPswdSaltText())) {
			oldData.setPswdSaltText(UtilsCrypt.getSalt());
			params.setPswdSaltText(oldData.getPswdSaltText());
		}

		// 임시 비밀번호 암호화 후 파라메터 세팅
		params.setPswdText(UtilsCrypt.sha256(tempPswd, oldData.getPswdSaltText()));
		params.setPswdInitYn(pswdYn);
		params.setPswdChangeYn(pswdYn);
		params.setLoginFailInit(Const.BOOLEAN_TRUE);
		params.setPswdDscordYn(Const.BOOLEAN_FALSE);
		params.setModerNo(user.getAdminNo());

		int resultCnt = adminService.updateAdminDefaultData(params);

		if (resultCnt > 0) {
			user.setPswdInitYn(Const.BOOLEAN_TRUE);
			user.setPswdChngDtm(UtilsDate.getSqlTimeStamp());
			LoginManager.setUserDetails(user);
		}

		// 비밀번호 변경 이력을 저장
		if (oldData != null) {
			try {
				String[] historyFields = { "pswdText" };
				SyAdminChangeHistory historyParams = new SyAdminChangeHistory();

				for (String historyField : historyFields) {
					Field oldKey = ReflectionUtils.findField(oldData.getClass(), historyField);
					Field newKey = ReflectionUtils.findField(params.getClass(), historyField);
					oldKey.setAccessible(true);
					newKey.setAccessible(true);

					String oldValue = (String) oldKey.get(oldData);
					String newValue = (String) newKey.get(params);

					if (!UtilsText.equals(oldValue, newValue)) {
						historyParams.setAdminNo(params.getAdminNo());
						historyParams.setChngField(historyField);
						historyParams.setChngFieldName(HistoryFieldsName.getByCode(historyField).getDesc());
						historyParams.setChngBeforeFieldValue(oldValue);
						historyParams.setChngAfterFieldValue(newValue);
						historyParams.setRgsterNo(user.getAdminNo());

						adminService.insertAdminChangeHistory(historyParams);
					}
				}
			} catch (Exception e) {
				log.error("비밀번호 초기화 이력 저장 에러 : {}", e.getMessage());
			}
		}

		Map<String, String> map = new HashMap<>();
		map.put("tempPswd", tempPswd);

		MessageVO messageVO = new MessageVO();
		messageVO.setSndrName(Const.SYS_SENDER_MESSAGE_NAME); // 발신자
		messageVO.setSendTelNoText(Const.SYS_SENDER_MESSAGE_NUMBER); // 발신 번호
		messageVO.setRcvrName(params.getAdminName()); // 수신사
		messageVO.setRecvTelNoText(params.getHdphnNoText()); // 수신번호
		messageVO.setMesgId(MessageCode.MESG_ID_SYADMIN_PW_RESET);
		messageVO.setMessageTemplateModel(map);
		messageVO.setSendTypeCode(CommonCode.SEND_TYPE_CODE_SMS);
		messageVO.setReal(true);

		messageService.setSendMessageProcess(messageVO);
		result.put("result", Const.BOOLEAN_TRUE);

		return result;
	}

	/**
	 * @Desc : 비밀번호 체크
	 * @Method Name : getAdminCheckPswd
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param params
	 * @return boolean
	 * @throws Exception
	 */
	public boolean getAdminCheckPswd(SyAdmin params) throws Exception {
		boolean result = false;
		String inputPswd = "";
		String oldPswd = "";
		String oldPswdSalt = "";

		// 저장되어 있는 데이터를 조회 & 저장된 패스워드 세팅
		SyAdmin oldData = selectAdminDetailInfo(params.getAdminNo());
		oldPswd = oldData.getPswdText();
		oldPswdSalt = oldData.getPswdSaltText();

		// 입력된 비밀번호 값을 암호화하여 비교
		inputPswd = UtilsCrypt.sha256(params.getPswdText(), oldPswdSalt);
		if (UtilsText.equals(oldPswd, inputPswd)) {
			result = true;
		}

		return result;
	}

	/**
	 * @Desc : 관리자 로그인 이력 조회
	 * @Method Name : getAdminUseHistoryList
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param pageable
	 * @return Page
	 * @throws Exception
	 */
	public Page<SyAdminUseHistory> getAdminUseHistoryList(Pageable<SyAdminUseHistory, SyAdminUseHistory> pageable)
			throws Exception {

		int count = syAdminUseHistoryDao.selectAdminUseHistoryListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(syAdminUseHistoryDao.selectAdminUseHistoryList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 관리자 변경 이력 조회
	 * @Method Name : getAdminChangeHistoryList
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param pageable
	 * @return pageable
	 * @throws Exception
	 */
	public Page<SyAdminChangeHistory> getAdminChangeHistoryList(
			Pageable<SyAdminChangeHistory, SyAdminChangeHistory> pageable) throws Exception {

		int count = syAdminChangeHistoryDao.selectAdminChangeHistoryListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(syAdminChangeHistoryDao.selectAdminChangeHistoryList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 권한그룹목록 조회
	 * @Method Name : getAuthorityGroupList
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SyAuthority> getAuthorityGroupList(Pageable<SyAuthority, SyAuthority> pageable) throws Exception {
		int count = syAuthorityDao.selectAuthorityGroupListCount(pageable);
		pageable.setTotalCount(count);
		if (count > 0) {
			pageable.setContent(syAuthorityDao.selectAuthorityGroupList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 권한그룹 등록/수정
	 * @Method Name : updateAuthorityGroup
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateAuthorityGroup(SyAuthority params) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int resultCnt = 0;
		UserDetails user = LoginManager.getUserDetails();
		if (UtilsText.equals(params.getStatus(), Const.CRUD_C)) {
			params.setRgsterNo(user.getAdminNo());
			params.setModerNo(user.getAdminNo());
			resultCnt = syAuthorityDao.insertAuthorityGroup(params);
		} else if (UtilsText.equals(params.getStatus(), Const.CRUD_U)) {
			// 변경이력 저장위해 예전데이터 조회
			SyAuthority ChngBeforeParam = new SyAuthority();
			ChngBeforeParam.setAuthNo(params.getAuthNo());
			SyAuthority oldData = getAuthorytyList(ChngBeforeParam).stream().findFirst().orElse(null);

			params.setModerNo(user.getAdminNo());
			resultCnt = syAuthorityDao.updateAuthorityGroup(params);

			// 변경이력 저장
			if (resultCnt > 0 && oldData != null) {
				try {
					String[] historyFields = { "authName", "authApplySystemType", "useYn" };
					SyAuthorityChangeHistory historyParams = new SyAuthorityChangeHistory();

					for (String historyField : historyFields) {
						Field oldKey = ReflectionUtils.findField(oldData.getClass(), historyField);
						Field newKey = ReflectionUtils.findField(params.getClass(), historyField);
						oldKey.setAccessible(true);
						newKey.setAccessible(true);

						String oldValue = (String) oldKey.get(oldData);
						String newValue = (String) newKey.get(params);

						if (!UtilsText.equals(oldValue, newValue)) {
							historyParams.setAuthNo(oldData.getAuthNo());
							historyParams.setChngField(historyField);
							historyParams.setChngFieldName(HistoryFieldsName.getByCode(historyField).getDesc());
							historyParams.setChngBeforeFieldValue(oldValue);
							historyParams.setChngAfterFieldValue(newValue);
							historyParams.setRgsterNo(user.getAdminNo());
							log.debug("historyField : {}, value : {}", historyField, oldKey.get(params));

							adminService.insertAuthorityGroupHistory(historyParams);

						}
					}
				} catch (Exception e) {
					log.error("권한변경이력 저장 에러 : {}", e.getMessage());
				}
			}
		}

		if (resultCnt > 0) {
			resultMap.put("Message", "저장되었습니다.");
		} else {
			resultMap.put("Message", "저장에 실패하였습니다.");
		}

		return resultMap;
	}

	/**
	 * @Desc : 권한이력 삭제
	 * @Method Name : deleteAuthorityGroup
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> deleteAuthorityGroup(SyAuthority params) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 해당 그룹에 관리자가 있는지 유무 확인
		int userCount = syAuthorityDao.selectAdminCount(params);
		if (userCount > 0) {
			resultMap.put("result", Const.BOOLEAN_FALSE);
			resultMap.put("Message", "해당 권한그룹에 관리자가 있습니다.\n권한 그룹 이동 후 삭제 가능합니다.");
		} else {
			SyAuthorityMenu syAuthorityMenu = new SyAuthorityMenu();
			syAuthorityMenu.setAuthNo(params.getAuthNo());
			syAuthorityMenuDao.deleteAuthorityMenu(syAuthorityMenu);

			SyAuthorityChangeHistory syAuthorityChangeHistory = new SyAuthorityChangeHistory();
			syAuthorityChangeHistory.setAuthNo(params.getAuthNo());
			syAuthorityChangeHistoryDao.deleteAuthorityChangeHistory(syAuthorityChangeHistory);

			int resultCnt = syAuthorityDao.delete(params);
			if (resultCnt > 0) {
				resultMap.put("result", Const.BOOLEAN_TRUE);
				resultMap.put("Message", "삭제 되었습니다.");
			} else {
				resultMap.put("result", Const.BOOLEAN_FALSE);
				resultMap.put("Message", "삭제에 실패하였습니다.");
			}
		}

		return resultMap;
	}

	/**
	 * @Desc : 관리자 권한그룹 변경이력 저장
	 * @Method Name : insertAuthorityGroupHistoryNoTrx
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param historyParams
	 * @throws Exception
	 */
	public void insertAuthorityGroupHistory(SyAuthorityChangeHistory historyParams) throws Exception {
		syAuthorityChangeHistoryDao.insertAuthorityGroupHistoryNoTrx(historyParams);
	}

	/**
	 * @Desc : 관리자 권한메뉴 수정
	 * @Method Name : updateAuthorityGroupMenuList
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@CacheEvict(value = { "userMenuService.getUserMenuList" }, allEntries = true)
	public int updateAuthorityGroupMenuList(SyAuthorityMenu[] params) throws Exception {
		int resultCnt = 0;
		UserDetails user = LoginManager.getUserDetails();

		String authNo = UtilsText.defaultString(params[0].getAuthNo(), null);
		if (UtilsText.isEmpty(authNo)) {
			throw new Exception("권한번호가 없습니다");
		}
		String useYn = Const.BOOLEAN_FALSE;
		for (SyAuthorityMenu param : params) {
			param.setRgsterNo(user.getAdminNo());
			param.setModerNo(user.getAdminNo());
			param.setAuthNo(authNo);
			if (param.getUseYnCode() > 0) {
				useYn = Const.BOOLEAN_TRUE;
			}
			param.setUseYn(useYn);
			resultCnt = syAuthorityMenuDao.updateAuthorityGroupMenu(param);
		}

		if (resultCnt > 0) {
			// 권한변경이력 저장
			SyAuthorityChangeHistory historyParams = new SyAuthorityChangeHistory();

			historyParams.setAuthNo(authNo);
			historyParams.setChngField("menuNo");
			historyParams.setChngFieldName("권한메뉴");
			historyParams.setChngBeforeFieldValue("-");
			historyParams.setChngAfterFieldValue("-");
			historyParams.setRgsterNo(user.getAdminNo());

			try {
				adminService.insertAuthorityGroupHistory(historyParams);
			} catch (Exception e) {
				log.error("권한변경이력 저장 에러 : {}", e.getMessage());
			}
		}

		return resultCnt;
	}

	/**
	 * @Desc : 관리자 권한그룹 권한메뉴화면 호출
	 * @Method Name : getAuthorityGroupMenuList
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getAuthorityGroupMenuList(SyAuthorityMenu params) throws Exception {
		Map<String, Object> resultData = new HashMap<String, Object>();

		List<SyAuthorityMenu> data = syAuthorityMenuDao.selectAuthorityGroupMenuList(params);
		resultData.put("Total", 1);
		resultData.put("Data", data);

		return resultData;

	}

	/**
	 * @Desc : 관리자 권한변경이력 조회
	 * @Method Name : getAuthorityGroupHistoryList
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SyAuthorityChangeHistory> getAuthorityGroupHistoryList(
			Pageable<SyAuthorityChangeHistory, SyAuthorityChangeHistory> pageable) throws Exception {
		int count = syAuthorityChangeHistoryDao.selectAuthorityGroupHistoryListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(syAuthorityChangeHistoryDao.selectAuthorityGroupHistoryList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 관리자 찾기 검색 Form에 필요한 정보 조회
	 * @Method Name : getAdminFindPopForm
	 * @Date : 2019. 2. 14.
	 * @Author : kiowa
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getAdminFindPopForm() throws Exception {
		Map<String, Object> rtnVal = new HashMap<String, Object>(); // 결과 정보

		// rtnVal.put("siteInfo", siteService.getSiteList());

		return rtnVal;
	}

	/**
	 * @Desc : 메일 템플릿 vo를 리턴한다.
	 * @Method Name : getMailTemplateVo
	 * @Date : 2019. 4. 11.
	 * @Author : Kimyounghyun
	 * @param certificationNumber
	 * @return
	 * @throws Exception
	 */
	private MailTemplateVO getMailTemplateVo(SyAdmin syadmin) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("adminName", syadmin.getAdminName());
		map.put("pswdText", syadmin.getMailSendPswd());
		map.put("loginId", syadmin.getLoginId());
		map.put("abcUrl", Const.SERVICE_DOMAIN_ART_FO);
		map.put("backOfficeUrl", Const.URL_WWW_HTTPS);

		MailTemplateVO mailTemplateVO = new MailTemplateVO();
		mailTemplateVO.setMailTemplateId(syadmin.getMailSendCode());
		mailTemplateVO.setMailTemplateModel(map);
		mailTemplateVO.setReceiverEmail(syadmin.getEmailAddrText());
		mailTemplateVO.setReceiverName(syadmin.getAdminName());
		mailTemplateVO.setRealTime(true);

		return mailTemplateVO;
	}

	/**
	 * @Desc : 슈퍼관리자 목록
	 * @Method Name : getSuperAdminNoList
	 * @Date : 2019. 6. 7.
	 * @Author : 유성민
	 * @return
	 */
	public List<SyAdmin> getSuperAdminNoList() throws Exception {
		return syAdminDao.getSuperAdminNoList();
	}
}
