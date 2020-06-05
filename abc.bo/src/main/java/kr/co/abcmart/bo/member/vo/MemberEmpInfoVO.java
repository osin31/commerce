package kr.co.abcmart.bo.member.vo;

import lombok.Data;

@Data
public class MemberEmpInfoVO {
	private String empNm;
	private String empCd;
	private String jikChaek;
	private String retdt;
	private String entDt;
	private String deptCd;
	private int balance;
}
