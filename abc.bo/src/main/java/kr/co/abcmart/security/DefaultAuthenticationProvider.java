package kr.co.abcmart.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;

import kr.co.abcmart.bo.member.service.MemberService;
import kr.co.abcmart.bo.member.vo.CertificationVO;
import kr.co.abcmart.bo.system.service.SystemService;
import kr.co.abcmart.common.util.UtilsRequest;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.security.password.SHA256KISAPasswordEncoder;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private SystemService systemService;

	@Autowired
	private SessionRegistry sessionRegistry;

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	private MemberService memberService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String serverName = httpServletRequest.getServerName().toLowerCase();
		log.debug("authenticate serverName - {}", serverName);

		if (serverName.startsWith(Const.LOGIN_DOMAIN_BO)) {
			return boLogin(authentication);
		} else {
			return poLogin(authentication);
		}
	}

	/**
	 * @Desc : bo 로그인
	 * @Method Name : boLogin
	 * @Date : 2019. 8. 12.
	 * @Author : Kimyounghyun
	 * @param authentication
	 * @return
	 * @throws AuthenticationException
	 */
	private Authentication boLogin(Authentication authentication) throws AuthenticationException {
		String principal = (String) authentication.getPrincipal();
		String credentials = (String) authentication.getCredentials();
		String forceLoginYn = (String) UtilsRequest.getSession().getAttribute("forceLoginYn");
		UtilsRequest.getSession().removeAttribute("forceLoginYn");
		SHA256KISAPasswordEncoder passwordEncoder = new SHA256KISAPasswordEncoder();

		try {
			log.debug("boLogin principal : {}, credentials : {}", principal, credentials);
			UserDetails userDetail = systemService.loadUserByUsername(principal);
			String passwordEncode = passwordEncoder.encode(credentials, userDetail.getSalt());
			log.debug("boLogin credentials : {}, getSalt : {}", credentials, userDetail.getSalt());
			log.debug("boLogin passwordEncode : {}, getPassword() : {} ", passwordEncode, userDetail.getPassword());

			// 화면에서 입력한 이용자의 비밀번호(평문)와 DB에서 가져온 이용자의 암호화된 비밀번호를 비교한다
			if (!passwordEncoder.matches(passwordEncode, userDetail.getPassword())) {
				// 비밀번호 실패횟수 저장
				log.debug("boLogin 비밀번호 실패횟수 저장");
				systemService.setAdminLoginFailProc(userDetail);
				if (userDetail.getLoginFailCount().intValue() >= 4) {
					throw new BadCredentialsException(CommonCode.LOGIN_FAIL_RSN_PWD5FAIL);
				} else {
					throw new BadCredentialsException(CommonCode.LOGIN_FAIL_RSN_PWD);
				}
			}

			// 중복로그인 체크
			log.debug("boLogin 중복로그인 체크 start");
			if (!UtilsText.equals(forceLoginYn, Const.BOOLEAN_TRUE)) {
				List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
				for (Object oldPrincipal : allPrincipals) {
					if (UtilsText.equals(String.valueOf(oldPrincipal), principal)) {
						throw new BadCredentialsException(CommonCode.DUP_LOGIN_ID);
					}
				}
			}
			log.debug("boLogin 중복로그인 체크 end");

			// password,salt 초기화 시킨다.
			userDetail.eraseCredentials();
			// spring security session에 저장
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, credentials,
					userDetail.getAuthorities());
			token.setDetails(userDetail);
			log.debug("userDetail : {}", userDetail);
			log.debug("isLogin : {}", LoginManager.isLogin());

			return token;

		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}
	}

	/**
	 * @Desc : po 로그인
	 * @Method Name : poLogin
	 * @Date : 2019. 8. 12.
	 * @Author : Kimyounghyun
	 * @param authentication
	 * @return
	 * @throws AuthenticationException
	 */
	private Authentication poLogin(Authentication authentication) throws AuthenticationException {
		String principal = (String) UtilsRequest.getSession().getAttribute("loginId");
		String credentials = (String) UtilsRequest.getSession().getAttribute("password");
		String forceLoginYn = (String) UtilsRequest.getSession().getAttribute("forceLoginYn");
		UtilsRequest.getSession().removeAttribute("loginId");
		UtilsRequest.getSession().removeAttribute("password");
		UtilsRequest.getSession().removeAttribute("forceLoginYn");
		SHA256KISAPasswordEncoder passwordEncoder = new SHA256KISAPasswordEncoder();

		try {
			log.debug("principal : {}, credentials : {}", principal, credentials);
			UserDetails userDetail = systemService.loadUserByUsername(principal);
			String passwordEncode = passwordEncoder.encode(credentials, userDetail.getSalt());
			log.debug("credentials : {}, getSalt : {}", credentials, userDetail.getSalt());
			log.debug("passwordEncode : {}, getPassword() : {} ", passwordEncode, userDetail.getPassword());

			// 인증번호 검증
			try {
				String crtfcNoSendInfo = userDetail.getHdphnNoText();
				String crtfcNoText = (String) UtilsRequest.getSession().getAttribute("crtfcNoText"); // 인증번호
				UtilsRequest.getSession().removeAttribute("crtfcNoText");

				CertificationVO certificationVO = new CertificationVO();
				certificationVO.setCrtfcNoSendInfo(crtfcNoSendInfo); // 전화번호
				certificationVO.setCrtfcNoText(crtfcNoText); // 인증번호
				certificationVO.setForceLoginYn(forceLoginYn);

				memberService.setValidateCertificationNumber(certificationVO);
			} catch (Exception e) {
				throw new BadCredentialsException(CommonCode.LOGIN_FAIL_RSN_CRTFCNO);
			}

			// 화면에서 입력한 이용자의 비밀번호(평문)와 DB에서 가져온 이용자의 암호화된 비밀번호를 비교한다
			if (!passwordEncoder.matches(passwordEncode, userDetail.getPassword())) {
				// 비밀번호 실패횟수 저장
				systemService.setAdminLoginFailProc(userDetail);
				if (userDetail.getLoginFailCount().intValue() >= 4) {
					throw new BadCredentialsException(CommonCode.LOGIN_FAIL_RSN_PWD5FAIL);
				} else {
					throw new BadCredentialsException(CommonCode.LOGIN_FAIL_RSN_PWD);
				}
			}

			// 중복로그인 체크
			if (!UtilsText.equals(forceLoginYn, Const.BOOLEAN_TRUE)) {
				List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
				for (Object oldPrincipal : allPrincipals) {
					if (UtilsText.equals(String.valueOf(oldPrincipal), principal)) {
						throw new BadCredentialsException(CommonCode.DUP_LOGIN_ID);
					}
				}
			}

			// password,salt 초기화 시킨다.
			userDetail.eraseCredentials();
			// spring security session에 저장
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, credentials,
					userDetail.getAuthorities());
			token.setDetails(userDetail);
			log.debug("userDetail : {}", userDetail);
			log.debug("isLogin : {}", LoginManager.isLogin());

			return token;

		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(UsernamePasswordAuthenticationToken.class);
	}

	public static void main(String[] args) {

		String salt = "yx0YF422meiv8Lkq5T+eqbSE71+wiD/BsO2jRBFKdMI=";

		SHA256KISAPasswordEncoder encoder = new SHA256KISAPasswordEncoder();
		System.out.println(salt);
		System.out.println(encoder.encode("q1w2e34t5", salt));
	}

}
