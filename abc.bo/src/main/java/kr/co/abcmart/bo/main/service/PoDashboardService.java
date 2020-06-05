package kr.co.abcmart.bo.main.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.board.model.master.BdAdminNotice;
import kr.co.abcmart.bo.board.model.master.BdCorprBoard;
import kr.co.abcmart.bo.board.model.master.BdMemberCounsel;
import kr.co.abcmart.bo.board.service.AdminNoticeService;
import kr.co.abcmart.bo.board.service.BdInquiryService;
import kr.co.abcmart.bo.delivery.model.master.OcDelivery;
import kr.co.abcmart.bo.delivery.service.DeliveryService;
import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.bo.order.service.OrderService;
import kr.co.abcmart.bo.product.model.master.BdDashboardProduct;
import kr.co.abcmart.bo.product.service.ProductInquiryService;
import kr.co.abcmart.bo.product.service.ProductService;
import kr.co.abcmart.bo.promotion.service.PlanningDisplayService;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsNumber;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;

@Service
public class PoDashboardService {
	@Autowired
	private VendorService vendorService;
	@Autowired
	private BdInquiryService bdInquiryService;
	@Autowired
	private AdminNoticeService adminNoticeService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductInquiryService productInquiryService;
	@Autowired
	private PlanningDisplayService planningDisplayService;
	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private OrderService orderService;

	/**
	 * @Desc : po 대시보드 데이터 bind
	 * @Method Name : poDashboardData
	 * @Date : 2019. 6. 12.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	public Map<String, Object> poDashboardData(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		VdVendor vdVendor = new VdVendor();

		vdVendor.setVndrNo(LoginManager.getUserDetails().getVndrNo());
		vdVendor = vendorService.getVendorBaseInfo(vdVendor);

		resultMap.put("vndrNo", vdVendor.getVndrNo());
		resultMap.put("vndrName", vdVendor.getVndrName());

		// 대시보드 기간 설정
		resultMap.put("nowDtm", Timestamp.valueOf(LocalDateTime.now()));
		resultMap.put("oneMonthAgoDtm", Timestamp.valueOf(LocalDateTime.now().minusMonths(1)));
		resultMap.put("oneDayAgoDtm", Timestamp.valueOf(LocalDateTime.now().minusDays(1)));

		resultMap.put("coworkCount", getPoDashboardCoworkCount(parameter));
		resultMap.put("inquiryCount", getPoDashboardInquiryCount());
		resultMap.put("orderDlvyCountInfo", getDashboardOrderDlvyCount()); // 대시보드 주문/배송 현황
		resultMap.put("vndrProductInquiryCount", getPoDashboardProductQna());
		resultMap.put("useplanning", getplanningDisplay());
		resultMap.put("usereadyplanning", getplanningDisplayusereadyplanning());
		resultMap.put("notuseplanning", getplanningDisplaynotuseplanning());
		resultMap.put("productsell", getProductSellCount());
		resultMap.put("productsold", getProductSoldCount());
		resultMap.put("productAprv", getProductAprvCount());
		resultMap.put("productDenyAprv", getProductDenyAprvCount());
		resultMap.put("adNoticeList", getPoDashboardAdNoticeBoardTopFive());
		resultMap.put("adminNoticeMainPop", getMainPopAdminNotice(parameter));

		// 클레임
		OcDelivery ocDelivery = new OcDelivery();
		ocDelivery.setVndrNo(vdVendor.getVndrNo());
		ocDelivery.setFromDashYn(Const.BOOLEAN_TRUE);
		Map<String, Object> claimMap = deliveryService.getDeliveryOrderVendorClaim(ocDelivery);
		resultMap.put("orderPrdtStatCodeClaim", claimMap.get("orderPrdtStatCodeClaim"));
		resultMap.put("orderPrdtStatCodeDeliveryReturn", claimMap.get("orderPrdtStatCodeDeliveryReturn"));
		resultMap.put("orderPrdtStatCodeExchangeDelivery", claimMap.get("orderPrdtStatCodeExchangeDelivery"));
		resultMap.put("orderPrdtStatCodeClaimComplete", claimMap.get("orderPrdtStatCodeClaimComplete"));

		// 판매현황 (주문건수, 주문금액)
		OcOrder ocOrder = getPoOrderCntAmt(vdVendor);
		if (ocOrder.getOrderCnt() == null) {
			ocOrder.setOrderCnt(0L);
			ocOrder.setOrderAmt((long) 0);
		}
		resultMap.put("poOrderCntAmt", ocOrder);

		return resultMap;
	}

	/**
	 * @Desc : PO 대시보드 주문/배송 개수 조회
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
	 * @Desc : 관리자 공지 메인팝업 대상을 조회하낟. 최대 3개
	 * @Method Name : getMainPopAdminNotice
	 * @Date : 2019. 6. 13.
	 * @Author : Kimyounghyun
	 * @param parameter
	 * @return
	 */
	public String[] getMainPopAdminNotice(Parameter<?> parameter) {
		String vndrNo = LoginManager.getUserDetails().getVndrNo();

		return adminNoticeService.getPoMainPopAdminNotice(vndrNo);
	}

