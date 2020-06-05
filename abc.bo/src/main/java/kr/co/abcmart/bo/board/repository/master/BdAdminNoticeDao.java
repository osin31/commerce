package kr.co.abcmart.bo.board.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.board.model.master.BdAdminNotice;
import kr.co.abcmart.bo.board.repository.master.base.BaseBdAdminNoticeDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface BdAdminNoticeDao extends BaseBdAdminNoticeDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseBdAdminNoticeDao 클래스에 구현 되어있습니다.
	 * BaseBdAdminNoticeDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public BdAdminNotice selectByPrimaryKey(BdAdminNotice bdAdminNotice) throws Exception;

	public int selectAdminNoticeListCount(Pageable<BdAdminNotice, BdAdminNotice> pageable) throws Exception;

	public List<BdAdminNotice> selectAdminNoticeList(Pageable<BdAdminNotice, BdAdminNotice> pageable) throws Exception;

	public BdAdminNotice selectAdminNotice(BdAdminNotice params) throws Exception;

	public int insertAdminNotice(BdAdminNotice params) throws Exception;

	public void updateAdminNotice(BdAdminNotice params) throws Exception;

	public void deleteAdminNotice(BdAdminNotice params) throws Exception;

	public void updateAdminNoticeHitCount(BdAdminNotice params) throws Exception;

	public int selectAdNoticeForDashboardCount(Pageable<BdAdminNotice, BdAdminNotice> pageable) throws Exception;

	public List<BdAdminNotice> selectAdNoticeForDashboardTopFive(BdAdminNotice bdAdminNotice, String vndrNo)
			throws Exception;

	public List<BdAdminNotice> selectAdNoticeForDashboard(Pageable<BdAdminNotice, BdAdminNotice> pageable)
			throws Exception;

	public String[] selectBoMainPopAdminNotice();

	public String[] selectPoMainPopAdminNotice(String vndrNo);

}
