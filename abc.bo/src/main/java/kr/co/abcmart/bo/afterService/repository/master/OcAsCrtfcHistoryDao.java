package kr.co.abcmart.bo.afterService.repository.master;

import org.apache.ibatis.annotations.Mapper;
import kr.co.abcmart.bo.afterService.repository.master.base.BaseOcAsCrtfcHistoryDao;
import kr.co.abcmart.bo.afterService.model.master.OcAsCrtfcHistory;

@Mapper
public interface OcAsCrtfcHistoryDao extends BaseOcAsCrtfcHistoryDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BaseOcAsCrtfcHistoryDao 클래스에 구현 되어있습니다.
     * BaseOcAsCrtfcHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
     */

	public OcAsCrtfcHistory selectByPrimaryKey(OcAsCrtfcHistory ocAsCrtfcHistory) throws Exception;

}
