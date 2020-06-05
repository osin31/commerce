package kr.co.abcmart.bo.system.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.system.model.master.SyAuthorityChangeHistory;
import kr.co.abcmart.bo.system.repository.master.base.BaseSyAuthorityChangeHistoryDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface SyAuthorityChangeHistoryDao extends BaseSyAuthorityChangeHistoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSyAuthorityChangeHistoryDao 클래스에 구현
	 * 되어있습니다. BaseSyAuthorityChangeHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당
	 * 소스에서 작업 하시면 됩니다.
	 *
	 */

	public SyAuthorityChangeHistory selectByPrimaryKey(SyAuthorityChangeHistory SyAuthorityChangeHistory)
			throws Exception;

	public int selectAuthorityGroupHistoryListCount(
			Pageable<SyAuthorityChangeHistory, SyAuthorityChangeHistory> pageable) throws Exception;

	public List<SyAuthorityChangeHistory> selectAuthorityGroupHistoryList(
			Pageable<SyAuthorityChangeHistory, SyAuthorityChangeHistory> pageable) throws Exception;

	public void insertAuthorityGroupHistoryNoTrx(SyAuthorityChangeHistory historyParams) throws Exception;

	public void deleteAuthorityChangeHistory(SyAuthorityChangeHistory syAuthorityChangeHistory);

}
