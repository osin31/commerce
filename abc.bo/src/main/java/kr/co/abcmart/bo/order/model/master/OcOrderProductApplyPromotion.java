package kr.co.abcmart.bo.order.model.master;

import kr.co.abcmart.bo.order.model.master.base.BaseOcOrderProductApplyPromotion;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OcOrderProductApplyPromotion extends BaseOcOrderProductApplyPromotion {

	private String[] promoTypeCodes; // 프로모션 타입 코드 배열

}
