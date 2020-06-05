package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.PdProductDetail;
import kr.co.abcmart.bo.product.repository.master.base.BasePdProductDetailDao;

@Mapper
public interface PdProductDetailDao extends BasePdProductDetailDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePdProductDetailDao 클래스에 구현 되어있습니다.
	 * BasePdProductDetailDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 */

	public PdProductDetail selectByPrimaryKey(PdProductDetail pdProductDetail) throws Exception;

	/**
	 * @Desc : 상품 번호 기반 상세 정보 조회
	 * @Method Name : selectByPrdtNo
	 * @Date : 2019. 4. 17.
	 * @Author : tennessee
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public List<PdProductDetail> selectByPrdtNo(String prdtNo) throws Exception;

	/**
	 * @Desc : 상품번호로 상품상세 삭제
	 * @Method Name : deleteByPrdtNo
	 * @Date : 2019. 10. 29.
	 * @Author : hsjhsj19
	 * @param prdtNo
	 * @return
	 * @throws Exception
	 */
	public Integer deleteByPrdtNo(String prdtNo) throws Exception;

}
