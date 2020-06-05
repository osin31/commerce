package kr.co.abcmart.bo.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.display.model.master.DpCategoryProduct;
import kr.co.abcmart.bo.display.service.DisplayCategoryService;
import kr.co.abcmart.bo.product.model.master.BdDashboardProduct;
import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductChangeHistory;
import kr.co.abcmart.bo.product.model.master.PdProductMapped;
import kr.co.abcmart.bo.product.model.master.PdRelationProduct;
import kr.co.abcmart.bo.product.repository.master.PdProductDao;
import kr.co.abcmart.bo.product.vo.PdProductMappingVO;
import kr.co.abcmart.bo.product.vo.PdProductSearchVO;
import kr.co.abcmart.bo.product.vo.PdProductSkuExcelListVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySiteChnnl;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.model.master.VdVendorDisplayChnnl;
import kr.co.abcmart.bo.vendor.service.VendorService;
import kr.co.abcmart.common.config.Config;
import kr.co.abcmart.common.message.Message;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsCollection;
import kr.co.abcmart.common.util.UtilsDevice;
import kr.co.abcmart.common.util.UtilsMasking;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품 서비스
 * @FileName : ProductService.java
 * @Project : abc.bo
 * @Date : 2019. 4. 17.
 * @Author : tennessee
 */
@Slf4j
@Service
public class ProductService {

	@Autowired
	private PdProductDao productDao;

	@Autowired
	private ProductInsideDetailService productInsideDetailService;
	@Autowired
	private ProductInsideRelationProductService productInsideRelationProductService;
	@Autowired
	private ProductInsideColorService productInsideColorService;
	@Autowired
	private ProductInsideIconService productInsideIconService;
	@Autowired
	private ProductInsideAddInfoService productInsideAddInfoService;
	@Autowired
	private ProductInsidePriceHistoryService productInsidePriceHistoryService;
	@Autowired
	private ProductInsideChangeHistoryService productInsideChangeHistoryService;
	@Autowired
	private ProductInsideApprovalHistoryService productInsideApprovalHistoryService;
	@Autowired
	private ProductInsideOptionService productInsideOptionService;
	@Autowired
	private ProductInsideRelationFileService productInsideRelationFileService;
	@Autowired
	private ProductInterfaceService productInterfaceService;

	@Autowired
	private ProductReflectionService productReflectionService;

	@Autowired
	private SiteService siteService;
	@Autowired
	private DisplayCategoryService displayCategoryService;
	@Autowired
	private VendorService vendorService;
	@Autowired
	private CommonCodeService commonCodeService;

	private static final String CHANNEL_NO_ART = "10000";

