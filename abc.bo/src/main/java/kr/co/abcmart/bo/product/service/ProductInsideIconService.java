package kr.co.abcmart.bo.product.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductIcon;
import kr.co.abcmart.bo.product.repository.master.PdProductIconDao;
import kr.co.abcmart.common.util.UtilsCollection;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품 아이콘 서비스
 * @FileName : ProductInsideIconService.java
 * @Project : abc.bo
 * @Date : 2019. 4. 17.
 * @Author : tennessee
 */
@Slf4j
@Service
public class ProductInsideIconService {

	@Autowired
	private PdProductIconDao productIconDao;

	/**
	 * @Desc : 상품 아이콘 등록
	 * @Method Name : regist
	 * @Date : 2019. 4. 17.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	public void regist(PdProduct product) throws Exception {
		// 기존 정보 삭제
		PdProductIcon productIcon = new PdProductIcon();
		productIcon.setPrdtNo(product.getPrdtNo());
		this.productIconDao.deleteByForeignKeys(productIcon);

		if (ArrayUtils.isNotEmpty(product.getProductIcon())) {
			for (PdProductIcon item : product.getProductIcon()) {
				item.setPrdtNo(product.getPrdtNo());
				item.setUseYn("Y");
				item.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
				item.setRgstDtm(new Timestamp(new Date().getTime()));
				this.productIconDao.insert(item);
			}
		}
	}

	/**
	 * @Desc : 상품에 해당하는 아이콘 목록 조회
	 * @Method Name : search
	 * @Date : 2019. 3. 27.
	 * @Author : tennessee
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public List<PdProductIcon> search(PdProduct product) throws Exception {
		List<PdProductIcon> result = null;

		if (!UtilsObject.isEmpty(product)) {
			PdProductIcon criteria = new PdProductIcon();
			criteria.setPrdtNo(product.getPrdtNo());
			result = this.productIconDao.select(criteria);
		}
		return result;
	}

	/**
	 * @Desc : 상품에 해당하는 아이콘을 조회하여 객체 내에 설정
	 * @Method Name : setProductIcon
	 * @Date : 2019. 4. 29.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	public void setProductIcon(PdProduct product) throws Exception {
		List<PdProductIcon> result = this.search(product);
		if (UtilsCollection.isNotEmpty(result)) {
			product.setProductIcon(result.toArray(new PdProductIcon[result.size()]));
		}
	}

}
