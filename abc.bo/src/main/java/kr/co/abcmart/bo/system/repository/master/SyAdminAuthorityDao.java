package kr.co.abcmart.bo.system.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import kr.co.abcmart.bo.system.repository.master.base.BaseSyAdminAuthorityDao;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.bo.system.model.master.SyAdminAuthority;
import kr.co.abcmart.bo.system.model.master.SyAuthority;
import kr.co.abcmart.bo.system.model.master.SyAuthorityMenu;

@Mapper
public interface SyAdminAuthorityDao extends BaseSyAdminAuthorityDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BaseSyAdminAuthorityDao 클래스에 구현 되어있습니다.
     * BaseSyAdminAuthorityDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
	 *	※ 중 요 ※
     * 
     *     sqlSession 은 다음 메소드를 이용 하여 호출 합니다. 본인이 쓰고 있는 sqlSession(DB 호출)이 어떤 것인지 
     *     명확 하게 알아 보기 위함입니다. 
     * 
     *       - getSqlSessionMaster()
     */

	public SyAdminAuthority selectByPrimaryKey(SyAdminAuthority syAdminAuthority) throws Exception;
	
	/***
	 * 관리자 권한 등록
	 * @param SyAdminAuthority
	 * @return 
	 */
	public void insertAdminAuthority(SyAdminAuthority syAdminAuthority) throws Exception;
	
	/***
	 * 관리자 권한 수정
	 * @param SyAdminAuthority
	 * @return 
	 */
	public void updataAdminAuthority(SyAdminAuthority syAdminAuthority) throws Exception;


}
