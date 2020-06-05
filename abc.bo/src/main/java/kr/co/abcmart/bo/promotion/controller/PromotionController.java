package kr.co.abcmart.bo.promotion.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ValidationException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.product.model.master.PdFreeGift;
import kr.co.abcmart.bo.promotion.model.master.PrPromotion;
import kr.co.abcmart.bo.promotion.model.master.PrPromotionMultiBuyDiscount;
import kr.co.abcmart.bo.promotion.model.master.PrPromotionTargetBrand;
import kr.co.abcmart.bo.promotion.model.master.PrPromotionTargetCategory;
import kr.co.abcmart.bo.promotion.model.master.PrPromotionTargetChannel;
import kr.co.abcmart.bo.promotion.model.master.PrPromotionTargetDevice;
import kr.co.abcmart.bo.promotion.model.master.PrPromotionTargetGrade;
import kr.co.abcmart.bo.promotion.service.PromotionService;
import kr.co.abcmart.bo.promotion.vo.PrPromotionSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.CommonCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("promotion/promotion")
public class PromotionController extends BaseController {
	@Autowired
	private SiteService siteService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private PromotionService promotionService;

	/**
	 * @Desc : 프로모션 관리
	 * @Method Name : promotion
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ModelAndView promotion(Parameter<?> parameter) throws Exception {
		List<SySiteChnnl> channelList = siteService.getUseChannelList();
		List<SyCodeDetail> deviceCodeList = commonCodeService.getCodeNoName(CommonCode.DEVICE_CODE);
		List<SyCodeDetail> mbshpGradeCodeList = commonCodeService.getCodeNoName(CommonCode.MBSHP_GRADE_CODE);
		List<SyCodeDetail> memberTypeCodeList = commonCodeService.getCodeNoName(CommonCode.MEMBER_TYPE_CODE);
		List<SyCodeDetail> promoTypeCodeList = commonCodeService.getCodeNoName(CommonCode.PROMO_TYPE_CODE);

		log.debug("promotion");
		parameter.addAttribute("channelList", channelList);
		parameter.addAttribute("deviceCodeList", deviceCodeList);
		parameter.addAttribute("mbshpGradeCodeList", mbshpGradeCodeList);
		parameter.addAttribute("memberTypeCodeList", memberTypeCodeList);
		parameter.addAttribute("promoTypeCodeList", promoTypeCodeList);
		return forward("/promotion/promotion/promotion");
	}

	/**
	 * @Desc : 프로모션 관리(리스트조회)
	 * @Method Name : readPromotionList
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/list/read")
	public void readPromotionList(Parameter<PrPromotionSearchVO> parameter) throws Exception {

		Pageable<PrPromotionSearchVO, PrPromotion> pageable = new Pageable<>(parameter);

		Page<PrPromotion> page = promotionService.getPromotionList(pageable);

		writeJson(parameter, page.getGrid());

	}

	/**
	 * @Desc : 프로모션 관리(상세)
	 * @Method Name : promotionDetail
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/detail")
	public ModelAndView promotionDetail(Parameter<PrPromotion> parameter) throws Exception {
		// 화면에 사용되는 공통코드 조회
		String[] codeFields = { CommonCode.SELL_STAT_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
				.getCodeListByGroupByCombo(codeFields, false);

		parameter.addAttribute("codeCombo", pair.getFirst()); // 그리드 공통코드. 판매상태

		PrPromotion prPromotion = parameter.get();

		String promoNo = prPromotion.getPromoNo();
		if (promoNo != null) {
			// 프로모션상세
			prPromotion = promotionService.getPromotion(prPromotion);
			// 디바이스
			List<PrPromotionTargetDevice> prPromotionTargetDeviceList = promotionService
					.getPrPromotionDeviceListByPromoNo(promoNo);

			// 채널
			List<PrPromotionTargetChannel> prPromotionTargetChannelList = promotionService
					.getPrPromotionChannelListByPromoNo(promoNo);

			// 카테고리
			List<PrPromotionTargetCategory> prPromotionTargetCategoryList = promotionService
					.getPrPromotionCategoryListByPromoNo(promoNo);

			// 다족구매
			List<PrPromotionMultiBuyDiscount> prPromotionMultiBuyDiscountList = promotionService
					.getPromotionMultiBuyDiscountListByPromoNo(promoNo);

			// 회원유형 ,등급
			List<PrPromotionTargetGrade> prPromotionTargetGradeList = promotionService
					.getPrPromotionTargetGradeListByPromoNo(promoNo);

			// 브랜드
			List<PrPromotionTargetBrand> prPromotionTargetBrandList = promotionService
					.getPrPromotionTargetBrandListByPromoNo(promoNo);

			// 사은품
			PdFreeGift pdFreeGift = new PdFreeGift();
			pdFreeGift.setPromoNo(promoNo);
			List<PdFreeGift> prPromotionTargetGiftList = promotionService.getPromotionTargetFreeGift(pdFreeGift);

			// 이미지
			// List<PrPromotionImage> prPromotionImageList =
			// promotionService.getPrPromotionImageListByPromoNo(promoNo);
			// parameter.addAttribute("prPromotionImageList", prPromotionImageList);

			parameter.addAttribute("prPromotionTargetDeviceList", prPromotionTargetDeviceList);
			parameter.addAttribute("prPromotionTargetChannelList", prPromotionTargetChannelList);
			parameter.addAttribute("prPromotionTargetCategoryList", prPromotionTargetCategoryList);
			parameter.addAttribute("prPromotionMultiBuyDiscountList", prPromotionMultiBuyDiscountList);
			parameter.addAttribute("prPromotionTargetGradeList", prPromotionTargetGradeList);
			parameter.addAttribute("prPromotionTargetBrandList", prPromotionTargetBrandList);
			parameter.addAttribute("prPromotionTargetGiftList", prPromotionTargetGiftList);
		}

		List<SySiteChnnl> channelList = siteService.getUseChannelList();
		List<SyCodeDetail> deviceCodeList = commonCodeService.getCodeNoName(CommonCode.DEVICE_CODE);
		List<SyCodeDetail> mbshpGradeCodeList = commonCodeService.getCodeNoName(CommonCode.MBSHP_GRADE_CODE);
		List<SyCodeDetail> memberTypeCodeList = commonCodeService.getCodeNoName(CommonCode.MEMBER_TYPE_CODE);
		List<SyCodeDetail> promoTypeCodeList = commonCodeService.getCodeNoName(CommonCode.PROMO_TYPE_CODE);
		List<SyCodeDetail> affltsCodeList = commonCodeService.getCodeNoName(CommonCode.AFFLTS_CODE);

		parameter.addAttribute("prPromotion", prPromotion);
		parameter.addAttribute("channelList", channelList);
		parameter.addAttribute("affltsCodeList", affltsCodeList);
		parameter.addAttribute("deviceCodeList", deviceCodeList);
		parameter.addAttribute("mbshpGradeCodeList", mbshpGradeCodeList);
		parameter.addAttribute("memberTypeCodeList", memberTypeCodeList);
		parameter.addAttribute("promoTypeCodeList", promoTypeCodeList);
		return forward("/promotion/promotion/promotion-detail");
	}

	/**
	 * @Desc : 내부관리 번호 중복체크
	 * @Method Name : promotionDuplCheck
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/duplCheck")
	public void promotionDuplCheck(Parameter<?> parameter) throws Exception {
		String insdMgmtInfoText = parameter.getString("insdMgmtInfoText");

		boolean duplCheckVal = promotionService.getPromotionDuplCheck(insdMgmtInfoText);

		writeJson(parameter, duplCheckVal);

	}

	/**
	 * @Desc : 프로모션 관리(프로모션 등록)
	 * @Method Name : savePromotion
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/save")
	public void savePromotion(Parameter<PrPromotion> parameter) throws Exception {
		parameter.validate();

		if (!parameter.isValidation()) {
			throw new ValidationException();
		}

		PrPromotion prPromotion = parameter.get();

		promotionService.insertPrPromotion(prPromotion);
	}

	/**
	 * @Desc : 프로모션 관리(프로모션 수정)
	 * @Method Name : modifyPromotion
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/modify")
	public void modifyPromotion(Parameter<PrPromotion> parameter) throws Exception {
		parameter.validate();

		if (!parameter.isValidation()) {
			throw new ValidationException();
		}

		PrPromotion prPromotion = parameter.get();
		promotionService.updatePrPromotion(prPromotion);
	}

	/**
	 * @Desc : 프로모션 현황 팝업
	 * @Method Name : promotionStatusPopup
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/status/popup")
	public ModelAndView promotionStatusPopup(Parameter<?> parameter) throws Exception {
		log.debug("promotionStatusPopup");
		String promoNo = parameter.getString("promoNo");
		PrPromotion prPromotion = promotionService.getPromotionTotalStatus(promoNo);

		// Grid 코드정보
		String[] codeFields = { CommonCode.DEVICE_CODE // 디바이스 구분
				, CommonCode.ORDER_PRDT_STAT_CODE // 상품 배송 상태
		};

		// site Combo,site List
		Pair<JSONObject, List<SySite>> siteCode = siteService.getSiteListByCombo();

		// Code값 get
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		parameter.addAttribute("codeCombo", pair.getFirst()); // code Combo
		parameter.addAttribute("codeList", pair.getSecond()); // code list
		parameter.addAttribute("siteCombo", siteCode.getFirst()); // Site Combo
		parameter.addAttribute("siteList", siteCode.getSecond()); // Site List

		parameter.addAttribute("promoNo", promoNo);
		parameter.addAttribute("prPromotion", prPromotion);
		return forward("/promotion/promotion/promotion-status-popup");
	}

	/**
	 * @Desc : 프로모션 현황(리스트조회)
	 * @Method Name : readPromotionStatusList
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/status/list/read")
	public void readPromotionStatusList(Parameter<PrPromotionSearchVO> parameter) throws Exception {

		Pageable<PrPromotionSearchVO, PrPromotion> pageable = new Pageable<>(parameter);

		Page<PrPromotion> page = promotionService.getPromotionStatusList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 프로모션 현황 엑셀 다운로드
	 * @Method Name : promotionStatusExcelDownload
	 * @Date : 2019. 12. 11.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/status/excel/download")
	public void promotionStatusExcelDownload(Parameter<PrPromotionSearchVO> parameter) throws Exception {
		Pageable<PrPromotionSearchVO, PrPromotion> pageable = new Pageable<>(parameter);

		Page<PrPromotion> page = promotionService.getPromotionStatusListExcel(pageable);
		pageable.setUsePage(false);

		ExcelValue excelValue = null;

		if (page.getContent() != null) {
			int idx = 0;
			if (page.getContent() != null) {
				for (PrPromotion result : page.getContent()) {
					result.setExcelIndex(++idx);
				}
			}
			excelValue = ExcelValue.builder(1)
					.columnNames(Arrays.asList("excelIndex", "siteName", "deviceCodeName", "orderNo",
							"summaryProductName", "ordNormalAmt", "ordPayment", "ordDscntAmt", "orderStatCodeName"))
					.data(page.getContent()).build();
		}

		parameter.downloadExcelTemplate("promotion/promotion/excel/promotion_status", excelValue);
	}

	/**
	 * @Desc : 상품 중복 프로모션 조회
	 * @Method Name : promotionDuplProduct
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/duplProduct")
	public void promotionDuplProduct(Parameter<?> parameter) throws Exception {
		String prdtNo = parameter.getString("prdtNo");
		String promoNo = parameter.getString("promoNo");

		Map<String, String> map = new HashMap();
		String duplPromoNo = promotionService.getPromotionDuplProduct(prdtNo, promoNo);
		map.put("duplPromoNo", duplPromoNo);

		writeJson(parameter, map);

	}

	/**
	 * @Desc : 상품번호에 의한 프로모션 조회
	 * @Method Name : prdtNoByPromoList
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/target/product/promotion/list")
	public void prdtNoByPromoList(Parameter<?> parameter) throws Exception {
		String prdtNo = parameter.getString("prdtNo");

		List<PrPromotion> prdtNoByPromoList = promotionService.getPrdtNoByPromoList(prdtNo);

		writeJson(parameter, prdtNoByPromoList);
	}

}