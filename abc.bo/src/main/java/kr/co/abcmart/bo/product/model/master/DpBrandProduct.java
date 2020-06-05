package kr.co.abcmart.bo.product.model.master;

import kr.co.abcmart.bo.product.model.master.base.BaseDpBrandProduct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DpBrandProduct extends BaseDpBrandProduct {

	/* 대상 상품 관리 */
	private String status;
	private String prdtNo;
	private String prdtName;
	private String vndrPrdtNoText;
	private String titleImageName;
	private String titleImagePathText;
	private String titleImageUrl;
	private String titleImage;
	private String stdCtgrName;
	private String chnnlName;
	private String sellStatCode;
	private String dispYn;

}
