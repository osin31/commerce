package kr.co.abcmart.bo.board.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.board.model.master.BdAdminNotice;
import kr.co.abcmart.bo.board.model.master.BdAdminNoticeTargetVendor;
import kr.co.abcmart.bo.board.repository.master.base.BaseBdAdminNoticeTargetVendorDao;

@Mapper
public interface BdAdminNoticeTargetVendorDao extends BaseBdAdminNoticeTargetVendorDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseBdAdminNoticeTargetVendorDao 클래스에 구현
	 * 되어있습니다. BaseBdAdminNoticeTargetVendorDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당
	 * 소스에서 작업 하시면 됩니다.
	 *
	 */

	public BdAdminNoticeTargetVendor selectByPrimaryKey(BdAdminNoticeTargetVendor bdAdminNoticeTargetVendor)
			throws Exception;

	/**
	 * @Desc : 공지 대상 업체 정보를 조회한다
	 * @Method Name : selectBdAdminNoticeTargetVendorList
	 * @Date : 2019. 2. 21.
	 * @Author : kiowa
	 * @param adminNotcSeq
	 * @return
	 * @throws Exception
	 */
	public List<BdAdminNoticeTargetVendor> selectBdAdminNoticeTargetVendorList(
			BdAdminNoticeTargetVendor bdAdminNoticeTargetVendor) throws Exception;

	/**
	 * @Desc : 관리자 공지 사항 번호로 대상 업체 정보를 모두 삭제한다.
	 * @Method Name : deleteBdAdminNoticeTargetVendorByAdminNotcSeq
	 * @Date : 2019. 2. 22.
	 * @Author : kiowa
	 * @param bdAdminNoticeTargetVendor
	 * @return
	 * @throws Exception
	 */
	public int deleteBdAdminNoticeTargetVendorByAdminNotcSeq(BdAdminNotice bdAdminNotice) throws Exception;

}
