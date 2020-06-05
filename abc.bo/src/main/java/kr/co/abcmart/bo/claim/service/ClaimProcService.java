package kr.co.abcmart.bo.claim.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.abcmart.bo.claim.model.master.OcClaim;
import kr.co.abcmart.bo.claim.model.master.OcClaimPayment;
import kr.co.abcmart.bo.claim.model.master.OcClaimProduct;
import kr.co.abcmart.bo.claim.model.master.OcClaimProductApplyPromotion;
import kr.co.abcmart.bo.claim.model.master.OcClaimStatusHistory;
import kr.co.abcmart.bo.claim.repository.master.OcClaimDao;
import kr.co.abcmart.bo.claim.repository.master.OcClaimPaymentDao;
import kr.co.abcmart.bo.claim.repository.master.OcClaimProductApplyPromotionDao;
import kr.co.abcmart.bo.claim.repository.master.OcClaimProductDao;
import kr.co.abcmart.bo.claim.repository.master.OcClaimStatusHistoryDao;
import kr.co.abcmart.bo.claim.vo.OcClaimAmountVO;
import kr.co.abcmart.bo.claim.vo.OcClaimPromoVO;
import kr.co.abcmart.bo.event.service.EventService;
import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.model.master.MbMemberCoupon;
import kr.co.abcmart.bo.member.model.master.MbMemberPoint;
import kr.co.abcmart.bo.member.repository.master.MbMemberCouponDao;
import kr.co.abcmart.bo.member.service.MemberPointService;
import kr.co.abcmart.bo.order.model.master.OcCashReceipt;
import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.bo.order.model.master.OcOrderPayment;
import kr.co.abcmart.bo.order.model.master.OcOrderProduct;
import kr.co.abcmart.bo.order.model.master.OcOrderProductApplyPromotion;
import kr.co.abcmart.bo.order.model.master.OcOrderProductHistory;
import kr.co.abcmart.bo.order.model.master.OcOrderUseCoupon;
import kr.co.abcmart.bo.order.repository.master.OcCashReceiptDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderPaymentDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderProductApplyPromotionDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderProductDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderProductHistoryDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderUseCouponDao;
import kr.co.abcmart.bo.order.service.OrderService;
import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.repository.master.PdProductDao;
import kr.co.abcmart.bo.promotion.model.master.PrCoupon;
import kr.co.abcmart.bo.promotion.model.master.PrPromotionMultiBuyDiscount;
import kr.co.abcmart.bo.promotion.repository.master.PrCouponDao;
import kr.co.abcmart.bo.promotion.repository.master.PrCouponPaperNumberDao;
import kr.co.abcmart.bo.promotion.repository.master.PrPromotionMultiBuyDiscountDao;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.repository.master.SySiteDao;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.repository.master.VdVendorDao;
import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.interfaces.module.member.model.PointUse;
import kr.co.abcmart.interfaces.module.payment.PaymentEntrance;
import kr.co.abcmart.interfaces.module.payment.base.PaymentConst;
import kr.co.abcmart.interfaces.module.payment.base.PaymentException;
import kr.co.abcmart.interfaces.module.payment.base.PaymentResult;
import kr.co.abcmart.interfaces.module.payment.base.model.PaymentInfo;
import kr.co.abcmart.interfaces.module.payment.kakao.model.KakaoPaymentCancel;
import kr.co.abcmart.interfaces.module.payment.kcp.model.KcpPaymentCancel;
import kr.co.abcmart.interfaces.module.payment.kcp.model.KcpPaymentRefund;
import kr.co.abcmart.interfaces.module.payment.naver.model.NaverPaymentCancel;
import kr.co.abcmart.interfaces.module.payment.nice.NiceGiftService;
import kr.co.abcmart.interfaces.module.payment.nice.model.ChargeRequest;
import kr.co.abcmart.interfaces.module.payment.nice.model.ChargeResponse;
import kr.co.abcmart.interfaces.module.payment.nice.model.CommNiceRes;
import kr.co.abcmart.interfaces.zinterfacesdb.service.InterfacesClaimService;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 클레임 처리(취소,교환,반품)와 관련된 기능 및 비즈니스 프로세스 관련 Service
 * @FileName : ClaimProcService.java
 * @Project : abc.bo
 * @Date : 2019. 2. 20.
 * @Author : KTH
 */
@Slf4j
@Service
public class ClaimProcService {
	@Autowired
	private PaymentEntrance paymentEntrance;

	@Autowired
	private OcClaimProductDao ocClaimProductDao;

	@Autowired
	private OcClaimPaymentDao ocClaimPaymentDao;

	@Autowired
	private OcOrderPaymentDao ocOrderPaymentDao;

	@Autowired
	private OcOrderDao ocOrderDao;

	@Autowired
	private OcOrderProductDao ocOrderProductDao;

	@Autowired
	private OcOrderUseCouponDao ocOrderUseCouponDao;

	@Autowired
	private OcOrderProductApplyPromotionDao ocOrderProductApplyPromotionDao;

	@Autowired
	private VdVendorDao vdVendorDao;

	@Autowired
	private SySiteDao sySiteDao;

	@Autowired
	private OrderService orderService;

	@Autowired
	private PrCouponDao prCouponDao;

	@Autowired
	private MbMemberCouponDao mbMemberCouponDao;

	@Autowired
	MemberPointService memberPointService;

	@Autowired
	InterfacesClaimService interfacesClaimService;

	@Autowired
	OcOrderProductHistoryDao ocOrderProductHistoryDao;

	@Autowired
	OcClaimDao ocClaimDao;

	@Autowired
	OcClaimStatusHistoryDao ocClaimStatusHistoryDao;

	@Autowired
	PdProductDao pdProductDao;

	@Autowired
	OcClaimProductApplyPromotionDao ocClaimProductApplyPromotionDao;

	@Autowired
	PrPromotionMultiBuyDiscountDao prPromotionMultiBuyDiscountDao;

	@Autowired
	NiceGiftService niceGiftService;

	@Autowired
	OcCashReceiptDao ocCashReceiptDao;

	@Autowired
	CommonCodeService commonCodeService;

	@Autowired
	private PrCouponPaperNumberDao prCouponPaperNumberDao;

	@Autowired
	private EventService eventService;

