package kr.co.abcmart.bo.cmm.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.cmm.model.master.CmDaysCondtn;
import kr.co.abcmart.bo.cmm.repository.master.base.BaseCmDaysCondtnDao;
import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.common.paging.Pageable;

import java.lang.Object;
import java.util.List;

@Mapper
public interface CmDaysCondtnDao extends BaseCmDaysCondtnDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BaseCmDaysCondtnDao 클래스에 구현 되어있습니다.
     * BaseCmDaysCondtnDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
     */

	public CmDaysCondtn selectByPrimaryKey(Object cmDaysCondtn) throws Exception;
	
	/***
	 * 조건 날짜 관리 목록 카운터 호출
	 * @param CmDaysCondtn
	 * @return int
	 */
	public int selectDaysCondtnListCount(Pageable<CmDaysCondtn, CmDaysCondtn> pageable) throws Exception;
	
	/***
	 * 조건 날짜 관리 목록 호출
	 * @param CmDaysCondtn
	 * @return CmDaysCondtn
	 */
	public List<CmDaysCondtn> selectDaysCondtnList(Pageable<CmDaysCondtn, CmDaysCondtn> pageable) throws Exception;
	
	/***
	 * 조건 날짜 관리 목록 수정
	 * @param CmDaysCondtn
	 * @return 
	 */
	public void updateList(CmDaysCondtn cmDaysCondtn) throws Exception;
	
	/***
	 * 조건 날짜 단건 조회
	 * @param String
	 * @return 
	 */
	public CmDaysCondtn selectDaysCondtn(String condtnTermName) throws Exception;

}
