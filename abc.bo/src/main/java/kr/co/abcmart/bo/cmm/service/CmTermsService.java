package kr.co.abcmart.bo.cmm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.cmm.model.master.CmTerms;
import kr.co.abcmart.bo.cmm.model.master.CmTermsDetail;
import kr.co.abcmart.bo.cmm.repository.master.CmTermsDao;
import kr.co.abcmart.bo.cmm.repository.master.CmTermsDetailAddInfoDao;
import kr.co.abcmart.bo.cmm.repository.master.CmTermsDetailDao;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;

@Service
public class CmTermsService {
	@Autowired
	CommonCodeService commoncodeService;
	@Autowired
	CmTermsService cmTermsService;
	@Autowired
	CmTermsDao cmTermsDao;
	@Autowired
	CmTermsDetailDao cmTermsDetailDao;
	@Autowired
	CmTermsDetailAddInfoDao cmTermsDetailAddInfoDao;

	/**
	 * @Desc : 약관설정 메인페이지 호출
	 * @Method Name : getTermsPage
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getTermsPage() throws Exception {
		Map<String, Object> termsSettingMap = new HashMap<>();

		Map<String, Object> termsCodeMap = cmTermsService.getTermsListMap();

		termsSettingMap.put("termsTypeCode", termsCodeMap.get("codeTypeList"));
		termsSettingMap.put("termsDtlCode", termsCodeMap.get("termsOfUseList"));
		termsSettingMap.put("privacyCodeList", termsCodeMap.get("privacyCodeList"));
		termsSettingMap.put("signUpCodeList", termsCodeMap.get("signUpCodeList"));
		termsSettingMap.put("orderCodeList", termsCodeMap.get("orderCodeList"));

		return termsSettingMap;
	}

	/**
	 * @Desc : 이용약관 그리드 호출
	 * @Method Name : getTermsGrid
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<CmTerms> getTermsGrid(Pageable<CmTerms, CmTerms> pageable) throws Exception {
		pageable.setTotalCount(cmTermsDao.selectTermsGridCount(pageable));
		pageable.setContent(cmTermsDao.selectTermsGrid(pageable));

		return pageable.getPage();

	}

	/**
	 * @Desc : 이용약관, 개인정보 취급방침 등록
	 * @Method Name : insertTermsOfUse
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param cmTerms
	 * @param cmTermsDetail
	 * @throws Exception
	 */
	public Map<String, Object> setTermsOfUsePrivacy(CmTerms cmTerms, CmTermsDetail cmTermsDetail) throws Exception {
		Map<String, Object> rsMap = new HashMap<>();
		try {
			UserDetails user = LoginManager.getUserDetails();

			cmTerms.setRgsterNo(user.getAdminNo());
			cmTerms.setModerNo(user.getAdminNo());
			cmTermsDao.insertTerms(cmTerms);

			cmTermsDetail.setTermsSeq(cmTerms.getTermsSeq());
			cmTermsDetail.setRgsterNo(user.getAdminNo());
			cmTermsDetail.setModerNo(user.getAdminNo());
			cmTermsDetailDao.insertTermsDetail(cmTermsDetail);

			rsMap.put("result", 1);
		} catch (Exception e) {
			rsMap.put("result", 0);
			rsMap.put("Message", e.toString());
		}

		return rsMap;
	}

