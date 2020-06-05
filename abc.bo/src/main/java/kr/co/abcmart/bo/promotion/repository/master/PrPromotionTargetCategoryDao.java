package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.promotion.model.master.PrPromotionTargetCategory;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrPromotionTargetCategoryDao;

@Mapper
public interface PrPromotionTargetCategoryDao extends BasePrPromotionTargetCategoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrPromotionTargetCategoryDao 클래스에 구현
	 * 되어있습니다. BasePrPromotionTargetCategoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당
	 * 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public PrPromotionTargetCategory selectByPrimaryKey(PrPromotionTargetCategory prPromotionTargetCategory)
			throws Exception;

	/**
	 * 프로모션 카테고리 조회
	 * 
	 * @Desc :
	 * @Method Name : selectPrPromotionCategoryListByPromoNo
	 * @Date : 2019. 3. 14
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public List<PrPromotionTargetCategory> selectPrPromotionCategoryListByPromoNo(String promoNo) throws Exception;

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
