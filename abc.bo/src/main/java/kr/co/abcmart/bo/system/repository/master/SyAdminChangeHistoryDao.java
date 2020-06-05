package kr.co.abcmart.bo.system.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.system.model.master.SyAdminChangeHistory;
import kr.co.abcmart.bo.system.repository.master.base.BaseSyAdminChangeHistoryDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface SyAdminChangeHistoryDao extends BaseSyAdminChangeHistoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSyAdminChangeHistoryDao 클래스에 구현 되어있습니다.
	 * BaseSyAdminChangeHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public SyAdminChangeHistory selectByPrimaryKey(SyAdminChangeHistory SyAdminChangeHistory) throws Exception;

	/***
	 * 관리자 변경 이력 목록 카운터 조회
	 * 
	 * @param syAdminUseHistory
	 * @return int
	 */
	public int selectAdminChangeHistoryListCount(Pageable<SyAdminChangeHistory, SyAdminChangeHistory> pageable)
			throws Exception;

	/***
	 * 관리자 변경 이력 목록 조회
	 * 
	 * @param syAdminUseHistory
	 * @return int
	 */
	public List<SyAdminChangeHistory> selectAdminChangeHistoryList(
			Pageable<SyAdminChangeHistory, SyAdminChangeHistory> pageable) throws Exception;

	/***
	 * 관리자 수정 이력 저장
	 * 
	 * @param SyAdminChangeHistory
	 * @return
	 */
	public void insertAdminChangeHistoryNoTrx(SyAdminChangeHistory historyParams) throws Exception;

}
