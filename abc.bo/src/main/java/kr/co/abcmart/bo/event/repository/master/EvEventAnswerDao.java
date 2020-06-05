package kr.co.abcmart.bo.event.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.event.model.master.EvEventAnswer;
import kr.co.abcmart.bo.event.repository.master.base.BaseEvEventAnswerDao;
import kr.co.abcmart.bo.event.vo.EvEventJoinResultVO;
import kr.co.abcmart.bo.event.vo.EvEventSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface EvEventAnswerDao extends BaseEvEventAnswerDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseEvEventAnswerDao 클래스에 구현 되어있습니다.
	 * BaseEvEventAnswerDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public EvEventAnswer selectByPrimaryKey(EvEventAnswer evEventAnswer) throws Exception;

	/**
	 * 이벤트 참여 조회
	 * 
	 * @Desc : 이벤트 참여 조회
	 * @Method Name : selectEvEventAnswerCount
	 * @Date : 2019. 4. 09
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public int selectEvEventAnswerCount(Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable) throws Exception;

	/**
	 * 이벤트 참여 조회
	 * 
	 * @Desc : 이벤트 참여 조회
	 * @Method Name : selectEvEventAnswerList
	 * @Date : 2019. 4. 09
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventJoinResultVO> selectEvEventAnswerList(Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable)
			throws Exception;

	/**
	 * 이벤트 댓글 수정
	 * 
	 * @Desc : 이벤트 댓글 수정
	 * @Method Name : updateEvEventAnswer
	 * @Date : 2019. 6. 10
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public void updateEvEventAnswer(EvEventAnswer evEventAnswer) throws Exception;

	/**
	 * 이벤트 참여 seq 조회
	 * 
	 * @Desc : 이벤트 참여 seq 조회
	 * @Method Name : selectEvEventAnswerSeq
	 * @Date : 2019. 4. 29
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public Long selectEvEventAnswerSeq(String memberNo, String eventNo) throws Exception;

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
	 * @Method Name : selectEvEventResultAnswerCount
	 * @Date : 2019. 4. 09
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public int selectEvEventResultAnswerCount(Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable) throws Exception;

	/**
	 * 이벤트 참여 조회
	 * 
	 * @Desc : 이벤트 참여 조회
	 * @Method Name : selectEvEventResultAnswerList
	 * @Date : 2019. 4. 09
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventJoinResultVO> selectEvEventResultAnswerList(
			Pageable<EvEventSearchVO, EvEventJoinResultVO> pageable) throws Exception;

}
