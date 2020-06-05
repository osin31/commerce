package kr.co.abcmart.security.base;

public enum Auth {

	/**
	 * 인증 성공 :  onAuthenticationSuccess 호출시
	 */
	S,	
	
	/**
	 * 인증 실패 :  onAuthenticationFailure 호출시
	 */
	F,	
	
	/**
	 * 접근 금지 :  handle 호출시
	 */
	D, 	
	
	/**
	 * 인증 필요 :  commence 호출시
	 */
	U;
	

	
}