	/**
	 * @Desc : PO 대시보드 협력게시판 미처리 현황 건수 조회.
	 * @Method Name : getPoDashboardCoworkCount
	 * @Date : 2019.4.29.
	 * @Author : 이재렬
	 * @param
	 * @throws Exception
	 */
	public int getPoDashboardCoworkCount(Parameter<?> parameter) throws Exception {
		UserDetails userDetails = LoginManager.getUserDetails();
		BdCorprBoard bdCorprBoard = new BdCorprBoard();
		int count = 0;

		if (UtilsText.equals(userDetails.getUpAuthNo(), Const.ROLE_VENDER_GROUP)) {
			bdCorprBoard.setAswrStatCode(CommonCode.ASWR_STAT_CODE_UN);
			bdCorprBoard.setVndrNo(userDetails.getVndrNo());

			count = vendorService.getCoworkGroupCountPo(bdCorprBoard);
		}

		return count;
	}

	/**
	 * @Desc : PO 대시보드 1:1문의 게시판 미처리 현황 건수 조회.
	 * @Method Name : getPoDashboardInquiryCount
	 * @Date : 2019.4.29.
	 * @Author : 이재렬
	 * @param
	 * @throws Exception
	 */
	public int getPoDashboardInquiryCount() throws Exception {
		// BO에서 이관되면 답변보류로 넘어오기때문에 PO에서는 답변보류 상태를 미처리로 한다
		BdMemberCounsel bdMemberCounsel = new BdMemberCounsel();
		bdMemberCounsel.setCnslGbnCode(CommonCode.CNSL_GBN_CODE_INQUIRY);
		bdMemberCounsel.setAswrStatCode(CommonCode.ASWR_STAT_CODE_HD);
		bdMemberCounsel.setVndrNo(LoginManager.getUserDetails().getVndrNo());

		return bdInquiryService.getNoAswrCounselListOfPO(bdMemberCounsel);
	}

