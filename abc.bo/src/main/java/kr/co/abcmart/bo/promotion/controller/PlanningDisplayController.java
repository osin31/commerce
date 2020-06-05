package kr.co.abcmart.bo.promotion.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.product.model.master.PdProductMapped;
import kr.co.abcmart.bo.product.service.ProductService;
import kr.co.abcmart.bo.product.vo.PdProductMappingVO;
import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplay;
import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplayCorner;
import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplayProduct;
import kr.co.abcmart.bo.promotion.service.PlanningDisplayService;
import kr.co.abcmart.bo.promotion.vo.PrExhibitionPlanningVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.model.master.VdVendorDisplayChnnl;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/promotion/planning-display")
public class PlanningDisplayController extends BaseController {

	@Autowired
	private PlanningDisplayService planningDisplayService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private ProductService productService;

	/**
	 * 
	 * @Desc : 기획전 관리
	 * @Method Name : planningDisplay
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ModelAndView planningDisplay(Parameter<?> parameter) throws Exception {

		// BO/PO 구분
		UserDetails user = LoginManager.getUserDetails();
		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		// PO인 경우
		if (!isAdmin) {
			String vndrName = null;
			VdVendor vndr = new VdVendor();

			vndr.setVndrNo(user.getVndrNo());
			vndr = vendorService.getVendorBaseInfo(vndr);

			if (vndr != null)
				vndrName = vndr.getVndrName();

			parameter.addAttribute("vndrNo", vndr.getVndrNo());
			parameter.addAttribute("vndrName", vndrName);

			VdVendorDisplayChnnl criteriaForVendorChannel = new VdVendorDisplayChnnl();
			criteriaForVendorChannel.setVndrNo(user.getVndrNo());
			List<VdVendorDisplayChnnl> vendorChannelList = this.vendorService
					.selectVendorDisplayChnnlList(criteriaForVendorChannel);
			String vndrChnnlList = "";
			String separator = "";
			for (int i = 0; i < vendorChannelList.size(); i++) {
				separator = (i == 0) ? "" : "|";
				vndrChnnlList += separator + vendorChannelList.get(i).getChnnlNo();
			}

			// 입점사 적용 채널 조회
			parameter.addAttribute("vndrChnnlList", vndrChnnlList);
		}

		String[] codeFields = { CommonCode.MEMBER_TYPE_CODE, CommonCode.DEVICE_CODE, "PLNDP_TYPE_CODE",
				"PLNDP_STAT_CODE" };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();

		parameter.addAttribute("codeCombo", pair.getFirst());
		parameter.addAttribute("memberTypeCodeList", codeList.get("MEMBER_TYPE_CODE"));
		parameter.addAttribute("deviceCodeList", codeList.get("DEVICE_CODE"));
		parameter.addAttribute("plndpTypeCodeList", codeList.get("PLNDP_TYPE_CODE"));
		parameter.addAttribute("plndpStatCodeList", codeList.get("PLNDP_STAT_CODE"));

		/** 채널 콤보 */
		List<SySiteChnnl> chnnlList = siteService.getUseChannelList();

		String chnnlName = chnnlList.stream().map(SySiteChnnl::getChnnlName).collect(Collectors.joining("|"));
		String chnnlValue = chnnlList.stream().map(SySiteChnnl::getChnnlNo).collect(Collectors.joining("|"));

		JSONObject json = new JSONObject();
		json.put("code", chnnlValue);
		json.put("text", chnnlName);

		parameter.addAttribute("chnnlList", chnnlList);
		parameter.addAttribute("chnnlCombo", Pair.of(json, chnnlList).getFirst());
		/** 채널 콤보 */

