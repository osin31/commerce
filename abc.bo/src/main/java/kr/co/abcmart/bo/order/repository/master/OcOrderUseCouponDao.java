package kr.co.abcmart.bo.order.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.claim.vo.OcClaimDiscountVO;
import kr.co.abcmart.bo.order.model.master.OcOrderUseCoupon;
import kr.co.abcmart.bo.order.repository.master.base.BaseOcOrderUseCouponDao;
import kr.co.abcmart.bo.order.vo.OcOrderDiscountVO;

@Mapper
public interface OcOrderUseCouponDao extends BaseOcOrderUseCouponDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcOrderUseCouponDao 클래스에 구현 되어있습니다.
	 * BaseOcOrderUseCouponDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public OcOrderUseCoupon selectByPrimaryKey(OcOrderUseCoupon ocOrderUseCoupon) throws Exception;

	/**
	 * @Desc :
	 * @Method Name : selectOrderCouponList
	 * @Date : 2019. 2. 25.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderDiscount
	 * @return
	 */
	public List<OcOrderDiscountVO> selectOrderCouponList(OcOrderDiscountVO ocOrderDiscount) throws Exception;

	/**
	 * @Desc : 클레임 취소 쿠폰 사용 목록 조회
	 * @Method Name : selectClaimCancelCouponList
	 * @Date : 2019. 3. 19.
	 * @Author : KTH
	 * @param ocClaimDiscountVO
	 * @return
	 * @throws Exception
	 */
	public List<OcOrderDiscountVO> selectClaimCancelCouponList(OcClaimDiscountVO ocClaimDiscountVO) throws Exception;

	/**
	 * @Desc : 클레임 처리용 쿠폰 사용 목록 조회
	 * @Method Name : selectOrderUserCouponForClaimList
	 * @Date : 2019. 4. 26.
	 * @Author : KTH
	 * @param ocOrderUseCoupon
	 * @return
	 * @throws Exception
	 */
	public List<OcOrderUseCoupon> selectOrderUserCouponForClaimList(OcOrderUseCoupon ocOrderUseCoupon) throws Exception;

	/**
	 * @Desc : 주문사용쿠폰 클레임번호 업데이트
	 * @Method Name : updateOrderUseCouponClmNo
	 * @Date : 2019. 7. 4.
	 * @Author : KTH
	 * @param ocOrderUseCoupon
	 * @return
	 * @throws Exception
	 */
	public int updateOrderUseCouponClmNo(OcOrderUseCoupon ocOrderUseCoupon) throws Exception;

}
