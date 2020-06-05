package kr.co.abcmart.bo.vendor.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.vendor.model.master.VdVendorChargeMd;
import kr.co.abcmart.bo.vendor.repository.master.base.BaseVdVendorChargeMdDao;

@Mapper
public interface VdVendorChargeMdDao extends BaseVdVendorChargeMdDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseVdVendorChargeMdDao 클래스에 구현 되어있습니다.
	 * BaseVdVendorChargeMdDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public VdVendorChargeMd selectByPrimaryKey(VdVendorChargeMd vdVendorChargeMd) throws Exception;

	public int getVndrChargeMdSeqNextVal(VdVendorChargeMd chargeMdParam) throws Exception;

	public void deleteVendorChargeMdByVndrNo(String vndrNo) throws Exception;

}