	/**
	 * @Desc : 이용약관 상세보기
	 * @Method Name : getTermsOfUseDetail
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param cmTerms
	 * @param cmTermsDetail
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getTermsOfUseDetail(CmTerms cmTerms, CmTermsDetail cmTermsDetail) throws Exception {
		Map<String, Object> termsMap = new HashMap<>();
		List<SyCodeDetail> termsDtlList = cmTermsService.getTermsCodeList(CommonCode.TERMS_TYPE_CODE_TERMSOFUSE);

		termsMap.put("cmTermsDetail", cmTermsDetailDao.selectTermsOfUseDetail(cmTermsDetail));
		termsMap.put("cmTerms", cmTermsDao.selectTermsDetail(cmTerms));
		termsMap.put("termsDtlList", termsDtlList);
		return termsMap;
	}

	/**
	 * @Desc : 개인정보 취급방침 상세보기
	 * @Method Name : getPrivacyDetail
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param cmTerms
	 * @param cmTermsDetail
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getPrivacyDetail(CmTerms cmTerms, CmTermsDetail cmTermsDetail) throws Exception {
		Map<String, Object> termsMap = new HashMap<>();

		termsMap.put("cmTerms", cmTermsDao.selectTermsDetail(cmTerms));
		termsMap.put("cmTermsDetail", cmTermsDetailDao.selectTermsOfUseDetail(cmTermsDetail));
		return termsMap;
	}

	/**
	 * @Desc : 회원가입동의, 주문동의 약관 상세보기
	 * @Method Name : getSignUpDetail
	 * @Date : 2019. 2. 7.
	 * @Author : 신인철
	 * @param cmTerms
	 * @param cmTermsDetail
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSignUpOrderDetail(CmTerms cmTerms, CmTermsDetail cmTermsDetail) throws Exception {
		Map<String, Object> termsMap = new HashMap<>();
		List<CmTermsDetail> signUpDetailList = cmTermsDetailDao.selectTermsDetailList(cmTermsDetail);
		termsMap.put("cmTerms", cmTermsDao.selectTermsDetail(cmTerms));
		termsMap.put("cmTermsDetail", signUpDetailList);
		return termsMap;
	}

	/**
	 * @Desc : 상세보기시에 영역 호출
	 * @Method Name : getTermsDetailArea
	 * @Date : 2019. 2. 11.
	 * @Author : 신인철
	 * @param cmTermsDetail
	 * @return
	 * @throws Exception
	 */
	public CmTermsDetail getTermsDetailArea(CmTermsDetail cmTermsDetail) throws Exception {
		cmTermsDetail = cmTermsDetailDao.selectSignUpOrderDetail(cmTermsDetail);

		return cmTermsDetail;
	}

	/**
	 * @Desc : 회원가입동의약관 등록
	 * @Method Name : insertSignUpTerms
	 * @Date : 2019. 2. 7.
	 * @Author : 신인철
	 * @param cmTerms
	 * @param cmTermsDetail
	 * @throws Exception
	 */
	public Map<String, Object> setSignUpOrderTerms(CmTerms cmTerms, Parameter<?> parameter) throws Exception {
		Map<String, Object> rsMap = new HashMap<>();
		try {
			UserDetails user = LoginManager.getUserDetails();
			cmTerms.setRgsterNo(user.getAdminNo());
			cmTerms.setModerNo(user.getAdminNo());

			cmTermsDao.insertTerms(cmTerms);

			int listIndex = 0;

			if (parameter.getInt("signUpListIndex") != 0) {
				listIndex = parameter.getInt("signUpListIndex");
			} else if (parameter.getInt("orderListIndex") != 0) {
				listIndex = parameter.getInt("orderListIndex");
			}
			if (UtilsText.equals(cmTerms.getDirectChange(), Const.BOOLEAN_TRUE)) {
				CmTermsDetail changeParam = cmTermsDao.selectDispTerms(cmTerms);
				changeParam.setDispYn(Const.BOOLEAN_FALSE);
				changeParam.setModerNo(user.getAdminNo());
				cmTermsDetailDao.updateTermsDetail(changeParam);
			}

			int deleteCount = parameter.getInt("deleteCount");

			CmTermsDetail termsDetail = new CmTermsDetail();
			for (int i = 1; i <= listIndex + deleteCount; i++) {
				if (parameter.getString("reqAgreeYn_" + Integer.toString(i)) != null) {
					termsDetail.setTermsSeq(cmTerms.getTermsSeq());
					termsDetail.setTermsName(parameter.getString("termsName_" + Integer.toString(i)));
					termsDetail.setTermsInfo(parameter.getString("termsInfo_" + Integer.toString(i), false));
					termsDetail.setReqAgreeYn(parameter.getString("reqAgreeYn_" + Integer.toString(i)));
					termsDetail.setDispYn(Const.BOOLEAN_TRUE);
					termsDetail.setModerNo(user.getAdminNo());
					termsDetail.setRgsterNo(user.getAdminNo());
					cmTermsDetailDao.insertTermsDetail(termsDetail);
				}
			}
			rsMap.put("result", 1);
		} catch (Exception e) {
			rsMap.put("result", 0);
			rsMap.put("Message", e.toString());
		}
		return rsMap;
	}

