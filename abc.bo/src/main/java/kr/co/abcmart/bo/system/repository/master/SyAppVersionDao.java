package kr.co.abcmart.bo.system.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.system.model.master.SyAppVersion;
import kr.co.abcmart.bo.system.repository.master.base.BaseSyAppVersionDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface SyAppVersionDao extends BaseSyAppVersionDao {
	
     /**
     * 기본 insert, update, delete 메소드는 BaseSyAppVersionDao 클래스에 구현 되어있습니다.
     * BaseSyAppVersionDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     * 
     */

	public SyAppVersion selectByPrimaryKey(SyAppVersion SyAppVersion) throws Exception;

	/**
	 * 
	 * @Desc      	: APP 버전 데이터 count를 조회한다.
	 * @Method Name : selectAppversionListCount
	 * @Date  	  	: 2019. 2. 20.
	 * @Author    	: choi
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectAppversionListCount(Pageable<SyAppVersion, SyAppVersion> pageable) throws Exception;

	/**
	 * 
	 * @Desc      	: APP 버전 리스트 데이터를 조회한다.
	 * @Method Name : selectAppversionList
	 * @Date  	  	: 2019. 2. 20.
	 * @Author    	: choi
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SyAppVersion> selectAppversionList(Pageable<SyAppVersion, SyAppVersion> pageable) throws Exception;

	/**
	 * 
	 * @Desc      	: APP 버전 데이터를 등록한다.
	 * @Method Name : setAppversionData
	 * @Date  	  	: 2019. 2. 20.
	 * @Author    	: choi
	 * @param syAppVersion
	 * @return
	 * @throws Exception
	 */
	public int setAppversionData(SyAppVersion syAppVersion) throws Exception;

}