	/**
	 * @Desc : 상품 번호를 통한 단건 상품 조회
	 * @Method Name : getProduct
	 * @Date : 2019. 2. 26.
	 * @Author : tennessee
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public PdProduct getProduct(String prdtNo, String siteNo, String chnnlNo, String vndrPrdtNoText) throws Exception {
		PdProduct criteria = new PdProduct();
		UserDetails user = LoginManager.getUserDetails();
		criteria.setPrdtNo(UtilsText.isNotBlank(prdtNo) ? prdtNo : null);
		criteria.setSiteNo(UtilsText.isNotBlank(siteNo) ? siteNo : null);
		criteria.setChnnlNo(UtilsText.isNotBlank(chnnlNo) ? chnnlNo : null);
		criteria.setVndrPrdtNoText(UtilsText.isNotBlank(vndrPrdtNoText) ? vndrPrdtNoText : null);

		if (UtilsText.equals(LoginManager.getUserDetails().getUpAuthNo(), Const.ROLE_VENDER_GROUP)) {
			// 입점사인 경우, 입점사 상품만 조회될 수 있도록 추가
			criteria.setVndrNo(user.getVndrNo());
			criteria.setMmnyPrdtYn("N");
		}

		PdProduct result = this.productDao.selectDetail(criteria);

		if (UtilsObject.isNotEmpty(result)) {
			result.setPrivacy(false);
		}

		return result;
	}

	/**
	 * @Desc : 상품 검색을 통한 다건 상품 조회
	 * @Method Name : searchProduct
	 * @Date : 2019. 2. 26.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PdProduct> searchProduct(Pageable<PdProductSearchVO, PdProduct> pageable) throws Exception {
		if (UtilsText.equals(LoginManager.getUserDetails().getUpAuthNo(), Const.ROLE_VENDER_GROUP)) {
			pageable.getBean().setVndrNo(LoginManager.getUserDetails().getVndrNo());
		}
		Integer count = this.productDao.selectProductCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			// 재고구분코드 설정
			String[] codeFields = { CommonCode.STOCK_GBN_CODE };
			Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
					.getCodeListByGroupByCombo(codeFields, false);
			pageable.getBean().setStockGbnCodeList(pair.getSecond().get(CommonCode.STOCK_GBN_CODE));

			pageable.setContent(this.productDao.selectProduct(pageable));

			for (PdProduct item : pageable.getContent()) {
				item.setPrivacy(true); // 목록 페이지 강제 마스킹 적용
			}
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 옵션별 상품 엑셀 다운로드
	 * @Method Name : searchProductExcelSku
	 * @Date : 2020. 3. 19.
	 * @Author : 이동엽
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PdProductSkuExcelListVO> searchProductExcelSku(
			Pageable<PdProductSearchVO, PdProductSkuExcelListVO> pageable) throws Exception {
		Integer count = this.productDao.selectProductExcelSkuCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(this.productDao.selectProductExcelSku(pageable));
		}
		return pageable.getPage();
	}

	/**
	 * @Desc : 매핑테이블 기준 상품정보를 조회하여 반환
	 * @Method Name : searchProductByMapped
	 * @Date : 2019. 5. 20.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PdProductMapped> searchProductByMapped(Pageable<PdProductMappingVO, PdProductMapped> pageable)
			throws Exception {
		Integer count = this.productDao.selectProductCountByMapped(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			// 재고구분코드 설정
			String[] codeFields = { CommonCode.STOCK_GBN_CODE };
			Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
					.getCodeListByGroupByCombo(codeFields, false);
			pageable.getBean().setStockGbnCodeList(pair.getSecond().get(CommonCode.STOCK_GBN_CODE));

			pageable.setContent(this.productDao.selectProductByMapped(pageable));

			for (Map<String, Object> item : pageable.getContent()) {
				// 개인정보보호 설정
				item.replace("aprverId", String.valueOf(item.get("aprverId")),
						UtilsMasking.loginId(String.valueOf(item.get("aprverId"))));
				item.replace("aprverName", String.valueOf(item.get("aprverName")),
						UtilsMasking.userName(String.valueOf(item.get("aprverName"))));
				item.replace("rgsterId", String.valueOf(item.get("rgsterId")),
						UtilsMasking.loginId(String.valueOf(item.get("rgsterId"))));
				item.replace("rgsterName", String.valueOf(item.get("rgsterName")),
						UtilsMasking.userName(String.valueOf(item.get("rgsterName"))));
				item.replace("moderId", String.valueOf(item.get("moderId")),
						UtilsMasking.loginId(String.valueOf(item.get("moderId"))));
				item.replace("moderName", String.valueOf(item.get("moderName")),
						UtilsMasking.userName(String.valueOf(item.get("moderName"))));
			}
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 입점업체 상품정보 입점업체상태에 따른 일시중지여부 일괄변경
	 * @Method Name : updateVndrSuspdYn
	 * @Date : 2019. 4. 23.
	 * @Author : tennessee
	 * @param vendor
	 * @return
	 * @throws Exception
	 */
	public Integer updateVndrSuspdYn(VdVendor vendor) throws Exception {
		log.debug("입점업체 상품정보 일시중지여부 일괄변경. 업체번호 : {}, 일시중지코드값 : {}", vendor.getVndrNo(), vendor.getVndrStatCode());
		String vndrSuspdYn = "N";

		switch (vendor.getVndrStatCode()) {
		case CommonCode.VNDR_STAT_CODE_IN_TRANSIT:
			// 거래중인 경우
			vndrSuspdYn = "N";
			break;
		case CommonCode.VNDR_STAT_CODE_PAUSE:
			// 일시중지인 경우
			vndrSuspdYn = "Y";
			break;
		case CommonCode.VNDR_STAT_CODE_END_TRANSIT:
			// 거래종료인 경우
			vndrSuspdYn = "Y";
			break;
		default:
			log.debug("알 수 없는 유형입니다. {}", vendor.getVndrStatCode());
			return 0;
		}

		PdProduct criteria = new PdProduct();
		criteria.setVndrNo(vendor.getVndrNo());
		criteria.setVndrSuspdYn(vndrSuspdYn);
		return this.productDao.updateByVndrStatCode(criteria);
	}

	/**
	 * @Desc : 상품 속성정보 기반 조회
	 * @Method Name : searchProduct
	 * @Date : 2019. 3. 4.
	 * @Author : tennessee
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public List<PdProduct> searchProduct(PdProduct product) throws Exception {
		return this.productDao.select(product);
	}

	/**
	 * @Desc : 상품 기본키 정보를 통한 상품 조회
	 * @Method Name : searchProductByPrimaryKey
	 * @Date : 2019. 3. 4.
	 * @Author : tennessee
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public PdProduct searchProductByPrimaryKey(String prdtNo) throws Exception {
		PdProduct criteria = new PdProduct();
		criteria.setPrdtNo(prdtNo);
		return this.productDao.selectByPrimaryKey(criteria);
	}

	/**
	 * @Desc : 자사/입점상품번호와 관련된 상품정보를 채널기준으로 조회하여 맵 객체에 설정. 맵에 저장된 키는 채널번호.
	 * @Method Name : searchAssociatedProductsByChannels
	 * @Date : 2019. 3. 5.
	 * @Author : tennessee
	 * @param channelList
	 * @param product
	 * @return
	 * @throws Exception
	 */
	private Map<String, PdProduct> searchAssociatedProductsByChannels(PdProduct product) throws Exception {
		Map<String, PdProduct> result = null;

		List<SySiteChnnl> channelList = this.siteService.getUseChannelList();

		if (UtilsCollection.isNotEmpty(channelList) && UtilsText.isNotBlank(product.getVndrPrdtNoText())) {
			result = new HashMap<String, PdProduct>();

			PdProduct criteria = new PdProduct();
			List<PdProduct> associatedProducts = null;
			criteria.setMmnyPrdtYn(Const.BOOLEAN_TRUE);
			criteria.setVndrPrdtNoText(product.getVndrPrdtNoText());

			for (SySiteChnnl item : channelList) {
				criteria.setPrdtNo(null);
				criteria.setChnnlNo(item.getChnnlNo());

				if (UtilsText.isNotBlank(product.getPrdtNo())
						&& UtilsText.equals(item.getChnnlNo(), product.getChnnlNo())) {
					// 동일 채널에 동일 입점업체상품정보로 중복된 상품이 있는 경우, 현재 상품번호로 조회
					criteria.setPrdtNo(product.getPrdtNo());
				}

				associatedProducts = this.productDao.select(criteria);

				if (UtilsCollection.isNotEmpty(associatedProducts)) {
					result.put(item.getChnnlNo(), associatedProducts.get(0));
				}
			}
		}
		return result;
	}

