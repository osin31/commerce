package kr.co.abcmart.bo.noacl.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.noacl.vo.UserMenuVO;

@Mapper
public interface UserMenuDao {

	public List<UserMenuVO> getUserMenuList(UserMenuVO userInfo) throws Exception;

	public List<UserMenuVO> getFavoriteMenuList(UserMenuVO userInfo) throws Exception;

	public int insertFavoritesMenu(UserMenuVO params) throws Exception;

	public int deleteFavoritesMenu(UserMenuVO params) throws Exception;

	public int selectFavoritesMenuCount(UserMenuVO params) throws Exception;

	public int selectFavoritesMenuCountByUser(UserMenuVO params) throws Exception;
	

}
