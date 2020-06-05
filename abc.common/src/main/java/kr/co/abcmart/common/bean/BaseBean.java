package kr.co.abcmart.common.bean;

import java.io.Serializable;

public class BaseBean implements Bean, Serializable {

	private String userId;

	private Integer pageNum;

	private Integer rowsPerPage;

	private String sort;

	// FO 회원 번호
	private String memberNo;

	// 회원 유형 코드 2020-01-09 추가
	private String memberTypeCode;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getPageNum() {
		return (pageNum != null ? pageNum : 1);
	}

	public Integer getRowsPerPage() {
		return (rowsPerPage != null ? rowsPerPage : 10);
	}

	public String getSort() {
		return sort;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public void setRowsPerPage(Integer rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberTypeCode() {
		return memberTypeCode;
	}

	public void setMemberTypeCode(String memberTypeCode) {
		this.memberTypeCode = memberTypeCode;
	}

}
