package kr.co.abcmart.bo.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.member.model.master.MbMemberGiftCard;
import kr.co.abcmart.bo.member.service.GiftCardPurchaseService;
import kr.co.abcmart.bo.order.model.master.OcGiftCardOrder;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.interfaces.module.payment.nice.model.BalanceRequest;

@Controller
@RequestMapping("member")
public class GiftCardPurchaseController extends BaseController {
	@Autowired
	private GiftCardPurchaseService giftCardPurchaseService;

	@Autowired
	private CommonCodeService commonCodeService; // 코드

	/**
	 * @Desc : 기프트카드 회원 보유 목록 조회
	 * @Method Name : readMemberInfoTab
	 * @Date : 2019. 4. 22.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/giftcard/read-giftcard-info-tab")
	public ModelAndView readMembergiftCardInfoTab(Parameter<MbMemberGiftCard> parameter) throws Exception {
		MbMemberGiftCard mbMemberGiftCard = parameter.get();

		Map<String, Object> dataMap = new HashMap<String, Object>();

		// 회원 기프트 보유 카드 조회
		dataMap = giftCardPurchaseService.getPossesionGiftCard(mbMemberGiftCard);

		// Grid 코드정보
		String[] codeFields = { CommonCode.GIFT_CARD_ORDER_TYPE_CODE // 기프트타입 주문
				, CommonCode.PYMNT_MEANS_CODE // 결제구분
		};
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();

		parameter.addAttribute("codeCombo", pair.getFirst()); // code Combo
		parameter.addAttribute("codeList", pair.getSecond()); // code list
		// 기프트 카드 타입 주문 유형, 결제 수단 코드
		parameter.addAttribute("giftCardOrderTypeCode", codeList.get(CommonCode.GIFT_CARD_ORDER_TYPE_CODE));
		parameter.addAttribute("pymntMeansCode", codeList.get(CommonCode.PYMNT_MEANS_CODE));
		parameter.addAttribute("mbMemberGiftCardData", dataMap.get("mbMemberGiftCardData"));

		return forward("/member/read-giftcardpurchase-info-tab");
	}

	/**
	 * @Desc : 기프트카드 리스트
	 * @Method Name : readMemberGiftCardList
	 * @Date : 2019. 4. 22.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/giftcard/read-member-giftcard-list")
	public void readMemberGiftCardList(Parameter<MbMemberGiftCard> parameter) throws Exception {
		// 보유카드 정보
		MbMemberGiftCard mbMemberGiftCard = parameter.get();
		// 보유카드를 선택이 없을시
		// mbMemberGiftCard.validate();

		Pageable<MbMemberGiftCard, OcGiftCardOrder> giftcardVOPageble = new Pageable<>(parameter);

		Page<OcGiftCardOrder> page = giftCardPurchaseService.getMemberGiftCardList(giftcardVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 기프트 카드 잔액조회
	 * @Method Name : readGiftCardBalance
	 * @Date : 2019. 5. 3.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/giftcard/read-giftcard-balance")
	public void readGiftCardBalance(Parameter<MbMemberGiftCard> parameter) throws Exception {
		// 보유카드 정보
		MbMemberGiftCard mbMemberGiftCard = parameter.get();

		BalanceRequest balanceParam = new BalanceRequest();
		balanceParam.setHistory(true);
		balanceParam.setCardNo(mbMemberGiftCard.getCardNoText());

		Map<String, Object> resultMap = giftCardPurchaseService.getMyGiftCardBalance(balanceParam);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 기프트카드 결제취소
	 * @Method Name : setGiftCardHistoryCancel
	 * @Date : 2019. 5. 3.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/giftcard/set-giftcard-hisoty-cancel")
	public void setGiftCardHistoryCancel(Parameter<OcGiftCardOrder> parameter) throws Exception {
		OcGiftCardOrder ocGiftCardOrder = parameter.get();

		Map<String, Object> resultMap = giftCardPurchaseService.setGiftCardHistoryCancel(ocGiftCardOrder);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc :MMS 저재 전송
	 * @Method Name : setGiftCardResend
	 * @Date : 2019. 7. 25.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/giftcard/set-giftcard-mms-resend")
	public void setGiftCardMmsResend(Parameter<OcGiftCardOrder> parameter) throws Exception {
		OcGiftCardOrder ocGiftCardOrder = parameter.get();

		Map<String, Object> resultMap = giftCardPurchaseService.setGiftCardMmsResend(ocGiftCardOrder);

		writeJson(parameter, resultMap);
	}
}
