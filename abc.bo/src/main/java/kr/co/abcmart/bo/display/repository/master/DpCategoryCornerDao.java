package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpCategoryCorner;
import kr.co.abcmart.bo.display.model.master.DpDisplayTemplateCorner;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpCategoryCornerDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpCategoryCornerDao extends BaseDpCategoryCornerDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpCategoryCornerDao 클래스에 구현 되어있습니다.
	 * BaseDpCategoryCornerDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public DpCategoryCorner selectByPrimaryKey(DpCategoryCorner dpCategoryCorner) throws Exception;

	/**
	 * @Desc : 전시 카테고리 코너 삭제
	 * @Method Name : deleteDpCtgrCorner
	 * @Date : 2019. 11. 15.
	 * @Author : lee
	 * @param dpCategoryCorner
	 * @throws Exception
	 */
	public void deleteDpCtgrCorner(DpCategoryCorner dpCategoryCorner) throws Exception;

	/**
	 * @Desc : 전시 카테고리 코너 리스트 조회
	 * @Method Name : selectDpCategoryCornerList
	 * @Date : 2019. 11. 15.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<DpDisplayTemplateCorner> selectDpCategoryCornerList(
			Pageable<DpCategoryCorner, DpDisplayTemplateCorner> pageable) throws Exception;

	/**
	 * @Desc : 전시 카테고리 코너 리스트 카운트 조회
	 * @Method Name : selectDpCategoryCornerListCount
	 * @Date : 2019. 11. 15.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectDpCategoryCornerListCount(Pageable<DpCategoryCorner, DpDisplayTemplateCorner> pageable)
			throws Exception;

}
