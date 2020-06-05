package kr.co.abcmart.bo.cmm.model.master.base;

import lombok.Data;
import java.io.Serializable;
import kr.co.abcmart.common.bean.BaseBean;

@Data
public class BaseCmMembershipPolicy extends BaseBean implements Serializable {

    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 정책순번
     */
	private java.lang.Integer plcySeq;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 정책시작일
     */
	private java.sql.Timestamp plcyStartYmd;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 정책종료일
     */
	private java.sql.Timestamp plcyEndYmd;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 회원가입포인트
     */
	private java.lang.Integer memberJoinPointAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 첫구매포인트
     */
	private java.lang.Integer firstBuyPointAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 구매포인트율
     */
	private java.lang.Short buyPointRate;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 기념일첫구매포인트지급여부
     */
	private String annvrFirstBuyPointPayYn;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 기념일첫구매포인트배수
     */
	private java.lang.Short annvrFirstBuyPointMultCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 최소사용포인트
     */
	private java.lang.Integer minUsePointAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 포인트소멸년수
     */
	private java.lang.Integer pointExtnYearCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 상품후기포인트
     */
	private java.lang.Integer prdtRvwPointAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 포토상품후기포인트
     */
	private java.lang.Integer photoPrdtRvwPointAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 월별베스트후기수
     */
	private java.lang.Integer monthBestRvwCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 월별베스트후기포인트
     */
	private java.lang.Integer monthBestRvwPointAmt;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 온라인적립기간
     */
	private java.lang.Integer onlnSaveTermCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 오프라인적립기간
     */
	private java.lang.Integer oflnSaveTermCount;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 생일쿠폰번호
     */
	private String birthDayCpnNo;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 사용여부
     */
	private String useYn;
	
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
