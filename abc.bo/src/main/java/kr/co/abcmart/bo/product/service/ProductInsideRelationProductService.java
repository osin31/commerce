package kr.co.abcmart.bo.product.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdRelationProduct;
import kr.co.abcmart.bo.product.repository.master.PdProductDao;
import kr.co.abcmart.bo.product.repository.master.PdRelationProductDao;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsArray;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 연관 상품 서비스
 * @FileName : ProductInsideRelationProductService.java
 * @Project : abc.bo
 * @Date : 2019. 4. 17.
 * @Author : tennessee
 */
@Slf4j
@Service
public class ProductInsideRelationProductService {

	@Autowired
	private PdRelationProductDao relationProductDao;

	@Autowired
	private ProductReflectionService productReflectionService;
	
	@Autowired
	private PdProductDao productDao;

	/**
	 * @Desc : 연관 상품 등록
	 * @Method Name : regist
	 * @Date : 2019. 4. 17.
	 * @Author : tennessee
	 * @param product
	 * @param relationProductsUseYn
	 * @param relationProducts
	 * @param rltnPrdtTypeCode
	 * @throws Exception
	 */
	public void regist(PdProduct product, String relationProductsUseYn, PdRelationProduct[] relationProducts,
			String rltnPrdtTypeCode) throws Exception {
		if (UtilsText.equals(relationProductsUseYn, "Y") && UtilsArray.isNotEmpty(relationProducts)) {
			// 등록 전 삭제를 먼저 수행함
			this.deleteRelationProduct(product, rltnPrdtTypeCode);

			for (PdRelationProduct item : relationProducts) {
				item.setPrdtNo(product.getPrdtNo());
				if(!UtilsText.equals(item.getPrdtNo(), item.getRltnPrdtNo())) {
					item.setRltnPrdtTypeCode(rltnPrdtTypeCode);
					
					if(!UtilsText.equals(Const.CRUD_D, item.getStatus())){
						item.setStatus(Const.CRUD_I);
					}
					
					switch (item.getStatus()) {
					case Const.CRUD_I:
						// 신규
						this.productReflectionService.setUserInfo(item);
						//연계상품 등록
						this.relationProductDao.insertWithPrimaryKey(item);
						//연계상품에 등록된 상품 역으로 등록
						if(UtilsText.equals(rltnPrdtTypeCode, CommonCode.RLTN_PRDT_TYPE_CODE_PRODUCT)) {
							this.setWithKeyReverse(item);
						}
						break;
					case Const.CRUD_U:
						// 갱신. 해당경우 없음.
						break;
					case Const.CRUD_D:
						// 삭제. 해당경우 없음.
						break;
					default:
						log.debug("연관 상품 등록에서 지원하지 않는 유형입니다. {}", item.getStatus());
					}
				}
			}
		} else {
			// 사용하지 않는 경우, 삭제
			this.deleteRelationProduct(product, rltnPrdtTypeCode);
		}
	}

	/**
	 * @Desc : 연관상품 조회
	 * @Method Name : searchRelationProduct
	 * @Date : 2019. 5. 9.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PdRelationProduct> searchRelationProduct(Pageable<PdRelationProduct, PdRelationProduct> pageable)
			throws Exception {
		if (UtilsText.isBlank(pageable.getBean().getPrdtNo())) {
			pageable.setContent(new ArrayList<PdRelationProduct>());
		} else {
			Integer count = this.relationProductDao.selectRelationProductCount(pageable);
			pageable.setTotalCount(count);

			if (count > 0) {
				pageable.setContent(this.relationProductDao.selectRelationProduct(pageable));
			}
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 상품에 해당하는 연관상품을 삭제
	 * @Method Name : deleteRelationProduct
	 * @Date : 2019. 5. 9.
	 * @Author : tennessee
	 * @param product
	 * @param rltnPrdtTypeCode
	 * @throws Exception
	 */
	private void deleteRelationProduct(PdProduct product, String rltnPrdtTypeCode) throws Exception {
		// 사용하지 않는 경우, 삭제
		PdRelationProduct criteriaForDelete = new PdRelationProduct();
		criteriaForDelete.setPrdtNo(product.getPrdtNo());
		criteriaForDelete.setRltnPrdtTypeCode(rltnPrdtTypeCode);
		this.relationProductDao.deleteByRltnPrdtTypeCode(criteriaForDelete);
	}
	
	/**
	 * @Desc      	: 연계상품에 등록된 상품 역으로 등록
	 * @Method Name : setWithKeyReverse
	 * @Date  	  	: 2020. 4. 20.
	 * @Author    	: sic
	 * @param item
	 * @throws Exception
	 */
	public void setWithKeyReverse(PdRelationProduct item)throws Exception{
		if(!UtilsText.equals(item.getPrdtNo(), item.getRltnPrdtNo())) {
			PdProduct product = new PdProduct();
			product.setPrdtNo(item.getRltnPrdtNo());
			//연계상품에 역으로 등록되는 상품의 연계상품 여부 수정
			productDao.updateCntcPrdtSetup(product);
			//연계상품에 등록되었던 상품 역으로 등록
			relationProductDao.insertWithKeyReverse(item);
		}
	}

}
