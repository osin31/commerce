package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpCategoryProduct;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpCategoryProductDao;
import kr.co.abcmart.bo.display.vo.DpExhibitionPageVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpCategoryProductDao extends BaseDpCategoryProductDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpCategoryProductDao 클래스에 구현 되어있습니다.
	 * BaseDpCategoryProductDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public DpCategoryProduct selectByPrimaryKey(DpCategoryProduct dpCategoryProduct) throws Exception;

	/**
	 * @Desc : 상품 번호 기반 삭제
	 * @Method Name : deleteByPrdtNo
	 * @Date : 2019. 4. 17.
	 * @Author : tennessee
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public Integer deleteByPrdtNo(String prdtNo) throws Exception;

	/**
	 * @Desc : 한 상품과 연계된 전시 페이지 조회
	 * @Method Name : selectByExhibitionPageForProduct
	 * @Date : 2019. 4. 23.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<DpExhibitionPageVO> selectByExhibitionPageForProduct(
			Pageable<DpExhibitionPageVO, DpExhibitionPageVO> pageable) throws Exception;

	/**
	 * @Desc : 한 상품과 연계된 전시 페이지 전체 갯수 조회
	 * @Method Name : selectByExhibitionPageForProductTotalCount
	 * @Date : 2019. 4. 23.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Integer selectByExhibitionPageForProductTotalCount(Pageable<DpExhibitionPageVO, DpExhibitionPageVO> pageable)
			throws Exception;

	/**
	 * @Desc : 카테고리 번호 및 그와 연계된 카테고리에 연결된 상품 갯수 조회
	 * @Method Name : selectCountForRelatedProduct
	 * @Date : 2019. 6. 19.
	 * @Author : tennessee
	 * @param ctgrNo
	 * @return
	 * @throws Exception
	 */
	public Integer selectCountForRelatedProduct(String ctgrNo) throws Exception;

}
