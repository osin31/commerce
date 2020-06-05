package kr.co.abcmart.bo.settlement.vo;

import java.io.Serializable;

import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class RvKcpComparision extends BaseBean implements Serializable {

	private String kcpDealHisSeq; // kcp거래이력순번
	private String kcpDealNum; // kcp거래번호
	private String pymntDtm; // 거래일시
	private String pymntOrgDtm; // 원거래일시
	private String lgdDtm; // 입금/취소처리일시
	private String lgdBankDtm; // 은행입금/취소일시
	private String pymntStatCode; // 거래구분
	private String orderNum; // 주문번호
	private String pymntAmt; // 결제금액(입금/취소금액)
	private String pymntCharge; // 수수료
	private String freeCharge; // 무이자수수료
	private String pymntSurTax; // 부가세
	private String accountsAmt; // 정산금액
	private String deadlineDtm; // 마감일자
	private String receiveMonth; // 수납월
	private String accountsDtm; // 정산일자
	private String escrowCharge; // 에스크로수수료
	private String escrowSurtax; // 에스크로부가세
	private String bankCode; // 은행코드
	private String lgdAccountnum; // 가상계좌번호
	private String lgdCasseqno; // 입금처리고유번호
	private String lgdPayer; // 입금인성명
	private String istmtCount; // 할부개월수
	private String prmtNum; // 승인번호
	private String crdtCardCode; // 매입카드사코드
	private String merchantName; // 가맹점번호
	private String pymntMeans; // 결제수단
	private String rgstDtm; // 데이터입력일시
	private String orgOrderNo; // 원주문번호
	private String mapngYn; // 매핑여부
	private String sendParmDate; // 요청일자

	private String comparisonType; // 주문, 기프트카드, 클레임, AS구분

	private String srchType; // 검택조건
	private String searchWord; // 검색어
	private String siteNo; // 사이트 번호

	private String fromDate; // 날짜 조건
	private String toDate; // 날짜 조건

	private int totalOrderPay; // 결제금액
	private int totalClaimPay; // 결제금액
	private int totalGiftcardPay; // 결제금액
	private int totalAsPay; // 결제금액
	private int mapngPay; // 매칭금액
	private int notMapngPay; // 미매칭금액

}
