package kr.co.abcmart.bo.product.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductOption;
import kr.co.abcmart.bo.product.model.master.PdProductOptionPriceHistory;
import kr.co.abcmart.bo.product.model.master.PdProductOptionStock;
import kr.co.abcmart.bo.product.model.master.PdProductOptionWithStockAndPrice;
import kr.co.abcmart.bo.product.repository.master.PdProductOptionChangeHistoryDao;
import kr.co.abcmart.bo.product.repository.master.PdProductOptionDao;
import kr.co.abcmart.bo.product.repository.master.PdProductOptionPriceHistoryDao;
import kr.co.abcmart.bo.product.repository.master.PdProductOptionStockDao;
import kr.co.abcmart.bo.product.vo.PdProductOptionStockSearchVO;
import kr.co.abcmart.bo.promotion.model.master.PrPromotion;
import kr.co.abcmart.bo.promotion.model.master.PrPromotionTargetProduct;
import kr.co.abcmart.bo.promotion.service.PromotionService;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsArray;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.interfaces.module.product.offlineproduct.ProductOfflineStockService;
import kr.co.abcmart.interfaces.module.product.offlineproduct.model.ProductOfflineStockHttp;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품 옵션 서비스
 * @FileName : ProductInsideOptionService.java
 * @Project : abc.bo
 * @Date : 2019. 3. 13.
 * @Author : tennessee
 */
@Slf4j
@Service
public class ProductInsideOptionService {

	@Autowired
	private PdProductOptionDao productOptionDao;

	@Autowired
	private PdProductOptionStockDao productOptionStockDao;

	@Autowired
	private PdProductOptionPriceHistoryDao productOptionPriceHistoryDao;

	@Autowired
	private PdProductOptionChangeHistoryDao productOptionChangeHistoryDao;

	@Autowired
	private ProductReflectionService productReflectionService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private ProductOfflineStockService productOfflineStockService;

	@Autowired
	private PromotionService promotionService;

	/**
	 * @Desc : 상품옵션 등록/수정/삭제
	 * @Method Name : registProductOptionDefault
	 * @Date : 2019. 4. 3.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	public void regist(PdProduct product) throws Exception {
		PdProductOption[] productOption = product.getProductOption();

		if (UtilsArray.isNotEmpty(productOption)) {
			for (PdProductOption item : productOption) {

				this.initialize(item, product); // 옵션 초기 설정
				this.setDataBeforeCalculation(item); // 계산 이전 설정 작업
				item.calculateQuantity(product); // 수량 계산
				this.productReflectionService.setUserInfo(item); // 사용자정보 설정

				switch (item.getStatus()) {
				case Const.CRUD_I:
					this.productOptionDao.insertWithSelectKey(item);
					this.insertProductOptionStock(item); // 재고작업
					break;
				case Const.CRUD_U:
					this.productOptionDao.update(item);
					this.updateProductOptionStock(item); // 재고작업
					break;
				default:
					log.debug("상태정보가 올바르지 않습니다. PdProductOption.getStatus() : {}", item.getStatus());
				}

				this.insertProductOptionPriceHistory(item); // 가격이력 작업
//				this.insertProductOptionChangeHistory(item); // 변경이력작업
			}
			// 삭제 처리
			this.productOptionChangeHistoryDao.deleteContraryOptions(product.getPrdtNo(), Arrays.asList(productOption));
			this.productOptionPriceHistoryDao.deleteContraryOptions(product.getPrdtNo(), Arrays.asList(productOption));
			this.productOptionStockDao.deleteContraryOptions(product.getPrdtNo(), Arrays.asList(productOption));
			this.productOptionDao.deleteContraryOptions(product.getPrdtNo(), Arrays.asList(productOption));
		}
	}

	/**
	 * @Desc : 상품 옵션 초기화 작업
	 * @Method Name : initialize
	 * @Date : 2019. 8. 9.
	 * @Author : tennessee
	 * @param productOption
	 * @param product
	 * @throws Exception
	 */
	private void initialize(PdProductOption productOption, PdProduct product) throws Exception {
		productOption.setPrdtNo(product.getPrdtNo()); // 상품번호 설정

		if (UtilsText.equals(Const.BOOLEAN_TRUE, product.getMmnyPrdtYn())) {
			// 자사인 경우, 옵션별 판매상태코드는 상품 판매상태코드와 동일하게 설정함
			productOption.setSellStatCode(product.getSellStatCode());
		} else {
			// 입점인 경우, 사용자로부터 설정된 값 유지
		}

		if (UtilsText.equals(product.getNewErpProductYn(), Const.BOOLEAN_TRUE)) {
			// ERP신규상품등록은 상태정보를 I로 변경.
			productOption.setStatus(Const.CRUD_I);
		} else if (UtilsText.equals(Const.CRUD_R, productOption.getStatus())) {
			// 갱신 플래그 재설정
			productOption.setStatus(Const.CRUD_U);
		}
	}

