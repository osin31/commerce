package kr.co.abcmart.bo.order.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.order.model.master.IfOffDealHistory;
import kr.co.abcmart.bo.order.repository.master.base.BaseIfOffDealHistoryDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface IfOffDealHistoryDao extends BaseIfOffDealHistoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseIfOffDealHistoryDao 클래스에 구현 되어있습니다.
	 * BaseIfOffDealHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public IfOffDealHistory selectByPrimaryKey(IfOffDealHistory ifOffDealHistory) throws Exception;

	/**
	 * @Desc :
	 * @Method Name : readOfflineOrderListCount
	 * @Date : 2019. 3. 9.
	 * @Author : flychani@3top.co.kr
	 * @param pageableㄴ
	 * @return
	 */
	public int readOfflineOrderListCount(Pageable<IfOffDealHistory, IfOffDealHistory> pageable) throws Exception;

	/**
	 * @Desc :
	 * @Method Name : readOfflineOrderList
	 * @Date : 2019. 3. 9.
	 * @Author : flychani@3top.co.kr
	 * @param pageable
	 * @return
	 */
	public List<IfOffDealHistory> readOfflineOrderList(Pageable<IfOffDealHistory, IfOffDealHistory> pageable)
			throws Exception;

}
