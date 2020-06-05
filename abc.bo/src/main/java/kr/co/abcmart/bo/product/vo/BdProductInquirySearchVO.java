package kr.co.abcmart.bo.product.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품문의(Q&A) 검색 조건
 * @FileName : BdProductInquirySearchVO.java
 * @Project : abc.bo
 * @Date : 2019. 4. 9.
 * @Author : hsjhsj19
 */
@Slf4j
@Data
public class BdProductInquirySearchVO implements Serializable, Validator {

	/** 검색어. 구분 */
	private String searchKeywordType;

	/** 검색어. 키워드 */
	private String searchKeyword;

	/** 상품코드. 구분 */
	private String prdtCodeType;

	/** 상품코드. 상품코드목록 */
	private String prdtCodeKeyword;

	/** 상품코드. 상품번호목록 */
	private List<String> prdtCodeList;

	/** 회원. 구분 */
	private String searchMemberType;

	/** 회원. 키워드 */
	private String searchMember;

	/** 관리자. 구분 */
	private String searchAdminType;

	/** 관리자. 키워드 */
	private String searchAdmin;

	/** 답변여부 */
	private String aswrStatCode;

	/** 기간 */
	private String periodType;

	/** 시작일 */
	private String periodStartDate;

	/** 종료일 */
	private String periodEndDate;

	/** 브랜드 */
	private String brandNo;

	/** 브랜드이름 */
	private String brandName;

	/** 브랜드영문이름 */
	private String brandEnName;

	/** 자사 / 입점 구분 */
	private String mmnyPrdtYn;

	/** 문의유형 */
	private String cnslTypeCode;

	/** 문의구분 */
	private String cnslTypeDtlCode;

	/** 회원번호 */
	private String memberNo;

	/** 입점사 번호 */
	private String vndrNo;

	/** 입점사 번호 */
	private String vndrName;

	/** 전시여부 */
	private String dispYn;
	
	/** 휴대폰번호 뒷자리*/
	private String hdphnBackNoText;

	@Override
	public void validate() throws ValidatorException {
		if (UtilsText.isNotBlank(this.getPrdtCodeType()) && UtilsText.isNotBlank(this.getPrdtCodeKeyword())) {
			List<String> codeList = Arrays.asList(this.getPrdtCodeKeyword().split("\r\n"));
			switch (this.getPrdtCodeType()) {
			case "prdt-code-online":
				// 온라인 상품코드 검색
				this.setPrdtCodeList(codeList);
				break;
			case "prdt-code-erp":
				// ERP 상품코드 검색
				this.setPrdtCodeList(codeList);
				break;
			default:
				this.validationMessage("product.valid.product.search.period.default.prdtCodeType");
				log.debug("지원하지 않는 상품코드 검색 유형입니다. {}", this.getPrdtCodeType());
			}
		}
	}

}
