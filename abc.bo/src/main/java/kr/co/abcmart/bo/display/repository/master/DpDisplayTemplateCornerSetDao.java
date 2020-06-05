package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpDisplayTemplateCorner;
import kr.co.abcmart.bo.display.model.master.DpDisplayTemplateCornerSet;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpDisplayTemplateCornerSetDao;

@Mapper
public interface DpDisplayTemplateCornerSetDao extends BaseDpDisplayTemplateCornerSetDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpDisplayTemplateCornerSetDao 클래스에 구현
	 * 되어있습니다. BaseDpDisplayTemplateCornerSetDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당
	 * 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public DpDisplayTemplateCornerSet selectByPrimaryKey(DpDisplayTemplateCornerSet dpDisplayTemplateCornerSet)
			throws Exception;

	/**
	 * 
	 * @Desc : 전시 템플릿 코너 셋 저장
	 * @Method Name : insertTemplateCornerSet
	 * @Date : 2019. 2. 27.
	 * @Author : SANTA
	 * @param dpDisplayTemplateCornerSet
	 * @return
	 * @throws Exception
	 */
	public int insertTemplateCornerSet(DpDisplayTemplateCornerSet dpDisplayTemplateCornerSet) throws Exception;

	/**
	 * 
	 * @Desc : 전시 템플릿 코너 셋 리스트 조회
	 * @Method Name : selectTemplateCornerSet
	 * @Date : 2019. 2. 28.
	 * @Author : SANTA
	 * @param dpDisplayTemplateCornerSet
	 * @return
	 * @throws Exception
	 */
	public List<DpDisplayTemplateCornerSet> selectTemplateCornerSet(
			DpDisplayTemplateCornerSet dpDisplayTemplateCornerSet) throws Exception;

	/**
	 * 
	 * @Desc : 코너 세트 삭제
	 * @Method Name : deleteCornerSetByDispTmplCornerSeqArr
	 * @Date : 2019. 3. 4.
	 * @Author : SANTA
	 * @param dpDisplayTemplateCorner
	 * @throws Exception
	 */
	public void deleteCornerSetByDispTmplCornerSeqArr(DpDisplayTemplateCorner dpDisplayTemplateCorner) throws Exception;
}
