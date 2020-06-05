package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.DpBrandStyleProduct;
import kr.co.abcmart.bo.product.repository.master.base.BaseDpBrandStyleProductDao;
import kr.co.abcmart.bo.product.vo.DpBrandStyleProductSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpBrandStyleProductDao extends BaseDpBrandStyleProductDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpBrandStyleProductDao 클래스에 구현 되어있습니다.
	 * BaseDpBrandStyleProductDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public DpBrandStyleProduct selectByPrimaryKey(DpBrandStyleProduct dpBrandStyleProduct) throws Exception;

	/**
	 * @Desc : 브랜드 스타일 저장
	 * @Method Name : insertBrandStyleProduct
	 * @Date : 2019. 6. 21.
	 * @Author : 백인천
	 * @param dpBrandStyleProduct
	 * @return
	 * @throws Exception
	 */
	public int insertBrandStyleProduct(DpBrandStyleProduct dpBrandStyleProduct) throws Exception;

	/**
	 * @Desc : 브랜드 스타일 목록 조회
	 * @Method Name : selectBrandStyleList
	 * @Date : 2019. 2. 11.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<DpBrandStyleProduct> selectBrandStyleProductList(
			Pageable<DpBrandStyleProductSearchVO, DpBrandStyleProduct> pageable) throws Exception;

	/**
	 * @Desc : 브랜드 스타일 목록 총갯수 조회
	 * @Method Name : selectBrandStyleListCount
	 * @Date : 2019. 2. 11.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Integer selectBrandStyleProductListCount(Pageable<DpBrandStyleProductSearchVO, DpBrandStyleProduct> pageable)
			throws Exception;

	/**
	 * @Desc : 브랜드 스타일 대상상품 삭제
	 * @Method Name : deleteBrandStyleProduct
	 * @Date : 2019. 7. 1.
	 * @Author : 백인천
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int deleteBrandStyleProduct(DpBrandStyleProduct dpBrandStyleProduct) throws Exception;

}
