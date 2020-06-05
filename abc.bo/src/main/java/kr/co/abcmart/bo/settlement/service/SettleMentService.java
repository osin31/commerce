package kr.co.abcmart.bo.settlement.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import kr.co.abcmart.bo.settlement.model.master.SeExactCalculation;
import kr.co.abcmart.bo.settlement.model.master.SeExactCalculationBrand;
import kr.co.abcmart.bo.settlement.model.master.SeExactCalculationBrandProduct;
import kr.co.abcmart.bo.settlement.model.master.SeExactCalculationMemo;
import kr.co.abcmart.bo.settlement.repository.master.SeExactCalculationBrandDao;
import kr.co.abcmart.bo.settlement.repository.master.SeExactCalculationBrandProductDao;
import kr.co.abcmart.bo.settlement.repository.master.SeExactCalculationDao;
import kr.co.abcmart.bo.settlement.repository.master.SeExactCalculationMemoDao;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SettleMentService {
	@Autowired
	private SeExactCalculationDao seExactCalculationDao;

	@Autowired
	private SeExactCalculationBrandProductDao seExactCalculationBrandProductDao;

	@Autowired
	private SeExactCalculationBrandDao seExactCalculationBrandDao;

	@Autowired
	private SeExactCalculationMemoDao seExactCalculationMemoDao;

	@Autowired
	private SettleMentService settleMentService;

	/**
	 * @Desc : 업체별 정산관리 리스트
	 * @Method Name : getSettleMentListList
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SeExactCalculation> getSettleMentListList(Pageable<SeExactCalculation, SeExactCalculation> pageable)
			throws Exception {
		int totalCount = seExactCalculationDao.selectSettleMentListCount(pageable);
		;

		pageable.setTotalCount(totalCount);

		if (totalCount > 0) {
			List<SeExactCalculation> list = seExactCalculationDao.selectSettleMentList(pageable);
			pageable.setContent(list);
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 입점사 최종정산금액 합계
	 * @Method Name : getSettlementTotalAmt
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param seExactCalculation
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSettlementTotalAmt(SeExactCalculation seExactCalculation) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String totalAmt = seExactCalculationDao.selectSettleMentTotalAmt(seExactCalculation);

		if (totalAmt != null) {
			resultMap.put("searchSuccYn", "Y");
			resultMap.put("totalAmt", totalAmt);
		} else {
			resultMap.put("searchSuccYn", "N");
			resultMap.put("totalAmt", "0");
		}

		return resultMap;
	}

	/**
	 * @Desc : 월정산 관리자 메모 조회
	 * @Method Name : getSeExactCalMemoData
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param seExactCalculationMemo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSeExactCalMemoData(SeExactCalculationMemo seExactCalculationMemo) throws Exception {
		ModelMap resultMap = new ModelMap();

		// 관리자 메모 조회
		List<SeExactCalculationMemo> memoData = seExactCalculationMemoDao
				.selectSeExactCalculationMemoData(seExactCalculationMemo);

		resultMap.put("memoInfo", memoData);

		return resultMap;
	}

	/**
	 * @Desc : 월정산 메인 리스트
	 * @Method Name : getSeExactCalcMonthSettle
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SeExactCalculationBrand> getSeExactCalcMonthSettle(
			Pageable<SeExactCalculation, SeExactCalculationBrand> pageable) throws Exception {
		int totalCount = seExactCalculationBrandDao.selectSeExactCalcMonthSettleMentCount(pageable);
		;

		pageable.setTotalCount(totalCount);

		if (totalCount > 0) {
			pageable.setContent(seExactCalculationBrandDao.selectSeExactCalcMonthSettleMentList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * 
	 * @Desc : 월장산 브랜드 리스트
	 * @Method Name : getSeExactCalcSellAmt
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SeExactCalculationBrandProduct> getSeExactCalcSellAmt(
			Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable) throws Exception {
		int totalCount = seExactCalculationBrandProductDao.selectSeExactCalcSellAmtCount(pageable);
		;

		pageable.setTotalCount(totalCount);

		if (totalCount > 0) {
			pageable.setContent(seExactCalculationBrandProductDao.selectSeExactCalcSellAmtList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 월장산 배송비 리스트
	 * @Method Name : getSeExactCalcDlvyAmt
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SeExactCalculationBrandProduct> getSeExactCalcDlvyAmt(
			Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable) throws Exception {
		int totalCount = seExactCalculationBrandProductDao.selectSeExactCalcDlvyAmtCount(pageable);
		;

		pageable.setTotalCount(totalCount);

		if (totalCount > 0) {
			pageable.setContent(seExactCalculationBrandProductDao.selectSeExactCalcDlvyAmtList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 월정산 프로모션 탭 리스트
	 * @Method Name : getSeExactCalcPromoAmt
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SeExactCalculationBrandProduct> getSeExactCalcPromoAmt(
			Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable) throws Exception {
		int totalCount = seExactCalculationBrandProductDao.selectSeExactCalcPromoAmtCount(pageable);
		;

		pageable.setTotalCount(totalCount);

		if (totalCount > 0) {
			pageable.setContent(seExactCalculationBrandProductDao.selectSeExactCalcPromoAmtList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc :월정산 패널티 리스트
	 * @Method Name : getSeExactCalcPenltyAmt
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SeExactCalculationBrandProduct> getSeExactCalcPenltyAmt(
			Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable) throws Exception {
		int totalCount = seExactCalculationBrandProductDao.selectSeExactCalcPenltyAmtCount(pageable);
		;

		pageable.setTotalCount(totalCount);

		if (totalCount > 0) {
			pageable.setContent(seExactCalculationBrandProductDao.selectSeExactCalcPenltyAmtList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * 
	 * @Desc : 월정산 월정산 정산내역 조회 (패널티 조정 금액, 정산 조정 금액 SUM)
	 * @Method Name : getSettlementMonth
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param seExactCalculation
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSettlementMonth(SeExactCalculation seExactCalculation) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		SeExactCalculation seExactCalculationData = seExactCalculationDao.selectSettleMentMonth(seExactCalculation);

		if (seExactCalculationData == null) {
			resultMap.put("noData", "noData");
		} else {
			SeExactCalculationBrand seExactCalculationBrandParam = new SeExactCalculationBrand();
			seExactCalculationBrandParam.setExcclcSeq(seExactCalculation.getExcclcSeq());

			SeExactCalculationBrand seExactCalculationBrand = seExactCalculationBrandDao
					.selectMdatAmt(seExactCalculationBrandParam);
			// 브랜드에 값을 있을때만
			if (seExactCalculationBrand != null) {
				seExactCalculationData.setPenltyMdatAmt(seExactCalculationBrand.getPenltyMdatAmt());
				seExactCalculationData.setExcclcMdatAmt(seExactCalculationBrand.getExcclcMdatAmt());
			}
			resultMap.put("seExactCalculationData", seExactCalculationData);
		}
		return resultMap;
	}

	/**
	 * 
	 * @Desc : 정산내역중 판매금액, 공제금액, 패널티 금액 만 조회
	 * @Method Name : getSettlementHistory
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param seExactCalculation
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSettlementHistory(SeExactCalculation seExactCalculation) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		SeExactCalculation seExactCalculationData = seExactCalculationDao.selectSettleMentHistory(seExactCalculation);
		if (seExactCalculationData == null) {
			resultMap.put("noData", "noData");
		} else {
			resultMap.put("seExactCalculationData", seExactCalculationData);
		}

		return resultMap;
	}

	/**
	 * @Desc : 패널티 및 정산 조정 금액 수정
	 * @Method Name : updateSeExactBrandMod
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param seExactCalculationBrand
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateSeExactBrandMod(SeExactCalculationBrand seExactCalculationBrand) throws Exception {

		Map<String, Object> result = new HashMap<>();
		String adminNo = LoginManager.getUserDetails().getAdminNo();

		try {

			// 수정자 셋팅
			seExactCalculationBrand.setModerNo(adminNo);
			// 정산 브랜드 조정금액 업데이트
			seExactCalculationBrandDao.updateSeExactCalBrand(seExactCalculationBrand);
			result.put("result", Const.BOOLEAN_TRUE);
		} catch (Exception e) {
			result.put("result", Const.BOOLEAN_FALSE);
			e.printStackTrace(System.err);
			throw new Exception(e.toString());
		}
		return result;
	}

	/**
	 * @Desc :정산 테이블에 업데이트 (패널티 및 정산 조정 금액 SUM값으로 업데이트)
	 * @Method Name : updateSeExactCalSumMod
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param seExactCalculation
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateSeExactCalSumMod(SeExactCalculation seExactCalculation) throws Exception {

		Map<String, Object> result = new HashMap<>();
		String adminNo = LoginManager.getUserDetails().getAdminNo();

		try {

			// 수정자 셋팅
			seExactCalculation.setModerNo(adminNo);

			// 정산 테이블 조회 업데이트 하기전
			SeExactCalculation seExactCalculationBefore = new SeExactCalculation();
			seExactCalculationBefore.setExcclcSeq(seExactCalculation.getExcclcSeq());
			SeExactCalculation seExactCcalculationData = seExactCalculationDao
					.selectByPrimaryKey(seExactCalculationBefore);
			// 정산 테이블에 UPDATE
			SeExactCalculation seExactCcalculationMod = new SeExactCalculation();
			seExactCcalculationMod.setExcclcSeq(seExactCcalculationData.getExcclcSeq());
			seExactCcalculationMod.setPenltyMdatAmt(seExactCalculation.getPenltyMdatAmt());
			seExactCcalculationMod.setExcclcMdatAmt(seExactCalculation.getExcclcMdatAmt());
			seExactCcalculationMod.setExcclcAmt(seExactCcalculationData.getExcclcAmt());
			seExactCcalculationMod.setModerNo(adminNo); // 등록자
			seExactCalculationDao.updateSeExactCalculationMod(seExactCcalculationMod);

			result.put("result", Const.BOOLEAN_TRUE);
		} catch (Exception e) {
			result.put("result", Const.BOOLEAN_FALSE);
			e.printStackTrace(System.err);
			throw new Exception(e.toString());
		}

		return result;
	}

	/**
	 * @Desc : 정산확정
	 * @Method Name : updateExcclcConfirmation
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param seExactCalculation
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateExcclcConfirmation(SeExactCalculation seExactCalculation) throws Exception {
		Map<String, Object> result = new HashMap<>();
		String adminNo = LoginManager.getUserDetails().getAdminNo();

		try {

			// 수정자 셋팅
			seExactCalculation.setModerNo(adminNo);
			seExactCalculation.setExcclcDcsnYn(Const.BOOLEAN_TRUE);

			SeExactCalculationBrand seExactCalculationBrandParam = new SeExactCalculationBrand();
			seExactCalculationBrandParam.setExcclcSeq(seExactCalculation.getExcclcSeq());

			List<SeExactCalculationBrand> seExactCalculationBrand = seExactCalculationBrandDao
					.select(seExactCalculationBrandParam);

			SeExactCalculationBrandProduct seExactCalculationBrandProductParam = new SeExactCalculationBrandProduct();
			seExactCalculationBrandProductParam.setExcclcSeq(seExactCalculation.getExcclcSeq());

			List<SeExactCalculationBrandProduct> seExactCalculationBrandProduct = seExactCalculationBrandProductDao
					.select(seExactCalculationBrandProductParam);
			log.info("seExactCalculationBrand ::" + seExactCalculationBrand.size());
			log.info("seExactCalculationBrandProduct ::" + seExactCalculationBrandProduct.size());

			// 브랜드 및 브랜드 상품이 있을경우에만 정산확정에 만족
			if (seExactCalculationBrand.size() != 0 && seExactCalculationBrandProduct.size() != 0) {
				seExactCalculationDao.updateExcclcConfirmation(seExactCalculation);
				result.put("result", Const.BOOLEAN_TRUE);
			} else {
				result.put("result", "noData");
			}
		} catch (Exception e) {
			result.put("result", Const.BOOLEAN_FALSE);
			e.printStackTrace(System.err);
			throw new Exception(e.toString());
		}

		return result;
	}

	/**
	 * @Desc :관리자 메모 조회
	 * @Method Name : selectExactCalMemoData
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param seExactCalculationMemo
	 * @return
	 * @throws Exception
	 */
	public List<SeExactCalculationMemo> selectExactCalMemoData(SeExactCalculationMemo seExactCalculationMemo)
			throws Exception {
		return seExactCalculationMemoDao.selectSeExactCalculationMemoData(seExactCalculationMemo);
	}

	/**
	 * @Desc :관리자 메모 등록
	 * @Method Name : createSeExactCalculationMemo
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param seExactCalculationMemo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> createSeExactCalculationMemo(SeExactCalculationMemo seExactCalculationMemo)
			throws Exception {
		ModelMap result = new ModelMap();
		UserDetails user = LoginManager.getUserDetails();
		seExactCalculationMemo.setRgsterNo(user.getAdminNo());

		// 접수관리자 메모를 등록한다.
		seExactCalculationMemoDao.insertSeExactCalculationMemo(seExactCalculationMemo);

		// 등록한 메모의 정보를 조회
		result.addAttribute("memoInfo", settleMentService.selectExactCalMemoData(seExactCalculationMemo));
		return result;
	}

	/**
	 * @Desc : 접수 관리자 메모 삭제
	 * @Method Name : updateSeExactCalculationMemo
	 * @Date : 2019. 7. 16.
	 * @Author : dtimer2@3top.co.kr
	 * @param updateSeExactCalculationMemo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updateSeExactCalculationMemo(SeExactCalculationMemo seExactCalculationMemo)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		seExactCalculationMemo.setDelYn(Const.BOOLEAN_TRUE);
		seExactCalculationMemo.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
		int resultCnt = seExactCalculationMemoDao.updateSeExactCalculationMemo(seExactCalculationMemo);

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
	 * @Desc :재정산 프로스져 콜
	 * @Method Name : getPropertySettleMent
	 * @Date : 2019. 7. 24.
	 * @Author : lee
	 * @param seExactCalculation
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getPropertySettleMent(SeExactCalculation seExactCalculation) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> param = new HashMap<>();
		param.put("excclcSeq", seExactCalculation.getExcclcSeq());
		param.put("excclcYm", seExactCalculation.getExcclcYm());
		param.put("vndrNo", seExactCalculation.getVndrNo());

		try {
			// 프로시저 호출
			seExactCalculationDao.callProcedurePropertySettleMent(param);
			log.debug("### procedure output: {}", (String) param.get("output"));
			resultMap.put("procResult", (String) param.get("output"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return resultMap;
	}

	public String getSeExactCal(SeExactCalculation seExactCalculation) throws Exception {
		// 정산 순번
		String excclcSeq = "";

		excclcSeq = seExactCalculationDao.selectSeExactCal(seExactCalculation);

		if (UtilsText.isEmpty(excclcSeq)) {
			excclcSeq = "0";
		}

		return excclcSeq;
	}
}
