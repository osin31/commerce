package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import kr.co.abcmart.bo.product.model.master.PdProductPriceHistory;
import kr.co.abcmart.bo.product.repository.master.base.BasePdProductPriceHistoryDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface PdProductPriceHistoryDao extends BasePdProductPriceHistoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePdProductPriceHistoryDao 클래스에 구현 되어있습니다.
	 * BasePdProductPriceHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 *
	 */

	public PdProductPriceHistory selectByPrimaryKey(PdProductPriceHistory pdProductPriceHistory) throws Exception;

	/**
	 * @Desc : 상품 가격 이력 조회
	 * @Method Name : selectByPrdtNo
	 * @Date : 2019. 3. 15.
	 * @Author : tennessee
	 * @param pdProductPriceHistory
	 * @return
	 * @throws Exception
	 */
//	public List<PdProductPriceHistory> selectByPrdtNo(PdProductPriceHistory pdProductPriceHistory) throws Exception;

	/**
	 * @Desc : 가격이력 중 상품번호에 대한 최근 가격정보를 반환
	 * @Method Name : selectApplyingPriceByPrdtNo
	 * @Date : 2019. 3. 5.
	 * @Author : tennessee
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public PdProductPriceHistory selectApplyingPriceByPrdtNo(@Param("prdtNo") String prdtNo,
			@Param("chnnlNo") String chnnlNo) throws Exception;

	/**
	 * @Desc : 상품 가격 이력 등록
	 * @Method Name : insertWithSelectKey
	 * @Date : 2019. 3. 13.
	 * @Author : tennessee
	 * @param productPriceHistory
	 * @throws Exception
	 */
	public void insertWithSelectKey(PdProductPriceHistory productPriceHistory) throws Exception;

	/**
	 * @Desc : 상품 가격 이력 조회
	 * @Method Name : selectProdutPriceHistory
	 * @Date : 2019. 4. 22.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public List<PdProductPriceHistory> selectByPrdtNo(Pageable<PdProductPriceHistory, PdProductPriceHistory> criteria)
			throws Exception;

	/**
	 * @Desc : 상품 가격 이력 전체 갯수 조회
	 * @Method Name : selectProductPriceHistoryTotalCount
	 * @Date : 2019. 4. 22.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public Integer selectByPrdtNoTotalCount(Pageable<PdProductPriceHistory, PdProductPriceHistory> criteria)
			throws Exception;

	/**
	 * @Desc : 상품번호로 상품가격이력 삭제
	 * @Method Name : deleteByPrdtNo
	 * @Date : 2019. 10. 29.
	 * @Author : hsjhsj19
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public Integer deleteByPrdtNo(String prdtNo) throws Exception;

}
