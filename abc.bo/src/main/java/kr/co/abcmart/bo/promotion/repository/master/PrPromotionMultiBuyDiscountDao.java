package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.promotion.model.master.PrPromotionMultiBuyDiscount;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrPromotionMultiBuyDiscountDao;

@Mapper
public interface PrPromotionMultiBuyDiscountDao extends BasePrPromotionMultiBuyDiscountDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrPromotionMultiBuyDiscountDao 클래스에 구현
	 * 되어있습니다. BasePrPromotionMultiBuyDiscountDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당
	 * 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public PrPromotionMultiBuyDiscount selectByPrimaryKey(PrPromotionMultiBuyDiscount prPromotionMultiBuyDiscount)
			throws Exception;

	/**
	 * 프로모션 다족구매 조회
	 * 
	 * @Desc : 프로모션 다족구매 조회
	 * @Method Name : selectPromotionMultiBuyDiscountListByPromoNo
	 * @Date : 2019. 3. 20
	 * @Author : easyhun
	 * @param promoNo
	 * @return
	 */
	public List<PrPromotionMultiBuyDiscount> selectPromotionMultiBuyDiscountListByPromoNo(String promoNo)
			throws Exception;

	/**
	 * 프로모션 연계테이블 삭제
	 * 
	 * @Desc : 프로모션 수정
	 * @Method Name : deleteByPromoNo
	 * @Date : 2019. 3. 20
	 * @Author : easyhun
	 * @param String promoNo
	 * @throws Exception
	 */
	public void deleteByPromoNo(String promoNo) throws Exception;

}
