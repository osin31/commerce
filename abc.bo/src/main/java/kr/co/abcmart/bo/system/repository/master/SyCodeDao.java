package kr.co.abcmart.bo.system.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.system.model.master.SyCode;
import kr.co.abcmart.bo.system.repository.master.base.BaseSyCodeDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface SyCodeDao extends BaseSyCodeDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSyCodeDao 클래스에 구현 되어있습니다. BaseSyCodeDao는
	 * 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */
	public SyCode selectByPrimaryKey(SyCode syCode) throws Exception;

	/**
	 * @Desc : 코드그룹 개수 체크
	 * @Method Name : selectCodeListCount
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int selectCodeListCount(Pageable<SyCode, SyCode> param) throws Exception;

	/**
	 * @Desc : 코드그룹 전체 리스트
	 * @Method Name : selectCodeGroup
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<SyCode> selectCodeGroup(Pageable<SyCode, SyCode> params) throws Exception;

	/**
	 * @Desc : 코드그룹 삭제
	 * @Method Name : deleteCodeGroup
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param params
	 * @throws Exception
	 */
	public void deleteCodeGroup(SyCode params) throws Exception;

	/**
	 * @Desc : 코드그룹 수정
	 * @Method Name : updateCodeGroup
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param params
	 * @throws Exception
	 */
	public void updateCodeGroup(SyCode params) throws Exception;

	/**
	 * @Desc : 중복되는 코드필드 있나 확인
	 * @Method Name : selectOverlapCodefield
	 * @Date : 2019. 7. 25.
	 * @Author : sic
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int selectOverlapCodefield(SyCode params) throws Exception;

	/**
	 * @Desc : 코드그룹 등록
	 * @Method Name : insertCodeGroup
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param params
	 * @throws Exception
	 */
	public void insertCodeGroup(SyCode params) throws Exception;

	/**
	 * @Desc : 코드그룹 상세조회
	 * @Method Name : selectGroupDetail
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<SyCode> selectGroupDetail(SyCode params) throws Exception;

	/**
	 * @Desc : 현재 코드그룹의 최대값 + 1
	 * @Method Name : selectMaxSortSeq
	 * @Date : 2019. 6. 7.
	 * @Author : 신인철
	 * @return
	 * @throws Exception
	 */
	public int selectMaxSortSeq() throws Exception;

}
