package kr.co.abcmart.bo.cmm.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.cmm.model.master.CmOnlineMemberPolicy;
import kr.co.abcmart.bo.cmm.model.master.CmOnlineMemberPolicyDetail;
import kr.co.abcmart.bo.cmm.repository.master.CmOnlineMemberPolicyDao;
import kr.co.abcmart.bo.cmm.repository.master.CmOnlineMemberPolicyDetailDao;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsDate;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OnlinePolicyService {

	@Autowired
	CmOnlineMemberPolicyDao cmOnlineMemberPolicyDao;

	@Autowired
	CmOnlineMemberPolicyDetailDao cmOnlineMemberPolicyDetailDao;

	/**
	 *
	 * @Desc      	: 온라인 혜택 리스트 데이터 조회
	 * @Method Name : getOnlinePolicyList
	 * @Date  	  	: 2019. 2. 7.
	 * @Author    	: choi
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<CmOnlineMemberPolicy> getOnlinePolicyList(Pageable<CmOnlineMemberPolicy, CmOnlineMemberPolicy> pageable) throws Exception {
		int count = cmOnlineMemberPolicyDao.selectOnlinePolicyListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(cmOnlineMemberPolicyDao.selectOnlinePolicyList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 *
	 * @Desc      	: 온라인 혜택 기본, 혜택, 보상 데이터 조회
	 * @Method Name : getOnlinePolicyData
	 * @Date  	  	: 2019. 2. 7.
	 * @Author    	: choi
	 * @param cmOnlineMemberPolicy
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getOnlinePolicyData(CmOnlineMemberPolicy cmOnlineMemberPolicy) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		// 온라인 기본정보 데이터(1)
		result.put("DATA", cmOnlineMemberPolicyDao.selectOnlinePolicyData(cmOnlineMemberPolicy));
		
		// 온라인 회원 가입 쿠폰 데이터
		List<CmOnlineMemberPolicy> onlineCouponList = cmOnlineMemberPolicyDao.selectOnlinePolicyDtlData(cmOnlineMemberPolicy);
		result.put("ONLINE_COUPON_LIST", UtilsText.stringify(onlineCouponList));
		
		// 통합 회원 가입 쿠폰 데이터
		List<CmOnlineMemberPolicy> membershipCouponList = cmOnlineMemberPolicyDao.selectOnlinePolicyDtlData(cmOnlineMemberPolicy);
		result.put("MEMBERSHIP_COUPON_LIST",  UtilsText.stringify(membershipCouponList));

		// 온라인 혜택 PLCY_DTL_SEQ
		result.put("PLCY_SEQ", cmOnlineMemberPolicyDao.selectOnlinePolicySeq(cmOnlineMemberPolicy));

		// 온라인 품절 보상 데이터(1,2,3)
		List<CmOnlineMemberPolicy> listCmOnlineMemberPolicy = cmOnlineMemberPolicyDao.selectOnlinePolicyDtlData(cmOnlineMemberPolicy);
		if(listCmOnlineMemberPolicy.size() > 0) {
			result.put("SOLDOUT_DATA_ONE", listCmOnlineMemberPolicy.get(0));
		}
		result.put("SOLDOUT_DATA", listCmOnlineMemberPolicy);

		return result;

	}

	/**
	 *
	 * @Desc      	: 온라인 혜택 쿠폰 데이터 조회
	 * @Method Name : getOnlinePolicyCpnData
	 * @Date  	  	: 2019. 2. 7.
	 * @Author    	: choi
	 * @param cmOnlineMemberPolicy
	 * @return
	 * @throws Exception
	 */
	public List<CmOnlineMemberPolicy> getOnlinePolicyCpnData(CmOnlineMemberPolicy cmOnlineMemberPolicy) throws Exception {
		return cmOnlineMemberPolicyDao.selectOnlinePolicyCouponData(cmOnlineMemberPolicy);
	}

	/**
	 *
	 * @Desc      	: 온라인 혜택 상세 데이터 조회
	 * @Method Name : getOnlinePolicyBenefitData
	 * @Date  	  	: 2019. 2. 7.
	 * @Author    	: choi
	 * @param cmOnlineMemberPolicyDetail
	 * @return
	 * @throws Exception
	 */
	public CmOnlineMemberPolicyDetail getOnlinePolicyBenefitData(CmOnlineMemberPolicyDetail cmOnlineMemberPolicyDetail) throws Exception {
		CmOnlineMemberPolicyDetail policyParam = new CmOnlineMemberPolicyDetail();

		// TODO Auto-generated method stub
		policyParam.setPlcySeq(cmOnlineMemberPolicyDetail.getPlcySeq());
		policyParam.setPlcyDtlSeq(cmOnlineMemberPolicyDetail.getPlcyDtlSeq());

		List<CmOnlineMemberPolicyDetail> list = cmOnlineMemberPolicyDetailDao.select(policyParam);
		if(list.size() > 0) {
			cmOnlineMemberPolicyDetail = list.get(0);
		}else {
			cmOnlineMemberPolicyDetail = new CmOnlineMemberPolicyDetail();
		}

		return cmOnlineMemberPolicyDetail;
	}

	/**
	 *
	 * @Desc      	: 온라인 혜택 기본정보, 상세 정보, 혜택/품절 보상 정보 등록
	 * @Method Name : setOnlinePolicyCreate
	 * @Date  	  	: 2019. 2. 7.
	 * @Author    	: choi
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setOnlinePolicyCreate(Parameter<CmOnlineMemberPolicy> parameter) throws Exception {
		UserDetails user 			= LoginManager.getUserDetails();
		Map<String, Object> result 	= new HashMap<>(); // 결과
		int plcyDtlSeq = 0;	// 정책상세 순번의 인데스(loop가 혜택, 보상 각각 돌면서 겹칠 수 있으므로 변수로 선언)

		CmOnlineMemberPolicy cmOnlineMemberPolicy = parameter.get();
		cmOnlineMemberPolicy.setRgsterNo(user.getAdminNo());
		cmOnlineMemberPolicy.setModerNo(user.getAdminNo());

		// 날짜 세팅
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date date 	  = df.parse(UtilsDate.formatter(Const.DEFAULT_DATE_PATTERN_NOT_CHARACTERS, (cmOnlineMemberPolicy.getPlcyApplyYmd())));

        // 날짜 더하기
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);

        cmOnlineMemberPolicy.setEndDate(df.format(cal.getTime()));
		cmOnlineMemberPolicyDao.updateOnlinePolicyUseYn(cmOnlineMemberPolicy);	// 시행되고 있거나 시행될 정책 날짜 종료

		// 온라인 혜택 기본정보 등록
		cmOnlineMemberPolicyDao.insertOnlinePolicy(cmOnlineMemberPolicy);
		result.put("plcySeq", cmOnlineMemberPolicy.getPlcySeq());	// 키 세팅

		// S:혜택 쿠폰정보 등록
		int benefitIndex = parameter.getInt("benefitIndex");
		for(int i=1; i<=benefitIndex; i++) {	// loop s
			String cpnNoData  = parameter.getString("cpnNoArr_"+i);		// 혜택 영역의 쿠폰 정보[1000|2000|3000]
			String payQtyData = parameter.getString("payQtyArr_"+i);	// 혜택 영역의 쿠폰 발급 개수 정보[1|2|3]

			if(cpnNoData != null) {	// 영역의 인덱스 데이터가 있다면 혜택 데이터 정보들이 있는 것임
				int buyQty = parameter.getInt("buyQty_"+i, 0);
				int buyAmt = parameter.getInt("buyAmt_"+i, 0);

				cmOnlineMemberPolicy.setPlcyDtlSeq(++plcyDtlSeq);
				cmOnlineMemberPolicy.setBuyAmt(buyAmt);
				cmOnlineMemberPolicy.setBuyQty(buyQty);

				cmOnlineMemberPolicyDao.insertOnlinePolicyDetail(cmOnlineMemberPolicy);

				// 상세 쿠폰 정보 등록
				String[] cpnNoArr  = cpnNoData.split("\\|");
				String[] payQtyArr = payQtyData.split("\\|");

				for(int j=0; j<cpnNoArr.length; j++) {
					cmOnlineMemberPolicy.setCpnNo(cpnNoArr[j]);
					cmOnlineMemberPolicy.setPayQty(Integer.parseInt(payQtyArr[j]));
					cmOnlineMemberPolicyDao.insertOnlinePolicyCoupon(cmOnlineMemberPolicy);
				}	// cpnNoArr loop end
			}	// cpnNoData if end
		}	// benefitIndex loop end
		// E:혜택 쿠폰정보 등록
		return result;
	}

	public CmOnlineMemberPolicy getMembershipData() throws Exception {
		CmOnlineMemberPolicy  cmOnlineMemberPolicy = cmOnlineMemberPolicyDao.selectMembershipData();
		return cmOnlineMemberPolicy;
	}

}
