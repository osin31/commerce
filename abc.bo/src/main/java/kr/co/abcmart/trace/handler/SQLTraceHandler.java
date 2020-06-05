package kr.co.abcmart.trace.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.co.abcmart.bo.member.service.MemberService;
import kr.co.abcmart.trace.SQLTrace;
import kr.co.abcmart.user.UserDetails;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @Desc : Controller 메소드 실행 시 Sql 호출 추적.
 * @FileName : SQLTraceHandler.java
 * @Project : abc.bo
 * @Date : 2019. 3. 13.
 * @Author : 장진철 zerocooldog@zen9.co.kr
 */
@Data
@ToString
@Slf4j
@Component
public class SQLTraceHandler implements TraceHandler {

	@Autowired
	MemberService memberService;

	/***
	 * 호출 받은 값을 DB에 저장한다.
	 */
	@Override
	public synchronized void call(UserDetails userDetails, SQLTrace sqlTrace) {

		boolean isLogin = isLogin(userDetails);

		try {
			if (isLogin) {
				// 로그인 회원
				sqlTrace.setAdminNo(userDetails.getAdminNo());
				sqlTrace.setAuthNo(userDetails.getUpAuthNo());
			}

			// 로그인이 안되어 있어도 로그는 남겨야 함
			// [ex:로그인 페이지 요청 또는 세션 만료로 인한 로그인 페이지 포워딩 시에 로그를 남기므로]
			memberService.setMenuAccessLogging(sqlTrace);
		} catch (Exception e) {
			e.printStackTrace();
		}

//		log.debug("memberService : {}", memberService);
//		log.debug("SQLTrace.toString() : {}", sqlTrace);
	}
}
