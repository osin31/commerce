package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.DpBrandProduct;
import kr.co.abcmart.bo.product.repository.master.base.BaseDpBrandProductDao;
import kr.co.abcmart.bo.product.vo.DpBrandProductSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpBrandProductDao extends BaseDpBrandProductDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpBrandProductDao 클래스에 구현 되어있습니다.
	 * BaseDpBrandProductDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */
	public DpBrandProduct selectByPrimaryKey(DpBrandProduct dpBrandProduct) throws Exception;

	public int insertBrandProduct(DpBrandProduct dpBrandProduct) throws Exception;

	/**
	 * @Desc : 브랜드 비주얼 대상상품 목록 카운트
	 * @Method Name : selectBrandProductCount
	 * @Date : 2019. 6. 13.
	 * @Author : 백인천
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectBrandProductCount(Pageable<DpBrandProductSearchVO, DpBrandProduct> pageable) throws Exception;

	/**
	 * @Desc : 브랜드 비주얼 대상상품 목록 조회
	 * @Method Name : selectBrandProduct
	 * @Date : 2019. 6. 13.
	 * @Author : 백인천
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<DpBrandProduct> selectBrandProduct(Pageable<DpBrandProductSearchVO, DpBrandProduct> pageable)
			throws Exception;

	/**
	 * @Desc : 브랜드 비주얼 대상상품 삭제
	 * @Method Name : deleteBrandProduct
	 * @Date : 2019. 6. 13.
	 * @Author : 백인천
	 * @param dpBrandProduct
	 * @return
	 * @throws Exception
	 */
	public int deleteBrandProduct(DpBrandProduct dpBrandProduct) throws Exception;

	/**
	 * @Desc : 타 브랜드 배너 비주얼 대상상품 체크
	 * @Method Name : selectDuplicateBannerCheckCount
	 * @Date : 2019. 12. 19.
	 * @Author : 이지훈
	 * @param dpBrandProduct
	 * @return
	 * @throws Exception
	 */
	public int selectDuplicateBannerCheckCount(DpBrandProduct dpBrandProduct) throws Exception;

}
