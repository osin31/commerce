package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.PdRelationProduct;
import kr.co.abcmart.bo.product.repository.master.base.BasePdRelationProductDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface PdRelationProductDao extends BasePdRelationProductDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePdRelationProductDao 클래스에 구현 되어있습니다.
	 * BasePdRelationProductDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 */

	public PdRelationProduct selectByPrimaryKey(PdRelationProduct pdRelationProduct) throws Exception;

	/**
	 * @Desc : 관련 상품 등록
	 * @Method Name : insertWithPrimaryKey
	 * @Date : 2019. 3. 18.
	 * @Author : tennessee
	 * @param pdRelationProduct
	 * @return
	 * @throws Exception
	 */
	public Integer insertWithPrimaryKey(PdRelationProduct pdRelationProduct) throws Exception;
	
	/**
	 * @Desc      	: 관련상품 역으로 등록
	 * @Method Name : insertWithKeyReverse
	 * @Date  	  	: 2020. 4. 20.
	 * @Author    	: sic
	 * @param pdRelationProduct
	 * @return
	 * @throws Exception
	 */
	public Integer insertWithKeyReverse(PdRelationProduct pdRelationProduct) throws Exception;

	/**
	 * @Desc : 관련 상품 조회. 상품으로 서비스 이동됨
	 * @Method Name : selectRelationProduct
	 * @Date : 2019. 3. 27.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<PdRelationProduct> selectRelationProduct(Pageable<PdRelationProduct, PdRelationProduct> pageable)
			throws Exception;

	/**
	 * @Desc : 관련 상품 갯수 조회. 상품으로 서비스 이동됨
	 * @Method Name : selectRelationProductCount
	 * @Date : 2019. 3. 27.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Integer selectRelationProductCount(Pageable<PdRelationProduct, PdRelationProduct> pageable) throws Exception;

	/**
	 * @Desc : 관련상품유형코드를 사용한 연관상품 삭제
	 * @Method Name : deleteByRltnPrdtTypeCode
	 * @Date : 2019. 4. 3.
	 * @Author : tennessee
	 * @param pdRelationProduct
	 * @return
	 * @throws Exception
	 */
	public Integer deleteByRltnPrdtTypeCode(PdRelationProduct pdRelationProduct) throws Exception;

	/**
	 * @Desc : 상품번호로 관련상품 삭제
	 * @Method Name : deleteByPrdtNo
	 * @Date : 2019. 10. 29.
	 * @Author : hsjhsj19
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public Integer deleteByPrdtNo(String prdtNo) throws Exception;

}
