package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.PdFreeGift;
import kr.co.abcmart.bo.promotion.model.master.PrPromotion;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrPromotionDao;
import kr.co.abcmart.bo.promotion.vo.PrPromotionSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface PrPromotionDao extends BasePrPromotionDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrPromotionDao 클래스에 구현 되어있습니다.
	 * BasePrPromotionDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public PrPromotion selectByPrimaryKey(PrPromotion prPromotion) throws Exception;

	/**
	 * 프로모션 리스트 조회
	 * 
	 * @Desc : 프로모션 리스트 조회
	 * @Method Name : selectPrPromotionList
	 * @Date : 2019. 3. 13
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public List<PrPromotion> selectPrPromotionList(Pageable<PrPromotionSearchVO, PrPromotion> pageable)
			throws Exception;

	/**
	 * 프로모션 리스트 카운트 조회
	 * 
	 * @Desc :
	 * @Method Name : selectPrPromotionCount
	 * @Date : 2019. 3. 13
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public int selectPrPromotionCount(Pageable<PrPromotionSearchVO, PrPromotion> pageable) throws Exception;

	/**
	 * 프로모션 상세 조회
	 * 
	 * @Desc :
	 * @Method Name : selectPrPromotion
	 * @Date : 2019. 3. 13
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public PrPromotion selectPrPromotion(PrPromotion prPromotion) throws Exception;

	/**
	 * 내부 관리번호 count 조회
	 * 
	 * @Desc :
	 * @Method Name : selectPromotionDuplCheck
	 * @Date : 2019. 3. 15
	 * @Author : easyhun
	 * @param
	 * @return
	 */
	public int selectPromotionDuplCheck(String insdMgmtInfoText) throws Exception;

	/**
	 * 프로모션 등록
	 * 
	 * @Desc :
	 * @Method Name : insertPrPromotion
	 * @Date : 2019. 3. 15
	 * @Author : easyhun
	 * @param
	 * @return
	 */
	public void insertPrPromotion(PrPromotion prPromotion) throws Exception;

	/**
	 * 프로모션 현황 총 주문, 금액 조회
	 * 
	 * @Desc : 프로모션 현황 총 주문, 금액 조회
	 * @Method Name : getPromotionTotalStatus
	 * @Date : 2019. 3. 19
	 * @Author : easyhun
	 * @param promoNo
	 * @return PrPromotion
	 */
	public PrPromotion selectPromotionTotalStatus(String promoNo) throws Exception;

	/**
	 * 
	 * @Desc : 프로모션 현황 총 주문, 금액 조회 엑셀
	 * @Method Name : selectPromotionTotalStatus
	 * @Date : 2019. 12. 18.
	 * @Author : NKB
	 * @param promoNo
	 * @return
	 * @throws Exception
	 */
	public List<PrPromotion> selectPromotionStatusListExcel(Pageable<PrPromotionSearchVO, PrPromotion> pageable)
			throws Exception;

	/**
	 * 프로모션 현황 리스트 조회
	 * 
	 * @Desc : 프로모션 현황 리스트 조회
	 * @Method Name : selectPrPromotionList
	 * @Date : 2019. 3. 20
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public List<PrPromotion> selectPromotionStatusList(Pageable<PrPromotionSearchVO, PrPromotion> pageable)
			throws Exception;

	/**
	 * 프로모션 현황 카운트 조회
	 * 
	 * @Desc : 프로모션 현황 카운트 조회
	 * @Method Name : selectPrPromotionCount
	 * @Date : 2019. 3. 20
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public int selectPromotionStatusCount(Pageable<PrPromotionSearchVO, PrPromotion> pageable) throws Exception;

	/**
	 * @Desc : 대상 삼품 갯수 조회
	 * @Method Name : selectPromotionProductCount
	 * @Date : 2019. 4. 25.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 */
	public int selectPromotionProductCount(Pageable<PrPromotionSearchVO, PrPromotion> pageable) throws Exception;

	/**
	 * @Desc : 대상 삼품 목록 조회
	 * @Method Name : selectPromotionProductList
	 * @Date : 2019. 4. 25.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 */
	public List<PrPromotion> selectPromotionProductList(Pageable<PrPromotionSearchVO, PrPromotion> pageable)
			throws Exception;

	/**
	 * @Desc : 대상상품 사은품 목록
	 * @Method Name : selectTargetProductFreeGift
	 * @Date : 2019. 4. 25.
	 * @Author : hsjhsj19
	 * @param freeGift
	 * @return
	 * @throws Exception
	 */
	public List<PdFreeGift> selectTargetProductFreeGift(PdFreeGift freeGift) throws Exception;

	/**
	 * 
	 * @Desc : 상품번호에 의한 프로모션 조회
	 * @Method Name : selectPrdtNoByPromoList
	 * @Date : 2019. 9. 10
	 * @Author : easyhun
	 * @param prdtNo
	 * @throws Exception
	 */
	public List<PrPromotion> selectPrdtNoByPromoList(String prdtNo) throws Exception;

	/**
	 * @Desc : 상품번호에 의한 프로모션 조회
	 * @Method Name : selectPromotionProduct
	 * @Date : 2019. 10. 4.
	 * @Author : hsjhsj19
	 * @param prPromotion
	 * @return
	 * @throws Exception
	 */
	public PrPromotion selectPromotionProduct(PrPromotion prPromotion) throws Exception;

}
