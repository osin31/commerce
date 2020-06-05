package kr.co.abcmart.bo.event.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.event.model.master.EvEventImage;
import kr.co.abcmart.bo.event.repository.master.base.BaseEvEventImageDao;

@Mapper
public interface EvEventImageDao extends BaseEvEventImageDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseEvEventImageDao 클래스에 구현 되어있습니다.
	 * BaseEvEventImageDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public EvEventImage selectByPrimaryKey(EvEventImage evEventImage) throws Exception;

	/**
	 * 이벤트 이미지 조회
	 * 
	 * @Desc : 이벤트 이미지 조회
	 * @Method Name : getEvEventImageListByEventNo
	 * @Date : 2019. 3. 28
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventImage> selectEvEventImageListByEventNo(String eventNo) throws Exception;

}
