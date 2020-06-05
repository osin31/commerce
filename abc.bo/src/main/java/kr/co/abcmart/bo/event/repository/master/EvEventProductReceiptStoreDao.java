package kr.co.abcmart.bo.event.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.event.model.master.EvEventProductReceiptStore;
import kr.co.abcmart.bo.event.repository.master.base.BaseEvEventProductReceiptStoreDao;

@Mapper
public interface EvEventProductReceiptStoreDao extends BaseEvEventProductReceiptStoreDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseEvEventProductReceiptStoreDao 클래스에 구현
	 * 되어있습니다. BaseEvEventProductReceiptStoreDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당
	 * 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public EvEventProductReceiptStore selectByPrimaryKey(EvEventProductReceiptStore evEventProductReceiptStore)
			throws Exception;

	/**
	 * 이벤트 매장 조회
	 * 
	 * @Desc : 이벤트 매장 조회
	 * @Method Name : selectEvEventRouletteBenefitListByEventNo
	 * @Date : 2019. 6. 07
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventProductReceiptStore> selectEvEventProductReceiptStoreListByEventNo(String eventNo)
			throws Exception;

	/**
	 * 이벤트 매장 삭제
	 * 
	 * @Desc : 이벤트 매장 삭제
	 * @Method Name : deleteByEventNo
	 * @Date : 2019. 6. 7
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public void deleteByEventNo(String eventNo) throws Exception;
}
