package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.DpBrandPage;
import kr.co.abcmart.bo.product.model.master.DpBrandProduct;
import kr.co.abcmart.bo.product.model.master.DpBrandVisual;
import kr.co.abcmart.bo.product.repository.master.base.BaseDpBrandPageDao;
import kr.co.abcmart.bo.product.vo.DpBrandPageSearchVO;
import kr.co.abcmart.bo.product.vo.DpBrandVisualSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpBrandPageDao extends BaseDpBrandPageDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpBrandPageDao 클래스에 구현 되어있습니다.
	 * BaseDpBrandPageDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 */

	public DpBrandPage selectByPrimaryKey(DpBrandPage dpBrandPage) throws Exception;

	/**
	 * @Desc : 브랜드 통합몰 페이지 리스트 조회
	 * @Method Name : selectBrandPageList
	 * @Date : 2019. 6. 5.
	 * @Author : 백인천
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<DpBrandPage> selectBrandPageList(Pageable<DpBrandPageSearchVO, DpBrandPage> pageable) throws Exception;

	/**
	 * @Desc : 브랜드 페이지 목록 총갯수 조회
	 * @Method Name : selectBrandPageListCount
	 * @Date : 2019. 6. 5.
	 * @Author : 백인천
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectBrandPageListCount(Pageable<DpBrandPageSearchVO, DpBrandPage> pageable) throws Exception;

	/**
	 * @Desc : 브랜드 페이지 insert
	 * @Method Name : insertBrandPage
	 * @Date : 2019. 6. 5.
	 * @Author : 백인천
	 * @param dpBrandPage
	 * @return
	 * @throws Exception
	 */
	public int insertBrandPage(DpBrandPage dpBrandPage) throws Exception;

	/**
	 * @Desc : 브랜드 페이지 목록 총갯수 조회
	 * @Method Name : getBrandPageCount
	 * @Date : 2019. 6. 29.
	 * @Author : 백인천
	 * @param brandPage
	 * @return
	 * @throws Exception
	 */
	public int getBrandPageCount(DpBrandPage brandPage) throws Exception;

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
	 * @Desc : 브랜드 프로모션 타입별 리스트 조회
	 * @Method Name : selectBrandPageTypeList
	 * @Date : 2019. 6. 5.
	 * @Author : 백인천
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<DpBrandPage> selectBrandPageTypeList(DpBrandPage dpBrandPage) throws Exception;

	/**
	 * @Desc : 브랜드페이지 전시여부 업데이트
	 * @Method Name : updateBrandPageDispYn
	 * @Date : 2019. 12. 24.
	 * @Author : 유성민
	 * @param brandPage
	 */
	public void updateBrandPageDispYn(DpBrandPage brandPage) throws Exception;

}