	/**
	 * @Desc : 상품 저장
	 * @Method Name : registProduct
	 * @Date : 2019. 4. 17.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	public void regist(PdProduct product) throws Exception {
		if (UtilsText.isNotBlank(product.getType())) {
			try {
				log.error("상품 저장  >>>>  adminNo = {}, params = {} "
							, LoginManager.getUserDetails().getAdminNo(), UtilsText.stringify(product));
			}catch(Exception e) {
				log.error("ex ={}", e);
			}

			switch (product.getType()) {
			case PdProduct.TYPE_TEMPORARY:
				if (UtilsText.isEmpty(product.getPrdtNo())) { // 상품번호 채번이 안되어 있으면 상품 등록
					this.insertProduct(product);
				} else {
					this.updateProduct(product);
				}
				break;
			case PdProduct.TYPE_SAVE:
				this.insertProduct(product);
				break;
			case PdProduct.TYPE_MODIFY:
				this.updateProduct(product);
				break;
			default:
				log.debug("유형이 올바르지 않습니다. {}", product.getType());
			}
		}
	}

	/**
	 * @Desc : 상품 정보 등록
	 * @Method Name : insertProduct
	 * @Date : 2019. 4. 17.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	private void insertProduct(PdProduct product) throws Exception {
		// ============================================================
		// step0. 기본 정보 설정
		// ============================================================
		if (UtilsText.equals(Const.BOOLEAN_TRUE, product.getMmnyPrdtYn())) {
			// 자사상품인 경우, 등록상품채널에 따른 입점사번호 설정
			SySiteChnnl sySiteChnnl = this.siteService.getVendorNo(product);
			product.setVndrNo(sySiteChnnl.getVndrNo());

			// 자사상품의 경우 SY_SITE_CHNNL 테이블의 채널상품구분번호[CHNNL_PRDT_GBN_NO]로 이용하여 상품을 채번한다.
			// 입점사 상품의 경우 기존과 같이 sequesce를 이용해서 채번한다.
			String chnnlPrdtGbnNo = sySiteChnnl.getChnnlPrdtGbnNo(); // 채널상품구분번호
			product.setPrdtNo(chnnlPrdtGbnNo + product.getVndrPrdtNoText());

			if (product.getPrdtNo().length() > 10) {
				throw new Exception("상품번호가 올바르지 않습니다. 상품번호는 10자리입니다.");
			}
		} else {
			// 입점업체상품
			if (UtilsText.isBlank(product.getVndrNo())) {
				if (UtilsText.equals(LoginManager.getUserDetails().getAuthApplySystemType(),
						Const.AUTH_APPLY_SYSTEM_TYPE_BO)) {
					throw new Exception("업체번호가 없습니다. BO권한사용자가 입점업체상품 등록 시 발생함.");
				} else {
					product.setVndrNo(LoginManager.getUserDetails().getVndrNo());
					// 입점업체일경우 첫 등록시에 최종입고일 들어가지 않아야함
					product.setLastWrhsDay(null);
				}
			} else {
				// 입점업체일경우 첫 등록시에 최종입고일 들어가지 않아야함
				product.setLastWrhsDay(null);
			}
		}
		product.setDefaultDataWhenRegist();

		// ============================================================
		// step1. 상품 기본정보 저장
		// ============================================================
		// step1-1. 상품 저장 (자동승인/변경이력/승인이력 포함)
		this.insertProductDefault(product);
		// step1-2. 상품상세 저장
		this.productInsideDetailService.insert(product);
		// step1-3-1. 관련상품 중 색상연계상품 저장
		this.productInsideRelationProductService.regist(product, product.getCntcPrdtSetupYn(),
				product.getCntcPrdtSetupList(), CommonCode.RLTN_PRDT_TYPE_CODE_PRODUCT);
		// step1-3-2. 관련상품 중 관련용품 저장
		this.productInsideRelationProductService.regist(product, product.getRltnGoodsSetupYn(),
				product.getRltnGoodsSetupList(), CommonCode.RLTN_PRDT_TYPE_CODE_GOODS);
		// step1-4. 상품아이콘 저장
		this.productInsideIconService.regist(product);
		// step1-5. 상품색상 저장
		this.productInsideColorService.regist(product);
		// step1-6. 상품추가정보 저장
		this.productInsideAddInfoService.regist(product);
		// step1-7. 상품관련파일 저장 (이미지, 3D 등)
		this.productInsideRelationFileService.regist(product);
		// step1-8. 상품가격이력 저장
		this.productInsidePriceHistoryService.insert(product);

		// ============================================================
		// step2. 상품 옵션정보 저장
		// ============================================================
		// step2-1. 상품옵션저장 (재고/가격이력/변경이력 포함)
		this.productInsideOptionService.regist(product);

		// ============================================================
		// step3. 타 서비스 정보 입력 처리
		// ============================================================
		// step3-1. 전시 카테고리
		this.displayCategoryService.insertDisplayCategory(product);

		// ============================================================
		// step4. 자사 상품인 경우, 중간계에 상품등록완료 알림
		// ============================================================
		if (UtilsText.equals("Y", product.getMmnyPrdtYn())) {
			this.productInterfaceService.sendErpProductRegistered(product);

			// 사방넷 재고 신규등록을 위한 재고조회 및 사방넷 재고 등록 작업
			this.productInterfaceService.setStockForSabangnet(product);
		}
	}

	/**
	 * @Desc : 상품 정보 수정
	 * @Method Name : updateProduct
	 * @Date : 2019. 4. 17.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	private void updateProduct(PdProduct product) throws Exception {
		// ============================================================
		// step0. 기본 정보 설정
		// ============================================================
		product.setDefaultDataWhenRegist();

		// ============================================================
		// step1. 상품 기본정보 저장
		// ============================================================
		// step1-1. 상품 저장 (자동승인/변경이력/승인이력 포함)
		this.updateProductDefault(product);
		// step1-2. 상품상세 저장
		this.productInsideDetailService.regist(product);
		// step1-3-1. 관련상품 중 색상연계상품 저장
		this.productInsideRelationProductService.regist(product, product.getCntcPrdtSetupYn(),
				product.getCntcPrdtSetupList(), CommonCode.RLTN_PRDT_TYPE_CODE_PRODUCT);
		// step1-3-2. 관련상품 중 관련용품 저장
		this.productInsideRelationProductService.regist(product, product.getRltnGoodsSetupYn(),
				product.getRltnGoodsSetupList(), CommonCode.RLTN_PRDT_TYPE_CODE_GOODS);
		// step1-4. 상품아이콘 저장
		this.productInsideIconService.regist(product);
		// step1-5. 상품색상 저장
		this.productInsideColorService.regist(product);
		// step1-6. 상품추가정보 저장
		this.productInsideAddInfoService.regist(product);
		// step1-7. 상품관련파일 저장 (이미지, 3D 등)
		this.productInsideRelationFileService.regist(product);

		// step1-8. 상품가격이력 저장 = 입점사 상품일 경우에만 수정
		if (UtilsText.equals(product.getMmnyPrdtYn(), Const.BOOLEAN_FALSE)
				&& UtilsText.equals(product.getOrgPriceChangeYn(), Const.BOOLEAN_TRUE)) {
			this.productInsidePriceHistoryService.insert(product);
		}

		// ============================================================
		// step2. 상품 옵션정보 갱신
		// ============================================================
		// step2-1. 상품옵션저장 (재고/가격이력/변경이력 포함)
		this.productInsideOptionService.regist(product);

		// ============================================================
		// step3. 타 서비스 정보 갱신
		// ============================================================
		// step3-1. 전시 카테고리
		this.displayCategoryService.insertDisplayCategory(product);
	}

	/**
	 * @Desc : 전시카테고리 번호에 대한 상품정보 조회
	 * @Method Name : searchByCtgrNo
	 * @Date : 2019. 3. 13.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PdProduct> searchByCtgrNo(Pageable<DpCategoryProduct, PdProduct> pageable) throws Exception {
		Integer count = this.productDao.selectCountByCtgrNo(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(this.productDao.selectByCtgrNo(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 상품 정보 저장
	 * @Method Name : insertProductDefault
	 * @Date : 2019. 4. 23.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	private void insertProductDefault(PdProduct product) throws Exception {

		if (UtilsText.equals(PdProduct.TYPE_TEMPORARY, product.getType())) {
			product.setAprvStatCode(CommonCode.APRV_STAT_CODE_TEMPORARY); // 방어용
		}

		// step4. 상품 등록 (승인이력 저장을 위한 상품정보 입력)
		this.productReflectionService.setUserInfo(product);

		// 입점업체 상품일 경우만 상품번호[prdtNo]를 채번한다.[자사상품은 10자리로 별도 채번함]
		if (UtilsText.equals(Const.BOOLEAN_FALSE, product.getMmnyPrdtYn())) {
			product.setPrdtNo(productDao.selectKey(product));
		}

		this.productDao.insertWithSelectKey(product);

		if (UtilsText.equals(PdProduct.TYPE_TEMPORARY, product.getType())) {
			// 임시저장과정에서는 자동승인 및 변경/승인이력 작업하지 않음

			// 결과메시지 설정
			product.setResultMessage(Message.getMessage("product.result.save.aprvStatCode.temporary"));

		} else {

			// step1. 자동승인 점검. 자동승인 대상이 아니므로 수행하지 않음
			// step2. 상품변경이력 저장. 신규이므로 수행하지 않음
			// step3. 상품승인이력 저장. 최초등록은 신규상품으로 승인이력 저장. 상품승인코드 재설정
			this.productInsideApprovalHistoryService.insert(product);

		}

		// 상품 승인정보 수정 (승인정보 갱신을 위한 수정)
		this.productDao.update(product);

		// 결과메시지 설정
		if (UtilsText.equals(Const.BOOLEAN_TRUE, product.getMmnyPrdtYn())) {
			// 자사인 경우, 저장됨 메시지 설정
			product.setResultMessage(Message.getMessage("product.result.save.aprvStatCode.confirm"));
		} else {
			// 입점인 경우
			if (UtilsText.equals(CommonCode.APRV_STAT_CODE_TEMPORARY, product.getAprvStatCode())) {
				// 임시저장인 경우
				product.setResultMessage(Message.getMessage("product.result.save.aprvStatCode.temporary"));
			} else {
				// 승인요청인 경우
				product.setResultMessage(Message.getMessage("product.result.save.aprvStatCode.request"));
			}
		}
	}

	/**
	 * @Desc : 상품 정보 갱신
	 * @Method Name : updateProductDefault
	 * @Date : 2019. 5. 10.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	private void updateProductDefault(PdProduct product) throws Exception {
		if (UtilsText.equals(PdProduct.TYPE_TEMPORARY, product.getType())) {
			// 임시저장은 상품변경이력/승인이력 저장하지 않음.

			// 승인상태코드 임시저장 설정 (방어용)
			product.setAprvStatCode(CommonCode.APRV_STAT_CODE_TEMPORARY);

		} else {

			// step1. 자동승인 점검
			this.setAutoApproval(product);
			// step2. 상품변경이력 저장. 변경이력 검사 후 승인요청코드 설정됨.
			this.productInsideChangeHistoryService.insert(product);
			// step3. 상품승인이력 저장. 상품승인코드 재설정
			this.productInsideApprovalHistoryService.insert(product);

		}
		// step4. 상품 갱신
		this.productReflectionService.setUserInfo(product);
		this.productDao.update(product);

		// 결과메시지 설정
		if (UtilsText.equals(Const.BOOLEAN_TRUE, product.getMmnyPrdtYn())) {
			// 자사인 경우, 저장됨 메시지 설정
			product.setResultMessage(Message.getMessage("product.result.save.aprvStatCode.confirm"));
		} else {
			// 입점인 경우
			if (UtilsText.isNotBlank(product.getResultMessage())) {
				// 결과메시지가 설정된 경우 (자동승인 된 경우)
				product.setResultMessage(Message.getMessage("product.result.save.aprvStatCode.confirm.auto"));
			} else {
				if (UtilsText.equals(CommonCode.APRV_STAT_CODE_TEMPORARY, product.getAprvStatCode())) {
					// 임시저장인 경우
					product.setResultMessage(Message.getMessage("product.result.save.aprvStatCode.temporary"));
				} else {
					// 승인요청인 경우
					product.setResultMessage(Message.getMessage("product.result.save.aprvStatCode.request"));
				}
			}
		}

	}

	/**
	 * @Desc : 관련상품 조회 서비스. 색상연관상품코드 "10000", 관련용품코드 "10001"
	 * @Method Name : searchRelatedProduct
	 * @Date : 2019. 4. 1.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PdProduct> searchRelationProduct(Pageable<PdRelationProduct, PdProduct> pageable) throws Exception {
		Integer count = this.productDao.selectRelationProductCount(pageable);
		pageable.setTotalCount(count);
		if (count > 0) {
			pageable.setContent(this.productDao.selectRelationProduct(pageable));
		}
		return pageable.getPage();
	}

	/**
	 * @Desc : 상품 수정 (A-Connect 전시여부)
	 * @Method Name : updateProductForAconnect
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param products
	 * @throws Exception
	 */
	public void updateProductForAconnect(PdProduct[] products) throws Exception {

		for (PdProduct product : products) {

			this.productInsideChangeHistoryService.insertForAconnect(product);
			this.productReflectionService.setUserInfo(product);
			this.productDao.update(product);
		}
	}

