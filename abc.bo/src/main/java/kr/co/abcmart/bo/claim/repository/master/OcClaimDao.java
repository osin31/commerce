package kr.co.abcmart.bo.claim.repository.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.claim.model.master.OcClaim;
import kr.co.abcmart.bo.claim.model.master.OcClaimProduct;
import kr.co.abcmart.bo.claim.repository.master.base.BaseOcClaimDao;
import kr.co.abcmart.bo.claim.vo.OcClaimCountVO;
import kr.co.abcmart.common.paging.Pageable;

/**
 * @Desc : 클레임 Dao
 * @FileName : OcClaimDao.java
 * @Project : abc.bo
 * @Date : 2019. 2. 22.
 * @Author : KTH
 */
@Mapper
public interface OcClaimDao extends BaseOcClaimDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcClaimDao 클래스에 구현 되어있습니다. BaseOcClaimDao는
	 * 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public OcClaim selectByPrimaryKey(OcClaim ocClaim) throws Exception;

	/**
	 * @Desc : 클레임 목록 갯수 조회
	 * @Method Name : selectClaimListCount
	 * @Date : 2019. 3. 4.
	 * @Author : 이강수
	 * @param Pageable<OcClaim, OcClaim>
	 * @return int
	 * @throws Exception
	 */
	public int selectClaimListCount(Pageable<OcClaim, OcClaim> pageable) throws Exception;

	/**
	 * @Desc : 클레임 목록 조회
	 * @Method Name : selectClaimList
	 * @Date : 2019. 3. 4.
	 * @Author : 이강수
	 * @param Pageable<OcClaim, OcClaim>
	 * @return List<OcClaim>
	 * @throws Exception
	 */
	public List<OcClaim> selectClaimList(Pageable<OcClaim, OcClaim> pageable) throws Exception;

	/**
	 * @Desc : 클레임 정보 조회
	 * @Method Name : selectClaimBasisInfo
	 * @Date : 2019. 2. 22.
	 * @Author : KTH
	 * @param OcClaim
	 * @return OcClaim
	 * @throws Exception
	 */
	public OcClaim selectClaimBasisInfo(OcClaim ocClaim) throws Exception;

	/**
	 * @Desc : 클레임 내역 목록 조회
	 * @Method Name : selectClaimBasisList
	 * @Date : 2019. 3. 4.
	 * @Author : 이강수
	 * @param OcClaim
	 * @return List<OcClaim>
	 * @throws Exception
	 */
	public List<OcClaim> selectClaimBasisList(OcClaim ocClaim) throws Exception;

	/**
	 * @Desc : 클레임 등록
	 * @Method Name : insertClaimInfo
	 * @Date : 2019. 3. 8.
	 * @Author : KTH
	 * @param ocClaim
	 * @return
	 * @throws Exception
	 */
	public int insertClaimInfo(OcClaim ocClaim) throws Exception;

	/**
	 * @Desc : 클레임 미처리표시 상태 업데이트]
	 * @Method Name : updateClaimUnProcYn
	 * @Date : 2019. 3. 31.
	 * @Author : KTH
	 * @param ocClaim
	 * @return
	 * @throws Exception
	 */
	public int updateClaimUnProcYn(OcClaim ocClaim) throws Exception;

	/**
	 * @Desc : 클레임 접수철회 업데이트
	 * @Method Name : updateClaimWthdraw
	 * @Date : 2019. 4. 1.
	 * @Author : KTH
	 * @param ocClaim
	 * @return
	 * @throws Exception
	 */
	public int updateClaimWthdraw(OcClaim ocClaim) throws Exception;

	/**
	 * @Desc : 클레임 배송비정보 업데이트
	 * @Method Name : updateClaimDeliveryAmtInfo
	 * @Date : 2019. 4. 3.
	 * @Author : KTH
	 * @param ocClaim
	 * @return
	 * @throws Exception
	 */
	public int updateClaimDeliveryAmtInfo(OcClaim ocClaim) throws Exception;

	/**
	 * @Desc : 클레임 주소정보 업데이트
	 * @Method Name : updateClaimAddrInfo
	 * @Date : 2019. 4. 3.
	 * @Author : KTH
	 * @param ocClaim
	 * @return
	 * @throws Exception
	 */
	public int updateClaimAddrInfo(OcClaim ocClaim) throws Exception;

	/**
	 * @Desc : 클레임 점간이동 여부 업데이트
	 * @Method Name : updateClaimBranchMoveCode
	 * @Date : 2019. 4. 3.
	 * @Author : KTH
	 * @param ocClaim
	 * @return
	 * @throws Exception
	 */
	public int updateClaimBranchMoveCode(OcClaim ocClaim) throws Exception;

	/**
	 * @Desc : 클레임 상태코드 업데이트
	 * @Method Name : updateClaimStatCode
	 * @Date : 2019. 4. 5.
	 * @Author : KTH
	 * @param ocClaim
	 * @return
	 * @throws Exception
	 */
	public int updateClaimStatCode(OcClaim ocClaim) throws Exception;

	/**
	 * @Desc : 클레임 주문금액합계/배송비합계 조회
	 * @Method Name : selectSumOrderAmtSumDlvyAmt
	 * @Date : 2019. 7. 9.
	 * @Author : 이강수
	 * @param OcClaimProduct
	 * @return OcClaim
	 * @throws Exception
	 */
	public OcClaim selectSumOrderAmtSumDlvyAmt(OcClaimProduct ocClaimProduct) throws Exception;

	/**
	 * @Desc : 클레임 상세조회 - backend
	 * @Method Name : selectClaimDetail
	 * @Date : 2019. 7. 9.
	 * @Author : 이강수
	 * @param OcClaim
	 * @return OcClaim
	 * @throws Exception
	 */
	public OcClaim selectClaimDetail(OcClaim ocClaim) throws Exception;

	/**
	 * @Desc : 클레임 금액/상태 업데이트
	 * @Method Name : updateClaimStat
	 * @Date : 2019. 6. 20.
	 * @Author : KTH
	 * @param ocClaim
	 * @return
	 * @throws Exception
	 */
	public int updateClaimStat(OcClaim ocClaim) throws Exception;

	/**
	 * @Desc : 환불계좌정보 업데이트
	 * @Method Name : updateRefundAccount
	 * @Date : 2019. 7. 18.
	 * @Author : KTH
	 * @param ocClaim
	 * @return
	 * @throws Exception
	 */
	public int updateRefundAccount(OcClaim ocClaim) throws Exception;

	/**
	 * @Desc : BO 대쉬보드 - 클레임 건수 조회
	 * @Method Name : selectClaimCountForDashborad
	 * @Date : 2019. 7. 18.
	 * @Author : 이강수
	 * @param OcClaimCountVO
	 * @return OcClaimCountVO
	 * @throws Exception
	 */
	public OcClaimCountVO selectClaimCountForDashborad(OcClaimCountVO ocClaimCountVO) throws Exception;

	/**
	 * @Desc : 클레임배송비 관련 업데이트
	 * @Method Name : updateClaimAddDlvy
	 * @Date : 2019. 8. 4.
	 * @Author : KTH
	 * @param ocClaim
	 * @return
	 * @throws Exception
	 */
	public int updateClaimAddDlvy(OcClaim ocClaim) throws Exception;

	/**
	 * @Desc : 동봉 배송비 업데이트
	 * @Method Name : updateEclsDlvyAmt
	 * @Date : 2020. 2. 3.
	 * @Author : 이강수
	 * @param ocClaim
	 * @return
	 * @throws Exception
	 */
	public int updateEclsDlvyAmt(OcClaim ocClaim) throws Exception;

	/**
	 * @Desc : 클레임 완료 처리시 SALES 생성 프로시져 호출 / 성공 : "0"
	 * @Method Name : callProcedureForSalesClaim
	 * @Date : 2020. 2. 11.
	 * @Author : 이강수
	 * @param Map<String, String> map
	 * @return
	 * @throws Exception
	 */
	public void callProcedureForSalesClaim(Map<String, String> map) throws Exception;

	/**
	 * @Desc : 교환매출 프로시져 호출 / 성공 : "0"
	 * @Method Name : callProcedureForSalesExchange
	 * @Date : 2020. 2. 17.
	 * @Author : 이강수
	 * @param Map<String, Object> map
	 * @return
	 * @throws Exception
	 */
	public void callProcedureForSalesExchange(Map<String, String> exchangeParamMap) throws Exception;

	/**
	 * @Desc : 반품 클레임 금액정보 업데이트
	 * @Method Name : updateReturnClaimAmtInfo
	 * @Date : 2020. 2. 28.
	 * @Author : 이강수
	 * @param ocClaim
	 * @return
	 * @throws Exception
	 */
	public int updateReturnClaimAmtInfo(OcClaim ocClaim) throws Exception;

}
