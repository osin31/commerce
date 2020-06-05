package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpDisplayTemplateCorner;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpDisplayTemplateCornerDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpDisplayTemplateCornerDao extends BaseDpDisplayTemplateCornerDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpDisplayTemplateCornerDao 클래스에 구현 되어있습니다.
	 * BaseDpDisplayTemplateCornerDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public DpDisplayTemplateCorner selectByPrimaryKey(DpDisplayTemplateCorner dpDisplayTemplateCorner) throws Exception;

	/**
	 * 
	 * @Desc : 템픞릿 코너 저장
	 * @Method Name : insertTemplateCorner
	 * @Date : 2019. 2. 27.
	 * @Author : SANTA
	 * @param dpDisplayTemplateCorner
	 * @return
	 * @throws Exception
	 */
	public int insertTemplateCorner(DpDisplayTemplateCorner dpDisplayTemplateCorner) throws Exception;

	/**
	 * 
	 * @Desc : 템플릿 코너 리스트 조회
	 * @Method Name : selectTemplateCornerList
	 * @Date : 2019. 2. 27.
	 * @Author : SANTA
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<DpDisplayTemplateCorner> selectTemplateCornerList(
			Pageable<DpDisplayTemplateCorner, DpDisplayTemplateCorner> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 템플릿 코너 리스트 카운트 조회
	 * @Method Name : selectTemplateCornerListCount
	 * @Date : 2019. 2. 27.
	 * @Author : SANTA
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectTemplateCornerListCount(Pageable<DpDisplayTemplateCorner, DpDisplayTemplateCorner> pageable)
			throws Exception;

	/**
	 * 
	 * @Desc : 코너 삭제
	 * @Method Name : deleteCornerByDispTmplCornerSeqArr
	 * @Date : 2019. 3. 4.
	 * @Author : SANTA
	 * @param dpDisplayTemplateCorner
	 * @throws Exception
	 */
	public void deleteCornerByDispTmplCornerSeqArr(DpDisplayTemplateCorner dpDisplayTemplateCorner) throws Exception;

	/**
	 * 
	 * @Desc : 코너 수정
	 * @Method Name : updateTemplCornerArr
	 * @Date : 2019. 7. 3.
	 * @Author : bje0507
	 * @param dpDisplayTemplateCorner
	 * @return
	 * @throws Exception
	 */
	public int updateTemplCornerArr(DpDisplayTemplateCorner dpDisplayTemplateCorner) throws Exception;

}
