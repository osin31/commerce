package kr.co.abcmart.bo.board.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.board.model.master.BdMemberCounsel;
import kr.co.abcmart.bo.board.repository.master.base.BaseBdMemberCounselDao;
import kr.co.abcmart.bo.board.vo.BdInquirySearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface BdMemberCounselDao extends BaseBdMemberCounselDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseBdMemberCounselDao 클래스에 구현 되어있습니다.
	 * BaseBdMemberCounselDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 */

	public BdMemberCounsel selectByPrimaryKey(BdMemberCounsel bdMemberCounsel) throws Exception;

	/**
	 * @Desc : 문의 검색 조회결과 개수
	 * @Method Name : selectBdMemberCounselListCount
	 * @Date : 2019. 2. 18.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectBdMemberCounselListCount(Pageable<BdInquirySearchVO, BdMemberCounsel> pageable) throws Exception;

	/**
	 * @Desc : 문의 검색 조회결과
	 * @Method Name : selectBdMemberCounselList
	 * @Date : 2019. 2. 18.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<BdMemberCounsel> selectBdMemberCounselList(Pageable<BdInquirySearchVO, BdMemberCounsel> pageable)
			throws Exception;

	/**
	 * @Desc : 문의 정보를 조회한다.
	 * @Method Name : selectBdMemberCounsel
	 * @Date : 2019. 2. 18.
	 * @Author : 신인철
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public BdMemberCounsel selectBdMemberCounsel(BdMemberCounsel param) throws Exception;

	/**
	 * @Desc : 문의 정보를 수정한다.
	 * @Method Name : updatebdMemberCounsel
	 * @Date : 2019. 2. 18.
	 * @Author : 신인철
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void updatebdMemberCounsel(BdMemberCounsel param) throws Exception;

	/**
	 * @Desc : 미답변인 문의사항을 조회한다.
	 * @Method Name : selectNoAswrCounselList
	 * @Date : 2019. 4. 24.
	 * @Author : 신인철
	 * @param param
	 * @throws Exception
	 */
	public List<BdMemberCounsel> selectNoAswrCounselList(BdMemberCounsel param) throws Exception;

	/**
	 * @Desc : 회원 문의 현황 조회
	 * @Method Name : selectInquiryStatData
	 * @Date : 2019. 2. 27.
	 * @Author : 이동엽
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public BdMemberCounsel selectInquiryStatData(BdMemberCounsel bdMemberCounsel) throws Exception;

	/**
	 * @Desc : 회원 문의 등록
	 * @Method Name : insertMemberCounsel
	 * @Date : 2019. 3. 4.
	 * @Author : 이동엽
	 * @param bdMemberCounsel
	 * @throws Exception
	 */
	public void insertMemberCounsel(BdMemberCounsel bdMemberCounsel) throws Exception;

	/**
	 * @Desc : 문의 정보를 조회한다.
	 * @Method Name : selectIndividualInquiryDetail
	 * @Date : 2019. 4. 17.
	 * @Author : 고웅환
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public BdMemberCounsel selectIndividualInquiryDetail(BdMemberCounsel param) throws Exception;

	/**
	 * @Desc : BO 대시보드 1:1 문의 미답변 건수 그룹별 조회
	 * @Method Name : selectInquiryGroupCount
	 * @Date : 2019. 5. 8.
	 * @Author : 고웅환
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<BdMemberCounsel> selectInquiryGroupCount(BdMemberCounsel bdMemberCounsel) throws Exception;

	/************************************************************************************************************************************************************/
	/**
	 * @Desc :
	 * @Method Name : selectOrderiquiryArr
	 * @Date : 2019. 3. 11.
	 * @Author : flychani@3top.co.kr
	 * @param orderNos
	 * @return
	 */
	public List<BdMemberCounsel> selectOrderiquiryArr(String[] orderNos);

	/**
	 * @Desc : BO 대시보드 고객의소리 미답변 건수 그룹별 조회
	 * @Method Name : selectNoAswrVoiceCustom
	 * @Date : 2019. 5. 16.
	 * @Author : 이재렬
	 * @param
	 * @throws Exception
	 */
	public int selectNoAswrVoiceCustom(BdMemberCounsel bdMemberCounsel) throws Exception;

	/**
	 * @Desc : po 대시보드 1:1문의 미답변 건수 조회
	 * @Method Name : selectNoAswrCounselListForPo
	 * @Date : 2019. 6. 12.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	public int selectNoAswrCounselListForPo(BdMemberCounsel bdMemberCounsel) throws Exception;

	/**
	 * @Desc : 관리대상 업체 리스트 조회
	 * @Method Name : selectManagementVndrList
	 * @Date : 2019. 10. 14.
	 * @Author : sic
	 * @return
	 * @throws Exception
	 */
	public List<BdMemberCounsel> selectManagementVndrList() throws Exception;
	
	public List<BdMemberCounsel> selectCounselExcelList(BdInquirySearchVO param) throws Exception;

}
