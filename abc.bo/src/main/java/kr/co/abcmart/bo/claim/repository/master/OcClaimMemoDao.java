package kr.co.abcmart.bo.claim.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.claim.model.master.OcClaimMemo;
import kr.co.abcmart.bo.claim.repository.master.base.BaseOcClaimMemoDao;

@Mapper
public interface OcClaimMemoDao extends BaseOcClaimMemoDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseOcClaimMemoDao 클래스에 구현 되어있습니다.
	 * BaseOcClaimMemoDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
	 *
	 */

	public OcClaimMemo selectByPrimaryKey(OcClaimMemo ocClaimMemo) throws Exception;

	/**
	 * @Desc : 관리자 메모 갯수 조회
	 * @Method Name : selectByOrderNo
	 * @Date : 2019. 3. 21.
	 * @Author : KTH
	 * @param ocClaimMemo
	 * @return
	 * @throws Exception
	 */
	public List<OcClaimMemo> selectByOrderNo(OcClaimMemo ocClaimMemo) throws Exception;

	/**
	 * @Desc : 주문번호에 따른 메모 목록 조회 쿼리
	 * @Method Name : countByOrderNo
	 * @Date : 2019. 3. 21.
	 * @Author : KTH
	 * @param ocClaimMemo
	 * @return
	 * @throws Exception
	 */
	public int countByOrderNo(OcClaimMemo ocClaimMemo) throws Exception;

	/**
	 * @Desc : 메모 등록
	 * @Method Name : insertClaimMemo
	 * @Date : 2019. 3. 21.
	 * @Author : KTH
	 * @param ocClaimMemo
	 * @return
	 * @throws Exception
	 */
	public int insertClaimMemo(OcClaimMemo ocClaimMemo) throws Exception;

	/**
	 * @Desc : 클레임 메모 목록 조회 쿼리
	 * @Method Name : selectClaimMemoList
	 * @Date : 2019. 3. 29.
	 * @Author : KTH
	 * @param ocClaimMemo
	 * @return
	 * @throws Exception
	 */
	public List<OcClaimMemo> selectClaimMemoList(OcClaimMemo ocClaimMemo) throws Exception;

	/**
	 * @Desc : 클레임 메모 삭제 업데이트
	 * @Method Name : udpateClaimMemoForDelete
	 * @Date : 2019. 3. 29.
	 * @Author : KTH
	 * @param ocClaimMemo
	 * @return
	 * @throws Exception
	 */
	public int udpateClaimMemoForDelete(OcClaimMemo ocClaimMemo) throws Exception;

}
