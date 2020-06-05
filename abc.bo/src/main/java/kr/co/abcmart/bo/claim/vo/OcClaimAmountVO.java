package kr.co.abcmart.bo.claim.vo;

import java.util.List;

import kr.co.abcmart.bo.claim.model.master.OcClaimPayment;
import kr.co.abcmart.bo.claim.model.master.OcClaimProduct;
import kr.co.abcmart.bo.claim.model.master.OcClaimProductApplyPromotion;
import kr.co.abcmart.bo.order.model.master.OcOrderProduct;
import kr.co.abcmart.bo.order.model.master.OcOrderUseCoupon;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 클레임 금액 계산 관련 정보
 * @FileName : OcClaimAmountVO.java
 * @Project : abc.bo
 * @Date : 2019. 4. 10.
 * @Author : KTH
 */
@Slf4j
@Data
public class OcClaimAmountVO {
	private String clmNo;
	private String orgOrderNo;
	private String orderNo;

	/*
	 * 주문기준 금액
	 */
	private java.lang.Integer dscntAmt; // 할인금액
	private java.lang.Integer prdtNormalAmt; // 상품정상가
	private java.lang.Integer prdtSellAmt; // 옵션추가금액
	private java.lang.Integer optnAddAmt; // 옵션추가금액
	private java.lang.Integer sellAmt; // 판매가
	private java.lang.Short empDscntRate; // 임직원할인율
	private java.lang.Integer pointUseAmt; // 포인트사용액
	private java.lang.Integer eventPointUseAmt; // 이벤트포인트사용액
	private java.lang.Integer dlvyAmt; // 배송비
	private java.lang.Integer pymntTodoAmt; // 결제예정금액
	private java.lang.Integer pymntAmt; // 결제금액
	private java.lang.Integer cnclAmt; // 취소금액

	private java.lang.Integer totalDscntAmt; // 할인금액합계
	private java.lang.Integer totalNormalAmt; // 정상가총액
	private java.lang.Integer totalSellAmt; // 판매가총액
	private java.lang.Integer totalPromoDscntAmt; // 프로모션할인총액
	private java.lang.Integer totalCpnDscntAmt; // 쿠폰할인총액
	private java.lang.Integer totalEmpDscntAmt; // 임직원할인총액

	private java.lang.Integer totalOrderPymntAmt; // 주문기준 총 결제금액(=최초결제금액)
	private java.lang.Integer totalOrderCnclAmt; // 주문기준 총 취소금액

	/*
	 * 클레임 계산
	 */
	private java.lang.Integer expectCnclAmt; // 취소예정금액(=클레임 금액)
	private java.lang.Integer totalAccumulatedCnclAmt; // 총 누적 취소금액
	private java.lang.Integer totalCnclAbleRemainAmt; // 총 잔여 취소가능금액

	private java.lang.Integer eventPointCnclAmt; // 이벤트포인트 취소금
	private java.lang.Integer mainCnclAmt; // 주결제 취소금
	private java.lang.Integer giftCnclAmt; // 기프트 취소금
	private java.lang.Integer pointCnclAmt; // 구매포인트 취소금

	private java.lang.Integer eventPointCnclAbleRemainAmt; // 이벤트포인트 취소 가능 잔여금액
	private java.lang.Integer mainCnclAbleRemainAmt; // 주결제 취소 가능 잔여금액
	private java.lang.Integer giftCnclAbleRemainAmt; // 기프트 취소 가능 잔여금액
	private java.lang.Integer pointCnclAbleRemainAmt; // 구매포인트 취소 가능 잔여금액

	private java.lang.Integer saveTodoPoint; // 적립예정포인트
	private java.lang.Integer cnclSaveTodoPoint; // 취소적립예정포인트

	private java.lang.Integer redempAmtByEventPoint; // 환수이벤트포인트(적립포인트 취소 발생)
	private java.lang.Integer redempAmtByMultiBuy; // 환수프로모션비(다족구매로 인해 발생)
	private java.lang.Integer refundAmtByDlvyProduct; // 환불배송비
	private java.lang.Integer redempAmtByFreeDlvyProduct; // 환수배송비(무료배송 상품 취소 시 발생)
	private java.lang.Integer previousRedempAmtDlvyProduct; // 이전 환수된 배송비 발생금(환수배송비)
	private java.lang.Integer addMultiBuyDifferAmt; // 다족구매 리오더 매출로 인한 실제 취소금과의 차액

