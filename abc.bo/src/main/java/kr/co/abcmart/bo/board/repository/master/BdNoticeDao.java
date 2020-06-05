package kr.co.abcmart.bo.board.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.board.model.master.BdNotice;
import kr.co.abcmart.bo.board.repository.master.base.BaseBdNoticeDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface BdNoticeDao extends BaseBdNoticeDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseBdNoticeDao 클래스에 구현 되어있습니다.
	 * BaseBdNoticeDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public BdNotice selectByPrimaryKey(BdNotice bdNotice) throws Exception;

	/**
	 * @Desc : 공지사항 조회결과 개수
	 * @Method Name : selectBdNoticeCount
	 * @Date : 2019. 2. 27.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectBdNoticeCount(Pageable<BdNotice, BdNotice> pageable) throws Exception;

	/**
	 * @Desc : 공지사항 그리드 호출
	 * @Method Name : selectBdNoticeList
	 * @Date : 2019. 2. 28.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<BdNotice> selectBdNoticeList(Pageable<BdNotice, BdNotice> pageable) throws Exception;

	/**
	 * @Desc : 공지사항 상세보기
	 * @Method Name : selectNoticeDetail
	 * @Date : 2019. 2. 28.
	 * @Author : 신인철
	 * @param bdNotice
	 * @return
	 * @throws Exception
	 */
	public BdNotice selectNoticeDetail(BdNotice bdNotice) throws Exception;

	/**
	 * @Desc : 공지사항 수정
	 * @Method Name : updateBdNotice
	 * @Date : 2019. 2. 28.
	 * @Author : 신인철
	 * @param bdNotice
	 * @return
	 * @throws Exception
	 */
	public int updateBdNotice(BdNotice bdNotice) throws Exception;

	/**
	 * @Desc : 공지사항 등록
	 * @Method Name : insertNotice
	 * @Date : 2019. 3. 1.
	 * @Author : 신인철
	 * @param bdNotice
	 * @throws Exception
	 */
	public void insertNotice(BdNotice bdNotice) throws Exception;

	/**
	 * @Desc : 상단공지 통합몰 개수 확인
	 * @Method Name : selectTopNoticeCount
	 * @Date : 2019. 3. 11.
	 * @Author : 신인철
	 * @return
	 * @throws Exception
	 */
	public int selectTopNoticeARTCount() throws Exception;

	/**
	 * @Desc : 상단공지 OTS 개수 확인
	 * @Method Name : selectTopNoticeOTSCount
	 * @Date : 2019. 3. 26.
	 * @Author : 신인철
	 * @return
	 * @throws Exception
	 */
	public int selectTopNoticeOTSCount() throws Exception;

}
