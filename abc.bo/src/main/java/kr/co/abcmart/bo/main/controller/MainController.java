package kr.co.abcmart.bo.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController extends BaseController {

	@RequestMapping("")
	public String main(Parameter<?> parameter) throws Exception {
		UserDetails userDetails = LoginManager.getUserDetails();

		if (UtilsText.equals(userDetails.getAuthApplySystemType(), Const.AUTH_APPLY_SYSTEM_TYPE_BO)) {
			return UtilsText.concat("redirect:", "/bo/dashboard");
		} else if (UtilsText.equals(userDetails.getAuthApplySystemType(), Const.AUTH_APPLY_SYSTEM_TYPE_PO)) {
			return UtilsText.concat("redirect:", "/po/dashboard");
		} else {
			throw new Exception("MainController error");
		}
	}
}