package kr.co.abcmart.bo.order.repository.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.delivery.vo.DeliveryPrdtVO;
import kr.co.abcmart.bo.order.model.master.OcOrderDeliveryHistory;
import kr.co.abcmart.bo.order.repository.master.base.BaseOcOrderDeliveryHistoryDao;

@Mapper
public interface OcOrderDeliveryHistoryDao extends BaseOcOrderDeliveryHistoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcOrderDeliveryHistoryDao 클래스에 구현 되어있습니다.
	 * BaseOcOrderDeliveryHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public OcOrderDeliveryHistory selectByPrimaryKey(OcOrderDeliveryHistory ocOrderDeliveryHistory) throws Exception;

	/**
	 * @Desc : 주문 배송 목록 조회
	 * @Method Name : selectDeliveryHistoryList
	 * @Date : 2019. 2. 24.
	 * @Author : flychani@3top.co.kro
	 * @param ocOrderDeliveryHistory
	 * @return
	 */
	public List<OcOrderDeliveryHistory> selectDeliveryHistoryList(OcOrderDeliveryHistory ocOrderDeliveryHistory)
			throws Exception;

	/**
	 * @Desc : 주문 배송 상세 조회
	 * @Method Name : getDeliveryInfo
	 * @Date : 2019. 3. 25.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderDeliveryHistory
	 * @return
	 */
	public OcOrderDeliveryHistory getPrdtDeliveryInfo(OcOrderDeliveryHistory ocOrderDeliveryHistory) throws Exception;

	/**
	 * @Desc :
	 * @Method Name : UpdateDeliveryStop
	 * @Date : 2019. 3. 1.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderDeliveryHistory
	 * @return
	 */
	public int UpdateDeliveryStop(OcOrderDeliveryHistory ocOrderDeliveryHistory) throws Exception;

	/**
	 * @Desc :
	 * @Method Name : setReDelivery
	 * @Date : 2019. 3. 2.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderDeliveryHistory
	 * @return
	 */
	public int setReDelivery(OcOrderDeliveryHistory ocOrderDeliveryHistory) throws Exception;

	/**
	 * @Desc : 주문배송이력 등록
	 * @Method Name : insertDelivery
	 * @Date : 2019. 4. 5.
	 * @Author : KTH
	 * @param ocOrderDeliveryHistory
	 * @throws Exception
	 */
	public void insertDelivery(OcOrderDeliveryHistory ocOrderDeliveryHistory) throws Exception;

	/**
	 * @Desc : 클레임 상품 반송 상세 조회
	 * @Method Name : selectDeliveryByClmNoClmPrdtSeq
	 * @Date : 2019. 3. 13.
	 * @Author : 이강수
	 * @param ocOrderDeliveryHistory
	 * @return OcOrderDeliveryHistory
	 */
	public OcOrderDeliveryHistory selectDeliveryByClmNoClmPrdtSeq(OcOrderDeliveryHistory ocOrderDeliveryHistory)
			throws Exception;

	/**
	 * @Desc :
	 * @Method Name : setChangeDelivery
	 * @Date : 2019. 3. 25.
	 * @Author : flychani@3top.co.kr
	 * @param dlvyInfo
	 * @return
	 */
	public int setChangeDelivery(OcOrderDeliveryHistory dlvyInfo) throws Exception;

	/**
	 * @Desc :
	 * @Method Name : updateDeliveryStatus
	 * @Date : 2019. 3. 29.
	 * @Author : flychani@3top.co.kr
	 * @param dlvyHistory
	 * @return
	 */
	public int updateDeliveryStatus(OcOrderDeliveryHistory dlvyHistory) throws Exception;

	/**
	 * @Desc :
	 * @Method Name : updateDlvyPickupPsbltDate
	 * @Date : 2019. 3. 31.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderDeliveryHistory
	 * @return
	 */
	public int updateDlvyPickupPsbltDate(OcOrderDeliveryHistory ocOrderDeliveryHistory) throws Exception;

	/**
	 * @Desc : 배송지 변경 가능 배송이력 데이터 조회
	 * @Method Name : updateDeliveryHistoryData
	 * @Date : 2019. 8. 16.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderDeliveryHistory
	 * @return
	 */
	public List<OcOrderDeliveryHistory> getUpdatePsbltDlvyHistData(OcOrderDeliveryHistory ocOrderDeliveryHistory);

	/*
	 * ******************************* NKB
	 *************************************************/

	/**
	 * 
	 * @Desc : 배송아이디 기준으로 수취인 주소 저장
	 * @Method Name : updateAddressDeliveryHistory
	 * @Date : 2019. 3. 25.
	 * @Author : NKB
	 * @param ocOrderDeliveryHistory
	 * @return
	 * @throws Exception
	 */
	public int updateAddressDeliveryHistory(OcOrderDeliveryHistory ocOrderDeliveryHistory) throws Exception;

	/**
	 * 
	 * @Desc : 배송ID를 기준으로 주문배송이력을 조회
	 * @Method Name : getOrderDeliveryHistoryDlvyId
	 * @Date : 2019. 3. 25.
	 * @Author : NKB
	 * @param ocOrderDeliveryHistory
	 * @return
	 * @throws Exception
	 */
	public OcOrderDeliveryHistory getOrderDeliveryHistoryDlvyId(OcOrderDeliveryHistory ocOrderDeliveryHistory)
			throws Exception;

	/**
	 * 
	 * @Desc :주문배송이력순번
	 * @Method Name : selectOrderDlvyHistSeq
	 * @Date : 2019. 3. 28.
	 * @Author : NKB
	 * @param orderNo
	 * @param orderPrdtSeq
	 * @param orderDlvyHistSeq
	 * @return
	 * @throws Exception
	 */
	public int selectOrderDlvyHistSeq(String orderNo, int orderPrdtSeq, int orderDlvyHistSeq) throws Exception;

	/**
	 * 
	 * @Desc : 기존 배송이력 취소 처리 진행
	 * @Method Name : updateDeliveryHistoryCancel
	 * @Date : 2019. 3. 28.
	 * @Author : NKB
	 * @param ocDelivery
	 * @return
	 * @throws Exception
	 */
	public int updateDeliveryHistoryCancel(OcOrderDeliveryHistory ocOrderDeliveryHistory) throws Exception;

	/**
	 * 
	 * @Desc : 택배사 결제 비용 관련 Update
	 * @Method Name : UpdateDeliveryOrderLoss
	 * @Date : 2019. 4. 8.
	 * @Author : NKB
	 * @param ocOrderDeliveryHistory
	 * @return
	 * @throws Exception
	 */
	public int updateDeliveryHistoryLogis(OcOrderDeliveryHistory ocOrderDeliveryHistory) throws Exception;

	/**
	 * 
	 * @Desc : 업체 - 배송상태 변경 처리
	 * @Method Name : updateDeliveryHistoryCancel
	 * @Date : 2019. 4. 23.
	 * @Author : NKB
	 * @param ocOrderDeliveryHistory
	 * @return
	 * @throws Exception
	 */
	public int updateDeliveryVendorStatCode(OcOrderDeliveryHistory ocOrderDeliveryHistory) throws Exception;

	/**
	 * @Desc : 배송상태 일괄/개별 update 쿼리
	 * @Method Name : updateOrderDeliveryHistoryStat
	 * @Date : 2019. 7. 7.
	 * @Author : KTH
	 * @param ocOrderDeliveryHistory
	 * @return
	 * @throws Exception
	 */
	public int updateOrderDeliveryHistoryStat(OcOrderDeliveryHistory ocOrderDeliveryHistory) throws Exception;

	/**
	 * @Desc : 주문기준으로 배송지 정보 Update
	 * @Method Name : updateOcOrderDeliveryHistoryModify
	 * @Date : 2019. 8. 19.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderDeliveryHistory
	 * @return
	 */
	public int updateOcOrderDeliveryHistoryModify(OcOrderDeliveryHistory ocOrderDeliveryHistory) throws Exception;

	/**
	 * @Desc : AS 회송수지시 CBCD , 매장관리 번호 조회
	 * @Method Name : selectAfterServicePickup
	 * @Date : 2019. 8. 23.
	 * @Author : lee
	 * @param ocOrderDeliveryHistory
	 * @return
	 * @throws Exception
	 */
	public OcOrderDeliveryHistory selectAfterServicePickup(OcOrderDeliveryHistory ocOrderDeliveryHistory)
			throws Exception;

	/**
	 * 
	 * @Desc : 매장배송 택배전환 프로시져 호출
	 * @Method Name : callProcedureForOrderChangeForDlvyType
	 * @Date : 2019. 8. 25.
	 * @Author : NKB
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public void callProcedureForOrderChangeForDlvyType(Map<String, String> map) throws Exception;

	/**
	 * @Desc : 분실처리 진행 Update
	 * @Method Name : UpdateDeliveryMiss
	 * @Date : 2019. 8. 29.
	 * @Author : NKB
	 * @param ocOrderDeliveryHistory
	 * @return
	 * @throws Exception
	 */
	public int UpdateDeliveryMiss(OcOrderDeliveryHistory ocOrderDeliveryHistory) throws Exception;

	/**
	 * @Desc : 배송기준 상품조회 ( 사은품 포함 )
	 * @Method Name : getDeliveryForPrdtInfo
	 * @Date : 2019. 10. 11.
	 * @Author : flychani@3top.co.kr
	 * @param dlvyParams
	 * @return
	 */
	public List<OcOrderDeliveryHistory> getDeliveryForPrdtInfo(OcOrderDeliveryHistory dlvyParams) throws Exception;

	/**
	 * @Desc :배송상태 구매확정으로 변경 처리
	 * @Method Name : updateOrderConfirm
	 * @Date : 2019. 6. 12.
	 * @Author : NKB
	 * @param order
	 * @throws Exception
	 */
	public int updateOrderConfirm(OcOrderDeliveryHistory ocOrderDeliveryHistory) throws Exception;

	/**
	 * @Desc : 가장 마지막 차수의 배송 이전의 마지막으로 분실처리된 배송 조회 (배송비, 사은품 제외)
	 * @Method Name : selectLastMissOrderDelivery
	 * @Date : 2020. 3. 30.
	 * @Author : 이강수
	 * @param dlvyParams
	 * @return
	 */
	public OcOrderDeliveryHistory selectLastMissOrderDelivery(OcOrderDeliveryHistory ocOrderDeliveryHistory)
			throws Exception;

	/**
	 * @Desc : 배송 진행중인 상품 => 배송완료 처리
	 * @Method Name : updateOrderDeliveryStat
	 * @Date : 2020. 4. 7.
	 * @Author : 이동엽
	 * @param dlvyIdText
	 * @throws Exception
	 */
	public void updateOrderDeliveryStat(DeliveryPrdtVO params) throws Exception;

}
