package kr.co.abcmart.bo.cmm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.cmm.model.master.CmEmailSendHistory;
import kr.co.abcmart.bo.cmm.model.master.CmMessageSendHistory;
import kr.co.abcmart.bo.cmm.service.CmLogHistoryService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("cmm")
public class CmLogHistoryController extends BaseController {

	@Autowired
	private CmLogHistoryService cmLogHistoryService;

	/**
	 *
	 * @Desc : 메일 발송 이력 리스트 페이지를 호출한다.
	 * @Method Name : cmailSendHistoryMain
	 * @Date : 2019. 2. 28.
	 * @Author : choi
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/email-send-history")
	public ModelAndView cmEailSendHistoryMain(Parameter<CmEmailSendHistory> parameter) throws Exception {
		parameter.addAttribute("SITE_CODE", cmLogHistoryService.getSiteList());

		return forward("/cmm/email-history/email-send-history-main");
	}

	/**
	 *
	 * @Desc : 메일 발송 이력 그리드 리스트 데이터를 조회한다.
	 * @Method Name : cmEmailSendHistoryList
	 * @Date : 2019. 2. 28.
	 * @Author : choi
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/email-send-history/read-list")
	public void cmEmailSendHistoryList(Parameter<CmEmailSendHistory> parameter) throws Exception {
		CmEmailSendHistory cmEmailSendHistory = parameter.get();
		cmEmailSendHistory.setArrSendYn(parameter.getStringArray("sendYn"));

		Pageable<CmEmailSendHistory, CmEmailSendHistory> pageable = new Pageable<>(parameter);
		Page<CmEmailSendHistory> page = cmLogHistoryService.getEmailSendHistoryList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 *
	 * @Desc : 메일발송 이력 상세화면
	 * @Method Name : cmEmailSendHistoryDetail
	 * @Date : 2019. 3. 4.
	 * @Author : choi
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/email-send-history/detail")
	public ModelAndView cmEmailSendHistoryDetail(Parameter<CmEmailSendHistory> parameter) throws Exception {
		CmEmailSendHistory cmEmailSendHistory = parameter.get();
		parameter.addAttribute("DATA", cmLogHistoryService.getMailSendHistoryDetail(cmEmailSendHistory));

		return forward("/cmm/email-history/email-send-history-detail");
	}

	/**
	 *
	 * @Desc : 문자발송 이력 페이지를 호출한다.
	 * @Method Name : cmailSendHistoryMain
	 * @Date : 2019. 3. 5.
	 * @Author : choi
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/message-send-history")
	public ModelAndView cmMessageSendHistoryMain(Parameter<CmMessageSendHistory> parameter) throws Exception {
		parameter.addAttribute("SITE_CODE", cmLogHistoryService.getSiteList());

		return forward("/cmm/message-history/message-send-history-main");
	}

	/**
	 *
	 * @Desc : 메세지 발송 로그 데이터 리스트 조회
	 * @Method Name : cmMessageSendHistoryList
	 * @Date : 2019. 3. 20.
	 * @Author : choi
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/message-send-history/read-list")
	public void cmMessageSendHistoryList(Parameter<CmMessageSendHistory> parameter) throws Exception {
		CmMessageSendHistory cmMessageSendHistory = parameter.get();
		cmMessageSendHistory.setArrSendYn(parameter.getStringArray("sendYn"));

		Pageable<CmMessageSendHistory, CmMessageSendHistory> pageable = new Pageable<>(parameter);
		Page<CmMessageSendHistory> page = cmLogHistoryService.getMessageSendHistoryList(pageable);

		writeJson(parameter, page.getGrid());
	}
}