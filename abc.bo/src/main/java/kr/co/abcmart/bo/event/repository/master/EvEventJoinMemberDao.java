package kr.co.abcmart.bo.event.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.event.model.master.EvEventJoinMember;
import kr.co.abcmart.bo.event.repository.master.base.BaseEvEventJoinMemberDao;
import kr.co.abcmart.bo.event.vo.EvEventJoinResultVO;
import kr.co.abcmart.bo.event.vo.EvEventSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface EvEventJoinMemberDao extends BaseEvEventJoinMemberDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseEvEventJoinMemberDao 클래스에 구현 되어있습니다.
	 * BaseEvEventJoinMemberDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public EvEventJoinMember selectByPrimaryKey(EvEventJoinMember evEventJoinMember) throws Exception;

	/**
	 * 이벤트 참여 조회
	 * 
	 * @Desc : 이벤트 참여 조회
	 * @Method Name : selectEvEventJoinMemberCount
	 * @Date : 2019. 4. 09
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public int selectEvEventJoinMemberCount(Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable) throws Exception;

	/**
	 * 이벤트 참여 조회
	 * 
	 * @Desc : 이벤트 참여 조회
	 * @Method Name : selectEvEventJoinMemberList
	 * @Date : 2019. 4. 09
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventJoinResultVO> selectEvEventJoinMemberList(
			Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable) throws Exception;

	/**
	 * 이벤트 참여 seq 조회
	 * 
	 * @Desc : 이벤트 참여 seq 조회
	 * @Method Name : selectEventJoinMemberSeq
	 * @Date : 2019. 4. 29
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public Long selectEventJoinMemberSeq(String memberNo, String eventNo) throws Exception;

	/**
	 * 이벤트 참여자 memberNo 조회
	 * 
	 * @Desc : 응모자 memberNo 조회
	 * @Method Name : selectMemberNoByLoginId
	 * @Date : 2019. 6. 10
	 * @Author : easyhun
	 * @param loginId, eventNo
	 * @return
	 */
	public String selectMemberNoByLoginId(String loginId, String eventNo) throws Exception;

	/**
	 * @Desc : 이벤트 회원 참여 수
	 * @Method Name : selectEventJoinMemberCount
	 * @Date : 2019. 6. 25.
	 * @Author : easyhun
	 * @param evEventRouletteJoinMember
	 * @return
	 * @throws Exception
	 */
	public int selectEventJoinMemberCount(EvEventJoinMember evEventJoinMember) throws Exception;

	/**
	 * 이벤트 참여 조회
	 * 
	 * @Desc : 이벤트 참여 조회
	 * @Method Name : selectEvEventJoinMemberCountByEventNo
	 * @Date : 2019. 7. 26
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public int selectEvEventJoinMemberCountByEventNo(String eventNo) throws Exception;

	/**
	 * 이벤트 참여 조회
	 * 
	 * @Desc : 이벤트 참여 조회
	 * @Method Name : selectEvEventResultMemberCount
	 * @Date : 2019. 4. 09
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public int selectEvEventResultMemberCount(Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable) throws Exception;

	/**
	 * 이벤트 참여 조회
	 * 
	 * @Desc : 이벤트 참여 조회
	 * @Method Name : selectEvEventResultMemberList
	 * @Date : 2019. 4. 09
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventJoinResultVO> selectEvEventResultMemberList(
			Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable) throws Exception;

	/**
	 * 이벤트 참여 수정
	 * 
	 * @Desc : 이벤트 참여 수정
	 * @Method Name : updateEvEventJoinMember
	 * @Date : 2019. 8. 29
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public void updateEvEventJoinMember(EvEventJoinMember evEventJoinMember) throws Exception;

	/**
	 * 이벤트 참여 수정
	 * 
	 * @Desc : 이벤트 참여 수정(eventNo)
	 * @Method Name : updateWinYnByEventNo
	 * @Date : 2019. 8. 29
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public void updateWinYnByEventNo(EvEventJoinMember evEventJoinMember) throws Exception;

}
