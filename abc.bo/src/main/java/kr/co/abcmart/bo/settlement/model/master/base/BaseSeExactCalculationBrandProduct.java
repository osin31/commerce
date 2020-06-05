package kr.co.abcmart.bo.settlement.model.master.base;

import lombok.Data;
import java.io.Serializable;
import kr.co.abcmart.common.bean.BaseBean;

@Data
public class BaseSeExactCalculationBrandProduct extends BaseBean implements Serializable {

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 정산순번
     */
	private java.lang.Integer excclcSeq;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 브랜드번호
     */
	private String brandNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문번호
     */
	private String orderNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문상품순번
     */
	private java.lang.Integer orderPrdtSeq;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문배송이력순번
     */
	private java.lang.Short orderDlvyHistSeq;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 브랜드명
     */
	private String brandName;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 매출취소구분
     */
	private String salesCnclGbnType;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 상품번호
     */
	private String prdtNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 상품명
     */
	private String prdtName;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 영문상품명
     */
	private String engPrdtName;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 옵션명
     */
	private String optnName;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 스타일정보
     */
	private String styleInfo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 상품색상코드
     */
	private String prdtColorCode;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 업체상품번호
     */
	private String vndrPrdtNoText;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 사이트번호
     */
	private String siteNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 채널번호
     */
	private String chnnlNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 무료배송여부
     */
	private String freeDlvyYn;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 주문수량
     */
	private java.lang.Integer orderQty;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 상품정상가
     */
	private java.lang.Integer prdtNormalAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 상품판매가
     */
	private java.lang.Integer prdtSellAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 옵션추가금액
     */
	private java.lang.Integer optnAddAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 판매가
     */
	private java.lang.Integer sellAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 임직원할인율
     */
	private java.lang.Short empDscntRate;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 임직원가
     */
	private java.lang.Integer empAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 할인금액합계
     */
	private java.lang.Integer totalDscntAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 업체수수료율
     */
	private java.lang.Short vndrCmsnRate;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 판매수수료금액
     */
	private java.lang.Integer vndrCmsnAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 구매확정일시
     */
	private java.sql.Timestamp buyDcsnDtm;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 프로모션할인금액
     */
	private java.lang.Integer promoDscntAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 프로모션수수료금액
     */
	private java.lang.Integer promoCmsnAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 쿠폰사용여부
     */
	private String cpnUseYn;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 쿠폰번호
     */
	private String cpnNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 쿠폰할인금액
     */
	private java.lang.Integer cpnDscntAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 쿠폰분담율
     */
	private java.lang.Short cpnShareRate;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 쿠폰분담금액
     */
	private java.lang.Integer cpnShareAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 플러스쿠폰번호
     */
	private String plusCpnNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 플러스쿠폰할인금액
     */
	private java.lang.Integer plusCpnDscntAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 플러스쿠폰분담율
     */
	private java.lang.Short plusCpnShareRate;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 플러스쿠폰분담금액
     */
	private java.lang.Integer plusCpnShareAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 구매포인트적립율
     */
	private java.lang.Short buyPointSaveRate;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 구매포인트적립금액
     */
	private java.lang.Integer buyPointSaveAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 정산금액
     */
	private java.lang.Integer excclcAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 지연일수
     */
	private java.lang.Short delayDayCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 페널티부과비율
     */
	private java.lang.Short penltyLevyRate;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 패널티금액
     */
	private java.lang.Integer penltyAmt;
	
}
