package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpChnnlSearchWord;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpChnnlSearchWordDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpChnnlSearchWordDao extends BaseDpChnnlSearchWordDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpChnnlSearchWordDao 클래스에 구현 되어있습니다.
	 * BaseDpChnnlSearchWordDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */
	public DpChnnlSearchWord selectByPrimaryKey(DpChnnlSearchWord dpChnnlSearchWord) throws Exception;

	/**
	 * @Desc : 채널 검색어 목록 갯수 조회
	 * @Method Name : selectDpChnnlSearchWordListCount
	 * @Date : 2019. 12. 18.
	 * @Author : 이강수
	 * @param Pageable<DpChnnlSearchWord, DpChnnlSearchWord>
	 * @return
	 */
	public int selectDpChnnlSearchWordListCount(Pageable<DpChnnlSearchWord, DpChnnlSearchWord> pageable)
			throws Exception;

	/**
	 * @Desc : 채널 검색어 목록 조회
	 * @Method Name : selectDpChnnlSearchWordList
	 * @Date : 2019. 12. 18.
	 * @Author : 이강수
	 * @param Pageable<DpChnnlSearchWord, DpChnnlSearchWord>
	 * @return
	 */
	public List<DpChnnlSearchWord> selectDpChnnlSearchWordList(Pageable<DpChnnlSearchWord, DpChnnlSearchWord> pageable)
			throws Exception;

	/**
	 * @Desc : 채널 검색어 추가
	 * @Method Name : insertDpChnnlSearchWord
	 * @Date : 2019. 12. 18.
	 * @Author : 이강수
	 * @param DpChnnlSearchWord
	 */
	public void insertDpChnnlSearchWord(DpChnnlSearchWord dpChnnlSearchWord) throws Exception;

	/**
	 * @Desc : 채널 검색어 수정
	 * @Method Name : updateDpChnnlSearchWord
	 * @Date : 2019. 12. 18.
	 * @Author : 이강수
	 * @param DpChnnlSearchWord
	 */
	public void updateDpChnnlSearchWord(DpChnnlSearchWord dpChnnlSearchWord) throws Exception;

	/**
	 * @Desc : 채널 검색어 삭제
	 * @Method Name : deleteDpChnnlSearchWord
	 * @Date : 2019. 12. 18.
	 * @Author : 이강수
	 * @param DpChnnlSearchWord
	 */
	public void deleteDpChnnlSearchWord(DpChnnlSearchWord dpChnnlSearchWord) throws Exception;

	/**
	 * @Desc : 채널 검색어 중복 검사
	 * @Method Name : selectDpChnnlSearchWordForValidate
	 * @Date : 2019. 12. 19.
	 * @Author : 이강수
	 * @param DpChnnlSearchWord
	 * @return
	 */
	public int selectDpChnnlSearchWordForValidate(DpChnnlSearchWord dpChnnlSearchWord) throws Exception;
}
