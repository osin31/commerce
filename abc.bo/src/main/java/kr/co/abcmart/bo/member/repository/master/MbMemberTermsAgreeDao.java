package kr.co.abcmart.bo.member.repository.master;

import org.apache.ibatis.annotations.Mapper;
import kr.co.abcmart.bo.member.repository.master.base.BaseMbMemberTermsAgreeDao;
import kr.co.abcmart.bo.member.model.master.MbMemberTermsAgree;

@Mapper
public interface MbMemberTermsAgreeDao extends BaseMbMemberTermsAgreeDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BaseMbMemberTermsAgreeDao 클래스에 구현 되어있습니다.
     * BaseMbMemberTermsAgreeDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
     */

	public MbMemberTermsAgree selectByPrimaryKey(MbMemberTermsAgree mbMemberTermsAgree) throws Exception;

}
