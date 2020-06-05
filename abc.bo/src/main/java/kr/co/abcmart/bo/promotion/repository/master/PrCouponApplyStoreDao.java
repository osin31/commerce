package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.promotion.model.master.PrCouponApplyStore;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrCouponApplyStoreDao;

@Mapper
public interface PrCouponApplyStoreDao extends BasePrCouponApplyStoreDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrCouponApplyStoreDao 클래스에 구현 되어있습니다.
	 * BasePrCouponApplyStoreDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public PrCouponApplyStore selectByPrimaryKey(PrCouponApplyStore prCouponApplyStore) throws Exception;

	/**
	 * 매장 삭제
	 * 
	 * @Desc : 매장 삭제
	 * @Method Name : deleteByCpnNo
	 * @Date : 2019. 4. 15
	 * @Author : easyhun
	 * @param cpnNo
	 * @throws Exception
	 */
	public void deleteByCpnNo(String cpnNo) throws Exception;

	/**
	 * 쿠폰 관리 매장 조회
	 * 
	 * @Desc : 쿠폰 관리 매장 조회
	 * @Method Name : selectPrCouponApplyStoreListByCpnNo
	 * @Date : 2019. 4. 17
	 * @Author : easyhun
	 * @param cpnNo
	 * @return
	 * @throws Exception
	 */
	public List<PrCouponApplyStore> selectPrCouponApplyStoreListByCpnNo(String cpnNo) throws Exception;

}
