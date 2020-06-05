package kr.co.abcmart.bo.order.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.ibm.icu.util.Calendar;

import kr.co.abcmart.bo.afterService.model.master.OcAsAccept;
import kr.co.abcmart.bo.afterService.model.master.OcAsAcceptProduct;
import kr.co.abcmart.bo.afterService.service.AfterServiceService;
import kr.co.abcmart.bo.board.model.master.BdMemberCounsel;
import kr.co.abcmart.bo.board.service.BdInquiryService;
import kr.co.abcmart.bo.claim.model.master.OcClaimProduct;
import kr.co.abcmart.bo.claim.repository.master.OcClaimProductDao;
import kr.co.abcmart.bo.claim.service.ClaimService;
import kr.co.abcmart.bo.cmm.service.MessageService;
import kr.co.abcmart.bo.cmm.vo.MessageVO;
import kr.co.abcmart.bo.display.model.master.CmStore;
import kr.co.abcmart.bo.display.service.StoreService;
import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.repository.master.MbMemberDao;
import kr.co.abcmart.bo.member.repository.master.MbMemberExpostSavePointDao;
import kr.co.abcmart.bo.order.model.master.IfOffDealHistory;
import kr.co.abcmart.bo.order.model.master.IfOrder;
import kr.co.abcmart.bo.order.model.master.OcCashReceipt;
import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.bo.order.model.master.OcOrderConvenienceStore;
import kr.co.abcmart.bo.order.model.master.OcOrderDeliveryHistory;
import kr.co.abcmart.bo.order.model.master.OcOrderMemo;
import kr.co.abcmart.bo.order.model.master.OcOrderPayment;
import kr.co.abcmart.bo.order.model.master.OcOrderPaymentFailureHistory;
import kr.co.abcmart.bo.order.model.master.OcOrderProduct;
import kr.co.abcmart.bo.order.model.master.OcOrderProductApplyPromotion;
import kr.co.abcmart.bo.order.model.master.OcOrderProductHistory;
import kr.co.abcmart.bo.order.model.master.OcOrderReceiverChangeHistory;
import kr.co.abcmart.bo.order.model.master.OcOrderZeroBankbookFailureDetails;
import kr.co.abcmart.bo.order.repository.master.IfOffDealHistoryDao;
import kr.co.abcmart.bo.order.repository.master.IfOrderDao;
import kr.co.abcmart.bo.order.repository.master.OcCashReceiptDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderConvenienceStoreDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderDeliveryHistoryDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderMemoDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderPaymentDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderPaymentFailureHistoryDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderProductApplyPromotionDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderProductDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderProductHistoryDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderReceiverChangeHistoryDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderUseCouponDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderZeroBankbookFailureDetailsDao;
import kr.co.abcmart.bo.order.vo.OcOrderDiscountVO;
import kr.co.abcmart.bo.order.vo.OcOrderExcelVo;
import kr.co.abcmart.bo.order.vo.OcOrderMemberExpostSaveVO;
import kr.co.abcmart.bo.order.vo.OrderStatVO;
import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductOption;
import kr.co.abcmart.bo.product.model.master.PdProductOptionStock;
import kr.co.abcmart.bo.product.model.master.PdProductOptionWithStockAndPrice;
import kr.co.abcmart.bo.product.repository.master.PdProductDao;
import kr.co.abcmart.bo.product.repository.master.PdProductOptionDao;
import kr.co.abcmart.bo.product.service.ProductInsideOptionService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.constant.MessageCode;
import kr.co.abcmart.interfaces.module.member.MembershipPointService;
import kr.co.abcmart.interfaces.module.member.model.BuyFixProduct;
import kr.co.abcmart.interfaces.module.order.model.OrderNumberGetDelivery;
import kr.co.abcmart.interfaces.module.order.service.OrderNumberGetDeliveryService;
import kr.co.abcmart.interfaces.module.payment.base.PaymentException;
import kr.co.abcmart.interfaces.module.payment.base.PaymentResult;
import kr.co.abcmart.interfaces.module.payment.base.model.PaymentInfo;
import kr.co.abcmart.interfaces.module.payment.kakao.KakaoPaymentService;
import kr.co.abcmart.interfaces.module.payment.kakao.model.KakaoPaymentOrder;
import kr.co.abcmart.interfaces.module.payment.kakao.model.KakaoPaymentOrderReturn;
import kr.co.abcmart.interfaces.module.payment.kakao.model.KakaoPaymentOrderReturn.KakaoCardInfo;
import kr.co.abcmart.interfaces.module.payment.kakao.model.KakaoPaymentOrderReturn.KakaoPayDetailList;
import kr.co.abcmart.interfaces.module.payment.naver.NaverPaymentService;
import kr.co.abcmart.interfaces.module.payment.naver.model.NaverPaymentCashAmountReturn;
import kr.co.abcmart.interfaces.module.payment.naver.model.NaverPaymentListReturn;
import kr.co.abcmart.interfaces.zinterfacesdb.service.InterfacesDeliveryService;
import kr.co.abcmart.interfaces.zinterfacesdb.service.InterfacesOrderService;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService {

	@Autowired
	private OcOrderMemoDao memoDao;

	@Autowired
	private OcOrderDao ocOrderDao;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private NaverPaymentService naverService;

	@Autowired
	private ClaimService claimService;

	@Autowired
	private AfterServiceService afterserviceservice;

	@Autowired
	private OcOrderPaymentDao ocOrderPaymentDao;

	@Autowired
	private OcOrderProductDao ocOrderProductDao;

	@Autowired
	private OcOrderProductHistoryDao ocOrderProductHistoryDao;

	@Autowired
	private OcOrderUseCouponDao ocOrderUseCouponDao;

	@Autowired
	private OcOrderDeliveryHistoryDao ocOrderDeliveryHistoryDao;

	@Autowired
	private MbMemberDao mbMemberDao;

	@Autowired
	private OcOrderProductApplyPromotionDao ocOrderProductApplyPromotionDao;

	@Autowired
	private OcOrderConvenienceStoreDao ocOrderConvenienceStoreDao;

	@Autowired
	private MbMemberExpostSavePointDao mbMemberExpostSavePointDao;

	@Autowired
	private OcOrderPaymentFailureHistoryDao ocOrderPaymentFailureHistoryDao;

	@Autowired
	private OcOrderZeroBankbookFailureDetailsDao ocOrderZeroBankbookFailureDetailsDao;

	@Autowired
	private StoreService storeService;

	@Autowired
	private BdInquiryService bdInquiryService;

	@Autowired
	private IfOffDealHistoryDao ifOffDealHistoryDao;

	@Autowired
	private OcOrderReceiverChangeHistoryDao ocOrderReceiverChangeHistoryDao;

	@Autowired
	private KakaoPaymentService kakaoPaymentService;

	@Autowired
	private ProductInsideOptionService productInsideOptionService;

	@Autowired
	PdProductOptionDao pdProductOptionDao;

	@Autowired
	InterfacesDeliveryService interfacesDeliveryService;

	@Autowired
	MessageService messageService;

	@Autowired
	private MembershipPointService membershipPointService;

	@Autowired
	OrderNumberGetDeliveryService orderNumberGetDeliveryService;

	@Autowired
	InterfacesOrderService interfacesOrderService;

	@Autowired
	OrderMessageService orderMessageService;

	@Autowired
	PdProductDao pdProductDao;

	@Autowired
	OcCashReceiptDao ocCashReceiptDao;

	@Autowired
	IfOrderDao ifOrderDao;

	@Autowired
	OrderService orderService;

	@Autowired
	OcClaimProductDao ocClaimProductDao;

	/**
	 * @Desc : 주문번호 신규 채번 , yyyyMMdd + 5자리Seq(5자리가 안될경우 Seq 앞으로 ZeroFill)
	 * @Method Name : createOrderSeq
	 * @Date : 2019. 4. 22.
	 * @Author : ljyoung@3top.co.kr
	 * @return
	 * @throws Exception
	 */
	public String createOrderSeq() throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS);
		Date toDay = Calendar.getInstance().getTime();
		String orderNo = dateFormat.format(toDay);
		orderNo = orderNo.concat(String.format("%05d", ocOrderDao.selectOrderNoNextVal()));
		return orderNo;
	}

	/*************************************************************************************************
	 * jeon start
	 *************************************************************************************************/
	/**
	 * @Desc :
	 * @Method Name : getOrderInfoMain
	 * @Date : 2019. 2. 19.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 */
	public void getOrderInfoMain(Parameter<?> parameter) throws Exception {
		String[] codeFields = { CommonCode.DEVICE_CODE // 디바이스코드
				, CommonCode.PYMNT_MEANS_CODE // 결제수단코드
				, CommonCode.STOCK_GBN_CODE // 발송처
				, CommonCode.ORDER_PRDT_STAT_CODE // 주문배송상태
				, CommonCode.DLVY_TYPE_CODE // 주문배송유형
				, CommonCode.MEMBER_TYPE_CODE // 회원유형
				, CommonCode.MBSHP_GRADE_CODE // 회원등급
				, CommonCode.ORDER_STAT_CODE // 주문 상태 코드
				, CommonCode.DLVY_STAT_CODE // 배송상태코드
		};

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		Pair<JSONObject, List<SySite>> siteInfo = siteService.getSiteListByCombo();

		// 전시채널 조회
		parameter.addAttribute("channelList", siteService.getUseChannelList());
		parameter.addAttribute("codeCombo", pair.getFirst());
		parameter.addAttribute("codeList", pair.getSecond());

		parameter.addAttribute("siteCombo", siteInfo.getFirst());
		parameter.addAttribute("siteInfo", siteInfo.getSecond());

	}

	/**
	 * @Desc : 주문 목록 데이터 조회
	 * @Method Name : getOrderList
	 * @Date : 2019. 2. 15.
	 * @Author : flychani@3top.co.kr
	 * @param orderVOPageble
	 * @return
	 */
	public Page<OcOrder> getOrderList(Pageable<OcOrder, OcOrder> pageable) throws Exception {

//		 관리자 담당 사이트 조회
//		 UserDetails user = LoginManager.getUserDetails();
//		 List<SyAdminManagementSite> authSiteList = user.getAuthSiteList();
//
//		 pageable.getBean().setAuthSiteList(authSiteList.stream().map(SyAdminManagementSite::getSiteNo).collect(		 Collectors.toList()));

		log.debug("::::::::::::pageable.cpnDscntAmtYn::::::::" + pageable.getBean().getCpnDscntAmtYn() + "...");

		int count = ocOrderDao.selectOrderListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			List<OcOrder> orderList = ocOrderDao.selectOrderList(pageable);

			// 주문 번호 배열 처리
			String[] orderNos = orderList.stream().map(OcOrder::getOrderNo).collect(Collectors.<String>toList())
					.toArray(new String[orderList.size()]);

			// 주문상품 조회
			Map<String, List<OcOrderProduct>> productList = selectProductOrderGrouping(orderNos);

			// 결제수단 조회
			Map<String, List<OcOrderPayment>> paymentList = selectPaymentOrderGrouping(orderNos);

			// 배송 목록조회
			Map<String, List<OcOrderDeliveryHistory>> productDelveryList = selectOrderDelveryOrderGrouping(orderNos);

			for (OcOrder order : orderList) {
				int i = 0;
				paymentList.forEach((k, v) -> {
					if (order.getOrderNo().equals(k)) {
						// 결제 수단 , 결제 완료 상태 ( 주문 수단 변경 때문에)
						if (v.get(i).getPymntMeansChngDtm() != null) {

							order.setPymntMeansCodeName(v.stream().filter(a -> UtilsText.equals(a.getPymntStatCode(),
									CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH)
									|| UtilsText.equals(a.getPymntStatCode(), CommonCode.PYMNT_STAT_CODE_WAIT_DEPOSIT))
									.map(OcOrderPayment::getPymntMeansCodeName).collect(Collectors.joining("|")));
						} else {
							order.setPymntMeansCodeName(v.stream().map(OcOrderPayment::getPymntMeansCodeName)
									.collect(Collectors.joining("|")));
						}
						// 결제 완료일 최초 결제완료일 기준 0
						order.setPymntDtm(v.stream().filter(a -> a.getMainPymntMeansYn().equals("Y"))
								.map(OcOrderPayment::getPymntDtm).findFirst().orElse(null));
					}
				});

				productDelveryList.forEach((k, v) -> {
					if (order.getOrderNo().equals(k)) {
						// 발송처 조회
						order.setStockGbnCodeName(v.stream().filter(dlvy -> dlvy.getClmNo() == null) // clmNo is null
								.collect(Collectors.groupingBy(OcOrderDeliveryHistory::getOrderPrdtSeq,
										Collectors.maxBy(
												Comparator.comparing(OcOrderDeliveryHistory::getOrderDlvyHistSeq))))
								.entrySet().stream().map(e -> e.getValue().get().getStockGbnCodeName()).distinct()
								.collect(Collectors.joining("|")));
					}
				});

				productList.forEach((k, v) -> {
					if (order.getOrderNo().equals(k)) {
						Long prdtCancelCnt = v.stream().filter(p -> UtilsText.equals(p.getOrderPrdtStatCode(),
								CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE)).count();
						if (UtilsText.equals(order.getOrderStatCode(), CommonCode.ORDER_STAT_CODE_COMPLETE)) { // 결제 완료
																												// && 상품
																												// 취소건
																												// 조회
							if (prdtCancelCnt > 0) {
								// 부분 취소 상품
								order.setOrderCancelFlag("part");
							}
						} else if (UtilsText.equals(order.getOrderStatCode(),
								CommonCode.ORDER_STAT_CODE_CANCEL_COMPLETE)) { // 전체 취소
							order.setOrderCancelFlag("total");
						} else {
							order.setOrderCancelFlag("");
						}

						// 택배전환 상품기준으로 조회 --> 변경된 건수가 있으면 주문 목록에 "택배전환" 으로 노출 처리
						Long logisCnvrtYCnt = v.stream()
								.filter(p -> UtilsText.equals(p.getLogisCnvrtYn(), Const.BOOLEAN_TRUE)).count();
						order.setLogisCnvrtCnt(logisCnvrtYCnt);
						if (logisCnvrtYCnt > 0) {
							order.setDlvyTypeCode("99999");
						}
					}
				});
			}

			pageable.setContent(orderList);
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 주문 상품조회 주문별 grouping
	 * @Method Name : selectProductOrderGrouping
	 * @Date : 2019. 3. 20.
	 * @Author : flychani@3top.co.kr
	 * @param orderNos
	 * @return
	 */
	private Map<String, List<OcOrderProduct>> selectProductOrderGrouping(String[] orderNos) throws Exception {
		OcOrderProduct ocOrderProduct = new OcOrderProduct();
		ocOrderProduct.setOrderNos(orderNos);
		List<OcOrderProduct> list = ocOrderProductDao.selectOrderProductForOrderNos(ocOrderProduct);

		return list.stream().collect(Collectors.groupingBy(OcOrderProduct::getOrderNo));
	}

	/**
	 * @Desc : 주문 배송 목록 조회 (주문번호별 grouping)
	 * @Method Name : selectOrderDelveryArr
	 * @Date : 2019. 2. 17.
	 * @Author : flychani@3top.co.kr
	 * @param orderNos
	 * @return
	 */
	private Map<String, List<OcOrderDeliveryHistory>> selectOrderDelveryOrderGrouping(String[] orderNos)
			throws Exception {

		OcOrderDeliveryHistory ocOrderDeliveryHistory = new OcOrderDeliveryHistory();
		ocOrderDeliveryHistory.setOrderNos(orderNos);

		List<OcOrderDeliveryHistory> list = ocOrderDeliveryHistoryDao.selectDeliveryHistoryList(ocOrderDeliveryHistory);

		if (list == null) {
			return null;
		}
		return list.stream().collect(Collectors.groupingBy(OcOrderDeliveryHistory::getOrderNo));
	}

	/**
	 * @Desc : 주문 결제 수단 목록 조회 (주문번호별 grouping)
	 * @Method Name : selectPaymentArr
	 * @Date : 2019. 2. 15.
	 * @Author : flychani@3top.co.kr
	 * @param orderNos
	 * @return
	 */
	private Map<String, List<OcOrderPayment>> selectPaymentOrderGrouping(String[] orderNos) throws Exception {

		OcOrderPayment ocOrderPayment = new OcOrderPayment();
		ocOrderPayment.setOrderNos(orderNos);

		List<OcOrderPayment> list = ocOrderPaymentDao.selectPaymentList(ocOrderPayment);

		if (list == null) {
			return null;
		}
		return list.stream().collect(Collectors.groupingBy(OcOrderPayment::getOrderNo));

	}

	/**
	 * @Desc : 주문 결제 수단 목록 조회
	 * @Method Name : selectPaymentList
	 * @Date : 2019. 2. 15.
	 * @Author : flychani@3top.co.kr
	 * @param orderNo
	 * @return
	 */
	private List<OcOrderPayment> selectPaymentList(String orderNo) throws Exception {
		OcOrderPayment ocOrderPayment = new OcOrderPayment();
		ocOrderPayment.setOrderNo(orderNo);
		List<OcOrderPayment> paymentList = ocOrderPaymentDao.selectPaymentList(ocOrderPayment);
		return paymentList;
	}

	/**
	 * @Desc : 주문 상세 조회
	 * @Method Name : getOrderDetailInfo
	 * @Date : 2019. 2. 22.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 */
	public void getOrderDetailInfo(Parameter<OcOrder> parameter) throws Exception {

		String[] codeFields = { CommonCode.BANK_CODE }; // 은헁코드

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		OcOrder ocOrder = parameter.get();
		String orderNo = parameter.get().getOrderNo();

		// 1. 주문 마스터 정보 조회
		OcOrder orderDtail = ocOrderDao.selectOrderDetail(ocOrder);
		String memberNo = orderDtail.getMemberNo();

		// 2020.01.15 : 구매확정 가능 여부 체크 (버튼 컨트롤)
		String orderAllConfirmYn = this.orderStatActionValue(orderNo);
		parameter.addAttribute("orderAllConfirmYn", orderAllConfirmYn);

//		List<OcOrder> relationOrderList = ocOrderDao.selectRelationOrderList(ocOrder);
//
//		long cancelSumAmt = relationOrderList.stream()
//				.filter(f -> UtilsText.equals(f.getSalesCnclGbnType(), CommonCode.SALES_CNCL_GBN_TYPE_CANCEL))
//				.mapToLong(o -> o.getCnclAmt()).sum();
//		orderDtail.setCancelSumAmt(cancelSumAmt);

		// 테스트 용
		// memberNo = "MB00000002";
		// 회원 기본 정보 조회
		MbMember memberInfo = mbMemberDao.selectMemberDefalutInfo(memberNo);
		// 회원 부가 정보 조회
		// List<MbMemberAddInfo> memberAddInfo =
		// mbMemberAddInfoDao.selectMemberAddInfo(memberNo);
		orderDtail.setLoginId(memberInfo.getLoginId());

		// 주문 상품 조회
		OcOrderProduct ocOrderProduct = new OcOrderProduct();
		ocOrderProduct.setOrderNo(orderNo);
		List<OcOrderProduct> orderProductList = ocOrderProductDao.selectOrderProductForOrderNos(ocOrderProduct);

		// 주문 상품 입점사 상품 조회 후 해당 입점사 조회
		List<OcOrderProduct> orderVendorList = ocOrderProductDao.selectOrderVendorList(orderNo);

		// 클레임 건수 조회 (원주문번호 기준)
		OcClaimProduct ocClaimProduct = new OcClaimProduct();
		ocClaimProduct.setOrgOrderNo(orderNo);

		List<OcClaimProduct> clmPrdtList = claimService.selectClaimProductListForOrder(ocClaimProduct);

		// CLM_GBN_CODE
		/**
		 * -- 취소 10000 -- 교환 10001 -- 반품 10002 10000 취소접수 10006 교환접수 10018 반품접수 10001
		 * 자동접수취소 10007 자동접수취소 10019 자동접수취소 10002 취소접수철회 10008 교환접수철회 10020 반품접수철회 10003
		 * 환수접수 10009 수거지시 10021 수거지시 10004 환불접수 10010 수령완료 10022 수령완료 10005 취소완료 10011
		 * 심의완료 10023 심의완료 10012 환수접수 10024 환수접수 10013 환불접수 10025 환불접수 10014 교환배송지시
		 * 10026 반품완료 10015 교환배송중 10027 반품불가 10016 교환완료 10017 교환불가 CLM_GBN_CODE
		 * CLM_PRDT_STAT_CODE
		 */
		Long exchangeCnt = clmPrdtList.stream().filter(c -> UtilsText.equals(c.getClmGbnCode(),
				CommonCode.CLM_GBN_CODE_EXCHANGE) // 교환
				&& (!UtilsText.equals(c.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL) // 교환접수취소
						&& !UtilsText.equals(c.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT) // 교환접수철회
						&& UtilsObject.isEmpty(c.getUpClmPrdtSeq()))) // UP_CLM_PRDT_SEQ 가 null 인 교환상품
				.count();

		Long returnCnt = clmPrdtList.stream().filter(c -> UtilsText.equals(c.getClmGbnCode(),
				CommonCode.CLM_GBN_CODE_RETURN) // 반품
				&& (!UtilsText.equals(c.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL) // 반품접수취소
						&& !UtilsText.equals(c.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT))) // 반품접수철회
				.count();

		Long exchangeCancelCnt = clmPrdtList
				.stream().filter(c -> UtilsText.equals(c.getClmGbnCode(), CommonCode.CLM_GBN_CODE_EXCHANGE) // 교환
						&& UtilsText.equals(c.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT)// 교환접수철회
						&& UtilsObject.isEmpty(c.getUpClmPrdtSeq())) // UP_CLM_PRDT_SEQ 가 null 인 교환상품
				.count();

		Long returnCancelCnt = clmPrdtList
				.stream().filter(c -> UtilsText.equals(c.getClmGbnCode(), CommonCode.CLM_GBN_CODE_RETURN) // 반품
						&& UtilsText.equals(c.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)) // 반품접수철회
				.count();

		// 2020.04.29 : 원주문번호에 걸린 클레임중 취소접수된 건들을 조회하여 취소완료전에는 접수 못하도록 하기 위해 조회
		Long cancelAcceptCnt = clmPrdtList
				.stream().filter(c -> UtilsText.equals(c.getClmGbnCode(), CommonCode.CLM_GBN_CODE_CANCEL) // 취소
						&& UtilsText.equals(c.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT)) // 취소 접수
				.count();
		
		orderDtail.setExchangeCnt(exchangeCnt);
		orderDtail.setReturnCnt(returnCnt);
		orderDtail.setExchangeCancelCnt(exchangeCancelCnt);
		orderDtail.setReturnCancelCnt(returnCancelCnt);
		orderDtail.setCancelAcceptCnt(cancelAcceptCnt);

		// as 건수
		OcAsAccept ocAsAccept = new OcAsAccept();
		ocAsAccept.setOrgOrderNo(orderNo);
		List<OcAsAccept> afterServiceList = afterserviceservice.selectASListForOrgOrder(ocAsAccept);

		Long asAcceptCnt = afterServiceList.stream().count();
		orderDtail.setAsAcceptCnt(asAcceptCnt);

		// 관리자 메모 건수 조회
		OcOrderMemo ocOrderMemo = new OcOrderMemo();
		ocOrderMemo.setOrderNo(orderNo);

		int memoTotCnt = memoCountAllByOrderNo(ocOrderMemo);
		orderDtail.setMemoTotCnt(memoTotCnt);

		// 은행코드 조회
		parameter.addAttribute("codeList", pair.getSecond()); // 코드 정보 ( 은행 코드 )

		// 2020.02.15 : 주결제 수단 - 에스크로 적용여부를 알기 위하여
		parameter.addAttribute("mainPayment", this.getMainPayment(orderNo));

		long onlyOrderProductCnt = orderProductList.stream()
				.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
						&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
				.count();
		parameter.addAttribute("onlyOrderProductCnt", onlyOrderProductCnt);

		// addAttribute
		parameter.addAttribute("orderDtail", orderDtail);
		parameter.addAttribute("orderVendorList", orderVendorList);

		parameter.addAttribute("isPrivateAuth", LoginManager.getUserDetails().getMemberInfoMgmtYn());

	}

	/**
	 * @Desc : 편의점 정보 조회
	 * @Method Name : selectConvenienceStore
	 * @Date : 2019. 3. 3.
	 * @Author : flychani@3top.co.kr
	 * @param cvs
	 * @return
	 */
	private List<OcOrderConvenienceStore> selectConvenienceStore(OcOrderConvenienceStore cvs) throws Exception {
		List<OcOrderConvenienceStore> cvsList = ocOrderConvenienceStoreDao.selectConvenienceStore(cvs);
		return cvsList;
	}

	/**
	 * @Desc : 주문정보 tab기본 정보 조회
	 * @Method Name : getReadOrderInfoTab
	 * @Date : 2019. 2. 24.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 */
	public void getReadOrderInfoTab(Parameter<OcOrder> parameter) throws Exception {

		String orderNo = parameter.getString("orderNo");
		OcOrder ocOrder = parameter.get();
		// 1. 주문 마스터 정보 조회
		OcOrder orderDtail = ocOrderDao.selectOrderDetail(ocOrder);

		String[] codeFields = { CommonCode.CVNSTR_CODE // 편의점 코드
				, CommonCode.STOCK_GBN_CODE // 재고 구분 코드
				, CommonCode.LOGIS_VNDR_CODE // 택배사코드
				, CommonCode.ORDER_PRDT_STAT_CODE // 주문상품상태코드
				, CommonCode.DLVY_STAT_CODE // 배송상태코드
				, CommonCode.DLVY_DSCNTC_RSN_CODE // 배송 중단 사유코드
				, CommonCode.PYMNT_STAT_CODE // 결제상태코드
				, CommonCode.PYMNT_MEANS_CODE // 결제수단코드
				, CommonCode.DEVICE_CODE // 디바이스코드
				, CommonCode.DLVY_DSCNTC_RSN_CODE // 배송중단사유코드
				, CommonCode.CLM_GBN_CODE // 클레임유형코드
				, CommonCode.SELL_CNCL_RSN_CODE // 판매취소요청코드 CommonCode.SELL_CNCL_RSN_CODE
		};

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		String dlvyTypeCode = orderDtail.getDlvyTypeCode();

		if (UtilsText.equals(dlvyTypeCode, CommonCode.DLVY_TYPE_CODE_CONVENIENCE_PICKUP)) { // 편의점 정보 조회
			// OC_ORDER_CONVENIENCE_STORE
			OcOrderConvenienceStore cvs = new OcOrderConvenienceStore();
			cvs.setOrderNo(orderNo);
			List<OcOrderConvenienceStore> cvsList = this.selectConvenienceStore(cvs);

			// 주문번호 기준 편의점 테이블에서 최대값으로 추출
			OcOrderConvenienceStore cvsInfo = cvsList.stream()
					.sorted(Comparator.comparing(OcOrderConvenienceStore::getCvnstrSeq).reversed()).findFirst()
					.orElse(null);
			parameter.addAttribute("cvsInfo", cvsInfo);

		} else if (UtilsText.equals(dlvyTypeCode, CommonCode.DLVY_TYPE_CODE_STORE_PICKUP)) { // 매장 상세 정보 조회 ?
			CmStore cmStore = new CmStore();
			// 주문 마스터의 상점번호 기준으로 조회함
			cmStore.setStoreNo(orderDtail.getStoreNo());
			CmStore storeInfo = storeService.selectCmStoreInfo(cmStore);
			parameter.addAttribute("storeInfo", storeInfo);
		}

		OcOrderProduct ocOrderProduct = new OcOrderProduct();
		ocOrderProduct.setOrderNo(orderNo);
		List<OcOrderProduct> prdtList = ocOrderProductDao.selectOrderProductForOrderNos(ocOrderProduct);

		Long logisCnvrtYCnt = prdtList.stream().filter(p -> UtilsText.equals(p.getLogisCnvrtYn(), Const.BOOLEAN_TRUE))
				.count();

		parameter.addAttribute("orderDtail", orderDtail);
		parameter.addAttribute("logisCnvrtYCnt", logisCnvrtYCnt); // 택배전환
		parameter.addAttribute("codeCombo", pair.getFirst()); // 코드 정보 combo
		parameter.addAttribute("codeList", pair.getSecond()); // 코드 정보 list

	}

	/**
	 * @Desc : 상품정보 목록 조회
	 * @Method Name : getReadOrderProductList
	 * @Date : 2019. 2. 24.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @return
	 */
	public List<OcOrderProduct> getReadOrderProductList(Parameter<?> parameter) throws Exception {
		// 교환주문건 포함하여 주문 조회
		String orderNo = parameter.getString("orderNo");
		String mmnyPrdtYn = parameter.getString("mmnyPrdtYn");

		String dlvyTypeCode = parameter.getString("dlvyTypeCode");

		List<OcOrderProduct> rtnProductList = new ArrayList<>(); // 상품정보 리턴 리스트

		OcOrderProduct ocOrderProduct = new OcOrderProduct();
		// ocOrderProduct.setOrderNo(orderNo);
		ocOrderProduct.setOrgOrderNo(orderNo);
		ocOrderProduct.setMmnyPrdtYn(mmnyPrdtYn);

		String[] salesCnclGbnTypes = { CommonCode.SALES_CNCL_GBN_TYPE_SALE,
				CommonCode.SALES_CNCL_GBN_TYPE_EXCHANGE_SALE };
		ocOrderProduct.setSalesCnclGbnTypes(salesCnclGbnTypes);

		// 클레임 제외 상태 코드 배열 처리 클레임 마스터 기준으로 처리 추후 변경시 클레임 상품상태값으로 변경 여지 있음
		String[] clmPrdtStatCodes = { CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_CANCEL_WITHDRAWAL_ACCEPT // 취소접수취소 , 취소접수철회
				, CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE// 교환접수취소 , 교환접수철회 , 교환불가
				, CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT // 반품접수취소 , 반품접수철회
				// 2020.03.17 : 반품불가
				, CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE };
		ocOrderProduct.setClmPrdtStatCodes(clmPrdtStatCodes);
		ocOrderProduct.setPrdtTypeCode(CommonCode.PRDT_TYPE_CODE_GIFT);

		// 주문 상품 목록 조회
		List<OcOrderProduct> productList = ocOrderProductDao.selectProductList(ocOrderProduct);

		// 주문 상품 배송 조회 각 상품별 MAX

		String[] orderNos = productList.stream().map(OcOrderProduct::getOrderNo).distinct().toArray(String[]::new);

		OcOrderDeliveryHistory ocOrderDeliveryHistory = new OcOrderDeliveryHistory();
		ocOrderDeliveryHistory.setOrderNos(orderNos);

		List<OcOrderDeliveryHistory> deliveryList = selectDeliveryHistoryMaxList(ocOrderDeliveryHistory);

		// 차수 , 1차 드랍사유 , 2차 드랍 사유는 interface 처리 되어야 함 // 배송목록 표현은 max로 처리
		productList.forEach(x -> deliveryList.forEach(y -> {
			if (x.getOrderNo().equals(y.getOrderNo()) && x.getOrderPrdtSeq().equals(y.getOrderPrdtSeq())) {
				// 배송 id
				x.setOrderDlvyHistSeq(y.getOrderDlvyHistSeq());
				// 택배사
				x.setLogisVndrCode(y.getLogisVndrCode());
				// 송장번호
				x.setWaybilNoText(y.getWaybilNoText());
				// 배송분실처리
				x.setMissProcYn(y.getMissProcYn());
				// 배송중단여부
				x.setDlvyDscntcYn(y.getDlvyDscntcYn());
				// 배송중단사유코드
				x.setDlvyDscntcRsnCode(y.getDlvyDscntcRsnCode());
				// 배송중단처리일시
				x.setDlvyDscntcProcDtm(y.getDlvyDscntcProcDtm());
				// 배송처리일시
				x.setDlvyProcDtm(y.getDlvyProcDtm());
				// 발송처
				x.setStockGbnCode(y.getStockGbnCode());
				// 매장번호
				x.setStoreNo(y.getStoreNo());
				// 매장명
				x.setStoreName(y.getStoreName());
				// 배송ID
				x.setDlvyIdText(y.getDlvyIdText());
				// 발송처 (notCombo)
				x.setOrgStockGbnCode(y.getOrgStockGbnCode());
				// 픽업가능일자
				x.setPickupPsbltYmd(y.getPickupPsbltYmd());
				// 배송상태코드 2020.04.10 추가
				x.setDlvyStatCode(y.getDlvyStatCode());
			}
		}));

		// 배송 drop 사유 interface
		if (UtilsText.equals(mmnyPrdtYn, "Y")) { // 자사상품
			if (UtilsText.equals(dlvyTypeCode, CommonCode.DLVY_TYPE_CODE_STORE_PICKUP)) { // 매장 픽업
				try {
					List<OrderNumberGetDelivery> asDlvyDropList = orderNumberGetDeliveryService
							.getDeliveryInfoForStockMerge(orderNo);
					log.debug("배송 drop 사유 asDlvyDropListChange" + asDlvyDropList);
					productList.forEach(x -> asDlvyDropList.forEach(y -> {
						if (UtilsText.equals(x.getDlvyIdText(), y.getDelvNo())) {
							x.setChasu(y.getChasu()); // 배송 차수
							x.setDropReason1(y.getReason1());
							x.setDropReason2(y.getReason2());
							x.setMemo(y.getMemo());
						}
					}));
				} catch (Exception e) {
					log.error("I/F 통신오류 발생하였습니다. 관리자에게 문의하세요.");
				}
			}
		}

		// as 접수 정보 조회

		OcAsAcceptProduct ocAsAcceptProduct = new OcAsAcceptProduct();
		ocAsAcceptProduct.setOrderNo(orderNo);
		String[] asPrdtStatCodes = { CommonCode.AS_PRDT_STAT_CODE_ACCEPT // A/S접수
				, CommonCode.AS_PRDT_STAT_CODE_ACCEPT_FINISH // A/S접수완료
				, CommonCode.AS_PRDT_STAT_CODE_PICKUP_ORDER // 수거지시
				, CommonCode.AS_PRDT_STAT_CODE_RECEIVE_FINISH // 수령완료
				, CommonCode.AS_PRDT_STAT_CODE_JUDGE_FINISH // 심의완료
				, CommonCode.AS_PRDT_STAT_CODE_REPAIR_FINISH // 수선완료
				, CommonCode.AS_PRDT_STAT_CODE_PAYMENT_REQUEST // 비용결제요청
				, CommonCode.AS_PRDT_STAT_CODE_PAYMENT_FINISH // 비용결제완료
				, CommonCode.AS_PRDT_STAT_CODE_SHIPPING // 배송중
		};

		ocAsAcceptProduct.setAsPrdtStatCodes(asPrdtStatCodes);
		List<OcAsAcceptProduct> afterServiceList = afterserviceservice.getOrderPrdtAsAccept(ocAsAcceptProduct);

		if (afterServiceList != null) {
			productList.forEach(x -> afterServiceList.forEach(y -> {
				if (UtilsText.equals(x.getOrderNo(), y.getOrderNo())
						&& x.getOrderPrdtSeq().equals(y.getOrderPrdtSeq())) {
					x.setPrdtAsAcceptFlag("Y"); // AS 접수 여부
					x.setAsAcceptNo(y.getAsAcceptNo()); // AS 접수 여부
				}
			}));
		}

		// 2020.03.30 : 주문배송상품의 배송분실처리 영역 표현 (배송비,사은품 제외)
		for (OcOrderProduct oop : productList) {
			OcOrderDeliveryHistory paramDlvy = new OcOrderDeliveryHistory();
			paramDlvy.setOrderNo(oop.getOrderNo());
			paramDlvy.setOrderPrdtSeq(oop.getOrderPrdtSeq());
			paramDlvy = ocOrderDeliveryHistoryDao.selectLastMissOrderDelivery(paramDlvy);

			if (paramDlvy == null) {
				oop.setMissProcYn(null);
			} else {
				if (UtilsText.equals(paramDlvy.getMissProcYn(), Const.BOOLEAN_FALSE)) {
					oop.setMissProcYn(null);
				} else {
					oop.setMissProcYn(paramDlvy.getMissProcYn());
					oop.setMissProcTypeCode(paramDlvy.getMissProcTypeCode());
					oop.setMissProcTypeCodeName(paramDlvy.getMissProcTypeCodeName());
				}
			}
		}

		rtnProductList.addAll(productList);

		/*
		 * 2020.04.23 수정 : 교환재배송상품 배송비상품 별도 조회 처리 add and sort
		 */
		OcOrderProduct ocOrderProductChange = new OcOrderProduct();

		ocOrderProductChange.setOrgOrderNo(orderNo);
		ocOrderProductChange.setMmnyPrdtYn(mmnyPrdtYn);
		ocOrderProductChange.setSalesCnclGbnTypes(salesCnclGbnTypes);

		ocOrderProductChange.setClmPrdtStatCodes(clmPrdtStatCodes);
		ocOrderProductChange.setPrdtTypeCode(CommonCode.PRDT_TYPE_CODE_GIFT);

		List<OcOrderProduct> productChangeList = ocOrderProductDao.selectProductChangeList(ocOrderProductChange);

		if (productChangeList.size() > 0) {
			// 배송정보 조회
			
			// 리오더 주문번호 목록
			String[] ChangeOrderNos = productChangeList.stream().map(OcOrderProduct::getOrderNo).distinct()
					.toArray(String[]::new);
			
			String[] reOrderNoParam = new String[1];
			OcOrderDeliveryHistory changeOrderDeliveryHistory = new OcOrderDeliveryHistory();
			List<OcOrderDeliveryHistory> changeDeliveryList = new ArrayList<>();
			
			// 2020.04.23 : 한 주문상품에 여러개의 리오더 교환상품이 발생할 수 있으므로 리오더주문번호를 분리해서 for문 돌린다.
			//               => 한 주문상품을 여러번 교환했을때, 배송정보가 set 안되던 문제 해결
			for(String reOrderNo : ChangeOrderNos) {
				reOrderNoParam[0] = reOrderNo;
				changeOrderDeliveryHistory.setOrderNos(reOrderNoParam);
				List<OcOrderDeliveryHistory> list = selectDeliveryHistoryMaxListClaim(changeOrderDeliveryHistory);
				
				for(OcOrderDeliveryHistory odh : list) {
					changeDeliveryList.add(odh);
				}
			}

			System.out.println("changeDeliveryList >>>>> " + changeDeliveryList);

			// 차수 , 1차 드랍사유 , 2차 드랍 사유는 interface 처리 되어야 함 // 배송목록 표현은 max로 처리
			productChangeList.forEach(x -> changeDeliveryList.forEach(y -> {
				if (x.getOrderNo().equals(y.getOrderNo()) && x.getOrderPrdtSeq().equals(y.getOrderPrdtSeq())) {
					// 배송 id
					x.setOrderDlvyHistSeq(y.getOrderDlvyHistSeq());
					// 택배사
					x.setLogisVndrCode(y.getLogisVndrCode());
					// 송장번호
					x.setWaybilNoText(y.getWaybilNoText());
					// 배송분실처리
					x.setMissProcYn(y.getMissProcYn());
					// 배송중단여부
					x.setDlvyDscntcYn(y.getDlvyDscntcYn());
					// 배송중단사유코드
					x.setDlvyDscntcRsnCode(y.getDlvyDscntcRsnCode());
					// 배송중단처리일시
					x.setDlvyProcDtm(y.getDlvyDscntcProcDtm());
					// 발송처
					x.setStockGbnCode(y.getStockGbnCode());
					// 매장번호
					x.setStoreNo(y.getStoreNo());
					// 매장명
					x.setStoreName(y.getStoreName());
					// 배송ID
					x.setDlvyIdText(y.getDlvyIdText());
					// 배송상태코드 2020.04.10 추가
					x.setDlvyStatCode(y.getDlvyStatCode());
				}
			}));

			// 상품 정보 for
			for (OcOrderProduct changePrdt : productChangeList) {

				String changeOrderNo = changePrdt.getOrderNo();
				String changeMmnyPrdtYn = changePrdt.getMmnyPrdtYn();

				String changeDlvyTypeCode = changePrdt.getDlvyTypeCode();

				// 배송 drop 사유 interface
				if (UtilsText.equals(changeMmnyPrdtYn, "Y")) { // 자사상품
					if (UtilsText.equals(changeDlvyTypeCode, CommonCode.DLVY_TYPE_CODE_STORE_PICKUP)) { // 매장 픽업

						// 2020.02.08 : 인터페이스 리턴 null로 받아 임시로 try/catch
						try {
							List<OrderNumberGetDelivery> asDlvyDropListChange = orderNumberGetDeliveryService
									.getDeliveryInfoForStockMerge(changeOrderNo);
							log.debug("배송 drop 사유 asDlvyDropListChange" + asDlvyDropListChange);

							for (OcOrderProduct x : productChangeList) {
								log.debug("getDlvyIdText : {}", x.getDlvyIdText());
								for (OrderNumberGetDelivery y : asDlvyDropListChange) {
									log.debug("getDelvNo : {}", y.getDelvNo());
									log.debug("getChasu : {}", y.getChasu());
									log.debug("getReason1 : {}", y.getReason1());
									log.debug("getReason2 : {}", y.getReason2());
								}
							}

							/*
							 * productChangeList.forEach(x -> asDlvyDropListChange.forEach(y -> { if
							 * (UtilsText.equals(x.getDlvyIdText(), y.getDelvNo())) {
							 * x.setChasu(y.getChasu()); // 배송 차수 x.setDropReason1(y.getReason1());
							 * x.setDropReason2(y.getReason2()); x.setMemo(y.getMemo()); } }));
							 */
						} catch (Exception e) {
							log.error("배송 drop 사유 interface error");
						}
					}
				}

				// as 접수 정보 조회

				OcAsAcceptProduct ocAsAcceptProductChange = new OcAsAcceptProduct();
				ocAsAcceptProductChange.setOrderNo(changeOrderNo);

				ocAsAcceptProductChange.setAsPrdtStatCodes(asPrdtStatCodes);
				List<OcAsAcceptProduct> afterServiceListChange = afterserviceservice
						.getOrderPrdtAsAccept(ocAsAcceptProductChange);

				if (afterServiceListChange != null) {
					productChangeList.forEach(x -> afterServiceListChange.forEach(y -> {
						if (UtilsText.equals(x.getOrderNo(), y.getOrderNo())
								&& x.getOrderPrdtSeq().equals(y.getOrderPrdtSeq())) {
							x.setPrdtAsAcceptFlag("Y"); // AS 접수 여부
							x.setAsAcceptNo(y.getAsAcceptNo()); // AS 접수 여부
						}
					}));
				}

			}

			// 2020.03.30 : 교환배송상품의 배송분실처리 영역 표현 (배송비,사은품 제외)
			for (OcOrderProduct oop : productChangeList) {
				OcOrderDeliveryHistory paramDlvy = new OcOrderDeliveryHistory();
				paramDlvy.setOrderNo(oop.getOrderNo());
				paramDlvy.setOrderPrdtSeq(oop.getOrderPrdtSeq());
				paramDlvy = ocOrderDeliveryHistoryDao.selectLastMissOrderDelivery(paramDlvy);

				if (paramDlvy == null) {
					oop.setMissProcYn(null);
				} else {
					if (UtilsText.equals(paramDlvy.getMissProcYn(), Const.BOOLEAN_FALSE)) {
						oop.setMissProcYn(null);
					} else {
						oop.setMissProcYn(paramDlvy.getMissProcYn());
						oop.setMissProcTypeCode(paramDlvy.getMissProcTypeCode());
						oop.setMissProcTypeCodeName(paramDlvy.getMissProcTypeCodeName());
					}
				}
			}

			rtnProductList.addAll(productChangeList);
		}

		return rtnProductList;
	}

	/**
	 * @Desc : 주문 배송 정보 조회 클레임건 제외
	 * @Method Name : selectDeliveryHistoryMaxList
	 * @Date : 2019. 3. 1.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderDeliveryHistory
	 * @return
	 */
	private List<OcOrderDeliveryHistory> selectDeliveryHistoryMaxList(OcOrderDeliveryHistory ocOrderDeliveryHistory)
			throws Exception {

		List<OcOrderDeliveryHistory> list = ocOrderDeliveryHistoryDao.selectDeliveryHistoryList(ocOrderDeliveryHistory);

		return list.stream().filter(dlvy -> dlvy.getClmNo() == null)
				.collect(Collectors.groupingBy(OcOrderDeliveryHistory::getOrderPrdtSeq,
						Collectors.maxBy(Comparator.comparing(OcOrderDeliveryHistory::getOrderDlvyHistSeq))))
				.entrySet().stream().map(e -> e.getValue().get()).collect(Collectors.toList());
	}

	/**
	 * @Desc : 주문 배송 정보 조회 클레임건 포함
	 * @Method Name : selectDeliveryHistoryMaxList
	 * @Date : 2019. 3. 1.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderDeliveryHistory
	 * @return
	 */
	private List<OcOrderDeliveryHistory> selectDeliveryHistoryMaxListClaim(OcOrderDeliveryHistory ocOrderDeliveryHistory) throws Exception {

		List<OcOrderDeliveryHistory> list = ocOrderDeliveryHistoryDao.selectDeliveryHistoryList(ocOrderDeliveryHistory);

		return list.stream().collect(
							Collectors.groupingBy(
									  OcOrderDeliveryHistory::getOrderPrdtSeq
									, Collectors.maxBy(Comparator.comparing(OcOrderDeliveryHistory::getOrderDlvyHistSeq))
							)
				)
				.entrySet().stream().map(e -> e.getValue().get()).collect(Collectors.toList());
	}

	/**
	 * @Desc : 할인 정보 ( 쿠폰 + 프로모션)
	 * @Method Name : getReadOrderDiscountList
	 * @Date : 2019. 2. 24.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @return
	 */
	public List<OcOrderDiscountVO> getReadOrderDiscountList(Parameter<?> parameter) throws Exception {

		String orderNo = parameter.getString("orderNo");
		String memberNo = parameter.getString("memberNo");

		List<OcOrderDiscountVO> finalList = new ArrayList<>();
		OcOrderDiscountVO ocOrderDiscount = new OcOrderDiscountVO();
		ocOrderDiscount.setOrderNo(orderNo);
		ocOrderDiscount.setMemberNo(memberNo);

		List<OcOrderDiscountVO> orderCouponList = ocOrderUseCouponDao.selectOrderCouponList(ocOrderDiscount);
		List<OcOrderDiscountVO> orderPromoList = ocOrderProductApplyPromotionDao.selectOrderPromoList(ocOrderDiscount);

		finalList.addAll(orderCouponList);
		finalList.addAll(orderPromoList);

		return finalList;
	}

	/**
	 * @Desc :결제 정보 상세
	 * @Method Name : getReadOrderPaymentList
	 * @Date : 2019. 2. 24.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @return
	 */
	public List<OcOrderPayment> getReadOrderPaymentList(Parameter<OcOrder> parameter) throws Exception {
		String orderNo = parameter.get().getOrderNo();

		OcOrderPayment ocOrderPayment = new OcOrderPayment();
		ocOrderPayment.setOrderNo(orderNo);

		List<OcOrderPayment> paymentList = ocOrderPaymentDao.selectPaymentList(ocOrderPayment);

		OcOrderPayment ocOrderPaymentData = ocOrderPaymentDao.selectPaymentInfo(ocOrderPayment);

		log.info("pymntMeansCnt" + ocOrderPaymentData.getPymntMeansCnt());
		log.info("changeCnt	" + ocOrderPaymentData.getChangeCnt());
		log.info("vendorPrdtCnt" + ocOrderPaymentData.getVendorPrdtCnt());
		log.info("claimCnt	" + ocOrderPaymentData.getClaimCnt());

		for (OcOrderPayment payment : paymentList) {

			// 클레임 접수건 카운트
			payment.setClaimCnt(ocOrderPaymentData.getClaimCnt());
			// 카드, 실시간 외 카운트
			payment.setPymntMeansCnt(ocOrderPaymentData.getPymntMeansCnt());
			// 결제 변경 카운트
			payment.setChangeCnt(ocOrderPaymentData.getChangeCnt());
			// 업체 상품 카운트
			payment.setVendorPrdtCnt(ocOrderPaymentData.getVendorPrdtCnt());

			if (UtilsText.equals(payment.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_MOBILE_GIFT)) { // 기프트 상품권 일경

				OcCashReceipt ocr = new OcCashReceipt();
				ocr.setOrderNo(orderNo);

				OcCashReceipt ocrInfo = ocCashReceiptDao.getGiftCashReceipt(ocr);

				if (ocrInfo != null) {
					payment.setRcptDealNoText(ocrInfo.getRcptDealNoText());
				}
			}
		}

		// 결제 수단 변경을 위한 조건
//		paymentList.stream().map(payment -> {
//			// 클레임 접수건 카운트
//			payment.setClaimCnt(ocOrderPaymentData.getClaimCnt());
//			// 카드, 실시간 외 카운트
//			payment.setPymntMeansCnt(ocOrderPaymentData.getPymntMeansCnt());
//			// 결제 변경 카운트
//			payment.setChangeCnt(ocOrderPaymentData.getChangeCnt());
//			// 업체 상품 카운트
//			payment.setVendorPrdtCnt(ocOrderPaymentData.getVendorPrdtCnt());
//			return payment;
//		}).collect(Collectors.toList());

		return paymentList;

	}

	/**
	 * @Desc : 구매확정 처리 - I/F포함
	 * @Method Name : updateOrderConfirm
	 * @Date : 2019. 6. 12.
	 * @Author : NKB
	 * @param mbMember
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateOrderConfirm(OcOrder ocOrder) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		String rtnValue = Const.BOOLEAN_FALSE;

		// 주문정보
		OcOrder ocOrderDetail = ocOrderDao.selectOrderDetail(ocOrder);

		// 회원 정보 조회
		MbMember mbMember = mbMemberDao.selectMemberDefalutInfo(ocOrderDetail.getMemberNo());

		// 상품정보
		OcOrderProduct ocOrderProduct = new OcOrderProduct();
		ocOrderProduct.setOrderNo(ocOrder.getOrderNo());
		List<OcOrderProduct> productList = ocOrderProductDao.selectOrderProductList(ocOrderProduct); // 주문상품 정보 조회

		// 구매확정 여부
		String orderAllConfirmYn = this.orderStatActionValue(ocOrder.getOrderNo());

		// 구매확정 불가인경우
		if (UtilsText.equals(orderAllConfirmYn, Const.BOOLEAN_FALSE)) {
			throw new Exception("validMsg:구매확정이 불가능한 상태입니다.");
		}

		// 주문관련 테이블 구매확정 set
		this.setOrderConfirm(ocOrder, ocOrderProduct, productList);

		// 주문자가 '맴버쉽 회원' 이라면
		if (UtilsText.equals(mbMember.getMemberTypeCode(), CommonCode.MEMBER_TYPE_CODE_MEMBERSHIP)) {
			// 구매확정시 포인트지급
			List<BuyFixProduct> buyFixProduct = membershipPointService.buyFixRequest(mbMember.getSafeKey(),
					mbMember.getSafeKeySeq(), ocOrderDetail.getOrderNo());
			// 구매확정시 list 가 비어 있으면 Exception 발생.
			if (buyFixProduct.isEmpty()) {
				throw new Exception("validMsg:구매확정시 포인트 지급 실패");
			} else {
				for (BuyFixProduct bFix : buyFixProduct) {
					log.debug("구매확정시 포인트 지급 > " + bFix.getSangpumFullCd() + " / " + bFix.getResultYn());

					if (UtilsText.equals(bFix.getResultYn(), Const.BOOLEAN_TRUE)) {
						rtnValue = Const.BOOLEAN_TRUE;
					} else {
						throw new Exception("validMsg:구매확정시 포인트 지급 실패");
					}
				}
			}
		}

		rtnValue = Const.BOOLEAN_TRUE;
		map.put("resultCode", rtnValue);

		return map;
	}

	/**
	 * @Desc : 구매확정 처리 - 주문관련테이블 set
	 * @Method Name : setOrderConfirm
	 * @Date : 2020. 1. 15.
	 * @Author : 이강수
	 * @param OcOrder
	 * @param OcOrderProduct
	 * @param                List<OcOrderProduct>
	 * @throws Exception
	 */
	public void setOrderConfirm(OcOrder ocOrder, OcOrderProduct ocOrderProduct, List<OcOrderProduct> productList)
			throws Exception {

		String adminNo = LoginManager.getUserDetails().getAdminNo();

		// 주문 마스터 일자 변경
		ocOrder.setModerNo(adminNo);
		ocOrderDao.updateOrderDcsnDtm(ocOrder);

		// 상품 상태변경
		ocOrderProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_BUY_CONFIRM); // 구매확정
		ocOrderProduct.setWhereOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_DELIVERY_FINISH); // 배송완료
		ocOrderProduct.setModerNo(adminNo);
		int prdUpdateCnt = ocOrderProductDao.updateOrderConfirm(ocOrderProduct);

		if (prdUpdateCnt > 0) {

			// 배송 상테변경
			OcOrderDeliveryHistory ocOrderDeliveryHistory = new OcOrderDeliveryHistory();
			ocOrderDeliveryHistory.setOrderNo(ocOrder.getOrderNo());
			ocOrderDeliveryHistory.setModerNo(adminNo);
			ocOrderDeliveryHistory.setDlvyStatCode(CommonCode.DLVY_STAT_CODE_BUY_CONFIRM); // 구매확정
			ocOrderDeliveryHistory.setWhereDlvyStatCode(CommonCode.DLVY_STAT_CODE_DELIVERY_FINISH); // 배송완료

			int dlyUpdateCnt = ocOrderDeliveryHistoryDao.updateOrderConfirm(ocOrderDeliveryHistory);

			if (dlyUpdateCnt > 0) {
				// 상품 이력 저장
				for (OcOrderProduct param : productList) {

					if (CommonCode.ORDER_PRDT_STAT_CODE_DELIVERY_FINISH.equals(param.getOrderPrdtStatCode())) {

						OcOrderProductHistory prdtHistory = new OcOrderProductHistory();
						prdtHistory.setOrderNo(param.getOrderNo());
						prdtHistory.setOrderPrdtSeq(param.getOrderPrdtSeq());
						prdtHistory.setPrdtNo(param.getPrdtNo());
						prdtHistory.setPrdtOptnNo(param.getPrdtOptnNo());
						prdtHistory.setPrdtName(param.getPrdtName());
						prdtHistory.setEngPrdtName(param.getEngPrdtName());
						prdtHistory.setOptnName(param.getOptnName());
						prdtHistory.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_BUY_CONFIRM); // 구매확정
						prdtHistory.setNoteText("구매확정 변경"); // NOTE_TEXT
						prdtHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo());

						ocOrderProductHistoryDao.insertProductHistory(prdtHistory);
					}
				} // end for
			}
		}
	}

	/**
	 * @Desc : 배송중단 처리
	 * @Method Name : UpdateDeliveryStop
	 * @Date : 2019. 3. 1.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderProduct
	 * @return
	 */
	public Map<String, Object> UpdateDeliveryStop(OcOrderProduct ocOrderProduct) throws Exception {
		Map<String, Object> result = new HashMap<>();
		UserDetails user = LoginManager.getUserDetails();

//		as-is
//		1. 배송상태 변경 00 상품대기중
//		2. 배송중단테이블 등록 TB_STOP_DELIVERY
//		3. 상품대기내역테이블에 등록  TB_DELIVERY_WAIT_HISTORY
//		4. 01 배송대기 , 03 상품준비중 , 04 상품출고
//			getWmsYn 전송 --> 프로시져 호출 , WMS_YN = 'S'
//

		int updCnt = 0;
		boolean rtnFlag = true;

		String resultCode = Const.BOOLEAN_TRUE;
		String resultMsg = "";

		String[] clmPrdtStatCodes = { CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_CANCEL_WITHDRAWAL_ACCEPT // 취소접수취소 , 취소접수철회
				, CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE// 교환접수취소 , 교환접수철회 , 교환불가
				, CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT // 반품접수취소 , 반품접수철회
		};

		// validate

		// 2020.04.23 : 노란 언더라인으로 안쓰이고 있기에 주석 처리
		//short orderDlvyHistSeq = ocOrderProduct.getOrderDlvyHistSeq();

		ocOrderProduct.setClmPrdtStatCodes(clmPrdtStatCodes);

		// 배송중단 처리시 상품 조회 selectProductDetailDeliveryStop으로 수정
		// OcOrderProduct prdtInfo =
		// ocOrderProductDao.selectProductDetail(ocOrderProduct);
		OcOrderProduct prdtInfo = ocOrderProductDao.selectProductDetailDeliveryStop(ocOrderProduct);
		// 상품배송상태 확인 미정 , 택배 전환여부 확인 필요

		/*
		 * if (prdtInfo.getClmNo() != null && !UtilsText.equals(prdtInfo.getClmNo(),
		 * "")) { resultCode = Const.BOOLEAN_FALSE; resultMsg = "클레임이 처리된 상품이 있습니다."; }
		 * 
		 * if (prdtInfo.getOrderDlvyHistSeq() != orderDlvyHistSeq) { resultCode =
		 * Const.BOOLEAN_FALSE; resultMsg = "배송이력이 추가된 상품이 있습니다."; }
		 */

		if (UtilsText.equals(resultCode, Const.BOOLEAN_TRUE)) {

			if (UtilsText.equals(prdtInfo.getDlvyStatCode(), CommonCode.DLVY_STAT_CODE_PAYMENT_FINISH) // 결제완료
					|| UtilsText.equals(prdtInfo.getDlvyStatCode(), CommonCode.DLVY_STAT_CODE_PRODUCT_PREPARATION) // 상품준비중
					|| UtilsText.equals(prdtInfo.getDlvyStatCode(), CommonCode.DLVY_STAT_CODE_PRODUCT_DELIVERY) // 상품출고
					|| UtilsText.equals(prdtInfo.getOrderPrdtStatCode(),
							CommonCode.ORDER_PRDT_STAT_CODE_PICKUP_PREPARATION_FINISH) // 픽업준비완료
			) {

				try {

					if (UtilsText.equals(prdtInfo.getWmsSendYn(), Const.BOOLEAN_TRUE)
							|| UtilsText.equals(prdtInfo.getWmsSendYn(), "I")) {

						Map<String, String> paramMap = new HashMap<>();
						paramMap.put("dlvyIdText", prdtInfo.getDlvyIdText());

						ocClaimProductDao.callProcedureForOrderHold(paramMap);
						String errorCode = String.valueOf(paramMap.get("errorCode"));
						log.error("callProcedureForOrderHold return >>> " + errorCode);

						// 배송중단 처리 실패
						if (!UtilsText.equals(errorCode, "0")) {
							resultCode = Const.BOOLEAN_FALSE;
							resultMsg = "배송중단처리(PR_ORDER_HOLD)에 실패하였습니다.";
							rtnFlag = false;
							// throw new Exception("배송중단처리(PR_ORDER_HOLD)에 실패하였습니다.");
						}

					}

					if (rtnFlag) {

						// 배송이력 테이블 배송중단 처리
						OcOrderDeliveryHistory ocOrderDeliveryHistory = new OcOrderDeliveryHistory();

						ocOrderDeliveryHistory.setOrderNo(ocOrderProduct.getOrderNo());
						ocOrderDeliveryHistory.setOrderPrdtSeq(ocOrderProduct.getOrderPrdtSeq());
						ocOrderDeliveryHistory.setOrderDlvyHistSeq(ocOrderProduct.getOrderDlvyHistSeq());
						ocOrderDeliveryHistory.setDlvyDscntcRsnCode(ocOrderProduct.getDlvyDscntcRsnCode());
						ocOrderDeliveryHistory.setDlvyDscntcYn(Const.BOOLEAN_TRUE);
						//ocOrderDeliveryHistory.setWmsSendYn("S"); // 배송 중단 처리시 WMS전송여부 "S" 처리
						ocOrderDeliveryHistory.setModerNo(user.getAdminNo());
						ocOrderDeliveryHistory.setDlvyDscntcOpetrNo(user.getAdminNo());
						
						//처리구분코드 추가  확인필요코드 
						ocOrderDeliveryHistory.setProcGubnCode(CommonCode.PROC_GUBN_CODE_CHECK_REQUIRE);

						updCnt = ocOrderDeliveryHistoryDao.UpdateDeliveryStop(ocOrderDeliveryHistory);

						if (updCnt > 0) {

							resultCode = Const.BOOLEAN_TRUE;
							resultMsg = "배송중단 처리 되었습니다.";
						} else {
							resultCode = Const.BOOLEAN_FALSE;
							resultMsg = "배송중단 실패 되었습니다.";
						}
					} else {
						resultCode = Const.BOOLEAN_FALSE;
						resultMsg = "배송중단 실패 되었습니다.(I)";
					}

				} catch (Exception e) {
					log.debug(e.toString());
					e.printStackTrace(System.err);
					result.put("result", Const.BOOLEAN_FALSE);
					resultCode = Const.BOOLEAN_FALSE;
					resultMsg = "배송중단 중 오류가 발생 되었습니다.";
					// throw new Exception("배송중단 중 오류가 발생 되었습니다.");
				}
			} else {
				resultCode = Const.BOOLEAN_FALSE;
				resultMsg = "배송중단 처리 가능 상태가 아닙니다.";
			}
		}

		result.put("resultCode", resultCode);
		result.put("resultMsg", resultMsg);

		return result;
	}

	/**
	 * @Desc : 주문 마스터 상세
	 * @Method Name : getOrderDetail
	 * @Date : 2019. 3. 2.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @return
	 */
	public OcOrder getOcOrderMstDetail(Parameter<OcOrder> parameter) throws Exception {
		OcOrder ocOrder = parameter.get();
		OcOrder orderDtail = ocOrderDao.selectOrderDetail(ocOrder);

		return orderDtail;
	}

	/**
	 * @Desc : 비회원 비밀번호 변경
	 * @Method Name : setPasswordChange
	 * @Date : 2019. 3. 2.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @return
	 */
	public Map<String, Object> updatePasswordChange(Parameter<?> parameter) throws Exception {
		Map<String, Object> result = new HashMap<>();
		OcOrder ocOrder = new OcOrder();
		// 세션정보
		UserDetails user = LoginManager.getUserDetails();

		String passwordText = parameter.getString("pswdText");

		// ==> 컬럼 변경시 추가
		ocOrder.setNmbrCrtfcNoText(passwordText);
		ocOrder.setModerNo(user.getAdminNo());
		ocOrder.setOrderNo(parameter.getString("orderNo"));

		int updCnt = ocOrderDao.updatePasswordChange(ocOrder);

		if (updCnt > 0) {
			result.put("result", Const.BOOLEAN_TRUE);
		} else {
			result.put("result", Const.BOOLEAN_FALSE);
		}

		// 파라메터 세팅
		return result;
	}

	/**
	 * @Desc :
	 * @Method Name : getOrderVaildation
	 * @Date : 2019. 3. 3.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderProduct
	 * @return
	 */
	public Map<String, Object> getOrderVaildateTypeOne(OcOrderProduct ocOrderProduct) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		String result = Const.BOOLEAN_TRUE;

		String[] clmPrdtStatCodes = { CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_CANCEL_WITHDRAWAL_ACCEPT // 취소접수취소 , 취소접수철회
				, CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE// 교환접수취소 , 교환접수철회 , 교환불가
				, CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT // 반품접수취소 , 반품접수철회
		};
		ocOrderProduct.setClmPrdtStatCodes(clmPrdtStatCodes);
		List<OcOrderProduct> productList = ocOrderProductDao.selectOrderProductValidationList(ocOrderProduct);

		int productTotCnt = productList.size();

		for (int i = 0, lCnt = productList.size(); i < lCnt; i++) {
			String orderPrdtStatCode = productList.get(i).getOrderPrdtStatCode(); // 주문상품상태코드
			String clmPrdtStatCode = productList.get(i).getClmPrdtStatCode() == null ? ""
					: productList.get(i).getClmPrdtStatCode();
			String sellCnclReqYn = productList.get(i).getSellCnclReqYn();

			if (!UtilsText.equals(orderPrdtStatCode, CommonCode.ORDER_PRDT_STAT_CODE_COMPLETE)) { // 결제완료 상태가 아닌 상품 확인
				result = Const.BOOLEAN_FALSE;
				break;
			}
			if (!UtilsText.equals(clmPrdtStatCode, "")) { // 접수중 or 처리된 클레임 확인
				result = Const.BOOLEAN_FALSE;
				break;
			}
			if (UtilsText.equals(sellCnclReqYn, Const.BOOLEAN_TRUE)) { // 판매 취소 요청 건
				result = Const.BOOLEAN_FALSE;
				break;
			}
		}

		resultMap.put("result", result);
		// 파라메터 세팅
		return resultMap;
	}

	/**
	 * @Desc :
	 * @Method Name : getOrderVaildation
	 * @Date : 2019. 3. 3.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderProduct
	 * @return
	 */
	public Map<String, Object> getOrderVaildateTypeTwo(OcOrderProduct ocOrderProduct) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		String result = Const.BOOLEAN_TRUE;
		String orderNo = ocOrderProduct.getOrderNo();

		String[] clmPrdtStatCodes = { CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_CANCEL_WITHDRAWAL_ACCEPT // 취소접수취소 , 취소접수철회
				, CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE// 교환접수취소 , 교환접수철회 , 교환불가
				, CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT // 반품접수취소 , 반품접수철회
		};
		ocOrderProduct.setClmPrdtStatCodes(clmPrdtStatCodes);
		List<OcOrderProduct> productList = ocOrderProductDao.selectOrderProductValidationList(ocOrderProduct);

		OcOrderPayment ocOrderPayment = new OcOrderPayment();
		ocOrderPayment.setOrderNo(orderNo);
		ocOrderPayment.setPymntMeansChngPsbltYn(Const.BOOLEAN_TRUE);
		String[] pymntMeansCodes = { CommonCode.PYMNT_MEANS_CODE_CARD, CommonCode.PYMNT_MEANS_CODE_ACCOUNT_TRANSFER }; // 신용카드
																														// ,
																														// 실시간
																														// 계좌이체
		ocOrderPayment.setPymntMeansCodes(pymntMeansCodes);

		List<OcOrderPayment> paymentList = ocOrderPaymentDao.selectPaymentList(ocOrderPayment);
		long paymentChangeCnt = paymentList.stream()
				.filter(p -> UtilsText.equals(p.getPymntMeansChngPsbltYn(), Const.BOOLEAN_TRUE)).count();

		int productTotCnt = productList.size();

		for (int i = 0, lCnt = productList.size(); i < lCnt; i++) {
			String orderStatCode = productList.get(i).getOrderStatCode();
			String clmPrdtStatCode = productList.get(i).getClmPrdtStatCode() == null ? ""
					: productList.get(i).getClmPrdtStatCode();
			String sellCnclReqYn = productList.get(i).getSellCnclReqYn();

			if (!UtilsText.equals(orderStatCode, CommonCode.ORDER_STAT_CODE_COMPLETE)) { // !주문상태코드가 결제 완료
				result = Const.BOOLEAN_FALSE;
				break;
			}
			if (!UtilsText.equals(clmPrdtStatCode, "")) { // 접수중 or 처리된 클레임 확인
				result = Const.BOOLEAN_FALSE;
				break;
			}
			if (UtilsText.equals(sellCnclReqYn, Const.BOOLEAN_TRUE)) { // 판매 취소 요청 건 클레임과 확인 하여야 함
				result = Const.BOOLEAN_FALSE;
				break;
			}
		}

		if (paymentChangeCnt >= 2) { // 최초 결제건 포함하여 결제수단을 변경 (3번) 하였을 경우 마지막 3번째는 변경 불가
			result = Const.BOOLEAN_FALSE;
		}

		resultMap.put("result", result);
		// 파라메터 세팅
		return resultMap;
	}

	/**
	 * @Desc :
	 * @Method Name : getOrderVaildateTypeThree
	 * @Date : 2019. 3. 29.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderProduct
	 * @return
	 */
	public Map<String, Object> getOrderVaildateTypeThree(OcOrderProduct ocOrderProduct) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		String result = Const.BOOLEAN_TRUE;
		String orderNo = ocOrderProduct.getOrderNo();

		String[] clmPrdtStatCodes = { CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_CANCEL_WITHDRAWAL_ACCEPT // 취소접수취소 , 취소접수철회
				, CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE// 교환접수취소 , 교환접수철회 , 교환불가
				, CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT, CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE // 반품접수취소
																														// ,
																														// 반품접수철회
																														// ,
																														// 반품
																														// 불가
		};
		ocOrderProduct.setClmPrdtStatCodes(clmPrdtStatCodes);
		List<OcOrderProduct> productList = ocOrderProductDao.selectOrderProductValidationList(ocOrderProduct);

		int productTotCnt = productList.size();

		for (int i = 0, lCnt = productList.size(); i < lCnt; i++) {
			String orderPrdtStatCode = productList.get(i).getOrderPrdtStatCode();
			String clmPrdtStatCode = productList.get(i).getClmPrdtStatCode() == null ? ""
					: productList.get(i).getClmPrdtStatCode();
			String sellCnclReqYn = productList.get(i).getSellCnclReqYn();

			if (!UtilsText.equals(orderPrdtStatCode, CommonCode.ORDER_PRDT_STAT_CODE_DELIVERY_ING) && // 배송중
					!UtilsText.equals(orderPrdtStatCode, CommonCode.ORDER_PRDT_STAT_CODE_PICKUP_PREPARATION_FINISH) && // 픽업준비완료
																														// 필요
					!UtilsText.equals(orderPrdtStatCode, CommonCode.ORDER_PRDT_STAT_CODE_DELIVERY_FINISH) // 배송완료
			) {
				result = Const.BOOLEAN_FALSE;
				System.out.println(" 배송관련 :  " + orderPrdtStatCode + " result " + result);
				break;
			}
			if (!UtilsText.equals(clmPrdtStatCode, "")) { // 접수중 or 처리된 클레임 확인
				result = Const.BOOLEAN_FALSE;
				System.out.println(" 클레임  :  " + result);
				break;
			}
			if (UtilsText.equals(sellCnclReqYn, Const.BOOLEAN_TRUE)) { // 판매 취소 요청 건 클레임과 확인 하여야 함
				result = Const.BOOLEAN_FALSE;
				System.out.println(" 취소요청 :  " + result);
				break;
			}
		}

		resultMap.put("result", result);
		// 파라메터 세팅
		return resultMap;
	}

	/**
	 * @Desc : 수령지 주소변경 가능 여부 판단
	 * @Method Name : getOrderAddrUpdateCheck
	 * @Date : 2019. 8. 14.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrder
	 * @return
	 */
	public Map<String, Object> getOrderAddrUpdateCheck(OcOrderProduct ocOrderProduct) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		boolean resultFlag = true;
		String resultCode = "Y";
		String resultMsg = "가능";

		String[] clmPrdtStatCodes = { CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_CANCEL_WITHDRAWAL_ACCEPT // 취소접수취소 , 취소접수철회
				, CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE// 교환접수취소 , 교환접수철회 , 교환불가
				, CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT, CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE // 반품접수취소
																														// ,
																														// 반품접수철회
																														// ,
																														// 반품
																														// 불가
		};
		ocOrderProduct.setClmPrdtStatCodes(clmPrdtStatCodes);

		OcOrderProduct orderStatCount = ocOrderProductDao.selectOrderStatCount(ocOrderProduct);

		int orderTotalCnt = orderStatCount.getOrderTotalCnt();
		int orderReadlyCnt = orderStatCount.getOrderReadlyCnt();
		// int orderConfirmCnt = orderStatCount.getOrderConfirmCnt() ;
		// int orderCancelCnt = orderStatCount.getOrderCancelCnt() ;
		int orderReqCancelCnt = orderStatCount.getOrderReqCancelCnt();
		int orderClaimTotalCnt = orderStatCount.getOrderClaimTotalCnt();

		if (orderClaimTotalCnt > 0) {
			resultMap.put("resultCode", Const.BOOLEAN_FALSE);
			resultMap.put("resultMsg", "클레임 건수 존재");
			resultFlag = false;
		}

		if (resultFlag) {
			if (orderReqCancelCnt > 0) {
				resultMap.put("resultCode", Const.BOOLEAN_FALSE);
				resultMap.put("resultMsg", "취소요청건 존재");
				resultFlag = false;
			}
		}

		if (resultFlag) {
			//
			if (orderTotalCnt == orderReadlyCnt) {
				resultMap.put("resultCode", Const.BOOLEAN_TRUE); //
			} else {
				resultMap.put("resultCode", Const.BOOLEAN_FALSE); //
				resultMap.put("resultMsg", "상품전체 건수 != (결제완료  or 입금대기 )"); //
			}
		}

		return resultMap;
	}

	/**
	 * @Desc : 구매확정 가능상태 확인
	 * @Method Name : getOrderComfirmUpdateCheck
	 * @Date : 2019. 8. 25.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderProduct
	 * @return
	 */
	public Map<String, Object> getOrderComfirmUpdateCheck(OcOrderProduct ocOrderProduct) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		boolean resultFlag = true;
		String resultCode = "Y";
		String resultMsg = "가능";

		String[] clmPrdtStatCodes = { CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT_CANCEL // 취소접수취소
				, CommonCode.CLM_PRDT_STAT_CODE_CANCEL_WITHDRAWAL_ACCEPT // 취소접수철회
				, CommonCode.CLM_PRDT_STAT_CODE_CANCEL_FINISH // 취소완료
				, CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE, CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_FINISH// 교환접수취소
																												// ,
																												// 교환접수철회
																												// ,
																												// 교환불가
																												// ,
																												// 교환완료
				, CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT, CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE,
				CommonCode.CLM_PRDT_STAT_CODE_RETURN_FINISH // 반품접수취소 , 반품접수철회 , 반품 불가 ,반품완료
		};
		ocOrderProduct.setClmPrdtStatCodes(clmPrdtStatCodes);

		OcOrderProduct orderStatCount = ocOrderProductDao.selectOrderStatCount(ocOrderProduct);

		int orderTotalCnt = orderStatCount.getOrderTotalCnt();
		int orderReadlyCnt = orderStatCount.getOrderReadlyCnt();
		int orderConfirmCnt = orderStatCount.getOrderConfirmCnt();
		// int orderCancelCnt = orderStatCount.getOrderCancelCnt() ;
		int orderReqCancelCnt = orderStatCount.getOrderReqCancelCnt();
		int orderClaimTotalCnt = orderStatCount.getOrderClaimTotalCnt();

		if (orderClaimTotalCnt > 0) {
			resultMap.put("resultCode", Const.BOOLEAN_FALSE);
			resultMap.put("resultMsg", "클레임 건수 존재");
			resultFlag = false;
		}

		if (resultFlag) {
			if (orderReqCancelCnt > 0) {
				resultMap.put("resultCode", Const.BOOLEAN_FALSE);
				resultMap.put("resultMsg", "취소요청건 존재");
				resultFlag = false;
			}
		}

		if (resultFlag) {
			//
			if (orderTotalCnt == orderConfirmCnt) {
				resultMap.put("resultCode", Const.BOOLEAN_TRUE); //
			} else {
				resultMap.put("resultCode", Const.BOOLEAN_FALSE); //
				resultMap.put("resultMsg", "상품전체 건수 != (구매확정가능)"); //
			}
		}

		return resultMap;
	}

	/**
	 * @Desc : 배송지 변경 처리
	 * @Method Name : updateRcvrInfoUdpate
	 * @Date : 2019. 3. 3.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrder
	 * @return
	 */
	public Map<String, Object> updateRcvrInfoUdpate(OcOrderReceiverChangeHistory rcvrInfo) throws Exception {
		Map<String, Object> result = new HashMap<>();

		System.out.println("rcvrInfo >>>>>>>>>>" + rcvrInfo);
		boolean resultFlag = true;
		String resultCode = Const.BOOLEAN_TRUE;
		String resultMsg = "배송지 변경 되었습니다.";

		// 세션정보
		UserDetails user = LoginManager.getUserDetails();

		String orderNo = rcvrInfo.getOrderNo();
		String dlvyTypeCode = rcvrInfo.getDlvyTypeCode();

		OcOrderDeliveryHistory ocOrderDeliveryHistory = new OcOrderDeliveryHistory();
		ocOrderDeliveryHistory.setOrderNo(orderNo);
		ocOrderDeliveryHistory.setDlvyDscntcYn(Const.BOOLEAN_FALSE); // 배송중단여부
		ocOrderDeliveryHistory.setMissProcYn(Const.BOOLEAN_FALSE); // 분실처리
		ocOrderDeliveryHistory.setImpsbltProcYn(Const.BOOLEAN_FALSE); // 불가처리여부
		ocOrderDeliveryHistory.setWmsSendYn(Const.BOOLEAN_TRUE); // wms 전송여부
		ocOrderDeliveryHistory.setRcvrName(rcvrInfo.getRcvrName());
		ocOrderDeliveryHistory.setRcvrHdphnNoText(rcvrInfo.getRcvrHdphnNoText());
		ocOrderDeliveryHistory.setRcvrPostCodeText(rcvrInfo.getRcvrPostCodeText());
		ocOrderDeliveryHistory.setRcvrPostAddrText(rcvrInfo.getRcvrPostAddrText());
		ocOrderDeliveryHistory.setRcvrDtlAddrText(rcvrInfo.getRcvrDtlAddrText());
		ocOrderDeliveryHistory.setModerNo(user.getAdminNo());

		List<OcOrderDeliveryHistory> ocOrderDeliveryHistoryList = ocOrderDeliveryHistoryDao
				.getUpdatePsbltDlvyHistData(ocOrderDeliveryHistory);
		String[] hdphnNoArr = this.getTelPhoneSpliter(rcvrInfo.getRcvrHdphnNoText());
		String[] telNoArr = this.getTelPhoneSpliter(rcvrInfo.getRcvrTelNoText());

		if (ocOrderDeliveryHistoryList.isEmpty()) {
			resultFlag = false;
			resultCode = Const.BOOLEAN_FALSE;
			resultMsg = "배송지 변경 데이터가 존재하지 않습니다.";
			result.put("resultCode", resultCode);
			result.put("resultMsg", resultMsg);
			return result;
		}

		// 주소지 변경 interface 호출

		if (resultFlag) {
			try {
				for (OcOrderDeliveryHistory dlvyHistoryData : ocOrderDeliveryHistoryList) {

					if (UtilsText.equals(dlvyHistoryData.getWmsSendYn(), Const.BOOLEAN_TRUE)) {
						log.debug("배송지 변경 wms 전송건  interface 처리 >>> ");
						resultFlag = interfacesOrderService.updateOrderChangeAddrNoTrx(dlvyHistoryData.getCbcd(),
								dlvyHistoryData.getDlvyIdText(), rcvrInfo.getDlvyMemoText(),
								dlvyHistoryData.getInsdMgmtInfoText(), rcvrInfo.getRcvrPostAddrText(),
								rcvrInfo.getRcvrDtlAddrText(), rcvrInfo.getRcvrPostCodeText(), hdphnNoArr[0],
								hdphnNoArr[1], hdphnNoArr[2], rcvrInfo.getRcvrName(), telNoArr[0], telNoArr[1],
								telNoArr[2]);

						if (!resultFlag) {
							resultFlag = false;
							resultCode = Const.BOOLEAN_FALSE;
							resultMsg = "배송지 주소 변경 interface 오류 발생하였습니다.";
							result.put("resultCode", resultCode);
							result.put("resultMsg", resultMsg);
							return result;
						}
					}

				}

			} catch (Exception e) {
				resultFlag = false;
				resultCode = Const.BOOLEAN_FALSE;
				resultMsg = "배송지 주소 변경 interface 오류 발생하였습니다.";
				result.put("resultCode", resultCode);
				result.put("resultMsg", resultMsg);
				return result;
			}
		}

		/*
		 * if (!resultFlag) { result.put("resultCode", resultCode);
		 * result.put("resultMsg", resultMsg); return result; }
		 */

		// 1. 수취인 변경 이력 테이블 저장 ( 이전 편의점 일경우 선등록 처리 )
		// 20190726 일반 택배를 제외한 편의점 픽업, 매장 픽업 배송의 경우 배송지 변경 불가
		if (UtilsText.equals(dlvyTypeCode, CommonCode.DLVY_TYPE_CODE_CONVENIENCE_PICKUP)) { // 편의점 픽업

			OcOrderConvenienceStore cvsInfo = new OcOrderConvenienceStore();
			cvsInfo.setOrderNo(rcvrInfo.getOrderNo());
			cvsInfo.setCvnstrCode(rcvrInfo.getCvnstrCode());
			cvsInfo.setCvnstrNoText(rcvrInfo.getCvnstrNoText());
			cvsInfo.setCvnstrName(rcvrInfo.getCvnstrName());
			cvsInfo.setTelNoText(rcvrInfo.getTelNoText());
			cvsInfo.setPostCodeText(rcvrInfo.getPostCodeText());
			cvsInfo.setPostAddrText(rcvrInfo.getPostAddrText());
			cvsInfo.setDtlAddrText(rcvrInfo.getDtlAddrText());
			cvsInfo.setArvlStoreCodeText(rcvrInfo.getArvlStoreCodeText());
			cvsInfo.setArvlStoreBrcdText(rcvrInfo.getArvlStoreBrcdText());
			cvsInfo.setDongNameCodeText(rcvrInfo.getDongNameCodeText());
			cvsInfo.setArvlDongName(rcvrInfo.getArvlDongName());
			cvsInfo.setDlvyPlaceYn(rcvrInfo.getDlvyPlaceYn());

			ocOrderConvenienceStoreDao.insertConvenienceStore(cvsInfo);

			// 편의점 주소 정보 setting
			rcvrInfo.setCvnstrSeq(cvsInfo.getCvnstrSeq());
			rcvrInfo.setRcvrPostCodeText(cvsInfo.getPostCodeText()); // 편의점 우편번호
			rcvrInfo.setRcvrPostAddrText(cvsInfo.getPostAddrText()); // 편의점 우편주소
			rcvrInfo.setRcvrDtlAddrText(cvsInfo.getDtlAddrText()); // 편의점 상세주소

		} else if (UtilsText.equals(dlvyTypeCode, CommonCode.DLVY_TYPE_CODE_NORMAL_LOGISTICS)) { // 일반 택배

			rcvrInfo.setRcvrPostCodeText(rcvrInfo.getRcvrPostCodeText());
			rcvrInfo.setRcvrPostAddrText(rcvrInfo.getRcvrPostAddrText());
			rcvrInfo.setRcvrDtlAddrText(rcvrInfo.getRcvrDtlAddrText());

		} else if (UtilsText.equals(dlvyTypeCode, CommonCode.DLVY_TYPE_CODE_STORE_PICKUP)) { // 매장 픽업

			CmStore cmStore = new CmStore();
			// 주문 마스터의 상점번호 기준으로 조회함
			cmStore.setStoreNo(rcvrInfo.getStoreNo());
			CmStore storeInfo = storeService.selectCmStoreInfo(cmStore);

			rcvrInfo.setStoreNo(rcvrInfo.getStoreNo());
			rcvrInfo.setStoreName(rcvrInfo.getStoreName());
			// rcvrInfo.setStoreAddInfo(rcvrInfo.getStoreAddInfo());
			rcvrInfo.setRcvrPostCodeText(storeInfo.getPostCodeText());
			rcvrInfo.setRcvrPostAddrText(storeInfo.getPostAddrText());
			rcvrInfo.setRcvrDtlAddrText(storeInfo.getDtlAddrText());
		}

		rcvrInfo.setRgsterNo(user.getAdminNo());
		// 주문 수취인 변경 이력 저장
		if (resultFlag) {
			int inChangCnt = ocOrderReceiverChangeHistoryDao.insertReceiverChangeHistory(rcvrInfo);

			if (inChangCnt <= 0) {
				resultFlag = false;
				resultCode = Const.BOOLEAN_FALSE;
				resultMsg = "주문 수취인 변경 이력 저장 실패.";
				result.put("resultCode", resultCode);
				result.put("resultMsg", resultMsg);
				return result;
			}
		}

		// 2. 주문 배송 이력 테이블 update

		if (resultFlag) {

			int updHisCnt = ocOrderDeliveryHistoryDao.updateOcOrderDeliveryHistoryModify(ocOrderDeliveryHistory);

			if (updHisCnt <= 0) {
				resultFlag = false;
				resultCode = Const.BOOLEAN_FALSE;
				resultMsg = "주문 배송 이력 테이블 수정 실패.";
				result.put("resultCode", resultCode);
				result.put("resultMsg", resultMsg);
				return result;
			}
		}

		// 3.주문정보 update
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(rcvrInfo.getOrderNo());
		ocOrder.setRcvrName(rcvrInfo.getRcvrName());
		ocOrder.setRcvrTelNoText(rcvrInfo.getRcvrTelNoText());
		ocOrder.setRcvrHdphnNoText(rcvrInfo.getRcvrHdphnNoText());

		ocOrder.setRcvrPostCodeText(rcvrInfo.getRcvrPostCodeText());
		ocOrder.setRcvrPostAddrText(rcvrInfo.getRcvrPostAddrText());
		ocOrder.setRcvrDtlAddrText(rcvrInfo.getRcvrDtlAddrText());

		ocOrder.setDlvyMemoText(rcvrInfo.getDlvyMemoText()); // 배송 메모

		System.out.println("rcvrInfo.getDlvyMemoText() >>>>>>>>" + rcvrInfo.getDlvyMemoText());

		ocOrder.setModerNo(user.getAdminNo());

		if (resultFlag) {
			int updCnt = ocOrderDao.updateRcvrInfoUdpate(ocOrder); // 주문 마스터 수취인 정보 업데이트

			if (updCnt <= 0) {
				resultFlag = false;
				resultCode = Const.BOOLEAN_FALSE;
				resultMsg = "주문 마스터 수취인 정보 업데이트 실패.";
				result.put("resultCode", resultCode);
				result.put("resultMsg", resultMsg);
				return result;
			}
		}

		if (resultFlag) {

			IfOrder ifOrder = new IfOrder();
			// NAMERECEIVER MOBILERECEIVER PHONERECEIVER ZIPCODE ADDRESS ADDRESSDETAIL

			String ifMobilNo = hdphnNoArr[0] + "-" + hdphnNoArr[1] + "-" + hdphnNoArr[2];
			String ifTelNo = telNoArr[0] + "-" + telNoArr[1] + "-" + telNoArr[2];

			ifOrder.setOldordno(rcvrInfo.getOrderNo());
			ifOrder.setNamereceiver(rcvrInfo.getRcvrName());
			ifOrder.setMobilereceiver(ifMobilNo);
			ifOrder.setPhonereceiver(ifTelNo);
			ifOrder.setZipcode(rcvrInfo.getRcvrPostCodeText());
			ifOrder.setAddress(rcvrInfo.getRcvrPostAddrText());
			ifOrder.setAddressdetail(rcvrInfo.getRcvrDtlAddrText());

			int updCnt = ifOrderDao.updateRcvrInfoUdpate(ifOrder); // if order 수취인 정보 업데이트

			if (updCnt > 0) {
				log.debug("if order 테이블 정보 upate ");
			} else {
				log.debug("if order 테이블 정보 없음 data 생성전  ");
			}

		}

		// 4. 배송지 변경이 성공했으면 알림톡 및 SMS 문자 보내기 호출
		if (resultFlag) {

			try {
				// 배송지 변경이 성공했으면 알림톡 및 SMS 문자 보내기 호출
				orderMessageService.orderDlvrChange(ocOrder);
			} catch (Exception e) {

			}
		}

		System.out.println("resultCode" + resultCode);
		System.out.println("resultMsg" + resultMsg);
		result.put("resultCode", resultCode);
		result.put("resultMsg", resultMsg);

		// 파라메터 세팅
		return result;
	}

	/**
	 *
	 * @Desc : 휴대폰 문자열 split
	 * @Method Name : getTelPhoneSpliter
	 * @Date : 2019. 8. 19.
	 * @Author : flychani@3top.co.kr
	 * @param noStr
	 * @return
	 * @throws Exception
	 */
	public String[] getTelPhoneSpliter(String noStr) throws Exception {
		Pattern tellPattern = Pattern.compile("^(01\\d{1}|02|0505|0502|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})");

		if (noStr == null)
			return new String[] { "", "", "" };

		Matcher matcher = tellPattern.matcher(noStr);
		if (matcher.matches()) {
			return new String[] { matcher.group(1), matcher.group(2), matcher.group(3) };
		} else {
			String str1 = noStr.substring(0, 3);
			String str2 = noStr.substring(3, 7);
			String str3 = noStr.substring(7, 11);
			return new String[] { str1, str2, str3 };
		}
	}

	/**
	 * @Desc : 결제수단 변경 가능 여부 체크
	 * @Method Name : getOrderPaymentUpdateCheck
	 * @Date : 2019. 8. 16.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderProduct
	 * @return
	 */
	public Map<String, Object> getOrderPaymentUpdateCheck(OcOrderProduct ocOrderProduct) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		String resultCode = Const.BOOLEAN_TRUE;
		String resultMsg = "가능";

		String orderNo = ocOrderProduct.getOrderNo();

		String[] clmPrdtStatCodes = { CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_CANCEL_WITHDRAWAL_ACCEPT // 취소접수취소 , 취소접수철회
				, CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE// 교환접수취소 , 교환접수철회 , 교환불가
				, CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT, CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE // 반품접수취소
																														// ,
																														// 반품접수철회
																														// ,
																														// 반품
																														// 불가
		};
		ocOrderProduct.setClmPrdtStatCodes(clmPrdtStatCodes);

		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(orderNo);
		// 1. 주문 마스터 정보 조회
		OcOrder orderDetail = ocOrderDao.selectOrderDetail(ocOrder);

		if (!UtilsText.equals(orderDetail.getOrderStatCode(), CommonCode.ORDER_STAT_CODE_COMPLETE)) {
			resultCode = Const.BOOLEAN_FALSE;
			resultMsg = "결제완료상태가 아닙니다.";
		}

		// 1. kcp 신용카드 , 실시간 계좌이체 , 복함 결제 불가
		OcOrderPayment ocOrderPayment = new OcOrderPayment();
		ocOrderPayment.setOrderNo(orderNo);
		List<OcOrderPayment> paymentList = ocOrderPaymentDao.selectPaymentList(ocOrderPayment);

		// 결제수단 변경건 건수 조회
		long pymntChangeCnt = paymentList.stream()
				.filter(p -> UtilsText.equals(p.getPymntMeansChngPsbltYn(), Const.BOOLEAN_TRUE)).count();

		if (pymntChangeCnt >= 3) {
			resultCode = Const.BOOLEAN_FALSE;
			resultMsg = "결제 수단 변경 3회까지만 변경 가능합니다.";
		}

		for (OcOrderPayment pymnt : paymentList) {
			if (pymnt.getPymntMeansCnt() > 0) {
				resultCode = Const.BOOLEAN_FALSE;
				resultMsg = "결제수단 복합 결제건은 결제수단변경이 불가능합니다.";
			}
		}

		OcOrderProduct orderStatCount = ocOrderProductDao.selectOrderStatCount(ocOrderProduct);

		// 2. 구매 확정 이전 상태 ( 상품 기준 )
		if (orderStatCount.getVndrPrdtCnt() > 0) {
			resultCode = Const.BOOLEAN_FALSE;
			resultMsg = "구매확정 이전 상태에서 결제수단 변경 가능합니다.";
		}

		// 3. 원주문 기준 클레임 건수 조회 ( 클레임 상태에 따른 제외 항목 재확인 필요 )
		if (orderStatCount.getOrderClaimTotalCnt() > 0) {
			resultCode = Const.BOOLEAN_FALSE;
			resultMsg = "클레임이 존재하는 상품이 있습니다.";
		}

		resultMap.put("resultCode", resultCode); //
		resultMap.put("resultMsg", resultMsg); //
		return resultMap;
	}

	/**
	 * @Desc : 결제수단변경 처리
	 * @Method Name : updateOrderPaymentChange
	 * @Date : 2019. 3. 3.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderPayment
	 * @return
	 */
	public Map<String, Object> updateOrderPaymentChange(OcOrderPayment ocOrderPayment) throws Exception {
		Map<String, Object> result = new HashMap<>();
		// 세션정보
		UserDetails user = LoginManager.getUserDetails();

		ocOrderPayment.setPymntMeansChngPsbltYn(Const.BOOLEAN_TRUE);
		ocOrderPayment.setModerNo(user.getAdminNo());

		int updCnt = ocOrderPaymentDao.updateOrderPaymentChange(ocOrderPayment);

		if (updCnt > 0) {
			result.put("result", Const.BOOLEAN_TRUE);
		} else {
			result.put("result", Const.BOOLEAN_FALSE);
		}

		// 파라메터 세팅
		return result;
	}

	/**
	 * @Desc : 미수령기간갱신
	 * @Method Name : updateNonPickupDlvyDateModify
	 * @Date : 2019. 3. 30.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderProduct
	 * @return
	 */
	public Map<String, Object> updateNonPickupDlvyDateModify(OcOrderProduct[] ocOrderProduct) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		// 세션정보
		UserDetails user = LoginManager.getUserDetails();

		String resultCode = Const.BOOLEAN_TRUE;
		String resultMsg = "";

		String[] clmPrdtStatCodes = { CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_CANCEL_WITHDRAWAL_ACCEPT // 취소접수취소 , 취소접수철회
				, CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE// 교환접수취소 , 교환접수철회 , 교환불가
				, CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT // 반품접수취소 , 반품접수철회
				, CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE };

		String orderNo = ocOrderProduct[0].getOrderNo();

		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(orderNo);
		// 1. 주문 마스터 정보 조회
		OcOrder orderDtail = ocOrderDao.selectOrderDetail(ocOrder);

		CmStore cmStore = new CmStore();
		// 주문 마스터의 상점번호 기준으로 조회함
		cmStore.setStoreNo(orderDtail.getStoreNo());
		CmStore storeInfo = storeService.selectCmStoreInfo(cmStore);

		// validate
		for (OcOrderProduct prdt : ocOrderProduct) {

			short orderDlvyHistSeq = prdt.getOrderDlvyHistSeq();
			prdt.setClmPrdtStatCodes(clmPrdtStatCodes);
			prdt.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 유형 코드 10003

			OcOrderProduct prdtInfo = ocOrderProductDao.selectProductDetail(prdt);
			// 상품배송상태 확인 미정 , 택배 전환여부 확인 필요

			if (UtilsText.equals(prdtInfo.getLogisCnvrtYn(), Const.BOOLEAN_TRUE)) {
				resultCode = Const.BOOLEAN_FALSE;
				resultMsg = "택배전환이 된 상품이 있습니다.";
				break;
			}

			if (prdtInfo.getClmNo() != null && !UtilsText.equals(prdtInfo.getClmNo(), "")) {
				resultCode = Const.BOOLEAN_FALSE;
				resultMsg = "클레임이 처리된 상품이 있습니다.";
				break;
			}

			if (prdtInfo.getOrderDlvyHistSeq() != orderDlvyHistSeq) {
				resultCode = Const.BOOLEAN_FALSE;
				resultMsg = "배송이력이 추가된 상품이 있습니다.";
				break;
			}

			// 픽업 준비 완료 기준으로 픽업 가능일자 update 됨
			if (UtilsText.equals(prdtInfo.getPickupPsbltYmdOverYn(), Const.BOOLEAN_TRUE)) {
				resultCode = Const.BOOLEAN_FALSE;
				resultMsg = "수령날짜가 지난 상품이 있습니다.";
				break;
			}

			// 배송이력 순번이 같은지 확인 날짜만 체크 현재 날짜 보다 크거나 같은지
		}
		
		if (UtilsText.equals(resultCode, Const.BOOLEAN_TRUE)) {

			for (OcOrderProduct prdt : ocOrderProduct) {

				prdt.setClmPrdtStatCodes(clmPrdtStatCodes);
				prdt.setPrdtTypeCodeGift(CommonCode.PRDT_TYPE_CODE_GIFT); // 사은품 유형 코드 10003

				OcOrderProduct prdtInfo = ocOrderProductDao.selectProductDetail(prdt);
				log.error("#########prdtInfo######=" + prdtInfo.toString());
				
				String dlvyId = prdtInfo.getDlvyIdText();
				String addPsbltDt = prdtInfo.getPickupPsbltYmdAddSeven();

				/**
				 * as-is 기준 픽업 가능일 연장시 프로시져 호출 PR_ORDER_CHANGE_FOR_PICKUP_PSBLT_DT -- 픽업 기간연장
				 */

				if (UtilsText.equals(prdtInfo.getWmsSendYn(), Const.BOOLEAN_TRUE)) {

					// String rtnAddDate =
					// interfacesDeliveryService.updateExtensionPickupPsbltNoTrx(dlvyId);
					Map<String, String> paramMap = new HashMap<>();
					paramMap.put("dlvyIdText", dlvyId);
					
					// updateExtensionPickupPsbltNoTrx>> PR_ORDER_CHANGE_FOR_PICKUP_PSBLT_DT 로 수정
					Map<String, String> rsMap = orderService.callProcedureForPickupPsbltDt(paramMap);
					String prErrorCode = rsMap.get("errorCode");
					String rtnAddDate = rsMap.get("addPsbltDt");
					log.error("####aftercallProcedureForPickupPsbltDt#####=" + rsMap.toString());
					log.error("####aftercallProcedureForPickupPsbltDt.prErrorCode#####=" + rsMap.get("errorCode"));
					log.error("####aftercallProcedureForPickupPsbltDt.rtnAddDate#####=" + rsMap.get("addPsbltDt"));
					
					if (UtilsText.equals(prErrorCode, "0") && rtnAddDate.indexOf("-") > 0) {
						rtnAddDate = rtnAddDate.replace("-", "");
					}
					
					log.error("####afterReplace#####=" + rsMap.toString());
					log.error("####afterReplace.rtnAddDate#####=" + rsMap.get("addPsbltDt"));
					if (UtilsText.equals(prErrorCode, "0") && rtnAddDate != null && rtnAddDate != "") {

						// 상품 테이블 픽업연장여부 LOGIS_CNVRT_YN = y
						prdt.setPickupExtsnYn(Const.BOOLEAN_TRUE);
						prdt.setModerNo(user.getAdminNo());
						int prdUpCnt = ocOrderProductDao.updateProdutLogisCnvrt(prdt); // 픽업연장여부 변경

						// 배송테이블 픽업가능일 PICKUP_PSBLT_YMD + 7일 처리
						OcOrderDeliveryHistory ocOrderDeliveryHistory = new OcOrderDeliveryHistory();
						ocOrderDeliveryHistory.setOrderNo(prdtInfo.getOrderNo());
						ocOrderDeliveryHistory.setOrderPrdtSeq(prdtInfo.getOrderPrdtSeq());
						ocOrderDeliveryHistory.setOrderDlvyHistSeq(prdtInfo.getOrderDlvyHistSeq());

						// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
						// Date rtnAddDateDlvy = dateFormat.parse(rtnAddDate);

						LocalDate date = LocalDate.parse(rtnAddDate, DateTimeFormatter.BASIC_ISO_DATE);
						Timestamp rtnAddDateDlvy = Timestamp.valueOf(date.atStartOfDay());

						System.out.println("rtnAddDateDlvy >> " + rtnAddDateDlvy);

						ocOrderDeliveryHistory.setPickupPsbltYmd(rtnAddDateDlvy); // 픽업 연장 가능기간
						ocOrderDeliveryHistory.setModerNo(user.getAdminNo());

						int dlvyUpCnt = ocOrderDeliveryHistoryDao.updateDlvyPickupPsbltDate(ocOrderDeliveryHistory);

						// 상품 변경 이력 등록
						OcOrderProductHistory prdtHistory = new OcOrderProductHistory();
						prdtHistory.setOrderNo(prdtInfo.getOrderNo());
						prdtHistory.setOrderPrdtSeq(prdtInfo.getOrderPrdtSeq());
						prdtHistory.setPrdtNo(prdtInfo.getPrdtNo());
						prdtHistory.setPrdtOptnNo(prdtInfo.getPrdtOptnNo());
						prdtHistory.setPrdtName(prdtInfo.getPrdtName());
						prdtHistory.setEngPrdtName(prdtInfo.getEngPrdtName());
						prdtHistory.setOptnName(prdtInfo.getOptnName());
						prdtHistory.setOrderPrdtStatCode(prdtInfo.getOrderPrdtStatCode());
						prdtHistory.setNoteText("픽업 가능일 연장 처리"); // NOTE_TEXT
						prdtHistory.setRgsterNo(user.getAdminNo());

						int prdtHisCnt = ocOrderProductHistoryDao.insertProductHistory(prdtHistory);

						Map<String, String> map = new HashMap<>();
						map.put("orderNo", orderDtail.getOrderNo()); // 주문번호
						map.put("buyerName", orderDtail.getBuyerName()); // 주문자명
						map.put("prdtName", prdt.getPrdtName()); // 상품명
						map.put("crtfcNoText", orderDtail.getCrtfcNoText()); // 픽업 코드 , 인증번호
						map.put("storeName", storeInfo.getStoreName()); // 매장명
						map.put("storeAddr", storeInfo.getFullAddr()); // 매장 주소
						map.put("telNo", storeInfo.getTelNoFormat()); // 매장 전화번호

						SimpleDateFormat formatDate = new SimpleDateFormat("yyyy년 MM월 dd일");
						String rtnAddDateFormat = formatDate.format(rtnAddDateDlvy);
						map.put("pickupPsbltYmd", rtnAddDateFormat);

//					map.put("pickupPsbltYmd", new SimpleDateFormat("yyyy년 MM월 dd일").format(rtnAddDate));
						MessageVO messageVO = new MessageVO();
						
						// 2020.06.01 : 알림톡으로 발송안되던 이유 
						messageVO.setSiteNo(orderDtail.getSiteNo()); 
						
						if (UtilsText.equals(orderDtail.getSiteNo(), Const.SITE_NO_ART)) {
							messageVO.setMesgId(MessageCode.PICKUP_PSBLT_YMD_ART);
							map.put("landingUrl", Const.SERVICE_DOMAIN_ART_MO + "/mypage"); // 통합몰
						} else if (UtilsText.equals(orderDtail.getSiteNo(), Const.SITE_NO_OTS)) {
							messageVO.setMesgId("OTSA139"); // 2020.06.01 : OTS 알림톡 재승인으로 인한 수정 
							map.put("landingUrl", Const.SERVICE_DOMAIN_OTS_MO + "/mypage"); // ost
						}

						messageVO.setSndrName(Const.SYS_SENDER_MESSAGE_NAME); // 발신자
						messageVO.setSendTelNoText(Const.SYS_SENDER_MESSAGE_NUMBER); // 발신 번호
						messageVO.setRcvrName(orderDtail.getBuyerName()); // 수신사
						messageVO.setRecvTelNoText(orderDtail.getBuyerHdphnNoText()); // 수신번호
						messageVO.setMessageTemplateModel(map);

						messageVO.setReal(true); // 즉시발송

						try {
							// 알림톡 발송 처리
							messageService.setSendMessageProcessNoTrx(messageVO);
						}catch (Exception e) {
							log.error("주문번호["+orderDtail.getOrderNo()+"] 픽업기간연장 메세지발송 실패");
						}

					} else {
						resultCode = Const.BOOLEAN_FALSE;
						resultMsg = "미수령 갱신이 불가합니다.";
					}
				} else {
					resultCode = Const.BOOLEAN_FALSE;
					resultMsg = "미수령 갱신이 불가합니다. (wms 전송 처리 안됨)";
				}

			}
		}

		Map<String, Object> itemResult = new HashMap<String, Object>();
		itemResult.put("Code", resultCode);
		itemResult.put("Message", resultMsg);

		resultMap.put("Result", itemResult);

		return resultMap;
	}

	/**
	 * @Desc : 주문 엑셀 전체 다운로드
	 * @Method Name : getonOrderForExcelList
	 * @Date : 2019. 3. 5.
	 * @Author : flychani@3top.co.kr
	 * @param params
	 * @return
	 */
	public List<OcOrderExcelVo> getonOrderForExcelList(OcOrder params) throws Exception {

		List<OcOrderExcelVo> list = ocOrderDao.getonOrderForExcelList(params);
		return list;
	}

	/**
	 * @Desc : 선택 주문 엑셀 다운로드
	 * @Method Name : getonOrdeSelectListExcelDn
	 * @Date : 2019. 3. 6.
	 * @Author : flychani@3top.co.kr
	 * @param params
	 * @return
	 */
	public List<OcOrderExcelVo> getonOrdeSelectListExcelDn(OcOrder params) throws Exception {

		List<OcOrderExcelVo> list = ocOrderDao.getonOrdeSelectListExcelDn(params);
		return list;
	}

	/**
	 * @Desc :
	 * @Method Name : readOnlineOrderList
	 * @Date : 2019. 3. 8.
	 * @Author : flychani@3top.co.kr
	 * @param orderVOPageble
	 * @return
	 */
	public Page<OcOrder> readOnlineOrderList(Pageable<OcOrder, OcOrder> pageable) throws Exception {
		int count = ocOrderDao.readOnlineOrderListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			List<OcOrder> orderList = ocOrderDao.readOnlineOrderList(pageable);

			// 주문 번호 배열 처리
			String[] orderNos = orderList.stream().map(OcOrder::getOrderNo).collect(Collectors.<String>toList())
					.toArray(new String[orderList.size()]);

			// 주문상품 조회
			Map<String, List<OcOrderProduct>> productList = selectProductOrderGrouping(orderNos);

			// 결제수단 조회
			// Map<String, List<OcOrderPayment>> paymentList = selectPaymentArr(orderNos);
			Map<String, List<OcOrderPayment>> paymentList = selectPaymentOrderGrouping(orderNos);

			// 클레임
			Map<String, List<OcClaimProduct>> ClaimList = selectClaimArr(orderNos);

			// as OC_AS_ACCEPT_PRODUCT
			Map<String, List<OcAsAcceptProduct>> asList = selectOrderAsArr(orderNos);

			for (OcOrder order : orderList) {
				// 결제완료된 금액만 노출 ( 클레임 ( 부분 취소 클레임 처리된 내역은 주문상세 클레임 화면에서 )
				order.setOrderCnt(orderList.stream()
						.filter(o -> UtilsText.equals(o.getOrderStatCode(), CommonCode.ORDER_STAT_CODE_COMPLETE))
						.count());
				order.setOrderAmt(orderList.stream()
						.filter(o -> UtilsText.equals(o.getOrderStatCode(), CommonCode.ORDER_STAT_CODE_COMPLETE))
						.mapToLong(OcOrder::getPymntAmt).sum());
				paymentList.forEach((k, v) -> {
					if (order.getOrderNo().equals(k)) {
						// 결제 완료일
						order.setPymntDtm(v.stream().filter(a -> a.getMainPymntMeansYn().equals("Y"))
								.map(OcOrderPayment::getPymntDtm).findFirst().orElse(null));
					}
				});
			}

			for (OcOrder order : orderList) {
				ClaimList.forEach((k, v) -> {
					if (order.getOrderNo().equals(k)) {
						order.setClaimCancelCnt(v.stream()
								.filter(c -> UtilsText.equals(c.getClmGbnCode(), CommonCode.CLM_GBN_CODE_CANCEL))
								.count()); // 취소
						order.setClaimExchangeCnt(v.stream()
								.filter(c -> UtilsText.equals(c.getClmGbnCode(), CommonCode.CLM_GBN_CODE_EXCHANGE))
								.count()); // 교환
						order.setClaimReturnCnt(v.stream()
								.filter(c -> UtilsText.equals(c.getClmGbnCode(), CommonCode.CLM_GBN_CODE_RETURN))
								.count()); // 반품
					}
				});
			}

			for (OcOrder order : orderList) {
				asList.forEach((k, v) -> {
					if (order.getOrderNo().equals(k)) {
						order.setClaimASCnt(v.stream().count()); // as 수량
					}
				});
			}

			for (OcOrder order : orderList) {
				productList.forEach((k, v) -> {
					if (order.getOrderNo().equals(k)) {
						Long prdtCancelCnt = v.stream().filter(p -> UtilsText.equals(p.getOrderPrdtStatCode(),
								CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE)).count();
						if (UtilsText.equals(order.getOrderStatCode(), CommonCode.ORDER_STAT_CODE_COMPLETE)) { // 결제 완료
							if (prdtCancelCnt > 0) {
								// 부분 취소 상품
								order.setOrderCancelFlag("part");
							}
						} else if (UtilsText.equals(order.getOrderStatCode(),
								CommonCode.ORDER_STAT_CODE_CANCEL_COMPLETE)) {
							// 전체 취소
							order.setOrderCancelFlag("total");
						} else {
							order.setOrderCancelFlag("");
						}
					}
				});
			}

			pageable.setContent(orderList);
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 오프라인 주문
	 * @Method Name : readOfflineOrderList
	 * @Date : 2019. 3. 9.
	 * @Author : flychani@3top.co.kr
	 * @param orderVOPageble
	 * @return
	 */
	public Page<IfOffDealHistory> readOfflineOrderList(Pageable<IfOffDealHistory, IfOffDealHistory> pageable)
			throws Exception {
		int count = ifOffDealHistoryDao.readOfflineOrderListCount(pageable);
		pageable.setTotalCount(count);
		OcOrder param = new OcOrder();
		if (count > 0) {
			List<IfOffDealHistory> list = ifOffDealHistoryDao.readOfflineOrderList(pageable);

			for (IfOffDealHistory offOrder : list) {
				offOrder.setOrderCnt(count);
				BigDecimal total = list.stream().map(IfOffDealHistory::getSalePrice).reduce(BigDecimal.ZERO,
						BigDecimal::add);
				offOrder.setOrderAmt(total);

			}

			pageable.setContent(list);

		}

		return pageable.getPage();
	}

	/**
	 * @Desc :
	 * @Method Name : selectClaimArr
	 * @Date : 2019. 3. 8.
	 * @Author : flychani@3top.co.kr
	 * @param orderNos
	 * @return
	 */
	private Map<String, List<OcClaimProduct>> selectClaimArr(String[] orderNos) throws Exception {
		OcClaimProduct ocClaimProduct = new OcClaimProduct();
		ocClaimProduct.setOrderNos(orderNos);

		List<OcClaimProduct> list = claimService.selectClaimProductListForOrder(ocClaimProduct);

		if (list != null) {
			list.stream().collect(Collectors.groupingBy(OcClaimProduct::getOrderNo));
		} else {
			return null;
		}
		return list.stream().collect(Collectors.groupingBy(OcClaimProduct::getOrderNo));
	}

	/**
	 * @Desc :
	 * @Method Name : selectClaimArr
	 * @Date : 2019. 3. 8.
	 * @Author : flychani@3top.co.kr
	 * @param orderNos
	 * @return
	 */
	private Map<String, List<OcAsAcceptProduct>> selectOrderAsArr(String[] orderNos) throws Exception {
		List<OcAsAcceptProduct> list = afterserviceservice.selectOrderAsArr(orderNos);

		if (list != null) {
			list.stream().collect(Collectors.groupingBy(OcAsAcceptProduct::getOrderNo));
		} else {
			return null;
		}

		return list.stream().collect(Collectors.groupingBy(OcAsAcceptProduct::getOrderNo));
	}

	/**
	 * @Desc :
	 * @Method Name : selectClaimArr
	 * @Date : 2019. 3. 8.
	 * @Author : flychani@3top.co.kr
	 * @param orderNos
	 * @return
	 */
	private Map<String, List<BdMemberCounsel>> selectOrderiquiryArr(String[] orderNos) throws Exception {
		List<BdMemberCounsel> list = bdInquiryService.selectOrderiquiryArr(orderNos);

		list.stream().collect(Collectors.groupingBy(BdMemberCounsel::getOrderNo));

		if (list == null) {
			return null;
		}
		return list.stream().collect(Collectors.groupingBy(BdMemberCounsel::getOrderNo));
	}

	/**
	 *
	 * @param ocOrder
	 * @Desc :
	 * @Method Name : selectOrderMstList
	 * @Date : 2019. 3. 8.
	 * @Author : flychani@3top.co.kr
	 * @return
	 * @throws Exception
	 */
	public List<OcOrder> selectOrderMstList(OcOrder ocOrder) throws Exception {
		return ocOrderDao.selectOrderMstList(ocOrder);
	}

	/**
	 *
	 * @Desc :
	 * @Method Name : selectOrderProductList
	 * @Date : 2019. 3. 8.
	 * @Author : flychani@3top.co.kr
	 * @return
	 * @throws Exception
	 */
	public List<OcOrderProduct> selectOrderProductList(String orderNo) throws Exception {
		OcOrderProduct ocOrderProduct = new OcOrderProduct();
		ocOrderProduct.setOrderNo(orderNo);
		return ocOrderProductDao.selectOrderProductList(ocOrderProduct);
	}

	/**
	 * @Desc :
	 * @Method Name : getKakaoPayHistoryList
	 * @Date : 2019. 4. 2.
	 * @Author : flychani@3top.co.kr
	 * @param kakaoPaymentOrder
	 * @return
	 */
	public List<KakaoPayDetailList> getKakaoPayHistoryList(KakaoPaymentOrder kakaoPaymentOrder) throws Exception {

		List<KakaoPayDetailList> returnList;

		String tid = kakaoPaymentOrder.getTid();

		PaymentResult result = kakaoPaymentService.orderDetail(new PaymentInfo("KAKAO", kakaoPaymentOrder));

		KakaoPaymentOrderReturn reponse = (KakaoPaymentOrderReturn) result.getData();

		returnList = reponse.getPaymentActionDetails();

		KakaoCardInfo cardInfo = reponse.getSelectedCardInfo();

//		for (KakaoPayDetailList payList : returnList) {
//			payList.setCardBinView(cardInfo.getCardBin());
//			payList.setInstallMonthView(cardInfo.getInstallMonth());
//			payList.setInterestFreeInstallView(cardInfo.getInterestFreeInstall());
//			payList.setPaymentMethodTypeView(reponse.getPaymentMethodType());
//			payList.setCardCorpNameView(cardInfo.getCardCorpName());
//			;
//		}

		return returnList;
	}

	/**
	 * @Desc :
	 * @Method Name : getOrderPrdtDetailInfo
	 * @Date : 2019. 7. 16.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 */
	public void getOrderPrdtDetailInfo(Parameter<OcOrderProduct> parameter) throws Exception {

		OcOrderProduct ocOrderProduct = parameter.get();

		String layerType = ocOrderProduct.getLayerType();
		String dlvyTypeCode = "";
		String promoNo = "";

		// 주문 상품 기본 정보
		OcOrderProduct prdtInfo = ocOrderProductDao.selectOnlyProductDetail(ocOrderProduct);
		dlvyTypeCode = prdtInfo.getDlvyTypeCode();

		if (UtilsText.equals(layerType, "Order")) {
			// 주문일경우 프로모션 정보 조회
			OcOrderProductApplyPromotion applyPromotion = new OcOrderProductApplyPromotion();
			applyPromotion.setOrderNo(ocOrderProduct.getOrderNo());
			applyPromotion.setOrderPrdtSeq(ocOrderProduct.getOrderPrdtSeq());

			String[] promoTypeCodes = { CommonCode.PROMO_TYPE_CODE_ONE_PLUS_ONE // 1+1증정
					, CommonCode.PROMO_TYPE_CODE_DISCOUNT_MULTI_SHOUES // 다족구매할인
					, CommonCode.PROMO_TYPE_CODE_DISCOUNT_IMMEDIATELY // 즉시할인
					, CommonCode.PROMO_TYPE_CODE_DISCOUNT_IMMEDIATELY_AFFILIATES // 제휴사 즉시할인
					, CommonCode.PROMO_TYPE_CODE_SPECIAL_PRICE_TIME // 타임특가
			};

			applyPromotion.setPromoTypeCodes(promoTypeCodes);

			List<OcOrderProductApplyPromotion> applyPromotionData = new ArrayList<>();
			applyPromotionData = ocOrderProductApplyPromotionDao.selectOrderProductApplyPromotion(applyPromotion);

			if (applyPromotionData != null && applyPromotionData.size() > 0) {
				// System.out.println("applyPromotionData promoNo >> " +
				// applyPromotionData.get(0).getPromoNo());
				promoNo = applyPromotionData.get(0).getPromoNo(); // 사은품을 제외한 프로모션 정보
			}
		}

		String prdtNo = prdtInfo.getPrdtNo(); // 상품번호
		String prdtOptnNo = prdtInfo.getPrdtOptnNo(); // 상품옵션번호
		String mmnyPrdtYn = prdtInfo.getMmnyPrdtYn(); // 자사상품여부

		PdProductOption ppo = new PdProductOption();
		ppo.setPrdtNo(prdtNo);
		ppo.setPrdtOptnNo(prdtOptnNo);

		PdProductOption optnInfo = pdProductOptionDao.selectByPrimaryKey(ppo);

		// 상품번호 기준 옵션 정보
		// getProductOptionListWithStockAndPrice 변경 , 매장 픽업 주문 으로 수량 변경 가능 여부 확인

		List<PdProductOptionWithStockAndPrice> prdtOptList = productInsideOptionService
				.getProductOptionListWithStockAndPrice(prdtNo, promoNo);

		log.debug(" promoNo " + promoNo);
		log.debug(" mmnyPrdtYn " + mmnyPrdtYn);

		log.debug(" dlvyTypeCode " + dlvyTypeCode);
		log.debug(" layerType " + layerType);

		for (PdProductOptionWithStockAndPrice optn : prdtOptList) {
			int stockTotQTy = 0;
			if (UtilsText.equals(optn.getSellStatCode(), "10001")) {
				if (UtilsText.equals(mmnyPrdtYn, "Y")) {
					if (UtilsText.equals(layerType, "Order")) {
						if (UtilsText.equals(dlvyTypeCode, CommonCode.DLVY_TYPE_CODE_STORE_PICKUP)) {
							optn.setStockTotQty(optn.getStockAsQty());
						} else {
							optn.setStockTotQty(optn.getStockAiQty() + optn.getStockAsQty() + optn.getStockAwQty());
						}
					} else { // 클레임
						optn.setStockTotQty(optn.getStockAiQty() + optn.getStockAsQty() + optn.getStockAwQty());
					}
				} else {
					optn.setStockTotQty(optn.getStockVdQty());
				}
			} else {
				optn.setStockTotQty(0);
			}
		}

		long sizeOptn = prdtOptList.stream().filter(p -> !UtilsText.isEmpty(p.getOptnName())).count(); // 사이즈
		long addOptn = prdtOptList.stream().filter(p -> !UtilsText.isEmpty(p.getAddOptn2Text())).count(); // 추가옵션

		List<PdProductOptionWithStockAndPrice> distinctOptnList = new ArrayList<>(); // 중복 제거 list
		List<PdProductOptionWithStockAndPrice> copyOptnList = new ArrayList<>(); // 옵션 정보 copy list
		List<PdProductOptionWithStockAndPrice> copyAddOptnList = new ArrayList<>(); // 옵션 하위 정보 copy list
		List<PdProductOptionWithStockAndPrice> OptnList = new ArrayList<>(); // 담을 변수

		if (addOptn > 0) {
			copyOptnList.addAll(prdtOptList);
			copyAddOptnList.addAll(prdtOptList);

			// 중복 옵션명만 노출 처리
			Map seen = new ConcurrentHashMap<>();
			distinctOptnList = prdtOptList.stream().filter(x -> seen.putIfAbsent(x.getOptnName(), Boolean.TRUE) == null)
					.collect(Collectors.toList());

			// 중복 옵션에 대한 하위 옵션 총 수량
			Map<String, IntSummaryStatistics> optnSummary = copyOptnList.stream().filter(x -> x != null)
					.collect(Collectors.groupingBy(PdProductOptionWithStockAndPrice::getOptnName,
							Collectors.summarizingInt(PdProductOptionWithStockAndPrice::getStockTotQty)));

			for (PdProductOptionWithStockAndPrice distinctOptn : distinctOptnList) {
				for (Entry<String, IntSummaryStatistics> data : optnSummary.entrySet()) {
					if (UtilsText.equals(data.getKey(), distinctOptn.getOptnName())) {
						PdProductOptionWithStockAndPrice option = new PdProductOptionWithStockAndPrice();
						option.setPrdtNo(distinctOptn.getPrdtNo());
						option.setPrdtOptnNo(distinctOptn.getPrdtOptnNo());
						option.setOptnName(distinctOptn.getOptnName());
						option.setOptnAddAmt(distinctOptn.getOptnAddAmt());
						// aa.set 금액
						option.setStockTotQty((int) data.getValue().getSum());
						// setCartCpnSelected(true);

						if (UtilsText.equals(distinctOptn.getOptnName(), optnInfo.getOptnName())) {
							option.setOptnSelected(true);
						} else {
							option.setOptnSelected(false);
						}

						OptnList.add(option);

					}
				}
			}

			// 추가 옵션 --> 상품 옵션명으로 하우 옵션정보 조회
			copyAddOptnList = copyAddOptnList.stream().filter(p -> p.getAddOptn2Text() != null)
					.filter(p -> UtilsText.equals(p.getOptnName(), optnInfo.getOptnName()))
					.collect(Collectors.toList());

			for (PdProductOptionWithStockAndPrice addOptnInfo : copyAddOptnList) {
				if (UtilsText.equals(addOptnInfo.getPrdtOptnNo(), optnInfo.getPrdtOptnNo())) {
					addOptnInfo.setOptnSelected(true);
				} else {
					addOptnInfo.setOptnSelected(false);
				}
			}

		} else {
			for (PdProductOptionWithStockAndPrice prdtOpt : prdtOptList) {
				if (UtilsText.equals(prdtOpt.getPrdtOptnNo(), optnInfo.getPrdtOptnNo())) {
					prdtOpt.setOptnSelected(true);
				} else {
					prdtOpt.setOptnSelected(false);
				}
			}
		}

		parameter.addAttribute("prdtInfo", prdtInfo); // 주문 상품 정보
		parameter.addAttribute("prdtOptList", prdtOptList); // 상품 옵션 리스트
		parameter.addAttribute("OptnList", OptnList); // 중복 제거 옵션 리스트
		parameter.addAttribute("addOptnList", copyAddOptnList); // 중복 제거 옵션 리스트
		parameter.addAttribute("sizeOptn", sizeOptn); // 사이즈 count
		parameter.addAttribute("addOptn", addOptn); // 추가 옵션 count
		parameter.addAttribute("promoNo", promoNo); // 프로모션번호
		parameter.addAttribute("layerType", layerType); // layer정보
		parameter.addAttribute("dlvyTypeCode", dlvyTypeCode); // 주문 정보 배송타입

	}

	/**
	 * @Desc : 추가 옵션 선택 조회
	 * @Method Name : getPrdtOptionAdd
	 * @Date : 2019. 7. 17.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @return
	 */
	public Map<String, Object> getPrdtOptionAdd(Parameter<OcOrderProduct> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		OcOrderProduct ocOrderProduct = parameter.get();
		// 상품 옵션 목록 조회
		String prdtNo = ocOrderProduct.getPrdtNo();
		String mmnyPrdtYn = ocOrderProduct.getMmnyPrdtYn();
		String selectVal = ocOrderProduct.getSelectVal();
		String promoNo = ocOrderProduct.getPromoNo();
		String layerType = ocOrderProduct.getLayerType();
		String dlvyTypeCode = ocOrderProduct.getDlvyTypeCode();

		log.debug(" promoNo " + promoNo);
		log.debug(" mmnyPrdtYn " + mmnyPrdtYn);

		log.debug(" dlvyTypeCode " + dlvyTypeCode);
		log.debug(" layerType " + layerType);

		List<PdProductOptionWithStockAndPrice> prdtOptList = productInsideOptionService
				.getProductOptionListWithStockAndPrice(prdtNo, promoNo);

		for (PdProductOptionWithStockAndPrice optn : prdtOptList) {
			int stockTotQTy = 0;
			if (UtilsText.equals(optn.getSellStatCode(), "10001")) {
				if (UtilsText.equals(mmnyPrdtYn, "Y")) {
					if (UtilsText.equals(layerType, "Order")) {
						if (UtilsText.equals(dlvyTypeCode, CommonCode.DLVY_TYPE_CODE_STORE_PICKUP)) {
							optn.setStockTotQty(optn.getStockAsQty());
						} else {
							optn.setStockTotQty(optn.getStockAiQty() + optn.getStockAsQty() + optn.getStockAwQty());
						}
					} else { // 클레임
						optn.setStockTotQty(optn.getStockAiQty() + optn.getStockAsQty() + optn.getStockAwQty());
					}
				} else {
					optn.setStockTotQty(optn.getStockVdQty());
				}
			} else {
				optn.setStockTotQty(0);
			}
		}

		prdtOptList = prdtOptList.stream().filter(p -> p.getAddOptn2Text() != null)
				.filter(p -> UtilsText.equals(p.getOptnName(), selectVal)).collect(Collectors.toList());

		resultMap.put("addOption", prdtOptList);
		resultMap.put("addOptionSize", prdtOptList.size());

		return resultMap;
	}

	/**
	 * @Desc : 상품 옵션 정보 수정 저장 처리
	 * @Method Name : setPrdtOptionSave
	 * @Date : 2019. 7. 17.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderProduct
	 * @return
	 */
	public Map<String, Object> setPrdtOptionSave(OcOrderProduct ocOrderProduct) throws Exception {
		// 20190812 fo mypage와 동일 처리

		log.debug("ocOrderProduct >>>>>>>>>" + ocOrderProduct);

		boolean rtnFlag = true;
		String resultCode = Const.BOOLEAN_TRUE;
		String resultMsg = "상품옵션 정보가 수정 되었습니다.";

		Map<String, Object> result = new HashMap<>();
		UserDetails user = LoginManager.getUserDetails();

		ocOrderProduct.setModerNo(user.getAdminNo()); // 수정자명

//		OcOrder ocOrder = new OcOrder();
//		ocOrder.setOrderNo(ocOrderProduct.getOrderNo());
//		OcOrder orderDtail = ocOrderDao.selectOrderDetail(ocOrder);

		// step 1
		OcOrderDeliveryHistory dlvyHist = new OcOrderDeliveryHistory();
		dlvyHist.setOrderNo(ocOrderProduct.getOrderNo());
		dlvyHist.setOrderPrdtSeq(ocOrderProduct.getOrderPrdtSeq());
		;
		OcOrderDeliveryHistory dlvyHistData = ocOrderDeliveryHistoryDao.getPrdtDeliveryInfo(dlvyHist);

		if (dlvyHistData == null) {
			rtnFlag = false;
			resultCode = Const.BOOLEAN_FALSE;
			resultMsg = "배송 히스토리 데이타가 없습니다.";
		}

		if (UtilsText.equals(dlvyHistData.getWmsSendYn(), Const.BOOLEAN_TRUE)) {
			rtnFlag = false;
			resultCode = Const.BOOLEAN_FALSE;
			resultMsg = "상품출고 상태입니다. 옵션변경이 불가능합니다.";
		}

		// 분실 처리 재배송건 옵션 변경 불가
		// aw as 건 옵션 변경 불가
		if (UtilsText.equals(dlvyHistData.getStockGbnCode(), CommonCode.STOCK_GBN_CODE_AW)
				|| UtilsText.equals(dlvyHistData.getStockGbnCode(), CommonCode.STOCK_GBN_CODE_AS)) {
			rtnFlag = false;
			resultCode = Const.BOOLEAN_FALSE;
			resultMsg = "AW, AS 발송건은 옵션변경이 불가능 합니다.";
		}

		if (dlvyHistData.getClmNo() != null) {
			rtnFlag = false;
			resultCode = Const.BOOLEAN_FALSE;
			resultMsg = "옵션변경이 불가능 합니다.(클레임)";
		}

		OcOrderProduct orgPrdtData = ocOrderProductDao.selectByPrimaryKey(ocOrderProduct);

		log.debug("옵션수정 이전 상품정보 orgPrdtData >>>>>>>>>" + orgPrdtData);

		if (orgPrdtData == null) {
			rtnFlag = false;
			resultCode = Const.BOOLEAN_FALSE;
			resultMsg = "상품 데이타가 없습니다.";
		}

		String prdtNo = orgPrdtData.getPrdtNo();
		String changePrdtOptnNo = ocOrderProduct.getPrdtOptnNo(); // 변경 할 옵션 번호
		String storePickupYn = "N"; // ai 옵션만 변경 가능
		String promoNo = "";

		OcOrderProductApplyPromotion applyPromotion = new OcOrderProductApplyPromotion();
		applyPromotion.setOrderNo(ocOrderProduct.getOrderNo());
		applyPromotion.setOrderPrdtSeq(ocOrderProduct.getOrderPrdtSeq());

		String[] promoTypeCodes = { CommonCode.PROMO_TYPE_CODE_ONE_PLUS_ONE // 1+1증정
				, CommonCode.PROMO_TYPE_CODE_DISCOUNT_MULTI_SHOUES // 다족구매할인
				, CommonCode.PROMO_TYPE_CODE_DISCOUNT_IMMEDIATELY // 즉시할인
				, CommonCode.PROMO_TYPE_CODE_DISCOUNT_IMMEDIATELY_AFFILIATES // 제휴사 즉시할인
				, CommonCode.PROMO_TYPE_CODE_SPECIAL_PRICE_TIME // 타임특가
		};

		applyPromotion.setPromoTypeCodes(promoTypeCodes);

		List<OcOrderProductApplyPromotion> applyPromotionData = new ArrayList<>();
		applyPromotionData = ocOrderProductApplyPromotionDao.selectOrderProductApplyPromotion(applyPromotion);

		if (applyPromotionData != null && applyPromotionData.size() > 0) {
			// System.out.println("applyPromotionData promoNo >> " +
			// applyPromotionData.get(0).getPromoNo());
			promoNo = applyPromotionData.get(0).getPromoNo(); // 사은품을 제외한 프로모션 정보
		}

		// 상품 재고 확인
		int newOptionStock = productInsideOptionService.getProductOptionStock(prdtNo, changePrdtOptnNo, storePickupYn,
				promoNo);

		log.debug("변경할 재고 수량 " + newOptionStock + "주문 수량 " + orgPrdtData.getOrderQty());
		if (orgPrdtData.getOrderQty() > newOptionStock) {
			rtnFlag = false;
			resultCode = Const.BOOLEAN_FALSE;
			resultMsg = "재고 수량이 부족합니다.";
		}

		// 옵션 정보 수정
		if (rtnFlag) {
			log.debug("ocOrderProduct >> " + ocOrderProduct);
			rtnFlag = ocOrderProductDao.setPrdtOptionSave(ocOrderProduct);

			if (!rtnFlag) {
				resultCode = Const.BOOLEAN_FALSE;
				resultMsg = "옵션 정보 수정 실패하였습니다.";
			}

			// 재고 차감 , 증가

			// 이전 재고구분코드 증가
			log.debug("이전 재고 증가  prdtNo:" + prdtNo + " optnNo:" + orgPrdtData.getPrdtOptnNo());
			orderService.updateProductStockAdjust(prdtNo, orgPrdtData.getPrdtOptnNo(), 1, CommonCode.STOCK_GBN_CODE_AI,
					false);

			log.debug("변경 재고 차감  prdtNo:" + prdtNo + " optnNo:" + changePrdtOptnNo);
			// 변경 재고구분코드 차감
			orderService.updateProductStockAdjust(prdtNo, changePrdtOptnNo, 1, CommonCode.STOCK_GBN_CODE_AI, true);

		}

		// 히스토리 ROW INSERT
		if (rtnFlag) {

			OcOrderProductHistory prdtHistory = new OcOrderProductHistory();
			prdtHistory.setOrderNo(ocOrderProduct.getOrderNo());
			prdtHistory.setOrderPrdtSeq(ocOrderProduct.getOrderPrdtSeq());
			prdtHistory.setPrdtNo(orgPrdtData.getPrdtNo());
			prdtHistory.setPrdtOptnNo(ocOrderProduct.getPrdtOptnNo());
			prdtHistory.setPrdtName(orgPrdtData.getPrdtName());
			prdtHistory.setEngPrdtName(orgPrdtData.getEngPrdtName());
			prdtHistory.setOptnName(ocOrderProduct.getOptnName());
			prdtHistory.setOrderPrdtStatCode(orgPrdtData.getOrderPrdtStatCode()); // 변경전 상태 코드 그대로 진행.
			prdtHistory.setNoteText("옵션 변경"); // NOTE_TEXT
			prdtHistory.setRgsterNo(ocOrderProduct.getModerNo());

			int insertCnt = ocOrderProductHistoryDao.insertProductHistory(prdtHistory);

			if (insertCnt <= 0) {
				rtnFlag = false;
				resultCode = Const.BOOLEAN_FALSE;
				resultMsg = "상품 히스토리 데이타가 등록되지 않았습니다.";
			}
		}

		// if_order table update
		if (rtnFlag) {

			IfOrder ifOrder = new IfOrder();
			ifOrder.setOrdno(dlvyHistData.getDlvyIdText());
			ifOrder.setOpt1(changePrdtOptnNo);

			// 2020.03.09 : VNDR_PRDT_NO_TEXT + '001' + (CHANGE) PRDT_OPTN_NO
			String sangPumFullCd = UtilsText.concat(orgPrdtData.getVndrPrdtNoText(), "001", changePrdtOptnNo);
			ifOrder.setSangpumfullcd(sangPumFullCd);

			int updCnt = ifOrderDao.updateOptionChange(ifOrder); // if order 옵션변경

			if (updCnt > 0) {
				log.error("if order 테이블 정보 upate ");
			} else {
				log.error("if order 테이블 정보 없음 data 생성전  ");
			}
		}

		if (rtnFlag) {
			if (UtilsText.equals(dlvyHistData.getWmsSendYn(), Const.BOOLEAN_TRUE)) {
// 20191016 wmssendyn = Y 인 조건은 없음 	, interface 제거
//				rtnFlag = interfacesOrderService.updateOrderChangeOptnNoTrx("AI", dlvyHistData.getDlvyIdText(),
//						String.valueOf(ocOrderProduct.getOrderPrdtSeq()), orgPrdtData.getInsdMgmtInfoText(),
//						orgPrdtData.getVndrPrdtNoText() + "001" + orgPrdtData.getPrdtOptnNo(),
//						orgPrdtData.getVndrPrdtNoText() + "001" + ocOrderProduct.getPrdtOptnNo());
//
//				if (!rtnFlag) {
//					resultCode = Const.BOOLEAN_FALSE;
//					resultMsg = "상품 옵션 정보 변경 interface 연동 실패 하였습니다.";
//				}
			}
		}

		result.put("resultCode", resultCode);
		result.put("resultMsg", resultMsg);
		return result;

	}

	/**
	 * @Desc : 현금영수증 정보 조회
	 * @Method Name : getCashReceipte
	 * @Date : 2019. 8. 13.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderPayment
	 * @return
	 */
	public Map<String, Object> getCashReceipte(OcOrderPayment ocOrderPayment) throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		double naverTotCashAmt = 0.0;
		String orderNo = ocOrderPayment.getOrderNo();
		String pymntMeansCode = ocOrderPayment.getPymntMeansCode();
		String siteNo = ocOrderPayment.getSiteNo();
		OcOrderPayment orderPymntData = ocOrderPaymentDao.selectByPrimaryKey(ocOrderPayment);

		// 네이버 페이의 경우
		if (UtilsText.equals(pymntMeansCode, CommonCode.PYMNT_MEANS_CODE_NAVER_PAY)) {
			String paymentId = orderPymntData.getPymntAprvNoText();
			String partnerCode = "";
			if (UtilsText.equals(ocOrderPayment.getSiteNo(), Const.SITE_NO_ART)) {
				partnerCode = Config.getString("art.naver.api.partnerCode");
			}
			if (UtilsText.equals(orderPymntData.getSiteNo(), Const.SITE_NO_OTS)) {
				partnerCode = Config.getString("ots.naver.api.partnerCode");
			}

			NaverPaymentCashAmountReturn naverPymntData = naverService.viewCashAmount(siteNo, partnerCode, paymentId);

			naverTotCashAmt = naverPymntData.getBody().getTotalCashAmount();
		} else if (UtilsText.equals(pymntMeansCode, CommonCode.PYMNT_MEANS_CODE_MOBILE_GIFT)) { // 기프트 상품권 일경 {
			OcCashReceipt ocr = new OcCashReceipt();
			ocr.setOrderNo(orderNo);

			OcCashReceipt ocrInfo = ocCashReceiptDao.getGiftCashReceipt(ocr);
			if (ocrInfo != null) {
				orderPymntData.setCashRcptDealNo(ocrInfo.getRcptDealNoText()); // 현금영수증 번호
				orderPymntData.setPymntAmt(ocrInfo.getRcptIssueAmt()); // 현금영수증 영수증발급금액
			}
		}
		resultMap.put("orderPymntData", orderPymntData);
		resultMap.put("naverTotCashAmt", naverTotCashAmt);
		return resultMap;
	}

	/*************************************************************************************************
	 * jeon end
	 *************************************************************************************************/

	/*************************************************************************************************
	 * ljyoung start
	 *************************************************************************************************/

	/**
	 *
	 * @Desc : 공콩코드 조회
	 * @Method Name : getCodeListByGroup
	 * @Date : 2019. 2. 14.
	 * @Author : ljyoung@3top.co.kr
	 * @param codeFields
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<SyCodeDetail>> getCodeListByGroup(String[] codeFields) throws Exception {
		return commonCodeService.getCodeListByGroup(codeFields);
	}

	/**
	 *
	 * @Desc : 사이트 목록 조회
	 * @Method Name : getSiteList
	 * @Date : 2019. 2. 14.
	 * @Author : ljyoung@3top.co.kr
	 * @return
	 * @throws Exception
	 */
	public Parameter<?> getSiteList(Parameter<?> parameter) throws Exception {
		Pair<JSONObject, List<SySite>> siteInfo = siteService.getSiteListByCombo();
		parameter.addAttribute("siteCombo", siteInfo.getFirst());
		parameter.addAttribute("siteInfo", siteInfo.getSecond());
		return parameter;
	}

	/**
	 *
	 * @Desc : 네이버페이 결제목록 조회
	 * @Method Name : naverpaymentList
	 * @Date : 2019. 2. 14.
	 * @Author : ljyoung@3top.co.kr
	 * @param paymentId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws PaymentException
	 */
	public NaverPaymentListReturn naverpaymentList(String siteNo, String paymentId, String startDate, String endDate)
			throws PaymentException {
//		String clientId = "";
//		String clientSecret = "";
		String partnerCode = "";
		if (UtilsText.equals(siteNo, Const.SITE_NO_ART)) {
//			clientId = Config.getString("art.naver.api.clientId");
//			clientSecret = Config.getString("art.naver.api.clientSecret");
			partnerCode = Config.getString("art.naver.api.partnerCode");
		}
		if (UtilsText.equals(siteNo, Const.SITE_NO_OTS)) {
//			clientId = Config.getString("ots.naver.api.clientId");
//			clientSecret = Config.getString("ots.naver.api.clientSecret");
			partnerCode = Config.getString("ots.naver.api.partnerCode");
		}
		return naverService.list(siteNo, partnerCode, paymentId, startDate, endDate, 1);
	}

	/**
	 * @Desc : 관리자 메모 갯수 조회
	 * @Method Name : memoList
	 * @Date : 2019. 2. 14.
	 * @Author : ljyoung@3top.co.kr
	 * @param ocOrderMemo
	 * @return
	 * @throws Exception
	 */
	public int memoCountByOrderNo(OcOrderMemo ocOrderMemo) throws Exception {
		return memoDao.countByOrderNo(ocOrderMemo);
	}

	/**
	 * @Desc : 관리자 메모 목록 조회
	 * @Method Name : memoList
	 * @Date : 2019. 2. 14.
	 * @Author : ljyoung@3top.co.kr
	 * @param ocOrderMemo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> memoSelectByOrderNo(OcOrderMemo ocOrderMemo) throws Exception {
		int count = memoCountByOrderNo(ocOrderMemo);
		List<OcOrderMemo> dataList = new ArrayList<OcOrderMemo>();

		if (count > 0) {
			dataList = memoDao.selectByOrderNo(ocOrderMemo);
		}
		return getDataMap(count, dataList);
	}

	/**
	 * @Desc : 주문 ,클레임 전체 관리자 메모 갯수 조회
	 * @Method Name : memoCountAllByOrderNo
	 * @Date : 2019. 2. 15.
	 * @Author : ljyoung@3top.co.kr
	 * @param ocOrderMemo
	 * @return
	 * @throws Exception
	 */
	public int memoCountAllByOrderNo(OcOrderMemo ocOrderMemo) throws Exception {
		return memoDao.countAllByOrderNo(ocOrderMemo);
	}

	/**
	 * @Desc : 주문 ,클레임 전체 관리자 메모 목록 조회
	 * @Method Name : memoSelectAllByOrderNo
	 * @Date : 2019. 2. 15.
	 * @Author : ljyoung@3top.co.kr
	 * @param ocOrderMemo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> memoSelectAllByOrderNo(OcOrderMemo ocOrderMemo) throws Exception {
		int count = memoCountAllByOrderNo(ocOrderMemo);
		List<OcOrderMemo> dataList = new ArrayList<OcOrderMemo>();

		if (count > 0)
			dataList = memoDao.selectAllByOrderNo(ocOrderMemo);
		return getDataMap(count, dataList);
	}

	/**
	 * @Desc : 관리자 메모 목록 등록
	 * @Method Name : memoCreate
	 * @Date : 2019. 2. 14.
	 * @Author : ljyoung@3top.co.kr
	 * @param ocOrderMemo
	 * @throws Exception
	 */
	public void memoCreate(OcOrderMemo ocOrderMemo) throws Exception {
		// 개행 추가
		if (UtilsText.isNoneBlank(ocOrderMemo.getMemoText())) {
			ocOrderMemo.setMemoText(
					ocOrderMemo.getMemoText().replace(Const.STRING_HTML_NEW_LINE, Const.STRING_HTML_BR_TAG));
		}
		memoDao.insertOrdertMemo(ocOrderMemo);
	}

	/**
	 * @Desc : 관리자 메모 목록 삭제
	 * @Method Name : memoDelete
	 * @Date : 2019. 2. 14.
	 * @Author : ljyoung@3top.co.kr
	 * @param ocOrderMemo
	 * @throws Exception
	 */
	public void memoDelete(OcOrderMemo ocOrderMemo) throws Exception {
		memoDao.delete(ocOrderMemo);
	}

	/**
	 * @Desc : 결제 실패 목록 조회
	 * @Method Name : selectPaymentFailList
	 * @Date : 2019. 2. 19.
	 * @Author : ljyoung@3top.co.kr
	 * @param ocOrderPayment
	 * @return
	 * @throws Exception
	 */
	public Page<OcOrderPaymentFailureHistory> selectPaymentFailList(
			Pageable<OcOrderPaymentFailureHistory, OcOrderPaymentFailureHistory> pageable) throws Exception {
		int count = ocOrderPaymentFailureHistoryDao.selectPaymentFailListCount(pageable);
		pageable.setTotalCount(count);
		if (count > 0)
			pageable.setContent(ocOrderPaymentFailureHistoryDao.selectPaymentFailList(pageable));
		return pageable.getPage();
	}

	/**
	 * @Desc : 무통장입금 오류 목록 조회
	 * @Method Name : selectAccountTransferFailList
	 * @Date : 2019. 2. 21.
	 * @Author : ljyoung@3top.co.kr
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<OcOrderZeroBankbookFailureDetails> selectAccountTransferFailList(
			Pageable<OcOrderZeroBankbookFailureDetails, OcOrderZeroBankbookFailureDetails> pageable) throws Exception {
		int count = ocOrderZeroBankbookFailureDetailsDao.selectListCount(pageable);
		pageable.setTotalCount(count);
		if (count > 0)
			pageable.setContent(ocOrderZeroBankbookFailureDetailsDao.selectList(pageable));
		return pageable.getPage();
	}

	public void readOcOrderZeroBankbookFailureDetails(Parameter<OcOrderZeroBankbookFailureDetails> parameter)
			throws Exception {
		parameter.addAttribute("refundInfo", ocOrderZeroBankbookFailureDetailsDao.selectByPrimaryKey(parameter.get()));
	}

	/**
	 * @Desc : 무통장입금 오류 건 환불계좌 정보 업데이트
	 * @Method Name : registRefundInfo
	 * @Date : 2019. 3. 28.
	 * @Author : ljyoung@3top.co.kr
	 * @param ocOrderZeroBankbookFailureDetails
	 * @throws Exception
	 */
	public void registOcOrderZeroBankbookFailureDetails(
			OcOrderZeroBankbookFailureDetails ocOrderZeroBankbookFailureDetails) throws Exception {
		ocOrderZeroBankbookFailureDetailsDao.update(ocOrderZeroBankbookFailureDetails);
	}

	/**
	 * @Desc : 무통장입금 오류 건 처리 완료
	 * @Method Name : completeRefundProc
	 * @Date : 2019. 3. 28.
	 * @Author : ljyoung@3top.co.kr
	 * @param ocOrderZeroBankbookFailureDetails
	 * @throws Exception
	 */
	public void completeOcOrderZeroBankbookFailureDetails(
			OcOrderZeroBankbookFailureDetails ocOrderZeroBankbookFailureDetails) throws Exception {
		if (ocOrderZeroBankbookFailureDetails.isUpdateProcDate()) {
			ocOrderZeroBankbookFailureDetails.setModDtm(new Timestamp(new Date().getTime()));
			ocOrderZeroBankbookFailureDetails.setModerNo(LoginManager.getUserDetails().getAdminNo());
		}
		ocOrderZeroBankbookFailureDetails.setProcYn("Y");
		ocOrderZeroBankbookFailureDetailsDao.update(ocOrderZeroBankbookFailureDetails);
	}

	/**
	 * @Desc : 주문 단일 상품 기본 정보 조회
	 * @Method Name : selectProduct
	 * @Date : 2019. 2. 28.
	 * @Author : ljyoung@3top.co.kr
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public OcOrderProduct selectProduct(OcOrderProduct ocOrderProduct) throws Exception {
		return ocOrderProductDao.selectByPrimaryKey(ocOrderProduct);
	}

	/**
	 * @Desc : 주문 단일 상품 상태 변경 이력 조회
	 * @Method Name : selectOrderProductStatusHistory
	 * @Date : 2019. 2. 28.
	 * @Author : ljyoung@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public List<OcOrderProductHistory> selectOrderProductHistory(OcOrderProductHistory ocOrderProductStatusHistory)
			throws Exception {
		return ocOrderProductHistoryDao.selectByProduct(ocOrderProductStatusHistory);
	}

	public Page<OcOrderMemberExpostSaveVO> selectMemberExpostSavePointList(
			Pageable<OcOrderMemberExpostSaveVO, OcOrderMemberExpostSaveVO> pageable) throws Exception {
		int count = mbMemberExpostSavePointDao.countAllAdminList(pageable);
		pageable.setTotalCount(count);

		if (count > 0)
			pageable.setContent(mbMemberExpostSavePointDao.selectAdminList(pageable));

		return pageable.getPage();
	}

	/**
	 * @Desc : 목록 형태로 데이터 변환.
	 * @Method Name : getDataMap
	 * @Date : 2019. 2. 21.
	 * @Author : ljyoung@3top.co.kr
	 * @param iTotCnt
	 * @param data
	 * @return
	 */
	public Map<String, Object> getDataMap(int iTotCnt, Object data) {
		Map<String, Object> resultData = new HashMap<String, Object>();

		resultData.put("Total", iTotCnt);
		resultData.put("Data", data);

		return resultData;
	}

	/*************************************************************************************************
	 * ljyoung end
	 *************************************************************************************************/

	/*************************************************************************************************
	 * kth start
	 *************************************************************************************************/

	/*************************************************************************************************
	 * kth end
	 *************************************************************************************************/
	/*************************************************************************************************
	 * lsh start
	 *************************************************************************************************/

	/**
	 * @Desc : 업체 주문 상세 화면 포워딩시 주문 데이타 조회
	 * @Method Name : getOrderVendorDetailInfo
	 * @Date : 2019. 4. 23.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	public void getOrderVendorDetailInfo(Parameter<OcOrder> parameter) throws Exception {
		OcOrder ocOrder = parameter.get();
		String orderNo = parameter.get().getOrderNo();
		String vndrNo = LoginManager.getUserDetails().getVndrNo();

		// 업체 코드 셋팅
		ocOrder.setVndrNo(vndrNo);

		// 1. 주문 마스터 정보 조회
		OcOrder orderDtail = ocOrderDao.selectOrderDetail(ocOrder);

		// 마스터 정보가 있을 경우만 해당 정보를 노출 한다.
		if (UtilsObject.isNotEmpty(orderDtail)) {
			String memberNo = orderDtail.getMemberNo();

			// 회원 기본 정보 조회
			MbMember memberInfo = mbMemberDao.selectMemberDefalutInfo(memberNo);
			orderDtail.setLoginId(memberInfo.getLoginId());

			// 관리자 메모 건수 조회
			OcOrderMemo ocOrderMemo = new OcOrderMemo();
			ocOrderMemo.setOrderNo(orderNo);

			int memoTotCnt = memoCountAllByOrderNo(ocOrderMemo);
			orderDtail.setMemoTotCnt(memoTotCnt);
		}

		// addAttribute
		parameter.addAttribute("orderDtail", orderDtail);
		parameter.addAttribute("vndrNo", vndrNo);

	}

	/**
	 * @Desc : 업체 주문 정보 탭 클릭시 주문정보 조회
	 * @Method Name : getReadOrderVendorInfoTab
	 * @Date : 2019. 4. 23.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	public void getReadOrderVendorInfoTab(Parameter<OcOrder> parameter) throws Exception {

		OcOrder ocOrder = parameter.get();
		// 1. 주문 마스터 정보 조회
		OcOrder orderDtail = ocOrderDao.selectOrderDetail(ocOrder);

		String[] codeFields = { CommonCode.CVNSTR_CODE // 편의점 코드
				, CommonCode.STOCK_GBN_CODE // 재고 구분 코드
				, CommonCode.LOGIS_VNDR_CODE // 택배사코드
				, CommonCode.ORDER_PRDT_STAT_CODE // 주문상품상태코드
				, CommonCode.DLVY_STAT_CODE // 배송상태코드
				, CommonCode.DLVY_DSCNTC_RSN_CODE // 배송 중단 사유코드
				, CommonCode.PYMNT_STAT_CODE // 결제상태코드
				, CommonCode.PYMNT_MEANS_CODE // 결제수단코드
				, CommonCode.DEVICE_CODE // 디바이스코드
				, CommonCode.DLVY_DSCNTC_RSN_CODE // 배송중단사유코드
				, CommonCode.CLM_GBN_CODE // 클레임유형코드
				, CommonCode.SELL_CNCL_RSN_CODE // 판매취소요청코드 CommonCode.SELL_CNCL_RSN_CODE
		};

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		parameter.addAttribute("orderDtail", orderDtail);
		parameter.addAttribute("codeCombo", pair.getFirst()); // 코드 정보 combo
		parameter.addAttribute("codeList", pair.getSecond()); // 코드 정보 list
	}

	/**
	 * @Desc : 업체 주문 상품 리스트
	 * @Method Name : getReadOrderVendorProductList
	 * @Date : 2019. 4. 23.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public List<OcOrderProduct> getReadOrderVendorProductList(Parameter<?> parameter) throws Exception {

		List<OcOrderProduct> productList = null;
		if (LoginManager.getUserDetails().getVndrNo() == null && LoginManager.getUserDetails().getVndrNo() == "") {
			throw new Exception("Login ID를 확인해주세요. ");
		} else {
			// 교환주문건 포함하여 주문 조회
			String orderNo = parameter.getString("orderNo");
			String mmnyPrdtYn = parameter.getString("mmnyPrdtYn");

			OcOrderProduct ocOrderProduct = new OcOrderProduct();
			ocOrderProduct.setOrgOrderNo(orderNo);
			ocOrderProduct.setMmnyPrdtYn(mmnyPrdtYn);

			String[] salesCnclGbnTypes = { CommonCode.SALES_CNCL_GBN_TYPE_SALE,
					CommonCode.SALES_CNCL_GBN_TYPE_EXCHANGE_SALE };
			ocOrderProduct.setSalesCnclGbnTypes(salesCnclGbnTypes);

			ocOrderProduct.setPrdtTypeCode(CommonCode.PRDT_TYPE_CODE_GIFT);

			ocOrderProduct.setVndrNo(LoginManager.getUserDetails().getVndrNo());
			// 주문 상품 목록 조회
			productList = ocOrderProductDao.selectVendorProductList(ocOrderProduct);

			// 2020.05.27 : 입점 주문 상품 배송 조회 추가
			String[] orderNos = productList.stream().map(OcOrderProduct::getOrderNo).distinct().toArray(String[]::new);

			OcOrderDeliveryHistory ocOrderDeliveryHistory = new OcOrderDeliveryHistory();
			ocOrderDeliveryHistory.setOrderNos(orderNos);

			List<OcOrderDeliveryHistory> deliveryList = selectDeliveryHistoryMaxList(ocOrderDeliveryHistory);

			productList.forEach(x -> deliveryList.forEach(y -> {
				if (x.getOrderNo().equals(y.getOrderNo()) && x.getOrderPrdtSeq().equals(y.getOrderPrdtSeq())) {
					// 배송 id
					x.setOrderDlvyHistSeq(y.getOrderDlvyHistSeq());
					// 택배사
					x.setLogisVndrCode(y.getLogisVndrCode());
					// 택배사명
					x.setLogisVndrCodeName(y.getLogisVndrCodeName());
					// 송장번호
					x.setWaybilNoText(y.getWaybilNoText());
					// 배송분실처리
					x.setMissProcYn(y.getMissProcYn());
					// 배송중단여부
					x.setDlvyDscntcYn(y.getDlvyDscntcYn());
					// 배송중단사유코드
					x.setDlvyDscntcRsnCode(y.getDlvyDscntcRsnCode());
					// 배송중단처리일시
					x.setDlvyDscntcProcDtm(y.getDlvyDscntcProcDtm());
					// 배송처리일시
					x.setDlvyProcDtm(y.getDlvyProcDtm());
					// 발송처
					x.setStockGbnCode(y.getStockGbnCode());
					// 배송ID
					x.setDlvyIdText(y.getDlvyIdText());
					// 발송처 (notCombo)
					x.setOrgStockGbnCode(y.getOrgStockGbnCode());
					// 배송상태코드
					x.setDlvyStatCode(y.getDlvyStatCode());
				}
			}));
		}

		return productList;
	}

	/*************************************************************************************************
	 * lsh end
	 *************************************************************************************************/

	/**
	 *
	 * @Desc :
	 * @Method Name : updateProductStockAdjust
	 * @Date : 2019. 9. 17.
	 * @Author : NKB
	 * @param prdtNo       상품번호
	 * @param prdtOptionNo 옵션번호
	 * @param orderQty     수량
	 * @param stockGbnCode 발송처
	 * @param adjust       - True - 빼기, false - 더하기
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateProductStockAdjust(String prdtNo, String prdtOptionNo, int orderQty,
			String stockGbnCode, boolean adjust) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 상품정보를 조회한다.
		PdProduct productParam = new PdProduct();
		productParam.setPrdtNo(prdtNo);
		PdProduct pdProduct = pdProductDao.selectByPrimaryKey(productParam);

		String mmnyPrdtYn = pdProduct.getMmnyPrdtYn(); // 자사 상품여부
		String stockMgmtYn = pdProduct.getStockMgmtYn(); // 재고 관리여부

		log.debug("자사 상품여부::::" + mmnyPrdtYn + "::재고 관리여부::" + stockMgmtYn);
		log.error("자사 상품여부::::" + mmnyPrdtYn + "::재고 관리여부::" + stockMgmtYn);

		// DB 재고 조회용 매장/AW/AI
		List<Map<String, Object>> productOptionList = new ArrayList<Map<String, Object>>();
		Map<String, Object> prdoctOption = new HashMap<String, Object>();
		prdoctOption.put("prdtNo", prdtNo);
		prdoctOption.put("prdtOptnNo", prdtOptionNo);
		productOptionList.add(prdoctOption);

		try {

			// 재고를 차감용 Model
			PdProductOptionStock prdtStock = new PdProductOptionStock();

			log.error("mmnyPrdtYn >>>>>" + mmnyPrdtYn);
			log.error("adjust >>>>>" + adjust);
			log.error("stockGbnCode >>>>>" + stockGbnCode);
			log.error("stockMgmtYn >>>>>" + stockMgmtYn);

			/* 재고를 차감 */
			if (adjust) {
				// 재고를 차감 한다. AI
				if (CommonCode.STOCK_GBN_CODE_AI.equals(stockGbnCode)) {
					prdtStock.setOrderCount(orderQty);
					prdtStock.setPrdtNo(prdtNo);
					prdtStock.setPrdtOptnNo(prdtOptionNo);
					prdtStock.setStockGbnCode(CommonCode.STOCK_GBN_CODE_AI); // 온라인 물류 10000
					ocOrderProductDao.updateProductStock(prdtStock);
					resultMap.put("stockGbnCode", CommonCode.STOCK_GBN_CODE_AI);
				}

				// 재고를 차감 한다. AW
				if (CommonCode.STOCK_GBN_CODE_AW.equals(stockGbnCode)) {
					prdtStock.setOrderCount(orderQty);
					prdtStock.setPrdtNo(prdtNo);
					prdtStock.setPrdtOptnNo(prdtOptionNo);
					prdtStock.setStockGbnCode(CommonCode.STOCK_GBN_CODE_AW); // 온라인 물류 10001
					ocOrderProductDao.updateProductStock(prdtStock);
					resultMap.put("stockGbnCode", CommonCode.STOCK_GBN_CODE_AW);
				}

				// 재고를 차감 한다. AS
				if (CommonCode.STOCK_GBN_CODE_AS.equals(stockGbnCode)) {
					prdtStock.setOrderCount(orderQty);
					prdtStock.setPrdtNo(prdtNo);
					prdtStock.setPrdtOptnNo(prdtOptionNo);
					prdtStock.setStockGbnCode(CommonCode.STOCK_GBN_CODE_AS); // 매장 10002
					ocOrderProductDao.updateProductStock(prdtStock);
					resultMap.put("stockGbnCode", CommonCode.STOCK_GBN_CODE_AS);
				}

				// 재고를 차감 한다. AD
				if (CommonCode.STOCK_GBN_CODE_VD.equals(stockGbnCode)) {
					// 재고관리 안함
					if ("N".equals(stockMgmtYn)) {
						resultMap.put("stockGbnCode", CommonCode.STOCK_GBN_CODE_VD);
					} else {
						prdtStock.setOrderCount(orderQty);
						prdtStock.setPrdtNo(prdtNo);
						prdtStock.setPrdtOptnNo(prdtOptionNo);
						prdtStock.setStockGbnCode(CommonCode.STOCK_GBN_CODE_VD); // 업체 10003
						ocOrderProductDao.updateProductStock(prdtStock);
						resultMap.put("stockGbnCode", CommonCode.STOCK_GBN_CODE_VD);
					}
				}

			} else {
				/* 재고를 더하기 */

				// 재고를 차감 한다. AI
				if (CommonCode.STOCK_GBN_CODE_AI.equals(stockGbnCode)) {
					prdtStock.setOrderCount(orderQty);
					prdtStock.setPrdtNo(prdtNo);
					prdtStock.setPrdtOptnNo(prdtOptionNo);
					prdtStock.setStockGbnCode(CommonCode.STOCK_GBN_CODE_AI); // 온라인 물류 10000
					ocOrderProductDao.updateProductStockAdd(prdtStock);
					resultMap.put("stockGbnCode", CommonCode.STOCK_GBN_CODE_AI);
				}

				// 재고를 차감 한다. AW
				if (CommonCode.STOCK_GBN_CODE_AW.equals(stockGbnCode)) {
					prdtStock.setOrderCount(orderQty);
					prdtStock.setPrdtNo(prdtNo);
					prdtStock.setPrdtOptnNo(prdtOptionNo);
					prdtStock.setStockGbnCode(CommonCode.STOCK_GBN_CODE_AW); // 온라인 물류 10001
					ocOrderProductDao.updateProductStockAdd(prdtStock);
					resultMap.put("stockGbnCode", CommonCode.STOCK_GBN_CODE_AW);
				}

				// 재고를 차감 한다. AS
				if (CommonCode.STOCK_GBN_CODE_AS.equals(stockGbnCode)) {
					prdtStock.setOrderCount(orderQty);
					prdtStock.setPrdtNo(prdtNo);
					prdtStock.setPrdtOptnNo(prdtOptionNo);
					prdtStock.setStockGbnCode(CommonCode.STOCK_GBN_CODE_AS); // 매장 10002
					ocOrderProductDao.updateProductStockAdd(prdtStock);
					resultMap.put("stockGbnCode", CommonCode.STOCK_GBN_CODE_AS);
				}

				// 재고를 차감 한다. AD
				if (CommonCode.STOCK_GBN_CODE_VD.equals(stockGbnCode)) {
					// 재고관리 안함
					if ("N".equals(stockMgmtYn)) {
						resultMap.put("stockGbnCode", CommonCode.STOCK_GBN_CODE_VD);
					} else {
						prdtStock.setOrderCount(orderQty);
						prdtStock.setPrdtNo(prdtNo);
						prdtStock.setPrdtOptnNo(prdtOptionNo);
						prdtStock.setStockGbnCode(CommonCode.STOCK_GBN_CODE_VD); // 업체 10003
						ocOrderProductDao.updateProductStockAdd(prdtStock);
						resultMap.put("stockGbnCode", CommonCode.STOCK_GBN_CODE_VD);
					}
				}

			} // end if(adjust) {

		} catch (Exception e) {
			log.error(":::::::::::: 재고변경 ERROR : " + e.getMessage());
			throw new Exception("재고 변경시 에러가 발생되었습니다.");
		}

		return resultMap;

	}

	/**
	 * @Desc : 주문 전체 취소 처리 가능 체크
	 * @Method Name : getOrderCancelCheck
	 * @Date : 2019. 9. 18.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderProduct
	 * @return
	 */
	public Map<String, Object> getOrderCancelCheck(OcOrderProduct ocOrderProduct) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		boolean resultFlag = true;
		String resultCode = "Y";
		String resultMsg = "가능";

		String[] clmPrdtStatCodes = { CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_CANCEL_WITHDRAWAL_ACCEPT // 취소접수취소 , 취소접수철회
				, CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT,
				CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE// 교환접수취소 , 교환접수철회 , 교환불가
				, CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL,
				CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT, CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE // 반품접수취소
																														// ,
																														// 반품접수철회
																														// ,
																														// 반품
																														// 불가
		};
		ocOrderProduct.setClmPrdtStatCodes(clmPrdtStatCodes);

		OcOrderProduct orderStatCount = ocOrderProductDao.selectOrderStatCount(ocOrderProduct);

		int orderTotalCnt = orderStatCount.getOrderTotalCnt();
		int orderCancelPsltCnt = orderStatCount.getOrderCancelPsltCnt();
		// int orderConfirmCnt = orderStatCount.getOrderConfirmCnt() ;
		// int orderCancelCnt = orderStatCount.getOrderCancelCnt() ;
		int orderReqCancelCnt = orderStatCount.getOrderReqCancelCnt();
		int orderClaimTotalCnt = orderStatCount.getOrderClaimTotalCnt();

		if (orderClaimTotalCnt > 0) {
			resultMap.put("resultMsg", Const.BOOLEAN_FALSE);
			resultMap.put("resultMsg", "클레임 건수 존재");
			resultFlag = false;
		}

		if (resultFlag) {
			if (orderReqCancelCnt > 0) {
				resultMap.put("resultMsg", Const.BOOLEAN_FALSE);
				resultMap.put("resultMsg", "취소요청건 존재");
				resultFlag = false;
			}
		}

		if (resultFlag) {
			//
			if (orderTotalCnt == orderCancelPsltCnt) {
				resultMap.put("resultCode", Const.BOOLEAN_TRUE); //
			} else {
				resultMap.put("resultMsg", Const.BOOLEAN_FALSE); //
				resultMap.put("resultMsg", "상품전체 건수 != (결제완료  or 입금대기 or 상품준비중 )"); //
			}
		}

		return resultMap;
	}

	/**
	 * @Desc : 대시보드용 판매현황 조회 쿼리(한달) 업체, 자사상품 여부 (po)
	 * @Method Name : getOrderCntAmtList
	 * @Date : 2019. 10. 11.
	 * @Author : 신인철
	 * @return
	 * @throws Exception
	 */
	public List<OcOrder> getOrderCntAmtList(OcOrder ocOrder) throws Exception {
		return ocOrderDao.selectOrderCntAmtList(ocOrder);
	}

	/**
	 * @Desc : 대시보드용 주문/배송 현황 개수 정보 조회
	 * @Method Name : selectDashboardOrderDlvyCount
	 * @Date : 2019. 10. 14.
	 * @Author : 최경호
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDashboardOrderDlvyCount() throws Exception {
		UserDetails userDetails = LoginManager.getUserDetails();
		String vendorNo = "null".equals(userDetails.getVndrNo()) ? null : userDetails.getVndrNo();

		return ocOrderDao.selectDashboardOrderDlvyCount(vendorNo);
	}

	/**
	 * @Desc : 대시보드 베스트 입점사 조회
	 * @Method Name : getBestVndrList
	 * @Date : 2019. 10. 11.
	 * @Author : sic
	 * @return
	 * @throws Exception
	 */
	public List<OcOrder> getBestVndrList() throws Exception {
		return ocOrderDao.selectBestVndrList();
	}

	/**
	 * @Desc : 대시보드 업체배송지연 거수 TOP5
	 * @Method Name : getDelayedDeliveryVndrList
	 * @Date : 2019. 10. 14.
	 * @Author : 최경호
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getDelayedDeliveryVndrList() throws Exception {
		return ocOrderDao.selectDelayedDeliveryVndrList();
	}

	/**
	 * @Desc : 구매확정 가능여부 체크
	 * @Method Name : orderStatActionValue
	 * @Date : 2020. 1. 15.
	 * @Author : 이강수
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public String orderStatActionValue(String orderNo) throws Exception {

		if (orderNo != null && !orderNo.isEmpty()) {
			OcOrder ocOrder = new OcOrder();
			// 주문번호 별 Count체크
			ocOrder.setOrderNo(orderNo);
			OrderStatVO orderStatParam = ocOrderDao.selectOrderStatCount(ocOrder);

			// 2020.02.06 : 기존 쿼리에서 조회 원주문번호로 클레임상품 따로 조회
			OcClaimProduct paramClmPrdt = new OcClaimProduct();
			paramClmPrdt.setOrgOrderNo(orderNo);
			List<OcClaimProduct> clmPrdtListByOrgOrderNo = ocClaimProductDao
					.selectClmPrdtListByOrgOrderNo(paramClmPrdt);

			long finishClaimCnt = clmPrdtListByOrgOrderNo.stream()
					.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
							&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY)) // 사은품/배송비 제외
					.filter(x -> UtilsText.equals(x.getClmPrdtStatCode(),
							CommonCode.CLM_PRDT_STAT_CODE_CANCEL_REDEPTION_ACCEPT)
							|| UtilsText.equals(x.getClmPrdtStatCode(),
									CommonCode.CLM_PRDT_STAT_CODE_CANCEL_REFUND_ACCEPT)
							|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_CANCEL_FINISH)
							|| UtilsText.equals(x.getClmPrdtStatCode(),
									CommonCode.CLM_PRDT_STAT_CODE_RETURN_REDEPTION_ACCEPT)
							|| UtilsText.equals(x.getClmPrdtStatCode(),
									CommonCode.CLM_PRDT_STAT_CODE_RETURN_REFUND_ACCEPT)
							|| UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_FINISH))
					.count();

			// 2020.02.06 : 진행중 클레임 개수(사은품/배송비 제외)
			long procClaimCnt = clmPrdtListByOrgOrderNo.stream()
					.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
							&& !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY)) // 사은품/배송비 제외
					.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
							CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT_CANCEL) // 취소접수취소
							&& !UtilsText.equals(x.getClmPrdtStatCode(),
									CommonCode.CLM_PRDT_STAT_CODE_CANCEL_WITHDRAWAL_ACCEPT) // 취소접수철회
							&& !UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_CANCEL_FINISH) // 취소완료
							&& !UtilsText.equals(x.getClmPrdtStatCode(),
									CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL) // 교환접수취소
							&& !UtilsText.equals(x.getClmPrdtStatCode(),
									CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT) // 교환접수철회
							&& !UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_FINISH) // 교환완료
							&& !UtilsText.equals(x.getClmPrdtStatCode(),
									CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE) // 교환불가
							&& !UtilsText.equals(x.getClmPrdtStatCode(),
									CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL) // 반품접수취소
							&& !UtilsText.equals(x.getClmPrdtStatCode(),
									CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT) // 반품접수철회
							&& !UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_FINISH) // 반품완료
							&& !UtilsText.equals(x.getClmPrdtStatCode(),
									CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE) // 반품불가
					).count();

			// 2020.02.06 : 주문 상품 카운트 따로 분리
			int orderTotalCnt = orderStatParam.getOrderTotalCnt(); // 전체 주문상품수
			int orderConfirmCnt = orderStatParam.getOrderConfirmCnt(); // 전체 배송완료 건수
			int orderCancelCnt = orderStatParam.getOrderCancelCnt(); // 전체 취소 건수

			// 2020.02.06 : 클레임 상품 카운트 따로 분리
			int orderClaimCnt = 0;
			orderClaimCnt = (int) finishClaimCnt; // 취소/반품 클레임 완료 상품 건수
			int orderClaimIngCnt = 0;
			orderClaimIngCnt = (int) procClaimCnt; // 취소/반품/교환 클레임 진행 상품 건수

			log.debug("///////////////////// orderClaimCnt : " + orderClaimCnt);
			log.debug("///////////////////// orderClaimIngCnt : " + orderClaimIngCnt);

			// 1. 총주문상품건수 - (배송완료건수 + 주문취소건수) = 0
			// 2. 진행중 클레임상품건수 = 0
			// 3. 배송완료건수 > 0
			// 2020.02.06 : 총주문상품개수 - 완료된 취소/반품클레임 상품 건수 > 0
			if (orderTotalCnt - (orderConfirmCnt + orderCancelCnt) == 0 && orderClaimIngCnt == 0 && orderConfirmCnt > 0
					&& orderTotalCnt - orderClaimCnt > 0) {
				return Const.BOOLEAN_TRUE; // 구매확정 가능
			}

		}
		return Const.BOOLEAN_FALSE;
	}

	/**
	 * @Desc : 주문번호로 주문 마스터테이블 정보 조회
	 * @Method Name : getOrderMasterInfo
	 * @Date : 2020. 1. 20.
	 * @Author : 이강수
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public OcOrder getOrderMasterInfo(String orderNo) throws Exception {
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(orderNo);
		return ocOrderDao.selectByPrimaryKey(ocOrder);
	}

	/**
	 * @Desc : 주결제수단 조회
	 * @Method Name : getMainPayment
	 * @Date : 2020. 1. 20.
	 * @Author : 이강수
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	public OcOrderPayment getMainPayment(String orderNo) throws Exception {
		List<OcOrderPayment> pymntList = this.selectPaymentList(orderNo);
		// 결제목록에서 먼저 주결제 수단인것만 찾고
		pymntList = pymntList.stream().filter(x -> UtilsText.equals(x.getMainPymntMeansYn(), Const.BOOLEAN_TRUE))
				.collect(Collectors.toList());
		// 1. 결제수단이 변경된적이 없는 주결제 수단이거나
		// 2. 관리자가 결제수단변경을 승인했으나 아직 주문자가 결제수단을 변경하지 않은 주결제
		OcOrderPayment mainPayment = pymntList.stream()
				.filter(x -> UtilsText.equals(x.getPymntMeansChngPsbltYn(), Const.BOOLEAN_FALSE)
						|| (UtilsText.equals(x.getPymntMeansChngPsbltYn(), Const.BOOLEAN_TRUE)
								&& UtilsText.equals(x.getPymntStatCode(), CommonCode.PYMNT_STAT_CODE_PAYMENT_FINISH)))
				.findFirst().orElse(null);

		return mainPayment;
	}

	/**
	 * @Desc : 미수령기간 갱신 프로시져 호출
	 * @Method Name : callProcedureForPickupPsbltDt
	 * @Date : 2020. 3. 17.
	 * @Author : sic
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> callProcedureForPickupPsbltDt(Map<String, String> map) throws Exception {
		ocOrderDao.callProcedureForPickupPsbltDt(map);
		return map;
	}
	
	/**
	 * @Desc      	: 주문상품 상태 조회
	 * @Method Name : getOrderProducts
	 * @Date  	  	: 2020. 5. 25.
	 * @Author    	: 3top
	 * @param ocOrder
	 * @return
	 * @throws Exception
	 */
	public List<OcOrderProduct> getOrderProducts(OcOrder ocOrder) throws Exception {
		return ocOrderProductDao.selectOrderProducts(ocOrder);
	}

}
