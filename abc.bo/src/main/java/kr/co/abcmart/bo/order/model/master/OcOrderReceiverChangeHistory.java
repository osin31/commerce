package kr.co.abcmart.bo.order.model.master;

import kr.co.abcmart.bo.order.model.master.base.BaseOcOrderReceiverChangeHistory;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString(callSuper = true)
public class OcOrderReceiverChangeHistory extends BaseOcOrderReceiverChangeHistory {

	// 배송메모
	private String dlvyMemoText;

	private String orderNo;
	private String cvnstrCode;
	private String cvnstrNoText;
	private String cvnstrName;
	private String telNoText;
	private String postCodeText;
	private String postAddrText;
	private String dtlAddrText;
	private String arvlStoreCodeText;
	private String arvlStoreBrcdText;
	private String dongNameCodeText;
	private String arvlDongName;
	private String dlvyPlaceYn;
}
