package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.PdProductIcon;
import kr.co.abcmart.bo.product.repository.master.base.BasePdProductIconDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface PdProductIconDao extends BasePdProductIconDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePdProductIconDao 클래스에 구현 되어있습니다.
	 * BasePdProductIconDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public PdProductIcon selectByPrimaryKey(PdProductIcon pdProductIcon) throws Exception;

	/**
	 * @Desc : 상품 아이콘 등록
	 * @Method Name : insertWithSelectKey
	 * @Date : 2019. 3. 13.
	 * @Author : tennessee
	 * @param productIcon
	 * @throws Exception
	 */
	public void insertWithSelectKey(PdProductIcon productIcon) throws Exception;

	/**
	 * @Desc : 왜래키를 이용한 상품아이콘 삭제
	 * @Method Name : deleteByForeignKeys
	 * @Date : 2019. 4. 3.
	 * @Author : tennessee
	 * @param productIcon
	 * @throws Exception
	 */
	public Integer deleteByForeignKeys(PdProductIcon productIcon) throws Exception;

	/**
	 * @Desc : 아이콘 대상 상품 카운트 조회
	 * @Method Name : selectIconMappingProductListCount
	 * @Date : 2019. 5. 22.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public int selectIconMappingProductListCount(Pageable<PdProductIcon, PdProductIcon> pageable) throws Exception;

	/**
	 * @Desc : 아이콘 대상 상품 리스트 조회
	 * @Method Name : selectIconMappingProductList
	 * @Date : 2019. 5. 22.
	 * @Author : 이가영
	 * @param cmProductIcon
	 * @return
	 */
	public List<PdProductIcon> selectIconMappingProductList(Pageable<PdProductIcon, PdProductIcon> pageable)
			throws Exception;

}
