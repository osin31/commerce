package kr.co.abcmart.bo.cmm.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import kr.co.abcmart.bo.cmm.repository.master.base.BaseCmMessageSendHistoryDao;
import kr.co.abcmart.bo.cmm.vo.MessageVO;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.bo.cmm.model.master.CmMessageSendHistory;

@Mapper
public interface CmMessageSendHistoryDao extends BaseCmMessageSendHistoryDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BaseCmMessageSendHistoryDao 클래스에 구현 되어있습니다.
     * BaseCmMessageSendHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
     */

	public CmMessageSendHistory selectByPrimaryKey(CmMessageSendHistory cmMessageSendHistory) throws Exception;

	/**
	 * 
	 * @Desc      	: 메세지 발송 리스트 데이터 개수 조회
	 * @Method Name : selectMessageSendHistoryListCount
	 * @Date  	  	: 2019. 3. 20.
	 * @Author    	: choi
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectMessageSendHistoryListCount(Pageable<CmMessageSendHistory, CmMessageSendHistory> pageable) throws Exception;

	/**
	 * 
	 * @Desc      	: 메세지 발송 로그 리스트 데이터 조회
	 * @Method Name : selectMessageSendHistoryList
	 * @Date  	  	: 2019. 3. 20.
	 * @Author    	: choi
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<CmMessageSendHistory> selectMessageSendHistoryList(Pageable<CmMessageSendHistory, CmMessageSendHistory> pageable) throws Exception;

	/**
	 * 
	 * @Desc      	: SMS 발송 메세지 등록
	 * @Method Name : insertSendRealTimeSms
	 * @Date  	  	: 2019. 4. 12.
	 * @Author    	: choi
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int insertSendRealTimeSms(MessageVO mailReceiveVO) throws Exception;

	/**
	 * 
	 * @Desc      	: LMS 발송 메세지 등록
	 * @Method Name : insertSendRealTimeLms
	 * @Date  	  	: 2019. 4. 23.
	 * @Author    	: choi
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int insertSendRealTimeLms(MessageVO mailReceiveVO) throws Exception;

	/**
	 * 
	 * @Desc      	: KKO 발송 메세지 등록
	 * @Method Name : insertSendRealTimeKko
	 * @Date  	  	: 2019. 4. 23.
	 * @Author    	: choi
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int insertSendRealTimeKko(MessageVO mailReceiveVO) throws Exception;

	/**
	 * 
	 * @Desc      	: 메세지 전송 히스토리 테이블 insert
	 * @Method Name : insertMessageSendHistory
	 * @Date  	  	: 2019. 6. 14.
	 * @Author    	: choi
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int insertMessageSendHistory(CmMessageSendHistory params) throws Exception;

}
