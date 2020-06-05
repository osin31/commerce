package kr.co.abcmart.bo.promotion.model.master;

import kr.co.abcmart.bo.promotion.model.master.base.BasePrPromotionTargetCategory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PrPromotionTargetCategory extends BasePrPromotionTargetCategory {

	private String upCtgrNo;

	private String ctgrName;

	private String chnnlName;

	private String siteNo;

	private String chnnlNo;

	private String ctgrNamePath;

	private String ctgrNoPath;

	private String level;
}
