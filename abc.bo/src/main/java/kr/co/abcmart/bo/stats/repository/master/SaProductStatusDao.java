package kr.co.abcmart.bo.stats.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.stats.model.master.SaProductStats;
import kr.co.abcmart.bo.stats.vo.ProductStatsSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface SaProductStatusDao {

	/**
	 * @Desc : 쿠폰 통계 리스트 카운트
	 * @Method Name : productStatsListCount
	 * @Date : 2019. 7. 26.
	 * @Author : 백인천
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int productStatsListCount(Pageable<ProductStatsSearchVO, SaProductStats> pageable) throws Exception;

	/**
	 * @Desc : 쿠폰 통계 리스트
	 * @Method Name : productStatsList
	 * @Date : 2019. 7. 26.
	 * @Author : 백인천
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SaProductStats> productStatsList(Pageable<ProductStatsSearchVO, SaProductStats> pageable)
			throws Exception;

	/**
	 * @Desc : 쿠폰 통계 리스트 엑셀다운로드
	 * @Method Name : selectProductStatsListForExcel
	 * @Date : 2019. 7. 26.
	 * @Author : 백인천
	 * @param productStatsSearchVO
	 * @throws Exception
	 */
	public List<SaProductStats> selectProductStatsListForExcel(ProductStatsSearchVO productStatsSearchVO)
			throws Exception;

	/**
	 * @Desc : 공유하기 현황 리스트 카운트
	 * @Method Name : shareStatsListCount
	 * @Date : 2019. 8. 1.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int shareStatsListCount(Pageable<ProductStatsSearchVO, SaProductStats> pageable) throws Exception;

	/**
	 * @Desc : 공유하기 현황 리스트
	 * @Method Name : shareStatsList
	 * @Date : 2019. 8. 1.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SaProductStats> shareStatsList(Pageable<ProductStatsSearchVO, SaProductStats> pageable)
			throws Exception;

	/**
	 * @Desc : 입점사 수수료 변경이력 조회 카운트
	 * @Method Name : cmsnStatsListCount
	 * @Date : 2019. 8. 5.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int cmsnStatsListCount(Pageable<ProductStatsSearchVO, SaProductStats> pageable) throws Exception;

	/**
	 * @Desc : 입점사 수수료 변경이력 조회
	 * @Method Name : cmsnStatsList
	 * @Date : 2019. 8. 5.
	 * @Author : bje0507
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SaProductStats> cmsnStatsList(Pageable<ProductStatsSearchVO, SaProductStats> pageable) throws Exception;

}
