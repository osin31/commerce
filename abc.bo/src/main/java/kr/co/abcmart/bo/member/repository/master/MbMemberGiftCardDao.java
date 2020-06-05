package kr.co.abcmart.bo.member.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.member.model.master.MbMemberGiftCard;
import kr.co.abcmart.bo.member.repository.master.base.BaseMbMemberGiftCardDao;

@Mapper
public interface MbMemberGiftCardDao extends BaseMbMemberGiftCardDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseMbMemberGiftCardDao 클래스에 구현 되어있습니다.
	 * BaseMbMemberGiftCardDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public MbMemberGiftCard selectByPrimaryKey(MbMemberGiftCard mbMemberGiftCard) throws Exception;

	public List<MbMemberGiftCard> selectGiftCardData(MbMemberGiftCard mbMemberGiftCard) throws Exception;
	
	public int selectGiftCardCount(MbMemberGiftCard mbMemberGiftCard) throws Exception;
	
	public int updateGiftCardDelYn(MbMemberGiftCard mbMemberGiftCard) throws Exception;

}
