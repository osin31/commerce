package kr.co.abcmart.bo.afterService.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.afterService.model.master.OcAsAccept;
import kr.co.abcmart.bo.afterService.model.master.OcAsAcceptProduct;
import kr.co.abcmart.bo.afterService.repository.master.base.BaseOcAsAcceptDao;
import kr.co.abcmart.bo.afterService.vo.OcAfterServiceExcelVo;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface OcAsAcceptDao extends BaseOcAsAcceptDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcAsAcceptDao 클래스에 구현 되어있습니다.
	 * BaseOcAsAcceptDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	/************ S: 이상호 ******************************/
	public OcAsAccept selectByPrimaryKey(OcAsAccept ocAsAccept) throws Exception;

	public int selectAfterServiceListCount(Pageable<OcAsAccept, OcAsAccept> pageable) throws Exception;

	public List<OcAsAccept> selectAfterServiceList(Pageable<OcAsAccept, OcAsAccept> pageable) throws Exception;

	public int selectAsAcceptNoNextVal() throws Exception;

	public OcAsAccept selectAfterServiceDetailInfo(OcAsAccept ocAsAccept) throws Exception;

	public String selectSiteNameInfo(String siteNo) throws Exception;

	public void inertOcAsAccept(OcAsAccept ocAsAccept);

	public void updateOcAsAccept(OcAsAccept ocAsAccept);

	public void updateOcAsAcceptTempory(OcAsAccept ocAsAccept);

	public List<OcAfterServiceExcelVo> selectOcAfterServiceForAllExcelList(OcAsAccept ocAsAccept) throws Exception;

	public List<OcAfterServiceExcelVo> selectOcAfterServiceForExcelList(OcAfterServiceExcelVo ocAfterServiceExcelVo)
			throws Exception;

	public int updateOcAsAcceptAddDlvyAmtMinus(OcAsAccept ocAsAccept) throws Exception;

	/*********** * E: 이상호 ****************************/

	public List<OcAsAccept> selectASListForOrgOrder(OcAsAccept ocAsAccept) throws Exception;

	/************ S: 이강수 ******************************/
	public List<OcAsAccept> selectAsAcceptListInOrderDetailTap(OcAsAccept ocAsAccept) throws Exception;

	public OcAsAcceptProduct selectAsPrdtDetailForInquiry(OcAsAccept ocAsAccept) throws Exception;

	public OcAsAccept selectOcAsAcceptDetailInfoBackend(OcAsAccept ocAsAccept) throws Exception;
	/************ E: 이강수 ******************************/
}
