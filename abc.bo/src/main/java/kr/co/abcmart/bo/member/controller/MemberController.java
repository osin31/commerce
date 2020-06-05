package kr.co.abcmart.bo.member.controller;

import java.util.ArrayList;
//@formatter:off
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.afterService.model.master.OcAsAcceptProduct;
import kr.co.abcmart.bo.afterService.service.AfterServiceService;
import kr.co.abcmart.bo.board.model.master.BdMemberCounsel;
import kr.co.abcmart.bo.board.model.master.BdMemberCounselMemo;
import kr.co.abcmart.bo.board.service.BdInquiryService;
import kr.co.abcmart.bo.board.vo.BdInquirySearchVO;
import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.model.master.MbMemberChangeHistory;
import kr.co.abcmart.bo.member.model.master.MbMemberCrtfcHistory;
import kr.co.abcmart.bo.member.model.master.MbMemberDeliveryAddress;
import kr.co.abcmart.bo.member.model.master.MbMemberExpostSavePoint;
import kr.co.abcmart.bo.member.model.master.MbMemberLoginHistory;
import kr.co.abcmart.bo.member.model.master.MbMemberMemo;
import kr.co.abcmart.bo.member.model.master.MbMemberPoint;
import kr.co.abcmart.bo.member.service.MemberPointService;
import kr.co.abcmart.bo.member.service.MemberService;
import kr.co.abcmart.bo.member.vo.CertificationVO;
import kr.co.abcmart.bo.member.vo.MemberHistorySearchVO;
import kr.co.abcmart.bo.member.vo.MemberSearchVO;
import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.bo.order.service.OrderService;
import kr.co.abcmart.bo.promotion.model.master.PrCoupon;
import kr.co.abcmart.bo.promotion.service.CouponService;
import kr.co.abcmart.bo.promotion.vo.PrCouponSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.bean.BaseJsonView;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.message.Message;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.interfaces.module.member.model.HistoryPoint;
import kr.co.abcmart.interfaces.module.member.model.PrivateReport;
import kr.co.abcmart.interfaces.module.member.model.StorePointHistory;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("member")
public class MemberController extends BaseController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private CouponService couponService;

	@Autowired
	private BdInquiryService bdInquiryService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private MemberPointService memberPointService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private AfterServiceService afterServiceService;

	/**
	 * @Desc : 회원 목록 화면 호출
	 * @Method Name : main
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	// @Access(menuNo="10064", successRsnText="회원관리 목록", accessType="R01")
	@RequestMapping("")
	public ModelAndView main(Parameter<?> parameter) throws Exception {
		// 맴버쉽 등급코드, 회원유형코드
		String[] codeFields = { CommonCode.MBSHP_GRADE_CODE, CommonCode.MEMBER_TYPE_CODE, CommonCode.EMAIL_SITE_CODE };
		parameter.addAttribute("codeList", memberService.getAllCommonCodeGroup(codeFields));

		return forward("/member/member-main");
	}

	/**
	 * @Desc : 환불계좌 인증
	 * @Method Name : accountAuth
	 * @Date : 2019. 4. 18.
	 * @Author : 최경호
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/account-auth")
	public void accountAuth(Parameter<MbMember> parameter) throws Exception {
		MbMember params = parameter.get();

		Map<String, Object> resultMap = memberService.getAccountAuth(params);
		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 회원 목록 호출
	 * @Method Name : readList
	 * @Date : 2019. 2. 11.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
//	@Access(menuNo="10064", successRsnText="회원 목록 데이터 조회", accessType="R02")
	@RequestMapping("/read-list")
	@ResponseBody
	public void readList(Parameter<MbMember> parameter) throws Exception {
		Pageable<MbMember, MbMember> memberVOPageble = new Pageable<>(parameter);
		Page<MbMember> page = memberService.getMemberList(memberVOPageble);

		writeJson(parameter, BaseJsonView.List.class, page.getGrid());
	}

	/**
	 * @Desc : 회원이력조회 팝업
	 * @Method Name : historyPop
	 * @Date : 2019. 2. 13.
	 * @Author : Kimyounghyun
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/history-pop")
	public ModelAndView historyPop(Parameter<?> parameter) throws Exception {
		String memberNo = parameter.getString("memberNo");
		MbMember params = new MbMember();

		params.setMemberNo(memberNo);
		parameter.addAttribute("memberNo", memberNo);

		MbMember member = memberService.getMember(params);

		String[] codeFields = { CommonCode.CNNCTR_STAT_CODE, CommonCode.CRTFC_PATH_CODE, CommonCode.DEVICE_CODE,
				CommonCode.MEMBER_TYPE_CODE, CommonCode.CRTFC_TYPE_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = memberService.getCodeListByGroupByCombo(codeFields,
				false);

		JSONObject codeCombo = pair.getFirst();
		Map<String, List<SyCodeDetail>> codeList = pair.getSecond();

		parameter.addAttribute("codeCombo", codeCombo);

		parameter.addAttribute("cnnctrStatCode", codeList.get(CommonCode.CNNCTR_STAT_CODE));
		parameter.addAttribute("crtfcPathCode", codeList.get(CommonCode.CRTFC_PATH_CODE));
		parameter.addAttribute("deviceCode", codeList.get(CommonCode.DEVICE_CODE));
		parameter.addAttribute("memberTypeCode", codeList.get(CommonCode.MEMBER_TYPE_CODE));
		parameter.addAttribute("crtfcTypeCode", codeList.get(CommonCode.CRTFC_TYPE_CODE));

		parameter.addAttribute("member", member);

		return forward("/member/member-history-pop");
	}

	/**
	 * @Desc : 로그인이력 조회
	 * @Method Name : loginHistory
	 * @Date : 2019. 2. 12.
	 * @Author : Kimyounghyun
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/login-history")
	public void loginHistory(Parameter<MemberHistorySearchVO> parameter) throws Exception {

		Pageable<MemberHistorySearchVO, MbMemberLoginHistory> pageable = new Pageable<>(parameter);
		Page<MbMemberLoginHistory> page = memberService.getLoginHistory(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 변경이력 조회
	 * @Method Name : changeHistory
	 * @Date : 2019. 2. 12.
	 * @Author : Kimyounghyun
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/change-history")
	public void changeHistory(Parameter<MemberHistorySearchVO> parameter) throws Exception {
		Pageable<MemberHistorySearchVO, MbMemberChangeHistory> pageable = new Pageable<>(parameter);
		Page<MbMemberChangeHistory> page = memberService.getChangeHistory(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 변경이력 조회가 기본젇보인지 부가정보인에 따라서 상세필드를 조회한다.
	 * @Method Name : changeHistoryFields
	 * @Date : 2019. 2. 18.
	 * @Author : Kimyounghyun
	 * @param field
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/change-history/{field}")
	public void changeHistoryFields(@PathVariable String field, Parameter<?> parameter) throws Exception {
		writeJson(parameter, memberService.getHistoryFields(field));
	}

	/**
	 * @Desc : 본인인증이력 조회
	 * @Method Name : crtfcHistory
	 * @Date : 2019. 2. 12.
	 * @Author : Kimyounghyun
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/certify-history")
	public void certifyHistory(Parameter<MemberHistorySearchVO> parameter) throws Exception {
		Pageable<MemberHistorySearchVO, MbMemberCrtfcHistory> pageable = new Pageable<>(parameter);
		Page<MbMemberCrtfcHistory> page = memberService.getCertifyHistory(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 회원 상세 팝업 호출
	 * @Method Name : memberDetailPop
	 * @Date : 2019. 2. 12.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/member-detail-pop")
	public ModelAndView memberDetailPop(Parameter<MbMember> parameter) throws Exception {
		MbMember params = parameter.get();
		parameter.addAttribute("memberNo", parameter.getString("memberNo"));
		parameter.addAttribute("memberDefaultInfo", memberService.getMember(params));

		return forward("/member/member-detail-pop");
	}

	/**
	 * @Desc : 회원정보 탭 호출.
	 * @Method Name : readMemberInfoTab
	 * @Date : 2019. 2. 12.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-member-info-tab")
	@ResponseBody
	public ModelAndView readMemberInfoTab(Parameter<MbMember> parameter) throws Exception {
		String memberNo = parameter.getString("memberNo");
		Map<String, Object> dataMap = new HashMap<String, Object>();

		// 회원 상세 관련 데이터 조회
		dataMap = memberService.getMemberWithAllHasManyNoTrx(memberNo);

		parameter.addAttribute("counselData", dataMap.get("counselData"));
		parameter.addAttribute("prodAswrData", dataMap.get("productAswrData"));
		parameter.addAttribute("codeList", dataMap.get("codeData"));
		parameter.addAttribute("memberInfo", dataMap.get("detailData"));
		parameter.addAttribute("deliveryInfo", dataMap.get("deliveryData"));
		parameter.addAttribute("memoInfo", dataMap.get("memoData"));
		parameter.addAttribute("productReviewCount", dataMap.get("productReviewCount"));
		parameter.addAttribute("pointInfo", dataMap.get("privateReport"));
		parameter.addAttribute("eventData", dataMap.get("eventData"));
		parameter.addAttribute("ifResut", dataMap.get("ifResut"));
		parameter.addAttribute("empInfo", dataMap.get("memberEmpInfo"));

		return forward("/member/read-member-info-tab");
	}

	/**
	 * @Desc : 주문내역 탭 호출.
	 * @Method Name : readOrderInfoTab
	 * @Date : 2019. 2. 12.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-order-info-tab")
	@ResponseBody
	public ModelAndView readOrderInfoTab(Parameter<MbMember> parameter) throws Exception {
		orderService.getOrderInfoMain(parameter);
		return forward("/member/read-order-info-tab");
	}

	/**
	 * @Desc : 문의내역 탭 호출.
	 * @Method Name : readInquiryInfoTab
	 * @Date : 2019. 2. 12.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-inquiry-info-tab")
	@ResponseBody
	public ModelAndView readInquiryInfoTab(Parameter<?> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String memberNo = parameter.getString("memberNo");

		dataMap = memberService.getInquiryData(memberNo);

		parameter.addAttribute("memberNo", memberNo);
		parameter.addAttribute("codeCombo", dataMap.get("codeCombo"));
		parameter.addAttribute("codeList", dataMap.get("codeList"));
		parameter.addAttribute("siteInfo", dataMap.get("siteInfo"));
		parameter.addAttribute("inquiryData", dataMap.get("inquiryData"));

		return forward("/member/read-inquiry-info-tab");
	}

	/**
	 * @Desc : 상품Q&A 탭 호출.
	 * @Method Name : readProdInquiryInfoTab
	 * @Date : 2019. 2. 12.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-prod-inquiry-info-tab")
	@ResponseBody
	public ModelAndView readProdInquiryInfoTab(Parameter<MbMember> parameter) throws Exception {

		// 후기 작성 포인트 지급 현황 조회
		parameter.addAttribute("inquiryStats",
				memberService.getMemberProductInquiryStats(parameter.getString("memberNo")));
		// 답변상태코드
		String[] codeFields = { CommonCode.ASWR_STAT_CODE, CommonCode.CNSL_TYPE_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = memberService.getCodeListByGroupByCombo(codeFields,
				false);

		JSONObject codeCombo = pair.getFirst();
		parameter.addAttribute("codeCombo", codeCombo);
		parameter.addAttribute("memberNo", parameter.getString("memberNo"));

		List<SyCodeDetail> codeSearchCondition = new ArrayList<SyCodeDetail>();

		// 문의유형 코드
		SyCodeDetail syCodeDetail = new SyCodeDetail();
		syCodeDetail.setCodeField(CommonCode.CNSL_TYPE_CODE);
		codeSearchCondition.add(syCodeDetail);

		// 문의구분 코드
		syCodeDetail = new SyCodeDetail();
		syCodeDetail.setCodeField(CommonCode.CNSL_TYPE_DTL_CODE);
		syCodeDetail.setAddInfo1(CommonCode.CNSL_TYPE_CODE_PRODUCT_INFO);
		codeSearchCondition.add(syCodeDetail);

		Map<String, List<SyCodeDetail>> codeListMap = commonCodeService.getCodeListByGroup(codeSearchCondition);

		parameter.addAttribute("cnslTypeCode", codeListMap.get(CommonCode.CNSL_TYPE_CODE)); // 문의유형
		parameter.addAttribute("cnslTypeDtlCode", codeListMap.get(CommonCode.CNSL_TYPE_DTL_CODE)); // 문의분류
		parameter.addAttribute("aswrStatCode", pair.getSecond().get(CommonCode.ASWR_STAT_CODE));// 답변상태코드

		return forward("/member/read-prod-inquiry-info-tab");
	}

	/**
	 * @Desc : 상품후기 탭 호출.
	 * @Method Name : readProdReviewInfoTab
	 * @Date : 2019. 2. 12.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-prod-review-info-tab")
	@ResponseBody
	public ModelAndView readProdReviewInfoTab(Parameter<MbMember> parameter) throws Exception {

		// 후기 작성 포인트 지급 현황 조회
		parameter.addAttribute("reviewStats", memberService.getMemberReviewPointStats(parameter.getString("memberNo")));
		// 답변상태코드
		String[] codeFields = { CommonCode.ASWR_STAT_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = memberService.getCodeListByGroupByCombo(codeFields,
				false);

		JSONObject codeCombo = pair.getFirst();
		parameter.addAttribute("codeCombo", codeCombo);
		parameter.addAttribute("memberNo", parameter.getString("memberNo"));

		return forward("/member/read-prod-review-info-tab");
	}

	/**
	 * @Desc : 쿠폰관리 탭 호출.
	 * @Method Name : readCouponInfoTab
	 * @Date : 2019. 2. 12.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/read-coupon-info-tab")
	public ModelAndView readCouponInfoTab(Parameter<MbMember> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String memberNo = parameter.getString("memberNo");

		dataMap = memberService.getMemberCouponData(memberNo);

		parameter.addAttribute("codeCombo", dataMap.get("codeCombo"));
		parameter.addAttribute("codeList", dataMap.get("codeList"));
		parameter.addAttribute("couponData", dataMap.get("couponData"));
		parameter.addAttribute("chanList", dataMap.get("chanList"));
		parameter.addAttribute("memberNo", memberNo);
		
		// 2020.05.13 : 쿠폰지급 버튼 노출여부를 따지기 위해 set
		parameter.addAttribute("noCpnBtnLoinId", LoginManager.getUserDetails().getLoginId());

		return forward("/member/read-coupon-info-tab");
	}

	/**
	 * @Desc : 회원 쿠폰 리스트 조회
	 * @Method Name : readMemberCouponList
	 * @Date : 2019. 2. 26.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-member-coupon-list")
	public void readMemberCouponList(Parameter<PrCouponSearchVO> parameter) throws Exception {
		Pageable<PrCouponSearchVO, PrCoupon> memberCouponVOPageble = new Pageable<>(parameter);
		Page<PrCoupon> page = couponService.getMemberCouponList(memberCouponVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 포인트관리 탭 호출.
	 * @Method Name : readPointInfoTab
	 * @Date : 2019. 2. 12.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-point-info-tab")
	@ResponseBody
	public ModelAndView readPointInfoTab(Parameter<MbMember> parameter) throws Exception {
		String memberNo = parameter.getString("memberNo");
		String safeKey = parameter.getString("safeKey");
		String safeKeySeq = parameter.getString("safeKeySeq");
		String memberTypeCode = parameter.getString("memberTypeCode");

		PrivateReport privateReport = new PrivateReport();

		if (UtilsText.isNotBlank(safeKey) && UtilsText.equals(memberTypeCode, CommonCode.MEMBER_TYPE_MEMBERSHIP)) {
			try {
				privateReport = memberPointService.getPrivateReportBySafeKey(safeKey, safeKeySeq);
			} catch (Exception e) {
				throw new Exception(Message.getMessage("system.error.interface"));
			}
		}

		parameter.addAttribute("memberNo", memberNo);
		parameter.addAttribute("memberTypeCode", memberTypeCode);
		parameter.addAttribute("safeKey", safeKey);
		parameter.addAttribute("safeKeySeq", safeKeySeq);
		parameter.addAttribute("pointInfo", privateReport);
		
		//권한에 따라 포인트 적립/차감 버튼 생성여부 판단위해 권한 내려줌
		boolean pointBtnView = !parameter.hasRole("ROLE_20004");
		parameter.addAttribute("pointBtnView", pointBtnView);
		
		return forward("/member/read-point-info-tab");
	}

	/**
	 * @Desc : 이벤트내역 탭 호출.
	 * @Method Name : readEventInfoTab
	 * @Date : 2019. 2. 12.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-event-info-tab")
	@ResponseBody
	public ModelAndView readEventInfoTab(Parameter<MbMember> parameter) throws Exception {
		List<SySiteChnnl> channelList = siteService.getUseChannelList();
		List<SyCodeDetail> eventTypeCodeList = commonCodeService.getCodeNoName(CommonCode.EVENT_TYPE_CODE);

		parameter.addAttribute("channelList", channelList);
		parameter.addAttribute("eventTypeCodeList", eventTypeCodeList);
		parameter.addAttribute("memberNo", parameter.getString("memberNo"));

		return forward("/member/read-event-info-tab");
	}

	/**
	 * @Desc : 회원 정보 수정
	 * @Method Name : updateMember
	 * @Date : 2019. 2. 21.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/update-member")
	@ResponseBody
	public void updateMember(Parameter<?> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		MbMember params = parameter.create(MbMember.class, Arrays.asList("blackListYn"));
		MbMemberDeliveryAddress mbMemberDeliveryAddress = parameter.create(MbMemberDeliveryAddress.class);
		params.setMbMemberDeliveryAddress(mbMemberDeliveryAddress);

		resultMap = memberService.updateMember(params);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 회원 비밀번호 초기화
	 * @Method Name : updatePswdReset
	 * @Date : 2019. 2. 20.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/update-pswd-reset")
	@ResponseBody
	public void updatePswdReset(Parameter<MbMember> parameter) throws Exception {
		MbMember params = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = memberService.updatePswdReset(params);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 회원 포인트 비밀번호 초기화
	 * @Method Name : updatePointPswdReset
	 * @Date : 2019. 6. 19.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/update-point-pswd-reset")
	@ResponseBody
	public void updatePointPswdReset(Parameter<MbMember> parameter) throws Exception {
		MbMember params = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = memberService.updatePointPswdReset(params);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 회원 블랙리스트 정보 수정
	 * @Method Name : updateMember
	 * @Date : 2019. 2. 19.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/update-member-blacklist")
	@ResponseBody
	public void updateMemberBlackList(Parameter<MbMember> parameter) throws Exception {
		MbMember params = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = memberService.updateMemberBlackList(params);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 회원 관리자 메모 등록
	 * @Method Name : createMemberAdminMemo
	 * @Date : 2019. 2. 19.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/create-member-memo")
	@ResponseBody
	public void createMemberMemo(Parameter<MbMemberMemo> parameter) throws Exception {
		MbMemberMemo params = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = memberService.setMemberMemo(params);

		parameter.addAttribute("memoInfo", resultMap);
		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 회원 관리자 메모 삭제
	 * @Method Name : deleteMemberAdminMemo
	 * @Date : 2019. 2. 19.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/delete-member-memo")
	@ResponseBody
	public void deleteMemberMemo(Parameter<MbMemberMemo> parameter) throws Exception {
		MbMemberMemo params = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = memberService.deleteMemberMemo(params);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 회원 배송지 목록 팝업 호출
	 * @Method Name : memberDeliveryPop
	 * @Date : 2019. 2. 23.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/member-delivery-pop")
	public ModelAndView memberDeliveryPop(Parameter<MbMember> parameter) throws Exception {
		MbMember member = memberService.getMemberWithAddInfo(parameter.getString("memberNo"));
		parameter.addAttribute("member", member);

		return forward("/member/member-delivery-pop");
	}

	/**
	 * @Desc : 회원 배송지 목록 조회
	 * @Method Name : readMemberDeliveryList
	 * @Date : 2019. 2. 23.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-member-delivery-list")
	public void readMemberDeliveryList(Parameter<MbMemberDeliveryAddress> parameter) throws Exception {
		Pageable<MbMemberDeliveryAddress, MbMemberDeliveryAddress> deliveryVOPageble = new Pageable<>(parameter);
		Page<MbMemberDeliveryAddress> page = memberService.getMemberDeliveryList(deliveryVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 회원 유선상담 등록/상세 팝업 호출
	 * @Method Name : memberCounselPop
	 * @Date : 2019. 3. 3.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/member-counsel-pop")
	public ModelAndView memberCounselPop(Parameter<BdMemberCounsel> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		BdMemberCounsel params = parameter.get();

		resultMap = memberService.getMemberCouselInfo(params);

		OcOrder ocOrder = new OcOrder();
		ocOrder.setMemberNo(params.getMemberNo());
		OcAsAcceptProduct ocAsAcceptProduct = new OcAsAcceptProduct();
		ocAsAcceptProduct.setMemberNo(params.getMemberNo());

		List<OcOrder> orderList = orderService.selectOrderMstList(ocOrder);
		List<OcAsAcceptProduct> asList = afterServiceService.getAsListByMemberNo(ocAsAcceptProduct);

		parameter.addAttribute("viewType", parameter.getString("viewType"));
		parameter.addAttribute("codeList", resultMap.get("codeList"));
		parameter.addAttribute("detailInfo", resultMap.get("detailInfo"));
		parameter.addAttribute("aswrCounselAttachFiles", resultMap.get("fileInfo"));
		parameter.addAttribute("bdMemberCounselMemo", resultMap.get("memoInfo"));
		parameter.addAttribute("siteInfo", resultMap.get("siteInfo"));
		parameter.addAttribute("memberInfo", resultMap.get("memberInfo"));
		parameter.addAttribute("orderList", orderList);
		parameter.addAttribute("asList", asList);

		return forward("/member/member-counsel-pop");
	}

	/**
	 * @Desc : 회원 유선상담 등록
	 * @Method Name : createMemberCounsel
	 * @Date : 2019. 3. 4.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/create-member-counsel")
	public void createMemberCounsel(Parameter<BdMemberCounsel> parameter) throws Exception {
		BdMemberCounsel params = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = memberService.setMemberCounsel(params);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 회원 유선상담 수정
	 * @Method Name : createMemberCounsel
	 * @Date : 2019. 3. 4.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/update-member-counsel")
	public void updateMemberCounsel(Parameter<BdMemberCounsel> parameter) throws Exception {
		BdMemberCounsel params = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = memberService.setMemberCounsel(params);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 유선상담 관리자 메모등록
	 * @Method Name : createMemberAdminMemo
	 * @Date : 2019. 3. 5.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/create-member-admin-memo")
	public void createMemberAdminMemo(Parameter<BdMemberCounselMemo> parameter) throws Exception {
		BdMemberCounselMemo bdMemberCounselMemo = parameter.get();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			bdMemberCounselMemo.validate();
			BdMemberCounselMemo resultVO = bdInquiryService.setAdminMemo(bdMemberCounselMemo);

			resultMap.put("code", Const.BOOLEAN_TRUE);
			resultMap.put("memberCnslSeq", resultVO.getMemberCnslSeq());
			resultMap.put("cnslMemoSeq", resultVO.getCnslMemoSeq());
			resultMap.put("rgsterDpName", resultVO.getRgsterDpName());
			resultMap.put("rgstDtm", resultVO.getRgstDtm());
			resultMap.put("memoText", resultVO.getMemoText());
		} catch (Exception e) {
			resultMap.put("code", Const.RESULT_FAIL);
			resultMap.put("Message", e.toString());
		}
		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 유선상담 관리자 메모 삭제
	 * @Method Name : removeMemberAdminMemo
	 * @Date : 2019. 3. 5.
	 * @Author : 3TOP
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/remove-member-admin-memo")
	public void removeMemberAdminMemo(Parameter<BdMemberCounselMemo> parameter) throws Exception {
		BdMemberCounselMemo bdMemberCounselMemo = parameter.get();

		bdInquiryService.deleteAdminMemo(bdMemberCounselMemo);
	}

	/**
	 * @Desc : 회원 포인트 목록 조회
	 * @Method Name : readMemberPointList
	 * @Date : 2019. 3. 5.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-member-point-list")
	public void readMemberPointList(Parameter<MemberSearchVO> parameter) throws Exception {
		Pageable<MemberSearchVO, StorePointHistory> poiontVOPageble = new Pageable<>(parameter);
		Page<StorePointHistory> page = memberService.getMemberPointList(poiontVOPageble);

		writeJson(parameter, page.getGrid());
	}
	
	/**
	 * @Desc      	: 과거 포인트 이력 조회
	 * @Method Name : readHistoryPointList
	 * @Date  	  	: 2020. 5. 27.
	 * @Author    	: 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-history-point-list")
	public void readHistoryPointList(Parameter<MemberSearchVO> parameter) throws Exception {
		Pageable<MemberSearchVO, HistoryPoint> poiontVOPageble = new Pageable<>(parameter);
		Page<HistoryPoint> page = memberService.getHistoryPoint(poiontVOPageble);

		writeJson(parameter, page.getGrid());
	}
	
	/**
	 * @Desc : 온라인회원 구매, 비회원 구매내역 조회
	 * @Method Name : readMemberPurchaseList
	 * @Date : 2019. 7. 3.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-member-purchase-list")
	public void readMemberPurchaseList(Parameter<OcOrder> parameter) throws Exception {
		Pageable<OcOrder, OcOrder> purchaseVOPageble = new Pageable<>(parameter);
		Page<OcOrder> page = memberService.getMemberPurchaseList(purchaseVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 회원 포인트 적립/사용 팝업 호출
	 * @Method Name : memberPointSavePop
	 * @Date : 2019. 3. 6.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/member-point-save-pop")
	public ModelAndView memberPointSavePop(Parameter<?> parameter) throws Exception {
		MbMember member = memberService.getMemberWithAddInfo(parameter.getString("memberNo"));
		parameter.addAttribute("member", member);

		return forward("/member/member-point-save-pop");
	}

	/**
	 * @Desc : 회원 포인트 적립/차감
	 * @Method Name : updateMemberSavePoint
	 * @Date : 2019. 4. 24.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/update-member-save-point")
	public void updateMemberSavePoint(Parameter<MbMemberPoint> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		MbMemberPoint mbMemberPoint = parameter.get();

		if (UtilsText.equals(mbMemberPoint.getValidateCode(), "0")) {
			// 포인트 적립
			resultMap = memberPointService.setMemberSavePoint(mbMemberPoint);
		} else {
			// 기타 포인트 적립
			resultMap = memberPointService.setMemberSaveEtcPoint(mbMemberPoint);
		}
		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 회원 사후적립 팝업 호출
	 * @Method Name : memberPointExpostPop
	 * @Date : 2019. 3. 6.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/member-point-expost-pop")
	public ModelAndView memberPointExpostPop(Parameter<?> parameter) throws Exception {
		MbMember member = memberService.getMemberWithAddInfo(parameter.getString("memberNo"));
		parameter.addAttribute("member", member);

		return forward("/member/member-point-expost-pop");
	}

	/**
	 * @Desc : 회원 포인트 사후 적립
	 * @Method Name : memberCreateLatedSavePoint
	 * @Date : 2019. 7. 9.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/member-create-expost-point")
	public void memberCreateLatedSavePoint(Parameter<MbMemberExpostSavePoint> parameter) throws Exception {
		MbMemberExpostSavePoint params = parameter.get();
		Map<String, Object> map = memberPointService.insertLatedSavePoint(params);

		writeJson(parameter, map);
	}

	/**
	 * @Desc : 회원 인증번호 발송
	 * @Method Name : createCertNumberHdphn
	 * @Date : 2019. 5. 9.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/create-cert-number-hdphn")
	public void createCertNumberHdphn(Parameter<CertificationVO> parameter) throws Exception {
		Map<String, Object> map = new HashMap<>();
		CertificationVO params = parameter.get();
		String certNum = memberService.createCertNumberHdphn(params);

		map.put("certNum", certNum);
		writeJson(parameter, map);
	}
	// @formatter:off

	/**
	 * @Desc : 인증번호 확인 및 히스토리 업데이트
	 * @Method Name : setValidateCertificationNumber
	 * @Date : 2019. 5. 9.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/validate-certification-number")
	public void setValidateCertificationNumber(Parameter<CertificationVO> parameter) throws Exception {
		CertificationVO params = parameter.get();

		memberService.setValidateCertificationNumber(params);

	}

	/******************************************************
	 * S : 문의 내역 탭
	 ****************************************************/
	/**
	 * @Desc : 검색 조건에 맞는 1:1문의 정보를 조회한다.
	 * @Method Name : readInquieryList
	 * @Date : 2019. 2. 11.
	 * @Author : 신인철
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/board/inquiry/read-list")
	public void readInquieryList(Parameter<BdInquirySearchVO> parameter) throws Exception {
		Pageable<BdInquirySearchVO, BdMemberCounsel> inquirySearchVO = new Pageable<>(parameter);
		try {
			Page<BdMemberCounsel> page = bdInquiryService.getInquiryReadList(inquirySearchVO);

			writeJson(parameter, page.getGrid());
		} catch (Exception e) {
			log.error(e.toString());
		}

	}

	/******************************************************
	 * E : 문의 내역 탭
	 ****************************************************/

	/**
	 * @Desc : 임직원 인증 오류횟수 초기화
	 * @Method Name : setEmpCertReset
	 * @Date : 2019. 8. 27.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/update-emp-cert-reset")
	public void setEmpCertReset(Parameter<MbMember> parameter) throws Exception {
		MbMember params = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = memberService.setEmpCertReset(params);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 회원탈퇴
	 * @Method Name : leaveProcess
	 * @Date : 2020. 3. 13.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/leave-process")
	public void leaveProcess(Parameter<MbMember> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		MbMember mbMember = parameter.get();
		resultMap = memberService.setLeaveProcess(mbMember);

		writeJson(parameter, resultMap);
	}

}
