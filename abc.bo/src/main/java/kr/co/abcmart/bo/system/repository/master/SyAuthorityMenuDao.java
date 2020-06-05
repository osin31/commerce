package kr.co.abcmart.bo.system.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.system.model.master.SyAuthorityMenu;
import kr.co.abcmart.bo.system.repository.master.base.BaseSyAuthorityMenuDao;

@Mapper
public interface SyAuthorityMenuDao extends BaseSyAuthorityMenuDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSyAuthorityMenuDao 클래스에 구현 되어있습니다.
	 * BaseSyAuthorityMenuDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 * ※ 중 요 ※
	 *
	 * sqlSession 은 다음 메소드를 이용 하여 호출 합니다. 본인이 쓰고 있는 sqlSession(DB 호출)이 어떤 것인지 명확 하게
	 * 알아 보기 위함입니다.
	 *
	 * - getSqlSessionMaster()
	 */

	public SyAuthorityMenu selectByPrimaryKey(SyAuthorityMenu syAuthorityMenu) throws Exception;

	/***
	 * 권한 마다 특정 페이지에 접근 할 수 있도록, URL별 접근 권한 설정 정보를 불러온다.
	 *
	 * @param roleId
	 * @return
	 */
	public List<SyAuthorityMenu> getAuthorizedUrls(String roleId);

	public int updateAuthorityGroupMenu(SyAuthorityMenu param) throws Exception;

	public int selectAuthorityGroupMenuCount(SyAuthorityMenu params) throws Exception;

	public List<SyAuthorityMenu> selectAuthorityGroupMenuList(SyAuthorityMenu params) throws Exception;

	public void deleteMenu(SyAuthorityMenu syAuthorityMenu) throws Exception;

	public void deleteAuthorityMenu(SyAuthorityMenu syAuthorityMenu) throws Exception;

}
