package kr.co.abcmart.bo.afterService.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.afterService.model.master.OcAsAccept;
import kr.co.abcmart.bo.afterService.model.master.OcAsAcceptProduct;
import kr.co.abcmart.bo.afterService.model.master.OcAsPayment;
import kr.co.abcmart.bo.afterService.repository.master.OcAsAcceptDao;
import kr.co.abcmart.bo.afterService.repository.master.OcAsAcceptProductDao;
import kr.co.abcmart.bo.afterService.repository.master.OcAsPaymentDao;
import kr.co.abcmart.bo.cmm.service.MailService;
import kr.co.abcmart.bo.cmm.service.MessageService;
import kr.co.abcmart.bo.cmm.vo.MailTemplateVO;
import kr.co.abcmart.bo.cmm.vo.MessageVO;
import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.service.MemberService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.constant.MailCode;
import kr.co.abcmart.constant.MessageCode;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : AS 메세지 전송 서비스
 * @FileName : AfterServiceMessageService.java
 * @Project : abc.bo
 * @Date : 2019. 7. 16.
 * @Author : 이강수
 */
@Slf4j
@Service
public class AfterServiceMessageService extends BaseController {

	@Autowired
	private OcAsAcceptDao ocAsAcceptDao;

	@Autowired
	private OcAsAcceptProductDao ocAsAcceptProductDao;

	@Autowired
	private OcAsPaymentDao ocAsPaymentDao;

	@Autowired
	private MemberService memberService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private MailService mailService;

	// 주문 상세페이지 URL
	private static final String ORDER_DETAIL_URL = "/mypage/order/read-order-detail?orderNo=";

	// AS 상세페이지 URL
	private static final String AS_DETAIL_URL = "/mypage/afterservice-request-detail?asAcceptNo=";

	private static final String NO_IMAGE_URL = UtilsText.concat(Const.SERVICE_DOMAIN_ART_FO,
			"/static/images/common/no_image.png");

	private static final String ABC_URL = "abcUrl";
	private static final String OTS_URL = "otsUrl";

	private static final String CMM_DATE_FORM_HM = "yyyy년 MM월 dd일 HH시 mm분";

