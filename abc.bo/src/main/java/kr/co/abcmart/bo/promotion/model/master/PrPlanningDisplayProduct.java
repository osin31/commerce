package kr.co.abcmart.bo.promotion.model.master;

import kr.co.abcmart.bo.promotion.model.master.base.BasePrPlanningDisplayProduct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PrPlanningDisplayProduct extends BasePrPlanningDisplayProduct {

	/** pd_product **/
	private String prdtNo;
	private String prdtName;
	private String brandNo;
	private String brandName;
	private String vndrNo;
	private String mmnyPrdtYn;
	private String siteNo;
	private String stdCtgrNo;
	private String stdCtgrName;
	private String sellStatCode;
	private String dispYn;
	/** pd_product **/

	private String imageUrl;
	private String altrnText;

	private String[] prdtNoArr;

	/** 스타일 정보 */
	private String styleInfo;
	/** 색상 정보 */
	private String prdtColorInfo;

}