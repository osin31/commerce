package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpWebzineProduct;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpWebzineProductDao;

@Mapper
public interface DpWebzineProductDao extends BaseDpWebzineProductDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpWebzineProductDao 클래스에 구현 되어있습니다.
	 * BaseDpWebzineProductDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public DpWebzineProduct selectByPrimaryKey(DpWebzineProduct dpWebzineProduct) throws Exception;

	/**
	 * 
	 * @Desc : 웹진 연관상품 삭제
	 * @Method Name : deleteDpWebzineProduct
	 * @Date : 2019. 4. 15.
	 * @Author : SANTA
	 * @param dpWebzineProduct
	 * @return
	 * @throws Exception
	 */
	public int deleteDpWebzineProduct(DpWebzineProduct dpWebzineProduct) throws Exception;

	/**
	 * 
	 * @Desc : 웹진 연관상품 리스트 조회
	 * @Method Name : selectDpWebzineProductList
	 * @Date : 2019. 4. 15.
	 * @Author : SANTA
	 * @param dpWebzineProduct
	 * @return
	 * @throws Exception
	 */
	public List<DpWebzineProduct> selectDpWebzineProductList(DpWebzineProduct dpWebzineProduct) throws Exception;

}
