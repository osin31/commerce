package kr.co.abcmart.bo.order.vo;

import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OcOrderExcelVo extends BaseBean {

	private String siteNo;
	private String siteName;
	private String orderNo;
	private String orderDtm;
	private String orderStatCode;
	private String orderStatCodeName;
	private String orderPrdtStatCodeName;
	private String salesCnclGbnType;
	private String prdtNo;
	private String vndrPrdtNoText;;
	private String brandNo;;
	private String brandName;
	private String prdtName;;
	private String styleInfo;;
	private String optnName;;
	private String prdtTypeCode;;
	private String prdtTypeFlag;
	private String orderQty;
	private String mmnyPrdtYn;
	private String dlvyTypeCode;
	private String dlvyTypeCodeName;
	private String stockGbnCode;
	private String stockGbnCodeName;
	private String storeName;
	private String logisVndrCode;
	private String logisVndrCodeName;
	private String waybilNoText;
	private String dlvyProcDtm;
	private String prdtNormalAmt;
	private String prdtSellAmt;
	private String optnAddAmt;
	private String sellAmt;
	private String empDscntRate;
	private String empAmt;
	private String totalDscntAmt;
	private String cpnDscntAmt;
	private String orderAmt;
	private String deliveryAmt;
	private String promoName;
	private String couponName;
	private String vendorCancelFlag;
	private String memberTypeCode;
	private String memberTypeCodeName;
	private String dlvyIdText;

	private String[] orderNos;
	private String strOrderNos;
	// private String[] orderPrdtSeqs;

	// 결제수단 추가
	private String pymntMeansCodeName;
	// 색상코드 추가
	private String prdtColorInfo;

	// 구매포인트 사용량
	private String buyPointUseAmt;
	// 이벤트포인트 사용량
	private String eventPointUseAmt;
}
