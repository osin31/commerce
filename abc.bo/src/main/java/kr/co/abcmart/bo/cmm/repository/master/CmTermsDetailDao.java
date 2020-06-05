package kr.co.abcmart.bo.cmm.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.cmm.model.master.CmTermsDetail;
import kr.co.abcmart.bo.cmm.repository.master.base.BaseCmTermsDetailDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface CmTermsDetailDao extends BaseCmTermsDetailDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseCmTermsDetailDao 클래스에 구현 되어있습니다.
	 * BaseCmTermsDetailDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public CmTermsDetail selectByPrimaryKey(CmTermsDetail cmTermsDetail) throws Exception;

	/**
	 * @Desc : 이용약관설정 그리드
	 * @Method Name : selectTermsGrid
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<CmTermsDetail> selectTermsGrid(Pageable<CmTermsDetail, CmTermsDetail> params) throws Exception;

	/**
	 * @Desc : 이용약관 등록
	 * @Method Name : insertTermsOfUse
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param cmTermsDetail
	 * @throws Exception
	 */
	public void insertTermsDetail(CmTermsDetail cmTermsDetail) throws Exception;

	/**
	 * @Desc : 이용약관 상세보기
	 * @Method Name : selectTermsOfUseDetail
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param cmTermsDetail
	 * @return
	 * @throws Exception
	 */
	public CmTermsDetail selectTermsOfUseDetail(CmTermsDetail cmTermsDetail) throws Exception;

	/**
	 * @Desc : 회원가입동의, 주문동의 약관 상세보기 리스트
	 * @Method Name : selectTermsDetailList
	 * @Date : 2019. 2. 7.
	 * @Author : 신인철
	 * @param cmTermsDetail
	 * @return
	 * @throws Exception
	 */
	public List<CmTermsDetail> selectTermsDetailList(CmTermsDetail cmTermsDetail) throws Exception;

	/**
	 * @Desc : 약관 삭제
	 * @Method Name : deleteTerms
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param cmTermsDetail
	 * @throws Exception
	 */
	public void deleteTerms(CmTermsDetail cmTermsDetail) throws Exception;

	/**
	 * @Desc : 회원가입동의 상세보기 영역 호출
	 * @Method Name : selectSignUpDetail
	 * @Date : 2019. 2. 11.
	 * @Author : 신인철
	 * @param cmTermsDetail
	 * @return
	 * @throws Exception
	 */
	public CmTermsDetail selectSignUpOrderDetail(CmTermsDetail cmTermsDetail) throws Exception;

	/**
	 * @Desc : 당일 수정시에 Detail 전체 dispYn 수정
	 * @Method Name : updateTermsDetail
	 * @Date : 2019. 5. 23.
	 * @Author : 신인철
	 * @param cmTermsDetail
	 * @return
	 * @throws Exception
	 */
	public void updateTermsDetail(CmTermsDetail cmTermsDetail) throws Exception;

	/**
	 * @Desc : 개인정보방침 등록시에 추가로 들어갈 테이블 키값
	 * @Method Name : selectPrivacyKey
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param cmTermsDetail
	 * @return
	 * @throws Exception
	 */
	public int selectPrivacyKey(CmTermsDetail cmTermsDetail) throws Exception;

}
