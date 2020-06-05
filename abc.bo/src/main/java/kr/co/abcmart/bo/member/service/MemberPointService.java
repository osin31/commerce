package kr.co.abcmart.bo.member.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import kr.co.abcmart.bo.cmm.model.master.CmOnlineMemberPolicy;
import kr.co.abcmart.bo.cmm.service.OnlinePolicyService;
import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.model.master.MbMemberExpostSavePoint;
import kr.co.abcmart.bo.member.model.master.MbMemberPoint;
import kr.co.abcmart.bo.member.repository.master.MbMemberExpostSavePointDao;
import kr.co.abcmart.bo.member.repository.master.MbMemberPointDao;
import kr.co.abcmart.bo.member.vo.MemberShipErrorCode;
import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.bo.order.service.OrderOtherPartService;
import kr.co.abcmart.bo.order.vo.OrderOtherPartVo;
import kr.co.abcmart.common.constant.BaseConst;
import kr.co.abcmart.common.message.Message;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.interfaces.module.member.MembershipPointService;
import kr.co.abcmart.interfaces.module.member.model.BuyFixProduct;
import kr.co.abcmart.interfaces.module.member.model.PointUse;
import kr.co.abcmart.interfaces.module.member.model.PrivateReport;
import kr.co.abcmart.interfaces.module.member.utils.MemberShipProcessException;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsDate;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberPointService {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberPointService memberPointService;

	@Autowired
	private OnlinePolicyService onlinePolicyService;

	@Autowired
	private MembershipPointService membershipPointService;

	@Autowired
	private OrderOtherPartService orderOtherPartService;

	@Autowired
	private MbMemberExpostSavePointDao mbMemberExpostSavePointDao;

	@Autowired
	private MbMemberPointDao mbMemberPointDao;

	/**
	 * @Desc : 일반/포토 후기작성 시 포인트 지급
	 * @Method Name : updateMemberUserPointProcess
	 * @Date : 2019. 5. 2.
	 * @Author : 이동엽
	 * @param memberNo
	 * @param reviewType
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateMemberUserPointProvide(String memberNo, String reviewType) throws Exception {
		Map<String, Object> resultMap = new ModelMap();
		MbMemberPoint params = new MbMemberPoint();

		int point = 0;
		String returnMessage = "";

		// 회원정보를 조회하여 SafeKey를 세팅
		MbMember mbMember = memberService.getMember(memberNo);
		if (mbMember == null) {
			returnMessage = "회원정보가 존재하지 않습니다.";
			resultMap.put("result", Const.IF_RESULT_FAIL);
			resultMap.put("returnMessage", returnMessage);
			return resultMap;
		}

		// 현재 시행중인 회원 혜택을 조회
		CmOnlineMemberPolicy cmOnlineMemberPolicy = onlinePolicyService.getMembershipData();

		// 포트후기 여부에 따른 포인트 세팅
		if (cmOnlineMemberPolicy != null) {
			if (UtilsText.equals(reviewType, "G")) {
				point = cmOnlineMemberPolicy.getPrdtRvwPointAmt();
				params.setPoint(cmOnlineMemberPolicy.getPrdtRvwPointAmt());
			} else {
				point = cmOnlineMemberPolicy.getPhotoPrdtRvwPointAmt();
				params.setPoint(cmOnlineMemberPolicy.getPhotoPrdtRvwPointAmt());
			}
		}

		params.setMemberNo(memberNo);
		params.setEtcSavedCode("04");
		params.setSaveTypeCode(CommonCode.SAVE_TYPE_CODE_EARN_PRODUCTREVIEW);
		params.setSaveUseContText(Const.SAVE_TYPE_CODE_EARN_PRODUCTREVIEW_NAME);

		// 인터페이스 호출
		try {
			resultMap = memberPointService.registUserEtcPoint(params);

			if ((boolean) resultMap.get("code")) {
				resultMap.put("plcySeq", cmOnlineMemberPolicy.getPlcySeq());
				resultMap.put("point", point);
				resultMap.put("result", Const.IF_RESULT_SUCCESS);
				resultMap.put("returnMessage", "정상 지급 처리되었습니다.");
			} else {
				resultMap.put("point", point);
				resultMap.put("result", Const.IF_RESULT_FAIL);
				resultMap.put("returnMessage", "정상 지급 처리되지 않았습니다.");
			}

		} catch (MemberShipProcessException e) {
			resultMap.put("result", Const.IF_RESULT_FAIL);
			resultMap.put("returnMessage", "I/F 통신간 문제가 발생되었습니다. 관리자에게 문의하세요.");
		} catch (Exception e) {
			resultMap.put("result", Const.IF_RESULT_FAIL);
			resultMap.put("returnMessage", "I/F 통신간 문제가 발생되었습니다. 관리자에게 문의하세요.");
		}

		return resultMap;
	}

	/**
	 * @Desc : 일반/포토 후기작성 시 포인트 취소(차감/사용)
	 * @Method Name : updateMemberUserPointDeduct
	 * @Date : 2019. 5. 2.
	 * @Author : 3TOP
	 * @param memberNo
	 * @param point
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateMemberUserPointDeduct(String memberNo, int point) throws Exception {
		Map<String, Object> resultMap = new ModelMap();
		MbMemberPoint params = new MbMemberPoint();

		String returnMessage = "";

		// 회원정보를 조회하여 SafeKey를 세팅
		MbMember mbMember = memberService.getMember(memberNo);
		if (mbMember == null) {
			returnMessage = "회원정보가 존재하지 않습니다.";
			resultMap.put("result", Const.IF_RESULT_FAIL);
			resultMap.put("returnMessage", returnMessage);
			return resultMap;
		}

		params.setMemberNo(memberNo);
		params.setEtcSavedCode("04");
		params.setPoint(point);
		params.setSaveTypeCode(CommonCode.SAVE_TYPE_CODE_DEDUCTION_ETC);
		params.setSaveUseContText("후기 작성 포인트 지급 취소");
		params.setPointType(Const.POINT_TYPE_USE);

		// 인터페이스 호출
		try {
			resultMap = memberPointService.registUserEtcPoint(params);

			if ((boolean) resultMap.get("code")) {
				resultMap.put("point", point);
				resultMap.put("result", Const.IF_RESULT_SUCCESS);
				resultMap.put("returnMessage", "정상 차감 처리되었습니다.");
			} else {
				resultMap.put("point", point);
				resultMap.put("result", Const.IF_RESULT_FAIL);
				resultMap.put("returnMessage", "정상 차감 처리되지 않았습니다.");
			}

		} catch (MemberShipProcessException e) {
			resultMap.put("result", Const.IF_RESULT_FAIL);
			resultMap.put("returnMessage", "I/F 통신간 문제가 발생되었습니다. 관리자에게 문의하세요.");
		} catch (Exception e) {
			resultMap.put("result", Const.IF_RESULT_FAIL);
			resultMap.put("returnMessage", "I/F 통신간 문제가 발생되었습니다. 관리자에게 문의하세요.");
		}

		return resultMap;
	}

	/**
	 * @Desc : 1개월간 사후적립 신청 카운트
	 * @Method Name : getLatedSavePointCount
	 * @Date : 2019. 7. 8.
	 * @Author : 이동엽
	 * @param params
	 * @return
	 */
	public int getLatedSavePointRecent1MonthCount(MbMemberExpostSavePoint params) throws Exception {
		return mbMemberExpostSavePointDao.getLatedSavePointRecent1MonthCount(params);
	}

	/**
	 * @Desc : 회원 포인트 사후적립
	 * @Method Name : insertLatedSavePoint
	 * @Date : 2019. 7. 9.
	 * @Author : 이동엽
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> insertLatedSavePoint(MbMemberExpostSavePoint params) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 월 10회미만 체크
		int count = memberPointService.getLatedSavePointRecent1MonthCount(params);
		if (count >= 10) {
			resultMap.put("resultCode", Const.BOOLEAN_FALSE);
			resultMap.put("resultMsg", Message.getMessage("member.error.latedSavePoint10"));
		} else {
			/** 온라인,오프라인 사후 적립 **/
			if (UtilsText.equals(params.getOnlnBuyYn(), Const.BOOLEAN_TRUE)) {
				resultMap = memberPointService.saveLatedSavePointForOnlineOrder(params);
			} else if (UtilsText.equals(params.getOnlnBuyYn(), Const.BOOLEAN_FALSE)) {
				resultMap = memberPointService.savePossibleLatedSavePointForOfflineOrder(params);
			} else {
				resultMap.put("resultCode", Const.BOOLEAN_FALSE);
				resultMap.put("resultMsg", Message.getMessage("member.error.wrongApproach"));
			}
		}

		return resultMap;
	}

	/**
	 * @Desc : 온라인(온라인, 비회원) 사후적립
	 * @Method Name : saveLatedSavePointForOnlineOrder
	 * @Date : 2019. 7. 8.
	 * @Author : 3TOP
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> saveLatedSavePointForOnlineOrder(MbMemberExpostSavePoint params) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		MbMember memberInfo = null;
		String storeName = "";
		OrderOtherPartVo orderOtherPartVo = null;

		resultMap.put("resultCode", Const.BOOLEAN_TRUE);
		resultMap.put("resultMsg", Message.getMessage("member.msg.latedSavePoint"));

		try {
			memberInfo = memberService.getMember(params.getMemberNo());

			OcOrder ocOrder = new OcOrder();

			if (UtilsText.equals(CommonCode.MEMBER_TYPE_CODE_NONMEMBER, params.getMemberTypeCode())) {
				ocOrder.setMemberNo(Const.NON_MEMBER_NO);
			} else {
				ocOrder.setMemberNo(params.getMemberNo());
			}
			ocOrder.setOrderNo(params.getBuyNoText());
			orderOtherPartVo = orderOtherPartService.getOverDayYnAfterBuyDecision(ocOrder);

			/********** 1. 온라인 사후적립 가능 확인 **********/
			Map<String, Object> result = memberPointService.checkPossibleLatedSavePoint(params, memberInfo,
					orderOtherPartVo);
			if (UtilsText.equals(String.valueOf(result.get("resultCode")), Const.BOOLEAN_FALSE)) {
				return result;
			}

			/********** 2. ENCODE ORDER_NUM GET **********/
			if (UtilsText.isBlank(params.getCrtfcNoText())) {
				params.setCrtfcNoText(orderOtherPartVo.getCrtfcNoText());
			}

			/********** 3. PROCEDURE_DECODE CALL **********/
			log.error("MemberNo => {}", params.getMemberNo());
			log.error("BuyNoText => {}", params.getBuyNoText());
			log.error("CrtfcNoText => {}", params.getCrtfcNoText());
			String procResult = orderOtherPartService.callProcedureForDecodeOrderNum("on", params.getBuyNoText(),
					params.getCrtfcNoText());
			log.error("procResult : {}", procResult);
			if (!UtilsText.equals(procResult, "1")) {
				resultMap.put("resultCode", Const.BOOLEAN_FALSE);
				resultMap.put("resultMsg", Message.getMessage("member.error.dealCont"));
				return resultMap;
			}

			/********** 4. 멤버십 포인트 적립 통신[memA890a] **********/