	/**
	 * @Desc : 입점사 등록가능채널 반환. 자사반환형과 동일하게 변경됨.
	 * @Method Name : getAvailableChannelInfoForVendor
	 * @Date : 2019. 5. 3.
	 * @Author : tennessee
	 * @param vndrNo
	 * @param vndrPrdtNoText
	 * @return
	 * @throws Exception
	 */
	private Map<String, PdProduct> getAvailableChannelInfoForVendor(String vndrNo, String vndrPrdtNoText)
			throws Exception {
		Map<String, PdProduct> result = null;

		// 입점상품. 자사상품 반환형과 동일하게 맞춤.
		VdVendorDisplayChnnl criteriaForVendorChannel = new VdVendorDisplayChnnl();
		criteriaForVendorChannel.setVndrNo(vndrNo);
		List<VdVendorDisplayChnnl> vendorChannelList = this.vendorService
				.selectVendorDisplayChnnlList(criteriaForVendorChannel);

		result = new HashMap<String, PdProduct>();
		PdProduct criteria = new PdProduct();
		for (VdVendorDisplayChnnl item : vendorChannelList) {
			criteria.setPrdtNo(null);
			criteria.setSiteNo(null);
			criteria.setChnnlNo(item.getChnnlNo());
			criteria.setVndrNo(vndrNo);
			criteria.setVndrPrdtNoText(vndrPrdtNoText);
			criteria.setMmnyPrdtYn(Const.BOOLEAN_FALSE);

			List<PdProduct> channelRelatedProducts = this.productDao.select(criteria);

			if (UtilsCollection.isEmpty(channelRelatedProducts)) {
				result.put(item.getChnnlNo(), null);
			} else {
				result.put(item.getChnnlNo(), channelRelatedProducts.get(channelRelatedProducts.size() - 1));
			}
		}

		return result;
	}

