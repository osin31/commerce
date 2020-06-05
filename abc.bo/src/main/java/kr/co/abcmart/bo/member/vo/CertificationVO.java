package kr.co.abcmart.bo.member.vo;

import lombok.Data;

@Data
public class CertificationVO {
	private String memberNo;
	private String memberName;
	private String loginId;
	private String crtfcPathCode;
	private String crtfcNoSendInfo; // 전화번호
	private String crtfcNoText; // 인증번호
	private int validTime;
	private String crtfcSuccessYn;
	private String certificationNumber; // 인증횟수
	private String crtfcYn; // 인증횟수 제한 여부(Y : 제한없음 , N : 제한)
	private String forceLoginYn;
}
