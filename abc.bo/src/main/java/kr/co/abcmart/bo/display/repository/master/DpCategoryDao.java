package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpCategory;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpCategoryDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpCategoryDao extends BaseDpCategoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpCategoryDao 클래스에 구현 되어있습니다.
	 * BaseDpCategoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 */

	public DpCategory selectByPrimaryKey(DpCategory dpCategory) throws Exception;

	/**
	 * @Desc : 전시 카테고리 기본 조회 쿼리
	 * @Method Name : selectDpCategory
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param dpCategory
	 * @return
	 */
	public DpCategory selectDpCategory(DpCategory dpCategory) throws Exception;

	/**
	 * @Desc : 전시 카테고리 목록 조회
	 * @Method Name : selectDpCategoryList
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param dpCategory
	 * @return
	 */
	public List<DpCategory> selectDpCategoryList(DpCategory dpCategory) throws Exception;

	/**
	 * @Desc : 전시 카테고리 목록 카운트 조회
	 * @Method Name : selectDpCategoryListForGridCount
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public int selectDpCategoryListForGridCount(Pageable<DpCategory, DpCategory> pageable) throws Exception;

	/**
	 * @Desc : 전시 카테고리 목록 조회
	 * @Method Name : selectDpCategoryListForGrid
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public List<DpCategory> selectDpCategoryListForGrid(Pageable<DpCategory, DpCategory> pageable) throws Exception;

	/**
	 * @Desc : 사용 중인 전시 템플릿 카운트 조회
	 * @Method Name : selectDpTmplCountInDisplayCtgr
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param dispTmplNo
	 * @return
	 */
	public int selectDpTmplCountInDisplayCtgr(String dispTmplNo) throws Exception;

	/**
	 * @Desc : 전시 카테고리 등록
	 * @Method Name : insertDpCategory
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param dpCategory
	 * @return
	 */
	public int insertDpCategory(DpCategory dpCategory) throws Exception;

	/**
	 *
	 * @Desc : 전시 카테고리 수정
	 * @Method Name : updateDpCategory
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param dpCategory
	 * @return
	 */
	public int updateDpCategory(DpCategory dpCategory) throws Exception;

	/**
	 * @Desc : 사용 중인 하위 전시 카테고리 카운트 조회
	 * @Method Name : selectActiveSubCategoryCount
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param dpCategory
	 * @return
	 */
	public int selectActiveSubCategoryCount(DpCategory dpCategory) throws Exception;

	/**
	 * @Desc : 표준 카테고리 정보 수정
	 * @Method Name : updateStdCtgrNo
	 * @Date : 2019. 11. 20.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateStdCtgrNo(DpCategory param) throws Exception;

	/**
	 * @Desc : 표준 카테고리와 연결된 카테고리 정보를 조회한다.
	 * @Method Name : selectCategoryListByStgCtgrNo
	 * @Date : 2019. 11. 21.
	 * @Author : kiowa
	 * @param stgCtgrNo : 표준카테고리 번호
	 * @return
	 * @throws Exception
	 */
	public List<DpCategory> selectCategoryListByStdCtgrNo(String stdCtgrNo) throws Exception;

	/**
	 * @Desc : 선택한 표준 카테고리에 맵핑 정보 초기화
	 * @Method Name : updateStdCtgrNoReset
	 * @Date : 2019. 12. 26.
	 * @Author : kiowa
	 * @param stdCtgrNo
	 * @return
	 * @throws Exception
	 */
	public int updateStdCtgrNoReset(String stdCtgrNo) throws Exception;

}
