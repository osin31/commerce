package kr.co.abcmart.bo.product.model.master;

import java.sql.Timestamp;
import java.util.Calendar;

import kr.co.abcmart.bo.product.model.master.base.BasePdProductPriceHistory;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.util.UtilsNumber;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품가격이력
 * @FileName : PdProductPriceHistory.java
 * @Project : abc.bo
 * @Date : 2019. 5. 2.
 * @Author : tennessee
 */
@Slf4j
@Data
public class PdProductPriceHistory extends BasePdProductPriceHistory
		implements Validator, Comparable<PdProductPriceHistory> {

	/** 등록자아이디 */
	private String rgsterId;
	/** 등록자이름 */
	private String rgsterName;
	/** 수정자아이디 */
	private String moderId;
	/** 수정자이름 */
	private String moderName;

	/** 자사상품여부 */
	private String mmnyPrdtYn;

	/** 상품프론트표시가격. 상세팝업에서 사용됨 */
	private Integer displayProductPrice;
	/** 상품프론트표시할인율. 상세팝업에서 사용됨 */
	private Integer displayDiscountRate;

	/**
	 * @Desc : 개인정보 마스킹 수행
	 * @Method Name : setPrivacy
	 * @Date : 2019. 5. 14.
	 * @Author : tennessee
	 */
	public void setPrivacy() {
		if (UtilsText.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn(), Const.BOOLEAN_FALSE)) {
			// 개인정보 접근 권한이 없는 경우
			this.setRgsterId(UtilsMasking.loginId(this.getRgsterId()));
			this.setRgsterName(UtilsMasking.userName(this.getRgsterName()));
			this.setModerId(UtilsMasking.loginId(this.getModerId()));
			this.setModerName(UtilsMasking.userName(this.getModerName()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.abcmart.common.validation.Validator#validate()
	 */
	@Override
	public void validate() throws ValidatorException {
		log.error(">>>>>>>>>>>>>validate check Start");
		if (UtilsText.equals(this.mmnyPrdtYn, Const.BOOLEAN_FALSE)) {
			log.error(">>>>>>>>>>>>>validate 입점사");
			// PO권한인 경우 정상가도 검증
			if (UtilsObject.isEmpty(this.getNormalAmt()) || UtilsNumber.compare(100, this.getNormalAmt()) > 0) {
				// 정상가가 없거나 100이하인 경우
				log.error(">>>>>>>>>>>>>validate 정상가가 없거나 100이하");
				this.validationFieldMessage("product.valid.product.priceHistory.normalAmt",
						"productPriceHistory.normalAmt"); // 정상가
			} else if (this.getNormalAmt() - (Math.ceil(this.getNormalAmt() / 100) * 100) > 0) {
				// 정상가에 10단위 이하 값이 있는 경우
				this.validationFieldMessage("product.valid.product.priceHistory.normalAmt.length",
						"productPriceHistory.normalAmt"); // 정상가
			}
		}
		log.error(">>>>>>>>>>>>>validate 입점사 아님");
		if (UtilsObject.isEmpty(this.getOnlnSellAmt()) || UtilsNumber.compare(100, this.getOnlnSellAmt()) > 0
				|| UtilsNumber.compare(this.getOnlnSellAmt(), this.getNormalAmt()) > 0) {
			// 온라인판매가가 없거나 100보다 작거나 정상가보다 큰 경우
			this.validationFieldMessage("product.valid.product.priceHistory.onlnSellAmt",
					"productPriceHistory.onlnSellAmt"); // 온라인판매가
		} else if (this.getOnlnSellAmt() - (Math.ceil(this.getOnlnSellAmt() / 100) * 100) > 0) {
			// 온라인판매가에 10단위 이하 값이 있는 경우
			this.validationFieldMessage("product.valid.product.priceHistory.onlnSellAmt.length",
					"productPriceHistory.onlnSellAmt"); // 온라인판매가
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(PdProductPriceHistory target) {
		int result = 0;

		if (UtilsObject.isEmpty(target)) {
			result = -1;
		} else {

			this.setZeroIfNull();
			target.setZeroIfNull();

			if (UtilsNumber.compare(this.getNormalAmt().intValue(), target.getNormalAmt()) != 0) {
				result++;
			}
			if (UtilsNumber.compare(this.getSellAmt().intValue(), target.getSellAmt()) != 0) {
				result++;
			}
			if (UtilsNumber.compare(this.getOnlnSellAmt().intValue(), target.getOnlnSellAmt()) != 0) {
				result++;
			}
			if (UtilsNumber.compare(this.getErpSellAmt().intValue(), target.getErpSellAmt()) != 0) {
				result++;
			}
			if (UtilsNumber.compare(this.getOnlnDscntRate().intValue(), target.getOnlnDscntRate()) != 0) {
				result++;
			}
			if (UtilsNumber.compare(this.getErpDscntRate().intValue(), target.getErpDscntRate()) != 0) {
				result++;
			}
			if (UtilsNumber.compare(this.getEmpDscntRate().intValue(), target.getEmpDscntRate()) != 0) {
				result++;
			}
		}
		return result;
	}

	/**
	 * @Desc : 숫자정보가 NULL인 경우, 0으로 설정
	 * @Method Name : setZeroIfNull
	 * @Date : 2019. 4. 17.
	 * @Author : tennessee
	 */
	private void setZeroIfNull() {
		if (UtilsObject.isEmpty(this.getNormalAmt())) {
			this.setNormalAmt(0);
		}
		if (UtilsObject.isEmpty(this.getSellAmt())) {
			this.setSellAmt(0);
		}
		if (UtilsObject.isEmpty(this.getOnlnSellAmt())) {
			this.setOnlnSellAmt(0);
		}
		if (UtilsObject.isEmpty(this.getErpSellAmt())) {
			this.setErpSellAmt(0);
		}
		if (UtilsObject.isEmpty(this.getOnlnDscntRate())) {
			this.setOnlnDscntRate((short) 0);
		}
		if (UtilsObject.isEmpty(this.getErpDscntRate())) {
			this.setErpDscntRate((short) 0);
		}
		if (UtilsObject.isEmpty(this.getEmpDscntRate())) {
			this.setEmpDscntRate((short) 0);
		}
	}

	/**
	 * @Desc : 기본 데이터를 설정
	 * @Method Name : setDefaultData
	 * @Date : 2019. 4. 26.
	 * @Author : tennessee
	 */
	public void setDefaultData(PdProduct product) {
		// 숫자필드에 대한 null 처리
		this.setZeroIfNull();

		// 적용일자 설정
		this.setApplyStartDtm(null); // 서버시간 입력을 위한 null 설정
		if (UtilsObject.isEmpty(this.getApplyEndDtm())) {
			Calendar c = Calendar.getInstance();
			c.set(9999, Calendar.DECEMBER, 31, 23, 59, 59);
			this.setApplyEndDtm(new Timestamp(c.getTimeInMillis()));
		}

		// 판매가 설정
		if (UtilsText.equals(Const.BOOLEAN_TRUE, product.getDprcExceptYn())
				|| UtilsNumber.compare(this.getErpSellAmt(), 0) < 1) {
			// 감가제외인 경우, 온라인판매가를 판매가에 설정
			// 입점상품은 감가제외여부값이 "Y"로 고정되므로 해당 경우에서 처리됨
			// 오프라인판매가가 0 이하인 경우(자사예외인 경우와 입점사인 경우), 온파인판매가를 판매가로 설정
			this.setSellAmt(this.getOnlnSellAmt());
		} else {
			// 감가제외가 아닌 경우, 온라인판매가와 오프라인판매가 중 낮은 가격을 판매가에 설정
			// 오프라인판매가가 0인 경우는, 위 if문에서 처리됨

			// if (UtilsNumber.compare(this.getErpSellAmt(), this.getOnlnSellAmt()) >= 0) {
			// 2020.03.07 임점사 상품이 아닌 경우 sellamt에는 무조건 erp 판매 가격을 등록 한다.
			// 임접사 상품 확인 필요
			if (UtilsText.equals(Const.BOOLEAN_FALSE, this.getMmnyPrdtYn())
					&& UtilsNumber.compare(this.getErpSellAmt(), this.getOnlnSellAmt()) >= 0) {
				// 온라인가격이 낮은 경우
				this.setSellAmt(this.getOnlnSellAmt());
			} else {
				// 오프라인가격이 낮은 경우
				this.setSellAmt(this.getErpSellAmt());
			}
		}

	}

}
