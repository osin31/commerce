package kr.co.abcmart.bo.display.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.display.model.master.BdPopup;
import kr.co.abcmart.bo.display.model.master.BdPopupDevice;
import kr.co.abcmart.bo.display.model.master.BdPopupDisplayPosition;
import kr.co.abcmart.bo.display.service.PopupService;
import kr.co.abcmart.bo.display.vo.BdPopupSearchVO;
import kr.co.abcmart.bo.system.model.master.SyCodeDetail;
import kr.co.abcmart.bo.system.model.master.SySite;
import kr.co.abcmart.bo.system.service.CommonCodeService;
import kr.co.abcmart.bo.system.service.SiteService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.CommonCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("display/standard/popup")
public class PopupController extends BaseController {
	@Autowired
	SiteService siteService;

	@Autowired
	private PopupService popupService;

	@Autowired
	private CommonCodeService commonCodeService;

	/**
	 * @Desc : 팝업 관리
	 * @Method Name : popup
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ModelAndView popup(Parameter<?> parameter) throws Exception {
		List<SySite> siteList = siteService.getSiteList();
		List<SyCodeDetail> deviceCodeList = commonCodeService.getCodeNoName(CommonCode.DEVICE_CODE);

		log.debug("popup");
		parameter.addAttribute("siteList", siteList);
		parameter.addAttribute("deviceCodeList", deviceCodeList);
		return forward("/display/standard/popup");
	}

	/**
	 * @Desc : 팝업 관리(리스트조회)
	 * @Method Name : readPopupList
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/list/read")
	public void readPopupList(Parameter<BdPopupSearchVO> parameter) throws Exception {

		Pageable<BdPopupSearchVO, BdPopup> pageable = new Pageable<>(parameter);

		Page<BdPopup> page = popupService.getPopupList(pageable);

		writeJson(parameter, page.getGrid());

	}

	/**
	 * @Desc : 팝업 관리(상세)
	 * @Method Name : popupDetail
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/detail")
	public ModelAndView popupDetail(Parameter<BdPopup> parameter) throws Exception {

		BdPopup bdPopup = parameter.get();

		Integer popupSeq = bdPopup.getPopupSeq();

		if (popupSeq != null) {
			bdPopup = popupService.getPopup(bdPopup);

			BdPopupDevice bdPopupDevice = new BdPopupDevice();
			bdPopupDevice.setPopupSeq(popupSeq);

			bdPopupDevice.setDeviceCode(CommonCode.DEVICE_PC);
			BdPopupDevice pcBdPopupDevice = popupService.getPopupDeviceListByPrimaryKey(bdPopupDevice);

			bdPopupDevice.setDeviceCode(CommonCode.DEVICE_MOBILE);
			BdPopupDevice moBdPopupDevice = popupService.getPopupDeviceListByPrimaryKey(bdPopupDevice);

			List<BdPopupDisplayPosition> bdPopupDisplayPositionList = popupService
					.getPopupDisplayPositionListByPopupSeq(popupSeq);

			parameter.addAttribute("pcBdPopupDevice", pcBdPopupDevice);
			parameter.addAttribute("moBdPopupDevice", moBdPopupDevice);
			parameter.addAttribute("bdPopupDisplayPositionList", bdPopupDisplayPositionList);
		}
		log.debug("bdPopup :: {} ", bdPopup);

		List<SySite> siteList = siteService.getSiteList();
		List<SyCodeDetail> deviceCodeList = commonCodeService.getCodeNoName(CommonCode.DEVICE_CODE);
		List<SyCodeDetail> dispPostnCodeList = commonCodeService.getCodeNoName(CommonCode.POPUP_DISP_POSTN_CODE);

		parameter.addAttribute("bdPopup", bdPopup);
		parameter.addAttribute("siteList", siteList);
		parameter.addAttribute("deviceCodeList", deviceCodeList);
		parameter.addAttribute("dispPostnCodeList", dispPostnCodeList);

		return forward("/display/standard/popup-detail");
	}

	/**
	 * @Desc : 팝업 관리(저장)
	 * @Method Name : savePopup
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/save")
	public void savePopup(Parameter<BdPopup> parameter) throws Exception {

		parameter.validate();

		BdPopup bdPopup = parameter.get();

		popupService.insertPopup(bdPopup);

	}

	/**
	 * @Desc : 팝업 관리(수정)
	 * @Method Name : modifyPopup
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/modify")
	public void modifyPopup(Parameter<BdPopup> parameter) throws Exception {

		parameter.validate();

		BdPopup bdPopup = parameter.get();

		popupService.updatePopup(bdPopup);
	}

	/**
	 * @Desc : 팝업 관리(삭제)
	 * @Method Name : removePopup
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/remove")
	public void removePopup(Parameter<BdPopup> parameter) throws Exception {
		popupService.deletePopup(parameter.get().getPopupSeq());
	}

	/**
	 * @Desc : 팝업 관리(미리보기)
	 * @Method Name : popupPreview
	 * @Date : 2019. 11. 7.
	 * @Author : 이지훈
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/preview")
	public ModelAndView popupPreview(Parameter<BdPopup> parameter) throws Exception {

		BdPopup bdPopup = parameter.get();

		parameter.addAttribute("bdPopup", bdPopup);

		return forward("/display/standard/popup-preview");
	}
}