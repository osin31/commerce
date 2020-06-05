package kr.co.abcmart.bo.event.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.event.model.master.EvEventTargetChannel;
import kr.co.abcmart.bo.event.repository.master.base.BaseEvEventTargetChannelDao;

@Mapper
public interface EvEventTargetChannelDao extends BaseEvEventTargetChannelDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseEvEventTargetChannelDao 클래스에 구현 되어있습니다.
	 * BaseEvEventTargetChannelDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public EvEventTargetChannel selectByPrimaryKey(EvEventTargetChannel evEventTargetChannel) throws Exception;

	/**
	 * 이벤트 채널 조회
	 * 
	 * @Desc : 이벤트 채널 조회
	 * @Method Name : getPrPromotionDeviceListByPromoNo
	 * @Date : 2019. 3. 26
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventTargetChannel> selectEvEventChannelListByEventNo(String eventNo) throws Exception;

	/**
	 * 이벤트 채널 삭제
	 * 
	 * @Desc : 이벤트 채널 삭제
	 * @Method Name : deleteByEventNo
	 * @Date : 2019. 4. 5
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public void deleteByEventNo(String eventNo) throws Exception;

}
