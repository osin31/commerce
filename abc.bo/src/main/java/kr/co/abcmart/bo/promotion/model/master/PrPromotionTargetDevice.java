package kr.co.abcmart.bo.promotion.model.master;

import kr.co.abcmart.bo.promotion.model.master.base.BasePrPromotionTargetDevice;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PrPromotionTargetDevice extends BasePrPromotionTargetDevice {

	/** 디바이스명 */
	private String deviceName;

	/** 채널번호 */
	private String chnnlNo;

	/** 상품번호 */
	private String prdtNo;

}
