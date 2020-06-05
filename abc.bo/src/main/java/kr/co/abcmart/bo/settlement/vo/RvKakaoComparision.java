package kr.co.abcmart.bo.settlement.vo;

import java.io.Serializable;

import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class RvKakaoComparision extends BaseBean implements Serializable {

	private String tid; // 결제 고유번호
	private String aid; // 호출 고유번호
	private String cid; // 가맹점 코드
	private String status; // 결제 상태값
	private String paymentMethodType; // 결제수단 CARD:카드, MONEY:현금
	private String itemName; // 상품이름
	private String itemCode; // 상품코드
	private String cardBin; // 카드빈
	private String installMonth; // 할부개월수
	private String cardCorpName; // 카드사정보
	private String interestFreeInstall; // 무이자 할부여부
	private String approvedAt; // 결제/취소거래시간
	private String amount; // 결제/취소총액
	private String paymentActionType; // 결제타입 결제 - PAYMENT, 취소 - CANCEL
	private String[] paymentActionTypes; // 결제타입 결제 - PAYMENT, 취소 - CANCEL
	private String orgOrderNo; // 주문번호
	private String mapngYn; // 매칭 여부
	private String sendParmDate; // 매칠 일자

	private String srchType; // 검택조건
	private String searchWord; // 검색어
	private String siteNo; // 사이트 번호

	private String fromDate; // 날짜 조건
	private String toDate; // 날짜 조건

	private int totalPay; // 결제금액
	private int mapngPay; // 매칭금액
	private int notMapngPay; // 미매칭금액

}
