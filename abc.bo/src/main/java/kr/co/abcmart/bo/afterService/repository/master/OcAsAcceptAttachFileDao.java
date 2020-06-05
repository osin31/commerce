package kr.co.abcmart.bo.afterService.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.afterService.model.master.OcAsAcceptAttachFile;
import kr.co.abcmart.bo.afterService.repository.master.base.BaseOcAsAcceptAttachFileDao;

@Mapper
public interface OcAsAcceptAttachFileDao extends BaseOcAsAcceptAttachFileDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcAsAcceptAttachFileDao 클래스에 구현 되어있습니다.
	 * BaseOcAsAcceptAttachFileDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public OcAsAcceptAttachFile selectByPrimaryKey(OcAsAcceptAttachFile ocAsAcceptAttachFile) throws Exception;

	public int selectGetFileSeq(String asAcceptNo, Short seq) throws Exception;

	public int selectAtchFileSeqNextVal() throws Exception;

	public List<OcAsAcceptAttachFile> selectAfterServiceAttachFileList(OcAsAcceptAttachFile params) throws Exception;
}
