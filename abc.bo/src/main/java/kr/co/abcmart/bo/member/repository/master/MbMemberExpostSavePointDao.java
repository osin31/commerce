package kr.co.abcmart.bo.member.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.member.model.master.MbMemberExpostSavePoint;
import kr.co.abcmart.bo.member.repository.master.base.BaseMbMemberExpostSavePointDao;
import kr.co.abcmart.bo.order.vo.OcOrderMemberExpostSaveVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface MbMemberExpostSavePointDao extends BaseMbMemberExpostSavePointDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseMbMemberExpostSavePointDao 클래스에 구현 되어있습니다.
	 * BaseMbMemberExpostSavePointDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public MbMemberExpostSavePoint selectByPrimaryKey(MbMemberExpostSavePoint mbMemberExpostSavePoint) throws Exception;

	public int countAllAdminList(Pageable<OcOrderMemberExpostSaveVO, OcOrderMemberExpostSaveVO> pageable)
			throws Exception;

	public List<OcOrderMemberExpostSaveVO> selectAdminList(
			Pageable<OcOrderMemberExpostSaveVO, OcOrderMemberExpostSaveVO> pageable) throws Exception;

	public int getLatedSavePointRecent1MonthCount(MbMemberExpostSavePoint params) throws Exception;

	public int selectExpostSavePointSeqNextVal(MbMemberExpostSavePoint params) throws Exception;

}
