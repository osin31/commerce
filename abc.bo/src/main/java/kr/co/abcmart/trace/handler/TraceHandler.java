package kr.co.abcmart.trace.handler;

import kr.co.abcmart.trace.SQLTrace;
import kr.co.abcmart.user.UserDetails;

public interface TraceHandler {

	public void call(UserDetails userDetails, SQLTrace sqlTrace);

	default boolean isLogin(UserDetails userDetails) {
		return userDetails != null && userDetails.isLogin();
	}
}
