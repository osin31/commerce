package kr.co.abcmart.bo.settlement.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.settlement.service.PgService;
import kr.co.abcmart.bo.settlement.vo.RvGiftCardComparision;
import kr.co.abcmart.bo.settlement.vo.RvKakaoComparision;
import kr.co.abcmart.bo.settlement.vo.RvKcpComparision;
import kr.co.abcmart.bo.settlement.vo.RvNaverComparision;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("pg")
public class PgController extends BaseController {
	@Autowired
	private SiteService siteService;
	@Autowired
	private PgService pgService;

	@RequestMapping("/pg-main")
	public ModelAndView PgMain(Parameter<?> parameter) throws Exception {

		parameter.addAttribute("siteNo", siteService.getSiteList());
		return forward("/settlement/pg-main");
	}

	/**
	 * @Desc : kakao 대사관리 페이지 호출
	 * @Method Name : PgKakaoMain
	 * @Date : 2019. 11. 8.
	 * @Author : sic
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/kakao")
	public ModelAndView PgKakaoMain(Parameter<?> parameter) throws Exception {

		parameter.addAttribute("siteNo", siteService.getSiteList());
		return forward("/settlement/pg-kakao-main");
	}

	/**
	 * @Desc : kakao 대사관리 리스트 조회
	 * @Method Name : pgKakaoList
	 * @Date : 2019. 11. 8.
	 * @Author : sic
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/kakao/pg-kakao-list")
	public void pgKakaoList(Parameter<RvKakaoComparision> parameter) throws Exception {
		Pageable<RvKakaoComparision, RvKakaoComparision> pageable = new Pageable<>(parameter);
		Page<RvKakaoComparision> page = pgService.getKakaoList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 카카오 대사관리 엑셀 다운로드
	 * @Method Name : kakaoListExcel
	 * @Date : 2019. 11. 8.
	 * @Author : sic
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/kakao/kakao-list-excel")
	public void kakaoListExcel(Parameter<RvKakaoComparision> parameter) throws Exception {
		List<RvKakaoComparision> list = pgService.getKakaoExcelLIst(parameter.get());
		ExcelValue excelValue = ExcelValue.builder(2, 0)
				.columnNames(Arrays.asList("tid", "aid", "cid", "status", "paymentMethodType", "itemName", "itemCode",
						"installMonth", "cardCorpName", "interestFreeInstall", "approvedAt", "amount",
						"paymentActionType", "orgOrderNo", "mapngYn", "sendParmDate"))
				.data(list).build();

		parameter.downloadExcelTemplate("settlement/excel/pgKakaoList", excelValue);

	}

	/**
	 * @Desc : 카카오 정산내역 부분 조회
	 * @Method Name : setKakaoSettlementHistory
	 * @Date : 2019. 11. 8.
	 * @Author : sic
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/kakao/setSettlementHistory")
	public void setKakaoSettlementHistory(Parameter<RvKakaoComparision> parameter) throws Exception {
		RvKakaoComparision rvKakaoComparision = pgService.getKakaoSettlementHistory(parameter.get());

		writeJson(parameter, rvKakaoComparision);
	}

	/**
	 * @Desc : 네이버 대사관리 페이지 호출
	 * @Method Name : PgNaverMain
	 * @Date : 2019. 11. 8.
	 * @Author : sic
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/naver")
	public ModelAndView PgNaverMain(Parameter<?> parameter) throws Exception {

		parameter.addAttribute("siteNo", siteService.getSiteList());
		return forward("/settlement/pg-naver-main");
	}

	/**
	 * @Desc : 네이버 대사관리 리스트 조회
	 * @Method Name : pgNaverList
	 * @Date : 2019. 11. 8.
	 * @Author : sic
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/naver/pg-naver-list")
	public void pgNaverList(Parameter<RvNaverComparision> parameter) throws Exception {
		Pageable<RvNaverComparision, RvNaverComparision> pageable = new Pageable<>(parameter);
		Page<RvNaverComparision> page = pgService.getNaverList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 네이버 대사관리 엑셀 다운로드
	 * @Method Name : naverListExcel
	 * @Date : 2019. 11. 8.
	 * @Author : sic
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/naver/naver-list-excel")
	public void naverListExcel(Parameter<RvNaverComparision> parameter) throws Exception {
		List<RvNaverComparision> list = pgService.getNaverExcelLIst(parameter.get());
		ExcelValue excelValue = ExcelValue.builder(2, 0)
				.columnNames(Arrays.asList("payHistId", "payMentId", "admissionTypeCode", "merchantId", "admissionYmdt",
						"totalPayAmount", "admissionState", "primaryCommissionAmount", "primarySettleAmount",
						"totalSettleAmount", "totalCommissionAmount", "orgOrderNo", "mapngYn", "sendParmDate"))
				.data(list).build();

		parameter.downloadExcelTemplate("settlement/excel/pgNaverList", excelValue);

	}

	/**
	 * @Desc : 네이버 정산 내역 부분 조회
	 * @Method Name : setNaverSettlementHistory
	 * @Date : 2019. 11. 8.
	 * @Author : sic
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/naver/setSettlementHistory")
	public void setNaverSettlementHistory(Parameter<RvNaverComparision> parameter) throws Exception {
		RvNaverComparision rvNaverComparision = pgService.getNaverSettlementHistory(parameter.get());

		writeJson(parameter, rvNaverComparision);
	}

	/**
	 * @Desc : 기프트카드 대사관리 페이지 호출
	 * @Method Name : PgGiftCardMain
	 * @Date : 2019. 11. 8.
	 * @Author : sic
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/gift-card")
	public ModelAndView PgGiftCardMain(Parameter<RvGiftCardComparision> parameter) throws Exception {

		return forward("/settlement/pg-giftcard-main");
	}

	/**
	 * @Desc : 기프트카드 대사관리 리스트 조회
	 * @Method Name : pgGiftCardList
	 * @Date : 2019. 11. 8.
	 * @Author : sic
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/gift-card/pg-gift-card-list")
	public void pgGiftCardList(Parameter<RvGiftCardComparision> parameter) throws Exception {
		Pageable<RvGiftCardComparision, RvGiftCardComparision> pageable = new Pageable<>(parameter);
		Page<RvGiftCardComparision> page = pgService.getGiftCardList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 기르트카드 대사관리 엑셀 다운로드
	 * @Method Name : giftCardListExcel
	 * @Date : 2019. 11. 8.
	 * @Author : sic
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/gift-card/gift-card-list-excel")
	public void giftCardListExcel(Parameter<RvGiftCardComparision> parameter) throws Exception {
		List<RvGiftCardComparision> list = pgService.getGiftCardExcelLIst(parameter.get());
		ExcelValue excelValue = ExcelValue.builder(2, 0)
				.columnNames(Arrays.asList("niceServiceType", "apprNo", "tranDt", "tranHms", "storeCd", "voucherFrom",
						"tranKd", "transStatus", "tranType", "tranAmt", "salerKind", "mapngYn", "sendParmDate",
						"orgOrderNo"))
				.data(list).build();

		parameter.downloadExcelTemplate("settlement/excel/pgGiftCardList", excelValue);

	}

	/**
	 * @Desc : 기프트카드 정산 내역 부분 조회
	 * @Method Name : setGiftCardSettlementHistory
	 * @Date : 2019. 11. 8.
	 * @Author : sic
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/gift-card/setSettlementHistory")
	public void setGiftCardSettlementHistory(Parameter<RvGiftCardComparision> parameter) throws Exception {
		RvGiftCardComparision rvGiftCardComparision = pgService.getGiftCardSettlementHistory(parameter.get());

		writeJson(parameter, rvGiftCardComparision);
	}

	/**
	 * @Desc : KCP 대사관리 페이지 호출
	 * @Method Name : PgKcpMain
	 * @Date : 2019. 11. 12.
	 * @Author : sic
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/kcp")
	public ModelAndView PgKcpMain(Parameter<RvKcpComparision> parameter) throws Exception {

		parameter.addAttribute("siteNo", siteService.getSiteList());
		return forward("/settlement/pg-kcp-main");
	}

	/**
	 * @Desc : KCP 대사관리 리스트 조회
	 * @Method Name : pgKcpList
	 * @Date : 2019. 11. 12.
	 * @Author : sic
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/kcp/pg-kcp-list")
	public void pgKcpList(Parameter<RvKcpComparision> parameter) throws Exception {
		Pageable<RvKcpComparision, RvKcpComparision> pageable = new Pageable<>(parameter);
		Page<RvKcpComparision> page = pgService.getKcpList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : KCP 대사관리 엑셀 다운로드
	 * @Method Name : kcpListExcel
	 * @Date : 2019. 11. 12.
	 * @Author : sic
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/kcp/kcp-list-excel")
	public void kcpListExcel(Parameter<RvKcpComparision> parameter) throws Exception {
		List<RvKcpComparision> list = pgService.getKcpExcelLIst(parameter.get());
		ExcelValue excelValue = ExcelValue.builder(2, 0)
				.columnNames(Arrays.asList("kcpDealNum", "pymntDtm", "pymntStatCode", "orderNum", "pymntAmt",
						"accountsAmt", "accountsDtm", "escrowCharge", "escrowSurtax", "istmtCount", "prmtNum",
						"merchantName", "pymntMeans", "orgOrderNo", "mapngYn", "sendParmDate"))
				.data(list).build();

		parameter.downloadExcelTemplate("settlement/excel/pgKcpList", excelValue);

	}

	/**
	 * @Desc : KCP 정산 내역 부분 조회
	 * @Method Name : setKcpSettlementHistory
	 * @Date : 2019. 11. 12.
	 * @Author : sic
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/kcp/setSettlementHistory")
	public void setKcpSettlementHistory(Parameter<RvKcpComparision> parameter) throws Exception {
		RvKcpComparision rvKcpComparision = pgService.getKcpSettlementHistory(parameter.get());

		writeJson(parameter, rvKcpComparision);
	}

}
