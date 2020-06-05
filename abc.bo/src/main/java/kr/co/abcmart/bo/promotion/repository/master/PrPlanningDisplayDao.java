package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplay;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrPlanningDisplayDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface PrPlanningDisplayDao extends BasePrPlanningDisplayDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrPlanningDisplayDao 클래스에 구현 되어있습니다.
	 * BasePrPlanningDisplayDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public PrPlanningDisplay selectByPrimaryKey(PrPlanningDisplay prPlanningDisplay) throws Exception;

	/**
	 * @Desc : 기획전 리스트 조회
	 * @Method Name : selectPrPlanningDisplayList
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<PrPlanningDisplay> selectPrPlanningDisplayList(Pageable<PrPlanningDisplay, PrPlanningDisplay> pageable)
			throws Exception;

	/**
	 * @Desc : 기획전 카운트 조회
	 * @Method Name : selectPrPlanningDisplayCount
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectPrPlanningDisplayCount(Pageable<PrPlanningDisplay, PrPlanningDisplay> pageable) throws Exception;

	/**
	 * @Desc : 기획전 상세 조회
	 * @Method Name : selectPrPlanningDisplayDetail
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param prPlanningDisplay
	 * @return
	 * @throws Exception
	 */
	public PrPlanningDisplay selectPrPlanningDisplayDetail(PrPlanningDisplay prPlanningDisplay) throws Exception;

	/**
	 * @Desc : 기획전 등록
	 * @Method Name : insertPrPlanningDisplay
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param prPlanningDisplay
	 * @return
	 * @throws Exception
	 */
	public int insertPrPlanningDisplay(PrPlanningDisplay prPlanningDisplay) throws Exception;

	/**
	 * @Desc : 기획전 수정
	 * @Method Name : updatePrPlanningDisplay
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param prPlanningDisplay
	 * @throws Exception
	 */
	public void updatePrPlanningDisplay(PrPlanningDisplay prPlanningDisplay) throws Exception;

	/**
	 * @Desc : 기획전 상태 수정
	 * @Method Name : updatePrPlanningDisplayStatus
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param prPlanningDisplay
	 * @throws Exception
	 */
	public void updatePrPlanningDisplayStatus(PrPlanningDisplay prPlanningDisplay) throws Exception;

	/**
	 * @Desc : 대시보드 기획전 카운트 조회
	 * @Method Name : selectDashboardDisplanningCount
	 * @Date : 2019. 10. 21.
	 * @Author : 김영진
	 * @param prPlanning
	 * @return
	 */
	public List<Map<String, Object>> selectDashboardDisplanningCount(PrPlanningDisplay prPlanning) throws Exception;

}
