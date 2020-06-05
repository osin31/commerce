package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.abcmart.bo.promotion.model.master.PrCouponApplyProduct;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrCouponApplyProductDao;

@Mapper
public interface PrCouponApplyProductDao extends BasePrCouponApplyProductDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrCouponApplyProductDao 클래스에 구현 되어있습니다.
	 * BasePrCouponApplyProductDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 *
	 */

	public PrCouponApplyProduct selectByPrimaryKey(PrCouponApplyProduct prCouponApplyProduct) throws Exception;

	/**
	 * 쿠폰 상품 삭제
	 *
	 * @Desc : 쿠폰 상품 삭제
	 * @Method Name : deleteByCpnNo
	 * @Date : 2019. 4. 16
	 * @Author : easyhun
	 * @param cpnNo
	 * @throws Exception
	 */
	public void deleteByCpnNo(String cpnNo) throws Exception;

	/**
	 * 쿠폰 관리 상품 조회
	 *
	 * @Desc : 쿠폰 관리 상품 조회
	 * @Method Name : selectPrCouponApplyProductListByCpnNo
	 * @Date : 2019. 4. 16
	 * @Author : easyhun
	 * @param cpnNo
	 * @return
	 * @throws Exception
	 */
	public List<PrCouponApplyProduct> selectPrCouponApplyProductListByCpnNo(String cpnNo) throws Exception;

	/**
	 * @Desc : 선택한 쿠폰 적용 상품을 삭제한다.
	 * @Method Name : deleteCouponProduct
	 * @Date : 2020. 2. 17.
	 * @Author : kiowa
	 * @param cpnNo
	 * @param couponProductList
	 * @return
	 * @throws Exception
	 */
	public int deleteCouponProduct(@Param("cpnNo") String cpnNo,
			@Param("couponProductList") PrCouponApplyProduct[] couponProductList) throws Exception;

	/**
	 * @Desc : 선택한 쿠폰 적용 상품을 삭제한다.
	 * @Method Name : deleteCouponProduct
	 * @Date : 2020. 2. 17.
	 * @Author : kiowa
	 * @param cpnNo
	 * @param couponProductList
	 * @return
	 * @throws Exception
	 */
	public int deleteCouponProductList(@Param("cpnNo") String cpnNo,
			@Param("couponProductList") String[] couponProductList) throws Exception;

}
