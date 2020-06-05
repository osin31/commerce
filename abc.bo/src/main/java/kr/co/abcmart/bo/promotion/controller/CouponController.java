package kr.co.abcmart.bo.promotion.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.apache.poi.ss.usermodel.Sheet;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.service.MemberService;
import kr.co.abcmart.bo.member.vo.MemberSearchVO;
import kr.co.abcmart.bo.product.model.master.PdProductMapped;
import kr.co.abcmart.bo.product.model.master.SyStandardCategory;
import kr.co.abcmart.bo.product.service.ProductService;
import kr.co.abcmart.bo.product.service.StandardCategoryService;
import kr.co.abcmart.bo.product.vo.PdProductMappingVO;
import kr.co.abcmart.bo.promotion.model.master.PrCoupon;
import kr.co.abcmart.bo.promotion.model.master.PrCouponApplyChannel;
import kr.co.abcmart.bo.promotion.model.master.PrCouponApplyDevice;
import kr.co.abcmart.bo.promotion.model.master.PrCouponApplyStore;
import kr.co.abcmart.bo.promotion.model.master.PrCouponPaperNumber;
import kr.co.abcmart.bo.promotion.model.master.PrCouponVendorShareRate;
import kr.co.abcmart.bo.promotion.service.CouponService;
import kr.co.abcmart.bo.promotion.vo.PrCouponSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.excel.ExcelReader;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.interfaces.util.UtilsText;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("promotion/coupon")
public class CouponController extends BaseController {
	@Autowired
	private SiteService siteService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private CouponService couponService;

	@Autowired
	private StandardCategoryService standardCategoryService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ProductService productService;

	@Autowired
	private VendorService vendorService;

	/**
	 * @Desc : 쿠폰 관리
	 * @Method Name : coupon
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@GetMapping("")
	public ModelAndView coupon(Parameter<?> parameter) throws Exception {
		List<SySiteChnnl> channelList = siteService.getUseChannelList();
		List<SyCodeDetail> deviceCodeList = commonCodeService.getCodeNoName(CommonCode.DEVICE_CODE);
		List<SyCodeDetail> cpnTypeCodeList = commonCodeService.getCodeNoName(CommonCode.CPN_TYPE_CODE);

		log.debug("coupon");
		parameter.addAttribute("channelList", channelList);
		parameter.addAttribute("deviceCodeList", deviceCodeList);
		parameter.addAttribute("cpnTypeCodeList", cpnTypeCodeList);
		return forward("/promotion/coupon/coupon");
	}

	/**
	 * @Desc : 쿠폰 관리(리스트조회)
	 * @Method Name : readCouponList
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@PostMapping("/list/read")
	public void readCouponList(Parameter<PrCouponSearchVO> parameter) throws Exception {

		Pageable<PrCouponSearchVO, PrCoupon> pageable = new Pageable<>(parameter);

		Page<PrCoupon> page = couponService.getCouponList(pageable);

		writeJson(parameter, page.getGrid());

	}

	/**
	 * @Desc : 쿠폰 관리(입점사리스트조회)
	 * @Method Name : readCouponVendorList
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@PostMapping("/vendor/list/read")
	public void readCouponVendorList(Parameter<?> parameter) throws Exception {
		String cpnNo = parameter.getString("cpnNo");
		List<PrCouponVendorShareRate> prCouponVendorShareRateList = couponService
				.getPrCouponVendorShareRateListByCpnNo(cpnNo);

		writeJson(parameter, prCouponVendorShareRateList);

	}

	/**
	 * @Desc : 쿠폰 관리(상품 조회)
	 * @Method Name : readCouponProductList
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@PostMapping("/product/list/read")
	public void readCouponProductList(Parameter<PdProductMappingVO> parameter) throws Exception {
		// String cpnNo = parameter.getString("cpnNo");
		// List<PrCouponApplyProduct> prCouponApplyProductList =
		// couponService.getPrCouponApplyProductListByCpnNo(cpnNo);

		// writeJson(parameter, prCouponApplyProductList);

		Pageable<PdProductMappingVO, PdProductMapped> pageable = new Pageable<PdProductMappingVO, PdProductMapped>(
				parameter);

		pageable.getBean().setMappingTableName("pr_coupon_apply_product");
		pageable.getBean().setConditionColumnName("cpn_no");
		pageable.getBean().setConditionColumnValue(parameter.getString("cpnNo"));
		pageable.getBean().setConditionColumnName1("prdt_type");
		pageable.getBean().setConditionColumnValue1(parameter.getString("prdtType"));
		pageable.getBean().setSortColumnName("prdt_no");
		pageable.getBean().setSortType("ASC");
		pageable.setBean(pageable.getBean());

		Page<PdProductMapped> page = this.productService.searchProductByMapped(pageable);
		this.writeJson(parameter, page.getGrid());

	}

	/**
	 * @Desc : 쿠폰 관리(상세)
	 * @Method Name : couponDetail
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@GetMapping("/detail")
	public ModelAndView couponDetail(Parameter<PrCoupon> parameter) throws Exception {

		PrCoupon prCoupon = parameter.get();

		String cpnNo = prCoupon.getCpnNo();

		// 화면에 사용되는 공통코드 조회
		String[] codeFields = { CommonCode.SELL_STAT_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
				.getCodeListByGroupByCombo(codeFields, false);

		parameter.addAttribute("codeCombo", pair.getFirst()); // 그리드 공통코드. 판매상태

		if (cpnNo != null) {
			// 쿠폰상세
			prCoupon = couponService.getCoupon(prCoupon);

			// 디바이스
			List<PrCouponApplyDevice> prCouponApplyDeviceList = couponService.getPrCouponDeviceListByCpnNo(cpnNo);

			// 채널
			List<PrCouponApplyChannel> prCouponApplyChannelList = couponService
					.getPrCouponApplyChannelListByCpnNo(cpnNo);

			// 카테고리
			/*
			 * List<PrCouponApplyCategory> prCouponApplyCategoryList = couponService
			 * .getPrCouponApplyCategoryListByCpnNo(cpnNo);
			 */

