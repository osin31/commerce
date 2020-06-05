package kr.co.abcmart.bo.stats.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.stats.model.master.SaCouponStats;
import kr.co.abcmart.bo.stats.model.master.SaPromotionPlanStatus;
import kr.co.abcmart.bo.stats.model.master.SaSalesOrder;
import kr.co.abcmart.bo.stats.vo.CouponStatsSearchVO;
import kr.co.abcmart.bo.stats.vo.PromotionStatsSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface SaPromotionStatusDao {

	/**
	 * @Desc : 기획전 현황 count
	 * @Method Name : planningStatsCount
	 * @Date : 2019. 7. 16.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int planningStatsCount(Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable) throws Exception;

	/**
	 * @Desc : 기획전 현황
	 * @Method Name : planningStats
	 * @Date : 2019. 7. 16.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SaPromotionPlanStatus> planningStats(Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable)
			throws Exception;

	/**
	 * @Desc : 기획전 상품 현황 popup count
	 * @Method Name : planningProductStatsCount
	 * @Date : 2019. 7. 16.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int planningProductStatsCount(Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable)
			throws Exception;

	/**
	 * @Desc : 기획전 현황 popup
	 * @Method Name : planningProductStats
	 * @Date : 2019. 7. 16.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SaPromotionPlanStatus> planningProductStats(
			Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable) throws Exception;

	/**
	 * @Desc : 프로모션 현황 count
	 * @Method Name : promotionStatsCount
	 * @Date : 2019. 7. 22.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int promotionStatsCount(Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable) throws Exception;

	/**
	 * @Desc : 프로모션 현황 조회
	 * @Method Name : promotionStats
	 * @Date : 2019. 7. 17.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SaPromotionPlanStatus> promotionStats(Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable)
			throws Exception;

	/**
	 * @Desc : 프로모션 상품 현황 popup count
	 * @Method Name : promotionProductCount
	 * @Date : 2019. 7. 16.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int promotionProductStatsCount(Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable)
			throws Exception;

	/**
	 * @Desc : 프로모션 상품 현황 popup
	 * @Method Name : promotionProductStats
	 * @Date : 2019. 7. 22.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SaPromotionPlanStatus> promotionProductStats(
			Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable) throws Exception;

	/**
	 * @Desc : 쿠폰 통계 리스트 카운트
	 * @Method Name : couponStatsListCount
	 * @Date : 2019. 7. 17.
	 * @Author : 백인천
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int couponStatsListCount(Pageable<CouponStatsSearchVO, SaCouponStats> pageable) throws Exception;

	/**
	 * @Desc : 쿠폰 통계 리스트
	 * @Method Name : couponStatsList
	 * @Date : 2019. 7. 17.
	 * @Author : 백인천
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SaCouponStats> couponStatsList(Pageable<CouponStatsSearchVO, SaCouponStats> pageable) throws Exception;

	/**
	 * @Desc : 쿠폰 통계 리스트 엑셀다운로드
	 * @Method Name : selectCouponStatsListForExcel
	 * @Date : 2019. 7. 19.
	 * @Author : 백인천
	 * @param couponStatsSearchVO
	 * @throws Exception
	 */
	public List<SaCouponStats> selectCouponStatsListForExcel(CouponStatsSearchVO couponStatsSearchVO) throws Exception;

	/**
	 * @Desc : 다족구매 족수별 통계 목록 카운트
	 * @Method Name : selectMultiShoeCount
	 * @Date : 2019. 7. 29.
	 * @Author : 이재렬
	 * @param pageable
	 * @throws Exception
	 */
	public int selectMultiShoeCount(Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable) throws Exception;

	/**
	 * @Desc : 다족구매 족수별 통계 목록 조회
	 * @Method Name : selectMultiShoeList
	 * @Date : 2019. 7. 29
	 * @Author : 이재렬
	 * @param pageable
	 * @throws Exception
	 */
	public List<SaPromotionPlanStatus> selectMultiShoeList(Pageable<PromotionStatsSearchVO, SaPromotionPlanStatus> pageable)
			throws Exception;


}