//			OcOrderProduct orderProductParams = new OcOrderProduct();
//			orderProductParams.setOrderNo(params.getBuyNoText());
//			List<OcOrderProduct> orderProductList = orderOtherPartService.getOrderProductInfoList(orderProductParams);
//			// 자사 상품 포함여부
//			boolean includedMmnyPrdtY = orderProductList.stream().map(OcOrderProduct::getMmnyPrdtYn)
//					.anyMatch(x -> UtilsText.equals(x, Const.BOOLEAN_TRUE));
//			log.debug("자사 상품 포함여부  : {}", includedMmnyPrdtY);
//
//			// 입점사 상품 포함여부
//			boolean includedMmnyPrdtN = orderProductList.stream().map(OcOrderProduct::getMmnyPrdtYn)
//					.anyMatch(x -> UtilsText.equals(x, Const.BOOLEAN_FALSE));
//			log.debug("입점사 상품 포함여부  : {}", includedMmnyPrdtN);

			// TODO 유성민멤버십 포인트 적립 통신 자사, 입점 구분필요
			List<BuyFixProduct> products = null;
//			if (includedMmnyPrdtY) {

			log.error("safeKey => {}", memberInfo.getSafeKey());
			log.error("SafeKeySeq => {}", memberInfo.getSafeKeySeq());

			products = membershipPointService.buyFixRequest(memberInfo.getSafeKey(), memberInfo.getSafeKeySeq(),
					params.getBuyNoText());