	/**
	 * @Desc : 상품 옵션 계산작업 전 설정 작업
	 * @Method Name : setDataBeforeCalculation
	 * @Date : 2019. 8. 9.
	 * @Author : tennessee
	 * @param productOption
	 * @param product
	 * @throws Exception
	 */
	private void setDataBeforeCalculation(PdProductOption productOption) throws Exception {
		if (UtilsText.equals(Const.CRUD_I, productOption.getStatus())) {
			// 신규등록인 경우, 총주문수량 0으로 설정
			productOption.setTotalOrderQty(0);
		} else {
			// 주문수량 조회하여 설정
			PdProductOption criteria = new PdProductOption();
			criteria.setPrdtNo(productOption.getPrdtNo());
			criteria.setPrdtOptnNo(productOption.getPrdtOptnNo());
			PdProductOption before = this.productOptionDao.selectByPrimaryKey(criteria);
			if (UtilsObject.isNotEmpty(before)) {
				productOption.setTotalOrderQty(before.getTotalOrderQty());
			}
		}
	}

	/**
	 * @Desc : 상품옵션재고 등록
	 * @Method Name : insertProductOptionStock
	 * @Date : 2019. 4. 3.
	 * @Author : tennessee
	 * @param productOption
	 * @throws Exception
	 */
	private void insertProductOptionStock(PdProductOption productOption) throws Exception {
		PdProductOptionStock productOptionStock = new PdProductOptionStock();
		this.productReflectionService.setUserInfo(productOptionStock);

		if (UtilsText.equals(productOption.getMmnyPrdtYn(), Const.BOOLEAN_TRUE)) {
			productOptionStock.setPrdtNo(productOption.getPrdtNo());
			productOptionStock.setPrdtOptnNo(productOption.getPrdtOptnNo());

			productOptionStock.setStockGbnCode(CommonCode.STOCK_GBN_CODE_AI);
			productOptionStock.setStockQty(
					UtilsObject.isNotEmpty(productOption.getStockAiQty()) ? productOption.getStockAiQty() : 0);
			productOptionStock.setOrderCount(0);
			this.productOptionStockDao.insert(productOptionStock);

			productOptionStock.setStockGbnCode(CommonCode.STOCK_GBN_CODE_AW);
			productOptionStock.setStockQty(
					UtilsObject.isNotEmpty(productOption.getStockAwQty()) ? productOption.getStockAwQty() : 0);
			this.productOptionStockDao.insert(productOptionStock);

			productOptionStock.setStockGbnCode(CommonCode.STOCK_GBN_CODE_AS);
			productOptionStock.setStockQty(
					UtilsObject.isNotEmpty(productOption.getStockAsQty()) ? productOption.getStockAsQty() : 0);
			this.productOptionStockDao.insert(productOptionStock);

		} else if (UtilsText.equals(productOption.getMmnyPrdtYn(), Const.BOOLEAN_FALSE)) {
			productOptionStock.setPrdtNo(productOption.getPrdtNo());
			productOptionStock.setPrdtOptnNo(productOption.getPrdtOptnNo());
			productOptionStock.setStockGbnCode(CommonCode.STOCK_GBN_CODE_VD);
			productOptionStock.setStockQty(
					UtilsObject.isNotEmpty(productOption.getStockVdQty()) ? productOption.getStockVdQty() : 0);
			productOptionStock.setOrderCount(0);
			this.productOptionStockDao.insert(productOptionStock);
		}
	}

	/**
	 * @Desc : 상품옵션재고 수정
	 * @Method Name : updateProductOptionStock
	 * @Date : 2019. 4. 3.
	 * @Author : tennessee
	 * @param productOption
	 * @throws Exception
	 */
	private void updateProductOptionStock(PdProductOption productOption) throws Exception {
		PdProductOptionStock productOptionStock = new PdProductOptionStock();
		this.productReflectionService.setUserInfo(productOptionStock);

		if (UtilsText.equals(Const.BOOLEAN_TRUE, productOption.getMmnyPrdtYn())) {
			productOptionStock.setPrdtNo(productOption.getPrdtNo());
			productOptionStock.setPrdtOptnNo(productOption.getPrdtOptnNo());

			productOptionStock.setStockGbnCode(CommonCode.STOCK_GBN_CODE_AI);
			productOptionStock.setStockQty(productOption.getStockAiQty());
			this.regist(productOptionStock);
			productOptionStock.setStockGbnCode(CommonCode.STOCK_GBN_CODE_AW);
			productOptionStock.setStockQty(productOption.getStockAwQty());
			this.regist(productOptionStock);
			productOptionStock.setStockGbnCode(CommonCode.STOCK_GBN_CODE_AS);
			productOptionStock.setStockQty(productOption.getStockAsQty());
			this.regist(productOptionStock);
		} else {
			productOptionStock.setPrdtNo(productOption.getPrdtNo());
			productOptionStock.setPrdtOptnNo(productOption.getPrdtOptnNo());
			productOptionStock.setStockGbnCode(CommonCode.STOCK_GBN_CODE_VD);
			productOptionStock.setStockQty(productOption.getStockVdQty());
			this.regist(productOptionStock);
		}
	}