	/**
	 * @Desc : 주문 전체취소 체크
	 * @Method Name : setAllCancelCheck
	 * @Date : 2019. 7. 17.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocClaimAmountVO
	 * @throws Exception
	 */
	public void setAllCancelCheck(OcClaim ocClaim, OcClaimAmountVO ocClaimAmountVO) throws Exception {
		/*
		 * [원 주문상품 목록 - 사은품/배송비 포함]
		 */
		OcOrderProduct ocOrderProduct = new OcOrderProduct();
		ocOrderProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 추출용
		ocOrderProduct.setOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준

		// 원주문 상품목록 조회
		List<OcOrderProduct> orgOrderProductList = ocOrderProductDao.selectOrgOrderProductList(ocOrderProduct);

		ocClaimAmountVO.setOrgOrderProductList(orgOrderProductList);

		/*
		 * [원 주문에 걸린 모든 클레임 - 사은품/배송비 포함]
		 */
		OcClaimProduct ocClaimProduct = new OcClaimProduct();
		ocClaimProduct.setOrgOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준
		ocClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 상품 코드

		List<OcClaimProduct> orderAllClaimProductList = ocClaimProductDao.selectOrgClaimProductList(ocClaimProduct);

		ocClaimAmountVO.setOrderAllClaimProductList(orderAllClaimProductList);

		/*
		 * 전체취소 여부
		 */
		// 원 주문 상품 개수(사은품/배송비 제외)
		long orgOrderProductCnt = orgOrderProductList.stream()
				.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
						&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
				.count();

		// 완료된 클레임 개수(반품/취소 기준, 사은품/배송비 제외)
		long finishClaimCnt = orderAllClaimProductList.stream()
				.filter(x -> UtilsText.equals(x.getClmGbnCode(), CommonCode.CLM_GBN_CODE_CANCEL)
						|| UtilsText.equals(x.getClmGbnCode(), CommonCode.CLM_GBN_CODE_RETURN))
				.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
						&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
				.filter(x -> UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_CANCEL_REDEPTION_ACCEPT)
						|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_CANCEL_REFUND_ACCEPT)
						|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_CANCEL_FINISH)
						|| UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_REDEPTION_ACCEPT)
						|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_REFUND_ACCEPT)
						|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_FINISH))

				.count();

		// 현재 클레임 개수(사은품/배송비 제외)
		long thisTimeClaimCnt = ocClaim.getOcClaimProductList().size();

		// 주문 전체 취소 여부
		if (orgOrderProductCnt == thisTimeClaimCnt) {
			ocClaimAmountVO.setAllCancelYn(true);
		} else {
			ocClaimAmountVO.setAllCancelYn(false);

			// 주문 전체 취소가 아닌 경우 이전 부분 취소 후 남은 상품 모두 취소인지 여부
			if (orgOrderProductCnt == finishClaimCnt + thisTimeClaimCnt) {
				ocClaimAmountVO.setRemainAllCancelYn(true);
			} else {
				ocClaimAmountVO.setRemainAllCancelYn(false);
			}
		}
	}

	/**
	 * @Desc : 전체취소 클레임 금액 계산
	 * @Method Name : setClaimAmountCalcForAllCancel
	 * @Date : 2019. 4. 11.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocOrder
	 * @param ocClaimProducts
	 * @return
	 */
	public OcClaimAmountVO setClaimAmountCalcForAllCancel(OcClaim ocClaim, OcOrder ocOrder,
			OcClaimAmountVO ocClaimAmountVO) throws Exception {

		/*
		 * [원 주문에 걸린 모든 클레임] - 클레임 상품 등록 후 내용을 다시 갱신
		 */
		OcClaimProduct ocClaimProduct = new OcClaimProduct();
		ocClaimProduct.setOrgOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준
		ocClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 상품 코드

		List<OcClaimProduct> orderAllClaimProductList = ocClaimProductDao.selectOrgClaimProductList(ocClaimProduct);

		ocClaimAmountVO.setOrderAllClaimProductList(orderAllClaimProductList); // 다시 set

		/*
		 * [현재 클레임 상품 목록] - 결제 취소 후처리 부분취소와 같이 쓰기 위해 set
		 */
		// 전체취소만 이렇게 사용 가능함
		ocClaimAmountVO.setThisTimeClaimProductList(orderAllClaimProductList);

		/*
		 * [원 주문 상품 쿠폰 사용 정보]
		 */
		OcOrderUseCoupon useCoupon = new OcOrderUseCoupon();
		useCoupon.setOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준

		List<OcOrderUseCoupon> orderUseCouponList = ocOrderUseCouponDao.selectOrderUserCouponForClaimList(useCoupon);

		ocClaimAmountVO.setOrderUseCouponList(orderUseCouponList);

		/*
		 * [현재 클레임 상품 목록]
		 */
		List<OcClaimProduct> claimProductList = orderAllClaimProductList.stream()
				.filter(x -> UtilsText.equals(x.getClmNo(), ocClaim.getClmNo())).collect(Collectors.toList());

		/*
		 * [클레임 취소가능 잔여금액 목록]
		 */
		OcClaimPayment ocOrderClaimPayment = new OcClaimPayment();
		ocOrderClaimPayment.setClmNo(ocClaim.getClmNo());
		ocOrderClaimPayment.setOrgOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준
		ocOrderClaimPayment.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 재경팀처리 제외
		ocOrderClaimPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_PUBLIC); // 일반결제
		ocOrderClaimPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REFUND); // 환불
		ocOrderClaimPayment.setOrderPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH); // 주문결제상태(결제완료)

		List<OcClaimPayment> _orderClaimPaymentList = ocClaimPaymentDao
				.selectOrderClaimPaymentList(ocOrderClaimPayment);

		// 결제완료만
		List<OcClaimPayment> orderClaimPaymentList = _orderClaimPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getPymntStatCode(), CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH))
				.collect(Collectors.toList());

		ocClaimAmountVO.setOrderClaimPaymentList(orderClaimPaymentList);

		/*
		 * 클레임 금액 기본 계산
		 */
		// 현재 클레임으로 인해 취소될 금액(=상품 클레임 금액)
		int expectCnclAmt = claimProductList.stream().mapToInt(o -> o.getOrderAmt()).sum();

		ocClaimAmountVO.setExpectCnclAmt(expectCnclAmt);

		OcClaimPayment mainPayment = null;
		OcClaimPayment giftPayment = null;
		OcClaimPayment pointPayment = null;
		OcClaimPayment eventPointPayment = null;

		for (OcClaimPayment claimPayment : orderClaimPaymentList) {
			if (UtilsText.equals(claimPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_MOBILE_GIFT)) { // 기프트
				giftPayment = claimPayment;
			} else if (UtilsText.equals(claimPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_BUY_POINT)) { // 구매포인트
				pointPayment = claimPayment;
			} else if (UtilsText.equals(claimPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_EVENT_POINT)) { // 이벤트포인트
				eventPointPayment = claimPayment;
			} else {
				mainPayment = claimPayment;
			}
		}

		ocClaimAmountVO.setMainPayment(mainPayment);
		ocClaimAmountVO.setGiftPayment(giftPayment);
		ocClaimAmountVO.setPointPayment(pointPayment);
		ocClaimAmountVO.setEventPointPayment(eventPointPayment);

		return ocClaimAmountVO;
	}

	/**
	 * @Desc : 부분취소 클레임 금액 계산(반품과 같이 사용)
	 * @Method Name : setClaimAmountCalcForPartCancel
	 * @Date : 2019. 8. 29.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocOrder
	 * @param ocClaimAmountVO
	 * @param clmGbnCode
	 * @param isMissCancel
	 * @return
	 * @throws Exception
	 */
	public OcClaimAmountVO setClaimAmountCalcForPartCancel(OcClaim ocClaim, OcOrder ocOrder,
			OcClaimAmountVO ocClaimAmountVO, String clmGbnCode, boolean isMissCancel) throws Exception {

		/******************************************************************
		 * [원 주문상품 목록 - 사은품/배송비 포함] - ocClaimAmountVO에 담겨 온 내용이 없으면 조회하여 set
		 ******************************************************************/
		List<OcOrderProduct> orgOrderProductList;

		if (ObjectUtils.isEmpty(ocClaimAmountVO.getOrgOrderProductList())) {
			OcOrderProduct ocOrderProduct = new OcOrderProduct();
			ocOrderProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 추출용
			ocOrderProduct.setOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준

			orgOrderProductList = ocOrderProductDao.selectOrgOrderProductList(ocOrderProduct);

			ocClaimAmountVO.setOrgOrderProductList(orgOrderProductList);

		} else {
			orgOrderProductList = ocClaimAmountVO.getOrgOrderProductList();
		}

		/******************************************************************
		 * [원 주문에 걸린 모든 클레임] - 클레임 상품 등록 후 내용을 다시 갱신, 현재 클레임은 사은품만 포함, 배송비는 이후 별도 저장
		 ******************************************************************/
		OcClaimProduct ocClaimProduct = new OcClaimProduct();
		ocClaimProduct.setOrgOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준
		ocClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 상품 코드

		List<OcClaimProduct> orderAllClaimProductList = ocClaimProductDao.selectOrgClaimProductList(ocClaimProduct);

		ocClaimAmountVO.setOrderAllClaimProductList(orderAllClaimProductList); // 다시 set

		/******************************************************************
		 * [원 주문 상품 결제 정보]
		 ******************************************************************/
		OcOrderPayment ocOrderPayment = new OcOrderPayment();
		ocOrderPayment.setOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준

		List<OcOrderPayment> _orderPaymentList = ocOrderPaymentDao.selectPaymentList(ocOrderPayment);

		// 결제완료만
		List<OcOrderPayment> orderPaymentList = _orderPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getPymntStatCode(), CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH))
				.collect(Collectors.toList());
		;

		/******************************************************************
		 * [리오더 상품 목록] - 원 주문 상품 제외
		 ******************************************************************/
		OcOrderProduct paramProduct = new OcOrderProduct();
		paramProduct.setOrgOrderNo(ocClaim.getOrgOrderNo());

		List<OcOrderProduct> reOrderProductList = ocOrderProductDao.selectReOrderProductList(paramProduct);

		ocClaimAmountVO.setReOrderProductList(reOrderProductList);

		/******************************************************************
		 * [리오더 매출 목록] - 리오더 상품 목록 중 매출에 해당하는 목록만 다시 추출
		 ******************************************************************/
		List<OcOrderProduct> reOrderProductSalesList = reOrderProductList.stream()
				.filter(x -> UtilsText.equals(x.getSalesCnclGbnType(), CommonCode.SALES_CNCL_GBN_TYPE_SALE)
						|| UtilsText.equals(x.getSalesCnclGbnType(), CommonCode.SALES_CNCL_GBN_TYPE_MULTIBUY_SALE)) // 매출
				.collect(Collectors.toList());

		ocClaimAmountVO.setReOrderProductSalesList(reOrderProductSalesList);

		/******************************************************************
		 * [원 주문 상품 쿠폰 사용 정보]
		 ******************************************************************/
		OcOrderUseCoupon useCoupon = new OcOrderUseCoupon();
		useCoupon.setOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준

		List<OcOrderUseCoupon> orderUseCouponList = ocOrderUseCouponDao.selectOrderUserCouponForClaimList(useCoupon);

		ocClaimAmountVO.setOrderUseCouponList(orderUseCouponList);

		/******************************************************************
		 * [더블적립 쿠폰 사용 내역]
		 ******************************************************************/
		OcOrderUseCoupon doubleDscntCpn = orderUseCouponList.stream()
				.filter(x -> UtilsText.equals(x.getCpnTypeCode(), CommonCode.CPN_TYPE_CODE_DOUBLE_POINT)).findFirst()
				.orElse(null);

		ocClaimAmountVO.setOrderDoubleDscntCpnInfo(doubleDscntCpn);

		/******************************************************************
		 * [원 주문 상품 프로모션 정보]
		 ******************************************************************/
		OcOrderProductApplyPromotion orderApplyPromotion = new OcOrderProductApplyPromotion();
		orderApplyPromotion.setOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준

		List<OcOrderProductApplyPromotion> orderApplyPromotionList = ocOrderProductApplyPromotionDao
				.select(orderApplyPromotion);

		/******************************************************************
		 * [기존 완료 클레임 상품 목록 - 사은품/배송비 포함]
		 ******************************************************************/
		List<OcClaimProduct> finishedClaimProductList = orderAllClaimProductList.stream()
				.filter(x -> UtilsText.equals(x.getClmGbnCode(), CommonCode.CLM_GBN_CODE_CANCEL)
						|| UtilsText.equals(x.getClmGbnCode(), CommonCode.CLM_GBN_CODE_RETURN))
				.filter(x -> UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_CANCEL_REDEPTION_ACCEPT)
						|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_CANCEL_REFUND_ACCEPT)
						|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_CANCEL_FINISH)
						|| UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_REDEPTION_ACCEPT)
						|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_REFUND_ACCEPT)
						|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_FINISH))
				.collect(Collectors.toList());

		ocClaimAmountVO.setFinishedClaimProductList(finishedClaimProductList);

		/******************************************************************
		 * [현재 클레임 상품 목록 - 사은품 포함] - 현재 클레임은 사은품만 포함, 배송비는 환불 배송비 판별 후 포함
		 ******************************************************************/
		List<OcClaimProduct> thisTimeClaimProductList = orderAllClaimProductList.stream()
				.filter(x -> UtilsText.equals(x.getClmNo(), ocClaim.getClmNo()))
				.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
				.filter(x -> UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT)
						|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT)
						|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_PICKUP_ORDER)
						|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_RECEIVE_FINISH)
						|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_JUDGE_FINISH))
				.collect(Collectors.toList());

		ocClaimAmountVO.setThisTimeClaimProductList(thisTimeClaimProductList);
		log.debug("######################### claimProductList : {} ", thisTimeClaimProductList);

		/******************************************************************
		 * [클레임이 걸려있지 않은 주문 상품] - 완료클레임과 현재클레임 상품을 제외한 남은 주문상품
		 ******************************************************************/
		int[] tempFinishedClaimProductPrdtSeqs = finishedClaimProductList.stream()
				.mapToInt(OcClaimProduct::getOrderPrdtSeq).toArray();

		int[] tempThisTimeClaimProductPrdtSeqs = thisTimeClaimProductList.stream()
				.mapToInt(OcClaimProduct::getOrderPrdtSeq).toArray();

		List<Integer> tempAllClaimProductPrdtSeqsList = new ArrayList<Integer>();

		for (Integer tempFinishedClaimProductPrdtSeq : tempFinishedClaimProductPrdtSeqs) {
			tempAllClaimProductPrdtSeqsList.add(tempFinishedClaimProductPrdtSeq);
		}

		for (Integer tempThisTimeClaimProductPrdtSeq : tempThisTimeClaimProductPrdtSeqs) {
			tempAllClaimProductPrdtSeqsList.add(tempThisTimeClaimProductPrdtSeq);
		}

		List<OcOrderProduct> notClaimOrderProductList = orgOrderProductList.stream()
				.filter(x -> !tempAllClaimProductPrdtSeqsList.contains(x.getOrderPrdtSeq()))
				.collect(Collectors.toList());

		ocClaimAmountVO.setNotClaimOrderProductList(notClaimOrderProductList);

		/******************************************************************
		 * [자사/업체별 원주문 상품 목록]
		 ******************************************************************/
		// 자사 원주문 상품 목록
		List<OcOrderProduct> mmnyOrgOrderProductList = orgOrderProductList.stream()
				.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_TRUE)).collect(Collectors.toList());

		// 업체 원주문 상품 목록
		List<OcOrderProduct> vndrOrgOrderProductList = orgOrderProductList.stream()
				.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_FALSE)).collect(Collectors.toList());

		/******************************************************************
		 * [자사/업체별 기존 완료 클레임 상품 목록]
		 ******************************************************************/
		// 자사 기존 완료 클레임 상품 목록
		List<OcClaimProduct> mmnyFinishedClaimProductList = finishedClaimProductList.stream()
				.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_TRUE)).collect(Collectors.toList());

		// 업체 기존 완료 클레임 상품 목록
		List<OcClaimProduct> vndrFinishedClaimProductList = finishedClaimProductList.stream()
				.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_FALSE)).collect(Collectors.toList());

		/******************************************************************
		 * [자사/업체별 현재 클레임 상품 목록]
		 ******************************************************************/
		// 자사 현재 클레임 상품 목록
		List<OcClaimProduct> mmnyThisTimeClaimProductList = thisTimeClaimProductList.stream()
				.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_TRUE)).collect(Collectors.toList());

		// 현재 클레임 대상 자사 업체코드 목록
		List<String> mmnyVndrNoList = mmnyThisTimeClaimProductList.stream().map(OcClaimProduct::getVndrNo).distinct()
				.collect(Collectors.toCollection(ArrayList::new));

		// 업체 현재 클레임 상품 목록
		List<OcClaimProduct> vndrThisTimeClaimProductList = thisTimeClaimProductList.stream()
				.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_FALSE)).collect(Collectors.toList());

		// 현재 클레임 대상 업체코드 목록
		List<String> vndrNoList = vndrThisTimeClaimProductList.stream().map(OcClaimProduct::getVndrNo).distinct()
				.collect(Collectors.toCollection(ArrayList::new));

		/******************************************************************
		 * [클레임 결제 정보 - 원주문 기준 전체]
		 ******************************************************************/
		OcClaimPayment ocClaimPayment = new OcClaimPayment();
		ocClaimPayment.setOrgOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준

		List<OcClaimPayment> claimAllPaymentList = ocClaimPaymentDao.selectClaimPaymentList(ocClaimPayment);

		/******************************************************************
		 * [클레임 취소가능 잔여금액 목록]
		 ******************************************************************/
		OcClaimPayment ocOrderClaimPayment = new OcClaimPayment();
		ocOrderClaimPayment.setClmNo(ocClaim.getClmNo());
		ocOrderClaimPayment.setOrgOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준
		ocOrderClaimPayment.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 재경팀처리 제외
		ocOrderClaimPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_PUBLIC); // 일반결제
		ocOrderClaimPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REFUND); // 환불
		ocOrderClaimPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_ORDER_AMT); // 주문금
		ocOrderClaimPayment.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL); // 주문취소(결제취소)
		ocOrderClaimPayment.setOrderPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH); // 주문결제상태(결제완료)

		List<OcClaimPayment> _orderClaimPaymentList = ocClaimPaymentDao
				.selectOrderClaimPaymentList(ocOrderClaimPayment);

		// 결제완료만
		List<OcClaimPayment> orderClaimPaymentList = _orderClaimPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getPymntStatCode(), CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH))
				.collect(Collectors.toList());

		ocClaimAmountVO.setOrderClaimPaymentList(orderClaimPaymentList);

		/******************************************************************
		 * [사이트 정책 정보]
		 ******************************************************************/
		SySite sySite = sySiteDao.selectSite(ocOrder.getSiteNo());

		/******************************************************************
		 * [배송비상품 조회]
		 ******************************************************************/
		List<PdProduct> dlvyProductList = pdProductDao.selectProductDlvy(CommonCode.PRDT_TYPE_CODE_DELIVERY);

		// 자사 배송비 상품
		PdProduct mmnyDlvyProduct = (PdProduct) dlvyProductList.stream()
				.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_TRUE)).findFirst().orElse(null);

		// 업체 배송비 상품
		PdProduct vndrDlvyProduct = (PdProduct) dlvyProductList.stream()
				.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_FALSE)).findFirst().orElse(null);

		// 기존 총 취소된 금액(클레임으로 인해 누적된 취소금)
		int accumulatedCnclAmt = claimAllPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND)) // 환불
				.filter(x -> !UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE)) // 재경팀처리 제외
				.filter(x -> !UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_PUBLIC)) // 일반결제
				.filter(x -> !UtilsText.equals(x.getClmNo(), ocClaim.getClmNo())) // 이번 클레임 제외
				.mapToInt(o -> o.getPymntAmt()).sum();

		ocClaimAmountVO.setTotalAccumulatedCnclAmt(accumulatedCnclAmt);
		log.debug("######################### accumulatedCnclAmt : {} ", accumulatedCnclAmt);

		/******************************************************************
		 * [원 주문에 걸린 다족구매 프로모션 현황 조회(현재 클레임 포함)]
		 ******************************************************************/
		String[] clmPrdtStatCodesForMultiBuy = { CommonCode.CLM_PRDT_STAT_CODE_CANCEL_REDEPTION_ACCEPT,
				CommonCode.CLM_PRDT_STAT_CODE_CANCEL_REFUND_ACCEPT, CommonCode.CLM_PRDT_STAT_CODE_CANCEL_FINISH,
				CommonCode.CLM_PRDT_STAT_CODE_RETURN_REDEPTION_ACCEPT,
				CommonCode.CLM_PRDT_STAT_CODE_RETURN_REFUND_ACCEPT, CommonCode.CLM_PRDT_STAT_CODE_RETURN_FINISH };
		OcClaimProduct multiBuyProduct = new OcClaimProduct();
		multiBuyProduct.setClmNo(ocClaim.getClmNo());
		multiBuyProduct.setOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준
		multiBuyProduct.setPromoTypeCode(CommonCode.PROMO_TYPE_CODE_DISCOUNT_MULTI_SHOUES); // 프로모션유형 : 다족구매
		multiBuyProduct.setClmPrdtStatCodes(clmPrdtStatCodesForMultiBuy);

		List<OcClaimPromoVO> orderMultiBuyPromoCheckList = ocClaimProductDao
				.selectOrderMultiBuyPromoCheckList(multiBuyProduct);
		log.debug("######################### orderMultiBuyPromoCheckList : {} ", orderMultiBuyPromoCheckList);

		/******************************************************************
		 * 
		 * 다족구매 프로모션 체크 / 2020.04 : 클레임 사유코드의 ADD_INFO2가 Y인 사유라면 프로모션 환수 X
		 * 
		 * String redempAddPrdtAmtYn = _thisTimeclaimProductList.get(0).getClmRsnCodeAddInfo2();
		 * 
		 ******************************************************************/
		// 다족구매로 인한 금액 재조정 되기 이전의 주문상품 목록
		List<OcOrderProduct> beforeAdjustOrderProductByMultiBuyList = new ArrayList<>();
		// 다족구매로 인한 금액 재조정 된 주문상품 목록
		List<OcOrderProduct> adjustedOrderProductByMultiBuyList = new ArrayList<>();
		// 다족구매로 인한 금액 재조정 된 주문상품 프로모션 변경 목록
		List<OcClaimProductApplyPromotion> adjustedClaimProductApplyPromotionList = new ArrayList<>();
		// 환수 다족구매 결제 목록
		List<OcClaimPayment> redempMultiBuyPaymentList = new ArrayList<>();
		// 다족구매 리오더 매출과 실제 결제금액 차이가 발생한 금액 목록(환불개념)
		List<OcClaimPayment> addMultiBuyDifferPaymentList = new ArrayList<>();
		// 다족구매 클레임으로 인해 클레임 진행 후 주문에 남아있어야 할 결제금액
		int totalOrderRemainAmtByMultiBuy = 0;
		// 다족구매 클레임으로 발생된 환수금
		int redempAmtByMultiBuy = 0;
		// 다족구매 리오더 매출로 인한 실제 취소금과의 차액
		int addMultiBuyDifferAmt = 0;

		// 자사상품 다족구매 프로모션 체크
		if (!ObjectUtils.isEmpty(mmnyThisTimeClaimProductList)) {
			// 현재 클레임 다족구매 프로모션 상품 목록
			List<OcClaimPromoVO> _thisTimeMultiBuyProductList = orderMultiBuyPromoCheckList.stream()
					.filter(x -> UtilsText.equals(x.getNowClmNo(), ocClaim.getClmNo()))
					.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_TRUE)).collect(Collectors.toList());

			// 다족구매 프로모션이 적용된 클레임인 경우
			if (!ObjectUtils.isEmpty(_thisTimeMultiBuyProductList)) {
				// 현재 클레임 상품(사은품/배송비 제외)
				List<OcClaimProduct> _thisTimeclaimProductList = mmnyThisTimeClaimProductList.stream()
						.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
								&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
						.collect(Collectors.toList());

				/**
				 *  2020.04 : 클레임 사유코드의 ADD_INFO2가 Y인 사유라면 프로모션 환수 X
				 */
				String redempAddPrdtAmtYn = _thisTimeclaimProductList.get(0).getClmRsnCodeAddInfo2();
				
				// 현재 클레임 개수(사은품/배송비 제외)
				long _thisTimeClaimCnt = _thisTimeclaimProductList.size();

				// 원 주문 상품 개수(사은품/배송비 제외)
				long _orderProductCnt = mmnyOrgOrderProductList.stream()
						.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
								&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
						.count();

				// 완료된 클레임 개수(사은품/배송비 제외)
				long _finishClaimCnt = mmnyFinishedClaimProductList.stream()
						.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
								&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
						.count();

				// 현재 클레임에 적용된 다족구매 프로모션 번호 추출(자사 또는 업체단위로 2개 이상 적용 가능성 고려)
				List<String> _multiBuyPromoNos = _thisTimeMultiBuyProductList.stream().map(OcClaimPromoVO::getPromoNo)
						.distinct().collect(Collectors.toList()); // 중복 프로모션 번호 제거

				/**
				 * 현재 클레임으로 인해 취소되는 다족구매 상품의 리오더 매출을 확인하여 실제 취소금에 추가 할 금액 계산
				 */
				for (OcClaimPromoVO thisTimeMultiBuyProduct : _thisTimeMultiBuyProductList) {
					// 리오더 매출 목록 중에서 조정된 금액이 있는지 확인(가장 큰 금액을 추출)
					OcOrderProduct _reOrderSalesMultiBuyProduct = reOrderProductSalesList.stream()
							.filter(x -> UtilsText.equals(String.valueOf(x.getOrderPrdtSeq()),
									String.valueOf(thisTimeMultiBuyProduct.getOrderPrdtSeq())))
							.max(Comparator.comparing(OcOrderProduct::getOrderAmt)).orElse(null);

					// 기존 리오더 주문금액과 원 주문 기준 취소상품 금액 차이(현재 취소금을 맞추기 위함)
					if (_reOrderSalesMultiBuyProduct != null) {
						addMultiBuyDifferAmt += (_reOrderSalesMultiBuyProduct.getOrderAmt()
								- thisTimeMultiBuyProduct.getOrderAmt());
					}
				}
				
				/**
				 * 다족구매 프로모션 번호 기준으로 루프 처리
				 */
				for (String multiBuyPromoNo : _multiBuyPromoNos) {
					// 클레임 상품에 적용된 다족구매 프로모션 할인률 정보 목록
					PrPromotionMultiBuyDiscount _prPromotionMultiBuyDiscount = new PrPromotionMultiBuyDiscount();
					_prPromotionMultiBuyDiscount.setPromoNo(multiBuyPromoNo);

					List<PrPromotionMultiBuyDiscount> _multiBuyDiscountList = prPromotionMultiBuyDiscountDao
							.select(_prPromotionMultiBuyDiscount);

					log.error("######################### _multiBuyDiscountList : {} ", _multiBuyDiscountList);

					// 다족구매 프로모션에 등록된 최대 개수의 할인 정보
					PrPromotionMultiBuyDiscount _maxMultiBuyDiscountInfo = (PrPromotionMultiBuyDiscount) _multiBuyDiscountList
							.stream().max(Comparator.comparing(PrPromotionMultiBuyDiscount::getBuyQty)).orElse(null);

					log.error("######################### _maxMultiBuyDiscountInfo : {} ", _maxMultiBuyDiscountInfo);

					// 다족구매 할인 재적용 상품 대상 목록(기존 취소된 클레임, 현재 클레임 상품 제외)
					List<OcClaimPromoVO> _adjustMultiBuyProductTargetList = orderMultiBuyPromoCheckList.stream()
							.filter(x -> UtilsText.isEmpty(x.getNowClmNo()))
							.filter(x -> UtilsText.isEmpty(x.getCanceledClmNo()))
							.filter(x -> UtilsText.equals(x.getPromoNo(), multiBuyPromoNo))
							.collect(Collectors.toList());

					// 다족구매 할인 재적용 상품 개수
					int _reApplyMultiBuyProductCnt = ObjectUtils.isEmpty(_adjustMultiBuyProductTargetList) ? 0
							: _adjustMultiBuyProductTargetList.size();

					// 새로 적용해야 할 다족구매 프로모션 할인 정보 확인
					PrPromotionMultiBuyDiscount _reApplyMultiBuyDiscountInfo = (PrPromotionMultiBuyDiscount) _multiBuyDiscountList
							.stream().filter(x -> x.getBuyQty() == _reApplyMultiBuyProductCnt).findFirst().orElse(null);

					// 현재 클레임 기준 자사상품 전체 취소가 아니며, 이전 부분 취소 후 남은 상품 모두 취소가 아닌 경우(부분 취소 클레임)
					// 2020.04 : 클레임 사유코드의 ADD_INFO2가 Y인 사유라면 프로모션 환수 X
					if ((_orderProductCnt != _thisTimeClaimCnt)
							&& (_orderProductCnt != _finishClaimCnt + _thisTimeClaimCnt)
							&& !UtilsText.equals(redempAddPrdtAmtYn, Const.BOOLEAN_TRUE)) {
						// 주문 상품 중 다족구매 할인 재적용 상품 존재(없는 경우는 현재 상품 금액 그대로 취소)
						
						log.error(">>>>>>>>>> _reApplyMultiBuyProductCnt : {}",  _reApplyMultiBuyProductCnt);
						
						if (_reApplyMultiBuyProductCnt > 0) {
//							// 다족구매 할인 재적용 상품이 한 개인 경우 다족구매 할인금액 변경(다족구매 적용되지 않은 금액으로)
//							if (_reApplyMultiBuyProductCnt == 1) {
//								// 다족구매 재적용 상품이 1개인 경우는 할인 정보도 없으므로 할인정보 임의로 0 set
//								_reApplyMultiBuyDiscountInfo = new PrPromotionMultiBuyDiscount();
//								_reApplyMultiBuyDiscountInfo.setBuyQty(1);
//								_reApplyMultiBuyDiscountInfo.setDscntRate((short) 0);
//							}

							log.error(">>>>>>>>>> _reApplyMultiBuyProductCnt : {}",  _reApplyMultiBuyProductCnt);
							log.error(">>>>>>>>>> _maxMultiBuyDiscountInfo.getBuyQty() : {}",  _maxMultiBuyDiscountInfo.getBuyQty());
							
							// 적용해야 할 다족구매 할인 적용 상품 개수가 등록된 다족구매 프로모션 최대 지정 개수보다 작을 때만 금액 재조정
							if (_reApplyMultiBuyProductCnt <= _maxMultiBuyDiscountInfo.getBuyQty()) {
								for (OcClaimPromoVO targetProduct : _adjustMultiBuyProductTargetList) {
									// 이전 클레임에 의해 재 조정된 다족구매 리오더 매출 주문 확인
									OcOrderProduct _beforeAdjustProduct = new OcOrderProduct();
									_beforeAdjustProduct.setOrderNo(targetProduct.getOrderNo());
									_beforeAdjustProduct.setOrgOrderNo(targetProduct.getOrgOrderNo());
									_beforeAdjustProduct.setOrderPrdtSeq(targetProduct.getOrderPrdtSeq());
									_beforeAdjustProduct
											.setSalesCnclGbnType(CommonCode.SALES_CNCL_GBN_TYPE_MULTIBUY_SALE);

									_beforeAdjustProduct = ocOrderProductDao
											.selectRecentMultiBuyReApplyProduct(_beforeAdjustProduct);

									// 이전 클레임으로 재 조정된 다족구매 매출 상품이 없으면 처음 조정 대상이므로 원 주문에서 해당 상품 추출
									if (_beforeAdjustProduct == null) {
										_beforeAdjustProduct = mmnyOrgOrderProductList.stream()
												.filter(x -> UtilsText.equals(x.getOrderNo(),
														targetProduct.getOrderNo()))
												.filter(x -> UtilsText.equals(String.valueOf(x.getOrderPrdtSeq()),
														String.valueOf(targetProduct.getOrderPrdtSeq())))
												.findFirst().orElse(null);
									}

									// 이전 클레임에 의해 재 조정된 다족구매 할인 금액 정보 확인
									OcClaimProductApplyPromotion _recentClaimProductApplyPromotion = new OcClaimProductApplyPromotion();
									_recentClaimProductApplyPromotion.setOrderPrdtSeq(targetProduct.getOrderPrdtSeq());

									_recentClaimProductApplyPromotion = ocClaimProductApplyPromotionDao
											.selectRecentClaimProductApplyPromotion(_recentClaimProductApplyPromotion);

									// 이전 클레임으로 재 조정된 다족구매 할인 금액 정보가 없으면 처음 조정 대상이므로 원 주문 기준의 프로모션 할인금 정보 추출
									if (_recentClaimProductApplyPromotion == null) {

										OcOrderProductApplyPromotion _recentOrderProductApplyPromotion = orderApplyPromotionList
												.stream()
												.filter(x -> UtilsText.equals(x.getOrderNo(),
														targetProduct.getOrderNo()))
												.filter(x -> UtilsText.equals(String.valueOf(x.getOrderPrdtSeq()),
														String.valueOf(targetProduct.getOrderPrdtSeq())))
												.filter(x -> UtilsText.equals(x.getPromoTypeCode(),
														CommonCode.PROMO_TYPE_CODE_DISCOUNT_MULTI_SHOUES))
												.findFirst().orElse(null);

										_recentClaimProductApplyPromotion = new OcClaimProductApplyPromotion();

										BeanUtils.copyProperties(_recentOrderProductApplyPromotion,
												_recentClaimProductApplyPromotion); // 내용 복사

										_recentClaimProductApplyPromotion.setClmNo(targetProduct.getClmNo());
										// _recentClaimProductApplyPromotion.setOrderPrdtSeq(targetProduct.getOrderPrdtSeq());
									}

									int _prdtNormalAmt = _beforeAdjustProduct.getPrdtNormalAmt(); // 조정 대상상품 상품정상가
									int _prdtSellAmt = _beforeAdjustProduct.getPrdtSellAmt(); // 조정 대상상품 상품판매가
									int _optnAddAmt = _beforeAdjustProduct.getOptnAddAmt(); // 조정 대상상품 옵션추가금액
									int _sellAmt = _beforeAdjustProduct.getSellAmt(); // 조정 대상상품 판매가(상품판매가 + 옵션추가금)
									int _totalDscntAmt = _beforeAdjustProduct.getTotalDscntAmt(); // 조정 대상상품 할인금액합계
									int _cpnDscntAmt = _beforeAdjustProduct.getCpnDscntAmt(); // 조정 대상상품 쿠폰할인금액
									int _orderAmt = _beforeAdjustProduct.getOrderAmt(); // 조정 대상상품 주문금액
									int _compPrdtSellAmt = 0; // 조정 대상상품 상품판매가와 비교할 임시 금액
									int _multiBuyDscntAmt = _recentClaimProductApplyPromotion.getDscntAmt(); // 조정대상상품다족구매할인금

									int _adjustMultiBuyDscntAmt = 0; // 조정된 할인금액
									int _adjustTotalDscntAmt = 0; // 조정된 할인금액합계
									int _adjustOrderAmt = 0; // 조정된 주문금액
									int _differDscntAmt = 0; // 새롭게 적용된 상품할인금액과 기존 할인금액 차이

									int _newDscntRate = _reApplyMultiBuyDiscountInfo.getDscntRate(); // 새롭게 적용할 할인율

									// 새로 적용해야 할 할인율 퍼센티지를 이용하여 다족구매 할인금액 계산 - 옵션 추가금 포함
									_adjustMultiBuyDscntAmt = (int) Math
											.ceil((double) (_prdtNormalAmt + _optnAddAmt) * _newDscntRate / 10000)
											* 100;

									// 조정 대상상품 판매가와 비교할 임시 금액 ( (상품정상가+옵션추가금) - 조정된 다족구매 할인금 )
									_compPrdtSellAmt = (_prdtNormalAmt + _optnAddAmt) - _adjustMultiBuyDscntAmt;

									// 원 조정 대상상품 (판매가+옵션추가금) 이 비교할 임시 금액보다 낮은 경우
									if ((_prdtSellAmt + _optnAddAmt) < _compPrdtSellAmt) {
										// 조정된 할인금액 합계(조정된 다족구매 할인금 + 정액쿠폰 할인금)
										_adjustTotalDscntAmt = _cpnDscntAmt;
										// 조정된 주문금액(조정된판매가 - 조정된 할인금액 합계)
										_adjustOrderAmt = (_prdtSellAmt + _optnAddAmt) - _adjustTotalDscntAmt;

									}
									// 원 조정 대상상품 판매가가 조정된 다족구매 할인금을 적용한 금액보다 높은 경우
									else {
										// 조정된 할인금액 합계(조정된 다족구매 할인금 + 정액쿠폰 할인금)
										_adjustTotalDscntAmt = _adjustMultiBuyDscntAmt + _cpnDscntAmt;
										// 조정된 주문금액
										_adjustOrderAmt = (_prdtNormalAmt + _optnAddAmt) - _adjustTotalDscntAmt;
									}

									// 조정된 금액이 재조정 되기 이전과 다를 경우만 리오더 생성을 위한 재조정 리스트에 포함
									if (_orderAmt != _adjustOrderAmt) {
										// 다족구매로 인한 금액 재조정 되기 이전의 주문상품 목록 set
										beforeAdjustOrderProductByMultiBuyList.add(_beforeAdjustProduct);

										// 다족구매로 인한 금액 재조정 된 주문상품 목록 set
										OcOrderProduct _adjustedProduct = new OcOrderProduct();
										BeanUtils.copyProperties(_beforeAdjustProduct, _adjustedProduct); // 내용 복사

										_adjustedProduct.setTotalDscntAmt(_adjustTotalDscntAmt);
										_adjustedProduct.setOrderAmt(_adjustOrderAmt);

										adjustedOrderProductByMultiBuyList.add(_adjustedProduct);
									}

									// 다족구매로 인한 금액 재조정 된 주문상품 프로모션 변경 목록 set
									OcClaimProductApplyPromotion _adjustedClaimProductApplyPromotion = new OcClaimProductApplyPromotion();
									_adjustedClaimProductApplyPromotion.setClmNo(ocClaim.getClmNo());
									_adjustedClaimProductApplyPromotion.setOrderNo(targetProduct.getOrderNo());
									_adjustedClaimProductApplyPromotion
											.setOrderPrdtSeq(targetProduct.getOrderPrdtSeq());
									_adjustedClaimProductApplyPromotion
											.setApplyPromoSeq(_recentClaimProductApplyPromotion.getApplyPromoSeq());
									_adjustedClaimProductApplyPromotion
											.setPromoNo(_recentClaimProductApplyPromotion.getPromoNo());
									_adjustedClaimProductApplyPromotion
											.setPromoTypeCode(_recentClaimProductApplyPromotion.getPromoTypeCode());
									_adjustedClaimProductApplyPromotion
											.setDscntType(_recentClaimProductApplyPromotion.getDscntType());
									_adjustedClaimProductApplyPromotion.setDscntValue(_newDscntRate);
									_adjustedClaimProductApplyPromotion.setDscntAmt(_adjustMultiBuyDscntAmt);

									adjustedClaimProductApplyPromotionList.add(_adjustedClaimProductApplyPromotion);

									// 다족구매 클레임으로 발생된 환수금
									redempAmtByMultiBuy += (_adjustOrderAmt - _orderAmt);
								}
							}
						}
					}
				}
			}
		}

		// 업체상품 다족구매 프로모션 체크
		if (!ObjectUtils.isEmpty(vndrThisTimeClaimProductList)) {
			for (String vndrNo : vndrNoList) {
				// 현재 클레임 다족구매 프로모션 상품 목록
				List<OcClaimPromoVO> _thisTimeMultiBuyProductList = orderMultiBuyPromoCheckList.stream()
						.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList());

				// 다족구매 프로모션이 적용된 클레임인 경우
				if (!ObjectUtils.isEmpty(_thisTimeMultiBuyProductList)) {
					// 현재 클레임 상품(사은품/배송비 제외)
					List<OcClaimProduct> _thisTimeclaimProductList = vndrThisTimeClaimProductList.stream()
							.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
									&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
							.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList());

					// 현재 클레임 개수(사은품/배송비 제외)
					long _thisTimeClaimCnt = _thisTimeclaimProductList.size();

					// 원 주문 상품 개수(사은품/배송비 제외)
					long _orderProductCnt = vndrOrgOrderProductList.stream()
							.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
									&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
							.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).count();

					// 완료된 클레임 개수(사은품/배송비 제외)
					long _finishClaimCnt = vndrFinishedClaimProductList.stream()
							.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
									&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
							.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).count();

					// 현재 클레임에 적용된 다족구매 프로모션 번호 추출(자사 또는 업체단위로 2개 이상 적용 가능성 고려)
					List<String> _multiBuyPromoNos = _thisTimeMultiBuyProductList.stream()
							.map(OcClaimPromoVO::getPromoNo).distinct().collect(Collectors.toList()); // 중복 프로모션 번호 제거

					/**
					 * 현재 클레임으로 인해 취소되는 다족구매 상품의 리오더 매출을 확인하여 실제 취소금에 추가 할 금액 계산
					 */
					for (OcClaimPromoVO thisTimeMultiBuyProduct : _thisTimeMultiBuyProductList) {
						// 리오더 매출 목록 중에서 조정된 금액이 있는지 확인(가장 큰 금액을 추출)
						OcOrderProduct _reOrderSalesMultiBuyProduct = reOrderProductSalesList.stream()
								.filter(x -> UtilsText.equals(String.valueOf(x.getOrderPrdtSeq()),
										String.valueOf(thisTimeMultiBuyProduct.getOrderPrdtSeq())))
								.max(Comparator.comparing(OcOrderProduct::getOrderAmt)).orElse(null);

						// 기존 리오더 주문금액과 원 주문 기준 취소상품 금액 차이(현재 취소금을 맞추기 위함)
						if (_reOrderSalesMultiBuyProduct != null) {
							addMultiBuyDifferAmt += (_reOrderSalesMultiBuyProduct.getOrderAmt()
									- thisTimeMultiBuyProduct.getOrderAmt());
						}
					}

					/**
					 * 다족구매 프로모션 번호 기준으로 루프 처리
					 */
					for (String multiBuyPromoNo : _multiBuyPromoNos) {
						// 클레임 상품에 적용된 다족구매 프로모션 할인률 정보 목록
						PrPromotionMultiBuyDiscount _prPromotionMultiBuyDiscount = new PrPromotionMultiBuyDiscount();
						_prPromotionMultiBuyDiscount.setPromoNo(multiBuyPromoNo);

						List<PrPromotionMultiBuyDiscount> _multiBuyDiscountList = prPromotionMultiBuyDiscountDao
								.select(_prPromotionMultiBuyDiscount);

						log.error("######################### _multiBuyDiscountList : {} ", _multiBuyDiscountList);

						// 다족구매 프로모션에 등록된 최대 개수의 할인 정보
						PrPromotionMultiBuyDiscount _maxMultiBuyDiscountInfo = (PrPromotionMultiBuyDiscount) _multiBuyDiscountList
								.stream().max(Comparator.comparing(PrPromotionMultiBuyDiscount::getBuyQty))
								.orElse(null);

						// 다족구매 할인 재적용 상품 대상 목록(기존 취소된 클레임, 현재 클레임 상품 제외)
						List<OcClaimPromoVO> _adjustMultiBuyProductTargetList = orderMultiBuyPromoCheckList.stream()
								.filter(x -> UtilsText.isEmpty(x.getNowClmNo()))
								.filter(x -> UtilsText.isEmpty(x.getCanceledClmNo()))
								.filter(x -> UtilsText.equals(x.getPromoNo(), multiBuyPromoNo))
								.collect(Collectors.toList());

						// 다족구매 할인 재적용 상품 개수
						int _reApplyMultiBuyProductCnt = ObjectUtils.isEmpty(_adjustMultiBuyProductTargetList) ? 0
								: _adjustMultiBuyProductTargetList.size();

						// 새로 적용해야 할 다족구매 프로모션 할인 정보 확인
						PrPromotionMultiBuyDiscount _reApplyMultiBuyDiscountInfo = (PrPromotionMultiBuyDiscount) _multiBuyDiscountList
								.stream().filter(x -> x.getBuyQty() == _reApplyMultiBuyProductCnt).findFirst()
								.orElse(null);

						// 현재 클레임 기준 업체상품 전체 취소가 아니며, 이전 부분 취소 후 남은 상품 모두 취소가 아닌 경우(부분 취소 클레임)
						if ((_orderProductCnt != _thisTimeClaimCnt)
								&& (_orderProductCnt != _finishClaimCnt + _thisTimeClaimCnt)) {
							// 주문 상품 중 다족구매 할인 재적용 상품 존재(없는 경우는 현재 상품 금액 그대로 취소)
							if (_reApplyMultiBuyProductCnt > 0) {
								// 다족구매 할인 재적용 상품이 한 개인 경우 다족구매 할인금액 변경(다족구매 적용되지 않은 금액으로)
								if (_reApplyMultiBuyProductCnt == 1) {
									// 다족구매 재적용 상품이 1개인 경우는 할인 정보도 없으므로 할인정보 임의로 0 set
									_reApplyMultiBuyDiscountInfo = new PrPromotionMultiBuyDiscount();
									_reApplyMultiBuyDiscountInfo.setBuyQty(1);
									_reApplyMultiBuyDiscountInfo.setDscntRate((short) 0);
								}

								// 적용해야 할 다족구매 할인 적용 상품 개수가 등록된 다족구매 프로모션 최대 지정 개수보다 작을 때만 금액 재조정
								if (_reApplyMultiBuyProductCnt < _maxMultiBuyDiscountInfo.getBuyQty()) {
									for (OcClaimPromoVO targetProduct : _adjustMultiBuyProductTargetList) {
										// 이전 클레임에 의해 재 조정된 다족구매 리오더 매출 주문 확인
										OcOrderProduct _beforeAdjustProduct = new OcOrderProduct();
										_beforeAdjustProduct.setOrderNo(targetProduct.getOrderNo());
										_beforeAdjustProduct.setOrderNo(targetProduct.getOrderNo());
										_beforeAdjustProduct.setOrderPrdtSeq(targetProduct.getOrderPrdtSeq());
										_beforeAdjustProduct
												.setSalesCnclGbnType(CommonCode.SALES_CNCL_GBN_TYPE_MULTIBUY_SALE);

										_beforeAdjustProduct = ocOrderProductDao
												.selectRecentMultiBuyReApplyProduct(_beforeAdjustProduct);

										// 이전 클레임으로 재 조정된 다족구매 매출 상품이 없으면 처음 조정 대상이므로 원 주문에서 해당 상품 추출
										if (_beforeAdjustProduct == null) {
											_beforeAdjustProduct = mmnyOrgOrderProductList.stream()
													.filter(x -> UtilsText.equals(x.getOrderNo(),
															targetProduct.getOrderNo()))
													.filter(x -> UtilsText.equals(String.valueOf(x.getOrderPrdtSeq()),
															String.valueOf(targetProduct.getOrderPrdtSeq())))
													.findFirst().orElse(null);
										}

										// 이전 클레임에 의해 재 조정된 다족구매 할인 금액 정보 확인
										OcClaimProductApplyPromotion _recentClaimProductApplyPromotion = new OcClaimProductApplyPromotion();
										_recentClaimProductApplyPromotion
												.setOrderPrdtSeq(targetProduct.getOrderPrdtSeq());

										_recentClaimProductApplyPromotion = ocClaimProductApplyPromotionDao
												.selectRecentClaimProductApplyPromotion(
														_recentClaimProductApplyPromotion);

										// 이전 클레임으로 재 조정된 다족구매 할인 금액 정보가 없으면 처음 조정 대상이므로 원 주문 기준의 프로모션 할인금 정보 추출
										if (_recentClaimProductApplyPromotion == null) {

											OcOrderProductApplyPromotion _recentOrderProductApplyPromotion = orderApplyPromotionList
													.stream()
													.filter(x -> UtilsText.equals(x.getOrderNo(),
															targetProduct.getOrderNo()))
													.filter(x -> UtilsText.equals(String.valueOf(x.getOrderPrdtSeq()),
															String.valueOf(targetProduct.getOrderPrdtSeq())))
													.filter(x -> UtilsText.equals(x.getPromoTypeCode(),
															CommonCode.PROMO_TYPE_CODE_DISCOUNT_MULTI_SHOUES))
													.findFirst().orElse(null);

											_recentClaimProductApplyPromotion = new OcClaimProductApplyPromotion();

											BeanUtils.copyProperties(_recentOrderProductApplyPromotion,
													_recentClaimProductApplyPromotion); // 내용 복사

											_recentClaimProductApplyPromotion.setClmNo(targetProduct.getClmNo());
											// _recentClaimProductApplyPromotion.setOrderPrdtSeq(targetProduct.getOrderPrdtSeq());
										}

										int _prdtNormalAmt = _beforeAdjustProduct.getPrdtNormalAmt(); // 조정 대상상품 상품정상가
										int _prdtSellAmt = _beforeAdjustProduct.getPrdtSellAmt(); // 조정 대상상품 상품판매가
										int _optnAddAmt = _beforeAdjustProduct.getOptnAddAmt(); // 조정 대상상품 옵션추가금액
										int _sellAmt = _beforeAdjustProduct.getSellAmt(); // 조정 대상상품 판매가(상품판매가 + 옵션추가금)
										int _totalDscntAmt = _beforeAdjustProduct.getTotalDscntAmt(); // 조정 대상상품 할인금액합계
										int _cpnDscntAmt = _beforeAdjustProduct.getCpnDscntAmt(); // 조정 대상상품 쿠폰할인금액
										int _orderAmt = _beforeAdjustProduct.getOrderAmt(); // 조정 대상상품 주문금액
										int _compPrdtSellAmt = 0; // 조정 대상상품 상품판매가와 비교할 임시 금액
										int _multiBuyDscntAmt = _recentClaimProductApplyPromotion.getDscntAmt(); // 조정대상상품다족구매할인금

										int _adjustMultiBuyDscntAmt = 0; // 조정된 할인금액
										int _adjustTotalDscntAmt = 0; // 조정된 할인금액합계
										int _adjustOrderAmt = 0; // 조정된 주문금액
										int _differDscntAmt = 0; // 새롭게 적용된 상품할인금액과 기존 할인금액 차이

										int _newDscntRate = _reApplyMultiBuyDiscountInfo.getDscntRate(); // 새롭게 적용할 할인율

										// 새로 적용해야 할 할인율 퍼센티지를 이용하여 다족구매 할인금액 계산 - 옵션 추가금 포함
										_adjustMultiBuyDscntAmt = (int) Math
												.ceil((double) (_prdtNormalAmt + _optnAddAmt) * _newDscntRate / 10000)
												* 100;

										// 조정 대상상품 판매가와 비교할 임시 금액 ( (상품정상가+옵션추가금) - 조정된 다족구매 할인금 )
										_compPrdtSellAmt = (_prdtNormalAmt + _optnAddAmt) - _adjustMultiBuyDscntAmt;

										// 원 조정 대상상품 (판매가+옵션추가금) 이 비교할 임시 금액보다 낮은 경우
										if ((_prdtSellAmt + _optnAddAmt) < _compPrdtSellAmt) {
											// 조정된 할인금액 합계(조정된 다족구매 할인금 + 정액쿠폰 할인금)
											_adjustTotalDscntAmt = _cpnDscntAmt;
											// 조정된 주문금액(조정된판매가 - 조정된 할인금액 합계)
											_adjustOrderAmt = (_prdtSellAmt + _optnAddAmt) - _adjustTotalDscntAmt;

										}
										// 원 조정 대상상품 판매가가 조정된 다족구매 할인금을 적용한 금액보다 높은 경우
										else {
											// 조정된 할인금액 합계(조정된 다족구매 할인금 + 정액쿠폰 할인금)
											_adjustTotalDscntAmt = _adjustMultiBuyDscntAmt + _cpnDscntAmt;
											// 조정된 주문금액
											_adjustOrderAmt = (_prdtNormalAmt + _optnAddAmt) - _adjustTotalDscntAmt;
										}

										// 조정된 금액이 재조정 되기 이전과 다를 경우만 리오더 생성을 위한 재조정 리스트에 포함
										if (_orderAmt != _adjustOrderAmt) {
											// 다족구매로 인한 금액 재조정 되기 이전의 주문상품 목록 set
											beforeAdjustOrderProductByMultiBuyList.add(_beforeAdjustProduct);

											// 다족구매로 인한 금액 재조정 된 주문상품 목록 set
											OcOrderProduct _adjustedProduct = new OcOrderProduct();
											BeanUtils.copyProperties(_beforeAdjustProduct, _adjustedProduct); // 내용 복사

											_adjustedProduct.setTotalDscntAmt(_adjustTotalDscntAmt);
											_adjustedProduct.setOrderAmt(_adjustOrderAmt);

											adjustedOrderProductByMultiBuyList.add(_adjustedProduct);
										}

										// 다족구매로 인한 금액 재조정 된 주문상품 프로모션 변경 목록 set
										OcClaimProductApplyPromotion _adjustedClaimProductApplyPromotion = new OcClaimProductApplyPromotion();
										_adjustedClaimProductApplyPromotion.setClmNo(ocClaim.getClmNo());
										_adjustedClaimProductApplyPromotion.setOrderNo(targetProduct.getOrderNo());
										_adjustedClaimProductApplyPromotion
												.setOrderPrdtSeq(targetProduct.getOrderPrdtSeq());
										_adjustedClaimProductApplyPromotion
												.setApplyPromoSeq(_recentClaimProductApplyPromotion.getApplyPromoSeq());
										_adjustedClaimProductApplyPromotion
												.setPromoNo(_recentClaimProductApplyPromotion.getPromoNo());
										_adjustedClaimProductApplyPromotion
												.setPromoTypeCode(_recentClaimProductApplyPromotion.getPromoTypeCode());
										_adjustedClaimProductApplyPromotion
												.setDscntType(_recentClaimProductApplyPromotion.getDscntType());
										_adjustedClaimProductApplyPromotion.setDscntValue(_newDscntRate);
										_adjustedClaimProductApplyPromotion.setDscntAmt(_adjustMultiBuyDscntAmt);

										adjustedClaimProductApplyPromotionList.add(_adjustedClaimProductApplyPromotion);

										// 다족구매 클레임으로 발생된 환수금
										redempAmtByMultiBuy += (_adjustOrderAmt - _orderAmt);
									}
								}
							}
						}
					}
				}
			}
		}

		/*
		 * 리오더 매출이 있는 경우 다족구매 리오더 매출로 인한 실제 취소금과의 차액이 발생된 경우 결제히스토리 set
		 */
		if (addMultiBuyDifferAmt > 0) {
			// 실제 환불 금액은 아니나 매출 기준으로 계산을 맞추기 위하여 환불개념의 금액으로 가져감
			// 결제정보 set(히스토리용 이기 때문에 실결제와 관련된 내용은 의미없어도 무방)
			OcClaimPayment _addMultiBuyDifferPayment = new OcClaimPayment();
			_addMultiBuyDifferPayment.setClmNo(ocClaim.getClmNo());
			_addMultiBuyDifferPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REFUND); // 환수환불구분:환불
			_addMultiBuyDifferPayment.setPymntDtm(new Timestamp(new Date().getTime())); // 결제일시
			_addMultiBuyDifferPayment.setDeviceCode(orderPaymentList.get(0).getDeviceCode()); // 디바이스코드
			_addMultiBuyDifferPayment.setMainPymntMeansYn(Const.BOOLEAN_TRUE); // 주결제수단여부
			_addMultiBuyDifferPayment.setPymntMeansCode(orderPaymentList.get(0).getPymntMeansCode()); // 결제수단코드
			_addMultiBuyDifferPayment.setPymntVndrCode(null); // 결제업체코드
			_addMultiBuyDifferPayment.setInstmtTermCount((short) 0); // 할부기간
			_addMultiBuyDifferPayment.setPymntTodoAmt(addMultiBuyDifferAmt); // 결제예정금액
			_addMultiBuyDifferPayment.setPymntAmt(addMultiBuyDifferAmt); // 결제금액
			_addMultiBuyDifferPayment.setCardPartCnclPsbltYn(Const.BOOLEAN_FALSE); // 카드부분취소가능여부
			_addMultiBuyDifferPayment.setCashRcptIssueYn(Const.BOOLEAN_FALSE); // 현금영수증발급여부
			_addMultiBuyDifferPayment.setEscrApplyYn(Const.BOOLEAN_FALSE); // 에스크로적용여부
			_addMultiBuyDifferPayment.setAcntCrtfcYn(Const.BOOLEAN_FALSE); // 계좌인증여부
			_addMultiBuyDifferPayment.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 자사처리대상여부
			_addMultiBuyDifferPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_HISTORY); // 이력구분-이력근거용
			_addMultiBuyDifferPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_PROMOTION); // 발생사유코드:프로모션
			_addMultiBuyDifferPayment.setRgsterNo(ocClaim.getClaimRgsterNo()); // 등록자
			_addMultiBuyDifferPayment.setModerNo(ocClaim.getClaimRgsterNo()); // 수정자

			addMultiBuyDifferPaymentList.add(_addMultiBuyDifferPayment); // 환불 프로모션비 이력 set
		}

		/*
		 * 다족구매 클레임으로 발생된 환수금이 발생된 경우 결제히스토리 set
		 */
		if (redempAmtByMultiBuy > 0) {
			// 결제정보 set(히스토리용 이기 때문에 실결제와 관련된 내용은 의미없어도 무방)
			OcClaimPayment _redempMultiBuyPayment = new OcClaimPayment();
			_redempMultiBuyPayment.setClmNo(ocClaim.getClmNo());
			_redempMultiBuyPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP); // 환수환불구분:환수
			_redempMultiBuyPayment.setPymntDtm(new Timestamp(new Date().getTime())); // 결제일시
			_redempMultiBuyPayment.setDeviceCode(orderPaymentList.get(0).getDeviceCode()); // 디바이스코드
			_redempMultiBuyPayment.setMainPymntMeansYn(Const.BOOLEAN_TRUE); // 주결제수단여부
			_redempMultiBuyPayment.setPymntMeansCode(orderPaymentList.get(0).getPymntMeansCode()); // 결제수단코드
			_redempMultiBuyPayment.setPymntVndrCode(null); // 결제업체코드
			_redempMultiBuyPayment.setInstmtTermCount((short) 0); // 할부기간
			_redempMultiBuyPayment.setPymntTodoAmt(redempAmtByMultiBuy); // 결제예정금액
			_redempMultiBuyPayment.setPymntAmt(redempAmtByMultiBuy); // 결제금액
			_redempMultiBuyPayment.setCardPartCnclPsbltYn(Const.BOOLEAN_FALSE); // 카드부분취소가능여부
			_redempMultiBuyPayment.setCashRcptIssueYn(Const.BOOLEAN_FALSE); // 현금영수증발급여부
			_redempMultiBuyPayment.setEscrApplyYn(Const.BOOLEAN_FALSE); // 에스크로적용여부
			_redempMultiBuyPayment.setAcntCrtfcYn(Const.BOOLEAN_FALSE); // 계좌인증여부
			_redempMultiBuyPayment.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 자사처리대상여부
			_redempMultiBuyPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_HISTORY); // 이력구분-이력근거용
			_redempMultiBuyPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_PROMOTION); // 발생사유코드:프로모션
			_redempMultiBuyPayment.setRgsterNo(ocClaim.getClaimRgsterNo()); // 등록자
			_redempMultiBuyPayment.setModerNo(ocClaim.getClaimRgsterNo()); // 수정자

			redempMultiBuyPaymentList.add(_redempMultiBuyPayment); // 환수 프로모션비 이력 set
		}

		// 다족구매로 인한 금액 재조정 되기 이전의 주문상품 목록 vo set
		ocClaimAmountVO.setBeforeAdjustOrderProductByMultiBuyList(beforeAdjustOrderProductByMultiBuyList);
		// 다족구매로 인한 금액 재조정 된 주문상품 목록 vo set
		ocClaimAmountVO.setAdjustedOrderProductByMultiBuyList(adjustedOrderProductByMultiBuyList);
		// 다족구매로 인한 금액 재조정 된 주문상품 프로모션 변경 목록
		ocClaimAmountVO.setAdjustedClaimProductApplyPromotionList(adjustedClaimProductApplyPromotionList);
		// 환수프로모션비(다족구매로 인해 발생) vo set
		ocClaimAmountVO.setRedempAmtByMultiBuy(redempAmtByMultiBuy);
		// 환수 다족구매 결제 목록 vo set
		ocClaimAmountVO.setRedempMultiBuyPaymentList(redempMultiBuyPaymentList);
		// 다족구매 리오더 매출과 실제 결제금액 차이가 발생한 금액 목록(환불개념) vo set
		ocClaimAmountVO.setAddMultiBuyDifferPaymentList(addMultiBuyDifferPaymentList);
		// 다족구매 리오더 매출로 인한 실제 취소금과의 차액 vo set
		ocClaimAmountVO.setAddMultiBuyDifferAmt(addMultiBuyDifferAmt);

		log.error("### CLM_NO [" + ocClaim.getClmNo() + "] 부분취소 금액계산");
		log.error("######################### beforeAdjustOrderProductByMultiBuyList : "
				+ beforeAdjustOrderProductByMultiBuyList.toString());
		log.error("######################### adjustedOrderProductByMultiBuyList : "
				+ adjustedOrderProductByMultiBuyList.toString());
		log.error("######################### redempAmtByMultiBuy : {} ", redempAmtByMultiBuy);
		log.error("######################### addMultiBuyDifferAmt : {} ", addMultiBuyDifferAmt);

		/******************************************************************
		 * 
		 * 배송비 체크(환불/환수/기존 환수된 배송비)
		 * 
		 ******************************************************************/
		int refundAmtByDlvyProduct = 0; // 환불배송비
		int redempAmtByFreeDlvyProduct = 0; // 무료배송상품 취소로 인한 배송비 발생금(환수배송비)
		List<OcOrderProduct> cancelDlvyProductList = new ArrayList<OcOrderProduct>(); // 취소 대상 배송비 상품
		List<OcOrderProduct> redempDlvyProductList = new ArrayList<OcOrderProduct>(); // 환수할 배송비 상품
		List<OcClaimPayment> vndrRedempDlvyPaymentList = new ArrayList<OcClaimPayment>(); // (자사)업체별 환수 주문배송비
		int redempDlvyOrderPrdtSeq = 0; // 발생된 환수배송비 상품의 신규 prdtseq 기준 값
		int maxReOrderPrdtSeq = 0; // 리오더 상품 중 max prdt seq

		int previousRedempAmtDlvyProduct = 0; // 이전 환수된 배송비 발생금(환수배송비)
		List<OcOrderProduct> previousRedempDlvyProductList = new ArrayList<OcOrderProduct>(); // 이전 환수된 배송비 상품
		List<OcClaimPayment> previousRedempDlvyPaymentList = new ArrayList<OcClaimPayment>(); // 이전 환수된 배송비 결제 목록

		// 리오더 상품 중 max prdt seq 추출
		if (!ObjectUtils.isEmpty(reOrderProductList)) {
			maxReOrderPrdtSeq = reOrderProductList.stream().mapToInt(x -> x.getOrderPrdtSeq()).max().getAsInt();
		}

		// 리오더 상품 max prdtseq 와 원주문 상품 개수 비교하여 새롭게 생성될 prdtseq 의 기준을 잡는다.
		if (maxReOrderPrdtSeq >= orgOrderProductList.size()) {
			redempDlvyOrderPrdtSeq = maxReOrderPrdtSeq;
		} else {
			redempDlvyOrderPrdtSeq = orgOrderProductList.size();
		}

		// 주문취소인 경우만 계산
		if (UtilsText.equals(clmGbnCode, CommonCode.CLM_GBN_CODE_CANCEL)) {

			/**
			 * 자사상품 배송비 체크
			 */
			if (!ObjectUtils.isEmpty(mmnyThisTimeClaimProductList)) {

				// 현재 클레임 상품(사은품/배송비 제외)
				List<OcClaimProduct> _thisTimeclaimProductList = mmnyThisTimeClaimProductList.stream()
						.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
								&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
						.collect(Collectors.toList());

				// 2020.02.20 : 클레임 사유코드의 ADD_INFO2가 Y인 사유라면 환수발생비 X
				String redempDlvyAmtYn = _thisTimeclaimProductList.get(0).getClmRsnCodeAddInfo2();

				// 현재 클레임 개수(사은품/배송비 제외)
				long _thisTimeClaimCnt = _thisTimeclaimProductList.size();

				// 배송비 상품
				OcOrderProduct _dlvyProduct = mmnyOrgOrderProductList.stream()
						.filter(x -> UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
						.findFirst().orElse(null);

				// 기존 취소된 배송비 상품
				OcClaimProduct _canceledDlvyProduct = mmnyFinishedClaimProductList.stream()
						.filter(x -> UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
						.findFirst().orElse(null);

				// 기존 환수된 배송비 여부
				long _redemptDlvyCnt = claimAllPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP)) // 환수
						.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE)) // 재경팀처리 제외
						.filter(x -> UtilsText.equals(x.getOcrncRsnCode(),
								CommonCode.OCRNC_RSN_CODE_ORDER_DELIVERY_AMT)) // 주문배송비
						.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY)) // 히스토리
						.filter(x -> !UtilsText.equals(x.getClmNo(), ocClaim.getClmNo())) // 이번 클레임 제외
						.filter(x -> mmnyVndrNoList.contains(x.getVndrNo())) // 자사 업체번호
						.count();

				// 원 주문 상품 개수(사은품/배송비 제외)
				long _orderProductCnt = mmnyOrgOrderProductList.stream()
						.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
								&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
						.count();

				// 완료된 클레임 개수(사은품/배송비 제외)
				long _finishClaimCnt = mmnyFinishedClaimProductList.stream()
						.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
								&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
						.count();

				// 원 주문에 포함된 무료배송상품 개수
				long _orderClaimFreeDlvyProductCnt = mmnyOrgOrderProductList.stream()
						.filter(x -> UtilsText.equals(x.getFreeDlvyYn(), Const.BOOLEAN_TRUE)).count();

				// 완료된 클레임에 포함된 무료배송상품 개수
				long _finishClaimFreeDlvyProductCnt = mmnyFinishedClaimProductList.stream()
						.filter(x -> UtilsText.equals(x.getFreeDlvyYn(), Const.BOOLEAN_TRUE)).count();

				// 현재 클레임 무료배송상품 개수
				long _thisClaimFreeDlvyProductCnt = mmnyThisTimeClaimProductList.stream()
						.filter(x -> UtilsText.equals(x.getFreeDlvyYn(), Const.BOOLEAN_TRUE)).count();

				// 완료/진행 클레임의 무료배송상품 남은 개수를 확인
				long _remainFreeDlvyProductCnt = _orderClaimFreeDlvyProductCnt - _finishClaimFreeDlvyProductCnt
						- _thisClaimFreeDlvyProductCnt;

				// 원 주문 금액 sum(사은품/배송비 제외)
				int _orderAmtSum = mmnyOrgOrderProductList.stream()
						.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
								&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
						.mapToInt(x -> x.getOrderAmt()).sum();

				// 완료 클레임 금액 sum(사은품/배송비 제외)
				int _canceledAmtSum = mmnyFinishedClaimProductList.stream()
						.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
								&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
						.mapToInt(x -> x.getOrderAmt()).sum();

				// 현재 클레임 금액 sum(사은품/배송비 제외)
				int _thisTiemCancelAmtSum = _thisTimeclaimProductList.stream().mapToInt(x -> x.getOrderAmt()).sum();

				/**
				 * 환불배송비 처리
				 */
				// 현재 클레임 기준 자사상품 전체 취소, 이전 부분 취소 후 남은 상품 모두 취소인 경우
				if ((_orderProductCnt == _thisTimeClaimCnt)
						|| (_orderProductCnt == _finishClaimCnt + _thisTimeClaimCnt)) {
					// 지불한 배송비가 있으면 클레임 상품 추가 및 환불 대상(배송비 쿠폰을 사용하던 안하던 주문 배송비 행 처리)
					if (_dlvyProduct != null) {
						cancelDlvyProductList.add(_dlvyProduct); // 취소될 배송비 상품 set
						refundAmtByDlvyProduct += _dlvyProduct.getOrderAmt(); // 환불배송비 set
					}
				}

				/**
				 * 환수배송비 체크
				 */
				// 기본전제 : 초도 주문 배송비가 없어야 함
				if (_dlvyProduct == null) {
					// 1.현재 클레임을 제외한 남은 무료배송상품이 없고
					// 2.기 환수된 배송비 없고
					// 3.남은상품 모두 취소 아니고
					// 4.배송비 면제금액보다 낮으면 발생

					// 2020.02.20 : 5. 클레임 사유코드의 ADD_INFO2가 Y인 사유라면 환수발생비 X

					if (_remainFreeDlvyProductCnt == 0 && _redemptDlvyCnt == 0
							&& (_orderProductCnt != _finishClaimCnt + _thisTimeClaimCnt)
							&& (_orderAmtSum - _canceledAmtSum - _thisTiemCancelAmtSum < sySite.getFreeDlvyStdrAmt())
							&& !UtilsText.equals(redempDlvyAmtYn, Const.BOOLEAN_TRUE)) {

						OcOrderProduct _redempDlvyProduct = new OcOrderProduct(); // 상품
						OcClaimPayment _redempDlvyAmt = new OcClaimPayment(); // 결제

						redempDlvyOrderPrdtSeq += 1;

						// 환수 배송비 상품정보 set
						_redempDlvyProduct.setOrderNo(null); // 주문 생성시 지정
						_redempDlvyProduct.setOrderPrdtSeq((Integer) (redempDlvyOrderPrdtSeq)); // 기존 주문상품 기준으로 +1
						_redempDlvyProduct.setUpOrderPrdtSeq(null);
						_redempDlvyProduct.setPrdtNo("1900000001"); // 배송비상품번호 임의 set
						_redempDlvyProduct.setPrdtOptnNo("001"); // 배송비옵션번호 임의 set
						_redempDlvyProduct.setPrdtName("클레임배송비");
						_redempDlvyProduct.setEngPrdtName("Delivery charge");
						_redempDlvyProduct.setOptnName("클레임배송비");
						_redempDlvyProduct.setPrdtTypeCode(CommonCode.PRDT_TYPE_CODE_DELIVERY);
						_redempDlvyProduct.setStyleInfo("DELIVERY_CHARGE");
						_redempDlvyProduct.setRsvPrdtYn(Const.BOOLEAN_FALSE); // 예약상품여부
						_redempDlvyProduct.setVndrNo(_thisTimeclaimProductList.get(0).getVndrNo()); // 업체번호:첫번째상품의업체번호기준
						_redempDlvyProduct.setVndrName(_thisTimeclaimProductList.get(0).getVndrName()); // 업체명:첫번째상품의업체번호기준
						_redempDlvyProduct.setChnnlNo(_thisTimeclaimProductList.get(0).getChnnlNo()); // 채널번호
						_redempDlvyProduct.setMmnyPrdtYn(Const.BOOLEAN_TRUE); // 자사상품여부
						_redempDlvyProduct.setEmpDscntYn(Const.BOOLEAN_FALSE); // 임직원할인여부
						_redempDlvyProduct.setOrderMnfctYn(Const.BOOLEAN_FALSE); // 주문제작여부
						_redempDlvyProduct.setDprcExceptYn(Const.BOOLEAN_FALSE); // 감가제외여부
						_redempDlvyProduct.setFreeDlvyYn(Const.BOOLEAN_FALSE); // 무료배송여부
						_redempDlvyProduct.setOrderQty(1); // 주문수량
						_redempDlvyProduct.setPrdtNormalAmt(sySite.getDlvyAmt()); // 상품정상가
						_redempDlvyProduct.setPrdtSellAmt(sySite.getDlvyAmt()); // 상품판매가
						_redempDlvyProduct.setOptnAddAmt(0); // 옵션추가금액
						_redempDlvyProduct.setSellAmt(sySite.getDlvyAmt()); // 판매가
						_redempDlvyProduct.setEmpDscntRate((short) 0); // 임직원할인율
						_redempDlvyProduct.setEmpAmt(0); // 임직원가
						_redempDlvyProduct.setTotalDscntAmt(0); // 할인금액합계
						_redempDlvyProduct.setCpnDscntAmt(0); // 쿠폰할인금액
						_redempDlvyProduct.setOrderAmt(sySite.getDlvyAmt()); // 주문금액
						_redempDlvyProduct.setVndrCmsnRate((short) 0); // 업체수수료율
						_redempDlvyProduct.setSellCnclReqYn(Const.BOOLEAN_FALSE); // 판매취소요청여부
						_redempDlvyProduct.setLogisCnvrtYn(Const.BOOLEAN_FALSE); // 택배전환여부
						_redempDlvyProduct.setPickupExtsnYn(Const.BOOLEAN_FALSE); // 픽업연장여부
						_redempDlvyProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_COMPLETE); // 주문상품상태코드:결제완료
						_redempDlvyProduct.setGenderGbnCode(CommonCode.GENDER_GBN_CODE_COMMON);
						_redempDlvyProduct.setBuyPointSaveRate((short) 0); // 구매포인트적립율
						_redempDlvyProduct.setExcclcDcsnYmd(new Timestamp(new Date().getTime())); // 정산확정일자를 넣는다
						_redempDlvyProduct.setRgsterNo(ocClaim.getClaimRgsterNo());
						_redempDlvyProduct.setModerNo(ocClaim.getClaimRgsterNo());

						redempDlvyProductList.add(_redempDlvyProduct); // 환수할 배송비 상품 set

						// 결제정보 set(히스토리용 이기 때문에 실결제와 관련된 내용은 의미없어도 무방)
						_redempDlvyAmt.setClmNo(ocClaim.getClmNo());
						_redempDlvyAmt.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP); // 환수환불구분:환수
						_redempDlvyAmt.setPymntDtm(new Timestamp(new Date().getTime())); // 결제일시
						_redempDlvyAmt.setDeviceCode(orderPaymentList.get(0).getDeviceCode()); // 디바이스코드
						_redempDlvyAmt.setMainPymntMeansYn(Const.BOOLEAN_TRUE); // 주결제수단여부
						_redempDlvyAmt.setPymntMeansCode(orderPaymentList.get(0).getPymntMeansCode()); // 결제수단코드
						_redempDlvyAmt.setPymntVndrCode(null); // 결제업체코드
						_redempDlvyAmt.setInstmtTermCount((short) 0); // 할부기간
						_redempDlvyAmt.setPymntTodoAmt(sySite.getDlvyAmt()); // 결제예정금액
						_redempDlvyAmt.setPymntAmt(sySite.getDlvyAmt()); // 결제금액
						_redempDlvyAmt.setCardPartCnclPsbltYn(Const.BOOLEAN_FALSE); // 카드부분취소가능여부
						_redempDlvyAmt.setCashRcptIssueYn(Const.BOOLEAN_FALSE); // 현금영수증발급여부
						_redempDlvyAmt.setEscrApplyYn(Const.BOOLEAN_FALSE); // 에스크로적용여부
						_redempDlvyAmt.setAcntCrtfcYn(Const.BOOLEAN_FALSE); // 계좌인증여부
						_redempDlvyAmt.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 자사처리대상여부
						_redempDlvyAmt.setHistGbnType(CommonCode.HIST_GBN_TYPE_HISTORY); // 이력구분 - 이력근거용
						_redempDlvyAmt.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_ORDER_DELIVERY_AMT); // 발생사유코드:주문배송비
						_redempDlvyAmt.setVndrNo(_thisTimeclaimProductList.get(0).getVndrNo()); // 업체번호:자사업체코드
						_redempDlvyAmt.setRgsterNo(ocClaim.getClaimRgsterNo()); // 등록자
						_redempDlvyAmt.setModerNo(ocClaim.getClaimRgsterNo()); // 수정자

						vndrRedempDlvyPaymentList.add(_redempDlvyAmt); // 업체별 환수 주문배송비 이력 set
						redempAmtByFreeDlvyProduct += sySite.getDlvyAmt(); // 환수배송비 set
					}
				}

				/**
				 * 이전 환수된 배송비 환불 체크
				 */
				// 자사/업체별로 이전 부분 취소 후 남은 상품 모두 취소인 경우에 확인하여 다시 환불해줘야 함
				if (_orderProductCnt == _finishClaimCnt + _thisTimeClaimCnt) {
					int _previousRedempAmtDlvyProduct = 0; // 이전 환수된 배송비 발생금(환수배송비)
					List<OcOrderProduct> _previousRedempDlvyProductList = new ArrayList<OcOrderProduct>(); // 이전환수된배송비상품
					List<OcClaimPayment> _previousRedempDlvyPaymentList = new ArrayList<OcClaimPayment>(); // 이전환수된배송비결제목록
					List<String> mmnyVndrNos = _thisTimeclaimProductList.stream().map(OcClaimProduct::getVndrNo)
							.distinct().collect(Collectors.toList()); // 자사 vndrNo

					// 이전 환수된 배송비 금액
					_previousRedempAmtDlvyProduct = claimAllPaymentList.stream()
							.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(),
									CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP)) // 환수
							.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE)) // 재경팀처리 제외
							.filter(x -> UtilsText.equals(x.getOcrncRsnCode(),
									CommonCode.OCRNC_RSN_CODE_ORDER_DELIVERY_AMT)) // 주문배송비
							.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY)) // 히스토리
							.filter(x -> !UtilsText.equals(x.getClmNo(), ocClaim.getClmNo())) // 이번 클레임 제외
							.filter(x -> mmnyVndrNos.contains(x.getVndrNo())) // 자사 vndrNo 포함
							.mapToInt(x -> x.getPymntAmt()).sum();

					// 이전 환수된 배송비 결제 목록
					_previousRedempDlvyPaymentList = claimAllPaymentList.stream()
							.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(),
									CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP)) // 환수
							.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE)) // 재경팀처리 제외
							.filter(x -> UtilsText.equals(x.getOcrncRsnCode(),
									CommonCode.OCRNC_RSN_CODE_ORDER_DELIVERY_AMT)) // 주문배송비
							.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY)) // 히스토리
							.filter(x -> !UtilsText.equals(x.getClmNo(), ocClaim.getClmNo())) // 이번 클레임 제외
							.filter(x -> mmnyVndrNos.contains(x.getVndrNo())) // 자사 vndrNo 포함
							.collect(Collectors.toList());

					// 이전 환수된 배송비 상품(리오더 매출 발생 상품)
					_previousRedempDlvyProductList = reOrderProductList.stream()
							.filter(x -> UtilsText.equals(x.getSalesCnclGbnType(),
									CommonCode.SALES_CNCL_GBN_TYPE_REDEMP_DLVY_AMT)) // 환수배송비매출
							.filter(x -> mmnyVndrNos.contains(x.getVndrNo())) // 자사 vndrNo 포함
							.collect(Collectors.toList());

					// 자사와 업체 모두를 포함하기 위한 변수에 set
					previousRedempAmtDlvyProduct += _previousRedempAmtDlvyProduct;
					previousRedempDlvyPaymentList.addAll(_previousRedempDlvyPaymentList);
					previousRedempDlvyProductList.addAll(_previousRedempDlvyProductList);
				}
			}

			/**
			 * 업체상품 배송비 체크
			 */
			if (!ObjectUtils.isEmpty(vndrNoList)) {
				for (String vndrNo : vndrNoList) {
					// 해당 업체 현재 클레임 상품(사은품/배송비 제외)
					List<OcClaimProduct> _thisTimeclaimProductList = vndrThisTimeClaimProductList.stream()
							.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
									&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
							.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList());

					// 2020.02.20 : 클레임 사유코드의 ADD_INFO2가 Y인 사유라면 환수발생비 X
					String redempDlvyAmtYn = _thisTimeclaimProductList.get(0).getClmRsnCodeAddInfo2();

					// 현재 클레임 개수(사은품/배송비 제외)
					long _thisTimeClaimCnt = _thisTimeclaimProductList.size();

					// 배송비 상품
					OcOrderProduct _dlvyProduct = vndrOrgOrderProductList.stream()
							.filter(x -> UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
							.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).findFirst().orElse(null);

					// 기존 취소된 배송비 상품
					OcClaimProduct _canceledDlvyProduct = vndrFinishedClaimProductList.stream()
							.filter(x -> UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
							.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).findFirst().orElse(null);

					// 기존 환수된 배송비 여부
					long _redemptDlvyCnt = claimAllPaymentList.stream()
							.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(),
									CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP)) // 환수
							.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE)) // 재경팀처리 제외
							.filter(x -> UtilsText.equals(x.getOcrncRsnCode(),
									CommonCode.OCRNC_RSN_CODE_ORDER_DELIVERY_AMT)) // 주문배송비
							.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY)) // 히스토리
							.filter(x -> !UtilsText.equals(x.getClmNo(), ocClaim.getClmNo())) // 이번 클레임 제외
							.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)) // 업체번호
							.count();

					// 원 주문 상품 개수(사은품/배송비 제외)
					long _orderProductCnt = vndrOrgOrderProductList.stream()
							.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
									&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
							.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).count();

					// 완료된 클레임 개수(사은품/배송비 제외)
					long _finishClaimCnt = vndrFinishedClaimProductList.stream()
							.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
									&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
							.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).count();

					// 원 주문에 포함된 무료배송상품 개수
					long _orderClaimFreeDlvyProductCnt = vndrOrgOrderProductList.stream()
							.filter(x -> UtilsText.equals(x.getFreeDlvyYn(), Const.BOOLEAN_TRUE))
							.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).count();

					// 완료된 클레임에 포함된 무료배송상품 개수
					long _finishClaimFreeDlvyProductCnt = vndrFinishedClaimProductList.stream()
							.filter(x -> UtilsText.equals(x.getFreeDlvyYn(), Const.BOOLEAN_TRUE))
							.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).count();

					// 현재 클레임 무료배송상품 개수
					long _thisClaimFreeDlvyProductCnt = vndrThisTimeClaimProductList.stream()
							.filter(x -> UtilsText.equals(x.getFreeDlvyYn(), Const.BOOLEAN_TRUE))
							.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).count();

					// 완료/진행 클레임의 무료배송상품 남은 개수를 확인
					long _remainFreeDlvyProductCnt = _orderClaimFreeDlvyProductCnt - _finishClaimFreeDlvyProductCnt
							- _thisClaimFreeDlvyProductCnt;

					// 원 주문 금액 sum(사은품/배송비 제외)
					int _orderAmtSum = vndrOrgOrderProductList.stream()
							.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
									&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
							.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).mapToInt(x -> x.getOrderAmt()).sum();

					// 완료 클레임 금액 sum(사은품/배송비 제외)
					int _canceledAmtSum = vndrFinishedClaimProductList.stream()
							.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
									&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
							.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).mapToInt(x -> x.getOrderAmt()).sum();

					// 현재 클레임 금액 sum(사은품/배송비 제외)
					int _thisTiemCancelAmtSum = _thisTimeclaimProductList.stream()
							.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).mapToInt(x -> x.getOrderAmt()).sum();

					/**
					 * 환불배송비 처리
					 */
					// 현재 클레임 기준 업체상품 전체 취소, 이전 부분 취소 후 남은 상품 모두 취소인 경우
					if ((_orderProductCnt == _thisTimeClaimCnt)
							|| (_orderProductCnt == _finishClaimCnt + _thisTimeClaimCnt)) {
						// 지불한 배송비가 있으면 클레임 상품 추가 및 환불 대상(배송비 쿠폰을 사용하던 안하던 주문 배송비 행 처리)
						if (_dlvyProduct != null) {
							cancelDlvyProductList.add(_dlvyProduct); // 취소될 배송비 상품 set
							refundAmtByDlvyProduct += _dlvyProduct.getOrderAmt(); // 환불배송비 set
						}
					}

					VdVendor vdVendor = new VdVendor();
					vdVendor.setVndrNo(vndrNo);
					vdVendor = vdVendorDao.selectByPrimaryKey(vdVendor);

					/**
					 * 환수배송비 체크
					 */
					// 기본전제 : 초도 주문 배송비가 없어야 함
					if (_dlvyProduct == null) {
						// 1.현재 클레임을 제외한 남은 무료배송상품이 없고
						// 2.기 환수된 배송비 없고
						// 3.남은상품 모두 취소 아니고
						// 4.배송비 면제금액보다 낮으면 발생

						// 2020.02.20 : 5. 클레임 사유코드의 ADD_INFO2가 Y인 사유라면 환수발생비 X

						if (_remainFreeDlvyProductCnt == 0 && _redemptDlvyCnt == 0
								&& (_orderProductCnt != _finishClaimCnt + _thisTimeClaimCnt)
								&& (_orderAmtSum - _canceledAmtSum - _thisTiemCancelAmtSum < sySite
										.getFreeDlvyStdrAmt())
								&& !UtilsText.equals(redempDlvyAmtYn, Const.BOOLEAN_TRUE)) {

							OcOrderProduct _redempDlvyProduct = new OcOrderProduct(); // 상품
							OcClaimPayment _redempDlvyAmt = new OcClaimPayment(); // 결제

							redempDlvyOrderPrdtSeq += 1;

							// 환수 배송비 상품정보 set
							_redempDlvyProduct.setOrderNo(null); // 주문 생성시 지정
							_redempDlvyProduct.setOrderPrdtSeq((Integer) (redempDlvyOrderPrdtSeq)); // 기존 주문상품 기준으로 +1
							_redempDlvyProduct.setUpOrderPrdtSeq(null);
							_redempDlvyProduct.setPrdtNo("1900000001"); // 배송비상품번호 임의 set
							_redempDlvyProduct.setPrdtOptnNo("001"); // 배송비옵션번호 임의 set
							_redempDlvyProduct.setPrdtName("클레임배송비");
							_redempDlvyProduct.setEngPrdtName("Delivery charge");
							_redempDlvyProduct.setOptnName("클레임배송비");
							_redempDlvyProduct.setPrdtTypeCode(CommonCode.PRDT_TYPE_CODE_DELIVERY);
							_redempDlvyProduct.setStyleInfo("DELIVERY_CHARGE");
							_redempDlvyProduct.setRsvPrdtYn(Const.BOOLEAN_FALSE); // 예약상품여부
							_redempDlvyProduct.setVndrNo(_thisTimeclaimProductList.get(0).getVndrNo()); // 업체번호:첫번째상품의업체번호기준
							_redempDlvyProduct.setVndrName(_thisTimeclaimProductList.get(0).getVndrName()); // 업체명:첫번째상품의업체번호기준
							_redempDlvyProduct.setChnnlNo(_thisTimeclaimProductList.get(0).getChnnlNo()); // 채널번호
							_redempDlvyProduct.setMmnyPrdtYn(Const.BOOLEAN_FALSE); // 자사상품여부
							_redempDlvyProduct.setEmpDscntYn(Const.BOOLEAN_FALSE); // 임직원할인여부
							_redempDlvyProduct.setOrderMnfctYn(Const.BOOLEAN_FALSE); // 주문제작여부
							_redempDlvyProduct.setDprcExceptYn(Const.BOOLEAN_FALSE); // 감가제외여부
							_redempDlvyProduct.setFreeDlvyYn(Const.BOOLEAN_FALSE); // 무료배송여부
							_redempDlvyProduct.setOrderQty(1); // 주문수량
							_redempDlvyProduct.setPrdtNormalAmt(vdVendor.getDlvyAmt()); // 상품정상가
							_redempDlvyProduct.setPrdtSellAmt(vdVendor.getDlvyAmt()); // 상품판매가
							_redempDlvyProduct.setOptnAddAmt(0); // 옵션추가금액
							_redempDlvyProduct.setSellAmt(vdVendor.getDlvyAmt()); // 판매가
							_redempDlvyProduct.setEmpDscntRate((short) 0); // 임직원할인율
							_redempDlvyProduct.setEmpAmt(0); // 임직원가
							_redempDlvyProduct.setTotalDscntAmt(0); // 할인금액합계
							_redempDlvyProduct.setCpnDscntAmt(0); // 쿠폰할인금액
							_redempDlvyProduct.setOrderAmt(vdVendor.getDlvyAmt()); // 주문금액
							_redempDlvyProduct.setVndrCmsnRate((short) 0); // 업체수수료율
							_redempDlvyProduct.setSellCnclReqYn(Const.BOOLEAN_FALSE); // 판매취소요청여부
							_redempDlvyProduct.setLogisCnvrtYn(Const.BOOLEAN_FALSE); // 택배전환여부
							_redempDlvyProduct.setPickupExtsnYn(Const.BOOLEAN_FALSE); // 픽업연장여부
							_redempDlvyProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_COMPLETE); // 주문상품상태코드:결제완료
							_redempDlvyProduct.setGenderGbnCode(CommonCode.GENDER_GBN_CODE_COMMON);
							_redempDlvyProduct.setBuyPointSaveRate((short) 0); // 구매포인트적립율
							_redempDlvyProduct.setExcclcDcsnYmd(new Timestamp(new Date().getTime())); // 정산확정일자를 넣는다
							_redempDlvyProduct.setRgsterNo(ocClaim.getClaimRgsterNo());
							_redempDlvyProduct.setModerNo(ocClaim.getClaimRgsterNo());

							redempDlvyProductList.add(_redempDlvyProduct); // 환수할 배송비 상품 set

							// 결제정보 set(히스토리용 이기 때문에 실결제와 관련된 내용은 의미없어도 무방)
							_redempDlvyAmt.setClmNo(ocClaim.getClmNo());
							_redempDlvyAmt.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP); // 환수환불구분:환수
							_redempDlvyAmt.setPymntDtm(new Timestamp(new Date().getTime())); // 결제일시
							_redempDlvyAmt.setDeviceCode(orderPaymentList.get(0).getDeviceCode()); // 디바이스코드
							_redempDlvyAmt.setMainPymntMeansYn(Const.BOOLEAN_TRUE); // 주결제수단여부
							_redempDlvyAmt.setPymntMeansCode(orderPaymentList.get(0).getPymntMeansCode()); // 결제수단코드
							_redempDlvyAmt.setPymntVndrCode(null); // 결제업체코드
							_redempDlvyAmt.setInstmtTermCount((short) 0); // 할부기간
							_redempDlvyAmt.setPymntTodoAmt(vdVendor.getDlvyAmt()); // 결제예정금액
							_redempDlvyAmt.setPymntAmt(vdVendor.getDlvyAmt()); // 결제금액
							_redempDlvyAmt.setCardPartCnclPsbltYn(Const.BOOLEAN_FALSE); // 카드부분취소가능여부
							_redempDlvyAmt.setCashRcptIssueYn(Const.BOOLEAN_FALSE); // 현금영수증발급여부
							_redempDlvyAmt.setEscrApplyYn(Const.BOOLEAN_FALSE); // 에스크로적용여부
							_redempDlvyAmt.setAcntCrtfcYn(Const.BOOLEAN_FALSE); // 계좌인증여부
							_redempDlvyAmt.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 자사처리대상여부
							_redempDlvyAmt.setHistGbnType(CommonCode.HIST_GBN_TYPE_HISTORY); // 이력구분 - 이력근거용
							_redempDlvyAmt.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_ORDER_DELIVERY_AMT); // 발생사유코드:주문배송비
							_redempDlvyAmt.setVndrNo(_thisTimeclaimProductList.get(0).getVndrNo()); // 업체번호:자사업체코드
							_redempDlvyAmt.setRgsterNo(ocClaim.getClaimRgsterNo()); // 등록자
							_redempDlvyAmt.setModerNo(ocClaim.getClaimRgsterNo()); // 수정자

							vndrRedempDlvyPaymentList.add(_redempDlvyAmt); // 업체별 환수 주문배송비 이력 set
							redempAmtByFreeDlvyProduct += vdVendor.getDlvyAmt(); // 환수배송비 set
						}
					}

					/**
					 * 이전 환수된 배송비 환불 체크
					 */
					// 자사/업체별로 이전 부분 취소 후 남은 상품 모두 취소인 경우에 확인하여 다시 환불해줘야 함
					if (_orderProductCnt == _finishClaimCnt + _thisTimeClaimCnt) {
						int _previousRedempAmtDlvyProduct = 0; // 이전 환수된 배송비 발생금(환수배송비)
						List<OcOrderProduct> _previousRedempDlvyProductList = new ArrayList<OcOrderProduct>(); // 이전환수된배송비상품
						List<OcClaimPayment> _previousRedempDlvyPaymentList = new ArrayList<OcClaimPayment>(); // 이전환수된배송비결제목록

						// 이전 환수된 배송비 금액
						_previousRedempAmtDlvyProduct = claimAllPaymentList.stream()
								.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(),
										CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP)) // 환수
								.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE)) // 재경팀처리 제외
								.filter(x -> UtilsText.equals(x.getOcrncRsnCode(),
										CommonCode.OCRNC_RSN_CODE_ORDER_DELIVERY_AMT)) // 주문배송비
								.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY)) // 히스토리
								.filter(x -> !UtilsText.equals(x.getClmNo(), ocClaim.getClmNo())) // 이번 클레임 제외
								.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)) // 업체 vndrNo
								.mapToInt(x -> x.getPymntAmt()).sum();

						// 이전 환수된 배송비 결제 목록
						_previousRedempDlvyPaymentList = claimAllPaymentList.stream()
								.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(),
										CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP)) // 환수
								.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE)) // 재경팀처리 제외
								.filter(x -> UtilsText.equals(x.getOcrncRsnCode(),
										CommonCode.OCRNC_RSN_CODE_ORDER_DELIVERY_AMT)) // 주문배송비
								.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY)) // 히스토리
								.filter(x -> !UtilsText.equals(x.getClmNo(), ocClaim.getClmNo())) // 이번 클레임 제외
								.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)) // 업체 vndrNo
								.collect(Collectors.toList());

						// 이전 환수된 배송비 상품(리오더 매출 발생 상품)
						_previousRedempDlvyProductList = reOrderProductList.stream()
								.filter(x -> UtilsText.equals(x.getSalesCnclGbnType(),
										CommonCode.SALES_CNCL_GBN_TYPE_REDEMP_DLVY_AMT)) // 환수배송비매출
								.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)) // 업체 vndrNo
								.collect(Collectors.toList());

						// 자사와 업체 모두를 포함하기 위한 변수에 set
						previousRedempAmtDlvyProduct += _previousRedempAmtDlvyProduct;
						previousRedempDlvyPaymentList.addAll(_previousRedempDlvyPaymentList);
						previousRedempDlvyProductList.addAll(_previousRedempDlvyProductList);
					}
				}
			}
		}

		// 환수할 배송비 상품 set
		ocClaimAmountVO.setRedempDlvyProductList(redempDlvyProductList);
		// 환수 주문배송비 결제 목록(이력용 - 업체별)
		ocClaimAmountVO.setVndrRedempDlvyPaymentList(vndrRedempDlvyPaymentList);
		// 취소시킬(환불대상) 배송비 상품
		ocClaimAmountVO.setCancelDlvyProductList(cancelDlvyProductList);
		// 환불배송비 vo set
		ocClaimAmountVO.setRefundAmtByDlvyProduct(refundAmtByDlvyProduct);
		// 환수배송비 vo set
		ocClaimAmountVO.setRedempAmtByFreeDlvyProduct(redempAmtByFreeDlvyProduct);

		// 이전 환수된 배송비 발생금(환수배송비) set
		ocClaimAmountVO.setPreviousRedempAmtDlvyProduct(previousRedempAmtDlvyProduct);
		// 이전 환수된 배송비 상품 set
		ocClaimAmountVO.setPreviousRedempDlvyProductList(previousRedempDlvyProductList);
		// 이전 환수된 배송비 결제 목록 set
		ocClaimAmountVO.setPreviousRedempDlvyPaymentList(previousRedempDlvyPaymentList);
		log.debug("######################### previousRedempAmtDlvyProduct : {} ", previousRedempAmtDlvyProduct);

		/******************************************************************
		 * 
		 * 클레임 배송비 체크(반품 해당)
		 * 
		 ******************************************************************/
		int claimDlvyAmt = 0; // 클레임배송비

		// 반품인 경우만 계산하며 환불금차감인 경우만 취소환불 금액에 포함한다.
		if (UtilsText.equals(clmGbnCode, CommonCode.CLM_GBN_CODE_RETURN)) {
			if (ocClaim.getAddDlvyAmt() > 0) {
				if (UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(),
						CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_REFUND_DEDUCTION)) {

					claimDlvyAmt = ocClaim.getAddDlvyAmt();
				}
			}
		}

		// 클레임배송비 vo set
		ocClaimAmountVO.setClaimDlvyAmt(claimDlvyAmt);

		/******************************************************************
		 * 
		 * 증감 적립포인트 계산
		 * 
		 ******************************************************************/
		// 원 주문 적립 포인트
		double orgOrderSavePoint = orgOrderProductList.stream()
				.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
						&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
				.mapToDouble(o -> Math.ceil(o.getOrderAmt() * o.getBuyPointSaveRate() / 1000.0) * 10).sum();
		log.debug("######################### orgOrderSavePoint : {} ", orgOrderSavePoint);

		// 현재 클레임 이전 기준 증가 적립포인트
		double beforeClaimSavePlusPoint = reOrderProductList.stream()
				.filter(x -> UtilsText.equals(x.getSalesCnclGbnType(), CommonCode.SALES_CNCL_GBN_TYPE_SALE)
						|| UtilsText.equals(x.getSalesCnclGbnType(), CommonCode.SALES_CNCL_GBN_TYPE_MULTIBUY_SALE))
				.filter(x -> !UtilsText.equals(x.getClmNo(), ocClaim.getClmNo()))
				.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
						&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
				.mapToDouble(o -> Math.ceil(o.getOrderAmt() * o.getBuyPointSaveRate() / 1000.0) * 10).sum();
		log.debug("######################### beforeClaimSavePlusPoint : {} ", beforeClaimSavePlusPoint);

		// 현재 클레임 이전 기준 취소 적립포인트
		double beforeClaimSaveMinusPoint = reOrderProductList.stream()
				.filter(x -> UtilsText.equals(x.getSalesCnclGbnType(), CommonCode.SALES_CNCL_GBN_TYPE_CANCEL)
						|| UtilsText.equals(x.getSalesCnclGbnType(), CommonCode.SALES_CNCL_GBN_TYPE_MULTIBUY_CANCEL))
				.filter(x -> !UtilsText.equals(x.getClmNo(), ocClaim.getClmNo()))
				.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
						&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
				.mapToDouble(o -> Math.ceil(o.getOrderAmt() * o.getBuyPointSaveRate() / 1000.0) * 10).sum();

		double beforeClaimVariationSavePoint = beforeClaimSavePlusPoint - beforeClaimSaveMinusPoint;
		log.debug("######################### beforeClaimVariationSavePoint : {} ", beforeClaimVariationSavePoint);

		// 현재 클레임 기준 취소 적립포인트
		double thisClaimSaveMinusPoint = thisTimeClaimProductList.stream()
				.filter(x -> UtilsText.equals(x.getClmNo(), ocClaim.getClmNo()))
				.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
						&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
				.mapToDouble(o -> Math.ceil(o.getOrderAmt() * o.getBuyPointSaveRate() / 1000.0) * 10).sum();
		log.debug("######################### thisClaimSaveMinusPoint : {} ", thisClaimSaveMinusPoint);

		// 다족구매로 인한 금액 재조정 되기 이전의 주문상품 포인트(매출 대상)
		double multiBuySavePlusPoint = adjustedOrderProductByMultiBuyList.stream()
				.mapToDouble(o -> Math.ceil(o.getOrderAmt() * o.getBuyPointSaveRate() / 1000.0) * 10).sum();
		log.debug("######################### multiBuySavePlusPoint : {} ", multiBuySavePlusPoint);

		// 다족구매로 인한 금액 재조정 된 주문상품 포인트(매출취소 대상)
		double multiBuySaveMinusPoint = beforeAdjustOrderProductByMultiBuyList.stream()
				.mapToDouble(o -> Math.ceil(o.getOrderAmt() * o.getBuyPointSaveRate() / 1000.0) * 10).sum();
		log.debug("######################### multiBuySaveMinusPoint : {} ", multiBuySaveMinusPoint);

		// 현재 클레임 기준 변동 적립포인트
		double thisClaimVariationSavePoint = multiBuySavePlusPoint - multiBuySaveMinusPoint - thisClaimSaveMinusPoint;
		log.debug("######################### thisClaimVariationSavePoint : {} ", thisClaimVariationSavePoint);

		// 최종 변동 적립포인트
		double variationSavePoint = beforeClaimVariationSavePoint + thisClaimVariationSavePoint;

		ocClaimAmountVO.setOrgOrderSavePoint((int) orgOrderSavePoint);
		ocClaimAmountVO.setVariationSavePoint((int) variationSavePoint);

		log.debug("######################### variationSavePoint : {} ", variationSavePoint);

		/******************************************************************
		 * 
		 * 클레임 금액 기본 계산
		 * 
		 ******************************************************************/
		// 주문기준 총 결제금액(=최초결제금액)
		ocClaimAmountVO.setTotalOrderPymntAmt(ocOrder.getPymntAmt());
		// 주문기준 총 취소된 금액
		ocClaimAmountVO.setTotalOrderCnclAmt(ocOrder.getCnclAmt());
		// 총 잔여 취소가능금액
		ocClaimAmountVO.setTotalCnclAbleRemainAmt(
				ocClaimAmountVO.getTotalOrderPymntAmt() - ocClaimAmountVO.getTotalOrderCnclAmt());

		int expectCnclAmt = 0; // 현재 클레임으로 인해 취소될 금액
		int refundCnclAmt = 0; // 실제 취소로 인해 환불될 금액

		// 현재 클레임으로 인해 취소될 금액(클레임상품금액 + 환불배송비)
		expectCnclAmt = thisTimeClaimProductList.stream().mapToInt(o -> o.getOrderAmt()).sum() + refundAmtByDlvyProduct;

		// 현재 취소 클레임으로 인해 환불될 금액
		// 다족구매 환수금 포함, 배송비 환수금 포함, 클레임 배송비(환불금 차감) 제외, 이전 발생한환수배송비 포함
		// 다족구매 리오더 매출로 인한 실제 취소금과의 차액이 발생된 금액 포함
		refundCnclAmt = expectCnclAmt - redempAmtByMultiBuy - redempAmtByFreeDlvyProduct - claimDlvyAmt
				+ previousRedempAmtDlvyProduct + addMultiBuyDifferAmt;

		// 분실 취소인 경우 상품금액 및 환불배송비 계산 금액을 실제 환불될 금액으로 계산한다.
		if (isMissCancel) {
			refundCnclAmt = expectCnclAmt;
		}
		log.error("######################### expectCnclAmt : {} ", expectCnclAmt);
		log.error("######################### redempAmtByMultiBuy : {} ", redempAmtByMultiBuy);
		log.error("######################### redempAmtByFreeDlvyProduct : {} ", redempAmtByFreeDlvyProduct);
		log.error("######################### claimDlvyAmt : {} ", claimDlvyAmt);
		log.error("######################### previousRedempAmtDlvyProduct : {} ", previousRedempAmtDlvyProduct);
		log.error("######################### addMultiBuyDifferAmt : {} ", addMultiBuyDifferAmt);
		log.error("######################### ocOrder.getPymntAmt() : {} ", ocOrder.getPymntAmt());
		log.error("######################### ocOrder.getCnclAmt() : {} ", ocOrder.getCnclAmt());
		log.error("######################### refundCnclAmt 환불금액 : {} ", refundCnclAmt);
		if (refundCnclAmt > ocOrder.getPymntAmt() - ocOrder.getCnclAmt()) {
			throw new Exception("validMsg:환불금액이 남은 결제금액을 초과하여 취소가 불가합니다.");
		}

		ocClaimAmountVO.setExpectCnclAmt(expectCnclAmt);
		ocClaimAmountVO.setRefundCnclAmt(refundCnclAmt);

		// ocClaimAmountVO.setTotalCnclAmt(expectCnclAmt);

		ocClaimAmountVO.setTotalRedempAmt(redempAmtByMultiBuy + redempAmtByFreeDlvyProduct);
		log.error("######################### TotalRedempAmt : {} ", redempAmtByMultiBuy + redempAmtByFreeDlvyProduct);

		/******************************************************************
		 * 
		 * 결제 취소금액 결제수단 별 계산
		 * 
		 ******************************************************************/
		OcClaimPayment mainPayment = null;
		OcClaimPayment mainTempPayment = null;
		OcClaimPayment giftPayment = null;
		OcClaimPayment pointPayment = null;
		OcClaimPayment eventPointPayment = null;

		// 이벤트 포인트 사용 가능 최대 금액(이벤트 포인트 환불 적용 기준)
		int maxUsableEventPointOrderAmt = notClaimOrderProductList.stream().filter(o -> o.getOrderAmt() >= 30000)
				.mapToInt(o -> o.getOrderAmt()).sum();

		for (OcClaimPayment claimPayment : orderClaimPaymentList) {
			if (UtilsText.equals(claimPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_MOBILE_GIFT)) { // 기프트
				if (claimPayment.getRemainPymntCnclAmt() > 0) {
					giftPayment = claimPayment;
				}
			} else if (UtilsText.equals(claimPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_BUY_POINT)) { // 구매포인트
				if (claimPayment.getRemainPymntCnclAmt() > 0) {
					pointPayment = claimPayment;
				}
			} else if (UtilsText.equals(claimPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_EVENT_POINT)) { // 이벤트포인트
				if (claimPayment.getRemainPymntCnclAmt() > 0) {
					eventPointPayment = claimPayment;
				}
			} else {
				if (claimPayment.getRemainPymntCnclAmt() > 0) {
					mainPayment = claimPayment;
					mainTempPayment = claimPayment;
				}
			}
		}

		int mainPymntAmt = (mainPayment == null) ? 0 : mainPayment.getRemainPymntCnclAmt();
		int giftPymntAmt = (giftPayment == null) ? 0 : giftPayment.getRemainPymntCnclAmt();
		int pointPymntAmt = (pointPayment == null) ? 0 : pointPayment.getRemainPymntCnclAmt();
		int eventPointPymntAmt = (eventPointPayment == null) ? 0 : eventPointPayment.getRemainPymntCnclAmt();

		ocClaimAmountVO.setMainPayment(mainPayment);
		ocClaimAmountVO.setGiftPayment(giftPayment);
		ocClaimAmountVO.setPointPayment(pointPayment);
		ocClaimAmountVO.setEventPointPayment(eventPointPayment);

		int mainRfndAmt = 0;
		int giftRfndAmt = 0;
		int pointRfndAmt = 0;
		int eventPointRfndAmt = 0;

		/**
		 * @Desc : 이벤트 포인트 환불 대상 금액이 있는 경우 클레임이 걸리지 않은 남은 상품의 금액 조건을 확인
		 * @Desc : 1. 남은 상품 금액이 이벤트 포인트 사용 조건이 되지 않는 경우 환불 순서는 다음과 같다.
		 * @Desc : 이벤트포인트>주결제수단>기프트카드>구매포인트 순으로 환불
		 * @Desc : 2. 남은 상품 금액이 이벤트 포인트 사용 조건이 되는 경우 환불 순서는 다음과 같다.
		 * @Desc : 주결제수단>이벤트포인트>기프트카드>구매포인트 순으로 환불
		 * @Desc : 3. 이벤트 포인트 환불 대상 금액이 없는 경우는 주결제부터 차감한다.
		 */
		// 이벤트 포인트 환불 대상 금액이 있는 경우
		if (eventPointPymntAmt > 0) {
			// 클레임 상품 외 이벤트포인트 적용될 상품이 없고 취소할 이벤트 포인트 있는 경우
			if (maxUsableEventPointOrderAmt == 0) {
				// 취소 할 이벤트 포인트
				if (eventPointPymntAmt >= refundCnclAmt) {
					eventPointRfndAmt = refundCnclAmt;
					refundCnclAmt = 0;
				} else {
					eventPointRfndAmt = eventPointPymntAmt;
					refundCnclAmt -= eventPointRfndAmt;
				}

				// 취소 할 주결제
				if (refundCnclAmt > 0 && mainPymntAmt > 0) {
					if (mainPymntAmt >= refundCnclAmt) {
						mainRfndAmt = refundCnclAmt;
						refundCnclAmt = 0;
					} else {
						mainRfndAmt = mainPymntAmt;
						refundCnclAmt -= mainRfndAmt;
					}
				}
			} else {
				// 취소 할 주결제
				if (mainPymntAmt > 0) {
					if (mainPymntAmt >= refundCnclAmt) {
						mainRfndAmt = refundCnclAmt;
						refundCnclAmt = 0;
					} else {
						mainRfndAmt = mainPymntAmt;
						refundCnclAmt -= mainRfndAmt;
					}
				}

				// 취소 할 이벤트 포인트
				if (refundCnclAmt > 0) {
					if (eventPointPymntAmt >= refundCnclAmt) {
						eventPointRfndAmt = refundCnclAmt;
						refundCnclAmt = 0;
					} else {
						eventPointRfndAmt = eventPointPymntAmt;
						refundCnclAmt -= eventPointRfndAmt;
					}
				}
			}
		}
		// 이벤트 포인트 환불 대상 금액이 없는 경우
		else {
			// 취소 할 주결제
			if (mainPymntAmt > 0) {
				if (mainPymntAmt >= refundCnclAmt) {
					mainRfndAmt = refundCnclAmt;
					refundCnclAmt = 0;
				} else {
					mainRfndAmt = mainPymntAmt;
					refundCnclAmt -= mainRfndAmt;
				}
			}
		}

		// 클레임 상품 외 이벤트포인트 적용될 상품이 없고 취소할 이벤트 포인트 있는 경우
