package kr.co.abcmart.bo.product.vo;

import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PdProductSkuExcelListVO extends BaseBean {
	private String prdtNo;
	private String chnnlName;
	private String vndrPrdtNoText;
	private String brandEnName;
	private String engPrdtName;
	private String brandName;
	private String prdtName;
	private String styleInfo;
	private String prdtColorInfo;
	private String prdtOptnNo;
	private String optnName;
	private Integer sortSeq;
	private Integer availableStockAiQty;
	private Integer availableStockAwQty;
	private Integer availableStockAsQty;
	private Integer orderPsbltQty;
	private String sellStatCode;
	private String useYn;
	private String stockIntgrYn;
	private Integer cmsnRate;
	private String stockUnIntgrRsnCodeName;
}
