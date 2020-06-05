package kr.co.abcmart.bo.afterService.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.util.Calendar;

import kr.co.abcmart.bo.afterService.model.master.OcAsAccept;
import kr.co.abcmart.bo.afterService.model.master.OcAsAcceptAttachFile;
import kr.co.abcmart.bo.afterService.model.master.OcAsAcceptMemo;
import kr.co.abcmart.bo.afterService.model.master.OcAsAcceptProduct;
import kr.co.abcmart.bo.afterService.model.master.OcAsAcceptProductStatusHistory;
import kr.co.abcmart.bo.afterService.model.master.OcAsPayment;
import kr.co.abcmart.bo.afterService.repository.master.OcAsAcceptAttachFileDao;
import kr.co.abcmart.bo.afterService.repository.master.OcAsAcceptDao;
import kr.co.abcmart.bo.afterService.repository.master.OcAsAcceptMemoDao;
import kr.co.abcmart.bo.afterService.repository.master.OcAsAcceptProductDao;
import kr.co.abcmart.bo.afterService.repository.master.OcAsAcceptProductStatusHistoryDao;
import kr.co.abcmart.bo.afterService.repository.master.OcAsPaymentDao;
import kr.co.abcmart.bo.afterService.vo.OcAfterServiceAmountExcelVo;
import kr.co.abcmart.bo.afterService.vo.OcAfterServiceExcelVo;
import kr.co.abcmart.bo.claim.model.master.OcClaim;
import kr.co.abcmart.bo.claim.service.ClaimService;
import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.repository.master.MbMemberDao;
import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.bo.order.model.master.OcOrderDeliveryHistory;
import kr.co.abcmart.bo.order.model.master.OcOrderProduct;
import kr.co.abcmart.bo.order.repository.master.OcOrderDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderDeliveryHistoryDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderProductDao;
import kr.co.abcmart.bo.order.service.OrderService;
import kr.co.abcmart.bo.product.model.master.PdProductRelationFile;
import kr.co.abcmart.bo.product.repository.master.PdProductRelationFileDao;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.interfaces.module.payment.PaymentEntrance;
import kr.co.abcmart.interfaces.module.payment.base.PaymentResult;
import kr.co.abcmart.interfaces.module.payment.base.model.PaymentInfo;
import kr.co.abcmart.interfaces.module.payment.config.KcpPaymentConfig;
import kr.co.abcmart.interfaces.module.payment.kcp.model.KcpPaymentCancel;
import kr.co.abcmart.interfaces.module.payment.kcp.model.KcpPaymentCancelReturn;
import kr.co.abcmart.interfaces.zinterfacesdb.service.InterfacesClaimService;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsDevice;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : AS관리
 * @FileName : AfterServiceService.java
 * @Project : abc.bo
 * @Date : 2019. 2. 14.
 * @Author : dtimer2@3top.co.kr
 */
@Slf4j
@Service
public class AfterServiceService {

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private OrderService orderservice;

	@Autowired
	private OcOrderProductDao orderproductdao;

	@Autowired
	private OcOrderDao orderdao;

	@Autowired
	private MbMemberDao memberDao;

	@Autowired
	private OcAsAcceptDao ocAfterServiceDao;

	@Autowired
	private OcAsAcceptAttachFileDao ocAsAcceptAttchFileDao;

	@Autowired
	private OcAsAcceptProductDao ocAsAcceptProductDao;

	@Autowired
	private OcAsAcceptProductStatusHistoryDao ocAsAcceptProductStatusHistoryDao;

	@Autowired
	private AfterServiceService afterService;

	@Autowired
	private OcAsPaymentDao ocAsPaymentDao;

	@Autowired
	private OcAsAcceptMemoDao ocAsAcceptMemoDao;

	@Autowired
	PaymentEntrance payment;

	@Autowired
	KcpPaymentConfig kcpPaymentConfig;

	@Autowired
	private ClaimService claimService;

	@Autowired
	private AfterServiceMessageService afterServiceMessageService;

	@Autowired
	private PdProductRelationFileDao pdProductRelationFileDao;

	@Autowired
	private OcOrderDeliveryHistoryDao ocOrderDeliveryHistoryDao;

	@Autowired
	InterfacesClaimService interfacesClaimService;

	/**************************** S : 이상호 *****************************/

	/**
	 * 
	 * @Desc : AS 코드 그룹
	 * @Method Name : getCodeListByGroup
	 * @Date : 2019. 2. 14.
	 * @Author : dtimer2@3top.co.kr
	 * @param codeFields
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<SyCodeDetail>> getCodeListByGroup(String[] codeFields) throws Exception {

		return commonCodeService.getCodeListByGroup(codeFields);
	}

	/**
	 * 
	 * @Desc : 사이트 목록 조회
	 * @Method Name : getSiteList
	 * @Date : 2019. 2. 14.
	 * @Author : dtimer2@3top.co.kr
	 * @return
	 * @throws Exception
	 */
	public List<SySite> getSiteList() throws Exception {
		return siteService.getSiteList();
	}

	/**
	 * @Desc : 주문번호 및 순번으로 OC_ORDER , OC_ORDER_PRODUCT selectByPromaryKey 키로 조회하여
	 *       필요한 정보만 골라쓴다.
	 * @Method Name :
	 * @Date : 2019. 2. 14.
	 * @Author : dtimer2@3top.co.kr
	 * @param orderPrdtSeq
	 * @return
	 */
	public Map<String, Object> getOrderPrdDetailInfo(OcOrderProduct ordProduct) throws Exception {
		ModelMap resultMap = new ModelMap();

		// 주문 상품 int orderPrdtSeq
		OcOrderProduct asOrdPrdDEtail = orderproductdao.selectByPrimaryKey(ordProduct);

		PdProductRelationFile prdtRltnFile = new PdProductRelationFile();
		prdtRltnFile.setPrdtNo(asOrdPrdDEtail.getPrdtNo());
		prdtRltnFile.setPrdtRltnFileSeq(1);
		prdtRltnFile = pdProductRelationFileDao.selectByPrimaryKey(prdtRltnFile);

		asOrdPrdDEtail.setImageUrl(prdtRltnFile.getImageUrl());
		asOrdPrdDEtail.setAltrnText(prdtRltnFile.getAltrnText());

		// 주문 마스터 조회 String orderNo
		String ordNo = (String) ordProduct.getOrderNo();
		OcOrder newOrd = new OcOrder();
		newOrd.setOrderNo(ordNo);

		OcOrder asOr = orderdao.selectByPrimaryKey(newOrd);
		// 키로 조회를 해서 코드 번호로 넘어옴. 다시 셀렉트 처리함.
		String siteName = ocAfterServiceDao.selectSiteNameInfo(asOr.getSiteNo());

		// 유저정보 조회 String memberNo A/S 접수시 email , 문자 , 카톡에 대한 정보 수신 여부 조회
		MbMember member = new MbMember();
		member.setMemberNo(asOr.getMemberNo());
		MbMember asMemberDeliInfo = memberDao.selectByPrimaryKey(member);

		// BO에서의 접수자, 처리자는 로그인한 관리자 기준으로
		UserDetails user = LoginManager.getUserDetails();
		// 택배사 리스트 ??

		resultMap.put("asOrdPrdDEtail", asOrdPrdDEtail);
		resultMap.put("asOrd", asOr);
		resultMap.put("asMemberDeliInfo", asMemberDeliInfo);
		resultMap.put("userInfo", user);
		resultMap.put("siteName", siteName);
		return resultMap;
	}

