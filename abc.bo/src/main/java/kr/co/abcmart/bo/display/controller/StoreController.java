package kr.co.abcmart.bo.display.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.display.model.master.CmArea;
import kr.co.abcmart.bo.display.model.master.CmAreaDetail;
import kr.co.abcmart.bo.display.model.master.CmStore;
import kr.co.abcmart.bo.display.model.master.CmStoreService;
import kr.co.abcmart.bo.display.service.StoreService;
import kr.co.abcmart.bo.stats.model.master.SaMemberLeaveStatus;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/display/store")
public class StoreController extends BaseController {

	@Autowired
	private StoreService storeService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private SiteService siteService;

	/**
	 * @Desc : 오프라인 매장관리
	 * @Method Name : store
	 * @Date : 2019. 3. 18.
	 * @Author : 이가영
	 * @param parameter
	 * @return
	 */
	@GetMapping("")
	public ModelAndView store(Parameter<?> parameter) throws Exception {

		List<SySite> siteList = siteService.getSiteList();
		List<CmArea> areaList = storeService.getCmAreaList();

		String[] codeFields = { CommonCode.STORE_TYPE_CODE, CommonCode.STORE_SERVICE_CODE };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();

		parameter.addAttribute("siteList", siteList);
		parameter.addAttribute("areaList", areaList);
		parameter.addAttribute("codeCombo", pair.getFirst());
		parameter.addAttribute("storeTypeCodeList", codeList.get(CommonCode.STORE_TYPE_CODE));
		parameter.addAttribute("storeServiceCodeList", codeList.get(CommonCode.STORE_SERVICE_CODE));

		return forward("/display/store/store");
	}

	/**
	 * @Desc : 오프라인 매장관리 리스트 조회
	 * @Method Name : list
	 * @Date : 2019. 3. 18.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/list")
	@ResponseBody
	public void list(Parameter<CmStore> parameter) throws Exception {

		Pageable<CmStore, CmStore> pageable = new Pageable<>(parameter);

		Page<CmStore> page = storeService.getCmStoreList(pageable);

		writeJson(parameter, page.getGrid());

	}

	/**
	 * @Desc : 오프라인 매장관리 상세 조회
	 * @Method Name : detail
	 * @Date : 2019. 3. 18.
	 * @Author : 이가영
	 * @param parameter
	 * @return
	 */
	@GetMapping("/detail")
	public ModelAndView detail(Parameter<CmStore> parameter) throws Exception {

		CmStore cmStore = parameter.get();

		if (cmStore.getStoreNo() != null) {
			CmStore detail = storeService.getCmStoreDetail(cmStore);
			parameter.addAttribute("areaDtlList", storeService.getCmAreaDetailList(detail));
			parameter.addAttribute("store", detail);

			// 매장 제공 서비스
			CmStoreService cmStoreService = new CmStoreService();
			cmStoreService.setStoreNo(detail.getStoreNo());

			List<CmStoreService> serviceList = storeService.getStoreServiceList(cmStoreService);
			parameter.addAttribute("serviceList", serviceList);
		}

		List<SySite> siteList = siteService.getSiteList();
		List<CmArea> areaList = storeService.getCmAreaList();

		String[] codeFields = { CommonCode.STORE_TYPE_CODE, CommonCode.STORE_GBN_CODE, CommonCode.STORE_SERVICE_CODE,
				CommonCode.BUSINESS_STOP_RSN_CODE };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				true);

		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();

		parameter.addAttribute("siteList", siteList);
		parameter.addAttribute("areaList", areaList);
		parameter.addAttribute("codeCombo", pair.getFirst());
		parameter.addAttribute("storeTypeCodeList", codeList.get(CommonCode.STORE_TYPE_CODE));
		parameter.addAttribute("storeGbnCodeList", codeList.get(CommonCode.STORE_GBN_CODE));
		parameter.addAttribute("storeServiceCodeList", codeList.get(CommonCode.STORE_SERVICE_CODE));
		parameter.addAttribute("businessStopRsnCodeList", codeList.get(CommonCode.BUSINESS_STOP_RSN_CODE));

