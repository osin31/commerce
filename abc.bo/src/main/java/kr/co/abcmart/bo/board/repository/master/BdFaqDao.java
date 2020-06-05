package kr.co.abcmart.bo.board.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.board.model.master.BdFaq;
import kr.co.abcmart.bo.board.repository.master.base.BaseBdFaqDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface BdFaqDao extends BaseBdFaqDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseBdFaqDao 클래스에 구현 되어있습니다. BaseBdFaqDao는 절대
	 * 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public BdFaq selectByPrimaryKey(BdFaq bdFaq) throws Exception;

	/**
	 * @Desc : 그리드 조회 결과 갯수
	 * @Method Name : selectBdFaqListCount
	 * @Date : 2019. 3. 5.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectBdFaqListCount(Pageable<BdFaq, BdFaq> pageable) throws Exception;

	/**
	 * @Desc : top10 그리드 조회 결과 갯수
	 * @Method Name : selectTop10ListCount
	 * @Date : 2019. 3. 12.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectTop10ListCount(Pageable<BdFaq, BdFaq> pageable) throws Exception;

	/**
	 * @Desc : TOP10으로 수정시에 현재 TOP10 개수
	 * @Method Name : selectTop10UpdateCount
	 * @Date : 2019. 4. 15.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectTop10UpdateCount() throws Exception;

	/**
	 * @Desc : TOP10 설정할 경우 개수 카운트
	 * @Method Name : selectTop10SetCount
	 * @Date : 2019. 4. 16.
	 * @Author : 신인철
	 * @return
	 * @throws Exception
	 */
	public int selectTop10SetCount() throws Exception;

	/**
	 * @Desc : 그리드 조회
	 * @Method Name : selectBdFaqList
	 * @Date : 2019. 3. 5.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<BdFaq> selectBdFaqList(Pageable<BdFaq, BdFaq> pageable) throws Exception;

	/**
	 * @Desc : top10 그리드 조회
	 * @Method Name : selectTop10List
	 * @Date : 2019. 3. 12.
	 * @Author : 신인철
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<BdFaq> selectTop10List(Pageable<BdFaq, BdFaq> pageable) throws Exception;

	/**
	 * @Desc : FAQ상세보기
	 * @Method Name : selectFaqDetail
	 * @Date : 2019. 3. 5.
	 * @Author : 신인철
	 * @param bdFaq
	 * @return
	 * @throws Exception
	 */
	public BdFaq selectFaqDetail(BdFaq bdFaq) throws Exception;

	/**
	 * @Desc : FAQ 수정
	 * @Method Name : updateFaqDetail
	 * @Date : 2019. 3. 5.
	 * @Author : 신인철
	 * @param bdFaq
	 * @throws Exception
	 */
	public void updateFaqDetail(BdFaq bdFaq) throws Exception;

	/**
	 * @Desc : FAQ 등록
	 * @Method Name : insertFaqDetail
	 * @Date : 2019. 3. 5.
	 * @Author : 신인철
	 * @param bdFaq
	 * @throws Exception
	 */
	public void insertFaqDetail(BdFaq bdFaq) throws Exception;

	/**
	 * @Desc : TOP10 설정여부가 Y일경우 TOP10 정렬순서도 같이 등록
	 * @Method Name : inserTop10Faq
	 * @Date : 2019. 4. 11.
	 * @Author : 신인철
	 * @param bdFaq
	 * @throws Exception
	 */
	public void insertTop10Faq(BdFaq bdFaq) throws Exception;

	/**
	 * @Desc : FAQ 삭제
	 * @Method Name : deleteFaqDetail
	 * @Date : 2019. 3. 5.
	 * @Author : 신인철
	 * @param bdFaq
	 * @throws Exception
	 */
	public void deleteFaqDetail(BdFaq bdFaq) throws Exception;

	/**
	 * @Desc : TOP10 설정이 되었던 FAQ삭제나 수정후에 정렬순번 맞출 리스트 호출
	 * @Method Name : selectPuulingTop10List
	 * @Date : 2019. 4. 16.
	 * @Author : 신인철
	 * @param bdFaq
	 * @return
	 * @throws Exception
	 */
	public List<BdFaq> selectPuulingTop10List(BdFaq bdFaq) throws Exception;

	/**
	 * @Desc : 리스트로 들어온 FAQ 정렬순서 저장
	 * @Method Name : updateMultiTop10Sort
	 * @Date : 2019. 4. 18.
	 * @Author : 신인철
	 * @param faqList
	 * @throws Exception
	 */
	public void updateMultiTop10Sort(List<BdFaq> faqList) throws Exception;

	/**
	 * @Desc : TOP10 정렬순서 저장
	 * @Method Name : updateTop10Sort
	 * @Date : 2019. 4. 12.
	 * @Author : 신인철
	 * @param bdFaq
	 * @return
	 * @throws Exception
	 */
	public void updateTop10Sort(BdFaq bdFaq) throws Exception;

}
