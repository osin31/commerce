package kr.co.abcmart.bo.delivery.service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ib_renderer.util.Util;
import io.netty.util.internal.StringUtil;
import kr.co.abcmart.bo.claim.model.master.OcClaim;
import kr.co.abcmart.bo.claim.model.master.OcClaimProduct;
import kr.co.abcmart.bo.claim.model.master.OcClaimStatusHistory;
import kr.co.abcmart.bo.claim.repository.master.OcClaimDao;
import kr.co.abcmart.bo.claim.repository.master.OcClaimProductDao;
import kr.co.abcmart.bo.claim.repository.master.OcClaimStatusHistoryDao;
import kr.co.abcmart.bo.cmm.model.master.CmMessageSendHistory;
import kr.co.abcmart.bo.cmm.repository.master.base.BaseCmMessageSendHistoryDao;
import kr.co.abcmart.bo.delivery.model.master.OcDelivery;
import kr.co.abcmart.bo.delivery.repository.master.OcDeliveryDao;
import kr.co.abcmart.bo.delivery.vo.DeliveryOrderNotExcelVO;
import kr.co.abcmart.bo.delivery.vo.DeliveryPrdtVO;
import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.bo.order.model.master.OcOrderDeliveryHistory;
import kr.co.abcmart.bo.order.model.master.OcOrderProduct;
import kr.co.abcmart.bo.order.model.master.OcOrderProductHistory;
import kr.co.abcmart.bo.order.repository.master.IfOrderDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderDeliveryAlertHistoryDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderDeliveryHistoryDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderProductDao;
import kr.co.abcmart.bo.order.repository.master.OcOrderProductHistoryDao;
import kr.co.abcmart.bo.order.service.OrderMessageService;
import kr.co.abcmart.bo.order.service.OrderService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.constant.BaseCommonCode;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.interfaces.zinterfacesdb.service.InterfacesClaimService;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
/**
 *
 * @Desc : 배송관리 Serveice
 * @FileName : DeliveryService.java
 * @Project : abc.bo
 * @Date : 2019. 2. 8.
 * @Author : NKB
 */
public class DeliveryService {

	@Autowired
	private OcDeliveryDao ocDeliveryDao;

	@Autowired
	private OcOrderDeliveryHistoryDao ocOrderDeliveryHistoryDao;

	@Autowired
	private OcOrderDao ocOrderDao;

	@Autowired
	private OcOrderProductDao ocOrderProductDao;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private BaseCmMessageSendHistoryDao baseCmMessageSendHistoryDao;

	@Autowired
	private OcClaimProductDao ocClaimProductDao;

	@Autowired
	private OcClaimStatusHistoryDao ocClaimStatusHistoryDao;

	@Autowired
	private OcClaimDao ocClaimDao;

	@Autowired
	private OcOrderProductHistoryDao ocOrderProductHistoryDao;

	@Autowired
	private OrderMessageService orderMessageService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private IfOrderDao ifOrderDao;

	@Autowired
	private OcOrderDeliveryAlertHistoryDao ocOrderDeliveryAlertHistoryDao;

	@Autowired
	InterfacesClaimService interfacesClaimService;

	/**
	 * @Desc : 배송관리 목록 (자사)
	 * @Method Name : searchDeliveryOrderList
	 * @Date : 2019. 2. 8.
	 * @Author : NKB
	 * @param Pageable<OcDelivery,OcDelivery> pageable
	 * @return Page<OcDelivery>
	 * @throws Exception
	 */
	public Page<OcDelivery> searchDeliveryOrderList(Pageable<OcDelivery, OcDelivery> pageable) throws Exception {

		// 배송목록
		List<OcDelivery> deliveryOrderList = null;

		/* ************** 우선작업 추후 간소화 작업 필요 *****************/
		// 사이트
		String[] siteArr = pageable.getBean().getSiteNoArr();
		if (siteArr != null && !Arrays.asList(siteArr).contains("")) {
			pageable.getBean().setSiteNoList(Arrays.asList(siteArr));
		}

		// 배송유형
		String[] dlvyTypeCodeArr = pageable.getBean().getDlvyTypeCodeArr();
		if (dlvyTypeCodeArr != null && !Arrays.asList(dlvyTypeCodeArr).contains("")) {
			pageable.getBean().setDlvyTypeCodeList(Arrays.asList(dlvyTypeCodeArr));
		}

		// 배송상태
		String[] dlvyStatCodeArr = pageable.getBean().getDlvyStatCodeArr();
		if (dlvyStatCodeArr != null && !Arrays.asList(dlvyStatCodeArr).contains("")) {
			pageable.getBean().setDlvyStatCodeList(Arrays.asList(dlvyStatCodeArr));
		}

		// 발송처
		String[] stockGbnCodeArr = pageable.getBean().getStockGbnCodeArr();
		if (stockGbnCodeArr != null && !Arrays.asList(stockGbnCodeArr).contains("")) {
			pageable.getBean().setStockGbnCodeList(Arrays.asList(stockGbnCodeArr));
		}

		// 결제수단
		String[] pymntMeansCodeArr = pageable.getBean().getPymntMeansCodeArr();
		if (pymntMeansCodeArr != null && !Arrays.asList(pymntMeansCodeArr).contains("")) {
			pageable.getBean().setPymntMeansCodeList(Arrays.asList(pymntMeansCodeArr));
		}
		/* ************** 우선작업 추후 간소화 작업 필요 *****************/
		/*
		 * 기타 param Set
		 */
		// 배송중단여부 N
		pageable.getBean().setDlvyDscntcYn(Const.BOOLEAN_FALSE);
		// 분실처리
		pageable.getBean().setMissProcYn(Const.BOOLEAN_FALSE);
		// 불가 처리 여부 IMPSBLTPROCYN
		pageable.getBean().setImpsbltProcYn(Const.BOOLEAN_FALSE);

		// Total Count
		int totalCount = ocDeliveryDao.selectDeliveryOrderListCount(pageable);

		if (totalCount > 0) {
			deliveryOrderList = ocDeliveryDao.selectDeliveryOrderList(pageable);

			for (OcDelivery ocDelivery : deliveryOrderList) {
				// 목록이기에 권한과 상관없이 마스킹 처리 Y
				ocDelivery.setIsListYn(Const.BOOLEAN_TRUE);
			}
			pageable.setContent(deliveryOrderList);
		}

		pageable.setTotalCount(totalCount);

		return pageable.getPage();
	}

	/**
	 * @Desc : 배송관리 목록 (자사) - 엑셀 전체 다운로드
	 * @Method Name : searchDeliveryOrderAllExcelList
	 * @Date : 2019. 2. 8.
	 * @Author : NKB
	 * @param Pageable<OcDelivery,DeliveryOrderNotExcelVO> pageable
	 * @return Page<OcDelivery>
	 * @throws Exception
	 */
	public Page<DeliveryOrderNotExcelVO> searchDeliveryOrderAllExcelList(
			Pageable<OcDelivery, DeliveryOrderNotExcelVO> pageable) throws Exception {

		/* ************** 우선작업 추후 간소화 작업 필요 *****************/
		// 사이트
		String[] siteArr = pageable.getBean().getSiteNoArr();
		if (siteArr != null && !Arrays.asList(siteArr).contains("")) {
			pageable.getBean().setSiteNoList(Arrays.asList(siteArr));
		}

		// 배송유형
		String[] dlvyTypeCodeArr = pageable.getBean().getDlvyTypeCodeArr();
		if (dlvyTypeCodeArr != null && !Arrays.asList(dlvyTypeCodeArr).contains("")) {
			pageable.getBean().setDlvyTypeCodeList(Arrays.asList(dlvyTypeCodeArr));
		}

		// 배송상태
		String[] dlvyStatCodeArr = pageable.getBean().getDlvyStatCodeArr();
		if (dlvyStatCodeArr != null && !Arrays.asList(dlvyStatCodeArr).contains("")) {
			pageable.getBean().setDlvyStatCodeList(Arrays.asList(dlvyStatCodeArr));
		}

		// 발송처
		String[] stockGbnCodeArr = pageable.getBean().getStockGbnCodeArr();
		if (stockGbnCodeArr != null && !Arrays.asList(stockGbnCodeArr).contains("")) {
			pageable.getBean().setStockGbnCodeList(Arrays.asList(stockGbnCodeArr));
		}

		// 결제수단
		String[] pymntMeansCodeArr = pageable.getBean().getPymntMeansCodeArr();
		if (pymntMeansCodeArr != null && !Arrays.asList(pymntMeansCodeArr).contains("")) {
			pageable.getBean().setPymntMeansCodeList(Arrays.asList(pymntMeansCodeArr));
		}
		/* ************** 우선작업 추후 간소화 작업 필요 *****************/
		/*
		 * 기타 param Set
		 */
		// 배송중단여부 N
		pageable.getBean().setDlvyDscntcYn(Const.BOOLEAN_FALSE);
		// 분실처리
		pageable.getBean().setMissProcYn(Const.BOOLEAN_FALSE);
		// 불가 처리 여부 IMPSBLTPROCYN
		pageable.getBean().setImpsbltProcYn(Const.BOOLEAN_FALSE);

		pageable.setContent(ocDeliveryDao.selectDeliveryOrderListExcel(pageable));

		return pageable.getPage();
	}

	/**
	 * 미출고 현황
	 *
	 * @Desc : 미출고 현황
	 * @Method Name : deliveryOrderNotReadList
	 * @Date : 2019. 2. 12.
	 * @Author : NKB
	 * @param ocDelivery
	 * @return
	 * @throws Exception
	 */
	public Page<OcDelivery> deliveryOrderNotReadList(Pageable<OcDelivery, OcDelivery> pageable) throws Exception {

		// 배송목록
		List<OcDelivery> deliveryOrderNotList = null;

		/* ************** 우선작업 추후 간소화 작업 필요 *****************/
		// 사이트
		String[] siteArr = pageable.getBean().getSiteNoArr();
		if (siteArr != null && !Arrays.asList(siteArr).contains("")) {
			pageable.getBean().setSiteNoList(Arrays.asList(siteArr));
		}

		// 배송유형
		String[] dlvyTypeCodeArr = pageable.getBean().getDlvyTypeCodeArr();
		if (dlvyTypeCodeArr != null && !Arrays.asList(dlvyTypeCodeArr).contains("")) {
			pageable.getBean().setDlvyTypeCodeList(Arrays.asList(dlvyTypeCodeArr));
		}

		// 주문상품상태 - 미출고에서는 결제완료와 상품준비중만 조회한다
		// 10002 - 결제완료 , 10003 - 상품준비중
		String[] dlvyStatCodeArr = { CommonCode.DLVY_STAT_CODE_PAYMENT_FINISH,
				CommonCode.DLVY_STAT_CODE_PRODUCT_PREPARATION };
		if (dlvyStatCodeArr != null && !Arrays.asList(dlvyStatCodeArr).contains("")) {
			pageable.getBean().setDlvyStatCodeList(Arrays.asList(dlvyStatCodeArr));
		}

		// 자바/입점 파라미터 관련 세팅
		String[] mmnyPrdtYnArr = pageable.getBean().getMmnyPrdtYnArr();
		if (mmnyPrdtYnArr != null && !Arrays.asList(mmnyPrdtYnArr).contains("")) {
			pageable.getBean().setMmnyPrdtYnList(Arrays.asList(mmnyPrdtYnArr));
		}

		/*
		 * 배송예외구분 - [OC_ORDER_DELIVERY_HISTORY] define : [상품대기] dlvy_dscntc_yn : 상품대기는
		 * 상품준비중인 상태에서 배송중단 데이타 기준 [분실대기] miss_proc_yn : 분실대기 클레임에서 분실 재배송 접수된 상품기준의 데이타
		 * 기준
		 */
		String[] deliveryEtcStatArr = pageable.getBean().getDeliveryEtcStatArr();
		if (deliveryEtcStatArr != null) {
			// 배송중단여부
			if (Arrays.asList(deliveryEtcStatArr).contains("dlvyDscntcYn")) {
				pageable.getBean().setDlvyDscntcYn(Const.BOOLEAN_TRUE);
				log.debug(":::::::::::::::::::::::::::::::::::::::::::::********** 상품대기 dlvyDscntcYn :::");
			} else {
				pageable.getBean().setDlvyDscntcYn(Const.BOOLEAN_FALSE); // 배송중단여부
			}
			// 분실처리여부
			if (Arrays.asList(deliveryEtcStatArr).contains("missProcYn")) {
				pageable.getBean().setMissProcYn(Const.BOOLEAN_TRUE);
				log.debug(":::::::::::::::::::::::::::::::::::::::::::::********** 분실처리여부 missProcYn :::");
			} else {
				pageable.getBean().setMissProcYn(Const.BOOLEAN_FALSE); // 분실처리여부
			}
		} else {
			pageable.getBean().setDlvyDscntcYn(Const.BOOLEAN_FALSE); // 배송중단여부
			pageable.getBean().setMissProcYn(Const.BOOLEAN_FALSE); // 분실처리여부
		}

		// 불가 처리 여부 IMPSBLTPROCYN
		pageable.getBean().setImpsbltProcYn(Const.BOOLEAN_FALSE);

		/* ************** 우선작업 추후 간소화 작업 필요 *****************/
		log.debug(" pageable.getBean().getOrderDlvOverDay()  ###############################"
				+ pageable.getBean().getOrderDlvOverDay());

		// Total Count
		int totalCount = ocDeliveryDao.selectDeliveryOrderNotCount(pageable);

		if (totalCount > 0) {
			deliveryOrderNotList = ocDeliveryDao.selectDeliveryOrderNotList(pageable);

			for (OcDelivery ocDelivery : deliveryOrderNotList) {
				// 목록이기에 권한과 상관없이 마스킹 처리 Y
				ocDelivery.setIsListYn(Const.BOOLEAN_TRUE);
			}
			pageable.setContent(deliveryOrderNotList);
		}

		pageable.setTotalCount(totalCount);

		return pageable.getPage();
	}

