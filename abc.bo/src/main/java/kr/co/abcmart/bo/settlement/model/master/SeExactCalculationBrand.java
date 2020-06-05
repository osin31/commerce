package kr.co.abcmart.bo.settlement.model.master;

import kr.co.abcmart.bo.settlement.model.master.base.BaseSeExactCalculationBrand;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SeExactCalculationBrand extends BaseSeExactCalculationBrand {

	// 정산 확정 여부
	private String excclcDcsnYn;

	// 브랜드 네임
	private String brandName;

}
