package kr.co.abcmart.bo.vendor.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.bo.system.model.master.SyAdminChangeHistory;
import kr.co.abcmart.bo.system.model.master.SyAdminUseHistory;
import kr.co.abcmart.bo.system.model.master.SyAuthority;
import kr.co.abcmart.bo.system.service.AdminService;
import kr.co.abcmart.bo.vendor.service.VendorAdminService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.Const;

@Controller
@RequestMapping("vendor/admin")
public class VendorAdminController extends BaseController {

	@Autowired
	private VendorAdminService vendorAdminService;

	@Autowired
	private AdminService adminService;

	/**
	 * @Desc : PO 관리자 목록화면 호출
	 * @Method Name : getAdminIndex
	 * @Date : 2019. 9. 30.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin")
	public ModelAndView getAdminIndex(Parameter<?> parameter) throws Exception {
		// 목록화면에서 필요한 검색조건인 사이트 목록과 권한 그룹을 조회
		Map<String, Object> resultMap = new HashMap<>();

		resultMap = vendorAdminService.getAdminDetailCodeData();
		parameter.addAttribute("authGroup", resultMap.get("authGroup"));

		return forward("/vendor/admin/admin-main");
	}

	/**
	 * @Desc : PO 관리자 목록 데이터를 조회한다.
	 * @Method Name : readList
	 * @Date : 2019. 9. 30.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-list")
	@ResponseBody
	public void readList(Parameter<SyAdmin> parameter) throws Exception {
		Pageable<SyAdmin, SyAdmin> adminVOPageble = new Pageable<>(parameter);
		Page<SyAdmin> page = vendorAdminService.getAdminList(adminVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : PO 관리자 상세 팝업 호출
	 * @Method Name : adminDetailPop
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/admin-detail-pop")
	public ModelAndView adminDetailPop(Parameter<SyAdmin> parameter) throws Exception {
		Map<String, Object> dataMap = new HashMap<>();
		String adminNo = "";

		adminNo = parameter.getString("adminNo");

		// 관리자 상세 관련 데이터 조회
		dataMap = vendorAdminService.getAdminDetailInfo(adminNo);

		parameter.addAttribute("authGroup", dataMap.get("authGroup"));
		parameter.addAttribute("emailSiteCode", dataMap.get("emailSiteCode"));
		parameter.addAttribute("detailInfo", dataMap.get("detailData"));
		parameter.addAttribute("accessIpData", dataMap.get("accessIpData"));

		return forward("/vendor/admin/admin-detail-pop");
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
	@RequestMapping("/update-admin-authority-pop")
	public ModelAndView updateAdminAuthorityPop(Parameter<SyAuthority> parameter) throws Exception {
		SyAuthority params = parameter.get();
		params.setAuthApplySystemType(Const.AUTH_APPLY_SYSTEM_PO);
		String adminNoStr = parameter.getString("adminNoStr");
		String[] arrayStr = adminNoStr.split(",");
		parameter.addAttribute("adminNoStr", arrayStr);
		parameter.addAttribute("authGroup", adminService.getAuthorytyList(params));

		return forward("/system/admin/admin/update-admin-authority-pop");
	}

	/**
	 * @Desc : 관리자 권한 그룹 변경
	 * @Method Name : updateAdminAuthority
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/update-admin-authority")
	@ResponseBody
	public void updateAdminAuthority(Parameter<?> parameter) throws Exception {
		String authNo = "";

		Map<String, Object> resultMap = new HashMap<>();

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
	 * @Desc : 관리자 권한 조회
	 * @Method Name : readAuthority
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/read-authority")
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
	@RequestMapping("/create")
	@ResponseBody
	public void create(Parameter<SyAdmin> parameter) throws Exception {
		SyAdmin params = parameter.get();

		Map<String, Object> resultMap = new HashMap<>();
		resultMap = adminService.setAdmin(params);

		writeJson(parameter, resultMap);
	}

	/**
	 * @Desc : 관리자 상세 데이터 수정
	 * @Method Name : updateAdmin
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/update")
	@ResponseBody
	public void updateAdmin(Parameter<SyAdmin> parameter) throws Exception {
		SyAdmin params = parameter.get();

		Map<String, Object> resultMap = new HashMap<>();
		resultMap = adminService.updateAdmin(params);

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
	@RequestMapping("/read-admin-use-history")
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
	@RequestMapping("/read-admin-change-history")
	@ResponseBody
	public void readAdminChangeHistory(Parameter<SyAdminChangeHistory> parameter) throws Exception {

		Pageable<SyAdminChangeHistory, SyAdminChangeHistory> adminVOPageble = new Pageable<>(parameter);
		Page<SyAdminChangeHistory> page = adminService.getAdminChangeHistoryList(adminVOPageble);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 관리자 비밀번호 초기화
	 * @Method Name : updatePswdReset
	 * @Date : 2019. 1. 31.
	 * @Author : 이동엽
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/update-pswd-reset")
	@ResponseBody
	public void updatePswdReset(Parameter<SyAdmin> parameter) throws Exception {
		SyAdmin params = parameter.get();

		Map<String, Object> resultMap = new HashMap<>();
		resultMap = adminService.updatePswdReset(params);

		writeJson(parameter, resultMap);
	}
}