	/**
	 * @Desc : 상품에 대한 채널별 정보 반환
	 * @Method Name : getProductInfoAboutEachChannel
	 * @Date : 2019. 5. 3.
	 * @Author : tennessee
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public Map<String, PdProduct> getProductInfoAboutEachChannel(PdProduct product) throws Exception {
		Map<String, PdProduct> result = null;

		if (UtilsObject.isNotEmpty(product)) {
			if (UtilsText.equals(product.getMmnyPrdtYn(), "Y")) {
				// 자사상품
				result = this.searchAssociatedProductsByChannels(product);
			} else {
				// 입점상품. 자사상품 반환형과 동일하게 맞춤.
				if (UtilsText.equalsIgnoreCase(LoginManager.getUserDetails().getUpAuthNo(), Const.ROLE_ADMIN_GROUP)) {
					result = this.getAvailableChannelInfoForVendor(product.getVndrNo(), product.getVndrPrdtNoText());
				} else {
					result = this.getAvailableChannelInfoForVendor(LoginManager.getUserDetails().getVndrNo(),
							product.getVndrPrdtNoText());
				}
			}
		} else {
			// 상품정보 없음. 자사 또는 입점 상품 신규등록 경우
			if (UtilsText.equalsIgnoreCase(LoginManager.getUserDetails().getUpAuthNo(), Const.ROLE_ADMIN_GROUP)) {
				// 정보 없음.
			}
		}

		return result;
	}

	/**
	 * @Desc : 자동승인이 가능한 경우(수정작업 및 입점사상품인 경우), 승인정보를 완료로 설정
	 * @Method Name : setAutoApproval
	 * @Date : 2019. 5. 10.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	public void setAutoApproval(PdProduct product) throws Exception {
		// 기존 상품 조회
		PdProduct productOrigin = this.getProduct(product.getPrdtNo(), product.getSiteNo(), product.getChnnlNo(),
				product.getVndrPrdtNoText());

		if (UtilsObject.isNotEmpty(productOrigin)
				&& UtilsText.equals(CommonCode.APRV_STAT_CODE_CONFIRM, productOrigin.getAprvStatCode())) {
			// 기존 상품정보가 있고 그 승인정보가 승인완료인 경우

			if (UtilsText.equals(PdProduct.TYPE_MODIFY, product.getType())
					&& UtilsText.equals("N", product.getMmnyPrdtYn())) {
				// 수정상태이고 입점상품인 경우

				if (this.processAutoApproval(product)) {
					// 자동승인수행이 가능한 경우

					// 자동승인 코드 설정
					product.setAprvStatCode(CommonCode.APRV_STAT_CODE_CONFIRM);

					// 결과메시지 설정 (자동승인처리)
					product.setResultMessage(Message.getMessage("product.result.save.aprvStatCode.confirm.auto"));
				}
			}
		}
	}

	/**
	 * @Desc : 자동승인대상 항목들을 모두 체크하여 가능여부를 반환
	 * @Method Name : processAutoApproval
	 * @Date : 2019. 5. 10.
	 * @Author : tennessee
	 * @param product
	 * @return
	 * @throws Exception
	 */
	private boolean processAutoApproval(PdProduct product) throws Exception {
		List<Boolean> checkResult = new ArrayList<Boolean>();
		checkResult.add(this.isPossibleAutoApproval(product)); // 비교 상품
		checkResult.add(this.productInsideDetailService.isPossibleAutoApproval(product)); // 비교 상품상세
//		checkResult.add(this.productInsideAddInfoService.isPossibleAutoApproval(product)); // 비교 상품추가정보
		checkResult.add(this.productInsideRelationFileService.isPossibleAutoApproval(product)); // 비교 상품관련파일
		checkResult.add(this.productInsidePriceHistoryService.isPossibleAutoApproval(product)); // 비교 상품가격이력
		return checkResult.stream().reduce(
				(now, next) -> now.booleanValue() == true && now.booleanValue() == next.booleanValue() ? true : false)
				.get();
	}

