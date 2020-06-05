package kr.co.abcmart.bo.display.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.CmStoreService;
import kr.co.abcmart.bo.display.repository.master.base.BaseCmStoreServiceDao;

@Mapper
public interface CmStoreServiceDao extends BaseCmStoreServiceDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseCmStoreServiceDao 클래스에 구현 되어있습니다.
	 * BaseCmStoreServiceDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public CmStoreService selectByPrimaryKey(CmStoreService cmStoreService) throws Exception;

	/**
	 * @Desc : 매장 서비스 등록
	 * @Method Name : insertCmStoreService
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param cmStoreService
	 */
	public void insertCmStoreService(CmStoreService cmStoreService) throws Exception;

	/**
	 * @Desc : 매장 서비스 삭제
	 * @Method Name : deleteCmStoreService
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param cmStoreService
	 */
	public void deleteCmStoreService(CmStoreService cmStoreService) throws Exception;

	/**
	 * @Desc : 매장 서비스 삭제 (by storeNo)
	 * @Method Name : deleteCmStoreServiceByStoreNo
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param storeNo
	 */
	public void deleteCmStoreServiceByStoreNo(String storeNo) throws Exception;

}
