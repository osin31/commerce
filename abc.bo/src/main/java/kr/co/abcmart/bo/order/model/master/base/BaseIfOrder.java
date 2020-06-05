package kr.co.abcmart.bo.order.model.master.base;

import lombok.Data;
import java.io.Serializable;
import kr.co.abcmart.common.bean.BaseBean;

@Data
public class BaseIfOrder extends BaseBean implements Serializable {

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : B코드
     */
	private String cbcd;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 매장코드
     */
	private String maejangcd;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문번호
     */
	private String ordno;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 서브키
     */
	private String itemsno;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문구분(1:신규주문 2:교환주문 3:맞교환주문)
     */
	private String ordtype;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문일시
     */
	private java.sql.Timestamp orddt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 합포장기준(0:주문번호 ...)
     */
	private String sumpakingrule;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문접수차수
     */
	private String orddp;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 원주문번호주문
     */
	private String oldordno;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문자
     */
	private String nameorder;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문자휴대폰
     */
	private String mobileorder;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문자연락처
     */
	private String phoneorder;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 회원구분(1:회원 2:비회원)
     */
	private String memberyn;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 회원ID
     */
	private String memberid;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 수령자
     */
	private String namereceiver;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 수령자휴대폰
     */
	private String mobilereceiver;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 수령자연락처
     */
	private String phonereceiver;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 우편번호
     */
	private String zipcode;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주소1
     */
	private String address;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주소2
     */
	private String addressdetail;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 이메일
     */
	private String email;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 배송메시지
     */
	private String delimsg;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문자메시지
     */
	private String memo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 메모
     */
	private String adminnmemo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 몰명
     */
	private String mallname;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 몰주문번호
     */
	private String mallordno;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 지불방법
     */
	private String settlekind;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 판매금액
     */
	private java.lang.Integer prnsettleprice;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 결제금액
     */
	private java.lang.Integer settleprice;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문별할인액1
     */
	private java.lang.Integer enuri;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문별할인액2
     */
	private java.lang.Integer memberdc;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문별포인트사용1
     */
	private java.lang.Integer emoney1;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문별포인트사용2
     */
	private java.lang.Integer emoney2;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문별적립금사용1
     */
	private java.lang.Integer reserve1;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문별적립금사용2
     */
	private java.lang.Integer reserve2;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문별쿠폰사용1
     */
	private java.lang.Integer coupon1;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문별쿠폰번호1
     */
	private String couponno1;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문별쿠폰사용2
     */
	private java.lang.Integer coupon2;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문별쿠폰번호2
     */
	private String couponno2;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 배송료
     */
	private java.lang.Integer delivery;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 배송료구분(1:착불 2:선불 3:자사착불)
     */
	private String delitype;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 결제은행
     */
	private String bankcode;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 계좌번호
     */
	private String bankaccount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 입금자명
     */
	private String banksender;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 가상계좌은행
     */
	private String vbank;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 가상계좌번호
     */
	private String vacount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 입금일
     */
	private String cdt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 몰상품코드
     */
	private String goodsno;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 몰상품명
     */
	private String goodsnm;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 옵션1
     */
	private String opt1;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 옵션2
     */
	private String opt2;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 옵션3
     */
	private String addopt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 바코드
     */
	private String bacode;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 과세구분(0:비과세 1:과세)
     */
	private String taxyn;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 수량
     */
	private java.lang.Integer ea;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 판매단가
     */
	private java.lang.Integer price;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 공급단가
     */
	private java.lang.Integer supply;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 합포장키
     */
	private java.lang.Integer sumpakingkey;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 부분배송구분(0:불가 1:가능)
     */
	private java.lang.Boolean partdelitype;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 부분배송여부(0:전체배송 1:부분배송)
     */
	private java.lang.Boolean partdeliyn;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 상품풀코드
     */
	private String sangpumfullcd;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 택배사코드
     */
	private String deliverycode;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 운송장번호
     */
	private String deliveryno;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 배송일
     */
	private String ddt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 자사부담배송비
     */
	private java.lang.Integer selfdelivery;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 자사부담배송사용
     */
	private String selfdeliveryrs;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 구매확정일
     */
	private String confirmdt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 구매확정주체(0:확정전 1:소비자확정 2:관리자확정)
     */
	private String confirm;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 에스크로구분
     */
	private java.lang.Boolean escrowyn;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 에스크로상태
     */
	private String escrowconfirm;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 에스크로번호
     */
	private String escrowno;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 현금영수증번호
     */
	private String cashreceipt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 환불은행
     */
	private String cancelbankcode;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 환불계좌번호
     */
	private String cancelbankaccount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 환불예금주
     */
	private String cancelbackuser;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 환불일자
     */
	private String cancelccdt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 환불비용
     */
	private java.lang.Integer cancelrfee;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 작업상태구분(P1)
     */
	private String ordstatus;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 패킹일시
     */
	private java.sql.Timestamp packingdt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 인계일시
     */
	private java.sql.Timestamp receivedt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 택배사연동구분(P4)
     */
	private String deliverytype;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 사전취소구분(P2)
     */
	private String bfcanceltype;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 회수요청구분(P3)
     */
	private String rtreqtype;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 출고확정구분(P5)
     */
	private String outconfirmtype;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 반품처리구분(P6)
     */
	private String prgstatus;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 등록일시
     */
	private java.sql.Timestamp regdate;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 처리일시
     */
	private java.sql.Timestamp condate;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 오류상태(0:정상 1:오류)
     */
	private String errorstatus;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 작업상태(0:MALL등록 1:WMS등록 2:WMS수신 3:MALL반영)
     */
	private String workdiv;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 안심키
     */
	private String safeKey;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : ARS 수신여부 (0:미수신,1:수신)
     */
	private String arsworkdiv;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : ARS 작업 진행 상태 ( 03:상품준비중,04:상품출고(주문취소불가),05:출고완료,99:판매완료)
     */
	private String arsstatus;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : ARS 수신일시 ( ARS에서 주문정보 수신한 시간)
     */
	private java.sql.Timestamp arsrecvdate;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 에러구분(ARS)
     */
	private String arserrorstatus;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 1차 매장코드(온라인,INET에서지정)
     */
	private String oriStoreCd;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 발송확정 매장코드 (매장에서 확정)
     */
	private String confStoreCd;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 택배사 배송지코드(대한통운)
     */
	private String deleverystorecd;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 배송지변경, 기본값:0,배송지변경:1,ARS수신완료:2
     */
	private String addresschange;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 고객 결제완료 여부, 결제완료:1
     */
	private String paymentstatus;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 무통장입금완료여부 : 무통장입금완료:1, ARS수신완료:2
     */
	private String moneypaymentcomplete;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 지역구분코드
     */
	private String offZoneAreaFlag;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 지역코드
     */
	private String offZoneAreaCd;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 
     */
	private java.sql.Timestamp sendbackreqdate;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 
     */
	private java.sql.Timestamp sendbackresdate;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 
     */
	private String sendbackdeliveryno;
	
}
