package kr.co.abcmart.bo.cmm.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.cmm.model.master.CmForbiddenWord;
import kr.co.abcmart.bo.cmm.repository.master.base.BaseCmForbiddenWordDao;

@Mapper
public interface CmForbiddenWordDao extends BaseCmForbiddenWordDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseCmForbiddenWordDao 클래스에 구현 되어있습니다.
	 * BaseCmForbiddenWordDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public CmForbiddenWord selectByPrimaryKey(CmForbiddenWord cmForbiddenWord) throws Exception;

	public CmForbiddenWord selectTopByForbidWordTypeOrderbyModDtmDesc(CmForbiddenWord cmForbiddenWord) throws Exception;

	public CmForbiddenWord selectTopWithSyAdminByForbidWordTypeOrderbyModDtmDesc(CmForbiddenWord cmForbiddenWord)
			throws Exception;

	public void insertIdentity(CmForbiddenWord cmForbiddenWord) throws Exception;

	public void updateIdentity(CmForbiddenWord cmForbiddenWord) throws Exception;

}
