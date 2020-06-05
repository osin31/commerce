package kr.co.abcmart.bo.cmm.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.cmm.model.master.CmCounselScript;
import kr.co.abcmart.bo.cmm.repository.master.base.BaseCmCounselScriptDao;
import kr.co.abcmart.bo.cmm.vo.CounselScriptSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface CmCounselScriptDao extends BaseCmCounselScriptDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseCmCounselScriptDao 클래스에 구현 되어있습니다.
	 * BaseCmCounselScriptDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 */

	public CmCounselScript selectByPrimaryKey(CmCounselScript CmCounselScript) throws Exception;

	/**
	 * @Desc : 상담스크립트 검색 조회결과 개수
	 * @Method Name : selectBdMemberCounselListCount
	 * @Date : 2019. 2. 11.
	 * @Author : kiowa
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectCmCounselScriptListCount(Pageable<CounselScriptSearchVO, CmCounselScript> pageable)
			throws Exception;

	/**
	 * 상담스크립트 검색 조회결과
	 *
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<CmCounselScript> selectCmCounselScriptList(Pageable<CounselScriptSearchVO, CmCounselScript> pageable)
			throws Exception;

	/**
	 * 상담스크립트 상세 정보
	 *
	 * @param CmCounselScript
	 * @return
	 * @throws Exception
	 */
	public CmCounselScript selectCmCounselScript(CmCounselScript cmCounselScript) throws Exception;

	/**
	 * 상담스크립트 정보를 등록한다.
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertCmCounselScript(CmCounselScript cmCounselScript) throws Exception;

	/**
	 * 상담스크립트 정보를 수정한다.
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateCmCounselScript(CmCounselScript cmCounselScript) throws Exception;

	/**
	 * 상담스크립트 정보를 삭제한다.
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int deleteCmCounselScript(CmCounselScript cmCounselScript) throws Exception;

	/**
	 * @Desc : 답변으로 사용된 상담스크립트 조회
	 * @Method Name : selectAswrCnslScript
	 * @Date : 2019. 4. 19.
	 * @Author : 신인철
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public CmCounselScript selectAswrCnslScript(String param) throws Exception;

	/**
	 * 선택한 유형의 상담 스크립트 내용을 조회한다.
	 *
	 * @param counselScriptSearchVO
	 * @return
	 * @throws Exception
	 */
	public List<CmCounselScript> selectCounselScriptTitle(CmCounselScript cmCounselScript) throws Exception;

}
