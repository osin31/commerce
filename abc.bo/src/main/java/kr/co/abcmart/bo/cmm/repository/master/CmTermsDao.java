package kr.co.abcmart.bo.cmm.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.cmm.model.master.CmTerms;
import kr.co.abcmart.bo.cmm.model.master.CmTermsDetail;
import kr.co.abcmart.bo.cmm.repository.master.base.BaseCmTermsDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface CmTermsDao extends BaseCmTermsDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseCmTermsDao 클래스에 구현 되어있습니다. BaseCmTermsDao는
	 * 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public CmTerms selectByPrimaryKey(CmTerms cmTerms) throws Exception;

	/**
	 * @Desc : 약관분류 코드 가져오기
	 * @Method Name : selectTermsClassCode
	 * @Date : 2019. 1. 31.
	 * @Author : 신인철
	 * @param codeField
	 * @return
	 * @throws Exception
	 */
	public List<CmTerms> selectTermsClassCode(String codeField) throws Exception;

	/**
	 * @Desc : 그리드 호출
	 * @Method Name : selectTermsGrid
	 * @Date : 2019. 1. 31.
	 * @Author : 신인철
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<CmTerms> selectTermsGrid(Pageable<CmTerms, CmTerms> param) throws Exception;

	/**
	 * @Desc : 그리드 항목 개수 체크
	 * @Method Name : selectTermsGridCount
	 * @Date : 2019. 1. 31.
	 * @Author : 신인철
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int selectTermsGridCount(Pageable<CmTerms, CmTerms> param) throws Exception;

	/**
	 * @Desc : 이용약관 등록
	 * @Method Name : insertTermsOfUse
	 * @Date : 2019. 1. 31.
	 * @Author : 신인철
	 * @param cmTerms
	 * @throws Exception
	 */
	public void insertTerms(CmTerms cmTerms) throws Exception;

	/**
	 * @Desc : 현재 적용중인 약관 가져오기
	 * @Method Name : selectDispTerms
	 * @Date : 2019. 5. 22.
	 * @Author : 신인철
	 * @param cmTerms
	 * @return
	 * @throws Exception
	 */
	public CmTermsDetail selectDispTerms(CmTerms cmTerms) throws Exception;

	/**
	 * @Desc : 약관 상세보기
	 * @Method Name : selectTermsDetail
	 * @Date : 2019. 1. 31.
	 * @Author : 신인철
	 * @param cmTerms
	 * @return
	 * @throws Exception
	 */
	public CmTerms selectTermsDetail(CmTerms cmTerms) throws Exception;

	/**
	 * @Desc : 약관 삭제
	 * @Method Name : deleteTerms
	 * @Date : 2019. 1. 31.
	 * @Author : 신인철
	 * @param cmTerms
	 * @throws Exception
	 */
	public void deleteTerms(CmTerms cmTerms) throws Exception;

}
