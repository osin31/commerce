package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.product.model.master.base.BasePdProduct;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PdFreeGift extends BasePdProduct {

	/** 총재고수량 */
	private String totalStockQty;

	/** 총주문수량 */
	private String totalOrderQty;

	/** 주문가능수량 */
	private String orderPsbltQty;

	/** 행사제한수량 */
	private Integer eventLimitQty;

	/** 최대행사제한수량 */
	private String maxEventLimitQty;

	/** 수수료율 */
	private Integer cmsnRate;

	/** 프로모션 번호 */
	private String promoNo;

	/** 등록자 */
	private String rgsterName;

	/** 등록자 Id */
	private String rgsterId;

	/** 수정자 */
	private String moderName;

	/** 수정자 Id */
	private String moderId;

	/** 상품옵션 */
	@ParameterOption(target = "status")
	private PdProductOption[] productOption;

	/** 첨부 파일 */
	@ParameterOption(target = "prdtRltnFileSeq")
	private PdProductRelationFile[] productRelationFile;

	// 등록자 정보 마스킹 처리
	public String getRgsterInfo() {
		return UtilsMasking.gridMasking(getRgsterId(), getRgsterName());
	}

	// 수정자 정보 마스킹 처리
	public String getModerInfo() {
		return UtilsMasking.gridMasking(getModerId(), getModerName());
	}

}
