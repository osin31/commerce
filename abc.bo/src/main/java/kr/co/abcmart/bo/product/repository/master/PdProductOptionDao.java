package kr.co.abcmart.bo.product.repository.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import kr.co.abcmart.bo.product.model.master.PdProductOption;
import kr.co.abcmart.bo.product.model.master.PdProductOptionStock;
import kr.co.abcmart.bo.product.model.master.PdProductOptionWithStockAndPrice;
import kr.co.abcmart.bo.product.repository.master.base.BasePdProductOptionDao;
import kr.co.abcmart.bo.product.vo.PdProductOptionStockSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface PdProductOptionDao extends BasePdProductOptionDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePdProductOptionDao 클래스에 구현 되어있습니다.
	 * BasePdProductOptionDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 */

	public PdProductOption selectByPrimaryKey(PdProductOption pdProductOption) throws Exception;

	/**
	 * @Desc : 상품번호와 관련된 상품옵션 조회
	 * @Method Name : selectByPrdtNo
	 * @Date : 2019. 3. 12.
	 * @Author : tennessee
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public List<PdProductOption> selectByPrdtNo(String prdtNo) throws Exception;

	/**
	 * @Desc : 상품 옵션 등록
	 * @Method Name : insertWithSelectKey
	 * @Date : 2019. 3. 13.
	 * @Author : tennessee
	 * @param productOption
	 * @throws Exception
	 */
	public void insertWithSelectKey(PdProductOption productOption) throws Exception;

	/**
	 * @Desc : 상품 옵션 및 재고 조회
	 * @Method Name : selectWithStocks
	 * @Date : 2019. 3. 20.
	 * @Author : tennessee
	 * @param productOptionStockSearchVO
	 * @throws Exception
	 */
	public List<PdProductOption> selectWithStocks(Pageable<PdProductOptionStockSearchVO, PdProductOption> pageable)
			throws Exception;

	/**
	 * @Desc : 해당 상품 옵션 전체 갯수 조회
	 * @Method Name : selectProductOptionTotalCount
	 * @Date : 2019. 3. 21.
	 * @Author : tennessee
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public Integer selectProductOptionTotalCount(Pageable<PdProductOptionStockSearchVO, PdProductOption> pageable)
			throws Exception;

	/**
	 * @Desc : 상품번호와 관련된 상품옵션 조회(사은품용)
	 * @Method Name : selectFreeGiftByPrdtNo
	 * @Date : 2019. 3. 28.
	 * @Author : hsjhsj19
	 * @param prdtNo
	 * @return
	 */
	public List<PdProductOption> selectFreeGiftByPrdtNo(String prdtNo) throws Exception;

	/**
	 * @Desc : 상품 옵션 조회 시 옵션별 재고와 추가금액 포함 조회
	 * @Method Name : selectWithStockAndAddAmt
	 * @Date : 2019. 4. 15.
	 * @Author : tennessee
	 * @param productOptionStockSearchVO
	 * @return
	 * @throws Exception
	 */
	public List<PdProductOption> selectWithStockAndAddAmt(PdProductOptionStockSearchVO productOptionStockSearchVO)
			throws Exception;

	/**
	 * @Desc : 상품옵션 목록 재고와 가격까지 함께 조회 (서비스 요청서)
	 * @Method Name : selectProductOptionListWithStockAndPrice
	 * @Date : 2019. 4. 22.
	 * @Author : hsjhsj19
	 * @param optionWithStockAndPrice
	 * @return
	 */
	public List<PdProductOptionWithStockAndPrice> selectProductOptionListWithStockAndPrice(
			PdProductOptionWithStockAndPrice optionWithStockAndPrice) throws Exception;

	/**
	 * @Desc : 상품옵션 목록 재고까지 함께 조회(서비스 요청서)
	 * @Method Name : selectProductOptionListWithStock
	 * @Date : 2019. 4. 22.
	 * @Author : hsjhsj19
	 * @param optionWithStockAndPrice
	 * @return
	 * @throws Exception
	 */
	public List<PdProductOptionWithStockAndPrice> selectProductOptionListWithStock(
			PdProductOptionWithStockAndPrice optionWithStockAndPrice) throws Exception;

	/**
	 * @Desc : 상품 재고 수량을 조회 한다.
	 * @Method Name : selectProductOptionStock
	 * @Date : 2019. 6. 20.
	 * @Author : kiowa
	 * @param productOptionStock
	 * @return
	 * @throws Exception
	 */
	public List<PdProductOptionStock> selectProductOptionStock(List<Map<String, Object>> params) throws Exception;

	/**
	 * @Desc : 상품 옵션 목록에 존재하지 않는 옵션 삭제
	 * @Method Name : deleteContraryOptions
	 * @Date : 2019. 8. 9.
	 * @Author : tennessee
	 * @param prdtNo
	 * @param productOption
	 * @throws Exception
	 */
	public void deleteContraryOptions(@Param("prdtNo") String prdtNo,
			@Param("productOption") List<PdProductOption> productOption) throws Exception;
}