	/**
	 * @Desc : 옵션재고등록. 기존 등록유무에 따라 insert 또는 update를 수행
	 * @Method Name : regist
	 * @Date : 2019. 6. 7.
	 * @Author : tennessee
	 * @param productOptionStock
	 * @throws Exception
	 */
	private void regist(PdProductOptionStock productOptionStock) throws Exception {
		PdProductOptionStock before = this.productOptionStockDao.selectByPrimaryKey(productOptionStock);

		if (UtilsObject.isEmpty(before)) {
			if (UtilsObject.isEmpty(productOptionStock.getOrderCount())) {
				// 주문수량 0으로 설정
				productOptionStock.setOrderCount(0);
			}
			this.productOptionStockDao.insert(productOptionStock);
		} else {
			this.productOptionStockDao.update(productOptionStock);
		}
	}

//	/**
//	 * @Desc : 상품옵션재고 삭제
//	 * @Method Name : deleteProductOptionStock
//	 * @Date : 2019. 4. 3.
//	 * @Author : tennessee
//	 * @param productOption
//	 * @throws Exception
//	 */
//	private void deleteProductOptionStock(PdProductOption productOption) throws Exception {
//		PdProductOptionStock productOptionStock = new PdProductOptionStock();
//		productOptionStock.setPrdtNo(productOption.getPrdtNo());
//		productOptionStock.setPrdtOptnNo(productOption.getPrdtOptnNo());
//		this.productOptionStockDao.delete(productOptionStock);
//	}

	/**
	 * @Desc : 상품옵션가격이력 등록
	 * @Method Name : insertProductOptionPriceHistory
	 * @Date : 2019. 4. 3.
	 * @Author : tennessee
	 * @param productOption
	 * @throws Exception
	 */
	private void insertProductOptionPriceHistory(PdProductOption productOption) throws Exception {
		// 기존 정보 조회
		PdProductOptionPriceHistory before = this.productOptionPriceHistoryDao
				.selectRecentPrice(productOption.getPrdtNo(), productOption.getPrdtOptnNo());
		if (UtilsObject.isEmpty(before) || before.getOptnAddAmt().compareTo(productOption.getOptnAddAmt()) != 0) {
			// 신규등록 또는 가격변동이 있는 경우 저장
			PdProductOptionPriceHistory productOptionPriceHistory = new PdProductOptionPriceHistory();
			Calendar c = Calendar.getInstance();
			c.set(9999, 11, 31, 23, 59, 59);
			if (UtilsObject.isEmpty(productOption.getOptnAddAmt())) {
				productOption.setOptnAddAmt(0);
			}
			productOptionPriceHistory.setPrdtNo(productOption.getPrdtNo());
			productOptionPriceHistory.setPrdtOptnNo(productOption.getPrdtOptnNo());
			productOptionPriceHistory.setOptnAddAmt(productOption.getOptnAddAmt());
			productOptionPriceHistory.setApplyStartDtm(null); // 적용일자 GETDATE를 위한 null 처리

			if (UtilsObject.isNotEmpty(before)) {
				// 기존적용가격 적용날짜 변경(미적용)
				Calendar c2 = Calendar.getInstance();
				c2.add(Calendar.DATE, -1);
				before.setApplyEndDtm(new Timestamp(c2.getTime().getTime()));
				this.productOptionPriceHistoryDao.update(before);
			}

			productOptionPriceHistory.setApplyEndDtm(new Timestamp(c.getTimeInMillis()));
			this.productReflectionService.setUserInfo(productOptionPriceHistory);
			this.productOptionPriceHistoryDao.insertWithSelectKey(productOptionPriceHistory);
		}
	}

