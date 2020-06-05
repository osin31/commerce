package kr.co.abcmart.bo.system.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.system.model.master.SyAdminUseHistory;
import kr.co.abcmart.bo.system.repository.master.base.BaseSyAdminUseHistoryDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface SyAdminUseHistoryDao extends BaseSyAdminUseHistoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSyAdminUseHistoryDao 클래스에 구현 되어있습니다.
	 * BaseSyAdminUseHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 */

	public SyAdminUseHistory selectByPrimaryKey(SyAdminUseHistory SyAdminUseHistory) throws Exception;

	/***
	 * 로그인 이력 목록 카운터 조회
	 *
	 * @param syAdminUseHistory
	 * @return int
	 */
	public Integer selectAdminUseHistoryListCount(Pageable<SyAdminUseHistory, SyAdminUseHistory> pageable)
			throws Exception;

	/***
	 * 로그인 이력 목록 조회
	 *
	 * @param syAdminUseHistory
	 * @return int
	 */
	public List<SyAdminUseHistory> selectAdminUseHistoryList(Pageable<SyAdminUseHistory, SyAdminUseHistory> pageable)
			throws Exception;

	/**
	 * 관리자사용이력 저장
	 *
	 * @param historyParam
	 * @throws Exception
	 */
	public void insertAdminUseHistory(SyAdminUseHistory historyParam) throws Exception;

	void deleteAdminUseHistory(SyAdminUseHistory syAdminUseHistory) throws Exception;

}
