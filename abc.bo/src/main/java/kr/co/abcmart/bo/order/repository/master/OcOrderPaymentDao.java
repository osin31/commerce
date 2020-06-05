package kr.co.abcmart.bo.order.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.order.model.master.OcOrderPayment;
import kr.co.abcmart.bo.order.repository.master.base.BaseOcOrderPaymentDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface OcOrderPaymentDao extends BaseOcOrderPaymentDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcOrderPaymentDao 클래스에 구현 되어있습니다.
	 * BaseOcOrderPaymentDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public OcOrderPayment selectByPrimaryKey(OcOrderPayment ocOrderPayment) throws Exception;

	/**
	 * @Desc : 주문 결제 정보 조회
	 * @Method Name : selectPaymentList
	 * @Date : 2019. 2. 15.
	 * @Author : flychani@3top.co.kr
	 * @param OcOrderPayment
	 * @return List<OcOrderPayment>
	 */
	public List<OcOrderPayment> selectPaymentList(OcOrderPayment ocOrderPayment) throws Exception;

	/**
	 * 
	 * @Desc : 결제 실패 목록 조회
	 * @Method Name : selectPaymentFailList
	 * @Date : 2019. 2. 19.
	 * @Author : ljyoung@3top.co.kr
	 * @return
	 * @throws Exception
	 */
	public List<OcOrderPayment> selectPaymentFailList(Pageable<OcOrderPayment, OcOrderPayment> pageable)
			throws Exception;

	/**
	 * @Desc : 결제 실패 목록 갯수
	 * @Method Name : selectPaymentFailListCount
	 * @Date : 2019. 2. 19.
	 * @Author : ljyoung@3top.co.kr
	 * @param pageable
	 * @return
	 */
	public int selectPaymentFailListCount(Pageable<OcOrderPayment, OcOrderPayment> pageable) throws Exception;

	/**
	 * @Desc : 결제수단 변경 가능 변경 처리
	 * @Method Name : updateOrderPaymentChange
	 * @Date : 2019. 3. 3.
	 * @Author : flychani@3top.co.kr
	 * @param ocOrderPayment
	 * @return
	 */
	public int updateOrderPaymentChange(OcOrderPayment ocOrderPayment) throws Exception;

	/**
	 * 
	 * @Desc : 대표카드 판변값 (없으면 원카드번호 전달)
	 * @Method Name : selectByPrimaryKey
	 * @Date : 2019. 5. 23.
	 * @Author : NKB
	 * @param ocOrderPayment
	 * @return
	 * @throws Exception
	 */
	public OcOrderPayment selectByReturnRprsntCard(OcOrderPayment ocOrderPayment) throws Exception;

	/**
	 * @Desc : 결제수단 변경을 위한 조건 검색
	 * @Method Name : selectPaymentInfo
	 * @Date : 2019. 7. 25.
	 * @Author : lee
	 * @param ocOrderPayment
	 * @return
	 * @throws Exception
	 */
	public OcOrderPayment selectPaymentInfo(OcOrderPayment ocOrderPayment) throws Exception;

}
