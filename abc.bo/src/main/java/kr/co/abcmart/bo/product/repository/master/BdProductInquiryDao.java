package kr.co.abcmart.bo.product.repository.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.BdProductInquiry;
import kr.co.abcmart.bo.product.repository.master.base.BaseBdProductInquiryDao;
import kr.co.abcmart.bo.product.vo.BdProductInquirySearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface BdProductInquiryDao extends BaseBdProductInquiryDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseBdProductInquiryDao 클래스에 구현 되어있습니다.
	 * BaseBdProductInquiryDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public BdProductInquiry selectByPrimaryKey(BdProductInquiry bdProductInquiry) throws Exception;

	/**
	 * @Desc : 상품 문의 갯수 조회
	 * @Method Name : selectProductInquiryCount
	 * @Date : 2019. 4. 9.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 */
	public Integer selectProductInquiryCount(Pageable<BdProductInquirySearchVO, BdProductInquiry> pageable)
			throws Exception;

	/**
	 * @Desc : 상품 문의 목록 조회
	 * @Method Name : selectProductInquiry
	 * @Date : 2019. 4. 9.
	 * @Author : hsjhsj19
	 * @param pageable
	 * @return
	 */
	public List<BdProductInquiry> selectProductInquiry(Pageable<BdProductInquirySearchVO, BdProductInquiry> pageable)
			throws Exception;

	/**
	 * @Desc : 상품Q&A 답변, 미답변 상태별 건수(최근2개월 기준)
	 * @Method Name : getAswrCount
	 * @Date : 2019. 4. 10.
	 * @Author : hsjhsj19
	 * @param hashMap
	 * @return
	 */
	public Map<String, Object> getAswrCount(Map<String, Object> hashMap) throws Exception;

	/**
	 * @Desc : 추가 컬럼이 있는 상품문의 단건조회
	 * @Method Name : selectByPK
	 * @Date : 2019. 4. 10.
	 * @Author : hsjhsj19
	 * @param bdProductInquiry
	 * @return
	 */
	public BdProductInquiry selectByPK(BdProductInquiry bdProductInquiry) throws Exception;

	/**
	 * @Desc : PK 제외한 업데이트
	 * @Method Name : updateWithoutPK
	 * @Date : 2019. 4. 22.
	 * @Author : hsjhsj19
	 * @param bdProductInquiry
	 * @return
	 * @throws Exception
	 */
	public int updateWithoutPK(BdProductInquiry bdProductInquiry) throws Exception;

	/**
	 * @param bdProductInquiry
	 * @Desc : 미답변 문의 갯수 조회
	 * @Method Name : selectProductInquiryGroupCount
	 * @Date : 2019. 5. 10.
	 * @Author : hsjhsj19
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectProductInquiryGroupCount(BdProductInquirySearchVO bdProductInquiry)
			throws Exception;

	/**
	 * @Desc : 상품문의 답변현황
	 * @Method Name : selectMemberInquiryStats
	 * @Date : 2019. 8. 20.
	 * @Author : hsjhsj19
	 * @param inquiry
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectMemberInquiryStats(BdProductInquiry inquiry) throws Exception;;

}
