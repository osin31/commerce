package kr.co.abcmart.bo.system.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.bo.system.service.AdminService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : A-Connect 사용자 관리
 * @FileName : StoreAdminController.java
 * @Project : abc.bo
 * @Date : 2019. 2. 15.
 * @Author : kiowa
 */
@Slf4j
@Controller
@RequestMapping("system/admin/store")
public class StoreAdminController extends BaseController {

	@Autowired
	private AdminService adminService;

	/**
	 * @Desc : A-Connect 사용자 조회 Form
	 * @Method Name : getStoreAdminIndex
	 * @Date : 2019. 2. 15.
	 * @Author : kiowa
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("")
	public ModelAndView getStoreAdminIndex(Parameter<?> parameter) throws Exception {

		return forward("/system/admin/store/admin-main");
	}

	/**
	 * @Desc : A-Connect 사용자 리스트 조회
	 * @Method Name : readStoreAdiminList
	 * @Date : 2019. 2. 15.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-list")
	@ResponseBody
	public void readStoreAdiminList(Parameter<SyAdmin> parameter) throws Exception {
		SyAdmin syAdmin = parameter.get();

		syAdmin.setAuthNo(Const.ACONNECT_AUTH_NO);

		Pageable<SyAdmin, SyAdmin> adminVOPageble = new Pageable<>(parameter);

		Page<SyAdmin> page = adminService.getAdminList(adminVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : A-Connect 사용자 상세 정보를 조회
	 * @Method Name : readStoreAdmin
	 * @Date : 2019. 2. 15.
	 * @Author : kiowa
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/read-detail")
	public ModelAndView readStoreAdmin(Parameter<SyAdmin> parameter) throws Exception {
		SyAdmin syAdmin = parameter.get();

//		if (syAdmin.getAdminNo() != null && !"".equals(syAdmin.getAdminNo())) {
		Map<String, Object> dataMap = adminService.getAdminDetailInfo(syAdmin.getAdminNo());

		parameter.addAttribute("detailInfo", dataMap.get("detailData"));
		parameter.addAttribute("emailSiteCode", dataMap.get("emailSiteCode")); // Email Code List
//		}

		return forward("/system/admin/store/admin-detail");
	}

	/**
	 * @Desc : A-Connect 사용자 정보 등록
	 * @Method Name : createStoreAdmin
	 * @Date : 2019. 2. 15.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/create")
	@ResponseBody
	public void createStoreAdmin(Parameter<SyAdmin> parameter) throws Exception {
		SyAdmin syAdmin = parameter.get();

		syAdmin.setAuthNo(Const.ACONNECT_AUTH_NO); // A Connect 사용자 권한.
		syAdmin.setMemberInfoMgmtYn(Const.BOOLEAN_FALSE); // A Connect 개인정보 취급 여부
		syAdmin.setBatchErrorAlertYn(Const.BOOLEAN_FALSE); // Batch 알림 수신 여부
		syAdmin.setUseYn(Const.BOOLEAN_TRUE); // 사용 여부

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = adminService.setAconnectAdmin(syAdmin);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : A-Connect 사용자 정보 수정
	 * @Method Name : updateStoreAdmin
	 * @Date : 2019. 2. 18.
	 * @Author : kiowa
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/update")
	@ResponseBody
	public void updateStoreAdmin(Parameter<SyAdmin> parameter) throws Exception {
		SyAdmin syAdmin = parameter.get();

		syAdmin.setAuthNo(Const.ACONNECT_AUTH_NO); // A Connect 사용자 권한.
		syAdmin.setMemberInfoMgmtYn(Const.BOOLEAN_FALSE); // A Connect 개인정보 취급 여부
		syAdmin.setBatchErrorAlertYn(Const.BOOLEAN_FALSE); // Batch 알림 수신 여부

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = adminService.updateAdmin(syAdmin);

		writeJson(parameter, resultMap);
	}

}
