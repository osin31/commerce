package kr.co.abcmart.bo.order.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.order.model.master.IfOrder;
import kr.co.abcmart.bo.order.repository.master.base.BaseIfOrderDao;

@Mapper
public interface IfOrderDao extends BaseIfOrderDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseIfOrderDao 클래스에 구현 되어있습니다. BaseIfOrderDao는
	 * 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public IfOrder selectByPrimaryKey(IfOrder ifOrder) throws Exception;

	/* 수취인 변경시 */
	public int updateRcvrInfoUdpate(IfOrder ifOrder) throws Exception;

	/* 상태 변경시 */
	public int updateOrdStatusUdpate(IfOrder ifOrder) throws Exception;

	/* 옵션 변경시 */
	public int updateOptionChange(IfOrder ifOrder) throws Exception;

}
