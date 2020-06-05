package kr.co.abcmart.bo.vendor.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.vendor.model.master.VdVendorDisplayChnnl;
import kr.co.abcmart.bo.vendor.repository.master.base.BaseVdVendorDisplayChnnlDao;

@Mapper
public interface VdVendorDisplayChnnlDao extends BaseVdVendorDisplayChnnlDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseVdVendorDisplayChnnlDao 클래스에 구현 되어있습니다.
	 * BaseVdVendorDisplayChnnlDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public VdVendorDisplayChnnl selectByPrimaryKey(VdVendorDisplayChnnl vdVendorDisplayChnnl) throws Exception;

	public int getVndrDispChnnlSeq(VdVendorDisplayChnnl chnnlParam) throws Exception;

	public void deleteDisplayChnnlByVndrNo(String vndrNo) throws Exception;

	public List<VdVendorDisplayChnnl> selectVendorDisplayChnnlList(VdVendorDisplayChnnl params);

}
