package kr.co.abcmart.bo.vendor.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.board.model.master.BdContactUs;
import kr.co.abcmart.bo.board.model.master.BdContactUsMemo;
import kr.co.abcmart.bo.board.model.master.BdCorprBoard;
import kr.co.abcmart.bo.board.model.master.BdCorprBoardMemo;
import kr.co.abcmart.bo.stats.model.master.CurrentSaleStats;
import kr.co.abcmart.bo.stats.vo.OrderStatsSearchVO;
import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.bo.vendor.model.master.VdVendor;
import kr.co.abcmart.bo.vendor.repository.master.base.BaseVdVendorDao;
import kr.co.abcmart.bo.vendor.vo.VendorOtherPartVo;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;

@Mapper
public interface VdVendorDao extends BaseVdVendorDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseVdVendorDao 클래스에 구현 되어있습니다.
	 * BaseVdVendorDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public VdVendor selectByPrimaryKey(VdVendor vdVendor) throws Exception;

	public int selectVendorInfoListCount(Pageable<VdVendor, VdVendor> pageable) throws Exception;

	public List<VdVendor> selectVendorInfoList(Pageable<VdVendor, VdVendor> pageable) throws Exception;

	public String selectVndrNoNextVal() throws Exception;

	public List<VdVendor> getVendorBaseInfoList(VdVendor vdVendor) throws Exception;

	public List<BdContactUs> selectVendorInquiryList(Pageable<BdContactUs, BdContactUs> pageable) throws Exception;

	public int selectVendorInquiryListCount(Pageable<BdContactUs, BdContactUs> pageable) throws Exception;

	public BdContactUs selectVendorInquiryList(Parameter<BdContactUs> parameter) throws Exception;

	public BdContactUs selectVendorInquiryDetail(BdContactUs parameter) throws Exception;

	public int insertVendorAdminMemo(BdContactUsMemo bdContactUsMemo) throws Exception;

	public List<BdContactUsMemo> selectVendorInquiryMemoList(BdContactUsMemo bdContactUsMemo) throws Exception;

	public int updateVendorInquiryMemoToDelete(BdContactUsMemo bdContactUsMemo) throws Exception;

	public int updateVendorInquiry(BdContactUs bdContactUs) throws Exception;

	public void updateVendorCowork(BdCorprBoard bdCorprBoard) throws Exception;

	public int selectVendorCoworkListCount(Pageable<BdCorprBoard, BdCorprBoard> pageable) throws Exception;

	public List<BdCorprBoard> selectVendorCoworkList(Pageable<BdCorprBoard, BdCorprBoard> pageable) throws Exception;

	public BdCorprBoard selectVendorCoworkDetail(BdCorprBoard bdCorprBoard) throws Exception;

	public List<BdCorprBoardMemo> selectVendorCoworkMemoList(BdCorprBoardMemo bdCorprBoardMemo) throws Exception;

	public int insertVendorCoworkAdminMemo(BdCorprBoardMemo bdCorprBoardMemo) throws Exception;

	public int updateVendorCoworkMemoToDelete(BdCorprBoardMemo bdCorprBoardMemo) throws Exception;

	public int updateVendorCoworkReply(BdCorprBoard bdCorprBoard) throws Exception;

	public void updateVendorInquiryForDelete(BdContactUs bdContactUs) throws Exception;

	public int selectDealVendorCount() throws Exception;

	public List<SyAdmin> getAllVendorSendInfoList(String[] vndrNo) throws Exception;

	public List<SyAdmin> getRepVendorSendInfoList(String[] vndrNo) throws Exception;

	public List<BdCorprBoard> selectCorprGroupCount(String aswrStatCode) throws Exception;

	public int selectCorprGroupCountPo(BdCorprBoard bdCorprBoard) throws Exception;

	public List<BdContactUs> selectContactGroupCount() throws Exception;

	public String selectVndrStatCode(String vndrNo) throws Exception;

	public VendorOtherPartVo getVendorCmsnRateAndEmpDscntRate(String vndrNo, String stdCtgrNo, String brandNo)
			throws Exception;

	public int selectVendorListCounting(Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable) throws Exception;

	public List<VdVendor> selectVendorListPaging(Pageable<OrderStatsSearchVO, CurrentSaleStats> pageable)
			throws Exception;

	public void updateAsMngrText(VdVendor vdVendor) throws Exception;

	public VdVendor selectVendorAsMngrInfo(String vndrNo) throws Exception;

	public void updateVendorDlvyAmtByPolicy(VdVendor vdVendor) throws Exception;
}