	/**
	 * @Desc : 재접수 하기 위한 접수번호로 조회
	 * @Method Name : getSelectAfterServiceOld
	 * @Date : 2019. 3. 19.
	 * @Author : lee
	 * @param asAcceptNo
	 * @param asAcceptPrdtSeq
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSelectAfterServiceOld(OcAsAccept ocAsaccept) throws Exception {
		ModelMap resultMap = new ModelMap();

		// 접수마스터 조회
		OcAsAccept ocAccept = new OcAsAccept();
		ocAccept.setAsAcceptNo(ocAsaccept.getAsAcceptNo());
		OcAsAccept ocAcceptOldData = ocAfterServiceDao.selectByPrimaryKey(ocAccept);

		// 접수상품 조회
		OcAsAcceptProduct ocAsAcceptProduct = new OcAsAcceptProduct();
		ocAsAcceptProduct.setAsAcceptNo(ocAsaccept.getAsAcceptNo());
		ocAsAcceptProduct.setAsAcceptPrdtSeq(ocAsaccept.getAsAcceptPrdtSeq());
		OcAsAcceptProduct ocAsAcceptProductOld = ocAsAcceptProductDao.selectByPrimaryKey(ocAsAcceptProduct);

		PdProductRelationFile pdProductRelationFile = new PdProductRelationFile();
		pdProductRelationFile.setPrdtNo(ocAsAcceptProductOld.getPrdtNo());
		pdProductRelationFile.setPrdtRltnFileSeq(1);
		pdProductRelationFile = pdProductRelationFileDao.selectByPrimaryKey(pdProductRelationFile);

		ocAsAcceptProductOld.setImageUrl(pdProductRelationFile.getImageUrl());
		ocAsAcceptProductOld.setAltrnText(pdProductRelationFile.getAltrnText());

		// 키로 조회를 해서 코드 번호로 넘어옴. 다시 셀렉트 처리함.
		String siteName = ocAfterServiceDao.selectSiteNameInfo(ocAcceptOldData.getSiteNo());

		// 유저정보 조회 String memberNo A/S 접수시 email , 문자 , 카톡에 대한 정보 수신 여부 조회
		MbMember member = new MbMember();
		member.setMemberNo(ocAcceptOldData.getMemberNo());
		MbMember asMemberDeliInfo = memberDao.selectByPrimaryKey(member);

		// BO에서의 접수자, 처리자는 로그인한 관리자 기준으로
		UserDetails user = LoginManager.getUserDetails();

		resultMap.put("ocAsAcceptProductOld", ocAsAcceptProductOld);
		resultMap.put("ocAcceptOldData", ocAcceptOldData);
		resultMap.put("asMemberDeliInfo", asMemberDeliInfo);
		resultMap.put("userInfo", user);
		resultMap.put("siteName", siteName);
		return resultMap;
	}

	/**
	 * 
	 * @Desc : A/S 목록 조회
	 * @Method Name : getAfterServiceList
	 * @Date : 2019. 2. 21.
	 * @Author : dtimer2@3top.co.kr
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<OcAsAccept> getAfterServiceList(Pageable<OcAsAccept, OcAsAccept> pageable) throws Exception {

		int count = ocAfterServiceDao.selectAfterServiceListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			List<OcAsAccept> ocAsAcceptList = ocAfterServiceDao.selectAfterServiceList(pageable);
			for (OcAsAccept ocAsAccept : ocAsAcceptList) {
				ocAsAccept.setIsListYn(Const.BOOLEAN_TRUE);
			}
			pageable.setContent(ocAsAcceptList);
		}

		return pageable.getPage();
	}

	/**
	 * 
	 * @Desc : 접수번호 상세 조회
	 * @Method Name : getAfterServiceDetailInfo
	 * @Date : 2019. 2. 22.
	 * @Author : dtimer2@3top.co.kr
	 * @param asAcceptNo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getAfterServiceDetailInfo(OcAsAccept ocAsaccept) throws Exception {
		ModelMap resultMap = new ModelMap();

		OcAsAccept dataInfo = ocAfterServiceDao.selectAfterServiceDetailInfo(ocAsaccept);

		OcAsAcceptProduct dataSubInfo = null;
		OcAsPayment dataPayInfo = null;
		List<OcAsAcceptMemo> memoData = null;

		if (dataInfo != null) {

			// 관리자 메모 조회
			OcAsAcceptMemo ocAsAcceptMemo = new OcAsAcceptMemo();
			ocAsAcceptMemo.setAsAcceptNo(dataInfo.getAsAcceptNo());
			memoData = afterService.getOcAsAcceptMemoData(ocAsAcceptMemo);

			// 접수 상품에 등록된 데이타 있는지 확인
			OcAsAcceptProduct ocAsAcceptProduct = new OcAsAcceptProduct();
			ocAsAcceptProduct.setAsAcceptNo(dataInfo.getAsAcceptNo());
			ocAsAcceptProduct.setAsAcceptPrdtSeq(dataInfo.getAsAcceptPrdtSeq());

			dataSubInfo = ocAsAcceptProductDao.selectByPrimaryKey(ocAsAcceptProduct);

			if ("N".equals(dataInfo.getAdminAcceptYn())) {
				// 결제 정보
				OcAsPayment ocAsPaymentParam = new OcAsPayment();
				ocAsPaymentParam.setAsAcceptNo(dataInfo.getAsAcceptNo());
				// TODO
				ocAsPaymentParam.setAsPymntSeq(dataInfo.getAsAcceptPrdtSeq());
				dataPayInfo = ocAsPaymentDao.selectDetailPayInfo(ocAsPaymentParam);

				OcAsAcceptProductStatusHistory ocAsAcceptProductStatusHistory = new OcAsAcceptProductStatusHistory();
				ocAsAcceptProductStatusHistory.setAsAcceptNo(dataInfo.getAsAcceptNo());
				ocAsAcceptProductStatusHistory.setAsAcceptPrdtSeq(dataInfo.getAsAcceptPrdtSeq());

			}
		} else {
			resultMap.put("errorMessage", "존재하지 않는 접수번호 입니다.");
		}
		// TODO

		resultMap.put("dataInfo", dataInfo);
		resultMap.put("dataSubInfo", dataSubInfo);
		resultMap.put("memoData", memoData);
		resultMap.put("dataPayInfo", dataPayInfo);

		return resultMap;
	}

	/**
	 * @Desc : 관리자 메모를 조회
	 * @Method Name : getOcAsAcceptMemoData
	 * @Date : 2019. 3. 19.
	 * @Author : lee
	 * @param ocAsAcceptMemo
	 * @return
	 * @throws Exception
	 */
	public List<OcAsAcceptMemo> getOcAsAcceptMemoData(OcAsAcceptMemo ocAsAcceptMemo) throws Exception {
		return ocAsAcceptMemoDao.selectOcAsAcceptMemoData(ocAsAcceptMemo);
	}

