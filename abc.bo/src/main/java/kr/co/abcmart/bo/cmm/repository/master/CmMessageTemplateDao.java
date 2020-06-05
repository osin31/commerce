package kr.co.abcmart.bo.cmm.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.cmm.model.master.CmMessageTemplate;
import kr.co.abcmart.bo.cmm.repository.master.base.BaseCmMessageTemplateDao;
import kr.co.abcmart.bo.cmm.vo.MessageVO;
import kr.co.abcmart.bo.cmm.vo.MsgTemplateSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface CmMessageTemplateDao extends BaseCmMessageTemplateDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseCmMessageTemplateDao 클래스에 구현 되어있습니다.
	 * BaseCmMessageTemplateDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 */

	public CmMessageTemplate selectByPrimaryKey(CmMessageTemplate cmMessageTemplate) throws Exception;

	/**
	 * @Desc : 문자 템플릿 정보 등록
	 * @Method Name : insertCmMessageTemplate
	 * @Date : 2019. 3. 28.
	 * @Author : kiowa
	 * @param cmMessageTemplate
	 * @return
	 * @throws Exception
	 */
	public int insertCmMessageTemplate(CmMessageTemplate cmMessageTemplate) throws Exception;

	/**
	 * @Desc : 문자 템플릿 상세 정보 조회
	 * @Method Name : selectCmMessageTemplate
	 * @Date : 2019. 3. 28.
	 * @Author : kiowa
	 * @return
	 * @throws Exception
	 */
	public CmMessageTemplate selectCmMessageTemplate(CmMessageTemplate cmMessageTemplate) throws Exception;

	/**
	 * @Desc : SMS 템플릿 조회 개수
	 * @Method Name : selectCmMessageTemplateListCount
	 * @Date : 2019. 3. 13.
	 * @Author : kiowa
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectCmMessageTemplateListCount(Pageable<MsgTemplateSearchVO, CmMessageTemplate> pageable)
			throws Exception;

	/**
	 * @Desc : SMS 템플릿 조회 리스트
	 * @Method Name : selectCmMessageTemplateList
	 * @Date : 2019. 3. 13.
	 * @Author : kiowa
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<CmMessageTemplate> selectCmMessageTemplateList(
			Pageable<MsgTemplateSearchVO, CmMessageTemplate> pageable) throws Exception;

	/**
	 * @Desc : SMS 템플릿 조회 리스트 엑셀 다운
	 * @Method Name : selectCmMessageTemplateListExcelDown
	 * @Date : 2019. 4. 11.
	 * @Author : 이재렬
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<CmMessageTemplate> selectCmMessageTemplateListExcelDown(
			Pageable<MsgTemplateSearchVO, CmMessageTemplate> pageable) throws Exception;

	/**
	 * @Desc : 문자 템플릿 정보를 수정한다.
	 * @Method Name : updateCmMessageTemplate
	 * @Date : 2019. 3. 14.
	 * @Author : kiowa
	 * @param cmMessageTemplate
	 * @return
	 * @throws Exception
	 */
	public int updateCmMessageTemplate(CmMessageTemplate cmMessageTemplate) throws Exception;

	/**
	 * @Desc : 몬자 보내기 공통 팝업에서 문자 템플릿 조회
	 * @Method Name : selectTextMesgSendPopTemplateList
	 * @Date : 2019. 3. 20.
	 * @Author : kiowa
	 * @param msgTemplatetSearchVO
	 * @return
	 * @throws Exception
	 */
	public List<CmMessageTemplate> selectTextMesgSendPopTemplateList(MsgTemplateSearchVO msgTemplatetSearchVO)
			throws Exception;

	/**
	 * @Desc : 발송코드 중복확인
	 * @Method Name : selectCmMesgIdCount
	 * @Date : 2019. 7. 11.
	 * @Author : 고웅환
	 * @param mesgId
	 * @return
	 * @throws Exception
	 */
	public int selectCmMesgIdCount(String mesgId) throws Exception;

	public CmMessageTemplate selectMessageTemplateByMesgId(String mesgId) throws Exception;

	public CmMessageTemplate selectMessageTemplate(MessageVO messageVO) throws Exception;

}
