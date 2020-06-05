package kr.co.abcmart.bo.settlement.repository.master;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.settlement.model.master.SeExactCalculation;
import kr.co.abcmart.bo.settlement.repository.master.base.BaseSeExactCalculationDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface SeExactCalculationDao extends BaseSeExactCalculationDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSeExactCalculationDao 클래스에 구현 되어있습니다.
	 * BaseSeExactCalculationDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public SeExactCalculation selectByPrimaryKey(SeExactCalculation seExactCalculation) throws Exception;

	public int selectSettleMentListCount(Pageable<SeExactCalculation, SeExactCalculation> pageable) throws Exception;

	public List<SeExactCalculation> selectSettleMentList(Pageable<SeExactCalculation, SeExactCalculation> pageable)
			throws Exception;

	public String selectSettleMentTotalAmt(SeExactCalculation seExactCalculation) throws Exception;

	public SeExactCalculation selectSettleMentHistory(SeExactCalculation seExactCalculation) throws Exception;

	public SeExactCalculation selectSettleMentMonth(SeExactCalculation seExactCalculation) throws Exception;

	public int updateSeExactCalculationMod(SeExactCalculation seExactCalculation) throws Exception;

	public int updateExcclcConfirmation(SeExactCalculation seExactCalculation) throws Exception;

	public void callProcedurePropertySettleMent(HashMap<String, Object> param) throws Exception;

	public String selectSeExactCal(SeExactCalculation seExactCalculation) throws Exception;

}
