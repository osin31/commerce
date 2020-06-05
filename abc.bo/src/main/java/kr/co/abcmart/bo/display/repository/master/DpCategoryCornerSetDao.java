package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpCategoryCornerSet;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpCategoryCornerSetDao;
import kr.co.abcmart.bo.display.vo.DisplayContentsPopupVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpCategoryCornerSetDao extends BaseDpCategoryCornerSetDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpCategoryCornerSetDao 클래스에 구현 되어있습니다.
	 * BaseDpCategoryCornerSetDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public DpCategoryCornerSet selectByPrimaryKey(DpCategoryCornerSet dpCategoryCornerSet) throws Exception;

	/**
	 * 
	 * @Desc : 전시 카테고리 코너 셋 저장
	 * @Method Name : insertSet
	 * @Date : 2019. 3. 21.
	 * @Author : SANTA
	 * @param dpCategoryCornerSet
	 * @throws Exception
	 */
	public void insertSet(DpCategoryCornerSet dpCategoryCornerSet) throws Exception;

	/**
	 * 
	 * @Desc : 전시 카테고리 코너 셋 수정
	 * @Method Name : updateSet
	 * @Date : 2019. 3. 20.
	 * @Author : SANTA
	 * @param dpCategoryCornerSet
	 * @throws Exception
	 */
	public void updateSet(DpCategoryCornerSet dpCategoryCornerSet) throws Exception;

	/**
	 * 
	 * @Desc : 전시 카테고리 코너 셋 리스트 조회
	 * @Method Name : selectDisplayCategoryCornerSetList
	 * @Date : 2019. 3. 20.
	 * @Author : SANTA
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<DpCategoryCornerSet> selectDisplayCategoryCornerSetList(
			Pageable<DisplayContentsPopupVO, DpCategoryCornerSet> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 전시 카테고리 코너 셋 리스트 갯수 조회
	 * @Method Name : selectDisplayCategoryCornerSetListCount
	 * @Date : 2019. 3. 20.
	 * @Author : SANTA
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectDisplayCategoryCornerSetListCount(Pageable<DisplayContentsPopupVO, DpCategoryCornerSet> pageable)
			throws Exception;

	/**
	 * 
	 * @Desc : 전시 카테고리 코너셋 조회
	 * @Method Name : selectDisplayCategoryCornerSet
	 * @Date : 2019. 3. 28.
	 * @Author : SANTA
	 * @param dpCategoryCornerSet
	 * @return
	 * @throws Exception
	 */
	public DpCategoryCornerSet selectDisplayCategoryCornerSet(DisplayContentsPopupVO displayContentsPopupVO)
			throws Exception;

	/**
	 * 
	 * @Desc : 전시 카테고리 코너셋 삭제
	 * @Method Name : deleteDisplayCategoryCornerSet
	 * @Date : 2019. 3. 29.
	 * @Author : SANTA
	 * @param displayContentsPopupVO
	 * @throws Exception
	 */
	public void deleteDisplayCategoryCornerSet(DisplayContentsPopupVO displayContentsPopupVO) throws Exception;
}
