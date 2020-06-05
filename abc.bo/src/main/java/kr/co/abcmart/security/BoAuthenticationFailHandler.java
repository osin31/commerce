package kr.co.abcmart.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;

import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.bo.system.service.SystemService;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.security.base.BaseAuthenticationFailHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoAuthenticationFailHandler extends BaseAuthenticationFailHandler {

	@Autowired
	private SystemService systemService;

	@Override
	public void doProcess(Parameter<?> parameter, AuthenticationException exception) {

		if (!UtilsText.equals(exception.getMessage(), CommonCode.DUP_LOGIN_ID)) {
			String id = parameter.getString("username");
			SyAdmin syAdmin = new SyAdmin();
			syAdmin.setLoginId(id);
			SyAdmin userInfo;
			try {
				userInfo = systemService.loadUserByLoginId(syAdmin);
				if (userInfo != null) {
					systemService.setAdminLoginLog(userInfo, parameter, exception.getMessage());
				}
			} catch (Exception e) {
				log.error("BoAuthenticationFailHandler adminLoginLog Error");
			}
		}
	}

}
