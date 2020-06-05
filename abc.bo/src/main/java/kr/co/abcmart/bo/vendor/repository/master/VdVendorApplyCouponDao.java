package kr.co.abcmart.bo.vendor.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.vendor.model.master.VdVendorApplyCoupon;
import kr.co.abcmart.bo.vendor.repository.master.base.BaseVdVendorApplyCouponDao;

@Mapper
public interface VdVendorApplyCouponDao extends BaseVdVendorApplyCouponDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseVdVendorApplyCouponDao 클래스에 구현 되어있습니다.
	 * BaseVdVendorApplyCouponDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public VdVendorApplyCoupon selectByPrimaryKey(VdVendorApplyCoupon vdVendorApplyCoupon) throws Exception;

	public void deleteVdVendorApplyCouponByVndrNo(String vndrNo) throws Exception;

}
