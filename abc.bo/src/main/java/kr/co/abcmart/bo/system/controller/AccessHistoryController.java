package kr.co.abcmart.bo.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.abcmart.bo.cmm.service.CmLogHistoryService;
import kr.co.abcmart.bo.system.model.master.SyAdmin;
import kr.co.abcmart.bo.system.model.master.SyMenu;
import kr.co.abcmart.bo.system.model.master.SyMenuAccessHistory;
import kr.co.abcmart.bo.system.service.AdminService;
import kr.co.abcmart.bo.system.service.HistoryService;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.paging.Page;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.common.request.Parameter;
import kr.co.abcmart.constant.Const;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("system")
public class AccessHistoryController extends BaseController {
	@Autowired
	private CmLogHistoryService cmLogHistoryService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private HistoryService historyService;

	/**
	 *
	 * @Desc : URL 접근로그 화면
	 * @Method Name : syUrlAccessHistory
	 * @Date : 2019. 8. 1.
	 * @Author : 최경호
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/url-log-history")
	public ModelAndView syUrlAccessHistory(Parameter<SyAdmin> parameter) throws Exception {
		Pageable<SyAdmin, SyAdmin> pageable = new Pageable<SyAdmin, SyAdmin>(parameter);
		pageable.setUsePage(false);
		pageable.getBean().setUseYn(Const.BOOLEAN_TRUE);
		Page<SyAdmin> syAdmin = adminService.getAdminList(pageable);
		List<SyAdmin> data = syAdmin.getContent();

		parameter.addAttribute("ADMIN_DATA", data);

		return forward("/cmm/log-history/url-log-history-main");
	}

	/**
	 *
	 * @Desc : URL 접근로그 리스트 조회
	 * @Method Name : cmMessageSendHistoryList
	 * @Date : 2019. 3. 20.
	 * @Author : 최경호
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/url-log-history/read-list")
	public void syUrlAccessHistoryList(Parameter<SyMenuAccessHistory> parameter) throws Exception {
		Pageable<SyMenuAccessHistory, SyMenuAccessHistory> pageable = new Pageable<>(parameter);
		Page<SyMenuAccessHistory> page = historyService.getUrlAccessHistoryList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 *
	 * @Desc : 접근로그 - MENU
	 * @Method Name : syMenuAccessHistory
	 * @Date : 2019. 8. 1.
	 * @Author : 최경호
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/menu-log-history")
	public ModelAndView menuLogHistoryMain(Parameter<SyAdmin> parameter) throws Exception {
		Pageable<SyAdmin, SyAdmin> pageable = new Pageable<SyAdmin, SyAdmin>(parameter);
		pageable.setUsePage(false);
		pageable.getBean().setUseYn(Const.BOOLEAN_TRUE);
		Page<SyAdmin> syAdmin = adminService.getAdminList(pageable);
		List<SyAdmin> data = syAdmin.getContent();

		parameter.addAttribute("ADMIN_DATA", data);

		SyMenu syMenu = parameter.create(SyMenu.class);
		syMenu.setUpMenuNo("10000");
		syMenu.setDispYn("Y");
		syMenu.setUseYn("Y");
		parameter.addAttribute("MENU_DATA", historyService.getMenuData(syMenu));

		return forward("/cmm/log-history/menu-log-history-main");
	}

	/**
	 *
	 * @Desc : MENU 접근로그 리스트 조회
	 * @Method Name : syMenuAccessHistoryList
	 * @Date : 2019. 8. 6.
	 * @Author : 최경호
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("menu-log-history/read-list")
	public void syMenuAccessHistoryList(Parameter<SyMenuAccessHistory> parameter) throws Exception {
		Pageable<SyMenuAccessHistory, SyMenuAccessHistory> pageable = new Pageable<>(parameter);
		Page<SyMenuAccessHistory> page = historyService.getMenuAccessHistoryList(pageable);

		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 접근 이력 메뉴 정보 조회
	 * @Method Name : menuList
	 * @Date : 2019. 8. 6.
	 * @Author : 최경호
	 * @param parameter
	 */
	@PostMapping("/menu")
	@ResponseBody
	public void menuList(Parameter<SyMenu> parameter) throws Exception {

		List<SyMenu> syMenuList = historyService.getMenuData(parameter.get());
		writeJson(parameter, syMenuList);
	}

