package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.DpBrandStyle;
import kr.co.abcmart.bo.product.repository.master.base.BaseDpBrandStyleDao;
import kr.co.abcmart.bo.product.vo.DpBrandStyleSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpBrandStyleDao extends BaseDpBrandStyleDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpBrandStyleDao 클래스에 구현 되어있습니다.
	 * BaseDpBrandStyleDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public DpBrandStyle selectByPrimaryKey(DpBrandStyle dpBrandStyle) throws Exception;

	/**
	 * @Desc : 브랜드 스타일 목록 조회
	 * @Method Name : selectBrandStyleList
	 * @Date : 2019. 2. 11.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<DpBrandStyle> selectBrandStyleList(Pageable<DpBrandStyleSearchVO, DpBrandStyle> pageable)
			throws Exception;

	/**
	 * @Desc : 브랜드 스타일 목록 총갯수 조회
	 * @Method Name : selectBrandStyleListCount
	 * @Date : 2019. 2. 11.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Integer selectBrandStyleListCount(Pageable<DpBrandStyleSearchVO, DpBrandStyle> pageable) throws Exception;

	/**
	 * @Desc : 브랜드 스타일 등록
	 * @Method Name : insertBrandStyle
	 * @Date : 2019. 2. 19.
	 * @Author : tennessee
	 * @param dpBrandStyle
	 * @return
	 * @throws Exception
	 */
	public int insertBrandStyle(DpBrandStyle dpBrandStyle) throws Exception;

	/**
	 * @Desc : 브랜드 스타일 노출순서 가져오기
	 * @Method Name : getBrandStyleRow
	 * @Date : 2019. 8. 16.
	 * @Author : 백인천
	 * @param dpBrandStyle
	 * @return
	 * @throws Exception
	 */
	public DpBrandStyle getBrandStyleRow(DpBrandStyle brandStyle) throws Exception;

}
