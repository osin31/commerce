package kr.co.abcmart.bo.board.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.board.model.master.BdMemberCounselAttachFile;
import kr.co.abcmart.bo.board.repository.master.base.BaseBdMemberCounselAttachFileDao;

@Mapper
public interface BdMemberCounselAttachFileDao extends BaseBdMemberCounselAttachFileDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseBdMemberCounselAttachFileDao 클래스에 구현
	 * 되어있습니다. BaseBdMemberCounselAttachFileDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당
	 * 소스에서 작업 하시면 됩니다.
	 *
	 */

	public BdMemberCounselAttachFile selectByPrimaryKey(BdMemberCounselAttachFile bdMemberCounselAttachFile)
			throws Exception;

	/**
	 * @Desc : 상담 첨부 파일 조회
	 * @Method Name : selectBdMemberCounselAttachFileList
	 * @Date : 2019. 2. 21.
	 * @Author : 신인철
	 * @param bdMemberCounselAttachFile
	 * @return
	 * @throws Exception
	 */
	public List<BdMemberCounselAttachFile> selectBdMemberCounselAttachFileList(
			BdMemberCounselAttachFile bdMemberCounselAttachFile) throws Exception;

	/**
	 * @Desc : 상담 첨부파일 db저장
	 * @Method Name : insertAtchFile
	 * @Date : 2019. 2. 21.
	 * @Author : 신인철
	 * @param bdMemberCounselAttachFile
	 * @throws Exception
	 */
	public void insertAtchFile(BdMemberCounselAttachFile bdMemberCounselAttachFile) throws Exception;

	/**
	 * @Desc : 상담 첨부파일 삭제
	 * @Method Name : deleteCounselAtchFile
	 * @Date : 2019. 2. 21.
	 * @Author : 신인철
	 * @param bdMemberCounselAttachFile
	 * @throws Exception
	 */
	public void deleteCounselAtchFile(BdMemberCounselAttachFile bdMemberCounselAttachFile) throws Exception;

}
