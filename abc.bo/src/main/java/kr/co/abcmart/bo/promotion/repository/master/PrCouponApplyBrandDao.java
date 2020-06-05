package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.promotion.model.master.PrCouponApplyBrand;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrCouponApplyBrandDao;

@Mapper
public interface PrCouponApplyBrandDao extends BasePrCouponApplyBrandDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrCouponApplyBrandDao 클래스에 구현 되어있습니다.
	 * BasePrCouponApplyBrandDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public PrCouponApplyBrand selectByPrimaryKey(PrCouponApplyBrand prCouponApplyBrand) throws Exception;

	/**
	 * 쿠폰 브랜드 삭제
	 * 
	 * @Desc : 쿠폰 브랜드 삭제
	 * @Method Name : deleteByCpnNo
	 * @Date : 2019. 4. 17
	 * @Author : easyhun
	 * @param cpnNo
	 * @throws Exception
	 */
	public void deleteByCpnNo(String cpnNo) throws Exception;

	/**
	 * 쿠폰 관리 브랜드 조회
	 * 
	 * @Desc : 쿠폰 관리 브랜드 조회
	 * @Method Name : selectPrCouponApplyBrandListByCpnNo
	 * @Date : 2019. 4. 17
	 * @Author : easyhun
	 * @param cpnNo
	 * @return
	 * @throws Exception
	 */
	public List<PrCouponApplyBrand> selectPrCouponApplyBrandListByCpnNo(String cpnNo) throws Exception;

}
