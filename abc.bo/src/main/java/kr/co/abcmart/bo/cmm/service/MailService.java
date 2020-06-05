/**
 * 
 */
package kr.co.abcmart.bo.cmm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import kr.co.abcmart.bo.cmm.model.master.CmEmailSendHistory;
import kr.co.abcmart.bo.cmm.model.master.CmEmailTemplate;
import kr.co.abcmart.bo.cmm.vo.MailTemplateVO;
import kr.co.abcmart.common.exception.EmailException;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.util.UtilsMail;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc :
 * @FileName : MailService.java
 * @Project : abc.bo
 * @Date : 2019. 3. 25.
 * @Author : Kimyounghyun
 */
@Slf4j
@Service
public class MailService {

	@Autowired
	private Configuration freemarkerConfig;

	@Autowired
	EmailTemplateService emailTemplateService;

	@Autowired
	CmLogHistoryService cmLogHistoryService;

	/**
	 * @Desc : 템플릿 메일 발송 - 실시간 여부를 판단하여 처리한다.
	 * @Method Name : sendMail
	 * @Date : 2019. 4. 2.
	 * @Author : Kimyounghyun
	 * @param mailTemplateVo
	 * @throws Exception
	 */
	public void sendMail(MailTemplateVO mailTemplateVo) throws Exception {
		CmEmailSendHistory cmEmailSendHistory = getCmEmailSendHistory(mailTemplateVo);

		if (mailTemplateVo.isRealTime()) {
			try {
				UtilsMail.send(cmEmailSendHistory.getSndrEmailAddrText(), cmEmailSendHistory.getSndrName(),
						cmEmailSendHistory.getRcvrEmailAddrText(), cmEmailSendHistory.getRcvrName(),
						cmEmailSendHistory.getEmailTitleText(), cmEmailSendHistory.getEmailContText());
				cmEmailSendHistory.setSendYn(Const.BOOLEAN_TRUE);
			} catch (EmailException ee) {
				ee.printStackTrace();
				throw new Exception("실시간 이메일 발송에 실패하였습니다.");
			}
		}
		cmLogHistoryService.insertCmEmailSendHistory(cmEmailSendHistory);
	}

	/**
	 * @Desc : BO 공통 팝업 메일 발송 - 실시간 처리를 하지 않는다.
	 * @Method Name : sendMail
	 * @Date : 2019. 4. 2.
	 * @Author : Kimyounghyun
	 * @param mailVo
	 * @throws Exception
	 */
	public int sendMail(CmEmailSendHistory cmEmailSendHistory) throws Exception {
		cmEmailSendHistory.setSndrEmailAddrText(Const.SYS_SENDER_EMAIL_ADDRESS);
		cmEmailSendHistory.setSndrName(Const.SYS_SENDER_EMAIL_NAME);

		cmEmailSendHistory.setSendYn(Const.BOOLEAN_FALSE);
		cmEmailSendHistory.setRgsterNo(LoginManager.getUserDetails().getAdminNo());

		if (UtilsText.isAllBlank(cmEmailSendHistory.getMemberNo())) {
			cmEmailSendHistory.setMemberNo(Const.NON_MEMBER_NO);
		}

		return cmLogHistoryService.insertCmEmailSendHistory(cmEmailSendHistory);
	}

	/**
	 * @Desc : 데이터가 담긴 메일발송이력(큐) 모델객체를 얻는다.
	 * @Method Name : getCmEmailSendHistory
	 * @Date : 2019. 4. 2.
	 * @Author : Kimyounghyun
	 * @param mailTemplateVo
	 * @return
	 * @throws Exception
	 */
	private CmEmailSendHistory getCmEmailSendHistory(MailTemplateVO mailTemplateVo) throws Exception {
		CmEmailSendHistory cmEmailSendHistory = new CmEmailSendHistory();

		cmEmailSendHistory = processTemplate(mailTemplateVo, cmEmailSendHistory);

		cmEmailSendHistory.setSndrEmailAddrText(Const.SYS_SENDER_EMAIL_ADDRESS);
		cmEmailSendHistory.setSndrName(Const.SYS_SENDER_EMAIL_NAME);

		cmEmailSendHistory.setMemberNo(mailTemplateVo.getReceiverMemberNo());
		cmEmailSendHistory.setRcvrEmailAddrText(mailTemplateVo.getReceiverEmail());
		cmEmailSendHistory.setRcvrName(mailTemplateVo.getReceiverName());

		cmEmailSendHistory.setSendYn(Const.BOOLEAN_FALSE);
		cmEmailSendHistory.setRgsterNo(Const.SYSTEM_ADMIN_NO);

		return cmEmailSendHistory;
	}

	/**
	 * @Desc : 템플릿 변환 처리를 한다.
	 * @Method Name : processTemplate
	 * @Date : 2019. 4. 2.
	 * @Author : Kimyounghyun
	 * @param mailTemplateVo
	 * @param cmEmailSendHistory
	 * @return
	 * @throws Exception
	 */
	private CmEmailSendHistory processTemplate(MailTemplateVO mailTemplateVo, CmEmailSendHistory cmEmailSendHistory)
			throws Exception {
		CmEmailTemplate cmEmailTemplate = getMailTemplateByEmailId(mailTemplateVo.getMailTemplateId());

		String templateId = cmEmailTemplate.getEmailId();
		String templateTitleName = UtilsText.concat(templateId, "Title");
		String templateContentName = UtilsText.concat(templateId, "Content");

		// title template
		Template templateTitle = new Template(templateTitleName, cmEmailTemplate.getEmailTitleText(), freemarkerConfig);
		String sTitle = FreeMarkerTemplateUtils.processTemplateIntoString(templateTitle,
				mailTemplateVo.getMailTemplateModel());

		// content template
		Template templateContent = new Template(templateContentName, cmEmailTemplate.getEmailContText(),
				freemarkerConfig);
		String sContent = FreeMarkerTemplateUtils.processTemplateIntoString(templateContent,
				mailTemplateVo.getMailTemplateModel());

		log.debug("freemarker template result - {}, {}", sTitle, sContent);

		cmEmailSendHistory.setSiteNo(cmEmailTemplate.getSiteNo());
		cmEmailSendHistory.setEmailTmplSeq(cmEmailTemplate.getEmailTmplSeq());
		cmEmailSendHistory.setEmailTitleText(sTitle);
		cmEmailSendHistory.setEmailContText(sContent);

		return cmEmailSendHistory;
	}

	/**
	 * @Desc : 템블릿 코드에 해당하는 메일 템플릿을 조회한다.
	 * @Method Name : getMailTemplateByEmailId
	 * @Date : 2019. 3. 29.
	 * @Author : Kimyounghyun
	 * @param emailId
	 * @return
	 * @throws Exception
	 */
	private CmEmailTemplate getMailTemplateByEmailId(String emailId) throws Exception {
		return emailTemplateService.getEmailTemplateByMailId(emailId);
	}

}