	private java.lang.Integer redempAmt; // 환수금액
	private java.lang.Integer rfndAmt; // 환불금액

	private java.lang.Integer totalCnclAmt; // 총취소금액
	private java.lang.Integer totalRfndAmt; // 총환불금액
	private java.lang.Integer totalRedempAmt; // 총환수금액

	private java.lang.Integer refundCnclAmt; // 실제 취소로 인해 환불될 금액

	private java.lang.Integer claimDlvyAmt; // 클레임배송비

	Integer orgOrderSavePoint; // 원 주문 기준 적립포인트
	Integer variationSavePoint; // 현재 클레임 증감 적립포인트

	OcOrderUseCoupon OrderDoubleDscntCpnInfo; // 주문 더블적립사용쿠폰

	/*
	 * 목록 정보
	 */
	List<OcOrderProduct> orgOrderProductList; // 원 주문 상품 목록
	List<OcOrderProduct> reOrderProductList; // 리오더 상품 목록
	List<OcOrderProduct> reOrderProductSalesList; // 리오더 매출 상품 목록
	List<OcOrderProduct> cancelDlvyProductList; // 취소시킬(환불대상) 배송비 상품
	List<OcOrderProduct> redempDlvyProductList; // 환수할 배송비 상품
	List<OcOrderProduct> previousRedempDlvyProductList; // 이전 환수된 배송비 상품
	List<OcOrderUseCoupon> orderUseCouponList; // 원 주문 상품 쿠폰 사용 정보
	// List<OcClaimPayment> claimPaymentList; // 클레임 결제정보 목록
	List<OcClaimProduct> orderAllClaimProductList; // 원 주문에 걸린 모든 클레임
	List<OcClaimProduct> thisTimeClaimProductList; // 현재클레임목록
	List<OcClaimProduct> finishedClaimProductList; // 기존 완료 클레임 상품 목록
	List<OcOrderProduct> notClaimOrderProductList; // 클레임이 걸려있지 않은 주문 상품 목록
	List<OcClaimPayment> orderClaimPaymentList; // 주문/클레임 결제 금액 조인 목록 - 클레임 취소 누적 금액 포함
	List<OcClaimPayment> vndrRedempDlvyPaymentList; // 환수 주문배송비 결제 목록(이력용 - 업체별)
	List<OcClaimPayment> previousRedempDlvyPaymentList; // 이전 환수된 배송비 결제 목록
	List<OcClaimPayment> redempMultiBuyPaymentList; // 환수 다족구매 결제 목록
	List<OcClaimPayment> addMultiBuyDifferPaymentList; // 다족구매 리오더 매출과 실제 결제금액 차이가 발생한 금액 목록(환불개념)
	List<OcClaimPayment> vndrRfndDlvyList; // 업체별 환불 주문배송비
	List<OcOrderProduct> beforeAdjustOrderProductByMultiBuyList; // 다족구매로 인한 금액 재조정 되기 이전의 주문상품 목록
	List<OcOrderProduct> adjustedOrderProductByMultiBuyList; // 다족구매로 인한 금액 재조정 된 주문상품 목록
	List<OcClaimProductApplyPromotion> adjustedClaimProductApplyPromotionList; // 다족구매로 인한 금액 재조정 된 주문상품 프로모션 변경 목록

	/*************************************************/
	OcClaimPayment mainPayment;
	OcClaimPayment giftPayment;
	OcClaimPayment pointPayment;
	OcClaimPayment eventPointPayment;

	private String orderUserId;
	private boolean allCancelYn;
	private boolean remainAllCancelYn;
	private boolean cancelByForce = false; // 초기 고정 값 false
	private boolean checkAllYn;
	private boolean escrSendYn;
	private boolean refundOccurrence;
	private boolean reOrderRegistPossible = true; // 초기 고정 값 true
	private String pymntStatCode;

	String isDlvyAmtAllCancelYn;// 반품 배송비 취소완료여부
	String resultMessage;

	OcClaimPayment mainCancelResult; // 주결제 취소 결과

}
