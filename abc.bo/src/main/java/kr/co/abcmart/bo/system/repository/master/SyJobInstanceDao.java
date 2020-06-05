package kr.co.abcmart.bo.system.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.system.model.master.SyJobInstance;
import kr.co.abcmart.bo.system.repository.master.base.BaseSyJobInstanceDao;
import kr.co.abcmart.bo.system.vo.ScheduleVO;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface SyJobInstanceDao extends BaseSyJobInstanceDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseSyJobInstanceDao 클래스에 구현 되어있습니다.
	 * BaseSyJobInstanceDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 * ※ 중 요 ※
	 * 
	 * sqlSession 은 다음 메소드를 이용 하여 호출 합니다. 본인이 쓰고 있는 sqlSession(DB 호출)이 어떤 것인지 명확 하게
	 * 알아 보기 위함입니다.
	 * 
	 * - getSqlSessionMaster()
	 */

	public SyJobInstance selectByPrimaryKey(SyJobInstance syJobInstance) throws Exception;

	public List<SyJobInstance> selectJobInstanceForPaging(Pageable<ScheduleVO, SyJobInstance> pageable)
			throws Exception;

	public Integer selectJobInstanceForPagingCount(Pageable<ScheduleVO, SyJobInstance> pageable) throws Exception;

}
