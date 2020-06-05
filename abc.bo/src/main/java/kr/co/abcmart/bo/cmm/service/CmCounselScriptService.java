package kr.co.abcmart.bo.cmm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.cmm.model.master.CmCounselScript;
import kr.co.abcmart.bo.cmm.repository.master.CmCounselScriptDao;
import kr.co.abcmart.bo.cmm.repository.master.base.BaseCmCounselScriptDao;
import kr.co.abcmart.bo.cmm.vo.CounselScriptSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CmCounselScriptService {

	@Autowired
	private CmCounselScriptDao cmCounselScriptDao;

	@Autowired
	private BaseCmCounselScriptDao baseCmCounselScriptDao;

	@Autowired
	CommonCodeService commonCodeService;

	/**
	 * @Desc : 상담 스크립트 관리 리스트 Form 기초 데이터를 조회
	 * @Method Name : getCounselScriptSearchForm
	 * @Date : 2019. 2. 8.
	 * @Author : kiowa
	 * @param codeField
	 * @return
	 * @throws Exception
	 */
	public List<SyCodeDetail> getCounselScriptSearchForm(String codeField) throws Exception {
		return commonCodeService.getCodeNoNameFilterAddInfo(codeField, Const.BOOLEAN_TRUE);
	}

	/**
	 * @Desc : 검색 조건에 맞는 상담스크립트 정보를 조회한다.
	 * @Method Name : getCouselScriptReadList
	 * @Date : 2019. 2. 8.
	 * @Author : kiowa
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<CmCounselScript> getCouselScriptReadList(Pageable<CounselScriptSearchVO, CmCounselScript> pageable)
			throws Exception {
		int totalCount = cmCounselScriptDao.selectCmCounselScriptListCount(pageable);

		pageable.setTotalCount(totalCount);

		if (totalCount > 0) {
			pageable.setContent(cmCounselScriptDao.selectCmCounselScriptList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 선택한 상담스크립트 상세 정보를 조회한다.
	 * @Method Name : getCouselScriptDetail
	 * @Date : 2019. 2. 8.
	 * @Author : kiowa
	 * @param cmCounselScript
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getCouselScriptDetail(CmCounselScript cmCounselScript) throws Exception {
		Map<String, Object> rtnVal = new HashMap<String, Object>(); // 결과 정보

		String[] codeFields = { CommonCode.CNSL_GBN_CODE, CommonCode.CNSL_TYPE_CODE, CommonCode.CNSL_TYPE_DTL_CODE };

		Map<String, List<SyCodeDetail>> resultMap = commonCodeService.getAllCodeListByGroup(codeFields);

		// 상담 메뉴 코드 리스트
		rtnVal.put("cnslGbnCode", resultMap.get(CommonCode.CNSL_GBN_CODE));

		// 상담 스크립트 상세 조회
		if (cmCounselScript.getCnslScriptSeq() != null && !cmCounselScript.getCnslScriptSeq().equals("")) {
			cmCounselScript = this.selectCouselScript(cmCounselScript);

			// 상담유형 코드
			rtnVal.put("cnslTypeCode", resultMap.get(CommonCode.CNSL_TYPE_CODE));

			// 상담유형 상세 코드
			rtnVal.put("cnslTypeDtlCode", resultMap.get(CommonCode.CNSL_TYPE_DTL_CODE));
		}

		// 상담 스크립트 상세 정보 조회
		rtnVal.put("cmCounselScript", cmCounselScript);

		return rtnVal;
	}

	/**
	 * @Desc : 상담스크립트 상세 정보를 조회 한다.
	 * @Method Name : selectCouselScript
	 * @Date : 2019. 2. 8.
	 * @Author : kiowa
	 * @param cmCounselScript
	 * @return
	 * @throws Exception
	 */
	public CmCounselScript selectCouselScript(CmCounselScript cmCounselScript) throws Exception {

		return cmCounselScriptDao.selectCmCounselScript(cmCounselScript);
	}

	/**
	 * @Desc : 상담 스크립트 정보를 등록 한다.
	 * @Method Name : insertCouselScriptDetail
	 * @Date : 2019. 2. 8.
	 * @Author : kiowa
	 * @param cmCounselScript
	 * @return
	 * @throws Exception
	 */
	public int insertCouselScriptDetail(CmCounselScript cmCounselScript) throws Exception {
		// 기존에 동일한 상담 유형 스크립트를 등록했는지 조회한다.
		/*
		 * if (this.selectCounselScriptTitle(counselScriptSearchVO) != null) { return
		 * -1; }
		 */

		cmCounselScript.setRgsterNo(LoginManager.getUserDetails().getAdminNo());
		cmCounselScript.setModerNo(LoginManager.getUserDetails().getAdminNo());
		return cmCounselScriptDao.insertCmCounselScript(cmCounselScript);
	}

	/**
	 * @Desc : 상담 스크립트 정보를 수정한다.
	 * @Method Name : updateCouselScriptDetail
	 * @Date : 2019. 2. 8.
	 * @Author : kiowa
	 * @param cmCounselScript
	 * @return
	 * @throws Exception
	 */
	public int updateCouselScriptDetail(CmCounselScript cmCounselScript) throws Exception {
		cmCounselScript.setModerNo(LoginManager.getUserDetails().getAdminNo());

		return cmCounselScriptDao.updateCmCounselScript(cmCounselScript);
	}

	/**
	 * @Desc : 상담 스크립트 정보를 삭제한다.
	 * @Method Name : deleteCounselScriptDetail
	 * @Date : 2019. 2. 8.
	 * @Author : kiowa
	 * @param cmCounselScript
	 * @return
	 * @throws Exception
	 */
	public int deleteCounselScriptDetail(CmCounselScript cmCounselScript) throws Exception {
		cmCounselScript.setModerNo(LoginManager.getUserDetails().getAdminNo());

		return cmCounselScriptDao.delete(cmCounselScript);
	}

	/**
	 * @Desc : 선택한 상담스크립트 정보를 삭제한다.
	 * @Method Name : deleteMmultiCounselScriptDetail
	 * @Date : 2019. 2. 8.
	 * @Author : kiowa
	 * @param cmCounselScriptArray
	 * @return
	 * @throws Exception
	 */
	public int deleteMmultiCounselScriptDetail(CmCounselScript[] cmCounselScriptArray) throws Exception {
		int deleteCount = 0;
		for (CmCounselScript cmCounselScript : cmCounselScriptArray) {
			if (deleteCounselScriptDetail(cmCounselScript) > 0) {
				deleteCount++;
			}
		}
		return deleteCount;
	}

	/**
	 * @Desc : 상담유형 코드에 등록된 유효한 상담 스크립트를 조회 한다.
	 * @Method Name : selectCounselScriptTitle
	 * @Date : 2019. 2. 8.
	 * @Author : kiowa
	 * @param counselScriptSearchVO
	 * @return
	 * @throws Exception
	 */
	public List<CmCounselScript> selectCounselScriptTitle(CmCounselScript cmCounselScript) throws Exception {
		return cmCounselScriptDao.selectCounselScriptTitle(cmCounselScript);
	}

	/**
	 * @Desc : 상담에 사용된 답변스크립트 조회
	 * @Method Name : selectAswrCnslScript
	 * @Date : 2019. 4. 19.
	 * @Author : 신인철
	 * @return
	 * @throws Exception
	 */
	public CmCounselScript getAswrCnslScript(String cnslScriptSeq) throws Exception {
		return cmCounselScriptDao.selectAswrCnslScript(cnslScriptSeq);
	}

	/**
	 *
	 * @Desc :
	 * @Method Name : selectCouselScriptListForUse
	 * @Date : 2019. 2. 22.
	 * @Author : ansuk
	 * @param cmCounselScript
	 * @return
	 * @throws Exception
	 */
	public List<CmCounselScript> selectCouselScriptListForUse(CmCounselScript cmCounselScript) throws Exception {

		return baseCmCounselScriptDao.select(cmCounselScript);
	}

}
