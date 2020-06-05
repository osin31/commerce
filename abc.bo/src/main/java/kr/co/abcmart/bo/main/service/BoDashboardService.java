package kr.co.abcmart.bo.main.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.board.model.master.BdAdminNotice;
import kr.co.abcmart.bo.board.model.master.BdBulkBuyInquiry;
import kr.co.abcmart.bo.board.model.master.BdContactUs;
import kr.co.abcmart.bo.board.model.master.BdCorprBoard;
import kr.co.abcmart.bo.board.model.master.BdMemberCounsel;
import kr.co.abcmart.bo.board.service.AdminNoticeService;
import kr.co.abcmart.bo.board.service.BdBulkBuyInquiryService;
import kr.co.abcmart.bo.board.service.BdInquiryService;
import kr.co.abcmart.bo.claim.service.ClaimService;
import kr.co.abcmart.bo.claim.vo.OcClaimCountVO;
import kr.co.abcmart.bo.main.vo.BoDashboardVO;
import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.service.MemberService;
import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.bo.order.service.OrderService;
import kr.co.abcmart.bo.product.model.master.BdDashboardProduct;
import kr.co.abcmart.bo.product.service.ProductInquiryService;
import kr.co.abcmart.bo.product.service.ProductReviewService;
import kr.co.abcmart.bo.product.service.ProductService;
import kr.co.abcmart.bo.promotion.service.PlanningDisplayService;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsNumber;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoDashboardService {

	@Autowired
	private BdInquiryService bdInquiryService;

	@Autowired
	private VendorService vendorService;

	@Autowired
	private AdminNoticeService adminNoticeService;

	@Autowired
	private BdBulkBuyInquiryService bdBulkBuyInquiryService;

	@Autowired
	private ProductReviewService reviewService;

	@Autowired
	private ProductInquiryService productInquiryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ClaimService claimService;

	@Autowired
	private PlanningDisplayService planningDisplayService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private BdInquiryService inquiryService;

	/**
	 * @Desc : BO 대시보드 데이터를 조회한다.
	 * @Method Name : getDashboardData
	 * @Date : 2019. 4. 24.
	 * @Author : 고웅환
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDashboardData(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();

		// 대시보드 기간 설정
		resultMap.put("nowDtm", Timestamp.valueOf(LocalDateTime.now()));
		resultMap.put("oneMonthAgoDtm", Timestamp.valueOf(LocalDateTime.now().minusMonths(1)));
		resultMap.put("oneDayAgoDtm", Timestamp.valueOf(LocalDateTime.now().minusDays(1)));

		// 대시보드 상품정보 조회
		resultMap.put("productStateList", getDashboardProductCount());

		// 대시보드 주문/배송 현황
		resultMap.put("orderDlvyCountInfo", getDashboardOrderDlvyCount());

		// 미처리 답변 현황
		resultMap.put("unAswrList", makeUnAswrList());

		// 기타문의 현황
		resultMap.put("etcList", makeEtcList());

		// 관리자 공지
		resultMap.put("adminNoticeList", getDashboardAdminNotice(parameter));

		// 관리자 공지 팝업
		resultMap.put("adminNoticeMainPop", getMainPopAdminNotice(parameter));

		// 클레임 현황
		resultMap.put("claimCountInfo", getClaimCountInfo());

		// 진행중인 기획전
		resultMap.put("useplanning", getplanningDisplay());

		// 승인대기 기획전
		resultMap.put("usereadyplanning", getplanningDisplayusereadyplanning());

		// 승인반려 기획전
		resultMap.put("notuseplanning", getplanningDisplaynotuseplanning());

		// 회원현황
		resultMap.put("memberStatus", getDashboardMemberStatus());

		// 주문건수, 주문금액
		Map<String, Object> orderCntAmtMap = getDashboardOrderCntAmt();
		resultMap.put("localOrderCntAmt", orderCntAmtMap.get("localOrderCntAmt"));
		resultMap.put("vendorOrderCntAmt", orderCntAmtMap.get("vendorOrderCntAmt"));

		// 베스트 입점사
		Map<String, Object> bestVndrListMap = getDashBoardBestVndr();
		resultMap.put("bestVndrOrderCnt", bestVndrListMap.get("vndrOrderCnt"));
		resultMap.put("bestVndrOrderAmt", bestVndrListMap.get("vndrOrderAmt"));

		// 관리대상 입점사
		Map<String, Object> mgmtVndrMap = getDashBoardManagementVndr();
		resultMap.put("unAswrVndrList", mgmtVndrMap.get("unAswrVndrList"));
		resultMap.put("delayedDeliveryVndrList", mgmtVndrMap.get("delayedDeliveryVndrList"));

		return resultMap;
	}

	/**
	 * @Desc : 관리자 공지 메인팝업 대상을 조회한다. 최대 3개
	 * @Method Name : getAdminNoticeMainPop
	 * @Date : 2019. 6. 11.
	 * @Author : Kimyounghyun
	 * @param parameter
	 * @return
	 */
	public String[] getMainPopAdminNotice(Parameter<?> parameter) {

		return adminNoticeService.getBoMainPopAdminNotice();
	}

	/**
	 * @Desc : BO 대시보드 미처리 답변 현황의 각 건수 데이터를 리스트로 만들어준다.
	 * @Method Name : makeUnAswrList
	 * @Date : 2019. 5. 10.
	 * @Author : 고웅환
	 * @param
	 * @return
	 * @throws Exception
	 */
	public List<BoDashboardVO> makeUnAswrList() throws Exception {
		List<BoDashboardVO> unAswrList = new ArrayList<>(); // 미처리 답변 현황 list
		Map<String, Integer> inquiryMap = getDashboardInquiry(); // 1:1 문의 건수조회 map
		Map<String, Integer> coworkMap = getDashboardCowork(); // 협력게시판 건수조회 map
		Map<String, Integer> voiceMap = getDashboardVoiceCustom(); // 고객의소리 건수조회 map
		Map<String, Integer> prdtQnaMap = getDashboardProductInquiry(); // 상품 Q&A
		Map<String, Integer> reviewMap = getDashboardReview(); // 상품후기 map
		for (int i = 0; i < 3; i++) {
			BoDashboardVO unAswrListVo = new BoDashboardVO();
			// 전체(0), 자사(1), 입점(2)
			if (i == 0) {
				unAswrListVo.setTabSeq(i);
				unAswrListVo.setInquiryCnt(inquiryMap.get("allCnt"));
				unAswrListVo.setCoworkCnt(coworkMap.get("allCnt"));
				unAswrListVo.setVoiceCnt(voiceMap.get("allCnt"));
				unAswrListVo.setPrdtQnaCnt(prdtQnaMap.get("allProductInquiryCount"));
				unAswrListVo.setReviewCnt(reviewMap.get("allReviewCount"));
				unAswrList.add(unAswrListVo);
			} else if (i == 1) {
				unAswrListVo.setTabSeq(i);
				unAswrListVo.setInquiryCnt(inquiryMap.get("abcCnt"));
				unAswrListVo.setCoworkCnt(coworkMap.get("abcCnt"));
				unAswrListVo.setVoiceCnt(voiceMap.get("abcCnt"));
				unAswrListVo.setPrdtQnaCnt(prdtQnaMap.get("abcProductInquiryCount"));
				unAswrListVo.setReviewCnt(reviewMap.get("abcReviewCount"));
				unAswrList.add(unAswrListVo);
			} else if (i == 2) {
				unAswrListVo.setTabSeq(i);
				unAswrListVo.setInquiryCnt(inquiryMap.get("vndrCnt"));
				unAswrListVo.setCoworkCnt(coworkMap.get("vndrCnt"));
				unAswrListVo.setPrdtQnaCnt(prdtQnaMap.get("vndrProductInquiryCount"));
				unAswrListVo.setReviewCnt(reviewMap.get("vndrReviewCount"));
				unAswrList.add(unAswrListVo);
			}
		}

		// tabSeq로 정렬 후 return
		return unAswrList.stream().sorted(Comparator.comparing(BoDashboardVO::getTabSeq)).collect(Collectors.toList());
	}

	/**
	 * @Desc : BO 대시보드 기타 문의 현황의 각 건수 데이터를 리스트로 만들어준다.
	 * @Method Name : makeEtcList
	 * @Date : 2019. 5. 10.
	 * @Author : 고웅환
	 * @param
	 * @return
	 * @throws Exception
	 */
	public List<BoDashboardVO> makeEtcList() throws Exception {
		List<BoDashboardVO> etcList = new ArrayList<>(); // 미처리 답변 현황 list
		Map<String, Integer> contactMap = getDashboardContact(); // 입점/제휴 건수
		Map<String, Integer> bulkBuyMap = getDashboardBulkBuy(); // 단체주문 건수

		for (int i = 0; i < 3; i++) {
			BoDashboardVO etcListVo = new BoDashboardVO();
			// 전체(0), 통합몰(1), OTS(2)
			if (i == 0) {
				// 미처리 답변 현황
				etcListVo.setTabSeq(i);
				etcListVo.setContactCnt(contactMap.get("allCnt"));
				etcListVo.setBulkBuyCnt(bulkBuyMap.get("allCnt"));

				etcList.add(etcListVo);
			} else if (i == 1) {
				etcListVo.setTabSeq(i);
				etcListVo.setContactCnt(contactMap.get("artCnt"));
				etcListVo.setBulkBuyCnt(bulkBuyMap.get("artCnt"));

				etcList.add(etcListVo);
			} else if (i == 2) {
				etcListVo.setTabSeq(i);
				etcListVo.setContactCnt(contactMap.get("otsCnt"));
				etcListVo.setBulkBuyCnt(bulkBuyMap.get("otsCnt"));

				etcList.add(etcListVo);
			}
		}

		// tabSeq로 정렬 후 return
		return etcList.stream().sorted(Comparator.comparing(BoDashboardVO::getTabSeq)).collect(Collectors.toList());
	}

	/**
	 * @Desc : BO 대시보드 주문/배송 개수 조회
	 * @Method Name : getDashboardOrderDlvyCount
	 * @Date : 2019. 10. 14.
	 * @Author : 최경호
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDashboardOrderDlvyCount() throws Exception {
		return orderService.getDashboardOrderDlvyCount();
	}

	/**
	 * @Desc : BO 대시보드 1:1문의 미답변 건수조회
	 * @Method Name : getDashboardInquiry
	 * @Date : 2019. 4. 30.
	 * @Author : 고웅환
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getDashboardInquiry() throws Exception {
		Map<String, Integer> map = new HashMap<>();
		int abcCnt = 0;
		int vndrCnt = 0;

		BdMemberCounsel bdMemberCounsel = new BdMemberCounsel();
		bdMemberCounsel.setCnslGbnCode(CommonCode.CNSL_GBN_CODE_INQUIRY);
		bdMemberCounsel.setAswrStatCode(CommonCode.ASWR_STAT_CODE_UN);

		// 미답변이면서 VNDR_ASSIGN_YN(이관여부)로 구분, 자사(N), 입점(Y)
		for (BdMemberCounsel inquiryList : bdInquiryService.getInquiryGroupCount(bdMemberCounsel)) {
			if (UtilsText.equals(Const.BOOLEAN_FALSE, inquiryList.getVndrAssignYn())) {
				abcCnt = inquiryList.getTotalCnt();
			} else if (UtilsText.equals(Const.BOOLEAN_TRUE, inquiryList.getVndrAssignYn())) {
				vndrCnt = inquiryList.getTotalCnt();
			}
		}
		map.put("allCnt", abcCnt + vndrCnt);
		map.put("abcCnt", abcCnt);
		map.put("vndrCnt", vndrCnt);

		return map;
	}

	/**
	 * @Desc : BO 대시보드 협력게시판 미답변 건수조회
	 * @Method Name : getDashboardCowork
	 * @Date : 2019. 4. 30.
	 * @Author : 고웅환
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getDashboardCowork() throws Exception {
		Map<String, Integer> map = new HashMap<>();
		int abcCnt = 0;
		int vndrCnt = 0;

		// VNDR_INQRY_YN(업체문의여부)로 구분
		for (BdCorprBoard coworkList : vendorService.getCoworkGroupCount(CommonCode.ASWR_STAT_CODE_UN)) {
			if (UtilsText.equals(Const.BOOLEAN_FALSE, coworkList.getVndrInqryYn())) {
				abcCnt = coworkList.getTotalCnt();
			} else if (UtilsText.equals(Const.BOOLEAN_TRUE, coworkList.getVndrInqryYn())) {
				vndrCnt = coworkList.getTotalCnt();
			}
		}
		map.put("allCnt", abcCnt + vndrCnt);
		map.put("abcCnt", abcCnt);
		map.put("vndrCnt", vndrCnt);

		return map;
	}

	/**
	 * @Desc : BO 대시보드 입점/제휴 건수조회
	 * @Method Name : getDashboardCowork
	 * @Date : 2019. 5. 8.
	 * @Author : 고웅환
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getDashboardContact() throws Exception {
		Map<String, Integer> map = new HashMap<>();
		int artCnt = 0;
		int otsCnt = 0;

		// 입점/제휴 최신 7일기준 건수 조회, 사이트로 구분 => 통합몰(10000), OTS(10001)
		for (BdContactUs contactList : vendorService.getContactGroupCount()) {
			if (UtilsText.equals(Const.SITE_NO_ART, contactList.getSiteNo())) {
				artCnt = contactList.getTotalCnt();

			} else if (UtilsText.equals(Const.SITE_NO_OTS, contactList.getSiteNo())) {
				otsCnt = contactList.getTotalCnt();
			}
		}
		map.put("allCnt", artCnt + otsCnt);
		map.put("artCnt", artCnt);
		map.put("otsCnt", otsCnt);

		return map;
	}

	/**
	 * @Desc : BO 대시보드 단체주문 건수조회
	 * @Method Name : getDashboardBulkBuy
	 * @Date : 2019. 5. 8.
	 * @Author : 고웅환
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getDashboardBulkBuy() throws Exception {
		Map<String, Integer> map = new HashMap<>();
		int artCnt = 0;
		int otsCnt = 0;

		// 단체주문 최신 7일기준 건수 조회, 사이트로 구분 => 통합몰(10000), OTS(10001)
		for (BdBulkBuyInquiry bulkBuyList : bdBulkBuyInquiryService.getBulkBuyGroupCount()) {
			if (UtilsText.equals(Const.SITE_NO_ART, bulkBuyList.getSiteNo())) {
				artCnt = bulkBuyList.getTotalCnt();

			} else if (UtilsText.equals(Const.SITE_NO_OTS, bulkBuyList.getSiteNo())) {
				otsCnt = bulkBuyList.getTotalCnt();
			}
		}
		map.put("allCnt", artCnt + otsCnt);
		map.put("artCnt", artCnt);
		map.put("otsCnt", otsCnt);

		return map;
	}

	/**
	 * @Desc : BO 대시보드 공지사항(관리자)
	 * @Method Name : getDashboardAdminNotice
	 * @Date : 2019. 4. 30.
	 * @Author : 고웅환
	 * @param
	 * @return
	 * @throws Exception
	 */
	public List<BdAdminNotice> getDashboardAdminNotice(Parameter<?> parameter) throws Exception {
		List<BdAdminNotice> adminNoticeList = new ArrayList<>();
		Pageable<BdAdminNotice, BdAdminNotice> adminNtcVOPageble = new Pageable<>(parameter, BdAdminNotice.class);
		// dashboard는 5개만 가져오면 되기 때문에...
		adminNtcVOPageble.setRowsPerPage(5);

		Page<BdAdminNotice> adminNoticePage = adminNoticeService.getAdminNoticeList(adminNtcVOPageble);
		adminNoticeList = adminNoticePage.getContent();

		return adminNoticeList;
	}

	/**
	 * @Desc : BO 대시보드 회원현황
	 * @Method Name : getDashboardMemberStatus
	 * @Date : 2019. 9. 4.
	 * @Author : 고웅환
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getDashboardMemberStatus() throws Exception {
		Map<String, Integer> map = new HashMap<>();
		int memberShip = 0;
		int online = 0;
		int mbshpCnvrt = 0;

		for (MbMember member : memberService.getMemberStatusCount()) {
			if (UtilsText.equals(CommonCode.MEMBER_TYPE_CODE_MEMBERSHIP, member.getMemberTypeCode())) {
				memberShip = member.getStatusCnt();
			} else if (UtilsText.equals(CommonCode.MEMBER_TYPE_CODE_ONLINE, member.getMemberTypeCode())) {
				online = member.getStatusCnt();
			} else if (UtilsText.equals("99999", member.getMemberTypeCode())) {
				mbshpCnvrt = member.getStatusCnt();
			}
		}

		map.put("memberShip", memberShip);
		map.put("online", online);
		map.put("mbshpCnvrt", mbshpCnvrt);

		return map;
	}

	/**
	 * @Desc : BO 대시보드 새로고침
	 * @Method Name : getDashboardRefresh
	 * @Date : 2019. 4. 29.
	 * @Author : 고웅환
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDashboardRefresh(Parameter<?> parameter) throws Exception {
		Map<String, Object> map = new HashMap<>();

		if (!UtilsText.isBlank(parameter.getString("refresh"))) {

			switch (parameter.getString("refresh")) {
			// 미처리 답변현황 새로고침
			case "unAswrRefresh":
				map.put("refreshList", makeUnAswrList());
				break;
			// 기획전 현황 새로고침
			case "plndpRefresh":
				map.put("plndpList", getplanningDisplayCount());
				break;
			// 상품 현황 새로고침
			case "orderRefresh":
				map.put("orderDlvyCountInfo", getDashboardOrderDlvyCount());
				break;
			// 상품 현황 새로고침
			case "productRefresh":
				map.put("productList", getDashboardProductCount());
				break;

			default:
				map.put("message", "새로고침에 맞는 값이 없습니다.");
			}

		} else {
			map.put("message", "새로고침 값이 없습니다.");
		}

		return map;
	}

	/**
	 * @Desc : 미처리 상품 후기 갯수 조회
	 * @Method Name : getDashboardReview
	 * @Date : 2019. 5. 10.
	 * @Author : hsjhsj19
	 * @return
	 * @throws Exception
	 */
	private Map<String, Integer> getDashboardReview() throws Exception {
		Map<String, Integer> rMap = new HashMap<String, Integer>();
		List<Map<String, Object>> list = this.reviewService.getReviewGroupCount();
		int abcReviewCount = 0;
		int vndrReviewCount = 0;
		for (Map<String, Object> map : list) {
			if ("Y".equals(map.get("mmny_prdt_yn"))) { // 자사
				abcReviewCount = UtilsNumber.toInt(map.get("count") + "");
			} else { // 입점
				vndrReviewCount = UtilsNumber.toInt(map.get("count") + "");
			}
		}
		rMap.put("abcReviewCount", abcReviewCount);
		rMap.put("vndrReviewCount", vndrReviewCount);
		rMap.put("allReviewCount", abcReviewCount + vndrReviewCount);
		return rMap;
	}

	/**
	 * @Desc : 미처리 상품 문의 갯수 조회
	 * @Method Name : getDashboardProductInquiry
	 * @Date : 2019. 5. 10.
	 * @Author : hsjhsj19
	 * @return
	 * @throws Exception
	 */
	private Map<String, Integer> getDashboardProductInquiry() throws Exception {
		Map<String, Integer> rMap = new HashMap<String, Integer>();
		List<Map<String, Object>> list = this.productInquiryService.getProductInquiryGroupCount();
		int abcProductInquiryCount = 0;
		int vndrProductInquiryCount = 0;
		for (Map<String, Object> map : list) {
			if ("Y".equals(map.get("mmny_prdt_yn"))) { // 자사
				abcProductInquiryCount = UtilsNumber.toInt(map.get("count") + "");
			} else { // 입점
				vndrProductInquiryCount = UtilsNumber.toInt(map.get("count") + "");
			}
		}
		rMap.put("abcProductInquiryCount", abcProductInquiryCount);
		rMap.put("vndrProductInquiryCount", vndrProductInquiryCount);
		rMap.put("allProductInquiryCount", abcProductInquiryCount + vndrProductInquiryCount);

		return rMap;
	}

	/**
	 * @Desc : BO 대시보드 고객의소리 미답변 건수조회
	 * @Method Name : getDashboardVoiceCustom
	 * @Date : 2019. 5. 16.
	 * @Author : 이재렬
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getDashboardVoiceCustom() throws Exception {
		Map<String, Integer> map = new HashMap<>();
		int vndrCnt = 0;

		BdMemberCounsel bdMemberCounsel = new BdMemberCounsel();
		bdMemberCounsel.setCnslGbnCode(CommonCode.CNSL_GBN_CODE_VOC);
		bdMemberCounsel.setAswrStatCode(CommonCode.ASWR_STAT_CODE_UN);

		vndrCnt = bdInquiryService.getNoAswrVoiceCustom(bdMemberCounsel);
		map.put("allCnt", vndrCnt);
		map.put("abcCnt", vndrCnt);

		return map;
	}

	/**
	 * @Desc : BO 대시보드 상품정보 건수조회
	 * @Method Name : getDashboardProductCount
	 * @Date : 2019. 6. 5.
	 * @Author : 김영진
	 * @param
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<BdDashboardProduct>> getDashboardProductCount() throws Exception {
		Map<String, List<BdDashboardProduct>> rMap = new HashMap<String, List<BdDashboardProduct>>();
		List<BdDashboardProduct> all = new ArrayList<BdDashboardProduct>();
		List<BdDashboardProduct> my = new ArrayList<BdDashboardProduct>();
		List<BdDashboardProduct> company = new ArrayList<BdDashboardProduct>();

		List<BdDashboardProduct> prdtStatCount = productService.selectProductDashBoardCount();

		String[] subTab = { "a-rt", "abc", "gs", "ots" };
		String[] cpyTab = { "a-rt", "ots" };

		// 전체
		for (String sTab : subTab) {
			BdDashboardProduct allVal = new BdDashboardProduct();
			boolean chk = true;
			for (BdDashboardProduct prdtCnt : prdtStatCount) {
				if ("all".equals(prdtCnt.getTabCode()) && sTab.equals(prdtCnt.getSiteName())) {
					allVal.setTabCode(prdtCnt.getTabCode());
					allVal.setSiteName(prdtCnt.getSiteName());
					allVal.setDeps1(prdtCnt.getDeps1());
					allVal.setDeps2(prdtCnt.getDeps2());
					allVal.setDeps3(prdtCnt.getDeps3());
					allVal.setDeps4(prdtCnt.getDeps4());
					chk = false;
				}
			}
			if (chk == true) {
				allVal.setTabCode("all");
				allVal.setSiteName(sTab);
				allVal.setDeps1(0);
				allVal.setDeps2(0);
				allVal.setDeps3(0);
				allVal.setDeps4(0);
			}
			all.add(allVal);
		}

		// 자사
		for (String sTab : subTab) {
			BdDashboardProduct myVal = new BdDashboardProduct();
			boolean chk = true;
			for (BdDashboardProduct prdtCnt : prdtStatCount) {
				if ("my".equals(prdtCnt.getTabCode()) && sTab.equals(prdtCnt.getSiteName())) {
					myVal.setTabCode(prdtCnt.getTabCode());
					myVal.setSiteName(prdtCnt.getSiteName());
					myVal.setDeps1(prdtCnt.getDeps1());
					myVal.setDeps2(prdtCnt.getDeps2());
					myVal.setDeps3(prdtCnt.getDeps3());
					myVal.setDeps4(prdtCnt.getDeps4());
					chk = false;
				}
			}
			if (chk == true) {
				myVal.setTabCode("my");
				myVal.setSiteName(sTab);
				myVal.setDeps1(0);
				myVal.setDeps2(0);
				myVal.setDeps3(0);
				myVal.setDeps4(0);
			}
			my.add(myVal);
		}

		// 입점
		for (String cTab : cpyTab) {
			BdDashboardProduct companyVal = new BdDashboardProduct();
			boolean chk = true;
			for (BdDashboardProduct prdtCnt : prdtStatCount) {
				if ("company".equals(prdtCnt.getTabCode()) && cTab.equals(prdtCnt.getSiteName())) {
					companyVal.setTabCode(prdtCnt.getTabCode());
					companyVal.setSiteName(prdtCnt.getSiteName());
					companyVal.setDeps1(prdtCnt.getDeps1());
					companyVal.setDeps2(prdtCnt.getDeps2());
					companyVal.setDeps3(prdtCnt.getDeps3());
					companyVal.setDeps4(prdtCnt.getDeps4());
					chk = false;
				}
			}
			if (chk == true) {
				companyVal.setTabCode("company");
				companyVal.setSiteName(cTab);
				companyVal.setDeps1(0);
				companyVal.setDeps2(0);
				companyVal.setDeps3(0);
				companyVal.setDeps4(0);
			}
			company.add(companyVal);
		}

		// 전체구하기
		BdDashboardProduct allTotal = new BdDashboardProduct();
		allTotal.setTabCode("all");
		allTotal.setSiteName("all");
		for (BdDashboardProduct allTemp : all) {
			allTotal.setDeps1(allTotal.getDeps1() + allTemp.getDeps1());
			allTotal.setDeps2(allTotal.getDeps2() + allTemp.getDeps2());
			allTotal.setDeps3(allTotal.getDeps3() + allTemp.getDeps3());
			allTotal.setDeps4(allTotal.getDeps4() + allTemp.getDeps4());
		}
		all.add(allTotal);

		// 자사구하기
		BdDashboardProduct myTotal = new BdDashboardProduct();
		myTotal.setTabCode("my");
		myTotal.setSiteName("all");
		for (BdDashboardProduct myTemp : my) {
			myTotal.setDeps1(myTotal.getDeps1() + myTemp.getDeps1());
			myTotal.setDeps2(myTotal.getDeps2() + myTemp.getDeps2());
			myTotal.setDeps3(myTotal.getDeps3() + myTemp.getDeps3());
			myTotal.setDeps4(myTotal.getDeps4() + myTemp.getDeps4());
		}
		my.add(myTotal);

		// 입점구하기
		BdDashboardProduct companyTotal = new BdDashboardProduct();
		companyTotal.setTabCode("company");
		companyTotal.setSiteName("all");
		for (BdDashboardProduct companyTemp : company) {
			companyTotal.setDeps1(companyTotal.getDeps1() + companyTemp.getDeps1());
			companyTotal.setDeps2(companyTotal.getDeps2() + companyTemp.getDeps2());
			companyTotal.setDeps3(companyTotal.getDeps3() + companyTemp.getDeps3());
			companyTotal.setDeps4(companyTotal.getDeps4() + companyTemp.getDeps4());
		}
		company.add(companyTotal);

		log.debug("all > {}", all);
		log.debug("my > {}", my);
		log.debug("company > {}", company);

		rMap.put("all", all);
		rMap.put("my", my);
		rMap.put("company", company);

		return rMap;

	}

	/**
	 * @Desc : BO 대쉬보드 - 클레임 건수 조회
	 * @Method Name : getClaimCountInfo
	 * @Date : 2019. 7. 23.
	 * @Author : 이강수
	 * @param OcClaimCountVO
	 * @return OcClaimCountVO
	 * @throws Exception
	 */
	public OcClaimCountVO getClaimCountInfo() throws Exception {

		return claimService.getClaimCountInfo();
	}

	/**
	 * @Desc : PO 대시보드 기획전 현황 .
	 * @Method Name : getplanningDisplayCount
	 * @Date : 2019.6.7.
	 * @Author : 김영진
	 * @param parameter
	 * @throws Exception
	 */
	public Map<String, Integer> getplanningDisplayCount() throws Exception {
		Map<String, Integer> planningMap = new HashMap<String, Integer>();
		List<Map<String, Object>> List = this.planningDisplayService.getProductInquiryGroupCount();
		int p = 0;
		int r = 0;
		int d = 0;
		for (Map<String, Object> map : List) {
			if ("P".equals(map.get("gbn"))) {
				p = UtilsNumber.toInt(map.get("count") + "");
			} else if ("R".equals(map.get("gbn"))) {
				r = UtilsNumber.toInt(map.get("count") + "");
			} else {
				d = UtilsNumber.toInt(map.get("count") + "");
			}
		}
		planningMap.put("useplanning", p);
		planningMap.put("usereadyplanning", r);
		planningMap.put("notuseplanning", d);

		return planningMap;
	}

	/**
	 * 진행중인 기획전
	 *
	 * @return
	 * @throws Exception
	 */
	public Integer getplanningDisplay() throws Exception {
		Map<String, Integer> planinngMap = this.getplanningDisplayCount();
		int useplanning = planinngMap.get("useplanning");
		return useplanning;
	}

	/**
	 * 승인 요청중인 기획전
	 *
	 * @return
	 * @throws Exception
	 */
	public Integer getplanningDisplayusereadyplanning() throws Exception {
		Map<String, Integer> planinngMap = this.getplanningDisplayCount();
		int usereadyplanning = planinngMap.get("usereadyplanning");
		return usereadyplanning;
	}

	/**
	 * 승인 반려 기획전
	 *
	 * @return
	 * @throws Exception
	 */
	public Integer getplanningDisplaynotuseplanning() throws Exception {
		Map<String, Integer> planinngMap = this.getplanningDisplayCount();
		int notuseplanning = planinngMap.get("notuseplanning");
		return notuseplanning;
	}

	/**
	 * @Desc : 주문건수 판매현황 조회
	 * @Method Name : getDashboardOrderCntAmt
	 * @Date : 2019. 10. 11.
	 * @Author : 신인철
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDashboardOrderCntAmt() throws Exception {
		Map<String, Object> orderCntAmtMap = new HashMap<String, Object>();

		List<OcOrder> orderCntAmtList = orderService.getOrderCntAmtList(new OcOrder());
		for (OcOrder order : orderCntAmtList) {
			if (UtilsText.equals(order.getMmnyPrdtYn(), Const.BOOLEAN_TRUE)) {
				orderCntAmtMap.put("localOrderCntAmt", order);
			} else {
				orderCntAmtMap.put("vendorOrderCntAmt", order);
			}

		}
		return orderCntAmtMap;
	}

	/**
	 * @Desc : 베스트 입점사 조회
	 * @Method Name : getDashBoardBestVndr
	 * @Date : 2019. 10. 14.
	 * @Author : sic
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDashBoardBestVndr() throws Exception {
		Map<String, Object> bestVndrListMap = new HashMap<String, Object>();
		List<OcOrder> bestVndrList = orderService.getBestVndrList();
		bestVndrListMap.put("vndrOrderCnt",
				bestVndrList.stream().filter(o -> o.getOrderType().equals(Const.DASH_ORDER_CNT))
						.sorted(Comparator.comparingLong(OcOrder::getOrderCnt).reversed())
						.collect(Collectors.toList()));
		bestVndrListMap.put("vndrOrderAmt",
				bestVndrList.stream().filter(o -> o.getOrderType().equals(Const.DASH_ORDER_AMT))
						.sorted(Comparator.comparingLong(OcOrder::getOrderCnt).reversed())
						.collect(Collectors.toList()));

		return bestVndrListMap;
	}

	/**
	 * @Desc : 관리대상 입점사 조회
	 * @Method Name : getDashBoardManagementVndr
	 * @Date : 2019. 10. 14.
	 * @Author : sic
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDashBoardManagementVndr() throws Exception {
		Map<String, Object> rsMap = new HashMap<>();
		List<BdMemberCounsel> unAswrVndrList = inquiryService.getManagementVndrList();
		List<Map<String, Object>> delayedDeliveryVndrList = orderService.getDelayedDeliveryVndrList();

		rsMap.put("unAswrVndrList", unAswrVndrList); // 미처리 건수
		rsMap.put("delayedDeliveryVndrList", delayedDeliveryVndrList); // 업체의 배송지연 건수 TOP5

		return rsMap;
	}

}
