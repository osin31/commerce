package kr.co.abcmart.security.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import kr.co.abcmart.common.message.Message;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.util.UtilsRequest;
import kr.co.abcmart.util.UtilsResponse;
import kr.co.abcmart.zconfiguration.exception.RedirectException;

public abstract class BaseAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler
		implements DefaultAuthenticationHandler, Fail {

	private String failMessage = "";

	private boolean redirect;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		Parameter<?> parameter = new Parameter<>(request, response);

		doProcess(parameter, exception);

		boolean isWrite = (UtilsRequest.isAjax() || UtilsRequest.isJson(request));

		if (isWrite) {
			switch (exception.getMessage()) {
			// 비밀번호실패
			case CommonCode.LOGIN_FAIL_RSN_PWD:
				failMessage = Message.getMessage("member.msg.login.fail");
				break;
			// 장기미사용자
			case CommonCode.LOGIN_FAIL_RSN_LONGUNUSED:
				failMessage = Message.getMessage("member.msg.login.longUnused");
				break;
			// 접근불가아이피
			case CommonCode.LOGIN_FAIL_RSN_UNACCESSIP:
				failMessage = Message.getMessage("member.msg.login.unAccessIp");
				break;
			// 사용불가계정
			case CommonCode.LOGIN_FAIL_RSN_USED:
				failMessage = Message.getMessage("member.msg.login.unused");
				break;
			// 비밀번호5번연속 실패
			case CommonCode.LOGIN_FAIL_RSN_PWD5FAIL:
				failMessage = Message.getMessage("member.msg.login.loginFail5Count");
				break;
			// 비밀번호변경일시 초과
			case CommonCode.LOGIN_FAIL_RSN_PWDCHNG_PERIODOVER:
				failMessage = Message.getMessage("member.msg.login.pswdChngPeriodOver");
				break;
			// 미인증 업체관리자
			case CommonCode.LOGIN_FAIL_RSN_VENDORNO:
				failMessage = Message.getMessage("member.msg.login.unVendorNo");
				break;
			case CommonCode.DUP_LOGIN_ID:
				failMessage = CommonCode.DUP_LOGIN_ID;
				break;
			case CommonCode.LOGIN_FAIL_RSN_CRTFCNO:
				failMessage = CommonCode.LOGIN_FAIL_RSN_CRTFCNO;
				break;
			default:
				failMessage = Message.getMessage("member.msg.login.fail");
				break;
			}

			writeResponse(parameter, Auth.F, exception, failMessage);
		} else if (!redirect) {
			super.onAuthenticationFailure(request, response, exception);
		}
	}

	public void redirect(String redirectUrl) throws RedirectException {

		this.redirect = true;

		try {
			getRedirectStrategy().sendRedirect(UtilsRequest.getRequest(), UtilsResponse.getResponse(), redirectUrl);
		} catch (Exception e) {
			throw new RedirectException(e.getMessage());
		}
	}

}
