package kr.co.abcmart.bo.claim.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.claim.model.master.OcClaim;
import kr.co.abcmart.bo.claim.model.master.OcClaimPayment;
import kr.co.abcmart.bo.claim.model.master.OcClaimProduct;
import kr.co.abcmart.bo.claim.repository.master.OcClaimDao;
import kr.co.abcmart.bo.claim.repository.master.OcClaimPaymentDao;
import kr.co.abcmart.bo.claim.repository.master.OcClaimProductDao;
import kr.co.abcmart.bo.cmm.service.MailService;
import kr.co.abcmart.bo.cmm.service.MessageService;
import kr.co.abcmart.bo.cmm.vo.MailTemplateVO;
import kr.co.abcmart.bo.cmm.vo.MessageVO;
import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.bo.order.repository.master.OcOrderDao;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.util.UtilsArray;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.constant.MailCode;
import kr.co.abcmart.constant.MessageCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 클레임 메세지발송 서비스
 * @FileName : ClaimMessageService.java
 * @Project : abc.bo
 * @Date : 2019. 6. 13.
 * @Author : 이강수
 */
@Slf4j
@Service
public class ClaimMessageService extends BaseController {

	@Autowired
	private OcOrderDao ocOrderDao;

	@Autowired
	private OcClaimDao ocClaimDao;

	@Autowired
	private OcClaimPaymentDao ocClaimPaymentDao;

	@Autowired
	private MessageService messageService;

	@Autowired
	private MailService mailService;

	@Autowired
	private OcClaimProductDao ocClaimProductDao;

	// 취소 상세 URL
	private static final String CANCEL_DETAIL_URL = "/mypage/claim/cancel-claim-detail?clmNo=";
	// 교환 상세 URL
	private static final String EXCHANGE_DETAIL_URL = "/mypage/claim/exchange-claim-detail?clmNo=";
	// 반품 상세 URL
	private static final String RETURN_DETAIL_URL = "/mypage/claim/return-claim-detail?clmNo=";

	private static final String NO_IMAGE_URL = UtilsText.concat(Const.SERVICE_DOMAIN_ART_FO,
			"/static/images/common/no_image.png");

	private static final String SENDER_TITLE_ABC = "[A-RT]에서 보내드립니다.";
	private static final String SENDER_TITLE_OTS = "[ON THE SPOT]에서 보내드립니다.";

	private static final String ABC_URL = "abcUrl";
	private static final String OTS_URL = "otsUrl";

	private static final String CMM_DATE_FORM_HM = "yyyy년 MM월 dd일 HH시 mm분";

	/**
	 * @Desc : 메세지발송 - 주문 취소
	 * @Method Name : orderAllCancel
	 * @Date : 2019. 6. 13.
	 * @Author : 이강수
	 * @param OcClaimMessageVO
	 * @throws Exception
	 */
	public void orderAllCancel(OcClaim ocClaim) throws Exception {

		Map<String, String> map = new HashMap<>();
		Map<String, Object> mailTempMap = new HashMap<>();

		// 이메일 상단 URL
		mailTempMap.put(ABC_URL, Const.SERVICE_DOMAIN_ART_FO);
		mailTempMap.put(OTS_URL, Const.SERVICE_DOMAIN_OTS_FO);

		/** 클레임 상세조회 */
		Map<String, Object> claimMap = this.getClaimDetailInfo(ocClaim);
		// 클레임 마스터
		OcClaim detail = (OcClaim) claimMap.get("detail");
		// 클레임 상품 리스트(자사/업체 구분 없는 모든 클레임 상품)
		List<OcClaimProduct> prdtList = (List<OcClaimProduct>) claimMap.get("prdtList");
		// 취소 클레임 - 환수배송비 환불대상 히스토리 목록
		List<OcClaimPayment> refundPreviousRedempDlvyList = (List<OcClaimPayment>) claimMap
				.get("refundPreviousRedempDlvyList");
		// 다족구매 프로모션 추가 환불대상 히스토리 목록
		List<OcClaimPayment> addMultiBuyDifferPaymentList = (List<OcClaimPayment>) claimMap
				.get("addMultiBuyDifferPaymentList");
		// 클레임 환불 영역 정보
		List<OcClaimPayment> refundPymntAmtList = (List<OcClaimPayment>) claimMap.get("refundPymntAmtList");

		// 클레임 상품 금액 sum
		int claimPrdtSumAmt = 0;
		claimPrdtSumAmt = (int) claimMap.get("claimPrdtSumAmt");
		// 클레임 주문배송비 금액 sum
		int claimDlvySumAmt = 0;
		claimDlvySumAmt = (int) claimMap.get("claimDlvySumAmt");

		// 취소 클레임 - 환수배송비 환불대상 히스토리 sum
		int refundPreviousRedempDlvyAmtTotal = 0;
		if (!UtilsArray.isEmpty(refundPreviousRedempDlvyList)) {
			refundPreviousRedempDlvyAmtTotal = refundPreviousRedempDlvyList.stream().mapToInt(x -> x.getPymntAmt())
					.sum();
		}
		// 다족구매 프로모션 추가 환불대상 히스토리 sum
		int addMultiBuyDifferAmtTotal = 0;
		if (!UtilsArray.isEmpty(addMultiBuyDifferPaymentList)) {
			addMultiBuyDifferAmtTotal = addMultiBuyDifferPaymentList.stream().mapToInt(x -> x.getPymntAmt()).sum();
		}

		DecimalFormat formatter = new DecimalFormat(Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT);

		int intCancelAmt = 0;
		String pymntMeansName = "";
		String cancelAmt = "";

		if (!UtilsArray.isEmpty(refundPymntAmtList)) {

			for (int i = 0; i < refundPymntAmtList.size(); i++) {

				/** set pymntMeansName */
				if (i < refundPymntAmtList.size() - 1) {
					pymntMeansName += refundPymntAmtList.get(i).getPymntMeansCodeName() + ", ";
				} else {
					pymntMeansName += refundPymntAmtList.get(i).getPymntMeansCodeName();
				}

				/** set cancelAmt */
				intCancelAmt += refundPymntAmtList.get(i).getPymntAmt();
			}

			// String에 ,까지 찍고 '원' 붙인 취소금액
			cancelAmt = UtilsText.concat(formatter.format(intCancelAmt), "원");
		}

		/**
		 * SMS알림톡 전송 & 메일 발송 set
		 */
		MessageVO messageVO = new MessageVO();
		MailTemplateVO mailTempVO = new MailTemplateVO();

		this.setClmInfoMesgMail(messageVO, mailTempVO, map, mailTempMap, detail);

		// 취소상품 정보 리스트 - mail
		mailTempMap.put("prdtList", prdtList);
		// 메일
		long mailTempCancelAmt = 0;
		mailTempCancelAmt = claimPrdtSumAmt + claimDlvySumAmt + refundPreviousRedempDlvyAmtTotal
				+ addMultiBuyDifferAmtTotal;
		mailTempMap.put("cancelAmt", formatter.format(mailTempCancelAmt));
		// 결제방식명 - 알림톡/SMS
		if (UtilsObject.isEmpty(pymntMeansName)) {
			map.put("pymntMeansName", "무통장");
			// 메세지 : 취소 금액
			map.put("cancelAmt", UtilsText.concat(formatter.format(mailTempCancelAmt), "원"));
		} else {
			map.put("pymntMeansName", pymntMeansName);
			// 메세지 : 취소 금액
			map.put("cancelAmt", cancelAmt);
		}
		// 추가 배송비 - mail
		mailTempMap.put("totalRedempAmt", formatter.format(claimMap.get("redempPymntAmtSum")));
		// 취소 리스트 - mail
		mailTempMap.put("refundPymntAmtList", refundPymntAmtList);
		// 총 환불금액 - mail
		mailTempMap.put("totalRfndAmt", formatter.format(claimMap.get("refundPymntAmtSum")));

		// 랜딩 URL
		String landingUrl = "";

		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {
			// 메세지 번호
			messageVO.setMesgId(MessageCode.MESG_ID_ORDER_ALL_CANCEL_ART);
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_ORDER_CANCEL_ART);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, CANCEL_DETAIL_URL, detail.getClmNo());
			map.put("landingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_FO, CANCEL_DETAIL_URL, detail.getClmNo());
			mailTempMap.put("landingUrl", landingUrl);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {
			// 메세지 번호 2020.05.28 OTSA132  으로 수정
			messageVO.setMesgId("OTSA132");
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_ORDER_CANCEL_OTS);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, CANCEL_DETAIL_URL, detail.getClmNo());
			map.put("landingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_FO, CANCEL_DETAIL_URL, detail.getClmNo());
			mailTempMap.put("landingUrl", landingUrl);
		}

		// 알림톡/SMS 발송
		messageVO.setMessageTemplateModel(map);
		try {
			messageService.setSendMessageProcess(messageVO);
		} catch (Exception e) {
			log.error("주문전체취소 메세지 발송 실패 : " + e.getMessage());
		}

