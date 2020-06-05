package kr.co.abcmart.bo.system.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.bo.system.model.master.SyAdminChangeHistory;
import kr.co.abcmart.bo.system.model.master.SyAdminUseHistory;
import kr.co.abcmart.bo.system.model.master.SyAuthority;
import kr.co.abcmart.bo.system.model.master.SyAuthorityChangeHistory;
import kr.co.abcmart.bo.system.model.master.SyAuthorityMenu;
import kr.co.abcmart.bo.system.service.AdminService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.util.UtilsText;

@Controller
@RequestMapping("system/admin")
public class AdminController extends BaseController {

	@Autowired
	private AdminService adminService;

	/**
	 * @Desc : 관리자 목록화면 호출
	 * @Method Name : getAdminIndex
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin")
	public ModelAndView getAdminIndex(Parameter<?> parameter) throws Exception {
		// 목록화면에서 필요한 검색조건인 사이트 목록과 권한 그룹을 조회
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = adminService.getAdminDetailCodeData();
		parameter.addAttribute("authGroup", resultMap.get("authGroup"));

		return forward("/system/admin/admin/admin-main");
	}

	/**
	 * @Desc : 관리자 목록 데이터를 조회한다.
	 * @Method Name : readList
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/admin/read-list")
	@ResponseBody
	public void readList(Parameter<SyAdmin> parameter) throws Exception {
		Pageable<SyAdmin, SyAdmin> adminVOPageble = new Pageable<>(parameter);
		Page<SyAdmin> page = adminService.getAdminList(adminVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 관리자 등록 팝업 화면 호출
	 * @Method Name : createPop
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/create-pop")
	public ModelAndView createPop(Parameter<SyAuthority> parameter) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap = adminService.getAdminDetailCodeData();
		parameter.addAttribute("emailSiteCode", resultMap.get("emailSiteCode"));
		parameter.addAttribute("authGroup", resultMap.get("authGroup"));

		return forward("/system/admin/admin/create-admin-form-pop");
	}

	/**
	 * @Desc : 관리자 권한 조회
	 * @Method Name : readAuthority
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/admin/read-authority")
	@ResponseBody
	public void readAuthority(Parameter<SyAuthority> parameter) throws Exception {
		SyAuthority params = parameter.get();
		List<SyAuthority> result = adminService.getAuthorytyList(params);

		writeJson(parameter, result);
	}

	/**
	 * @Desc : 관리자 등록
	 * @Method Name : create
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/admin/create")
	@ResponseBody
	public void create(Parameter<SyAdmin> parameter) throws Exception {
		SyAdmin params = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = adminService.setAdmin(params);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 관리자 권한 그룹변경 팝업 화면 호출
	 * @Method Name : updateAdminAuthorityPop
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/update-admin-authority-pop")
	public ModelAndView updateAdminAuthorityPop(Parameter<SyAuthority> parameter) throws Exception {
		SyAuthority params = parameter.get();
		String adminNoStr = parameter.getString("adminNoStr");
		String[] arrayStr = adminNoStr.split(",");
		parameter.addAttribute("adminNoStr", arrayStr);
		parameter.addAttribute("authGroup", adminService.getAuthorytyList(params));

		return forward("/system/admin/admin/update-admin-authority-pop");
	}

	/**
	 * @Desc : 관리자 상세 팝업 호출
	 * @Method Name : adminDetailPop
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin/admin-detail-pop")
	public ModelAndView adminDetailPop(Parameter<SyAdmin> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String adminNo = "";

		adminNo = parameter.getString("adminNo");

		// 관리자 상세 관련 데이터 조회
		dataMap = adminService.getAdminDetailInfo(adminNo);

		parameter.addAttribute("authGroup", dataMap.get("authGroup"));
		parameter.addAttribute("emailSiteCode", dataMap.get("emailSiteCode"));
		parameter.addAttribute("detailInfo", dataMap.get("detailData"));
		parameter.addAttribute("accessIpData", dataMap.get("accessIpData"));

		return forward("/system/admin/admin/admin-detail-pop");
	}

	/**
	 * @Desc : 관리자 상세 데이터 수정
	 * @Method Name : updateAdmin
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/admin/update")
	@ResponseBody
	public void updateAdmin(Parameter<SyAdmin> parameter) throws Exception {
		SyAdmin params = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = adminService.updateAdmin(params);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 관리자 상세 데이터 수정 (본인계정 수정)
	 * @Method Name : updateAdminAccount
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/admin/update-account")
	@ResponseBody
	public void updateAdminAccount(Parameter<SyAdmin> parameter) throws Exception {
		SyAdmin params = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = adminService.updateAdminAccount(params);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 관리자 비밀번호 초기화
	 * @Method Name : updatePswdReset
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/admin/update-pswd-reset")
	@ResponseBody
	public void updatePswdReset(Parameter<SyAdmin> parameter) throws Exception {
		SyAdmin params = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = adminService.updatePswdReset(params);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 관리자 권한 그룹 변경
	 * @Method Name : updateAdminAuthority
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/admin/update-admin-authority")
	@ResponseBody
	public void updateAdminAuthority(Parameter<?> parameter) throws Exception {
		String authNo = "";

		Map<String, Object> resultMap = new HashMap<String, Object>();

		authNo = parameter.getString("authNo");
		SyAdmin[] params = parameter.createArray(SyAdmin.class, "adminNo", Arrays.asList("arrayAdminNo", "authNo"));

		try {
			adminService.setAdminAuthorityList(params, authNo);

			resultMap.put("Message", "저장되었습니다.");
		} catch (Exception e) {
			resultMap.put("Message", "저장에 실패하였습니다.");
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 관리자 상세 - 로그인 이력 조회
	 * @Method Name : readAdminUseHistory
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/admin/read-admin-use-history")
	@ResponseBody
	public void readAdminUseHistory(Parameter<SyAdminUseHistory> parameter) throws Exception {

		Pageable<SyAdminUseHistory, SyAdminUseHistory> adminVOPageble = new Pageable<>(parameter);
		Page<SyAdminUseHistory> page = adminService.getAdminUseHistoryList(adminVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 관리자 상세 - 변경이력 조회
	 * @Method Name : readAdminChangeHistory
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/admin/read-admin-change-history")
	@ResponseBody
	public void readAdminChangeHistory(Parameter<SyAdminChangeHistory> parameter) throws Exception {

		Pageable<SyAdminChangeHistory, SyAdminChangeHistory> adminVOPageble = new Pageable<>(parameter);
		Page<SyAdminChangeHistory> page = adminService.getAdminChangeHistoryList(adminVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 관리자 권한그룹 관리화면 호출
	 * @Method Name : getAuthorityMain
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/authority")
	public ModelAndView getAuthorityMain(Parameter<?> parameter) throws Exception {
		return forward("/system/admin/authority/main");
	}

	/**
	 * @Desc : 관리자 권한그룹 목록 호출
	 * @Method Name : authorityGroupReadList
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/authority/read-list")
	@ResponseBody
	public void authorityGroupReadList(Parameter<SyAuthority> parameter) throws Exception {

		Pageable<SyAuthority, SyAuthority> adminVOPageble = new Pageable<>(parameter);

		Page<SyAuthority> page = adminService.getAuthorityGroupList(adminVOPageble);
		writeJson(parameter, page.getGrid());

	}

	/**
	 * @Desc : 관리자 권한그룹 등록/수정화면 호출
	 * @Method Name : authorityUpatePop
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/authority/update-pop")
	public ModelAndView authorityUpatePop(Parameter<SyAuthority> parameter) throws Exception {
		SyAuthority params = parameter.get();
		parameter.addAttribute("status", params.getStatus());
		if (UtilsText.equals(params.getStatus(), Const.CRUD_U)) {
			SyAuthority authGroup = adminService.getAuthorytyList(params).stream().findFirst().orElse(null);
			parameter.addAttribute("authGroup", authGroup);
		}

		return forward("/system/admin/authority/update-pop");
	}

	/**
	 * @Desc : 관리자 권한그룹 등록/수정
	 * @Method Name : authorityUpdate
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/authority/update")
	@ResponseBody
	public void authorityUpdate(Parameter<SyAuthority> parameter) throws Exception {
		SyAuthority params = parameter.get();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = adminService.updateAuthorityGroup(params);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 관리자 권한그룹 삭제
	 * @Method Name : authorityDelete
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/authority/delete")
	@ResponseBody
	public void authorityDelete(Parameter<SyAuthority> parameter) throws Exception {
		SyAuthority params = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (!parameter.hasRole(Const.ROLE_ADMIN)) {
			resultMap.put("Message", "삭제 권한이 없습니다.");
		} else {
			resultMap = adminService.deleteAuthorityGroup(params);
		}

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 관리자 권한그룹 변경이력화면 호출
	 * @Method Name : authorityHistoryPop
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/authority/history-pop")
	public ModelAndView authorityHistoryPop(Parameter<SyAuthority> parameter) throws Exception {
		SyAuthority params = parameter.get();
		parameter.addAttribute("authGroup", adminService.getAuthorytyList(params));

		return forward("/system/admin/authority/history-pop");
	}

	/**
	 * @Desc : 관리자 권한그룹 변경이력 조회
	 * @Method Name : authorityHistoryReadList
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/authority/history-read-list")
	@ResponseBody
	public void authorityHistoryReadList(Parameter<SyAuthorityChangeHistory> parameter) throws Exception {

		Pageable<SyAuthorityChangeHistory, SyAuthorityChangeHistory> adminVOPageble = new Pageable<>(parameter);
		Page<SyAuthorityChangeHistory> page = adminService.getAuthorityGroupHistoryList(adminVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 관리자 권한그룹 권한메뉴화면 호출
	 * @Method Name : authorityMenuPop
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/authority/menu-pop")
	public ModelAndView authorityMenuPop(Parameter<SyAuthority> parameter) throws Exception {
		SyAuthority params = parameter.get();
		parameter.addAttribute("params", params);

		return forward("/system/admin/authority/menu-pop");
	}

	/**
	 * @Desc : 관리자 권한그룹 권한메뉴화면 호출
	 * @Method Name : authorityMenuReadList
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/authority/menu-read-list")
	@ResponseBody
	public void authorityMenuReadList(Parameter<SyAuthorityMenu> parameter) throws Exception {
		SyAuthorityMenu params = parameter.get();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = adminService.getAuthorityGroupMenuList(params);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 관리자 권한메뉴 수정
	 * @Method Name : authorityMenuUpdate
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/authority/menu-update")
	@ResponseBody
	public void authorityMenuUpdate(Parameter<?> parameter) throws Exception {

		SyAuthorityMenu[] params = parameter.createArray(SyAuthorityMenu.class, "menuNo");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		int cnt = 0;
		try {
			cnt = adminService.updateAuthorityGroupMenuList(params);

			resultMap.put("Code", cnt);
			resultMap.put("Message", "저장되었습니다.");
		} catch (Exception e) {
			resultMap.put("Code", cnt);
			resultMap.put("Message", "저장에 실패하였습니다.");
		}

		writeJson(parameter, resultMap);
	}

}
