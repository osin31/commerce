package kr.co.abcmart.bo.order.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.delivery.vo.DeliveryPrdtVO;
import kr.co.abcmart.bo.order.model.master.OcOrder;
import kr.co.abcmart.bo.order.model.master.OcOrderProduct;
import kr.co.abcmart.bo.order.repository.master.base.BaseOcOrderProductDao;
import kr.co.abcmart.bo.product.model.master.PdProductOptionStock;

@Mapper
public interface OcOrderProductDao extends BaseOcOrderProductDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcOrderProductDao 클래스에 구현 되어있습니다.
	 * BaseOcOrderProductDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public OcOrderProduct selectByPrimaryKey(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 주문 상세 상품목록 조회
	 * @Method Name : selectProductInfo
	 * @Date : 2019. 2. 23.
	 * @Author : flychani@3top.co.kr
	 * @param orderNo
	 * @return
	 */
	public List<OcOrderProduct> selectProductList(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 주문 상세 목록 조회
	 * @Method Name : selectProductInfo
	 * @Date : 2019. 2. 23.
	 * @Author : flychani@3top.co.kr
	 * @param orderNo
	 * @return
	 */
	public List<OcOrderProduct> selectOrderProductList(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 클레임 대상 주문상품 상세 조회
	 * @Method Name : selectOrderProductDetailForClaim
	 * @Date : 2019. 8. 29.
	 * @Author : KTH
	 * @param ocOrderProduct
	 * @return
	 * @throws Exception
	 */
	public OcOrderProduct selectOrderProductDetailForClaim(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc :
	 * @Method Name : selectOrderVendorList
	 * @Date : 2019. 2. 24.
	 * @Author : flychani@3top.co.kr
	 * @param orderNo
	 * @return
	 */
	public List<OcOrderProduct> selectOrderVendorList(String orderNo) throws Exception;

	/**
	 * @Desc : 주문 상품 구매확정 처리
	 * @Method Name : UpdateOrderConfirm
	 * @Date : 2019. 3. 1.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderProduct
	 * @return
	 */
	public int updatePrdtOrderStatus(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc :
	 * @Method Name : selectOrderProductValidationList
	 * @Date : 2019. 3. 27.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderProduct
	 * @return
	 */
	public List<OcOrderProduct> selectOrderProductValidationList(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 주문상품상세 정보
	 * @Method Name : selectProductDetail
	 * @Date : 2019. 3. 30.
	 * @Author : flychani@3top.co.kr
	 * @param prdt
	 * @return
	 */
	public OcOrderProduct selectProductDetail(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 픽업연장여부 변경
	 * @Method Name : updateProdutLogisCnvrt
	 * @Date : 2019. 3. 31.
	 * @Author : flychani@3top.co.kr
	 * @param prdt
	 * @return
	 */
	public int updateProdutLogisCnvrt(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 주문상품 저장
	 * @Method Name : insertOrderProduct
	 * @Date : 2019. 4. 3.
	 * @Author : KTH
	 * @param ocOrderProduct
	 * @return
	 * @throws Exception
	 */
	public int insertOrderProduct(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 주문상품 이벤트 포인트 사용 최대 가능 금액 - 클레임상품 제외
	 * @Method Name : selectMaxUsableEventPointOrderAmt
	 * @Date : 2019. 4. 17.
	 * @Author : KTH
	 * @param ocOrderProduct
	 * @return
	 * @throws Exception
	 */
	public int selectMaxUsableEventPointOrderAmt(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 원 주문번호 기준 주문상품 목록 조회
	 * @Method Name : selectOrgOrderProductList
	 * @Date : 2019. 5. 27.
	 * @Author : KTH
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<OcOrderProduct> selectOrgOrderProductList(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 다족구매 프로모션 재조정으로 인한 최근 금액변경 상품 추출
	 * @Method Name : selectRecentMultiBuyReApplyProduct
	 * @Date : 2019. 7. 10.
	 * @Author : KTH
	 * @param ocOrderProduct
	 * @return
	 * @throws Exception
	 */
	public OcOrderProduct selectRecentMultiBuyReApplyProduct(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 클레임 처리 위한 주문상품 update
	 * @Method Name : updateOrderProductForCalim
	 * @Date : 2019. 6. 16.
	 * @Author : KTH
	 * @param ocOrderProduct
	 * @return
	 * @throws Exception
	 */
	public int updateOrderProductForCalim(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 클레임 번호로 리오더 주문 상품 정산확정일자 업데이트
	 * @Method Name : updateExcclcByClmNo
	 * @Date : 2019. 8. 6.
	 * @Author : KTH
	 * @param ocOrderProduct
	 * @return
	 * @throws Exception
	 */
	public int updateExcclcByClmNo(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 클레임 번호로 교환 재배송 상품 기준 상태 값 업데이트
	 * @Method Name : updatePrdtStatExchangeReDlvyByClmNo
	 * @Date : 2019. 8. 27.
	 * @Author : KTH
	 * @param ocOrderProduct
	 * @return
	 * @throws Exception
	 */
	public int updatePrdtStatExchangeReDlvyByClmNo(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 리오더 주문상품 목록
	 * @Method Name : selectReOrderProductList
	 * @Date : 2019. 7. 15.
	 * @Author : KTH
	 * @param orderProduct
	 * @return
	 * @throws Exception
	 */
	public List<OcOrderProduct> selectReOrderProductList(OcOrderProduct orderProduct) throws Exception;

	/**
	 * 
	 * @Desc : 주문 상품 배송상태 변경 - vendor
	 * @Method Name : updateOrderPrdtVendorStatCode
	 * @Date : 2019. 4. 23.
	 * @Author : NKB
	 * @param ocOrderProduct
	 * @return
	 * @throws Exception
	 */
	public int updateOrderPrdtVendorStatCode(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * 
	 * @Desc : 판매취소 요청 현황 - vendor
	 * @Method Name : updateOrderPrdtVendorCancel
	 * @Date : 2019. 4. 24.
	 * @Author : NKB
	 * @param ocOrderProduct
	 * @return
	 * @throws Exception
	 */
	public int updateOrderPrdtVendorCancel(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * 
	 * @Desc : 주문상품상세 정보 - 주문상품 순번으로 조회
	 * @Method Name : selectOnlyProductDetail
	 * @Date : 2019. 4. 24.
	 * @Author : NKB
	 * @param ocOrderProduct
	 * @return
	 * @throws Exception
	 */
	public OcOrderProduct selectOnlyProductDetail(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 업체 주문 상품 리스트 VNDR_NO 세션으로 받음.
	 * @Method Name : selectVendorProductList
	 * @Date : 2019. 4. 23.
	 * @Author : lee
	 * @param ocOrderProduct
	 * @return
	 * @throws Exception
	 */
	public List<OcOrderProduct> selectVendorProductList(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * 
	 * @Desc : 발송지연 안내
	 * @Method Name : updateOrderPrdtVendorReservation
	 * @Date : 2019. 4. 25.
	 * @Author : NKB
	 * @param ocOrderProduct
	 * @return
	 * @throws Exception
	 */
	public int updateOrderPrdtVendorReservation(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 상품 옵션 변경 저장
	 * @Method Name : setPrdtOptionSave
	 * @Date : 2019. 7. 18.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderProduct
	 * @return
	 */
	public boolean setPrdtOptionSave(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 교환 상품 조회
	 * @Method Name : selectProductChangeList
	 * @Date : 2019. 7. 25.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderProductChange
	 * @return
	 */
	public List<OcOrderProduct> selectProductChangeList(OcOrderProduct ocOrderProductChange) throws Exception;

	/**
	 * @Desc :
	 * @Method Name : selectOrderStatCount
	 * @Date : 2019. 8. 14.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderProduct
	 * @return
	 */
	public OcOrderProduct selectOrderStatCount(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc :
	 * @Method Name : selectOrderProductAllByOrder
	 * @Date : 2019. 8. 20.
	 * @Author : flychani@3top.co.kr
	 * @param orderProduct
	 * @return
	 */
	public List<OcOrderProduct> selectOrderProductAllByOrder(OcOrderProduct orderProduct) throws Exception;

	/**
	 * @Desc : 매장 픽업 택배전환 상태변경
	 * @Method Name : updatePrdtOrderDeliveryChange
	 * @Date : 2019. 8. 28.
	 * @Author : NKB
	 * @param ocOrderProduct
	 * @return
	 * @throws Exception
	 */
	public int updatePrdtOrderDeliveryChange(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 주문 상품 재고 수량 변경 - 플러스
	 * @Method Name : updateProductStockMinus
	 * @Date : 2019. 9. 17.
	 * @Author : NKB
	 * @param prdtStock
	 * @throws Exception
	 */
	public void updateProductStockAdd(PdProductOptionStock prdtStock) throws Exception;

	/**
	 * @Desc : 주문 상품 재고 수량 변경 - 플러스
	 * @Method Name : updateProductStock
	 * @Date : 2019. 5. 31.
	 * @Author : ljyoung@3top.co.kr
	 * @param prdtStock
	 * @throws Exception
	 */
	public void updateProductStock(PdProductOptionStock prdtStock) throws Exception;

	/**
	 * @Desc : 주문번호 배열 상품조회
	 * @Method Name : selectOrderProductForOrderNos
	 * @Date : 2019. 10. 17.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderProduct
	 * @return
	 */
	public List<OcOrderProduct> selectOrderProductForOrderNos(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 주문상품 AS접수 시 validate
	 * @Method Name : validateOrderProduct
	 * @Date : 2019. 11. 08.
	 * @Author : 이강수
	 * @param OcOrderProduct
	 * @return int
	 */
	public int selectOrderProductForAsValidate(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * 
	 * @Desc :배송상태 구매확정으로 변경 처리
	 * @Method Name : updateOrderConfirm
	 * @Date : 2019. 6. 12.
	 * @Author : NKB
	 * @param order
	 * @throws Exception
	 */
	public int updateOrderConfirm(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 배송중단 처리시 주문 상품 상세 조회
	 * @Method Name : selectProductDetailDeliveryStop
	 * @Date : 2020. 3. 26.
	 * @Author : sic
	 * @param ocOrderProduct
	 * @return
	 * @throws Exception
	 */
	public OcOrderProduct selectProductDetailDeliveryStop(OcOrderProduct ocOrderProduct) throws Exception;

	/**
	 * @Desc : 배송 진행중인 상품 => 배송완료 처리
	 * @Method Name : udpateOrderPrdtDeliveryStat
	 * @Date : 2020. 4. 7.
	 * @Author : 이동엽
	 * @param orderNo
	 * @param orderPrdtSeq
	 * @throws Exception
	 */
	public void udpateOrderPrdtDeliveryStat(DeliveryPrdtVO params) throws Exception;
	
	/**
	 * @Desc : 미출로인한 교환불가처리 시 취소처리할 교환재배송상품 조회
	 * @Method Name : selectLastChangeOrderDeliveryProduct
	 * @Date : 2020. 5. 22.
	 * @Author : 이강수
	 * @param clmNo, orgOrderNo
	 * @return
	 */
	public List<OcOrderProduct> selectLastChangeOrderDeliveryProduct(OcOrderProduct ocOrderProduct)
			throws Exception;
	
	/**
	 * @Desc      	: 주문상품 상태 조회
	 * @Method Name : selectOrderProducts
	 * @Date  	  	: 2020. 5. 25.
	 * @Author    	: 3top
	 * @param ocOrder
	 * @return
	 * @throws Exception
	 */
	public List<OcOrderProduct> selectOrderProducts(OcOrder ocOrder) throws Exception;
}