		return forward("/promotion/planning-display/planning-display");
	}

	/**
	 * 
	 * @Desc : 기획전 리스트 조회
	 * @Method Name : list
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/list")
	@ResponseBody
	public void list(Parameter<PrPlanningDisplay> parameter) throws Exception {

		// pageType : 기획전 관리 (default)
		parameter.get().setPageType("D");

		Pageable<PrPlanningDisplay, PrPlanningDisplay> pageable = new Pageable<>(parameter);

		Page<PrPlanningDisplay> page = planningDisplayService.getPrPlanningDisplayList(pageable);

		writeJson(parameter, page.getGrid());

	}

	/**
	 * 
	 * @Desc : 기획전 상세 조회
	 * @Method Name : planningDisplayDetail
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/detail")
	public ModelAndView planningDisplayDetail(Parameter<PrPlanningDisplay> parameter) throws Exception {

		/** BO/PO 구분 */
		UserDetails user = LoginManager.getUserDetails();
		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		/** 기획전 승인 상세 페이지인 경우 */
		if (UtilsText.isNotEmpty(parameter.get().getPageType()))
			parameter.addAttribute("isApproval", true);

		/** checkbox */
		List<SySiteChnnl> chnnlList = siteService.getUseChannelList();

		String[] codeFields = { CommonCode.MEMBER_TYPE_CODE, CommonCode.MBSHP_GRADE_CODE, CommonCode.DEVICE_CODE,
				"PLNDP_TYPE_CODE", "PLNDP_STAT_CODE", "PC_DISP_TYPE_CODE", "MOBILE_DISP_TYPE_CODE" };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();

		parameter.addAttribute("codeCombo", pair.getFirst());

		parameter.addAttribute("chnnlList", chnnlList);
		parameter.addAttribute("memberTypeCodeList", codeList.get("MEMBER_TYPE_CODE"));
		parameter.addAttribute("mbshpGradeCodeList", codeList.get("MBSHP_GRADE_CODE"));
		parameter.addAttribute("deviceCodeList", codeList.get("DEVICE_CODE"));
		parameter.addAttribute("plndpTypeCodeList", codeList.get("PLNDP_TYPE_CODE"));
		parameter.addAttribute("plndpStatCodeList", codeList.get("PLNDP_STAT_CODE"));
		/** checkbox */

		/** data */
		PrPlanningDisplay planningDisplay = parameter.get();
		String plndpNo = planningDisplay.getPlndpNo();
		if (UtilsText.equals(user.getUpAuthNo(), Const.ROLE_VENDER_GROUP)) {
			planningDisplay.setVndrNo(user.getVndrNo());
		}

		if (plndpNo != null) {
			planningDisplay = planningDisplayService.getPrPlanningDisplayDetail(planningDisplay);

			if (UtilsObject.isEmpty(planningDisplay)) {
				throw new Exception("해당하는 기획전 정보가 없습니다.");
			}

			// 기획전
			parameter.addAttribute("planningDisplay", planningDisplay);
			// 기획전 대상 회원
			parameter.addAttribute("prPlanningDisplayApplyGradeList",
					planningDisplayService.getPrPlanningDisplayApplyGradeListByPlndpNo(plndpNo));
			// 기획전 대상 디바이스
			parameter.addAttribute("prPlanningDisplayApplyDeviceList",
					planningDisplayService.getPrPlanningDisplayApplyDeviceListByPlndpNo(plndpNo));
			// 기획전 유형
			parameter.addAttribute("prPlanningDisplayTypeList",
					planningDisplayService.getPrPlanningDisplayTypeListByPlndpNo(plndpNo));
		}
		/** data */

		// 입점업체 전시채널 조회
		if (!isAdmin || (isAdmin && plndpNo != null
				&& UtilsText.equals(planningDisplay.getVndrGbnType(), Const.VNDR_GBN_TYPE_V))) {
			VdVendorDisplayChnnl criteriaForVendorChannel = new VdVendorDisplayChnnl();
			criteriaForVendorChannel.setVndrNo(!isAdmin ? user.getVndrNo() : planningDisplay.getVndrNo());
			List<VdVendorDisplayChnnl> vendorChannelList = this.vendorService
					.selectVendorDisplayChnnlList(criteriaForVendorChannel);
			String vndrChnnlList = "";
			String separator = "";
			for (int i = 0; i < vendorChannelList.size(); i++) {
				separator = (i == 0) ? "" : "|";
				vndrChnnlList += separator + vendorChannelList.get(i).getChnnlNo();
			}

			parameter.addAttribute("vndrChnnlList", vndrChnnlList);
		}

		return forward("/promotion/planning-display/planning-display-detail");
	}

	/**
	 * 
	 * @Desc : 기획전 에디터 이미지 업로드
	 * @Method Name : editorUploadPc
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/detail/editor/upload")
	public void editorUploadPc(Parameter<PrPlanningDisplay> parameter) throws Exception {

		PrPlanningDisplay prPlanningDisplay = parameter.get();

		Map<String, Object> result = planningDisplayService.uploadImageByEditor(prPlanningDisplay.getUpload());

		writeJson(parameter, result);
	}

	/**
	 * 
	 * @Desc : 기획전 상품 관리
	 * @Method Name : planningDisplayProductManage
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/product/manage")
	public ModelAndView planningDisplayProductManage(Parameter<PrPlanningDisplay> parameter) throws Exception {

		/** BO/PO 구분 */
		UserDetails user = LoginManager.getUserDetails();
		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		/** grid */
		String[] codeFields = { "PC_DISP_TYPE_CODE", "MOBILE_DISP_TYPE_CODE", CommonCode.SELL_STAT_CODE };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		parameter.addAttribute("codeCombo", pair.getFirst());
		/** grid */

		/** 채널 콤보 */
		List<SySiteChnnl> chnnlList = siteService.getUseChannelList();

		String chnnlName = chnnlList.stream().map(SySiteChnnl::getChnnlName).collect(Collectors.joining("|"));
		String chnnlValue = chnnlList.stream().map(SySiteChnnl::getChnnlNo).collect(Collectors.joining("|"));

		JSONObject json = new JSONObject();
		json.put("code", chnnlValue);
		json.put("text", chnnlName);

		parameter.addAttribute("chnnlCombo", Pair.of(json, chnnlList).getFirst());
		/** 채널 콤보 */

		/** data */
		PrPlanningDisplay planningDisplay = parameter.get();
		String plndpNo = planningDisplay.getPlndpNo();
		if (UtilsText.equals(user.getUpAuthNo(), Const.ROLE_VENDER_GROUP)) {
			planningDisplay.setVndrNo(user.getVndrNo());
		}

		if (plndpNo != null) {
			planningDisplay = planningDisplayService.getPrPlanningDisplayDetail(planningDisplay);

			if (UtilsObject.isEmpty(planningDisplay)) {
				throw new Exception("해당하는 기획전 정보가 없습니다.");
			}

			parameter.addAttribute("planningDisplay", planningDisplay);
		}
		/** data */

		return forward("/promotion/planning-display/planning-display-product-manage");
	}

	/**
	 * 
	 * @Desc : 기획전 상품 관리 수정
	 * @Method Name : modifyProductManage
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/product/manage/modify")
	@ResponseBody
	public void modifyProductManage(Parameter<PrPlanningDisplay> parameter) throws Exception {

		UserDetails user = LoginManager.getUserDetails();
		parameter.get().setModerNo(user.getAdminNo());

		PrPlanningDisplay planningDisplay = parameter.get();

		planningDisplay.validate();

		planningDisplayService.updatePrPlanningDisplay(planningDisplay, true);

		writeJson(parameter, planningDisplay.getPlndpNo());
	}

	/**
	 * 
	 * @Desc : 기획전 등록
	 * @Method Name : add
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/add")
	@ResponseBody
	public void add(Parameter<PrPlanningDisplay> parameter) throws Exception {

		UserDetails user = LoginManager.getUserDetails();
		parameter.get().setModerNo(user.getAdminNo());
		parameter.get().setRgsterNo(user.getAdminNo());

		// 업체
		if (UtilsText.equals(user.getUpAuthNo(), Const.ROLE_VENDER_GROUP)) {
			parameter.get().setVndrGbnType(Const.VNDR_GBN_TYPE_V);
			parameter.get().setVndrNo(user.getVndrNo());
			String plndpStatCode = UtilsText.equals("saveTmprly", parameter.get().getPlndpStatName()) ? "10000"
					: "10001"; // 임시저장 or 승인요청
			parameter.get().setPlndpStatCode(plndpStatCode);
			parameter.get().setDispYn(Const.BOOLEAN_FALSE);
			parameter.get().setUseYn(Const.BOOLEAN_TRUE);
		}
		// 자사
		else if (UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP)) {
			parameter.get().setVndrGbnType(Const.VNDR_GBN_TYPE_C);
			parameter.get().setPlndpStatCode("10003"); // 승인완료
		}

		parameter.get().setDispType("B");
		parameter.get().setCornerApplyYn(Const.BOOLEAN_FALSE);
		parameter.get().setCornerDispType(Const.BOOLEAN_FALSE);

		parameter.validate();

		PrPlanningDisplay planningDisplay = parameter.get();

		String plDispName = UtilsText.unescapeXss(parameter.getString("plndpName"));
		planningDisplay.setPlndpName(plDispName);

		planningDisplayService.insertPrPlanningDisplay(planningDisplay);

		writeJson(parameter, planningDisplay.getPlndpNo());
	}

	/**
	 * 
	 * @Desc : 기획전 수정
	 * @Method Name : modify
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/modify")
	@ResponseBody
	public void modify(Parameter<PrPlanningDisplay> parameter) throws Exception {

		UserDetails user = LoginManager.getUserDetails();
		parameter.get().setModerNo(user.getAdminNo());

		// 업체
		if (UtilsText.equals(user.getUpAuthNo(), Const.ROLE_VENDER_GROUP)) {
			String plndpStatCode = UtilsText.equals("saveTmprly", parameter.get().getPlndpStatName()) ? "10000"
					: "10001";
			parameter.get().setPlndpStatCode(plndpStatCode);
		}
		// 자사
		else if (UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP)) {
			parameter.get().setVndrGbnType(Const.VNDR_GBN_TYPE_C);
		}

		parameter.validate();

		PrPlanningDisplay planningDisplay = parameter.get();

		String plDispName = UtilsText.unescapeXss(parameter.getString("plndpName"));
		planningDisplay.setPlndpName(plDispName);

		planningDisplayService.updatePrPlanningDisplay(planningDisplay, false);

		writeJson(parameter, planningDisplay.getPlndpNo());
	}

	/**
	 * 
	 * @Desc : 기획전 코너 관리 팝업
	 * @Method Name : cornerPopup
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/corner")
	public ModelAndView cornerPopup(Parameter<PrPlanningDisplayCorner> parameter) throws Exception {

		PrPlanningDisplayCorner params = parameter.get();

		String[] codeFields = { "PC_DISP_TYPE_CODE", "MOBILE_DISP_TYPE_CODE" };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();

		parameter.addAttribute("pcDispTypeCode", codeList.get("PC_DISP_TYPE_CODE"));
		parameter.addAttribute("mobileDispTypeCode", codeList.get("MOBILE_DISP_TYPE_CODE"));
		parameter.addAttribute("plndpNo", params.getPlndpNo());

		return forward("/promotion/planning-display/planning-display-corner-popup");
	}

	/**
	 * 
	 * @Desc : 기획전 코너 리스트 조회
	 * @Method Name : cornerList
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/corner/list")
	@ResponseBody
	public void cornerList(Parameter<PrPlanningDisplayCorner> parameter) throws Exception {

		Pageable<PrPlanningDisplayCorner, PrPlanningDisplayCorner> pageable = new Pageable<>(parameter);

		Page<PrPlanningDisplayCorner> page = planningDisplayService.getPrPlanningDisplayCornerList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * 
	 * @Desc : 기획전 코너 등록
	 * @Method Name : cornerAdd
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/corner/add")
	@ResponseBody
	public void cornerAdd(Parameter<?> parameter) throws Exception {

		PrPlanningDisplayCorner[] corners = parameter.createArray(PrPlanningDisplayCorner.class, "sortSeq");

		planningDisplayService.setPrPlanningDisplayCorners(corners);
	}

	/**
	 * 
	 * @Desc : 기획전 코너 수정
	 * @Method Name : cornerModify
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/corner/modify")
	@ResponseBody
	public void cornerModify(Parameter<PrPlanningDisplayCorner> parameter) throws Exception {

		PrPlanningDisplayCorner prPlanningDisplayCorner = parameter.get();

		planningDisplayService.updatePrPlanningDisplayCorner(prPlanningDisplayCorner);
	}

	/**
	 * 
	 * @Desc : 기획전 코너 순서 저장
	 * @Method Name : cornerSave
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/corner/save")
	@ResponseBody
	public void cornerSave(Parameter<PrPlanningDisplayCorner[]> parameter) throws Exception {

		PrPlanningDisplayCorner[] prPlanningDisplayCorners = parameter.get();

		if (prPlanningDisplayCorners != null && prPlanningDisplayCorners.length > 0)
			planningDisplayService.updatePrPlanningDisplayCornerSort(prPlanningDisplayCorners);
	}

	/**
	 * 
	 * @Desc : 기획전 코너 삭제
	 * @Method Name : cornerRemove
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/corner/remove")
	@ResponseBody
	public void cornerRemove(Parameter<PrPlanningDisplayCorner> parameter) throws Exception {

		PrPlanningDisplayCorner prPlanningDisplayCorner = parameter.get();

		planningDisplayService.deletePrPlanningDisplayCorner(prPlanningDisplayCorner);
	}

	/**
	 * 
	 * @Desc : 기획전 상품 리스트 조회
	 * @Method Name : productList
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/product/list")
	@ResponseBody
	public void productList(Parameter<PdProductMappingVO> parameter) throws Exception {

		Pageable<PdProductMappingVO, PdProductMapped> pageable = new Pageable<PdProductMappingVO, PdProductMapped>(
				parameter);
		pageable.getBean().setMappingTableName("pr_planning_display_product");
		pageable.getBean().setConditionColumnName("plndp_no");
		pageable.getBean().setConditionColumnValue(parameter.getString("plndpNo"));
		pageable.getBean().setSortColumnName("sort_seq");
		pageable.getBean().setSortType("ASC");
		pageable.getBean().setSearchForAppliedPromotion(true);
		Page<PdProductMapped> page = this.productService.searchProductByMapped(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * 
	 * @Desc : 기획전 상품 등록
	 * @Method Name : productAdd
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/product/add")
	@ResponseBody
	public void productAdd(Parameter<PrPlanningDisplayProduct[]> parameter) throws Exception {

		PrPlanningDisplayProduct[] prPlanningDisplayProducts = parameter.get();

		planningDisplayService.insertPrPlanningDisplayProduct(prPlanningDisplayProducts);
	}

	/**
	 * 
	 * @Desc : 기획전 상품 순서 저장
	 * @Method Name : productSave
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/product/save")
	@ResponseBody
	public void productSave(Parameter<PrPlanningDisplayProduct[]> parameter) throws Exception {

		PrPlanningDisplayProduct[] prPlanningDisplayProducts = parameter.get();

		planningDisplayService.updatePrPlanningDisplayProduct(prPlanningDisplayProducts);
	}

	/**
	 * 
	 * @Desc : 기획전 상품 삭제
	 * @Method Name : productRemove
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/product/remove")
	@ResponseBody
	public void productRemove(Parameter<PrPlanningDisplayProduct[]> parameter) throws Exception {

		PrPlanningDisplayProduct[] prPlanningDisplayProducts = parameter.get();

		planningDisplayService.deletePrPlanningDisplayProduct(prPlanningDisplayProducts);
	}

	/**
	 * 
	 * @Desc : 상품에 대한 기획전 매핑 정보 조회
	 * @Method Name : readExhibitionPageForProduct
	 * @Date : 2019. 11. 5.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/product/exhibition/planning")
	public void readExhibitionPageForProduct(Parameter<PrExhibitionPlanningVO> parameter) throws Exception {
		Pageable<PrExhibitionPlanningVO, PrExhibitionPlanningVO> pageable = new Pageable<PrExhibitionPlanningVO, PrExhibitionPlanningVO>(
				parameter);
		Page<PrExhibitionPlanningVO> productList = this.planningDisplayService
				.getExhibitionPlanningForProduct(pageable);
		this.writeJson(parameter, productList.getGrid());
	}

}