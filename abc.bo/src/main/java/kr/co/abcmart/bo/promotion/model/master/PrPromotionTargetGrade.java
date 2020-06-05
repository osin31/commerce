package kr.co.abcmart.bo.promotion.model.master;

import kr.co.abcmart.bo.promotion.model.master.base.BasePrPromotionTargetGrade;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PrPromotionTargetGrade extends BasePrPromotionTargetGrade {

	private String memberTypeCodeName;

	private String mbshpGradeCodeName;

}
