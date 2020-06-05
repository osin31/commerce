package kr.co.abcmart.bo.settlement.vo;

import java.io.Serializable;

import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class RvGiftCardComparision extends BaseBean implements Serializable {

	private String niceServiceType; // 거래구분 회수: 0215 판매 : 0216
	private String[] niceServiceTypes; // 거래구분 회수: 0215 판매 : 0216
	private String apprNo; // 승인번호
	private String tranDt; // 거래일자
	private String tranHms; // 거래시간
	private String storeCd; // 점포코드
	private String voucherFrom; // 카드번호
	private String tranKd; // 서비스코드 0215 : 회수:01, 회수취소:21 / 0216 : 판매승인(01), 판매취소(21), 충전형 승인(08), 충전취소(28)
							// 판매대행승인(0x), 판매대행승인취소(2x)
	private String transStatus; // 거래상태 0 : 승인 , 2: 강제취소 , 1 : 망상취소 , 그외 망상취소,
	private String tranType; // 구분코드 0215 : "1" : 일반 회수, "2" : 교환, "4" : 잔액환불, "b" : 낙전 / 0216 - "1" : 일반 판매
								// "2" : 교환 판매(카드교체, 잔액이전 시) "3" : 특판 판매 "4" : 자가소비(발행사소비)
	private String purchaseAmt; // 액면가( hidden 처리)
	private String tranAmt; // 회수/판매금액
	private String salerKind; // 판매형태 그대로 표기
	private String returnType; // 환불대상자 ( hidden 처리)
	private String oriApprDt; // 원거래일자 ( hidden 처리)
	private String oriApprNo; // 원거래승인번호 ( hidden 처리)
	private String custId; // 회원번호 ( hidden 처리)
	private String payCd; // 결재수단 ( hidden 처리)
	private String companyInfo; // 가맹점영역 ( hidden 처리)
	private String mapngYn; // 매핑여부
	private String giftCardOrderNo; // 기프트카드주문번호 ( hidden 처리)
	private String rsnCont; // 사유내용 ( hidden 처리)
	private String rsnRgsterNo; // 사유등록자번호 ( hidden 처리)
	private String rsnRgstDtm; // 사유등록일시 ( hidden 처리)
	private String intrlkDtm; // 연동일시 ( hidden 처리)
	private String sendParmDate; // 매칭일자
	private String orgOrderNo; // 원주문번호
	private String orderNo; // 주문번호 ( hidden 처리)

	private String srchType; // 검택조건
	private String searchWord; // 검색어
	private String siteNo; // 사이트 번호

	private String fromDate; // 날짜 조건
	private String toDate; // 날짜 조건

	private int recoveryAmount; // 회수금액
	private int saleAmount;
	private int mapngPay; // 매칭금액
	private int notMapngPay; // 미매칭금액

}
