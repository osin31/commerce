package kr.co.abcmart.bo.display.repository.master;

import org.apache.ibatis.annotations.Mapper;
import kr.co.abcmart.bo.display.repository.master.base.BaseCmAreaDao;
import kr.co.abcmart.bo.display.model.master.CmArea;

@Mapper
public interface CmAreaDao extends BaseCmAreaDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BaseCmAreaDao 클래스에 구현 되어있습니다.
     * BaseCmAreaDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
     */

	public CmArea selectByPrimaryKey(CmArea cmArea) throws Exception;

}
