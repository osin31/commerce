package kr.co.abcmart.bo.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductDetail;
import kr.co.abcmart.bo.product.repository.master.PdProductDetailDao;
import kr.co.abcmart.common.util.UtilsArray;
import kr.co.abcmart.common.util.UtilsCollection;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품 상세 서비스
 * @FileName : ProductInsideDetailService.java
 * @Project : abc.bo
 * @Date : 2019. 4. 17.
 * @Author : tennessee
 */
@Slf4j
@Service
public class ProductInsideDetailService {

	@Autowired
	private PdProductDetailDao productDetailDao;

	@Autowired
	private ProductReflectionService productReflectionService;

	/**
	 * @Desc : 상품상세정보 등록/수정/삭제
	 * @Method Name : regist
	 * @Date : 2019. 10. 28.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	public void regist(PdProduct product) throws Exception {
		if (UtilsArray.isNotEmpty(product.getProductDetail())) {
			for (PdProductDetail item : product.getProductDetail()) {

				item.setPrdtNo(product.getPrdtNo());

				if (UtilsText.isBlank(item.getPrdtDtlInfo())) {
					// 상세내용이 없는 경우, 삭제
					this.productDetailDao.delete(item);
				} else {
					// 상세내용이 있는 경우, 등록/수정

					PdProductDetail criteria = new PdProductDetail();
					criteria.setPrdtNo(product.getPrdtNo());
					criteria.setDeviceCode(item.getDeviceCode());
					PdProductDetail storedData = this.productDetailDao.selectByPrimaryKey(criteria);

					if (UtilsObject.isEmpty(storedData)) {
						// 기존에 저장된 데이터가 없는 경우
						this.productReflectionService.setUserInfo(item);
						this.productDetailDao.insert(item);
					} else {
						// 기존에 저장된 데이터가 있는 경우
						this.productDetailDao.update(item);
					}
				}
			}
		}
	}

	/**
	 * @Desc : 상품상세정보 등록
	 * @Method Name : insert
	 * @Date : 2019. 4. 3.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	public void insert(PdProduct product) throws Exception {
		if (UtilsArray.isNotEmpty(product.getProductDetail())) {
			for (PdProductDetail item : product.getProductDetail()) {
				if (UtilsText.isNotBlank(item.getPrdtDtlInfo())) {
					item.setPrdtNo(product.getPrdtNo());
					this.productReflectionService.setUserInfo(item);
					this.productDetailDao.insert(item);
				}
			}
		}
	}

	/**
	 * @Desc : 상품상세정보 수정. regist로 대체됨.
	 * @Method Name : update
	 * @Date : 2019. 4. 3.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	@Deprecated
	public void update(PdProduct product) throws Exception {
		if (UtilsArray.isNotEmpty(product.getProductDetail())) {
			for (PdProductDetail item : product.getProductDetail()) {
				item.setPrdtNo(product.getPrdtNo());
				if (UtilsText.isNotBlank(item.getPrdtDtlInfo())) {
					this.productReflectionService.setUserInfo(item);
					this.productDetailDao.update(item);
				} else {
					this.productDetailDao.delete(item);
				}
			}
		}
	}

	/**
	 * @Desc : 상품 정보 내에 상세 정보 설정
	 * @Method Name : setIntoProduct
	 * @Date : 2019. 4. 17.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	public void setIntoProduct(PdProduct product) throws Exception {
		List<PdProductDetail> result = null;
		result = this.productDetailDao.selectByPrdtNo(product.getPrdtNo());
		if (UtilsCollection.isNotEmpty(result)) {
			product.setProductDetail(result.toArray(new PdProductDetail[result.size()]));
		}
	}

	/**
	 * @Desc : 최근 등록 된 내용과 현재 내용을 비교하여 다른 항목 갯수를 반환
	 * @Method Name : isPossibleAutoApproval
	 * @Date : 2019. 5. 9.
	 * @Author : tennessee
	 * @param product
	 * @return
	 * @throws Exception
	 */
	public boolean isPossibleAutoApproval(PdProduct product) throws Exception {
		List<PdProductDetail> origin = this.productDetailDao.selectByPrdtNo(product.getPrdtNo());

		if (UtilsCollection.isEmpty(origin) && UtilsArray.isEmpty(product.getProductDetail())) {
			return true;
		}

		int differCount = 0;

		if (origin.size() > 0 && UtilsArray.isEmpty(product.getProductDetail())) {
			return false;
		}

		for (PdProductDetail source : product.getProductDetail()) {

			List<PdProductDetail> sameDeviceCodeDetail = origin.stream()
					.filter(item -> UtilsText.equals(item.getDeviceCode(), source.getDeviceCode()))
					.collect(Collectors.toList());

			if (UtilsText.isBlank(source.getPrdtDtlInfo())) {
				// 신규 입력값 없음

				if (UtilsCollection.isEmpty(sameDeviceCodeDetail)) {
					// 기존 입력값 없음
				} else {
					// 기존 입력값 있음
					differCount += 1;
				}

			} else {
				// 신규 입력값 있음

				if (UtilsCollection.isEmpty(sameDeviceCodeDetail)) {
					// 기존 입력값 없음
					differCount += 1;
				} else {
					// 기존 입력값 있음
					differCount += sameDeviceCodeDetail.get(0).compareTo(source);
				}

			}
		}

		return differCount == 0 ? true : false;
	}
}
