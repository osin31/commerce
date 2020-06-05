package kr.co.abcmart.bo.settlement.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.settlement.model.master.SeExactCalculation;
import kr.co.abcmart.bo.settlement.model.master.SeExactCalculationBrand;
import kr.co.abcmart.bo.settlement.repository.master.base.BaseSeExactCalculationBrandDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface SeExactCalculationBrandDao extends BaseSeExactCalculationBrandDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSeExactCalculationBrandDao 클래스에 구현 되어있습니다.
	 * BaseSeExactCalculationBrandDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public SeExactCalculationBrand selectByPrimaryKey(SeExactCalculationBrand seExactCalculationBrand) throws Exception;

	/**
	 * @Desc :월정산 브랜드 리스트 카운트
	 * @Method Name : selectSeExactCalcMonthSettleMentCount
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectSeExactCalcMonthSettleMentCount(Pageable<SeExactCalculation, SeExactCalculationBrand> pageable)
			throws Exception;

	/**
	 * @Desc :월정산 브랜드 리스트
	 * @Method Name : selectSeExactCalcMonthSettleMentList
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SeExactCalculationBrand> selectSeExactCalcMonthSettleMentList(
			Pageable<SeExactCalculation, SeExactCalculationBrand> pageable) throws Exception;

	/**
	 * @Desc : 브랜드 조정금액
	 * @Method Name : updateSeExactCalBrand
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param seExactCalculationBrand
	 * @return
	 * @throws Exception
	 */
	public int updateSeExactCalBrand(SeExactCalculationBrand seExactCalculationBrand) throws Exception;

	/**
	 * @Desc : 패널티 및 정산 조정금액 SUM
	 * @Method Name : selectMdatAmt
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param seExactCalculationBrand
	 * @return
	 * @throws Exception
	 */
	public SeExactCalculationBrand selectMdatAmt(SeExactCalculationBrand seExactCalculationBrand) throws Exception;
}
