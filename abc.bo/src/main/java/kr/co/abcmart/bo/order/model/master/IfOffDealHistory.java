package kr.co.abcmart.bo.order.model.master;

import kr.co.abcmart.bo.order.model.master.base.BaseIfOffDealHistory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class IfOffDealHistory extends BaseIfOffDealHistory {

	// 상품명
	private String prdtName;
	// 매장명
	private String storeName;
	// 매장명
	private Integer orderCnt;
	// 매장명
	private java.math.BigDecimal orderAmt;

	// 회원번호
	private String memberNo;

	// 주문채널
	private String chnnlNo;

	// 거래 번호
	private String orderNo;

	// 기간 검색
	private String orderDateKey;

	/**
	 * 검색기간 from
	 */
	private String fromDate;

	/**
	 * 검색기간 to
	 */
	private String toDate;

	/**
	 * 주문 취소 여부
	 */
	private String chkCancelAll;
	/**
	 * 주문 취소 여부
	 */
	private String chkCancel;

	// 오프라인 매장번호
	private String storeIdText;
}
