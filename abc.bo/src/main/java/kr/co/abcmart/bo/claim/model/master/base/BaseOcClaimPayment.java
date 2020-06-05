package kr.co.abcmart.bo.claim.model.master.base;

import lombok.Data;
import java.io.Serializable;
import kr.co.abcmart.common.bean.BaseBean;

@Data
public class BaseOcClaimPayment extends BaseBean implements Serializable {

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 클레임번호
     */
	private String clmNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 클레임결제순번
     */
	private java.lang.Short clmPymntSeq;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 환수환불구분
     */
	private String redempRfndGbnType;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 결제일시
     */
	private java.sql.Timestamp pymntDtm;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 디바이스코드
     */
	private String deviceCode;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주결제수단여부
     */
	private String mainPymntMeansYn;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 결제수단코드
     */
	private String pymntMeansCode;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 결제업체코드
     */
	private String pymntVndrCode;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 결제기관코드
     */
	private String pymntOrganCodeText;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 결제기관명
     */
	private String pymntOrganName;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 결제기관번호
     */
	private String pymntOrganNoText;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 무이자여부
     */
	private String intrstFreeYn;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 할부기간
     */
	private java.lang.Short instmtTermCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 카드구분
     */
	private String cardGbnType;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 카드유형
     */
	private String cardType;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 결제승인번호
     */
	private String pymntAprvNoText;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 결제예정금액
     */
	private java.lang.Integer pymntTodoAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 결제금액
     */
	private java.lang.Integer pymntAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 카드부분취소가능여부
     */
	private String cardPartCnclPsbltYn;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 현금영수증발급여부
     */
	private String cashRcptIssueYn;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 현금영수증승인번호
     */
	private String cashRcptAprvNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 현금영수증거래번호
     */
	private String cashRcptDealNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 에스크로적용여부
     */
	private String escrApplyYn;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 에스크로승인번호
     */
	private String escrAprvNoText;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 은행코드
     */
	private String bankCode;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 계좌번호
     */
	private String acntNoText;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 예금주명
     */
	private String acntHldrName;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 계좌인증여부
     */
	private String acntCrtfcYn;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 가상계좌발급일시
     */
	private java.sql.Timestamp vrtlAcntIssueDtm;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 가상계좌만료일시
     */
	private java.sql.Timestamp vrtlAcntExprDtm;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 상품권PIN번호
     */
	private String giftCardPinNoText;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 환수환불메모
     */
	private String redempRfndMemoText;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 처리불가여부
     */
	private String procImpsbltYn;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 처리불가사유
     */
	private String procImpsbltRsnText;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 처리불가완료일시
     */
	private java.sql.Timestamp procImpsbltCmlptDtm;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 환수환불처리자번호
     */
	private String redempRfndOpetrNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 환수환불처리일시
     */
	private java.sql.Timestamp redempRfndOpetrDtm;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 자사처리대상여부
     */
	private String mmnyProcTrgtYn;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 발생사유코드
     */
	private String ocrncRsnCode;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 결제상태코드
     */
	private String pymntStatCode;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 응답코드
     */
	private String rspnsCodeText;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 응답메시지
     */
	private String rspnsMesgText;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 결제로그
     */
	private String pymntLogInfo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 이력구분
     */
	private String histGbnType;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 업체번호
     */
	private String vndrNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 환수금임의처리여부
     */
	private String redempAmtRndmProcYn;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 등록자번호
     */
	private String rgsterNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 등록일시
     */
	private java.sql.Timestamp rgstDtm;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 수정자번호
     */
	private String moderNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 수정일시
     */
	private java.sql.Timestamp modDtm;
	
}
