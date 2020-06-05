package kr.co.abcmart.bo.board.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.board.model.master.BdAdminNotice;
import kr.co.abcmart.bo.board.model.master.BdAdminNoticeAttachFile;
import kr.co.abcmart.bo.board.repository.master.base.BaseBdAdminNoticeAttachFileDao;

@Mapper
public interface BdAdminNoticeAttachFileDao extends BaseBdAdminNoticeAttachFileDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseBdAdminNoticeAttachFileDao 클래스에 구현 되어있습니다.
	 * BaseBdAdminNoticeAttachFileDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public BdAdminNoticeAttachFile selectByPrimaryKey(BdAdminNoticeAttachFile bdAdminNoticeAttachFile) throws Exception;

	public List<BdAdminNoticeAttachFile> selectAdminNoticeAttachFileList(BdAdminNoticeAttachFile params)
			throws Exception;

	public List<BdAdminNoticeAttachFile> selectAdminNoticeAttachFileListByAtchFileSeq(BdAdminNotice params)
			throws Exception;

	public void insertAdminNoticeAttachFile(BdAdminNoticeAttachFile params) throws Exception;

	public void deleteAdminNoticeAttachFileByAdminNotcSeq(BdAdminNotice params) throws Exception;

	public void deleteAdminNoticeAttachFileByAtchFileSeq(BdAdminNotice params) throws Exception;

	public void selectAdminNoticeAttachFileByAtchFileSeq(BdAdminNoticeAttachFile params) throws Exception;

}
