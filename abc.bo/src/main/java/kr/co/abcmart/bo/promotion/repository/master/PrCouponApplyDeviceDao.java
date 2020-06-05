package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.promotion.model.master.PrCouponApplyDevice;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrCouponApplyDeviceDao;

@Mapper
public interface PrCouponApplyDeviceDao extends BasePrCouponApplyDeviceDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrCouponApplyDeviceDao 클래스에 구현 되어있습니다.
	 * BasePrCouponApplyDeviceDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public PrCouponApplyDevice selectByPrimaryKey(PrCouponApplyDevice prCouponApplyDevice) throws Exception;

	/**
	 * 쿠폰 디바이스 삭제
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
	 * 쿠폰 디바이스 리스트 조회
	 * 
	 * @Desc :
	 * @Method Name : selectPrCouponDeivceListByCpnNo
	 * @Date : 2019. 3. 08
	 * @Author : easyhun
	 * @param cpnNo
	 * @return
	 */
	public List<PrCouponApplyDevice> selectPrCouponDeviceListByCpnNo(String cpnNo) throws Exception;

}
