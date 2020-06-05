package kr.co.abcmart.bo.promotion.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.promotion.model.master.PrPlanningDisplayDevice;
import kr.co.abcmart.bo.promotion.repository.master.base.BasePrPlanningDisplayDeviceDao;

@Mapper
public interface PrPlanningDisplayDeviceDao extends BasePrPlanningDisplayDeviceDao {

	/**
	 * 기본 insert, update, delete 메소드는 BasePrPlanningDisplayDeviceDao 클래스에 구현 되어있습니다.
	 * BasePrPlanningDisplayDeviceDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public PrPlanningDisplayDevice selectByPrimaryKey(PrPlanningDisplayDevice prPlanningDisplayDevice) throws Exception;

	/**
	 * @Desc : 기획전 디바이스 리스트 조회
	 * @Method Name : selectPrPlanningDisplayDeviceListByPlndpNo
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param plndpNo
	 * @return
	 * @throws Exception
	 */
	public List<PrPlanningDisplayDevice> selectPrPlanningDisplayDeviceListByPlndpNo(String plndpNo) throws Exception;

}
