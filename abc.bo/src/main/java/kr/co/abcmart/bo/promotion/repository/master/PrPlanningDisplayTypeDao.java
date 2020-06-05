package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplayType;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrPlanningDisplayTypeDao;

@Mapper
public interface PrPlanningDisplayTypeDao extends BasePrPlanningDisplayTypeDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrPlanningDisplayTypeDao 클래스에 구현 되어있습니다.
	 * BasePrPlanningDisplayTypeDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public PrPlanningDisplayType selectByPrimaryKey(PrPlanningDisplayType prPlanningDisplayType) throws Exception;

	/**
	 * @Desc : 기획전 유형 리스트 조회
	 * @Method Name : selectPrPlanningDisplayTypeListByPlndpNo
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param plndpNo
	 * @return
	 * @throws Exception
	 */
	public List<PrPlanningDisplayType> selectPrPlanningDisplayTypeListByPlndpNo(String plndpNo) throws Exception;

	/**
	 * @Desc : 기획전 유형 등록
	 * @Method Name : insertPrPlanningDisplayType
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param prPlanningDisplayType
	 */
	public void insertPrPlanningDisplayType(PrPlanningDisplayType prPlanningDisplayType) throws Exception;

	/**
	 * @Desc : 기획전 유형 삭제
	 * @Method Name : deletePrPlanningDisplayTypeListByPlndpNo
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param plndpNo
	 * @throws Exception
	 */
	public void deletePrPlanningDisplayTypeListByPlndpNo(String plndpNo) throws Exception;

}
