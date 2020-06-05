package kr.co.abcmart.bo.event.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.event.model.master.EvEventResultBenefit;
import kr.co.abcmart.bo.event.repository.master.base.BaseEvEventResultBenefitDao;

@Mapper
public interface EvEventResultBenefitDao extends BaseEvEventResultBenefitDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseEvEventResultBenefitDao 클래스에 구현 되어있습니다.
	 * BaseEvEventResultBenefitDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public EvEventResultBenefit selectByPrimaryKey(EvEventResultBenefit evEventResultBenefit) throws Exception;

	/**
	 * 이벤트 당첨자 헤택 회원 조회
	 * 
	 * @Desc : 이벤트 당첨자 헤택 회원 조회
	 * @Method Name : selectEvEventResultBenefitListByEventNo
	 * @Date : 2019. 4. 11
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventResultBenefit> selectEvEventResultBenefitListByEventNo(String eventNo) throws Exception;

	/**
	 * 당첨자 관리 혜택 삭제
	 * 
	 * @Desc : 당첨자 관리 혜택 삭제
	 * @Method Name : deleteByEventNo
	 * @Date : 2019. 4. 29
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public void deleteByEventNo(String eventNo) throws Exception;

}
