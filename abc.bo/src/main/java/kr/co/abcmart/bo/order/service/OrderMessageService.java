package kr.co.abcmart.bo.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.cmm.service.MailService;
import kr.co.abcmart.bo.cmm.service.MessageService;
import kr.co.abcmart.bo.cmm.vo.MailTemplateVO;
import kr.co.abcmart.bo.cmm.vo.MessageVO;
import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.bo.order.model.master.OcOrderProduct;
import kr.co.abcmart.bo.order.repository.master.OcOrderDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderProductDao;
import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.bo.system.repository.master.SyAdminDao;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.constant.MailCode;
import kr.co.abcmart.constant.MessageCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 주문 이메일, 메세지 발송 담당하는 서비스
 * @FileName : orderMessageService.java
 * @Project : abc.backend
 * @Date : 2019. 7. 26.
 * @Author : lee
 */
@Slf4j
@Service
public class OrderMessageService {

	@Autowired
	private OcOrderDao ocOrderDao;
	@Autowired
	private OcOrderProductDao ocOrderProductDao;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private SyAdminDao syAdminDao;

	@Autowired
	private MailService mailService;
	@Autowired
	private MessageService messageService;

	private static final String ORDER_URL = "/mypage"; // 주문배송현황조회
	private static final String MO_ORDER_URL = "/mypage/order-main"; // MO 주문배송현황조회

	private static final String CMM_DATE_FORM_HM = "yyyy년 MM월 dd일 HH시 mm분";

