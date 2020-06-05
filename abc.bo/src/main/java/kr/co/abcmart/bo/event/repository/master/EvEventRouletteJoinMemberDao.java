package kr.co.abcmart.bo.event.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.event.model.master.EvEventRouletteJoinMember;
import kr.co.abcmart.bo.event.repository.master.base.BaseEvEventRouletteJoinMemberDao;
import kr.co.abcmart.bo.event.vo.EvEventJoinResultVO;
import kr.co.abcmart.bo.event.vo.EvEventSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface EvEventRouletteJoinMemberDao extends BaseEvEventRouletteJoinMemberDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseEvEventRouletteJoinMemberDao 클래스에 구현
	 * 되어있습니다. BaseEvEventRouletteJoinMemberDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당
	 * 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public EvEventRouletteJoinMember selectByPrimaryKey(EvEventRouletteJoinMember evEventRouletteJoinMember)
			throws Exception;

	/**
	 * 이벤트 참여 조회
	 * 
	 * @Desc : 이벤트 참여 조회
	 * @Method Name : selectEvEventRouletteJoinMemberCount
	 * @Date : 2019. 4. 09
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public int selectEvEventRouletteJoinMemberCount(Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable)
			throws Exception;

	/**
	 * 이벤트 참여 조회
	 * 
	 * @Desc : 이벤트 참여 조회
	 * @Method Name : selectEvEventRouletteJoinMemberCountByEventNo
	 * @Date : 2019. 7. 19
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public int selectEvEventRouletteJoinMemberCountByEventNo(String eventNo) throws Exception;

	/**
	 * 이벤트 참여 조회
	 * 
	 * @Desc : 이벤트 참여 조회
	 * @Method Name : selectEvEventRouletteJoinMemberList
	 * @Date : 2019. 4. 09
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventJoinResultVO> selectEvEventRouletteJoinMemberList(
			Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable) throws Exception;

	/**
	 * 이벤트 참여 seq 조회
	 * 
	 * @Desc : 이벤트 참여 seq 조회
	 * @Method Name : selectEvEventRouletteJoinMemberSeq
	 * @Date : 2019. 4. 29
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public Long selectEvEventRouletteJoinMemberSeq(String memberNo, String eventNo) throws Exception;

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
	 * 이벤트 참여 조회
	 * 
	 * @Desc : 이벤트 참여 조회
	 * @Method Name : selectEvEventRouletteResultMemberCount
	 * @Date : 2019. 4. 09
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public int selectEvEventRouletteResultMemberCount(Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable)
			throws Exception;

	/**
	 * 이벤트 참여 조회
	 * 
	 * @Desc : 이벤트 참여 조회
	 * @Method Name : selectEvEventRouletteResultMemberList
	 * @Date : 2019. 4. 09
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventJoinResultVO> selectEvEventRouletteResultMemberList(
			Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable) throws Exception;

	/**
	 * 이벤트 룰렛 수정
	 * 
	 * @Desc : 이벤트 룰렛 수정
	 * @Method Name : updateEvEventRouletteJoinMember
	 * @Date : 2019. 8. 06
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public void updateEvEventRouletteJoinMember(EvEventRouletteJoinMember evEventRouletteJoinMember) throws Exception;

}
