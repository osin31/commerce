package kr.co.abcmart.bo.stats.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.stats.model.master.SaMemberLoginStatus;
import kr.co.abcmart.bo.stats.repository.master.base.BaseSaMemberLoginStatusDao;

@Mapper
public interface SaMemberLoginStatusDao extends BaseSaMemberLoginStatusDao {

     /**
     * 기본 insert, update, delete 메소드는 BaseSaMemberLoginStatusDao 클래스에 구현 되어있습니다.
     * BaseSaMemberLoginStatusDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
     *
     */

	public SaMemberLoginStatus selectByPrimaryKey(SaMemberLoginStatus saMemberLoginStatus) throws Exception;

	/**
	 * @Desc : 로그인 현황 데이터 조회
	 * @Method Name : getYesterdayMemberInfo
	 * @Date : 2019. 6. 28.
	 * @Author : 최경호
	 * @param saMemberLoginStatus
	 * @return
	 * @throws Exception
	 */
	public List<SaMemberLoginStatus> selectLoginList(SaMemberLoginStatus saMemberLoginStatus);

}
