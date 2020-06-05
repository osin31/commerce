package kr.co.abcmart.bo.order.repository.master;

import org.apache.ibatis.annotations.Mapper;
import kr.co.abcmart.bo.order.repository.master.base.BaseOcGiftCardInterlockHistoryDao;
import kr.co.abcmart.bo.order.model.master.OcGiftCardInterlockHistory;

@Mapper
public interface OcGiftCardInterlockHistoryDao extends BaseOcGiftCardInterlockHistoryDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BaseOcGiftCardInterlockHistoryDao 클래스에 구현 되어있습니다.
     * BaseOcGiftCardInterlockHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
     */

	public OcGiftCardInterlockHistory selectByPrimaryKey(OcGiftCardInterlockHistory ocGiftCardInterlockHistory) throws Exception;

}
