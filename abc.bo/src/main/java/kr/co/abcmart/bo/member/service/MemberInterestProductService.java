package kr.co.abcmart.bo.member.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.cmm.model.master.CmMessageTemplate;
import kr.co.abcmart.bo.cmm.service.MessageService;
import kr.co.abcmart.bo.cmm.service.MsgTemplateService;
import kr.co.abcmart.bo.cmm.vo.MessageVO;
import kr.co.abcmart.bo.member.model.master.MbMemberInterestProduct;
import kr.co.abcmart.bo.member.repository.master.MbMemberInterestProductDao;
import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.constant.MessageCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberInterestProductService {

	@Autowired
	private MbMemberInterestProductDao mbMemberInterestProductDao;

	@Autowired
	private MessageService messageService;

	@Autowired
	private MsgTemplateService msgTemplateService;

	/**
	 * @Desc : 재입고 알림 서비스 리스트 조회
	 * @Method Name : getWarehousingAlertList
	 * @Date : 2019. 7. 31.
	 * @Author : 이가영
	 * @param parameter
	 * @return
	 */
	public Page<MbMemberInterestProduct> getWarehousingAlertList(Parameter<MbMemberInterestProduct> parameter)
			throws Exception {

		Pageable<MbMemberInterestProduct, MbMemberInterestProduct> pageable = new Pageable<>(parameter);

		int count = mbMemberInterestProductDao.selectWarehousingAlertCount(pageable);

		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(mbMemberInterestProductDao.selectWarehousingAlertList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 임의발송·일괄처리
	 * @Method Name : msgProcess
	 * @Date : 2019. 7. 31.
	 * @Author : 이가영
	 * @param mbMemberInterestProduct
	 */
	public void setMsgProcess(MbMemberInterestProduct[] mbMemberInterestProduct) throws Exception {

		for (MbMemberInterestProduct item : mbMemberInterestProduct) {
			if (item.getMemberNo() != null) {
				// 재입고 알림 서비스 메시지 발송
				this.setSendMessage(item);
				// 알림발송 처리
				mbMemberInterestProductDao.updateMemberReStockAlarm(item);
			}
		}
	}

	/**
	 * @Desc : 재입고 알림 서비스 메시지 발송
	 * @Method Name : sendMessage
	 * @Date : 2019. 7. 31.
	 * @Author : 이가영
	 * @param data
	 */
	public void setSendMessage(MbMemberInterestProduct data) throws Exception {

		Map<String, String> map = new HashMap<>();
		MessageVO messageVO = new MessageVO();

		messageVO.setSiteNo(data.getSiteNo()); // 사이트
		messageVO.setSndrName(Const.SYS_SENDER_MESSAGE_NAME); // 발신자
		messageVO.setSendTelNoText(Const.SYS_SENDER_MESSAGE_NUMBER); // 발신번호
		messageVO.setReal(true); // 즉시발송여부
		messageVO.setRcvrName(data.getMemberName()); // 수신자명
		messageVO.setRecvTelNoText(data.getHdphnNoText()); // 수신번호

		// 메세지 번호
		// ${랜딩 URL}
		// 사이트 : 통합몰 (임시)
		String mesgId;
		String prdtNo = data.getPrdtNo();
		String pageUrl = "/product?prdtNo=";
		String landingUrl;

		if (UtilsText.equals(data.getSiteNo(), Const.SITE_NO_OTS)) { // OTS
			// landingUrl = UtilsText.concat("https://m.dev.onthespot.co.kr", pageUrl,
			// prdtNo);
			landingUrl = UtilsText.concat(Config.getString("service.domain.ots.fo"), pageUrl, prdtNo);

			mesgId = MessageCode.PRODUCT_RESTOCK_OTS;
		} else { // 통합몰
			// landingUrl = UtilsText.concat("https://m.dev.a-rt.com", pageUrl, prdtNo);
			landingUrl = UtilsText.concat(Config.getString("service.domain.art.fo"), pageUrl, prdtNo);
			mesgId = MessageCode.PRODUCT_RESTOCK_ABC;
		}

		CmMessageTemplate cmMessageTemplate = msgTemplateService.getMessageTemplateByMesgId(mesgId);

		map.put("memberName", data.getMemberName()); // 수신자명
		map.put("brandName", data.getBrandName()); // 브랜드명
		map.put("prdtName", data.getPrdtName()); // 상품명
		map.put("prdtOptnNo", data.getPrdtOptnNo()); // 옵션명
		map.put("landingUrlMo", landingUrl);
		messageVO.setMemberNo(data.getMemberNo());
		messageVO.setMesgId(mesgId);
		messageVO.setMesgContText(cmMessageTemplate.getMesgContText());
		messageVO.setMessageTemplateModel(map);

		// 알림톡/SMS 발송
		messageService.setSendMessageProcess(messageVO);
	}

	/**
	 * @Desc : 재입고 알림 서비스 신청 취소
	 * @Method Name : setAlertCancel
	 * @Date : 2019. 7. 31.
	 * @Author : 이가영
	 * @param mbMemberInterestProduct
	 */
	public void setAlertCancel(MbMemberInterestProduct[] mbMemberInterestProduct) throws Exception {

		for (MbMemberInterestProduct item : mbMemberInterestProduct) {

			if (item.getMemberNo() != null) {
				mbMemberInterestProductDao.updateMemberReStockAlarmCancel(item);
			}
		}
	}

}
