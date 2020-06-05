package kr.co.abcmart.bo.system.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.system.model.master.SyAdminFavorites;
import kr.co.abcmart.bo.system.repository.master.base.BaseSyAdminFavoritesDao;

@Mapper
public interface SyAdminFavoritesDao extends BaseSyAdminFavoritesDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSyAdminFavoritesDao 클래스에 구현 되어있습니다.
	 * BaseSyAdminFavoritesDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public SyAdminFavorites selectByPrimaryKey(SyAdminFavorites syAdminFavorites) throws Exception;

	public void deleteFavoritesMenu(SyAdminFavorites syAdminFavorites) throws Exception;

}
