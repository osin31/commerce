package kr.co.abcmart.bo.claim.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.claim.model.master.IfBankda;
import kr.co.abcmart.bo.claim.repository.master.base.BaseIfBankdaDao;
import kr.co.abcmart.common.paging.Pageable;

/**
 * @Desc : 자동 입금 확인 서비스 관리
 * @FileName : IfBankdaDao.java
 * @Project : abc.bo
 * @Date : 2019. 4. 1.
 * @Author : ljyoung@3top.co.kr
 */
@Mapper
public interface IfBankdaDao extends BaseIfBankdaDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseIfBankdaDao 클래스에 구현 되어있습니다.
	 * BaseIfBankdaDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 * 
	 */

	public IfBankda selectByPrimaryKey(IfBankda ifBankda) throws Exception;

	/**
	 * @Desc : 자동 입금 확인 서비스 목록
	 * @Method Name : selectListBankda
	 * @Date : 2019. 4. 1.
	 * @Author : ljyoung@3top.co.kr
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<IfBankda> selectListBankda(Pageable<IfBankda, IfBankda> pageable) throws Exception;

	/**
	 * @Desc : 자동 입금 확인 서비스 목록 갯수
	 * @Method Name : selectListBankdaCount
	 * @Date : 2019. 4. 1.
	 * @Author : ljyoung@3top.co.kr
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectListBankdaCount(Pageable<IfBankda, IfBankda> pageable) throws Exception;

	/**
	 * @Desc : 오늘 Bankda 정보
	 * @Method Name : selectTodayInfoOfBankda
	 * @Date : 2019. 4. 1.
	 * @Author : ljyoung@3top.co.kr
	 * @param ifBankda
	 * @return
	 * @throws Exception
	 */
	public IfBankda selectTodayInfoOfBankda(IfBankda ifBankda) throws Exception;

	/**
	 * @Desc : BANKDA 테이블에서 bkcode max 값을 조회한다.
	 * @Method Name : selectMaxBankdaCode
	 * @Date : 2019. 4. 1.
	 * @Author : ljyoung@3top.co.kr
	 * @param ifBankda
	 * @return
	 * @throws Exception
	 */
	public int selectMaxBankdaCode(IfBankda ifBankda) throws Exception;
}
