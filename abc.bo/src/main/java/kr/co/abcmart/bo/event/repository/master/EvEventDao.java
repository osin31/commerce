package kr.co.abcmart.bo.event.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.event.model.master.EvEvent;
import kr.co.abcmart.bo.event.repository.master.base.BaseEvEventDao;
import kr.co.abcmart.bo.event.vo.EvEventSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface EvEventDao extends BaseEvEventDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseEvEventDao 클래스에 구현 되어있습니다. BaseEvEventDao는
	 * 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public EvEvent selectByPrimaryKey(EvEvent evEvent) throws Exception;

	/**
	 * 이벤트 리스트 조회
	 * 
	 * @Desc : 이벤트 리스트 조회
	 * @Method Name : selectEvEventList
	 * @Date : 2019. 3. 25
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public List<EvEvent> selectEvEventList(Pageable<EvEventSearchVO, EvEvent> pageable) throws Exception;

	/**
	 * 이벤트 리스트 카운트 조회
	 * 
	 * @Desc :
	 * @Method Name : selectEvEventCount
	 * @Date : 2019. 3. 25
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public int selectEvEventCount(Pageable<EvEventSearchVO, EvEvent> pageable) throws Exception;

	/**
	 * 이벤트 상세 조회
	 * 
	 * @Desc :
	 * @Method Name : selectEvEvent
	 * @Date : 2019. 3. 27
	 * @Author : easyhun
	 * @param evEvent
	 * @return
	 */
	public EvEvent selectEvEvent(EvEvent evEvent) throws Exception;

	/**
	 * 이벤트 상세 조회
	 * 
	 * @Desc :
	 * @Method Name : selectEventDuplCheck
	 * @Date : 2019. 3. 27
	 * @Author : easyhun
	 * @param insdMgmtInfoText
	 * @return
	 */
	public int selectEventDuplCheck(String insdMgmtInfoText, String eventNo) throws Exception;

	/**
	 * 이벤트 등록
	 * 
	 * @Desc :
	 * @Method Name : insertEvEvent
	 * @Date : 2019. 4. 03
	 * @Author : easyhun
	 * @param evEvent
	 * @return
	 */
	public void insertEvEvent(EvEvent evEvent) throws Exception;

	/**
	 * 이벤트 수정
	 * 
	 * @Desc :
	 * @Method Name : updateEvEvent
	 * @Date : 2019. 7. 17
	 * @Author : easyhun
	 * @param evEvent
	 * @return
	 */
	public void updateEvEvent(EvEvent evEvent) throws Exception;

	/**
	 * 이벤트 리스트 카운트 조회(회원참여)
	 * 
	 * @Desc :
	 * @Method Name : selectMemberHistoryEventCount
	 * @Date : 2019. 7. 24
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public int selectMemberHistoryEventCount(Pageable<EvEventSearchVO, EvEvent> pageable) throws Exception;

	/**
	 * 이벤트 리스트 조회(회원참여)
	 * 
	 * @Desc : 이벤트 리스트 조회(회원참여)
	 * @Method Name : selectMemberHistoryEventList
	 * @Date : 2019. 7. 24
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public List<EvEvent> selectMemberHistoryEventList(Pageable<EvEventSearchVO, EvEvent> pageable) throws Exception;

}
