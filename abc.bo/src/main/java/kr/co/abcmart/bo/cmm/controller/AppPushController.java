package kr.co.abcmart.bo.cmm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.cmm.model.master.CmPushMessage;
import kr.co.abcmart.bo.cmm.service.CmPushMessageService;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.constant.CommonCode;
import kr.co.abcmart.constant.Const;

@Controller
@RequestMapping("/cmm/msg/apppush")
public class AppPushController extends BaseController {
	@Autowired
	private CmPushMessageService cmPushMessageService;
	@Autowired
	private SiteService siteService;

	/**
	 * @Desc : app push 관리 메인화면.
	 * @Method Name : appPushMain
	 * @Date : 2019. 5. 8.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("")
	public ModelAndView appPushMain(Parameter<?> parameter) throws Exception {

		parameter.addAttribute("ingCode", cmPushMessageService.pushIngStatCode(CommonCode.PUSH_ING_STAT_CODE));
		parameter.addAttribute("siteNoList", cmPushMessageService.pushSiteCode());

		return forward("/cmm/app-push/app-push-main");
	}

	/**
	 * @Desc : app push 메시지 목록
	 * @Method Name : appPushReadList
	 * @Date : 2019. 5. 15.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-list")
	public void appPushReadList(Parameter<CmPushMessage> parameter) throws Exception {

		Pageable<CmPushMessage, CmPushMessage> pushPageable = new Pageable<>(parameter);
		Page<CmPushMessage> page = cmPushMessageService.appPushReadList(pushPageable);

		writeJson(parameter, page.getGrid());

	}

	/**
	 * @Desc : app push 관리 등록 폼.
	 * @Method Name : appPushCreateForm
	 * @Date : 2019. 5. 8.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/create-form")
	public ModelAndView appPushCreateForm(Parameter<?> parameter) throws Exception {
		List<SySite> siteList = siteService.getSiteList();
		parameter.addAttribute("siteList", siteList);

		return forward("/cmm/app-push/app-push-create-form");
	}

	/**
	 * @Desc : app push 등록
	 * @Method Name : saveAppPush
	 * @Date : 2019. 5. 8.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/save")
	public void saveAppPush(Parameter<CmPushMessage> parameter) throws Exception {
		CmPushMessage cmPushMessage = parameter.get();

		String titleText = parameter.getString("pushTitleText", false);
		String contText = parameter.getString("contText", false);

		try {
			if (titleText.indexOf("<script") > -1 || contText.indexOf("<script") > -1) {
				throw new Exception("발송 불가능한 내용이 포함되어 있습니다.");
			}

			cmPushMessage.setPushTitleText(titleText);
			cmPushMessage.setContText(contText);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		writeJson(parameter, cmPushMessageService.createPushMsg(cmPushMessage));

	}

	/**
	 * @Desc : app push 상세.
	 * @Method Name : detailAppPush
	 * @Date : 2019. 5. 8.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-detail")
	public ModelAndView detailAppPush(Parameter<?> parameter) throws Exception {

		String pushMesgNo = parameter.getString("pushMesgNo");
		Map<String, Object> rsMap = cmPushMessageService.readDetailAppPushForm(pushMesgNo);

		parameter.addAttribute("pushMesgValue", rsMap.get("cmPushMessage"));
		parameter.addAttribute("siteList", rsMap.get("siteList"));

		return forward("/cmm/app-push/app-push-create-form");
	}

	/**
	 * @Desc : app push 수정.
	 * @Method Name : modifyAppPush.
	 * @Date : 2019. 5. 21.
	 * @Author : 이재렬
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/modify")
	public void modifyAppPush(Parameter<CmPushMessage> parameter) throws Exception {
		CmPushMessage cmPushMessage = parameter.get();

		String titleText = parameter.getString("pushTitleText", false);
		String contText = parameter.getString("contText", false);

		try {
			if (titleText.indexOf("<script") > -1 || contText.indexOf("<script") > -1) {
				throw new Exception("발송 불가능한 내용이 포함되어 있습니다.");
			}

			cmPushMessage.setPushTitleText(titleText);
			cmPushMessage.setContText(contText);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		writeJson(parameter, cmPushMessageService.modifyAppPush(cmPushMessage));
	}

	/**
	 * @Desc : app push excel 폼 다운로드
	 * @Method Name : excelFormDownload
	 * @Date : 2019. 6. 5.
	 * @Author : 고웅환
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/excel-form-down")
	public void excelFormDownload(Parameter<CmPushMessage> parameter) throws Exception {
		parameter.downloadExcelTemplate("cmm/app-push/excel/appPush-target-template");
	}

	/**
	 * @Desc : 발송관리 팝업
	 * @Method Name : sendAppPushManagement
	 * @Date : 2019. 6. 20.
	 * @Author : 고웅환
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/send-managemnet")
	public ModelAndView sendAppPushManagement(Parameter<CmPushMessage> parameter) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map = cmPushMessageService.getMessageDetail(parameter.get().getPushMesgNo());

		parameter.addAttribute("trgtCodeList", map.get("trgtCodeList"));
		parameter.addAttribute("messageDetail", map.get("messageDetail"));
		parameter.addAttribute("messageHistory", map.get("messageHistory"));

		return forward("/cmm/app-push/app-push-send-management");
	}

	/**
	 * @Desc : 대상자 확인
	 * @Method Name : readTargetCheck
	 * @Date : 2019. 6. 21.
	 * @Author : 고웅환
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/read-target-list")
	public void readTargetCheck(Parameter<CmPushMessage> parameter) throws Exception {
		// 테스트 발송일 경우 grid 리턴
		if (UtilsText.equals(Const.PUSH_SEND_GBN_TYPE_TEST, parameter.get().getSendGbnType())) {
			Pageable<CmPushMessage, CmPushMessage> pageable = new Pageable<>(parameter);
			pageable.setContent(cmPushMessageService.sendTestTargetMemberList(parameter.get()));
			Page<CmPushMessage> page = pageable.getPage();

			writeJson(parameter, page.getGrid());
		} else if (UtilsText.equals(Const.PUSH_SEND_GBN_TYPE_REGULAR, parameter.get().getSendGbnType())) {
			Map<String, Object> map = cmPushMessageService.sendTargetMemberList(parameter.get());

			// 일반 발송일 경우 map(건수만) 리턴
			writeJson(parameter, map);
		}
	}

	/**
	 * @Desc : 테스트 발송 대상자 삭제
	 * @Method Name : deleteTestSendTarget
	 * @Date : 2019. 6. 227.
	 * @Author : 고웅환
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/delete-target")
	public void deleteTestSendTarget(Parameter<CmPushMessage> parameter) throws Exception {
		Pageable<CmPushMessage, CmPushMessage> pageable = new Pageable<>(parameter);
		pageable.setContent(cmPushMessageService.deleteTestSendTarget(parameter.get()));
		Page<CmPushMessage> page = pageable.getPage();

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 즉시발송/예약발송
	 * @Method Name : sendAppPushMessage
	 * @Date : 2019. 6. 26.
	 * @Author : 고웅환
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/send-app-push")
	public void sendAppPushMessage(Parameter<CmPushMessage> parameter) throws Exception {
		writeJson(parameter, cmPushMessageService.sendAppPush(parameter.get()));

	}

	/**
	 * @Desc : 발송취소
	 * @Method Name : cancelAppPushMessage
	 * @Date : 2019. 6. 26.
	 * @Author : 고웅환
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/cancel-send")
	public void cancelAppPushMessage(Parameter<CmPushMessage> parameter) throws Exception {
		// String message = cmPushMessageService.cancelAppPush(parameter.get());
		writeJson(parameter, cmPushMessageService.cancelAppPush(parameter.get()));

	}
}
