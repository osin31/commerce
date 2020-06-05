package kr.co.abcmart.bo.claim.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.claim.model.master.OcClaimStatusHistory;
import kr.co.abcmart.bo.claim.repository.master.base.BaseOcClaimStatusHistoryDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface OcClaimStatusHistoryDao extends BaseOcClaimStatusHistoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcClaimStatusHistoryDao 클래스에 구현 되어있습니다.
	 * BaseOcClaimStatusHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public OcClaimStatusHistory selectByPrimaryKey(OcClaimStatusHistory ocClaimStatusHistory) throws Exception;

	/**
	 * @Desc : 클레임 이력 목록 갯수 조회
	 * @Method Name : selectClaimHistoryListCount
	 * @Date : 2019. 3. 18.
	 * @Author : 이강수
	 * @param Pageable<OcClaimStatusHistory, OcClaimStatusHistory>
	 * @return int
	 * @throws Exception
	 */
	public int selectClaimHistoryListCount(Pageable<OcClaimStatusHistory, OcClaimStatusHistory> pageable)
			throws Exception;

	/**
	 * @Desc : 클레임 이력 목록 조회
	 * @Method Name : selectClaimHistoryList
	 * @Date : 2019. 3. 18.
	 * @Author : 이강수
	 * @param Pageable<OcClaimStatusHistory, OcClaimStatusHistory>
	 * @return List<OcClaimStatusHistory>
	 * @throws Exception
	 */
	public List<OcClaimStatusHistory> selectClaimHistoryList(
			Pageable<OcClaimStatusHistory, OcClaimStatusHistory> pageable) throws Exception;

	/**
	 * @Desc : 클레임상태이력 등록
	 * @Method Name : insertClaimStatusHistory
	 * @Date : 2019. 3. 26.
	 * @Author : KTH
	 * @param ocClaimStatusHistory
	 * @return
	 * @throws Exception
	 */
	public int insertClaimStatusHistory(OcClaimStatusHistory ocClaimStatusHistory) throws Exception;

	/**
	 * @Desc : 클레임상품 기준 클레임상태이력 등록
	 * @Method Name : insertClaimStatusHistoryByClaimProduct
	 * @Date : 2019. 4. 1.
	 * @Author : KTH
	 * @param ocClaimStatusHistory
	 * @return
	 * @throws Exception
	 */
	public int insertClaimStatusHistoryByClaimProduct(OcClaimStatusHistory ocClaimStatusHistory) throws Exception;

}