	/**
	 * @Desc : 상품옵션변경이력 등록. AS-IS기준 변경이력남기는 항목 없음
	 * @Method Name : insertProductOptionChangeHistory
	 * @Date : 2019. 4. 3.
	 * @Author : tennessee
	 * @param productOption
	 * @throws Exception
	 */
	@Deprecated
	private void insertProductOptionChangeHistory(PdProductOption productOption) throws Exception {
//		PdProductOptionChangeHistory productOptionChangeHistory = new PdProductOptionChangeHistory();
//		productOptionChangeHistory.setPrdtNo(productOption.getPrdtNo());
//		productOptionChangeHistory.setPrdtOptnNo(productOption.getPrdtOptnNo());
//		this.productReflectionService.setUserInfo(productOptionChangeHistory);
//		this.productOptionChangeHistoryDao.insertWithSelectKey(productOptionChangeHistory);
	}

	/**
	 * @Desc : 상품번호에 해당하는 상품옵션을 조회 서비스
	 * @Method Name : searchProductOption
	 * @Date : 2019. 3. 12.
	 * @Author : tennessee
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public List<PdProductOption> searchProductOption(String prdtNo) throws Exception {
		return this.productOptionDao.selectByPrdtNo(prdtNo);
	}

	/**
	 * @Desc : 상품 옵션 및 재고 조회
	 * @Method Name : searchProductOptionWithStocks
	 * @Date : 2019. 3. 20.
	 * @Author : tennessee
	 * @param productOptionStockSearchVO
	 * @return
	 * @throws Exception
	 */
	public Page<PdProductOption> searchProductOptionWithStocks(
			Pageable<PdProductOptionStockSearchVO, PdProductOption> pageable) throws Exception {
		Integer count = this.productOptionDao.selectProductOptionTotalCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			// 재고구분코드 설정
			String[] codeFields = { CommonCode.STOCK_GBN_CODE };
			Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
					.getCodeListByGroupByCombo(codeFields, false);
			pageable.getBean().setStockGbnCodeList(pair.getSecond().get(CommonCode.STOCK_GBN_CODE));
			pageable.setContent(this.productOptionDao.selectWithStocks(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 재고정보 수정
	 * @Method Name : updateProductOptionStock
	 * @Date : 2019. 4. 1.
	 * @Author : tennessee
	 * @param productOptionStock
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public Boolean updateProductOptionStock(PdProductOptionStock productOptionStock) throws Exception {
		Boolean result = false;
		if (UtilsText.isNotBlank(productOptionStock.getPrdtNo())
				&& UtilsText.isNotBlank(productOptionStock.getPrdtOptnNo())
				&& UtilsText.isNotBlank(productOptionStock.getStockGbnCode())) {
			this.productReflectionService.setUserInfo(productOptionStock);

			Integer appliedCount = this.productOptionStockDao.update(productOptionStock);
			result = appliedCount.intValue() > 0 ? true : false;
		}
		return result;
	}

	/**
	 * @Desc : 상품에 대한 옵션 목록(재고량과 추가금액 포함)을 반환하는 서비스
	 * @Method Name : getProductOptions
	 * @Date : 2019. 4. 15.
	 * @Author : tennessee
	 * @param prdtNo 상품번호
	 * @return 상품옵션목록
	 * @throws Exception
	 */
	public List<PdProductOption> getProductOptions(String prdtNo) throws Exception {
		// 공통코드 조회
		String[] codeFields = { CommonCode.STOCK_GBN_CODE };
		Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair = this.commonCodeService
				.getCodeListByGroupByCombo(codeFields, false);

		PdProductOptionStockSearchVO criteria = new PdProductOptionStockSearchVO();
		criteria.setPrdtNo(prdtNo);
		criteria.setStockGbnCodeList(pair.getSecond().get(CommonCode.STOCK_GBN_CODE));
		return this.productOptionDao.selectWithStockAndAddAmt(criteria);
	}

	/**
	 * @Desc : 상품 옵션 목록 재고와 가격까지 함께 조회 (서비스 요청서)
	 * @Method Name : getProductOptionListWithStockAndPrice
	 * @Date : 2019. 4. 22.
	 * @Author : hsjhsj19
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public List<PdProductOptionWithStockAndPrice> getProductOptionListWithStockAndPrice(String prdtNo, String promoNo)
			throws Exception {

		PdProductOptionWithStockAndPrice optionWithStockAndPrice = new PdProductOptionWithStockAndPrice();
		optionWithStockAndPrice.setPrdtNo(prdtNo);

		List<PdProductOptionWithStockAndPrice> list = this.productOptionDao
				.selectProductOptionListWithStockAndPrice(optionWithStockAndPrice);

		// 프로모션상세
		PrPromotion prPromotion = null;
		if (UtilsObject.isNotEmpty(promoNo)) {
			prPromotion = new PrPromotion();
			prPromotion.setPromoNo(promoNo);
			prPromotion = promotionService.getPromotionProduct(prPromotion);
		}

		// 오프라인 재고 조회 대상 창고 구분
		String[] stockGubnList = { "AW", "AS" };
		int offLineStockQty = 0;
		String channelGb = "01";

		for (PdProductOptionWithStockAndPrice option : list) {
			// option.setOptnAddAmt(0);
			if (CommonCode.SELL_STAT_CODE_PROC.equals(option.getSellStatCode())) {
				// 자사 상품인 경우 AI,AW 참고
				if (Const.BOOLEAN_TRUE.equals(option.getMmnyPrdtYn())) {
					if (Const.BOOLEAN_TRUE.equals(option.getStockIntgrYn())) { // 재고통합여부
						channelGb = UtilsText.equals(option.getSiteNo(), "10000") ? "01" : "07";
						// 서비스 호출
						List<ProductOfflineStockHttp> offlines = new ArrayList<ProductOfflineStockHttp>();

						try {
							offlines = productOfflineStockService
									.getChannelProductOfflineStockFromHttp(option.getVndrPrdtNoText(), channelGb);

							if (!offlines.isEmpty()) {
								for (String stockGubn : stockGubnList) {
									offLineStockQty = 0;
									for (ProductOfflineStockHttp offline : offlines) {
										if (stockGubn.equals(offline.getGubun())) {
											offLineStockQty = offline.getCurrQty();
										}
									}
									if ("AW".equals(stockGubn)) {
										option.setStockAwQty(offLineStockQty - option.getStockAwQty() > 0
												? offLineStockQty - option.getStockAwQty()
												: 0);
									} else if ("AS".equals(stockGubn)) {
										option.setStockAsQty(offLineStockQty - option.getStockAsQty() > 0
												? offLineStockQty - option.getStockAsQty()
												: 0);
									}
								}
							} else { // 오프라인 재고 조회 실패 혹은 데이터가 없을 경우 재고는 0으로 초기화
								option.setStockAwQty(0);
								option.setStockAsQty(0);
							}
						} catch (Exception e) {
							option.setStockAwQty(0);
							option.setStockAsQty(0);
							log.error("실시간 Offline 재고 조회 오류 \n" + e.getMessage());
						}
					} else {
						option.setStockAwQty(0);
						option.setStockAsQty(0);
					}
				} else {
					// 재고관리 안하는 경우
					if (Const.BOOLEAN_FALSE.equals(option.getStockMgmtYn())) {
						option.setStockVdQty(10000);
					}
				}
			} else {
				option.setStockAiQty(0);
				option.setStockAwQty(0);
				option.setStockAsQty(0);
				option.setStockVdQty(0);
			}

			// 프로모션 타입 조회
			if (UtilsObject.isNotEmpty(prPromotion)) {
				if (CommonCode.PROMO_TYPE_CODE_SPECIAL_PRICE_TIME.equals(prPromotion.getPromoTypeCode())) {
					List<PrPromotionTargetProduct> products = prPromotion.getPrPromotionTargetProductList();
					for (PrPromotionTargetProduct product : products) {
						if (prdtNo.equals(product.getPrdtNo())) {
							int eventLimitQty = product.getEventLimitQty(); // 타임특가 프로모션 한정 수량

							option.setStockAiQty(
									option.getStockAiQty() < eventLimitQty ? option.getStockAiQty() : eventLimitQty);
							option.setStockAwQty(
									option.getStockAwQty() < eventLimitQty ? option.getStockAwQty() : eventLimitQty);
							option.setStockAsQty(
									option.getStockAsQty() < eventLimitQty ? option.getStockAsQty() : eventLimitQty);
							option.setStockVdQty(
									option.getStockVdQty() < eventLimitQty ? option.getStockVdQty() : eventLimitQty);
							break;
						}
					}
				}
				/*
				 * 2019-10-03 김진성 프로모션 재고 차감 위치가 온라인 재고 한정일 경우 AW, AS 재고를 0으로 수정한다.
				 */
				if ("O".equals(prPromotion.getStockDdctType())) {
					option.setStockAwQty(0);
					option.setStockAsQty(0);
				}
			}
		}

		return list;
	}