	/**
	 * @Desc :배송지 정보 변경시 Email X SMS,알림톡 O
	 * @Method Name : orderDlvrChange
	 * @Date : 2019. 7. 29.
	 * @Author : lee
	 * @param ocOrder
	 * @throws Exception
	 */
	public void orderDlvrChange(OcOrder ocOrder) throws Exception {

		// sms 관련
		MessageVO messageVO = new MessageVO();
		Map<String, String> map = new HashMap<>();

		// email 관련
		MailTemplateVO mailTempVO = new MailTemplateVO();
		Map<String, Object> mailTempMap = new HashMap<>();

		// 1. 주문 마스터 정보 조회
		OcOrder ocOrderParam = new OcOrder();
		ocOrderParam.setOrderNo(ocOrder.getOrderNo());
		OcOrder ocOrderData = ocOrderDao.selectByPrimaryKey(ocOrderParam);

		// 2.주문 상품 정보 조회 ( 입점사 상품이 있을 경우 메일 전송)
		OcOrderProduct orderProduct = new OcOrderProduct();
		orderProduct.setOrderNo(ocOrder.getOrderNo());
		orderProduct.setMemberNo(ocOrder.getMemberNo());

		// 상품정보
		List<OcOrderProduct> productList = ocOrderProductDao.selectOrderProductAllByOrder(orderProduct); // 주문상품 정보

		// 업체상품기준 -전체
		List<OcOrderProduct> vendorProduct = productList.stream().filter(x -> "N".equals(x.getMmnyPrdtYn()))
				.collect(Collectors.toList());

		long vendorProductCnt = vendorProduct.stream().filter(x -> UtilsText.equals(x.getMmnyPrdtYn(), "N")).count();

		mailTempMap.put("boUrl", Const.SERVICE_DOMAIN_BO); // 이메일 상단 URL
		// 입점사별 담당자 메일 전송 처리
		if (vendorProductCnt > 0) {
			// 업체 기준으로 중복제거
			List<String> vendorList = vendorProduct.stream().map(OcOrderProduct::getVndrNo).distinct()
					.collect(Collectors.toCollection(ArrayList::new));

			for (int k = 0; k < vendorList.size(); k++) {

				String vendorNo = vendorList.get(k);

				VdVendor param = new VdVendor();
				param.setVndrNo(vendorNo);
				VdVendor vdVendor = vendorService.getVendorInfo(param);

				SyAdmin syAdmin = new SyAdmin();
				syAdmin.setVndrNo(vendorNo);

				SyAdmin syAdminData = syAdminDao.selectVndrNoInfo(syAdmin);

				// 수신자명
				mailTempVO.setReceiverName(syAdminData.getAdminName());
				// 수신번호
				mailTempVO.setReceiverEmail(syAdminData.getEmailAddrText());

				mailTempMap.put("vndrName", vdVendor.getVndrName());

				mailTempMap.put("orderNo", ocOrderData.getOrderNo());

				String orderDtm = UtilsDate.formatter(CMM_DATE_FORM_HM, ocOrderData.getOrderDtm());
				mailTempMap.put("orderDtm", orderDtm);

				// 이미지경로(FO URL)
				mailTempMap.put("abcUrl", Const.SERVICE_DOMAIN_ART_FO);
				// 이메일 번호 EA-05001
				mailTempVO.setMailTemplateId(MailCode.DLVY_CHANGE_EMAIL_ART);

				mailTempMap.put("landingUrl", Const.SERVICE_DOMAIN_BO);

				// 메일 발송
				mailTempVO.setMailTemplateModel(mailTempMap);
				try {
					mailService.sendMail(mailTempVO);
				} catch (Exception e) {
					log.error("배송지변경 메일 발송 실패 : " + e.getMessage());
				}
			}
		}

		/**
		 * SMS알림톡 전송 set
		 */
		// 사이트
		messageVO.setSiteNo(ocOrderData.getSiteNo());
		// 발신자
		messageVO.setSndrName(Const.SYS_SENDER_MESSAGE_NAME);
		// 발신번호
		messageVO.setSendTelNoText(Const.SYS_SENDER_MESSAGE_NUMBER);
		// messageVO.setSendTypeCode("10001");
		// 즉시발송여부
		messageVO.setReal(true);
		mailTempVO.setRealTime(true);

		// 수신자명
		messageVO.setRcvrName(ocOrderData.getBuyerName());
		// 수신번호
		messageVO.setRecvTelNoText(ocOrderData.getBuyerHdphnNoText());
		// ${주문자}
		map.put("memberName", ocOrderData.getBuyerName());

		// ${랜딩 URL}
		String landingUrl = "";
		if (ocOrderData.getSiteNo().equals(Const.SITE_NO_ART)) {
			// 메세지 번호
			messageVO.setMesgId(MessageCode.DLVY_CHANGE_MMS_ART);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, MO_ORDER_URL);
			map.put("landingUrl", landingUrl);

		} else if (ocOrderData.getSiteNo().equals(Const.SITE_NO_OTS)) {
			// 메세지 번호
			messageVO.setMesgId("OTSA018");

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, MO_ORDER_URL);
			map.put("landingUrl", landingUrl);
		}

		messageVO.setMessageTemplateModel(map);
		try {
			messageService.setSendMessageProcess(messageVO);
		} catch (Exception e) {
			log.error("배송지변경 메세지 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 매장픽업 재고 없음 (미출) Email X / SMS,알림톡 O
	 * @Method Name : pickupStoreOutOfStock
	 * @Date : 2019. 10. 07.
	 * @Author : 이강수
	 * @param OcOrderProduct
	 * @throws Exception
	 */
	public void pickupStoreOutOfStock(OcOrderProduct ocOrderProduct) throws Exception {

		// sms 관련
		MessageVO messageVO = new MessageVO();
		Map<String, String> map = new HashMap<>();

		// 1. 주문 마스터 정보 조회
		OcOrder ocOrderParam = new OcOrder();
		ocOrderParam.setOrderNo(ocOrderProduct.getOrderNo());
		OcOrder detail = ocOrderDao.selectByPrimaryKey(ocOrderParam);

		// 2.주문 상품 정보 조회
		OcOrderProduct orderProductParam = new OcOrderProduct();
		orderProductParam.setOrderNo(ocOrderProduct.getOrderNo());
		orderProductParam.setOrderPrdtSeq(ocOrderProduct.getOrderPrdtSeq());

		// 상품정보
		OcOrderProduct prdt = ocOrderProductDao.selectByPrimaryKey(orderProductParam); // 주문상품 정보

		/**
		 * SMS알림톡 전송 set
		 */
		// 사이트
		messageVO.setSiteNo(detail.getSiteNo());
		// 발신자
		messageVO.setSndrName(Const.SYS_SENDER_MESSAGE_NAME);
		// 발신번호
		messageVO.setSendTelNoText(Const.SYS_SENDER_MESSAGE_NUMBER);
		// 즉시발송여부
		messageVO.setReal(true);
		// 수신자명
		messageVO.setRcvrName(detail.getBuyerName());
		// 수신번호
		messageVO.setRecvTelNoText(detail.getBuyerHdphnNoText());

		// LMS 강제발송
		// messageVO.setSendTypeCode(CommonCode.SEND_TYPE_CODE_LMS);

		// ${주문자}
		map.put("memberName", detail.getBuyerName());
		// ${주문번호}
		map.put("orderNo", detail.getOrgOrderNo());

		// ${랜딩 URL}
		String landingUrl = "";
		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {
			// 메세지 번호
			messageVO.setMesgId(MessageCode.MESG_ID_STORE_PICKUP_DELIVERY_CANCEL_ART);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, MO_ORDER_URL);
			map.put("landingUrlMo", landingUrl);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {
			// 메세지 번호
			messageVO.setMesgId(MessageCode.MESG_ID_STORE_PICKUP_DELIVERY_CANCEL_OTS);

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, MO_ORDER_URL);
			map.put("landingUrlMo", landingUrl);
		}

		/** 메세지는 상품단위? */
		// ${상품명}
		map.put("prdtName", prdt.getPrdtName());
		// ${옵션명}
		map.put("optnName", prdt.getOptnName());

		// 내용 초기화
		messageVO.setMesgContText(null);
		messageVO.setFailedMsg(null);
		messageVO.setFailMesgContText(null);

		// 알림톡 등록
		messageVO.setMessageTemplateModel(map);
		try {
			messageService.setSendMessageProcess(messageVO);
		} catch (Exception e) {
			log.error("매장픽업재고없음 메세지 발송 실패 : " + e.getMessage());
		}
	}

	/**
	 * @Desc : 매장픽업 택배전환 신청 Email X / SMS,알림톡 O
	 * @Method Name : changePickupToDelivery
	 * @Date : 2019. 10. 10.
	 * @Author : 이강수
	 * @param OcOrder
	 * @throws Exception
	 */
	public void changePickupToDelivery(OcOrder ocOrder) throws Exception {

		// sms 관련
		MessageVO messageVO = new MessageVO();
		Map<String, String> map = new HashMap<>();

		// 1. 주문 마스터 정보 조회
		OcOrder ocOrderParam = new OcOrder();
		ocOrderParam.setOrderNo(ocOrder.getOrderNo());
		OcOrder detail = ocOrderDao.selectByPrimaryKey(ocOrderParam);

		/**
		 * SMS알림톡 전송 set
		 */
		// 사이트
		messageVO.setSiteNo(detail.getSiteNo());
		// 발신자
		messageVO.setSndrName(Const.SYS_SENDER_MESSAGE_NAME);
		// 발신번호
		messageVO.setSendTelNoText(Const.SYS_SENDER_MESSAGE_NUMBER);
		// 즉시발송여부
		messageVO.setReal(true);
		// 수신자명
		messageVO.setRcvrName(detail.getBuyerName());
		// 수신번호
		messageVO.setRecvTelNoText(detail.getBuyerHdphnNoText());

		// LMS 강제발송
		// messageVO.setSendTypeCode(CommonCode.SEND_TYPE_CODE_LMS);

		// ${주문자}
		map.put("memberName", detail.getBuyerName());

		// ${랜딩 URL}
		String landingUrl = "";
		if (detail.getSiteNo().equals(Const.SITE_NO_ART)) {
			// 메세지 번호
			messageVO.setMesgId("ART2014");

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_ART_MO, MO_ORDER_URL);
			map.put("landingUrl", landingUrl);

		} else if (detail.getSiteNo().equals(Const.SITE_NO_OTS)) {
			// 메세지 번호
			messageVO.setMesgId("OTSA130"); // 2020.05.28 : 새로 등록 

			landingUrl = UtilsText.concat(Const.SERVICE_DOMAIN_OTS_MO, MO_ORDER_URL);
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
			log.error("매장픽업택배전환 메세지 발송 실패 : " + e.getMessage());
		}
	}
}
