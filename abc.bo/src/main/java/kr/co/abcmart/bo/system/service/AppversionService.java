package kr.co.abcmart.bo.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.system.model.master.SyAppVersion;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.repository.master.SyAppVersionDao;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AppversionService {

	@Autowired
	SyAppVersionDao syAppVersionDao;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	SiteService siteService;

	/**
	 * 
	 * @Desc : APP 버전 정보를 조회한다.
	 * @Method Name : getAppversionList
	 * @Date : 2019. 2. 20.
	 * @Author : choi
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<SyAppVersion> getAppversionList(Pageable<SyAppVersion, SyAppVersion> pageable) throws Exception {
		int count = syAppVersionDao.selectAppversionListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(syAppVersionDao.selectAppversionList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * 
	 * @Desc : APP 버전 정보를 등록한다.
	 * @Method Name : setAppversionData
	 * @Date : 2019. 2. 20.
	 * @Author : choi
	 * @param syAppVersion
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> setAppversionData(SyAppVersion syAppVersion) throws Exception {
		int resultCnt = 0;
		Map<String, Object> result = new HashMap<>(); // 결과 카운트
		UserDetails user = LoginManager.getUserDetails();
		syAppVersion.setRgsterNo(user.getAdminNo());

		resultCnt = syAppVersionDao.setAppversionData(syAppVersion);
		if (resultCnt > 0) {
			result.put("result", Const.BOOLEAN_TRUE);
		}
		return result;
	}

	/**
	 * 
	 * @Desc : 사이트 정보를 조회한다.
	 * @Method Name : getUseSiteList
	 * @Date : 2019. 2. 20.
	 * @Author : choi
	 * @return
	 * @throws Exception
	 */
	public List<SySite> getSiteList() throws Exception {
		return siteService.getSiteList();
	}

	/**
	 * 
	 * @Desc : APP 공통 코드를 조회한다.
	 * @Method Name : getCodeNoName
	 * @Date : 2019. 2. 20.
	 * @Author : choi
	 * @return
	 * @throws Exception
	 */
	public List<SyCodeDetail> getCodeNoName() throws Exception {
		return commonCodeService.getCodeNoName(CommonCode.APP_OS_CODE);
	}
}
