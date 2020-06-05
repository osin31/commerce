package kr.co.abcmart.bo.promotion.model.master;

import kr.co.abcmart.bo.promotion.model.master.base.BasePrCouponApplyProduct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PrCouponApplyProduct extends BasePrCouponApplyProduct {
	// 상품 컬럼
	private String prdtNo;

	private String prdtTypeCode;

	private String siteNo;

	private String chnnlNo;

	private String prdtName;

	private String engPrdtName;

	private String vndrNo;

	private String vndrPrdtNoText;

	private String mmnyPrdtYn;

	private String brandNo;

	private String orgPlaceCode;

	private String styleInfo;

	private String prdtColorInfo;

	private String mnftrName;

	private String stdCtgrNo;

	private String stdrCtgrNo;

	private String cntcPrdtSetupYn;

	private String rltnGoodsSetupYn;

	private String addOptnSetupYn;

	private String sizeChartDispYn;

	private java.lang.Integer sizeChartSeq;

	private String empDscntYn;

	private String orderMnfctYn;

	private String dprcExceptYn;

	private String storePickupPsbltYn;

	private String freeDlvyYn;

	private String stockIntgrYn;

	private String stockUnIntgrRsnCode;

	private String stockMgmtYn;

	private String buyLimitYn;

	private java.lang.Integer minBuyPsbltQty;

	private java.lang.Integer dayMaxBuyPsbltQty;

	private java.lang.Integer maxBuyPsbltQty;

	private String itemCode;

	private String genderGbnCode;

	private String dispFlagText;

	private String srchPsbltYn;

	private String srchKeyWordText;

	private String wrhsAlertYn;

	private java.sql.Timestamp sellStartDtm;

	private java.sql.Timestamp sellEndDtm;

	private String rsvPrdtYn;

	private java.sql.Timestamp rsvDlvyYmd;

	private String affltsSendYn;

	private String aconnectDispYn;

	private String dispYn;

	private String igrmallDispExceptYn;

	private String vndrSuspdYn;

	private String aprvStatCode;

	private String sellStatCode;

	private String useYn;

	private String rgsterNo;

	private java.sql.Timestamp rgstDtm;

	private String aprverNo;

	private java.sql.Timestamp aprverDtm;

	private String moderNo;

	private java.sql.Timestamp modDtm;

}
