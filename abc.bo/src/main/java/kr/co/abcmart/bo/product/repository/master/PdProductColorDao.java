package kr.co.abcmart.bo.product.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.PdProductColor;
import kr.co.abcmart.bo.product.repository.master.base.BasePdProductColorDao;

@Mapper
public interface PdProductColorDao extends BasePdProductColorDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePdProductColorDao 클래스에 구현 되어있습니다.
	 * BasePdProductColorDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public PdProductColor selectByPrimaryKey(PdProductColor pdProductColor) throws Exception;

	/**
	 * @Desc : 왜래키를 사용한 상품색상 삭제
	 * @Method Name : deleteByForeignKeys
	 * @Date : 2019. 4. 3.
	 * @Author : tennessee
	 * @param productColor
	 * @return
	 * @throws Exception
	 */
	public Integer deleteByForeignKeys(PdProductColor productColor) throws Exception;

}
