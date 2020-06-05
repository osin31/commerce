package kr.co.abcmart.bo.product.vo;

import java.util.Arrays;
import java.util.List;

import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PdProductApprovalSearchVO implements Validator {

	/** ■■ 상품승인대상관리 (PO) ■ */

	/** ■■ 자사상품 승인관리 (BO) ■ */

	/** 브랜드명 */
	private String brandName;

	/** 상품명 */
	private String prdtName;

	/** 매장구분 */
	private String maejangCd;

	/** 매장등급 코드 */
//	private String tierFlagCode;

	private String[] tierFlagCode;

	/** ■■ 입점상품 승인관리 (BO) ■ */

	/** 사이트 구분 */
	private String siteNo;

	/** ■■ 화면 공통 ■ */

	/** 승인상태 제어 */
	private String atPO;

	// BO 전용 검색 옵션
	/** 자사상품여부 (Y:자사, N:입점) */
	private String mmnyPrdtYn;

	/** 상품코드. 구분 */
	private String prdtCodeType;

	/** 상품코드. 상품코드목록 */
	private String prdtCodeKeyword;

	/** 상품코드. 상품번호목록 */
	private List<String> prdtCodeList;

	/** 브랜드 */
	private String brandNo;

	/** 승인 처리자 */
	private String aprverNo;

	/** 기간 중 형태 */
	private String periodType;

	/** 기간 중 시작일자 */
	private String periodStartDate;

	/** 기간 중 종료일자 */
	private String periodEndDate;

	/** 승인구분 */
	private String prdtAprvReqCode;

	/** 승인상태 */
	private String[] aprvStatCodeArr;

	/** 검색어. 구분 */
	private String searchKeywordType;

	/** 검색어. 키워드 */
	private String searchKeyword;

	/** 전시 채널 */
	private String chnnlNo;

	/** 업체 번호 */
	private String vndrNo;

	/** 업체명 */
	private String vndrName;

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
