package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.promotion.model.master.PrPromotionTargetProduct;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrPromotionTargetProductDao;

@Mapper
public interface PrPromotionTargetProductDao extends BasePrPromotionTargetProductDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrPromotionTargetProductDao 클래스에 구현
	 * 되어있습니다. BasePrPromotionTargetProductDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당
	 * 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public PrPromotionTargetProduct selectByPrimaryKey(PrPromotionTargetProduct prPromotionTargetProduct)
			throws Exception;

	/**
	 * 프로모션 대상 상품 삭제
	 * 
	 * @Desc : 프로모션 대상 상품 삭제
	 * @Method Name : deleteByPromoNo
	 * @Date : 2019. 4. 23
	 * @Author : easyhun
	 * @param promoNo
	 * @throws Exception
	 */
	public void deleteByPromoNo(String promoNo) throws Exception;

	/**
	 * 프로모션 대상 상품 삭제
	 * 
	 * @Desc : 프로모션 대상 상품 삭제
	 * @Method Name : deleteTargetProduct
	 * @Date : 2019. 4. 23
	 * @Author : easyhun
	 * @param promoNo
	 * @throws Exception
	 */
	public void deleteTargetProduct(String promoNo, String prdtNo) throws Exception;

	/**
	 * 프로모션 대상 상품 등록
	 * 
	 * @Desc : 프로모션 대상 상품 등록
	 * @Method Name : insertPrPromotionTargetProduct
	 * @Date : 2019. 4. 23
	 * @Author : easyhun
	 * @param prPromotionTargetProduct
	 * @throws Exception
	 */
	public void insertPrPromotionTargetProduct(PrPromotionTargetProduct prPromotionTargetProduct) throws Exception;

	/**
	 * 프로모션 대상 상품 수정
	 * 
	 * @Desc : 프로모션 대상 상품 수정
	 * @Method Name : updatePrPromotionTargetProduct
	 * @Date : 2019. 4. 23
	 * @Author : easyhun
	 * @param prPromotionTargetProduct
	 * @throws Exception
	 */
	public void updatePrPromotionTargetProduct(PrPromotionTargetProduct prPromotionTargetProduct) throws Exception;

	/**
	 * 상품 중복 프로모션 조회
	 * 
	 * @Desc : 상품 중복 프로모션 조회
	 * @Method Name : selectPromotionDuplProduct
	 * @Date : 2019. 5. 29
	 * @Author : easyhun
	 * @param prdtNo
	 * @throws Exception
	 */
	public String selectPromotionDuplProduct(Map<String, String> map) throws Exception;

	/**
	 * 상품 프로모션 조회
	 * 
	 * @Desc : 상품 프로모션 조회
	 * @Method Name : selectPromotionPrdtNoByPromoNo
	 * @Date : 2019. 10. 10
	 * @Author : easyhun
	 * @param promoNo
	 * @throws Exception
	 */
	public List<String> selectPromotionPrdtNoByPromoNo(String promoNo) throws Exception;

	/**
	 * 프로모션 다족, 타임특가 중복 체크
	 * 
	 * @Desc : 프로모션 다족, 타임특가 중복 체크
	 * @Method Name : selectPromotionPrdtNoByPromoNo
	 * @Date : 2019. 10. 22
	 * @Author : easyhun
	 * @param promoNo
	 * @throws Exception
	 */
	public List<PrPromotionTargetProduct> selectDuplPrdtNo(PrPromotionTargetProduct prPromotionTargetProduct)
			throws Exception;

}
