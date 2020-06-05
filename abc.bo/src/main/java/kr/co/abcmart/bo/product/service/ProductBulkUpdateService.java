package kr.co.abcmart.bo.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.display.model.master.DpCategoryProduct;
import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductApprovalHistory;
import kr.co.abcmart.bo.product.model.master.PdProductBulkUpdateWrapper;
import kr.co.abcmart.bo.product.model.master.PdProductChangeHistory;
import kr.co.abcmart.bo.product.model.master.PdProductColor;
import kr.co.abcmart.bo.product.model.master.PdProductIcon;
import kr.co.abcmart.bo.product.model.master.PdRelationProduct;
import kr.co.abcmart.bo.product.repository.master.PdProductBulkUpdateDao;
import kr.co.abcmart.common.util.UtilsArray;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc :
 * @FileName : ProductBulkUpdateService.java
 * @Project : abc.bo
 * @Date : 2019. 7. 18.
 * @Author : tennessee
 */
@Slf4j
@Service
public class ProductBulkUpdateService {

	@Autowired
	private PdProductBulkUpdateDao productBulkUpdateDao;

	@Autowired
	private ProductInsideChangeHistoryService productInsideChangeHistoryService;

	@Autowired
	private ProductInsideApprovalHistoryService productInsideApprovalHistoryService;

	@Autowired
	private ProductReflectionService productReflectionService;

	/**
	 * @Desc : 대량수정
	 * @Method Name : regist
	 * @Date : 2019. 7. 18.
	 * @Author : tennessee
	 * @param productBulkUpdateWrapper
	 * @throws Exception
	 */
	public PdProductBulkUpdateWrapper regist(PdProductBulkUpdateWrapper productBulkUpdateWrapper) throws Exception {
		this.registProduct(productBulkUpdateWrapper); // 상품 정보 대량 수정
		this.registIcon(productBulkUpdateWrapper); // 상품 아이콘 전체 삭제 후 대량 등록
		this.registDisplayCategory(productBulkUpdateWrapper); // 전시카테고리 전체 삭제 후 대량 등록
		this.registColor(productBulkUpdateWrapper); // 상품 색상 전체 삭제 후 대량 등록
		this.registGoods(productBulkUpdateWrapper); // 관련용품 전체 삭제 후 대량 등록
		this.registChangeHistory(productBulkUpdateWrapper); // 변경이력 남기기
		this.registApprovalHistory(productBulkUpdateWrapper); // 승인 또는 승인요청 처리

		return productBulkUpdateWrapper;
	}

	/**
	 * @Desc : 상품 정보 처리
	 * @Method Name : registProduct
	 * @Date : 2019. 7. 19.
	 * @Author : tennessee
	 * @param productBulkUpdateWrapper
	 * @throws Exception
	 */
	private void registProduct(PdProductBulkUpdateWrapper productBulkUpdateWrapper) throws Exception {
		if (UtilsText.equals(productBulkUpdateWrapper.getAuth(), Const.AUTH_APPLY_SYSTEM_TYPE_PO)) {
			// PO권한인 경우, 승인요청상태로 변경
			productBulkUpdateWrapper.setAprvStatCode(CommonCode.APRV_STAT_CODE_REQUEST);
		}
		this.productReflectionService.setAprverInfo(productBulkUpdateWrapper);
		this.productReflectionService.setModifierInfo(productBulkUpdateWrapper);
		this.productBulkUpdateDao.updateProduct(productBulkUpdateWrapper);
		this.productBulkUpdateDao.updateProductOption(productBulkUpdateWrapper);
	}

	/**
	 * @Desc : 아이콘 처리
	 * @Method Name : registIcon
	 * @Date : 2019. 7. 19.
	 * @Author : tennessee
	 * @param productBulkUpdateWrapper
	 * @throws Exception
	 */
	private void registIcon(PdProductBulkUpdateWrapper productBulkUpdateWrapper) throws Exception {
		if (UtilsArray.isNotEmpty(productBulkUpdateWrapper.getProductIcon())) {

			this.productBulkUpdateDao.deleteProductIcon(productBulkUpdateWrapper);

			for (PdProduct product : productBulkUpdateWrapper.getProducts()) {
				for (PdProductIcon item : productBulkUpdateWrapper.getProductIcon()) {
					item.setPrdtNo(product.getPrdtNo());
					this.productReflectionService.setUserInfo(item);
				}
				this.productBulkUpdateDao.insertProductIcon(productBulkUpdateWrapper);
			}
		}
	}

	/**
	 * @Desc : 전시카테고리 처리
	 * @Method Name : registDisplayCategory
	 * @Date : 2019. 7. 19.
	 * @Author : tennessee
	 * @param productBulkUpdateWrapper
	 * @throws Exception
	 */
	private void registDisplayCategory(PdProductBulkUpdateWrapper productBulkUpdateWrapper) throws Exception {
		if (UtilsArray.isNotEmpty(productBulkUpdateWrapper.getDisplayCategories())) {

			this.productBulkUpdateDao.deleteDisplayCategory(productBulkUpdateWrapper);

			for (PdProduct product : productBulkUpdateWrapper.getProducts()) {
				for (DpCategoryProduct item : productBulkUpdateWrapper.getDisplayCategories()) {
					item.setPrdtNo(product.getPrdtNo());
					this.productReflectionService.setUserInfo(item);
				}
				this.productBulkUpdateDao.insertDisplayCategory(productBulkUpdateWrapper);
			}
		}
	}