	/**
	 * @Desc : 최근 등록 된 내용과 현재 내용을 비교하여 다른 항목 갯수를 반환
	 * @Method Name : isPossibleAutoApproval
	 * @Date : 2019. 5. 10.
	 * @Author : tennessee
	 * @param product
	 * @return
	 * @throws Exception
	 */
	private boolean isPossibleAutoApproval(PdProduct product) throws Exception {

		PdProduct criteriaForOrigin = new PdProduct();
		criteriaForOrigin.setPrdtNo(product.getPrdtNo());
		PdProduct origin = this.productDao.selectByPrimaryKey(criteriaForOrigin);

//		return origin.compareTo(product) == 0 ? true : false;
		return UtilsText.equals(product.getPrdtName(), origin.getPrdtName())
				&& UtilsText.equals(product.getEngPrdtName(), origin.getEngPrdtName());
	}

	/**
	 * @Desc : 상품상태 리스트 목록 조회
	 * @Method Name : selectProductDashBoardCount
	 * @Date : 2019. 5. 21.
	 * @Author : 김영진
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public List<BdDashboardProduct> selectProductDashBoardCount() throws Exception {

		return this.productDao.selectProductDashBoardCount();
	}

	/**
	 * @Desc : 상품상태 리스트 목록 조회
	 * @Method Name : selectProductDashBoardCount
	 * @Date : 2019. 5. 21.
	 * @Author : 김영진
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public List<BdDashboardProduct> selectProductPoDashBoardCount() throws Exception {
		BdDashboardProduct productCnt = new BdDashboardProduct();

		productCnt.setVndrNo(LoginManager.getUserDetails().getVndrNo());

		return this.productDao.selectProductPoDashBoardCount(productCnt);
	}

	/**
	 * @Desc : 상품 URL 반환 서비스 (FO)
	 * @Method Name : getUrl
	 * @Date : 2019. 7. 8.
	 * @Author : tennessee
	 * @param prdtNo
	 * @param deviceCode
	 * @return
	 * @throws Exception
	 */
	public String getUrl(String prdtNo) throws Exception {
		return this.getUrl(prdtNo, CommonCode.DEVICE_PC);
	}

