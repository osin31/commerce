package kr.co.abcmart.bo.cmm.model.master;

import kr.co.abcmart.bo.cmm.model.master.base.BaseCmMembershipPolicy;
import kr.co.abcmart.common.exception.ValidatorException;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.common.validation.Validator;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@ToString
public class CmMembershipPolicy extends BaseCmMembershipPolicy implements Validator {
	private String cpnName;	// 쿠폰명
	private String cpnNo;	//쿸폰번호
	private String benefitIndex;// 화면'추가'버튼 인덱스
	private String endDate;	// 검색 종료날짜
	private String codeDtlName;// 회원등급명 
	private String mbshpGradeName;// 회원멤버십 코드
	private String freeGiftSeq;// 사은품 번호
	private String addPointSeq;	// 추가 포인트 번호
	private String loginId;	// 로그인ID
	private String adminName;	// 관리자명
	
	// 관리자 정보 마스킹
	public String getAdminInfo() {
		return UtilsText.concat(UtilsMasking.loginId(getLoginId()), Const.L_PAREN,
				UtilsMasking.userName(getAdminName()), Const.R_PAREN);
	}
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 멤버십정책상세순번
     */
	private int mbshpPlcyDtlSeq;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 지급수량
     */
	private int payQty;
	
	/**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 멤버십등급코드
     */
	private String mbshpGradeCode;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 멤버십정책상세코드
     */
	private String mbshpPlcyDtlCode;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 추가포인트
     */
	private int addPointAmt;
	
	/**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 정책상세사은품순번
     */
	private int plcyDtlFreeGiftSeq;
	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : 상품번호
     */
	private String prdtNo;
	
	@Override
	public void validate() throws ValidatorException {
		if (getPlcyStartYmd()==null) {
			validationMessage("cmm.valid.policy.plcyStartYmd");             	// 적용시작날짜
		}else if (getPlcyEndYmd()==null) {
			validationMessage("cmm.valid.policy.plcyEndYmd");             		// 적용종료날짜
		}else if (getMemberJoinPointAmt()==null) {
			validationMessage("cmm.valid.policy.memberJoinPointAmt");             // 회원가입 포인트
		}else if (getFirstBuyPointAmt()==null) {
			validationMessage("cmm.valid.policy.firstBuyPointAmt");               // 첫구매 포인트
		}else if (getBuyPointRate()==null) {
			validationMessage("cmm.valid.policy.buyPointRate");                   // 구매 포인트율
		}else if (getAnnvrFirstBuyPointMultCount()==null) {
			validationMessage("cmm.valid.policy.annvrFirstBuyPointMultCount");    // 기념일 첫구매 포인트 배수
		}else if (getMinUsePointAmt()==null) {
			validationMessage("cmm.valid.policy.minUsePointAmt");                 // 최소 사용포인트
		}else if (getPointExtnYearCount()==null) {
			validationMessage("cmm.valid.policy.pointExtnYearCount");             // 포인트 자동 소멸 년 수
		}else if (getPrdtRvwPointAmt()==null) {
			validationMessage("cmm.valid.policy.prdtRvwPointAmt");                // 일반후기 포인트
		}else if (getPhotoPrdtRvwPointAmt()==null) {
			validationMessage("cmm.valid.policy.photoPrdtRvwPointAmt");           // 포토후기 포인트
		}else if (getMonthBestRvwCount()==null) {
			validationMessage("cmm.valid.policy.monthBestRvwCount");              // 월별 베스트 후기 수
		}else if (getMonthBestRvwPointAmt()==null) {
			validationMessage("cmm.valid.policy.monthBestRvwPointAmt");           // 월별 베스트 후기 포인트
		}else if (getOnlnSaveTermCount()==null) {
			validationMessage("cmm.valid.policy.onlnSaveTermCount");              // 온라인 배송완료 일수
		}else if (getOflnSaveTermCount()==null) {
			validationMessage("cmm.valid.policy.oflnSaveTermCount");              // 오프라인 배송완료 일수
		}else if (getBirthDayCpnNo()==null) {
			validationMessage("cmm.valid.policy.birthDayCpnNo");                  // 생일쿠폰
		}else if (getAnnvrFirstBuyPointPayYn()==null) {
			validationMessage("cmm.valid.policy.annvrFirstBuyPointPayYn");        // 기념일 첫구매 사용여부
		}
	}
}
