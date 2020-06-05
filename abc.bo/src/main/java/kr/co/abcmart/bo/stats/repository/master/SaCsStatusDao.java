package kr.co.abcmart.bo.stats.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.stats.model.master.CurrentSaleStats;
import kr.co.abcmart.bo.stats.model.master.SaCsStatus;
import kr.co.abcmart.bo.stats.vo.CsStatsSearchVO;
import kr.co.abcmart.bo.stats.vo.OrderStatsSearchVO;
import kr.co.abcmart.common.paging.Pageable;

/**
 * @Desc :
 * @FileName : SaCsStatusDao.java
 * @Project : abc.bo
 * @Date : 2019. 7. 26.
 * @Author : bje0507
 */
@Mapper
public interface SaCsStatusDao {

	/**
	 * @Desc : 상품 후기 통계 count
	 * @Method Name : reviewCount
	 * @Date : 2019. 7. 23.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int reviewCount(Pageable<CsStatsSearchVO, SaCsStatus> pageable) throws Exception;

	/**
	 * @Desc : 상품 문의 통계 count
	 * @Method Name : inquiryCount
	 * @Date : 2019. 7. 24.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int inquiryCount(Pageable<CsStatsSearchVO, SaCsStatus> pageable) throws Exception;

	/**
	 * @Desc : 상품 후기 통계
	 * @Method Name : reviewStats
	 * @Date : 2019. 7. 23.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SaCsStatus> reviewStats(Pageable<CsStatsSearchVO, SaCsStatus> pageable) throws Exception;

	/**
	 * @Desc : 상품 문의 통계
	 * @Method Name : inquiryStats
	 * @Date : 2019. 7. 24.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SaCsStatus> inquiryStats(Pageable<CsStatsSearchVO, SaCsStatus> pageable) throws Exception;

	/**
	 * @Desc : 클레임 일자별 통계 count
	 * @Method Name : selectClmCntGroupbyModDtmCount
	 * @Date : 2019. 10. 21.
	 * @Author : 이강수
	 * @param Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable
	 * @return int
	 * @throws Exception
	 */
	public int selectClmCntGroupbyModDtmCount(Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable) throws Exception;

	/**
	 * @Desc : 클레임 일자별 통계 paging
	 * @Method Name : selectClmCntGroupbyModDtmPaging
	 * @Date : 2019. 10. 21.
	 * @Author : 이강수
	 * @param Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable
	 * @return List<CurrentSaleStats>
	 * @throws Exception
	 */
	public List<CurrentSaleStats> selectClmCntGroupbyModDtmPaging(
			Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable) throws Exception;

}
