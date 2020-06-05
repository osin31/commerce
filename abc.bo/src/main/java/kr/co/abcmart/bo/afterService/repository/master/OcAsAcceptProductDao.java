package kr.co.abcmart.bo.afterService.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.afterService.model.master.OcAsAcceptProduct;
import kr.co.abcmart.bo.afterService.repository.master.base.BaseOcAsAcceptProductDao;

@Mapper
public interface OcAsAcceptProductDao extends BaseOcAsAcceptProductDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcAsAcceptProductDao 클래스에 구현 되어있습니다.
	 * BaseOcAsAcceptProductDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public OcAsAcceptProduct selectByPrimaryKey(OcAsAcceptProduct ocAsAcceptProduct) throws Exception;

	public void inertOcAsAcceptProduct(OcAsAcceptProduct ocAsAcceptProduct);

	public void updateOcAsAcceptProduct(OcAsAcceptProduct ocAsAcceptProduct);

	public List<OcAsAcceptProduct> selectOrderAsArr(String[] orderNos) throws Exception;

	public OcAsAcceptProduct selectAsAcceptProductDetailInfoBackend(OcAsAcceptProduct ocAsAcceptProduct)
			throws Exception;

	/**
	 * @Desc : 원 주문에 걸린 모든 AS 목록
	 * @Method Name : selectOrgOrderAllAsProductList
	 * @Date : 2019. 9. 26.
	 * @Author : KTH
	 * @param ocAsAcceptProduct
	 * @return
	 * @throws Exception
	 */
	public List<OcAsAcceptProduct> selectOrgOrderAllAsProductList(OcAsAcceptProduct ocAsAcceptProduct) throws Exception;

	/**
	 * @Desc : 주문에 대한 as 상품 조회
	 * @Method Name : getOrderPrdtAsAccept
	 * @Date : 2019. 9. 27.
	 * @Author : flychani@3top.co.kr
	 * @param ocAsAcceptProduct
	 * @return
	 */
	public List<OcAsAcceptProduct> getOrderPrdtAsAccept(OcAsAcceptProduct ocAsAcceptProduct) throws Exception;

	/**
	 * @Desc : 회원번호로 AS목록 조회
	 * @Method Name : selectAsListByMemberNo
	 * @Date : 2020. 1. 29.
	 * @Author : 이강수
	 * @param ocAsAcceptProduct
	 * @return
	 */
	public List<OcAsAcceptProduct> selectAsListByMemberNo(OcAsAcceptProduct ocAsAcceptProduct) throws Exception;
}
