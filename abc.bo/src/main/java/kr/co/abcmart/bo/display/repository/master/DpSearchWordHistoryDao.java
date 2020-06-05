package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpSearchWord;
import kr.co.abcmart.bo.display.model.master.DpSearchWordHistory;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpSearchWordHistoryDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpSearchWordHistoryDao extends BaseDpSearchWordHistoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpSearchWordHistoryDao 클래스에 구현 되어있습니다.
	 * BaseDpSearchWordHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public DpSearchWordHistory selectByPrimaryKey(DpSearchWordHistory dpSearchWordHistory) throws Exception;

	/**
	 * @Desc : 검색어 이력 목록 카운트 조회
	 * @Method Name : selectDpSearchWordHistoryListCount
	 * @Date : 2019. 4. 18.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public int selectDpSearchWordHistoryListCount(Pageable<DpSearchWordHistory, DpSearchWordHistory> pageable)
			throws Exception;

	/**
	 * @Desc : 검색어 이력 목록 조회
	 * @Method Name : selectDpSearchWordHistoryList
	 * @Date : 2019. 4. 18.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public List<DpSearchWordHistory> selectDpSearchWordHistoryList(
			Pageable<DpSearchWordHistory, DpSearchWordHistory> pageable) throws Exception;

	/**
	 * @Desc : 검색어 이력 추가
	 * @Method Name : insertDpSearchWordHistory
	 * @Date : 2019. 4. 17.
	 * @Author : 이가영
	 * @param dpSearchWord
	 */
	public void insertDpSearchWordHistory(DpSearchWord dpSearchWord) throws Exception;

}
