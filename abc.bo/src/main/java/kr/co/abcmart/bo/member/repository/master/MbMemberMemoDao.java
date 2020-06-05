package kr.co.abcmart.bo.member.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.member.model.master.MbMemberMemo;
import kr.co.abcmart.bo.member.repository.master.base.BaseMbMemberMemoDao;

@Mapper
public interface MbMemberMemoDao extends BaseMbMemberMemoDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BaseMbMemberMemoDao 클래스에 구현 되어있습니다.
     * BaseMbMemberMemoDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
     */

	public MbMemberMemo selectByPrimaryKey(MbMemberMemo mbMemberMemo) throws Exception;
	
	public List<MbMemberMemo> selectMemberMemoData(MbMemberMemo mbMemberMemo) throws Exception;
	
	public int deleteMemberMemo(MbMemberMemo mbMemberMemo) throws Exception;
	
	public void insertMemberMemo(MbMemberMemo mbMemberMemo) throws Exception;

}
