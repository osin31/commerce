package kr.co.abcmart.bo.event.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.event.model.master.EvEventResultBenefitMember;
import kr.co.abcmart.bo.event.repository.master.base.BaseEvEventResultBenefitMemberDao;

@Mapper
public interface EvEventResultBenefitMemberDao extends BaseEvEventResultBenefitMemberDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseEvEventResultBenefitMemberDao 클래스에 구현
	 * 되어있습니다. BaseEvEventResultBenefitMemberDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당
	 * 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public EvEventResultBenefitMember selectByPrimaryKey(EvEventResultBenefitMember evEventResultBenefitMember)
			throws Exception;

	/**
	 * 당첨자 관리 멤버 삭제
	 * 
	 * @Desc : 당첨자 관리 멤버 삭제
	 * @Method Name : deleteByEventNo
	 * @Date : 2019. 4. 29
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public void deleteByEventNo(String eventNo) throws Exception;

}
