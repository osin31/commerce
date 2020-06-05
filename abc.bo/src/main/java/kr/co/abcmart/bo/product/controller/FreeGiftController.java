package kr.co.abcmart.bo.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.product.model.master.PdFreeGift;
import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductOption;
import kr.co.abcmart.bo.product.model.master.PdProductRelationFile;
import kr.co.abcmart.bo.product.service.FreeGiftService;
import kr.co.abcmart.bo.product.service.ProductInsideRelationFileService;
import kr.co.abcmart.bo.product.service.ProductService;
import kr.co.abcmart.bo.product.vo.PdFreeGiftSearchVO;
import kr.co.abcmart.bo.promotion.model.master.PrPromotion;
import kr.co.abcmart.bo.promotion.service.PromotionService;
import kr.co.abcmart.bo.promotion.vo.PrPromotionSearchVO;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("product/freeGift")
public class FreeGiftController extends BaseController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductInsideRelationFileService productRelationFileService;

	@Autowired
	private FreeGiftService freeGiftService;

	@Autowired
	private PromotionService promotionService;

	/**
	 * @Desc : 사은품 목록 페이지
	 * @Method Name : getList
	 * @Date : 2019. 3. 26.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping
	public ModelAndView getList(Parameter<?> parameter) throws Exception {
		log.debug("사은품 목록 페이지");

		return forward("/product/freeGift/freeGift-list");
	}

	/**
	 * @Desc : 사은품 목록 조회
	 * @Method Name : searchFreeGiftList
	 * @Date : 2019. 3. 26.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping
	public void searchFreeGiftList(Parameter<PdFreeGiftSearchVO> parameter) throws Exception {
		log.debug("사은품 목록 조회");
		Pageable<PdFreeGiftSearchVO, PdFreeGift> pageable = new Pageable<PdFreeGiftSearchVO, PdFreeGift>(parameter);
		Page<PdFreeGift> freeGiftList = this.freeGiftService.selectFreeGift(pageable);

		this.writeJson(parameter, freeGiftList.getGrid());
	}

	/**
	 * @Desc : 사은품 상세 페이지 이동
	 * @Method Name : getDetail
	 * @Date : 2019. 3. 26.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("detail")
	public ModelAndView getDetail(Parameter<PdProduct> parameter) throws Exception {
		log.debug("사은품 등록/수정 화면");

		String prdtNo = parameter.getString("prdtNo"); // 온라인상품번호

		if (UtilsText.isNotBlank(prdtNo)) {
			// PD_상품 조회(사은품)
			PdProduct freeGift = this.productService.searchProductByPrimaryKey(prdtNo);
			parameter.addAttribute("freeGift", freeGift);

			// PD_상품옵션 조회(사은품) 기획은 1:1 매칭
			List<PdProductOption> freeGiftOptions = this.freeGiftService.searchProductOption(prdtNo);
			if (!freeGiftOptions.isEmpty()) {
				parameter.addAttribute("freeGiftOption", freeGiftOptions.get(0));
			}

			// PD_상품관련파일 조회(사은품)
			List<PdProductRelationFile> freeGiftFiles = this.productRelationFileService
					.searchProductRelationFile(prdtNo);
			if (!freeGiftFiles.isEmpty()) {
				parameter.addAttribute("pcBannerImg", freeGiftFiles.get(0));
				parameter.addAttribute("moBannerImg", freeGiftFiles.get(1));
			}
		}

		return forward("/product/freeGift/freeGift-detail");
	}

	/**
	 * @Desc : 사은품 상세 팝업
	 * @Method Name : getDetailPopup
	 * @Date : 2019. 11. 5.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("detail/popup")
	public ModelAndView getDetailPopup(Parameter<?> parameter) throws Exception {
		log.debug("사은품 팝업 페이지");

		return forward("/product/freeGift/freeGift-detail-popup");
	}

	/**
	 * @Desc : 사은품 상세 팝업 조회
	 * @Method Name : searchFreeGiftListPopup
	 * @Date : 2019. 11. 5.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("detail/popup")
	public void searchFreeGiftListPopup(Parameter<PdFreeGiftSearchVO> parameter) throws Exception {
		log.debug("사은품 팝업 목록 조회");
		Pageable<PdFreeGiftSearchVO, PdFreeGift> pageable = new Pageable<PdFreeGiftSearchVO, PdFreeGift>(parameter);
		Page<PdFreeGift> freeGiftList = this.freeGiftService.selectFreeGift(pageable);

		this.writeJson(parameter, freeGiftList.getGrid());
	}

	/**
	 * @Desc : 사은품 프로모션 목록 조회
	 * @Method Name : searchPromotionList
	 * @Date : 2019. 3. 26.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("promotion")
	public void searchPromotionList(Parameter<PrPromotionSearchVO> parameter) throws Exception {
		log.debug("사은품 프로모션 목록 조회");

		Pageable<PrPromotionSearchVO, PrPromotion> pageable = new Pageable<PrPromotionSearchVO, PrPromotion>(parameter);

		Page<PrPromotion> promotionList = this.promotionService.getPromotionProductList(pageable);

		this.writeJson(parameter, promotionList.getGrid());

	}

	/**
	 * @Desc : 사은품 정보 저장
	 * @Method Name : save
	 * @Date : 2019. 3. 26.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("save")
	public void save(Parameter<PdProduct> parameter) throws Exception {
		log.debug("사은품 정보 저장");

		PdProduct freeGift = parameter.get();
		freeGift.validateFreeGift();

		// 등록 후 중간계 호출
		this.freeGiftService.insertFreeGift(freeGift);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("freeGift", freeGift);

		this.writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 사은품 정보 수정
	 * @Method Name : modify
	 * @Date : 2019. 3. 26.
	 * @Author : hsjhsj19
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("modify")
	public void modify(Parameter<PdProduct> parameter) throws Exception {
		log.debug("사은품 정보 수정");

		PdProduct freeGift = parameter.get();
		freeGift.validateFreeGift();

		this.freeGiftService.updateFreeGift(freeGift);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("freeGift", freeGift);

		this.writeJson(parameter, resultMap);
	}

}
