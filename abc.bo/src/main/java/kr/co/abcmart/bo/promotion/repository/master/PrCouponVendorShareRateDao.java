package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.abcmart.bo.promotion.model.master.PrCouponVendorShareRate;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrCouponVendorShareRateDao;

@Mapper
public interface PrCouponVendorShareRateDao extends BasePrCouponVendorShareRateDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrCouponVendorShareRateDao 클래스에 구현 되어있습니다.
	 * BasePrCouponVendorShareRateDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 *
	 */

	public PrCouponVendorShareRate selectByPrimaryKey(PrCouponVendorShareRate prCouponVendorShareRate) throws Exception;

	/**
	 * 쿠폰 입점사 삭제
	 *
	 * @Desc :
	 * @Method Name : deleteByCpnNo
	 * @Date : 2019. 3. 04
	 * @Author : easyhun
	 * @param cpnNo
	 * @return
	 */
	public void deleteByCpnNo(String cpnNo) throws Exception;

	/**
	 * 쿠폰 입점사 조회
	 *
	 * @Desc :
	 * @Method Name : selectPrCouponVendorShareRateListByCpnNo
	 * @Date : 2019. 3. 08
	 * @Author : easyhun
	 * @param cpnNo
	 * @return
	 */
	public List<PrCouponVendorShareRate> selectPrCouponVendorShareRateListByCpnNo(String cpnNo) throws Exception;

	/**
	 * @Desc : 쿠폰 상품이 없는 업체 정보 삭제 처리
	 * @Method Name : deleteNotCouponProductVendor
	 * @Date : 2020. 2. 17.
	 * @Author : kiowa
	 * @param cpnNo
	 * @return
	 * @throws Exception
	 */
	public int deleteNotCouponProductVendor(String cpnNo) throws Exception;

	/**
	 * 쿠폰
	 *
	 * @Desc : 쿠폰 참여 업체 정보 조회
	 * @Method Name : selectCouponVendorList
	 * @Date : 2020. 2. 17.
	 * @Author : kiowa
	 * @param cpnNo
	 * @return
	 * @throws Exception
	 */
	public List<String> selectCouponProductVendorList(String cpnNo) throws Exception;

	/**
	 * @Desc : 추가된 입점 업체 정보 및 기존 입점 업체 정보를 조회한다.
	 * @Method Name : selectVendorCouponShareRateList
	 * @Date : 2020. 2. 18.
	 * @Author : kiowa
	 * @param cpnNo
	 * @param vndrNoList
	 * @return
	 * @throws Exception
	 */
	public List<PrCouponVendorShareRate> selectVendorCouponShareRateList(@Param("cpnNo") String cpnNo,
			@Param("vndrNoList") List<String> vndrNoList) throws Exception;

}
