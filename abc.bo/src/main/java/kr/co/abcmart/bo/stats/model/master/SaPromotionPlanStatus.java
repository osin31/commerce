package kr.co.abcmart.bo.stats.model.master;

import java.io.Serializable;

import kr.co.abcmart.common.bean.BaseBean;
import lombok.Data;

@Data
public class SaPromotionPlanStatus extends BaseBean implements Serializable {

	/**
	 * 수량 총 합계
	 */
	private int orderQtyAll;

	/**
	 * 정상가 총 합계
	 */
	private long prdtNormalAmtAll;

	/**
	 * 결제금액 총 합계
	 */
	private long sellAmtAll;

	/**
	 * 온라인 수량 합계
	 */
	private int orderQty10000;

	/**
	 * 온라인 정상가 합계
	 */
	private long prdtNormalAmt10000;

	/**
	 * 온라인 결제금액 합계
	 */
	private long sellAmt10000;

	/**
	 * 스마트 수량 합계
	 */
	private int orderQty10001;

	/**
	 * 스마트 정상가 합계
	 */
	private long prdtNormalAmt10001;

	/**
	 * 스마트 결제금액 합계
	 */
	private long sellAmt10001;

	/**
	 * 매장 수량 합계
	 */
	private int orderQty10002;

	/**
	 * 매장 정상가 합계
	 */
	private long prdtNormalAmt10002;

	/**
	 * 매장 결제금액 합계
	 */
	private long sellAmt10002;

	/**
	 * 입점사 수량 합계
	 */
	private int orderQty10003;

	/**
	 * 입점사 정상가 합계
	 */
	private long prdtNormalAmt10003;

	/**
	 * 입점사 결제금액 합계
	 */
	private long sellAmt10003;

	private String hourTitle;
	// 다족구매 족수별 합계(수량, 에누리금액, 결제금액)
	private int dscntAmtAll;
	private int pymntAmtAll;

	// 2족
	private int twoQty;
	private int twoDscnt;
	private int twoPymnt;
	// 3족
	private int threeQty;
	private int threeDscnt;
	private int threePymnt;
	// 4족
	private int fourQty;
	private int fourDscnt;
	private int fourPymnt;
	// 5족
	private int fiveQty;
	private int fiveDscnt;
	private int fivePymnt;
	// 6족 이상
	private int sixMoreQty;
	private int sixMoreDscnt;
	private int sixMorePymnt;

	// 검색 타입
	private String searchType;
	// 기획전 아이디
	private String planningId;
	// 기획전명
	private String planningName;
	// 기획전 분류
	private String planningType;

	// 프로모션 번호
	private String promoNo;
	// 프로모션명
	private String promoName;
	// 프로모션 타입
	private String promoType;

	// 브랜드명
	private String brandName;
	// 카테고리명
	private String ctgrName;
	// style 정보
	private String styleInfo;
	// color 정보
	private String prdtColorInfo;
	// 상품정보
	private String prdtNoName;

	// 순번
	private String selectSeq;

}