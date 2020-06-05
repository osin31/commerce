package kr.co.abcmart.bo.event.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.event.model.master.EvEventAttendanceCheckBenefit;
import kr.co.abcmart.bo.event.repository.master.base.BaseEvEventAttendanceCheckBenefitDao;

@Mapper
public interface EvEventAttendanceCheckBenefitDao extends BaseEvEventAttendanceCheckBenefitDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseEvEventAttendanceCheckBenefitDao 클래스에 구현
	 * 되어있습니다. BaseEvEventAttendanceCheckBenefitDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는
	 * 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public EvEventAttendanceCheckBenefit selectByPrimaryKey(EvEventAttendanceCheckBenefit evEventAttendanceCheckBenefit)
			throws Exception;

	/**
	 * 이벤트 출석 혜택 조회
	 * 
	 * @Desc : 이벤트 쿠폰 조회
	 * @Method Name : selectEvEventAttendanceCheckBenefitListByEventNo
	 * @Date : 2019. 4. 03
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventAttendanceCheckBenefit> selectEvEventAttendanceCheckBenefitListByEventNo(String eventNo)
			throws Exception;

	/**
	 * 이벤트 출석 혜택 삭제
	 * 
	 * @Desc : 이벤트 출석 혜택 삭제
	 * @Method Name : deleteByEventNo
	 * @Date : 2019. 4. 5
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public void deleteByEventNo(String eventNo) throws Exception;

	/**
	 * 이벤트 출석체크 헤택 등록
	 * 
	 * @Desc : 이벤트 출석체크 헤택 등록
	 * @Method Name : insertEvEventAttendanceCheckBenefit
	 * @Date : 2019. 4. 26
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public void insertEvEventAttendanceCheckBenefit(EvEventAttendanceCheckBenefit evEventAttendanceCheckBenefit)
			throws Exception;

}
