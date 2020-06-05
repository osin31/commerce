package kr.co.abcmart.security;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import kr.co.abcmart.bo.cmm.model.master.CmDaysCondtn;
import kr.co.abcmart.bo.cmm.service.DaysCondtnService;
import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.bo.system.model.master.SyAdminAuthority;
import kr.co.abcmart.bo.system.repository.master.SyAdminDao;
import kr.co.abcmart.bo.system.service.SystemService;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.security.base.BaseAuthenticationSuccessHandler;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoAuthenticationSuccessHandler extends BaseAuthenticationSuccessHandler {

	@Autowired
	private SystemService systemService;

	@Autowired
	private DaysCondtnService daysCondtnService;

	@Autowired
	private SyAdminDao syAdminDao;

	@Override
	public void doProcess(Parameter<?> parameter, Authentication authentication) {

		log.debug(SUCCESS_MESSAGE);

		try {
			// 관리자정보 UPDATE(최종로그인일시, 최종로그인ip,비밀번호실패횟수 초기화)
			SyAdmin syAdmin = new SyAdmin();
			this.updateAdminInfo(syAdmin);

			// session 정보추가
			this.setUserDetails(syAdmin);

			// 로그인 이력 저장
			try {
				systemService.setAdminLoginLog(syAdmin, parameter, "");
			} catch (Exception e) {
				log.error(e.getMessage());
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	private void updateAdminInfo(SyAdmin syAdmin) throws Exception {
		log.debug("updateAdminInfo START");
		String ipAddress = systemService.getIpAddress();
		syAdmin.setAdminNo(LoginManager.getUserDetails().getAdminNo());
		syAdmin.setModerNo(LoginManager.getUserDetails().getAdminNo());
		syAdmin.setLastLoginIpText(ipAddress);
		syAdmin.setLastLoginDtm(new Timestamp(new Date().getTime()));
		syAdmin.setLoginFailInit(Const.BOOLEAN_TRUE);
		syAdmin.setPswdDscordYn(Const.BOOLEAN_FALSE);
		syAdminDao.updateAdmin(syAdmin);
		log.debug("updateAdminInfo END");
	}

	private void setUserDetails(SyAdmin syAdmin) throws Exception {
		log.debug("setUserDetails START");

		SyAdmin adminInfo = systemService.loadUserByAdminNo(syAdmin);
		log.debug("setUserDetails adminInfo = {} ", adminInfo);
		UserDetails userDetails = LoginManager.getUserDetails();

		userDetails.setVndrNo(String.valueOf(adminInfo.getVndrNo()));
		userDetails.setVndrMngrNo(adminInfo.getVndrMngrNo() == null ? 0 : adminInfo.getVndrMngrNo());
		userDetails.setStoreNo(String.valueOf(adminInfo.getStoreNo()));
		userDetails.setTelNoText(String.valueOf(adminInfo.getTelNoText()));
		userDetails.setHdphnNoText(String.valueOf(adminInfo.getHdphnNoText()));
		userDetails.setEmailAddrText(String.valueOf(adminInfo.getEmailAddrText()));
		userDetails.setMemberInfoMgmtYn(String.valueOf(adminInfo.getMemberInfoMgmtYn()));
		userDetails.setLastLoginDtm(adminInfo.getLastLoginDtm());
		userDetails.setLastLoginIpText(String.valueOf(adminInfo.getLastLoginIpText()));
		userDetails.setPswdInitYn(String.valueOf(adminInfo.getPswdInitYn()));
		userDetails.setLongUnusedYn(String.valueOf(adminInfo.getLongUnusedYn()));
		userDetails.setPswdChngDtm(adminInfo.getPswdChngDtm());
		log.debug("setUserDetails adminInfo end");

		CmDaysCondtn cmDaysCondtn = daysCondtnService.getDaysCondtn("ADMIN_PSWD_RENEW_CONDITION");
		String condtnTermValue = UtilsText.defaultString(cmDaysCondtn.getCondtnTermValue(), "14");
		userDetails.setCondtnTermValue(condtnTermValue);
		log.debug("setUserDetails setCondtnTermValue end");

		String inqryGbnType = "";
		String upAuthNo = "";
		Optional<SyAdminAuthority> optional = userDetails.getAdminAuthorities().stream().findFirst();
		if (optional.isPresent()) {
			upAuthNo = optional.get().getUpAuthNo();
		}
		userDetails.setUpAuthNo(upAuthNo);

		if (UtilsText.equals(upAuthNo, Const.ROLE_VENDER_GROUP)) {
			inqryGbnType = Const.INQRY_GBN_TYPE_VENDOR;
			userDetails.setAuthApplySystemType(Const.AUTH_APPLY_SYSTEM_TYPE_PO);
		} else {

			List<SyAdminAuthority> adminAuthList = userDetails.getAdminAuthorities();
			if (adminAuthList.stream().anyMatch(x -> UtilsText.equals(x.getAuthNo(), Const.ROLE_CALL_CENTER))) {
				inqryGbnType = Const.INQRY_GBN_TYPE_CALLCENTER;
			} else {
				inqryGbnType = Const.INQRY_GBN_TYPE_ADMIN;
			}

			userDetails.setAuthApplySystemType(Const.AUTH_APPLY_SYSTEM_TYPE_BO);
		}
		userDetails.setInqryGbnType(inqryGbnType);

		LoginManager.setUserDetails(userDetails);

		log.debug("userDetails : {}", UtilsText.stringify(userDetails));
		log.debug("setUserDetails END");
	}

}