	/**
	 * @Desc : 심의/수선 접수와 동시에 접수완료 - BO접수
	 * @Method Name : asAcceptComplete
	 * @Date : 2019. 7. 11.
	 * @Author : 이강수
	 * @param OcAsAccept
	 * @throws Exception
	 */
	public void asAcceptComplete(OcAsAccept ocAsAccept) throws Exception {

		Map<String, String> map = new HashMap<>();
		Map<String, Object> mailTempMap = new HashMap<>();

		// 이메일 상단 URL
		mailTempMap.put(ABC_URL, Const.SERVICE_DOMAIN_ART_FO);
		mailTempMap.put(OTS_URL, Const.SERVICE_DOMAIN_OTS_FO);

		/** AS 상세조회 */
		Map<String, Object> asMap = this.getAsAcceptDetailInfo(ocAsAccept);
		// AS 마스터
		OcAsAccept detail = (OcAsAccept) asMap.get("asAcceptInfo");
		// AS 상품 리스트
		OcAsAcceptProduct prdt = (OcAsAcceptProduct) asMap.get("ocAsAcceptProductInfo");
		// AS 결제정보 조회
		OcAsPayment ocAsPayment = (OcAsPayment) asMap.get("ocAsPaymentInfo");

		if (UtilsObject.isEmpty(prdt.getImageUrl())) {
			prdt.setImageUrl(NO_IMAGE_URL);
		}
		if (UtilsObject.isEmpty(prdt.getAltrnText())) {
			prdt.setAltrnText("");
		}

		DecimalFormat formatter = new DecimalFormat(Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT);

		/**
		 * SMS알림톡 전송 set & 메일 발송 set
		 */
		MessageVO messageVO = new MessageVO();
		MailTemplateVO mailTempVO = new MailTemplateVO();

		// 메일, 메세지 보내는이, 받는이 set
		this.setAsMemberInfoMesgMail(messageVO, mailTempVO, map, mailTempMap, detail);

		// 회수지
		mailTempMap.put("buyerPostCodeText", detail.getBuyerPostCodeText());
		mailTempMap.put("buyerPostAddrText", detail.getBuyerPostAddrText());
		mailTempMap.put("buyerDtlAddrText", detail.getBuyerDtlAddrText());
		// 수령지
		mailTempMap.put("rcvrPostCodeText", detail.getRcvrPostCodeText());
		mailTempMap.put("rcvrPostAddrText", detail.getRcvrPostAddrText());
		mailTempMap.put("rcvrDtlAddrText", detail.getRcvrDtlAddrText());

		if (UtilsObject.isEmpty(ocAsPayment)) {
			// ${배송비 결제방식명}
			map.put("dlvyPymntMeansName", "");
			mailTempMap.put("dlvyPymntMeansName", "");
			// ${배송비 결제금액}
			map.put("dlvyPymntAmt", "");
			mailTempMap.put("dlvyPymntAmt", "");

			ocAsPayment = new OcAsPayment();
			ocAsPayment.setPymntOrganName("");
			ocAsPayment.setCardMask("");
			ocAsPayment.setInstmtTermCount((short) 0);
			ocAsPayment.setStrPymntDtm("");
			ocAsPayment.setPymntMeansCode("");
			ocAsPayment.setPymntMeansCodeName("");

			// ${배송비 모델}
			mailTempMap.put("dlvyAmtPymnt", ocAsPayment);

		} else {
			// ${배송비 결제방식명}
			map.put("dlvyPymntMeansName", ocAsPayment.getPymntMeansCodeName());
			mailTempMap.put("dlvyPymntMeansName", ocAsPayment.getPymntMeansCodeName());
			// ${배송비 결제금액}
			map.put("dlvyPymntAmt", UtilsText.concat(formatter.format(ocAsPayment.getPymntAmt()), "원"));
			mailTempMap.put("dlvyPymntAmt", UtilsText.concat(formatter.format(ocAsPayment.getPymntAmt()), "원"));

			ocAsPayment.setStrPymntDtm(UtilsDate.formatter(CMM_DATE_FORM_HM, ocAsPayment.getDbPymntCmlptDate()));

			// ${배송비 모델}
			mailTempMap.put("dlvyAmtPymnt", ocAsPayment);
		}

		// ${랜딩 URL}
		String landingUrl = "";
		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {

			if (detail.getAsGbnCode().equals(CommonCode.AS_GBN_CODE_REVIEW)) {
				// 심의접수 메세지 번호
				messageVO.setMesgId(MessageCode.MESG_ID_REVIEW_ACCEPT_BY_ADMIN_ART);
			} else if (detail.getAsGbnCode().equals(CommonCode.AS_GBN_CODE_REPAIR)) {
				// 수선접수 메세지 번호
				messageVO.setMesgId(MessageCode.MESG_ID_REPAIR_ACCEPT_BY_ADMIN_ART);
			}

			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_AS_ACCEPT_BY_ADMIN_ART);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, AS_DETAIL_URL, detail.getAsAcceptNo());
			map.put("landingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_FO, AS_DETAIL_URL, detail.getAsAcceptNo());
			mailTempMap.put("landingUrl", landingUrl);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {

			if (detail.getAsGbnCode().equals(CommonCode.AS_GBN_CODE_REVIEW)) {
				// 심의접수 메세지 번호
				messageVO.setMesgId(MessageCode.MESG_ID_REVIEW_ACCEPT_BY_ADMIN_OTS);

			} else if (detail.getAsGbnCode().equals(CommonCode.AS_GBN_CODE_REPAIR)) {
				// 수선접수 메세지 번호
				messageVO.setMesgId(MessageCode.MESG_ID_REPAIR_ACCEPT_BY_ADMIN_OTS);
			}

			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_AS_ACCEPT_BY_ADMIN_OTS);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, AS_DETAIL_URL, detail.getAsAcceptNo());
			map.put("landingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_FO, AS_DETAIL_URL, detail.getAsAcceptNo());
			mailTempMap.put("landingUrl", landingUrl);
		}

		// ${상품명}
		map.put("prdtName", prdt.getPrdtName());

