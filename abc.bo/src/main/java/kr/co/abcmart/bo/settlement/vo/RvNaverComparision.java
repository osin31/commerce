package kr.co.abcmart.bo.settlement.vo;

import java.io.Serializable;

import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class RvNaverComparision extends BaseBean implements Serializable {

	private String payHistId; // 네이버페이결제이력번호
	private String payMentId; // 네이버페이결제번호
	private String admissionTypeCode; // 결제승인유형 code 결제 :01 , 취소 : 03,04
	private String[] admissionTypeCodes; // 결제승인유형 code 결제 :01 , 취소 : 03,04
	private String merchantId; // 가맹점센터로그인아이디
	private String admissionYmdt; // 결제/취소일시
	private String totalPayAmount; // 총결제/취소금액
	private String productName; // 상품명
	private String admissionState; // 결제상태 ( SUCCESS : 성공 , FAIL : 실패 )
	private String primaryCommissionAmount; // 정산수수료
	private String primarySettleAmount; // 정산금액
	private String totalSettleAmount; // 전체정산금액
	private String totalCommissionAmount; // 전체정산수수료
	private String orgOrderNo; // 주문번호
	private String mapngYn; // 매칭 여부
	private String sendParmDate; // 매칭일자

	private String srchType; // 검택조건
	private String searchWord; // 검색어
	private String siteNo; // 사이트 번호

	private String fromDate; // 날짜 조건
	private String toDate; // 날짜 조건

	private int totalPay; // 결제금액
	private int mapngPay; // 매칭금액
	private int notMapngPay; // 미매칭금액

}
