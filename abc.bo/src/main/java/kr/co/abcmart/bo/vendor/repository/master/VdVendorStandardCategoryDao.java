package kr.co.abcmart.bo.vendor.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.vendor.model.master.VdVendorStandardCategory;
import kr.co.abcmart.bo.vendor.repository.master.base.BaseVdVendorStandardCategoryDao;

@Mapper
public interface VdVendorStandardCategoryDao extends BaseVdVendorStandardCategoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseVdVendorStandardCategoryDao 클래스에 구현
	 * 되어있습니다. BaseVdVendorStandardCategoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당
	 * 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public VdVendorStandardCategory selectByPrimaryKey(VdVendorStandardCategory vdVendorStandardCategory)
			throws Exception;

	public void deleteVendorStandardCategoryByVndrNo(VdVendorStandardCategory vdVendorStandardCategory)
			throws Exception;

}
