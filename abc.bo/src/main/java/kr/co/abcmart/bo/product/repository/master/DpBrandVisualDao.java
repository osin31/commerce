package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.DpBrandProduct;
import kr.co.abcmart.bo.product.model.master.DpBrandVisual;
import kr.co.abcmart.bo.product.repository.master.base.BaseDpBrandVisualDao;
import kr.co.abcmart.bo.product.vo.DpBrandVisualSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpBrandVisualDao extends BaseDpBrandVisualDao {

	/**
	 * @Desc : 브랜드 비주얼 등록
	 * @Method Name : insertBrandVisual
	 * @Date : 2019. 6. 5.
	 * @Author : 백인천
	 * @param dpBrandStyle
	 * @return
	 * @throws Exception
	 */
	public int insertBrandVisual(DpBrandVisual dpBrandVisual) throws Exception;

	/**
	 * @Desc : 브랜드 비주얼 대상상품 등록
	 * @Method Name : insertBrandVisualProduct
	 * @Date : 2019. 6. 13.
	 * @Author : 백인천
	 * @param dpBrandStyle
	 * @return
	 * @throws Exception
	 */
	public int insertBrandVisualProduct(DpBrandProduct dpBrandProduct) throws Exception;

	/**
	 * @Desc : 브랜드 비주얼 리스트 카운트
	 * @Method Name : selectBrandVisualListCount
	 * @Date : 2019. 6. 5.
	 * @Author : 백인천
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectBrandVisualListCount(Pageable<DpBrandVisualSearchVO, DpBrandVisual> pageable) throws Exception;

	/**
	 * @Desc : 브랜드 비주얼 리스트 조회
	 * @Method Name : selectBrandVisualList
	 * @Date : 2019. 6. 5.
	 * @Author : 백인천
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<DpBrandVisual> selectBrandVisualList(Pageable<DpBrandVisualSearchVO, DpBrandVisual> pageable)
			throws Exception;

	/**
	 * @Desc : 브랜드 비주얼 전시이미지 조회
	 * @Method Name : getBrandVisualInfo
	 * @Date : 2019. 7. 24.
	 * @Author : 백인천
	 * @param brandVisual
	 * @return
	 * @throws Exception
	 */
	public DpBrandVisual getBrandVisualInfo(DpBrandVisual brandVisual) throws Exception;

}
