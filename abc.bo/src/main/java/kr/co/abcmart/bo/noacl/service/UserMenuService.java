package kr.co.abcmart.bo.noacl.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import kr.co.abcmart.bo.noacl.repository.master.UserMenuDao;
import kr.co.abcmart.bo.noacl.vo.UserMenuVO;
import kr.co.abcmart.bo.system.model.master.SyAdminAuthority;
import kr.co.abcmart.common.util.UtilsText;
import kr.co.abcmart.user.LoginManager;
import kr.co.abcmart.user.UserDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserMenuService {

	@Autowired
	private UserMenuService userMenuService;

	@Autowired
	private UserMenuDao userMenuDao;

	/**
	 *
	 * @Desc :메뉴 리스트와 즐겨찾기 등록한 메뉴 조회
	 * @Method Name : getUserMenu
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getUserMenu() throws Exception {
		UserDetails user = LoginManager.getUserDetails();

		UserMenuVO userInfo = new UserMenuVO();
		userInfo.setAdminNo(user.getAdminNo());
		userInfo.setAdminAuthorities(user.getAdminAuthorities());
		userInfo.setAuthApplySystemType(user.getAuthApplySystemType());

		// 캐시 키 생성
		String authMenuRedisKey = user.getAdminAuthorities().stream().map(SyAdminAuthority::getAuthNo)
				.collect(Collectors.joining(""));

		userInfo.setAuthMenuRedisKey(authMenuRedisKey);

		Map<String, Object> resultMap = new HashMap<>();
		List<UserMenuVO> userMenuList = userMenuService.getUserMenuList(userInfo);
		List<UserMenuVO> favoriteMenuList = userMenuService.getFavoriteMenuList(userInfo);
		resultMap.put("userMenuList", userMenuList);
		resultMap.put("favoriteMenuList", favoriteMenuList);
		return resultMap;
	}

	/**
	 *
	 * @Desc :메뉴 리스트 조회
	 * @Method Name : getUserMenuList
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	@Cacheable(value = "userMenuService.getUserMenuList", key = "#userInfo.authMenuRedisKey", unless = "#result == null")
	public List<UserMenuVO> getUserMenuList(UserMenuVO userInfo) throws Exception {
		List<UserMenuVO> userMenuList = userMenuDao.getUserMenuList(userInfo);
		for (UserMenuVO userMenu : userMenuList) {
			try {
				if (!UtilsText.isBlank(userMenu.getAllPathMenuNo())) {

					String[] menuGbnUpMenuNoArr = userMenu.getAllPathMenuNo().split("\\^");
					userMenu.setMenuGbnUpMenuNo(menuGbnUpMenuNoArr[1]);
				}

			} catch (Exception e) {
				log.debug("setMenuGbnUpMenuNo error");
			}
		}
		return userMenuList;
	}

	/**
	 *
	 * @Desc :즐겨찾기 조회
	 * @Method Name : getFavoriteMenuList
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public List<UserMenuVO> getFavoriteMenuList(UserMenuVO userInfo) throws Exception {
		List<UserMenuVO> favoriteMenuList = userMenuDao.getFavoriteMenuList(userInfo);
		return favoriteMenuList;
	}

	/**
	 *
	 * @Desc :개인별 등록한 즐겨찾기 중복체크
	 * @Method Name : selectFavoritesMenuCountByUser
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int selectFavoritesMenuCountByUser(UserMenuVO params) throws Exception {
		return userMenuDao.selectFavoritesMenuCountByUser(params);
	}

	/**
	 *
	 * @Desc :개인별 등록한 즐겨찾기 카운트
	 * @Method Name : selectFavoritesMenuCount
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int selectFavoritesMenuCount(UserMenuVO params) throws Exception {
		return userMenuDao.selectFavoritesMenuCount(params);
	}

	/**
	 *
	 * @Desc :즐겨찾기 추가
	 * @Method Name : insertFavoritesMenu
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> insertFavoritesMenu(UserMenuVO params) throws Exception {

		UserDetails user = LoginManager.getUserDetails();
		params.setAdminNo(user.getAdminNo());
		params.setAdminAuthorities(user.getAdminAuthorities());

		Map<String, Object> resultMap = new HashMap<>();
		// 중복체크
		int dupCnt = this.selectFavoritesMenuCountByUser(params);
		if (dupCnt < 1) {
			// 즐겨찾기 최대 10개까지 가능
			int favoritesMenuCount = this.selectFavoritesMenuCount(params);
			if (favoritesMenuCount < 10) {
				int resutCnt = userMenuDao.insertFavoritesMenu(params);
				if (resutCnt > 0) {
					resultMap.put("resultMsg", "즐겨찾기에 등록되었습니다");
				}

			} else {
				resultMap.put("resultMsg", "즐겨찾기는 최대 10개까지 등록 가능합니다");
			}
		} else {
			resultMap.put("resultMsg", "이미 등록된 메뉴입니다.");
		}

		List<UserMenuVO> favoriteMenuList = this.getFavoriteMenuList(params);
		resultMap.put("favoriteMenuList", favoriteMenuList);

		return resultMap;
	}

	/**
	 *
	 * @Desc :즐겨찾기 삭제
	 * @Method Name : deleteFavoritesMenu
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> deleteFavoritesMenu(UserMenuVO params) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		UserDetails user = LoginManager.getUserDetails();
		params.setAdminNo(user.getAdminNo());
		params.setAdminAuthorities(user.getAdminAuthorities());
		int resultCnt = userMenuDao.deleteFavoritesMenu(params);
		if (resultCnt > 0) {
			List<UserMenuVO> favoriteMenuList = userMenuService.getFavoriteMenuList(params);
			resultMap.put("favoriteMenuList", favoriteMenuList);
			resultMap.put("resultMsg", "즐겨찾기에 삭제되었습니다");
		}
		return resultMap;
	}

}