	/**
	 * @Desc : 색상 처리
	 * @Method Name : registColor
	 * @Date : 2019. 7. 19.
	 * @Author : tennessee
	 * @param productBulkUpdateWrapper
	 * @throws Exception
	 */
	private void registColor(PdProductBulkUpdateWrapper productBulkUpdateWrapper) throws Exception {
		if (UtilsArray.isNotEmpty(productBulkUpdateWrapper.getProductColor())) {

			this.productBulkUpdateDao.deleteProductColor(productBulkUpdateWrapper);

			for (PdProduct product : productBulkUpdateWrapper.getProducts()) {
				for (PdProductColor item : productBulkUpdateWrapper.getProductColor()) {
					item.setPrdtNo(product.getPrdtNo());
					this.productReflectionService.setUserInfo(item);
				}
				this.productBulkUpdateDao.insertProductColor(productBulkUpdateWrapper);
			}
		}
	}

	/**
	 * @Desc : 관련용품 처리
	 * @Method Name : registGoods
	 * @Date : 2019. 7. 19.
	 * @Author : tennessee
	 * @param productBulkUpdateWrapper
	 * @throws Exception
	 */
	private void registGoods(PdProductBulkUpdateWrapper productBulkUpdateWrapper) throws Exception {
		if (UtilsText.isNotBlank(productBulkUpdateWrapper.getRltnGoodsSetupYn())) {
			if (UtilsText.equals(Const.BOOLEAN_TRUE, productBulkUpdateWrapper.getRltnGoodsSetupYn())) {
				// 관련상품 설정
				if (UtilsArray.isNotEmpty(productBulkUpdateWrapper.getRelationProducts())) {
					// 관련상품 목록 있음
					this.productBulkUpdateDao.deleteRelationProductGoods(productBulkUpdateWrapper);

					for (PdProduct product : productBulkUpdateWrapper.getProducts()) {
						for (PdRelationProduct item : productBulkUpdateWrapper.getRelationProducts()) {
							item.setPrdtNo(product.getPrdtNo());
							this.productReflectionService.setUserInfo(item);
						}
						this.productBulkUpdateDao.insertRelationProductGoods(productBulkUpdateWrapper);
					}
				} else {
					// 관련상품 목록 없음
					this.productBulkUpdateDao.deleteRelationProductGoods(productBulkUpdateWrapper);
				}
			} else {
				// 관련상품 설정안함
				this.productBulkUpdateDao.deleteRelationProductGoods(productBulkUpdateWrapper);
			}
		}
	}

	/**
	 * @Desc : 변경이력 처리
	 * @Method Name : registChangeHistory
	 * @Date : 2019. 7. 19.
	 * @Author : tennessee
	 * @param productBulkUpdateWrapper
	 * @throws Exception
	 */
	private void registChangeHistory(PdProductBulkUpdateWrapper productBulkUpdateWrapper) throws Exception {
		for (PdProduct product : productBulkUpdateWrapper.getProducts()) {

			PdProductChangeHistory history = new PdProductChangeHistory();
			history.setPrdtNo(product.getPrdtNo());
			history.setChngField("BULK_UPDATE");
			history.setChngFieldName("일괄수정");
			this.productReflectionService.setUserInfo(history);

			try {
				String productInfo = UtilsText.stringify(product);
				if (UtilsText.getByteLength(productInfo) > 1900) {
					productInfo = UtilsText.strByteSubstring(productInfo, 0, 1900) + "...";
				}
				history.setChngAfterFieldValue(productInfo);
				this.productInsideChangeHistoryService.insert(history);
			}catch(Exception e) {

				history.setChngAfterFieldValue("일괄수정");
				this.productInsideChangeHistoryService.insert(history);
			}

		}
	}

	/**
	 * @Desc : 승인이력 처리
	 * @Method Name : registApprovalHistory
	 * @Date : 2019. 7. 19.
	 * @Author : tennessee
	 * @param productBulkUpdateWrapper
	 * @throws Exception
	 */
	private void registApprovalHistory(PdProductBulkUpdateWrapper productBulkUpdateWrapper) throws Exception {
		for (PdProduct product : productBulkUpdateWrapper.getProducts()) {
			PdProductApprovalHistory history = new PdProductApprovalHistory();
			history.setPrdtNo(product.getPrdtNo());
			history.setPrdtAprvReqCode(CommonCode.PRDT_APRV_REQ_CODE_MODIFY_INFO);

			if (UtilsText.equals(productBulkUpdateWrapper.getAuth(), Const.AUTH_APPLY_SYSTEM_TYPE_BO)) {
				history.setPrdtAprvStatCode(CommonCode.PRDT_APRV_STAT_CODE_COMPLETE);
			} else {
				history.setPrdtAprvStatCode(CommonCode.PRDT_APRV_STAT_CODE_REQUEST);
			}

			this.productReflectionService.setReqtrInfo(history);
			this.productReflectionService.setAprverInfo(history);

			this.productInsideApprovalHistoryService.insert(history);
		}
	}

}