			// 매장
			List<PrCouponApplyStore> prCouponApplyStoreList = couponService.getPrCouponApplyStoreListByCpnNo(cpnNo);

			// 브랜드
			/*
			 * List<PrCouponApplyBrand> prCouponApplyBrandList =
			 * couponService.getPrCouponApplyBrandListByCpnNo(cpnNo);
			 */

			parameter.addAttribute("prCouponApplyDeviceList", prCouponApplyDeviceList);
			parameter.addAttribute("prCouponApplyChannelList", prCouponApplyChannelList);
			// parameter.addAttribute("prCouponApplyCategoryList",
			// prCouponApplyCategoryList);
			parameter.addAttribute("prCouponApplyStoreList", prCouponApplyStoreList);
			// parameter.addAttribute("prCouponApplyBrandList", prCouponApplyBrandList);
			SyStandardCategory syStandardCategory = new SyStandardCategory();
			parameter.addAttribute("standardCategoryList",
					standardCategoryService.getStandardCategoryList(syStandardCategory));
		}

		List<SySiteChnnl> channelList = siteService.getUseChannelList();
		List<SyCodeDetail> deviceCodeList = commonCodeService.getCodeNoName(CommonCode.DEVICE_CODE);
		List<SyCodeDetail> cpnTypeCodeList = commonCodeService.getCodeNoName(CommonCode.CPN_TYPE_CODE);
		List<SyCodeDetail> affltsCodeList = commonCodeService.getCodeNoName(CommonCode.AFFLTS_CODE);

		parameter.addAttribute("prCoupon", prCoupon);
		parameter.addAttribute("channelList", channelList);
		parameter.addAttribute("deviceCodeList", deviceCodeList);
		parameter.addAttribute("cpnTypeCodeList", cpnTypeCodeList);
		parameter.addAttribute("affltsCodeList", affltsCodeList);
		return forward("/promotion/coupon/coupon-detail");
	}

	/**
	 * @Desc : 쿠폰 검색 팝업
	 * @Method Name : couponPopup
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@GetMapping("/popup")
	public ModelAndView couponPopup(Parameter<?> parameter) throws Exception {
		List<SySiteChnnl> channelList = siteService.getUseChannelList();
		List<SyCodeDetail> deviceCodeList = commonCodeService.getCodeNoName(CommonCode.DEVICE_CODE);
		List<SyCodeDetail> cpnTypeCodeList = commonCodeService.getCodeNoName(CommonCode.CPN_TYPE_CODE);

		log.debug("coupon");
		parameter.addAttribute("channelList", channelList);
		parameter.addAttribute("deviceCodeList", deviceCodeList);
		parameter.addAttribute("cpnTypeCodeList", cpnTypeCodeList);
		return forward("/promotion/coupon/coupon-popup");
	}

	/**
	 * @Desc : 쿠폰 관리(등록)
	 * @Method Name : saveCoupon
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@PostMapping("/save")
	public void saveCoupon(Parameter<PrCoupon> parameter) throws Exception {
		parameter.validate();

		if (!parameter.isValidation()) {
			throw new ValidationException();
		}

		PrCoupon prCoupon = parameter.get();

		couponService.insertCoupon(prCoupon);

	}

	/**
	 * @Desc : 쿠폰 관리(수정)
	 * @Method Name : modifyCoupon
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@PostMapping("/modify")
	public void modifyCoupon(Parameter<PrCoupon> parameter) throws Exception {
		parameter.validate();

		if (!parameter.isValidation()) {
			throw new ValidationException();
		}

		PrCoupon prCoupon = parameter.get();

		couponService.updateCoupon(prCoupon);

	}

	/**
	 * @Desc : 쿠폰 현황 팝업(회원)
	 * @Method Name : memberCouponPopup
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@PostMapping("/member/popup")
	public ModelAndView memberCouponPopup(Parameter<?> parameter) throws Exception {
		List<SyCodeDetail> cpnTypeCodeList = commonCodeService.getCodeNoName(CommonCode.CPN_TYPE_CODE);
		List<SyCodeDetail> mbshpGradeCodeList = commonCodeService.getCodeNoName(CommonCode.MBSHP_GRADE_CODE);
		List<SyCodeDetail> memberTypeCodeList = commonCodeService.getCodeNoName(CommonCode.MEMBER_TYPE_CODE);

		log.debug("memberCouponPopup");
		parameter.addAttribute("cpnTypeCodeList", cpnTypeCodeList);
		parameter.addAttribute("mbshpGradeCodeList", mbshpGradeCodeList);
		parameter.addAttribute("memberTypeCodeList", memberTypeCodeList);
		parameter.addAttribute("cpnNo", parameter.getString("cpnNo"));
		parameter.addAttribute("paperYn", parameter.getString("paperYn"));
		
		// 2020.05.13 : 쿠폰지급 버튼 노출여부를 따지기 위해 set
		parameter.addAttribute("noCpnBtnLoinId", LoginManager.getUserDetails().getLoginId());		
		
		return forward("/promotion/coupon/member-coupon-popup");
	}

	/**
	 * @Desc : 쿠폰 현황(회원 리스트조회 tab1)
	 * @Method Name : readMemberCouponList
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@PostMapping("/member/list/read")
	public void readMemberCouponList(Parameter<PrCouponSearchVO> parameter) throws Exception {

		Pageable<PrCouponSearchVO, PrCoupon> pageable = new Pageable<>(parameter);

		Page<PrCoupon> page = couponService.getMemberCouponPopList(pageable);

		writeJson(parameter, page.getGrid());

	}

	/**
	 * @Desc : 쿠폰 현황(회원 리스트조회 tab2)
	 * @Method Name : readMemberSubCouponList
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@PostMapping("/member/sub/list/read")
	public void readMemberSubCouponList(Parameter<MemberSearchVO> parameter) throws Exception {

		Pageable<MemberSearchVO, MbMember> pageable = new Pageable<>(parameter);

		Page<MbMember> page = memberService.getMemberSubList(pageable);

		writeJson(parameter, page.getGrid());

	}

	/**
	 * @Desc : 쿠폰 관리(회원 쿠폰 등록)
	 * @Method Name : saveMemberCoupon
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@PostMapping("/member/save")
	public void saveMemberCoupon(Parameter<PrCoupon> parameter) throws Exception {
		PrCoupon prCoupon = parameter.get();

		writeJson(parameter, couponService.insertMemberCoupon(prCoupon));
	}

	/**
	 * @Desc : 회원 체크(회원 쿠폰 등록 체크)
	 * @Method Name : checkMemberCoupon
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@PostMapping("/member/check")
	public void checkMemberCoupon(Parameter<PrCoupon> parameter) throws Exception {
		PrCoupon prCoupon = parameter.get();
		writeJson(parameter, couponService.checkMemberCoupon(prCoupon));
	}

	/**
	 * @Desc : 쿠폰 관리(회원 쿠폰 상태값 변경 조건)
	 * @Method Name : couponMemberCondition
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@PostMapping("/member/condition")
	public void couponMemberCondition(Parameter<PrCoupon> parameter) throws Exception {
		PrCoupon prCoupon = parameter.get();

		boolean condition = couponService.getCouponCondtion(prCoupon);

		writeJson(parameter, condition);
	}

	/**
	 * @Desc : 쿠폰 관리(회원 쿠폰 상태값 변경)
	 * @Method Name : couponStatusModify
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@PostMapping("/status/modify")
	public void couponStatusModify(Parameter<PrCoupon> parameter) throws Exception {
		PrCoupon prCoupon = parameter.get();

		couponService.updateStatusCoupon(prCoupon);
	}

	/**
	 * @Desc : 쿠폰 관리(회원 쿠폰 엑셀 데이터)
	 * @Method Name : readMemberExcelList
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/member/excel/list")
	public void readMemberExcelList(Parameter<?> parameter) throws Exception {
		FileUpload uploadFile = parameter.getUploadFile("excelUpload");

		ExcelReader reader = ExcelReader.builder(1).converter((Sheet sheet, int rowNum, String[] row) -> {

			String memberNo = "";
			if (UtilsText.isNotBlank(row[0])) {
				memberNo = row[0];
			} else {
				memberNo = "0";
			}
			return memberNo;
		}).build();

		List<String> memberNos = reader.read(uploadFile.getMultiPartFile().getInputStream());

		List<MbMember> list = couponService.memberInfoSet(memberNos);

		List<MbMember> effectiveList = list.stream()
				.filter(effectiveMember -> UtilsText.equals("N", effectiveMember.getLeaveYn()))
				.collect(Collectors.<MbMember>toList());

		writeJson(parameter, effectiveList);

	}

	/**
	 * @Desc : 쿠폰 관리(쿠폰 발급 엑셀 폼)
	 * @Method Name : memberExcelDownload
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/member/excel/download")
	public void memberExcelDownload(Parameter<?> parameter) throws Exception {

		parameter.downloadExcelTemplate("promotion/coupon/excel/memberCoupon", null);
	}

	/**
	 * @Desc : 입점사 기본 정보
	 * @Method Name : readVendorList
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@GetMapping("/vendor/info/list/read")
	public void readVendorList(Parameter<?> parameter) throws Exception {
		/*
		 * VdVendor vdVendor = new VdVendor();
		 * vdVendor.setVndrNoList(Arrays.asList(parameter.getStringArray("vndrNoList")))
		 * ; List<VdVendor> vendorInfoList =
		 * vendorService.getVendorBaseInfoList(vdVendor);
		 */

		List<String> vndrNoList = new ArrayList<String>();
		List<PrCouponVendorShareRate> couponVendorShareRateList = new ArrayList<PrCouponVendorShareRate>();
		if (UtilsObject.isNotEmpty(parameter.getStringArray("vndrNoList"))) {
			vndrNoList = Arrays.asList(parameter.getStringArray("vndrNoList"));
			couponVendorShareRateList = couponService.getVendorCouponShareRateList(parameter.getString("cpnNo"),
					vndrNoList);
		}

		writeJson(parameter, couponVendorShareRateList);
	}

	/**
	 * @Desc : 상품번호에 의한 쿠폰 조회
	 * @Method Name : prdtNoByCpnList
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@GetMapping("/target/product/coupon/list")
	public void prdtNoByCpnList(Parameter<?> parameter) throws Exception {
		String prdtNo = parameter.getString("prdtNo");

		List<PrCoupon> prdtNoByCpnList = couponService.getPrdtNoByCpnList(prdtNo);

		writeJson(parameter, prdtNoByCpnList);
	}

	/**
	 * @Desc : 쿠폰 지류 엑셀 다운로드
	 * @Method Name : paperNumberExcelDownload
	 * @Date : 2019. 2. 26.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 */
	@RequestMapping("/paper/number/excel/download")
	public void paperNumberExcelDownload(Parameter<?> parameter) throws Exception {
		List<PrCouponPaperNumber> list = couponService.getPrCouponPaperNumberList(parameter.getString("cpnNo"));

		ExcelValue excelValue = ExcelValue.builder(0, 0).columnNames(Arrays.asList("paperNoText")).data(list).build();

		parameter.downloadExcel("couponPaperNumber", excelValue);
	}

	/**
	 * @Desc : 쿠폰 상품 업로드 SAMPLE 다운로드
	 * @Method Name : couponProductExcelSampleDown
	 * @Date : 2020. 2. 17.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/product/coupon-product-excel-sample-down")
	public void couponProductExcelSampleDown(Parameter<?> parameter) throws Exception {
		parameter.downloadExcelTemplate("promotion/coupon/excel/couponProductexcelUploadSample");
	}

	/**
	 * @Desc : 선택 상품 삭제 처리
	 * @Method Name : couponProductDelte
	 * @Date : 2020. 2. 17.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/product/coupon-product-delete")
	public void couponProductDelte(Parameter<PdProductMappingVO> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("vndrNoAddr",
				couponService.deleteCouponProductInfo(parameter.getString("cpnNo"), parameter.get().getPrdtNoList()));

		writeJson(parameter, resultMap);
	}

}