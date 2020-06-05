package kr.co.abcmart.bo.product.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abcmart.bo.product.model.master.CmProductInfoNotice;
import kr.co.abcmart.bo.product.repository.master.base.BaseCmProductInfoNoticeDao;
import kr.co.abcmart.common.paging.Pageable;

@Mapper
public interface CmProductInfoNoticeDao extends BaseCmProductInfoNoticeDao {

	/**
	 * 기본 insert, update, delete 메소드는 BaseCmProductInfoNoticeDao 클래스에 구현 되어있습니다.
	 * BaseCmProductInfoNoticeDao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면
	 * 됩니다.
	 * 
	 */

	public CmProductInfoNotice selectByPrimaryKey(CmProductInfoNotice cmProductInfoNotice) throws Exception;

	/**
	 * @Desc : 총 상품고시정보 카운트
	 * @Method Name : selectCmProductInfoNoticeCount
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public int selectCmProductInfoNoticeCount(Pageable<CmProductInfoNotice, CmProductInfoNotice> pageable)
			throws Exception;

	/**
	 * @Desc : 상품정보고시 목록 조회
	 * @Method Name : selectCmProductInfoNoticeList
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	public List<CmProductInfoNotice> selectCmProductInfoNoticeList(
			Pageable<CmProductInfoNotice, CmProductInfoNotice> pageable) throws Exception;

	/**
	 * @Desc : 사용 중인 상품정보고시 목록 조회
	 * @Method Name : selectUseCmProductInfoNoticeList
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param itemCode
	 * @return
	 * @throws Exception
	 */
	public List<CmProductInfoNotice> selectUseCmProductInfoNoticeList(String itemCode) throws Exception;

	/**
	 * @Desc : 상품정보고시 상세 조회
	 * @Method Name : selectCmProductInfoNotice
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param cmProductInfoNotice
	 * @return
	 * @throws Exception
	 */
	public CmProductInfoNotice selectCmProductInfoNotice(CmProductInfoNotice cmProductInfoNotice) throws Exception;

	/**
	 * @Desc : 상품정보고시 수정
	 * @Method Name : updateCmProductInfoNotice
	 * @Date : 2019. 10. 21.
	 * @Author : 이가영
	 * @param cmProductInfoNotice
	 * @throws Exception
	 */
	public void updateCmProductInfoNotice(CmProductInfoNotice cmProductInfoNotice) throws Exception;

	/**
	 * @Desc : 상품정보고시 중복 카운트 조회
	 * @Method Name : selectDuplicationOfCmProductInfoNotice
	 * @Date : 2019. 11. 15.
	 * @Author : lee
	 * @param cmProductInfoNotice
	 * @return
	 * @throws Exception
	 */
	public CmProductInfoNotice selectDuplicationOfCmProductInfoNotice(CmProductInfoNotice cmProductInfoNotice)
			throws Exception;

}