		// 알림톡 등록
		messageVO.setMessageTemplateModel(map);
		try {
			messageService.setSendMessageProcess(messageVO);
		} catch (Exception e) {
			log.error("AS관리자접수 메세지 발송 실패 : " + e.getMessage());
		}

		/** 교환 메일은 ? */
		// 이미지경로
		mailTempMap.put("imgPath", Const.SERVICE_DOMAIN_ART_FO);

		// ${상품 정보} - mail
		mailTempMap.put("prdt", prdt);

		// 메일 발송
		mailTempVO.setMailTemplateModel(mailTempMap);
		try {
			mailService.sendMail(mailTempVO);
		} catch (Exception e) {
			log.error("AS관리자접수 메일 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 수선/심의 상품 수령 완료 (메세지 만)
	 * @Method Name : asReceiptComplete
	 * @Date : 2019. 7. 11.
	 * @Author : 이강수
	 * @param OcAsAccept
	 * @throws Exception
	 */
	public void asReceiptComplete(OcAsAccept ocAsAccept) throws Exception {

		Map<String, String> map = new HashMap<>();

		/** AS 상세조회 */
		Map<String, Object> asMap = this.getAsAcceptDetailInfo(ocAsAccept);
		// AS 마스터
		OcAsAccept detail = (OcAsAccept) asMap.get("asAcceptInfo");
		// AS 상품 리스트
		OcAsAcceptProduct prdt = (OcAsAcceptProduct) asMap.get("ocAsAcceptProductInfo");

		/**
		 * SMS알림톡 전송 set
		 */
		MessageVO messageVO = new MessageVO();

		this.setAsMemberInfoMesg(messageVO, map, detail);

		// ${상품명}
		map.put("prdtName", prdt.getPrdtName());

		// ${랜딩 URL}
		String landingUrl = "";
		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {

			if (detail.getAsGbnCode().equals(CommonCode.AS_GBN_CODE_REVIEW)) {
				// 심의접수 메세지 번호
				messageVO.setMesgId(MessageCode.MESG_ID_AS_REVIEW_RECEIPT_ART);
			} else if (detail.getAsGbnCode().equals(CommonCode.AS_GBN_CODE_REPAIR)) {
				// 수선접수 메세지 번호
				messageVO.setMesgId(MessageCode.MESG_ID_AS_REPAIR_RECEIPT_ART);
			}

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, AS_DETAIL_URL, detail.getAsAcceptNo());
			map.put("landingUrl", landingUrl);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {

			if (detail.getAsGbnCode().equals(CommonCode.AS_GBN_CODE_REVIEW)) {
				// 심의접수 메세지 번호
				messageVO.setMesgId(MessageCode.MESG_ID_AS_REVIEW_RECEIPT_OTS);
			} else if (detail.getAsGbnCode().equals(CommonCode.AS_GBN_CODE_REPAIR)) {
				// 수선접수 메세지 번호
				messageVO.setMesgId(MessageCode.MESG_ID_AS_REPAIR_RECEIPT_OTS);
			}

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, AS_DETAIL_URL, detail.getAsAcceptNo());
			map.put("landingUrl", landingUrl);
		}

		// 알림톡 등록
		messageVO.setMessageTemplateModel(map);
		try {
			messageService.setSendMessageProcess(messageVO);
		} catch (Exception e) {
			log.error("AS상품수령완료 메세지 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 심의 불가 반송 (메세지 만)
	 * @Method Name : asJudgeImpsbltSendBack
	 * @Date : 2019. 7. 11.
	 * @Author : 이강수
	 * @param OcAsAccept
	 * @throws Exception
	 */
	public void asJudgeImpsbltSendBack(OcAsAccept ocAsAccept) throws Exception {

		Map<String, String> map = new HashMap<>();

		/** AS 상세조회 */
		Map<String, Object> asMap = this.getAsAcceptDetailInfo(ocAsAccept);
		// AS 마스터
		OcAsAccept detail = (OcAsAccept) asMap.get("asAcceptInfo");
		// AS 상품 리스트
		OcAsAcceptProduct prdt = (OcAsAcceptProduct) asMap.get("ocAsAcceptProductInfo");

		/**
		 * SMS알림톡 전송 set
		 */
		MessageVO messageVO = new MessageVO();

		this.setAsMemberInfoMesg(messageVO, map, detail);

		// ${asGbnCodeName}
		map.put("asGbnCodeName", "심의");
		// ${상품명}
		map.put("prdtName", prdt.getPrdtName());

		// ${택배사명}
		if (UtilsObject.isEmpty(prdt.getLogisVndrCodeName()) && UtilsObject.isEmpty(prdt.getWaybilNoText())) {
			map.put("logisVndrName", "");
			map.put("waybilNoText", "");
		} else {
			map.put("logisVndrName", prdt.getLogisVndrCodeName());
			map.put("waybilNoText", prdt.getWaybilNoText());
		}

		// ${랜딩 URL}
		String landingUrl = "";
		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {
			// 메세지 번호
			messageVO.setMesgId(MessageCode.MESG_ID_AS_IMPSBLT_SEND_BACK_ART);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, AS_DETAIL_URL, detail.getAsAcceptNo());
			map.put("landingUrl", landingUrl);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {
			// 메세지 번호
			messageVO.setMesgId("OTSA060");

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, AS_DETAIL_URL, detail.getAsAcceptNo());
			map.put("landingUrl", landingUrl);
		}

		// 알림톡 등록
		messageVO.setMessageTemplateModel(map);
		try {
			messageService.setSendMessageProcess(messageVO);
		} catch (Exception e) {
			log.error("AS심의불가반송 메세지 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 수선비용 발생 (메세지만)
	 * @Method Name : asRepairAmtOccurrence
	 * @Date : 2019. 7. 11.
	 * @Author : 이강수
	 * @param OcAsAccept
	 * @throws Exception
	 */
	public void asRepairAmtOccurrence(OcAsAccept ocAsAccept) throws Exception {

		Map<String, String> map = new HashMap<>();

		/** AS 상세조회 */
		Map<String, Object> asMap = this.getAsAcceptDetailInfo(ocAsAccept);
		// AS 마스터
		OcAsAccept detail = (OcAsAccept) asMap.get("asAcceptInfo");
		// AS 상품 리스트
		// OcAsAcceptProduct prdt = (OcAsAcceptProduct)
		// asMap.get("ocAsAcceptProductInfo");

		/**
		 * SMS알림톡 전송 set
		 */
		MessageVO messageVO = new MessageVO();

		this.setAsMemberInfoMesg(messageVO, map, detail);

		// ${발생사유}
		map.put("ocrncRsnName", detail.getAsProcTypeDtlCodeName());

		if (detail.getTotalPymntAmt().equals(0)) {
			// ${수선비용}
			map.put("repairAmt", "0원");
		} else {
			DecimalFormat formatter = new DecimalFormat(Const.DEFAULT_NUMBER_PATTERN_COMMA_THREE_DIGIT);
			// ${수선비용}
			map.put("repairAmt", UtilsText.concat(formatter.format(detail.getTotalPymntAmt()), "원"));
		}

		// ${랜딩 URL}
		String landingUrl = "";
		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {
			// 메세지 번호
			messageVO.setMesgId(MessageCode.MESG_ID_AS_REPAIR_AMT_OCCURRENCE_ART);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, AS_DETAIL_URL, detail.getAsAcceptNo());
			map.put("landingUrl", landingUrl);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {
			// 메세지 번호
			messageVO.setMesgId(MessageCode.MESG_ID_AS_REPAIR_AMT_OCCURRENCE_OTS);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, AS_DETAIL_URL, detail.getAsAcceptNo());
			map.put("landingUrl", landingUrl);
		}

		// 알림톡 등록
		messageVO.setMessageTemplateModel(map);
		try {
			messageService.setSendMessageProcess(messageVO);
		} catch (Exception e) {
			log.error("AS수선비용발생 메세지 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 수선 배송 처리
	 * @Method Name : asRepairDeliveryProc
	 * @Date : 2019. 7. 11.
	 * @Author : 이강수
	 * @param OcAsAccept
	 * @throws Exception
	 */
	public void asRepairDeliveryProc(OcAsAccept ocAsAccept) throws Exception {

		Map<String, String> map = new HashMap<>();
		Map<String, Object> mailTempMap = new HashMap<>();

		// 이메일 상단 URL
		mailTempMap.put(ABC_URL, Const.SERVICE_DOMAIN_ART_FO);
		mailTempMap.put(OTS_URL, Const.SERVICE_DOMAIN_OTS_FO);

		/** AS 상세조회 */
		Map<String, Object> asMap = this.getAsAcceptDetailInfo(ocAsAccept);
		// AS 마스터
		OcAsAccept detail = (OcAsAccept) asMap.get("asAcceptInfo");
		// AS 상품 리스트
		OcAsAcceptProduct prdt = (OcAsAcceptProduct) asMap.get("ocAsAcceptProductInfo");

		if (UtilsObject.isEmpty(prdt.getImageUrl())) {
			prdt.setImageUrl(NO_IMAGE_URL);
		}
		if (UtilsObject.isEmpty(prdt.getAltrnText())) {
			prdt.setAltrnText("");
		}

		/**
		 * SMS알림톡 전송 set & 메일 발송 set
		 */
		MessageVO messageVO = new MessageVO();
		MailTemplateVO mailTempVO = new MailTemplateVO();

		this.setAsMemberInfoMesgMail(messageVO, mailTempVO, map, mailTempMap, detail);

		// ${상품명}
		if (UtilsObject.isEmpty(prdt.getPrdtName())) {
			map.put("prdtName", "");
		} else {
			map.put("prdtName", prdt.getPrdtName());
		}
		// ${상품 정보} - mail
		mailTempMap.put("prdt", prdt);

		// ${배송방법명}
		mailTempMap.put("dlvyTypeCodeName", "일반택배");
		// ${택배사명}
		if (UtilsObject.isEmpty(prdt.getLogisVndrCodeName()) && UtilsObject.isEmpty(prdt.getWaybilNoText())) {
			map.put("logisVndrName", "");
			map.put("waybilNoText", "");
			// ${택배사명}
			mailTempMap.put("logisVndrName", "");
			// ${운송장번호}
			mailTempMap.put("waybilNoText", "");
		} else {
			map.put("logisVndrName", prdt.getLogisVndrCodeName());
			map.put("waybilNoText", prdt.getWaybilNoText());
			// ${택배사명}
			mailTempMap.put("logisVndrName", prdt.getLogisVndrCodeName());
			// ${운송장번호}
			mailTempMap.put("waybilNoText", prdt.getWaybilNoText());
		}

		// ${랜딩 URL}
		String landingUrl = "";
		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {
			// 메세지 번호
			messageVO.setMesgId(MessageCode.MESG_ID_AS_REPAIR_DLVY_PROC_ART);
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_AS_REPAIR_DLVY_PROC_ART);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, AS_DETAIL_URL, detail.getAsAcceptNo());
			map.put("landingUrl", landingUrl);

			map.put("logisVndrLandingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_FO, AS_DETAIL_URL, detail.getAsAcceptNo());
			mailTempMap.put("landingUrl", landingUrl);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {
			// 메세지 번호
			messageVO.setMesgId(MessageCode.MESG_ID_AS_REPAIR_DLVY_PROC_OTS);
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_AS_REPAIR_DLVY_PROC_OTS);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, AS_DETAIL_URL, detail.getAsAcceptNo());
			map.put("landingUrl", landingUrl);

			map.put("logisVndrLandingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_FO, AS_DETAIL_URL, detail.getAsAcceptNo());
			mailTempMap.put("landingUrl", landingUrl);
		}

		// 알림톡 등록
		messageVO.setMessageTemplateModel(map);
		try {
			messageService.setSendMessageProcess(messageVO);
		} catch (Exception e) {
			log.error("AS상품배송 메세지 발송 실패 : " + e.getMessage());
		}

		// 메일 발송
		mailTempVO.setMailTemplateModel(mailTempMap);
		try {
			mailService.sendMail(mailTempVO);
		} catch (Exception e) {
			log.error("AS상품배송 메일 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 수선 불가 반송
	 * @Method Name : asRepairImpsbltSendBack
	 * @Date : 2019. 7. 11.
	 * @Author : 이강수
	 * @param OcAsAccept
	 * @throws Exception
	 */
	public void asRepairImpsbltSendBack(OcAsAccept ocAsAccept) throws Exception {

		Map<String, String> map = new HashMap<>();
		Map<String, Object> mailTempMap = new HashMap<>();

		// 이메일 상단 URL
		mailTempMap.put(ABC_URL, Const.SERVICE_DOMAIN_ART_FO);
		mailTempMap.put(OTS_URL, Const.SERVICE_DOMAIN_OTS_FO);

		/** AS 상세조회 */
		Map<String, Object> asMap = this.getAsAcceptDetailInfo(ocAsAccept);
		// AS 마스터
		OcAsAccept detail = (OcAsAccept) asMap.get("asAcceptInfo");
		// AS 상품 리스트
		OcAsAcceptProduct prdt = (OcAsAcceptProduct) asMap.get("ocAsAcceptProductInfo");

		if (UtilsObject.isEmpty(prdt.getImageUrl())) {
			prdt.setImageUrl(NO_IMAGE_URL);
		}
		if (UtilsObject.isEmpty(prdt.getAltrnText())) {
			prdt.setAltrnText("");
		}

		/**
		 * SMS알림톡 전송 set & 메일 발송 set
		 */
		MessageVO messageVO = new MessageVO();
		MailTemplateVO mailTempVO = new MailTemplateVO();

		this.setAsMemberInfoMesgMail(messageVO, mailTempVO, map, mailTempMap, detail);

		// ${asGbnCodeName}
		map.put("asGbnCodeName", "수선");

		// ${상품명}
		if (UtilsObject.isEmpty(prdt.getPrdtName())) {
			map.put("prdtName", "");
		} else {
			map.put("prdtName", prdt.getPrdtName());
		}
		// ${상품 정보} - mail
		mailTempMap.put("prdt", prdt);

		// ${배송방법명}
		mailTempMap.put("dlvyTypeCodeName", "일반택배");
		// ${택배사명}
		if (UtilsObject.isEmpty(prdt.getLogisVndrCodeName()) && UtilsObject.isEmpty(prdt.getWaybilNoText())) {
			map.put("logisVndrName", "");
			map.put("waybilNoText", "");
			// ${택배사명}
			mailTempMap.put("logisVndrName", "");
			// ${운송장번호}
			mailTempMap.put("waybilNoText", "");
		} else {
			map.put("logisVndrName", prdt.getLogisVndrCodeName());
			map.put("waybilNoText", prdt.getWaybilNoText());
			// ${택배사명}
			mailTempMap.put("logisVndrName", prdt.getLogisVndrCodeName());
			// ${운송장번호}
			mailTempMap.put("waybilNoText", prdt.getWaybilNoText());
		}

		// ${랜딩 URL}
		String landingUrl = "";
		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {
			// 메세지 번호
			messageVO.setMesgId(MessageCode.MESG_ID_AS_IMPSBLT_SEND_BACK_ART);
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_AS_REPAIR_IMPSBLT_SEND_BACK_ART);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, AS_DETAIL_URL, detail.getAsAcceptNo());
			map.put("landingUrl", landingUrl);

			map.put("logisVndrLandingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_FO, AS_DETAIL_URL, detail.getAsAcceptNo());
			mailTempMap.put("landingUrl", landingUrl);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {
			// 메세지 번호
			messageVO.setMesgId("OTSA060");
			// 이메일 번호
			mailTempVO.setMailTemplateId(MailCode.MAIL_ID_AS_REPAIR_IMPSBLT_SEND_BACK_OTS);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, AS_DETAIL_URL, detail.getAsAcceptNo());
			map.put("landingUrl", landingUrl);

			map.put("logisVndrLandingUrl", landingUrl);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_FO, AS_DETAIL_URL, detail.getAsAcceptNo());
			mailTempMap.put("landingUrl", landingUrl);
		}

		// 알림톡 등록
		messageVO.setMessageTemplateModel(map);
		try {
			messageService.setSendMessageProcess(messageVO);
		} catch (Exception e) {
			log.error("AS수선불가반송 메세지 발송 실패 : " + e.getMessage());
		}

		// 메일 발송
		mailTempVO.setMailTemplateModel(mailTempMap);
		try {
			mailService.sendMail(mailTempVO);
		} catch (Exception e) {
			log.error("AS수선불가반송 메일 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 메세지 보내는이, 받는이 set
	 * @Method Name : setAsMemberInfoMesg
	 * @Date : 2019. 7. 30.
	 * @Author : 이강수
	 * @param
	 * @throws Exception
	 */
	public void setAsMemberInfoMesg(MessageVO messageVO, Map<String, String> map, OcAsAccept detail) throws Exception {
		// 사이트
		messageVO.setSiteNo(detail.getSiteNo());
		// 즉시발송여부
		messageVO.setReal(true);
		// 관리자 발송여부
		messageVO.setAdminSendYn(Const.BOOLEAN_TRUE);

		// LMS 강제발송
		// messageVO.setSendTypeCode(CommonCode.SEND_TYPE_CODE_LMS);

		// 머릿말
		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {
			messageVO.setMesgTitleText("[A-RT]에서 보내드립니다.");
			messageVO.setFailedSubject("[A-RT]에서 보내드립니다.");
			// 발신자
			messageVO.setSndrName(Const.SYS_SENDER_MESSAGE_NAME);
			// 발신번호
			messageVO.setSendTelNoText(Const.SYS_SENDER_MESSAGE_NUMBER);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {
			messageVO.setMesgTitleText("[ON THE SPOT]에서 보내드립니다.");
			messageVO.setFailedSubject("[ON THE SPOT]에서 보내드립니다.");
			// 발신자
			messageVO.setSndrName(Const.SYS_OTS_SENDER_MESSAGE_NAME);
			// 발신번호
			messageVO.setSendTelNoText(Const.SYS_OTS_SENDER_MESSAGE_NUMBER);
		}

		messageVO.setMesgContText("");

		MbMember memberInfo = memberService.getMember(detail.getMemberNo());
		// 수신자명
		messageVO.setRcvrName(memberInfo.getMemberName());
		// 수신번호
		messageVO.setRecvTelNoText(memberInfo.getHdphnNoText());
		// ${수신자명}
		map.put("memberName", memberInfo.getMemberName());

		// ${주문번호}
		map.put("orderNo", detail.getOrgOrderNo());
		// ${접수번호}
		map.put("asAcceptNo", detail.getAsAcceptNo());
	}

	/**
	 * @Desc : 메세지, 메일 보내는이, 받는이 set
	 * @Method Name : setAsMemberInfoMesgMail
	 * @Date : 2019. 7. 30.
	 * @Author : 이강수
	 * @param
	 * @throws Exception
	 */
	public void setAsMemberInfoMesgMail(MessageVO messageVO, MailTemplateVO mailTempVO, Map<String, String> map,
			Map<String, Object> mailTempMap, OcAsAccept detail) throws Exception {
		// 사이트
		messageVO.setSiteNo(detail.getSiteNo());
		// 즉시발송여부
		messageVO.setReal(true);
		mailTempVO.setRealTime(true);
		// 관리자 발송여부
		messageVO.setAdminSendYn(Const.BOOLEAN_TRUE);

		// LMS 강제발송
		// messageVO.setSendTypeCode(CommonCode.SEND_TYPE_CODE_LMS);

		// 머릿말
		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {
			messageVO.setMesgTitleText("[A-RT]에서 보내드립니다.");
			messageVO.setFailedSubject("[A-RT]에서 보내드립니다.");
			// 발신자
			messageVO.setSndrName(Const.SYS_SENDER_MESSAGE_NAME);
			// 발신번호
			messageVO.setSendTelNoText(Const.SYS_SENDER_MESSAGE_NUMBER);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {
			messageVO.setMesgTitleText("[ON THE SPOT]에서 보내드립니다.");
			messageVO.setFailedSubject("[ON THE SPOT]에서 보내드립니다.");
			// 발신자
			messageVO.setSndrName(Const.SYS_OTS_SENDER_MESSAGE_NAME);
			// 발신번호
			messageVO.setSendTelNoText(Const.SYS_OTS_SENDER_MESSAGE_NUMBER);
		}

		messageVO.setMesgContText("");

		MbMember memberInfo = memberService.getMember(detail.getMemberNo());
		// 수신자명
		messageVO.setRcvrName(memberInfo.getMemberName());
		mailTempVO.setReceiverName(memberInfo.getMemberName());
		// 수신번호
		messageVO.setRecvTelNoText(memberInfo.getHdphnNoText());
		mailTempVO.setReceiverEmail(memberInfo.getEmailAddrText());
		// ${수신자명}
		map.put("memberName", memberInfo.getMemberName());
		mailTempMap.put("memberName", memberInfo.getMemberName());

		// ${주문번호}
		map.put("orderNo", detail.getOrgOrderNo());
		// ${접수번호}
		map.put("asAcceptNo", detail.getAsAcceptNo());
		mailTempMap.put("asAcceptNo", detail.getAsAcceptNo());

		// 신청 날짜
		mailTempMap.put("asAcceptDtm", detail.getAsAcceptDate().substring(0, 4) + "년 "
				+ detail.getAsAcceptDate().substring(5, 7) + "월 " + detail.getAsAcceptDate().substring(8, 10) + "일");

		// modDtm날짜
		if (UtilsObject.isEmpty(detail.getStrModDtm())) {
			mailTempMap.put("modDtm",
					detail.getAsAcceptDate().substring(0, 4) + "년 " + detail.getAsAcceptDate().substring(5, 7) + "월 "
							+ detail.getAsAcceptDate().substring(8, 10) + "일");
		} else {
			mailTempMap.put("modDtm", detail.getStrModDtm().substring(0, 4) + "년 "
					+ detail.getStrModDtm().substring(5, 7) + "월 " + detail.getStrModDtm().substring(8, 10) + "일");
		}
	}

	/**
	 * @Desc :AS접수 상세 조회 - backend
	 * @Method Name : getAsAcceptDetailInfo
	 * @Date : 2019. 7. 16.
	 * @Author : 이강수
	 * @param ocAsAccept
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getAsAcceptDetailInfo(OcAsAccept ocAsAccept) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		// AS 상세 조회
		OcAsAccept asAcceptInfo = ocAsAcceptDao.selectOcAsAcceptDetailInfoBackend(ocAsAccept);
		// 상세주소 - 이스케이프로 변환되어 DB에 들어있는 데이터 가져오기
		asAcceptInfo.setBuyerDtlAddrText(UtilsText.unescapeXss(asAcceptInfo.getBuyerDtlAddrText()));
		asAcceptInfo.setRcvrDtlAddrText(UtilsText.unescapeXss(asAcceptInfo.getRcvrDtlAddrText()));

		resultMap.put("asAcceptInfo", asAcceptInfo);

		// AS 상품 조회
		OcAsAcceptProduct ocAsAcceptProductParam = new OcAsAcceptProduct();
		ocAsAcceptProductParam.setAsAcceptNo(ocAsAccept.getAsAcceptNo());
		ocAsAcceptProductParam.setAsAcceptPrdtSeq(asAcceptInfo.getAsAcceptPrdtSeq());
		OcAsAcceptProduct ocAsAcceptProduct = ocAsAcceptProductDao
				.selectAsAcceptProductDetailInfoBackend(ocAsAcceptProductParam);
		resultMap.put("ocAsAcceptProductInfo", ocAsAcceptProduct);

		// 결제정보 조회
		OcAsPayment ocAsPaymentParam = new OcAsPayment();
		ocAsPaymentParam.setAsAcceptNo(ocAsAccept.getAsAcceptNo());
		ocAsPaymentParam.setAsPymntSeq(asAcceptInfo.getAsAcceptPrdtSeq());
		OcAsPayment ocAsPayment = ocAsPaymentDao.selectAsPymntDetailInfoBackend(ocAsPaymentParam);
		resultMap.put("ocAsPaymentInfo", ocAsPayment);

		return resultMap;
	}
}
