package kr.co.abcmart.bo.afterService.controller;

import java.util.Arrays;
import java.util.HashMap;
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

import kr.co.abcmart.bo.afterService.model.master.OcAsAccept;
import kr.co.abcmart.bo.afterService.model.master.OcAsAcceptAttachFile;
import kr.co.abcmart.bo.afterService.model.master.OcAsAcceptMemo;
import kr.co.abcmart.bo.afterService.model.master.OcAsPayment;
import kr.co.abcmart.bo.afterService.service.AfterServiceService;
import kr.co.abcmart.bo.afterService.vo.OcAfterServiceAmountExcelVo;
import kr.co.abcmart.bo.afterService.vo.OcAfterServiceExcelVo;
import kr.co.abcmart.bo.order.model.master.OcOrderProduct;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : AS관리
 * @FileName : AfterServiceController.java
 * @Project : abc.bo
 * @Date : 2019. 2. 14.
 * @Author : dtimer2@3top.co.kr
 */

@Slf4j
@Controller
@RequestMapping("afterservice")
public class AfterServiceController extends BaseController {

	@Autowired
	private AfterServiceService afterserviceservice;

	@Autowired
	private SiteService siteService; // 사이트

	@Autowired
	private CommonCodeService commonCodeService; // 코드

	/************************** S: 이상호 *********************/