	/**
	 * @Desc : 미출고 엑셀파일 모두 다운로드
	 * @Method Name : getDeliveryOrderNotAllExcel
	 * @Date : 2019. 9. 23.
	 * @Author : 이강수
	 * @param Pageable<OcDelivery, DeliveryOrderNotExcelVO>
	 * @return
	 * @throws Exception
	 */
	public List<DeliveryOrderNotExcelVO> getDeliveryOrderNotAllExcel(
			Pageable<OcDelivery, DeliveryOrderNotExcelVO> pageable) throws Exception {

		// 배송목록
		List<DeliveryOrderNotExcelVO> deliveryOrderNotList = null;

		/* ************** 우선작업 추후 간소화 작업 필요 *****************/
		// 사이트
		String[] siteArr = pageable.getBean().getSiteNoArr();
		if (siteArr != null && !Arrays.asList(siteArr).contains("")) {
			pageable.getBean().setSiteNoList(Arrays.asList(siteArr));
		}

		// 배송유형
		String[] dlvyTypeCodeArr = pageable.getBean().getDlvyTypeCodeArr();
		if (dlvyTypeCodeArr != null && !Arrays.asList(dlvyTypeCodeArr).contains("")) {
			pageable.getBean().setDlvyTypeCodeList(Arrays.asList(dlvyTypeCodeArr));
		}

		// 주문상품상태 - 미출고에서는 결제완료와 상품준비중만 조회한다
		// 10002 - 결제완료 , 10003 - 상품준비중
		String[] dlvyStatCodeArr = { CommonCode.DLVY_STAT_CODE_PAYMENT_FINISH,
				CommonCode.DLVY_STAT_CODE_PRODUCT_PREPARATION };
		if (dlvyStatCodeArr != null && !Arrays.asList(dlvyStatCodeArr).contains("")) {
			pageable.getBean().setDlvyStatCodeList(Arrays.asList(dlvyStatCodeArr));
		}

		/*
		 * 배송예외구분 - [OC_ORDER_DELIVERY_HISTORY] define : [상품대기] dlvy_dscntc_yn : 상품대기는
		 * 상품준비중인 상태에서 배송중단 데이타 기준 [분실대기] miss_proc_yn : 분실대기 클레임에서 분실 재배송 접수된 상품기준의 데이타
		 * 기준
		 */
		String[] deliveryEtcStatArr = pageable.getBean().getDeliveryEtcStatArr();
		if (deliveryEtcStatArr != null) {
			// 배송중단여부
			if (Arrays.asList(deliveryEtcStatArr).contains("dlvyDscntcYn")) {
				pageable.getBean().setDlvyDscntcYn(Const.BOOLEAN_TRUE);
				log.debug(":::::::::::::::::::::::::::::::::::::::::::::********** 상품대기 dlvyDscntcYn :::");
			} else {
				pageable.getBean().setDlvyDscntcYn(Const.BOOLEAN_FALSE); // 배송중단여부
			}
			// 분실처리여부
			if (Arrays.asList(deliveryEtcStatArr).contains("missProcYn")) {
				pageable.getBean().setMissProcYn(Const.BOOLEAN_TRUE);
				log.debug(":::::::::::::::::::::::::::::::::::::::::::::********** 분실처리여부 missProcYn :::");
			} else {
				pageable.getBean().setMissProcYn(Const.BOOLEAN_FALSE); // 분실처리여부
			}
		} else {
			pageable.getBean().setDlvyDscntcYn(Const.BOOLEAN_FALSE); // 배송중단여부
			pageable.getBean().setMissProcYn(Const.BOOLEAN_FALSE); // 분실처리여부
		}

		// 불가 처리 여부 IMPSBLTPROCYN
		pageable.getBean().setImpsbltProcYn(Const.BOOLEAN_FALSE);

		/* ************** 우선작업 추후 간소화 작업 필요 *****************/
		log.debug(" pageable.getBean().getOrderDlvOverDay()  ###############################"
				+ pageable.getBean().getOrderDlvOverDay());

		deliveryOrderNotList = ocDeliveryDao.selectDeliveryOrderNotAllExcel(pageable);

		return deliveryOrderNotList;
	}

	/**
	 * 매장픽업 택배전환
	 *
	 * @Desc :
	 * @Method Name : storePickDeliveryChangeReadList
	 * @Date : 2019. 2. 12.
	 * @Author : NKB
	 * @param ocDelivery
	 * @return
	 * @throws Exception
	 */
	public Page<OcDelivery> storePickDeliveryChangeReadList(Pageable<OcDelivery, OcDelivery> pageable)
			throws Exception {

		/* ************** 우선작업 추후 간소화 작업 필요 *****************/
		log.debug("pageable.getBean().getChanListArr():::::" + pageable.getBean().getChanListArr());
		// 채널
		String[] chanArr = pageable.getBean().getChanListArr();
		if (chanArr != null && !Arrays.asList(chanArr).contains("")) {
			pageable.getBean().setChanList(Arrays.asList(chanArr));
		}

		/* ************** 우선작업 추후 간소화 작업 필요 *****************/

		// 택배전환 목록
		List<OcDelivery> storePickDeliveryChangeList = null;
		pageable.getBean().setLogisCnvrtYn(Const.BOOLEAN_TRUE);

		// 배송중단여부 N
		pageable.getBean().setDlvyDscntcYn(Const.BOOLEAN_FALSE);
		// 분실처리
		pageable.getBean().setMissProcYn(Const.BOOLEAN_FALSE);
		// 불가 처리 여부 IMPSBLTPROCYN
		pageable.getBean().setImpsbltProcYn(Const.BOOLEAN_FALSE);

		log.debug("pageable.getBean().getDlvyStatCode():::::" + pageable.getBean().getDlvyStatCode());

		boolean dlvyStatCode = StringUtil.isNullOrEmpty(pageable.getBean().getDlvyStatCode());
		log.debug("pageable.getBean().getDlvyStatCode():::::" + dlvyStatCode);
		if (dlvyStatCode) {
			pageable.getBean().setDlvyStatCode("");
		}

		// Total Count
		int totalCount = ocDeliveryDao.selectStorePickDeliveryChangeCount(pageable);

		if (totalCount > 0) {
			storePickDeliveryChangeList = ocDeliveryDao.selectStorePickDeliveryChangeList(pageable);

			for (OcDelivery ocDelivery : storePickDeliveryChangeList) {
				ocDelivery.setIsListYn(Const.BOOLEAN_TRUE);
			}
			pageable.setContent(storePickDeliveryChangeList);
		}

		pageable.setTotalCount(totalCount);

		return pageable.getPage();

	}

	/**
	 *
	 * @Desc : 고객주문정보를 조회한다 - by Order
	 * @Method Name : getDeliveryOrderInfo
	 * @Date : 2019. 3. 22.
	 * @Author : NKB
	 * @param ocOrder
	 * @return
	 * @throws Exception
	 */
	public OcOrder getDeliveryOrderInfo(OcOrder ocOrder) throws Exception {

		OcOrder orderDtail = ocOrderDao.selectOrderDetail(ocOrder);

		return orderDtail;
	}

	/**
	 *
	 * @Desc : 택배전환 주문 상품목록 - List
	 * @Method Name : getDeliveryOrderProduct
	 * @Date : 2019. 3. 22.
	 * @Author : NKB
	 * @param ocOrderProduct
	 * @return
	 * @throws Exception
	 */
	public List<OcOrderProduct> getDeliveryOrderProduct(OcDelivery ocDelivery) throws Exception {

		List<OcOrderProduct> ocOrderProductList = ocDeliveryDao.selectOrderProductList(ocDelivery);

		return ocOrderProductList;
	}

	/**
	 *
	 * @Desc : 택배전환 주문 상품목록
	 * @Method Name : getDeliveryOrderProduct
	 * @Date : 2019. 3. 22.
	 * @Author : NKB
	 * @param ocOrderProduct
	 * @return
	 * @throws Exception
	 */
	public List<OcDelivery> getDeliveryOrderProductDelivery(OcDelivery ocDelivery) throws Exception {

		List<OcDelivery> ocOrderProductDeliveryList = ocDeliveryDao.selectOrderProductDeliveryList(ocDelivery);

		return ocOrderProductDeliveryList;
	}

	/**
	 *
	 * @Desc : 택배전환 배송지 주소 등록
	 * @Method Name : storePickDeliveryPopupUpdate
	 * @Date : 2019. 3. 25.
	 * @Author : NKB
	 * @param ocOrderDeliveryChangeHistory
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateStorePickDeliveryPopup(OcOrderDeliveryHistory[] ocOrderDeliveryHistory,
			OcOrder ocOrder) throws Exception {

		Map<String, Object> result = new HashMap<>();
		String adminNo = LoginManager.getUserDetails().getAdminNo();
		boolean smsSendBoolean = false;

		try {
			String orderNo = null;

			// 배송지 변경 시작
			for (int i = 0; i < ocOrderDeliveryHistory.length; i++) {
				OcOrderDeliveryHistory param = ocOrderDeliveryHistory[i];

				log.debug(" param.getOrderPrdtSeq()  ::::::" + param.getOrderPrdtSeq());
				log.debug(" param.getDlvyIdText()  ::::::" + param.getDlvyIdText());

				orderNo = param.getOrderNo();
				/*
				 * OcOrderProduct ocOrderProductParam = new OcOrderProduct();
				 * ocOrderProductParam.setOrderNo(param.getOrderNo());
				 * ocOrderProductParam.setOrderPrdtSeq(param.getOrderPrdtSeq()); OcOrderProduct
				 * prdtInfo = ocOrderProductDao.selectOnlyProductDetail(ocOrderProductParam);
				 */
				// 신규 Insert 진행을 하지 않고 기존 배송 아이디에 배송지 정보를 갱신 한다.
				OcOrderDeliveryHistory ocOrderDeliveryNew = new OcOrderDeliveryHistory();
				ocOrderDeliveryNew.setRcvrName(ocOrder.getRcvrName()); // 수취인명
				ocOrderDeliveryNew.setRcvrTelNoText(ocOrder.getRcvrTelNoText()); // 수취인전화번호
				ocOrderDeliveryNew.setRcvrHdphnNoText(ocOrder.getRcvrHdphnNoText()); // 수취인핸드폰번호
				ocOrderDeliveryNew.setRcvrPostCodeText(ocOrder.getRcvrPostCodeText()); // 수취인우편번호
				ocOrderDeliveryNew.setRcvrPostAddrText(ocOrder.getRcvrPostAddrText()); // 수취인우편주소
				ocOrderDeliveryNew.setRcvrDtlAddrText(ocOrder.getRcvrDtlAddrText()); // 수취인상세주소
				ocOrderDeliveryNew.setDlvyIdText(param.getDlvyIdText());
				ocOrderDeliveryNew.setStockGbnCode("10000"); // 매장배송 택배전환시 택배코드로 전환
				int updateDelivery = ocOrderDeliveryHistoryDao.updateAddressDeliveryHistory(ocOrderDeliveryNew);

				String errorCode = "9";
				if (updateDelivery > 0) {
					// 택배변경 프로시져 호출
					Map<String, String> map = new HashMap<String, String>();
					map.put("dlvyIdText", param.getDlvyIdText());
					ocOrderDeliveryHistoryDao.callProcedureForOrderChangeForDlvyType(map);
					errorCode = String.valueOf(map.get("errorCode"));

					log.debug("callProcedureForOrderChangeForDlvyType : " + errorCode);
					log.error("callProcedureForOrderChangeForDlvyType : " + errorCode);
				}

