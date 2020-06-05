package kr.co.abcmart.bo.settlement.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.settlement.model.master.SeExactCalculationMemo;
import kr.co.abcmart.bo.settlement.repository.master.base.BaseSeExactCalculationMemoDao;

@Mapper
public interface SeExactCalculationMemoDao extends BaseSeExactCalculationMemoDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSeExactCalculationMemoDao 클래스에 구현 되어있습니다.
	 * BaseSeExactCalculationMemoDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public SeExactCalculationMemo selectByPrimaryKey(SeExactCalculationMemo seExactCalculationMemo) throws Exception;

	public List<SeExactCalculationMemo> selectSeExactCalculationMemoData(SeExactCalculationMemo seExactCalculationMemo)
			throws Exception;

	public void insertSeExactCalculationMemo(SeExactCalculationMemo seExactCalculationMemo) throws Exception;

	public int updateSeExactCalculationMemo(SeExactCalculationMemo seExactCalculationMemo) throws Exception;
}
