package kr.co.abcmart.bo.member.service;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.member.model.master.MbMemberGiftCard;
import kr.co.abcmart.bo.member.repository.master.MbMemberGiftCardDao;
import kr.co.abcmart.bo.order.model.master.OcGiftCardOrder;
import kr.co.abcmart.bo.order.model.master.OcGiftCardOrderPayment;
import kr.co.abcmart.bo.order.repository.master.OcGiftCardInterlockHistoryDao;
import kr.co.abcmart.bo.order.repository.master.OcGiftCardOrderDao;
import kr.co.abcmart.bo.order.repository.master.OcGiftCardOrderPaymentDao;
import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.interfaces.module.payment.PaymentEntrance;
import kr.co.abcmart.interfaces.module.payment.base.model.PaymentInfo;
import kr.co.abcmart.interfaces.module.payment.kcp.model.KcpPaymentCancel;
import kr.co.abcmart.interfaces.module.payment.nice.NiceGiftService;
import kr.co.abcmart.interfaces.module.payment.nice.model.BalanceRequest;
import kr.co.abcmart.interfaces.module.payment.nice.model.BalanceResponse;
import kr.co.abcmart.interfaces.module.payment.nice.model.ChargeRequest;
import kr.co.abcmart.interfaces.module.payment.nice.model.ChargeResponse;
import kr.co.abcmart.interfaces.module.payment.nice.model.CommNiceRes;
import kr.co.abcmart.interfaces.module.payment.nice.model.SaleAgenciesRequest;
import kr.co.abcmart.interfaces.module.payment.nice.model.SaleAgenciesResponse;
import kr.co.abcmart.interfaces.module.payment.nice.model.TranHistoryRequest;
import kr.co.abcmart.interfaces.module.payment.nice.model.TranHistoryResponse;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GiftCardPurchaseService {
	@Autowired
	private MbMemberGiftCardDao mbMemberGiftCardDao;

	@Autowired
	private OcGiftCardOrderDao ocGiftCardOrderDao;

	@Autowired
	private NiceGiftService niceGiftService;

	@Autowired
	private OcGiftCardOrderPaymentDao ocGiftCardOrderPaymentDao;

	@Autowired
	private OcGiftCardInterlockHistoryDao ocGiftCardInterlockHistoryDao;

	@Autowired
	PaymentEntrance payment;

	/**
	 * @Desc : 기프트 카드 목록
	 * @Method Name : getMemberGiftCardList
	 * @Date : 2019. 4. 22.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<OcGiftCardOrder> getMemberGiftCardList(Pageable<MbMemberGiftCard, OcGiftCardOrder> pageable)
			throws Exception {
		int count = ocGiftCardOrderDao.selectMemberGiftCardListCount(pageable);

		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(ocGiftCardOrderDao.selectMemberGiftCardList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 기프트 카드 보유 리스트
	 * @Method Name : getPossesionGiftCard
	 * @Date : 2019. 4. 22.
	 * @Author : lee
	 * @param mbMemberGiftCard
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getPossesionGiftCard(MbMemberGiftCard mbMemberGiftCard) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<MbMemberGiftCard> mbMemberGiftCardData = mbMemberGiftCardDao.selectGiftCardData(mbMemberGiftCard);

		resultMap.put("mbMemberGiftCardData", mbMemberGiftCardData);

		return resultMap;
	}

	/**
	 * @Desc : 기프트카드 잔액조회
	 * @Method Name : getMyGiftCardBalance
	 * @Date : 2019. 5. 3.
	 * @Author : lee
	 * @param balanceRequest
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMyGiftCardBalance(BalanceRequest balanceRequest) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		BalanceRequest balanceParam = new BalanceRequest(balanceRequest.getCardNo());
		CommNiceRes<BalanceResponse> balanceResult = niceGiftService.sendBalance(balanceParam);

		resultMap.put("niceResCode", balanceResult.getResCode());
		resultMap.put("niceResMsg", balanceResult.getResMsg());
		resultMap.put("niceResData", balanceResult.getResData());

		if (!balanceRequest.isHistory()) {
			TranHistoryRequest tranHistoryParam = new TranHistoryRequest(balanceRequest.getCardNo(), 1, "1");
			CommNiceRes<TranHistoryResponse> tranHistoryResult = niceGiftService.sendTranHistory(tranHistoryParam);

			resultMap.put("tranHisResCode", tranHistoryResult.getResCode());
			resultMap.put("tranHisResMsg", tranHistoryResult.getResMsg());
			resultMap.put("tranHisResData", tranHistoryResult.getResData());
		}

		return resultMap;
	}

	/**
	 * @Desc : 기프트카드 결제취소
	 * @Method Name : setGiftCardHistoryCancel
	 * @Date : 2019. 5. 3.
	 * @Author : lee
	 * @param ocGiftCardOrder
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setGiftCardHistoryCancel(OcGiftCardOrder ocGiftCardOrder) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		OcGiftCardOrder giftCardHis = ocGiftCardOrderDao.selectGiftCardHistoryForCancel(ocGiftCardOrder);

		InetAddress ip = InetAddress.getLocalHost();
		String custIp = ip.getHostAddress();

		// 1.kcp 결제 취소
		KcpPaymentCancel kcpPaymentCancel = new KcpPaymentCancel();
		if (UtilsText.equals(giftCardHis.getSiteNo(), Const.SITE_NO_ART)) {
			kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.gift.siteCode"));
			kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.gift.siteKey"));
		} else {
			kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.ots.gift.siteCode"));
			kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.ots.gift.siteKey"));
		}
		kcpPaymentCancel.setTno(giftCardHis.getPymntAprvNoText()); // KCP 원거래 거래번호
		kcpPaymentCancel.setModType(CommonCode.KCP_MOD_TYPE_STSC); // 전체취소 STSC / 부분취소 STPC
		kcpPaymentCancel.setCustIp(custIp); // 변경 요청자 IP
		kcpPaymentCancel.setModDesc("사용자 취소"); // 취소 사유

		// KCP 결제한 금액 취소

		payment.cancel(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, kcpPaymentCancel));

		OcGiftCardOrderPayment updatePaymentParam = new OcGiftCardOrderPayment();
		updatePaymentParam.setGiftCardOrderNo(ocGiftCardOrder.getGiftCardOrderNo());
		updatePaymentParam.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL);
		// OC_GIFT_CARD_ORDER_PAYMENT 테이블 업데이트
		ocGiftCardOrderPaymentDao.updateGiftCardPaymentCancelStat(updatePaymentParam);

		// 2.nice 취소 전문
		String payCode = "00";
		if (UtilsText.equals(giftCardHis.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_CARD)) {
			payCode = "01";
		}
		Date approvalDtm = null;
		if (UtilsText.equals(CommonCode.GIFT_CARD_ORDER_TYPE_CODE_GIFT, giftCardHis.getGiftCardOrderTypeCode())) {
			SaleAgenciesRequest saleAgenciesRequest = new SaleAgenciesRequest(giftCardHis.getPymntAmt(),
					giftCardHis.getPymntAmt(), giftCardHis.getRcvrHdphnNoText(), "", payCode);
			saleAgenciesRequest.setMessageType("0420");
			saleAgenciesRequest.setServiceCode("C2");
			saleAgenciesRequest.setOApprovalNo(giftCardHis.getGiftCardAprvNoText());
			saleAgenciesRequest
					.setOApprovalDate(giftCardHis.getGiftCardAprvDtm().toString().replace("-", "").substring(0, 8));

			CommNiceRes<SaleAgenciesResponse> saleAgenciesCancelResult = niceGiftService
					.sendSaleAgencies(saleAgenciesRequest);

			resultMap.put("niceCancelRes", saleAgenciesCancelResult.getResCode());
			resultMap.put("niceCancelMsg", saleAgenciesCancelResult.getResMsg());
			resultMap.put("nideCancelData", saleAgenciesCancelResult.getResData());

			// 수정 날짜 조합
			String aprvDt = saleAgenciesCancelResult.getResData().getApprovalDate()
					+ saleAgenciesCancelResult.getResData().getApprovalTime();
			log.debug("##### aprvDt: {}", aprvDt);
			String formatedAprvDt = aprvDt.substring(0, 4) + "-" + aprvDt.substring(4, 6) + "-" + aprvDt.substring(6, 8)
					+ " " + aprvDt.substring(8, 10) + ":" + aprvDt.substring(10, 12) + ":" + aprvDt.substring(12, 14);
			log.debug("##### formatedAprvDt: {}", formatedAprvDt);
			SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyy-MM-dd HH:mm:ss");
			approvalDtm = dtFormat.parse(formatedAprvDt);

			MbMemberGiftCard mbMemberGiftCard = new MbMemberGiftCard();
			mbMemberGiftCard.setMemberNo(giftCardHis.getMemberNo());
			mbMemberGiftCard.setCardNoText(giftCardHis.getCardNoText());

			// 나의 기프트카드 등록했을 경우 폐기처분
			if (mbMemberGiftCardDao.selectGiftCardCount(mbMemberGiftCard) >= 1) {
				mbMemberGiftCardDao.updateGiftCardDelYn(mbMemberGiftCard);
			}

		} else if (UtilsText.equals(CommonCode.GIFT_CARD_ORDER_TYPE_CODE_CHARGE,
				giftCardHis.getGiftCardOrderTypeCode())) {
			ChargeRequest chargeRequest = new ChargeRequest(giftCardHis.getCardNoText(), giftCardHis.getPymntAmt(),
					"1");
			chargeRequest.setMessageType("0420");
			chargeRequest.setServiceCode("C3");
			chargeRequest.setOApprovalNo(giftCardHis.getGiftCardAprvNoText());
			log.debug("##### aprvNoText: {}", giftCardHis.getGiftCardAprvNoText());
			chargeRequest
					.setOApprovalDate(giftCardHis.getGiftCardAprvDtm().toString().replace("-", "").substring(0, 8));
			log.debug("##### aprvDtm: {}",
					giftCardHis.getGiftCardAprvDtm().toString().substring(0, 10).replace("-", ""));

			CommNiceRes<ChargeResponse> chargeCancelResult = niceGiftService.sendCharge(chargeRequest);

			resultMap.put("niceCancelRes", chargeCancelResult.getResCode());
			resultMap.put("niceCancelMsg", chargeCancelResult.getResMsg());
			resultMap.put("nideCancelData", chargeCancelResult.getResData());

			// 수정 날짜 조합
			String aprvDt = chargeCancelResult.getResData().getApprovalDate()
					+ chargeCancelResult.getResData().getApprovalTime();
			log.debug("##### aprvDt: {}", aprvDt);
			String formatedAprvDt = aprvDt.substring(0, 4) + "-" + aprvDt.substring(4, 6) + "-" + aprvDt.substring(6, 8)
					+ " " + aprvDt.substring(8, 10) + ":" + aprvDt.substring(10, 12) + ":" + aprvDt.substring(12, 14);
			log.debug("##### formatedAprvDt: {}", formatedAprvDt);
			SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyy-MM-dd HH:mm:ss");
			approvalDtm = dtFormat.parse(formatedAprvDt);
		}

		// OC_GIFT_CARD_ORDER 테이블 업데이트
		OcGiftCardOrder updateOrderParam = new OcGiftCardOrder();
		updateOrderParam.setGiftCardOrderNo(ocGiftCardOrder.getGiftCardOrderNo());
		updateOrderParam.setOrderStatCode(CommonCode.ORDER_STAT_CODE_CANCEL_COMPLETE);
		updateOrderParam.setModDtm(new Timestamp(approvalDtm.getTime()));
		updateOrderParam.setModerNo(LoginManager.getUserDetails().getAdminNo());

		ocGiftCardOrderDao.updateGiftCardOrderCancelStat(updateOrderParam);

		return resultMap;
	}

	/**
	 * @Desc : 기프트카드 구매시 MMS 재전송
	 * @Method Name : setGiftCardMmsResend
	 * @Date : 2019. 7. 25.
	 * @Author : lee
	 * @param ocGiftCardOrder
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setGiftCardMmsResend(OcGiftCardOrder ocGiftCardOrder) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		OcGiftCardOrder giftCardHis = ocGiftCardOrderDao.selectGiftCardHistoryForCancel(ocGiftCardOrder);

		String payCode = "00";
		if (UtilsText.equals(giftCardHis.getPymntMeansCode(), CommonCode.PYMNT_MEANS_CODE_CARD)) {
			payCode = "01";
		}

		SaleAgenciesRequest saleAgenciesRequest = new SaleAgenciesRequest(giftCardHis.getPymntAmt(),
				giftCardHis.getPymntAmt(), giftCardHis.getRcvrHdphnNoText(), "", payCode);
		saleAgenciesRequest.setMessageType("0200");
		saleAgenciesRequest.setServiceCode("V5");
		saleAgenciesRequest.setOApprovalNo(giftCardHis.getGiftCardAprvNoText());
		saleAgenciesRequest
				.setOApprovalDate(giftCardHis.getGiftCardAprvDtm().toString().replace("-", "").substring(0, 8));
		// 추가정보 필드에 상품권번호 넘김
		saleAgenciesRequest.setCompanyInfo(giftCardHis.getCardNoText() + giftCardHis.getGiftCardOrderNo()); 
																											

		log.debug("##### saleAgenciesRequest: {}", saleAgenciesRequest);

		CommNiceRes<SaleAgenciesResponse> saleAgenciesMmsResendResult = niceGiftService
				.sendSaleAgencies(saleAgenciesRequest);

		resultMap.put("niceResultCode", saleAgenciesMmsResendResult.getResCode());
		resultMap.put("niceResultMsg", saleAgenciesMmsResendResult.getResMsg());
		resultMap.put("nideResultData", saleAgenciesMmsResendResult.getResData());

		if (UtilsText.equals("0000", saleAgenciesMmsResendResult.getResCode())) {
			ocGiftCardOrderDao.updateGiftCardMmsResend(ocGiftCardOrder);
			resultMap.put("niceResultCode", "0000");
		} else {
			resultMap.put("niceResultCode", "9999");
		}
		return resultMap;
	}

}
