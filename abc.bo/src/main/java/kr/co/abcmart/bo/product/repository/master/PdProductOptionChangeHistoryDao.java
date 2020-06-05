package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import kr.co.abcmart.bo.product.model.master.PdProductOption;
import kr.co.abcmart.bo.product.model.master.PdProductOptionChangeHistory;
import kr.co.abcmart.bo.product.repository.master.base.BasePdProductOptionChangeHistoryDao;

@Mapper
public interface PdProductOptionChangeHistoryDao extends BasePdProductOptionChangeHistoryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePdProductOptionChangeHistoryDao 클래스에 구현
	 * 되어있습니다. BasePdProductOptionChangeHistoryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는
	 * 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public PdProductOptionChangeHistory selectByPrimaryKey(PdProductOptionChangeHistory pdProductOptionChangeHistory)
			throws Exception;

	/**
	 * @Desc : 상품 옵션 변경 이력 등록
	 * @Method Name : insertWithSelectKey
	 * @Date : 2019. 3. 13.
	 * @Author : tennessee
	 * @param productOptionChangeHistory
	 * @throws Exception
	 */
	public void insertWithSelectKey(PdProductOptionChangeHistory productOptionChangeHistory) throws Exception;

	/**
	 * 
	 * @Desc : 왜래키에 의한 상품옵션변경이력 삭제
	 * @Method Name : deleteByForeignKeys
	 * @Date : 2019. 4. 3.
	 * @Author : tennessee
	 * @param productOptionChangeHistory
	 * @throws Exception
	 */
	public void deleteByForeignKeys(PdProductOptionChangeHistory productOptionChangeHistory) throws Exception;

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
