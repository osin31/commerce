package kr.co.abcmart.bo.member.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.member.model.master.MbMember;
import kr.co.abcmart.bo.member.model.master.MbMemberDeliveryAddress;
import kr.co.abcmart.bo.member.repository.master.base.BaseMbMemberDeliveryAddressDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface MbMemberDeliveryAddressDao extends BaseMbMemberDeliveryAddressDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseMbMemberDeliveryAddressDao 클래스에 구현 되어있습니다.
	 * BaseMbMemberDeliveryAddressDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public MbMemberDeliveryAddress selectByPrimaryKey(MbMemberDeliveryAddress mbMemberDeliveryAddress) throws Exception;

	public MbMemberDeliveryAddress selectMemberDfltDeliveryAddress(MbMemberDeliveryAddress mbMemberDeliveryAddress) throws Exception;
	
	public void insertMemberDeliveryAddressData(MbMemberDeliveryAddress mbMemberDeliveryAddress) throws Exception;
	
	public int updateMemberDeliveryAddressData(MbMemberDeliveryAddress mbMemberDeliveryAddress) throws Exception;
	
	public int selectMemberDeliveryListCount(Pageable<MbMemberDeliveryAddress, MbMemberDeliveryAddress> pageable) throws Exception;

	public List<MbMemberDeliveryAddress> selectMemberDeliveryList(Pageable<MbMemberDeliveryAddress, MbMemberDeliveryAddress> pageable) throws Exception;

}
