package kr.co.abcmart.bo.display.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.display.model.master.BdPopupDevice;
import kr.co.abcmart.bo.display.repository.master.base.BaseBdPopupDeviceDao;

@Mapper
public interface BdPopupDeviceDao extends BaseBdPopupDeviceDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseBdPopupDeviceDao 클래스에 구현 되어있습니다.
	 * BaseBdPopupDeviceDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public BdPopupDevice selectByPrimaryKey(BdPopupDevice bdPopupDevice) throws Exception;

	/**
	 * 팝업 수정
	 * 
	 * @Desc : 팝업 수정
	 * @Method Name : updatePopupDevice
	 * @Date : 2019. 2. 21
	 * @Author : easyhun
	 * @param bdPopupDevice
	 * @return
	 * @throws Exception
	 */
	public int updatePopupDevice(BdPopupDevice bdPopupDevice) throws Exception;

	/**
	 * 팝업 등록
	 * 
	 * @Desc : 팝업 등록
	 * @Method Name : insertPopupDevcie
	 * @Date : 2019. 4. 22
	 * @Author : easyhun
	 * @param bdPopupDevice
	 * @return
	 * @throws Exception
	 */
	public void insertPopupDevice(BdPopupDevice bdPopupDevice) throws Exception;

	/**
	 * 팝업 삭제
	 * 
	 * @Desc : 팝업 삭제
	 * @Method Name : deleteByPopupSeq
	 * @Date : 2019. 2. 21
	 * @Author : easyhun
	 * @param bdPopupDevice
	 * @return
	 * @throws Exception
	 */
	public int deleteByPopupSeq(Integer popupSeq) throws Exception;
}
