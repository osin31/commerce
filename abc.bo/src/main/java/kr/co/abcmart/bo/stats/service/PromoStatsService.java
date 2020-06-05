package kr.co.abcmart.bo.stats.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.stats.model.master.SaCouponStats;
import kr.co.abcmart.bo.stats.model.master.SaPromotionPlanStatus;
import kr.co.abcmart.bo.stats.model.master.SaSalesOrder;
import kr.co.abcmart.bo.stats.repository.master.SaPromotionStatusDao;
import kr.co.abcmart.bo.stats.vo.CouponStatsSearchVO;
import kr.co.abcmart.bo.stats.vo.PromotionStatsSearchVO;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PromoStatsService {

	@Autowired
	private SaPromotionStatusDao saPromotionStatusDao;

	/**
	 * @Desc : 오늘날짜 데이터
	 * @Method Name : getToday
	 * @Date : 2019. 7. 5.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	public void getToday(Parameter<?> parameter) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		Date date = new Date();

		parameter.addAttribute("thisYear", formatter.format(date).substring(0, 4));
		parameter.addAttribute("thisMonth", formatter.format(date).substring(5, 7));
		parameter.addAttribute("thisDay", formatter.format(date).substring(8, 10));
	}

	/**
	 * @Desc : 기획전 현황 통계
	 * @Method Name : getPlanningList
	 * @Date : 2019. 7. 16.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SaPromotionPlanStatus> getPlanningList(Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable)
			throws Exception {

		int count = saPromotionStatusDao.planningStatsCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(saPromotionStatusDao.planningStats(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 기획전 상품 판매 현황 조회
	 * @Method Name : getPlanningProductList
	 * @Date : 2019. 7. 16.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SaPromotionPlanStatus> getPlanningProductList(
			Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable) throws Exception {

		int count = saPromotionStatusDao.planningProductStatsCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(saPromotionStatusDao.planningProductStats(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 쿠폰 현황 통계 리스트 조회
	 * @Method Name : getCouponStatsList
	 * @Date : 2019. 7. 17.
	 * @Author : 백인천
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SaCouponStats> getCouponStatsList(Pageable<CouponStatsSearchVO, SaCouponStats> pageable)
			throws Exception {

		int count = saPromotionStatusDao.couponStatsListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(saPromotionStatusDao.couponStatsList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 프로모션 현황 통계
	 * @Method Name : getPromotionList
	 * @Date : 2019. 7. 17.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SaPromotionPlanStatus> getPromotionList(
			Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable) throws Exception {

		int count = saPromotionStatusDao.promotionStatsCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(saPromotionStatusDao.promotionStats(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 프로모션 상품 판매 현황 조회
	 * @Method Name : getPlanningProductList
	 * @Date : 2019. 7. 16.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SaPromotionPlanStatus> getPromotionProductList(
			Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable) throws Exception {

		int count = saPromotionStatusDao.promotionProductStatsCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(saPromotionStatusDao.promotionProductStats(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 쿠폰 현황 통계 엑셀다운로드
	 * @Method Name : getCouponStatsListForExcel
	 * @Date : 2019. 7. 19.
	 * @Author : 백인천
	 * @param couponStatsSearchVO
	 * @throws Exception
	 */
	public List<SaCouponStats> getCouponStatsListForExcel(CouponStatsSearchVO couponStatsSearchVO) throws Exception {

		return saPromotionStatusDao.selectCouponStatsListForExcel(couponStatsSearchVO);
	}

	/**
	 * @Desc : 다족구매 족수별 통계 조회
	 * @Method Name : getMultiShoeList
	 * @Date : 2019. 7. 29.
	 * @Author : 이재렬
	 * @param pageable
	 * @throws Exception
	 */
	public Page<SaPromotionPlanStatus> getMultiShoeList(Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable)
			throws Exception {
		int count = saPromotionStatusDao.selectMultiShoeCount(pageable);

		if (count > 0) {
			pageable.setContent(saPromotionStatusDao.selectMultiShoeList(pageable));
		}

		return pageable.getPage();
	}

}
