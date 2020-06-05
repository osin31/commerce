package kr.co.abcmart.bo.order.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.order.model.master.OcOrderMemo;
import kr.co.abcmart.bo.order.repository.master.base.BaseOcOrderMemoDao;

@Mapper
public interface OcOrderMemoDao extends BaseOcOrderMemoDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcOrderMemoDao 클래스에 구현 되어있습니다.
	 * BaseOcOrderMemoDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public OcOrderMemo selectByPrimaryKey(OcOrderMemo ocOrderMemo) throws Exception;

	public List<OcOrderMemo> selectByOrderNo(OcOrderMemo ocOrderMemo) throws Exception;

	public int countByOrderNo(OcOrderMemo ocOrderMemo) throws Exception;

	public List<OcOrderMemo> selectAllByOrderNo(OcOrderMemo ocOrderMemo) throws Exception;

	public int countAllByOrderNo(OcOrderMemo ocOrderMemo) throws Exception;

	public int insertOrdertMemo(OcOrderMemo ocOrderMemo) throws Exception;
}
