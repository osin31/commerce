package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.product.model.master.base.BasePdProductOption;
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

@Slf4j
@Data
public class PdProductOption extends BasePdProductOption implements Validator {

	/** IBsheet 상태정보 */
	private String status;
	/** 자사상품여부 */
	private String mmnyPrdtYn;

	/** 등록자아이디 */
	private String rgsterId;
	/** 등록자이름 */
	private String rgsterName;
	/** 수정자아이디 */
	private String moderId;
	/** 수정자이름 */
	private String moderName;

	// 상품옵션재고 정보
	/** 온라인물류 */
	private Integer stockAiQty;
	/** 스마트물류 */
	private Integer stockAwQty;
	/** 오프라인매장 */
	private Integer stockAsQty;
	/** 입점사배송 */
	private Integer stockVdQty;
	/** 사방넷재고 */
	private Integer stockSbQty;

	/**
	 * BO 자사인경우 사용됨
	 */
	/** 온라인물류 주문수량 */
	private Integer orderAiCount;
	/** 스마트물류 주문수량 */
	private Integer orderAwCount;
	/** 오프라인매장 주문수량 */
	private Integer orderAsCount;
	/** 입점사 주문수량 */
	private Integer orderVdCount;

	/** 상품옵션 재고 정보 sum */
	private Integer stockTotQty;
	/** 옵션 selected flag */
	private boolean optnSelected = false;

	// 상품옵션가격이력 정보
	/** 추가금액 */
	private Integer optnAddAmt;

	// 재고관리여부(검증회피용)
	private String stockMgmtYn;

	// 판매상태명
	private String sellStatCodeName;

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
		if (UtilsText.equals(Const.BOOLEAN_TRUE, this.getMmnyPrdtYn())) {
			// BO권한인 경우
			// 옵션명
			if (UtilsText.isBlank(this.getOptnName())) {
				this.validationFieldMessage("product.valid.product.option.optnName", "wrhsAlertYn");
			} else if (this.getOptnName().length() > 33) {
				this.validationFieldMessage("product.valid.product.option.optnName.length", "wrhsAlertYn");
			}
			// 온라인재고량
			if (UtilsObject.isEmpty(this.getStockAiQty()) || this.getStockAiQty().compareTo(0) < 0) {
				this.validationFieldMessage("product.valid.product.option.stockAiQty", "wrhsAlertYn");
			}
		} else {
			// PO권한인 경우
			// 색상
			if (UtilsText.isBlank(this.getOptnName())) {
				this.validationFieldMessage("product.valid.product.option.vendor.optnName", "wrhsAlertYn");
			} else if (this.getOptnName().length() > 33) {
				this.validationFieldMessage("product.valid.product.option.vendor.optnName.length", "wrhsAlertYn");

				// 추가옵션
				if (UtilsText.isBlank(this.getAddOptn2Text())) {
					this.setAddOptn2Text(""); // 공백만 있는 경우, 공백 제거
				} else if (this.getAddOptn2Text().length() > 33) {
					this.validationFieldMessage("product.valid.product.option.addOptn2Text.length", "wrhsAlertYn");
				}
			}
			// 재고량
			if (UtilsText.equals(Const.BOOLEAN_TRUE, this.getStockMgmtYn())) {
				// 재고관리를 하는 경우, 재고량 검사 수행
				if (UtilsObject.isEmpty(this.getStockVdQty()) || this.getStockVdQty().compareTo(0) < 0) {
					this.validationFieldMessage("product.valid.product.option.stockAiQty", "wrhsAlertYn");
				}
			}
		}
		// 추가금액
		if (UtilsObject.isEmpty(this.getOptnAddAmt())) {
			this.validationFieldMessage("product.valid.product.option.optnAddAmt", "wrhsAlertYn");
		} else if (UtilsNumber.compare(100, this.getOptnAddAmt()) > 0) {
			// 옵션추가금액이 100보다 작은 경우
			if (UtilsNumber.compare(this.getOptnAddAmt(), 0) != 0) {
				// 추가금액이 0이 아닌 경우
				this.validationFieldMessage("product.valid.product.option.optnAddAmt.length", "wrhsAlertYn");
			}
		}
		// 전시여부
		if (UtilsObject.isEmpty(this.getUseYn())) {
			this.validationFieldMessage("product.valid.product.option.useYn", "wrhsAlertYn");
		}
		// 노출순서
		if (UtilsObject.isEmpty(this.getSortSeq())) {
			this.validationFieldMessage("product.valid.product.option.sortSeq", "wrhsAlertYn");
		}
	}

	/**
	 * 
	 * @Desc : 총재고수량과 주문가능수량을 계산
	 * @Method Name : calculateQuantity
	 * @Date : 2019. 4. 3.
	 * @Author : tennessee
	 * @param product
	 */
	public void calculateQuantity(PdProduct product) {
		this.calculateTotalStockQty(product); // 총재고수량 계산
		this.calculateOrderPsbltQty(); // 주문가능수량 계산
	}

	/**
	 * 
	 * @Desc : 사용자 권한에 따른 총재고수량을 계산하여 입력
	 * @Method Name : calculateTotalStockQty
	 * @Date : 2019. 4. 3.
	 * @Author : tennessee
	 * @param product
	 */
	private void calculateTotalStockQty(PdProduct product) {
		if (UtilsText.equals(Const.BOOLEAN_TRUE, this.getMmnyPrdtYn())) {
			// BO권한
			if (UtilsObject.isEmpty(this.getStockAiQty())) {
				this.setStockAiQty(0);
			}
			if (UtilsObject.isEmpty(this.getStockAwQty())) {
				this.setStockAwQty(0);
			}
			if (UtilsObject.isEmpty(this.getStockAsQty())) {
				this.setStockAsQty(0);
			}
			if (UtilsText.equals(Const.BOOLEAN_TRUE, product.getStockIntgrYn())) {
				// 재고연동을 하는 경우, AI/AW/AS 수량을 모두 합산하여 설정
				this.setTotalStockQty(UtilsNumber.sum(this.getStockAiQty().intValue(), this.getStockAwQty().intValue(),
						this.getStockAsQty().intValue()));
			} else {
				// 재고연동을 안하는 경우, AI 수량으로 설정
				this.setTotalStockQty(this.getStockAiQty().intValue());
			}
		} else {
			// PO권한
			this.setTotalStockQty(UtilsObject.isEmpty(this.getStockVdQty()) ? 0 : this.getStockVdQty());
		}
	}

	/**
	 * 
	 * @Desc : 주문가능수량을 계산
	 * @Method Name : calculateOrderPsbltQty
	 * @Date : 2019. 4. 3.
	 * @Author : tennessee
	 */
	private void calculateOrderPsbltQty() {
		this.setOrderPsbltQty(this.getTotalStockQty().intValue() - this.getTotalOrderQty().intValue());

	}

}
