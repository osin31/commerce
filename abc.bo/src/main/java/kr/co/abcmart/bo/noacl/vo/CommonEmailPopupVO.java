package kr.co.abcmart.bo.noacl.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class CommonEmailPopupVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 사이트 번호
	 */
	private String siteNo;

	/**
	 * 수신자 메일 주소
	 */
	private String rcvrEmailAddrText;

	/**
	 * 수신자명
	 */
	private String rcvrName;

	/**
	 * 회원번호
	 */
	private String rcvrMemberNo;

}
