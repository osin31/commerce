package kr.co.abcmart.bo.order.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.order.model.master.OcOrderDeliveryAlertHistory;
import kr.co.abcmart.bo.order.repository.master.base.BaseOcOrderDeliveryAlertHistoryDao;

@Mapper
public interface OcOrderDeliveryAlertHistoryDao extends BaseOcOrderDeliveryAlertHistoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcOrderDeliveryAlertHistoryDao 클래스에 구현
	 * 되어있습니다. BaseOcOrderDeliveryAlertHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당
	 * 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public OcOrderDeliveryAlertHistory selectByPrimaryKey(OcOrderDeliveryAlertHistory ocOrderDeliveryAlertHistory)
			throws Exception;

	public void insertOcOrderDeliveryAlertHistory(String dlvyIdText) throws Exception;

}
