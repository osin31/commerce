package kr.co.abcmart.bo.board.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.board.model.master.BdMemberCounselMemo;
import kr.co.abcmart.bo.board.repository.master.base.BaseBdMemberCounselMemoDao;

@Mapper
public interface BdMemberCounselMemoDao extends BaseBdMemberCounselMemoDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseBdMemberCounselMemoDao 클래스에 구현 되어있습니다.
	 * BaseBdMemberCounselMemoDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 *
	 */

	public BdMemberCounselMemo selectByPrimaryKey(BdMemberCounselMemo bdMemberCounselMemo) throws Exception;

	/**
	 * @Desc : 상담 메모 정보를 조회한다.
	 * @Method Name : selectBdMemberCounselMemoList
	 * @Date : 2019. 2. 12.
	 * @Author : kiowa
	 * @param bdMemberCounselMemo
	 * @return
	 * @throws Exception
	 */
	public List<BdMemberCounselMemo> selectBdMemberCounselMemoList(BdMemberCounselMemo bdMemberCounselMemo)
			throws Exception;

	/**
	 * @Desc : 관리자 메모 작성
	 * @Method Name : insertAdminMemo
	 * @Date : 2019. 2. 20.
	 * @Author : 신인철
	 * @param bdMemberCounselMemo
	 * @throws Exception
	 */
	public void insertAdminMemo(BdMemberCounselMemo bdMemberCounselMemo) throws Exception;

	/**
	 * @Desc : 추가된 메모 뿌려줄때 이용
	 * @Method Name : selectAddMemo
	 * @Date : 2019. 2. 20.
	 * @Author : 신인철
	 * @param bdMemberCounselMemo
	 * @return
	 * @throws Exception
	 */
	public BdMemberCounselMemo selectAddMemo(BdMemberCounselMemo bdMemberCounselMemo) throws Exception;

	/**
	 * @Desc : 상담 메모 삭제
	 * @Method Name : deleteAddminMemo
	 * @Date : 2019. 4. 15.
	 * @Author : 신인철
	 * @param bdMemberCounselMemo
	 * @throws Exception
	 */
	public void deleteAddminMemo(BdMemberCounselMemo bdMemberCounselMemo) throws Exception;

}
