package kr.co.abcmart.bo.settlement.repository.master;

import org.apache.ibatis.annotations.Mapper;
import kr.co.abcmart.bo.settlement.repository.master.base.BaseSeExactCalculationDeliveryAmountDao;
import kr.co.abcmart.bo.settlement.model.master.SeExactCalculationDeliveryAmount;

@Mapper
public interface SeExactCalculationDeliveryAmountDao extends BaseSeExactCalculationDeliveryAmountDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BaseSeExactCalculationDeliveryAmountDao 클래스에 구현 되어있습니다.
     * BaseSeExactCalculationDeliveryAmountDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
     */

	public SeExactCalculationDeliveryAmount selectByPrimaryKey(SeExactCalculationDeliveryAmount seExactCalculationDeliveryAmount) throws Exception;

}
