package kr.co.abcmart.bo.order.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.order.model.master.OcOrderReceiverChangeHistory;
import kr.co.abcmart.bo.order.repository.master.base.BaseOcOrderReceiverChangeHistoryDao;

@Mapper
public interface OcOrderReceiverChangeHistoryDao extends BaseOcOrderReceiverChangeHistoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcOrderReceiverChangeHistoryDao 클래스에 구현
	 * 되어있습니다. BaseOcOrderReceiverChangeHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는
	 * 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public OcOrderReceiverChangeHistory selectByPrimaryKey(OcOrderReceiverChangeHistory ocOrderReceiverChangeHistory)
			throws Exception;

	/**
	 * @Desc : 주문 수취인 변경 이력 저장
	 * @Method Name : insertReceiverChangeHistory
	 * @Date : 2019. 3. 28.
	 * @Author : flychani@3top.co.kr
	 * @param receiverChangeHistory
	 */
	public int insertReceiverChangeHistory(OcOrderReceiverChangeHistory receiverChangeHistory) throws Exception;

}
