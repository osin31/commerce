package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpCategoryProduct;
import kr.co.abcmart.bo.product.model.master.BdDashboardProduct;
import kr.co.abcmart.bo.product.model.master.PdFreeGift;
import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductMapped;
import kr.co.abcmart.bo.product.model.master.PdRelationProduct;
import kr.co.abcmart.bo.product.repository.master.base.BasePdProductDao;
import kr.co.abcmart.bo.product.vo.PdFreeGiftSearchVO;
import kr.co.abcmart.bo.product.vo.PdProductApprovalSearchVO;
import kr.co.abcmart.bo.product.vo.PdProductMappingVO;
import kr.co.abcmart.bo.product.vo.PdProductSearchVO;
import kr.co.abcmart.bo.product.vo.PdProductSkuExcelListVO;
import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.common.paging.Pageable;

/**
 * @Desc :
 * @FileName : PdProductDao.java
 * @Project : abc.bo
 * @Date : 2019. 4. 1.
 * @Author : tennessee
 */
@Mapper
public interface PdProductDao extends BasePdProductDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePdProductDao 클래스에 구현 되어있습니다.
	 * BasePdProductDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 */

	/**
	 * @Desc : 상품 단건 조회
	 * @Method Name : selectByPrimaryKey
	 * @Date : 2019. 2. 26.
	 * @Author : tennessee
	 * @param pdProduct
	 * @return
	 * @throws Exception
	 */
	public PdProduct selectByPrimaryKey(PdProduct pdProduct) throws Exception;

	/**
	 * @Desc : 상품 다건 조회
	 * @Method Name : selectProduct
	 * @Date : 2019. 2. 26.
	 * @Author : tennessee
	 * @param pdProductSearchVO
	 * @return
	 * @throws Exception
	 */
	public List<PdProduct> selectProduct(Pageable<PdProductSearchVO, PdProduct> criteria) throws Exception;

	/**
	 * @Desc : 옵션별 상품 엑셀 다운로드 리스트 조회
	 * @Method Name : selectProductExcelSku
	 * @Date : 2020. 3. 20.
	 * @Author : 3top
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public List<PdProductSkuExcelListVO> selectProductExcelSku(
			Pageable<PdProductSearchVO, PdProductSkuExcelListVO> criteria) throws Exception;

	/**
	 * @Desc : 상품 갯수 조회
	 * @Method Name : selectProductCount
	 * @Date : 2019. 3. 4.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public Integer selectProductCount(Pageable<PdProductSearchVO, PdProduct> criteria) throws Exception;

	/**
	 * @Desc : 옵션별 상품 엑셀다운로드 카운트 조회
	 * @Method Name : selectProductExcelSkuCount
	 * @Date : 2020. 3. 20.
	 * @Author : 3top
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public Integer selectProductExcelSkuCount(Pageable<PdProductSearchVO, PdProductSkuExcelListVO> criteria)
			throws Exception;

	/**
	 * @Desc : 매핑 테이블 기준 상품 조회
	 * @Method Name : selectProductByMapped
	 * @Date : 2019. 5. 20.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public List<PdProductMapped> selectProductByMapped(Pageable<PdProductMappingVO, PdProductMapped> criteria)
			throws Exception;

	/**
	 * @Desc : 매핑 테이블 기준 상품 전체 갯수 조회
	 * @Method Name : selectProductCountByMapped
	 * @Date : 2019. 5. 20.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public Integer selectProductCountByMapped(Pageable<PdProductMappingVO, PdProductMapped> criteria) throws Exception;

	/**
	 * @Desc : 전시카테고리번호에 대한 상품목록 조회
	 * @Method Name : selectByCtgrNo
	 * @Date : 2019. 3. 13.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public List<PdProduct> selectByCtgrNo(Pageable<DpCategoryProduct, PdProduct> criteria) throws Exception;

	/**
	 * @Desc : 전시카테고리번호에 대한 상품목록 전체 갯수 조회
	 * @Method Name : selectCountByCtgrNo
	 * @Date : 2019. 3. 13.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public Integer selectCountByCtgrNo(Pageable<DpCategoryProduct, PdProduct> criteria) throws Exception;

	/**
	 * @Desc : 상품 등록
	 * @Method Name : insertWithSelectKey
	 * @Date : 2019. 3. 19.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public Integer insertWithSelectKey(PdProduct criteria) throws Exception;

	/**
	 * @Desc : 상품번호 채번
	 * @Method Name : selectKey
	 * @Date : 2019. 12. 20.
	 * @Author : 최경호
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public String selectKey(PdProduct criteria) throws Exception;

	/**
	 * @Desc : 사은품 다건 조회
	 * @Method Name : selectFreeGift
	 * @Date : 2019. 3. 28.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 */
	public List<PdFreeGift> selectFreeGift(Pageable<PdFreeGiftSearchVO, PdFreeGift> pageable) throws Exception;

	/**
	 * @Desc : 사은품 갯수 조회
	 * @Method Name : selectFreeGiftCount
	 * @Date : 2019. 3. 29.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 */
	public Integer selectFreeGiftCount(Pageable<PdFreeGiftSearchVO, PdFreeGift> pageable);

	/**
	 * @Desc : 관련상품 조회
	 * @Method Name : selectRelationProduct
	 * @Date : 2019. 4. 1.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public List<PdProduct> selectRelationProduct(Pageable<PdRelationProduct, PdProduct> criteria) throws Exception;

	/**
	 * @Desc : 관련상품 갯수 조회
	 * @Method Name : selectRelationProductCount
	 * @Date : 2019. 4. 1.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public Integer selectRelationProductCount(Pageable<PdRelationProduct, PdProduct> criteria) throws Exception;

	/**
	 * @Desc : 상품승인 목록 조회
	 * @Method Name : selectProductApproval
	 * @Date : 2019. 4. 4.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 */
	public List<PdProduct> selectProductApproval(Pageable<PdProductApprovalSearchVO, PdProduct> pageable)
			throws Exception;

	/**
	 * @Desc : 상품승인 갯수 조회
	 * @Method Name : selectProductApprovalCount
	 * @Date : 2019. 4. 4.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 */
	public Integer selectProductApprovalCount(Pageable<PdProductApprovalSearchVO, PdProduct> pageable) throws Exception;

	/**
	 * @Desc : 입점업체 상품정보 일시중지여부 일괄변경
	 * @Method Name : updateByVndrStatCode
	 * @Date : 2019. 4. 16.
	 * @Author : tennessee
	 * @param vendor
	 * @return
	 * @throws Exception
	 */
	public Integer updateByVndrStatCode(PdProduct product) throws Exception;

	/**
	 * @Desc : 상품 상세 조회
	 * @Method Name : selectDetail
	 * @Date : 2019. 4. 17.
	 * @Author : tennessee
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public PdProduct selectDetail(PdProduct product) throws Exception;

	/**
	 * @Desc : 승인 처리자 목록 조회
	 * @Method Name : selectAprvAdminList
	 * @Date : 2019. 5. 10.
	 * @Author : hsjhsj19
	 * @return
	 * @throws Exception
	 */
	public List<SyAdmin> selectAprvAdminList() throws Exception;

	/**
	 * @Desc : BO상품 카운트 목록 조회
	 * @Method Name : selectAprvAdminList
	 * @Date : 2019. 6. 7.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public List<BdDashboardProduct> selectProductDashBoardCount() throws Exception;

	/**
	 * @Desc : PO상품 카운트 목록 조회
	 * @Method Name : selectAprvAdminList
	 * @Date : 2019. 6. 7.
	 * @Author : 김영진
	 * @return
	 * @throws Exception
	 */
	public List<BdDashboardProduct> selectProductPoDashBoardCount(BdDashboardProduct productCnt) throws Exception;

	/**
	 * @Desc : 배송비 상품 조회 쿼리
	 * @Method Name : selectProductDlvy
	 * @Date : 2019. 7. 17.
	 * @Author : KTH
	 * @param prdtTypeCode
	 * @return
	 * @throws Exception
	 */
	public List<PdProduct> selectProductDlvy(String prdtTypeCode) throws Exception;

	/**
	 * @Desc : 브랜드 사용안함에 의한 상품 비활성화 처리
	 * @Method Name : updateDisableByBrandNo
	 * @Date : 2019. 9. 25.
	 * @Author : tennessee
	 * @param brandNo
	 * @return
	 * @throws Exception
	 */
	public Integer updateDisableByBrandNo(String brandNo) throws Exception;

	/**
	 * @Desc : IF중간계에 보낼 vndrPrdtNoText 값 얻어오기
	 * @Method Name : selectFreegiftVndrPrdtNoText
	 * @Date : 2019. 11. 14.
	 * @Author : hsjhsj19
	 * @return
	 * @throws Exception
	 */
	public String selectFreegiftVndrPrdtNoText() throws Exception;

	/**
	 * @Desc : vndrPrdtNoText로 해당 사이트에 등록 여부를 조회한다.
	 * @Method Name : selectProductSiteInfo
	 * @Date : 2020. 1. 7.
	 * @Author : kiowa
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public PdProduct selectProductSiteInfo(PdProduct product) throws Exception;

	public Integer selectProductCountNew(Pageable<PdProductSearchVO, PdProduct> criteria) throws Exception;

	/**
	 * @Desc : 상품목록 조회(NEW)
	 * @Method Name : selectProductCountNew
	 * @Date : 2020. 3. 30.
	 * @Author : 3top
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public List<PdProduct> selectProductNew(Pageable<PdProductSearchVO, PdProduct> criteria) throws Exception;

	/**
	 * @Desc : 상품목록 엑셀다운로드(NEW)
	 * @Method Name : selectExcelDownloadProductList
	 * @Date : 2020. 3. 25.
	 * @Author : kiowa
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public List<PdProduct> selectExcelDownloadProductList(Pageable<PdProductSearchVO, PdProduct> criteria)
			throws Exception;
	
	/**
	 * @Desc      	: 연계상품 관련 업데이트
	 * @Method Name : updateCntcPrdtSetup
	 * @Date  	  	: 2020. 4. 20.
	 * @Author    	: sic
	 * @param product
	 * @throws Exception
	 */
	public void updateCntcPrdtSetup(PdProduct product) throws Exception;

}