	/**
	 * @Desc : 상품 URL 반환 서비스
	 * @Method Name : getUrl
	 * @Date : 2019. 7. 8.
	 * @Author : tennessee
	 * @param prdtNo
	 * @param deviceCode
	 * @return
	 * @throws Exception
	 */
	public String getUrl(String prdtNo, String deviceCode) throws Exception {
		StringBuffer url = new StringBuffer();

		PdProduct product = this.getProduct(prdtNo, "", "", "");
		String uri = "/product";
		String parameter = "?prdtNo=" + prdtNo;

		url.append(this.getDomain(String.valueOf(product.getChnnlNo()), deviceCode));
		url.append(uri);
		url.append(parameter);

		return url.toString();
	}

	/**
	 * @Desc : 채널에 해당하는 도메인 반환 서비스
	 * @Method Name : getDomain
	 * @Date : 2019. 7. 8.
	 * @Author : tennessee
	 * @param chnnlNo
	 * @param deviceCode
	 * @return
	 * @throws Exception
	 */
	private String getDomain(String chnnlNo, String deviceCode) throws Exception {
		StringBuffer key = new StringBuffer();
		key.append("service.domain");

		switch (chnnlNo) {
		case Const.CHANNEL_NO_ART:
			key.append(".art");
			break;
		case Const.CHANNEL_NO_ABCMART:
			key.append(".abc");
			break;
		case Const.CHANNEL_NO_GRANDSTAGE:
			key.append(".gs");
			break;
		case Const.CHANNEL_NO_OTS:
			key.append(".ots");
			break;
		case Const.CHANNEL_NO_KIDS:
			key.append(".kids");
			break;
		default:
			log.debug("알 수 없는 유형입니다. chnnlNo : {}", chnnlNo);
		}

		if (UtilsDevice.isNotPc(deviceCode)) {
			key.append(".mo");
		} else {
			key.append(".fo");
		}

		return Config.getString(key.toString(), "");
	}

	/**
	 * @Desc : 브랜드 사용안함에 의한 상품 비활성화 처리
	 * @Method Name : setDisableByBrandNo
	 * @Date : 2019. 9. 25.
	 * @Author : tennessee
	 * @param brandNo
	 * @return
	 * @throws Exception
	 */
	public Integer setDisableByBrandNo(String brandNo) throws Exception {
		PdProduct criteria = new PdProduct();
		criteria.setBrandNo(brandNo);
		List<PdProduct> disableTarget = this.productDao.select(criteria);
		disableTarget.forEach(item -> {
			item.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
			try {
				PdProductChangeHistory history = new PdProductChangeHistory();
				history.setPrdtNo(item.getPrdtNo());
				history.setChngField("disp_yn"); // 변경필드
				history.setChngFieldName("전시여부"); // 변경필드명
				history.setChngBeforeFieldValue(item.getDispYn()); // 변경전필드값
				history.setChngAfterFieldValue("N"); // 변경후필드값
				history.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
				this.productInsideChangeHistoryService.insert(history);
				history.setChngField("sell_stat_code"); // 변경필드
				history.setChngFieldName("판매상태코드"); // 변경필드명
				history.setChngBeforeFieldValue(item.getSellStatCode()); // 변경전필드값
				history.setChngAfterFieldValue(CommonCode.SELL_STAT_CODE_STOP); // 변경후필드값
				this.productInsideChangeHistoryService.insert(history);
			} catch (Exception e) {
				log.error("브랜드 번호에 의한 상품 비활성화 처리 중 오류 발생");
				log.error("{}", e);
			}
		});
		return this.productDao.updateDisableByBrandNo(brandNo);
	}

