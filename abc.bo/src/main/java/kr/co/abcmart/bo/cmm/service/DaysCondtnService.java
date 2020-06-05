package kr.co.abcmart.bo.cmm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.cmm.model.master.CmDaysCondtn;
import kr.co.abcmart.bo.cmm.repository.master.CmDaysCondtnDao;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DaysCondtnService {

	@Autowired
	private CmDaysCondtnDao cmDaysCondtnDao;

	/**
	 * @Desc : 조건 날짜 관리목록을 조회
	 * @Method Name : getDaysCondtnList
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<CmDaysCondtn> getDaysCondtnList(Pageable<CmDaysCondtn, CmDaysCondtn> pageable) throws Exception {
		int count = cmDaysCondtnDao.selectDaysCondtnListCount(pageable);

		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(cmDaysCondtnDao.selectDaysCondtnList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : 조건 날짜 관리목록을 수정
	 * @Method Name : updateList
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param params
	 * @throws Exception
	 */
	public void updateList(CmDaysCondtn[] params) throws Exception {

		// 세션정보
		UserDetails user = LoginManager.getUserDetails();

		for (CmDaysCondtn cmDaysCondtn : params) {
			if (UtilsText.equals(cmDaysCondtn.getStatus(), "U")) {
				cmDaysCondtn.setModerNo(user.getAdminNo());
				cmDaysCondtnDao.updateList(cmDaysCondtn);
			}
		}
	}

	/**
	 * @Desc : 조건 날짜 조회(단건)
	 * @Method Name : getDaysCondtn
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param condtnTermName
	 * @return
	 * @throws Exception
	 */
	public CmDaysCondtn getDaysCondtn(String condtnTermName) throws Exception {
		return cmDaysCondtnDao.selectDaysCondtn(condtnTermName);
	}
}
