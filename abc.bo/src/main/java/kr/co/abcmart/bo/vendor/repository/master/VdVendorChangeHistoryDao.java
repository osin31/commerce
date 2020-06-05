package kr.co.abcmart.bo.vendor.repository.master;

import org.apache.ibatis.annotations.Mapper;
import kr.co.abcmart.bo.vendor.repository.master.base.BaseVdVendorChangeHistoryDao;
import kr.co.abcmart.bo.vendor.model.master.VdVendorChangeHistory;

@Mapper
public interface VdVendorChangeHistoryDao extends BaseVdVendorChangeHistoryDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BaseVdVendorChangeHistoryDao 클래스에 구현 되어있습니다.
     * BaseVdVendorChangeHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
     */

	public VdVendorChangeHistory selectByPrimaryKey(VdVendorChangeHistory vdVendorChangeHistory) throws Exception;

}
