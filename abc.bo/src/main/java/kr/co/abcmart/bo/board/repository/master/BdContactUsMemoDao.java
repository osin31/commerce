package kr.co.abcmart.bo.board.repository.master;

import org.apache.ibatis.annotations.Mapper;
import kr.co.abcmart.bo.board.repository.master.base.BaseBdContactUsMemoDao;
import kr.co.abcmart.bo.board.model.master.BdContactUsMemo;

@Mapper
public interface BdContactUsMemoDao extends BaseBdContactUsMemoDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BaseBdContactUsMemoDao 클래스에 구현 되어있습니다.
     * BaseBdContactUsMemoDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
     */

	public BdContactUsMemo selectByPrimaryKey(BdContactUsMemo bdContactUsMemo) throws Exception;

}
