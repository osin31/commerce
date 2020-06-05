package kr.co.abcmart.bo.stats.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.stats.model.master.SaMemberSnsConnectionStatus;
import kr.co.abcmart.bo.stats.repository.master.base.BaseSaMemberSnsConnectionStatusDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface SaMemberSnsConnectionStatusDao extends BaseSaMemberSnsConnectionStatusDao {

     /**
     * 기본 insert, update, delete 메소드는 BaseSaMemberSnsConnectionStatusDao 클래스에 구현 되어있습니다.
     * BaseSaMemberSnsConnectionStatusDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     *
     */

	public SaMemberSnsConnectionStatus selectByPrimaryKey(SaMemberSnsConnectionStatus saMemberSnsConnectionStatus) throws Exception;

	/**
	 * @Desc : SNS 계정 연결 현황 데이터 조회
	 * @Method Name : selectSnsConnectList
	 * @Date : 2019. 7. 1.
	 * @Author : 최경호
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<SaMemberSnsConnectionStatus> selectSnsConnectList(Pageable<SaMemberSnsConnectionStatus, SaMemberSnsConnectionStatus> pageable) throws Exception;

	/**
	 * @Desc : SNS 계정 연결 현황 데이터 개수 조회
	 * @Method Name : selectSnsConnectListCount
	 * @Date : 2019. 7. 1.
	 * @Author : 최경호
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectSnsConnectListCount(Pageable<SaMemberSnsConnectionStatus, SaMemberSnsConnectionStatus> pageable) throws Exception;

	/**
	 * @Desc : 전일기준 SNS 계정 연결 현황 데이터
	 * @Method Name : selectYesterdaySnsTotal
	 * @Date : 2019. 7. 2.
	 * @Author : 최경호
	 * @param :
	 * @return :SaMemberSnsConnectionStatus
	 * @throws Exception
	 */
	public SaMemberSnsConnectionStatus selectYesterdaySnsTotal() throws Exception;

}
