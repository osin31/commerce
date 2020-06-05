package kr.co.abcmart.bo.cmm.repository.master;

import org.apache.ibatis.annotations.Mapper;
import kr.co.abcmart.bo.cmm.repository.master.base.BaseCmPushSendHistoryDao;
import kr.co.abcmart.bo.cmm.model.master.CmPushSendHistory;

@Mapper
public interface CmPushSendHistoryDao extends BaseCmPushSendHistoryDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BaseCmPushSendHistoryDao 클래스에 구현 되어있습니다.
     * BaseCmPushSendHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
     */

	public CmPushSendHistory selectByPrimaryKey(CmPushSendHistory cmPushSendHistory) throws Exception;

}
