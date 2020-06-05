package kr.co.abcmart.bo.event.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import kr.co.abcmart.bo.event.model.master.EvEventPublicationNumber;
import kr.co.abcmart.bo.event.repository.master.base.BaseEvEventPublicationNumberDao;

@Mapper
public interface EvEventPublicationNumberDao extends BaseEvEventPublicationNumberDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseEvEventPublicationNumberDao 클래스에 구현
	 * 되어있습니다. BaseEvEventPublicationNumberDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당
	 * 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public EvEventPublicationNumber selectByPrimaryKey(EvEventPublicationNumber evEventPublicationNumber)
			throws Exception;

	/**
	 * 지류 카운트
	 * 
	 * @Desc : 지류 카운트
	 * @Method Name : selectEvEventPublicationNumberCount
	 * @Date : 2019. 6. 11
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public Long selectEvEventPublicationNumberCount(String eventNo) throws Exception;

	/**
	 * 이벤트 발행 조회
	 * 
	 * @Desc : 이벤트 발행 조회
	 * @Method Name : selectEvEventPublicationNumberListByEventNo
	 * @Date : 2019. 7. 18
	 * @Author : easyhun
	 * @param eventNo
	 * @return
	 */
	public List<EvEventPublicationNumber> selectEvEventPublicationNumberListByEventNo(String eventNo) throws Exception;

	/**
	 * 지류 Seq
	 * 
	 * @Desc : 지류 Seq
	 * @Method Name : selectEvEventPublicationNumberSeq
	 * @Date : 2019. 7. 23
	 * @Author : easyhun
	 * @param seq
	 * @return
	 */
	public Long selectEvEventPublicationNumberSeq(@Param("seq") Integer seq) throws Exception;

	/**
	 * @Desc : 이벤트 난수 다중 등록
	 * @Method Name : insertRows
	 * @Date : 2019. 12. 5.
	 * @Author : tennessee
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	public Integer insertRows(List<EvEventPublicationNumber> rows) throws Exception;

}
