package kr.co.abcmart.bo.system.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.bo.system.repository.master.base.BaseSyAdminDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface SyAdminDao extends BaseSyAdminDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSyAdminDao 클래스에 구현 되어있습니다. BaseSyAdminDao는
	 * 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 * ※ 중 요 ※
	 * 
	 * sqlSession 은 다음 메소드를 이용 하여 호출 합니다. 본인이 쓰고 있는 sqlSession(DB 호출)이 어떤 것인지 명확 하게
	 * 알아 보기 위함입니다.
	 * 
	 * - getSqlSessionMaster()
	 */

	public SyAdmin selectByPrimaryKey(SyAdmin syAdmin) throws Exception;

	/***
	 * adminNo로 admin 사용자 계정 및 권한 정보 조회.
	 * 
	 * @param syAdmin
	 * @return SyAdmin
	 */
	public SyAdmin selectAdminAndAuthorityByPrimaryKey(SyAdmin syAdmin) throws Exception;

	/***
	 * LoginId로 admin 사용자 계정 및 권한 정보 조회.
	 * 
	 * @param syAdmin
	 * @return SyAdmin
	 */
	public SyAdmin selectAdminAndAuthorityByLoginId(SyAdmin syAdmin) throws Exception;

	/***
	 * 관리자 목록 카운터 호출
	 * 
	 * @param syAdmin
	 * @return int
	 */
	public int selectAdminListCount(Pageable<SyAdmin, SyAdmin> pageable) throws Exception;

	/***
	 * 관리자 목록 호출
	 * 
	 * @param syAdmin
	 * @return SyAdmin
	 */
	public List<SyAdmin> selectAdminList(Pageable<SyAdmin, SyAdmin> pageable) throws Exception;

	/***
	 * 관리자 중복 체크
	 * 
	 * @param syAdmin
	 * @return int
	 */
	public int selectCheckLoginId(SyAdmin sysAdmin) throws Exception;

	/***
	 * 관리자 등록
	 * 
	 * @param syAdmin
	 * @return
	 */
	public void insertAdmin(SyAdmin sysAdmin) throws Exception;

	/***
	 * 관리자 기본정보 조회
	 * 
	 * @param String
	 * @return
	 */
	public SyAdmin selectAdminDetailInfo(String adminNo) throws Exception;

	/***
	 * 관리자 기본정보 수정
	 * 
	 * @param syAdmin
	 * @return
	 */
	public int updateAdmin(SyAdmin sysAdmin) throws Exception;

	public List<SyAdmin> getSuperAdminNoList() throws Exception;

	/**
	 * @Desc :
	 * @Method Name : selectVndrNoInfo
	 * @Date : 2019. 8. 20.
	 * @Author : flychani@3top.co.kr
	 * @param syAdmin
	 * @return
	 */
	public SyAdmin selectVndrNoInfo(SyAdmin syAdmin) throws Exception;

}
