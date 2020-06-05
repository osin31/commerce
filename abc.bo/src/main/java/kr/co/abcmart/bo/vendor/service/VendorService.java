package kr.co.abcmart.bo.vendor.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.board.model.master.BdContactUs;
import kr.co.abcmart.bo.board.model.master.BdContactUsMemo;
import kr.co.abcmart.bo.board.model.master.BdCorprBoard;
import kr.co.abcmart.bo.board.model.master.BdCorprBoardMemo;
import kr.co.abcmart.bo.board.model.master.BdMemberCounsel;
import kr.co.abcmart.bo.board.model.master.BdMemberCounselAttachFile;
import kr.co.abcmart.bo.board.model.master.BdMemberCounselMemo;
import kr.co.abcmart.bo.board.repository.master.BdCorprBoardDao;
import kr.co.abcmart.bo.board.repository.master.BdCorprBoardMemoDao;
import kr.co.abcmart.bo.board.repository.master.BdMemberCounselAttachFileDao;
import kr.co.abcmart.bo.board.repository.master.BdMemberCounselDao;
import kr.co.abcmart.bo.board.service.BdInquiryService;
import kr.co.abcmart.bo.cmm.model.master.CmCounselScript;
import kr.co.abcmart.bo.cmm.model.master.CmEmailSendHistory;
import kr.co.abcmart.bo.cmm.model.master.CmHoliday;
import kr.co.abcmart.bo.cmm.service.CmCounselScriptService;
import kr.co.abcmart.bo.cmm.service.HolidayService;
import kr.co.abcmart.bo.cmm.service.MailService;
import kr.co.abcmart.bo.cmm.service.MessageService;
import kr.co.abcmart.bo.cmm.vo.MessageVO;
import kr.co.abcmart.bo.member.service.MemberService;
import kr.co.abcmart.bo.product.model.master.DpBrand;
import kr.co.abcmart.bo.product.model.master.SyStandardCategory;
import kr.co.abcmart.bo.product.service.BrandService;
import kr.co.abcmart.bo.product.service.ProductInsideAddInfoService;
import kr.co.abcmart.bo.product.service.ProductService;
import kr.co.abcmart.bo.product.service.StandardCategoryService;
import kr.co.abcmart.bo.product.vo.DpBrandSearchVO;
import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.repository.master.SyAdminDao;
import kr.co.abcmart.bo.system.service.AdminService;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.model.master.VdVendorBrand;
import kr.co.abcmart.bo.vendor.model.master.VdVendorBrandEmployeeDiscount;
import kr.co.abcmart.bo.vendor.model.master.VdVendorChargeMd;
import kr.co.abcmart.bo.vendor.model.master.VdVendorDefaultCommission;
import kr.co.abcmart.bo.vendor.model.master.VdVendorDeliveryGuide;
import kr.co.abcmart.bo.vendor.model.master.VdVendorDisplayChnnl;
import kr.co.abcmart.bo.vendor.model.master.VdVendorExceptionCommission;
import kr.co.abcmart.bo.vendor.model.master.VdVendorManager;
import kr.co.abcmart.bo.vendor.model.master.VdVendorStandardCategory;
import kr.co.abcmart.bo.vendor.model.master.VdVendorWrhsDlvyAddress;
import kr.co.abcmart.bo.vendor.repository.master.VdVendorBrandDao;
import kr.co.abcmart.bo.vendor.repository.master.VdVendorBrandEmployeeDiscountDao;
import kr.co.abcmart.bo.vendor.repository.master.VdVendorChargeMdDao;
import kr.co.abcmart.bo.vendor.repository.master.VdVendorDao;
import kr.co.abcmart.bo.vendor.repository.master.VdVendorDefaultCommissionDao;
import kr.co.abcmart.bo.vendor.repository.master.VdVendorDeliveryGuideDao;
import kr.co.abcmart.bo.vendor.repository.master.VdVendorDisplayChnnlDao;
import kr.co.abcmart.bo.vendor.repository.master.VdVendorExceptionCommissionDao;
import kr.co.abcmart.bo.vendor.repository.master.VdVendorManagerDao;
import kr.co.abcmart.bo.vendor.repository.master.VdVendorStandardCategoryDao;
import kr.co.abcmart.bo.vendor.repository.master.VdVendorWrhsDlvyAddressDao;
import kr.co.abcmart.bo.vendor.vo.VendorOtherPartVo;
import kr.co.abcmart.common.message.Message;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsDate;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VendorService {

	@Autowired
	private VendorService vendorService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private HolidayService holidayService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private BdInquiryService bdInquiryService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private MailService mailService;

	@Autowired
	private SyAdminDao syAdminDao;

	@Autowired
	private VdVendorDao vdVendorDao;

	@Autowired
	private VdVendorManagerDao vdVendorManagerDao;

	@Autowired
	private VdVendorChargeMdDao vdVendorChargeMdDao;

	@Autowired
	private VdVendorDisplayChnnlDao vdVendorDisplayChnnlDao;

	@Autowired
	private VdVendorDeliveryGuideDao vdVendorDeliveryGuideDao;

	@Autowired
	private VdVendorBrandDao vdVendorBrandDao;

	@Autowired
	private VdVendorStandardCategoryDao vdVendorStandardCategoryDao;

	@Autowired
	private VdVendorExceptionCommissionDao vdVendorExceptionCommissionDao;

	@Autowired
	private VdVendorBrandEmployeeDiscountDao vdVendorBrandEmployeeDiscountDao;

	@Autowired
	private VdVendorWrhsDlvyAddressDao vdVendorWrhsDlvyAddressDao;

	@Autowired
	private VdVendorDefaultCommissionDao vdVendorDefaultCommissionDao;

	@Autowired
	private BdMemberCounselDao bdMemberCounselDao;

	@Autowired
	private BdMemberCounselAttachFileDao bdMemberCounselAttachFileDao;

	@Autowired
	private CmCounselScriptService conselScriptService;

	@Autowired
	private StandardCategoryService standardCategoryService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private BdCorprBoardDao bdCorprBoardDao;

	@Autowired
	private BdCorprBoardMemoDao bdCorprBoardMemoDao;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductInsideAddInfoService productInsideAddInfoService;

	/**
	 * @Desc :입점사목록검색
	 * @Method Name : selectVendorInfoForPaging
	 * @Date : 2019. 2. 7.
	 * @Author : 유성민
	 * @param pageable
	 * @return
	 */
	public Page<VdVendor> selectVendorInfoList(Pageable<VdVendor, VdVendor> pageable) throws Exception {
		int count = vdVendorDao.selectVendorInfoListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(vdVendorDao.selectVendorInfoList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc :입점사업체정보 중복확인
	 * @Method Name : checkInsdMgmtInfo
	 * @Date : 2019. 2. 8.
	 * @Author : 유성민
	 * @param params
	 * @return
	 */
	public Map<String, Object> getVedorInfoDup(VdVendor params) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		int resultCnt = selectVendorInfoCount(params);
		if (resultCnt > 0) {
			resultMap.put("result", Const.BOOLEAN_FALSE);
		} else {
			resultMap.put("result", Const.BOOLEAN_TRUE);
		}
		return resultMap;
	}

	private int selectVendorInfoCount(VdVendor params) throws Exception {
		return vdVendorDao.select(params).size();
	}

	/**
	 * @Desc : 입점사 기본정보 신규저장
	 * @Method Name : setVendorBaseInfo
	 * @Date : 2019. 2. 13.
	 * @Author : 유성민
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setVendorBaseInfo(VdVendor vdVendor) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		UserDetails user = LoginManager.getUserDetails();

		String vndrNo = vdVendorDao.selectVndrNoNextVal();

		// 1.업체저장(VD_VENDOR)
		vdVendor.setVndrNo(vndrNo);
		vendorService.setVdVendorParam(vdVendor, user);
		vdVendorDao.insert(vdVendor);

		// 1.1 입점업체 상태코드 변경에 따른 입점업체 상품 상태 변경 서비스
		try {
			productService.updateVndrSuspdYn(vdVendor);
		} catch (Exception e) {
			log.error("updateVndrSuspdYn : {}", e.getMessage());
		}

		// 2. 기본수수료 저장
		VdVendorDefaultCommission[] defaultCommissionParams = vdVendor.getDefaultCommissionList();
		for (VdVendorDefaultCommission defaultCommissionParam : defaultCommissionParams) {
			defaultCommissionParam.setVndrNo(vndrNo);
			defaultCommissionParam.setRgsterNo(user.getAdminNo());
			// 적용시작일
			defaultCommissionParam.setApplyStartYmd(UtilsDate.getSqlTimeStamp());
			// 적용종료일
			defaultCommissionParam.setApplyEndYmd(Timestamp.valueOf(LocalDateTime.now().plusYears(1000)));
			vdVendorDefaultCommissionDao.insert(defaultCommissionParam);
		}

		// 3.업체전시채널저장(VD_VENDOR_DISPLAY_CHNNL)
		VdVendorDisplayChnnl[] chnnlParams = vdVendor.getChnnlNoList();

		for (VdVendorDisplayChnnl chnnlParam : chnnlParams) {
			chnnlParam.setVndrNo(vndrNo);
			chnnlParam.setRgsterNo(user.getAdminNo());
			vdVendorDisplayChnnlDao.insert(chnnlParam);
		}

		// 4.업체담당자 저장(VD_VENDOR_MANAGER)
		VdVendorManager[] managerParams = vdVendor.getManagerList();
		for (VdVendorManager managerParam : managerParams) {
			if (UtilsText.equals(managerParam.getManagerDelYn(), Const.BOOLEAN_FALSE)) {
				// 업체담당자등록
				managerParam.setVndrNo(vndrNo);
				vendorService.setVdVendorManagerParam(managerParam, user);
				vdVendorManagerDao.insert(managerParam);

				// 관리자등록
				SyAdmin syAdmin = new SyAdmin();
				vendorService.setSyAdminParam(syAdmin, managerParam);
				adminService.setAdmin(syAdmin);
			}
		}

		// 5.업체 브랜드 저장(VD_VENDOR_BRAND)
		VdVendorBrand[] brandParams = vdVendor.getVendorbrandList();
		for (VdVendorBrand brandParam : brandParams) {
			brandParam.setVndrNo(vndrNo);
			brandParam.setBrandNo(brandParam.getVendorBrandNo());
			brandParam.setRgsterNo(user.getAdminNo());
			vdVendorBrandDao.insert(brandParam);
		}

		// 6.업체표준카테고리 저장(VD_VENDOR_STANDARD_CATEGORY)
		VdVendorStandardCategory[] categoryParams = vdVendor.getVendorStdCtgList();
		for (VdVendorStandardCategory categoryParam : categoryParams) {
			categoryParam.setVndrNo(vndrNo);
			categoryParam.setStdCtgrNo(categoryParam.getVendorStdCtgrNo());
			categoryParam.setRgsterNo(user.getAdminNo());
			vdVendorStandardCategoryDao.insert(categoryParam);
		}

		// 7.업체배송안내저장 (VD_VENDOR_DELIVERY_GUIDE)
		VdVendorDeliveryGuide[] deliveryGuideParams = vdVendor.getDeliveryGuideList();
		for (VdVendorDeliveryGuide deliveryGuideParam : deliveryGuideParams) {
			if (UtilsText.isNotEmpty(deliveryGuideParam.getDlvyGuideInfo())) {
				String deliveryGuide = UtilsText.unescapeXss(deliveryGuideParam.getDlvyGuideInfo());
				deliveryGuideParam.setDlvyGuideInfo(deliveryGuide);
				deliveryGuideParam.setVndrNo(vndrNo);
				deliveryGuideParam.setRgsterNo(user.getAdminNo());
				deliveryGuideParam.setModerNo(user.getAdminNo());

				vdVendorDeliveryGuideDao.insert(deliveryGuideParam);
			}
		}

		// 8.업체휴일 등록
		try {
			CmHoliday cmHoliday = new CmHoliday();
			cmHoliday.setVndrNo(vndrNo);
			holidayService.setSystemHolidayVndr(cmHoliday);
		} catch (Exception e) {
			log.error("업체휴일 등록 error :{}", e.getMessage());
		}
		resultMap.put("result", Const.BOOLEAN_TRUE);
		resultMap.put("vndrNo", vndrNo);
		return resultMap;
	}

	/**
	 * @Desc : 업체등록 파라미터 세팅
	 * @Method Name : setVdVendorParam
	 * @Date : 2019. 2. 19.
	 * @Author : 유성민
	 * @param vendorParam
	 * @param user
	 * @throws Exception
	 */
	public void setVdVendorParam(VdVendor vendorParam, UserDetails user) throws Exception {
		vendorParam.setVndrGbnType(Const.VNDR_GBN_TYPE_V); // 입점사
//		vendorParam.setExchngDlvyAmt(vendorParam.getExchngDlvyAmt() != null ? vendorParam.getExchngDlvyAmt() : 0);
//		vendorParam.setChngExchngDlvyAmt(
//				vendorParam.getChngExchngDlvyAmt() != null ? vendorParam.getChngExchngDlvyAmt() : 0);
//		vendorParam.setRtnDlvyAmt(vendorParam.getRtnDlvyAmt() != null ? vendorParam.getRtnDlvyAmt() : 0);
//		vendorParam.setChngRtnDlvyAmt(vendorParam.getChngRtnDlvyAmt() != null ? vendorParam.getChngRtnDlvyAmt() : 0);
		vendorParam.setRgsterNo(user.getAdminNo());
		vendorParam.setModerNo(user.getAdminNo());
	}

	/**
	 * @Desc :업체담당자등록 파라메터 세팅
	 * @Method Name : setVdVendorManagerParam
	 * @Date : 2019. 2. 19.
	 * @Author : 유성민
	 * @param managerParam
	 * @param user
	 * @throws Exception
	 */
	public void setVdVendorManagerParam(VdVendorManager managerParam, UserDetails user) throws Exception {
		// 업체담당자순번 조회
		short vndrMngrNo = vdVendorManagerDao.getVndrMngrNoNextVal(managerParam);
		managerParam.setVndrMngrNo(vndrMngrNo);

		managerParam.setDelYn(Const.BOOLEAN_FALSE);
		managerParam.setRgsterNo(user.getAdminNo());
		managerParam.setModerNo(user.getAdminNo());
	}

	/**
	 * @Desc : 관리자등록 파라미터 세팅
	 * @Method Name : setSyAdminParam
	 * @Date : 2019. 2. 19.
	 * @Author : 유성민
	 * @param syAdmin
	 * @throws Exception
	 */
	public void setSyAdminParam(SyAdmin syAdmin, VdVendorManager managerParam) throws Exception {
		syAdmin.setLoginId(managerParam.getVndrMngrId());
		syAdmin.setAdminName(managerParam.getVndrMngrName());
		syAdmin.setPswdText(managerParam.getPswdText());
		syAdmin.setVndrNo(managerParam.getVndrNo());
		syAdmin.setVndrMngrNo(managerParam.getVndrMngrNo());
		syAdmin.setTelNoText(managerParam.getTelNoText());
		syAdmin.setHdphnNoText(managerParam.getHdphnNoText());
		syAdmin.setEmailAddrText(managerParam.getEmailAddrText());
		syAdmin.setMemberInfoMgmtYn(Const.BOOLEAN_FALSE);
		syAdmin.setBatchErrorAlertYn(Const.BOOLEAN_FALSE);
		syAdmin.setUseYn(Const.BOOLEAN_FALSE);
		syAdmin.setAuthNo(Const.ROLE_VENDER);
	}

	/**
	 * @Desc :업체담당MD 등록 파라미터 세팅
	 * @Method Name : setVdVendorChargeMdParam
	 * @Date : 2019. 2. 19.
	 * @Author : 유성민
	 * @param chargeMdParam
	 * @param user
	 * @throws Exception
	 */
	public void setVdVendorChargeMdParam(VdVendorChargeMd chargeMdParam, UserDetails user) throws Exception {
		// 업체담당MD순번
		chargeMdParam.setChnnlNo(chargeMdParam.getChargeMdChnnlNo());
		int vndrChargeMdSeq = vdVendorChargeMdDao.getVndrChargeMdSeqNextVal(chargeMdParam);
		chargeMdParam.setVndrChargeMdSeq(vndrChargeMdSeq);
		chargeMdParam.setRgsterNo(user.getAdminNo());
	}

	/**
	 * @Desc : 입점사 기본정보 상세
	 * @Method Name : getVendorBaseTabInfo
	 * @Date : 2019. 2. 14.
	 * @Author : 유성민
	 * @param params
	 * @return
	 */
	public Map<String, Object> getVendorBaseTabInfo(VdVendor vdVendor) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		vdVendor.validate();
		String vndrNo = vdVendor.getVndrNo();
		// 업체
		VdVendor vendorBaseInfo = vendorService.getVendorBaseInfo(vdVendor);
		vendorBaseInfo.setStatus(Const.CRUD_U);
		resultMap.put("vendorInfo", vendorBaseInfo);

		// 기본수수료 조회
		VdVendorDefaultCommission defaultCommissionParams = new VdVendorDefaultCommission();
		defaultCommissionParams.setVndrNo(vndrNo);
		List<VdVendorDefaultCommission> defaultCommissionList = vdVendorDefaultCommissionDao
				.getVendorDefaultCommissionList(defaultCommissionParams).stream()
				.sorted(Comparator.comparing(VdVendorDefaultCommission::getVndrDfltCmsnSeq))
				.collect(Collectors.toList());

		resultMap.put("defaultCommissionList", defaultCommissionList);

		// 업체전시채널
		VdVendorDisplayChnnl vdVendorDisplayChnnl = new VdVendorDisplayChnnl();
		vdVendorDisplayChnnl.setVndrNo(vndrNo);
		List<VdVendorDisplayChnnl> chnnlList = vdVendorDisplayChnnlDao.select(vdVendorDisplayChnnl);
		resultMap.put("chnnlList", chnnlList);

		// 관리자
		SyAdmin syAdmin = new SyAdmin();
		syAdmin.setVndrNo(vndrNo);

		List<SyAdmin> adminList = syAdminDao.select(syAdmin);

		// 업체담당자
		VdVendorManager vdVendorManager = new VdVendorManager();
		vdVendorManager.setVndrNo(vndrNo);
		vdVendorManager.setDelYn(Const.BOOLEAN_FALSE);
		List<VdVendorManager> managerList = vdVendorManagerDao.select(vdVendorManager);
		for (VdVendorManager manager : managerList) {
			for (SyAdmin admin : adminList) {
				if (UtilsText.equals(String.valueOf(manager.getVndrMngrNo()), String.valueOf(admin.getVndrMngrNo()))) {
					manager.setVndrMngrId(admin.getLoginId());
					manager.setVndrMngrName(admin.getAdminName());
					manager.setTelNoText(admin.getTelNoText());
					manager.setHdphnNoText(admin.getHdphnNoText());
					manager.setEmailAddrText(admin.getEmailAddrText());
					manager.setVndrMngrAdminNo(admin.getAdminNo());
					break;
				}
			}
		}
		resultMap.put("managerList", managerList);

		// 브랜드
		DpBrandSearchVO criteria = new DpBrandSearchVO();
		List<DpBrand> dpBrandList = brandService.searchBrandApi(criteria);

		// 업체브랜드
		VdVendorBrand vdVendorBrand = new VdVendorBrand();
		vdVendorBrand.setVndrNo(vndrNo);
		List<VdVendorBrand> vendorBrandList = vdVendorBrandDao.select(vdVendorBrand);
		for (VdVendorBrand vendorBrand : vendorBrandList) {
			for (DpBrand dpBrand : dpBrandList) {
				if (UtilsText.equals(vendorBrand.getBrandNo(), dpBrand.getBrandNo())) {
					vendorBrand.setBrandName(dpBrand.getBrandName());
					break;
				}
			}
		}
		resultMap.put("vendorBrandList", vendorBrandList);

		// 표준카테고리 조회
		SyStandardCategory params = new SyStandardCategory();
		params.setLevel("1");
		List<SyStandardCategory> standardCategoryList = standardCategoryService.getStandardCategoryList(params);
		resultMap.put("standardCategoryList", standardCategoryList);

		// 업체표준카테고리
		VdVendorStandardCategory vdVendorStandardCategory = new VdVendorStandardCategory();
		vdVendorStandardCategory.setVndrNo(vndrNo);
		List<VdVendorStandardCategory> vendorCategoryList = vdVendorStandardCategoryDao
				.select(vdVendorStandardCategory);

		for (VdVendorStandardCategory vendorCategory : vendorCategoryList) {
			for (SyStandardCategory standardCategory : standardCategoryList) {
				if (UtilsText.equals(vendorCategory.getStdCtgrNo(), standardCategory.getStdCtgrNo())) {
					vendorCategory.setStdCtgrName(standardCategory.getStdCtgrName());
					break;
				}
			}
		}
		resultMap.put("vendorCategoryList", vendorCategoryList);

		// 업체배송안내
		VdVendorDeliveryGuide vdVendorDeliveryGuide = new VdVendorDeliveryGuide();
		vdVendorDeliveryGuide.setVndrNo(vndrNo);
		List<VdVendorDeliveryGuide> guideList = vdVendorDeliveryGuideDao.select(vdVendorDeliveryGuide);
		resultMap.put("guideList", guideList);

		return resultMap;
	}

	/**
	 * @Desc :업체정보 조회
	 * @Method Name : getVendorBaseInfo
	 * @Date : 2019. 2. 12.
	 * @Author : 유성민
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public VdVendor getVendorBaseInfo(VdVendor params) throws Exception {
		params.validate();
		return vdVendorDao.select(params).stream().findFirst().orElse(null);
	}

	/**
	 * @Desc :입점사 기본정보 수정
	 * @Method Name : updateVendorBaseInfo
	 * @Date : 2019. 2. 22.
	 * @Author : 유성민
	 * @param parameter
	 * @return
	 */
	public Map<String, Object> updateVendorBaseInfo(VdVendor vdVendor) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		UserDetails user = LoginManager.getUserDetails();
		String vndrNo = "";

		// 1-1 업체정보 조회
		VdVendor oldData = vdVendorDao.selectByPrimaryKey(vdVendor);

		// 1-2.업체수정(VD_VENDOR)
		vndrNo = vdVendor.getVndrNo();
		vdVendor.setModerNo(user.getAdminNo());
		vdVendor.setModDtm(UtilsDate.getSqlTimeStamp());
		vdVendor.setCrprtNoText(UtilsText.isBlank(vdVendor.getCrprtNoText()) ? "" : vdVendor.getCrprtNoText());
		vdVendor.setAsMngrText(UtilsText.isBlank(vdVendor.getAsMngrText()) ? "" : vdVendor.getAsMngrText());
		vdVendorDao.update(vdVendor);

		productInsideAddInfoService.setVendorAsMngrText(vdVendor); // 입점사 AS담당자 연락처 일괄변경

		// 1.3 입점업체 상태코드 변경에 따른 입점업체 상품 상태 변경 서비스
		if (!UtilsText.equals(oldData.getVndrStatCode(), vdVendor.getVndrStatCode())) {
			productService.updateVndrSuspdYn(vdVendor);
		}

		// 2. 기본수수료 수정
		VdVendorDefaultCommission[] defaultCommissionParams = vdVendor.getDefaultCommissionList();
		List<VdVendorDefaultCommission> commissionList = Arrays.asList(defaultCommissionParams);

		long cmsnRateNullLength = commissionList.stream().filter(x -> x.getDfltCmsnRate() == null).count();

		for (VdVendorDefaultCommission defaultCommissionParam : defaultCommissionParams) {
			defaultCommissionParam.setVndrNo(vndrNo);
			defaultCommissionParam.setRgsterNo(user.getAdminNo());

			if (defaultCommissionParam.getVndrDfltCmsnSeq() == null
					&& defaultCommissionParam.getDfltCmsnRate() != null) {
				// 신규저장시
				int vndrDfltCmsnSeq = vdVendorDefaultCommissionDao.getVndrDfltCmsnSeqNextVal(defaultCommissionParam);
				defaultCommissionParam.setVndrDfltCmsnSeq(vndrDfltCmsnSeq);
				defaultCommissionParam.setApplyStartYmd(Timestamp.valueOf(LocalDateTime.now().plusDays(1))); // 적용시작일
				defaultCommissionParam.setApplyEndYmd(Timestamp.valueOf(LocalDateTime.now().plusYears(1000))); // 적용종료일
				vdVendorDefaultCommissionDao.insert(defaultCommissionParam);
			} else if (defaultCommissionParam.getDfltCmsnRate() == null
					&& defaultCommissionParam.getVndrDfltCmsnSeq() != null) {
				// 변결할 수수료 삭제시
				vdVendorDefaultCommissionDao.delete(defaultCommissionParam);

				defaultCommissionParam.setVndrDfltCmsnSeq(defaultCommissionParam.getVndrDfltCmsnSeq() - 1);
				defaultCommissionParam.setApplyStartYmd(null);
				defaultCommissionParam.setApplyEndYmd(Timestamp.valueOf(LocalDateTime.now().plusYears(1000)));
				vdVendorDefaultCommissionDao.update(defaultCommissionParam);
			} else if (cmsnRateNullLength == 0) {
				// 변경할 수수료 수정시
				// 적용종료일
				if (defaultCommissionParam.getApplyStartYmd() != null
						&& defaultCommissionParam.getApplyStartYmd().before(UtilsDate.getSqlTimeStamp())) {
					defaultCommissionParam.setApplyEndYmd(Timestamp.valueOf(LocalDateTime.now()));
				}
				vdVendorDefaultCommissionDao.update(defaultCommissionParam);
			}
		}

		// 3.업체전시채널수정(VD_VENDOR_DISPLAY_CHNNL)
		vdVendorDisplayChnnlDao.deleteDisplayChnnlByVndrNo(vndrNo);
		VdVendorDisplayChnnl[] chnnlParams = vdVendor.getChnnlNoList();
		for (VdVendorDisplayChnnl chnnlParam : chnnlParams) {
			chnnlParam.setVndrNo(vndrNo);
			chnnlParam.setRgsterNo(user.getAdminNo());
			vdVendorDisplayChnnlDao.insert(chnnlParam);
		}

		// 4.업체담당자 수정(VD_VENDOR_MANAGER)
		VdVendorManager[] managerParams = vdVendor.getManagerList();
		for (VdVendorManager managerParam : managerParams) {
			managerParam.setVndrNo(vndrNo);
			SyAdmin syAdmin = new SyAdmin();

			if (UtilsText.equals(managerParam.getManagetStatus(), Const.CRUD_C)) {
				if (UtilsText.equals(managerParam.getManagerDelYn(), Const.BOOLEAN_FALSE)) {
					// 업체담당자등록
					vendorService.setVdVendorManagerParam(managerParam, user);
					vdVendorManagerDao.insert(managerParam);

					// 관리자등록
					vendorService.setSyAdminParam(syAdmin, managerParam);
					adminService.setAdmin(syAdmin);
				}
			} else if (UtilsText.equals(managerParam.getManagetStatus(), Const.CRUD_U)) {

				managerParam.setDelYn(managerParam.getManagerDelYn());
				managerParam.setModerNo(user.getAdminNo());
				vdVendorManagerDao.update(managerParam);

				if (UtilsText.equals(managerParam.getDelYn(), Const.BOOLEAN_TRUE)) {
					syAdmin.setUseYn(Const.BOOLEAN_FALSE);
				} else {
					syAdmin.setAdminName(managerParam.getVndrMngrName());
					syAdmin.setHdphnNoText(managerParam.getHdphnNoText());
					syAdmin.setTelNoText(managerParam.getTelNoText());
					syAdmin.setEmailAddrText(managerParam.getEmailAddrText());
				}
				syAdmin.setAdminNo(managerParam.getVndrMngrAdminNo());
				syAdmin.setModerNo(user.getAdminNo());
				adminService.updateAdminDefaultData(syAdmin);
			}
		}

		// 5.업체표준카테고리 수정(VD_VENDOR_STANDARD_CATEGORY)
		VdVendorStandardCategory[] categoryParams = vdVendor.getVendorStdCtgList();
		for (VdVendorStandardCategory categoryParam : categoryParams) {
			if (UtilsText.equals(categoryParam.getVendorStdCtgrStatus(), Const.CRUD_C)) {
				categoryParam.setVndrNo(vndrNo);
				categoryParam.setStdCtgrNo(categoryParam.getVendorStdCtgrNo());
				categoryParam.setRgsterNo(user.getAdminNo());
				vdVendorStandardCategoryDao.insert(categoryParam);
			}
		}

		// 6.업체 브랜드 수정(VD_VENDOR_BRAND)
		VdVendorBrand[] brandParams = vdVendor.getVendorbrandList();
		for (VdVendorBrand brandParam : brandParams) {
			if (UtilsText.equals(brandParam.getVendorBrandStatus(), Const.CRUD_C)) {
				brandParam.setVndrNo(vndrNo);
				brandParam.setBrandNo(brandParam.getVendorBrandNo());
				brandParam.setRgsterNo(user.getAdminNo());
				vdVendorBrandDao.insert(brandParam);
			}
		}

		// 7.업체배송안내 수정 (VD_VENDOR_DELIVERY_GUIDE)
		vendorService.updateVendorDlvyGuide(vdVendor, user);

		resultMap.put("result", Const.BOOLEAN_TRUE);
		resultMap.put("vndrNo", vndrNo);
		return resultMap;
	}

	/**
	 * @Desc :입점사 기본정보 수정(PO전용)
	 * @Method Name : updateVendorBaseInfoPo
	 * @Date : 2019. 3. 11.
	 * @Author : 유성민
	 * @param params
	 * @return
	 */
	public Map<String, Object> updateVendorBaseInfoPo(VdVendor vdVendor) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		UserDetails user = LoginManager.getUserDetails();
		String vndrNo = vdVendor.getVndrNo();

		// AS책임자 전화번호 수정
		vendorService.updateAsMngrText(vdVendor);

		SyAdmin syAdmin = null;
		// 업체담당자 수정(VD_VENDOR_MANAGER)
		VdVendorManager[] managerParams = vdVendor.getManagerList();
		for (VdVendorManager managerParam : managerParams) {
			managerParam.setVndrNo(vndrNo);
			managerParam.setModerNo(user.getAdminNo());
			vdVendorManagerDao.update(managerParam);
			syAdmin = new SyAdmin();
			syAdmin.setAdminNo(managerParam.getVndrMngrAdminNo());
			syAdmin.setTelNoText(managerParam.getTelNoText());
			syAdmin.setHdphnNoText(managerParam.getHdphnNoText());
			syAdmin.setEmailAddrText(managerParam.getEmailAddrText());
			syAdmin.setModerNo(user.getAdminNo());
			adminService.updateAdminDefaultData(syAdmin);
		}
		// 업체배송안내 수정
		vendorService.updateVendorDlvyGuide(vdVendor, user);

		resultMap.put("result", Const.BOOLEAN_TRUE);
		resultMap.put("vndrNo", vndrNo);
		return resultMap;
	}

	/**
	 * @Desc : 입점사부가정보 조회
	 * @Method Name : getVendorAddInfoCreate
	 * @Date : 2019. 2. 25.
	 * @Author : 유성민
	 * @param parameter
	 */
	public Map<String, Object> getVendorAddInfoTabBaseInfo(VdVendor vdVendor) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		String vndrNo = "";

		// 업체
		vndrNo = vdVendor.getVndrNo();
		VdVendor vendorBaseInfo = vendorService.getVendorBaseInfo(vdVendor);
		resultMap.put("vendorInfo", vendorBaseInfo);

		// 브랜드
		DpBrandSearchVO criteria = new DpBrandSearchVO();
		List<DpBrand> dpBrandList = brandService.searchBrandApi(criteria);

		// 업체브랜드
		VdVendorBrand vdVendorBrand = new VdVendorBrand();
		vdVendorBrand.setVndrNo(vndrNo);
		List<VdVendorBrand> vendorBrandList = vdVendorBrandDao.select(vdVendorBrand);
		for (VdVendorBrand vendorBrand : vendorBrandList) {
			for (DpBrand dpBrand : dpBrandList) {
				if (UtilsText.equals(vendorBrand.getBrandNo(), dpBrand.getBrandNo())) {
					vendorBrand.setBrandName(dpBrand.getBrandName());
					break;
				}
			}
		}
		resultMap.put("vendorBrandList", vendorBrandList);

		// 표준카테고리 조회
		SyStandardCategory params = new SyStandardCategory();
		params.setLevel("1");
		List<SyStandardCategory> standardCategoryList = standardCategoryService.getStandardCategoryList(params);
		resultMap.put("standardCategoryList", standardCategoryList);

		// 업체표준카테고리
		VdVendorStandardCategory vdVendorStandardCategory = new VdVendorStandardCategory();
		vdVendorStandardCategory.setVndrNo(vndrNo);
		List<VdVendorStandardCategory> vendorCategoryList = vdVendorStandardCategoryDao
				.select(vdVendorStandardCategory);
		for (VdVendorStandardCategory vendorCategory : vendorCategoryList) {
			for (SyStandardCategory standardCategory : standardCategoryList) {
				if (UtilsText.equals(vendorCategory.getStdCtgrNo(), standardCategory.getStdCtgrNo())) {
					vendorCategory.setStdCtgrName(standardCategory.getStdCtgrName());
					break;
				}
			}
		}
		resultMap.put("vendorCategoryList", vendorCategoryList);

		// 예외 수수료 정보
		VdVendorExceptionCommission vdVendorExceptionCommission = new VdVendorExceptionCommission();
		vdVendorExceptionCommission.setVndrNo(vndrNo);
		vdVendorExceptionCommission.setDelYn(Const.BOOLEAN_FALSE);
		List<VdVendorExceptionCommission> commissionList = vdVendorExceptionCommissionDao
				.selectVendorExceptionCommission(vdVendorExceptionCommission);
		resultMap.put("commissionList", commissionList);

		// 임직원 할인 여부 정보
		VdVendorBrandEmployeeDiscount vdVendorBrandEmployeeDiscount = new VdVendorBrandEmployeeDiscount();
		vdVendorBrandEmployeeDiscount.setVndrNo(vndrNo);
		vdVendorBrandEmployeeDiscount.setDelYn(Const.BOOLEAN_FALSE);
		Collection<List<VdVendorBrandEmployeeDiscount>> discountList = vendorService
				.getBrandEmployeeDiscountByGroup(vdVendorBrandEmployeeDiscount);
		resultMap.put("discountList", discountList);

		// 입점사 기본 입출고 설정 정보
		VdVendorWrhsDlvyAddress vdVendorWrhsDlvyAddress = new VdVendorWrhsDlvyAddress();
		vdVendorWrhsDlvyAddress.setVndrNo(vndrNo);
		Map<String, List<VdVendorWrhsDlvyAddress>> dlvyAddList = getWrhsDlvyAddressByGroup(vdVendorWrhsDlvyAddress);
		resultMap.put("dlvyAddList", dlvyAddList);

		return resultMap;
	}

	/**
	 * @Desc :업체브랜드임직원할인 정보
	 * @Method Name : getBrandEmployeeDiscountByGroup
	 * @Date : 2019. 3. 4.
	 * @Author : 유성민
	 * @param vdVendorBrandEmployeeDiscount
	 * @return
	 * @throws Exception
	 */
	public Collection<List<VdVendorBrandEmployeeDiscount>> getBrandEmployeeDiscountByGroup(
			VdVendorBrandEmployeeDiscount vdVendorBrandEmployeeDiscount) throws Exception {
		List<VdVendorBrandEmployeeDiscount> discountList = vdVendorBrandEmployeeDiscountDao
				.selectBrandEmployeeDiscount(vdVendorBrandEmployeeDiscount);
		if (discountList == null) {
			return null;
		}
		return discountList.stream().collect(Collectors.groupingBy(VdVendorBrandEmployeeDiscount::getDscntRate))
				.values();
	}

	/**
	 * @Desc :입점사 입출고지 지정 조회
	 * @Method Name : getWrhsDlvyAddressByGroup
	 * @Date : 2019. 2. 27.
	 * @Author : 유성민
	 * @param vdVendorWrhsDlvyAddress
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<VdVendorWrhsDlvyAddress>> getWrhsDlvyAddressByGroup(
			VdVendorWrhsDlvyAddress vdVendorWrhsDlvyAddress) throws Exception {
		List<VdVendorWrhsDlvyAddress> dlvyAddList = vdVendorWrhsDlvyAddressDao.select(vdVendorWrhsDlvyAddress);
		if (dlvyAddList == null) {
			return null;
		}
		return dlvyAddList.stream().collect(Collectors.groupingBy(VdVendorWrhsDlvyAddress::getWrhsDlvyGbnType));
	}

	/**
	 * @Desc :입점사 부가정보 저장
	 * @Method Name : updateVendorAddInfo
	 * @Date : 2019. 2. 27.
	 * @Author : 유성민
	 * @param parameter
	 * @return
	 */
	public Map<String, Object> setVendorAddInfo(VdVendor vdVendor) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		UserDetails user = LoginManager.getUserDetails();

		String vndrNo = vdVendor.getVndrNo();

		// 예외 수수료 정보
		if (vdVendor.getExceptionCommissionList() != null) {
			VdVendorExceptionCommission[] commissionParams = vdVendor.getExceptionCommissionList();
			for (VdVendorExceptionCommission commissionParam : commissionParams) {
				log.debug("commissionParam : {}", commissionParam);
				commissionParam.setVndrNo(vndrNo);
				commissionParam.setRgsterNo(user.getAdminNo());
				commissionParam.setModerNo(user.getAdminNo());

				if (commissionParam.getVndrExceptCmsnSeq() == null
						&& UtilsText.equals(commissionParam.getDelYn(), Const.BOOLEAN_FALSE)) { // 신규등록
					// 업체예외수수료순번
					int vndrExceptCmsnSeq = vdVendorExceptionCommissionDao.selectVndrExceptCmsnSeq(commissionParam);
					commissionParam.setVndrExceptCmsnSeq(vndrExceptCmsnSeq);
					vdVendorExceptionCommissionDao.insert(commissionParam);

				} else if (commissionParam.getVndrExceptCmsnSeq() != null) { // 기존데이터 update
					if (UtilsText.equals(commissionParam.getDelYn(), Const.BOOLEAN_TRUE)) {
						vdVendorExceptionCommissionDao.updateVendorExceptionCommissionDelYn(commissionParam);
					} else {
						vdVendorExceptionCommissionDao.update(commissionParam);
					}
				}
			}
		}

		// 임직원 할인 여부
		if (vdVendor.getEmployeeDiscountList() != null) {
			VdVendorBrandEmployeeDiscount[] discountParams = vdVendor.getEmployeeDiscountList();
			for (VdVendorBrandEmployeeDiscount discountParam : discountParams) {
				discountParam.setVndrNo(vndrNo);
				discountParam.setRgsterNo(user.getAdminNo());
				discountParam.setModerNo(user.getAdminNo());

				if (discountParam.getVndrBrandEmpDscntSeq() == null
						&& UtilsText.equals(discountParam.getDelYn(), Const.BOOLEAN_FALSE)) {
					// 업체브랜드임직원할인순번
					int vndrBrandEmpDscntSeq = vdVendorBrandEmployeeDiscountDao
							.selectVndrBrandEmpDscntSeq(discountParam);
					discountParam.setVndrBrandEmpDscntSeq(vndrBrandEmpDscntSeq);
					// 적용시작일
					discountParam.setApplyStartYmd(UtilsDate.getSqlTimeStamp());
					// 적용종료일
					discountParam.setApplyEndYmd(Timestamp.valueOf(LocalDateTime.now().plusYears(100)));

					vdVendorBrandEmployeeDiscountDao.insert(discountParam);

				} else if (discountParam.getVndrBrandEmpDscntSeq() != null) {
					if (UtilsText.equals(discountParam.getDelYn(), Const.BOOLEAN_TRUE)) {
						// 젹용종료일, 삭제여부만 업데이트
						// 할인율
						discountParam.setDscntRate(null);
						// 적용시작일
						discountParam.setApplyStartYmd(null);
						// 적용종료일
						discountParam.setApplyEndYmd(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));

						vdVendorBrandEmployeeDiscountDao.update(discountParam);
					}
				}
			}
		}

		// 입점사 입출고 설정
		VdVendorWrhsDlvyAddress[] wrhsDlvyAddressParams = vdVendor.getDlvyAddressList();
		for (VdVendorWrhsDlvyAddress wrhsDlvyAddressParam : wrhsDlvyAddressParams) {
			wrhsDlvyAddressParam.setVndrNo(vndrNo);
			wrhsDlvyAddressParam.setRgsterNo(user.getAdminNo());
			wrhsDlvyAddressParam.setModerNo(user.getAdminNo());

			if (vdVendorWrhsDlvyAddressDao.selectByPrimaryKey(wrhsDlvyAddressParam) != null) {
				vdVendorWrhsDlvyAddressDao.update(wrhsDlvyAddressParam);
			} else {
				vdVendorWrhsDlvyAddressDao.insert(wrhsDlvyAddressParam);
			}
		}

		resultMap.put("result", Const.BOOLEAN_TRUE);
		return resultMap;
	}

	/**
	 * @Desc :입점사 부가정보 저장(PO전용)
	 * @Method Name : setVendorAddInfoPo
	 * @Date : 2019. 3. 11.
	 * @Author : 유성민
	 * @param params
	 * @return
	 */
	public Map<String, Object> setVendorAddInfoPo(VdVendor params) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		resultMap.put("result", Const.BOOLEAN_TRUE);
		return resultMap;
	}

	/**
	 * @Desc : 업체예외수수료 이력조회
	 * @Method Name : getVendorExceptionCommissionHistory
	 * @Date : 2019. 3. 6.
	 * @Author : 유성민
	 * @param pageable
	 */
	public Page<VdVendorExceptionCommission> getVendorExceptionCommissionHistory(
			Pageable<VdVendorExceptionCommission, VdVendorExceptionCommission> pageable) throws Exception {

		int count = vdVendorExceptionCommissionDao.selectVendorExceptionCommissionHistoryListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			// 표준카테고리 조회
			SyStandardCategory params = new SyStandardCategory();
			params.setLevel("1");
			List<SyStandardCategory> standardCategoryList = standardCategoryService.getStandardCategoryList(params);

			// 브랜드
			DpBrandSearchVO criteria = new DpBrandSearchVO();
			List<DpBrand> dpBrandList = brandService.searchBrandApi(criteria);

			// 관리자
			SyAdmin syAdmin = new SyAdmin();
			List<SyAdmin> adminList = syAdminDao.select(syAdmin);

			List<VdVendorExceptionCommission> histroyList = vdVendorExceptionCommissionDao
					.selectVendorExceptionCommissionHistoryList(pageable);

			for (VdVendorExceptionCommission history : histroyList) {
				String commissionstdCtgrName = standardCategoryList.stream()
						.filter(x -> UtilsText.equals(x.getStdCtgrNo(), history.getStdCtgrNo()))
						.map(x -> x.getStdCtgrName()).findFirst().orElse(null);
				history.setCommissionstdCtgrName(commissionstdCtgrName);

				String commissionBrandName = dpBrandList.stream()
						.filter(x -> UtilsText.equals(x.getBrandNo(), history.getBrandNo())).map(x -> x.getBrandName())
						.findFirst().orElse(null);
				history.setCommissionBrandName(commissionBrandName);

				String rgsterName = adminList.stream()
						.filter(x -> UtilsText.equals(x.getAdminNo(), history.getRgsterNo())).map(x -> x.getAdminName())
						.findFirst().orElse(null);
				history.setRgsterName(rgsterName);

				String moderName = adminList.stream()
						.filter(x -> UtilsText.equals(x.getAdminNo(), history.getModerNo())).map(x -> x.getAdminName())
						.findFirst().orElse(null);
				history.setModerName(moderName);

				String applyPeriod = "";
				if (UtilsText.equals(history.getDelYn(), Const.BOOLEAN_TRUE)) {
					history.setCommissionGubun("D");
//					applyPeriod = UtilsDate.formatter(history.getModDtm());
//					history.setApplyPeriod(applyPeriod);
				} else {
					history.setCommissionGubun("E");
				}
				applyPeriod = UtilsText.concat(UtilsDate.formatter(history.getApplyStartYmd()), " ~ ",
						UtilsDate.formatter(history.getApplyEndYmd()));

				history.setApplyPeriod(applyPeriod);

			}

			pageable.setContent(histroyList);
		}

		return pageable.getPage();

	}

	/**
	 * @Desc :업체전시채널리스트
	 * @Method Name : selectVendorDisplayChnnl
	 * @Date : 2019. 3. 19.
	 * @Author : 유성민
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<VdVendorDisplayChnnl> selectVendorDisplayChnnlList(VdVendorDisplayChnnl params) throws Exception {
		params.validate();
		return vdVendorDisplayChnnlDao.selectVendorDisplayChnnlList(params);
	}

	/**
	 * @Desc : 업체상태코드 조회
	 * @Method Name : getVndrStatCode
	 * @Date : 2019. 5. 21.
	 * @Author : 유성민
	 * @param vndrNo
	 * @return
	 */
	public String getVndrStatCode(String vndrNo) throws Exception {
		return vdVendorDao.selectVndrStatCode(vndrNo);
	}

	/**
	 * @Desc :입점문의 목록메인
	 * @Method Name : getVendorInquiryMain
	 * @Date : 2019. 2. 15.
	 * @Author : 안석진
	 * @param pageable
	 * @return
	 */
	public Map<String, Object> getVendorInquiryMain(Parameter<?> parameter) throws Exception {
		Map<String, Object> result = new HashMap<>();

		String[] codeFields = { CommonCode.ASWR_STAT_CODE }; // 답변상태

		// 답변 상태 코드 조회
		result.put("codeList", commonCodeService.getAllCodeListByGroup(codeFields));
		// 입점/제휴 문의 코드 조회
		result.put("inquiryDtlCodeList", commonCodeService.getCodeNoNameFilterAddInfo(CommonCode.CNSL_TYPE_DTL_CODE,
				CommonCode.CNSL_TYPE_CODE_VENDOR_INQUIRY));
		// 사이트 구분
		result.put("siteNoList", siteService.getSiteList());

		return result;
	}

	/**
	 * @Desc :입점문의검색
	 * @Method Name : selectVendorInquireList
	 * @Date : 2019. 2. 18.
	 * @Author : 안석진
	 * @param pageable
	 * @return
	 */
	public Page<BdContactUs> selectVendorInquiryList(Pageable<BdContactUs, BdContactUs> pageable) throws Exception {
		int count = vdVendorDao.selectVendorInquiryListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(vdVendorDao.selectVendorInquiryList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc :입점문의상세 팝업
	 * @Method Name : selectVendorInquiryDetail
	 * @Date : 2019. 2. 18.
	 * @Author : 안석진
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getVendorInquiryDetail(Parameter<?> parameter) throws Exception {
		Map<String, Object> result = new HashMap<>();

		result.put("inquiryDetail", vdVendorDao.selectVendorInquiryDetail(parameter.create(BdContactUs.class)));

		// 상담유형코드가 입점문의인것만 보고 상담유형상세코드는 보지 않음
		// 기획의도상 입점문의 메뉴에서 상담유형상세코드는 의미 없음
		CmCounselScript cmCounselScript = new CmCounselScript();
		cmCounselScript.setCnslTypeCode(CommonCode.CNSL_TYPE_CODE_VENDOR_INQUIRY);
		cmCounselScript.setUseYn("Y");

		result.put("counselScriptList", conselScriptService.selectCouselScriptListForUse(cmCounselScript));

		return result;
	}

	/**
	 *
	 * @Desc : 입점문의 관리자 메모 등록
	 * @Method Name : setVendorAdminMemo
	 * @Date : 2019. 2. 20.
	 * @Author : ansuk
	 * @param params
	 * @throws Exception
	 */
	public Map<String, Object> setVendorAdminMemo(BdContactUsMemo bdContactUsMemo) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		UserDetails user = LoginManager.getUserDetails();
		bdContactUsMemo.setRgsterNo(user.getAdminNo());
		int resultCnt = vdVendorDao.insertVendorAdminMemo(bdContactUsMemo);

		if (resultCnt > 0) {
			resultMap.put("result", Const.BOOLEAN_TRUE);
		} else {
			resultMap.put("result", Const.BOOLEAN_FALSE);
		}
		return resultMap;
	}

	/**
	 *
	 * @Desc : 입점문의 관리자 메모 목록
	 * @Method Name : selectVendorInquiryMemoList
	 * @Date : 2019. 2. 20.
	 * @Author : ansuk
	 * @param bdContactUsMemo
	 * @return
	 * @throws Exception
	 */
	public List<BdContactUsMemo> selectVendorInquiryMemoList(BdContactUsMemo bdContactUsMemo) throws Exception {
		return vdVendorDao.selectVendorInquiryMemoList(bdContactUsMemo);
	}

	/**
	 *
	 * @Desc : 입점문의 관리자 메모 삭제
	 * @Method Name : updateVendorAdminMemo
	 * @Date : 2019. 2. 20.
	 * @Author : ansuk
	 * @param create
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateVendorInquiryMemoToDelete(BdContactUsMemo bdContactUsMemo) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		int resultCnt = vdVendorDao.updateVendorInquiryMemoToDelete(bdContactUsMemo);
		if (resultCnt > 0) {
			resultMap.put("result", Const.BOOLEAN_TRUE);
		} else {
			resultMap.put("result", Const.BOOLEAN_FALSE);
		}
		return resultMap;
	}

	/**
	 *
	 * @Desc : 입점문의 업데이트
	 * @Method Name : updateVendorInquiry
	 * @Date : 2019. 2. 21.
	 * @Author : ansuk
	 * @param bdContactUs
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateVendorInquiry(BdContactUs bdContactUs) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		int resultCnt = vdVendorDao.updateVendorInquiry(bdContactUs);
		if (resultCnt > 0) {
			resultMap.put("result", Const.BOOLEAN_TRUE);
		} else {
			resultMap.put("result", Const.BOOLEAN_FALSE);
		}
		return resultMap;
	}

	/**
	 *
	 * @Desc : 협력게시판 목록
	 * @Method Name : selectVendorCoworkList
	 * @Date : 2019. 2. 26.
	 * @Author : ansuk
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<BdCorprBoard> selectVendorCoworkList(Pageable<BdCorprBoard, BdCorprBoard> pageable) throws Exception {
		int count = vdVendorDao.selectVendorCoworkListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(vdVendorDao.selectVendorCoworkList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 *
	 * @Desc : 협력게시판 저장 - 질문자용(merge 쿼리 사용)
	 * @Method Name : updateVendorCowork
	 * @Date : 2019. 2. 28.
	 * @Author : ansuk
	 * @param bdCorprBoard
	 * @return
	 * @throws Exception
	 */
	public void updateVendorCowork(BdCorprBoard bdCorprBoard) throws Exception {
		vdVendorDao.updateVendorCowork(bdCorprBoard); // MERGE INTO 쿼리
	}

	/**
	 * @Desc : 협력게시판 답변 폼
	 * @Method Name : selectVendorCoworkDetail
	 * @Date : 2019. 3. 4.
	 * @Author : ansuk
	 * @param parameter
	 * @throws Exception
	 */
	public Map<String, Object> getVendorCoworkDetail(BdCorprBoard params) throws Exception {
		Map<String, Object> result = new HashMap<>();
		result.put("coworkDetail", vdVendorDao.selectVendorCoworkDetail(params));

		// 상담유형코드가 협력게시판인것만 보고 상담유형상세코드는 보지 않음
		// 기획의도상 협력게시판 메뉴에서 상담유형상세코드는 의미 없음
//		CmCounselScript cmCounselScript = new CmCounselScript();
//		cmCounselScript.setCnslTypeCode(CommonCode.CNSL_TYPE_CODE_VENDOR_COWORK);
//		cmCounselScript.setUseYn("Y");
//		result.put("counselScriptList", conselScriptService.selectCouselScriptListForUse(cmCounselScript));

		return result;
	}

	/**
	 * @Desc : 협력게시판 관리자 메모 목록
	 * @Method Name : selectVendorCoworkMemoList
	 * @Date : 2019. 3. 5.
	 * @Author : ansuk
	 * @param bdCorprBoardMemo
	 * @return
	 * @throws Exception
	 */
	public List<BdCorprBoardMemo> selectVendorCoworkMemoList(BdCorprBoardMemo bdCorprBoardMemo) throws Exception {
		return vdVendorDao.selectVendorCoworkMemoList(bdCorprBoardMemo);
	}

	/**
	 *
	 * @Desc : 협력게시판 메모 등록
	 * @Method Name : setVendorCoworkAdminMemo
	 * @Date : 2019. 3. 6.
	 * @Author : ansuk
	 * @param bdCorprBoardMemo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setVendorCoworkAdminMemo(BdCorprBoardMemo bdCorprBoardMemo) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		UserDetails user = LoginManager.getUserDetails();
		bdCorprBoardMemo.setRgsterNo(user.getAdminNo());
		int resultCnt = vdVendorDao.insertVendorCoworkAdminMemo(bdCorprBoardMemo);

		if (resultCnt > 0) {
			resultMap.put("result", Const.BOOLEAN_TRUE);
		} else {
			resultMap.put("result", Const.BOOLEAN_FALSE);
		}
		return resultMap;
	}

	/**
	 *
	 * @Desc : 협력게시판 메모 삭제
	 * @Method Name : updateVendorCoworkMemoToDelete
	 * @Date : 2019. 3. 6.
	 * @Author : ansuk
	 * @param bdCorprBoardMemo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateVendorCoworkMemoToDelete(BdCorprBoardMemo bdCorprBoardMemo) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		int resultCnt = vdVendorDao.updateVendorCoworkMemoToDelete(bdCorprBoardMemo);
		if (resultCnt > 0) {
			resultMap.put("result", Const.BOOLEAN_TRUE);
		} else {
			resultMap.put("result", Const.BOOLEAN_FALSE);
		}
		return resultMap;
	}

	/**
	 *
	 * @Desc : 협력게시판 답변 등록/수정
	 * @Method Name : updateVendorCoworkReply
	 * @Date : 2019. 3. 6.
	 * @Author : ansuk
	 * @param bdCorprBoard
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateVendorCoworkReply(BdCorprBoard bdCorprBoard) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		int resultCnt = vdVendorDao.updateVendorCoworkReply(bdCorprBoard);
		if (resultCnt > 0) {
			resultMap.put("result", Const.BOOLEAN_TRUE);
		} else {
			resultMap.put("result", Const.BOOLEAN_FALSE);
		}
		return resultMap;
	}

	/**
	 *
	 * @Desc : 입점문의 삭제
	 * @Method Name : updateVendorInquiryForDelete
	 * @Date : 2019. 3. 13.
	 * @Author : ansuk
	 * @param bdContactUs
	 * @throws Exception
	 */
	public void updateVendorInquiryForDelete(BdContactUs bdContactUs) throws Exception {
		vdVendorDao.updateVendorInquiryForDelete(bdContactUs);
	}

	/**
	 * @Desc : 업체기본정보 리스트
	 * @Method Name : getVendorBaseInfoList
	 * @Date : 2019. 3. 22.
	 * @Author : 유성민
	 * @return
	 */
	public List<VdVendor> getVendorBaseInfoList(VdVendor vdVendor) throws Exception {

		return vdVendorDao.getVendorBaseInfoList(vdVendor);
	}

	/**
	 * @Desc : 협력게시판 삭제
	 * @Method Name : deleteVendorCowork
	 * @Date : 2019. 4. 17.
	 * @Author : 유성민
	 * @param bdCorprBoard
	 */
	public void deleteVendorCowork(BdCorprBoard bdCorprBoard) throws Exception {

		for (String corprBoardSeqString : bdCorprBoard.getCorprBoardSeqArr()) {
			int corprBoardSeq = Integer.parseInt(corprBoardSeqString);

			// 협력게시판 메모 삭제
			bdCorprBoardMemoDao.deleteCorprBoardMemo(corprBoardSeq);

			// 협력게시판 삭제
			BdCorprBoard param = new BdCorprBoard();
			param.setCorprBoardSeq(corprBoardSeq);
			bdCorprBoardDao.delete(param);
		}

	}

	/**
	 * @Desc : 입점관리 1:1 문의 관리
	 * @Method Name : getIndividualInquiryMain
	 * @Date : 2019. 4. 12.
	 * @Author : 고웅환
	 * @param pageable
	 * @return
	 */
	public Map<String, Object> getIndividualInquiryMain(Parameter<?> parameter) throws Exception {
		Map<String, Object> rtnVal = new HashMap<>();

		String[] codeFields = { CommonCode.ASWR_STAT_CODE, CommonCode.CNSL_TYPE_CODE };
		Map<String, List<SyCodeDetail>> codeList = commonCodeService.getCodeListByGroup(codeFields);

		codeList.get(CommonCode.CNSL_TYPE_CODE).removeIf(
				cnslTypeCode -> !UtilsText.equals(CommonCode.CNSL_GBN_CODE_INQUIRY, cnslTypeCode.getAddInfo1())
						|| UtilsText.equals(Const.BOOLEAN_FALSE, cnslTypeCode.getAddInfo2()));

		// 답변여부, 상담유형 코드, 상담구분 코드
		rtnVal.put("codeList", codeList);

		return rtnVal;
	}

	/**
	 * @Desc : 입점관리 1:1문의 상세정보 조회
	 * @Method Name : getIndividualInquiryDetail
	 * @Date : 2019. 4. 17.
	 * @Author : 고웅환
	 * @param bdMemberCounsel
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getIndividualInquiryDetail(BdMemberCounsel bdMemberCounsel) throws Exception {
		Map<String, Object> rtnVal = new HashMap<>();

		// 문의 정보 조회
		bdMemberCounsel = this.selectIndividualInquiryDetail(bdMemberCounsel);

		String replaceCont = bdMemberCounsel.getInqryContText().replace("</br>", "\r\n");
		bdMemberCounsel.setInqryContText(replaceCont);
		if (UtilsText.isNotBlank(bdMemberCounsel.getAswrContText())) {
			replaceCont = bdMemberCounsel.getAswrContText().replace("</br>", "\r\n");
			bdMemberCounsel.setAswrContText(replaceCont);
		}
		rtnVal.put("bdMemberCounsel", bdMemberCounsel);

		// 회원 정보 조회
		rtnVal.put("memberInfo", memberService.getMember(bdMemberCounsel.getMemberNo()));
		// 문의/답변 정보 조회
		BdMemberCounselAttachFile bdMemberCounselAttachFile = new BdMemberCounselAttachFile();
		bdMemberCounselAttachFile.setMemberCnslSeq(bdMemberCounsel.getMemberCnslSeq());

		// 첨부파일 조회
		List<BdMemberCounselAttachFile> counselAttachFiles = this.selectInquryAttachFiles(bdMemberCounselAttachFile);
		List<BdMemberCounselAttachFile> inqryCounselAttachFiles = new ArrayList<BdMemberCounselAttachFile>(); // 문의 첨부파일
		List<BdMemberCounselAttachFile> aswrCounselAttachFiles = new ArrayList<BdMemberCounselAttachFile>(); // 답변 첨부 파일

		for (BdMemberCounselAttachFile counselAttachFile : counselAttachFiles) {
			if (Const.COUNSEL_INQRY_FILE_GBN_TYPE.equals(counselAttachFile.getAtchFileGbnType())) {
				inqryCounselAttachFiles.add(counselAttachFile);
			} else {
				aswrCounselAttachFiles.add(counselAttachFile);
			}
		}
		rtnVal.put("inqryCounselAttachFiles", inqryCounselAttachFiles);
		rtnVal.put("aswrCounselAttachFiles", aswrCounselAttachFiles);

		// 관리자 메모 정보 조회
		BdMemberCounselMemo bdMemberCounselMemo = new BdMemberCounselMemo();
		bdMemberCounselMemo.setMemberCnslSeq(bdMemberCounsel.getMemberCnslSeq());
		List<BdMemberCounselMemo> memoList = bdInquiryService.selectBdMemberCounselMemoList(bdMemberCounselMemo);
		for (int i = 0; i < memoList.size(); i++) {
			if (UtilsText.equals(Message.getMessage("system.msg.escalation"), memoList.get(i).getMemoText())) {
				memoList.get(i).setEscalationYn(Const.BOOLEAN_TRUE);
			}
		}

		rtnVal.put("bdMemberCounselMemo", memoList);

		return rtnVal;
	}

	/**
	 * @Desc : 입점관리 1:1문의 상세정보 조회
	 * @Method Name : selectIndividualInquiryDetail
	 * @Date : 2019. 4. 17.
	 * @Author : 고웅환
	 * @param bdMemberCounsel
	 * @return
	 * @throws Exception
	 */
	public BdMemberCounsel selectIndividualInquiryDetail(BdMemberCounsel bdMemberCounsel) throws Exception {
		return bdMemberCounselDao.selectIndividualInquiryDetail(bdMemberCounsel);
	}

	/**
	 * @Desc : 상담 첨부 파일 조회
	 * @Method Name : selectInquiryAttachFiles
	 * @Date : 2019. 4. 17.
	 * @Author : 고웅환
	 * @param bdMemberCounselAttachFile
	 * @return
	 * @throws Exception
	 */
	public List<BdMemberCounselAttachFile> selectInquryAttachFiles(BdMemberCounselAttachFile bdMemberCounselAttachFile)
			throws Exception {
		return bdMemberCounselAttachFileDao.selectBdMemberCounselAttachFileList(bdMemberCounselAttachFile);
	}

	/**
	 * @Desc : BO 대시보드 협력게시판 그룹별 건수 조회
	 * @Method Name : getCoworkGroupCount
	 * @Date : 2019. 5. 8.
	 * @Author : 고웅환
	 * @param
	 * @return
	 * @throws Exception
	 */
	public List<BdCorprBoard> getCoworkGroupCount(String aswrStatCode) throws Exception {
		return vdVendorDao.selectCorprGroupCount(aswrStatCode);
	}

	/**
	 * @Desc : PO 대시보드 협력게시판 그룹별 건수 조회
	 * @Method Name : getCoworkGroupCount
	 * @Date : 2019. 5. 8.
	 * @Author : 고웅환
	 * @param
	 * @return
	 * @throws Exception
	 */
	public int getCoworkGroupCountPo(BdCorprBoard bdCorprBoard) throws Exception {
		return vdVendorDao.selectCorprGroupCountPo(bdCorprBoard);
	}

	/**
	 * @Desc : BO 대시보드 입점/제휴 그룹별 건수 조회
	 * @Method Name : getContactGroupCount
	 * @Date : 2019. 5. 8.
	 * @Author : 고웅환
	 * @param
	 * @return
	 * @throws Exception
	 */
	public List<BdContactUs> getContactGroupCount() throws Exception {
		return vdVendorDao.selectContactGroupCount();
	}

	/**
	 * @Desc : 거래중인 입점사수 조회
	 * @Method Name : selectDealVendorCount
	 * @Date : 2019. 4. 17.
	 * @Author : 이재렬
	 * @throws Exception
	 */
	public int selectDealVendorCount() throws Exception {

		return vdVendorDao.selectDealVendorCount();
	}

	/**
	 * @Desc : 업체 모든 담당자 sms or email 발송을위한 정보 리스트
	 * @Method Name : getAllVendorSendInfoList
	 * @Date : 2019. 5. 13.
	 * @Author : 이재렬
	 * @throws Exception
	 */
	public List<SyAdmin> getAllVendorSendInfoList(String[] vndrNo) throws Exception {

		return vdVendorDao.getAllVendorSendInfoList(vndrNo);
	}

	/**
	 * @Desc : 업체 대표 담당자 sms or email 발송을위한 정보 리스트
	 * @Method Name : getRepVendorSendInfoList
	 * @Date : 2019. 5. 13.
	 * @Author : 이재렬
	 * @throws Exception
	 */
	public List<SyAdmin> getRepVendorSendInfoList(String[] vndrNo) throws Exception {

		return vdVendorDao.getRepVendorSendInfoList(vndrNo);
	}

	/**
	 * @Desc : sms발송 서비스.
	 * @Method Name : sendVendorSmsService
	 * @Date : 2019. 5. 21.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	public HashMap<String, Object> sendVendorSmsService(Parameter<?> parameter) throws Exception {

		MessageVO messageVo = new MessageVO();

		String sendVendorType = parameter.getString("radioAnswerModule");
		String tmsGubun = parameter.getString("tmsGubun");
		String[] vndrNo = parameter.getStringArray("vndrNo");

		String sndrName = LoginManager.getUserDetails().getUsername();
		String sendTelNoText = parameter.getString("inputRprsntTelNoText");
//		String mesgContextTitle = parameter.getString("mesgContextTitle");
		String mesgContText = parameter.getString("mesgContText");
		String sendTypeCode = parameter.getString("sendTypeCode");

		List<SyAdmin> recvTelNoText = new ArrayList<>();
		HashMap<String, Object> resultMap = new HashMap<>();

		int recvTelNoTextCount = Integer.parseInt(parameter.getString("recvTelNoTextCount"));

		try {
			if (!UtilsText.equals(tmsGubun, "ALL")) {

				// 선택 sms발송
				if (sendVendorType.equals("allVendor")) {
					recvTelNoText.addAll(getAllVendorSendInfoList(vndrNo));

				} else if (sendVendorType.equals("repVendor")) {
					recvTelNoText.addAll(getRepVendorSendInfoList(vndrNo));

				} else if (sendVendorType.equals("toMe")) {
					SyAdmin toMe = new SyAdmin();
					toMe.setAdminName(LoginManager.getUserDetails().getUsername());
					toMe.setHdphnNoText(LoginManager.getUserDetails().getHdphnNoText());
					recvTelNoText.add(toMe);

				}

			} else {

				// 전체 sms발송
				if (sendVendorType.equals("allVendor")) {
					recvTelNoText.addAll(getAllVendorSendInfoList(null));

				} else if (sendVendorType.equals("repVendor")) {
					recvTelNoText.addAll(getRepVendorSendInfoList(null));

				} else if (sendVendorType.equals("toMe")) {
					SyAdmin toMe = new SyAdmin();
					toMe.setAdminName(LoginManager.getUserDetails().getUsername());
					toMe.setHdphnNoText(LoginManager.getUserDetails().getHdphnNoText());
					recvTelNoText.add(toMe);
				}
			}

			messageVo.setSndrName(sndrName); // 발신자 이름
			messageVo.setSendTelNoText(sendTelNoText); // 발신자 번호

			messageVo.setSiteNo(Const.SITE_NO_ART); // 사이트번호
			messageVo.setSendTypeCode(sendTypeCode); // 송신유형 코드 sms or lms

			messageVo.setMesgContText(mesgContText); // 메세지 내용

			for (int i = recvTelNoTextCount; i < recvTelNoText.size(); i++) {

				messageVo.setRcvrName(recvTelNoText.get(i).getAdminName().trim()); // 수신자 이름
				messageVo.setRecvTelNoText(recvTelNoText.get(i).getHdphnNoText().trim()); // 수신 전화번호

				recvTelNoTextCount = i; // 재송신 시작점을 위한 전화번호 index

				// 메시지보내기 필수항목 validation 체크.
				// 필수항목 : 발신자 이름, 발신자 전화번호, 수신자 이름, 수신자 전화번호, 메세지 내용.
				messageService.isMessageValidate(messageVo);

				// 즉시발송 여부, true면 즉시발송, default값은 false.
				messageVo.setReal(true);
				messageService.setSendMessageProcess(messageVo);

			}

			resultMap.put("code", Const.BOOLEAN_TRUE);

		} catch (Exception e) {
			e.printStackTrace();

			resultMap.put("code", Const.BOOLEAN_FALSE);
			resultMap.put("recvTelNoTextCount", recvTelNoTextCount);
			resultMap.put("message", e.getMessage());

		}

		return resultMap;
	}

	/**
	 * @Desc : email 발송 서비스.
	 * @Method Name : sendVendorEmailService.
	 * @Date : 2019. 5. 21.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	public HashMap<String, Object> sendVendorEmailService(Parameter<?> parameter) throws Exception {

		String sendVendorType = parameter.getString("radioAnswerModule");
		String tmsGubun = parameter.getString("tmsGubun");
		String[] vndrNo = parameter.getStringArray("vndrNo");

		String title = UtilsText.unescapeXss(parameter.getString("emailTitleText")); // 메일제목
		String content = UtilsText.unescapeXss(parameter.getString("emailContText")); // 메일내용

		List<SyAdmin> recvInfoList = new ArrayList<>();
		HashMap<String, Object> resultMap = new HashMap<>();

		CmEmailSendHistory cmEmailSendHistory = new CmEmailSendHistory();
		int recvEmailAddrTextCount = Integer.parseInt(parameter.getString("recvEmailAddrTextCount"));

		try {

			if (!UtilsText.equals(tmsGubun, "ALL")) {

				// 선택 email발송
				if (sendVendorType.equals("allVendor")) {
					recvInfoList = getAllVendorSendInfoList(vndrNo);

				} else if (sendVendorType.equals("repVendor")) {
					recvInfoList = getRepVendorSendInfoList(vndrNo);

				}

			} else {

				// 전체 email발송
				if (sendVendorType.equals("allVendor")) {
					recvInfoList = getAllVendorSendInfoList(null);

				} else if (sendVendorType.equals("repVendor")) {
					recvInfoList = getRepVendorSendInfoList(null);

				}
			}

			cmEmailSendHistory.setSiteNo(Const.SITE_NO_ART); // 사이트번호
			cmEmailSendHistory.setEmailTitleText(title); // 이메일 제목
			cmEmailSendHistory.setEmailContText(content); // 이메일 내용

			for (int i = recvEmailAddrTextCount; i < recvInfoList.size(); i++) {
				cmEmailSendHistory.setRcvrName(recvInfoList.get(i).getAdminName()); // 수신자 이름
				cmEmailSendHistory.setRcvrEmailAddrText(recvInfoList.get(i).getEmailAddrText()); // 수신자 이메일
				recvEmailAddrTextCount = i; // 재송신 시작점을 위한 이메일 index

				mailService.sendMail(cmEmailSendHistory);
			}

			resultMap.put("code", Const.BOOLEAN_TRUE);

		} catch (Exception e) {
			e.printStackTrace();

			resultMap.put("code", Const.BOOLEAN_FALSE);
			resultMap.put("recvEmailAddrTextCount", recvEmailAddrTextCount);
			resultMap.put("message", e.getMessage());

		}

		return resultMap;
	}

	/////////////////////////// 이재렬 끝

	/**
	 * @Desc :입점사별 판매수수료율 && 임직원 할인율 정보
	 * @Method Name : getVendorCmsnRateAndEmpDscntRate
	 * @Date : 2019. 6. 21.
	 * @Author : 유성민
	 * @param vndrNo
	 * @param stdCtgrNo
	 * @param brandNo
	 * @return
	 * @throws Exception
	 */
	public VendorOtherPartVo getVendorCmsnRateAndEmpDscntRate(String vndrNo, String stdCtgrNo, String brandNo)
			throws Exception {
		return vdVendorDao.getVendorCmsnRateAndEmpDscntRate(vndrNo, stdCtgrNo, brandNo);
	}

	public VdVendor getVendorInfo(VdVendor param) throws Exception {
		return vdVendorDao.selectByPrimaryKey(param);
	}

	/**
	 * @Desc : A/S 책임자와 전화번호 항목 수정
	 * @Method Name : updateAsMngrText
	 * @Date : 2019. 10. 22.
	 * @Author : 신인철
	 * @param asMngrText
	 * @throws Exception
	 */
	public void updateAsMngrText(VdVendor vdVendor) throws Exception {
		vdVendor.setAsMngrText(UtilsText.isBlank(vdVendor.getAsMngrText()) ? "" : vdVendor.getAsMngrText());
		vdVendorDao.updateAsMngrText(vdVendor);

		productInsideAddInfoService.setVendorAsMngrText(vdVendor); // 입점사 AS담당자 연락처 일괄변경
	}

	/**
	 * @Desc : 업체 배송안내 수정
	 * @Method Name : updateVendorDlvyGuide
	 * @Date : 2019. 10. 22.
	 * @Author : 신인철
	 * @param vdVendor
	 * @param user
	 * @throws Exception
	 */
	public void updateVendorDlvyGuide(VdVendor vdVendor, UserDetails user) throws Exception {
		VdVendorDeliveryGuide[] deliveryGuideParams = vdVendor.getDeliveryGuideList();
		for (VdVendorDeliveryGuide deliveryGuideParam : deliveryGuideParams) {
			deliveryGuideParam.setVndrNo(vdVendor.getVndrNo());
			deliveryGuideParam.setModerNo(user.getAdminNo());
			String deliveryGuide = UtilsText.unescapeXss(deliveryGuideParam.getDlvyGuideInfo());
			deliveryGuideParam.setDlvyGuideInfo(deliveryGuide);
			if (vdVendorDeliveryGuideDao.selectByPrimaryKey(deliveryGuideParam) != null) {
				if (UtilsText.isBlank(deliveryGuideParam.getDlvyGuideInfo())) {
					deliveryGuideParam.setDlvyGuideInfo("");
				}
				vdVendorDeliveryGuideDao.update(deliveryGuideParam);
			} else {
				if (UtilsText.isBlank(deliveryGuideParam.getDlvyGuideInfo())) {
					deliveryGuideParam.setDlvyGuideInfo("");
				}
				deliveryGuideParam.setRgsterNo(user.getAdminNo());
				vdVendorDeliveryGuideDao.insert(deliveryGuideParam);
			}
		}
	}

	/**
	 * @Desc : 이름, 업체명, 업체 구분 , as담당자 정보 조회
	 * @Method Name : getVendorAsMngrInfo
	 * @Date : 2019. 10. 23.
	 * @Author : sic
	 * @param vndrNo
	 * @return
	 * @throws Exception
	 */
	public VdVendor getVendorAsMngrInfo(String vndrNo) throws Exception {
		VdVendor vdVendor = new VdVendor();
		vdVendor.setVndrNo(vndrNo);
		return vendorService.getVendorInfo(vdVendor);
	}

	/**
	 * @Desc : 사이트 정책 변경으로 인한 업체정보 업데이트
	 * @Method Name : setVendorBySySite
	 * @Date : 2020. 1. 21.
	 * @Author : 이강수
	 * @param vndrNo
	 * @return
	 * @throws Exception
	 */
	public void setVendorBySySite(SySite sySite) throws Exception {

		String siteNo = sySite.getSiteNo();
		int freeDlvyStdrAmt = sySite.getFreeDlvyStdrAmt();
		int dlvyAmt = sySite.getDlvyAmt();

		// 사용중 여부를 따지지 않고 전체 채널 가지고
		List<SySiteChnnl> chnnlList = siteService.getChannelListBySiteNo(siteNo, false);
		VdVendor vdVendor = new VdVendor();

		for (SySiteChnnl chnnl : chnnlList) {
			String vndrNo = chnnl.getVndrNo();
			if (!UtilsText.isBlank(vndrNo)) {
				vdVendor.setVndrNo(vndrNo);

				// 사이트 '무료배송기준금액'이나 사이트 '배송비'가 변경되었다면
				// 변경된 사이트번호를 물고있는 자사업체 정보를 수정한다.
				vdVendor.setFreeDlvyStdrAmt(freeDlvyStdrAmt);
				vdVendor.setDlvyAmt(dlvyAmt);

				vdVendorDao.updateVendorDlvyAmtByPolicy(vdVendor);
			}
		}
	}

}
