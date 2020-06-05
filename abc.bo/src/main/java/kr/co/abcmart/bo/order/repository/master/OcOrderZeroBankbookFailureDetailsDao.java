package kr.co.abcmart.bo.order.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.order.model.master.OcOrderZeroBankbookFailureDetails;
import kr.co.abcmart.bo.order.repository.master.base.BaseOcOrderZeroBankbookFailureDetailsDao;
import kr.co.abcmart.common.paging.Pageable;

/**
 * @Desc :
 * @FileName : OcOrderZeroBankbookFailureDetailsDao.java
 * @Project : abc.bo
 * @Date : 2019. 3. 25.
 * @Author : ljyoung@3top.co.kr
 */
@Mapper
public interface OcOrderZeroBankbookFailureDetailsDao extends BaseOcOrderZeroBankbookFailureDetailsDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcOrderZeroBankbookFailureDetailsDao 클래스에
	 * 구현 되어있습니다. BaseOcOrderZeroBankbookFailureDetailsDao는 절대 수정 불가 하며 새로운 메소드 추가
	 * 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public OcOrderZeroBankbookFailureDetails selectByPrimaryKey(
			OcOrderZeroBankbookFailureDetails ocOrderZeroBankbookFailureDetails) throws Exception;

	/**
	 * @Desc : 무통장입금실패 목록 조회
	 * @Method Name : selectAccountFailList
	 * @Date : 2019. 3. 25.
	 * @Author : ljyoung@3top.co.kr
	 * @param pageable
	 * @return
	 */
	public List<OcOrderZeroBankbookFailureDetails> selectList(
			Pageable<OcOrderZeroBankbookFailureDetails, OcOrderZeroBankbookFailureDetails> pageable);

	/**
	 * @Desc : 무통장입금실패 목록 갯수
	 * @Method Name : selectAccountFailListCount
	 * @Date : 2019. 3. 25.
	 * @Author : ljyoung@3top.co.kr
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectListCount(Pageable<OcOrderZeroBankbookFailureDetails, OcOrderZeroBankbookFailureDetails> pageable)
			throws Exception;
}
