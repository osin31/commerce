package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpDisplayPage;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpDisplayPageDao;

@Mapper
public interface DpDisplayPageDao extends BaseDpDisplayPageDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpDisplayPageDao 클래스에 구현 되어있습니다.
	 * BaseDpDisplayPageDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public DpDisplayPage selectByPrimaryKey(DpDisplayPage dpDisplayPage) throws Exception;

	/**
	 * @Desc : 전시 페이지 목록 조회
	 * @Method Name : selectDpDisplayPageList
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param dpDisplayPage
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public List<DpDisplayPage> selectDpDisplayPageList(DpDisplayPage dpDisplayPage) throws Exception;

	/**
	 * @Desc : 전시페이지 목록 조회
	 * @Method Name : selectDisplayPageList
	 * @Date : 2019. 12. 12.
	 * @Author : sic
	 * @param dpDisplayPage
	 * @return
	 * @throws Exception
	 */
	public List<DpDisplayPage> selectDisplayPageList(DpDisplayPage dpDisplayPage) throws Exception;

	/**
	 * @Desc : 사용 중인 전시 템플릿 카운트 조회
	 * @Method Name : selectDpTmplCountInDisplayPage
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param dispTmplNo
	 * @return
	 * @throws Exception
	 */
	public int selectDpTmplCountInDisplayPage(String dispTmplNo) throws Exception;

	/**
	 * @Desc : 전시 페이지 등록
	 * @Method Name : insertDpDisplayPage
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param dpDisplayPage
	 * @throws Exception
	 */
	public void insertDpDisplayPage(DpDisplayPage dpDisplayPage) throws Exception;

	/**
	 * @Desc : 전시 페이지 수정
	 * @Method Name : updateDpDisplayPage
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param dpDisplayPage
	 * @throws Exception
	 */
	public void updateDpDisplayPage(DpDisplayPage dpDisplayPage) throws Exception;

}
