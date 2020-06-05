package kr.co.abcmart.bo.board.repository.master;

import org.apache.ibatis.annotations.Mapper;
import kr.co.abcmart.bo.board.repository.master.base.BaseBdCorprBoardDao;
import kr.co.abcmart.bo.board.model.master.BdCorprBoard;

@Mapper
public interface BdCorprBoardDao extends BaseBdCorprBoardDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BaseBdCorprBoardDao 클래스에 구현 되어있습니다.
     * BaseBdCorprBoardDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
     */

	public BdCorprBoard selectByPrimaryKey(BdCorprBoard bdCorprBoard) throws Exception;

}
