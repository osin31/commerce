package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.BdProductInquiryMemo;
import kr.co.abcmart.bo.product.repository.master.base.BaseBdProductInquiryMemoDao;

@Mapper
public interface BdProductInquiryMemoDao extends BaseBdProductInquiryMemoDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseBdProductInquiryMemoDao 클래스에 구현 되어있습니다.
	 * BaseBdProductInquiryMemoDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public BdProductInquiryMemo selectByPrimaryKey(BdProductInquiryMemo bdProductInquiryMemo) throws Exception;

	/**
	 * @Desc : 상품문의 메모 저장(순서 자동 채번)
	 * @Method Name : insertWithoutPrdtInqryMemoSeq
	 * @Date : 2019. 4. 10.
	 * @Author : hsjhsj19
	 * @param bdProductInquiryMemo
	 * @return
	 */
	public int insertWithoutPrdtInqryMemoSeq(BdProductInquiryMemo bdProductInquiryMemo) throws Exception;

	/**
	 * @Desc : 상품문의 메모 목록 조회
	 * @Method Name : searchProductInquiryMemo
	 * @Date : 2019. 4. 10.
	 * @Author : hsjhsj19
	 * @param bdProductInquiryMemo
	 * @return
	 * @throws Exception
	 */
	public List<BdProductInquiryMemo> searchProductInquiryMemo(BdProductInquiryMemo bdProductInquiryMemo)
			throws Exception;

}
