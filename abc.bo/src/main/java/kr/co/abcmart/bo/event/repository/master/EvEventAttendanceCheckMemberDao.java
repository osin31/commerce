package kr.co.abcmart.bo.event.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.event.model.master.EvEventAttendanceCheckMember;
import kr.co.abcmart.bo.event.repository.master.base.BaseEvEventAttendanceCheckMemberDao;
import kr.co.abcmart.bo.event.vo.EvEventJoinResultVO;
import kr.co.abcmart.bo.event.vo.EvEventSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface EvEventAttendanceCheckMemberDao extends BaseEvEventAttendanceCheckMemberDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseEvEventAttendanceCheckMemberDao 클래스에 구현
	 * 되어있습니다. BaseEvEventAttendanceCheckMemberDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는
	 * 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public EvEventAttendanceCheckMember selectByPrimaryKey(EvEventAttendanceCheckMember evEventAttendanceCheckMember)
			throws Exception;

	/**
	 * 이벤트 참여 조회
	 * 
	 * @Desc : 이벤트 참여 조회
	 * @Method Name : selectEvEventAttendanceCheckMemberCount
	 * @Date : 2019. 4. 09
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public int selectEvEventAttendanceCheckMemberCount(Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable)
			throws Exception;

	/**
	 * 이벤트 참여 조회
	 * 
	 * @Desc : 이벤트 참여 조회
	 * @Method Name : selectEvEventAttendanceCheckMemberList
	 * @Date : 2019. 4. 09
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventJoinResultVO> selectEvEventAttendanceCheckMemberList(
			Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable) throws Exception;

	/**
	 * 이벤트 참여 조회
	 * 
	 * @Desc : 이벤트 참여 조회
	 * @Method Name : selectEvEventAttendMemberCountByEventNo
	 * @Date : 2019. 7. 26
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public int selectEvEventAttendMemberCountByEventNo(String eventNo) throws Exception;

	/**
	 * 이벤트 참여 seq 조회
	 * 
	 * @Desc : 이벤트 참여 seq 조회
	 * @Method Name : selectEvEventAttendanceCheckMemberSeq
	 * @Date : 2019. 4. 29
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public Long selectEvEventAttendanceCheckMemberSeq(String memberNo, String eventNo) throws Exception;

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
	 * @Method Name : selectEvEventAttendanceResultMemberCount
	 * @Date : 2019. 4. 09
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public int selectEvEventAttendanceResultMemberCount(Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable)
			throws Exception;

	/**
	 * 이벤트 참여 조회
	 * 
	 * @Desc : 이벤트 참여 조회
	 * @Method Name : selectEvEventAttendanceReusltMemberList
	 * @Date : 2019. 4. 09
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventJoinResultVO> selectEvEventAttendanceReusltMemberList(
			Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable) throws Exception;

}
