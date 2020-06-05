package kr.co.abcmart.bo.cmm.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.cmm.model.master.CmEmailSendHistory;
import kr.co.abcmart.bo.cmm.repository.master.base.BaseCmEmailSendHistoryDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface CmEmailSendHistoryDao extends BaseCmEmailSendHistoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseCmEmailSendHistoryDao 클래스에 구현 되어있습니다.
	 * BaseCmEmailSendHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public CmEmailSendHistory selectByPrimaryKey(CmEmailSendHistory cmEmailSendHistory) throws Exception;

	/**
	 * 
	 * @Desc : 이메일 발송 리스트 개수 조회
	 * @Method Name : selectEmailSendHistoryListCount
	 * @Date : 2019. 3. 20.
	 * @Author : choi
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectEmailSendHistoryListCount(Pageable<CmEmailSendHistory, CmEmailSendHistory> pageable)
			throws Exception;

	/**
	 * 
	 * @Desc : 이메일 발송 리스트 데이터 조회
	 * @Method Name : selectEmailSendHistoryList
	 * @Date : 2019. 3. 20.
	 * @Author : choi
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<CmEmailSendHistory> selectEmailSendHistoryList(
			Pageable<CmEmailSendHistory, CmEmailSendHistory> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 이메일 발송 로그 상세 데이터 조회
	 * @Method Name : getmailSendHistoryDetail
	 * @Date : 2019. 3. 20.
	 * @Author : choi
	 * @param cmEmailSendHistory
	 * @return
	 * @throws Exception
	 */
	public CmEmailSendHistory getmailSendHistoryDetail(CmEmailSendHistory cmEmailSendHistory) throws Exception;

	/**
	 * @Desc : 실시간 발송여부에 다른 send_dtm 처리 insert
	 * @Method Name : insertMail
	 * @Date : 2019. 5. 10.
	 * @Author : Kimyounghyun
	 * @param cmEmailSendHistory
	 * @return
	 * @throws Exception
	 */
	public int insertMail(CmEmailSendHistory cmEmailSendHistory) throws Exception;
}
