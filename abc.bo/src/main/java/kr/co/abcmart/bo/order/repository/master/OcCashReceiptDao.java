package kr.co.abcmart.bo.order.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.order.model.master.OcCashReceipt;
import kr.co.abcmart.bo.order.repository.master.base.BaseOcCashReceiptDao;

@Mapper
public interface OcCashReceiptDao extends BaseOcCashReceiptDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcCashReceiptDao 클래스에 구현 되어있습니다.
	 * BaseOcCashReceiptDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public OcCashReceipt selectByPrimaryKey(OcCashReceipt ocCashReceipt) throws Exception;

	/**
	 * @Desc : 현금영수증 정보 insert
	 * @Method Name : insertCashReceipt
	 * @Date : 2019. 7. 12.
	 * @Author : KTH
	 * @param ocCashReceipt
	 * @return
	 * @throws Exception
	 */
	public void insertCashReceipt(OcCashReceipt ocCashReceipt) throws Exception;

	/**
	 * @Desc : 현금영수증 번호 조회
	 * @Method Name : getGiftCashReceipt
	 * @Date : 2019. 9. 18.
	 * @Author : flychani@3top.co.kr
	 * @param ocr
	 * @return
	 */
	public OcCashReceipt getGiftCashReceipt(OcCashReceipt ocr) throws Exception;
}
