package kr.co.abcmart.bo.vendor.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.board.model.master.BdContactUs;
import kr.co.abcmart.bo.board.model.master.BdContactUsMemo;
import kr.co.abcmart.bo.board.model.master.BdCorprBoard;
import kr.co.abcmart.bo.board.model.master.BdCorprBoardMemo;
import kr.co.abcmart.bo.board.model.master.BdMemberCounsel;
import kr.co.abcmart.bo.board.model.master.BdMemberCounselAttachFile;
import kr.co.abcmart.bo.board.model.master.BdMemberCounselMemo;
import kr.co.abcmart.bo.board.service.BdInquiryService;
import kr.co.abcmart.bo.board.vo.BdInquirySearchVO;
import kr.co.abcmart.bo.product.model.master.SyStandardCategory;
import kr.co.abcmart.bo.product.service.StandardCategoryService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.model.master.VdVendorExceptionCommission;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("vendor")
public class VendorController extends BaseController {

	@Autowired
	private VendorService vendorService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private StandardCategoryService standardCategoryService;

	@Autowired
	private BdInquiryService bdInquiryService;

	/**
	 * @Desc : 입점사 정보관리 메인화면
	 * @Method Name : vendorInfo
	 * @Date : 2019. 2. 1.
	 * @Author : 유성민
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/info")
	public ModelAndView vendorInfo(Parameter<VdVendor> parameter) throws Exception {
		if (UtilsText.equals(LoginManager.getUserDetails().getUpAuthNo(), Const.ROLE_VENDER_GROUP)) {
			return redirect("vendor/info/read-detail");
		}
		String[] codeFields = { CommonCode.VNDR_STAT_CODE, CommonCode.CPN_TYPE_CODE }; // 거래상태, 쿠폰타입

		// 공통코드 조회
		Map<String, List<SyCodeDetail>> codeList = commonCodeService.getCodeListByGroup(codeFields);
		parameter.addAttribute("codeList", codeList);

		// 전시채널 조회
		parameter.addAttribute("channelList", siteService.getVendorUseChannelList());

		// 표준카테고리 조회
		SyStandardCategory params = new SyStandardCategory();
		params.setLevel("1");
		List<SyStandardCategory> standardCategoryList = standardCategoryService.getStandardCategoryList(params);
		parameter.addAttribute("standardCategoryList", standardCategoryList);

		return forward("/vendor/info/main");
	}

	/**
	 * @Desc : 입점사목록검색
	 * @Method Name : readList
	 * @Date : 2019. 2. 7.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/info/read-list")
	public void readList(Parameter<VdVendor> parameter) throws Exception {
		Pageable<VdVendor, VdVendor> pageable = new Pageable<>(parameter);
		Page<VdVendor> page = vendorService.selectVendorInfoList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc :업체정보엑셀다운로드
	 * @Method Name : vendorInfoExcelDown
	 * @Date : 2019. 3. 6.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/info/excel-down")
	public void vendorInfoExcelDown(Parameter<VdVendor> parameter) throws Exception {
		Pageable<VdVendor, VdVendor> pageable = new Pageable<>(parameter);
		pageable.setUsePage(false);
		Page<VdVendor> page = vendorService.selectVendorInfoList(pageable);

		ExcelValue excelValue = ExcelValue.builder(1, 0)
				.columnNames(Arrays.asList("vndrNo", "vndrName", "bossName", "bizNoText", "rprsntTelNoText",
						"faxNoText", "chnnlNames", "vndrMngrName", "vndrStatCodeName", "dfltCmsnRateText",
						"exceptionCommissionApplyYn", "employeeDiscountApplyYn", "rgstDtm", "modDtm"))
				.data(page.getContent()).build();

		parameter.downloadExcelTemplate("vendor/info/excel/vendorList", excelValue);

	}

	/**
	 * @Desc :입점사 정보등록 화면
	 * @Method Name : vendorInfoCreate
	 * @Date : 2019. 2. 8.
	 * @Author : 유성민
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/info/create-form")
	public ModelAndView vendorInfoCreateForm(Parameter<?> parameter) throws Exception {
		String[] codeFields = { CommonCode.VNDR_STAT_CODE, CommonCode.CPN_TYPE_CODE, CommonCode.LOGIS_VNDR_CODE,
				CommonCode.BANK_CODE, CommonCode.DLVY_GUIDE_BGN_CODE };

		// 공통코드 조회
		Map<String, List<SyCodeDetail>> codeList = commonCodeService.getCodeListByGroup(codeFields);
		parameter.addAttribute("codeList", codeList);

		// 전시채널 조회
		parameter.addAttribute("channelList", siteService.getVendorUseChannelList());

		// 표준카테고리 조회
		SyStandardCategory params = new SyStandardCategory();
		params.setLevel("1");
		List<SyStandardCategory> standardCategoryList = standardCategoryService.getStandardCategoryList(params);
		parameter.addAttribute("standardCategoryList", standardCategoryList);

		return forward("/vendor/info/create-form-vendor");
	}

	/**
	 * @Desc : 입점사 정보상세 화면
	 * @Method Name : vendorInfoDetail
	 * @Date : 2019. 2. 13.
	 * @Author : 유성민
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/info/read-detail")
	public ModelAndView vendorInfoDetail(Parameter<VdVendor> parameter) throws Exception {
		UserDetails userDetails = LoginManager.getUserDetails();
		VdVendor vdVendor = new VdVendor();

		if (UtilsText.equals(userDetails.getUpAuthNo(), Const.ROLE_VENDER_GROUP)) {
			vdVendor.setVndrNo(userDetails.getVndrNo());
		} else {
			vdVendor = parameter.create(VdVendor.class);
		}

		String[] codeFields = { CommonCode.VNDR_STAT_CODE, CommonCode.CPN_TYPE_CODE, CommonCode.LOGIS_VNDR_CODE,
				CommonCode.BANK_CODE, CommonCode.DLVY_GUIDE_BGN_CODE };
		// 공통코드 조회
		Map<String, List<SyCodeDetail>> codeList = commonCodeService.getCodeListByGroup(codeFields);
		parameter.addAttribute("codeList", codeList);

		// 전시채널 조회
		parameter.addAttribute("channelList", siteService.getVendorUseChannelList());

		// 입점사기본정보
		Map<String, Object> resultMap = vendorService.getVendorBaseTabInfo(vdVendor);

		return forward("/vendor/info/create-form-vendor", resultMap);
	}

	/**
	 * @Desc : 입점사코드 중복체크
	 * @Method Name : checkInsdMgmtInfo
	 * @Date : 2019. 2. 8.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/info/check-insdmgmtinfo")
	public void checkInsdMgmtInfo(Parameter<VdVendor> parameter) throws Exception {
		VdVendor params = parameter.get();

		writeJson(parameter, vendorService.getVedorInfoDup(params));
	}

	/**
	 * @Desc : 사업자번호 중복체크
	 * @Method Name : checkBizNoText
	 * @Date : 2019. 2. 8.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/info/check-bizNoText")
	public void checkBizNoText(Parameter<VdVendor> parameter) throws Exception {
		VdVendor params = parameter.get();

		writeJson(parameter, vendorService.getVedorInfoDup(params));
	}

	/**
	 * @Desc : 입점사 담당자 정보
	 * @Method Name : vendorAddInfo
	 * @Date : 2019. 2. 13.
	 * @Author : 유성민
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/info/vendor-manager-add")
	public ModelAndView vendorManager(Parameter<?> parameter) throws Exception {

		parameter.addAttribute("managerIndex", parameter.getInt("managerIndex") + 1);
		return forward("/vendor/info/basetab/vendor-manager-create");
	}

	/**
	 * @Desc : 입점사 기본정보 신규저장
	 * @Method Name : vendorInfoCreateBaseTab
	 * @Date : 2019. 2. 15.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/info/create-basetab")
	public void vendorInfoCreateBaseTab(Parameter<VdVendor> parameter) throws Exception {
		VdVendor params = parameter.get();
		log.debug("params : {}", params);

		writeJson(parameter, vendorService.setVendorBaseInfo(params));
	}

	/**
	 * @Desc : 입점사 기본정보 수정
	 * @Method Name : vendorInfoUpdateBaseTab
	 * @Date : 2019. 2. 22.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/info/update-basetab")
	public void vendorInfoUpdateBaseTab(Parameter<VdVendor> parameter) throws Exception {
		VdVendor params = parameter.get();
		log.debug("params : {}", params);

		writeJson(parameter, vendorService.updateVendorBaseInfo(params));
	}

	/**
	 * @Desc :입점사 기본정보 수정(PO전용)
	 * @Method Name : vendorInfoUpdateBaseTabPo
	 * @Date : 2019. 3. 11.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/info/update-basetab-po")
	public void vendorInfoUpdateBaseTabPo(Parameter<VdVendor> parameter) throws Exception {
		VdVendor params = parameter.get();
		log.debug("params : {}", params);

		writeJson(parameter, vendorService.updateVendorBaseInfoPo(params));
	}

	/**
	 * @Desc : 입점사 부가정보 저장
	 * @Method Name : vendorInfoUpdateAddTab
	 * @Date : 2019. 2. 27.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/info/update-addtab")
	public void vendorInfoUpdateAddTab(Parameter<VdVendor> parameter) throws Exception {
		VdVendor params = parameter.get();
		log.debug("params : {}", params);

		writeJson(parameter, vendorService.setVendorAddInfo(params));
	}

	/**
	 * @Desc :입점사 부가정보 저장(PO전용)
	 * @Method Name : vendorInfoUpdateAddTabPo
	 * @Date : 2019. 3. 11.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/info/update-addtab-po")
	public void vendorInfoUpdateAddTabPo(Parameter<VdVendor> parameter) throws Exception {
		VdVendor params = parameter.get();
		log.debug("params : {}", params);

		writeJson(parameter, vendorService.setVendorAddInfoPo(params));
	}

	/**
	 * @Desc : 예외수수료 적용이력팝업
	 * @Method Name : vendorInfoCommissionHistoryPop
	 * @Date : 2019. 3. 6.
	 * @Author : 유성민
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/info/commission-history-pop")
	public ModelAndView vendorInfoCommissionHistoryPop(Parameter<?> parameter) throws Exception {
		parameter.addAttribute("vndrNo", parameter.getString("vndrNo"));
		return forward("/vendor/info/addtab/vendor-exception-commission-history-pop");
	}

	/**
	 * @Desc : 예외수수료 적용이력조회
	 * @Method Name : vendorInfoCommissionHistoryList
	 * @Date : 2019. 3. 6.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/info/read-commission-history")
	public void vendorInfoCommissionHistoryList(Parameter<VdVendorExceptionCommission> parameter) throws Exception {
		Pageable<VdVendorExceptionCommission, VdVendorExceptionCommission> pageable = new Pageable<>(parameter);
		Page<VdVendorExceptionCommission> page = vendorService.getVendorExceptionCommissionHistory(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 입점업체 sms발송 폼
	 * @Method Name : vendorInfoSmsSendPop
	 * @Date : 2019. 3. 21.
	 * @Author : 유성민
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/info/sms-send-pop")
	public ModelAndView vendorInfoSmsSendPop(Parameter<?> parameter) throws Exception {

		String tmsGubun = parameter.getString("tmsGubun");
		VdVendor vdVendor = new VdVendor();
		if (!UtilsText.equals(tmsGubun, "ALL")) {
			vdVendor.setVndrNoList(Arrays.asList(parameter.getStringArray("vndrNo")));

			List<VdVendor> vendorInfoList = vendorService.getVendorBaseInfoList(vdVendor);
			parameter.addAttribute("vendorInfoList", vendorInfoList);
		} else {

			int vendorCount = vendorService.selectDealVendorCount();
			parameter.addAttribute("vendorCount", vendorCount);
		}

		return forward("/vendor/popup/vendor-sms-pop");
	}

	/////////////////////////// 이재렬 시작

	/**
	 * @Desc : 입점업체 sms발송.
	 * @Method Name : vendorSmsSend
	 * @Date : 2019. 04. 15.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/info/sms-send")
	public void vendorSmsSend(Parameter<?> parameter) throws Exception {

		writeJson(parameter, vendorService.sendVendorSmsService(parameter));

	}

	/**
	 * @Desc : 입점업체 email발송 폼
	 * @Method Name : vendorInfoEmailSendPop
	 * @Date : 2019. 4. 17.
	 * @Author : 이재렬
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/info/email-send-pop")
	public ModelAndView vendorInfoEmailSendPop(Parameter<?> parameter) throws Exception {

		String tmsGubun = parameter.getString("tmsGubun");
		VdVendor vdVendor = new VdVendor();

		if (!UtilsText.equals(tmsGubun, "ALL")) {
			vdVendor.setVndrNoList(Arrays.asList(parameter.getStringArray("vndrNo")));

			List<VdVendor> vendorInfoList = vendorService.getVendorBaseInfoList(vdVendor);
			parameter.addAttribute("vendorInfoList", vendorInfoList);
		} else {
			int vendorCount = vendorService.selectDealVendorCount();
			parameter.addAttribute("vendorCount", vendorCount);

		}

		return forward("/vendor/popup/vendor-email-pop");
	}

	/**
	 * @Desc : 입점업체 email발송.
	 * @Method Name : vendorEmailSend
	 * @Date : 2019. 04. 23.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/info/email-send")
	public void vendorEmailSend(Parameter<?> parameter) throws Exception {
		Map<String, Object> result = vendorService.sendVendorEmailService(parameter);

		writeJson(parameter, result);

	}

	/////////////////////////// 이재렬 끝

	// @formatter:off

	// @formatter:on
	/////////////////////////// 안석진 시작

	/**
	 * @Desc : 입점 문의관리 메인화면
	 * @Method Name : vendorInquiry
	 * @Date : 2019. 2. 15.
	 * @Author : 안석진
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/inquiry")
	public ModelAndView vendorInquiry(Parameter<?> parameter) throws Exception {
		if (!UtilsText.isBlank(parameter.getString("fromDash"))
				&& UtilsText.equals(Const.BOOLEAN_TRUE, parameter.getString("fromDash"))) {
			parameter.addAttribute("fromDash", parameter.getString("fromDash"));
			parameter.addAttribute("tabIdx", parameter.getString("tabIdx"));
		}

		Map<String, Object> result = vendorService.getVendorInquiryMain(parameter);

		parameter.addAttribute("codeList", result.get("codeList"));
		parameter.addAttribute("inquiryDtlCodeList", result.get("inquiryDtlCodeList"));
		parameter.addAttribute("siteNoList", result.get("siteNoList"));

		return forward("/vendor/inquiry/main");
	}

	/**
	 * @Desc : 입점문의목록검색
	 * @Method Name : inquiryReadList
	 * @Date : 2019. 2. 18.
	 * @Author : 안석진
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/inquiry/read-list")
	public void inquiryReadList(Parameter<BdContactUs> parameter) throws Exception {
		Pageable<BdContactUs, BdContactUs> pageable = new Pageable<>(parameter);
		pageable.setUsePage(true);
		Page<BdContactUs> page = vendorService.selectVendorInquiryList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 입점문의상세
	 * @Method Name : inquiryReadDetailPop
	 * @Date : 2019. 2. 19.
	 * @Author : 안석진
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/inquiry/read-detail-pop")
	public ModelAndView inquiryReadDetailPop(Parameter<?> parameter) throws Exception {
		Map<String, Object> result = vendorService.getVendorInquiryDetail(parameter);

		parameter.addAttribute("inquiryDetail", result.get("inquiryDetail"));
		parameter.addAttribute("counselScriptList", result.get("counselScriptList"));

		return forward("/vendor/inquiry/inquiry-detail-pop");
	}

	/**
	 * @Desc : 입점문의 메모 조회
	 * @Method Name : inquiryReadMemo
	 * @Date : 2019. 2. 26.
	 * @Author : ansuk
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/inquiry/read-memo")
	public ModelAndView inquiryReadMemo(Parameter<?> parameter) throws Exception {
		parameter.addAttribute("memoList",
				vendorService.selectVendorInquiryMemoList(parameter.create(BdContactUsMemo.class)));
		return forward("/vendor/inquiry/read-memo");
	}

	/**
	 * @Desc : 입점문의 메모 등록
	 * @Method Name : createInquiryMemo
	 * @Date : 2019. 2. 26.
	 * @Author : ansuk
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/inquiry/create-memo")
	public void createInquiryMemo(Parameter<BdContactUsMemo> parameter) throws Exception {
		writeJson(parameter, vendorService.setVendorAdminMemo(parameter.create(BdContactUsMemo.class)));
	}

	/**
	 * @Desc : 입점문의 메모 삭제
	 * @Method Name : deleteInquiryMemo
	 * @Date : 2019. 2. 26.
	 * @Author : ansuk
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/inquiry/delete-memo")
	public void deleteInquiryMemo(Parameter<BdContactUsMemo> parameter) throws Exception {
		BdContactUsMemo bdContactUsMemo = parameter.get();
		bdContactUsMemo.setRgsterNo(LoginManager.getUserDetails().getAdminNo());

		// 자기글만 삭제시킴
		writeJson(parameter, vendorService.updateVendorInquiryMemoToDelete(bdContactUsMemo));
	}

	/**
	 * @Desc : 입점문의 업데이트(답변 등록)
	 * @Method Name : updateInquiry
	 * @Date : 2019. 2. 26.
	 * @Author : ansuk
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/inquiry/update-inquiry")
	public void updateInquiry(Parameter<BdContactUs> parameter) throws Exception {
		BdContactUs bdContactUs = parameter.get();

		writeJson(parameter, vendorService.updateVendorInquiry(bdContactUs));
	}

	/**
	 * @Desc : 협력게시판 메인
	 * @Method Name : coworkInquiry
	 * @Date : 2019. 2. 26.
	 * @Author : ansuk
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cowork")
	public ModelAndView coworkInquiry(Parameter<?> parameter) throws Exception {
		Map<String, Object> result = vendorService.getVendorInquiryMain(parameter);
		if (!UtilsText.isBlank(parameter.getString("fromDash"))
				&& UtilsText.equals(Const.BOOLEAN_TRUE, parameter.getString("fromDash"))) {
			parameter.addAttribute("fromDash", parameter.getString("fromDash"));
			parameter.addAttribute("tabIdx", parameter.getString("tabIdx"));
		}

		parameter.addAttribute("codeList", result.get("codeList"));

		return forward("/vendor/cowork/main");
	}

	/**
	 * @Desc : 협력게시판 목록
	 * @Method Name : coworkReadList
	 * @Date : 2019. 2. 26.
	 * @Author : ansuk
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/cowork/read-list")
	public void coworkReadList(Parameter<BdCorprBoard> parameter) throws Exception {
		Pageable<BdCorprBoard, BdCorprBoard> pageable = new Pageable<>(parameter);

		UserDetails userDetails = LoginManager.getUserDetails();

		if (UtilsText.equals(userDetails.getUpAuthNo(), Const.ROLE_VENDER_GROUP)) {
			pageable.getBean().setVndrNo(userDetails.getVndrNo());
		}
		pageable.getBean().setAuthApplySystemType(userDetails.getAuthApplySystemType());

		Page<BdCorprBoard> page = vendorService.selectVendorCoworkList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 협력게시판 등록 팝업 - 질문자용
	 * @Method Name : coworkCreateForm
	 * @Date : 2019. 2. 27.
	 * @Author : ansuk
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cowork/create-form")
	public ModelAndView coworkCreateForm(Parameter<BdCorprBoard> parameter) throws Exception {
		parameter.addAttribute("isUpdate", "false");

		return forward("/vendor/cowork/detail-cowork-pop");
	}

	@RequestMapping("/cowork/read-vendor-detail")
	public void readVendorDetail(Parameter<VdVendor> parameter) throws Exception {
		VdVendor vendorParam = vendorService.getVendorBaseInfo(parameter.get());

		writeJson(parameter, vendorParam);
	}

	/**
	 * @Desc : 협력게시판 수정을 위한 작성글 조회 팝업 - 질문자용
	 * @Method Name : coworkUpdateForm
	 * @Date : 2019. 3. 4.
	 * @Author : ansuk
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cowork/update-form")
	public ModelAndView coworkUpdateForm(Parameter<BdCorprBoard> parameter) throws Exception {
		BdCorprBoard params = parameter.create(BdCorprBoard.class);

		parameter.addAttribute("isUpdateMode", "true");

		Map<String, Object> result = vendorService.getVendorCoworkDetail(params);
		parameter.addAttribute("coworkDetail", result.get("coworkDetail"));
		parameter.addAttribute("counselScriptList", result.get("counselScriptList"));

		return forward("/vendor/cowork/detail-cowork-pop");
	}

	/**
	 * @Desc : 협력게시판 저장 - 질문자용(merge)
	 * @Method Name : updateCowork
	 * @Date : 2019. 2. 28.
	 * @Author : ansuk
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/cowork/save")
	public void updateCowork(Parameter<BdCorprBoard> parameter) throws Exception {
		UserDetails userDetails = LoginManager.getUserDetails();
		BdCorprBoard bdCorprBoard = parameter.get();
		bdCorprBoard.setInqrerNo(userDetails.getAdminNo());

		if (UtilsText.equals(userDetails.getAuthApplySystemType(), Const.AUTH_APPLY_SYSTEM_TYPE_PO)) {
			bdCorprBoard.setVndrNo(userDetails.getVndrNo());
			bdCorprBoard.setVndrInqryYn(Const.BOOLEAN_FALSE);
		} else {
			bdCorprBoard.setVndrInqryYn(Const.BOOLEAN_TRUE);
		}

		vendorService.updateVendorCowork(bdCorprBoard);
	}

	/**
	 * @Desc : 협력게시판 답변 폼 팝업
	 * @Method Name : updateCoworkReplyForm
	 * @Date : 2019. 3. 5.
	 * @Author : ansuk
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cowork/reply-form")
	public ModelAndView updateCoworkReplyForm(Parameter<BdCorprBoard> parameter) throws Exception {
		BdCorprBoard params = parameter.create(BdCorprBoard.class);
		Map<String, Object> result = vendorService.getVendorCoworkDetail(params);

		parameter.addAttribute("coworkDetail", result.get("coworkDetail"));
		parameter.addAttribute("counselScriptList", result.get("counselScriptList"));

		return forward("/vendor/cowork/reply-cowork-pop");
	}

	/**
	 * @Desc : 협력게시판 관리자 메모 목록
	 * @Method Name : coworkReadMemo
	 * @Date : 2019. 3. 5.
	 * @Author : ansuk
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cowork/read-memo")
	public ModelAndView coworkReadMemo(Parameter<?> parameter) throws Exception {
		parameter.addAttribute("memoList",
				vendorService.selectVendorCoworkMemoList(parameter.create(BdCorprBoardMemo.class)));
		return forward("/vendor/cowork/read-memo");
	}

	/**
	 * @Desc : 협력게시판 메모 등록
	 * @Method Name : createCoworkMemo
	 * @Date : 2019. 3. 6.
	 * @Author : ansuk
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/cowork/create-memo")
	public void createCoworkMemo(Parameter<?> parameter) throws Exception {
		writeJson(parameter, vendorService.setVendorCoworkAdminMemo(parameter.create(BdCorprBoardMemo.class)));
	}

	/**
	 * @Desc : 협력게시판 메모 삭제
	 * @Method Name : deleteCoworkMemo
	 * @Date : 2019. 3. 6.
	 * @Author : ansuk
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/cowork/delete-memo")
	public void deleteCoworkMemo(Parameter<BdCorprBoardMemo> parameter) throws Exception {
		BdCorprBoardMemo bdCorprBoardMemo = parameter.get();
		bdCorprBoardMemo.setRgsterNo(LoginManager.getUserDetails().getAdminNo());

		// 자기글만 삭제시킴
		writeJson(parameter, vendorService.updateVendorCoworkMemoToDelete(bdCorprBoardMemo));
	}

	/**
	 * @Desc : 협력게시판 답변 등록/수정
	 * @Method Name : updateCoworkReply
	 * @Date : 2019. 3. 6.
	 * @Author : ansuk
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/cowork/update-cowork")
	public void updateCoworkReply(Parameter<BdCorprBoard> parameter) throws Exception {
		BdCorprBoard bdCorprBoard = parameter.get();
		bdCorprBoard.setAswrNo(LoginManager.getUserDetails().getAdminNo());

		writeJson(parameter, vendorService.updateVendorCoworkReply(bdCorprBoard));
	}

	/**
	 * @Desc : 협력게시판 삭제 - 질문자용
	 * @Method Name : deleteCowork
	 * @Date : 2019. 3. 7.
	 * @Author : ansuk
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/cowork/delete-cowork")
	public void deleteCowork(Parameter<BdCorprBoard> parameter) throws Exception {
		BdCorprBoard bdCorprBoard = parameter.get();
		bdCorprBoard.setInqrerNo((LoginManager.getUserDetails().getAdminNo())); // 질문자가 자기 자신인 글만 삭제

		vendorService.deleteVendorCowork(bdCorprBoard);
	}

	/**
	 *
	 * @Desc : 입점문의 삭제
	 * @Method Name : deleteInquiry
	 * @Date : 2019. 3. 13.
	 * @Author : ansuk
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/inquiry/delete-inquiry")
	public void deleteInquiry(Parameter<BdContactUs> parameter) throws Exception {
		BdContactUs bdContactUs = parameter.get();

		// 질문자가 삭제할수도, 관리자가 삭제할수도 있으므로 마지막 삭제자 기록 남김
		vendorService.updateVendorInquiryForDelete(bdContactUs);
	}

	/**
	 * @Desc : 입점관리 1:1 문의 조회 form을 조회한다.
	 * @Method Name : individualInquirySearchForm
	 * @Date : 2019. 4. 12.
	 * @Author : 고웅환
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/individual-inquiry")
	public ModelAndView individualInquirySearchForm(Parameter<?> parameter) throws Exception {
		Map<String, Object> result = vendorService.getIndividualInquiryMain(parameter);

		if (!UtilsText.isBlank(parameter.getString("fromDash"))
				&& UtilsText.equals(Const.BOOLEAN_TRUE, parameter.getString("fromDash"))) {
			parameter.addAttribute("fromDash", parameter.getString("fromDash"));
		}

		parameter.addAttribute("codeList", result.get("codeList"));

		return forward("/vendor/individual-inquiry/individual-inquiry-main");
	}

	/**
	 * @Desc : 입점관리 1:1 문의 정보를 조회한다.
	 * @Method Name : readIndividualInquiryList
	 * @Date : 2019. 4. 12.
	 * @Author : 고웅환
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/individual-inquiry/read-list")
	public void readIndividualInquiryList(Parameter<BdInquirySearchVO> parameter) throws Exception {
		UserDetails userDetails = LoginManager.getUserDetails();
		Pageable<BdInquirySearchVO, BdMemberCounsel> inquirySearchVO = new Pageable<>(parameter);

		inquirySearchVO.getBean().setVndrAssignYn(Const.BOOLEAN_TRUE);
		if (UtilsText.equals(userDetails.getUpAuthNo(), Const.ROLE_VENDER_GROUP)) {
			inquirySearchVO.getBean().setVndrNo(userDetails.getVndrNo());
		}
		Page<BdMemberCounsel> page = bdInquiryService.getInquiryReadList(inquirySearchVO);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 입점관리 1:1 문의 상세 조회 팝업
	 * @Method Name : readIndividualInquiryDetail
	 * @Date : 2019. 4. 12.
	 * @Author : 고웅환
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/individual-inquiry/detail")
	public ModelAndView readIndividualInquiryDetail(Parameter<BdMemberCounsel> parameter) throws Exception {
		BdMemberCounsel bdMemberCounsel = parameter.get();

		Map<String, Object> rtnVal = vendorService.getIndividualInquiryDetail(bdMemberCounsel);

		parameter.addAttribute("bdMemberCounsel", rtnVal.get("bdMemberCounsel")); // 상담정보 및 답변 정보
		parameter.addAttribute("inqryCounselAttachFiles", rtnVal.get("inqryCounselAttachFiles")); // 문의 첨부 파일
		parameter.addAttribute("aswrCounselAttachFiles", rtnVal.get("aswrCounselAttachFiles")); // 답변 첨부 파일
		parameter.addAttribute("bdMemberCounselMemo", rtnVal.get("bdMemberCounselMemo")); // 관리자 메모
		parameter.addAttribute("memberInfo", rtnVal.get("memberInfo")); // 회원 정보

		return forward("/vendor/individual-inquiry/individual-inquiry-detail-pop");
	}

	/**
	 * @Desc : 입점관리 1:1 문의 답변 등록
	 * @Method Name : updateIndividualInquiry
	 * @Date : 2019. 4. 15.
	 * @Author : 고웅환
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/individual-inquiry/update")
	public void updateIndividualInquiry(Parameter<BdMemberCounsel> parameter) throws Exception {
		BdMemberCounsel bdMemberCounselParam = parameter.get();

		Map<String, Object> resultMap = new HashMap<>();
		// bdMemberCounselParam.validate();
		FileUpload[] uploadFiles = bdMemberCounselParam.getAswrUpLoadFile();
		BdMemberCounselAttachFile[] bdMemberAtchFiles = bdInquiryService.uploadAtchFile(uploadFiles);

		try {

			bdMemberCounselParam.setAswrAtchFiles(bdMemberAtchFiles);
			bdInquiryService.updateInquryDetail(bdMemberCounselParam);

			resultMap.put("code", Const.BOOLEAN_TRUE);
			resultMap.put("memberCnslSeq", bdMemberCounselParam.getMemberCnslSeq());
		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("message", e.toString());
			bdInquiryService.deleteNasImage(bdMemberAtchFiles);
		}
		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 입점관리 1:1문의 관리자메모 작성
	 * @Method Name : createAdminMemo
	 * @Date : 2019. 4. 15.
	 * @Author : 고웅환
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/individual-inquiry/create-adminmemo")
	public void createAdminMemo(Parameter<BdMemberCounselMemo> parameter) throws Exception {
		BdMemberCounselMemo bdMemberCounselMemo = parameter.get();

		Map<String, Object> resultMap = new HashMap<>();

		try {
			bdMemberCounselMemo.validate();
			BdMemberCounselMemo resultVO = bdInquiryService.setAdminMemo(bdMemberCounselMemo);

			resultMap.put("code", Const.BOOLEAN_TRUE);
			resultMap.put("message", "저장되었습니다.");

			resultMap.put("memberCnslSeq", resultVO.getMemberCnslSeq());
			resultMap.put("cnslMemoSeq", resultVO.getCnslMemoSeq());
			resultMap.put("rgsterDpName", resultVO.getRgsterDpName());
			resultMap.put("rgsterNo", resultVO.getRgsterNo());
			resultMap.put("rgstDtm", resultVO.getRgstDtm());
			resultMap.put("memoText", resultVO.getMemoText());
		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("message", e.toString());
		}
		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 입점관리 1:1문의 관리자 메모 삭제
	 * @Method Name : removeAdminMemo
	 * @Date : 2019. 4. 15.
	 * @Author : 고웅환
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/individual-inquiry/remove-adminmemo")
	public void removeAdminMemo(Parameter<BdMemberCounselMemo> parameter) throws Exception {
		BdMemberCounselMemo bdMemberCounselMemo = parameter.get();

		bdInquiryService.deleteAdminMemo(bdMemberCounselMemo);
	}

	/**
	 * @Desc : 입점관리 1:1문의 관리자 첨부파일 삭제
	 * @Method Name : removeMemberCounselAtchFile
	 * @Date : 2019. 4. 15.
	 * @Author : 고웅환
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/individual-inquiry/remove-membercounsel-atchfile")
	@ResponseBody
	public void removeMemberCounselAtchFile(Parameter<BdMemberCounselAttachFile> parameter) throws Exception {
		BdMemberCounselAttachFile bdMemberCounselAttachFile = parameter.get();

		bdInquiryService.deleteCounselAtchFile(bdMemberCounselAttachFile);

	}

}