				if (!UtilsText.equals(errorCode, "0")) {
					throw new Exception("택배전환 처리에 실패하였습니다.(PR_ORDER_CHANGE_FOR_DLVY_TYPE)");
				} else {
					// 택배전환신청 처리 진행
					OcOrderProduct ocOrderProduct = new OcOrderProduct();
					ocOrderProduct.setLogisCnvrtRsnCode("10002"); // 사유는 기타로 우선등록
					ocOrderProduct.setOrderNo(param.getOrderNo());
					ocOrderProduct.setOrderPrdtSeq(param.getOrderPrdtSeq());
					ocOrderProduct.setModerNo(adminNo); // 수정자
					int orderPrdtUpdate = ocOrderProductDao.updatePrdtOrderDeliveryChange(ocOrderProduct);
				}

			} // end for

			// 메세지 발송
			try {
				OcOrder mesgOrder = new OcOrder();
				mesgOrder.setOrderNo(orderNo);

				orderMessageService.changePickupToDelivery(mesgOrder);

			} catch (Exception e) {
				log.debug("매장픽업 택배전환 메세지 발송 에러 : " + e.getMessage());
			}

			// throw new Exception("강제 익셉션 발생");

			result.put("result", Const.BOOLEAN_TRUE);
		} catch (Exception e) {
			result.put("result", Const.BOOLEAN_FALSE);
			log.debug(e.toString());
			e.printStackTrace(System.err);
		}

		return result;
	}

	/**
	 *
	 * @Desc : 분실 재배송 처리
	 * @Method Name : storePickDeliveryPopupUpdate
	 * @Date : 2019. 3. 25.
	 * @Author : NKB
	 * @param ocOrderDeliveryChangeHistory
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateMissDeliveryPopup(OcOrderDeliveryHistory[] ocOrderDeliveryHistory, OcOrder ocOrder)
			throws Exception {

		Map<String, Object> result = new HashMap<>();
		String adminNo = LoginManager.getUserDetails().getAdminNo();

		try {

			// 배송지 변경 시작
			for (int i = 0; i < ocOrderDeliveryHistory.length; i++) {
				OcOrderDeliveryHistory param = ocOrderDeliveryHistory[i];

				log.debug(" param.getOrderPrdtSeq()  ::::::" + param.getOrderPrdtSeq());
				log.debug(" param.getDlvyIdText()  ::::::" + param.getDlvyIdText());

				// 등록된 배송 정보를 가져와 처리가 가능한지 체크 한다.
				OcOrderDeliveryHistory ocOrderDeliveryHistoryParam = new OcOrderDeliveryHistory();
				ocOrderDeliveryHistoryParam.setOrderNo(param.getOrderNo());
				ocOrderDeliveryHistoryParam.setOrderPrdtSeq(param.getOrderPrdtSeq());
				OcOrderDeliveryHistory deliveryInfo = ocOrderDeliveryHistoryDao
						.getPrdtDeliveryInfo(ocOrderDeliveryHistoryParam);

				if (UtilsText.equals(deliveryInfo.getMissProcYn(), Const.BOOLEAN_TRUE)) {
					throw new Exception("이미 분실처리된 상태입니다.");
				}

				// 분실처리여부 진행 - 상태값은 취소로
				OcOrderDeliveryHistory updateStopDelivery = new OcOrderDeliveryHistory();
				updateStopDelivery.setMissProcYn(Const.BOOLEAN_TRUE); // 분실처리 여부
				updateStopDelivery.setMissProcTypeCode("10002"); // 분실처리유형코드
				updateStopDelivery.setOrderNo(ocOrder.getOrderNo());
				updateStopDelivery.setDlvyStatCode(CommonCode.DLVY_STAT_CODE_DELIVERY_CANCEL); // 배송 취소로 변경 처리
				updateStopDelivery.setDlvyIdText(param.getDlvyIdText());
				updateStopDelivery.setModerNo(adminNo); // 수정자
				int updateCnt = ocOrderDeliveryHistoryDao.UpdateDeliveryMiss(updateStopDelivery);

				if (updateCnt > 0) {
					// 신규 Insert 진행
					OcOrderDeliveryHistory ocOrderDeliveryNew = new OcOrderDeliveryHistory();
					ocOrderDeliveryNew.setOrderNo(param.getOrderNo());
					ocOrderDeliveryNew.setOrderPrdtSeq(param.getOrderPrdtSeq());
					ocOrderDeliveryNew.setStockGbnCode(CommonCode.STOCK_GBN_CODE_AI); // 재고구분코드
					ocOrderDeliveryNew.setStoreNo(null); // 매장번호
					ocOrderDeliveryNew.setStoreChngDgreCount((short) 0); // 매장변경차수
					ocOrderDeliveryNew.setDlvyProcDtm(null); // 배송처리일시
					ocOrderDeliveryNew.setLogisVndrCode(null); // 택배사코드
					ocOrderDeliveryNew.setWaybilNoText(null); // 운송장번호
					ocOrderDeliveryNew.setDlvyDscntcYn(Const.BOOLEAN_FALSE); // 배송중단여부
					ocOrderDeliveryNew.setDlvyDscntcRsnCode(null); // 배송중단사유코드
					ocOrderDeliveryNew.setDlvyDscntcOpetrNo(null); // 배송중단처리자번호
					ocOrderDeliveryNew.setDlvyDscntcAcceptDtm(null); // 배송중단접수일시
					ocOrderDeliveryNew.setDlvyDscntcProcDtm(null); // 배송중단처리일시
					ocOrderDeliveryNew.setPickupPrpareCmlptDtm(null); // 픽업준비완료일시
					ocOrderDeliveryNew.setPickupPsbltYmd(null); // 픽업가능일
					ocOrderDeliveryNew.setMissProcYn(Const.BOOLEAN_FALSE); // 분실처리여부
					ocOrderDeliveryNew.setMissProcTypeCode(null); // 분실처리유형코드
					ocOrderDeliveryNew.setInsdMgmtInfoText(null); // 내부관리정보
					ocOrderDeliveryNew.setWmsSendYn(Const.BOOLEAN_FALSE); // WMS전송여부
					ocOrderDeliveryNew.setDlvyTodoYmd(null); // 배송예정일
					ocOrderDeliveryNew.setDlvyCmlptDtm(null); // 배송완료일시
					ocOrderDeliveryNew.setRcvrName(ocOrder.getRcvrName()); // 수취인명
					ocOrderDeliveryNew.setRcvrTelNoText(ocOrder.getRcvrTelNoText()); // 수취인전화번호
					ocOrderDeliveryNew.setRcvrHdphnNoText(ocOrder.getRcvrHdphnNoText()); // 수취인핸드폰번호
					ocOrderDeliveryNew.setRcvrPostCodeText(ocOrder.getRcvrPostCodeText()); // 수취인우편번호
					ocOrderDeliveryNew.setRcvrPostAddrText(ocOrder.getRcvrPostAddrText()); // 수취인우편주소
					ocOrderDeliveryNew.setRcvrDtlAddrText(ocOrder.getRcvrDtlAddrText()); // 수취인상세주소
					ocOrderDeliveryNew.setClmNo(null); // 클레임번호
					ocOrderDeliveryNew.setClmPrdtSeq(null); // 클레임상품순번(재배송상품기준)
					ocOrderDeliveryNew.setDlvyMemoText(null); // 배송메모
					ocOrderDeliveryNew.setDlvyStatCode(CommonCode.DLVY_STAT_CODE_PAYMENT_FINISH); // 배송상태코드
					ocOrderDeliveryNew.setLogisCnvrtSendDtm(null); // 택배전환발송일시
					ocOrderDeliveryNew.setRsvDlvyDtm(null); // 예약출고일시
					// 배송ID는 쿼리에서 생성
					ocOrderDeliveryNew.setLogisPymntPrdtAmt(0); // 택배사결제상품금액
					ocOrderDeliveryNew.setLogisPymntDlvyAmt(0); // 택배사결제배송비
					ocOrderDeliveryNew.setImpsbltProcYn(Const.BOOLEAN_FALSE); // 불가처리여부
					ocOrderDeliveryNew.setImpsbltProcAcceptDtm(null); // 불가처리접수일시
					ocOrderDeliveryNew.setImpsbltProcCmlptDtm(null); // 불가처리완료일시
					ocOrderDeliveryNew.setRgsterNo(LoginManager.getUserDetails().getAdminNo()); // 등록자
					ocOrderDeliveryNew.setModerNo(LoginManager.getUserDetails().getAdminNo()); // 수정자
					ocOrderDeliveryHistoryDao.insertDelivery(ocOrderDeliveryNew);

				}

			} // end for
			result.put("result", Const.BOOLEAN_TRUE);
		} catch (Exception e) {
			result.put("result", Const.BOOLEAN_FALSE);
			log.debug(e.toString());
			e.printStackTrace(System.err);
		}

		return result;
	}

	/**
	 * 상품대기조회
	 *
	 * @Desc : 상품대기조회
	 * @Method Name : deliveryProdStandbyReadList
	 * @Date : 2019. 2. 12.
	 * @Author : NKB
	 * @param ocDelivery
	 * @return
	 * @throws Exception
	 */
	public Page<OcDelivery> deliveryOrderStandbyList(Pageable<OcDelivery, OcDelivery> pageable) throws Exception {

		// 상품대기 목록 - 배송중단여부가 Y 인 데이타를 기준으로 조회
		List<OcDelivery> deliveryOrderStandbyList = null;
		pageable.getBean().setDlvyDscntcYn(Const.BOOLEAN_TRUE); // 배송중단 여부
		pageable.getBean().setMissProcYn(Const.BOOLEAN_FALSE); // 분실처리 여부
		pageable.getBean().setMmnyPrdtYn(Const.BOOLEAN_TRUE); // 자사 여부 -
		pageable.getBean().setImpsbltProcYn(Const.BOOLEAN_FALSE); // 불가 처리 여부 IMPSBLTPROCYN

		// 배송유형
		String[] dlvyTypeCodeArr = pageable.getBean().getDlvyTypeCodeArr();
		if (dlvyTypeCodeArr != null && !Arrays.asList(dlvyTypeCodeArr).contains("")) {
			pageable.getBean().setDlvyTypeCodeList(Arrays.asList(dlvyTypeCodeArr));
		}

		// Site 구분
		if (pageable.getBean().getSiteNo() != null) {
			pageable.getBean().setSiteNoList(Arrays.asList(pageable.getBean().getSiteNo()));
		}

		// 미출처
		if (pageable.getBean().getStockGbnCode() != null) {
			pageable.getBean().setStockGbnCodeList(Arrays.asList(pageable.getBean().getStockGbnCode()));
		}

		// Total Count
		int totalCount = ocDeliveryDao.selectDeliveryProdStandbyCount(pageable);

		log.info("::::::::::::::::::::::::::::::::" + totalCount);
		if (totalCount > 0) {
			deliveryOrderStandbyList = ocDeliveryDao.selectDeliveryProdStandbyList(pageable);

			for (OcDelivery ocDelivery : deliveryOrderStandbyList) {
				// 목록이기에 권한과 상관없이 마스킹 처리 Y
				ocDelivery.setIsListYn(Const.BOOLEAN_TRUE);
			}
			pageable.setContent(deliveryOrderStandbyList);
		}

		pageable.setTotalCount(totalCount);

		return pageable.getPage();

	}

	/**
	 *
	 * @Desc : 상품대기조회 - 일괄 재배송 처리
	 * @Method Name : storePickDeliveryPopupUpdate
	 * @Date : 2019. 3. 25.
	 * @Author : NKB
	 * @param ocOrderDeliveryChangeHistory
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> insertDeliveryOrderStandbyOld(OcDelivery ocDelivery,
			OcOrderDeliveryHistory ocOrderDeliveryHistory) throws Exception {

		Map<String, Object> result = new HashMap<>();
		String adminNo = LoginManager.getUserDetails().getAdminNo();
		String targetStockGbnCode = ocDelivery.getNewStockGbnCode(); // 변경 대상 구분코드

		try {

			// System.out.println("ocOrderDeliveryHistory >> " + ocOrderDeliveryHistory);
			// System.out.println("ocDelivery >> " + ocDelivery);
			log.error("ocDelivery  ]]]] " + ocDelivery);
			log.error("ocDelivery.getOrderNo()    ]]]] " + ocDelivery.getOrderNo());
			log.error("신규 출고지 지정값    ]]]] " + ocDelivery.getNewStockGbnCode());

			// 배송아이디를 기준으로 이전 배송정보를 조회한다.
			OcOrderDeliveryHistory oldOrderDeliveryHistory = ocOrderDeliveryHistoryDao
					.getOrderDeliveryHistoryDlvyId(ocOrderDeliveryHistory);

			// 주문번호를 기준을 주문정보 추출
			OcOrder ocOrder = new OcOrder();
			ocOrder.setOrderNo(ocDelivery.getOrderNo());
			OcOrder orderDtail = ocOrderDao.selectOrderDetail(ocOrder);

			// 1. 이전 배송지는 취소로 변경 처리 진행
			oldOrderDeliveryHistory.setDlvyStatCode("10006"); // 배송상태코드 배송취소
			oldOrderDeliveryHistory.setDlvyDscntcOpetrNo(adminNo); // 배송중단처리자번호
			int updateCnt = ocOrderDeliveryHistoryDao.updateDeliveryHistoryCancel(oldOrderDeliveryHistory);
			log.error("이전 배송지는 취소로 변경 처리 진행 :::::::::::::::::::::::::::::" + updateCnt);
			log.error("변경대상 출고지 정보 :::::::::::::" + ocOrderDeliveryHistory.getStockGbnCode());

			/*
			 * 10000 : 온라인 물류 10001 : 스마트 물류 10002
			 */

			log.error("targetStockGbnCode :::::" + targetStockGbnCode + "::::::BaseCommonCode.STOCK_GBN_CODE_AI ::::"
					+ BaseCommonCode.STOCK_GBN_CODE_AI + "....");

			if (UtilsText.equals(targetStockGbnCode, BaseCommonCode.STOCK_GBN_CODE_AI)
					|| UtilsText.equals(targetStockGbnCode, BaseCommonCode.STOCK_GBN_CODE_AW)) {

				OcOrderDeliveryHistory setOdhVo = new OcOrderDeliveryHistory();

				// 주문 상품번호를 기준으로 신규 이력순번을 추출
				int orderDlvyHistSeq = ocOrderDeliveryHistoryDao.selectOrderDlvyHistSeq(
						ocOrderDeliveryHistory.getOrderNo(), ocOrderDeliveryHistory.getOrderPrdtSeq(),
						ocOrderDeliveryHistory.getOrderDlvyHistSeq());

				String dlvyIdText = ocDelivery.getOrderNo()
						+ String.format("%03d", oldOrderDeliveryHistory.getOrderPrdtSeq())
						+ String.format("%03d", orderDlvyHistSeq);

				setOdhVo.setOrderNo(ocDelivery.getOrderNo());
				setOdhVo.setOrderPrdtSeq(oldOrderDeliveryHistory.getOrderPrdtSeq());
				setOdhVo.setOrderDlvyHistSeq((short) (orderDlvyHistSeq));
				setOdhVo.setDlvyDgreCount((short) (orderDlvyHistSeq));
				setOdhVo.setStockGbnCode(targetStockGbnCode); // 온라인 AS
				setOdhVo.setDlvyDscntcYn(Const.BOOLEAN_FALSE);
				setOdhVo.setMissProcYn(Const.BOOLEAN_FALSE);
				setOdhVo.setRcvrName(orderDtail.getRcvrName());
				setOdhVo.setRcvrTelNoText(orderDtail.getRcvrTelNoText());
				setOdhVo.setRcvrHdphnNoText(orderDtail.getRcvrHdphnNoText());
				setOdhVo.setRcvrPostCodeText(orderDtail.getRcvrPostCodeText());
				setOdhVo.setRcvrPostAddrText(orderDtail.getRcvrPostAddrText());
				setOdhVo.setRcvrDtlAddrText(orderDtail.getRcvrDtlAddrText());
				setOdhVo.setWmsSendYn(Const.BOOLEAN_FALSE);
				setOdhVo.setDlvyIdText(dlvyIdText);
				setOdhVo.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
				setOdhVo.setModerNo(adminNo);
				setOdhVo.setDlvyStatCode(BaseCommonCode.DLVY_STAT_CODE_PAYMENT_FINISH); // 결제완료 상태

				setOdhVo.setStoreChngDgreCount((short) 0); // 매장변경차수
				setOdhVo.setLogisPymntPrdtAmt(0); // 택배사결제상품금액
				setOdhVo.setLogisPymntDlvyAmt(0); // 택배사결제배송비
				setOdhVo.setImpsbltProcYn(Const.BOOLEAN_FALSE); // 불가처리여부 추가 20191002

				// 배송대상 저장
				ocOrderDeliveryHistoryDao.insertDelivery(setOdhVo);

				log.error(":::::::::::::::::::::::::::::신규 온라인 물류 / 스마트 물류 로 변경");

			} // end if 온라인 물류 / 스마트 물류 로 변경

			/*
			 * 10002 : 오프라인 매장
			 */
			if (UtilsText.equals(targetStockGbnCode, BaseCommonCode.STOCK_GBN_CODE_AS)) {

				OcOrderDeliveryHistory setOdhVo = new OcOrderDeliveryHistory();

				// 주문 상품번호를 기준으로 신규 이력순번을 추출
				int orderDlvyHistSeq = ocOrderDeliveryHistoryDao.selectOrderDlvyHistSeq(
						ocOrderDeliveryHistory.getOrderNo(), ocOrderDeliveryHistory.getOrderPrdtSeq(),
						ocOrderDeliveryHistory.getOrderDlvyHistSeq());

				String dlvyIdText = ocDelivery.getOrderNo()
						+ String.format("%03d", oldOrderDeliveryHistory.getOrderPrdtSeq())
						+ String.format("%03d", orderDlvyHistSeq);

				log.error("orderDlvyHistSeq:::::::::::::" + orderDlvyHistSeq);
				log.error("dlvyIdText:::::::::::::" + dlvyIdText);

				setOdhVo.setOrderNo(ocDelivery.getOrderNo());
				setOdhVo.setOrderPrdtSeq(oldOrderDeliveryHistory.getOrderPrdtSeq());
				setOdhVo.setOrderDlvyHistSeq((short) (orderDlvyHistSeq));
				setOdhVo.setDlvyDgreCount((short) (orderDlvyHistSeq));
				setOdhVo.setStockGbnCode(targetStockGbnCode); // 온라인 AI
				setOdhVo.setDlvyDscntcYn(Const.BOOLEAN_FALSE);
				setOdhVo.setMissProcYn(Const.BOOLEAN_FALSE);
				setOdhVo.setRcvrName(orderDtail.getRcvrName());
				setOdhVo.setRcvrTelNoText(orderDtail.getRcvrTelNoText());
				setOdhVo.setRcvrHdphnNoText(orderDtail.getRcvrHdphnNoText());
				setOdhVo.setRcvrPostCodeText(orderDtail.getRcvrPostCodeText());
				setOdhVo.setRcvrPostAddrText(orderDtail.getRcvrPostAddrText());
				setOdhVo.setRcvrDtlAddrText(orderDtail.getRcvrDtlAddrText());
				setOdhVo.setWmsSendYn(Const.BOOLEAN_FALSE);
				setOdhVo.setDlvyIdText(dlvyIdText);
				setOdhVo.setRgsterNo(adminNo);
				setOdhVo.setModerNo(adminNo);
				setOdhVo.setDlvyStatCode(BaseCommonCode.DLVY_STAT_CODE_PAYMENT_FINISH); // 결제완료 상태

				setOdhVo.setStoreChngDgreCount((short) 0); // 매장변경차수
				setOdhVo.setLogisPymntPrdtAmt(0); // 택배사결제상품금액
				setOdhVo.setLogisPymntDlvyAmt(0); // 택배사결제배송비
				setOdhVo.setImpsbltProcYn(Const.BOOLEAN_FALSE); // 불가처리여부 추가 20191002

				// 배송대상 저장
				ocOrderDeliveryHistoryDao.insertDelivery(setOdhVo);

				/*
				 * // 변경 대상이 온라인물류 if
				 * (UtilsText.equals(oldOrderDeliveryHistory.getStockGbnCode(),
				 * BaseCommonCode.STOCK_GBN_CODE_AI)) { } // end if 변경 대상이 온라인물류 // 변경 대상이 매장인경우
				 * - 저장 처리 - 온라인물류, 스마트 물류 if
				 * (UtilsText.equals(oldOrderDeliveryHistory.getStockGbnCode(),
				 * BaseCommonCode.STOCK_GBN_CODE_AW)) { } // end if 변경 대상이 매장인경우 // 대상이 스마트 인경우
				 * - 저장 처리 if (UtilsText.equals(ocOrderDeliveryHistory.getStockGbnCode(),
				 * BaseCommonCode.STOCK_GBN_CODE_AS)) { } //end if 대상이 스마트 인경우
				 */
			} // end if

			/**********************************************************************************
			 * *NBK* 프로시져 부분 취소 처리
			 **********************************************************************************/

			/**********************************************************************************
			 * *NBK* SMS 전송 필요
			 **********************************************************************************/

			result.put("result", Const.BOOLEAN_TRUE);

		} catch (Exception e) {
			result.put("result", Const.BOOLEAN_FALSE);
			log.debug(e.toString());
			e.printStackTrace(System.err);
			throw new Exception(e.toString());
		}

		return result;
	}

	/**
	 *
	 * @Desc : 상품대기조회 - 일괄 재배송 처리 , 주문상세 재배송 처리
	 * @Method Name : storePickDeliveryPopupUpdate
	 * @Date : 2019. 3. 25.
	 * @Author : NKB
	 * @param ocOrderDeliveryChangeHistory
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> insertDeliveryOrderStandby(OcDelivery ocDelivery,
			OcOrderDeliveryHistory ocOrderDeliveryHistory) throws Exception {

		Map<String, Object> result = new HashMap<>();
		String adminNo = LoginManager.getUserDetails().getAdminNo();
		String targetStockGbnCode = ocDelivery.getNewStockGbnCode(); // 변경할 재배송 재고구분코드
		String orgStockGbnCode = ocOrderDeliveryHistory.getStockGbnCode(); // 기존 배송 재고구분코드
		String orderNo = ocOrderDeliveryHistory.getOrderNo(); // 주문번호
		int orderPrdtSeq = ocOrderDeliveryHistory.getOrderPrdtSeq(); // 주문 상품 시퀀스
		String dlvyIdText = ocOrderDeliveryHistory.getDlvyIdText(); // 기존 배송 배송idtxt

		try {

			log.error("error - dlvyIdText ::" + dlvyIdText);
			log.info("info - dlvyIdText :: " + dlvyIdText);

			log.error("error - targetStockGbnCode :: " + targetStockGbnCode);
			log.info("info - targetStockGbnCode :: " + targetStockGbnCode);
			log.error("error - orgStockGbnCode :: " + orgStockGbnCode);
			log.info("info - orgStockGbnCode :: " + orgStockGbnCode);

			log.error("ocDelivery  ]]]] " + ocDelivery);
			log.error("ocDelivery.getOrderNo()    ]]]] " + ocDelivery.getOrderNo());
			log.error("신규 출고지 지정값    ]]]] " + ocDelivery.getNewStockGbnCode());

			/**
			 * 2020.04.28 : 교환재배송 상품의 재배송 처리시 로직
			 */
			String setClmNo = null;
			int setClmPrdtSeq = 0;
			
			// 주문조회
			OcOrder paramOrder = new OcOrder();
			paramOrder.setOrderNo(orderNo);
			paramOrder = ocOrderDao.selectByPrimaryKey(paramOrder);
			String orgOrderNo = paramOrder.getOrgOrderNo();
			
			// 1.원주문번호와 다른 리오더 
			// 2.매출구분타입 D(교환재배송)인 경우
			// 3.clmNo가 null이 아닌경우
			if(!UtilsText.equals(paramOrder.getOrgOrderNo(), paramOrder.getOrderNo())
					&& UtilsText.equals(paramOrder.getSalesCnclGbnType(), CommonCode.SALES_CNCL_GBN_TYPE_EXCHANGE_SALE)
					&& UtilsObject.isNotEmpty(paramOrder.getClmNo())) {
				
				OcClaimProduct paramClmPrdt = new OcClaimProduct();
				paramClmPrdt.setOrderNo(orgOrderNo);
				paramClmPrdt.setOrderPrdtSeq(orderPrdtSeq);
				paramClmPrdt = ocClaimProductDao.selectClmExchangeProduct(paramClmPrdt);
				
				setClmNo = paramClmPrdt.getClmNo();
				setClmPrdtSeq = paramClmPrdt.getClmPrdtSeq();
			}
			
			// 배송기준으로 상품 정보 조회 (상품에 포함된 사은품 정보 조회)
			OcOrderDeliveryHistory dlvyParams = new OcOrderDeliveryHistory();
			dlvyParams.setOrderNo(orderNo);
			dlvyParams.setOrderPrdtSeq(orderPrdtSeq);
			dlvyParams.setDlvyIdText(dlvyIdText);

			List<OcOrderDeliveryHistory> orgDlvyPrdtInfo = ocOrderDeliveryHistoryDao.getDeliveryForPrdtInfo(dlvyParams);

			for (OcOrderDeliveryHistory dlvy : orgDlvyPrdtInfo) {

				Map<String, String> paramMap = new HashMap<>();
				paramMap.put("dlvyIdText", dlvyIdText);

				log.debug("for in :::::"+dlvyIdText);
				
				String wmsSendYn = dlvy.getWmsSendYn(); // wms 전송여부값
				String errorCode = "9"; // 프로시져 return 값 실패로 선언

				ocClaimProductDao.callProcedureForOrderCancel(paramMap);
				errorCode = String.valueOf(paramMap.get("errorCode"));
				log.error("error - callProcedureForOrderCancel return >>> " + errorCode);
				log.info("info - callProcedureForOrderCancel return >>> " + errorCode);

				// 배송취소 처리 실패
				if (!UtilsText.equals(errorCode, "0")) {
					result.put("result", Const.BOOLEAN_FALSE);
					throw new Exception("배송중단처리(PR_ORDER_CANCEL)에 실패하였습니다.");
				} else {

					OcOrderDeliveryHistory oldOrderDeliveryHistory = new OcOrderDeliveryHistory();
					log.debug("배송 취소 처리 ");
					oldOrderDeliveryHistory.setDlvyIdText(dlvy.getDlvyIdText());
					oldOrderDeliveryHistory.setOrderNo(dlvy.getOrderNo());
					oldOrderDeliveryHistory.setOrderPrdtSeq(dlvy.getOrderPrdtSeq());
					oldOrderDeliveryHistory.setDlvyStatCode(CommonCode.DLVY_STAT_CODE_DELIVERY_CANCEL); // 배송상태코드 배송취소
					oldOrderDeliveryHistory.setDlvyDscntcOpetrNo(adminNo); // 배송중단처리자번호
					oldOrderDeliveryHistory.setModerNo(adminNo);
					int updateCnt = ocOrderDeliveryHistoryDao.updateDeliveryHistoryCancel(oldOrderDeliveryHistory);

					if (UtilsText.equals(targetStockGbnCode, BaseCommonCode.STOCK_GBN_CODE_AI)
							|| UtilsText.equals(targetStockGbnCode, BaseCommonCode.STOCK_GBN_CODE_AW)) {
						OcOrderDeliveryHistory setOdhVo = new OcOrderDeliveryHistory();

						// 주문 상품번호를 기준으로 신규 이력순번을 추출
						int orderDlvyHistSeq = ocOrderDeliveryHistoryDao.selectOrderDlvyHistSeq(dlvy.getOrderNo(),
								dlvy.getOrderPrdtSeq(), dlvy.getOrderDlvyHistSeq());

						String newDlvyIdText = dlvy.getOrderNo() + String.format("%03d", dlvy.getOrderPrdtSeq())
								+ String.format("%03d", orderDlvyHistSeq);

						setOdhVo.setOrderNo(dlvy.getOrderNo());
						setOdhVo.setOrderPrdtSeq(dlvy.getOrderPrdtSeq());
						setOdhVo.setOrderDlvyHistSeq((short) (orderDlvyHistSeq));
						setOdhVo.setDlvyDgreCount((short) (orderDlvyHistSeq));
						setOdhVo.setStockGbnCode(targetStockGbnCode); // 변경할 재배송 재고구분코드
						setOdhVo.setDlvyDscntcYn(Const.BOOLEAN_FALSE);
						setOdhVo.setMissProcYn(Const.BOOLEAN_FALSE);
						// 배송정보에 등록된 수취인 정보 등록
						setOdhVo.setRcvrName(dlvy.getRcvrName());
						setOdhVo.setRcvrTelNoText(dlvy.getRcvrTelNoText());
						setOdhVo.setRcvrHdphnNoText(dlvy.getRcvrHdphnNoText());
						setOdhVo.setRcvrPostCodeText(dlvy.getRcvrPostCodeText());
						setOdhVo.setRcvrPostAddrText(dlvy.getRcvrPostAddrText());
						setOdhVo.setRcvrDtlAddrText(dlvy.getRcvrDtlAddrText());
						setOdhVo.setWmsSendYn(Const.BOOLEAN_FALSE);
						setOdhVo.setDlvyIdText(newDlvyIdText);
						setOdhVo.setRgsterNo(adminNo);
						setOdhVo.setModerNo(adminNo);
						setOdhVo.setDlvyStatCode(BaseCommonCode.DLVY_STAT_CODE_PAYMENT_FINISH); // 결제완료 상태

						setOdhVo.setStoreChngDgreCount((short) 0); // 매장변경차수
						setOdhVo.setLogisPymntPrdtAmt(0); // 택배사결제상품금액
						setOdhVo.setLogisPymntDlvyAmt(0); // 택배사결제배송비
						setOdhVo.setImpsbltProcYn(Const.BOOLEAN_FALSE); // 불가처리여부 추가 20191002

						log.debug(":::::"+setClmNo);
						log.debug(":::::"+setClmPrdtSeq);
						// 2020.04.28 : 교환재배송 상품 재배송처리일 때
						if(setClmNo != null && setClmPrdtSeq != 0) {
							setOdhVo.setClmNo(setClmNo);
							setOdhVo.setClmPrdtSeq(setClmPrdtSeq);
						}
						
						// 배송대상 저장
						ocOrderDeliveryHistoryDao.insertDelivery(setOdhVo);

					}

					if (UtilsText.equals(targetStockGbnCode, BaseCommonCode.STOCK_GBN_CODE_AS)) {
						OcOrderDeliveryHistory setOdhVo = new OcOrderDeliveryHistory();

						// 주문 상품번호를 기준으로 신규 이력순번을 추출
						int orderDlvyHistSeq = ocOrderDeliveryHistoryDao.selectOrderDlvyHistSeq(dlvy.getOrderNo(),
								dlvy.getOrderPrdtSeq(), dlvy.getOrderDlvyHistSeq());

						String newDlvyIdText = dlvy.getOrderNo() + String.format("%03d", dlvy.getOrderPrdtSeq())
								+ String.format("%03d", orderDlvyHistSeq);

						log.error("orderDlvyHistSeq:::::::::::::" + orderDlvyHistSeq);
						log.error("dlvyIdText:::::::::::::" + dlvyIdText);

						setOdhVo.setOrderNo(dlvy.getOrderNo());
						setOdhVo.setOrderPrdtSeq(dlvy.getOrderPrdtSeq());
						setOdhVo.setOrderDlvyHistSeq((short) (orderDlvyHistSeq));
						setOdhVo.setDlvyDgreCount((short) (orderDlvyHistSeq));
						setOdhVo.setStockGbnCode(targetStockGbnCode); // 변경할 재배송 재고구분코드
						setOdhVo.setDlvyDscntcYn(Const.BOOLEAN_FALSE);
						setOdhVo.setMissProcYn(Const.BOOLEAN_FALSE);
						// 배송정보에 등록된 수취인 정보 등록
						setOdhVo.setRcvrName(dlvy.getRcvrName());
						setOdhVo.setRcvrTelNoText(dlvy.getRcvrTelNoText());
						setOdhVo.setRcvrHdphnNoText(dlvy.getRcvrHdphnNoText());
						setOdhVo.setRcvrPostCodeText(dlvy.getRcvrPostCodeText());
						setOdhVo.setRcvrPostAddrText(dlvy.getRcvrPostAddrText());
						setOdhVo.setRcvrDtlAddrText(dlvy.getRcvrDtlAddrText());
						setOdhVo.setWmsSendYn(Const.BOOLEAN_FALSE);
						setOdhVo.setDlvyIdText(newDlvyIdText);
						setOdhVo.setRgsterNo(adminNo);
						setOdhVo.setModerNo(adminNo);
						setOdhVo.setDlvyStatCode(BaseCommonCode.DLVY_STAT_CODE_PAYMENT_FINISH); // 결제완료 상태

						setOdhVo.setStoreChngDgreCount((short) 0); // 매장변경차수
						setOdhVo.setLogisPymntPrdtAmt(0); // 택배사결제상품금액
						setOdhVo.setLogisPymntDlvyAmt(0); // 택배사결제배송비
						setOdhVo.setImpsbltProcYn(Const.BOOLEAN_FALSE); // 불가처리여부 추가 20191002

						// 2020.04.28 : 교환재배송 상품 재배송처리일 때
						if(setClmNo != null && setClmPrdtSeq != 0) {
							setOdhVo.setClmNo(setClmNo);
							setOdhVo.setClmPrdtSeq(setClmPrdtSeq);
						}
						
						// 배송대상 저장
						ocOrderDeliveryHistoryDao.insertDelivery(setOdhVo);
					}

					// 재고 관리 - // 다를경우
					if (!UtilsText.equals(targetStockGbnCode, dlvy.getStockGbnCode())) {

						if (!UtilsText.equals(dlvy.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)) { // 사은품이 아닐경우

							// 이전 재고구분코드 증가
							orderService.updateProductStockAdjust(dlvy.getPrdtNo(), dlvy.getPrdtOptnNo(), 1,
									dlvy.getStockGbnCode(), false);

							// 변경 재고구분코드 차감
							orderService.updateProductStockAdjust(dlvy.getPrdtNo(), dlvy.getPrdtOptnNo(), 1,
									targetStockGbnCode, true);
						} else { // 사은품일경우 온라인 재고 차감 증가 처리 한다
							// 이전 재고구분코드 증가
							orderService.updateProductStockAdjust(dlvy.getPrdtNo(), dlvy.getPrdtOptnNo(), 1,
									CommonCode.STOCK_GBN_CODE_AI, false);

							// 변경 재고구분코드 차감
							orderService.updateProductStockAdjust(dlvy.getPrdtNo(), dlvy.getPrdtOptnNo(), 1,
									CommonCode.STOCK_GBN_CODE_AI, true);

						}

					} // 재고 관리 end

					/**
					 * as-is PR_ORDER_CANCEL_FOR_PART 프로시져 내에서 wms_yn null 일때 실행 to-be wmsSendYn = Y
					 * 일경우 실행 처리
					 */
					if (UtilsText.equals(dlvy.getWmsSendYn(), Const.BOOLEAN_TRUE)) {
						// 프로시져 부분 취소 처리
						String dlvyId = dlvy.getDlvyIdText(); // 기존 배송 id
						String cbcd = dlvy.getCbcd();

						String maejangCd = dlvy.getInsdMgmtInfoText(); // 매장코드
						String freeGiftItemSno = String.valueOf(dlvy.getOrderPrdtSeq()).concat("F")
								.concat(String.valueOf(dlvy.getUpOrderPrdtSeq())); // 서브키
						String sangpumFullCd = dlvy.getVndrPrdtNoText().concat("001").concat(dlvy.getPrdtOptnNo()); // 상품풀코드
						String itemSno = String.valueOf(dlvy.getOrderPrdtSeq()); // 서브키
						String count = "1"; // 수량

						if (UtilsText.equals(dlvy.getPrdtTypeCode(), CommonCode.PRDT_TYPE_CODE_GIFT)) { // 사은품 상품 일경우

							log.debug(" 사은품 상품 부분 취소 interface  cbcd :" + cbcd + " maejangCd:" + maejangCd + " dlvyId:"
									+ dlvyId + " freeGiftItemSno :" + freeGiftItemSno + " sangpumFullCd:"
									+ sangpumFullCd + " count:" + count);
							interfacesClaimService.updateOrderPrdtGiftCancelNoTrx(cbcd, maejangCd, dlvyId,
									freeGiftItemSno, sangpumFullCd, count);

						} else { // 일반 상품 일경우

							log.debug(" 일반 상품 부분 취소 interface  cbcd :" + cbcd + " maejangCd:" + maejangCd + " dlvyId:"
									+ dlvyId + " itemSno :" + itemSno + " sangpumFullCd:" + sangpumFullCd + " count:"
									+ count);
							interfacesClaimService.updateOrderPrdtNoGiftCancelNoTrx(cbcd, maejangCd, dlvyId, itemSno,
									sangpumFullCd, count);

						}
					} // end if if (UtilsText.eq

				} // end if
			} // for end

			result.put("result", Const.BOOLEAN_TRUE);
		} catch (Exception e) {
			result.put("result", Const.BOOLEAN_FALSE);
			log.debug(e.toString());
			e.printStackTrace(System.err);
			throw new Exception(e.toString());
		}

		return result;
	}

	/**
	 *
	 * @Desc : 분실배송조회
	 * @Method Name : deliveryOrderLossList
	 * @Date : 2019. 4. 7.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<OcDelivery> deliveryOrderLossList(Pageable<OcDelivery, OcDelivery> pageable) throws Exception {

		// 상품대기 목록 - 배송중단여부가 Y 인 데이타를 기준으로 조회
		List<OcDelivery> deliveryOrderLossList = null;
		pageable.getBean().setDlvyDscntcYn(Const.BOOLEAN_FALSE); // 배송중단 여부
		pageable.getBean().setMissProcYn(Const.BOOLEAN_TRUE); // 분실처리 여부
		pageable.getBean().setMmnyPrdtYn(Const.BOOLEAN_FALSE); // 자사 여부 - 자사만
		pageable.getBean().setImpsbltProcYn(Const.BOOLEAN_FALSE); // 불가 처리 여부 IMPSBLTPROCYN

		// 사이트
		String[] siteArr = pageable.getBean().getSiteNoArr();
		if (siteArr != null && !Arrays.asList(siteArr).contains("")) {
			pageable.getBean().setSiteNoList(Arrays.asList(siteArr));
		}

		// Total Count
		int totalCount = ocDeliveryDao.selectDeliveryOrderLossCount(pageable);

		log.info("::::::::::::::::::::::::::::::::" + totalCount);
		if (totalCount > 0) {
			deliveryOrderLossList = ocDeliveryDao.selectDeliveryOrderLossList(pageable);

			if (UtilsObject.isEmpty(pageable.getBean().getIsExcel())) {
				for (OcDelivery ocDelivery : deliveryOrderLossList) {
					// 목록이기에 권한과 상관없이 마스킹 처리 Y
					ocDelivery.setIsListYn(Const.BOOLEAN_TRUE);
				}
			} else {
				for (OcDelivery ocDelivery : deliveryOrderLossList) {
					// 분실 취소라면 재배송일 null
					if (UtilsText.equals(ocDelivery.getMissProcTypeCode(),
							CommonCode.MISS_PROC_TYPE_CODE_MISS_CANCEL)) {
						ocDelivery.setDlvyProcDtm(null);
					}
				}
			}
			pageable.setContent(deliveryOrderLossList);
		}

		pageable.setTotalCount(totalCount);

		return pageable.getPage();

	}

	/**
	 *
	 * @Desc : 분실배송조회 금액 관련 Update
	 * @Method Name : UpdateDeliveryOrderLoss
	 * @Date : 2019. 4. 8.
	 * @Author : NKB
	 * @param ocOrderDeliveryHistory
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> UpdateDeliveryOrderLoss(OcDelivery ocDelivery, OcOrderDeliveryHistory[] entityGrid)
			throws Exception {

		// return Value
		Map<String, Object> result = new HashMap<>();
		// 관리자 번호
		String adminNo = LoginManager.getUserDetails().getAdminNo();
		// Update Count
		int updateCnt = 0;

		try {
			// 배송지 변경 시작
			for (int i = 0; i < entityGrid.length; i++) {
				OcOrderDeliveryHistory gridOcOrderDeliveryHistory = entityGrid[i];

				/*
				 * ********************* 필요 없을 수도 있을 듯 하다 추후 판단 후 재정의 Grid에서 정리가
				 * 된다************** // 택배사 상품금액 Null체크 int logisPymntDlvyAmt =
				 * (gridOcOrderDeliveryHistory.getLogisPymntDlvyAmt() == null ? 0 :
				 * gridOcOrderDeliveryHistory.getLogisPymntDlvyAmt()); // 택배사 배송비금액 Null체크 int
				 * logisPymntPrdtAmt = (gridOcOrderDeliveryHistory.getLogisPymntPrdtAmt() ==
				 * null ? 0 : gridOcOrderDeliveryHistory.getLogisPymntPrdtAmt());
				 *
				 * gridOcOrderDeliveryHistory.setLogisPymntDlvyAmt(logisPymntDlvyAmt); // 배송비
				 * 재셋팅 gridOcOrderDeliveryHistory.setLogisPymntPrdtAmt(logisPymntPrdtAmt); //
				 * 상품금액 재셋팅
				 *********************
				 * 필요 없을 수도 있을 듯 하다 추후 판단 후 재정의 Grid에서 정리가 된다
				 **************/
				// 수정자
				gridOcOrderDeliveryHistory.setModerNo(adminNo);

				updateCnt += ocOrderDeliveryHistoryDao.updateDeliveryHistoryLogis(gridOcOrderDeliveryHistory);
			} // end for

			result.put("result", Const.BOOLEAN_TRUE);

		} catch (Exception e) {
			result.put("result", Const.BOOLEAN_FALSE);
			log.error(e.toString());
			e.printStackTrace(System.err);
			throw new Exception(e.toString());
		} // end try

		return result;
	}

	/**
	 *
	 * @Desc : 업체별 상태 조회건수
	 * @Method Name : getDeliveryOrderVendorPrdtStat
	 * @Date : 2019. 4. 16.
	 * @Author : NKB
	 * @param ocDelivery
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDeliveryOrderVendorPrdtStat(OcDelivery ocDelivery) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();

		List<OcDelivery> list = ocDeliveryDao.selectDelveryOrderVendorPrdStatList(ocDelivery);

		// 결제완료 Count - 10002
		int orderPrdtStatCodeComplete = (int) list.stream()
				.filter(o -> o.getOrderPrdtStatCode().equals(CommonCode.ORDER_PRDT_STAT_CODE_COMPLETE)).count();
		// 상품 준비중
		int orderPrdtStatCodeProductPreparation = (int) list.stream()
				.filter(o -> o.getOrderPrdtStatCode().equals(CommonCode.ORDER_PRDT_STAT_CODE_PRODUCT_PREPARATION))
				.count();

		log.debug("CommonCode.ORDER_PRDT_STAT_CODE_PRODUCT_PREPARATION:"
				+ CommonCode.ORDER_PRDT_STAT_CODE_PRODUCT_PREPARATION + ":::orderPrdtStatCodeProductPreparation:::"
				+ orderPrdtStatCodeProductPreparation);
		// 상품출고
		int orderPrdtStatCodeProductDelivery = (int) list.stream()
				.filter(o -> o.getOrderPrdtStatCode().equals(CommonCode.ORDER_PRDT_STAT_CODE_PRODUCT_DELIVERY)).count();

		// 배송중
		int orderPrdtStatCodeDeliveryIng = (int) list.stream()
				.filter(o -> o.getOrderPrdtStatCode().equals(CommonCode.ORDER_PRDT_STAT_CODE_DELIVERY_ING)).count();

		// 배송완료
		int orderPrdtStatCodeDeliveryFinish = (int) list.stream()
				.filter(o -> o.getOrderPrdtStatCode().equals(CommonCode.ORDER_PRDT_STAT_CODE_DELIVERY_FINISH)).count();

		// 취소완료
		int orderPrdtStatCodeCancelComplete = (int) list.stream()
				.filter(o -> o.getOrderPrdtStatCode().equals(CommonCode.ORDER_PRDT_STAT_CODE_CANCEL_COMPLETE)).count();

		retMap.put("orderPrdtStatCodeComplete", orderPrdtStatCodeComplete); // 결제완료
		retMap.put("orderPrdtStatCodeProductPreparation", orderPrdtStatCodeProductPreparation); // 상품 준비중
		retMap.put("orderPrdtStatCodeProductDelivery", orderPrdtStatCodeProductDelivery); // 상품출고
		retMap.put("orderPrdtStatCodeDeliveryIng", orderPrdtStatCodeDeliveryIng); // 배송중
		retMap.put("orderPrdtStatCodeDeliveryFinish", orderPrdtStatCodeDeliveryFinish); // 배송완료
		retMap.put("orderPrdtStatCodeCancelComplete", orderPrdtStatCodeCancelComplete); // 취소완료

		return retMap;

	}

	/**
	 *
	 * @Desc : 업체 주문 배송관리 - List
	 * @Method Name : deliveryOrderVendorList
	 * @Date : 2019. 4. 21.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<OcDelivery> deliveryOrderVendorList(Pageable<OcDelivery, OcDelivery> pageable) throws Exception {

		// 상품대기 목록 - 배송중단여부가 Y 인 데이타를 기준으로 조회
		List<OcDelivery> deliveryOrderVendorList = null;
		pageable.getBean().setDlvyDscntcYn(Const.BOOLEAN_FALSE); // 배송중단 여부
		pageable.getBean().setMissProcYn(Const.BOOLEAN_FALSE); // 분실처리 여부
		pageable.getBean().setImpsbltProcYn(Const.BOOLEAN_FALSE); // 불가 처리 여부 IMPSBLTPROCYN

		// 주문번호 검색은 기존 조건을 사용하기위해서
		if (!StringUtil.isNullOrEmpty(pageable.getBean().getOrderNo())) {
			pageable.getBean().setSearchOrderKey("orderNo");
			pageable.getBean().setSearchOrderValue(pageable.getBean().getOrderNo());
		}

		// 사이트
		String[] siteArr = pageable.getBean().getSiteNoArr();
		if (siteArr != null && !Arrays.asList(siteArr).contains("")) {
			pageable.getBean().setSiteNoList(Arrays.asList(siteArr));
		}

		log.info("::[pageable.getBean().getDelaySendYn() ] ::::::::::::::::::::::::::::::"
				+ pageable.getBean().getDelaySendYn());
		log.info("::[pageable.getBean().getWaybilNoYn() ] ::::::::::::::::::::::::::::::"
				+ pageable.getBean().getWaybilNoYn());

		// Total Count
		int totalCount = ocDeliveryDao.selectDeliveryOrderVendorCount(pageable);

		log.info("::::::::::::::::::::::::::::::::" + totalCount);

		if (totalCount > 0) {
			deliveryOrderVendorList = ocDeliveryDao.selectDeliveryOrderVendorList(pageable);

			for (OcDelivery ocDelivery : deliveryOrderVendorList) {
				// 목록이기에 권한과 상관없이 마스킹 처리 Y
				ocDelivery.setIsListYn(Const.BOOLEAN_TRUE);
			}

			pageable.setContent(deliveryOrderVendorList);
		}

		pageable.setTotalCount(totalCount);

		return pageable.getPage();

	}

	/**
	 * @Desc : 업체 주문 배송관리 전체 엑셀 다운로드
	 * @Method Name : getDeliveryOrderVendorAllExcel
	 * @Date : 2019. 11. 19.
	 * @Author : 이강수
	 * @param Pageable<OcDelivery, OcDelivery>
	 * @return
	 * @throws Exception
	 */
	public Page<OcDelivery> getDeliveryOrderVendorAllExcel(Pageable<OcDelivery, OcDelivery> pageable) throws Exception {

		// 상품대기 목록 - 배송중단여부가 Y 인 데이타를 기준으로 조회
		List<OcDelivery> deliveryOrderVendorList = null;
		pageable.getBean().setDlvyDscntcYn(Const.BOOLEAN_FALSE); // 배송중단 여부
		pageable.getBean().setMissProcYn(Const.BOOLEAN_FALSE); // 분실처리 여부
		pageable.getBean().setImpsbltProcYn(Const.BOOLEAN_FALSE); // 불가 처리 여부 IMPSBLTPROCYN

		// 주문번호 검색은 기존 조건을 사용하기위해서
		if (!StringUtil.isNullOrEmpty(pageable.getBean().getOrderNo())) {
			pageable.getBean().setSearchOrderKey("orderNo");
			pageable.getBean().setSearchOrderValue(pageable.getBean().getOrderNo());
		}

		// 사이트
		String[] siteArr = pageable.getBean().getSiteNoArr();
		if (siteArr != null && !Arrays.asList(siteArr).contains("")) {
			pageable.getBean().setSiteNoList(Arrays.asList(siteArr));
		}

		log.info("::[pageable.getBean().getDelaySendYn() ] ::::::::::::::::::::::::::::::"
				+ pageable.getBean().getDelaySendYn());
		log.info("::[pageable.getBean().getWaybilNoYn() ] ::::::::::::::::::::::::::::::"
				+ pageable.getBean().getWaybilNoYn());

		// Total Count
		int totalCount = ocDeliveryDao.selectDeliveryOrderVendorCount(pageable);

		log.info("::::::::::::::::::::::::::::::::" + totalCount);

		if (totalCount > 0) {
			deliveryOrderVendorList = ocDeliveryDao.selectDeliveryOrderVendorAllExcelDownload(pageable);
			pageable.setContent(deliveryOrderVendorList);
		}

		pageable.setTotalCount(totalCount);

		return pageable.getPage();
	}

	/**
	 *
	 * @Desc : 배송상태 변경 처리
	 * @Method Name : updateDeliveryOrderVendorStatCode
	 * @Date : 2019. 4. 23.
	 * @Author : NKB
	 * @param ocDelivery
	 * @param ocOrderDeliveryHistory
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateDeliveryOrderVendorStatCode(OcDelivery ocDelivery,
			OcOrderDeliveryHistory ocOrderDeliveryHistory) throws Exception {

		Map<String, Object> result = new HashMap<>();
		String adminNo = LoginManager.getUserDetails().getAdminNo();
		Boolean updateBoolean = false; // 상품 상태 업데으트 가능 여부 값
		String noteText = ""; // 상품 이력 저장문구
		int updateCnt = 0; //배송상태 update건수
		
		try {

			ocOrderDeliveryHistory.setModerNo(adminNo);

			// 배송상태를 업데이트 진행
			updateCnt = ocOrderDeliveryHistoryDao.updateDeliveryVendorStatCode(ocOrderDeliveryHistory);
			//배송 상태 업데이트 안됨
			if( updateCnt < 1 ) {
				result.put("result", Const.BOOLEAN_FALSE);
				result.put("resultMsg", "배송ID/배송상태 확인필요");
				return result;
			}

			String PrdtStatCode = ocOrderDeliveryHistory.getOrderPrdtStatCode(); // 현재 상품 배송 상태
			String DlvyStatCode = ocOrderDeliveryHistory.getDlvyStatCode(); // 변경된 배송 배송 상태

			// 상품 배송상태가 없는 경우 Select한다.
			if (Util.isNullOrEmpty(PrdtStatCode)) {

				OcOrderProduct setOrderPorduct = new OcOrderProduct();
				setOrderPorduct.setOrderNo(ocOrderDeliveryHistory.getOrderNo());
				setOrderPorduct.setOrderPrdtSeq(ocOrderDeliveryHistory.getOrderPrdtSeq());

				OcOrderProduct ocOrderProduct = ocOrderProductDao.selectByPrimaryKey(setOrderPorduct);

				if (!UtilsObject.isNotEmpty(ocOrderProduct)) {
					PrdtStatCode = ocOrderProduct.getOrderPrdtStatCode();
				}
			}

			log.debug("######### 상품 테이블의 배송상태 대상 비교##################" + PrdtStatCode + ":::::" + DlvyStatCode);

			// 변경 처리 대상 상품 상태 코드
			String orderPrdtStatCode = "";

			// 상품 준비중 ( 상품상태 : 결제완료 , 배송상태 : 상품준비중 )
			if (UtilsText.equals(PrdtStatCode, "10002") && UtilsText.equals(DlvyStatCode, "10001")) {
				orderPrdtStatCode = "10003";
				updateBoolean = true;
				noteText = "결제완료 -> 상품준비중 변경";

			}
			// 상품 출고 ( 상품상태 : 상품준비중 , 배송상태 : 상품출고 )
			if (UtilsText.equals(PrdtStatCode, "10003") && UtilsText.equals(DlvyStatCode, "10002")) {
				orderPrdtStatCode = "10004";
				updateBoolean = true;
				noteText = "상품준비중 -> 상품출고 변경";

			}

			// 상품 출고 ( 상품상태 : 상품준비중 , 배송상태 :배송중 ) -- 업체만
			if (UtilsText.equals(PrdtStatCode, "10003") && UtilsText.equals(DlvyStatCode, "10003")) {
				orderPrdtStatCode = "10005";
				updateBoolean = true;
				noteText = "상품준비중 -> 배송중 변경";

			}

			// 배송중 ( 상품상태 : 상품출고 , 배송상태 : 배송중 )
			if (UtilsText.equals(PrdtStatCode, "10004") && UtilsText.equals(DlvyStatCode, "10003")) {
				orderPrdtStatCode = "10005";
				updateBoolean = true;
				noteText = "상품출고 -> 배송중 변경";

			}

			// 픽업준비완료 ( 상품상태 : 상품출고 , 배송상태 : 픽업준비완료 )
			if (UtilsText.equals(PrdtStatCode, "10004") && UtilsText.equals(DlvyStatCode, "10004")) {
				orderPrdtStatCode = "10006";
				updateBoolean = true;
				noteText = "상품출고 -> 픽업준비완료 변경";

			}

			// 배송완료 ( 상품상태 : 배송중 , 배송상태 : 배송완료) -- 업체만
			if (UtilsText.equals(PrdtStatCode, "10005") && UtilsText.equals(DlvyStatCode, "10005")) {
				orderPrdtStatCode = "10007";
				updateBoolean = true;
				noteText = "배송중 -> 배송완료 변경";

			}

			// 배송완료 ( 상품상태 : 픽업준비완료 , 배송상태 : 배송완료)
			if (UtilsText.equals(PrdtStatCode, "10006") && UtilsText.equals(DlvyStatCode, "10005")) {
				orderPrdtStatCode = "10007";
				updateBoolean = true;
				noteText = "픽업준비완료 -> 배송완료 변경";

			}
			// 구매 확정 ( 상품상태 : 배송완료 , 배송상태 : 배송완료) - 업체만
			if (UtilsText.equals(PrdtStatCode, "10007") && UtilsText.equals(DlvyStatCode, "10008")) {
				orderPrdtStatCode = "10008";
				updateBoolean = true;
				noteText = "배송완료 -> 구매확정 변경";

			}

			log.debug("::::::::::" + updateBoolean + "##" + PrdtStatCode + ":::::" + DlvyStatCode);
			/*
			 * 상품의 배송 상태와 배송의 배송 상태가 같은경우 상태를 동일 하게 Update진행 한다.
			 */
			if (updateBoolean) {
				log.debug(
						"######### 상품 테이블의 배송상태 변경 처리  진행 ##################" + PrdtStatCode + ":::::" + DlvyStatCode);

				String orderNo = ocOrderDeliveryHistory.getOrderNo();
				Integer orderPrdtSeq = ocOrderDeliveryHistory.getOrderPrdtSeq();

				OcOrderProduct ocOrderProduct = new OcOrderProduct();
				ocOrderProduct.setOrderNo(orderNo);
				ocOrderProduct.setOrderPrdtSeq(orderPrdtSeq);
				ocOrderProduct.setModerNo(adminNo);
				ocOrderProduct.setOrderPrdtStatCode(orderPrdtStatCode);

				int updatePrdtCnt = ocOrderProductDao.updateOrderPrdtVendorStatCode(ocOrderProduct);

				//상품 상태 업데이트 안됨
				if( updatePrdtCnt < 1 ) {
					result.put("result", Const.BOOLEAN_FALSE);
					result.put("resultMsg", "주문번호/주문순번 확인필요");
					return result;
				}
				
				OcOrderProductHistory ocOrderProductHistory = new OcOrderProductHistory();
				ocOrderProductHistory.setOrderNo(orderNo);
				ocOrderProductHistory.setOrderPrdtSeq(orderPrdtSeq);
				ocOrderProductHistory.setNoteText(noteText);
				ocOrderProductHistory.setRgsterNo(adminNo);

				ocOrderProductHistoryDao.insertProductHistorybyOrderNo(ocOrderProductHistory);

			}

			result.put("result", Const.BOOLEAN_TRUE);
			result.put("resultMsg", "완료");

		} catch (Exception e) {
			result.put("result", Const.BOOLEAN_FALSE);
			result.put("resultMsg", (result.get("resultMsg") == null ? "실패" : result.get("resultMsg")) );
			e.printStackTrace(System.err);
			throw new Exception(e.toString());
		}

		return result;
	}

	/**
	 *
	 * @Desc : 판매취소 요청
	 * @Method Name : updateDeliveryPrdtCancelPopup
	 * @Date : 2019. 4. 24.
	 * @Author : NKB
	 * @param deliveryCancelList
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateDeliveryPrdtCancelPopup(List<OcOrderProduct> deliveryCancelList,
			OcOrderProduct param) throws Exception {

		Map<String, Object> result = new HashMap<>();

		// update 결과 건수
		int updateCnt = 0;
		String adminNo = LoginManager.getUserDetails().getAdminNo();

		try {

			for (OcOrderProduct ocOrderProduct : deliveryCancelList) {
				log.debug(":::::::::::::::" + ocOrderProduct);
				ocOrderProduct.setSellCnclRsnCode(param.getSellCnclRsnCode());
				ocOrderProduct.setSellCnclRsnText(param.getSellCnclRsnText());
				ocOrderProduct.setSellCnclReqtrNo(adminNo);
				ocOrderProduct.setModerNo(adminNo);
				ocOrderProduct.setVndrNo(param.getVndrNo());
				updateCnt += ocOrderProductDao.updateOrderPrdtVendorCancel(ocOrderProduct);
			}

			log.debug(":updateDeliveryPrdtCancelPopup:::::::::::::::updateCnt::" + updateCnt + ":::::::::::"
					+ deliveryCancelList.size());
			result.put("result", Const.BOOLEAN_TRUE);
		} catch (Exception e) {
			result.put("result", Const.BOOLEAN_FALSE);
			log.debug(e.toString());
			e.printStackTrace(System.err);
			throw new Exception(e.toString());
		}

		return result;
	}

	/**
	 *
	 * @Desc : 선택주문 상품 상세
	 * @Method Name : selectOrderProductDeatail
	 * @Date : 2019. 4. 24.
	 * @Author : NKB
	 * @param ocOrderProduct
	 * @return
	 * @throws Exception
	 */
	public OcOrderProduct selectOrderProductDeatail(OcOrderProduct ocOrderProduct) throws Exception {
		OcOrderProduct prdtInfo = ocOrderProductDao.selectOnlyProductDetail(ocOrderProduct);
		return prdtInfo;
	}

	/**
	 *
	 * @Desc : 발송지연 안내 등록
	 * @Method Name : updateDeliveryPrdtReservationPopup
	 * @Date : 2019. 11. 19.
	 * @Author : NKB, 이강수
	 * @param deliveryCancelList
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateDeliveryPrdtReservationPopup(List<OcOrderProduct> deliveryCancelList,
			Parameter<?> parameter) throws Exception {

		Map<String, Object> result = new HashMap<>();

		// update 결과 건수
		int updateCntOrd = 0;
		int updateCntMessg = 0;
		String adminNo = LoginManager.getUserDetails().getAdminNo();

		try {

			for (OcOrderProduct ocOrderProduct : deliveryCancelList) {

				String cnslScriptSeq = parameter.getString("cnslScriptSeq");
				String orderNo = ocOrderProduct.getOrderNo();
				Integer orderPrdtSeq = ocOrderProduct.getOrderPrdtSeq();

				// 주문정보
				OcOrder ocOrder = new OcOrder();
				ocOrder.setOrderNo(orderNo);
				OcOrder orderDtail = ocOrderDao.selectOrderDetail(ocOrder);

				String siteNo = orderDtail.getSiteNo();
				String sendTelNoText = "";
				String mesgTitleText = "";
				String sndrName = "";

				if (UtilsText.equals(siteNo, Const.SITE_NO_OTS)) {
					sendTelNoText = Config.getString("sys.ots.sender.message.number");
					mesgTitleText = "[ON THE SPOT]에서 보내드립니다.";
					sndrName = "ON THE SPOT";
				} else {
					sendTelNoText = Config.getString("sys.sender.message.number");
					mesgTitleText = "A-RT에서 보내드립니다.";
					sndrName = "ABC MART";
				}

				// 발송지연 일자 Update
				ocOrderProduct.setModerNo(adminNo);
				// String 일자 변경
				Timestamp delaySendDay = UtilsDate.getSqlTimeStamp(
						UtilsDate.parseDate(parameter.getString("delaySendDay"), Const.DEFAULT_DATE_PATTERN));
				ocOrderProduct.setDelaySendDay(delaySendDay);
				updateCntOrd += ocOrderProductDao.updateOrderPrdtVendorReservation(ocOrderProduct);

				String recvTelNoText = orderDtail.getBuyerHdphnNoText();
				String rcvrName = orderDtail.getBuyerName();
				String memberNo = orderDtail.getMemberNo();

				// 전송메세지 정리
				StringBuilder sendMesgCont = new StringBuilder();
				if (UtilsText.equals(siteNo, Const.SITE_NO_OTS)) {
					sendMesgCont.append("[ON THE SPOT] ");
				} else {
					sendMesgCont.append("[ABC마트] ");
				}

				sendMesgCont.append(parameter.getString("mesgContText"));
				sendMesgCont.append("\n\n * 주문번호 : " + orderNo);
				sendMesgCont.append("\n * 상품명 : " + ocOrderProduct.getPrdtName().trim());
				sendMesgCont.append("\n * 옵션 : " + ocOrderProduct.getOptnName().trim());

				String strDelaySendDay = parameter.getString("delaySendDay");
				strDelaySendDay = strDelaySendDay.substring(0, 4) + "년 " + strDelaySendDay.substring(5, 7) + "월 "
						+ strDelaySendDay.substring(8, 10) + "일";
				sendMesgCont.append("\n * 예상 발송일 : " + strDelaySendDay);

				log.debug("sendMesgCont.toString() :::::::::::" + sendMesgCont.toString());
				log.debug("parameter.getString(mesgContText) :::::::::::" + parameter.getString("mesgContText"));
				log.debug("주문번호 :::::::::::" + orderNo);
				log.debug("상품명 :::::::::::" + ocOrderProduct.getPrdtName());
				log.debug("getOptnName :::::::::::" + ocOrderProduct.getOptnName());
				log.debug("수신자 :::::::::::" + recvTelNoText);
				log.debug("sendMesgCont.toString() :::::::::::" + sendMesgCont.toString());

				// 메세지 저장
				CmMessageSendHistory cmMessageSendHistory = new CmMessageSendHistory();
				cmMessageSendHistory.setSendTelNoText(sendTelNoText); // 발송전화번호
				cmMessageSendHistory.setSndrName(sndrName); // 발송자명
				cmMessageSendHistory.setRecvTelNoText(recvTelNoText); // 수신전화번호
				cmMessageSendHistory.setRcvrName(rcvrName); // 수신자명
				cmMessageSendHistory.setMesgTitleText(mesgTitleText); // 메시지 제목
				cmMessageSendHistory.setMesgContText(sendMesgCont.toString()); // 메세지 내용
				cmMessageSendHistory.setSendTypeCode(CommonCode.SEND_TYPE_CODE_MMS); // 전송 타입 10002 : MMS
				cmMessageSendHistory.setSendYn(Const.BOOLEAN_FALSE);
				cmMessageSendHistory.setSiteNo(siteNo); // 사이트번호
				cmMessageSendHistory.setMemberNo(memberNo); // 회원번호
				cmMessageSendHistory.setOrderNo(orderNo); // 주문번호
				cmMessageSendHistory.setOrderPrdtSeq(orderPrdtSeq); // 주문상품순번
				cmMessageSendHistory.setCnslScriptSeq(cnslScriptSeq); // 상담스크립트순번
				cmMessageSendHistory.setAdminSendYn(Const.BOOLEAN_TRUE); // 관리자발송여부
				cmMessageSendHistory.setRgsterNo(adminNo); // 등록자 updateCntMessg +=
				baseCmMessageSendHistoryDao.insert(cmMessageSendHistory);
			}

			log.debug(":updateDeliveryPrdtCancelPopup:::::::::::::::updateCnt::" + updateCntOrd + ":::::::::::"
					+ deliveryCancelList.size());
			result.put("result", Const.BOOLEAN_TRUE);
		} catch (Exception e) {
			result.put("result", Const.BOOLEAN_FALSE);
			log.debug(e.toString());
			e.printStackTrace(System.err);
			throw new Exception(e.toString());
		}

		return result;
	}

	/**
	 *
	 * @Desc : 판매취소현황 목록
	 * @Method Name : searchDeliveryOrderList
	 * @Date : 2019. 5. 14.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<OcDelivery> deliveryOrderVendorCancelList(Pageable<OcDelivery, OcDelivery> pageable) throws Exception {

		// 배송목록
		List<OcDelivery> deliveryOrderList = null;

		log.debug(":::  pageable.getBean().getSiteNoArr()  ::::::" + pageable.getBean().getSiteNoArr());

		// 사이트
		String siteNo = pageable.getBean().getSiteNo();
		if (siteNo != null && !Arrays.asList(siteNo).contains("")) {
			pageable.getBean().setSiteNoList(Arrays.asList(siteNo));
		}

		// 결제 구분
		String[] deviceCodeArr = pageable.getBean().getDeviceCodeArr();
		if (deviceCodeArr != null && !Arrays.asList(deviceCodeArr).contains("")) {
			pageable.getBean().setDeviceCodeList(Arrays.asList(deviceCodeArr));
		}

		/*
		 * 기타 param Set
		 */
		// 배송중단여부 N
		pageable.getBean().setDlvyDscntcYn(Const.BOOLEAN_FALSE);
		// 분실처리
		pageable.getBean().setMissProcYn(Const.BOOLEAN_FALSE);
		pageable.getBean().setImpsbltProcYn(Const.BOOLEAN_FALSE); // 불가 처리 여부 IMPSBLTPROCYN

		// Total Count
		int totalCount = ocDeliveryDao.selectDeliveryOrderVendorCancelCount(pageable);

		if (totalCount > 0) {
			deliveryOrderList = ocDeliveryDao.selectDeliveryOrderVendorCancelList(pageable);

			for (OcDelivery ocDelivery : deliveryOrderList) {
				// 목록이기에 권한과 상관없이 마스킹 처리 Y
				ocDelivery.setIsListYn(Const.BOOLEAN_TRUE);
			}
			pageable.setContent(deliveryOrderList);
		}
		pageable.setTotalCount(totalCount);

		return pageable.getPage();

	}

	/**
	 * @Desc : 판매취소현황 목록 전체 엑셀다운로드
	 * @Method Name : getDeliveryOrderVendorCancelAllExcel
	 * @Date : 2019. 11. 18.
	 * @Author : 이강수
	 * @param Pageable<OcDelivery, OcDelivery>
	 * @return Page<OcDelivery>
	 * @throws Exception
	 */
	public Page<OcDelivery> getDeliveryOrderVendorCancelAllExcel(Pageable<OcDelivery, OcDelivery> pageable)
			throws Exception {

		// 배송목록
		List<OcDelivery> deliveryOrderList = null;

		log.debug(":::  pageable.getBean().getSiteNoArr()  ::::::" + pageable.getBean().getSiteNoArr());

		// 사이트
		String siteNo = pageable.getBean().getSiteNo();
		if (siteNo != null && !Arrays.asList(siteNo).contains("")) {
			pageable.getBean().setSiteNoList(Arrays.asList(siteNo));
		}

		// 결제 구분
		String[] deviceCodeArr = pageable.getBean().getDeviceCodeArr();
		if (deviceCodeArr != null && !Arrays.asList(deviceCodeArr).contains("")) {
			pageable.getBean().setDeviceCodeList(Arrays.asList(deviceCodeArr));
		}

		/*
		 * 기타 param Set
		 */
		// 배송중단여부 N
		pageable.getBean().setDlvyDscntcYn(Const.BOOLEAN_FALSE);
		// 분실처리
		pageable.getBean().setMissProcYn(Const.BOOLEAN_FALSE);
		pageable.getBean().setImpsbltProcYn(Const.BOOLEAN_FALSE); // 불가 처리 여부 IMPSBLTPROCYN

		// Total Count
		int totalCount = ocDeliveryDao.selectDeliveryOrderVendorCancelCount(pageable);

		if (totalCount > 0) {
			deliveryOrderList = ocDeliveryDao.selectSellCnclAllExcelDownload(pageable);
			pageable.setContent(deliveryOrderList);
		}
		pageable.setTotalCount(totalCount);

		return pageable.getPage();
	}

	/**
	 *
	 * @Desc : 업체별 클레임 건수 조회
	 * @Method Name : getDeliveryOrderVendorClaim
	 * @Date : 2019. 5. 15.
	 * @Author : NKB
	 * @param ocDelivery
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDeliveryOrderVendorClaim(OcDelivery ocDelivery) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();

		List<OcDelivery> list = ocDeliveryDao.selectDelveryOrderVendorClaimCount(ocDelivery);

		/* 10000 -- 취소접수 10006 -- 교환접수 10018 -- 반품접수 */
		int orderPrdtStatCodeClaim = (int) list.stream()
				.filter(o -> CommonCode.CLM_STAT_CODE_CANCEL_ACCEPT.equals(o.getClmStatCode())
						|| CommonCode.CLM_STAT_CODE_EXCHANGE_ACCEPT.equals(o.getClmStatCode())
						|| CommonCode.CLM_STAT_CODE_RETURN_ACCEPT.equals(o.getClmStatCode()))
				.count();

		/* 10009 --수거지시(교환) 10021 --수거지시(반품) */
		int orderPrdtStatCodeDeliveryReturn = (int) list.stream()
				.filter(o -> CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_PICKUP_ORDER.equals(o.getClmStatCode())
						|| CommonCode.CLM_PRDT_STAT_CODE_RETURN_PICKUP_ORDER.equals(o.getClmStatCode()))
				.count();

		/* 10015 -- 교환배송중 */
		int orderPrdtStatCodeExchangeDelivery = (int) list.stream()
				.filter(o -> CommonCode.CLM_STAT_CODE_EXCHANGE_DELIVERY_PROC.equals(o.getClmStatCode())).count();

		/* 10005 -- 취소완료 , 10016 -- 교환완료 , 10026 -- 반품완료 */
		int orderPrdtStatCodeClaimComplete = (int) list.stream()
				.filter(o -> CommonCode.CLM_PRDT_STAT_CODE_CANCEL_FINISH.equals(o.getClmStatCode())
						|| CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_FINISH.equals(o.getClmStatCode())
						|| CommonCode.CLM_PRDT_STAT_CODE_RETURN_FINISH.equals(o.getClmStatCode()))
				.count();

		/* 10002 -- 반품불가 , 10001 -- 교환불가 , 10000 - 취소접수 철화 */
		int orderPrdtStatCodeClaimCancel = (int) list.stream()
				.filter(o -> CommonCode.CLM_PRDT_STAT_CODE_CANCEL_FINISH.equals(o.getClmStatCode())
						|| CommonCode.CLM_PRDT_STAT_CODE_EXCHANGE_FINISH.equals(o.getClmStatCode())
						|| CommonCode.CLM_PRDT_STAT_CODE_RETURN_FINISH.equals(o.getClmStatCode()))
				.count();

		retMap.put("orderPrdtStatCodeClaim", orderPrdtStatCodeClaim); // 클레임 접수 (미확인 클레임)
		retMap.put("orderPrdtStatCodeDeliveryReturn", orderPrdtStatCodeDeliveryReturn); // 회수중
		retMap.put("orderPrdtStatCodeExchangeDelivery", orderPrdtStatCodeExchangeDelivery); // 교환품 배송중
		retMap.put("orderPrdtStatCodeClaimComplete", orderPrdtStatCodeClaimComplete); // 클레임 완료
		retMap.put("orderPrdtStatCodeClaimCancel", orderPrdtStatCodeClaimCancel); // 클레임 불가 요청

		return retMap;

	}

	/**
	 *
	 * @Desc : 업체 주문 배송관리 - List
	 * @Method Name : deliveryOrderVendorList
	 * @Date : 2019. 4. 21.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<OcDelivery> deliveryOrderVendorClaimList(Pageable<OcDelivery, OcDelivery> pageable) throws Exception {

		// 상품대기 목록 - 배송중단여부가 Y 인 데이타를 기준으로 조회
		List<OcDelivery> deliveryOrderVendorClaimList = null;
		String clmGbnCode = pageable.getBean().getClmGbnCode();
		// pageable.getBean().setDlvyDscntcYn(Const.BOOLEAN_FALSE); // 배송중단 여부
		// pageable.getBean().setMissProcYn(Const.BOOLEAN_FALSE); // 분실처리 여부
		// pageable.getBean().setMmnyPrdtYn(Const.BOOLEAN_FALSE); // 자사 여부 - 자사만

		log.debug("clmGbnCode :::::::::::::::" + clmGbnCode);

		// 주문번호 검색은 기존 조건을 사용하기위해서
		if (!StringUtil.isNullOrEmpty(pageable.getBean().getOrderNo())) {
			pageable.getBean().setSearchOrderKey("orderNo");
			pageable.getBean().setSearchOrderValue(pageable.getBean().getOrderNo());
		}

		// 사이트
		String[] siteArr = pageable.getBean().getSiteNoArr();
		if (siteArr != null && !Arrays.asList(siteArr).contains("")) {
			pageable.getBean().setSiteNoList(Arrays.asList(siteArr));
		}

		// 취소
		if ("C".equals(clmGbnCode)) {

			String[] clmPrdtStatCArr = pageable.getBean().getClmPrdtStatCArr();
			if (clmPrdtStatCArr != null && !Arrays.asList(clmPrdtStatCArr).contains("")) {
				pageable.getBean().setClmPrdtStatList(Arrays.asList(clmPrdtStatCArr));
			}
			log.debug("C :::::::::::::::" + clmPrdtStatCArr.length);
		}

		// 교환
		if ("E".equals(clmGbnCode)) {
			String[] clmPrdtStatEArr = pageable.getBean().getClmPrdtStatEArr();
			if (clmPrdtStatEArr != null && !Arrays.asList(clmPrdtStatEArr).contains("")) {
				pageable.getBean().setClmPrdtStatList(Arrays.asList(clmPrdtStatEArr));
			}
			log.debug("E :::::::::::::::" + clmPrdtStatEArr.length);
		}

		// 반품
		if ("R".equals(clmGbnCode)) {
			String[] clmPrdtStatRArr = pageable.getBean().getClmPrdtStatRArr();

			log.debug("R Arrays.asList(clmPrdtStatRArr) :::::::::::::::" + Arrays.asList(clmPrdtStatRArr));

			if (clmPrdtStatRArr != null && !Arrays.asList(clmPrdtStatRArr).contains("")) {
				pageable.getBean().setClmPrdtStatList(Arrays.asList(clmPrdtStatRArr));
			}
			log.debug("R :::::::::::::::" + clmPrdtStatRArr.length);
		}

		// Total Count
		int totalCount = ocDeliveryDao.selectDeliveryOrderVendorClaimCount(pageable);

		log.info("::::::::::::::::::::::::::::::::" + totalCount);
		if (totalCount > 0) {
			deliveryOrderVendorClaimList = ocDeliveryDao.selectDeliveryOrderVendorClaimList(pageable);

			for (OcDelivery ocDelivery : deliveryOrderVendorClaimList) {
				// 목록이기에 권한과 상관없이 마스킹 처리 Y
				ocDelivery.setIsListYn(Const.BOOLEAN_TRUE);
			}

			pageable.setContent(deliveryOrderVendorClaimList);
		}

		pageable.setTotalCount(totalCount);

		return pageable.getPage();

	}

	/**
	 *
	 * @Desc :
	 * @Method Name : updateDeliveryOrderVendorClaim
	 * @Date : 2019. 5. 15.
	 * @Author : NKB
	 * @param ocDelivery
	 * @param ocOrderDeliveryHistory
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateDeliveryOrderVendorClaim(OcClaim ocClaim, OcClaimProduct ocClaimProduct)
			throws Exception {

		Map<String, Object> result = new HashMap<>();
		String adminNo = LoginManager.getUserDetails().getAdminNo();

		try {

			// 수정자 셋팅
			ocClaimProduct.setModerNo(adminNo);
			String clmPrdtStatCodeBefor = ocClaimProduct.getClmPrdtStatCodesBefor(); // 이전 클레임상태
			String clmPrdtStatCode = ocClaimProduct.getClmPrdtStatCode(); // 변경 클레임상태

			// 클레임 상품 상태 업데이트
			int updateCnt = ocClaimProductDao.updateClaimProductStatCode(ocClaimProduct);

			if (updateCnt > 0) {
				OcClaimStatusHistory ocClaimStatusHistory = new OcClaimStatusHistory();
				ocClaimStatusHistory.setClmNo(ocClaimProduct.getClmNo());
				ocClaimStatusHistory.setClmPrdtSeq(ocClaimProduct.getClmPrdtSeq());
				ocClaimStatusHistory.setClmPrdtStatCode(clmPrdtStatCode); // 클레임상품 상태 코드
				ocClaimStatusHistory.setStockGbnCode(null);
				ocClaimStatusHistory.setNoteText(null);
				ocClaimStatusHistory.setRgsterNo(adminNo); // 등록자

				ocClaimStatusHistoryDao.insertClaimStatusHistory(ocClaimStatusHistory); // 클레임상태이력 등록

				ocClaim.setClmStatCode(clmPrdtStatCode);
				int updateMstCnt = ocClaimDao.updateClaimStatCode(ocClaim);

			}

			result.put("result", Const.BOOLEAN_TRUE);

		} catch (Exception e) {
			result.put("result", Const.BOOLEAN_FALSE);
			e.printStackTrace(System.err);
			throw new Exception(e.toString());
		}

		return result;
	}

	public Page<OcDelivery> deliveryOrderVendorImpossibilityList(Pageable<OcDelivery, OcDelivery> pageable)
			throws Exception {

		// 배송목록
		List<OcDelivery> deliveryOrderList = null;

		log.debug(":::  pageable.getBean().getSiteNoArr()  ::::::" + pageable.getBean().getSiteNoArr());

		// 사이트
		String siteNo = pageable.getBean().getSiteNo();
		if (siteNo != null && !Arrays.asList(siteNo).contains("")) {
			pageable.getBean().setSiteNoList(Arrays.asList(siteNo));
		}

		// 결제 구분
		String[] deviceCodeArr = pageable.getBean().getDeviceCodeArr();
		if (deviceCodeArr != null && !Arrays.asList(deviceCodeArr).contains("")) {
			pageable.getBean().setDeviceCodeList(Arrays.asList(deviceCodeArr));
		}

		/*
		 * 기타 param Set
		 */
		// 배송중단여부 N
		pageable.getBean().setDlvyDscntcYn(Const.BOOLEAN_FALSE);
		// 분실처리
		pageable.getBean().setMissProcYn(Const.BOOLEAN_FALSE);

		// Total Count
		int totalCount = ocDeliveryDao.selectDeliveryOrderVendorImpossibilityCount(pageable);

		if (totalCount > 0) {
			deliveryOrderList = ocDeliveryDao.selectDeliveryOrderVendorImpossibilityList(pageable);

			for (OcDelivery ocDelivery : deliveryOrderList) {
				// 목록이기에 권한과 상관없이 마스킹 처리 Y
				ocDelivery.setIsListYn(Const.BOOLEAN_TRUE);
			}
			pageable.setContent(deliveryOrderList);
		}

		pageable.setTotalCount(totalCount);

		return pageable.getPage();

	}
	// =========================================================================================================================

	/**
	 * @Desc : 재배송 가능 상태 확인
	 * @Method Name : getReDeliveryCheck
	 * @Date : 2019. 3. 25.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderDeliveryHistory
	 * @return
	 */
	public Map<String, Object> getReDeliveryCheck(OcOrderDeliveryHistory ocOrderDeliveryHistory) throws Exception {
		Map<String, Object> result = new HashMap<>();

		// 1. 배송 상태 확인 ( DLVY_STAT_CODE 결제완료 10000 , 상품준비중 10001 , 상품출고 10002
		OcOrderDeliveryHistory dlvyInfo = ocOrderDeliveryHistoryDao.getPrdtDeliveryInfo(ocOrderDeliveryHistory);

		result.put("result", Const.BOOLEAN_TRUE);

		if (UtilsText.equals(dlvyInfo.getDlvyStatCode(), CommonCode.DLVY_STAT_CODE_PAYMENT_FINISH)) {
		} else if (UtilsText.equals(dlvyInfo.getDlvyStatCode(), CommonCode.DLVY_STAT_CODE_PRODUCT_PREPARATION)) {
		} else if (UtilsText.equals(dlvyInfo.getDlvyStatCode(), CommonCode.DLVY_STAT_CODE_PRODUCT_DELIVERY)) {
		} else {

			result.put("result", Const.BOOLEAN_FALSE);
		}

		return result;
	}

	/**
	 * @Desc :재배송 처리 팝업 기본 정보
	 * @Method Name : getOrderDeliveryInfo
	 * @Date : 2019. 3. 25.
	 * @Author : flychani@3top.co.kr
	 * @param parameter
	 */
	public void getOrderDeliveryInfo(Parameter<OcOrderDeliveryHistory> parameter) throws Exception {
		// TODO Auto-generated method stub

		// 1 주문 마스터 정보
		OcOrder ocOrder = new OcOrder();
		ocOrder.setOrderNo(parameter.get().getOrderNo());

		OcOrder orderDtail = ocOrderDao.selectOrderDetail(ocOrder);
		// 2.주문 배송 정보
		OcOrderDeliveryHistory ocOrderDeliveryHistory = new OcOrderDeliveryHistory();
		ocOrderDeliveryHistory.setOrderNo(parameter.get().getOrderNo());
		ocOrderDeliveryHistory.setOrderPrdtSeq(parameter.get().getOrderPrdtSeq());

		OcOrderDeliveryHistory dlvyInfo = ocOrderDeliveryHistoryDao.getPrdtDeliveryInfo(ocOrderDeliveryHistory);
		// 3.배송처 정보
		List<SyCodeDetail> code = commonCodeService.getCodeNoName(CommonCode.STOCK_GBN_CODE).stream()
				.filter(c -> !UtilsText.equals(c.getAddInfo1(), Const.BOOLEAN_FALSE)).collect(Collectors.toList()); // 입점사코드제거

		parameter.addAttribute("STOCK_GBN_CODE", code); // 발송처 구분
		parameter.addAttribute("dlvyInfo", dlvyInfo); // 배송 정보
		parameter.addAttribute("orderDtail", orderDtail); // 주문 정보

	}

	/**
	 * @Desc :
	 * @Method Name : setReDeliverySave
	 * @Date : 2019. 3. 25.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderDeliveryHistory
	 * @return
	 */
	public Map<String, Object> setReDeliverySave(OcOrderDeliveryHistory params) throws Exception {
		Map<String, Object> result = new HashMap<>();
		UserDetails user = LoginManager.getUserDetails();

		String resultFlag = Const.BOOLEAN_TRUE;
		String resultMsg = "";

		System.out.println("params >>> " + params);

		// 배송중 이전 DATA 상품준비중 상태 여부 확인
		OcOrderDeliveryHistory dlvyInfo = ocOrderDeliveryHistoryDao.getPrdtDeliveryInfo(params);

		if (UtilsText.equals(dlvyInfo.getDlvyStatCode(), CommonCode.DLVY_STAT_CODE_PAYMENT_FINISH)) { // 결제완료
		} else if (UtilsText.equals(dlvyInfo.getDlvyStatCode(), CommonCode.DLVY_STAT_CODE_PRODUCT_PREPARATION)) { // 상품준비중
		} else if (UtilsText.equals(dlvyInfo.getDlvyStatCode(), CommonCode.DLVY_STAT_CODE_PRODUCT_DELIVERY)) { // 상품출고
		} else {
			resultFlag = Const.BOOLEAN_FALSE;
			resultMsg = "재배송처리 할 수 없는 상태입니다.";
			result.put("result", resultFlag);
		}
		// 추가 배송 seq 추가 처리
		if (UtilsText.equals(resultFlag, Const.BOOLEAN_TRUE)) {

			int inCnt = 0;
			try {

				OcOrderDeliveryHistory newDlvyInfo = new OcOrderDeliveryHistory();
				newDlvyInfo.setOrderNo(params.getOrderNo());
				newDlvyInfo.setOrderPrdtSeq(params.getOrderPrdtSeq());
				newDlvyInfo.setStockGbnCode(params.getStockGbnCode()); // 선택된 발송처
				newDlvyInfo.setStoreNo(params.getStoreNo()); // 매장 선택 후 선택된 매장 정보
				newDlvyInfo.setDlvyDscntcYn(Const.BOOLEAN_FALSE);// 배송중단여부
				newDlvyInfo.setMissProcYn(Const.BOOLEAN_FALSE);// 분실처리여부
				newDlvyInfo.setWmsSendYn(Const.BOOLEAN_FALSE); // WMS전송여부
				newDlvyInfo.setDlvyStatCode(CommonCode.DLVY_STAT_CODE_PRODUCT_PREPARATION); // 배송상태코드 상품준비중 상태
				newDlvyInfo.setRgsterNo(user.getAdminNo());

				System.out.println("newDlvyInfo >>> " + newDlvyInfo);
				inCnt = ocOrderDeliveryHistoryDao.setReDelivery(newDlvyInfo);

				if (inCnt > 0) {
					result.put("result", Const.BOOLEAN_TRUE);
				} else {
					result.put("result", Const.BOOLEAN_FALSE);
				}

			} catch (Exception e) {
				log.debug(e.toString());
				e.printStackTrace(System.err);
				result.put("result", Const.BOOLEAN_FALSE);
			}
		}

		// 이전 배송 seq 배송 취소 처리
		if (UtilsText.equals(resultFlag, Const.BOOLEAN_TRUE)) {

			int upCnt = 0;
			try {

				dlvyInfo.setDlvyStatCode(CommonCode.DLVY_STAT_CODE_DELIVERY_CANCEL); // 배송 취소 처리
				dlvyInfo.setModerNo(user.getAdminNo());

				System.out.println("dlvyInfo >>> " + dlvyInfo);
				upCnt = ocOrderDeliveryHistoryDao.setChangeDelivery(dlvyInfo);

				if (upCnt > 0) {
					result.put("result", Const.BOOLEAN_TRUE);
				} else {
					result.put("result", Const.BOOLEAN_FALSE);
				}

			} catch (Exception e) {
				log.debug(e.toString());
				e.printStackTrace(System.err);
				result.put("result", Const.BOOLEAN_FALSE);
			}
		}

		// 상태 변경 이력 처리

		return result;
	}

	/**
	 * @Desc : 상품이 배송중일 경우 배송완료 상태로 변경
	 * @Method Name : setOrderDeliveryPrdt
	 * @Date : 2020. 4. 7.
	 * @Author : 3top
	 * @param orderNo
	 * @param orderPrdtSeq
	 * @throws Exception
	 */
	public void setOrderDeliveryPrdt(String orderNo, int orderPrdtSeq) throws Exception {
		DeliveryPrdtVO params = new DeliveryPrdtVO();

		// 배송중인 상품 조회
		params.setOrderNo(orderNo);
		params.setOrderPrdtSeq(orderPrdtSeq);
		params = ocDeliveryDao.selectDeliveryPrdt(params);

		if (UtilsObject.isNotEmpty(params)) {
			params.setModerNo(LoginManager.getUserDetails().getAdminNo());
			// 배송 히스토리 배송상태 업데이트  
			ocOrderDeliveryHistoryDao.updateOrderDeliveryStat(params);
			// 주문 상품 배송상태 업데이트
			ocOrderProductDao.udpateOrderPrdtDeliveryStat(params);
			// 주문배송알림이력 등록
			ocOrderDeliveryAlertHistoryDao.insertOcOrderDeliveryAlertHistory(params.getDlvyIdText());
		} else {
			throw new Exception("상품이 존재하지 않습니다.");
		}
	}
	
	/**
	 * @Desc      	: 상품 대기조회 처리구분 코드 수정
	 * @Method Name : updateConfirmStandby
	 * @Date  	  	: 2020. 5. 28.
	 * @Author    	: sic
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateConfirmStandby(OcDelivery[] param) throws Exception{
		Map<String, Object> rsMap = new HashMap<>();
		int count = 0;
		try {
			for(OcDelivery dlvyParam : param) {
				ocDeliveryDao.updateConfirmStandbyProc(dlvyParam);
				count ++;
			}
			rsMap.put("result", true);
			rsMap.put("count", count);
		}catch(Exception e) {
			rsMap.put("result", false);
			rsMap.put("msg", e.getMessage());
		}
		return rsMap;
	}
}
