package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplayApplyDevice;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrPlanningDisplayApplyDeviceDao;

@Mapper
public interface PrPlanningDisplayApplyDeviceDao extends BasePrPlanningDisplayApplyDeviceDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrPlanningDisplayApplyDeviceDao 클래스에 구현
	 * 되어있습니다. BasePrPlanningDisplayApplyDeviceDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는
	 * 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public PrPlanningDisplayApplyDevice selectByPrimaryKey(PrPlanningDisplayApplyDevice prPlanningDisplayApplyDevice)
			throws Exception;

	/**
	 * @Desc : 기획전 적용 디바이스 리스트 조회
	 * @Method Name : selectPrPlanningDisplayApplyDeviceListByPlndpNo
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param plndpNo
	 * @return
	 */
	public List<PrPlanningDisplayApplyDevice> selectPrPlanningDisplayApplyDeviceListByPlndpNo(String plndpNo)
			throws Exception;

	/**
	 * @Desc : 기획전 적용 디바이스 등록
	 * @Method Name : insertPrPlanningDisplayApplyDevice
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param prPlanningDisplayApplyDevice
	 */
	public void insertPrPlanningDisplayApplyDevice(PrPlanningDisplayApplyDevice prPlanningDisplayApplyDevice)
			throws Exception;

	/**
	 * 
	 * @Desc : 기획전 적용 디바이스 삭제
	 * @Method Name : deletePrPlanningDisplayApplyDeviceByPlndpNo
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param plndpNo
	 */
	public void deletePrPlanningDisplayApplyDeviceByPlndpNo(String plndpNo) throws Exception;
}
