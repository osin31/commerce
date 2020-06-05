package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpDisplayPageCornerName;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpDisplayPageCornerNameDao;
import kr.co.abcmart.bo.display.vo.DisplayContentsPopupVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpDisplayPageCornerNameDao extends BaseDpDisplayPageCornerNameDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpDisplayPageCornerNameDao 클래스에 구현 되어있습니다.
	 * BaseDpDisplayPageCornerNameDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public DpDisplayPageCornerName selectByPrimaryKey(DpDisplayPageCornerName dpDisplayPageCornerName) throws Exception;

	/**
	 * 
	 * @Desc : 전시 페이지 코너명 리스트 조회
	 * @Method Name : selectDisplayPageCornerNameList
	 * @Date : 2019. 4. 1.
	 * @Author : SANTA
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<DpDisplayPageCornerName> selectDisplayPageCornerNameList(
			Pageable<DisplayContentsPopupVO, DpDisplayPageCornerName> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 전시 페이지 코너명 리스트 갯수 조회
	 * @Method Name : selectDisplayPageCornerNameListCount
	 * @Date : 2019. 4. 1.
	 * @Author : SANTA
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectDisplayPageCornerNameListCount(Pageable<DisplayContentsPopupVO, DpDisplayPageCornerName> pageable)
			throws Exception;

	/**
	 * 
	 * @Desc : 전시 페이지 코너명 조회
	 * @Method Name : selectCornerName
	 * @Date : 2019. 3. 19.
	 * @Author : SANTA
	 * @param displayContentsPopupVO
	 * @return
	 * @throws Exception
	 */
	public DpDisplayPageCornerName selectCornerName(DisplayContentsPopupVO displayContentsPopupVO) throws Exception;

	/**
	 * 
	 * @Desc : 전시 페이지 코너명 수정
	 * @Method Name : updateName
	 * @Date : 2019. 3. 19.
	 * @Author : SANTA
	 * @param DpDisplayPageCornerName
	 * @return
	 * @throws Exception
	 */
	public int updateName(DpDisplayPageCornerName dpDisplayPageCornerName) throws Exception;

	/**
	 * 
	 * @Desc : 전시 페이지 코너명 저장
	 * @Method Name : insertName
	 * @Date : 2019. 3. 25.
	 * @Author : SANTA
	 * @param DpDisplayPageCornerName
	 * @return
	 * @throws Exception
	 */
	public int insertName(DpDisplayPageCornerName dpDisplayPageCornerName) throws Exception;

	/**
	 * 
	 * @Desc : 전시 페이지 코너명 삭제
	 * @Method Name : deleteDisplayCategoryCornerName
	 * @Date : 2019. 3. 29.
	 * @Author : SANTA
	 * @param displayContentsPopupVO
	 * @return
	 * @throws Exception
	 */
	public int deleteDisplayPageCornerName(DisplayContentsPopupVO displayContentsPopupVO) throws Exception;

}
