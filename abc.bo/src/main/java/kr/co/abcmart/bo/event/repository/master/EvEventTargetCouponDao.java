package kr.co.abcmart.bo.event.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.event.model.master.EvEventTargetCoupon;
import kr.co.abcmart.bo.event.repository.master.base.BaseEvEventTargetCouponDao;

@Mapper
public interface EvEventTargetCouponDao extends BaseEvEventTargetCouponDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseEvEventTargetCouponDao 클래스에 구현 되어있습니다.
	 * BaseEvEventTargetCouponDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public EvEventTargetCoupon selectByPrimaryKey(EvEventTargetCoupon evEventTargetCoupon) throws Exception;

	/**
	 * 이벤트 쿠폰 조회
	 * 
	 * @Desc : 이벤트 쿠폰 조회
	 * @Method Name : selectEvEventTargetCouponListByEventNo
	 * @Date : 2019. 4. 02
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventTargetCoupon> selectEvEventTargetCouponListByEventNo(String eventNo) throws Exception;

	/**
	 * 이벤트 쿠폰 삭제
	 * 
	 * @Desc : 이벤트 쿠폰 삭제
	 * @Method Name : deleteByEventNo
	 * @Date : 2019. 4. 5
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public void deleteByEventNo(String eventNo) throws Exception;

}