	/**
	 * @Desc : 상품 내부관리번호 기반 등록 여부 검사. 등록된 경우, 중간계로 등록완료신호를 전송.
	 * @Method Name : checkRegistered
	 * @Date : 2019. 11. 26.
	 * @Author : tennessee
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public PdProduct checkRegistered(PdProduct product) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>();

		// 상품 내부관리번호 기준 등록상품 조회
		PdProduct criteria = new PdProduct();

		SySiteChnnl params = new SySiteChnnl();
		params.setChnnlNo(product.getChnnlNo());

		criteria.setSiteNo(siteService.getSiteNo(product.getChnnlNo()));

		criteria.setVndrPrdtNoText(product.getVndrPrdtNoText());

		PdProduct productInfo = productDao.selectProductSiteInfo(criteria);

		if (UtilsObject.isNotEmpty(productInfo)) {

		} else {

		}

		return productDao.selectProductSiteInfo(criteria);
	}

	/**
	 * @Desc : 상품 정보에 따른 등록가능한 채널 조회. 상품정보가 없는 경우 사용자 정보에 따라 조회됨
	 * @Method Name : getChannels
	 * @Date : 2019. 12. 3.
	 * @Author : tennessee
	 * @param product
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getChannels(PdProduct product) throws Exception {
		Object result = null;

		if (UtilsObject.isNotEmpty(product)) {
			// 상품정보가 있는 경우, 상품 정보에 의한 채널 조회

			if (UtilsText.equals(Const.BOOLEAN_TRUE, product.getMmnyPrdtYn())) {
				// 자사상품인 경우, 사용중인 채널 조회
				result = this.getChannels();
			} else {
				// 입점상품인 경우, 입점업체가 사용가능한 채널 목록 조회
				VdVendorDisplayChnnl criteria = new VdVendorDisplayChnnl();
				criteria.setVndrNo(product.getVndrNo());
				result = this.getChannelsForVendor(product.getVndrNo());
			}

		} else {
			// 상품정보가 없는 경우, 사용자 권한에 의한 채널 조회

			if (UtilsText.equals(Const.AUTH_APPLY_SYSTEM_BO, LoginManager.getUserDetails().getAuthApplySystemType())) {
				// 자사상품인 경우, 사용중인 채널 조회
				result = this.getChannels();
			} else {
				// 입점상품인 경우, 입점업체가 사용가능한 채널 목록 조회
				result = this.getChannelsForVendor(LoginManager.getUserDetails().getVndrNo());
			}

		}

		return (List<Object>) result;
	}

	/**
	 * @Desc : 자사에서 등록 가능한 채널 조회
	 * @Method Name : getChannels
	 * @Date : 2019. 12. 3.
	 * @Author : tennessee
	 * @return
	 * @throws Exception
	 */
	private List<SySiteChnnl> getChannels() throws Exception {
		List<SySiteChnnl> channels = this.siteService.getUseChannelList().stream()
				.filter(channel -> !UtilsText.equals(ProductService.CHANNEL_NO_ART, channel.getChnnlNo()))
				.collect(Collectors.toList());
		return channels;
	}

	/**
	 * @Desc : 입점사에서 등록 가능한 채널 조회
	 * @Method Name : getChannelsForVendor
	 * @Date : 2019. 12. 3.
	 * @Author : tennessee
	 * @param vndrNo
	 * @return
	 * @throws Exception
	 */
	private List<VdVendorDisplayChnnl> getChannelsForVendor(String vndrNo) throws Exception {
		VdVendorDisplayChnnl criteria = new VdVendorDisplayChnnl();
		criteria.setVndrNo(vndrNo);
		return this.vendorService.selectVendorDisplayChnnlList(criteria);
	}

	/**
	 * @Desc : 상품목록 엑셀다운로드(NEW)
	 * @Method Name : getExcelDownLoadProductList
	 * @Date : 2020. 3. 25.
	 * @Author : kiowa
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PdProduct> getExcelDownLoadProductList(Pageable<PdProductSearchVO, PdProduct> pageable)
			throws Exception {

		if (UtilsText.equals(LoginManager.getUserDetails().getUpAuthNo(), Const.ROLE_VENDER_GROUP)) {
			pageable.getBean().setVndrNo(LoginManager.getUserDetails().getVndrNo());
		}

		pageable.setContent(productDao.selectExcelDownloadProductList(pageable));

		return pageable.getPage();
	}

	/**
	 * @Desc : 상품목록 조회(NEW)
	 * @Method Name : searchProductNew
	 * @Date : 2020. 3. 30.
	 * @Author : 3top
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PdProduct> searchProductNew(Pageable<PdProductSearchVO, PdProduct> pageable) throws Exception {
		if (UtilsText.equals(LoginManager.getUserDetails().getUpAuthNo(), Const.ROLE_VENDER_GROUP)) {
			pageable.getBean().setVndrNo(LoginManager.getUserDetails().getVndrNo());
		}
		Integer count = productDao.selectProductCountNew(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			// 재고구분코드 설정
			String[] codeFields = { CommonCode.STOCK_GBN_CODE };
			Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
					.getCodeListByGroupByCombo(codeFields, false);
			pageable.getBean().setStockGbnCodeList(pair.getSecond().get(CommonCode.STOCK_GBN_CODE));

			pageable.setContent(productDao.selectProductNew(pageable));

//			for (PdProduct item : pageable.getContent()) {
//				item.setPrivacy(true); // 목록 페이지 강제 마스킹 적용
//			}
		}

		return pageable.getPage();
	}

}
