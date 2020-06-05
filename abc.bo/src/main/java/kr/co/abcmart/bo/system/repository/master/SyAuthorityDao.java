package kr.co.abcmart.bo.system.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import kr.co.abcmart.bo.system.repository.master.base.BaseSyAuthorityDao;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.bo.system.model.master.SyAuthority;

@Mapper
public interface SyAuthorityDao extends BaseSyAuthorityDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BaseSyAuthorityDao 클래스에 구현 되어있습니다.
     * BaseSyAuthorityDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
	 *	※ 중 요 ※
     * 
     *     sqlSession 은 다음 메소드를 이용 하여 호출 합니다. 본인이 쓰고 있는 sqlSession(DB 호출)이 어떤 것인지 
     *     명확 하게 알아 보기 위함입니다. 
     * 
     *       - getSqlSessionMaster()
     */

	public SyAuthority selectByPrimaryKey(SyAuthority syAuthority) throws Exception;

	/**
	 * 권한 전체 목록을 가져온다.
	 * @return List<String>
	 */
	public List<String> getAllRoles();
	
	public List<SyAuthority> selectAuthorytyList(SyAuthority params);

	public int selectAuthorityGroupListCount(Pageable<SyAuthority, SyAuthority> pageable) throws Exception;

	public List<SyAuthority> selectAuthorityGroupList(Pageable<SyAuthority, SyAuthority> pageable) throws Exception;

	public int insertAuthorityGroup(SyAuthority params) throws Exception;

	public int updateAuthorityGroup(SyAuthority params) throws Exception;

	public int selectAdminCount(SyAuthority params) throws Exception;

}
