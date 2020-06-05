package kr.co.abcmart.bo.promotion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.promotion.model.master.EvCardBenefit;
import kr.co.abcmart.bo.promotion.service.CardBenefitService;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.constant.BaseConst;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/promotion/card-benefit")
public class CardBenefitController extends BaseController {

	@Autowired
	private CardBenefitService cardBenefitService;

	@Autowired
	private SiteService siteService;

	/**
	 * 
	 * @Desc : 카드사 혜택 관리
	 * @Method Name : cardBenefit
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ModelAndView cardBenefit(Parameter<EvCardBenefit> parameter) throws Exception {

		return forward("/promotion/card-benefit/card-benefit-list");
	}

	/**
	 * 
	 * @Desc : 카드사 혜택 관리 리스트 조회
	 * @Method Name : cardBenefitList
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read/list")
	public void cardBenefitList(Parameter<EvCardBenefit> parameter) throws Exception {

		EvCardBenefit params = parameter.get();

		Pageable<EvCardBenefit, EvCardBenefit> pageable = new Pageable<>(parameter);
		pageable.setRowsPerPage(params.getPageCount());

		Page<EvCardBenefit> page = cardBenefitService.getCardBenefitList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * 
	 * @Desc : 카드사 혜택 관리 상세 조회
	 * @Method Name : cardBenefitDetail
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/detail")
	public ModelAndView cardBenefitDetail(Parameter<EvCardBenefit> parameter) throws Exception {

		List<SySite> siteList = siteService.getSiteList();

		EvCardBenefit cardBenefit = parameter.get();

		if (cardBenefit.getCardBenefitSeq() != null) {

			cardBenefit = cardBenefitService.getCardBenefitDetail(cardBenefit);
		}

		parameter.addAttribute("cardBenefit", cardBenefit);
		parameter.addAttribute("siteList", siteList);

		return forward("/promotion/card-benefit/card-benefit-detail");
	}

	/**
	 * 
	 * @Desc : 카드사 혜택 관리 저장
	 * @Method Name : saveCardBenefit
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/save")
	public void saveCardBenefit(Parameter<EvCardBenefit> parameter) throws Exception {

		parameter.validate();

		EvCardBenefit params = parameter.get();

		List<EvCardBenefit> dubList = cardBenefitService.getCardBenefitCountByDate(params);

		if (UtilsText.equals(params.getDispYn(), BaseConst.BOOLEAN_TRUE) && dubList.size() > 0) {

			for (EvCardBenefit data : dubList) {

				data.setDispYn(BaseConst.BOOLEAN_FALSE);
				cardBenefitService.updateCardBenefit(data);
			}
		}

		if (params.getCardBenefitSeq() != null) {
			// 수정
			cardBenefitService.updateCardBenefitDetail(params);
		} else {
			// 등록
			cardBenefitService.insertCardBenefitDetail(params);
		}
	}

	/**
	 * 
	 * @Desc : 카드사 혜택 관리 중복 여부 체크
	 * @Method Name : cardBenefitDupList
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/dupCheck")
	public void cardBenefitDupList(Parameter<EvCardBenefit> parameter) throws Exception {

		parameter.validate();

		EvCardBenefit params = parameter.get();

		List<EvCardBenefit> dubList = cardBenefitService.getCardBenefitCountByDate(params);

		writeJson(parameter, dubList.size());
	}

}