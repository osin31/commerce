package kr.co.abcmart.bo.promotion.repository.master.base;

import java.util.List;
import java.lang.Object;
import kr.co.abcmart.bo.promotion.model.master.PrPromotionMultiBuyDiscount;


/**
 * ※ 절대 수정 금지. 해당 파일은 code generator 작동 시 내용을 전부 덮어 씌우게 됩니다. 
 * 
 */

public interface BasePrPromotionMultiBuyDiscountDao {
	
    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	public List<PrPromotionMultiBuyDiscount> select(PrPromotionMultiBuyDiscount prPromotionMultiBuyDiscount) throws Exception;
	
    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	public int insert(PrPromotionMultiBuyDiscount prPromotionMultiBuyDiscount) throws Exception;
	
    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	public int update(PrPromotionMultiBuyDiscount prPromotionMultiBuyDiscount) throws Exception;
	
	 /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	public int delete(PrPromotionMultiBuyDiscount prPromotionMultiBuyDiscount) throws Exception;


}
