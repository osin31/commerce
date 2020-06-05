package kr.co.abcmart.bo.stats.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.stats.model.master.SaRestockStatus;
import kr.co.abcmart.bo.stats.vo.RestockStatsSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface SaRestockStatusDao {

	/**
	 * @Desc : 재입고 알림서비스 상품별 통계 count
	 * @Method Name : productRestockCount
	 * @Date : 2019. 7. 30.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int productRestockCount(Pageable<RestockStatsSearchVO, SaRestockStatus> pageable) throws Exception;

	/**
	 * @Desc : 재입고 알림서비스 상품별 통계
	 * @Method Name : productRestockStats
	 * @Date : 2019. 7. 30.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SaRestockStatus> productRestockStats(Pageable<RestockStatsSearchVO, SaRestockStatus> pageable)
			throws Exception;

	/**
	 * @Desc : 재입고 알림서비스 기간별 통계 count
	 * @Method Name : dateRestockCount
	 * @Date : 2019. 7. 30.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int dateRestockCount(Pageable<RestockStatsSearchVO, SaRestockStatus> pageable) throws Exception;

	/**
	 * @Desc : 재입고 알림서비스 기간별 통계
	 * @Method Name : dateRestockStats
	 * @Date : 2019. 7. 30.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SaRestockStatus> dateRestockStats(Pageable<RestockStatsSearchVO, SaRestockStatus> pageable)
			throws Exception;

}
