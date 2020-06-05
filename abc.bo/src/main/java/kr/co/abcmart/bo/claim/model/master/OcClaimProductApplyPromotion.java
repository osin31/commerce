package kr.co.abcmart.bo.claim.model.master;

import kr.co.abcmart.bo.claim.model.master.base.BaseOcClaimProductApplyPromotion;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OcClaimProductApplyPromotion extends BaseOcClaimProductApplyPromotion {

	private java.lang.Integer orderPrdtSeq; // 주문상품순번

}
