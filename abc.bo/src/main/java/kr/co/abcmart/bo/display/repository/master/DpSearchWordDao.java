package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpSearchWord;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpSearchWordDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpSearchWordDao extends BaseDpSearchWordDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpSearchWordDao 클래스에 구현 되어있습니다.
	 * BaseDpSearchWordDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public DpSearchWord selectByPrimaryKey(DpSearchWord dpSearchWord) throws Exception;

	/**
	 * @Desc : 검색어 목록 조회
	 * @Method Name : selectSearchWordListCount
	 * @Date : 2019. 4. 16.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public int selectDpSearchWordListCount(Pageable<DpSearchWord, DpSearchWord> pageable) throws Exception;

	/**
	 * @Desc : 검색어 목록 카운트 조회
	 * @Method Name : selectSearchWordList
	 * @Date : 2019. 4. 16.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public List<DpSearchWord> selectDpSearchWordList(Pageable<DpSearchWord, DpSearchWord> pageable) throws Exception;

	/**
	 * @Desc : 검색어 추가
	 * @Method Name : insertDpSearchWord
	 * @Date : 2019. 4. 17.
	 * @Author : 이가영
	 * @param dpSearchWord
	 */
	public void insertDpSearchWord(DpSearchWord dpSearchWord) throws Exception;

	/**
	 * @Desc : 검색어 수정
	 * @Method Name : updateDpSearchWord
	 * @Date : 2019. 4. 17.
	 * @Author : 이가영
	 * @param dpSearchWord
	 */
	public void updateDpSearchWord(DpSearchWord dpSearchWord) throws Exception;

	/**
	 * @Desc : 검색어 삭제
	 * @Method Name : deleteDpSearchWord
	 * @Date : 2019. 4. 17.
	 * @Author : 이가영
	 * @param dpSearchWord
	 */
	public void deleteDpSearchWord(DpSearchWord dpSearchWord) throws Exception;

	/**
	 * @Desc : 검색어 중복 validate
	 * @Method Name : selectDpSearchWordForValidate
	 * @Date : 2019. 12. 18.
	 * @Author : 이강수
	 * @param DpSearchWord
	 * @return
	 */
	public int selectDpSearchWordForValidate(DpSearchWord dpSearchWord) throws Exception;
}
