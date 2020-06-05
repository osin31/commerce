package kr.co.abcmart.bo.noacl.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class TextMsgVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 사이트 번호
	 */
	private String siteNo;

	/**
	 * 수신자 전화번호
	 */
	private String recvTelNoText;

	/**
	 * 수신자명
	 */
	private String rcvrName;

	/**
	 * 회원번호
	 */
	private String memberNo;

}
