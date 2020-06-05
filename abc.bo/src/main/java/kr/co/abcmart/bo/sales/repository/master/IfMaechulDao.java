package kr.co.abcmart.bo.sales.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.sales.model.master.IfMaechul;
import kr.co.abcmart.bo.sales.repository.master.base.BaseIfMaechulDao;
import kr.co.abcmart.bo.sales.vo.IfMeaechulExcelVo;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface IfMaechulDao extends BaseIfMaechulDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseIfMaechulDao 클래스에 구현 되어있습니다.
	 * BaseIfMaechulDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public IfMaechul selectByPrimaryKey(IfMaechul ifMaechul) throws Exception;

	public int selectIfMaechulListCount(Pageable<IfMaechul, IfMaechul> pageable) throws Exception;

	public List<IfMaechul> selectIfMaechulList(Pageable<IfMaechul, IfMaechul> pageable) throws Exception;

	public List<IfMeaechulExcelVo> selectIfMaechulAllExcelList(IfMaechul ifMaechul) throws Exception;

	public List<IfMaechul> selectIfMaechulExcelList(IfMaechul ifMaechul) throws Exception;

	public IfMaechul getIfMaechulSelector(IfMaechul ifMaechul) throws Exception;
}
