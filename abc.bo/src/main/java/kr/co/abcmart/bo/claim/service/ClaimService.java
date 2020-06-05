
package kr.co.abcmart.bo.claim.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.abcmart.bo.afterService.model.master.OcAsAccept;
import kr.co.abcmart.bo.afterService.model.master.OcAsAcceptProduct;
import kr.co.abcmart.bo.afterService.repository.master.OcAsAcceptDao;
import kr.co.abcmart.bo.afterService.repository.master.OcAsAcceptProductDao;
import kr.co.abcmart.bo.afterService.service.AfterServiceService;
import kr.co.abcmart.bo.claim.model.master.IfBankda;
import kr.co.abcmart.bo.claim.model.master.OcClaim;
import kr.co.abcmart.bo.claim.model.master.OcClaimCertificationHistory;
import kr.co.abcmart.bo.claim.model.master.OcClaimMemo;
import kr.co.abcmart.bo.claim.model.master.OcClaimPayment;
import kr.co.abcmart.bo.claim.model.master.OcClaimProduct;
import kr.co.abcmart.bo.claim.model.master.OcClaimProductApplyPromotion;
import kr.co.abcmart.bo.claim.model.master.OcClaimStatusHistory;
import kr.co.abcmart.bo.claim.repository.master.IfBankdaDao;
import kr.co.abcmart.bo.claim.repository.master.OcClaimCertificationHistoryDao;
import kr.co.abcmart.bo.claim.repository.master.OcClaimDao;
import kr.co.abcmart.bo.claim.repository.master.OcClaimMemoDao;
import kr.co.abcmart.bo.claim.repository.master.OcClaimPaymentDao;
import kr.co.abcmart.bo.claim.repository.master.OcClaimProductApplyPromotionDao;
import kr.co.abcmart.bo.claim.repository.master.OcClaimProductDao;
import kr.co.abcmart.bo.claim.repository.master.OcClaimStatusHistoryDao;
import kr.co.abcmart.bo.claim.vo.OcClaimAmountVO;
import kr.co.abcmart.bo.claim.vo.OcClaimCountVO;
import kr.co.abcmart.bo.claim.vo.OcClaimDiscountVO;
import kr.co.abcmart.bo.claim.vo.OcClaimPaymentExcelVo;
import kr.co.abcmart.bo.claim.vo.OcClaimProductExcelVo;
import kr.co.abcmart.bo.delivery.service.DeliveryService;
import kr.co.abcmart.bo.member.model.master.MbEmployeeCertificationHistory;
import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.model.master.MbMemberCoupon;
import kr.co.abcmart.bo.member.repository.master.MbEmployeeCertificationHistoryDao;
import kr.co.abcmart.bo.member.repository.master.MbMemberCouponDao;
import kr.co.abcmart.bo.member.repository.master.MbMemberDao;
import kr.co.abcmart.bo.member.service.MemberService;
import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.bo.order.model.master.OcOrderDeliveryHistory;
import kr.co.abcmart.bo.order.model.master.OcOrderPayment;
import kr.co.abcmart.bo.order.model.master.OcOrderProduct;
import kr.co.abcmart.bo.order.model.master.OcOrderProductApplyPromotion;
import kr.co.abcmart.bo.order.model.master.OcOrderProductHistory;
import kr.co.abcmart.bo.order.model.master.OcOrderUseCoupon;
import kr.co.abcmart.bo.order.repository.master.OcOrderDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderDeliveryHistoryDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderPaymentDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderProductApplyPromotionDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderProductDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderProductHistoryDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderUseCouponDao;
import kr.co.abcmart.bo.order.service.OrderService;
import kr.co.abcmart.bo.order.vo.OcOrderDiscountVO;
import kr.co.abcmart.bo.product.service.ProductInsideOptionService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.repository.master.SySiteDao;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.account.AccountAuth;
import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsArray;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsRequest;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.interfaces.module.member.MembershipPointService;
import kr.co.abcmart.interfaces.module.member.model.EmployPoint;
import kr.co.abcmart.interfaces.module.payment.PaymentEntrance;
import kr.co.abcmart.interfaces.module.payment.base.PaymentResult;
import kr.co.abcmart.interfaces.module.payment.base.model.PaymentInfo;
import kr.co.abcmart.interfaces.module.payment.kcp.model.KcpPaymentApproval;
import kr.co.abcmart.interfaces.module.payment.kcp.model.KcpPaymentApprovalReturn;
import kr.co.abcmart.interfaces.module.payment.kcp.model.KcpPaymentCancel;
import kr.co.abcmart.interfaces.module.payment.kcp.model.KcpPaymentCancelReturn;
import kr.co.abcmart.interfaces.zinterfacesdb.service.InterfacesClaimService;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClaimService extends BaseController {

	@Autowired
	private ClaimMessageService claimMessageService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private ClaimProcService claimProcService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private AfterServiceService afterServiceService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OcClaimDao ocClaimDao;

	@Autowired
	private OcClaimMemoDao ocClaimMemoDao;

	@Autowired
	private OcClaimPaymentDao ocClaimPaymentDao;

	@Autowired
	private OcClaimProductDao ocClaimProductDao;

	@Autowired
	private OcOrderPaymentDao ocOrderPaymentDao;

	@Autowired
	private OcOrderProductDao ocOrderProductDao;

	@Autowired
	private OcOrderDao ocOrderDao;

	@Autowired
	private OcOrderDeliveryHistoryDao ocOrderDeliveryHistoryDao;

	@Autowired
	private OcClaimStatusHistoryDao ocClaimStatusHistoryDao;

	@Autowired
	private OcOrderProductHistoryDao ocOrderProductHistoryDao;

	@Autowired
	private OcOrderUseCouponDao ocOrderUseCouponDao;

	@Autowired
	private OcOrderProductApplyPromotionDao ocOrderProductApplyPromotionDao;

	@Autowired
	private OcAsAcceptDao ocAsAcceptDao;

	@Autowired
	private OcAsAcceptProductDao ocAsAcceptProductDao;

	@Autowired
	private MbMemberCouponDao mbMemberCouponDao;

	@Autowired
	private IfBankdaDao ifBankdaDao;

	@Autowired
	private PaymentEntrance paymentEntrance;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MbMemberDao memberDao;

	@Autowired
	MembershipPointService membershipPointService;

	@Autowired
	ProductInsideOptionService productInsideOptionService;

	@Autowired
	OcClaimCertificationHistoryDao ocClaimCertificationHistoryDao;

	@Autowired
	OcClaimProductApplyPromotionDao ocClaimProductApplyPromotionDao;

	@Autowired
	SySiteDao sySiteDao;

	@Autowired
	MbEmployeeCertificationHistoryDao mbEmployeeCertificationHistoryDao;

	@Autowired
	InterfacesClaimService interfacesClaimService;

	@Autowired
	DeliveryService deliveryService;

	/**************************** S : 이강수 *****************************/

	/**
	 * @Desc : 공콩코드 조회 & 사이트 조회
	 * @Method Name : getSiteCommonCodeData
	 * @Date : 2019. 3. 8.
	 * @Author : 이강수
	 * @param
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> getSiteCommonCodeData() throws Exception {

		Map<String, Object> retMap = new HashMap<>();

		// 사이트
		retMap.put("SITE_TYPE", siteService.getSiteList());
		// 클레임 상태코드
		retMap.put(CommonCode.CLM_STAT_CODE, commonCodeService.getCodeNoName(CommonCode.CLM_STAT_CODE));
		// 클레임 구분 코드
		retMap.put(CommonCode.CLM_GBN_CODE, commonCodeService.getCodeNoName(CommonCode.CLM_GBN_CODE));
		// 클레임 사유 코드
		retMap.put(CommonCode.CLM_RSN_CODE, commonCodeService.getCodeNoName(CommonCode.CLM_RSN_CODE));
		// 디바이스 코드
		retMap.put(CommonCode.DEVICE_CODE, commonCodeService.getCodeNoName(CommonCode.DEVICE_CODE));
		// 물류 구분 코드
		retMap.put(CommonCode.STOCK_GBN_CODE, commonCodeService.getCodeNoName(CommonCode.STOCK_GBN_CODE));
		// 결제 수단 코드
		retMap.put(CommonCode.PYMNT_MEANS_CODE, commonCodeService.getCodeNoName(CommonCode.PYMNT_MEANS_CODE));
		// 결제 상태 코드
		retMap.put(CommonCode.PYMNT_STAT_CODE, commonCodeService.getCodeNoName(CommonCode.PYMNT_STAT_CODE));

		return retMap;
	}

	/**
	 * @Desc : 클레임 목록 조회
	 * @Method Name : getClaimList
	 * @Date : 2019. 2. 20.
	 * @Author : 이강수
	 * @param Pageable<OcClaim, OcClaim>
	 * @return Page<OcClaim>
	 * @throws Exception
	 */
	public Page<OcClaim> getClaimList(Pageable<OcClaim, OcClaim> pageable) throws Exception {

		// 사은품, 배송비 제외 한 클레임상품의 갯수 파악을 위한 set
		pageable.getBean().setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT);
		pageable.getBean().setPrdtTypeCodeDelivery(CommonCode.PRDT_TYPE_CODE_DELIVERY);

		// 비회원은 이름을 주문의 구매자명으로 가져오기위한 set
		pageable.getBean().setNonMemberNo(Const.NON_MEMBER_NO);

		// 최종적으로 결제완료된 결제수단을 검색조건으로 갖기위한 set
		pageable.getBean().setPymntStatCodePaymentFinish(CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH);

		int count = ocClaimDao.selectClaimListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {

			List<OcClaim> ocClaimList = ocClaimDao.selectClaimList(pageable);

			for (OcClaim ocClaim : ocClaimList) {
				// 목록이기에 권한과 상관없이 마스킹 처리 Y
				ocClaim.setIsListYn(Const.BOOLEAN_TRUE);
			}

			pageable.setContent(ocClaimList);
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 환불/환수 목록 조회
	 * @Method Name : getRefundRedempList
	 * @Date : 2019. 2. 20.
	 * @Author : 이강수
	 * @param Pageable<OcClaimPayment, OcClaimPayment>
	 * @return Page<OcClaimPayment>
	 * @throws Exception
	 */
	public Page<OcClaimPayment> getRefundRedempList(Pageable<OcClaimPayment, OcClaimPayment> pageable)
			throws Exception {

		// 일반결제 P / 재경처리 Y 인 클레임결제 목록을 불러온다.
		pageable.getBean().setHistGbnType(CommonCode.HIST_GBN_TYPE_PUBLIC);
		// 상품유형코드 : 사은품
		pageable.getBean().setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT);
		// 비회원은 이름을 주문의 구매자명으로 가져오기위한 set
		pageable.getBean().setNonMemberNo(Const.NON_MEMBER_NO);

		// 환불금액과 환수금액의 환불,환수 상태값를 따져 검색하기위한 set
		pageable.getBean().setPymntStatCodeRefundAccept(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT);
		pageable.getBean().setPymntStatCodeRefundComplete(CommonCode.PYMNT_STAT_CODE_REFUND_COMPLETE);
		pageable.getBean().setPymntStatCodeRedempAccept(CommonCode.PYMNT_STAT_CODE_REDEMP_ACCEPT);
		pageable.getBean().setPymntStatCodeRedempComplete(CommonCode.PYMNT_STAT_CODE_REDEMP_COMPLETE);

		int count = ocClaimPaymentDao.selectRefundRedempListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {

			List<OcClaimPayment> list = ocClaimPaymentDao.selectRefundRedempList(pageable);

			for (OcClaimPayment ocClaimPayment : list) {
				// 목록이기에 권한과 상관없이 마스킹 처리 Y
				ocClaimPayment.setIsListYn(Const.BOOLEAN_TRUE);
			}

			pageable.setContent(list);
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 환불 처리상태 업데이트 - 처리완료
	 * @Method Name : updateProcessComplete
	 * @Date : 2019. 4. 5.
	 * @Author : KTH, 이강수
	 * @param paramsArr
	 * @throws Exception
	 */
	public void updateProcessComplete(OcClaimPayment[] paramsArr) throws Exception {

		for (OcClaimPayment params : paramsArr) {

			/**
			 * 클레임 결제 업데이트
			 */
			// 처리자 번호 등록
			params.setModerNo(LoginManager.getUserDetails().getAdminNo());
			// 환불완료(입금 완료) 코드 등록
			params.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_COMPLETE);
			// 처리불가여부 N
			params.setProcImpsbltYn(Const.BOOLEAN_FALSE);

			ocClaimPaymentDao.updateRefundProcessComplete(params);

			params.setIsRefundProcCmplt(Const.BOOLEAN_TRUE);
			// 이력구분타입 P
			params.setHistGbnType(CommonCode.HIST_GBN_TYPE_PUBLIC);
			// 자사재경처리 N
			params.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE);
			ocClaimPaymentDao.updateRefundProcessComplete(params);

			/**
			 * 클레임 마스터 업데이트
			 */
			OcClaim ocClaim = new OcClaim();

			ocClaim.setClmNo(params.getClmNo());
			ocClaim.setModerNo(LoginManager.getUserDetails().getAdminNo());

			// 클레임 구분에 따라 업데이트 해야하는 완료코드가 다르다.
			if (UtilsText.equals(params.getClmGbnCode(), CommonCode.CLM_GBN_CODE_CANCEL)) {
				// 클레임상태코드 조건 : 취소 환불접수
				ocClaim.setClmStatCodeCondition(CommonCode.CLM_STAT_CODE_CANCEL_REFUND_ACCEPT);
				// 취소 완료
				ocClaim.setClmStatCode(CommonCode.CLM_STAT_CODE_CANCEL_FINISH);
			} else if (UtilsText.equals(params.getClmGbnCode(), CommonCode.CLM_GBN_CODE_EXCHANGE)) {
				// 클레임상태코드 조건 : 교환 환불접수
				ocClaim.setClmStatCodeCondition(CommonCode.CLM_STAT_CODE_EXCHANGE_REFUND_ACCEPT);
				// 교환 완료
				ocClaim.setClmStatCode(CommonCode.CLM_STAT_CODE_EXCHANGE_FINISH);
			} else if (UtilsText.equals(params.getClmGbnCode(), CommonCode.CLM_GBN_CODE_RETURN)) {
				// 클레임상태코드 조건 : 반품 환불접수
				ocClaim.setClmStatCodeCondition(CommonCode.CLM_STAT_CODE_RETURN_REFUND_ACCEPT);
				// 반품 완료
				ocClaim.setClmStatCode(CommonCode.CLM_STAT_CODE_RETURN_FINISH);
			}

			ocClaimDao.updateClaimStatCode(ocClaim);

			/**
			 * 클레임 상품 업데이트
			 */
			OcClaimProduct ocClaimProduct = new OcClaimProduct();
			ocClaimProduct.setClmNo(params.getClmNo());
			ocClaimProduct.setPrdtRltnFileSeq(1);
			ocClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT);

			// 클레임번호에 물려있는 상품목록 조회
			List<OcClaimProduct> ocClaimProductList = ocClaimProductDao.selectClaimProductList(ocClaimProduct);

			if (!UtilsArray.isEmpty(ocClaimProductList)) {
				for (OcClaimProduct ocp : ocClaimProductList) {

					ocp.setModerNo(LoginManager.getUserDetails().getAdminNo());

					// 클레임상품중 상태코드가 각각 환불접수인 경우만 완료처리를 한다.
					if (UtilsText.equals(params.getClmGbnCode(), CommonCode.CLM_GBN_CODE_CANCEL)) {
						// 클레임 상품 상태코드 조건 : 취소 환불접수
						ocp.setClmPrdtStatCodeCondition(CommonCode.CLM_PRDT_STAT_CODE_CANCEL_REFUND_ACCEPT);
						// 취소 완료
						ocp.setClmPrdtStatCode(CommonCode.CLM_PRDT_STAT_CODE_CANCEL_FINISH);

					} else if (UtilsText.equals(params.getClmGbnCode(), CommonCode.CLM_GBN_CODE_EXCHANGE)) {
						// 클레임 상품 상태코드 조건 : 교환 환불접수
						ocp.setClmPrdtStatCodeCondition(CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_REFUND_ACCEPT);
						// 교환 완료
						ocp.setClmPrdtStatCode(CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_FINISH);

					} else if (UtilsText.equals(params.getClmGbnCode(), CommonCode.CLM_GBN_CODE_RETURN)) {
						// 클레임 상품 상태코드 조건 : 반품 환불접수
						ocp.setClmPrdtStatCodeCondition(CommonCode.CLM_PRDT_STAT_CODE_RETURN_REFUND_ACCEPT);
						// 반품 완료
						ocp.setClmPrdtStatCode(CommonCode.CLM_PRDT_STAT_CODE_RETURN_FINISH);
					}

					/** 클레임 상품 업데이트 */
					int updateCnt = ocClaimProductDao.updateClaimProductStatCode(ocp);

					// 상품 상태가 업데이트 된 경우에만 상품이력을 추가한다.
					if (updateCnt > 0) {
						OcClaimStatusHistory ocClaimStatusHistory = new OcClaimStatusHistory();
						ocClaimStatusHistory.setClmNo(ocp.getClmNo()); // 클레임번호
						ocClaimStatusHistory.setClmPrdtSeq(ocp.getClmPrdtSeq()); // 클레임 상품 순번
						ocClaimStatusHistory.setClmPrdtStatCode(ocp.getClmPrdtStatCode()); // 클레임상품상태코드
						ocClaimStatusHistory.setStockGbnCode(null);
						ocClaimStatusHistory.setNoteText("입금완료(환불완료)");
						ocClaimStatusHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자

						/** 클레임 상품 상태 이력 추가 */
						ocClaimStatusHistoryDao.insertClaimStatusHistoryByClaimProduct(ocClaimStatusHistory);
					}
				}
			}
		}
	}

	/**
	 * @Desc : 환불/환수 처리상태 업데이트 - 처리불가
	 * @Method Name : updateProcessImpossible
	 * @Date : 2019. 4. 5.
	 * @Author : KTH
	 * @param paramsArr
	 * @throws Exception
	 */
	public void updateProcessImpossible(OcClaimPayment[] paramsArr) throws Exception {

		for (OcClaimPayment params : paramsArr) {
			// 처리자 번호 등록
			params.setModerNo(LoginManager.getUserDetails().getAdminNo());

			ocClaimPaymentDao.updateRefundRedempProcess(params);
			
			/**
			 * 클레임 마스터 업데이트 // 2020.05.28 : 처리완료와 같이 클레임 완료처리
			 */
			OcClaim ocClaim = new OcClaim();

			ocClaim.setClmNo(params.getClmNo());
			ocClaim.setModerNo(LoginManager.getUserDetails().getAdminNo());

			// 클레임 구분에 따라 업데이트 해야하는 완료코드가 다르다.
			if (UtilsText.equals(params.getClmGbnCode(), CommonCode.CLM_GBN_CODE_CANCEL)) {
				// 클레임상태코드 조건 : 취소 환불접수
				ocClaim.setClmStatCodeCondition(CommonCode.CLM_STAT_CODE_CANCEL_REFUND_ACCEPT);
				// 취소 완료
				ocClaim.setClmStatCode(CommonCode.CLM_STAT_CODE_CANCEL_FINISH);
			} else if (UtilsText.equals(params.getClmGbnCode(), CommonCode.CLM_GBN_CODE_EXCHANGE)) {
				// 클레임상태코드 조건 : 교환 환불접수
				ocClaim.setClmStatCodeCondition(CommonCode.CLM_STAT_CODE_EXCHANGE_REFUND_ACCEPT);
				// 교환 완료
				ocClaim.setClmStatCode(CommonCode.CLM_STAT_CODE_EXCHANGE_FINISH);
			} else if (UtilsText.equals(params.getClmGbnCode(), CommonCode.CLM_GBN_CODE_RETURN)) {
				// 클레임상태코드 조건 : 반품 환불접수
				ocClaim.setClmStatCodeCondition(CommonCode.CLM_STAT_CODE_RETURN_REFUND_ACCEPT);
				// 반품 완료
				ocClaim.setClmStatCode(CommonCode.CLM_STAT_CODE_RETURN_FINISH);
			}

			ocClaimDao.updateClaimStatCode(ocClaim);

			/**
			 * 클레임 상품 업데이트 // 2020.05.28 : 처리완료와 같이 클레임 완료처리
			 */
			OcClaimProduct ocClaimProduct = new OcClaimProduct();
			ocClaimProduct.setClmNo(params.getClmNo());
			ocClaimProduct.setPrdtRltnFileSeq(1);
			ocClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT);

			// 클레임번호에 물려있는 상품목록 조회
			List<OcClaimProduct> ocClaimProductList = ocClaimProductDao.selectClaimProductList(ocClaimProduct);

			if (!UtilsArray.isEmpty(ocClaimProductList)) {
				for (OcClaimProduct ocp : ocClaimProductList) {

					ocp.setModerNo(LoginManager.getUserDetails().getAdminNo());

					// 클레임상품중 상태코드가 각각 환불접수인 경우만 완료처리를 한다.
					if (UtilsText.equals(params.getClmGbnCode(), CommonCode.CLM_GBN_CODE_CANCEL)) {
						// 클레임 상품 상태코드 조건 : 취소 환불접수
						ocp.setClmPrdtStatCodeCondition(CommonCode.CLM_PRDT_STAT_CODE_CANCEL_REFUND_ACCEPT);
						// 취소 완료
						ocp.setClmPrdtStatCode(CommonCode.CLM_PRDT_STAT_CODE_CANCEL_FINISH);

					} else if (UtilsText.equals(params.getClmGbnCode(), CommonCode.CLM_GBN_CODE_EXCHANGE)) {
						// 클레임 상품 상태코드 조건 : 교환 환불접수
						ocp.setClmPrdtStatCodeCondition(CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_REFUND_ACCEPT);
						// 교환 완료
						ocp.setClmPrdtStatCode(CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_FINISH);

					} else if (UtilsText.equals(params.getClmGbnCode(), CommonCode.CLM_GBN_CODE_RETURN)) {
						// 클레임 상품 상태코드 조건 : 반품 환불접수
						ocp.setClmPrdtStatCodeCondition(CommonCode.CLM_PRDT_STAT_CODE_RETURN_REFUND_ACCEPT);
						// 반품 완료
						ocp.setClmPrdtStatCode(CommonCode.CLM_PRDT_STAT_CODE_RETURN_FINISH);
					}

					/** 클레임 상품 업데이트 */
					int updateCnt = ocClaimProductDao.updateClaimProductStatCode(ocp);

					// 상품 상태가 업데이트 된 경우에만 상품이력을 추가한다.
					if (updateCnt > 0) {
						OcClaimStatusHistory ocClaimStatusHistory = new OcClaimStatusHistory();
						ocClaimStatusHistory.setClmNo(ocp.getClmNo()); // 클레임번호
						ocClaimStatusHistory.setClmPrdtSeq(ocp.getClmPrdtSeq()); // 클레임 상품 순번
						ocClaimStatusHistory.setClmPrdtStatCode(ocp.getClmPrdtStatCode()); // 클레임상품상태코드
						ocClaimStatusHistory.setStockGbnCode(null);
						ocClaimStatusHistory.setNoteText("재경 처리불가로 인한 클레임완료처리"); // 2020.05.28 : 처리완료와 다른점
						ocClaimStatusHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자

						/** 클레임 상품 상태 이력 추가 */
						ocClaimStatusHistoryDao.insertClaimStatusHistoryByClaimProduct(ocClaimStatusHistory);
					}
				}
			}
		}
	}

	/**
	 * @Desc : AS 내역 목록 조회 - 주문상세 팝업 클레임 탭
	 * @Method Name : getAsAcceptListInOrderDetailTap
	 * @Date : 2019. 3. 4.
	 * @Author : 이강수
	 * @param OcAsAccept
	 * @return List<OcAsAccept>
	 * @throws Exception
	 */
	public List<OcAsAccept> getAsAcceptListInOrderDetailTap(OcAsAccept ocAsAccept) throws Exception {

		return afterServiceService.getAsAcceptListInOrderDetailTap(ocAsAccept);
	}

	/**
	 * @Desc : 클레임 상품 목록 엑셀 다운로드
	 * @Method Name : getOcClaimProductForAllExcelList
	 * @Date : 2019. 3. 6.
	 * @Author : 이강수
	 * @param OcClaimProduct
	 * @return List<OcClaimProductExcelVo>
	 * @throws Exception
	 */
	public List<OcClaimProductExcelVo> getOcClaimProductForAllExcelList(OcClaimProduct ocClaimProduct)
			throws Exception {

		// 결제상태코드 : 결제완료
		ocClaimProduct.setPymntStatCodePaymentFinish(CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH);

		return ocClaimProductDao.selectOcClaimProductForAllExcelList(ocClaimProduct);
	}

	/**
	 * @Desc : 클레임 상품 목록 선택엑셀 다운로드
	 * @Method Name : getOcClaimProductForExcelList
	 * @Date : 2019. 3. 7.
	 * @Author : 이강수
	 * @param OcClaimProduct
	 * @return List<OcClaimProductExcelVo>
	 * @throws Exception
	 */
	public List<OcClaimProductExcelVo> getOcClaimProductForExcelList(OcClaimProductExcelVo ocClaimProductExcelVo)
			throws Exception {

		return ocClaimProductDao.selectOcClaimProductForExcelList(ocClaimProductExcelVo);
	}

	/**
	 * @Desc : 환불/환수 전체 목록 엑셀 조회
	 * @Method Name : getRefundRedempListForAllExcel
	 * @Date : 2019. 3. 7.
	 * @Author : 이강수
	 * @param OcOrderPayment
	 * @return List<OcClaimPayment>
	 * @throws Exception
	 */
	public List<OcClaimPaymentExcelVo> getRefundRedempListForAllExcel(OcClaimPayment params) throws Exception {

		// 환불금액과 환수금액의 환불,환수 상태값를 따져 검색하기위한 set
		params.setPymntStatCodeRefundAccept(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT);
		params.setPymntStatCodeRefundComplete(CommonCode.PYMNT_STAT_CODE_REFUND_COMPLETE);
		params.setPymntStatCodeRedempAccept(CommonCode.PYMNT_STAT_CODE_REDEMP_ACCEPT);
		params.setPymntStatCodeRedempComplete(CommonCode.PYMNT_STAT_CODE_REDEMP_COMPLETE);

		return ocClaimPaymentDao.selectRefundRedempListForAllExcel(params);
	}

	/**
	 * @Desc : 환불/환수 선택 목록 엑셀 조회
	 * @Method Name : getRefundRedempListForExcel
	 * @Date : 2019. 3. 7.
	 * @Author : 이강수
	 * @param OcOrderPayment
	 * @return List<OcClaimPayment>
	 * @throws Exception
	 */
	public List<OcClaimPaymentExcelVo> getRefundRedempListForExcel(OcClaimPaymentExcelVo params) throws Exception {
		return ocClaimPaymentDao.selectRefundRedempListForExcel(params);
	}

	/**
	 * @Desc : 클레임불가 처리 -- 사용X
	 * @Method Name : setClaimImpossibility
	 * @Date : 2019. 4. 5.
	 * @Author : KTH, 이강수
	 * @param ocClaimProduct
	 * @param ocOrderDeliveryHistory
	 * @throws Exception
	 */
	public void setClaimImpossibility(OcClaimProduct ocClaimProduct, OcOrderDeliveryHistory ocOrderDeliveryHistory)
			throws Exception {

		// 클레임 마스터 조회
		OcClaim ocClaim = new OcClaim();
		ocClaim.setClmNo(ocClaimProduct.getClmNo());
		ocClaim = ocClaimDao.selectByPrimaryKey(ocClaim);

		/*
		 * [주문배송이력 불가처리에 의한 반송정보 생성]
		 */
		// ocOrderDeliveryHistory.setOrderNo(null); // 폼 값 사용
		// ocOrderDeliveryHistory.setOrderPrdtSeq(null); // 폼 값 사용
		ocOrderDeliveryHistory.setStockGbnCode(CommonCode.STOCK_GBN_CODE_AI); // 재고구분코드
		ocOrderDeliveryHistory.setStoreNo(null); // 매장번호
		ocOrderDeliveryHistory.setStoreChngDgreCount((short) 0); // 매장변경차수
		ocOrderDeliveryHistory.setDlvyProcDtm(null); // 배송처리일시
		// ocOrderDeliveryHistory.setLogisVndrCode(null); // 택배사코드 // 폼 값 사용
		// ocOrderDeliveryHistory.setWaybilNoText(null); // 운송장번호 // 폼 값 사용
		ocOrderDeliveryHistory.setDlvyDscntcYn(Const.BOOLEAN_FALSE); // 배송중단여부
		ocOrderDeliveryHistory.setDlvyDscntcRsnCode(null); // 배송중단사유코드
		ocOrderDeliveryHistory.setDlvyDscntcOpetrNo(null); // 배송중단처리자번호
		ocOrderDeliveryHistory.setDlvyDscntcAcceptDtm(null); // 배송중단접수일시
		ocOrderDeliveryHistory.setDlvyDscntcProcDtm(null); // 배송중단처리일시
		ocOrderDeliveryHistory.setPickupPrpareCmlptDtm(null); // 픽업준비완료일시
		ocOrderDeliveryHistory.setPickupPsbltYmd(null); // 픽업가능일
		ocOrderDeliveryHistory.setMissProcYn(Const.BOOLEAN_FALSE); // 분실처리여부
		ocOrderDeliveryHistory.setMissProcTypeCode(null); // 분실처리유형코드
		ocOrderDeliveryHistory.setInsdMgmtInfoText(null); // 내부관리정보
		ocOrderDeliveryHistory.setWmsSendYn(Const.BOOLEAN_FALSE); // WMS전송여부
		// ocOrderDeliveryHistory.setDlvyTodoYmd(null); // 배송예정일 - 폼 값 사용
		ocOrderDeliveryHistory.setDlvyCmlptDtm(null); // 배송완료일시
		// ocOrderDeliveryHistory.setRcvrName(null); // 수취인명 - 폼 값 사용
		ocOrderDeliveryHistory.setRcvrTelNoText(ocOrderDeliveryHistory.getRcvrHdphnNoText()); // 수취인전화번호
		// ocOrderDeliveryHistory.setRcvrHdphnNoText(null); // 수취인핸드폰번호 - 폼 값 사용
		// ocOrderDeliveryHistory.setRcvrPostCodeText(null); // 수취인우편번호 - 폼 값 사용
		// ocOrderDeliveryHistory.setRcvrPostAddrText(null); // 수취인우편주소 - 폼 값 사용
		// ocOrderDeliveryHistory.setRcvrDtlAddrText(null); // 수취인상세주소 - 폼 값 사용
		// ocOrderDeliveryHistory.setClmNo(null); // 클레임번호 - 폼 값 사용
		// ocOrderDeliveryHistory.setClmPrdtSeq(null); // 클레임번호 - 폼 값 사용
		// ocOrderDeliveryHistory.setDlvyMemoText(null); // 배송메모 - 폼 값 사용
		ocOrderDeliveryHistory.setDlvyStatCode(CommonCode.DLVY_STAT_CODE_PAYMENT_FINISH); // 배송상태코드 - 결제완료
		ocOrderDeliveryHistory.setLogisCnvrtSendDtm(null); // 택배전환발송일시
		ocOrderDeliveryHistory.setRsvDlvyDtm(null); // 예약출고일시
		// 배송ID는 쿼리에서 생성
		ocOrderDeliveryHistory.setLogisPymntPrdtAmt(0); // 택배사결제상품금액
		ocOrderDeliveryHistory.setLogisPymntDlvyAmt(0); // 택배사결제배송비
		ocOrderDeliveryHistory.setImpsbltProcYn(Const.BOOLEAN_TRUE); // 불가처리여부
		ocOrderDeliveryHistory.setImpsbltProcAcceptDtm(new Timestamp(new Date().getTime())); // 불가처리접수일시
		ocOrderDeliveryHistory.setImpsbltProcCmlptDtm(new Timestamp(new Date().getTime())); // 불가처리완료일시
		ocOrderDeliveryHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자
		ocOrderDeliveryHistory.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자

		ocOrderDeliveryHistoryDao.insertDelivery(ocOrderDeliveryHistory); // 주문배송이력 생성

		/*
		 * [클레임 상품 불가 처리에 따른 업데이트] [클레임상태이력 등록]
		 */
		ocClaimProduct.setModerNo(LoginManager.getUserDetails().getAdminNo());
		if (UtilsText.equals(CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE, ocClaim.getClmGbnCode())) {
			// 클레임상품 상태 코드 - 교환불가
			ocClaimProduct.setClmPrdtStatCode(CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE);
		} else {
			// 클레임상품 상태 코드 - 반품불가
			ocClaimProduct.setClmPrdtStatCode(CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE);
		}
		ocClaimProductDao.updateClaimProductImpossible(ocClaimProduct);

		OcClaimStatusHistory ocClaimStatusHistory = new OcClaimStatusHistory();
		ocClaimStatusHistory.setClmNo(ocClaimProduct.getClmNo());
		ocClaimStatusHistory.setClmPrdtSeq(ocClaimProduct.getClmPrdtSeq());
		if (UtilsText.equals(CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE, ocClaim.getClmGbnCode())) {
			// 클레임상품 상태 코드 - 교환불가
			ocClaimStatusHistory.setClmPrdtStatCode(CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE);
		} else {
			// 클레임상품 상태 코드 - 반품불가
			ocClaimStatusHistory.setClmPrdtStatCode(CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE);
		}
		ocClaimStatusHistory.setStockGbnCode(null);
		ocClaimStatusHistory.setNoteText(null);
		ocClaimStatusHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자

		ocClaimStatusHistoryDao.insertClaimStatusHistory(ocClaimStatusHistory); // 클레임상태이력 등록

	}

	/**
	 * @Desc : 클레임 불가처리 상품정보, 반송지 상세 조회 - 사용 X
	 * @Method Name : setClaimImpossibility
	 * @Date : 2019. 3. 13.
	 * @Author : 이강수
	 * @param OcClaimProduct, OcOrderDeliveryHistory
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> getClaimImpossibilityDetail(OcClaimProduct ocClaimProduct) throws Exception {
		Map<String, Object> retMap = new HashMap<>();

		retMap.put(CommonCode.CLM_IMPSBLT_RSN_CODE, commonCodeService.getCodeNoName(CommonCode.CLM_IMPSBLT_RSN_CODE)); // 클레임불가사유코드
		retMap.put(CommonCode.LOGIS_VNDR_CODE, commonCodeService.getCodeNoName(CommonCode.LOGIS_VNDR_CODE)); // 택배사코드

		// 클레임 상품 상세 조회
		ocClaimProduct.setPrdtRltnFileSeq(1);
		ocClaimProduct = ocClaimProductDao.selectClaimProductDetail(ocClaimProduct);
		retMap.put("ocClaimProduct", ocClaimProductDao.selectClaimProductDetail(ocClaimProduct));

		// 클레임 주문 정보
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaimProduct.getOrderNo());
		retMap.put("ocOrder", ocOrderDao.selectOrderDetail(ocOrder));

		// 주문 배송 이력 테이블로부터의 조회
		OcOrderDeliveryHistory ocOrderDeliveryHistory = new OcOrderDeliveryHistory();
		ocOrderDeliveryHistory.setClmNo(ocClaimProduct.getClmNo());
		ocOrderDeliveryHistory.setClmPrdtSeq(ocClaimProduct.getClmPrdtSeq());
		ocOrderDeliveryHistory.setImpsbltProcYn(Const.BOOLEAN_TRUE); // 불가처리여부
		retMap.put("ocOrderDeliveryHistory",
				ocOrderDeliveryHistoryDao.selectDeliveryByClmNoClmPrdtSeq(ocOrderDeliveryHistory));

		return retMap;
	}

	/**
	 * @Desc : 클레임 이력 화면 -> 클레임 상품 상세 조회
	 * @Method Name : getClaimHistoryDetail
	 * @Date : 2019. 3. 18.
	 * @Author : 이강수
	 * @param OcClaimProduct
	 * @return OcClaimProduct
	 * @throws Exception
	 */
	public OcClaimProduct getClaimHistoryDetail(OcClaimProduct ocClaimProduct) throws Exception {

		ocClaimProduct.setPrdtRltnFileSeq(1); // 상품대표이미지 seq : 1

		return ocClaimProductDao.selectClaimProductDetail(ocClaimProduct);
	}

	/**
	 * @Desc : 클레임 이력 화면 -> 클레임 이력 목록 조회
	 * @Method Name : getClaimHistoryList
	 * @Date : 2019. 3. 18.
	 * @Author : 이강수
	 * @param OcClaimProduct
	 * @return List<OcClaimStatusHistory>
	 * @throws Exception
	 */
	public Page<OcClaimStatusHistory> getClaimHistoryList(Pageable<OcClaimStatusHistory, OcClaimStatusHistory> pageable)
			throws Exception {

		int count = ocClaimStatusHistoryDao.selectClaimHistoryListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			List<OcClaimStatusHistory> list = ocClaimStatusHistoryDao.selectClaimHistoryList(pageable);

			pageable.setContent(list);
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 주문상세 클레임탭 내부 환불정보 조회
	 * @Method Name : getRefundInfoInOrderTab
	 * @Date : 2019. 8. 7.
	 * @Author : 이강수
	 * @param OcClaim : orgOrderNo
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> getRefundInfoInOrderTab(OcClaim ocClaim) throws Exception {
		Map<String, Object> map = new HashMap<>();

		/**
		 * 총 취소 금액(①)
		 */
		// 배송비
		ocClaim.setPrdtTypeCodeDelivery(CommonCode.PRDT_TYPE_CODE_DELIVERY);

		// 취소, 반품
		String[] clmGbnCodes = { CommonCode.CLM_GBN_CODE_CANCEL, CommonCode.CLM_GBN_CODE_RETURN };
		ocClaim.setClmGbnCodes(clmGbnCodes);

		String[] clmStatCodes = {
				// 취소:환불접수
				CommonCode.CLM_STAT_CODE_CANCEL_REFUND_ACCEPT
				// 취소:취소완료
				, CommonCode.CLM_STAT_CODE_CANCEL_FINISH
				// 반품:환불접수
				, CommonCode.CLM_STAT_CODE_RETURN_REFUND_ACCEPT
				// 반품:반품완료
				, CommonCode.CLM_STAT_CODE_RETURN_FINISH };
		ocClaim.setClmStatCodes(clmStatCodes);

		OcClaimProduct totalCancelAmt = ocClaimProductDao.selectCancelPrdtAmtCancelDlvyAmt(ocClaim);

		/**
		 * 총 추가비용(②)
		 */
		ocClaim.setRedempRefundGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP);
		ocClaim.setHistGbnType(CommonCode.HIST_GBN_TYPE_HISTORY);
		ocClaim.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE);

		List<OcClaimPayment> totalAddAmtList = ocClaimPaymentDao.selectOrgOrderRedempAmt(ocClaim);

		/**
		 * 총 환불금액(①-②)
		 */
		ocClaim.setRedempRefundGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
		ocClaim.setHistGbnType(CommonCode.HIST_GBN_TYPE_PUBLIC);
		ocClaim.setOrderPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH);

		List<OcClaimPayment> totalRefundAmtList = ocClaimPaymentDao.selectOrgOrderRefundAmt(ocClaim);

		if (UtilsObject.isNotEmpty(totalCancelAmt)) {
			map.put("totalCancelAmt", totalCancelAmt);
		}

		if (!UtilsArray.isEmpty(totalAddAmtList)) {
			map.put("totalAddAmtList", totalAddAmtList);
			map.put("totalAddAmtSum", totalAddAmtList.stream().mapToInt(x -> x.getRedempAmt()).sum());
		}

		if (!UtilsArray.isEmpty(totalRefundAmtList)) {
			map.put("totalRefundAmtList", totalRefundAmtList);
			map.put("totalRefundAmtSum", totalRefundAmtList.stream().mapToInt(x -> x.getRefundAmt()).sum());
		}

		/**
		 * 원 주문 기준의 모든 클레임 결제 목록 추출
		 */
		OcClaimPayment paramClaimPayment = new OcClaimPayment();
		paramClaimPayment.setOrgOrderNo(ocClaim.getOrgOrderNo());
		List<OcClaimPayment> orderAllclaimPaymentList = ocClaimPaymentDao.selectClaimPaymentList(paramClaimPayment);

		// 기존 환수배송비 환불 발생 내역
		map.put("refundPreviousRedempDlvyList", orderAllclaimPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
				.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY))
				.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
				.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_ORDER_DELIVERY_AMT))
				.collect(Collectors.toList()));

		// 기존 환수배송비 환불 발생 내역 sum
		map.put("refundPreviousRedempDlvySumAmt", orderAllclaimPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
				.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY))
				.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
				.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_ORDER_DELIVERY_AMT))
				.mapToInt(o -> o.getPymntAmt()).sum());

		// 다족구매 프로모션 추가 환불대상 환불 발생 내역
		map.put("addMultiBuyDifferList",
				orderAllclaimPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
						.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY))
						.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
						.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_PROMOTION))
						.collect(Collectors.toList()));

		// 다족구매 프로모션 추가 환불대상 환불 발생 내역 sum
		map.put("addMultiBuyDifferSumAmt",
				orderAllclaimPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
						.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY))
						.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
						.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_PROMOTION))
						.mapToInt(o -> o.getPymntAmt()).sum());

		return map;
	}

	/**
	 * @Desc : BO 대쉬보드 - 클레임 건수 조회
	 * @Method Name : getClaimCountInfo
	 * @Date : 2019. 7. 23.
	 * @Author : 이강수
	 * @param OcClaimCountVO
	 * @return OcClaimCountVO
	 * @throws Exception
	 */
	public OcClaimCountVO getClaimCountInfo() throws Exception {

		OcClaimCountVO ocClaimCountVO = new OcClaimCountVO();

		ocClaimCountVO.setClmGbnCodeExchange(CommonCode.CLM_GBN_CODE_EXCHANGE);
		ocClaimCountVO.setClmGbnCodeReturn(CommonCode.CLM_GBN_CODE_RETURN);

		ocClaimCountVO.setPrdtTypeCodeDelivery(CommonCode.PRDT_TYPE_CODE_DELIVERY);
		ocClaimCountVO.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT);

		return ocClaimDao.selectClaimCountForDashborad(ocClaimCountVO);
	}

	/**************************** E : 이강수 *****************************/

	/**************************** S : 김태호 *****************************/
	/**
	 * @Desc : 할인 취소 정보 ( 쿠폰 + 프로모션)
	 * @Method Name : getClaimCancelDiscountList
	 * @Date : 2019. 4. 11.
	 * @Author : KTH
	 * @param ocClaimDiscountVO
	 * @return
	 * @throws Exception
	 */
	public List<OcOrderDiscountVO> getClaimCancelDiscountList(OcClaimDiscountVO ocClaimDiscountVO) throws Exception {
		ocClaimDiscountVO.setOrderNo(null); // 조회를 위한 조건 조정

		List<OcOrderDiscountVO> orderCouponList = ocOrderUseCouponDao.selectClaimCancelCouponList(ocClaimDiscountVO);
		List<OcOrderDiscountVO> orderPromoList = ocOrderProductApplyPromotionDao
				.selectClaimCancelPromoList(ocClaimDiscountVO);

		List<OcOrderDiscountVO> finalList = new ArrayList<>();

		finalList.addAll(orderCouponList);
		finalList.addAll(orderPromoList);

		return finalList;
	}

	/**
	 * @Desc : 환불/환수 정보 목록 조회 - 주문상세 팝업 클레임 탭
	 * @Method Name : getClaimPaymentListIntab
	 * @Date : 2019. 7. 19.
	 * @Author : 이강수
	 * @param OcClaimPayment
	 * @return List<OcClaimPayment>
	 * @throws Exception
	 */
	public List<OcClaimPayment> getClaimPaymentListIntab(OcClaimPayment ocClaimPayment) throws Exception {

		return ocClaimPaymentDao.selectClmPymntListInOrderTab(ocClaimPayment);
	}

	/**
	 * @Desc : 클레임 내역 목록 조회 - 주문상세 팝업 클레임 탭
	 * @Method Name : getClaimListIntab
	 * @Date : 2019. 3. 4.
	 * @Author : KTH, 이강수
	 * @param OcClaim
	 * @return List<OcClaim>
	 * @throws Exception
	 */
	public List<OcClaim> getClaimListIntab(OcClaim ocClaim) throws Exception {

		ocClaim.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT);
		ocClaim.setPrdtTypeCodeDelivery(CommonCode.PRDT_TYPE_CODE_DELIVERY);

		List<OcClaim> ocClaimList = ocClaimDao.selectClaimBasisList(ocClaim);

		OcClaimProduct ocClaimProduct = new OcClaimProduct();

		for (OcClaim oc : ocClaimList) {

			oc.setClmRsnNameExtraCnt(0);

			ocClaimProduct.setClmNo(oc.getClmNo());
			ocClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT);
			ocClaimProduct.setPrdtTypeCodeDelivery(CommonCode.PRDT_TYPE_CODE_DELIVERY);

			List<OcClaimProduct> ocpList = ocClaimProductDao.selectClmPrdtRsnCodeGroup(ocClaimProduct);

			if (ocpList != null) {
				if (ocpList.size() > 1) {
					oc.setClmRsnNameExtraCnt(ocpList.size() - 1);
				}
			}
		}

		return ocClaimList;
	}

	/**
	 * @Desc : 클레임 취소 접수 대상 주문상품 정보
	 * @Method Name : getClaimCancelOrderProductInfo
	 * @Date : 2019. 4. 9.
	 * @Author : KTH
	 * @param ocClaimProduct
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getClaimCancelOrderProductInfo(OcClaimProduct ocClaimProduct) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();

		retMap.put(CommonCode.CLM_RSN_CODE,
				commonCodeService.getCodeNoNameFilterAddInfo(CommonCode.CLM_RSN_CODE, CommonCode.CLM_GBN_CODE_CANCEL)); // 클레임사유코드

		/*
		 * 주문 정보
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaimProduct.getOrderNos()[0]);
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보
		retMap.put("ORDER_INFO", ocOrder);

		/*
		 * 사이트 무료배송 정책 조회를 위한 set
		 */
		SySite sySite = new SySite();
		sySite.setSiteNo(ocOrder.getSiteNo());
		sySite = sySiteDao.selectByPrimaryKey(sySite);
		retMap.put("sySite", sySite);

		/*
		 * 파라미터 주문번호에 해당하는 주문상품 목록
		 */
		OcOrderProduct ocOrderProduct = new OcOrderProduct();
		ocOrderProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 추출용
		ocOrderProduct.setOrderNo(ocClaimProduct.getOrderNos()[0]);
		ocOrderProduct.setPrdtRltnFileSeq(1); // 상품관련파일순번 : 1 (대표)
		List<OcOrderProduct> orderProductList = ocOrderProductDao.selectOrderProductList(ocOrderProduct);

		// 파라미터로 넘어온 orderPrdtSeq 에 해당되는 리스트만 별도 추출
		retMap.put("ORDER_PRODUCT_LIST",
				orderProductList.stream()
						.filter(x -> Arrays.asList(ocClaimProduct.getOrderPrdtSeqs()).contains(x.getOrderPrdtSeq()))
						.collect(Collectors.toList()));

		/*
		 * 전체취소/부분취소 여부를 가리기위해 사은품/배송비를 제외한 전체주문상품 목록 갯수를 내린다.
		 */
		long onlyOrderProductCnt = orderProductList.stream()
				.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
						&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
				.count();
		retMap.put("onlyOrderProductCnt", onlyOrderProductCnt);

		/*
		 * 주문 결제정보
		 */
		OcOrderPayment ocOrderPayment = new OcOrderPayment();
		ocOrderPayment.setOrderNo(ocClaimProduct.getOrderNos()[0]);
		retMap.put("ORDER_PAYMENT_LIST", ocOrderPaymentDao.selectPaymentList(ocOrderPayment));

		return retMap;
	}

	/**
	 * @Desc : 클레임 취소 등록(전체취소와 부분취소 확인 후 해당 메서드 호출)
	 * @Method Name : insertClaimCancel
	 * @Date : 2019. 7. 17.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocClaimProducts
	 * @param ocClaimMemo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> registClaimCancel(OcClaim ocClaim, OcClaimProduct[] ocClaimProducts,
			OcClaimMemo ocClaimMemo) throws Exception {
		OcClaimAmountVO ocClaimAmountVO = new OcClaimAmountVO();
		ocClaim.setClaimRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자 no set

		// 배열로 넘어온 클레임 대상 상품을 array 로 전환하여 set
		ocClaim.setOcClaimProductList(Arrays.asList(ocClaimProducts));

		// 클레임 대상 상품의 현재 상태를 실시간으로 조회
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrderNo());
		ocOrder.setOrderPrdtSeqs(ocClaim.getOcClaimProductList().stream().map(OcClaimProduct::getOrderPrdtSeq).collect(Collectors.<Integer>toList()).toArray(new Integer[ocClaim.getOcClaimProductList().size()]));
		List<OcOrderProduct> orderPrd = orderService.getOrderProducts(ocOrder);
		
		// 대상 상품의 상태가 결제완료, 상품준비중이 아니면 예외처리
		for (OcOrderProduct ocOrderProduct : orderPrd) {
			// 매장픽업인 경우 별도의 예외처리
			// 상품출고, 픽업준비완료일 경우 배송중단이 되어 있지 않으면 취소접수 불가처리
			if(UtilsText.equals(ocOrderProduct.getDlvyTypeCode(), CommonCode.DLVY_TYPE_CODE_STORE_PICKUP) && 
				(UtilsText.equals(ocOrderProduct.getOrderPrdtStatCode(), CommonCode.ORDER_PRDT_STAT_CODE_PRODUCT_DELIVERY) ||
					UtilsText.equals(ocOrderProduct.getOrderPrdtStatCode(), CommonCode.ORDER_PRDT_STAT_CODE_PICKUP_PREPARATION_FINISH))) {
				if(UtilsText.equals(ocOrderProduct.getDlvyDscntcYn(), Const.BOOLEAN_FALSE)){
					throw new Exception("validMsg:매장픽업주문이 상품출고 또는 픽업준비완료 상태라면 배송중단 전에는 주문취소할 수 없습니다.");
				}
			} else {
				if(!(UtilsText.equals(ocOrderProduct.getOrderPrdtStatCode(), CommonCode.ORDER_PRDT_STAT_CODE_COMPLETE) || 
						UtilsText.equals(ocOrderProduct.getOrderPrdtStatCode(), CommonCode.ORDER_PRDT_STAT_CODE_PRODUCT_PREPARATION))){
					throw new Exception("validMsg:취소가 불가능한 상품이 존재합니다.");
				}
			}
		}
		
		// 클레임 금액계산 정보(전체/부분 취소 여부만 확인)
		claimProcService.setAllCancelCheck(ocClaim, ocClaimAmountVO);
		
		/*
		 * 기본 validate
		 */
		OcClaimProduct validClaimProduct = new OcClaimProduct();
		Integer[] tempClaimProductPrdtSeqs = new Integer[ocClaimProducts.length];

		for (int i = 0; i < ocClaimProducts.length; i++) {
			tempClaimProductPrdtSeqs[i] = ocClaimProducts[i].getOrderPrdtSeq();
		}

		validClaimProduct.setOrgOrderNo(ocClaim.getOrderNo());
		validClaimProduct.setClmGbnCode(CommonCode.CLM_GBN_CODE_CANCEL);
		validClaimProduct.setOrderPrdtSeqs(tempClaimProductPrdtSeqs);

		Map<String, Object> validMap = this.validateClaimAccept(validClaimProduct);

		if (UtilsText.equals((String) validMap.get("success"), Const.BOOLEAN_FALSE)) {
			throw new Exception("validMsg:".concat((String) validMap.get("message")));
		}

		/*
		 * 전체 취소에 따른 분기
		 */
		if (ocClaimAmountVO.isAllCancelYn()) {
			return this.registClaimAllCancel(ocClaim, ocClaimAmountVO, ocClaimMemo);
		} else {
			return this.registClaimPartCancel(ocClaim, ocClaimAmountVO, ocClaimMemo);
		}
	}

	/**
	 * @Desc : 클레임 전체 취소 등록
	 * @Method Name : registClaimAllCancel
	 * @Date : 2019. 7. 18.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocClaimAmountVO
	 * @param ocClaimMemo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> registClaimAllCancel(OcClaim ocClaim, OcClaimAmountVO ocClaimAmountVO,
			OcClaimMemo ocClaimMemo) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		/*
		 * [원 주문정보 get]
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보

		/*
		 * [주문자 정보]
		 */
		MbMember mbMember = memberDao.selectMemberDefalutInfo(ocOrder.getMemberNo());

		/*
		 * [기본 등록정보 set]
		 */
		ocClaim.setMemberNo(ocOrder.getMemberNo()); // 인서트 시 주문정보를 이용하나 ocClaim 재 사용을 위해 set
		ocClaim.setClmGbnCode(CommonCode.CLM_GBN_CODE_CANCEL); // 클레임구분코드-취소
		ocClaim.setDeviceCode(ocOrder.getDeviceCode()); // 디바이스코드

		ocClaim.setRtrvlGbnType(CommonCode.RTRVL_GBN_TYPE_ONLINE); // 회수지구분(O:온라인)
		ocClaim.setRtrvlStoreNo(null); // 회수매장번호
		ocClaim.setRtrvlReqYn(Const.BOOLEAN_FALSE); // 회수신청여부

		ocClaim.setBuyerName(ocOrder.getBuyerName());
		ocClaim.setBuyerTelNoText(ocOrder.getBuyerTelNoText());
		ocClaim.setBuyerHdphnNoText(ocOrder.getBuyerHdphnNoText());
		ocClaim.setBuyerPostCodeText(ocOrder.getBuyerPostCodeText());
		ocClaim.setBuyerPostAddrText(ocOrder.getBuyerPostAddrText());
		ocClaim.setBuyerDtlAddrText(ocOrder.getBuyerDtlAddrText());

		ocClaim.setRcvrName(ocOrder.getRcvrName());
		ocClaim.setRcvrTelNoText(ocOrder.getRcvrTelNoText());
		ocClaim.setRcvrHdphnNoText(ocOrder.getRcvrHdphnNoText());
		ocClaim.setRcvrPostCodeText(ocOrder.getRcvrPostCodeText());
		ocClaim.setRcvrPostAddrText(ocOrder.getRcvrPostAddrText());
		ocClaim.setRcvrDtlAddrText(ocOrder.getRcvrDtlAddrText());

		ocClaim.setDlvyMemoText(ocOrder.getDlvyMemoText());
		ocClaim.setDlvyTypeCode(ocOrder.getDlvyTypeCode()); // 배송유형코드

		// 주문자 회원 정보에 환불계좌 정보가 있으면 set
		if (UtilsText.isNotEmpty(mbMember.getBankCode()) && UtilsText.isNotEmpty(mbMember.getAcntNoText())
				&& UtilsText.isNotEmpty(mbMember.getAcntHldrName())) {
			ocClaim.setBankCode(mbMember.getBankCode());
			ocClaim.setRfndAcntText(mbMember.getAcntNoText());
			ocClaim.setAcntHldrName(mbMember.getAcntHldrName());
		}

		ocClaim.setVndrNo(ocClaim.getOcClaimProductList().get(0).getVndrNo()); // 업체번호(클레임상품 중 한개 발췌-취소는 상품단위 의미없음)

		ocClaim.setTotalRfndAmt(ocOrder.getPymntAmt()); // 총환불금액
		ocClaim.setTotalRedempAmt(0); // 총환수금액

		// 미처리 여부 set
		if (UtilsText.isEmpty(ocClaim.getUnProcYn())) {
			ocClaim.setUnProcYn(Const.BOOLEAN_FALSE);
		}

		ocClaim.setAdminAcceptYn(Const.BOOLEAN_TRUE); // 관리자접수여부
		ocClaim.setOflnAcceptYn(Const.BOOLEAN_FALSE); // 오프라인접수여부

		// 추가배송비 관련 set
		ocClaim.setAddDlvyAmtPymntType(null); // 추가배송비결제방법
		ocClaim.setAddDlvyAmt(0); // 추가배송비
		ocClaim.setHoldCpnSeq(null); // 보유쿠폰순번

		ocClaim.setClmStatCode(CommonCode.CLM_STAT_CODE_CANCEL_ACCEPT); // 클레임상태코드 - 취소접수
		ocClaim.setBranchMoveCode(CommonCode.BRANCH_MOVE_CODE_ORIGIN_DEAL); // 점간이동코드 - 원거래

		ocClaim.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자
		ocClaim.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자

		/*
		 * [클레임 등록(마스터)] 등록 시 생성된 clmNo 를 ocClaim 에서 가지고 있음
		 */
		ocClaimDao.insertClaimInfo(ocClaim);

		/*
		 * [클레임상품 등록] [클레임상태이력 등록]
		 */
		for (OcClaimProduct ocClaimProduct : ocClaim.getOcClaimProductList()) {
			// 클레임상품 등록, 클레임상태이력 등록
			this.insertClaimPrdtAndClaimStatusHistory(ocClaim, ocClaimProduct,
					CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT);

			// 해당 상품에 사은품이 있는 경우 사은품 한번 더 등록
			if (!ObjectUtils.isEmpty(ocClaimProduct.getOrderGiftPrdtSeq())) {
				ocClaimProduct.setOrderPrdtSeq(ocClaimProduct.getOrderGiftPrdtSeq()); // 사은품 주문상품순번으로 대체
				this.insertClaimPrdtAndClaimStatusHistory(ocClaim, ocClaimProduct,
						CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT);
			}
		}

		/*
		 * 클레임 상품에 배송비 등록
		 */
		for (OcOrderProduct orderProduct : ocClaimAmountVO.getOrgOrderProductList()) {
			if (UtilsText.equals(orderProduct.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY)) { // 배송비
				OcClaimProduct dlvyClaimProduct = new OcClaimProduct();

				dlvyClaimProduct.setOrderPrdtSeq(orderProduct.getOrderPrdtSeq());
				dlvyClaimProduct.setClmRsnCode(ocClaim.getOcClaimProductList().get(0).getClmRsnCode());
				dlvyClaimProduct.setClmEtcRsnText(ocClaim.getOcClaimProductList().get(0).getClmEtcRsnText());

				// 클레임상품 등록, 클레임상태이력 등록
				this.insertClaimPrdtAndClaimStatusHistory(ocClaim, dlvyClaimProduct,
						CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT);
			}
		}

		/*
		 * 클레임 금액 계산(클레임 마스터/상품 등록 후 호출) - 전체취소는 배송비까지 등록 후 호출
		 */
		claimProcService.setClaimAmountCalcForAllCancel(ocClaim, ocOrder, ocClaimAmountVO);

		/*
		 * 원 주문 마스터/상품 상태 값 변경
		 */
		OcOrderProduct updateOrderProduct = new OcOrderProduct();
		updateOrderProduct.setOrderNo(ocOrder.getOrderNo());
		updateOrderProduct.setModerNo(LoginManager.getUserDetails().getAdminNo());
		updateOrderProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_ACCEPT); // 주문상품상태코드 : 취소접수

		ocOrderProductDao.updateOrderProductForCalim(updateOrderProduct); // 원 주문상품 상태 값 변경

		OcOrder updateOrder = new OcOrder();
		updateOrder.setOrderNo(ocOrder.getOrderNo());
		updateOrder.setOrderStatCode(CommonCode.ORDER_STAT_CODE_CANCEL_ACCEPT); // 주문상태코드 : 전체취소접수
		updateOrder.setCnclAmt(ocOrder.getPymntAmt());
		updateOrder.setClmNo(ocClaim.getClmNo());
		updateOrder.setModerNo(LoginManager.getUserDetails().getAdminNo());

		ocOrderDao.updateOrderForClaim(updateOrder); // 원 주문 상태 값 변경

		// 원 주문의 주문상품이력 등록(취소접수)
		for (OcOrderProduct orderProduct : ocClaimAmountVO.getOrgOrderProductList()) {
			OcOrderProductHistory orgOrderProductHistory = new OcOrderProductHistory();
			orgOrderProductHistory.setOrderNo(orderProduct.getOrderNo());
			orgOrderProductHistory.setOrderPrdtSeq(orderProduct.getOrderPrdtSeq());
			orgOrderProductHistory.setPrdtNo(orderProduct.getPrdtNo());
			orgOrderProductHistory.setPrdtOptnNo(orderProduct.getPrdtOptnNo());
			orgOrderProductHistory.setPrdtName(orderProduct.getPrdtName());
			orgOrderProductHistory.setEngPrdtName(orderProduct.getEngPrdtName());
			orgOrderProductHistory.setOptnName(orderProduct.getOptnName());
			orgOrderProductHistory.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_ACCEPT); // 주문상품상태코드:취소접수
			orgOrderProductHistory.setNoteText(null);
			orgOrderProductHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo());

			ocOrderProductHistoryDao.insertProductHistory(orgOrderProductHistory); // 주문상품이력 생성
		}

		/*
		 * 주문배송이력 상태 변경 2020.03.20 : 취소처리 단계에서 업데이트
		 */
//		OcOrderDeliveryHistory orderDeliveryHistory = new OcOrderDeliveryHistory();
//		orderDeliveryHistory.setOrderNo(ocClaim.getOrderNo());
//		orderDeliveryHistory.setDlvyStatCode(CommonCode.DLVY_STAT_CODE_DELIVERY_CANCEL); // 배송 상태 코드 : 배송취소
//		orderDeliveryHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
//		orderDeliveryHistory.setModerNo(LoginManager.getUserDetails().getAdminNo());
//		ocOrderDeliveryHistoryDao.updateOrderDeliveryHistoryStat(orderDeliveryHistory);

		/*
		 * 클레임 결제 등록
		 */
		// 주 결제 수단
		if (ocClaimAmountVO.getMainPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getMainPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getMainPayment().getPymntAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 기프트
		if (ocClaimAmountVO.getGiftPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getGiftPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getGiftPayment().getPymntAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 이벤트 포인트
		if (ocClaimAmountVO.getEventPointPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getEventPointPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getEventPointPayment().getPymntAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소
																									// 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 포인트
		if (ocClaimAmountVO.getPointPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getPointPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getPointPayment().getPymntAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로
																								// 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		/*
		 * [메모 등록] 클레임 상품 단위가 아닌 클레임 단위, 클레임 등록 시 메모 text가 입력된 상태만 등록
		 */
		if (UtilsText.isNotEmpty(ocClaimMemo.getMemoText())) {
			ocClaimMemo.setClmNo(ocClaim.getClmNo());
			ocClaimMemo.setOrgOrderNo(ocClaim.getOrgOrderNo());
			ocClaimMemo.setClmPrdtSeq(null);
			ocClaimMemo.setClmGbnCode(CommonCode.CLM_GBN_CODE_CANCEL); // 클레임구분코드-취소
			ocClaimMemo.setRgsterNo(LoginManager.getUserDetails().getAdminNo());

			ocClaimMemoDao.insertClaimMemo(ocClaimMemo); // 메모 등록
		}

		resultMap.put("success", Const.BOOLEAN_TRUE);
		resultMap.put("clmNo", ocClaim.getClmNo());

		return resultMap;
	}

	/**
	 * @Desc : 클레임 부분 취소 등록
	 * @Method Name : registClaimPartCancel
	 * @Date : 2019. 7. 18.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocClaimAmountVO
	 * @param ocClaimMemo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> registClaimPartCancel(OcClaim ocClaim, OcClaimAmountVO ocClaimAmountVO,
			OcClaimMemo ocClaimMemo) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		/*
		 * [파라미터로 넘어온 클레임 대상 주문상품 순번 배열] - 파라미터 클레임 상품 대상은 사은품과 배송비가 없음
		 */
		Object[] claimOrderPrdtSeqs = ocClaim.getOcClaimProductList().stream().map(OcClaimProduct::getOrderPrdtSeq)
				.toArray();

		/*
		 * [원 주문정보 get]
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보

		/*
		 * [주문자 회원 정보]
		 */
		MbMember mbMember = memberDao.selectMemberDefalutInfo(ocOrder.getMemberNo());

		/*
		 * [기본 등록정보 set]
		 */
		ocClaim.setMemberNo(ocOrder.getMemberNo()); // 인서트 시 주문정보를 이용하나 ocClaim 재 사용을 위해 set
		ocClaim.setClmGbnCode(CommonCode.CLM_GBN_CODE_CANCEL); // 클레임구분코드-취소
		ocClaim.setDeviceCode(ocOrder.getDeviceCode()); // 디바이스코드

		ocClaim.setRtrvlGbnType(CommonCode.RTRVL_GBN_TYPE_ONLINE); // 회수지구분(O:온라인)
		ocClaim.setRtrvlStoreNo(null); // 회수매장번호
		ocClaim.setRtrvlReqYn(Const.BOOLEAN_FALSE); // 회수신청여부

		ocClaim.setBuyerName(ocOrder.getBuyerName());
		ocClaim.setBuyerTelNoText(ocOrder.getBuyerTelNoText());
		ocClaim.setBuyerHdphnNoText(ocOrder.getBuyerHdphnNoText());
		ocClaim.setBuyerPostCodeText(ocOrder.getBuyerPostCodeText());
		ocClaim.setBuyerPostAddrText(ocOrder.getBuyerPostAddrText());
		ocClaim.setBuyerDtlAddrText(ocOrder.getBuyerDtlAddrText());

		ocClaim.setRcvrName(ocOrder.getRcvrName());
		ocClaim.setRcvrTelNoText(ocOrder.getRcvrTelNoText());
		ocClaim.setRcvrHdphnNoText(ocOrder.getRcvrHdphnNoText());
		ocClaim.setRcvrPostCodeText(ocOrder.getRcvrPostCodeText());
		ocClaim.setRcvrPostAddrText(ocOrder.getRcvrPostAddrText());
		ocClaim.setRcvrDtlAddrText(ocOrder.getRcvrDtlAddrText());

		ocClaim.setDlvyMemoText(ocOrder.getDlvyMemoText());
		ocClaim.setDlvyTypeCode(ocOrder.getDlvyTypeCode()); // 배송유형코드

		// 주문자 회원 정보에 환불계좌 정보가 있으면 set
		if (UtilsText.isNotEmpty(mbMember.getBankCode()) && UtilsText.isNotEmpty(mbMember.getAcntNoText())
				&& UtilsText.isNotEmpty(mbMember.getAcntHldrName())) {
			ocClaim.setBankCode(mbMember.getBankCode());
			ocClaim.setRfndAcntText(mbMember.getAcntNoText());
			ocClaim.setAcntHldrName(mbMember.getAcntHldrName());
		}

		ocClaim.setVndrNo(ocClaim.getOcClaimProductList().get(0).getVndrNo()); // 업체번호(클레임상품 중 한개 발췌-취소는 상품단위 의미없음)

		ocClaim.setTotalRfndAmt(ocOrder.getPymntAmt()); // 총환불금액
		ocClaim.setTotalRedempAmt(0); // 총환수금액

		// 미처리 여부 set
		if (UtilsText.isEmpty(ocClaim.getUnProcYn())) {
			ocClaim.setUnProcYn(Const.BOOLEAN_FALSE);
		}

		ocClaim.setAdminAcceptYn(Const.BOOLEAN_TRUE); // 관리자접수여부
		ocClaim.setOflnAcceptYn(Const.BOOLEAN_FALSE); // 오프라인접수여부

		// 추가배송비 관련 set
		ocClaim.setAddDlvyAmtPymntType(null); // 추가배송비결제방법
		ocClaim.setAddDlvyAmt(0); // 추가배송비
		ocClaim.setHoldCpnSeq(null); // 보유쿠폰순번

		ocClaim.setClmStatCode(CommonCode.CLM_STAT_CODE_CANCEL_ACCEPT); // 클레임상태코드 - 취소접수
		ocClaim.setBranchMoveCode(CommonCode.BRANCH_MOVE_CODE_ORIGIN_DEAL); // 점간이동코드 - 원거래

		ocClaim.setRgsterNo(ocClaim.getClaimRgsterNo()); // 등록자
		ocClaim.setModerNo(ocClaim.getClaimRgsterNo()); // 수정자

		/*
		 * [클레임 등록(마스터)] 등록 시 생성된 clmNo 를 ocClaim 에서 가지고 있음
		 */
		ocClaimDao.insertClaimInfo(ocClaim);

		/*
		 * [클레임상품 등록] [클레임상태이력 등록]
		 */
		for (OcClaimProduct ocClaimProduct : ocClaim.getOcClaimProductList()) {
			// 클레임상품 등록, 클레임상태이력 등록
			this.insertClaimPrdtAndClaimStatusHistory(ocClaim, ocClaimProduct,
					CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT);

			// 해당 상품에 사은품이 있는 경우 사은품 한번 더 등록
			if (!ObjectUtils.isEmpty(ocClaimProduct.getOrderGiftPrdtSeq())) {
				ocClaimProduct.setOrderPrdtSeq(ocClaimProduct.getOrderGiftPrdtSeq()); // 사은품 주문상품순번으로 대체
				this.insertClaimPrdtAndClaimStatusHistory(ocClaim, ocClaimProduct,
						CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT);
			}
		}

		/*************************************
		 * 클레임 금액 계산(클레임 마스터/상품 등록 후 호출)
		 *************************************/
		// ocClaimAmountVO 에 계산된 금액 set
		claimProcService.setClaimAmountCalcForPartCancel(ocClaim, ocOrder, ocClaimAmountVO,
				CommonCode.CLM_GBN_CODE_CANCEL, false);

		/*
		 * 환불 배송비 발생된 경우 클레임 상품에 배송비 등록
		 */
		if (!ObjectUtils.isEmpty(ocClaimAmountVO.getCancelDlvyProductList())) {
			for (OcOrderProduct orderProduct : ocClaimAmountVO.getCancelDlvyProductList()) {
				OcClaimProduct dlvyClaimProduct = new OcClaimProduct();

				dlvyClaimProduct.setOrderPrdtSeq(orderProduct.getOrderPrdtSeq());
				dlvyClaimProduct.setClmRsnCode(ocClaim.getOcClaimProductList().get(0).getClmRsnCode());
				dlvyClaimProduct.setClmEtcRsnText(ocClaim.getOcClaimProductList().get(0).getClmEtcRsnText());

				// 클레임상품 등록, 클레임상태이력 등록
				this.insertClaimPrdtAndClaimStatusHistory(ocClaim, dlvyClaimProduct,
						CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT);
			}
		}

		/*
		 * 클레임 계산 결과 환수금이 더 많은 경우 취소 불가 처리
		 */
		if (ocClaimAmountVO.getRefundCnclAmt() < 0) {
			throw new Exception("validMsg:환수금이 발생하여 취소가 불가합니다.");
		}

		/*
		 * 클레임 마스터 금액 업데이트
		 */
		OcClaim updateAmtClaim = new OcClaim();
		updateAmtClaim.setClmNo(ocClaim.getClmNo());
		updateAmtClaim.setTotalRfndAmt(ocClaimAmountVO.getRefundCnclAmt()); // 총환불금액
		updateAmtClaim.setTotalRedempAmt(
				ocClaimAmountVO.getRedempAmtByMultiBuy() + ocClaimAmountVO.getRedempAmtByFreeDlvyProduct()); // 총환수금액
		updateAmtClaim.setModerNo(LoginManager.getUserDetails().getAdminNo());

		ocClaimDao.updateClaimStat(updateAmtClaim);

		/*************************************
		 * 환불 배송비까지 등록된 이후 목록 재 조회
		 *************************************/
		/*
		 * [원 주문에 걸린 모든 클레임 - 현재 클레임으로 인해 등록된 사은품/배송비 모두 포함]
		 */
		OcClaimProduct ocClaimProduct = new OcClaimProduct();
		ocClaimProduct.setOrgOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준
		ocClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 상품 코드
		List<OcClaimProduct> orderAllClaimProductList = ocClaimProductDao.selectOrgClaimProductList(ocClaimProduct);

		ocClaimAmountVO.setOrderAllClaimProductList(orderAllClaimProductList); // 다시 set

		/*
		 * [현재 클레임 상품 목록 - 현재 클레임으로 인해 등록된 사은품/배송비 모두 포함]
		 */
		List<OcClaimProduct> thisTimeClaimProductList = orderAllClaimProductList.stream()
				.filter(x -> UtilsText.equals(x.getClmNo(), ocClaim.getClmNo())).collect(Collectors.toList());

		ocClaimAmountVO.setThisTimeClaimProductList(thisTimeClaimProductList); // 다시 set

		/*
		 * 원 주문 마스터/상품 상태 값 변경
		 */
		// 원 주문 상품상태 업데이트
		for (OcClaimProduct claimProduct : thisTimeClaimProductList) {
			OcOrderProduct updateOrderProduct = new OcOrderProduct();
			updateOrderProduct.setOrderNo(claimProduct.getOrderNo());
			updateOrderProduct.setOrderPrdtSeq(claimProduct.getOrderPrdtSeq());
			updateOrderProduct.setModerNo(ocClaim.getClaimRgsterNo());
			updateOrderProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_ACCEPT); // 주문상품상태코드 : 취소접수

			ocOrderProductDao.updateOrderProductForCalim(updateOrderProduct); // 원 주문상품 상태 값 변경
		}

		// 남은 상품 모두 취소인 경우 원 주문 마스터 상태 업데이트
		if (ocClaimAmountVO.isRemainAllCancelYn()) {
			OcOrder updateOrder = new OcOrder();
			updateOrder.setOrderNo(ocOrder.getOrderNo());
			updateOrder.setOrderStatCode(CommonCode.ORDER_STAT_CODE_CANCEL_ACCEPT); // 주문상태코드 : 전체취소접수
			updateOrder.setClmNo(ocClaim.getClmNo());
			updateOrder.setModerNo(ocClaim.getClaimRgsterNo());

			ocOrderDao.updateOrderForClaim(updateOrder); // 원 주문 상태 값 변경
		}

		// 원 주문의 주문상품이력 등록(취소접수)
		for (OcClaimProduct claimProduct : thisTimeClaimProductList) {
			OcOrderProductHistory orgOrderProductHistory = new OcOrderProductHistory();
			orgOrderProductHistory.setOrderNo(claimProduct.getOrderNo());
			orgOrderProductHistory.setOrderPrdtSeq(claimProduct.getOrderPrdtSeq());
			orgOrderProductHistory.setPrdtNo(claimProduct.getPrdtNo());
			orgOrderProductHistory.setPrdtOptnNo(claimProduct.getPrdtOptnNo());
			orgOrderProductHistory.setPrdtName(claimProduct.getPrdtName());
			orgOrderProductHistory.setEngPrdtName(claimProduct.getEngPrdtName());
			orgOrderProductHistory.setOptnName(claimProduct.getOptnName());
			orgOrderProductHistory.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_ACCEPT); // 주문상품상태코드:취소접수
			orgOrderProductHistory.setNoteText(null);
			orgOrderProductHistory.setRgsterNo(ocClaim.getClaimRgsterNo());

			ocOrderProductHistoryDao.insertProductHistory(orgOrderProductHistory); // 주문상품이력 생성
		}

		/*
		 * 주문배송이력 상태 변경 2020.03.20 : 취소처리 단계에서 업데이트
		 */
//		for (OcClaimProduct claimProduct : thisTimeClaimProductList) {
//			OcOrderDeliveryHistory orderDeliveryHistory = new OcOrderDeliveryHistory();
//			orderDeliveryHistory.setOrderNo(ocClaim.getOrderNo());
//			orderDeliveryHistory.setOrderPrdtSeq(claimProduct.getOrderPrdtSeq());
//			orderDeliveryHistory.setDlvyStatCode(CommonCode.DLVY_STAT_CODE_DELIVERY_CANCEL); // 배송 상태 코드 : 배송취소
//			orderDeliveryHistory.setRgsterNo(ocClaim.getClaimRgsterNo());
//			orderDeliveryHistory.setModerNo(ocClaim.getClaimRgsterNo());
//			ocOrderDeliveryHistoryDao.updateOrderDeliveryHistoryStat(orderDeliveryHistory);
//		}

		/*
		 * 환수 주문배송비 결제 등록(이력용 - 업체별)
		 */
		// 환수 주문배송비가 발생한 경우 이력용 결제 등록
		if (!ObjectUtils.isEmpty(ocClaimAmountVO.getVndrRedempDlvyPaymentList())) {
			for (OcClaimPayment redempDlvyPayment : ocClaimAmountVO.getVndrRedempDlvyPaymentList()) {
				ocClaimPaymentDao.insertClaimPayment(redempDlvyPayment);
			}
		}

		/*
		 * 남은 상품 전체 취소인 경우 기존 환수된 배송비 환불 이력 결제 등록
		 */
		if (ocClaimAmountVO.isRemainAllCancelYn()) {
			if (!ObjectUtils.isEmpty(ocClaimAmountVO.getPreviousRedempDlvyProductList())) {
				// 결제 이력 등록
				for (OcClaimPayment previousRedempDlvyPayment : ocClaimAmountVO.getPreviousRedempDlvyPaymentList()) {
					previousRedempDlvyPayment.setClmNo(ocClaim.getClmNo()); // 현재 클레임 번호로 set
					previousRedempDlvyPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REFUND); // 환수환불구분:환불
					previousRedempDlvyPayment.setPymntDtm(new Timestamp(new Date().getTime())); // 결제일시
					previousRedempDlvyPayment.setRgsterNo(ocClaim.getClaimRgsterNo()); // 등록자
					previousRedempDlvyPayment.setModerNo(ocClaim.getClaimRgsterNo()); // 수정자

					ocClaimPaymentDao.insertClaimPayment(previousRedempDlvyPayment);
				}
			}
		}

		/*
		 * 환수프로모션비(다족구매로 인해 발생) 발생된 경우 결제히스토리 등록
		 */
		if (ocClaimAmountVO.getRedempAmtByMultiBuy() > 0) {
			for (OcClaimPayment redempMultiBuyPayment : ocClaimAmountVO.getRedempMultiBuyPaymentList()) {
				ocClaimPaymentDao.insertClaimPayment(redempMultiBuyPayment);
			}
		}

		/*
		 * 기존 다족구매 리오더 매출로 인해 변경된 금액과 원 주문 기준 취소상품 금액 차이 결제히스토리 등록(현재 취소금을 맞추기 위한 이력)
		 */
		if (ocClaimAmountVO.getAddMultiBuyDifferAmt() > 0) {
			for (OcClaimPayment addMultiBuyDifferPaymentList : ocClaimAmountVO.getAddMultiBuyDifferPaymentList()) {
				ocClaimPaymentDao.insertClaimPayment(addMultiBuyDifferPaymentList);
			}
		}

		/*
		 * 클레임 결제 등록
		 */
		// 주 결제 수단
		if (ocClaimAmountVO.getMainPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getMainPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getMainCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 기프트
		if (ocClaimAmountVO.getGiftPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getGiftPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getGiftCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 이벤트 포인트
		if (ocClaimAmountVO.getEventPointPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getEventPointPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getEventPointCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소
																									// 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 포인트
		if (ocClaimAmountVO.getPointPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getPointPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getPointCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로
																								// 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		/*
		 * [메모 등록] 클레임 상품 단위가 아닌 클레임 단위, 클레임 등록 시 메모 text가 입력된 상태만 등록
		 */
		if (UtilsText.isNotEmpty(ocClaimMemo.getMemoText())) {
			ocClaimMemo.setClmNo(ocClaim.getClmNo());
			ocClaimMemo.setOrgOrderNo(ocClaim.getOrgOrderNo());
			ocClaimMemo.setClmPrdtSeq(null);
			ocClaimMemo.setClmGbnCode(CommonCode.CLM_GBN_CODE_CANCEL); // 클레임구분코드-취소
			ocClaimMemo.setRgsterNo(ocClaim.getClaimRgsterNo());

			ocClaimMemoDao.insertClaimMemo(ocClaimMemo); // 메모 등록
		}

		resultMap.put("success", Const.BOOLEAN_TRUE);
		resultMap.put("clmNo", ocClaim.getClmNo());

		return resultMap;
	}

	/**
	 * @Desc : 클레임 취소 처리(전체취소와 부분취소 확인 후 해당 메서드 호출)
	 * @Method Name : setClaimCancelProc
	 * @Date : 2019. 7. 20.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocClaimProducts
	 * @param ocClaimMemo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setClaimCancelProc(OcClaim paramClaim, OcClaimPayment[] ocClaimPayments)
			throws Exception {
		OcClaimAmountVO ocClaimAmountVO = new OcClaimAmountVO();

		/*
		 * 클레임 정보
		 */
		OcClaim ocClaimInfo = ocClaimDao.selectClaimBasisInfo(paramClaim); // 클레임 기본 정보

		// 파라미터에서 넘어오거나 기타 필요한 정보는 별도로 set
		ocClaimInfo.setClaimRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자 no set
		ocClaimInfo.setChngInputCnclAmtYn(paramClaim.getChngInputCnclAmtYn()); // 폼 취소 설정금액 변경 여부

		// 클레임 대상 상품
		OcClaimProduct ocClaimProduct = new OcClaimProduct();
		ocClaimProduct.setClmNo(ocClaimInfo.getClmNo());

		List<OcClaimProduct> claimProductList = ocClaimProductDao.selectClaimProductList(ocClaimProduct);

		// 클레임 대상 상품을 set
		ocClaimInfo.setOcClaimProductList(claimProductList.stream()
				.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
						&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
				.collect(Collectors.toList()));
		
		// 클레임 대상 상품의 상태가 취소접수 일때만 주문취소 완료가 가능 
		for (OcClaimProduct ocClaimPrdt : claimProductList) {
			if(!(UtilsText.equals(ocClaimPrdt.getOrderPrdtStatCode(), CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_ACCEPT))) {
				throw new Exception("validMsg:취소가 불가능한 상품이 존재합니다.");
			}
		}

		// 클레임 금액계산 정보(전체/부분 취소 여부만 확인)
		claimProcService.setAllCancelCheck(ocClaimInfo, ocClaimAmountVO);

		if (ocClaimAmountVO.isAllCancelYn()) {
			return this.setClaimAllCancelProc(ocClaimInfo, ocClaimAmountVO);
		} else {
			return this.setClaimPartCancelProc(ocClaimInfo, ocClaimAmountVO, ocClaimPayments);
		}
	}

	/**
	 * @Desc : 클레임 전체 취소 처리
	 * @Method Name : setClaimAllCancelProc
	 * @Date : 2019. 7. 1.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocClaimAmountVO
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setClaimAllCancelProc(OcClaim ocClaim, OcClaimAmountVO ocClaimAmountVO)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		// 리오더 주문 생성 여부(가상계좌 입금상태로 판단 - 전체취소인 경우만 사용)
		boolean reOrderRegistPossible = true;

		ocClaim.setClaimRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자 no set

		/*
		 * [원 주문정보 get]
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보

		// claim 정보에 siteNo set
		ocClaim.setSiteNo(ocOrder.getSiteNo());
		// claim 정보에 memberNo set (품절보상쿠폰 지급 서비스)
		ocClaim.setMemberNo(ocOrder.getMemberNo());

		/*
		 * [주문 상품 결제 정보]
		 */
		OcOrderPayment ocOrderPayment = new OcOrderPayment();
		ocOrderPayment.setOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준

		List<OcOrderPayment> orderPaymentList = ocOrderPaymentDao.selectPaymentList(ocOrderPayment);

		// 주문결제 가상계좌 입금대기 카운트
		long virtualAccountWaitDepositCnt = orderPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT)
						&& UtilsText.equals(x.getPymntStatCode(), CommonCode.PYMNT_STAT_CODE_WAIT_DEPOSIT))
				.count();

		// 가상계좌 입금대기 상태이면 리오더 주문을 생성하지 않음
		if (virtualAccountWaitDepositCnt > 0) {
			reOrderRegistPossible = false;
			ocClaimAmountVO.setReOrderRegistPossible(false); // 취소 후 처리를 위해 set
		}

		/*
		 * [주문자 정보]
		 */
		MbMember mbMember = memberDao.selectMemberDefalutInfo(ocOrder.getMemberNo());

		/*
		 * 클레임 금액 계산
		 */
		log.error("클레임 전체 취소 처리 금액계산  START ClmNo => {}", ocClaim.getClmNo());
		claimProcService.setClaimAmountCalcForAllCancel(ocClaim, ocOrder, ocClaimAmountVO);

		/*
		 * [현재 클레임 상품 목록 - 현재 클레임으로 인해 등록된 사은품/배송비 모두 포함]
		 */
		// 위 클레임 금액 계산 시 현재 클레임 상품은 배송비를 제외했기 때문에 다시 조회하여 set
		List<OcClaimProduct> thisTimeClaimProductList = ocClaimAmountVO.getOrderAllClaimProductList().stream()
				.filter(x -> UtilsText.equals(x.getClmNo(), ocClaim.getClmNo())).collect(Collectors.toList());

		ocClaimAmountVO.setThisTimeClaimProductList(thisTimeClaimProductList); // 다시 set

		/*
		 * 주문 마스터/상품 신규(매출취소 리오더) 등록
		 */
		if (reOrderRegistPossible) {
			OcOrder reOrder = new OcOrder();
			BeanUtils.copyProperties(ocOrder, reOrder); // 내용 복사

			String reOrderNo = orderService.createOrderSeq(); // 신규 주문번호

			ocClaim.setReOrderNo(reOrderNo); // 클레임 정보에도 신규주문번호 set

			reOrder.setOrderNo(reOrderNo); // 신규주문번호
			reOrder.setClmNo(ocClaim.getClmNo()); // 클레임번호 set
			reOrder.setSalesCnclGbnType(CommonCode.SALES_CNCL_GBN_TYPE_CANCEL); // 매출취소구분 : 취소
			reOrder.setOrderStatCode(CommonCode.ORDER_STAT_CODE_CANCEL_ACCEPT); // 주문상태코드 : 전체취소접수
			reOrder.setCnclAmt(ocOrder.getPymntAmt()); // 취소금액
			reOrder.setRgsterNo(ocClaim.getClaimRgsterNo());
			reOrder.setModerNo(ocClaim.getClaimRgsterNo());

			// 신규 주문(매출취소 리오더) 생성
			ocOrderDao.insertOrder(reOrder);

			// 신규 주문상품(매출취소 리오더) 생성
			for (OcOrderProduct orderProduct : ocClaimAmountVO.getOrgOrderProductList()) {
				OcOrderProduct reOrderProduct = new OcOrderProduct();
				BeanUtils.copyProperties(orderProduct, reOrderProduct); // 내용 복사

				reOrderProduct.setOrderNo(reOrderNo); // 신규 생성 주문번호
				reOrderProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_ACCEPT); // 주문상품상태코드:취소접수
				reOrderProduct.setRgsterNo(ocClaim.getClaimRgsterNo());
				reOrderProduct.setModerNo(ocClaim.getClaimRgsterNo());

				// 신규 리오더 주문상품 생성
				ocOrderProductDao.insertOrderProduct(reOrderProduct);
			}

			// 클레임상품 클레임생성 리오더 주문번호 업데이트
			OcClaimProduct reOrderUpdateClaimProduct = new OcClaimProduct();

			reOrderUpdateClaimProduct.setModerNo(ocClaim.getClaimRgsterNo());
			reOrderUpdateClaimProduct.setClmCrtOrderNo(reOrderNo);
			reOrderUpdateClaimProduct.setClmNo(ocClaim.getClmNo());

			ocClaimProductDao.updateClmCrtOrderNo(reOrderUpdateClaimProduct);
		}

		/*
		 * 품절보상 쿠폰 지급 처리
		 */
		Date orderDtm = new Date(ocOrder.getOrderDtm().getTime());
		Date compareDate = DateUtils.addDays(new Date(), -3);

		// 주문일 3일 경과 기준, 클레임 사유 품절, 임직원아닌 회원 주문 상품
		if (compareDate.compareTo(orderDtm) >= 0
				&& UtilsText.equals(ocClaim.getOcClaimProductList().get(0).getClmRsnCode(),
						CommonCode.CLM_RSN_CODE_CANCEL_SOLDOUT)
				&& UtilsText.equals(ocOrder.getEmpYn(), Const.BOOLEAN_FALSE)
				&& (UtilsText.equals(ocOrder.getMemberTypeCode(), CommonCode.MEMBER_TYPE_CODE_ONLINE)
						|| UtilsText.equals(ocOrder.getMemberTypeCode(), CommonCode.MEMBER_TYPE_CODE_MEMBERSHIP))) {
			// 클레임 대상 상품 수량만큼 지급
			claimProcService.setGiveSoldOutCmpns(ocClaim, ocClaim.getOcClaimProductList());
		}

		/*
		 * 주문 사용쿠폰 클레임번호 업데이트
		 */
		OcOrderUseCoupon orderUseCoupon = new OcOrderUseCoupon();
		orderUseCoupon.setOrderNo(ocClaim.getOrderNo());
		orderUseCoupon.setClmNo(ocClaim.getClmNo());

		ocOrderUseCouponDao.updateOrderUseCouponClmNo(orderUseCoupon); // 주문 사용쿠폰 클레임번호 업데이트

		/*
		 * 사용 쿠폰 재 발급
		 */
		List<Integer> holdCpnSeqs = ocClaimAmountVO.getOrderUseCouponList().stream()
				.map(OcOrderUseCoupon::getHoldCpnSeq).distinct().collect(Collectors.toList()); // 중복 쿠폰 제거
		for (Integer holdCpnSeq : holdCpnSeqs) {
			MbMemberCoupon reIssueMemberCoupon = new MbMemberCoupon();
			reIssueMemberCoupon.setMemberNo(ocOrder.getMemberNo());
			reIssueMemberCoupon.setHoldCpnSeq(holdCpnSeq);
			reIssueMemberCoupon.setReIssueRsnText("주문취소 재 발급");
			reIssueMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_REISSUANCE);
			reIssueMemberCoupon.setRgsterNo(Const.SYSTEM_ADMIN_NO);
			reIssueMemberCoupon.setModerNo(Const.SYSTEM_ADMIN_NO);

			mbMemberCouponDao.insertMemberCouponReIssue(reIssueMemberCoupon); // 쿠폰 재 발급
		}

		/*
		 * 주문상품 프로모션 클레임번호 업데이트
		 */
		OcOrderProductApplyPromotion ocOrderProductApplyPromotion = new OcOrderProductApplyPromotion();
		ocOrderProductApplyPromotion.setOrderNo(ocClaim.getOrderNo());
		ocOrderProductApplyPromotion.setClmNo(ocClaim.getClmNo());

		ocOrderProductApplyPromotionDao.updateOrderProductApplyPromotionClmNo(ocOrderProductApplyPromotion);

		/*
		 * 주문배송이력 상태 변경 2020.03.30 취소접수단계에서 업데이트하는 부분 삭제로 취소처리단계에서 업데이트한다.
		 */
		OcOrderDeliveryHistory orderDeliveryHistory = new OcOrderDeliveryHistory();
		orderDeliveryHistory.setOrderNo(ocClaim.getOrderNo());
		orderDeliveryHistory.setDlvyStatCode(CommonCode.DLVY_STAT_CODE_DELIVERY_CANCEL); // 배송 상태 코드 : 배송취소
		orderDeliveryHistory.setRgsterNo(ocClaim.getClaimRgsterNo());
		orderDeliveryHistory.setModerNo(ocClaim.getClaimRgsterNo());
		ocOrderDeliveryHistoryDao.updateOrderDeliveryHistoryStat(orderDeliveryHistory);

		/*
		 * 임직원 주문인 경우 임직원 한도 복원
		 */
		if (UtilsText.equals(ocOrder.getEmpOrderYn(), Const.BOOLEAN_TRUE)) {
			String orderYearMonth = new SimpleDateFormat(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS)
					.format(ocOrder.getOrderDtm());
			String orderDate = new SimpleDateFormat(Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS)
					.format(ocOrder.getOrderDtm());
			String thisYearMonth = new SimpleDateFormat(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS)
					.format(new Date());

			// 주문월과 해당월이 같을 경우만 한도복원 인터페이스 호출
			if (UtilsText.equals(orderYearMonth, thisYearMonth)) {
				MbEmployeeCertificationHistory employeeInfo = mbEmployeeCertificationHistoryDao
						.selectEmpCertfiHistory(mbMember.getMemberNo());

				EmployPoint returnEmployPointResult = membershipPointService.savePointByEmployNum("1", orderDate,
						employeeInfo.getEmpNoText(), String.valueOf(ocOrder.getPymntAmt()), Const.SALE_EMP_CD);
			}
		}

		/*
		 * 재고 변경, 주문 취소 인터페이스 호출, IF_ORDER 주문상태값 변경
		 */
		for (OcOrderProduct orderProduct : ocClaimAmountVO.getOrgOrderProductList()) {

			// 2020.03.24 : BO,PO 에서 배송전 주문취소시 재고원복 X (레드마인 #1242)
//			// 배송비 상품이 아닌 경우 재고 변경
//			if (!UtilsText.equals(orderProduct.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY)) {
//				// 예약 주문이 아닌 경우 재고 변경
//				if (UtilsText.equals(ocOrder.getRsvOrderYn(), Const.BOOLEAN_FALSE)) {
//					// 재고구분이 AI, VD 인 경우
//					if (UtilsText.equals(orderProduct.getStockGbnCode(), CommonCode.STOCK_GBN_CODE_AI)
//							|| UtilsText.equals(orderProduct.getStockGbnCode(), CommonCode.STOCK_GBN_CODE_VD)) {
//
//						// 2020.03.10 : '품절'로 인한 취소가 아닌 경우에만 재고반영 (레드마인 #1069)
//						if (!UtilsText.equals(ocClaim.getOcClaimProductList().get(0).getClmRsnCode(),
//								CommonCode.CLM_RSN_CODE_CANCEL_SOLDOUT)) {
//
//							orderService.updateProductStockAdjust(orderProduct.getPrdtNo(),
//									orderProduct.getPrdtOptnNo(), 1, orderProduct.getStockGbnCode(), false);
//						}
//					}
//				}
//			}

			// 자사 상품인 경우만 취소 인터페이스 호출
			if (UtilsText.equals(orderProduct.getMmnyPrdtYn(), Const.BOOLEAN_TRUE)) {
				// 주문 취소 인터페이스 호출(사은품, 배송비 제외 - 사은품은 본 상품과 join된 데이터 사용)
				if (!UtilsText.equals(orderProduct.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
						&& !UtilsText.equals(orderProduct.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY)) {

					claimProcService.callCancelOrderInterface(orderProduct);
				}

				// IF_ORDER 주문상태값 변경
				if (UtilsText.isNotEmpty(orderProduct.getDlvyIdText())) {
					// ocClaimProductDao.updateInterfaceOrderCancel(orderProduct.getDlvyIdText());

					Map<String, String> paramMap = new HashMap<>();
					paramMap.put("dlvyIdText", orderProduct.getDlvyIdText());

					// 2020.02.07 : PR_ORDER_CANCEL 프로시져 호출로 변경
					String errorCode = this.setCallProcedureForOrderCancel(paramMap);
					log.error("[" + orderProduct.getDlvyIdText() + "] callProcedureForOrderCancel return >>> "
							+ errorCode);

					if (!UtilsText.equals(errorCode, "0")) {
						throw new Exception("validMsg:배송취소연동(PR_ORDER_CANCEL)에 실패하였습니다.");
					}
				} else {
					log.error("callProcedureForOrderCancel 실행안함 !!!!!!");
				}
			}
		}

		/*
		 * 결제취소
		 */
		// 결제취소시 환불 발생여부(refundOccurrence) ocClaimAmountVO 에 set
		claimProcService.setCancelPayment(ocClaimAmountVO, ocClaim, mbMember);

//		if(true) {
//			throw new Exception("오류 발생 !!!!!! ");
//		}
		/*
		 * 결제취소 처리 후 처리 주문, 클레임 상태 값 후 처리
		 */
		claimProcService.setClaimCancelAfterProc(ocClaim, ocClaimAmountVO);

		if (ocClaimAmountVO.isRefundOccurrence()) {
			resultMap.put("success", Const.BOOLEAN_FALSE);
			resultMap.put("message", "취소 처리는 완료되었으나\n\n결제취소 실패가 발생하여 환불접수 처리되었습니다.");
			resultMap.put("clmNo", ocClaim.getClmNo());
		} else {
			resultMap.put("success", Const.BOOLEAN_TRUE);
			resultMap.put("message", "주문이 정상적으로 취소되었습니다.");
			resultMap.put("clmNo", ocClaim.getClmNo());
		}

		// 이메일 , 카톡 문자 알림 서비스 시작
		try {
			claimMessageService.orderAllCancel(ocClaim);
		} catch (Exception e) {
			log.error("주문전체취소 메세지 메일 발송 실패 : " + e.getMessage());
		}

		return resultMap;
	}

	/**
	 * @Desc : PR_ORDER_CANCEL 호출 / 성공 : "0"
	 * @Method Name : setCallProcedureForOrderCancel
	 * @Date : 2020. 02. 17.
	 * @Author : 이강수
	 * @param Map<String, String> map
	 * @return
	 */
	public String setCallProcedureForOrderCancel(Map<String, String> map) throws Exception {
		ocClaimProductDao.callProcedureForOrderCancel(map);
		return map.get("errorCode");
	}

	/**
	 * @Desc : 클레임 부분 취소 처리
	 * @Method Name : setClaimPartCancelProc
	 * @Date : 2019. 7. 1.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocClaimAmountVO
	 * @param ocClaimPayments
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setClaimPartCancelProc(OcClaim ocClaim, OcClaimAmountVO ocClaimAmountVO,
			OcClaimPayment[] ocClaimPayments) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		String clmNo = ocClaim.getClmNo(); // 클레임 번호

		/*
		 * 클레임 정보
		 */
		ocClaim = ocClaimDao.selectClaimBasisInfo(ocClaim); // 클레임 기본 정보

		ocClaim.setClaimRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자 no set

		/*
		 * [원 주문정보 get]
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보

		// claim 정보에 siteNo set
		ocClaim.setSiteNo(ocOrder.getSiteNo());
		// claim 정보에 memberNo set (품절보상쿠폰 지급 서비스)
		ocClaim.setMemberNo(ocOrder.getMemberNo());

		/*
		 * [주문자 정보]
		 */
		MbMember mbMember = memberDao.selectMemberDefalutInfo(ocOrder.getMemberNo());

		/*************************************
		 * 클레임 금액 계산
		 *************************************/
		log.error("클레임 부분 취소 처리 금액계산  START ClmNo => {}", ocClaim.getClmNo());
		claimProcService.setClaimAmountCalcForPartCancel(ocClaim, ocOrder, ocClaimAmountVO,
				CommonCode.CLM_GBN_CODE_CANCEL, false);

		/*
		 * [현재 클레임 상품 목록 - 현재 클레임으로 인해 등록된 사은품/배송비 모두 포함]
		 */
		// 위 클레임 금액 계산 시 현재 클레임 상품은 배송비를 제외했기 때문에 다시 조회하여 set
		List<OcClaimProduct> thisTimeClaimProductList = ocClaimAmountVO.getOrderAllClaimProductList().stream()
				.filter(x -> UtilsText.equals(x.getClmNo(), clmNo)).collect(Collectors.toList());

		/*
		 * 현재 클레임 상품 목록의 금액 정보를 매출기준 리오더 금액으로 변경(다족구매로 인한 주문금액 변경 고려)
		 */
		for (OcClaimProduct thisTimeClaimProduct : thisTimeClaimProductList) {
			OcOrderProduct reOrderSalesProduct = ocClaimAmountVO.getReOrderProductSalesList().stream()
					.filter(x -> UtilsText.equals(String.valueOf(x.getOrderPrdtSeq()),
							String.valueOf(thisTimeClaimProduct.getOrderPrdtSeq())))
					.max(Comparator.comparing(OcOrderProduct::getOrderNo)).orElse(null);

			if (reOrderSalesProduct != null) {
				thisTimeClaimProduct.setTotalDscntAmt(reOrderSalesProduct.getTotalDscntAmt());
				thisTimeClaimProduct.setOrderAmt(reOrderSalesProduct.getOrderAmt());
			}
		}

		ocClaimAmountVO.setThisTimeClaimProductList(thisTimeClaimProductList); // 다시 set

		/**
		 * 폼에서 임의로 취소 설정금액을 변경한 경우 해당 금액으로 취소금액 히스토리 및 정보 변경
		 */
		if (UtilsText.equals(ocClaim.getChngInputCnclAmtYn(), Const.BOOLEAN_TRUE)) {
			this.setChngInputCnclAmt(ocClaim, ocClaimAmountVO, ocClaimPayments);
		}

		/*
		 * 주문 마스터/상품 신규(매출취소 리오더) 등록
		 */
		OcOrder reOrder = new OcOrder();
		BeanUtils.copyProperties(ocOrder, reOrder); // 내용 복사

		String reOrderNo = orderService.createOrderSeq(); // 신규 주문번호

		ocClaim.setReOrderNo(reOrderNo); // 클레임 정보에도 신규주문번호 set

		// 리오더 주문에 들어갈 주문 합계금 컬럼 값 관련
		int _totalNormalAmt = ocClaimAmountVO.getThisTimeClaimProductList().stream().mapToInt(o -> o.getPrdtNormalAmt())
				.sum();
		int _totalSellAmt = ocClaimAmountVO.getThisTimeClaimProductList().stream().mapToInt(o -> o.getPrdtSellAmt())
				.sum();
		int _pymntTodoAmt = ocClaimAmountVO.getThisTimeClaimProductList().stream().mapToInt(o -> o.getOrderAmt()).sum();
		int _pymntAmt = ocClaimAmountVO.getThisTimeClaimProductList().stream().mapToInt(o -> o.getOrderAmt()).sum();

		reOrder.setOrderNo(reOrderNo); // 신규주문번호
		reOrder.setClmNo(ocClaim.getClmNo()); // 클레임번호 set
		reOrder.setTotalNormalAmt(_totalNormalAmt); // 정상가총액
		reOrder.setTotalSellAmt(_totalSellAmt); // 판매가총액
		reOrder.setTotalPromoDscntAmt(0); // 프로모션할인총액
		reOrder.setTotalCpnDscntAmt(0); // 쿠폰할인총액
		reOrder.setTotalEmpDscntAmt(0); // 임직원할인총액
		reOrder.setPointUseAmt(0); // 포인트사용액
		reOrder.setEventPointUseAmt(0); // 이벤트포인트사용액
		reOrder.setMmnyDlvyAmt(ocClaimAmountVO.getThisTimeClaimProductList().stream()
				.filter(x -> UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
				.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_TRUE)).mapToInt(o -> o.getOrderAmt())
				.sum()); // 자사배송비
		reOrder.setTotalVndrDlvyAmt(ocClaimAmountVO.getThisTimeClaimProductList().stream()
				.filter(x -> UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
				.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_FALSE)).mapToInt(o -> o.getOrderAmt())
				.sum()); // 입점사배송비총액
		reOrder.setPymntTodoAmt(_pymntTodoAmt); // 결제예정금액
		reOrder.setPymntAmt(_pymntAmt); // 결제금액
		reOrder.setCnclAmt(0); // 취소금액
		reOrder.setSalesCnclGbnType(CommonCode.SALES_CNCL_GBN_TYPE_CANCEL); // 매출취소구분 : 취소
		// reOrder.setOrderStatCode(); // 부분취소 시 상태 값은 생성 시 지정하지 않음
		reOrder.setRgsterNo(ocClaim.getClaimRgsterNo());
		reOrder.setModerNo(ocClaim.getClaimRgsterNo());

		// 신규 주문(매출취소 리오더) 생성
		ocOrderDao.insertOrder(reOrder);

		// 신규 주문상품(매출취소 리오더) 생성
		for (OcClaimProduct claimProduct : ocClaimAmountVO.getThisTimeClaimProductList()) {
			OcOrderProduct reOrderProduct = new OcOrderProduct();

			OcOrderProduct orderProduct = ocClaimAmountVO.getOrgOrderProductList().stream()
					.filter(x -> UtilsText.equals(claimProduct.getOrderNo(), x.getOrderNo()) && UtilsText.equals(
							String.valueOf(claimProduct.getOrderPrdtSeq()), String.valueOf(x.getOrderPrdtSeq())))
					.findFirst().orElse(null);

			BeanUtils.copyProperties(orderProduct, reOrderProduct); // 내용 복사

			reOrderProduct.setOrderNo(reOrderNo); // 신규 생성 주문번호
			reOrderProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_ACCEPT); // 주문상품상태코드:취소접수
			reOrderProduct.setTotalDscntAmt(claimProduct.getTotalDscntAmt()); // 클레임 상품의 조정된 금액으로 다시 set
			reOrderProduct.setOrderAmt(claimProduct.getOrderAmt()); // 클레임 상품의 조정된 금액으로 다시 set
			reOrderProduct.setRgsterNo(ocClaim.getClaimRgsterNo());
			reOrderProduct.setModerNo(ocClaim.getClaimRgsterNo());

			// 신규 리오더 주문상품 생성
			ocOrderProductDao.insertOrderProduct(reOrderProduct);
		}

		// 클레임상품 클레임생성 리오더 주문번호 업데이트
		OcClaimProduct reOrderUpdateClaimProduct = new OcClaimProduct();

		reOrderUpdateClaimProduct.setModerNo(ocClaim.getClaimRgsterNo());
		reOrderUpdateClaimProduct.setClmCrtOrderNo(reOrderNo);
		reOrderUpdateClaimProduct.setClmNo(ocClaim.getClmNo());

		ocClaimProductDao.updateClmCrtOrderNo(reOrderUpdateClaimProduct);

		/*
		 * 다족구매로 인해 재조정 대상 상품이 있는 경우 신규(매출취소 리오더) 등록
		 */
		if (!ObjectUtils.isEmpty(ocClaimAmountVO.getBeforeAdjustOrderProductByMultiBuyList())) {
			/*
			 * 주문 마스터 등록(다족구매 주문금액 변경으로 인한 매출취소)
			 */
			OcOrder multiCancelOrder = new OcOrder();
			BeanUtils.copyProperties(ocOrder, multiCancelOrder); // 내용 복사

			String multiCancelOrderNo = orderService.createOrderSeq(); // 신규 주문번호

			multiCancelOrder.setOrderNo(multiCancelOrderNo); // 신규주문번호
			multiCancelOrder.setClmNo(ocClaim.getClmNo()); // 클레임번호 set
			multiCancelOrder.setTotalNormalAmt(ocClaimAmountVO.getBeforeAdjustOrderProductByMultiBuyList().stream()
					.mapToInt(o -> o.getPrdtNormalAmt()).sum()); // 정상가총액
			multiCancelOrder.setTotalSellAmt(ocClaimAmountVO.getBeforeAdjustOrderProductByMultiBuyList().stream()
					.mapToInt(o -> o.getPrdtSellAmt()).sum()); // 판매가총액
			multiCancelOrder.setTotalPromoDscntAmt(0); // 프로모션할인총액
			multiCancelOrder.setTotalCpnDscntAmt(0); // 쿠폰할인총액
			multiCancelOrder.setTotalEmpDscntAmt(0); // 임직원할인총액
			multiCancelOrder.setPointUseAmt(0); // 포인트사용액
			multiCancelOrder.setEventPointUseAmt(0); // 이벤트포인트사용액
			multiCancelOrder.setMmnyDlvyAmt(0); // 자사배송비
			multiCancelOrder.setTotalVndrDlvyAmt(0); // 입점사배송비총액
			multiCancelOrder.setPymntTodoAmt(ocClaimAmountVO.getBeforeAdjustOrderProductByMultiBuyList().stream()
					.mapToInt(o -> o.getOrderAmt()).sum()); // 결제예정금액
			multiCancelOrder.setPymntAmt(ocClaimAmountVO.getBeforeAdjustOrderProductByMultiBuyList().stream()
					.mapToInt(o -> o.getOrderAmt()).sum()); // 결제금액
			multiCancelOrder.setCnclAmt(0); // 취소금액
			multiCancelOrder.setSalesCnclGbnType(CommonCode.SALES_CNCL_GBN_TYPE_MULTIBUY_CANCEL); // 다족구매 매출취소(매가변경)
			multiCancelOrder.setOrderStatCode(CommonCode.ORDER_STAT_CODE_CANCEL_COMPLETE); // 주문상태코드 : 전체취소완료
			multiCancelOrder.setRgsterNo(ocClaim.getClaimRgsterNo());
			multiCancelOrder.setModerNo(ocClaim.getClaimRgsterNo());

			// 신규 주문(매출 리오더) 생성
			ocOrderDao.insertOrder(multiCancelOrder);

			for (OcOrderProduct cancelMultiBuyOrderProduct : ocClaimAmountVO
					.getBeforeAdjustOrderProductByMultiBuyList()) {
				cancelMultiBuyOrderProduct.setOrderNo(multiCancelOrderNo); // 신규 생성 주문번호
				cancelMultiBuyOrderProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE); // 주문상품상태코드:취소완료
				cancelMultiBuyOrderProduct.setExcclcDcsnYmd(new Timestamp(new Date().getTime())); // 정산확정일자를 넣는다
				cancelMultiBuyOrderProduct.setRgsterNo(ocClaim.getClaimRgsterNo());
				cancelMultiBuyOrderProduct.setModerNo(ocClaim.getClaimRgsterNo());

				// 신규 리오더 주문상품 생성
				ocOrderProductDao.insertOrderProduct(cancelMultiBuyOrderProduct);

				// 원 주문의 주문상품이력 등록(취소완료)
				OcOrderProductHistory orgOrderProductHistory = new OcOrderProductHistory();
				orgOrderProductHistory.setOrderNo(cancelMultiBuyOrderProduct.getOrgOrderNo());
				orgOrderProductHistory.setOrderPrdtSeq(cancelMultiBuyOrderProduct.getOrderPrdtSeq());
				orgOrderProductHistory.setPrdtNo(cancelMultiBuyOrderProduct.getPrdtNo());
				orgOrderProductHistory.setPrdtOptnNo(cancelMultiBuyOrderProduct.getPrdtOptnNo());
				orgOrderProductHistory.setPrdtName(cancelMultiBuyOrderProduct.getPrdtName());
				orgOrderProductHistory.setEngPrdtName(cancelMultiBuyOrderProduct.getEngPrdtName());
				orgOrderProductHistory.setOptnName(cancelMultiBuyOrderProduct.getOptnName());
				orgOrderProductHistory.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE); // 주문상품상태코드:취소완료
				orgOrderProductHistory.setNoteText("다족구매 재조정 취소");
				orgOrderProductHistory.setRgsterNo(ocClaim.getClaimRgsterNo());

				ocOrderProductHistoryDao.insertProductHistory(orgOrderProductHistory); // 주문상품이력 생성
			}
		}

		/*
		 * 다족구매로 인해 재조정 된 대상 상품이 있는 경우 새로운 매출용 주문 리오더 등록
		 */
		if (!ObjectUtils.isEmpty(ocClaimAmountVO.getAdjustedOrderProductByMultiBuyList())) {
			/*
			 * 주문 마스터 등록(다족구매 주문금액 변경으로 인한 매출)
			 */
			OcOrder reSalesOrder = new OcOrder();
			BeanUtils.copyProperties(ocOrder, reSalesOrder); // 내용 복사

			String reSalesOrderNo = orderService.createOrderSeq(); // 신규 주문번호

			reSalesOrder.setOrderNo(reSalesOrderNo); // 신규주문번호
			reSalesOrder.setClmNo(ocClaim.getClmNo()); // 클레임번호 set
			reSalesOrder.setTotalNormalAmt(ocClaimAmountVO.getAdjustedOrderProductByMultiBuyList().stream()
					.mapToInt(o -> o.getPrdtNormalAmt()).sum()); // 정상가총액
			reSalesOrder.setTotalSellAmt(ocClaimAmountVO.getAdjustedOrderProductByMultiBuyList().stream()
					.mapToInt(o -> o.getPrdtSellAmt()).sum()); // 판매가총액
			reSalesOrder.setTotalPromoDscntAmt(0); // 프로모션할인총액
			reSalesOrder.setTotalCpnDscntAmt(0); // 쿠폰할인총액
			reSalesOrder.setTotalEmpDscntAmt(0); // 임직원할인총액
			reSalesOrder.setPointUseAmt(0); // 포인트사용액
			reSalesOrder.setEventPointUseAmt(0); // 이벤트포인트사용액
			reSalesOrder.setMmnyDlvyAmt(0); // 자사배송비
			reSalesOrder.setTotalVndrDlvyAmt(0); // 입점사배송비총액
			reSalesOrder.setPymntTodoAmt(ocClaimAmountVO.getAdjustedOrderProductByMultiBuyList().stream()
					.mapToInt(o -> o.getOrderAmt()).sum()); // 결제예정금액
			reSalesOrder.setPymntAmt(ocClaimAmountVO.getAdjustedOrderProductByMultiBuyList().stream()
					.mapToInt(o -> o.getOrderAmt()).sum()); // 결제금액
			reSalesOrder.setCnclAmt(0); // 취소금액
			reSalesOrder.setSalesCnclGbnType(CommonCode.SALES_CNCL_GBN_TYPE_MULTIBUY_SALE); // 다족구매 매출(매가변경)
			reSalesOrder.setOrderStatCode(CommonCode.ORDER_STAT_CODE_COMPLETE); // 주문상태코드 : 결제완료
			reSalesOrder.setRgsterNo(ocClaim.getClaimRgsterNo());
			reSalesOrder.setModerNo(ocClaim.getClaimRgsterNo());

			// 신규 주문(매출 리오더) 생성
			ocOrderDao.insertOrder(reSalesOrder);

			/*
			 * 다족구매로 인해 재조정 되는 대상 상품이 있는 경우 신규(매출 리오더) 등록
			 */
			for (OcOrderProduct salesMultiBuyOrderProduct : ocClaimAmountVO.getAdjustedOrderProductByMultiBuyList()) {
				salesMultiBuyOrderProduct.setOrderNo(reSalesOrderNo); // 신규 생성 주문번호
				salesMultiBuyOrderProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_COMPLETE); // 주문상품상태코드:결제완료
				salesMultiBuyOrderProduct.setExcclcDcsnYmd(new Timestamp(new Date().getTime())); // 정산확정일자를 넣는다
				salesMultiBuyOrderProduct.setRgsterNo(ocClaim.getClaimRgsterNo());
				salesMultiBuyOrderProduct.setModerNo(ocClaim.getClaimRgsterNo());

				// 신규 리오더 주문상품 생성
				ocOrderProductDao.insertOrderProduct(salesMultiBuyOrderProduct);

				// 원 주문의 주문상품이력 등록(매출기준 결제완료)
				OcOrderProductHistory orgOrderProductHistory = new OcOrderProductHistory();
				orgOrderProductHistory.setOrderNo(salesMultiBuyOrderProduct.getOrgOrderNo());
				orgOrderProductHistory.setOrderPrdtSeq(salesMultiBuyOrderProduct.getOrderPrdtSeq());
				orgOrderProductHistory.setPrdtNo(salesMultiBuyOrderProduct.getPrdtNo());
				orgOrderProductHistory.setPrdtOptnNo(salesMultiBuyOrderProduct.getPrdtOptnNo());
				orgOrderProductHistory.setPrdtName(salesMultiBuyOrderProduct.getPrdtName());
				orgOrderProductHistory.setEngPrdtName(salesMultiBuyOrderProduct.getEngPrdtName());
				orgOrderProductHistory.setOptnName(salesMultiBuyOrderProduct.getOptnName());
				orgOrderProductHistory.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_COMPLETE); // 주문상품상태코드:결제완료
				orgOrderProductHistory.setNoteText("다족구매 재조정 매출");
				orgOrderProductHistory.setRgsterNo(ocClaim.getClaimRgsterNo());

				ocOrderProductHistoryDao.insertProductHistory(orgOrderProductHistory); // 주문상품이력 생성
			}
		}

		/*
		 * 다족구매로 인해 재조정 되는 프로모션 변경 정보 등록
		 */
		if (!ObjectUtils.isEmpty(ocClaimAmountVO.getAdjustedClaimProductApplyPromotionList())) {
			for (OcClaimProductApplyPromotion claimProductApplyPromotion : ocClaimAmountVO
					.getAdjustedClaimProductApplyPromotionList()) {

				// 클레임상품적용프로모션 등록
				ocClaimProductApplyPromotionDao.insert(claimProductApplyPromotion);
			}
		}

		/*
		 * 품절보상 쿠폰 지급 처리
		 */
		Date orderDtm = new Date(ocOrder.getOrderDtm().getTime());
		Date compareDate = DateUtils.addDays(new Date(), -3);

		// 주문일 3일 경과 기준, 클레임 사유 품절, 임직원아닌 회원 주문 상품
		if (compareDate.compareTo(orderDtm) >= 0
				&& UtilsText.equals(ocClaim.getOcClaimProductList().get(0).getClmRsnCode(),
						CommonCode.CLM_RSN_CODE_CANCEL_SOLDOUT)
				&& UtilsText.equals(ocOrder.getEmpYn(), Const.BOOLEAN_FALSE)
				&& (UtilsText.equals(ocOrder.getMemberTypeCode(), CommonCode.MEMBER_TYPE_CODE_ONLINE)
						|| UtilsText.equals(ocOrder.getMemberTypeCode(), CommonCode.MEMBER_TYPE_CODE_MEMBERSHIP))) {
			// 클레임 대상 상품 수량만큼 지급
			claimProcService.setGiveSoldOutCmpns(ocClaim, ocClaim.getOcClaimProductList());
		}

		/*
		 * 사용 쿠폰 재 발급, 주문 사용쿠폰 클레임번호 업데이트
		 */
		// 사은품, 배송비 제외한 클레임순번 배열
		Object[] claimOrderPrdtSeqs = ocClaimAmountVO.getThisTimeClaimProductList().stream()
				.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
						&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
				.map(OcClaimProduct::getOrderPrdtSeq).toArray();
		;

		// 원주문 사용 쿠폰 목록에서 현재 클레임 대상 주문상품에 해당하는 쿠폰사용 정보 목록(이전 클레임으로 인한 항목 제외)
		List<OcOrderUseCoupon> claimProductUserCouponList = ocClaimAmountVO.getOrderUseCouponList().stream()
				.filter(x -> Arrays.asList(claimOrderPrdtSeqs).contains(x.getOrderPrdtSeq()))
				.filter(x -> UtilsText.equals(x.getClmNo(), null)).collect(Collectors.toList());

		// 원주문 사용 쿠폰 목록에서 현재 클레임 대상 주문상품을 제외한 남은 상품 쿠폰사용 정보 목록(이전 클레임으로 인한 항목 제외)
		List<OcOrderUseCoupon> excludeClaimProductUserCouponList = ocClaimAmountVO.getOrderUseCouponList().stream()
				.filter(x -> !Arrays.asList(claimOrderPrdtSeqs).contains(x.getOrderPrdtSeq()))
				.filter(x -> UtilsText.equals(x.getClmNo(), null)).collect(Collectors.toList());

		// 현재 클레임 대상 중복 쿠폰 제거
		List<Integer> claimHoldCpnSeqs = claimProductUserCouponList.stream().map(OcOrderUseCoupon::getHoldCpnSeq)
				.distinct().collect(Collectors.toList());

		// 재 발급한 쿠폰 목록(체크용)
		List<Integer> reIssueHoldCpnSeq = new ArrayList<Integer>();

		// 이전 클레임 부분 취소 후 남은 상품 모두 취소인 경우
		if (ocClaimAmountVO.isRemainAllCancelYn()) {
			// 더블적립 쿠폰 처리(주문에 걸린 쿠폰이므로 상품기준과 다르게 별도 처리)
			if (ocClaimAmountVO.getOrderDoubleDscntCpnInfo() != null) {
				OcOrderUseCoupon orderUseCoupon = new OcOrderUseCoupon();
				orderUseCoupon.setOrderNo(ocClaim.getOrderNo());
				orderUseCoupon.setClmNo(ocClaim.getClmNo());
				orderUseCoupon.setHoldCpnSeq(ocClaimAmountVO.getOrderDoubleDscntCpnInfo().getHoldCpnSeq());

				ocOrderUseCouponDao.updateOrderUseCouponClmNo(orderUseCoupon); // 주문 사용쿠폰 클레임번호 업데이트

				MbMemberCoupon reIssueMemberCoupon = new MbMemberCoupon();
				reIssueMemberCoupon.setMemberNo(ocOrder.getMemberNo());
				reIssueMemberCoupon.setHoldCpnSeq(ocClaimAmountVO.getOrderDoubleDscntCpnInfo().getHoldCpnSeq());
				reIssueMemberCoupon.setReIssueRsnText("주문취소 재 발급");
				reIssueMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_REISSUANCE);
				reIssueMemberCoupon.setRgsterNo(Const.SYSTEM_ADMIN_NO);
				reIssueMemberCoupon.setModerNo(Const.SYSTEM_ADMIN_NO);

				mbMemberCouponDao.insertMemberCouponReIssue(reIssueMemberCoupon); // 쿠폰 재 발급
			}

			// 상품단위에 걸린 holdCpnSeq 기준으로 재 발급
			for (Integer holdCpnSeq : claimHoldCpnSeqs) {
				OcOrderUseCoupon orderUseCoupon = new OcOrderUseCoupon();
				orderUseCoupon.setOrderNo(ocClaim.getOrderNo());
				orderUseCoupon.setClmNo(ocClaim.getClmNo());
				orderUseCoupon.setHoldCpnSeq(holdCpnSeq);

				ocOrderUseCouponDao.updateOrderUseCouponClmNo(orderUseCoupon); // 주문 사용쿠폰 클레임번호 업데이트

				MbMemberCoupon reIssueMemberCoupon = new MbMemberCoupon();
				reIssueMemberCoupon.setMemberNo(ocOrder.getMemberNo());
				reIssueMemberCoupon.setHoldCpnSeq(holdCpnSeq);
				reIssueMemberCoupon.setReIssueRsnText("주문취소 재 발급");
				reIssueMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_REISSUANCE);
				reIssueMemberCoupon.setRgsterNo(Const.SYSTEM_ADMIN_NO);
				reIssueMemberCoupon.setModerNo(Const.SYSTEM_ADMIN_NO);

				mbMemberCouponDao.insertMemberCouponReIssue(reIssueMemberCoupon); // 쿠폰 재 발급
			}
		} else {
			for (OcOrderUseCoupon claimProductUserCoupon : claimProductUserCouponList) {
				// 취소대상 상품에 적용된 쿠폰이 할인유형 쿠폰인 경우
				if (UtilsText.equals(claimProductUserCoupon.getCpnTypeCode(), CommonCode.CPN_TYPE_CODE_DISCOUNT)
						|| UtilsText.equals(claimProductUserCoupon.getCpnTypeCode(),
								CommonCode.CPN_TYPE_CODE_DISCOUNT_AFFILIATES)
						|| UtilsText.equals(claimProductUserCoupon.getCpnTypeCode(),
								CommonCode.CPN_TYPE_CODE_DOUBLE_POINT)) {

					// 취소대상이 아닌 상품에 적용된 동일 할인유형 쿠폰번호 존재 확인
					long existCpnCnt = excludeClaimProductUserCouponList.stream()
							.filter(x -> UtilsText.equals(String.valueOf(claimProductUserCoupon.getHoldCpnSeq()),
									String.valueOf(x.getHoldCpnSeq())))
							.count();

					OcOrderUseCoupon orderUseCoupon = new OcOrderUseCoupon();
					orderUseCoupon.setOrderNo(ocClaim.getOrderNo());
					orderUseCoupon.setClmNo(ocClaim.getClmNo());
					orderUseCoupon.setOrderUseCpnSeq(claimProductUserCoupon.getOrderUseCpnSeq());

					ocOrderUseCouponDao.updateOrderUseCouponClmNo(orderUseCoupon); // 주문 사용쿠폰 클레임번호 업데이트

					// 남아있는 상품에 동일 할인쿠폰이 없는 경우에만 쿠폰 재 발급
					if (existCpnCnt == 0) {
						// 재 발급한 쿠폰 목록에 없다면 재 발급 진행
						if (!reIssueHoldCpnSeq.contains(claimProductUserCoupon.getHoldCpnSeq())) {
							MbMemberCoupon reIssueMemberCoupon = new MbMemberCoupon();
							reIssueMemberCoupon.setMemberNo(ocOrder.getMemberNo());
							reIssueMemberCoupon.setHoldCpnSeq(claimProductUserCoupon.getHoldCpnSeq());
							reIssueMemberCoupon.setReIssueRsnText("주문취소 재 발급");
							reIssueMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_REISSUANCE);
							reIssueMemberCoupon.setRgsterNo(Const.SYSTEM_ADMIN_NO);
							reIssueMemberCoupon.setModerNo(Const.SYSTEM_ADMIN_NO);

							mbMemberCouponDao.insertMemberCouponReIssue(reIssueMemberCoupon); // 쿠폰 재 발급
							reIssueHoldCpnSeq.add(claimProductUserCoupon.getHoldCpnSeq()); // 재 발급 쿠폰 목록에 추가
						}
					}
				}
			}
		}

		// 환불 대상 배송비가 발생한 경우 해당 배송비 상품에 적용된 쿠폰 재 발급(업체번호 확인을 위해 별도로 처리)
		if (!ObjectUtils.isEmpty(ocClaimAmountVO.getCancelDlvyProductList())) {
			for (OcOrderProduct orderProduct : ocClaimAmountVO.getCancelDlvyProductList()) {
				// 배송비무료타입, 업체번호를 확인하여 업체별로 적용된 쿠폰을 추출
				OcOrderUseCoupon dlvyFreeCpn = claimProductUserCouponList.stream()
						.filter(x -> UtilsText.equals(x.getCpnTypeCode(), CommonCode.CPN_TYPE_CODE_FREE_DELIVERY))
						.filter(x -> UtilsText.equals(x.getVndrNo(), orderProduct.getVndrNo())).findFirst()
						.orElse(null);

				if (dlvyFreeCpn != null) {
					OcOrderUseCoupon orderUseCoupon = new OcOrderUseCoupon();
					orderUseCoupon.setOrderNo(ocClaim.getOrderNo());
					orderUseCoupon.setClmNo(ocClaim.getClmNo());
					orderUseCoupon.setHoldCpnSeq(dlvyFreeCpn.getHoldCpnSeq());

					ocOrderUseCouponDao.updateOrderUseCouponClmNo(orderUseCoupon); // 주문 사용쿠폰 클레임번호 업데이트

					MbMemberCoupon reIssueMemberCoupon = new MbMemberCoupon();
					reIssueMemberCoupon.setMemberNo(ocOrder.getMemberNo());
					reIssueMemberCoupon.setHoldCpnSeq(dlvyFreeCpn.getHoldCpnSeq());
					reIssueMemberCoupon.setReIssueRsnText("주문취소 재 발급");
					reIssueMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_REISSUANCE);
					reIssueMemberCoupon.setRgsterNo(Const.SYSTEM_ADMIN_NO);
					reIssueMemberCoupon.setModerNo(Const.SYSTEM_ADMIN_NO);

					mbMemberCouponDao.insertMemberCouponReIssue(reIssueMemberCoupon); // 쿠폰 재 발급
				}
			}
		}

		/*
		 * 주문상품 프로모션 클레임번호 업데이트
		 */
		for (OcClaimProduct claimProduct : thisTimeClaimProductList) {
			OcOrderProductApplyPromotion ocOrderProductApplyPromotion = new OcOrderProductApplyPromotion();
			ocOrderProductApplyPromotion.setOrderNo(claimProduct.getOrderNo());
			ocOrderProductApplyPromotion.setClmNo(claimProduct.getClmNo());
			ocOrderProductApplyPromotion.setOrderPrdtSeq(claimProduct.getOrderPrdtSeq());

			ocOrderProductApplyPromotionDao.updateOrderProductApplyPromotionClmNo(ocOrderProductApplyPromotion);
		}

		/*
		 * 주문배송이력 상태 변경
		 */
		for (OcClaimProduct claimProduct : thisTimeClaimProductList) {
			OcOrderDeliveryHistory orderDeliveryHistory = new OcOrderDeliveryHistory();
			orderDeliveryHistory.setOrderNo(ocClaim.getOrderNo());
			orderDeliveryHistory.setOrderPrdtSeq(claimProduct.getOrderPrdtSeq());
			orderDeliveryHistory.setDlvyStatCode(CommonCode.DLVY_STAT_CODE_DELIVERY_CANCEL); // 배송 상태 코드 : 배송취소
			orderDeliveryHistory.setRgsterNo(ocClaim.getClaimRgsterNo());
			orderDeliveryHistory.setModerNo(ocClaim.getClaimRgsterNo());
			ocOrderDeliveryHistoryDao.updateOrderDeliveryHistoryStat(orderDeliveryHistory);
		}

		/*
		 * 환수 주문배송비 매출 리오더 등록(업체별) - 히스토리는 접수 시 이미 등록
		 */
		// 환수 주문배송비가 발생한 경우 매출 리오더, 이력용 결제 등록
		if (!ObjectUtils.isEmpty(ocClaimAmountVO.getVndrRedempDlvyPaymentList())) {
			/*
			 * [신규 주문 생성] - 환수배송비 매출 주문
			 */
			OcOrder reOrderRedempDlvy = new OcOrder();
			BeanUtils.copyProperties(ocOrder, reOrderRedempDlvy); // 내용 복사

			String reOrderNoRedempDlvy = orderService.createOrderSeq(); // 신규 주문번호

			// 리오더 주문에 들어갈 주문 합계금 컬럼 값 관련
			int _totalNormalAmtRedempDlvy = ocClaimAmountVO.getRedempDlvyProductList().stream()
					.mapToInt(o -> o.getPrdtNormalAmt()).sum();
			int _totalSellAmtRedempDlvy = ocClaimAmountVO.getRedempDlvyProductList().stream()
					.mapToInt(o -> o.getPrdtSellAmt()).sum();
			int _pymntTodoAmtRedempDlvy = ocClaimAmountVO.getRedempDlvyProductList().stream()
					.mapToInt(o -> o.getOrderAmt()).sum();
			int _pymntAmtRedempDlvy = ocClaimAmountVO.getRedempDlvyProductList().stream().mapToInt(o -> o.getOrderAmt())
					.sum();

			reOrderRedempDlvy.setOrderNo(reOrderNoRedempDlvy); // 신규주문번호
			reOrderRedempDlvy.setClmNo(ocClaim.getClmNo()); // 클레임번호 set
			reOrderRedempDlvy.setTotalNormalAmt(_totalNormalAmtRedempDlvy); // 정상가총액
			reOrderRedempDlvy.setTotalSellAmt(_totalSellAmtRedempDlvy); // 판매가총액
			reOrderRedempDlvy.setTotalPromoDscntAmt(0); // 프로모션할인총액
			reOrderRedempDlvy.setTotalCpnDscntAmt(0); // 쿠폰할인총액
			reOrderRedempDlvy.setTotalEmpDscntAmt(0); // 임직원할인총액
			reOrderRedempDlvy.setPointUseAmt(0); // 포인트사용액
			reOrderRedempDlvy.setEventPointUseAmt(0); // 이벤트포인트사용액
			reOrderRedempDlvy.setMmnyDlvyAmt(0); // 자사배송비
			reOrderRedempDlvy.setTotalVndrDlvyAmt(0); // 입점사배송비총액
			reOrderRedempDlvy.setPymntTodoAmt(_pymntTodoAmtRedempDlvy); // 결제예정금액
			reOrderRedempDlvy.setPymntAmt(_pymntAmtRedempDlvy); // 결제금액
			reOrderRedempDlvy.setCnclAmt(0); // 취소금액
			reOrderRedempDlvy.setSalesCnclGbnType(CommonCode.SALES_CNCL_GBN_TYPE_REDEMP_DLVY_AMT); // 매출취소구분 - 환수배송비
			reOrderRedempDlvy.setOrderStatCode(CommonCode.ORDER_STAT_CODE_COMPLETE); // 주문상태 - 결제완료
			reOrderRedempDlvy.setRgsterNo(ocClaim.getClaimRgsterNo());
			reOrderRedempDlvy.setModerNo(ocClaim.getClaimRgsterNo());

			ocOrderDao.insertOrder(reOrderRedempDlvy); // 신규 주문 생성

			/*
			 * [신규 주문상품 생성] - 환수배송비 매출 상품
			 */
			for (OcOrderProduct redempDlvyProduct : ocClaimAmountVO.getRedempDlvyProductList()) {
				redempDlvyProduct.setOrderNo(reOrderNoRedempDlvy); // 생성된 주문번호로 set
				ocOrderProductDao.insertOrderProduct(redempDlvyProduct); // 신규 주문상품 생성
			}
		}

		/*
		 * 남은 상품 전체 취소인 경우 기존 환수된 배송비를 매출 취소로 리오더 생성 - 히스토리는 접수 시 이미 등록
		 */
		if (ocClaimAmountVO.isRemainAllCancelYn()) {
			if (!ObjectUtils.isEmpty(ocClaimAmountVO.getPreviousRedempDlvyProductList())) {
				/*
				 * [신규 주문 생성] - 환수배송비 매출취소 주문
				 */
				OcOrder reOrderPreviousRedempDlvy = new OcOrder();
				BeanUtils.copyProperties(ocOrder, reOrderPreviousRedempDlvy); // 내용 복사

				String reOrderNoPreviousRedempDlvy = orderService.createOrderSeq(); // 신규 주문번호

				// 리오더 주문에 들어갈 주문 합계금 컬럼 값 관련
				int _totalNormalAmtRedempDlvy = ocClaimAmountVO.getPreviousRedempDlvyProductList().stream()
						.mapToInt(o -> o.getPrdtNormalAmt()).sum();
				int _totalSellAmtRedempDlvy = ocClaimAmountVO.getPreviousRedempDlvyProductList().stream()
						.mapToInt(o -> o.getPrdtSellAmt()).sum();
				int _pymntTodoAmtRedempDlvy = ocClaimAmountVO.getPreviousRedempDlvyProductList().stream()
						.mapToInt(o -> o.getOrderAmt()).sum();
				int _pymntAmtRedempDlvy = ocClaimAmountVO.getPreviousRedempDlvyProductList().stream()
						.mapToInt(o -> o.getOrderAmt()).sum();

				reOrderPreviousRedempDlvy.setOrderNo(reOrderNoPreviousRedempDlvy); // 신규주문번호
				reOrderPreviousRedempDlvy.setClmNo(ocClaim.getClmNo()); // 클레임번호 set
				reOrderPreviousRedempDlvy.setTotalNormalAmt(_totalNormalAmtRedempDlvy); // 정상가총액
				reOrderPreviousRedempDlvy.setTotalSellAmt(_totalSellAmtRedempDlvy); // 판매가총액
				reOrderPreviousRedempDlvy.setTotalPromoDscntAmt(0); // 프로모션할인총액
				reOrderPreviousRedempDlvy.setTotalCpnDscntAmt(0); // 쿠폰할인총액
				reOrderPreviousRedempDlvy.setTotalEmpDscntAmt(0); // 임직원할인총액
				reOrderPreviousRedempDlvy.setPointUseAmt(0); // 포인트사용액
				reOrderPreviousRedempDlvy.setEventPointUseAmt(0); // 이벤트포인트사용액
				reOrderPreviousRedempDlvy.setMmnyDlvyAmt(0); // 자사배송비
				reOrderPreviousRedempDlvy.setTotalVndrDlvyAmt(0); // 입점사배송비총액
				reOrderPreviousRedempDlvy.setPymntTodoAmt(_pymntTodoAmtRedempDlvy); // 결제예정금액
				reOrderPreviousRedempDlvy.setPymntAmt(_pymntAmtRedempDlvy); // 결제금액
				reOrderPreviousRedempDlvy.setCnclAmt(0); // 취소금액
				reOrderPreviousRedempDlvy.setSalesCnclGbnType(CommonCode.SALES_CNCL_GBN_TYPE_REDEMP_DLVY_AMT_CANCEL); // 매출취소구분-환수배송비
				reOrderPreviousRedempDlvy.setOrderStatCode(CommonCode.ORDER_STAT_CODE_CANCEL_COMPLETE); // 주문상태 - 전체취소완료
				reOrderPreviousRedempDlvy.setRgsterNo(ocClaim.getClaimRgsterNo());
				reOrderPreviousRedempDlvy.setModerNo(ocClaim.getClaimRgsterNo());

				ocOrderDao.insertOrder(reOrderPreviousRedempDlvy); // 신규 주문 생성

				/*
				 * [신규 주문상품 생성] - 환수배송비 매출 상품
				 */
				for (OcOrderProduct previousRedempDlvyProduct : ocClaimAmountVO.getPreviousRedempDlvyProductList()) {
					previousRedempDlvyProduct.setOrderNo(reOrderNoPreviousRedempDlvy); // 생성된 주문번호로 set
					previousRedempDlvyProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE); // 주문상품상태코드:취소완료
					previousRedempDlvyProduct.setExcclcDcsnYmd(new Timestamp(new Date().getTime())); // 정산확정일자를 넣는다
					ocOrderProductDao.insertOrderProduct(previousRedempDlvyProduct); // 신규 주문상품 생성
				}
			}
		}

		/*
		 * 임직원 주문인 경우 임직원 한도 복원
		 */
		if (UtilsText.equals(ocOrder.getEmpOrderYn(), Const.BOOLEAN_TRUE)) {
			if (UtilsText.equals(ocOrder.getEmpOrderYn(), Const.BOOLEAN_TRUE)) {
				String orderYearMonth = new SimpleDateFormat(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS)
						.format(ocOrder.getOrderDtm());
				String orderDate = new SimpleDateFormat(Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS)
						.format(ocOrder.getOrderDtm());
				String thisYearMonth = new SimpleDateFormat(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS)
						.format(new Date());

				// 주문월과 해당월이 같을 경우만 한도복원 인터페이스 호출
				if (UtilsText.equals(orderYearMonth, thisYearMonth)) {
					MbEmployeeCertificationHistory employeeInfo = mbEmployeeCertificationHistoryDao
							.selectEmpCertfiHistory(mbMember.getMemberNo());

					EmployPoint returnEmployPointResult = membershipPointService.savePointByEmployNum("1", orderDate,
							employeeInfo.getEmpNoText(), String.valueOf(ocClaimAmountVO.getRefundCnclAmt()),
							Const.SALE_EMP_CD);
				}
			}
		}

		/*
		 * 재고 변경, 주문 취소 인터페이스 호출, IF_ORDER 주문상태값 변경
		 */
		for (OcClaimProduct claimProduct : ocClaimAmountVO.getThisTimeClaimProductList()) {
			OcOrderProduct orderProduct = ocClaimAmountVO.getOrgOrderProductList().stream()
					.filter(x -> UtilsText.equals(claimProduct.getOrderNo(), x.getOrderNo()) && UtilsText.equals(
							String.valueOf(claimProduct.getOrderPrdtSeq()), String.valueOf(x.getOrderPrdtSeq())))
					.findFirst().orElse(null);

			// 2020.03.24 : BO,PO 에서 배송전 주문취소시 재고원복 X (레드마인 #1242)
			// 배송비 상품이 아닌 경우 재고 변경
//			if (!UtilsText.equals(orderProduct.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY)) {
//				// 예약 주문이 아닌 경우 재고 변경
//				if (UtilsText.equals(ocOrder.getRsvOrderYn(), Const.BOOLEAN_FALSE)) {
//					// 재고구분이 AI, VD 인 경우
//					if (UtilsText.equals(orderProduct.getStockGbnCode(), CommonCode.STOCK_GBN_CODE_AI)
//							|| UtilsText.equals(orderProduct.getStockGbnCode(), CommonCode.STOCK_GBN_CODE_VD)) {
//
//						// 2020.03.10 : '품절'로 인한 취소가 아닌 경우에만 재고반영 (레드마인 #1069)
//						if (!UtilsText.equals(claimProduct.getClmRsnCode(), CommonCode.CLM_RSN_CODE_CANCEL_SOLDOUT)) {
//
//							orderService.updateProductStockAdjust(orderProduct.getPrdtNo(),
//									orderProduct.getPrdtOptnNo(), 1, orderProduct.getStockGbnCode(), false);
//						}
//					}
//				}
//			}

			// 자사 상품인 경우만 취소 인터페이스 호출
			if (UtilsText.equals(orderProduct.getMmnyPrdtYn(), Const.BOOLEAN_TRUE)) {
				// 주문 취소 인터페이스 호출(사은품, 배송비 제외 - 사은품은 본 상품과 join된 데이터 사용)
				if (!UtilsText.equals(orderProduct.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
						&& !UtilsText.equals(orderProduct.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY)) {

					claimProcService.callCancelOrderInterface(orderProduct);
				}

				// IF_ORDER 주문상태값 변경
				if (UtilsText.isNotEmpty(orderProduct.getDlvyIdText())) {
					// ocClaimProductDao.updateInterfaceOrderCancel(orderProduct.getDlvyIdText());

					Map<String, String> paramMap = new HashMap<>();
					paramMap.put("dlvyIdText", orderProduct.getDlvyIdText());

					// 2020.02.07 : PR_ORDER_CANCEL 프로시져 호출로 변경
					String errorCode = this.setCallProcedureForOrderCancel(paramMap);
					log.error("callProcedureForOrderCancel return >>> " + errorCode);

					if (!UtilsText.equals(errorCode, "0")) {
						throw new Exception("validMsg:배송취소연동(PR_ORDER_CANCEL)에 실패하였습니다.");
					}
				} else {
					log.error("callProcedureForOrderCancel 실행안함 !!!!!!");
				}
			}
		}

		/*
		 * 결제취소
		 */
		// 결제취소 진행 시 환불 발생여부(refundOccurrence) ocClaimAmountVO 에 set
		claimProcService.setCancelPayment(ocClaimAmountVO, ocClaim, mbMember);

		/*
		 * 결제취소 처리 후 처리 주문, 클레임 상태 값 후 처리
		 */
		claimProcService.setClaimCancelAfterProc(ocClaim, ocClaimAmountVO);

		if (ocClaimAmountVO.isRefundOccurrence()) {
			resultMap.put("success", Const.BOOLEAN_FALSE);
			resultMap.put("message", "취소 처리는 완료되었으나\n\n결제취소 실패가 발생하여 환불접수 처리되었습니다.");
			resultMap.put("clmNo", ocClaim.getClmNo());
		} else {
			resultMap.put("success", Const.BOOLEAN_TRUE);
			resultMap.put("message", "주문이 정상적으로 취소되었습니다.");
			resultMap.put("clmNo", ocClaim.getClmNo());
		}

		// 이메일 , 카톡 문자 알림 서비스 시작
		try {
			claimMessageService.orderPartCancel(ocClaim);
		} catch (Exception e) {
			log.error("주문부분취소 메세지 메일 발송 실패 : " + e.getMessage());
		}

		return resultMap;
	}

	/**
	 * @Desc : 폼에서 임의로 취소 설정금액을 변경한 경우 해당 금액으로 취소금액 히스토리 및 정보 변경
	 * @Method Name : setChngInputCnclAmt
	 * @Date : 2019. 8. 25.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocClaimAmountVO
	 * @param ocClaimPayments
	 * @throws Exception
	 */
	public void setChngInputCnclAmt(OcClaim ocClaim, OcClaimAmountVO ocClaimAmountVO, OcClaimPayment[] ocClaimPayments)
			throws Exception {
		int refundCnclAmt = ocClaimAmountVO.getRefundCnclAmt(); // 실제 취소로 인해 환불될 금액(계산 금액)
		int chngInputCnclAmtSum = 0; // 폼에서 입력받은 취소 설정금액 sum

		// 원 주문 기준 결제수단별 결제금액 목록
		List<OcClaimPayment> orderClaimPaymentList = ocClaimAmountVO.getOrderClaimPaymentList();

		// 폼에서 입력 받은 결제 수단별 변경 금액
		List<OcClaimPayment> chngInputPaymentList = Arrays.asList(ocClaimPayments);

		chngInputCnclAmtSum = chngInputPaymentList.stream().mapToInt(o -> o.getPymntAmt()).sum();

		// 폼 입력금액 검증
		if (refundCnclAmt != chngInputCnclAmtSum) {
			throw new Exception("validMsg:실제 취소금액과 입력한 취소 설정금액이 다릅니다.");
		}

		/*
		 * 기존 주문금 환불 기준 데이터 삭제
		 */
		OcClaimPayment deleteClaimOrderAmtPayment = new OcClaimPayment();
		deleteClaimOrderAmtPayment.setClmNo(ocClaim.getClmNo());
		deleteClaimOrderAmtPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REFUND); // 환불
		deleteClaimOrderAmtPayment.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 재경처리 N
		deleteClaimOrderAmtPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_ORDER_AMT); // 주문금
		deleteClaimOrderAmtPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_HISTORY); // 히스토리

		ocClaimPaymentDao.deleteClaimPayment(deleteClaimOrderAmtPayment); // 클레임결제 삭제

		// 클레임 금액 계산 시 적용된 기본 취소 결제 삭제
		ocClaimAmountVO.setEventPointCnclAmt(null);
		ocClaimAmountVO.setGiftCnclAmt(null);
		ocClaimAmountVO.setMainCnclAmt(null);
		ocClaimAmountVO.setPointCnclAmt(null);

		OcClaimPayment mainPayment = null;
		OcClaimPayment giftPayment = null;
		OcClaimPayment pointPayment = null;
		OcClaimPayment eventPointPayment = null;

		// 원 주문 결제수단과 입력받은 변경 결제수단을 비교하여 취소 정보를 다시 set
		for (OcClaimPayment orderPayment : orderClaimPaymentList) {
			for (OcClaimPayment inputClaimPayment : chngInputPaymentList) {
				if (inputClaimPayment.getPymntAmt() > 0
						&& UtilsText.equals(inputClaimPayment.getPymntMeansCode(), orderPayment.getPymntMeansCode())) {
					if (UtilsText.equals(inputClaimPayment.getPymntMeansCode(),
							CommonCode.PYMNT_MEANS_CODE_MOBILE_GIFT)) { // 기프트
						if (orderPayment.getRemainPymntCnclAmt() > 0) {
							OcClaimPayment claimPayment = new OcClaimPayment();
							BeanUtils.copyProperties(orderPayment, claimPayment); // 내용 복사

							claimPayment.setPymntTodoAmt(inputClaimPayment.getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
							claimPayment.setPymntAmt(inputClaimPayment.getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

							giftPayment = claimPayment;
						}
					} else if (UtilsText.equals(inputClaimPayment.getPymntMeansCode(),
							CommonCode.PYMNT_MEANS_CODE_BUY_POINT)) { // 구매포인트
						if (orderPayment.getRemainPymntCnclAmt() > 0) {
							OcClaimPayment claimPayment = new OcClaimPayment();
							BeanUtils.copyProperties(orderPayment, claimPayment); // 내용 복사

							claimPayment.setPymntTodoAmt(inputClaimPayment.getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
							claimPayment.setPymntAmt(inputClaimPayment.getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

							pointPayment = claimPayment;
						}
					} else if (UtilsText.equals(inputClaimPayment.getPymntMeansCode(),
							CommonCode.PYMNT_MEANS_CODE_EVENT_POINT)) { // 이벤트포인트
						if (orderPayment.getRemainPymntCnclAmt() > 0) {
							OcClaimPayment claimPayment = new OcClaimPayment();
							BeanUtils.copyProperties(orderPayment, claimPayment); // 내용 복사

							claimPayment.setPymntTodoAmt(inputClaimPayment.getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
							claimPayment.setPymntAmt(inputClaimPayment.getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

							eventPointPayment = claimPayment;
						}
					} else {
						if (orderPayment.getRemainPymntCnclAmt() > 0) {
							OcClaimPayment claimPayment = new OcClaimPayment();
							BeanUtils.copyProperties(orderPayment, claimPayment); // 내용 복사

							claimPayment.setPymntTodoAmt(inputClaimPayment.getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
							claimPayment.setPymntAmt(inputClaimPayment.getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

							mainPayment = claimPayment;
						}
					}
				}
			}
		}

		int mainRfndAmt = (mainPayment == null) ? 0 : mainPayment.getPymntAmt();
		int giftRfndAmt = (giftPayment == null) ? 0 : giftPayment.getPymntAmt();
		int pointRfndAmt = (pointPayment == null) ? 0 : pointPayment.getPymntAmt();
		int eventPointRfndAmt = (eventPointPayment == null) ? 0 : eventPointPayment.getPymntAmt();

		// 금액 재검증
		if (refundCnclAmt != mainRfndAmt + giftRfndAmt + pointRfndAmt + eventPointRfndAmt) {
			throw new Exception("validMsg:실제 취소금액과 입력한 취소 설정금액이 다릅니다.");
		}

		ocClaimAmountVO.setMainPayment(mainPayment);
		ocClaimAmountVO.setGiftPayment(giftPayment);
		ocClaimAmountVO.setPointPayment(pointPayment);
		ocClaimAmountVO.setEventPointPayment(eventPointPayment);

		ocClaimAmountVO.setEventPointCnclAmt(eventPointRfndAmt);
		ocClaimAmountVO.setGiftCnclAmt(giftRfndAmt);
		ocClaimAmountVO.setMainCnclAmt(mainRfndAmt);
		ocClaimAmountVO.setPointCnclAmt(pointRfndAmt);

		/*
		 * 클레임 결제 등록
		 */
		// 주 결제 수단
		if (ocClaimAmountVO.getMainPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getMainPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getMainCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제예정금액-클레임취소금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제금액-클레임취소금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 기프트
		if (ocClaimAmountVO.getGiftPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getGiftPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getGiftCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제예정금액-클레임취소금액으로변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제금액-클레임취소금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 이벤트 포인트
		if (ocClaimAmountVO.getEventPointPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getEventPointPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getEventPointCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제예정금액-클레임취소금액으로변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제금액-클레임취소금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 포인트
		if (ocClaimAmountVO.getPointPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getPointPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getPointCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제예정금액-클레임취소금액으로변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제금액-클레임취소금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}
	}

	/**
	 * @Desc : 주문 전체 취소 처리(주문에서 요청 - 클레임 접수, 취소처리 상태 진행)
	 * @Method Name : setClaimAllCancelProc
	 * @Date : 2019. 7. 1.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocClaimAmountVO
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setOrderAllCancelProc(OcClaim ocClaim) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		// 리오더 주문 생성 여부(가상계좌 입금상태로 판단 - 전체취소인 경우만 사용)
		boolean reOrderRegistPossible = true;

		OcClaimAmountVO ocClaimAmountVO = new OcClaimAmountVO();

		ocClaimAmountVO.setAllCancelYn(true); // 전체취소 여부 set

		ocClaim.setClaimRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자 no set

		/*
		 * [주문정보 get]
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보

		ocClaim.setOrgOrderNo(ocOrder.getOrgOrderNo()); // 클레임 orgOrderNo set
		ocClaim.setSiteNo(ocOrder.getSiteNo()); // claim 정보에 siteNo set
		ocClaim.setMemberNo(ocOrder.getMemberNo()); // claim 정보에 memberNo set (품절보상쿠폰 지급 서비스)

		/*
		 * [주문 상품 결제 정보]
		 */
		OcOrderPayment ocOrderPayment = new OcOrderPayment();
		ocOrderPayment.setOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준

		List<OcOrderPayment> orderPaymentList = ocOrderPaymentDao.selectPaymentList(ocOrderPayment);

		// 주문결제 가상계좌 입금대기 카운트
		long virtualAccountWaitDepositCnt = orderPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT)
						&& UtilsText.equals(x.getPymntStatCode(), CommonCode.PYMNT_STAT_CODE_WAIT_DEPOSIT))
				.count();

		// 가상계좌 입금대기 상태이면 리오더 주문을 생성하지 않음
		if (virtualAccountWaitDepositCnt > 0) {
			reOrderRegistPossible = false;
			ocClaimAmountVO.setReOrderRegistPossible(false); // 취소 후 처리를 위해 set
		}

		/*
		 * [주문자 정보]
		 */
		MbMember mbMember = memberDao.selectMemberDefalutInfo(ocOrder.getMemberNo());

		/*
		 * [주문상품 목록 - 사은품/배송비 포함]
		 */
		OcOrderProduct ocOrderProduct = new OcOrderProduct();
		ocOrderProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 추출용
		ocOrderProduct.setOrderNo(ocClaim.getOrderNo());

		List<OcOrderProduct> orgOrderProductList = ocOrderProductDao.selectOrgOrderProductList(ocOrderProduct);

		ocClaimAmountVO.setOrgOrderProductList(orgOrderProductList);

		/*
		 * [클레임 상품 목록 - 사은품/배송비 제외]
		 */
		List<OcClaimProduct> claimProductList = new ArrayList<OcClaimProduct>();

		for (OcOrderProduct orderProduct : orgOrderProductList) {
			if (!UtilsText.equals(orderProduct.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
					&& !UtilsText.equals(orderProduct.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY)) {

				OcClaimProduct claimProduct = new OcClaimProduct();
				BeanUtils.copyProperties(orderProduct, claimProduct); // 내용 복사

				claimProductList.add(claimProduct);
			}
		}

		ocClaim.setOcClaimProductList(claimProductList);

		/*
		 * [기본 등록정보 set]
		 */
		ocClaim.setMemberNo(ocOrder.getMemberNo()); // 인서트 시 주문정보를 이용하나 ocClaim 재 사용을 위해 set
		ocClaim.setClmGbnCode(CommonCode.CLM_GBN_CODE_CANCEL); // 클레임구분코드-취소
		ocClaim.setDeviceCode(ocOrder.getDeviceCode()); // 디바이스코드

		ocClaim.setRtrvlGbnType(CommonCode.RTRVL_GBN_TYPE_ONLINE); // 회수지구분(O:온라인)
		ocClaim.setRtrvlStoreNo(null); // 회수매장번호
		ocClaim.setRtrvlReqYn(Const.BOOLEAN_FALSE); // 회수신청여부

		ocClaim.setBuyerName(ocOrder.getBuyerName());
		ocClaim.setBuyerTelNoText(ocOrder.getBuyerTelNoText());
		ocClaim.setBuyerHdphnNoText(ocOrder.getBuyerHdphnNoText());
		ocClaim.setBuyerPostCodeText(ocOrder.getBuyerPostCodeText());
		ocClaim.setBuyerPostAddrText(ocOrder.getBuyerPostAddrText());
		ocClaim.setBuyerDtlAddrText(ocOrder.getBuyerDtlAddrText());

		ocClaim.setRcvrName(ocOrder.getRcvrName());
		ocClaim.setRcvrTelNoText(ocOrder.getRcvrTelNoText());
		ocClaim.setRcvrHdphnNoText(ocOrder.getRcvrHdphnNoText());
		ocClaim.setRcvrPostCodeText(ocOrder.getRcvrPostCodeText());
		ocClaim.setRcvrPostAddrText(ocOrder.getRcvrPostAddrText());
		ocClaim.setRcvrDtlAddrText(ocOrder.getRcvrDtlAddrText());

		ocClaim.setDlvyMemoText(ocOrder.getDlvyMemoText());
		ocClaim.setDlvyTypeCode(ocOrder.getDlvyTypeCode()); // 배송유형코드

		ocClaim.setVndrNo(orgOrderProductList.get(0).getVndrNo()); // 업체번호(클레임상품 중 한개 발췌-취소는 상품단위 의미없음)

		ocClaim.setTotalRfndAmt(ocOrder.getPymntAmt()); // 총환불금액
		ocClaim.setTotalRedempAmt(0); // 총환수금액

		ocClaim.setUnProcYn(Const.BOOLEAN_FALSE); // 미처리 여부 set
		ocClaim.setAdminAcceptYn(Const.BOOLEAN_TRUE); // 관리자접수여부
		ocClaim.setOflnAcceptYn(Const.BOOLEAN_FALSE); // 오프라인접수여부

		// 추가배송비 관련 set
		ocClaim.setAddDlvyAmtPymntType(null); // 추가배송비결제방법
		ocClaim.setAddDlvyAmt(0); // 추가배송비
		ocClaim.setHoldCpnSeq(null); // 보유쿠폰순번

		ocClaim.setClmStatCode(CommonCode.CLM_STAT_CODE_CANCEL_ACCEPT); // 클레임상태코드 - 취소접수
		ocClaim.setBranchMoveCode(CommonCode.BRANCH_MOVE_CODE_ORIGIN_DEAL); // 점간이동코드 - 원거래

		ocClaim.setRgsterNo(ocClaim.getClaimRgsterNo()); // 등록자
		ocClaim.setModerNo(ocClaim.getClaimRgsterNo()); // 수정자

		/*
		 * [클레임 등록(마스터)] 등록 시 생성된 clmNo 를 ocClaim 에서 가지고 있음
		 */
		ocClaimDao.insertClaimInfo(ocClaim);

		/*
		 * [클레임상품 등록] [클레임상태이력 등록]
		 */
		for (OcOrderProduct orderProduct : orgOrderProductList) {
			OcClaimProduct ocClaimProduct = new OcClaimProduct();
			BeanUtils.copyProperties(orderProduct, ocClaimProduct); // 내용 복사

			ocClaimProduct.setClmRsnCode(ocClaim.getClmRsnCode()); // 클레임유형
			ocClaimProduct.setClmEtcRsnText(ocClaim.getClmEtcRsnText()); // 클레임사유

			// 클레임상품 등록, 클레임상태이력 등록
			this.insertClaimPrdtAndClaimStatusHistory(ocClaim, ocClaimProduct,
					CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT);
		}

		/*
		 * 클레임 금액 계산(클레임 마스터/상품 등록 후 호출)
		 */
		log.error("주문 전체 취소 처리 금액계산  START ClmNo => {}", ocClaim.getClmNo());
		claimProcService.setClaimAmountCalcForAllCancel(ocClaim, ocOrder, ocClaimAmountVO);

		/*
		 * 원 주문 마스터/상품 상태 값 변경
		 */
		OcOrderProduct updateOrderProduct = new OcOrderProduct();
		updateOrderProduct.setOrderNo(ocOrder.getOrderNo());
		updateOrderProduct.setModerNo(ocClaim.getClaimRgsterNo());
		updateOrderProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_ACCEPT); // 주문상품상태코드 : 취소접수

		ocOrderProductDao.updateOrderProductForCalim(updateOrderProduct); // 원 주문상품 상태 값 변경

		OcOrder updateOrder = new OcOrder();
		updateOrder.setOrderNo(ocOrder.getOrderNo());
		updateOrder.setOrderStatCode(CommonCode.ORDER_STAT_CODE_CANCEL_ACCEPT); // 주문상태코드 : 전체취소접수
		updateOrder.setCnclAmt(ocOrder.getPymntAmt());
		updateOrder.setClmNo(ocClaim.getClmNo());
		updateOrder.setModerNo(ocClaim.getClaimRgsterNo());

		ocOrderDao.updateOrderForClaim(updateOrder); // 원 주문 상태 값 변경

		/*
		 * 주문 마스터/상품 신규(매출취소 리오더) 등록
		 */
		if (reOrderRegistPossible) {
			OcOrder reOrder = new OcOrder();
			BeanUtils.copyProperties(ocOrder, reOrder); // 내용 복사

			String reOrderNo = orderService.createOrderSeq(); // 신규 주문번호

			ocClaim.setReOrderNo(reOrderNo); // 클레임 정보에도 신규주문번호 set

			reOrder.setOrderNo(reOrderNo); // 신규주문번호
			reOrder.setClmNo(ocClaim.getClmNo()); // 클레임번호 set
			reOrder.setSalesCnclGbnType(CommonCode.SALES_CNCL_GBN_TYPE_CANCEL); // 매출취소구분 : 취소
			reOrder.setOrderStatCode(CommonCode.ORDER_STAT_CODE_CANCEL_ACCEPT); // 주문상태코드 : 전체취소접수
			reOrder.setCnclAmt(ocOrder.getPymntAmt()); // 취소금액
			reOrder.setRgsterNo(ocClaim.getClaimRgsterNo());
			reOrder.setModerNo(ocClaim.getClaimRgsterNo());

			// 신규 주문(매출취소 리오더) 생성
			ocOrderDao.insertOrder(reOrder);

			// 신규 주문상품(매출취소 리오더) 생성
			for (OcOrderProduct orderProduct : ocClaimAmountVO.getOrgOrderProductList()) {
				OcOrderProduct reOrderProduct = new OcOrderProduct();
				BeanUtils.copyProperties(orderProduct, reOrderProduct); // 내용 복사

				reOrderProduct.setOrderNo(reOrderNo); // 신규 생성 주문번호
				reOrderProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_ACCEPT); // 주문상품상태코드:취소접수
				reOrderProduct.setRgsterNo(ocClaim.getClaimRgsterNo());
				reOrderProduct.setModerNo(ocClaim.getClaimRgsterNo());

				// 신규 리오더 주문상품 생성
				ocOrderProductDao.insertOrderProduct(reOrderProduct);
			}

			// 클레임상품 클레임생성 리오더 주문번호 업데이트
			OcClaimProduct reOrderUpdateClaimProduct = new OcClaimProduct();

			reOrderUpdateClaimProduct.setModerNo(ocClaim.getClaimRgsterNo());
			reOrderUpdateClaimProduct.setClmCrtOrderNo(reOrderNo);
			reOrderUpdateClaimProduct.setClmNo(ocClaim.getClmNo());

			ocClaimProductDao.updateClmCrtOrderNo(reOrderUpdateClaimProduct);
		}

		/*
		 * 원 주문의 주문상품이력 등록(취소접수)
		 */
		for (OcOrderProduct orderProduct : ocClaimAmountVO.getOrgOrderProductList()) {
			OcOrderProductHistory orgOrderProductHistory = new OcOrderProductHistory();
			orgOrderProductHistory.setOrderNo(orderProduct.getOrderNo());
			orgOrderProductHistory.setOrderPrdtSeq(orderProduct.getOrderPrdtSeq());
			orgOrderProductHistory.setPrdtNo(orderProduct.getPrdtNo());
			orgOrderProductHistory.setPrdtOptnNo(orderProduct.getPrdtOptnNo());
			orgOrderProductHistory.setPrdtName(orderProduct.getPrdtName());
			orgOrderProductHistory.setEngPrdtName(orderProduct.getEngPrdtName());
			orgOrderProductHistory.setOptnName(orderProduct.getOptnName());
			orgOrderProductHistory.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_ACCEPT); // 주문상품상태코드:취소접수
			orgOrderProductHistory.setNoteText(null);
			orgOrderProductHistory.setRgsterNo(ocClaim.getClaimRgsterNo());

			ocOrderProductHistoryDao.insertProductHistory(orgOrderProductHistory); // 주문상품이력 생성
		}

		/*
		 * 품절보상 쿠폰 지급 처리
		 */
		Date orderDtm = new Date(ocOrder.getOrderDtm().getTime());
		Date compareDate = DateUtils.addDays(new Date(), -3);

		// 주문일 3일 경과 기준, 클레임 사유 품절, 임직원아닌 회원 주문 상품
		if (compareDate.compareTo(orderDtm) >= 0
				&& UtilsText.equals(ocClaim.getClmRsnCode(), CommonCode.CLM_RSN_CODE_CANCEL_SOLDOUT)
				&& UtilsText.equals(ocOrder.getEmpYn(), Const.BOOLEAN_FALSE)
				&& (UtilsText.equals(ocOrder.getMemberTypeCode(), CommonCode.MEMBER_TYPE_CODE_ONLINE)
						|| UtilsText.equals(ocOrder.getMemberTypeCode(), CommonCode.MEMBER_TYPE_CODE_MEMBERSHIP))) {
			// 클레임 대상 상품 수량만큼 지급
			claimProcService.setGiveSoldOutCmpns(ocClaim, ocClaim.getOcClaimProductList());
		}

		/*
		 * 주문 사용쿠폰 클레임번호 업데이트
		 */
		OcOrderUseCoupon orderUseCoupon = new OcOrderUseCoupon();
		orderUseCoupon.setOrderNo(ocClaim.getOrderNo());
		orderUseCoupon.setClmNo(ocClaim.getClmNo());

		ocOrderUseCouponDao.updateOrderUseCouponClmNo(orderUseCoupon); // 주문 사용쿠폰 클레임번호 업데이트

		/*
		 * 사용 쿠폰 재 발급
		 */
		List<Integer> holdCpnSeqs = ocClaimAmountVO.getOrderUseCouponList().stream()
				.map(OcOrderUseCoupon::getHoldCpnSeq).distinct().collect(Collectors.toList()); // 중복 쿠폰 제거
		for (Integer holdCpnSeq : holdCpnSeqs) {
			MbMemberCoupon reIssueMemberCoupon = new MbMemberCoupon();
			reIssueMemberCoupon.setMemberNo(ocOrder.getMemberNo());
			reIssueMemberCoupon.setHoldCpnSeq(holdCpnSeq);
			reIssueMemberCoupon.setReIssueRsnText("주문취소 재 발급");
			reIssueMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_REISSUANCE);
			reIssueMemberCoupon.setRgsterNo(Const.SYSTEM_ADMIN_NO);
			reIssueMemberCoupon.setModerNo(Const.SYSTEM_ADMIN_NO);

			mbMemberCouponDao.insertMemberCouponReIssue(reIssueMemberCoupon); // 쿠폰 재 발급
		}

		/*
		 * 주문상품 프로모션 클레임번호 업데이트
		 */
		OcOrderProductApplyPromotion ocOrderProductApplyPromotion = new OcOrderProductApplyPromotion();
		ocOrderProductApplyPromotion.setOrderNo(ocClaim.getOrderNo());
		ocOrderProductApplyPromotion.setClmNo(ocClaim.getClmNo());

		ocOrderProductApplyPromotionDao.updateOrderProductApplyPromotionClmNo(ocOrderProductApplyPromotion);

		/*
		 * 주문배송이력 상태 변경
		 */
		OcOrderDeliveryHistory orderDeliveryHistory = new OcOrderDeliveryHistory();
		orderDeliveryHistory.setOrderNo(ocClaim.getOrderNo());
		orderDeliveryHistory.setDlvyStatCode(CommonCode.DLVY_STAT_CODE_DELIVERY_CANCEL); // 배송 상태 코드 : 배송취소
		orderDeliveryHistory.setRgsterNo(ocClaim.getRgsterNo());
		orderDeliveryHistory.setModerNo(ocClaim.getModerNo());
		ocOrderDeliveryHistoryDao.updateOrderDeliveryHistoryStat(orderDeliveryHistory);

		/*
		 * 클레임 결제 등록
		 */
		// 주 결제 수단
		if (ocClaimAmountVO.getMainPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getMainPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getMainPayment().getPymntAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 기프트
		if (ocClaimAmountVO.getGiftPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getGiftPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getGiftPayment().getPymntAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 이벤트 포인트
		if (ocClaimAmountVO.getEventPointPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getEventPointPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getEventPointPayment().getPymntAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소
																									// 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 포인트
		if (ocClaimAmountVO.getPointPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getPointPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getPointPayment().getPymntAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로
																								// 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		/*
		 * 임직원 주문인 경우 임직원 한도 복원
		 */
		if (UtilsText.equals(ocOrder.getEmpOrderYn(), Const.BOOLEAN_TRUE)) {
			String orderYearMonth = new SimpleDateFormat(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS)
					.format(ocOrder.getOrderDtm());
			String orderDate = new SimpleDateFormat(Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS)
					.format(ocOrder.getOrderDtm());
			String thisYearMonth = new SimpleDateFormat(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS)
					.format(new Date());

			// 주문월과 해당월이 같을 경우만 한도복원 인터페이스 호출
			if (UtilsText.equals(orderYearMonth, thisYearMonth)) {
				MbEmployeeCertificationHistory employeeInfo = mbEmployeeCertificationHistoryDao
						.selectEmpCertfiHistory(mbMember.getMemberNo());

				EmployPoint returnEmployPointResult = membershipPointService.savePointByEmployNum("1", orderDate,
						employeeInfo.getEmpNoText(), String.valueOf(ocOrder.getPymntAmt()), Const.SALE_EMP_CD);
			}
		}

		/*
		 * 재고 변경, 주문 취소 인터페이스 호출, IF_ORDER 주문상태값 변경
		 */
		for (OcOrderProduct orderProduct : ocClaimAmountVO.getOrgOrderProductList()) {

			// 2020.03.24 : BO,PO 에서 배송전 주문취소시 재고원복 X (레드마인 #1242)
			// 배송비 상품이 아닌 경우 재고 변경
//			if (!UtilsText.equals(orderProduct.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY)) {
//				// 예약 주문이 아닌 경우 재고 변경
//				if (UtilsText.equals(ocOrder.getRsvOrderYn(), Const.BOOLEAN_FALSE)) {
//					// 재고구분이 AI, VD 인 경우
//					if (UtilsText.equals(orderProduct.getStockGbnCode(), CommonCode.STOCK_GBN_CODE_AI)
//							|| UtilsText.equals(orderProduct.getStockGbnCode(), CommonCode.STOCK_GBN_CODE_VD)) {
//
//						// 2020.03.10 : '품절'로 인한 취소가 아닌 경우에만 재고반영 (레드마인 #1069)
//						if (!UtilsText.equals(ocClaim.getClmRsnCode(), CommonCode.CLM_RSN_CODE_CANCEL_SOLDOUT)) {
//
//							
//							orderService.updateProductStockAdjust(orderProduct.getPrdtNo(),
//									orderProduct.getPrdtOptnNo(), 1, orderProduct.getStockGbnCode(), false);
//						}
//					}
//				}
//			}

			// 자사 상품인 경우만 취소 인터페이스 호출
			if (UtilsText.equals(orderProduct.getMmnyPrdtYn(), Const.BOOLEAN_TRUE)) {
				// 주문 취소 인터페이스 호출(사은품, 배송비 제외 - 사은품은 본 상품과 join된 데이터 사용)
				if (!UtilsText.equals(orderProduct.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
						&& !UtilsText.equals(orderProduct.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY)) {

					claimProcService.callCancelOrderInterface(orderProduct);
				}

				// IF_ORDER 주문상태값 변경
				if (UtilsText.isNotEmpty(orderProduct.getDlvyIdText())) {
					// ocClaimProductDao.updateInterfaceOrderCancel(orderProduct.getDlvyIdText());

					Map<String, String> paramMap = new HashMap<>();
					paramMap.put("dlvyIdText", orderProduct.getDlvyIdText());

					// 2020.02.07 : PR_ORDER_CANCEL 프로시져 호출로 변경
					String errorCode = this.setCallProcedureForOrderCancel(paramMap);
					log.error("callProcedureForOrderCancel return >>> " + errorCode);

					if (!UtilsText.equals(errorCode, "0")) {
						throw new Exception("validMsg:배송취소연동(PR_ORDER_CANCEL)에 실패하였습니다.");
					}
				} else {
					log.error("callProcedureForOrderCancel 실행안함 !!!!!!");
				}
			}
		}

		/*
		 * 결제취소
		 */
		// 결제취소시 환불 발생여부(refundOccurrence) ocClaimAmountVO 에 set
		claimProcService.setCancelPayment(ocClaimAmountVO, ocClaim, mbMember);

		/*
		 * 결제취소 처리 후 처리 주문, 클레임 상태 값 후 처리
		 */
		claimProcService.setClaimCancelAfterProc(ocClaim, ocClaimAmountVO);

		if (ocClaimAmountVO.isRefundOccurrence()) {
			resultMap.put("success", Const.BOOLEAN_FALSE);
			resultMap.put("message", "취소 처리는 완료되었으나\n\n결제취소 실패가 발생하여 환불접수 처리되었습니다.");
			resultMap.put("clmNo", ocClaim.getClmNo());
		} else {
			resultMap.put("success", Const.BOOLEAN_TRUE);
			resultMap.put("message", "주문이 정상적으로 취소되었습니다.");
			resultMap.put("clmNo", ocClaim.getClmNo());
		}

		// 이메일 , 카톡 문자 알림 서비스 시작
		try {
			claimMessageService.orderAllCancel(ocClaim);
		} catch (Exception e) {
			log.error("주문전체취소 메세지 메일 발송 실패 : " + e.getMessage());
		}

		return resultMap;
	}

	/**
	 * @Desc : 클레임 취소 기본 정보
	 * @Method Name : getClaimCancelBasisInfo
	 * @Date : 2019. 4. 11.
	 * @Author : KTH
	 * @param ocClaim
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getClaimCancelBasisInfo(OcClaim ocClaim) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		OcClaimAmountVO ocClaimAmountVO = new OcClaimAmountVO();

		retMap.put(CommonCode.CLM_RSN_CODE,
				commonCodeService.getCodeNoNameFilterAddInfo(CommonCode.CLM_RSN_CODE, CommonCode.CLM_GBN_CODE_CANCEL)); // 클레임사유코드
		retMap.put(CommonCode.CLM_WTHDRAW_RSN_CODE, commonCodeService.getCodeNoName(CommonCode.CLM_WTHDRAW_RSN_CODE)); // 클레임철회사유코드

		// addInfo4 있는 은행만 뽑기
		List<SyCodeDetail> bankCodeList = commonCodeService.getCodeNoName(CommonCode.BANK_CODE);
		bankCodeList = bankCodeList.stream().filter(x -> UtilsObject.isNotEmpty(x.getAddInfo4()))
				.collect(Collectors.toList());
		retMap.put(CommonCode.BANK_CODE, bankCodeList); // 은행코드

		/*
		 * 클레임 정보
		 */
		ocClaim.setNonMemberNo(Const.NON_MEMBER_NO);
		ocClaim = ocClaimDao.selectClaimBasisInfo(ocClaim); // 클레임기본정보
		// 마스킹 해제
		// ocClaim.setIsMessageMailYn(Const.BOOLEAN_TRUE);
		retMap.put("CLAIM_INFO", ocClaim);

		/*
		 * 원 주문 정보
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보
		retMap.put("ORDER_INFO", ocOrder);

		/*
		 * 주문결제 정보
		 */
		OcOrderPayment ocOrderPayment = new OcOrderPayment();
		ocOrderPayment.setOrderNo(ocClaim.getOrgOrderNo());

		List<OcOrderPayment> tempOrderPaymentList = ocOrderPaymentDao.selectPaymentList(ocOrderPayment);

		// 결제수단을 변경하지 않았거나 관리자가 결제수단 변경을 허가했으나 아직 유저가 재결제(결제 시, 기존결제 결제취소)를 하지 않은
		// 주문결제 정보
		retMap.put("ORDER_PAYMENT_LIST", tempOrderPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getPymntMeansChngPsbltYn(), Const.BOOLEAN_FALSE)
						|| (UtilsText.equals(x.getPymntMeansChngPsbltYn(), Const.BOOLEAN_TRUE)
								&& UtilsText.equals(x.getPymntStatCode(), CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH)))
				.collect(Collectors.toList()));

		/*
		 * 현재 클레임결제 정보 목록
		 */
		OcClaimPayment thisClaimPayment = new OcClaimPayment();
		thisClaimPayment.setClmNo(ocClaim.getClmNo());
		List<OcClaimPayment> thisTimeClaimPaymentList = ocClaimPaymentDao.selectClaimPaymentList(thisClaimPayment);

		/*
		 * for (OcClaimPayment clmPymnt : claimPaymentList) { // 마스킹 해제
		 * clmPymnt.setIsMessageMailYn(Const.BOOLEAN_TRUE); }
		 */
		retMap.put("THIS_TIME_CLAIM_PAYMENT_LIST", thisTimeClaimPaymentList); // 현재 클레임결제 정보 목록

		// 환수금 이력 목록
		retMap.put("REDEMP_CLAIM_PAYMENT_LIST",
				thisTimeClaimPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
						.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY))
						.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP))
						.collect(Collectors.toList())); // 환수결제 목록

		// 환불금 이력용 목록(주문금 기준 - 환불, 환수 될 사항을 모두 계산한 금액임)
		retMap.put("REFUND_CLAIM_PAYMENT_LIST",
				thisTimeClaimPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
						.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY))
						.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
						.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_ORDER_AMT))
						.collect(Collectors.toList())); // 환불결제 목록

		// 환불금 실결제 목록(실제 환불대상 실결제)
		retMap.put("REFUND_CLAIM_PAYMENT_PUBLIC_LIST",
				thisTimeClaimPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
						.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_PUBLIC))
						.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
						.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_ORDER_AMT))
						.collect(Collectors.toList())); // 환불결제 목록

		// 기존 환수배송비 환불 발생 내역
		retMap.put("REFUND_PREVIOUS_REDEMP_DLVY_LIST", thisTimeClaimPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
				.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY))
				.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
				.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_ORDER_DELIVERY_AMT))
				.collect(Collectors.toList()));

		// 기존 환수배송비 환불 발생 내역 sum
		retMap.put("REFUND_PREVIOUS_REDEMP_DLVY_SUM_AMT", thisTimeClaimPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
				.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY))
				.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
				.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_ORDER_DELIVERY_AMT))
				.mapToInt(o -> o.getPymntAmt()).sum());

		// 다족구매 프로모션 추가 환불대상 환불 발생 내역 sum
		retMap.put("ADD_MULTI_BUY_DIFFER_SUM_AMT",
				thisTimeClaimPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
						.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY))
						.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
						.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_PROMOTION))
						.mapToInt(o -> o.getPymntAmt()).sum());

		// 재경팀'환수'처리 결제대상목록
		retMap.put("MMNY_REDEMP_PAYMENT_LIST",
				thisTimeClaimPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_TRUE))
						.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_PUBLIC))
						.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP))
						.collect(Collectors.toList()));

		// 재경팀'환불'처리 결제대상목록
		retMap.put("MMNY_REFUNND_PAYMENT_LIST",
				thisTimeClaimPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_TRUE))
						.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_PUBLIC))
						.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
						.collect(Collectors.toList()));

		/*
		 * 계산용 클레임결제 정보 목록 - 주문결제 기준으로 클레임 취소가능 잔여금액 포함
		 */
		OcClaimPayment ocClaimPayment = new OcClaimPayment();
		ocClaimPayment.setClmNo(ocClaim.getClmNo()); // 원주문번호 기준
		ocClaimPayment.setOrgOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준
		ocClaimPayment.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 재경팀처리 제외
		ocClaimPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_PUBLIC); // 일반결제
		ocClaimPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REFUND); // 환불
		ocClaimPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_ORDER_AMT); // 주문금
		ocClaimPayment.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL); // 주문취소(결제취소)
		ocClaimPayment.setOrderPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH); // 주문결제상태(결제완료)

		List<OcClaimPayment> calcPaymentList = ocClaimPaymentDao.selectOrderClaimPaymentList(ocClaimPayment);

		retMap.put("CALC_PAYMENT_LIST", calcPaymentList); // 계산용 클레임결제 정보 목록

		/*
		 * 클레임 상품 목록
		 */
		// 사은품 제외
		String[] prdtTypeCodeList = { CommonCode.PRDT_TYPE_CODE_GIFT };

		OcClaimProduct ocClaimProduct = new OcClaimProduct();
		ocClaimProduct.setClmNo(ocClaim.getClmNo());
		ocClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 상품 코드
		ocClaimProduct.setPrdtTypeCodeList(prdtTypeCodeList);
		ocClaimProduct.setPrdtRltnFileSeq(1); // 상품관련파일순번 : 1 (대표)

		List<OcClaimProduct> claimProductList = ocClaimProductDao.selectClaimProductList(ocClaimProduct);

		retMap.put("CLAIM_PRODUCT_LIST", claimProductList);

		// 클레임상품 금액(주문배송비, 기존환수추가배송비 환불대상 포함)
		retMap.put("CLAIM_SUM_AMT", claimProductList.stream().mapToInt(o -> o.getOrderAmt()).sum()); // 클레임총금액

		// 배송비 제외한 클레임상품 금액
		retMap.put("CLAIM_PRODUCT_SUM_AMT",
				claimProductList.stream()
						.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
						.mapToInt(o -> o.getOrderAmt()).sum()); // 클레임상품총금액

		// 배송비 클레임상품 금액
		retMap.put("CLAIM_DLVY_SUM_AMT",
				claimProductList.stream()
						.filter(x -> UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
						.mapToInt(o -> o.getOrderAmt()).sum()); // 클레임배송비총금액

		/*
		 * 클레임으로 변경되는 구매적립 증감 포인트(더블포인트 사용 포함)
		 */
		retMap.put("THIS_CLAIM_VARIATION_SAVE_POINT", 100);

		return retMap;
	}

	/**
	 * @Desc : 클레임 접수 대상 주문상품 정보
	 * @Method Name : getClaimOrderProductInfo
	 * @Date : 2019. 3. 6.
	 * @Author : KTH
	 * @param ocClaimProduct
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getClaimOrderProductInfo(OcClaimProduct ocClaimProduct, String cpnTypeCode)
			throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();

		retMap.put(CommonCode.CLM_RSN_CODE,
				commonCodeService.getCodeNoNameFilterAddInfo(CommonCode.CLM_RSN_CODE, ocClaimProduct.getClmGbnCode())); // 클레임사유코드

		/*
		 * 원 주문 정보
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaimProduct.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보
		retMap.put("ORDER_INFO", ocOrder);

		/*
		 * 주문상품 목록 추출
		 */
		List<OcOrderProduct> orderProductList = new ArrayList<OcOrderProduct>();

		// 교환, 반품은 재교환 대상으로 진행되는 경우가 있으므로 상품 단위로 대상을 조회
		for (int i = 0; i < ocClaimProduct.getOrderPrdtSeqs().length; i++) {
			OcOrderProduct ocOrderProduct = new OcOrderProduct();
			ocOrderProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 추출용
			ocOrderProduct.setOrderNo(ocClaimProduct.getOrderNos()[i]);
			ocOrderProduct.setOrderPrdtSeq(ocClaimProduct.getOrderPrdtSeqs()[i]);
			ocOrderProduct.setPrdtRltnFileSeq(1); // 상품관련파일순번 : 1 (대표)

			orderProductList.add(ocOrderProductDao.selectOrderProductDetailForClaim(ocOrderProduct));
		}

		retMap.put("ORDER_PRODUCT_LIST", orderProductList);

		/*
		 * [사이트 정책 정보]
		 */
		SySite sySite = sySiteDao.selectSite(ocOrder.getSiteNo());

		/*
		 * 클레임 배송비 기준
		 */
		if (UtilsText.equals(ocClaimProduct.getClmGbnCode(), CommonCode.CLM_GBN_CODE_EXCHANGE)) {
			// 교환
			retMap.put("CLAIM_DELIVERY_AMT", sySite.getExchngDlvyAmt());
		} else if (UtilsText.equals(ocClaimProduct.getClmGbnCode(), CommonCode.CLM_GBN_CODE_RETURN)) {
			// 반품
			retMap.put("CLAIM_DELIVERY_AMT", this.getClaimReturnDeliveryAmt(ocOrder, orderProductList, sySite));
		}

		/*
		 * 주문 결제정보
		 */
		OcOrderPayment ocOrderPayment = new OcOrderPayment();
		ocOrderPayment.setOrderNo(ocClaimProduct.getOrgOrderNo());

		List<OcOrderPayment> tempOrderPaymentList = ocOrderPaymentDao.selectPaymentList(ocOrderPayment);

		// 결제완료만
		retMap.put("ORDER_PAYMENT_LIST", tempOrderPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getPymntMeansChngPsbltYn(), Const.BOOLEAN_FALSE)
						|| (UtilsText.equals(x.getPymntMeansChngPsbltYn(), Const.BOOLEAN_TRUE)
								&& UtilsText.equals(x.getPymntStatCode(), CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH)))
				.collect(Collectors.toList()));

		/*
		 * 회원 교환/반품 무료사용 가능 보유쿠폰
		 */
		MbMemberCoupon mbMemberCoupon = new MbMemberCoupon();
		mbMemberCoupon.setCpnStatCodes(
				new String[] { CommonCode.CPN_STAT_CODE_ISSUANCE, CommonCode.CPN_STAT_CODE_REISSUANCE });
		mbMemberCoupon.setMemberNo(ocOrder.getMemberNo());
		List<MbMemberCoupon> memberCouponList = mbMemberCouponDao.selectMemberCouponAvailableList(mbMemberCoupon);

		retMap.put("USABLE_COUPON_LIST", memberCouponList.stream()
				.filter(x -> UtilsText.equals(x.getCpnTypeCode(), cpnTypeCode)).collect(Collectors.toList()));

		/*
		 * KCP 결제요청정보 set - 클레임 배송비 결제
		 */
		KcpPaymentApproval kcpPaymentApproval = new KcpPaymentApproval();

		// site no 에 따른 siteCd, siteName 구분
		if (UtilsText.equals(ocOrder.getSiteNo(), Const.SITE_NO_ART)) {
			retMap.put("pgSiteCd", Config.getString("pg.kcp.claim.siteCode"));
			retMap.put("pgSiteName", Config.getString("pg.kcp.siteName"));
		} else {
			retMap.put("pgSiteCd", Config.getString("pg.kcp.ots.claim.siteCode"));
			retMap.put("pgSiteName", Config.getString("pg.kcp.ots.siteName"));
		}

		kcpPaymentApproval.setReqTx(CommonCode.KCP_REQ_TX_PAY); // 요청 종류
		kcpPaymentApproval.setOrdrIdxx(Const.KCP_ORDERIDXX_PREFIX_CLAIM.concat(ocOrder.getOrderNo())); // 쇼핑몰 주문번호

		// goodName 상품명 - jsp 에서 set
		// goodMny 결제 총금액 - jsp 에서 set
		kcpPaymentApproval.setBuyrName(ocOrder.getBuyerName()); // 주문자명
		kcpPaymentApproval.setBuyrTel2(ocOrder.getBuyerHdphnNoText()); // 주문자 핸드폰 번호
		kcpPaymentApproval.setBuyrMail(ocOrder.getBuyerEmailAddrText()); // 주문자 E-mail 주소
		kcpPaymentApproval.setPayMethod(CommonCode.KCP_PAY_METHOD_VIRTUAL_ACCOUNT); // 결제 방법 (가상계좌 고정값)
		kcpPaymentApproval.setCurrency(CommonCode.KCP_CURRENCY_WON);
		kcpPaymentApproval.setSkinIndx(Integer.parseInt(CommonCode.KCP_DEFAULT_SKIN_INDEX));
		kcpPaymentApproval.setDispTaxYn(Const.BOOLEAN_FALSE);

		kcpPaymentApproval.setVcntExpireTerm(Integer.parseInt(CommonCode.KCP_AVAILABLE_PAYMENT_DAY)); // 결제유효기간
																										// 3일(발급일+설정일)
		kcpPaymentApproval.setVcntExpireTermTime(Const.DEFAULT_END_TIME_NOT_CHARACTERS);

		// kcpPaymentApproval.setSiteLogo(Const.DEFAULT_ABCMART_LOGO_IMAGE_URL);

		kcpPaymentApproval.setShopUserId(ocOrder.getMemberNo()); // 가맹점 고객 아이디

		retMap.put("KCP_PAYMENT_APPROVAL", kcpPaymentApproval);

		return retMap;
	}

	/**
	 * @Desc : 반품 클레임 배송비 계산
	 * @Method Name : getClaimReturnDeliveryAmt
	 * @Date : 2019. 7. 24.
	 * @Author : KTH
	 * @param ocOrder
	 * @param orderProductList
	 * @return
	 * @throws Exception
	 */
	public int getClaimReturnDeliveryAmt(OcOrder ocOrder, List<OcOrderProduct> orderProductList, SySite sySite)
			throws Exception {
		int claimDeliveryAmt = 0; // 클레임 배송비

		/*
		 * [원 주문상품 목록 - 사은품/배송비 포함]
		 */
		OcOrderProduct orgOrderProduct = new OcOrderProduct();
		orgOrderProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 추출용
		orgOrderProduct.setOrderNo(ocOrder.getOrgOrderNo()); // 원주문번호 기준

		String _mmnyPrdtYn = orderProductList.get(0).getMmnyPrdtYn(); // 자사상품여부
		String _vndrNo = orderProductList.get(0).getVndrNo(); // 클레임대상 주문상품의 업체번호
		long _orgOrderProductCnt = 0;
		long _thisTimeClaimeProductCnt = 0;
		long _orgOrderDlvyProductCnt = 0;

		List<OcOrderProduct> orgOrderProductList = ocOrderProductDao.selectOrgOrderProductList(orgOrderProduct);

		if (UtilsText.equals(_mmnyPrdtYn, Const.BOOLEAN_TRUE)) {
			// 원 주문 상품의 주문 상품 총 개수(배송비, 사은품 제외)
			_orgOrderProductCnt = orgOrderProductList.stream()
					.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
							&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
					.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_TRUE)).count();

			// 클레임대상 주문상품 개수(배송비, 사은품 제외)
			_thisTimeClaimeProductCnt = orderProductList.stream()
					.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
							&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
					.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_TRUE)).count();

			// 주문배송비(무료배송 상품이 존재하거나 배송비 상품이 있으면 주문배송비 존재함으로 간주)
			_orgOrderDlvyProductCnt = orgOrderProductList.stream()
					.filter(x -> UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
					.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_TRUE)).count();
		} else {
			// 원 주문 상품의 주문 상품 총 개수(배송비, 사은품 제외)
			_orgOrderProductCnt = orgOrderProductList.stream()
					.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
							&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
					.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_FALSE))
					.filter(x -> UtilsText.equals(x.getVndrNo(), _vndrNo)).count();

			// 클레임대상 주문상품 개수(배송비, 사은품 제외)
			_thisTimeClaimeProductCnt = orderProductList.stream()
					.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
							&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
					.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_FALSE))
					.filter(x -> UtilsText.equals(x.getVndrNo(), _vndrNo)).count();

			// 주문배송비(무료배송 상품이 존재하거나 배송비 상품이 있으면 주문배송비 존재함으로 간주)
			_orgOrderDlvyProductCnt = orgOrderProductList.stream()
					.filter(x -> UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
					.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_FALSE))
					.filter(x -> UtilsText.equals(x.getVndrNo(), _vndrNo)).count();
		}

		// 업체단위(자사) 전체반품
		if (_orgOrderProductCnt == _thisTimeClaimeProductCnt) {
			// 배송비 상품이 있는 경우는 배송비 지불한 주문으로 간주
			if (_orgOrderDlvyProductCnt > 0) {
				claimDeliveryAmt = sySite.getPaidDlvyRtnAmt();
			}
			// 그 이외에는 배송비 지불하지 않은 것으로 간주
			else {
				claimDeliveryAmt = sySite.getFreeDlvyRtnAmt();
			}
			// 부분반품은 배송비 지불한 금액과 동일 처리
		} else {
			claimDeliveryAmt = sySite.getPaidDlvyRtnAmt();
		}

		return claimDeliveryAmt;
	}

	/**
	 * @Desc : 클레임 교환 기본 정보
	 * @Method Name : getClaimExchangeBasisInfo
	 * @Date : 2019. 2. 21.
	 * @Author : KTH
	 * @param OcClaim
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> getClaimExchangeBasisInfo(OcClaim ocClaim, String cpnTypeCode) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();

		retMap.put(CommonCode.CLM_RSN_CODE,
				commonCodeService.getCodeNoNameFilterAddInfo(CommonCode.CLM_RSN_CODE, ocClaim.getClmGbnCode())); // 클레임사유코드
		retMap.put(CommonCode.CLM_PROC_TYPE_CODE,
				commonCodeService.getCodeNoNameFilterAddInfo(CommonCode.CLM_PROC_TYPE_CODE, ocClaim.getClmGbnCode())); // 클레임처리유형코드
		retMap.put(CommonCode.LOGIS_VNDR_CODE, commonCodeService.getCodeNoName(CommonCode.LOGIS_VNDR_CODE)); // 택배사코드
		retMap.put(CommonCode.CLM_WTHDRAW_RSN_CODE, commonCodeService.getCodeNoName(CommonCode.CLM_WTHDRAW_RSN_CODE)); // 클레임철회사유코드

		// addInfo4 있는 은행만 뽑기
		List<SyCodeDetail> bankCodeList = commonCodeService.getCodeNoName(CommonCode.BANK_CODE);
		bankCodeList = bankCodeList.stream().filter(x -> UtilsObject.isNotEmpty(x.getAddInfo4()))
				.collect(Collectors.toList());
		retMap.put(CommonCode.BANK_CODE, bankCodeList); // 은행코드

		/*
		 * 클레임 정보
		 */
		ocClaim.setNonMemberNo(Const.NON_MEMBER_NO);
		ocClaim = ocClaimDao.selectClaimBasisInfo(ocClaim); // 교환기본정보
		// 마스킹 해제
		// ocClaim.setIsMessageMailYn(Const.BOOLEAN_TRUE);
		retMap.put("CLAIM_INFO", ocClaim);

		/*
		 * 클레임 상품 정보
		 */
		// 사은품, 배송비 제외
		String[] prdtTypeCodeList = { CommonCode.PRDT_TYPE_CODE_GIFT, CommonCode.PRDT_TYPE_CODE_DELIVERY };

		OcClaimProduct ocClaimProduct = new OcClaimProduct();
		ocClaimProduct.setClmNo(ocClaim.getClmNo());
		ocClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 상품 코드
		ocClaimProduct.setPrdtRltnFileSeq(1); // 상품관련파일순번 : 1 (대표)
		ocClaimProduct.setPrdtTypeCodeList(prdtTypeCodeList);

		retMap.put("CLAIM_PRODUCT_LIST", ocClaimProductDao.selectClaimProductList(ocClaimProduct));

		/*
		 * 주문 정보
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보
		retMap.put("ORDER_INFO", ocOrder);

		/*
		 * 주문결제 정보
		 */
		OcOrderPayment ocOrderPayment = new OcOrderPayment();
		ocOrderPayment.setOrderNo(ocClaim.getOrgOrderNo());

		List<OcOrderPayment> tempOrderPaymentList = ocOrderPaymentDao.selectPaymentList(ocOrderPayment);

		// 결제완료만
		retMap.put("ORDER_PAYMENT_LIST", tempOrderPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getPymntMeansChngPsbltYn(), Const.BOOLEAN_FALSE)
						|| (UtilsText.equals(x.getPymntMeansChngPsbltYn(), Const.BOOLEAN_TRUE)
								&& UtilsText.equals(x.getPymntStatCode(), CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH)))
				.collect(Collectors.toList()));

		/*
		 * 클레임결제 정보 목록
		 */
		OcClaimPayment ocClaimPayment = new OcClaimPayment();
		ocClaimPayment.setClmNo(ocClaim.getClmNo());
		List<OcClaimPayment> claimPaymentList = ocClaimPaymentDao.selectClaimPaymentList(ocClaimPayment);
		/*
		 * for (OcClaimPayment clmPymnt : claimPaymentList) { // 마스킹 해제
		 * clmPymnt.setIsMessageMailYn(Const.BOOLEAN_TRUE); }
		 */
		retMap.put("CLAIM_PAYMENT_LIST", claimPaymentList); // 클레임 결제정보 목록

		/*
		 * 교환배송비 내역
		 */
		// 클레임 결제정보 목록에서 배송비만 추출
		retMap.put("ADD_DELIVERY_PAYMENT_INFO",
				claimPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT))
						.findFirst().orElse(null));
		// 2020.03.18 추가: 클레임 결제정보 목록에서 실결제 배송비만 추출
		retMap.put("publicAddDlvyPymntInfo",
				claimPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT))
						.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_PUBLIC)).findFirst()
						.orElse(null));

		/*
		 * 회원 무료사용 가능 보유쿠폰
		 */
		MbMemberCoupon mbMemberCoupon = new MbMemberCoupon();
		mbMemberCoupon.setCpnStatCodes(
				new String[] { CommonCode.CPN_STAT_CODE_ISSUANCE, CommonCode.CPN_STAT_CODE_REISSUANCE }); // 발급, 재발급
		mbMemberCoupon.setMemberNo(ocOrder.getMemberNo());

		List<MbMemberCoupon> memberCouponList = mbMemberCouponDao.selectMemberCouponAvailableList(mbMemberCoupon);

		// 무료 교환/반품 쿠폰만 추출
		retMap.put("USABLE_COUPON_LIST", memberCouponList.stream()
				.filter(x -> UtilsText.equals(x.getCpnTypeCode(), cpnTypeCode)).collect(Collectors.toList()));

		/*
		 * [사이트 정책 정보]
		 */
		SySite sySite = sySiteDao.selectSite(ocOrder.getSiteNo());

		retMap.put("CLAIM_DELIVERY_AMT", sySite.getExchngDlvyAmt()); // 클레임 배송비

		/*
		 * KCP 결제요청정보 set - 배송비 결제
		 */
		KcpPaymentApproval kcpPaymentApproval = new KcpPaymentApproval();

		// site no 에 따른 siteCd, siteName 구분
		if (UtilsText.equals(ocOrder.getSiteNo(), Const.SITE_NO_ART)) {
			retMap.put("pgSiteCd", Config.getString("pg.kcp.claim.siteCode"));
			retMap.put("pgSiteName", Config.getString("pg.kcp.siteName"));
		} else {
			retMap.put("pgSiteCd", Config.getString("pg.kcp.ots.claim.siteCode"));
			retMap.put("pgSiteName", Config.getString("pg.kcp.ots.siteName"));
		}

		kcpPaymentApproval.setReqTx(CommonCode.KCP_REQ_TX_PAY); // 요청 종류
		kcpPaymentApproval.setOrdrIdxx(Const.KCP_ORDERIDXX_PREFIX_CLAIM.concat(ocOrder.getOrderNo())); // 쇼핑몰 주문번호

		// goodName 상품명 - jsp 에서 set
		// goodMny 결제 총금액 - jsp 에서 set
		kcpPaymentApproval.setBuyrName(ocOrder.getBuyerName()); // 주문자명
		kcpPaymentApproval.setBuyrTel2(ocOrder.getBuyerHdphnNoText()); // 주문자 핸드폰 번호
		kcpPaymentApproval.setBuyrMail(ocOrder.getBuyerEmailAddrText()); // 주문자 E-mail 주소
		kcpPaymentApproval.setPayMethod(CommonCode.KCP_PAY_METHOD_VIRTUAL_ACCOUNT); // 결제 방법 (가상계좌 고정값)
		kcpPaymentApproval.setCurrency(CommonCode.KCP_CURRENCY_WON);
		kcpPaymentApproval.setSkinIndx(Integer.parseInt(CommonCode.KCP_DEFAULT_SKIN_INDEX));
		kcpPaymentApproval.setDispTaxYn(Const.BOOLEAN_FALSE);
		kcpPaymentApproval
				.setVcntExpireTerm(Integer.parseInt(CommonCode.KCP_AVAILABLE_PAYMENT_DAY)); /* 결제유효기간 3일(발급일+설정일) */
		kcpPaymentApproval.setVcntExpireTermTime(Const.DEFAULT_END_TIME_NOT_CHARACTERS);
		// kcpPaymentApproval.setSiteLogo(Const.DEFAULT_ABCMART_LOGO_IMAGE_URL);
		kcpPaymentApproval.setShopUserId(ocOrder.getMemberNo()); // 가맹점 고객 아이디

		retMap.put("KCP_PAYMENT_APPROVAL", kcpPaymentApproval);

		return retMap;
	}

	/**
	 * @Desc : 클레임 반품 기본 정보
	 * @Method Name : getClaimReturnBasisInfo
	 * @Date : 2019. 2. 21.
	 * @Author : KTH
	 * @param OcClaim
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> getClaimReturnBasisInfo(OcClaim ocClaim, String cpnTypeCode) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();

		retMap.put(CommonCode.CLM_RSN_CODE,
				commonCodeService.getCodeNoNameFilterAddInfo(CommonCode.CLM_RSN_CODE, ocClaim.getClmGbnCode())); // 클레임사유코드
		retMap.put(CommonCode.CLM_PROC_TYPE_CODE,
				commonCodeService.getCodeNoNameFilterAddInfo(CommonCode.CLM_PROC_TYPE_CODE, ocClaim.getClmGbnCode())); // 클레임처리유형코드
		retMap.put(CommonCode.LOGIS_VNDR_CODE, commonCodeService.getCodeNoName(CommonCode.LOGIS_VNDR_CODE)); // 택배사코드
		retMap.put(CommonCode.CLM_WTHDRAW_RSN_CODE, commonCodeService.getCodeNoName(CommonCode.CLM_WTHDRAW_RSN_CODE)); // 클레임철회사유코드

		// addInfo4 있는 은행만 뽑기
		List<SyCodeDetail> bankCodeList = commonCodeService.getCodeNoName(CommonCode.BANK_CODE);
		bankCodeList = bankCodeList.stream().filter(x -> UtilsObject.isNotEmpty(x.getAddInfo4()))
				.collect(Collectors.toList());
		retMap.put(CommonCode.BANK_CODE, bankCodeList); // 은행코드

		/*
		 * 클레임 정보
		 */
		ocClaim.setNonMemberNo(Const.NON_MEMBER_NO);
		ocClaim = ocClaimDao.selectClaimBasisInfo(ocClaim); // 교환기본정보
		// 마스킹 해제
		// ocClaim.setIsMessageMailYn(Const.BOOLEAN_TRUE);
		retMap.put("CLAIM_INFO", ocClaim);

		/*
		 * 원 주문 정보
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보
		retMap.put("ORDER_INFO", ocOrder);

		/*
		 * 주문상품 목록 추출
		 */
		OcOrderProduct ocOrderProduct = new OcOrderProduct();
		ocOrderProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 추출용
		ocOrderProduct.setOrderNo(ocOrder.getOrgOrderNo());
		ocOrderProduct.setPrdtRltnFileSeq(1); // 상품관련파일순번 : 1 (대표)
		List<OcOrderProduct> orderProductAllList = ocOrderProductDao.selectOrderProductList(ocOrderProduct);

		/*
		 * 클레임 상품 목록
		 */
		// 사은품, 배송비 제외
		String[] prdtTypeCodeList = { CommonCode.PRDT_TYPE_CODE_GIFT, CommonCode.PRDT_TYPE_CODE_DELIVERY };

		OcClaimProduct ocClaimProduct = new OcClaimProduct();
		ocClaimProduct.setClmNo(ocClaim.getClmNo());
		ocClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 상품 코드
		ocClaimProduct.setPrdtTypeCodeList(prdtTypeCodeList);
		ocClaimProduct.setPrdtRltnFileSeq(1); // 상품관련파일순번 : 1 (대표)

		List<OcClaimProduct> claimProductList = ocClaimProductDao.selectClaimProductList(ocClaimProduct);

		retMap.put("CLAIM_PRODUCT_LIST", claimProductList);

		/*
		 * 주문결제 정보
		 */
		OcOrderPayment ocOrderPayment = new OcOrderPayment();
		ocOrderPayment.setOrderNo(ocClaim.getOrgOrderNo());

		List<OcOrderPayment> tempOrderPaymentList = ocOrderPaymentDao.selectPaymentList(ocOrderPayment);

		// 결제완료만
		retMap.put("ORDER_PAYMENT_LIST", tempOrderPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getPymntMeansChngPsbltYn(), Const.BOOLEAN_FALSE)
						|| (UtilsText.equals(x.getPymntMeansChngPsbltYn(), Const.BOOLEAN_TRUE)
								&& UtilsText.equals(x.getPymntStatCode(), CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH)))
				.collect(Collectors.toList()));

		/*
		 * 현재 클레임결제 정보 목록
		 */
		OcClaimPayment thisClaimPayment = new OcClaimPayment();
		thisClaimPayment.setClmNo(ocClaim.getClmNo());
		List<OcClaimPayment> thisTimeClaimPaymentList = ocClaimPaymentDao.selectClaimPaymentList(thisClaimPayment);

		retMap.put("CLAIM_PAYMENT_LIST", thisTimeClaimPaymentList); // 클레임결제 정보

		/*
		 * [사이트 정책 정보]
		 */
		SySite sySite = sySiteDao.selectSite(ocOrder.getSiteNo());

		/*
		 * for (OcClaimPayment clmPymnt : claimPaymentList) { // 마스킹 해제
		 * clmPymnt.setIsMessageMailYn(Const.BOOLEAN_TRUE); }
		 */
		retMap.put("THIS_TIME_CLAIM_PAYMENT_LIST", thisTimeClaimPaymentList); // 현재 클레임결제 정보 목록

		// 환수금 이력 목록
		retMap.put("REDEMP_CLAIM_PAYMENT_LIST",
				thisTimeClaimPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
						.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY))
						.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP))
						.collect(Collectors.toList())); // 환수결제 목록

		// 환불금 실결제 목록(실제 환불대상 실결제)
		retMap.put("REFUND_CLAIM_PAYMENT_PUBLIC_LIST",
				thisTimeClaimPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
						.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_PUBLIC))
						.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
						.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_ORDER_AMT))
						.collect(Collectors.toList())); // 환불결제 목록

		// 다족구매 프로모션 추가 환불대상 환불 발생 내역 sum
		retMap.put("ADD_MULTI_BUY_DIFFER_SUM_AMT",
				thisTimeClaimPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
						.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY))
						.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
						.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_PROMOTION))
						.mapToInt(o -> o.getPymntAmt()).sum());

		retMap.put("MMNY_REDEMP_PAYMENT_LIST",
				thisTimeClaimPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_TRUE))
						.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_PUBLIC))
						.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP))
						.collect(Collectors.toList())); // 재경팀환수처리대상목록

		retMap.put("MMNY_REFUNND_PAYMENT_LIST",
				thisTimeClaimPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_TRUE))
						.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_PUBLIC))
						.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
						.collect(Collectors.toList())); // 재경팀환불처리대상목록

		// 2020.02.28 : 환불금 이력용 목록 리스트화
		List<OcClaimPayment> refundClaimPaymentList = thisTimeClaimPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
				.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY))
				.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
				.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_ORDER_AMT))
				.collect(Collectors.toList());

		/*
		 * 계산용 클레임결제 정보 목록 - 주문결제 기준으로 클레임 취소가능 잔여금액 포함
		 */
		OcClaimPayment ocClaimPayment = new OcClaimPayment();
		ocClaimPayment.setClmNo(ocClaim.getClmNo()); // 원주문번호 기준
		ocClaimPayment.setOrgOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준
		ocClaimPayment.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 재경팀처리 제외
		ocClaimPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_PUBLIC); // 일반결제
		ocClaimPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REFUND); // 환불
		ocClaimPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_ORDER_AMT); // 주문금
		ocClaimPayment.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL); // 주문취소(결제취소)
		ocClaimPayment.setOrderPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH); // 주문결제상태(결제완료)

		List<OcClaimPayment> calcPaymentList = ocClaimPaymentDao.selectOrderClaimPaymentList(ocClaimPayment);

		/***********************************************************************************
		 * S: 환불금차감 반품완료 배송비 취소시 환수/환불 정보 영역 금액 조정 (2020.03.03)
		 ***********************************************************************************/

		// 2020.03.03 : 주결제수단 결제 조회
		OcOrderPayment mainPayment = orderService.getMainPayment(ocClaim.getOrgOrderNo()); // 원주문번호 기준

		// 2020.02.28 : 환불금 차감 반품완료후 배송비 취소할 경우 (주결제수단 == 신용/체크카드 + 네이버페이 + 카카오페이 / 배송비 == 0 )
		// 2020.05.08 : 네이버페이 / 카카오페이 도 환불금 차감 반품완료후 배송비 취소할 예정 / 반품상세에서는 미리 보이게 제어
		if (UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_RETURN_FINISH)
				&& UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(),
						CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_REFUND_DEDUCTION)
				&& (
						   UtilsText.equals(mainPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_CARD)
						|| UtilsText.equals(mainPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_NAVER_PAY)
						|| UtilsText.equals(mainPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_KAKAO_PAY)
				   )
				&& ocClaim.getAddDlvyAmt() == 0) {

			// 2020.02.28 : 환불금 차감 반품완료후 배송비 취소할 경우 환불 정보 영역 보여주기 위해
			refundClaimPaymentList = thisTimeClaimPaymentList.stream()
					.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
					.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_PUBLIC))
					.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
					.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_ORDER_AMT))
					.collect(Collectors.toList());

			if (!UtilsArray.isNull(refundClaimPaymentList)) {
				for (OcClaimPayment ocp : calcPaymentList) {
					if (UtilsText.equals(ocp.getMainPymntMeansYn(), Const.BOOLEAN_TRUE)
							&& UtilsText.equals(ocp.getPymntStatCode(), CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH)
							&& UtilsText.equals(ocp.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_CARD)) {

						// 2020.03.03 : 취소금액 설정에 꽂힐 금액
						int thisPymntCnclAmt = refundClaimPaymentList.stream().mapToInt(x -> x.getPymntAmt()).sum();

						// 2020.02.28 : 환수/환불 정보 영역 제어
						ocp.setThisPymntCnclAmt(thisPymntCnclAmt);
					}
				}
			}

			// 만일 결제취소가 실패서 환불금 재경접수 처리된 경우
			OcClaimPayment mmnyProcTrgtPayment = thisTimeClaimPaymentList.stream()
					.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_TRUE))
					.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_PUBLIC))
					.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
					.findFirst().orElse(null);
			// 환불금 재경접수 처리된 경우 "환불금액(①-②) ~원" 영역에 뿌려줄 정보 add
			if (mmnyProcTrgtPayment != null) {
				if (UtilsText.equals(mmnyProcTrgtPayment.getPymntStatCode(),
						CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT)) {
					mmnyProcTrgtPayment.setPymntMeansCodeName("반품배송비 환불대기");
				} else if (UtilsText.equals(mmnyProcTrgtPayment.getPymntStatCode(),
						CommonCode.PYMNT_STAT_CODE_REFUND_COMPLETE)) {
					mmnyProcTrgtPayment.setPymntMeansCodeName("반품배송비 환불완료");
				}
				refundClaimPaymentList.add(mmnyProcTrgtPayment);
			}
		}

		/***********************************************************************************
		 * E: 환불금차감 반품완료 배송비 취소시 환수/환불 정보 영역 금액 조정 (2020.03.03)
		 ***********************************************************************************/

		retMap.put("CALC_PAYMENT_LIST", calcPaymentList); // 계산용 클레임결제 정보 목록

		// 2020.02.28 환불금 이력용 목록 위치이동
		retMap.put("REFUND_CLAIM_PAYMENT_LIST", refundClaimPaymentList); // 환불결제 목록

		// 클레임상품 금액(반품불가 상태를 모두 포함)
		retMap.put("CLAIM_SUM_AMT", claimProductList.stream().mapToInt(o -> o.getOrderAmt()).sum()); // 클레임총금액

		// 반품불가 상태 상품을 제외한 클레임상품 금액
		retMap.put("CLAIM_POSSIBLE_SUM_AMT", claimProductList.stream()
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE))
				.mapToInt(o -> o.getOrderAmt()).sum());

		retMap.put("CLAIM_PRODUCT_SUM_AMT",
				claimProductList.stream()
						.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
						.mapToInt(o -> o.getOrderAmt()).sum()); // 클레임상품총금액

		/*
		 * 클레임으로 변경되는 구매적립 증감 포인트(더블포인트 사용 포함)
		 */
		retMap.put("THIS_CLAIM_VARIATION_SAVE_POINT", 100);

		/*
		 * 반품배송비 내역
		 */
		// 클레임 결제정보 목록에서 배송비만 추출
		retMap.put("ADD_DELIVERY_PAYMENT_INFO",
				thisTimeClaimPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT))
						.findFirst().orElse(null));
		// 2020.03.18 추가: 클레임 결제정보 목록에서 실결제 배송비만 추출
		retMap.put("publicAddDlvyPymntInfo",
				thisTimeClaimPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT))
						.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_PUBLIC)).findFirst()
						.orElse(null));

		/*
		 * 환수포인트 결제 내역
		 */
		retMap.put("REDEMP_POINT_PAYMENT_INFO",
				thisTimeClaimPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_POINT)).findFirst()
						.orElse(null));

		/*
		 * 회원 무료사용 가능 보유쿠폰
		 */
		MbMemberCoupon mbMemberCoupon = new MbMemberCoupon();
		mbMemberCoupon.setCpnStatCodes(
				new String[] { CommonCode.CPN_STAT_CODE_ISSUANCE, CommonCode.CPN_STAT_CODE_REISSUANCE }); // 발급, 재발급
		mbMemberCoupon.setMemberNo(ocOrder.getMemberNo());

		List<MbMemberCoupon> memberCouponList = mbMemberCouponDao.selectMemberCouponAvailableList(mbMemberCoupon);

		// 무료 교환/반품 쿠폰만 추출
		retMap.put("USABLE_COUPON_LIST", memberCouponList.stream()
				.filter(x -> UtilsText.equals(x.getCpnTypeCode(), cpnTypeCode)).collect(Collectors.toList()));

		/*
		 * 클레임 배송비 기준(반품기준)
		 */
		List<Integer> claimOrderPrdtSeqList = claimProductList.stream().map(OcClaimProduct::getOrderPrdtSeq)
				.collect(Collectors.toList());

		// 클레임 상품에 해당하는 orderPrdtSeq 에 해당되는 리스트만 별도 추출
		List<OcOrderProduct> orderProductList = orderProductAllList.stream()
				.filter(x -> claimOrderPrdtSeqList.contains(x.getOrderPrdtSeq())).collect(Collectors.toList());

		// 클레임 배송비
		retMap.put("CLAIM_DELIVERY_AMT", this.getClaimReturnDeliveryAmt(ocOrder, orderProductList, sySite));

		/*
		 * KCP 결제요청정보 set - 배송비 결제
		 */
		KcpPaymentApproval kcpPaymentApproval = new KcpPaymentApproval();

		// site no 에 따른 siteCd, siteName 구분
		if (UtilsText.equals(ocOrder.getSiteNo(), Const.SITE_NO_ART)) {
			retMap.put("pgSiteCd", Config.getString("pg.kcp.claim.siteCode"));
			retMap.put("pgSiteName", Config.getString("pg.kcp.siteName"));
		} else {
			retMap.put("pgSiteCd", Config.getString("pg.kcp.ots.claim.siteCode"));
			retMap.put("pgSiteName", Config.getString("pg.kcp.ots.siteName"));
		}

		kcpPaymentApproval.setReqTx(CommonCode.KCP_REQ_TX_PAY); // 요청 종류
		kcpPaymentApproval.setOrdrIdxx(Const.KCP_ORDERIDXX_PREFIX_CLAIM.concat(ocOrder.getOrderNo())); // 쇼핑몰 주문번호

		// goodName 상품명 - jsp 에서 set
		// goodMny 결제 총금액 - jsp 에서 set
		kcpPaymentApproval.setBuyrName(ocOrder.getBuyerName()); // 주문자명
		kcpPaymentApproval.setBuyrTel2(ocOrder.getBuyerHdphnNoText()); // 주문자 핸드폰 번호
		kcpPaymentApproval.setBuyrMail(ocOrder.getBuyerEmailAddrText()); // 주문자 E-mail 주소
		kcpPaymentApproval.setPayMethod(CommonCode.KCP_PAY_METHOD_VIRTUAL_ACCOUNT); // 결제 방법 (가상계좌 고정값)
		kcpPaymentApproval.setCurrency(CommonCode.KCP_CURRENCY_WON);
		kcpPaymentApproval.setSkinIndx(Integer.parseInt(CommonCode.KCP_DEFAULT_SKIN_INDEX));
		kcpPaymentApproval.setDispTaxYn(Const.BOOLEAN_FALSE);
		kcpPaymentApproval
				.setVcntExpireTerm(Integer.parseInt(CommonCode.KCP_AVAILABLE_PAYMENT_DAY)); /* 결제유효기간 3일(발급일+설정일) */
		kcpPaymentApproval.setVcntExpireTermTime(Const.DEFAULT_END_TIME_NOT_CHARACTERS);
		// kcpPaymentApproval.setSiteLogo(Const.DEFAULT_ABCMART_LOGO_IMAGE_URL);
		kcpPaymentApproval.setShopUserId(ocOrder.getMemberNo()); // 가맹점 고객 아이디

		retMap.put("KCP_PAYMENT_APPROVAL", kcpPaymentApproval);

		return retMap;
	}

	/**
	 * @Desc : 클레임 교환 등록
	 * @Method Name : registClaimExchange
	 * @Date : 2019. 7. 22.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocClaimProducts
	 * @param ocClaimMemo
	 * @param kcpPaymentApproval
	 * @throws Exception
	 */
	public void registClaimExchange(OcClaim ocClaim, OcClaimProduct[] ocClaimProducts, OcClaimMemo ocClaimMemo,
			KcpPaymentApproval kcpPaymentApproval) throws Exception {
		String clmStatCode = "";
		String clmPrdtStatCode = "";

		clmStatCode = CommonCode.CLM_STAT_CODE_EXCHANGE_ACCEPT; // 클레임상태코드 - 교환접수
		clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT; // 클레임상품상태코드 - 교환접수

		ocClaim.setClaimRgsterNo(LoginManager.getUserDetails().getAdminNo());

		/*
		 * 기본 validate
		 */
		OcClaimProduct validClaimProduct = new OcClaimProduct();
		Integer[] tempClaimProductPrdtSeqs = new Integer[ocClaimProducts.length];

		for (int i = 0; i < ocClaimProducts.length; i++) {
			// 2020.04.07 반품중인 상품이 배송중일 경우 배송완료 상태로 변경
			if (UtilsText.equals(ocClaimProducts[i].getDlvyStatCode(), CommonCode.DLVY_STAT_CODE_DELIVERY_ING)) {
				deliveryService.setOrderDeliveryPrdt(ocClaimProducts[i].getOrderNo(),
						ocClaimProducts[i].getOrderPrdtSeq());
			}

			tempClaimProductPrdtSeqs[i] = ocClaimProducts[i].getOrderPrdtSeq();
		}

		validClaimProduct.setOrgOrderNo(ocClaim.getOrderNo());
		validClaimProduct.setClmGbnCode(CommonCode.CLM_GBN_CODE_EXCHANGE);
		validClaimProduct.setOrderPrdtSeqs(tempClaimProductPrdtSeqs);

		Map<String, Object> validMap = this.validateClaimAccept(validClaimProduct);

		if (UtilsText.equals((String) validMap.get("success"), Const.BOOLEAN_FALSE)) {
			throw new Exception("validMsg:".concat((String) validMap.get("message")));
		}

		/*
		 * [원 주문정보 get]
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보

		/*
		 * [주문자 회원 정보]
		 */
		MbMember mbMember = memberDao.selectMemberDefalutInfo(ocOrder.getMemberNo());

		/*
		 * 교환상품 재고체크
		 */
		for (OcClaimProduct ocClaimProduct : ocClaimProducts) {
			String targetPrdtNo = "";
			String targetPrdtOptNo = "";

			targetPrdtNo = ocClaimProduct.getPrdtNo();

			if (UtilsText.equals(ocClaimProduct.getClmRsnCode(), CommonCode.CLM_RSN_CODE_EXCHANGE_OPTION_CHANGE)) { // 옵션변경
				targetPrdtOptNo = ocClaimProduct.getChangePrdtOptnNo();
			} else {
				targetPrdtOptNo = ocClaimProduct.getPrdtOptnNo();
			}

			int optCnt = productInsideOptionService.getProductOptionStock(targetPrdtNo, targetPrdtOptNo,
					Const.BOOLEAN_FALSE);

			if (optCnt < 1) {
				throw new Exception("validMsg:재고가 부족한 상품이 포함되어 있어 교환이 불가능합니다.");
			}
		}

		/*
		 * [기본 등록정보 set]
		 */
		// ocClaim.setClmGbnCode(CommonCode.CLM_GBN_CODE_EXCHANGE); // 클레임구분코드
		ocClaim.setOrderNo(ocClaim.getOrgOrderNo());
		ocClaim.setRtrvlGbnType(CommonCode.RTRVL_GBN_TYPE_ONLINE); // 회수지구분(O:온라인)
		ocClaim.setRtrvlStoreNo(null); // 회수매장번호
		ocClaim.setRtrvlReqYn(Const.BOOLEAN_FALSE); // 회수신청여부

		ocClaim.setDlvyTypeCode(CommonCode.DLVY_TYPE_CODE_NORMAL_LOGISTICS); // 배송유형코드-일반택배

		// 주문자 회원 정보에 환불계좌 정보가 있으면 set
		if (UtilsText.isNotEmpty(mbMember.getBankCode()) && UtilsText.isNotEmpty(mbMember.getAcntNoText())
				&& UtilsText.isNotEmpty(mbMember.getAcntHldrName())) {
			ocClaim.setBankCode(mbMember.getBankCode());
			ocClaim.setRfndAcntText(mbMember.getAcntNoText());
			ocClaim.setAcntHldrName(mbMember.getAcntHldrName());
		}

		ocClaim.setVndrNo(ocClaimProducts[0].getVndrNo()); // 업체번호(클레임상품 중 한개 발췌-업체단위로 클레임 걸림)

		ocClaim.setTotalRfndAmt(0); // 총환불금액
		ocClaim.setTotalRedempAmt(0); // 총환수금액

		// 미처리 여부 set
		if (UtilsText.isEmpty(ocClaim.getUnProcYn())) {
			ocClaim.setUnProcYn(Const.BOOLEAN_FALSE);
		}

		ocClaim.setAdminAcceptYn(Const.BOOLEAN_TRUE); // 관리자접수여부
		ocClaim.setOflnAcceptYn(Const.BOOLEAN_FALSE); // 오프라인접수여부

		// 추가배송비 관련 set
		if (ocClaim.getAddDlvyAmt() > 0) {
			// form에서 클레임 배송비 결제방법을 무료교환쿠폰 선택이 아니면 null
			if (!UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(), CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON)) {
				ocClaim.setHoldCpnSeq(null); // 보유쿠폰순번
			}
		} else {
			ocClaim.setAddDlvyAmtPymntType(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE); // 추가배송비결제방법
			ocClaim.setAddDlvyAmt(0); // 추가배송비
			ocClaim.setHoldCpnSeq(null); // 보유쿠폰순번
		}

		ocClaim.setClmStatCode(clmStatCode); // 클레임상태코드
		ocClaim.setBranchMoveCode(CommonCode.BRANCH_MOVE_CODE_ORIGIN_DEAL); // 점간이동코드 - 원거래

		ocClaim.setRgsterNo(ocClaim.getClaimRgsterNo()); // 등록자
		ocClaim.setModerNo(ocClaim.getClaimRgsterNo()); // 수정자

		// 2020.04.06 : 원주문번호와 주문번호가 다를 때 리오더의 주문번호를 적용한다.
		// 주문상품끼리 서로 다른 주문번호는 스크립트상으로 방지되어 있다.
		for (OcClaimProduct ocp : ocClaimProducts) {
			
			// 2020.04.13 : 신청팝업창에서 던져지는 원주문번호 대상주문상품에 set
			ocp.setOrgOrderNo(ocClaim.getOrgOrderNo());
			
			log.error("OcClaimProduct 원주문번호 : {}", ocp.getOrgOrderNo());
			log.error("OcClaimProduct 주문번호 : {}", ocp.getOrderNo());
			if (!UtilsText.equals(ocp.getOrgOrderNo(), ocp.getOrderNo())) {
				ocClaim.setOrgOrderNo(ocp.getOrgOrderNo());
				ocClaim.setOrderNo(ocp.getOrderNo());
			}
		}
		log.error("OcClaim 원주문번호 : {}", ocClaim.getOrgOrderNo());
		log.error("OcClaim 주문번호 : {}", ocClaim.getOrderNo());

		/*
		 * [클레임 등록(마스터)] 등록 시 생성된 clmNo 를 ocClaim 에서 가지고 있음
		 */
		ocClaimDao.insertClaimInfo(ocClaim);

		/*
		 * [클레임상품 등록] [클레임상태이력 등록]
		 */
		for (OcClaimProduct ocClaimProduct : ocClaimProducts) {
			// 교환 대상 상품 등록
			OcClaimProduct exchangeClaimProduct = new OcClaimProduct(); // 재배송 상품
			BeanUtils.copyProperties(ocClaimProduct, exchangeClaimProduct);

			if (UtilsText.equals(ocClaimProduct.getClmRsnCode(), CommonCode.CLM_RSN_CODE_EXCHANGE_OPTION_CHANGE)) { // 옵션변경
				ocClaimProduct.setChangePrdtOptnNo(null); // 등록 시 영향받지 않게 회수상품은 null 처리
				ocClaimProduct.setChangeOptnName(null); // 등록 시 영향받지 않게 회수상품은 null 처리
			}

			// 클레임상품 등록, 클레임상태이력 등록
			this.insertClaimPrdtAndClaimStatusHistory(ocClaim, ocClaimProduct, clmPrdtStatCode);

			// 교환대상 상품 등록
			exchangeClaimProduct.setUpClmPrdtSeq(ocClaimProduct.getClmPrdtSeq()); // 회수 상품 기준의 클레임상품순번

			// 클레임상품 등록, 클레임상태이력 등록
			this.insertClaimPrdtAndClaimStatusHistory(ocClaim, exchangeClaimProduct, clmPrdtStatCode);
		}

		/*
		 * [메모 등록] 클레임 상품 단위가 아닌 클레임 단위, 클레임 등록 시 메모 text가 입력된 상태만 등록
		 */
		if (UtilsText.isNotEmpty(ocClaimMemo.getMemoText())) {
			ocClaimMemo.setClmNo(ocClaim.getClmNo());
			ocClaimMemo.setOrgOrderNo(ocClaim.getOrgOrderNo());
			ocClaimMemo.setClmPrdtSeq(null);
			ocClaimMemo.setClmGbnCode(ocClaim.getClmGbnCode()); // 클레임구분코드
			ocClaimMemo.setRgsterNo(LoginManager.getUserDetails().getAdminNo());

			ocClaimMemoDao.insertClaimMemo(ocClaimMemo); // 메모 등록
		}

		/*
		 * 클레임배송비 처리
		 */
		if (ocClaim.getAddDlvyAmt() > 0) {
			if (UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(), CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT)) {
				/*
				 * [클레임결제 등록] 클레임배송비 결제
				 */
				OcClaimPayment ocClaimPayment = new OcClaimPayment();
				ocClaimPayment.setClmNo(ocClaim.getClmNo()); // 클레임번호
				ocClaimPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP); // 환수환불구분-환수
				// ocClaimPayment.setPymntDtm(new Timestamp(new Date().getTime())); // 결제일시
				ocClaimPayment.setPymntDtm(null); // 결제일시
				ocClaimPayment.setDeviceCode(ocOrder.getDeviceCode()); // 디바이스코드
				ocClaimPayment.setMainPymntMeansYn(Const.BOOLEAN_TRUE); // 주결제수단여부
				ocClaimPayment.setPymntMeansCode(CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT); // 결제수단코드-가상계좌
				ocClaimPayment.setPymntVndrCode(CommonCode.PYMNT_VNDR_CODE_KCP); // 결제업체코드
				ocClaimPayment.setPymntOrganCodeText(null); // 결제기관코드
				ocClaimPayment.setPymntOrganName(null); // 결제기관명
				ocClaimPayment.setPymntOrganNoText(null); // 결제기관번호(카드번호, 은행계좌번호)
				ocClaimPayment.setIntrstFreeYn(Const.BOOLEAN_FALSE); // 무이자여부
				ocClaimPayment.setInstmtTermCount((short) 0); // 할부기간
				ocClaimPayment.setCardGbnType(null); // 카드구분
				ocClaimPayment.setCardType(null); // 카드유형
				ocClaimPayment.setPymntAprvNoText(null); // 결제승인번호
				ocClaimPayment.setPymntTodoAmt(ocClaim.getAddDlvyAmt()); // 결제예정금액
				ocClaimPayment.setPymntAmt(ocClaim.getAddDlvyAmt()); // 결제금액
				ocClaimPayment.setCardPartCnclPsbltYn(Const.BOOLEAN_FALSE); // 카드부분취소가능여부
				ocClaimPayment.setCashRcptIssueYn(Const.BOOLEAN_FALSE); // 현금영수증발급여부
				ocClaimPayment.setEscrApplyYn(Const.BOOLEAN_FALSE); // 에스크로적용여부
				ocClaimPayment.setEscrAprvNoText(null); // 에스크로승인번호
				ocClaimPayment.setBankCode(null); // 환불:회원환불계좌, 환수:가상계좌발급
				ocClaimPayment.setAcntNoText(null); // 계좌번호
				ocClaimPayment.setAcntHldrName(null); // 예금주명
				ocClaimPayment.setAcntCrtfcYn(Const.BOOLEAN_FALSE); // 계좌인증여부
				ocClaimPayment.setVrtlAcntIssueDtm(null); // 가상계좌발급일시
				ocClaimPayment.setVrtlAcntExprDtm(null); // 가상계좌만료일시
				ocClaimPayment.setGiftCardPinNoText(null); // 상품권PIN번호
				ocClaimPayment.setRedempRfndMemoText(null); // 환수환불메모
				ocClaimPayment.setProcImpsbltYn(Const.BOOLEAN_FALSE); // 처리불가여부
				ocClaimPayment.setProcImpsbltRsnText(null); // 처리불가사유
				ocClaimPayment.setProcImpsbltCmlptDtm(null); // 처리불가완료일시
				ocClaimPayment.setRedempRfndOpetrNo(null); // 환수환불처리자번호
				ocClaimPayment.setRedempRfndOpetrDtm(null); // 환수환불처리일시
				ocClaimPayment.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 자사처리대상여부(교환 추가배송비는 재경팀 확인 N)
				ocClaimPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT); // 발생사유코드 - (클레임)배송비
				ocClaimPayment.setPymntStatCode(null); // 결제상태코드
				ocClaimPayment.setRspnsCodeText(null); // 응답코드
				ocClaimPayment.setRspnsMesgText(null); // 응답메시지
				ocClaimPayment.setPymntLogInfo(null); // 결제로그
				ocClaimPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_PUBLIC); // 이력구분 - 일반결제
				ocClaimPayment.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자
				ocClaimPayment.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자

				ocClaimPaymentDao.insertClaimPayment(ocClaimPayment); // 클레임결제 등록

				/*
				 * [클레임배송비 결제(KCP)] 가상계좌 결제 선택 시 KCP 가상계좌 요청, 가상계좌 요청 후 리턴결과를 이용하여 결제내역 업데이트
				 */
				// site no 에 따른 siteKey 구분
				if (UtilsText.equals(ocOrder.getSiteNo(), Const.SITE_NO_ART)) {
					kcpPaymentApproval.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
				} else {
					kcpPaymentApproval.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
				}

				PaymentResult paymentResult = paymentEntrance
						.approval(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, kcpPaymentApproval)); // KCP 결제

				// 결제 실패인 경우 exception
				if (UtilsText.equals(paymentResult.getSuccessYn(), Const.BOOLEAN_FALSE)) {
					throw new Exception("validMsg:".concat(paymentResult.getMessage()));
				}

				KcpPaymentApprovalReturn kcpPaymentApprovalReturn = ((KcpPaymentApprovalReturn) paymentResult
						.getData());

				List<SyCodeDetail> bankList = commonCodeService.getCodeNoName(CommonCode.BANK_CODE); // 은행코드 목록

				// 2020.03.23 : 신한은행의 경우 가상계좌발급시 은행코드는 "BK26"이므로 실시간계좌이체 "BK88"로 bankCode 조회
				if (UtilsText.equals(kcpPaymentApprovalReturn.getBankCode(), "BK26")) {
					kcpPaymentApprovalReturn.setBankCode("BK88");
				}

				// kcp 리턴 bankcode를 이용하여 공통코드의 bankcode 를 추출
				String commonBankCode = bankList.stream()
						.filter(x -> UtilsText.equals(x.getAddInfo1(), kcpPaymentApprovalReturn.getBankCode()))
						.map(SyCodeDetail::getCodeDtlNo).findFirst().orElse(null);

				// kcp 리턴 가상계좌발급일, 만료일 string 을 기준으로 날짜 데이터 생성
				SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DEFAULT_DATETIME_PATTERN_NOT_CHARACTERS);
				Date acntIssueDate = dateFormat.parse(kcpPaymentApprovalReturn.getAppTime());
				Date acntExprDate = dateFormat.parse(kcpPaymentApprovalReturn.getVaDate());

				OcClaimPayment virtualPayment = new OcClaimPayment();
				virtualPayment.setPymntOrganCodeText(kcpPaymentApprovalReturn.getBankCode()); // 결제기관코드(카드, 은행코드...)
				virtualPayment.setPymntOrganName(kcpPaymentApprovalReturn.getBankName()); // 결제기관명(카드, 은행명...)
				virtualPayment.setPymntOrganNoText(kcpPaymentApprovalReturn.getAccount()); // 결제기관번호(카드번호, 은행계좌번호...)
				virtualPayment.setPymntAprvNoText(kcpPaymentApprovalReturn.getTno()); // 결제승인번호
				virtualPayment.setBankCode(commonBankCode); // 환불:회원환불계좌, 환수:가상계좌발급
				virtualPayment.setAcntNoText(kcpPaymentApprovalReturn.getAccount()); // 계좌번호
				virtualPayment.setAcntHldrName(kcpPaymentApprovalReturn.getDepositor()); // 예금주명
				virtualPayment.setVrtlAcntIssueDtm(new Timestamp(acntIssueDate.getTime())); // 가상계좌발급일시
				virtualPayment.setVrtlAcntExprDtm(new Timestamp(acntExprDate.getTime())); // 가상계좌만료일시
				virtualPayment.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_WAIT_DEPOSIT); // 결제상태코드
				virtualPayment.setRspnsCodeText(kcpPaymentApprovalReturn.getResCd()); // 응답코드
				virtualPayment.setRspnsMesgText(kcpPaymentApprovalReturn.getResMsg()); // 응답메시지
				virtualPayment.setPymntLogInfo(new ObjectMapper().writeValueAsString(kcpPaymentApprovalReturn)); // 결제로그
				virtualPayment.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자
				virtualPayment.setClmNo(ocClaim.getClmNo());
				virtualPayment.setClmPymntSeq(ocClaimPayment.getClmPymntSeq());

				try {
					// 가상계좌요청 시 클레임결제 수정(kcp 리턴 값 사용)
					ocClaimPaymentDao.updateClaimPaymentRequestVirtualAccount(virtualPayment);
				} catch (Exception e) {
					// 클레임결제 수정이 실패할 경우 kcp 가상계좌 요청 취소
					KcpPaymentCancel kcpPaymentCancel = new KcpPaymentCancel();

					// site no 에 따른 siteCd, siteKey 구분
					if (UtilsText.equals(ocOrder.getSiteNo(), Const.SITE_NO_ART)) {
						kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.claim.siteCode"));
						kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
					} else {
						kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.ots.claim.siteCode"));
						kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
					}

					kcpPaymentCancel.setTno(kcpPaymentApprovalReturn.getTno()); // KCP 원거래 거래번호
					kcpPaymentCancel.setModType(CommonCode.KCP_MOD_TYPE_STSC); // 전체취소 STSC / 부분취소 STPC
					kcpPaymentCancel.setCustIp(kcpPaymentApproval.getCustIp()); // 변경 요청자 IP
					kcpPaymentCancel.setModDesc("가맹점 처리 실패"); // 취소 사유

					// 가상계좌 취소(가상계좌 사용중지-환불아님)
					paymentEntrance.cancel(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, kcpPaymentCancel));

					throw new Exception("validMsg:결제 에러 발생");
				}

			} else { // 가상계좌 결제가 아닌 무료쿠폰 사용 시
				/*
				 * [회원 보유쿠폰 사용 업데이트]
				 */
				MbMemberCoupon mbMemberCoupon = new MbMemberCoupon();
				mbMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_USED);
				mbMemberCoupon.setMemberNo(ocOrder.getMemberNo());
				mbMemberCoupon.setHoldCpnSeq(ocClaim.getHoldCpnSeq());
				mbMemberCoupon.setModerNo((LoginManager.getUserDetails().getAdminNo())); // 수정자

				mbMemberCouponDao.updateMemberCouponUseInfo(mbMemberCoupon); // 회원 보유쿠폰 사용 업데이트
			}
		}

		// 메세지, 메일 발송
		try {
			claimMessageService.exchangeClaimAcceptByAdmin(ocClaim);
		} catch (Exception e) {
			log.error("BO교환접수 메세지 메일 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 클레임 반품 등록
	 * @Method Name : registClaimReturn
	 * @Date : 2019. 7. 22.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocClaimProducts
	 * @param ocClaimMemo
	 * @param kcpPaymentApproval
	 * @throws Exception
	 */
	public void registClaimReturn(OcClaim ocClaim, OcClaimProduct[] ocClaimProducts, OcClaimMemo ocClaimMemo,
			KcpPaymentApproval kcpPaymentApproval) throws Exception {

		OcClaimAmountVO ocClaimAmountVO = new OcClaimAmountVO();

		String clmStatCode = "";
		String clmPrdtStatCode = "";

		// 2020.03.25 : 클레임 사유 코드가 오프라인반품 10019 일때 예외처리
		// 2020.04.02 : 클레임 사유 코드가 미출반품 10018 일때 예외처리
		boolean isNormalAccept = true;
		for (OcClaimProduct ocp : ocClaimProducts) {
			// 2020.04.07 반품중인 상품이 배송중일 경우 배송완료 상태로 변경
			if (UtilsText.equals(ocp.getDlvyStatCode(), CommonCode.DLVY_STAT_CODE_DELIVERY_ING)) {
				deliveryService.setOrderDeliveryPrdt(ocp.getOrderNo(), ocp.getOrderPrdtSeq());
			}

			// BaseCommonCode에 없으므로 임시로 set
			if (UtilsText.equals(ocp.getClmRsnCode(), "10019") || UtilsText.equals(ocp.getClmRsnCode(), "10018")) {
				isNormalAccept = false;
			}
		}

		if (isNormalAccept) {
			// 기존 로직
			// 클레임상태코드 - 반품접수
			clmStatCode = CommonCode.CLM_STAT_CODE_RETURN_ACCEPT;
			// 클레임상품상태코드 - 반품접수
			clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT;
		} else {
			// 클레임 사유 코드가 오프라인 반품/미출반품 이라면 수거지시부터
			// 클레임상태코드 - 수거지시
			clmStatCode = CommonCode.CLM_STAT_CODE_RETURN_PICKUP_ORDER;
			// 클레임상품상태코드 - 수거지시
			clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_RETURN_PICKUP_ORDER;
		}

		// 2020.05.07 : 비회원주문전용 AS반품접수여부 Y일때, 심의완료상태로 AS불가반품으로 생성한다.
		if (UtilsText.equals(ocClaimProducts[0].getAsReturnYn(), Const.BOOLEAN_TRUE)) {
			// 클레임상태코드 - 심의완료
			clmStatCode = CommonCode.CLM_STAT_CODE_RETURN_JUDGE_FINISH;
			// 클레임상품상태코드 - 심의완료
			clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_RETURN_JUDGE_FINISH;
			for (OcClaimProduct ocp : ocClaimProducts) {
				ocp.setClmProcTypeCode(CommonCode.CLM_PROC_TYPE_CODE_RETURN_AS_IMPOSSIBLE); // 클레임 처리유형 코드(AS불가반품)
				ocp.setClmProcContText(ocp.getClmEtcRsnText()); // 클레임처리내용
			}
		}
		
		ocClaim.setClaimRgsterNo(LoginManager.getUserDetails().getAdminNo());

		/*
		 * 기본 validate
		 */
		OcClaimProduct validClaimProduct = new OcClaimProduct();
		Integer[] tempClaimProductPrdtSeqs = new Integer[ocClaimProducts.length];

		for (int i = 0; i < ocClaimProducts.length; i++) {
			tempClaimProductPrdtSeqs[i] = ocClaimProducts[i].getOrderPrdtSeq();
		}

		validClaimProduct.setOrgOrderNo(ocClaim.getOrderNo());
		validClaimProduct.setClmGbnCode(CommonCode.CLM_GBN_CODE_RETURN);
		validClaimProduct.setOrderPrdtSeqs(tempClaimProductPrdtSeqs);

		Map<String, Object> validMap = this.validateClaimAccept(validClaimProduct);

		if (UtilsText.equals((String) validMap.get("success"), Const.BOOLEAN_FALSE)) {
			throw new Exception("validMsg:".concat((String) validMap.get("message")));
		}

		/*
		 * [원 주문정보 get]
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보

		/*
		 * [원 주문상품 목록 - 사은품/배송비 포함]
		 */
		OcOrderProduct ocOrderProduct = new OcOrderProduct();
		ocOrderProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 추출용
		ocOrderProduct.setOrderNo(ocOrder.getOrgOrderNo()); // 원주문번호 기준

		List<OcOrderProduct> orgOrderProductList = ocOrderProductDao.selectOrgOrderProductList(ocOrderProduct);

		ocClaimAmountVO.setOrgOrderProductList(orgOrderProductList);

		/*
		 * [주문자 회원 정보]
		 */
		MbMember mbMember = memberDao.selectMemberDefalutInfo(ocOrder.getMemberNo());

		/*
		 * [기본 등록정보 set]
		 */
		// ocClaim.setClmGbnCode(CommonCode.CLM_GBN_CODE_RETURN); // 클레임구분코드
		ocClaim.setOrderNo(ocClaim.getOrgOrderNo());
		ocClaim.setRtrvlGbnType(CommonCode.RTRVL_GBN_TYPE_ONLINE); // 회수지구분(O:온라인)
		ocClaim.setRtrvlStoreNo(null); // 회수매장번호
		ocClaim.setRtrvlReqYn(Const.BOOLEAN_FALSE); // 회수신청여부

		ocClaim.setDlvyTypeCode(CommonCode.DLVY_TYPE_CODE_NORMAL_LOGISTICS); // 배송유형코드-일반택배

		// 주문자 회원 정보에 환불계좌 정보가 있으면 set
		if (UtilsText.isNotEmpty(mbMember.getBankCode()) && UtilsText.isNotEmpty(mbMember.getAcntNoText())
				&& UtilsText.isNotEmpty(mbMember.getAcntHldrName())) {
			ocClaim.setBankCode(mbMember.getBankCode());
			ocClaim.setRfndAcntText(mbMember.getAcntNoText());
			ocClaim.setAcntHldrName(mbMember.getAcntHldrName());
		}

		ocClaim.setVndrNo(ocClaimProducts[0].getVndrNo()); // 업체번호(클레임상품 중 한개 발췌-업체단위로 클레임 걸림)

		ocClaim.setTotalRfndAmt(0); // 총환불금액
		ocClaim.setTotalRedempAmt(0); // 총환수금액

		// 미처리 여부 set
		if (UtilsText.isEmpty(ocClaim.getUnProcYn())) {
			ocClaim.setUnProcYn(Const.BOOLEAN_FALSE);
		}

		ocClaim.setAdminAcceptYn(Const.BOOLEAN_TRUE); // 관리자접수여부
		ocClaim.setOflnAcceptYn(Const.BOOLEAN_FALSE); // 오프라인접수여부

		// 추가배송비 관련 set
		if (ocClaim.getAddDlvyAmt() > 0) {
			// form에서 교환배송비 결제방법을 무료교환쿠폰 선택이 아니면 null
			if (!UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(), CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON)) {
				ocClaim.setHoldCpnSeq(null); // 보유쿠폰순번
			}
		} else {
			ocClaim.setAddDlvyAmtPymntType(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE); // 추가배송비결제방법
			ocClaim.setAddDlvyAmt(0); // 추가배송비
			ocClaim.setHoldCpnSeq(null); // 보유쿠폰순번
		}

		ocClaim.setClmStatCode(clmStatCode); // 클레임상태코드
		ocClaim.setBranchMoveCode(CommonCode.BRANCH_MOVE_CODE_ORIGIN_DEAL); // 점간이동코드 - 원거래

		ocClaim.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자
		ocClaim.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자

		// 2020.04.06 : 원주문번호와 주문번호가 다를 때 리오더의 주문번호를 적용한다.
		// 주문상품끼리 서로 다른 주문번호는 스크립트상으로 방지되어 있다.
		for (OcClaimProduct ocp : ocClaimProducts) {
			
			// 2020.04.13 : 신청팝업창에서 던져지는 원주문번호 대상주문상품에 set
			ocp.setOrgOrderNo(ocClaim.getOrgOrderNo());
			
			log.error("OcClaimProduct 원주문번호 : {}", ocp.getOrgOrderNo());
			log.error("OcClaimProduct 주문번호 : {}", ocp.getOrderNo());
			if (!UtilsText.equals(ocp.getOrgOrderNo(), ocp.getOrderNo())) {
				ocClaim.setOrgOrderNo(ocp.getOrgOrderNo());
				ocClaim.setOrderNo(ocp.getOrderNo());
			}
		}
		log.error("OcClaim 원주문번호 : {}", ocClaim.getOrgOrderNo());
		log.error("OcClaim 주문번호 : {}", ocClaim.getOrderNo());

		/*
		 * [클레임 등록(마스터)] 등록 시 생성된 clmNo 를 ocClaim 에서 가지고 있음
		 */
		ocClaimDao.insertClaimInfo(ocClaim);

		/*
		 * [클레임상품 등록] [클레임상태이력 등록]
		 */
		for (OcClaimProduct ocClaimProduct : ocClaimProducts) {
			// 클레임상품 등록, 클레임상태이력 등록
			this.insertClaimPrdtAndClaimStatusHistory(ocClaim, ocClaimProduct, clmPrdtStatCode);

			// 해당 상품에 사은품이 있는 경우 사은품 한번 더 등록
			if (!ObjectUtils.isEmpty(ocClaimProduct.getOrderGiftPrdtSeq())) {
				OcClaimProduct giftClaimProduct = new OcClaimProduct();

				BeanUtils.copyProperties(ocClaimProduct, giftClaimProduct);

				giftClaimProduct.setOrderPrdtSeq(ocClaimProduct.getOrderGiftPrdtSeq()); // 사은품 주문상품순번으로 대체

				this.insertClaimPrdtAndClaimStatusHistory(ocClaim, giftClaimProduct, clmPrdtStatCode);
			}
		}

		/*************************************
		 * 클레임 금액 계산(클레임 마스터/상품 등록 후 호출)
		 *************************************/
		// ocClaimAmountVO 에 계산된 금액 set
		claimProcService.setClaimAmountCalcForPartCancel(ocClaim, ocOrder, ocClaimAmountVO,
				CommonCode.CLM_GBN_CODE_RETURN, false);

		/*
		 * 클레임 계산 결과 환수금이 더 많은 경우 취소 불가 처리
		 */
		if (ocClaimAmountVO.getRefundCnclAmt() < 0) {
			throw new Exception("validMsg:환수금이 발생하여 취소가 불가합니다.");
		}

		/*
		 * 증감 구매적립 포인트 계산
		 */
		int thisClaimVariationSavePoint = ocClaimAmountVO.getVariationSavePoint(); // 현재 클레임 증감 적립포인트

		// 더블적립 쿠폰이 사용된 경우
		if (ocClaimAmountVO.getOrderDoubleDscntCpnInfo() != null) {
			// 주문의 구매적립률을 이용하여 계산하므로 주석처리
			// thisClaimVariationSavePoint = thisClaimVariationSavePoint * 2;
		}

		/*
		 * 구매적립 포인트 환수 체크(임직원이 아닌 멤버쉽 회원인 경우만 체크)
		 */
		int clawbackPoint = 0; // 환수포인트

		if (UtilsText.equals(ocOrder.getMemberTypeCode(), CommonCode.MEMBER_TYPE_CODE_MEMBERSHIP)
				&& UtilsText.equals(ocOrder.getEmpYn(), Const.BOOLEAN_FALSE)) {

			// 환수할 포인트가 발생한 경우
			if (thisClaimVariationSavePoint < 0) {
				// 환수포인트 조회 인터페이스
				try {
					clawbackPoint = membershipPointService.getClawbackPoint(mbMember.getSafeKey(),
							mbMember.getSafeKeySeq(), ocOrder.getOrgOrderNo(), Math.abs(thisClaimVariationSavePoint));
				} catch (Exception e) {
					throw new Exception("validMsg:포인트 조회에 실패했습니다.");
				}
			}
		}

		/*
		 * 클레임 마스터 금액 업데이트(변동 적립포인트, 환수포인트 포함)
		 */
		OcClaim updateAmtClaim = new OcClaim();
		updateAmtClaim.setClmNo(ocClaim.getClmNo());
		updateAmtClaim.setTotalRfndAmt(ocClaimAmountVO.getRefundCnclAmt()); // 총환불금액
		updateAmtClaim.setTotalRedempAmt(ocClaimAmountVO.getRedempAmtByMultiBuy() + ocClaimAmountVO.getClaimDlvyAmt()); // 총환수금액
		updateAmtClaim.setIrdsSavePoint((int) ocClaimAmountVO.getVariationSavePoint()); // 증감적립포인트
		updateAmtClaim.setRedempSavePoint(clawbackPoint); // 환수적립포인트
		updateAmtClaim.setModerNo(LoginManager.getUserDetails().getAdminNo());

		ocClaimDao.updateClaimStat(updateAmtClaim);

		/*
		 * 클레임 결제 히스토리 등록
		 */
		// 클레임 배송비 환불금 차감인 경우 히스토리 등록
		if (ocClaim.getAddDlvyAmt() > 0) {
			if (UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(),
					CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_REFUND_DEDUCTION)) {
				// 결제정보 set(히스토리용 이기 때문에 실결제와 관련된 내용은 의미없어도 무방)
				OcClaimPayment _refundClaimDlvyAmt = new OcClaimPayment(); // 결제

				_refundClaimDlvyAmt.setClmNo(ocClaim.getClmNo());
				_refundClaimDlvyAmt.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP); // 환수환불구분:환수
				_refundClaimDlvyAmt.setPymntDtm(new Timestamp(new Date().getTime())); // 결제일시
				_refundClaimDlvyAmt.setDeviceCode(ocOrder.getDeviceCode()); // 디바이스코드
				_refundClaimDlvyAmt.setMainPymntMeansYn(Const.BOOLEAN_TRUE); // 주결제수단여부
				_refundClaimDlvyAmt.setPymntMeansCode(CommonCode.PYMNT_MEANS_CODE_CARD); // 결제수단코드:임의로 아무거나
				_refundClaimDlvyAmt.setPymntVndrCode(null); // 결제업체코드
				_refundClaimDlvyAmt.setInstmtTermCount((short) 0); // 할부기간
				_refundClaimDlvyAmt.setPymntTodoAmt(ocClaim.getAddDlvyAmt()); // 결제예정금액
				_refundClaimDlvyAmt.setPymntAmt(ocClaim.getAddDlvyAmt()); // 결제금액
				_refundClaimDlvyAmt.setCardPartCnclPsbltYn(Const.BOOLEAN_FALSE); // 카드부분취소가능여부
				_refundClaimDlvyAmt.setCashRcptIssueYn(Const.BOOLEAN_FALSE); // 현금영수증발급여부
				_refundClaimDlvyAmt.setEscrApplyYn(Const.BOOLEAN_FALSE); // 에스크로적용여부
				_refundClaimDlvyAmt.setAcntCrtfcYn(Const.BOOLEAN_FALSE); // 계좌인증여부
				_refundClaimDlvyAmt.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 자사처리대상여부
				_refundClaimDlvyAmt.setHistGbnType(CommonCode.HIST_GBN_TYPE_HISTORY); // 이력구분 - 이력근거용
				_refundClaimDlvyAmt.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT); // 발생사유코드:(클레임)배송비
				_refundClaimDlvyAmt.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH); // 결제완료
				_refundClaimDlvyAmt.setVndrNo(ocClaimProducts[0].getVndrNo()); // 업체번호
				_refundClaimDlvyAmt.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자
				_refundClaimDlvyAmt.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자

				// 클레임 결제 이력데이터 등록
				ocClaimPaymentDao.insertClaimPayment(_refundClaimDlvyAmt);
			}
		}

		/*
		 * 환수프로모션비(다족구매로 인해 발생) 발생된 경우 결제히스토리 등록
		 */
		if (ocClaimAmountVO.getRedempAmtByMultiBuy() > 0) {
			for (OcClaimPayment redempMultiBuyPayment : ocClaimAmountVO.getRedempMultiBuyPaymentList()) {
				ocClaimPaymentDao.insertClaimPayment(redempMultiBuyPayment);
			}
		}

		/*
		 * 기존 다족구매 리오더 매출로 인해 변경된 금액과 원 주문 기준 취소상품 금액 차이 결제히스토리 등록(현재 취소금을 맞추기 위한 이력)
		 */
		if (ocClaimAmountVO.getAddMultiBuyDifferAmt() > 0) {
			for (OcClaimPayment addMultiBuyDifferPaymentList : ocClaimAmountVO.getAddMultiBuyDifferPaymentList()) {
				ocClaimPaymentDao.insertClaimPayment(addMultiBuyDifferPaymentList);
			}
		}

		/*
		 * 클레임 결제 등록
		 */
		// 주 결제 수단
		if (ocClaimAmountVO.getMainPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getMainPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getMainCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 기프트
		if (ocClaimAmountVO.getGiftPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getGiftPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getGiftCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 이벤트 포인트
		if (ocClaimAmountVO.getEventPointPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getEventPointPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getEventPointCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소
																									// 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 포인트
		if (ocClaimAmountVO.getPointPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getPointPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getPointCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로
																								// 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		/*
		 * [메모 등록] 클레임 상품 단위가 아닌 클레임 단위, 클레임 등록 시 메모 text가 입력된 상태만 등록
		 */
		if (UtilsText.isNotEmpty(ocClaimMemo.getMemoText())) {
			ocClaimMemo.setClmNo(ocClaim.getClmNo());
			ocClaimMemo.setOrgOrderNo(ocClaim.getOrgOrderNo());
			ocClaimMemo.setClmPrdtSeq(null);
			ocClaimMemo.setClmGbnCode(CommonCode.CLM_GBN_CODE_CANCEL); // 클레임구분코드-취소
			ocClaimMemo.setRgsterNo(ocClaim.getClaimRgsterNo());

			ocClaimMemoDao.insertClaimMemo(ocClaimMemo); // 메모 등록
		}

		/*
		 * 클레임배송비 처리
		 */
		if (ocClaim.getAddDlvyAmt() > 0) {
			if (UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(), CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT)) {
				/*
				 * [클레임결제 등록] 클레임배송비 결제
				 */
				OcClaimPayment ocClaimPayment = new OcClaimPayment();
				ocClaimPayment.setClmNo(ocClaim.getClmNo()); // 클레임번호
				ocClaimPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP); // 환수환불구분-환수
				// ocClaimPayment.setPymntDtm(new Timestamp(new Date().getTime())); // 결제일시
				ocClaimPayment.setPymntDtm(null); // 결제일시
				ocClaimPayment.setDeviceCode(ocOrder.getDeviceCode()); // 디바이스코드
				ocClaimPayment.setMainPymntMeansYn(Const.BOOLEAN_TRUE); // 주결제수단여부
				ocClaimPayment.setPymntMeansCode(CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT); // 결제수단코드-가상계좌
				ocClaimPayment.setPymntVndrCode(CommonCode.PYMNT_VNDR_CODE_KCP); // 결제업체코드
				ocClaimPayment.setPymntOrganCodeText(null); // 결제기관코드
				ocClaimPayment.setPymntOrganName(null); // 결제기관명
				ocClaimPayment.setPymntOrganNoText(null); // 결제기관번호(카드번호, 은행계좌번호)
				ocClaimPayment.setIntrstFreeYn(Const.BOOLEAN_FALSE); // 무이자여부
				ocClaimPayment.setInstmtTermCount((short) 0); // 할부기간
				ocClaimPayment.setCardGbnType(null); // 카드구분
				ocClaimPayment.setCardType(null); // 카드유형
				ocClaimPayment.setPymntAprvNoText(null); // 결제승인번호
				ocClaimPayment.setPymntTodoAmt(ocClaim.getAddDlvyAmt()); // 결제예정금액
				ocClaimPayment.setPymntAmt(ocClaim.getAddDlvyAmt()); // 결제금액
				ocClaimPayment.setCardPartCnclPsbltYn(Const.BOOLEAN_FALSE); // 카드부분취소가능여부
				ocClaimPayment.setCashRcptIssueYn(Const.BOOLEAN_FALSE); // 현금영수증발급여부
				ocClaimPayment.setEscrApplyYn(Const.BOOLEAN_FALSE); // 에스크로적용여부
				ocClaimPayment.setEscrAprvNoText(null); // 에스크로승인번호
				ocClaimPayment.setBankCode(null); // 환불:회원환불계좌, 환수:가상계좌발급
				ocClaimPayment.setAcntNoText(null); // 계좌번호
				ocClaimPayment.setAcntHldrName(null); // 예금주명
				ocClaimPayment.setAcntCrtfcYn(Const.BOOLEAN_FALSE); // 계좌인증여부
				ocClaimPayment.setVrtlAcntIssueDtm(null); // 가상계좌발급일시
				ocClaimPayment.setVrtlAcntExprDtm(null); // 가상계좌만료일시
				ocClaimPayment.setGiftCardPinNoText(null); // 상품권PIN번호
				ocClaimPayment.setRedempRfndMemoText(null); // 환수환불메모
				ocClaimPayment.setProcImpsbltYn(Const.BOOLEAN_FALSE); // 처리불가여부
				ocClaimPayment.setProcImpsbltRsnText(null); // 처리불가사유
				ocClaimPayment.setProcImpsbltCmlptDtm(null); // 처리불가완료일시
				ocClaimPayment.setRedempRfndOpetrNo(null); // 환수환불처리자번호
				ocClaimPayment.setRedempRfndOpetrDtm(null); // 환수환불처리일시
				ocClaimPayment.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 자사처리대상여부(교환 추가배송비는 재경팀 확인 N)
				ocClaimPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT); // 발생사유코드 - 배송비
				ocClaimPayment.setPymntStatCode(null); // 결제상태코드
				ocClaimPayment.setRspnsCodeText(null); // 응답코드
				ocClaimPayment.setRspnsMesgText(null); // 응답메시지
				ocClaimPayment.setPymntLogInfo(null); // 결제로그
				ocClaimPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_PUBLIC); // 이력구분 - 일반결제
				ocClaimPayment.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자
				ocClaimPayment.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자

				ocClaimPaymentDao.insertClaimPayment(ocClaimPayment); // 클레임결제 등록

				/*
				 * [클레임배송비 결제(KCP)] 가상계좌 결제 선택 시 KCP 가상계좌 요청, 가상계좌 요청 후 리턴결과를 이용하여 결제내역 업데이트
				 */
				// site no 에 따른 siteKey 구분
				if (UtilsText.equals(ocOrder.getSiteNo(), Const.SITE_NO_ART)) {
					kcpPaymentApproval.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
				} else {
					kcpPaymentApproval.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
				}

				PaymentResult paymentResult = paymentEntrance
						.approval(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, kcpPaymentApproval)); // KCP 결제

				// 결제 실패인 경우 exception
				if (UtilsText.equals(paymentResult.getSuccessYn(), Const.BOOLEAN_FALSE)) {
					throw new Exception("validMsg:".concat(paymentResult.getMessage()));
				}
				log.error("가상계좌 paymentResult : " + paymentResult.toString());

				KcpPaymentApprovalReturn kcpPaymentApprovalReturn = ((KcpPaymentApprovalReturn) paymentResult
						.getData());

				List<SyCodeDetail> bankList = commonCodeService.getCodeNoName(CommonCode.BANK_CODE); // 은행코드 목록

				// 2020.03.23 : 신한은행의 경우 가상계좌발급시 은행코드는 "BK26"이므로 실시간계좌이체 "BK88"로 bankCode 조회
				if (UtilsText.equals(kcpPaymentApprovalReturn.getBankCode(), "BK26")) {
					kcpPaymentApprovalReturn.setBankCode("BK88");
				}

				// kcp 리턴 bankcode를 이용하여 공통코드의 bankcode 를 추출
				String commonBankCode = bankList.stream()
						.filter(x -> UtilsText.equals(x.getAddInfo1(), kcpPaymentApprovalReturn.getBankCode()))
						.map(SyCodeDetail::getCodeDtlNo).findFirst().orElse(null);

				// kcp 리턴 가상계좌발급일, 만료일 string 을 기준으로 날짜 데이터 생성
				SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DEFAULT_DATETIME_PATTERN_NOT_CHARACTERS);
				Date acntIssueDate = dateFormat.parse(kcpPaymentApprovalReturn.getAppTime());
				Date acntExprDate = dateFormat.parse(kcpPaymentApprovalReturn.getVaDate());

				OcClaimPayment virtualPayment = new OcClaimPayment();
				virtualPayment.setPymntOrganCodeText(kcpPaymentApprovalReturn.getBankCode()); // 결제기관코드(카드, 은행코드...)
				virtualPayment.setPymntOrganName(kcpPaymentApprovalReturn.getBankName()); // 결제기관명(카드, 은행명...)
				virtualPayment.setPymntOrganNoText(kcpPaymentApprovalReturn.getAccount()); // 결제기관번호(카드번호, 은행계좌번호...)
				virtualPayment.setPymntAprvNoText(kcpPaymentApprovalReturn.getTno()); // 결제승인번호
				virtualPayment.setBankCode(commonBankCode); // 환불:회원환불계좌, 환수:가상계좌발급
				virtualPayment.setAcntNoText(kcpPaymentApprovalReturn.getAccount()); // 계좌번호
				virtualPayment.setAcntHldrName(kcpPaymentApprovalReturn.getDepositor()); // 예금주명
				virtualPayment.setVrtlAcntIssueDtm(new Timestamp(acntIssueDate.getTime())); // 가상계좌발급일시
				virtualPayment.setVrtlAcntExprDtm(new Timestamp(acntExprDate.getTime())); // 가상계좌만료일시
				virtualPayment.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_WAIT_DEPOSIT); // 결제상태코드
				virtualPayment.setRspnsCodeText(kcpPaymentApprovalReturn.getResCd()); // 응답코드
				virtualPayment.setRspnsMesgText(kcpPaymentApprovalReturn.getResMsg()); // 응답메시지
				virtualPayment.setPymntLogInfo(new ObjectMapper().writeValueAsString(kcpPaymentApprovalReturn)); // 결제로그
				virtualPayment.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자
				virtualPayment.setClmNo(ocClaim.getClmNo());
				virtualPayment.setClmPymntSeq(ocClaimPayment.getClmPymntSeq());

				try {
					// 가상계좌요청 시 클레임결제 수정(kcp 리턴 값 사용)
					ocClaimPaymentDao.updateClaimPaymentRequestVirtualAccount(virtualPayment);
				} catch (Exception e) {
					// 클레임결제 수정이 실패할 경우 kcp 가상계좌 요청 취소
					KcpPaymentCancel kcpPaymentCancel = new KcpPaymentCancel();

					// site no 에 따른 siteCd, siteKey 구분
					if (UtilsText.equals(ocOrder.getSiteNo(), Const.SITE_NO_ART)) {
						kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.claim.siteCode"));
						kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
					} else {
						kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.ots.claim.siteCode"));
						kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
					}

					kcpPaymentCancel.setTno(kcpPaymentApprovalReturn.getTno()); // KCP 원거래 거래번호
					kcpPaymentCancel.setModType(CommonCode.KCP_MOD_TYPE_STSC); // 전체취소 STSC / 부분취소 STPC
					kcpPaymentCancel.setCustIp(kcpPaymentApproval.getCustIp()); // 변경 요청자 IP
					kcpPaymentCancel.setModDesc("가맹점 처리 실패"); // 취소 사유

					// 가상계좌 취소(가상계좌 사용중지-환불아님)
					paymentEntrance.cancel(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, kcpPaymentCancel));

					throw new Exception("validMsg:결제 에러 발생");
				}

			} else { // 가상계좌 결제가 아닌 무료쿠폰 사용 시
				/*
				 * [회원 보유쿠폰 사용 업데이트]
				 */
				MbMemberCoupon mbMemberCoupon = new MbMemberCoupon();
				mbMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_USED);
				mbMemberCoupon.setMemberNo(ocOrder.getMemberNo());
				mbMemberCoupon.setHoldCpnSeq(ocClaim.getHoldCpnSeq());
				mbMemberCoupon.setModerNo((LoginManager.getUserDetails().getAdminNo())); // 수정자

				mbMemberCouponDao.updateMemberCouponUseInfo(mbMemberCoupon); // 회원 보유쿠폰 사용 업데이트
			}
		}

		// 메세지, 메일 발송
		try {
			claimMessageService.returnClaimAcceptByAdmin(ocClaim);
		} catch (Exception e) {
			log.error("BO반품접수 메세지 메일 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 클레임상품 등록, 클레임상태이력 등록
	 * @Method Name : insertClaimPrdtAndClaimStatusHistory
	 * @Date : 2019. 4. 10.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocClaimProduct
	 * @param clmPrdtStatCode
	 * @throws Exception
	 */
	public void insertClaimPrdtAndClaimStatusHistory(OcClaim ocClaim, OcClaimProduct ocClaimProduct,
			String clmPrdtStatCode) throws Exception {
		ocClaimProduct.setClmNo(ocClaim.getClmNo());

		if (ObjectUtils.isEmpty(ocClaimProduct.getOrderNo())) {
			ocClaimProduct.setOrderNo(ocClaim.getOrderNo());
		} else {
			ocClaimProduct.setOrderNo(ocClaimProduct.getOrderNo());
		}

		ocClaimProduct.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자
		ocClaimProduct.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자
		ocClaimProduct.setClmPrdtStatCode(clmPrdtStatCode); // 클레임상품상태코드

		ocClaimProductDao.insertClaimProduct(ocClaimProduct); // 클레임상품 등록

		OcClaimStatusHistory ocClaimStatusHistory = new OcClaimStatusHistory();
		ocClaimStatusHistory.setClmNo(ocClaim.getClmNo());
		ocClaimStatusHistory.setClmPrdtSeq(ocClaimProduct.getClmPrdtSeq());
		ocClaimStatusHistory.setClmPrdtStatCode(clmPrdtStatCode); // 클레임상품상태코드
		ocClaimStatusHistory.setStockGbnCode(null);
		ocClaimStatusHistory.setNoteText(null);
		ocClaimStatusHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자

		ocClaimStatusHistoryDao.insertClaimStatusHistory(ocClaimStatusHistory); // 클레임상태이력 등록
	}

	/**
	 * @Desc : 클레임 취소 상품 저장(업데이트)
	 * @Method Name : updateClaimCancelProduct
	 * @Date : 2019. 7. 23.
	 * @Author : KTH
	 * @param ocClaimProducts
	 * @throws Exception
	 */
	public void updateClaimCancelProduct(OcClaimProduct[] ocClaimProducts) throws Exception {
		for (OcClaimProduct ocClaimProduct : ocClaimProducts) {
			if (!ObjectUtils.isEmpty(ocClaimProduct.getClmPrdtSeq())) {
				ocClaimProduct.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자번호

				ocClaimProductDao.updateClaimProduct(ocClaimProduct);
			}
		}
	}

	/**
	 * @Desc : 클레임 교환 상품 저장(업데이트)
	 * @Method Name : updateClaimExchangeProduct
	 * @Date : 2019. 7. 23.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocClaimProducts
	 * @throws Exception
	 */
	public void updateClaimExchangeProduct(OcClaim ocClaim, OcClaimProduct[] ocClaimProducts) throws Exception {
		/*
		 * 클레임 정보
		 */
		ocClaim = ocClaimDao.selectClaimBasisInfo(ocClaim); // 클레임 정보

		for (OcClaimProduct ocClaimProduct : ocClaimProducts) {
			if (!ObjectUtils.isEmpty(ocClaimProduct.getClmPrdtSeq())) {
				/*
				 * 넘어온 폼 값 조정(고객 알림 내용은 상시 업데이트 가능)
				 */
				// 교환접수
				if (UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_ACCEPT)) {
					ocClaimProduct.setClmProcTypeCode(null); // 처리(심의)유형 업데이트 제외
					ocClaimProduct.setClmProcContText(null); // 처리(심의)유형 업데이트 제외
					ocClaimProduct.setRecptYn(null); // 수령완료 업데이트 제외
				}
				// 수거지시
				else if (UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_PICKUP_ORDER)) {
					// none
				}
				// 수령완료
				else if (UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_RECEIVE_FINISH)) {
					ocClaimProduct.setRecptYn(null); // 수령완료 업데이트 제외
				}
				// 심의완료
				else if (UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_JUDGE_FINISH)) {
					// ocClaimProduct.setClmProcTypeCode(null); // 처리(심의)유형 업데이트 제외
					// 2020.03.19 : 심의완료에도 수정 가능하게 변경
					// ocClaimProduct.setClmProcContText(null); // 처리(심의)유형 내용 업데이트 제외
					// 2020.03.19 : 심의완료에도 수정 가능하게 변경
					ocClaimProduct.setRecptYn(null); // 수령완료 업데이트 제외

					// 2020.03.19 : 심의완료에는 상품을 교환불가로 저장할 수 없다.
					if (UtilsText.equals(ocClaimProduct.getClmProcTypeCode(),
							CommonCode.CLM_PROC_TYPE_CODE_EXCHANGE_IMPOSSIBLE)) {
						throw new Exception("validMsg:심의완료가 된 상태에서는 상품을 교환불가로 저장할 수 없습니다.");
					}
				}

				/*
				 * 클레임 상품 업데이트
				 */
				ocClaimProduct.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자번호

				ocClaimProductDao.updateClaimProduct(ocClaimProduct); // 회수상품 기준 수정

				// 교환접수, 수거지시, 수령완료, 심의완료 상태에서 재배송 상품 옵션 변경에 대한 값 조정
				if (UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_ACCEPT)
						|| UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_PICKUP_ORDER)
						|| UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_RECEIVE_FINISH)
						|| UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_JUDGE_FINISH)) {

					/*
					 * 재배송 상품 수정(교환 옵션)
					 */
					OcClaimProduct exchangeClaimProduct = new OcClaimProduct();

					BeanUtils.copyProperties(ocClaimProduct, exchangeClaimProduct);
					exchangeClaimProduct.setUpClmPrdtSeq(ocClaimProduct.getClmPrdtSeq()); // 회수 상품 기준의 클레임상품순번

					if (UtilsText.equals(ocClaimProduct.getClmRsnCode(),
							CommonCode.CLM_RSN_CODE_EXCHANGE_OPTION_CHANGE)) { // 옵션변경
						exchangeClaimProduct.setPrdtOptnNo(ocClaimProduct.getChangePrdtOptnNo()); // 변경옵션번호
						exchangeClaimProduct.setOptnName(ocClaimProduct.getChangeOptnName()); // 변경옵션명
					} else {
						exchangeClaimProduct.setPrdtOptnNo(ocClaimProduct.getPrdtOptnNo()); // 회수상품 기준 옵션번호
						exchangeClaimProduct.setOptnName(ocClaimProduct.getOptnName()); // 회수상품 기준 옵션명
					}

					ocClaimProductDao.updateClmChangeOptn(exchangeClaimProduct); // 재배송 상품 수정

				}
			}
		}
	}

	/**
	 * @Desc : 클레임 반품 상품 저장(업데이트)
	 * @Method Name : updateClaimReturnProduct
	 * @Date : 2019. 7. 23.
	 * @Author : KTH
	 * @param ocClaimProducts
	 * @throws Exception
	 */
	public void updateClaimReturnProduct(OcClaim ocClaim, OcClaimProduct[] ocClaimProducts) throws Exception {
		/*
		 * 클레임 정보
		 */
		ocClaim = ocClaimDao.selectClaimBasisInfo(ocClaim); // 클레임 정보

		/*
		 * 클레임 상품 목록
		 */
		OcClaimProduct paramProduct = new OcClaimProduct();
		paramProduct.setClmNo(ocClaim.getClmNo());

		List<OcClaimProduct> claimProductList = ocClaimProductDao.selectClaimProductList(paramProduct);

		for (OcClaimProduct ocClaimProduct : ocClaimProducts) {
			/*
			 * 넘어온 폼 값 조정(고객 알림 내용은 상시 업데이트 가능)
			 */
			// 반품접수
			if (UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_RETURN_ACCEPT)) {
				ocClaimProduct.setClmProcTypeCode(null); // 처리(심의)유형 업데이트 제외
				ocClaimProduct.setClmProcContText(null); // 처리(심의)유형 업데이트 제외
				ocClaimProduct.setRecptYn(null); // 수령완료 업데이트 제외
			}
			// 수거지시
			else if (UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_RETURN_PICKUP_ORDER)) {
				// none
			}
			// 수령완료
			else if (UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_RETURN_RECEIVE_FINISH)) {
				ocClaimProduct.setRecptYn(null); // 수령완료 업데이트 제외
			}
			// 심의완료
			else if (UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_RETURN_JUDGE_FINISH)) {
				// ocClaimProduct.setClmProcTypeCode(null); // 처리(심의)유형 업데이트 제외
				// 2020.03.19 : 심의완료에도 수정 가능하게 변경
				// ocClaimProduct.setClmProcContText(null); // 처리(심의)유형 내용 업데이트 제외
				// 2020.03.19 : 심의완료에도 수정 가능하게 변경
				ocClaimProduct.setRecptYn(null); // 수령완료 업데이트 제외

				// 2020.03.19 : 심의완료에는 상품을 반품불가로 저장할 수 없다.
				if (UtilsText.equals(ocClaimProduct.getClmProcTypeCode(),
						CommonCode.CLM_PROC_TYPE_CODE_RETURN_IMPOSSIBLE)) {
					throw new Exception("validMsg:심의완료가 된 상태에서는 상품을 반품불가로 저장할 수 없습니다.");
				}
			}

			/*
			 * 클레임 상품 업데이트
			 */
			if (!ObjectUtils.isEmpty(ocClaimProduct.getClmPrdtSeq())) {
				ocClaimProduct.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자번호

				ocClaimProductDao.updateClaimProduct(ocClaimProduct); // 본상품 업데이트

				// 해당 클레임 상품에 사은품 클레임 상품이 있는 경우 동일 내용 업데이트
				if (!ObjectUtils.isEmpty(ocClaimProduct.getOrderGiftPrdtSeq())) {
					OcClaimProduct giftProduct = claimProductList.stream()
							.filter(x -> UtilsText.equals(String.valueOf(x.getOrderPrdtSeq()),
									String.valueOf(ocClaimProduct.getOrderGiftPrdtSeq())))
							.findFirst().orElse(null);

					if (giftProduct != null) {
						// 업데이트 정보의 본 상품 클레임 순번을 사은품 상품의 클레임 순번으로 대체
						ocClaimProduct.setClmPrdtSeq(giftProduct.getClmPrdtSeq());

						ocClaimProductDao.updateClaimProduct(ocClaimProduct); // 사은품 업데이트
					}

				}
			}
		}
	}

	/**
	 * @Desc : 클레임메모 목록 조회
	 * @Method Name : getClaimMemoList
	 * @Date : 2019. 3. 30.
	 * @Author : KTH
	 * @param ocClaimMemo
	 * @return
	 * @throws Exception
	 */
	public List<OcClaimMemo> getClaimMemoList(OcClaimMemo ocClaimMemo) throws Exception {
		ocClaimMemo.setDelYn(Const.BOOLEAN_FALSE); // 삭제 안된 것만
		return ocClaimMemoDao.selectClaimMemoList(ocClaimMemo);
	}

	/**
	 * @Desc : 클레임 메모 등록
	 * @Method Name : insertClaimMemo
	 * @Date : 2019. 3. 30.
	 * @Author : KTH
	 * @param ocClaimMemo
	 * @throws Exception
	 */
	public void insertClaimMemo(OcClaimMemo ocClaimMemo) throws Exception {
		ocClaimMemo.setRgsterNo(LoginManager.getUserDetails().getAdminNo());

		// 개행 처리
		if (ocClaimMemo.getMemoText() != null) {
			String replaceCont = ocClaimMemo.getMemoText().replace("\n", Const.STRING_HTML_BR_TAG);
			ocClaimMemo.setMemoText(replaceCont);
		}

		ocClaimMemoDao.insertClaimMemo(ocClaimMemo);
	}

	/**
	 * @Desc : 클레임 메모 삭제(삭제 플래그 업데이트)
	 * @Method Name : deleteClaimMemo
	 * @Date : 2019. 3. 30.
	 * @Author : KTH
	 * @param ocClaimMemo
	 * @throws Exception
	 */
	public void deleteClaimMemo(OcClaimMemo ocClaimMemo) throws Exception {
		ocClaimMemo.setDelYn(Const.BOOLEAN_TRUE);
		ocClaimMemo.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
		ocClaimMemoDao.udpateClaimMemoForDelete(ocClaimMemo);
	}

	/**
	 * @Desc : 클레임 미처리표시 상태 업데이트
	 * @Method Name : updateClaimUnProcYn
	 * @Date : 2019. 3. 31.
	 * @Author : KTH
	 * @param ocClaim
	 * @throws Exception
	 */
	public void updateClaimUnProcYn(OcClaim ocClaim) throws Exception {
		ocClaim.setModerNo(LoginManager.getUserDetails().getAdminNo());
		ocClaimDao.updateClaimUnProcYn(ocClaim);
	}

	/**
	 * @Desc : 클레임 점간이동여부 업데이트
	 * @Method Name : updateClaimBranchMoveCode
	 * @Date : 2019. 7. 23.
	 * @Author : KTH
	 * @param ocClaim
	 * @throws Exception
	 */
	public void updateClaimBranchMoveCode(OcClaim ocClaim) throws Exception {
		ocClaim.setModerNo(LoginManager.getUserDetails().getAdminNo());
		ocClaimDao.updateClaimBranchMoveCode(ocClaim);
	}

	/**
	 * @Desc : 취소 접수철회 업데이트
	 * @Method Name : updateClaimCancelWthdraw
	 * @Date : 2019. 4. 1.
	 * @Author : KTH
	 * @param ocClaim
	 * @throws Exception
	 */
	public void updateClaimCancelWthdraw(OcClaim ocClaim) throws Exception {
		String clmStatCode = CommonCode.CLM_STAT_CODE_CANCEL_WITHDRAWAL_ACCEPT; // 클레임 상태 코드(취소) - 취소접수철회
		String clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_CANCEL_WITHDRAWAL_ACCEPT; // 클레임상품 상태 코드(취소) - 취소접수철회

		/*
		 * 클레임 상품 목록
		 */
		OcClaimProduct claimProduct = new OcClaimProduct();
		claimProduct.setClmNo(ocClaim.getClmNo());

		List<OcClaimProduct> claimProductList = ocClaimProductDao.selectClaimProductList(claimProduct);

		/*
		 * 클레임상품 상태 코드 업데이트
		 */
		OcClaimProduct ocClaimProduct = new OcClaimProduct();
		ocClaimProduct.setModerNo(LoginManager.getUserDetails().getAdminNo());
		ocClaimProduct.setClmNo(ocClaim.getClmNo());

		ocClaimProduct.setClmPrdtStatCode(clmPrdtStatCode); // 클레임상품 상태 코드

		ocClaimProductDao.updateClaimProductStatCode(ocClaimProduct); // 클레임상품 상태 코드 업데이트

		/*
		 * 클레임 상태이력 등록
		 */
		OcClaimStatusHistory ocClaimStatusHistory = new OcClaimStatusHistory();
		ocClaimStatusHistory.setClmNo(ocClaim.getClmNo());
		ocClaimStatusHistory.setClmPrdtStatCode(clmPrdtStatCode); // 클레임상품상태코드
		ocClaimStatusHistory.setStockGbnCode(null);
		ocClaimStatusHistory.setNoteText(null);
		ocClaimStatusHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자

		ocClaimStatusHistoryDao.insertClaimStatusHistoryByClaimProduct(ocClaimStatusHistory); // 클레임상태이력 등록

		/*
		 * 클레임 상태 코드 업데이트
		 */
		ocClaim.setModerNo(LoginManager.getUserDetails().getAdminNo());
		ocClaim.setClmStatCode(clmStatCode); // 클레임 상태 코드

		ocClaimDao.updateClaimWthdraw(ocClaim); // 클레임 접수철회 상태 업데이트

		/*
		 * 2020.01.22 주문마스터의 취소금액을 CNCL_AMT 를 취소접수 전 으로 업데이트
		 */
		OcClaim paramClaim = new OcClaim();
		paramClaim.setClmNo(ocClaim.getClmNo());
		OcClaim claim = ocClaimDao.selectByPrimaryKey(paramClaim);
		claim.setOcClaimProductList(claimProductList);
		OcClaimAmountVO ocClaimAmountVO = new OcClaimAmountVO();
		// 클레임 금액계산 정보(전체/부분 취소 여부만 확인)
		claimProcService.setAllCancelCheck(claim, ocClaimAmountVO);

		if (ocClaimAmountVO.isAllCancelYn()) {
			// 주문상품 '전체취소접수' 라면
			OcOrder paramOrder = new OcOrder();
			paramOrder.setOrderNo(claim.getOrgOrderNo());
			paramOrder.setModerNo(LoginManager.getUserDetails().getAdminNo());
			paramOrder.setCnclAmt(-claim.getTotalRfndAmt()); // - 취소접수 클래임 마스터 환불금
			paramOrder.setOrderStatCode(CommonCode.ORDER_STAT_CODE_COMPLETE);

			ocOrderDao.updateOrderForClaim(paramOrder);
		}

		/*
		 * 주문상품 상태 업데이트, 주문 배송이력상태 업데이트, 주문상품이력 생성
		 */
		for (OcClaimProduct claimProdut : claimProductList) {

			/*
			 * 2020.03.30 : 주문상품 취소접수 전 상태 값 조회 '결제완료' 이외의 코드에서 취소접수 후 철회시 '결제완료'로만 원복되는것을
			 * 수정하기 위해 조회한다.
			 */
			OcOrderProductHistory paramHistory = new OcOrderProductHistory();
			paramHistory.setOrderNo(claimProdut.getOrderNo());
			paramHistory.setOrderPrdtSeq(claimProdut.getOrderPrdtSeq());
			paramHistory.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_ACCEPT);
			OcOrderProductHistory returnHistory = ocOrderProductHistoryDao
					.selectOrderPrdtStatBeforeCancelAccept(paramHistory);

			OcOrderProduct orderProduct = new OcOrderProduct();
			orderProduct.setOrderNo(claimProdut.getOrderNo());
			orderProduct.setOrderPrdtSeq(claimProdut.getOrderPrdtSeq());
			// 2020.03.30 : 주문상품 취소접수 전 상태 값 조회
			orderProduct.setOrderPrdtStatCode(returnHistory.getOrderPrdtStatCode());
			orderProduct.setModerNo(LoginManager.getUserDetails().getAdminNo());

			ocOrderProductDao.updateOrderProductForCalim(orderProduct); // 주문상품 상태 업데이트

			// 2020.03.30 : 주문 배송이력상태는 취소접수가 아니라 취소처리에서 업데이트 치기때문에
			// 기존에 접수철회 시 업데이트 치는 부분 주석처리
//			OcOrderDeliveryHistory orderDeliveryHistory = new OcOrderDeliveryHistory();
//			orderDeliveryHistory.setOrderNo(claimProdut.getOrderNo());
//			orderDeliveryHistory.setOrderPrdtSeq(claimProdut.getOrderPrdtSeq());
//			orderDeliveryHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
//			orderDeliveryHistory.setModerNo(LoginManager.getUserDetails().getAdminNo());
//			ocOrderDeliveryHistoryDao.updateOrderDeliveryHistoryStat(orderDeliveryHistory); // 주문 배송이력상태 업데이트

			OcOrderProductHistory orgOrderProductHistory = new OcOrderProductHistory();
			orgOrderProductHistory.setOrderNo(claimProdut.getOrderNo());
			orgOrderProductHistory.setOrderPrdtSeq(claimProdut.getOrderPrdtSeq());
			orgOrderProductHistory.setPrdtNo(claimProdut.getPrdtNo());
			orgOrderProductHistory.setPrdtOptnNo(claimProdut.getPrdtOptnNo());
			orgOrderProductHistory.setPrdtName(claimProdut.getPrdtName());
			orgOrderProductHistory.setEngPrdtName(claimProdut.getEngPrdtName());
			orgOrderProductHistory.setOptnName(claimProdut.getOptnName());
			// 2020.03.30 : 주문상품 취소접수 전 상태 값 조회
			orgOrderProductHistory.setOrderPrdtStatCode(returnHistory.getOrderPrdtStatCode());
			orgOrderProductHistory.setNoteText("취소접수절회로 인한 상태값 복원");
			orgOrderProductHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo());

			ocOrderProductHistoryDao.insertProductHistory(orgOrderProductHistory); // 주문상품이력 생성
		}
	}

	/**
	 * @Desc : 교환 접수철회 업데이트
	 * @Method Name : updateClaimExchangeWthdraw
	 * @Date : 2019. 4. 1.
	 * @Author : KTH
	 * @param ocClaim
	 * @throws Exception
	 */
	public Map<String, Object> updateClaimExchangeWthdraw(OcClaim ocClaim) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();

		OcClaim claimInfo = ocClaimDao.selectByPrimaryKey(ocClaim);

		// 2020.03.10 : 접수 상태 & 수거지시 가 아니면 철회 불가
		if (!UtilsText.equals(claimInfo.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_ACCEPT)
				&& !UtilsText.equals(claimInfo.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_PICKUP_ORDER)) {
			throw new Exception("validMsg:교환 클레임 철회가 불가한 상태입니다.");
		}

		String clmStatCode = CommonCode.CLM_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT; // 클레임 상태 코드(교환) - 교환접수철회
		String clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT; // 클레임상품 상태 코드(교환) - 교환접수철회

		/*
		 * 클레임상품 상태 코드 업데이트
		 */
		OcClaimProduct ocClaimProduct = new OcClaimProduct();
		ocClaimProduct.setModerNo(LoginManager.getUserDetails().getAdminNo());
		ocClaimProduct.setClmNo(ocClaim.getClmNo());

		ocClaimProduct.setClmPrdtStatCode(clmPrdtStatCode); // 클레임상품 상태 코드

		ocClaimProductDao.updateClaimProductStatCode(ocClaimProduct); // 클레임상품 상태 코드 업데이트

		/*
		 * 클레임 상태이력 등록
		 */
		OcClaimStatusHistory ocClaimStatusHistory = new OcClaimStatusHistory();
		ocClaimStatusHistory.setClmNo(ocClaim.getClmNo());
		ocClaimStatusHistory.setClmPrdtStatCode(clmPrdtStatCode); // 클레임상품상태코드
		ocClaimStatusHistory.setStockGbnCode(null);
		ocClaimStatusHistory.setNoteText(null);
		ocClaimStatusHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자

		ocClaimStatusHistoryDao.insertClaimStatusHistoryByClaimProduct(ocClaimStatusHistory); // 클레임상태이력 등록

		/*
		 * 클레임 상태 코드 업데이트
		 */
		ocClaim.setModerNo(LoginManager.getUserDetails().getAdminNo());
		ocClaim.setClmStatCode(clmStatCode); // 클레임 상태 코드

		ocClaimDao.updateClaimWthdraw(ocClaim); // 클레임 접수철회 상태 업데이트

		/*
		 * 클레임 배송비 취소
		 */
		OcClaim ocClaimInfo = ocClaimDao.selectClaimBasisInfo(ocClaim); // 클레임기본정보

		ocClaimInfo.setClaimRgsterNo(LoginManager.getUserDetails().getAdminNo());
		ocClaimInfo.setCustIp(UtilsRequest.getRequest().getRemoteAddr());
		retMap = claimProcService.setCancelClaimDeliveryAmt(ocClaimInfo);

		return retMap;
	}

	/**
	 * @Desc : 반품 접수철회 업데이트
	 * @Method Name : updateClaimReturnWthdraw
	 * @Date : 2019. 4. 1.
	 * @Author : KTH
	 * @param ocClaim
	 * @throws Exception
	 */
	public Map<String, Object> updateClaimReturnWthdraw(OcClaim ocClaim) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();

		OcClaim claimInfo = ocClaimDao.selectByPrimaryKey(ocClaim);

		// 2020.03.10 : 접수 상태 & 수거지시 가 아니면 철회 불가
		if (!UtilsText.equals(claimInfo.getClmStatCode(), CommonCode.CLM_STAT_CODE_RETURN_ACCEPT)
				&& !UtilsText.equals(claimInfo.getClmStatCode(), CommonCode.CLM_STAT_CODE_RETURN_PICKUP_ORDER)) {
			throw new Exception("validMsg:반품 클레임 철회가 불가한 상태입니다.");
		}

		String clmStatCode = CommonCode.CLM_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT; // 클레임 상태 코드(반품) - 반품접수철회
		String clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT; // 클레임상품 상태 코드(반품) - 반품접수철회

		/*
		 * 클레임상품 상태 코드 업데이트
		 */
		OcClaimProduct ocClaimProduct = new OcClaimProduct();
		ocClaimProduct.setModerNo(LoginManager.getUserDetails().getAdminNo());
		ocClaimProduct.setClmNo(ocClaim.getClmNo());

		ocClaimProduct.setClmPrdtStatCode(clmPrdtStatCode); // 클레임상품 상태 코드

		ocClaimProductDao.updateClaimProductStatCode(ocClaimProduct); // 클레임상품 상태 코드 업데이트

		/*
		 * 클레임 상태이력 등록
		 */
		OcClaimStatusHistory ocClaimStatusHistory = new OcClaimStatusHistory();
		ocClaimStatusHistory.setClmNo(ocClaim.getClmNo());
		ocClaimStatusHistory.setClmPrdtStatCode(clmPrdtStatCode); // 클레임상품상태코드
		ocClaimStatusHistory.setStockGbnCode(null);
		ocClaimStatusHistory.setNoteText(null);
		ocClaimStatusHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자

		ocClaimStatusHistoryDao.insertClaimStatusHistoryByClaimProduct(ocClaimStatusHistory); // 클레임상태이력 등록

		/*
		 * 클레임 상태 코드 업데이트
		 */
		ocClaim.setModerNo(LoginManager.getUserDetails().getAdminNo());
		ocClaim.setClmStatCode(clmStatCode); // 클레임 상태 코드

		ocClaimDao.updateClaimWthdraw(ocClaim); // 클레임 접수철회 상태 업데이트

		/*
		 * 클레임 배송비 취소
		 */
		OcClaim ocClaimInfo = ocClaimDao.selectClaimBasisInfo(ocClaim); // 클레임기본정보

		ocClaimInfo.setClaimRgsterNo(LoginManager.getUserDetails().getAdminNo());
		ocClaimInfo.setCustIp(UtilsRequest.getRequest().getRemoteAddr());
		retMap = claimProcService.setCancelClaimDeliveryAmt(ocClaimInfo);

		return retMap;
	}

	/**
	 * @Desc : 회수지시 처리
	 * @Method Name : setRequestClaimRetrieval
	 * @Date : 2019. 4. 5.
	 * @Author : KTH
	 * @param ocClaim
	 * @throws Exception
	 */
	public void setRequestClaimRetrieval(OcClaim ocClaim) throws Exception {

		String clmStatCode = "";
		String clmPrdtStatCode = "";

		// 클레임 정보
		OcClaim claimInfo = ocClaimDao.selectClaimBasisInfo(ocClaim);

		// 클레임 상태코드 체크
		if (!UtilsText.equals(claimInfo.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_ACCEPT)
				&& !UtilsText.equals(claimInfo.getClmStatCode(), CommonCode.CLM_STAT_CODE_RETURN_ACCEPT)) {
			throw new Exception("validMsg:수거 지시가 불가한 상태입니다.");
		}

		if (UtilsText.equals(claimInfo.getClmGbnCode(), CommonCode.CLM_GBN_CODE_EXCHANGE)) {
			clmStatCode = CommonCode.CLM_STAT_CODE_EXCHANGE_PICKUP_ORDER;
			clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_PICKUP_ORDER;
		} else if (UtilsText.equals(claimInfo.getClmGbnCode(), CommonCode.CLM_GBN_CODE_RETURN)) {
			clmStatCode = CommonCode.CLM_STAT_CODE_RETURN_PICKUP_ORDER;
			clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_RETURN_PICKUP_ORDER;
		}

		// 클레임상품 정보
		OcClaimProduct paramClaimProduct = new OcClaimProduct();
		paramClaimProduct.setClmNo(ocClaim.getClmNo());
		paramClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 상품 코드
		paramClaimProduct.setPrdtRltnFileSeq(1); // 상품관련파일순번 : 1 (대표)
		List<OcClaimProduct> claimProductList = ocClaimProductDao.selectClaimProductList(paramClaimProduct);

		/**
		 * 회수지시 종전 인터페이스에 일반택배용과 픽업상품용 두 가지를 사용 일반택배용은 초도 주문배송 delivery id 기준으로만 회수지시
		 * 픽업상품용은 회수지 주소를 받아 주소정보를 넘겨 회수지시 일반택배 주문의 회수지 주소 변경의 상황을 고려하여 픽업상품 회수지시로만 통일하여
		 * 사용하기로 함
		 */
		for (OcClaimProduct claimProduct : claimProductList) {

			// interfacesClaimService.updateOrderReturnProductNoTrx(cbcd, maejangCd, dlvyId,
			// asReturnConfirm); // 일반택배용 회수지시

			String cbcd = ""; // B코드(재고구분)
			String maejangCd = ""; // ERP매장코드
			String dlvyId = ""; // 배송ID
			String postNum = ""; // 회수지 우편번호
			String addr = ""; // 회수지 주소
			String addrDtl = ""; // 회수지 상세주소
			String hdphnNum = ""; // 회수 발송자 전화번호
			String rcvrName = ""; // 회수 발송자명
			String prdtCode = ""; // 업체상품코드
			String optionValue = ""; // 옵션번호

			switch (claimProduct.getStockGbnCode()) {
			case CommonCode.STOCK_GBN_CODE_AI:
				cbcd = CommonCode.STOCK_GBN_CODE_IMINFO_AI;
				break;
			case CommonCode.STOCK_GBN_CODE_AW:
				cbcd = CommonCode.STOCK_GBN_CODE_IMINFO_AW;
				break;
			case CommonCode.STOCK_GBN_CODE_AS:
				cbcd = CommonCode.STOCK_GBN_CODE_IMINFO_AS;
				break;
			default:
				break;
			}

			maejangCd = claimProduct.getInsdMgmtInfoText();
			dlvyId = claimProduct.getDlvyIdText();
			postNum = claimInfo.getBuyerPostCodeText();
			addr = claimInfo.getBuyerPostAddrText();
			addrDtl = claimInfo.getBuyerDtlAddrText();
			hdphnNum = claimInfo.getBuyerHdphnNoText();
			rcvrName = claimInfo.getBuyerName();
			prdtCode = claimProduct.getVndrPrdtNoText();
			optionValue = claimProduct.getPrdtOptnNo();

			// 픽업상품 회수 지시(일반택배 주문의 회수지 변경 상황을 고려하여 일반/픽업 구분 없이 픽업용으로 사용)
			// interfacesClaimService.updateOrderReturnProductPickUpNoTrx(cbcd, maejangCd,
			// dlvyId, postNum, addr, addrDtl,
			// hdphnNum, rcvrName, prdtCode, optionValue);

			Map<String, String> orderParamMap = new HashMap<>();
			orderParamMap.put("dlvyIdText", dlvyId);

			// 2020.02.18 : 회수지시 프로시져 호출 생성 프로시져 / 성공 : "0"
			log.error("클레임 회수지시 프로시저 시작");
			log.error("dlvyIdText :: => {}",  dlvyId);
			String resultOrderRetrun = this.setCallProcedureForOrderReturn(orderParamMap);

			// TODO: 2020.03.10 : 로컬 테스트시에만 주석을 푼다. 운영반영시 반드시!! 주석처리해야한다!!!!
			// resultOrderRetrun = "0";
			log.error("OrderNo :: => {}",  claimProduct.getOrderNo());
			log.error("ClmNo :: => {}",  claimProduct.getClmNo());
			log.error("resultOrderRetrun :: => {}",  resultOrderRetrun);
			if (!UtilsText.equals(resultOrderRetrun, "0")) {
				log.error("resultOrderRetrun : {}", resultOrderRetrun);
				throw new Exception("validMsg:회수지시 프로시져 호출에 실패하였습니다.");
			}
			log.error("클레임 회수지시 프로시저 종료");
		}

		/*
		 * 클레임/클레임상품 상태 변경 처리 및 상태변경이력 등록
		 */
		this.setClaimAndClaimPrdtStatChangeProc(claimInfo, claimProductList, clmStatCode, clmPrdtStatCode);

		// 이메일 , 카톡 문자 알림 서비스 시작
		try {
			OcClaim param = ocClaimDao.selectByPrimaryKey(ocClaim);

			if (param != null) {
				if (UtilsText.equals(CommonCode.CLM_GBN_CODE_EXCHANGE, param.getClmGbnCode())) {
					// 교환 수거지시
					claimMessageService.exchangeClaimAcceptComplete(param);

				} else if (UtilsText.equals(CommonCode.CLM_GBN_CODE_RETURN, param.getClmGbnCode())) {
					// 반품 수거 지시
					claimMessageService.returnClaimAcceptComplete(param);
				}
			}
		} catch (Exception e) {
			log.error("교환/반품 수거지시 메세지,메일 전송 에러 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 회수지시 프로시져 호출 / 성공 : "0"
	 * @Method Name : setCallProcedureForOrderReturn
	 * @Date : 2020. 02. 17.
	 * @Author : 이강수
	 * @param Map<String, String> map
	 * @return
	 */
	public String setCallProcedureForOrderReturn(Map<String, String> map) throws Exception {
		ocClaimProductDao.callProcedureForOrderReturn(map);
		return map.get("errorCode");
	}

	/**
	 * @Desc : 수령완료 처리
	 * @Method Name : setFinishiClaimReceipt
	 * @Date : 2019. 4. 5.
	 * @Author : KTH
	 * @param ocClaim
	 * @throws Exception
	 */
	public void setFinishiClaimReceipt(OcClaim ocClaim) throws Exception {

		String clmStatCode = "";
		String clmPrdtStatCode = "";

		// 클레임 정보
		OcClaim claimInfo = ocClaimDao.selectClaimBasisInfo(ocClaim);

		// 클레임 상태코드 체크
		if (!UtilsText.equals(claimInfo.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_PICKUP_ORDER)
				&& !UtilsText.equals(claimInfo.getClmStatCode(), CommonCode.CLM_STAT_CODE_RETURN_PICKUP_ORDER)) {
			throw new Exception("validMsg:수령 완료가 불가한 상태입니다.");
		}

		if (UtilsText.equals(claimInfo.getClmGbnCode(), CommonCode.CLM_GBN_CODE_EXCHANGE)) {
			clmStatCode = CommonCode.CLM_STAT_CODE_EXCHANGE_RECEIVE_FINISH;
			clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_RECEIVE_FINISH;
		} else if (UtilsText.equals(claimInfo.getClmGbnCode(), CommonCode.CLM_GBN_CODE_RETURN)) {
			clmStatCode = CommonCode.CLM_STAT_CODE_RETURN_RECEIVE_FINISH;
			clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_RETURN_RECEIVE_FINISH;
		}

		// 클레임상품 정보
		OcClaimProduct paramClaimProduct = new OcClaimProduct();
		paramClaimProduct.setClmNo(ocClaim.getClmNo());
		paramClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 상품 코드
		paramClaimProduct.setPrdtRltnFileSeq(1); // 상품관련파일순번 : 1 (대표)
		List<OcClaimProduct> claimProductList = ocClaimProductDao.selectClaimProductList(paramClaimProduct);

		/*
		 * 클레임/클레임상품 상태 변경 처리 및 상태변경이력 등록
		 */
		this.setClaimAndClaimPrdtStatChangeProc(claimInfo, claimProductList, clmStatCode, clmPrdtStatCode);
	}

	/**
	 * @Desc : 교환 심의완료 처리
	 * @Method Name : setFinishClaimExchangeJudge
	 * @Date : 2019. 4. 5.
	 * @Author : KTH
	 * @param ocClaim
	 * @throws Exception
	 */
	public void setFinishClaimExchangeJudge(OcClaim ocClaim) throws Exception {
		String clmStatCode = "";
		String clmPrdtStatCode = "";
		int impossiblePrdtCnt = 0; // 처리유형이 불가인 클레임 상품 개수

		// 클레임 정보
		OcClaim claimInfo = ocClaimDao.selectClaimBasisInfo(ocClaim);

		// 클레임 상태코드 체크
		if (!UtilsText.equals(claimInfo.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_RECEIVE_FINISH)) {
			throw new Exception("validMsg:심의 완료가 불가한 상태입니다.");
		}

		// 클레임상품 정보
		OcClaimProduct paramClaimProduct = new OcClaimProduct();
		paramClaimProduct.setClmNo(ocClaim.getClmNo());
		paramClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 상품 코드
		paramClaimProduct.setPrdtRltnFileSeq(1); // 상품관련파일순번 : 1 (대표)
		List<OcClaimProduct> claimProductList = ocClaimProductDao.selectClaimProductList(paramClaimProduct);

		/*
		 * [클레임상품 상태 변경] [클레임상태이력 등록]
		 */
		for (OcClaimProduct claimProduct : claimProductList) {
			// 처리유형이 불가인 경우 상태 값 또한 불가로 처리
			if (UtilsText.equals(claimProduct.getClmProcTypeCode(),
					CommonCode.CLM_PROC_TYPE_CODE_EXCHANGE_IMPOSSIBLE)) {

				clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE; // 클레임상품 상태 코드:교환불가
				impossiblePrdtCnt++;
			} else {
				clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_JUDGE_FINISH; // 클레임상품 상태 코드:심의완료
			}

			claimProduct.setModerNo(LoginManager.getUserDetails().getAdminNo());
			claimProduct.setClmPrdtStatCode(clmPrdtStatCode); // 클레임상품 상태 코드

			ocClaimProductDao.updateClaimProductStatCode(claimProduct); // 클레임상품 상태 변경

			OcClaimStatusHistory ocClaimStatusHistory = new OcClaimStatusHistory();
			ocClaimStatusHistory.setClmNo(claimProduct.getClmNo());
			ocClaimStatusHistory.setClmPrdtSeq(claimProduct.getClmPrdtSeq());
			ocClaimStatusHistory.setClmPrdtStatCode(clmPrdtStatCode); // 클레임상품 상태 코드
			ocClaimStatusHistory.setStockGbnCode(null);
			ocClaimStatusHistory.setNoteText(null);
			ocClaimStatusHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자

			ocClaimStatusHistoryDao.insertClaimStatusHistory(ocClaimStatusHistory); // 클레임상태이력 등록
		}

		/*
		 * [클레임 상태 변경]
		 */
		if (claimProductList.size() == impossiblePrdtCnt) {
			clmStatCode = CommonCode.CLM_STAT_CODE_EXCHANGE_IMPOSSIBLE; // 클레임 상태 코드:교환불가
		} else {
			clmStatCode = CommonCode.CLM_STAT_CODE_EXCHANGE_JUDGE_FINISH; // 클레임 상태 코드:심의완료
		}

		claimInfo.setModerNo(LoginManager.getUserDetails().getAdminNo());
		claimInfo.setClmStatCode(clmStatCode); // 클레임 상태 코드
		ocClaimDao.updateClaimStatCode(claimInfo);
	}

	/**
	 * @Desc : 반품 심의완료 처리
	 * @Method Name : setFinishClaimReturnJudge
	 * @Date : 2019. 4. 5.
	 * @Author : KTH
	 * @param ocClaim
	 * @throws Exception
	 */
	public void setFinishClaimReturnJudge(OcClaim ocClaim) throws Exception {
		String clmStatCode = "";
		String clmPrdtStatCode = "";
		int impossiblePrdtCnt = 0; // 처리유형이 불가인 클레임 상품 개수

		// 클레임 정보
		ocClaim = ocClaimDao.selectClaimBasisInfo(ocClaim);

		// 클레임 상태코드 체크
		if (!UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_RETURN_RECEIVE_FINISH)) {
			throw new Exception("validMsg:심의 완료가 불가한 상태입니다.");
		}

		ocClaim.setClaimRgsterNo(LoginManager.getUserDetails().getAdminNo());

		// 클레임상품 정보
		OcClaimProduct paramClaimProduct = new OcClaimProduct();
		paramClaimProduct.setClmNo(ocClaim.getClmNo());
		paramClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 상품 코드
		paramClaimProduct.setPrdtRltnFileSeq(1); // 상품관련파일순번 : 1 (대표)
		List<OcClaimProduct> claimProductList = ocClaimProductDao.selectClaimProductList(paramClaimProduct);

		/*
		 * [원 주문정보 get]
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보

		/*
		 * [클레임상품 상태 변경] [클레임상태이력 등록]
		 */
		for (OcClaimProduct claimProduct : claimProductList) {
			// 처리유형이 불가인 경우 상태 값 또한 불가로 처리
			if (UtilsText.equals(claimProduct.getClmProcTypeCode(), CommonCode.CLM_PROC_TYPE_CODE_RETURN_IMPOSSIBLE)) {

				clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE; // 클레임상품 상태 코드:반품불가
				impossiblePrdtCnt++;
			} else {
				clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_RETURN_JUDGE_FINISH; // 클레임상품 상태 코드:심의완료
			}

			claimProduct.setModerNo(LoginManager.getUserDetails().getAdminNo());
			claimProduct.setClmPrdtStatCode(clmPrdtStatCode); // 클레임상품 상태 코드

			ocClaimProductDao.updateClaimProductStatCode(claimProduct); // 클레임상품 상태 변경

			OcClaimStatusHistory ocClaimStatusHistory = new OcClaimStatusHistory();
			ocClaimStatusHistory.setClmNo(claimProduct.getClmNo());
			ocClaimStatusHistory.setClmPrdtSeq(claimProduct.getClmPrdtSeq());
			ocClaimStatusHistory.setClmPrdtStatCode(clmPrdtStatCode); // 클레임상품 상태 코드
			ocClaimStatusHistory.setStockGbnCode(null);
			ocClaimStatusHistory.setNoteText(null);
			ocClaimStatusHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자

			ocClaimStatusHistoryDao.insertClaimStatusHistory(ocClaimStatusHistory); // 클레임상태이력 등록
		}

		/*
		 * 반품불가 상품이 있는 경우 클레임 금액 재계산
		 */
		if (impossiblePrdtCnt > 0) {
			/*
			 * [주문자 회원 정보]
			 */
			MbMember mbMember = memberDao.selectMemberDefalutInfo(ocOrder.getMemberNo());

			/*
			 * 기존 주문금 환불 기준 데이터 삭제
			 */
			OcClaimPayment deleteClaimOrderAmtPayment = new OcClaimPayment();
			deleteClaimOrderAmtPayment.setClmNo(ocClaim.getClmNo());
			deleteClaimOrderAmtPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REFUND); // 환불
			deleteClaimOrderAmtPayment.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 재경처리 N
			deleteClaimOrderAmtPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_ORDER_AMT); // 주문금
			deleteClaimOrderAmtPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_HISTORY); // 히스토리

			ocClaimPaymentDao.deleteClaimPayment(deleteClaimOrderAmtPayment); // 클레임결제 삭제

			/*
			 * 기존 다족구매 프로모션 관련 히스토리 데이터 삭제(환수/환불 구분 없이 모두 삭제)
			 */
			OcClaimPayment deleteClaimPromoPayment = new OcClaimPayment();
			deleteClaimPromoPayment.setClmNo(ocClaim.getClmNo());
			deleteClaimPromoPayment.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 재경처리 N
			deleteClaimPromoPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_PROMOTION); // 프로모션
			deleteClaimPromoPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_HISTORY); // 히스토리

			ocClaimPaymentDao.deleteClaimPayment(deleteClaimPromoPayment); // 클레임결제 삭제

			// 클레임 금액 계산
			OcClaimAmountVO ocClaimAmountVO = new OcClaimAmountVO();

			/*************************************
			 * 클레임 금액 계산
			 *************************************/
			claimProcService.setClaimAmountCalcForPartCancel(ocClaim, ocOrder, ocClaimAmountVO,
					CommonCode.CLM_GBN_CODE_RETURN, false);

			/*
			 * 증감 구매적립 포인트 계산
			 */
			int thisClaimVariationSavePoint = ocClaimAmountVO.getVariationSavePoint(); // 현재 클레임 증감 적립포인트

			// 더블적립 쿠폰이 사용된 경우
			if (ocClaimAmountVO.getOrderDoubleDscntCpnInfo() != null) {
				// 주문의 구매적립률을 이용하여 계산하므로 주석처리
				// thisClaimVariationSavePoint = thisClaimVariationSavePoint * 2;
			}

			/*
			 * 구매적립 포인트 환수 체크(임직원이 아닌 멤버쉽 회원인 경우만 체크)
			 */
			int clawbackPoint = 0; // 환수포인트

			if (UtilsText.equals(ocOrder.getMemberTypeCode(), CommonCode.MEMBER_TYPE_CODE_MEMBERSHIP)
					&& UtilsText.equals(ocOrder.getEmpYn(), Const.BOOLEAN_FALSE)) {

				// 환수할 포인트가 발생한 경우
				if (thisClaimVariationSavePoint < 0) {
					// 환수포인트 조회 인터페이스
					try {
						clawbackPoint = membershipPointService.getClawbackPoint(mbMember.getSafeKey(),
								mbMember.getSafeKeySeq(), ocOrder.getOrgOrderNo(),
								Math.abs(thisClaimVariationSavePoint));
					} catch (Exception e) {
						throw new Exception("validMsg:포인트 조회에 실패했습니다.");
					}
				}
			}

			/*
			 * 클레임 마스터 금액 업데이트(변동 적립포인트, 환수포인트 포함)
			 */
			OcClaim updateAmtClaim = new OcClaim();
			updateAmtClaim.setClmNo(ocClaim.getClmNo());
			updateAmtClaim.setTotalRfndAmt(ocClaimAmountVO.getRefundCnclAmt()); // 총환불금액
			updateAmtClaim
					.setTotalRedempAmt(ocClaimAmountVO.getRedempAmtByMultiBuy() + ocClaimAmountVO.getClaimDlvyAmt()); // 총환수금액
			updateAmtClaim.setIrdsSavePoint((int) ocClaimAmountVO.getVariationSavePoint()); // 증감적립포인트
			updateAmtClaim.setRedempSavePoint(clawbackPoint); // 환수적립포인트
			updateAmtClaim.setModerNo(LoginManager.getUserDetails().getAdminNo());

			ocClaimDao.updateClaimStat(updateAmtClaim);

			/*
			 * 환수프로모션비(다족구매로 인해 발생) 발생된 경우 결제히스토리 등록
			 */
			if (ocClaimAmountVO.getRedempAmtByMultiBuy() > 0) {
				for (OcClaimPayment redempMultiBuyPayment : ocClaimAmountVO.getRedempMultiBuyPaymentList()) {
					ocClaimPaymentDao.insertClaimPayment(redempMultiBuyPayment);
				}
			}

			/*
			 * 기존 다족구매 리오더 매출로 인해 변경된 금액과 원 주문 기준 취소상품 금액 차이 결제히스토리 등록(현재 취소금을 맞추기 위한 이력)
			 */
			if (ocClaimAmountVO.getAddMultiBuyDifferAmt() > 0) {
				for (OcClaimPayment addMultiBuyDifferPaymentList : ocClaimAmountVO.getAddMultiBuyDifferPaymentList()) {
					ocClaimPaymentDao.insertClaimPayment(addMultiBuyDifferPaymentList);
				}
			}

			/*
			 * 클레임 결제 등록
			 */
			// 주 결제 수단
			if (ocClaimAmountVO.getMainPayment() != null) {
				OcClaimPayment claimPayment = new OcClaimPayment();
				BeanUtils.copyProperties(ocClaimAmountVO.getMainPayment(), claimPayment); // 내용 복사

				if (ocClaimAmountVO.getMainCnclAmt() > 0) { // 실제 취소금이 있는 경우
					claimPayment.setPymntTodoAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소
																									// 금액으로 변경
					claimPayment.setPymntAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

					// 클레임 결제 이력데이터 등록
					claimProcService.setClaimPaymentHistory(ocClaim, claimPayment,
							CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
				}
			}

			// 기프트
			if (ocClaimAmountVO.getGiftPayment() != null) {
				OcClaimPayment claimPayment = new OcClaimPayment();
				BeanUtils.copyProperties(ocClaimAmountVO.getGiftPayment(), claimPayment); // 내용 복사

				if (ocClaimAmountVO.getGiftCnclAmt() > 0) { // 실제 취소금이 있는 경우
					claimPayment.setPymntTodoAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소
																									// 금액으로 변경
					claimPayment.setPymntAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

					// 클레임 결제 이력데이터 등록
					claimProcService.setClaimPaymentHistory(ocClaim, claimPayment,
							CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
				}
			}

			// 이벤트 포인트
			if (ocClaimAmountVO.getEventPointPayment() != null) {
				OcClaimPayment claimPayment = new OcClaimPayment();
				BeanUtils.copyProperties(ocClaimAmountVO.getEventPointPayment(), claimPayment); // 내용 복사

				if (ocClaimAmountVO.getEventPointCnclAmt() > 0) { // 실제 취소금이 있는 경우
					claimPayment.setPymntTodoAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제예정금액 - 클레임
																										// 취소
																										// 금액으로 변경
					claimPayment.setPymntAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소
																									// 금액으로 변경

					// 클레임 결제 이력데이터 등록
					claimProcService.setClaimPaymentHistory(ocClaim, claimPayment,
							CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
				}
			}

			// 포인트
			if (ocClaimAmountVO.getPointPayment() != null) {
				OcClaimPayment claimPayment = new OcClaimPayment();
				BeanUtils.copyProperties(ocClaimAmountVO.getPointPayment(), claimPayment); // 내용 복사

				if (ocClaimAmountVO.getPointCnclAmt() > 0) { // 실제 취소금이 있는 경우
					claimPayment.setPymntTodoAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소
																									// 금액으로
																									// 변경
					claimPayment.setPymntAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

					// 클레임 결제 이력데이터 등록
					claimProcService.setClaimPaymentHistory(ocClaim, claimPayment,
							CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
				}
			}
		}

		/*
		 * [클레임 상태 변경]
		 */
		if (claimProductList.size() == impossiblePrdtCnt) {
			clmStatCode = CommonCode.CLM_STAT_CODE_RETURN_IMPOSSIBLE; // 클레임 상태 코드:반품불가
		} else {
			clmStatCode = CommonCode.CLM_STAT_CODE_RETURN_JUDGE_FINISH; // 클레임 상태 코드:심의완료
		}

		ocClaim.setModerNo(LoginManager.getUserDetails().getAdminNo());
		ocClaim.setClmStatCode(clmStatCode); // 클레임 상태 코드
		ocClaimDao.updateClaimStatCode(ocClaim);
	}

	/**
	 * @Desc : 교환 배송지시 처리
	 * @Method Name : setCmdClaimExchangeDeliveryProc
	 * @Date : 2019. 4. 5.
	 * @Author : KTH
	 * @param ocClaim
	 * @throws Exception
	 */
	public void setCmdClaimExchangeDeliveryProc(OcClaim ocClaim) throws Exception {
		String clmStatCode = CommonCode.CLM_STAT_CODE_EXCHANGE_DELIVERY_ORDER;
		String clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_DELIVERY_ORDER;

		// 클레임 정보
		OcClaim claimInfo = ocClaimDao.selectClaimBasisInfo(ocClaim);

		// 클레임 상태코드 체크
		if (!UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_JUDGE_FINISH)) {
			throw new Exception("validMsg:배송 지시가 불가한 상태입니다.");
		}

		// 클레임상품 정보(클레임불가 상태인 상품은 제외)
		OcClaimProduct paramClaimProduct = new OcClaimProduct();
		paramClaimProduct.setClmNo(ocClaim.getClmNo());
		paramClaimProduct.setIncludeDlvyPrdtYn(Const.BOOLEAN_TRUE); // 교환대상상품 모두 가져오기
		paramClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 상품 코드
		paramClaimProduct.setPrdtRltnFileSeq(1); // 상품관련파일순번 : 1 (대표)
		List<OcClaimProduct> tempSourceClaimProducts = ocClaimProductDao.selectClaimProductList(paramClaimProduct);

		List<OcClaimProduct> claimProductList = tempSourceClaimProducts.stream()
				.filter(x -> x.getUpClmPrdtSeq() == null).filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE))
				.collect(Collectors.toList());

		// 주문 정보
		OcOrder sourceOrder = new OcOrder();
		sourceOrder.setOrderNo(claimInfo.getOrgOrderNo());
		sourceOrder = ocOrderDao.selectOrderDetail(sourceOrder);

		/*
		 * [신규 주문 생성] - 회수상품 기준으로 교환매출 취소 리오더 생성(주문마스터)
		 */
		OcOrder reOrderSalesCancel = new OcOrder();
		BeanUtils.copyProperties(sourceOrder, reOrderSalesCancel); // 내용 복사

		String reOrderNoSalesCancel = orderService.createOrderSeq(); // 신규 주문번호

		// 리오더 주문에 들어갈 주문 합계금 컬럼 값 관련
		int _totalNormalAmtSalesCancel = claimProductList.stream().mapToInt(o -> o.getPrdtNormalAmt()).sum();
		int _totalSellAmtSalesCancel = claimProductList.stream().mapToInt(o -> o.getPrdtSellAmt()).sum();
		int _pymntTodoAmtSalesCancel = claimProductList.stream().mapToInt(o -> o.getOrderAmt()).sum();
		int _pymntAmtSalesCancel = claimProductList.stream().mapToInt(o -> o.getOrderAmt()).sum();

		reOrderSalesCancel.setOrderNo(reOrderNoSalesCancel); // 신규주문번호
		reOrderSalesCancel.setClmNo(ocClaim.getClmNo()); // 클레임번호 set
		reOrderSalesCancel.setTotalNormalAmt(_totalNormalAmtSalesCancel); // 정상가총액
		reOrderSalesCancel.setTotalSellAmt(_totalSellAmtSalesCancel); // 판매가총액
		reOrderSalesCancel.setTotalPromoDscntAmt(0); // 프로모션할인총액
		reOrderSalesCancel.setTotalCpnDscntAmt(0); // 쿠폰할인총액
		reOrderSalesCancel.setTotalEmpDscntAmt(0); // 임직원할인총액
		reOrderSalesCancel.setPointUseAmt(0); // 포인트사용액
		reOrderSalesCancel.setEventPointUseAmt(0); // 이벤트포인트사용액
		reOrderSalesCancel.setMmnyDlvyAmt(0); // 자사배송비
		reOrderSalesCancel.setTotalVndrDlvyAmt(0); // 입점사배송비총액
		reOrderSalesCancel.setPymntTodoAmt(_pymntTodoAmtSalesCancel); // 결제예정금액
		reOrderSalesCancel.setPymntAmt(_pymntAmtSalesCancel); // 결제금액
		reOrderSalesCancel.setCnclAmt(0); // 취소금액
		reOrderSalesCancel.setSalesCnclGbnType(CommonCode.SALES_CNCL_GBN_TYPE_SALE_EXCHANGE_CANCEL); // 매출취소구분 - 교환취소
		reOrderSalesCancel.setOrderStatCode(CommonCode.ORDER_STAT_CODE_CANCEL_COMPLETE); // 주문상태 - 전체취소완료
		reOrderSalesCancel.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
		reOrderSalesCancel.setModerNo(LoginManager.getUserDetails().getAdminNo());

		ocOrderDao.insertOrder(reOrderSalesCancel); // 신규 주문 생성

		/*
		 * [신규 주문 생성] - 재배송상품 기준으로 교환매출 리오더 생성(주문마스터)
		 */
		OcOrder reOrderSales = new OcOrder();
		BeanUtils.copyProperties(sourceOrder, reOrderSales); // 내용 복사

		String reOrderNoSales = orderService.createOrderSeq(); // 신규 주문번호

		// 리오더 주문에 들어갈 주문 합계금 컬럼 값 관련
		int _totalNormalAmt = claimProductList.stream().mapToInt(o -> o.getPrdtNormalAmt()).sum();
		int _totalSellAmt = claimProductList.stream().mapToInt(o -> o.getPrdtSellAmt()).sum();
		int _pymntTodoAmt = claimProductList.stream().mapToInt(o -> o.getOrderAmt()).sum();
		int _pymntAmt = claimProductList.stream().mapToInt(o -> o.getOrderAmt()).sum();

		reOrderSales.setOrderNo(reOrderNoSales); // 신규주문번호
		reOrderSales.setClmNo(ocClaim.getClmNo()); // 클레임번호 set
		reOrderSales.setTotalNormalAmt(_totalNormalAmt); // 정상가총액
		reOrderSales.setTotalSellAmt(_totalSellAmt); // 판매가총액
		reOrderSales.setTotalPromoDscntAmt(0); // 프로모션할인총액
		reOrderSales.setTotalCpnDscntAmt(0); // 쿠폰할인총액
		reOrderSales.setTotalEmpDscntAmt(0); // 임직원할인총액
		reOrderSales.setPointUseAmt(0); // 포인트사용액
		reOrderSales.setEventPointUseAmt(0); // 이벤트포인트사용액
		reOrderSales.setMmnyDlvyAmt(0); // 자사배송비
		reOrderSales.setTotalVndrDlvyAmt(0); // 입점사배송비총액
		reOrderSales.setPymntTodoAmt(_pymntTodoAmt); // 결제예정금액
		reOrderSales.setPymntAmt(_pymntAmt); // 결제금액
		reOrderSales.setCnclAmt(0); // 취소금액
		reOrderSales.setSalesCnclGbnType(CommonCode.SALES_CNCL_GBN_TYPE_EXCHANGE_SALE); // 매출취소구분 - 교환매출
		reOrderSales.setOrderStatCode(CommonCode.ORDER_STAT_CODE_COMPLETE); // 주문상태 - 결제완료
		reOrderSales.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
		reOrderSales.setModerNo(LoginManager.getUserDetails().getAdminNo());

		// 클레임 접수된 배송지 주소로 변경
		if (UtilsText.isNotBlank(ocClaim.getRcvrName())) {
			reOrderSales.setRcvrName(ocClaim.getRcvrName()); // 클레임 수취인명
			reOrderSales.setRcvrTelNoText(ocClaim.getRcvrHdphnNoText()); // 클레임 수취인 전화번호
			reOrderSales.setRcvrHdphnNoText(ocClaim.getRcvrHdphnNoText()); // 클레임 수취인 핸드폰번호
			reOrderSales.setRcvrPostCodeText(ocClaim.getRcvrPostCodeText()); // 클레임 우편번호
			reOrderSales.setRcvrPostAddrText(ocClaim.getRcvrPostAddrText()); // 클레임 우편주소
			reOrderSales.setRcvrDtlAddrText(ocClaim.getRcvrDtlAddrText()); // 클레임 수취인 상세주
		}

		ocOrderDao.insertOrder(reOrderSales); // 신규 주문 생성

		/*
		 * 클레임상품 기준으로 교환매출취소, 교환매출/배송이력 리오더 상품 생성(주문상품)
		 */
		for (OcClaimProduct claimProduct : claimProductList) {
			/*
			 * [신규 주문상품 생성] [주문상품이력 생성] - 교환매출취소
			 */
			OcOrderProduct newOrderProductSalesCancel = new OcOrderProduct();

			newOrderProductSalesCancel.setOrderNo(claimProduct.getOrderNo());
			newOrderProductSalesCancel.setOrderPrdtSeq(claimProduct.getOrderPrdtSeq());

			// 클레임의 주문번호와 주문상품순번으로 해당 주문상품 내용 조회
			newOrderProductSalesCancel = ocOrderProductDao.selectByPrimaryKey(newOrderProductSalesCancel);

			newOrderProductSalesCancel.setOrderNo(reOrderNoSalesCancel); // 새롭게 생성된 주문 번호로 set
			// newOrderProductSalesCancel.setOrderPrdtSeq(null);
			newOrderProductSalesCancel.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE); // 취소완료
			newOrderProductSalesCancel.setSalesDcsnYmd(null); // 매출확정일자
			newOrderProductSalesCancel.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
			newOrderProductSalesCancel.setModerNo(LoginManager.getUserDetails().getAdminNo());

			ocOrderProductDao.insertOrderProduct(newOrderProductSalesCancel); // 신규 주문상품 생성

			// 클레임상품 클레임생성 리오더 주문번호 업데이트(매출취소대상 클레임 상품)
			OcClaimProduct reOrderSalesCancelUpdateClaimProduct = new OcClaimProduct();

			reOrderSalesCancelUpdateClaimProduct.setModerNo(LoginManager.getUserDetails().getAdminNo());
			reOrderSalesCancelUpdateClaimProduct.setClmCrtOrderNo(reOrderNoSalesCancel);
			reOrderSalesCancelUpdateClaimProduct.setClmNo(claimProduct.getClmNo());
			reOrderSalesCancelUpdateClaimProduct.setClmPrdtSeq(claimProduct.getClmPrdtSeq());

			ocClaimProductDao.updateClmCrtOrderNo(reOrderSalesCancelUpdateClaimProduct); // 클레임상품 클레임생성 리오더 주문번호 업데이트

			OcOrderProductHistory ocOrderProductHistorySalesCancel = new OcOrderProductHistory();
			ocOrderProductHistorySalesCancel.setOrderNo(reOrderNoSalesCancel);
			ocOrderProductHistorySalesCancel.setOrderPrdtSeq(newOrderProductSalesCancel.getOrderPrdtSeq());
			ocOrderProductHistorySalesCancel.setPrdtNo(newOrderProductSalesCancel.getPrdtNo());
			ocOrderProductHistorySalesCancel.setPrdtOptnNo(newOrderProductSalesCancel.getPrdtOptnNo());
			ocOrderProductHistorySalesCancel.setPrdtName(newOrderProductSalesCancel.getPrdtName());
			ocOrderProductHistorySalesCancel.setEngPrdtName(newOrderProductSalesCancel.getEngPrdtName());
			ocOrderProductHistorySalesCancel.setOptnName(newOrderProductSalesCancel.getOptnName());
			ocOrderProductHistorySalesCancel.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_COMPLETE); // 결제완료
			ocOrderProductHistorySalesCancel.setNoteText(null);
			ocOrderProductHistorySalesCancel.setRgsterNo(LoginManager.getUserDetails().getAdminNo());

			ocOrderProductHistoryDao.insertProductHistory(ocOrderProductHistorySalesCancel); // 주문상품이력 생성

			/*
			 * [신규 주문상품 생성] [주문상품이력 생성] - 교환매출
			 */
			OcOrderProduct newOrderProductSales = new OcOrderProduct();

			newOrderProductSales.setOrderNo(claimProduct.getOrderNo());
			newOrderProductSales.setOrderPrdtSeq(claimProduct.getOrderPrdtSeq());

			// 클레임의 주문번호와 주문상품순번으로 해당 주문상품 내용 조회
			newOrderProductSales = ocOrderProductDao.selectByPrimaryKey(newOrderProductSales);

			newOrderProductSales.setOrderNo(reOrderNoSales); // 새롭게 생성된 주문 번호로 set
			// newOrderProductSales.setOrderPrdtSeq(null);
			newOrderProductSales.setPrdtOptnNo(claimProduct.getChangePrdtOptnNo()); // 재배송상품기준으로
			newOrderProductSales.setOptnName(claimProduct.getChangeOptnName()); // 재배송상품기준으로
			newOrderProductSales.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_COMPLETE); // 주문상품상태코드 - 결제완료
			newOrderProductSalesCancel.setSalesDcsnYmd(null); // 매출확정일자
			newOrderProductSales.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
			newOrderProductSales.setModerNo(LoginManager.getUserDetails().getAdminNo());

			ocOrderProductDao.insertOrderProduct(newOrderProductSales); // 신규 주문상품 생성

			// 클레임상품 클레임생성 리오더 주문번호 업데이트(매출대상 클레임 상품)
			OcClaimProduct reOrderSalesUpdateClaimProduct = new OcClaimProduct();

			reOrderSalesUpdateClaimProduct.setModerNo(LoginManager.getUserDetails().getAdminNo());
			reOrderSalesUpdateClaimProduct.setClmCrtOrderNo(reOrderNoSales);
			reOrderSalesUpdateClaimProduct.setClmNo(claimProduct.getClmNo());
			reOrderSalesUpdateClaimProduct.setUpClmPrdtSeq(claimProduct.getClmPrdtSeq()); // 재배송상품 기준으로 set

			ocClaimProductDao.updateClmCrtOrderNo(reOrderSalesUpdateClaimProduct); // 클레임상품 클레임생성 리오더 주문번호 업데이트

			OcOrderProductHistory ocOrderProductHistorySales = new OcOrderProductHistory();
			ocOrderProductHistorySales.setOrderNo(reOrderNoSales);
			ocOrderProductHistorySales.setOrderPrdtSeq(newOrderProductSales.getOrderPrdtSeq());
			ocOrderProductHistorySales.setPrdtNo(newOrderProductSales.getPrdtNo());
			ocOrderProductHistorySales.setPrdtOptnNo(claimProduct.getChangePrdtOptnNo()); // 재배송상품기준으로
			ocOrderProductHistorySales.setPrdtName(newOrderProductSales.getPrdtName());
			ocOrderProductHistorySales.setEngPrdtName(newOrderProductSales.getEngPrdtName());
			ocOrderProductHistorySales.setOptnName(claimProduct.getChangeOptnName()); // 재배송상품기준으로
			ocOrderProductHistorySales.setOrderPrdtStatCode(newOrderProductSales.getOrderPrdtStatCode());
			ocOrderProductHistorySales.setNoteText(null);
			ocOrderProductHistorySales.setRgsterNo(LoginManager.getUserDetails().getAdminNo());

			ocOrderProductHistoryDao.insertProductHistory(ocOrderProductHistorySales); // 주문상품이력 생성

			/*
			 * [신규주문 주문배송이력 생성] - 교환매출
			 */
			OcOrderDeliveryHistory ocOrderDeliveryHistory = new OcOrderDeliveryHistory();
			ocOrderDeliveryHistory.setOrderNo(reOrderNoSales);
			ocOrderDeliveryHistory.setOrderPrdtSeq(newOrderProductSales.getOrderPrdtSeq());
			ocOrderDeliveryHistory.setStockGbnCode(CommonCode.STOCK_GBN_CODE_AI); // 재고구분코드
			ocOrderDeliveryHistory.setStoreNo(null); // 매장번호
			ocOrderDeliveryHistory.setStoreChngDgreCount((short) 0); // 매장변경차수
			ocOrderDeliveryHistory.setDlvyProcDtm(null); // 배송처리일시
			ocOrderDeliveryHistory.setLogisVndrCode(null); // 택배사코드
			ocOrderDeliveryHistory.setWaybilNoText(null); // 운송장번호
			ocOrderDeliveryHistory.setDlvyDscntcYn(Const.BOOLEAN_FALSE); // 배송중단여부
			ocOrderDeliveryHistory.setDlvyDscntcRsnCode(null); // 배송중단사유코드
			ocOrderDeliveryHistory.setDlvyDscntcOpetrNo(null); // 배송중단처리자번호
			ocOrderDeliveryHistory.setDlvyDscntcAcceptDtm(null); // 배송중단접수일시
			ocOrderDeliveryHistory.setDlvyDscntcProcDtm(null); // 배송중단처리일시
			ocOrderDeliveryHistory.setPickupPrpareCmlptDtm(null); // 픽업준비완료일시
			ocOrderDeliveryHistory.setPickupPsbltYmd(null); // 픽업가능일
			ocOrderDeliveryHistory.setMissProcYn(Const.BOOLEAN_FALSE); // 분실처리여부
			ocOrderDeliveryHistory.setMissProcTypeCode(null); // 분실처리유형코드
			ocOrderDeliveryHistory.setInsdMgmtInfoText(null); // 내부관리정보
			ocOrderDeliveryHistory.setWmsSendYn(Const.BOOLEAN_FALSE); // WMS전송여부
			ocOrderDeliveryHistory.setDlvyTodoYmd(null); // 배송예정일
			ocOrderDeliveryHistory.setDlvyCmlptDtm(null); // 배송완료일시
			ocOrderDeliveryHistory.setRcvrName(claimInfo.getRcvrName()); // 수취인명
			ocOrderDeliveryHistory.setRcvrTelNoText(claimInfo.getRcvrTelNoText()); // 수취인전화번호
			ocOrderDeliveryHistory.setRcvrHdphnNoText(claimInfo.getRcvrHdphnNoText()); // 수취인핸드폰번호
			ocOrderDeliveryHistory.setRcvrPostCodeText(claimInfo.getRcvrPostCodeText()); // 수취인우편번호
			ocOrderDeliveryHistory.setRcvrPostAddrText(claimInfo.getRcvrPostAddrText()); // 수취인우편주소
			ocOrderDeliveryHistory.setRcvrDtlAddrText(claimInfo.getRcvrDtlAddrText()); // 수취인상세주소
			ocOrderDeliveryHistory.setClmNo(claimProduct.getClmNo()); // 클레임번호
			ocOrderDeliveryHistory.setClmPrdtSeq(claimProduct.getChangeClmPrdtSeq()); // 클레임상품순번(재배송상품기준)
			ocOrderDeliveryHistory.setDlvyMemoText(null); // 배송메모
			ocOrderDeliveryHistory.setDlvyStatCode(CommonCode.DLVY_STAT_CODE_PAYMENT_FINISH); // 배송상태코드
			ocOrderDeliveryHistory.setLogisCnvrtSendDtm(null); // 택배전환발송일시
			ocOrderDeliveryHistory.setRsvDlvyDtm(null); // 예약출고일시
			// 배송ID는 쿼리에서 생성
			ocOrderDeliveryHistory.setLogisPymntPrdtAmt(0); // 택배사결제상품금액
			ocOrderDeliveryHistory.setLogisPymntDlvyAmt(0); // 택배사결제배송비
			ocOrderDeliveryHistory.setImpsbltProcYn(Const.BOOLEAN_FALSE); // 불가처리여부
			ocOrderDeliveryHistory.setImpsbltProcAcceptDtm(null); // 불가처리접수일시
			ocOrderDeliveryHistory.setImpsbltProcCmlptDtm(null); // 불가처리완료일시
			ocOrderDeliveryHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자
			ocOrderDeliveryHistory.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자

			ocOrderDeliveryHistoryDao.insertDelivery(ocOrderDeliveryHistory); // 주문배송이력 생성

			/*
			 * 재배송 상품 기준 재고 차감
			 */
			orderService.updateProductStockAdjust(newOrderProductSalesCancel.getPrdtNo(),
					newOrderProductSalesCancel.getPrdtOptnNo(), 1, CommonCode.STOCK_GBN_CODE_AI, true);
		}

		/*
		 * 클레임/클레임상품 상태 변경 처리 및 상태변경이력 등록
		 */
		// 회수상품 기준 상태변경 및 상태변경이력 등록
		this.setClaimAndClaimPrdtStatChangeProc(claimInfo, claimProductList, clmStatCode, clmPrdtStatCode);

		/*
		 * 재배송상품 기준 상태변경
		 */
		Object[] claimPrdtSeqs = claimProductList.stream().map(OcClaimProduct::getClmPrdtSeq).toArray();

		// 교환불가 상품을 제외한 회수상품 기준으로 재배송 상품 추출
		List<OcClaimProduct> reDlvyProductList = tempSourceClaimProducts.stream()
				.filter(x -> Arrays.asList(claimPrdtSeqs).contains(x.getUpClmPrdtSeq())).collect(Collectors.toList());

		// 상태변경
		for (OcClaimProduct claimProduct : reDlvyProductList) {
			claimProduct.setModerNo(LoginManager.getUserDetails().getAdminNo());
			claimProduct.setClmPrdtStatCode(clmPrdtStatCode); // 클레임상품 상태 코드

			ocClaimProductDao.updateClaimProductStatCode(claimProduct); // 클레임상품 상태 변경
		}
	}

	/**
	 * @Desc : 교환 클레임완료 처리
	 * @Method Name : setFinishClaimExchangeAllProc
	 * @Date : 2019. 4. 5.
	 * @Author : KTH
	 * @param ocClaim
	 * @throws Exception
	 */
	public void setFinishClaimExchangeAllProc(OcClaim ocClaim) throws Exception {
		int impossiblePrdtCnt = 0; // 처리유형이 불가인 클레임 상품 개수

		// 클레임 정보
		OcClaim claimInfo = ocClaimDao.selectClaimBasisInfo(ocClaim);

		// 클레임 상태코드 체크
		if (!UtilsText.equals(claimInfo.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_DELIVERY_ORDER)
				&& !UtilsText.equals(claimInfo.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_DELIVERY_PROC)) {
			throw new Exception("validMsg:클레임 완료가 불가한 상태입니다.");
		}

		/*
		 * [원 주문정보 get]
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보

		// 클레임상품 정보(클레임처리불가 상태인 상품은 제외)
		OcClaimProduct paramClaimProduct = new OcClaimProduct();
		paramClaimProduct.setClmNo(ocClaim.getClmNo());
		paramClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 상품 코드
		paramClaimProduct.setPrdtRltnFileSeq(1); // 상품관련파일순번 : 1 (대표)
		List<OcClaimProduct> claimProductAllList = ocClaimProductDao.selectClaimProductList(paramClaimProduct);

		// 교환불가인 상품 제외한 클레임 상품 목록
		List<OcClaimProduct> claimProductPossibleList = claimProductAllList.stream().filter(
				x -> !UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE))
				.collect(Collectors.toList());

		// 교환불가인 상품 목록
		List<OcClaimProduct> claimProductImpossibleList = claimProductAllList.stream().filter(
				x -> UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE))
				.collect(Collectors.toList());

		/*
		 * 교환불가 중 미출반품처리 대상인 경우 반품 등록 대상으로 지정
		 */
		// 미출반품처리 대상 지정 목록
		List<OcClaimProduct> unReleasedClaimProductList = new ArrayList<OcClaimProduct>();

		if (!ObjectUtils.isEmpty(claimProductImpossibleList)) {
			for (OcClaimProduct claimProductImpossible : claimProductImpossibleList) {
				if (UtilsText.equals(claimProductImpossible.getClmProcTypeCode(),
						CommonCode.CLM_PROC_TYPE_CODE_EXCHANGE_UNRELEASED_PROC)) {

					unReleasedClaimProductList.add(claimProductImpossible); // 반품등록 대상 목록 추가
				}
			}
		}

		/*
		 * 교환 미출반품처리 대상 반품 등록
		 */
		if (!ObjectUtils.isEmpty(unReleasedClaimProductList)) {
			this.registClaimReturnForUnreleased(claimInfo, unReleasedClaimProductList);
		}

		/*
		 * 교환 불가가 아닌 클레임 상품 기준으로 리오더 주문 정산확정일자, 리오더 주문상품 상태 값 변경
		 */
		if (!ObjectUtils.isEmpty(claimProductPossibleList)) {
			for (OcClaimProduct claimProductPossible : claimProductPossibleList) {
				OcOrderProduct updateOrderProduct = new OcOrderProduct();

				updateOrderProduct.setModerNo(LoginManager.getUserDetails().getAdminNo());
				updateOrderProduct.setClmNo(claimProductPossible.getClmNo());
				updateOrderProduct.setOrderPrdtSeq(claimProductPossible.getOrderPrdtSeq());

				// 회수상품, 재배송상품 리오더 기준으로 정산확정일자를 업데이트
				ocOrderProductDao.updateExcclcByClmNo(updateOrderProduct);

				updateOrderProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_DELIVERY_FINISH); // 배송완료
				updateOrderProduct.setSalesCnclGbnType(CommonCode.SALES_CNCL_GBN_TYPE_EXCHANGE_SALE); // 교환매출(재배송 상품)

				// 교환 재배송 상품 기준 상태 값 업데이트
				ocOrderProductDao.updatePrdtStatExchangeReDlvyByClmNo(updateOrderProduct);
			}
		}

		/*
		 * 클레임 배송비 매출 리오더 생성
		 */
		this.registClaimDlvyAmtReOrder(claimInfo, ocOrder);

		/*
		 * [클레임상품 상태 변경] [클레임상태이력 등록] - 교환 불가가 아닌 클레임 상품 기준으로 교환완료 상태 변경
		 */
		for (OcClaimProduct claimProduct : claimProductPossibleList) {
			claimProduct.setModerNo(LoginManager.getUserDetails().getAdminNo());
			claimProduct.setClmPrdtStatCode(CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_FINISH); // 클레임상품 상태 코드

			ocClaimProductDao.updateClaimProductStatCode(claimProduct); // 클레임상품 상태 변경

			OcClaimStatusHistory ocClaimStatusHistory = new OcClaimStatusHistory();
			ocClaimStatusHistory.setClmNo(claimProduct.getClmNo());
			ocClaimStatusHistory.setClmPrdtSeq(claimProduct.getClmPrdtSeq());
			ocClaimStatusHistory.setClmPrdtStatCode(CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_FINISH); // 클레임상품 상태 코드
			ocClaimStatusHistory.setStockGbnCode(null);
			ocClaimStatusHistory.setNoteText(null);
			ocClaimStatusHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자

			ocClaimStatusHistoryDao.insertClaimStatusHistory(ocClaimStatusHistory); // 클레임상태이력 등록
		}

		/*
		 * [클레임 상태 변경]
		 */
		if (claimProductAllList.size() == impossiblePrdtCnt) {
			claimInfo.setModerNo(LoginManager.getUserDetails().getAdminNo());
			claimInfo.setClmStatCode(CommonCode.CLM_STAT_CODE_EXCHANGE_IMPOSSIBLE); // 클레임 상태 코드:교환불가
			ocClaimDao.updateClaimStatCode(claimInfo);
		} else {
			claimInfo.setModerNo(LoginManager.getUserDetails().getAdminNo());
			claimInfo.setClmStatCode(CommonCode.CLM_STAT_CODE_EXCHANGE_FINISH); // 클레임 상태 코드:교환완료
			ocClaimDao.updateClaimStatCode(claimInfo);
		}

		Map<String, String> exchangeParamMap = new HashMap<>();
		exchangeParamMap.put("claimNo", ocClaim.getClmNo());

		// 2020.02.17 : 교환 완료 처리시 SALES 생성 프로시져 / 성공 : "0"
		// String resultSalesExchange =
		// this.setCallProcedureForSalesExchange(exchangeParamMap);
		String resultSalesExchange = "0";
		log.debug("resultSalesExchange : {}", resultSalesExchange);
		if (!UtilsText.equals(resultSalesExchange, "0")) {
			throw new Exception("validMsg:교환 클레임 매출 생성에 실패하였습니다.");
		}

	}

	/**
	 * @Desc : 교환 완료 처리시 SALES 생성 프로시져 / 성공 : "0"
	 * @Method Name : setCallProcedureForSalesExchange
	 * @Date : 2020. 02. 11.
	 * @Author : 이강수
	 * @param Map<String, String> map
	 * @return
	 */
	public String setCallProcedureForSalesExchange(Map<String, String> map) throws Exception {
		ocClaimDao.callProcedureForSalesExchange(map);
		return map.get("errorCode");
	}

	/**
	 * @Desc : 교환 미출반품처리 대상 반품 등록
	 * @Method Name : registClaimReturn
	 * @Date : 2019. 7. 22.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocClaimProducts
	 * @param ocClaimMemo
	 * @param kcpPaymentApproval
	 * @throws Exception
	 */
	public void registClaimReturnForUnreleased(OcClaim ocClaimInfo, List<OcClaimProduct> ocClaimProductList)
			throws Exception {

		OcClaimAmountVO ocClaimAmountVO = new OcClaimAmountVO();

		OcClaim ocClaim = new OcClaim();
		BeanUtils.copyProperties(ocClaimInfo, ocClaim); // 내용 복사

		String clmStatCode = "";
		String clmPrdtStatCode = "";

		clmStatCode = CommonCode.CLM_STAT_CODE_RETURN_JUDGE_FINISH; // 클레임상태코드 - 심의완료
		clmPrdtStatCode = CommonCode.CLM_PRDT_STAT_CODE_RETURN_JUDGE_FINISH; // 클레임상품상태코드 - 심의완료

		ocClaim.setClaimRgsterNo(LoginManager.getUserDetails().getAdminNo());

		/*
		 * [원 주문정보 get]
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보

		/*
		 * [원 주문상품 목록 - 사은품/배송비 포함]
		 */
		OcOrderProduct ocOrderProduct = new OcOrderProduct();
		ocOrderProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 추출용
		ocOrderProduct.setOrderNo(ocOrder.getOrgOrderNo()); // 원주문번호 기준

		List<OcOrderProduct> orgOrderProductList = ocOrderProductDao.selectOrgOrderProductList(ocOrderProduct);

		ocClaimAmountVO.setOrgOrderProductList(orgOrderProductList);

		/*
		 * [주문자 회원 정보]
		 */
		MbMember mbMember = memberDao.selectMemberDefalutInfo(ocOrder.getMemberNo());

		/*
		 * [기본 등록정보 set]
		 */
		ocClaim.setClmNo(null);
		ocClaim.setClmGbnCode(CommonCode.CLM_GBN_CODE_RETURN); // 클레임구분코드

		ocClaim.setRtrvlGbnType(CommonCode.RTRVL_GBN_TYPE_ONLINE); // 회수지구분(O:온라인)
		ocClaim.setRtrvlStoreNo(null); // 회수매장번호
		ocClaim.setRtrvlReqYn(Const.BOOLEAN_FALSE); // 회수신청여부

		ocClaim.setDlvyTypeCode(CommonCode.DLVY_TYPE_CODE_NORMAL_LOGISTICS); // 배송유형코드-일반택배

		// 주문자 회원 정보에 환불계좌 정보가 있으면 set
		if (UtilsText.isNotEmpty(mbMember.getBankCode()) && UtilsText.isNotEmpty(mbMember.getAcntNoText())
				&& UtilsText.isNotEmpty(mbMember.getAcntHldrName())) {
			ocClaim.setBankCode(mbMember.getBankCode());
			ocClaim.setRfndAcntText(mbMember.getAcntNoText());
			ocClaim.setAcntHldrName(mbMember.getAcntHldrName());
		}

		ocClaim.setVndrNo(ocClaimProductList.get(0).getVndrNo()); // 업체번호(클레임상품 중 한개 발췌-업체단위로 클레임 걸림)

		ocClaim.setTotalRfndAmt(0); // 총환불금액
		ocClaim.setTotalRedempAmt(0); // 총환수금액

		// 미처리 여부 set
		if (UtilsText.isEmpty(ocClaim.getUnProcYn())) {
			ocClaim.setUnProcYn(Const.BOOLEAN_FALSE);
		}

		ocClaim.setAdminAcceptYn(Const.BOOLEAN_TRUE); // 관리자접수여부
		ocClaim.setOflnAcceptYn(Const.BOOLEAN_FALSE); // 오프라인접수여부

		// 교환 미출반품처리 대상 반품 등록은 클레임 배송비 무료
		ocClaim.setAddDlvyAmtPymntType(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE); // 추가배송비결제방법
		ocClaim.setAddDlvyAmt(0); // 추가배송비
		ocClaim.setHoldCpnSeq(null); // 보유쿠폰순번

		ocClaim.setClmStatCode(clmStatCode); // 클레임상태코드
		ocClaim.setBranchMoveCode(CommonCode.BRANCH_MOVE_CODE_ORIGIN_DEAL); // 점간이동코드 - 원거래

		ocClaim.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자
		ocClaim.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자

		/*
		 * [클레임 등록(마스터)] 등록 시 생성된 clmNo 를 ocClaim 에서 가지고 있음
		 */
		ocClaimDao.insertClaimInfo(ocClaim);

		/*
		 * [클레임상품 등록] [클레임상태이력 등록]
		 */
		for (OcClaimProduct ocClaimProduct : ocClaimProductList) {
			ocClaimProduct.setClmRsnCode(CommonCode.CLM_RSN_CODE_RETURN_NOT_DELIVERY);
			ocClaimProduct.setClmEtcRsnText("교환 미출반품처리 대상 반품 등록");
			ocClaimProduct.setCstmrAlertContText(null);
			ocClaimProduct.setClmProcTypeCode(CommonCode.CLM_PROC_TYPE_CODE_RETURN_UNRELEASED);
			ocClaimProduct.setClmProcContText("교환 미출반품처리 대상 반품 심의완료 처리");

			// 클레임상품 등록, 클레임상태이력 등록
			this.insertClaimPrdtAndClaimStatusHistory(ocClaim, ocClaimProduct, clmPrdtStatCode);

			// 교환 미출반품처리 반품등록은 사은품 등록 하지 않음
		}

		/*************************************
		 * 클레임 금액 계산(클레임 마스터/상품 등록 후 호출)
		 *************************************/
		// ocClaimAmountVO 에 계산된 금액 set
		claimProcService.setClaimAmountCalcForPartCancel(ocClaim, ocOrder, ocClaimAmountVO,
				CommonCode.CLM_GBN_CODE_RETURN, false);

		/*
		 * 클레임 계산 결과 환수금이 더 많은 경우 취소 불가 처리
		 */
		if (ocClaimAmountVO.getRefundCnclAmt() < 0) {
			throw new Exception("validMsg:환수금이 발생하여 취소가 불가합니다.");
		}

		/*
		 * 증감 구매적립 포인트 계산
		 */
		int thisClaimVariationSavePoint = ocClaimAmountVO.getVariationSavePoint(); // 현재 클레임 증감 적립포인트

		// 더블적립 쿠폰이 사용된 경우
		if (ocClaimAmountVO.getOrderDoubleDscntCpnInfo() != null) {
			// 주문의 구매적립률을 이용하여 계산하므로 주석처리
			// thisClaimVariationSavePoint = thisClaimVariationSavePoint * 2;
		}

		/*
		 * 구매적립 포인트 환수 체크(임직원이 아닌 멤버쉽 회원인 경우만 체크)
		 */
		int clawbackPoint = 0; // 환수포인트

		if (UtilsText.equals(ocOrder.getMemberTypeCode(), CommonCode.MEMBER_TYPE_CODE_MEMBERSHIP)
				&& UtilsText.equals(ocOrder.getEmpYn(), Const.BOOLEAN_FALSE)) {

			// 환수할 포인트가 발생한 경우
			if (thisClaimVariationSavePoint < 0) {
				// 환수포인트 조회 인터페이스
				try {
					clawbackPoint = membershipPointService.getClawbackPoint(mbMember.getSafeKey(),
							mbMember.getSafeKeySeq(), ocOrder.getOrgOrderNo(), Math.abs(thisClaimVariationSavePoint));
				} catch (Exception e) {
					throw new Exception("validMsg:포인트 조회에 실패했습니다.");
				}
			}
		}

		/*
		 * 클레임 마스터 금액 업데이트(변동 적립포인트, 환수포인트 포함)
		 */
		OcClaim updateAmtClaim = new OcClaim();
		updateAmtClaim.setClmNo(ocClaim.getClmNo());
		updateAmtClaim.setTotalRfndAmt(ocClaimAmountVO.getRefundCnclAmt()); // 총환불금액
		updateAmtClaim.setTotalRedempAmt(ocClaimAmountVO.getRedempAmtByMultiBuy() + ocClaimAmountVO.getClaimDlvyAmt()); // 총환수금액
		updateAmtClaim.setIrdsSavePoint((int) ocClaimAmountVO.getVariationSavePoint()); // 증감적립포인트
		updateAmtClaim.setRedempSavePoint(clawbackPoint); // 환수적립포인트
		updateAmtClaim.setModerNo(LoginManager.getUserDetails().getAdminNo());

		ocClaimDao.updateClaimStat(updateAmtClaim);

		/*
		 * 환수프로모션비(다족구매로 인해 발생) 발생된 경우 결제히스토리 등록
		 */
		if (ocClaimAmountVO.getRedempAmtByMultiBuy() > 0) {
			for (OcClaimPayment redempMultiBuyPayment : ocClaimAmountVO.getRedempMultiBuyPaymentList()) {
				ocClaimPaymentDao.insertClaimPayment(redempMultiBuyPayment);
			}
		}

		/*
		 * 기존 다족구매 리오더 매출로 인해 변경된 금액과 원 주문 기준 취소상품 금액 차이 결제히스토리 등록(현재 취소금을 맞추기 위한 이력)
		 */
		if (ocClaimAmountVO.getAddMultiBuyDifferAmt() > 0) {
			for (OcClaimPayment addMultiBuyDifferPaymentList : ocClaimAmountVO.getAddMultiBuyDifferPaymentList()) {
				ocClaimPaymentDao.insertClaimPayment(addMultiBuyDifferPaymentList);
			}
		}

		/*
		 * 클레임 결제 등록
		 */
		// 주 결제 수단
		if (ocClaimAmountVO.getMainPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getMainPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getMainCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 기프트
		if (ocClaimAmountVO.getGiftPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getGiftPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getGiftCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 이벤트 포인트
		if (ocClaimAmountVO.getEventPointPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getEventPointPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getEventPointCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소
																									// 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 포인트
		if (ocClaimAmountVO.getPointPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getPointPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getPointCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로
																								// 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

	}

	/**
	 * @Desc : 반품 클레임완료 처리
	 * @Method Name : setFinishClaimReturnAllProc
	 * @Date : 2019. 4. 5.
	 * @Author : KTH
	 * @param ocClaim
	 * @throws Exception
	 */
	public Map<String, Object> setFinishClaimReturnAllProc(OcClaim paramClaim, OcClaimPayment[] ocClaimPayments)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		OcClaimAmountVO ocClaimAmountVO = new OcClaimAmountVO();

		/*
		 * 클레임 정보
		 */
		OcClaim ocClaim = ocClaimDao.selectClaimBasisInfo(paramClaim);

		// 클레임 상태코드 체크
		if (!UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_RETURN_JUDGE_FINISH)) {
			throw new Exception("validMsg:클레임 완료가 불가한 상태입니다.");
		}

		// 파라미터에서 넘어오거나 기타 필요한 정보는 별도로 set
		ocClaim.setClaimRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자 no set
		ocClaim.setChngInputCnclAmtYn(paramClaim.getChngInputCnclAmtYn()); // 폼 취소 설정금액 변경 여부

		/*
		 * 클레임상품 정보(클레임처리불가 상태인 상품은 제외)
		 */
		OcClaimProduct paramClaimProduct = new OcClaimProduct();
		paramClaimProduct.setClmNo(ocClaim.getClmNo());
		paramClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 상품 코드
		paramClaimProduct.setPrdtRltnFileSeq(1); // 상품관련파일순번 : 1 (대표)
		List<OcClaimProduct> tempSourceClaimProducts = ocClaimProductDao.selectClaimProductList(paramClaimProduct);

		// 클레임상품 정보(클레임처리불가 상태인 상품은 제외)
		List<OcClaimProduct> claimProductList = tempSourceClaimProducts.stream()
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE))
				.collect(Collectors.toList());

		// 현재 클레임 개수(사은품/배송비 제외)
		long thisTimeClaimCnt = claimProductList.stream()
				.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
						&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
				.count();

		/*
		 * [원 주문정보 get]
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보

		/*
		 * [주문자 정보]
		 */
		MbMember mbMember = memberDao.selectMemberDefalutInfo(ocOrder.getMemberNo());

		/*************************************
		 * 클레임 금액 계산
		 *************************************/
		log.error("반품 클레임완료 처리 금액계산  START ClmNo => {}", ocClaim.getClmNo());
		claimProcService.setClaimAmountCalcForPartCancel(ocClaim, ocOrder, ocClaimAmountVO,
				CommonCode.CLM_GBN_CODE_RETURN, false);

		// 원 주문 상품 개수(사은품/배송비 제외)
		long orgOrderProductCnt = ocClaimAmountVO.getOrgOrderProductList().stream()
				.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
						&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
				.count();

		// 완료된 클레임 개수(반품/취소 기준, 사은품/배송비 제외)
		int finishClaimCnt = ocClaimAmountVO.getFinishedClaimProductList().size();

		/*
		 * 현재 클레임 상품 목록의 금액 정보를 매출기준 리오더 금액으로 변경(다족구매로 인한 주문금액 변경 고려)
		 */
		for (OcClaimProduct thisTimeClaimProduct : ocClaimAmountVO.getThisTimeClaimProductList()) {
			OcOrderProduct reOrderSalesProduct = ocClaimAmountVO.getReOrderProductSalesList().stream()
					.filter(x -> UtilsText.equals(String.valueOf(x.getOrderPrdtSeq()),
							String.valueOf(thisTimeClaimProduct.getOrderPrdtSeq())))
					.max(Comparator.comparing(OcOrderProduct::getOrderNo)).orElse(null);

			if (reOrderSalesProduct != null) {
				thisTimeClaimProduct.setTotalDscntAmt(reOrderSalesProduct.getTotalDscntAmt());
				thisTimeClaimProduct.setOrderAmt(reOrderSalesProduct.getOrderAmt());
			}
		}

		/**
		 * 폼에서 임의로 취소 설정금액을 변경한 경우 해당 금액으로 취소금액 히스토리 및 정보 변경
		 */
		if (UtilsText.equals(ocClaim.getChngInputCnclAmtYn(), Const.BOOLEAN_TRUE)) {
			this.setChngInputCnclAmt(ocClaim, ocClaimAmountVO, ocClaimPayments);
		}

		/*
		 * 주문 마스터/상품 신규(매출취소 리오더) 등록
		 */
		OcOrder reOrder = new OcOrder();
		BeanUtils.copyProperties(ocOrder, reOrder); // 내용 복사

		String reOrderNo = orderService.createOrderSeq(); // 신규 주문번호

		ocClaim.setReOrderNo(reOrderNo); // 클레임 정보에도 신규주문번호 set

		// 리오더 주문에 들어갈 주문 합계금 컬럼 값 관련
		int _totalNormalAmt = ocClaimAmountVO.getThisTimeClaimProductList().stream().mapToInt(o -> o.getPrdtNormalAmt())
				.sum();
		int _totalSellAmt = ocClaimAmountVO.getThisTimeClaimProductList().stream().mapToInt(o -> o.getPrdtSellAmt())
				.sum();
		int _pymntTodoAmt = ocClaimAmountVO.getThisTimeClaimProductList().stream().mapToInt(o -> o.getOrderAmt()).sum();
		int _pymntAmt = ocClaimAmountVO.getThisTimeClaimProductList().stream().mapToInt(o -> o.getOrderAmt()).sum();

		reOrder.setOrderNo(reOrderNo); // 신규주문번호
		reOrder.setClmNo(ocClaim.getClmNo()); // 클레임번호 set
		reOrder.setTotalNormalAmt(_totalNormalAmt); // 정상가총액
		reOrder.setTotalSellAmt(_totalSellAmt); // 판매가총액
		reOrder.setTotalPromoDscntAmt(0); // 프로모션할인총액
		reOrder.setTotalCpnDscntAmt(0); // 쿠폰할인총액
		reOrder.setTotalEmpDscntAmt(0); // 임직원할인총액
		reOrder.setPointUseAmt(0); // 포인트사용액
		reOrder.setEventPointUseAmt(0); // 이벤트포인트사용액
		reOrder.setMmnyDlvyAmt(ocClaimAmountVO.getThisTimeClaimProductList().stream()
				.filter(x -> UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
				.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_TRUE)).mapToInt(o -> o.getOrderAmt())
				.sum()); // 자사배송비
		reOrder.setTotalVndrDlvyAmt(ocClaimAmountVO.getThisTimeClaimProductList().stream()
				.filter(x -> UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
				.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_FALSE)).mapToInt(o -> o.getOrderAmt())
				.sum()); // 입점사배송비총액
		reOrder.setPymntTodoAmt(_pymntTodoAmt); // 결제예정금액
		reOrder.setPymntAmt(_pymntAmt); // 결제금액
		reOrder.setCnclAmt(0); // 취소금액
		reOrder.setSalesCnclGbnType(CommonCode.SALES_CNCL_GBN_TYPE_CANCEL); // 매출취소구분 : 취소
		// reOrder.setOrderStatCode(); // 부분취소 시 상태 값은 생성 시 지정하지 않음
		reOrder.setRgsterNo(ocClaim.getClaimRgsterNo());
		reOrder.setModerNo(ocClaim.getClaimRgsterNo());

		// 신규 주문(매출취소 리오더) 생성
		ocOrderDao.insertOrder(reOrder);

		// 2020.02.09 -> 사은품이 있을 시에 사은품이 먼저 OcOrderProduct에 들어가서 DB Error 뱉던문제를 수정
		// 해결 : clmPrdtSeq로 재정렬을 하여 insert하는 for문에 투입함으로써 해결됨
		List<OcClaimProduct> thisTimeClaimProductList = ocClaimAmountVO.getThisTimeClaimProductList();
		thisTimeClaimProductList = thisTimeClaimProductList.stream()
				.sorted(Comparator.comparing(OcClaimProduct::getClmPrdtSeq)).collect(Collectors.toList());

		// 신규 주문상품(매출취소 리오더) 생성
		for (OcClaimProduct claimProduct : thisTimeClaimProductList) {
			OcOrderProduct reOrderProduct = new OcOrderProduct();

			OcOrderProduct orderProduct = null;

			// 기존 교환매출 기준의 상품을 주문번호 기준으로 먼저 확인
			orderProduct = ocClaimAmountVO.getReOrderProductList().stream()
					.filter(x -> UtilsText.equals(claimProduct.getOrderNo(), x.getOrderNo()) && UtilsText.equals(
							String.valueOf(claimProduct.getOrderPrdtSeq()), String.valueOf(x.getOrderPrdtSeq())))
					.filter(x -> UtilsText.equals(x.getOrderPrdtStatCode(),
							CommonCode.ORDER_PRDT_STAT_CODE_DELIVERY_FINISH)
							|| UtilsText.equals(x.getOrderPrdtStatCode(), CommonCode.ORDER_PRDT_STAT_CODE_BUY_CONFIRM))
					.filter(x -> UtilsText.equals(x.getSalesCnclGbnType(),
							CommonCode.SALES_CNCL_GBN_TYPE_EXCHANGE_SALE))
					.findFirst().orElse(null);

			// 클레임 상품이 교환매출 상품 대상이 아니었다면 원주문 번호 기준으로 원주문 상품을 확인
			if (orderProduct == null) {
				orderProduct = ocClaimAmountVO.getOrgOrderProductList().stream()
						.filter(x -> UtilsText.equals(claimProduct.getOrgOrderNo(), x.getOrgOrderNo())
								&& UtilsText.equals(String.valueOf(claimProduct.getOrderPrdtSeq()),
										String.valueOf(x.getOrderPrdtSeq())))
						.findFirst().orElse(null);
			}

			BeanUtils.copyProperties(orderProduct, reOrderProduct); // 내용 복사

			reOrderProduct.setOrderNo(reOrderNo); // 신규 생성 주문번호
			reOrderProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_ACCEPT); // 주문상품상태코드:취소접수
			reOrderProduct.setTotalDscntAmt(claimProduct.getTotalDscntAmt()); // 클레임 상품의 조정된 금액으로 다시 set
			reOrderProduct.setOrderAmt(claimProduct.getOrderAmt()); // 클레임 상품의 조정된 금액으로 다시 set
			reOrderProduct.setRgsterNo(ocClaim.getClaimRgsterNo());
			reOrderProduct.setModerNo(ocClaim.getClaimRgsterNo());

			// 신규 리오더 주문상품 생성
			ocOrderProductDao.insertOrderProduct(reOrderProduct);

			// 클레임상품 클레임생성 리오더 주문번호 업데이트
			OcClaimProduct reOrderUpdateClaimProduct = new OcClaimProduct();

			reOrderUpdateClaimProduct.setModerNo(ocClaim.getClaimRgsterNo());
			reOrderUpdateClaimProduct.setClmCrtOrderNo(reOrderNo);
			reOrderUpdateClaimProduct.setClmNo(claimProduct.getClmNo());
			reOrderUpdateClaimProduct.setClmPrdtSeq(claimProduct.getClmPrdtSeq());

			ocClaimProductDao.updateClmCrtOrderNo(reOrderUpdateClaimProduct);
		}

		/*
		 * 다족구매로 인해 재조정 대상 상품이 있는 경우 신규(매출취소 리오더) 등록
		 */
		if (!ObjectUtils.isEmpty(ocClaimAmountVO.getBeforeAdjustOrderProductByMultiBuyList())) {
			/*
			 * 주문 마스터 등록(다족구매 주문금액 변경으로 인한 매출취소)
			 */
			OcOrder multiCancelOrder = new OcOrder();
			BeanUtils.copyProperties(ocOrder, multiCancelOrder); // 내용 복사

			String multiCancelOrderNo = orderService.createOrderSeq(); // 신규 주문번호

			multiCancelOrder.setOrderNo(multiCancelOrderNo); // 신규주문번호
			multiCancelOrder.setClmNo(ocClaim.getClmNo()); // 클레임번호 set
			multiCancelOrder.setTotalNormalAmt(ocClaimAmountVO.getBeforeAdjustOrderProductByMultiBuyList().stream()
					.mapToInt(o -> o.getPrdtNormalAmt()).sum()); // 정상가총액
			multiCancelOrder.setTotalSellAmt(ocClaimAmountVO.getBeforeAdjustOrderProductByMultiBuyList().stream()
					.mapToInt(o -> o.getPrdtSellAmt()).sum()); // 판매가총액
			multiCancelOrder.setTotalPromoDscntAmt(0); // 프로모션할인총액
			multiCancelOrder.setTotalCpnDscntAmt(0); // 쿠폰할인총액
			multiCancelOrder.setTotalEmpDscntAmt(0); // 임직원할인총액
			multiCancelOrder.setPointUseAmt(0); // 포인트사용액
			multiCancelOrder.setEventPointUseAmt(0); // 이벤트포인트사용액
			multiCancelOrder.setMmnyDlvyAmt(0); // 자사배송비
			multiCancelOrder.setTotalVndrDlvyAmt(0); // 입점사배송비총액
			multiCancelOrder.setPymntTodoAmt(ocClaimAmountVO.getBeforeAdjustOrderProductByMultiBuyList().stream()
					.mapToInt(o -> o.getOrderAmt()).sum()); // 결제예정금액
			multiCancelOrder.setPymntAmt(ocClaimAmountVO.getBeforeAdjustOrderProductByMultiBuyList().stream()
					.mapToInt(o -> o.getOrderAmt()).sum()); // 결제금액
			multiCancelOrder.setCnclAmt(0); // 취소금액
			multiCancelOrder.setSalesCnclGbnType(CommonCode.SALES_CNCL_GBN_TYPE_MULTIBUY_CANCEL); // 다족구매 매출취소(매가변경)
			multiCancelOrder.setOrderStatCode(CommonCode.ORDER_STAT_CODE_CANCEL_COMPLETE); // 주문상태코드 : 전체취소완료
			multiCancelOrder.setRgsterNo(ocClaim.getClaimRgsterNo());
			multiCancelOrder.setModerNo(ocClaim.getClaimRgsterNo());

			// 신규 주문(매출 리오더) 생성
			ocOrderDao.insertOrder(multiCancelOrder);

			for (OcOrderProduct cancelMultiBuyOrderProduct : ocClaimAmountVO
					.getBeforeAdjustOrderProductByMultiBuyList()) {
				cancelMultiBuyOrderProduct.setOrderNo(multiCancelOrderNo); // 신규 생성 주문번호
				cancelMultiBuyOrderProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE); // 주문상품상태코드:취소완료
				cancelMultiBuyOrderProduct.setExcclcDcsnYmd(new Timestamp(new Date().getTime())); // 정산확정일자를 넣는다
				cancelMultiBuyOrderProduct.setRgsterNo(ocClaim.getClaimRgsterNo());
				cancelMultiBuyOrderProduct.setModerNo(ocClaim.getClaimRgsterNo());

				// 신규 리오더 주문상품 생성
				ocOrderProductDao.insertOrderProduct(cancelMultiBuyOrderProduct);

				// 원 주문의 주문상품이력 등록(취소완료)
				OcOrderProductHistory orgOrderProductHistory = new OcOrderProductHistory();
				orgOrderProductHistory.setOrderNo(cancelMultiBuyOrderProduct.getOrgOrderNo());
				orgOrderProductHistory.setOrderPrdtSeq(cancelMultiBuyOrderProduct.getOrderPrdtSeq());
				orgOrderProductHistory.setPrdtNo(cancelMultiBuyOrderProduct.getPrdtNo());
				orgOrderProductHistory.setPrdtOptnNo(cancelMultiBuyOrderProduct.getPrdtOptnNo());
				orgOrderProductHistory.setPrdtName(cancelMultiBuyOrderProduct.getPrdtName());
				orgOrderProductHistory.setEngPrdtName(cancelMultiBuyOrderProduct.getEngPrdtName());
				orgOrderProductHistory.setOptnName(cancelMultiBuyOrderProduct.getOptnName());
				orgOrderProductHistory.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE); // 주문상품상태코드:취소완료
				orgOrderProductHistory.setNoteText("다족구매 재조정 취소");
				orgOrderProductHistory.setRgsterNo(ocClaim.getClaimRgsterNo());

				ocOrderProductHistoryDao.insertProductHistory(orgOrderProductHistory); // 주문상품이력 생성
			}
		}

		/*
		 * 다족구매로 인해 재조정 된 대상 상품이 있는 경우 새로운 매출용 주문 리오더 등록
		 */
		if (!ObjectUtils.isEmpty(ocClaimAmountVO.getAdjustedOrderProductByMultiBuyList())) {
			/*
			 * 주문 마스터 등록(다족구매 주문금액 변경으로 인한 매출)
			 */
			OcOrder reSalesOrder = new OcOrder();
			BeanUtils.copyProperties(ocOrder, reSalesOrder); // 내용 복사

			String reSalesOrderNo = orderService.createOrderSeq(); // 신규 주문번호

			reSalesOrder.setOrderNo(reSalesOrderNo); // 신규주문번호
			reSalesOrder.setClmNo(ocClaim.getClmNo()); // 클레임번호 set
			reSalesOrder.setTotalNormalAmt(ocClaimAmountVO.getAdjustedOrderProductByMultiBuyList().stream()
					.mapToInt(o -> o.getPrdtNormalAmt()).sum()); // 정상가총액
			reSalesOrder.setTotalSellAmt(ocClaimAmountVO.getAdjustedOrderProductByMultiBuyList().stream()
					.mapToInt(o -> o.getPrdtSellAmt()).sum()); // 판매가총액
			reSalesOrder.setTotalPromoDscntAmt(0); // 프로모션할인총액
			reSalesOrder.setTotalCpnDscntAmt(0); // 쿠폰할인총액
			reSalesOrder.setTotalEmpDscntAmt(0); // 임직원할인총액
			reSalesOrder.setPointUseAmt(0); // 포인트사용액
			reSalesOrder.setEventPointUseAmt(0); // 이벤트포인트사용액
			reSalesOrder.setMmnyDlvyAmt(0); // 자사배송비
			reSalesOrder.setTotalVndrDlvyAmt(0); // 입점사배송비총액
			reSalesOrder.setPymntTodoAmt(ocClaimAmountVO.getAdjustedOrderProductByMultiBuyList().stream()
					.mapToInt(o -> o.getOrderAmt()).sum()); // 결제예정금액
			reSalesOrder.setPymntAmt(ocClaimAmountVO.getAdjustedOrderProductByMultiBuyList().stream()
					.mapToInt(o -> o.getOrderAmt()).sum()); // 결제금액
			reSalesOrder.setCnclAmt(0); // 취소금액
			reSalesOrder.setSalesCnclGbnType(CommonCode.SALES_CNCL_GBN_TYPE_MULTIBUY_SALE); // 다족구매 매출(매가변경)
			reSalesOrder.setOrderStatCode(CommonCode.ORDER_STAT_CODE_COMPLETE); // 주문상태코드 : 결제완료
			reSalesOrder.setRgsterNo(ocClaim.getClaimRgsterNo());
			reSalesOrder.setModerNo(ocClaim.getClaimRgsterNo());

			// 신규 주문(매출 리오더) 생성
			ocOrderDao.insertOrder(reSalesOrder);

			/*
			 * 다족구매로 인해 재조정 되는 대상 상품이 있는 경우 신규(매출 리오더) 등록
			 */
			for (OcOrderProduct salesMultiBuyOrderProduct : ocClaimAmountVO.getAdjustedOrderProductByMultiBuyList()) {
				salesMultiBuyOrderProduct.setOrderNo(reSalesOrderNo); // 신규 생성 주문번호
				salesMultiBuyOrderProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_COMPLETE); // 주문상품상태코드:결제완료
				salesMultiBuyOrderProduct.setExcclcDcsnYmd(new Timestamp(new Date().getTime())); // 정산확정일자를 넣는다
				salesMultiBuyOrderProduct.setRgsterNo(ocClaim.getClaimRgsterNo());
				salesMultiBuyOrderProduct.setModerNo(ocClaim.getClaimRgsterNo());

				// 신규 리오더 주문상품 생성
				ocOrderProductDao.insertOrderProduct(salesMultiBuyOrderProduct);

				// 원 주문의 주문상품이력 등록(매출기준 결제완료)
				OcOrderProductHistory orgOrderProductHistory = new OcOrderProductHistory();
				orgOrderProductHistory.setOrderNo(salesMultiBuyOrderProduct.getOrgOrderNo());
				orgOrderProductHistory.setOrderPrdtSeq(salesMultiBuyOrderProduct.getOrderPrdtSeq());
				orgOrderProductHistory.setPrdtNo(salesMultiBuyOrderProduct.getPrdtNo());
				orgOrderProductHistory.setPrdtOptnNo(salesMultiBuyOrderProduct.getPrdtOptnNo());
				orgOrderProductHistory.setPrdtName(salesMultiBuyOrderProduct.getPrdtName());
				orgOrderProductHistory.setEngPrdtName(salesMultiBuyOrderProduct.getEngPrdtName());
				orgOrderProductHistory.setOptnName(salesMultiBuyOrderProduct.getOptnName());
				orgOrderProductHistory.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_COMPLETE); // 주문상품상태코드:결제완료
				orgOrderProductHistory.setNoteText("다족구매 재조정 매출");
				orgOrderProductHistory.setRgsterNo(ocClaim.getClaimRgsterNo());

				ocOrderProductHistoryDao.insertProductHistory(orgOrderProductHistory); // 주문상품이력 생성
			}
		}

		/*
		 * 다족구매로 인해 재조정 되는 프로모션 변경 정보 등록
		 */
		if (!ObjectUtils.isEmpty(ocClaimAmountVO.getAdjustedClaimProductApplyPromotionList())) {
			for (OcClaimProductApplyPromotion claimProductApplyPromotion : ocClaimAmountVO
					.getAdjustedClaimProductApplyPromotionList()) {

				// 클레임상품적용프로모션 등록
				ocClaimProductApplyPromotionDao.insert(claimProductApplyPromotion);
			}
		}

		/*
		 * 클레임 배송비 매출 리오더 생성
		 */
		this.registClaimDlvyAmtReOrder(ocClaim, ocOrder);

		/*
		 * 사용 쿠폰 재 발급, 주문 사용쿠폰 클레임번호 업데이트
		 */
		// 사은품, 배송비 제외한 클레임순번 배열
		Object[] claimOrderPrdtSeqs = ocClaimAmountVO.getThisTimeClaimProductList().stream()
				.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
						&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
				.map(OcClaimProduct::getOrderPrdtSeq).toArray();
		;

		// 원주문 사용 쿠폰 목록에서 현재 클레임 대상 주문상품에 해당하는 쿠폰사용 정보 목록(이전 클레임으로 인한 항목 제외)
		List<OcOrderUseCoupon> claimProductUserCouponList = ocClaimAmountVO.getOrderUseCouponList().stream()
				.filter(x -> Arrays.asList(claimOrderPrdtSeqs).contains(x.getOrderPrdtSeq()))
				.filter(x -> UtilsText.equals(x.getClmNo(), null)).collect(Collectors.toList());

		// 원주문 사용 쿠폰 목록에서 현재 클레임 대상 주문상품을 제외한 남은 상품 쿠폰사용 정보 목록(이전 클레임으로 인한 항목 제외)
		List<OcOrderUseCoupon> excludeClaimProductUserCouponList = ocClaimAmountVO.getOrderUseCouponList().stream()
				.filter(x -> !Arrays.asList(claimOrderPrdtSeqs).contains(x.getOrderPrdtSeq()))
				.filter(x -> UtilsText.equals(x.getClmNo(), null)).collect(Collectors.toList());

		// 현재 클레임 대상 중복 쿠폰 제거
		List<Integer> claimHoldCpnSeqs = claimProductUserCouponList.stream().map(OcOrderUseCoupon::getHoldCpnSeq)
				.distinct().collect(Collectors.toList());

		// 재 발급한 쿠폰 목록(체크용)
		List<Integer> reIssueHoldCpnSeq = new ArrayList<Integer>();

		// 주문 전체 취소(반품) 이거나 남은 상품 모두 취소(반품)인지 여부
		if (orgOrderProductCnt == thisTimeClaimCnt || orgOrderProductCnt == finishClaimCnt + thisTimeClaimCnt) {
			// 더블적립 쿠폰 처리(주문에 걸린 쿠폰이므로 상품기준과 다르게 별도 처리)
			if (ocClaimAmountVO.getOrderDoubleDscntCpnInfo() != null) {
				OcOrderUseCoupon orderUseCoupon = new OcOrderUseCoupon();
				orderUseCoupon.setOrderNo(ocClaim.getOrgOrderNo());
				orderUseCoupon.setClmNo(ocClaim.getClmNo());
				orderUseCoupon.setHoldCpnSeq(ocClaimAmountVO.getOrderDoubleDscntCpnInfo().getHoldCpnSeq());

				ocOrderUseCouponDao.updateOrderUseCouponClmNo(orderUseCoupon); // 주문 사용쿠폰 클레임번호 업데이트

				MbMemberCoupon reIssueMemberCoupon = new MbMemberCoupon();
				reIssueMemberCoupon.setMemberNo(ocOrder.getMemberNo());
				reIssueMemberCoupon.setHoldCpnSeq(ocClaimAmountVO.getOrderDoubleDscntCpnInfo().getHoldCpnSeq());
				reIssueMemberCoupon.setReIssueRsnText("주문반품 재 발급");
				reIssueMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_REISSUANCE);
				reIssueMemberCoupon.setRgsterNo(Const.SYSTEM_ADMIN_NO);
				reIssueMemberCoupon.setModerNo(Const.SYSTEM_ADMIN_NO);

				mbMemberCouponDao.insertMemberCouponReIssue(reIssueMemberCoupon); // 쿠폰 재 발급
			}

			// 상품단위에 걸린 holdCpnSeq 기준으로 재 발급
			for (Integer holdCpnSeq : claimHoldCpnSeqs) {
				OcOrderUseCoupon orderUseCoupon = new OcOrderUseCoupon();
				orderUseCoupon.setOrderNo(ocClaim.getOrgOrderNo());
				orderUseCoupon.setClmNo(ocClaim.getClmNo());
				orderUseCoupon.setHoldCpnSeq(holdCpnSeq);

				ocOrderUseCouponDao.updateOrderUseCouponClmNo(orderUseCoupon); // 주문 사용쿠폰 클레임번호 업데이트

				MbMemberCoupon reIssueMemberCoupon = new MbMemberCoupon();
				reIssueMemberCoupon.setMemberNo(ocOrder.getMemberNo());
				reIssueMemberCoupon.setHoldCpnSeq(holdCpnSeq);
				reIssueMemberCoupon.setReIssueRsnText("주문반품 재 발급");
				reIssueMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_REISSUANCE);
				reIssueMemberCoupon.setRgsterNo(Const.SYSTEM_ADMIN_NO);
				reIssueMemberCoupon.setModerNo(Const.SYSTEM_ADMIN_NO);

				mbMemberCouponDao.insertMemberCouponReIssue(reIssueMemberCoupon); // 쿠폰 재 발급
			}
		} else {
			for (OcOrderUseCoupon claimProductUserCoupon : claimProductUserCouponList) {
				// 취소대상 상품에 적용된 쿠폰이 할인유형 쿠폰인 경우
				if (UtilsText.equals(claimProductUserCoupon.getCpnTypeCode(), CommonCode.CPN_TYPE_CODE_DISCOUNT)
						|| UtilsText.equals(claimProductUserCoupon.getCpnTypeCode(),
								CommonCode.CPN_TYPE_CODE_DISCOUNT_AFFILIATES)
						|| UtilsText.equals(claimProductUserCoupon.getCpnTypeCode(),
								CommonCode.CPN_TYPE_CODE_DOUBLE_POINT)) {

					// 취소대상이 아닌 상품에 적용된 동일 할인유형 쿠폰번호 존재 확인
					long existCpnCnt = excludeClaimProductUserCouponList.stream()
							.filter(x -> UtilsText.equals(String.valueOf(claimProductUserCoupon.getHoldCpnSeq()),
									String.valueOf(x.getHoldCpnSeq())))
							.count();

					OcOrderUseCoupon orderUseCoupon = new OcOrderUseCoupon();
					orderUseCoupon.setOrderNo(ocClaim.getOrgOrderNo());
					orderUseCoupon.setClmNo(ocClaim.getClmNo());
					orderUseCoupon.setOrderUseCpnSeq(claimProductUserCoupon.getOrderUseCpnSeq());

					ocOrderUseCouponDao.updateOrderUseCouponClmNo(orderUseCoupon); // 주문 사용쿠폰 클레임번호 업데이트

					// 남아있는 상품에 동일 할인쿠폰이 없는 경우에만 쿠폰 재 발급
					if (existCpnCnt == 0) {
						// 재 발급한 쿠폰 목록에 없다면 재 발급 진행
						if (!reIssueHoldCpnSeq.contains(claimProductUserCoupon.getHoldCpnSeq())) {
							MbMemberCoupon reIssueMemberCoupon = new MbMemberCoupon();
							reIssueMemberCoupon.setMemberNo(ocOrder.getMemberNo());
							reIssueMemberCoupon.setHoldCpnSeq(claimProductUserCoupon.getHoldCpnSeq());
							reIssueMemberCoupon.setReIssueRsnText("주문반품 재 발급");
							reIssueMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_REISSUANCE);
							reIssueMemberCoupon.setRgsterNo(Const.SYSTEM_ADMIN_NO);
							reIssueMemberCoupon.setModerNo(Const.SYSTEM_ADMIN_NO);

							mbMemberCouponDao.insertMemberCouponReIssue(reIssueMemberCoupon); // 쿠폰 재 발급
							reIssueHoldCpnSeq.add(claimProductUserCoupon.getHoldCpnSeq()); // 재 발급 쿠폰 목록에 추가
						}
					}
				}
			}
		}

		/*
		 * 주문상품 프로모션 클레임번호 업데이트
		 */
		for (OcClaimProduct claimProduct : ocClaimAmountVO.getThisTimeClaimProductList()) {
			OcOrderProductApplyPromotion ocOrderProductApplyPromotion = new OcOrderProductApplyPromotion();
			ocOrderProductApplyPromotion.setOrderNo(claimProduct.getOrgOrderNo());
			ocOrderProductApplyPromotion.setClmNo(claimProduct.getClmNo());
			ocOrderProductApplyPromotion.setOrderPrdtSeq(claimProduct.getOrderPrdtSeq());

			ocOrderProductApplyPromotionDao.updateOrderProductApplyPromotionClmNo(ocOrderProductApplyPromotion);
		}

		/*
		 * 임직원 주문인 경우 임직원 한도 복원
		 */
		if (UtilsText.equals(ocOrder.getEmpOrderYn(), Const.BOOLEAN_TRUE)) {
			if (UtilsText.equals(ocOrder.getEmpOrderYn(), Const.BOOLEAN_TRUE)) {
				String orderYearMonth = new SimpleDateFormat(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS)
						.format(ocOrder.getOrderDtm());
				String orderDate = new SimpleDateFormat(Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS)
						.format(ocOrder.getOrderDtm());
				String thisYearMonth = new SimpleDateFormat(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS)
						.format(new Date());

				// 주문월과 해당월이 같을 경우만 한도복원 인터페이스 호출
				if (UtilsText.equals(orderYearMonth, thisYearMonth)) {
					MbEmployeeCertificationHistory employeeInfo = mbEmployeeCertificationHistoryDao
							.selectEmpCertfiHistory(mbMember.getMemberNo());

					EmployPoint returnEmployPointResult = membershipPointService.savePointByEmployNum("1", orderDate,
							employeeInfo.getEmpNoText(), String.valueOf(ocClaimAmountVO.getRefundCnclAmt()),
							Const.SALE_EMP_CD);
				}
			}
		}

		/*
		 * 구매확정 처리
		 */
		// 구매확정 상태가 아닌 경우
		if (ObjectUtils.isEmpty(ocOrder.getBuyDcsnDtm())) {
			// 주문 전체 취소(반품) 이거나 남은 상품 모두 취소(반품)인지 여부
			if (orgOrderProductCnt == thisTimeClaimCnt || orgOrderProductCnt == finishClaimCnt + thisTimeClaimCnt) {
				Map<String, Object> resultOrderConfirmMap = claimProcService.orderConfirmNoTrx(ocOrder);

				if (UtilsText.equals((String) resultOrderConfirmMap.get("resultCode"), Const.BOOLEAN_FALSE)) {
					log.error("반품 클레임 구매확정 처리 실패 claimNo : {}", ocClaim.getClmNo());
				}
			}
		}

		// 2020.03.03 : 주결제가 신용/체크카드이고 주결제수단일때, 잔여 취소금액 재계산하여 set
		// 이전 클레임의 환불금차감 부분반품 완료에서 '반품배송비 결제취소'시, 결제취소 리턴값을 못받아서 환불금재경이 등록된 경우
		// 취소 승인이 난것으로 판단하여 카드 취소가능 잔여금액을 맞춰 KCP에 날린다.
		if (ocClaimAmountVO.getMainPayment() != null) {
			if (UtilsText.equals(ocClaimAmountVO.getMainPayment().getPymntMeansCode(),
					CommonCode.PYMNT_MEANS_CODE_CARD)) {
				OcClaimPayment paramPymnt = new OcClaimPayment();
				paramPymnt.setOrgOrderNo(ocClaim.getOrgOrderNo());
				List<OcClaimPayment> allPublicClaimPaymentList = ocClaimPaymentDao.selectClaimPaymentList(paramPymnt);
				List<OcClaimPayment> mmnyProcTrgtPaymentList = allPublicClaimPaymentList.stream()
						.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
						.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT))
						.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_PUBLIC))
						.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_TRUE))
						.filter(x -> UtilsText.equals(x.getRspnsCodeText(), "9999")).collect(Collectors.toList());
				// "9999" 결제취소 응답을 못받은 코드

				if (!UtilsArray.isEmpty(mmnyProcTrgtPaymentList)) {

					int remainCardPayment = ocClaimAmountVO.getMainPayment().getRealRemainPymntCnclAmt();

					for (OcClaimPayment mmnyProcTrgtPayment : mmnyProcTrgtPaymentList) {
						OcClaim prevClm = new OcClaim();
						prevClm.setClmNo(mmnyProcTrgtPayment.getClmNo());
						prevClm = ocClaimDao.selectByPrimaryKey(prevClm);

						if (UtilsText.equals(prevClm.getAddDlvyAmtPymntType(),
								CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_REFUND_DEDUCTION) && prevClm.getAddDlvyAmt() == 0) {
							remainCardPayment = remainCardPayment - mmnyProcTrgtPayment.getPymntAmt();
						}
					}
					log.error("+++++++++++++++++++++++++ before RealRemainPymntCnclAmt : "
							+ ocClaimAmountVO.getMainPayment().getRealRemainPymntCnclAmt());

					ocClaimAmountVO.getMainPayment().setRealRemainPymntCnclAmt(remainCardPayment);

					log.error("+++++++++++++++++++++++++ after RealRemainPymntCnclAmt : "
							+ ocClaimAmountVO.getMainPayment().getRealRemainPymntCnclAmt());
				}
			}
		}
		/*
		 * 결제취소
		 */
		// 결제취소 진행 시 환불 발생여부(refundOccurrence) ocClaimAmountVO 에 set
		claimProcService.setCancelPayment(ocClaimAmountVO, ocClaim, mbMember);

		/*
		 * 반품 처리 후 처리 주문, 클레임 상태 값 후 처리
		 */
		claimProcService.setClaimReturnAfterProc(ocClaim, ocClaimAmountVO);

		if (ocClaimAmountVO.isRefundOccurrence()) {
			resultMap.put("success", Const.BOOLEAN_FALSE);
			resultMap.put("message", "반품 처리는 완료되었으나\n\n결제취소 실패가 발생하여 환불접수 처리되었습니다.");
			resultMap.put("clmNo", ocClaim.getClmNo());
		} else {
			resultMap.put("success", Const.BOOLEAN_TRUE);
			resultMap.put("message", "반품이 정상적으로 완료되었습니다.");
			resultMap.put("clmNo", ocClaim.getClmNo());
		}

		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("claimNo", ocClaim.getClmNo());

		// 2020.02.11 : 클레임 완료 처리시 SALES 생성 프로시져 호출 / 성공 : "0"
		// String resultSalesClaim = this.setCallProcedureForSalesClaim(paramMap);
		String resultSalesClaim = "0";
		log.debug("resultSalesClaim : {}", resultSalesClaim);
		if (!UtilsText.equals(resultSalesClaim, "0")) {
			throw new Exception("validMsg:반품 클레임 매출 생성에 실패하였습니다.");
		}

		// 메세지, 메일 발송
		try {
			claimMessageService.returnProcessComplete(ocClaim);
		} catch (Exception e) {
			log.error("반품처리완료 메세지 메일 발송 실패 : " + e.getMessage());
		}

		return resultMap;
	}

	/**
	 * @Desc : 클레임 완료 처리시 SALES 생성 프로시져
	 * @Method Name : setCallProcedureForSalesClaim
	 * @Date : 2020. 02. 11.
	 * @Author : 이강수
	 * @param Map<String, String> map
	 * @return
	 */
	public String setCallProcedureForSalesClaim(Map<String, String> map) throws Exception {
		ocClaimDao.callProcedureForSalesClaim(map);
		return map.get("output");
	}

	/**
	 * @Desc : 적립포인트 환수접수 처리
	 * @Method Name : setAcceptClaimRedempPoint
	 * @Date : 2019. 8. 22.
	 * @Author : KTH
	 * @param ocClaim
	 * @param kcpPaymentApproval
	 * @throws Exception
	 */
	public void setAcceptClaimRedempPoint(OcClaim ocClaim, KcpPaymentApproval kcpPaymentApproval) throws Exception {
		/*
		 * 클레임 정보
		 */
		OcClaim claimInfo = ocClaimDao.selectClaimBasisInfo(ocClaim);
		// 클레임 번호 미리 채번
		String clmNo = ocClaim.getClmNo();

		/*
		 * 환수포인트 가상계좌 등록
		 */
		// 클레임결제 set
		OcClaimPayment ocClaimPayment = new OcClaimPayment();
		ocClaimPayment.setClmNo(claimInfo.getClmNo()); // 클레임번호
		ocClaimPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP); // 환수환불구분-환수
		ocClaimPayment.setPymntDtm(null); // 결제일시
		ocClaimPayment.setDeviceCode(claimInfo.getDeviceCode()); // 디바이스코드
		ocClaimPayment.setMainPymntMeansYn(Const.BOOLEAN_TRUE); // 주결제수단여부
		ocClaimPayment.setPymntMeansCode(CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT); // 결제수단코드-가상계좌
		ocClaimPayment.setPymntVndrCode(CommonCode.PYMNT_VNDR_CODE_KCP); // 결제업체코드
		ocClaimPayment.setPymntOrganCodeText(null); // 결제기관코드
		ocClaimPayment.setPymntOrganName(null); // 결제기관명
		ocClaimPayment.setPymntOrganNoText(null); // 결제기관번호(카드번호, 은행계좌번호)
		ocClaimPayment.setIntrstFreeYn(Const.BOOLEAN_FALSE); // 무이자여부
		ocClaimPayment.setInstmtTermCount((short) 0); // 할부기간
		ocClaimPayment.setCardGbnType(null); // 카드구분
		ocClaimPayment.setCardType(null); // 카드유형
		ocClaimPayment.setPymntAprvNoText(null); // 결제승인번호
		ocClaimPayment.setPymntTodoAmt(claimInfo.getRedempSavePoint()); // 결제예정금액
		ocClaimPayment.setPymntAmt(claimInfo.getRedempSavePoint()); // 결제금액
		ocClaimPayment.setCardPartCnclPsbltYn(Const.BOOLEAN_FALSE); // 카드부분취소가능여부
		ocClaimPayment.setCashRcptIssueYn(Const.BOOLEAN_FALSE); // 현금영수증발급여부
		ocClaimPayment.setEscrApplyYn(Const.BOOLEAN_FALSE); // 에스크로적용여부
		ocClaimPayment.setEscrAprvNoText(null); // 에스크로승인번호
		ocClaimPayment.setBankCode(null); // 환불:회원환불계좌, 환수:가상계좌발급
		ocClaimPayment.setAcntNoText(null); // 계좌번호
		ocClaimPayment.setAcntHldrName(null); // 예금주명
		ocClaimPayment.setAcntCrtfcYn(Const.BOOLEAN_FALSE); // 계좌인증여부
		ocClaimPayment.setVrtlAcntIssueDtm(null); // 가상계좌발급일시
		ocClaimPayment.setVrtlAcntExprDtm(null); // 가상계좌만료일시
		ocClaimPayment.setGiftCardPinNoText(null); // 상품권PIN번호
		ocClaimPayment.setRedempRfndMemoText("환수포인트"); // 환수환불메모
		ocClaimPayment.setProcImpsbltYn(Const.BOOLEAN_FALSE); // 처리불가여부
		ocClaimPayment.setProcImpsbltRsnText(null); // 처리불가사유
		ocClaimPayment.setProcImpsbltCmlptDtm(null); // 처리불가완료일시
		ocClaimPayment.setRedempRfndOpetrNo(null); // 환수환불처리자번호
		ocClaimPayment.setRedempRfndOpetrDtm(null); // 환수환불처리일시
		ocClaimPayment.setMmnyProcTrgtYn(Const.BOOLEAN_TRUE); // 자사처리대상여부:Y
		ocClaimPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_POINT); // 발생사유코드 - 포인트
		ocClaimPayment.setRedempAmtRndmProcYn(null); // 환수금임의처리여부
		ocClaimPayment.setPymntStatCode(null); // 결제상태코드
		ocClaimPayment.setRspnsCodeText(null); // 응답코드
		ocClaimPayment.setRspnsMesgText(null); // 응답메시지
		ocClaimPayment.setPymntLogInfo(null); // 결제로그
		ocClaimPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_PUBLIC); // 이력구분 - 일반결제
		ocClaimPayment.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자
		ocClaimPayment.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자

		if (ocClaim.getRedempSavePoint() > 0) {
			if (UtilsText.equals(ocClaim.getRedempAmtRndmProcYn(), Const.BOOLEAN_FALSE)) {
				ocClaimPayment.setRedempAmtRndmProcYn(Const.BOOLEAN_FALSE); // 환수금임의처리여부
				ocClaimPaymentDao.insertClaimPayment(ocClaimPayment); // 클레임결제 등록

				/*
				 * [환수포인트 결제(KCP)] 가상계좌 결제 선택 시 KCP 가상계좌 요청, 가상계좌 요청 후 리턴결과를 이용하여 결제내역 업데이트
				 */
				// site no 에 따른 siteKey 구분
				if (UtilsText.equals(claimInfo.getSiteNo(), Const.SITE_NO_ART)) {
					kcpPaymentApproval.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
				} else {
					kcpPaymentApproval.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
				}

				PaymentResult paymentResult = paymentEntrance
						.approval(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, kcpPaymentApproval)); // KCP 결제

				// 결제 실패인 경우 exception
				if (UtilsText.equals(paymentResult.getSuccessYn(), Const.BOOLEAN_FALSE)) {
					throw new Exception("validMsg:".concat(paymentResult.getMessage()));
				}

				KcpPaymentApprovalReturn kcpPaymentApprovalReturn = ((KcpPaymentApprovalReturn) paymentResult
						.getData());

				List<SyCodeDetail> bankList = commonCodeService.getCodeNoName(CommonCode.BANK_CODE); // 은행코드 목록

				// kcp 리턴 bankcode를 이용하여 공통코드의 bankcode 를 추출
				String commonBankCode = bankList.stream()
						.filter(x -> UtilsText.equals(x.getAddInfo1(), kcpPaymentApprovalReturn.getBankCode()))
						.map(SyCodeDetail::getCodeDtlNo).findFirst().orElse(null);

				// kcp 리턴 가상계좌발급일, 만료일 string 을 기준으로 날짜 데이터 생성
				SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DEFAULT_DATETIME_PATTERN_NOT_CHARACTERS);
				Date acntIssueDate = dateFormat.parse(kcpPaymentApprovalReturn.getAppTime());
				Date acntExprDate = dateFormat.parse(kcpPaymentApprovalReturn.getVaDate());

				OcClaimPayment virtualPayment = new OcClaimPayment();
				virtualPayment.setPymntOrganCodeText(kcpPaymentApprovalReturn.getBankCode()); // 결제기관코드(카드, 은행코드...)
				virtualPayment.setPymntOrganName(kcpPaymentApprovalReturn.getBankName()); // 결제기관명(카드, 은행명...)
				virtualPayment.setPymntOrganNoText(kcpPaymentApprovalReturn.getAccount()); // 결제기관번호(카드번호,
																							// 은행계좌번호...)
				virtualPayment.setPymntAprvNoText(kcpPaymentApprovalReturn.getTno()); // 결제승인번호
				virtualPayment.setBankCode(commonBankCode); // 환불:회원환불계좌, 환수:가상계좌발급
				virtualPayment.setAcntNoText(kcpPaymentApprovalReturn.getAccount()); // 계좌번호
				virtualPayment.setAcntHldrName(kcpPaymentApprovalReturn.getDepositor()); // 예금주명
				virtualPayment.setVrtlAcntIssueDtm(new Timestamp(acntIssueDate.getTime())); // 가상계좌발급일시
				virtualPayment.setVrtlAcntExprDtm(new Timestamp(acntExprDate.getTime())); // 가상계좌만료일시
				virtualPayment.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REDEMP_ACCEPT); // 결제상태코드
				virtualPayment.setRspnsCodeText(kcpPaymentApprovalReturn.getResCd()); // 응답코드
				virtualPayment.setRspnsMesgText(kcpPaymentApprovalReturn.getResMsg()); // 응답메시지
				virtualPayment.setPymntLogInfo(new ObjectMapper().writeValueAsString(kcpPaymentApprovalReturn)); // 결제로그
				virtualPayment.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자
				virtualPayment.setClmNo(ocClaim.getClmNo());
				virtualPayment.setClmPymntSeq(ocClaimPayment.getClmPymntSeq());

				try {
					// 가상계좌요청 시 클레임결제 수정(kcp 리턴 값 사용)
					ocClaimPaymentDao.updateClaimPaymentRequestVirtualAccount(virtualPayment);
				} catch (Exception e) {
					// 클레임결제 수정이 실패할 경우 kcp 가상계좌 요청 취소
					KcpPaymentCancel kcpPaymentCancel = new KcpPaymentCancel();

					// site no 에 따른 siteCd, siteKey 구분
					if (UtilsText.equals(claimInfo.getSiteNo(), Const.SITE_NO_ART)) {
						kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.claim.siteCode"));
						kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
					} else {
						kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.ots.claim.siteCode"));
						kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
					}

					kcpPaymentCancel.setTno(kcpPaymentApprovalReturn.getTno()); // KCP 원거래 거래번호
					kcpPaymentCancel.setModType(CommonCode.KCP_MOD_TYPE_STSC); // 전체취소 STSC / 부분취소 STPC
					kcpPaymentCancel.setCustIp(kcpPaymentApproval.getCustIp()); // 변경 요청자 IP
					kcpPaymentCancel.setModDesc("가맹점 처리 실패"); // 취소 사유

					// 가상계좌 취소(가상계좌 사용중지-환불아님)
					paymentEntrance.cancel(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, kcpPaymentCancel));

					throw new Exception("validMsg:결제 에러 발생");
				}
			} else {
				ocClaimPayment.setRedempAmtRndmProcYn(Const.BOOLEAN_TRUE); // 환수금임의처리여부
				ocClaimPaymentDao.insertClaimPayment(ocClaimPayment); // 클레임결제 등록
			}
		}

		// 이메일 , 카톡 문자 알림 서비스 시작
		try {
			OcClaim msgParam = new OcClaim();
			msgParam.setClmNo(clmNo);

			claimMessageService.redempAmtOccurrence(ocClaim);
		} catch (Exception e) {
			log.error("환수포인트비용결제요청 메세지 메일 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 클레임 배송비 매출 리오더 등록 : 교환/반품 해당
	 * @Method Name : registClaimDlvyAmtReOrder
	 * @Date : 2019. 8. 3.
	 * @Author : KTH
	 * @param ocClaim
	 * @param sourceOrder
	 * @throws Exception
	 */
	public void registClaimDlvyAmtReOrder(OcClaim ocClaim, OcOrder sourceOrder) throws Exception {

		// 클레임 배송비 결제 형태가 무료, 배송비무료 쿠폰은 처리 없이 종료
		if (UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(), CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON)
				|| UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(), CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE)) {

			return;
		}

		/*
		 * [클레임 결제 정보]
		 */
		OcClaimPayment ocClaimPayment = new OcClaimPayment();
		ocClaimPayment.setClmNo(ocClaim.getClmNo()); // 클레임번호

		List<OcClaimPayment> claimPaymentList = ocClaimPaymentDao.selectClaimPaymentList(ocClaimPayment);

		/*
		 * 클레임 배송비 체크(선결제는 일반결제, 환불금차감은 히스토리로 payment 생성되어있음)
		 */
		if (UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(), CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT)) {
			// 클레임 배송비 payment
			OcClaimPayment claimDlvyPymnt = claimPaymentList.stream()
					.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP))
					.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
					.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT))
					.findFirst().orElse(null);
			;

			// 결제정보가 없으면 종료
			if (claimDlvyPymnt == null) {
				return;
			}

			// 결제완료 상태가 아니면 처리 종료
			if (!UtilsText.equals(claimDlvyPymnt.getPymntStatCode(), CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH)) {
				return;
			}
		}

		/*
		 * 원 주문상품 목록
		 */
		OcOrderProduct ocOrderProduct = new OcOrderProduct();
		ocOrderProduct.setOrderNo(ocClaim.getOrgOrderNo()); // 원 주문번호로 조회

		List<OcOrderProduct> orderProductList = ocOrderProductDao.selectOrderProductList(ocOrderProduct);

		/*
		 * 클레임 상품 목록
		 */
		OcClaimProduct ocClaimProduct = new OcClaimProduct();
		ocClaimProduct.setClmNo(ocClaim.getClmNo());
		ocClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 상품 코드
		ocClaimProduct.setPrdtRltnFileSeq(1); // 상품관련파일순번 : 1 (대표)

		List<OcClaimProduct> claimProductList = ocClaimProductDao.selectClaimProductList(ocClaimProduct);

		/*
		 * [신규 주문 생성]
		 */
		OcOrder reOrder = new OcOrder();
		BeanUtils.copyProperties(sourceOrder, reOrder); // 내용 복사

		String reOrderNo = orderService.createOrderSeq(); // 신규 주문번호

		reOrder.setOrderNo(reOrderNo); // 신규주문번호
		reOrder.setClmNo(ocClaim.getClmNo()); // 클레임번호 set
		reOrder.setTotalNormalAmt(ocClaim.getAddDlvyAmt()); // 정상가총액
		reOrder.setTotalSellAmt(ocClaim.getAddDlvyAmt()); // 판매가총액
		reOrder.setTotalPromoDscntAmt(0); // 프로모션할인총액
		reOrder.setTotalCpnDscntAmt(0); // 쿠폰할인총액
		reOrder.setTotalEmpDscntAmt(0); // 임직원할인총액
		reOrder.setPointUseAmt(0); // 포인트사용액
		reOrder.setEventPointUseAmt(0); // 이벤트포인트사용액
		reOrder.setMmnyDlvyAmt(0); // 자사배송비
		reOrder.setTotalVndrDlvyAmt(0); // 입점사배송비총액
		reOrder.setPymntTodoAmt(ocClaim.getAddDlvyAmt()); // 결제예정금액
		reOrder.setPymntAmt(ocClaim.getAddDlvyAmt()); // 결제금액
		reOrder.setCnclAmt(0); // 취소금액
		reOrder.setSalesCnclGbnType(CommonCode.SALES_CNCL_GBN_TYPE_CLAIM_DLVY_AMT); // 매출취소구분 - 클레임배송비
		reOrder.setOrderStatCode(CommonCode.ORDER_STAT_CODE_COMPLETE); // 주문상태 - 결제완료
		reOrder.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
		reOrder.setModerNo(LoginManager.getUserDetails().getAdminNo());

		ocOrderDao.insertOrder(reOrder); // 신규 주문 생성

		/*
		 * [신규 주문상품 생성]
		 */
		OcOrderProduct newOrderProduct = new OcOrderProduct(); // 임의의 한개 상품

		newOrderProduct.setOrderNo(reOrderNo); // 새롭게 생성된 주문 번호로 set
		newOrderProduct.setOrderPrdtSeq(orderProductList.size() + 1); // 기존 주문상품 기준으로 +1
		newOrderProduct.setUpOrderPrdtSeq(null);
		newOrderProduct.setPrdtNo("1900000001"); // 배송비상품번호 임의 set
		newOrderProduct.setPrdtOptnNo("001"); // 배송비옵션번호 임의 set
		newOrderProduct.setPrdtName("클레임배송비");
		newOrderProduct.setEngPrdtName("Delivery charge");
		newOrderProduct.setOptnName("클레임배송비");
		newOrderProduct.setPrdtTypeCode(CommonCode.PRDT_TYPE_CODE_DELIVERY);
		newOrderProduct.setStyleInfo("DELIVERY_CHARGE");
		newOrderProduct.setRsvPrdtYn(Const.BOOLEAN_FALSE);
		newOrderProduct.setVndrNo(claimProductList.get(0).getVndrNo());
		newOrderProduct.setVndrName(claimProductList.get(0).getVndrName());
		newOrderProduct.setChnnlNo(claimProductList.get(0).getChnnlNo());
		newOrderProduct.setMmnyPrdtYn(claimProductList.get(0).getMmnyPrdtYn());
		newOrderProduct.setEmpDscntYn(Const.BOOLEAN_FALSE);
		newOrderProduct.setOrderMnfctYn(Const.BOOLEAN_FALSE);
		newOrderProduct.setDprcExceptYn(Const.BOOLEAN_FALSE);
		newOrderProduct.setFreeDlvyYn(Const.BOOLEAN_FALSE);
		newOrderProduct.setOrderQty(1);
		newOrderProduct.setPrdtNormalAmt(ocClaim.getAddDlvyAmt());
		newOrderProduct.setPrdtSellAmt(ocClaim.getAddDlvyAmt());
		newOrderProduct.setOptnAddAmt(0);
		newOrderProduct.setSellAmt(ocClaim.getAddDlvyAmt());
		newOrderProduct.setEmpDscntRate((short) 0);
		newOrderProduct.setEmpAmt(0);
		newOrderProduct.setTotalDscntAmt(0);
		newOrderProduct.setCpnDscntAmt(0);
		newOrderProduct.setOrderAmt(ocClaim.getAddDlvyAmt());
		newOrderProduct.setVndrCmsnRate((short) 0);
		newOrderProduct.setSellCnclReqYn(Const.BOOLEAN_FALSE);
		newOrderProduct.setLogisCnvrtYn(Const.BOOLEAN_FALSE);
		newOrderProduct.setPickupExtsnYn(Const.BOOLEAN_FALSE);
		newOrderProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_COMPLETE); // 주문상품상태코드 - 결제완료
		newOrderProduct.setGenderGbnCode(CommonCode.GENDER_GBN_CODE_COMMON);
		newOrderProduct.setBuyPointSaveRate((short) 0);
		newOrderProduct.setExcclcDcsnYmd(new Timestamp(new Date().getTime())); // 정산확정일자를 넣는다
		newOrderProduct.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
		newOrderProduct.setModerNo(LoginManager.getUserDetails().getAdminNo());

		ocOrderProductDao.insertOrderProduct(newOrderProduct); // 신규 주문상품 생성
	}

	/**
	 * @Desc : 배송ID 생성
	 * @Method Name : getDeliveryId
	 * @Date : 2019. 7. 26.
	 * @Author : KTH
	 * @param _orderNo
	 * @param _prdtSeq
	 * @param _deliverySeq
	 * @return
	 * @throws Exception
	 */
	public String getDeliveryId(String _orderNo, Short _prdtSeq, short _deliverySeq) throws Exception {
		return _orderNo + String.format("%02d", _prdtSeq) + String.format("%02d", _deliverySeq);
	}

	/**
	 * @Desc : 환수환불 접수 처리
	 * @Method Name : setAcceptClaimRedempRefund
	 * @Date : 2019. 4. 5.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocClaimProduct
	 * @throws Exception
	 */
	public void setAcceptClaimRedempRefund(OcClaim ocClaim, OcClaimProduct ocClaimProduct) throws Exception {
		// 클레임 정보
		OcClaim sourceClaim = ocClaimDao.selectClaimBasisInfo(ocClaim);

		// 클레임상품 정보
		ocClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 상품 코드
		List<OcClaimProduct> sourceClaimProducts = ocClaimProductDao.selectClaimProductList(ocClaimProduct);

		/*
		 * 클레임/클레임상품 상태 변경 처리 및 상태변경이력 등록
		 */
		// 클레임(상품) 상태 코드 - 환불접수
		this.setClaimAndClaimPrdtStatChangeProc(sourceClaim, sourceClaimProducts,
				CommonCode.CLM_STAT_CODE_EXCHANGE_REFUND_ACCEPT, CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_REFUND_ACCEPT);
	}

	/**
	 * @Desc : 클레임/클레임상품 상태 변경 처리 및 상태변경이력 등록
	 * @Method Name : setClaimStatChangeProc
	 * @Date : 2019. 4. 5.
	 * @Author : KTH
	 * @param oclaim
	 * @param claimProducts
	 * @throws Exception
	 */
	public void setClaimAndClaimPrdtStatChangeProc(OcClaim oclaim, List<OcClaimProduct> claimProducts,
			String clmStatCode, String clmPrdtStatCode) throws Exception {
		/*
		 * [클레임상품 상태 변경] [클레임상태이력 등록]
		 */
		for (OcClaimProduct claimProduct : claimProducts) {
			claimProduct.setModerNo(LoginManager.getUserDetails().getAdminNo());
			claimProduct.setClmPrdtStatCode(clmPrdtStatCode); // 클레임상품 상태 코드

			ocClaimProductDao.updateClaimProductStatCode(claimProduct); // 클레임상품 상태 변경

			OcClaimStatusHistory ocClaimStatusHistory = new OcClaimStatusHistory();
			ocClaimStatusHistory.setClmNo(claimProduct.getClmNo());
			ocClaimStatusHistory.setClmPrdtSeq(claimProduct.getClmPrdtSeq());
			ocClaimStatusHistory.setClmPrdtStatCode(clmPrdtStatCode); // 클레임상품 상태 코드
			ocClaimStatusHistory.setStockGbnCode(null);
			ocClaimStatusHistory.setNoteText(null);
			ocClaimStatusHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자

			ocClaimStatusHistoryDao.insertClaimStatusHistory(ocClaimStatusHistory); // 클레임상태이력 등록
		}

		/*
		 * [클레임 상태 변경]
		 */
		oclaim.setModerNo(LoginManager.getUserDetails().getAdminNo());
		oclaim.setClmStatCode(clmStatCode); // 클레임 상태 코드
		ocClaimDao.updateClaimStatCode(oclaim);
	}

	/**
	 * @Desc : 환수포인트 가상계좌 재발급
	 * @Method Name : setClaimDlvyVirtualAccountPointAgain
	 * @Date : 2019. 8. 22.
	 * @Author : KTH
	 * @param ocClaimPayment
	 * @param kcpPaymentApproval
	 * @throws Exception
	 */
	public void setClaimDlvyVirtualAccountPointAgain(OcClaimPayment ocClaimPayment,
			KcpPaymentApproval kcpPaymentApproval) throws Exception {

		// 클레임 정보
		OcClaim ocClaim = new OcClaim();
		ocClaim.setClmNo(ocClaimPayment.getClmNo());
		OcClaim claimInfo = ocClaimDao.selectClaimBasisInfo(ocClaim);

		ocClaimPayment.setClmPymntSeq(null); // 클레임번호 대상 조회를 위해 null set
		List<OcClaimPayment> claimPaymentList = ocClaimPaymentDao.selectClaimPaymentList(ocClaimPayment);

		ocClaimPayment = claimPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_POINT)).findFirst()
				.orElse(null);

		// 클레임결제 수정이 실패할 경우 kcp 가상계좌 요청 취소
		KcpPaymentCancel virtualCancel = new KcpPaymentCancel();

		// site no 에 따른 siteCd, siteKey 구분
		if (UtilsText.equals(claimInfo.getSiteNo(), Const.SITE_NO_ART)) {
			virtualCancel.setSiteCd(Config.getString("pg.kcp.claim.siteCode"));
			virtualCancel.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
		} else {
			virtualCancel.setSiteCd(Config.getString("pg.kcp.ots.claim.siteCode"));
			virtualCancel.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
		}

		virtualCancel.setTno(ocClaimPayment.getPymntAprvNoText()); // KCP 원거래 거래번호
		virtualCancel.setModType(CommonCode.KCP_MOD_TYPE_STSC); // 전체취소 STSC / 부분취소 STPC
		virtualCancel.setCustIp(kcpPaymentApproval.getCustIp()); // 변경 요청자 IP
		virtualCancel.setModDesc("가맹점 가상계좌 사용중지"); // 취소 사유

		// 가상계좌 취소(가상계좌 사용중지-환불아님)
		PaymentResult cancelResult = paymentEntrance
				.cancel(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, virtualCancel));

		// 취소 실패인 경우 exception
		if (UtilsText.equals(cancelResult.getSuccessYn(), Const.BOOLEAN_FALSE)) {
			if (UtilsText.equals(((KcpPaymentCancelReturn) cancelResult.getData()).getResCd(), "8332")) {
				// KCP 8332 이미 사용중지된 가상계좌 사용중지요청 - skip
			} else {
				throw new Exception("validMsg:".concat(cancelResult.getMessage()));
			}
		}

		/*
		 * [클레임배송비 결제(KCP)] 가상계좌 결제 선택 시 KCP 가상계좌 요청, 가상계좌 요청 후 리턴결과를 이용하여 결제내역 업데이트
		 */
		// site no 에 따른 siteKey 구분
		if (UtilsText.equals(claimInfo.getSiteNo(), Const.SITE_NO_ART)) {
			kcpPaymentApproval.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
		} else {
			kcpPaymentApproval.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
		}

		PaymentResult paymentResult = paymentEntrance
				.approval(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, kcpPaymentApproval)); // KCP 결제

		// 결제 실패인 경우 exception
		if (UtilsText.equals(paymentResult.getSuccessYn(), Const.BOOLEAN_FALSE)) {
			throw new Exception("validMsg:".concat(paymentResult.getMessage()));
		}

		KcpPaymentApprovalReturn kcpPaymentApprovalReturn = ((KcpPaymentApprovalReturn) paymentResult.getData());

		List<SyCodeDetail> bankList = commonCodeService.getCodeNoName(CommonCode.BANK_CODE); // 은행코드 목록

		// 2020.03.23 : 신한은행의 경우 가상계좌발급시 은행코드는 "BK26"이므로 실시간계좌이체 "BK88"로 bankCode 조회
		if (UtilsText.equals(kcpPaymentApprovalReturn.getBankCode(), "BK26")) {
			kcpPaymentApprovalReturn.setBankCode("BK88");
		}

		// kcp 리턴 bankcode를 이용하여 공통코드의 bankcode 를 추출
		String commonBankCode = bankList.stream()
				.filter(x -> UtilsText.equals(x.getAddInfo1(), kcpPaymentApprovalReturn.getBankCode()))
				.map(SyCodeDetail::getCodeDtlNo).findFirst().orElse(null);

		// kcp 리턴 가상계좌발급일, 만료일 string 을 기준으로 날짜 데이터 생성
		SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DEFAULT_DATETIME_PATTERN_NOT_CHARACTERS);
		Date acntIssueDate = dateFormat.parse(kcpPaymentApprovalReturn.getAppTime());
		Date acntExprDate = dateFormat.parse(kcpPaymentApprovalReturn.getVaDate());

		OcClaimPayment virtualPayment = new OcClaimPayment();
		virtualPayment.setPymntOrganCodeText(kcpPaymentApprovalReturn.getBankCode()); // 결제기관코드(카드, 은행코드...)
		virtualPayment.setPymntOrganName(kcpPaymentApprovalReturn.getBankName()); // 결제기관명(카드, 은행명...)
		virtualPayment.setPymntOrganNoText(kcpPaymentApprovalReturn.getAccount()); // 결제기관번호(카드번호, 은행계좌번호...)
		virtualPayment.setPymntAprvNoText(kcpPaymentApprovalReturn.getTno()); // 결제승인번호
		virtualPayment.setBankCode(commonBankCode); // 환불:회원환불계좌, 환수:가상계좌발급
		virtualPayment.setAcntNoText(kcpPaymentApprovalReturn.getAccount()); // 계좌번호
		virtualPayment.setAcntHldrName(kcpPaymentApprovalReturn.getDepositor()); // 예금주명
		virtualPayment.setVrtlAcntIssueDtm(new Timestamp(acntIssueDate.getTime())); // 가상계좌발급일시
		virtualPayment.setVrtlAcntExprDtm(new Timestamp(acntExprDate.getTime())); // 가상계좌만료일시
		virtualPayment.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REDEMP_ACCEPT); // 결제상태코드
		virtualPayment.setRspnsCodeText(kcpPaymentApprovalReturn.getResCd()); // 응답코드
		virtualPayment.setRspnsMesgText(kcpPaymentApprovalReturn.getResMsg()); // 응답메시지
		virtualPayment.setPymntLogInfo(new ObjectMapper().writeValueAsString(kcpPaymentApprovalReturn)); // 결제로그
		virtualPayment.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자
		virtualPayment.setClmNo(ocClaimPayment.getClmNo());
		virtualPayment.setClmPymntSeq(ocClaimPayment.getClmPymntSeq());

		try {
			// 가상계좌요청 시 클레임결제 수정(kcp 리턴 값 사용)
			ocClaimPaymentDao.updateClaimPaymentRequestVirtualAccount(virtualPayment);
		} catch (Exception e) {
			// 클레임결제 수정이 실패할 경우 kcp 가상계좌 요청 취소
			KcpPaymentCancel kcpPaymentCancel = new KcpPaymentCancel();

			// site no 에 따른 siteCd, siteKey 구분
			if (UtilsText.equals(claimInfo.getSiteNo(), Const.SITE_NO_ART)) {
				kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.claim.siteCode"));
				kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
			} else {
				kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.ots.claim.siteCode"));
				kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
			}

			kcpPaymentCancel.setTno(kcpPaymentApprovalReturn.getTno()); // KCP 원거래 거래번호
			kcpPaymentCancel.setModType(CommonCode.KCP_MOD_TYPE_STSC); // 전체취소 STSC / 부분취소 STPC
			kcpPaymentCancel.setCustIp(kcpPaymentApproval.getCustIp()); // 변경 요청자 IP
			kcpPaymentCancel.setModDesc("가맹점 처리 실패"); // 취소 사유

			// 가상계좌 취소(가상계좌 사용중지-환불아님)
			paymentEntrance.cancel(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, kcpPaymentCancel));

			throw new Exception("validMsg:결제 에러 발생");
		}
	}

	/**
	 * @Desc : 배송비 가상계좌 재발급
	 * @Method Name : setClaimDlvyVirtualAccountAgain
	 * @Date : 2019. 4. 3.
	 * @Author : KTH
	 * @param ocClaimPayment
	 * @param kcpPaymentApproval
	 * @throws Exception
	 */
	public void setClaimDlvyVirtualAccountAgain(OcClaimPayment ocClaimPayment, KcpPaymentApproval kcpPaymentApproval)
			throws Exception {

		// 클레임 정보
		OcClaim ocClaim = new OcClaim();
		ocClaim.setClmNo(ocClaimPayment.getClmNo());
		OcClaim claimInfo = ocClaimDao.selectClaimBasisInfo(ocClaim);

		ocClaimPayment.setClmPymntSeq(null); // 클레임번호 대상 조회를 위해 null set
		List<OcClaimPayment> claimPaymentList = ocClaimPaymentDao.selectClaimPaymentList(ocClaimPayment);

		ocClaimPayment = claimPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT)).findFirst()
				.orElse(null);

		// 클레임결제 수정이 실패할 경우 kcp 가상계좌 요청 취소
		KcpPaymentCancel virtualCancel = new KcpPaymentCancel();

		// site no 에 따른 siteCd, siteKey 구분
		if (UtilsText.equals(claimInfo.getSiteNo(), Const.SITE_NO_ART)) {
			virtualCancel.setSiteCd(Config.getString("pg.kcp.claim.siteCode"));
			virtualCancel.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
		} else {
			virtualCancel.setSiteCd(Config.getString("pg.kcp.ots.claim.siteCode"));
			virtualCancel.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
		}

		virtualCancel.setTno(ocClaimPayment.getPymntAprvNoText()); // KCP 원거래 거래번호
		virtualCancel.setModType(CommonCode.KCP_MOD_TYPE_STSC); // 전체취소 STSC / 부분취소 STPC
		virtualCancel.setCustIp(kcpPaymentApproval.getCustIp()); // 변경 요청자 IP
		virtualCancel.setModDesc("가맹점 가상계좌 사용중지"); // 취소 사유

		// 가상계좌 취소(가상계좌 사용중지-환불아님)
		PaymentResult cancelResult = paymentEntrance
				.cancel(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, virtualCancel));

		// 취소 실패인 경우 exception
		if (UtilsText.equals(cancelResult.getSuccessYn(), Const.BOOLEAN_FALSE)) {
			if (UtilsText.equals(((KcpPaymentCancelReturn) cancelResult.getData()).getResCd(), "8332")) {
				// KCP 8332 이미 사용중지된 가상계좌 사용중지요청 - skip
			} else {
				throw new Exception("validMsg:".concat(cancelResult.getMessage()));
			}
		}

		/*
		 * [클레임배송비 결제(KCP)] 가상계좌 결제 선택 시 KCP 가상계좌 요청, 가상계좌 요청 후 리턴결과를 이용하여 결제내역 업데이트
		 */
		// site no 에 따른 siteKey 구분
		if (UtilsText.equals(claimInfo.getSiteNo(), Const.SITE_NO_ART)) {
			kcpPaymentApproval.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
		} else {
			kcpPaymentApproval.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
		}

		PaymentResult paymentResult = paymentEntrance
				.approval(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, kcpPaymentApproval)); // KCP 결제

		// 결제 실패인 경우 exception
		if (UtilsText.equals(paymentResult.getSuccessYn(), Const.BOOLEAN_FALSE)) {
			throw new Exception("validMsg:".concat(paymentResult.getMessage()));
		}

		KcpPaymentApprovalReturn kcpPaymentApprovalReturn = ((KcpPaymentApprovalReturn) paymentResult.getData());

		List<SyCodeDetail> bankList = commonCodeService.getCodeNoName(CommonCode.BANK_CODE); // 은행코드 목록

		// 2020.03.23 : 신한은행의 경우 가상계좌발급시 은행코드는 "BK26"이므로 실시간계좌이체 "BK88"로 bankCode 조회
		if (UtilsText.equals(kcpPaymentApprovalReturn.getBankCode(), "BK26")) {
			kcpPaymentApprovalReturn.setBankCode("BK88");
		}

		// kcp 리턴 bankcode를 이용하여 공통코드의 bankcode 를 추출
		String commonBankCode = bankList.stream()
				.filter(x -> UtilsText.equals(x.getAddInfo1(), kcpPaymentApprovalReturn.getBankCode()))
				.map(SyCodeDetail::getCodeDtlNo).findFirst().orElse(null);

		// kcp 리턴 가상계좌발급일, 만료일 string 을 기준으로 날짜 데이터 생성
		SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DEFAULT_DATETIME_PATTERN_NOT_CHARACTERS);
		Date acntIssueDate = dateFormat.parse(kcpPaymentApprovalReturn.getAppTime());
		Date acntExprDate = dateFormat.parse(kcpPaymentApprovalReturn.getVaDate());

		OcClaimPayment virtualPayment = new OcClaimPayment();
		virtualPayment.setPymntOrganCodeText(kcpPaymentApprovalReturn.getBankCode()); // 결제기관코드(카드, 은행코드...)
		virtualPayment.setPymntOrganName(kcpPaymentApprovalReturn.getBankName()); // 결제기관명(카드, 은행명...)
		virtualPayment.setPymntOrganNoText(kcpPaymentApprovalReturn.getAccount()); // 결제기관번호(카드번호, 은행계좌번호...)
		virtualPayment.setPymntAprvNoText(kcpPaymentApprovalReturn.getTno()); // 결제승인번호
		virtualPayment.setBankCode(commonBankCode); // 환불:회원환불계좌, 환수:가상계좌발급
		virtualPayment.setAcntNoText(kcpPaymentApprovalReturn.getAccount()); // 계좌번호
		virtualPayment.setAcntHldrName(kcpPaymentApprovalReturn.getDepositor()); // 예금주명
		virtualPayment.setVrtlAcntIssueDtm(new Timestamp(acntIssueDate.getTime())); // 가상계좌발급일시
		virtualPayment.setVrtlAcntExprDtm(new Timestamp(acntExprDate.getTime())); // 가상계좌만료일시
		virtualPayment.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_WAIT_DEPOSIT); // 결제상태코드
		virtualPayment.setRspnsCodeText(kcpPaymentApprovalReturn.getResCd()); // 응답코드
		virtualPayment.setRspnsMesgText(kcpPaymentApprovalReturn.getResMsg()); // 응답메시지
		virtualPayment.setPymntLogInfo(new ObjectMapper().writeValueAsString(kcpPaymentApprovalReturn)); // 결제로그
		virtualPayment.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자
		virtualPayment.setClmNo(ocClaimPayment.getClmNo());
		virtualPayment.setClmPymntSeq(ocClaimPayment.getClmPymntSeq());

		try {
			// 가상계좌요청 시 클레임결제 수정(kcp 리턴 값 사용)
			ocClaimPaymentDao.updateClaimPaymentRequestVirtualAccount(virtualPayment);
		} catch (Exception e) {
			// 클레임결제 수정이 실패할 경우 kcp 가상계좌 요청 취소
			KcpPaymentCancel kcpPaymentCancel = new KcpPaymentCancel();

			// site no 에 따른 siteCd, siteKey 구분
			if (UtilsText.equals(claimInfo.getSiteNo(), Const.SITE_NO_ART)) {
				kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.claim.siteCode"));
				kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
			} else {
				kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.ots.claim.siteCode"));
				kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
			}

			kcpPaymentCancel.setTno(kcpPaymentApprovalReturn.getTno()); // KCP 원거래 거래번호
			kcpPaymentCancel.setModType(CommonCode.KCP_MOD_TYPE_STSC); // 전체취소 STSC / 부분취소 STPC
			kcpPaymentCancel.setCustIp(kcpPaymentApproval.getCustIp()); // 변경 요청자 IP
			kcpPaymentCancel.setModDesc("가맹점 처리 실패"); // 취소 사유

			// 가상계좌 취소(가상계좌 사용중지-환불아님)
			paymentEntrance.cancel(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, kcpPaymentCancel));

			throw new Exception("validMsg:결제 에러 발생");
		}
	}

	/**
	 * @Desc : 클레임 배송비 결제취소/무료쿠폰 사용 복원
	 * @Method Name : setCancelClaimDeliveryAmt
	 * @Date : 2019. 4. 1.
	 * @Author : KTH
	 * @param ocClaim
	 * @throws Exception
	 */
	public Map<String, Object> setCancelDeliveryAmt(OcClaim ocClaim) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();

		/*
		 * 클레임 정보
		 */
		ocClaim = ocClaimDao.selectClaimBasisInfo(ocClaim); // 클레임기본정보

		/*
		 * 클레임 배송비 취소
		 */
		ocClaim.setClaimRgsterNo(LoginManager.getUserDetails().getAdminNo());
		ocClaim.setCustIp(UtilsRequest.getRequest().getRemoteAddr());
		retMap = claimProcService.setCancelClaimDeliveryAmt(ocClaim);

		/*
		 * 반품인 경우 클레임 금액 재계산
		 */
		if (UtilsText.equals(ocClaim.getClmGbnCode(), CommonCode.CLM_GBN_CODE_RETURN)) {
			/*
			 * 클레임 정보 재 조회(클레임 배송비가 무료로 변경되는 경우가 생길 수 있기 때문)
			 */
			ocClaim = ocClaimDao.selectClaimBasisInfo(ocClaim); // 클레임기본정보

			ocClaim.setClaimRgsterNo(LoginManager.getUserDetails().getAdminNo());

			/*
			 * [원 주문정보 get]
			 */
			OcOrder ocOrder = new OcOrder();
			ocOrder.setOrderNo(ocClaim.getOrgOrderNo());
			ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보

			/*
			 * [주문자 회원 정보]
			 */
			MbMember mbMember = memberDao.selectMemberDefalutInfo(ocOrder.getMemberNo());

			/*
			 * 기존 주문금 환불 기준 데이터 삭제
			 */
			OcClaimPayment deleteClaimPayment = new OcClaimPayment();
			deleteClaimPayment.setClmNo(ocClaim.getClmNo());
			deleteClaimPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REFUND); // 환불
			deleteClaimPayment.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 재경처리 N
			deleteClaimPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_ORDER_AMT); // 주문금
			deleteClaimPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_HISTORY); // 히스토리

			ocClaimPaymentDao.deleteClaimPayment(deleteClaimPayment); // 클레임결제 삭제

			/*
			 * 기존 다족구매 프로모션 관련 히스토리 데이터 삭제(환수/환불 구분 없이 모두 삭제)
			 */
			OcClaimPayment deleteClaimPromoPayment = new OcClaimPayment();
			deleteClaimPromoPayment.setClmNo(ocClaim.getClmNo());
			deleteClaimPromoPayment.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 재경처리 N
			deleteClaimPromoPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_PROMOTION); // 프로모션
			deleteClaimPromoPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_HISTORY); // 히스토리

			ocClaimPaymentDao.deleteClaimPayment(deleteClaimPromoPayment); // 클레임결제 삭제

			// 클레임 금액 계산
			OcClaimAmountVO ocClaimAmountVO = new OcClaimAmountVO();

			/*************************************
			 * 클레임 금액 계산
			 *************************************/
			claimProcService.setClaimAmountCalcForPartCancel(ocClaim, ocOrder, ocClaimAmountVO,
					CommonCode.CLM_GBN_CODE_RETURN, false);

			/*
			 * 증감 구매적립 포인트 계산
			 */
			int thisClaimVariationSavePoint = ocClaimAmountVO.getVariationSavePoint(); // 현재 클레임 증감 적립포인트

			// 더블적립 쿠폰이 사용된 경우
			if (ocClaimAmountVO.getOrderDoubleDscntCpnInfo() != null) {
				// 주문의 구매적립률을 이용하여 계산하므로 주석처리
				// thisClaimVariationSavePoint = thisClaimVariationSavePoint * 2;
			}

			/*
			 * 구매적립 포인트 환수 체크(임직원이 아닌 멤버쉽 회원인 경우만 체크)
			 */
			int clawbackPoint = 0; // 환수포인트

			if (UtilsText.equals(ocOrder.getMemberTypeCode(), CommonCode.MEMBER_TYPE_CODE_MEMBERSHIP)
					&& UtilsText.equals(ocOrder.getEmpYn(), Const.BOOLEAN_FALSE)) {

				// 환수할 포인트가 발생한 경우
				if (thisClaimVariationSavePoint < 0) {
					// 환수포인트 조회 인터페이스
					try {
						clawbackPoint = membershipPointService.getClawbackPoint(mbMember.getSafeKey(),
								mbMember.getSafeKeySeq(), ocOrder.getOrgOrderNo(),
								Math.abs(thisClaimVariationSavePoint));
					} catch (Exception e) {
						throw new Exception("validMsg:포인트 조회에 실패했습니다.");
					}
				}
			}

			/*
			 * 클레임 마스터 금액 업데이트(변동 적립포인트, 환수포인트 포함)
			 */
			OcClaim updateAmtClaim = new OcClaim();
			updateAmtClaim.setClmNo(ocClaim.getClmNo());
			updateAmtClaim.setTotalRfndAmt(ocClaimAmountVO.getRefundCnclAmt()); // 총환불금액
			updateAmtClaim
					.setTotalRedempAmt(ocClaimAmountVO.getRedempAmtByMultiBuy() + ocClaimAmountVO.getClaimDlvyAmt()); // 총환수금액
			updateAmtClaim.setIrdsSavePoint((int) ocClaimAmountVO.getVariationSavePoint()); // 증감적립포인트
			updateAmtClaim.setRedempSavePoint(clawbackPoint); // 환수적립포인트
			updateAmtClaim.setModerNo(LoginManager.getUserDetails().getAdminNo());

			ocClaimDao.updateClaimStat(updateAmtClaim);

			/*
			 * 환수프로모션비(다족구매로 인해 발생) 발생된 경우 결제히스토리 등록
			 */
			if (ocClaimAmountVO.getRedempAmtByMultiBuy() > 0) {
				for (OcClaimPayment redempMultiBuyPayment : ocClaimAmountVO.getRedempMultiBuyPaymentList()) {
					ocClaimPaymentDao.insertClaimPayment(redempMultiBuyPayment);
				}
			}

			/*
			 * 기존 다족구매 리오더 매출로 인해 변경된 금액과 원 주문 기준 취소상품 금액 차이 결제히스토리 등록(현재 취소금을 맞추기 위한 이력)
			 */
			if (ocClaimAmountVO.getAddMultiBuyDifferAmt() > 0) {
				for (OcClaimPayment addMultiBuyDifferPaymentList : ocClaimAmountVO.getAddMultiBuyDifferPaymentList()) {
					ocClaimPaymentDao.insertClaimPayment(addMultiBuyDifferPaymentList);
				}
			}

			/*
			 * 클레임 결제 등록
			 */
			// 주 결제 수단
			if (ocClaimAmountVO.getMainPayment() != null) {
				OcClaimPayment claimPayment = new OcClaimPayment();
				BeanUtils.copyProperties(ocClaimAmountVO.getMainPayment(), claimPayment); // 내용 복사

				if (ocClaimAmountVO.getMainCnclAmt() > 0) { // 실제 취소금이 있는 경우
					claimPayment.setPymntTodoAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소
																									// 금액으로 변경
					claimPayment.setPymntAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

					// 클레임 결제 이력데이터 등록
					claimProcService.setClaimPaymentHistory(ocClaim, claimPayment,
							CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
				}
			}

			// 기프트
			if (ocClaimAmountVO.getGiftPayment() != null) {
				OcClaimPayment claimPayment = new OcClaimPayment();
				BeanUtils.copyProperties(ocClaimAmountVO.getGiftPayment(), claimPayment); // 내용 복사

				if (ocClaimAmountVO.getGiftCnclAmt() > 0) { // 실제 취소금이 있는 경우
					claimPayment.setPymntTodoAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소
																									// 금액으로 변경
					claimPayment.setPymntAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

					// 클레임 결제 이력데이터 등록
					claimProcService.setClaimPaymentHistory(ocClaim, claimPayment,
							CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
				}
			}

			// 이벤트 포인트
			if (ocClaimAmountVO.getEventPointPayment() != null) {
				OcClaimPayment claimPayment = new OcClaimPayment();
				BeanUtils.copyProperties(ocClaimAmountVO.getEventPointPayment(), claimPayment); // 내용 복사

				if (ocClaimAmountVO.getEventPointCnclAmt() > 0) { // 실제 취소금이 있는 경우
					claimPayment.setPymntTodoAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제예정금액 - 클레임
																										// 취소
																										// 금액으로 변경
					claimPayment.setPymntAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소
																									// 금액으로 변경

					// 클레임 결제 이력데이터 등록
					claimProcService.setClaimPaymentHistory(ocClaim, claimPayment,
							CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
				}
			}

			// 포인트
			if (ocClaimAmountVO.getPointPayment() != null) {
				OcClaimPayment claimPayment = new OcClaimPayment();
				BeanUtils.copyProperties(ocClaimAmountVO.getPointPayment(), claimPayment); // 내용 복사

				if (ocClaimAmountVO.getPointCnclAmt() > 0) { // 실제 취소금이 있는 경우
					claimPayment.setPymntTodoAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소
																									// 금액으로
																									// 변경
					claimPayment.setPymntAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

					// 클레임 결제 이력데이터 등록
					claimProcService.setClaimPaymentHistory(ocClaim, claimPayment,
							CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
				}
			}
		}

		return retMap;
	}

	/**
	 * @Desc : 무료배송비 쿠폰 사용복원
	 * @Method Name : setCancelUseDeliveryCoupon
	 * @Date : 2019. 7. 23.
	 * @Author : KTH
	 * @param ocClaim
	 * @param kcpPaymentApproval
	 * @throws Exception
	 */
	public void setCancelUseDeliveryCoupon(OcClaim ocClaim, KcpPaymentApproval kcpPaymentApproval) throws Exception {
		/*
		 * 클레임 정보
		 */
		ocClaim = ocClaimDao.selectClaimBasisInfo(ocClaim); // 클레임기본정보

		MbMemberCoupon reIssueMemberCoupon = new MbMemberCoupon();
		reIssueMemberCoupon.setMemberNo(ocClaim.getMemberNo());
		reIssueMemberCoupon.setHoldCpnSeq(ocClaim.getHoldCpnSeq());
		reIssueMemberCoupon.setReIssueRsnText("클레임 배송비 사용 취소");
		reIssueMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_REISSUANCE);
		reIssueMemberCoupon.setRgsterNo(Const.SYSTEM_ADMIN_NO);
		reIssueMemberCoupon.setModerNo(Const.SYSTEM_ADMIN_NO);

		mbMemberCouponDao.insertMemberCouponReIssue(reIssueMemberCoupon); // 쿠폰 재 발급
	}

	/**
	 * @Desc : 교환 접수기본정보 및 배송비 저장
	 * @Method Name : setClaimExchangeDeliveryInfo
	 * @Date : 2019. 4. 3.
	 * @Author : KTH
	 * @param ocClaim
	 * @param kcpPaymentApproval
	 * @throws Exception
	 */
	public void setClaimExchangeDeliveryInfo(OcClaim ocClaim, KcpPaymentApproval kcpPaymentApproval) throws Exception {
		/*
		 * [원 주문정보 get]
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보

		ocClaim.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자

		/*
		 * [클레임 등록 정보]
		 */
		OcClaim basisOcClaimInfo = ocClaimDao.selectClaimBasisInfo(ocClaim);

		// 추가배송비 관련 set
		if (ocClaim.getAddDlvyAmt() > 0) {
			// form에서 교환배송비 결제방법을 무료교환쿠폰 선택이 아니면 null
			if (!UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(), CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON)) {
				ocClaim.setHoldCpnSeq(null); // 보유쿠폰순번
			}
		} else {
			ocClaim.setAddDlvyAmtPymntType(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE); // 추가배송비결제방법
			ocClaim.setAddDlvyAmt(0); // 추가배송비
			ocClaim.setHoldCpnSeq(null); // 보유쿠폰순번
		}

		// 동봉매송비 처리 - 2020.02.03 별도 저장으로 뺌
		/*
		 * if (ObjectUtils.isEmpty(ocClaim.getEclsDlvyAmt())) {
		 * ocClaim.setEclsDlvyAmt(0); }
		 */

		/*
		 * [클레임 주소정보 업데이트]
		 */
		// 접수취소 상태가 아니면 회수지 정보 업데이트 제외
		if (!UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_ACCEPT)) {
			// 회수지 정보 업데이트 제외
			ocClaim.setBuyerName(null);
			ocClaim.setBuyerHdphnNoText(null);
			ocClaim.setBuyerHdphnNoText(null);
			ocClaim.setBuyerPostCodeText(null);
			ocClaim.setBuyerPostAddrText(null);
			ocClaim.setBuyerDtlAddrText(null);
		}

		// 교환접수, 수거지시, 수령완료, 심의완료 상태에서 교환 회수/재발송 정보 업데이트
		if (UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_ACCEPT)
				|| UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_PICKUP_ORDER)
				|| UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_RECEIVE_FINISH)
				|| UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_JUDGE_FINISH)) {

			ocClaimDao.updateClaimAddrInfo(ocClaim);
		}

		/*
		 * 교환배송비 처리
		 */
		// 교환접수, 수거지시, 수령완료, 심의완료 상태에서 가능
		if (UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_ACCEPT)
				|| UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_PICKUP_ORDER)
				|| UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_RECEIVE_FINISH)
				|| UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_EXCHANGE_JUDGE_FINISH)) {

			if (ocClaim.getAddDlvyAmt() > 0 && basisOcClaimInfo.getAddDlvyAmt() == 0) { // 결제할 배송비가 있고 기존 교환배송비 결제금액이 없는
																						// 경우
				/*
				 * [클레임 배송비정보 업데이트]
				 */
				ocClaimDao.updateClaimDeliveryAmtInfo(ocClaim);

				if (UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(),
						CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT)) {
					/*
					 * [클레임결제 등록] 교환배송비 추가결제
					 */
					OcClaimPayment ocClaimPayment = new OcClaimPayment();
					ocClaimPayment.setClmNo(ocClaim.getClmNo()); // 클레임번호
					ocClaimPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP); // 환수환불구분-환수
					// ocClaimPayment.setPymntDtm(new Timestamp(new Date().getTime())); // 결제일시
					ocClaimPayment.setPymntDtm(null); // 결제일시
					ocClaimPayment.setDeviceCode(ocOrder.getDeviceCode()); // 디바이스코드
					ocClaimPayment.setMainPymntMeansYn(Const.BOOLEAN_TRUE); // 주결제수단여부
					ocClaimPayment.setPymntMeansCode(CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT); // 결제수단코드-가상계좌
					ocClaimPayment.setPymntVndrCode(CommonCode.PYMNT_VNDR_CODE_KCP); // 결제업체코드
					ocClaimPayment.setPymntOrganCodeText(null); // 결제기관코드
					ocClaimPayment.setPymntOrganName(null); // 결제기관명
					ocClaimPayment.setPymntOrganNoText(null); // 결제기관번호(카드번호, 은행계좌번호)
					ocClaimPayment.setIntrstFreeYn(Const.BOOLEAN_FALSE); // 무이자여부
					ocClaimPayment.setInstmtTermCount((short) 0); // 할부기간
					ocClaimPayment.setCardGbnType(null); // 카드구분
					ocClaimPayment.setCardType(null); // 카드유형
					ocClaimPayment.setPymntAprvNoText(null); // 결제승인번호
					ocClaimPayment.setPymntTodoAmt(ocClaim.getAddDlvyAmt()); // 결제예정금액
					ocClaimPayment.setPymntAmt(ocClaim.getAddDlvyAmt()); // 결제금액
					ocClaimPayment.setCardPartCnclPsbltYn(Const.BOOLEAN_FALSE); // 카드부분취소가능여부
					ocClaimPayment.setCashRcptIssueYn(Const.BOOLEAN_FALSE); // 현금영수증발급여부
					ocClaimPayment.setEscrApplyYn(Const.BOOLEAN_FALSE); // 에스크로적용여부
					ocClaimPayment.setEscrAprvNoText(null); // 에스크로승인번호
					ocClaimPayment.setBankCode(null); // 환불:회원환불계좌, 환수:가상계좌발급
					ocClaimPayment.setAcntNoText(null); // 계좌번호
					ocClaimPayment.setAcntHldrName(null); // 예금주명
					ocClaimPayment.setAcntCrtfcYn(Const.BOOLEAN_FALSE); // 계좌인증여부
					ocClaimPayment.setVrtlAcntIssueDtm(null); // 가상계좌발급일시
					ocClaimPayment.setVrtlAcntExprDtm(null); // 가상계좌만료일시
					ocClaimPayment.setGiftCardPinNoText(null); // 상품권PIN번호
					ocClaimPayment.setRedempRfndMemoText(null); // 환수환불메모
					ocClaimPayment.setProcImpsbltYn(Const.BOOLEAN_FALSE); // 처리불가여부
					ocClaimPayment.setProcImpsbltRsnText(null); // 처리불가사유
					ocClaimPayment.setProcImpsbltCmlptDtm(null); // 처리불가완료일시
					ocClaimPayment.setRedempRfndOpetrNo(null); // 환수환불처리자번호
					ocClaimPayment.setRedempRfndOpetrDtm(null); // 환수환불처리일시
					ocClaimPayment.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 자사처리대상여부(교환 추가배송비는 재경팀 확인 N)
					ocClaimPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT); // 발생사유코드 - 배송비
					ocClaimPayment.setPymntStatCode(null); // 결제상태코드
					ocClaimPayment.setRspnsCodeText(null); // 응답코드
					ocClaimPayment.setRspnsMesgText(null); // 응답메시지
					ocClaimPayment.setPymntLogInfo(null); // 결제로그
					ocClaimPayment.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자
					ocClaimPayment.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자

					ocClaimPaymentDao.insertClaimPayment(ocClaimPayment); // 클레임결제 등록

					/*
					 * [클레임배송비 결제(KCP)] 가상계좌 결제 선택 시 KCP 가상계좌 요청, 가상계좌 요청 후 리턴결과를 이용하여 결제내역 업데이트
					 */
					// site no 에 따른 siteKey 구분
					if (UtilsText.equals(ocOrder.getSiteNo(), Const.SITE_NO_ART)) {
						kcpPaymentApproval.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
					} else {
						kcpPaymentApproval.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
					}

					PaymentResult paymentResult = paymentEntrance
							.approval(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, kcpPaymentApproval)); // KCP 결제

					// 결제 실패인 경우 exception
					if (UtilsText.equals(paymentResult.getSuccessYn(), Const.BOOLEAN_FALSE)) {
						throw new Exception("validMsg:".concat(paymentResult.getMessage()));
					}

					KcpPaymentApprovalReturn kcpPaymentApprovalReturn = ((KcpPaymentApprovalReturn) paymentResult
							.getData());

					List<SyCodeDetail> bankList = commonCodeService.getCodeNoName(CommonCode.BANK_CODE); // 은행코드 목록

					// kcp 리턴 bankcode를 이용하여 공통코드의 bankcode 를 추출
					String commonBankCode = bankList.stream()
							.filter(x -> UtilsText.equals(x.getAddInfo1(), kcpPaymentApprovalReturn.getBankCode()))
							.map(SyCodeDetail::getCodeDtlNo).findFirst().orElse(null);

					// kcp 리턴 가상계좌발급일, 만료일 string 을 기준으로 날짜 데이터 생성
					SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DEFAULT_DATETIME_PATTERN_NOT_CHARACTERS);
					Date acntIssueDate = dateFormat.parse(kcpPaymentApprovalReturn.getAppTime());
					Date acntExprDate = dateFormat.parse(kcpPaymentApprovalReturn.getVaDate());

					OcClaimPayment virtualPayment = new OcClaimPayment();
					virtualPayment.setPymntOrganCodeText(kcpPaymentApprovalReturn.getBankCode()); // 결제기관코드(카드, 은행코드...)
					virtualPayment.setPymntOrganName(kcpPaymentApprovalReturn.getBankName()); // 결제기관명(카드, 은행명...)
					virtualPayment.setPymntOrganNoText(kcpPaymentApprovalReturn.getAccount()); // 결제기관번호(카드번호,
																								// 은행계좌번호...)
					virtualPayment.setPymntAprvNoText(kcpPaymentApprovalReturn.getTno()); // 결제승인번호
					virtualPayment.setBankCode(commonBankCode); // 환불:회원환불계좌, 환수:가상계좌발급
					virtualPayment.setAcntNoText(kcpPaymentApprovalReturn.getAccount()); // 계좌번호
					virtualPayment.setAcntHldrName(kcpPaymentApprovalReturn.getDepositor()); // 예금주명
					virtualPayment.setVrtlAcntIssueDtm(new Timestamp(acntIssueDate.getTime())); // 가상계좌발급일시
					virtualPayment.setVrtlAcntExprDtm(new Timestamp(acntExprDate.getTime())); // 가상계좌만료일시
					virtualPayment.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_WAIT_DEPOSIT); // 결제상태코드
					virtualPayment.setRspnsCodeText(kcpPaymentApprovalReturn.getResCd()); // 응답코드
					virtualPayment.setRspnsMesgText(kcpPaymentApprovalReturn.getResMsg()); // 응답메시지
					virtualPayment.setPymntLogInfo(new ObjectMapper().writeValueAsString(kcpPaymentApprovalReturn)); // 결제로그
					virtualPayment.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자
					virtualPayment.setClmNo(ocClaim.getClmNo());
					virtualPayment.setClmPymntSeq(ocClaimPayment.getClmPymntSeq());

					try {
						// 가상계좌요청 시 클레임결제 수정(kcp 리턴 값 사용)
						ocClaimPaymentDao.updateClaimPaymentRequestVirtualAccount(virtualPayment);
					} catch (Exception e) {
						// 클레임결제 수정이 실패할 경우 kcp 가상계좌 요청 취소
						KcpPaymentCancel kcpPaymentCancel = new KcpPaymentCancel();

						// site no 에 따른 siteCd, siteKey 구분
						if (UtilsText.equals(ocOrder.getSiteNo(), Const.SITE_NO_ART)) {
							kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.claim.siteCode"));
							kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
						} else {
							kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.ots.claim.siteCode"));
							kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
						}

						kcpPaymentCancel.setTno(kcpPaymentApprovalReturn.getTno()); // KCP 원거래 거래번호
						kcpPaymentCancel.setModType(CommonCode.KCP_MOD_TYPE_STSC); // 전체취소 STSC / 부분취소 STPC
						kcpPaymentCancel.setCustIp(kcpPaymentApproval.getCustIp()); // 변경 요청자 IP
						kcpPaymentCancel.setModDesc("가맹점 처리 실패"); // 취소 사유

						// 가상계좌 취소(가상계좌 사용중지-환불아님)
						paymentEntrance.cancel(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, kcpPaymentCancel));

						throw new Exception("validMsg:결제 에러 발생");
					}

				} else { // 가상계좌 결제가 아닌 무료쿠폰 사용 시
					/*
					 * [회원 보유쿠폰 사용 업데이트]
					 */
					MbMemberCoupon mbMemberCoupon = new MbMemberCoupon();
					mbMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_USED);
					mbMemberCoupon.setMemberNo(ocOrder.getMemberNo());
					mbMemberCoupon.setHoldCpnSeq(ocClaim.getHoldCpnSeq());
					mbMemberCoupon.setModerNo((LoginManager.getUserDetails().getAdminNo())); // 수정자

					mbMemberCouponDao.updateMemberCouponUseInfo(mbMemberCoupon); // 회원 보유쿠폰 사용 업데이트
				}
			}
		}
	}

	/**
	 * @Desc : 반품 접수기본정보 및 배송비 저장
	 * @Method Name : setClaimReturnDeliveryInfo
	 * @Date : 2019. 4. 3. / 수정 2020.03.13
	 * @Author : KTH, 이강수
	 * @param ocClaim
	 * @param kcpPaymentApproval
	 * @throws Exception
	 */
	public void setClaimReturnDeliveryInfo(OcClaim ocClaim, KcpPaymentApproval kcpPaymentApproval) throws Exception {

		// 수정하는 admin의 no
		String loginAdminNo = LoginManager.getUserDetails().getAdminNo();
		// 클레임번호
		String clmNo = ocClaim.getClmNo();

		/*
		 * [원 주문정보 get]
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보

		ocClaim.setModerNo(loginAdminNo); // 수정자

		/*
		 * [클레임 등록 정보]
		 */
		OcClaim basisOcClaimInfo = ocClaimDao.selectClaimBasisInfo(ocClaim);

		// 추가배송비 관련 set
		if (ocClaim.getAddDlvyAmt() > 0) {
			// form에서 교환배송비 결제방법을 무료교환쿠폰 선택이 아니면 null
			if (!UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(), CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON)) {
				ocClaim.setHoldCpnSeq(null); // 보유쿠폰순번
			}
		} else {
			ocClaim.setAddDlvyAmtPymntType(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE); // 추가배송비결제방법
			ocClaim.setAddDlvyAmt(0); // 추가배송비
			ocClaim.setHoldCpnSeq(null); // 보유쿠폰순번
		}

		// 동봉매송비 처리 - 2020.02.03 별도 저장으로 뺌
		/*
		 * if (ObjectUtils.isEmpty(ocClaim.getEclsDlvyAmt())) {
		 * ocClaim.setEclsDlvyAmt(0); }
		 */

		/*
		 * [클레임 주소정보 업데이트]
		 */
		// 반품접수인 경우만 회수지 정보 업데이트
		if (UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_RETURN_ACCEPT)) {
			ocClaimDao.updateClaimAddrInfo(ocClaim);
		}

		// 2020.03.26 : 점간이동 여부 - 원거래 <-> 점간이동 만 업데이트 (레드마인#1272)
		if (UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_RETURN_ACCEPT)
				|| UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_RETURN_PICKUP_ORDER)
				|| UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_RETURN_RECEIVE_FINISH)) {
			OcClaim branchClm = new OcClaim();
			branchClm.setClmNo(clmNo);
			branchClm.setModerNo(loginAdminNo);
			branchClm.setBranchMoveCode(ocClaim.getBranchMoveCode());
			ocClaimDao.updateClaimAddrInfo(branchClm);
		}

		/*
		 * 반품배송비 처리
		 */
		// 반품접수, 수거지시, 수령완료, 심의완료
		if (!UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_RETURN_ACCEPT)
				&& !UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_RETURN_PICKUP_ORDER)
				&& !UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_RETURN_RECEIVE_FINISH)
				&& !UtilsText.equals(ocClaim.getClmStatCode(), CommonCode.CLM_STAT_CODE_RETURN_JUDGE_FINISH)) {
			return;
		}

		if (ocClaim.getAddDlvyAmt() == 0 || basisOcClaimInfo.getAddDlvyAmt() > 0) {
			// 결제할 배송비가 없거나 기존 반품배송비 결제금액이 있는 경우
			return;
		}

		if (UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(), CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_REFUND_DEDUCTION)) {

			OcOrderPayment ocOrderPayment = new OcOrderPayment();
			ocOrderPayment.setOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준

			List<OcOrderPayment> orderPaymentList = ocOrderPaymentDao.selectPaymentList(ocOrderPayment);

			// 2020.03.05 : 주결제수단 바르기
			// 결제목록에서 먼저 주결제 수단인것만 찾고
			List<OcOrderPayment> orderMainPaymentList = orderPaymentList.stream()
					.filter(x -> UtilsText.equals(x.getMainPymntMeansYn(), Const.BOOLEAN_TRUE))
					.collect(Collectors.toList());
			// 1. 결제수단이 변경된적이 없는 주결제 수단이거나
			// 2. 관리자가 결제수단변경을 승인했으나 아직 주문자가 결제수단을 변경하지 않은 주결제
			OcOrderPayment orderMainPayment = orderMainPaymentList.stream().filter(x -> UtilsText
					.equals(x.getPymntMeansChngPsbltYn(), Const.BOOLEAN_FALSE)
					|| (UtilsText.equals(x.getPymntMeansChngPsbltYn(), Const.BOOLEAN_TRUE)
							&& UtilsText.equals(x.getPymntStatCode(), CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH)))
					.findFirst().orElse(null);
			if (UtilsText.equals(orderMainPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_HANDPHONE)
					&& UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(),
							CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_REFUND_DEDUCTION)) {
				// 2020.03.05 : 주결제수단이 휴대폰결제인데 환불금차감으로 접수된 경우 던진다.
				return;
			}

			/*
			 * [클레임 배송비정보 업데이트]
			 */
			OcClaim paramClm = new OcClaim();
			paramClm.setClmNo(clmNo);
			paramClm.setModerNo(loginAdminNo);
			paramClm.setAddDlvyAmtPymntType(ocClaim.getAddDlvyAmtPymntType());
			paramClm.setAddDlvyAmt(ocClaim.getAddDlvyAmt());
			paramClm.setTotalRfndAmt(basisOcClaimInfo.getTotalRfndAmt() - ocClaim.getAddDlvyAmt());
			paramClm.setTotalRedempAmt(basisOcClaimInfo.getTotalRedempAmt() + ocClaim.getAddDlvyAmt());
			// 클레임 배송비정보 업데이트
			ocClaimDao.updateReturnClaimAmtInfo(paramClm);

			/*
			 * [환불금차감 배송비결제 이력등록] 반품배송비 환불금차감
			 */
			OcClaimPayment _refundClaimDlvyAmt = new OcClaimPayment();
			_refundClaimDlvyAmt.setClmNo(clmNo);
			_refundClaimDlvyAmt.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP); // 환수환불구분:환수
			_refundClaimDlvyAmt.setPymntDtm(new Timestamp(new Date().getTime())); // 결제일시
			_refundClaimDlvyAmt.setDeviceCode(basisOcClaimInfo.getDeviceCode()); // 디바이스코드
			_refundClaimDlvyAmt.setMainPymntMeansYn(Const.BOOLEAN_TRUE); // 주결제수단여부
			_refundClaimDlvyAmt.setPymntMeansCode(CommonCode.PYMNT_MEANS_CODE_CARD); // 결제수단코드:임의로 아무거나
			_refundClaimDlvyAmt.setPymntVndrCode(null); // 결제업체코드
			_refundClaimDlvyAmt.setInstmtTermCount((short) 0); // 할부기간
			_refundClaimDlvyAmt.setPymntTodoAmt(ocClaim.getAddDlvyAmt()); // 결제예정금액
			_refundClaimDlvyAmt.setPymntAmt(ocClaim.getAddDlvyAmt()); // 결제금액
			_refundClaimDlvyAmt.setCardPartCnclPsbltYn(Const.BOOLEAN_FALSE); // 카드부분취소가능여부
			_refundClaimDlvyAmt.setCashRcptIssueYn(Const.BOOLEAN_FALSE); // 현금영수증발급여부
			_refundClaimDlvyAmt.setEscrApplyYn(Const.BOOLEAN_FALSE); // 에스크로적용여부
			_refundClaimDlvyAmt.setAcntCrtfcYn(Const.BOOLEAN_FALSE); // 계좌인증여부
			_refundClaimDlvyAmt.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 자사처리대상여부
			_refundClaimDlvyAmt.setHistGbnType(CommonCode.HIST_GBN_TYPE_HISTORY); // 이력구분 - 이력근거용
			_refundClaimDlvyAmt.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT); // 발생사유코드:(클레임)배송비
			_refundClaimDlvyAmt.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH); // 결제완료
			_refundClaimDlvyAmt.setRgsterNo(loginAdminNo); // 등록자
			_refundClaimDlvyAmt.setModerNo(loginAdminNo); // 수정자

			OcClaimProduct paramPrdt = new OcClaimProduct();
			paramPrdt.setClmNo(clmNo);
			paramPrdt.setClmPrdtSeq(1);
			paramPrdt = ocClaimProductDao.selectByPrimaryKey(paramPrdt);
			_refundClaimDlvyAmt.setVndrNo(paramPrdt.getVndrNo()); // 업체번호
			// 환불금차감 배송비결제 이력등록
			ocClaimPaymentDao.insertClaimPayment(_refundClaimDlvyAmt);

			// TODO : 2020.03.12 : 반품배송비 환불금 차감으로 변경 시에 클레임 금액 다시 계산
			OcClaimAmountVO ocClaimAmountVO = new OcClaimAmountVO();
			OcClaim pkClm = new OcClaim();
			pkClm.setClmNo(clmNo);
			pkClm = ocClaimDao.selectByPrimaryKey(pkClm); // 변경후 기본 칼럼들 조회 후 set
			claimProcService.setClaimAmountCalcForPartCancel(pkClm, ocOrder, ocClaimAmountVO,
					CommonCode.CLM_GBN_CODE_RETURN, false);

			/*
			 * 기존 주문금 환불 이력 삭제
			 */
			OcClaimPayment deletePymnt = new OcClaimPayment();
			deletePymnt.setClmNo(clmNo);
			deletePymnt.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			deletePymnt.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE);
			deletePymnt.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_ORDER_AMT);
			deletePymnt.setHistGbnType(CommonCode.HIST_GBN_TYPE_HISTORY);
			ocClaimPaymentDao.deleteClaimPayment(deletePymnt);

			// 등록자 null 방지
			basisOcClaimInfo.setClaimRgsterNo(loginAdminNo);

			// 주 결제 수단
			if (ocClaimAmountVO.getMainPayment() != null) {
				OcClaimPayment claimPayment = new OcClaimPayment();
				BeanUtils.copyProperties(ocClaimAmountVO.getMainPayment(), claimPayment); // 내용 복사

				if (ocClaimAmountVO.getMainCnclAmt() > 0) { // 실제 취소금이 있는 경우
					int mainPymntAmt = ocClaimAmountVO.getMainPayment().getPymntAmt();
					log.error("***** mainPymntAmt : " + String.valueOf(mainPymntAmt));
					// 클레임 결제 이력데이터 등록
					claimProcService.setClaimPaymentHistory(basisOcClaimInfo, claimPayment,
							CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
				}
			}

			// 기프트
			if (ocClaimAmountVO.getGiftPayment() != null) {
				OcClaimPayment claimPayment = new OcClaimPayment();
				BeanUtils.copyProperties(ocClaimAmountVO.getGiftPayment(), claimPayment); // 내용 복사

				if (ocClaimAmountVO.getGiftCnclAmt() > 0) { // 실제 취소금이 있는 경우
					int giftPymntAmt = ocClaimAmountVO.getGiftPayment().getPymntAmt();
					log.error("***** giftPymntAmt : " + String.valueOf(giftPymntAmt));
					// 클레임 결제 이력데이터 등록
					claimProcService.setClaimPaymentHistory(basisOcClaimInfo, claimPayment,
							CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
				}
			}

			// 이벤트 포인트
			if (ocClaimAmountVO.getEventPointPayment() != null) {
				OcClaimPayment claimPayment = new OcClaimPayment();
				BeanUtils.copyProperties(ocClaimAmountVO.getEventPointPayment(), claimPayment); // 내용 복사

				if (ocClaimAmountVO.getEventPointCnclAmt() > 0) { // 실제 취소금이 있는 경우
					int eventPointPymntAmt = ocClaimAmountVO.getEventPointPayment().getPymntAmt();
					log.error("***** eventPointPymntAmt : " + String.valueOf(eventPointPymntAmt));
					// 클레임 결제 이력데이터 등록
					claimProcService.setClaimPaymentHistory(basisOcClaimInfo, claimPayment,
							CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
				}
			}

			// 포인트
			if (ocClaimAmountVO.getPointPayment() != null) {
				OcClaimPayment claimPayment = new OcClaimPayment();
				BeanUtils.copyProperties(ocClaimAmountVO.getPointPayment(), claimPayment); // 내용 복사

				if (ocClaimAmountVO.getPointCnclAmt() > 0) { // 실제 취소금이 있는 경우
					int pointPymntAmt = ocClaimAmountVO.getPointPayment().getPymntAmt();
					log.error("***** pointPymntAmt : " + String.valueOf(pointPymntAmt));
					// 클레임 결제 이력데이터 등록
					claimProcService.setClaimPaymentHistory(basisOcClaimInfo, claimPayment,
							CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
				}
			}

//			int i = 1;
//			if (i == 1) {
//				throw new Exception("멈추자!");
//			}

		} else if (UtilsText.equals(ocClaim.getAddDlvyAmtPymntType(), CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT)) {
			// 이하 반품배송비 '가상계좌발급'로 선결제 시

			/*
			 * 2020.03.12 [클레임 업데이트]
			 */
			OcClaim paramClm = new OcClaim();
			paramClm.setClmNo(clmNo);
			paramClm.setModerNo(LoginManager.getUserDetails().getAdminNo());
			paramClm.setAddDlvyAmtPymntType(ocClaim.getAddDlvyAmtPymntType());
			paramClm.setAddDlvyAmt(ocClaim.getAddDlvyAmt());
			paramClm.setTotalRfndAmt(basisOcClaimInfo.getTotalRfndAmt());
			paramClm.setTotalRedempAmt(0);
			ocClaimDao.updateReturnClaimAmtInfo(paramClm);

			/*
			 * [클레임결제 등록] 반품배송비 추가결제
			 */
			OcClaimPayment ocClaimPayment = new OcClaimPayment();
			ocClaimPayment.setClmNo(clmNo); // 클레임번호
			ocClaimPayment.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP); // 환수환불구분-환수
			// ocClaimPayment.setPymntDtm(new Timestamp(new Date().getTime())); // 결제일시
			ocClaimPayment.setPymntDtm(null); // 결제일시
			ocClaimPayment.setDeviceCode(ocOrder.getDeviceCode()); // 디바이스코드
			ocClaimPayment.setMainPymntMeansYn(Const.BOOLEAN_TRUE); // 주결제수단여부
			ocClaimPayment.setPymntMeansCode(CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT); // 결제수단코드-가상계좌
			ocClaimPayment.setPymntVndrCode(CommonCode.PYMNT_VNDR_CODE_KCP); // 결제업체코드
			ocClaimPayment.setPymntOrganCodeText(null); // 결제기관코드
			ocClaimPayment.setPymntOrganName(null); // 결제기관명
			ocClaimPayment.setPymntOrganNoText(null); // 결제기관번호(카드번호, 은행계좌번호)
			ocClaimPayment.setIntrstFreeYn(Const.BOOLEAN_FALSE); // 무이자여부
			ocClaimPayment.setInstmtTermCount((short) 0); // 할부기간
			ocClaimPayment.setCardGbnType(null); // 카드구분
			ocClaimPayment.setCardType(null); // 카드유형
			ocClaimPayment.setPymntAprvNoText(null); // 결제승인번호
			ocClaimPayment.setPymntTodoAmt(ocClaim.getAddDlvyAmt()); // 결제예정금액
			ocClaimPayment.setPymntAmt(ocClaim.getAddDlvyAmt()); // 결제금액
			ocClaimPayment.setCardPartCnclPsbltYn(Const.BOOLEAN_FALSE); // 카드부분취소가능여부
			ocClaimPayment.setCashRcptIssueYn(Const.BOOLEAN_FALSE); // 현금영수증발급여부
			ocClaimPayment.setEscrApplyYn(Const.BOOLEAN_FALSE); // 에스크로적용여부
			ocClaimPayment.setEscrAprvNoText(null); // 에스크로승인번호
			ocClaimPayment.setBankCode(null); // 환불:회원환불계좌, 환수:가상계좌발급
			ocClaimPayment.setAcntNoText(null); // 계좌번호
			ocClaimPayment.setAcntHldrName(null); // 예금주명
			ocClaimPayment.setAcntCrtfcYn(Const.BOOLEAN_FALSE); // 계좌인증여부
			ocClaimPayment.setVrtlAcntIssueDtm(null); // 가상계좌발급일시
			ocClaimPayment.setVrtlAcntExprDtm(null); // 가상계좌만료일시
			ocClaimPayment.setGiftCardPinNoText(null); // 상품권PIN번호
			ocClaimPayment.setRedempRfndMemoText(null); // 환수환불메모
			ocClaimPayment.setProcImpsbltYn(Const.BOOLEAN_FALSE); // 처리불가여부
			ocClaimPayment.setProcImpsbltRsnText(null); // 처리불가사유
			ocClaimPayment.setProcImpsbltCmlptDtm(null); // 처리불가완료일시
			ocClaimPayment.setRedempRfndOpetrNo(null); // 환수환불처리자번호
			ocClaimPayment.setRedempRfndOpetrDtm(null); // 환수환불처리일시
			ocClaimPayment.setMmnyProcTrgtYn(Const.BOOLEAN_FALSE); // 자사처리대상여부(교환 추가배송비는 재경팀 확인 N)
			ocClaimPayment.setHistGbnType(CommonCode.HIST_GBN_TYPE_PUBLIC); // 이력구분 - 실결제
			ocClaimPayment.setOcrncRsnCode(CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT); // 발생사유코드 - 배송비
			ocClaimPayment.setPymntStatCode(null); // 결제상태코드
			ocClaimPayment.setRspnsCodeText(null); // 응답코드
			ocClaimPayment.setRspnsMesgText(null); // 응답메시지
			ocClaimPayment.setPymntLogInfo(null); // 결제로그
			ocClaimPayment.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자
			ocClaimPayment.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자

			ocClaimPaymentDao.insertClaimPayment(ocClaimPayment); // 클레임결제 등록

			/*
			 * [반품배송비 결제(KCP)] 가상계좌 결제 선택 시 KCP 가상계좌 요청, 가상계좌 요청 후 리턴결과를 이용하여 결제내역 업데이트
			 */
			// site no 에 따른 siteKey 구분
			if (UtilsText.equals(ocOrder.getSiteNo(), Const.SITE_NO_ART)) {
				kcpPaymentApproval.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
			} else {
				kcpPaymentApproval.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
			}

			PaymentResult paymentResult = paymentEntrance
					.approval(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, kcpPaymentApproval)); // KCP 결제

			// 결제 실패인 경우 exception
			if (UtilsText.equals(paymentResult.getSuccessYn(), Const.BOOLEAN_FALSE)) {
				throw new Exception("validMsg:".concat(paymentResult.getMessage()));
			}

			KcpPaymentApprovalReturn kcpPaymentApprovalReturn = ((KcpPaymentApprovalReturn) paymentResult.getData());

			List<SyCodeDetail> bankList = commonCodeService.getCodeNoName(CommonCode.BANK_CODE); // 은행코드 목록

			// kcp 리턴 bankcode를 이용하여 공통코드의 bankcode 를 추출
			String commonBankCode = bankList.stream()
					.filter(x -> UtilsText.equals(x.getAddInfo1(), kcpPaymentApprovalReturn.getBankCode()))
					.map(SyCodeDetail::getCodeDtlNo).findFirst().orElse(null);

			// kcp 리턴 가상계좌발급일, 만료일 string 을 기준으로 날짜 데이터 생성
			SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DEFAULT_DATETIME_PATTERN_NOT_CHARACTERS);
			Date acntIssueDate = dateFormat.parse(kcpPaymentApprovalReturn.getAppTime());
			Date acntExprDate = dateFormat.parse(kcpPaymentApprovalReturn.getVaDate());

			OcClaimPayment virtualPayment = new OcClaimPayment();
			virtualPayment.setPymntOrganCodeText(kcpPaymentApprovalReturn.getBankCode()); // 결제기관코드(카드, 은행코드...)
			virtualPayment.setPymntOrganName(kcpPaymentApprovalReturn.getBankName()); // 결제기관명(카드, 은행명...)
			virtualPayment.setPymntOrganNoText(kcpPaymentApprovalReturn.getAccount()); // 결제기관번호(카드번호,
																						// 은행계좌번호...)
			virtualPayment.setPymntAprvNoText(kcpPaymentApprovalReturn.getTno()); // 결제승인번호
			virtualPayment.setBankCode(commonBankCode); // 환불:회원환불계좌, 환수:가상계좌발급
			virtualPayment.setAcntNoText(kcpPaymentApprovalReturn.getAccount()); // 계좌번호
			virtualPayment.setAcntHldrName(kcpPaymentApprovalReturn.getDepositor()); // 예금주명
			virtualPayment.setVrtlAcntIssueDtm(new Timestamp(acntIssueDate.getTime())); // 가상계좌발급일시
			virtualPayment.setVrtlAcntExprDtm(new Timestamp(acntExprDate.getTime())); // 가상계좌만료일시
			virtualPayment.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_WAIT_DEPOSIT); // 결제상태코드
			virtualPayment.setRspnsCodeText(kcpPaymentApprovalReturn.getResCd()); // 응답코드
			virtualPayment.setRspnsMesgText(kcpPaymentApprovalReturn.getResMsg()); // 응답메시지
			virtualPayment.setPymntLogInfo(new ObjectMapper().writeValueAsString(kcpPaymentApprovalReturn)); // 결제로그
			virtualPayment.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자
			virtualPayment.setClmNo(clmNo);
			virtualPayment.setClmPymntSeq(ocClaimPayment.getClmPymntSeq());

			try {
				// 가상계좌요청 시 클레임결제 수정(kcp 리턴 값 사용)
				ocClaimPaymentDao.updateClaimPaymentRequestVirtualAccount(virtualPayment);
			} catch (Exception e) {
				// 클레임결제 수정이 실패할 경우 kcp 가상계좌 요청 취소
				KcpPaymentCancel kcpPaymentCancel = new KcpPaymentCancel();

				// site no 에 따른 siteCd, siteKey 구분
				if (UtilsText.equals(ocOrder.getSiteNo(), Const.SITE_NO_ART)) {
					kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.claim.siteCode"));
					kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
				} else {
					kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.ots.claim.siteCode"));
					kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
				}

				kcpPaymentCancel.setTno(kcpPaymentApprovalReturn.getTno()); // KCP 원거래 거래번호
				kcpPaymentCancel.setModType(CommonCode.KCP_MOD_TYPE_STSC); // 전체취소 STSC / 부분취소 STPC
				kcpPaymentCancel.setCustIp(kcpPaymentApproval.getCustIp()); // 변경 요청자 IP
				kcpPaymentCancel.setModDesc("가맹점 처리 실패"); // 취소 사유

				// 가상계좌 취소(가상계좌 사용중지-환불아님)
				paymentEntrance.cancel(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, kcpPaymentCancel));

				throw new Exception("validMsg:결제 에러 발생");
			}

		} else { // 가상계좌 결제가 아닌 무료쿠폰 사용 시
			/*
			 * [회원 보유쿠폰 사용 업데이트]
			 */
			MbMemberCoupon mbMemberCoupon = new MbMemberCoupon();
			mbMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_USED);
			mbMemberCoupon.setMemberNo(ocOrder.getMemberNo());
			mbMemberCoupon.setHoldCpnSeq(ocClaim.getHoldCpnSeq());
			mbMemberCoupon.setModerNo((LoginManager.getUserDetails().getAdminNo())); // 수정자

			mbMemberCouponDao.updateMemberCouponUseInfo(mbMemberCoupon); // 회원 보유쿠폰 사용 업데이트
		}
	}

	/**
	 * @Desc : AS에서 수선 불가로 교환 접수
	 * @Method Name : registClaimExchangeFromAfterservice
	 * @Date : 2019. 4. 19.
	 * @Author : KTH
	 * @param ocAsAcceptProduct
	 * @return
	 * @throws Exception
	 */
	public OcClaim registClaimExchangeFromAfterservice(OcAsAcceptProduct ocAsAcceptProduct) throws Exception {

		if (!UtilsText.equals(ocAsAcceptProduct.getAsProcTypeDtlCode(), CommonCode.AS_PROC_TYPE_DTL_CODE_CHANGE)) {
			throw new Exception("validMsg:AS에서 진행할 수 없는 클레임 접수 입니다.");
		}

		OcClaim ocClaim = new OcClaim();
		ocClaim.setOrderNo(ocAsAcceptProduct.getOrderNo());

		ocClaim.setClaimRgsterNo(LoginManager.getUserDetails().getAdminNo());

		/*
		 * [주문자 회원 정보]
		 */
		MbMember mbMember = memberDao.selectMemberDefalutInfo(ocAsAcceptProduct.getMemberNo());

		/*
		 * [AS정보]
		 */
		OcAsAccept ocAsAccept = new OcAsAccept();
		ocAsAccept.setAsAcceptNo(ocAsAcceptProduct.getAsAcceptNo());
		ocAsAccept = ocAsAcceptDao.selectAfterServiceDetailInfo(ocAsAccept); // AS기본정보

		/*
		 * [주문상품정보 get]
		 */
		OcOrderProduct ocOrderProduct = new OcOrderProduct();
		ocOrderProduct.setOrderNo(ocAsAcceptProduct.getOrderNo());
		ocOrderProduct.setOrderPrdtSeq(ocAsAcceptProduct.getOrderPrdtSeq());
		List<OcOrderProduct> ocOrderProductList = ocOrderProductDao.select(ocOrderProduct);

		ocOrderProduct = ocOrderProductList.get(0);

		/*
		 * [클레임처리유형 코드]
		 */
		List<SyCodeDetail> clmProcTypeCodeList = commonCodeService.getCodeNoName(CommonCode.CLM_PROC_TYPE_CODE);
		String clmProcTypeCodeName = ""; // 클레임처리유형 코드 명

		/*
		 * [기본 등록정보 set]
		 */
		ocClaim.setClmGbnCode(CommonCode.CLM_GBN_CODE_EXCHANGE); // 교환
		ocClaim.setOrgOrderNo(ocAsAcceptProduct.getOrgOrderNo()); // 원주문번호
		ocClaim.setRtrvlGbnType(CommonCode.RTRVL_GBN_TYPE_ONLINE); // 회수지구분(O:온라인)
		ocClaim.setRtrvlStoreNo(null); // 회수매장번호
		ocClaim.setRtrvlReqYn(Const.BOOLEAN_TRUE); // 회수신청여부
		ocClaim.setDlvyTypeCode(CommonCode.DLVY_TYPE_CODE_NORMAL_LOGISTICS); // 배송유형코드-일반택배
		ocClaim.setVndrNo(ocOrderProduct.getVndrNo()); // 업체번호
		ocClaim.setUnProcYn(Const.BOOLEAN_FALSE); // 미처리 여부
		ocClaim.setAdminAcceptYn(Const.BOOLEAN_TRUE); // 관리자접수여부
		ocClaim.setOflnAcceptYn(Const.BOOLEAN_FALSE); // 오프라인접수여부
		ocClaim.setAddDlvyAmtPymntType(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE); // 클레임배송비결제방법
		ocClaim.setAddDlvyAmt(0); // 클레임배송비
		ocClaim.setHoldCpnSeq(null); // 보유쿠폰순번
		ocClaim.setClmStatCode(CommonCode.CLM_STAT_CODE_EXCHANGE_JUDGE_FINISH); // 클레임상태코드 - 심의완료
		ocClaim.setAsAcceptNo(ocAsAcceptProduct.getAsAcceptNo()); // AS접수번호
		ocClaim.setBranchMoveCode(CommonCode.BRANCH_MOVE_CODE_ORIGIN_DEAL); // 점간이동코드 - 원거래

		ocClaim.setTotalRfndAmt(0); // 총환불금액
		ocClaim.setTotalRedempAmt(0); // 총환수금액

		// AS정보의 주문자 정보로 set
		ocClaim.setBuyerName(ocAsAccept.getBuyerName());
		ocClaim.setBuyerTelNoText(ocAsAccept.getBuyerHdphnNoText());
		ocClaim.setBuyerHdphnNoText(ocAsAccept.getBuyerHdphnNoText());
		ocClaim.setBuyerPostCodeText(ocAsAccept.getBuyerPostCodeText());
		ocClaim.setBuyerPostAddrText(ocAsAccept.getBuyerPostAddrText());
		ocClaim.setBuyerDtlAddrText(ocAsAccept.getBuyerDtlAddrText());

		// AS정보의 수취인 정보로 set
		ocClaim.setRcvrName(ocAsAccept.getRcvrName());
		ocClaim.setRcvrTelNoText(ocAsAccept.getRcvrHdphnNoText());
		ocClaim.setRcvrHdphnNoText(ocAsAccept.getRcvrHdphnNoText());
		ocClaim.setRcvrPostCodeText(ocAsAccept.getRcvrPostCodeText());
		ocClaim.setRcvrPostAddrText(ocAsAccept.getRcvrPostAddrText());
		ocClaim.setRcvrDtlAddrText(ocAsAccept.getRcvrDtlAddrText());

		// 주문자 회원 정보에 환불계좌 정보가 있으면 set
		if (UtilsText.isNotEmpty(mbMember.getBankCode()) && UtilsText.isNotEmpty(mbMember.getAcntNoText())
				&& UtilsText.isNotEmpty(mbMember.getAcntHldrName())) {
			ocClaim.setBankCode(mbMember.getBankCode());
			ocClaim.setRfndAcntText(mbMember.getAcntNoText());
			ocClaim.setAcntHldrName(mbMember.getAcntHldrName());
		}

		ocClaim.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자
		ocClaim.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자

		/*
		 * [클레임 등록(마스터)] 등록 시 생성된 clmNo 를 ocClaim 에서 가지고 있음
		 */
		ocClaimDao.insertClaimInfo(ocClaim);

		/*
		 * [클레임상품 등록] [클레임상태이력 등록]
		 */
		clmProcTypeCodeName = clmProcTypeCodeList.stream()
				.filter(x -> UtilsText.equals(x.getCodeDtlNo(), CommonCode.CLM_PROC_TYPE_CODE_EXCHANGE_AS_IMPOSSIBLE))
				.map(o -> o.getCodeDtlName()).findFirst().orElse(null);

		OcClaimProduct ocClaimProduct = new OcClaimProduct();
		ocClaimProduct.setOrderPrdtSeq(ocAsAcceptProduct.getOrderPrdtSeq()); // 주문상품 순번
		ocClaimProduct.setClmRsnCode(CommonCode.CLM_RSN_CODE_EXCHANGE_ETC); // 클레임 사유 코드(교환) - 기타
		ocClaimProduct.setClmEtcRsnText(clmProcTypeCodeName); // 클레임기타사유(처리유형 코드명으로 대체)
		ocClaimProduct.setClmProcTypeCode(CommonCode.CLM_PROC_TYPE_CODE_EXCHANGE_AS_IMPOSSIBLE); // 클레임 처리유형 코드(교환)
																									// - A/S 불가
		ocClaimProduct.setClmProcContText(clmProcTypeCodeName); // 클레임처리내용(처리유형 코드명으로 대체)

		this.insertClaimPrdtAndClaimStatusHistory(ocClaim, ocClaimProduct,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_JUDGE_FINISH); // 클레임상품 상태 코드(교환) : 심의완료

		// 교환 대상 상품 등록
		OcClaimProduct exchangeClaimProduct = new OcClaimProduct(); // 재배송 상품
		BeanUtils.copyProperties(ocClaimProduct, exchangeClaimProduct);

		// 교환대상 상품 등록
		exchangeClaimProduct.setUpClmPrdtSeq(ocClaimProduct.getClmPrdtSeq()); // 회수 상품 기준의 클레임상품순번

		// 클레임상품 등록, 클레임상태이력 등록
		this.insertClaimPrdtAndClaimStatusHistory(ocClaim, exchangeClaimProduct,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT); // 클레임상품 상태 코드(교환) : 교환접수

		return ocClaim;
	}

	/**
	 * @Desc : AS에서 수선 불가로 반품 접수
	 * @Method Name : registClaimReturnFromAfterservice
	 * @Date : 2019. 4. 19.
	 * @Author : KTH
	 * @param ocAsAcceptProduct
	 * @return
	 * @throws Exception
	 */
	public OcClaim registClaimReturnFromAfterservice(OcAsAcceptProduct ocAsAcceptProduct) throws Exception {

		if (!UtilsText.equals(ocAsAcceptProduct.getAsProcTypeDtlCode(), CommonCode.AS_PROC_TYPE_DTL_CODE_RETURN)) {
			throw new Exception("validMsg:AS에서 진행할 수 없는 클레임 접수 입니다.");
		}

		OcClaimAmountVO ocClaimAmountVO = new OcClaimAmountVO();

		OcClaim ocClaim = new OcClaim();
		ocClaim.setOrderNo(ocAsAcceptProduct.getOrderNo());

		ocClaim.setClaimRgsterNo(LoginManager.getUserDetails().getAdminNo());

		/*
		 * [주문자 회원 정보]
		 */
		MbMember mbMember = memberDao.selectMemberDefalutInfo(ocAsAcceptProduct.getMemberNo());

		/*
		 * [AS정보]
		 */
		OcAsAccept ocAsAccept = new OcAsAccept();
		ocAsAccept.setAsAcceptNo(ocAsAcceptProduct.getAsAcceptNo());
		ocAsAccept = ocAsAcceptDao.selectAfterServiceDetailInfo(ocAsAccept); // AS기본정보

		/*
		 * [주문정보 get]
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocAsAcceptProduct.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보

		/*
		 * [주문상품정보 get]
		 */
		OcOrderProduct ocOrderProduct = new OcOrderProduct();
		ocOrderProduct.setOrderNo(ocAsAcceptProduct.getOrderNo());
		ocOrderProduct.setOrderPrdtSeq(ocAsAcceptProduct.getOrderPrdtSeq());
		List<OcOrderProduct> ocOrderProductList = ocOrderProductDao.select(ocOrderProduct);

		ocOrderProduct = ocOrderProductList.get(0);

		/*
		 * [클레임처리유형 코드]
		 */
		List<SyCodeDetail> clmProcTypeCodeList = commonCodeService.getCodeNoName(CommonCode.CLM_PROC_TYPE_CODE);
		String clmProcTypeCodeName = ""; // 클레임처리유형 코드 명

		/*
		 * [기본 등록정보 set]
		 */
		ocClaim.setClmGbnCode(CommonCode.CLM_GBN_CODE_RETURN); // 반품
		ocClaim.setOrgOrderNo(ocAsAcceptProduct.getOrgOrderNo()); // 원주문번호
		ocClaim.setRtrvlGbnType(CommonCode.RTRVL_GBN_TYPE_ONLINE); // 회수지구분(O:온라인)
		ocClaim.setRtrvlStoreNo(null); // 회수매장번호
		ocClaim.setRtrvlReqYn(Const.BOOLEAN_TRUE); // 회수신청여부
		ocClaim.setDlvyTypeCode(CommonCode.DLVY_TYPE_CODE_NORMAL_LOGISTICS); // 배송유형코드-일반택배
		ocClaim.setVndrNo(ocOrderProduct.getVndrNo()); // 업체번호
		ocClaim.setUnProcYn(Const.BOOLEAN_FALSE); // 미처리 여부
		ocClaim.setAdminAcceptYn(Const.BOOLEAN_TRUE); // 관리자접수여부
		ocClaim.setOflnAcceptYn(Const.BOOLEAN_FALSE); // 오프라인접수여부
		ocClaim.setAddDlvyAmtPymntType(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE); // 클레임배송비결제방법
		ocClaim.setAddDlvyAmt(0); // 클레임배송비
		ocClaim.setHoldCpnSeq(null); // 보유쿠폰순번
		ocClaim.setClmStatCode(CommonCode.CLM_STAT_CODE_RETURN_JUDGE_FINISH); // 클레임상태코드 - 심의완료
		ocClaim.setAsAcceptNo(ocAsAcceptProduct.getAsAcceptNo()); // AS접수번호
		ocClaim.setBranchMoveCode(CommonCode.BRANCH_MOVE_CODE_ORIGIN_DEAL); // 점간이동코드 - 원거래

		ocClaim.setTotalRfndAmt(0); // 총환불금액
		ocClaim.setTotalRedempAmt(0); // 총환수금액

		// AS정보의 주문자 정보로 set
		ocClaim.setBuyerName(ocAsAccept.getBuyerName());
		ocClaim.setBuyerTelNoText(ocAsAccept.getBuyerHdphnNoText());
		ocClaim.setBuyerHdphnNoText(ocAsAccept.getBuyerHdphnNoText());
		ocClaim.setBuyerPostCodeText(ocAsAccept.getBuyerPostCodeText());
		ocClaim.setBuyerPostAddrText(ocAsAccept.getBuyerPostAddrText());
		ocClaim.setBuyerDtlAddrText(ocAsAccept.getBuyerDtlAddrText());

		// AS정보의 쉬취인 정보로 set
		ocClaim.setRcvrName(ocAsAccept.getRcvrName());
		ocClaim.setRcvrTelNoText(ocAsAccept.getRcvrHdphnNoText());
		ocClaim.setRcvrHdphnNoText(ocAsAccept.getRcvrHdphnNoText());
		ocClaim.setRcvrPostCodeText(ocAsAccept.getRcvrPostCodeText());
		ocClaim.setRcvrPostAddrText(ocAsAccept.getRcvrPostAddrText());
		ocClaim.setRcvrDtlAddrText(ocAsAccept.getRcvrDtlAddrText());

		// 주문자 회원 정보에 환불계좌 정보가 있으면 set
		if (UtilsText.isNotEmpty(mbMember.getBankCode()) && UtilsText.isNotEmpty(mbMember.getAcntNoText())
				&& UtilsText.isNotEmpty(mbMember.getAcntHldrName())) {
			ocClaim.setBankCode(mbMember.getBankCode());
			ocClaim.setRfndAcntText(mbMember.getAcntNoText());
			ocClaim.setAcntHldrName(mbMember.getAcntHldrName());
		}

		ocClaim.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자
		ocClaim.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자

		/*
		 * [클레임 등록(마스터)] 등록 시 생성된 clmNo 를 ocClaim 에서 가지고 있음
		 */
		ocClaimDao.insertClaimInfo(ocClaim);

		/*
		 * [클레임상품 등록] [클레임상태이력 등록]
		 */
		clmProcTypeCodeName = clmProcTypeCodeList.stream()
				.filter(x -> UtilsText.equals(x.getCodeDtlNo(), CommonCode.CLM_PROC_TYPE_CODE_RETURN_AS_IMPOSSIBLE))
				.map(o -> o.getCodeDtlName()).findFirst().orElse(null);

		OcClaimProduct ocClaimProduct = new OcClaimProduct();
		ocClaimProduct.setOrderPrdtSeq(ocAsAcceptProduct.getOrderPrdtSeq()); // 주문상품 순번
		ocClaimProduct.setClmRsnCode(CommonCode.CLM_RSN_CODE_RETURN_ETC); // 클레임 사유 코드(반품) - 기타
		ocClaimProduct.setClmEtcRsnText(clmProcTypeCodeName); // 클레임기타사유(처리유형 코드명으로 대체)
		ocClaimProduct.setClmProcTypeCode(CommonCode.CLM_PROC_TYPE_CODE_RETURN_AS_IMPOSSIBLE); // 클레임 처리유형 코드(반품)
																								// - A/S 불가
		ocClaimProduct.setClmProcContText(clmProcTypeCodeName); // 클레임처리내용(처리유형 코드명으로 대체)
		this.insertClaimPrdtAndClaimStatusHistory(ocClaim, ocClaimProduct,
				CommonCode.CLM_PRDT_STAT_CODE_RETURN_JUDGE_FINISH); // 클레임상품 상태 코드(반품) : 심의완료

		/*************************************
		 * 클레임 금액 계산(클레임 마스터/상품 등록 후 호출)
		 *************************************/
		// ocClaimAmountVO 에 계산된 금액 set
		claimProcService.setClaimAmountCalcForPartCancel(ocClaim, ocOrder, ocClaimAmountVO,
				CommonCode.CLM_GBN_CODE_RETURN, false);

		/*
		 * 클레임 계산 결과 환수금이 더 많은 경우 취소 불가 처리
		 */
		if (ocClaimAmountVO.getRefundCnclAmt() < 0) {
			throw new Exception("validMsg:환수금이 발생하여 취소가 불가합니다.");
		}

		/*
		 * 증감 구매적립 포인트 계산
		 */
		int thisClaimVariationSavePoint = ocClaimAmountVO.getVariationSavePoint(); // 현재 클레임 증감 적립포인트

		// 더블적립 쿠폰이 사용된 경우
		if (ocClaimAmountVO.getOrderDoubleDscntCpnInfo() != null) {
			// 주문의 구매적립률을 이용하여 계산하므로 주석처리
			// thisClaimVariationSavePoint = thisClaimVariationSavePoint * 2;
		}

		/*
		 * 구매적립 포인트 환수 체크(임직원이 아닌 멤버쉽 회원인 경우만 체크)
		 */
		int clawbackPoint = 0; // 환수포인트

		if (UtilsText.equals(ocOrder.getMemberTypeCode(), CommonCode.MEMBER_TYPE_CODE_MEMBERSHIP)
				&& UtilsText.equals(ocOrder.getEmpYn(), Const.BOOLEAN_FALSE)) {

			// 환수할 포인트가 발생한 경우
			if (thisClaimVariationSavePoint < 0) {
				// 환수포인트 조회 인터페이스
				try {
					clawbackPoint = membershipPointService.getClawbackPoint(mbMember.getSafeKey(),
							mbMember.getSafeKeySeq(), ocOrder.getOrgOrderNo(), Math.abs(thisClaimVariationSavePoint));
				} catch (Exception e) {
					throw new Exception("validMsg:포인트 조회에 실패했습니다.");
				}
			}
		}

		/*
		 * 클레임 마스터 금액 업데이트(변동 적립포인트, 환수포인트 포함)
		 */
		OcClaim updateAmtClaim = new OcClaim();
		updateAmtClaim.setClmNo(ocClaim.getClmNo());
		updateAmtClaim.setTotalRfndAmt(ocClaimAmountVO.getRefundCnclAmt()); // 총환불금액
		updateAmtClaim.setTotalRedempAmt(ocClaimAmountVO.getRedempAmtByMultiBuy() + ocClaimAmountVO.getClaimDlvyAmt()); // 총환수금액
		updateAmtClaim.setIrdsSavePoint((int) ocClaimAmountVO.getVariationSavePoint()); // 증감적립포인트
		updateAmtClaim.setRedempSavePoint(clawbackPoint); // 환수적립포인트
		updateAmtClaim.setModerNo(LoginManager.getUserDetails().getAdminNo());

		ocClaimDao.updateClaimStat(updateAmtClaim);

		/*
		 * 환수프로모션비(다족구매로 인해 발생) 발생된 경우 결제히스토리 등록
		 */
		if (ocClaimAmountVO.getRedempAmtByMultiBuy() > 0) {
			for (OcClaimPayment redempMultiBuyPayment : ocClaimAmountVO.getRedempMultiBuyPaymentList()) {
				ocClaimPaymentDao.insertClaimPayment(redempMultiBuyPayment);
			}
		}

		/*
		 * 기존 다족구매 리오더 매출로 인해 변경된 금액과 원 주문 기준 취소상품 금액 차이 결제히스토리 등록(현재 취소금을 맞추기 위한 이력)
		 */
		if (ocClaimAmountVO.getAddMultiBuyDifferAmt() > 0) {
			for (OcClaimPayment addMultiBuyDifferPaymentList : ocClaimAmountVO.getAddMultiBuyDifferPaymentList()) {
				ocClaimPaymentDao.insertClaimPayment(addMultiBuyDifferPaymentList);
			}
		}

		/*
		 * 클레임 결제 등록
		 */
		// 주 결제 수단
		if (ocClaimAmountVO.getMainPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getMainPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getMainCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 기프트
		if (ocClaimAmountVO.getGiftPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getGiftPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getGiftCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 이벤트 포인트
		if (ocClaimAmountVO.getEventPointPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getEventPointPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getEventPointCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소
																									// 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 포인트
		if (ocClaimAmountVO.getPointPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getPointPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getPointCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로
																								// 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		return ocClaim;
	}

	/**
	 * @Desc : 환불계좌 인증
	 * @Method Name : setClaimAccountAuthProc
	 * @Date : 2019. 7. 18.
	 * @Author : KTH
	 * @param ocClaimCertificationHistory
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setClaimAccountAuthProc(OcClaimCertificationHistory ocClaimCertificationHistory)
			throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		AccountAuth accountAuth = new AccountAuth();

		MbMember mbMemeber = new MbMember();
		mbMemeber.setBankCode(ocClaimCertificationHistory.getBankCode());
		mbMemeber.setAcntNoText(ocClaimCertificationHistory.getAcntNoText());
		mbMemeber.setAcntHldrName(ocClaimCertificationHistory.getAcntHldrName());

		paramMap = memberService.setAccountParam(mbMemeber); // 계좌인증에 필요한 파라미터를 세팅
		Map<String, Object> result = accountAuth.start(paramMap);

		/*
		 * 인증이력 생성(실패, 성공 구분 없이 생성)
		 */
		String authSuccessYn = "N";
		if (UtilsObject.isNotEmpty(result.get("flag")) && !UtilsText.isEmpty((String) result.get("flag"))) {
			authSuccessYn = (String) result.get("flag");
		}

		ocClaimCertificationHistory.setCrtfcPathCode(CommonCode.CRTFC_PATH_CODE_REFUND_ACCOUNT);
		ocClaimCertificationHistory.setCrtfcTypeCode(CommonCode.CRTFC_TYPE_CODE_REFUND_ACCOUNT);
		ocClaimCertificationHistory.setAccessIpText(UtilsRequest.getRequest().getRemoteAddr());
		ocClaimCertificationHistory.setSessionId(UtilsRequest.getRequest().getSession().getId());
		ocClaimCertificationHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
		ocClaimCertificationHistory.setCrtfcSuccessYn(authSuccessYn); // 인증성공여부 set

		ocClaimCertificationHistoryDao.insert(ocClaimCertificationHistory); // 인증이력 생성

		return result;
	}

	/**
	 * @Desc : 분실 취소 처리(전체취소와 부분취소 확인 후 해당 메서드 호출)
	 * @Method Name : setMissCancelProc
	 * @Date : 2019. 8. 29.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocOrderProducts
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setMissCancelProc(OcClaim ocClaim, OcClaimProduct[] ocClaimProducts) throws Exception {

		OcClaimAmountVO ocClaimAmountVO = new OcClaimAmountVO();
		ocClaim.setClaimRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자 no set

		// addInfo4로 받은 은행정보를 bankCode로 전환
		List<SyCodeDetail> bankList = commonCodeService.getCodeNoName(CommonCode.BANK_CODE); // 은행코드 목록
		bankList = bankList.stream().filter(x -> UtilsObject.isNotEmpty(x.getAddInfo4()))
				.filter(x -> UtilsText.equals(ocClaim.getBankCode(), x.getAddInfo4())).collect(Collectors.toList());
		// 오직 하나만 남는다.
		String bankCode = bankList.get(0).getCodeDtlNo();
		// kcp코드를 은행코드로 대체
		ocClaim.setBankCode(bankCode);

		// 분실 취소는 리오더 생성 없음
		ocClaimAmountVO.setReOrderRegistPossible(false); // 취소 후 처리를 위해 set

		ocClaim.setOcClaimProductList(Arrays.asList(ocClaimProducts));

		for (OcClaimProduct claimProduct : ocClaim.getOcClaimProductList()) {
			claimProduct.setClmRsnCode(CommonCode.CLM_RSN_CODE_CANCEL_ETC);
			claimProduct.setClmEtcRsnText("분실취소");
		}

		// 클레임 금액계산 정보(전체/부분 취소 여부만 확인)
		claimProcService.setAllCancelCheck(ocClaim, ocClaimAmountVO);

		if (ocClaimAmountVO.isAllCancelYn()) {
			return this.setMissAllCancelProc(ocClaim, ocClaimAmountVO);
		} else {
			return this.setMissPartCancelProc(ocClaim, ocClaimAmountVO);
		}
	}

	/**
	 * @Desc : 분실 전체 취소 처리
	 * @Method Name : setMissAllCancelProc
	 * @Date : 2019. 8. 29.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocClaimAmountVO
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setMissAllCancelProc(OcClaim ocClaim, OcClaimAmountVO ocClaimAmountVO) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		/*
		 * [주문정보 get]
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보

		/*
		 * [주문 상품 결제 정보]
		 */
		OcOrderPayment ocOrderPayment = new OcOrderPayment();
		ocOrderPayment.setOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준

		List<OcOrderPayment> orderPaymentList = ocOrderPaymentDao.selectPaymentList(ocOrderPayment);

		/*
		 * [주문자 정보]
		 */
		MbMember mbMember = memberDao.selectMemberDefalutInfo(ocOrder.getMemberNo());

		/*
		 * [클레임 기본 등록정보 set]
		 */
		ocClaim.setSiteNo(ocOrder.getSiteNo()); // ocClaim 재 사용을 위해 siteNo set
		ocClaim.setMemberNo(ocOrder.getMemberNo()); // 인서트 시 주문정보를 이용하나 ocClaim 재 사용을 위해 set
		ocClaim.setClmGbnCode(CommonCode.CLM_GBN_CODE_CANCEL); // 클레임구분코드-취소
		ocClaim.setDeviceCode(ocOrder.getDeviceCode()); // 디바이스코드

		ocClaim.setRtrvlGbnType(CommonCode.RTRVL_GBN_TYPE_ONLINE); // 회수지구분(O:온라인)
		ocClaim.setRtrvlStoreNo(null); // 회수매장번호
		ocClaim.setRtrvlReqYn(Const.BOOLEAN_FALSE); // 회수신청여부

		ocClaim.setBuyerName(ocOrder.getBuyerName());
		ocClaim.setBuyerTelNoText(ocOrder.getBuyerTelNoText());
		ocClaim.setBuyerHdphnNoText(ocOrder.getBuyerHdphnNoText());
		ocClaim.setBuyerPostCodeText(ocOrder.getBuyerPostCodeText());
		ocClaim.setBuyerPostAddrText(ocOrder.getBuyerPostAddrText());
		ocClaim.setBuyerDtlAddrText(ocOrder.getBuyerDtlAddrText());

		ocClaim.setRcvrName(ocOrder.getRcvrName());
		ocClaim.setRcvrTelNoText(ocOrder.getRcvrTelNoText());
		ocClaim.setRcvrHdphnNoText(ocOrder.getRcvrHdphnNoText());
		ocClaim.setRcvrPostCodeText(ocOrder.getRcvrPostCodeText());
		ocClaim.setRcvrPostAddrText(ocOrder.getRcvrPostAddrText());
		ocClaim.setRcvrDtlAddrText(ocOrder.getRcvrDtlAddrText());

		ocClaim.setDlvyMemoText(ocOrder.getDlvyMemoText());
		ocClaim.setDlvyTypeCode(ocOrder.getDlvyTypeCode()); // 배송유형코드

		// 폼에서 넘어온 환불계좌 정보가 없을 경우 주문자 회원 정보에 환불계좌 정보가 있으면 set
		if (UtilsText.isEmpty(ocClaim.getBankCode()) || UtilsText.isEmpty(ocClaim.getRfndAcntText())
				|| UtilsText.isEmpty(ocClaim.getAcntHldrName())) {
			if (UtilsText.isNotEmpty(mbMember.getBankCode()) && UtilsText.isNotEmpty(mbMember.getAcntNoText())
					&& UtilsText.isNotEmpty(mbMember.getAcntHldrName())) {
				ocClaim.setBankCode(mbMember.getBankCode());
				ocClaim.setRfndAcntText(mbMember.getAcntNoText());
				ocClaim.setAcntHldrName(mbMember.getAcntHldrName());
			}
		}

		ocClaim.setVndrNo(ocClaim.getOcClaimProductList().get(0).getVndrNo()); // 업체번호(클레임상품 중 한개 발췌-취소는 상품단위 의미없음)

		ocClaim.setTotalRfndAmt(ocOrder.getPymntAmt()); // 총환불금액
		ocClaim.setTotalRedempAmt(0); // 총환수금액

		ocClaim.setUnProcYn(Const.BOOLEAN_FALSE); // 미처리 여부 set
		ocClaim.setAdminAcceptYn(Const.BOOLEAN_FALSE); // 관리자접수여부
		ocClaim.setOflnAcceptYn(Const.BOOLEAN_FALSE); // 오프라인접수여부

		// 추가배송비 관련 set
		ocClaim.setAddDlvyAmtPymntType(null); // 추가배송비결제방법
		ocClaim.setAddDlvyAmt(0); // 추가배송비
		ocClaim.setHoldCpnSeq(null); // 보유쿠폰순번

		ocClaim.setClmStatCode(CommonCode.CLM_STAT_CODE_CANCEL_ACCEPT); // 클레임상태코드 - 취소접수
		ocClaim.setBranchMoveCode(CommonCode.BRANCH_MOVE_CODE_ORIGIN_DEAL); // 점간이동코드 - 원거래

		ocClaim.setRgsterNo(ocClaim.getClaimRgsterNo()); // 등록자
		ocClaim.setModerNo(ocClaim.getClaimRgsterNo()); // 수정자

		/*
		 * [클레임 등록(마스터)] 등록 시 생성된 clmNo 를 ocClaim 에서 가지고 있음
		 */
		ocClaimDao.insertClaimInfo(ocClaim);

		/*
		 * [클레임상품 등록] [클레임상태이력 등록]
		 */
		for (OcClaimProduct ocClaimProduct : ocClaim.getOcClaimProductList()) {
			// 클레임상품 등록, 클레임상태이력 등록
			this.insertClaimPrdtAndClaimStatusHistory(ocClaim, ocClaimProduct,
					CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT);

			// 해당 상품에 사은품이 있는 경우 사은품 한번 더 등록
			if (!ObjectUtils.isEmpty(ocClaimProduct.getOrderGiftPrdtSeq())) {
				ocClaimProduct.setOrderPrdtSeq(ocClaimProduct.getOrderGiftPrdtSeq()); // 사은품 주문상품순번으로 대체
				this.insertClaimPrdtAndClaimStatusHistory(ocClaim, ocClaimProduct,
						CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT);
			}
		}

		/*
		 * 클레임 상품에 배송비 등록
		 */
		for (OcOrderProduct orderProduct : ocClaimAmountVO.getOrgOrderProductList()) {
			if (UtilsText.equals(orderProduct.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY)) { // 배송비
				OcClaimProduct dlvyClaimProduct = new OcClaimProduct();

				dlvyClaimProduct.setOrderPrdtSeq(orderProduct.getOrderPrdtSeq());
				dlvyClaimProduct.setClmRsnCode(ocClaim.getOcClaimProductList().get(0).getClmRsnCode());
				dlvyClaimProduct.setClmEtcRsnText(ocClaim.getOcClaimProductList().get(0).getClmEtcRsnText());

				// 클레임상품 등록, 클레임상태이력 등록
				this.insertClaimPrdtAndClaimStatusHistory(ocClaim, dlvyClaimProduct,
						CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT);
			}
		}

		/*
		 * 클레임 금액 계산(클레임 마스터/상품 등록 후 호출) - 배송비까지 등록 후 호출
		 */
		log.error("분실 전체 취소 처리 금액계산  START ClmNo => {}", ocClaim.getClmNo());
		claimProcService.setClaimAmountCalcForAllCancel(ocClaim, ocOrder, ocClaimAmountVO);

		/*
		 * 원 주문 마스터/상품 상태 값 변경
		 */
		OcOrderProduct updateOrderProduct = new OcOrderProduct();
		updateOrderProduct.setOrderNo(ocOrder.getOrderNo());
		updateOrderProduct.setModerNo(ocClaim.getClaimRgsterNo());
		updateOrderProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_ACCEPT); // 주문상품상태코드 : 취소접수

		ocOrderProductDao.updateOrderProductForCalim(updateOrderProduct); // 원 주문상품 상태 값 변경

		OcOrder updateOrder = new OcOrder();
		updateOrder.setOrderNo(ocOrder.getOrderNo());
		updateOrder.setOrderStatCode(CommonCode.ORDER_STAT_CODE_CANCEL_ACCEPT); // 주문상태코드 : 전체취소접수
		updateOrder.setCnclAmt(ocOrder.getPymntAmt());
		updateOrder.setClmNo(ocClaim.getClmNo());
		updateOrder.setModerNo(ocClaim.getClaimRgsterNo());

		ocOrderDao.updateOrderForClaim(updateOrder); // 원 주문 상태 값 변경

		/*
		 * 원 주문의 주문상품이력 등록(취소접수)
		 */
		for (OcOrderProduct orderProduct : ocClaimAmountVO.getOrgOrderProductList()) {
			OcOrderProductHistory orgOrderProductHistory = new OcOrderProductHistory();
			orgOrderProductHistory.setOrderNo(orderProduct.getOrderNo());
			orgOrderProductHistory.setOrderPrdtSeq(orderProduct.getOrderPrdtSeq());
			orgOrderProductHistory.setPrdtNo(orderProduct.getPrdtNo());
			orgOrderProductHistory.setPrdtOptnNo(orderProduct.getPrdtOptnNo());
			orgOrderProductHistory.setPrdtName(orderProduct.getPrdtName());
			orgOrderProductHistory.setEngPrdtName(orderProduct.getEngPrdtName());
			orgOrderProductHistory.setOptnName(orderProduct.getOptnName());
			orgOrderProductHistory.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_ACCEPT); // 주문상품상태코드:취소접수
			orgOrderProductHistory.setNoteText(null);
			orgOrderProductHistory.setRgsterNo(ocClaim.getClaimRgsterNo());

			ocOrderProductHistoryDao.insertProductHistory(orgOrderProductHistory); // 주문상품이력 생성
		}

		/*
		 * 주문 사용쿠폰 클레임번호 업데이트
		 */
		OcOrderUseCoupon orderUseCoupon = new OcOrderUseCoupon();
		orderUseCoupon.setOrderNo(ocClaim.getOrderNo());
		orderUseCoupon.setClmNo(ocClaim.getClmNo());

		ocOrderUseCouponDao.updateOrderUseCouponClmNo(orderUseCoupon); // 주문 사용쿠폰 클레임번호 업데이트

		/*
		 * 사용 쿠폰 재 발급
		 */
		List<Integer> holdCpnSeqs = ocClaimAmountVO.getOrderUseCouponList().stream()
				.map(OcOrderUseCoupon::getHoldCpnSeq).distinct().collect(Collectors.toList()); // 중복 쿠폰 제거
		for (Integer holdCpnSeq : holdCpnSeqs) {
			MbMemberCoupon reIssueMemberCoupon = new MbMemberCoupon();
			reIssueMemberCoupon.setMemberNo(ocOrder.getMemberNo());
			reIssueMemberCoupon.setHoldCpnSeq(holdCpnSeq);
			reIssueMemberCoupon.setReIssueRsnText("주문취소 재 발급");
			reIssueMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_REISSUANCE);
			reIssueMemberCoupon.setRgsterNo(Const.SYSTEM_ADMIN_NO);
			reIssueMemberCoupon.setModerNo(Const.SYSTEM_ADMIN_NO);

			mbMemberCouponDao.insertMemberCouponReIssue(reIssueMemberCoupon); // 쿠폰 재 발급
		}

		/*
		 * 주문상품 프로모션 클레임번호 업데이트
		 */
		OcOrderProductApplyPromotion ocOrderProductApplyPromotion = new OcOrderProductApplyPromotion();
		ocOrderProductApplyPromotion.setOrderNo(ocClaim.getOrderNo());
		ocOrderProductApplyPromotion.setClmNo(ocClaim.getClmNo());

		ocOrderProductApplyPromotionDao.updateOrderProductApplyPromotionClmNo(ocOrderProductApplyPromotion);

		/*
		 * 주문배송이력 상태 변경
		 */
		OcOrderDeliveryHistory orderDeliveryHistory = new OcOrderDeliveryHistory();
		orderDeliveryHistory.setOrderNo(ocClaim.getOrderNo());
		orderDeliveryHistory.setDlvyStatCode(CommonCode.DLVY_STAT_CODE_DELIVERY_CANCEL); // 배송 상태 코드 : 배송취소
		orderDeliveryHistory.setMissProcYn(Const.BOOLEAN_TRUE); // 분실처리여부
		orderDeliveryHistory.setMissProcTypeCode(CommonCode.MISS_PROC_TYPE_CODE_MISS_CANCEL); // 배송중단사유코드
		orderDeliveryHistory.setRgsterNo(ocClaim.getRgsterNo());
		orderDeliveryHistory.setModerNo(ocClaim.getModerNo());
		ocOrderDeliveryHistoryDao.updateOrderDeliveryHistoryStat(orderDeliveryHistory);

		/*
		 * 클레임 결제 등록
		 */
		// 주 결제 수단
		if (ocClaimAmountVO.getMainPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getMainPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getMainPayment().getPymntAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 기프트
		if (ocClaimAmountVO.getGiftPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getGiftPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getGiftPayment().getPymntAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 이벤트 포인트
		if (ocClaimAmountVO.getEventPointPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getEventPointPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getEventPointPayment().getPymntAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소
																									// 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 포인트
		if (ocClaimAmountVO.getPointPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getPointPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getPointPayment().getPymntAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로
																								// 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		/*
		 * 임직원 주문인 경우 임직원 한도 복원
		 */
		if (UtilsText.equals(ocOrder.getEmpOrderYn(), Const.BOOLEAN_TRUE)) {
			String orderYearMonth = new SimpleDateFormat(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS)
					.format(ocOrder.getOrderDtm());
			String orderDate = new SimpleDateFormat(Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS)
					.format(ocOrder.getOrderDtm());
			String thisYearMonth = new SimpleDateFormat(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS)
					.format(new Date());

			// 주문월과 해당월이 같을 경우만 한도복원 인터페이스 호출
			if (UtilsText.equals(orderYearMonth, thisYearMonth)) {
				MbEmployeeCertificationHistory employeeInfo = mbEmployeeCertificationHistoryDao
						.selectEmpCertfiHistory(mbMember.getMemberNo());

				EmployPoint returnEmployPointResult = membershipPointService.savePointByEmployNum("1", orderDate,
						employeeInfo.getEmpNoText(), String.valueOf(ocOrder.getPymntAmt()), Const.SALE_EMP_CD);
			}
		}

		/*
		 * 결제취소
		 */
		// 결제취소시 환불 발생여부(refundOccurrence) ocClaimAmountVO 에 set
		claimProcService.setCancelPayment(ocClaimAmountVO, ocClaim, mbMember);

		/*
		 * 결제취소 처리 후 처리 주문, 클레임 상태 값 후 처리
		 */
		claimProcService.setClaimCancelAfterProc(ocClaim, ocClaimAmountVO);

		if (ocClaimAmountVO.isRefundOccurrence()) {
			resultMap.put("success", Const.BOOLEAN_FALSE);
			resultMap.put("message", "취소 처리는 완료되었으나\n\n결제취소 실패가 발생하여 환불접수 처리되었습니다.");
			resultMap.put("clmNo", ocClaim.getClmNo());
		} else {
			resultMap.put("success", Const.BOOLEAN_TRUE);
			resultMap.put("message", "주문이 정상적으로 취소되었습니다.");
			resultMap.put("clmNo", ocClaim.getClmNo());
		}

		// 이메일 , 카톡 문자 알림 서비스 시작
		try {
			claimMessageService.orderAllCancel(ocClaim);
		} catch (Exception e) {
			log.error("주문전체취소 메세지 메일 발송 실패 : " + e.getMessage());
		}

		return resultMap;
	}

	/**
	 * @Desc : 분실 부분 취소 처리
	 * @Method Name : setMissPartCancelProc
	 * @Date : 2019. 8. 29.
	 * @Author : KTH
	 * @param ocClaim
	 * @param ocClaimAmountVO
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setMissPartCancelProc(OcClaim ocClaim, OcClaimAmountVO ocClaimAmountVO)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		/*
		 * [파라미터로 넘어온 클레임 대상 주문상품 순번 배열] - 파라미터 클레임 상품 대상은 사은품과 배송비가 없음
		 */
		Object[] claimOrderPrdtSeqs = ocClaim.getOcClaimProductList().stream().map(OcClaimProduct::getOrderPrdtSeq)
				.toArray();
		;

		/*
		 * [원 주문정보 get]
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보

		/*
		 * [주문자 정보]
		 */
		MbMember mbMember = memberDao.selectMemberDefalutInfo(ocOrder.getMemberNo());

		/*
		 * [기본 등록정보 set]
		 */
		ocClaim.setSiteNo(ocOrder.getSiteNo()); // ocClaim 재 사용을 위해 siteNo set
		ocClaim.setMemberNo(ocOrder.getMemberNo()); // 인서트 시 주문정보를 이용하나 ocClaim 재 사용을 위해 set
		ocClaim.setClmGbnCode(CommonCode.CLM_GBN_CODE_CANCEL); // 클레임구분코드-취소
		ocClaim.setDeviceCode(ocOrder.getDeviceCode()); // 디바이스코드

		ocClaim.setRtrvlGbnType(CommonCode.RTRVL_GBN_TYPE_ONLINE); // 회수지구분(O:온라인)
		ocClaim.setRtrvlStoreNo(null); // 회수매장번호
		ocClaim.setRtrvlReqYn(Const.BOOLEAN_FALSE); // 회수신청여부

		ocClaim.setBuyerName(ocOrder.getBuyerName());
		ocClaim.setBuyerTelNoText(ocOrder.getBuyerTelNoText());
		ocClaim.setBuyerHdphnNoText(ocOrder.getBuyerHdphnNoText());
		ocClaim.setBuyerPostCodeText(ocOrder.getBuyerPostCodeText());
		ocClaim.setBuyerPostAddrText(ocOrder.getBuyerPostAddrText());
		ocClaim.setBuyerDtlAddrText(ocOrder.getBuyerDtlAddrText());

		ocClaim.setRcvrName(ocOrder.getRcvrName());
		ocClaim.setRcvrTelNoText(ocOrder.getRcvrTelNoText());
		ocClaim.setRcvrHdphnNoText(ocOrder.getRcvrHdphnNoText());
		ocClaim.setRcvrPostCodeText(ocOrder.getRcvrPostCodeText());
		ocClaim.setRcvrPostAddrText(ocOrder.getRcvrPostAddrText());
		ocClaim.setRcvrDtlAddrText(ocOrder.getRcvrDtlAddrText());

		ocClaim.setDlvyMemoText(ocOrder.getDlvyMemoText());
		ocClaim.setDlvyTypeCode(ocOrder.getDlvyTypeCode()); // 배송유형코드

		// 폼에서 넘어온 환불계좌 정보가 없을 경우 주문자 회원 정보에 환불계좌 정보가 있으면 set
		if (UtilsText.isEmpty(ocClaim.getBankCode()) || UtilsText.isEmpty(ocClaim.getRfndAcntText())
				|| UtilsText.isEmpty(ocClaim.getAcntHldrName())) {
			if (UtilsText.isNotEmpty(mbMember.getBankCode()) && UtilsText.isNotEmpty(mbMember.getAcntNoText())
					&& UtilsText.isNotEmpty(mbMember.getAcntHldrName())) {
				ocClaim.setBankCode(mbMember.getBankCode());
				ocClaim.setRfndAcntText(mbMember.getAcntNoText());
				ocClaim.setAcntHldrName(mbMember.getAcntHldrName());
			}
		}

		ocClaim.setVndrNo(ocClaim.getOcClaimProductList().get(0).getVndrNo()); // 업체번호(클레임상품 중 한개 발췌-취소는 상품단위 의미없음)

		ocClaim.setTotalRfndAmt(0); // 총환불금액(결제취소 이후 후처리 시 업데이트)
		ocClaim.setTotalRedempAmt(0); // 총환수금액(결제취소 이후 후처리 시 업데이트)

		ocClaim.setUnProcYn(Const.BOOLEAN_FALSE); // 미처리 여부 set
		ocClaim.setAdminAcceptYn(Const.BOOLEAN_FALSE); // 관리자접수여부
		ocClaim.setOflnAcceptYn(Const.BOOLEAN_FALSE); // 오프라인접수여부

		// 추가배송비 관련 set
		ocClaim.setAddDlvyAmtPymntType(null); // 추가배송비결제방법
		ocClaim.setAddDlvyAmt(0); // 추가배송비
		ocClaim.setHoldCpnSeq(null); // 보유쿠폰순번

		ocClaim.setClmStatCode(CommonCode.CLM_STAT_CODE_CANCEL_ACCEPT); // 클레임상태코드 - 취소접수
		ocClaim.setBranchMoveCode(CommonCode.BRANCH_MOVE_CODE_ORIGIN_DEAL); // 점간이동코드 - 원거래

		ocClaim.setRgsterNo(ocClaim.getClaimRgsterNo()); // 등록자
		ocClaim.setModerNo(ocClaim.getClaimRgsterNo()); // 수정자

		/*
		 * [클레임 등록(마스터)] 등록 시 생성된 clmNo 를 ocClaim 에서 가지고 있음
		 */
		ocClaimDao.insertClaimInfo(ocClaim);

		/*
		 * [클레임상품 등록] [클레임상태이력 등록]
		 */
		for (OcClaimProduct ocClaimProduct : ocClaim.getOcClaimProductList()) {
			// 클레임상품 등록, 클레임상태이력 등록
			this.insertClaimPrdtAndClaimStatusHistory(ocClaim, ocClaimProduct,
					CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT);

			// 해당 상품에 사은품이 있는 경우 사은품 한번 더 등록
			if (!ObjectUtils.isEmpty(ocClaimProduct.getOrderGiftPrdtSeq())) {
				ocClaimProduct.setOrderPrdtSeq(ocClaimProduct.getOrderGiftPrdtSeq()); // 사은품 주문상품순번으로 대체
				this.insertClaimPrdtAndClaimStatusHistory(ocClaim, ocClaimProduct,
						CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT);
			}
		}

		/*************************************
		 * 클레임 금액 계산(클레임 마스터/상품 등록 후 호출)
		 *************************************/
		// ocClaimAmountVO 에 계산된 금액 set
		log.error("분실 부분 취소 처리 금액계산  START ClmNo => {}", ocClaim.getClmNo());
		claimProcService.setClaimAmountCalcForPartCancel(ocClaim, ocOrder, ocClaimAmountVO,
				CommonCode.CLM_GBN_CODE_CANCEL, true);

		/*
		 * 환불 배송비 발생된 경우 클레임 상품에 배송비 등록
		 */
		if (!ObjectUtils.isEmpty(ocClaimAmountVO.getCancelDlvyProductList())) {
			for (OcOrderProduct orderProduct : ocClaimAmountVO.getCancelDlvyProductList()) {
				OcClaimProduct dlvyClaimProduct = new OcClaimProduct();

				dlvyClaimProduct.setOrderPrdtSeq(orderProduct.getOrderPrdtSeq());
				dlvyClaimProduct.setClmRsnCode(ocClaim.getOcClaimProductList().get(0).getClmRsnCode());
				dlvyClaimProduct.setClmEtcRsnText(ocClaim.getOcClaimProductList().get(0).getClmEtcRsnText());

				// 클레임상품 등록, 클레임상태이력 등록
				this.insertClaimPrdtAndClaimStatusHistory(ocClaim, dlvyClaimProduct,
						CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT);
			}
		}

		/*
		 * 클레임 계산 결과 환수금이 더 많은 경우 취소 불가 처리
		 */
		if (ocClaimAmountVO.getRefundCnclAmt() < 0) {
			throw new Exception("validMsg:환수금이 발생하여 취소가 불가합니다.");
		}

		/*************************************
		 * 환불 배송비까지 등록된 이후 목록 재 조회
		 *************************************/
		/*
		 * [원 주문에 걸린 모든 클레임 - 현재 클레임으로 인해 등록된 사은품/배송비 모두 포함]
		 */
		OcClaimProduct ocClaimProduct = new OcClaimProduct();
		ocClaimProduct.setOrgOrderNo(ocClaim.getOrgOrderNo()); // 원주문번호 기준
		ocClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 상품 코드
		List<OcClaimProduct> orderAllClaimProductList = ocClaimProductDao.selectOrgClaimProductList(ocClaimProduct);

		ocClaimAmountVO.setOrderAllClaimProductList(orderAllClaimProductList); // 다시 set

		/*
		 * [현재 클레임 상품 목록 - 현재 클레임으로 인해 등록된 사은품/배송비 모두 포함]
		 */
		List<OcClaimProduct> thisTimeClaimProductList = orderAllClaimProductList.stream()
				.filter(x -> UtilsText.equals(x.getClmNo(), ocClaim.getClmNo())).collect(Collectors.toList());

		/*
		 * 현재 클레임 상품 목록의 금액 정보를 매출기준 리오더 금액으로 변경(다족구매로 인한 주문금액 변경 고려)
		 */
		for (OcClaimProduct thisTimeClaimProduct : thisTimeClaimProductList) {
			OcOrderProduct reOrderSalesProduct = ocClaimAmountVO.getReOrderProductSalesList().stream()
					.filter(x -> UtilsText.equals(String.valueOf(x.getOrderPrdtSeq()),
							String.valueOf(thisTimeClaimProduct.getOrderPrdtSeq())))
					.max(Comparator.comparing(OcOrderProduct::getOrderNo)).orElse(null);

			if (reOrderSalesProduct != null) {
				thisTimeClaimProduct.setTotalDscntAmt(reOrderSalesProduct.getTotalDscntAmt());
				thisTimeClaimProduct.setOrderAmt(reOrderSalesProduct.getOrderAmt());
			}
		}

		// 현재 클레임 금액 변경 후 다시 set
		ocClaimAmountVO.setThisTimeClaimProductList(thisTimeClaimProductList);

		/*
		 * 원 주문 마스터/상품 상태 값 변경
		 */
		// 원 주문 상품상태 업데이트
		for (OcClaimProduct claimProduct : thisTimeClaimProductList) {
			OcOrderProduct updateOrderProduct = new OcOrderProduct();
			updateOrderProduct.setOrderNo(claimProduct.getOrderNo());
			updateOrderProduct.setOrderPrdtSeq(claimProduct.getOrderPrdtSeq());
			updateOrderProduct.setModerNo(ocClaim.getClaimRgsterNo());
			updateOrderProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_ACCEPT); // 주문상품상태코드 : 취소접수

			ocOrderProductDao.updateOrderProductForCalim(updateOrderProduct); // 원 주문상품 상태 값 변경
		}

		// 남은 상품 모두 취소인 경우 원 주문 마스터 상태 업데이트
		if (ocClaimAmountVO.isRemainAllCancelYn()) {
			OcOrder updateOrder = new OcOrder();
			updateOrder.setOrderNo(ocOrder.getOrderNo());
			updateOrder.setOrderStatCode(CommonCode.ORDER_STAT_CODE_CANCEL_ACCEPT); // 주문상태코드 : 전체취소접수
			updateOrder.setClmNo(ocClaim.getClmNo());
			updateOrder.setModerNo(ocClaim.getClaimRgsterNo());

			ocOrderDao.updateOrderForClaim(updateOrder); // 원 주문 상태 값 변경
		}

		// 원 주문의 주문상품이력 등록(취소접수)
		for (OcClaimProduct claimProduct : thisTimeClaimProductList) {
			OcOrderProductHistory orgOrderProductHistory = new OcOrderProductHistory();
			orgOrderProductHistory.setOrderNo(claimProduct.getOrderNo());
			orgOrderProductHistory.setOrderPrdtSeq(claimProduct.getOrderPrdtSeq());
			orgOrderProductHistory.setPrdtNo(claimProduct.getPrdtNo());
			orgOrderProductHistory.setPrdtOptnNo(claimProduct.getPrdtOptnNo());
			orgOrderProductHistory.setPrdtName(claimProduct.getPrdtName());
			orgOrderProductHistory.setEngPrdtName(claimProduct.getEngPrdtName());
			orgOrderProductHistory.setOptnName(claimProduct.getOptnName());
			orgOrderProductHistory.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_ACCEPT); // 주문상품상태코드:취소접수
			orgOrderProductHistory.setNoteText(null);
			orgOrderProductHistory.setRgsterNo(ocClaim.getClaimRgsterNo());

			ocOrderProductHistoryDao.insertProductHistory(orgOrderProductHistory); // 주문상품이력 생성
		}

		/*
		 * 다족구매로 인해 재조정 되는 프로모션 변경 정보 등록
		 */
		if (!ObjectUtils.isEmpty(ocClaimAmountVO.getAdjustedClaimProductApplyPromotionList())) {
			for (OcClaimProductApplyPromotion claimProductApplyPromotion : ocClaimAmountVO
					.getAdjustedClaimProductApplyPromotionList()) {

				// 클레임상품적용프로모션 등록
				ocClaimProductApplyPromotionDao.insert(claimProductApplyPromotion);
			}
		}

		/*
		 * 사용 쿠폰 재 발급, 주문 사용쿠폰 클레임번호 업데이트
		 */
		// 원주문 사용 쿠폰 목록에서 현재 클레임 대상 주문상품에 해당하는 쿠폰사용 정보 목록(이전 클레임으로 인한 항목 제외)
		List<OcOrderUseCoupon> claimProductUserCouponList = ocClaimAmountVO.getOrderUseCouponList().stream()
				.filter(x -> Arrays.asList(claimOrderPrdtSeqs).contains(x.getOrderPrdtSeq()))
				.filter(x -> UtilsText.equals(x.getClmNo(), null)).collect(Collectors.toList());

		// 원주문 사용 쿠폰 목록에서 현재 클레임 대상 주문상품을 제외한 남은 상품 쿠폰사용 정보 목록(이전 클레임으로 인한 항목 제외)
		List<OcOrderUseCoupon> excludeClaimProductUserCouponList = ocClaimAmountVO.getOrderUseCouponList().stream()
				.filter(x -> !Arrays.asList(claimOrderPrdtSeqs).contains(x.getOrderPrdtSeq()))
				.filter(x -> UtilsText.equals(x.getClmNo(), null)).collect(Collectors.toList());

		// 현재 클레임 대상 중복 쿠폰 제거
		List<Integer> claimHoldCpnSeqs = claimProductUserCouponList.stream().map(OcOrderUseCoupon::getHoldCpnSeq)
				.distinct().collect(Collectors.toList());

		// 재 발급한 쿠폰 목록(체크용)
		List<Integer> reIssueHoldCpnSeq = new ArrayList<Integer>();

		// 이전 클레임 부분 취소 후 남은 상품 모두 취소인 경우
		if (ocClaimAmountVO.isRemainAllCancelYn()) {
			// 더블적립 쿠폰 처리(주문에 걸린 쿠폰이므로 상품기준과 다르게 별도 처리)
			if (ocClaimAmountVO.getOrderDoubleDscntCpnInfo() != null) {
				OcOrderUseCoupon orderUseCoupon = new OcOrderUseCoupon();
				orderUseCoupon.setOrderNo(ocClaim.getOrderNo());
				orderUseCoupon.setClmNo(ocClaim.getClmNo());
				orderUseCoupon.setHoldCpnSeq(ocClaimAmountVO.getOrderDoubleDscntCpnInfo().getHoldCpnSeq());

				ocOrderUseCouponDao.updateOrderUseCouponClmNo(orderUseCoupon); // 주문 사용쿠폰 클레임번호 업데이트

				MbMemberCoupon reIssueMemberCoupon = new MbMemberCoupon();
				reIssueMemberCoupon.setMemberNo(ocOrder.getMemberNo());
				reIssueMemberCoupon.setHoldCpnSeq(ocClaimAmountVO.getOrderDoubleDscntCpnInfo().getHoldCpnSeq());
				reIssueMemberCoupon.setReIssueRsnText("주문취소 재 발급");
				reIssueMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_REISSUANCE);
				reIssueMemberCoupon.setRgsterNo(Const.SYSTEM_ADMIN_NO);
				reIssueMemberCoupon.setModerNo(Const.SYSTEM_ADMIN_NO);

				mbMemberCouponDao.insertMemberCouponReIssue(reIssueMemberCoupon); // 쿠폰 재 발급
			}

			// 상품단위에 걸린 holdCpnSeq 기준으로 재 발급
			for (Integer holdCpnSeq : claimHoldCpnSeqs) {
				OcOrderUseCoupon orderUseCoupon = new OcOrderUseCoupon();
				orderUseCoupon.setOrderNo(ocClaim.getOrderNo());
				orderUseCoupon.setClmNo(ocClaim.getClmNo());
				orderUseCoupon.setHoldCpnSeq(holdCpnSeq);

				ocOrderUseCouponDao.updateOrderUseCouponClmNo(orderUseCoupon); // 주문 사용쿠폰 클레임번호 업데이트

				MbMemberCoupon reIssueMemberCoupon = new MbMemberCoupon();
				reIssueMemberCoupon.setMemberNo(ocOrder.getMemberNo());
				reIssueMemberCoupon.setHoldCpnSeq(holdCpnSeq);
				reIssueMemberCoupon.setReIssueRsnText("주문취소 재 발급");
				reIssueMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_REISSUANCE);
				reIssueMemberCoupon.setRgsterNo(Const.SYSTEM_ADMIN_NO);
				reIssueMemberCoupon.setModerNo(Const.SYSTEM_ADMIN_NO);

				mbMemberCouponDao.insertMemberCouponReIssue(reIssueMemberCoupon); // 쿠폰 재 발급
			}
		} else {
			for (OcOrderUseCoupon claimProductUserCoupon : claimProductUserCouponList) {
				// 취소대상 상품에 적용된 쿠폰이 할인유형 쿠폰인 경우
				if (UtilsText.equals(claimProductUserCoupon.getCpnTypeCode(), CommonCode.CPN_TYPE_CODE_DISCOUNT)
						|| UtilsText.equals(claimProductUserCoupon.getCpnTypeCode(),
								CommonCode.CPN_TYPE_CODE_DISCOUNT_AFFILIATES)
						|| UtilsText.equals(claimProductUserCoupon.getCpnTypeCode(),
								CommonCode.CPN_TYPE_CODE_DOUBLE_POINT)) {

					// 취소대상이 아닌 상품에 적용된 동일 할인유형 쿠폰번호 존재 확인
					long existCpnCnt = excludeClaimProductUserCouponList.stream()
							.filter(x -> UtilsText.equals(String.valueOf(claimProductUserCoupon.getHoldCpnSeq()),
									String.valueOf(x.getHoldCpnSeq())))
							.count();

					OcOrderUseCoupon orderUseCoupon = new OcOrderUseCoupon();
					orderUseCoupon.setOrderNo(ocClaim.getOrderNo());
					orderUseCoupon.setClmNo(ocClaim.getClmNo());
					orderUseCoupon.setOrderUseCpnSeq(claimProductUserCoupon.getOrderUseCpnSeq());

					ocOrderUseCouponDao.updateOrderUseCouponClmNo(orderUseCoupon); // 주문 사용쿠폰 클레임번호 업데이트

					// 남아있는 상품에 동일 할인쿠폰이 없는 경우에만 쿠폰 재 발급
					if (existCpnCnt == 0) {
						// 재 발급한 쿠폰 목록에 없다면 재 발급 진행
						if (!reIssueHoldCpnSeq.contains(claimProductUserCoupon.getHoldCpnSeq())) {
							MbMemberCoupon reIssueMemberCoupon = new MbMemberCoupon();
							reIssueMemberCoupon.setMemberNo(ocOrder.getMemberNo());
							reIssueMemberCoupon.setHoldCpnSeq(claimProductUserCoupon.getHoldCpnSeq());
							reIssueMemberCoupon.setReIssueRsnText("주문취소 재 발급");
							reIssueMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_REISSUANCE);
							reIssueMemberCoupon.setRgsterNo(Const.SYSTEM_ADMIN_NO);
							reIssueMemberCoupon.setModerNo(Const.SYSTEM_ADMIN_NO);

							mbMemberCouponDao.insertMemberCouponReIssue(reIssueMemberCoupon); // 쿠폰 재 발급
							reIssueHoldCpnSeq.add(claimProductUserCoupon.getHoldCpnSeq()); // 재 발급 쿠폰 목록에 추가
						}
					}
				}
			}
		}

		// 환불 대상 배송비가 발생한 경우 해당 배송비 상품에 적용된 쿠폰 재 발급(업체번호 확인을 위해 별도로 처리)
		if (!ObjectUtils.isEmpty(ocClaimAmountVO.getCancelDlvyProductList())) {
			for (OcOrderProduct orderProduct : ocClaimAmountVO.getCancelDlvyProductList()) {
				// 배송비무료타입, 업체번호를 확인하여 업체별로 적용된 쿠폰을 추출
				OcOrderUseCoupon dlvyFreeCpn = claimProductUserCouponList.stream()
						.filter(x -> UtilsText.equals(x.getCpnTypeCode(), CommonCode.CPN_TYPE_CODE_FREE_DELIVERY))
						.filter(x -> UtilsText.equals(x.getVndrNo(), orderProduct.getVndrNo())).findFirst()
						.orElse(null);

				if (dlvyFreeCpn != null) {
					OcOrderUseCoupon orderUseCoupon = new OcOrderUseCoupon();
					orderUseCoupon.setOrderNo(ocClaim.getOrderNo());
					orderUseCoupon.setClmNo(ocClaim.getClmNo());
					orderUseCoupon.setHoldCpnSeq(dlvyFreeCpn.getHoldCpnSeq());

					ocOrderUseCouponDao.updateOrderUseCouponClmNo(orderUseCoupon); // 주문 사용쿠폰 클레임번호 업데이트

					MbMemberCoupon reIssueMemberCoupon = new MbMemberCoupon();
					reIssueMemberCoupon.setMemberNo(ocOrder.getMemberNo());
					reIssueMemberCoupon.setHoldCpnSeq(dlvyFreeCpn.getHoldCpnSeq());
					reIssueMemberCoupon.setReIssueRsnText("주문취소 재 발급");
					reIssueMemberCoupon.setCpnStatCode(CommonCode.CPN_STAT_CODE_REISSUANCE);
					reIssueMemberCoupon.setRgsterNo(Const.SYSTEM_ADMIN_NO);
					reIssueMemberCoupon.setModerNo(Const.SYSTEM_ADMIN_NO);

					mbMemberCouponDao.insertMemberCouponReIssue(reIssueMemberCoupon); // 쿠폰 재 발급
				}
			}
		}

		/*
		 * 주문상품 프로모션 클레임번호 업데이트
		 */
		for (OcClaimProduct claimProduct : thisTimeClaimProductList) {
			OcOrderProductApplyPromotion ocOrderProductApplyPromotion = new OcOrderProductApplyPromotion();
			ocOrderProductApplyPromotion.setOrderNo(claimProduct.getOrderNo());
			ocOrderProductApplyPromotion.setClmNo(claimProduct.getClmNo());
			ocOrderProductApplyPromotion.setOrderPrdtSeq(claimProduct.getOrderPrdtSeq());

			ocOrderProductApplyPromotionDao.updateOrderProductApplyPromotionClmNo(ocOrderProductApplyPromotion);
		}

		/*
		 * 주문배송이력 상태 변경
		 */
		for (OcClaimProduct claimProduct : thisTimeClaimProductList) {
			OcOrderDeliveryHistory orderDeliveryHistory = new OcOrderDeliveryHistory();
			orderDeliveryHistory.setOrderNo(ocClaim.getOrderNo());
			orderDeliveryHistory.setOrderPrdtSeq(claimProduct.getOrderPrdtSeq());
			orderDeliveryHistory.setDlvyStatCode(CommonCode.DLVY_STAT_CODE_DELIVERY_CANCEL); // 배송 상태 코드 : 배송취소
			orderDeliveryHistory.setMissProcYn(Const.BOOLEAN_TRUE); // 분실처리여부
			orderDeliveryHistory.setMissProcTypeCode(CommonCode.MISS_PROC_TYPE_CODE_MISS_CANCEL); // 배송중단사유코드
			orderDeliveryHistory.setRgsterNo(ocClaim.getClaimRgsterNo());
			orderDeliveryHistory.setModerNo(ocClaim.getClaimRgsterNo());
			ocOrderDeliveryHistoryDao.updateOrderDeliveryHistoryStat(orderDeliveryHistory);
		}

		/*
		 * 클레임 결제 등록
		 */
		// 주 결제 수단
		if (ocClaimAmountVO.getMainPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getMainPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getMainCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getMainPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 기프트
		if (ocClaimAmountVO.getGiftPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getGiftPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getGiftCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getGiftPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 이벤트 포인트
		if (ocClaimAmountVO.getEventPointPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getEventPointPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getEventPointCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소
																									// 금액으로 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getEventPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		// 포인트
		if (ocClaimAmountVO.getPointPayment() != null) {
			OcClaimPayment claimPayment = new OcClaimPayment();
			BeanUtils.copyProperties(ocClaimAmountVO.getPointPayment(), claimPayment); // 내용 복사

			if (ocClaimAmountVO.getPointCnclAmt() > 0) { // 실제 취소금이 있는 경우
				claimPayment.setPymntTodoAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제예정금액 - 클레임 취소 금액으로
																								// 변경
				claimPayment.setPymntAmt(ocClaimAmountVO.getPointPayment().getPymntAmt()); // 결제금액 - 클레임 취소 금액으로 변경

				// 클레임 결제 이력데이터 등록
				claimProcService.setClaimPaymentHistory(ocClaim, claimPayment, CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
			}
		}

		/*
		 * 임직원 주문인 경우 임직원 한도 복원
		 */
		if (UtilsText.equals(ocOrder.getEmpOrderYn(), Const.BOOLEAN_TRUE)) {
			if (UtilsText.equals(ocOrder.getEmpOrderYn(), Const.BOOLEAN_TRUE)) {
				String orderYearMonth = new SimpleDateFormat(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS)
						.format(ocOrder.getOrderDtm());
				String orderDate = new SimpleDateFormat(Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS)
						.format(ocOrder.getOrderDtm());
				String thisYearMonth = new SimpleDateFormat(Const.DEFAULT_YEARMONTH_PATTERN_NOT_CHARACTERS)
						.format(new Date());

				// 주문월과 해당월이 같을 경우만 한도복원 인터페이스 호출
				if (UtilsText.equals(orderYearMonth, thisYearMonth)) {
					MbEmployeeCertificationHistory employeeInfo = mbEmployeeCertificationHistoryDao
							.selectEmpCertfiHistory(mbMember.getMemberNo());

					EmployPoint returnEmployPointResult = membershipPointService.savePointByEmployNum("1", orderDate,
							employeeInfo.getEmpNoText(), String.valueOf(ocClaimAmountVO.getRefundCnclAmt()),
							Const.SALE_EMP_CD);
				}
			}
		}

		/*
		 * 결제취소
		 */
		// 결제취소 진행 시 환불 발생여부(refundOccurrence) ocClaimAmountVO 에 set
		claimProcService.setCancelPayment(ocClaimAmountVO, ocClaim, mbMember);

		/*
		 * 결제취소 처리 후 처리 주문, 클레임 상태 값 후 처리
		 */
		claimProcService.setClaimCancelAfterProc(ocClaim, ocClaimAmountVO);

		if (ocClaimAmountVO.isRefundOccurrence()) {
			resultMap.put("success", Const.BOOLEAN_FALSE);
			resultMap.put("message", "취소 처리는 완료되었으나\n\n결제취소 실패가 발생하여 환불접수 처리되었습니다.");
			resultMap.put("clmNo", ocClaim.getClmNo());
		} else {
			resultMap.put("success", Const.BOOLEAN_TRUE);
			resultMap.put("message", "주문이 정상적으로 취소되었습니다.");
			resultMap.put("clmNo", ocClaim.getClmNo());
		}

		// 이메일 , 카톡 문자 알림 서비스 시작
		try {
			claimMessageService.orderPartCancel(ocClaim);
		} catch (Exception e) {
			log.error("주문부분취소 메세지 메일 발송 실패 : " + e.getMessage());
		}

		return resultMap;
	}

	/**
	 * @Desc : 클레임 환불계좌 저장
	 * @Method Name : setRefundAccountInfo
	 * @Date : 2019. 7. 18.
	 * @Author : KTH
	 * @param ocClaim
	 * @throws Exception
	 */
	public void setRefundAccountInfo(OcClaim ocClaim) throws Exception {
		List<SyCodeDetail> bankList = commonCodeService.getCodeNoName(CommonCode.BANK_CODE); // 은행코드 목록
		// addInfo4 있는 은행만 뽑기
		bankList = bankList.stream().filter(x -> UtilsObject.isNotEmpty(x.getAddInfo4())).collect(Collectors.toList());
		// 넘어오는 은행코드는 환불계좌인증용 은행코드(addInfo4) 공통코드의 bankcode 를 추출
		String commonBankCode = bankList.stream().filter(x -> UtilsText.equals(x.getAddInfo4(), ocClaim.getBankCode()))
				.map(SyCodeDetail::getCodeDtlNo).findFirst().orElse(null);

		ocClaim.setBankCode(commonBankCode); // 공통코드 은행코드로 다시 set
		ocClaim.setModerNo(LoginManager.getUserDetails().getAdminNo());
		ocClaimDao.updateRefundAccount(ocClaim);

		/*
		 * 환불계좌 정보가 없는 재경팀 환불처리 대상 payment 업데이트
		 */
		OcClaimPayment ocClaimPayment = new OcClaimPayment();
		ocClaimPayment.setClmNo(ocClaim.getClmNo());
		ocClaimPayment.setBankCode(commonBankCode);
		ocClaimPayment.setAcntNoText(ocClaim.getRfndAcntText());
		ocClaimPayment.setAcntHldrName(ocClaim.getAcntHldrName());
		ocClaimPayment.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_REFUND_ACCEPT);
		ocClaimPayment.setModerNo(LoginManager.getUserDetails().getAdminNo());

		ocClaimPaymentDao.updatePaymentRefundAccountInfo(ocClaimPayment);
	}

	/**
	 * @Desc : 주문 전체 취소 validate
	 * @Method Name : validateOrderAllCancel
	 * @Date : 2019. 9. 23.
	 * @Author : KTH
	 * @param ocOrder
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> validateOrderAllCancel(OcOrder ocOrder) throws Exception {
		OcClaimProduct ocClaimProduct = new OcClaimProduct();

		ocClaimProduct.setOrgOrderNo(ocOrder.getOrderNo());
		ocClaimProduct.setClmGbnCode(CommonCode.CLM_GBN_CODE_CANCEL);

		return this.validateClaimAccept(ocClaimProduct);
	}

	/**
	 * @Desc : 클레임 접수 validate
	 * @Method Name : validateClaimAccept
	 * @Date : 2019. 9. 23.
	 * @Author : KTH
	 * @param ocClaimProduct
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> validateClaimAccept(OcClaimProduct ocClaimProduct) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		/*
		 * 2020.03.20 추가 [원 주문]
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaimProduct.getOrgOrderNo());
		ocOrder = ocOrderDao.selectByPrimaryKey(ocOrder);

		/*
		 * [원 주문상품 목록 - 사은품/배송비 포함]
		 */
		OcOrderProduct ocOrderProduct = new OcOrderProduct();
		ocOrderProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 추출용
		ocOrderProduct.setOrderNo(ocClaimProduct.getOrgOrderNo()); // 원주문번호 기준

		List<OcOrderProduct> orgOrderProductList = ocOrderProductDao.selectOrgOrderProductList(ocOrderProduct);

		// 원 주문 상품 개수(사은품/배송비 제외)
		long orgOrderProductCnt = orgOrderProductList.stream()
				.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
						&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
				.count();

		// 주문 전체취소 시 주문순번 상품이 별도 들어오지 않을 경우에 전체 상품으로 set
		if (ocClaimProduct.getOrderPrdtSeqs() == null) {
			Integer[] tempClaimProductPrdtSeqs = orgOrderProductList.stream().map(o -> o.getOrderPrdtSeq())
					.toArray(Integer[]::new);

			ocClaimProduct.setOrderPrdtSeqs(tempClaimProductPrdtSeqs);
		}

		/*
		 * [원 주문에 걸린 모든 클레임] - 클레임 상품 등록 후 내용을 다시 갱신
		 */
		OcClaimProduct paramClaimProduct = new OcClaimProduct();
		paramClaimProduct.setOrgOrderNo(ocClaimProduct.getOrgOrderNo()); // 원주문번호 기준
		paramClaimProduct.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 상품 코드

		List<OcClaimProduct> orderAllClaimProductList = ocClaimProductDao.selectOrgClaimProductList(paramClaimProduct);

		// 진행 중인 클레임 목록
		List<OcClaimProduct> ingClaimProductList = new ArrayList<OcClaimProduct>();

		// 진행 중인 AS 목록
		List<OcAsAcceptProduct> ingAsProductList = new ArrayList<OcAsAcceptProduct>();

		/*
		 * [주문 상품 결제 정보]
		 */
		OcOrderPayment ocOrderPayment = new OcOrderPayment();
		ocOrderPayment.setOrderNo(ocClaimProduct.getOrgOrderNo()); // 원주문번호 기준

		List<OcOrderPayment> orderPaymentList = ocOrderPaymentDao.selectPaymentList(ocOrderPayment);

		// 주문결제 가상계좌 입금대기 카운트
		long virtualAccountWaitDepositCnt = orderPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT)
						&& UtilsText.equals(x.getPymntStatCode(), CommonCode.PYMNT_STAT_CODE_WAIT_DEPOSIT))
				.count();

		for (OcOrderProduct oop : orgOrderProductList) {
			oop.setDlvyTypeCode(ocOrder.getDlvyTypeCode()); // 배송타입코드 set
		}

		/**
		 * 취소
		 */
		if (UtilsText.equals(ocClaimProduct.getClmGbnCode(), CommonCode.CLM_GBN_CODE_CANCEL)) {
			// 자사상품 취소 불가능 상품 개수 체크(입금대기, 결제완료 가능 - BO에서 자사상품은 상품준비중도 가능)
			long impossibleMmnyCancelProductCnt = orgOrderProductList.stream()
					.filter(x -> !UtilsText.equals(x.getOrderPrdtStatCode(), CommonCode.ORDER_PRDT_STAT_CODE_STAND_BY)
							&& !UtilsText.equals(x.getOrderPrdtStatCode(), CommonCode.ORDER_PRDT_STAT_CODE_COMPLETE)
							&& !UtilsText.equals(x.getOrderPrdtStatCode(),
									CommonCode.ORDER_PRDT_STAT_CODE_PRODUCT_PREPARATION)
							&& (
							// 매장픽업주문이고 상품출고일때
							UtilsText.equals(x.getOrderPrdtStatCode(), CommonCode.ORDER_PRDT_STAT_CODE_PRODUCT_DELIVERY)
									&& !UtilsText.equals(x.getDlvyTypeCode(), CommonCode.DLVY_TYPE_CODE_STORE_PICKUP))
							&& (
							// 매장픽업주문이고 픽업준비완료일때
							UtilsText.equals(x.getOrderPrdtStatCode(),
									CommonCode.ORDER_PRDT_STAT_CODE_PICKUP_PREPARATION_FINISH)
									&& !UtilsText.equals(x.getDlvyTypeCode(), CommonCode.DLVY_TYPE_CODE_STORE_PICKUP)))
					.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_TRUE))
					.filter(x -> Arrays.asList(ocClaimProduct.getOrderPrdtSeqs()).contains(x.getOrderPrdtSeq()))
					.count();

			if (impossibleMmnyCancelProductCnt > 0) {
				resultMap.put("success", Const.BOOLEAN_FALSE);
				resultMap.put("message", "취소 접수가 불가능한 상품 상태 값이 존재합니다.");

				return resultMap;
			}

			// 업체상품 취소 불가능 상품 개수 체크(입금대기, 결제완료 가능)
			long impossibleVndrCancelProductCnt = orgOrderProductList.stream()
					.filter(x -> !UtilsText.equals(x.getOrderPrdtStatCode(), CommonCode.ORDER_PRDT_STAT_CODE_STAND_BY)
							&& !UtilsText.equals(x.getOrderPrdtStatCode(), CommonCode.ORDER_PRDT_STAT_CODE_COMPLETE))
					.filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), Const.BOOLEAN_FALSE))
					.filter(x -> Arrays.asList(ocClaimProduct.getOrderPrdtSeqs()).contains(x.getOrderPrdtSeq()))
					.count();

			if (impossibleVndrCancelProductCnt > 0) {
				resultMap.put("success", Const.BOOLEAN_FALSE);
				resultMap.put("message", "취소 접수가 불가능한 상품 상태 값이 존재합니다.");

				return resultMap;
			}

			// 부분 취소는 무통장 미입금 상태 접수 불가
			if (virtualAccountWaitDepositCnt > 0) {
				if (orgOrderProductCnt != ocClaimProduct.getOrderPrdtSeqs().length) {
					resultMap.put("success", Const.BOOLEAN_FALSE);
					resultMap.put("message", "무통장결제 입금대기 상태에서는 주문 전체취소만 가능합니다.");

					return resultMap;
				}
			}
		}
		/**
		 * 교환
		 */
		else if (UtilsText.equals(ocClaimProduct.getClmGbnCode(), CommonCode.CLM_GBN_CODE_EXCHANGE)) {
			// 교환 불가능 상품 개수 체크(배송완료, 구매확정 가능)
//			long impossibleExchangeProductList = orgOrderProductList.stream().filter(
//					x -> !UtilsText.equals(x.getOrderPrdtStatCode(), CommonCode.ORDER_PRDT_STAT_CODE_DELIVERY_FINISH)
//							&& !UtilsText.equals(x.getOrderPrdtStatCode(), CommonCode.ORDER_PRDT_STAT_CODE_BUY_CONFIRM))
//					.filter(x -> Arrays.asList(ocClaimProduct.getOrderPrdtSeqs()).contains(x.getOrderPrdtSeq()))
//					.count();
//
//			if (impossibleExchangeProductList > 0) {
//				resultMap.put("success", Const.BOOLEAN_FALSE);
//				resultMap.put("message", "교환 접수가 불가능한 상품 상태 값이 존재합니다.");
//
//				return resultMap;
//			}
		}
		/**
		 * 반품
		 */
		else if (UtilsText.equals(ocClaimProduct.getClmGbnCode(), CommonCode.CLM_GBN_CODE_RETURN)) {
			// 교환 불가능 상품 개수 체크(배송완료, 구매확정 가능)
//			long impossibleReturnProductList = orgOrderProductList.stream().filter(
//					x -> !UtilsText.equals(x.getOrderPrdtStatCode(), CommonCode.ORDER_PRDT_STAT_CODE_DELIVERY_FINISH)
//							&& !UtilsText.equals(x.getOrderPrdtStatCode(), CommonCode.ORDER_PRDT_STAT_CODE_BUY_CONFIRM))
//					.filter(x -> Arrays.asList(ocClaimProduct.getOrderPrdtSeqs()).contains(x.getOrderPrdtSeq()))
//					.count();
//
//			if (impossibleReturnProductList > 0) {
//				resultMap.put("success", Const.BOOLEAN_FALSE);
//				resultMap.put("message", "반품 접수가 불가능한 상품 상태 값이 존재합니다.");
//
//				return resultMap;
//			}
		}

		/**
		 * 진행 중인 클레임 목록 추출
		 */
		ingClaimProductList = orderAllClaimProductList.stream().filter(x -> UtilsText.equals(x.getClmPrdtStatCode(),
				CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT)
				|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT)
				|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_PICKUP_ORDER)
				|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_RECEIVE_FINISH)
				|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_JUDGE_FINISH)
				|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_DELIVERY_ORDER)
				|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_DELIVERY_PROC)
				|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT)
				|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_PICKUP_ORDER)
				|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_RECEIVE_FINISH)
				|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_JUDGE_FINISH)
				|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_REDEPTION_ACCEPT))
				.collect(Collectors.toList());

		// 클레임 진행 중인 상품 체크
		if (!ObjectUtils.isEmpty(ingClaimProductList)) {
			long ingClaimProductCnt = ingClaimProductList.stream()
					.filter(x -> Arrays.asList(ocClaimProduct.getOrderPrdtSeqs()).contains(x.getOrderPrdtSeq()))
					.count();

			if (ingClaimProductCnt > 0) {
				resultMap.put("success", Const.BOOLEAN_FALSE);
				resultMap.put("message", "클레임 진행 중인 상품이 있습니다.");

				return resultMap;
			}
		}

		/**
		 * 진행중인 AS 목록 추출
		 */
		OcAsAcceptProduct ocAsAcceptProduct = new OcAsAcceptProduct();
		ocAsAcceptProduct.setOrgOrderNo(ocClaimProduct.getOrgOrderNo());

		List<OcAsAcceptProduct> tempOrgOrderAllAsList = ocAsAcceptProductDao
				.selectOrgOrderAllAsProductList(ocAsAcceptProduct);

		ingAsProductList = tempOrgOrderAllAsList.stream()
				.filter(x -> !UtilsText.equals(x.getAsPrdtStatCode(), CommonCode.AS_PRDT_STAT_CODE_ACCEPT_CANCEL)
						&& !UtilsText.equals(x.getAsPrdtStatCode(), CommonCode.AS_PRDT_STAT_CODE_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getAsPrdtStatCode(), CommonCode.AS_PRDT_STAT_CODE_AS_FINISH)
						&& !UtilsText.equals(x.getAsPrdtStatCode(), CommonCode.AS_PRDT_STAT_CODE_AS_REJECT))
				.collect(Collectors.toList());

		// AS 진행 중인 상품 체크
		if (!ObjectUtils.isEmpty(ingAsProductList)) {
			long ingAsProductCnt = ingAsProductList.stream()
					.filter(x -> Arrays.asList(ocClaimProduct.getOrderPrdtSeqs()).contains(x.getOrderPrdtSeq()))
					.count();

			if (ingAsProductCnt > 0) {
				resultMap.put("success", Const.BOOLEAN_FALSE);
				resultMap.put("message", "AS 진행 중인 상품이 있습니다.");

				return resultMap;
			}
		}

		resultMap.put("success", Const.BOOLEAN_TRUE);
		resultMap.put("message", "PASS");

		return resultMap;
	}

	/**************************** E : 김태호 *****************************/

	/**************************** S : 이재영 *****************************/

	/**
	 * @Desc : 관리자 메모 갯수 조회
	 * @Method Name : memoCountByOrderNo
	 * @Date : 2019. 2. 15.
	 * @Author : ljyoung@3top.co.kr
	 * @param ocClaimMemo
	 * @return
	 * @throws Exception
	 */
	public int memoCountByOrderNo(OcClaimMemo ocClaimMemo) throws Exception {
		return ocClaimMemoDao.countByOrderNo(ocClaimMemo);
	}

	/**
	 * @Desc : 주문번호에 따른 메모 목록 조회 쿼리
	 * @Method Name : memoSelectByOrderNo
	 * @Date : 2019. 3. 21.
	 * @Author : KTH
	 * @param ocClaimMemo
	 * @return
	 * @throws Exception
	 */
	public List<OcClaimMemo> memoSelectByOrderNo(OcClaimMemo ocClaimMemo) throws Exception {
		return ocClaimMemoDao.selectByOrderNo(ocClaimMemo);
	}

	/**
	 * @Desc : bankda key를 통한 단건 조회
	 * @Method Name : selectBankdaByPrimaryKey
	 * @Date : 2019. 4. 3.
	 * @Author : ljyoung@3top.co.kr
	 * @return
	 */
	public IfBankda selectBankdaByPrimaryKey(IfBankda ifBankda) throws Exception {
		return ifBankdaDao.selectByPrimaryKey(ifBankda);
	}

	/**
	 * @Desc : 자동 입금 확인 서비스 관리 목록
	 * @Method Name : selectListBankda
	 * @Date : 2019. 4. 1.
	 * @Author : ljyoung@3top.co.kr
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<IfBankda> selectListBankda(Pageable<IfBankda, IfBankda> pageable) throws Exception {
		int count = ifBankdaDao.selectListBankdaCount(pageable);
		pageable.setTotalCount(count);
		if (count > 0)
			pageable.setContent(ifBankdaDao.selectListBankda(pageable));
		return pageable.getPage();
	}

	/**
	 * @Desc : 자동 입금 확인 계좌 오늘의 정보
	 * @Method Name : selectTodayInfoOfBankda
	 * @Date : 2019. 4. 1.
	 * @Author : ljyoung@3top.co.kr
	 * @param ifBankda
	 * @return
	 * @throws Exception
	 */
	public IfBankda selectTodayInfoOfBankda(IfBankda ifBankda) throws Exception {
		return ifBankdaDao.selectTodayInfoOfBankda(ifBankda);
	}

	/**
	 * @Desc :
	 * @Method Name : selectMaxBankdaCode
	 * @Date : 2019. 4. 1.
	 * @Author : ljyoung@3top.co.kr
	 * @return
	 * @throws Exception
	 */
	public int selectMaxBankdaCode(IfBankda ifBankda) throws Exception {
		return ifBankdaDao.selectMaxBankdaCode(ifBankda);
	}

	/**
	 * @Desc : bankda 정보 update
	 * @Method Name : updateBankda
	 * @Date : 2019. 4. 2.
	 * @Author : ljyoung@3top.co.kr
	 * @param ifBankda
	 * @throws Exception
	 */
	public void updateBankda(IfBankda ifBankda) throws Exception {
		if (ifBankda.getDepositconfirm().equals(Const.BOOLEAN_TRUE)) {
			if (ifBankda.getDepositdtm() == null)
				ifBankda.setDepositdtm(new Timestamp(new Date().getTime()));
		}

		if (ifBankda.getDepositconfirm().equals("N"))
			ifBankda.setDepositdtm(null);

		ifBankdaDao.update(ifBankda);
	}

	/**************************** E : 이재영 *****************************/

	/**************************** S : jeon *****************************/
	/**
	 * @Desc : 주문 기준 클레임 리스트 확인
	 * @Method Name : selectClaimProductList
	 * @Date : 2019. 2. 23.
	 * @Author : flychani@3top.co.kr
	 * @param orderNo
	 * @return
	 */
	public List<OcClaimProduct> selectClaimProductListForOrder(OcClaimProduct ocClaimProducts) throws Exception {
		return ocClaimProductDao.selectClaimProductListForOrder(ocClaimProducts);
	}

	/**************************** E : jeon *****************************/

	/**
	 * @Desc : 동봉 배송비 업데이트 - 심의완료 전까지만
	 * @Method Name : setEclsDlvyAmt
	 * @Date : 2020. 2. 3.
	 * @Author : 이강수
	 * @param OcClaim
	 */
	public void setEclsDlvyAmt(OcClaim ocClaim) throws Exception {

		/*
		 * [클레임 등록 정보]
		 */
		OcClaim basisOcClaimInfo = ocClaimDao.selectClaimBasisInfo(ocClaim);
		String clmGbnCode = basisOcClaimInfo.getClmGbnCode();
		String clmStatCode = basisOcClaimInfo.getClmStatCode();
		String clmStatCodeName = basisOcClaimInfo.getClmStatCodeName();

		if (UtilsText.equals(clmGbnCode, CommonCode.CLM_GBN_CODE_EXCHANGE)) {
			// 교환
			if (UtilsText.equals(clmStatCode, CommonCode.CLM_STAT_CODE_EXCHANGE_JUDGE_FINISH)
					|| UtilsText.equals(clmStatCode, CommonCode.CLM_STAT_CODE_EXCHANGE_REDEPTION_ACCEPT)
					|| UtilsText.equals(clmStatCode, CommonCode.CLM_STAT_CODE_EXCHANGE_REFUND_ACCEPT)
					|| UtilsText.equals(clmStatCode, CommonCode.CLM_STAT_CODE_EXCHANGE_FINISH)
					|| UtilsText.equals(clmStatCode, CommonCode.CLM_STAT_CODE_EXCHANGE_IMPOSSIBLE)
					|| UtilsText.equals(clmStatCode, CommonCode.CLM_STAT_CODE_EXCHANGE_ACCEPT_CANCEL)
					|| UtilsText.equals(clmStatCode, CommonCode.CLM_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT)
					|| UtilsText.equals(clmStatCode, CommonCode.CLM_STAT_CODE_EXCHANGE_DELIVERY_ORDER)
					|| UtilsText.equals(clmStatCode, CommonCode.CLM_STAT_CODE_EXCHANGE_DELIVERY_PROC)) {

				throw new Exception("validMsg:" + clmStatCodeName + " 상태에서는 동봉 배송비를 저장할 수 없습니다.");
			}
		} else if (UtilsText.equals(clmGbnCode, CommonCode.CLM_GBN_CODE_RETURN)) {
			// 반품
			if (UtilsText.equals(clmStatCode, CommonCode.CLM_STAT_CODE_RETURN_JUDGE_FINISH)
					|| UtilsText.equals(clmStatCode, CommonCode.CLM_STAT_CODE_RETURN_REDEPTION_ACCEPT)
					|| UtilsText.equals(clmStatCode, CommonCode.CLM_STAT_CODE_RETURN_REFUND_ACCEPT)
					|| UtilsText.equals(clmStatCode, CommonCode.CLM_STAT_CODE_RETURN_FINISH)
					|| UtilsText.equals(clmStatCode, CommonCode.CLM_STAT_CODE_RETURN_IMPOSSIBLE)
					|| UtilsText.equals(clmStatCode, CommonCode.CLM_STAT_CODE_RETURN_ACCEPT_CANCEL)
					|| UtilsText.equals(clmStatCode, CommonCode.CLM_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)) {

				throw new Exception("validMsg:" + clmStatCodeName + " 상태에서는 동봉 배송비를 저장할 수 없습니다.");
			}
		} else {
			throw new Exception("validMsg:교환 또는 반품 클레임만 동봉 배송비를 저장할 수 있습니다.");
		}

		/**
		 * 동봉 배송비 업데이트
		 */
		log.debug("기존 동봉 배송비 : " + basisOcClaimInfo.getEclsDlvyAmt());
		log.debug("변경 동봉 배송비 : " + ocClaim.getEclsDlvyAmt());

		OcClaim paramClaim = new OcClaim();
		paramClaim.setClmNo(ocClaim.getClmNo());
		paramClaim.setEclsDlvyAmt(ocClaim.getEclsDlvyAmt());
		paramClaim.setModerNo(LoginManager.getUserDetails().getAdminNo());

		ocClaimDao.updateEclsDlvyAmt(paramClaim);
	}

	/**
	 * @Desc : 주문 주결제수단 조회
	 * @Method Name : getMainPayment
	 * @Date : 2020. 3. 5.
	 * @Author : 이강수
	 * @param orgOrderNo
	 * @return
	 * @throws Exception
	 */
	public OcOrderPayment getMainPayment(String orderNo) throws Exception {
		return orderService.getMainPayment(orderNo);
	}

	/**
	 * @Desc : 반품 배송비 결제취소 (배송비 환불금차감으로 반품완료했으나 차감한 배송비를 결제취소해서 환불해줘야하는 경우)
	 * @Method Name : setCancelReturnDlvyAmt
	 * @Date : 2020. 2. 26.
	 * @Author : 이강수
	 * @param OcClaim
	 */
	public Map<String, Object> setCancelReturnDlvyAmt(OcClaim paramClaim) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		/*
		 * [클레임 등록 정보]
		 */
		OcClaim ocClaim = ocClaimDao.selectByPrimaryKey(paramClaim);
		ocClaim.setClaimRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자
		String clmStatCode = ocClaim.getClmStatCode();
		String addDlvyAmtPymntType = ocClaim.getAddDlvyAmtPymntType();
		int addDlvyAmt = ocClaim.getAddDlvyAmt();

		if (!UtilsText.equals(clmStatCode, CommonCode.CLM_STAT_CODE_RETURN_FINISH)) {
			throw new Exception("validMsg:현재 클레임 상태에서는 반품 배송비를 결제취소할 수 없습니다.");
		}

		if (!UtilsText.equals(addDlvyAmtPymntType, CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_REFUND_DEDUCTION)) {
			throw new Exception("validMsg:배송비 결제가 환불금차감 일때만 반품 배송비를 결제취소할 수 있습니다.");
		}

		if (addDlvyAmt <= 0) {
			throw new Exception("validMsg:결제취소할 반품배송비가 존재하지 않습니다");
		}

		String clmNo = ocClaim.getClmNo();

		/*
		 * 클레임 정보
		 */
		ocClaim = ocClaimDao.selectClaimBasisInfo(ocClaim); // 클레임 기본 정보

		ocClaim.setClaimRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자 no set

		/*
		 * [원 주문정보 get]
		 */
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(ocClaim.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder); // 주문기본정보

		// claim 정보에 siteNo set
		ocClaim.setSiteNo(ocOrder.getSiteNo());
		ocClaim.setMemberNo(ocOrder.getMemberNo());

		/*
		 * [주문자 정보]
		 */
		MbMember mbMember = memberDao.selectMemberDefalutInfo(ocOrder.getMemberNo());

		/*************************************
		 * 클레임 금액 계산
		 *************************************/
		OcClaimAmountVO ocClaimAmountVO = new OcClaimAmountVO();

		claimProcService.setClaimAmountCalcForPartCancel(ocClaim, ocOrder, ocClaimAmountVO,
				CommonCode.CLM_GBN_CODE_CANCEL, false);

		OcClaimPayment mainPayment = ocClaimAmountVO.getMainPayment();
//		OcClaimPayment giftPayment = ocClaimAmountVO.getGiftPayment();
		OcClaimPayment buyPointPayment = ocClaimAmountVO.getPointPayment();
		OcClaimPayment eventPointPayment = ocClaimAmountVO.getEventPointPayment();
		
		if (!UtilsText.equals(mainPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_CARD)
				&& !UtilsText.equals(mainPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_NAVER_PAY)
//				&& !UtilsText.equals(mainPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_KAKAO_PAY)
//				&& !UtilsText.equals(mainPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_MOBILE_GIFT)
				&& !UtilsText.equals(mainPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_BUY_POINT)
				&& !UtilsText.equals(mainPayment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_EVENT_POINT)
				) {
			// 주결제 수단이 카드결제/네이버페이/카카오(TODO)/기프트카드(TODO)/포인트결제 가 아닐때 EXCEPT
			throw new Exception("validMsg:주결제수단이 신용/체크카드, 네이버페이, 포인트결제 일때만 반품배송비 취소가 가능합니다.");
		}
		
		/*
		 * [클레임 결제 정보]
		 */
		OcClaimPayment paramPayment = new OcClaimPayment();
		paramPayment.setClmNo(clmNo);
		List<OcClaimPayment> basisClaimPaymentList = ocClaimPaymentDao.selectClaimPaymentList(paramPayment);

		// 반품완료시에 차감한 환수한 추가반품배송비
		// 환수환불구분 : E
		// 이력구분타입 : H
		// 발생사유코드 : 10003
		OcClaimPayment redempDlvyAmtPymnt = basisClaimPaymentList.stream()
				.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP))
				.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
				.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY))
				.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT)).findFirst()
				.orElse(null);

		if (redempDlvyAmtPymnt == null) {
			throw new Exception("validMsg:결제취소할 반품배송비가 존재하지 않습니다");
		}

		////////////////////// S : START 배송비 취소금액 결제수단별 set ////////////////////////////
		
		int refundCnclAmt = redempDlvyAmtPymnt.getPymntAmt();
		
		int mainPymntAmt = (mainPayment == null) ? 0 : mainPayment.getRemainPymntCnclAmt();
//		int giftPymntAmt = (giftPayment == null) ? 0 : giftPayment.getRemainPymntCnclAmt();
		int buyPointPymntAmt = (buyPointPayment == null) ? 0 : buyPointPayment.getRemainPymntCnclAmt();
		int eventPointPymntAmt = (eventPointPayment == null) ? 0 : eventPointPayment.getRemainPymntCnclAmt();
		
		log.error("반품배송비 취소 금액계산 - claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrgOrderNo());
		log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		log.error("::::::::::::::::::::::: mainPymntAmt 남은잔여취소금액 : " + mainPymntAmt);
//		log.error("::::::::::::::::::::::: giftPymntAmt 남은잔여취소금액 : " + giftPymntAmt);
		log.error("::::::::::::::::::::::: buyPointPymntAmt 남은잔여취소금액 : " + buyPointPymntAmt);
		log.error("::::::::::::::::::::::: eventPointPymntAmt 남은잔여취소금액 : " + eventPointPymntAmt);
		log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		int totalRemainPymnt = mainPymntAmt + buyPointPymntAmt + eventPointPymntAmt;
		if(totalRemainPymnt < refundCnclAmt) {
			throw new Exception("validMsg:취소가능한 잔여금액이 부족합니다.\n현재 취소가능 잔여금액의 결제수단은 신용/체크카드, 네이버페이, 포인트결제 등입니다.");
		}
		
		int mainRfndAmt = 0;
//		int giftRfndAmt = 0;
		int buyPointRfndAmt = 0;
		int eventPointRfndAmt = 0;
		
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
		// 취소 할 기프트카드
//		if (refundCnclAmt > 0 && giftPymntAmt > 0) {
//			if (giftPymntAmt >= refundCnclAmt) {
//				giftRfndAmt = refundCnclAmt;
//				refundCnclAmt = 0;
//			} else {
//				giftRfndAmt = giftPymntAmt;
//				refundCnclAmt -= giftRfndAmt;
//			}
//		}
		// 취소 할 이벤트 포인트
		if (refundCnclAmt > 0 && eventPointPymntAmt > 0) {
			if (eventPointPymntAmt >= refundCnclAmt) {
				eventPointRfndAmt = refundCnclAmt;
				refundCnclAmt = 0;
			} else {
				eventPointRfndAmt = eventPointPymntAmt;
				refundCnclAmt -= eventPointRfndAmt;
			}
		}
		// 취소 할 구매포인트
		if (refundCnclAmt > 0 && buyPointPymntAmt > 0) {
			if (buyPointPymntAmt >= refundCnclAmt) {
				buyPointRfndAmt = refundCnclAmt;
				refundCnclAmt = 0;
			} else {
				buyPointRfndAmt = buyPointPymntAmt;
				refundCnclAmt -= buyPointRfndAmt;
			}
		}
		
		log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		log.error("::::::::::::::::::::::: mainRfndAmt 환불해주는 주결제 금액 : " + mainRfndAmt);
//		log.error("::::::::::::::::::::::: giftRfndAmt 환불해주는 기프트카드 금액 : " + giftRfndAmt);
		log.error("::::::::::::::::::::::: buyPointRfndAmt 환불해주는 구매포인트 : " + buyPointRfndAmt);
		log.error("::::::::::::::::::::::: eventPointRfndAmt 환불해주는 이벤트포인트 : " + eventPointRfndAmt);
		log.error("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		if (ocClaimAmountVO.getEventPointPayment() != null) {
			ocClaimAmountVO.getEventPointPayment().setPymntAmt(eventPointRfndAmt);
			ocClaimAmountVO.getEventPointPayment().setPymntTodoAmt(eventPointRfndAmt);
		}
//		if (ocClaimAmountVO.getGiftPayment() != null) {
//			ocClaimAmountVO.getGiftPayment().setPymntAmt(giftRfndAmt);
//			ocClaimAmountVO.getGiftPayment().setPymntTodoAmt(giftRfndAmt);
//		}
		if (ocClaimAmountVO.getMainPayment() != null) {
			ocClaimAmountVO.getMainPayment().setPymntAmt(mainRfndAmt);
			ocClaimAmountVO.getMainPayment().setPymntTodoAmt(mainRfndAmt);
		}
		if (ocClaimAmountVO.getPointPayment() != null) {
			ocClaimAmountVO.getPointPayment().setPymntAmt(buyPointRfndAmt);
			ocClaimAmountVO.getPointPayment().setPymntTodoAmt(buyPointRfndAmt);
		}
		
		////////////////////// E : START 배송비 취소금액 결제수단별 set ////////////////////////////
		
//		int i = 1;
//		if(i == 1) {
//			throw new Exception("!!!");
//		}
		
		// BEFORE
		// 1. 원주문 취소금액 업데이트
		// 2. 배송비취소 리오더 매출 등록
		// 3. 주문상품(매출취소 리오더) 생성
		claimProcService.setBeforeCancelReturnDlvyPayment(ocOrder, ocClaim, redempDlvyAmtPymnt, ocClaimAmountVO);

		/*
		 * 배송비 결제 취소 (PG사 결제, 포인트 등)
		 */
		log.error(">>>>>> 반품배송비 취소 시작 - claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrgOrderNo());
		
		claimProcService.setCancelReturnDlvyPayment(ocClaimAmountVO, ocClaim, mbMember, redempDlvyAmtPymnt);
		Boolean isRefundOccurrence = ocClaimAmountVO.isRefundOccurrence(); // 재경팀환불접수 여부 - false or true
		
		log.error("<<<<<< 반품배송비 취소 끝 - claimNo : {} orderNo : {}", ocClaim.getClmNo(), ocClaim.getOrgOrderNo());
		
		/**
		 * 배송비 결제취소 validate에 걸렸을경우
		 */
		if (UtilsText.equals(ocClaimAmountVO.getIsDlvyAmtAllCancelYn(), Const.BOOLEAN_FALSE)) {
			throw new Exception(UtilsText.concat("validMsg:", ocClaimAmountVO.getResultMessage()));
		}

		// AFTER
		// 4. 반품완료시에 차감한 환수한 추가반품배송비 삭제
		// 환수환불구분 : E
		// 이력구분타입 : H
		// 발생사유코드 : 10003
		// 5. 클레임 마스터 업데이트
		// 클레임 마스터 기존 총환불금 += 결제취소한 배송비 금액
		// 클레임 마스터 추가배송비결제금액 = 기존 추가 배송비 - 결제취소한 배송비 금액
		// 클레임 마스터 추가배송비결제타입 = 'F'
		claimProcService.setAfterCancelReturnDlvyPayment(clmNo, redempDlvyAmtPymnt);

		/**
		 * 배송비 결제취소 실패하고 재경팀 환불금처리로 넘기는 경우
		 */
		if (isRefundOccurrence) {
			resultMap.put("success", Const.BOOLEAN_FALSE);
			resultMap.put("message", "반품 배송비 결제취소 실패가 발생하여 환불접수 처리되었습니다.");
			resultMap.put("clmNo", ocClaim.getClmNo());

			return resultMap;
		}

		/**
		 * 정상적으로 반품 배송비가 취소된경우!
		 */
		resultMap.put("success", Const.BOOLEAN_TRUE);
		resultMap.put("message", "반품 배송비 결제취소가 정상적으로 완료되었습니다.");
		resultMap.put("clmNo", ocClaim.getClmNo());

		return resultMap;
	}
	
	/**
	 * @Desc : 미출로인한 교환불가 (교환배송지시가 내려진 상태에서 교환재배송상품 중 하나라도 미출반품처리대상이되어 배송중단이 내려지면 전체 교환불가)
	 * @Method Name : setClaimExchangeImpossible
	 * @Date : 2020. 4. 23.
	 * @Author : 이강수
	 * @param OcClaim
	 */
	public Map<String, Object> setClaimExchangeImpossible(OcClaim paramClaim, OcClaimProduct[] ocClaimProducts) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		/*
		 * [클레임 등록 정보]
		 */
		OcClaim ocClaim = ocClaimDao.selectByPrimaryKey(paramClaim);
		String clmNo = ocClaim.getClmNo();
		String clmStatCode = ocClaim.getClmStatCode();
		String orgOrderNo = ocClaim.getOrgOrderNo();

		if (!UtilsText.equals(clmStatCode, CommonCode.CLM_STAT_CODE_EXCHANGE_DELIVERY_ORDER)) {
			throw new Exception("validMsg:교환배송지시 상태일때만 미출로 인한 교환불가를 내릴 수 있습니다.");
		}
		
		String moderAdminNo = LoginManager.getUserDetails().getAdminNo();
		
		/*
		 * 클레임  업데이트
		 */
		ocClaim.setModerNo(moderAdminNo); // 수정자번호
		ocClaim.setClmStatCode(CommonCode.CLM_STAT_CODE_EXCHANGE_IMPOSSIBLE); // 클레임 상태 코드:교환불가
		
		ocClaimDao.updateClaimStatCode(ocClaim);

		/*
		 * 클레임 상품 업데이트
		 */
		for(OcClaimProduct ocp : ocClaimProducts) {
			// 2020.05.25 :배송중단 내려진것만 불가처리
			if(UtilsText.equals(ocp.getChangeDlvyDscntcYn(), Const.BOOLEAN_TRUE)) { 
				/**
				 * 원상품처리
				 */
				// 클레임 상품 상태코드
				ocp.setModerNo(moderAdminNo); // 수정자번호
				ocp.setClmPrdtStatCode(CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE); // 클레임 상품 상태 코드:교환불가
				ocClaimProductDao.updateClaimProductStatCode(ocp);
				// 클레임 상품 처리 내용
				ocp.setClmProcTypeCode(CommonCode.CLM_PROC_TYPE_CODE_EXCHANGE_UNRELEASED_PROC);
				ocp.setClmProcContText("재고부족으로 인한 배송중단");
				ocClaimProductDao.updateClaimProduct(ocp);
				
				try {
					OcClaimStatusHistory histParam = new OcClaimStatusHistory();
					histParam.setClmNo(clmNo);
					histParam.setClmPrdtSeq(ocp.getClmPrdtSeq());
					histParam.setClmPrdtStatCode(CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE);
					histParam.setStockGbnCode(ocp.getStockGbnCode());
					histParam.setNoteText("배송중단에 따른 교환불가 처리");
					histParam.setRgsterNo(moderAdminNo);
					ocClaimStatusHistoryDao.insertClaimStatusHistoryByClaimProduct(histParam);
				} catch (Exception e) {
					log.error("이력등록 실패해도 흘러가게 둔다.");
					
				} finally {
					/**  
					 * 재배송상품처리
					 */
					ocp.setClmPrdtSeq(ocp.getChangeClmPrdtSeq()); // 재배송상품이므로
					// 클레임 상품 상태코드
					ocp.setModerNo(moderAdminNo); // 수정자번호
					ocp.setClmPrdtStatCode(CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE); // 클레임 상품 상태 코드:교환불가
					ocClaimProductDao.updateClaimProductStatCode(ocp);
					// 클레임 상품 처리 내용
					ocp.setClmProcTypeCode(CommonCode.CLM_PROC_TYPE_CODE_EXCHANGE_UNRELEASED_PROC);
					ocp.setClmProcContText("재고부족으로 인한 배송중단");
					ocClaimProductDao.updateClaimProduct(ocp);
				}
			}
		}
		
		/*
		   2020.05.22 : 교환상세팝업 '미출로인한 교환불가' 내릴 때, 교환재배송 리오더 주문을 취소처리
		   1. 교환상세팝업 '미출로인한 교환불가' 내릴 때, 교환재배송 리오더 주문을 취소처리 조건 
			 ㄱ. 리오더 주문의 판매구분타입이 'D(교환재배송매출)'일 경우 
			 ㄴ. 리오더 주문이 '상품준비중'일 경우 (교환배송지시 내려졌기 때문) 
			 ㄷ. 리오더 주문이 '배송중단' 이 내려진 경우 (이미 배송중단 시 조건에 따라 PR_ORDER_HOLD 호출되었음)
		   2. 1번의 조건을 만족하는 경우
		          ㄱ. 리오더 주문상품"취소완료" 업데이트 처리 ( ORDER_PRDT_STAT_CODE )
		          ㄴ. 가장 마지막 차수의 리오더 배송행 "취소완료" 업데이트 처리 ( DLVY_STAT_CODE )
		 */
		OcOrderProduct paramChangeProduct = new OcOrderProduct();
		paramChangeProduct.setClmNo(clmNo);
		paramChangeProduct.setOrgOrderNo(orgOrderNo);
		List<OcOrderProduct> lastChangeOrderDeliveryList = ocOrderProductDao.selectLastChangeOrderDeliveryProduct(paramChangeProduct);
		
		for(OcOrderProduct oop : lastChangeOrderDeliveryList) {
		
			oop.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE); // 취소완료
			oop.setModerNo(moderAdminNo);
			ocOrderProductDao.updatePrdtOrderStatus(oop);
			
			OcOrderDeliveryHistory paramChangeDlvy = new OcOrderDeliveryHistory();
			paramChangeDlvy.setOrderNo(oop.getOrderNo());
			paramChangeDlvy.setOrderPrdtSeq(oop.getOrderPrdtSeq());
			paramChangeDlvy.setOrderDlvyHistSeq(oop.getOrderDlvyHistSeq());
			paramChangeDlvy.setDlvyStatCode(CommonCode.DLVY_STAT_CODE_DELIVERY_CANCEL); // 배송취소
			paramChangeDlvy.setModerNo(moderAdminNo);
			ocOrderDeliveryHistoryDao.setChangeDelivery(paramChangeDlvy);
			
			try { // 상품이력 등록안된다고 던져지면 안되므로 try catch 
				OcOrderProductHistory paramOrderPrdtHist = new OcOrderProductHistory();
				paramOrderPrdtHist.setOrderNo(oop.getOrderNo());
				paramOrderPrdtHist.setOrderPrdtSeq(oop.getOrderPrdtSeq());
				paramOrderPrdtHist.setPrdtNo(oop.getPrdtNo());
				paramOrderPrdtHist.setPrdtOptnNo(oop.getPrdtOptnNo());
				paramOrderPrdtHist.setOptnName(oop.getOptnName());
				paramOrderPrdtHist.setPrdtName(oop.getPrdtName());
				paramOrderPrdtHist.setEngPrdtName(oop.getEngPrdtName());
				paramOrderPrdtHist.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE); // 취소완료
				paramOrderPrdtHist.setNoteText("미출 교환불가로인한 취소");
				paramOrderPrdtHist.setRgsterNo(moderAdminNo);
				ocOrderProductHistoryDao.insertProductHistory(paramOrderPrdtHist);
			} catch (Exception e) {
				log.error("주문번호["+oop.getOrderNo()+"] / 상품순번["+oop.getOrderPrdtSeq().toString()+"]이력등록 에러");
				log.error(e.getMessage());
			}
		}
		
		resultMap.put("success", Const.BOOLEAN_TRUE);
		resultMap.put("message", "미출로인한 교환불가 처리가 정상적으로 완료되었습니다.");
		resultMap.put("clmNo", clmNo);

		return resultMap;
	}
	
	/**
	 * @Desc : 배송중단 프로시져 호출 / 성공 : "0"
	 * @Method Name : callProcedureForOrderHold
	 * @Date : 2020. 4. 28.
	 * @Author : 이강수
	 * @param Map<String, String> map
	 * @return
	 * @throws Exception
	 */
	public String callProcedureForOrderHold(Map<String, String> map) throws Exception {
		ocClaimProductDao.callProcedureForOrderHold(map);
		return map.get("errorCode");
	}
}
