package kr.co.abcmart.bo.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.PdProductMemo;
import kr.co.abcmart.bo.product.repository.master.PdProductMemoDao;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품 메모 서비스
 * @FileName : ProductInsideMemoService.java
 * @Project : abc.bo
 * @Date : 2019. 4. 17.
 * @Author : tennessee
 */
@Slf4j
@Service
public class ProductInsideMemoService {

	@Autowired
	private PdProductMemoDao productMemoDao;

	@Autowired
	private ProductReflectionService productReflectionService;

	/**
	 * @Desc : 상품번호에 대한 상품메모 조회
	 * @Method Name : searchProductMemo
	 * @Date : 2019. 3. 7.
	 * @Author : tennessee
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public List<PdProductMemo> searchByPrdtNo(String prdtNo) throws Exception {
		List<PdProductMemo> productMemoList = this.productMemoDao.selectByProduct(prdtNo);

//		if (UtilsCollection.isNotEmpty(productMemoList)) {
//			for (PdProductMemo item : productMemoList) {
//				item.setPrivacy();
//			}
//		}

		return productMemoList;
	}

	/**
	 * @Desc : 관리자 메모 저장
	 * @Method Name : insertProductdMeno
	 * @Date : 2019. 3. 7.
	 * @Author : SANTA
	 * @param productMemo
	 * @throws Exception
	 */
	public void insert(PdProductMemo productMemo) throws Exception {
		this.productReflectionService.setUserInfo(productMemo);
		this.productMemoDao.insertProductMemo(productMemo);
	}

	/**
	 * @Desc : 관리자 메모 삭제
	 * @Method Name : deleteProductdMeno
	 * @Date : 2019. 3. 7.
	 * @Author : SANTA
	 * @param pdProductMemo
	 * @throws Exception
	 */
	public void delete(PdProductMemo pdProductMemo) throws Exception {
		this.productMemoDao.delete(pdProductMemo);
	}

}
