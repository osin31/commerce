package kr.co.abcmart.bo.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.system.model.master.SyAdminFavorites;
import kr.co.abcmart.bo.system.model.master.SyAdminUseHistory;
import kr.co.abcmart.bo.system.model.master.SyAuthorityMenu;
import kr.co.abcmart.bo.system.model.master.SyMenu;
import kr.co.abcmart.bo.system.repository.master.SyAdminFavoritesDao;
import kr.co.abcmart.bo.system.repository.master.SyAdminUseHistoryDao;
import kr.co.abcmart.bo.system.repository.master.SyAuthorityMenuDao;
import kr.co.abcmart.bo.system.repository.master.SyMenuDao;
import kr.co.abcmart.constant.Const;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import kr.co.abcmart.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MenuService {

	@Autowired
	SyMenuDao syMenuDao;

	@Autowired
	SyAuthorityMenuDao syAuthorityMenuDao;

	@Autowired
	SyAdminFavoritesDao syAdminFavoritesDao;

	@Autowired
	SyAdminUseHistoryDao syAdminUseHistoryDao;

	/**
	 *
	 * @Desc :권한적용시스템별 메뉴조회
	 * @Method Name : getMenuList
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMenuList(SyMenu params) throws Exception {
		Map<String, Object> resultData = new HashMap<>();

		List<SyMenu> data = syMenuDao.selectMenuList(params);
		resultData.put("Total", 1);
		resultData.put("Data", data);

		return resultData;
	}

	/**
	 *
	 * @Desc :메뉴저장(저장시 캐시 삭제)
	 * @Method Name : setMenu
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@CacheEvict(value = { "userMenuService.getUserMenuList" }, allEntries = true)
	public void setMenu(SyMenu params) throws Exception {
		UserDetails user = LoginManager.getUserDetails();
		params.setRgsterNo(user.getAdminNo());
		params.setModerNo(user.getAdminNo());

		if (UtilsText.equals(params.getStatus(), Const.CRUD_C)) {
			syMenuDao.insertMenu(params);
		} else if (UtilsText.equals(params.getStatus(), Const.CRUD_U)) {
			syMenuDao.updateMenu(params);
		}
	}

	/**
	 *
	 * @Desc :메뉴삭제(삭제시 캐시삭제)
	 * @Method Name : deleteMenu
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@CacheEvict(value = { "userMenuService.getUserMenuList" }, allEntries = true)
	public void deleteMenu(SyMenu params) throws Exception {
		SyAuthorityMenu syAuthorityMenu = new SyAuthorityMenu();
		syAuthorityMenu.setMenuNo(params.getMenuNo());
		syAuthorityMenuDao.deleteMenu(syAuthorityMenu);

		SyAdminFavorites syAdminFavorites = new SyAdminFavorites();
		syAdminFavorites.setMenuNo(params.getMenuNo());
		syAdminFavoritesDao.deleteFavoritesMenu(syAdminFavorites);

		SyAdminUseHistory syAdminUseHistory = new SyAdminUseHistory();
		syAdminUseHistory.setMenuNo(params.getMenuNo());
		syAdminUseHistoryDao.deleteAdminUseHistory(syAdminUseHistory);

		syMenuDao.delete(params);
	}

}
