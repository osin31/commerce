package kr.co.abcmart.bo.product.vo;

import java.util.List;

import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PdProductOptionStockSearchVO {

	/** 조회유형. ERP상품등록 */
	private String newErpProductYn;

	/** 상품 번호 */
	private String prdtNo;

	/** 상품옵션번호 */
	private String prdtOptnNo;

	/** 내부관리번호 */
	private String vndrPrdtNoText;

	/** 채널번호 */
	private String chnnlNo;

	/** 재고구분코드 목록 */
	private List<SyCodeDetail> stockGbnCodeList;

}
