package kr.co.abcmart.bo.member.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.member.model.master.MbMemberChangeHistory;
import kr.co.abcmart.bo.member.repository.master.base.BaseMbMemberChangeHistoryDao;
import kr.co.abcmart.bo.member.vo.MemberHistorySearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface MbMemberChangeHistoryDao extends BaseMbMemberChangeHistoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseMbMemberChangeHistoryDao 클래스에 구현 되어있습니다.
	 * BaseMbMemberChangeHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public MbMemberChangeHistory selectByPrimaryKey(MbMemberChangeHistory mbMemberChangeHistory) throws Exception;

	public Integer selectChangeHistoryListCount(Pageable<MemberHistorySearchVO, MbMemberChangeHistory> pageable)
			throws Exception;

	public List<MbMemberChangeHistory> selectChangeHistoryList(
			Pageable<MemberHistorySearchVO, MbMemberChangeHistory> pageable) throws Exception;
	
	public void insertMemberHistory(MbMemberChangeHistory mbMemberChangeHistory) throws Exception;

}
