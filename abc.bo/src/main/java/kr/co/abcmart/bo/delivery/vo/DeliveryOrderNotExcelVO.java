package kr.co.abcmart.bo.delivery.vo;

import kr.co.abcmart.bo.order.model.master.base.BaseOcOrder;
import kr.co.abcmart.common.util.UtilsMasking;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.interfaces.util.UtilsText;
import kr.co.abcmart.user.LoginManager;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString(callSuper = true)
public class DeliveryOrderNotExcelVO extends BaseOcOrder {

	private String siteName;
	private String strOrderDtm;
	private java.lang.Integer orderPrdtSeq;
	private String prdtNo;
	private String prdtName;
	private String optnName;
	private String strPymnt;
	private String strOrderAmt;
	private String dlvyTypeCodeName;
	private String dlvyStatCodeName;
	private String dlvyIdText;
	private String logisVndrCodeName;
	private String waybilNoText;
	private String stockGbnCode;
	private String stockGbnCodeName;
	private String buyerId;

	public String getBuyerName() {

		if (UtilsText.equals(super.getMemberNo(), Const.NON_MEMBER_NO)) {
			if (LoginManager.isPersonalInfoManager()) {
				return super.getBuyerName();
			} else {
				return UtilsMasking.userName(super.getBuyerName());
			}
		} else {
			if (LoginManager.isPersonalInfoManager()) {
				return UtilsText.concat(super.getBuyerName(), Const.L_PAREN, this.buyerId, Const.R_PAREN);
			} else {
				return UtilsText.concat(UtilsMasking.userName(super.getBuyerName()), Const.L_PAREN,
						UtilsMasking.loginId(this.buyerId), Const.R_PAREN);
			}
		}
	}

	// 수령지 우편주소
	public String getRcvrPostAddrText() {

		if (LoginManager.isPersonalInfoManager()) {
			return super.getRcvrPostAddrText();
		} else {
			return UtilsMasking.postAddress(super.getRcvrPostAddrText());
		}
	}

	// 수령지 상세주소
	public String getRcvrDtlAddrText() {

		if (LoginManager.isPersonalInfoManager()) {
			return super.getRcvrDtlAddrText();
		} else {
			return UtilsMasking.postAddress(super.getRcvrDtlAddrText());
		}
	}

	// 수령자 이름
	public String getRcvrName() {

		if (LoginManager.isPersonalInfoManager()) {
			return super.getRcvrName();
		} else {
			return UtilsMasking.userName(super.getRcvrName());
		}
	}

	public String getStockGbnCodeName() {

		if (UtilsText.equals(this.stockGbnCode, CommonCode.STOCK_GBN_CODE_AS)
				&& !UtilsText.isBlank(super.getStoreName())) {
			// 재고구분코드가 매장이고 매장명이 있다면 : 매장(매장명)
			return UtilsText.concat(this.stockGbnCodeName, Const.L_PAREN, super.getStoreName(), Const.R_PAREN);
		}
		return this.stockGbnCodeName;
	}
}
