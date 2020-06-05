package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.PdProductApprovalHistory;
import kr.co.abcmart.bo.product.repository.master.base.BasePdProductApprovalHistoryDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface PdProductApprovalHistoryDao extends BasePdProductApprovalHistoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePdProductApprovalHistoryDao 클래스에 구현
	 * 되어있습니다. BasePdProductApprovalHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당
	 * 소스에서 작업 하시면 됩니다.
	 *
	 */

	public PdProductApprovalHistory selectByPrimaryKey(PdProductApprovalHistory pdProductApprovalHistory)
			throws Exception;

	/**
	 * @Desc : 상품 승인 이력 등록
	 * @Method Name : insertWithSelectKey
	 * @Date : 2019. 3. 13.
	 * @Author : tennessee
	 * @param productApprovalHistory
	 * @throws Exception
	 */
	public void insertWithSelectKey(PdProductApprovalHistory productApprovalHistory) throws Exception;

	/**
	 * @Desc : 상품 번호를 통한 승인 이력 역순 조회
	 * @Method Name : selectWithDescentOrder
	 * @Date : 2019. 4. 18.
	 * @Author : tennessee
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public List<PdProductApprovalHistory> selectWithDescentOrder(String prdtNo) throws Exception;

	/**
	 * @Desc : 한 상품에 대한 이력 조회
	 * @Method Name : selectApprovalHistoryByPrdtNo
	 * @Date : 2019. 4. 23.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public List<PdProductApprovalHistory> selectApprovalHistoryByPrdtNo(
			Pageable<PdProductApprovalHistory, PdProductApprovalHistory> criteria) throws Exception;

	/**
	 * @Desc : 한 상품에 대한 이력 전체 갯수 조회
	 * @Method Name : selectApprovalHistoryByPrdtNoTotalCount
	 * @Date : 2019. 4. 23.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public Integer selectApprovalHistoryByPrdtNoTotalCount(
			Pageable<PdProductApprovalHistory, PdProductApprovalHistory> criteria) throws Exception;

	public PdProductApprovalHistory selectApplyingApprovalByPrdtNo(String prdtNo) throws Exception;

	/**
	 * @Desc : 상품번호로 상품승인이력 삭제
	 * @Method Name : deleteByPrdtNo
	 * @Date : 2019. 10. 29.
	 * @Author : hsjhsj19
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public Integer deleteByPrdtNo(String prdtNo) throws Exception;

}
