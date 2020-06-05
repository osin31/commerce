package kr.co.abcmart.bo.member.service;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.ReflectionUtils;

import kr.co.abcmart.bo.board.model.master.BdMemberCounsel;
import kr.co.abcmart.bo.board.model.master.BdMemberCounselMemo;
import kr.co.abcmart.bo.board.repository.master.BdMemberCounselDao;
import kr.co.abcmart.bo.board.service.BdInquiryService;
import kr.co.abcmart.bo.cmm.model.master.CmDaysCondtn;
import kr.co.abcmart.bo.cmm.service.DaysCondtnService;
import kr.co.abcmart.bo.cmm.service.MailService;
import kr.co.abcmart.bo.cmm.service.MessageService;
import kr.co.abcmart.bo.cmm.vo.MailTemplateVO;
import kr.co.abcmart.bo.cmm.vo.MessageVO;
import kr.co.abcmart.bo.event.model.master.EvEventJoinMember;
import kr.co.abcmart.bo.event.service.EventService;
import kr.co.abcmart.bo.member.model.master.MbEmployeeCertificationHistory;
import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.model.master.MbMemberCertificationHistory;
import kr.co.abcmart.bo.member.model.master.MbMemberChangeHistory;
import kr.co.abcmart.bo.member.model.master.MbMemberCoupon;
import kr.co.abcmart.bo.member.model.master.MbMemberCrtfcHistory;
import kr.co.abcmart.bo.member.model.master.MbMemberDeliveryAddress;
import kr.co.abcmart.bo.member.model.master.MbMemberEasyLogin;
import kr.co.abcmart.bo.member.model.master.MbMemberInterestBrand;
import kr.co.abcmart.bo.member.model.master.MbMemberInterestCategory;
import kr.co.abcmart.bo.member.model.master.MbMemberInterestStore;
import kr.co.abcmart.bo.member.model.master.MbMemberLoginHistory;
import kr.co.abcmart.bo.member.model.master.MbMemberMemo;
import kr.co.abcmart.bo.member.repository.master.MbEmployeeCertificationHistoryDao;
import kr.co.abcmart.bo.member.repository.master.MbMemberCertificationHistoryDao;
import kr.co.abcmart.bo.member.repository.master.MbMemberChangeHistoryDao;
import kr.co.abcmart.bo.member.repository.master.MbMemberCouponDao;
import kr.co.abcmart.bo.member.repository.master.MbMemberCrtfcHistoryDao;
import kr.co.abcmart.bo.member.repository.master.MbMemberDao;
import kr.co.abcmart.bo.member.repository.master.MbMemberDeliveryAddressDao;
import kr.co.abcmart.bo.member.repository.master.MbMemberEasyLoginDao;
import kr.co.abcmart.bo.member.repository.master.MbMemberInterestBrandDao;
import kr.co.abcmart.bo.member.repository.master.MbMemberInterestCategoryDao;
import kr.co.abcmart.bo.member.repository.master.MbMemberInterestStoreDao;
import kr.co.abcmart.bo.member.repository.master.MbMemberLoginHistoryDao;
import kr.co.abcmart.bo.member.repository.master.MbMemberMemoDao;
import kr.co.abcmart.bo.member.repository.master.MbMemberPointDao;
import kr.co.abcmart.bo.member.vo.CertificationVO;
import kr.co.abcmart.bo.member.vo.MemberEmpInfoVO;
import kr.co.abcmart.bo.member.vo.MemberHistorySearchVO;
import kr.co.abcmart.bo.member.vo.MemberSearchVO;
import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.bo.order.service.OrderOtherPartService;
import kr.co.abcmart.bo.order.vo.OrderOtherPartVo;
import kr.co.abcmart.bo.product.model.master.BdProductInquiry;
import kr.co.abcmart.bo.product.model.master.BdProductReview;
import kr.co.abcmart.bo.product.service.ProductInquiryService;
import kr.co.abcmart.bo.product.service.ProductReviewService;
import kr.co.abcmart.bo.promotion.service.CouponService;
import kr.co.abcmart.bo.promotion.vo.PrCouponSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.bo.system.service.SystemService;
import kr.co.abcmart.common.account.AccountAuth;
import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.constant.BaseCommonCode;
import kr.co.abcmart.common.message.Message;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsCrypt;
import kr.co.abcmart.common.util.UtilsHashKey;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsRequest;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.constant.MailCode;
import kr.co.abcmart.constant.MessageCode;
import kr.co.abcmart.interfaces.module.member.MembershipPointService;
import kr.co.abcmart.interfaces.module.member.model.EmployBalance;
import kr.co.abcmart.interfaces.module.member.model.EmployCertReport;
import kr.co.abcmart.interfaces.module.member.model.HistoryPoint;
import kr.co.abcmart.interfaces.module.member.model.PrivateReport;
import kr.co.abcmart.interfaces.module.member.model.StorePointHistory;
import kr.co.abcmart.trace.SQLTrace;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.Encryption;
import kr.co.abcmart.util.UtilsDate;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 회원정보에 필요한 서비스(회원관리 전용) **** 타도메인 사용금지*** **** 필요한 서비스가 필요할 경우 회원파트에
 *       문의 ***
 * @FileName : MemberService.java
 * @Project : abc.bo
 * @Date : 2019. 2. 19.
 * @Author : Kimyounghyun
 */
@Slf4j
@Service
public class MemberService {
	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private DaysCondtnService daysCondtnService;

	@Autowired
	private CouponService couponService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private BdInquiryService bdInquiryService;

	@Autowired
	private ProductInquiryService productInquiryService;

	@Autowired
	private SystemService systemService;

	@Autowired
	private ProductReviewService productReviewService;

	@Autowired
	private MbMemberDao mbMemberDao;

	@Autowired
	private MbMemberLoginHistoryDao mbMemberLoginHistoryDao;

	@Autowired
	private MbMemberChangeHistoryDao mbMemberChangeHistoryDao;

	@Autowired
	private MbMemberCrtfcHistoryDao mbMemberCrtfcHistoryDao;

	@Autowired
	private MbMemberDeliveryAddressDao mbMemberDeliveryAddressDao;

	@Autowired
	private MbMemberInterestBrandDao mbMemberInterestBrandDao;

	@Autowired
	private MbMemberInterestCategoryDao mbMemberInterestCategoryDao;

	@Autowired
	private MbMemberEasyLoginDao mbMemberEasyLoginDao;

	@Autowired
	private MbMemberMemoDao mbMemberMemoDao;

	@Autowired
	private MbMemberCouponDao mbMemberCouponDao;

	@Autowired
	private BdMemberCounselDao bdMemberCounselDao;

	@Autowired
	private MbMemberPointDao mbMemberPointDao;

	@Autowired
	private MbMemberInterestStoreDao mbMemberInterestStoreDao;

	@Autowired
	private MembershipPointService membershipPointService;

	@Autowired
	private MbMemberCertificationHistoryDao mbMemberCertificationHistoryDao;

	@Autowired
	private MessageService messageService;

	@Autowired
	private MailService mailService;

	@Autowired
	private MemberPointService memberPointService;

	@Autowired
	private EventService eventService;

	@Autowired
	private OrderOtherPartService orderOtherPartService;

	@Autowired
	private MbEmployeeCertificationHistoryDao mbEmployeeCertificationHistoryDao;

