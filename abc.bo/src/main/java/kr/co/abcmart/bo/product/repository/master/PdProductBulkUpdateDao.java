package kr.co.abcmart.bo.product.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.PdProductBulkUpdateWrapper;

/**
 * @Desc : 상품 정보 일괄 수정
 * @FileName : PdProductBulkUpdateDao.java
 * @Project : abc.bo
 * @Date : 2019. 7. 18.
 * @Author : tennessee
 */
@Mapper
public interface PdProductBulkUpdateDao {

	/**
	 * @Desc : 상품 정보 대량 수정
	 * @Method Name : updateProduct
	 * @Date : 2019. 7. 18.
	 * @Author : tennessee
	 * @param productBulkUpdateWrapper
	 * @return
	 * @throws Exception
	 */
	public Integer updateProduct(PdProductBulkUpdateWrapper productBulkUpdateWrapper) throws Exception;

	/**
	 * @Desc : 상품 아이콘 전체 삭제
	 * @Method Name : deleteProductIcon
	 * @Date : 2019. 7. 18.
	 * @Author : tennessee
	 * @param productBulkUpdateWrapper
	 * @return
	 * @throws Exception
	 */
	public Integer deleteProductIcon(PdProductBulkUpdateWrapper productBulkUpdateWrapper) throws Exception;

	/**
	 * @Desc : 상품 아이콘 일괄 등록
	 * @Method Name : insertProductIcon
	 * @Date : 2019. 7. 18.
	 * @Author : tennessee
	 * @param productBulkUpdateWrapper
	 * @return
	 * @throws Exception
	 */
	public Integer insertProductIcon(PdProductBulkUpdateWrapper productBulkUpdateWrapper) throws Exception;

	/**
	 * @Desc : 전시카테고리 전체 삭제
	 * @Method Name : deleteDisplayCategory
	 * @Date : 2019. 7. 18.
	 * @Author : tennessee
	 * @param productBulkUpdateWrapper
	 * @return
	 * @throws Exception
	 */
	public Integer deleteDisplayCategory(PdProductBulkUpdateWrapper productBulkUpdateWrapper) throws Exception;

	/**
	 * @Desc : 전시카테고리 일괄 등록
	 * @Method Name : insertDisplayCategory
	 * @Date : 2019. 7. 18.
	 * @Author : tennessee
	 * @param productBulkUpdateWrapper
	 * @return
	 * @throws Exception
	 */
	public Integer insertDisplayCategory(PdProductBulkUpdateWrapper productBulkUpdateWrapper) throws Exception;

	/**
	 * @Desc : 상품 색상 전체 삭제
	 * @Method Name : deleteProductColor
	 * @Date : 2019. 7. 18.
	 * @Author : tennessee
	 * @param productBulkUpdateWrapper
	 * @return
	 * @throws Exception
	 */
	public Integer deleteProductColor(PdProductBulkUpdateWrapper productBulkUpdateWrapper) throws Exception;

	/**
	 * @Desc : 상품 색상 일괄 등록
	 * @Method Name : insertProductColor
	 * @Date : 2019. 7. 18.
	 * @Author : tennessee
	 * @param productBulkUpdateWrapper
	 * @return
	 * @throws Exception
	 */
	public Integer insertProductColor(PdProductBulkUpdateWrapper productBulkUpdateWrapper) throws Exception;

	/**
	 * @Desc : 관련용품 전체 삭제
	 * @Method Name : deleteRelationProductGoods
	 * @Date : 2019. 7. 18.
	 * @Author : tennessee
	 * @param productBulkUpdateWrapper
	 * @return
	 * @throws Exception
	 */
	public Integer deleteRelationProductGoods(PdProductBulkUpdateWrapper productBulkUpdateWrapper) throws Exception;

	/**
	 * @Desc : 관련용품 일괄 등록
	 * @Method Name : insertRelationProductGoods
	 * @Date : 2019. 7. 18.
	 * @Author : tennessee
	 * @param productBulkUpdateWrapper
	 * @return
	 * @throws Exception
	 */
	public Integer insertRelationProductGoods(PdProductBulkUpdateWrapper productBulkUpdateWrapper) throws Exception;

	/**
	 * @Desc :
	 * @Method Name : updateProductOption
	 * @Date : 2020. 3. 24.
	 * @Author : bluao
	 * @param productBulkUpdateWrapper
	 */
	public void updateProductOption(PdProductBulkUpdateWrapper productBulkUpdateWrapper) throws Exception;

}