		return forward("/display/store/store-detail");
	}

	/**
	 * @Desc : 오프라인 매장 지도 검색
	 * @Method Name : getDetailInfo
	 * @Date : 2019. 5. 23.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/map/info")
	public void getDetailInfo(Parameter<CmStore> parameter) throws Exception {

		CloseableHttpClient httpClient = HttpClients.createDefault();

		// get 메서드와 URL 설정
		HttpGet httpGet = new HttpGet(UtilsText.concat("https://naveropenapi.apigw.ntruss.com/map-place/v1/search?",
				"query=", UtilsText.urlEncode(parameter.get().getSearchStoreName()),
				"&coordinate=127.1054328,37.3595963"));

		// agent 정보 설정
		httpGet.addHeader("X-NCP-APIGW-API-KEY-ID", Config.getString("naver.map.key"));
		httpGet.addHeader("X-NCP-APIGW-API-KEY", Config.getString("naver.map.secretkey"));
		httpGet.addHeader("Content-type", "application/json");

		// get 요청
		CloseableHttpResponse httpResponse;
		String json = "";

		try {
			httpResponse = httpClient.execute(httpGet);
			log.debug("GET Response Status");
			log.debug("" + httpResponse.getStatusLine().getStatusCode());
			json = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

			log.debug("result : {}", json);
		} catch (Exception e) {
			log.error("{}", e);
		} finally {
			httpClient.close();
		}

		writeJson(parameter, json);
	}

	/**
	 * @Desc : 오프라인 매장관리 등록·수정
	 * @Method Name : save
	 * @Date : 2019. 3. 19.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/save")
	@ResponseBody
	public void save(Parameter<CmStore> parameter) throws Exception {

		parameter.validate();

		CmStore cmStore = parameter.get();

		storeService.setCmStore(cmStore);

	}

	/**
	 * @Desc : 지역 상세 조회
	 * @Method Name : area
	 * @Date : 2019. 3. 18.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/area")
	@ResponseBody
	public void area(Parameter<CmStore> parameter) throws Exception {

		List<CmAreaDetail> areaDtlList = storeService.getCmAreaDetailList(parameter.get());

		writeJson(parameter, areaDtlList);
	}

	/**
	 * @Desc : 오프라인 매장 지도 검색 popup
	 * @Method Name : mapPopup
	 * @Date : 2019. 3. 19.
	 * @Author : 이가영
	 * @param parameter
	 * @return
	 */
	@GetMapping("/map")
	public ModelAndView mapPopup(Parameter<CmStore> parameter) throws Exception {

		Map<String, String> data = new HashMap<String, String>();

		data.put("pointX", parameter.get().getPointX());
		data.put("pointY", parameter.get().getPointY());

		parameter.addAttribute("data", data);

		return forward("/display/store/map-popup");
	}

	/**
	 * @Desc : 오프라인 매장 삭제
	 * @Method Name : delete
	 * @Date : 2019. 9. 20.
	 * @Author : 이가영
	 * @param parameter
	 */
	@PostMapping("/delete")
	public void delete(Parameter<CmStore> parameter) throws Exception {

		CmStore cmStore = parameter.get();

		storeService.deleteCmStore(cmStore.getStoreNo());
	}
	
	/**
	 * @Desc      	: 오프라인 매장관리 엑셀다운로드
	 * @Method Name : excelDown
	 * @Date  	  	: 2020. 4. 8.
	 * @Author    	: sic
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/excel-down")
	public void excelDown(Parameter<CmStore> parameter) throws Exception {
		List<CmStore> excelList = storeService.getStoreExcelList(parameter.get());
		
		ExcelValue excelValue = ExcelValue.builder(2, 0).columnNames(Arrays.asList("dispYn", "areaName", "storeTypeCode",
				"storeName", "storeIdText", "telNoText", "storeServiceArr", "businessStartTimeString", "businessEndTimeString", "pickupPsbltYn", 
				"empPriceBuyPsbltYn", "fullAddr", "rgsterInfo", "rgstDtmString", "moderInfo", "modDtmString"))
				.data(excelList).build();

		parameter.downloadExcelTemplate("display/store/excel/storeList", excelValue);
	}

}