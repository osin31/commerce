package kr.co.abcmart.bo.cmm.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.cmm.model.master.CmOnlineMemberPolicy;
import kr.co.abcmart.bo.cmm.repository.master.base.BaseCmOnlineMemberPolicyDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface CmOnlineMemberPolicyDao extends BaseCmOnlineMemberPolicyDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseCmOnlineMemberPolicyDao 클래스에 구현 되어있습니다.
	 * BaseCmOnlineMemberPolicyDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 *
	 */

	public CmOnlineMemberPolicy selectByPrimaryKey(CmOnlineMemberPolicy cmOnlineMemberPolicy) throws Exception;

	/**
	 *
	 * @Desc      	: 온라인 혜택 리스트 데이터 개수 조회
	 * @Method Name : selectOnlinePolicyListCount
	 * @Date  	  	: 2019. 3. 20.
	 * @Author    	: choi
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectOnlinePolicyListCount(Pageable<CmOnlineMemberPolicy, CmOnlineMemberPolicy> pageable)throws Exception;

	/**
	 *
	 * @Desc      	: 온라인 혜택 리스트 데이터 조회
	 * @Method Name : selectOnlinePolicyList
	 * @Date  	  	: 2019. 3. 20.
	 * @Author    	: choi
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<CmOnlineMemberPolicy> selectOnlinePolicyList(Pageable<CmOnlineMemberPolicy, CmOnlineMemberPolicy> pageable) throws Exception;

	/**
	 *
	 * @Desc      	: 온라인 혜택 기본 데이터 조회
	 * @Method Name : selectOnlinePolicyData
	 * @Date  	  	: 2019. 3. 20.
	 * @Author    	: choi
	 * @param cmOnlineMemberPolicy
	 * @return
	 * @throws Exception
	 */
	public CmOnlineMemberPolicy selectOnlinePolicyData(CmOnlineMemberPolicy cmOnlineMemberPolicy) throws Exception;

	/**
	 *
	 * @Desc      	: 온라인 혜택 상세 plcy_seq 조회
	 * @Method Name : selectOnlinePolicySeq
	 * @Date  	  	: 2019. 3. 20.
	 * @Author    	: choi
	 * @param cmOnlineMemberPolicy
	 * @return
	 * @throws Exception
	 */
	public List<String> selectOnlinePolicySeq(CmOnlineMemberPolicy cmOnlineMemberPolicy) throws Exception;

	/**
	 *
	 * @Desc      	: 온라인 혜택 상세 데이터 조회
	 * @Method Name : selectOnlinePolicyDtlData
	 * @Date  	  	: 2019. 3. 20.
	 * @Author    	: choi
	 * @param cmOnlineMemberPolicy
	 * @return
	 * @throws Exception
	 */
	public List<CmOnlineMemberPolicy> selectOnlinePolicyDtlData(CmOnlineMemberPolicy cmOnlineMemberPolicy) throws Exception;

	/**
	 *
	 * @Desc      	: 온라인 혜택 쿠폰 데이터 조회
	 * @Method Name : selectOnlinePolicyCouponData
	 * @Date  	  	: 2019. 3. 20.
	 * @Author    	: choi
	 * @param cmOnlineMemberPolicy
	 * @return
	 * @throws Exception
	 */
	public List<CmOnlineMemberPolicy> selectOnlinePolicyCouponData(CmOnlineMemberPolicy cmOnlineMemberPolicy) throws Exception;

	/**
	 *
	 * @Desc      	: 온라인 혜택 기본 데이터 등록
	 * @Method Name : insertOnlinePolicy
	 * @Date  	  	: 2019. 3. 20.
	 * @Author    	: choi
	 * @param cmOnlineMemberPolicy
	 * @throws Exception
	 */
	public void insertOnlinePolicy(CmOnlineMemberPolicy cmOnlineMemberPolicy) throws Exception;

	/**
	 *
	 * @Desc      	: 온라인 혜택 상세 데이터 등록
	 * @Method Name : insertOnlinePolicyDetail
	 * @Date  	  	: 2019. 3. 20.
	 * @Author    	: choi
	 * @param cmOnlineMemberPolicy
	 * @throws Exception
	 */
	public void insertOnlinePolicyDetail(CmOnlineMemberPolicy cmOnlineMemberPolicy) throws Exception;

	/**
	 *
	 * @Desc      	: 온라인 혜택 쿠폰 데이터 등록
	 * @Method Name : insertOnlinePolicyCoupon
	 * @Date  	  	: 2019. 3. 20.
	 * @Author    	: choi
	 * @param cmOnlineMemberPolicy
	 * @throws Exception
	 */
	public void insertOnlinePolicyCoupon(CmOnlineMemberPolicy cmOnlineMemberPolicy) throws Exception;

	/**
	 *
	 * @Desc      	: 온라인 정책 USE_YN=N 업데이트
	 * @Method Name : updateOnlinePolicyUseYn
	 * @Date  	  	: 2019. 3. 20.
	 * @Author    	: choi
	 * @param cmOnlineMemberPolicy
	 * @throws Exception
	 */
	public void updateOnlinePolicyUseYn(CmOnlineMemberPolicy cmOnlineMemberPolicy) throws Exception;

	/**
	 *
	 * @Desc      	: 온라인 정책 가져오기
	 * @Method Name : selectMembershipData
	 * @Date  	  	: 2019. 5. 08.
	 * @Author    	: choi
	 * @param selectMembershipData
	 * @throws Exception
	 */
	public CmOnlineMemberPolicy selectMembershipData() throws Exception;
}
