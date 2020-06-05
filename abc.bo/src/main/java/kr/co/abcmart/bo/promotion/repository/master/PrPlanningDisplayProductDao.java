package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplayProduct;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrPlanningDisplayProductDao;
import kr.co.abcmart.bo.promotion.vo.PrExhibitionPlanningVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface PrPlanningDisplayProductDao extends BasePrPlanningDisplayProductDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrPlanningDisplayProductDao 클래스에 구현
	 * 되어있습니다. BasePrPlanningDisplayProductDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당
	 * 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public PrPlanningDisplayProduct selectByPrimaryKey(PrPlanningDisplayProduct prPlanningDisplayProduct)
			throws Exception;

	/**
	 * @Desc : 기획전 상품 목록 카운트 조회
	 * @Method Name : selectPrPlanningDisplayProductListCount
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectPrPlanningDisplayProductListCount(
			Pageable<PrPlanningDisplayProduct, PrPlanningDisplayProduct> pageable) throws Exception;

	/**
	 * @Desc : 기획전 상품 목록 조회
	 * @Method Name : selectPrPlanningDisplayProductList
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<PrPlanningDisplayProduct> selectPrPlanningDisplayProductList(
			Pageable<PrPlanningDisplayProduct, PrPlanningDisplayProduct> pageable) throws Exception;

	/**
	 * @Desc : 기획전 상품 등록
	 * @Method Name : insertPrPlanningDisplayProduct
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param prPlanningDisplayProduct
	 * @throws Exception
	 */
	public void insertPrPlanningDisplayProduct(PrPlanningDisplayProduct prPlanningDisplayProduct) throws Exception;

	/**
	 * @Desc : 기획전 상품 수정
	 * @Method Name : updatePrPlanningDisplayProduct
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param prPlanningDisplayProduct
	 * @throws Exception
	 */
	public void updatePrPlanningDisplayProduct(PrPlanningDisplayProduct prPlanningDisplayProduct) throws Exception;

	/**
	 * @Desc : 기획전 상품 삭제
	 * @Method Name : deletePrPlanningDisplayProduct
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param prPlanningDisplayProduct
	 * @throws Exception
	 */
	public void deletePrPlanningDisplayProduct(PrPlanningDisplayProduct prPlanningDisplayProduct) throws Exception;

	/**
	 * @Desc : 한 상품에 대한 기획전 정보 조회
	 * @Method Name : selectByExhibitionPlanningForProduct
	 * @Date : 2019. 4. 23.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<PrExhibitionPlanningVO> selectByExhibitionPlanningForProduct(
			Pageable<PrExhibitionPlanningVO, PrExhibitionPlanningVO> pageable) throws Exception;

	/**
	 * @Desc : 한 상품에 대한 기획전 정보 전체 갯수 조회
	 * @Method Name : selectByExhibitionPlanningForProductTotalCount
	 * @Date : 2019. 4. 23.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Integer selectByExhibitionPlanningForProductTotalCount(
			Pageable<PrExhibitionPlanningVO, PrExhibitionPlanningVO> pageable) throws Exception;
}
