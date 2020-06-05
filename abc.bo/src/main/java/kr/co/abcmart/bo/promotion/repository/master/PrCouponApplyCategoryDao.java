package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.promotion.model.master.PrCouponApplyCategory;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrCouponApplyCategoryDao;

@Mapper
public interface PrCouponApplyCategoryDao extends BasePrCouponApplyCategoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrCouponApplyCategoryDao 클래스에 구현 되어있습니다.
	 * BasePrCouponApplyCategoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public PrCouponApplyCategory selectByPrimaryKey(PrCouponApplyCategory prCouponApplyCategory) throws Exception;

	/**
	 * 쿠폰 카테고리 삭제
	 * 
	 * @Desc :
	 * @Method Name : deleteByCpnNo
	 * @Date : 2019. 3. 07
	 * @Author : easyhun
	 * @param cpnNo
	 * @return
	 */
	public void deleteByCpnNo(String cpnNo) throws Exception;

	/**
	 * 쿠폰 카테고리 리스트 조회
	 * 
	 * @Desc :
	 * @Method Name : selectPrCouponApplyCategoryListByCpnNo
	 * @Date : 2019. 3. 08
	 * @Author : easyhun
	 * @param cpnNo
	 * @return
	 */
	public List<PrCouponApplyCategory> selectPrCouponApplyCategoryListByCpnNo(String cpnNo) throws Exception;
}
