package kr.co.abcmart.bo.claim.repository.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.claim.model.master.OcClaim;
import kr.co.abcmart.bo.claim.model.master.OcClaimProduct;
import kr.co.abcmart.bo.claim.repository.master.base.BaseOcClaimProductDao;
import kr.co.abcmart.bo.claim.vo.OcClaimProductExcelVo;
import kr.co.abcmart.bo.claim.vo.OcClaimPromoVO;
import kr.co.abcmart.bo.promotion.model.master.PrPromotionMultiBuyDiscount;

@Mapper
public interface OcClaimProductDao extends BaseOcClaimProductDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcClaimProductDao 클래스에 구현 되어있습니다.
	 * BaseOcClaimProductDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public OcClaimProduct selectByPrimaryKey(OcClaimProduct ocClaimProduct) throws Exception;

	/**
	 * @Desc : 클레임 상품 목록
	 * @Method Name : selectClaimProductList
	 * @Date : 2019. 2. 21.
	 * @Author : KTH
	 * @param ocClaimProduct
	 * @return
	 * @throws Exception
	 */
	public List<OcClaimProduct> selectClaimProductList(OcClaimProduct ocClaimProduct) throws Exception;

	/**
	 * @Desc : 클레임 상품정보 업데이트
	 * @Method Name : updateClaimProduct
	 * @Date : 2019. 3. 5.
	 * @Author : KTH
	 * @param ocClaimProduct
	 * @return
	 * @throws Exception
	 */
	public int updateClaimProduct(OcClaimProduct ocClaimProduct) throws Exception;

	/**
	 * @Desc : 클레임 상품 리스트 엑셀 다운로드
	 * @Method Name : selectOcClaimProductForAllExcelList
	 * @Date : 2019. 3. 6.
	 * @Author : 이강수
	 * @param OcClaimProduct
	 * @return
	 * @throws Exception
	 */
	public List<OcClaimProductExcelVo> selectOcClaimProductForAllExcelList(OcClaimProduct ocClaimProduct)
			throws Exception;

	/**
	 * @Desc : 클레임 상품 리스트 선택 엑셀 다운로드
	 * @Method Name : selectOcClaimProductForExcelList
	 * @Date : 2019. 3. 7.
	 * @Author : 이강수
	 * @param OcClaimProductExcelVo
	 * @return
	 * @throws Exception
	 */
	public List<OcClaimProductExcelVo> selectOcClaimProductForExcelList(OcClaimProductExcelVo ocClaimProductExcelVo)
			throws Exception;

	/**
	 * @Desc :
	 * @Method Name : selectOrderClaimArr
	 * @Date : 2019. 3. 8.
	 * @Author : flychani@3top.co.kr
	 * @param orderNos
	 * @return
	 */
	public List<OcClaimProduct> selectClaimProductListForOrder(OcClaimProduct ocClaimProduct) throws Exception;

	/**
	 * @Desc : 클레임 상품 저장
	 * @Method Name : insertClaimProduct
	 * @Date : 2019. 3. 12.
	 * @Author : KTH
	 * @param ocClaimproduct
	 * @return
	 * @throws Exception
	 */
	public int insertClaimProduct(OcClaimProduct ocClaimProduct) throws Exception;

	/**
	 * @Desc : 클레임불가 처리에 따른 클레임 상품 업데이트
	 * @Method Name : updateClaimProductImpossible
	 * @Date : 2019. 3. 13.
	 * @Author : 이강수
	 * @param OcClaimProduct
	 * @throws Exception
	 */
	public int updateClaimProductImpossible(OcClaimProduct ocClaimProduct) throws Exception;

	/**
	 * @Desc : 클클레임상품 상세 조회
	 * @Method Name : selectClaimProductDetail
	 * @Date : 2019. 3. 13.
	 * @Author : 이강수
	 * @param OcClaimProduct
	 * @throws Exception
	 */
	public OcClaimProduct selectClaimProductDetail(OcClaimProduct ocClaimProduct) throws Exception;

	/**
	 * @Desc : 클레임상품 상태코드 업데이트
	 * @Method Name : updateClaimProductStatCode
	 * @Date : 2019. 4. 1.
	 * @Author : KTH
	 * @param ocClaimProduct
	 * @return
	 * @throws Exception
	 */
	public int updateClaimProductStatCode(OcClaimProduct ocClaimProduct) throws Exception;

	/**
	 * @Desc : 교환대상 상품 수정
	 * @Method Name : updateClmChangeOptn
	 * @Date : 2019. 7. 1.
	 * @Author : KTH
	 * @param ocClaimProduct
	 * @return
	 * @throws Exception
	 */
	public int updateClmChangeOptn(OcClaimProduct ocClaimProduct) throws Exception;

	/**
	 * @Desc : 클레임상품에 적용된 다족구매 프로모션 정보 조회
	 * @Method Name : selectMultiBuyPromoInfoList
	 * @Date : 2019. 4. 18.
	 * @Author : KTH
	 * @param ocClaimProduct
	 * @return
	 * @throws Exception
	 */
	public List<PrPromotionMultiBuyDiscount> selectMultiBuyPromoInfoList(OcClaimProduct ocClaimProduct)
			throws Exception;

	/**
	 * @Desc : 원 주문에 걸린 다족구매 프로모션 현황 조회(현재 클레임 포함)
	 * @Method Name : selectOrderMultiBuyPromoCheckList
	 * @Date : 2019. 4. 22.
	 * @Author : KTH
	 * @param ocClaimProduct
	 * @return
	 * @throws Exception
	 */
	public List<OcClaimPromoVO> selectOrderMultiBuyPromoCheckList(OcClaimProduct ocClaimProduct) throws Exception;

	/**
	 * @Desc : 클레임 상품 목록 조회 - backend
	 * @Method Name : selectClaimProductListBackend
	 * @Date : 2019. 7. 9.
	 * @Author : 이강수
	 * @param OcClaim
	 * @return
	 * @throws Exception
	 */
	public List<OcClaimProduct> selectClaimProductListBackend(OcClaim ocClaim) throws Exception;

	/**
	 * @Desc : 원 주문 클레임 상품 목록 조회 쿼리
	 * @Method Name : selectOrgClaimProductList
	 * @Date : 2019. 5. 27.
	 * @Author : KTH
	 * @param ocClaimProduct
	 * @return
	 * @throws Exception
	 */
	public List<OcClaimProduct> selectOrgClaimProductList(OcClaimProduct ocClaimProduct) throws Exception;

	/**
	 * @Desc : 클레임 상품 사유코드 그룹목록 조회 쿼리
	 * @Method Name : selectClmPrdtRsnCodeGroup
	 * @Date : 2019. 7. 24.
	 * @Author : 이강수
	 * @param ocClaimProduct
	 * @return
	 * @throws Exception
	 */
	public List<OcClaimProduct> selectClmPrdtRsnCodeGroup(OcClaimProduct ocClaimProduct) throws Exception;

	/**
	 * @Desc : 취소 상품 금액과 취소 배송 금액을 조회
	 * @Method Name : selectCancelPrdtAmtCancelDlvyAmt
	 * @Date : 2019. 8. 7.
	 * @Author : 이강수
	 * @param OcClaim
	 * @return OcClaimProduct
	 * @throws Exception
	 */
	public OcClaimProduct selectCancelPrdtAmtCancelDlvyAmt(OcClaim ocClaimProduct) throws Exception;

	/**
	 * @Desc : 클레임생성 리오더 주문번호 업데이트
	 * @Method Name : updateClmCrtOrderNo
	 * @Date : 2019. 9. 2.
	 * @Author : KTH
	 * @param ocClaimProduct
	 * @return
	 * @throws Exception
	 */
	public int updateClmCrtOrderNo(OcClaimProduct ocClaimProduct) throws Exception;

	/**
	 * @Desc : IF_ORDER 주문취소 상태 값 변경
	 * @Method Name : updateInterfaceOrderCancel
	 * @Date : 2019. 10. 8.
	 * @Author : KTH
	 * @param dlvyIdText
	 * @return
	 * @throws Exception
	 */
	public int updateInterfaceOrderCancel(String dlvyIdText) throws Exception;

	/**
	 * @Desc : 원주문번호에 걸리는 모든 클레임 상품을 클레임상품 테이블에서만 조회
	 * @Method Name : selectClmPrdtListByOrgOrderNo
	 * @Date : 2020. 2. 6.
	 * @Author : 이강수
	 * @param OcClaimProduct
	 * @return
	 * @throws Exception
	 */
	public List<OcClaimProduct> selectClmPrdtListByOrgOrderNo(OcClaimProduct ocClaimProduct) throws Exception;

	/**
	 * @Desc : 주문취소 프로시져 호출
	 * @Method Name : callProcedureForOrderCancel
	 * @Date : 2020. 2. 7.
	 * @Author : 이강수
	 * @param Map<String, String> map
	 * @return
	 * @throws Exception
	 */
	public void callProcedureForOrderCancel(Map<String, String> paramMap) throws Exception;

	/**
	 * @Desc : 회수지시 프로시져 호출 / 성공 : "0"
	 * @Method Name : callProcedureForOrderReturn
	 * @Date : 2020. 2. 19.
	 * @Author : 이강수
	 * @param Map<String, String> map
	 * @return
	 * @throws Exception
	 */
	public void callProcedureForOrderReturn(Map<String, String> orderParamMap) throws Exception;

	/**
	 * @Desc : 배송중단 프로시져 호출 / 성공 : "0"
	 * @Method Name : callProcedureForOrderHold
	 * @Date : 2020. 2. 19.
	 * @Author : 이강수
	 * @param Map<String, String> map
	 * @return
	 * @throws Exception
	 */
	public void callProcedureForOrderHold(Map<String, String> orderParamMap) throws Exception;

	/**
	 * @Desc : 클레임번호로 클레임 상품 목록 조회 쿼리  (교환재배송상품 포함)
	 * @Method Name : selectClaimProductListByClmNo
	 * @Date : 2020. 4. 23.
	 * @Author : 이강수
	 * @param OcClaimProduct
	 * @return
	 * @throws Exception
	 */
	public List<OcClaimProduct> selectClaimProductListByClmNo(OcClaimProduct ocClaimProduct) throws Exception;
	
	/**
	 * @Desc : 주문정보로 교환재배송 상품 조회 쿼리
	 * @Method Name : selectClmExchangeProduct
	 * @Date : 2020. 4. 28.
	 * @Author : 이강수
	 * @param OcClaimProduct
	 * @return
	 * @throws Exception
	 */
	public OcClaimProduct selectClmExchangeProduct(OcClaimProduct ocClaimProduct) throws Exception;
}
