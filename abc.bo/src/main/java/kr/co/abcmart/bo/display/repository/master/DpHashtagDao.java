package kr.co.abcmart.bo.display.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.DpHashtag;
import kr.co.abcmart.bo.display.repository.master.base.BaseDpHashtagDao;
import kr.co.abcmart.bo.display.vo.HashTagSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface DpHashtagDao extends BaseDpHashtagDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseDpHashtagDao 클래스에 구현 되어있습니다.
	 * BaseDpHashtagDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 */

	public DpHashtag selectByPrimaryKey(DpHashtag dpHashtag) throws Exception;

	/**
	 * @Desc : 해쉬태그 검색 조회 결과 개수
	 * @Method Name : selectHashTagListCount
	 * @Date : 2019. 6. 26.
	 * @Author : kiowa
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectHashTagListCount(Pageable<HashTagSearchVO, DpHashtag> pageable) throws Exception;

	/**
	 * @Desc : 해쉬태그 검색 조회 결과
	 * @Method Name : selectHashTagList
	 * @Date : 2019. 6. 26.
	 * @Author : kiowa
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<DpHashtag> selectHashTagList(Pageable<HashTagSearchVO, DpHashtag> pageable) throws Exception;

	/**
	 * @Desc : 해쉬태그 정보 저장
	 * @Method Name : insertHashTag
	 * @Date : 2019. 6. 27.
	 * @Author : kiowa
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int insertHashTag(DpHashtag param) throws Exception;

	/**
	 * @Desc : 해쉬태그 정보 수정
	 * @Method Name : updateHashTag
	 * @Date : 2019. 6. 27.
	 * @Author : kiowa
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int updateHashTag(DpHashtag param) throws Exception;
}
