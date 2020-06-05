package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.DpSizeChart;
import kr.co.abcmart.bo.product.repository.master.base.BaseDpSizeChartDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpSizeChartDao extends BaseDpSizeChartDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpSizeChartDao 클래스에 구현 되어있습니다.
	 * BaseDpSizeChartDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public DpSizeChart selectByPrimaryKey(DpSizeChart dpSizeChart) throws Exception;

	/**
	 * 사이즈조견표 리스트 조회
	 * 
	 * @Desc : 사이즈조견표 리스트 조회
	 * @Method Name : selectDpSizeChartCount
	 * @Date : 2019. 2. 12.
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public List<DpSizeChart> selectDpSizeChartList(Pageable<DpSizeChart, DpSizeChart> pageable) throws Exception;

	/**
	 * 사이즈조견표 리스트 조회
	 * 
	 * @Desc : 사이즈조견표 상세 조회
	 * @Method Name : selectDpSizeChart
	 * @Date : 2019. 4. 15.
	 * @Author : easyhun
	 * @param DpSizeChart
	 * @return
	 */
	public DpSizeChart selectDpSizeChart(DpSizeChart dpSizeChart) throws Exception;

	/**
	 * 사이즈조견표 리스트 카운트 조회
	 * 
	 * @Desc :
	 * @Method Name : selectDpSizeChartList
	 * @Date : 2019. 2. 12.
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public int selectDpSizeChartCount(Pageable<DpSizeChart, DpSizeChart> pageable) throws Exception;

	/**
	 * 사이즈조견표 저장
	 * 
	 * @Desc : 사이즈조견표 저장
	 * @Method Name : insertDpSizeChart
	 * @Date : 2019. 2. 12.
	 * @Author : easyhun
	 * @param dpSizeChart
	 * @return
	 * @throws Exception
	 */
	// public int insertDpSizeChart(DpSizeChart dpSizeChart) throws Exception;

	/**
	 * 사이즈조견표 수정
	 * 
	 * @Desc : 사이즈조견표 수정
	 * @Method Name : updateDpSizeChart
	 * @Date : 2019. 2. 12.
	 * @Author : easyhun
	 * @param dpSizeChart
	 * @return
	 * @throws Exception
	 */
	public int updateDpSizeChart(DpSizeChart dpSizeChart) throws Exception;

	/**
	 * @Desc : 상품에 관련된 사이즈조견표 조회
	 * @Method Name : selectDpSizeChartForProduct
	 * @Date : 2019. 4. 22.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public List<DpSizeChart> selectDpSizeChartForProduct(DpSizeChart criteria) throws Exception;

}
