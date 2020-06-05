package kr.co.abcmart.bo.order.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.order.model.master.OcOrderDeliveryChangeHistory;
import kr.co.abcmart.bo.order.model.master.OcOrderDeliveryHistory;
import kr.co.abcmart.bo.order.repository.master.base.BaseOcOrderDeliveryChangeHistoryDao;

@Mapper
public interface OcOrderDeliveryChangeHistoryDao extends BaseOcOrderDeliveryChangeHistoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcOrderDeliveryChangeHistoryDao 클래스에 구현
	 * 되어있습니다. BaseOcOrderDeliveryChangeHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는
	 * 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public OcOrderDeliveryChangeHistory selectByPrimaryKey(OcOrderDeliveryChangeHistory ocOrderDeliveryChangeHistory)
			throws Exception;

	/**
	 * @Desc :
	 * @Method Name : selectOrderDelveryArr
	 * @Date : 2019. 2. 17.
	 * @Author : flychani@3top.co.kr
	 * @param orderNos
	 * @return
	 */
	public List<OcOrderDeliveryHistory> selectOrderDelveryArr(String[] orderNos) throws Exception;

}
