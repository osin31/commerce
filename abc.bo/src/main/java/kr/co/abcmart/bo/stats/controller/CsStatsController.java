package kr.co.abcmart.bo.stats.controller;

import java.util.Arrays;
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

import kr.co.abcmart.bo.stats.model.master.CurrentSaleStats;
import kr.co.abcmart.bo.stats.model.master.SaCsStatus;
import kr.co.abcmart.bo.stats.service.CsStatsService;
import kr.co.abcmart.bo.stats.service.OrderRealTimeStatsService;
import kr.co.abcmart.bo.stats.service.SalesStatsService;
import kr.co.abcmart.bo.stats.vo.CsStatsSearchVO;
import kr.co.abcmart.bo.stats.vo.OrderStatsSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/stats/cs")
public class CsStatsController extends BaseController {

	@Autowired
	private CsStatsService csStatsService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private OrderRealTimeStatsService orderRealTimeService;

	@Autowired
	private SalesStatsService salesStatsService;

	@Autowired
	private VendorService vendorService;

	/**
	 * @Desc : 상품 후기 통계 화면
	 * @Method Name : readReviewStatus
	 * @Date : 2019. 7. 23.
	 * @Author : bje0507
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/review-main")
	public ModelAndView readReviewStatus(Parameter<?> parameter) throws Exception {
		String[] codeFields = { CommonCode.DEVICE_CODE, "PLNDP_TYPE_CODE", };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		parameter.addAttribute("searchConditionSiteList", this.siteService.getSiteList()); // 사이트 정보
		parameter.addAttribute("codeList", pair.getSecond());

		return forward("/stats/cs/review-main");
	}

	/**
	 * @Desc : 상품 문의 통계 화면
	 * @Method Name : readInquiryStatus
	 * @Date : 2019. 7. 24.
	 * @Author : bje0507
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/inquiry-main")
	public ModelAndView readInquiryStatus(Parameter<?> parameter) throws Exception {
		SyCodeDetail syCodeDetail = new SyCodeDetail();
		syCodeDetail.setCodeField(CommonCode.CNSL_TYPE_DTL_CODE);
		syCodeDetail.setAddInfo1("10001");

		orderRealTimeService.getStatusSearchCondition(parameter);
		salesStatsService.getToday(parameter);

		String[] codeFields = { CommonCode.DEVICE_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		parameter.addAttribute("codeList", pair.getSecond()); // 디바이스 코드 목록
		parameter.addAttribute("searchConditionSiteList", this.siteService.getSiteList()); // 사이트 정보
		parameter.addAttribute("searchConditionSiteChannelList", this.siteService.getUseChannelList()); // 채널 정보
		parameter.addAttribute("cnslTypeDtlCodeList", commonCodeService.getCodeDtlInfoList(syCodeDetail));

		return forward("/stats/cs/inquiry-main");
	}

	/**
	 * @Desc : 상품 후기 통계 조회
	 * @Method Name : statsReviewSearch
	 * @Date : 2019. 7. 15.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/review-stats-search")
	public void statsReviewSearch(Parameter<CsStatsSearchVO> parameter) throws Exception {

		Pageable<CsStatsSearchVO, SaCsStatus> pageable = new Pageable<>(parameter);
		Page<SaCsStatus> page = csStatsService.getReviewList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 상품 문의 통계 조회
	 * @Method Name : statsInquirySearch
	 * @Date : 2019. 7. 24.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/inquiry-stats-search")
	public void statsInquirySearch(Parameter<CsStatsSearchVO> parameter) throws Exception {

		Pageable<CsStatsSearchVO, SaCsStatus> pageable = new Pageable<>(parameter);
		Page<SaCsStatus> page = csStatsService.getInquiryList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 상품후기 통계 엑셀
	 * @Method Name : downloadReviewExcel
	 * @Date : 2019. 7. 18.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-reviewExcel")
	public void downloadReviewExcel(Parameter<CsStatsSearchVO> parameter) throws Exception {
		Pageable<CsStatsSearchVO, SaCsStatus> pageable = new Pageable<CsStatsSearchVO, SaCsStatus>(parameter);
		CsStatsSearchVO bean = pageable.getBean();
		bean.setIsExcel("Y");
		pageable.setBean(bean);
		Page<SaCsStatus> page = csStatsService.getReviewList(pageable);

		ExcelValue excelValue = ExcelValue.builder(2, 0)
				.columnNames(Arrays.asList("reviewDtm", "totalPoint", "totalPayCount", "totalPayNo", "totalPayCncl",
						"totalPayImpsblt", "generalPoint", "generalPayCount", "generalPayNo", "generalPayCncl",
						"generalPayImpsblt", "photoPoint", "photoPayCount", "photoPayNo", "photoPayCncl",
						"photoPayImpsblt"))
				.data(page.getContent()).build();

		parameter.downloadExcelTemplate("stats/cs/excel/reviewStats", excelValue);
	}

	/**
	 * @Desc : 상품 문의 통계 엑셀
	 * @Method Name : downloadInquiryExcel
	 * @Date : 2019. 7. 18.
	 * @Author : bje0507
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/download-inquiryExcel")
	public void downloadInquiryExcel(Parameter<CsStatsSearchVO> parameter) throws Exception {
		Pageable<CsStatsSearchVO, SaCsStatus> pageable = new Pageable<CsStatsSearchVO, SaCsStatus>(parameter);
		CsStatsSearchVO bean = pageable.getBean();
		bean.setIsExcel("Y");
		pageable.setBean(bean);
		Page<SaCsStatus> page = csStatsService.getInquiryList(pageable);
		List<SaCsStatus> list = page.getContent();

		// 쿼리에서 나온 코드 자르기
		for (int i = 0; i < list.size(); i++) {
			SaCsStatus item = list.get(i);
			String[] cnslList = item.getCnslList().split("\\|");
			for (int j = 0; j < cnslList.length; j++) {
				String cnsl = cnslList[j];
				switch (j) {
				case 0:
					item.setCnsl10001(cnsl);
					break;
				case 1:
					item.setCnsl10002(cnsl);
					break;
				case 2:
					item.setCnsl10003(cnsl);
					break;
				case 3:
					item.setCnsl10004(cnsl);
					break;
				case 4:
					item.setCnsl10005(cnsl);
					break;
				case 5:
					item.setCnsl10006(cnsl);
					break;
				default:
					log.error("예상 수 초과");
					break;
				}
			}
		}

		ExcelValue excelValue = ExcelValue
				.builder(2, 0).columnNames(Arrays.asList("inqryDtm", "totalCount", "aswrReady", "aswrNo", "aswrSuccess",
						"cnsl10001", "cnsl10002", "cnsl10003", "cnsl10004", "cnsl10005", "cnsl10006"))
				.data(list).build();

		parameter.downloadExcelTemplate("stats/cs/excel/inquiryStats", excelValue);
	}

	/**
	 * @Desc : 반품/교환 통계
	 * @Method Name : claimMain
	 * @Date : 2019. 7. 26.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/claim-main")
	public ModelAndView claimMain(Parameter<?> parameter) throws Exception {

		// bo, po 권한 구분
		UserDetails user = LoginManager.getUserDetails();
		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;
		parameter.addAttribute("isAdmin", isAdmin);

		orderRealTimeService.getStatusSearchCondition(parameter);
		salesStatsService.getCommonCondition(parameter);

		return forward("/stats/cs/claim-main");
	}

	/**
	 * @Desc : 반품/교환 완료 통계 목록 조회
	 * @Method Name : claimReadList
	 * @Date : 2019. 10. 18.
	 * @Author : 이재렬, 이강수
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim-read-list")
	public void claimReadList(Parameter<OrderStatsSearchVO> parameter) throws Exception {

		Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable = new Pageable<>(parameter);

		// bo, po 권한 구분
		UserDetails user = LoginManager.getUserDetails();
		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;

		parameter.addAttribute("isAdmin", isAdmin);
		if (!isAdmin) {
			VdVendor vndr = new VdVendor();
			vndr.setVndrNo(user.getVndrNo());
			vndr = vendorService.getVendorBaseInfo(vndr);
			pageable.getBean().setVndrNo(vndr.getVndrNo());
		}

		Page<CurrentSaleStats> page = csStatsService.getClaimList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 반품/교환 통계 엑셀 다운로드
	 * @Method Name : claimExcelDown
	 * @Date : 2019. 10. 18.
	 * @Author : 이재렬, 이강수
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/claim-excel-down")
	public void claimExcelDown(Parameter<OrderStatsSearchVO> parameter) throws Exception {

		Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable = new Pageable<>(parameter);

		// bo, po 권한 구분
		UserDetails user = LoginManager.getUserDetails();
		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;

		parameter.addAttribute("isAdmin", isAdmin);
		if (!isAdmin) {
			VdVendor vndr = new VdVendor();
			vndr.setVndrNo(user.getVndrNo());
			vndr = vendorService.getVendorBaseInfo(vndr);
			pageable.getBean().setVndrNo(vndr.getVndrNo());
		}

		// 엑셀다운로드이므로
		pageable.getBean().setIsExcel(Const.BOOLEAN_TRUE);

		List<CurrentSaleStats> list = csStatsService.getClaimList(pageable).getContent();

		List<String> columnList = null;
		String claimType = parameter.getString("claimType");

		if (UtilsText.equals(claimType, "return")) {
			columnList = Arrays.asList("dateGbn", "returnTotalCnt", "returnRsn01", "returnRsn02", "returnRsn03",
					"returnRsn04", "returnRsn05", "returnRsn06", "returnRsn07", "returnRsn08", "returnRsn09");
			claimType = "Return";
		} else if (UtilsText.equals(claimType, "change")) {
			columnList = Arrays.asList("dateGbn", "exchangeTotalCnt", "exchangeRsn01", "exchangeRsn02", "exchangeRsn03",
					"exchangeRsn04", "exchangeRsn05");
			claimType = "Change";
		}

		ExcelValue excelValue = ExcelValue.builder(2, 0).columnNames(columnList).data(list).build();

		parameter.downloadExcelTemplate("stats/cs/excel/csClaim" + claimType + "Stats", excelValue);
	}

}
