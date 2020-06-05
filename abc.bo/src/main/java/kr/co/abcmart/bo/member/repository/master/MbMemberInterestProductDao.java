package kr.co.abcmart.bo.member.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.member.model.master.MbMemberInterestProduct;
import kr.co.abcmart.bo.member.repository.master.base.BaseMbMemberInterestProductDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface MbMemberInterestProductDao extends BaseMbMemberInterestProductDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseMbMemberInterestProductDao 클래스에 구현 되어있습니다.
	 * BaseMbMemberInterestProductDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */
	public MbMemberInterestProduct selectByPrimaryKey(MbMemberInterestProduct mbMemberInterestProduct) throws Exception;

	/**
	 * @Desc : 재입고 알림 서비스 리스트 조회
	 * @Method Name : selectWarehousingAlertList
	 * @Date : 2019. 7. 30.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public List<MbMemberInterestProduct> selectWarehousingAlertList(
			Pageable<MbMemberInterestProduct, MbMemberInterestProduct> pageable) throws Exception;

	/**
	 * @Desc : 재입고 알림 서비스 카운트 조회
	 * @Method Name : selectWarehousingAlertCount
	 * @Date : 2019. 7. 30.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 */
	public int selectWarehousingAlertCount(Pageable<MbMemberInterestProduct, MbMemberInterestProduct> pageable)
			throws Exception;

	public void updateMemberReStockAlarm(MbMemberInterestProduct mb) throws Exception;

	/**
	 * @Desc : 재입고 알림 서비스 신청 취소
	 * @Method Name : updateMemberReStockAlarmCancel
	 * @Date : 2019. 7. 31.
	 * @Author : 이가영
	 * @param mbMemberInterestProduct
	 */
	public void updateMemberReStockAlarmCancel(MbMemberInterestProduct mbMemberInterestProduct) throws Exception;

}
