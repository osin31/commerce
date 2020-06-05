package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpCategoryCornerName;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpCategoryCornerNameDao;
import kr.co.abcmart.bo.display.vo.DisplayContentsPopupVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpCategoryCornerNameDao extends BaseDpCategoryCornerNameDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpCategoryCornerNameDao 클래스에 구현 되어있습니다.
	 * BaseDpCategoryCornerNameDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public DpCategoryCornerName selectByPrimaryKey(DpCategoryCornerName dpCategoryCornerName) throws Exception;

	/**
	 * 
	 * @Desc : 전시 카테고리 코너명 리스트 조회
	 * @Method Name : selectDisplayCategoryCornerNameList
	 * @Date : 2019. 3. 18.
	 * @Author : SANTA
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<DpCategoryCornerName> selectDisplayCategoryCornerNameList(
			Pageable<DisplayContentsPopupVO, DpCategoryCornerName> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 전시 카테고리 코너명 리스트 샛수 조회
	 * @Method Name : selectDisplayCategoryCornerNameListCount
	 * @Date : 2019. 3. 18.
	 * @Author : SANTA
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectDisplayCategoryCornerNameListCount(Pageable<DisplayContentsPopupVO, DpCategoryCornerName> pageable)
			throws Exception;

	/**
	 * 
	 * @Desc : 전시 카테고리 코너명 조회
	 * @Method Name : selectCornerName
	 * @Date : 2019. 3. 19.
	 * @Author : SANTA
	 * @param displayContentsPopupVO
	 * @return
	 * @throws Exception
	 */
	public DpCategoryCornerName selectCornerName(DisplayContentsPopupVO displayContentsPopupVO) throws Exception;

	/**
	 * 
	 * @Desc : 전시 카테고리 코너명 수정
	 * @Method Name : updateName
	 * @Date : 2019. 3. 19.
	 * @Author : SANTA
	 * @param dpCategoryCornerName
	 * @return
	 * @throws Exception
	 */
	public int updateName(DpCategoryCornerName dpCategoryCornerName) throws Exception;

	/**
	 * 
	 * @Desc : 전시 카테고리 코너명 저장
	 * @Method Name : insertName
	 * @Date : 2019. 3. 25.
	 * @Author : SANTA
	 * @param dpCategoryCornerName
	 * @return
	 * @throws Exception
	 */
	public int insertName(DpCategoryCornerName dpCategoryCornerName) throws Exception;

	/**
	 * 
	 * @Desc : 전시 카테고리 코너명 삭제
	 * @Method Name : deleteDisplayCategoryCornerName
	 * @Date : 2019. 3. 29.
	 * @Author : SANTA
	 * @param displayContentsPopupVO
	 * @return
	 * @throws Exception
	 */
	public int deleteDisplayCategoryCornerName(DisplayContentsPopupVO displayContentsPopupVO) throws Exception;
}
