package kr.co.abcmart.bo.product.service;

import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.product.model.master.PdProduct;
import kr.co.abcmart.bo.product.model.master.PdProductPriceHistory;
import kr.co.abcmart.bo.product.repository.master.PdProductPriceHistoryDao;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsArray;
import kr.co.abcmart.common.util.UtilsNumber;
import kr.co.abcmart.common.util.UtilsObject;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 상품 가격 이력 서비스
 * @FileName : ProductInsidePriceHistoryService.java
 * @Project : abc.bo
 * @Date : 2019. 3. 13.
 * @Author : tennessee
 */
@Slf4j
@Service
public class ProductInsidePriceHistoryService {

	@Autowired
	private PdProductPriceHistoryDao productPriceHistoryDao;

	@Autowired
	private ProductReflectionService productReflectionService;

	/**
	 * @Desc : 상품 가격 이력 저장
	 * @Method Name : insert
	 * @Date : 2019. 4. 17.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	public void insert(PdProduct product) throws Exception {
		if (UtilsText.isNotBlank(product.getPrdtNo()) && UtilsArray.isNotEmpty(product.getProductPriceHistory())) {

			// 기존 가격이력 조회
			PdProductPriceHistory before = this.getApplyingPriceByPrdtNo(product.getPrdtNo(), product.getChnnlNo());
			PdProductPriceHistory after = product.getProductPriceHistory()[0];

			after.setDefaultData(product);
			after.setPrdtNo(product.getPrdtNo());
			this.productReflectionService.setUserInfo(after);

			if (UtilsObject.isEmpty(before)) {
				// 가격이력이 없는 경우
				this.productPriceHistoryDao.insertWithSelectKey(after);
			} else {
				if (after.compareTo(before) != 0) {
					// 기존적용가격 적용날짜 변경(미적용)
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DATE, -1);
					before.setApplyEndDtm(new Timestamp(c.getTime().getTime()));
					this.productPriceHistoryDao.update(before);

					// 두 정보가 다른 경우, 가격 이력 저장 시작.
					this.productPriceHistoryDao.insertWithSelectKey(after);
				}
			}

		}
	}

	/**
	 * @Desc : 상품 가격이력 중에서 현재 시간기준 상품가격정보를 반환. 여러 이력이 조회된 경우, 상품가격이력순번 역순으로 반환
	 * @Method Name : getApplyingPriceByPrdtNo
	 * @Date : 2019. 3. 5.
	 * @Author : tennessee
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public PdProductPriceHistory getApplyingPriceByPrdtNo(String prdtNo, String chnnlNo) throws Exception {
		return this.productPriceHistoryDao.selectApplyingPriceByPrdtNo(prdtNo, chnnlNo);
	}

	/**
	 * @Desc : 최근상품정보를 조회하여 상품객체 내에 설정
	 * @Method Name : setRecentProductPriceHistory
	 * @Date : 2019. 4. 5.
	 * @Author : tennessee
	 * @param product
	 * @throws Exception
	 */
	public void setProductPriceHistory(PdProduct product) throws Exception {
		if (!UtilsObject.isEmpty(product) && UtilsText.isNotBlank(product.getPrdtNo())) {
//			PdProductPriceHistory result = this.getApplyingPriceByPrdtNo(product.getPrdtNo());
			PdProductPriceHistory result = this.getApplyingPriceByPrdtNo(product.getPrdtNo(), product.getChnnlNo());
			if (!UtilsObject.isEmpty(result)) {
				product.setProductPriceHistory(new PdProductPriceHistory[] { result });
			}
		}
	}

	/**
	 * @Desc : 상품 가격 이력 조회
	 * @Method Name : getProdutPriceHistory
	 * @Date : 2019. 4. 22.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<PdProductPriceHistory> getProdutPriceHistory(
			Pageable<PdProductPriceHistory, PdProductPriceHistory> pageable) throws Exception {
		Integer count = this.getProductPriceHistoryTotalCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(this.productPriceHistoryDao.selectByPrdtNo(pageable));

			for (PdProductPriceHistory item : pageable.getContent()) {
				item.setPrivacy();
			}
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 상품 가격 이력 전체 갯수 조회
	 * @Method Name : getProductPriceHistoryTotalCount
	 * @Date : 2019. 4. 22.
	 * @Author : tennessee
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Integer getProductPriceHistoryTotalCount(Pageable<PdProductPriceHistory, PdProductPriceHistory> pageable)
			throws Exception {
		return this.productPriceHistoryDao.selectByPrdtNoTotalCount(pageable);
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
		boolean result = false;
		PdProductPriceHistory target = this.productPriceHistoryDao.selectApplyingPriceByPrdtNo(product.getPrdtNo(),
				product.getChnnlNo());

		if (UtilsArray.isEmpty(product.getProductPriceHistory()) || UtilsObject.isEmpty(target)) {
			return false;
		}

		// 최근 내용을 조회하여 정상가, 가격기준으로만 비교해야 함
		if (product.getProductPriceHistory().length == 1) {
			PdProductPriceHistory source = product.getProductPriceHistory()[0];

			if (UtilsText.equals(Const.BOOLEAN_TRUE, product.getMmnyPrdtYn())) {

				// 자사상품인 경우, 정상가/온라인판매가/오프라인판매가 비교
				if (UtilsObject.isNotEmpty(target)
						&& (UtilsNumber.compare(source.getNormalAmt(), target.getNormalAmt()) == 0)
						&& (UtilsNumber.compare(source.getOnlnSellAmt(), target.getOnlnSellAmt()) == 0)
						&& (UtilsNumber.compare(source.getErpSellAmt(), target.getErpSellAmt()) == 0)) {
					result = true;
				}

			} else {

				// 입점사인 경우, 정상가/온라인판매가 비교
				if (UtilsObject.isNotEmpty(target)
						&& (UtilsNumber.compare(source.getNormalAmt(), target.getNormalAmt()) == 0)
						&& (UtilsNumber.compare(source.getOnlnSellAmt(), target.getOnlnSellAmt()) == 0)) {
					result = true;
				}
			}
		}
		return result;
	}

}
