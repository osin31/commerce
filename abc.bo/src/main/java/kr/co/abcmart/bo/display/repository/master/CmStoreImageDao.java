package kr.co.abcmart.bo.display.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.CmStoreImage;
import kr.co.abcmart.bo.display.repository.master.base.BaseCmStoreImageDao;

@Mapper
public interface CmStoreImageDao extends BaseCmStoreImageDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseCmStoreImageDao 클래스에 구현 되어있습니다.
	 * BaseCmStoreImageDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public CmStoreImage selectByPrimaryKey(CmStoreImage cmStoreImage) throws Exception;

	public void updateCmStoreImage(CmStoreImage cmStoreImage) throws Exception;

}
