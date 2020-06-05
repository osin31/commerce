package kr.co.abcmart.bo.board.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.board.model.master.BdBulkBuyInquiry;
import kr.co.abcmart.bo.board.repository.master.base.BaseBdBulkBuyInquiryDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface BdBulkBuyInquiryDao extends BaseBdBulkBuyInquiryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseBdBulkBuyInquiryDao 클래스에 구현 되어있습니다.
	 * BaseBdBulkBuyInquiryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public BdBulkBuyInquiry selectByPrimaryKey(BdBulkBuyInquiry bdBulkBuyInquiry) throws Exception;

	/**
	 * @Desc : 대량구매 문의 그리드 조회결과 갯수
	 * @Method Name : selectBulkBuyistCount
	 * @Date : 2019. 3. 13.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectBulkBuyistCount(Pageable<BdBulkBuyInquiry, BdBulkBuyInquiry> pageable) throws Exception;

	/**
	 * @Desc : 단체주문문의 그리드 검색결과 조회
	 * @Method Name : selectBulkBuylList
	 * @Date : 2019. 3. 13.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<BdBulkBuyInquiry> selectBulkBuylList(Pageable<BdBulkBuyInquiry, BdBulkBuyInquiry> pageable)
			throws Exception;

	/**
	 * @Desc : 단체주문문의 상세보기
	 * @Method Name : selectBulkBuyDetail
	 * @Date : 2019. 3. 13.
	 * @Author : 신인철
	 * @param bdBulkBuyInquiry
	 * @return
	 * @throws Exception
	 */
	public BdBulkBuyInquiry selectBulkBuyDetail(BdBulkBuyInquiry bdBulkBuyInquiry) throws Exception;

	/**
	 * @Desc : 단체주문 문의 답변 등록
	 * @Method Name : updateBulkBuy
	 * @Date : 2019. 3. 13.
	 * @Author : 신인철
	 * @param bdBulkBuyInquiry
	 * @return
	 * @throws Exception
	 */
	public int updateBulkBuy(BdBulkBuyInquiry bdBulkBuyInquiry) throws Exception;

	/**
	 * @Desc : BO 대시보드 단체주문 그룹별 건수조회
	 * @Method Name : selectBulkBuyGroupCount
	 * @Date : 2019. 5. 8.
	 * @Author : 고웅환
	 * @param bdBulkBuyInquiry
	 * @return
	 * @throws Exception
	 */
	public List<BdBulkBuyInquiry> selectBulkBuyGroupCount() throws Exception;

}
