package kr.co.abcmart.bo.vendor.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.vendor.model.master.VdVendorBrand;
import kr.co.abcmart.bo.vendor.repository.master.base.BaseVdVendorBrandDao;

@Mapper
public interface VdVendorBrandDao extends BaseVdVendorBrandDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseVdVendorBrandDao 클래스에 구현 되어있습니다.
	 * BaseVdVendorBrandDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public VdVendorBrand selectByPrimaryKey(VdVendorBrand vdVendorBrand) throws Exception;

	public void deleteVendorBrandByVndrNo(VdVendorBrand vdVendorBrand) throws Exception;

}
