package kr.co.abcmart.bo.claim.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.claim.model.master.OcClaim;
import kr.co.abcmart.bo.claim.model.master.OcClaimPayment;
import kr.co.abcmart.bo.claim.repository.master.base.BaseOcClaimPaymentDao;
import kr.co.abcmart.bo.claim.vo.OcClaimPaymentExcelVo;
import kr.co.abcmart.common.paging.Pageable;

/**
 * @Desc : 클레임 결제 Dao
 * @FileName : OcClaimPaymentDao.java
 * @Project : abc.bo
 * @Date : 2019. 2. 18.
 * @Author : 김태호
 */
@Mapper
public interface OcClaimPaymentDao extends BaseOcClaimPaymentDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcClaimPaymentDao 클래스에 구현 되어있습니다.
	 * BaseOcClaimPaymentDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public OcClaimPayment selectByPrimaryKey(OcClaimPayment ocClaimPayment) throws Exception;

	/**
	 * @Desc : 클레임 결제 목록 조회 쿼리
	 * @Method Name : selectClaimPaymentList
	 * @Date : 2019. 2. 18.
	 * @Author : 김태호
	 * @param OcClaimPayment
	 * @return List<OcClaimPayment>
	 * @throws Exception
	 */
	public List<OcClaimPayment> selectClaimPaymentList(OcClaimPayment OcClaimPayment) throws Exception;

	/**
	 * @Desc : 환불/환수 리스트 목록 갯수 조회
	 * @Method Name : selectRefundListCount
	 * @Date : 2019. 3. 4.
	 * @Author : 이강수
	 * @param Pageable<OcClaimPayment, OcClaimPayment>
	 * @return int
	 * @throws Exception
	 */
	public int selectRefundRedempListCount(Pageable<OcClaimPayment, OcClaimPayment> pageable) throws Exception;

	/**
	 * @Desc : 환불/환수 리스트 목록 조회
	 * @Method Name : selectRefundRedempList
	 * @Date : 2019. 3. 4.
	 * @Author : 이강수
	 * @param Pageable<OcClaimPayment, OcClaimPayment>
	 * @return List<OcClaimPayment>
	 * @throws Exception
	 */
	public List<OcClaimPayment> selectRefundRedempList(Pageable<OcClaimPayment, OcClaimPayment> pageable)
			throws Exception;

	/**
	 * @Desc : 환불/환수 처리 업데이트
	 * @Method Name : updateRefundRedempProcess
	 * @Date : 2019. 3. 4.
	 * @Author : 이강수
	 * @param OcClaimPayment
	 * @throws Exception
	 */
	public void updateRefundRedempProcess(OcClaimPayment params) throws Exception;

	/**
	 * @Desc : 환불 입금완료 확인 처리
	 * @Method Name : updateRefundProcessComplete
	 * @Date : 2019. 8. 6.
	 * @Author : 이강수
	 * @param OcClaimPayment
	 * @throws Exception
	 */
	public void updateRefundProcessComplete(OcClaimPayment params) throws Exception;

	/**
	 * @Desc : 환불/환수 전체 목록 엑셀 조회
	 * @Method Name : selectRefundRedempListForAllExcel
	 * @Date : 2019. 3. 7.
	 * @Author : 이강수
	 * @param OcOrderPayment
	 * @return List<OcClaimPaymentExcelVo>
	 * @throws Exception
	 */
	public List<OcClaimPaymentExcelVo> selectRefundRedempListForAllExcel(OcClaimPayment params) throws Exception;

	/**
	 * @Desc : 환불/환수 선택 목록 엑셀 조회
	 * @Method Name : selectRefundRedempListForExcel
	 * @Date : 2019. 3. 7.
	 * @Author : 이강수
	 * @param OcClaimPaymentExcelVo
	 * @return List<OcClaimPaymentExcelVo>
	 * @throws Exception
	 */
	public List<OcClaimPaymentExcelVo> selectRefundRedempListForExcel(OcClaimPaymentExcelVo params) throws Exception;

	/**
	 * @Desc : 클레임결제 등록
	 * @Method Name : insertClaimPayment
	 * @Date : 2019. 3. 25.
	 * @Author : KTH
	 * @param ocClaimPayment
	 * @return
	 * @throws Exception
	 */
	public int insertClaimPayment(OcClaimPayment ocClaimPayment) throws Exception;

	/**
	 * @Desc : 가상계좌요청시 클레임결제 수정
	 * @Method Name : updateClaimPaymentRequestVirtualAccount
	 * @Date : 2019. 3. 25.
	 * @Author : KTH
	 * @param ocClaimPayment
	 * @return
	 * @throws Exception
	 */
	public int updateClaimPaymentRequestVirtualAccount(OcClaimPayment ocClaimPayment) throws Exception;

	/**
	 * @Desc : 클레임 취소가능 잔여금액 목록
	 * @Method Name : selectOrderClaimPaymentList
	 * @Date : 2019. 4. 16.
	 * @Author : KTH
	 * @param ocClaimPayment
	 * @return
	 * @throws Exception
	 */
	public List<OcClaimPayment> selectOrderClaimPaymentList(OcClaimPayment ocClaimPayment) throws Exception;

	/**
	 * @Desc : 환수/환불금액관리 목록 조회 쿼리 - 주문상세 클레임 탭
	 * @Method Name : selectClmPymntListInOrderTab
	 * @Date : 2019. 7. 19.
	 * @Author : 이강수
	 * @param OcClaimPayment
	 * @return List<OcClaimPayment>
	 * @throws Exception
	 */
	public List<OcClaimPayment> selectClmPymntListInOrderTab(OcClaimPayment OcClaimPayment) throws Exception;

	/**
	 * @Desc : 클레임 결제 목록 조회 쿼리 - backend
	 * @Method Name : selectClaimPaymentListBackend
	 * @Date : 2019. 7. 9.
	 * @Author : 이강수
	 * @param OcClaimPayment
	 * @return List<OcClaimPayment>
	 * @throws Exception
	 */
	public List<OcClaimPayment> selectClaimPaymentListBackend(OcClaimPayment OcClaimPayment) throws Exception;

	/**
	 * @Desc : 결제취소 상태 업데이트
	 * @Method Name : updateClaimPaymentForCancel
	 * @Date : 2019. 7. 23.
	 * @Author : KTH
	 * @param ocClaimPayment
	 * @return
	 * @throws Exception
	 */
	public int updateClaimPaymentForCancel(OcClaimPayment ocClaimPayment) throws Exception;

	/**
	 * @Desc : 원주문의 총 환수금액 목록 조회 쿼리
	 * @Method Name : selectOrgOrderRedempAmt
	 * @Date : 2019. 8. 7.
	 * @Author : 이강수
	 * @param OcClaim
	 * @return List<OcClaimPayment>
	 * @throws Exception
	 */
	public List<OcClaimPayment> selectOrgOrderRedempAmt(OcClaim ocClaim) throws Exception;

	/**
	 * @Desc : 원주문의 총 환불금액 목록 조회 쿼리
	 * @Method Name : selectOrgOrderRefundAmt
	 * @Date : 2019. 8. 7.
	 * @Author : 이강수
	 * @param OcClaim
	 * @return List<OcClaimPayment>
	 * @throws Exception
	 */
	public List<OcClaimPayment> selectOrgOrderRefundAmt(OcClaim ocClaim) throws Exception;

	/**
	 * @Desc : 클레임결제 삭제
	 * @Method Name : deleteClaimPayment
	 * @Date : 2019. 8. 11.
	 * @Author : KTH
	 * @param ocClaimPayment
	 * @return
	 * @throws Exception
	 */
	public int deleteClaimPayment(OcClaimPayment ocClaimPayment) throws Exception;

	/**
	 * @Desc : 환불계좌정보 업데이트
	 * @Method Name : updatePaymentRefundAccountInfo
	 * @Date : 2019. 9. 20.
	 * @Author : KTH
	 * @param ocClaimPayment
	 * @return
	 * @throws Exception
	 */
	public int updatePaymentRefundAccountInfo(OcClaimPayment ocClaimPayment) throws Exception;

	/**
	 * @Desc : 재경팀환불금 이중 등록 방지 카운트
	 * @Method Name : selectValidateRefundPymnt
	 * @Date : 2020. 3. 26.
	 * @Author : 이강수
	 * @param ocClaimPayment
	 * @return
	 * @throws Exception
	 */
	public int selectValidateRefundPymnt(OcClaimPayment ocClaimPayment) throws Exception;

}
