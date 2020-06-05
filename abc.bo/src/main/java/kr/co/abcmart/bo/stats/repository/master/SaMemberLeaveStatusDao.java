package kr.co.abcmart.bo.stats.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.stats.model.master.SaMemberLeaveStatus;
import kr.co.abcmart.bo.stats.repository.master.base.BaseSaMemberLeaveStatusDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface SaMemberLeaveStatusDao extends BaseSaMemberLeaveStatusDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSaMemberLeaveStatusDao 클래스에 구현 되어있습니다.
	 * BaseSaMemberLeaveStatusDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public SaMemberLeaveStatus selectByPrimaryKey(SaMemberLeaveStatus saMemberLeaveStatus) throws Exception;

	/**
	 * @Desc : 그리드 토탈 카운트
	 * @Method Name : selectLeaveGridCount
	 * @Date : 2019. 6. 21.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectLeaveGridCount(Pageable<SaMemberLeaveStatus, SaMemberLeaveStatus> pageable) throws Exception;

	/**
	 * @Desc : 그리드 조회
	 * @Method Name : selectLeaveGrid
	 * @Date : 2019. 6. 21.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SaMemberLeaveStatus> selectLeaveGrid(Pageable<SaMemberLeaveStatus, SaMemberLeaveStatus> pageable)
			throws Exception;

	/**
	 * @Desc : 엑셀 다운로드할 리스트 조회
	 * @Method Name : selectExcelList
	 * @Date : 2019. 6. 21.
	 * @Author : 신인철
	 * @param saMemberLeaveStatus
	 * @return
	 * @throws Exception
	 */
	public List<SaMemberLeaveStatus> selectExcelList(SaMemberLeaveStatus saMemberLeaveStatus) throws Exception;

}
