package kr.co.abcmart.bo.order.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.order.model.master.OcOrderProductHistory;
import kr.co.abcmart.bo.order.repository.master.base.BaseOcOrderProductHistoryDao;

@Mapper
public interface OcOrderProductHistoryDao extends BaseOcOrderProductHistoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcOrderProductHistoryDao 클래스에 구현 되어있습니다.
	 * BaseOcOrderProductHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public OcOrderProductHistory selectByPrimaryKey(OcOrderProductHistory ocOrderProductHistory) throws Exception;

	public List<OcOrderProductHistory> selectByProduct(OcOrderProductHistory ocOrderProductStatusHistory)
			throws Exception;

	public List<OcOrderProductHistory> selectOrderProductStatusHistory(
			OcOrderProductHistory ocOrderProductStatusHistory) throws Exception;

	/**
	 * @Desc : 상품 변경 이력 등록
	 * @Method Name : insertProductHistory
	 * @Date : 2019. 3. 29.
	 * @Author : flychani@3top.co.kr
	 * @param prdtHistory
	 * @return
	 */
	public int insertProductHistory(OcOrderProductHistory prdtHistory) throws Exception;

	/**
	 * 
	 * @Desc : 주문번호 기준으로 History 등록
	 * @Method Name : insertProductHistorybyOrderNo
	 * @Date : 2019. 7. 25.
	 * @Author : NKB
	 * @param prodHistoryList
	 * @return
	 * @throws Exception
	 */
	public int insertProductHistorybyOrderNo(OcOrderProductHistory ocOrderProductHistor) throws Exception;

	/**
	 * @Desc : 주문취소 접수 전 주문상품 상태값 조회
	 * @Method Name : selectOrderPrdtStatBeforeCancelAccept
	 * @Date : 2020. 3. 30.
	 * @Author : 이강수
	 * @param OcOrderProductHistory
	 * @return
	 * @throws Exception
	 */
	public OcOrderProductHistory selectOrderPrdtStatBeforeCancelAccept(OcOrderProductHistory ocOrderProductHistor)
			throws Exception;
}
