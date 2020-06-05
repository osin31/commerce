package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpDisplayTemplate;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpDisplayTemplateDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpDisplayTemplateDao extends BaseDpDisplayTemplateDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpDisplayTemplateDao 클래스에 구현 되어있습니다.
	 * BaseDpDisplayTemplateDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public DpDisplayTemplate selectByPrimaryKey(DpDisplayTemplate dpDisplayTemplate) throws Exception;

	/**
	 * 
	 * @Desc : 전시 템플릿 저장
	 * @Method Name : insertTemplate
	 * @Date : 2019. 2. 21.
	 * @Author : SANTA
	 * @param dpDisplayTemplate
	 * @throws Exception
	 */
	public void insertTemplate(DpDisplayTemplate dpDisplayTemplate) throws Exception;

	/**
	 * 
	 * @Desc : 전시 템플릿 조회
	 * @Method Name : selectTemplatList
	 * @Date : 2019. 2. 21.
	 * @Author : SANTA
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<DpDisplayTemplate> selectTemplateList(Pageable<DpDisplayTemplate, DpDisplayTemplate> pageable)
			throws Exception;

	/**
	 * 
	 * @Desc : 전시 템플릿 갯수 조회
	 * @Method Name : selectTemplateListCount
	 * @Date : 2019. 2. 21.
	 * @Author : SANTA
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectTemplateListCount(Pageable<DpDisplayTemplate, DpDisplayTemplate> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 전시 템플릿 조회
	 * @Method Name : selectTemplate
	 * @Date : 2019. 2. 22.
	 * @Author : SANTA
	 * @param dpDisplayTemplate
	 * @return
	 * @throws Exception
	 */
	public DpDisplayTemplate selectTemplate(DpDisplayTemplate dpDisplayTemplate) throws Exception;

	/**
	 * 
	 * @Desc : 전시 템플릿 수정
	 * @Method Name : updateTemplate
	 * @Date : 2019. 2. 25.
	 * @Author : SANTA
	 * @param dpDisplayTemplate
	 * @throws Exception
	 */
	public void updateTemplate(DpDisplayTemplate dpDisplayTemplate) throws Exception;
}
