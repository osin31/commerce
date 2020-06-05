package kr.co.abcmart.bo.system.repository.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.system.model.master.SyMenu;
import kr.co.abcmart.bo.system.model.master.SyMenuAccessHistory;
import kr.co.abcmart.bo.system.repository.master.base.BaseSyMenuAccessHistoryDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface SyMenuAccessHistoryDao extends BaseSyMenuAccessHistoryDao {

     /**
     * 기본 insert, update, delete 메소드는 BaseSyMenuAccessHistoryDao 클래스에 구현 되어있습니다.
     * BaseSyMenuAccessHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     *
     */

	public SyMenuAccessHistory selectByPrimaryKey(SyMenuAccessHistory syMenuAccessHistory) throws Exception;

	public int selectUrlAccessHistoryListCount(Pageable<SyMenuAccessHistory, SyMenuAccessHistory> pageable) throws Exception;

	public List<SyMenuAccessHistory> selectUrlAccessHistoryList(Pageable<SyMenuAccessHistory, SyMenuAccessHistory> pageable) throws Exception;

	public int selectMenuAccessHistoryListCount(Pageable<SyMenuAccessHistory, SyMenuAccessHistory> pageable) throws Exception;

	public List<SyMenuAccessHistory> selectMenuAccessHistoryList(Pageable<SyMenuAccessHistory, SyMenuAccessHistory> pageable) throws Exception;

	public int selectLeafMenuCount(SyMenu syMenu) throws Exception;

	public List<SyMenu> selectMenu(SyMenu syMenu) throws Exception;

	public List<SyMenu> selectMenuDataList(SyMenuAccessHistory syMenuAccessHistory) throws Exception;

	public int selectMenuAccessHistoryDetailCount(Pageable<SyMenuAccessHistory, SyMenuAccessHistory> pageable) throws Exception;

	public List<SyMenuAccessHistory> selectMenuAccessHistoryDetailPop(Pageable<SyMenuAccessHistory, SyMenuAccessHistory> pageable) throws Exception;

	public int selectAdminAccessHistoryDetailCount(Pageable<SyMenuAccessHistory, SyMenuAccessHistory> pageable) throws Exception;

	public List<Map<String, Object>> selectAdminAccessHistoryList(SyMenuAccessHistory syMenuAccessHistory) throws Exception;

	public List<SyMenuAccessHistory> selectAdminAccessHistoryDetailPop(Pageable<SyMenuAccessHistory, SyMenuAccessHistory> pageable) throws Exception;

}
