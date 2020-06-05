package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.promotion.model.master.PrCoupon;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrCouponDao;
import kr.co.abcmart.bo.promotion.vo.PrCouponSearchVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface PrCouponDao extends BasePrCouponDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrCouponDao 클래스에 구현 되어있습니다.
	 * BasePrCouponDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public PrCoupon selectByPrimaryKey(PrCoupon prCoupon) throws Exception;

	/**
	 * 쿠폰 리스트 조회
	 * 
	 * @Desc : 쿠폰 리스트 조회
	 * @Method Name : selectPrCouponList
	 * @Date : 2019. 2. 25
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public List<PrCoupon> selectPrCouponList(Pageable<PrCouponSearchVO, PrCoupon> pageable) throws Exception;

	/**
	 * 쿠폰 리스트 카운트 조회
	 * 
	 * @Desc :
	 * @Method Name : selectPrCouponCount
	 * @Date : 2019. 2. 25
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public int selectPrCouponCount(Pageable<PrCouponSearchVO, PrCoupon> pageable) throws Exception;

	/**
	 * 쿠폰 리스트 조회
	 * 
	 * @Desc : 쿠폰 리스트 조회(회원)
	 * @Method Name : selectMemberCouponList
	 * @Date : 2019. 2. 26
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public List<PrCoupon> selectMemberCouponList(Pageable<PrCouponSearchVO, PrCoupon> pageable) throws Exception;

	/**
	 * 쿠폰 리스트 카운트 조회(회원)
	 * 
	 * @Desc :
	 * @Method Name : selectMemberCouponCount
	 * @Date : 2019. 2. 26
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public int selectMemberCouponCount(Pageable<PrCouponSearchVO, PrCoupon> pageable) throws Exception;

	/**
	 * 쿠폰 상세 조회
	 * 
	 * @Desc :
	 * @Method Name : selectPrCoupon
	 * @Date : 2019. 2. 27
	 * @Author : easyhun
	 * @param PrCoupon
	 * @return
	 */
	public PrCoupon selectPrCoupon(PrCoupon prCoupon) throws Exception;

	/**
	 * 쿠폰 등록
	 * 
	 * @Desc :
	 * @Method Name : insertPrCoupon
	 * @Date : 2019. 3. 4
	 * @Author : easyhun
	 * @param PrCoupon
	 * @return
	 */
	public void insertPrCoupon(PrCoupon prCoupon) throws Exception;

	/**
	 * 쿠폰 수정
	 * 
	 * @Desc :
	 * @Method Name : updatePrCoupon
	 * @Date : 2019. 3. 4
	 * @Author : easyhun
	 * @param PrCoupon
	 * @return
	 */
	public void updatePrCoupon(PrCoupon prCoupon) throws Exception;

	/**
	 * 쿠폰 현황 리스트 조회
	 * 
	 * @Desc : 쿠폰 리스트 조회(회원)
	 * @Method Name : selectMemberListByCpnNo
	 * @Date : 2019. 3. 5
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public List<PrCoupon> selectMemberCouponPopList(Pageable<PrCouponSearchVO, PrCoupon> pageable) throws Exception;

	/**
	 * 쿠폰 현황 카운트 조회(회원)
	 * 
	 * @Desc :
	 * @Method Name : selectMemberListByCpnNoCount
	 * @Date : 2019. 3. 5
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public int selectMemberCouponPopCount(Pageable<PrCouponSearchVO, PrCoupon> pageable) throws Exception;

	/**
	 * 쿠폰 리스트 카운트 조회
	 * 
	 * @Desc :
	 * @Method Name : selectMemberCanUseCouponCount
	 * @Date : 2019. 3. 28
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public int selectMemberCanUseCouponCount(PrCouponSearchVO prCouponSearchVO) throws Exception;

	/**
	 * 쿠폰 발행 카운트 조회
	 * 
	 * @Desc :
	 * @Method Name : selectTotalMemberCouponCount
	 * @Date : 2019. 3. 28
	 * @Author : easyhun
	 * @param pageable
	 * @return
	 */
	public int selectTotalMemberCouponCount(String cpnNo) throws Exception;

	/**
	 * @Desc : 현재 시행중인 온라인 회원 기본 정책의 품절보상쿠폰 조회
	 * @Method Name : selectSoldOutCmpnsCpnPolicy
	 * @Date : 2019. 6. 15.
	 * @Author : KTH
	 * @return
	 * @throws Exception
	 */
	public PrCoupon selectSoldOutCmpnsCpnPolicy() throws Exception;

	/**
	 * 쿠폰 다운로드 수 증가
	 * 
	 * @Desc :
	 * @Method Name : updateTotalIssueCount
	 * @Date : 2019. 8. 16
	 * @Author : easyhun
	 * @param PrCoupon
	 * @return
	 */
	public void updateTotalIssueCount(PrCoupon prCoupon) throws Exception;

	/**
	 * 상품번호에 의한 쿠폰 조회
	 * 
	 * @Desc : 상품번호에 의한 쿠폰 조회
	 * @Method Name : selectPrdtNoByCpnList
	 * @Date : 2019. 9. 10
	 * @Author : easyhun
	 * @param prdtNo
	 * @throws Exception
	 */
	public List<PrCoupon> selectPrdtNoByCpnList(String prdtNo) throws Exception;

}
