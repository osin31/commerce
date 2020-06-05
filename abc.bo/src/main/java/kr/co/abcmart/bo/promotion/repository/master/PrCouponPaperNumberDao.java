package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import kr.co.abcmart.bo.promotion.model.master.PrCouponPaperNumber;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrCouponPaperNumberDao;

@Mapper
public interface PrCouponPaperNumberDao extends BasePrCouponPaperNumberDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrCouponPaperNumberDao 클래스에 구현 되어있습니다.
	 * BasePrCouponPaperNumberDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public PrCouponPaperNumber selectByPrimaryKey(PrCouponPaperNumber prCouponPaperNumber) throws Exception;

	/**
	 * 지류 카운트
	 * 
	 * @Desc : 지류 카운트
	 * @Method Name : selectPrCouponPaperNumberCount
	 * @Date : 2019. 6. 11
	 * @Author : easyhun
	 * @param cpnNo
	 * @return
	 */
	public Long selectPrCouponPaperNumberCount(String cpnNo) throws Exception;

	/**
	 * 지류 등록
	 * 
	 * @Desc : 지류 등록
	 * @Method Name : 쿠폰
	 * @Date : 2019. 6. 11
	 * @Author : easyhun
	 * @param cpnNo
	 * @return
	 */
	public void insertPrCouponPaperNumber(PrCouponPaperNumber prCouponPaperNumber) throws Exception;

	/**
	 * 지류 Seq
	 * 
	 * @Desc : 지류 카운트
	 * @Method Name : selectPrCouponPaperNumberSeq
	 * @Date : 2019. 7. 23
	 * @Author : easyhun
	 * @param seq
	 * @return
	 */
	public Long selectPrCouponPaperNumberSeq(@Param("seq") Integer seq) throws Exception;

	/**
	 * 지류 리스트
	 * 
	 * @Desc : 지류 리스트
	 * @Method Name : selectPrCouponPaperNumberList
	 * @Date : 2019. 7. 23
	 * @Author : easyhun
	 * @param
	 * @return
	 */
	public List<PrCouponPaperNumber> selectPrCouponPaperNumberList(String cpnNo) throws Exception;

	/**
	 * @Desc : 쿠폰 지류 다중 등록
	 * @Method Name : insertRows
	 * @Date : 2019. 12. 6.
	 * @Author : tennessee
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	public Integer insertRows(List<PrCouponPaperNumber> rows) throws Exception;

}
