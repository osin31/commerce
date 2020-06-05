package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.product.model.master.base.BaseBdProductInquiry;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BdProductInquiry extends BaseBdProductInquiry implements Validator {

	/** 상담유형코드명 */
	private String cnslTypeCodeName;

	/** 상담유형상세코드 */
	private String cnslTypeDtlCodeName;

	/** 상품 */
	private PdProduct product;

	/** 상품명 */
	private String prdtName;

	/** 담당업체번호 */
	private String vndrNo;

	/** 담당업체 */
	private String vndrName;

	/** 회원등급 */
	private String memberTypeCodeName;

	/** 작성자 ID */
	private String memberId;

	/** 작성자 */
	private String memberName;

	/** 답변자 ID */
	private String aswrId;

	/** 답변자 */
	private String aswrName;

	/** 스타일 */
	private String styleInfo;

	/** 자사입점구분 */
	private String mmnyPrdtYn;

	/** 답변상태코드명 */
	private String aswrStatCodeName;

	/** 사이트명 */
	private String siteName;

	/** 채널명 */
	private String chnnlName;

	/** 브랜드명 */
	private String brandName;
	
	/**브랜드영문이름*/
	private String brandEnName;	

	/** ERP상품번호 */
	private String vndrPrdtNoText;

	/** 채널번호 */
	private String chnnlNo;

	/** 표준카테고리명 */
	private String stdCtgrName;

	/** 상품이미지 */
	private String productImageUrl;

	/** 상품이미지대체텍스트 */
	private String productAltrnText;

	@Override
	public void validate() throws ValidatorException {
		if (UtilsText.isBlank(this.getDispYn())) {
			validationMessage("product.valid.product.inquiry.dispYn");
		}
		if (UtilsText.isBlank(this.getAswrContText())) {
			validationMessage("product.valid.product.inquiry.aswrContText");
		}

		if (!CommonCode.ASWR_STAT_CODE_HD.equals(this.getAswrStatCode())) { // 답변보류가 아닐 경우 답변완료 처리
			this.setAswrStatCode(CommonCode.ASWR_STAT_CODE_CM);
		}

//		if (UtilsText.isBlank(this.getAswrStatCode())) { 
//			validationMessage("product.valid.product.inquiry.aswrStatCode");
//		}
	}

	// 작성자 정보 마스킹 처리
	public String getMemberInfo() {
		return UtilsMasking.gridMasking(getMemberId(), getMemberName());
	}

	// 답변자 정보 마스킹 처리
	public String getAswrInfo() {
		return UtilsMasking.gridMasking(getAswrId(), getAswrName());
	}

}
