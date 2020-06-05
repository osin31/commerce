package kr.co.abcmart.bo.cmm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.cmm.model.master.CmEmailSendHistory;
import kr.co.abcmart.bo.cmm.model.master.CmMessageSendHistory;
import kr.co.abcmart.bo.cmm.repository.master.CmEmailSendHistoryDao;
import kr.co.abcmart.bo.cmm.repository.master.CmMessageSendHistoryDao;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CmLogHistoryService {

	@Autowired
	CmEmailSendHistoryDao cmEmailSendHistoryDao;

	@Autowired
	CmMessageSendHistoryDao cmMessageSendHistoryDao;

	@Autowired
	SiteService siteService;

	/**
	 * 
	 * @Desc : 이메일 발송 로그 데이터 리스트 조회
	 * @Method Name : getEmailSendHistoryList
	 * @Date : 2019. 3. 20.
	 * @Author : choi
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<CmEmailSendHistory> getEmailSendHistoryList(Pageable<CmEmailSendHistory, CmEmailSendHistory> pageable)
			throws Exception {
		int count = cmEmailSendHistoryDao.selectEmailSendHistoryListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(cmEmailSendHistoryDao.selectEmailSendHistoryList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * 
	 * @Desc : 이메일 발송 로그 상세 데이터 조회
	 * @Method Name : getMailSendHistoryDetail
	 * @Date : 2019. 3. 20.
	 * @Author : choi
	 * @param cmEmailSendHistory
	 * @return
	 * @throws Exception
	 */
	public CmEmailSendHistory getMailSendHistoryDetail(CmEmailSendHistory cmEmailSendHistory) throws Exception {
		return cmEmailSendHistoryDao.getmailSendHistoryDetail(cmEmailSendHistory);

	}

	/**
	 * 
	 * @Desc : 사이트목록 조회
	 * @Method Name : getUseSiteList
	 * @Date : 2019. 3. 20.
	 * @Author : choi
	 * @return
	 * @throws Exception
	 */
	public List<SySite> getSiteList() throws Exception {
		return siteService.getSiteList();
	}

	/**
	 * 
	 * @Desc : 메세지 발송 리스트 데이트 조회
	 * @Method Name : getMessageSendHistoryList
	 * @Date : 2019. 3. 20.
	 * @Author : choi
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public Page<CmMessageSendHistory> getMessageSendHistoryList(
			Pageable<CmMessageSendHistory, CmMessageSendHistory> pageable) throws Exception {
		int count = cmMessageSendHistoryDao.selectMessageSendHistoryListCount(pageable);
		pageable.setTotalCount(count);

		if (count > 0) {
			pageable.setContent(cmMessageSendHistoryDao.selectMessageSendHistoryList(pageable));
		}

		return pageable.getPage();
	}

	/**
	 * @Desc : CM_EMAIL_SEND_HISTOR에 데이터를 저장한다.
	 * @Method Name : insertCmEmailSendHistor
	 * @Date : 2019. 3. 21.
	 * @Author : kiowa
	 * @param cmEmailSendHistory
	 * @return
	 * @throws Exception
	 */
	protected int insertCmEmailSendHistory(CmEmailSendHistory cmEmailSendHistory) throws Exception {
		log.debug("cmEmailSendHistory - {}", cmEmailSendHistory);

		return cmEmailSendHistoryDao.insertMail(cmEmailSendHistory);
	}

	@Deprecated
	public int sendTextMesg(CmMessageSendHistory params) throws Exception {
		String sndrName = "(주)에이비씨마트코리아";

		if ("100001".equals(params.getSiteNo())) {
			sndrName = "(주)에이비씨마트코리아";
		}
		params.setSndrName(sndrName);
		params.setSendYn(Const.BOOLEAN_FALSE);

		if (UtilsText.isAllBlank(params.getMemberNo())) {
			params.setMemberNo(Const.NON_MEMBER_NO);
		}
		params.setAdminSendYn(Const.BOOLEAN_TRUE);
		params.setRgsterNo(LoginManager.getUserDetails().getAdminNo());

		return insertCmMessageSendHistory(params);
	}

	/**
	 * @Desc : CM_MESSAGE_SEND_HISTORY에 데이터를 저장한다.
	 * @Method Name : insertCmMessageSendHistory
	 * @Date : 2019. 3. 21.
	 * @Author : kiowa
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private int insertCmMessageSendHistory(CmMessageSendHistory params) throws Exception {
		return cmMessageSendHistoryDao.insert(params);
	}

}
