package kr.co.abcmart.bo.stats.repository.master;

import org.apache.ibatis.annotations.Mapper;
import kr.co.abcmart.bo.stats.repository.master.base.BaseSaMemberJoinStatusDao;
import kr.co.abcmart.bo.stats.model.master.SaMemberJoinStatus;

@Mapper
public interface SaMemberJoinStatusDao extends BaseSaMemberJoinStatusDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BaseSaMemberJoinStatusDao 클래스에 구현 되어있습니다.
     * BaseSaMemberJoinStatusDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
     */

	public SaMemberJoinStatus selectByPrimaryKey(SaMemberJoinStatus saMemberJoinStatus) throws Exception;

}
