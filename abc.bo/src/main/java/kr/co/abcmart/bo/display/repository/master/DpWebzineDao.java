package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpWebzine;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpWebzineDao;
import kr.co.abcmart.bo.display.vo.DpWebzineSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpWebzineDao extends BaseDpWebzineDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpWebzineDao 클래스에 구현 되어있습니다.
	 * BaseDpWebzineDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public DpWebzine selectByPrimaryKey(DpWebzine DpWebzine) throws Exception;

	/**
	 * 웹진 수정
	 * 
	 * @Desc : 웹진 수정
	 * @Method Name : updateDpWebzine
	 * @Date : 2019. 2. 8.
	 * @Author : SANTA
	 * @param dpWebzine
	 * @return
	 * @throws Exception
	 */
	public int updateDpWebzine(DpWebzine dpWebzine) throws Exception;

	/**
	 * 웹진 리스트 조회
	 * 
	 * @Desc : 웹진 리스트 조회
	 * @Method Name : selectDpWebzineList
	 * @Date : 2019. 1. 31.
	 * @Author : SANTA
	 * @param pageable
	 * @return
	 */
	public List<DpWebzine> selectDpWebzineList(Pageable<DpWebzineSearchVO, DpWebzine> pageable) throws Exception;

	/**
	 * 
	 * @Desc : 웹진 리스트 카운트 조회
	 * @Method Name : selectAdminNoticeListCount
	 * @Date : 2019. 2. 1.
	 * @Author : SANTA
	 * @param pageable
	 * @return
	 */
	public int selectDpWebzineCount(Pageable<DpWebzineSearchVO, DpWebzine> pageable) throws Exception;

	/**
	 * 웹진 조회
	 * 
	 * @Desc : 웹진 조회
	 * @Method Name : selectDpWebzine
	 * @Date : 2019. 2. 1.
	 * @Author : SANTA
	 * @param dpWebzine
	 * @return
	 * @throws Exception
	 */
	public DpWebzine selectDpWebzine(DpWebzine dpWebzine) throws Exception;

}
