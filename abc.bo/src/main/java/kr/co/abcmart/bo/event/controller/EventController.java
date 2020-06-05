package kr.co.abcmart.bo.event.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.apache.poi.ss.usermodel.Sheet;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.event.model.master.EvEvent;
import kr.co.abcmart.bo.event.model.master.EvEventAnswer;
import kr.co.abcmart.bo.event.model.master.EvEventAttendanceCheckBenefit;
import kr.co.abcmart.bo.event.model.master.EvEventImage;
import kr.co.abcmart.bo.event.model.master.EvEventProductReceiptStore;
import kr.co.abcmart.bo.event.model.master.EvEventPublicationNumber;
import kr.co.abcmart.bo.event.model.master.EvEventResult;
import kr.co.abcmart.bo.event.model.master.EvEventResultBenefit;
import kr.co.abcmart.bo.event.model.master.EvEventResultBenefitMember;
import kr.co.abcmart.bo.event.model.master.EvEventRouletteBenefit;
import kr.co.abcmart.bo.event.model.master.EvEventRouletteJoinMember;
import kr.co.abcmart.bo.event.model.master.EvEventTargetChannel;
import kr.co.abcmart.bo.event.model.master.EvEventTargetCoupon;
import kr.co.abcmart.bo.event.model.master.EvEventTargetDevice;
import kr.co.abcmart.bo.event.model.master.EvEventTargetGrade;
import kr.co.abcmart.bo.event.service.EventService;
import kr.co.abcmart.bo.event.vo.EvEventJoinResultVO;
import kr.co.abcmart.bo.event.vo.EvEventSearchVO;
import kr.co.abcmart.bo.member.service.MemberService;
import kr.co.abcmart.bo.product.model.master.PdProductMapped;
import kr.co.abcmart.bo.product.service.ProductService;
import kr.co.abcmart.bo.product.vo.PdProductMappingVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.excel.ExcelReader;
import kr.co.abcmart.common.excel.ExcelValue;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.FileUpload;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("promotion/event")
public class EventController extends BaseController {

	@Autowired
	private SiteService siteService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private EventService eventService;

	@Autowired
	private ProductService productService;

	@Autowired
	private MemberService memberService;

