package kr.co.abcmart.bo.system.service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import kr.co.abcmart.bo.cmm.model.master.CmDaysCondtn;
import kr.co.abcmart.bo.cmm.service.DaysCondtnService;
import kr.co.abcmart.bo.cmm.service.MsgTemplateService;
import kr.co.abcmart.bo.cmm.vo.MessageVO;
import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.bo.system.model.master.SyAdminAccessIp;
import kr.co.abcmart.bo.system.model.master.SyAdminAuthority;
import kr.co.abcmart.bo.system.model.master.SyAdminChangeHistory;
import kr.co.abcmart.bo.system.model.master.SyAdminUseHistory;
import kr.co.abcmart.bo.system.model.master.SyAuthorityMenu;
import kr.co.abcmart.bo.system.repository.master.SyAdminAccessIpDao;
import kr.co.abcmart.bo.system.repository.master.SyAdminDao;
import kr.co.abcmart.bo.system.repository.master.SyAdminUseHistoryDao;
import kr.co.abcmart.bo.system.repository.master.SyAuthorityDao;
import kr.co.abcmart.bo.system.repository.master.SyAuthorityMenuDao;
import kr.co.abcmart.bo.system.vo.HistoryFieldsName;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsCrypt;
import kr.co.abcmart.common.util.UtilsRequest;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.security.acl.AuthorizedUrl;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsArray;
import kr.co.abcmart.util.UtilsDate;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SystemService implements UserDetailsService {

	@Autowired
	private SystemService systemService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private DaysCondtnService daysCondtnService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private MsgTemplateService msgTemplateService;

	@Autowired
	private SyAdminDao syAdminDao;

	@Autowired
	private SyAuthorityDao syAuthorityDao;

	@Autowired
	private SyAuthorityMenuDao syAuthorityMenuDao;

	@Autowired
	private SyAdminAccessIpDao syAdminAccessIpDao;

	@Autowired
	private SyAdminUseHistoryDao syAdminUseHistoryDao;

	/**
	 *
	 * @Desc :사용자정보(pk key 이용)
	 * @Method Name : loadUserByAdminNo
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param syAdmin
	 * @return
	 * @throws Exception
	 */
	public SyAdmin loadUserByAdminNo(SyAdmin syAdmin) throws Exception {
		return syAdminDao.selectByPrimaryKey(syAdmin);
	}

	/**
	 *
	 * @Desc :로그인아이디로 사용자정보
	 * @Method Name : loadUserByLoginId
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param syAdmin
	 * @return
	 * @throws Exception
	 */
	public SyAdmin loadUserByLoginId(SyAdmin syAdmin) throws Exception {
		return syAdminDao.selectAdminAndAuthorityByLoginId(syAdmin);
	}

	/***
	 * 사용자(admin) 정보를 불러온다.
	 */
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

		UserDetails userDetails = null;
		try {

			SyAdmin syAdmin = new SyAdmin();
			syAdmin.setLoginId(id);
			SyAdmin result = syAdminDao.selectAdminAndAuthorityByLoginId(syAdmin);
			log.debug("loadUserByUsername result = {}", result);
			if (result == null) {
				throw new UsernameNotFoundException("usename not found");
			}

			this.setLoginValidProc(result);

			userDetails = new UserDetails(result);

		} catch (Exception e) {
			log.error("LoginValidProc : {}", e.getMessage());
			throw new UsernameNotFoundException(e.getMessage());
		}

		return userDetails;
	}

	/**
	 *
	 * @Desc :권한 마다 특정 페이지에 접근 할 수 있도록, URL별 접근 권한 설정 정보를 불러온다.
	 * @Method Name : getAuthorizedUrls
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<AuthorizedUrl> getAuthorizedUrls(String roleId) throws Exception {

		List<SyAuthorityMenu> list = systemService.getAuthorizedUrlsList(roleId);
		List<AuthorizedUrl> authorizedUrlList = new ArrayList<>();

		for (SyAuthorityMenu syAuthorityMenu : list) {

			authorizedUrlList
					.add(new AuthorizedUrl(syAuthorityMenu.getAuthApplySystemType(), syAuthorityMenu.getRsrcUrl(),
							syAuthorityMenu.getRsrcType(), syAuthorityMenu.getSortSeq(), syAuthorityMenu.getAuthNo()));
		}

		return authorizedUrlList;
	}

	public List<SyAuthorityMenu> getAuthorizedUrlsList(String roleId) throws Exception {
		return syAuthorityMenuDao.getAuthorizedUrls(roleId);
	}

	/**
	 *
	 * @Desc :권한 전체 목록을 가져온다.
	 * @Method Name : getAllRoles
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @return
	 * @throws Exception
	 */
	public List<String> getAllRoles() throws Exception {
		return syAuthorityDao.getAllRoles();
	}

	/**
	 *
	 * @Desc :로그인 validation
	 * @Method Name : setLoginValidProc
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param result
	 * @throws Exception
	 */
	public void setLoginValidProc(SyAdmin result) throws Exception {

		log.debug("setLoginValidProc start");
		// 개발편의를 위해 sysop, sysdev 계정의 체크 안함
		if (UtilsArray.contains(Const.ADMIN_ID, result.getLoginId())) {
			return;
		}

		// 관리자 계정 사용여부 체크
		if (UtilsText.equals(result.getUseYn(), Const.BOOLEAN_FALSE)) {
			throw new Exception(CommonCode.LOGIN_FAIL_RSN_USED);
		}

		// 장기미사용자체크(장기미사용자 배치로 관리, 30일 이상 로그인 안한 관리자)
		if (UtilsText.equals(result.getLongUnusedYn(), Const.BOOLEAN_TRUE)) {
			throw new Exception(CommonCode.LOGIN_FAIL_RSN_LONGUNUSED);
		}

		// 로그인실패횟수(연속5회실패시)
		log.debug("result.getLoginFailCount().intValue() : {}", result.getLoginFailCount().intValue());
		if (result.getLoginFailCount().intValue() >= 5) {
			throw new Exception(CommonCode.LOGIN_FAIL_RSN_PWD5FAIL);
		}

		// 비밀번호변경일시 14일초과시
		// 조건 날짜관리에서 비밀번호 변경기한 가져와서 비교 기본값:14일
		LocalDateTime pswdChngTime = result.getPswdChngDtm().toLocalDateTime();
		CmDaysCondtn cmDaysCondtn = daysCondtnService.getDaysCondtn("ADMIN_PSWD_RENEW_CONDITION");
		String condtnTermValue = UtilsText.defaultString(cmDaysCondtn.getCondtnTermValue(), "14");

		long pswdChngPeriod = ChronoUnit.DAYS.between(pswdChngTime.toLocalDate(), LocalDate.now());

		log.debug("pswdChngPeriod : {}", pswdChngPeriod);
		if (pswdChngPeriod > Integer.parseInt(condtnTermValue)) {
			// 로그인은 가능 비밀번호 변경팝업 띄움
//			throw new Exception(CommonCode.LOGIN_FAIL_RSN_PWDCHNG_PERIODOVER);
		}

		SyAdminAuthority syAdminAuthority = result.getAdminAuthorities().stream().findFirst().orElse(null);
		log.debug("SyAdminAuthority syAdminAuthority : {}", syAdminAuthority);
		String upAuthNo = "";
		String authNo = "";
		if (syAdminAuthority != null) {
			upAuthNo = syAdminAuthority.getUpAuthNo();
			authNo = syAdminAuthority.getAuthNo();
		}

		// AConnect 관리자 로그인 불가 처리
		if (UtilsText.equals(authNo, Const.ACONNECT_AUTH_NO)) {
			throw new Exception("ACONNECT 관리자 로그인 불가 처리");
		}

		log.debug("SyAdminAuthority upAuthNo : {}", upAuthNo);

		if (UtilsText.equals(upAuthNo, Const.ROLE_VENDER_GROUP)) {
			log.debug("********** ROLE_VENDER_GROUP ************");
			// 접근 도메인 체크
			if (UtilsText.equals(Const.LOGIN_DOMAIN_BO, UtilsRequest.getRequest().getServerName())) {
				throw new Exception(CommonCode.LOGIN_FAIL_RSN_PWD);
			}

			// 업체번호 체크
			if (UtilsText.isEmpty(result.getVndrNo())) {
				throw new Exception(CommonCode.LOGIN_FAIL_RSN_VENDORNO);
			}
			// 업체 거래상태 체크
			String vndrStatCode = vendorService.getVndrStatCode(result.getVndrNo());
			if (UtilsText.equals(vndrStatCode, CommonCode.VNDR_STAT_CODE_END_TRANSIT)) {
				throw new Exception(CommonCode.LOGIN_FAIL_RSN_VENDORNO);
			}

		} else if (!UtilsText.equals(upAuthNo, Const.ROLE_VENDER_GROUP)) {
			log.debug("********** ROLE_SYSTEM_GROUP ************");
			// 허용되지 않은 아이피 접근 시 문자가 수시로 발송되어 일부(로컬, 개발)에서는 해당 로직을 타지 않도록 설정(09/11)
			if (UtilsText.equals(Const.IS_CHECKED_ACCESS_IP, Const.BOOLEAN_TRUE)) {
				// 접근허용아이피체크
				List<SyAdminAccessIp> accessIpData = syAdminAccessIpDao.selectAdminAccessIp(result.getAdminNo());
				String ipAddress = this.getIpAddress();
				String localIp = "127.0.0.1";

				boolean matchAccessIp = false;
				log.debug("ROLE_SYSTEM_GROUP loginId ={}, ipAddress = {}", result.getLoginId(), ipAddress);
				for (SyAdminAccessIp accessIp : accessIpData) {
					if (UtilsText.equals(accessIp.getAccessIpText(), ipAddress)
							|| UtilsText.equals(accessIp.getAccessIpText(), localIp)) {
						matchAccessIp = true;
						break;
					}
				}
				if (!matchAccessIp) {
					// 허용되지 않은 아이피로 관리자가 로그인하는 경우 담당자(슈퍼관리자)에게 SMS발송
					unMatchAccessIpSendSms(result.getLoginId());
					throw new Exception(CommonCode.LOGIN_FAIL_RSN_UNACCESSIP);
				}
			}
		}
	}

	/**
	 * @Desc :허용되지 않은 아이피로 관리자가 로그인하는 경우 담당자(슈퍼관리자)에게 SMS발송
	 * @Method Name : unMatchAccessIpSendSmsNoTrx
	 * @Date : 2019. 6. 7.
	 * @Author : 유성민
	 */
	public void unMatchAccessIpSendSms(String loginId) {
		try {
			List<SyAdmin> adminList = adminService.getSuperAdminNoList();
			List<SyAdmin> adminDistinctList = adminList.stream()
					.filter(UtilsArray.distinctByKey(SyAdmin::getHdphnNoText)).collect(Collectors.toList());

			MessageVO messageVO = null;
			for (SyAdmin admin : adminDistinctList) {
				messageVO = new MessageVO();
				messageVO.setSendTelNoText(Const.SYS_SENDER_MESSAGE_NUMBER);
				messageVO.setMesgContText("[ABC마트] (ID: " + loginId + ") 허용되지 않은 아이피로 로그인이 발생했습니다");
				messageVO.setMesgTitleText("[ABC마트]");
				messageVO.setRecvTelNoText(admin.getHdphnNoText());
				// log.debug("messageVO = {}", messageVO);
				if (UtilsText.getByteLength(messageVO.getMesgContText()) > 80) {
					msgTemplateService.setSendRealTimeLmsNoTrx(messageVO);
				} else {
					msgTemplateService.setSendRealTimeSmsNoTrx(messageVO);
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * @Desc : Client IP를 가져오기
	 * @Method Name : getIpAddress
	 * @Date : 2019. 3. 13.
	 * @Author : 유성민
	 * @return
	 */
	public String getIpAddress() {
		for (String header : Const.HEADER_NAMES_FOR_CLIENT_IP) {
			String ipAddress = UtilsRequest.getRequest().getHeader(header);
			if (UtilsText.isNotEmpty(ipAddress)) {
				return UtilsText.trim(UtilsText.split(ipAddress, ",")[0]);
			}
		}
		return UtilsRequest.getRequest().getRemoteAddr();
	}

	/**
	 *
	 * @Desc :사용자정보 update
	 * @Method Name : updateAdminNoTrx
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param syAdmin
	 * @throws Exception
	 */
	public void updateAdmin(SyAdmin syAdmin) throws Exception {
		syAdminDao.updateAdmin(syAdmin);
	}

	/**
	 *
	 * @Desc :관리자사용이력 저장
	 * @Method Name : insertAdminUseHistoryNoTrx
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param historyParam
	 * @throws Exception
	 */
	public void insertAdminUseHistory(SyAdminUseHistory historyParam) throws Exception {
		syAdminUseHistoryDao.insertAdminUseHistory(historyParam);
	}

	/**
	 * @Desc : 로그인 이력 저장
	 * @Method Name : setAdminLoginLog
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param userInfo
	 * @param parameter
	 * @param code
	 */
	public void setAdminLoginLog(SyAdmin userInfo, Parameter<?> parameter, String code) {

		try {
			String ipAddress = this.getIpAddress();
			String succesYn = Const.BOOLEAN_FALSE;
			if (UtilsText.isEmpty(code)) {
				succesYn = Const.BOOLEAN_TRUE;
			}

			SyAdminUseHistory historyParam = new SyAdminUseHistory();
			historyParam.setAdminNo(userInfo.getAdminNo());
			historyParam.setUseHistClassCode("10000");
			historyParam.setAccessUrl(parameter.getRequest().getRequestURI());
			historyParam.setUseContText("관리자로그인");
			historyParam.setAccessIpText(ipAddress);
			historyParam.setSessionId(parameter.getRequest().getSession().getId());
			historyParam.setSuccessYn(succesYn);
			historyParam.setLoginFailRsnCode(code);
			historyParam.setRgsterNo(userInfo.getAdminNo());
			systemService.insertAdminUseHistory(historyParam);
		} catch (Exception e) {

		}
	}

	/**
	 *
	 * @Desc : 비밀번호 변경
	 * @Method Name : updatePassword
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updatePassword(SyAdmin params) throws Exception {
		Map<String, Object> result = new HashMap<>();

		// 세션정보
		UserDetails user = LoginManager.getUserDetails();
		// 암호화 SaltKey를 셋팅하기 위해 등록되어 있는 데이터 조회
		SyAdmin oldData = syAdminDao.selectAdminDetailInfo(params.getAdminNo());
		// 파라메터 세팅
		params.setPswdText(UtilsCrypt.sha256(params.getPswdText(), oldData.getPswdSaltText()));

		params.setPswdInitYn(Const.BOOLEAN_FALSE);
		params.setPswdChangeYn(Const.BOOLEAN_TRUE);
		params.setModerNo(user.getAdminNo());

		int resultCnt = syAdminDao.updateAdmin(params);
		if (resultCnt > 0) {
			result.put("result", Const.BOOLEAN_TRUE);
			user.setPswdInitYn(Const.BOOLEAN_FALSE);
			user.setPswdChngDtm(UtilsDate.getSqlTimeStamp());
			LoginManager.setUserDetails(user);
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
		}
		return result;
	}

	/**
	 * 비밀번호 실패횟수 저장
	 *
	 * @param userDetail
	 */
	public void setAdminLoginFailProc(UserDetails userDetail) {
		SyAdmin syAdmin = new SyAdmin();
		syAdmin.setAdminNo(userDetail.getAdminNo());
		syAdmin.setModerNo(userDetail.getAdminNo());
		syAdmin.setLoginFailIncrease(Const.BOOLEAN_TRUE);
		if (userDetail.getLoginFailCount().intValue() >= 4) {
			syAdmin.setPswdDscordYn(Const.BOOLEAN_TRUE);
		}
		try {
			systemService.updateAdmin(syAdmin);
		} catch (Exception e) {

		}
	}
}