	/**
	 * @Desc : A/S 관리 및 목록 화면 호출
	 * @Method Name : afterserviceMain
	 * @Date : 2019. 2. 14.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/afterservice")
	public ModelAndView afterserviceMain(Parameter<?> parameter) throws Exception {

		if (!UtilsObject.isEmpty(parameter.getString("fromDash"))) {
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

		return forward("/afterservice/afterservice-main");
	}

	/**
	 * @Desc : A/S 관리 리스트 목록
	 * @Method Name : readAsList
	 * @Date : 2019. 2. 18.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-afterservice-list")
	public void readAfterServiceList(Parameter<OcAsAccept> parameter) throws Exception {
		Pageable<OcAsAccept, OcAsAccept> afterServiceVOPageble = new Pageable<>(parameter);
		try {
			Page<OcAsAccept> page = afterserviceservice.getAfterServiceList(afterServiceVOPageble);

			writeJson(parameter, page.getGrid());
		} catch (Exception e) {
			log.error("getAfterServiceList error : {}" + e.getMessage());
		}
	}

	/**
	 * @Desc : 재접수를 위한 팝업 생성
	 * @Method Name : createReAcceptDetailPop
	 * @Date : 2019. 3. 19.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/afterservice/create-reAfterservice-detail-pop")
	public ModelAndView createReAcceptDetailPop(Parameter<OcAsAccept> parameter) throws Exception {
		OcAsAccept ocAsAccept = parameter.get();
		String[] codeFields = { CommonCode.AS_GBN_CODE, CommonCode.AS_RSN_CODE };

		// 목록에서 넘겨받은 접수번호와 상품 순번을 가지고 데이타 조회
		Map<String, Object> dataMap = afterserviceservice.getSelectAfterServiceOld(ocAsAccept);

		// 공통코드 조회 결제구분코드 , AS구분 , AS진행상태 코드
		parameter.addAttribute("codeList", afterserviceservice.getCodeListByGroup(codeFields));
		parameter.addAttribute("asOldInfo", dataMap.get("ocAcceptOldData"));
		parameter.addAttribute("asProductOldInfo", dataMap.get("ocAsAcceptProductOld"));
		parameter.addAttribute("asMemberDeliInfo", dataMap.get("asMemberDeliInfo"));
		parameter.addAttribute("userInfo", dataMap.get("userInfo"));
		parameter.addAttribute("siteName", dataMap.get("siteName"));

		return forward("/afterservice/create-reaccept-detail-pop");
	}

	/**
	 * @Desc : 주문상세에서 AS신청
	 * @Method Name : adminDetailPop
	 * @Date : 2019. 2. 14.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/create-afterservice-detail-pop")
	public ModelAndView createDetailPop(Parameter<OcOrderProduct> parameter) throws Exception {
		OcOrderProduct ordProduct = parameter.get();
		// 주문상세에서 주문번호 , 상품 시퀀스 넘겨받음.

		String[] codeFields = { CommonCode.AS_GBN_CODE, CommonCode.AS_RSN_CODE };

		// 주문상품일련번호를 가지고 주문상품테이블정보를 가져온다.
		Map<String, Object> dataMap = afterserviceservice.getOrderPrdDetailInfo(ordProduct);

		parameter.addAttribute("asOrdPrdDEtail", dataMap.get("asOrdPrdDEtail"));
		parameter.addAttribute("asOrd", dataMap.get("asOrd"));
		parameter.addAttribute("asMemberDeliInfo", dataMap.get("asMemberDeliInfo"));
		parameter.addAttribute("userInfo", dataMap.get("userInfo"));
		parameter.addAttribute("siteName", dataMap.get("siteName"));

		// 접수자 , 처리자는 로그인한 관리자가 된다.

		// 공통코드 조회 결제구분코드 , AS구분 , AS진행상태 코드
		parameter.addAttribute("codeList", afterserviceservice.getCodeListByGroup(codeFields));
		return forward("/afterservice/create-afterservice-detail-pop");
	}

	/**
	 * @Desc : AS관리 목록에서 AS번호 클릭시 팝업
	 * @Method Name : AsDetailInfoPop
	 * @Date : 2019. 2. 18.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/afterservice/read-afterservice-detail-pop")
	public ModelAndView AsDetailInfoPop(Parameter<OcAsAccept> parameter) throws Exception {
		OcAsAccept ocAsAccept = parameter.get();

		// 공통코드 조회 결제구분코드 , AS구분 , AS진행상태 코드 , AS사유, 택배사코드 , AS처리유형코드 , AS심의코드 , AS철회유형
		String[] codeFields = { CommonCode.AS_GBN_CODE, CommonCode.AS_STAT_CODE, CommonCode.AS_RSN_CODE,
				CommonCode.LOGIS_VNDR_CODE, CommonCode.AS_PROC_TYPE_CODE, CommonCode.AS_PROC_TYPE_DTL_CODE,
				CommonCode.AS_WTHDRAW_RSN_CODE };

		// AS 상세 관련 데이터 조회
		Map<String, Object> dataMap = afterserviceservice.getAfterServiceDetailInfo(ocAsAccept);

		// 상세에 필요한 공통코드 가져간다.
		parameter.addAttribute("codeList", afterserviceservice.getCodeListByGroup(codeFields));

		parameter.addAttribute("asInfo", dataMap.get("dataInfo"));
		parameter.addAttribute("dataSubInfo", dataMap.get("dataSubInfo"));
		parameter.addAttribute("memoInfo", dataMap.get("memoData"));
		parameter.addAttribute("dataPayInfo", dataMap.get("dataPayInfo"));
		parameter.addAttribute("errorMessage", dataMap.get("errorMessage"));

		return forward("/afterservice/read-afterservice-detail-pop");
	}

	/**
	 * @Desc : 재접수 등록 --> 기존 AS번호 조회하여 신규채번된 데이타 INSERT
	 * @Method Name : createReAfterService
	 * @Date : 2019. 2. 25.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/afterservice/create-reAfterService")
	public void regReEntryAfterService(Parameter<OcAsAccept> parameter) throws Exception {

		OcAsAccept ocAsAccept = parameter.get();
		Map<String, Object> resultMap = afterserviceservice.setReEntryAfterService(ocAsAccept);
		writeJson(parameter, resultMap);
	}

	/**
	 * 
	 * @Desc : BO에서 사유등록시 팝업 사유 선택한 값 및 내용 업데이트 아니고 기존 내용을 보여주는 확인용.
	 * @Method Name : AsReasonPop
	 * @Date : 2019. 3. 5.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/afterservice/create-afterservice-reason-pop")
	public ModelAndView AsReasonPop(Parameter<?> parameter) {

		parameter.addAttribute("asGbnCode", parameter.getString("asGbnCode"));
		parameter.addAttribute("asRsnCode", parameter.getString("asRsnCode"));
		parameter.addAttribute("prdtName", parameter.getString("prdtName"));
		parameter.addAttribute("asAcceptContText", parameter.getString("asAcceptContText"));
		parameter.addAttribute("adminAcceptYn", parameter.getString("adminAcceptYn"));

		return forward("/afterservice/create-afterservice-reason-pop");
	}

	/**
	 * 
	 * @Desc : BO에서 사유등록시 팝업 사유 선택한 값 및 내용 업데이트 아니고 기존 내용을 보여주는 확인용.
	 * @Method Name : AsReasonPop
	 * @Date : 2019. 3. 5.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/afterservice/read-afterservice-reason-pop")
	public ModelAndView AsReadReasonPop(Parameter<OcAsAcceptAttachFile> parameter) throws Exception {

		OcAsAcceptAttachFile ocAsAcceptAttachFile = parameter.get();

		if ("N".equals(parameter.getString("adminAcceptYn"))) {
			Map<String, Object> result = afterserviceservice.getAfterServiceAttachFileInfo(ocAsAcceptAttachFile);
			parameter.addAttribute("fileList", result.get("afterServiceAttachFileList"));

		}

		parameter.addAttribute("asGbnCode", parameter.getString("asGbnCode"));
		parameter.addAttribute("asRsnCode", parameter.getString("asRsnCode"));
		parameter.addAttribute("prdtName", parameter.getString("prdtName"));
		parameter.addAttribute("asAcceptContText", parameter.getString("asAcceptContText"));
		parameter.addAttribute("adminAcceptYn", parameter.getString("adminAcceptYn"));
		parameter.addAttribute("asStatCode", parameter.getString("asStatCode"));

		// 첨부 파일 관련 상수(AS용 따로 CREATE 안함. )
		parameter.addAttribute("AS_FILE_MAX_CNT", Const.ADMIN_NOTICE_FILE_MAX_CNT);

		return forward("/afterservice/create-afterservice-reason-pop");
	}

	/**
	 * 
	 * @Desc : 주문상세에서 AS 신청 팝업에서 접수를 클릭시 해당하는
	 * @Method Name : regAfterServiceAccept
	 * @Date : 2019. 3. 5.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/afterservice/create-afterServiceAccept")
	public void createAfterServiceAccept(Parameter<OcAsAccept> parameter) throws Exception {
		OcAsAccept ocAsAccept = parameter.get();

		ocAsAccept.validate();

		// Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = afterserviceservice.setAfterServiceAccept(ocAsAccept);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 상태별 업데이트
	 * @Method Name : updateAfterServiceStatcode
	 * @Date : 2019. 3. 7.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	// TODO
	@PostMapping("/afterservice/updateAfterServiceStatCode")
	public void updateAfterServiceStatcode(Parameter<OcAsAccept> parameter) throws Exception {
		// Map<String, Object> resultMap = null;

		OcAsAccept ocAsAccept = parameter.get();

		Map<String, Object> resultMap = afterserviceservice.setAfterServiceStat(ocAsAccept);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 내용 저자을 위한 상태가 변경 없음
	 * @Method Name : updateAfterServiceStatcode
	 * @Date : 2019. 3. 13.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/afterservice/updateAfterServiceTemporaryStorage")
	public void updateAfterServiceTemporaryStorage(Parameter<OcAsAccept> parameter) throws Exception {
		// Map<String, Object> resultMap = new HashMap<String, Object>();
		// log.debug("parameter : {}", parameter);

		OcAsAccept ocAsAccept = parameter.get();

		Map<String, Object> resultMap = afterserviceservice.setAfterServiceTemporary(ocAsAccept);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 관리자메모 등록한다.
	 * @Method Name : createOcAsAcceptMemo
	 * @Date : 2019. 3. 8.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/afterservice/create-ocAsAccept-memo")
	public void createOcAsAcceptMemo(Parameter<OcAsAcceptMemo> parameter) throws Exception {
		OcAsAcceptMemo ocAsAcceptMemo = parameter.get();

		// Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = afterserviceservice.setOcAsAcceptMemo(ocAsAcceptMemo);

		parameter.addAttribute("memoInfo", resultMap);
		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 관리자 메모 삭제 상태만 Y으로 업데이트
	 * @Method Name : deleteOcAsAcceptMemo
	 * @Date : 2019. 3. 8.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/afterservice/update-ocAsAccept-memo")
	public void updateOcAsAcceptMemo(Parameter<OcAsAcceptMemo> parameter) throws Exception {

		OcAsAcceptMemo ocAsAcceptMemo = null;
		if ("orderDetail".equals(parameter.getString("click"))) {
			ocAsAcceptMemo = new OcAsAcceptMemo();
			ocAsAcceptMemo.setAsAcceptNo(parameter.getString("orderNo"));
			ocAsAcceptMemo.setAsMemoSeq(parameter.getInt("orderMemoSeq", 0));
		} else {
			ocAsAcceptMemo = parameter.get();
		}

		Map<String, Object> resultMap = afterserviceservice.updateOcAsAcceptMemo(ocAsAcceptMemo);
		parameter.addAttribute("memoInfo", resultMap);
		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : AS목록에서 엑셀 전체 다운로드 클릭
	 * @Method Name : downloadAfterserviceAllExcel
	 * @Date : 2019. 3. 13.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/afterservice/download-afterservice-all-excel")
	public void downloadAfterserviceAllExcel(Parameter<OcAsAccept> parameter) throws Exception {

		OcAsAccept ocAsAccept = parameter.get();

		List<OcAfterServiceExcelVo> list = afterserviceservice.getOcAsAcceptForAllExcelList(ocAsAccept);

		ExcelValue excelValue = ExcelValue.builder(1)
				.columnNames(Arrays.asList("siteName", "asStatCodeName", "asAcceptNo", "orderNo", "dlvyIdText",
						"prdtNo", "styleInfo", "prdtName", "optnName", "pymntAmt", "asRsnName", "asAcceptContText",
						"asProcess", "addDlvyAmt", "methodOfPayment", "asAmt", "dlvrName", "unProcYn"))
				.data(list).build();

		parameter.downloadExcelTemplate("afterservice/excel/afterServiceList", excelValue);
	}

	/**
	 * @Desc : AS목록에서 선택 다운로드 클릭
	 * @Method Name : downloadAfterserviceExcel
	 * @Date : 2019. 3. 13.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/afterservice/download-afterservice-excel")
	public void downloadAfterserviceExcel(Parameter<OcAfterServiceExcelVo> parameter) throws Exception {

		OcAfterServiceExcelVo ocAfterServiceExcelVo = parameter.get();

		List<OcAfterServiceExcelVo> list = afterserviceservice.getOcAsAcceptForExcelList(ocAfterServiceExcelVo);

		ExcelValue excelValue = ExcelValue.builder(1)
				.columnNames(Arrays.asList("siteName", "asStatCodeName", "asAcceptNo", "orderNo", "dlvyIdText",
						"prdtNo", "styleInfo", "prdtName", "optnName", "pymntAmt", "asRsnName", "asAcceptContText",
						"asProcess", "addDlvyAmt", "methodOfPayment", "asAmt", "dlvrName", "unProcYn"))
				.data(list).build();

		parameter.downloadExcelTemplate("afterservice/excel/afterServiceList", excelValue);
	}

	/**
	 * @Desc : A/S 금액 관리 페이지 진입
	 * @Method Name : afterserviceAmountMain
	 * @Date : 2019. 3. 18.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/afterserviceamount")
	public ModelAndView afterserviceAmountMain(Parameter<?> parameter) throws Exception {

		String[] codeFields = { CommonCode.AS_STAT_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);
		// site Combo,site List
		Pair<JSONObject, List<SySite>> siteCode = siteService.getSiteListByCombo();

		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();

		parameter.addAttribute("codeCombo", pair.getFirst()); // code Combo
		parameter.addAttribute("codeList", pair.getSecond()); // code list
		// 공통코드 조회 결제구분코드 , AS구분 , AS진행상태 코드, 사유공통코드
		parameter.addAttribute("asStatCode", codeList.get(CommonCode.AS_STAT_CODE));

		// 사이트구분코드(사이트)
		parameter.addAttribute("siteCombo", siteCode.getFirst()); // Site Combo
		parameter.addAttribute("siteList", siteCode.getSecond()); // Site List

		return forward("/afterservice/afterservice-amount-main");
	}

	/**
	 * @Desc : A/S 금액 관리 목록
	 * @Method Name : readAfterServiceAmtList
	 * @Date : 2019. 3. 18.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-afterservice-amount-list")
	public void readAfterServiceAmtList(Parameter<OcAsPayment> parameter) throws Exception {
		Pageable<OcAsPayment, OcAsPayment> afterServiceAmtVOPageble = new Pageable<>(parameter);

		try {
			Page<OcAsPayment> page = afterserviceservice.getAfterServiceAmtList(afterServiceAmtVOPageble);

			writeJson(parameter, page.getGrid());
		} catch (Exception e) {
			log.error("getAfterServiceAmtList error : {}" + e.getMessage());
		}
	}

	/**
	 * @Desc : AS 금액 관리 목록에서 엑셀 전체 다운로드 클릭
	 * @Method Name : downloadAfterserviceAmountAllExcel
	 * @Date : 2019. 3. 20.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/afterservice/download-afterserviceamount-all-excel")
	public void downloadAfterserviceAmountAllExcel(Parameter<OcAsPayment> parameter) throws Exception {

		OcAsPayment ocAsPayment = parameter.get();

		List<OcAfterServiceAmountExcelVo> list = afterserviceservice.getAsAmountForAllExcelList(ocAsPayment);

		ExcelValue excelValue = ExcelValue.builder(1)
				.columnNames(Arrays.asList("siteName", "asAcceptNo", "rgstDtm", "orderNo", "prdtNo", "styleInfo",
						"brandName", "prdtName", "optnName", "strOrderAmt", "asProcess", "strAddDlvyAmt", "strAsAmt",
						"strTotalPymntAmt", "asStatCodeName"))
				.data(list).build();

		parameter.downloadExcelTemplate("afterservice/excel/afterServiceAmountList", excelValue);
	}

	/**
	 * @Desc : AS 금액 관리 목록에서 선택 다운로드 클릭
	 * @Method Name : downloadAfterserviceAmountExcel
	 * @Date : 2019. 3. 20.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/afterservice/download-afterserviceamount-excel")
	public void downloadAfterserviceAmountExcel(Parameter<OcAfterServiceAmountExcelVo> parameter) throws Exception {

		OcAfterServiceAmountExcelVo ocAfterServiceAmountExcelVo = parameter.get();

		List<OcAfterServiceAmountExcelVo> list = afterserviceservice
				.getAsAmountForExcelList(ocAfterServiceAmountExcelVo);

		ExcelValue excelValue = ExcelValue.builder(1)
				.columnNames(Arrays.asList("siteName", "asAcceptNo", "rgstDtm", "orderNo", "prdtNo", "styleInfo",
						"brandName", "prdtName", "optnName", "strOrderAmt", "asProcess", "strAddDlvyAmt", "strAsAmt",
						"strTotalPymntAmt", "asStatCodeName"))
				.data(list).build();

		parameter.downloadExcelTemplate("afterservice/excel/afterServiceAmountList", excelValue);
	}

	/**
	 * @Desc : 배송비에 대한 결제 취소
	 * @Method Name : updateOcAsAcceptPaymentCancel
	 * @Date : 2019. 3. 22.
	 * @Author : dtimer2@3top.co.kr
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/afterservice/update-ocAsPayment-cancel")
	public void updateOcAsAcceptPaymentCancel(Parameter<OcAsPayment> parameter) throws Exception {
		OcAsPayment ocAsPayment = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = afterserviceservice.updateOcAsAcceptPaymentCancel(ocAsPayment);
		writeJson(parameter, resultMap);
	}

	/************************** E: 이상호 *********************/

}