	/**
	 *
	 * @Desc : 접근로그 - MENU 상세 팝업
	 * @Method Name : syMenuAccessHistoryDetail
	 * @Date : 2019. 8. 6.
	 * @Author : 최경호
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/menu-log-history-detail")
	public ModelAndView syMenuAccessHistoryDetail(Parameter<SyAdmin> parameter) throws Exception {
		Pageable<SyAdmin, SyAdmin> pageable = new Pageable<SyAdmin, SyAdmin>(parameter);
		pageable.setUsePage(false);
		pageable.getBean().setUseYn(Const.BOOLEAN_TRUE);
		Page<SyAdmin> syAdmin = adminService.getAdminList(pageable);
		List<SyAdmin> data = syAdmin.getContent();

		parameter.addAttribute("ADMIN_DATA", data);

		SyMenuAccessHistory syMenuAccessHistory = parameter.create(SyMenuAccessHistory.class);

		Map<String, Object> MapData = historyService.getMenuDataList(syMenuAccessHistory);
		List<SyMenu> menuList = (List<SyMenu>)MapData.get("menuList");
		List<SyMenu> selectList = (List<SyMenu>)MapData.get("selectList");

		for(int i=0; i<menuList.size(); i++) {
			System.out.println("menuList >>>" + menuList.get(i));
			parameter.addAttribute("MENU_DATA_"+(i+1), menuList.get(i));
		}

		for(int i=0; i<selectList.size(); i++) {
			parameter.addAttribute("SELECT_DATA_"+(i+1), selectList.get(i));
		}

		return forward("/cmm/log-history/menu-log-history-detail");
	}

	/**
	 *
	 * @Desc : 접근로그 - MENU 상세 데이터 조회
	 * @Method Name : syMenuAccessHistoryDetailPop
	 * @Date : 2019. 8. 7.
	 * @Author : 최경호
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/menu-log-history-detail/read-list")
	public void syMenuAccessHistoryDetailPop(Parameter<SyMenuAccessHistory> parameter) throws Exception {
		Pageable<SyMenuAccessHistory, SyMenuAccessHistory> pageable = new Pageable<SyMenuAccessHistory, SyMenuAccessHistory>(parameter);
		Page<SyMenuAccessHistory> page = historyService.getMenuAccessHistoryDetailPop(pageable);
		writeJson(parameter, page.getGrid());
	}

	/**
	 * @Desc : 관리자별 URI별 접근로그
	 * @Method Name : adminMenuLogHistoryMain
	 * @Date : 2019. 8. 21.
	 * @Author : 최경호
	 * @param parameter
	 * @throws Exception
	 */
	@GetMapping("/admin-log-history")
	public ModelAndView adminLogHistoryMain(Parameter<SyAdmin> parameter) throws Exception {
		Pageable<SyAdmin, SyAdmin> pageable = new Pageable<SyAdmin, SyAdmin>(parameter);

		SyAdmin admin = pageable.getBean();
		admin.setSearchType("STATS");
		pageable.setBean(admin);

		pageable.setUsePage(false);
		pageable.getBean().setUseYn(Const.BOOLEAN_TRUE);
		Page<SyAdmin> syAdmin = adminService.getAdminList(pageable);
		List<SyAdmin> data = syAdmin.getContent();

		parameter.addAttribute("ADMIN_DATA", data);

		SyMenu syMenu = parameter.create(SyMenu.class);
		syMenu.setUpMenuNo("10000");
		syMenu.setDispYn("Y");
		syMenu.setUseYn("Y");
		parameter.addAttribute("MENU_DATA", historyService.getMenuData(syMenu));

		return forward("/cmm/log-history/admin-log-history-main");
	}

	/**
	 *
	 * @Desc : 관리자별 URI별 접근로그 조회
	 * @Method Name : syMenuAccessHistoryList
	 * @Date : 2019. 8. 6.
	 * @Author : 최경호
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("admin-log-history/read-list")
	public void adminLogHistoryList(Parameter<SyMenuAccessHistory> parameter) throws Exception {
//		Pageable<SyMenuAccessHistory, Map<String, Object>> pageable = new Pageable<>(parameter);
		SyMenuAccessHistory syMenuAccessHistory = parameter.get();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = historyService.getAdminAccessHistoryList(syMenuAccessHistory);

		dataMap.put("data", list);
		writeJson(parameter, dataMap);
	}

	/**
	 *
	 * @Desc : 접근로그 - ADMIN 상세 팝업
	 * @Method Name : syMenuAccessHistoryDetail
	 * @Date : 2019. 8. 6.
	 * @Author : 최경호
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/admin-log-history-detail")
	public ModelAndView syAdminAccessHistoryDetail(Parameter<SyAdmin> parameter) throws Exception {
		Pageable<SyAdmin, SyAdmin> pageable = new Pageable<SyAdmin, SyAdmin>(parameter);
		pageable.setUsePage(false);
		pageable.getBean().setUseYn(Const.BOOLEAN_TRUE);
		Page<SyAdmin> syAdmin = adminService.getAdminList(pageable);
		List<SyAdmin> data = syAdmin.getContent();

		parameter.addAttribute("ADMIN_DATA", data);

//		SyMenuAccessHistory syMenuAccessHistory = parameter.create(SyMenuAccessHistory.class);

//		Map<String, Object> MapData = historyService.getMenuDataList(syMenuAccessHistory);
//		List<SyMenu> menuList = (List<SyMenu>)MapData.get("menuList");
//		List<SyMenu> selectList = (List<SyMenu>)MapData.get("selectList");

//		for(int i=0; i<menuList.size(); i++) {
//			System.out.println("menuList >>>" + menuList.get(i));
//			parameter.addAttribute("MENU_DATA_"+(i+1), menuList.get(i));
//		}
//
//		for(int i=0; i<selectList.size(); i++) {
//			parameter.addAttribute("SELECT_DATA_"+(i+1), selectList.get(i));
//		}

		return forward("/cmm/log-history/admin-log-history-detail");
	}


	/**
	 *
	 * @Desc : 접근로그 - ADMIN 상세 데이터 조회
	 * @Method Name : syAdminAccessHistoryDetailPop
	 * @Date : 2019. 8. 28.
	 * @Author : 최경호
	 * @param parameter
	 * @throws Exception
	 */
	@PostMapping("/admin-log-history-detail/read-list")
	public void syAdminAccessHistoryDetailPop(Parameter<SyMenuAccessHistory> parameter) throws Exception {
		Pageable<SyMenuAccessHistory, SyMenuAccessHistory> pageable = new Pageable<SyMenuAccessHistory, SyMenuAccessHistory>(parameter);
		Page<SyMenuAccessHistory> page = historyService.getAdminAccessHistoryDetailPop(pageable);
		writeJson(parameter, page.getGrid());
	}
}