	/**
	 * @Desc : 회원 목록을 조회
	 * @Method Name : getAdminList
	 * @Date : 2019. 2. 11.
	 * @Author : 이동엽
	 * @param pageable
	 * @return page
	 * @throws Exception
	 */
	public Page<MbMember> getMemberList(Pageable<MbMember, MbMember> pageable) throws Exception {

		// 기준일자 조회(장기미사용)
		CmDaysCondtn cmDaysCondtn = new CmDaysCondtn();
		cmDaysCondtn = daysCondtnService.getDaysCondtn(CommonCode.LONG_UNUSED_USER_CONDITION);
		pageable.getBean().setCondtnTermValue(Integer.parseInt(cmDaysCondtn.getCondtnTermValue()) - 30);

		int count = mbMemberDao.selectMemberListCount(pageable);

		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(mbMemberDao.selectMemberList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 회원 목록 조회(타 도메인 사용)
	 * @Method Name : getMemberSubList
	 * @Date : 2019. 3. 6.
	 * @Author : 이동엽
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<MbMember> getMemberSubList(Pageable<MemberSearchVO, MbMember> pageable) throws Exception {
		int count = mbMemberDao.selectMemberSubListCount(pageable);

		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(mbMemberDao.selectMemberSubList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 로그인이력 조회
	 * @Method Name : getLoginHistory
	 * @Date : 2019. 2. 12.
	 * @Author : Kimyounghyun
	 * @param pageable
	 * @return
	 */
	public Page<MbMemberLoginHistory> getLoginHistory(Pageable<MemberHistorySearchVO, MbMemberLoginHistory> pageable)
			throws Exception {
		int count = mbMemberLoginHistoryDao.selectLoginHistoryListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(mbMemberLoginHistoryDao.selectLoginHistoryList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 변경이력 조회
	 * @Method Name : getChangeHistory
	 * @Date : 2019. 2. 12.
	 * @Author : Kimyounghyun
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<MbMemberChangeHistory> getChangeHistory(Pageable<MemberHistorySearchVO, MbMemberChangeHistory> pageable)
			throws Exception {
		int count = mbMemberChangeHistoryDao.selectChangeHistoryListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(mbMemberChangeHistoryDao.selectChangeHistoryList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 본인인증이력 조회
	 * @Method Name : getCertifyHistory
	 * @Date : 2019. 2. 12.
	 * @Author : Kimyounghyun
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<MbMemberCrtfcHistory> getCertifyHistory(Pageable<MemberHistorySearchVO, MbMemberCrtfcHistory> pageable)
			throws Exception {
		int count = mbMemberCrtfcHistoryDao.selectCertifyHistoryListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(mbMemberCrtfcHistoryDao.selectCertifyHistoryList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 회원 상세 정보를 조회
	 * @Method Name : getMemberDetailInfo
	 * @Date : 2019. 2. 13.
	 * @Author : 이동엽
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMemberWithAllHasManyNoTrx(String memberNo) throws Exception {

		MbMember params = new MbMember();
		params.setMemberNo(memberNo);

		String[] codeFields = { CommonCode.BANK_CODE, CommonCode.BLACK_LIST_TYPE_CODE };
		String condtnTermName = CommonCode.USER_PSWD_RENEW_CONDITION;
		String ifResut = "";

		Date pswdChngDtm = new Date();
		ModelMap resultMap = new ModelMap();
		MbMemberMemo mbMemberMemo = new MbMemberMemo();
		MbMemberDeliveryAddress mbMemberDeliveryAddress = new MbMemberDeliveryAddress();
		BdMemberCounsel bdMemberCounsel = new BdMemberCounsel();
		PrivateReport privateReport = new PrivateReport();
		MemberEmpInfoVO memberEmpInfo = new MemberEmpInfoVO();

		// 코드정보 조회
		Map<String, List<SyCodeDetail>> codeData = commonCodeService.getCodeListByGroup(codeFields);

		// 회원정보 조회
		MbMember detailData = memberService.getMember(params);
		pswdChngDtm = detailData.getPswdChngDtm();

		// 비밀번호 초기화 시 사용자 비밀번호 변경기한을 조회하여 계산.
		if (UtilsText.equals(detailData.getPswdInitYn(), Const.BOOLEAN_TRUE)) {
			// 조건날짜 테이블 조회
			CmDaysCondtn cmDaysCondtn = daysCondtnService.getDaysCondtn(condtnTermName);
			String condtnTermValue = cmDaysCondtn.getCondtnTermValue();
			pswdChngDtm = UtilsDate.addDays(pswdChngDtm, Integer.parseInt(condtnTermValue));
			// Date 형태의 값을 Timestamp 형태의 값으로 변환
			Timestamp convertDate = new Timestamp(pswdChngDtm.getTime());
			detailData.setPswdChngDtm(convertDate);
		}

		// 임직원 정보를 세팅
		if (UtilsText.equals(detailData.getEmpYn(), Const.BOOLEAN_TRUE)
				&& UtilsText.equals(detailData.getMemberTypeCode(), CommonCode.MEMBER_TYPE_MEMBERSHIP)) {
			memberEmpInfo = memberService.setEmpInfo(detailData);
		}

		// 주소정보 조회 (회원 상세에서는 기본배송지)
		mbMemberDeliveryAddress.setMemberNo(memberNo);
		mbMemberDeliveryAddress.setDfltDlvyAddrYn(Const.BOOLEAN_TRUE);
		MbMemberDeliveryAddress deliveryData = memberService.getMemberDfltDeliveryAddress(mbMemberDeliveryAddress);

		// 회원 문의현황 조회
		bdMemberCounsel.setMemberTabInfo(Const.BOOLEAN_TRUE);
		bdMemberCounsel.setMemberNo(memberNo);
		BdMemberCounsel counselData = memberService.getInquiryStatData(bdMemberCounsel);

		// 상품 QnA 답변, 미답변 건수 조회
		Map<String, Object> productAswrData = productInquiryService.getAswrCount(memberNo);

		// 상품후기 미답변 건수 조회
		String productReviewCount = productReviewService.getAswrCount(memberNo);

		// 회원보유 사용가능 쿠폰 카운트 조회
		PrCouponSearchVO couponVO = new PrCouponSearchVO();
		couponVO.setMemberNo(memberNo);
		String cpnStatCodes[] = { CommonCode.CPN_STAT_CODE_ISSUANCE, CommonCode.CPN_STAT_CODE_REISSUANCE };
		couponVO.setCpnStatCodes(cpnStatCodes);
		detailData.setCouponCnt(couponService.getMemberCanUseCouponCount(couponVO));

		try {
			if (UtilsText.isNotBlank(detailData.getSafeKey())
					&& UtilsText.equals(CommonCode.MEMBER_TYPE_CODE_MEMBERSHIP, detailData.getMemberTypeCode())) {
				privateReport = memberPointService.getPrivateReportBySafeKey(detailData.getSafeKey(),
						detailData.getSafeKeySeq());
			}
		} catch (Exception e) {
			ifResut = Message.getMessage("member.error.interface");
		}

		// 이벤트 내역 조회
		EvEventJoinMember eventParams = new EvEventJoinMember();
		eventParams.setMemberNo(memberNo);
		Map<String, Object> eventData = eventService.getEventJoinMemberCount(eventParams);

		List<MbMemberInterestStore> storeData = memberService.getMemberInterestStore(memberNo);

		// 회원 관심브랜드 조회
		List<MbMemberInterestBrand> brandData = memberService.getMemberInterestBrand(memberNo);

		// 회원 연동 SNS 조회
		List<MbMemberEasyLogin> snsData = memberService.getMemberSnsData(memberNo);

		// 관리자 메모 조회
		mbMemberMemo.setMemberNo(memberNo);
		List<MbMemberMemo> memoData = memberService.getMemberMemoData(mbMemberMemo);

		// N개가 존재하는 카테코리, 브랜드, sns 데이터를 문자열 형태로 변환하여 회원 상세 데이터에 담는다.
		String interestBrandText = brandData.stream().map(MbMemberInterestBrand::getBrandName)
				.collect(Collectors.joining(","));

		String memberSnsText = snsData.stream().map(MbMemberEasyLogin::getSnsGbnCodeName)
				.collect(Collectors.joining(","));

		detailData.setInterestBrand(interestBrandText);
		if (UtilsObject.isNotEmpty(storeData)) {
			String interestStoreText = storeData.stream().map(MbMemberInterestStore::getStoreName)
					.collect(Collectors.joining(","));
			detailData.setInterestStore(interestStoreText);
		}
		detailData.setMemberSns(memberSnsText);

		resultMap.addAttribute("counselData", counselData);
		resultMap.addAttribute("productAswrData", productAswrData);
		resultMap.addAttribute("codeData", codeData);
		resultMap.addAttribute("detailData", detailData);
		resultMap.addAttribute("deliveryData", deliveryData);
		resultMap.addAttribute("memoData", memoData);
		resultMap.addAttribute("productReviewCount", productReviewCount);
		resultMap.addAttribute("privateReport", privateReport);
		resultMap.addAttribute("eventData", eventData);
		resultMap.addAttribute("ifResut", ifResut);
		resultMap.addAttribute("memberEmpInfo", memberEmpInfo);

		return resultMap;
	}

	/**
	 * @Desc : 회원 기본정보 조회
	 * @Method Name : getMember
	 * @Date : 2019. 2. 12.
	 * @Author : 이동엽
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public MbMember getMember(String memberNo) throws Exception {
		return mbMemberDao.selectMemberDefalutInfo(memberNo);
	}

	/**
	 * @Desc : 회원 기본정보 조회(회원번호 외에 다수의 파라메터를 받기 위해서 생성) getMember가 개발초기에 만들어져 현재 사용하는
	 *       도메인들이 많아 getMember를 수정하지 않고 별도 생성.
	 * @Method Name : getMember
	 * @Date : 2019. 2. 12.
	 * @Author : 이동엽
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public MbMember getMember(MbMember mbMember) throws Exception {
		// 기준일자 조회(장기미사용)
		CmDaysCondtn longUnusedUser = daysCondtnService.getDaysCondtn(CommonCode.LONG_UNUSED_USER_CONDITION);
		mbMember.setCondtnTermValue((Integer.parseInt(longUnusedUser.getCondtnTermValue()) - 30));
		return mbMemberDao.selectMemberDefalutInfoParams(mbMember);
	}

	/**
	 * @Desc : 회원 기본정보, 부가정보 조회
	 * @Method Name : getMemberDefalutInfoWithAddInfo
	 * @Date : 2019. 2. 19.
	 * @Author : Kimyounghyun
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public MbMember getMemberWithAddInfo(String memberNo) throws Exception {
		MbMember member = mbMemberDao.selectMemberDefalutInfo(memberNo);
		return member;
	}

	/**
	 * @Desc : 회원 관심 브랜드 조회
	 * @Method Name : getMemberInterestBrand
	 * @Date : 2019. 2. 14.
	 * @Author : 이동엽
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public List<MbMemberInterestBrand> getMemberInterestBrand(String memberNo) throws Exception {
		return mbMemberInterestBrandDao.selectMemberInterestBrand(memberNo);
	}

	/**
	 * @Desc : 회원 관심 카테고리 조회
	 * @Method Name : getMemberInterestCategory
	 * @Date : 2019. 2. 14.
	 * @Author : 이동엽
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public List<MbMemberInterestCategory> getMemberInterestCategory(String memberNo) throws Exception {
		return mbMemberInterestCategoryDao.selectMemberInterestCategory(memberNo);
	}

	/**
	 * @Desc : 회원 연동 sns 조회
	 * @Method Name : getMemberSnsData
	 * @Date : 2019. 2. 14.
	 * @Author : 이동엽
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public List<MbMemberEasyLogin> getMemberSnsData(String memberNo) throws Exception {
		return mbMemberEasyLoginDao.selectMemberSnsData(memberNo);
	}

	/**
	 * @Desc : 회원 기본 배송지 조회
	 * @Method Name : getMemberDfltDeliveryAddress
	 * @Date : 2019. 2. 13.
	 * @Author : 이동엽
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public MbMemberDeliveryAddress getMemberDfltDeliveryAddress(MbMemberDeliveryAddress mbMemberDeliveryAddress)
			throws Exception {
		return mbMemberDeliveryAddressDao.selectMemberDfltDeliveryAddress(mbMemberDeliveryAddress);
	}

	/**
	 * @Desc : 회원 관리자 메모 조회
	 * @Method Name : getMemberAdminMemoData
	 * @Date : 2019. 2. 15.
	 * @Author : 이동엽
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public List<MbMemberMemo> getMemberMemoData(MbMemberMemo mbMemberMemo) throws Exception {
		return mbMemberMemoDao.selectMemberMemoData(mbMemberMemo);
	}

	/**
	 * @Desc : 회원보유 사용가능 쿠폰 카운트 조회
	 * @Method Name : getMemberCouponCnt
	 * @Date : 2019. 2. 18.
	 * @Author : 이동엽
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public MbMemberCoupon getMemberCouponCnt(String memberNo) throws Exception {
		return mbMemberCouponDao.selectMemberCouponCnt(memberNo);
	}

	/**
	 * @Desc : 회원정보 수정
	 * @Method Name : updateMember
	 * @Date : 2019. 2. 20.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateMember(MbMember mbMember) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserDetails user = LoginManager.getUserDetails();
		MbMemberDeliveryAddress oldDeliveryData = new MbMemberDeliveryAddress();

		String memberNo = mbMember.getMemberNo();

		// 변경이력 저장을 위해 과거 데이터를 조회
		MbMember oldData = memberService.getMember(memberNo);

		if (oldData != null) {
			String marketingData = memberService.hasMarketingData(oldData);
			String oldAccountData = memberService.hasRefundAccountData(oldData);
			oldData.setMarketing(marketingData);
			oldData.setAccountInfo(oldAccountData);
		}

		// 1.회원 기본정보 수정
		mbMember.setModerNo(user.getAdminNo());
		mbMember.setModDtm(UtilsDate.getSqlTimeStamp());
		if (UtilsText.equals(mbMember.getAcntCrtfcYn(), Const.BOOLEAN_FALSE)) {
			mbMember.setAcntCrtfcYn(null);
			mbMember.setBankCode(null);
			mbMember.setAcntHldrName(null);
			mbMember.setAcntNoText(null);
		}
		memberService.updateMemberDefaultData(mbMember);

		// 2.회원 기본배송지 수정
		mbMember.getMbMemberDeliveryAddress().setDlvyAddrName(mbMember.getMemberName());
		mbMember.getMbMemberDeliveryAddress().setRcvrName(mbMember.getMemberName());
		mbMember.getMbMemberDeliveryAddress().setDfltDlvyAddrYn(Const.BOOLEAN_TRUE);
		mbMember.getMbMemberDeliveryAddress().setAddDlvyAddrYn(Const.BOOLEAN_FALSE);
		mbMember.getMbMemberDeliveryAddress().setRgsterNo(user.getAdminNo());
		mbMember.getMbMemberDeliveryAddress().setModerNo(user.getAdminNo());
		oldDeliveryData = memberService.getMemberDfltDeliveryAddress(mbMember.getMbMemberDeliveryAddress());

		if (oldDeliveryData != null) {
			// 이력 저장을 위한 주소 값 세팅
			oldData.setDtlAddressText(oldDeliveryData.getDtlAddressText());
			if (UtilsText.isNotBlank(mbMember.getMbMemberDeliveryAddress().getPostCodeText())) {
				memberService.updateMemberDeliveryAddressData(mbMember.getMbMemberDeliveryAddress());
			}
		} else {
			if (UtilsText.isNotBlank(mbMember.getMbMemberDeliveryAddress().getPostCodeText())) {
				memberService.insertMemberDeliveryAddressData(mbMember.getMbMemberDeliveryAddress());
			}
		}

		// 회원 기본정보 변경 이력을 저장
		if (oldData != null) {
			try {
				String basicFields = Const.MEMBER_CHANGE_HISTORY_FIELDS;
				String[] historyFields = basicFields.split(",");
				MbMemberChangeHistory historyParams = new MbMemberChangeHistory();

				for (String historyField : historyFields) {
					String[] keyValue = historyField.split(":");

					Field oldKey = ReflectionUtils.findField(oldData.getClass(), keyValue[0]);
					Field newKey = ReflectionUtils.findField(mbMember.getClass(), keyValue[0]);
					oldKey.setAccessible(true);
					newKey.setAccessible(true);

					String oldValue = oldKey.get(oldData) != null ? oldKey.get(oldData).toString() : "";
					String newValue = newKey.get(mbMember) != null ? newKey.get(mbMember).toString() : "";

					if (!UtilsText.equals(oldValue, newValue)) {
						historyParams.setMemberNo(memberNo);
						historyParams.setChngField(keyValue[0]);
						historyParams.setChngFieldName(keyValue[1]);
						historyParams.setChngBeforeFieldValue(oldValue);
						historyParams.setChngAfterFieldValue(newValue);
						historyParams.setRgsterNo(user.getAdminNo());

						memberService.insertMemberHistory(historyParams);
					}
				}
			} catch (Exception e) {
				log.error("회원 기본정보 변경이력 저장 에러 : {}", e.getMessage());
			}
		}

		resultMap.put("result", Const.BOOLEAN_TRUE);
		return resultMap;
	}

	/**
	 * @Desc : 회원 비밀번호 초기화
	 * @Method Name : updatePswdReset
	 * @Date : 2019. 2. 20.
	 * @Author : 이동엽
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updatePswdReset(MbMember params) throws Exception {
		// 비밀번호 변경 플래그
		String pswdYn = Const.BOOLEAN_TRUE;
		Map<String, Object> result = new HashMap<>();
		// 세션정보
		UserDetails user = LoginManager.getUserDetails();
		MbMember oldData = memberService.getMember(params.getMemberNo());

		String pswdSaltKey = oldData.getPswdSaltText();

		// 임시 비밀번호 생성 및 파라메터 세팅
		// 자바 UUID 유틸을 사용하여 랜덤한 36자리의 숫자&영문소문자 문자열값을 출력
		// 구분자인 '-' 제거 총 32자리의 랜덤 문자열이 출력 그중 앞에서 부터 12자리를 잘라서 사용
		// 요구사항 : 영문 소문자 & 숫자 조합 12자리
		String tempPswd = UUID.randomUUID().toString().replaceAll("-", "");
		tempPswd = tempPswd.substring(0, 12);

		// salt key가 존재하지 않을 경우 salt key 생성
		if (UtilsText.equals(pswdSaltKey, "") || pswdSaltKey == null) {
			pswdSaltKey = UtilsCrypt.getSalt();
			params.setPswdSaltText(pswdSaltKey);
		}

		params.setPswdText(UtilsCrypt.sha256(tempPswd, pswdSaltKey));
		params.setPswdInitYn(pswdYn);
		params.setPswdChangeYn(pswdYn);
		params.setModerNo(user.getAdminNo());
		params.setModDtm(UtilsDate.getSqlTimeStamp());

		memberService.updateMemberDefaultData(params);

		// 비밀번호 변경 후 이메일 발송
		sendMailPswdReset(oldData, tempPswd);

		result.put("result", Const.BOOLEAN_TRUE);

		return result;
	}

	/**
	 * @Desc : 비밀번호 초기화 이메일 전송
	 * @Method Name : sendMailPswdReset
	 * @Date : 2019. 8. 7.
	 * @Author : sic
	 * @param memberParam
	 * @param tempPswd
	 * @throws Exception
	 */
	public void sendMailPswdReset(MbMember memberParam, String tempPswd) throws Exception {
		MailTemplateVO mailVO = new MailTemplateVO();
		Map<String, Object> dataMap = new HashMap<>();

		String issueDateArray[] = UtilsDate.today().split("\\.");
		String issueDate = UtilsText
				.concat(issueDateArray[0] + "년 " + issueDateArray[1] + "월 " + issueDateArray[2] + "일");

		dataMap.put("pswdText", tempPswd);
		dataMap.put("memberName", memberParam.getMemberName());
		dataMap.put("issueDate", issueDate);
		dataMap.put("abcUrl", Const.SERVICE_DOMAIN_ART_FO);
		log.debug("issueDate >>>>>>>>>" + issueDate);

		mailVO.setMailTemplateId(MailCode.SY_MEMBER_PASSWORD_RESET);
		mailVO.setMailTemplateModel(dataMap);
		mailVO.setReceiverMemberNo(memberParam.getMemberNo());
		mailVO.setReceiverEmail(memberParam.getEmailAddrText());
		mailVO.setReceiverName(memberParam.getMemberName());
		mailVO.setRealTime(true);
		mailService.sendMail(mailVO);
	}

	/**
	 * @Desc : 회원 포인트 비밀번호 초기화
	 * @Method Name : updatePointPswdReset
	 * @Date : 2019. 6. 19.
	 * @Author : 이동엽
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updatePointPswdReset(MbMember params) throws Exception {
		Map<String, Object> result = new HashMap<>();
		MbMember mbMember = memberService.getMember(params.getMemberNo());

		// 세션정보
		UserDetails user = LoginManager.getUserDetails();

		SimpleDateFormat formatter = new SimpleDateFormat(Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS);

		String pointPswdText = formatter.format(mbMember.getBirthYmd());
		pointPswdText = pointPswdText.substring(pointPswdText.length() - 4);

		// 포인트 비밀번호 Salt키가 존재하지 않을 경우를 대비하여 분기(AS-ID 회원)
		String pointPswd = Encryption.password(pointPswdText);
		params.setPointPswdText(pointPswd);

		// 맴버십카드 비밀번호 변경 [memA970a]
		try {
			String safeKeySeq = UtilsText.isNotBlank(mbMember.getSafeKeySeq()) ? mbMember.getSafeKeySeq() : "01";
			membershipPointService.changeCardPasswordBySafeKey(mbMember.getSafeKey(), safeKeySeq, pointPswd);
		} catch (Exception e) {
			log.error("memA970a error={}", e.getMessage());
		}

		// 변경이력 등록
		MbMemberChangeHistory historyParams = new MbMemberChangeHistory();

		try {
			historyParams.setMemberNo(params.getMemberNo());
			historyParams.setChngField("pointReset");
			historyParams.setChngFieldName("포인트비밀번호 초기화");
			historyParams.setChngAfterFieldValue(Const.BOOLEAN_TRUE);
			historyParams.setRgsterNo(user.getAdminNo());

			memberService.insertMemberHistory(historyParams);
		} catch (Exception e) {
			log.error("회원 기본정보 변경이력 저장 에러 : {}", e.getMessage());
		}

		params.setModerNo(user.getAdminNo());
		params.setModDtm(UtilsDate.getSqlTimeStamp());

		memberService.updateMemberDefaultData(params);
		result.put("result", Const.BOOLEAN_TRUE);

		return result;
	}

	/**
	 * @Desc : 회원 블랙리스트 설정
	 * @Method Name : updateMemberBlackList
	 * @Date : 2019. 2. 19.
	 * @Author : 이동엽
	 * @param mbMember
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateMemberBlackList(MbMember mbMember) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 세션정보 세팅
		UserDetails user = LoginManager.getUserDetails();
		mbMember.setBlackListTypeCode((UtilsText.equals(mbMember.getBlackListYn(), Const.BOOLEAN_FALSE)) ? ""
				: mbMember.getBlackListTypeCode());
		mbMember.setModerNo(user.getAdminNo());
		mbMember.setModDtm(UtilsDate.getSqlTimeStamp());

		// 회원 정보 업데이트
		memberService.updateMemberDefaultData(mbMember);
		resultMap.put("result", Const.BOOLEAN_TRUE);

		return resultMap;
	}

	/**
	 * @Desc : 회원 관리자 메모 등록
	 * @Method Name : setMemberAdminMemo
	 * @Date : 2019. 2. 19.
	 * @Author : 이동엽
	 * @param mbMemberMemo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setMemberMemo(MbMemberMemo mbMemberMemo) throws Exception {
		ModelMap result = new ModelMap();
		UserDetails user = LoginManager.getUserDetails();
		mbMemberMemo.setRgsterNo(user.getAdminNo());

		// 개행 처리
		if (mbMemberMemo.getMemoText() != null) {
			String replaceCont = mbMemberMemo.getMemoText().replace("\n", Const.STRING_HTML_BR_TAG);
			mbMemberMemo.setMemoText(replaceCont);
		}

		// 회원 관리자 메모를 등록한다.
		mbMemberMemoDao.insertMemberMemo(mbMemberMemo);

		// 등록한 메모의 정보를 조회
		result.addAttribute("memoInfo", memberService.getMemberMemoData(mbMemberMemo));

		return result;
	}

	/**
	 * @Desc : 회원 관리자 메모 삭제
	 * @Method Name : deleteMemberAdminMemo
	 * @Date : 2019. 2. 19.
	 * @Author : 이동엽
	 * @param mbMemberMemo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> deleteMemberMemo(MbMemberMemo mbMemberMemo) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		int resultCnt = mbMemberMemoDao.deleteMemberMemo(mbMemberMemo);

		if (resultCnt > 0) {
			resultMap.put("result", Const.BOOLEAN_TRUE);
			resultMap.put("Message", "삭제 되었습니다.");
		} else {
			resultMap.put("result", Const.BOOLEAN_FALSE);
			resultMap.put("Message", "삭제에 실패하였습니다.");
		}

		return resultMap;
	}

	/**
	 * @Desc : 회원 기본정보 수정
	 * @Method Name : updateMember
	 * @Date : 2019. 2. 19.
	 * @Author : 이동엽
	 * @param mbMember
	 * @return
	 * @throws Exception
	 */
	public int updateMemberDefaultData(MbMember mbMember) throws Exception {
		return mbMemberDao.updateDefaultMemberInfo(mbMember);
	}

	/**
	 * @Desc : 회원 배송지 등록
	 * @Method Name : insertMemberDeliveryAddressData
	 * @Date : 2019. 2. 20.
	 * @Author : 이동엽
	 * @param mbMemberDeliveryAddress
	 * @return
	 * @throws Exception
	 */
	public void insertMemberDeliveryAddressData(MbMemberDeliveryAddress mbMemberDeliveryAddress) throws Exception {
		mbMemberDeliveryAddressDao.insertMemberDeliveryAddressData(mbMemberDeliveryAddress);
	}

	/**
	 * @Desc : 회원 배송지 수정
	 * @Method Name : updateMemberDeliveryAddressData
	 * @Date : 2019. 2. 20.
	 * @Author : 이동엽
	 * @param mbMemberDeliveryAddress
	 * @return
	 * @throws Exception
	 */
	public int updateMemberDeliveryAddressData(MbMemberDeliveryAddress mbMemberDeliveryAddress) throws Exception {
		return mbMemberDeliveryAddressDao.updateMemberDeliveryAddressData(mbMemberDeliveryAddress);
	}

	/**
	 * @Desc : 회원 변경이력 저장
	 * @Method Name : insertMemberHistory
	 * @Date : 2019. 2. 22.
	 * @Author : 이동엽
	 * @param mbMemberChangeHistory
	 * @throws Exception
	 */
	public void insertMemberHistory(MbMemberChangeHistory mbMemberChangeHistory) throws Exception {
		mbMemberChangeHistoryDao.insertMemberHistory(mbMemberChangeHistory);
	}

	/**
	 * @Desc : 사용여부와 상관 없이 전체 공통코드 조회
	 * @Method Name : getCommonCode
	 * @Date : 2019. 2. 13.
	 * @Author : Kimyounghyun
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public List<SyCodeDetail> getAllCommonCode(String codeField) throws Exception {
		return commonCodeService.getAllCodeNoName(codeField);
	}

	/**
	 * @Desc : 사용여부와 상관 없이 전체 공통코드 조회
	 * @Method Name : getCommonCodeGroup
	 * @Date : 2019. 2. 13.
	 * @Author : Kimyounghyun
	 * @param codeFields
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<SyCodeDetail>> getAllCommonCodeGroup(String[] codeFields) throws Exception {
		return commonCodeService.getAllCodeListByGroup(codeFields);
	}

	/**
	 * @Desc : 공통코드 & ibsheet combo 필드 data 조회.
	 * @Method Name : getCodeListByGroupByCombo
	 * @Date : 2019. 2. 19.
	 * @Author : Kimyounghyun
	 * @param codeFields
	 * @param isUse
	 * @return
	 * @throws Exception
	 */
	public Pair<JSONObject, Map<String, List<SyCodeDetail>>> getCodeListByGroupByCombo(String[] codeFields,
			boolean isUse) throws Exception {
		return commonCodeService.getCodeListByGroupByCombo(codeFields, isUse);
	}

	/**
	 * @Desc : 변경이력 기본정보를 config에서 읽어서 공통코드 객체타입으로 반환한다. 공통코드 객체로 반환하는 이유는 화면에서
	 *       부가정보를 공통코드에서 조회하기 때문에 형식을 맞추기 위함이다.
	 * @Method Name : getHistoryBasicFields
	 * @Date : 2019. 2. 18.
	 * @Author : Kimyounghyun
	 * @return
	 */
	public List<SyCodeDetail> getHistoryFields(String field) throws Exception {
		List<SyCodeDetail> list = new ArrayList<SyCodeDetail>();
		String fields = "";

		if (UtilsText.equals("basicInfo", field)) {
			fields = Const.MEMBER_CHANGE_HISTORY_BASIC_FIELDS;
		} else {
			fields = Const.MEMBER_CHANGE_HISTORY_ADD_FIELDS;
		}

		String[] pair = fields.split(",");

		for (String str : pair) {
			String[] keyValue = str.split(":");
			SyCodeDetail syCodeDetail = new SyCodeDetail();
			syCodeDetail.setCodeDtlNo(keyValue[0]);
			syCodeDetail.setCodeDtlName(keyValue[1]);
			list.add(syCodeDetail);
		}

		return list;
	}

	/**
	 * @Desc : 마케팅 활용동의 체크여부를 확인하여 문자열 형태로 반환
	 * @Method Name : hasMarketingData
	 * @Date : 2019. 2. 22.
	 * @Author : 이동엽
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String hasMarketingData(MbMember params) throws Exception {
		String sms = (UtilsText.equals(params.getSmsRecvYn(), Const.BOOLEAN_TRUE) ? CommonCode.MEMBER_MARKETING_SMS
				: "");
		String email = (UtilsText.equals(params.getEmailRecvYn(), Const.BOOLEAN_TRUE)
				? CommonCode.MEMBER_MARKETING_EMAIL
				: "");

		List<String> marketingArray = new ArrayList<String>();

		if (UtilsText.isNotBlank(sms)) {
			marketingArray.add(sms);
		}

		if (UtilsText.isNotBlank(email)) {
			marketingArray.add(email);
		}

		return marketingArray.stream().collect(Collectors.joining(","));
	}

	/**
	 * @Desc : 환불계좌 정보를 확인하여 문자열 형태로 반환
	 * @Method Name : hasRefundAccountData
	 * @Date : 2019. 2. 22.
	 * @Author : 이동엽
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String hasRefundAccountData(MbMember params) throws Exception {
		String bankName = params.getBankName();
		String accountNo = params.getAcntNoText();
		String accountUser = params.getAcntHldrName();

		List<String> accountArray = new ArrayList<String>();

		if (UtilsText.isNotEmpty(bankName)) {
			accountArray.add(bankName);
		}

		if (UtilsText.isNotEmpty(accountNo)) {
			accountArray.add(accountNo);
		}

		if (UtilsText.isNotEmpty(accountUser)) {
			accountArray.add(accountUser);
		}

		return accountArray.stream().collect(Collectors.joining(","));
	}

	/**
	 * @Desc : 회원 배송지 목록 조회
	 * @Method Name : getMemberDeliveryList
	 * @Date : 2019. 2. 26.
	 * @Author : 이동엽
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<MbMemberDeliveryAddress> getMemberDeliveryList(
			Pageable<MbMemberDeliveryAddress, MbMemberDeliveryAddress> pageable) throws Exception {
		int count = mbMemberDeliveryAddressDao.selectMemberDeliveryListCount(pageable);
		pageable.setTotalCount(count);
		if (count > 0) {
			pageable.setContent(mbMemberDeliveryAddressDao.selectMemberDeliveryList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 회원 쿠폰 탭 호출 시 필요데이터 조회
	 * @Method Name : getMemberCouponData
	 * @Date : 2019. 2. 26.
	 * @Author : 이동엽
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMemberCouponData(String memberNo) throws Exception {
		ModelMap resultMap = new ModelMap();

		String[] codeFields = { CommonCode.CPN_TYPE_CODE, CommonCode.CPN_STAT_CODE };

		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		resultMap.addAttribute("codeCombo", pair.getFirst());
		resultMap.addAttribute("codeList", pair.getSecond());
		resultMap.addAttribute("couponData", memberService.getMemberCouponStatData(memberNo));
		resultMap.addAttribute("chanList", siteService.getUseChannelList());

		return resultMap;
	}

	/**
	 * @Desc : 회원 쿠폰현황 조회
	 * @Method Name : getMemberCouponStatData
	 * @Date : 2019. 2. 26.
	 * @Author : 이동엽
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public MbMemberCoupon getMemberCouponStatData(String memberNo) throws Exception {
		return mbMemberCouponDao.selectMemberCouponStatData(memberNo);
	}

	/**
	 * @Desc : 회원 문의 내역 탭 호출 시 필요데이터 조회
	 * @Method Name : getInquiryData
	 * @Date : 2019. 2. 27.
	 * @Author : 이동엽
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getInquiryData(String memberNo) throws Exception {
		ModelMap resultMap = new ModelMap();
		BdMemberCounsel params = new BdMemberCounsel();
		params.setMemberNo(memberNo);

		String[] codeFields = { CommonCode.CNSL_GBN_CODE, CommonCode.ASWR_STAT_CODE, CommonCode.CNSL_TYPE_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		resultMap.addAttribute("codeCombo", pair.getFirst());
		resultMap.addAttribute("codeList", pair.getSecond());
		resultMap.addAttribute("siteInfo", siteService.getSiteList());
		resultMap.addAttribute("inquiryData", memberService.getInquiryStatData(params));

		return resultMap;
	}

	/**
	 * @Desc : 회원 문의 현황 조회
	 * @Method Name : getInquiryStatData
	 * @Date : 2019. 2. 27.
	 * @Author : 3TOP
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public BdMemberCounsel getInquiryStatData(BdMemberCounsel bdMemberCounsel) throws Exception {
		return bdMemberCounselDao.selectInquiryStatData(bdMemberCounsel);
	}

	/**
	 * @Desc : 회원 유선상담 등록/상세 팝업 호출
	 * @Method Name : getMemberCouselInfo
	 * @Date : 2019. 3. 4.
	 * @Author : 이동엽
	 * @param bdMemberCounsel
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMemberCouselInfo(BdMemberCounsel bdMemberCounsel) throws Exception {
		ModelMap resultMap = new ModelMap();

		BdMemberCounsel resultCounsel = new BdMemberCounsel();
		BdMemberCounselMemo bdMemberCounselMemo = new BdMemberCounselMemo();
		List<BdMemberCounselMemo> bdMemberCounselMemoList = new ArrayList<BdMemberCounselMemo>();

		String[] codeFields = { CommonCode.CNSL_TYPE_CODE, CommonCode.ASWR_STAT_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = commonCodeService.getCodeListByGroupByCombo(codeFields,
				false);

		if (!UtilsText.equals(Const.CRUD_C, bdMemberCounsel.getViewType())) {
			resultCounsel = bdInquiryService.selectInquiryDetail(bdMemberCounsel);
			bdMemberCounselMemo.setMemberCnslSeq(resultCounsel.getMemberCnslSeq());
			bdMemberCounselMemoList = bdInquiryService.selectBdMemberCounselMemoList(bdMemberCounselMemo);

		}

		resultMap.addAttribute("codeList", pair.getSecond());
		resultMap.addAttribute("detailInfo", resultCounsel);
		resultMap.addAttribute("memoInfo", bdMemberCounselMemoList);
		resultMap.addAttribute("siteInfo", siteService.getSiteList());
		resultMap.addAttribute("memberInfo", memberService.getMember(bdMemberCounsel.getMemberNo()));

		return resultMap;
	}

	/**
	 * @Desc : 회원 유선 상담 등록
	 * @Method Name : setMemberCounsel
	 * @Date : 2019. 3. 4.
	 * @Author : 이동엽
	 * @param bdMemberCounsel
	 * @throws Exception
	 */
	public Map<String, Object> setMemberCounsel(BdMemberCounsel bdMemberCounsel) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserDetails user = LoginManager.getUserDetails();

		bdMemberCounsel.validate();

		bdMemberCounsel.setCnslGbnCode(CommonCode.CNSL_GBN_CODE_TELCNSL);
		if (UtilsText.equals(Const.CRUD_C, bdMemberCounsel.getViewType())) {
			bdInquiryService.insertMemberCounsel(bdMemberCounsel);
		} else {
			bdMemberCounsel.setAswrNo(user.getAdminNo());
			bdInquiryService.updateInquryDetail(bdMemberCounsel);
		}

		resultMap.put("code", Const.BOOLEAN_TRUE);
		return resultMap;
	}

	/**
	 * @Desc : 회원 포인트 목록 조회
	 * @Method Name : getMemberPointList
	 * @Date : 2019. 3. 5.
	 * @Author : 이동엽
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<StorePointHistory> getMemberPointList(Pageable<MemberSearchVO, StorePointHistory> pageable)
			throws Exception {

		String safeKey = pageable.getBean().getSafeKey();
		String safeKeySeq = pageable.getBean().getSafeKeySeq();
		List<StorePointHistory> pointHistoryList = new ArrayList<>();

		if (UtilsText.isNotBlank(safeKey)) {
			pointHistoryList = membershipPointService.getStorePointHistoryBySafeKey(safeKey, safeKeySeq);
		}

		pageable.setTotalCount(pointHistoryList.size());

		if (pointHistoryList.size() > 0) {
			pageable.setContent((List<StorePointHistory>) this.getCurPageList(pointHistoryList, pageable.getPageNum(),
					pageable.getRowsPerPage()));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 온라인회원 구매, 비회원 구매내역 조회
	 * @Method Name : getMemberPurchaseList
	 * @Date : 2019. 7. 3.
	 * @Author : 이동엽
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<OcOrder> getMemberPurchaseList(Pageable<OcOrder, OcOrder> pageable) throws Exception {
		OcOrder params = new OcOrder();
		List<OcOrder> purchaseList = new ArrayList<>();

		params.setMemberNo(pageable.getBean().getMemberNo());
		params.setMemberTypeCode(pageable.getBean().getMemberTypeCode());
		params.setOrderNo(pageable.getBean().getOrderNo());
		params.setCrtfcNoText(pageable.getBean().getCrtfcNoText());
		params.setOrderDtm(pageable.getBean().getOrderDtm());

		purchaseList = orderOtherPartService.getOrderMemberPointExportList(params);
		pageable.setTotalCount(purchaseList.size());

		if (purchaseList.size() > 0) {
			pageable.setContent((List<OcOrder>) this.getCurPageList(purchaseList, pageable.getPageNum(),
					pageable.getRowsPerPage()));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc :페이징에 맞게 list데이터 가공
	 * @Method Name : getCurPageMembershipPointList
	 * @Date : 2019. 3. 25.
	 * @Author : 유성민
	 * @param list
	 * @param page
	 * @param listSize
	 * @return
	 */
	private List<?> getCurPageList(List<?> list, int page, int listSize) throws Exception {
		int start = listSize * (page - 1); // 페이지 당 데이터 목록 수 * (현재 페이지 번호 -1)
		int end = listSize * page; // 페이지 당 데이터 목록 수 * 현재 페이지 번호
		if (end > list.size()) {
			end = list.size();
		} // 마지막 페이지라면 리스트의 size 까지만 가져온다.
		return list.subList(start, end); // 현재 페이지에 보여줄 목록 담기
	}

	/**
	 * @Desc : 회원 보유 쿠폰 시퀀스 조회
	 * @Method Name : getMemberCouponHoldSeq
	 * @Date : 2019. 3. 6.
	 * @Author : 이동엽
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public String getMemberCouponHoldSeq(String memberNo) throws Exception {
		return mbMemberCouponDao.selectMemberCouponHoldSeq(memberNo);
	}

	/**
	 * @Desc :환불계좌 인증
	 * @Method Name : setAccountParam
	 * @Date : 2019. 4. 25.
	 * @Author : 최경호
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getAccountAuth(MbMember params) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		AccountAuth accountAuth = new AccountAuth();

		paramMap = this.setAccountParam(params); // 계좌인증에 필요한 파라미터를 세팅
		Map<String, Object> result = accountAuth.start(paramMap);
		String flag = "Y".equals(result.get("flag")) ? Const.BOOLEAN_TRUE : Const.BOOLEAN_FALSE;

		MbMemberCertificationHistory memberCertificationHistory = new MbMemberCertificationHistory();

		memberCertificationHistory.setMemberNo(params.getMemberNo());
		memberCertificationHistory.setCrtfcPathCode(CommonCode.CRTFC_PATH_CODE_REFUND_ACCOUNT);
		memberCertificationHistory.setCrtfcTypeCode(CommonCode.CRTFC_TYPE_CODE_REFUND_ACCOUNT);
		memberCertificationHistory.setCrtfcNoText(null);
		memberCertificationHistory.setCrtfcNoSendInfo(null);
		memberCertificationHistory.setAccessIpText(systemService.getIpAddress());
		memberCertificationHistory.setSessionId(UtilsRequest.getSession().getId());
		memberCertificationHistory.setCrtfcSuccessYn(flag);
		memberCertificationHistory.setCrtfcDtm(UtilsDate.getSqlTimeStamp());

		mbMemberCertificationHistoryDao.insert(memberCertificationHistory);

		return result;
	}

	/**
	 * @Desc :환불계좌 파라미터 세팅
	 * @Method Name : setAccountParam
	 * @Date : 2019. 4. 25.
	 * @Author : 최경호
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> setAccountParam(MbMember mbMember) throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("url", Config.getString("nice.account.url")); // 계좌인증 URL
		paramMap.put("niceUid", Config.getString("nice.account.niceUid")); // ID
		paramMap.put("svcPwd", Config.getString("nice.account.svcPwd")); // PW
		paramMap.put("inq_rsn", Config.getString("nice.account.inq_rsn")); // 조회사유[10:회원가입 20:기존회원가입 30:성인인증 40:비회원확인
																			// 90:기타사유]
		paramMap.put("strGbn", Config.getString("nice.account.strGbn")); // 개인[1]/사업자[2]
		paramMap.put("service", Config.getString("nice.account.serviceGroup2").split("\\|")[0]); // 서비스구분[1=계좌소유주확인
																									// 2=계좌성명확인
																									// 3=계좌유효성확인]
		paramMap.put("svcGbn", Config.getString("nice.account.serviceGroup2").split("\\|")[1]); // 업무구분

		paramMap.put("strAccountNo", mbMember.getAcntNoText()); // 계좌번호
		paramMap.put("strBankCode", mbMember.getBankCode()); // 환불은행코드
		paramMap.put("strNm", mbMember.getAcntHldrName()); // 예금주명
		return paramMap;
	}

	/**
	 * @Desc : 회원 인증번호 발송 (기본 카카오)
	 * @Method Name : createCertNumberHdphn
	 * @Date : 2019. 5. 9.
	 * @Author : 이동엽
	 * @param certificationVO
	 * @throws Exception
	 */
	public String createCertNumberHdphn(CertificationVO certificationVO) throws Exception {
		return this.createCertNumberHdphn(certificationVO, CommonCode.SEND_TYPE_CODE_KKO);
	}

	/**
	 * @Desc : 회원 인증번호 발송 (발송형태 지정가능)
	 * @Method Name : createCertNumberHdphn
	 * @Date : 2019. 5. 9.
	 * @Author : 이동엽
	 * @param certificationVO
	 * @throws Exception
	 */
	public String createCertNumberHdphn(CertificationVO certificationVO, String sendTypeCode) throws Exception {
		MbMemberCertificationHistory memberCertificationHistory = new MbMemberCertificationHistory();

		String certificationNumber = UtilsHashKey.makeRandomNumber(Const.CERTIFY_NUMBER_DIGIT, true);
		String sessionId = UtilsRequest.getSession().getId();
		certificationVO.setCertificationNumber(certificationNumber);

		MessageVO messageVO = new MessageVO();
		messageVO.setMesgId(MessageCode.CERT_NUMBER_HDPHN);
		messageVO.setSndrName(Const.SYS_SENDER_MESSAGE_NAME);
		messageVO.setSendTelNoText(Const.SYS_SENDER_MESSAGE_NUMBER);
		messageVO.setRecvTelNoText(certificationVO.getCrtfcNoSendInfo());
		messageVO.setRcvrName(certificationVO.getMemberName());
		messageVO.setMessageTemplateModel(certificationVO);
		messageVO.setSendTypeCode(sendTypeCode);
		messageVO.setReal(true);

		messageService.setSendMessageProcess(messageVO);

		memberCertificationHistory.setMemberNo(certificationVO.getMemberNo());
		memberCertificationHistory.setCrtfcPathCode(certificationVO.getCrtfcPathCode());
		memberCertificationHistory.setCrtfcTypeCode(BaseCommonCode.CRTFC_TYPE_CODE_PHONE);
		memberCertificationHistory.setCrtfcNoText(certificationNumber);
		memberCertificationHistory.setCrtfcNoSendInfo(certificationVO.getCrtfcNoSendInfo());
		memberCertificationHistory.setAccessIpText(systemService.getIpAddress());
		memberCertificationHistory.setSessionId(sessionId);
		memberCertificationHistory.setCrtfcSuccessYn(Const.BOOLEAN_FALSE);

		mbMemberCertificationHistoryDao.insertCertification(memberCertificationHistory);

		return certificationNumber;
	}

	/**
	 * @Desc : 인증 번호 검증
	 * @Method Name : setValidateCertificationNumber
	 * @Date : 2019. 5. 9.
	 * @Author : 이동엽
	 * @param certificationVO
	 * @throws Exception
	 */
	public void setValidateCertificationNumber(CertificationVO certificationVO) throws Exception {
		MbMemberCertificationHistory memberCertificationHistory = new MbMemberCertificationHistory();

		memberCertificationHistory.setCrtfcNoSendInfo(certificationVO.getCrtfcNoSendInfo());
		memberCertificationHistory.setCrtfcNoText(certificationVO.getCrtfcNoText());

		memberCertificationHistory.setCrtfcSuccessYn(Const.BOOLEAN_FALSE);
		if (UtilsText.equals(certificationVO.getForceLoginYn(), Const.BOOLEAN_TRUE)) {
			memberCertificationHistory.setCrtfcSuccessYn(Const.BOOLEAN_TRUE);
		}

		memberCertificationHistory = mbMemberCertificationHistoryDao.selectCertification(memberCertificationHistory);

		if (!UtilsText.equals(memberCertificationHistory.getCrtfcNoText(), certificationVO.getCrtfcNoText())) {
			throw new Exception("정확한 인증번호 6자리를 입력해주세요.");
		}

		memberService.updateCertificationNumber(memberCertificationHistory);
	}

	/**
	 * @Desc : 인증번호 히스토리 업데이트
	 * @Method Name : updateCertificationNumber
	 * @Date : 2019. 5. 9.
	 * @Author : 이동엽
	 * @param memberCertificationHistory
	 */
	public void updateCertificationNumber(MbMemberCertificationHistory memberCertificationHistory) {
		memberCertificationHistory.setCrtfcSuccessYn(Const.BOOLEAN_TRUE);

		mbMemberCertificationHistoryDao.updateCertificationNumber(memberCertificationHistory);
	}

	/**
	 *
	 * @Desc : 메뉴/URL 접근 로그 남기기
	 * @Method Name : setMenuAccessLogging
	 * @Date : 2019. 7. 15.
	 * @Author : 최경호
	 * @param sqlTrace
	 * @throws Exception
	 */
	public void setMenuAccessLogging(SQLTrace sqlTrace) throws Exception {
		mbMemberDao.insertMenuAccessLogging(sqlTrace);
	}

	/**
	 * @Desc : 회원 단골매장 조회
	 * @Method Name : getMemberInterestStore
	 * @Date : 2019. 7. 24.
	 * @Author : 이동엽
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public List<MbMemberInterestStore> getMemberInterestStore(String memberNo) throws Exception {
		return mbMemberInterestStoreDao.selectMemberInterestStore(memberNo);
	}

	/**
	 * @Desc : 후기 작성 포인트 지급 현황
	 * @Method Name : getMemberReviewPointStats
	 * @Date : 2019. 8. 7.
	 * @Author : hsjhsj19
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMemberReviewPointStats(String memberNo) throws Exception {
		BdProductReview review = new BdProductReview();
		review.setMemberNo(memberNo);
		return productReviewService.getMemberReviewPointStats(review);
	}

	/**
	 * @Desc : AppPush 대상 회원 조회
	 * @Method Name : selectAppPushMemberList
	 * @Date : 2019. 8. 8.
	 * @Author : 이동엽
	 * @param mbMember
	 * @return
	 * @throws Exception
	 */
	public List<MbMember> selectAppPushMemberList(MbMember mbMember) throws Exception {
		return mbMemberDao.selectAppPushMemberList(mbMember);
	}

	/**
	 * @Desc : 상품문의 답변현황
	 * @Method Name : getMemberProductInquiryStats
	 * @Date : 2019. 8. 20.
	 * @Author : hsjhsj19
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMemberProductInquiryStats(String memberNo) throws Exception {
		BdProductInquiry inquiry = new BdProductInquiry();
		inquiry.setMemberNo(memberNo);
		return productInquiryService.getMemberInquiryStats(inquiry);
	}

	/**
	 * @Desc : 임직원 인증 이력 조회
	 * @Method Name : getEmpCertfiHistory
	 * @Date : 2019. 8. 26.
	 * @Author : 이동엽
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public MbEmployeeCertificationHistory getEmpCertfiHistory(String memberNo) throws Exception {
		return mbEmployeeCertificationHistoryDao.selectEmpCertfiHistory(memberNo);
	}

	/**
	 * @Desc : 임직원 인증 초기화 업데이트
	 * @Method Name : setEmpCertReset
	 * @Date : 2019. 8. 27.
	 * @Author : 이동엽
	 * @param mbMember
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setEmpCertReset(MbMember mbMember) throws Exception {
		int cnt = 0;
		Map<String, Object> result = new HashMap<>();
		UserDetails userDetails = LoginManager.getUserDetails();

		mbMember.setEmpCrtfcFailCount((short) 0);
		mbMember.setEmpCrtfcInitDtm(UtilsDate.getSqlTimeStamp());
		mbMember.setModerNo(userDetails.getAdminNo());
		mbMember.setModDtm(UtilsDate.getSqlTimeStamp());

		cnt = memberService.updateMemberDefaultData(mbMember);

		if (cnt > 0) {
			result.put("result", Const.BOOLEAN_TRUE);
			result.put("resultMsg", Message.getMessage("member.msg.emp.cert.reset"));
		} else {
			result.put("result", Const.BOOLEAN_FALSE);
			result.put("resultMsg", Message.getMessage("member.error.emp.cert"));

		}
		return result;
	}

	/**
	 * @Desc : 임직원 정보 세팅
	 * @Method Name : setEmpInfo
	 * @Date : 2019. 8. 30.
	 * @Author : 이동엽
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public MemberEmpInfoVO setEmpInfo(MbMember mbMember) throws Exception {

		MemberEmpInfoVO memberEmpInfo = new MemberEmpInfoVO();
		EmployBalance employBalance = new EmployBalance();
		EmployCertReport employCertReport = new EmployCertReport();
		MbEmployeeCertificationHistory empCertfi = new MbEmployeeCertificationHistory();

		SimpleDateFormat formatter = new SimpleDateFormat(Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS);
		String empSaleDate = formatter.format(UtilsDate.getSqlTimeStamp());

		try {
			empCertfi = memberService.getEmpCertfiHistory(mbMember.getMemberNo());

			// 임직원 인증 기본정보
			employCertReport = membershipPointService.employeeCertificationByEmployNum(empCertfi.getEmpNoText(),
					empCertfi.getEmpNm());
			// 임직원 한도조회
			employBalance = membershipPointService.getBalanceByEmployBalance(empCertfi.getEmpNoText(), empSaleDate);

			memberEmpInfo.setEmpCd(employCertReport.getEmpCd());
			memberEmpInfo.setEmpNm(employCertReport.getEmpNm());
			memberEmpInfo.setJikChaek(employCertReport.getJikChaek());
			memberEmpInfo.setDeptCd(employCertReport.getDeptCd());
			memberEmpInfo.setRetdt(employCertReport.getRetdt());
			memberEmpInfo.setEntDt(employCertReport.getEntDt());
			memberEmpInfo.setBalance(Integer.parseInt(employBalance.getBalance()));

		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return memberEmpInfo;
	}

	/**
	 * @Desc : 통합멤버십 회원수, 온라인 회원수, 멤버십 전환수를 조회(통계)
	 * @Method Name : getMemberStatusCount
	 * @Date : 2019. 9. 4.
	 * @Author : 고웅환
	 * @param
	 * @return
	 * @throws Exception
	 */
	public List<MbMember> getMemberStatusCount() throws Exception {
		return mbMemberDao.selectMemberStatusCount();
	}
	
	/**
	 * @Desc : 회원 탈퇴
	 * @Method Name : setLeaveProcess
	 * @Date : 2020. 4. 29.
	 * @Author : 3top
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setLeaveProcess(MbMember member) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		// 주문,배송,클래임 데이터 조회
		OcOrder ocOrder = new OcOrder();
		ocOrder.setMemberNo(member.getMemberNo());
		OrderOtherPartVo orderOtherPartVo = orderOtherPartService.getProgressOrderClmAsCount(ocOrder);

		if (orderOtherPartVo.getOrderCnt() > 0 || orderOtherPartVo.getClaimCnt() > 0
				|| orderOtherPartVo.getAsCnt() > 0) {
			result.put("orderCnt", orderOtherPartVo.getOrderCnt());
			result.put("claimCnt", orderOtherPartVo.getClaimCnt());
			result.put("asCnt", orderOtherPartVo.getAsCnt());
			result.put("success", "0"); // 성공여부 실패[0]로 세팅

			return result;
		} else {
			member.setLeaveYn(Const.BOOLEAN_TRUE);
			member.setLeaveGbnCode(CommonCode.LEAVE_GBN_CODE_LONG_FORCED);
			result.put("success", String.valueOf(mbMemberDao.updateLeave(member)));

			// 탈퇴 시 연결된 SNS 계정을 연결해지
			// memberService.setSnsAccountDisconnect(member.getMemberNo());
		}

		return result;
	}
	
	/**
	 * @Desc      	: 과거 포인트 조회
	 * @Method Name : getHistoryPoint
	 * @Date  	  	: 2020. 5. 27.
	 * @Author    	: 이동엽
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<HistoryPoint> getHistoryPoint(Pageable<MemberSearchVO, HistoryPoint> pageable) throws Exception{
		
		MbMember member = new MbMember();
		List<HistoryPoint> historyPoint = new ArrayList<HistoryPoint>();
		
		// 회원정보 조회
		member.setMemberNo(pageable.getBean().getMemberNo());
		MbMember detailData = memberService.getMember(member);
		
		if (UtilsText.isNotBlank(detailData.getSafeKey())) {
			historyPoint = membershipPointService.getApplyEventPointHistory(detailData.getSafeKey());
		}
		
		pageable.setTotalCount(historyPoint.size());
		
		if (historyPoint.size() > 0) {
			pageable.setContent((List<HistoryPoint>) this.getCurPageList(historyPoint, pageable.getPageNum(),pageable.getRowsPerPage()));
		}

		return pageable.getPage();
	}
}
