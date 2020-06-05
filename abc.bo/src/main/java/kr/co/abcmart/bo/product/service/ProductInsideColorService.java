package kr.co.abcmart.bo.product.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductColor;
import kr.co.abcmart.bo.product.repository.master.PdProductColorDao;
import kr.co.abcmart.common.util.UtilsCollection;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품 색상 서비스
 * @FileName : ProductInsideColorService.java
 * @Project : abc.bo
 * @Date : 2019. 4. 17.
 * @Author : tennessee
 */
@Slf4j
@Service
public class ProductInsideColorService {

	@Autowired
	private PdProductColorDao productColorDao;

	/**
	 * @Desc : 상품 색상 등록
	 * @Method Name : registProductColor
	 * @Date : 2019. 4. 17.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	public void regist(PdProduct product) throws Exception {
		// 기존 정보 삭제
		PdProductColor productColor = new PdProductColor();
		productColor.setPrdtNo(product.getPrdtNo());
		this.productColorDao.deleteByForeignKeys(productColor);

		if (ArrayUtils.isNotEmpty(product.getProductColor())) {
			for (PdProductColor item : product.getProductColor()) {
				item.setPrdtNo(product.getPrdtNo());
				item.setUseYn("Y");
				item.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
				item.setRgstDtm(new Timestamp(new Date().getTime()));
				this.productColorDao.insert(item);
			}
		}
	}

	/**
	 * @Desc : 상품 번호에 대한 색상 조회
	 * @Method Name : searchProductColor
	 * @Date : 2019. 3. 27.
	 * @Author : tennessee
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public List<PdProductColor> searchProductColor(PdProduct product) throws Exception {
		List<PdProductColor> result = null;
		if (!UtilsObject.isEmpty(product) && UtilsText.isNotBlank(product.getPrdtNo())) {
			PdProductColor criteria = new PdProductColor();
			criteria.setPrdtNo(product.getPrdtNo());
			result = this.productColorDao.select(criteria);
		}
		return result;
	}

	/**
	 * @Desc : 상품 번호에 대한 색상 조회
	 * @Method Name : setProductColor
	 * @Date : 2019. 4. 3.
	 * @Author : tennessee
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public void setProductColor(PdProduct product) throws Exception {
		List<PdProductColor> result = this.searchProductColor(product);
		if (UtilsCollection.isNotEmpty(result)) {
			product.setProductColor(result.toArray(new PdProductColor[result.size()]));
		}
	}

}
