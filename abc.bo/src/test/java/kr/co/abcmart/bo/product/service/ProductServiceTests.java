package kr.co.abcmart.bo.product.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import kr.co.abcmart.bo.display.model.master.DpCategoryProduct;
import kr.co.abcmart.bo.member.service.MemberService;
import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductOption;
import kr.co.abcmart.bo.product.model.master.PdProductPriceHistory;
import kr.co.abcmart.bo.product.model.master.PdProductRelationFile;
import kr.co.abcmart.bo.product.vo.PdProductMappingVO;
import kr.co.abcmart.bo.product.vo.PdProductOptionStockSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.interfaces.zinterfacesdb.model.master.InterfacesSangPumDetailToErp;
import kr.co.abcmart.interfaces.zinterfacesdb.service.InterfacesProductService;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class ProductServiceTests {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductInsideDetailService productInsideDetailService;
	@Autowired
	private ProductInsideAddInfoService productInsideAddInfoService;
	@Autowired
	private ProductInsideRelationFileService productInsideRelationFileService;
	@Autowired
	private ProductInsideOptionService productInsideOptionService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private InterfacesProductService interfacesProductService;

	@Mock
	HttpServletRequest request;

	@Mock
	HttpServletResponse response;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

//	@Test
	public void testSearchProductOption() throws Exception {
		log.debug("상품 옵션 조회 API 테스트");

		// 파라미터 설정 전 작업

		// 파라미터 설정
		String prdtNo = "1000000";

		List<PdProductOption> result = this.productInsideOptionService.searchProductOption(prdtNo);
		log.debug("{}", result);
	}

//	@Test
	public void testSearchByCtgrNo() throws Exception {
		log.debug("카테고리 번호에 대한 상품 옵션 조회 API 테스트");

		Parameter<DpCategoryProduct> parameter = new Parameter<DpCategoryProduct>(request, response);
		Pageable<DpCategoryProduct, PdProduct> pageable = new Pageable<DpCategoryProduct, PdProduct>(parameter);

		// 파라미터 설정 전 작업

		// 파라미터 설정
		String ctgrNo = "1000";
		DpCategoryProduct bean = new DpCategoryProduct();
		bean.setCtgrNo(ctgrNo);

		Page<PdProduct> result = this.productService.searchByCtgrNo(pageable);
		log.debug("{}", result);
	}

//	@Test
	public void testProductOptionWithStocks() throws Exception {
		log.debug("상품 옵션 및 재고량 조회");

		Parameter<PdProductOptionStockSearchVO> parameter = new Parameter<PdProductOptionStockSearchVO>(request,
				response);
		Pageable<PdProductOptionStockSearchVO, PdProductOption> pageable = new Pageable<PdProductOptionStockSearchVO, PdProductOption>(
				parameter);

		// 파라미터 설정 전 작업
		String[] codeFields = { CommonCode.STOCK_GBN_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.memberService
				.getCodeListByGroupByCombo(codeFields, false);

		// 파라미터 설정
		String prdtNo = "1";
		List<SyCodeDetail> commonCodeList = pair.getSecond().get(CommonCode.STOCK_GBN_CODE);
		PdProductOptionStockSearchVO bean = new PdProductOptionStockSearchVO();
		bean.setPrdtNo(prdtNo);
		bean.setStockGbnCodeList(commonCodeList);
		pageable.setBean(bean);

		Page<PdProductOption> result = this.productInsideOptionService.searchProductOptionWithStocks(pageable);
		log.debug("{}", result);
	}

//	@Test
	public void testInterfacesErpProductService() throws Exception {
		log.debug("인터페이스 연계 테스트");
		String sangpumCd = "0010514";
		String maejangCd = "0072";

		List<InterfacesSangPumDetailToErp> erpSangpum = this.interfacesProductService.selectErpSangPumDetail(sangpumCd,
				maejangCd);
		log.debug("{}", erpSangpum);
	}

//	@Test
	public void testProductOptionWithStockAndAddAmt() throws Exception {
		log.debug("옵션/재고/추가금액 조회 테스트");
		String prdtNo = "1000000008";

		List<PdProductOption> productOptionList = this.productInsideOptionService.getProductOptions(prdtNo);
		log.debug("{}", productOptionList);
	}

	/**
	 * @Desc : 자동승인 가능여부 테스트
	 * @Method Name : testAutoApproval
	 * @Date : 2019. 5. 10.
	 * @Author : tennessee
	 * @throws Exception
	 */
//	@Test
	public void testAutoApproval() throws Exception {
		log.debug("자동승인 가능여부 테스트");
		boolean result1st = false;
		boolean result2nd = false;
		String prdtNo = "2000000022";
		PdProduct product = this.productService.searchProductByPrimaryKey(prdtNo);
		this.productInsideDetailService.setIntoProduct(product);
		this.productInsideAddInfoService.setProductAddInfo(product);
		this.productInsideRelationFileService.setProductRelationFiles(product);
		PdProductPriceHistory[] testPrice = new PdProductPriceHistory[1];
		testPrice[0] = new PdProductPriceHistory();
		testPrice[0].setNormalAmt(10000);
		testPrice[0].setSellAmt(5000);
		product.setProductPriceHistory(testPrice);

		// 1차 테스트 수행 (변동없음)
//		this.productService.setAutoApproval(product);
		result1st = product.getAprvStatCode() == CommonCode.APRV_STAT_CODE_CONFIRM ? true : false;

		// 변경내용 설정
		product.setBrandNo("777");
		product.getProductDetail()[0].setPrdtDtlInfo("이거 좋음.");
		testPrice[0].setNormalAmt(20000);
		testPrice[0].setSellAmt(15000);
		product.setProductPriceHistory(testPrice);

		// 2차 테스트 수행 (변동있음)
//		this.productService.setAutoApproval(product);
		result2nd = product.getAprvStatCode() == CommonCode.APRV_STAT_CODE_CONFIRM ? true : false;

		log.info("변경내용 없음 결과 : {}", result1st);
		log.info("변경내용 있음 결과 : {}", result2nd);
	}

	/**
	 * @Desc : 상품 매핑정보 기준 조회 테스트
	 * @Method Name : testMappingProductList
	 * @Date : 2019. 5. 20.
	 * @Author : tennessee
	 * @throws Exception
	 */
//	@Test
	public void testMappingProductList() throws Exception {
		log.debug("매핑 테이블 기준 상품 조회 테스트");
		Parameter<PdProductMappingVO> parameter = new Parameter<>(request, response);
		Pageable<PdProductMappingVO, Map<String, Object>> pageable = new Pageable<PdProductMappingVO, Map<String, Object>>(
				parameter);
		PdProductMappingVO bean = new PdProductMappingVO();
		bean.setMappingTableName("pr_promotion_target_product");
		bean.setConditionColumnName("promo_no");
		bean.setConditionColumnValue("2000000022");
		bean.setSortColumnName("prdt_no");
		bean.setSortType("ASC");
		pageable.setBean(bean);
//		Page<Map<String, Object>> page = this.productService.searchProductByMapped(pageable);
//		log.debug("{}", page.getGrid());
	}

	/**
	 * @Desc : 상품 한 건에 대한 대표이미지 조회
	 * @Method Name : testProductTitleImage
	 * @Date : 2019. 6. 24.
	 * @Author : tennessee
	 * @throws Exception
	 */
//	@Test
	public void testProductTitleImage() throws Exception {
		log.debug("매핑 테이블 기준 상품 조회 테스트");
		String prdtNo = "2000000045";
		PdProductRelationFile result = this.productInsideRelationFileService.getProductTitleImageByPrdtNo(prdtNo);
		log.debug("{}", result);
	}

	/**
	 * @Desc : 프론트 URL 조회 서비스 테스트
	 * @Method Name : testGetFrontUrl
	 * @Date : 2019. 7. 15.
	 * @Author : tennessee
	 * @throws Exception
	 */
//	@Test
	public void testGetFrontUrl() throws Exception {
		log.debug("프론트 URL 조회 서비스 테스트");
		String prdtNo = "2000000045";
		log.debug("URL : {}", this.productService.getUrl(prdtNo));
		log.debug("URL : {}", this.productService.getUrl(prdtNo, CommonCode.DEVICE_MOBILE));
	}

//	@Test
	public void testSetDisableByBrandNo() throws Exception {
		log.debug("브랜드 사용안함에 의한 상품 비활성화 처리 테스트");
		String brandNo = "000083";
		log.debug("적용 갯수 : {}", this.productService.setDisableByBrandNo(brandNo));
	}

//	@Test
	public void testSetVendorAsMngrText() throws Exception {
		log.debug("입점업체 AS담당자 연락처 일괄변경 서비스 테스트");
		VdVendor vendor = new VdVendor();
		vendor.setVndrNo("VD20000061");
		vendor.setAsMngrText("테스또다!");
		log.debug("적용 갯수 : {}", this.productInsideAddInfoService.setVendorAsMngrText(vendor));
	}

}