//			}
			log.error("products Info => {}", products);

//			if (includedMmnyPrdtN) {

//			}

			if (products.isEmpty()) {
				resultMap.put("resultCode", Const.BOOLEAN_FALSE);
				resultMap.put("resultMsg", Message.getMessage("member.error.memberShipProcess"));
				return resultMap;
			}

			/********** 5. 우리쪽 DB에 완료데이터 저장 **********/
			// MB_MEMBER_EXPOST_SAVE_POINT
			int expostSavePointSeq = memberPointService.selectExpostSavePointSeqNextVal(params);
			params.setAdminRgstYn(Const.BOOLEAN_TRUE);
			params.setExpostSavePointSeq(expostSavePointSeq);
			memberPointService.insertExpostSavePoint(params);

			// OC_ORDER update
			OcOrder ocOrderParma = new OcOrder();
			ocOrderParma.setMemberNo(params.getMemberNo());
			ocOrderParma.setOrderNo(params.getBuyNoText());
			ocOrderParma.setModerNo(params.getMemberNo());
			orderOtherPartService.updatePointSaveYn(ocOrderParma);

		} catch (MemberShipProcessException e) {
			resultMap.put("resultCode", Const.BOOLEAN_FALSE);
			resultMap.put("resultMsg", Message.getMessage("member.error.memberShipProcess"));
		} catch (Exception e) {
			resultMap.put("resultCode", Const.BOOLEAN_FALSE);
			resultMap.put("resultMsg", Message.getMessage("member.error.latedSavePoint"));
		}

		return resultMap;
	}

	/**
	 * @Desc :오프라인 사후적립
	 * @Method Name : savePossibleLatedSavePointForOfflineOrder
	 * @Date : 2019. 3. 28.
	 * @Author : 유성민
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> savePossibleLatedSavePointForOfflineOrder(MbMemberExpostSavePoint params)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		MbMember memberInfo = null;
		resultMap.put("resultCode", Const.BOOLEAN_TRUE);
		resultMap.put("resultMsg", Message.getMessage("member.msg.latedSavePoint"));

		try {
			memberInfo = memberService.getMember(params.getMemberNo());
			if (!UtilsText.equals(memberInfo.getMemberTypeCode(), CommonCode.MEMBER_TYPE_CODE_MEMBERSHIP)) {
				resultMap.put("resultCode", Const.BOOLEAN_FALSE);
				resultMap.put("resultMsg", Message.getMessage("member.error.notMembershipUser"));
				return resultMap;
			}

			/********** 1. 결제일로부터 (policy)일 이내 (관리자 채널이 아닐때만) **********/
			if (UtilsText.equals(params.getAdminRgstYn(), Const.BOOLEAN_FALSE)) {

				LocalDate saleDateDayAdd = LocalDate.from(params.getBuyYmd().toLocalDateTime()).plusDays(30);
				LocalDate today = LocalDate.now();

				if (saleDateDayAdd.isBefore(today)) {
					resultMap.put("resultCode", Const.BOOLEAN_FALSE);
					resultMap.put("resultMsg", Message.getMessage("member.error.saveDateExpr"));
					return resultMap;
				}
			}

			/********** 2. PROCEDURE_DECODE CALL **********/
			String procResult = orderOtherPartService.callProcedureForDecodeOrderNum("off", params.getBuyNoText(),
					params.getCrtfcNoText());
			log.error("procResult : {}", procResult);

			if (!UtilsText.equals(procResult, "1")) {
				resultMap.put("resultCode", Const.BOOLEAN_FALSE);
				resultMap.put("resultMsg", Message.getMessage("member.error.dealCont"));
				return resultMap;
			}

			/********** 3. 멤버십 포인트 부여 API [memB300 통신] **********/
			String safeKey = memberInfo.getSafeKey();
			String safeKeySeq = UtilsText.isNotBlank(memberInfo.getSafeKeySeq()) ? memberInfo.getSafeKeySeq() : "01";
			String saleDate = UtilsDate.formatter(Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS, params.getBuyYmd());
			String storeCd = params.getStoreNo();
			String posNo = params.getBuyNoText().substring(0, 4);
			String dealNo = params.getBuyNoText().substring(4, 8);
			int price = params.getPymntAmt();

			String failCode = membershipPointService.updatePointAfterPurchase(safeKey, safeKeySeq, saleDate, storeCd,
					posNo, dealNo, price);

			log.error("membershipPointService.updatePointAfterPurchase : failCode", failCode);

			if (!UtilsText.equals("00000000", failCode)) {
				resultMap.put("resultCode", Const.BOOLEAN_FALSE);
				resultMap.put("resultMsg", MemberShipErrorCode.getByCode(failCode).getDesc());
				return resultMap;
			}

			/********** 4. 우리쪽 DB에 완료데이터 저장 **********/
			// MB_MEMBER_EXPOST_SAVE_POINT
			int expostSavePointSeq = memberPointService.selectExpostSavePointSeqNextVal(params);
			params.setExpostSavePointSeq(expostSavePointSeq);
			params.setAdminRgstYn(Const.BOOLEAN_TRUE);
			memberPointService.insertExpostSavePoint(params);

			log.error("membershipPointService.updatePointAfterPurchase : success");

		} catch (MemberShipProcessException e) {
			resultMap.put("resultCode", Const.BOOLEAN_FALSE);
			resultMap.put("resultMsg", Message.getMessage("member.error.memberShipProcess"));
		} catch (Exception e) {
			resultMap.put("resultCode", Const.BOOLEAN_FALSE);
			resultMap.put("resultMsg", Message.getMessage("member.error.latedSavePoint"));
		}

		log.error("membershipPointService.updatePointAfterPurchase : resultCode", resultMap.get("resultCode"));
		log.error("membershipPointService.updatePointAfterPurchase : resultMsg", resultMap.get("resultMsg"));

		return resultMap;
	}

	/**
	 * @Desc : 온라인 사후적립이 가능 여부 확인
	 * @Method Name : checkPossibleLatedSavePoint
	 * @Date : 2019. 4. 24.
	 * @Author : 유성민
	 * @param params
	 * @param memberInfo
	 */
	private Map<String, Object> checkPossibleLatedSavePoint(MbMemberExpostSavePoint params, MbMember memberInfo,
			OrderOtherPartVo orderOtherPartVo) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String resultCode = Const.BOOLEAN_FALSE;
		String resultMsg = "";

		if (UtilsObject.isEmpty(orderOtherPartVo)) {
			resultMsg = Message.getMessage("member.error.notOrderNo");
		} else if (UtilsObject.isEmpty(orderOtherPartVo.getBuyDcsnDtm())) {
			resultMsg = Message.getMessage("member.error.notBuyDcsnDtm");
		} else if (UtilsText.equals(orderOtherPartVo.getMemberShipYn(), Const.BOOLEAN_TRUE)) {
			resultMsg = Message.getMessage("member.error.orderDtmMemberShipOrder");
		} else if (!UtilsText.equals(memberInfo.getMemberTypeCode(), CommonCode.MEMBER_TYPE_CODE_MEMBERSHIP)) {
			resultMsg = Message.getMessage("member.error.noMemberShip");
		} else if (UtilsText.equals(orderOtherPartVo.getPointSaveYn(), Const.BOOLEAN_TRUE)) {
			resultMsg = Message.getMessage("member.error.pointSaveYn");
		} else if ((params.getPymntAmt() - orderOtherPartVo.getPymntAmt()) != 0) {
			log.debug("params.getPymntAmt() : {}", params.getPymntAmt());
			log.debug("orderOtherPartVo.getPymntAmt() : {}", orderOtherPartVo.getPymntAmt());
			resultMsg = Message.getMessage("member.error.pymntAmtDiff");
		} else if (!UtilsText.equals(
				UtilsDate.formatter(UtilsDate.DEFAULT_DATE_PATTERN_NOT_CHARACTERS, params.getBuyYmd()),
				UtilsDate.formatter(UtilsDate.DEFAULT_DATE_PATTERN_NOT_CHARACTERS, orderOtherPartVo.getOrderDtm()))) {
			log.debug("getBuyYmd : {}",
					UtilsDate.formatter(UtilsDate.DEFAULT_DATE_PATTERN_NOT_CHARACTERS, params.getBuyYmd()));
			log.debug("getOrderDtm : {}",
					UtilsDate.formatter(UtilsDate.DEFAULT_DATE_PATTERN_NOT_CHARACTERS, orderOtherPartVo.getOrderDtm()));
			resultMsg = Message.getMessage("member.error.buyYmdDiff");
		} else if (UtilsText.equals(orderOtherPartVo.getBuydayOverday(), Const.BOOLEAN_TRUE)) {
			resultMsg = Message.getMessage("member.error.saveDateExpr");
		}

		if (UtilsText.isBlank(resultMsg)) {
			resultCode = Const.BOOLEAN_TRUE;
		}
		resultMap.put("resultCode", resultCode);
		resultMap.put("resultMsg", resultMsg);
		return resultMap;
	}

	/**
	 * @Desc : 사후적립 순번
	 * @Method Name : selectExpostSavePointSeqNextVal
	 * @Date : 2019. 4. 24.
	 * @Author : 유성민
	 * @param params
	 * @return
	 */
	public int selectExpostSavePointSeqNextVal(MbMemberExpostSavePoint params) throws Exception {
		log.debug("MbMemberExpostSavePoint params : {}", params);
		return mbMemberExpostSavePointDao.selectExpostSavePointSeqNextVal(params);
	}

	/**
	 * @Desc : 사후적립 저장
	 * @Method Name : insertExpostSavePoint
	 * @Date : 2019. 4. 24.
	 * @Author : 유성민
	 * @param params
	 */
	public void insertExpostSavePoint(MbMemberExpostSavePoint params) throws Exception {
		mbMemberExpostSavePointDao.insert(params);
	}

	/**
	 * @Desc : 구매확정 시 포인트 지급 : 필수 파라메터 : 회원번호(memberNo), 주문번호(ORDER_NO)
	 * @Method Name : getBuyFixRequest
	 * @Date : 2019. 7. 26.
	 * @Author : 이동엽
	 * @return
	 * @throws Exception
	 */
	public List<BuyFixProduct> setBuyFixRequest(MbMemberPoint params) throws Exception {
		UserDetails userDetails = LoginManager.getUserDetails();
		// 유저 정보 체크
		MbMember mbMember = memberPointService.validateCheckMemberInfo(params.getMemberNo());

		// 구매확정 인터페이스[memA890a]
		List<BuyFixProduct> result = membershipPointService.buyFixRequest(mbMember.getSafeKey(),
				mbMember.getSafeKeySeq(), params.getOrderNo());

		// 구매확정 이력 파라메터 셋팅 및 등록
		for (BuyFixProduct buyFixProduct : result) {
			if (UtilsText.equals(buyFixProduct.getResultYn(), Const.BOOLEAN_TRUE)) {
				params.setSavePathType(BaseConst.SAVE_PATH_TYPE_ONLINE);
				params.setSaveTypeCode(CommonCode.SAVE_TYPE_CODE_EARN_PURCHASE);
				params.setSaveUseContText(BaseConst.SAVE_TYPE_CODE_EARN_PURCHASE_NAME);
				params.setSaveAmt(buyFixProduct.getProductPoint());
				params.setUseAmt(0);
				params.setValidStartDtm(UtilsDate.getSqlTimeStamp());
				params.setValidEndDtm(
						Timestamp.valueOf(LocalDateTime.of(LocalDate.now().plusYears(2).getYear(), 12, 31, 23, 59)));
				params.setRgsterNo(userDetails.getAdminNo());
				memberPointService.insertMemberPoint(params);
			}
		}

		return result;
	}

	/**
	 * @Desc : 포인트 강제 적립/차감(관리자)
	 * @Method Name : setMemberSavePoint
	 * @Date : 2019. 9. 6.
	 * @Author : 3TOP
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setMemberSavePoint(MbMemberPoint params) throws Exception {

		if (UtilsText.equals(params.getSaveType(), "S")) {
			params.setSaveTypeCode(CommonCode.SAVE_TYPE_CODE_EARN_MEMBER);
			// 각 도메인에서 별도의 세팅이 없을 경우 적립유형명을 기본으로 세팅.
			if (UtilsText.isEmpty(params.getSaveUseContText())) {
				params.setSaveUseContText(Const.SAVE_TYPE_CODE_EARN_MEMBER_NAME);
			}
		} else {
			params.setSaveTypeCode(CommonCode.SAVE_TYPE_CODE_DEDUCTION_MEMBER);
			// 각 도메인에서 별도의 세팅이 없을 경우 적립유형명을 기본으로 세팅.
			if (UtilsText.isEmpty(params.getSaveUseContText())) {
				params.setSaveUseContText(Const.SAVE_TYPE_CODE_DEDUCTION_MEMBER_NAME);
			}
			params.setPointType(Const.POINT_TYPE_USE);
		}

		return memberPointService.registUserPoint(params);
	}

	/**
	 * @Desc : 기타 포인트 강제 적립/차감(관리자)
	 * @Method Name : setMemberSaveEtcPoint
	 * @Date : 2019. 9. 11.
	 * @Author : 3TOP
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setMemberSaveEtcPoint(MbMemberPoint params) throws Exception {

		if (UtilsText.equals(params.getSaveType(), "S")) {
			params.setSaveTypeCode(CommonCode.SAVE_TYPE_CODE_EARN_ETC);
			// 각 도메인에서 별도의 세팅이 없을 경우 적립유형명을 기본으로 세팅.
			if (UtilsText.isEmpty(params.getSaveUseContText())) {
				params.setSaveUseContText(Const.SAVE_TYPE_CODE_EARN_ETC_NAME);
			}
		} else {
			params.setSaveTypeCode(CommonCode.SAVE_TYPE_CODE_DEDUCTION_ETC);
			// 각 도메인에서 별도의 세팅이 없을 경우 적립유형명을 기본으로 세팅.
			if (UtilsText.isEmpty(params.getSaveUseContText())) {
				params.setSaveUseContText(Const.SAVE_TYPE_CODE_DEDUCTION_ETC_NAME);
			}
			params.setPointType(Const.POINT_TYPE_USE);
		}

		return memberPointService.registUserEtcPoint(params);
	}

	/**
	 * @Desc : 상품구매 적립 (I/F연동)
	 * @Method Name : setPurchaseSavePoint
	 * @Date : 2019. 8. 6.
	 * @Author : 3TOP
	 * @param params
	 */
	public PointUse setPurchaseSavePoint(MbMemberPoint params) throws Exception {
		params.setSaveTypeCode(CommonCode.SAVE_TYPE_CODE_EARN_PURCHASE);
		// 각 도메인에서 별도의 세팅이 없을 경우 적립유형명을 기본으로 세팅.
		if (UtilsText.isEmpty(params.getSaveUseContText())) {
			params.setSaveUseContText(Const.SAVE_TYPE_CODE_EARN_PURCHASE_NAME);
		}
		PointUse pointUse = setCancelBuyPoint(params);

		return pointUse;
	}

	/**
	 * @Desc : 상품구매 취소(취소된 포인트를 적립)
	 * @Method Name : setPurchaseCancelPoint
	 * @Date : 2019. 9. 5.
	 * @Author : 3TOP
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public PointUse setPurchaseCancelPoint(MbMemberPoint params) throws Exception {
		params.setSaveTypeCode(CommonCode.SAVE_TYPE_CODE_EARN_PURCHASE);
		// 각 도메인에서 별도의 세팅이 없을 경우 적립유형명을 기본으로 세팅.
		if (UtilsText.isEmpty(params.getSaveUseContText())) {
			params.setSaveUseContText(Const.SAVE_TYPE_CODE_EARN_PURCHASE_NAME);
		}
		PointUse pointUse = setCancelBuyPoint(params);

		return pointUse;
	}

	/**
	 * @Desc : 구매사용 차감
	 * @Method Name : setPurchaseUsePoint
	 * @Date : 2019. 8. 6.
	 * @Author : 3TOP
	 */
	public PointUse setPurchaseUsePoint(MbMemberPoint params) throws Exception {
		params.setSaveTypeCode(CommonCode.SAVE_TYPE_CODE_DEDUCTION_USEPURCHASE);
		// 각 도메인에서 별도의 세팅이 없을 경우 적립유형명을 기본으로 세팅.
		if (UtilsText.isEmpty(params.getSaveUseContText())) {
			params.setSaveUseContText(Const.SAVE_TYPE_CODE_DEDUCTION_USEPURCHASE_NAME);
		}
		params.setPointType(Const.POINT_TYPE_USE);
		PointUse pointUse = setUseBuyPoint(params);

		return pointUse;
	}

	// ==========================I/F Service S:====================================

	/**
	 * @Desc : 가용포인트 조회 : 총 스탬프, 현 스탬프, 가용포인트, 전체포인트, 소멸포인트
	 * @Method Name : getPrivateReportBySafeKey
	 * @Date : 2019. 6. 18.
	 * @Author : 이동엽
	 * @param safeKey
	 * @return
	 */
	public PrivateReport getPrivateReportBySafeKey(String safeKey, String safeKeySeq) throws Exception {
		return membershipPointService.getPrivateReportBySafeKey(safeKey, safeKeySeq);
	}

	/**
	 * @Desc : 가용포인트 조회 [memA910a]
	 * @Method Name : getMemberPointInfo
	 * @Date : 2019. 4. 30.
	 * @Author : 3TOP
	 * @param memberNo
	 * @throws Exception
	 */
	public PrivateReport getMemberPointInfo(String memberNo) throws Exception {
		PrivateReport privateReport = new PrivateReport();

		String safeKey = "";
		String safeKeySeq = "";
		// 유저정보 조회
		MbMember member = new MbMember();
		member.setMemberNo(memberNo);
		if (UtilsText.isNotEmpty(member.getMemberNo())) {
			member = memberService.getMember(member);
		} else {
			throw new Exception("회원정보가 존재하지 않습니다.");
		}

		// 가용포인트 인터페이스 조회[memA910a]
		safeKey = member.getSafeKey();
		safeKeySeq = member.getSafeKeySeq();

		try {
			privateReport = membershipPointService.getPrivateReportBySafeKey(safeKey, safeKeySeq);
		} catch (Exception e) {
			throw new Exception("비정상적인 접근입니다.");
		}

		return privateReport;
	}

	/**
	 * @Desc : 포인트 강제 적립 [memA940a]
	 * @Method Name : registUserPointByUser
	 * @Date : 2019. 6. 20.
	 * @Author : 3TOP
	 * @param safeKey
	 * @param changeAmount
	 * @param type
	 */
	public Map<String, Object> registUserPoint(MbMemberPoint params) throws Exception {
		boolean result = false;
		Map<String, Object> resultMap = new ModelMap();
		PrivateReport privateReport = getMemberPointInfo(params.getMemberNo());

		SimpleDateFormat formatter = new SimpleDateFormat(Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS);
		MbMember member = validateCheckMemberInfo(params.getMemberNo());

		String gubun = "";
		int point = 0;
		String forceDate = formatter.format(UtilsDate.getSqlTimeStamp());
		String forcePoint = Integer.toString(params.getPoint());

		if (UtilsText.equals(params.getPointType(), Const.POINT_TYPE_USE)) {
			point = params.getPoint() * -1;
			gubun = Const.POINT_TYPE_SUBT;
		} else {
			point = params.getPoint();
			gubun = Const.POINT_TYPE_SAVE;
		}

		if (privateReport.getPossPoint() + point < 0) {
			throw new Exception(
					"사용가능 포인트[" + privateReport.getPossPoint() + "] 보다 차감 포인트 [" + params.getPoint() + "] 값이 더 큽니다.");
		}

		result = membershipPointService.registUserPointByUser(member.getSafeKey(), member.getSafeKeySeq(), forceDate,
				forcePoint, gubun);

		try {
			memberPointService.insertMemberPoint(params);
		} catch (Exception e) {
			e.getMessage();
		}

		resultMap.put("code", result);

		return resultMap;
	}

	/**
	 * @Desc : 기타적립금 적립/차감
	 * @Method Name : registUserEtcPoint
	 * @Date : 2019. 9. 11.
	 * @Author : 3TOP
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> registUserEtcPoint(MbMemberPoint params) throws Exception {
		boolean result = false;
		Map<String, Object> resultMap = new ModelMap();
		PrivateReport privateReport = getMemberPointInfo(params.getMemberNo());

		SimpleDateFormat formatter = new SimpleDateFormat(Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS);
		MbMember member = validateCheckMemberInfo(params.getMemberNo());

		String gubun = "";
		int point = 0;
		String etcCode = params.getEtcSavedCode();
		String forceDate = formatter.format(UtilsDate.getSqlTimeStamp());
		String forcePoint = Integer.toString(params.getPoint());

		if (UtilsText.equals(params.getPointType(), Const.POINT_TYPE_USE)) {
			point = params.getPoint() * -1;
			gubun = Const.POINT_TYPE_SUBT;
		} else {
			point = params.getPoint();
			gubun = Const.POINT_TYPE_SAVE;
		}

		// 인터페이스 memA860a[기타 적립코드 유효성 확인]을 체크한다.
		boolean etcResult = membershipPointService.isValidationBySavedCode(params.getEtcSavedCode());

		if (!etcResult) {
			throw new Exception("통합 멤버십 코드를 확인해주세요.");
		}

		if (privateReport.getPossPoint() + point < 0) {
			throw new Exception(
					"사용가능 포인트[" + privateReport.getPossPoint() + "] 보다 차감 포인트 [" + params.getPoint() + "] 값이 더 큽니다.");
		}

		result = membershipPointService.updateEtcProcessUserPointByUser(member.getSafeKey(), member.getSafeKeySeq(),
				forceDate, etcCode, forcePoint, gubun);

		try {
			memberPointService.insertMemberPoint(params);
		} catch (Exception e) {
			e.getMessage();
		}

		resultMap.put("code", result);

		return resultMap;
	}

	/**
	 * @Desc : 포인트 사용 [memB290a]
	 * @Method Name : setUseBuyPoint
	 * @Date : 2019. 6. 24.
	 * @Author : 이동엽
	 * @param memberNo
	 * @param point
	 * @param eventpoint
	 * @throws Exception
	 */
	private PointUse setUseBuyPoint(MbMemberPoint params) throws Exception {

		PointUse pointUse = new PointUse();
		SimpleDateFormat formatter = new SimpleDateFormat(Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS);

		MbMember member = validateCheckMemberInfo(params.getMemberNo());
		int point = params.getPoint();
		int eventPoint = params.getEventPoint();
		String saleDate = formatter.format(UtilsDate.getSqlTimeStamp());

		log.error(
				"setUseBuyPoint 포인트 사용 [memB290a] ==>> memberNo = {}, saleDate = {}, point = {}, useEvnetPoint = {}, useGubun = {}, orderNo = {}",
				params.getMemberNo(), saleDate, point, eventPoint, Const.POINT_TYPE_USE, params.getOrderNo());

		pointUse = membershipPointService.updatePointForMembershipHandler(member.getSafeKey(), member.getSafeKeySeq(),
				saleDate, point, eventPoint, Const.POINT_TYPE_USE, params.getOrderNo());

		log.error("인터페이스 호출 : 포인트 사용 [memB290a] ==>> memberNo = {}, pointUse= {}", params.getMemberNo(), pointUse);

		// 인터페이스 호출 : 포인트 적립 혹은 차감 [memB290a]
		if (UtilsText.equals(pointUse.getReSult(), Const.IF_RESULT_SUCCESS)) {
			try {
				memberPointService.insertMemberPoint(params);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		} else {
			throw new Exception(pointUse.getReSultMsg());
		}

		return pointUse;
	}

	/**
	 * @Desc : 포인트 사용 취소(적립) [memB290a]
	 * @Method Name : setCancelBuyPoint
	 * @Date : 2019. 6. 24.
	 * @Author : 이동엽
	 * @param memberNo
	 * @param point
	 * @param eventpoint
	 * @throws Exception
	 */
	private PointUse setCancelBuyPoint(MbMemberPoint params) throws Exception {
		PrivateReport privateReport = getMemberPointInfo(params.getMemberNo());

		PointUse pointUse = new PointUse();
		SimpleDateFormat formatter = new SimpleDateFormat(Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS);

		int point = params.getPoint();
		int eventPoint = params.getEventPoint();
		String saleDate = formatter.format(UtilsDate.getSqlTimeStamp());

		// 가용포인트 조회
		if (privateReport == null) {
			throw new Exception("회원 포인트 데이터 연동 조회에 실패하였습니다.");
		}

		// 이벤트 만료일을 체크하여 이벤트 포인트 설정
		// 2020-04-01 고객측 요청으로 주석처리
//		int validDate = privateReport != null ? privateReport.getEventValidateDate() : -1;
//		if (validDate < 0) {
//			eventPoint = 0;
//		}

		// 유저 정보 체크
		MbMember member = validateCheckMemberInfo(params.getMemberNo());

		log.error(
				"setCancelBuyPoint 포인트 사용 취소(적립) [memB290a] ==>> memberNo = {}, saleDate = {}, point = {}, useEvnetPoint = {}, useGubun = {}, orderNo = {}",
				params.getMemberNo(), saleDate, point, eventPoint, Const.POINT_TYPE_SAVE, params.getOrderNo());

		// 인터페이스 호출 : 포인트 적립 혹은 차감 [memB290a]
		pointUse = membershipPointService.updatePointForMembershipHandler(member.getSafeKey(), member.getSafeKeySeq(),
				saleDate, point, eventPoint, Const.POINT_TYPE_SAVE, params.getOrderNo());

		log.error("인터페이스 호출 : 포인트 사용 취소(적립) [memB290a] ==>> memberNo = {}, pointUse= {}", params.getMemberNo(),
				pointUse);

		// 이력 등록
		if (UtilsText.equals(pointUse.getReSult(), Const.IF_RESULT_SUCCESS)) {
			try {
				memberPointService.insertMemberPoint(params);
			} catch (Exception e) {
				log.error("insertMemberPoint error= {}", e.getMessage());
			}
		} else {
			throw new Exception("데이터를 조회하는데 실패하였습니다. 잠시 후 다시 시도해주시거나 고객센터(1588-9667)로 연락주시기 바랍니다.");
		}

		return pointUse;
	}

	// ==========================I/F Service E:====================================

	/**
	 * @Desc : 유저 정보 체크
	 * @Method Name : validateCheckMemberInfo
	 * @Date : 2019. 6. 21.
	 * @Author : 이동엽
	 * @param memberNo
	 * @param changeAmount
	 * @return
	 * @throws Exception
	 */
	public MbMember validateCheckMemberInfo(String memberNo) throws Exception {
		MbMember mbMember = new MbMember();
		mbMember.setMemberNo(memberNo);
		mbMember = memberService.getMember(mbMember);

		if (mbMember == null) {
			throw new Exception("유저 정보가 존재하지 않습니다.");
		} else if (!UtilsText.equals(mbMember.getMemberTypeCode(), CommonCode.MEMBER_TYPE_MEMBERSHIP)) {
			throw new Exception("통합 맴버십 회원이 아닙니다.");
		}

		return mbMember;
	}

	/**
	 * @Desc : 포인트 이력 등록
	 * @Method Name : insertMemberPoint
	 * @Date : 2019. 6. 12.
	 * @Author : 이동엽
	 * @param mbMemberPoint
	 * @throws Exception
	 */
	public void insertMemberPoint(MbMemberPoint mbMemberPoint) throws Exception {
		mbMemberPoint.setSavePathType(Const.SAVE_PATH_TYPE_ONLINE);
		int usePoint = 0;
		int userEventPoint = 0;

		if (!UtilsText.equals(mbMemberPoint.getPointType(), Const.POINT_TYPE_USE)) {
			mbMemberPoint.setSaveAmt(mbMemberPoint.getPoint() + mbMemberPoint.getEventPoint());
			mbMemberPoint.setUseAmt(0);
		} else {
			usePoint = mbMemberPoint.getPoint() * -1;
			userEventPoint = mbMemberPoint.getEventPoint() * -1;

			mbMemberPoint.setSaveAmt(0);
			mbMemberPoint.setUseAmt(usePoint + userEventPoint);
		}
		mbMemberPoint.setValidStartDtm(UtilsDate.getSqlTimeStamp());
		mbMemberPoint.setValidEndDtm(
				Timestamp.valueOf(LocalDateTime.of(LocalDate.now().plusYears(2).getYear(), 12, 31, 23, 59)));
		mbMemberPoint.setRgsterNo(mbMemberPoint.getMemberNo());
		mbMemberPointDao.insertMemberPoint(mbMemberPoint);
	}
}
