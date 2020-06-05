package kr.co.abcmart.bo.member.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.member.model.master.MbMemberCoupon;
import kr.co.abcmart.bo.member.repository.master.base.BaseMbMemberCouponDao;

@Mapper
public interface MbMemberCouponDao extends BaseMbMemberCouponDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseMbMemberCouponDao 클래스에 구현 되어있습니다.
	 * BaseMbMemberCouponDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public MbMemberCoupon selectByPrimaryKey(MbMemberCoupon mbMemberCoupon) throws Exception;

	public MbMemberCoupon selectMemberCouponCnt(String memberNo) throws Exception;

	public MbMemberCoupon selectMemberCouponStatData(String memberNo) throws Exception;

	public String selectMemberCouponHoldSeq(String memberNo) throws Exception;

	/**
	 * @Desc : 회원 보유쿠폰 사용가능 목록 조회
	 * @Method Name : selectMemberCouponAvailableList
	 * @Date : 2019. 3. 27.
	 * @Author : KTH
	 * @param mbMemberCoupon
	 * @return
	 * @throws Exception
	 */
	public List<MbMemberCoupon> selectMemberCouponAvailableList(MbMemberCoupon mbMemberCoupon) throws Exception;

	/**
	 * @Desc : 회원 보유쿠폰 사용 업데이트
	 * @Method Name : updateMemberCouponUseInfo
	 * @Date : 2019. 3. 28.
	 * @Author : KTH
	 * @param mbMemberCoupon
	 * @return
	 * @throws Exception
	 */
	public int updateMemberCouponUseInfo(MbMemberCoupon mbMemberCoupon) throws Exception;

	/**
	 * @Desc : 회원 쿠폰 발급
	 * @Method Name : insertMemberCoupon
	 * @Date : 2019. 5. 31.
	 * @param mbMemberCoupon
	 * @return
	 * @throws Exception
	 */
	public void insertMemberCoupon(MbMemberCoupon mbMemberCoupon) throws Exception;

	/**
	 * @Desc : 회원 쿠폰 재 발급 등록(복원 - 기 사용된 쿠폰 기준)
	 * @Method Name : insertMemberCouponReIssue
	 * @Date : 2019. 6. 16.
	 * @Author : KTH
	 * @param mbMemberCoupon
	 * @return
	 * @throws Exception
	 */
	public int insertMemberCouponReIssue(MbMemberCoupon mbMemberCoupon) throws Exception;

}
