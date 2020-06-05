package kr.co.abcmart.bo.display.vo;

import java.io.Serializable;

import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.validation.Validator;
import lombok.Data;

@Data
public class DpWebzineSearchVO implements Serializable, Validator {

	// 목록 개수
	private int pageCount;

	/** 웹진 타입 */
	private String wbznType;

	/** 날짜 타입 */
	private String dateType;

	/** 시작일시 */
	private String startYmd;

	/** 종료일시 */
	private String endYmd;

	/** 시작 호수 */
	private String startWbznNo;

	/** 종료 호수 */
	private String endWbznNo;

	/** 전시여부 */
	private String dispYn;

	/** 검색 타입 */
	private String keywordType;

	/** 검색어 */
	private String keyword;

	/** 사이트 번호 */
	private String siteNo;

	private String[] wbznTypeArr;

	@Override
	public void validate() throws ValidatorException {
//		// 상담유형코드 Null 체크
//		if (UtilsText.isBlank(getSearchCnslTypeCode())) {
//			validationMessage("common.valid.variableisnull", "",
//					new String[] { Message.getMessage("cs.msg.cnsltypecode") });
//		}
//		// 상담유형상세코드 Null 체크
//		if (UtilsText.isBlank(getSearchCnslTypeDtlCode())) {
//			validationMessage("common.valid.variableisnull", "",
//					new String[] { Message.getMessage("cs.msg.cnsltypetypecode") });
//		}
	}

}
