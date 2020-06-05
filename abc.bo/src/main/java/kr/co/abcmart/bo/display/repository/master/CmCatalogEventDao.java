package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.CmCatalogEvent;
import kr.co.abcmart.bo.display.repository.master.base.BaseCmCatalogEventDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface CmCatalogEventDao extends BaseCmCatalogEventDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseCmCatalogEventDao 클래스에 구현 되어있습니다.
	 * BaseCmCatalogEventDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public CmCatalogEvent selectByPrimaryKey(CmCatalogEvent cmCatalogEvent) throws Exception;

	/**
	 * @Desc : 이벤트 목록 조회
	 * @Method Name : selectCmCatalogEventList
	 * @Date : 2019. 4. 25.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public List<CmCatalogEvent> selectCmCatalogEventList(Pageable<CmCatalogEvent, CmCatalogEvent> pageable)
			throws Exception;

	/**
	 * @Desc : 이벤트 목록 카운트 조회
	 * @Method Name : selectCmCatalogEventCount
	 * @Date : 2019. 4. 25.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public List<Integer> selectCmCatalogEventCount(Pageable<CmCatalogEvent, CmCatalogEvent> pageable) throws Exception;

	/**
	 * @Desc : 이벤트 조회
	 * @Method Name : selectCmCatalogEvent
	 * @Date : 2019. 4. 26.
	 * @Author : 이가영
	 * @param cmCatalogEvent
	 * @return
	 */
	public CmCatalogEvent selectCmCatalogEvent(CmCatalogEvent cmCatalogEvent) throws Exception;

	/**
	 * @Desc : 이벤트 등록
	 * @Method Name : insertCmCatalogEvent
	 * @Date : 2019. 4. 26.
	 * @Author : 이가영
	 * @param cmCatalogEvent
	 */
	public void insertCmCatalogEvent(CmCatalogEvent cmCatalogEvent) throws Exception;

	/**
	 * @Desc : 이벤트 수정
	 * @Method Name : updateCmCatalogEvent
	 * @Date : 2019. 4. 26.
	 * @Author : 이가영
	 * @param cmCatalogEvent
	 */
	public void updateCmCatalogEvent(CmCatalogEvent cmCatalogEvent) throws Exception;

}
