package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.CmStore;
import kr.co.abcmart.bo.display.repository.master.base.BaseCmStoreDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface CmStoreDao extends BaseCmStoreDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseCmStoreDao 클래스에 구현 되어있습니다. BaseCmStoreDao는
	 * 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public CmStore selectByPrimaryKey(CmStore cmStore) throws Exception;

	/**
	 * @Desc : 오프라인 매장 정보 조회
	 * @Method Name : selectCmStoreInfo
	 * @Date : 2019. 3. 4.
	 * @Author : flychani@3top.co.kr
	 * @param cmStore
	 * @return
	 */
	public CmStore selectCmStoreInfo(CmStore cmStore) throws Exception;

	/**
	 * @Desc : 오프라인 매장 리스트 조회
	 * @Method Name : selectCmStoreList
	 * @Date : 2019. 3. 18.
	 * @Author : 이가영
	 * @param cmStore
	 * @return
	 */
	public List<CmStore> selectCmStoreList(Pageable<CmStore, CmStore> pageable) throws Exception;

	/**
	 * @Desc : 오프라인 매장 상세 조회
	 * @Method Name : selectCmStoreDetail
	 * @Date : 2019. 3. 18.
	 * @Author : 이가영
	 * @param cmStore
	 * @return
	 */
	public CmStore selectCmStoreDetail(CmStore cmStore) throws Exception;

	/**
	 * @Desc : 오프라인 매장 카운트 조회
	 * @Method Name : selectCmStoreCount
	 * @Date : 2019. 3. 18.
	 * @Author : 이가영
	 * @param cmStore
	 * @return
	 */
	public int selectCmStoreCount(Pageable<CmStore, CmStore> pageable) throws Exception;

	/**
	 * @Desc : 오프라인 매장 등록
	 * @Method Name : insertCmStore
	 * @Date : 2019. 3. 19.
	 * @Author : 이가영
	 * @param cmStore
	 */
	public void insertCmStore(CmStore cmStore) throws Exception;

	/**
	 * @Desc : 오프라인 매장 수정
	 * @Method Name : updateCmStore
	 * @Date : 2019. 3. 19.
	 * @Author : 이가영
	 * @param cmStore
	 */
	public void updateCmStore(CmStore cmStore) throws Exception;

	/**
	 * @Desc : 오프라인 매장 삭제
	 * @Method Name : deleteCmStore
	 * @Date : 2019. 9. 20.
	 * @Author : 이가영
	 * @param storeNo
	 */
	public void deleteCmStoreByStoreNo(String storeNo) throws Exception;
	
	/**
	 * @Desc      	: 오프라인 매장찾기 엑셀 다운 리스트
	 * @Method Name : selectStoreExcelList
	 * @Date  	  	: 2020. 4. 9.
	 * @Author    	: sic
	 * @param cmStore
	 * @return
	 * @throws Exception
	 */
	public List selectStoreExcelList(CmStore cmStore) throws Exception;

}
