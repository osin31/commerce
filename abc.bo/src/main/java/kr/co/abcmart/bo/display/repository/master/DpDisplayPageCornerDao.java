package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpDisplayPageCorner;
import kr.co.abcmart.bo.display.model.master.DpDisplayTemplateCorner;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpDisplayPageCornerDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpDisplayPageCornerDao extends BaseDpDisplayPageCornerDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpDisplayPageCornerDao 클래스에 구현 되어있습니다.
	 * BaseDpDisplayPageCornerDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public DpDisplayPageCorner selectByPrimaryKey(DpDisplayPageCorner dpDisplayPageCorner) throws Exception;

	/**
	 * @Desc : 전시 페이지 코너 삭제
	 * @Method Name : deleteDpPageCorner
	 * @Date : 2019. 11. 15.
	 * @Author : lee
	 * @param dpDisplayPageCorner
	 * @throws Exception
	 */
	public void deleteDpPageCorner(DpDisplayPageCorner dpDisplayPageCorner) throws Exception;

	/**
	 * @Desc : 전시 페이지 코너 리스트 조회
	 * @Method Name : selectDpPageCornerList
	 * @Date : 2019. 11. 15.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<DpDisplayTemplateCorner> selectDpPageCornerList(
			Pageable<DpDisplayPageCorner, DpDisplayTemplateCorner> pageable) throws Exception;

	/**
	 * @Desc : 전시 페이지 코너 리스트 카운트 조회
	 * @Method Name : selectDpPageCornerListCount
	 * @Date : 2019. 11. 15.
	 * @Author : lee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectDpPageCornerListCount(Pageable<DpDisplayPageCorner, DpDisplayTemplateCorner> pageable)
			throws Exception;

	/**
	 * @Desc : 전시 콘텐츠관리에서 전시여부 수정
	 * @Method Name : updateDispYn
	 * @Date : 2019. 12. 16.
	 * @Author : sic
	 * @param dpDisplayPageCorner
	 * @throws Exception
	 */
	public void updateDispYn(DpDisplayPageCorner dpDisplayPageCorner) throws Exception;
}
