package kr.co.abcmart.bo.promotion.model.master;

import java.util.List;

import kr.co.abcmart.bo.promotion.model.master.base.BasePrPromotionTargetProduct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PrPromotionTargetProduct extends BasePrPromotionTargetProduct {

	private String giftDelCheck;

	private String productDelCheck;

	private int promoDuplCount;

	private List<String> targetPrdtNos;

	private String promoTypeCode;
}
