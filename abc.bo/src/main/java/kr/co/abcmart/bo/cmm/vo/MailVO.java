/**
 * 
 */
package kr.co.abcmart.bo.cmm.vo;

import lombok.Data;

/**
 * @Desc :
 * @FileName : MailVO.java
 * @Project : abc.bo
 * @Date : 2019. 3. 25.
 * @Author : Kimyounghyun
 */
@Data
public class MailVO {

	// 사이트번호
	private String siteNo;
	// 메일 템플릿 고유번호
	private Integer emailTmplSeq;

	// 메일 제목
	private String title;
	// 메일 내용
	private String content;

	// 발신자 회원번호
	private String senderMemberNo;
	// 발신자 메일주소
	private String senderEmail;
	// 발신자 명
	private String senderName;

	// 수신자 회원번호
	private String receiverMemberNo;
	// 수신자 메일주소
	private String receiverEmail;
	// 수신자 명
	private String receiverName;

}
