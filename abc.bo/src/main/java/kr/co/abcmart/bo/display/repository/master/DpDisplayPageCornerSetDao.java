package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpDisplayPageCornerSet;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpDisplayPageCornerSetDao;
import kr.co.abcmart.bo.display.vo.DisplayContentsPopupVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpDisplayPageCornerSetDao extends BaseDpDisplayPageCornerSetDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpDisplayPageCornerSetDao 클래스에 구현 되어있습니다.
	 * BaseDpDisplayPageCornerSetDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public DpDisplayPageCornerSet selectByPrimaryKey(DpDisplayPageCornerSet dpDisplayPageCornerSet) throws Exception;

	/**
	 * 
	 * @Desc : 전시 페이지 코너 셋 저장
	 * @Method Name : insertSet
	 * @Date : 2019. 3. 21.
	 * @Author : SANTA
	 * @param DpDisplayPageCornerSet
	 * @throws Exception
	 */
	public void insertSet(DpDisplayPageCornerSet dpDisplayPageCornerSet) throws Exception;

	/**
	 * 
	 * @Desc : 전시 페이지 코너 셋 수정
	 * @Method Name : updateSet
	 * @Date : 2019. 3. 20.
	 * @Author : SANTA
	 * @param DpDisplayPageCornerSet
	 * @throws Exception
	 */
	public void updateSet(DpDisplayPageCornerSet dpDisplayPageCornerSet) throws Exception;

	/**
	 * 
	 * @Desc : 전시 페이지 코너 셋 리스트 조회
	 * @Method Name : selectDisplayPageCornerSetList
	 * @Date : 2019. 3. 20.
	 * @Author : SANTA
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<DpDisplayPageCornerSet> selectDisplayPageCornerSetList(
			Pageable<DisplayContentsPopupVO, DpDisplayPageCornerSet> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 전시 페이지 코너 셋 리스트 갯수 조회
	 * @Method Name : selectDisplayPageCornerSetListCount
	 * @Date : 2019. 3. 20.
	 * @Author : SANTA
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectDisplayPageCornerSetListCount(Pageable<DisplayContentsPopupVO, DpDisplayPageCornerSet> pageable)
			throws Exception;

	/**
	 * 
	 * @Desc : 전시 페이지 코너셋 조회
	 * @Method Name : selectDisplayPageCornerSet
	 * @Date : 2019. 3. 28.
	 * @Author : SANTA
	 * @param DpDisplayPageCornerSet
	 * @return
	 * @throws Exception
	 */
	public DpDisplayPageCornerSet selectDisplayPageCornerSet(DisplayContentsPopupVO displayContentsPopupVO)
			throws Exception;

	/**
	 * 
	 * @Desc : 전시 페이지 코너셋 삭제
	 * @Method Name : deleteDisplayPageCornerSet
	 * @Date : 2019. 3. 29.
	 * @Author : SANTA
	 * @param displayContentsPopupVO
	 * @throws Exception
	 */
	public void deleteDisplayPageCornerSet(DisplayContentsPopupVO displayContentsPopupVO) throws Exception;
}
