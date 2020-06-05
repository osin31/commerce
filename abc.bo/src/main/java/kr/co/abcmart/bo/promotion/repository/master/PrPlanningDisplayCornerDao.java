package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplayCorner;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrPlanningDisplayCornerDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface PrPlanningDisplayCornerDao extends BasePrPlanningDisplayCornerDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrPlanningDisplayCornerDao 클래스에 구현 되어있습니다.
	 * BasePrPlanningDisplayCornerDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public PrPlanningDisplayCorner selectByPrimaryKey(PrPlanningDisplayCorner prPlanningDisplayCorner) throws Exception;

	/**
	 * @Desc : 기획전 코너 카운트 조회
	 * @Method Name : selectPrPlanningDisplayCornerCount
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public int selectPrPlanningDisplayCornerCount(Pageable<PrPlanningDisplayCorner, PrPlanningDisplayCorner> pageable)
			throws Exception;

	/**
	 * @Desc : 기획전 코너 리스트 조회
	 * @Method Name : selectPrPlanningDisplayCornerList
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public List<PrPlanningDisplayCorner> selectPrPlanningDisplayCornerList(
			Pageable<PrPlanningDisplayCorner, PrPlanningDisplayCorner> pageable) throws Exception;

	/**
	 * @Desc : 기획전 코너 등록
	 * @Method Name : insertPrPlanningDisplayCorner
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param prPlanningDisplayCorner
	 */
	public void insertPrPlanningDisplayCorner(PrPlanningDisplayCorner prPlanningDisplayCorner) throws Exception;

	/**
	 * @Desc : 기획전 코너 수정
	 * @Method Name : updatePrPlanningDisplayCorner
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param prPlanningDisplayCorner
	 */
	public void updatePrPlanningDisplayCorner(PrPlanningDisplayCorner prPlanningDisplayCorner) throws Exception;

	/**
	 * @Desc : 기획전 코너 삭제
	 * @Method Name : deletePrPlanningDisplayCorner
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param prPlanningDisplayCorner
	 */
	public void deletePrPlanningDisplayCorner(PrPlanningDisplayCorner prPlanningDisplayCorner) throws Exception;

	/**
	 * @Desc : 기획전 코너 삭제 By plndpNo
	 * @Method Name : deletePrPlanningDisplayCornerByPlndpNo
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param plndpNo
	 */
	public void deletePrPlanningDisplayCornerByPlndpNo(String plndpNo) throws Exception;

}
