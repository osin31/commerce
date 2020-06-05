package kr.co.abcmart.bo.delivery.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.delivery.model.master.OcDelivery;
import kr.co.abcmart.bo.delivery.vo.DeliveryOrderNotExcelVO;
import kr.co.abcmart.bo.delivery.vo.DeliveryPrdtVO;
import kr.co.abcmart.bo.order.model.master.OcOrderProduct;
import kr.co.abcmart.bo.order.repository.master.base.BaseOcOrderDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface OcDeliveryDao extends BaseOcOrderDao {

	/**
	 * 
	 * @Desc : 배송관리 목록 (자사)
	 * @Method Name : selectDeliveryOrderList
	 * @Date : 2019. 2. 8.
	 * @Author : NKB
	 * @param Pageable<OcDelivery, OcDelivery> pageable
	 * @return List<OcDelivery>
	 * @throws Exception
	 */
	public List<OcDelivery> selectDeliveryOrderList(Pageable<OcDelivery, OcDelivery> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 배송관리 목록 (자사) - count
	 * @Method Name : selectDeliveryOrderListCount
	 * @Date : 2019. 2. 14.
	 * @Author : NKB
	 * @param Pageable<OcDelivery, OcDelivery> pageable
	 * @return Integer
	 * @throws Exception
	 */
	public Integer selectDeliveryOrderListCount(Pageable<OcDelivery, OcDelivery> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 배송관리 목록 (자사) - 엑셀다운로드
	 * @Method Name : selectDeliveryOrderListExcel
	 * @Date : 2020. 1. 21.
	 * @Author : 이강수
	 * @param Pageable<OcDelivery, OcDelivery> pageable
	 * @return List<DeliveryOrderNotExcelVO>
	 * @throws Exception
	 */
	public List<DeliveryOrderNotExcelVO> selectDeliveryOrderListExcel(
			Pageable<OcDelivery, DeliveryOrderNotExcelVO> pageable) throws Exception;

	/**
	 * 미출고 현황
	 * 
	 * @Desc : 미출고 현황
	 * @Method Name : selectDeliveryOrderNotList
	 * @Date : 2019. 2. 12.
	 * @Author : NKB
	 * @param ocDelivery
	 * @return
	 * @throws Exception
	 */
	public List<OcDelivery> selectDeliveryOrderNotList(Pageable<OcDelivery, OcDelivery> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 미출고 현황 - count
	 * @Method Name : selectDeliveryOrderListCount
	 * @Date : 2019. 2. 14.
	 * @Author : NKB
	 * @param Pageable<OcDelivery, OcDelivery> pageable
	 * @return Integer
	 * @throws Exception
	 */
	public Integer selectDeliveryOrderNotCount(Pageable<OcDelivery, OcDelivery> pageable) throws Exception;

	/**
	 * @Desc : 미출고 엑셀파일 모두 다운로드
	 * @Method Name : selectDeliveryOrderNotAllExcel
	 * @Date : 2019. 9. 23.
	 * @Author : 이강수
	 * @param Pageable<OcDelivery, DeliveryOrderNotExcelVO>
	 * @return
	 * @throws Exception
	 */
	public List<DeliveryOrderNotExcelVO> selectDeliveryOrderNotAllExcel(
			Pageable<OcDelivery, DeliveryOrderNotExcelVO> pageable) throws Exception;

	/**
	 * 매장 픽업 택배전환
	 * 
	 * @Desc : 매장 픽업 택배전환
	 * @Method Name : selectStorePickDeliveryChangeList
	 * @Date : 2019. 2. 12.
	 * @Author : NKB
	 * @param ocDelivery
	 * @return
	 * @throws Exception
	 */
	public List<OcDelivery> selectStorePickDeliveryChangeList(Pageable<OcDelivery, OcDelivery> pageable)
			throws Exception;

	/**
	 * 매장 픽업 택배전환 -count
	 * 
	 * @Desc : 매장 픽업 택배전환
	 * @Method Name : selectStorePickDeliveryChangeList
	 * @Date : 2019. 2. 12.
	 * @Author : NKB
	 * @param ocDelivery
	 * @return
	 * @throws Exception
	 */
	public Integer selectStorePickDeliveryChangeCount(Pageable<OcDelivery, OcDelivery> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 상품대기조회 Count
	 * @Method Name : selectStorePickDeliveryChangeCount
	 * @Date : 2019. 3. 27.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Integer selectDeliveryProdStandbyCount(Pageable<OcDelivery, OcDelivery> pageable) throws Exception;

	/**
	 * 상품대기조회
	 * 
	 * @Desc :상품대기조회
	 * @Method Name : selectDeliveryProdStandbyList
	 * @Date : 2019. 2. 12.
	 * @Author : NKB
	 * @param ocDelivery
	 * @return
	 * @throws Exception
	 */
	public List<OcDelivery> selectDeliveryProdStandbyList(Pageable<OcDelivery, OcDelivery> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 배송관리 목록 (업체)
	 * @Method Name : selectDeliveryVendorList
	 * @Date : 2019. 2. 12.
	 * @Author : NKB
	 * @param ocDelivery
	 * @return
	 * @throws Exception
	 */
	public OcDelivery selectDeliveryVendorList(OcDelivery ocDelivery) throws Exception;

	/**
	 * 
	 * @Desc : 주문 상품목록
	 * @Method Name : selectOrderProductList
	 * @Date : 2019. 3. 22.
	 * @Author : NKB
	 * @param ocOrderProduct
	 * @return
	 * @throws Exception
	 */
	public List<OcOrderProduct> selectOrderProductList(OcDelivery ocDelivery) throws Exception;

	/**
	 * 
	 * @Desc : 택배전환 주문 배송송정보 상품목록
	 * @Method Name : selectOrderProductDeliveryList
	 * @Date : 2019. 3. 25.
	 * @Author : NKB
	 * @param ocDelivery
	 * @return
	 * @throws Exception
	 */
	public List<OcDelivery> selectOrderProductDeliveryList(OcDelivery ocDelivery) throws Exception;

	/**
	 * 
	 * @Desc : 분실배송조회 - Count
	 * @Method Name : selectDeliveryOrderLossCount
	 * @Date : 2019. 4. 7.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Integer selectDeliveryOrderLossCount(Pageable<OcDelivery, OcDelivery> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 분실배송조회 - List
	 * @Method Name : selectDeliveryOrderLossList
	 * @Date : 2019. 4. 7.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<OcDelivery> selectDeliveryOrderLossList(Pageable<OcDelivery, OcDelivery> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 업체별 상태 Count를 위한 조회
	 * @Method Name : selectDelveryOrderVendorPrdStat
	 * @Date : 2019. 4. 16.
	 * @Author : NKB
	 * @param ocDelivery
	 * @return
	 * @throws Exception
	 */
	public List<OcDelivery> selectDelveryOrderVendorPrdStatList(OcDelivery ocDelivery) throws Exception;

	/**
	 * @Desc : 업체 주문 배송관리 - count
	 * @Method Name : selectDeliveryOrderVendorCount
	 * @Date : 2019. 4. 21.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectDeliveryOrderVendorCount(Pageable<OcDelivery, OcDelivery> pageable) throws Exception;

	/**
	 * @Desc : 업체 주문 배송관리 - List
	 * @Method Name : selectDeliveryOrderVendorList
	 * @Date : 2019. 4. 21.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<OcDelivery> selectDeliveryOrderVendorList(Pageable<OcDelivery, OcDelivery> pageable) throws Exception;

	/**
	 * @Desc : 업체 주문 배송관리 전체 엑셀 다운로드
	 * @Method Name : selectDeliveryOrderVendorAllExcelDownload
	 * @Date : 2019. 9. 23.
	 * @Author : 이강수
	 * @param Pageable<OcDelivery, OcDelivery>
	 * @return
	 * @throws Exception
	 */
	public List<OcDelivery> selectDeliveryOrderVendorAllExcelDownload(Pageable<OcDelivery, OcDelivery> pageable)
			throws Exception;

	/**
	 * 
	 * @Desc : 판매취소 현황 Count
	 * @Method Name : selectDeliveryOrderVendorCancelCount
	 * @Date : 2019. 5. 14.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Integer selectDeliveryOrderVendorCancelCount(Pageable<OcDelivery, OcDelivery> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 판매 취소 현황 목록
	 * @Method Name : selectDeliveryOrderVendorCancelList
	 * @Date : 2019. 5. 14.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<OcDelivery> selectDeliveryOrderVendorCancelList(Pageable<OcDelivery, OcDelivery> pageable)
			throws Exception;

	/**
	 * @Desc : 판매취소요청 주문 목록 엑셀 전체 다운로드
	 * @Method Name : selectSellCnclAllExcelDownload
	 * @Date : 2019. 11. 18.
	 * @Author : 이강수
	 * @param Pageable<OcDelivery, OcDelivery>
	 * @return List<OcDelivery>
	 * @throws Exception
	 */
	public List<OcDelivery> selectSellCnclAllExcelDownload(Pageable<OcDelivery, OcDelivery> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 업체별 클레임 건수 조회
	 * @Method Name : selectDelveryOrderVendorClaimCount
	 * @Date : 2019. 5. 15.
	 * @Author : NKB
	 * @param ocDelivery
	 * @return
	 * @throws Exception
	 */
	public List<OcDelivery> selectDelveryOrderVendorClaimCount(OcDelivery ocDelivery) throws Exception;

	/**
	 * @Desc : 업체 주문 배송관리 - count
	 * @Method Name : selectDeliveryOrderVendorCount
	 * @Date : 2019. 4. 21.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Integer selectDeliveryOrderVendorClaimCount(Pageable<OcDelivery, OcDelivery> pageable) throws Exception;

	/**
	 * @Desc : 업체 주문 배송관리 - List
	 * @Method Name : selectDeliveryOrderVendorList
	 * @Date : 2019. 4. 21.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<OcDelivery> selectDeliveryOrderVendorClaimList(Pageable<OcDelivery, OcDelivery> pageable)
			throws Exception;

	/**
	 * 
	 * @Desc : 클레임 불가 요청 현황 - count
	 * @Method Name : selectDeliveryOrderVendorImpossibilityCount
	 * @Date : 2019. 5. 19.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Integer selectDeliveryOrderVendorImpossibilityCount(Pageable<OcDelivery, OcDelivery> pageable)
			throws Exception;

	/**
	 * 
	 * @Desc : 클레임 불가 요청 현황 - list
	 * @Method Name : selectDeliveryOrderVendorImpossibilityList
	 * @Date : 2019. 5. 19.
	 * @Author : NKB
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<OcDelivery> selectDeliveryOrderVendorImpossibilityList(Pageable<OcDelivery, OcDelivery> pageable)
			throws Exception;

	/**
	 * @Desc : 배송진행중인 상품 조회
	 * @Method Name : selectDeliveryPrdt
	 * @Date : 2020. 4. 7.
	 * @Author : 이동엽
	 * @param orderNo
	 * @param orderPrdtSeq
	 * @return
	 * @throws Exception
	 */
	public DeliveryPrdtVO selectDeliveryPrdt(DeliveryPrdtVO params) throws Exception;
	
	/**
	 * @Desc      	: 상품대기조회 처리구분 수정
	 * @Method Name : updateConfirmStandbyProc
	 * @Date  	  	: 2020. 5. 28.
	 * @Author    	: sic
	 * @param param
	 * @throws Exception
	 */
	public void updateConfirmStandbyProc(OcDelivery param)throws Exception;
}
