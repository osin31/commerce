package kr.co.abcmart.bo.order.model.master;

import kr.co.abcmart.bo.order.model.master.base.BaseOcGiftCardOrder;
import kr.co.abcmart.interfaces.module.payment.kcp.model.KcpPaymentApproval;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OcGiftCardOrder extends BaseOcGiftCardOrder {
	// 결제일시
	private String pymntDtm;// PYMNT_DTM
	// 주문 상태 코드
	private String orderStatCodeName;// ORDER_STAT_CODE_NAME

	private String pymntDate;

	private String pymntMeansCode;
	private String kcpPayType;
	private String imageUrl;
	private String historyStartDtm;
	private String historyEndDtm;
	private String pymntAprvNoText;
	/**
	 * kcp결제 모듈에 필요한 객체
	 */
	private KcpPaymentApproval kcpPaymentApproval;

}
