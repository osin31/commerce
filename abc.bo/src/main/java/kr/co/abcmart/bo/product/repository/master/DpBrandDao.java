package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.DpBrand;
import kr.co.abcmart.bo.product.repository.master.base.BaseDpBrandDao;
import kr.co.abcmart.bo.product.vo.DpBrandSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpBrandDao extends BaseDpBrandDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpBrandDao 클래스에 구현 되어있습니다. BaseDpBrandDao는
	 * 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 */

	public DpBrand selectByPrimaryKey(DpBrand dpBrand) throws Exception;

	/**
	 * 브랜드 조회
	 *
	 * @Desc :
	 * @Method Name : selectBrand
	 * @Date : 2019. 7. 29.
	 * @Author : SANTA
	 * @param dpBrand
	 * @return
	 * @throws Exception
	 */
	public DpBrand selectBrand(DpBrand dpBrand) throws Exception;

	/**
	 * @Desc : 브랜드 목록 조회
	 * @Method Name : selectBrandList
	 * @Date : 2019. 2. 19.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<DpBrand> selectBrandList(Pageable<DpBrandSearchVO, DpBrand> pageable) throws Exception;

	/**
	 * @Desc : 브랜드 목록 총 갯수 조회
	 * @Method Name : selectBrandListCount
	 * @Date : 2019. 2. 19.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Integer selectBrandListCount(Pageable<DpBrandSearchVO, DpBrand> pageable) throws Exception;

	/**
	 * @Desc : 브랜드 API 조회
	 * @Method Name : selectBrandApi
	 * @Date : 2019. 2. 25.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public List<DpBrand> selectBrandApi(DpBrandSearchVO criteria) throws Exception;

	/**
	 * @Desc : 브랜드 수정
	 * @Method Name : updateBrand
	 * @Date : 2019. 11. 21.
	 * @Author : hsjhsj19
	 * @param dpBrand
	 * @return
	 * @throws Exception
	 */
	public int updateBrand(DpBrand dpBrand) throws Exception;

	/**
	 * @Desc : 브랜드 번호 카운트
	 * @Method Name : selectBrandNoCount
	 * @Date : 2019. 12. 23.
	 * @Author : 유성민
	 * @param brand
	 * @return
	 */
	public int selectBrandNoCount(DpBrand brand) throws Exception;

}