//		if (eventPointPymntAmt > 0) {
//			if (eventPointPymntAmt >= refundCnclAmt) {
//				eventPointRfndAmt = refundCnclAmt;
//				refundCnclAmt = 0;
//			} else {
//				eventPointRfndAmt = eventPointPymntAmt;
//				refundCnclAmt -= eventPointRfndAmt;
//			}
//		}

		// 취소 할 주결제
//		if (refundCnclAmt > 0 && mainPymntAmt > 0) {
//			if (mainPymntAmt >= refundCnclAmt) {
//				mainRfndAmt = refundCnclAmt;
//				refundCnclAmt = 0;
//			} else {
//				mainRfndAmt = mainPymntAmt;
//				refundCnclAmt -= mainRfndAmt;
//			}
//		}

		// 취소 할 기프트카드
		if (refundCnclAmt > 0 && giftPymntAmt > 0) {
			if (giftPymntAmt >= refundCnclAmt) {
				giftRfndAmt = refundCnclAmt;
				refundCnclAmt = 0;
			} else {
				giftRfndAmt = giftPymntAmt;
				refundCnclAmt -= giftRfndAmt;
			}
		}

		// 취소 할 구매포인트
		if (refundCnclAmt > 0 && pointPymntAmt > 0) {
			if (pointPymntAmt >= refundCnclAmt) {
				pointRfndAmt = refundCnclAmt;
				refundCnclAmt = 0;
			} else {
				pointRfndAmt = pointPymntAmt;
				refundCnclAmt -= pointRfndAmt;
			}
		}

		if (ocClaimAmountVO.getEventPointPayment() != null) {
			ocClaimAmountVO.getEventPointPayment().setPymntAmt(eventPointRfndAmt);
			ocClaimAmountVO.getEventPointPayment().setPymntTodoAmt(eventPointRfndAmt);
		}

		if (ocClaimAmountVO.getGiftPayment() != null) {
			ocClaimAmountVO.getGiftPayment().setPymntAmt(giftRfndAmt);
			ocClaimAmountVO.getGiftPayment().setPymntTodoAmt(giftRfndAmt);
		}

		if (ocClaimAmountVO.getMainPayment() != null) {
			ocClaimAmountVO.getMainPayment().setPymntAmt(mainRfndAmt);
			ocClaimAmountVO.getMainPayment().setPymntTodoAmt(mainRfndAmt);
		}

		if (ocClaimAmountVO.getPointPayment() != null) {
			ocClaimAmountVO.getPointPayment().setPymntAmt(pointRfndAmt);
			ocClaimAmountVO.getPointPayment().setPymntTodoAmt(pointRfndAmt);
		}

		ocClaimAmountVO.setEventPointCnclAmt(eventPointRfndAmt);
		ocClaimAmountVO.setGiftCnclAmt(giftRfndAmt);
		ocClaimAmountVO.setMainCnclAmt(mainRfndAmt);
		ocClaimAmountVO.setPointCnclAmt(pointRfndAmt);

		return ocClaimAmountVO;
	}

	/**
	 * @Desc : 결제 취소
	 * @Method Name : setCancelPayment
	 * @Date : 2019. 5. 31.
	 * @Author : KTH
	 * @param paymentCancelInfo
	 * @param ocClaim
	 * @param useEscr
	 * @throws Exception
	 */
	public OcClaimAmountVO setCancelPayment(OcClaimAmountVO paymentCancelInfo, OcClaim ocClaim, MbMember mbMember) {

		log.error("클레임 결제취소 시작 claimNo : {}", ocClaim.getClmNo());
		
		ObjectMapper mapper = new ObjectMapper();
		PaymentResult paymentResult = null; // 결제취소 결과(KCP, NAVER, KAKAO)
		boolean pointCancelSuccess = false; // 포인트 취소 성공여부
		boolean isRefundOccurrence = false; // 환불접수 상황 발생 여부

		/*
		 * 2020.03.23 : 원주문 주결제 수단 조회
		 */
		OcOrderPayment orgOrderMainPayment = null;
		try {
			orgOrderMainPayment = orderService.getMainPayment(ocClaim.getOrgOrderNo());
			log.error("주결제수단 조회 orderNo : " + ocClaim.getOrgOrderNo());
		} catch (Exception e1) {
			log.error("주결제수단 조회 실패 orderNo : " + ocClaim.getOrgOrderNo());
			e1.printStackTrace();
		}

		/*
		 * 결제 취소 정보 저장
		 */
		OcClaimPayment mainPayment = paymentCancelInfo.getMainPayment();
		OcClaimPayment giftPayment = paymentCancelInfo.getGiftPayment();
		OcClaimPayment pointPayment = paymentCancelInfo.getPointPayment();
		OcClaimPayment eventPointPayment = paymentCancelInfo.getEventPointPayment();

		/*
		 * 구매/이벤트 포인트 사용 취소
		 */
		if ((pointPayment != null && pointPayment.getPymntAmt() > 0)
				|| (eventPointPayment != null && eventPointPayment.getPymntAmt() > 0)) {
			// 포인트 결제 취소 처리
			pointCancelSuccess = this.setCancelOrderUsePoint(ocClaim, pointPayment, eventPointPayment, mbMember);

			/*
			 * 구매/이벤트 포인트 사용 취소 결과 등록
			 */
			OcClaimPayment pointCancelResult = new OcClaimPayment();

			if (pointCancelSuccess) {
				if (pointPayment != null) {
					BeanUtils.copyProperties(pointPayment, pointCancelResult); // 내용 복사

					pointCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL); // 결제취소
					this.setClaimPayment(ocClaim, pointCancelResult, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
				}

				if (eventPointPayment != null) {
					BeanUtils.copyProperties(eventPointPayment, pointCancelResult); // 내용 복사

					pointCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL); // 결제취소
					this.setClaimPayment(ocClaim, pointCancelResult, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
				}
			} else {
				if (pointPayment != null) {

					log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					log.error("claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
					log.error("pointPaymentCancel is fail : 구매포인트 결제취소 실패");
					log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

					BeanUtils.copyProperties(pointPayment, pointCancelResult); // 내용 복사
					isRefundOccurrence = true; // 환불접수 상황 발생

					pointCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 환불접수
					this.setClaimPayment(ocClaim, pointCancelResult, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
				}

				if (eventPointPayment != null) {

					log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					log.error("claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
					log.error("eventPointPaymentCancel is fail : 이벤트포인트 결제취소 실패");
					log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

					BeanUtils.copyProperties(eventPointPayment, pointCancelResult); // 내용 복사
					isRefundOccurrence = true; // 환불접수 상황 발생

					pointCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 환불접수
					this.setClaimPayment(ocClaim, pointCancelResult, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
				}
			}
		}

		// 이미 수기로 환불을 진행한 경우이므로 포인트는 환불하되, PG사 호출을 통한 환불처리는 skip
		if (paymentCancelInfo.isCancelByForce()) {
			return paymentCancelInfo;
		}

		/*
		 * 기프트 사용 취소
		 */
		if (giftPayment != null && giftPayment.getPymntAmt() > 0) {
			// 기프트 결제 취소
			Map<String, Object> chargeResultMap = this.setCancelNicePayment(ocClaim, giftPayment,
					paymentCancelInfo.isAllCancelYn());

			/*
			 * 기프트 사용 취소 결과 등록
			 */
			OcClaimPayment giftCancelResult = new OcClaimPayment();
			BeanUtils.copyProperties(giftPayment, giftCancelResult); // 내용 복사

			String giftCardNo = (String) chargeResultMap.get("giftCardNo");

			giftCancelResult.setPymntOrganNoText(giftCardNo); // 기프트카드 번호 set

			if (chargeResultMap.get("chargeResult") == null) {
				isRefundOccurrence = true; // 환불접수 상황 발생

				giftCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 환불접수
				giftCancelResult.setRspnsCodeText("9999");
				giftCancelResult.setRspnsMesgText("결제취소 실패");

				log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				log.error("claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
				log.error("giftCancelResult is null : 기프트카드 결제취소 실패");
				log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

				this.setClaimPayment(ocClaim, giftCancelResult, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			} else {
				@SuppressWarnings("unchecked")
				CommNiceRes<ChargeResponse> chargeResult = (CommNiceRes<ChargeResponse>) chargeResultMap
						.get("chargeResult");

				int chargeGiftAmt = (Integer) chargeResultMap.get("chargeGiftAmt");

				if (UtilsText.equals(chargeResult.getResCode(), "0000")) {
					giftCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL); // 결제취소

					// 결제취소가 성공한경우 기프트카드 현금 영수증 테이블 인서트
					OcCashReceipt giftCashReceipt = new OcCashReceipt();
					giftCashReceipt.setRcptType(CommonCode.RCPT_TYPE_DEDUCTION); // 소득공제
					giftCashReceipt.setRcptIssueInfo(mbMember.getHdphnNoText()); // 휴대폰번호
					giftCashReceipt.setOrderNo(ocClaim.getReOrderNo()); // 리오더주문번호
					giftCashReceipt.setOrgOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호
					giftCashReceipt.setClmNo(ocClaim.getClmNo()); // 클레임 번호
					giftCashReceipt.setPymntOrganNoText(giftCardNo); // 카드번호
					giftCashReceipt.setPymntAprvNoText(chargeResult.getResData().getApprovalNo()); // 결제승인번호
					giftCashReceipt.setRcptProcStatCode(CommonCode.RCPT_PROC_STAT_CODE_ACCEPT);
					giftCashReceipt.setOcrncAmt(chargeGiftAmt); // 발생금액
					giftCashReceipt.setRgsterNo(ocClaim.getClaimRgsterNo()); // 등록자

					try {
						ocCashReceiptDao.insertCashReceipt(giftCashReceipt);
					} catch (Exception e) {
						log.error("기프트 현금영수증 등록 에러 - giftCashReceipt : {}", giftCashReceipt);
						e.printStackTrace();
					}
				} else {

					log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					log.error("claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
					log.error("giftCancelResult is fail : 기프트카드 결제취소 실패");
					log.error("chargeResult.getResCode() : " + chargeResult.getResCode());
					log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

					isRefundOccurrence = true; // 환불접수 상황 발생
					giftCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 환불접수
				}

				giftCancelResult.setRspnsCodeText(chargeResult.getResCode());
				giftCancelResult.setRspnsMesgText(chargeResult.getResMsg());

				try {
					giftCancelResult.setPymntLogInfo(mapper.writeValueAsString(chargeResult.getResData()));

					// 결제 리턴 로그
					log.error("+++++++++++++++++++  PymntLogInfo");
					log.error("claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
					log.error(giftCancelResult.getPymntLogInfo());
					log.error("+++++++++++++++++++++++++++++++++");

				} catch (JsonProcessingException e) {
					log.error("결제처리 데이터 변환 에러 - claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
					giftCancelResult.setPymntLogInfo(null);
					e.printStackTrace();
				}

				this.setClaimPayment(ocClaim, giftCancelResult, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 메인 결제수단 취소 내용이 없으면 skip
		if (mainPayment == null || mainPayment.getPymntAmt() == 0) {
			return paymentCancelInfo;
		}

		/*
		 * 주결제 결제 취소
		 */
		if (paymentCancelInfo.isAllCancelYn()) { // 전체취소
			if (UtilsText.equals(mainPayment.getPymntVndrCode(), CommonCode.PYMNT_VNDR_CODE_KCP)) {
				if (UtilsText.equals(mainPayment.getEscrApplyYn(), Const.BOOLEAN_TRUE)) { // 에스크로 결제
					String escrowModType = "";

					if (!ObjectUtils.isEmpty(mainPayment.getEscrSendDtm())) {
						if (UtilsText.equals(mainPayment.getPymntMeansCode(),
								CommonCode.PYMNT_MEANS_CODE_ACCOUNT_TRANSFER)) { // 계좌이체
							escrowModType = CommonCode.KCP_ESCROW_MOD_TYPE_STE9_A;
						} else {
							escrowModType = CommonCode.KCP_ESCROW_MOD_TYPE_STE9_V;
						}
					} else {
						if (UtilsText.equals(mainPayment.getPymntMeansCode(),
								CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT)
								&& UtilsText.equals(mainPayment.getPymntStatCode(),
										CommonCode.PYMNT_STAT_CODE_WAIT_DEPOSIT)) { // 가상계좌 입금대기
							escrowModType = CommonCode.KCP_ESCROW_MOD_TYPE_STE5;
						} else {
							escrowModType = CommonCode.KCP_ESCROW_MOD_TYPE_STE2;
						}
					}

					paymentResult = this.setCancelKcpEscrowPayment(ocClaim, mainPayment, escrowModType, true);
				} else {
					if (UtilsText.equals(mainPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT)
							&& UtilsText.equals(mainPayment.getPymntStatCode(),
									CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH)) { // 가상계좌 결제완료

						this.setVcntRefundKcpPayment(ocClaim, mainPayment, true, true);
					} else {
						// 무통장입금 결제 선택후 입금하지 않은 상태에서 취소시 계좌 폐쇄하지않고 열어둠
						// 무통장입금 거래 주문후에 고객이 입금 후 kcp에서 계좌확인 코드가 리턴되기전에 고객이 주문취소한경우 예치금으로 돌리기 위해 계좌 열어둠
						if (UtilsText.equals(mainPayment.getPymntMeansCode(),
								CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT)
								&& UtilsText.equals(mainPayment.getPymntStatCode(),
										CommonCode.PYMNT_STAT_CODE_WAIT_DEPOSIT)) { // 가상계좌 입금대기
							paymentResult = new PaymentResult(PaymentConst.YN_Y, "0000", "", null, "가상계좌입금전취소SKIP",
									null);

						} else {

							// 2020.03.23 : 주결제가 휴대폰결제 일 시에 휴대폰결제 주문금과 취소할 금액이 같다면 전체 아니면 부분
							if (UtilsText.equals(orgOrderMainPayment.getPymntMeansCode(),
									CommonCode.PYMNT_MEANS_CODE_HANDPHONE)) {
								log.error("휴대폰결제 주문 결제금액 : {}", orgOrderMainPayment.getPymntAmt());
								log.error("취소할 휴대폰결제 금액 : {}", mainPayment.getPymntAmt());
								// 휴대폰결제 주문금과 취소할 금액이 같다면 전체
								if (orgOrderMainPayment.getPymntAmt().equals(mainPayment.getPymntAmt())) {
									paymentResult = this.setCancelKcpPayment(ocClaim, mainPayment, true, true);
								}
								// 휴대폰결제 주문금과 취소할 금액이 같않다면 부분
								else {
									paymentResult = this.setCancelKcpPayment(ocClaim, mainPayment, false, true);
								}
							} else {
								// 2020.03.23 : 주결제가 휴대폰결제이 아니라면 기존 로직
								paymentResult = this.setCancelKcpPayment(ocClaim, mainPayment, true, true);
							}
						}
					}
				}
			} else if (UtilsText.equals(mainPayment.getPymntVndrCode(), CommonCode.PYMNT_VNDR_CODE_NAVER)) {
				paymentResult = this.setCancelNaverPayment(mainPayment, ocClaim, true);
			} else if (UtilsText.equals(mainPayment.getPymntVndrCode(), CommonCode.PYMNT_VNDR_CODE_KAKAO)) {
				paymentResult = this.setCancelKakaoPayment(ocClaim, mainPayment, true);
			}
		} else {
			// 결제부분취소
			if (UtilsText.equals(mainPayment.getPymntVndrCode(), CommonCode.PYMNT_VNDR_CODE_KCP)) {
				if (UtilsText.equals(mainPayment.getEscrApplyYn(), Const.BOOLEAN_TRUE)) { // 에스크로 결제

					// 2020.03.13 : escrSendDtm 이 null이 아니면 재경팀 환불은 넘어가지 않게
					if (!ObjectUtils.isEmpty(mainPayment.getEscrSendDtm())) {

						if (UtilsText.equals(mainPayment.getPymntMeansCode(),
								CommonCode.PYMNT_MEANS_CODE_ACCOUNT_TRANSFER)) {
							// 에스크로 적용 실시간 계좌이체
							paymentResult = this.setCancelKcpEscrowPayment(ocClaim, mainPayment,
									CommonCode.KCP_ESCROW_MOD_TYPE_STE9_AP, true);
						} else {
							paymentResult = this.setCancelKcpEscrowPayment(ocClaim, mainPayment,
									CommonCode.KCP_ESCROW_MOD_TYPE_STE9_VP, true);
						}
					}
					// 2020.03.16 : escrSendDtm이 null이 라면 재경팀 환불금 접수로 넘어가게 수정
					else {
						paymentResult = new PaymentResult(Const.BOOLEAN_FALSE, "9999", null, null,
								"escrSendDtm이 null이기때문에 재경팀환불금접수", null);
					}

				} else {
					if (UtilsText.equals(mainPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT)
							&& UtilsText.equals(mainPayment.getPymntStatCode(),
									CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH)) { // 가상계좌 결제완료
						paymentResult = this.setVcntRefundKcpPayment(ocClaim, mainPayment, false, true);
					} else {

						// 2020.03.23 : 주결제가 휴대폰결제 일 시에 휴대폰결제 주문금과 취소할 금액이 같다면 전체 아니면 부분
						if (UtilsText.equals(orgOrderMainPayment.getPymntMeansCode(),
								CommonCode.PYMNT_MEANS_CODE_HANDPHONE)) {
							log.error("휴대폰결제 주문 결제금액 : {}", orgOrderMainPayment.getPymntAmt());
							log.error("취소할 휴대폰결제 금액 : {}", mainPayment.getPymntAmt());
							// 휴대폰결제 주문금과 취소할 금액이 같다면 전체
							if (orgOrderMainPayment.getPymntAmt().equals(mainPayment.getPymntAmt())) {
								paymentResult = this.setCancelKcpPayment(ocClaim, mainPayment, true, true);
							}
							// 휴대폰결제 주문금과 취소할 금액이 같않다면 부분
							else {
								paymentResult = this.setCancelKcpPayment(ocClaim, mainPayment, false, true);
							}
						} else {
							// 2020.03.23 : 주결제가 휴대폰결제이 아니라면 기존 로직
							paymentResult = this.setCancelKcpPayment(ocClaim, mainPayment, false, true);
						}
					}
				}
			} else if (UtilsText.equals(mainPayment.getPymntVndrCode(), CommonCode.PYMNT_VNDR_CODE_NAVER)) {
				paymentResult = this.setCancelNaverPayment(mainPayment, ocClaim, false);
			} else if (UtilsText.equals(mainPayment.getPymntVndrCode(), CommonCode.PYMNT_VNDR_CODE_KAKAO)) {
				paymentResult = this.setCancelKakaoPayment(ocClaim, mainPayment, false);
			}
		}

		/*
		 * 주결제 결제 취소 결과 등록
		 */
		OcClaimPayment mainCancelResult = new OcClaimPayment();
		BeanUtils.copyProperties(mainPayment, mainCancelResult); // 내용 복사

		if (paymentResult == null) {
			isRefundOccurrence = true; // 환불접수 상황 발생
			mainCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 환불접수
			mainCancelResult.setRspnsCodeText("9999");
			mainCancelResult.setRspnsMesgText("결제취소 실패");

			log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			log.error("claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
			log.error("paymentResult is null : 결제취소 실패");
			log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

			this.setClaimPayment(ocClaim, mainCancelResult, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
		} else {

			log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			log.error("claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
			log.error("paymentResult is not null : ");
			log.error("paymentResult.getSuccessYn() : " + paymentResult.getSuccessYn());
			log.error("paymentResult.getCode() : " + paymentResult.getCode());
			log.error("paymentResult.getMessage() : " + paymentResult.getMessage());
			log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

			if (UtilsText.equals(paymentResult.getSuccessYn(), Const.BOOLEAN_TRUE)) {
				mainCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL); // 결제취소
			} else {
				// isRefundOccurrence = true; // 환불접수 상황 발생
				// mainCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT);

				if (UtilsText.equals(mainCancelResult.getPymntVndrCode(), CommonCode.PYMNT_VNDR_CODE_KCP)) {
					// KCP 결제취소 실패
					// 2020.02.13 : [8133] KCP에  기취소처리된 신용카드 거래건에 다시 취소요청 시 발생.
					// 2020.02.14 : [8150] KCP에 이미 처리된 부분매입취소 거래건에 다시 취소요청 시 발생.
					// 2020.03.19 : [8729] KCP 계좌이체 기 취소된 거래
					// 2020.03.19 : [8233] KCP 기취소된 휴대폰 거래 취소요청
					if (!UtilsText.equals(paymentResult.getCode(), Const.KCP_ALRDY_CANCEL_CODE_CARD)
							&& !UtilsText.equals(paymentResult.getCode(), Const.KCP_ALRDY_CANCEL_CODE_PART)
							&& !UtilsText.equals(paymentResult.getCode(), Const.KCP_ALRDY_CANCEL_CODE_ACCOUNT)
							&& !UtilsText.equals(paymentResult.getCode(), Const.KCP_ALRDY_CANCEL_CODE_HANDPHONE)) {
						// [8133]이 아닌 KCP 거래취소 실패 응답코드는 '재경팀 환불 접수'
						// [8150]부분매입취소후 남은금액의 합계가 원거래 금액과 동일하고,
						// 남은 금액이 DB의 부분매입취소 가능금액과 동일한 경우 이미 처리된 요청건으로 간주함
						isRefundOccurrence = true; // 환불접수 상황 발생
						mainCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 환불접수
					} else {
						// [8133]나 [8150]면 취소완료
						isRefundOccurrence = false; // 환불접수 상황 X
						mainCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL);
					}

				} else if (UtilsText.equals(mainCancelResult.getPymntVndrCode(), CommonCode.PYMNT_VNDR_CODE_KAKAO)) {
					// KAKAO 결제취소 실패
					// 2020.02.17 : [-711] KAKAO에 이미 취소요청하여 취소처리된 거래건을 다시 취소요청 시 발생.
					if (!UtilsText.equals(paymentResult.getCode(), Const.KAKAO_ALRDY_CANCEL_CODE)) {
						// [-711]이 아닌 KAKAO 거래취소 실패 응답코드는 '재경팀 환불 접수'
						isRefundOccurrence = true; // 환불접수 상황 발생
						mainCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 환불접수
					} else {
						// [-711]면 취소완료
						isRefundOccurrence = false; // 환불접수 상황 X
						mainCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL);
					}

				} else if (UtilsText.equals(mainCancelResult.getPymntVndrCode(), CommonCode.PYMNT_VNDR_CODE_NAVER)) {
					// NAVER 결제취소 실패
					// 2020.04.02 : [AlreadyCanceled] 이미 전체 취소된 결제
					if (!UtilsText.equals(paymentResult.getCode(), Const.NAVER_ALRDY_CANCEL_CODE)) {
						// [AlreadyCanceled]이 아닌 NAVER 거래취소 실패 응답코드는 '재경팀 환불 접수'
						isRefundOccurrence = true; // 환불접수 상황 발생
						mainCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 환불접수
					} else {
						// [AlreadyCanceled]면 취소완료
						isRefundOccurrence = false; // 환불접수 상황 X
						mainCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL);
					}
					
				} else {
					isRefundOccurrence = true; // 환불접수 상황 발생
					mainCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 환불접수
				}

			}

			String message = paymentResult.getCode();
			if(message != null) {
				if (UtilsText.getByteLength(message) > 20) {
					message = UtilsText.strByteSubstring(message, 0, 15) + "...";
				}
			}
			
			mainCancelResult.setRspnsCodeText(message);
			mainCancelResult.setRspnsMesgText(paymentResult.getMessage());

			try {
				mainCancelResult.setPymntLogInfo(mapper.writeValueAsString(paymentResult.getData()));

				// 결제 리턴 로그
				log.error("+++++++++++++++++++  PymntLogInfo");
				log.error(mainCancelResult.getPymntLogInfo());
				log.error("+++++++++++++++++++++++++++++++++");

			} catch (JsonProcessingException e) {
				log.error("결제처리 데이터 변환 에러 - claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
				mainCancelResult.setPymntLogInfo(null);
				log.error("결제처리 데이터 변환 에러 - Exception e : " + e);
			}

			this.setClaimPayment(ocClaim, mainCancelResult, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
		}

		/*******************
		 * 환불발생 상황 set
		 *******************/
		paymentCancelInfo.setRefundOccurrence(isRefundOccurrence);

		return paymentCancelInfo;
	}

	/**
	 * @Desc : 품절보상 쿠폰 지급
	 * @Method Name : setGiveSoldOutCmpns
	 * @Date : 2019. 6. 15.
	 * @Author : KTH
	 * @param claim
	 * @param claimProductList
	 * @throws Exception
	 */
	public boolean setGiveSoldOutCmpns(OcClaim claim, List<OcClaimProduct> claimProductList) throws Exception {
		// 사이트 정책에 등록된 품절보상 쿠폰
		PrCoupon soldOutCmpnsCpn = prCouponDao.selectSoldOutCmpnsCpnPolicy(); // soldOutCompensation

		// 지급 대상 품절보상 쿠폰이 있는 경우만
		if (soldOutCmpnsCpn != null) {

			String memberNo = claim.getMemberNo();

			MbMemberCoupon mbMemberCoupon = new MbMemberCoupon();
			mbMemberCoupon.setMemberNo(memberNo);
			mbMemberCoupon.setCpnNo(soldOutCmpnsCpn.getCpnNo());
			mbMemberCoupon.setValidStartDtm(soldOutCmpnsCpn.getValidStartDtm());
			mbMemberCoupon.setValidEndDtm(soldOutCmpnsCpn.getValidEndDtm());
			mbMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_ISSUANCE); // 쿠폰 상태 코드 : 발급
			mbMemberCoupon.setRgsterNo(Const.SYSTEM_ADMIN_NO);
			mbMemberCoupon.setModerNo(Const.SYSTEM_ADMIN_NO);

			// 지류번호 set
			Long paperNumberCount = prCouponPaperNumberDao.selectPrCouponPaperNumberSeq(0);
			String makeRandomNumber = eventService.makeRandomNumber(paperNumberCount, soldOutCmpnsCpn.getCpnNo(),
					"coupon");
			mbMemberCoupon.setPaperNoText(makeRandomNumber);

			int i = 0;
			while (i < claimProductList.size()) {

				String strHoldCpnSeq = mbMemberCouponDao.selectMemberCouponHoldSeq(memberNo);
				int holdCpnSeq = Integer.parseInt(strHoldCpnSeq);
				mbMemberCoupon.setHoldCpnSeq(holdCpnSeq);
				mbMemberCoupon.setRgstDtm(new Timestamp(new Date().getTime()));
				mbMemberCoupon.setCpnIssueDtm(new Timestamp(new Date().getTime()));

				mbMemberCouponDao.insertMemberCoupon(mbMemberCoupon);
				i++;
			}
		}

		return true;
	}

	/**
	 * @Desc : 클레임 결제 이력데이터 등록(주문금 기준)
	 * @Method Name : setClaimPaymentHistory
	 * @Date : 2019. 6. 17.
	 * @Author : KTH
	 * @param ocClaim
	 * @param claimPayment
	 * @throws Exception
	 */
	public void setClaimPaymentHistory(OcClaim ocClaim, OcClaimPayment claimPayment, String redempRfndGbnType)
			throws Exception {

		claimPayment.setClmNo(ocClaim.getClmNo()); // 클레임번호
		claimPayment.setRedempRfndGbnType(redempRfndGbnType); // 환수환불구분
		claimPayment.setPymntDtm(null); // 결제일시
		claimPayment.setAcntCrtfcYn(Const.BOOLEAN_FALSE); // 계좌인증여부
		claimPayment.setRedempRfndMemoText(null); // 환수환불메모
		claimPayment.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 자사처리대상여부
		claimPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_ORDER_AMT); // 발생사유코드 - 주문금
		claimPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_HISTORY); // 클레임이력구분 - 이력근거용
		claimPayment.setRgsterNo(ocClaim.getClaimRgsterNo()); // 등록자
		claimPayment.setModerNo(ocClaim.getClaimRgsterNo()); // 수정자

		ocClaimPaymentDao.insertClaimPayment(claimPayment);
	}

	/**
	 * @Desc : 클레임 배송비 취소
	 * @Method Name : setCancelClaimDeliveryAmt
	 * @Date : 2019. 7. 29.
	 * @Author : KTH
	 * @param ocClaim
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setCancelClaimDeliveryAmt(OcClaim ocClaim) throws Exception {
		
		log.error("+++++++++++ 클레임 배송비 취소 시작  CLM_NO [" + ocClaim.getClmNo() +"]");
		
		Map<String, Object> retMap = new HashMap<String, Object>();

		PaymentResult paymentResult = null; // 결제취소 결과

		/*
		 * 클레임결제 정보 목록
		 */
		OcClaimPayment ocClaimPayment = new OcClaimPayment();
		ocClaimPayment.setClmNo(ocClaim.getClmNo());
		List<OcClaimPayment> claimPaymentList = ocClaimPaymentDao.selectClaimPaymentList(ocClaimPayment);

		/*
		 * 결제된 배송비 취소 : 무료는 처리하지 않음
		 */
		// 선결제
		if (UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(), CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT)) {
			OcClaimPayment claimDeliveryPayment = claimPaymentList.stream()
					.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT))
					.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_PUBLIC)).findFirst()
					.orElse(null);

			// 2020.03.17 : 결제완료 상태인 클레임 배송비가 있는 경우 (기존 : 클레임 배송비가 있는 경우)
			if (claimDeliveryPayment != null && UtilsText.equals(claimDeliveryPayment.getPymntStatCode(),
					CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH)) {
				claimDeliveryPayment.setCustIp(ocClaim.getCustIp());

				// 신용카드, 실시간계좌이체 결제
				if (UtilsText.equals(claimDeliveryPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_CARD)
						|| UtilsText.equals(claimDeliveryPayment.getPymntMeansCode(),
								CommonCode.PYMNT_MEANS_CODE_ACCOUNT_TRANSFER)) {
					paymentResult = this.setCancelKcpPayment(ocClaim, claimDeliveryPayment, true, false);
				}
				// 가상계좌
				else if (UtilsText.equals(claimDeliveryPayment.getPymntMeansCode(),
						CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT)) {
					// 입금대기
					if (UtilsText.equals(claimDeliveryPayment.getPymntStatCode(),
							CommonCode.PYMNT_STAT_CODE_WAIT_DEPOSIT)) {
						// 환불이 아닌 가상계좌 발번 취소
						paymentResult = this.setCancelKcpPayment(ocClaim, claimDeliveryPayment, true, false);
					}
					// 결제완료
					else if (UtilsText.equals(claimDeliveryPayment.getPymntStatCode(),
							CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH)) {

						if (UtilsText.isNotEmpty(ocClaim.getRfndAcntText())) {
							// 환불
							paymentResult = this.setVcntRefundKcpPayment(ocClaim, claimDeliveryPayment, true, false);
						} else {
							retMap.put("success", Const.BOOLEAN_FALSE);
							retMap.put("message", "환불계좌가 필요합니다.");
							return retMap;
						}
					}
				}

				try {
					/*
					 * 결제취소 상태 업데이트
					 */
					ObjectMapper mapper = new ObjectMapper();
					OcClaimPayment cancelResult = new OcClaimPayment();
					if (paymentResult == null) {
						cancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 환불접수
						cancelResult.setRspnsCodeText(null);
						cancelResult.setRspnsMesgText(null);
						cancelResult.setPymntLogInfo(null);
					} else {

						// 2020.03.10 : KCP 응답코드에 따른 결제취소 성공태우는 로직
						if (UtilsText.equals(paymentResult.getSuccessYn(), Const.BOOLEAN_TRUE)) {
							// 결제취소 성공 일때 O
							cancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL); // 주문취소(결제취소)
						} else {
							// 결제취소 실패 일때 X
							// 2020.03.10 : KCP 응답코드에 따른 결제취소 성공태우는 로직
							// KCP 결제취소 결과
							// 2020.02.13 : [8133] KCP에 이미 취소요청하여 취소처리된 거래건에 다시 취소요청 시 발생.
							// 2020.02.14 : [8150] KCP에 이미 취소요청하여 취소처리된 거래건에 다시 취소요청 시 발생.
							// 2020.03.19 : [8729] KCP 계좌이체 기 취소된 거래
							if (UtilsText.equals(paymentResult.getCode(), Const.KCP_ALRDY_CANCEL_CODE_CARD)
									|| UtilsText.equals(paymentResult.getCode(), Const.KCP_ALRDY_CANCEL_CODE_PART)
									|| UtilsText.equals(paymentResult.getCode(), Const.KCP_ALRDY_CANCEL_CODE_ACCOUNT)) {

								log.error("클레임 배송비 기취소로 KCP결제취소 이미 승인된 배송비결제 claimNo : {}", ocClaim.getClmNo());
								cancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL); // 주문취소(결제취소)
							} else {
								// [8133]나 [8150]이 아닌 실패 코드는 재경팀 환불접수 등록
								cancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 환불접수
							}
						}
						cancelResult.setRspnsCodeText(paymentResult.getCode());
						cancelResult.setRspnsMesgText(paymentResult.getMessage());
						cancelResult.setPymntLogInfo(mapper.writeValueAsString(paymentResult.getData()));
					}

					cancelResult.setModerNo(ocClaim.getClaimRgsterNo());
					cancelResult.setClmNo(ocClaim.getClmNo());
					cancelResult.setClmPymntSeq(claimDeliveryPayment.getClmPymntSeq());

					ocClaimPaymentDao.updateClaimPaymentForCancel(cancelResult); // 결제취소 상태 업데이트

					/*
					 * 결제취소 에러 시 재경팀 환불접수
					 */
					if (paymentResult == null || UtilsText.equals(paymentResult.getSuccessYn(), Const.BOOLEAN_FALSE)) {
						// 2020.03.10 : KCP 응답코드에 따른 재경팀 환불접수 등록 여부 판단
						if (paymentResult != null) {
							// KCP 결제취소 결과
							// 2020.02.13 : [8133] KCP에 이미 취소요청하여 취소처리된 거래건에 다시 취소요청 시 발생.
							// 2020.02.14 : [8150] KCP에 이미 취소요청하여 취소처리된 거래건에 다시 취소요청 시 발생.
							// 2020.03.19 : [8729] KCP 계좌이체 기 취소된 거래
							if (UtilsText.equals(paymentResult.getCode(), Const.KCP_ALRDY_CANCEL_CODE_CARD)
									|| UtilsText.equals(paymentResult.getCode(), Const.KCP_ALRDY_CANCEL_CODE_PART)
									|| UtilsText.equals(paymentResult.getCode(), Const.KCP_ALRDY_CANCEL_CODE_ACCOUNT)) {

								log.error("클레임 배송비 기취소로 이미 취소 승인 되었기에 재경팀환불접수하지 않는다. claimNo : {}", ocClaim.getClmNo());
								retMap.put("success", Const.BOOLEAN_TRUE);
								retMap.put("message", "정상 처리되었습니다.");

								return retMap;
							}
						}

						OcClaimPayment refundClaimPayment = new OcClaimPayment();
						BeanUtils.copyProperties(claimDeliveryPayment, refundClaimPayment);

						refundClaimPayment.setClmNo(ocClaim.getClmNo()); // 클레임번호
						refundClaimPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REFUND); // 환수환불구분
						refundClaimPayment.setBankCode(ocClaim.getBankCode()); // 은행코드 : 환불:회원환불계좌
						refundClaimPayment.setAcntNoText(ocClaim.getRfndAcntText()); // 계좌번호
						refundClaimPayment.setAcntHldrName(ocClaim.getAcntHldrName()); // 예금주명
						refundClaimPayment.setMmnyProcTrgtYn(Const.BOOLEAN_TRUE); // 자사처리대상여부 : 재경처리 Y
						refundClaimPayment.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 결제상태코드 : 환불접수
						refundClaimPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_PUBLIC); // 이력구분 : 일반결제처리용
						refundClaimPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT); // 발생사유코드:클레임배송비
						refundClaimPayment.setRedempRfndMemoText("클레임배송비 결제취소로 실패로 인한 환불접수");
						refundClaimPayment.setRgsterNo(ocClaim.getClaimRgsterNo());
						refundClaimPayment.setModerNo(ocClaim.getClaimRgsterNo());

						ocClaimPaymentDao.insertClaimPayment(refundClaimPayment);

						retMap.put("success", Const.BOOLEAN_FALSE);
						retMap.put("message", "결제취소 에러가 발생하여 환불접수 처리가 되었습니다.");
					} else {
						retMap.put("success", Const.BOOLEAN_TRUE);
						retMap.put("message", "정상 처리되었습니다.");
					}
				} catch (Exception e) {
					retMap.put("success", Const.BOOLEAN_FALSE);
					retMap.put("message", "결제취소 에러가 발생하였습니다.");
					log.error("클레임 배송비 취소 에러 - claimDeliveryPayment : {}", claimDeliveryPayment);
					e.printStackTrace();
				}
			} else {
				// 2020.03.17 추가
				retMap.put("success", Const.BOOLEAN_TRUE);
				retMap.put("message", "정상 처리되었습니다.");
			}
		}
		// 무료쿠폰
		else if (UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(), CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON)) {
			MbMemberCoupon reIssueMemberCoupon = new MbMemberCoupon();
			reIssueMemberCoupon.setMemberNo(ocClaim.getMemberNo());
			reIssueMemberCoupon.setHoldCpnSeq(ocClaim.getHoldCpnSeq());
			reIssueMemberCoupon.setReIssueRsnText("클레임철회 재 발급");
			reIssueMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_REISSUANCE);
			reIssueMemberCoupon.setRgsterNo(ocClaim.getClaimRgsterNo());
			reIssueMemberCoupon.setModerNo(ocClaim.getClaimRgsterNo());

			mbMemberCouponDao.insertMemberCouponReIssue(reIssueMemberCoupon); // 쿠폰 재 발급

			OcClaim claim = new OcClaim();
			claim.setClmNo(ocClaim.getClmNo());
			claim.setAddDlvyAmtPymntType(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE); // 무료변경
			claim.setAddDlvyAmt(0); // 클레임배송비 0
			claim.setHoldCpnSeq(null); // 쿠폰번호제거
			claim.setModerNo(ocClaim.getClaimRgsterNo());

			ocClaimDao.updateClaimAddDlvy(claim); // 클레임배송비 항목 업데이트

			retMap.put("success", Const.BOOLEAN_TRUE);
			retMap.put("message", "정상 처리되었습니다.");
		}
		// 환불금차감
		else if (UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(),
				CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_REFUND_DEDUCTION)) {
			OcClaimPayment claimDeliveryPayment = claimPaymentList.stream()
					.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT))
					.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY)).findFirst()
					.orElse(null);

			ocClaimPaymentDao.delete(claimDeliveryPayment); // 결제 히스토리로 있는 환불금차감 내용을 삭제

			OcClaim claim = new OcClaim();
			claim.setClmNo(ocClaim.getClmNo());
			claim.setAddDlvyAmtPymntType(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE); // 무료변경
			claim.setAddDlvyAmt(0); // 클레임배송비 0
			claim.setHoldCpnSeq(null); // 쿠폰번호제거
			claim.setModerNo(ocClaim.getClaimRgsterNo());

			ocClaimDao.updateClaimAddDlvy(claim); // 클레임배송비 항목 업데이트

			retMap.put("success", Const.BOOLEAN_TRUE);
			retMap.put("message", "정상 처리되었습니다.");
		}
		// 배송비무료 / 2020.03.10 : 철회시 undefined alert이 뜨던 원인
		else if (UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(), CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE)) {
			retMap.put("success", Const.BOOLEAN_TRUE);
			retMap.put("message", "정상 처리되었습니다.");
		}

		return retMap;
	}

	/*********************************************************************************************************
	 *********************************************************************************************************
	 * 이하 Exception throw 하지 않는 메서드
	 *********************************************************************************************************
	 *********************************************************************************************************/

	/**
	 * @Desc : 클레임 결제 결제취소 시 결제처리 데이터 등록
	 * @Method Name : setClaimPayment
	 * @Date : 2019. 7. 21.
	 * @Author : KTH
	 * @param ocClaim
	 * @param claimPayment
	 * @param redempRfndGbnType
	 */
	public void setClaimPayment(OcClaim ocClaim, OcClaimPayment claimPayment, String redempRfndGbnType) {

		try {
			claimPayment.setClmNo(ocClaim.getClmNo()); // 클레임번호
			claimPayment.setRedempRfndGbnType(redempRfndGbnType); // 환수환불구분
			claimPayment.setPymntDtm(new Timestamp(new Date().getTime())); // 결제일시
			claimPayment.setAcntCrtfcYn(Const.BOOLEAN_FALSE); // 계좌인증여부
			claimPayment.setRedempRfndMemoText(null); // 환수환불메모
			claimPayment.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 자사처리대상여부
			claimPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_ORDER_AMT); // 발생사유코드 - 주문금
			claimPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_PUBLIC); // 클레임이력구분 - 일반결제처리용
			claimPayment.setRgsterNo(ocClaim.getClaimRgsterNo()); // 등록자
			claimPayment.setModerNo(ocClaim.getClaimRgsterNo()); // 수정자

			// 2020.03.16 로그 추가
			log.error("결제 처리 데이터 등록 : " + claimPayment.toString());

			ocClaimPaymentDao.insertClaimPayment(claimPayment);
		} catch (Exception e) {
			log.error("결제처리 데이터 등록 에러 - claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
			log.error("결제처리 데이터 등록 에러 - Exception e : " + e);

			// 2020.03.16 로그 추가
			e.printStackTrace();
		}
	}

	/**
	 * @Desc : 클레임 취소 처리 후 처리대상 주문, 클레임 상태 값 변경 등 후처리
	 * @Method Name : setClaimCancelAfterProc
	 * @Date : 2019. 6. 20.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocClaimAmountVO
	 * @param reOrderNo
	 */
	public void setClaimCancelAfterProc(OcClaim ocClaim, OcClaimAmountVO ocClaimAmountVO) {
		String orderStatCode = CommonCode.ORDER_STAT_CODE_CANCEL_COMPLETE; // 주문 상태코드 : 전체취소완료
		String orderPrdtStatCode = CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE; // 주문상품 상태코드 : 취소완료
		String clmStatCode = CommonCode.CLM_STAT_CODE_CANCEL_FINISH; // 클레임 상태 코드(취소) ; 취소완료
		String clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_CANCEL_FINISH; // 클레임상품 상태 코드(취소) ; 취소완료

		if (ocClaimAmountVO.isRefundOccurrence()) { // 환불 발생 상황
			orderStatCode = CommonCode.ORDER_STAT_CODE_REFUND_ACCEPT; // 주문 상태코드 : 환수/환불접수
			orderPrdtStatCode = CommonCode.ORDER_PRDT_STAT_CODE_REFUND_ACCEPT; // 주문상품 상태코드 : 환수/환불접수
			clmStatCode = CommonCode.CLM_STAT_CODE_CANCEL_REFUND_ACCEPT; // 클레임 상태 코드(취소) : 환불접수
			clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_CANCEL_REFUND_ACCEPT; // 클레임상품 상태 코드(취소) ; 환불접수
		}

		/*
		 * 재경팀 처리용 환수/환불 대상 등록
		 */
		log.error("클레임 취소 처리 후 처리대상 주문, 클레임 상태 값 변경 등 후처리 -> 재경팀 환불로직 시작");
		this.setRedempRefundMmnyProc(ocClaim);

		/*
		 * 원주문 상품상태 업데이트
		 */
		for (OcClaimProduct claimProduct : ocClaimAmountVO.getThisTimeClaimProductList()) {
			OcOrderProduct updateOrderProduct = new OcOrderProduct();

			try {
				updateOrderProduct.setOrderNo(ocClaim.getOrgOrderNo());
				updateOrderProduct.setOrderPrdtSeq(claimProduct.getOrderPrdtSeq());
				updateOrderProduct.setOrderPrdtStatCode(orderPrdtStatCode);
				updateOrderProduct.setModerNo(ocClaim.getClaimRgsterNo());

				ocOrderProductDao.updateOrderProductForCalim(updateOrderProduct);
			} catch (Exception e) {
				log.error("주문상품상태 업데이트 에러 - updateOrderProduct={}", updateOrderProduct);
				e.printStackTrace();
			}
		}

		/*
		 * 원주문 상태 업데이트
		 */
		OcOrder updateOrder = new OcOrder();

		try {
			updateOrder.setOrderNo(ocClaim.getOrgOrderNo());

			// 전체취소 또는 이전 부분취소 이후 남은 상품 모두 취소인 경우 상태 값 set
			if (ocClaimAmountVO.isAllCancelYn() || ocClaimAmountVO.isRemainAllCancelYn()) {
				updateOrder.setOrderStatCode(orderStatCode);
			}

			// 전체취소가 아닌 경우 환불(취소)금액 set
			if (!ocClaimAmountVO.isAllCancelYn()) {
				updateOrder.setCnclAmt(ocClaimAmountVO.getRefundCnclAmt());
			}

			updateOrder.setModerNo(ocClaim.getClaimRgsterNo());

			ocOrderDao.updateOrderForClaim(updateOrder);
		} catch (Exception e) {
			log.error("원주문 상태 업데이트 에러 - updateOrder={}", updateOrder);
			e.printStackTrace();
		}

		/*
		 * 리오더 생성된 경우 리오더 주문/주문상품 상태 업데이트
		 */
		if (ocClaimAmountVO.isReOrderRegistPossible()) {
			/*
			 * 리오더주문 상품상태 업데이트
			 */
			OcOrderProduct updateReOrderProduct = new OcOrderProduct();

			try {
				updateReOrderProduct.setOrderNo(ocClaim.getReOrderNo());
				updateReOrderProduct.setOrderPrdtStatCode(orderPrdtStatCode);
				updateReOrderProduct.setExcclcDcsnYmd(new Timestamp(new Date().getTime())); // 정산확정일자
				updateReOrderProduct.setModerNo(ocClaim.getClaimRgsterNo());

				ocOrderProductDao.updateOrderProductForCalim(updateReOrderProduct);
			} catch (Exception e) {
				log.error("리오더주문 상품상태 업데이트 에러 - updateReOrderProduct={}", updateReOrderProduct);
				e.printStackTrace();
			}

			/*
			 * 리오더주문 상태 업데이트
			 */
			OcOrder updateReOrder = new OcOrder();

			try {
				updateReOrder.setOrderNo(ocClaim.getReOrderNo());
				updateReOrder.setOrderStatCode(orderStatCode);
				updateReOrder.setModerNo(ocClaim.getClaimRgsterNo());

				ocOrderDao.updateOrderForClaim(updateReOrder);
			} catch (Exception e) {
				log.error("리오더주문 상태 업데이트 에러 - updateReOrder={}", updateReOrder);
				e.printStackTrace();
			}
		}

		/*
		 * 원주문 이력 생성(취소완료 또는 환수/환불접수)
		 */
		for (OcClaimProduct claimProduct : ocClaimAmountVO.getThisTimeClaimProductList()) {
			// 원 주문의 주문상품이력 등록
			OcOrderProductHistory orgOrderProductHistory = new OcOrderProductHistory();

			try {
				orgOrderProductHistory.setOrderNo(claimProduct.getOrderNo());
				orgOrderProductHistory.setOrderPrdtSeq(claimProduct.getOrderPrdtSeq());
				orgOrderProductHistory.setPrdtNo(claimProduct.getPrdtNo());
				orgOrderProductHistory.setPrdtOptnNo(claimProduct.getPrdtOptnNo());
				orgOrderProductHistory.setPrdtName(claimProduct.getPrdtName());
				orgOrderProductHistory.setEngPrdtName(claimProduct.getEngPrdtName());
				orgOrderProductHistory.setOptnName(claimProduct.getOptnName());
				orgOrderProductHistory.setOrderPrdtStatCode(orderPrdtStatCode); // 주문상품상태코드
				orgOrderProductHistory.setNoteText(null);
				orgOrderProductHistory.setRgsterNo(ocClaim.getClaimRgsterNo());

				ocOrderProductHistoryDao.insertProductHistory(orgOrderProductHistory); // 주문상품이력 생성
			} catch (Exception e) {
				log.error("주문상품이력 등록 에러 - claimProduct={}", claimProduct);
				e.printStackTrace();
			}
		}

		/*
		 * 클레임 마스터 상태 업데이트
		 */
		OcClaim updateClaim = new OcClaim();
		updateClaim.setClmNo(ocClaim.getClmNo());
		updateClaim.setClmStatCode(clmStatCode);
		updateClaim.setModerNo(ocClaim.getClaimRgsterNo());

		// 전체취소가 아닌 경우
		if (!ocClaimAmountVO.isAllCancelYn()) {
			updateClaim.setTotalRfndAmt(ocClaimAmountVO.getRefundCnclAmt());
			updateClaim.setTotalRedempAmt(ocClaimAmountVO.getTotalRedempAmt());
		}

		try {
			ocClaimDao.updateClaimStat(updateClaim);
		} catch (Exception e) {
			log.error("클레임 마스터 상태 업데이트 에러 - updateClaim={}", updateClaim);
			e.printStackTrace();
		}

		/*
		 * 클레임상품 상태 업데이트
		 */
		OcClaimProduct updateClaimProduct = new OcClaimProduct();

		try {
			updateClaimProduct.setClmNo(ocClaim.getClmNo());
			updateClaimProduct.setClmPrdtStatCode(clmPrdtStatCode);
			updateClaimProduct.setModerNo(ocClaim.getClaimRgsterNo());

			ocClaimProductDao.updateClaimProductStatCode(updateClaimProduct);
		} catch (Exception e) {
			log.error("클레임상품 상태 업데이트 에러 - updateClaimProduct={}", updateClaimProduct);
			e.printStackTrace();
		}

		/*
		 * 클레임상태이력 등록
		 */
		for (OcClaimProduct ocClaimProduct : ocClaimAmountVO.getThisTimeClaimProductList()) {
			OcClaimStatusHistory ocClaimStatusHistory = new OcClaimStatusHistory();

			// 클레임상태이력 등록
			try {
				ocClaimStatusHistory.setClmNo(ocClaim.getClmNo());
				ocClaimStatusHistory.setClmPrdtSeq(ocClaimProduct.getClmPrdtSeq());
				ocClaimStatusHistory.setClmPrdtStatCode(clmPrdtStatCode); // 클레임상품상태코드
				ocClaimStatusHistory.setStockGbnCode(null);
				ocClaimStatusHistory.setNoteText(null);
				ocClaimStatusHistory.setRgsterNo(ocClaim.getRgsterNo()); // 등록자

				ocClaimStatusHistoryDao.insertClaimStatusHistory(ocClaimStatusHistory);
			} catch (Exception e) {
				log.error("클레임상태이력 등록 에러 - ocClaimStatusHistory={}", ocClaimStatusHistory);
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Desc : 클레임 반품 처리 후 처리대상 주문, 클레임 상태 값 변경 등 후처리
	 * @Method Name : setClaimCancelAfterProc
	 * @Date : 2019. 6. 20.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocClaimAmountVO
	 * @param reOrderNo
	 */
	public void setClaimReturnAfterProc(OcClaim ocClaim, OcClaimAmountVO ocClaimAmountVO) {
		String orderStatCode = CommonCode.ORDER_STAT_CODE_CANCEL_COMPLETE; // 주문 상태코드 : 전체취소완료
		String orderPrdtStatCode = CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE; // 주문상품 상태코드 : 취소완료
		String clmStatCode = CommonCode.CLM_STAT_CODE_RETURN_FINISH; // 클레임 상태 코드(반품) ; 반품완료
		String clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_RETURN_FINISH; // 클레임상품 상태 코드(반품) ; 반품완료

		if (ocClaimAmountVO.isRefundOccurrence()) { // 환불 발생 상황
			clmStatCode = CommonCode.CLM_STAT_CODE_RETURN_REFUND_ACCEPT; // 클레임 상태 코드(취소) : 환불접수
			clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_RETURN_REFUND_ACCEPT; // 클레임상품 상태 코드(취소) ; 환불접수
		}

		/*
		 * 재경팀 처리용 환수/환불 대상 등록
		 */
		log.error("클레임 반품 처리 후 처리대상 주문, 클레임 상태 값 변경 등 후처리 -> 재경팀 환불로직 시작");
		this.setRedempRefundMmnyProc(ocClaim);

		/*
		 * 원주문 취소금액 업데이트
		 */
		OcOrder updateOrder = new OcOrder();

		try {
			updateOrder.setOrderNo(ocClaim.getOrgOrderNo());

			// 환불(취소)금액 set
			if (!ocClaimAmountVO.isAllCancelYn()) {
				updateOrder.setCnclAmt(ocClaimAmountVO.getRefundCnclAmt());
			}

			updateOrder.setModerNo(ocClaim.getClaimRgsterNo());

			ocOrderDao.updateOrderForClaim(updateOrder);
		} catch (Exception e) {
			log.error("원주문 상태 업데이트 에러 - updateOrder={}", updateOrder);
			e.printStackTrace();
		}

		/*
		 * 리오더주문 상품상태 업데이트
		 */
		OcOrderProduct updateReOrderProduct = new OcOrderProduct();

		try {
			updateReOrderProduct.setOrderNo(ocClaim.getReOrderNo());
			updateReOrderProduct.setOrderPrdtStatCode(orderPrdtStatCode);
			updateReOrderProduct.setExcclcDcsnYmd(new Timestamp(new Date().getTime())); // 정산확정일자
			updateReOrderProduct.setModerNo(ocClaim.getClaimRgsterNo());

			ocOrderProductDao.updateOrderProductForCalim(updateReOrderProduct);
		} catch (Exception e) {
			log.error("리오더주문 상품상태 업데이트 에러 - updateReOrderProduct={}", updateReOrderProduct);
			e.printStackTrace();
		}

		/*
		 * 리오더주문 상태 업데이트
		 */
		OcOrder updateReOrder = new OcOrder();

		try {
			updateReOrder.setOrderNo(ocClaim.getReOrderNo());
			updateReOrder.setOrderStatCode(orderStatCode);
			updateReOrder.setModerNo(ocClaim.getClaimRgsterNo());

			ocOrderDao.updateOrderForClaim(updateReOrder);
		} catch (Exception e) {
			log.error("리오더주문 상태 업데이트 에러 - updateReOrder={}", updateReOrder);
			e.printStackTrace();
		}

		/*
		 * 클레임 마스터 상태 업데이트
		 */
		OcClaim updateClaim = new OcClaim();
		updateClaim.setClmNo(ocClaim.getClmNo());
		updateClaim.setClmStatCode(clmStatCode);
		updateClaim.setModerNo(ocClaim.getClaimRgsterNo());

		// 전체취소가 아닌 경우
		updateClaim.setTotalRfndAmt(ocClaimAmountVO.getRefundCnclAmt());
		updateClaim.setTotalRedempAmt(ocClaimAmountVO.getTotalRedempAmt());

		try {
			ocClaimDao.updateClaimStat(updateClaim);
		} catch (Exception e) {
			log.error("클레임 마스터 상태 업데이트 에러 - updateClaim={}", updateClaim);
			e.printStackTrace();
		}

		/*
		 * 클레임상품 상태 업데이트
		 */
		for (OcClaimProduct ocClaimProduct : ocClaimAmountVO.getThisTimeClaimProductList()) {
			OcClaimProduct updateClaimProduct = new OcClaimProduct();

			try {
				updateClaimProduct.setClmNo(ocClaim.getClmNo());
				updateClaimProduct.setClmPrdtSeq(ocClaimProduct.getClmPrdtSeq());
				updateClaimProduct.setClmPrdtStatCode(clmPrdtStatCode);
				updateClaimProduct.setModerNo(ocClaim.getClaimRgsterNo());

				ocClaimProductDao.updateClaimProductStatCode(updateClaimProduct);
			} catch (Exception e) {
				log.error("클레임상품 상태 업데이트 에러 - updateClaimProduct={}", updateClaimProduct);
				e.printStackTrace();
			}
		}

		/*
		 * 클레임상태이력 등록
		 */
		for (OcClaimProduct ocClaimProduct : ocClaimAmountVO.getThisTimeClaimProductList()) {
			OcClaimStatusHistory ocClaimStatusHistory = new OcClaimStatusHistory();

			// 클레임상태이력 등록
			try {
				ocClaimStatusHistory.setClmNo(ocClaim.getClmNo());
				ocClaimStatusHistory.setClmPrdtSeq(ocClaimProduct.getClmPrdtSeq());
				ocClaimStatusHistory.setClmPrdtStatCode(clmPrdtStatCode); // 클레임상품상태코드
				ocClaimStatusHistory.setStockGbnCode(null);
				ocClaimStatusHistory.setNoteText(null);
				ocClaimStatusHistory.setRgsterNo(ocClaim.getClaimRgsterNo()); // 등록자

				ocClaimStatusHistoryDao.insertClaimStatusHistory(ocClaimStatusHistory);
			} catch (Exception e) {
				log.error("클레임상태이력 등록 에러 - ocClaimStatusHistory={}", ocClaimStatusHistory);
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Desc : KCP 일반결제 취소/부분취소
	 * @Method Name : setCancelKcpPayment
	 * @Date : 2019. 5. 24.
	 * @Author : KTH
	 * @param claimPayment
	 * @param dpstChngRsnCode
	 * @param clmSeq
	 * @param isAllCancel
	 * @return
	 */
	public PaymentResult setCancelKcpPayment(OcClaim ocClaim, OcClaimPayment claimPayment, boolean isAllCancel,
			boolean isOrderCancel) {

		PaymentResult paymentResult;
		long leftAmt = 0; // 부분취소 시 취소가능 잔여금
		String modType = ""; // 전체/부분취소구분
		String modDesc = ""; // 취소사유
		KcpPaymentCancel kcpPaymentCancel = new KcpPaymentCancel();

		if (isAllCancel) {
			modType = CommonCode.KCP_MOD_TYPE_STSC;
			modDesc = "전체취소";
		} else {
			modType = CommonCode.KCP_MOD_TYPE_STPC;
			modDesc = "부분취소";
		}

		if (isOrderCancel) { // 주문취소
			if (UtilsText.equals(ocClaim.getSiteNo(), Const.SITE_NO_ART)) {
				kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.siteCode"));
				kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.siteKey"));
			} else {
				kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.ots.siteCode"));
				kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.ots.siteKey"));
			}
		} else { // 클레임
			if (UtilsText.equals(ocClaim.getSiteNo(), Const.SITE_NO_ART)) {
				kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.claim.siteCode"));
				kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
			} else {
				kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.ots.claim.siteCode"));
				kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
			}
		}

		kcpPaymentCancel.setReqTx(""); // 요청종류 : 일반취소는 빈 값 처리
		kcpPaymentCancel.setModType(modType); // 전체취소 STSC / 부분취소 STPC
		kcpPaymentCancel.setCustIp("127.0.0.1"); // 요청 IP
		kcpPaymentCancel.setTno(claimPayment.getPymntAprvNoText()); // 거래번호
		kcpPaymentCancel.setModDesc(modDesc); // 취소사유

		if (!isAllCancel) { // 부분취소
			log.error("############################## setModMny : {}", claimPayment.getPymntAmt());
			log.error("############################## setRemMny : {}", claimPayment.getRealRemainPymntCnclAmt());
			kcpPaymentCancel.setModMny(String.valueOf(claimPayment.getPymntAmt())); // 취소요청금액
			kcpPaymentCancel.setRemMny(String.valueOf(claimPayment.getRealRemainPymntCnclAmt())); // 부분취소이전에남은금액
		}

		try {
			log.error("KCP 일반결제 취소/부분취소 - claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
			log.error(">>>>>>>>>>> to KCP : " + kcpPaymentCancel.toString());
			paymentResult = paymentEntrance.cancel(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, kcpPaymentCancel));
			log.error("<<<<<<<<<<< KCP return : " + paymentResult.toString());
		} catch (PaymentException e) {
			paymentResult = new PaymentResult(Const.BOOLEAN_FALSE);
			log.error("KCP 일반결제 취소/부분취소 에러 - claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
			e.printStackTrace();
		}

		return paymentResult;
	}

	/**
	 * <pre>
	 * KCP 에스크로 취소/부분취소
	 * modType
	 * - STE1 : 배송시작
	 * - STE2 : 즉시취소(배송 전 취소)
	 * - STE3 : 정산보류
	 * - STE4 : 취소(배송 후 취소)
	 * - STE5 : 발급계좌해지(가상계좌의 경우에만 사용)
	 * - STE9_C : 신용카드 구매 확인 후 취소
	 * - STE9_CP : 신용카드 구매 확인 후 부분취소
	 * - STE9_A : 계좌이체 구매 확인 후 취소
	 * - STE9_AP : 계좌이체 구매 확인 후 부분취소
	 * - STE9_AR : 계좌이체 구매 확인 후 환불
	 * - STE9_V : 가상계좌 구매 확인 후 환불
	 * - STE9_VP : 가상계좌 구매 확인 후 부분환불
	 * </pre>
	 * 
	 * @Desc : KCP 에스크로 취소/부분취소
	 * @Method Name : commonCancelKcpEscrowPayment
	 * @Date : 2019. 5. 22.
	 * @Author : KTH
	 * @param payment
	 * @param dpstChngRsnCode
	 * @param clmSeq
	 * @param modType
	 * @param criteria
	 * @param gubun
	 * @throws PaymentException
	 */
	public PaymentResult setCancelKcpEscrowPayment(OcClaim ocClaim, OcClaimPayment claimPayment, String modType,
			boolean isOrderCancel) {

		PaymentResult paymentResult;
		long leftAmt = 0;
		KcpPaymentCancel kcpPaymentCancel = new KcpPaymentCancel();

		if (isOrderCancel) { // 주문취소
			if (UtilsText.equals(ocClaim.getSiteNo(), Const.SITE_NO_ART)) {
				kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.siteCode"));
				kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.siteKey"));
			} else {
				kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.ots.siteCode"));
				kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.ots.siteKey"));
			}
		} else { // 클레임
			if (UtilsText.equals(ocClaim.getSiteNo(), Const.SITE_NO_ART)) {
				kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.claim.siteCode"));
				kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
			} else {
				kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.ots.claim.siteCode"));
				kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
			}
		}

		kcpPaymentCancel.setReqTx(CommonCode.KCP_REQ_TX_MOD_ESCROW); // 요청종류
		kcpPaymentCancel.setCustIp("127.0.0.1"); // 요청 IP
		kcpPaymentCancel.setTno(claimPayment.getPymntAprvNoText()); // 거래번호
		kcpPaymentCancel.setModType(modType); // 환불타입
		kcpPaymentCancel.setModDesc("취소"); // 취소사유
		kcpPaymentCancel.setModDepositor(ocClaim.getAcntHldrName()); // 환불계좌주명(환불시에만 사용)
		kcpPaymentCancel.setModAccount(ocClaim.getRfndAcntText()); // 환불계좌번호(환불시에만 사용)
		kcpPaymentCancel.setModBankCode(getKcpBankCode(ocClaim.getBankCode())); // 환불은행코드(환불시에만 사용)
		// 가상계좌여부???
		kcpPaymentCancel.setRemMny(String.valueOf(leftAmt)); // 환불 가능 금액(잔여금액)
		kcpPaymentCancel.setModMny(String.valueOf(claimPayment.getPymntAmt())); // 환불 금액

		try {
			log.error(">>>>>>>>>> KCP 에스크로 : " + kcpPaymentCancel.toString());
			paymentResult = paymentEntrance.cancel(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, kcpPaymentCancel));
			log.error("<<<<<<<<<< KCP 에스크로 : " + paymentResult.toString());
		} catch (PaymentException e) {
			paymentResult = new PaymentResult(Const.BOOLEAN_FALSE);
			log.error("KCP 에스크로 취소/부분취소 에러 - claimPayment : {}", claimPayment);
			e.printStackTrace();
		}

		return paymentResult;
	}

	/**
	 * @Desc : KCP 가상계좌 환불
	 * @Method Name : setVcntRefundKcpPayment
	 * @Date : 2019. 5. 24.
	 * @Author : KTH
	 * @param claimPayment
	 * @param dpstChngRsnCode
	 * @param clmSeq
	 * @param ocClaim
	 * @param isAllCancel
	 * @return
	 * @throws PaymentException
	 */
	public PaymentResult setVcntRefundKcpPayment(OcClaim ocClaim, OcClaimPayment claimPayment, boolean isAllCancel,
			boolean isOrderCancel) {

		PaymentResult paymentResult;
		long leftAmt = 0; // 부분환불 시 취소가능 잔여금
		String modType = ""; // 전체/부분환불구분
		String modSubType = ""; // 환불요청타입 설정(전체환불-MDSC00,부분환불-MDSC03,복합과세부분환불-MDSC04)
		String modDesc = ""; // 환불요청에 대한 사유
		KcpPaymentRefund kcpPaymentRefund = new KcpPaymentRefund();

		if (isAllCancel) {
			modType = CommonCode.KCP_MOD_TYPE_STHD;
			modSubType = CommonCode.KCP_MOD_SUB_TYPE_MDSC00;
			modDesc = "취소환불";
		} else {
			modType = CommonCode.KCP_MOD_TYPE_STPD;
			modSubType = CommonCode.KCP_MOD_SUB_TYPE_MDSC03;
			modDesc = "부분환불";
		}

		if (isOrderCancel) { // 주문취소
			if (UtilsText.equals(ocClaim.getSiteNo(), Const.SITE_NO_ART)) {
				kcpPaymentRefund.setSiteCd(Config.getString("pg.kcp.siteCode"));
				kcpPaymentRefund.setSiteKey(Config.getString("pg.kcp.siteKey"));
			} else {
				kcpPaymentRefund.setSiteCd(Config.getString("pg.kcp.ots.siteCode"));
				kcpPaymentRefund.setSiteKey(Config.getString("pg.kcp.ots.siteKey"));
			}
		} else { // 클레임
			if (UtilsText.equals(ocClaim.getSiteNo(), Const.SITE_NO_ART)) {
				kcpPaymentRefund.setSiteCd(Config.getString("pg.kcp.claim.siteCode"));
				kcpPaymentRefund.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
			} else {
				kcpPaymentRefund.setSiteCd(Config.getString("pg.kcp.ots.claim.siteCode"));
				kcpPaymentRefund.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
			}
		}

		kcpPaymentRefund.setReqTx(CommonCode.KCP_REQ_TX_MOD); // 요청종류
		kcpPaymentRefund.setModType(modType); // 전체환불
		kcpPaymentRefund.setModCompType(CommonCode.KCP_MOD_COMP_TYPE_MDCP01); // 인증타입(계좌인증+환불등록-MDCP01,(계좌+실명)인증+환불등록-MDCP02)
		kcpPaymentRefund.setModSubType(modSubType); // 환불요청타입설정(전체환불-MDSC00,부분환불-MDSC03,복합과세부분환불-MDSC04)
		kcpPaymentRefund.setTno(claimPayment.getPymntAprvNoText()); // KCP 거래 고유 번호
		kcpPaymentRefund.setModDesc(modDesc); // 환불요청에 대한 사유
		kcpPaymentRefund.setModAccount(ocClaim.getRfndAcntText()); // 계좌인증 및 환불 받을 계좌번호
		kcpPaymentRefund.setModDepositor(ocClaim.getAcntHldrName()); // 예금주
		kcpPaymentRefund.setModBankcode(getKcpBankCode(ocClaim.getBankCode())); // 은행 코드
		kcpPaymentRefund.setCustIp("127.0.0.1"); // 요청 IP

		if (!isAllCancel) { // 부분환불
			kcpPaymentRefund.setModMny(claimPayment.getPymntAmt()); // 취소요청금액
			kcpPaymentRefund.setRemMny(claimPayment.getRealRemainPymntCnclAmt()); // 부분취소이전에남은금액
		}

		try {
			log.error("KCP 가상계좌 환불 - claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
			log.error(">>>>>>> TO KCP VIRTURE : " + kcpPaymentRefund.toString());
			paymentResult = paymentEntrance.refund(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, kcpPaymentRefund));
			log.error("<<<<<<< RETURN FROM KCP : " + paymentResult.toString());
			
		} catch (PaymentException e) {
			paymentResult = new PaymentResult(Const.BOOLEAN_FALSE);
			log.error("KCP 가상계좌 환불 에러 - claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
			e.printStackTrace();
		}

		return paymentResult;
	}

	/**
	 * @Desc : 네이버페이 취소/부분취소
	 * @Method Name : setCancelNaverPayment
	 * @Date : 2019. 5. 24.
	 * @Author : KTH
	 * @param claimPayment
	 * @param ocClaim
	 * @param isAllCancel
	 * @return
	 * @throws PaymentException
	 */
	public PaymentResult setCancelNaverPayment(OcClaimPayment claimPayment, OcClaim ocClaim, boolean isAllCancel) {
		PaymentResult paymentResult = null;
		String cancelReason = "";
		NaverPaymentCancel naverPaymentCancel = new NaverPaymentCancel();
		Map<String, Object> mapData = new HashMap<String, Object>();

		List<OcClaimProduct> ocClaimProduct = ocClaim.getOcClaimProductList();
		String clmEtcRsnText = "";

		if (ocClaimProduct != null) {
			if (ocClaimProduct.size() > 0) {
				if (ocClaimProduct.get(0).getClmEtcRsnText() != null) {
					clmEtcRsnText = ocClaimProduct.get(0).getClmEtcRsnText();
				}
			}
		}

		if (isAllCancel) {
			cancelReason = clmEtcRsnText + " [주문전체취소]";
		} else {
			cancelReason = clmEtcRsnText + " [주문부분취소]";
		}

		try {
			String clientId = "";
			String clientSecret = "";
			String partnerCode = "";
			if (UtilsText.equals(ocClaim.getSiteNo(), Const.SITE_NO_ART)) {
				clientId = Config.getString("art.naver.api.clientId");
				clientSecret = Config.getString("art.naver.api.clientSecret");
				partnerCode = Config.getString("art.naver.api.partnerCode");
			}
			if (UtilsText.equals(ocClaim.getSiteNo(), Const.SITE_NO_OTS)) {
				clientId = Config.getString("ots.naver.api.clientId");
				clientSecret = Config.getString("ots.naver.api.clientSecret");
				partnerCode = Config.getString("ots.naver.api.partnerCode");
			}

			mapData.put("siteNo", ocClaim.getSiteNo());

			naverPaymentCancel.setNaverClientKey(clientId);
			naverPaymentCancel.setNaverClientSecret(clientSecret);
			naverPaymentCancel.setPartnerCode(partnerCode);
			naverPaymentCancel.setPaymentId(claimPayment.getPymntAprvNoText());
			naverPaymentCancel.setMerchantPayKey(ocClaim.getOrderNo());
			naverPaymentCancel.setCancelAmount((double) claimPayment.getPymntAmt());
			naverPaymentCancel.setTaxScopeAmount((double) claimPayment.getPymntAmt());
			naverPaymentCancel.setTaxExScopeAmount((double) 0);
			naverPaymentCancel.setCancelRequester("2"); // 취소요청자(1:구매자 2:가맹점관리자)
			// 2020.03.12 : 75 byte 제한
			naverPaymentCancel.setCancelReason(UtilsText.strByteSubstring(cancelReason, 0, 75));

			// 2020.04.02 : 로그추가
			log.error("네이버페이 취소/부분취소 - claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
			log.error(">>>>>>> to NAVER : " + naverPaymentCancel.toString());
			paymentResult = paymentEntrance
					.cancel(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_NAVER, naverPaymentCancel, mapData));
			log.error("<<<<<<< from NAVER : " + paymentResult.toString());
		
		} catch (Exception e) {
			log.error("네이버페이 취소/부분취소 에러 paymentResult >>>>> " + UtilsText.stringify(paymentResult));
			paymentResult = new PaymentResult(Const.BOOLEAN_FALSE);
			log.error("네이버페이 취소/부분취소 에러 - claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
			log.error("네이버페이 취소/부분취소 에러 : " + e);
		}

		return paymentResult;
	}

	/**
	 * @Desc : 카카오페이 취소
	 * @Method Name : setCancelKakaoPayment
	 * @Date : 2019. 5. 24.
	 * @Author : KTH
	 * @param claimPayment
	 * @param ocClaim
	 * @param isAllCancel
	 * @return
	 * @throws PaymentException
	 */
	public PaymentResult setCancelKakaoPayment(OcClaim ocClaim, OcClaimPayment claimPayment, boolean isAllCancel) {
		PaymentResult paymentResult = null;
		KakaoPaymentCancel kakaoPaymentCancel = new KakaoPaymentCancel();

		try {
			if (UtilsText.equals(ocClaim.getSiteNo(), Const.SITE_NO_ART)) {
				kakaoPaymentCancel.setKakaoAdminKey(Config.getString("art.kakao.api.adminkey"));
				kakaoPaymentCancel.setCid(Config.getString("art.kakao.api.cid"));
			}
			if (UtilsText.equals(ocClaim.getSiteNo(), Const.SITE_NO_OTS)) {
				kakaoPaymentCancel.setKakaoAdminKey(Config.getString("ots.kakao.api.adminkey"));
				kakaoPaymentCancel.setCid(Config.getString("ots.kakao.api.cid"));
			}

			kakaoPaymentCancel.setTid(claimPayment.getPymntOrganNoText());
			kakaoPaymentCancel.setCancelAmount(claimPayment.getPymntAmt());
			kakaoPaymentCancel.setCancelTaxFreeAmount(claimPayment.getPymntAmt());

			// 2020.04.02 : 로그추가
			log.error("카카오페이 취소 - claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
			log.error(">>>>>>>> to KAKAO : " + kakaoPaymentCancel.toString());
			paymentResult = paymentEntrance
					.cancel(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KAKAO, kakaoPaymentCancel));
			log.error("<<<<<<<< from KAKAO : " + paymentResult.toString());
			
		} catch (Exception e) {
			paymentResult = new PaymentResult(Const.BOOLEAN_FALSE);
			log.error("카카오페이 취소 에러 - claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
			log.error("카카오페이 취소 에러 : " + e);
		}

		return paymentResult;
	}

	/**
	 * @Desc : 기프트카드 취소(주문기준 취소, 결제기준 충전) - 취소 금액만큼 충전
	 * @Method Name : setCancelNicePayment
	 * @Date : 2019. 7. 12.
	 * @Author : KTH
	 * @param ocClaim
	 * @param claimPayment
	 * @param allCancelYn
	 * @return
	 */
	public Map<String, Object> setCancelNicePayment(OcClaim ocClaim, OcClaimPayment claimPayment, boolean allCancelYn) {

		Map<String, Object> resultMap = new HashMap<>();
		CommNiceRes<ChargeResponse> chargeResult = null;
		OcOrderPayment giftChargePayment = new OcOrderPayment();
		giftChargePayment.setMemberNo(ocClaim.getMemberNo()); // 회원번호
		giftChargePayment.setPymntOrganNoText(claimPayment.getPymntOrganNoText()); // 결제시 카드번호
		giftChargePayment.setGiftCardPinNoText(claimPayment.getGiftCardPinNoText()); // 결제시 핀번호
		int chargeGiftAmt = 0;

		try {
			// 대표카드 번호 조회
			// 대표카드가 없는 경우는 현재카드 번호(현재카드가 대표카드)
			giftChargePayment = ocOrderPaymentDao.selectByReturnRprsntCard(giftChargePayment);

			// 카드삭제 등의 이유로 조회가 안되는 경우는 임의 set
			if (giftChargePayment == null) {
				giftChargePayment = new OcOrderPayment();
				giftChargePayment.setMemberNo(ocClaim.getClaimRgsterNo()); // 회원번호
				giftChargePayment.setPymntOrganNoText(claimPayment.getPymntOrganNoText()); // 결제시 카드번호
				giftChargePayment.setGiftCardPinNoText(claimPayment.getGiftCardPinNoText()); // 결제시 핀번호
			}

			if (allCancelYn) { // 전체취소
				chargeGiftAmt = claimPayment.getPymntAmt();
			} else {
				chargeGiftAmt = claimPayment.getPymntAmt();
			}

			log.debug("기프트카드 취소 - pymntOrganNoText : {} chargeGiftAmt : {}", giftChargePayment.getPymntOrganNoText(),
					chargeGiftAmt);

			// 2020.03.16 : exchangeCd "1" -> "2"
			// "1" : 일반 판매
			// "2: : 교환 판매
			// "3" : 특판 판매
			// "4" : 자가소비(발행사소비)
			// "5" : 의뢰 입고
			// "6" : 취소 복원
			ChargeRequest chargeRequest = new ChargeRequest(giftChargePayment.getPymntOrganNoText(), chargeGiftAmt,
					"2");
			log.error("기프트카드 전문 확인 : " + chargeRequest.toString());
			chargeResult = niceGiftService.sendCharge(chargeRequest); // 기프트 충전

		} catch (Exception e) {
			chargeResult = new CommNiceRes<ChargeResponse>();
			chargeResult.setResCode("9999");
			log.error("기프트카드 취소 에러 - claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
			e.printStackTrace();
		}

		resultMap.put("chargeResult", chargeResult);
		resultMap.put("giftCardNo", giftChargePayment.getPymntOrganNoText());
		resultMap.put("chargeGiftAmt", chargeGiftAmt);

		return resultMap;
	}

	/**
	 * @Desc : 포인트 사용 취소(구매/이벤트 포인트)
	 * @Method Name : setCancelOrderUsePoint
	 * @Date : 2019. 6. 17.
	 * @Author : KTH
	 * @param pointPayment
	 * @param eventPointPayment
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public boolean setCancelOrderUsePoint(OcClaim ocClaim, OcClaimPayment pointPayment,
			OcClaimPayment eventPointPayment, MbMember mbMember) {

		boolean success = true;
		Integer pointAmt = 0;
		Integer eventPointAmt = 0;

		try {
			if (pointPayment != null && pointPayment.getPymntAmt() > 0) {
				pointAmt = pointPayment.getPymntAmt();
			}

			if (eventPointPayment != null && eventPointPayment.getPymntAmt() > 0) {
				eventPointAmt = eventPointPayment.getPymntAmt();
			}

			MbMemberPoint mbMemberPoint = new MbMemberPoint();

			mbMemberPoint.setMemberNo(mbMember.getMemberNo());
			mbMemberPoint.setPoint(pointAmt);
			mbMemberPoint.setEventPoint(eventPointAmt);
			mbMemberPoint.setOrderNo(ocClaim.getOrderNo());

			PointUse complateChk = memberPointService.setPurchaseCancelPoint(mbMemberPoint);

			if (UtilsText.equals(complateChk.getReSult(), Const.IF_RESULT_FAIL)) {
				success = false;
			}
		} catch (Exception e) {
			success = false;
			log.error("포인트 사용 취소(구매/이벤트 포인트) 에러 - claimNo : {} orderNo : {} ", ocClaim.getClmNo(),
					ocClaim.getOrderNo());
			e.printStackTrace();
		}

		return success;
	}

	/**
	 * @Desc : 재경팀 처리용 환수/환불 대상 등록(취소를 진행하며 발생한 환수 또는 환불 결제금에 대한 sum을 별도 재경처리용으로 등록)
	 * @Method Name : saveDepositForPymntGubunNonRefundableOrder
	 * @Date : 2019. 6. 21.
	 * @Author : KTH
	 * @param ocClaim
	 * @throws Exception
	 */
	public void setRedempRefundMmnyProc(OcClaim ocClaim) {
		log.error("재경팀 처리용 환수/환불 대상 등록 START : ClmNo => {}", ocClaim.getClmNo());

		OcClaimPayment claimPyment = new OcClaimPayment();
		List<OcClaimPayment> claimPaymentList = null;

		try {
			claimPyment.setClmNo(ocClaim.getClmNo());

			claimPaymentList = ocClaimPaymentDao.selectClaimPaymentList(claimPyment);
		} catch (Exception e) {
			log.error("클레임결제 조회 에러 - claimPyment={}", claimPyment);
			log.error("클레임결제 조회 에러 - Exception e : " + e);
		}

		/*
		 * 재경처리 환불금 대상 등록
		 */
		// 환불, 재경처리 N, 환수접수상태, 일반결제 대상으로 환불금 sum
		int refundAmtSum = claimPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
				.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
				.filter(x -> UtilsText.equals(x.getPymntStatCode(), CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT))
				.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_PUBLIC))
				.filter(x -> UtilsText.equals(x.getClmNo(), ocClaim.getClmNo())).mapToInt(o -> o.getPymntAmt()).sum();

		OcClaimPayment refundClaimPayment = new OcClaimPayment();

		if (refundAmtSum > 0) {
			// 재경처리 환불금 등록
			try {
				refundClaimPayment.setClmNo(ocClaim.getClmNo()); // 클레임번호
				refundClaimPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REFUND); // 환수환불구분
				refundClaimPayment.setPymntDtm(null); // 결제일시
				refundClaimPayment.setDeviceCode(ocClaim.getDeviceCode());
				refundClaimPayment.setMainPymntMeansYn(Const.BOOLEAN_FALSE); // 주결제 수단 여부 : 재경처리 데이터는 의미없음
				refundClaimPayment.setPymntMeansCode(CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT); // 결제수단코드 : 재경처리 데이터는
																									// 의미없음
				refundClaimPayment.setInstmtTermCount((short) 0); // 할부기간 : 재경처리 데이터는 의미없음
				refundClaimPayment.setPymntTodoAmt(refundAmtSum); // 결제예정금액
				refundClaimPayment.setPymntAmt(refundAmtSum); // 결제금액
				refundClaimPayment.setCardPartCnclPsbltYn(Const.BOOLEAN_FALSE); // 카드부분취소가능여부 : 재경처리 데이터는 의미없음
				refundClaimPayment.setCashRcptIssueYn(Const.BOOLEAN_FALSE); // 현금영수증발급여부 : 재경처리 데이터는 의미없음
				refundClaimPayment.setEscrApplyYn(Const.BOOLEAN_FALSE); // 에스크로적용여부 : 재경처리 데이터는 의미없음
				refundClaimPayment.setBankCode(ocClaim.getBankCode()); // 은행코드 : 환불:회원환불계좌
				refundClaimPayment.setAcntNoText(ocClaim.getRfndAcntText()); // 계좌번호
				refundClaimPayment.setAcntHldrName(ocClaim.getAcntHldrName()); // 예금주명
				refundClaimPayment.setAcntCrtfcYn(Const.BOOLEAN_TRUE); // 계좌인증여부
				refundClaimPayment.setMmnyProcTrgtYn(Const.BOOLEAN_TRUE); // 자사처리대상여부 : 재경처리 Y
				refundClaimPayment.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 결제상태코드 : 환불접수
				refundClaimPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_PUBLIC); // 이력구분 : 일반결제처리용
				refundClaimPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_ORDER_AMT); // 발생사유코드
				refundClaimPayment.setRedempRfndMemoText("주문 결제취소 실패로 인한 환불발생");
				refundClaimPayment.setRgsterNo(ocClaim.getClaimRgsterNo());
				refundClaimPayment.setModerNo(ocClaim.getClaimRgsterNo());

				// 2020.03.26 : 재경팀환불금에 이중으로 등록되는것 방지코드
				int count = ocClaimPaymentDao.selectValidateRefundPymnt(refundClaimPayment);
				if (count == 0) {
					ocClaimPaymentDao.insertClaimPayment(refundClaimPayment);
				} else {
					log.error("재경처리 환불금 이중 등록 방지 작동 - refundClaimPayment={}", refundClaimPayment);
				}

			} catch (Exception e) {
				log.error("재경처리 환불금 등록 에러 - refundClaimPayment={}", refundClaimPayment);
				e.printStackTrace();
			}
		}

		/*
		 * 재경처리 환수금 대상 등록
		 */
		OcClaimPayment redempClaimPayment = new OcClaimPayment();

		// 환불, 재경처리 N, 환수접수상태, 일반결제 대상으로 환불금 sum
		int redempAmtSum = claimPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP))
				.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
				.filter(x -> UtilsText.equals(x.getPymntStatCode(), CommonCode.PYMNT_STAT_CODE_REDEMP_ACCEPT))
				.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_PUBLIC))
				.filter(x -> UtilsText.equals(x.getClmNo(), ocClaim.getClmNo())).mapToInt(o -> o.getPymntAmt()).sum();

		if (redempAmtSum > 0) {
			// 재경처리 환수금 등록
			try {
				redempClaimPayment.setClmNo(ocClaim.getClmNo()); // 클레임번호
				redempClaimPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP); // 환수환불구분
				redempClaimPayment.setPymntDtm(null); // 결제일시
				redempClaimPayment.setDeviceCode(ocClaim.getDeviceCode());
				redempClaimPayment.setMainPymntMeansYn(Const.BOOLEAN_FALSE); // 주결제 수단 여부 : 재경처리 데이터는 의미없음
				redempClaimPayment.setPymntMeansCode(CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT); // 결제수단코드 : 재경처리 데이터는
																									// 의미없음
				redempClaimPayment.setInstmtTermCount((short) 0); // 할부기간 : 재경처리 데이터는 의미없음
				redempClaimPayment.setPymntTodoAmt(redempAmtSum); // 결제예정금액
				redempClaimPayment.setPymntAmt(redempAmtSum); // 결제금액
				redempClaimPayment.setCardPartCnclPsbltYn(Const.BOOLEAN_FALSE); // 카드부분취소가능여부 : 재경처리 데이터는 의미없음
				redempClaimPayment.setCashRcptIssueYn(Const.BOOLEAN_FALSE); // 현금영수증발급여부 : 재경처리 데이터는 의미없음
				redempClaimPayment.setEscrApplyYn(Const.BOOLEAN_FALSE); // 에스크로적용여부 : 재경처리 데이터는 의미없음
				redempClaimPayment.setBankCode(ocClaim.getBankCode()); // 은행코드 : 환수:환수가상계좌
				redempClaimPayment.setAcntNoText(ocClaim.getRfndAcntText()); // 계좌번호
				redempClaimPayment.setAcntHldrName(ocClaim.getAcntHldrName()); // 예금주명
				redempClaimPayment.setAcntCrtfcYn(Const.BOOLEAN_TRUE); // 계좌인증여부
				redempClaimPayment.setMmnyProcTrgtYn(Const.BOOLEAN_TRUE); // 자사처리대상여부 : 재경처리 Y
				redempClaimPayment.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REDEMP_ACCEPT); // 결제상태코드 : 환수접수
				redempClaimPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_PUBLIC); // 이력구분 : 일반결제처리용
				redempClaimPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_ORDER_AMT); // 발생사유코드
				refundClaimPayment.setRedempRfndMemoText("주문 결제취소로 인한 환수발생");
				redempClaimPayment.setRgsterNo(ocClaim.getClaimRgsterNo());
				redempClaimPayment.setModerNo(ocClaim.getClaimRgsterNo());

				ocClaimPaymentDao.insertClaimPayment(redempClaimPayment);
			} catch (Exception e) {
				log.error("재경처리 환수금 등록 에러 - redempClaimPayment={}", redempClaimPayment);
				e.printStackTrace();
			}
		}

		log.error("재경팀 처리용 환수/환불 대상 등록 END : ClmNo => {}", ocClaim.getClmNo());
	}

	/**
	 * @Desc : 주문 취소 인터페이스 호출(배송비 제외한 본 상품 대상-사은품은 본 상품에 포함된 정보로 처리)
	 * @Method Name : setCancelOrderInterface
	 * @Date : 2019. 6. 18.
	 * @Author : KTH
	 * @param orderProduct
	 */
	public void callCancelOrderInterface(OcOrderProduct orderProduct) {
		try {
			// wms 전송 한 경우에만 인터페이스 처리
			if (UtilsText.equals(orderProduct.getWmsSendYn(), Const.BOOLEAN_TRUE)) {
				String cbcd = orderProduct.getStockGbnCbcd(); // B코드
				String maejangCd = orderProduct.getInsdMgmtInfoText(); // 매장코드
				String dlvyId = orderProduct.getDlvyIdText(); // 배송아이디
				String itemSno = String.valueOf(orderProduct.getOrderPrdtSeq()); // 서브키
				String sangpumFullCd = orderProduct.getVndrPrdtNoText().concat("001")
						.concat(orderProduct.getPrdtOptnNo()); // 상품풀코드
				Integer count = 1; // 수량

				// 본상품 취소 인터페이스
				interfacesClaimService.updateOrderPrdtNoGiftCancelNoTrx(cbcd, maejangCd, dlvyId, itemSno, sangpumFullCd,
						String.valueOf(count));

				// 사은품이 있는 경우
				if (!ObjectUtils.isEmpty(orderProduct.getOrderGiftPrdtSeq())) {
					OcOrderProduct giftProduct = new OcOrderProduct();
					giftProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품추출용(구조만맞추기위한의미없는set)
					giftProduct.setOrderNo(orderProduct.getOrderNo());
					giftProduct.setOrderPrdtSeq(orderProduct.getOrderGiftPrdtSeq());

					giftProduct = ocOrderProductDao.selectOrderProductDetailForClaim(giftProduct);

					String giftCbcd = giftProduct.getStockGbnCbcd(); // B코드
					String giftMaejangCd = giftProduct.getInsdMgmtInfoText(); // 매장코드
					String giftDlvyId = giftProduct.getDlvyIdText(); // 배송아이디
					String freeGiftItemSno = String.valueOf(giftProduct.getOrderPrdtSeq()).concat("F")
							.concat(String.valueOf(giftProduct.getUpOrderPrdtSeq())); // 서브키
					String giftSangpumFullCd = giftProduct.getVndrPrdtNoText().concat("001")
							.concat(giftProduct.getPrdtOptnNo()); // 상품풀코드
					Integer giftCount = 1; // 수량

					// 사은품 취소 인터페이스
					interfacesClaimService.updateOrderPrdtGiftCancelNoTrx(giftCbcd, giftMaejangCd, giftDlvyId,
							freeGiftItemSno, giftSangpumFullCd, String.valueOf(giftCount));
				}
			}
		} catch (Exception e) {
			log.error("주문취소 인터페이스 에러 - orderNo : {}, orderPrdtSeq{} ", orderProduct.getOrderNo(),
					orderProduct.getOrderPrdtSeq());
			e.printStackTrace();
		}
	}

	/**
	 * @Desc : 구매확정 처리 호출(반품 클레임 시)
	 * @Method Name : orderConfirmNoTrx
	 * @Date : 2019. 9. 4.
	 * @Author : KTH
	 * @param ocOrder
	 */
	public Map<String, Object> orderConfirmNoTrx(OcOrder ocOrder) {
		Map<String, Object> resultMap = new HashMap<>();

		try {
			resultMap = orderService.updateOrderConfirm(ocOrder);

			if (UtilsText.equals((String) resultMap.get("resultCode"), Const.BOOLEAN_FALSE)) {
				log.error("반품 클레임 구매확정 처리 실패 orderNo : {}", ocOrder.getOrderNo());
			}
		} catch (Exception e) {
			log.error("반품 클레임 구매확정 처리 실패 orderNo : {}", ocOrder.getOrderNo());
		}

		return resultMap;
	}

	/**
	 * @Desc : 업무도메인에서 사용하는 은행코드를 kcp은행(증권사) 코드로 반환
	 * @Method Name : getKcpBankCode
	 * @Date : 2019. 7. 31.
	 * @Author : KTH
	 * @param domainBankCode
	 * @return
	 * @throws Exception
	 */
	public String getKcpBankCode(String domainBankCode) {
		String kcpBankCode = "";

		if (UtilsText.isEmpty(domainBankCode)) {
			return kcpBankCode;
		}

		List<SyCodeDetail> bankList = null;
		try {
			bankList = commonCodeService.getCodeNoName(CommonCode.BANK_CODE); // 은행코드 목록

			// kcp 리턴 bankcode를 이용하여 공통코드의 bankcode 를 추출
			String _bankCode = bankList.stream().filter(x -> UtilsText.equals(x.getCodeDtlNo(), domainBankCode))
					.map(SyCodeDetail::getAddInfo1).findFirst().orElse(null);

			if (UtilsText.isNotEmpty(_bankCode)) {
				// kcpBankCode = _bankCode.replaceAll("BK", "").replaceAll("B", "");
				kcpBankCode = _bankCode;
			}
		} catch (Exception e) {
			log.error("은행코드 목록 추출 에러");
			e.printStackTrace();
		}

		log.debug("######################## bankCode : {}", kcpBankCode);

		return kcpBankCode;
	}

	/**
	 * @Desc : 환불금차감 반품완료 후 배송비결제취소
	 * @Method Name : setCancelReturnDlvyPayment
	 * @Date : 2020. 2. 26.
	 * @Author : 이강수
	 * @param paymentCancelInfo
	 * @param ocClaim
	 * @param useEscr
	 * @throws Exception
	 */
	public OcClaimAmountVO setCancelReturnDlvyPayment(OcClaimAmountVO paymentCancelInfo, OcClaim ocClaim,
			MbMember mbMember, OcClaimPayment redempDlvyAmtPymnt) {

		ObjectMapper mapper = new ObjectMapper();
		PaymentResult paymentResult = null; // 결제취소 결과
		boolean pointCancelSuccess = false; // 포인트 취소 성공여부
		boolean isRefundOccurrence = false; // 환불접수 상황 발생 여부
		
		String isDlvyAmtAllCancelYn = Const.BOOLEAN_TRUE; // 반품 배송비 취소완료여부

		log.error("::::::::::::::::::::::: 결제취소할 반품배송비 : " + redempDlvyAmtPymnt.getPymntAmt().toString());

		/*
		 * 결제 취소 정보 저장
		 */
		OcClaimPayment mainPayment = paymentCancelInfo.getMainPayment();
		OcClaimPayment pointPayment = paymentCancelInfo.getPointPayment();
		OcClaimPayment eventPointPayment = paymentCancelInfo.getEventPointPayment();
		
		/*
		 * 구매/이벤트 포인트 사용 취소
		 */
		if ((pointPayment != null && pointPayment.getPymntAmt() > 0)
				|| (eventPointPayment != null && eventPointPayment.getPymntAmt() > 0)) {
			// 포인트 결제 취소 처리
			pointCancelSuccess = this.setCancelOrderUsePoint(ocClaim, pointPayment, eventPointPayment, mbMember);

			/*
			 * 구매/이벤트 포인트 사용 취소 결과 등록
			 */
			OcClaimPayment pointCancelResult = new OcClaimPayment();

			if (pointCancelSuccess) {
				if (pointPayment != null) {
					BeanUtils.copyProperties(pointPayment, pointCancelResult); // 내용 복사

					pointCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL); // 결제취소
					log.error("반품배송비 구매포인트 취소 성공! - claimNo : {} orderNo : {}", ocClaim.getClmNo(),ocClaim.getOrderNo());
					
					try {
						this.setCancelReturnDlvyPayment(ocClaim, pointCancelResult);
					} catch (Exception e) {
						log.error("반품배송비 구매포인트 취소 데이터 등록 에러  - claimNo : {} orderNo : {}", ocClaim.getClmNo(),
								ocClaim.getOrderNo());
						e.printStackTrace();
					}
				}

				if (eventPointPayment != null) {
					BeanUtils.copyProperties(eventPointPayment, pointCancelResult); // 내용 복사

					pointCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL); // 결제취소
					log.error("반품배송비 이벤트포인트 취소 성공! - claimNo : {} orderNo : {}", ocClaim.getClmNo(),ocClaim.getOrderNo());
					
					try {
						this.setCancelReturnDlvyPayment(ocClaim, pointCancelResult);
					} catch (Exception e) {
						log.error("반품배송비 이벤트포인트 취소 데이터 등록 에러  - claimNo : {} orderNo : {}", ocClaim.getClmNo(),
								ocClaim.getOrderNo());
						e.printStackTrace();
					}
				}
			} else {
				if (pointPayment != null) {

					log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					log.error("반품배송비 claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
					log.error("pointPaymentCancel is fail : 반품배송비 구매포인트 결제취소 실패");
					log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

					BeanUtils.copyProperties(pointPayment, pointCancelResult); // 내용 복사
					isRefundOccurrence = true; // 환불접수 상황 발생

					pointCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 환불접수
					this.setClaimPayment(ocClaim, pointCancelResult, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
				}

				if (eventPointPayment != null) {

					log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					log.error("반품배송비 claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
					log.error("eventPointPaymentCancel is fail : 반품배송비 이벤트포인트 결제취소 실패");
					log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

					BeanUtils.copyProperties(eventPointPayment, pointCancelResult); // 내용 복사
					isRefundOccurrence = true; // 환불접수 상황 발생

					pointCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 환불접수
					this.setClaimPayment(ocClaim, pointCancelResult, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
				}
			}
		}
		
		/*
		 * 주결제 결제 취소
		 */
		if (mainPayment != null && mainPayment.getPymntAmt() > 0) {

			if (paymentCancelInfo.isAllCancelYn()) { // 전체취소
				if (UtilsText.equals(mainPayment.getPymntVndrCode(), CommonCode.PYMNT_VNDR_CODE_KCP)) {
					// 2020.02.26 : 결제수단이 신용/체크카드인 결제완료된 잔여금액만 취소 가능
					if (UtilsText.equals(mainPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_CARD)
							&& UtilsText.equals(mainPayment.getPymntStatCode(),
									CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH)) {
						paymentResult = this.setCancelKcpPayment(ocClaim, mainPayment, true, true);
					}
				// 2020.05.13 : 네이버페이도 환불금차감 반품완료후 배송비 취소 추가
				} else if (UtilsText.equals(mainPayment.getPymntVndrCode(), CommonCode.PYMNT_VNDR_CODE_NAVER)) {
					paymentResult = this.setCancelNaverPayment(mainPayment, ocClaim, true);
				} 
			} else {
				// 결제부분취소
				if (UtilsText.equals(mainPayment.getPymntVndrCode(), CommonCode.PYMNT_VNDR_CODE_KCP)) {
					// 2020.02.26 : 결제수단이 신용/체크카드인 결제완료된 잔여금액만 취소 가능
					if (UtilsText.equals(mainPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_CARD)
							&& UtilsText.equals(mainPayment.getPymntStatCode(),
									CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH)) {
						paymentResult = this.setCancelKcpPayment(ocClaim, mainPayment, false, true);
					}
				// 2020.05.13 : 네이버페이도 환불금차감 반품완료후 배송비 취소 추가
				} else if (UtilsText.equals(mainPayment.getPymntVndrCode(), CommonCode.PYMNT_VNDR_CODE_NAVER)) {
					paymentResult = this.setCancelNaverPayment(mainPayment, ocClaim, false);
				} 
			}
			
			// 2020.05.06 : 환불금차감 반품완료 후 배송비 취소해줄 때 BYPASS(포인트 선 환원) 해줬을 경우를 위한
//			paymentResult = new PaymentResult("Y","결제취소 성공");
//			paymentResult.setSuccessYn("Y");
//			paymentResult.setCode("0000");
//			paymentResult.setMessage("결제취소 성공");
			
			/*
			 * 주결제 결제 취소 결과 등록
			 */
			OcClaimPayment mainCancelResult = new OcClaimPayment();
			BeanUtils.copyProperties(mainPayment, mainCancelResult); // 내용 복사

			// 2020.03.05 : test 결제취소 결과를 받지 못하였을때
			// paymentResult = null;

			if (paymentResult == null) {
				isRefundOccurrence = true; // 환불접수 상황 발생
				mainCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 환불접수
				mainCancelResult.setRspnsCodeText("9999");
				mainCancelResult.setRspnsMesgText("결제취소 실패");

				log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				log.error("반품배송비 결제취소 claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
				log.error("paymentResult is null : 반품배송비 결제취소 실패");
				log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

				// 배송비취소 결과 담기
				paymentCancelInfo.setMainCancelResult(mainCancelResult);
				// 배송비취소 결과 등록
				try {
					this.setCancelReturnDlvyPaymentMnnyProcTrgtY(ocClaim, mainCancelResult);
				} catch (Exception e) {
					log.error("반품배송비 결제취소 재경팀환불 등록 에러 - claimNo : {} orderNo : {}", ocClaim.getClmNo(),
							ocClaim.getOrderNo());
					e.printStackTrace();
				}
			} else {

				if (UtilsText.equals(paymentResult.getSuccessYn(), Const.BOOLEAN_TRUE)) {
					mainCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL); // 결제취소
				} else {
					isRefundOccurrence = true; // 환불접수 상황 발생
					mainCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT);

					log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
					log.error("반품배송비 결제취소 claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
					log.error("paymentResult is not null and fail : ");
					log.error("paymentResult.getSuccessYn() : " + paymentResult.getSuccessYn());
					log.error("paymentResult.getCode() : " + paymentResult.getCode());
					log.error("paymentResult.getMessage() : " + paymentResult.getMessage());
					log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

					if (UtilsText.equals(mainCancelResult.getPymntVndrCode(), CommonCode.PYMNT_VNDR_CODE_KCP)) {
						// KCP 결제취소 실패
						// 2020.02.13 : [8133] KCP에 이미 취소요청하여 취소처리된 거래건에 다시 취소요청 시 발생.
						// 2020.02.14 : [8150] KCP에 이미 취소요청하여 취소처리된 거래건에 다시 취소요청 시 발생.
						// 2020.03.19 : [8729] KCP 계좌이체 기 취소된 거래
						if (!UtilsText.equals(paymentResult.getCode(), Const.KCP_ALRDY_CANCEL_CODE_CARD)
								&& !UtilsText.equals(paymentResult.getCode(), Const.KCP_ALRDY_CANCEL_CODE_PART)
								&& !UtilsText.equals(paymentResult.getCode(), Const.KCP_ALRDY_CANCEL_CODE_ACCOUNT)) {
							// [8133]이 아닌 KCP 거래취소 실패 응답코드는 '재경팀 환불 접수'
							// [8150]부분매입취소후 남은금액의 합계가 원거래 금액과 동일하고,
							// 남은 금액이 DB의 부분매입취소 가능금액과 동일한 경우 이미 처리된 요청건으로 간주함
							isRefundOccurrence = true; // 환불접수 상황 발생
							mainCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 환불접수
						}

					} else if (UtilsText.equals(mainCancelResult.getPymntVndrCode(), CommonCode.PYMNT_VNDR_CODE_NAVER)) {
						// NAVER 결제취소 실패
						// 2020.04.02 : [AlreadyCanceled] 이미 전체 취소된 결제
						if (!UtilsText.equals(paymentResult.getCode(), Const.NAVER_ALRDY_CANCEL_CODE)) {
							// [AlreadyCanceled]이 아닌 NAVER 거래취소 실패 응답코드는 '재경팀 환불 접수'
							isRefundOccurrence = true; // 환불접수 상황 발생
							mainCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 환불접수
						} else {
							// [AlreadyCanceled]면 취소완료
							isRefundOccurrence = false; // 환불접수 상황 X
							mainCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL);
						}

					} else {
						isRefundOccurrence = true; // 환불접수 상황 발생
						mainCancelResult.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 환불접수
					}
				}

				mainCancelResult.setRspnsCodeText(paymentResult.getCode());
				mainCancelResult.setRspnsMesgText(paymentResult.getMessage());

				try {
					mainCancelResult.setPymntLogInfo(mapper.writeValueAsString(paymentResult.getData()));

					// 결제 리턴 로그
					log.error("+++++++++++++++++++  PymntLogInfo");
					log.error(mainCancelResult.getPymntLogInfo());
					log.error("+++++++++++++++++++++++++++++++++");

				} catch (JsonProcessingException e) {
					log.error("결제처리 데이터 변환 에러 - claimNo : {} orderNo : {}", ocClaim.getClmNo(),
							ocClaim.getOrderNo());
					mainCancelResult.setPymntLogInfo(null);
					log.error("결제처리 데이터 변환 에러 - Exception e : " + e);
				}

				// 배송비취소 결과 담기
				paymentCancelInfo.setMainCancelResult(mainCancelResult);
				// 배송비취소 결과 등록
				if (isRefundOccurrence) {
					try {
						this.setCancelReturnDlvyPaymentMnnyProcTrgtY(ocClaim, mainCancelResult);
					} catch (Exception e) {
						log.error("반품배송비 주결제취소 재경팀환불 등록 에러 - claimNo : {} orderNo : {}", ocClaim.getClmNo(),
								ocClaim.getOrderNo());
						e.printStackTrace();
					}
				} else {
					try {
						this.setCancelReturnDlvyPayment(ocClaim, mainCancelResult);
					} catch (Exception e) {
						log.error("반품배송비 주결제취소 데이터 등록 에러  - claimNo : {} orderNo : {}", ocClaim.getClmNo(),
								ocClaim.getOrderNo());
						e.printStackTrace();
					}
				}
			}
		}

		log.error("환불발생함? " + isRefundOccurrence);
		paymentCancelInfo.setRefundOccurrence(isRefundOccurrence);
		paymentCancelInfo.setIsDlvyAmtAllCancelYn(isDlvyAmtAllCancelYn);

		return paymentCancelInfo;
	}

	/**
	 * @Desc : 반품배송비 결제취소 데이터 등록 / 주문과 주문상품 리오더 매출 등록
	 * @Method Name : setCancelReturnDlvyPayment
	 * @Date : 2019. 2. 27.
	 * @Author : 이강수
	 * @param ocClaim
	 * @param claimPayment
	 * @throws Exception
	 */
	public void setCancelReturnDlvyPayment(OcClaim ocClaim, OcClaimPayment claimPayment) throws Exception {

		claimPayment.setClmNo(ocClaim.getClmNo()); // 클레임번호
		claimPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REFUND); // 환수환불구분
		claimPayment.setPymntDtm(new Timestamp(new Date().getTime())); // 결제일시
		claimPayment.setAcntCrtfcYn(Const.BOOLEAN_FALSE); // 계좌인증여부
		claimPayment.setRedempRfndMemoText(null); // 환수환불메모
		claimPayment.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 자사처리대상여부
		claimPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_ORDER_AMT); // 발생사유코드 - 배송비지만 주문금으로 취소
		claimPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_PUBLIC); // 클레임이력구분 - 일반결제처리용
		claimPayment.setPymntTodoAmt(claimPayment.getPymntAmt());
		claimPayment.setPymntAmt(claimPayment.getPymntAmt());
		claimPayment.setRgsterNo(ocClaim.getClaimRgsterNo()); // 등록자
		claimPayment.setModerNo(ocClaim.getClaimRgsterNo()); // 수정자

		log.error("반품배송비 결제취소 데이터 등록  - claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
		ocClaimPaymentDao.insertClaimPayment(claimPayment);
		
		// 2020.05.06 : 환불금차감 반품완료 후 배송비 취소해줄 때 BYPASS(포인트 선 환원) 해줬을 경우를 위한
//		claimPayment.setPymntMeansCode(CommonCode.PYMNT_MEANS_CODE_BUY_POINT);
//		claimPayment.setPymntTodoAmt(1400);
//		claimPayment.setPymntAmt(1400);
//
//		log.error("반품배송비 결제취소 데이터 등록  - claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrderNo());
//		ocClaimPaymentDao.insertClaimPayment(claimPayment);
	}

	/**
	 * @Desc : 반품배송비 결제취소 재경팀환불처리 등록
	 * @Method Name : setCancelReturnDlvyPaymentMnnyProcTrgtY
	 * @Date : 2019. 2. 27.
	 * @Author : 이강수
	 * @param ocClaim
	 * @param claimPayment
	 * @throws Exception
	 */
	public void setCancelReturnDlvyPaymentMnnyProcTrgtY(OcClaim ocClaim, OcClaimPayment claimDeliveryPayment)
			throws Exception {

		OcClaimPayment refundClaimPayment = new OcClaimPayment();

		refundClaimPayment.setClmNo(ocClaim.getClmNo()); // 클레임번호
		refundClaimPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REFUND); // 환수환불구분
		refundClaimPayment.setPymntDtm(null); // 결제일시
		refundClaimPayment.setDeviceCode(ocClaim.getDeviceCode());
		refundClaimPayment.setMainPymntMeansYn(Const.BOOLEAN_FALSE); // 주결제 수단 여부 : 재경처리 데이터는 의미없음
		refundClaimPayment.setPymntMeansCode(CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT); // 결제수단코드 : 재경처리 데이터는
																							// 의미없음
		refundClaimPayment.setInstmtTermCount((short) 0); // 할부기간 : 재경처리 데이터는 의미없음
		refundClaimPayment.setPymntTodoAmt(claimDeliveryPayment.getPymntAmt()); // 취소 결제예정금액
		refundClaimPayment.setPymntAmt(claimDeliveryPayment.getPymntAmt()); // 취소 결제금액
		refundClaimPayment.setCardPartCnclPsbltYn(Const.BOOLEAN_FALSE); // 카드부분취소가능여부 : 재경처리 데이터는 의미없음
		refundClaimPayment.setCashRcptIssueYn(Const.BOOLEAN_FALSE); // 현금영수증발급여부 : 재경처리 데이터는 의미없음
		refundClaimPayment.setEscrApplyYn(Const.BOOLEAN_FALSE); // 에스크로적용여부 : 재경처리 데이터는 의미없음
		refundClaimPayment.setBankCode(ocClaim.getBankCode()); // 은행코드 : 환불:회원환불계좌
		refundClaimPayment.setAcntNoText(ocClaim.getRfndAcntText()); // 계좌번호
		refundClaimPayment.setAcntHldrName(ocClaim.getAcntHldrName()); // 예금주명
		refundClaimPayment.setAcntCrtfcYn(Const.BOOLEAN_TRUE); // 계좌인증여부
		refundClaimPayment.setMmnyProcTrgtYn(Const.BOOLEAN_TRUE); // 자사처리대상여부 : 재경처리 Y
		refundClaimPayment.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT); // 결제상태코드 : 환불접수
		refundClaimPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_PUBLIC); // 이력구분 : 일반결제처리용
		refundClaimPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT); // 발생사유코드 : 배송비
		refundClaimPayment.setRedempRfndMemoText("환불금차감 반품배송비 결제취소 실패로 인한 환불발생");
		refundClaimPayment.setRgsterNo(ocClaim.getClaimRgsterNo());
		refundClaimPayment.setModerNo(ocClaim.getClaimRgsterNo());

		refundClaimPayment.setRspnsCodeText(claimDeliveryPayment.getRspnsCodeText());
		refundClaimPayment.setRspnsMesgText(claimDeliveryPayment.getRspnsMesgText());

		log.error("환불금차감 반품배송비 결제취소 실패로 인한 재경팀환불 등록 - claimNo : {} orderNo : {}", ocClaim.getClmNo(),
				ocClaim.getOrderNo());
		ocClaimPaymentDao.insertClaimPayment(refundClaimPayment);
	}

	/**
	 * @Desc : 반품배송비 결제취소 후 로직
	 * @Method Name : setAfterCancelReturnDlvyPayment
	 * @Date : 2019. 2. 27.
	 * @Author : 이강수
	 * @param ocClaim
	 * @param claimPayment
	 * @throws Exception
	 */
	public void setAfterCancelReturnDlvyPayment(String clmNo, OcClaimPayment redempDlvyAmtPymnt) {

		String adminNo = LoginManager.getUserDetails().getAdminNo();
		int redempDlvyAmt = redempDlvyAmtPymnt.getPymntAmt();

		// 4. 반품완료시에 차감한 환수한 추가반품배송비 이력 삭제
		// 환수환불구분 : E
		// 이력구분타입 : H
		// 발생사유코드 : 10003
		// 재경처리여부 : N
		if (redempDlvyAmtPymnt.getClmPymntSeq() != null) {
			try {
				ocClaimPaymentDao.deleteClaimPayment(redempDlvyAmtPymnt);
			} catch (Exception e) {
				log.error("반품완료시에 차감한 환수한 추가반품배송비 삭제  실패 - claimNo : {}", clmNo);
				log.error("반품완료시에 차감한 환수한 추가반품배송비 삭제  실패 - " + e);
				e.printStackTrace();
			}
		}

		// 6. 클레임 마스터 업데이트
		OcClaim ocClaim = new OcClaim();
		ocClaim.setClmNo(clmNo);

		try {
			// 클레임조회
			ocClaim = ocClaimDao.selectByPrimaryKey(ocClaim);
			// 처리자 set
			ocClaim.setModerNo(adminNo);
			// 클레임 마스터 기존 총환불금 += 결제취소한 배송비 금액 set
			ocClaim.setTotalRfndAmt(ocClaim.getTotalRfndAmt() + redempDlvyAmt);
			// 클레임 마스터 추가배송비결제금액 = 기존 추가 배송비 - 결제취소한 배송비 금액 set
			ocClaim.setAddDlvyAmt(ocClaim.getAddDlvyAmt() - redempDlvyAmt);
			// 클레임 마스터 추가배송비결제타입 = 'S' 그대로 set
			ocClaim.setAddDlvyAmtPymntType(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_REFUND_DEDUCTION);

			ocClaimDao.updateReturnClaimAmtInfo(ocClaim);
		} catch (Exception e) {
			log.error("배송비취소로 인한 클레임 업데이트 실패 - claimNo : {}", clmNo);
			log.error("배송비취소로 인한 클레임 업데이트 실패 - " + e);
			e.printStackTrace();
		}
	}

	/**
	 * @Desc : 반품배송비 결제취소 전 로직
	 * @Method Name : setBeforeCancelReturnDlvyPayment
	 * @Date : 2019. 2. 27.
	 * @Author : 이강수
	 * @param ocClaim
	 * @param claimPayment
	 * @throws Exception
	 */
	public void setBeforeCancelReturnDlvyPayment(OcOrder ocOrder, OcClaim ocClaim, OcClaimPayment redempDlvyAmtPymnt,
			OcClaimAmountVO ocClaimAmountVO) throws Exception {

		String adminNo = LoginManager.getUserDetails().getAdminNo(); // 수정지
		int redempDlvyAmt = redempDlvyAmtPymnt.getPymntAmt(); // 반품배송비

		/*
		 * 1. 원주문 취소금액 업데이트
		 */
		OcOrder updateOrder = new OcOrder();
		updateOrder.setOrderNo(ocClaim.getOrgOrderNo());

		// 전체취소가 아닌 경우 환불(취소)금액 set / 기존 주문의 취소금액 + 취소한 배송비
		updateOrder.setCnclAmt(redempDlvyAmt);
		updateOrder.setModerNo(adminNo);

		// 원주문 취소금액 업데이트
		ocOrderDao.updateOrderForClaim(updateOrder);

		/*
		 * 2. 배송비취소 리오더 매출 등록
		 */
		OcOrder reOrder = new OcOrder();
		BeanUtils.copyProperties(ocOrder, reOrder); // 내용 복사

		String reOrderNo = orderService.createOrderSeq(); // 신규 주문번호

		ocClaim.setReOrderNo(reOrderNo); // 클레임 정보에도 신규주문번호 set

		reOrder.setOrderNo(reOrderNo); // 신규주문번호
		reOrder.setClmNo(ocClaim.getClmNo()); // 클레임번호 set
		reOrder.setSalesCnclGbnType(CommonCode.SALES_CNCL_GBN_TYPE_REDEMP_DLVY_AMT_CANCEL); // 매출취소구분 : 환수 배송비 결제취소
		reOrder.setOrderStatCode(CommonCode.ORDER_STAT_CODE_CANCEL_COMPLETE); // 주문상태코드 : 취소완료
		reOrder.setCnclAmt(0);
		reOrder.setTotalSellAmt(redempDlvyAmt); // 취소 배송비
		reOrder.setTotalNormalAmt(redempDlvyAmt); // 취소 배송비
		reOrder.setPymntTodoAmt(redempDlvyAmt); // 취소 배송비
		reOrder.setPymntAmt(redempDlvyAmt); // 취소 배송비
		reOrder.setRgsterNo(adminNo);
		reOrder.setModerNo(adminNo);

		// 리오더 주문 생성
		int reOrderResult = ocOrderDao.insertOrder(reOrder);

		/*
		 * 3. 주문상품(매출취소 리오더) 생성
		 */
		if (reOrderResult > 0) {
			// 리오더 상품 중 '배송비' '결제완료', '매출타입 클레임 배송비' 중 하나 뽑기
			OcOrderProduct reOrderProduct = ocClaimAmountVO.getReOrderProductList().stream()
					.filter(x -> UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
					.filter(x -> UtilsText.equals(x.getOrderPrdtStatCode(), CommonCode.ORDER_PRDT_STAT_CODE_COMPLETE))
					.filter(x -> UtilsText.equals(x.getSalesCnclGbnType(),
							CommonCode.SALES_CNCL_GBN_TYPE_CLAIM_DLVY_AMT))
					.findFirst().orElse(null);

			reOrderProduct.setPrdtNormalAmt(redempDlvyAmt);
			reOrderProduct.setPrdtSellAmt(redempDlvyAmt);
			reOrderProduct.setSellAmt(redempDlvyAmt);
			reOrderProduct.setOrderAmt(redempDlvyAmt);

			reOrderProduct.setOrderNo(reOrderNo); // 신규 생성 주문번호
			reOrderProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE); // 주문상품상태코드:취소완료
			reOrderProduct.setRgsterNo(adminNo);
			reOrderProduct.setModerNo(adminNo);
			reOrderProduct.setExcclcDcsnYmd(new Timestamp(new Date().getTime()));

			// 리오더 주문상품 생성
			ocOrderProductDao.insertOrderProduct(reOrderProduct);
		}
	}
}
