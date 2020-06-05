package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.PdProductMemo;
import kr.co.abcmart.bo.product.repository.master.base.BasePdProductMemoDao;

@Mapper
public interface PdProductMemoDao extends BasePdProductMemoDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePdProductMemoDao 클래스에 구현 되어있습니다.
	 * BasePdProductMemoDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 */

	public PdProductMemo selectByPrimaryKey(PdProductMemo pdProductMemo) throws Exception;

	/**
	 * @Desc : 상품번호에 대한 메모 조회
	 * @Method Name : selectByProduct
	 * @Date : 2019. 3. 7.
	 * @Author : tennessee
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public List<PdProductMemo> selectByProduct(String prdtNo) throws Exception;

	/**
	 * 상품 관리자 메모 저장
	 *
	 * @Desc :
	 * @Method Name : insertProductMemo
	 * @Date : 2019. 3. 7.
	 * @Author : SANTA
	 * @param pdProductMemo
	 * @throws Exception
	 */
	public void insertProductMemo(PdProductMemo pdProductMemo) throws Exception;

	/**
	 * @Desc : 상품번호로 상품메모 삭제
	 * @Method Name : deleteByPrdtNo
	 * @Date : 2019. 10. 29.
	 * @Author : hsjhsj19
	 * @param memo
	 * @return
	 * @throws Exception
	 */
	public Integer deleteByPrdtNo(String prdtNo) throws Exception;

}
