package kr.co.abcmart.bo.product.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.product.model.master.PdGiftCard;
import kr.co.abcmart.bo.product.service.GiftCardService;
import kr.co.abcmart.bo.product.vo.PdGiftCardSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 기프트카드 컨트롤러
 * @FileName : GiftCardController.java
 * @Project : abc.bo
 * @Date : 2019. 4. 12.
 * @Author : hsjhsj19
 */
@Slf4j
@Controller
@RequestMapping("product/giftCard")
public class GiftCardController extends BaseController {

	@Autowired
	private GiftCardService giftCardService;

	@Autowired
	private CommonCodeService commonCodeService;

	private static final String GIFT_CARD_TYPE_CODE = "GIFT_CARD_TYPE_CODE";

	/**
	 * @Desc : 기프트카드 목록 페이지
	 * @Method Name : getList
	 * @Date : 2019. 4. 11.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public ModelAndView getList(Parameter<?> parameter) throws Exception {
		log.debug("기프트카드 목록 페이지");

		// 기프트카드유형코드
		String[] codeFields = { GIFT_CARD_TYPE_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);
		JSONObject codeCombo = pair.getFirst();
		parameter.addAttribute("codeCombo", codeCombo);

		return forward("/product/giftCard/giftCard-list");
	}

	/**
	 * @Desc : 기프트카드 목록 조회
	 * @Method Name : searchGiftCardList
	 * @Date : 2019. 4. 11.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping
	public void searchGiftCardList(Parameter<PdGiftCardSearchVO> parameter) throws Exception {
		log.debug("기프트카드 목록 조회");

		Pageable<PdGiftCardSearchVO, PdGiftCard> pageable = new Pageable<PdGiftCardSearchVO, PdGiftCard>(parameter);
		Page<PdGiftCard> giftCardList = this.giftCardService.selectGiftCard(pageable);

		this.writeJson(parameter, giftCardList.getGrid());
	}

	/**
	 * @Desc : 기프트카드 상세 조회
	 * @Method Name : getDetail
	 * @Date : 2019. 4. 11.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("detail")
	public ModelAndView getDetail(Parameter<PdGiftCard> parameter) throws Exception {
		log.debug("기프트카드 상세 조회");

		PdGiftCard giftCard = parameter.get();
		if (!UtilsText.isBlank(giftCard.getGiftCardNo())) {
			parameter.addAttribute("giftCard", this.giftCardService.searchGiftCardByPrimaryKey(giftCard));
		}

		return forward("/product/giftCard/giftCard-detail-popup");
	}

	/**
	 * @Desc : 기프트카드 등록
	 * @Method Name : saveGiftCard
	 * @Date : 2019. 4. 11.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("save")
	public void saveGiftCard(Parameter<PdGiftCard> parameter) throws Exception {
		parameter.get().setTypeGbn("save");
		parameter.validate();

		this.writeJson(parameter, this.giftCardService.saveGiftCard(parameter.get()));
	}

	/**
	 * @Desc : 기프트카드 수정
	 * @Method Name : modifyGiftCard
	 * @Date : 2019. 4. 11.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("modify")
	public void modifyGiftCard(Parameter<PdGiftCard> parameter) throws Exception {
		parameter.get().setTypeGbn("modify");
		parameter.validate();

		this.writeJson(parameter, this.giftCardService.saveGiftCard(parameter.get()));
	}
}
