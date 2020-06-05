package kr.co.abcmart.bo.event.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.event.model.master.EvEventResult;
import kr.co.abcmart.bo.event.repository.master.base.BaseEvEventResultDao;
import kr.co.abcmart.bo.event.vo.EvEventSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface EvEventResultDao extends BaseEvEventResultDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseEvEventResultDao 클래스에 구현 되어있습니다.
	 * BaseEvEventResultDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public EvEventResult selectByPrimaryKey(EvEventResult evEventResult) throws Exception;

	/**
	 * 이벤트 당첨자 리스트 조회
	 * 
	 * @Desc : 이벤트 리스트 조회
	 * @Method Name : selectEventResultList
	 * @Date : 2019. 4. 11
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public List<EvEventResult> selectEventResultList(Pageable<EvEventSearchVO, EvEventResult> pageable)
			throws Exception;

	/**
	 * 이벤트 당첨자 리스트 카운트 조회
	 * 
	 * @Desc :
	 * @Method Name : selectEventResultCount
	 * @Date : 2019. 4. 11
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public int selectEventResultCount(Pageable<EvEventSearchVO, EvEventResult> pageable) throws Exception;

	/**
	 * 이벤트 당첨자 상세 카운트 조회
	 * 
	 * @Desc :
	 * @Method Name : selectEventResult
	 * @Date : 2019. 4. 11
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public EvEventResult selectEventResult(EvEventResult evEventResult) throws Exception;

	/**
	 * 이벤트 당첨자 상세수정
	 * 
	 * @Desc :
	 * @Method Name : updateEventResult
	 * @Date : 2019. 4. 29
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public void updateEventResult(EvEventResult evEventResult) throws Exception;

	/**
	 * 이벤트 결과 이벤트번호 중복체크 조회
	 * 
	 * @Desc :
	 * @Method Name : selectEventResultDuplCheck
	 * @Date : 2019. 7. 29
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public int selectEventResultDuplCheck(String eventNo) throws Exception;

}
