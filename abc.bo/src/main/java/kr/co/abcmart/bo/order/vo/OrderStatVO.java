package kr.co.abcmart.bo.order.vo;

import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;

@Data
public class OrderStatVO extends BaseBean {

	// 사이트번호
	private String siteNo;

	// 총 주문상품 건수
	private java.lang.Integer orderTotalCnt;

	// 결제완료/입금대기 건수
	private java.lang.Integer orderReadlyCnt;

	// 배송완료
	private java.lang.Integer orderConfirmCnt;

	// 취소완료 건수
	private java.lang.Integer orderCancelCnt;

	// 클레임 건수
	private java.lang.Integer orderClaimTotalCnt;

	// 클레임 종료건수
	private java.lang.Integer orderClaimEndCnt;

	// 클레임 진행 건수
	private java.lang.Integer orderClaimIngCnt;
}