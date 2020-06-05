package kr.co.abcmart.bo.event.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.event.model.master.EvEventRouletteBenefit;
import kr.co.abcmart.bo.event.repository.master.base.BaseEvEventRouletteBenefitDao;

@Mapper
public interface EvEventRouletteBenefitDao extends BaseEvEventRouletteBenefitDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseEvEventRouletteBenefitDao 클래스에 구현 되어있습니다.
	 * BaseEvEventRouletteBenefitDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public EvEventRouletteBenefit selectByPrimaryKey(EvEventRouletteBenefit evEventRouletteBenefit) throws Exception;

	/**
	 * 이벤트 룰렛 혜택 조회
	 * 
	 * @Desc : 이벤트 룰렛 혜택 조회
	 * @Method Name : selectEvEventRouletteBenefitListByEventNo
	 * @Date : 2019. 4. 03
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventRouletteBenefit> selectEvEventRouletteBenefitListByEventNo(String eventNo) throws Exception;

	/**
	 * 이벤트 룰렛 등록
	 * 
	 * @Desc : 이벤트 룰렛 등록
	 * @Method Name : insertEvEventRouletteBenefit
	 * @Date : 2019. 4. 26
	 * @Author : easyhun
	 * @param evEventRouletteBenefit
	 * @return
	 */
	public void insertEvEventRouletteBenefit(EvEventRouletteBenefit evEventRouletteBenefit) throws Exception;

	/**
	 * 이벤트 룰렛 등록
	 * 
	 * @Desc : 이벤트 룰렛 등록
	 * @Method Name : updateEvEventRouletteBenefit
	 * @Date : 2019. 4. 26
	 * @Author : easyhun
	 * @param evEventRouletteBenefit
	 * @return
	 */
	public void updateEvEventRouletteBenefit(EvEventRouletteBenefit evEventRouletteBenefit) throws Exception;

}
