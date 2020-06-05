package kr.co.abcmart.bo.order.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.member.model.master.MbMemberGiftCard;
import kr.co.abcmart.bo.order.model.master.OcGiftCardOrder;
import kr.co.abcmart.bo.order.repository.master.base.BaseOcGiftCardOrderDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface OcGiftCardOrderDao extends BaseOcGiftCardOrderDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcGiftCardOrderDao 클래스에 구현 되어있습니다.
	 * BaseOcGiftCardOrderDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public OcGiftCardOrder selectByPrimaryKey(OcGiftCardOrder ocGiftCardOrder) throws Exception;

	public Integer selectMemberGiftCardListCount(Pageable<MbMemberGiftCard, OcGiftCardOrder> pageable) throws Exception;

	public List<OcGiftCardOrder> selectMemberGiftCardList(Pageable<MbMemberGiftCard, OcGiftCardOrder> pageable)
			throws Exception;

	/**
	 * 
	 * @Desc : 구매 충전 내역 조회
	 * @Method Name : selectGiftCardHistoryForCancel
	 * @Date : 2019. 7. 31.
	 * @Author : NKB
	 * @param ocGiftCardOrder
	 * @return
	 * @throws Exception
	 */
	public OcGiftCardOrder selectGiftCardHistoryForCancel(OcGiftCardOrder ocGiftCardOrder) throws Exception;

	public void updateGiftCardOrderCancelStat(OcGiftCardOrder ocGiftCardOrder) throws Exception;

	public void updateGiftCardMmsResend(OcGiftCardOrder ocGiftCardOrder) throws Exception;

}
