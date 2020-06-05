package kr.co.abcmart.bo.settlement.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.settlement.model.master.SeExactCalculation;
import kr.co.abcmart.bo.settlement.model.master.SeExactCalculationBrandProduct;
import kr.co.abcmart.bo.settlement.repository.master.base.BaseSeExactCalculationBrandProductDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface SeExactCalculationBrandProductDao extends BaseSeExactCalculationBrandProductDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSeExactCalculationBrandProductDao 클래스에 구현
	 * 되어있습니다. BaseSeExactCalculationBrandProductDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는
	 * 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public SeExactCalculationBrandProduct selectByPrimaryKey(
			SeExactCalculationBrandProduct seExactCalculationBrandProduct) throws Exception;

	/**
	 * @Desc :판매수수료 탭 카운트
	 * @Method Name : selectSeExactCalcSellAmtCount
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectSeExactCalcSellAmtCount(Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable)
			throws Exception;

	/**
	 * @Desc :판매수수료 탭 리스트
	 * @Method Name : selectSeExactCalcSellAmtList
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SeExactCalculationBrandProduct> selectSeExactCalcSellAmtList(
			Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable) throws Exception;

	/**
	 * @Desc :배송비 카운트
	 * @Method Name : selectSeExactCalcDlvyAmtCount
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectSeExactCalcDlvyAmtCount(Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable)
			throws Exception;

	/**
	 * @Desc :배송비 리스트
	 * @Method Name : selectSeExactCalcDlvyAmtList
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SeExactCalculationBrandProduct> selectSeExactCalcDlvyAmtList(
			Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable) throws Exception;

	/**
	 * @Desc :프로모션 카운트
	 * @Method Name : selectSeExactCalcPromoAmtCount
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectSeExactCalcPromoAmtCount(Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable)
			throws Exception;

	/**
	 * @Desc :프로모션 리스트
	 * @Method Name : selectSeExactCalcPromoAmtList
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SeExactCalculationBrandProduct> selectSeExactCalcPromoAmtList(
			Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable) throws Exception;

	/**
	 * @Desc :패널티 카운트
	 * @Method Name : selectSeExactCalcPenltyAmtCount
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectSeExactCalcPenltyAmtCount(Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable)
			throws Exception;

	/**
	 * @Desc :패널티 리스트
	 * @Method Name : selectSeExactCalcPenltyAmtList
	 * @Date : 2019. 7. 16.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SeExactCalculationBrandProduct> selectSeExactCalcPenltyAmtList(
			Pageable<SeExactCalculation, SeExactCalculationBrandProduct> pageable) throws Exception;

}
