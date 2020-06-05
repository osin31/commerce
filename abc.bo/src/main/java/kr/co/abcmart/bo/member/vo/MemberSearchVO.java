package kr.co.abcmart.bo.member.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class MemberSearchVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String memberNo;

	private String loginId;

	private String memberName;

	private String safeKey;

	private String safeKeySeq;

	private String[] memberTypeCodes;

	private String[] mbshpGradeCodes;

	private String empYn;
}
