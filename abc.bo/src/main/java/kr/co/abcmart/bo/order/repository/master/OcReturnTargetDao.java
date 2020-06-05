package kr.co.abcmart.bo.order.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.order.model.master.OcReturnTarget;
import kr.co.abcmart.bo.order.repository.master.base.BaseOcReturnTargetDao;

@Mapper
public interface OcReturnTargetDao extends BaseOcReturnTargetDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcReturnTargetDao 클래스에 구현 되어있습니다.
	 * BaseOcReturnTargetDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public OcReturnTarget selectByPrimaryKey(OcReturnTarget ocReturnTarget) throws Exception;

	/**
	 * 
	 * @Desc : 구매확정후 반품(매출연동 후) 포인트 차감 대상 - 원주문기준으로 등록진행
	 * @Method Name : insertOcReturnTarget
	 * @Date : 2019. 8. 1.
	 * @Author : NKB
	 * @param prodHistoryList
	 * @return
	 * @throws Exception
	 */
	public int insertOcReturnTarget(OcReturnTarget ocReturnTarget) throws Exception;

}