		// 메일 발송
		mailTempVO.setMailTemplateModel(mailTempMap);
		try {
			mailService.sendMail(mailTempVO);
		} catch (Exception e) {
			log.error("주문전체취소 메일 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 메세지발송 - 주문 부분 취소
	 * @Method Name : orderPartCancel
	 * @Date : 2019. 6. 13.
	 * @Author : 이강수
	 * @param OcClaimMessageVO
	 * @throws Exception
	 */
	public void orderPartCancel(OcClaim ocClaim) throws Exception {

		Map<String, String> map = new HashMap<>();
		Map<String, Object> mailTempMap = new HashMap<>();

		// 이메일 상단 URL
		mailTempMap.put(ABC_URL, Const.SERVICE_DOMAIN_ART_FO);
		mailTempMap.put(OTS_URL, Const.SERVICE_DOMAIN_OTS_FO);

		/** 클레임 상세조회 */
		Map<String, Object> claimMap = this.getClaimDetailInfo(ocClaim);
		// 클레임 마스터
		OcClaim detail = (OcClaim) claimMap.get("detail");
		// 클레임 상품 리스트(자사/업체 구분 없는 모든 클레임 상품)
		List<OcClaimProduct> prdtList = (List<OcClaimProduct>) claimMap.get("prdtList");
		// 취소 클레임 - 환수배송비 환불대상 히스토리 목록
		List<OcClaimPayment> refundPreviousRedempDlvyList = (List<OcClaimPayment>) claimMap
				.get("refundPreviousRedempDlvyList");
		// 다족구매 프로모션 추가 환불대상 히스토리 목록
		List<OcClaimPayment> addMultiBuyDifferPaymentList = (List<OcClaimPayment>) claimMap
				.get("addMultiBuyDifferPaymentList");
		// 클레임 환불 영역 정보
		List<OcClaimPayment> refundPymntAmtList = (List<OcClaimPayment>) claimMap.get("refundPymntAmtList");

		// 클레임 상품 금액 sum
		int claimPrdtSumAmt = 0;
		claimPrdtSumAmt = (int) claimMap.get("claimPrdtSumAmt");
		// 클레임 주문배송비 금액 sum
		int claimDlvySumAmt = 0;
		claimDlvySumAmt = (int) claimMap.get("claimDlvySumAmt");

		// 취소 클레임 - 환수배송비 환불대상 히스토리 sum
		int refundPreviousRedempDlvyAmtTotal = 0;
		if (!UtilsArray.isEmpty(refundPreviousRedempDlvyList)) {
			refundPreviousRedempDlvyAmtTotal = refundPreviousRedempDlvyList.stream().mapToInt(x -> x.getPymntAmt())
					.sum();
		}
		// 다족구매 프로모션 추가 환불대상 히스토리 sum
		int addMultiBuyDifferAmtTotal = 0;
		if (!UtilsArray.isEmpty(addMultiBuyDifferPaymentList)) {
			addMultiBuyDifferAmtTotal = addMultiBuyDifferPaymentList.stream().mapToInt(x -> x.getPymntAmt()).sum();
		}

		DecimalFormat formatter = new DecimalFormat(Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT);

		int intCancelAmt = 0;
		String pymntMeansName = "";
		String cancelAmt = "";

		if (!UtilsArray.isEmpty(refundPymntAmtList)) {

			for (int i = 0; i < refundPymntAmtList.size(); i++) {

				/** set pymntMeansName */
				if (i < refundPymntAmtList.size() - 1) {
					pymntMeansName += refundPymntAmtList.get(i).getPymntMeansCodeName() + ", ";
				} else {
					pymntMeansName += refundPymntAmtList.get(i).getPymntMeansCodeName();
				}

				/** set cancelAmt */
				intCancelAmt += refundPymntAmtList.get(i).getPymntAmt();
			}

			// String에 ,까지 찍고 '원' 붙인 취소금액
			cancelAmt = UtilsText.concat(formatter.format(intCancelAmt), "원");
		}

		/**
		 * SMS알림톡 전송 & 메일 발송 set
		 */
		MessageVO messageVO = new MessageVO();
		MailTemplateVO mailTempVO = new MailTemplateVO();

		this.setClmInfoMesgMail(messageVO, mailTempVO, map, mailTempMap, detail);

		// 취소상품 정보 리스트 - mail
		mailTempMap.put("prdtList", prdtList);
		// 메일
		long mailTempCancelAmt = 0;
		mailTempCancelAmt = claimPrdtSumAmt + claimDlvySumAmt + refundPreviousRedempDlvyAmtTotal
				+ addMultiBuyDifferAmtTotal;
		mailTempMap.put("cancelAmt", formatter.format(mailTempCancelAmt));
		// 결제방식명 - 알림톡/SMS
		if (UtilsObject.isEmpty(pymntMeansName)) {
			map.put("pymntMeansName", "무통장");
			// 메세지 : 취소 금액
			map.put("todoCancelAmt", UtilsText.concat(formatter.format(mailTempCancelAmt), "원"));
		} else {
			map.put("pymntMeansName", pymntMeansName);
			// 메세지 : 취소 금액
			map.put("todoCancelAmt", cancelAmt);
		}
		// 추가 배송비 - mail
		mailTempMap.put("totalRedempAmt", formatter.format(claimMap.get("redempPymntAmtSum")));
		// 취소 리스트 - mail
		mailTempMap.put("refundPymntAmtList", refundPymntAmtList);
		// 총 환불금액 - mail
		mailTempMap.put("totalRfndAmt", formatter.format(claimMap.get("refundPymntAmtSum")));

		// 랜딩 URL
		String landingUrl = "";

		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {
			// 메세지 번호
			messageVO.setMesgId(MessageCode.MESG_ID_ORDER_PART_CANCEL_ART);
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_ORDER_CANCEL_ART);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, CANCEL_DETAIL_URL, detail.getClmNo());
			map.put("landingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_FO, CANCEL_DETAIL_URL, detail.getClmNo());
			mailTempMap.put("landingUrl", landingUrl);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {
			// 메세지 번호 2020.05.28 OTSA133  으로 수정
			messageVO.setMesgId("OTSA133");
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_ORDER_CANCEL_OTS);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, CANCEL_DETAIL_URL, detail.getClmNo());
			map.put("landingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_FO, CANCEL_DETAIL_URL, detail.getClmNo());
			mailTempMap.put("landingUrl", landingUrl);
		}

		// 알림톡/SMS 발송
		messageVO.setMessageTemplateModel(map);
		try {
			messageService.setSendMessageProcess(messageVO);
		} catch (Exception e) {
			log.error("주문부분취소 메세지 발송 실패 : " + e.getMessage());
		}

		// 메일 발송
		mailTempVO.setMailTemplateModel(mailTempMap);
		try {
			mailService.sendMail(mailTempVO);
		} catch (Exception e) {
			log.error("주문부분취소 메일 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 메세지발송 - 교환 접수(자사, 입점 상품 공통) - BO접수
	 * @Method Name : exchangeClaimAcceptByAdmin
	 * @Date : 2019. 6. 13.
	 * @Author : 이강수
	 * @param OcClaimMessageVO
	 * @throws Exception
	 */
	public void exchangeClaimAcceptByAdmin(OcClaim ocClaim) throws Exception {

		Map<String, String> map = new HashMap<>();
		Map<String, Object> mailTempMap = new HashMap<>();

		// 이메일 상단 URL
		mailTempMap.put(ABC_URL, Const.SERVICE_DOMAIN_ART_FO);
		mailTempMap.put(OTS_URL, Const.SERVICE_DOMAIN_OTS_FO);

		/** 클레임 상세조회 */
		Map<String, Object> claimMap = this.getClaimDetailInfo(ocClaim);
		// 클레임 마스터
		OcClaim detail = (OcClaim) claimMap.get("detail");
		// 클레임 상품 리스트(자사/업체 구분 없는 모든 클레임 상품)
		List<OcClaimProduct> prdtList = (List<OcClaimProduct>) claimMap.get("prdtList");
		// 교환/반품 배송비 결제 정보
		List<OcClaimPayment> dlvyAmtPymntList = (List<OcClaimPayment>) claimMap.get("dlvyAmtPymntList");

		DecimalFormat formatter = new DecimalFormat(Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT);

		/**
		 * SMS알림톡 전송 & 메일 발송 set
		 */
		MessageVO messageVO = new MessageVO();
		MailTemplateVO mailTempVO = new MailTemplateVO();

		this.setClmInfoMesgMail(messageVO, mailTempVO, map, mailTempMap, detail);

		// 추가배송비결제타입
		mailTempMap.put("addDlvyAmtPymntType", detail.getAddDlvyAmtPymntType());

		if (detail.getAddDlvyAmtPymntType().equals(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT)) {
			// 선결제
			if (!UtilsArray.isEmpty(dlvyAmtPymntList)) {
				// 교환배송비 결제방식명
				map.put("dlvyPymntMeansName", dlvyAmtPymntList.get(0).getPymntMeansCodeName());
				mailTempMap.put("dlvyPymntMeansName", dlvyAmtPymntList.get(0).getPymntMeansCodeName());
				// 교환배송비 결제금액
				map.put("dlvyPymntAmt", UtilsText.concat(formatter.format(dlvyAmtPymntList.get(0).getPymntAmt()), "원"));
				mailTempMap.put("dlvyPymntAmt",
						UtilsText.concat(formatter.format(dlvyAmtPymntList.get(0).getPymntAmt()), "원"));

				OcClaimPayment dlvyAmtPymnt = dlvyAmtPymntList.get(0);

				if (UtilsObject.isNotEmpty(dlvyAmtPymnt)) {
					if (dlvyAmtPymnt.getPymntMeansCode().equals(CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT)) {
						// 2020.03.18 : 가상계좌 발급시 은행명 추가
						map.put("acntNoText",
								UtilsText.concat(dlvyAmtPymnt.getBankCodeName(), " ", dlvyAmtPymnt.getAcntNoText()));
						map.put("acntExprDtm",
								UtilsDate.formatter(CMM_DATE_FORM_HM, dlvyAmtPymnt.getVrtlAcntExprDtm()));
						map.put("acntHldrName", "(예금주 : " + dlvyAmtPymnt.getAcntHldrName() + ")");
					}

					dlvyAmtPymnt.setStrPymntDtm(UtilsDate.formatter(CMM_DATE_FORM_HM, dlvyAmtPymnt.getPymntDtm()));
					dlvyAmtPymnt.setCardMask(dlvyAmtPymnt.getPymntOrganNoText());
				}

				// 교환배송비 모델
				mailTempMap.put("dlvyAmtPymnt", dlvyAmtPymnt);
			}

		} else if (detail.getAddDlvyAmtPymntType().equals(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON)) {
			return;
		} else if (detail.getAddDlvyAmtPymntType().equals(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE)) {
			return;
		}

		// 랜딩 URL
		String landingUrl = "";
		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {
			// 메세지 번호
			messageVO.setMesgId(MessageCode.MESG_ID_EXCHANGE_CLAIM_ACCEPT_BY_ADMIN_ART);
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_EXCHANGE_CLAIM_ACCEPT_BY_ADMIN_ART);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, EXCHANGE_DETAIL_URL, detail.getClmNo());
			map.put("landingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_FO, EXCHANGE_DETAIL_URL, detail.getClmNo());
			mailTempMap.put("landingUrl", landingUrl);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {
			// 메세지 번호 2020.06.03 OTS 메세지 재등록
			//messageVO.setMesgId(MessageCode.MESG_ID_EXCHANGE_CLAIM_ACCEPT_BY_ADMIN_OTS);
			messageVO.setMesgId("OTSA144");
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_EXCHANGE_CLAIM_ACCEPT_BY_ADMIN_OTS);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, EXCHANGE_DETAIL_URL, detail.getClmNo());
			map.put("landingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_FO, EXCHANGE_DETAIL_URL, detail.getClmNo());
			mailTempMap.put("landingUrl", landingUrl);
		}

		/** 교환 메세지는 상품단위 */
		if (!UtilsArray.isEmpty(prdtList)) {
			for (OcClaimProduct ocClaimProduct : prdtList) {
				// 상품명
				map.put("prdtName", ocClaimProduct.getPrdtName());

				// 교환옵션 - ex) 교환옵션 or 기존 옵션 -> 변경 옵션
				if (UtilsObject.isEmpty(ocClaimProduct.getChangePrdtOptnNo())
						|| UtilsText.equals(ocClaimProduct.getPrdtOptnNo(), ocClaimProduct.getChangePrdtOptnNo())) {
					map.put("optnName", ocClaimProduct.getOptnName());
				} else {
					map.put("optnName", ocClaimProduct.getOptnName() + " -> " + ocClaimProduct.getChangeOptnName());
				}

				// 내용 초기화
				messageVO.setMesgContText(null);
				messageVO.setFailedMsg(null);
				messageVO.setFailMesgContText(null);

				// 알림톡 등록
				messageVO.setMessageTemplateModel(map);
				try {
					messageService.setSendMessageProcess(messageVO);
				} catch (Exception e) {
					log.error("교환관리자접수 메세지 발송 실패 : " + e.getMessage());
				}
			}
		}

		/** 교환 메일은 ? */
		// 교환상품 정보 리스트 - mail
		mailTempMap.put("prdtList", prdtList);

		// 메일 발송
		mailTempVO.setMailTemplateModel(mailTempMap);
		try {
			mailService.sendMail(mailTempVO);
		} catch (Exception e) {
			log.error("교환관리자접수 메일 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 교환 접수 완료(자사, 입점 상품 공통) - 수거지시
	 * @Method Name : exchangeClaimAcceptByAdmin
	 * @Date : 2019. 6. 13.
	 * @Author : 이강수
	 * @param OcClaim
	 * @throws Exception
	 */
	public void exchangeClaimAcceptComplete(OcClaim ocClaim) throws Exception {

		Map<String, String> map = new HashMap<>();
		Map<String, Object> mailTempMap = new HashMap<>();

		// 이메일 상단 URL
		mailTempMap.put(ABC_URL, Const.SERVICE_DOMAIN_ART_FO);
		mailTempMap.put(OTS_URL, Const.SERVICE_DOMAIN_OTS_FO);

		/** 클레임 상세조회 */
		Map<String, Object> claimMap = this.getClaimDetailInfo(ocClaim);
		// 클레임 마스터
		OcClaim detail = (OcClaim) claimMap.get("detail");
		// 클레임 상품 리스트(자사/업체 구분 없는 모든 클레임 상품)
		List<OcClaimProduct> prdtList = (List<OcClaimProduct>) claimMap.get("prdtList");
		// 교환/반품 배송비 결제 정보
		List<OcClaimPayment> dlvyAmtPymntList = (List<OcClaimPayment>) claimMap.get("dlvyAmtPymntList");

		DecimalFormat formatter = new DecimalFormat(Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT);

		/**
		 * SMS알림톡 전송 & 메일 발송 set
		 */
		MessageVO messageVO = new MessageVO();
		MailTemplateVO mailTempVO = new MailTemplateVO();

		this.setClmInfoMesgMail(messageVO, mailTempVO, map, mailTempMap, detail);

		// 추가배송비결제타입
		mailTempMap.put("addDlvyAmtPymntType", detail.getAddDlvyAmtPymntType());

		map.put("depositInfo", "");

		if (detail.getAddDlvyAmtPymntType().equals(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT)) {
			// 선결제
			if (!UtilsArray.isEmpty(dlvyAmtPymntList)) {
				OcClaimPayment dlvyAmtPymnt = dlvyAmtPymntList.get(0);

				if (UtilsObject.isNotEmpty(dlvyAmtPymnt)) {
					// 교환배송비 결제방식명
					map.put("dlvyPymntMeansName", dlvyAmtPymnt.getPymntMeansCodeName());
					mailTempMap.put("dlvyPymntMeansName", dlvyAmtPymnt.getPymntMeansCodeName());
					// 교환배송비 결제금액
					map.put("dlvyPymntAmt", UtilsText.concat(formatter.format(dlvyAmtPymnt.getPymntAmt()), "원"));
					mailTempMap.put("dlvyPymntAmt",
							UtilsText.concat(formatter.format(dlvyAmtPymnt.getPymntAmt()), "원"));
					// 입금계좌정보 - 가상계좌입금결제 시 ex) *입금계좌정보 : 은행명 계좌번호 예금주
					if (!UtilsText.equals(dlvyAmtPymnt.getPymntMeansCode(),
							CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT)) {
						if (UtilsText.equals(detail.getSiteNo(), Const.SITE_NO_OTS)) {
							map.put("depositInfo", "*입금계좌정보 : 해당없음");
						} else {
							map.put("depositInfo", "해당없음");
						}
					} else {
						if (UtilsText.equals(detail.getSiteNo(), Const.SITE_NO_OTS)) {
							map.put("depositInfo", "*입금계좌정보 : " + dlvyAmtPymnt.getBankCodeName() + " "
									+ dlvyAmtPymnt.getAcntNoText() + " 입금완료");
						} else {
							map.put("depositInfo",
									dlvyAmtPymnt.getBankCodeName() + " " + dlvyAmtPymnt.getAcntNoText() + " 입금완료");
						}
					}

					dlvyAmtPymnt.setStrPymntDtm(UtilsDate.formatter(CMM_DATE_FORM_HM, dlvyAmtPymnt.getPymntDtm()));
					dlvyAmtPymnt.setCardMask(dlvyAmtPymnt.getPymntOrganNoText());
					// 교환배송비 모델
					mailTempMap.put("dlvyAmtPymnt", dlvyAmtPymnt);
				}
			}

		} else if (detail.getAddDlvyAmtPymntType().equals(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON)) {
			// 무료배송쿠폰
			// 반품배송비 결제방식명
			map.put("dlvyPymntMeansName", "무료배송쿠폰");
			mailTempMap.put("dlvyPymntMeansName", "무료배송쿠폰");
			// 반품배송비 결제금액
			map.put("dlvyPymntAmt", "");
			mailTempMap.put("dlvyPymntAmt", "0원");
			// 입금계좌정보
			if (UtilsText.equals(detail.getSiteNo(), Const.SITE_NO_OTS)) {
				map.put("depositInfo", "*입금계좌정보 : 해당없음");
			} else {
				map.put("depositInfo", "해당없음");
			}

		} else if (detail.getAddDlvyAmtPymntType().equals(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE)) {
			// 배송비 무료
			// 반품배송비 결제방식명
			map.put("dlvyPymntMeansName", "배송비 무료");
			mailTempMap.put("dlvyPymntMeansName", "배송비 무료");
			// 반품배송비 결제금액
			map.put("dlvyPymntAmt", "");
			mailTempMap.put("dlvyPymntAmt", "0원");
			// 입금계좌정보
			if (UtilsText.equals(detail.getSiteNo(), Const.SITE_NO_OTS)) {
				map.put("depositInfo", "*입금계좌정보 : 해당없음");
			} else {
				map.put("depositInfo", "해당없음");
			}
		}

		// 랜딩 URL
		String landingUrl = "";
		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {
			// 메세지 번호
			messageVO.setMesgId(MessageCode.MESG_ID_EXCHANGE_CLAIM_ACCEPT_COMPLETE_ART);
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_EXCHANGE_CLAIM_ACCEPT_COMPLETE_ART);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, EXCHANGE_DETAIL_URL, detail.getClmNo());
			map.put("landingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_FO, EXCHANGE_DETAIL_URL, detail.getClmNo());
			mailTempMap.put("landingUrl", landingUrl);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {
			// 메세지 번호 2020.06.03 OTS 메세지 재등록
			//messageVO.setMesgId(MessageCode.MESG_ID_EXCHANGE_CLAIM_ACCEPT_COMPLETE_OTS);
			messageVO.setMesgId("OTSA145");
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_EXCHANGE_CLAIM_ACCEPT_COMPLETE_OTS);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, EXCHANGE_DETAIL_URL, detail.getClmNo());
			map.put("landingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_FO, EXCHANGE_DETAIL_URL, detail.getClmNo());
			mailTempMap.put("landingUrl", landingUrl);
		}

		/** 교환 메세지는 상품단위 */
		if (!UtilsArray.isEmpty(prdtList)) {
			for (OcClaimProduct ocClaimProduct : prdtList) {
				// 상품명
				map.put("prdtName", ocClaimProduct.getPrdtName());

				// 교환옵션 - ex) 교환옵션 or 기존 옵션 -> 변경 옵션
				if (UtilsObject.isEmpty(ocClaimProduct.getChangePrdtOptnNo())
						|| UtilsText.equals(ocClaimProduct.getPrdtOptnNo(), ocClaimProduct.getChangePrdtOptnNo())) {
					map.put("optnName", ocClaimProduct.getOptnName());
				} else {
					map.put("optnName", ocClaimProduct.getOptnName() + " -> " + ocClaimProduct.getChangeOptnName());
				}

				// 내용 초기화
				messageVO.setMesgContText(null);
				messageVO.setFailedMsg(null);
				messageVO.setFailMesgContText(null);

				// 알림톡 등록
				messageVO.setMessageTemplateModel(map);
				try {
					messageService.setSendMessageProcess(messageVO);
				} catch (Exception e) {
					log.error("교환수거지시 메세지 발송 실패 : " + e.getMessage());
				}
			}
		}

		/** 교환 메일은 ? */
		// 교환상품 정보 리스트 - mail
		mailTempMap.put("prdtList", prdtList);

		// 메일 발송
		mailTempVO.setMailTemplateModel(mailTempMap);
		try {
			mailService.sendMail(mailTempVO);
		} catch (Exception e) {
			log.error("교환수거지시 메일 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 교환 상품 발송완료
	 * @Method Name : exchangeDeliveryComplete
	 * @Date : 2019. 6. 13.
	 * @Author : 이강수
	 * @param OcClaim
	 * @throws Exception
	 */
	public void exchangeDeliveryComplete(OcClaim ocClaim) throws Exception {

		Map<String, String> map = new HashMap<>();
		Map<String, Object> mailTempMap = new HashMap<>();

		// 이메일 상단 URL
		mailTempMap.put(ABC_URL, Const.SERVICE_DOMAIN_ART_FO);
		mailTempMap.put(OTS_URL, Const.SERVICE_DOMAIN_OTS_FO);

		/** 클레임 상세조회 */
		Map<String, Object> claimMap = this.getClaimDetailInfo(ocClaim);
		// 클레임 마스터
		OcClaim detail = (OcClaim) claimMap.get("detail");
		// 클레임 상품 리스트(자사/업체 구분 없는 모든 클레임 상품)
		List<OcClaimProduct> prdtList = (List<OcClaimProduct>) claimMap.get("prdtList");
		// 교환/반품 배송비 결제 정보
		List<OcClaimPayment> dlvyAmtPymntList = (List<OcClaimPayment>) claimMap.get("dlvyAmtPymntList");

		DecimalFormat formatter = new DecimalFormat(Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT);

		// 클레임 상품 리스트(자사/업체 구분 없는 모든 클레임 상품) - 클레임상품상태코드 : 교환불가 제외
		List<OcClaimProduct> possiblePrdtList = prdtList.stream().filter(
				x -> !UtilsText.equals(CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE, x.getClmPrdtStatCode()))
				.collect(Collectors.toList());

		if (UtilsArray.isEmpty(possiblePrdtList)) {
			log.debug("교환 가능 상품이 없으므로 메세지발송 종료");
			return;
		}

		/**
		 * SMS알림톡 전송 & 메일 발송 set
		 */
		MessageVO messageVO = new MessageVO();
		MailTemplateVO mailTempVO = new MailTemplateVO();

		this.setClmInfoMesgMail(messageVO, mailTempVO, map, mailTempMap, detail);

		// 클레임날짜
		mailTempMap.put("clmDtm", detail.getStrClmDtm().substring(0, 4) + "년 " + detail.getStrClmDtm().substring(5, 7)
				+ "월 " + detail.getStrClmDtm().substring(8, 10) + "일");
		// 클레임 완료 날짜
		if (UtilsObject.isEmpty(detail.getStrModDtm())) {
			mailTempMap.put("modDtm", "");
		} else {
			mailTempMap.put("modDtm", detail.getStrModDtm().substring(0, 4) + "년 "
					+ detail.getStrModDtm().substring(5, 7) + "월 " + detail.getStrModDtm().substring(8, 10) + "일");
		}

		// 랜딩 URL
		String landingUrl = "";
		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {
			// 메세지 번호
			messageVO.setMesgId(MessageCode.MESG_ID_EXCHANGE_DLVY_COMPLETE_ART);
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_EXCHANGE_DLVY_COMPLETE_ART);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, EXCHANGE_DETAIL_URL, detail.getClmNo());
			map.put("landingUrl", landingUrl);

			// 택배사 랜딩단축 URL
			map.put("logisVndrLandingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_FO, EXCHANGE_DETAIL_URL, detail.getClmNo());
			mailTempMap.put("landingUrl", landingUrl);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {
			// 메세지 번호
			messageVO.setMesgId(MessageCode.MESG_ID_EXCHANGE_DLVY_COMPLETE_OTS);
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_EXCHANGE_DLVY_COMPLETE_OTS);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, EXCHANGE_DETAIL_URL, detail.getClmNo());
			map.put("landingUrl", landingUrl);

			// 택배사 랜딩단축 URL
			map.put("logisVndrLandingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_FO, EXCHANGE_DETAIL_URL, detail.getClmNo());
			mailTempMap.put("landingUrl", landingUrl);
		}

		/** 교환 메세지는 상품단위 */
		if (!UtilsArray.isEmpty(possiblePrdtList)) {
			for (OcClaimProduct ocClaimProduct : possiblePrdtList) {
				// 상품명
				map.put("prdtName", ocClaimProduct.getPrdtName());
				// 브랜드명
				map.put("brandName", ocClaimProduct.getBrandName());

				// 교환옵션 - ex) 교환옵션 or 기존 옵션 -> 변경 옵션
				if (UtilsObject.isEmpty(ocClaimProduct.getChangePrdtOptnNo())
						|| UtilsText.equals(ocClaimProduct.getPrdtOptnNo(), ocClaimProduct.getChangePrdtOptnNo())) {
					map.put("optnName", ocClaimProduct.getOptnName());
				} else {
					map.put("optnName", ocClaimProduct.getOptnName() + " -> " + ocClaimProduct.getChangeOptnName());
				}

				if (UtilsObject.isEmpty(ocClaimProduct.getChangeWaybilNoText())) {
					// 택배사명
					map.put("logisVndrName", "");
					// 운송장번호
					map.put("waybilNoText", "");
				} else {
					// 택배사명
					map.put("logisVndrName", ocClaimProduct.getChangeLogisVndrCodeName());
					// 운송장번호
					map.put("waybilNoText", ocClaimProduct.getChangeWaybilNoText());
				}

				// 내용 초기화
				messageVO.setMesgContText(null);
				messageVO.setFailedMsg(null);
				messageVO.setFailMesgContText(null);

				// 알림톡 등록
				messageVO.setMessageTemplateModel(map);
				try {
					messageService.setSendMessageProcess(messageVO);
				} catch (Exception e) {
					log.error("교환발송완료 메세지 발송 실패 : " + e.getMessage());
				}
			}
		}

		OcClaimProduct possiblePrdt = possiblePrdtList.get(0);

		/** 교환 메일은 ? */
		if (UtilsObject.isEmpty(possiblePrdt.getChangeWaybilNoText())) {
			// 택배사명
			mailTempMap.put("logisVndrName", "");
			// 운송장번호
			mailTempMap.put("waybilNoText", "");
		} else {
			// 택배사명
			mailTempMap.put("logisVndrName", possiblePrdt.getChangeLogisVndrCodeName());
			// 운송장번호
			mailTempMap.put("waybilNoText", possiblePrdt.getChangeWaybilNoText());
		}
		// 택배사 랜딩단축 URL
		mailTempMap.put("logisVndrLandingUrl", "");

		// 교환상품 정보 리스트 - mail
		mailTempMap.put("prdtList", possiblePrdtList);

		// 메일 발송
		mailTempVO.setMailTemplateModel(mailTempMap);
		try {
			mailService.sendMail(mailTempVO);
		} catch (Exception e) {
			log.error("교환발송완료 메일 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 반품 접수(자사, 입점 상품 공통) - BO접수
	 * @Method Name : returnClaimAcceptByAdmin
	 * @Date : 2019. 6. 13.
	 * @Author : 이강수
	 * @param OcClaim
	 * @throws Exception
	 */
	public void returnClaimAcceptByAdmin(OcClaim ocClaim) throws Exception {

		Map<String, String> map = new HashMap<>();
		Map<String, Object> mailTempMap = new HashMap<>();

		// 이메일 상단 URL
		mailTempMap.put(ABC_URL, Const.SERVICE_DOMAIN_ART_FO);
		mailTempMap.put(OTS_URL, Const.SERVICE_DOMAIN_OTS_FO);

		/** 클레임 상세조회 */
		Map<String, Object> claimMap = this.getClaimDetailInfo(ocClaim);
		// 클레임 마스터
		OcClaim detail = (OcClaim) claimMap.get("detail");
		// 클레임 상품 리스트(자사/업체 구분 없는 모든 클레임 상품)
		List<OcClaimProduct> prdtList = (List<OcClaimProduct>) claimMap.get("prdtList");
		// 교환/반품 배송비 결제 정보
		List<OcClaimPayment> dlvyAmtPymntList = (List<OcClaimPayment>) claimMap.get("dlvyAmtPymntList");

		DecimalFormat formatter = new DecimalFormat(Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT);

		/**
		 * SMS알림톡 전송 & 메일 발송 set
		 */
		MessageVO messageVO = new MessageVO();
		MailTemplateVO mailTempVO = new MailTemplateVO();

		this.setClmInfoMesgMail(messageVO, mailTempVO, map, mailTempMap, detail);

		// 추가배송비결제타입
		mailTempMap.put("addDlvyAmtPymntType", detail.getAddDlvyAmtPymntType());

		if (detail.getAddDlvyAmtPymntType().equals(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT)) {
			// 선결제
			if (!UtilsArray.isEmpty(dlvyAmtPymntList)) {
				OcClaimPayment dlvyAmtPymnt = dlvyAmtPymntList.get(0);

				if (UtilsObject.isNotEmpty(dlvyAmtPymnt)) {
					// 반품배송비 결제방식명
					map.put("dlvyPymntMeansName", dlvyAmtPymnt.getPymntMeansCodeName());
					mailTempMap.put("dlvyPymntMeansName", dlvyAmtPymnt.getPymntMeansCodeName());
					// 반품배송비 결제금액
					map.put("dlvyPymntAmt", UtilsText.concat(formatter.format(dlvyAmtPymnt.getPymntAmt()), "원"));
					mailTempMap.put("dlvyPymntAmt",
							UtilsText.concat(formatter.format(dlvyAmtPymnt.getPymntAmt()), "원"));

					if (dlvyAmtPymnt.getPymntMeansCode().equals(CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT)) {
						// 2020.03.18 : 가상계좌 발급시 은행명 추가
						map.put("acntNoText",
								UtilsText.concat(dlvyAmtPymnt.getBankCodeName(), " ", dlvyAmtPymnt.getAcntNoText()));
						map.put("acntExprDtm",
								UtilsDate.formatter(CMM_DATE_FORM_HM, dlvyAmtPymnt.getVrtlAcntExprDtm()));
						map.put("acntHldrName", "(예금주 : " + dlvyAmtPymnt.getAcntHldrName() + ")");
					}

					dlvyAmtPymnt.setStrPymntDtm(UtilsDate.formatter(CMM_DATE_FORM_HM, dlvyAmtPymnt.getPymntDtm()));
					dlvyAmtPymnt.setCardMask(dlvyAmtPymnt.getPymntOrganNoText());
					// 반품배송비 모델
					mailTempMap.put("dlvyAmtPymnt", dlvyAmtPymnt);
				}
			}

		} else if (detail.getAddDlvyAmtPymntType().equals(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_REFUND_DEDUCTION)) {
			// 환불금액차감
			// 반품배송비 결제방식명
			map.put("dlvyPymntMeansName", "환불금액차감");
			mailTempMap.put("dlvyPymntMeansName", "환불금액차감");
			// 반품배송비 결제금액
			map.put("dlvyPymntAmt", UtilsText.concat(formatter.format(detail.getAddDlvyAmt()), "원"));
			mailTempMap.put("dlvyPymntAmt", UtilsText.concat(formatter.format(detail.getAddDlvyAmt()), "원"));

			// 입금계좌
			map.put("acntNoText", "-");
			// 입금기한
			map.put("acntExprDtm", "-");
			// 예금주
			map.put("acntHldrName", "");

		} else if (detail.getAddDlvyAmtPymntType().equals(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON)) {
			return;
		} else if (detail.getAddDlvyAmtPymntType().equals(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE)) {
			return;
		}

		// 랜딩 URL
		String landingUrl = "";
		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {
			// 메세지 번호
			messageVO.setMesgId(MessageCode.MESG_ID_RETURN_CLAIM_ACCEPT_BY_ADMIN_ART);
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_RETURN_CLAIM_ACCEPT_BY_ADMIN_ART);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, RETURN_DETAIL_URL, detail.getClmNo());
			map.put("landingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_FO, RETURN_DETAIL_URL, detail.getClmNo());
			mailTempMap.put("landingUrl", landingUrl);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {
			// 메세지 번호 2020.06.04 OTS 알림톡 재등록
			//messageVO.setMesgId(MessageCode.MESG_ID_RETURN_CLAIM_ACCEPT_BY_ADMIN_OTS);
			messageVO.setMesgId("OTSA148");
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_RETURN_CLAIM_ACCEPT_BY_ADMIN_OTS);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, RETURN_DETAIL_URL, detail.getClmNo());
			map.put("landingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_FO, RETURN_DETAIL_URL, detail.getClmNo());
			mailTempMap.put("landingUrl", landingUrl);
		}

		String prdtName = "";
		if (!UtilsArray.isEmpty(prdtList)) {
			if (prdtList.size() == 1) {
				prdtName = prdtList.get(0).getPrdtName();
			} else if (prdtList.size() > 1) {
				String extraSize = Integer.toString(prdtList.size() - 1);
				prdtName = UtilsText.concat(prdtList.get(0).getPrdtName(), " 외 ", extraSize, "건");
			}
		}
		map.put("prdtName", prdtName);

		// 내용 초기화
		messageVO.setMesgContText(null);
		messageVO.setFailedMsg(null);
		messageVO.setFailMesgContText(null);

		// 알림톡 등록
		messageVO.setMessageTemplateModel(map);
		try {
			messageService.setSendMessageProcess(messageVO);
		} catch (Exception e) {
			log.error("반품관리자접수 메세지 발송 실패 : " + e.getMessage());
		}

		// ${교환상품 정보 리스트} - mail
		mailTempMap.put("prdtList", prdtList);

		// 메일 발송
		mailTempVO.setMailTemplateModel(mailTempMap);
		try {
			mailService.sendMail(mailTempVO);
		} catch (Exception e) {
			log.error("반품관리자접수 메일 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 반품 접수 완료(자사, 입점 상품 공통) - 수거지시
	 * @Method Name : returnClaimAcceptComplete
	 * @Date : 2019. 6. 13.
	 * @Author : 이강수
	 * @param OcClaim
	 * @throws Exception
	 */
	public void returnClaimAcceptComplete(OcClaim ocClaim) throws Exception {

		Map<String, String> map = new HashMap<>();
		Map<String, Object> mailTempMap = new HashMap<>();

		// 이메일 상단 URL
		mailTempMap.put(ABC_URL, Const.SERVICE_DOMAIN_ART_FO);
		mailTempMap.put(OTS_URL, Const.SERVICE_DOMAIN_OTS_FO);

		/** 클레임 상세조회 */
		Map<String, Object> claimMap = this.getClaimDetailInfo(ocClaim);
		// 클레임 마스터
		OcClaim detail = (OcClaim) claimMap.get("detail");
		// 클레임 상품 리스트(자사/업체 구분 없는 모든 클레임 상품)
		List<OcClaimProduct> prdtList = (List<OcClaimProduct>) claimMap.get("prdtList");
		// 교환/반품 배송비 결제 정보
		List<OcClaimPayment> dlvyAmtPymntList = (List<OcClaimPayment>) claimMap.get("dlvyAmtPymntList");

		DecimalFormat formatter = new DecimalFormat(Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT);

		/**
		 * SMS알림톡 전송 & 메일 발송 set
		 */
		MessageVO messageVO = new MessageVO();
		MailTemplateVO mailTempVO = new MailTemplateVO();

		this.setClmInfoMesgMail(messageVO, mailTempVO, map, mailTempMap, detail);

		// 추가배송비결제타입
		mailTempMap.put("addDlvyAmtPymntType", detail.getAddDlvyAmtPymntType());

		if (detail.getAddDlvyAmtPymntType().equals(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT)) {
			// 선결제
			if (!UtilsArray.isEmpty(dlvyAmtPymntList)) {
				OcClaimPayment dlvyAmtPymnt = dlvyAmtPymntList.get(0);

				if (UtilsObject.isNotEmpty(dlvyAmtPymnt)) {
					// 반품배송비 결제방식명
					map.put("dlvyPymntMeansName", dlvyAmtPymnt.getPymntMeansCodeName());
					mailTempMap.put("dlvyPymntMeansName", dlvyAmtPymnt.getPymntMeansCodeName());
					// 반품배송비 결제금액
					map.put("dlvyPymntAmt", UtilsText.concat(formatter.format(dlvyAmtPymnt.getPymntAmt()), "원"));
					mailTempMap.put("dlvyPymntAmt",
							UtilsText.concat(formatter.format(dlvyAmtPymnt.getPymntAmt()), "원"));

					dlvyAmtPymnt.setStrPymntDtm(UtilsDate.formatter(CMM_DATE_FORM_HM, dlvyAmtPymnt.getPymntDtm()));
					dlvyAmtPymnt.setCardMask(dlvyAmtPymnt.getPymntOrganNoText());

					// 반품배송비 모델
					mailTempMap.put("dlvyAmtPymnt", dlvyAmtPymnt);
				}
			}

		} else if (detail.getAddDlvyAmtPymntType().equals(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_REFUND_DEDUCTION)) {
			// 환불금액차감
			// 반품배송비 결제방식명
			map.put("dlvyPymntMeansName", "환불금액차감");
			mailTempMap.put("dlvyPymntMeansName", "환불금액차감");
			// 반품배송비 결제금액
			map.put("dlvyPymntAmt", UtilsText.concat(formatter.format(detail.getAddDlvyAmt()), "원"));
			mailTempMap.put("dlvyPymntAmt", UtilsText.concat(formatter.format(detail.getAddDlvyAmt()), "원"));

		} else if (detail.getAddDlvyAmtPymntType().equals(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON)) {
			// 무료배송쿠폰
			// 반품배송비 결제방식명
			map.put("dlvyPymntMeansName", "무료배송쿠폰");
			mailTempMap.put("dlvyPymntMeansName", "무료배송쿠폰");
			// 반품배송비 결제금액
			map.put("dlvyPymntAmt", "");
			mailTempMap.put("dlvyPymntAmt", "0원");

		} else if (detail.getAddDlvyAmtPymntType().equals(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE)) {
			// 배송비 무료
			// 반품배송비 결제방식명
			map.put("dlvyPymntMeansName", "배송비 무료");
			mailTempMap.put("dlvyPymntMeansName", "배송비 무료");
			// 반품배송비 결제금액
			map.put("dlvyPymntAmt", "");
			mailTempMap.put("dlvyPymntAmt", "0원");
		}

		// 랜딩 URL
		String landingUrl = "";
		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {
			// 메세지 번호
			messageVO.setMesgId(MessageCode.MESG_ID_RETURN_CLAIM_ACCEPT_COMPLETE_ART);
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_RETURN_CLAIM_ACCEPT_COMPLETE_ART);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, RETURN_DETAIL_URL, detail.getClmNo());
			map.put("landingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_FO, RETURN_DETAIL_URL, detail.getClmNo());
			mailTempMap.put("landingUrl", landingUrl);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {
			// 메세지 번호 2020.06.04 알림톡 재승인
			//messageVO.setMesgId(MessageCode.MESG_ID_RETURN_CLAIM_ACCEPT_COMPLETE_OTS);
			messageVO.setMesgId("OTSA149");
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_RETURN_CLAIM_ACCEPT_COMPLETE_OTS);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, RETURN_DETAIL_URL, detail.getClmNo());
			map.put("landingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_FO, RETURN_DETAIL_URL, detail.getClmNo());
			mailTempMap.put("landingUrl", landingUrl);
		}

		String prdtName = "";
		if (!UtilsArray.isEmpty(prdtList)) {
			if (prdtList.size() == 1) {
				prdtName = prdtList.get(0).getPrdtName();
			} else if (prdtList.size() > 1) {
				String extraSize = Integer.toString(prdtList.size() - 1);
				prdtName = UtilsText.concat(prdtList.get(0).getPrdtName(), " 외 ", extraSize, "건");
			}
		}
		map.put("prdtName", prdtName);

		// 내용 초기화
		messageVO.setMesgContText(null);
		messageVO.setFailedMsg(null);
		messageVO.setFailMesgContText(null);

		// 알림톡 등록
		messageVO.setMessageTemplateModel(map);
		try {
			messageService.setSendMessageProcess(messageVO);
		} catch (Exception e) {
			log.error("반품수거지시 메세지 발송 실패 : " + e.getMessage());
		}

		// 교환상품 정보 리스트 - mail
		mailTempMap.put("prdtList", prdtList);

		// 메일 발송
		mailTempVO.setMailTemplateModel(mailTempMap);
		try {
			mailService.sendMail(mailTempVO);
		} catch (Exception e) {
			log.error("반품수거지시 메일 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 반품처리 완료
	 * @Method Name : returnProcessComplete
	 * @Date : 2019. 6. 13.
	 * @Author : 이강수
	 * @param OcClaim
	 * @throws Exception
	 */
	public void returnProcessComplete(OcClaim ocClaim) throws Exception {

		Map<String, String> map = new HashMap<>();
		Map<String, Object> mailTempMap = new HashMap<>();

		// 이메일 상단 URL
		mailTempMap.put(ABC_URL, Const.SERVICE_DOMAIN_ART_FO);
		mailTempMap.put(OTS_URL, Const.SERVICE_DOMAIN_OTS_FO);

		/** 클레임 상세조회 */
		Map<String, Object> claimMap = this.getClaimDetailInfo(ocClaim);
		// 클레임 마스터
		OcClaim detail = (OcClaim) claimMap.get("detail");
		// 클레임 상품 리스트(자사/업체 구분 없는 모든 클레임 상품)
		List<OcClaimProduct> prdtList = (List<OcClaimProduct>) claimMap.get("prdtList");
		// 클레임 환불 영역 정보
		List<OcClaimPayment> refundPymntAmtList = (List<OcClaimPayment>) claimMap.get("refundPymntAmtList");
		// 교환/반품 배송비 결제 정보
		List<OcClaimPayment> dlvyAmtPymntList = (List<OcClaimPayment>) claimMap.get("dlvyAmtPymntList");

		DecimalFormat formatter = new DecimalFormat(Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT);

		// 클레임 상품 리스트(자사/업체 구분 없는 모든 클레임 상품) - 클레임상품상태코드 : 반품불가 제외
		List<OcClaimProduct> possiblePrdtList = prdtList.stream()
				.filter(x -> !UtilsText.equals(CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE, x.getClmPrdtStatCode()))
				.collect(Collectors.toList());

		if (UtilsArray.isEmpty(possiblePrdtList)) {
			log.debug("반품 가능 상품이 없으므로 메세지발송 종료");
			return;
		}

		/**
		 * SMS알림톡 전송 & 메일 발송 set
		 */
		MessageVO messageVO = new MessageVO();
		MailTemplateVO mailTempVO = new MailTemplateVO();

		this.setClmInfoMesgMail(messageVO, mailTempVO, map, mailTempMap, detail);

		// LMS 강제발송
		// messageVO.setSendTypeCode(CommonCode.SEND_TYPE_CODE_LMS);

		/**
		 * 결제금액 (주문전체결제금액)
		 */
		OcOrder orderInfo = new OcOrder();
		orderInfo.setOrderNo(detail.getOrgOrderNo());
		orderInfo = ocOrderDao.selectOrderDetail(orderInfo);

		map.put("totalPymntAmt", UtilsText.concat(formatter.format(orderInfo.getPymntAmt()), "원"));
		mailTempMap.put("totalPymntAmt", UtilsText.concat(formatter.format(orderInfo.getPymntAmt()), "원"));

		/**
		 * 반품배송비
		 */
		// 추가배송비결제타입
		mailTempMap.put("addDlvyAmtPymntType", detail.getAddDlvyAmtPymntType());

		if (detail.getAddDlvyAmtPymntType().equals(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_ADD_PAYMENT)) {
			if (UtilsArray.isEmpty(dlvyAmtPymntList)) {
				// 선결제
				map.put("dlvyPymntMeans", "");
				map.put("dlvyPymntAmt", "");
				mailTempMap.put("dlvyPymntAmt", "");

			} else {
				OcClaimPayment dlvyPymnt = new OcClaimPayment();
				dlvyPymnt = dlvyAmtPymntList.get(0);
				// 선결제
				if (UtilsText.equals(dlvyPymnt.getPymntStatCode(), CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL)) {
					// 배송비결제 취소라면
					map.put("dlvyPymntMeans", dlvyPymnt.getPymntMeansCodeName());
					map.put("dlvyPymntAmt", UtilsText.concat(formatter.format(dlvyPymnt.getPymntAmt()), "원 결제취소"));
					mailTempMap.put("dlvyPymntAmt",
							UtilsText.concat(formatter.format(dlvyPymnt.getPymntAmt()), "원 결제취소"));

				} else {
					map.put("dlvyPymntMeans", dlvyPymnt.getPymntMeansCodeName());
					map.put("dlvyPymntAmt", UtilsText.concat(formatter.format(dlvyPymnt.getPymntAmt()), "원"));
					mailTempMap.put("dlvyPymntAmt", UtilsText.concat(formatter.format(dlvyPymnt.getPymntAmt()), "원"));
				}
			}

		} else if (detail.getAddDlvyAmtPymntType().equals(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_REFUND_DEDUCTION)) {
			// 환불금액차감
			map.put("dlvyPymntMeans", "환불금액차감");
			map.put("dlvyPymntAmt", UtilsText.concat(formatter.format(detail.getAddDlvyAmt()), "원"));
			mailTempMap.put("dlvyPymntAmt", UtilsText.concat(formatter.format(detail.getAddDlvyAmt()), "원"));

		} else if (detail.getAddDlvyAmtPymntType().equals(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE_COUPON)) {
			// 무료배송쿠폰
			map.put("dlvyPymntMeans", "무료배송비쿠폰 사용");
			map.put("dlvyPymntAmt", "");
			mailTempMap.put("dlvyPymntAmt", "0원");

		} else if (detail.getAddDlvyAmtPymntType().equals(CommonCode.ADD_DLVY_AMT_PYMNT_TYPE_FREE)) {
			// 배송비 무료
			map.put("dlvyPymntMeans", "배송비 무료");
			map.put("dlvyPymntAmt", "");
			mailTempMap.put("dlvyPymntAmt", "0원");
		}

		/**
		 * 총 환불금액
		 */
		int intRefundAmt = 0;
		if (UtilsObject.isNotEmpty(claimMap.get("refundPymntAmtSum"))) {
			intRefundAmt = (int) claimMap.get("refundPymntAmtSum");
		}
		String refundAmt = UtilsText.concat(formatter.format(intRefundAmt), "원");
		map.put("refundAmt", refundAmt);
		mailTempMap.put("refundAmt", refundAmt);

		// 랜딩 URL
		String landingUrl = "";
		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {
			// 메세지 번호
			messageVO.setMesgId("ART4020");
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_RETURN_PROC_COMPLETE_ART);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, RETURN_DETAIL_URL, detail.getClmNo());
			map.put("landingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_FO, RETURN_DETAIL_URL, detail.getClmNo());
			mailTempMap.put("landingUrl", landingUrl);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {
			// 메세지 번호 2020.06.04 OTS 알림톡 재등록
			// messageVO.setMesgId(MessageCode.MESG_ID_RETURN_PROC_COMPLETE_OTS);
			messageVO.setMesgId("OTSA150");
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_RETURN_PROC_COMPLETE_OTS);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, RETURN_DETAIL_URL, detail.getClmNo());
			map.put("landingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_FO, RETURN_DETAIL_URL, detail.getClmNo());
			mailTempMap.put("landingUrl", landingUrl);
		}

		String prdtName = "";
		if (!UtilsArray.isEmpty(possiblePrdtList)) {
			if (possiblePrdtList.size() == 1) {
				prdtName = possiblePrdtList.get(0).getPrdtName();
			} else if (possiblePrdtList.size() > 1) {
				String extraSize = Integer.toString(possiblePrdtList.size() - 1);
				prdtName = UtilsText.concat(possiblePrdtList.get(0).getPrdtName(), " 외 ", extraSize, "건");
			}
		}
		map.put("prdtName", prdtName);

		// 내용 초기화
		messageVO.setMesgContText(null);
		messageVO.setFailedMsg(null);
		messageVO.setFailMesgContText(null);

		// 알림톡 등록
		messageVO.setMessageTemplateModel(map);
		try {
			messageService.setSendMessageProcess(messageVO);
		} catch (Exception e) {
			log.error("반품처리완료 메세지 발송 실패 : " + e.getMessage());
		}

		// ${교환상품 정보 리스트} - mail
		mailTempMap.put("prdtList", possiblePrdtList);

		// 메일 발송
		mailTempVO.setMailTemplateModel(mailTempMap);
		try {
			mailService.sendMail(mailTempVO);
		} catch (Exception e) {
			log.error("반품처리완료 메일 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 환수 금액 발생 (메세지만)
	 * @Method Name : redempAmtOccurrence
	 * @Date : 2019. 7. 12.
	 * @Author : 이강수
	 * @param OcClaim
	 * @throws Exception
	 */
	public void redempAmtOccurrence(OcClaim ocClaim) throws Exception {

		Map<String, String> map = new HashMap<>();

		/** 클레임 상세조회 */
		Map<String, Object> claimMap = this.getClaimDetailInfo(ocClaim);
		// 클레임 마스터
		OcClaim detail = (OcClaim) claimMap.get("detail");
		// 반품 클레임 - 환수 포인트 금액 결제 정보
		List<OcClaimPayment> redempPymntAmtPublicList = (List<OcClaimPayment>) claimMap.get("redempPymntAmtPublicList");

		DecimalFormat formatter = new DecimalFormat(Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT);

		/**
		 * SMS알림톡 전송 set & 메일 발송 set
		 */
		MessageVO messageVO = new MessageVO();
		// 사이트
		messageVO.setSiteNo(detail.getSiteNo());
		// 관리자 발송여부
		messageVO.setAdminSendYn(Const.BOOLEAN_TRUE);
		// 즉시발송여부
		messageVO.setReal(true);

		// LMS 강제발송
		// messageVO.setSendTypeCode(CommonCode.SEND_TYPE_CODE_LMS);

		// 머릿말
		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {
			messageVO.setMesgTitleText(SENDER_TITLE_ABC);
			// 발신자
			messageVO.setSndrName(Const.SYS_SENDER_MESSAGE_NAME);
			// 발신번호
			messageVO.setSendTelNoText(Const.SYS_SENDER_MESSAGE_NUMBER);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {
			messageVO.setMesgTitleText(SENDER_TITLE_OTS);
			// 발신자
			messageVO.setSndrName(Const.SYS_OTS_SENDER_MESSAGE_NAME);
			// 발신번호
			messageVO.setSendTelNoText(Const.SYS_OTS_SENDER_MESSAGE_NUMBER);
		}

		messageVO.setMesgContText("");

		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(detail.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder);
		// 수신자명
		messageVO.setRcvrName(ocOrder.getBuyerName());
		// 수신번호
		messageVO.setRecvTelNoText(ocOrder.getBuyerHdphnNoText());
		// 수신자명
		map.put("memberName", ocOrder.getBuyerName());

		// 주문번호
		map.put("orderNo", detail.getOrgOrderNo());
		// 발생사유
		map.put("ocrncRsnName", "환수포인트발생");

		if (!UtilsArray.isEmpty(redempPymntAmtPublicList)) {

			OcClaimPayment redempPymntAmt = new OcClaimPayment();
			redempPymntAmt = redempPymntAmtPublicList.get(0);
			// 추가결제금액
			if (UtilsObject.isEmpty(redempPymntAmt.getPymntTodoAmt()) || redempPymntAmt.getPymntTodoAmt().equals(0)) {
				map.put("addPymntAmt", "0원");
			} else {
				map.put("addPymntAmt", UtilsText.concat(formatter.format(redempPymntAmt.getPymntTodoAmt()), "원"));
			}
			// 입금 가상 계좌
			if (UtilsObject.isEmpty(redempPymntAmt.getAcntNoText()) || redempPymntAmt.getAcntNoText().equals("")) {
				map.put("acntNoText", "");
			} else {
				map.put("acntNoText", redempPymntAmt.getAcntNoText());
			}
			// 예금주
			if (UtilsObject.isEmpty(redempPymntAmt.getAcntHldrName())) {
				map.put("acntHldrName", "");
			} else {
				map.put("acntHldrName", "(예금주 : " + redempPymntAmt.getAcntHldrName() + ")");
			}
		}

		// 랜딩 URL
		String landingUrl = "";
		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {
			messageVO.setMesgTitleText(SENDER_TITLE_ABC);
			messageVO.setFailedSubject(SENDER_TITLE_ABC);
			// 메세지 번호
			messageVO.setMesgId(MessageCode.MESG_ID_REDEMP_AMT_OCCURRENCE_ART);

			if (detail.getClmGbnCode().equals(CommonCode.CLM_GBN_CODE_CANCEL)) {
				landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, CANCEL_DETAIL_URL, detail.getClmNo());
			} else if (detail.getClmGbnCode().equals(CommonCode.CLM_GBN_CODE_EXCHANGE)) {
				landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, EXCHANGE_DETAIL_URL, detail.getClmNo());
			} else if (detail.getClmGbnCode().equals(CommonCode.CLM_GBN_CODE_RETURN)) {
				landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, RETURN_DETAIL_URL, detail.getClmNo());
			}
			// 랜딩 URL
			map.put("landingUrl", landingUrl);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {
			messageVO.setMesgTitleText(SENDER_TITLE_OTS);
			messageVO.setFailedSubject(SENDER_TITLE_OTS);
			// 메세지 번호
			messageVO.setMesgId(MessageCode.MESG_ID_REDEMP_AMT_OCCURRENCE_OTS);

			if (detail.getClmGbnCode().equals(CommonCode.CLM_GBN_CODE_CANCEL)) {
				landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, CANCEL_DETAIL_URL, detail.getClmNo());
			} else if (detail.getClmGbnCode().equals(CommonCode.CLM_GBN_CODE_EXCHANGE)) {
				landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, EXCHANGE_DETAIL_URL, detail.getClmNo());
			} else if (detail.getClmGbnCode().equals(CommonCode.CLM_GBN_CODE_RETURN)) {
				landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, RETURN_DETAIL_URL, detail.getClmNo());
			}
			// 랜딩 URL
			map.put("landingUrl", landingUrl);
		}

		// 내용 초기화
		messageVO.setMesgContText(null);
		messageVO.setFailedMsg(null);
		messageVO.setFailMesgContText(null);

		// 알림톡 등록
		messageVO.setMessageTemplateModel(map);
		try {
			messageService.setSendMessageProcess(messageVO);
		} catch (Exception e) {
			log.error("환수금액발생 메세지 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 메세지, 메일 보내는이, 받는이 set
	 * @Method Name : setClmInfoMesgMail
	 * @Date : 2019. 7. 30.
	 * @Author : 이강수
	 * @param
	 * @throws Exception
	 */
	public void setClmInfoMesgMail(MessageVO messageVO, MailTemplateVO mailTempVO, Map<String, String> map,
			Map<String, Object> mailTempMap, OcClaim detail) throws Exception {
		// 사이트
		messageVO.setSiteNo(detail.getSiteNo());
		messageVO.setMesgContText("");
		// 즉시발송여부
		messageVO.setReal(true);
		mailTempVO.setRealTime(true);
		// 관리자 발송여부
		messageVO.setAdminSendYn(Const.BOOLEAN_TRUE);

		// LMS 강제발송
		// messageVO.setSendTypeCode(CommonCode.SEND_TYPE_CODE_LMS);

		// 머릿말
		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {
			messageVO.setMesgTitleText(SENDER_TITLE_ABC);
			messageVO.setFailedSubject(SENDER_TITLE_ABC);
			// 발신자
			messageVO.setSndrName(Const.SYS_SENDER_MESSAGE_NAME);
			// 발신번호
			messageVO.setSendTelNoText(Const.SYS_SENDER_MESSAGE_NUMBER);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {
			messageVO.setMesgTitleText(SENDER_TITLE_OTS);
			messageVO.setFailedSubject(SENDER_TITLE_OTS);
			// 발신자
			messageVO.setSndrName(Const.SYS_OTS_SENDER_MESSAGE_NAME);
			// 발신번호
			messageVO.setSendTelNoText(Const.SYS_OTS_SENDER_MESSAGE_NUMBER);
		}

		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(detail.getOrgOrderNo());
		ocOrder = ocOrderDao.selectOrderDetail(ocOrder);
		// 수신자명
		messageVO.setRcvrName(ocOrder.getBuyerName());
		mailTempVO.setReceiverName(ocOrder.getBuyerName());
		// 수신번호
		messageVO.setRecvTelNoText(ocOrder.getBuyerHdphnNoText());
		mailTempVO.setReceiverEmail(ocOrder.getBuyerEmailAddrText());
		// 수신자명
		map.put("memberName", ocOrder.getBuyerName());
		mailTempMap.put("memberName", ocOrder.getBuyerName());

		// 주문번호
		map.put("orderNo", detail.getOrgOrderNo());
		// 접수번호
		mailTempMap.put("clmNo", detail.getClmNo());
		// 클레임날짜
		String clmDtm = detail.getStrClmDtm();
		mailTempMap.put("clmDtm",
				clmDtm.substring(0, 4) + "년 " + clmDtm.substring(5, 7) + "월 " + clmDtm.substring(8, 10) + "일");
		// 클레임수정날짜
		String modDtm = detail.getStrModDtm();
		if (UtilsObject.isEmpty(modDtm)) {
			mailTempMap.put("modDtm",
					clmDtm.substring(0, 4) + "년 " + clmDtm.substring(5, 7) + "월 " + clmDtm.substring(8, 10) + "일");
		} else {
			mailTempMap.put("modDtm",
					modDtm.substring(0, 4) + "년 " + modDtm.substring(5, 7) + "월 " + modDtm.substring(8, 10) + "일");
		}
		// 회수지
		mailTempMap.put("buyerPostCodeText", detail.getBuyerPostCodeText());
		mailTempMap.put("buyerPostAddrText", detail.getBuyerPostAddrText());
		mailTempMap.put("buyerDtlAddrText", detail.getBuyerDtlAddrText());
		// 수령지
		mailTempMap.put("rcvrPostCodeText", detail.getRcvrPostCodeText());
		mailTempMap.put("rcvrPostAddrText", detail.getRcvrPostAddrText());
		mailTempMap.put("rcvrDtlAddrText", detail.getRcvrDtlAddrText());
	}

	/**
	 * @Desc : 클레임 상세 조회 - backend와 동일하게 조회하는
	 * @Method Name : getClaimDetailInfo
	 * @Date : 2019. 7. 9.
	 * @Author : 이강수
	 * @param OcClaim
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> getClaimDetailInfo(OcClaim param) throws Exception {

		Map<String, Object> resultMap = new HashMap<>();

		param.setPymntMeansCodeVirtualAccount(CommonCode.PYMNT_MEANS_CODE_VIRTUAL_ACCOUNT);
		// 상품관련파일순번 1 (대표)
		param.setPrdtRltnFileSeq(1);

		/**
		 * 클레임 마스터
		 */
		OcClaim detail = ocClaimDao.selectClaimDetail(param);
		// 상세주소 - 이스케이프로 변환되어 DB에 들어있는 데이터 가져오기
		detail.setBuyerDtlAddrText(UtilsText.unescapeXss(detail.getBuyerDtlAddrText()));
		detail.setRcvrDtlAddrText(UtilsText.unescapeXss(detail.getRcvrDtlAddrText()));

		/**
		 * 클레임 상품 정보
		 */
		// 사은품, 배송비 제외
		String[] prdtTypeCodeList = { CommonCode.PRDT_TYPE_CODE_GIFT, CommonCode.PRDT_TYPE_CODE_DELIVERY };
		param.setPrdtTypeCodeList(prdtTypeCodeList);

		// 클레임 상품에서 주문배송비를 추출하기 위해 배송비 유형 set
		param.setPrdtTypeCode(CommonCode.PRDT_TYPE_CODE_DELIVERY);

		List<OcClaimProduct> clmPrdtList = ocClaimProductDao.selectClaimProductListBackend(param);

		if (!UtilsArray.isEmpty(clmPrdtList)) {

			for (OcClaimProduct ocp : clmPrdtList) {
				if (UtilsObject.isEmpty(ocp.getImageUrl())) {
					ocp.setImageUrl(NO_IMAGE_URL);
					// 2020.02.10 : alertText null 때문에 오류나던 문제 수정
					ocp.setAltrnText("");
				}
				// 2020.02.08 : 사은품/배송비는 브랜드 NO가 안들어가기에 ""처리
				if (UtilsText.equals(ocp.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)
						|| UtilsText.equals(ocp.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY)) {
					ocp.setBrandNo("");
					ocp.setBrandName("");
				}
			}

			detail.setClmRsnCodeName(clmPrdtList.get(0).getClmRsnCodeName());
			detail.setClmEtcRsnText(clmPrdtList.get(0).getClmEtcRsnText());

			// 클레임 상품 리스트(자사/업체 구분 없는 모든 클레임 상품)
			resultMap.put("prdtList", clmPrdtList);
		}

		/**
		 * 클레임 결제 정보
		 */
		OcClaimPayment ocClaimPayment = new OcClaimPayment();
		ocClaimPayment.setClmNo(param.getClmNo());
		List<OcClaimPayment> claimPaymentList = ocClaimPaymentDao.selectClaimPaymentList(ocClaimPayment);

		if (!UtilsArray.isEmpty(claimPaymentList)) {
			/*
			 * 교환/반품 배송비 결제 정보
			 */
			// 환수환불구분 : 환수
			// 자사처리대상여부 : N
			// 이력구분타입 : 일반결제
			// 결제발생사유코드 : (클레임)배송비
			List<OcClaimPayment> dlvyAmtPymntList = claimPaymentList.stream()
					.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP))
					.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
					.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_PUBLIC))
					.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT))
					.collect(Collectors.toList());

			resultMap.put("dlvyAmtPymntList", dlvyAmtPymntList);

			/*
			 * 취소/교환/반품 클레임 - 환수정보(추가결제금액)
			 */
			// 환수환불구분 : 환수
			// 자사처리대상여부 : N
			// 이력구분타입 : 히스토리
			List<OcClaimPayment> redempPymntAmtList = claimPaymentList.stream()
					.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP))
					.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
					.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY))
					// .filter(x -> !UtilsText.equals(x.getOcrncRsnCode(),
					// CommonCode.OCRNC_RSN_CODE_DELIVERY_AMT))
					.collect(Collectors.toList());

			resultMap.put("redempPymntAmtList", redempPymntAmtList);

			resultMap.put("redempPymntAmtSum", redempPymntAmtList.stream().mapToInt(x -> x.getPymntAmt()).sum()); // 환수금액sum

			/*
			 * 클레임 환불 영역 정보
			 */
			// 환수환불구분 : 환불
			// 자사처리대상여부 : N
			// 이력구분타입 : 일반결제(주문금 기존 - 반품/교환 배송비 제외)
			List<OcClaimPayment> refundPymntAmtList = claimPaymentList.stream()
					.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
					.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
					.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_PUBLIC))
					.filter(x -> !UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_ORDER_DELIVERY_AMT))
					.collect(Collectors.toList());

			resultMap.put("refundPymntAmtList", refundPymntAmtList);

			resultMap.put("refundPymntAmtSum", refundPymntAmtList.stream().mapToInt(x -> x.getPymntAmt()).sum()); // 환불금액sum

			// 환수환불구분 : 환불
			// 자사처리대상여부 : N
			// 이력구분타입 : 일반결제(주문금 기존 - 반품/교환 배송비 제외)
			List<OcClaimPayment> refundPymntAmtHistoryList = claimPaymentList.stream()
					.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
					.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
					.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY))
					.filter(x -> !UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_ORDER_DELIVERY_AMT))
					.collect(Collectors.toList());

			resultMap.put("refundPymntAmtHistoryList", refundPymntAmtHistoryList); // 환불 히스토리

			/*
			 * 취소 클레임 - 환수배송비 환불대상 히스토리 목록
			 */
			// 환수환불구분 : 환불
			// 자사처리대상여부 : N
			// 이력구분타입 : 히스토리
			List<OcClaimPayment> refundPreviousRedempDlvyList = claimPaymentList.stream()
					.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
					.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
					.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY))
					.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_ORDER_DELIVERY_AMT))
					.collect(Collectors.toList());

			resultMap.put("refundPreviousRedempDlvyList", refundPreviousRedempDlvyList);

			/**
			 * 다족구매 프로모션 추가 환불대상 히스토리 목록
			 */
			List<OcClaimPayment> addMultiBuyDifferPaymentList = claimPaymentList.stream()
					.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REFUND))
					.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_FALSE))
					.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_HISTORY))
					.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_PROMOTION))
					.collect(Collectors.toList());

			resultMap.put("addMultiBuyDifferPaymentList", addMultiBuyDifferPaymentList);

			/*
			 * 반품 클레임 - 환수 포인트 금액 결제 정보
			 */
			// 환수환불구분 : 환수
			// 자사처리대상여부 : Y
			// 이력구분타입 : P
			// 발생사유 : 포인트
			List<OcClaimPayment> redempPymntAmtPublicList = claimPaymentList.stream()
					.filter(x -> UtilsText.equals(x.getRedempRfndGbnType(), CommonCode.REDEMP_RFND_GBN_TYPE_REDEMP))
					.filter(x -> UtilsText.equals(x.getMmnyProcTrgtYn(), Const.BOOLEAN_TRUE))
					.filter(x -> UtilsText.equals(x.getHistGbnType(), CommonCode.HIST_GBN_TYPE_PUBLIC))
					.filter(x -> UtilsText.equals(x.getOcrncRsnCode(), CommonCode.OCRNC_RSN_CODE_POINT))
					.collect(Collectors.toList());

			resultMap.put("redempPymntAmtPublicList", redempPymntAmtPublicList);
		}

		/**
		 * 클레임 상품 금액 sum
		 */
		resultMap.put("claimPrdtSumAmt",
				clmPrdtList.stream()
						.filter(x -> !UtilsText.equals(x.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_DELIVERY))
						.mapToInt(o -> o.getOrderAmt()).sum());

		/**
		 * 클레임 주문배송비 금액 sum
		 */
		resultMap.put("claimDlvySumAmt", clmPrdtList.stream().mapToInt(o -> o.getRefundDlvyAmt()).sum());

		/**
		 * 반품불가 상태 상품을 제외한 클레임상품 금액
		 */
		resultMap.put("claimPossiblePrdtSumAmt", clmPrdtList.stream()
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE))
				.mapToInt(o -> o.getOrderAmt()).sum());

		/**
		 * 최종적으로 클레임 마스터를 담는다.
		 */
		if (detail != null) {
			resultMap.put("detail", detail);
		}

		return resultMap;
	}
}
