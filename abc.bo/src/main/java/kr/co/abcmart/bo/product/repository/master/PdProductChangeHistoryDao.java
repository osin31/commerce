package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.PdProductChangeHistory;
import kr.co.abcmart.bo.product.repository.master.base.BasePdProductChangeHistoryDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface PdProductChangeHistoryDao extends BasePdProductChangeHistoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePdProductChangeHistoryDao 클래스에 구현 되어있습니다.
	 * BasePdProductChangeHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 *
	 */

	public PdProductChangeHistory selectByPrimaryKey(PdProductChangeHistory pdProductChangeHistory) throws Exception;

	/**
	 * @Desc : 상품변경이력 등록
	 * @Method Name : insertWithSelectKey
	 * @Date : 2019. 3. 13.
	 * @Author : tennessee
	 * @param productChangeHistory
	 * @throws Exception
	 */
	public void insertWithSelectKey(PdProductChangeHistory productChangeHistory) throws Exception;

	/**
	 * @Desc : 상품 변경 이력 조회
	 * @Method Name : selectByPrdtNo
	 * @Date : 2019. 4. 22.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public List<PdProductChangeHistory> selectByPrdtNo(
			Pageable<PdProductChangeHistory, PdProductChangeHistory> criteria) throws Exception;

	/**
	 * @Desc : 상품 변경 이력 총 갯수 조회
	 * @Method Name : selectByPrdtNoTotalCount
	 * @Date : 2019. 4. 22.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public Integer selectByPrdtNoTotalCount(Pageable<PdProductChangeHistory, PdProductChangeHistory> criteria)
			throws Exception;

	/**
	 * @Desc : 상품번호로 상품변경이력 삭제
	 * @Method Name : deleteByPrdtNo
	 * @Date : 2019. 10. 29.
	 * @Author : hsjhsj19
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public Integer deleteByPrdtNo(String prdtNo) throws Exception;

}
