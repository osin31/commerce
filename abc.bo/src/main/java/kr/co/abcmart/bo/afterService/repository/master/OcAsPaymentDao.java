package kr.co.abcmart.bo.afterService.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.afterService.model.master.OcAsPayment;
import kr.co.abcmart.bo.afterService.repository.master.base.BaseOcAsPaymentDao;
import kr.co.abcmart.bo.afterService.vo.OcAfterServiceAmountExcelVo;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface OcAsPaymentDao extends BaseOcAsPaymentDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcAsPaymentDao 클래스에 구현 되어있습니다.
	 * BaseOcAsPaymentDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public OcAsPayment selectByPrimaryKey(OcAsPayment ocAsPayment) throws Exception;

	public int selectAfterServiceAmtListCount(Pageable<OcAsPayment, OcAsPayment> pageable) throws Exception;

	public List<OcAsPayment> selectAfterServiceAmtList(Pageable<OcAsPayment, OcAsPayment> pageable) throws Exception;

	public OcAsPayment selectDetailPayInfo(OcAsPayment ocAsPayment) throws Exception;

	public List<OcAfterServiceAmountExcelVo> selectOcAfterServiceAmountForAllExcelList(OcAsPayment ocAsPayment)
			throws Exception;

	public List<OcAfterServiceAmountExcelVo> selectOcAfterServiceAmountForExcelList(
			OcAfterServiceAmountExcelVo ocAfterServiceAmountExcelVo) throws Exception;

	public int updateOcAsPaymentAmtCancel(OcAsPayment ocAsPayment) throws Exception;

	public OcAsPayment selectAsPymntDetailInfoBackend(OcAsPayment ocAsPayment) throws Exception;
}
