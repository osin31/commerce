package kr.co.abcmart.bo.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.interfaces.module.payment.PaymentEntrance;
import kr.co.abcmart.interfaces.module.payment.base.PaymentResult;
import kr.co.abcmart.interfaces.module.payment.base.model.PaymentInfo;
import kr.co.abcmart.interfaces.module.payment.kakao.KakaoPaymentService;
import kr.co.abcmart.interfaces.module.payment.kakao.model.KakaoPaymentApproval;
import kr.co.abcmart.interfaces.module.payment.kakao.model.KakaoPaymentApprovalReturn;
import kr.co.abcmart.interfaces.module.payment.kakao.model.KakaoPaymentAuthority;
import kr.co.abcmart.interfaces.module.payment.kakao.model.KakaoPaymentAuthorityReturn;
import kr.co.abcmart.interfaces.module.payment.kakao.model.KakaoPaymentCancel;
import kr.co.abcmart.interfaces.module.payment.kakao.model.KakaoPaymentCancelReturn;
import kr.co.abcmart.interfaces.module.payment.kakao.model.KakaoPaymentOrder;
import kr.co.abcmart.interfaces.module.payment.kakao.model.KakaoPaymentOrderList;
import kr.co.abcmart.interfaces.module.payment.kakao.model.KakaoPaymentOrderListReturn;
import kr.co.abcmart.interfaces.module.payment.kakao.model.KakaoPaymentOrderReturn;
import kr.co.abcmart.interfaces.module.payment.kakao.model.KakaoPaymentOrderReturn.KakaoCardInfo;
import kr.co.abcmart.interfaces.module.payment.kakao.model.KakaoPaymentOrderReturn.KakaoPayDetailList;
import kr.co.abcmart.util.UtilsRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderTestService {

	@Autowired
	private KakaoPaymentService kakaoPaymentService;

	@Autowired
	private PaymentEntrance paymentEntrance;

	/**
	 * @Desc :
	 * @Method Name : authority
	 * @Date : 2019. 4. 1.
	 * @Author : flychani@3top.co.kr
	 * @return
	 */
	public Map<String, Object> authority() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		KakaoPaymentAuthority authority = new KakaoPaymentAuthority();
		authority.setCid("TC0ONETIME");
		authority.setPartnerOrderId("20190401000001");
		authority.setPartnerUserId("memberNo001");
		authority.setItemName("상품명");
		authority.setQuantity(10);
		authority.setTotalAmount(1000);
		authority.setTaxFreeAmount(1000);
		authority.setApprovalUrl("https://localhost:8800/orderTest/kakao-approve");
		authority.setCancelUrl("https://localhost:8800/orderTest/kakao-cancel");
		authority.setFailUrl("https://localhost:8800/orderTest/kakao-fail");

		PaymentResult result = paymentEntrance.authority(new PaymentInfo("KAKAO", authority));

		System.out.println(" ================================= paymentResult " + result.getData());
		KakaoPaymentAuthorityReturn reponse = (KakaoPaymentAuthorityReturn) result.getData();
		System.out.println(" ================================= reponse " + reponse.getNextRedirectPcUrl());

		resultMap.put("nextRedirectPcUrl", reponse.getNextRedirectPcUrl());
		resultMap.put("nextRedirectMobileUrl", reponse.getNextRedirectMobileUrl());
		resultMap.put("nextRedirectAppUrl", reponse.getNextRedirectAppUrl());
		resultMap.put("tid", reponse.getTid());
		resultMap.put("nextRedirectPcUrl", reponse.getNextRedirectPcUrl());

		UtilsRequest.getSession().setAttribute("tid", reponse.getTid());

		return resultMap;
	}

	/**
	 * @param parameter
	 * @Desc :
	 * @Method Name : kakaoApprove
	 * @Date : 2019. 4. 1.
	 * @Author : flychani@3top.co.kr
	 * @return
	 */
	public Map<String, Object> kakaoApprove(Parameter<?> parameter) throws Exception {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String tid = (String) UtilsRequest.getSession().getAttribute("tid");
		String pgToken = parameter.getString("pg_token");
		System.out.println("tid>>>>>>>>>>>>>>>>>>>>> " + tid);

		KakaoPaymentApproval approval = new KakaoPaymentApproval();
		approval.setCid("C0ONETIME");
		approval.setTid(tid);
		approval.setPartnerOrderId("20190401000001");
		approval.setPartnerUserId("memberNo001");
		approval.setPgToken(pgToken);

		PaymentResult result = paymentEntrance.approval(new PaymentInfo("KAKAO", approval));

		KakaoPaymentApprovalReturn reponse = (KakaoPaymentApprovalReturn) result.getData();

		resultMap.put("aid", reponse.getAid());
		resultMap.put("tid", reponse.getTid());
		resultMap.put("cid", reponse.getCid());
		resultMap.put("sid", reponse.getSid());
		resultMap.put("partner_order_id", reponse.getPartnerOrderId());
		resultMap.put("partner_user_id", reponse.getPartnerUserId());
		resultMap.put("payment_method_type", reponse.getPaymentMethodType());
		resultMap.put("amount", reponse.getAmount());
		resultMap.put("card_info", reponse.getCard_info());
		resultMap.put("item_name", reponse.getItemName());
		resultMap.put("item_code", reponse.getItemCode());
		resultMap.put("quantity", reponse.getQuantity());
		resultMap.put("created_at", reponse.getCreatedAt());
		resultMap.put("approved_at", reponse.getApprovedAt());
		resultMap.put("payload", reponse.getPayload());
		return resultMap;
	}

	/**
	 * @Desc :
	 * @Method Name : kakaoCancel
	 * @Date : 2019. 4. 1.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @return
	 */
	public Map<String, Object> kakaoCancel(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String tid = parameter.getString("tid");
		String pgToken = parameter.getString("pg_token");
		System.out.println("tid>>>>>>>>>>>>>>>>>>>>> " + tid);

		KakaoPaymentCancel cancel = new KakaoPaymentCancel();
		cancel.setCid("C0ONETIME");
		cancel.setTid(tid);
		cancel.setCancelAmount(1000);
		cancel.setCancelTaxFreeAmount(1000);

		PaymentResult result = paymentEntrance.cancel(new PaymentInfo("KAKAO", cancel));

		KakaoPaymentCancelReturn reponse = (KakaoPaymentCancelReturn) result.getData();

		resultMap.put("aid", reponse.getAid());
		resultMap.put("tid", reponse.getTid());
		resultMap.put("cid", reponse.getCid());
		resultMap.put("status", reponse.getStatus());
		resultMap.put("partner_order_id", reponse.getPartnerOrderId());
		resultMap.put("partner_user_id", reponse.getPartnerUserId());
		resultMap.put("payment_method_type", reponse.getPaymentMethodType());
		resultMap.put("amount", reponse.getAmount());
		resultMap.put("canceled_amount", reponse.getCanceledAmount());
		resultMap.put("cancel_available_amount", reponse.getCancelAvailableAmount());
		resultMap.put("item_name", reponse.getItemName());
		resultMap.put("item_code", reponse.getItemCode());
		resultMap.put("quantity", reponse.getQuantity());
		resultMap.put("created_at", reponse.getCreatedAt());
		resultMap.put("approved_at", reponse.getApprovedAt());
		resultMap.put("canceled_at", reponse.getCanceledAt());
		resultMap.put("payload", reponse.getPayload());

		return resultMap;
	}

	/**
	 * @Desc :
	 * @Method Name : kakaoOrder
	 * @Date : 2019. 4. 1.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @return
	 */
	public Map<String, Object> kakaoOrder(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String tid = parameter.getString("tid");
		String pgToken = parameter.getString("pg_token");
		System.out.println("tid>>>>>>>>>>>>>>>>>>>>> " + tid);

		KakaoPaymentOrder order = new KakaoPaymentOrder();
		order.setCid("C0ONETIME");
		order.setTid(tid);

		PaymentResult result = kakaoPaymentService.orderDetail(new PaymentInfo("KAKAO", order));

		KakaoPaymentOrderReturn reponse = (KakaoPaymentOrderReturn) result.getData();

		resultMap.put("tid", reponse.getTid());
		resultMap.put("cid", reponse.getCid());
		resultMap.put("status", reponse.getStatus());
		resultMap.put("partner_order_id", reponse.getPartnerOrderId());
		resultMap.put("partner_user_id", reponse.getPartnerUserId());
		resultMap.put("payment_method_type", reponse.getPaymentMethodType());
		resultMap.put("amount", reponse.getAmount());
		resultMap.put("canceled_amount", reponse.getCanceledAmount());
		resultMap.put("cancel_available_amount", reponse.getCancelAvailableAmount());
		resultMap.put("item_name", reponse.getItemName());
		resultMap.put("item_code", reponse.getItemCode());
		resultMap.put("quantity", reponse.getQuantity());
		resultMap.put("created_at", reponse.getCreatedAt());
		resultMap.put("approved_at", reponse.getApprovedAt());
		resultMap.put("canceled_at", reponse.getCanceledAt());
		resultMap.put("selected_card_info", reponse.getSelectedCardInfo());
		resultMap.put("payment_action_details", reponse.getPaymentActionDetails());

		KakaoCardInfo cardInfo = reponse.getSelectedCardInfo();

		cardInfo.getCardBin();

		List<KakaoPayDetailList> saleOrderInfoList = reponse.getPaymentActionDetails();
		for (int i = 0, lCnt = saleOrderInfoList.size(); i < lCnt; i++) {
			System.out.println("================ aid" + saleOrderInfoList.get(i).getAid()); // 결제 고유번호
			System.out.println("================ getApproved_at" + saleOrderInfoList.get(i).getApproved_at()); // 결제
																												// 고유번호
			System.out.println("================ getAmount" + saleOrderInfoList.get(i).getAmount()); // 결제 고유번호
			System.out.println("================ getPoint_amount" + saleOrderInfoList.get(i).getPoint_amount()); // 결제
																													// 고유번호
			System.out.println("================ getDiscount_amount" + saleOrderInfoList.get(i).getDiscount_amount()); // 결제
																														// 고유번호
			System.out.println(
					"================ getPayment_action_type" + saleOrderInfoList.get(i).getPayment_action_type()); // 결제
																													// 고유번호
			System.out.println("================ payload" + saleOrderInfoList.get(i).getPayload()); // 결제 고유번호
		}

		return resultMap;
	}

	/**
	 * @Desc :
	 * @Method Name : kakaoOrders
	 * @Date : 2019. 4. 1.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 * @return
	 */
	public Map<String, Object> kakaoOrders(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String tid = parameter.getString("tid");
		String pgToken = parameter.getString("pg_token");
		System.out.println("tid>>>>>>>>>>>>>>>>>>>>> " + tid);

		KakaoPaymentOrderList orders = new KakaoPaymentOrderList();
		orders.setCid("C0ONETIME");
		orders.setPaymentRequestDate("20190401");

		PaymentResult result = kakaoPaymentService.orderList(new PaymentInfo("KAKAO", orders));

		KakaoPaymentOrderListReturn reponse = (KakaoPaymentOrderListReturn) result.getData();

		resultMap.put("page", reponse.getPage());
		resultMap.put("payment_request_date", reponse.getPaymentRequestDate());
		resultMap.put("cid", reponse.getCid());
		resultMap.put("orders", reponse.getOrders());

		return resultMap;
	}

}
