package kr.co.abcmart.bo.noacl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.abcmart.bo.noacl.service.UserMenuService;
import kr.co.abcmart.bo.noacl.vo.UserMenuVO;
import kr.co.abcmart.common.controller.BaseController;
import kr.co.abcmart.common.request.Parameter;
import lombok.extern.slf4j.Slf4j;

/**
 * security acl 검증 에서 제외되는 공통영역 컨트롤러
 *
 * @author 유성민
 *
 */
@Slf4j
@Controller
@RequestMapping("noacl")
public class UserMenuController extends BaseController {

	@Autowired
	private UserMenuService userMenuService;

	/**
	 * @Desc :메뉴 리스트와 즐겨찾기 등록한 메뉴 조회
	 * @Method Name : userMenu
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/user-menu")
	@ResponseBody
	public void userMenu(Parameter<?> parameter) throws Exception {

		writeJson(parameter, userMenuService.getUserMenu());
	}

	/**
	 * @Desc : 즐겨찾기 추가
	 * @Method Name : favoritesMenuCreate
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/favorites-menu/create")
	@ResponseBody
	public void favoritesMenuCreate(Parameter<UserMenuVO> parameter) throws Exception {
		UserMenuVO params = parameter.get();

		writeJson(parameter, userMenuService.insertFavoritesMenu(params));
	}

	/**
	 *
	 * @Desc : 즐겨찾기 삭제
	 * @Method Name : favoritesMenuRemove
	 * @Date : 2019. 1. 31.
	 * @Author : 유성민
	 * @param parameter
	 * @throws Exception
	 */
	@RequestMapping("/favorites-menu/remove")
	@ResponseBody
	public void favoritesMenuRemove(Parameter<UserMenuVO> parameter) throws Exception {
		UserMenuVO params = parameter.get();
		writeJson(parameter, userMenuService.deleteFavoritesMenu(params));
	}

}