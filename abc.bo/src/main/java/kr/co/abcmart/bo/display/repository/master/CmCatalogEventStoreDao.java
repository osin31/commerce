package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.CmCatalogEvent;
import kr.co.abcmart.bo.display.model.master.CmCatalogEventStore;
import kr.co.abcmart.bo.display.repository.master.base.BaseCmCatalogEventStoreDao;

@Mapper
public interface CmCatalogEventStoreDao extends BaseCmCatalogEventStoreDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseCmCatalogEventStoreDao 클래스에 구현 되어있습니다.
	 * BaseCmCatalogEventStoreDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public CmCatalogEventStore selectByPrimaryKey(CmCatalogEventStore cmCatalogEventStore) throws Exception;

	/**
	 * @Desc : 카탈로그 이벤트 매장 목록 조회
	 * @Method Name : selectCmCatalogEventStoreListByCtlgEventNo
	 * @Date : 2019. 4. 26.
	 * @Author : 이가영
	 * @param ctlgEventNo
	 * @return
	 */
	public List<CmCatalogEventStore> selectCmCatalogEventStoreListByCtlgEventNo(String ctlgEventNo) throws Exception;

	/**
	 * @Desc : 카탈로그 이벤트 매장 등록
	 * @Method Name : insertCmCatalogEventStore
	 * @Date : 2019. 4. 26.
	 * @Author : 이가영
	 * @param cmCatalogEventStore
	 */
	public void insertCmCatalogEventStore(CmCatalogEvent cmCatalogEvent) throws Exception;

	/**
	 * @Desc : 카탈로그 이벤트 매장 삭제
	 * @Method Name : deleteCmCatalogEventStoreByCtlgEventNo
	 * @Date : 2019. 4. 26.
	 * @Author : 이가영
	 * @param ctlgEventNo
	 */
	public void deleteCmCatalogEventStoreByCtlgEventNo(String ctlgEventNo) throws Exception;

}