	/**
	 * 
	 * @Desc : 접수번호 SEQ 채번
	 * @Method Name : getAsAcceptNoNextVal
	 * @Date : 2019. 2. 26.
	 * @Author : dtimer2@3top.co.kr
	 * @throws Exception
	 */
	public String getAsAcceptNoNextVal() throws Exception {
		// 접수번호 채번

		SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS);
		Date toDay = Calendar.getInstance().getTime();
		String orderNo = dateFormat.format(toDay);
		orderNo = orderNo.concat(String.format("%05d", ocAfterServiceDao.selectAsAcceptNoNextVal()));
		return orderNo;
	}

	/**
	 * @Desc : OC_AS_ACCEPT 테이블 조회
	 * @Method Name : getSelectByPrimaryKey
	 * @Date : 2019. 2. 26.
	 * @Author : dtimer2@3top.co.kr
	 * @param ocAsAccept
	 * @return
	 * @throws Exception
	 */
	public OcAsAccept getSelectByOcAsAcceptPrimaryKey(OcAsAccept ocAsAccept) throws Exception {
		// 키로 단건 조회
		OcAsAccept ocAsAcceptTemp = ocAfterServiceDao.selectByPrimaryKey(ocAsAccept);
		return ocAsAcceptTemp;
	}

	/**
	 * @Desc : 접수 마스터 SETTER
	 * @Method Name : setOcAsAcceptParam
	 * @Date : 2019. 2. 26.
	 * @Author : dtimer2@3top.co.kr
	 * @param ocAsAcceptParam
	 * @param user
	 * @throws Exception
	 */
	public void setOcAsAcceptParam(String asAcceptNo, OcAsAccept ocAsAcceptParam, OcAsAccept ocAsAcceptOldParam,
			UserDetails user) throws Exception {
		OcAsAccept newOcAsAccept = new OcAsAccept();

		newOcAsAccept.setAsAcceptNo(asAcceptNo);
		newOcAsAccept.setAsGbnCode(ocAsAcceptParam.getAsGbnCode());
		newOcAsAccept.setSiteNo(ocAsAcceptOldParam.getSiteNo());
		newOcAsAccept.setOrgOrderNo(ocAsAcceptOldParam.getOrgOrderNo());
		newOcAsAccept.setOrgOrderDtm(ocAsAcceptOldParam.getOrgOrderDtm());
		newOcAsAccept.setOrderNo(ocAsAcceptOldParam.getOrderNo());
		newOcAsAccept.setMemberNo(ocAsAcceptOldParam.getMemberNo());
		newOcAsAccept.setMemberTypeCode(ocAsAcceptOldParam.getMemberTypeCode());
		newOcAsAccept.setMbshpGradeCode(ocAsAcceptOldParam.getMbshpGradeCode());
		newOcAsAccept.setEmpYn(ocAsAcceptOldParam.getEmpYn());
		newOcAsAccept.setOtsVipYn(ocAsAcceptOldParam.getOtsVipYn());
		newOcAsAccept.setDeviceCode(UtilsDevice.getDeviceCode());
		newOcAsAccept.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
		newOcAsAccept.setAdminAcceptYn(Const.BOOLEAN_TRUE);
		newOcAsAccept.setBuyerPostCodeText(ocAsAcceptParam.getBuyerPostCodeText());
		newOcAsAccept.setBuyerPostAddrText(ocAsAcceptParam.getBuyerPostAddrText());
		newOcAsAccept.setBuyerDtlAddrText(ocAsAcceptParam.getBuyerDtlAddrText());
		newOcAsAccept.setBuyerName(ocAsAcceptParam.getBuyerName());
		newOcAsAccept.setBuyerHdphnNoText(ocAsAcceptParam.getBuyerHdphnNoText());
		newOcAsAccept.setRcvrPostCodeText(ocAsAcceptParam.getRcvrPostCodeText());
		newOcAsAccept.setRcvrPostAddrText(ocAsAcceptParam.getRcvrPostAddrText());
		newOcAsAccept.setRcvrDtlAddrText(ocAsAcceptParam.getRcvrDtlAddrText());
		newOcAsAccept.setDlvyTypeCode(ocAsAcceptParam.getDlvyTypeCode());
		newOcAsAccept.setRcvrName(ocAsAcceptParam.getRcvrName());
		newOcAsAccept.setRcvrHdphnNoText(ocAsAcceptParam.getRcvrHdphnNoText());
		newOcAsAccept.setTotalAsAmt(0);
		newOcAsAccept.setAddDlvyAmt(0);
		newOcAsAccept.setTotalPymntAmt(0);
		newOcAsAccept.setUnProcYn(ocAsAcceptParam.getUnProcYn());
		newOcAsAccept.setAdminAcceptYn(Const.BOOLEAN_TRUE);
		
		// 2020.05.08 : 오프라인 접수일 경우가 추가되어 분기
		if( !UtilsText.equals(ocAsAcceptParam.getAsRsnCode(), "10018") ) {
			// 일반적인경우 "AS접수" 상태
			newOcAsAccept.setAsStatCode(CommonCode.AS_STAT_CODE_ACCEPT); 
		}else {
			// 오프라인접수일 경우 "수령완료" 상태
			newOcAsAccept.setAsStatCode(CommonCode.AS_STAT_CODE_RECEIVE_FINISH);
		}
		
		newOcAsAccept.setRgsterNo(user.getAdminNo());
		newOcAsAccept.setRgstDtm(null);
		newOcAsAccept.setModerNo("");

		ocAfterServiceDao.inertOcAsAccept(newOcAsAccept);
	}

	/**
	 * @Desc : OC_AS_ACCEPT 테이블 조회
	 * @Method Name : getSelectByPrimaryKey
	 * @Date : 2019. 2. 26.
	 * @Author : dtimer2@3top.co.kr
	 * @param ocAsAccept
	 * @return
	 * @throws Exception
	 */
	public OcAsAcceptProduct getSelectByOcAsAcceptProductPrimaryKey(OcAsAcceptProduct ocAsAcceptProduct)
			throws Exception {
		// 키로 단건 조회
		OcAsAcceptProduct ocAsAcceptProductData = ocAsAcceptProductDao.selectByPrimaryKey(ocAsAcceptProduct);
		return ocAsAcceptProductData;
	}

	/**
	 * @Desc : AsAcceptProduct SETTER
	 * @Method Name : setOldAsAcceptProductParam
	 * @Date : 2019. 2. 26.
	 * @Author : dtimer2@3top.co.kr
	 * @param asAcceptNo
	 * @param ocAsAcceptParam
	 * @param user
	 * @throws Exception
	 */
	public void setOldAsAcceptProductParam(String asAcceptNo, Short asAcceptPrdtSeq,
			OcAsAcceptProduct ocAsAcceptProductParam, UserDetails user) throws Exception {
		ocAsAcceptProductParam.setAsAcceptNo(asAcceptNo);
		ocAsAcceptProductParam.setAsAcceptPrdtSeq(asAcceptPrdtSeq);
		ocAsAcceptProductParam.setAsDlbrtCode(UtilsText.EMPTY);
		ocAsAcceptProductParam.setAsDlbrtContText(UtilsText.EMPTY);
		ocAsAcceptProductParam.setAsProcTypeCode(UtilsText.EMPTY);
		ocAsAcceptProductParam.setAsProcTypeDtlCode(UtilsText.EMPTY);
		ocAsAcceptProductParam.setAsProcContText(UtilsText.EMPTY);
		ocAsAcceptProductParam.setAsAmt(0);
		ocAsAcceptProductParam.setLogisVndrCode(UtilsText.EMPTY);
		ocAsAcceptProductParam.setWaybilNoText(UtilsText.EMPTY);
		ocAsAcceptProductParam.setAsPrdtStatCode(CommonCode.AS_STAT_CODE_ACCEPT);
		ocAsAcceptProductParam.setRgsterNo(user.getAdminNo());
		ocAsAcceptProductParam.setModerNo(UtilsText.EMPTY);
	}

	/**
	 * @Desc : 접수상품 히스토리 SEQ 채번
	 * @Method Name : getAsAcceptPrdtStatHistSeqNextVal
	 * @Date : 2019. 2. 26.
	 * @Author : dtimer2@3top.co.kr
	 * @return
	 * @throws Exception
	 */
	public String getAsAcceptPrdtStatHistSeqNextVal(String asAcceptNo, Short asAcceptPrdtSeq) throws Exception {
		// 접수번호 채번
		String newHistorySeq = ocAsAcceptProductStatusHistoryDao.selectAsAcceptPrdtStatHistSeqNextVal(asAcceptNo,
				asAcceptPrdtSeq);
		return newHistorySeq;
	}

	/**
	 * 
	 * @Desc : 접수 상품이력 테이블에 INSERT
	 * @Method Name : setAsAcceptProductStatusHistoryParam
	 * @Date : 2019. 3. 6.
	 * @Author : dtimer2@3top.co.kr
	 * @param asAcceptNo
	 * @param ocAsAcceptProductStatusHistoryParam
	 * @param newHistorSeq
	 * @param user
	 * @throws Exception
	 */
	public void setAsAcceptProductStatusHistoryParam(String asAcceptNo, Short asAcceptPrdtSeq,
			OcAsAcceptProductStatusHistory ocAsAcceptProductStatusHistoryParam, String newHistorSeq, UserDetails user)
			throws Exception {
		ocAsAcceptProductStatusHistoryParam.setAsAcceptNo(asAcceptNo);
		ocAsAcceptProductStatusHistoryParam.setAsAcceptPrdtSeq(asAcceptPrdtSeq);
		ocAsAcceptProductStatusHistoryParam.setAsAcceptPrdtStatHistSeq(Short.parseShort(newHistorSeq));
		ocAsAcceptProductStatusHistoryParam.setAsPrdtStatCode(CommonCode.AS_STAT_CODE_ACCEPT);
		ocAsAcceptProductStatusHistoryParam.setRgsterNo(user.getAdminNo());
	}

	/**
	 * @Desc : AS완료 , AS불가 , 접수취소 일때 재접수 등록.
	 * @Method Name : setReEntryAfterService
	 * @Date : 2019. 2. 25.
	 * @Author : dtimer2@3top.co.kr
	 * @param ocAsAccept
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setReEntryAfterService(OcAsAccept ocAsAccept) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserDetails user = LoginManager.getUserDetails();

		// 새로운 접수번호 생성
		String newAsAcceptNo = getAsAcceptNoNextVal();

		// 접수 마스터 ( 재접수된 마스터키로 조회)
		OcAsAccept oldAsData = new OcAsAccept();
		oldAsData.setAsAcceptNo(ocAsAccept.getAsAcceptNo());

		// 재접수 대상되는 AS_ACCEPT_NO 로 조회
		OcAsAccept oldAsAcceptData = getSelectByOcAsAcceptPrimaryKey(oldAsData);

		// AS접수 상품테이블 조회 OLD
		OcAsAcceptProduct oldProduct = new OcAsAcceptProduct();
		oldProduct.setAsAcceptNo(ocAsAccept.getAsAcceptNo());
		oldProduct.setAsAcceptPrdtSeq(ocAsAccept.getAsAcceptPrdtSeq());
		OcAsAcceptProduct oldAsAcceptProduct = getSelectByOcAsAcceptProductPrimaryKey(oldProduct);

		OcOrderProduct ocOrderProduct = new OcOrderProduct();
		ocOrderProduct.setOrgOrderNo(oldAsAcceptProduct.getOrgOrderNo());
		ocOrderProduct.setOrderPrdtSeq(oldAsAcceptProduct.getOrderPrdtSeq());

		String validateStr = getValidateOrderProduct(ocOrderProduct);
		if (UtilsText.equals(validateStr, Const.BOOLEAN_FALSE)) {
			resultMap.put("result", validateStr);
			return resultMap;
		}

		// 신규 접수 INSERT
		setOcAsAcceptParam(newAsAcceptNo, ocAsAccept, oldAsAcceptData, user);

		// DATA 존재하면 새로운 접수번호로 INSERT
		oldAsAcceptProduct.setAsRsnCode(ocAsAccept.getAsRsnCode());
		oldAsAcceptProduct.setAsAcceptContText(ocAsAccept.getAsAcceptContText());
		setOldAsAcceptProductParam(newAsAcceptNo, ocAsAccept.getAsAcceptPrdtSeq(), oldAsAcceptProduct, user);
		ocAsAcceptProductDao.insert(oldAsAcceptProduct);

		// 히스토리 시퀀스 채번
		String newHistorSeq = getAsAcceptPrdtStatHistSeqNextVal(newAsAcceptNo, ocAsAccept.getAsAcceptPrdtSeq());
		// AS접수 히스토리 적재
		OcAsAcceptProductStatusHistory histor = new OcAsAcceptProductStatusHistory();
		setAsAcceptProductStatusHistoryParam(newAsAcceptNo, ocAsAccept.getAsAcceptPrdtSeq(), histor, newHistorSeq,
				user);
		ocAsAcceptProductStatusHistoryDao.insert(histor);

		resultMap.put("result", Const.BOOLEAN_TRUE);
		return resultMap;
	}

	/**
	 * 
	 * @Desc : 주문상세 팝업에서 AS신청시
	 * @Method Name : setAfterServiceAccept
	 * @Date : 2019. 3. 5.
	 * @Author : dtimer2@3top.co.kr
	 * @param asAcceptNo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setAfterServiceAccept(OcAsAccept ocAsAccept) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 주문 마스터 조회
		OcOrder ocOrderParam = new OcOrder();
		ocOrderParam.setOrderNo(ocAsAccept.getOrderNo());
		OcOrder ocOrder = orderdao.selectOrderDetail(ocOrderParam);

		// 주문 상품 조회
		OcOrderProduct ocOrderProductParam = new OcOrderProduct();
		ocOrderProductParam.setOrderNo(ocAsAccept.getOrderNo());
		ocOrderProductParam.setOrderPrdtSeq(ocAsAccept.getOrderPrdtSeq());

		OcOrderProduct ocOrderProduct = orderproductdao.selectOnlyProductDetail(ocOrderProductParam);

		UserDetails user = LoginManager.getUserDetails();
		// 신규 채번하고 시작
		String newAsAcceptNo = getAsAcceptNoNextVal();

		// BO 접수 마스터를 SET
		ocAsAccept.setAsAcceptNo(newAsAcceptNo);
		ocAsAccept.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
		ocAsAccept.setAdminAcceptYn(Const.BOOLEAN_TRUE);
		ocAsAccept.setAsStatCode(CommonCode.AS_STAT_CODE_ACCEPT); 
		afterService.setOcAsAcceptNew(newAsAcceptNo, ocOrder, ocAsAccept, user);

		// BO접수 상세를 SET
		afterService.setOcAsAcceptProduct(newAsAcceptNo, ocAsAccept, ocOrder, ocOrderProduct);

		// 히스토리 시퀀스 채번
		String newHistorSeq = getAsAcceptPrdtStatHistSeqNextVal(newAsAcceptNo, ocAsAccept.getAsAcceptPrdtSeq());
		// AS접수 히스토리 적재
		OcAsAcceptProductStatusHistory histor = new OcAsAcceptProductStatusHistory();
		setAsAcceptProductStatusHistoryParam(newAsAcceptNo, ocAsAccept.getAsAcceptPrdtSeq(), histor, newHistorSeq,
				user);
		ocAsAcceptProductStatusHistoryDao.insert(histor);

		// 이메일 , 카톡 문자 알림 서비스 시작
		try {
			// 심의/수선 접수와 동시에 접수완료 - BO접수
			// afterServiceMessageService.asAcceptComplete(ocAsAccept);
		} catch (Exception e) {
			resultMap.put("result", Const.BOOLEAN_TRUE);
		}

		// 주문상세 값을 전달
		resultMap.put("result", Const.BOOLEAN_TRUE);

		return resultMap;
	}

	/**
	 * 
	 * @Desc : 접수 상세 INSERT
	 * @Method Name : setOcAsAcceptProduct
	 * @Date : 2019. 3. 6.
	 * @Author : dtimer2@3top.co.kr
	 * @param siteNo
	 * @param ocAsAccept
	 * @throws Exception
	 */
	public void setOcAsAcceptProduct(String newAsAcceptNo, OcAsAccept ocAsAccept, OcOrder ocOrder,
			OcOrderProduct ocOrderProduct) throws Exception {

		OcAsAcceptProduct newOcAsProduct = new OcAsAcceptProduct();
		newOcAsProduct.setAsAcceptNo(newAsAcceptNo);
		newOcAsProduct.setAsAcceptPrdtSeq(ocAsAccept.getAsAcceptPrdtSeq());
		newOcAsProduct.setOrgOrderNo(ocOrder.getOrgOrderNo());
		newOcAsProduct.setOrderNo(ocOrder.getOrderNo());
		newOcAsProduct.setOrderPrdtSeq(ocOrderProduct.getOrderPrdtSeq());
		newOcAsProduct.setPrdtNo(ocOrderProduct.getPrdtNo());
		newOcAsProduct.setPrdtOptnNo(ocOrderProduct.getPrdtOptnNo());
		newOcAsProduct.setPrdtName(ocOrderProduct.getPrdtName());
		newOcAsProduct.setEngPrdtName(ocOrderProduct.getEngPrdtName());
		newOcAsProduct.setOptnName(ocOrderProduct.getOptnName());
		newOcAsProduct.setPrdtTypeCode(ocOrderProduct.getPrdtTypeCode());
		newOcAsProduct.setStyleInfo(ocOrderProduct.getStyleInfo());
		newOcAsProduct.setPrdtColorCode(ocOrderProduct.getPrdtColorCode());
		newOcAsProduct.setBrandNo(ocOrderProduct.getBrandNo());
		newOcAsProduct.setOrderAmt(ocOrderProduct.getOrderAmt());
		newOcAsProduct.setAsRsnCode(ocAsAccept.getAsRsnCode());
		newOcAsProduct.setAsAcceptContText(ocAsAccept.getAsAcceptContText());
		
		// 2020.05.08 : 오프라인 접수일 경우가 추가되어 분기
		if( !UtilsText.equals(ocAsAccept.getAsRsnCode(), "10018") ) {
			// 일반적인경우 "AS접수" 상태
			newOcAsProduct.setAsPrdtStatCode(CommonCode.AS_STAT_CODE_ACCEPT);
		}else {
			// 오프라인접수일 경우 "수령완료" 상태
			newOcAsProduct.setAsPrdtStatCode(CommonCode.AS_STAT_CODE_RECEIVE_FINISH);
		}
		
		newOcAsProduct.setRgsterNo(LoginManager.getUserDetails().getAdminNo());

		ocAsAcceptProductDao.inertOcAsAcceptProduct(newOcAsProduct);

	}

	/**
	 * 
	 * @Desc : 상태를 업데이트 한다.
	 * @Method Name : setAfterServiceStat
	 * @Date : 2019. 3. 7.
	 * @Author : dtimer2@3top.co.kr
	 */
	public Map<String, Object> setAfterServiceStat(OcAsAccept ocAsAccept) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 접수 상세 조회
		OcAsAccept ocAsAcceptData = ocAfterServiceDao.selectAfterServiceDetailInfo(ocAsAccept);

		// 접수마스터
		ocAsAccept.setAsStatCode(ocAsAccept.getAsStatCode());
		ocAsAccept.setModerNo(LoginManager.getUserDetails().getAdminNo());
		ocAfterServiceDao.updateOcAsAccept(ocAsAccept);

		// 접수상품 정보 업데이트
		OcAsAcceptProduct ocAsAcceptProduct = new OcAsAcceptProduct();
		ocAsAcceptProduct.setAsAcceptNo(ocAsAccept.getAsAcceptNo());
		ocAsAcceptProduct.setAsAcceptPrdtSeq(ocAsAccept.getAsAcceptPrdtSeq());
		ocAsAcceptProduct.setAsRsnCode(ocAsAccept.getAsRsnCode());
		ocAsAcceptProduct.setAsAcceptContText(ocAsAccept.getAsAcceptContText());
		ocAsAcceptProduct.setAsProcTypeCode(ocAsAccept.getAsProcTypeCode());
		ocAsAcceptProduct.setAsProcTypeDtlCode(ocAsAccept.getAsProcTypeDtlCode());
		ocAsAcceptProduct.setAsProcContText(ocAsAccept.getAsProcContText());
		ocAsAcceptProduct.setAsAmt(ocAsAccept.getAsAmt());
		ocAsAcceptProduct.setLogisVndrCode(ocAsAccept.getLogisVndrCode());
		ocAsAcceptProduct.setWaybilNoText(ocAsAccept.getWaybilNoText());
		ocAsAcceptProduct.setAsPrdtStatCode(ocAsAccept.getAsStatCode());
		ocAsAcceptProduct.setModerNo(LoginManager.getUserDetails().getAdminNo());
		ocAsAcceptProductDao.updateOcAsAcceptProduct(ocAsAcceptProduct);

		// 교환 또는 반품 일경우
		if (UtilsText.equals(ocAsAccept.getAsProcTypeDtlCode(), CommonCode.AS_PROC_TYPE_DTL_CODE_CHANGE)
				|| UtilsText.equals(ocAsAccept.getAsProcTypeDtlCode(), CommonCode.AS_PROC_TYPE_DTL_CODE_RETURN)) {

			// 클레임에 넘길 OC_AS_ACCEPT_PRODUCT
			OcAsAcceptProduct ocAsAcceptProductCliam = new OcAsAcceptProduct();
			ocAsAcceptProductCliam.setAsAcceptNo(ocAsAccept.getAsAcceptNo());
			ocAsAcceptProductCliam.setAsAcceptPrdtSeq(ocAsAccept.getAsAcceptPrdtSeq());
			OcAsAcceptProduct acceptProducData = ocAsAcceptProductDao.selectByPrimaryKey(ocAsAcceptProductCliam);

			acceptProducData.setMemberNo(ocAsAcceptData.getMemberNo());

			OcClaim ocClaim = new OcClaim();

			if (UtilsText.equals(ocAsAccept.getAsProcTypeDtlCode(), CommonCode.AS_PROC_TYPE_DTL_CODE_CHANGE)) {
				ocClaim = claimService.registClaimExchangeFromAfterservice(acceptProducData);
			} else if (UtilsText.equals(ocAsAccept.getAsProcTypeDtlCode(), CommonCode.AS_PROC_TYPE_DTL_CODE_RETURN)) {
				ocClaim = claimService.registClaimReturnFromAfterservice(acceptProducData);
			}

			resultMap.put("clmNo", ocClaim.getClmNo());
			resultMap.put("clmGbnCode", ocClaim.getClmGbnCode());
		}

		// AS처리유형코드가 '수선불가' 이고
		if (ocAsAccept.getAsProcTypeCode() != null) {
			if (UtilsText.equals(CommonCode.AS_PROC_TYPE_CODE_REPAIR_REJECT, ocAsAccept.getAsProcTypeCode())) {
				// 2020.02.08 : AS처리유형디테일코드가 '반송'일 경우만
				if (UtilsText.equals(CommonCode.AS_PROC_TYPE_DTL_CODE_REJECT, ocAsAccept.getAsProcTypeDtlCode())) {
					log.debug("AS처리유형코드가 '수선불가' 이고 , AS처리유형디테일코드가 '반송'일 경우.");
					// 이메일 , 카톡 문자 알림 서비스 시작
					try {
						// 수선 불가 반송
						afterServiceMessageService.asRepairImpsbltSendBack(ocAsAccept);
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				}
			}
		}

		// 접수히스토리
		// 히스토리 시퀀스 채번
		String newHistorSeq = getAsAcceptPrdtStatHistSeqNextVal(ocAsAccept.getAsAcceptNo(),
				ocAsAccept.getAsAcceptPrdtSeq());
		// AS접수 히스토리 적재
		OcAsAcceptProductStatusHistory histor = new OcAsAcceptProductStatusHistory();
		histor.setAsAcceptNo(ocAsAccept.getAsAcceptNo());
		histor.setAsAcceptPrdtSeq(ocAsAccept.getAsAcceptPrdtSeq());
		histor.setAsAcceptPrdtStatHistSeq(Short.parseShort(newHistorSeq));
		histor.setAsPrdtStatCode(ocAsAccept.getAsStatCode());
		histor.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
		ocAsAcceptProductStatusHistoryDao.insert(histor);

		// AS 회송지시 IF 주문 배송 히스토리 이력 조회(단건)
		OcOrderDeliveryHistory ocOrderDeliveryHistory = null;

		if (UtilsText.equals(CommonCode.AS_STAT_CODE_PICKUP_ORDER, ocAsAccept.getAsStatCode())) {

			/**
			 * 회수지시 종전 인터페이스에 일반택배용과 픽업상품용 두 가지를 사용 일반택배용은 초도 주문배송 delivery id 기준으로만 회수지시
			 * 픽업상품용은 회수지 주소를 받아 주소정보를 넘겨 회수지시 일반택배 주문의 회수지 주소 변경의 상황을 고려하여 픽업상품 회수지시로만 통일하여
			 * 사용하기로 함
			 */

			OcOrderDeliveryHistory ocOrderDeliveryHistoryParam = new OcOrderDeliveryHistory();
			ocOrderDeliveryHistoryParam.setOrderNo(ocAsAcceptData.getOrderNo());
			ocOrderDeliveryHistoryParam.setOrderPrdtSeq(ocAsAcceptData.getOrderPrdtSeq());

			ocOrderDeliveryHistory = ocOrderDeliveryHistoryDao.selectAfterServicePickup(ocOrderDeliveryHistoryParam);
			if (ocOrderDeliveryHistory == null) {
				throw new Exception("배송지 내역이 없습니다.");
			}
			String cbcd = ""; // B코드(재고구분)
			String maejangCd = ""; // ERP매장코드
			String dlvyId = ""; // 배송ID
			String postNum = ""; // 회수지 우편번호
			String addr = ""; // 회수지 주소
			String addrDtl = ""; // 회수지 상세주소
			String hdphnNum = ""; // 회수 발송자 전화번호
			String rcvrName = ""; // 회수 발송자명
			String prdtCode = ""; // 업체상품코드
			String optionValue = ""; // 옵션번호

			switch (ocOrderDeliveryHistory.getStockGbnCode()) {
			case CommonCode.STOCK_GBN_CODE_AI:
				cbcd = CommonCode.STOCK_GBN_CODE_IMINFO_AI;
				break;
			case CommonCode.STOCK_GBN_CODE_AW:
				cbcd = CommonCode.STOCK_GBN_CODE_IMINFO_AW;
				break;
			case CommonCode.STOCK_GBN_CODE_AS:
				cbcd = CommonCode.STOCK_GBN_CODE_IMINFO_AS;
				break;
			default:
				break;
			}

			maejangCd = ocOrderDeliveryHistory.getInsdMgmtInfoText();
			dlvyId = ocOrderDeliveryHistory.getDlvyIdText();
			postNum = ocAsAcceptData.getBuyerPostCodeText();
			addr = ocAsAcceptData.getBuyerPostAddrText();
			addrDtl = ocAsAcceptData.getBuyerDtlAddrText();
			hdphnNum = ocAsAcceptData.getBuyerHdphnNoText();
			rcvrName = ocAsAcceptData.getBuyerName();
			prdtCode = ocAsAcceptData.getVndrPrdtNoText();
			optionValue = ocAsAcceptData.getPrdtOptnNo();

			// 픽업상품 회수 지시(일반택배 주문의 회수지 변경 상황을 고려하여 일반/픽업 구분 없이 픽업용으로 사용)
			// TODO WMS 오픈되면 주석 풀어야 됨
			// boolean result = true;
			boolean result = interfacesClaimService.updateOrderReturnProductPickUpNoTrx(cbcd, maejangCd, dlvyId,
					postNum, addr, addrDtl, hdphnNum, rcvrName, prdtCode, optionValue);

			if (!result) {
				throw new Exception("AS 회송(수거)지시 Fail");
			}

			/*
			 * Map<String, String> orderParamMap = new HashMap<>();
			 * orderParamMap.put("dlvyIdText", dlvyId); orderParamMap.put("callType", "AS");
			 * 
			 * // 2020.02.18 : 회수지시 프로시져 호출 생성 프로시져 / 성공 : "0" String resultOrderRetrun =
			 * claimService.setCallProcedureForOrderReturn(orderParamMap);
			 * 
			 * if (!UtilsText.equals(resultOrderRetrun, "0")) {
			 * log.error("resultOrderRetrun : {}", resultOrderRetrun); throw new
			 * Exception("회수지시 프로시져 호출에 실패하였습니다."); }
			 */

			// 이메일 , 카톡 문자 알림 서비스 시작
			try {
				// 심의/수선 접수와 동시에 접수완료 - BO접수
				afterServiceMessageService.asAcceptComplete(ocAsAccept);
			} catch (Exception e) {
				log.error("심의/수선 접수와 동시에 접수완료 메일 오류 ");
				log.error(e.getMessage());
			}

		} else if (UtilsText.equals(CommonCode.AS_STAT_CODE_RECEIVE_FINISH, ocAsAccept.getAsStatCode())) {
			log.debug("AS상품 수령 완료일때 메일 , 문자, 카톡 등 보냄.");
			log.info("AS상품 수령 완료일때 메일 , 문자, 카톡 등 보냄.");

			// 이메일 , 카톡 문자 알림 서비스 시작
			try {
				// 수선/심의 상품 수령 완료 (메세지 만)
				afterServiceMessageService.asReceiptComplete(ocAsAccept);
			} catch (Exception e) {
				log.error("AS상품 수령 완료일때 메일 , 문자, 카톡 등 오류");
				log.error(e.getMessage());
			}

		} else if (UtilsText.equals(CommonCode.AS_STAT_CODE_PAYMENT_REQUEST, ocAsAccept.getAsStatCode())) {
			log.debug("결제 요청 일때 메일 , 문자, 카톡 등 보냄.");
			log.info("결제 요청 일때 메일 , 문자, 카톡 등 보냄.");

			// 이메일 , 카톡 문자 알림 서비스 시작
			try {
				// 추가 결제금 발생
				afterServiceMessageService.asRepairAmtOccurrence(ocAsAccept);
			} catch (Exception e) {
				log.error("결제 요청 일때 메일 , 문자, 카톡 등 오류 ");
				log.error(e.getMessage());
			}

		} else if (UtilsText.equals(CommonCode.AS_STAT_CODE_SHIPPING, ocAsAccept.getAsStatCode())) {
			log.debug("배송중일 때 메일 , 문자, 카톡 등 보냄.");
			log.info("배송중일 때 메일 , 문자, 카톡 등 보냄.");

			// 이메일 , 카톡 문자 알림 서비스 시작
			try {
				// 수선 완료 발송
				afterServiceMessageService.asRepairDeliveryProc(ocAsAccept);
			} catch (Exception e) {
				log.error("배송중일 때 메일 , 문자, 카톡 등 오류 ");
				log.error(e.getMessage());
			}
		}

		// 상태코드 , 관리자 접수 여부 , 사유구분
		resultMap.put("asStatCode", ocAsAccept.getAsStatCode());
		resultMap.put("adminAcceptYn", ocAsAccept.getAdminAcceptYn());
		resultMap.put("asGbnCode", ocAsAccept.getAsGbnCode());

		return resultMap;

	}

	/**
	 * @Desc : 임시저장 (배송중 처리 전 단계까지 )
	 * @Method Name : setAfterServiceTemporary
	 * @Date : 2019. 3. 13.
	 * @Author : dtimer2@3top.co.kr
	 */
	public Map<String, Object> setAfterServiceTemporary(OcAsAccept ocAsAccept) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 접수마스터
		ocAsAccept.setModerNo(LoginManager.getUserDetails().getAdminNo());
		ocAfterServiceDao.updateOcAsAcceptTempory(ocAsAccept);

		// 접수상품 정보에 항목이 될지 대응하기 위해
		OcAsAcceptProduct ocAsAcceptProduct = new OcAsAcceptProduct();
		ocAsAcceptProduct.setAsAcceptNo(ocAsAccept.getAsAcceptNo());
		ocAsAcceptProduct.setAsAcceptPrdtSeq(ocAsAccept.getAsAcceptPrdtSeq());
		ocAsAcceptProduct.setAsRsnCode(ocAsAccept.getAsRsnCode());
		ocAsAcceptProduct.setAsAcceptContText(ocAsAccept.getAsAcceptContText());
		ocAsAcceptProduct.setAsProcTypeCode(ocAsAccept.getAsProcTypeCode());
		ocAsAcceptProduct.setAsProcTypeDtlCode(ocAsAccept.getAsProcTypeDtlCode());
		ocAsAcceptProduct.setAsProcContText(ocAsAccept.getAsProcContText());
		ocAsAcceptProduct.setAsAmt(ocAsAccept.getAsAmt());
		ocAsAcceptProduct.setLogisVndrCode(ocAsAccept.getLogisVndrCode());
		ocAsAcceptProduct.setWaybilNoText(ocAsAccept.getWaybilNoText());
		ocAsAcceptProduct.setModerNo(LoginManager.getUserDetails().getAdminNo());
		ocAsAcceptProductDao.updateOcAsAcceptProduct(ocAsAcceptProduct);

		// 상태코드 , 관리자 접수 여부 , 사유구분
		resultMap.put("asStatCode", ocAsAccept.getAsStatCode());
		resultMap.put("adminAcceptYn", ocAsAccept.getAdminAcceptYn());
		resultMap.put("asGbnCode", ocAsAccept.getAsGbnCode());

		return resultMap;

	}

	/**
	 * @Desc : 관리자 메모 등록
	 * @Method Name : setOcAsAcceptMemo
	 * @Date : 2019. 3. 13.
	 * @Author : dtimer2@3top.co.kr
	 * @param ocAsAcceptMemo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setOcAsAcceptMemo(OcAsAcceptMemo ocAsAcceptMemo) throws Exception {
		ModelMap result = new ModelMap();
		UserDetails user = LoginManager.getUserDetails();
		ocAsAcceptMemo.setRgsterNo(user.getAdminNo());

		// 개행 처리
		if (ocAsAcceptMemo.getMemoText() != null) {
			String replaceCont = ocAsAcceptMemo.getMemoText().replace("\n", Const.STRING_HTML_BR_TAG);
			ocAsAcceptMemo.setMemoText(replaceCont);
		}

		// 접수관리자 메모를 등록한다.
		ocAsAcceptMemoDao.insertOcAsAcceptMemo(ocAsAcceptMemo);

		// 등록한 메모의 정보를 조회
		result.addAttribute("memoInfo", afterService.getOcAsAcceptMemoData(ocAsAcceptMemo));

		return result;
	}

	/**
	 * @Desc : 접수 관리자 메모 삭제
	 * @Method Name : deleteOcAsAcceptMemo
	 * @Date : 2019. 3. 8.
	 * @Author : dtimer2@3top.co.kr
	 * @param ocAsAcceptMemo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateOcAsAcceptMemo(OcAsAcceptMemo ocAsAcceptMemo) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		ocAsAcceptMemo.setDelYn(Const.BOOLEAN_TRUE);
		ocAsAcceptMemo.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
		int resultCnt = ocAsAcceptMemoDao.updateOcAsAcceptMemo(ocAsAcceptMemo);

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
	 * @Desc : AS목록 전체 다운로드
	 * @Method Name : getOcAsAcceptForAllExcelList
	 * @Date : 2019. 3. 13.
	 * @Author : dtimer2@3top.co.kr
	 * @param ocAsAccept
	 * @return
	 * @throws Exception
	 */
	public List<OcAfterServiceExcelVo> getOcAsAcceptForAllExcelList(OcAsAccept ocAsAccept) throws Exception {

		return ocAfterServiceDao.selectOcAfterServiceForAllExcelList(ocAsAccept);
	}

	/**
	 * @Desc : AS목록 선택 다운로드
	 * @Method Name : getOcAsAcceptForExcelList
	 * @Date : 2019. 3. 13.
	 * @Author : dtimer2@3top.co.kr
	 * @param ocAfterServiceExcelVo
	 * @return
	 * @throws Exception
	 */
	public List<OcAfterServiceExcelVo> getOcAsAcceptForExcelList(OcAfterServiceExcelVo ocAfterServiceExcelVo)
			throws Exception {

		return ocAfterServiceDao.selectOcAfterServiceForExcelList(ocAfterServiceExcelVo);
	}

	/**
	 * @Desc : 사용자 접수시 첨부파일 목록
	 * @Method Name : getAfterServiceAttachFileInfo
	 * @Date : 2019. 3. 11.
	 * @Author : dtimer2@3top.co.kr
	 * @param ocAsAcceptAttachFile
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getAfterServiceAttachFileInfo(OcAsAcceptAttachFile ocAsAcceptAttachFile)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		// 공지 첨부 파일 정보
		OcAsAcceptAttachFile ocAsAcceptAttachFileInfo = new OcAsAcceptAttachFile();
		ocAsAcceptAttachFileInfo.setAsAcceptNo(ocAsAcceptAttachFile.getAsAcceptNo());
		ocAsAcceptAttachFileInfo.setAsAcceptPrdtSeq(ocAsAcceptAttachFile.getAsAcceptPrdtSeq());
		result.put("afterServiceAttachFileList", getAfterServiceAttachFile(ocAsAcceptAttachFileInfo));

		return result;
	}

	/**
	 * @Desc : 첨부파일 조회
	 * @Method Name : getAfterServiceAttachFile
	 * @Date : 2019. 3. 11.
	 * @Author : dtimer2@3top.co.kr
	 * @param ocAsAcceptAttachFileParam
	 * @return
	 * @throws Exception
	 */
	public List<OcAsAcceptAttachFile> getAfterServiceAttachFile(OcAsAcceptAttachFile ocAsAcceptAttachFileParam)
			throws Exception {

		return ocAsAcceptAttchFileDao.selectAfterServiceAttachFileList(ocAsAcceptAttachFileParam);
	}

	/**
	 * @Desc : AS금액관리
	 * @Method Name : getAfterServiceAmtList
	 * @Date : 2019. 3. 20.
	 * @Author : dtimer2@3top.co.kr
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<OcAsPayment> getAfterServiceAmtList(Pageable<OcAsPayment, OcAsPayment> pageable) throws Exception {

		int count = ocAsPaymentDao.selectAfterServiceAmtListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			List<OcAsPayment> ocAsPaymentList = ocAsPaymentDao.selectAfterServiceAmtList(pageable);
			for (OcAsPayment oAsPayment : ocAsPaymentList) {
				oAsPayment.setIsListYn(Const.BOOLEAN_TRUE);
			}
			pageable.setContent(ocAsPaymentList);
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : AS 금액 관리 목록 전체 다운로드
	 * @Method Name : getAsAmountForAllExcelList
	 * @Date : 2019. 3. 20.
	 * @Author : dtimer2@3top.co.kr
	 * @param ocAsAccept
	 * @return
	 * @throws Exception
	 */
	public List<OcAfterServiceAmountExcelVo> getAsAmountForAllExcelList(OcAsPayment ocAsPayment) throws Exception {

		return ocAsPaymentDao.selectOcAfterServiceAmountForAllExcelList(ocAsPayment);
	}

	/**
	 * @Desc : AS 금액 관리 목록 선택 다운로드
	 * @Method Name : getAsAmountForExcelList
	 * @Date : 2019. 3. 20.
	 * @Author : dtimer2@3top.co.kr
	 * @param ocAfterServiceExcelVo
	 * @return
	 * @throws Exception
	 */
	public List<OcAfterServiceAmountExcelVo> getAsAmountForExcelList(
			OcAfterServiceAmountExcelVo ocAfterServiceAmountExcelVo) throws Exception {

		return ocAsPaymentDao.selectOcAfterServiceAmountForExcelList(ocAfterServiceAmountExcelVo);
	}

	/**
	 * @Desc : 배송비에 대한 결제 취소
	 * @Method Name : updateOcAsAcceptPaymentCancel
	 * @Date : 2019. 3. 22.
	 * @Author : dtimer2@3top.co.kr
	 * @param ocAsPayment
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateOcAsAcceptPaymentCancel(OcAsPayment ocAsPayment) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		int resultCnt = 0;

		// 접수 마스터 조회
		OcAsAccept ocAsAccept = new OcAsAccept();
		ocAsAccept.setAsAcceptNo(ocAsPayment.getAsAcceptNo());

		// 접수마스터 data 정보
		OcAsAccept ocAsAcceptData = ocAfterServiceDao.selectByPrimaryKey(ocAsAccept);
		System.out.println(ocAsAcceptData.toString());
		//

		// 결제정보를 조회한다.
		OcAsPayment ocAsPaymentData = ocAsPaymentDao.selectByPrimaryKey(ocAsPayment);

		// 결제 테이블 업데이트 정보 SET
		OcAsPayment ocAsPaymentUpdate = new OcAsPayment();

		// 추가 배송비 계산된 변수
		int addDlvyAmt = 0;
		// 합계금액에서 결제 취소 금액 만큼 처리 변수
		int totalPymntAmt = 0;
		// 결제가 취소 되었다면 접수 마스터 업데이트 한다.

		if (ocAsAcceptData.getAddDlvyAmt() != 0) {
			addDlvyAmt = ocAsPaymentData.getPymntAmt() - ocAsAcceptData.getAddDlvyAmt();
			log.info("결제한 금액을 마이너스 처리하여 접수마스터 금액을 차감함. " + addDlvyAmt);
		} else {
			addDlvyAmt = 0;
		}
		// 합계 금액
		if (ocAsAcceptData.getTotalPymntAmt() != 0) {
			totalPymntAmt = ocAsAcceptData.getTotalPymntAmt() - ocAsPaymentData.getPymntAmt();
			log.info("결제한 금액을 마이너스 처리하여 접수마스터 합계  금액을 차감함. " + totalPymntAmt);
		} else {
			totalPymntAmt = 0;
		}
		// ocAsAccept.setAsAcceptNo(ocAsAccept.getAsAcceptNo());
		ocAsAcceptData.setAddDlvyAmt(0);
		ocAsAcceptData.setTotalPymntAmt(totalPymntAmt);
		ocAsAcceptData.setModerNo(LoginManager.getUserDetails().getAdminNo());
		resultCnt = ocAfterServiceDao.updateOcAsAcceptAddDlvyAmtMinus(ocAsAcceptData);

		// AS 결제 취소
		KcpPaymentCancel kcpPaymentCancel = new KcpPaymentCancel();
		if (UtilsText.equals(ocAsAcceptData.getSiteNo(), Const.SITE_NO_ART)) {
			kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.claim.siteCode"));
			kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.claim.siteKey"));
		} else {
			kcpPaymentCancel.setSiteCd(Config.getString("pg.kcp.ots.claim.siteCode"));
			kcpPaymentCancel.setSiteKey(Config.getString("pg.kcp.ots.claim.siteKey"));
		}

		kcpPaymentCancel.setTno(ocAsPaymentData.getPymntAprvNoText()); // 결제 승인번호
		kcpPaymentCancel.setModType(CommonCode.KCP_MOD_TYPE_STSC); // 전체취소 STSC / 부분취소 STPC
		kcpPaymentCancel.setCustIp(kcpPaymentCancel.getCustIp()); // 변경 요청자 IP
		kcpPaymentCancel.setModDesc("AS접수철회에 의한 취소 "); // 취소 사유

		PaymentResult result = payment.cancel(new PaymentInfo(Const.PAYMENT_VENDOR_NAME_KCP, kcpPaymentCancel));
		KcpPaymentCancelReturn kcpPaymentCancelReturn = ((KcpPaymentCancelReturn) result.getData());

		log.debug("########################{}###########", result.toString());
		log.debug("########################{}###########", kcpPaymentCancelReturn.toString());

		if (UtilsText.equals(result.getSuccessYn(), Const.BOOLEAN_FALSE)) {
			// 결제 취소 실패하면
			ocAsPaymentUpdate.setAsAcceptNo(ocAsAccept.getAsAcceptNo());
			ocAsPaymentUpdate.setAsPymntSeq(ocAsAccept.getAsAcceptPrdtSeq());
			ocAsPaymentUpdate.setRspnsCodeText(kcpPaymentCancelReturn.getResCd()); // 응답코드
			ocAsPaymentUpdate.setRspnsMesgText(kcpPaymentCancelReturn.getResMsg()); // 응답메시지
			ocAsPaymentUpdate.setPymntLogInfo(
					new ObjectMapper().writeValueAsString(kcpPaymentCancelReturn + "::결제실패 데이타 ::" + result.getData()));
			ocAsPaymentUpdate.setModerNo(ocAsAccept.getModerNo());
			ocAsPaymentDao.updateOcAsPaymentAmtCancel(ocAsPaymentUpdate);

			resultMap.put("result", Const.BOOLEAN_TRUE);
			resultMap.put("Message", "OC_AS_PAYMNET UPDATE 실패 :: " + result.getMessage());
			throw new Exception(result.getMessage());
		}
		// 결제 취소 가 정상이면 업데이트 및 결제 결과를 PAYMENT 테이블에 UPDATE
		ocAsPaymentUpdate.setAsAcceptNo(ocAsAccept.getAsAcceptNo());
		ocAsPaymentUpdate.setAsPymntSeq(ocAsPayment.getAsPymntSeq());
		ocAsPaymentUpdate.setRspnsCodeText(kcpPaymentCancelReturn.getResCd()); // 응답코드
		ocAsPaymentUpdate.setRspnsMesgText(kcpPaymentCancelReturn.getResMsg()); // 응답메시지
		ocAsPaymentUpdate.setRedempRfndOpetrNo(ocAsAccept.getModerNo()); // 응답메시지
		ocAsPaymentUpdate.setRedempRfndGbnType(CommonCode.REDEMP_RFND_GBN_TYPE_REFUND);
		ocAsPaymentUpdate.setPymntStatCode(CommonCode.PYMNT_STAT_CODE_PAYMENT_CANCEL);
		ocAsPaymentUpdate
				.setPymntLogInfo(new ObjectMapper().writeValueAsString(kcpPaymentCancelReturn + result.getMessage()));
		ocAsPaymentUpdate.setModerNo(ocAsAccept.getModerNo());

		// OC_AS_PAYMENT UPDATE
		try {
			ocAsPaymentDao.updateOcAsPaymentAmtCancel(ocAsPaymentUpdate);
		} catch (Exception e) {
			resultMap.put("result", Const.BOOLEAN_FALSE);
			resultMap.put("Message", "OC_AS_PAYMNET UPDATE 실패 :: " + e.getMessage());
			throw new Exception("OC_AS_PAYMNET UPDATE 실패");
		}

		if (resultCnt > 0) {
			resultMap.put("result", Const.BOOLEAN_TRUE);
			resultMap.put("Message", "결제가 취소 되었습니다.");
		} else {
			resultMap.put("result", Const.BOOLEAN_FALSE);
			resultMap.put("Message", "결제 취소에 실패하였습니다.");
		}

		return resultMap;
	}

	/**
	 * @Desc :공통코드 & ibsheet combo 필드 data 조회.
	 * @Method Name : getCodeListByGroupByCombo
	 * @Date : 2019. 4. 24.
	 * @Author : lee
	 * @param codeFields
	 * @param isUse
	 * @return
	 * @throws Exception
	 */
	public Pair<JSONObject, Map<String, List<SyCodeDetail>>> getCodeListByGroupByCombo(String[] codeFields,
			boolean isUse) throws Exception {
		return commonCodeService.getCodeListByGroupByCombo(codeFields, isUse);
	}

	/**************************** E : 이상호 *****************************/

	/**************************** S : jeon *****************************/
	/**
	 * @Desc :
	 * @Method Name : selectASListForOrgOrder
	 * @Date : 2019. 2. 25.
	 * @Author : flychani@3top.co.kr
	 * @param ocAsAccept
	 * @return
	 */
	public List<OcAsAccept> selectASListForOrgOrder(OcAsAccept ocAsAccept) throws Exception {
		List<OcAsAccept> orderASList = ocAfterServiceDao.selectASListForOrgOrder(ocAsAccept);

		return orderASList;
	}

	/**
	 * @Desc :
	 * @Method Name : selectOrderAsArr
	 * @Date : 2019. 3. 8.
	 * @Author : flychani@3top.co.kr
	 * @param orderNos
	 * @return
	 */
	public List<OcAsAcceptProduct> selectOrderAsArr(String[] orderNos) throws Exception {
		List<OcAsAcceptProduct> list = ocAsAcceptProductDao.selectOrderAsArr(orderNos);
		return list;
	}

	/**************************** E : jeon *****************************/

	/**************************** S : 이강수 *****************************/

	/**
	 * @Desc : A/S내역 목록 조회 - 주문상세 팝업 클레임 탭
	 * @Method Name : getAsAcceptListInOrderDetailTap
	 * @Date : 2019. 3. 19.
	 * @Author : 이강수
	 * @param OcAsAccept
	 * @return List<OcAsAccept>
	 * @throws Exception
	 */
	public List<OcAsAccept> getAsAcceptListInOrderDetailTap(OcAsAccept ocAsAccept) throws Exception {

		return ocAfterServiceDao.selectAsAcceptListInOrderDetailTap(ocAsAccept);
	}

	/**
	 * @Desc : 1:1문의관리 - AS 상품 상세
	 * @Method Name : getAsPrdtDetailForInquiry
	 * @Date : 2019. 5. 17.
	 * @Author : 이강수
	 * @param OcAsAccept
	 * @return OcAsAcceptProduct
	 * @throws Exception
	 */
	public OcAsAcceptProduct getAsPrdtDetailForInquiry(OcAsAccept ocAsaccept) throws Exception {

		return ocAfterServiceDao.selectAsPrdtDetailForInquiry(ocAsaccept);
	}

	/**************************** E : 이강수 *****************************/

	/**
	 * @Desc : 신규접수 BO 주문관리에서 AS신청시 사용
	 * @Method Name : setOcAsAcceptNew
	 * @Date : 2019. 9. 26.
	 * @Author : lee
	 * @param asAcceptNo
	 * @param ocOrder
	 * @param ocAsAccept
	 * @param user
	 * @throws Exception
	 */
	public void setOcAsAcceptNew(String asAcceptNo, OcOrder ocOrder, OcAsAccept ocAsAccept, UserDetails user)
			throws Exception {
		OcAsAccept newOcAsAccept = new OcAsAccept();

		newOcAsAccept.setAsAcceptNo(asAcceptNo);
		newOcAsAccept.setAsGbnCode(ocAsAccept.getAsGbnCode());
		newOcAsAccept.setSiteNo(ocOrder.getSiteNo());
		newOcAsAccept.setOrgOrderNo(ocOrder.getOrgOrderNo());
		newOcAsAccept.setOrgOrderDtm(ocOrder.getOrderDtm());
		newOcAsAccept.setOrderNo(ocOrder.getOrderNo());
		newOcAsAccept.setMemberNo(ocOrder.getMemberNo());
		newOcAsAccept.setMemberTypeCode(ocOrder.getMemberTypeCode());
		newOcAsAccept.setMbshpGradeCode(ocOrder.getMbshpGradeCode());
		newOcAsAccept.setEmpYn(ocOrder.getEmpYn());
		newOcAsAccept.setOtsVipYn(ocOrder.getOtsVipYn());
		newOcAsAccept.setDeviceCode(UtilsDevice.getDeviceCode());
		newOcAsAccept.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
		newOcAsAccept.setAdminAcceptYn(Const.BOOLEAN_TRUE);
		newOcAsAccept.setBuyerPostCodeText(ocAsAccept.getBuyerPostCodeText());
		newOcAsAccept.setBuyerPostAddrText(ocAsAccept.getBuyerPostAddrText());
		newOcAsAccept.setBuyerDtlAddrText(ocAsAccept.getBuyerDtlAddrText());
		newOcAsAccept.setBuyerName(ocAsAccept.getBuyerName());
		newOcAsAccept.setBuyerHdphnNoText(ocAsAccept.getBuyerHdphnNoText());
		newOcAsAccept.setRcvrPostCodeText(ocAsAccept.getRcvrPostCodeText());
		newOcAsAccept.setRcvrPostAddrText(ocAsAccept.getRcvrPostAddrText());
		newOcAsAccept.setRcvrDtlAddrText(ocAsAccept.getRcvrDtlAddrText());
		newOcAsAccept.setDlvyTypeCode(ocOrder.getDlvyTypeCode());
		newOcAsAccept.setRcvrName(ocAsAccept.getRcvrName());
		newOcAsAccept.setRcvrHdphnNoText(ocAsAccept.getRcvrHdphnNoText());
		newOcAsAccept.setTotalAsAmt(0);
		newOcAsAccept.setAddDlvyAmt(0);
		newOcAsAccept.setTotalPymntAmt(0);
		newOcAsAccept.setUnProcYn(ocAsAccept.getUnProcYn());
		newOcAsAccept.setAdminAcceptYn(Const.BOOLEAN_TRUE);
		
		// 2020.05.08 : 오프라인 접수일 경우가 추가되어 분기
		if( !UtilsText.equals(ocAsAccept.getAsRsnCode(), "10018") ) {
			// 일반적인경우 "AS접수" 상태
			newOcAsAccept.setAsStatCode(CommonCode.AS_STAT_CODE_ACCEPT); 
		}else {
			// 오프라인접수일 경우 "수령완료" 상태
			newOcAsAccept.setAsStatCode(CommonCode.AS_STAT_CODE_RECEIVE_FINISH);
		}
				
		newOcAsAccept.setRgsterNo(user.getAdminNo());
		newOcAsAccept.setRgstDtm(null);
		newOcAsAccept.setModerNo(user.getAdminNo());

		ocAfterServiceDao.inertOcAsAccept(newOcAsAccept);
	}

	/**
	 * @Desc : 주문에 대한 as 상품 조회
	 * @Method Name : getOrderPrdtAsAccept
	 * @Date : 2019. 9. 27.
	 * @Author : flychani@3top.co.kr
	 * @param ocAsAcceptProduct
	 * @return
	 */
	public List<OcAsAcceptProduct> getOrderPrdtAsAccept(OcAsAcceptProduct ocAsAcceptProduct) throws Exception {
		List<OcAsAcceptProduct> orderPrdtAsAccept = ocAsAcceptProductDao.getOrderPrdtAsAccept(ocAsAcceptProduct);
		return orderPrdtAsAccept;
	}

	/**
	 * @Desc : 주문상품 AS접수 시 validate
	 * @Method Name : validateOrderProduct
	 * @Date : 2019. 11. 08.
	 * @Author : 이강수
	 * @param OcOrderProduct
	 * @return String
	 */
	public String getValidateOrderProduct(OcOrderProduct ocOrderProduct) throws Exception {

		// NOT IN AS상품상태코드 배열
		String[] notInAsPrdtStatCodeList = {
				// A/S접수취소
				CommonCode.AS_PRDT_STAT_CODE_ACCEPT_CANCEL
				// A/S접수철회
				, CommonCode.AS_PRDT_STAT_CODE_WITHDRAWAL_ACCEPT
				// A/S완료
				, CommonCode.AS_PRDT_STAT_CODE_AS_FINISH
				// A/S불가
				, CommonCode.AS_PRDT_STAT_CODE_AS_REJECT };
		ocOrderProduct.setNotInAsPrdtStatCodeList(notInAsPrdtStatCodeList);
		// NOT IN 용 클레임상품상태코드리스트
		String[] notInClmPrdtStatCodeList = {
				// 취소접수취소
				CommonCode.CLM_PRDT_STAT_CODE_CANCEL_ACCEPT_CANCEL
				// 취소접수철회
				, CommonCode.CLM_PRDT_STAT_CODE_CANCEL_WITHDRAWAL_ACCEPT
				// 교환접수취소
				, CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_ACCEPT_CANCEL
				// 교환접수철회
				, CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_WITHDRAWAL_ACCEPT
				// 교환불가
				, CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_IMPOSSIBLE
				// 교환완료
				, CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_FINISH
				// 반품접수취소
				, CommonCode.CLM_PRDT_STAT_CODE_RETURN_ACCEPT_CANCEL
				// 반품접수철회
				, CommonCode.CLM_PRDT_STAT_CODE_RETURN_WITHDRAWAL_ACCEPT
				// 반품불가
				, CommonCode.CLM_PRDT_STAT_CODE_RETURN_IMPOSSIBLE };
		ocOrderProduct.setNotInClmPrdtStatCodeList(notInClmPrdtStatCodeList);

		ocOrderProduct.setOrderPrdtStatCode(CommonCode.ORDER_PRDT_STAT_CODE_BUY_CONFIRM);

		int count = orderproductdao.selectOrderProductForAsValidate(ocOrderProduct);

		if (count > 0) {
			return Const.BOOLEAN_TRUE;
		}

		return Const.BOOLEAN_FALSE;
	}

	/**
	 * @Desc : 회원번호로 AS목록 조회
	 * @Method Name : getAsListByMemberNo
	 * @Date : 2020. 1. 29.
	 * @Author : 이강수
	 * @param OcAsAcceptProduct
	 * @return List<OcAsAcceptProduct>
	 */
	public List<OcAsAcceptProduct> getAsListByMemberNo(OcAsAcceptProduct ocAsAcceptProduct) throws Exception {

		return ocAsAcceptProductDao.selectAsListByMemberNo(ocAsAcceptProduct);
	}
}