package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplayCornerProduct;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrPlanningDisplayCornerProductDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface PrPlanningDisplayCornerProductDao extends BasePrPlanningDisplayCornerProductDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrPlanningDisplayCornerProductDao 클래스에 구현
	 * 되어있습니다. BasePrPlanningDisplayCornerProductDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는
	 * 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public PrPlanningDisplayCornerProduct selectByPrimaryKey(
			PrPlanningDisplayCornerProduct prPlanningDisplayCornerProduct) throws Exception;

	/**
	 * @Desc : 기획전 코너 상품 삭제
	 * @Method Name : deletePrPlanningDisplayCornerProduct
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param prPlanningDisplayCornerProduct
	 * @throws Exception
	 */
	public void deletePrPlanningDisplayCornerProduct(PrPlanningDisplayCornerProduct prPlanningDisplayCornerProduct)
			throws Exception;

	/**
	 * @Desc : 기획전 코너 상품 삭제 By plndpNo
	 * @Method Name : deletePrPlanningDisplayCornerProductByPlndpNo
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param plndpNo
	 * @throws Exception
	 */
	public void deletePrPlanningDisplayCornerProductByPlndpNo(String plndpNo) throws Exception;

	/**
	 * @Desc : 기획전 코너 상품 삭제 By cornerSeq
	 * @Method Name : deletePrPlanningDisplayCornerProductByPlndpCornerSeq
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param plndpCornerSeq
	 * @throws Exception
	 */
	public void deletePrPlanningDisplayCornerProductByPlndpCornerSeq(int plndpCornerSeq) throws Exception;

	/**
	 * @Desc : 기획전 코너 대상 상품 리스트 카운트 조회
	 * @Method Name : selectPrPlanningDisplayCornerProductListCount
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectPrPlanningDisplayCornerProductListCount(
			Pageable<PrPlanningDisplayCornerProduct, PrPlanningDisplayCornerProduct> pageable) throws Exception;

	/**
	 * @Desc : 기획전 코너 대상 상품 리스트 조회
	 * @Method Name : selectPrPlanningDisplayCornerProductList
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<PrPlanningDisplayCornerProduct> selectPrPlanningDisplayCornerProductList(
			Pageable<PrPlanningDisplayCornerProduct, PrPlanningDisplayCornerProduct> pageable) throws Exception;

	/**
	 * @Desc : 기획전 코너 대상 상품 등록
	 * @Method Name : insertPrPlanningDisplayCornerProduct
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param prPlanningDisplayCornerProduct
	 * @throws Exception
	 */
	public void insertPrPlanningDisplayCornerProduct(PrPlanningDisplayCornerProduct prPlanningDisplayCornerProduct)
			throws Exception;

	/**
	 * @Desc : 기획전 코너 대상 상품 수정
	 * @Method Name : updatePrPlanningDisplayCornerProduct
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param prPlanningDisplayCornerProduct
	 * @throws Exception
	 */
	public void updatePrPlanningDisplayCornerProduct(PrPlanningDisplayCornerProduct prPlanningDisplayCornerProduct)
			throws Exception;

}
