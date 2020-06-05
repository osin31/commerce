package kr.co.abcmart.bo.order.repository.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.bo.order.repository.master.base.BaseOcOrderDao;
import kr.co.abcmart.bo.order.vo.OcOrderExcelVo;
import kr.co.abcmart.bo.order.vo.OrderStatVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface OcOrderDao extends BaseOcOrderDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcOrderDao 클래스에 구현 되어있습니다. BaseOcOrderDao는
	 * 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 */

	/**
	 * @Desc : 주문번호 신규채번을 위해 SEQ 조회
	 * @Method Name : selectOrderNoNextVal
	 * @Date : 2019. 4. 22.
	 * @Author : ljyoung@3top.co.kr
	 * @return
	 * @throws Exception
	 */
	public int selectOrderNoNextVal() throws Exception;

	public OcOrder selectByPrimaryKey(OcOrder ocOrder) throws Exception;

	/**
	 * @Desc :
	 * @Method Name : selectOrderListCount
	 * @Date : 2019. 2. 14.
	 * @Author : flychani@3top.co.kr
	 * @param pageable
	 * @return
	 */
	public int selectOrderListCount(Pageable<OcOrder, OcOrder> pageable) throws Exception;

	/**
	 * @Desc : 주문 목록 조회
	 * @Method Name : selectOrderList
	 * @Date : 2019. 2. 14.
	 * @Author : flychani@3top.co.kr
	 * @param pageable
	 * @return
	 */
	public List<OcOrder> selectOrderList(Pageable<OcOrder, OcOrder> pageable) throws Exception;

	/**
	 * @Desc : 주문 마스터 상세
	 * @Method Name : selectOrderDetail
	 * @Date : 2019. 2. 23.
	 * @Author : flychani@3top.co.kr
	 * @param orderNo
	 * @return
	 */
	public OcOrder selectOrderDetail(OcOrder ocOrder) throws Exception;

	/**
	 * @Desc :
	 * @Method Name : updateOrderRfndInfo
	 * @Date : 2019. 3. 1.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrder
	 * @return
	 */
	public int updateOrderRfndInfo(OcOrder ocOrder) throws Exception;

	/**
	 * @Desc : 비회원 비밀번호 변경
	 * @Method Name : updatePasswordChange
	 * @Date : 2019. 3. 3.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrder
	 * @return
	 */
	public int updatePasswordChange(OcOrder ocOrder) throws Exception;

	/**
	 * @Desc : 주문 마스터 수취인 정보 업데이트
	 * @Method Name : updateRcvrInfoUdpate
	 * @Date : 2019. 3. 3.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrder
	 * @return
	 */
	public int updateRcvrInfoUdpate(OcOrder ocOrder) throws Exception;

	/**
	 * @Desc : 주문 엑셀 전체 다운로드
	 * @Method Name : getonOrderForExcelList
	 * @Date : 2019. 3. 5.
	 * @Author : flychani@3top.co.kr
	 * @param params
	 * @return
	 */
	public List<OcOrderExcelVo> getonOrderForExcelList(OcOrder params) throws Exception;

	/**
	 * @Desc : 선택 주문 엑셀 다운로드
	 * @Method Name : getonOrdeSelectListExcelDn
	 * @Date : 2019. 3. 6.
	 * @Author : flychani@3top.co.kr
	 * @param params
	 * @return
	 */
	public List<OcOrderExcelVo> getonOrdeSelectListExcelDn(OcOrder params) throws Exception;

	/**
	 * @Desc :
	 * @Method Name : readOnlineOrderListCount
	 * @Date : 2019. 3. 8.
	 * @Author : flychani@3top.co.kr
	 * @param pageable
	 * @return
	 */
	public int readOnlineOrderListCount(Pageable<OcOrder, OcOrder> pageable) throws Exception;

	/**
	 * @Desc :
	 * @Method Name : readOnlineOrderList
	 * @Date : 2019. 3. 8.
	 * @Author : flychani@3top.co.kr
	 * @param pageable
	 * @return
	 */
	public List<OcOrder> readOnlineOrderList(Pageable<OcOrder, OcOrder> pageable) throws Exception;

	/**
	 * @param ocOrder
	 * @Desc :
	 * @Method Name : selectOrderMstList
	 * @Date : 2019. 3. 8.
	 * @Author : flychani@3top.co.kr
	 * @return
	 */
	public List<OcOrder> selectOrderMstList(OcOrder ocOrder) throws Exception;

	/**
	 * @Desc :
	 * @Method Name : selectRelationOrderList
	 * @Date : 2019. 3. 21.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrder
	 * @return
	 */
	public List<OcOrder> selectRelationOrderList(OcOrder ocOrder) throws Exception;

	/**
	 * @Desc : 구매확정일시 update
	 * @Method Name : updateOrderDcsnDtm
	 * @Date : 2019. 3. 29.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrder
	 * @return
	 */
	public int updateOrderDcsnDtm(OcOrder ocOrder) throws Exception;

	/**
	 * @Desc : 주문 저장
	 * @Method Name : insertOrder
	 * @Date : 2019. 4. 3.
	 * @Author : KTH
	 * @param ocOrder
	 * @return
	 * @throws Exception
	 */
	public int insertOrder(OcOrder ocOrder) throws Exception;

	/**
	 * @Desc : 클레임 처리를 위한 주문 update 쿼리
	 * @Method Name : updateOrderForClaim
	 * @Date : 2019. 7. 16.
	 * @Author : KTH
	 * @param order
	 * @throws Exception
	 */
	public void updateOrderForClaim(OcOrder order) throws Exception;

	/**
	 * @Desc : 대시보드용 주문건수 판매금액 쿼리 (한달)
	 * @Method Name : selectOrderCntAmtList
	 * @Date : 2019. 10. 11.
	 * @Author : 신인철
	 * @return
	 * @throws Exception
	 */
	public List<OcOrder> selectOrderCntAmtList(OcOrder ocOrder) throws Exception;

	/**
	 * @Desc : 대시보드용 주문/배송 현황 카운트 조회
	 * @Method Name : selectDashboardOrderDlvyCount
	 * @Date : 2019. 10. 14.
	 * @Author : 최경호
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectDashboardOrderDlvyCount(String vendorNo) throws Exception;

	/**
	 * @Desc : 베스트 입점사 조회
	 * @Method Name : selectBestVndrList
	 * @Date : 2019. 10. 11.
	 * @Author : sic
	 * @return
	 * @throws Exception
	 */
	public List<OcOrder> selectBestVndrList() throws Exception;

	/**
	 * @Desc : 대시보드 업체배송지연 거수 TOP5
	 * @Method Name : selectDelayedDeliveryVndrList
	 * @Date : 2019. 10. 14.
	 * @Author : 최경호
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectDelayedDeliveryVndrList() throws Exception;

	/**
	 *
	 * @Desc : 구매확정 가능여부 체크
	 * @Method Name : selectOrderStatCount
	 * @Date : 2020. 1. 15.
	 * @Author : 이강수
	 * @param order
	 * @return
	 */
	public OrderStatVO selectOrderStatCount(OcOrder order) throws Exception;

	/**
	 * @Desc : 미수령기간 갱신 프로시져 호출
	 * @Method Name : callProcedureForPickupPsbltDt
	 * @Date : 2020. 3. 17.
	 * @Author : sic
	 * @param map
	 * @throws Exception
	 */
	public void callProcedureForPickupPsbltDt(Map<String, String> paramMap) throws Exception;

}
