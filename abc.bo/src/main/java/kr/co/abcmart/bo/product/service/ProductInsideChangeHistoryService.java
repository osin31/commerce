package kr.co.abcmart.bo.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductChangeHistory;
import kr.co.abcmart.bo.product.model.master.PdProductPriceHistory;
import kr.co.abcmart.bo.product.repository.master.PdProductChangeHistoryDao;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsArray;
import kr.co.abcmart.common.util.UtilsCollection;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품 변경 이력 서비스
 * @FileName : ProductInsideChangeHistoryService.java
 * @Project : abc.bo
 * @Date : 2019. 4. 17.
 * @Author : tennessee
 */
@Slf4j
@Service
public class ProductInsideChangeHistoryService {

	@Autowired
	private PdProductChangeHistoryDao productChangeHistoryDao;

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductInsidePriceHistoryService productInsidePriceHistoryService;

	@Autowired
	private ProductReflectionService productReflectionService;

	/**
	 * @Desc : 상품 변경 이력 등록
	 * @Method Name : insert
	 * @Date : 2019. 4. 17.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	public void insert(PdProduct product) throws Exception {
		List<PdProductChangeHistory> insertList = new ArrayList<PdProductChangeHistory>();

		// 기존 정보 조회
		PdProduct storedProduct = this.productService.getProduct(product.getPrdtNo(), product.getSiteNo(),
				product.getChnnlNo(), product.getVndrPrdtNoText());

		if (UtilsObject.isNotEmpty(storedProduct)) {
			// 이전 상품정보가 있는 경우에만 수행. 신규 상품은 변경이력을 남기지 않음.

			// 상품 비교
			List<PdProductChangeHistory> changeHistoryForProduct = this.productReflectionService
					.compareChangeHistoryForProduct(storedProduct, product);
			if (UtilsCollection.isNotEmpty(changeHistoryForProduct)) {
				insertList.addAll(changeHistoryForProduct);
			}

			// 상품가격이력 비교
			if (UtilsArray.isNotEmpty(product.getProductPriceHistory())) {
				// 기존 저장 가격 정보. 최근 1건.
				PdProductPriceHistory storedProductPriceHistory = this.productInsidePriceHistoryService
						.getApplyingPriceByPrdtNo(product.getPrdtNo(), product.getChnnlNo());

				if (!UtilsObject.isEmpty(storedProductPriceHistory)) {
					List<PdProductChangeHistory> changeHistoryForPrice = this.productReflectionService
							.compareChangeHistoryForPrice(storedProductPriceHistory, product);
					if (UtilsCollection.isNotEmpty(changeHistoryForPrice)) {
						insertList.addAll(changeHistoryForPrice);
					}
				}
			}
		}

		// 변경 이력 저장
		if (UtilsCollection.isNotEmpty(insertList)) {
			for (PdProductChangeHistory item : insertList) {
				this.productChangeHistoryDao.insertWithSelectKey(item);
			}
		}
	}

	/**
	 * @Desc : 상품 변경 이력 등록 (aconnect 상품전시관리)
	 * @Method Name : insertForAconnect
	 * @Date : 2019. 8. 14.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	public void insertForAconnect(PdProduct product) throws Exception {

		PdProductChangeHistory history = null;
		UserDetails user = LoginManager.getUserDetails();

		String afterValue = product.getAconnectDispYn();
		String beforeValue = UtilsText.equals(afterValue, Const.BOOLEAN_TRUE) ? Const.BOOLEAN_FALSE
				: Const.BOOLEAN_TRUE;

		if (!UtilsText.equals(beforeValue, afterValue)) {
			history = new PdProductChangeHistory();
			history.setPrdtNo(product.getPrdtNo());
			history.setChngField("aconnect_disp_yn"); // 변경필드
			history.setChngFieldName("A-Connect 전시여부"); // 변경필드명
			history.setChngBeforeFieldValue(beforeValue); // 변경전필드값
			history.setChngAfterFieldValue(afterValue); // 변경후필드값
			history.setRgsterNo(user.getAdminNo());
		}

		this.productChangeHistoryDao.insertWithSelectKey(history);
	}

	/**
	 * @Desc : 상품 변경 이력 조회
	 * @Method Name : getProdutChangeHistory
	 * @Date : 2019. 4. 22.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PdProductChangeHistory> getProdutChangeHistory(
			Pageable<PdProductChangeHistory, PdProductChangeHistory> pageable) throws Exception {
		Integer count = this.getProductChangeHistoryTotalCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(this.productChangeHistoryDao.selectByPrdtNo(pageable));

			for (PdProductChangeHistory item : pageable.getContent()) {
				item.setPrivacy();
			}
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 상품 변경 이력 총 갯수 조회
	 * @Method Name : getProductChangeHistoryTotalCount
	 * @Date : 2019. 4. 22.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Integer getProductChangeHistoryTotalCount(Pageable<PdProductChangeHistory, PdProductChangeHistory> pageable)
			throws Exception {
		return this.productChangeHistoryDao.selectByPrdtNoTotalCount(pageable);
	}

	/**
	 * @Desc : 상품 변경 이력 저장
	 * @Method Name : insert
	 * @Date : 2019. 7. 19.
	 * @Author : tennessee
	 * @param productChangeHistory
	 * @throws Exception
	 */
	public void insert(PdProductChangeHistory productChangeHistory) throws Exception {
		this.productChangeHistoryDao.insertWithSelectKey(productChangeHistory);
	}

}
