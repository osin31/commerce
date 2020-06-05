package kr.co.abcmart.bo.afterService.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.afterService.model.master.OcAsAcceptMemo;
import kr.co.abcmart.bo.afterService.repository.master.base.BaseOcAsAcceptMemoDao;

@Mapper
public interface OcAsAcceptMemoDao extends BaseOcAsAcceptMemoDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcAsAcceptMemoDao 클래스에 구현 되어있습니다.
	 * BaseOcAsAcceptMemoDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public OcAsAcceptMemo selectByPrimaryKey(OcAsAcceptMemo ocAsAcceptMemo) throws Exception;

	public List<OcAsAcceptMemo> selectOcAsAcceptMemoData(OcAsAcceptMemo ocAsAcceptMemo) throws Exception;

	public void insertOcAsAcceptMemo(OcAsAcceptMemo ocAsAcceptMemo) throws Exception;

	public int updateOcAsAcceptMemo(OcAsAcceptMemo ocAsAcceptMemo) throws Exception;
}
