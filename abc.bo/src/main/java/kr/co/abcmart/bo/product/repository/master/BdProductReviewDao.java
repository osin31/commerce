package kr.co.abcmart.bo.product.repository.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.BdProductReview;
import kr.co.abcmart.bo.product.repository.master.base.BaseBdProductReviewDao;
import kr.co.abcmart.bo.product.vo.BdProductReviewSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface BdProductReviewDao extends BaseBdProductReviewDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseBdProductReviewDao 클래스에 구현 되어있습니다.
	 * BaseBdProductReviewDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public BdProductReview selectByPrimaryKey(BdProductReview bdProductReview) throws Exception;

	/**
	 * @Desc : 상품후기 미답변 건수 (서비스 요청)
	 * @Method Name : getAswrCount
	 * @Date : 2019. 4. 5.
	 * @Author : hsjhsj19
	 * @param reviewSearchVO
	 * @return
	 */
	public String getAswrCount(BdProductReviewSearchVO reviewSearchVO) throws Exception;

	/**
	 * @Desc : 상품후기 목록 갯수 조회
	 * @Method Name : selectProductReviewCount
	 * @Date : 2019. 4. 5.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 */
	public Integer selectProductReviewCount(Pageable<BdProductReviewSearchVO, BdProductReview> pageable)
			throws Exception;

	/**
	 * @Desc : 상품후기 목록 조회
	 * @Method Name : selectProductReview
	 * @Date : 2019. 4. 5.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 */
	public List<BdProductReview> selectProductReview(Pageable<BdProductReviewSearchVO, BdProductReview> pageable)
			throws Exception;

	/**
	 * @Desc : 상품후기 상세 조회
	 * @Method Name : selectByPK
	 * @Date : 2019. 4. 9.
	 * @Author : hsjhsj19
	 * @param bdProductReview
	 * @return
	 */
	public BdProductReview selectByPK(BdProductReview bdProductReview) throws Exception;

	/**
	 * @Desc : PK 제외한 업데이트
	 * @Method Name : updateWithoutPK
	 * @Date : 2019. 4. 22.
	 * @Author : hsjhsj19
	 * @param bdProductReview
	 * @return
	 * @throws Exception
	 */
	public int updateWithoutPK(BdProductReview bdProductReview) throws Exception;

	/**
	 * @Desc : 미답변 후기 갯수 조회
	 * @Method Name : selectReviewGroupCount
	 * @Date : 2019. 5. 10.
	 * @Author : hsjhsj19
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectReviewGroupCount() throws Exception;

	/**
	 * @Desc : 후기 작성 포인트 지급 현황
	 * @Method Name : selectMemberReviewPointStats
	 * @Date : 2019. 8. 7.
	 * @Author : hsjhsj19
	 * @param review
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectMemberReviewPointStats(BdProductReview review) throws Exception;

}