	/**
	 * @Desc : PO 대시보드 관리자 공지 게시판 top 5.
	 * @Method Name : getPoDashboardAdNoticeBoard
	 * @Date : 2019.4.29.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	public List<BdAdminNotice> getPoDashboardAdNoticeBoardTopFive() throws Exception {
		UserDetails userDetails = LoginManager.getUserDetails();

		List<BdAdminNotice> adNoticeList = adminNoticeService.selectAdminNoticeForPoDashboardTopFive(null,
				userDetails.getVndrNo());

		return adNoticeList;
	}

	/**
	 * @Desc : PO 대시보드 미처리 문의현황 상품QnA.
	 * @Method Name : getPoDashboardProductQna
	 * @Date : 2019.6.7.
	 * @Author : 김영진
	 * @param parameter
	 * @throws Exception
	 */
	public Integer getPoDashboardProductQna() throws Exception {
		Map<String, Integer> rMap = new HashMap<String, Integer>();
		List<Map<String, Object>> list = this.productInquiryService.getProductInquiryGroupCount();
		int vndrProductInquiryCount = 0;
		for (Map<String, Object> map : list) {
			if ("N".equals(map.get("mmny_prdt_yn"))) {
				vndrProductInquiryCount = UtilsNumber.toInt(map.get("count") + "");
			}
		}
		rMap.put("vndrProductInquiryCount", vndrProductInquiryCount);
		int productCount = rMap.get("vndrProductInquiryCount");

		return productCount;
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

	public Map<String, List<BdDashboardProduct>> getProductDashboardCount() throws Exception {
		Map<String, List<BdDashboardProduct>> rMap = new HashMap<String, List<BdDashboardProduct>>();
		List<BdDashboardProduct> company = new ArrayList<BdDashboardProduct>();
		List<BdDashboardProduct> prdtStatCount = productService.selectProductPoDashBoardCount();
		String[] cpyTab = { "a-rt", "ots" };
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

		rMap.put("company", company);

		return rMap;
	}

	/**
	 * 판매/전시중 상품
	 *
	 * @return
	 * @throws Exception
	 */
	public Integer getProductSellCount() throws Exception {
		Map<String, List<BdDashboardProduct>> rMap = this.getProductDashboardCount();
		List<BdDashboardProduct> prdtCnt = new ArrayList<BdDashboardProduct>();
		Map<String, Integer> tMap = new HashMap<String, Integer>();
		for (BdDashboardProduct t : rMap.get("company")) {
			t.getDeps1();
			tMap.put("sellCount", t.getDeps1());
		}

		int productsell = tMap.get("sellCount");

		return productsell;
	}

	/**
	 * 일시품절 상품
	 *
	 * @return
	 * @throws Exception
	 */
	public Integer getProductSoldCount() throws Exception {
		Map<String, List<BdDashboardProduct>> rMap = this.getProductDashboardCount();
		List<BdDashboardProduct> prdtCnt = new ArrayList<BdDashboardProduct>();
		Map<String, Integer> tMap = new HashMap<String, Integer>();
		for (BdDashboardProduct t : rMap.get("company")) {
			t.getDeps1();
			tMap.put("soldCount", t.getDeps2());
		}

		int productsold = tMap.get("soldCount");

		return productsold;

	}

	/**
	 * 승인대기 상품
	 *
	 * @return
	 * @throws Exception
	 */
	public Integer getProductAprvCount() throws Exception {
		Map<String, List<BdDashboardProduct>> rMap = this.getProductDashboardCount();
		List<BdDashboardProduct> prdtCnt = new ArrayList<BdDashboardProduct>();
		Map<String, Integer> tMap = new HashMap<String, Integer>();
		for (BdDashboardProduct t : rMap.get("company")) {
			t.getDeps1();
			tMap.put("AprvCount", t.getDeps3());
		}

		int productAprv = tMap.get("AprvCount");

		return productAprv;

	}

	/**
	 * 승인반려 상품
	 *
	 * @return
	 * @throws Exception
	 */
	public Integer getProductDenyAprvCount() throws Exception {
		Map<String, List<BdDashboardProduct>> rMap = this.getProductDashboardCount();
		List<BdDashboardProduct> prdtCnt = new ArrayList<BdDashboardProduct>();
		Map<String, Integer> tMap = new HashMap<String, Integer>();
		for (BdDashboardProduct t : rMap.get("company")) {
			t.getDeps1();
			tMap.put("DenyAprvCount", t.getDeps3());
		}

		int productDenyAprv = tMap.get("DenyAprvCount");

		return productDenyAprv;

	}

	/**
	 * @Desc : 판매현황 주문건수, 금액 조회
	 * @Method Name : getPoOrderCntAmt
	 * @Date : 2019. 10. 11.
	 * @Author : sic
	 * @param vdVendor
	 * @return
	 * @throws Exception
	 */
	public OcOrder getPoOrderCntAmt(VdVendor vdVendor) throws Exception {
		OcOrder ocOrder = new OcOrder();
		ocOrder.setVndrNo(vdVendor.getVndrNo());
		ocOrder.setMmnyPrdtYn(Const.BOOLEAN_FALSE);
		List<OcOrder> orderCntAmtList = orderService.getOrderCntAmtList(ocOrder);

		return orderCntAmtList.stream().findFirst().orElseGet(OcOrder::new);
	}

}
