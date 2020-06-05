package kr.co.abcmart.bo.sales.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.sales.model.master.IfMaechul;
import kr.co.abcmart.bo.sales.service.SalesService;
import kr.co.abcmart.bo.sales.vo.IfMeaechulExcelVo;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
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
@RequestMapping("sales")
public class SalesController extends BaseController {
	@Autowired
	private SiteService siteService; // 사이트

	@Autowired
	private CommonCodeService commonCodeService; // 코드

	@Autowired
	private SalesService salesService;

	/**
	 * @Desc : 자사 매출관리 메인 진입
	 * @Method Name : salesMain
	 * @Date : 2019. 11. 13.
	 * @Author : lee
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sales")
	public ModelAndView salesMain(Parameter<?> parameter) throws Exception {

		if (parameter.getString("fromDash") != null) {
			parameter.addAttribute("fromDash", parameter.getString("fromDash"));
		}

		// Grid 코드정보
		String[] codeFields = { CommonCode.DEVICE_CODE // 결제수단
				, CommonCode.AS_GBN_CODE // 사유구분코드
				, CommonCode.AS_STAT_CODE // 진행상태
				, CommonCode.AS_RSN_CODE // AS구분
				, CommonCode.AS_PRDT_STAT_CODE // 처리휴형 코드
		};
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);
		// site Combo,site List
		Pair<JSONObject, List<SySite>> siteCode = siteService.getSiteListByCombo();

		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();

		parameter.addAttribute("codeCombo", pair.getFirst()); // code Combo
		parameter.addAttribute("codeList", pair.getSecond()); // code list
		// 공통코드 조회 결제구분코드 , AS구분 , AS진행상태 코드, 사유공통코드
		parameter.addAttribute("deviceCode", codeList.get(CommonCode.DEVICE_CODE));
		parameter.addAttribute("asGbnCode", codeList.get(CommonCode.AS_GBN_CODE));
		parameter.addAttribute("asStatCode", codeList.get(CommonCode.AS_STAT_CODE));
		parameter.addAttribute("asRsnCode", codeList.get(CommonCode.AS_RSN_CODE));
		parameter.addAttribute("asPrdtStatCode", codeList.get(CommonCode.AS_PRDT_STAT_CODE));
		// 사이트구분코드(사이트)
		parameter.addAttribute("siteCombo", siteCode.getFirst()); // Site Combo
		parameter.addAttribute("siteList", siteCode.getSecond()); // Site List

		return forward("/sales/internal-sales-management");
	}

	/**
	 * @Desc : 자사 매출 관리 리스트 관리 목록
	 * @Method Name : readAfterServiceList
	 * @Date : 2019. 11. 13.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-sales-list")
	public void readAfterServiceList(Parameter<IfMaechul> parameter) throws Exception {
		log.debug("parameter : {}", parameter);

		Pageable<IfMaechul, IfMaechul> ifMaechulVOPageble = new Pageable<>(parameter);
		try {
			Page<IfMaechul> page = salesService.getIfMaechulList(ifMaechulVOPageble);

			writeJson(parameter, page.getGrid());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Desc : 자사 매출 관리 엑셀 다운 전체 목록
	 * @Method Name : downloadAfterserviceAllExcel
	 * @Date : 2019. 11. 13.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/download-sales-all-excel")
	public void downloadSaleAllExcel(Parameter<IfMaechul> parameter) throws Exception {

		IfMaechul ifMaechul = parameter.get();

		List<IfMeaechulExcelVo> list = salesService.getIfMaechulForAllExcelList(ifMaechul);

		ExcelValue excelValue = ExcelValue.builder(1)
				.columnNames(Arrays.asList("cbcd", "maejangcd", "iljai", "posno", "grNo", "seqNo", "pan", "hhmm",
						"sawonno", "srcmkcd", "saleqty", "saleamt", "salehalin", "saleenury", "pointamt", "coupon",
						"eventgb", "wonIlja", "wonPosno", "wonGrNo", "regdate", "errorstatus", "workdiv", "ordno",
						"itemsno", "mallname", "workday", "chasu", "safeKey", "eventpointamt"))
				.data(list).build();

		parameter.downloadExcelTemplate("sales/excel/salesList", excelValue);
	}

	/**
	 * @Desc :감색시에 맞는 조건으로 금액 산정
	 * @Method Name : getIfMaechulSelector
	 * @Date : 2019. 11. 21.
	 * @Author : lee
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/getIfMaechulSelector")
	public void getIfMaechulSelector(Parameter<IfMaechul> parameter) throws Exception {
		IfMaechul ifMaechul = salesService.getIfMaechulSelector(parameter.get());

		writeJson(parameter, ifMaechul);
	}

}