	/**
	 * @Desc : 상품 옵션 목록 재고까지 함께 조회(서비스 요청서)
	 * @Method Name : getProductOptionListWithStock
	 * @Date : 2019. 4. 22.
	 * @Author : hsjhsj19
	 * @param siteNo
	 * @param prdtNo
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public List<PdProductOptionWithStockAndPrice> getProductOptionListWithStock(String siteNo, String prdtNo,
			String prdtOptnNo) throws Exception {

		PdProductOptionWithStockAndPrice optionWithStockAndPrice = new PdProductOptionWithStockAndPrice();
		optionWithStockAndPrice.setSiteNo(siteNo);
		optionWithStockAndPrice.setPrdtNo(prdtNo);
		optionWithStockAndPrice.setPrdtOptnNo(prdtOptnNo);

		/*
		 * // 공통코드 조회 String[] codeFields = { CommonCode.STOCK_GBN_CODE };
		 * Pair<JSONObject, Map<String, List<SyCodeDetail>>> pair =
		 * this.commonCodeService .getCodeListByGroupByCombo(codeFields, false);
		 * optionWithStockAndPrice.setStockGbnCodeList(pair.getSecond().get(CommonCode.
		 * STOCK_GBN_CODE));
		 */
		return this.productOptionDao.selectProductOptionListWithStock(optionWithStockAndPrice);
	}

	/**
	 * @Desc : 옵션 재고 조회 단건
	 * @Method Name : getPorudctOptionStock
	 * @Date : 2019. 6. 21.
	 * @Author : kiowa
	 * @param prdtNo        : 상품 코드
	 * @param prdtOptnNo    : 옵션 코드
	 * @param storePickupYn : 픽업 배송 여부
	 * @return 판매 가능 재고 수량
	 * @throws Exception
	 */
	public int getProductOptionStock(String prdtNo, String prdtOptnNo, String storePickupYn) throws Exception {
		int availableStockQty = 0;

		List<Map<String, Object>> productOptionList = new ArrayList<Map<String, Object>>();

		Map<String, Object> prdoctOption = new HashMap<String, Object>();

		prdoctOption.put("prdtNo", prdtNo);
		prdoctOption.put("prdtOptnNo", prdtOptnNo);

		productOptionList.add(prdoctOption);

		List<PdProductOptionStock> productOptionStockList = this.selectProductOptionStock(productOptionList);

		if (!productOptionStockList.isEmpty() && productOptionStockList.size() > 0) {
			availableStockQty = this.stockCalculation(productOptionStockList.get(0), storePickupYn);
		}

		return availableStockQty;
	}

	/**
	 * @Desc : 옵션 재고 조회 서비스 프로모션 재고 포함
	 * @Method Name : getProductOptionStock
	 * @Date : 2019. 10. 4.
	 * @Author : kiowa
	 * @param prdtNo
	 * @param prdtOptnNo
	 * @param storePickupYn
	 * @param promoNo
	 * @return
	 * @throws Exception
	 */
	public int getProductOptionStock(String prdtNo, String prdtOptnNo, String storePickupYn, String promoNo)
			throws Exception {
		int availableStockQty = 0;

		List<Map<String, Object>> productOptionList = new ArrayList<Map<String, Object>>();

		Map<String, Object> prdoctOption = new HashMap<String, Object>();
		prdoctOption.put("prdtNo", prdtNo);
		prdoctOption.put("prdtOptnNo", prdtOptnNo);
		prdoctOption.put("promoNo", promoNo);

		productOptionList.add(prdoctOption);

		List<PdProductOptionStock> productOptionStockList = this.selectProductOptionStock(productOptionList);

		if (!productOptionStockList.isEmpty() && productOptionStockList.size() > 0) {
			availableStockQty = this.stockCalculation(productOptionStockList.get(0), storePickupYn);
		}

		return availableStockQty;
	}

	/**
	 * @Desc : 옵션 재고 조회 다건
	 * @Method Name : getProductOptionStock
	 * @Date : 2019. 6. 21.
	 * @Author : kiowa
	 * @param productOptions Map key [prdtNo, prdtOptnNo]
	 * @param storePickupYn  : 픽업 배송 여부
	 * @return Map Key [prdtNo, prdtOptnNo, stockQty]
	 * @throws Exception
	 */
	public List<Map<String, Object>> getProductOptionStock(List<Map<String, Object>> productOptions,
			String storePickupYn) throws Exception {

		List<PdProductOptionStock> productOptionStockList = this.selectProductOptionStock(productOptions);

		for (Map<String, Object> productOption : productOptions) {
			int availableStockQty = 0;
			if (!productOptionStockList.isEmpty() && productOptionStockList.size() > 0) {
				PdProductOptionStock productOptionStock = productOptionStockList.stream()
						.filter(x -> x.getPrdtNo().equals(productOption.get("prdtNo"))
								&& x.getPrdtOptnNo().equals(productOption.get("prdtOptnNo")))
						.collect(Collectors.toList()).get(0);

				availableStockQty = this.stockCalculation(productOptionStock, storePickupYn);
				productOption.put("stockAiQty", productOptionStock.getStockAiQty());
				productOption.put("stockAsQty", productOptionStock.getStockAsQty());
				productOption.put("stockAwQty", productOptionStock.getStockAwQty());
				productOption.put("stockVdQty", productOptionStock.getStockVdQty());

			}
			productOption.put("stockQty", availableStockQty);
		}

		return productOptions;
	}

	/**
	 * @Desc : 옵션 재고 계산
	 * @Method Name : stockCalculation
	 * @Date : 2019. 6. 21.
	 * @Author : kiowa
	 * @param prdtOptionStock
	 * @param storePickupYn
	 * @return
	 * @throws Exception
	 */
	private int stockCalculation(PdProductOptionStock prdtOptionStock, String storePickupYn) throws Exception {
		int availableStockQty = 0;

		storePickupYn = UtilsText.isEmpty(storePickupYn) ? "N" : storePickupYn; // 픽업 가능 상품 조회

		String mmnyPrdtYn = prdtOptionStock.getMmnyPrdtYn(); // 자사 상품 여부
		String stockMgmtYn = prdtOptionStock.getStockMgmtYn(); // 입점 업체 재고 관리 여부
		String stockIntgrYn = prdtOptionStock.getStockIntgrYn(); // 재고 통합 여부
		String sellStatCode = prdtOptionStock.getSellStatCode(); // 옵션 재고 상태 코드
		String useYn = prdtOptionStock.getUseYn(); // 옵션 전시 여부

		Integer stockAiQty = prdtOptionStock.getStockAiQty(); // 온라인 물류 재고 수량
		Integer stockAwQty = prdtOptionStock.getStockAwQty(); // 스마트 물류 재고 수량
		Integer stockAsQty = prdtOptionStock.getStockAsQty(); // 오프라인 매장 재고 수량
		Integer stockVdQty = prdtOptionStock.getStockVdQty(); // 입점사 재고 수량

		Integer eventLimitQty = prdtOptionStock.getEventLimitQty(); // 타임특가 한정 수량
		String promoTypeCode = prdtOptionStock.getPromoTypeCode(); // 프로모션 유형 코드
		String stockDdctType = prdtOptionStock.getStockDdctType(); // 프로모션에 적용된 통합재고 사용 여부 A(전체 재고), O (온라인 재고)

		// 오프라인 재고 창고 코드
		String[] stockGubnList = { "AW", "AS" };
		int offLineStockQty = 0;

		// 옵션 판매 상태가 판매 중이면서 전시인 경우에만 재고 계산을 한다.
		if (CommonCode.SELL_STAT_CODE_PROC.equals(sellStatCode) && Const.BOOLEAN_TRUE.equals(useYn)) {
			if (Const.BOOLEAN_TRUE.equals(mmnyPrdtYn)) { // 자사 상품
				if (Const.BOOLEAN_TRUE.equals(stockIntgrYn)) { // 재고 통합 여부일 경우 AW, AS 재고를 실시간 조회 한다.
					/*
					 * 2019-10-03 김진성 수정 오프라인 재고는 AI 재고 여부와 상관 없이 실시간 조회
					 */
					List<ProductOfflineStockHttp> offlines = new ArrayList<ProductOfflineStockHttp>();

					try {
						offlines = productOfflineStockService.getChannelProductOfflineStockFromHttp(
								prdtOptionStock.getVndrPrdtNoText(), prdtOptionStock.getChannelGb());

						if (!offlines.isEmpty()) {
							for (String stockGubn : stockGubnList) {
								offLineStockQty = 0;
								for (ProductOfflineStockHttp offline : offlines) {
									if (stockGubn.equals(offline.getGubun())) {
										offLineStockQty = offline.getCurrQty();
									}
								}
								if ("AW".equals(stockGubn)) {
									stockAwQty = offLineStockQty - stockAwQty > 0 ? offLineStockQty - stockAwQty : 0;
								} else if ("AS".equals(stockGubn)) {
									stockAsQty = offLineStockQty - stockAsQty > 0 ? offLineStockQty - stockAsQty : 0;
								}
							}
						} else {
							stockAwQty = 0;
							stockAsQty = 0;
						}
					} catch (Exception e) {
						stockAwQty = 0;
						stockAsQty = 0;
						log.error("실시간 Offline 재고 조회 오류 \n" + e.getMessage());
					}

					/*
					 * 2019-10-03 김진성 타임특가 프로모션 일 경우 한정 수량을 재고 수량으로 변경한다.
					 */
					if (CommonCode.PROMO_TYPE_CODE_SPECIAL_PRICE_TIME.equals(promoTypeCode)) {
						stockAiQty = stockAiQty < eventLimitQty ? stockAiQty : eventLimitQty;
						stockAwQty = stockAwQty < eventLimitQty ? stockAwQty : eventLimitQty;
						stockAsQty = stockAsQty < eventLimitQty ? stockAsQty : eventLimitQty;
					}
					/*
					 * 2019-10-03 김진성 프로모션 재고 차감 위치가 온라인 재고 한정일 경우 AW, AS 재고를 0으로 수정한다.
					 */
					if ("O".equals(stockDdctType)) {
						stockAwQty = 0;
						stockAsQty = 0;
					}

					if (Const.BOOLEAN_TRUE.equals(storePickupYn)) { // 픽업 주문일 경우에는 AS 재고만 내려 준다.
						availableStockQty = stockAsQty;
					} else {
						availableStockQty = stockAiQty + stockAwQty + stockAsQty;
					}
				} else {
					/*
					 * 2019-10-03 김진성 타임특가 프로모션 일 경우 한정 수량을 재고 수량으로 변경한다.
					 */
					if (CommonCode.PROMO_TYPE_CODE_SPECIAL_PRICE_TIME.equals(promoTypeCode)) {
						stockAiQty = stockAiQty < eventLimitQty ? stockAiQty : eventLimitQty;
					}
					if (Const.BOOLEAN_TRUE.equals(storePickupYn)) { // 픽업 주문일 경우에는 AS 재고만 내려 준다.
						availableStockQty = 0;
					} else {
						availableStockQty = stockAiQty;
					}
				}

			} else { // 업체 상품
				if (Const.BOOLEAN_TRUE.equals(stockMgmtYn)) { // 재고 관리
					/*
					 * 2019-10-03 김진성 타임특가 프로모션 일 경우 한정 수량을 재고 수량으로 변경한다.
					 */
					if (CommonCode.PROMO_TYPE_CODE_SPECIAL_PRICE_TIME.equals(promoTypeCode)) {
						stockVdQty = stockVdQty < eventLimitQty ? stockVdQty : eventLimitQty;
					}
					availableStockQty = stockVdQty;
				} else {
					availableStockQty = 9999;
				}
			}
		} else {
			stockAiQty = 0;
			stockAsQty = 0;
			stockAwQty = 0;
			stockVdQty = 0;
			availableStockQty = 0;
		}

		prdtOptionStock.setStockAiQty(stockAiQty);
		prdtOptionStock.setStockAsQty(stockAsQty);
		prdtOptionStock.setStockAwQty(stockAwQty);
		prdtOptionStock.setStockVdQty(stockVdQty);

		/*
		 * productOption.put("stockAiQty", productOptionStock.getStockAiQty());
		 * productOption.put("stockAsQty", productOptionStock.getStockAsQty());
		 * productOption.put("stockAwQty", productOptionStock.getStockAwQty());
		 * productOption.put("stockVdQty", productOptionStock.getStockVdQty());
		 */

//		if (CommonCode.SELL_STAT_CODE_PROC.equals(sellStatCode) && Const.BOOLEAN_TRUE.equals(useYn)) {
//			if (Const.BOOLEAN_TRUE.equals(mmnyPrdtYn)) { // 자사 상품
//				if (Const.BOOLEAN_TRUE.equals(storePickupYn)) { // 픽업 가능 상품 재고 조회시.
//					availableStockQty = stockAwQty;
//					// 서비스 호출
//					List<ProductOfflineStockHttp> offlines = productOfflineStockService
//							.getProductOfflineStockFromHttp(prdtOptionStock.getVndrPrdtNoText());
//
//					for (ProductOfflineStockHttp offline : offlines) {
//						if ("AS".equals(offline.getGubun())) {
//							availableStockQty += offline.getCurrQty();
//						}
//					}
//				} else {
//					if (Const.BOOLEAN_TRUE.equals(stockIntgrYn)) { // 재고 통합 관리
//						availableStockQty = stockAiQty + stockAwQty;
//						// 서비스 호출
//						List<ProductOfflineStockHttp> offlines = productOfflineStockService
//								.getProductOfflineStockFromHttp(prdtOptionStock.getVndrPrdtNoText());
//
//						for (ProductOfflineStockHttp offline : offlines) {
//							if ("AS".equals(offline.getGubun())) {
//								availableStockQty += offline.getCurrQty();
//							}
//						}
//					} else { // 재고 통합 안함
//						availableStockQty = stockAiQty + stockAwQty + 0;
//					}
//				}
//
//			} else {
//				if (Const.BOOLEAN_TRUE.equals(stockMgmtYn)) { // 재고 관리
//					availableStockQty = stockVdQty;
//				} else {
//					availableStockQty = 10000;
//				}
//			}
//		}

		return availableStockQty;
	}

	/**
	 * @Desc : 상품 옵션 재고 정보를 조회한다.
	 * @Method Name : selectProductOptionStock
	 * @Date : 2019. 6. 20.
	 * @Author : kiowa
	 * @param productOptionStock
	 * @return
	 * @throws Exception
	 */
	private List<PdProductOptionStock> selectProductOptionStock(List<Map<String, Object>> productOption)
			throws Exception {

		return productOptionDao.selectProductOptionStock(productOption);

	}

}