	/**
	 * @Desc : 이벤트 관리
	 * @Method Name : event
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ModelAndView event(Parameter<?> parameter) throws Exception {
		List<SySiteChnnl> channelList = siteService.getUseChannelList();
		List<SyCodeDetail> deviceCodeList = commonCodeService.getCodeNoName(CommonCode.DEVICE_CODE);
		List<SyCodeDetail> mbshpGradeCodeList = commonCodeService.getCodeNoName(CommonCode.MBSHP_GRADE_CODE);
		List<SyCodeDetail> memberTypeCodeList = commonCodeService.getCodeNoName(CommonCode.MEMBER_TYPE_CODE);
		List<SyCodeDetail> eventTypeCodeList = commonCodeService.getCodeNoName(CommonCode.EVENT_TYPE_CODE);

		log.debug("event");
		parameter.addAttribute("channelList", channelList);
		parameter.addAttribute("deviceCodeList", deviceCodeList);
		parameter.addAttribute("mbshpGradeCodeList", mbshpGradeCodeList);
		parameter.addAttribute("memberTypeCodeList", memberTypeCodeList);
		parameter.addAttribute("eventTypeCodeList", eventTypeCodeList);

		return forward("/promotion/event/event");
	}

	/**
	 * @Desc : 이벤트 관리(리스트조회)
	 * @Method Name : readEventList
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/list/read")
	public void readEventList(Parameter<EvEventSearchVO> parameter) throws Exception {

		Pageable<EvEventSearchVO, EvEvent> pageable = new Pageable<>(parameter);

		Page<EvEvent> page = eventService.getEventList(pageable);

		writeJson(parameter, page.getGrid());

	}

	/**
	 * @Desc : 이벤트 관리(상세)
	 * @Method Name : eventDetail
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("detail")
	public ModelAndView eventDetail(Parameter<EvEvent> parameter) throws Exception {

		EvEvent evEvent = parameter.get();

		// 화면에 사용되는 공통코드 조회
		String[] codeFields = { CommonCode.SELL_STAT_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
				.getCodeListByGroupByCombo(codeFields, false);

		parameter.addAttribute("codeCombo", pair.getFirst()); // 그리드 공통코드. 판매상태

		String eventNo = evEvent.getEventNo();
		if (eventNo != null) {
			// 이벤트 상세
			evEvent = eventService.getEvent(evEvent);
			// 디바이스
			List<EvEventTargetDevice> evEventTargetDeviceList = eventService.getEvEventDeviceListByEventNo(eventNo);
			// 채널
			List<EvEventTargetChannel> evEventTargetChannelList = eventService.getEvEventChannelListByEventNo(eventNo);
			// 대상회원
			List<EvEventTargetGrade> evEventTargetGradeList = eventService.getEvEventTargetGradeListByEventNo(eventNo);
			// 이미지
			List<EvEventImage> evEventImageList = eventService.getEvEventImageListByEventNo(eventNo);
			// 출석체크 혜택
			List<EvEventAttendanceCheckBenefit> evEventAttendanceCheckBenefitList = eventService
					.getEvEventAttendanceCheckBenefitListByEventNo(eventNo);
			// 룰렛 혜택
			List<EvEventRouletteBenefit> evEventRouletteBenefitList = eventService
					.getEvEventRouletteBenefitListByEventNo(eventNo);
			// 매장
			List<EvEventProductReceiptStore> evEventProductReceiptStoreList = eventService
					.getEvEventProductReceiptStoreListByEventNo(eventNo);

			parameter.addAttribute("evEventTargetDeviceList", evEventTargetDeviceList);
			parameter.addAttribute("evEventTargetChannelList", evEventTargetChannelList);
			parameter.addAttribute("evEventImageList", evEventImageList);
			parameter.addAttribute("evEventTargetGradeList", evEventTargetGradeList);
			parameter.addAttribute("evEventAttendanceCheckBenefitList", evEventAttendanceCheckBenefitList);
			parameter.addAttribute("evEventRouletteBenefitList", evEventRouletteBenefitList);
			parameter.addAttribute("evEventProductReceiptStoreList", evEventProductReceiptStoreList);
		}

		List<SySiteChnnl> channelList = siteService.getUseChannelList();
		List<SyCodeDetail> deviceCodeList = commonCodeService.getCodeNoName(CommonCode.DEVICE_CODE);
		List<SyCodeDetail> mbshpGradeCodeList = commonCodeService.getCodeNoName(CommonCode.MBSHP_GRADE_CODE);
		List<SyCodeDetail> memberTypeCodeList = commonCodeService.getCodeNoName(CommonCode.MEMBER_TYPE_CODE);
		List<SyCodeDetail> eventTypeCodeList = commonCodeService.getCodeNoName(CommonCode.EVENT_TYPE_CODE);
		List<SyCodeDetail> affltsCodeList = commonCodeService.getCodeNoName(CommonCode.AFFLTS_CODE);
		List<SyCodeDetail> joinLimitTypeCodeList = commonCodeService.getCodeNoName(CommonCode.JOIN_LIMIT_TYPE_CODE);
		List<SyCodeDetail> benefitGbnCodeList = commonCodeService.getCodeNoName(CommonCode.BENEFIT_GBN_CODE);
		List<SyCodeDetail> winRateCodeList = commonCodeService.getCodeNoName(CommonCode.WIN_RATE_CODE);

		parameter.addAttribute("evEvent", evEvent);
		parameter.addAttribute("channelList", channelList);
		parameter.addAttribute("affltsCodeList", affltsCodeList);
		parameter.addAttribute("deviceCodeList", deviceCodeList);
		parameter.addAttribute("mbshpGradeCodeList", mbshpGradeCodeList);
		parameter.addAttribute("memberTypeCodeList", memberTypeCodeList);
		parameter.addAttribute("eventTypeCodeList", eventTypeCodeList);
		parameter.addAttribute("joinLimitTypeCodeList", joinLimitTypeCodeList);
		parameter.addAttribute("benefitGbnCodeList", benefitGbnCodeList);
		parameter.addAttribute("winRateCodeList", winRateCodeList);
		return forward("/promotion/event/event-detail");
	}

	/**
	 * @Desc : 이벤트 등록 관리(이벤트 등록)
	 * @Method Name : saveEvent
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/save")
	public void saveEvent(Parameter<EvEvent> parameter) throws Exception {
		parameter.validate();

		if (!parameter.isValidation()) {
			throw new ValidationException();
		}

		EvEvent evEvent = parameter.get();

		eventService.insertEvEvent(evEvent);
	}

	/**
	 * @Desc : 이벤트 등록 관리(이벤트 수정)
	 * @Method Name : modifyEvent
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/modify")
	public void modifyEvent(Parameter<EvEvent> parameter) throws Exception {
		parameter.validate();

		if (!parameter.isValidation()) {
			throw new ValidationException();
		}

		EvEvent evEvent = parameter.get();

		eventService.updateEvEvent(evEvent);
	}

	/**
	 * @Desc : 내부관리 번호 중복체크
	 * @Method Name : promotionDuplCheck
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/duplCheck")
	public void promotionDuplCheck(Parameter<?> parameter) throws Exception {
		String insdMgmtInfoText = parameter.getString("insdMgmtInfoText");
		String eventNo = parameter.getString("eventNo");

		boolean duplCheckVal = eventService.getEventDuplCheck(insdMgmtInfoText, eventNo);

		writeJson(parameter, duplCheckVal);

	}

	/**
	 * @Desc : 프로모션 이벤트 쿠폰(조회)
	 * @Method Name : readEventCouponList
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/coupon/list/read")
	public void readEventCouponList(Parameter<?> parameter) throws Exception {
		String eventNo = parameter.getString("eventNo");

		List<EvEventTargetCoupon> evEventTargetCouponList = eventService.getEvEventTargetCouponListByEventNo(eventNo);

		writeJson(parameter, evEventTargetCouponList);
	}

	/**
	 * @Desc : 이벤트 응모 관리
	 * @Method Name : eventJoin
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("join")
	public ModelAndView eventJoin(Parameter<?> parameter) throws Exception {
		List<SySiteChnnl> channelList = siteService.getUseChannelList();
		List<SyCodeDetail> deviceCodeList = commonCodeService.getCodeNoName(CommonCode.DEVICE_CODE);
		List<SyCodeDetail> mbshpGradeCodeList = commonCodeService.getCodeNoName(CommonCode.MBSHP_GRADE_CODE);
		List<SyCodeDetail> memberTypeCodeList = commonCodeService.getCodeNoName(CommonCode.MEMBER_TYPE_CODE);
		List<SyCodeDetail> eventTypeCodeList = commonCodeService.getCodeNoName(CommonCode.EVENT_TYPE_CODE);

		log.debug("eventJoin");
		parameter.addAttribute("channelList", channelList);
		parameter.addAttribute("deviceCodeList", deviceCodeList);
		parameter.addAttribute("mbshpGradeCodeList", mbshpGradeCodeList);
		parameter.addAttribute("memberTypeCodeList", memberTypeCodeList);
		parameter.addAttribute("eventTypeCodeList", eventTypeCodeList);
		return forward("/promotion/event/join");
	}

	/**
	 * @Desc : 이벤트 응모 관리(상세)
	 * @Method Name : eventJoinDetail
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("join/detail")
	public ModelAndView eventJoinDetail(Parameter<EvEvent> parameter) throws Exception {

		EvEvent evEvent = parameter.get();

		evEvent = eventService.getEvent(evEvent);

		// 화면에 사용되는 공통코드 조회
		List<SyCodeDetail> codeList = commonCodeService.getCodeNoName("UN_DISP_RSN_CODE");

		if (codeList != null && codeList.size() > 0) {
			String[] codeFields = { "UN_DISP_RSN_CODE" };
			List<SyCodeDetail> unDIspRsnCode = new ArrayList<SyCodeDetail>();
			SyCodeDetail syCodeDetail = new SyCodeDetail();
			syCodeDetail.setCodeDtlNo("");
			syCodeDetail.setCodeDtlName("사유를 선택하세요");
			syCodeDetail.setCodeField("UN_DISP_RSN_CODE");
			unDIspRsnCode.add(syCodeDetail);
			unDIspRsnCode.addAll(codeList);

			Map<String, List<SyCodeDetail>> map = new HashMap<>();
			map.put("UN_DISP_RSN_CODE", unDIspRsnCode);

			parameter.addAttribute("codeCombo", commonCodeService.getPairCodeListGroup(codeFields, map).getFirst());
		}

		List<SySiteChnnl> channelList = siteService.getUseChannelList();
		List<SyCodeDetail> deviceCodeList = commonCodeService.getCodeNoName(CommonCode.DEVICE_CODE);
		List<SyCodeDetail> mbshpGradeCodeList = commonCodeService.getCodeNoName(CommonCode.MBSHP_GRADE_CODE);
		List<SyCodeDetail> memberTypeCodeList = commonCodeService.getCodeNoName(CommonCode.MEMBER_TYPE_CODE);
		List<SyCodeDetail> eventTypeCodeList = commonCodeService.getCodeNoName(CommonCode.EVENT_TYPE_CODE);

		parameter.addAttribute("evEvent", evEvent);
		parameter.addAttribute("channelList", channelList);
		parameter.addAttribute("deviceCodeList", deviceCodeList);
		parameter.addAttribute("mbshpGradeCodeList", mbshpGradeCodeList);
		parameter.addAttribute("memberTypeCodeList", memberTypeCodeList);
		parameter.addAttribute("eventTypeCodeList", eventTypeCodeList);
		return forward("/promotion/event/event-join-detail");
	}

	/**
	 * @Desc : 프로모션 이벤트 참여(조회)
	 * @Method Name : readEventJoinList
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/join/list/read")
	public void readEventJoinList(Parameter<EvEventSearchVO> parameter) throws Exception {
		Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable = new Pageable<>(parameter);

		Page<EvEventJoinResultVO> page = eventService.getEventJoinList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 프로모션 당첨자 목록(조회)
	 * @Method Name : readEventResultMemberList
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/result/member/list/read")
	public void readEventResultMemberList(Parameter<EvEventSearchVO> parameter) throws Exception {
		Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable = new Pageable<>(parameter);

		Page<EvEventJoinResultVO> page = eventService.getEventResultMemberList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 이벤트 당첨자 관리
	 * @Method Name : eventResult
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("result")
	public ModelAndView eventResult(Parameter<?> parameter) throws Exception {
		List<SySiteChnnl> channelList = siteService.getUseChannelList();
		List<SyCodeDetail> deviceCodeList = commonCodeService.getCodeNoName(CommonCode.DEVICE_CODE);
		List<SyCodeDetail> eventTypeCodeList = commonCodeService.getCodeNoName(CommonCode.EVENT_TYPE_CODE);

		log.debug("eventJoin");
		parameter.addAttribute("channelList", channelList);
		parameter.addAttribute("deviceCodeList", deviceCodeList);
		parameter.addAttribute("eventTypeCodeList", eventTypeCodeList);
		return forward("/promotion/event/result");
	}

	/**
	 * @Desc : 프로모션 이벤트 당첨자(조회)
	 * @Method Name : readEventResultList
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/result/list/read")
	public void readEventResultList(Parameter<EvEventSearchVO> parameter) throws Exception {
		Pageable<EvEventSearchVO, EvEventResult> pageable = new Pageable<>(parameter);

		Page<EvEventResult> page = eventService.getEventResultList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 이벤트 당첨자 관리(상세)
	 * @Method Name : eventResultDetail
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("result/detail")
	public ModelAndView eventResultDetail(Parameter<EvEventResult> parameter) throws Exception {

		EvEventResult evEventResult = parameter.get();

		String eventNo = evEventResult.getEventNo();
		if (eventNo != null) {
			// 이벤트 상세
			evEventResult = eventService.getEventResult(evEventResult);
			// 이벤트 당첨자 헤택
			List<EvEventResultBenefit> evEventResultBenefitList = eventService
					.getEvEventResultBenefitListByEventNo(eventNo);

			parameter.addAttribute("evEventResult", evEventResult);
			parameter.addAttribute("evEventResultBenefitList", evEventResultBenefitList);
			parameter.addAttribute("eventNo", eventNo);
			parameter.addAttribute("eventName", parameter.get().getEventName());
		}

		return forward("/promotion/event/event-result-detail");
	}

	/**
	 * @Desc : 당첨자 관리 등록
	 * @Method Name : saveResultEvent
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/result/save")
	public void saveResultEvent(Parameter<EvEventResult> parameter) throws Exception {
		parameter.validate();

		if (!parameter.isValidation()) {
			throw new ValidationException();
		}

		EvEventResult evEventResult = parameter.get();

		eventService.insertEvEventResult(evEventResult);
	}

	/**
	 * @Desc : 당첨자 관리 수정(이벤트 수정)
	 * @Method Name : modifyResultEvent
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/result/modify")
	public void modifyResultEvent(Parameter<EvEventResult> parameter) throws Exception {
		parameter.validate();

		if (!parameter.isValidation()) {
			throw new ValidationException();
		}

		EvEventResult evEventResult = parameter.get();

		eventService.updateEvEventResult(evEventResult);
	}

	/**
	 * @Desc : 이벤트 검색 팝업
	 * @Method Name : eventPopup
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/popup")
	public ModelAndView eventPopup(Parameter<?> parameter) throws Exception {
		List<SyCodeDetail> memberTypeCodeList = commonCodeService.getCodeNoName(CommonCode.MEMBER_TYPE_CODE);
		List<SyCodeDetail> eventTypeCodeList = commonCodeService.getCodeNoName(CommonCode.EVENT_TYPE_CODE);

		log.debug("event-popup");
		parameter.addAttribute("eventTypeCodeList", eventTypeCodeList);
		parameter.addAttribute("memberTypeCodeList", memberTypeCodeList);
		return forward("/promotion/event/popup/event-popup");
	}

	/**
	 * @Desc : 당첨자 등록 팝업
	 * @Method Name : eventResultMemberPopup
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/result/member/popup")
	public ModelAndView eventResultMemberPopup(Parameter<EvEvent> parameter) throws Exception {
		log.debug("event-result-member-popup");
		parameter.addAttribute("event", parameter.get());
		return forward("/promotion/event/popup/event-result-member-popup");
	}

	/**
	 * @Desc : 당첨자 목록 팝업
	 * @Method Name : eventWinPopup
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/win/popup")
	public ModelAndView eventWinPopup(Parameter<?> parameter) throws Exception {
		String eventNo = parameter.getString("eventNo");
		String eventTypeCode = parameter.getString("eventTypeCode");
		List<EvEventResultBenefit> evEventResultBenefitList = eventService
				.getEvEventResultBenefitListByEventNo(eventNo);
		log.debug("event-win-popup");
		parameter.addAttribute("eventNo", eventNo);
		parameter.addAttribute("eventTypeCode", eventTypeCode);
		parameter.addAttribute("evEventResultBenefitList", evEventResultBenefitList);
		return forward("/promotion/event/popup/event-win-popup");
	}

	/**
	 * @Desc : 당첨자 관리(엑셀 폼)
	 * @Method Name : memberExcelDownload
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/result/member/excel/form/download")
	public void memberExcelDownload(Parameter<?> parameter) throws Exception {

		parameter.downloadExcelTemplate("promotion/event/excel/memberResult", null);
	}

	/**
	 * @Desc : 당첨자 관리(당첨 회원 엑셀 데이터)
	 * @Method Name : readMemberExcelList
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/result/member/excel/list")
	public void readMemberExcelList(Parameter<?> parameter) throws Exception {
		String eventNo = parameter.getString("eventNo");
		String eventTypeCode = parameter.getString("eventTypeCode");
		FileUpload uploadFile = parameter.getUploadFile("excelUpload");

		ExcelReader reader = ExcelReader.builder(1).converter((Sheet sheet, int rowNum, String[] row) -> {

			EvEventResultBenefitMember evEventResultBenefitMember = new EvEventResultBenefitMember();
			log.debug("rowNum = {}", sheet.getLastRowNum());
			evEventResultBenefitMember.setTotalRowNum(sheet.getLastRowNum());
			try {
				if (UtilsText.isNotBlank(row[0]) && UtilsText.isNotBlank(row[1]) && UtilsText.isNotBlank(row[2])) {
					String memberNo = eventService.getMemberNoByLoginId(row[2], eventNo, eventTypeCode);
					if (UtilsText.isNotBlank(memberNo)) {
						evEventResultBenefitMember.setMemberNo(memberNo);
						evEventResultBenefitMember.setSortSeq(row[0]);
						evEventResultBenefitMember.setBenefitName(row[1]);
					}
				}
			} catch (Exception e) {
				log.error(e.toString());
			}

			if (UtilsText.isNotBlank(evEventResultBenefitMember.getMemberNo()))
				return evEventResultBenefitMember;
			else
				return null;
		}).build();

		List<EvEventResultBenefitMember> list = reader.read(uploadFile.getMultiPartFile().getInputStream());
		Collections.sort(list);
		if (list.size() > 0) {
			Set<EvEventResultBenefitMember> set = list.stream().collect(Collectors
					.toCollection(() -> new TreeSet<>(Comparator.comparing(EvEventResultBenefitMember::getMemberNo))));
			list = new ArrayList<EvEventResultBenefitMember>(set);
			Collections.sort(list);
		}
		writeJson(parameter, list);

	}

	/**
	 * @Desc : 이벤트 상세 에디터 업로드 이미지
	 * @Method Name : editorUpload
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/editor/upload")
	public void editorUpload(Parameter<EvEvent> parameter) throws Exception {

		EvEvent evEvent = parameter.get();

		Map<String, Object> result = eventService.uploadImageByEditor(evEvent.getUpload());

		writeJson(parameter, result);

	}

	/**
	 * @Desc : 이벤트 등록 관리(상품 조회)
	 * @Method Name : readCouponProductList
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/product/list/read")
	public void readCouponProductList(Parameter<PdProductMappingVO> parameter) throws Exception {
		Pageable<PdProductMappingVO, PdProductMapped> pageable = new Pageable<PdProductMappingVO, PdProductMapped>(
				parameter);
		pageable.getBean().setMappingTableName("ev_event_target_product");
		pageable.getBean().setConditionColumnName("event_no");
		pageable.getBean().setConditionColumnValue(parameter.getString("eventNo"));
		pageable.getBean().setSortColumnName("prdt_no");
		pageable.getBean().setSortType("ASC");
		Page<PdProductMapped> page = this.productService.searchProductByMapped(pageable);
		this.writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 이벤트 댓글 수정
	 * @Method Name : modifyAnswer
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/answer/modify")
	public void modifyAnswer(Parameter<EvEventAnswer> parameter) throws Exception {
		EvEventAnswer evEventAnswer = parameter.get();

		eventService.updateEvEventAnswer(evEventAnswer);
	}

	/**
	 * @Desc : 이벤트 룰렛 수정
	 * @Method Name : modifyRoulette
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/roulette/modify")
	public void modifyRoulette(Parameter<EvEventRouletteJoinMember> parameter) throws Exception {
		EvEventRouletteJoinMember evEventRouletteJoinMember = parameter.get();

		eventService.updateEvEventRouletteJoinMember(evEventRouletteJoinMember);
	}

	/**
	 * @Desc : 이벤트 지류 엑셀 다운로드
	 * @Method Name : pblicteExcelDownload
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/pblicte/excel/download")
	public void pblicteExcelDownload(Parameter<EvEvent> parameter) throws Exception {
		List<EvEventPublicationNumber> list = eventService
				.getEvEventPublicationNumberList(parameter.getString("eventNo"));

		ExcelValue excelValue = ExcelValue.builder(1, 0).columnNames(Arrays.asList("eventPblicteNo")).data(list)
				.build();

		parameter.downloadExcelTemplate("promotion/event/excel/pblicte", excelValue);
	}

	/**
	 * @Desc : 룰렛 영역 팝업
	 * @Method Name : roulettePreviewPopup
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/roulette/preivew/popup")
	public ModelAndView roulettePreviewPopup(Parameter<?> parameter) throws Exception {
		return forward("/promotion/event/popup/event-roulette-popup");
	}

	/**
	 * @Desc : 이벤트 리스트 조회(회원참여)
	 * @Method Name : readMemberHistoryEventList
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/member/history/list/read")
	public void readMemberHistoryEventList(Parameter<EvEventSearchVO> parameter) throws Exception {

		Pageable<EvEventSearchVO, EvEvent> pageable = new Pageable<>(parameter);

		Page<EvEvent> page = eventService.getMemberHistoryEventList(pageable);

		writeJson(parameter, page.getGrid());

	}

	/**
	 * @Desc : 이벤트 결과 이벤트번호 중복체크
	 * @Method Name : eventResultDuplCheck
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/result/duplCheck")
	public void eventResultDuplCheck(Parameter<?> parameter) throws Exception {
		String eventNo = parameter.getString("eventNo");

		boolean duplCheckVal = eventService.getEventResultDuplCheck(eventNo);

		writeJson(parameter, duplCheckVal);
	}

	/**
	 * @Desc : 응모 관리(엑셀 다운로드)
	 * @Method Name : joinMemberExcelDownload
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/join/member/excel/form/download")
	public void joinMemberExcelDownload(Parameter<EvEventSearchVO> parameter) throws Exception {
		EvEventSearchVO param = parameter.get();
		String eventTypeCode = param.getEventTypeCode();
		String excelPath = "";
		Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable = new Pageable<>(parameter);
		pageable.setUsePage(false);

		Page<EvEventJoinResultVO> page = eventService.getEventJoinList(pageable);

		List<EvEventJoinResultVO> list = page.getContent();
		int idx = 0;
		if (list != null) {
			for (EvEventJoinResultVO result : list) {
				result.setExcelIndex(++idx);
			}
		}

		ExcelValue excelValue = null;

		if (UtilsText.equals(eventTypeCode, CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_ROULETTE)) { // 룰렛
			excelValue = ExcelValue.builder(1)
					.columnNames(Arrays.asList("excelIndex", "loginId", "memberTypeCodeName", "deviceCodeName",
							"benefitName", "joinDtm", "issueYnName", "issueDtm", "moderInfo", "modDtm"))
					.data(list).build();
			excelPath = "promotion/event/excel/join_roulette";

		} else if (UtilsText.equals(eventTypeCode, CommonCode.EVENT_TYPE_CODE_REPLY_TYPE)) { // 댓글
			excelValue = ExcelValue.builder(1, 0)
					.columnNames(Arrays.asList("excelIndex", "loginId", "memberTypeCodeName", "deviceCodeName",
							"aswrContText", "rgstDtm", "unDispRsnCodeName", "unDispRsnText", "dispYnName", "moderInfo",
							"modDtm"))
					.data(list).build();
			excelPath = "promotion/event/excel/join_answer";
		} else if (UtilsText.equals(eventTypeCode, CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_ATTENDANCE_CHECK)) { // 출석체크
			excelValue = ExcelValue.builder(1, 0).columnNames(Arrays.asList("excelIndex", "loginId",
					"memberTypeCodeName", "deviceCodeName", "atendDtm", "attendCount", "benefitName")).data(list)
					.build();
			excelPath = "promotion/event/excel/join_attendance";
		} else if (UtilsText.equals(eventTypeCode, CommonCode.EVENT_TYPE_CODE_PARTICIPATION_TYPE_DRAW)) { // 드로우
			excelValue = ExcelValue.builder(1, 0)
					.columnNames(Arrays.asList("excelIndex", "loginId", "memberTypeCodeName", "deviceCodeName",
							"prdtName", "optnName", "addInfo1", "addInfo2", "addInfo3", "storeName", "joinDtm"))
					.data(list).build();
			excelPath = "promotion/event/excel/join_draw";
		} else { // 일반형
			excelValue = ExcelValue.builder(1, 0).columnNames(Arrays.asList("excelIndex", "loginId",
					"memberTypeCodeName", "deviceCodeName", "joinDtm", "addInfo1", "cpnName")).data(list).build();
			excelPath = "promotion/event/excel/join_basic";
		}

		if (excelValue != null)
			parameter.downloadExcelTemplate(excelPath, excelValue);
	}

	/**
	 * @Desc : 미리보기
	 * @Method Name : getFrontUrl
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/front/url")
	public void getFrontUrl(Parameter<?> parameter) throws Exception {
		String deviceCode = parameter.getString("deviceCode");
		String url = eventService.getUrl(deviceCode);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("data", url);
		this.writeJson(parameter, result);
	}

	/**
	 * @Desc : 드로우 상품 중복 체크
	 * @Method Name : eventDrawDuplCheck
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/draw/duplCheck")
	public void eventDrawDuplCheck(Parameter<?> parameter) throws Exception {
		String prdtNo = parameter.getString("prdtNo");
		String eventNo = parameter.getString("eventNo");

		boolean duplCheckVal = eventService.getEventDrawDuplCheck(prdtNo, eventNo);

		writeJson(parameter, duplCheckVal);

	}

}