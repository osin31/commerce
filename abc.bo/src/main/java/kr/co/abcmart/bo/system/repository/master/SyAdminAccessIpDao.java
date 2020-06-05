package kr.co.abcmart.bo.system.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import kr.co.abcmart.bo.system.repository.master.base.BaseSyAdminAccessIpDao;
import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.bo.system.model.master.SyAdminAccessIp;

@Mapper
public interface SyAdminAccessIpDao extends BaseSyAdminAccessIpDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BaseSyAdminAccessIpDao 클래스에 구현 되어있습니다.
     * BaseSyAdminAccessIpDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
     */

	public SyAdminAccessIp selectByPrimaryKey(SyAdminAccessIp syAdminAccessIp) throws Exception;
	
	/***
	 * 관리자 접근 아이피 등록
	 * @param syAdminAccessIp
	 * @return 
	 */
	public void insertAdminAccessIp(SyAdminAccessIp syAdminAccessIp) throws Exception;
	
	/***
	 * 관리자 접근 아이피 조회
	 * @param String
	 * @return 
	 */
	public List<SyAdminAccessIp> selectAdminAccessIp(String adminNo) throws Exception;
	
	/***
	 * 관리자 접근 아이피 삭제
	 * @param syAdminAccessIp
	 * @return 
	 */
	public void deleteAdminAccessIp(SyAdminAccessIp syAdminAccessIp) throws Exception;

}
