package kr.co.abcmart.bo.cmm.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.cmm.model.master.CmEmailTemplate;
import kr.co.abcmart.bo.cmm.repository.master.base.BaseCmEmailTemplateDao;
import kr.co.abcmart.bo.cmm.vo.EmailTemplateSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface CmEmailTemplateDao extends BaseCmEmailTemplateDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseCmEmailTemplateDao 클래스에 구현 되어있습니다.
	 * BaseCmEmailTemplateDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 */

	public CmEmailTemplate selectByPrimaryKey(CmEmailTemplate cmEmailTemplate) throws Exception;

	/**
	 * @Desc : 이메일 템플릿 상세 조회
	 * @Method Name : selectCmEmailTemplateDetail
	 * @Date : 2019. 3. 22.
	 * @Author : kiowa
	 * @param cmEmailTemplate
	 * @return
	 * @throws Exception
	 */
	public CmEmailTemplate selectCmEmailTemplateDetail(CmEmailTemplate cmEmailTemplate) throws Exception;

	/**
	 * @Desc : 이메일 Key 템플릿 정보를 등록한다.
	 * @Method Name : insertCmEmailTemplate
	 * @Date : 2019. 3. 29.
	 * @Author : kiowa
	 * @param cmEmailTemplate
	 * @return
	 * @throws Exception
	 */
	public int insertCmEmailTemplate(CmEmailTemplate cmEmailTemplate) throws Exception;

	/**
	 * @Desc : 이메일 템플릿 조회 개수
	 * @Method Name : selectCmMessageTemplateListCount
	 * @Date : 2019. 3. 13.
	 * @Author : kiowa
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectCmEmailTemplateListCount(Pageable<EmailTemplateSearchVO, CmEmailTemplate> pageable)
			throws Exception;

	/**
	 * @Desc : 이메일 템플릿 조회 리스트
	 * @Method Name : selectCmMessageTemplateList
	 * @Date : 2019. 3. 13.
	 * @Author : kiowa
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<CmEmailTemplate> selectCmEmailTemplateList(Pageable<EmailTemplateSearchVO, CmEmailTemplate> pageable)
			throws Exception;

	/**
	 * @Desc : 이메일 템플릿 정보를 수정한다
	 * @Method Name : updateCmEmailTemplate
	 * @Date : 2019. 3. 18.
	 * @Author : kiowa
	 * @param cmEmailTemplate
	 * @return
	 * @throws Exception
	 */
	public int updateCmEmailTemplate(CmEmailTemplate cmEmailTemplate) throws Exception;

	/**
	 * @Desc : 메일 보내기 공통 팝업에서 이메일 템플릿 조회
	 * @Method Name : selectEmailSendPopTemplateList
	 * @Date : 2019. 3. 18.
	 * @Author : kiowa
	 * @param emailTemplatetSearchVO
	 * @return
	 * @throws Exception
	 */
	public List<CmEmailTemplate> selectEmailSendPopTemplateList(EmailTemplateSearchVO emailTemplatetSearchVO)
			throws Exception;

	/**
	 * @Desc : emailId에 해당하는 템플릿 조회
	 * @Method Name : selectCmEmailTemplateByEmailId
	 * @Date : 2019. 3. 25.
	 * @Author : Kimyounghyun
	 * @param emailId
	 * @return
	 */
	public CmEmailTemplate selectCmEmailTemplateByEmailId(String emailId);

	/**
	 * 
	 * @Desc : 이메일 ID 중복 데이터 조회
	 * @Method Name : selectCmEmailIdCount
	 * @Date : 2019. 4. 12.
	 * @Author : 고웅환
	 * @param String
	 * @return int
	 * @throws Exception
	 */
	public int selectCmEmailIdCount(String emailId) throws Exception;
}
