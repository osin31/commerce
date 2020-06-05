package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplayApplyGrade;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrPlanningDisplayApplyGradeDao;

@Mapper
public interface PrPlanningDisplayApplyGradeDao extends BasePrPlanningDisplayApplyGradeDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrPlanningDisplayApplyGradeDao 클래스에 구현
	 * 되어있습니다. BasePrPlanningDisplayApplyGradeDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당
	 * 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public PrPlanningDisplayApplyGrade selectByPrimaryKey(PrPlanningDisplayApplyGrade prPlanningDisplayApplyGrade)
			throws Exception;

	/**
	 * @Desc : 기획전 적용 등급 리스트 조회
	 * @Method Name : selectPrPlanningDisplayApplyGradeListByPlndpNo
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param plndpNo
	 * @return
	 */
	public List<PrPlanningDisplayApplyGrade> selectPrPlanningDisplayApplyGradeListByPlndpNo(String plndpNo)
			throws Exception;

	/**
	 * @Desc : 기획전 적용 등급 등록
	 * @Method Name : insertPrPlanningDisplayApplyGrade
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param prPlanningDisplayApplyGrade
	 */
	public void insertPrPlanningDisplayApplyGrade(PrPlanningDisplayApplyGrade prPlanningDisplayApplyGrade)
			throws Exception;

	/**
	 * @Desc : 기획전 적용 등급 삭제
	 * @Method Name : deletePrPlanningDisplayApplyGradeByPlndpNo
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param plndpNo
	 */
	public void deletePrPlanningDisplayApplyGradeByPlndpNo(String plndpNo) throws Exception;

}
