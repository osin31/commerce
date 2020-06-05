package kr.co.abcmart.bo.member.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.repository.master.base.BaseMbMemberDao;
import kr.co.abcmart.bo.member.vo.MemberSearchVO;
import kr.co.abcmart.common.paging.Pageable;
import kr.co.abcmart.trace.SQLTrace;

@Mapper
public interface MbMemberDao extends BaseMbMemberDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseMbMemberDao 클래스에 구현 되어있습니다.
	 * BaseMbMemberDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 */

	public MbMember selectByPrimaryKey(MbMember mbMember) throws Exception;

	public int selectMemberListCount(Pageable<MbMember, MbMember> pageable) throws Exception;

	public List<MbMember> selectMemberList(Pageable<MbMember, MbMember> pageable) throws Exception;

	public MbMember selectMemberDefalutInfo(String memberNo) throws Exception;

	public MbMember selectMemberDefalutInfoParams(MbMember mbMember) throws Exception;

	public int update(MbMember mbMember) throws Exception;

	public int selectMemberSubListCount(Pageable<MemberSearchVO, MbMember> pageable) throws Exception;

	public List<MbMember> selectMemberSubList(Pageable<MemberSearchVO, MbMember> pageable) throws Exception;

	public int insertMenuAccessLogging(SQLTrace sqlTrace) throws Exception;

	public int selectCountByMemberNo(String memberNo) throws Exception;

	public List<MbMember> selectAppPushMemberList(MbMember mbMember) throws Exception;

	public List<MbMember> selectMemberStatusCount() throws Exception;

	public int updateDefaultMemberInfo(MbMember mbMember) throws Exception;

	public int updateLeave(MbMember mbMember) throws Exception;
}