	/**
	 * @Desc : 메인페이지 검색 정보로 이용할 코드 가져오기
	 * @Method Name : getTermsListMap
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getTermsListMap() throws Exception {
		Map<String, Object> termsListMap = new HashMap<>();
		List<SyCodeDetail> codeTypeList = commoncodeService.getCodeNoName(CommonCode.TERMS_TYPE_CODE);
		List<SyCodeDetail> codeDtlList = commoncodeService.getCodeNoName(CommonCode.TERMS_DTL_CODE);
		List<SyCodeDetail> termsOfUseList = new ArrayList<>();
		List<SyCodeDetail> privacyCodeList = new ArrayList<>();
		List<SyCodeDetail> signUpCodeList = new ArrayList<>();
		List<SyCodeDetail> orderCodeList = new ArrayList<>();
		for (SyCodeDetail codeParam : codeDtlList) {
			switch (codeParam.getAddInfo1()) {
			case CommonCode.TERMS_TYPE_CODE_TERMSOFUSE:
				termsOfUseList.add(codeParam);
				break;
			case CommonCode.TERMS_TYPE_CODE_PRIVACY:
				privacyCodeList.add(codeParam);
				break;
			case CommonCode.TERMS_TYPE_CODE_SIGNUP:
				signUpCodeList.add(codeParam);
				break;
			case CommonCode.TERMS_TYPE_CODE_ORDER:
				orderCodeList.add(codeParam);
				break;
			}
		}
		termsListMap.put("codeTypeList", codeTypeList);
		termsListMap.put("termsOfUseList", termsOfUseList);
		termsListMap.put("privacyCodeList", privacyCodeList);
		termsListMap.put("signUpCodeList", signUpCodeList);
		termsListMap.put("orderCodeList", orderCodeList);

		return termsListMap;
	}

	/**
	 * @Desc : 코드번호 입력하여 codeDtlList빼오기
	 * @Method Name : getTermsCodeList
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param codeNo
	 * @return
	 * @throws Exception
	 */
	public List<SyCodeDetail> getTermsCodeList(String codeNo) throws Exception {
		SyCodeDetail codeParam = new SyCodeDetail();
		codeParam.setCodeField(CommonCode.TERMS_DTL_CODE);
		codeParam.setAddInfo1(codeNo);
		List<SyCodeDetail> codeDtlList = commoncodeService.getCodeDtlInfoList(codeParam);

		return codeDtlList;
	}

	/**
	 * @Desc : 약관 삭제
	 * @Method Name : deleteTerms
	 * @Date : 2019. 2. 1.
	 * @Author : 신인철
	 * @param cmTerms
	 * @param cmTermsDetail
	 * @throws Exception
	 */
	public Map<String, Object> deleteTerms(CmTerms cmTerms, CmTermsDetail cmTermsDetail) throws Exception {
		Map<String, Object> rsMap = new HashMap<>();
		try {
			cmTermsDetailDao.deleteTerms(cmTermsDetail);
			cmTermsDao.deleteTerms(cmTerms);
			rsMap.put("result", 1);
		} catch (Exception e) {
			rsMap.put("result", 0);
			rsMap.put("Message", e.toString());
		}

		return rsMap;
	}

}
