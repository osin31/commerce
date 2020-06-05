package kr.co.abcmart.bo.vendor.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.vendor.model.master.VdVendorExceptionCommission;
import kr.co.abcmart.bo.vendor.repository.master.base.BaseVdVendorExceptionCommissionDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface VdVendorExceptionCommissionDao extends BaseVdVendorExceptionCommissionDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseVdVendorExceptionCommissionDao 클래스에 구현
	 * 되어있습니다. BaseVdVendorExceptionCommissionDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당
	 * 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public VdVendorExceptionCommission selectByPrimaryKey(VdVendorExceptionCommission vdVendorExceptionCommission)
			throws Exception;

	public int selectVndrExceptCmsnSeq(VdVendorExceptionCommission commissionParam) throws Exception;

	public int updateVendorExceptionCommissionDelYn(VdVendorExceptionCommission commissionParam) throws Exception;

	public List<VdVendorExceptionCommission> selectVendorExceptionCommission(
			VdVendorExceptionCommission vdVendorExceptionCommission) throws Exception;

	public int selectVendorExceptionCommissionHistoryListCount(
			Pageable<VdVendorExceptionCommission, VdVendorExceptionCommission> pageable) throws Exception;

	public List<VdVendorExceptionCommission> selectVendorExceptionCommissionHistoryList(
			Pageable<VdVendorExceptionCommission, VdVendorExceptionCommission> pageable) throws Exception;

	public VdVendorExceptionCommission selectExceptionCommissionData(
			VdVendorExceptionCommission vdVendorExceptionCommission) throws Exception;

}
