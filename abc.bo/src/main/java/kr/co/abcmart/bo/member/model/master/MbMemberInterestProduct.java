package kr.co.abcmart.bo.member.model.master;

import kr.co.abcmart.bo.member.model.master.base.BaseMbMemberInterestProduct;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMasking;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MbMemberInterestProduct extends BaseMbMemberInterestProduct {

	/* 기간검색시작 */
	private String startYmd;
	/* 기간검색종료 */
	private String endYmd;
	/* 회원검색타입 */
	private String memberSearchType;
	/* 회원검색어 */
	private String memberSearchWord;
	/* 상품검색타입 */
	private String productSearchType;
	/* 상품검색어 */
	private String productSearchWord;
	/* 판매상태코드 */
	private String[] sellStatCodeArr;
	/* 처리상태코드 */
	private String[] wrhsAlertStatCodeArr;

	/* 회원ID */
	private String loginId;
	/* 회원이름 */
	private String memberName;
	/* 상품명 */
	private String prdtName;
	/* 상품전시여부 */
	private String dispYn;
	/* 브랜드명 */
	private String brandName;
	/* 옵션명 */
	private String optnName;
	/* 추가옵션 */
	private String addOptn2Text;
	/* 정상가 */
	private String normalAmt;
	/* 판매가 */
	private String sellAmt;
	/* 할인율 */
	private String dscntRate;
	/* 주문가능수량 */
	private String orderPsbltQty;
	/* 매출순번 */
	private String salesSeq;
	
	/* 옵션 전시여부*/
	private String useYn;
	/* 옵션 전시여부 표기명 */
	private String useYnStr;

	/* 회원정보 */
	// 회원 ID 마스킹 처리
	public String getLoginIdLabel() {
		return doMaskingOnNoCondition(getLoginId());
	}

	// 회원명 마스킹 처리
	public String getMemberNameLabel() {
		return doMaskingOnNoCondition(getMemberName());
	}

	// 마스킹이 필요한지 확인 | 필요-true 불필요-false
	public boolean isNeedMasking() {
		if (Const.BOOLEAN_TRUE.equals(LoginManager.getUserDetails().getMemberInfoMgmtYn())) { // 권한 확인
			return false;
		}

		return true;
	}

	// 마스킹 처리
	public String doMasking(String str) {

		// 하나라도 비어있다면 "" 반환
		if (UtilsObject.isEmpty(str)) {
			return "";
		}

		if (isNeedMasking()) { // 마스킹 필요
			return UtilsMasking.userName(str);
		} else { // 마스킹 불필요
			return str;
		}
	}

	// 마스킹 처리
	public String doMaskingOnNoCondition(String str) {

		// 하나라도 비어있다면 "" 반환
		if (UtilsObject.isEmpty(str)) {
			return "";
		}

		return UtilsMasking.userName(str);
	}

}