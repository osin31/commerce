package kr.co.abcmart.bo.stats.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.stats.model.master.CurrentSaleStats;
import kr.co.abcmart.bo.stats.repository.master.OrderRealTimeStatsDao;
import kr.co.abcmart.bo.stats.vo.OrderStatsSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.repository.master.VdVendorDao;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderRealTimeStatsService {

	@Autowired
	private SiteService siteService;
	@Autowired
	private CommonCodeService commonCodeService;
	@Autowired
	private OrderRealTimeStatsDao orderRealTimeStatsDao;
	@Autowired
	private VendorService vendorService;
	@Autowired
	private VdVendorDao vdVenderDao;

	public void getStatusSearchCondition(Parameter<?> parameter) throws Exception {

		String[] codeFields = { CommonCode.PYMNT_MEANS_CODE // 결제수단코드
				, CommonCode.STOCK_GBN_CODE // 발송처
				, CommonCode.DEVICE_CODE // 디바이스 구분코드
				, CommonCode.MEMBER_TYPE_CODE // 회원 구분코드
				, CommonCode.GENDER_GBN_CODE // 성별 구분코드
		};

		// 사이트 정보
		Pair<JSONObject, List<SySite>> siteInfo = siteService.getSiteListByCombo();
		parameter.addAttribute("siteCombo", siteInfo.getFirst());
		parameter.addAttribute("siteInfo", siteInfo.getSecond());

		// 코드 정보
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);
		parameter.addAttribute("codeCombo", pair.getFirst());
		parameter.addAttribute("codeList", pair.getSecond());

		// 채널 정보
		Pair<JSONObject, List<SySiteChnnl>> chnallInfo = siteService.getUseChannelListByCombo();
		parameter.addAttribute("chnnlCombo", chnallInfo.getFirst());
		parameter.addAttribute("chnnlInfo", chnallInfo.getSecond());

		log.error(" chnnlInfo : {}", chnallInfo.getSecond());

		// 현재 시간 조회
		parameter.addAttribute("currentDateTime", orderRealTimeStatsDao.selectCurrentDateTime());

		// bo, po 권한 구분
		UserDetails user = LoginManager.getUserDetails();
		Boolean isAdmin = UtilsText.equals(user.getUpAuthNo(), Const.ROLE_ADMIN_GROUP) ? true : false;

		parameter.addAttribute("isAdmin", isAdmin);
	}

	/**
	 * @Desc : 당일 매출현황 목록 조회.
	 * @Method Name : getCurrentSalesStatsList
	 * @Date : 2019. 7. 18.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	public Page<CurrentSaleStats> getCurrentSalesStatsList(Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable)
			throws Exception {

		pageable.setContent(orderRealTimeStatsDao.selectCurrentSaleStatsList(pageable));

		return pageable.getPage();
	}

	/**
	 * @Desc : 당일 매출현황 상품상세 팝업 조회
	 * @Method Name : getCurrentSalesDetailList
	 * @Date : 2019. 7. 19.
	 * @Author : 이재렬
	 * @param pageable
	 * @throws Exception
	 */
	public Page<CurrentSaleStats> getCurrentSalesDetailList(Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable)
			throws Exception {

		pageable.setContent(orderRealTimeStatsDao.selectCurrentSaleDetailList(pageable));

		return pageable.getPage();
	}

	/**
	 * @Desc : 당일 디바이스별 매출현황 목록 조회.
	 * @Method Name : getCurrentDeviceStatsList
	 * @Date : 2019. 7. 19.
	 * @Author : 이재렬
	 * @param pageable
	 * @throws Exception
	 */
	public Page<CurrentSaleStats> getCurrentDeviceStatsList(Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable)
			throws Exception {

		pageable.setContent(orderRealTimeStatsDao.selectCurrentDeviceList(pageable));

		return pageable.getPage();
	}

	/**
	 * @Desc : 당일 결제수단별 현황 목록 조회
	 * @Method Name : getCurrentPaymentStatsList
	 * @Date : 2019. 7. 23.
	 * @Author : 이재렬
	 * @param pageable
	 * @throws Exception
	 */
	public Page<CurrentSaleStats> getCurrentPaymentStatsList(Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable)
			throws Exception {

		pageable.setContent(orderRealTimeStatsDao.selectCurrentPaymentList(pageable));

		return pageable.getPage();
	}

	/**
	 * @Desc : 당일 상품 판매 현황 목록 조회
	 * @Method Name : getCurrentPrdtStatsList
	 * @Date : 2019. 7. 23.
	 * @Author : 이재렬
	 * @param pageable
	 * @throws Exception
	 */
	public Page<CurrentSaleStats> getCurrentPrdtStatsList(Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable)
			throws Exception {

		pageable.setContent(orderRealTimeStatsDao.selectCurrentPrdtList(pageable));

		return pageable.getPage();
	}

	/**
	 * @Desc : 교환/반품 클레임 업체별/사유별 카운트 조회
	 * @Method getCurrentClaimStatsList
	 * @Date : 2019. 10. 16.
	 * @Author : 이강수
	 * @param Pageable<OrderStatsSearchVO, CurrentSaleStats>
	 * @return Page<CurrentSaleStats>
	 * @throws Exception
	 */
	public Page<CurrentSaleStats> getCurrentClaimStatsList(Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable)
			throws Exception {
		String sortColumn = pageable.getSortColumn();
		String sortType = pageable.getSortType();
		String claimType = pageable.getBean().getClaimType();

		// 전체 교환/반품 클레임 업체별/사유별 카운트 조회
		List<CurrentSaleStats> claimCntList = orderRealTimeStatsDao.selectClaimCntGroupbyVndr(pageable);

		/**
		 * 화면으로 내보낼 오브젝트 set
		 */
		List<CurrentSaleStats> resultClaimCntList = new ArrayList<CurrentSaleStats>();

		CurrentSaleStats allStat = new CurrentSaleStats();
		allStat.setVndrGbn("합계");

		if (UtilsObject.isEmpty(pageable.getBean().getVndrNo())) {
			if (UtilsText.equals(claimType, "return")) {
				this.setReturnClaimStat(claimCntList, allStat);
			} else if (UtilsText.equals(claimType, "change")) {
				this.setExchangeClaimStat(claimCntList, allStat);
			}
		}

		/**
		 * 업체들 (입점1 + .... + 입점n) 리스트
		 */
		int count = vdVenderDao.selectVendorListCounting(pageable);
		pageable.setTotalCount(count);

		List<VdVendor> vendorList = new ArrayList<VdVendor>();
		if (count > 0) {
			vendorList = vdVenderDao.selectVendorListPaging(pageable);
		}

		for (VdVendor vndr : vendorList) {
			CurrentSaleStats vndrStat = new CurrentSaleStats();
			vndrStat.setVndrNo(vndr.getVndrNo());
			vndrStat.setVndrGbn(vndr.getVndrName());
			resultClaimCntList.add(vndrStat);
		}

		if (UtilsText.equals(claimType, "return")) {
			for (CurrentSaleStats retCss : resultClaimCntList) {
				log.debug("업체 : " + retCss.getVndrNo() + " / " + retCss.getVndrGbn());
				if (UtilsObject.isNotEmpty(retCss.getVndrNo())) {
					this.setVndrReturnClaimStat(claimCntList, retCss, retCss.getVndrNo());
				}
			}

			// 시트에서 넘어온 sortColumn, sortType으로 sort
			if (UtilsObject.isNotEmpty(sortColumn)) {
				if (UtilsText.equals(sortColumn, "returnTotalCnt") && UtilsText.equals(sortType, "ASC")) {
					resultClaimCntList = resultClaimCntList.stream()
							.sorted(Comparator.comparing(CurrentSaleStats::getReturnTotalCnt))
							.collect(Collectors.toList());

				} else if (UtilsText.equals(sortColumn, "returnImpsblCnt") && UtilsText.equals(sortType, "ASC")) {
					resultClaimCntList = resultClaimCntList.stream()
							.sorted(Comparator.comparing(CurrentSaleStats::getReturnImpsblCnt))
							.collect(Collectors.toList());

				} else if (UtilsText.equals(sortColumn, "returnFinishCnt") && UtilsText.equals(sortType, "ASC")) {
					resultClaimCntList = resultClaimCntList.stream()
							.sorted(Comparator.comparing(CurrentSaleStats::getReturnFinishCnt))
							.collect(Collectors.toList());

				} else if (UtilsText.equals(sortColumn, "returnProcCnt") && UtilsText.equals(sortType, "ASC")) {
					resultClaimCntList = resultClaimCntList.stream()
							.sorted(Comparator.comparing(CurrentSaleStats::getReturnProcCnt))
							.collect(Collectors.toList());

				} else if (UtilsText.equals(sortColumn, "returnTotalCnt") && UtilsText.equals(sortType, "DESC")) {
					resultClaimCntList = resultClaimCntList.stream()
							.sorted(Comparator.comparing(CurrentSaleStats::getReturnTotalCnt).reversed())
							.collect(Collectors.toList());

				} else if (UtilsText.equals(sortColumn, "returnImpsblCnt") && UtilsText.equals(sortType, "DESC")) {
					resultClaimCntList = resultClaimCntList.stream()
							.sorted(Comparator.comparing(CurrentSaleStats::getReturnImpsblCnt).reversed())
							.collect(Collectors.toList());

				} else if (UtilsText.equals(sortColumn, "returnFinishCnt") && UtilsText.equals(sortType, "DESC")) {
					resultClaimCntList = resultClaimCntList.stream()
							.sorted(Comparator.comparing(CurrentSaleStats::getReturnFinishCnt).reversed())
							.collect(Collectors.toList());

				} else if (UtilsText.equals(sortColumn, "returnProcCnt") && UtilsText.equals(sortType, "DESC")) {
					resultClaimCntList = resultClaimCntList.stream()
							.sorted(Comparator.comparing(CurrentSaleStats::getReturnProcCnt).reversed())
							.collect(Collectors.toList());
				}
			}

		} else if (UtilsText.equals(claimType, "change")) {
			for (CurrentSaleStats retCss : resultClaimCntList) {
				log.debug("업체 : " + retCss.getVndrNo() + " / " + retCss.getVndrGbn());
				if (UtilsObject.isNotEmpty(retCss.getVndrNo())) {
					this.setVndrExchangeClaimStat(claimCntList, retCss, retCss.getVndrNo());
				}
			}

			// 시트에서 넘어온 sortColumn, sortType으로 sort
			if (UtilsObject.isNotEmpty(sortColumn)) {
				if (UtilsText.equals(sortColumn, "exchangeTotalCnt") && UtilsText.equals(sortType, "ASC")) {
					resultClaimCntList = resultClaimCntList.stream()
							.sorted(Comparator.comparing(CurrentSaleStats::getExchangeTotalCnt))
							.collect(Collectors.toList());

				} else if (UtilsText.equals(sortColumn, "exchangeImpsblCnt") && UtilsText.equals(sortType, "ASC")) {
					resultClaimCntList = resultClaimCntList.stream()
							.sorted(Comparator.comparing(CurrentSaleStats::getExchangeImpsblCnt))
							.collect(Collectors.toList());

				} else if (UtilsText.equals(sortColumn, "exchangeFinishCnt") && UtilsText.equals(sortType, "ASC")) {
					resultClaimCntList = resultClaimCntList.stream()
							.sorted(Comparator.comparing(CurrentSaleStats::getExchangeFinishCnt))
							.collect(Collectors.toList());

				} else if (UtilsText.equals(sortColumn, "exchangeProcCnt") && UtilsText.equals(sortType, "ASC")) {
					resultClaimCntList = resultClaimCntList.stream()
							.sorted(Comparator.comparing(CurrentSaleStats::getExchangeProcCnt))
							.collect(Collectors.toList());

				} else if (UtilsText.equals(sortColumn, "exchangeTotalCnt") && UtilsText.equals(sortType, "DESC")) {
					resultClaimCntList = resultClaimCntList.stream()
							.sorted(Comparator.comparing(CurrentSaleStats::getExchangeTotalCnt).reversed())
							.collect(Collectors.toList());

				} else if (UtilsText.equals(sortColumn, "exchangeImpsblCnt") && UtilsText.equals(sortType, "DESC")) {
					resultClaimCntList = resultClaimCntList.stream()
							.sorted(Comparator.comparing(CurrentSaleStats::getExchangeImpsblCnt).reversed())
							.collect(Collectors.toList());

				} else if (UtilsText.equals(sortColumn, "exchangeFinishCnt") && UtilsText.equals(sortType, "DESC")) {
					resultClaimCntList = resultClaimCntList.stream()
							.sorted(Comparator.comparing(CurrentSaleStats::getExchangeFinishCnt).reversed())
							.collect(Collectors.toList());

				} else if (UtilsText.equals(sortColumn, "exchangeProcCnt") && UtilsText.equals(sortType, "DESC")) {
					resultClaimCntList = resultClaimCntList.stream()
							.sorted(Comparator.comparing(CurrentSaleStats::getExchangeProcCnt).reversed())
							.collect(Collectors.toList());
				}
			}
		}

		// 시트에서 넘어온 sortColumn, sortType으로 sort
		if (UtilsObject.isNotEmpty(sortColumn)) {
			if (UtilsText.equals(sortColumn, "vndrGbn") && UtilsText.equals(sortType, "ASC")) {
				resultClaimCntList = resultClaimCntList.stream()
						.sorted(Comparator.comparing(CurrentSaleStats::getVndrGbn)).collect(Collectors.toList());

			} else if (UtilsText.equals(sortColumn, "vndrGbn") && UtilsText.equals(sortType, "DESC")) {
				resultClaimCntList = resultClaimCntList.stream()
						.sorted(Comparator.comparing(CurrentSaleStats::getVndrGbn).reversed())
						.collect(Collectors.toList());
			}
		}

		if (UtilsObject.isEmpty(pageable.getBean().getVndrNo())) {
			resultClaimCntList.add(0, allStat);
		}
		log.debug("claimType : " + claimType);
		pageable.setContent(resultClaimCntList);
		return pageable.getPage();
	}

	public void setReturnClaimStat(List<CurrentSaleStats> claimCntList, CurrentSaleStats allStat) throws Exception {
		/**
		 * 반품 자사들 + 업체들 통계
		 */
		// 클레임 상태 구분 별
		// 반품 불가 합계 건수 (자사 + 입점1 + ... + 입점n)
		int allReturnImpsblCnt = 0;
		allReturnImpsblCnt = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getGbn().substring(x.getGbn().length() - 1, x.getGbn().length()), "R"))
				.filter(x -> UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allReturnImpsblCnt : " + allReturnImpsblCnt);

		// 반품 완료 합계 건수 (자사 + 입점1 + ... + 입점n)
		int allReturnFinishCnt = 0;
		allReturnFinishCnt = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getGbn().substring(x.getGbn().length() - 1, x.getGbn().length()), "R"))
				.filter(x -> UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_FINISH))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allReturnFinishCnt : " + allReturnFinishCnt);

		// 반품 처리중 합계 건수 (자사 + 입점1 + ... + 입점n)
		int allReturnProcCnt = 0;
		allReturnProcCnt = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getGbn().substring(x.getGbn().length() - 1, x.getGbn().length()), "R"))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE)
						&& !UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_FINISH)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allReturnProcCnt : " + allReturnProcCnt);

		int allReturnTotalCnt = allReturnImpsblCnt + allReturnFinishCnt + allReturnProcCnt;
		log.debug("allReturnTotalCnt : " + allReturnTotalCnt);

		// 클레임 사유 구분 별
		// 불량
		int allReturnRsn01 = 0;
		allReturnRsn01 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_RETURN_BAD))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allReturnRsn01 불량 : " + allReturnRsn01);
		// 오배송
		int allReturnRsn02 = 0;
		allReturnRsn02 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_RETURN_WRONG_DELIVERY))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allReturnRsn02 오배송 : " + allReturnRsn02);
		// 변심(소재)
		int allReturnRsn03 = 0;
		allReturnRsn03 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_RETURN_REMORSE_MATERIAL))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allReturnRsn03 변심(소재) : " + allReturnRsn03);
		// 변심(사이즈미스)
		int allReturnRsn04 = 0;
		allReturnRsn04 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_RETURN_REMORSE_SIZEMISS))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allReturnRsn04 변심(사이즈미스) : " + allReturnRsn04);
		// 변심(화면과 실물 상이)
		int allReturnRsn05 = 0;
		allReturnRsn05 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_RETURN_REMORSE_DIFFERENT))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allReturnRsn05 변심(화면과실물상이) : " + allReturnRsn05);
		// 미출반품
		int allReturnRsn06 = 0;
		allReturnRsn06 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_RETURN_NOT_DELIVERY))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allReturnRsn06 미출반품 : " + allReturnRsn06);
		// 오프라인반품
		int allReturnRsn07 = 0;
		allReturnRsn07 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_RETURN_OFFLINE))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allReturnRsn07 오프라인반품 : " + allReturnRsn07);
		// 브랜드 박스 훼손
		int allReturnRsn08 = 0;
		allReturnRsn08 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_RETURN_BOX_DAMAGE))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allReturnRsn08 브랜드박스훼손 : " + allReturnRsn08);
		// 기타
		int allReturnRsn09 = 0;
		allReturnRsn09 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_RETURN_ETC))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allReturnRsn09 기타 : " + allReturnRsn09);

		allStat.setReturnTotalCnt(allReturnTotalCnt);
		allStat.setReturnImpsblCnt(allReturnImpsblCnt);
		allStat.setReturnFinishCnt(allReturnFinishCnt);
		allStat.setReturnProcCnt(allReturnProcCnt);

		allStat.setReturnRsn01(allReturnRsn01);
		allStat.setReturnRsn02(allReturnRsn02);
		allStat.setReturnRsn03(allReturnRsn03);
		allStat.setReturnRsn04(allReturnRsn04);
		allStat.setReturnRsn05(allReturnRsn05);
		allStat.setReturnRsn06(allReturnRsn06);
		allStat.setReturnRsn07(allReturnRsn07);
		allStat.setReturnRsn08(allReturnRsn08);
		allStat.setReturnRsn09(allReturnRsn09);
	}

	public void setExchangeClaimStat(List<CurrentSaleStats> claimCntList, CurrentSaleStats allStat) throws Exception {
		/**
		 * 교환 자사들 + 업체들 통계
		 */
		// 교환 불가 합계 건수 (자사 + 입점1 + ... + 입점n)
		int allExchangeImpsblCnt = 0;
		allExchangeImpsblCnt = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getGbn().substring(x.getGbn().length() - 1, x.getGbn().length()), "E"))
				.filter(x -> UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allExchangeImpsblCnt : " + allExchangeImpsblCnt);

		// 교환 완료 합계 건수 (자사 + 입점1 + ... + 입점n)
		int allExchangeFinishCnt = 0;
		allExchangeFinishCnt = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getGbn().substring(x.getGbn().length() - 1, x.getGbn().length()), "E"))
				.filter(x -> UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_FINISH))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allExchangeFinishCnt : " + allExchangeFinishCnt);

		// 교환 처리중 합계 건수 (자사 + 입점1 + ... + 입점n)
		int allExchangeProcCnt = 0;
		allExchangeProcCnt = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getGbn().substring(x.getGbn().length() - 1, x.getGbn().length()), "E"))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE)
						&& !UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_FINISH)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allExchangeProcCnt : " + allExchangeProcCnt);

		int allExchangeTotalCnt = allExchangeImpsblCnt + allExchangeFinishCnt + allExchangeProcCnt;
		log.debug("allExchangeTotalCnt : " + allExchangeTotalCnt);

		// 불량
		int allExchangeRsn01 = 0;
		allExchangeRsn01 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_EXCHANGE_BAD))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allExchangeRsn01 불량 : " + allExchangeRsn01);
		// 오배송
		int allExchangeRsn02 = 0;
		allExchangeRsn02 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_EXCHANGE_WRONG_DELIVERY))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allExchangeRsn02 오배송 : " + allExchangeRsn02);
		// 옵션변경
		int allExchangeRsn03 = 0;
		allExchangeRsn03 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_EXCHANGE_OPTION_CHANGE))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allExchangeRsn03 옵션변경 : " + allExchangeRsn03);
		// 브랜드 박스 훼손
		int allExchangeRsn04 = 0;
		allExchangeRsn04 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_EXCHANGE_BOX_DAMAGE))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allExchangeRsn04 브랜드 박스 훼손 : " + allExchangeRsn04);
		// 기타
		int allExchangeRsn05 = 0;
		allExchangeRsn05 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_EXCHANGE_ETC))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL))
				.collect(Collectors.toList()).stream().mapToInt(x -> x.getCnt()).sum();
		log.debug("allExchangeRsn05 기타 : " + allExchangeRsn05);

		allStat.setExchangeTotalCnt(allExchangeTotalCnt);
		allStat.setExchangeImpsblCnt(allExchangeImpsblCnt);
		allStat.setExchangeFinishCnt(allExchangeFinishCnt);
		allStat.setExchangeProcCnt(allExchangeProcCnt);

		allStat.setExchangeRsn01(allExchangeRsn01);
		allStat.setExchangeRsn02(allExchangeRsn02);
		allStat.setExchangeRsn03(allExchangeRsn03);
		allStat.setExchangeRsn04(allExchangeRsn04);
		allStat.setExchangeRsn05(allExchangeRsn05);
	}

	public void setVndrReturnClaimStat(List<CurrentSaleStats> claimCntList, CurrentSaleStats vndrStat, String vndrNo)
			throws Exception {

		// 반품 불가 합계 건수 (업체)
		int vndrReturnImpsblCnt = 0;
		vndrReturnImpsblCnt = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getGbn().substring(x.getGbn().length() - 1, x.getGbn().length()), "R"))
				.filter(x -> UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();

		// 반품 완료 합계 건수 (업체)
		int vndrReturnFinishCnt = 0;
		vndrReturnFinishCnt = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getGbn().substring(x.getGbn().length() - 1, x.getGbn().length()), "R"))
				.filter(x -> UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_FINISH))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();

		// 반품 처리중 합계 건수 (업체)
		int vndrReturnProcCnt = 0;
		vndrReturnProcCnt = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getGbn().substring(x.getGbn().length() - 1, x.getGbn().length()), "R"))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE)
						&& !UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_RETURN_FINISH)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();

		int vndrReturnTotalCnt = vndrReturnImpsblCnt + vndrReturnFinishCnt + vndrReturnProcCnt;
		log.debug(vndrNo + " / " + "vndrReturnTotalCnt : " + vndrReturnTotalCnt);

		// 클레임 사유 구분 별
		// 불량
		int vndrReturnRsn01 = 0;
		vndrReturnRsn01 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_RETURN_BAD))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();
		// 오배송
		int vndrReturnRsn02 = 0;
		vndrReturnRsn02 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_RETURN_WRONG_DELIVERY))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();
		// 변심(소재)
		int vndrReturnRsn03 = 0;
		vndrReturnRsn03 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_RETURN_REMORSE_MATERIAL))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();
		// 변심(사이즈미스)
		int vndrReturnRsn04 = 0;
		vndrReturnRsn04 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_RETURN_REMORSE_SIZEMISS))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();
		// 변심(화면과 실물 상이)
		int vndrReturnRsn05 = 0;
		vndrReturnRsn05 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_RETURN_REMORSE_DIFFERENT))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();
		// 미출반품
		int vndrReturnRsn06 = 0;
		vndrReturnRsn06 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_RETURN_NOT_DELIVERY))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();
		// 오프라인반품
		int vndrReturnRsn07 = 0;
		vndrReturnRsn07 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_RETURN_OFFLINE))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();
		// 브랜드 박스 훼손
		int vndrReturnRsn08 = 0;
		vndrReturnRsn08 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_RETURN_BOX_DAMAGE))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();
		// 기타
		int vndrReturnRsn09 = 0;
		vndrReturnRsn09 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_RETURN_ETC))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();

		vndrStat.setReturnTotalCnt(vndrReturnTotalCnt);
		vndrStat.setReturnImpsblCnt(vndrReturnImpsblCnt);
		vndrStat.setReturnFinishCnt(vndrReturnFinishCnt);
		vndrStat.setReturnProcCnt(vndrReturnProcCnt);

		vndrStat.setReturnRsn01(vndrReturnRsn01);
		vndrStat.setReturnRsn02(vndrReturnRsn02);
		vndrStat.setReturnRsn03(vndrReturnRsn03);
		vndrStat.setReturnRsn04(vndrReturnRsn04);
		vndrStat.setReturnRsn05(vndrReturnRsn05);
		vndrStat.setReturnRsn06(vndrReturnRsn06);
		vndrStat.setReturnRsn07(vndrReturnRsn07);
		vndrStat.setReturnRsn08(vndrReturnRsn08);
		vndrStat.setReturnRsn09(vndrReturnRsn09);
	}

	public void setVndrExchangeClaimStat(List<CurrentSaleStats> claimCntList, CurrentSaleStats vndrStat, String vndrNo)
			throws Exception {
		// 교환 불가 합계 건수 (업체)
		int vndrExchangeImpsblCnt = 0;
		vndrExchangeImpsblCnt = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getGbn().substring(x.getGbn().length() - 1, x.getGbn().length()), "E"))
				.filter(x -> UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();

		// 교환 완료 합계 건수 (업체)
		int vndrExchangeFinishCnt = 0;
		vndrExchangeFinishCnt = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getGbn().substring(x.getGbn().length() - 1, x.getGbn().length()), "E"))
				.filter(x -> UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_FINISH))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();

		// 교환 처리중 합계 건수 (자사)
		int vndrExchangeProcCnt = 0;
		vndrExchangeProcCnt = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getGbn().substring(x.getGbn().length() - 1, x.getGbn().length()), "E"))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE)
						&& !UtilsText.equals(x.getClmPrdtStatCode(), CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_FINISH)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();

		int vndrExchangeTotalCnt = vndrExchangeImpsblCnt + vndrExchangeFinishCnt + vndrExchangeProcCnt;
		log.debug(vndrNo + " / " + "vndrExchangeTotalCnt : " + vndrExchangeTotalCnt);

		// 불량
		int vndrExchangeRsn01 = 0;
		vndrExchangeRsn01 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_EXCHANGE_BAD))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();
		// 오배송
		int vndrExchangeRsn02 = 0;
		vndrExchangeRsn02 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_EXCHANGE_WRONG_DELIVERY))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();
		// 옵션변경
		int vndrExchangeRsn03 = 0;
		vndrExchangeRsn03 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_EXCHANGE_OPTION_CHANGE))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();
		// 브랜드 박스 훼손
		int vndrExchangeRsn04 = 0;
		vndrExchangeRsn04 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_EXCHANGE_BOX_DAMAGE))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();
		// 기타
		int vndrExchangeRsn05 = 0;
		vndrExchangeRsn05 = claimCntList.stream()
				.filter(x -> UtilsText.equals(x.getRsn().substring(x.getRsn().length() - 5, x.getRsn().length()),
						CommonCode.CLM_RSN_CODE_EXCHANGE_ETC))
				.filter(x -> !UtilsText.equals(x.getClmPrdtStatCode(),
						CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT)
						&& !UtilsText.equals(x.getClmPrdtStatCode(),
								CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL))
				.filter(x -> UtilsText.equals(x.getVndrNo(), vndrNo)).collect(Collectors.toList()).stream()
				.mapToInt(x -> x.getCnt()).sum();

		vndrStat.setExchangeTotalCnt(vndrExchangeTotalCnt);
		vndrStat.setExchangeImpsblCnt(vndrExchangeImpsblCnt);
		vndrStat.setExchangeFinishCnt(vndrExchangeFinishCnt);
		vndrStat.setExchangeProcCnt(vndrExchangeProcCnt);

		vndrStat.setExchangeRsn01(vndrExchangeRsn01);
		vndrStat.setExchangeRsn02(vndrExchangeRsn02);
		vndrStat.setExchangeRsn03(vndrExchangeRsn03);
		vndrStat.setExchangeRsn04(vndrExchangeRsn04);
		vndrStat.setExchangeRsn05(vndrExchangeRsn05);
	}

}
