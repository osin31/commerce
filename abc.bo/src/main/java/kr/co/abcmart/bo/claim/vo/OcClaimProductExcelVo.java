/**
 * 
 */
package kr.co.abcmart.bo.claim.vo;

import kr.co.abcmart.bo.claim.model.master.OcClaim;
import kr.co.abcmart.common.bean.BaseBean;
import kr.co.abcmart.common.request.annotation.ParameterOption;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.interfaces.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc :
 * @FileName : OcClaimProductExcelVo.java
 * @Project : abc.bo
 * @Date : 2019. 3. 6.
 * @Author : 이강수
 */

@Slf4j
@Data
public class OcClaimProductExcelVo extends BaseBean {

	private String siteName;

	private String clmGbnCodeName;

	private String clmNo;

	private String clmRsnCodeName;

	private String orgOrderNo;

	private String orderNo;

	private String dlvyIdText;

	private String prdtNo;

	private String prdtName;

	private String brandName;

	private String styleInfo;

	private String optnName;

	private String vndrPrdtNoText;

	private String vndrName;

	private String vndrNo;

	private String stockGbnCode;

	private String stockGbnCodeName;

	private String storeName;

	public String getStockGbnCodeName() {
		if (UtilsObject.isNotEmpty(stockGbnCode)) {
			if (UtilsText.equals(stockGbnCode, CommonCode.STOCK_GBN_CODE_AS)) { // 오프라인매장
				if (UtilsObject.isNotEmpty(storeName)) {
					return UtilsText.concat("매장(" + storeName + ")");
				}
			}
		}
		return stockGbnCodeName;
	}

	private String addDlvyAmtPymntText;

	// 배송비
	private String dlvyPymntAmt;

	// 환수금액
	private String redempAmt;

	// 환불금액
	private String refundAmt;

	// 결제금액
	private String pymntAmt;

	private String unProcYnText;

	private String rgstDtm;

	private String clmStatCodeName;

	private String clmPrdtStatCodeName;

	private String modDtm;

	private String[] clmNos;

	@ParameterOption(target = "clmPrdtSeq")
	private OcClaim[] clmPrdtSeqs;

}
