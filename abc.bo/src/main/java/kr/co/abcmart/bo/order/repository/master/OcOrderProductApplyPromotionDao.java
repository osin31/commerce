package kr.co.abcmart.bo.order.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.claim.vo.OcClaimDiscountVO;
import kr.co.abcmart.bo.order.model.master.OcOrderProductApplyPromotion;
import kr.co.abcmart.bo.order.repository.master.base.BaseOcOrderProductApplyPromotionDao;
import kr.co.abcmart.bo.order.vo.OcOrderDiscountVO;

@Mapper
public interface OcOrderProductApplyPromotionDao extends BaseOcOrderProductApplyPromotionDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcOrderProductApplyPromotionDao 클래스에 구현
	 * 되어있습니다. BaseOcOrderProductApplyPromotionDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는
	 * 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public OcOrderProductApplyPromotion selectByPrimaryKey(OcOrderProductApplyPromotion ocOrderProductApplyPromotion)
			throws Exception;

	/**
	 * @Desc : 주문 상세 프로모션 정보 조회
	 * @Method Name : selectOrderPromoList
	 * @Date : 2019. 2. 25.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderDiscount
	 * @return
	 */
	public List<OcOrderDiscountVO> selectOrderPromoList(OcOrderDiscountVO ocOrderDiscount) throws Exception;

	/**
	 * @Desc : 클레임 취소 프로모션 조회
	 * @Method Name : selectClaimCancelPromoList
	 * @Date : 2019. 3. 19.
	 * @Author : KTH
	 * @param ocClaimDiscountVO
	 * @return
	 * @throws Exception
	 */
	public List<OcOrderDiscountVO> selectClaimCancelPromoList(OcClaimDiscountVO ocClaimDiscountVO) throws Exception;

	/**
	 * @Desc : 주문상품 프로모션 클레임번호 업데이트
	 * @Method Name : updateOrderProductApplyPromotionClmNo
	 * @Date : 2019. 7. 30.
	 * @Author : KTH
	 * @param orderPromotion
	 * @throws Exception
	 */
	public void updateOrderProductApplyPromotionClmNo(OcOrderProductApplyPromotion ocOrderProductApplyPromotion)
			throws Exception;

	/**
	 * @Desc : 주문 상품 프로모션 정보 조회
	 * @Method Name : selectClaimCancelPromoList
	 * @Date : 2019. 3. 19.
	 * @Author : KTH
	 * @param OcOrderProductApplyPromotion
	 * @return
	 * @throws Exception
	 */
	public List<OcOrderProductApplyPromotion> selectOrderProductApplyPromotion(
			OcOrderProductApplyPromotion ocOrderProductApplyPromotion) throws Exception;
}
