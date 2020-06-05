package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import kr.co.abcmart.bo.product.model.master.PdProductOption;
import kr.co.abcmart.bo.product.model.master.PdProductOptionPriceHistory;
import kr.co.abcmart.bo.product.repository.master.base.BasePdProductOptionPriceHistoryDao;

@Mapper
public interface PdProductOptionPriceHistoryDao extends BasePdProductOptionPriceHistoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePdProductOptionPriceHistoryDao 클래스에 구현
	 * 되어있습니다. BasePdProductOptionPriceHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당
	 * 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public PdProductOptionPriceHistory selectByPrimaryKey(PdProductOptionPriceHistory pdProductOptionPriceHistory)
			throws Exception;

	/**
	 * @Desc : 상품 옵션 가격 이력 등록
	 * @Method Name : insertWithSelectKey
	 * @Date : 2019. 3. 13.
	 * @Author : tennessee
	 * @param productOptionPriceHistory
	 * @throws Exception
	 */
	public void insertWithSelectKey(PdProductOptionPriceHistory productOptionPriceHistory) throws Exception;

	/**
	 * @Desc : 왜래키를 사용한 상품옵션가격이력 삭제
	 * @Method Name : deleteByPrdtNoAndPrdOptnNo
	 * @Date : 2019. 4. 3.
	 * @Author : tennessee
	 * @param productOptionPriceHistory
	 * @return
	 * @throws Exception
	 */
	public Integer deleteByForeignKeys(PdProductOptionPriceHistory productOptionPriceHistory) throws Exception;

	/**
	 * @Desc : 최근 가격 정보 조회
	 * @Method Name : selectRecentPrice
	 * @Date : 2019. 5. 2.
	 * @Author : tennessee
	 * @param prdtNo
	 * @param prdtOptnNo
	 * @return
	 * @throws Exception
	 */
	public PdProductOptionPriceHistory selectRecentPrice(@Param("prdtNo") String prdtNo,
			@Param("prdtOptnNo") String prdtOptnNo) throws Exception;

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
