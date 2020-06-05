package kr.co.abcmart.user;

import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsRequest;
import lombok.extern.slf4j.Slf4j;

/***
 * 로그인 처리를 한다.
 * 
 * @author user
 *
 */
@Slf4j
public class LoginManager {

	/**
	 * 사용자 세션 정보를 저장한다.
	 * 
	 * @param request httpServletRequest
	 * @return UserDetails
	 */
	public static void setUserDetails(UserDetails userDetails) {

		Authentication authentication = getAuthentication();

		if (authentication != null) {
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(null, null);
			newAuth.setDetails(userDetails);
		}

		log.debug("LoginManager.setUserDetails Authentication value : {} ", authentication);

	}

	/**
	 * 사용자 세션 정보를 가져온다.
	 * 
	 * @param request httpServletRequest
	 * @return UserDetails
	 */
	public static UserDetails getUserDetails() {

		UserDetails userDetails = (UserDetails) getDetails();

		if (userDetails == null || !(userDetails instanceof UserDetails)) {
			return new UserDetails(null);
		}

		return userDetails;
	}

	/**
	 * 로그인 여부
	 * 
	 * @return true or false
	 */
	public static boolean isLogin() {
		return getAuthentication() != null;
	}

	/**
	 * @Desc : 개인정보취급여부
	 * @Method Name : isPersonalManager
	 * @Date : 2019. 3. 7.
	 * @Author : Kimyounghyun
	 * @return
	 */
	public static boolean isPersonalInfoManager() {
		return UtilsText.equals(getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_TRUE);
	}

	/**
	 * 세션 초기화 . 로그아웃시 사용
	 */

	public static void invalidate() {

		HttpSession session = UtilsRequest.getSession();

		// 사용자 정의 세션 null 처리

		// session.setAttribute(BaseConst._USER_SESSION_KEY, null);

		session.invalidate();
	}

	/**
	 * Authentication 조회
	 * 
	 * @return Authentication
	 */
	public static Authentication getAuthentication() {
		SecurityContext context = null;
		try {
			context = SecurityContextHolder.getContext();
		} catch (Exception e) {

		}
		if (context == null)
			return null;

		Authentication authentication = context.getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken)
			return null;

		return authentication;
	}

	public static Object getDetails() {
		Authentication authentication = getAuthentication();
		if (authentication == null)
			return null;

		return authentication.getDetails();
	}
}
