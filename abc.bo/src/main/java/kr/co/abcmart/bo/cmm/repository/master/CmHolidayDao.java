package kr.co.abcmart.bo.cmm.repository.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.cmm.model.master.CmHoliday;
import kr.co.abcmart.bo.cmm.repository.master.base.BaseCmHolidayDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface CmHolidayDao extends BaseCmHolidayDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BaseCmHolidayDao 클래스에 구현 되어있습니다.
     * BaseCmHolidayDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
     */

	public CmHoliday selectByPrimaryKey(CmHoliday cmHoliday) throws Exception;
	
	/**
	 * 
	 * @Desc      	: 휴일정보 리스트 count를 조회한다.
	 * @Method Name : selectHolidayYearListCount
	 * @Date  	  	: 2019. 2. 20.
	 * @Author    	: choi
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectHolidayYearListCount(Pageable<CmHoliday, CmHoliday> pageable) throws Exception;		/** 휴일 리스트 개수 가져오기 */
	
	/**
	 * 
	 * @Desc      	: 휴일 데이터를 조회한다.
	 * @Method Name : selectHolidayYearList
	 * @Date  	  	: 2019. 2. 20.
	 * @Author    	: choi
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<CmHoliday> selectHolidayYearList(Pageable<CmHoliday, CmHoliday> pageable) throws Exception;	/** 휴일 그리드 리스트 */
	
	/**
	 * 
	 * @Desc      	: 업체의 월별 휴일 정보를 조회한다.
	 * @Method Name : getHolidayDetailCalendarPop
	 * @Date  	  	: 2019. 2. 20.
	 * @Author    	: choi
	 * @param cmHoliday
	 * @return
	 * @throws Exception
	 */
	public List<CmHoliday> selectHolidayDetailCalendarPop(CmHoliday cmHoliday) throws Exception;	/** 휴일 캘린더로 보기 */

	/**
	 * 
	 * @Desc      	: 업체의 1년치 휴일 정보를 조회한다.
	 * @Method Name : selectHolidayList
	 * @Date  	  	: 2019. 2. 20.
	 * @Author    	: choi
	 * @param cmHoliday
	 * @return
	 * @throws Exception
	 */
	public List<CmHoliday> selectHolidayList(CmHoliday cmHoliday) throws Exception;

	/**
	 * 
	 * @Desc      	: 기준 휴일 정보를 조회한다.
	 * @Method Name : selectStandardHolidayList
	 * @Date  	  	: 2019. 2. 20.
	 * @Author    	: choi
	 * @param cmHoliday
	 * @return
	 * @throws Exception
	 */
	public List<CmHoliday> selectStandardHolidayList(CmHoliday cmHoliday) throws Exception;

	/**
	 * 
	 * @Desc      	: 기준 휴일 정보를 365개 데이터를 조회한다.
	 * @Method Name : selectStandardHolidayList
	 * @Date  	  	: 2019. 2. 20.
	 * @Author    	: choi
	 * @param cmHoliday
	 * @return
	 * @throws Exception
	 */
	public List<CmHoliday> selectStandardHolidayListAll(CmHoliday cmHoliday) throws Exception;
	
	/**
	 * 
	 * @Desc      	: 시스템 휴일 정보를 등록한다.
	 * @Method Name : insertsystemHolidayNo
	 * @Date  	  	: 2019. 2. 20.
	 * @Author    	: choi
	 * @param resultList
	 * @throws Exception
	 */
	public void insertsystemHolidayNo(List<Map<String, Object>> resultList) throws Exception;

	/**
	 * 
	 * @Desc      	: 시스템 휴일 정보를 등록한다.
	 * @Method Name : insertsystemHolidayNo
	 * @Date  	  	: 2019. 2. 20.
	 * @Author    	: choi
	 * @param resultList
	 * @throws Exception
	 */
	public void insertHoliday(List<CmHoliday> resultList) throws Exception;

	/**
	 * 
	 * @Desc      	: 업체 휴일정보로 업데이트 한다.
	 * @Method Name : updateHoliday
	 * @Date  	  	: 2019. 2. 20.
	 * @Author    	: choi
	 * @param cmHoliday
	 * @throws Exception
	 */
	public void updateHoliday(CmHoliday cmHoliday) throws Exception;

	/**
	 * 
	 * @Desc      	: 업체 공통휴일정보 데이터를 업데이트 한다.
	 * @Method Name : updateHoliday
	 * @Date  	  	: 2019. 6. 11.
	 * @Author    	: choi
	 * @param cmHoliday
	 * @throws Exception
	 */
	public void updateCommonHoliday(CmHoliday cmHoliday) throws Exception;
	
	/**
	 * 
	 * @Desc      	: 그리드의 휴일정보를 N으로 업데이트 처리(시스템을 제회한 모든 업체의 휴일을 공통으로 업데이트 처리함)
	 * @Method Name : updateHolidayGrid
	 * @Date  	  	: 2019. 2. 20.
	 * @Author    	: choi
	 * @param cmHoliday
	 * @throws Exception
	 */
	public void updateHolidayGrid(CmHoliday cmHoliday) throws Exception;

	/**
	 * 
	 * @Desc      	: 입점사 등록 시 입점사의 휴일 데이터 select > insert함(시스템 휴일정보로 등록함)
	 * @Method Name : insertSystemHolidayVndr
	 * @Date  	  	: 2019. 2. 20.
	 * @Author    	: choi
	 * @param cmHoliday
	 * @throws Exception
	 */
	public void insertSystemHolidayVndr(CmHoliday cmHoliday) throws Exception;

}
